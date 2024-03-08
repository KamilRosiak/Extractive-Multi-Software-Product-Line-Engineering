package apoSkunkman.level;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import org.apogames.ApoConstants;
import org.apogames.help.ApoHelp;

import apoSkunkman.ApoSkunkmanConstants;
import apoSkunkman.ApoSkunkmanImageContainer;
import apoSkunkman.entity.ApoSkunkmanBush;
import apoSkunkman.entity.ApoSkunkmanEntity;
import apoSkunkman.entity.ApoSkunkmanFire;
import apoSkunkman.entity.ApoSkunkmanGoodie;
import apoSkunkman.entity.ApoSkunkmanPlayer;
import apoSkunkman.entity.ApoSkunkmanSkunkman;
import apoSkunkman.entity.ApoSkunkmanStone;
import apoSkunkman.game.ApoSkunkmanPanel;
import apoSkunkman.replay.ApoSkunkmanReplay;

/**
 * Levelklasse, die das eigentliche Level repräsentiert mit Spielern und allen anderen wichtigen Sachen und <br />
 * deren Sachen handelt und zeichnet
 * @author Dirk Aporius
 *
 */
public class ApoSkunkmanLevel {
	/** Spielobjekt, um mit der eigentlichen Hauptklasse zu kommunizieren */
	private final ApoSkunkmanPanel game;
	/** Levelarray mit Steinen, Büschen, Goodies und den Skunkmans */
	private ApoSkunkmanEntity[][] level;
	/** Array mit den Spielern */
	private ApoSkunkmanPlayer[] players;
	/** Integervariable für die Zeit */
	private int time;
	/** Integervariable für die aktuelle Zeit */
	private int curTime;
	/** Integervariable für den Leveltypen, ob nun Standardlevel oder Level, wo man ein Zielpunkt erreichen muss */
	private int type;
	/** Longvariable, die der Ausgangspunkt zur Erstellung und Verteilung der Zufallsbüsche und Goodies ist */
	private long randomValue;
	/** das eigentliche Randomobjekt */
	private Random random;
	/** eine ArrayList mit den ganzen Feuern drin für die Anzeige */
	private ArrayList<ApoSkunkmanFire> fire;
	/** Integervariable mit der Angabe wieviel Spieler maximal teilnehmen */
	private int maxPlayers;
	/** boolean Variable, ob das Spiel beendet wurde, weil nur noch ein Spieler lebt */
	private boolean bWin;
	/** boolean-Variable, ob gerade Pause ist oder nicht im Spiel */
	private boolean bPause;
	/** Hilfsarray was aus Objekten besteht, wo der Levelname, minimale und maximale Spieleranzahl angegeben ist */
	private ApoSkunkmanTypeHelp[] typeHelp;
	/** boolean Variable die angibt, ob ein Level mit oder ohne Büsche erstellt werden soll */
	private boolean bBush;
	/** Punkt Variable die angibt, wo sich das Ziel befindet */	
	private Point goalX;
	/** Integervariable die angibt, welcher Spieler gewonnen hat */
	private int winner;
	/** Integervariable die angibt, wieviel Zeit vergangen bis zum nächsten Punkt für die Spieler */
	private int curTimePoints;
	/** Integervariable die angibt, wieviel Zeit vergehen muss, damit falls die Levelzeit schon abgelaufen ist, neue Steine ins Feld gesetzt werden */
	private int curFillTime;
	/** Integervariable die angibt, wie oft in eine Richtung gegangen werden muss bevor die Richtung wieder verändert wird beim Steinzuwachs, falls die Levelzeit schon abgelaufen ist */
	private int curFillMaxWidth;
	/** Integervariable die angibt, wie oft schon in eine Richtung gegangen worden ist beim Steinzuwachs, falls die Levelzeit schon abgelaufen ist */
	private int curFillWidth;
	/** Pointvariable die angibt, welcher Punkt gerade mit Steinen gefüllt wird, falls die Levelzeit schon abgelaufen ist */
	private Point curFillPoint;
	/** Integervariable die angibt, in welche Richtung der Steinzuwachs geht, falls die Levelzeit schon abgelaufen ist */
	private int curFillDirection;
	/** Integervariable die angibt, wie oft schon mit der gleichen Länge die Steine gesetzt wurden, falls die Levelzeit schon abgelaufen ist */
	private int curFillWidthCounter;
	/** Integervariable die angibt, wie schnell ein Spiel ablaufen soll */
	private int speedMode;
	/** Replayobjekt */
	private ApoSkunkmanReplay replay;
	/** boolean Variable die angibt, ob gerade ein Replay abgespielt wird oder nicht */
	private boolean bReplay;
	/** wird benötigt für die unterschiedlichen Schnelligkeitsstufen des Spiels */
	private int curDelta;
	/** gibt den Pfad zum Editorlevel an */
	private String editorString;
	/** ArrayList f�r die thinkTime der Spieler */
	private ArrayList<Long>[] thinkTime;
	
	private int goalXEasy, goalYEasy;
	/** boolean Variable die angibt, ob ein Spiel verloren wurde oder nicht */
	private boolean bLoose;
	
	/**
	 * Konstruktor
	 * @param game : Das Gameobjekt der Hauptklasse für das Spiel
	 */
	public ApoSkunkmanLevel(final ApoSkunkmanPanel game) {
		this.game = game;
	}
	
	/**
	 * wird beim initialisieren aufgerufen und setzt die Standardwerte für die Variablen
	 */
	@SuppressWarnings("unchecked")
	public void init() {
		if (this.goalX == null) {
			this.goalX = new Point(-1, -1);
		}
		this.bLoose = false;

		this.goalXEasy = ApoHelp.getRandomValue(9, 4);
		this.goalYEasy = ApoHelp.getRandomValue(9, 4);
		// erstelle ein Hilfsarray mit den Angaben zu den einzelnen Levels
		if (this.typeHelp == null) {
			this.typeHelp = new ApoSkunkmanTypeHelp[9];
			this.typeHelp[0] = new ApoSkunkmanTypeHelp("Standard-Level", 2, 4, false, new Point(-1, -1));
			this.typeHelp[1] = new ApoSkunkmanTypeHelp("GoalX-Level", 1, 4, true, new Point(13, 13));
			this.typeHelp[2] = new ApoSkunkmanTypeHelp("Editor", 1, 4, false, new Point(-1, -1));
			this.typeHelp[3] = new ApoSkunkmanTypeHelp("Second-Level", 2, 4, false, new Point(-1, -1));
			this.typeHelp[4] = new ApoSkunkmanTypeHelp("Third-Level", 2, 4, false, new Point(-1, -1));
			this.typeHelp[5] = new ApoSkunkmanTypeHelp("Prim-Level", 1, 4, true, new Point(7, 7));
			this.typeHelp[6] = new ApoSkunkmanTypeHelp("DeadEnd-Level", 2, 4, false, new Point(-1, -1));
			this.typeHelp[7] = new ApoSkunkmanTypeHelp("Little-Level", 2, 4, false, new Point(-1, -1));
			this.typeHelp[8] = new ApoSkunkmanTypeHelp("Easy-Level", 1, 4, true, new Point(-10, -10));
		}
		if (this.level == null) {
			this.level = new ApoSkunkmanEntity[ApoSkunkmanConstants.LEVEL_HEIGHT][ApoSkunkmanConstants.LEVEL_WIDTH];
		}
		// erstelle die Spieler
		if (this.players == null) {
			this.players = new ApoSkunkmanPlayer[ApoSkunkmanConstants.PLAYER_MAX_PLAYER];
			this.players[0] = new ApoSkunkmanPlayer(ApoSkunkmanImageContainer.iPlayerOne, 1 * ApoSkunkmanConstants.TILE_SIZE, 1 * ApoSkunkmanConstants.TILE_SIZE, 1 * ApoSkunkmanConstants.TILE_SIZE, 1 * ApoSkunkmanConstants.TILE_SIZE, 0);
			this.players[1] = new ApoSkunkmanPlayer(ApoSkunkmanImageContainer.iPlayerTwo, (this.level[0].length - 2) * ApoSkunkmanConstants.TILE_SIZE, (this.level.length - 2) * ApoSkunkmanConstants.TILE_SIZE, 1 * ApoSkunkmanConstants.TILE_SIZE, 1 * ApoSkunkmanConstants.TILE_SIZE, 1);
			this.players[2] = new ApoSkunkmanPlayer(ApoSkunkmanImageContainer.iPlayerThree, 1 * ApoSkunkmanConstants.TILE_SIZE, (this.level.length - 2) * ApoSkunkmanConstants.TILE_SIZE, 1 * ApoSkunkmanConstants.TILE_SIZE, 1 * ApoSkunkmanConstants.TILE_SIZE, 2);
			this.players[3] = new ApoSkunkmanPlayer(ApoSkunkmanImageContainer.iPlayerFour, (this.level[0].length - 2) * ApoSkunkmanConstants.TILE_SIZE, 1 * ApoSkunkmanConstants.TILE_SIZE, 1 * ApoSkunkmanConstants.TILE_SIZE, 1 * ApoSkunkmanConstants.TILE_SIZE, 3);
			
			this.maxPlayers = ApoSkunkmanConstants.LEVEL_PLAYERS;
		}
		this.speedMode = ApoSkunkmanConstants.SPEED_OPTIONS_NORMAL;
		this.bWin = false;
		this.bBush = ApoSkunkmanConstants.LEVEL_BUSH;
		if (this.fire == null) {
			this.fire = new ArrayList<ApoSkunkmanFire>();
		}
		this.setTime(ApoSkunkmanConstants.LEVEL_LASTTIME);
		this.bPause = false;
		this.fire.clear();
		this.setType(ApoSkunkmanConstants.LEVEL_TYPE);
		this.randomValue = ApoSkunkmanConstants.LEVEL_LASTRANDOM;
		this.makeNewLevel(this.randomValue);
		// setze die Zeit, die die nachdenkzeit der Spieler speichert auf leer
		if (this.thinkTime == null) {
			this.thinkTime = new ArrayList[ApoSkunkmanConstants.PLAYER_MAX_PLAYER];
			for (int i = 0; i < this.thinkTime.length; i++) {
				this.thinkTime[i] = new ArrayList<Long>();
			}
		}
		for (int i = 0; i < this.thinkTime.length; i++) {
			this.thinkTime[i].clear();
		}
		this.setReplay();
		this.setVisibleButtonsForPlayer();
	}
	
