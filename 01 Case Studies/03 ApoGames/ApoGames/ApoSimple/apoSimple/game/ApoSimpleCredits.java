package apoSimple.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.apogames.help.ApoGameCounter;

public class ApoSimpleCredits extends ApoSimpleModel {

	public static final String MENU_STRING = "menu";
	public static final String MENU = "credits_menu";

	private BufferedImage iBackground;
	
	private Rectangle2D.Float oneButtonRec;
	private Rectangle2D.Float bubbleRec;
	private Rectangle2D.Float hiddenPuzzleRec;
	private Rectangle2D.Float hiddenFunRec;
	private Rectangle2D.Float hiddenPushRec;
	
	public ApoSimpleCredits(ApoSimplePanel game) {
		super(game);
	}
	
	@Override
	public void init() {
		if (this.iBackground == null) {
			this.iBackground = this.getGame().getImages().getImage("images/credits.png", false);
		}
		if (this.oneButtonRec == null) {
			this.oneButtonRec = new Rectangle2D.Float(320, 395, 70, 50);
		}
		if (this.bubbleRec == null) {
			this.bubbleRec = new Rectangle2D.Float(400, 395, 75, 50);
		}
		if (this.hiddenPuzzleRec == null) {
			this.hiddenPuzzleRec = new Rectangle2D.Float(140, 376, 85, 60);
		}
		if (this.hiddenFunRec == null) {
			this.hiddenFunRec = new Rectangle2D.Float(428, 234, 47, 53);
		}
		if (this.hiddenPushRec == null) {
			this.hiddenPushRec = new Rectangle2D.Float(23, 105, 58, 48);
		}
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
			this.getGame().setMenu();
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
		if (function.equals(ApoSimpleCredits.MENU)) {
			this.getGame().setMenu();
		}
	}
	
	@Override
	public void mouseButtonReleased(int x, int y, boolean bRight) {
		if (this.oneButtonRec.contains(x, y)) {
			ApoGameCounter.getInstance().save("start_hiddenOneButtonGame_"+this.getGame().getTextName());
			this.getGame().setOneButton();
		} else if (this.bubbleRec.contains(x, y)) {
			ApoGameCounter.getInstance().save("start_hiddenBubbleGame_"+this.getGame().getTextName());
			this.getGame().setBubbleGame();
		} else if (this.hiddenPuzzleRec.contains(x, y)) {
			ApoGameCounter.getInstance().save("start_hiddenMoveTheSheep_"+this.getGame().getTextName());
			this.getGame().setHiddenPuzzleGame();
		} else if (this.hiddenFunRec.contains(x, y)) {
			ApoGameCounter.getInstance().save("start_hiddenfun_"+this.getGame().getTextName());
			this.getGame().setHiddenFun();
		} else if (this.hiddenPushRec.contains(x, y)) {
			ApoGameCounter.getInstance().save("start_hiddenpush_"+this.getGame().getTextName());
			this.getGame().setHiddenPush();
		}
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
