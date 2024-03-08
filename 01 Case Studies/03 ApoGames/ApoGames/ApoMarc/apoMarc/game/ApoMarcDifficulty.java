package apoMarc.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import apoMarc.ApoMarcConstants;

public class ApoMarcDifficulty extends ApoMarcModel {
	
	public static final String DIFFICULTY_MARC = "marc";
	public static final String DIFFICULTY_MANDY = "mandy";
	public static final String DIFFICULTY_APO = "apo";
	public static final String DIFFICULTY_UNBEATABLE = "unbeatable";
	public static final String BACK = "back";
	
	private BufferedImage iDifficulty;
	
	public ApoMarcDifficulty(ApoMarcPanel game) {
		super(game);
		
		this.init();
	}

	public void init() {
		if (this.iDifficulty == null) {
			BufferedImage iDifficulty = this.getGame().getImages().getImage("images/background_difficulty.png", false);
			this.iDifficulty = new BufferedImage(iDifficulty.getWidth(), iDifficulty.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
			Graphics2D g = this.iDifficulty.createGraphics();
			g.drawImage(iDifficulty, 0, 0, null);
			g.dispose();
		}
	}
	
	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			this.getGame().setMenu();
		}
		
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoMarcDifficulty.BACK)) {
			this.getGame().setMenu();
		} else if (function.equals(ApoMarcDifficulty.DIFFICULTY_MARC)) {
			this.getGame().setGame(ApoMarcConstants.DIFFICULTY_MARC);
		} else if (function.equals(ApoMarcDifficulty.DIFFICULTY_MANDY)) {
			this.getGame().setGame(ApoMarcConstants.DIFFICULTY_MANDY);
		} else if (function.equals(ApoMarcDifficulty.DIFFICULTY_APO)) {
			this.getGame().setGame(ApoMarcConstants.DIFFICULTY_APO);
		} else if (function.equals(ApoMarcDifficulty.DIFFICULTY_UNBEATABLE)) {
			this.getGame().setGame(ApoMarcConstants.DIFFICULTY_UNBEATABLE);
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
		
		if (this.iDifficulty != null) {
			int x = ApoMarcConstants.GAME_WIDTH/2 - this.iDifficulty.getWidth()/2;
			int y = 110;
			g.drawImage(this.iDifficulty, x, y, null); 
		}
	}

}