	/**
	 * gibt das Spielobjekt zur�ck
	 * @return gibt das Spielobjekt zur�ck
	 */
	public final ApoSkunkmanPanel getGame() {
		return this.game;
	}

	/**
	 * gibt den Pfad zum aktuellen Editorlevel zur�ck
	 * @return gibt den Pfad zum aktuellen Editorlevel zur�ck
	 */
	public final String getEditorString() {
		return this.editorString;
	}

	/**
	 * setzt den Pfad zum Editorlevel auf den �bergebenen
	 * @param editorString : neuer Pfad zum Editorlevel
	 */
	public void setEditorString(final String editorString) {
		this.editorString = editorString;
	}

	/**
	 * gibt den Zielpunkt zur�ck<br />
	 * besteht aus x = -1, y = -1, wenn es keinen Zielpunkt gibt
	 * @return gibt den Zielpunkt zur�ck<br />
	 */
	public final Point getGoalX() {
		return this.goalX;
	}

	/**
	 * gibt zurück, ob gerade ein Replay abgespielt wird oder nicht
	 * @return TRUE Replay wird gerade abgespielt, ansonsten FALSE
	 */
	public final boolean isReplayShowing() {
		return this.bReplay;
	}

	/**
	 * setzt den Wert, ob ein Replay gerade abgespielt wird oder nicht, auf den übergebenen
	 * @param replay : TRUE Replay wird gerade abgespielt, ansonsten FALSE
	 */
	public void setReplayShowing(final boolean replay) {
		this.bReplay = replay;
	}

	/**
	 * gibt das Replayobjekt zurück
	 * @return gibt das Replayobjekt zurück
	 */
	public final ApoSkunkmanReplay getReplay() {
		return this.replay;
	}

	/**
	 * setzt das Replayobjekt auf das Übergebene
	 * @param replay : neues Replay
	 */
	public void setReplay(final ApoSkunkmanReplay replay) {
		this.replay = replay;
	}

	/**
	 * gibt zurück, welcher Speedmodus gerade eingeschaltet ist
	 * @return gibt zurück, welcher Speedmodus gerade eingeschaltet ist
	 */
	public int getSpeedMode() {
		return speedMode;
	}

	/**
	 * setzt den Speedmodus auf den übergebenen
	 * @param speedMode : neuer Speedmodus
	 */
	public void setSpeedMode(int speedMode) {
		if (speedMode >= ApoSkunkmanConstants.SPEED_OPTIONS.length) {
			speedMode = 0;
		} else if (speedMode < 0) {
			speedMode = ApoSkunkmanConstants.SPEED_OPTIONS.length - 1;
		}
		this.speedMode = speedMode;
	}

	/**
	 * gibt zurück, wieviel Zeit noch für dieses Level zur Verfügung steht
	 * @return gibt zurück, wieviel Zeit noch für dieses Level zur Verfügung steht
	 */
	public final int getCurTime() {
		return this.curTime;
	}
	
	/**
	 * gibt zurück, wieviel Zeit für dieses Level insgesamt zur Verfügung steht bzw stand
	 * @return gibt zurück, wieviel Zeit für dieses Level insgesamt zur Verfügung steht bzw stand
	 */
	public final int getTime() {
		return this.time;
	}

	/**
	 * setzt die Variable, wieviel Zeit im Level zur Verfügung steht auf den übergebenen Wert<br />
	 * Die Zeit muss zwischen 5 Sekunden und 4 Minuten liegen bevor das Level kleiner wird
	 * @param time : neue Zeit
	 */
	public void setTime(int time) {
		if (time < ApoSkunkmanConstants.TIME_MIN) {
			time = ApoSkunkmanConstants.TIME_MIN;
		} if (time > ApoSkunkmanConstants.TIME_MAX + ApoSkunkmanConstants.TIME_CHANGE) {
			time = ApoSkunkmanConstants.TIME_MAX + ApoSkunkmanConstants.TIME_CHANGE;
		}
		ApoSkunkmanConstants.LEVEL_LASTTIME = time;
		this.time = time;
		this.curTime = this.time;
	}

	/**
	 * gibt zurück, ob ein Spiel pausiert ist oder nicht
	 * @return TRUE Spiel ist pausiert, FALSE Spiel läuft normal
	 */
	public boolean isPause() {
		return this.bPause;
	}

	/**
	 * setzt den booleanWert, ob gerade Pause im Spiel ist oder nicht, auf den übergebenen
	 * @param pause : TRUE Spiel ist dann pausiert, FALSE Spiel läuft normal weiter
	 */
	public void setPause(boolean pause) {
		this.bPause = pause;
	}

	/**
	 * gibt zurück, ob jemand ein Level gewonnen hat oder das Spiel noch läuft
	 * @return TRUE Jemand hat das Spiel gewonnen, else FALSE SPiel läuft noch
	 */
	public final boolean isWin() {
		return this.bWin;
	}
	
	/**
	 * gibt zurück, ob ein Level mit oder ohne Büsche erstellt werden soll
	 * @return TRUE ein Level mit Büsche soll erstellt werden else FALSE
	 */
	public final boolean isBush() {
		return this.bBush;
	}

	/**
	 * setzt den booleanWert, ob ein Level mit oder ohne Büsche erstellt werden soll, auf den Übergebenen
	 * @param bush : TRUE ein Level mit Büsche soll erstellt werden else FALSE 
	 */
	public void setBush(final boolean bush, final boolean bNewLevel) {
		this.bBush = bush;
		ApoSkunkmanConstants.LEVEL_BUSH = bush;
		if (bNewLevel) {
			this.makeNewLevel(this.randomValue);
			this.setVisibleButtonsForPlayer();
		}
	}

	/**
	 * gibt das Hilfsarray, was aus Objekten besteht, wo der Levelname, minimale und maximale Spieleranzahl angegeben ist, zurück
	 * @return gibt das Hilfsarray, was aus Objekten besteht, wo der Levelname, minimale und maximale Spieleranzahl angegeben ist, zurück
	 */
	public final ApoSkunkmanTypeHelp[] getTypeHelp() {
		return this.typeHelp;
	}

	/**
	 * gibt zurück, wieviel Spieler maximal teilnehmen
	 * @return gibt zurück, wieviel Spieler maximal teilnehmen
	 */
	public final int getMaxPlayers() {
		return this.maxPlayers;
	}

	/**
	 * setzt den Wert, wieviel Spieler maximal teilnehmen auf den übergebenen Wert
	 * @param maxPlayers : neue Anzahl an Spielern
	 */
	public void setMaxPlayers(int maxPlayers) {
		if (maxPlayers < this.typeHelp[this.type].getMinPlayers()) {
			maxPlayers = this.typeHelp[this.type].getMinPlayers();
		} else if (maxPlayers > this.typeHelp[this.type].getMaxPlayers()) {
			maxPlayers = this.typeHelp[this.type].getMaxPlayers();
		}
		if (this.maxPlayers != maxPlayers) {
			this.goalXEasy = ApoHelp.getRandomValue(9, 4);
			this.goalYEasy = ApoHelp.getRandomValue(9, 4);
		}
		
		ApoSkunkmanConstants.LEVEL_PLAYERS = maxPlayers;
		this.maxPlayers = maxPlayers;
		this.makeNewLevel(this.randomValue);
		this.setVisibleButtonsForPlayer();
	}
	
	/**
	 * setzt die Sichtbarkeit der Buttons anhand der maximalen Spieleranzahl
	 */
	public void setVisibleButtonsForPlayer() {
		for (int i = 7; i < 19; i++) {
			this.game.getButtons()[i].setBVisible(false);
		}
		for (int i = 7; i < 7 + this.maxPlayers * 3; i++) {
			this.game.getButtons()[i].setBVisible(true);
		}
		if (ApoConstants.B_APPLET) {
			for (int i = 7; i < 7 + this.maxPlayers * 3; i += 3) {
				this.game.getButtons()[i].setBVisible(false);
			}
		}
	}

	/**
	 * startet ein Level neu mit den alten Werten
	 */
	public void restart() {
		this.makeNewLevel(this.randomValue);
	}
	
	/**
	 * erstellt ein kopmplett leeres Level, ist Schritt 1 bei der Levelerstellung
	 * @param levelGenerationType : Integervariable die angibt, welcher Typ von Level erstellt werden soll
	 * @param randomValue : Zufallszahl als Basis f�r die Erstellung des Levels
	 */
	private void makeReallyEmptyLevel(final int levelGenerationType, final long randomValue) {
		// erstmal alles resetten
		this.fire.clear();
		for (ApoSkunkmanPlayer player : this.players) {
			player.init();
		}
		
		// neues Level erstellen mit der Grundstruktur der Steine
		this.level = new ApoSkunkmanEntity[ApoSkunkmanConstants.LEVEL_HEIGHT][ApoSkunkmanConstants.LEVEL_WIDTH];
		BufferedImage iStone = this.getStoneImage();
		for (int x = 0; x < this.level[0].length; x++) {
			this.level[0][x] = new ApoSkunkmanStone(iStone, x * ApoSkunkmanConstants.TILE_SIZE, 0 * ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE);
			this.level[this.level.length - 1][x] = new ApoSkunkmanStone(iStone, x * ApoSkunkmanConstants.TILE_SIZE, (this.level.length - 1) * ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE);
		}
		for (int y = 1; y < this.level.length - 1; y++) {
			this.level[y][0] = new ApoSkunkmanStone(iStone, 0 * ApoSkunkmanConstants.TILE_SIZE, y * ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE);
			this.level[y][this.level[0].length - 1] = new ApoSkunkmanStone(iStone, (this.level[0].length - 1) * ApoSkunkmanConstants.TILE_SIZE, y * ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE);
		}
		
		// setzte die anderen Variablen auf die Werte f�r die Standardlevel
		this.makeDefaultVariables(randomValue);
		this.setType(levelGenerationType);
	}
		
