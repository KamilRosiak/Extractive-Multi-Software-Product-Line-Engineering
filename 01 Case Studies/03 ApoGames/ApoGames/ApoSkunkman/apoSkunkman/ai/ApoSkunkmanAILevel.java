package apoSkunkman.ai;

import java.awt.Point;

import apoSkunkman.entity.ApoSkunkmanBush;
import apoSkunkman.entity.ApoSkunkmanGoodie;
import apoSkunkman.entity.ApoSkunkmanSkunkman;
import apoSkunkman.entity.ApoSkunkmanStone;
import apoSkunkman.level.ApoSkunkmanLevel;

/**
 * Klasse, die das Level und deren Informationen f�r die KI bereitstellt
 * @author Dirk Aporius, Enrico Ebert
 *
 */
public class ApoSkunkmanAILevel {
	/** Levelobjekt aus dem Spiel */
	private final ApoSkunkmanLevel level;
	/** bytearray des Levels */
	private byte[][] levelArray;
	/** Gegnerarray */
	private ApoSkunkmanAIEnemy[] enemies;
	
	public ApoSkunkmanAILevel(final ApoSkunkmanLevel level, final int playerInt) {
		this.level = level;
		this.init(playerInt);
	}
	
	/**
	 * initialisiert alle Werte und Variablen
	 */
	private void init(int playerInt) {
		this.levelArray = new byte[this.level.getLevel().length][this.level.getLevel()[0].length];
		for (int y = 0; y < this.levelArray.length; y++) {
			for (int x = 0; x < this.levelArray[0].length; x++) {
				if (this.level.getLevel()[y][x] == null) {
					this.levelArray[y][x] = ApoSkunkmanAIConstants.LEVEL_FREE;
				} else if (this.level.getLevel()[y][x] instanceof ApoSkunkmanStone) {
					this.levelArray[y][x] = ApoSkunkmanAIConstants.LEVEL_STONE;
				} else if (this.level.getLevel()[y][x] instanceof ApoSkunkmanBush) {
					this.levelArray[y][x] = ApoSkunkmanAIConstants.LEVEL_BUSH;
				} else if (this.level.getLevel()[y][x] instanceof ApoSkunkmanGoodie) {
					this.levelArray[y][x] = ApoSkunkmanAIConstants.LEVEL_GOODIE;
				} else if (this.level.getLevel()[y][x] instanceof ApoSkunkmanSkunkman) {
					this.levelArray[y][x] = ApoSkunkmanAIConstants.LEVEL_SKUNKMAN;
				}
			}
		}
		this.enemies = new ApoSkunkmanAIEnemy[this.level.getMaxPlayers() - 1];
		int count = 0;
		for (int i = 0; i < this.level.getPlayers().length && i < this.level.getMaxPlayers(); i++) {
			if (this.level.getPlayers()[i].getPlayer() != playerInt) {
				this.enemies[count] = new ApoSkunkmanAIEnemy(this.level.getPlayers()[i]);
				count += 1;
			}
		}
	}
	
	/**
	 * gibt ein Array mit den Gegnern zur�ck
	 * @return gibt ein Array mit den Gegnern zur�ck
	 */
	public final ApoSkunkmanAIEnemy[] getEnemies() {
		return this.enemies;
	}

	/**
	 * gibt das eigentliche Level als 2-dimensionales ByteArray zur�ck<br />
	 * m�gliche Werte f�r das Array sind:<br />
	 * ApoSkunkmanAIConstants.LEVEL_FREE, wenn dort nur Gras ist und damit frei begehbar<br />
	 * ApoSkunkmanAIConstants.LEVEL_STONE, wenn dort ein unzerst�rbarer Stein ist<br />
	 * ApoSkunkmanAIConstants.LEVEL_BUSH, wenn dort ein zerst�rbarer Busch ist<br />
	 * ApoSkunkmanAIConstants.LEVEL_GOODIE, wenn dort ein Goodie ist<br />
	 * ApoSkunkmanAIConstants.LEVEL_SKUNKMAN, wenn dort ein Skunkman ist<br />
	 * @return gibt das eigentliche Level als 2-dimensionales ByteArray zur�ck<br />
	 */
	public final byte[][] getLevelAsByte() {
		return this.levelArray;
	}
	
	/**
	 * falls bei den Koordinaten ein Goodie ist, dann wird es zur�ckgegeben, sonst null
	 * @return falls bei den Koordinaten ein Goodie ist, dann wird es zur�ckgegeben, sonst null
	 */
	public final ApoSkunkmanAILevelGoodie getGoodie(int y, int x) {
		if((this.level.getLevel()[y][x] != null) && (this.level.getLevel()[y][x] instanceof ApoSkunkmanGoodie)) {
			return new ApoSkunkmanAILevelGoodie((ApoSkunkmanGoodie)this.level.getLevel()[y][x]);
		}
		else {
			return null;
		}
	}
	
	/**
	 * falls bei den Koordinaten ein Skunkman ist, dann wird es zur�ckgegeben, sonst null
	 * @return falls bei den Koordinaten ein Skunkman ist, dann wird es zur�ckgegeben, sonst null
	 */
	public final ApoSkunkmanAILevelSkunkman getSkunkman(int y, int x) {
		if((this.level.getLevel()[y][x] != null) && (this.level.getLevel()[y][x] instanceof ApoSkunkmanSkunkman)) {
			return new ApoSkunkmanAILevelSkunkman((ApoSkunkmanSkunkman)this.level.getLevel()[y][x]);
		}
		else {
			return null;
		}
	}

	/**
	 * gibt den Leveltypen zur�ck<br />
	 * entweder ApoSkunkmanAIConstants.LEVEL_TYPE_GOAL_X, wenn ein Zielpunkt zu erreichen ist<br />
	 * ansonsten ApoSkunkmanAIConstants.LEVEL_TYPE_STANDARD, wenn das Ziel daraus besteht, die Gegner einzudampfen<br />
	 * @return gibt den Leveltypen zur�ck
	 */
	public final int getType() {
		if (this.level.getGoalX().x >= 0) {
			return ApoSkunkmanAIConstants.LEVEL_TYPE_GOAL_X;
		} else {
			return ApoSkunkmanAIConstants.LEVEL_TYPE_STANDARD;			
		}
	}
	
	/**
	 * gibt zur�ck wieviel Zeit in dem Level noch zur Verf�gung steht bevor es mit Steinen aufgef�llt wird (in Millisekunden)
	 * @return gibt zur�ck wieviel Zeit in dem Level noch zur Verf�gung steht bevor es mit Steinen aufgef�llt wird (in Millisekunden)
	 */
	public final int getTime() {
		return this.level.getCurTime();
	}
	
	/**
	 * gibt zur�ck, wieviel Zeit f�r dieses Level insgesamt zur Verf�gung steht bzw. stand
	 * @return gibt zur�ck, wieviel Zeit f�r dieses Level insgesamt zur Verf�gung steht bzw. stand
	 */
	public final int getStartTime() {
		return this.level.getTime();
	}
	
	/**
	 * gibt den Zielpunkt zur�ck<br />
	 * besteht aus x = -1, y = -1, wenn es keinen Zielpunkt gibt
	 * @return gibt den Zielpunkt zur�ck<br />
	 */
	public final Point getGoalXPoint() {
		Point p = new Point(this.level.getGoalX().x, this.level.getGoalX().y);
		return p;
	}
}
