package apoMarc.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import apoMarc.ApoMarcConstants;
import apoMarc.entity.ApoMarcHelpButton;

public class ApoMarcOptions extends ApoMarcModel {

	public static final String MENU_STRING = "menu";
	public static final String MENU = "options_menu";
	public static final String EFFECTS = "options_effects";
	public static final String LIGHTS = "options_lights";
	public static final String APO = "options_apo";
	
	private BufferedImage iOptions;
	
	public ApoMarcOptions(ApoMarcPanel game) {
		super(game);
		
		this.init();
	}
	
	public void init() {
		if (this.iOptions == null) {
			BufferedImage iOptions = this.getGame().getImages().getImage("images/background_options.png", false);
			this.iOptions = new BufferedImage(iOptions.getWidth(), iOptions.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
			Graphics2D g = this.iOptions.createGraphics();
			g.drawImage(iOptions, 0, 0, null);
			g.dispose();
		}
		((ApoMarcHelpButton)(this.getGame().getButtons()[15])).setBActive(ApoMarcConstants.LIGHTS);
		((ApoMarcHelpButton)(this.getGame().getButtons()[14])).setBActive(ApoMarcConstants.EFFECTS);
		
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			this.getGame().setMenu();
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoMarcOptions.MENU)) {
			this.getGame().setMenu();
		} else if (function.equals(ApoMarcOptions.EFFECTS)) {
			ApoMarcConstants.EFFECTS = !ApoMarcConstants.EFFECTS;
			((ApoMarcHelpButton)(this.getGame().getButtons()[14])).setBActive(ApoMarcConstants.EFFECTS);
		} else if (function.equals(ApoMarcOptions.LIGHTS)) {
			ApoMarcConstants.LIGHTS = !ApoMarcConstants.LIGHTS;
			((ApoMarcHelpButton)(this.getGame().getButtons()[15])).setBActive(ApoMarcConstants.LIGHTS);
		} else if (function.equals(ApoMarcOptions.APO)) {
			boolean bActive = ((ApoMarcHelpButton)(this.getGame().getButtons()[16])).isBActive();
			if (!bActive) {
				this.getGame().addAchievement(ApoMarcAchievements.ACHIEVEMENT_APO);
			}
			((ApoMarcHelpButton)(this.getGame().getButtons()[16])).setBActive(!bActive);
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
		
		if (this.iOptions != null) {
			int x = ApoMarcConstants.GAME_WIDTH/2 - this.iOptions.getWidth()/2;
			int y = 100;
			g.drawImage(this.iOptions, x, y, null); 
		}
	}

}
