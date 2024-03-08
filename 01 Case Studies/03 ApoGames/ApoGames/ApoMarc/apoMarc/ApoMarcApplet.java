package apoMarc;

import javax.swing.JApplet;

import org.apogames.ApoConstants;
import org.apogames.ApoDisplayConfiguration;

/**
* Startklasse für das Applet
* @author Dirk Aporius
*
*/
public class ApoMarcApplet extends JApplet {

	private static final long serialVersionUID = 4094382521849709508L;
	
	/**
	 * Konstruktor und sagt dem Programm das es ein Applet ist, damit es später ausgewertet werden kann
	 */
	public ApoMarcApplet() {
		ApoConstants.B_APPLET = true;
	}
	
	public void stop() {
	}
	
	public void destroy() {
		super.destroy();
	}
	
	public void init() {
		super.init();
		
		ApoDisplayConfiguration displayConfiguration = new ApoDisplayConfiguration(ApoMarcConstants.GAME_WIDTH, ApoMarcConstants.GAME_HEIGHT, 16, true, true);

        ApoLibraryGame game = new ApoLibraryGame(ApoMarcConstants.PROGRAM_NAME + " Version: "+ApoMarcConstants.VERSION, displayConfiguration);
        game.getScreen().setParent(this);
        game.getScreen().init();
        game.start();
	}

}
