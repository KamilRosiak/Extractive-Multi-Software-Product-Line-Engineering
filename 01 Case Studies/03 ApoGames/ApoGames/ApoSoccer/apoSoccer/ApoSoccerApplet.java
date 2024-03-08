package apoSoccer;

import org.apogames.ApoAppletBufferedStrategy;

import apoSoccer.game.ApoSoccerGame;

public class ApoSoccerApplet extends ApoAppletBufferedStrategy {

	private static final long serialVersionUID = 1L;

	/** das Spielfeldobjekt */
	private ApoSoccerGame game;
	
	public ApoSoccerApplet(int width, int height) {
		super(width, height);
	}
	
	public ApoSoccerApplet() {
		super(ApoSoccerConstants.GAME_WIDTH, ApoSoccerConstants.GAME_HEIGHT);
	}
	
	@Override
	public void init() {
		super.init();
		
		this.game = new ApoSoccerGame(true, true, ApoSoccerConstants.WAIT_TIME_THINK, ApoSoccerConstants.WAIT_TIME_RENDER);
		this.game.setSize( ApoSoccerConstants.GAME_WIDTH, ApoSoccerConstants.GAME_HEIGHT );
		
		super.setComponent( this.game );
	}


}
