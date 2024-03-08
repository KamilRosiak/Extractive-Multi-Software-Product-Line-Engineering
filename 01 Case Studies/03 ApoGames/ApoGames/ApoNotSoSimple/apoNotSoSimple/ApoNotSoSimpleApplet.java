package apoNotSoSimple;

import javax.swing.JApplet;

import org.apogames.ApoConstants;
import org.apogames.ApoDisplayConfiguration;
import org.apogames.ApoLibraryGame;
import org.apogames.ApoScreen;
import org.apogames.ApoSubGame;

import apoNotSoSimple.game.ApoNotSoSimplePanel;

/**
* Startklasse für das Applet
* @author Dirk Aporius
*
*/
public class ApoNotSoSimpleApplet extends JApplet {

	private static final long serialVersionUID = 4094382521849709508L;
	
	private ApoSubGame subGame;
	
	/**
	 * Konstruktor und sagt dem Programm das es ein Applet ist, damit es später ausgewertet werden kann
	 */
	public ApoNotSoSimpleApplet() {
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
		ApoDisplayConfiguration displayConfiguration = new ApoDisplayConfiguration(ApoConstants.GAME_WIDTH, ApoConstants.GAME_HEIGHT, 16, true, true);

        this.subGame = new ApoNotSoSimplePanel(new ApoScreen(ApoNotSoSimpleConstants.PROGRAM_NAME + " Version: "+ApoNotSoSimpleConstants.VERSION, displayConfiguration));
        final ApoLibraryGame game = new ApoLibraryGame(this.subGame);
        game.getScreen().setParent(this);
        game.getScreen().init();
        this.subGame.init();
        game.start();
	}

}
