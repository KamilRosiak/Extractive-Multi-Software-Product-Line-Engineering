package apoPongBeat;

import org.apogames.ApoConstants;
import org.apogames.ApoDisplayConfiguration;
import org.apogames.ApoLibraryGame;
import org.apogames.ApoScreen;
import org.apogames.ApoSubGame;

import apoPongBeat.game.ApoPongBeatPanel;

public class ApoPongBeatMain {

	/**
	 * Startpunkt des Spiels
	 * es werden die Properties geladen und dann das Spiel gestartet
	 * @param args : übergebenes Stringarray
	 */
    public static void main(String[] args) {
    	new ApoPongBeatMain();
    }
    
    /**
     * Konstruktor in dem das Spiel gestartet wird
     */
    public ApoPongBeatMain() {
        ApoDisplayConfiguration displayConfiguration = new ApoDisplayConfiguration(ApoConstants.GAME_WIDTH, ApoConstants.GAME_HEIGHT, 16, true, false);

        final ApoScreen screen = new ApoScreen(ApoPongBeatConstants.PROGRAM_NAME + " Version: "+ApoPongBeatConstants.VERSION, displayConfiguration);
        final ApoSubGame subGame = new ApoPongBeatPanel(screen);
        final ApoLibraryGame game = new ApoLibraryGame(subGame);
        game.getScreen().init();
        subGame.init();
        game.start();
    }

}
