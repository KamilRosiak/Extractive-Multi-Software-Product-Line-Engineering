package apoSnake;

import javax.swing.JApplet;

import org.apogames.ApoConstants;
import org.apogames.ApoDisplayConfiguration;
import org.apogames.ApoLibraryGame;
import org.apogames.ApoScreen;
import org.apogames.ApoSubGame;

import apoSnake.game.ApoSnakePanel;

/**
* Startklasse für das Applet
* @author Dirk Aporius
*
*/
public class ApoSnakeApplet extends JApplet {

	private static final long serialVersionUID = 4094382521849709508L;
	
	private ApoSubGame subGame;
	
	/**
	 * Konstruktor und sagt dem Programm das es ein Applet ist, damit es später ausgewertet werden kann
	 */
	public ApoSnakeApplet() {
		ApoConstants.B_APPLET = true;
	}
	
	public void stop() {
		this.subGame.stopGame();
	}
	
	public void destroy() {
		super.destroy();
	}
	
	public void init() {
		super.init();
		ApoDisplayConfiguration displayConfiguration = new ApoDisplayConfiguration(ApoSnakeConstants.GAME_WIDTH, ApoSnakeConstants.GAME_HEIGHT, 16, true, true);

		this.subGame = new ApoSnakePanel(new ApoScreen(ApoSnakeConstants.PROGRAM_NAME + " Version: "+ApoSnakeConstants.VERSION, displayConfiguration));
		final ApoLibraryGame game = new ApoLibraryGame(this.subGame);
		game.getScreen().setParent(this);
		game.getScreen().init();
		this.addNotify();
		game.start();
	}

	public void addNotify() {
		super.addNotify();
		
		if (this.subGame != null) {
	        this.subGame.init();
		}
	}
}
