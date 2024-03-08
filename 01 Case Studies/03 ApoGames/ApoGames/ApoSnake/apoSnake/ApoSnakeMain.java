package apoSnake;

import org.apogames.ApoDisplayConfiguration;
import org.apogames.ApoLibraryGame;
import org.apogames.ApoScreen;
import org.apogames.ApoSubGame;

import apoSnake.game.ApoSnakePanel;

public class ApoSnakeMain {

	/**
	 * Startpunkt des Spiels
	 * es werden die Properties geladen und dann das Spiel gestartet
	 * @param args : übergebenes Stringarray
	 */
    public static void main(String[] args) {
    	new ApoSnakeMain();
    }
    
    /**
     * Konstruktor in dem das Spiel gestartet wird
     */
    public ApoSnakeMain() {
        ApoDisplayConfiguration displayConfiguration = new ApoDisplayConfiguration(ApoSnakeConstants.GAME_WIDTH, ApoSnakeConstants.GAME_HEIGHT, 16, true, false);

        final ApoScreen screen = new ApoScreen(ApoSnakeConstants.PROGRAM_NAME + " Version: "+ApoSnakeConstants.VERSION, displayConfiguration);
        final ApoSubGame subGame = new ApoSnakePanel(screen);
        final ApoLibraryGame game = new ApoLibraryGame(subGame);
        game.getScreen().init();
        subGame.init();
        game.start();
    }

}
