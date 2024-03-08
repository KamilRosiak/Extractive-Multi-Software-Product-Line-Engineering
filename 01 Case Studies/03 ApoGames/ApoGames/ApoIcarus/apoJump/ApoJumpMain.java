package apoJump;

import org.apogames.ApoDisplayConfiguration;
import org.apogames.ApoLibraryGame;
import org.apogames.ApoScreen;
import org.apogames.ApoSubGame;

import apoJump.game.ApoJumpPanel;

public class ApoJumpMain {

	/**
	 * Startpunkt des Spiels
	 * es werden die Properties geladen und dann das Spiel gestartet
	 * @param args : übergebenes Stringarray
	 */
    public static void main(String[] args) {
    	new ApoJumpMain();
    }
    
    /**
     * Konstruktor in dem das Spiel gestartet wird
     */
    public ApoJumpMain() {
        ApoDisplayConfiguration displayConfiguration = new ApoDisplayConfiguration(ApoJumpConstants.GAME_WIDTH, ApoJumpConstants.GAME_HEIGHT, 16, true, false);

        final ApoScreen screen = new ApoScreen(ApoJumpConstants.PROGRAM_NAME + " Version: "+ApoJumpConstants.VERSION, displayConfiguration);
        final ApoSubGame subGame = new ApoJumpPanel(screen);
        final ApoLibraryGame game = new ApoLibraryGame(subGame);
        game.getScreen().init();
        subGame.init();
        game.start();
    }

}
