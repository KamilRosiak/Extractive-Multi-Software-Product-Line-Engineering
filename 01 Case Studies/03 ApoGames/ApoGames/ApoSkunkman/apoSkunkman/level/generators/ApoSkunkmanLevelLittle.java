package apoSkunkman.level.generators;

import java.awt.Point;
import java.util.Random;

import apoSkunkman.ApoSkunkmanConstants;

/**
 * Klasse für Generierung eines kleineren Standard-Levels
 * @author Dirk Aporius
 */
public class ApoSkunkmanLevelLittle {
	
	private static final int[][] LEVEL_ARRAY = new int[][] {
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
			{1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1},
			{1, 0, 1, 1, 2, 0, 0, 0, 0, 0, 5, 1, 1, 0, 1},
			{1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1},
			{1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1},
			{1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1},
			{1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1},
			{1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1},
			{1, 0, 1, 1, 4, 0, 0, 0, 0, 0, 3, 1, 1, 0, 1},
			{1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1},
			{1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
		};
	
	/**
	 * Erstellt ein Standard-Level
	 * @param level Spielfeld auf dem die Steine gesetzt werden sollen
	 * @param random Zufallsgenerator, der benutzt werden soll
	 */
	public static void calc(boolean[][] level, Random random) {
		for(int y = 0; y < ApoSkunkmanLevelLittle.LEVEL_ARRAY.length; y++) {
			for(int x = 0; x < ApoSkunkmanLevelLittle.LEVEL_ARRAY.length; x++) {
				if (ApoSkunkmanLevelLittle.LEVEL_ARRAY[y][x]!=1) {
					level[y][x] = true;
				} else {
					level[y][x] = false;
				}
			}
		}
	}
	
	/**
	 * gibt ein Punktarray mit den Spielerkoordinaten zurück
	 * @return gibt ein Punktarray mit den Spielerkoordinaten zurück
	 */
	public static Point[] getPlayers() {
		Point[] playerPoints = new Point[ApoSkunkmanConstants.PLAYER_MAX_PLAYER];
		for(int y = 0; y < ApoSkunkmanLevelLittle.LEVEL_ARRAY.length; y++) {
			for(int x = 0; x < ApoSkunkmanLevelLittle.LEVEL_ARRAY.length; x++) {
				if ((ApoSkunkmanLevelLittle.LEVEL_ARRAY[y][x] > 1) &&
					(ApoSkunkmanLevelLittle.LEVEL_ARRAY[y][x] <= 1 + playerPoints.length)) {
					playerPoints[ApoSkunkmanLevelLittle.LEVEL_ARRAY[y][x] - 2] = new Point(x, y);
				}
			}
		}
		return playerPoints;
	}
}
