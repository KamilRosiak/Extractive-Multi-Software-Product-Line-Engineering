package apoMario.game.panels;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.apogames.entity.ApoAnimation;

import apoMario.ApoMarioConstants;
import apoMario.ApoMarioImageContainer;
import apoMario.analysis.ApoMarioSimulate;
import apoMario.game.ApoMarioPanel;
import apoMario.game.ApoMarioSearch;
import apoMario.game.ApoMarioSearchNode;
import apoMario.game.ApoMarioSearchRunner;
import apoMario.level.ApoMarioLevel;

public class ApoMarioSimulation extends ApoMarioModelMenu {

	private final int SIZE = ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE;
	
	public static final String FUNCTION_SIMULATION_BACK = "backSimulation";
	public static final String FUNCTION_SIMULATION_SIMULATE = "simulateSimulation";

	public static final String FUNCTION_SIMULATION_LEFTGAME = "leftgameSimulation";
	public static final String FUNCTION_SIMULATION_RIGHTGAME = "rightgameSimulation";
	public static final String FUNCTION_SIMULATION_LEFTWIDTH = "leftwidthSimulation";
	public static final String FUNCTION_SIMULATION_RIGHTWIDTH = "rightwidthSimulation";
	public static final String FUNCTION_SIMULATION_LEFTDIF = "leftdifficultySimulation";
	public static final String FUNCTION_SIMULATION_RIGHTDIF = "rightdifficultySimulation";
	public static final String FUNCTION_SIMULATION_LEFTREPLAY = "leftreplaySimulation";
	public static final String FUNCTION_SIMULATION_RIGHTREPLAY = "rightreplaySimulation";
	
	private ApoMarioOptionsDrag dragDifficulty, dragWidth, dragCount;
	
	private int count, curRound, difficulty, width, finish;
	
	private ApoMarioSimulationTab tabSimulate, tabAnalysis;
	
	private ArrayList<ApoMarioSimulationButton> buttons;
	
	private int changeX;
	
	private final int changeXAfter = (int)(ApoMarioConstants.GAME_HEIGHT - 8 * SIZE) / 15 + 1;
	
	private boolean bBreak;
	
	public ApoMarioSimulation(ApoMarioPanel game) {
		super(game);
	}

	@Override
	public void init() {
		super.init();
		
		int size = ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE;
		
		int w = ApoMarioConstants.GAME_WIDTH - 4 * size;
		if (this.tabSimulate == null) {
			this.tabSimulate = new ApoMarioSimulationTab(size * 2, (int)(size * 2.5), w/2, size, "simulate");
		}
		if (this.tabAnalysis == null) {
			this.tabAnalysis = new ApoMarioSimulationTab(size * 2 + w/2, (int)(size * 2.5), w/2, size, "analysis");
		}
		if (this.buttons == null) {
			this.buttons = new ArrayList<ApoMarioSimulationButton>();
		}
		
		int x = (int)(this.getGame().getButtons()[24].getX()) - size;
		int y = (int)(this.getGame().getButtons()[24].getY());
		super.getRunner().setX(x - 3 * size);
		super.getRunner().setY(y - 0 * size);
		super.getRunner().setEnd(x - 3 * size, y - 0 * size);
		super.setCurNode(1);
		
		
		x = (int)(this.getGame().getButtons()[6].getX() + this.getGame().getButtons()[6].getWidth());
		y = (int)(this.getGame().getButtons()[6].getY() + ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE / 5 + 1 * size);
		int maxWidth = ApoMarioConstants.GAME_WIDTH*3/4 - 7 * ApoMarioConstants.APP_SIZE / 2 - x;
		if (this.dragDifficulty == null) {
			this.dragDifficulty = new ApoMarioOptionsDrag(x, y, ApoMarioConstants.DRAG_WIDTH, ApoMarioConstants.DRAG_HEIGHT, maxWidth);
			this.difficulty = ApoMarioConstants.SIM_DIFFICULTY;
		}
		if (this.dragWidth == null) {
			y = (int)(this.getGame().getButtons()[6].getY() + ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE / 5 + 3 * size);
			this.dragWidth = new ApoMarioOptionsDrag(x, y, ApoMarioConstants.DRAG_WIDTH, ApoMarioConstants.DRAG_HEIGHT, maxWidth);
			this.width = ApoMarioConstants.SIM_WIDTH;
		}
		if (this.dragCount == null) {
			y = (int)(this.getGame().getButtons()[6].getY() + ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE / 5 - 1 * size);
			this.dragCount = new ApoMarioOptionsDrag(x, y, ApoMarioConstants.DRAG_WIDTH, ApoMarioConstants.DRAG_HEIGHT, maxWidth);
		}
		if (this.count <= 0) {
			this.count = 1;
		}
		if (this.width < ApoMarioConstants.MIN_WIDTH) {
			this.width = ApoMarioConstants.MIN_WIDTH;
		}
		this.setDragObjects();
		
		this.setSimulateTab();
		
		this.bBreak = false;
	}
	
