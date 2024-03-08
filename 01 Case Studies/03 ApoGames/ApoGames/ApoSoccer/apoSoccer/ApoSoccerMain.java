package apoSoccer;

import java.awt.Dimension;

import org.apogames.ApoMainBufferedStrategy;

import apoSoccer.game.ApoSoccerGame;
//import org.apogames.speech.*;

/**
 * Klasse mit der das Spiel gestartet wird
 * @author Dirk Aporius
 *
 */
public class ApoSoccerMain extends ApoMainBufferedStrategy {

	private static final long serialVersionUID = 1L;
	/** das Spielfeldobjekt */
	private ApoSoccerGame game;
	
	public ApoSoccerMain(String splashUrl) {
		super(splashUrl);
	}

	@Override
	public void init() {
		super.setTitle( "=== ApoSoccer === ("+ApoSoccerGame.VERSION+")" );
		
		this.game = new ApoSoccerGame(true, true, ApoSoccerConstants.WAIT_TIME_THINK, ApoSoccerConstants.WAIT_TIME_RENDER);
		this.game.setSize( new Dimension(ApoSoccerConstants.GAME_WIDTH, ApoSoccerConstants.GAME_HEIGHT) );
		
		super.setComponent( this.game );
		
		super.setIconImage( new ApoSoccerImages().getLogo( 16, 16) );
	}

	public static void main(String[] args) {
		//System.setProperty("sun.java2d.opengl", "True");
		//System.setProperty("sun.java2d.translaccel", "True");

		//new ApoSpeech();
		/*ApoSoccerMain main = */new ApoSoccerMain("images/logo.png");
		//main.game.makeDebugAndAnalysisFrame();
		//main.game.loadProperties();
	}
	
}
