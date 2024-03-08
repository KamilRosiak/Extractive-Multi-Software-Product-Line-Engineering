package apoNotSoSimple.level;

public class ApoNotSoSimpleLevelGeneration {

	public static final ApoNotSoSimpleLevel getLevel(int level) {
		if (level == 0) {
			return ApoNotSoSimpleLevelGeneration.getFirstLevel();
		} else if (level == 1) {
			return ApoNotSoSimpleLevelGeneration.getSecondLevel();
		} else if (level == 2) {
			return ApoNotSoSimpleLevelGeneration.getThirdLevel();
		} else if (level == 3) {
			return ApoNotSoSimpleLevelGeneration.getFourthLevel();
		} else if (level == 4) {
			return ApoNotSoSimpleLevelGeneration.getFifthLevel();
		} else if (level == 5) {
			return ApoNotSoSimpleLevelGeneration.getSixLevel();
		} else if (level == 6) {
			return ApoNotSoSimpleLevelGeneration.getSevenLevel();
		} else if (level == 7) {
			return ApoNotSoSimpleLevelGeneration.getEightLevel();
		} else if (level == 8) {
			return ApoNotSoSimpleLevelGeneration.getNineLevel();
		} else if (level == 9) {
			return ApoNotSoSimpleLevelGeneration.getTenLevel();
		} else if (level == 10) {
			return ApoNotSoSimpleLevelGeneration.getElevenLevel();
		} else if (level == 11) {
			return ApoNotSoSimpleLevelGeneration.getTwelveLevel();
		} else if (level == 12) {
			return ApoNotSoSimpleLevelGeneration.getThirteenLevel();
		} else if (level == 13) {
			return ApoNotSoSimpleLevelGeneration.getFourteenLevel();
		} else if (level == 14) {
			return ApoNotSoSimpleLevelGeneration.getFifteenLevel();
		} else if (level == 15) {
			return ApoNotSoSimpleLevelGeneration.getSixteenLevel();
		} else if (level == 16) {
			return ApoNotSoSimpleLevelGeneration.getSeventeenLevel();
		} else if (level == 17) {
			return ApoNotSoSimpleLevelGeneration.getEighteenLevel();
		} else if (level == 18) {
			return ApoNotSoSimpleLevelGeneration.getNineteenLevel();
		} else if (level == 19) {
			return ApoNotSoSimpleLevelGeneration.getTwentyLevel();
		} else if (level == 20) {
			return ApoNotSoSimpleLevelGeneration.getTwentyOneLevel();
		} else if (level == 21) {
			return ApoNotSoSimpleLevelGeneration.getTwentyTwoLevel();
		} else if (level == 22) {
			return ApoNotSoSimpleLevelGeneration.getTwentyThreeLevel();
		} else if (level == 23) {
			return ApoNotSoSimpleLevelGeneration.getTwentyFourLevel();
		} else if (level == 24) {
			return ApoNotSoSimpleLevelGeneration.getTwentyFifeLevel();
		} else if (level == 25) {
			return ApoNotSoSimpleLevelGeneration.getTwentySixLevel();
		} else if (level == 26) {
			return ApoNotSoSimpleLevelGeneration.getTwentySevenLevel();
		} else if (level == 27) {
			return ApoNotSoSimpleLevelGeneration.getTwentyEightLevel();
		} else if (level == 28) {
			return ApoNotSoSimpleLevelGeneration.getTwentyNineLevel();
		} else if (level == 29) {
			return ApoNotSoSimpleLevelGeneration.getThirtyLevel();
		} else if (level == 30) {
			return ApoNotSoSimpleLevelGeneration.getThirtyOneLevel();
		}
		return null;
	}
	
	private static final ApoNotSoSimpleLevel getFirstLevel() {
		byte[][][] level = new byte[1][3][10];
		byte[][] firstLevel = new byte[][] {
				{0,0,0,0,0,0,0,0,0,0},
				{2,0,0,0,0,0,0,0,1,9},
				{0,0,0,0,0,0,0,0,0,0},
		};
		level[0] = firstLevel;
		String[] instructions = new String[2];
		instructions[0] = "Move the player with the arrow keys";
		instructions[1] = "Reach the finish in every level";
		String levelName = "FIRST";
		ApoNotSoSimpleLevel simpleLevel = new ApoNotSoSimpleLevel(level, instructions, levelName);
		return simpleLevel;
	}
	
