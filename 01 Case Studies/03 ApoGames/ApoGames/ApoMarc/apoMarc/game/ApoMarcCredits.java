package apoMarc.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import apoMarc.ApoMarcConstants;

public class ApoMarcCredits extends ApoMarcModel {

	public static final String MENU_STRING = "menu";
	public static final String MENU = "credits_menu";

	private BufferedImage iCredits;
	
	public ApoMarcCredits(ApoMarcPanel game) {
		super(game);
		
		this.init();
	}

	public void init() {
		if (this.iCredits == null) {
			BufferedImage iCredits = this.getGame().getImages().getImage("images/background_credits.png", false);
			this.iCredits = new BufferedImage(iCredits.getWidth(), iCredits.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
			Graphics2D g = this.iCredits.createGraphics();
			g.drawImage(iCredits, 0, 0, null);
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
		if (function.equals(ApoMarcCredits.MENU)) {
			this.getGame().setMenu();
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
		
		if (this.iCredits != null) {
			int x = ApoMarcConstants.GAME_WIDTH/2 - this.iCredits.getWidth()/2;
			int y = 100;
			g.drawImage(this.iCredits, x, y, null); 
		}
	}


}