	/**
	 * erstellt auf Basis der �bergebenen Variablen ein Level mit Steinform mithilfe des Levelgenerators<br />
	 * @param levelGenerationType : Integervariable die angibt, welcher Typ von Level erstellt werden soll
	 * @param randomValue : Zufallszahl als Basis f�r die Erstellung des Levels
	 */
	private void makeGenerationLevelWithLeveltype(final int levelGenerationType, final long randomValue) {
		if (levelGenerationType == ApoSkunkmanConstants.LEVEL_TYPE_EDITOR) {
			return;
		}
		this.makeReallyEmptyLevel(levelGenerationType, randomValue);

		ApoSkunkmanLevelGenerator levelRandom = new ApoSkunkmanLevelGenerator(this.randomValue);
		boolean[][] maze = levelRandom.getRandomLevel(levelGenerationType);

		BufferedImage iStone = this.getStoneImage();
		for (int y = 1; y < maze.length - 1; y++) {
			for (int x = 1; x < maze[0].length - 1; x++) {
				if (!maze[y][x]) {
					this.level[y][x] = new ApoSkunkmanStone(iStone, x * ApoSkunkmanConstants.TILE_SIZE, y * ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE);
				}
			}
		}

		
		Point[] playerStartPoint = levelRandom.getPlayerStartPoints(levelGenerationType);
		// falls die Spielerstartpositionen gesetzt werden sollen, dann
		if (playerStartPoint != null) {
			for (int i = 0; i < this.players.length; i++) {
				ApoSkunkmanPlayer player = this.players[i];
				if (playerStartPoint[i] != null) {
					player.setX(playerStartPoint[i].x * ApoSkunkmanConstants.TILE_SIZE);
					player.setY(playerStartPoint[i].y * ApoSkunkmanConstants.TILE_SIZE);
				}
			}
		}
		
		// füllt das Level mit zufälligen Büschen;
		this.fillWithBush();
		
		this.makeLastValuesForANewLevel();
	}
	
	/**
	 * f�llt das Level mit B�schen und setzt die GoalXSachen wenn es der Leveltyp vorsieht und macht die Spieler frei
	 */
	private final void makeLastValuesForANewLevel() {

		// setze den Zielpunkt, falls GoalX-Level
		if (this.typeHelp[this.getType()].hasGoalX()) {
			// falls der Zielpunkt nicht gesetzt ist dann setze ihn zuf�llig 
			if (this.typeHelp[this.getType()].getGoalXPoint().x == -10) {
				this.goalX.x = this.goalXEasy;
				this.goalX.y = this.goalYEasy;
				
				this.level[this.goalX.y][this.goalX.x] = null; 
			} else if (this.typeHelp[this.getType()].getGoalXPoint().x < 0) {
				this.goalX.x = -1;
				while (this.goalX.x < 0) {
					int x = this.random.nextInt(this.level[0].length - 4) + 2;
					int y = this.random.nextInt(this.level.length - 4) + 2;
					if (this.level[y][x] == null) {
						this.goalX = new Point(x, y);
					}
				}
			} else {
				// ansonsten setze ihn auf den �bergebenen Wert und sorge daf�r das er auch erreichbar ist
				this.goalX.x = this.typeHelp[this.getType()].getGoalXPoint().x;
				this.goalX.y = this.typeHelp[this.getType()].getGoalXPoint().y;
				
				this.level[this.goalX.y][this.goalX.x] = null; 
			}
		}
		
		// ermögliche den Spielern sich zu bewegen
		if (this.goalX.x < 0) {
			this.makePlayersFree(1);
		} else {
			this.makePlayersFree(2);
		}
		// erstelle einen neuen Background auf Basis des Levels (z.B. Steintiles müssen nicht immer einzeln gezeichnet, weil sie nicht zerstört werden können) 
		this.game.makeBackground(true, false, false, false);
	}
	
	/**
	 * gibt einen Stein f�r die �bergebene Position zur�ck
	 * @param x : x-Wert
	 * @param y : y-Wert
	 * @return gibt einen Stein f�r die �bergebene Position zur�ck
	 */
	public final ApoSkunkmanStone makeStone(final int x, final int y) {
		return new ApoSkunkmanStone(this.getStoneImage(), (x) * ApoSkunkmanConstants.TILE_SIZE, y * ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE);
	}
	
	/**
	 * /**
	 * gibt einen Busch f�r die �bergebene Position zur�ck
	 * @param x : x-Wert
	 * @param y : y-Wert
	 * @param givenGoodie : falls nicht -1, dann ist es das Goodie was der Busch enth�lt, ansonsten Zufall
	 * @return gibt einen Busch f�r die �bergebene Position zur�ck	 */
	public final ApoSkunkmanBush makeBush(final int x, final int y, final int givenGoodie) {
		int goodie = givenGoodie;
		if (givenGoodie < 0) {
			goodie = ApoSkunkmanConstants.GOODIE_EMPTY;
			int goodieRandom = this.random.nextInt(100);
			if (goodieRandom < ApoSkunkmanConstants.LEVEL_GOODIE_GENERATION_STANDARD) {
				goodie = this.generateAndGetGoodie();
			}
		}
		return new ApoSkunkmanBush(this.getBushImage(), x * ApoSkunkmanConstants.TILE_SIZE, y * ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE, goodie);
	}	
	
	/**
	 * Methode, um das Level mit B�sche aufzuf�llen  
	 */
	private void fillWithBush() {
		if (this.bBush) {
			// fülle das Levle zufällig mit Büschen und fülle die Büsche mit Goodies
			BufferedImage iBush = this.getBushImage();
			for (int y = 1; y < this.level.length - 1; y += 1) {
				for (int x = 1; x < this.level[0].length - 1; x += 1) {
					if (this.level[y][x] == null) {
						int bushRandom = this.random.nextInt(100);
						if (bushRandom < ApoSkunkmanConstants.LEVEL_BUSH_GENERATION_STANDARD) {
							int goodie = ApoSkunkmanConstants.GOODIE_EMPTY;
							int goodieRandom = this.random.nextInt(100);
							if (goodieRandom < ApoSkunkmanConstants.LEVEL_GOODIE_GENERATION_STANDARD) {
								goodie = this.generateAndGetGoodie();
							}
							this.level[y][x] = new ApoSkunkmanBush(iBush, x * ApoSkunkmanConstants.TILE_SIZE, y * ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE, goodie);
						}
					}
				}
			}
		}
	}
	
	/**
	 * stellt die Variablen auf die Defaultwerte
	 * @param randomValue : neue Zufallsvariable
	 */
	private void makeDefaultVariables(final long randomValue) {
		this.bWin = false;
		this.bPause = false;
		this.curTime = this.time;
		this.goalX = new Point(-1, -1);
		this.curTimePoints = 0;
		this.game.setButtonVisible(ApoSkunkmanConstants.BUTTON_GAME);
		this.curFillDirection = ApoSkunkmanConstants.PLAYER_DIRECTION_DOWN;
		this.curFillMaxWidth = this.level.length - 1;
		this.curFillWidth = 0;
		this.curFillTime = 0;
		this.curFillPoint = new Point(1, 0);
		this.curFillWidthCounter = 1;
		this.winner = -2;
		if (randomValue == -1) {
			this.randomValue = System.nanoTime();
			this.goalXEasy = ApoHelp.getRandomValue(9, 4);
			this.goalYEasy = ApoHelp.getRandomValue(9, 4);
		} else {
			if (this.randomValue != randomValue) {
			}
			this.randomValue = randomValue;
		}
		ApoSkunkmanConstants.LEVEL_LASTRANDOM = this.randomValue;
		this.random = new Random(this.randomValue);
		this.curDelta = 0;
		
		this.setReplay();
	}
	
	private void setReplay() {
		if (!this.bReplay) {
			String names[] = new String[this.maxPlayers];
			for (int i = 0; i < names.length; i++) {
				names[i] = this.players[i].getPlayerName();
			}
			this.replay = new ApoSkunkmanReplay(this.type, this.randomValue, this.bBush, this.time, this.maxPlayers, names);
		}
	}
	
	/**
	 * erstellt ein Goodie aus Basis der Konstanten aus ApoSkunkmanConstants
	 * @return ein Integerwert, der das Goodie darstellt
	 */
	private int generateAndGetGoodie() {
		int randomGoodie = this.random.nextInt(100);
		int goodie = ApoSkunkmanConstants.GOODIE_EMPTY;
		if (randomGoodie < ApoSkunkmanConstants.GOODIE_GENERATION_WIDTH) {
			goodie = ApoSkunkmanConstants.GOODIE_GOOD_WIDTH;
		} else if (randomGoodie < ApoSkunkmanConstants.GOODIE_GENERATION_SKUNKMAN) {
			goodie = ApoSkunkmanConstants.GOODIE_GOOD_SKUNKMAN;
		} else if (randomGoodie < ApoSkunkmanConstants.GOODIE_GENERATION_FAST) {
			goodie = ApoSkunkmanConstants.GOODIE_GOOD_FAST;
		} else {
			goodie = ApoSkunkmanConstants.GOODIE_GOOD_GOD;
		}
		int goodGoodie = this.random.nextInt(100);
		if (goodGoodie >= ApoSkunkmanConstants.GOODIE_GENERATION_GOOD) {
			goodie += ApoSkunkmanConstants.GOODIE_GOOD_GOD;
		}
		return goodie;
	}
	
