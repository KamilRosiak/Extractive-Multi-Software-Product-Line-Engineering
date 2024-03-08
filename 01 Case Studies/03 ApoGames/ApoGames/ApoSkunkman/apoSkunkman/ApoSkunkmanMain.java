package apoSkunkman;

import org.apogames.ApoDisplayConfiguration;
import org.apogames.ApoLibraryGame;
import org.apogames.ApoScreen;
import org.apogames.ApoSubGame;

import apoSkunkman.game.ApoSkunkmanPanel;

/**
 * Startklasse mit der Mainmethode für das Spiel
 * @author Dirk Aporius
 *
 */
public class ApoSkunkmanMain {

	/**
	 * Startpunkt des Spiels
	 * es werden die Properties geladen und dann das Spiel gestartet
	 * @param args : übergebenes Stringarray
	 */
    public static void main(String[] args) {
    	ApoSkunkmanConstants.loadProperties();
    	new ApoSkunkmanMain();
    }
    
    /**
     * Konstruktor in dem das Spiel gestartet wird
     */
    public ApoSkunkmanMain() {
        ApoDisplayConfiguration displayConfiguration = new ApoDisplayConfiguration(ApoSkunkmanConstants.GAME_WIDTH, ApoSkunkmanConstants.GAME_HEIGHT, 16, true, false);

        final ApoScreen screen = new ApoScreen(ApoSkunkmanConstants.PROGRAM_NAME + " Version: "+ApoSkunkmanConstants.VERSION, displayConfiguration);
        final ApoSubGame subGame = new ApoSkunkmanPanel(screen);
        final ApoLibraryGame game = new ApoLibraryGame(subGame);
        game.getScreen().init();
        subGame.init();
        game.start();
    }
}
