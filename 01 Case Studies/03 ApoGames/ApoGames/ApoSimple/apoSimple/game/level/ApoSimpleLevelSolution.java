package apoSimple.game.level;

import java.awt.Point;
import java.util.ArrayList;

import apoSimple.ApoSimpleConstants;

public class ApoSimpleLevelSolution {
	
	private static ArrayList<Point> curBestSolution;
	private static ArrayList<Point> points;
	
	public static void main(String[] args) {
//		ArrayList<Point> solutionsPoints = getMinSolution("05050505050505050505050505050505050505050505050505050505050505050505050505050505050505050204040505123", new ArrayList<Point>(), false, 5, new ArrayList<Point>());

		ArrayList<Point> solutionsPoints = getMinSolution("03040302030202010101030401010301030102040402030400010401030104030103010104030402010102020103010101234", 3);
		
		System.out.println("ready with: "+solutionsPoints.size());
		for (Point p : solutionsPoints) {
			System.out.println(p);
		}
	}
	
	public static ArrayList<Point> getMinSolution(String levelString, int maxSteps) {
		curBestSolution = new ArrayList<Point>();
		points = new ArrayList<Point>();
		
		getMinSolution(levelString, false, maxSteps);
		
		return curBestSolution;
	}
	
	private static void getMinSolution(String levelString, boolean bReady, int maxSteps) {
		if ((points.size() > maxSteps) || ((curBestSolution.size() > 0) && (points.size() >= curBestSolution.size()))) {
			return;
		}
		ApoSimpleLevel level = new ApoSimpleLevel(levelString);
		for (int y = 0; y < level.getLevel().length; y++) {
			for (int x = 0; x < level.getLevel()[0].length; x++) {
				if ((level.getLevel()[y][x] != ApoSimpleConstants.DOG_UP) &&
					(level.getLevel()[y][x] != ApoSimpleConstants.DOG_LEFT) &&
					(level.getLevel()[y][x] != ApoSimpleConstants.DOG_RIGHT) &&
					(level.getLevel()[y][x] != ApoSimpleConstants.DOG_DOWN) &&
					(level.getLevel()[y][x] != ApoSimpleConstants.EMPTY) &&
					(level.getLevel()[y][x] != ApoSimpleConstants.FENCE) &&
					(level.getLevel()[y][x] != ApoSimpleConstants.STONE_1) &&
					(level.getLevel()[y][x] != ApoSimpleConstants.STONE_2) &&
					(level.getLevel()[y][x] != ApoSimpleConstants.STONE_3) &&
					(level.getLevel()[y][x] != ApoSimpleConstants.REAL_EMPTY)) {
//					System.out.println(x+" "+y);
					int[][] newLevel = ApoSimpleLevelSolution.canBeSolved(level, x, y, points.size());
					if (ApoSimpleLevelSolution.isSolved(newLevel)) {
						Point p = new Point(x, y);
						points.add(p);
						maxSteps = points.size();
						bReady = true;
						ArrayList<Point> curBest = new ArrayList<Point>();
						for (int i = 0; i < points.size(); i++) {
							curBest.add(new Point(points.get(i).x, points.get(i).y));
						}
						if ((curBest.size() < curBestSolution.size()) || (curBestSolution.size() <= 0)) {
							curBestSolution = curBest;
						}
						points.remove(p);
						return;
					}
				}
			}
		}
		
		for (int y = 0; y < level.getLevel().length; y++) {
			for (int x = 0; x < level.getLevel()[0].length; x++) {
				if ((level.getLevel()[y][x] != ApoSimpleConstants.DOG_UP) &&
					(level.getLevel()[y][x] != ApoSimpleConstants.DOG_LEFT) &&
					(level.getLevel()[y][x] != ApoSimpleConstants.DOG_RIGHT) &&
					(level.getLevel()[y][x] != ApoSimpleConstants.DOG_DOWN) &&
					(level.getLevel()[y][x] != ApoSimpleConstants.EMPTY) &&
					(level.getLevel()[y][x] != ApoSimpleConstants.FENCE) &&
					(level.getLevel()[y][x] != ApoSimpleConstants.STONE_1) &&
					(level.getLevel()[y][x] != ApoSimpleConstants.STONE_2) &&
					(level.getLevel()[y][x] != ApoSimpleConstants.STONE_3) &&
					(level.getLevel()[y][x] != ApoSimpleConstants.REAL_EMPTY)) {
					Point p = new Point(x, y);
					points.add(p);
					int[][] newLevel = ApoSimpleLevelSolution.canBeSolved(level, x, y, points.size());
					if (maxSteps > points.size()) {
						String newLevelString = getNewLevelString(newLevel);
						getMinSolution(newLevelString, bReady, maxSteps);
					}
					points.remove(p);
				}
			}
		}
	}
	
