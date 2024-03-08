package apoSimple.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import apoSimple.game.level.ApoSimpleLevel;

public class ApoSimplePuzzle extends ApoSimpleModel {

	public static final String LEVELCHOOSER = "levelchooser";
	public static final String USERLEVELS = "userlevels";
	public static final String EDITOR = "editor";
	
	private BufferedImage iBackground;
	
	public ApoSimplePuzzle(ApoSimplePanel game) {
		super(game);
	}
	
	@Override
	public void init() {
		if (this.iBackground == null) {
			this.iBackground = this.getGame().getImages().getImage("images/puzzlemode.png", false);
		}
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
			this.getGame().setMenu();
		} else if (button == KeyEvent.VK_E) {
			super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
			this.getGame().setEditor();
		} else if (button == KeyEvent.VK_L) {
			super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
			this.getGame().setLevelChooser();
		} else if (button == KeyEvent.VK_U) {
			super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
			this.loadUserlevels();
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
		if (function.equals(ApoSimpleLevelChooser.MENU)) {
			this.getGame().setMenu();
		} else if (function.equals(ApoSimplePuzzle.LEVELCHOOSER)) {
			this.getGame().setLevelChooser();
		} else if (function.equals(ApoSimplePuzzle.EDITOR)) {
			this.getGame().setEditor();
		} else if (function.equals(ApoSimplePuzzle.USERLEVELS)) {
			this.loadUserlevels();
		}
	}
	
	private void loadUserlevels() {
		ApoSimpleLevel level = this.getGame().getUserlevels().getCurrentLevel();
		if (level != null) {
			this.getGame().setLevel(this.getGame().getUserlevels().getCurrentLevel(), false, ApoSimpleLevelGame.USERLEVELS);
		}
	}
	
	@Override
	public void mouseButtonReleased(int x, int y, boolean bRight) {
	}

	@Override
	public void think(int delta) {
		
	}
	
	@Override
	public void render(Graphics2D g) {
		if (this.iBackground != null) {
			g.drawImage(this.iBackground, 0, 0, null);
		}
	}
	
	public void drawOverlay(Graphics2D g) {	
		super.getGame().renderCoins(g, 0, 0);
	}

}
