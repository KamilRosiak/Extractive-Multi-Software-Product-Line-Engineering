package apoSkunkman.level;

import java.awt.Point;
import java.util.Random;

import apoSkunkman.ApoSkunkmanConstants;
import apoSkunkman.level.generators.*;

/**
 * Klasse zur Erstellung eines Levels
 * @author Enrico Ebert, Dirk Aporius
 */
public class ApoSkunkmanLevelGenerator {

	/** Anzahl der Level-Zeilen */
	private int row;
	/** Anzahl der Level-Spalten */
	private int col;
	/** das Level in Byte-Form */
	private byte[][] bytelevel;
	/** das Level in Boolean-Form */
	private boolean[][] boollevel;
	/** das Zufallsvariablenobjekt */
	private Random random;

	public static void main(String[] args) {
		long randValue = System.nanoTime();
		ApoSkunkmanLevelGenerator random = new ApoSkunkmanLevelGenerator(randValue);
		boolean[][] level;
		int strategie = ApoSkunkmanConstants.LEVEL_TYPE_DEADEND;

		level = random.getRandomLevel(strategie);
	
		System.out.println("randValue: "+randValue);
		System.out.println(level);
		System.out.println(random);
		System.out.println(ApoSkunkmanLevelTester.testLevel(level));
		
		// Level-Stress-Test
//		while(ApoSkunkmanLevelTester.testLevel(level)) {
//			level = random.getRandomLevel(strategie);
//		}
//		System.out.println(random);
	}
	
	/**
	 * Konstruktor mit den Zufallswerten
	 * @param randomValue : Zufallswerte
	 */
	public ApoSkunkmanLevelGenerator(final long randomValue) {
		this.init(ApoSkunkmanConstants.LEVEL_WIDTH, ApoSkunkmanConstants.LEVEL_HEIGHT, randomValue);
	}

	/**
	 * Konstruktor mit den Zufallswerten
	 * @param row : Zahl der Reihen
	 * @param col : Zahl der Spalten
	 * @param randomValue : Zufallswerte
	 */
	public ApoSkunkmanLevelGenerator(final int row, final int col, final long randomValue) {
		this.init(row, col, randomValue);
	}

	/**
	 * Initialisiertung des Zufallslevels
	 * @param row : Zahl der Reihen
	 * @param col : Zahl der Spalten
	 * @param randomValue : Zufallswerte
	 */
	private void init(final int row, final int col, final long randomValue) {
		this.col = col;
		this.row = row;
		
		this.bytelevel = new byte[row/2][col/2];
		this.boollevel = new boolean[row][col];
		this.random = new Random(randomValue);
	}
	
	/**
	 * entfernt alle Steine
	 */
	private void resetLevel() {
		for(int i = 0; i < this.bytelevel.length; i++) {
			for(int j = 0; j < this.bytelevel[i].length; j++) {
				this.bytelevel[i][j] = 0;
			}
		}
		for(int i = 0; i < this.boollevel.length; i++) {
			for(int j = 0; j < this.boollevel[i].length; j++) {
				this.boollevel[i][j] = true;
			}
		}
	}

	/**
	 * Berechnet Zufallslevel mit ausgewählter Strategie<br />
	 * Falls ungültige Strategie gewählt wurde, wird die Default-Strategie genommen.
	 * @param strategie Strategie-Switch
	 */
	private void calcLevel(final int strategie) {
		
		/*
		 * falls init mal vergessen wurde
		 * eine Initialisierung und dann in einer Schleife
		 * getRandomLevel(STRATEGIE_SWITCH) 
		 * um mehrere verschiedene Levels mit einem Start-Seed zu generieren
		 */
		this.resetLevel();
		
		switch(strategie) {
		case ApoSkunkmanConstants.LEVEL_TYPE_STANDARD:
			ApoSkunkmanLevelStandard.calc(this.boollevel, this.random);
			break;
		case ApoSkunkmanConstants.LEVEL_TYPE_GOAL_X:
			ApoSkunkmanLevelDeep.calc(this.bytelevel, this.random);
			this.byteToBoolean();
			break;
		case ApoSkunkmanConstants.LEVEL_TYPE_PRIM:
			ApoSkunkmanLevelPrim.calc(this.bytelevel, this.random);
			this.byteToBoolean();
			break;
		case ApoSkunkmanConstants.LEVEL_TYPE_STANDARD_SECOND:
			ApoSkunkmanLevelStandardSecond.calc(this.boollevel, this.random);
			break;
		case ApoSkunkmanConstants.LEVEL_TYPE_STANDARD_THIRD:
			ApoSkunkmanLevelStandardThird.calc(this.boollevel, this.random);
			break;
		case ApoSkunkmanConstants.LEVEL_TYPE_DEADEND:
			ApoSkunkmanLevelDeadEnd.calc(this.boollevel, this.random);
			break;
		case ApoSkunkmanConstants.LEVEL_TYPE_LITTLE:
			ApoSkunkmanLevelLittle.calc(this.boollevel, this.random);
			break;
		case ApoSkunkmanConstants.LEVEL_TYPE_EASY:
			ApoSkunkmanLevelEasy.calc(this.boollevel, this.random);
			break;
		default:
			this.calcLevel(ApoSkunkmanLevelConstants.LEVEL_GENERATION_DEFAULT);
			break;
		}	
		
		this.makeOuterWalls();
	}

