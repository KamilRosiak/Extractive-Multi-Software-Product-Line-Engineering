package apoSkunkman.level.generators;

import java.awt.Point;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;

import apoSkunkman.ApoSkunkmanConstants;
import apoSkunkman.level.ApoSkunkmanLevelConstants;

/*
 * http://en.wikipedia.org/wiki/Maze_generation_algorithm#Depth-first_search
 */

/**
 * Klasse für Generierung eines Zufalls-Levels mit Tiefensuche/Backtracking
 * @author Enrico Ebert
 */
public class ApoSkunkmanLevelDeep {
	
	/**
	 * Berechnet Zufallslevel mit Tiefensuche/Backtracking
	 * @param level Spielfeld auf dem die Steine gesetzt werden sollen
	 * @param random Zufallsgenerator, der benutzt werden soll
	 */
	public static void calc(byte[][] level, Random random) {
		
		Stack<Point> cross = new Stack<Point>();
//		cross.push(new Point(0, 0));
		cross.add(new Point(level[0].length-1, level.length-1));

		Point curpos = null;
		Point nextpos = null;
		
		while(!cross.isEmpty()) {
			curpos = cross.peek();
			
			level[curpos.y][curpos.x] |= ApoSkunkmanLevelConstants.VISITED;	// mark visited
			
			Vector<Point> neighbours = getNeighbours(level, curpos);
			
			int tmp = neighbours.size();
			if(tmp == 0) {
				cross.pop();
				continue;
			}
			else {
				nextpos = neighbours.elementAt(random.nextInt(tmp));
				cross.push(nextpos);
			}
			
			// remove wall
			if ((nextpos.y < curpos.y) && (nextpos.x == curpos.x)) { // going up
				level[curpos.y][curpos.x]  |= ApoSkunkmanLevelConstants.UP; 	// upper wall
				level[nextpos.y][nextpos.x] |= ApoSkunkmanLevelConstants.DOWN; // lower wall
			}
			else if ((nextpos.y > curpos.y) && (nextpos.x == curpos.x)) { // going down
				level[curpos.y][curpos.x]  |= ApoSkunkmanLevelConstants.DOWN; // lower wall
				level[nextpos.y][nextpos.x] |= ApoSkunkmanLevelConstants.UP;  // upper wall
			}
			else { // nextpos.y == curpos.y
				if ((nextpos.x < curpos.x) && (nextpos.y == curpos.y)) { // going left
					level[curpos.y][curpos.x]  |= ApoSkunkmanLevelConstants.LEFT;   // left wall
					level[nextpos.y][nextpos.x] |= ApoSkunkmanLevelConstants.RIGHT; // right wall
				} else if((nextpos.x > curpos.x) && (nextpos.y == curpos.y)) { // going right
					level[curpos.y][curpos.x]  |= ApoSkunkmanLevelConstants.RIGHT;	// right wall
					level[nextpos.y][nextpos.x] |= ApoSkunkmanLevelConstants.LEFT;	// left wall
				}
				else {
					System.out.println("Hier stimmt was nicht!");
				}
			}
		} // while
	}
	
	private static Vector<Point> getNeighbours(byte[][] level, Point pos) {
		Vector<Point> tmp = new Vector<Point>();
		

		int maxy = level.length;
		int maxx = level[0].length;
		
		for(int i=0;i<4;i++) {
			switch(i) {
			case 0:		// right
				if( (pos.x+1) >= maxx )
					continue;
				if( (level[pos.y][pos.x+1]&ApoSkunkmanLevelConstants.VISITED) == 0)
					tmp.add(new Point(pos.x+1,pos.y));
				break;
			case 1:		// up
				if( (pos.y -1) < 0 )
					continue;
				if( (level[pos.y-1][pos.x]&ApoSkunkmanLevelConstants.VISITED) == 0)
					tmp.add(new Point(pos.x,pos.y-1));
				break;
			case 2:		// left
				if( (pos.x-1) < 0 )
					continue;
				if( (level[pos.y][pos.x-1]&ApoSkunkmanLevelConstants.VISITED) == 0)
					tmp.add(new Point(pos.x-1,pos.y));
				break;
			case 3:		// down
				if( (pos.y+1) >= maxy )
					continue;
				if( (level[pos.y+1][pos.x]&ApoSkunkmanLevelConstants.VISITED) == 0)
					tmp.add(new Point(pos.x,pos.y+1));
				break;
			}
		}
		
		return tmp;
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
