package apoSimple.puzzle;

import apoSimple.ApoSimpleConstants;

public class ApoSimplePuzzleLevelMaker {

	public static final int MAX_LEVEL = 45;
	
	public static ApoSimplePuzzleLevel getLevel(int level) {
		if (level < 0) {
			level = ApoSimplePuzzleLevelMaker.MAX_LEVEL;
		}
		if (level == 0) {
			return ApoSimplePuzzleLevelMaker.getLevelOne();
		} else if (level == 1) {
			return ApoSimplePuzzleLevelMaker.getLevelTwo();
		} else if (level == 2) {
			return ApoSimplePuzzleLevelMaker.getLevelThree();
		} else if (level == 3) {
			return ApoSimplePuzzleLevelMaker.getLevelFour();
		} else if (level == 4) {
			return ApoSimplePuzzleLevelMaker.getLevelFive();
		} else if (level == 5) {
			return ApoSimplePuzzleLevelMaker.getLevelSix();
		} else if (level == 6) {
			return ApoSimplePuzzleLevelMaker.getLevelSeven();
		} else if (level == 7) {
			return ApoSimplePuzzleLevelMaker.getLevelEight();
		} else if (level == 8) {
			return ApoSimplePuzzleLevelMaker.getLevelNine();
		} else if (level == 9) {
			return ApoSimplePuzzleLevelMaker.getLevelTen();
		} else if (level == 10) {
			return ApoSimplePuzzleLevelMaker.getLevelEleven();
		} else if (level == 11) {
			return ApoSimplePuzzleLevelMaker.getLevelTwelve();
		} else if (level == 12) {
			return ApoSimplePuzzleLevelMaker.getLevelThirteen();
		} else if (level == 13) {
			return ApoSimplePuzzleLevelMaker.getLevelFourteen();
		} else if (level == 14) {
			return ApoSimplePuzzleLevelMaker.getLevelFiveteen();
		} else if (level == 15) {
			return ApoSimplePuzzleLevelMaker.getLevelSixteen();
		} else if (level == 16) {
			return ApoSimplePuzzleLevelMaker.getLevelSeventeen();
		} else if (level == 17) {
			return ApoSimplePuzzleLevelMaker.getLevelEighteen();
		} else if (level == 18) {
			return ApoSimplePuzzleLevelMaker.getLevelNineteen();
		} else if (level == 19) {
			return ApoSimplePuzzleLevelMaker.getLevelTwenty();
		} else if (level == 20) {
			return ApoSimplePuzzleLevelMaker.getLevelTwentyOne();
		} else if (level == 21) {
			return ApoSimplePuzzleLevelMaker.getLevelTwentyTwo();
		} else if (level == 22) {
			return ApoSimplePuzzleLevelMaker.getLevelTwentyThree();
		} else if (level == 23) {
			return ApoSimplePuzzleLevelMaker.getLevelTwentyFour();
		} else if (level == 24) {
			return ApoSimplePuzzleLevelMaker.getLevelTwentyFive();
		} else if (level == 25) {
			return ApoSimplePuzzleLevelMaker.getLevelTwentySix();
		} else if (level == 26) {
			return ApoSimplePuzzleLevelMaker.getLevelTwentySeven();
		} else if (level == 27) {
			return ApoSimplePuzzleLevelMaker.getLevelTwentyEight();
		} else if (level == 28) {
			return ApoSimplePuzzleLevelMaker.getLevelTwentyNine();
		} else if (level == 29) {
			return ApoSimplePuzzleLevelMaker.getLevelThirty();
		} else if (level == 30) {
			return ApoSimplePuzzleLevelMaker.getLevelThirtyOne();
		} else if (level == 31) {
			return ApoSimplePuzzleLevelMaker.getLevelThirtyTwo();
		} else if (level == 32) {
			return ApoSimplePuzzleLevelMaker.getLevelThirtyThree();
		} else if (level == 33) {
			return ApoSimplePuzzleLevelMaker.getLevelThirtyFour();
		} else if (level == 34) {
			return ApoSimplePuzzleLevelMaker.getLevelThirtyFive();
		} else if (level == 35) {
			return ApoSimplePuzzleLevelMaker.getLevelThirtySix();
		} else if (level == 36) {
			return ApoSimplePuzzleLevelMaker.getLevelThirtySeven();
		} else if (level == 37) {
			return ApoSimplePuzzleLevelMaker.getLevelThirtyEight();
		} else if (level == 38) {
			return ApoSimplePuzzleLevelMaker.getLevelThirtyNine();
		} else if (level == 39) {
			return ApoSimplePuzzleLevelMaker.getLevelFourthy();
		} else if (level == 40) {
			return ApoSimplePuzzleLevelMaker.getLevelFourthyOne();
		} else if (level == 41) {
			return ApoSimplePuzzleLevelMaker.getLevelFourthyTwo();
		} else if (level == 42) {
			return ApoSimplePuzzleLevelMaker.getLevelFourthyThree();
		} else if (level == 43) {
			return ApoSimplePuzzleLevelMaker.getLevelFourthyFour();
		} else if (level == 44) {
			return ApoSimplePuzzleLevelMaker.getLevelFourthyFive();
		} else if (level == 45) {
			return ApoSimplePuzzleLevelMaker.getLevelFourthySix();
		}
		return null;
	}
	
