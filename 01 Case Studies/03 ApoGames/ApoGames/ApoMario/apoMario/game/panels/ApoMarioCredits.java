package apoMario.game.panels;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.apogames.entity.ApoAnimation;

import apoMario.ApoMarioConstants;
import apoMario.ApoMarioImageContainer;
import apoMario.game.ApoMarioPanel;
import apoMario.game.ApoMarioSearch;
import apoMario.game.ApoMarioSearchNode;
import apoMario.game.ApoMarioSearchRunner;

/**
 * Creditspanelklasse<br />
 * Klasse, die die Credits des Spiels anzeigt<br />
 * @author Dirk Aporius
 */
public class ApoMarioCredits extends ApoMarioModelMenu {

	public static final String FUNCTION_CREDITS_BACK = "backCredits";
	
	public ApoMarioCredits(ApoMarioPanel game) {
		super(game);
	}

	@Override
	public void init() {
		super.init();

		int size = ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE;
		int x = (int)(this.getGame().getButtons()[24].getX()) - size;
		int y = (int)(this.getGame().getButtons()[24].getY());
		super.getRunner().setX(x - 3 * size);
		super.getRunner().setY(y - 0 * size);
		super.getRunner().setEnd(x - 3 * size, y - 0 * size);
		super.setCurNode(1);
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
			this.getGame().setMenu();
		} else if (button == KeyEvent.VK_T) {
			if (ApoMarioConstants.TILE_SET == ApoMarioConstants.TILE_SET_EAT) {
				ApoMarioConstants.TILE_SET = ApoMarioConstants.TILE_SET_MARIO;
			} else if (ApoMarioConstants.TILE_SET == ApoMarioConstants.TILE_SET_MARIO) {
				ApoMarioConstants.TILE_SET = ApoMarioConstants.TILE_SET_EAT;
			}
			ApoMarioImageContainer.setTileSet(ApoMarioConstants.TILE_SET);
			this.getGame().setCredits();
		} else {
			super.keyButtonReleasedArrowAndEnter(button, character);
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		super.setExcecuteFunction(function);
		int oldEndNode = this.getEndNode();
		this.setEndNode(-1);
		if (function.equals(ApoMarioCredits.FUNCTION_CREDITS_BACK)) {
			super.setEndNode(0);
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
		} else if (function.equals(ApoMarioCredits.FUNCTION_CREDITS_BACK)) {
			this.getGame().setMenu();
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
		
		this.renderCredits(g);
		
		super.renderButtonsAndRunner(g);
	}
	
	private void renderCredits(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.setFont(ApoMarioConstants.FONT_CREDITS);
		int size = ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE;
		int addSize = 10 + 10 * ApoMarioConstants.APP_SIZE;
		String s = "Credits";
		g.drawString(s, ApoMarioConstants.GAME_WIDTH/2 - g.getFontMetrics().stringWidth(s)/2, size * 2);
		
		g.setFont(ApoMarioConstants.FONT_MENU);		
		s = "Programmed by";
		g.drawString(s, size * 3, size * 4);
		s = "Dirk Aporius";
		g.drawString(s, size * 4, size * 4 + 1 * addSize);
		s = "Graphics by";
		g.drawString(s, size * 3, size * 4 + (int)(2.5 * addSize));
		s = "Antje 'Antjemon' Huebler";
		g.drawString(s, size * 4, size * 4 + (int)(3.5 * addSize));
		s = "Nintendo";
		g.drawString(s, size * 4, size * 4 + (int)(4.5 * addSize));
		s = "Dirk Aporius";
		g.drawString(s, size * 4, size * 4 + (int)(5.5 * addSize));
	}

}
