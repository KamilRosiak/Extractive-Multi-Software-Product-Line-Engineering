package apoCommando;


import org.apogames.ApoDisplayConfiguration;
import org.apogames.ApoLibraryGame;
import org.apogames.ApoScreen;
import org.apogames.ApoSubGame;

import apoCommando.game.ApoMarioPanel;

/**
 * Startklasse mit der Mainmethode für das Spiel
 * @author Dirk Aporius
 *
 */
public class ApoMarioMain {

	/**
	 * Startpunkt des Spiels
	 * es werden die Properties geladen und dann das Spiel gestartet
	 * @param args : übergebenes Stringarray
	 */
    public static void main(String[] args) {
    	new ApoMarioMain();
    }
    
    /**
     * Konstruktor in dem das Spiel gestartet wird
     */
    public ApoMarioMain() {
        ApoDisplayConfiguration displayConfiguration = new ApoDisplayConfiguration(ApoMarioConstants.GAME_WIDTH, ApoMarioConstants.GAME_HEIGHT, 16, true, false);

        final ApoScreen screen = new ApoScreen(ApoMarioConstants.PROGRAM_NAME + " Version: "+ApoMarioConstants.VERSION, displayConfiguration);
        final ApoSubGame subGame = new ApoMarioPanel(screen);
        final ApoLibraryGame game = new ApoLibraryGame(subGame);
        game.getScreen().init();
        subGame.init();
        game.start();
    }
}

