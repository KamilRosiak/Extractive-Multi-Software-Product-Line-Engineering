package apoBot;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.apogames.ApoMainBufferedStrategy;

import apoBot.game.ApoBotGame;

/**
 * Startklasse für die Applikation
 * @author Dirk Aporius
 *
 */
public class ApoBotMain extends ApoMainBufferedStrategy {

	private static final long serialVersionUID = 1L;

	private ApoBotGame game;
	
	/**
	 * main-methode
	 * @param args
	 */
	public static void main(String[] args) {
		new ApoBotMain("images/logo.png");
	}
	
	/**
	 * der Pfad zum Splashscreenlogo
	 * @param splashUrl : Pfad zum Splashscreenlogo
	 */
	public ApoBotMain(String splashUrl) {
		super(splashUrl);
	}

	@Override
	public void init() {
		super.setTitle( "=== ApoBot ===" );
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		this.game = new ApoBotGame(true, true, ApoBotConstants.WAIT_TIME_THINK, ApoBotConstants.WAIT_TIME_RENDER);
		this.game.setSize(ApoBotConstants.GAME_WIDTH, ApoBotConstants.GAME_HEIGHT );
		
		super.setComponent(this.game);
		
		super.setIconImage(new ApoBotImage().getLogo(23 * ApoBotConstants.SIZE, 25 * ApoBotConstants.SIZE, this.game.getIPlayer()));
		
	}

}
