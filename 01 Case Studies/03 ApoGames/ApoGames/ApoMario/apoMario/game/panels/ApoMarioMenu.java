package apoMario.game.panels;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.apogames.ApoConstants;
import org.apogames.entity.ApoAnimation;

import apoMario.ApoMarioConstants;
import apoMario.ApoMarioImageContainer;
import apoMario.entity.ApoMarioPlayer;
import apoMario.game.ApoMarioPanel;
import apoMario.game.ApoMarioSearch;
import apoMario.game.ApoMarioSearchNode;
import apoMario.game.ApoMarioSearchRunner;


/**
 * Menupanelklasse<br />
 * Klasse, die das eigentliche Startmenu anzeigt und sich um die Auswertung der Events dadrin kümmert<br />
 * @author Dirk Aporius
 */
public class ApoMarioMenu extends ApoMarioModelMenu {

	public static final String FUNCTION_QUIT = "quit";
	public static final String FUNCTION_START = "start";
	public static final String FUNCTION_CREDITS = "credits";
	public static final String FUNCTION_LOAD_PLAYER_ONE = "loadPlayerOne";
	public static final String FUNCTION_LOAD_PLAYER_TWO = "loadPlayerTwo";
	public static final String FUNCTION_OPTIONS = "options";
	public static final String FUNCTION_PLAYER_ONE_LEFT = "playerOneLeft";
	public static final String FUNCTION_PLAYER_ONE_RIGHT = "playerOneRight";
	public static final String FUNCTION_PLAYER_TWO_LEFT = "playerTwoLeft";
	public static final String FUNCTION_PLAYER_TWO_RIGHT = "playerTwoRight";
	public static final String FUNCTION_SIMULATE = "simulate";
	public static final String FUNCTION_REPLAYLOAD = "loadReplay";
	public static final String fUNCTION_EDITOR = "editor";
	public static final String FUNCTION_EDITORLOAD = "editorLoad";
	
	public ApoMarioMenu(ApoMarioPanel game) {
		super(game);
	}
	
	@Override
	public void init() {
		super.init();
	}
	