	private static String getNewLevelString(int[][] level) {
		for (int y = level.length - 1; y >= 0; y--) {
			for (int x = 0; x < level[0].length; x++) {
				if ((level[y][x] >= ApoSimpleConstants.BLACK_UP) &&
					(level[y][x] <= ApoSimpleConstants.BLACK_RIGHT)) {
					level[y][x] = level[y][x] + 1;
					if (level[y][x] > ApoSimpleConstants.BLACK_RIGHT) {
						level[y][x] = ApoSimpleConstants.BLACK_UP;
					}
				}
				if ((level[y][x] >= ApoSimpleConstants.DOUBLE_BLACK_UP) &&
					(level[y][x] <= ApoSimpleConstants.DOUBLE_BLACK_RIGHT)) {
					level[y][x] = level[y][x] + 1;
					if (level[y][x] > ApoSimpleConstants.DOUBLE_BLACK_RIGHT) {
						level[y][x] = ApoSimpleConstants.DOUBLE_BLACK_UP;
					}
				}
			}
		}
		
		
		for (int y = level.length - 1; y >= 0; y--) {
			for (int x = 0; x < level[0].length; x++) {
				if (level[y][x] == ApoSimpleConstants.REAL_EMPTY) {
					for (int curY = y - 1; curY >= 0; curY--) {
						if (level[curY][x] == ApoSimpleConstants.FENCE) {
							break;
						}
						if (level[curY][x] != ApoSimpleConstants.REAL_EMPTY) {
							level[y][x] = level[curY][x];
							level[curY][x] = ApoSimpleConstants.REAL_EMPTY;
							break;
						}
					}
				}
			}
		}
		return ApoSimpleLevel.getLevelString(level, 1);
	}
	
