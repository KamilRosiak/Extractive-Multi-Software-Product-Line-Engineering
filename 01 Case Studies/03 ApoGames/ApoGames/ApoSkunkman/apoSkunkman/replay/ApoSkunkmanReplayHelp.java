package apoSkunkman.replay;

import java.awt.Point;

/**
 * Dient f�r Editorlevels dazu f�rs Replay wieder geladen werden zu k�nnen
 * @author Dirk Aporius
 *
 */
public class ApoSkunkmanReplayHelp {
	
	/** ein 3dimensionales byteArray des Levels */
	private final byte[][][] levelArray;
	/** ein Array mit den Spielerstartpunkten */
	private final Point[] playerArray;
	/** der Zielpunkt */
	private final Point goalX;
	/** die Zeit des Levels */
	private final int time;
	
	public ApoSkunkmanReplayHelp(final byte[][][] levelArray, final Point[] playerArray, final Point goalX, final int time) {
		this.time = time;
		this.levelArray = levelArray;
		this.playerArray = playerArray;
		this.goalX = goalX;
	}

	/**
	 * gibt ein 3dimensionales byteArray des levels zur�ck
	 * @return
	 */
	public final byte[][][] getLevelArray() {
		return this.levelArray;
	}

	/**
	 * gibt ein Array mit den Spielerstartpunkten zur�ck
	 * @return gibt ein Array mit den Spielerstartpunkten zur�ck
	 */
	public final Point[] getPlayerArray() {
		return this.playerArray;
	}

	/**
	 * gibt den Zielpunkt zur�ck
	 * @return gibt den Zielpunkt zur�ck
	 */
	public final Point getGoalX() {
		return this.goalX;
	}

	/**
	 * gibt die Zeit des levels zur�ck
	 * @return gibt die Zeit des levels zur�ck
	 */
	public final int getTime() {
		return this.time;
	}
	
	/**
	 * gibt einen Clone des Objekts zur�ck
	 */
	public final ApoSkunkmanReplayHelp clone() {
		byte[][][] cloneLevelArray = new byte[this.levelArray.length][this.levelArray[0].length][this.levelArray[0][0].length];
		for (int y = 0; y < cloneLevelArray.length; y++) {
			for (int x = 0; x < cloneLevelArray[0].length; x++) {
				for (int z = 0; z < cloneLevelArray[0][0].length; z++) {
					cloneLevelArray[y][x][z] = this.levelArray[y][x][z];
				}
			}
		}
		return new ApoSkunkmanReplayHelp(cloneLevelArray, this.playerArray.clone(), (Point)this.goalX.clone(), this.time);
	}
}
