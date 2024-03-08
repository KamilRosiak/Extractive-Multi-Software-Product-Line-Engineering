package apoSkunkman.replay;

import java.awt.Point;

/**
 * Dient für Editorlevels dazu fürs Replay wieder geladen werden zu können
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
	 * gibt ein 3dimensionales byteArray des levels zurück
	 * @return
	 */
	public final byte[][][] getLevelArray() {
		return this.levelArray;
	}

	/**
	 * gibt ein Array mit den Spielerstartpunkten zurück
	 * @return gibt ein Array mit den Spielerstartpunkten zurück
	 */
	public final Point[] getPlayerArray() {
		return this.playerArray;
	}

	/**
	 * gibt den Zielpunkt zurück
	 * @return gibt den Zielpunkt zurück
	 */
	public final Point getGoalX() {
		return this.goalX;
	}

	/**
	 * gibt die Zeit des levels zurück
	 * @return gibt die Zeit des levels zurück
	 */
	public final int getTime() {
		return this.time;
	}
	
	/**
	 * gibt einen Clone des Objekts zurück
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
