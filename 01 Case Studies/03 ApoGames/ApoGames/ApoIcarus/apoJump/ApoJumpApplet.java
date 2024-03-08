package apoJump;

import javax.swing.JApplet;

import org.apogames.ApoConstants;
import org.apogames.ApoDisplayConfiguration;
import org.apogames.ApoLibraryGame;
import org.apogames.ApoScreen;
import org.apogames.ApoSubGame;

import apoJump.game.ApoJumpPanel;

/**
* Startklasse für das Applet
* @author Dirk Aporius
*
*/
public class ApoJumpApplet extends JApplet {

	private static final long serialVersionUID = 4094382521849709508L;
	
	/**
	 * Konstruktor und sagt dem Programm das es ein Applet ist, damit es später ausgewertet werden kann
	 */
	public ApoJumpApplet() {
		ApoConstants.B_APPLET = true;
	}
	
	public void stop() {
	}
	
	public void destroy() {
		super.destroy();
	}
	
	public void init() {
		super.init();
		// Größe des Displays, Bildwiederholungsrate, ob im Fenster oder nicht, ob Applet oder nicht
		ApoDisplayConfiguration displayConfiguration = new ApoDisplayConfiguration(ApoJumpConstants.GAME_WIDTH, ApoJumpConstants.GAME_HEIGHT, 16, true, true);

        final ApoSubGame subGame = new ApoJumpPanel(new ApoScreen(ApoJumpConstants.PROGRAM_NAME + " Version: "+ApoJumpConstants.VERSION, displayConfiguration));
        final ApoLibraryGame game = new ApoLibraryGame(subGame);
        game.getScreen().setParent(this);
        game.getScreen().init();
        subGame.init();
        game.start();
	}

}
