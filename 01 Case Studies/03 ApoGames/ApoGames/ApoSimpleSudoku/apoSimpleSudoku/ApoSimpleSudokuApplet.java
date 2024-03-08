package apoSimpleSudoku;

import javax.swing.JApplet;

import org.apogames.ApoConstants;
import org.apogames.ApoDisplayConfiguration;
import org.apogames.ApoLibraryGame;
import org.apogames.ApoScreen;
import org.apogames.ApoSubGame;

import apoSimpleSudoku.game.ApoSimpleSudokuPanel;

/**
* Startklasse für das Applet
* @author Dirk Aporius
*
*/
public class ApoSimpleSudokuApplet extends JApplet {

	private static final long serialVersionUID = 4094382521849709508L;
	
	/**
	 * Konstruktor und sagt dem Programm das es ein Applet ist, damit es später ausgewertet werden kann
	 */
	public ApoSimpleSudokuApplet() {
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
		ApoDisplayConfiguration displayConfiguration = new ApoDisplayConfiguration(ApoSimpleSudokuConstants.GAME_WIDTH, ApoSimpleSudokuConstants.GAME_HEIGHT, 16, true, true);

        final ApoSubGame subGame = new ApoSimpleSudokuPanel(new ApoScreen(ApoSimpleSudokuConstants.PROGRAM_NAME + " Version: "+ApoSimpleSudokuConstants.VERSION, displayConfiguration));
        final ApoLibraryGame game = new ApoLibraryGame(subGame);
        game.getScreen().setParent(this);
        game.getScreen().init();
        subGame.init();
        game.start();
	}

}
