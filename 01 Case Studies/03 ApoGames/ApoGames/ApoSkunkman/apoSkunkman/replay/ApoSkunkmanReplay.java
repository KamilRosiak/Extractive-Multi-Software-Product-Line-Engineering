package apoSkunkman.replay;

import java.util.ArrayList;

import apoSkunkman.ApoSkunkmanConstants;
import apoSkunkman.entity.ApoSkunkmanBush;
import apoSkunkman.entity.ApoSkunkmanPlayer;
import apoSkunkman.game.ApoSkunkmanEditorIO;
import apoSkunkman.level.ApoSkunkmanLevel;

/**
 * Klasse, die sich um das Replay kümmert und sowohl die Schritte speichert als auch wiedergeben kann
 * @author Dirk Aporius
 *
 */
public class ApoSkunkmanReplay {
	
	/** Konstante die angibt, dass der Spieler steht und keine Bombe legt */
	public static final byte STAND_NO_LAY = 0;
	/** Konstante die angibt, dass der Spieler steht und eine Bombe legt */
	public static final byte STAND_LAY = 1;
	/** Konstante die angibt, dass der Spieler nach links geht und keine Bombe legt */
	public static final byte LEFT = 2;
	/** Konstante die angibt, dass der Spieler nach links geht und eine Bombe legt */
	public static final byte LEFT_LAY = 3;
	/** Konstante die angibt, dass der Spieler nach rechts geht und keine Bombe legt */
	public static final byte RIGHT = 4;
	/** Konstante die angibt, dass der Spieler nach rechts geht und eine Bombe legt */
	public static final byte RIGHT_LAY = 5;
	/** Konstante die angibt, dass der Spieler nach oben geht und keine Bombe legt */
	public static final byte UP = 6;
	/** Konstante die angibt, dass der Spieler nach oben geht und eine Bombe legt */
	public static final byte UP_LAY = 7;
	/** Konstante die angibt, dass der Spieler nach unten geht und keine Bombe legt */
	public static final byte DOWN = 8;
	/** Konstante die angibt, dass der Spieler nach unten geht und eine Bombe legt */
	public static final byte DOWN_LAY = 9;
	
	/** den Leveltypus */
	private int type;
	/** der Zufallswert für die Levelerstellung */
	private long random;
	/** boolean Variable die angibt, ob ein Level mit oder ohne Büsche gespielt wurde */
	private boolean bBush;
	/** Integervariable mit der Angabe der Zeit, die im Level zur Verfügung steht in Millisekunden */
	private int time;
	/** Integervariable zur Anzahl der Spieler */
	private int players;
	/** String der angibt, wo das Editorlevel liegt */
	private String editorLevelString;
	/** Array mit den ganzen gespeicherten Informationen zu den Bewegungen */
	private ArrayList<Byte>[] playerArray;
	/** integerarray mit den Angaben welcher Step gerade ausgeführt wird*/
	private int[] step;
	/** Stringarray mit den Spielernamen*/
	private String[] names;
	/** Hilfsobjekt */
	private ApoSkunkmanReplayHelp help;
	
	public ApoSkunkmanReplay(int type, long random, boolean bBush, int time, int players, String[] names) {
		this(type, random, bBush, time, players, null, names);
	}
	
	@SuppressWarnings("unchecked")
	public ApoSkunkmanReplay(int type, long random, boolean bBush, int time, int players, String editorLevel, String[] names) {
		this.type = type;
		this.random = random;
		this.bBush = bBush;
		this.time = time;
		this.players = players;
		this.editorLevelString = editorLevel;
		if (this.editorLevelString == null) {
			this.editorLevelString = "";
		}
		
		if (this.playerArray == null) {
			this.playerArray = new ArrayList[ApoSkunkmanConstants.PLAYER_MAX_PLAYER];
			for (int i = 0; i < this.playerArray.length; i++) {
				this.playerArray[i] = new ArrayList<Byte>();
			}
		} else {
			for (int i = 0; i < this.playerArray.length; i++) {
				this.playerArray[i].clear();
			}
		}
		this.step = new int[ApoSkunkmanConstants.PLAYER_MAX_PLAYER];
		this.names = new String[ApoSkunkmanConstants.PLAYER_MAX_PLAYER];
		for (int i = 0; i < this.names.length; i++) {
			this.names[i] = "";
		}
		for (int i = 0; i < this.names.length && i < names.length; i++) {
			this.names[i] = names[i];
		}
	}
	