	private static final ApoNotSoSimpleLevel getSecondLevel() {
		byte[][][] level = new byte[1][3][10];
		byte[][] firstLevel = new byte[][] {
				{0,0,0,1,1,1,1,0,0,0},
				{2,1,0,0,1,0,0,0,1,0},
				{0,1,1,0,0,0,1,0,1,9},
		};
		level[0] = firstLevel;
		String[] instructions = new String[2];
		instructions[0] = "Find a way through the labyrinth";
		instructions[1] = "It's easy, isn't it?";
		String levelName = "LABYRINTH";
		ApoNotSoSimpleLevel simpleLevel = new ApoNotSoSimpleLevel(level, instructions, levelName);
		return simpleLevel;
	}
	
	private static final ApoNotSoSimpleLevel getThirdLevel() {
		byte[][][] level = new byte[1][3][10];
		byte[][] firstLevel = new byte[][] {
				{0,0,0,0,1,1,0,0,0,0},
				{2,0,1,1,1,1,1,1,0,9},
				{0,0,1,0,0,0,0,1,0,0},
		};
		level[0] = firstLevel;
		String[] instructions = new String[2];
		instructions[0] = "What to do now? Perhaps you will";
		instructions[1] = "find a way through the walls.";
		String levelName = "THROUGH";
		ApoNotSoSimpleLevel simpleLevel = new ApoNotSoSimpleLevel(level, instructions, levelName);
		return simpleLevel;
	}
	
	private static final ApoNotSoSimpleLevel getFourthLevel() {
		byte[][][] level = new byte[2][3][10];
		byte[][] firstLevel = new byte[][] {
				{0,0,1,0,0,0,1,1,0,0},
				{0,0,1,1,0,0,0,2,0,9},
				{0,0,0,0,0,0,0,0,0,0},
		};
		byte[][] secondLevel = new byte[][] {
				{0,0,0,0,0,0,0,0,1,0},
				{0,9,0,0,1,0,1,2,1,0},
				{0,0,0,0,1,0,0,0,1,0},
		};
		level[0] = firstLevel;
		level[1] = secondLevel;

		String[] instructions = new String[3];
		instructions[0] = "I see double?";
		instructions[1] = "That's crazy!";
		instructions[2] = "Lamer will press 'c'";
		String levelName = "DOUBLE";
		ApoNotSoSimpleLevel simpleLevel = new ApoNotSoSimpleLevel(level, instructions, levelName);
		return simpleLevel;
	}
	
	private static final ApoNotSoSimpleLevel getFifthLevel() {
		byte[][][] level = new byte[1][3][10];
		byte[][] firstLevel = new byte[][] {
				{0,0,0,0,3,3,0,3,0,0},
				{2,0,3,0,0,0,0,3,0,9},
				{0,0,3,0,3,3,0,0,0,0},
		};
		level[0] = firstLevel;
		String[] instructions = new String[2];
		instructions[0] = "Change your vertical direction";
		instructions[1] = "And the yellow tiles will go up";
		String levelName = "SURPRISE";
		ApoNotSoSimpleLevel simpleLevel = new ApoNotSoSimpleLevel(level, instructions, levelName);
		return simpleLevel;
	}
	
	private static final ApoNotSoSimpleLevel getSixLevel() {
		byte[][][] level = new byte[2][3][10];
		byte[][] firstLevel = new byte[][] {
				{0,0,0,0,3,0,0,3,0,0},
				{2,0,0,0,0,0,0,3,0,9},
				{0,0,0,0,3,0,0,0,0,0},
		};
		byte[][] secondLevel = new byte[][] {
				{0,0,0,0,0,3,0,0,0,0},
				{2,0,3,0,0,0,0,0,0,9},
				{0,0,3,0,0,3,0,0,0,0},
		};
		level[0] = firstLevel;
		level[1] = secondLevel;

		String[] instructions = new String[3];
		instructions[0] = "It looks like the previous level or?";
		instructions[1] = "But it feels different";
		instructions[2] = "Lamer will press 'c'";
		String levelName = "DOUBLE SURPRISE";
		ApoNotSoSimpleLevel simpleLevel = new ApoNotSoSimpleLevel(level, instructions, levelName);
		return simpleLevel;
	}
	
