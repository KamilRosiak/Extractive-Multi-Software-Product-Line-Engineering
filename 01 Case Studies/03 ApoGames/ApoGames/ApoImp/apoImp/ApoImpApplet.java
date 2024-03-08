package apoImp;

import javax.swing.JApplet;

import org.apogames.ApoConstants;
import org.apogames.ApoDisplayConfiguration;
import org.apogames.ApoLibraryGame;
import org.apogames.ApoScreen;
import org.apogames.ApoSubGame;

import apoImp.game.ApoImpPanel;

/**
* Startklasse für das Applet
* @author Dirk Aporius
*
*/
public class ApoImpApplet extends JApplet {

	private static final long serialVersionUID = 4094382521849709508L;
	
	private ApoSubGame subGame;
	
	/**
	 * Konstruktor und sagt dem Programm das es ein Applet ist, damit es später ausgewertet werden kann
	 */
	public ApoImpApplet() {
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
		ApoDisplayConfiguration displayConfiguration = new ApoDisplayConfiguration(ApoImpConstants.GAME_WIDTH, ApoImpConstants.GAME_HEIGHT, 16, true, true);

		this.subGame = new ApoImpPanel(new ApoScreen(ApoImpConstants.PROGRAM_NAME + " Version: "+ApoImpConstants.VERSION, displayConfiguration));
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