	private void setSimulateTab() {
		this.tabSimulate.setChoosen(true);
		this.tabAnalysis.setChoosen(false);
		for (int i = 27; i < 34; i++) {
			this.getGame().getButtons()[i].setBVisible(true);
		}
		for (int i = 34; i < 36; i++) {
			getGame().getButtons()[i].setBVisible(false);
		}
	}
	
	private void setAnalysisTab() {
		this.tabSimulate.setChoosen(false);
		this.tabAnalysis.setChoosen(true);
		for (int i = 27; i < 34; i++) {
			this.getGame().getButtons()[i].setBVisible(false);
		}
		if (buttons.size() < 2 * changeXAfter) {
			for (int i = 34; i < 36; i++) {
				getGame().getButtons()[i].setBVisible(false);
			}
		} else {
			for (int i = 34; i < 36; i++) {
				getGame().getButtons()[i].setBVisible(true);
			}
		}
	}
	
	private void setDragObjects() {
		if (this.getGame() != null) {
			int levelWidthPercent = (int)((this.width - ApoMarioConstants.MIN_WIDTH) * 100f / (float)(ApoMarioConstants.MAX_WIDTH - ApoMarioConstants.MIN_WIDTH));
			int levelDifficultyPercent = (int)((this.difficulty - ApoMarioConstants.MIN_DIFFICULTY) * 100f / (float)(ApoMarioConstants.MAX_DIFFICULTY - ApoMarioConstants.MIN_DIFFICULTY));
			int levelCountPercent = (int)((this.count - ApoMarioConstants.MIN_GAMES) * 100f / (float)(ApoMarioConstants.SIM_MAX_ROUNDS - ApoMarioConstants.MIN_GAMES));			
			this.dragDifficulty.setToPercent(levelDifficultyPercent);
			this.dragWidth.setToPercent(levelWidthPercent);
			this.dragCount.setToPercent(levelCountPercent);
		}
	}
	
	@Override
	public void makeBackground() {
		int size = ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE;

		this.setIBackground(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(ApoMarioConstants.GAME_WIDTH, ApoMarioConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB));
		Graphics2D g = (Graphics2D)(super.getIBackground().getGraphics());
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		BufferedImage iMenuTile = ApoMarioImageContainer.MENU;
		for (int x = 0; x < 20; x++) {
			for (int y = 0; y < 15; y++) {
				g.drawImage(iMenuTile.getSubimage(0 * size, 0 * size, size, size), x * size, y * size, null);
			}
		}
		//buttons 0, 1, 2, 3, 4, 19, 20
		int x = (int)(this.getGame().getButtons()[24].getX()) - size;
		int y = (int)(this.getGame().getButtons()[24].getY());
		g.drawImage(iMenuTile.getSubimage(1 * size, 2 * size, size, size), x, y, null);
		g.drawImage(iMenuTile.getSubimage(1 * size, 3 * size, size, size), x - 1 * size, y, null);
		g.drawImage(iMenuTile.getSubimage(1 * size, 3 * size, size, size), x - 2 * size, y, null);
		g.drawImage(iMenuTile.getSubimage(4 * size, 2 * size, size, size), x - 3 * size, y, null);
		
		g.setColor(new Color(255, 255, 255, 200));
		g.fillRoundRect(2 * size, (int)(size), ApoMarioConstants.GAME_WIDTH - size * 4, (int)(ApoMarioConstants.GAME_HEIGHT - 4 * size), 10, 10);
		g.setColor(Color.BLACK);
		g.drawRoundRect(2 * size, (int)(size), ApoMarioConstants.GAME_WIDTH - size * 4, (int)(ApoMarioConstants.GAME_HEIGHT - 4 * size), 10, 10);

		g.dispose();
	}