	private static final ApoNotSoSimpleLevel getSevenLevel() {
		byte[][][] level = new byte[1][3][10];
		byte[][] firstLevel = new byte[][] {
				{0,1,0,0,0,1,0,0,0,1},
				{2,1,3,1,3,0,0,1,3,9},
				{0,0,3,1,3,1,1,1,3,1},
		};
		level[0] = firstLevel;
		String[] instructions = new String[2];
		instructions[0] = "Go up and down";
		instructions[1] = "I am dizzy";
		String levelName = "GOING UP";
		ApoNotSoSimpleLevel simpleLevel = new ApoNotSoSimpleLevel(level, instructions, levelName);
		return simpleLevel;
	}

	private static final ApoNotSoSimpleLevel getEightLevel() {
		byte[][][] level = new byte[2][3][10];
		byte[][] firstLevel = new byte[][] {
				{0,0,0,0,3,0,0,0,0,0},
				{2,0,3,0,3,0,3,0,1,9},
				{0,0,3,0,0,0,3,0,1,0},
		};
		byte[][] secondLevel = new byte[][] {
				{0,0,0,0,0,1,0,0,0,0},
				{2,0,0,1,0,0,0,3,0,9},
				{0,1,0,0,0,0,0,3,0,1},
		};
		level[0] = firstLevel;
		level[1] = secondLevel;

		String[] instructions = new String[3];
		instructions[0] = "You have to catch the right";
		instructions[1] = "PHASE";
		instructions[2] = "Lamer will press 'c'";
		String levelName = "DOUBLE PHASE";
		ApoNotSoSimpleLevel simpleLevel = new ApoNotSoSimpleLevel(level, instructions, levelName);
		return simpleLevel;
	}
	
	private static final ApoNotSoSimpleLevel getNineLevel() {
		byte[][][] level = new byte[1][3][10];
		byte[][] firstLevel = new byte[][] {
				{1,1,0,1,4,4,0,4,4,0},
				{9,1,0,0,0,0,1,0,4,2},
				{0,0,4,1,4,4,0,0,0,0},
		};
		level[0] = firstLevel;
		String[] instructions = new String[2];
		instructions[0] = "Change your vertical direction";
		instructions[1] = "And the red tiles will go down";
		String levelName = "GO DOWN";
		ApoNotSoSimpleLevel simpleLevel = new ApoNotSoSimpleLevel(level, instructions, levelName);
		return simpleLevel;
	}

	private static final ApoNotSoSimpleLevel getTenLevel() {
		byte[][][] level = new byte[2][3][10];
		byte[][] firstLevel = new byte[][] {
				{0,0,0,0,0,0,0,0,0,0},
				{2,3,0,1,0,3,0,3,0,9},
				{0,3,0,1,0,3,0,3,0,0},
		};
		byte[][] secondLevel = new byte[][] {
				{0,0,4,0,4,0,1,0,4,0},
				{2,0,4,0,4,0,1,0,4,9},
				{0,0,0,0,0,0,0,0,0,0},
		};
		level[0] = firstLevel;
		level[1] = secondLevel;

		String[] instructions = new String[3];
		instructions[0] = "Oh they doubled the fun";
		instructions[1] = "Its like a wave";
		instructions[2] = "Lamer will press 'c'";
		String levelName = "DOUBLE COMBINE";
		ApoNotSoSimpleLevel simpleLevel = new ApoNotSoSimpleLevel(level, instructions, levelName);
		return simpleLevel;
	}
	
	private static final ApoNotSoSimpleLevel getElevenLevel() {
		byte[][][] level = new byte[1][3][10];
		byte[][] firstLevel = new byte[][] {
				{1,4,0,0,3,1,4,0,0,1},
				{2,0,3,4,3,4,4,1,3,9},
				{1,1,1,4,0,0,0,3,3,1},
		};
		level[0] = firstLevel;
		String[] instructions = new String[2];
		instructions[0] = "You need some help?";
		instructions[1] = "Flip is the magic word";
		String levelName = "FLIP";
		ApoNotSoSimpleLevel simpleLevel = new ApoNotSoSimpleLevel(level, instructions, levelName);
		return simpleLevel;
	}
	
