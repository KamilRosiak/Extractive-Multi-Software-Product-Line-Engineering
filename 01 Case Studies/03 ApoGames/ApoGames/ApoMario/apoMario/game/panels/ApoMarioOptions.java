package apoMario.game.panels;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.apogames.entity.ApoAnimation;
import org.apogames.help.ApoHelp;

import apoMario.ApoMarioComponent;
import apoMario.ApoMarioConstants;
import apoMario.ApoMarioImageContainer;
import apoMario.game.ApoMarioPanel;
import apoMario.game.ApoMarioSearch;
import apoMario.game.ApoMarioSearchNode;
import apoMario.game.ApoMarioSearchRunner;

/**
 * OptionPanelklasse<br />
 * Klasse, die die Options darstellt und die dazu benötigten Schieberegler und Buttons<br />
 * @author Dirk Aporius
 */
public class ApoMarioOptions extends ApoMarioModelMenu {

	public static final String FUNCTION_OPTIONS_BACK = "backOptions";
	public static final String FUNCTION_OPTIONS_LEFT_DIFFICULTY = "prevDifficulty";
	public static final String FUNCTION_OPTIONS_RIGHT_DIFFICULTY = "nextDifficulty";
	public static final String FUNCTION_OPTIONS_LEFT_LEVELWIDTH = "prevLevelwidth";
	public static final String FUNCTION_OPTIONS_RIGHT_LEVELWIDTH = "nextLevelwidth";
	
	public static final int Y_DIFFICULTY = ApoMarioConstants.GAME_HEIGHT*1/4 + 15 * ApoMarioConstants.APP_SIZE + 7 * ApoMarioConstants.APP_SIZE;
	public static final int Y_WIDTH = ApoMarioConstants.GAME_HEIGHT*1/2 + 10 * ApoMarioConstants.APP_SIZE;
	
	private ApoMarioOptionsDrag dragDifficulty, dragWidth;
	
	private ApoMarioOptionsButton myButton;
	
	private ApoMarioOptionsButton myButtonDebug;
	
	private ApoMarioOptionsTextfield myTextfield;
	
	public ApoMarioOptions(ApoMarioPanel game) {
		super(game);
		this.init();
	}
	
	public void init() {
		int x = (int)(this.getGame().getButtons()[6].getX() + this.getGame().getButtons()[6].getWidth());
		int y = (int)(this.getGame().getButtons()[6].getY() + ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE / 5);
		int maxWidth = ApoMarioConstants.GAME_WIDTH*3/4 - 7 * ApoMarioConstants.APP_SIZE / 2 - x;
		if (this.dragDifficulty == null) {
			this.dragDifficulty = new ApoMarioOptionsDrag(x, y, ApoMarioConstants.DRAG_WIDTH, ApoMarioConstants.DRAG_HEIGHT, maxWidth);
		}
		if (this.dragWidth == null) {
			y = (int)(this.getGame().getButtons()[8].getY() + ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE / 5);
			this.dragWidth = new ApoMarioOptionsDrag(x, y, ApoMarioConstants.DRAG_WIDTH, ApoMarioConstants.DRAG_HEIGHT, maxWidth);
		}
		int width = 15 * ApoMarioConstants.APP_SIZE;
		if (this.myButton == null) {
			x = ApoMarioConstants.GAME_WIDTH*1/4 - width/2;
			y += 20 * ApoMarioConstants.APP_SIZE;
			this.myButton = new ApoMarioOptionsButton(x, y, width, width);
			this.myButton.setBActive(true);
		}
		if (this.myTextfield == null) {
			x = x + width + 3 * ApoMarioConstants.APP_SIZE;
			int height = width;
			width = ApoMarioConstants.GAME_WIDTH/2;
			this.myTextfield = new ApoMarioOptionsTextfield(x, y, width, height);
			this.myTextfield.setBEnabled(true);
			this.myTextfield.setFont(ApoMarioConstants.FONT_STATISTICS);
			this.myTextfield.removeCurStringAndSetCurString(String.valueOf(this.getGame().getLevel().getLevelInt()));
		}
		if (this.myButtonDebug == null) {
			width = 15 * ApoMarioConstants.APP_SIZE;
			x = ApoMarioConstants.GAME_WIDTH*1/2 - width/2 - 15 * ApoMarioConstants.APP_SIZE;
			y = ApoMarioConstants.GAME_HEIGHT*1/4 - 15 * ApoMarioConstants.APP_SIZE;
			this.myButtonDebug = new ApoMarioOptionsButton(x, y, width, width);
			if (ApoMarioConstants.DEBUG) {
				this.myButtonDebug.setBActive(true);
				this.changeDebug();
			}
		}
		this.setOptionsDrag();

		super.init();
		
		int size = ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE;
		x = (int)(this.getGame().getButtons()[24].getX()) - size;
		y = (int)(this.getGame().getButtons()[24].getY());
		this.getRunner().setX(x - 3 * size);
		this.getRunner().setY(y - 0 * size);
		super.getRunner().setEnd(x - 3 * size, y - 0 * size);
		this.setCurNode(1);
	}
	