	@Override
	public void makeBackgroundAnimation() {
		int size = ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE;
		BufferedImage iMenuTile = ApoMarioImageContainer.MENU;
		
		//if (this.getBackgroundAnimation() == null) {
			this.setBackgroundAnimation(new ArrayList<ApoAnimation>());
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(6 * size, 0 * size, 4 * size, size), 0 * size, 4 * size, 4, 400));
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(6 * size, 1 * size, 4 * size, size), 1 * size, 9 * size, 4, 350));
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(2 * size, 1 * size, 4 * size, size), 19 * size, 8 * size, 4, 370));
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(6 * size, 0 * size, 4 * size, size), 18 * size, 14 * size, 4, 390));
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(2 * size, 0 * size, 4 * size, size), 5 * size, 12 * size, 4, 365));
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(6 * size, 1 * size, 4 * size, size), 8 * size, 0 * size, 4, 355));
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(13 * size, 1 * size, 1 * size, 1 * size), 18 * size, 2 * size, 1, 1000000));
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(12 * size, 0 * size, 1 * size, 2 * size), 12 * size, 12 * size, 1, 1000000));
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(10 * size, 1 * size, 1 * size, 1 * size), 0 * size, 13 * size, 1, 1000000));
		//}
	}

	@Override
	public void makeRunner() {
		int size = ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE;
		BufferedImage iMenuTile = ApoMarioImageContainer.MENU;
		int x = (int)(this.getGame().getButtons()[24].getX()) - size;
		int y = (int)(this.getGame().getButtons()[24].getY());
		
		//if (this.getRunner() == null) {
			this.setRunner(new ApoMarioSearchRunner(iMenuTile.getSubimage(0 * size, 1 * size, size * 2, size), x - 3 * size, y - 0 * size));
			this.setCurNode(1);
		//}
	}

	@Override
	public void makeSearch() {
		int size = ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE;
		int x = (int)(this.getGame().getButtons()[24].getX()) - size;
		int y = (int)(this.getGame().getButtons()[24].getY());
		
		this.setSearch(new ApoMarioSearch());
		ApoMarioSearchNode node = new ApoMarioSearchNode(0, x - 0 * size, y - 0 * size);
		node.addConnectedNode(1);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(1, x - 3 * size, y - 0 * size);
		node.addConnectedNode(0);
		this.getSearch().addNode(node);
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			if (this.getGame().isBSimulate()) {
				this.bBreak = true;
			} else {
				this.getGame().setMenu();
			}
		} else if (button == KeyEvent.VK_T) {
			if (ApoMarioConstants.TILE_SET == ApoMarioConstants.TILE_SET_EAT) {
				ApoMarioConstants.TILE_SET = ApoMarioConstants.TILE_SET_MARIO;
			} else if (ApoMarioConstants.TILE_SET == ApoMarioConstants.TILE_SET_MARIO) {
				ApoMarioConstants.TILE_SET = ApoMarioConstants.TILE_SET_EAT;
			}
			ApoMarioImageContainer.setTileSet(ApoMarioConstants.TILE_SET);
			this.getGame().setSimulation();
		} else {
			super.keyButtonReleasedArrowAndEnter(button, character);
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		super.setExcecuteFunction(function);
		int oldEndNode = this.getEndNode();
		this.setEndNode(-1);
		if (function.equals(ApoMarioSimulation.FUNCTION_SIMULATION_BACK)) {
			super.setEndNode(0);
		} else if (function.equals(ApoMarioSimulation.FUNCTION_SIMULATION_SIMULATE)) {
			this.startSimulation();
		} else {
			this.checkFunction(function);
		}
		if ((oldEndNode == this.getEndNode()) && (this.getEndNode() != -1)) {
			this.getRunner().setX(this.getSearch().getNodes().get(oldEndNode).getX());
			this.getRunner().setY(this.getSearch().getNodes().get(oldEndNode).getY());
			this.getRunner().setEnd((int)(this.getRunner().getX()), (int)(this.getRunner().getY()));
			this.setWay(null);
			this.setCurNode(oldEndNode);
			this.excecuteFunction();
		} else {
			super.searchWay();
		}
	}
	
	private void checkFunction(String function) {
		if (function.equals(ApoMarioSimulation.FUNCTION_SIMULATION_LEFTGAME)) {
			this.count -= 1;
			if (this.count < ApoMarioConstants.MIN_GAMES) {
				this.count = ApoMarioConstants.MIN_GAMES;
			}
			int percent = (int)((this.count - ApoMarioConstants.MIN_GAMES) * 100f / (float)(ApoMarioConstants.SIM_MAX_ROUNDS - ApoMarioConstants.MIN_GAMES));
			this.dragCount.setToPercent(percent);
		} else if (function.equals(ApoMarioSimulation.FUNCTION_SIMULATION_RIGHTGAME)) {
			this.count += 1;
			if (this.count > ApoMarioConstants.SIM_MAX_ROUNDS) {
				this.count = ApoMarioConstants.SIM_MAX_ROUNDS;
			}
			int percent = (int)((this.count - ApoMarioConstants.MIN_GAMES) * 100f / (float)(ApoMarioConstants.SIM_MAX_ROUNDS - ApoMarioConstants.MIN_GAMES));
			this.dragCount.setToPercent(percent);
		} else if (function.equals(ApoMarioSimulation.FUNCTION_SIMULATION_LEFTWIDTH)) {
			this.width -= 1;
			if (this.width < ApoMarioConstants.MIN_WIDTH) {
				this.width = ApoMarioConstants.MIN_WIDTH;
			}
			int percent = (int)((this.width - ApoMarioConstants.MIN_WIDTH) * 100f / (float)(ApoMarioConstants.MAX_WIDTH - ApoMarioConstants.MIN_WIDTH));
			this.dragWidth.setToPercent(percent);
		} else if (function.equals(ApoMarioSimulation.FUNCTION_SIMULATION_RIGHTWIDTH)) {
			this.width += 1;
			if (this.width > ApoMarioConstants.MAX_WIDTH) {
				this.width = ApoMarioConstants.MAX_WIDTH;
			}
			int percent = (int)((this.width - ApoMarioConstants.MIN_WIDTH) * 100f / (float)(ApoMarioConstants.MAX_WIDTH - ApoMarioConstants.MIN_WIDTH));
			this.dragWidth.setToPercent(percent);
		} else if (function.equals(ApoMarioSimulation.FUNCTION_SIMULATION_LEFTDIF)) {
			this.difficulty -= 1;
			if (this.difficulty < ApoMarioConstants.MIN_DIFFICULTY) {
				this.difficulty = ApoMarioConstants.MIN_DIFFICULTY;
			}
			int percent = (int)((this.difficulty - ApoMarioConstants.MIN_DIFFICULTY) * 100f / (float)(ApoMarioConstants.MAX_DIFFICULTY - ApoMarioConstants.MIN_DIFFICULTY));
			this.dragDifficulty.setToPercent(percent);
		} else if (function.equals(ApoMarioSimulation.FUNCTION_SIMULATION_RIGHTDIF)) {
			this.difficulty += 1;
			if (this.difficulty > ApoMarioConstants.MAX_DIFFICULTY) {
				this.difficulty = ApoMarioConstants.MAX_DIFFICULTY;
			}
			int percent = (int)((this.difficulty - ApoMarioConstants.MIN_DIFFICULTY) * 100f / (float)(ApoMarioConstants.MAX_DIFFICULTY - ApoMarioConstants.MIN_DIFFICULTY));
			this.dragDifficulty.setToPercent(percent);
		} else if (function.equals(ApoMarioSimulation.FUNCTION_SIMULATION_RIGHTREPLAY)) {
			this.changeX += this.changeXAfter;

			while (this.changeX + 1 * this.changeXAfter > this.buttons.size()) {
				this.changeX -= this.changeXAfter;
				if (this.changeX < 0) {
					this.changeX = 0;
					break;
				}
			}
		} else if (function.equals(ApoMarioSimulation.FUNCTION_SIMULATION_LEFTREPLAY)) {
			this.changeX -= this.changeXAfter;
			while (this.changeX < 0) {
				this.changeX = 0;
			}
		}
	}
	
	public void releasedEnter() {
		if (this.getCurNode() == 0) {
			this.getGame().setMenu();
		}
	}
	
	@Override
	public void excecuteFunction() {
		String function = super.getExcecuteFunction();
		if (function == null) {
		} else if (function.equals(ApoMarioSimulation.FUNCTION_SIMULATION_BACK)) {
			this.getGame().setMenu();
		} else if (function.equals(ApoMarioSimulation.FUNCTION_SIMULATION_SIMULATE)) {
			this.startSimulation();
		}
	}
	
	public int getCount() {
		return this.count;
	}

	public int getCurRound() {
		return this.curRound;
	}

	private void startSimulation() {
		Thread t = new Thread(new Runnable() {
			public void run() {
				ApoMarioSimulation.this.bBreak = false;
				ApoMarioSimulation.this.buttons.clear();
				ApoMarioSimulation.this.curRound = 0;
				int finishCount = 0;
				getGame().setBSimulate(true);
				ApoMarioSimulate simulate = new ApoMarioSimulate(getGame(), ApoMarioSimulation.this.width, ApoMarioSimulation.this.difficulty);
				int xChange = 0;
				int yChange = 0;
				ApoMarioSimulation.this.changeX = 0;
				int width = (int)(ApoMarioConstants.GAME_WIDTH - 4 * SIZE) / 2 - 4;
				
				while ((ApoMarioSimulation.this.curRound < ApoMarioSimulation.this.count) && (!ApoMarioSimulation.this.bBreak)) {
					ApoMarioSimulation.this.curRound += 1;
					if (getGame() != null) {
						simulate.startSimulate(true);
					} else {
						break;
					}
					boolean bFinish = false;
					if ((!getGame().getLevel().getPlayers().get(0).isBDie()) && (getGame().getLevel().getTime() > 0)) {
						finishCount += 1;
						bFinish = true;
					}
					ApoMarioLevel level = getGame().getLevel();
					try {
						String name = "human";
						if (level.getPlayers().get(0).getAi() != null) {
							name = level.getPlayers().get(0).getAi().getTeamName();
						}
						ApoMarioSimulationHelp help = new ApoMarioSimulationHelp(level.getWidth(), level.getDifficulty(), level.getLevelInt(), name, level.getPlayers().get(0).getPoints(), bFinish, getGame().getLevel().getReplay().getClone());
						if (level.getPlayers().get(1).getAi() != null) {
							help.setPlayerTwo(level.getPlayers().get(1).getAi().getTeamName(), level.getPlayers().get(1).getPoints());
						}
						
						ApoMarioSimulation.this.buttons.add(new ApoMarioSimulationButton(xChange + 2 + 2 * SIZE, yChange * 15 + SIZE * 4, width, 14, help));
						
						yChange += 1;
						if (yChange >= ApoMarioSimulation.this.changeXAfter) {
							xChange += width;
							yChange = 0;
						}
					} catch (Exception ex) {
					}
					if (ApoMarioSimulation.this.bBreak) {
						getGame().setBSimulate(false);
						break;
					}
				}
				ApoMarioSimulation.this.finish = finishCount;
				getGame().setSimulation();
				getGame().setBSimulate(false);
				setAnalysisTab();
				
			}
		});
		t.start();
	}

	@Override
	public void mouseButtonReleased(int x, int y) {
		if (this.tabAnalysis.isChoosen()) {
			for (int i = 0; i < this.buttons.size(); i++) {
				if ((this.buttons.get(i).isBVisible()) && (this.buttons.get(i).getReleased(x + (int)(changeX * this.buttons.get(i).getWidth() / this.changeXAfter), y))) {
					this.getGame().setReplay(this.buttons.get(i).getHelp().getReplay());
					return;
				}
			}
		}
		if (this.tabAnalysis != null) {
			if (this.tabAnalysis.getReleased(x, y)) {
				this.setAnalysisTab();
				return;
			}
		}
		if (this.tabSimulate != null) {
			if (this.tabSimulate.getReleased(x, y)) {
				this.setSimulateTab();
				return;
			}
		}
		if ((this.dragDifficulty != null) && (this.dragDifficulty.isBVisible())) {
			if (this.dragDifficulty.mouseReleased(x, y)) {
				this.difficulty = (int)(this.dragDifficulty.getPercent() / 100f * (ApoMarioConstants.MAX_DIFFICULTY - ApoMarioConstants.MIN_DIFFICULTY)) + ApoMarioConstants.MIN_DIFFICULTY;
			}
		}
		if ((this.dragWidth != null) && (this.dragWidth.isBVisible())) {
			if (this.dragWidth.mouseReleased(x, y)) {
				this.width = (int)(this.dragWidth.getPercent() * (ApoMarioConstants.MAX_WIDTH - ApoMarioConstants.MIN_WIDTH) / 100f) + ApoMarioConstants.MIN_WIDTH;
			}
		}
		if ((this.dragCount != null) && (this.dragCount.isBVisible())) {
			if (this.dragCount.mouseReleased(x, y)) {
				this.count = (int)(this.dragCount.getPercent() / 100f * (ApoMarioConstants.SIM_MAX_ROUNDS - ApoMarioConstants.MIN_GAMES)) + ApoMarioConstants.MIN_GAMES;
			}
		}
	}

	@Override
	public boolean mouseDragged(int x, int y) {
		if (this.mouseMoved(x, y)) {
			this.mouseButtonReleased(x, y);
			return true;
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int x, int y) {
		if ((this.tabAnalysis != null) && (this.tabAnalysis.isChoosen())) {
			for (int i = 0; i < this.buttons.size(); i++) {
				if ((this.buttons.get(i).isBVisible()) && (this.buttons.get(i).getMove(x + (int)(changeX * this.buttons.get(i).getWidth() / this.changeXAfter), y))) {
				}
			}
		}
		if ((this.dragDifficulty != null) && (this.dragDifficulty.isBVisible())) {
			if (this.dragDifficulty.mouseMoved(x, y)) {
				return true;
			}
		}
		if ((this.dragWidth != null) && (this.dragWidth.isBVisible())) {
			if (this.dragWidth.mouseMoved(x, y)) {
				return true;
			}
		}
		if ((this.dragCount != null) && (this.dragCount.isBVisible())) {
			if (this.dragCount.mouseMoved(x, y)) {
				return true;
			}
		}
		if (this.tabAnalysis != null) {
			this.tabAnalysis.getMove(x, y);
		}
		if (this.tabSimulate != null) {
			this.tabSimulate.getMove(x, y);
		}
		return false;
	}

	@Override
	public boolean mousePressed(int x, int y, boolean bRight) {
		if (this.tabAnalysis != null) {
			this.tabAnalysis.getPressed(x, y);
		}
		if (this.tabSimulate != null) {
			this.tabSimulate.getPressed(x, y);
		}
		if (this.tabAnalysis.isChoosen()) {
			for (int i = 0; i < this.buttons.size(); i++) {
				if (this.buttons.get(i).getPressed(x + (int)(changeX * this.buttons.get(i).getWidth() / this.changeXAfter), y)) {
				}
			}
		}
		return false;
	}
	
	@Override
	public void think(int delta) {
		for (int i = 0; i < this.getGame().getButtons().length; i++) {
			if (this.getGame().getButtons()[i].isBWait()) {
				int wait = this.getGame().getButtons()[i].getWait();
				this.getGame().getButtons()[i].think(delta);
				if (this.getGame().getButtons()[i].getWait() < wait) {
					this.checkFunction(this.getGame().getButtons()[i].getFunction());
				}
			}
		}
		super.thinkRunnerAndAnimation(delta);
	}

	@Override
	public void render(Graphics2D g) {
		super.renderBackgroundAndAnimation(g);
		
		this.renderSimulation(g);
		
		super.renderButtonsAndRunner(g);
	}
	
	private void renderSimulation(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.setFont(ApoMarioConstants.FONT_CREDITS);
		int size = ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE;
		String s = "Simulation";
		g.drawString(s, ApoMarioConstants.GAME_WIDTH/2 - g.getFontMetrics().stringWidth(s)/2, size * 2);
		
		this.tabSimulate.render(g, 0, 0);
		this.tabAnalysis.render(g, 0, 0);
		
		g.setFont(ApoMarioConstants.FONT_STATISTICS);
		g.setColor(Color.BLACK);
		if (this.tabSimulate.isChoosen()) {
			// Difficulty
			s = "Difficulty: "+this.difficulty;
			int w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, ApoMarioConstants.GAME_WIDTH/2 - w/2, ApoMarioOptions.Y_DIFFICULTY + g.getFontMetrics().getHeight()/2 + 1 * size);
			// Level width
			s = "Levelwidth: "+this.width;
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, ApoMarioConstants.GAME_WIDTH/2 - w/2, ApoMarioOptions.Y_DIFFICULTY + g.getFontMetrics().getHeight()/2 + 3 * size);
			// Level Count
			s = "Games: "+this.count;
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, ApoMarioConstants.GAME_WIDTH/2 - w/2, ApoMarioOptions.Y_DIFFICULTY + g.getFontMetrics().getHeight()/2 - 1 * size);
			
			// Dragobjekte
			if (this.dragDifficulty != null) {
				this.dragDifficulty.render(g, 0, 0);
			}
			if (this.dragWidth != null) {
				this.dragWidth.render(g, 0, 0);
			}
			if (this.dragCount != null) {
				this.dragCount.render(g, 0, 0);
			}
		} else if (this.tabAnalysis.isChoosen()) {
			if (this.buttons.size() > 0) {
				Shape shape = g.getClip();
				g.setClip(new Rectangle2D.Float(2 * size, 0, (int)(ApoMarioConstants.GAME_WIDTH - 4 * size), (int)(ApoMarioConstants.GAME_HEIGHT)));
				g.setFont(ApoMarioConstants.FONT_FPS);
				int changeX = this.changeX;
				for (int i = changeX; i < this.buttons.size() && i < changeX + 2 * this.changeXAfter; i++) {
					this.buttons.get(i).render(g, (int)(changeX * this.buttons.get(i).getWidth() / this.changeXAfter), 0);
				}
				
				g.setClip(shape);
			}
			g.setFont(ApoMarioConstants.FONT_STATISTICS);
			g.setColor(Color.BLACK);
			if ((this.buttons.size() > 0) && (this.buttons.get(0).getHelp().getReplay().getPlayerTwo().getMaxSteps() > 0)) {
				int playerOneWon = 0;
				for (int i = 0; i < this.buttons.size(); i++) {
					if (this.buttons.get(i).getHelp().getPointsOne() > this.buttons.get(i).getHelp().getPointsTwo()) {
						playerOneWon += 1;
					}
				}
				s = "p1 won: "+playerOneWon+" / p2 won: "+(this.buttons.size() - playerOneWon);
				int w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, ApoMarioConstants.GAME_WIDTH/2 - w/2, (int)(ApoMarioConstants.GAME_HEIGHT - 3 * size - 3));
			} else {
				if (this.buttons.size() > 0) {
					s = "Finish: "+this.finish+" / "+this.count;
				} else {
					s = "No results yet";
				}
				int w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, ApoMarioConstants.GAME_WIDTH/2 - w/2, (int)(ApoMarioConstants.GAME_HEIGHT - 3 * size - 3));
			}
		}
	}

}