	private static final ApoNotSoSimpleLevel getTwelveLevel() {
		byte[][][] level = new byte[2][3][10];
		byte[][] firstLevel = new byte[][] {
				{0,4,0,0,0,1,4,0,0,0},
				{2,4,0,0,0,4,4,4,0,9},
				{0,0,0,0,0,0,0,1,0,0},
		};
		byte[][] secondLevel = new byte[][] {
				{0,0,0,3,1,0,0,0,3,0},
				{2,0,3,3,3,0,0,0,3,9},
				{0,0,1,0,0,0,0,0,0,0},
		};
		level[0] = firstLevel;
		level[1] = secondLevel;

		String[] instructions = new String[3];
		instructions[0] = "That level blows my mind";
		instructions[1] = "Yours too?";
		instructions[2] = "Lamer will press 'c'";
		String levelName = "DOUBLE COMBINE";
		ApoNotSoSimpleLevel simpleLevel = new ApoNotSoSimpleLevel(level, instructions, levelName);
		return simpleLevel;
	}
	
	private static final ApoNotSoSimpleLevel getThirteenLevel() {
		byte[][][] level = new byte[1][3][10];
		byte[][] firstLevel = new byte[][] {
				{0,0,1,0,1,0,7,0,0,1},
				{2,0,7,0,1,0,1,7,7,9},
				{0,0,1,0,7,0,1,0,0,1},
		};
		level[0] = firstLevel;
		String[] instructions = new String[2];
		instructions[0] = "Change your vertical direction";
		instructions[1] = "and they switch on and off";
		String levelName = "ION";
		ApoNotSoSimpleLevel simpleLevel = new ApoNotSoSimpleLevel(level, instructions, levelName);
		return simpleLevel;
	}
	
	private static final ApoNotSoSimpleLevel getFourteenLevel() {
		byte[][][] level = new byte[2][3][10];
		byte[][] firstLevel = new byte[][] {
				{0,1,0,0,0,1,0,7,7,0},
				{2,0,7,1,0,7,7,0,1,9},
				{0,7,0,7,0,1,0,0,7,0},
		};
		byte[][] secondLevel = new byte[][] {
				{0,1,0,0,7,1,0,7,1,0},
				{0,9,7,1,0,7,7,2,1,0},
				{0,0,0,1,0,1,0,0,1,0},
		};
		level[0] = firstLevel;
		level[1] = secondLevel;

		String[] instructions = new String[3];
		instructions[0] = "Io a io a io";
		instructions[1] = "The path is ionized";
		instructions[2] = "Lamer will press 'c'";
		String levelName = "DOUBLE COMBINE";
		ApoNotSoSimpleLevel simpleLevel = new ApoNotSoSimpleLevel(level, instructions, levelName);
		return simpleLevel;
	}
	
	private static final ApoNotSoSimpleLevel getFifteenLevel() {
		byte[][][] level = new byte[1][3][10];
		byte[][] firstLevel = new byte[][] {
				{0,1,0,7,0,3,0,1,0,1},
				{2,7,3,1,4,3,1,7,4,9},
				{0,1,0,7,4,1,7,7,4,1},
		};
		level[0] = firstLevel;
		String[] instructions = new String[2];
		instructions[0] = "Slowly, it is tricky";
		instructions[1] = "More than 4 different kind of objects";
		String levelName = "DECEPTION";
		ApoNotSoSimpleLevel simpleLevel = new ApoNotSoSimpleLevel(level, instructions, levelName);
		return simpleLevel;
	}
	
	private static final ApoNotSoSimpleLevel getSixteenLevel() {
		byte[][][] level = new byte[2][3][10];
		byte[][] firstLevel = new byte[][] {
				{8,0,8,1,8,0,1,8,8,1},
				{2,0,7,7,0,3,7,3,7,8},
				{7,7,3,1,0,0,4,1,8,9},
		};
		byte[][] secondLevel = new byte[][] {
				{0,0,8,1,8,0,0,0,1,0},
				{0,9,7,2,0,3,3,1,1,0},
				{0,0,3,1,4,0,0,0,1,0},
		};
		level[0] = firstLevel;
		level[1] = secondLevel;

		String[] instructions = new String[3];
		instructions[0] = "So near but also so far away";
		instructions[1] = "";
		instructions[2] = "Lamer will press 'c'";
		String levelName = "DOUBLE COMBINE";
		ApoNotSoSimpleLevel simpleLevel = new ApoNotSoSimpleLevel(level, instructions, levelName);
		return simpleLevel;
	}
	
