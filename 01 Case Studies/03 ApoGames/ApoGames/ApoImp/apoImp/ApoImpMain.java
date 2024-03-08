package apoImp;

import org.apogames.ApoDisplayConfiguration;
import org.apogames.ApoLibraryGame;
import org.apogames.ApoScreen;
import org.apogames.ApoSubGame;

import apoImp.game.ApoImpPanel;

public class ApoImpMain {

	/**
	 * Startpunkt des Spiels
	 * es werden die Properties geladen und dann das Spiel gestartet
	 * @param args : übergebenes Stringarray
	 */
    public static void main(String[] args) {
    	new ApoImpMain();
    }
    
    /**
     * Konstruktor in dem das Spiel gestartet wird
     */
    public ApoImpMain() {
        ApoDisplayConfiguration displayConfiguration = new ApoDisplayConfiguration(ApoImpConstants.GAME_WIDTH, ApoImpConstants.GAME_HEIGHT, 16, true, false);

        final ApoScreen screen = new ApoScreen(ApoImpConstants.PROGRAM_NAME + " Version: "+ApoImpConstants.VERSION, displayConfiguration);
        final ApoSubGame subGame = new ApoImpPanel(screen);
        final ApoLibraryGame game = new ApoLibraryGame(subGame);
        game.getScreen().init();
        subGame.init();
        game.start();
    }

}
