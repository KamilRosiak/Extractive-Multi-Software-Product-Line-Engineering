package apoRelax;

import javax.swing.JApplet;

import org.apogames.ApoConstants;
import org.apogames.ApoDisplayConfiguration;
import org.apogames.ApoLibraryGame;
import org.apogames.ApoScreen;
import org.apogames.ApoSubGame;

import apoRelax.game.ApoRelaxPanel;

/**
* Startklasse für das Applet
* @author Dirk Aporius
*
*/
public class ApoRelaxApplet extends JApplet {

	private static final long serialVersionUID = 4094382521849709508L;
	
	private ApoSubGame subGame;
	
	/**
	 * Konstruktor und sagt dem Programm das es ein Applet ist, damit es später ausgewertet werden kann
	 */
	public ApoRelaxApplet() {
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
		// Größe des Displays, Bildwiederholungsrate, ob im Fenster oder nicht, ob Applet oder nicht
		ApoDisplayConfiguration displayConfiguration = new ApoDisplayConfiguration(ApoRelaxConstants.GAME_WIDTH, ApoRelaxConstants.GAME_HEIGHT, 16, true, true);

        this.subGame = new ApoRelaxPanel(new ApoScreen(ApoRelaxConstants.PROGRAM_NAME + " Version: "+ApoRelaxConstants.VERSION, displayConfiguration));
        final ApoLibraryGame game = new ApoLibraryGame(this.subGame);
        game.getScreen().setParent(this);
        game.getScreen().init();
        this.subGame.init();
        game.start();
	}

}