	/**
	 * befreit die Spieler, sodass sie sich frei bewegen können am Anfang
	 */
	private void makePlayersFree(final int width) {
		for (ApoSkunkmanPlayer player : this.players) {
			int startX = (int)(player.getX() / ApoSkunkmanConstants.TILE_SIZE - width);
			int startY = (int)(player.getY() / ApoSkunkmanConstants.TILE_SIZE - width);
			for (int x = startX; x < startX + 1 + 2 * width; x++) {
				for (int y = startY; y < startY + 1 + 2 * width; y++) {
					if ((y >= 0) && (y < this.level.length) &&
						(x >= 0) && (x < this.level[0].length)) {
						if ((this.level[y][x] != null) && (this.level[y][x] instanceof ApoSkunkmanBush)) {
							this.level[y][x] = null;
						}
					}
				}
			}
		}
	}
	
	/**
	 * gibt zurück, ob ein Spieler an diese Position gehen kann oder nicht
	 * @param changeX : Verschiebung in X-Richtung der Position des Spielers
	 * @param changeY : Verschiebung in X-Richtung der Position des Spielers
	 * @param player : der eigentliche Spieler
	 * @return TRUE, Spieler kann dahin gehen, ansonsten FALSE
	 */
	public boolean canPlayerGo(final int changeX, final int changeY, final ApoSkunkmanPlayer player) {
		int x = (int)(player.getX() / ApoSkunkmanConstants.TILE_SIZE);
		int y = (int)(player.getY() / ApoSkunkmanConstants.TILE_SIZE);
		return this.canPlayerGo(changeX, changeY, x, y);
	}
	
	/**
	 * gibt zurück, ob ein Spieler an diese Position gehen kann oder nicht
	 * @param changeX : Verschiebung in X-Richtung der Position des Spielers
	 * @param changeY : Verschiebung in X-Richtung der Position des Spielers
	 * @param player : der eigentliche Spieler
	 * @return TRUE, Spieler kann dahin gehen, ansonsten FALSE
	 */
	public boolean canPlayerGo(final int changeX, final int changeY, final int x, final int y) {
		int newX = (int)(x) + changeX;
		int newY = (int)(y) + changeY;
		if ((newX >= 0) && (newX < this.getLevel()[0].length) &&
			(newY >= 0) && (newY < this.getLevel().length) &&
			((this.getLevel()[newY][newX] == null) || (this.getLevel()[newY][newX] instanceof ApoSkunkmanGoodie))) {
			return true;
		}
		return false;
	}
	
	/**
	 * gibt zurück, ob der Spieler nach links gehen kann
	 * @param player : Spielerobjekt
	 * @return TRUE, Spieler kann nach links gehen, FALSE Spieler kann nicht nach links gehen
	 */
	public boolean canPlayerGoLeft(final ApoSkunkmanPlayer player) {
		return this.canPlayerGo(-1, 0, player);
	}
	
	/**
	 * gibt zurück, ob der Punkt links neben dem übergebenen Punkt frei ist
	 * @param x : x-Wert
	 * @param y : y-Wert
	 * @return TRUE, Spieler kann nach links gehen, FALSE Spieler kann nicht nach links gehen
	 */
	public boolean canPlayerGoLeft(final int x, final int y) {
		return this.canPlayerGo(-1, 0, x, y);
	}

	/**
	 * gibt zurück, ob der Spieler nach rechts gehen kann
	 * @param player : Spielerobjekt
	 * @return TRUE, Spieler kann nach rechts gehen, FALSE Spieler kann nicht nach rechts gehen
	 */
	public boolean canPlayerGoRight(final ApoSkunkmanPlayer player) {
		return this.canPlayerGo(1, 0, player);
	}
	
	/**
	 * gibt zurück, ob der Punkt rechts neben dem übergebenen Punkt frei ist
	 * @param x : x-Wert
	 * @param y : y-Wert
	 * @return TRUE, Spieler kann nach rechts gehen, FALSE Spieler kann nicht nach rechts gehen
	 */
	public boolean canPlayerGoRight(final int x, final int y) {
		return this.canPlayerGo(1, 0, x, y);
	}
	
	/**
	 * gibt zurück, ob der Spieler nach oben gehen kann
	 * @param player : Spielerobjekt
	 * @return TRUE, Spieler kann nach oben gehen, FALSE Spieler kann nicht nach oben gehen
	 */
	public boolean canPlayerGoUp(ApoSkunkmanPlayer player) {
		return this.canPlayerGo(0, -1, player);
	}
	
	/**
	 * gibt zurück, ob der Punkt oben neben dem übergebenen Punkt frei ist
	 * @param x : x-Wert
	 * @param y : y-Wert
	 * @return TRUE, Spieler kann nach oben gehen, FALSE Spieler kann nicht nach oben gehen
	 */
	public boolean canPlayerGoUp(final int x, final int y) {
		return this.canPlayerGo(0, -1, x, y);
	}
	
	/**
	 * gibt zurück, ob der Spieler nach unten gehen kann
	 * @param player : Spielerobjekt
	 * @return TRUE, Spieler kann nach unten gehen, FALSE Spieler kann nicht nach unten gehen
	 */
	public boolean canPlayerGoDown(final ApoSkunkmanPlayer player) {
		return this.canPlayerGo(0, 1, player);
	}
	
	/**
	 * gibt zurück, ob der Punkt unten neben dem übergebenen Punkt frei ist
	 * @param x : x-Wert
	 * @param y : y-Wert
	 * @return TRUE, Spieler kann nach unten gehen, FALSE Spieler kann nicht nach unten gehen
	 */
	public boolean canPlayerGoDown(final int x, final int y) {
		return this.canPlayerGo(0, 1, x, y);
	}
	
	/**
	 * legt eine Bombe an genau diese Stelle
	 * @param x : X-Stelle der Bombe
	 * @param y : Y-Stelle der Bombe
	 * @param playerInt : welcher Spieler
	 * @return immer TRUE
	 */
	public boolean layBomb(int x, int y, int playerInt) {
		ApoSkunkmanPlayer player = this.players[playerInt];
		ApoSkunkmanSkunkman skunkman = new ApoSkunkmanSkunkman(ApoSkunkmanImageContainer.iBomb, x * ApoSkunkmanConstants.TILE_SIZE, y * ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE, playerInt, player.getCurWidth());
		this.level[y][x] = skunkman;
		player.setCurSkunkman(player.getCurSkunkman() + 1);
		return true;
	}
	
	/**
	 * erstellt ein neues Level mit dem übergebenen Zufallswert<br />
	 * achtet dabei auf den derzeitigen Typus des Levels
	 * @param randomValue : Zufallszahl
	 */
	public void makeNewLevel(long randomValue) {
		this.makeNewLevel(randomValue, this.type);
	}
	
	/**
	 * erstellt ein neues Level mit dem übergebenen Zufallswert<br />
	 * achtet dabei auf den derzeitigen Typus des Levels
	 * @param randomValue : Zufallszahl
	 * @param type : Typus des Levels
	 */
	public void makeNewLevel(long randomValue, int type) {
		if (this.type != type) {
			this.setType(type);
		}
		if (this.type == ApoSkunkmanConstants.LEVEL_TYPE_EDITOR) {
			this.makeReallyEmptyLevel(this.getType(), this.randomValue);
			this.game.getEditor().setEditorLevel();
			this.game.getEditor().makeEditorLevel();
			this.makeLastValuesForANewLevel();
		} else {
			this.makeGenerationLevelWithLeveltype(this.type, randomValue);
		}

		this.setVisibleButtonsForPlayer();
	}
	
	
	/**
	 * wird aufgerufen, wenn ein Replay geladen werden soll
	 */
	public void loadAndMakeReplayLevel() {
		ApoSkunkmanReplay replay = this.replay.clone();
		this.setType(replay.getType());
		this.maxPlayers = replay.getPlayers();
		this.randomValue = replay.getRandom();
		this.setTime(replay.getTime());
		if (this.getType() == ApoSkunkmanConstants.LEVEL_TYPE_EDITOR) {
			if (this.replay.getHelp() != null) {
				this.makeReallyEmptyLevel(this.getType(), this.randomValue);
				this.replay.makeLevelFromHelp(this);
				this.makeLastValuesForANewLevel();
			} else {
				this.makeNewLevel(this.randomValue);	
			}
		} else {
			this.makeNewLevel(this.randomValue);			
		}
	}

	/**
	 * gibt das Array mit den Spielern zurück
	 * @return gibt das Array mit den Spielern zurück
	 */
	public final ApoSkunkmanPlayer[] getPlayers() {
		return this.players;
	}

	/**
	 * gibt das 2-dimensionale Array des Levels zurück, wo alle Steine, Büsche, Goodies und den Skunkmans zurück
	 * @return gibt das 2-dimensionale Array des Levels zurück, wo alle Steine, Büsche, Goodies und den Skunkmans zurück
	 */
	public final ApoSkunkmanEntity[][] getLevel() {
		return this.level;
	}

	/**
	 * gibt das Bild für das Gras zurück
	 * @return gibt das Bild für das Gras zurück
	 */
	public final BufferedImage getGrasImage() {
		return ApoSkunkmanImageContainer.iTile.getSubimage(0 * ApoSkunkmanConstants.TILE_SIZE, 0, 1 * ApoSkunkmanConstants.TILE_SIZE, 1 * ApoSkunkmanConstants.TILE_SIZE);
	}

	/**
	 * gibt das Bild für den Stein zurück
	 * @return gibt das Bild für den Stein zurück
	 */
	public final BufferedImage getStoneImage() {
		return ApoSkunkmanImageContainer.iTile.getSubimage(1 * ApoSkunkmanConstants.TILE_SIZE, 0, 1 * ApoSkunkmanConstants.TILE_SIZE, 1 * ApoSkunkmanConstants.TILE_SIZE);
	}
	
	/**
	 * gibt das Bild für den Busch zurück
	 * @return gibt das Bild für den Busch zurück
	 */
	public final BufferedImage getBushImage() {
		return ApoSkunkmanImageContainer.iTile.getSubimage(2 * ApoSkunkmanConstants.TILE_SIZE, 0, 1 * ApoSkunkmanConstants.TILE_SIZE, 1 * ApoSkunkmanConstants.TILE_SIZE);
	}

