package apoBot.level;

import java.awt.Point;
import java.util.ArrayList;

import apoBot.ApoBotConstants;

/**
 * war eine Testklasse, als es noch keinen Editor gab und erstellt nebenbei das zufällige Level für das Menu
 * @author TheApo
 *
 */
public class ApoBotLevelTest {

	private ArrayList<ApoBotLevel> testLevels;
	
	public ApoBotLevelTest() {
		this.testLevels = new ArrayList<ApoBotLevel>();
		this.testLevels.add(this.makeLevelOne());
	}
	
	public ApoBotLevel getLevel(int level) {
		if (level < 0) {
			level = 0;
		} else if (level >= this.testLevels.size()) {
			level = this.testLevels.size() - 1;
		}
		if (level >= 0) {
			return this.testLevels.get(level);
		} else {
			return null;
		}
	}
	
	private ApoBotLevel makeLevelOne() {
		ApoBotLevelLevel[][] level = new ApoBotLevelLevel[13][13];
		for (int y = 0; y < level.length; y++) {
			for (int x = 0; x < level[0].length; x++) {
				level[y][x] = new ApoBotLevelLevel(ApoBotConstants.GROUND_EMPTY, 0);
			}
		}
		for (int x = 0; x < level[0].length; x++) {
			level[0][x].setStartHeight(1);
			level[level.length-1][x].setStartHeight(1);
		}
		for (int y = 0; y < level.length; y++) {
			level[y][0].setStartHeight(1);
			level[y][level[0].length-1].setStartHeight(0);
			level[y][level[0].length-2].setStartHeight(2);
			level[y][level[0].length-3].setStartHeight(1);
		}
		level[4][5].setStartGround(ApoBotConstants.GROUND_ORIGINAL);
		
		Point startPoint = new Point(3, 4);
		int directionView = ApoBotConstants.DIR_SOUTH_DOWNRIGHT;
		
		int[] menu = new int[3];
		menu[0] = 14;
		menu[1] = 7;
		menu[2] = 6;
		return new ApoBotLevel(level, startPoint, directionView, menu);
	}
	
	/**
	 * erstellt das zufällige Level für das Menu
	 * @return zufälliges Level
	 */
	public ApoBotLevel getMenuLevel() {
		int yLevel = (int)(Math.random() * 10 + 5);
		int xLevel = (int)(Math.random() * 10 + 5);

		ApoBotLevelLevel[][] level = new ApoBotLevelLevel[yLevel][xLevel];
		for (int y = 0; y < level.length; y++) {
			for (int x = 0; x < level[0].length; x++) {
				if (Math.random() * 100 > 3) {
					int random = (int)(Math.random() * 1000);
					if (random > 50) {
						level[y][x] = new ApoBotLevelLevel(ApoBotConstants.GROUND_EMPTY, 0);
					} else if (random > 25) {
						level[y][x] = new ApoBotLevelLevel(ApoBotConstants.GROUND_ORIGINAL, 0);
					} else {
						level[y][x] = new ApoBotLevelLevel(ApoBotConstants.GROUND_GOAL, 0);
					}
					random = (int)(Math.random() * 500);
					if (random < 50) {
						level[y][x].setStartHeight(2);
					} else if (random < 150) {
						level[y][x].setStartHeight(1);
					}
				} else {
					level[y][x] = new ApoBotLevelLevel(ApoBotConstants.GROUND_REAL_EMPTY, 0);
				}
			}
		}
		
		int startX = (int)(Math.random() * level[0].length - 1);
		int startY = (int)(Math.random() * level.length - 1);
		while (level[startY][startX].getGround() == ApoBotConstants.GROUND_REAL_EMPTY) {
			startX += 1;
			if (startX >= level[0].length) {
				startX = 0;
				startY += 1;
				if (startY >= level.length) {
					startY = 0;
				}
			}
		}
		Point startPoint = new Point(startX, startY);
		int directionView = ApoBotConstants.DIR_SOUTH_DOWNRIGHT;
		
		int[] menu = new int[3];
		menu[0] = 14;
		menu[1] = 7;
		menu[2] = 6;
		return new ApoBotLevel(level, startPoint, directionView, menu);
	}
	
}