	@Override
	public void makeBackground() {
		//if (this.getIBackground() == null) {
			this.setIBackground(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(ApoMarioConstants.GAME_WIDTH, ApoMarioConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB));
			Graphics2D g = (Graphics2D)(this.getIBackground().getGraphics());
			
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			BufferedImage iMenuTile = ApoMarioImageContainer.MENU;
			int size = ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE;
			int waterHeight = 4;
			for (int x = 0; x < 20; x++) {
				for (int y = 0; y < waterHeight; y++) {
					g.drawImage(iMenuTile.getSubimage(14 * size, 0 * size, size, size), x * size, y * size, null);
				}
				for (int y = waterHeight; y < 15; y++) {
					g.drawImage(iMenuTile.getSubimage(0 * size, 0 * size, size, size), x * size, y * size, null);
				}

				g.drawImage(iMenuTile.getSubimage(11 * size, 4 * size, size/2, size/2), x * size, waterHeight * size, null);
				g.drawImage(iMenuTile.getSubimage(11 * size, 4 * size, size/2, size/2), x * size + size/2, waterHeight * size, null);
			}
			//buttons 0, 1, 2, 3, 4, 19, 20
			int x = (int)(this.getGame().getButtons()[0].getX()) - size;
			int y = (int)(this.getGame().getButtons()[0].getY());
			g.drawImage(iMenuTile.getSubimage(1 * size, 2 * size, size, size), x, y, null);
			g.drawImage(iMenuTile.getSubimage(5 * size, 2 * size, size, size), x - 1 * size, y, null);
			g.drawImage(iMenuTile.getSubimage(5 * size, 2 * size, size, size), x - 2 * size, y, null);
			g.drawImage(iMenuTile.getSubimage(7 * size, 2 * size, size, size), x - 3 * size, y, null);
			g.drawImage(iMenuTile.getSubimage(0 * size, 3 * size, size, size), x - 3 * size, y - 1 * size, null);
			g.drawImage(iMenuTile.getSubimage(0 * size, 3 * size, size, size), x - 3 * size, y - 2 * size, null);
			g.drawImage(iMenuTile.getSubimage(14 * size, 2 * size, size, size), x - 3 * size, y - 3 * size, null);
			g.drawImage(iMenuTile.getSubimage(5 * size, 2 * size, size, size), x - 2 * size, y - 3 * size, null);
			g.drawImage(iMenuTile.getSubimage(1 * size, 2 * size, size, size), x - 1 * size, y - 3 * size, null);
			g.drawImage(iMenuTile.getSubimage(9 * size, 2 * size, size, size), x - 3 * size, y - 4 * size, null);
			g.drawImage(iMenuTile.getSubimage(5 * size, 2 * size, size, size), x - 4 * size, y - 4 * size, null);
			g.drawImage(iMenuTile.getSubimage(6 * size, 2 * size, size, size), x - 5 * size, y - 4 * size, null);
			g.drawImage(iMenuTile.getSubimage(11 * size, 2 * size, size, size), x - 5 * size, y - 5 * size, null);
			g.drawImage(iMenuTile.getSubimage(12 * size, 2 * size, size, size), x - 5 * size, y - 6 * size, null);
			g.drawImage(iMenuTile.getSubimage(1 * size, 3 * size, size, size), x - 4 * size, y - 6 * size, null);
			g.drawImage(iMenuTile.getSubimage(1 * size, 3 * size, size, size), x - 3 * size, y - 6 * size, null);
			g.drawImage(iMenuTile.getSubimage(3 * size, 2 * size, size, size), x - 2 * size, y - 6 * size, null);
			g.drawImage(iMenuTile.getSubimage(0 * size, 3 * size, size, size), x - 2 * size, y - 7 * size, null);
			g.drawImage(iMenuTile.getSubimage(8 * size, 2 * size, size, size), x - 2 * size, y - 8 * size, null);
			g.drawImage(iMenuTile.getSubimage(1 * size, 3 * size, size, size), x - 6 * size, y - 5 * size, null);
			g.drawImage(iMenuTile.getSubimage(15 * size, 2 * size, size, size), x - 7 * size, y - 5 * size, null);
			g.drawImage(iMenuTile.getSubimage(1 * size, 3 * size, size, size), x - 8 * size, y - 5 * size, null);
			g.drawImage(iMenuTile.getSubimage(8 * size, 2 * size, size, size), x - 7 * size, y - 6 * size, null);
			g.drawImage(iMenuTile.getSubimage(1 * size, 3 * size, size, size), x - 9 * size, y - 5 * size, null);
			g.drawImage(iMenuTile.getSubimage(1 * size, 3 * size, size, size), x - 10 * size, y - 5 * size, null);
			g.drawImage(iMenuTile.getSubimage(1 * size, 3 * size, size, size), x - 11 * size, y - 5 * size, null);
			g.drawImage(iMenuTile.getSubimage(14 * size, 2 * size, size, size), x - 12 * size, y - 5 * size, null);
			g.drawImage(iMenuTile.getSubimage(0 * size, 3 * size, size, size), x - 12 * size, y - 6 * size, null);
			g.drawImage(iMenuTile.getSubimage(0 * size, 3 * size, size, size), x - 12 * size, y - 7 * size, null);
			g.drawImage(iMenuTile.getSubimage(8 * size, 2 * size, size, size), x - 12 * size, y - 8 * size, null);
			g.drawImage(iMenuTile.getSubimage(11 * size, 2 * size, size, size), x - 12 * size, y - 4 * size, null);
			g.drawImage(iMenuTile.getSubimage(4 * size, 2 * size, size, size), x - 13 * size, y - 4 * size, null);
			g.drawImage(iMenuTile.getSubimage(0 * size, 3 * size, size, size), x - 12 * size, y - 3 * size, null);
			g.drawImage(iMenuTile.getSubimage(11 * size, 2 * size, size, size), x - 12 * size, y - 2 * size, null);
			g.drawImage(iMenuTile.getSubimage(1 * size, 3 * size, size, size), x - 13 * size, y - 2 * size, null);
			g.drawImage(iMenuTile.getSubimage(4 * size, 2 * size, size, size), x - 14 * size, y - 2 * size, null);
			g.drawImage(iMenuTile.getSubimage(6 * size, 2 * size, size, size), x - 12 * size, y - 1 * size, null);
			g.drawImage(iMenuTile.getSubimage(1 * size, 3 * size, size, size), x - 11 * size, y - 1 * size, null);
			g.drawImage(iMenuTile.getSubimage(13 * size, 2 * size, size, size), x - 11 * size, y - 1 * size, null);
			g.drawImage(iMenuTile.getSubimage(2 * size, 2 * size, size, size), x - 11 * size, y - 0 * size, null);
			g.drawImage(iMenuTile.getSubimage(1 * size, 3 * size, size, size), x - 10 * size, y - 1 * size, null);
			g.drawImage(iMenuTile.getSubimage(1 * size, 3 * size, size, size), x - 9 * size, y - 1 * size, null);
			g.drawImage(iMenuTile.getSubimage(1 * size, 3 * size, size, size), x - 8 * size, y - 1 * size, null);
			g.drawImage(iMenuTile.getSubimage(11 * size, 2 * size, size, size), x - 7 * size, y - 1 * size, null);
			g.drawImage(iMenuTile.getSubimage(6 * size, 2 * size, size, size), x - 7 * size, y - 0 * size, null);
			g.drawImage(iMenuTile.getSubimage(1 * size, 3 * size, size, size), x - 6 * size, y - 0 * size, null);
			g.drawImage(iMenuTile.getSubimage(1 * size, 3 * size, size, size), x - 5 * size, y - 0 * size, null);
			g.drawImage(iMenuTile.getSubimage(1 * size, 3 * size, size, size), x - 4 * size, y - 0 * size, null);
			g.drawImage(iMenuTile.getSubimage(15 * size, 2 * size, size, size), x - 7 * size, y - 3 * size, null);
			g.drawImage(iMenuTile.getSubimage(1 * size, 2 * size, size, size), x - 6 * size, y - 3 * size, null);
			g.drawImage(iMenuTile.getSubimage(4 * size, 2 * size, size, size), x - 8 * size, y - 3 * size, null);
			g.drawImage(iMenuTile.getSubimage(0 * size, 3 * size, size, size), x - 7 * size, y - 4 * size, null);
			g.drawImage(iMenuTile.getSubimage(0 * size, 3 * size, size, size), x - 7 * size, y - 2 * size, null);
			
			g.setColor(new Color(255, 255, 255, 200));
			g.fillRoundRect(ApoMarioConstants.GAME_WIDTH/8 - size, (int)(ApoMarioConstants.GAME_HEIGHT*1/4 - 3 * size), ApoMarioConstants.GAME_WIDTH * 1 / 4 + size * 2, (int)(size * 3.4), 10, 10);
			g.fillRoundRect(ApoMarioConstants.GAME_WIDTH*5/8 - size, (int)(ApoMarioConstants.GAME_HEIGHT*1/4 - 3 * size), ApoMarioConstants.GAME_WIDTH * 1 / 4 + size * 2, (int)(size * 3.4), 10, 10);
			g.setColor(Color.BLACK);
			g.drawRoundRect(ApoMarioConstants.GAME_WIDTH/8 - size, (int)(ApoMarioConstants.GAME_HEIGHT*1/4 - 3 * size), ApoMarioConstants.GAME_WIDTH * 1 / 4 + size * 2, (int)(size * 3.4), 10, 10);
			g.drawRoundRect(ApoMarioConstants.GAME_WIDTH*5/8 - size, (int)(ApoMarioConstants.GAME_HEIGHT*1/4 - 3 * size), ApoMarioConstants.GAME_WIDTH * 1 / 4 + size * 2, (int)(size * 3.4), 10, 10);
			
			g.dispose();
		//}
	}

