package apoSimpleSudoku;

import org.apogames.ApoDisplayConfiguration;
import org.apogames.ApoLibraryGame;
import org.apogames.ApoScreen;
import org.apogames.ApoSubGame;

import apoSimpleSudoku.game.ApoSimpleSudokuPanel;

/**
 * Startklasse mit der Mainmethode für das Spiel
 * @author Dirk Aporius
 *
 */
public class ApoSimpleSudokuMain {

	/**
	 * Startpunkt des Spiels
	 * es werden die Properties geladen und dann das Spiel gestartet
	 * @param args : übergebenes Stringarray
	 */
    public static void main(String[] args) {
    	new ApoSimpleSudokuMain();
    }
    
    /**
     * Konstruktor in dem das Spiel gestartet wird
     */
    public ApoSimpleSudokuMain() {
        ApoDisplayConfiguration displayConfiguration = new ApoDisplayConfiguration(ApoSimpleSudokuConstants.GAME_WIDTH, ApoSimpleSudokuConstants.GAME_HEIGHT, 16, true, false);

        final ApoScreen screen = new ApoScreen(ApoSimpleSudokuConstants.PROGRAM_NAME + " Version: "+ApoSimpleSudokuConstants.VERSION, displayConfiguration);
        final ApoSubGame subGame = new ApoSimpleSudokuPanel(screen);
        final ApoLibraryGame game = new ApoLibraryGame(subGame);
        game.getScreen().init();
        subGame.init();
        game.start();
    }
}
