package apoSkunkman.level.generators;

import java.awt.Point;
import java.util.Random;

import apoSkunkman.ApoSkunkmanConstants;

/**
 * Klasse für Generierung eines Zufalls-Levels ohne Steine und Zufallsziel
 * @author Dirk Aporius
 */
public class ApoSkunkmanLevelEasy {

	/**
	 * Erstellt ein Standard-Level
	 * @param level Spielfeld auf dem die Steine gesetzt werden sollen
	 * @param random Zufallsgenerator, der benutzt werden soll
	 */
	public static void calc(boolean[][] level, Random random) {
		int[][] levelArray = new int[][] {
				{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
			};
		for(int y = 0; y < level.length; y++) {
			for(int x = 0; x < level.length; x++) {
				level[y][x] = (levelArray[y][x]==0);
			}
		}
	}
	
	/**
	 * gibt ein Punktarray mit den Spielerkoordinaten zurück
	 * @return gibt ein Punktarray mit den Spielerkoordinaten zurück
	 */
	public static Point[] getPlayers() {
		Point[] playerPoints = new Point[ApoSkunkmanConstants.PLAYER_MAX_PLAYER];
		for (int i = 0; i < playerPoints.length; i++) {
			playerPoints[i] = new Point(1, 1);
		}
		return playerPoints;
	}
	
}