	private static int[][] canBeSolved(ApoSimpleLevel simpleLevel, int x, int y, int curSize) {
		int[][] level = simpleLevel.getCopyFromLevel();

//		System.out.println("teste bei "+x+" "+y+" "+level[y][x]+" "+ApoSimpleLevelSolution.getDirection(new Point(0, 0), level[y][x]));
		Point oldDirection = new Point(0, 0);
		int size = 0;
		int step = 0;
		while ((x >= 0) && (x < level[0].length) && (y >= 0) && (y < level.length)) {
			if (level[y][x] == ApoSimpleConstants.FENCE) {
				break;
			}
			Point direction = ApoSimpleLevelSolution.getDirection(oldDirection, level[y][x], size);
			if (size > ApoSimpleConstants.MIN_DOG_SIZE) {
				if (level[y][x] != ApoSimpleConstants.REAL_EMPTY) {
					if (level[y][x] == ApoSimpleConstants.DOUBLE_LEFT) {
						level[y][x] = ApoSimpleConstants.LEFT;
					} else if (level[y][x] == ApoSimpleConstants.DOUBLE_RIGHT) {
						level[y][x] = ApoSimpleConstants.RIGHT;
					} else if (level[y][x] == ApoSimpleConstants.DOUBLE_UP) {
						level[y][x] = ApoSimpleConstants.UP;
					} else if (level[y][x] == ApoSimpleConstants.DOUBLE_DOWN) {
						level[y][x] = ApoSimpleConstants.DOWN;
					} else if (level[y][x] == ApoSimpleConstants.DOUBLE_BLACK_LEFT) {
						level[y][x] = ApoSimpleConstants.BLACK_LEFT;
					} else if (level[y][x] == ApoSimpleConstants.DOUBLE_BLACK_RIGHT) {
						level[y][x] = ApoSimpleConstants.BLACK_RIGHT;
					} else if (level[y][x] == ApoSimpleConstants.DOUBLE_BLACK_UP) {
						level[y][x] = ApoSimpleConstants.BLACK_UP;
					} else if (level[y][x] == ApoSimpleConstants.DOUBLE_BLACK_DOWN) {
						level[y][x] = ApoSimpleConstants.BLACK_DOWN;
					} else if (level[y][x] == ApoSimpleConstants.STONE_2) {
						level[y][x] = ApoSimpleConstants.STONE_1;
					} else if (level[y][x] == ApoSimpleConstants.STONE_3) {
						level[y][x] = ApoSimpleConstants.STONE_2;
					} else if (level[y][x] == ApoSimpleConstants.STONE_1) {
						level[y][x] = ApoSimpleConstants.EMPTY;
					} else {
						level[y][x] = ApoSimpleConstants.REAL_EMPTY;
					}
				}
			} else {
				if (level[y][x] != ApoSimpleConstants.REAL_EMPTY) {
					if ((level[y][x] == ApoSimpleConstants.LEFT) || 
						(level[y][x] == ApoSimpleConstants.RIGHT) ||
						(level[y][x] == ApoSimpleConstants.UP) ||
						(level[y][x] == ApoSimpleConstants.DOWN) ||
						(level[y][x] == ApoSimpleConstants.BLACK_DOWN) ||
						(level[y][x] == ApoSimpleConstants.BLACK_LEFT) ||
						(level[y][x] == ApoSimpleConstants.BLACK_RIGHT) ||
						(level[y][x] == ApoSimpleConstants.BLACK_UP) ||
						(level[y][x] == ApoSimpleConstants.DOUBLE_LEFT) || 
						(level[y][x] == ApoSimpleConstants.DOUBLE_RIGHT) ||
						(level[y][x] == ApoSimpleConstants.DOUBLE_UP) ||
						(level[y][x] == ApoSimpleConstants.DOUBLE_DOWN) ||
						(level[y][x] == ApoSimpleConstants.DOUBLE_BLACK_DOWN) ||
						(level[y][x] == ApoSimpleConstants.DOUBLE_BLACK_LEFT) ||
						(level[y][x] == ApoSimpleConstants.DOUBLE_BLACK_RIGHT) ||
						(level[y][x] == ApoSimpleConstants.DOUBLE_BLACK_UP)) {
						size += 1;
					}
					
					if ((level[y][x] == ApoSimpleConstants.LEFT) || 
						(level[y][x] == ApoSimpleConstants.RIGHT) ||
						(level[y][x] == ApoSimpleConstants.EMPTY) ||
						(level[y][x] == ApoSimpleConstants.UP) ||
						(level[y][x] == ApoSimpleConstants.DOWN) ||
						(level[y][x] == ApoSimpleConstants.BLACK_DOWN) ||
						(level[y][x] == ApoSimpleConstants.BLACK_LEFT) ||
						(level[y][x] == ApoSimpleConstants.BLACK_RIGHT) ||
						(level[y][x] == ApoSimpleConstants.BLACK_UP)) {
						level[y][x] = ApoSimpleConstants.REAL_EMPTY;
					} else if (level[y][x] == ApoSimpleConstants.DOUBLE_LEFT) {
						level[y][x] = ApoSimpleConstants.LEFT;
					} else if (level[y][x] == ApoSimpleConstants.DOUBLE_RIGHT) {
						level[y][x] = ApoSimpleConstants.RIGHT;
					} else if (level[y][x] == ApoSimpleConstants.DOUBLE_UP) {
						level[y][x] = ApoSimpleConstants.UP;
					} else if (level[y][x] == ApoSimpleConstants.DOUBLE_DOWN) {
						level[y][x] = ApoSimpleConstants.DOWN;
					} else if (level[y][x] == ApoSimpleConstants.DOUBLE_BLACK_LEFT) {
						level[y][x] = ApoSimpleConstants.BLACK_LEFT;
					} else if (level[y][x] == ApoSimpleConstants.DOUBLE_BLACK_RIGHT) {
						level[y][x] = ApoSimpleConstants.BLACK_RIGHT;
					} else if (level[y][x] == ApoSimpleConstants.DOUBLE_BLACK_UP) {
						level[y][x] = ApoSimpleConstants.BLACK_UP;
					} else if (level[y][x] == ApoSimpleConstants.DOUBLE_BLACK_DOWN) {
						level[y][x] = ApoSimpleConstants.BLACK_DOWN;
					} else if (level[y][x] == ApoSimpleConstants.STONE_2) {
						level[y][x] = ApoSimpleConstants.STONE_1;
					} else if (level[y][x] == ApoSimpleConstants.STONE_3) {
						level[y][x] = ApoSimpleConstants.STONE_2;
					} else if (level[y][x] == ApoSimpleConstants.STONE_1) {
						level[y][x] = ApoSimpleConstants.EMPTY;
					}
				}
			}
			step += 1;
			x += direction.x;
			y += direction.y;
			oldDirection = direction;
			
			if (step > 200) {
				break;
			}
		}
		
		return level;
	}
	
