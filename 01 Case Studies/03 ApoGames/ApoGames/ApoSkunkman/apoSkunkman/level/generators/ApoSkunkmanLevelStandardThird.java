package apoSkunkman.level.generators;

import java.util.Random;

/**
 * Klasse für Generierung eines Standard-Levels Typ 3
 * @author Dirk Aporius
 */
public class ApoSkunkmanLevelStandardThird {
	/**
	 * Erstellt ein Standard-Level Typ 3
	 * @param level Spielfeld auf dem die Steine gesetzt werden sollen
	 * @param random Zufallsgenerator, der benutzt werden soll
	 */
	public static void calc(boolean[][] level, Random random) {
		int[][] levelArray = new int[][] {
				{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1},
				{1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
				{1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1},
				{1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
				{1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1},
				{1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1},
				{1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1},
				{1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
				{1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1},
				{1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
				{1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
			};
		for(int y = 0; y < level.length; y++) {
			for(int x = 0; x < level.length; x++) {
				level[y][x] = (levelArray[y][x]==0);
			}
		}
	}
}