	private static final ApoNotSoSimpleLevel getSeventeenLevel() {
		byte[][][] level = new byte[1][3][10];
		byte[][] firstLevel = new byte[][] {
				{1,3,7,1,3,7,3,1,0,1},
				{2,7,0,0,7,0,7,0,4,9},
				{0,3,7,1,3,7,0,1,4,1},
		};
		level[0] = firstLevel;
		String[] instructions = new String[2];
		instructions[0] = "It just a jump to the left";
		instructions[1] = "And then a step to the right";
		String levelName = "SIDESTEP";
		ApoNotSoSimpleLevel simpleLevel = new ApoNotSoSimpleLevel(level, instructions, levelName);
		return simpleLevel;
	}
	
	private static final ApoNotSoSimpleLevel getEighteenLevel() {
		byte[][][] level = new byte[2][3][10];
		byte[][] firstLevel = new byte[][] {
				{0,1,0,0,8,1,8,0,7,0},
				{2,8,7,1,0,7,7,0,8,9},
				{0,7,0,7,8,8,1,7,8,1},
		};
		byte[][] secondLevel = new byte[][] {
				{0,7,0,0,8,1,1,0,7,0},
				{9,7,1,1,0,0,0,2,8,0},
				{0,7,0,0,7,1,1,7,7,0},
		};
		level[0] = firstLevel;
		level[1] = secondLevel;

		String[] instructions = new String[3];
		instructions[0] = "That's easy or?";
		instructions[1] = "Stop what's that ...";
		instructions[2] = "Lamer will press 'c'";
		String levelName = "DOUBLE COMBINE";
		ApoNotSoSimpleLevel simpleLevel = new ApoNotSoSimpleLevel(level, instructions, levelName);
		return simpleLevel;
	}
	
	private static final ApoNotSoSimpleLevel getNineteenLevel() {
		byte[][][] level = new byte[1][3][10];
		byte[][] firstLevel = new byte[][] {
				{0,0,0,8,7,0,7,8,0,0},
				{2,0,7,8,0,8,7,0,0,9},
				{0,0,7,0,7,8,0,8,0,0},
		};
		level[0] = firstLevel;
		String[] instructions = new String[2];
		instructions[0] = "Like a flashlight";
		instructions[1] = "On / off ... on / off";
		String levelName = "ONOFF";
		ApoNotSoSimpleLevel simpleLevel = new ApoNotSoSimpleLevel(level, instructions, levelName);
		return simpleLevel;
	}
	
	
	private static final ApoNotSoSimpleLevel getTwentyLevel() {
		byte[][][] level = new byte[1][3][10];
		byte[][] firstLevel = new byte[][] {
				{0,8,0,7,0,1,0,0,0,1},
				{2,1,4,7,3,1,4,3,1,9},
				{0,1,4,1,8,0,8,1,7,0},
		};
		level[0] = firstLevel;
		String[] instructions = new String[2];
		instructions[0] = "Now it is getting difficult";
		instructions[1] = "But not for you or?";
		String levelName = "Highjump";
		ApoNotSoSimpleLevel simpleLevel = new ApoNotSoSimpleLevel(level, instructions, levelName);
		return simpleLevel;
	}
	
	private static final ApoNotSoSimpleLevel getTwentyOneLevel() {
		byte[][][] level = new byte[2][3][10];
		byte[][] firstLevel = new byte[][] {
				{0,1,0,0,8,1,1,0,7,0},
				{2,0,7,1,0,7,7,0,8,9},
				{0,7,0,7,8,1,1,0,7,0},
		};
		byte[][] secondLevel = new byte[][] {
				{0,0,0,0,0,3,4,0,8,0},
				{9,0,3,3,0,0,7,2,1,7},
				{0,0,0,0,7,3,4,0,8,0},
		};
		level[0] = firstLevel;
		level[1] = secondLevel;

		String[] instructions = new String[3];
		instructions[0] = "Thats easy or?";
		instructions[1] = "Stop whats that ...";
		instructions[2] = "Lamer will press 'c'";
		String levelName = "DOUBLE COMBINE";
		ApoNotSoSimpleLevel simpleLevel = new ApoNotSoSimpleLevel(level, instructions, levelName);
		return simpleLevel;
	}
	
	
	private static final ApoNotSoSimpleLevel getTwentyTwoLevel() {
		byte[][][] level = new byte[1][3][10];
		byte[][] firstLevel = new byte[][] {
				{0,1,7,7,0,1,0,3,0,7},
				{2,1,3,1,8,3,4,1,1,9},
				{0,8,8,1,8,3,8,3,0,7},
		};
		level[0] = firstLevel;
		String[] instructions = new String[2];
		instructions[0] = "Sometimes I'm might going crazy";
		instructions[1] = "Hihihihihi";
		String levelName = "BACKDOOR";
		ApoNotSoSimpleLevel simpleLevel = new ApoNotSoSimpleLevel(level, instructions, levelName);
		return simpleLevel;
	}
	