	private static ApoSimplePuzzleLevel makeLevel(int[][] level, int[] steps) {
		ApoSimplePuzzleEntity[][] entities = new ApoSimplePuzzleEntity[level.length][level[0].length];
		for (int y = 0; y < entities.length; y++) {
			for (int x = 0; x < entities[0].length; x++) {
				if (level[y][x] == 1) {
					entities[y][x] = new ApoSimplePuzzleEntity(x * 65, y * 65, 60, 60, ApoSimpleConstants.DOWN);			
				} else if (level[y][x] == 2) {
					entities[y][x] = new ApoSimplePuzzleEntity(x * 65, y * 65, 60, 60, ApoSimpleConstants.BLACK_LEFT);			
				} else if (level[y][x] == 3) {
					entities[y][x] = new ApoSimplePuzzleEntity(x * 65, y * 65, 60, 60, ApoSimpleConstants.DOG_RIGHT);			
				} else if (level[y][x] == 5) {
					entities[y][x] = new ApoSimplePuzzleEntity(x * 65, y * 65, 60, 60, ApoSimpleConstants.STONE_3);			
				} else if (level[y][x] == 4) {
					entities[y][x] = new ApoSimplePuzzleEntity(x * 65, y * 65, 60, 60, ApoSimpleConstants.HIDDEN_DUCK);			
				} else if (level[y][x] == 6) {
					entities[y][x] = new ApoSimplePuzzleEntity(x * 65, y * 65, 60, 60, ApoSimpleConstants.HIDDEN_RABBIT);			
				}
			}
		}
		
		return new ApoSimplePuzzleLevel(entities, steps);
	}
	
	private static ApoSimplePuzzleLevel getLevelOne() {
		int[][] level = new int[][] {
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,1,1,0,1,0},
		};
		
