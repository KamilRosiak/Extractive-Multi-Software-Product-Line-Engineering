package apoNotSoSimple;

import org.apogames.ApoConstants;
import org.apogames.ApoDisplayConfiguration;
import org.apogames.ApoLibraryGame;
import org.apogames.ApoScreen;
import org.apogames.ApoSubGame;

import apoNotSoSimple.game.ApoNotSoSimplePanel;

public class ApoNotSoSimpleMain {

	/**
	 * Startpunkt des Spiels
	 * es werden die Properties geladen und dann das Spiel gestartet
	 * @param args : übergebenes Stringarray
	 */
    public static void main(String[] args) {
    	new ApoNotSoSimpleMain();
    }
    
    /**
     * Konstruktor in dem das Spiel gestartet wird
     */
    public ApoNotSoSimpleMain() {
        ApoDisplayConfiguration displayConfiguration = new ApoDisplayConfiguration(ApoConstants.GAME_WIDTH, ApoConstants.GAME_HEIGHT, 16, true, false);

        final ApoScreen screen = new ApoScreen(ApoNotSoSimpleConstants.PROGRAM_NAME + " Version: "+ApoNotSoSimpleConstants.VERSION, displayConfiguration);
        final ApoSubGame subGame = new ApoNotSoSimplePanel(screen);
        final ApoLibraryGame game = new ApoLibraryGame(subGame);
        game.getScreen().init();
        subGame.init();
        game.start();
    }

}