	@Override
	public void makeBackgroundAnimation() {
		int size = ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE;
		BufferedImage iMenuTile = ApoMarioImageContainer.MENU;
		
		//if (this.getBackgroundAnimation() == null) {
			this.setBackgroundAnimation(new ArrayList<ApoAnimation>());
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(6 * size, 1 * size, 4 * size, size), 2 * size, 5 * size, 4, 380));
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(13 * size, 1 * size, 1 * size, 1 * size), 18 * size, 6 * size, 1, 1000000));
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(13 * size, 1 * size, 1 * size, 1 * size), 1 * size, 7 * size, 1, 1000000));
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(12 * size, 0 * size, 1 * size, 2 * size), 7 * size, 6 * size, 1, 1000000));
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(10 * size, 1 * size, 1 * size, 1 * size), 12 * size, 12 * size, 1, 1000000));
			this.getBackgroundAnimation().add(new ApoAnimation(iMenuTile.getSubimage(11 * size, 1 * size, 1 * size, 1 * size), 7 * size, 11 * size, 1, 1000000));
		//}
	}

	@Override
	public void makeRunner() {
		int size = ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE;
		BufferedImage iMenuTile = ApoMarioImageContainer.MENU;
		int x = (int)(this.getGame().getButtons()[0].getX()) - size;
		int y = (int)(this.getGame().getButtons()[0].getY());
		
		if (this.getRunner() == null) {
			this.setRunner(new ApoMarioSearchRunner(iMenuTile.getSubimage(0 * size, 1 * size, size * 2, size), x - 7 * size, y - 5 * size));
			this.setCurNode(1);
		} else {
			this.getRunner().setIBackground(iMenuTile.getSubimage(0 * size, 1 * size, size * 2, size));
		}
	}

	@Override
	public void makeSearch() {
		int size = ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE;
		int x = (int)(this.getGame().getButtons()[0].getX()) - size;
		int y = (int)(this.getGame().getButtons()[0].getY());
		
		this.setSearch(new ApoMarioSearch());
		ApoMarioSearchNode node = new ApoMarioSearchNode(0, x - 7 * size, y - 6 * size);
		node.addConnectedNode(1);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(1, x - 7 * size, y - 5 * size);
		node.addConnectedNode(0);
		node.addConnectedNode(2);
		node.addConnectedNode(21);
		node.addConnectedNode(23);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(2, x - 5 * size, y - 5 * size);
		node.addConnectedNode(1);
		node.addConnectedNode(3);
		node.addConnectedNode(6);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(3, x - 5 * size, y - 6 * size);
		node.addConnectedNode(2);
		node.addConnectedNode(4);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(4, x - 2 * size, y - 6 * size);
		node.addConnectedNode(3);
		node.addConnectedNode(5);
		this.getSearch().addNode(node);

		node = new ApoMarioSearchNode(5, x - 2 * size, y - 8 * size);
		node.addConnectedNode(4);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(6, x - 5 * size, y - 4 * size);
		node.addConnectedNode(2);
		node.addConnectedNode(7);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(7, x - 3 * size, y - 4 * size);
		node.addConnectedNode(6);
		node.addConnectedNode(8);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(8, x - 3 * size, y - 3 * size);
		node.addConnectedNode(7);
		node.addConnectedNode(9);
		node.addConnectedNode(10);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(9, x - 1 * size, y - 3 * size);
		node.addConnectedNode(8);
		this.getSearch().addNode(node);

		node = new ApoMarioSearchNode(10, x - 3 * size, y - 0 * size);
		node.addConnectedNode(8);
		node.addConnectedNode(11);
		node.addConnectedNode(12);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(11, x - 0 * size, y - 0 * size);
		node.addConnectedNode(10);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(12, x - 7 * size, y - 0 * size);
		node.addConnectedNode(10);
		node.addConnectedNode(13);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(13, x - 7 * size, y - 1 * size);
		node.addConnectedNode(12);
		node.addConnectedNode(14);
		node.addConnectedNode(23);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(14, x - 11 * size, y - 1 * size);
		node.addConnectedNode(16);
		node.addConnectedNode(13);
		node.addConnectedNode(15);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(15, x - 11 * size, y - 0 * size);
		node.addConnectedNode(14);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(16, x - 12 * size, y - 1 * size);
		node.addConnectedNode(14);
		node.addConnectedNode(17);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(17, x - 12 * size, y - 2 * size);
		node.addConnectedNode(16);
		node.addConnectedNode(18);
		node.addConnectedNode(20);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(18, x - 14 * size, y - 2 * size);
		node.addConnectedNode(17);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(19, x - 13 * size, y - 4 * size);
		node.addConnectedNode(20);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(20, x - 12 * size, y - 4 * size);
		node.addConnectedNode(19);
		node.addConnectedNode(17);
		node.addConnectedNode(21);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(21, x - 12 * size, y - 5 * size);
		node.addConnectedNode(20);
		node.addConnectedNode(1);
		node.addConnectedNode(22);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(22, x - 12 * size, y - 8 * size);
		node.addConnectedNode(21);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(23, x - 7 * size, y - 3 * size);
		node.addConnectedNode(1);
		node.addConnectedNode(13);
		node.addConnectedNode(24);
		node.addConnectedNode(25);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(24, x - 6 * size, y - 3 * size);
		node.addConnectedNode(23);
		this.getSearch().addNode(node);
		
		node = new ApoMarioSearchNode(25, x - 8 * size, y - 3 * size);
		node.addConnectedNode(23);
		this.getSearch().addNode(node);
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			this.exit();
		} else if (button == KeyEvent.VK_O) {
			this.getGame().setOptions();
		} else if (button == KeyEvent.VK_E) {
			this.getGame().setEditor();
		} else if (button == KeyEvent.VK_C) {
			this.getGame().setCredits();
		} else if (button == KeyEvent.VK_S) {
			this.getGame().setSimulation();
		} else if (button == KeyEvent.VK_T) {
			if (ApoMarioConstants.TILE_SET == ApoMarioConstants.TILE_SET_EAT) {
				ApoMarioConstants.TILE_SET = ApoMarioConstants.TILE_SET_MARIO;
			} else if (ApoMarioConstants.TILE_SET == ApoMarioConstants.TILE_SET_MARIO) {
				ApoMarioConstants.TILE_SET = ApoMarioConstants.TILE_SET_EAT;
			}
			ApoMarioImageContainer.setTileSet(ApoMarioConstants.TILE_SET);
			this.getGame().restartLevel();
			this.getGame().setMenu();
		}
		this.keyButtonReleasedArrowAndEnter(button, character);
	}
	
	private void exit() {
		if (!ApoConstants.B_APPLET) {
			ApoMarioConstants.saveProperties();
			System.exit(0);
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		this.setExcecuteFunction(function);
		int oldEndNode = this.getEndNode();
		this.setEndNode(-1);
		if (function.equals(ApoMarioMenu.FUNCTION_QUIT)) {
			this.setEndNode(11);
		} else if (function.equals(ApoMarioMenu.FUNCTION_START)) {
			this.setEndNode(0);
		} else if (function.equals(ApoMarioMenu.FUNCTION_OPTIONS)) {
			this.setEndNode(19);
		} else if (function.equals(ApoMarioMenu.FUNCTION_LOAD_PLAYER_ONE)) {
			this.setEndNode(22);
		} else if (function.equals(ApoMarioMenu.FUNCTION_LOAD_PLAYER_TWO)) {
			this.setEndNode(5);
		} else if (function.equals(ApoMarioMenu.FUNCTION_PLAYER_ONE_LEFT)) {
			this.getGame().getLevel().changePlayerOneAI(-1);
		} else if (function.equals(ApoMarioMenu.FUNCTION_PLAYER_ONE_RIGHT)) {
			this.getGame().getLevel().changePlayerOneAI(+1);
		} else if (function.equals(ApoMarioMenu.FUNCTION_PLAYER_TWO_LEFT)) {
			this.getGame().getLevel().changePlayerTwoAI(-1);
		} else if (function.equals(ApoMarioMenu.FUNCTION_PLAYER_TWO_RIGHT)) {
			this.getGame().getLevel().changePlayerTwoAI(+1);
		} else if (function.equals(ApoMarioMenu.FUNCTION_SIMULATE)) {
			this.setEndNode(18);
		} else if (function.equals(ApoMarioMenu.FUNCTION_CREDITS)) {
			this.setEndNode(9);
		} else if (function.equals(ApoMarioMenu.FUNCTION_REPLAYLOAD)) {
			this.setEndNode(15);
		} else if (function.equals(ApoMarioMenu.fUNCTION_EDITOR)) {
			this.setEndNode(24);
		} else if (function.equals(ApoMarioMenu.FUNCTION_EDITORLOAD)) {
			this.setEndNode(25);
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
		if (this.getCurNode() == 11) {
			if (!ApoConstants.B_APPLET) {
				this.exit();
			}
		} else if (this.getCurNode() == 0) {
			this.getGame().setGame(false);
		} else if (this.getCurNode() == 19) {
			this.getGame().setOptions();
		} else if (this.getCurNode() == 22) {
			if (!ApoConstants.B_APPLET) {
				this.getGame().getLevel().loadPlayer(0, null);
			}
		} else if (this.getCurNode() == 5) {
			if (!ApoConstants.B_APPLET) {
				this.getGame().getLevel().loadPlayer(1, null);
			}
		}else if (this.getCurNode() == 9) {
			this.getGame().setCredits();
		} else if (this.getCurNode() == 18) {
			this.getGame().setSimulation();
		} else if (this.getCurNode() == 15) {
			if (!ApoConstants.B_APPLET) {
				this.getGame().loadReplay();
			}
		} else if (this.getCurNode() == 24) {
			this.getGame().setEditor();
		} else if (this.getCurNode() == 25) {
			if (!ApoConstants.B_APPLET) {
				this.getGame().loadEditorLevel();
			}
		}
	}
	
	@Override
	public void excecuteFunction() {
		String function = super.getExcecuteFunction();
		if (function == null) {
		} else if (function.equals(ApoMarioMenu.FUNCTION_QUIT)) {
			this.exit();
		} else if (function.equals(ApoMarioMenu.FUNCTION_START)) {
			this.getGame().setGame(false);
		} else if (function.equals(ApoMarioMenu.FUNCTION_OPTIONS)) {
			this.getGame().setOptions();
		} else if (function.equals(ApoMarioMenu.FUNCTION_LOAD_PLAYER_ONE)) {
			this.getGame().getLevel().loadPlayer(0, null);
		} else if (function.equals(ApoMarioMenu.FUNCTION_LOAD_PLAYER_TWO)) {
			this.getGame().getLevel().loadPlayer(1, null);
		} else if (function.equals(ApoMarioMenu.FUNCTION_PLAYER_ONE_LEFT)) {
			this.getGame().getLevel().changePlayerOneAI(-1);
		} else if (function.equals(ApoMarioMenu.FUNCTION_PLAYER_ONE_RIGHT)) {
			this.getGame().getLevel().changePlayerOneAI(+1);
		} else if (function.equals(ApoMarioMenu.FUNCTION_PLAYER_TWO_LEFT)) {
			this.getGame().getLevel().changePlayerTwoAI(-1);
		} else if (function.equals(ApoMarioMenu.FUNCTION_PLAYER_TWO_RIGHT)) {
			this.getGame().getLevel().changePlayerTwoAI(+1);
		} else if (function.equals(ApoMarioMenu.FUNCTION_CREDITS)) {
			this.getGame().setCredits();
		} else if (function.equals(ApoMarioMenu.FUNCTION_SIMULATE)) {
			this.getGame().setSimulation();
		} else if (function.equals(ApoMarioMenu.FUNCTION_REPLAYLOAD)) {
			if (!ApoConstants.B_APPLET) {
				this.getGame().loadReplay();
			}
		} else if (function.equals(ApoMarioMenu.FUNCTION_EDITORLOAD)) {
			if (!ApoConstants.B_APPLET) {
				if (this.getGame().loadEditorLevel()) {
					this.getGame().setGame(false);
				}
			}
		} else if (function.equals(ApoMarioMenu.fUNCTION_EDITOR)) {
			this.getGame().setEditor();
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
		super.thinkRunnerAndAnimation(delta);
	}

	@Override
	public void render(Graphics2D g) {
		super.renderBackgroundAndAnimation(g);
		
		g.setFont(ApoMarioConstants.FONT_MENU);
		g.setColor(Color.black);
		if ((this.getGame().getLevel().getPlayers() != null) && (this.getGame().getLevel().getPlayers().size() > 0)) {
			int y = (int)(this.getGame().getButtons()[14].getY() + this.getGame().getButtons()[14].getHeight()/2);
			int x = (int)(this.getGame().getButtons()[14].getX() - this.getGame().getButtons()[13].getX() - this.getGame().getButtons()[13].getWidth())/2 + (int)(this.getGame().getButtons()[13].getX()) + (int)(this.getGame().getButtons()[13].getWidth());
			this.renderPlayerStats(g, x, y, this.getGame().getLevel().getPlayers().get(0));
		}
		if ((this.getGame().getLevel().getPlayers() != null) && (this.getGame().getLevel().getPlayers().size() > 1)) {
			int y = (int)(this.getGame().getButtons()[16].getY() + this.getGame().getButtons()[16].getHeight()/2);
			int x = (int)(this.getGame().getButtons()[16].getX() - this.getGame().getButtons()[15].getX() - this.getGame().getButtons()[15].getWidth())/2 + (int)(this.getGame().getButtons()[15].getX()) + (int)(this.getGame().getButtons()[15].getWidth());
			if (this.getGame().getLevel().getPlayers().get(1).getAi() == null) {
				this.renderPlayerStats(g, x, y, null);				
			} else {
				this.renderPlayerStats(g, x, y, this.getGame().getLevel().getPlayers().get(1));
			}
		}
		super.renderButtonsAndRunner(g);
	}
	
	private void renderPlayerStats(Graphics2D g, int x, int y, ApoMarioPlayer player) {
		if (player == null) {
			int height = g.getFontMetrics().getHeight() - g.getFontMetrics().getDescent();
			y += height/2;
			String s = "No Player";
			int w = g.getFontMetrics().stringWidth(s);
			this.drawString(g, s, x - w/2, y, Color.LIGHT_GRAY, Color.BLACK);
		} else {
			int height = g.getFontMetrics().getHeight() - g.getFontMetrics().getDescent();
			y -= height/2;
			String aiName = "Human";
			if ((player.getAi() != null) && (player.getAi().getTeamName() != null)) {
				aiName = player.getAi().getTeamName();
			}
			int w = g.getFontMetrics().stringWidth(aiName);
			this.drawString(g, aiName, x - w/2, y, Color.LIGHT_GRAY, Color.BLACK);

			y += height;
			String by = "by";
			w = g.getFontMetrics().stringWidth(by);
			this.drawString(g, by, x - w/2, y, Color.LIGHT_GRAY, Color.BLACK);
			String author = "you";
			if ((player.getAi() != null) && (player.getAi().getAuthor() != null)) {
				author = player.getAi().getAuthor();
			}
			y += height;
			w = g.getFontMetrics().stringWidth(author);
			this.drawString(g, author, x - w/2, y, Color.LIGHT_GRAY, Color.BLACK);
		}	
	}
	
	private void drawString(Graphics2D g, String s, int x, int y, Color behind, Color color) {
		g.setColor(behind);
		g.drawString(s, x + 1, y + 1);
		g.setColor(color);
		g.drawString(s, x, y);
	}
}