	@Override
	public void makeBackground() {
		int size = ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE;

		this.setIBackground(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(ApoMarioConstants.GAME_WIDTH, ApoMarioConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB));
		Graphics2D g = (Graphics2D)(this.getIBackground().getGraphics());
		
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
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(6 * size, 0 * size, 4 * size, size), 0 * size, 2 * size, 4, 400));
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(6 * size, 1 * size, 4 * size, size), 1 * size, 10 * size, 4, 350));
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(2 * size, 0 * size, 4 * size, size), 18 * size, 3 * size, 4, 380));
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(2 * size, 1 * size, 4 * size, size), 19 * size, 7 * size, 4, 370));
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(6 * size, 0 * size, 4 * size, size), 17 * size, 14 * size, 4, 390));
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(2 * size, 0 * size, 4 * size, size), 3 * size, 13 * size, 4, 365));
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(6 * size, 1 * size, 4 * size, size), 9 * size, 12 * size, 4, 365));
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(6 * size, 0 * size, 4 * size, size), 7 * size, 14 * size, 4, 365));

			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(13 * size, 1 * size, 1 * size, 1 * size), 10 * size, 0 * size, 1, 1000000));
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(12 * size, 0 * size, 1 * size, 2 * size), 1 * size, 5 * size, 1, 1000000));
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(10 * size, 1 * size, 1 * size, 1 * size), 12 * size, 13 * size, 1, 1000000));
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
	
	public boolean isFreezeLevelInt() {
		if ((this.myButton != null) && (!this.myButton.isBActive())) {
			return true;
		}
		return false;
	}
	
	public void stopOptions() {
		this.myTextfield.setBSelect(false);
	}
	
	public void setOptionsDrag() {
		if (this.getGame() != null) {
			int levelWidthPercent = (int)((this.getGame().getLevel().getWidth() - ApoMarioConstants.MIN_WIDTH) * 100f / (float)(ApoMarioConstants.MAX_WIDTH - ApoMarioConstants.MIN_WIDTH));
			int levelDifficultyPercent = (int)((this.getGame().getLevel().getDifficulty() - ApoMarioConstants.MIN_DIFFICULTY) * 100f / (float)(ApoMarioConstants.MAX_DIFFICULTY - ApoMarioConstants.MIN_DIFFICULTY));
			this.dragDifficulty.setToPercent(levelDifficultyPercent);
			this.dragWidth.setToPercent(levelWidthPercent);
		}
	}
	
	@Override
	public void mouseButtonFunction(String function) {
		this.setExcecuteFunction(function);
		int oldEndNode = this.getEndNode();
		this.setEndNode(-1);
		if (function.equals(ApoMarioOptions.FUNCTION_OPTIONS_BACK)) {
			this.setEndNode(0);
		} else if (function.equals(ApoMarioOptions.FUNCTION_OPTIONS_LEFT_DIFFICULTY)) {
			this.difOrWidth(function);
		} else if (function.equals(ApoMarioOptions.FUNCTION_OPTIONS_RIGHT_DIFFICULTY)) {
			this.difOrWidth(function);
		} else if (function.equals(ApoMarioOptions.FUNCTION_OPTIONS_LEFT_LEVELWIDTH)) {
			this.difOrWidth(function);		
		} else if (function.equals(ApoMarioOptions.FUNCTION_OPTIONS_RIGHT_LEVELWIDTH)) {
			this.difOrWidth(function);
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

	public void releasedEnter() {
		if (this.getCurNode() == 0) {
			this.getGame().setMenu();
		}
	}
	
	@Override
	public void excecuteFunction() {
		String function = super.getExcecuteFunction();
		if (function == null) {
		} else if (function.equals(ApoMarioOptions.FUNCTION_OPTIONS_BACK)) {
			this.getGame().setMenu();
		}
	}
	
	private void difOrWidth(String function) {
		this.think(0, function);
		this.newLevel(0);
	}
	
	public void makeNewLevel(long random) {
		if (random == 0) {
			this.getGame().getLevel().makeLevel(System.nanoTime(), false, true, -1, -1);
		} else {
			this.getGame().getLevel().makeLevel(random, false, true, -1, -1);
		}
		if (this.getGame().getMiniMap() != null) {
			this.getGame().getMiniMap().makeMiniMap();
		}
		this.setOptionsDrag();
	}
	
	public void think(int delta, String function) {
		int difficulty = this.getGame().getLevel().getDifficulty();
		int width = this.getGame().getLevel().getWidth();
		if (function.equals(ApoMarioOptions.FUNCTION_OPTIONS_LEFT_DIFFICULTY)) {
			if (difficulty - 1 >= ApoMarioConstants.MIN_DIFFICULTY) {
				this.getGame().getLevel().setDifficulty(difficulty - 1);
				this.setOptionsDrag();
			}
		} else if (function.equals(ApoMarioOptions.FUNCTION_OPTIONS_RIGHT_DIFFICULTY)) {
			if (difficulty + 1 < ApoMarioConstants.MAX_DIFFICULTY) {
				this.getGame().getLevel().setDifficulty(difficulty + 1);
				this.setOptionsDrag();
			}
		} else if (function.equals(ApoMarioOptions.FUNCTION_OPTIONS_LEFT_LEVELWIDTH)) {
			if (width - 1 >= ApoMarioConstants.MIN_WIDTH) {
				this.getGame().getLevel().setWidth(width - 1);
				this.setOptionsDrag();
			}
		} else if (function.equals(ApoMarioOptions.FUNCTION_OPTIONS_RIGHT_LEVELWIDTH)) {
			if (width + 1 < ApoMarioConstants.MAX_WIDTH) {
				this.getGame().getLevel().setWidth(width + 1);
				this.setOptionsDrag();
			}
		}
	}
	
	public void mouseButtonReleased(int x, int y) {
		if (this.dragDifficulty != null) {
			if (this.dragDifficulty.mouseReleased(x, y)) {
				int difficulty = (int)(this.dragDifficulty.getPercent() / 100f * (ApoMarioConstants.MAX_DIFFICULTY - ApoMarioConstants.MIN_DIFFICULTY)) + ApoMarioConstants.MIN_DIFFICULTY;
				this.getGame().getLevel().setDifficulty(difficulty);
				this.newLevel(0);
			}
		}
		if (this.dragWidth != null) {
			if (this.dragWidth.mouseReleased(x, y)) {
				int width = (int)(this.dragWidth.getPercent() * (ApoMarioConstants.MAX_WIDTH - ApoMarioConstants.MIN_WIDTH) / 100f) + ApoMarioConstants.MIN_WIDTH;
				this.getGame().getLevel().setWidth(width);
				this.newLevel(0);
			}
		}
		if (this.myButtonDebug != null) {
			if (this.myButtonDebug.mouseReleased(x, y)) {
				this.changeDebug();
			}
		}
		if (this.myButton != null) {
			if (this.myButton.mouseReleased(x, y)) {
				if (this.myButton.isBActive()) {
					this.myTextfield.setBEnabled(true);
				} else {
					this.myTextfield.setBEnabled(false);					
				}
			}
		}
		if (this.myTextfield != null) {
			this.myTextfield.mouseReleased(x, y);
			if (this.myTextfield.intersects(x, y)) {
				this.myTextfield.setBSelect(true);
			} else {
				this.myTextfield.setBSelect(false);
			}
		}
	}
	
	private void changeDebug() {
		if (this.myButtonDebug != null) {
			if (this.myButtonDebug.isBActive()) {
				this.getGame().setDebugMode(true);
			} else {
				this.getGame().setDebugMode(false);					
			}
		}
	}
	
	public void setDebugChange(boolean bChange) {
		if (bChange) {
			this.myButtonDebug.setBActive(true);
		} else {
			this.myButtonDebug.setBActive(false);
		}
	}
	
	public void newLevel(long random) {
		if (this.myButton.isBActive()) {
			this.makeNewLevel(random);
		} else {
			if (random == 0) {
				random = this.getGame().getLevel().getLevelInt();
			}
			this.makeNewLevel(random);
		}
		if (this.myTextfield != null) {
			this.myTextfield.removeCurStringAndSetCurString(String.valueOf(this.getGame().getLevel().getLevelInt()));
		}
	}
	
	public boolean mouseMoved(int x, int y) {
		if (this.dragDifficulty != null) {
			if (this.dragDifficulty.mouseMoved(x, y)) {
				return true;
			}
		}
		if (this.dragWidth != null) {
			if (this.dragWidth.mouseMoved(x, y)) {
				return true;
			}
		}
		if (this.myButton != null) {
			if (this.myButton.getMove(x, y)) {
				return true;
			}
		}
		if (this.myButtonDebug != null) {
			if (this.myButtonDebug.getMove(x, y)) {
				return true;
			}
		}
		if (this.myTextfield != null) {
			if (this.myTextfield.getMove(x, y)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean mousePressed(int x, int y, boolean bRight) {
		if (this.myButton != null) {
			if (this.myButton.getPressed(x, y)) {
				return true;
			}
		}
		if (this.myButtonDebug != null) {
			if (this.myButtonDebug.getPressed(x, y)) {
				return true;
			}
		}
		if (this.myTextfield != null) {
			if (this.myTextfield.mousePressed(x, y)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean mouseDragged(int x, int y) {
		if (this.mouseMoved(x, y)) {
			this.mouseButtonReleased(x, y);
			return true;
		}
		if (this.myTextfield != null) {
			if (this.myTextfield.mouseDragged(x, y)) {
				return true;
			}
		}
		return false;
	}
	
	public void keyButtonReleased(int button, char character) {
		boolean bOther = true;
		if (this.myTextfield != null) {
			if (this.myTextfield.isBSelect()) {
				String oldString = this.myTextfield.getCurString();
				boolean bShift = false;
				if (this.getGame().getDown()[ApoMarioComponent.CTRL_DOWN]) {
					if (button == KeyEvent.VK_V) {
						String s = ApoHelp.getClipboardContents();
						if (s != null) {
							for (int i = 0; i < s.length(); i++) {
								char chara = s.charAt(i);
								this.myTextfield.addCharacter(button, chara);
							}
							bShift = true;
						}
					} else if (button == KeyEvent.VK_C) {
						String s = this.myTextfield.getSelectedString();
						if (s != null) {
							ApoHelp.setClipboardContents(s);
						}
						bShift = true;
					} else if (button == KeyEvent.VK_X) {
						String s = this.myTextfield.getSelectedString();
						if (s != null) {
							ApoHelp.setClipboardContents(s);
							this.myTextfield.deleteSelectedString();
						}
						bShift = true;
					}
				} else if (this.getGame().getDown()[ApoMarioComponent.SHIFT_DOWN]) {
					if (button == KeyEvent.VK_LEFT) {
						this.myTextfield.nextSelectedPosition(this.myTextfield.getPosition() - 1);
						bShift = true;
					} else if (button == KeyEvent.VK_RIGHT) {
						this.myTextfield.nextSelectedPosition(this.myTextfield.getPosition() + 1);
						bShift = true;
					}
				}
				bOther = false;
				if (!bShift) {
					this.myTextfield.addCharacter(button, character);
				}
				if (!oldString.equals(this.myTextfield.getCurString())) {
					try {
						long random = Long.valueOf(this.myTextfield.getCurString());
						this.newLevel(random);
					} catch (NumberFormatException ex) {
					}					
				}
			}
		}
		if (button == KeyEvent.VK_ESCAPE) {
			this.getGame().setMenu();
			return;
		} else if (bOther) {
			if (button == KeyEvent.VK_T) {
				if (ApoMarioConstants.TILE_SET == ApoMarioConstants.TILE_SET_EAT) {
					ApoMarioConstants.TILE_SET = ApoMarioConstants.TILE_SET_MARIO;
				} else if (ApoMarioConstants.TILE_SET == ApoMarioConstants.TILE_SET_MARIO) {
					ApoMarioConstants.TILE_SET = ApoMarioConstants.TILE_SET_EAT;
				}
				ApoMarioImageContainer.setTileSet(ApoMarioConstants.TILE_SET);
				this.getGame().setOptions();
			} else {
				super.keyButtonReleasedArrowAndEnter(button, character);
			}
		}
	}
	
	public void think(int delta) {
		if (this.myTextfield != null) {
			this.myTextfield.think(delta);
		}
		for (int i = 0; i < this.getGame().getButtons().length; i++) {
			if (this.getGame().getButtons()[i].isBWait()) {
				int wait = this.getGame().getButtons()[i].getWait();
				this.getGame().getButtons()[i].think(delta);
				if (this.getGame().getButtons()[i].getWait() < wait) {
					this.think(delta, this.getGame().getButtons()[i].getFunction());
				}
			}
		}
		super.thinkRunnerAndAnimation(delta);
	}
	
	public void render(Graphics2D g) {
		super.renderBackgroundAndAnimation(g);
		
		g.setFont(ApoMarioConstants.FONT_CREDITS);
		g.setColor(Color.BLACK);
		
		int size = ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE;
		String s = "Options";
		g.drawString(s, ApoMarioConstants.GAME_WIDTH/2 - g.getFontMetrics().stringWidth(s)/2, size * 2);

		g.setFont(ApoMarioConstants.FONT_STATISTICS);
		// Difficulty
		s = "Difficulty: "+this.getGame().getLevel().getDifficulty();
		int w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoMarioConstants.GAME_WIDTH/2 - w/2, ApoMarioOptions.Y_DIFFICULTY + g.getFontMetrics().getHeight()/2);
		// Level width
		s = "Levelwidth: "+this.getGame().getLevel().getWidth();
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoMarioConstants.GAME_WIDTH/2 - w/2, ApoMarioOptions.Y_WIDTH);
		// Dragobjekte
		if (this.dragDifficulty != null) {
			this.dragDifficulty.render(g, 0, 0);
		}
		if (this.dragWidth != null) {
			this.dragWidth.render(g, 0, 0);
		}
		// Häckchenbutton
		if (this.myButtonDebug != null) {
			s = "Debug: off";
			w = g.getFontMetrics().stringWidth(s);
			if (this.myButtonDebug.isBActive()) {
				s = "Debug: on";
			}
			g.setColor(new Color(60, 60, 60, 240));
			int x = (int)this.myButtonDebug.getX() - 3 * ApoMarioConstants.APP_SIZE;
			int y = (int)this.myButtonDebug.getY() - 3 * ApoMarioConstants.APP_SIZE;
			int width = (int)(w + 9 * ApoMarioConstants.APP_SIZE + this.myButtonDebug.getWidth());
			int height = (int)(this.myButtonDebug.getHeight() + 6 * ApoMarioConstants.APP_SIZE);
			int fontHeight = (int)(g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent());
			g.fillRoundRect(x, y, width, height, 10, 10);
			this.myButtonDebug.render(g, 0, 0);
			g.setColor(Color.WHITE);
			x = (int)(this.myButtonDebug.getX() + this.myButtonDebug.getWidth() + 3 * ApoMarioConstants.APP_SIZE);
			g.drawString(s, x, y + height/2 + fontHeight/2);
		}
		if (this.myButton != null) {
			if (!this.myButton.isBActive()) {
				g.setColor(new Color(60, 60, 60, 240));
			} else {
				g.setColor(new Color(120, 120, 120, 240));
			}
			int width = ApoMarioConstants.GAME_WIDTH/2 + 25 * ApoMarioConstants.APP_SIZE;
			g.fillRect((int)(this.myButton.getX() - 5 * ApoMarioConstants.APP_SIZE), (int)(this.myButton.getY() - 5 * ApoMarioConstants.APP_SIZE), width, (int)(this.myButton.getHeight() + 10 * ApoMarioConstants.APP_SIZE));
			this.myButton.render(g, 0, 0);
			if (this.myTextfield != null) {
				this.myTextfield.render(g, 0, 0);
			}
		}
		
		super.renderButtonsAndRunner(g);
	}
	
}
