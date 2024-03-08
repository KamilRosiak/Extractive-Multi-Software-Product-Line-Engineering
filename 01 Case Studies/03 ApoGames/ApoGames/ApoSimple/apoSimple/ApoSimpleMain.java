package apoSimple;

import org.apogames.ApoDisplayConfiguration;
import org.apogames.ApoLibraryGame;
import org.apogames.ApoScreen;
import org.apogames.ApoSubGame;

import apoSimple.game.ApoSimplePanel;

public class ApoSimpleMain {

	/**
	 * Startpunkt des Spiels
	 * es werden die Properties geladen und dann das Spiel gestartet
	 * @param args : übergebenes Stringarray
	 */
    public static void main(String[] args) {
    	new ApoSimpleMain();
    }
    
    /**
     * Konstruktor in dem das Spiel gestartet wird
     */
    public ApoSimpleMain() {
        ApoDisplayConfiguration displayConfiguration = new ApoDisplayConfiguration(ApoSimpleConstants.GAME_WIDTH, ApoSimpleConstants.GAME_HEIGHT, 16, true, false);

        final ApoScreen screen = new ApoScreen(ApoSimpleConstants.PROGRAM_NAME + " Version: "+ApoSimpleConstants.VERSION, displayConfiguration);
        final ApoSubGame subGame = new ApoSimplePanel(screen);
        final ApoLibraryGame game = new ApoLibraryGame(subGame);
        game.getScreen().init();
        subGame.init();
        game.start();
    }

}