	/**
	 * gibt den Typen des Levels zurück<br />
	 * Entweder ein Standardlevel oder ein Level, wo die Spieler einen gewissen Punkt erreichen müssen
	 * @return gibt den Typen des Levels zurück
	 */
	public final int getType() {
		return this.type;
	}
	
	/**
	 * setzt den Typen des Levels auf den übergebenen Wert<br />
	 * @param newType : neuer Leveltyp
	 */
	public final void setType(int newType) {
		if (newType < 0) {
			newType = this.typeHelp.length - 1;
		} else if (newType > this.typeHelp.length - 1) {
			newType = 0;
		}
		ApoSkunkmanConstants.LEVEL_TYPE = newType;
		this.type = newType;
		if (this.replay != null) {
		}
		if (this.type != ApoSkunkmanConstants.LEVEL_TYPE_EDITOR) {
			this.game.makeBackground(true, false, false, false);
		}
	}

	/**
	 * Nachdenkmethode<br />
	 * seit dem letzten Aufruf sind delta Millisekunden vergangen
	 * @param delta : Zeit, die seit dem letzten Aufruf vergangen ist
	 */
	public void think(long delta) {
		if (!this.bWin) {
			// wie oft soll das Nachdenken durchlaufen werden?
			int count = 1;
			if (this.speedMode == ApoSkunkmanConstants.SPEED_OPTIONS_FAST) {
				count = 5;
			} else if (this.speedMode == ApoSkunkmanConstants.SPEED_OPTIONS_VERY_FAST) {
				count = 20;
			} else if (this.speedMode == ApoSkunkmanConstants.SPEED_OPTIONS_MODERATE_FAST) {
				count = 10;
			} else if (this.speedMode == ApoSkunkmanConstants.SPEED_OPTIONS_VERY_SLOW) {
				this.curDelta += delta;
				if (this.curDelta >= 20 * delta) {
					this.curDelta -= 20 * delta;
					count = 1;
				} else {
					count = 0;
				}
			} else if (this.speedMode == ApoSkunkmanConstants.SPEED_OPTIONS_SLOWLY_SLOW) {
				this.curDelta += delta;
				if (this.curDelta >= 10 * delta) {
					this.curDelta -= 10 * delta;
					count = 1;
				} else {
					count = 0;
				}
			} else if (this.speedMode == ApoSkunkmanConstants.SPEED_OPTIONS_SLOW) {
				this.curDelta += delta;
				if (this.curDelta >= 5 * delta) {
					this.curDelta -= 5 * delta;
					count = 1;
				} else {
					count = 0;
				}
			}
			// durchlaufe es auch so oft
			for (int i = 0; i < count; i++) {
				this.thinkEverything((int)delta);
				if (this.isWin()) {
					break;
				}
			}
		}
	}
	
	/**
	 * das eigentliche Nachdenken<br />
	 * seit dem letzten Aufruf sind delta Millisekunden vergangen<br />
	 * @param delta : Zeit, die seit dem letzten Aufruf vergangen ist
	 */
	private void thinkEverything(int delta) {
		// veranlasst das Level nachzudenken und explodiert Skunkmans und handelt die Sachen wie Goodies eingesammelt usw.
		for (int y = 1; y < this.level.length - 1; y += 1) {
			for (int x = 1; x < this.level[0].length - 1; x += 1) {
				if (this.level[y][x] != null) {
					this.level[y][x].think((int)delta);
					if (this.level[y][x] instanceof ApoSkunkmanSkunkman) {
						ApoSkunkmanSkunkman skunkman = (ApoSkunkmanSkunkman)this.level[y][x];
						if (skunkman.shouldExplode()) {
							this.explodeSkunkman(x, y, skunkman);
						}
					} else if (this.level[y][x] instanceof ApoSkunkmanGoodie) {
						ApoSkunkmanGoodie goodie = (ApoSkunkmanGoodie)(this.level[y][x]);
						if (goodie.isBVisible()) {
							for (ApoSkunkmanPlayer player : this.players) {
								int playerX = (int)((player.getX() + player.getWidth()/2) / ApoSkunkmanConstants.TILE_SIZE);
								int playerY = (int)((player.getY() + player.getHeight()/2) / ApoSkunkmanConstants.TILE_SIZE);
								if ((playerX == x) && (playerY == y)) {
									player.addGoodie(goodie.getGoodie());
									this.level[y][x] = null;
								}
							}
						} else {
							this.level[y][x] = null;
						}
					}
				}
			}
		}
		// Lässt das Feuer nachdenken und setzt es invisible, wenn die Zeit vergangen ist
		if (this.fire != null) {
			for (int i = this.fire.size() - 1; i >= 0; i--) {
				this.fire.get(i).think((int)delta);
				if (!this.fire.get(i).isBVisible()) {
					this.fire.remove(i);
				}
			}
		}
		boolean bAddPoint = false;
		this.curTimePoints += delta;
		if (this.curTimePoints >= ApoSkunkmanConstants.PLAYER_TIME_TO_NEXT_POINTS) {
			this.curTimePoints -= ApoSkunkmanConstants.PLAYER_TIME_TO_NEXT_POINTS;
			bAddPoint = true;
		}
		// l�sst die SpielerAI nachdenken und �berpr�ft die Thinktimes
		for (int i = 0; i < this.players.length && i < this.maxPlayers; i++) {
			ApoSkunkmanPlayer player = this.players[i];
			long time = System.nanoTime();
			player.thinkAI(this, (int)delta);
			time = System.nanoTime() - time;
			this.thinkTime[i].add(time);
			
			if (bAddPoint) {
				if (player.isBVisible()) {
					player.setPoints(player.getPoints() + 1);
				}
			}
			
			long median = 0;
			if (this.thinkTime[i].size() > 100) {
				for (int t = 0; t < this.thinkTime[i].size(); t++) {
					median += this.thinkTime[i].get(t);
				}
				median /= this.thinkTime[i].size();
				if (median / 1000000 > ApoSkunkmanConstants.MAX_TIME_THINK) {
					player.setPoints(player.getPoints() + ApoSkunkmanConstants.PLAYER_EXCEPTION_POINTS);
					player.setBVisible(false);
				}
				this.thinkTime[i].clear();
			}
		}
		// lässt die eigentlichen Spieler nachdenken und bewegt sie
		int visible = 0;
		int playerInt = -1;
		for (int i = 0; i < this.players.length && i < this.maxPlayers; i++) {
			ApoSkunkmanPlayer player = this.players[i];
			player.think((int)delta);
			if (player.isBVisible()) {
				visible += 1;
				playerInt = player.getPlayer();
			}
			// falls ein Spieler im Ziel steht
			if (!player.isMoving()) {
				int x = (int)(player.getX() / ApoSkunkmanConstants.TILE_SIZE);
				int y = (int)(player.getY() / ApoSkunkmanConstants.TILE_SIZE);
				if ((this.goalX.x == x) && (this.goalX.y == y)) {
					this.winGame(player.getPlayer());
				}
			}
		}
		if (((this.goalX.x < 0)  && (visible <= 1)) ||  (visible <= 0)) {
			this.winGame(playerInt);
		}

		if (this.time <= ApoSkunkmanConstants.TIME_MAX) {
			// Zeit wird vermindert
			this.curTime -= delta;
			// falls die Zeit kleiner als 0 ist, dann fülle das Level mit Steinen auf, um einen Sieger zu erhalten 
			if (this.curTime <= 0) {
				this.fillLevelWithStones((int)delta);
			}
		}
	}
	
	/**
	 * füllt die Levels mit Steinen auf falls die Zeit abgelaufen ist
	 */
	private void fillLevelWithStones(final int delta) {
		this.curFillTime += delta;
		// falls es Zeit ist einen Stein zu setzten dann
		if (this.curFillTime >= ApoSkunkmanConstants.LEVEL_FILL_TIME) {
			this.curFillTime -= ApoSkunkmanConstants.LEVEL_FILL_TIME;
			BufferedImage iStone = this.getStoneImage();
			this.level[this.curFillPoint.y][this.curFillPoint.x] = new ApoSkunkmanStone(iStone, this.curFillPoint.x * ApoSkunkmanConstants.TILE_SIZE, this.curFillPoint.y * ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE);
			// falls ein Spieler vom Stein getroffen wurde, setzte ihn auf "unsichtbar"
			for (int i = 0; i < this.players.length && i < this.maxPlayers; i++) {
				if (this.players[i].isBVisible()) {
					if (this.players[i].intersects(this.curFillPoint.x * ApoSkunkmanConstants.TILE_SIZE, this.curFillPoint.y * ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE)) {
						this.players[i].setBVisible(false);
					}
				}
			}
			// neue Position des nächsten Steins berechnen
			if (this.curFillWidth + 1 < this.curFillMaxWidth) {
				this.curFillWidth += 1;
				this.setNextPositionForStone();
			} else {
				// falls die Richtung des Steines geändert werden muss
				// ändere gegebenenfalls die wie oft in einer Reihe Steine gesetzt werden müssen
				this.curFillWidthCounter += 1;
				if (this.curFillWidthCounter % 2 == 0) {
					this.curFillWidthCounter = 0;	
					this.curFillMaxWidth -= 1;
					if (this.curFillMaxWidth < 0) {
						this.curFillMaxWidth = 0;
					}
				}
				this.curFillWidth = 1;
				// ändere die Richtung
				if (this.curFillDirection == ApoSkunkmanConstants.PLAYER_DIRECTION_DOWN) {
					this.curFillDirection = ApoSkunkmanConstants.PLAYER_DIRECTION_RIGHT;
				} else if (this.curFillDirection == ApoSkunkmanConstants.PLAYER_DIRECTION_RIGHT) {
					this.curFillDirection = ApoSkunkmanConstants.PLAYER_DIRECTION_UP;
				} else if (this.curFillDirection == ApoSkunkmanConstants.PLAYER_DIRECTION_UP) {
					this.curFillDirection = ApoSkunkmanConstants.PLAYER_DIRECTION_LEFT;
				} else if (this.curFillDirection == ApoSkunkmanConstants.PLAYER_DIRECTION_LEFT) {
					this.curFillDirection = ApoSkunkmanConstants.PLAYER_DIRECTION_DOWN;
				}
				this.setNextPositionForStone();
			}
		}
	}
	