	private static boolean isSolved(int[][] level) {
		for (int y = 0; y < level.length; y++) {
			for (int x = 0; x < level[0].length; x++) {
				if ((level[y][x] != ApoSimpleConstants.REAL_EMPTY) && (level[y][x] != ApoSimpleConstants.FENCE)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static Point getDirection(Point oldDirection, int value, int size) {
		if ((value == ApoSimpleConstants.DOWN) || (value == ApoSimpleConstants.BLACK_DOWN) || (value == ApoSimpleConstants.DOUBLE_BLACK_DOWN) || (value == ApoSimpleConstants.DOUBLE_DOWN)) {
			return new Point(0, +1);
		} else if ((value == ApoSimpleConstants.UP) || (value == ApoSimpleConstants.BLACK_UP) || (value == ApoSimpleConstants.DOUBLE_BLACK_UP) || (value == ApoSimpleConstants.DOUBLE_UP)) {
			return new Point(0, -1);
		} else if ((value == ApoSimpleConstants.LEFT) || (value == ApoSimpleConstants.BLACK_LEFT) || (value == ApoSimpleConstants.DOUBLE_BLACK_LEFT) || (value == ApoSimpleConstants.DOUBLE_LEFT)) {
			return new Point(1, 0);
		} else if ((value == ApoSimpleConstants.RIGHT) || (value == ApoSimpleConstants.BLACK_RIGHT) || (value == ApoSimpleConstants.DOUBLE_BLACK_RIGHT) || (value == ApoSimpleConstants.DOUBLE_RIGHT)) {
			return new Point(-1, 0);
		} else if (value == ApoSimpleConstants.EMPTY) {
			return oldDirection;
		} else if ((value >= ApoSimpleConstants.DOG_UP) && (value <= ApoSimpleConstants.DOG_RIGHT)) {
			if (size <= ApoSimpleConstants.MIN_DOG_SIZE) {
				return new Point(-oldDirection.x, -oldDirection.y);
			}
		} else if ((value >= ApoSimpleConstants.STONE_3) && (value <= ApoSimpleConstants.STONE_1)) {
			return new Point(-oldDirection.x, -oldDirection.y);
		}
		return oldDirection;
	}
}
