package apoBot;

import org.apogames.ApoAppletBufferedStrategy;

import apoBot.game.ApoBotGame;

/**
 * Startklasse für das Applet
 * @author Dirk Aporius
 *
 */
public class ApoBotApplet extends ApoAppletBufferedStrategy {

	private static final long serialVersionUID = 1L;

	private ApoBotGame game;
	
	public ApoBotApplet() {
		super(ApoBotConstants.GAME_WIDTH, ApoBotConstants.GAME_HEIGHT);
	}
	
	public ApoBotApplet(int width, int height) {
		super(width, height);
	}
	
	public void init() {
		super.init();
		
		this.game = new ApoBotGame(true, true, ApoBotConstants.WAIT_TIME_THINK, ApoBotConstants.WAIT_TIME_RENDER);
		this.game.setSize(ApoBotConstants.GAME_WIDTH, ApoBotConstants.GAME_HEIGHT );
		
		super.setComponent(this.game);
	}

}
