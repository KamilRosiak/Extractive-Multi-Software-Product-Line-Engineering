package apoSkunkman.level;

import java.awt.Point;
import java.util.Stack;

import apoSkunkman.entity.ApoSkunkmanEntity;
import apoSkunkman.entity.ApoSkunkmanStone;

/**
 * Klasse um Levels zu testen, ob alle freien Felder erreichbar sind
 * @author Enrico Ebert
 */
public class ApoSkunkmanLevelTester {

	/**
	 * Testet das Level, ob die Startpositionen frei und alle freien Felder erreichbar sind
	 * @param level : zu testendes Level
	 * @return boolean ob Test bestanden
	 */
	public static boolean testLevel(final boolean[][] level) {
		// Startpositionen der Spieler testen
		if((!level[1][1]) || 
				(!level[level.length-2][1]) || 
				(!level[1][level[0].length-2]) ||
				(!level[level.length-2][level[0].length-2]) ) {
			return false;
		}
		
		boolean[][] visited = new boolean[level.length][level[0].length];
		Stack<Point> pointsToTest = new Stack<Point>();
		pointsToTest.add(new Point(1,1));
		
		while(!pointsToTest.isEmpty()) {
			Point cur = pointsToTest.pop();
			
			visited[cur.y][cur.x]= true;
			
			// oben
			if(level[cur.y-1][cur.x]&& !visited[cur.y-1][cur.x]) {
				pointsToTest.add(new Point(cur.x,cur.y-1));
			}
			// unten
			if(level[cur.y+1][cur.x]&& !visited[cur.y+1][cur.x]) {
				pointsToTest.add(new Point(cur.x,cur.y+1));
			}
			// links
			if(level[cur.y][cur.x-1]&& !visited[cur.y][cur.x-1]) {
				pointsToTest.add(new Point(cur.x-1,cur.y));
			}
			// rechts
			if(level[cur.y][cur.x+1]&& !visited[cur.y][cur.x+1]) {
				pointsToTest.add(new Point(cur.x+1,cur.y));
			}
		}
		
		for(int y = 1; y < level.length-1; y++) {
			for(int x = 1; x < level[0].length-1; x++) {
				if(level[y][x] && !visited[y][x]) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	// Wenn Test nicht bestanden wurde, dann reicht eine Warnung
	// Falls die unerreichbaren Felder gewollt waren, oder abweichende Startpositionen
	/**
	 * Testet Editor-Levels, ob Startpositionen frei und alle freien Felder erreichbar sind
	 * @param level : zu testendes Level
	 * @return boolean ob Test bestanden
	 */
	public static boolean testLevel(final ApoSkunkmanEntity[][] level) {
		boolean[][] blevel = new boolean[level.length][level[0].length];
		
		for(int y = 0; y < level.length; y ++) {
			for(int x = 0; x < level[0].length; x++) {
				if( (level[y][x] != null) && (level[y][x] instanceof ApoSkunkmanStone)) {
					blevel[y][x] = false;
				}
				else {
					blevel[y][x] = true;
				}
			}
		}
		return testLevel(blevel);
	}
}
