package apoMario.game.panels;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import org.apogames.ApoConstants;
import org.apogames.entity.ApoAnimation;
import org.apogames.help.ApoHelp;

import apoMario.ApoMarioConstants;
import apoMario.ApoMarioImageContainer;
import apoMario.entity.ApoMarioPlayer;
import apoMario.game.ApoMarioPanel;
import apoMario.game.ApoMarioSearch;
import apoMario.game.ApoMarioSearchNode;
import apoMario.game.ApoMarioSearchRunner;

/**
 * Analysepanelklasse<br />
 * Klasse, die sich um die Auswertung des Spiels kümmert und diese auch anzeigt
 * @author Dirk Aporius
 *
 */
public class ApoMarioAnalysis extends ApoMarioModelMenu {

	public static final String FUNCTION_ANALYSIS_BACK = "backAnalysis";
	public static final String FUNCTION_ANALYSIS_RESTART = "restartAnalysis";
	public static final String FUNCTION_ANALYSIS_NEWLEVEL = "newLevelAnalysis";
	public static final String FUNCTION_ANALYSIS_REPLAYSAVE = "saveReplay";
	public static final String FUNCTION_ANALYSIS_REPLAYLOAD = "loadReplay";
	public static final String FUNCTION_ANALYSIS_REPLAYPLAY = "playReplay";
	
	private final int FADE_TIME = 750;
	
	private boolean bFade;
	
	private BufferedImage iLevel;
	private int fadeTime;
	
	public ApoMarioAnalysis(ApoMarioPanel game) {
		super(game);
	}
	
	@Override
	public void init() {
		super.init();
		
		this.bFade = true;
		if (this.getGame().isBSimulate()) {
			this.bFade = false;
		} else {
			this.iLevel = this.getGame().getBackgroundImage();
			this.fadeTime = 0;
		}
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
		int x = (int)(this.getGame().getButtons()[10].getX()) - size;
		int y = (int)(this.getGame().getButtons()[10].getY());
		g.drawImage(iMenuTile.getSubimage(1 * size, 2 * size, size, size), x, y, null);
		g.drawImage(iMenuTile.getSubimage(1 * size, 3 * size, size, size), x - 1 * size, y, null);
		g.drawImage(iMenuTile.getSubimage(6 * size, 2 * size, size, size), x - 2 * size, y, null);
		g.drawImage(iMenuTile.getSubimage(0 * size, 3 * size, size, size), x - 2 * size, y - 1 * size, null);
		g.drawImage(iMenuTile.getSubimage(15 * size, 2 * size, size, size), x - 2 * size, y - 2 * size, null);
		g.drawImage(iMenuTile.getSubimage(0 * size, 3 * size, size, size), x - 2 * size, y - 3 * size, null);
		g.drawImage(iMenuTile.getSubimage(12 * size, 2 * size, size, size), x - 2 * size, y - 4 * size, null);
		g.drawImage(iMenuTile.getSubimage(1 * size, 3 * size, size, size), x - 1 * size, y - 4 * size, null);
		g.drawImage(iMenuTile.getSubimage(1 * size, 3 * size, size, size), x - 1 * size, y - 2 * size, null);
		g.drawImage(iMenuTile.getSubimage(1 * size, 2 * size, size, size), x - 0 * size, y - 2 * size, null);
		g.drawImage(iMenuTile.getSubimage(1 * size, 2 * size, size, size), x - 0 * size, y - 4 * size, null);
		g.drawImage(iMenuTile.getSubimage(1 * size, 3 * size, size, size), x - 3 * size, y - 2 * size, null);
		g.drawImage(iMenuTile.getSubimage(1 * size, 3 * size, size, size), x - 4 * size, y - 2 * size, null);
		g.drawImage(iMenuTile.getSubimage(1 * size, 3 * size, size, size), x - 5 * size, y - 2 * size, null);
		g.drawImage(iMenuTile.getSubimage(1 * size, 3 * size, size, size), x - 6 * size, y - 2 * size, null);
		g.drawImage(iMenuTile.getSubimage(1 * size, 3 * size, size, size), x - 7 * size, y - 2 * size, null);
		g.drawImage(iMenuTile.getSubimage(15 * size, 2 * size, size, size), x - 8 * size, y - 2 * size, null);
		g.drawImage(iMenuTile.getSubimage(1 * size, 3 * size, size, size), x - 9 * size, y - 2 * size, null);
		g.drawImage(iMenuTile.getSubimage(1 * size, 3 * size, size, size), x - 9 * size, y - 4 * size, null);
		g.drawImage(iMenuTile.getSubimage(1 * size, 3 * size, size, size), x - 9 * size, y - 0 * size, null);
		g.drawImage(iMenuTile.getSubimage(0 * size, 3 * size, size, size), x - 8 * size, y - 1 * size, null);
		g.drawImage(iMenuTile.getSubimage(0 * size, 3 * size, size, size), x - 8 * size, y - 3 * size, null);
		g.drawImage(iMenuTile.getSubimage(4 * size, 2 * size, size, size), x - 10 * size, y - 0 * size, null);
		g.drawImage(iMenuTile.getSubimage(4 * size, 2 * size, size, size), x - 10 * size, y - 2 * size, null);
		g.drawImage(iMenuTile.getSubimage(4 * size, 2 * size, size, size), x - 10 * size, y - 4 * size, null);
		g.drawImage(iMenuTile.getSubimage(3 * size, 2 * size, size, size), x - 8 * size, y - 0 * size, null);
		g.drawImage(iMenuTile.getSubimage(9 * size, 2 * size, size, size), x - 8 * size, y - 4 * size, null);
		
		g.setColor(new Color(255, 255, 255, 200));
		g.fillRoundRect(2 * size, (int)(size), ApoMarioConstants.GAME_WIDTH - size * 4, (int)(ApoMarioConstants.GAME_HEIGHT - 6 * size), 10, 10);
		g.setColor(Color.BLACK);
		g.drawRoundRect(2 * size, (int)(size), ApoMarioConstants.GAME_WIDTH - size * 4, (int)(ApoMarioConstants.GAME_HEIGHT - 6 * size), 10, 10);
	}