	private static final ApoNotSoSimpleLevel getTwentyThreeLevel() {
		byte[][][] level = new byte[2][3][10];
		byte[][] firstLevel = new byte[][] {
				{0,0,0,0,0,5,1,0,0,1},
				{2,0,0,0,0,5,0,0,0,9},
				{0,0,0,0,0,0,1,5,5,1},
		};
		byte[][] secondLevel = new byte[][] {
				{0,0,0,1,0,0,0,0,0,0},
				{2,1,5,0,4,0,0,0,0,9},
				{0,1,0,0,4,0,0,0,0,0},
		};
		level[0] = firstLevel;
		level[1] = secondLevel;

		String[] instructions = new String[3];
		instructions[0] = "The magenta tile goes up when";
		instructions[1] = "you change your direction horizontally";
		instructions[2] = "Lamer will press 'c'";
		String levelName = "DOUBLE COMBINE";
		ApoNotSoSimpleLevel simpleLevel = new ApoNotSoSimpleLevel(level, instructions, levelName);
		return simpleLevel;
	}
	
	
	private static final ApoNotSoSimpleLevel getTwentyFourLevel() {
		byte[][][] level = new byte[1][3][10];
		byte[][] firstLevel = new byte[][] {
				{0,1,5,5,5,5,5,5,5,1},
				{2,0,0,0,0,0,0,3,0,9},
				{0,1,0,7,0,4,0,3,0,1},
		};
		level[0] = firstLevel;
		String[] instructions = new String[2];
		instructions[0] = "Now there is a magenta wall";
		instructions[1] = "I hope I can pass it";
		String levelName = "The WALL";
		ApoNotSoSimpleLevel simpleLevel = new ApoNotSoSimpleLevel(level, instructions, levelName);
		return simpleLevel;
	}
	
	private static final ApoNotSoSimpleLevel getTwentyFifeLevel() {
		byte[][][] level = new byte[2][3][10];
		byte[][] firstLevel = new byte[][] {
				{0,0,0,0,1,0,5,7,0,0},
				{2,1,5,0,0,4,0,0,5,9},
				{0,0,0,0,0,4,0,3,5,1},
		};
		byte[][] secondLevel = new byte[][] {
				{0,0,0,1,1,0,0,7,0,0},
				{2,0,0,1,0,4,5,3,5,9},
				{0,1,0,7,0,4,0,0,5,0},
		};
		level[0] = firstLevel;
		level[1] = secondLevel;

		String[] instructions = new String[3];
		instructions[0] = "You are stuck?";
		instructions[1] = "Did you try to run backwards?";
		instructions[2] = "Lamer will press 'c'";
		String levelName = "DOUBLE COMBINE";
		ApoNotSoSimpleLevel simpleLevel = new ApoNotSoSimpleLevel(level, instructions, levelName);
		return simpleLevel;
	}
	
	private static final ApoNotSoSimpleLevel getTwentySixLevel() {
		byte[][][] level = new byte[1][3][10];
		byte[][] firstLevel = new byte[][] {
				{0,0,5,6,3,4,5,6,0,0},
				{2,0,0,0,0,0,0,0,0,9},
				{0,0,5,6,3,4,5,6,0,0},
		};
		level[0] = firstLevel;
		String[] instructions = new String[2];
		instructions[0] = "Pink Tiles? Goes down when";
		instructions[1] = "you change your direction horizontally";
		String levelName = "PINKY";
		ApoNotSoSimpleLevel simpleLevel = new ApoNotSoSimpleLevel(level, instructions, levelName);
		return simpleLevel;
	}
	