		int[] steps = new int[] {1, 2};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}
	
	private static ApoSimplePuzzleLevel getLevelTwo() {
		int[][] level = new int[][] {
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,2,0,0,0},
				{0,0,0,2,2,0,0},
		};
		
		int[] steps = new int[] {1, 2};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}
	
	private static ApoSimplePuzzleLevel getLevelThree() {
		int[][] level = new int[][] {
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,1,0,0,0},
				{0,0,1,1,0,1,0},
		};
		
		int[] steps = new int[] {1, 2};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}
	
	private static ApoSimplePuzzleLevel getLevelFour() {
		int[][] level = new int[][] {
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,2,1,2,0,0},
				{0,0,1,2,1,0,0},
		};
		
		int[] steps = new int[] {1, 2};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}
	
	private static ApoSimplePuzzleLevel getLevelFive() {
		int[][] level = new int[][] {
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,2,1,0,0},
				{0,0,0,1,2,0,0},
				{0,0,0,1,2,0,0},
		};
		
		int[] steps = new int[] {1, 2};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}
	
	private static ApoSimplePuzzleLevel getLevelSix() {
		int[][] level = new int[][] {
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,1,0,0,0},
				{0,0,0,3,3,0,0},
				{0,0,0,1,1,3,0},
		};
		
		int[] steps = new int[] {1, 2};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}
	
	private static ApoSimplePuzzleLevel getLevelSeven() {
		int[][] level = new int[][] {
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,3,0,0},
				{0,0,2,0,3,0,0},
				{0,0,3,2,2,0,0},
				{0,0,2,3,3,0,0},
				{0,0,2,2,3,0,0},
		};
		
		int[] steps = new int[] {1, 2};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}
	
	private static ApoSimplePuzzleLevel getLevelEight() {
		int[][] level = new int[][] {
				{0,0,0,0,0,0,0},
				{0,0,0,3,0,0,0},
				{0,0,0,1,0,0,0},
				{0,0,3,2,1,0,0},
				{0,0,2,1,2,0,0},
				{0,3,2,3,2,1,0},
				{0,3,3,2,1,1,0},
		};
		
		int[] steps = new int[] {1, 2};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}
	
	private static ApoSimplePuzzleLevel getLevelNine() {
		int[][] level = new int[][] {
				{0,0,0,1,0,0,0},
				{0,0,0,2,0,0,0},
				{0,0,0,1,0,0,0},
				{0,0,0,2,0,0,0},
				{0,0,2,1,2,0,0},
				{0,0,2,1,2,0,0},
				{0,0,1,2,1,0,0},
		};
		
		int[] steps = new int[] {1, 2};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}

	private static ApoSimplePuzzleLevel getLevelTen() {
		int[][] level = new int[][] {
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,3,0,3,0,3,0},
		};
		
		int[] steps = new int[] {2, 3};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}

	private static ApoSimplePuzzleLevel getLevelEleven() {
		int[][] level = new int[][] {
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,3,2,1,0,0},
				{0,0,1,3,2,0,0},
				{0,0,1,3,2,0,0},
		};
		
		int[] steps = new int[] {2, 3};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}

	private static ApoSimplePuzzleLevel getLevelTwelve() {
		int[][] level = new int[][] {
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,1,2,0,0,0},
				{0,0,1,2,0,0,0},
				{0,0,2,3,0,0,0},
				{0,0,1,3,3,0,0},
		};
		
		int[] steps = new int[] {2, 3};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}

	private static ApoSimplePuzzleLevel getLevelThirteen() {
		int[][] level = new int[][] {
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,3,0,0,0,0},
				{0,0,2,0,0,0,0},
				{0,0,2,3,3,0,0},
				{0,0,1,1,2,1,0},
		};
		
		int[] steps = new int[] {2, 3};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}

	private static ApoSimplePuzzleLevel getLevelFourteen() {
		int[][] level = new int[][] {
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,2,2,0,0},
				{0,0,2,1,3,0,0},
				{0,0,1,1,3,3,0},
		};
		
		int[] steps = new int[] {2, 3};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}

	private static ApoSimplePuzzleLevel getLevelFiveteen() {
		int[][] level = new int[][] {
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,1,1,0,1,0,0},
				{0,1,1,0,1,1,0},
		};
		
		int[] steps = new int[] {2, 3};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}

	private static ApoSimplePuzzleLevel getLevelSixteen() {
		int[][] level = new int[][] {
				{0,0,0,0,0,0,0},
				{0,0,0,4,0,0,0},
				{0,0,1,3,1,0,0},
				{0,0,4,5,4,0,0},
				{0,1,5,2,3,1,0},
				{0,5,6,4,6,3,0},
				{1,4,4,2,6,2,1},
		};
		
		int[] steps = new int[] {2, 3};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}

	private static ApoSimplePuzzleLevel getLevelSeventeen() {
		int[][] level = new int[][] {
				{0,0,0,0,0,0,0},
				{0,0,0,3,0,0,0},
				{0,0,0,2,0,0,0},
				{0,0,4,2,0,0,0},
				{0,0,4,1,0,0,0},
				{0,0,3,2,4,0,0},
				{0,0,1,1,3,0,0},
		};
		
		int[] steps = new int[] {2, 3};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}

	private static ApoSimplePuzzleLevel getLevelEighteen() {
		int[][] level = new int[][] {
				{0,0,0,0,0,0,0},
				{0,0,0,1,0,0,0},
				{0,0,0,2,0,0,0},
				{0,0,1,5,1,0,0},
				{0,0,5,2,5,0,0},
				{0,0,2,3,2,0,0},
				{0,1,3,2,3,1,0},
		};
		
		int[] steps = new int[] {2, 3};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}

	private static ApoSimplePuzzleLevel getLevelNineteen() {
		int[][] level = new int[][] {
				{0,0,0,0,0,0,0},
				{0,0,0,2,0,0,0},
				{0,0,0,1,0,0,0},
				{0,0,0,5,0,0,0},
				{0,0,0,5,0,0,0},
				{0,0,1,3,2,0,0},
				{0,1,3,5,2,3,0},
		};
		
		int[] steps = new int[] {2, 3};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}

	private static ApoSimplePuzzleLevel getLevelTwenty() {
		int[][] level = new int[][] {
				{0,0,0,2,0,0,0},
				{0,0,2,1,0,0,0},
				{0,0,1,2,1,0,0},
				{0,0,2,1,2,0,0},
				{0,0,1,2,1,0,0},
				{0,0,2,1,2,0,0},
				{0,0,1,2,1,0,0},
		};
		
		int[] steps = new int[] {2, 3};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}

	private static ApoSimplePuzzleLevel getLevelTwentyOne() {
		int[][] level = new int[][] {
				{0,0,0,5,0,0,0},
				{0,0,4,1,2,0,0},
				{0,0,2,4,2,0,0},
				{0,0,2,1,1,0,0},
				{0,0,1,3,2,0,0},
				{0,0,3,2,5,0,0},
				{0,1,1,3,4,5,0},
		};
		
		int[] steps = new int[] {2, 3};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}
	
	private static ApoSimplePuzzleLevel getLevelTwentyTwo() {
		int[][] level = new int[][] {
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,4,4,0,0,0},
				{0,4,3,3,0,0,0},
				{0,3,1,1,3,0,0},
				{0,1,2,2,1,2,3},
				{0,1,2,1,2,3,2},
		};
		
		int[] steps = new int[] {2, 3};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}

	private static ApoSimplePuzzleLevel getLevelTwentyThree() {
		int[][] level = new int[][] {
				{0,3,0,0,0,0,0},
				{0,5,3,0,0,0,0},
				{0,1,5,0,0,0,0},
				{0,4,1,3,0,0,0},
				{0,3,4,5,0,0,0},
				{0,4,3,1,2,0,0},
				{0,1,1,3,1,2,2},
		};
		
		int[] steps = new int[] {2, 3};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}
	
	private static ApoSimplePuzzleLevel getLevelTwentyFour() {
		int[][] level = new int[][] {
				{0,0,0,0,0,0,0},
				{0,0,0,2,2,0,0},
				{0,0,1,3,3,0,0},
				{0,0,2,3,3,0,0},
				{0,0,3,1,2,0,0},
				{0,0,2,2,1,3,0},
				{1,1,2,2,1,2,0},
		};
		
		int[] steps = new int[] {2, 3};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}
	
	private static ApoSimplePuzzleLevel getLevelTwentyFive() {
		int[][] level = new int[][] {
				{0,0,0,0,1,0,0},
				{0,0,0,0,2,0,0},
				{0,0,0,1,1,0,0},
				{0,0,0,1,2,0,0},
				{0,0,0,3,2,1,0},
				{0,0,0,2,3,3,0},
				{0,0,0,2,2,1,0},
		};
		
		int[] steps = new int[] {2, 3};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}
	
	private static ApoSimplePuzzleLevel getLevelTwentySix() {
		int[][] level = new int[][] {
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,3,0,1,0,0},
				{0,0,1,3,3,0,0},
				{0,0,2,1,4,0,0},
				{0,0,1,2,2,0,0},
				{0,0,4,4,1,0,1},
		};
		
		int[] steps = new int[] {2, 3};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}
	
	private static ApoSimplePuzzleLevel getLevelTwentySeven() {
		int[][] level = new int[][] {
				{0,0,0,1,0,0,0},
				{0,0,0,1,4,0,0},
				{0,0,0,5,5,0,0},
				{0,0,0,2,2,5,0},
				{0,0,4,3,4,2,0},
				{0,0,2,1,3,1,0},
				{0,1,1,2,2,3,0},
		};
		
		int[] steps = new int[] {2, 3};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}

	private static ApoSimplePuzzleLevel getLevelTwentyEight() {
		int[][] level = new int[][] {
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,5,1,0,2,0},
				{0,3,1,5,5,2,0},
				{0,1,3,3,2,1,0},
		};
		
		int[] steps = new int[] {3, 4};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}

	private static ApoSimplePuzzleLevel getLevelTwentyNine() {
		int[][] level = new int[][] {
				{0,0,0,2,0,0,0},
				{0,0,0,2,0,0,0},
				{0,0,0,1,0,0,0},
				{0,0,0,2,0,0,0},
				{0,0,0,2,0,0,0},
				{0,0,0,1,0,0,0},
				{0,0,1,1,0,0,0},
		};
		
		int[] steps = new int[] {3, 4};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}

	private static ApoSimplePuzzleLevel getLevelThirty() {
		int[][] level = new int[][] {
				{0,0,0,1,0,0,0},
				{0,0,0,5,1,0,0},
				{0,0,1,1,5,0,6},
				{0,0,5,3,1,1,6},
				{0,1,1,3,2,2,3},
				{0,3,3,2,3,6,6},
				{1,2,2,3,6,6,3},
		};
		
		int[] steps = new int[] {3, 4};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}
	
	private static ApoSimplePuzzleLevel getLevelThirtyOne() {
		int[][] level = new int[][] {
				{0,0,0,2,0,0,0},
				{0,1,0,1,0,0,0},
				{0,1,0,4,0,0,0},
				{0,4,4,3,0,0,0},
				{0,2,5,2,0,0,0},
				{0,3,3,1,0,0,0},
				{0,1,1,5,0,5,0},
		};
		
		int[] steps = new int[] {3, 4};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}

	private static ApoSimplePuzzleLevel getLevelThirtyTwo() {
		int[][] level = new int[][] {
				{0,0,0,0,0,0,0},
				{0,0,0,0,1,0,0},
				{0,0,0,0,4,0,1},
				{0,0,0,0,4,2,4},
				{0,0,0,3,1,2,2},
				{0,0,0,2,2,1,1},
				{0,0,0,3,3,2,1},
		};
		
		int[] steps = new int[] {3, 4};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}
	
	private static ApoSimplePuzzleLevel getLevelThirtyThree() {
		int[][] level = new int[][] {
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,5,5,0},
				{0,0,0,0,1,5,0},
				{0,0,0,4,4,1,0},
				{0,0,4,2,3,1,1},
				{0,3,3,2,1,2,1},
		};
		
		int[] steps = new int[] {3, 4};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}
	
	private static ApoSimplePuzzleLevel getLevelThirtyFour() {
		int[][] level = new int[][] {
				{0,0,0,0,1,0,0},
				{0,0,0,0,2,0,0},
				{0,0,3,0,1,1,0},
				{0,0,2,0,1,4,1},
				{0,0,1,0,3,4,4},
				{0,0,2,2,3,3,1},
				{1,0,1,2,2,3,3},
		};
		
		int[] steps = new int[] {3, 4};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}

	private static ApoSimplePuzzleLevel getLevelThirtyFive() {
		int[][] level = new int[][] {
				{0,0,0,0,0,0,0},
				{0,0,0,0,1,0,0},
				{0,0,1,3,2,0,0},
				{0,0,2,2,1,0,0},
				{0,0,3,4,1,0,0},
				{0,3,4,5,5,0,0},
				{0,4,1,1,5,0,0},
		};
		
		int[] steps = new int[] {3, 4};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}

	private static ApoSimplePuzzleLevel getLevelThirtySix() {
		int[][] level = new int[][] {
				{0,0,0,0,0,0,0},
				{0,0,0,0,1,0,0},
				{0,0,0,0,4,0,0},
				{0,0,1,0,4,0,0},
				{0,0,3,0,3,1,1},
				{0,0,4,0,1,1,2},
				{1,1,3,0,2,2,1},
		};
		
		int[] steps = new int[] {3, 4};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}

	private static ApoSimplePuzzleLevel getLevelThirtySeven() {
		int[][] level = new int[][] {
				{0,0,0,1,0,0,0},
				{0,0,0,2,0,0,0},
				{0,0,1,1,0,0,0},
				{0,0,1,5,0,0,0},
				{0,0,5,4,5,0,0},
				{0,4,4,3,3,0,0},
				{1,1,3,2,2,0,0},
		};
		
		int[] steps = new int[] {3, 4};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}
	
	private static ApoSimplePuzzleLevel getLevelThirtyEight() {
		int[][] level = new int[][] {
				{0,0,0,0,0,0,0},
				{0,0,1,0,0,0,0},
				{0,2,1,0,0,0,0},
				{1,1,2,0,0,0,0},
				{2,3,5,0,0,0,0},
				{3,1,4,5,0,0,0},
				{1,4,3,4,5,0,0},
		};
		
		int[] steps = new int[] {3, 4};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}

	private static ApoSimplePuzzleLevel getLevelThirtyNine() {
		int[][] level = new int[][] {
				{0,0,0,0,4,0,0},
				{0,0,0,0,3,0,0},
				{0,0,0,0,2,0,0},
				{0,0,3,0,1,0,0},
				{0,0,1,3,2,0,0},
				{0,0,2,2,4,4,2},
				{0,0,1,1,2,1,1},
		};
		
		int[] steps = new int[] {4, 5};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}

	private static ApoSimplePuzzleLevel getLevelFourthy() {
		int[][] level = new int[][] {
				{0,0,0,0,0,0,0},
				{0,0,0,5,0,0,0},
				{0,0,0,1,1,0,0},
				{0,0,4,2,4,0,0},
				{0,0,2,5,3,1,0},
				{0,0,1,3,5,4,0},
				{0,0,2,1,1,3,0},
		};
		
		int[] steps = new int[] {4, 5};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}
	
	private static ApoSimplePuzzleLevel getLevelFourthyOne() {
		int[][] level = new int[][] {
				{0,0,3,0,0,0,0},
				{0,0,2,0,0,0,0},
				{0,0,4,2,0,0,0},
				{0,0,4,3,2,0,0},
				{0,0,3,2,4,0,0},
				{0,0,1,2,3,0,0},
				{1,1,3,3,2,0,0},
		};
		
		int[] steps = new int[] {4, 5};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}
	
	private static ApoSimplePuzzleLevel getLevelFourthyTwo() {
		int[][] level = new int[][] {
				{0,0,1,0,0,0,0},
				{0,0,1,0,0,0,0},
				{0,0,4,0,0,0,0},
				{0,0,1,0,1,0,0},
				{0,0,3,1,1,0,0},
				{0,1,4,4,2,0,0},
				{3,3,2,2,1,0,1},
		};
		
		int[] steps = new int[] {4, 5};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}
	
	private static ApoSimplePuzzleLevel getLevelFourthyThree() {
		int[][] level = new int[][] {
				{0,0,0,0,0,1,0},
				{0,0,0,0,0,2,0},
				{0,0,0,0,0,2,0},
				{0,0,0,0,0,1,0},
				{0,0,0,2,1,3,0},
				{0,0,0,3,1,1,0},
				{0,0,0,1,3,1,0},
		};
		
		int[] steps = new int[] {4, 5};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}
	
	private static ApoSimplePuzzleLevel getLevelFourthyFour() {
		int[][] level = new int[][] {
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,2,0,0,0},
				{0,0,2,1,0,0,0},
				{0,2,2,1,0,0,0},
				{0,2,1,2,0,0,0},
				{2,3,3,1,1,3,1},
		};
		
		int[] steps = new int[] {4, 5};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}
	
	private static ApoSimplePuzzleLevel getLevelFourthyFive() {
		int[][] level = new int[][] {
				{0,0,0,0,0,0,0},
				{0,0,0,4,0,0,0},
				{0,0,0,1,4,0,0},
				{0,0,2,1,1,4,0},
				{0,0,1,2,2,3,0},
				{0,1,2,3,2,2,0},
				{0,1,2,1,3,2,0},
		};
		
		int[] steps = new int[] {4, 5};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}
	
	private static ApoSimplePuzzleLevel getLevelFourthySix() {
		int[][] level = new int[][] {
				{0,0,0,0,0,0,0},
				{0,0,0,2,0,2,0},
				{0,0,0,1,0,2,0},
				{0,0,0,1,4,1,2},
				{0,0,0,3,3,1,3},
				{0,0,0,2,2,4,4},
				{0,1,0,1,1,2,2},
		};
		
		int[] steps = new int[] {4, 5};
		
		return ApoSimplePuzzleLevelMaker.makeLevel(level, steps);
	}
}
