package apoSimple.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import apoSimple.ApoSimpleConstants;
import apoSimple.entity.ApoSimpleEntityMenu;

public class ApoSimpleMenu extends ApoSimpleModel {
	
	public static final String QUIT = "quit";
	public static final String START = "start";
	public static final String TUTORIAL = "tutorial";
	public static final String HIGHSCORE = "highscore";
	public static final String CREDITS = "credits";
	public static final String EDITOR = "editor";
	public static final String LEVEL = "level";
	public static final String OPTIONS = "options";
	
	private BufferedImage iBackground;
	
	public ApoSimpleMenu(ApoSimplePanel game) {
		super(game);
	}
	
	@Override
	public void init() {	
		if (this.iBackground == null) {
			this.iBackground = this.getGame().getImages().getImage("images/menu.png", false);
		}
		if (super.getMenuSheep().size() <= 0) {
			int width = ApoSimpleConstants.ENTITY_WIDTH;
			int minX = 400;
			int minY = 225;
			int maxWidth = ApoSimpleConstants.GAME_WIDTH - minX - width;
			int maxHeight = ApoSimpleConstants.GAME_HEIGHT - minY - width;
			for (int i = 10; i < maxWidth; i+= width + 10) {
				for (int j = 10; j < maxHeight; j+= width + 10) {
					super.getMenuSheep().add(new ApoSimpleEntityMenu(minX + i, minY + j, width, width, new Rectangle2D.Float(minX, minY, maxWidth, maxHeight)));					
				}
			}
			
			minX = 0;
			minY = 0;
			maxWidth = 270 - minX - width;
			maxHeight = 150 - width;
			for (int i = 10; i < maxWidth; i+= width + 10) {
				for (int j = 10; j < maxHeight; j+= width + 10) {
					super.getMenuSheep().add(new ApoSimpleEntityMenu(minX + i, minY + j, width, width, new Rectangle2D.Float(minX, minY, maxWidth, maxHeight)));					
				}
			}
			for (int i = 0; i < 1000; i++) {
				super.thinkSheep(ApoSimpleConstants.WAIT_TIME_THINK);
			}
		}
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
			this.quitGame();
		} else if (button == KeyEvent.VK_E) {
			this.getGame().setEditor();
		}
	}

	private void quitGame() {
		this.getGame().stopGame();
		System.exit(0);
	}
	
	@Override
	public void mouseButtonFunction(String function) {
		super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
		if (function.equals(ApoSimpleMenu.QUIT)) {
			this.quitGame();
		} else if (function.equals(ApoSimpleMenu.START)) {
			this.getGame().startGame();
		} else if (function.equals(ApoSimpleMenu.TUTORIAL)) {
			this.getGame().setTutorial();
		} else if (function.equals(ApoSimpleMenu.HIGHSCORE)) {
			this.getGame().setHighscore();
		} else if (function.equals(ApoSimpleMenu.CREDITS)) {
			this.getGame().setCredits();
		} else if (function.equals(ApoSimpleMenu.LEVEL)) {
			this.getGame().setPuzzle();
		} else if (function.equals(ApoSimpleMenu.EDITOR)) {
			this.getGame().setEditor();
		} else if (function.equals(ApoSimpleMenu.OPTIONS)) {
			this.getGame().setOptions();
		}
	}
	
	@Override
	public void mouseButtonReleased(int x, int y, boolean right) {
	}

	@Override
	public void think(int delta) {
		super.thinkSheep(delta);
	}
	
	@Override
	public void render(Graphics2D g) {
		if (this.iBackground != null) {
			g.drawImage(this.iBackground, 0, 0, null);
		}
		super.renderSheep(g);
		super.getGame().renderCoins(g, 0, 0);
	}

}