	/**
	 * setzte die neue Position des Steines in Abhängigkeit der Richtung
	 */
	private void setNextPositionForStone() {
		// falls er überhaupt noch bewegt werden muss, dann setzte den neuen Punkt abhängig von der Richtung auf den Neuen
		if (this.curFillMaxWidth > 1) {
			if (this.curFillDirection == ApoSkunkmanConstants.PLAYER_DIRECTION_DOWN) {
				this.curFillPoint.y += 1;
			} else if (this.curFillDirection == ApoSkunkmanConstants.PLAYER_DIRECTION_UP) {
				this.curFillPoint.y -= 1;
			} else if (this.curFillDirection == ApoSkunkmanConstants.PLAYER_DIRECTION_LEFT) {
				this.curFillPoint.x -= 1;
			} else if (this.curFillDirection == ApoSkunkmanConstants.PLAYER_DIRECTION_RIGHT) {
				this.curFillPoint.x += 1;
			}
		}
	}
	
	/**
	 * gibt den Gewinner des Spiels zurück
	 * @return gibt den Gewinner des Spiels zurück
	 */
	public final int getWinner() {
		return this.winner;
	}

	/**
	 * wird aufgerufen, wenn ein Spiel beendet wird und der Sieger feststeht
	 */
	private void winGame(int player) {
		for (int p = 0; p < this.getMaxPlayers(); p++) {
			this.getPlayers()[p].saveAI();
		}
		this.winner = player;
		if (this.winner >= 0) {
			this.players[this.winner].setPoints(this.players[this.winner].getPoints() + ApoSkunkmanConstants.PLAYER_WINNING_POINTS);
		} else {
			if ((this.goalX.x >= 0) && (player < 0)) {
				this.bLoose = true;
			} else {
				this.bLoose = false;
			}
			int maxPoints = this.players[0].getPoints();
			this.winner = 0;
			for (int i = 1; i < this.maxPlayers && i < this.players.length; i++) {
				if (maxPoints < this.players[i].getPoints()) {
					maxPoints = this.players[i].getPoints();
					this.winner = i;
				}
			}
		}
		this.bWin = true;
		this.game.stopGame();
		this.game.render();
	}
	
	/**
	 * wird aufgerufen, wenn ein Skunkman sich erleichtert <br />
	 * erstellt die Feuer und veranlasst dabei auch andere Skunkmans zum skunken<br />
	 * @param x : x-Variable des Skunkmans im Level
	 * @param y : y-Variable des Skunkmans im Level
	 * @param skunkman : der eigentliche Skunkman
	 */
	private void explodeSkunkman(final int x, final int y, ApoSkunkmanSkunkman skunkman) {
		int[][] fireBoolean = new int[this.level.length][this.level[0].length];
		this.players[skunkman.getPlayer()].setCurSkunkman(this.players[skunkman.getPlayer()].getCurSkunkman() - 1);
		fireBoolean[y][x] = 1;
		int skunkWidth = skunkman.getSkunkWidth();
		boolean bRepeat = true;
		int startX = x;
		int startY = y;
		this.testSkunk(startX, startY, fireBoolean, skunkman.getPlayer());
		while (bRepeat) {
			boolean bLeft = true;
			boolean bRight = true;
			boolean bUp = true;
			boolean bDown = true;
			for (int i = 1; i <= skunkWidth; i++) {
				// alles links testen
				if (bLeft) {
					bLeft = this.testSkunk(startX - i, startY, fireBoolean, skunkman.getPlayer());
				}
				// alles rechts testen
				if (bRight) {
					bRight = this.testSkunk(startX + i, startY, fireBoolean, skunkman.getPlayer());
				}
				// alles drüber testen
				if (bUp) {
					bUp = this.testSkunk(startX, startY - i, fireBoolean, skunkman.getPlayer());
				}
				// alles drunter testen
				if (bDown) {
					bDown = this.testSkunk(startX, startY + i, fireBoolean, skunkman.getPlayer());
				}
			}
			this.level[startY][startX] = null;
			// testet, ob andere "Skunkmans" betroffen sind und falls ja wiederhole das ganze für diese nochmal
			boolean shouldRepeat = false;
			for (int levelY = 1; levelY < this.level.length - 1; levelY += 1) {
				for (int levelX = 1; levelX < this.level[0].length - 1; levelX += 1) {
					if (fireBoolean[levelY][levelX] != 0) {
						if ((this.level[levelY][levelX] != null) && (this.level[levelY][levelX] instanceof ApoSkunkmanSkunkman)) {
							shouldRepeat = true;
							ApoSkunkmanSkunkman newSkunkman = (ApoSkunkmanSkunkman)this.level[levelY][levelX];
							skunkman = newSkunkman;
							this.players[skunkman.getPlayer()].setCurSkunkman(this.players[skunkman.getPlayer()].getCurSkunkman() - 1);
							skunkWidth = newSkunkman.getSkunkWidth();
							startX = levelX;
							startY = levelY;
							break;
						}
					}
				}
				if (shouldRepeat) {
					break;
				}
			}
			if (!shouldRepeat) {
				bRepeat = false;
			}
		}
		// erstellt mit dem fireBoolean Array das Feuer und sorge dafür das es schick aussieht
		for (int levelY = 1; levelY < this.level.length - 1; levelY += 1) {
			for (int levelX = 1; levelX < this.level[0].length - 1; levelX += 1) {
				if (fireBoolean[levelY][levelX] != 0) {
					if (this.isLeftSkunk(fireBoolean, levelX, levelY)) {
						this.fire.add(new ApoSkunkmanFire(ApoSkunkmanImageContainer.iFlame.getSubimage(3 * ApoSkunkmanConstants.TILE_SIZE, 0, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE), levelX * ApoSkunkmanConstants.TILE_SIZE, levelY * ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE));
					} else if (this.isRightSkunk(fireBoolean, levelX, levelY)) {
						this.fire.add(new ApoSkunkmanFire(ApoSkunkmanImageContainer.iFlame.getSubimage(6 * ApoSkunkmanConstants.TILE_SIZE, 0, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE), levelX * ApoSkunkmanConstants.TILE_SIZE, levelY * ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE));
					} else if (this.isUpSkunk(fireBoolean, levelX, levelY)) {
						this.fire.add(new ApoSkunkmanFire(ApoSkunkmanImageContainer.iFlame.getSubimage(5 * ApoSkunkmanConstants.TILE_SIZE, 0, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE), levelX * ApoSkunkmanConstants.TILE_SIZE, levelY * ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE));
					} else if (this.isDownSkunk(fireBoolean, levelX, levelY)) {
						this.fire.add(new ApoSkunkmanFire(ApoSkunkmanImageContainer.iFlame.getSubimage(4 * ApoSkunkmanConstants.TILE_SIZE, 0, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE), levelX * ApoSkunkmanConstants.TILE_SIZE, levelY * ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE));
					} else if (this.isLeftRightSkunk(fireBoolean, levelX, levelY)) {
						this.fire.add(new ApoSkunkmanFire(ApoSkunkmanImageContainer.iFlame.getSubimage(1 * ApoSkunkmanConstants.TILE_SIZE, 0, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE), levelX * ApoSkunkmanConstants.TILE_SIZE, levelY * ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE));
					} else if (this.isUpDownSkunk(fireBoolean, levelX, levelY)) {
						this.fire.add(new ApoSkunkmanFire(ApoSkunkmanImageContainer.iFlame.getSubimage(2 * ApoSkunkmanConstants.TILE_SIZE, 0, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE), levelX * ApoSkunkmanConstants.TILE_SIZE, levelY * ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE));
					} else {
						this.fire.add(new ApoSkunkmanFire(ApoSkunkmanImageContainer.iFlame.getSubimage(0 * ApoSkunkmanConstants.TILE_SIZE, 0, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE), levelX * ApoSkunkmanConstants.TILE_SIZE, levelY * ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE));
					}
				}
			}
		}
	}
	
