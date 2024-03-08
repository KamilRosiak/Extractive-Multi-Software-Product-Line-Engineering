package apoRelax;

import org.apogames.ApoDisplayConfiguration;
import org.apogames.ApoLibraryGame;
import org.apogames.ApoScreen;
import org.apogames.ApoSubGame;

import apoRelax.game.ApoRelaxPanel;

public class ApoRelaxMain {

	/**
	 * Startpunkt des Spiels
	 * es werden die Properties geladen und dann das Spiel gestartet
	 * @param args : übergebenes Stringarray
	 */
    public static void main(String[] args) {
    	new ApoRelaxMain();
    }
    
    /**
     * Konstruktor in dem das Spiel gestartet wird
     */
    public ApoRelaxMain() {
        ApoDisplayConfiguration displayConfiguration = new ApoDisplayConfiguration(ApoRelaxConstants.GAME_WIDTH, ApoRelaxConstants.GAME_HEIGHT, 16, true, false);

        final ApoScreen screen = new ApoScreen(ApoRelaxConstants.PROGRAM_NAME + " Version: "+ApoRelaxConstants.VERSION, displayConfiguration);
        final ApoSubGame subGame = new ApoRelaxPanel(screen);
        final ApoLibraryGame game = new ApoLibraryGame(subGame);
        game.getScreen().init();
        subGame.init();
        game.start();
    }

}
