package apoMarc;

import org.apogames.ApoConstants;
import org.apogames.ApoDisplayConfiguration;
import org.apogames.ApoLibraryGame;
import org.apogames.ApoScreen;
import org.apogames.ApoSubGame;

import apoMarc.game.ApoMarcPanel;

public class ApoMarcMain {

	/**
	 * Startpunkt des Spiels
	 * es werden die Properties geladen und dann das Spiel gestartet
	 * @param args : übergebenes Stringarray
	 */
    public static void main(String[] args) {
    	new ApoMarcMain();
    }
    
    /**
     * Konstruktor in dem das Spiel gestartet wird
     */
    public ApoMarcMain() {
        ApoDisplayConfiguration displayConfiguration = new ApoDisplayConfiguration(ApoConstants.GAME_WIDTH, ApoConstants.GAME_HEIGHT, 16, true, false);

        final ApoScreen screen = new ApoScreen("=== ApoMarc ===" + " Version: "+ApoMarcConstants.VERSION, displayConfiguration);
        final ApoSubGame subGame = new ApoMarcPanel(screen);
        final ApoLibraryGame game = new ApoLibraryGame(subGame);
        game.getScreen().init();
        subGame.init();
        game.start();
    }
}
