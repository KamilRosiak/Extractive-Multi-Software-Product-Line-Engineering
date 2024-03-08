package apoMarc;

import org.apogames.ApoDisplayConfiguration;
import org.apogames.ApoScreen;
import org.apogames.ApoSubGame;

import apoMarc.game.ApoMarcPanel;

public final class ApoLibraryGame extends Thread {

    /* SubGame IDs */
    public static final int GAME = 0;
    public static final int EXIT = -1;

    private int nextGameID = ApoLibraryGame.GAME;
    private ApoDisplayConfiguration displayConfiguration = null;
    private ApoScreen screen = null;

    public ApoLibraryGame(String title, ApoDisplayConfiguration displayConfiguration) {
        super();
        this.displayConfiguration = displayConfiguration;
        this.screen = new ApoScreen(title, displayConfiguration);
    }

    /* Game starts here */
    @Override
    public void run() {
        while (this.nextGameID != -1) {
            try {
                ApoSubGame subGame = selectGame(this.nextGameID);
                if (subGame == null) {
                    this.nextGameID = -1;
                } else {
                    subGame.start();
                    subGame.join();
                    this.nextGameID = subGame.getNextID();
                }
            } catch (InterruptedException e) {
            }
        }
    }
    
    public final ApoScreen getScreen() {
    	return this.screen;
    }

    public final ApoDisplayConfiguration getDisplayConfiguration() {
		return this.displayConfiguration;
	}

	/* SubGame selection */
    private ApoSubGame selectGame(int gameID) throws InterruptedException {
        switch (gameID) {
            case ApoLibraryGame.GAME:	return new ApoMarcPanel(this.screen);
            default:					return null;
        }
    }
}