	/**
	 * gibt das boolean array des Zufallslevels zurück
	 * @param strategie Strategie-Switch
	 * @return booleanarray des Zufallslevels, TRUE frei, FALSE Stein
	 */
	public boolean[][] getRandomLevel(final int strategie) {
		this.calcLevel(strategie);
		return this.boollevel;
	}
	
	/**
	 * erstellt die Außenwände, sicher ist sicher
	 */
	private void makeOuterWalls() {
		for(int x = 0; x < this.boollevel.length; x++) {
			this.boollevel[0][x] = false;
			this.boollevel[this.boollevel.length-1][x] = false;
		}
		for(int y = 0; y < this.boollevel[0].length; y++) {
			this.boollevel[y][0] = false;
			this.boollevel[y][this.boollevel[0].length-1] = false;
		}
	}
	
	/**
	 * wandelt das Byte-Level in ein Boolean-Level um
	 * @author Dirk Aporius
	 */
	private void byteToBoolean() {
		char[][] btmp = this.getRandomLevelInChar();
		
		boolean[][] randomLevel = new boolean[btmp.length/2][btmp[0].length/2];
		
		for(int y = 0; (y < btmp.length); y += 2) {
			for(int x = 0; (x < btmp[0].length); x += 2) {
				String value = String.valueOf(btmp[y][x]);
				if ((y/2 < randomLevel.length) && (x/2 < randomLevel[0].length)) {
					if (!value.equals("#")) {
						randomLevel[y/2][x/2] = true;
					}
				}
			}
		}
		this.boollevel = randomLevel;
	}
	
	/**
	 * gibt ein Punktarray mit den Spielerkoordinaten zurück
	 * @param strategie : welcher Leveltype ist gewählt
	 * @return gibt ein Punktarray mit den Spielerkoordinaten zurück
	 */
	public Point[] getPlayerStartPoints(final int strategie) {
		switch(strategie) {
		case ApoSkunkmanConstants.LEVEL_TYPE_GOAL_X:
			return ApoSkunkmanLevelDeep.getPlayers();
		case ApoSkunkmanConstants.LEVEL_TYPE_LITTLE:
			return ApoSkunkmanLevelLittle.getPlayers();
		default:
			return null;
		}
	}
	
	/**
	 * wandelt das byteArray in ein charArray um und gibt es zurück (' ' frei, '#' Stein)
	 * @return wandelt das byteArray in ein charArray um und gibt es zurück (' ' frei, '#' Stein)
	 */
	private char[][] getRandomLevelInChar() {
		int trow = this.bytelevel.length;
		int tcol = this.bytelevel[0].length;
		
		char[][] ctmp = new char[trow*4+2][tcol*4+3];
		
		for(int i = 0; i < ctmp.length; i++) {
			for(int j = 0; j < (ctmp[i].length-1); j++) {
				ctmp[i][j] = '#';
			}
			ctmp[i][ctmp[i].length-1] = '\n';
		}
		
		for(int i=0; i < this.bytelevel.length; i++) {
			for(int j=0; j < this.bytelevel[i].length; j++) {
				// remove field
				if ((this.bytelevel[i][j]&ApoSkunkmanLevelConstants.VISITED) > 0) {
					ctmp[i*4+2][j*4+2] = ' ';
					ctmp[i*4+2][j*4+3] = ' ';
					ctmp[i*4+3][j*4+2] = ' ';
					ctmp[i*4+3][j*4+3] = ' ';
				}
				
				// remove right
				if ((j < this.col-1)&&((this.bytelevel[i][j]&ApoSkunkmanLevelConstants.RIGHT) > 0)) {
					ctmp[i*4+2][j*4+4] = ' ';
					ctmp[i*4+3][j*4+4] = ' ';
				}
				
				// remove upper
				if ((i > 0)&&((this.bytelevel[i][j]&ApoSkunkmanLevelConstants.UP) > 0)) {
					ctmp[i*4+1][j*4+2] = ' ';
					ctmp[i*4+1][j*4+3] = ' ';
				}
				
				// remove left
				if ((j > 0)&&((this.bytelevel[i][j]&ApoSkunkmanLevelConstants.LEFT) > 0)) {
					ctmp[i*4+2][j*4+1] = ' ';
					ctmp[i*4+3][j*4+1] = ' ';
				}
				
				// remove lower
				if ((i < this.row-1)&&((this.bytelevel[i][j]&ApoSkunkmanLevelConstants.DOWN) > 0)) {
					ctmp[i*4+4][j*4+2] = ' ';
					ctmp[i*4+4][j*4+3] = ' ';
				}
			}
		}
		
		return ctmp;
	}
	
	public String toString() {
		
		/*
		 * Debug-Ausgabe
		 * ist jetzt zwar etwas kleiner, aber wenigstens wird das Boolean-Level getestet
		 * # Stein
		 * . freies Feld, kann man besser zählen, als Leerzeichen
		 */
		
		StringBuffer tmp = new StringBuffer((this.boollevel.length)*(this.boollevel[0].length+1));
		
		for(boolean[] y: this.boollevel) {
			for(boolean x: y) {
				tmp.append(x?".":"#");
			}
			tmp.append("\n");
		}
		return tmp.toString();
	}
}