	@Override
	public void makeBackgroundAnimation() {
		int size = ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE;
		BufferedImage iMenuTile = ApoMarioImageContainer.MENU;
		
		//if (this.getBackgroundAnimation() == null) {
			this.setBackgroundAnimation(new ArrayList<ApoAnimation>());
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(6 * size, 0 * size, 4 * size, size), 0 * size, 4 * size, 4, 400));
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(6 * size, 1 * size, 4 * size, size), 0 * size, 11 * size, 4, 355));
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(2 * size, 0 * size, 4 * size, size), 19 * size, 1 * size, 4, 375));
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(2 * size, 1 * size, 4 * size, size), 19 * size, 8 * size, 4, 365));
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(6 * size, 0 * size, 4 * size, size), 18 * size, 14 * size, 4, 380));
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(6 * size, 0 * size, 4 * size, size), 11 * size, 0 * size, 4, 390));
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(10 * size, 1 * size, 1 * size, size), 5 * size, 13 * size, 1, 1000000));
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(12 * size, 0 * size, 1 * size, 2 * size), 1 * size, 7 * size, 1, 1000000));
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(13 * size, 1 * size, 1 * size, 1 * size), 10 * size, 11 * size, 1, 1000000));
		//}
	}

	@Override
	public void makeRunner() {
		int size = ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE;
		BufferedImage iMenuTile = ApoMarioImageContainer.MENU;
		int x = (int)(this.getGame().getButtons()[10].getX()) - size;
		int y = (int)(this.getGame().getButtons()[10].getY());
		
		if (this.getRunner() == null) {
			this.setRunner(new ApoMarioSearchRunner(iMenuTile.getSubimage(0 * size, 1 * size, size * 2, size), x - 2 * size, y - 2 * size));
			this.setCurNode(2);
		} else {
			this.getRunner().setIBackground(iMenuTile.getSubimage(0 * size, 1 * size, size * 2, size));
		}
	}

	@Override
	public void makeSearch() {
		int size = ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE;
		int x = (int)(this.getGame().getButtons()[10].getX()) - size;
		int y = (int)(this.getGame().getButtons()[10].getY());
		
		this.setSearch(new ApoMarioSearch());
		ApoMarioSearchNode node = new ApoMarioSearchNode(0, x - 0 * size, y - 0 * size);
		node.addConnectedNode(1);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(1, x - 2 * size, y - 0 * size);
		node.addConnectedNode(0);
		node.addConnectedNode(2);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(2, x - 2 * size, y - 2 * size);
		node.addConnectedNode(1);
		node.addConnectedNode(3);
		node.addConnectedNode(4);
		node.addConnectedNode(8);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(3, x - 0 * size, y - 2 * size);
		node.addConnectedNode(2);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(4, x - 2 * size, y - 4 * size);
		node.addConnectedNode(5);
		node.addConnectedNode(2);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(5, x - 0 * size, y - 4 * size);
		node.addConnectedNode(4);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(6, x - 8 * size, y - 4 * size);
		node.addConnectedNode(7);
		node.addConnectedNode(8);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(7, x - 10 * size, y - 4 * size);
		node.addConnectedNode(6);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(8, x - 8 * size, y - 2 * size);
		node.addConnectedNode(6);
		node.addConnectedNode(2);
		node.addConnectedNode(9);
		node.addConnectedNode(10);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(9, x - 10 * size, y - 2 * size);
		node.addConnectedNode(8);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(10, x - 8 * size, y - 0 * size);
		node.addConnectedNode(8);
		node.addConnectedNode(11);
		this.getSearch().addNode(node);

		node = new ApoMarioSearchNode(11, x - 10 * size, y - 0 * size);
		node.addConnectedNode(10);
		this.getSearch().addNode(node);
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (bFade) {
			return;
		}
		if (button == KeyEvent.VK_ESCAPE) {
			this.getGame().setMenu();
		} else if ((button == KeyEvent.VK_R)) {
			this.getGame().setGame(false);
		} else if (button == KeyEvent.VK_N) {
			this.getGame().newLevel();
			this.getGame().setGame(false);
		} else if (button == KeyEvent.VK_M) {
			this.getGame().newLevel(0, 0);
			this.getGame().setGame(false);
		} else if (button == KeyEvent.VK_T) {
			if (ApoMarioConstants.TILE_SET == ApoMarioConstants.TILE_SET_EAT) {
				ApoMarioConstants.TILE_SET = ApoMarioConstants.TILE_SET_MARIO;
			} else if (ApoMarioConstants.TILE_SET == ApoMarioConstants.TILE_SET_MARIO) {
				ApoMarioConstants.TILE_SET = ApoMarioConstants.TILE_SET_EAT;
			}
			ApoMarioImageContainer.setTileSet(ApoMarioConstants.TILE_SET);
			this.getGame().setAnalysis();
			this.bFade = false;
		} else {
			super.keyButtonReleasedArrowAndEnter(button, character);
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (bFade) {
			return;
		}
		this.setExcecuteFunction(function);
		int oldEndNode = this.getEndNode();
		this.setEndNode(-1);
		if (function == null) {
		} else if (function.equals(ApoMarioAnalysis.FUNCTION_ANALYSIS_BACK)) {
			this.setEndNode(0);
		} else if (function.equals(ApoMarioAnalysis.FUNCTION_ANALYSIS_RESTART)) {
			this.setEndNode(3);
		} else if (function.equals(ApoMarioAnalysis.FUNCTION_ANALYSIS_NEWLEVEL)) {
			this.setEndNode(5);
		} else if (function.equals(ApoMarioAnalysis.FUNCTION_ANALYSIS_REPLAYSAVE)) {
			this.setEndNode(11);
		} else if (function.equals(ApoMarioAnalysis.FUNCTION_ANALYSIS_REPLAYLOAD)) {
			this.setEndNode(9);
		} else if (function.equals(ApoMarioAnalysis.FUNCTION_ANALYSIS_REPLAYPLAY)) {
			this.setEndNode(7);
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
		} else if (this.getCurNode() == 3) {
			this.getGame().setGame(false);
		} else if (this.getCurNode() == 5) {
			this.playNewLevel();
		} else if (this.getCurNode() == 11) {
			if (!ApoConstants.B_APPLET) {
				this.saveReplay();
			}
		} else if (this.getCurNode() == 9) {
			if (!ApoConstants.B_APPLET) {
				this.getGame().loadReplay();
			}
		} else if (this.getCurNode() == 7) {
			this.getGame().playReplay(true);
		}
	}
	
	@Override
	public void excecuteFunction() {
		String function = this.getExcecuteFunction();
		if (function == null) {
		} else if (function.equals(ApoMarioAnalysis.FUNCTION_ANALYSIS_BACK)) {
			this.getGame().setMenu();
		} else if (function.equals(ApoMarioAnalysis.FUNCTION_ANALYSIS_RESTART)) {
			this.getGame().setGame(false);
		} else if (function.equals(ApoMarioAnalysis.FUNCTION_ANALYSIS_NEWLEVEL)) {
			this.playNewLevel();
		} else if (function.equals(ApoMarioAnalysis.FUNCTION_ANALYSIS_REPLAYSAVE)) {
			this.saveReplay();
		} else if (function.equals(ApoMarioAnalysis.FUNCTION_ANALYSIS_REPLAYLOAD)) {
			this.getGame().loadReplay();
		} else if (function.equals(ApoMarioAnalysis.FUNCTION_ANALYSIS_REPLAYPLAY)) {
			this.getGame().playReplay(true);
		}
	}
	
	private void playNewLevel() {
		this.getGame().newLevel();
		this.getGame().setGame(false);
	}
	
	private void saveReplay() {
		if (this.getGame().getFileChooserReplay().showSaveDialog(this.getGame()) == JFileChooser.APPROVE_OPTION) {
			String path = this.getGame().getFileChooserReplay().getSelectedFile().getPath();
			if (path.indexOf(".rep") == -1) {
				path = path + ".rep";
			}
			this.getGame().getLevel().getReplay().save(path);
		}
	}

	@Override
	public void mouseButtonReleased(int x, int y) {
	}
	
	@Override
	public boolean mouseDragged(int x, int y) {
		return false;
	}

	@Override
	public boolean mouseMoved(int x, int y) {
		return false;
	}

	@Override
	public boolean mousePressed(int x, int y, boolean bRight) {
		return false;
	}

	@Override
	public void think(int delta) {
		if ((this.bFade) && (this.fadeTime < this.FADE_TIME)) {
			this.fadeTime += delta;
			if (this.fadeTime >= this.FADE_TIME) {
				this.bFade = false;
			}
		}
		super.thinkRunnerAndAnimation(delta);
	}

	@Override
	public void render(Graphics2D g) {		
		super.renderBackgroundAndAnimation(g);
		
		g.setFont(ApoMarioConstants.FONT_CREDITS);
		g.setColor(Color.BLACK);
		
		int size = ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE;
		String s = "Analysis";
		g.drawString(s, ApoMarioConstants.GAME_WIDTH/2 - g.getFontMetrics().stringWidth(s)/2, size * 2);
		
		g.setFont(ApoMarioConstants.FONT_STATISTICS);
		if (this.getGame().isBWin()) {
			s = "Congratulation";
			int w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, ApoMarioConstants.GAME_WIDTH/2 - w/2, size * 4);
			
			if (this.isOnePlayer()) {
				ApoMarioPlayer player = this.getGame().getLevel().getPlayers().get(0);
				this.renderWinPlayer(g, this.getGame().getLevel().getPlayers().get(0));
				
				s = "Points: "+String.valueOf(player.getPoints());
				w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, ApoMarioConstants.GAME_WIDTH*1/2 - w/2, size * 8);

				s = "Coins: "+String.valueOf(player.getCoins());
				w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, ApoMarioConstants.GAME_WIDTH*1/2 - w/2, size * 9);
			} else {
				if ((this.getGame().getLevel().getPlayers().get(0).getX() == this.getGame().getLevel().getPlayers().get(1).getX())) {
					if (this.getGame().getLevel().getPlayers().get(0).getPoints() == this.getGame().getLevel().getPlayers().get(1).getPoints()) {
						if (this.getGame().getLevel().getPlayers().get(0).getCoins() > this.getGame().getLevel().getPlayers().get(1).getCoins()) {
							this.renderWinPlayer(g, this.getGame().getLevel().getPlayers().get(0));
						} else {
							this.renderWinPlayer(g, this.getGame().getLevel().getPlayers().get(1));
						}
					} else if (this.getGame().getLevel().getPlayers().get(0).getPoints() > this.getGame().getLevel().getPlayers().get(1).getPoints()) {
						this.renderWinPlayer(g, this.getGame().getLevel().getPlayers().get(0));
					} else {
						this.renderWinPlayer(g, this.getGame().getLevel().getPlayers().get(1));
					}
				} else if ((this.getGame().getLevel().getPlayers().get(0).getX() > this.getGame().getLevel().getPlayers().get(1).getX())) {
					this.renderWinPlayer(g, this.getGame().getLevel().getPlayers().get(0));
				} else {
					this.renderWinPlayer(g, this.getGame().getLevel().getPlayers().get(1));
				}
				this.renderPlayerStats(g, this.getGame().getLevel().getPlayers().get(0), this.getGame().getLevel().getPlayers().get(1), this.getName(this.getGame().getLevel().getPlayers().get(0)), this.getName(this.getGame().getLevel().getPlayers().get(1)));
			}
		} else {
			if (isOnePlayer())  {
				s = "Game Over";
				int w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, ApoMarioConstants.GAME_WIDTH/2 - w/2, size * 4);
				ApoMarioPlayer player = this.getGame().getLevel().getPlayers().get(0);
				
				String name = "human";
				if (player.getAi() == null) {
					s = "You died. Please try the level again!";
				} else {
					name = player.getAi().getTeamName()+" ("+(player.getPlayer()+1)+")";
					s = name+" died. Please try the level again!";
				}
				w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, ApoMarioConstants.GAME_WIDTH/2 - w/2, size * 6);
				
				s = "Points: "+String.valueOf(player.getPoints());
				w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, ApoMarioConstants.GAME_WIDTH*1/2 - w/2, size * 8);

				s = "Coins: "+String.valueOf(player.getCoins());
				w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, ApoMarioConstants.GAME_WIDTH*1/2 - w/2, size * 9);

			} else {
				s = "Congratulation ";
				String nextS = "You won against ";
				String winner = "";
				String looser = "";
				ApoMarioPlayer player = this.getGame().getLevel().getPlayers().get(0);
				ApoMarioPlayer playerTwo = this.getGame().getLevel().getPlayers().get(1);
				if (player.isBVisible()) {
					winner = this.getName(player);
					s += winner + "!";
					looser = this.getName(playerTwo);
					nextS += looser + "!";
					this.renderPlayerStats(g, player, playerTwo, winner, looser);
				} else {
					winner = this.getName(playerTwo);
					s += winner + "!";
					looser = this.getName(player);
					nextS += looser + "!";
					this.renderPlayerStats(g, player, playerTwo, looser, winner);
				}
				int w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, ApoMarioConstants.GAME_WIDTH/2 - w/2, size * 4);
				
				w = g.getFontMetrics().stringWidth(nextS);
				g.drawString(nextS, ApoMarioConstants.GAME_WIDTH/2 - w/2, size * 5);
			}
		}
		
		super.renderButtonsAndRunner(g);
		
		if (this.bFade) {
			if (this.iLevel != null) {
				float percent = 1 - (float)this.fadeTime / (float)(this.FADE_TIME);
				Composite composite = g.getComposite();
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, percent));
				g.drawImage(this.iLevel, 0, 0, null);
				g.setComposite(composite);
			}
		}
	}
	
	private boolean isOnePlayer() {
		if ((!this.getGame().getLevel().isBReplay()) && (this.getGame().getLevel().getPlayers().get(1).getAi() == null))  {
			return true;
		}
		if  ((this.getGame().getLevel().isBReplay()) && (Math.abs(this.getGame().getLevel().getReplay().getPlayerTwo().getMaxSteps() - this.getGame().getLevel().getReplay().getPlayerOne().getMaxSteps()) >= 2)) {			
			return true;
		}
		return false;
	}
	
	private String getName(ApoMarioPlayer player) {
		if (this.getGame().getLevel().isBReplay()) {
			if (player.getPlayer() == ApoMarioConstants.PLAYER_ONE) {
				return this.getGame().getLevel().getReplay().getPlayerOne().getName();
			} else {
				return this.getGame().getLevel().getReplay().getPlayerTwo().getName();
			}
		} else {
			if (player.getAi() == null) {
				return "human";
			} else {
				return player.getAi().getTeamName()+" ("+(player.getPlayer()+1)+")";
			}
		}
	}
	
	private void renderPlayerStats(Graphics2D g, ApoMarioPlayer playerOne, ApoMarioPlayer playerTwo, String one, String two) {
		String s = one;
		int w = g.getFontMetrics().stringWidth(s);
		int size = ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE;
		g.drawString(s, ApoMarioConstants.GAME_WIDTH*1/4 - w/2, size * 7);
		
		s = two;
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoMarioConstants.GAME_WIDTH*3/4 - w/2, size * 7);
		
		s = "Points: "+String.valueOf(playerOne.getPoints());
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoMarioConstants.GAME_WIDTH*1/4 - w/2, size * 8);

		s = "Coins: "+String.valueOf(playerOne.getCoins());
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoMarioConstants.GAME_WIDTH*1/4 - w/2, size * 9);
		
		s = "Points: "+String.valueOf(playerTwo.getPoints());
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoMarioConstants.GAME_WIDTH*3/4 - w/2, size * 8);
		
		s = "Coins: "+String.valueOf(playerTwo.getCoins());
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoMarioConstants.GAME_WIDTH*3/4 - w/2, size * 9);
	}
	
	private void renderWinPlayer(Graphics2D g, ApoMarioPlayer player) {
		String s;
		int size = ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE;
		int w;
		if (player.getAi() == null) {
			s = "You solved the level in "+ApoHelp.getTimeToDraw(this.getGame().getLevel().getPassedTime())+" minutes";
		} else {
			s = player.getAi().getTeamName() + " ("+(player.getPlayer()+1)+") solved the level in "+ApoHelp.getTimeToDraw(this.getGame().getLevel().getPassedTime())+" minutes";					
		}
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoMarioConstants.GAME_WIDTH/2 - w/2, size * 5);
	}

}
