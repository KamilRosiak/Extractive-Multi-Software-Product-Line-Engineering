package apoMarc.game;

import java.awt.Graphics2D;

public class ApoMarcMenu extends ApoMarcModel {
	
	public static final String QUIT = "quit";
	public static final String START = "start";
	public static final String OPTIONS = "options";
	public static final String ACHIEVEMENTS = "achievements";
	public static final String CREDITS = "credits";
	
	public ApoMarcMenu(ApoMarcPanel game) {
		super(game);
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoMarcMenu.QUIT)) {
			System.exit(0);
		} else if (function.equals(ApoMarcMenu.START)) {
			this.getGame().setDifficulty();
		} else if (function.equals(ApoMarcMenu.OPTIONS)) {
			this.getGame().setOptions();
		} else if (function.equals(ApoMarcMenu.ACHIEVEMENTS)) {
			this.getGame().setAchievements();
		} else if (function.equals(ApoMarcMenu.CREDITS)) {
			this.getGame().setCredits();
		}
	}
	
	@Override
	public void mouseButtonReleased(int x, int y) {
	}

	@Override
	public void think(long delta) {
		
	}
	
	@Override
	public void render(Graphics2D g) {
		this.getGame().renderBackground(g);
	}

}