	/**
	 * gibt einen Klon des Replayobjektes zurück
	 * @return gibt einen Klon des Replayobjektes zurück
	 */
	public final ApoSkunkmanReplay clone() {
		ApoSkunkmanReplay replayClone = new ApoSkunkmanReplay(this.type, this.random, this.bBush, this.time, this.players,this.editorLevelString, this.names);
		
		for (int i = 0; i < this.playerArray.length; i++) {
			for (int j = 0; j < this.playerArray[i].size(); j++) {
				replayClone.getPlayerArray()[i].add(this.playerArray[i].get(j));
			}
		}
		if (this.help != null) {
			replayClone.setHelp(this.help.clone());
		}
		
		return replayClone;
	}
	
	/**
	 * erstellt aus dem Hilfsobjekt das Level
	 * @param level : Level
	 */
	public final void makeLevelFromHelp(ApoSkunkmanLevel level) {
		if (this.help != null) {
			if (level.getGame().getEditor().getLevel() == null) {
				level.getGame().getEditor().setEditorLevel();
				level.getGame().getEditor().makeEditorLevel();
			}
			level.setType(ApoSkunkmanConstants.LEVEL_TYPE_EDITOR);
			level.setTime(this.help.getTime());
			for (int i = 0; i < ApoSkunkmanConstants.PLAYER_MAX_PLAYER; i++) {
				level.getPlayers()[i].setX(this.help.getPlayerArray()[i].x * ApoSkunkmanConstants.TILE_SIZE);
				level.getPlayers()[i].setY(this.help.getPlayerArray()[i].y * ApoSkunkmanConstants.TILE_SIZE);
			}
			for (int y = 1; y < level.getLevel().length - 1; y++) {
				for (int x = 1; x < level.getLevel()[0].length - 1; x++) {
					byte levelType = this.help.getLevelArray()[y][x][0];

					if (levelType == ApoSkunkmanEditorIO.FREE) {
						level.getLevel()[y][x] = null;
					} else if (levelType == ApoSkunkmanEditorIO.STONE) {
						level.getLevel()[y][x] = level.makeStone(x, y);
					} else if (levelType == ApoSkunkmanEditorIO.BUSH) {
						int goodie = this.help.getLevelArray()[y][x][1];
						ApoSkunkmanBush levelBush = level.makeBush(x, y, goodie);
						level.getLevel()[y][x] = levelBush;
					}
				}
			}
			level.getGoalX().x = (this.help.getGoalX().x);
			level.getGoalX().y = (this.help.getGoalX().y);
			level.getGame().getEditor().makeEditorLevel();
			level.getGame().getEditor().setEditorLevel();
		}
	}

	/**
	 * gibt das Hilfsobjekt mit den Leveldaten zurück
	 * @return gibt das Hilfsobjekt mit den Leveldaten zurück
	 */
	public final ApoSkunkmanReplayHelp getHelp() {
		return this.help;
	}

	/**
	 * setzt das Hilfsobjekt mit den Leveldaten auf das Übergebene
	 * @param help : neues Hilfsobjekt mit den Leveldaten
	 */
	public void setHelp(final ApoSkunkmanReplayHelp help) {
		this.help = help;
	}

	/**
	 * gibt ein Array mit den Spielernamen zurück
	 * @return gibt ein Array mit den Spielernamen zurück
	 */
	public final String[] getNames() {
		return this.names;
	}

	/**
	 * gibt den Leveltypus zurück
	 * @return gibt den Leveltypus zurück
	 */
	public final int getType() {
		return this.type;
	}

	/**
	 * gibt den Zufallswert für die Levelerstellung wieder
	 * @return gibt den Zufallswert für die Levelerstellung wieder
	 */
	public final long getRandom() {
		return this.random;
	}

	/**
	 * gibt zurück, ob mit oder ohne Busch gespielt wurde
	 * @return TRUE mit Büschen ansonsten FALSE
	 */
	public final boolean isBBush() {
		return this.bBush;
	}

	/**
	 * gibt zurück, wieviel Zeit zur Verfügung steht
	 * @return gibt zurück, wieviel Zeit zur Verfügung steht
	 */
	public final int getTime() {
		return this.time;
	}

	/**
	 * gibt zurück wieviel Spieler maximal mitspielen
	 * @return gibt zurück wieviel Spieler maximal mitspielen
	 */
	public final int getPlayers() {
		return this.players;
	}

	/**
	 * String der angibt, wo das Editorlevel liegt
	 * @return String der angibt, wo das Editorlevel liegt
	 */
	public final String getEditorLevelString() {
		return this.editorLevelString;
	}

	/**
	 * setzt den Editornamensstring auf den übergebenen
	 * @param editorLevelString
	 */
	public void setEditorLevelString(String editorLevelString) {
		this.editorLevelString = editorLevelString;
	}

