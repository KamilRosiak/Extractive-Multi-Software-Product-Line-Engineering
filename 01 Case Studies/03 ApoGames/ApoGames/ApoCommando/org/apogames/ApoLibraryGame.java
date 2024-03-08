package org.apogames;

import org.apogames.ApoScreen;
import org.apogames.ApoSubGame;


/**
 * Spielstartklasse
 * @author Dirk Aporius
 *
 */
public final class ApoLibraryGame extends Thread {

    /** SubGame ID für das Spiel */
    public static final int GAME = 0;
    /** SubGame ID fürs Beenden des Spiels */
    public static final int EXIT = -1;

    /** nächstes SubGame wird mit dieser Variable gesetzt */
    private int nextGameID = ApoLibraryGame.GAME;
    /** Screenobjekt des Spiel */
    private ApoScreen screen = null;
    
    private final ApoSubGame game;

    /**
     * Konstruktor
     * @param title : Titel des Spiels
     * @param displayConfiguration : Displaykonfigurationen
     */
    public ApoLibraryGame(final ApoSubGame game) {
        super();
        this.game = game;
        this.screen = this.game.getScreen();//new ApoScreen(title, displayConfiguration);
    }

    /** Spiel startet hier */
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
    
    /**
     * gibt den eigentlichen Screen des Spiels zurück
     * @return gibt den eigentlichen Screen des Spiels zurück
     */
    public final ApoScreen getScreen() {
    	return this.screen;
    }

	/**
	 * mit dieser Methode kann zwischen den einzelnen Subgames hin und hergeschaltet werden
	 * @param gameID : GameID für das jeweilige SubGameObjekt
	 * @return das neue SubGameObjekt
	 * @throws InterruptedException
	 */
    private ApoSubGame selectGame(int gameID) throws InterruptedException {
        switch (gameID) {
            case ApoLibraryGame.GAME:	return this.game;
            default:					return null;
        }
    }
}