	private static final ApoNotSoSimpleLevel getTwentySevenLevel() {
		byte[][][] level = new byte[2][3][10];
		byte[][] firstLevel = new byte[][] {
				{0,7,6,0,0,0,5,0,0,0},
				{2,1,0,5,4,1,0,6,3,9},
				{0,0,0,0,0,7,0,0,0,1},
		};
		byte[][] secondLevel = new byte[][] {
				{0,0,0,0,4,8,0,0,3,1},
				{2,1,6,0,0,1,5,0,0,9},
				{0,8,0,5,0,0,0,6,0,0},
		};
		level[0] = firstLevel;
		level[1] = secondLevel;

		String[] instructions = new String[3];
		instructions[0] = "It's impossible!";
		instructions[1] = "Or wait ... perhaps when I ...";
		instructions[2] = "Lamer will press 'c'";
		String levelName = "DOUBLE COMBINE";
		ApoNotSoSimpleLevel simpleLevel = new ApoNotSoSimpleLevel(level, instructions, levelName);
		return simpleLevel;
	}
	
	private static final ApoNotSoSimpleLevel getTwentyEightLevel() {
		byte[][][] level = new byte[1][3][10];
		byte[][] firstLevel = new byte[][] {
				{3,0,4,0,3,8,4,0,3,0},
				{2,1,4,5,1,1,4,5,1,9},
				{3,6,7,0,3,6,7,0,3,6},
		};
		level[0] = firstLevel;
		String[] instructions = new String[2];
		instructions[0] = "So many different colors";
		instructions[1] = "These colors are very calming or?";
		String levelName = "Colored";
		ApoNotSoSimpleLevel simpleLevel = new ApoNotSoSimpleLevel(level, instructions, levelName);
		return simpleLevel;
	}
	
	private static final ApoNotSoSimpleLevel getTwentyNineLevel() {
		byte[][][] level = new byte[2][3][10];
		byte[][] firstLevel = new byte[][] {
				{0,0,10,11,0, 0,0, 0, 0,0},
				{2,0, 0,11,0, 0,0, 0,11,9},
				{0,0,10,11,0, 0,0, 0, 1,0},
		};
		byte[][] secondLevel = new byte[][] {
				{0,0, 0, 0,0, 3,8, 1, 0,0},
				{2,0, 0, 0,0,10,6, 6,11,9},
				{0,0, 0, 0,0, 3,0,10, 1,0},
		};
		level[0] = firstLevel;
		level[1] = secondLevel;

		String[] instructions = new String[3];
		instructions[0] = "Step on the * tile and the o tiles";
		instructions[1] = "will change their visible";
		instructions[2] = "Lamer will press 'c'";
		String levelName = "DOUBLE COMBINE";
		ApoNotSoSimpleLevel simpleLevel = new ApoNotSoSimpleLevel(level, instructions, levelName);
		return simpleLevel;
	}

	private static final ApoNotSoSimpleLevel getThirtyLevel() {
		byte[][][] level = new byte[1][3][10];
		byte[][] firstLevel = new byte[][] {
				{0,0, 8, 6, 3, 0,10, 1, 1,0},
				{2,1, 5, 6, 3, 5, 6,10,11,9},
				{0,0, 5,10,11, 5, 0, 1, 1,0},
		};
		level[0] = firstLevel;
		String[] instructions = new String[2];
		instructions[0] = "Damn heavy level!";
		instructions[1] = "Can you beat it?";
		String levelName = "MINDBLOW";
		ApoNotSoSimpleLevel simpleLevel = new ApoNotSoSimpleLevel(level, instructions, levelName);
		return simpleLevel;
	}

	private static final ApoNotSoSimpleLevel getThirtyOneLevel() {
		byte[][][] level = new byte[1][3][10];
		byte[][] firstLevel = new byte[][] {
				{0,5,10,11, 0, 5, 1, 1, 1, 1},
				{2,0, 6, 0, 7, 8,10,10,11, 9},
				{0,5, 6, 0, 6, 0, 1, 1, 1, 1},
		};
		level[0] = firstLevel;
		String[] instructions = new String[2];
		instructions[0] = "This level will blow your mind";
		instructions[1] = "Can you beat it?";
		String levelName = "FINAL LEVEL";
		ApoNotSoSimpleLevel simpleLevel = new ApoNotSoSimpleLevel(level, instructions, levelName);
		return simpleLevel;
	}
}