	/**
	 * gibt das Array mit den ganzen gespeicherten Informationen zu den Bewegungen zurück
	 * @return gibt das Array mit den ganzen gespeicherten Informationen zu den Bewegungen zurück
	 */
	public final ArrayList<Byte>[] getPlayerArray() {
		return this.playerArray;
	}
	
	/**
	 * setzt das Array mit den ganzen gespeicherten Informationen zu den Bewegungen auf den übergebenen Wert
	 * @param playerArray : neues Array mit den ganzen gespeicherten Informationen zu den Bewegungen
	 */
	public void setPlayerArray(final ArrayList<Byte>[] playerArray) {
		this.playerArray = playerArray;
	}

	/**
	 * wird beim eigentlichen Spielen aufgezeichnet<br />
	 * wird nach jedem Thinkaufruf aufgerufen<br />
	 * @param player : der eigentliche Spieler
	 * @param nextDirection : seine nächste Richtung
	 * @param bLayDown : boolean Variable, ob er eine Bombe legt
	 */
	public final void setStep(final int player, final int nextDirection, final boolean bLayDown) {
		if (nextDirection == ApoSkunkmanConstants.PLAYER_DIRECTION_DOWN) {
			if (bLayDown) {
				this.playerArray[player].add(ApoSkunkmanReplay.DOWN_LAY);
			} else {
				this.playerArray[player].add(ApoSkunkmanReplay.DOWN);
			}
		} else if (nextDirection == ApoSkunkmanConstants.PLAYER_DIRECTION_UP) {
			if (bLayDown) {
				this.playerArray[player].add(ApoSkunkmanReplay.UP_LAY);
			} else {
				this.playerArray[player].add(ApoSkunkmanReplay.UP);
			}
		} else if (nextDirection == ApoSkunkmanConstants.PLAYER_DIRECTION_LEFT) {
			if (bLayDown) {
				this.playerArray[player].add(ApoSkunkmanReplay.LEFT_LAY);
			} else {
				this.playerArray[player].add(ApoSkunkmanReplay.LEFT);
			}
		} else if (nextDirection == ApoSkunkmanConstants.PLAYER_DIRECTION_RIGHT) {
			if (bLayDown) {
				this.playerArray[player].add(ApoSkunkmanReplay.RIGHT_LAY);
			} else {
				this.playerArray[player].add(ApoSkunkmanReplay.RIGHT);
			}
		} else {
			if (bLayDown) {
				this.playerArray[player].add(ApoSkunkmanReplay.STAND_LAY);
			} else {
				this.playerArray[player].add(ApoSkunkmanReplay.STAND_NO_LAY);
			}
		}
	}
	
	/**
	 * wird aufgerufen, wenn ein Replay abgespielt wird und veranlasst den Spieler zu laufen usw.
	 * @param level : Das Levelobjekt
	 * @param playerInt : Der Spieler um den es geht
	 */
	public final void receiveStep(ApoSkunkmanLevel level, int playerInt) {
		if (this.step[playerInt] >= this.playerArray[playerInt].size()) {
			System.out.println("Replaybug or old version replay");
		} else {
			int nextStep = this.playerArray[playerInt].get(this.step[playerInt]);
			ApoSkunkmanPlayer player = level.getPlayers()[playerInt];
			if ((nextStep == ApoSkunkmanReplay.DOWN) || (nextStep == ApoSkunkmanReplay.DOWN_LAY)) {
				player.setNextDirection(ApoSkunkmanConstants.PLAYER_DIRECTION_DOWN);
			} else if ((nextStep == ApoSkunkmanReplay.UP) || (nextStep == ApoSkunkmanReplay.UP_LAY)) {
				player.setNextDirection(ApoSkunkmanConstants.PLAYER_DIRECTION_UP);
			} else if ((nextStep == ApoSkunkmanReplay.LEFT) || (nextStep == ApoSkunkmanReplay.LEFT_LAY)) {
				player.setNextDirection(ApoSkunkmanConstants.PLAYER_DIRECTION_LEFT);
			} else if ((nextStep == ApoSkunkmanReplay.RIGHT) || (nextStep == ApoSkunkmanReplay.RIGHT_LAY)) {
				player.setNextDirection(ApoSkunkmanConstants.PLAYER_DIRECTION_RIGHT);
			}
			if ((nextStep == ApoSkunkmanReplay.STAND_LAY) || (nextStep == ApoSkunkmanReplay.DOWN_LAY) ||
				(nextStep == ApoSkunkmanReplay.UP_LAY) || (nextStep == ApoSkunkmanReplay.LEFT_LAY) ||
				(nextStep == ApoSkunkmanReplay.RIGHT_LAY)) {
				player.setLaySkunkman(true);
			} else {
				player.setLaySkunkman(false);
			}
			this.step[playerInt] += 1;
		}
	}
	
}