	/**
	 * gibt zurück, ob links und rechts von den übergebenen Punkten auch Feuer ist
	 * @param fireBoolean : Das Array mit den Feuerangaben
	 * @param x : zu überprüfender x-Wert im Level
	 * @param y : zu überprüfender y-Wert im Level
	 * @return TRUE, links und rechts ist auch Feuer, ansonsten FALSE
	 */
	private boolean isLeftRightSkunk(int[][] fireBoolean, int x, int y) {
		if ((y - 1 >= 0) && (y + 1 < fireBoolean.length) &&
			(x - 1 >= 0) && (x + 1 < fireBoolean[0].length)) {
			if ((fireBoolean[y][x-1] > 0) && (fireBoolean[y][x+1] > 0) &&
				(fireBoolean[y-1][x] <= 0) && (fireBoolean[y+1][x] <= 0)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * gibt zurück, ob drüber und drunter von den übergebenen Punkten auch Feuer ist
	 * @param fireBoolean : Das Array mit den Feuerangaben
	 * @param x : zu überprüfender x-Wert im Level
	 * @param y : zu überprüfender y-Wert im Level
	 * @return TRUE, drüber und drunter ist auch Feuer, ansonsten FALSE
	 */
	private boolean isUpDownSkunk(int[][] fireBoolean, int x, int y) {
		if ((y - 1 >= 0) && (y + 1 < fireBoolean.length) &&
			(x - 1 >= 0) && (x + 1 < fireBoolean[0].length)) {
			if ((fireBoolean[y][x-1] <= 0) && (fireBoolean[y][x+1] <= 0) &&
				(fireBoolean[y-1][x] > 0) && (fireBoolean[y+1][x] > 0)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * gibt zurück, ob nur drüber von den übergebenen Punkten auch Feuer ist
	 * @param fireBoolean : Das Array mit den Feuerangaben
	 * @param x : zu überprüfender x-Wert im Level
	 * @param y : zu überprüfender y-Wert im Level
	 * @return TRUE, nur drüber ist Feuer, ansonsten FALSE
	 */
	private boolean isUpSkunk(int[][] fireBoolean, int x, int y) {
		if ((y - 1 >= 0) && (y + 1 < fireBoolean.length) &&
			(x - 1 >= 0) && (x + 1 < fireBoolean[0].length)) {
			if ((fireBoolean[y][x-1] <= 0) && (fireBoolean[y][x+1] <= 0) &&
				(fireBoolean[y-1][x] > 0) && (fireBoolean[y+1][x] <= 0)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * gibt zurück, ob nur drunter von den übergebenen Punkten auch Feuer ist
	 * @param fireBoolean : Das Array mit den Feuerangaben
	 * @param x : zu überprüfender x-Wert im Level
	 * @param y : zu überprüfender y-Wert im Level
	 * @return TRUE, nur drunter ist Feuer, ansonsten FALSE
	 */
	private boolean isDownSkunk(int[][] fireBoolean, int x, int y) {
		if ((y - 1 >= 0) && (y + 1 < fireBoolean.length) &&
			(x - 1 >= 0) && (x + 1 < fireBoolean[0].length)) {
			if ((fireBoolean[y][x-1] <= 0) && (fireBoolean[y][x+1] <= 0) &&
				(fireBoolean[y-1][x] <= 0) && (fireBoolean[y+1][x] > 0)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * gibt zurück, ob nur links von den übergebenen Punkten auch Feuer ist
	 * @param fireBoolean : Das Array mit den Feuerangaben
	 * @param x : zu überprüfender x-Wert im Level
	 * @param y : zu überprüfender y-Wert im Level
	 * @return TRUE, nur links ist Feuer, ansonsten FALSE
	 */
	private boolean isLeftSkunk(int[][] fireBoolean, int x, int y) {
		if ((y - 1 >= 0) && (y + 1 < fireBoolean.length) &&
			(x - 1 >= 0) && (x + 1 < fireBoolean[0].length)) {
			if ((fireBoolean[y][x-1] > 0) && (fireBoolean[y][x+1] <= 0) &&
				(fireBoolean[y-1][x] <= 0) && (fireBoolean[y+1][x] <= 0)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * gibt zurück, ob nur rechts von den übergebenen Punkten auch Feuer ist
	 * @param fireBoolean : Das Array mit den Feuerangaben
	 * @param x : zu überprüfender x-Wert im Level
	 * @param y : zu überprüfender y-Wert im Level
	 * @return TRUE, nur rechts ist Feuer, ansonsten FALSE
	 */
	private boolean isRightSkunk(int[][] fireBoolean, int x, int y) {
		if ((y - 1 >= 0) && (y + 1 < fireBoolean.length) &&
			(x - 1 >= 0) && (x + 1 < fireBoolean[0].length)) {
			if ((fireBoolean[y][x-1] <= 0) && (fireBoolean[y][x+1] > 0) &&
				(fireBoolean[y-1][x] <= 0) && (fireBoolean[y+1][x] <= 0)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * testet einen Tile, ob es frei ist oder besetzt und gibt TRUE zurück, wenn das Feuer weiter gehen kann, ansonsten FALSE
	 * @param x : zu überprüfender x-Wert im Level
	 * @param y : zu überprüfender y-Wert im Level
	 * @param fireBoolean : Das Array mit den Feuerangaben
	 * @return TRUE Feuer geht weiter, FALSE Feuer kommt zum Stoppen
	 */
	private boolean testSkunk(int x, int y, int[][] fireBoolean, int playerInt) {
		if ((x >= 0) && (y >= 0) && 
			(x < this.level[0].length) && (y < this.level.length)) {
			for (int i = 0; i < this.maxPlayers && i < this.players.length; i++) {
				ApoSkunkmanPlayer player = this.players[i];
				if (player.isBVisible()) {
					int playerX = (int)((player.getX() + player.getWidth()/2) / ApoSkunkmanConstants.TILE_SIZE);
					int playerY = (int)((player.getY() + player.getHeight()/2) / ApoSkunkmanConstants.TILE_SIZE);
					if ((playerX == x) && (playerY == y)) {
						player.setBVisible(false);
						if (playerInt == player.getPlayer()) {
							player.setPoints(player.getPoints() + ApoSkunkmanConstants.SKUNKMAN_POINTS_OWN);
						} else {
							this.players[playerInt].setPoints(this.players[playerInt].getPoints() + ApoSkunkmanConstants.SKUNKMAN_POINTS_ENEMY);
						}
					}
				}
			}
			if (fireBoolean[y][x] > 1) {
				return false;
			}
			if (this.level[y][x] == null) {
				if (fireBoolean[y][x] > 1) {
					return false;
				}
				fireBoolean[y][x] = 1;
				return true;
			} else if (this.level[y][x] instanceof ApoSkunkmanGoodie) {
				if (fireBoolean[y][x] <= 0) {
					this.level[y][x] = null;
				} else {
					return false;
				}
				fireBoolean[y][x] = 1;
				return true;
			} else if (this.level[y][x] instanceof ApoSkunkmanBush) {
				final ApoSkunkmanBush bush = (ApoSkunkmanBush)(this.level[y][x]);
				int goodie = bush.getGoodie();
				if (goodie != ApoSkunkmanConstants.GOODIE_EMPTY) {
					this.level[y][x] = new ApoSkunkmanGoodie(ApoSkunkmanImageContainer.iGoodie.getSubimage(ApoSkunkmanConstants.GOODIE_TILES * (goodie - 1) * ApoSkunkmanConstants.TILE_SIZE, 0, ApoSkunkmanConstants.GOODIE_TILES * ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE), x * ApoSkunkmanConstants.TILE_SIZE, y * ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE, goodie);
				} else {
					this.level[y][x] = null;
				}
				fireBoolean[y][x] = 2;
				return false;
			} else if (this.level[y][x] instanceof ApoSkunkmanStone) {
				fireBoolean[y][x] = 0;
				return false;
			} else if (this.level[y][x] instanceof ApoSkunkmanSkunkman) {
				fireBoolean[y][x] = 1;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * rendert alles zum Level dazugehörige
	 * @param g : das Grafikobjekt
	 * @param changeX : Verschiebung in x-Richtung
	 * @param changeY : Verschiebung in y-Richtung
	 */
	public void render(Graphics2D g, int changeX, int changeY) {
		if (this.bWin) {
		} else {
			this.renderLevel(g, changeX, changeY);
		}
	}

	/**
	 * rendert die Analyse wenn jemand gewonnen hat
	 * @param g : das Grafikobjekt
	 * @param changeX : Verschiebung in x-Richtung
	 * @param changeY : Verschiebung in y-Richtung
	 */
	public void renderAnalysis(Graphics2D g, int changeX, int changeY) {
		g.setFont(ApoSkunkmanConstants.FONT_HUD_ANALYSIS);
		int width = (int)(this.getLevel()[0].length * ApoSkunkmanConstants.TILE_SIZE);
		String s = "Analysis";
		ApoSkunkmanConstants.drawString(g, s, changeX + width/2, changeY + 15 * ApoSkunkmanConstants.APPLICATION_SIZE, true, Color.WHITE, Color.BLACK);
		
		if (!this.bLoose) {
			g.setFont(ApoSkunkmanConstants.FONT_HUD_TREE);
			s = "Nach "+ApoHelp.getTimeToDraw(this.time - this.curTime)+" des Leveltypes \""+this.typeHelp[this.type].getTypeString()+"\"";
			ApoSkunkmanConstants.drawString(g, s, changeX + width/2, changeY + 35 * ApoSkunkmanConstants.APPLICATION_SIZE, true, Color.WHITE, Color.BLACK);
			
			if (this.goalX.x >= 0) {
				s = "kam Spieler "+(winner + 1)+" \""+this.players[winner].getPlayerName()+"\"";
				ApoSkunkmanConstants.drawString(g, s, changeX + width/2, changeY + 50 * ApoSkunkmanConstants.APPLICATION_SIZE, true, Color.WHITE, Color.BLACK);
				
				s = "als Erster ins Ziel!";
				ApoSkunkmanConstants.drawString(g, s, changeX + width/2, changeY + 65 * ApoSkunkmanConstants.APPLICATION_SIZE, true, Color.WHITE, Color.BLACK);
				
				g.drawImage(this.players[winner].getImages()[0][0], changeX + width/2 - this.players[winner].getImages()[0][0].getWidth()/2, changeY + 70 * ApoSkunkmanConstants.APPLICATION_SIZE, null);
			} else {
				s = "hat Spieler "+(winner + 1)+" \""+this.players[winner].getPlayerName()+"\"";
				ApoSkunkmanConstants.drawString(g, s, changeX + width/2, changeY + 50 * ApoSkunkmanConstants.APPLICATION_SIZE, true, Color.WHITE, Color.BLACK);
				
				s = " mit "+String.valueOf(this.players[winner].getPoints())+" Punkten gewonnen!";
				ApoSkunkmanConstants.drawString(g, s, changeX + width/2, changeY + 65 * ApoSkunkmanConstants.APPLICATION_SIZE, true, Color.WHITE, Color.BLACK);
				
				g.drawImage(this.players[winner].getImages()[0][0], changeX + width/2 - this.players[winner].getImages()[0][0].getWidth()/2, changeY + 70 * ApoSkunkmanConstants.APPLICATION_SIZE, null);			
			}
		} else {
			g.setFont(ApoSkunkmanConstants.FONT_HUD_TREE);
			s = "Niemand hat das Level gewonnen.";
			ApoSkunkmanConstants.drawString(g, s, changeX + width/2, changeY + 35 * ApoSkunkmanConstants.APPLICATION_SIZE, true, Color.WHITE, Color.BLACK);
		}
		
		s = "Spieler";
		ApoSkunkmanConstants.drawString(g, s, changeX + 1 * ApoSkunkmanConstants.TILE_SIZE, changeY + 110 * ApoSkunkmanConstants.APPLICATION_SIZE, false, Color.WHITE, Color.BLACK);
		s = "Punkte";
		ApoSkunkmanConstants.drawString(g, s, changeX + width*3/4, changeY + 110 * ApoSkunkmanConstants.APPLICATION_SIZE, false, Color.WHITE, Color.BLACK);
		
		g.setColor(Color.WHITE);
		g.drawLine(changeX + 1 * ApoSkunkmanConstants.TILE_SIZE, changeY + 112 * ApoSkunkmanConstants.APPLICATION_SIZE, changeX + width - 1 * ApoSkunkmanConstants.TILE_SIZE, changeY + 112 * ApoSkunkmanConstants.APPLICATION_SIZE);
		
		ArrayList<Integer> playerSortedAfterPoints = new ArrayList<Integer>();
		while (playerSortedAfterPoints.size() < this.maxPlayers) {
			int maxPoints = -1;
			int playerInt = -1;
			for (int i = 0; i < this.players.length && i < this.maxPlayers; i++) {
				if (playerSortedAfterPoints.indexOf((Integer)(i)) < 0) {
					if ((maxPoints < 0) || (maxPoints < this.players[i].getPoints())) {
						maxPoints = this.players[i].getPoints();
						playerInt = i;
					}
				}
			}
			if (playerInt >= 0) {
				playerSortedAfterPoints.add(playerInt);
			} else {
				break;
			}
		}
		
		if (this.players != null) {
			for (int i = 0; i < playerSortedAfterPoints.size(); i++) {
				ApoSkunkmanPlayer player = this.players[playerSortedAfterPoints.get(i)];
				BufferedImage iHead = player.getIHead();
				g.drawImage(iHead, changeX + 1 * ApoSkunkmanConstants.TILE_SIZE, changeY + (130 + 14 * i) * ApoSkunkmanConstants.APPLICATION_SIZE - iHead.getHeight(), null);
				int w = iHead.getWidth() + 3 * ApoSkunkmanConstants.APPLICATION_SIZE;
				s = player.getPlayerName() + " ("+(player.getPlayer()+1)+")";
				if (this.isReplayShowing()) {
					s = this.replay.getNames()[playerSortedAfterPoints.get(i)] + " ("+(player.getPlayer()+1)+")";
				}
				ApoSkunkmanConstants.drawString(g, s, changeX + 1 * ApoSkunkmanConstants.TILE_SIZE + w, changeY + (130 + 14 * i) * ApoSkunkmanConstants.APPLICATION_SIZE, false, Color.WHITE, Color.BLACK);
				
				s = String.valueOf(player.getPoints());
				w = g.getFontMetrics().stringWidth(s);
				ApoSkunkmanConstants.drawString(g, s, changeX + width*3/4 + 25 * ApoSkunkmanConstants.APPLICATION_SIZE - w, changeY + (130 + 14 * i) * ApoSkunkmanConstants.APPLICATION_SIZE, false, Color.WHITE, Color.BLACK);
			}
		}
		
		Stroke stroke = g.getStroke();
		g.setStroke(new BasicStroke(4));
		g.setColor(Color.WHITE);
		g.drawLine(changeX + 1 * ApoSkunkmanConstants.TILE_SIZE, (int)(this.game.getButtons()[30].getY() - 18 * ApoSkunkmanConstants.APPLICATION_SIZE), changeX + width - 1 * ApoSkunkmanConstants.TILE_SIZE, (int)(this.game.getButtons()[30].getY() - 18 * ApoSkunkmanConstants.APPLICATION_SIZE));
		g.setStroke(stroke);
		
		s = "Replay des Spiels";
		ApoSkunkmanConstants.drawString(g, s, changeX + width/2, this.game.getButtons()[30].getY() - 3 * ApoSkunkmanConstants.APPLICATION_SIZE, true, Color.WHITE, Color.BLACK);
	}
	
	/**
	 * rendert das eigentliche Level, wenn es noch läuft und noch niemand gewonnen hat
	 * @param g : das Grafikobjekt
	 * @param changeX : Verschiebung in x-Richtung
	 * @param changeY : Verschiebung in y-Richtung
	 */
	private void renderLevel(Graphics2D g, int changeX, int changeY) {
		if (this.game.isGameRunning()) {
			// male das Level
			this.renderRealLevel(g, changeX, changeY);
		}
		// male das Feuer
		try {
			if (this.fire != null) {
				for (ApoSkunkmanFire fire : this.fire) {
					fire.render(g, changeX, changeY);
				}
			}
		} catch (Exception ex) {
			System.out.println("Exception beim Render des Fires");
		}
		// male das Ziel
		if (this.goalX.x != -1) {
			g.drawImage(ApoSkunkmanImageContainer.iGoalX, this.goalX.x * ApoSkunkmanConstants.TILE_SIZE + changeX, this.goalX.y * ApoSkunkmanConstants.TILE_SIZE + changeY, null);
		}
		
		// male die Spieler
		try {
			if (this.players != null) {
				int maxPlayer = this.maxPlayers;
				if (this.game.isEditorMode()) {
					maxPlayer = ApoSkunkmanConstants.PLAYER_MAX_PLAYER;
				}
				for (int i = 0; i < this.players.length && i < maxPlayer; i++) {
					ApoSkunkmanPlayer player = this.players[i];
					if (player.isBVisible()) {
						player.render(g, changeX, changeY);
					}
				}
			}
		} catch (Exception ex) {
			System.out.println("Exception beim Render der Player");
		}
		this.renderTimeStatistics(g, changeX, changeY);
	}
	
	/**
	 * rendert das eigentliche Level
	 * @param g : Grafikobjekt, auf dem gezeichnet wird
	 * @param changeX : Verschiebung in x-Richtung
	 * @param changeY : Verschiebung in y-Richtung
	 */
	public void renderRealLevel(Graphics2D g, int changeX, int changeY) {
		try {
			for (int y = 1; y < this.level.length - 1; y += 1) {
				for (int x = 1; x < this.level[0].length - 1; x += 1) {
					if (this.level[y][x] != null) {
						if ((this.curTime >= 0) && (!(this.level[y][x] instanceof ApoSkunkmanStone))) {
							this.level[y][x].render(g, changeX, changeY);
						} else {
							this.level[y][x].render(g, changeX, changeY);
						}
					}
					if (ApoSkunkmanConstants.DEBUG) {
						g.setColor(Color.BLACK);
						g.drawRect(x * ApoSkunkmanConstants.TILE_SIZE + changeX, y * ApoSkunkmanConstants.TILE_SIZE + changeY, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE);
					}
				}
			}
		} catch (Exception ex) {
			System.out.println("Exception beim Render des Levels");
		}
	}
	
	/**
	 * rendert die Zeit an die richtige Stelle
	 * @param g : Grafikobjekt, auf dem gezeichnet wird
	 * @param changeX : Verschiebung in x-Richtung
	 * @param changeY : Verschiebung in y-Richtung
	 */
	private void renderTimeStatistics(Graphics2D g, int changeX, int changeY) {
		if (!this.game.isEditorMode()) {	
			String timeString = ApoHelp.getTimeToDraw(this.curTime);
			if (this.time > ApoSkunkmanConstants.TIME_MAX) {
				timeString = String.valueOf((char)(247));
			} else if (this.curTime < 0) {
				timeString = "Stonetime";
			}
			g.setFont(ApoSkunkmanConstants.FONT_LEVEL_TIME);
			g.setColor(Color.BLACK);
			ApoSkunkmanConstants.drawString(g, timeString, changeX + 1 * ApoSkunkmanConstants.APPLICATION_SIZE + (float)this.level[0].length * (float)ApoSkunkmanConstants.TILE_SIZE / 2f, ApoSkunkmanConstants.GAME_HEIGHT - 12 * ApoSkunkmanConstants.APPLICATION_SIZE, true);
			
			if (this.isReplayShowing()) {
				g.setFont(ApoSkunkmanConstants.FONT_LEVEL_TIME);
				String s = "Replay";
				int x = (int)(ApoSkunkmanImageContainer.iHud.getWidth() + ApoSkunkmanConstants.TILE_SIZE/2 + 1 * ApoSkunkmanConstants.APPLICATION_SIZE + (float)this.getGame().getLevel().getLevel()[0].length * (float)ApoSkunkmanConstants.TILE_SIZE / 2f);
				int y = (int)(12 * ApoSkunkmanConstants.APPLICATION_SIZE);
				ApoSkunkmanConstants.drawString(g, s, x, y, true);		
			}
			
			if (this.getGame().isSimulate()) {
				g.setColor(new Color(0, 0, 0, 200));
				int width = (this.getLevel()[0].length + 1) * ApoSkunkmanConstants.TILE_SIZE;
				int simuWidth = 180 * ApoSkunkmanConstants.APPLICATION_SIZE;
				int height = 25 * ApoSkunkmanConstants.APPLICATION_SIZE;
				int x = ApoSkunkmanImageContainer.iHud.getWidth() + width/2 - simuWidth/2;
				int y = ApoSkunkmanConstants.GAME_HEIGHT/2 - height/2;
				g.fillRoundRect(x, y, simuWidth, height, 30, 30);
				g.setColor(Color.WHITE);
				g.drawRoundRect(x, y, simuWidth, height, 30, 30);
				
				g.setFont(ApoSkunkmanConstants.FONT_OPTIONS_BOLD);
				String s = "Simulation runs";
				int w = g.getFontMetrics().stringWidth(s);
				int h = g.getFontMetrics().getHeight();
				g.drawString(s, x + simuWidth/2 - w/2, y + height/2 + h/2);
			}
		}
	}
}
