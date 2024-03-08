package apoSimpleSudoku.level;

public class ApoSimpleSudokuLevelMake {

	public static final ApoSimpleSudokuLevel getLevel(int level) {
		if (level == 0) {
			return ApoSimpleSudokuLevelMake.getFirstLevel();
		} else if (level == 1) {
			return ApoSimpleSudokuLevelMake.getSecondLevel();
		} else if (level == 2) {
			return ApoSimpleSudokuLevelMake.getThirdLevel();
		} else if (level == 3) {
			return ApoSimpleSudokuLevelMake.getFourthLevel();
		} else if (level == 4) {
			return ApoSimpleSudokuLevelMake.getFifthLevel();
		} else if (level == 5) {
			return ApoSimpleSudokuLevelMake.getSixLevel();
		} else if (level == 6) {
			return ApoSimpleSudokuLevelMake.getSevenLevel();
		} else if (level == 7) {
			return ApoSimpleSudokuLevelMake.getEightLevel();
		} else if (level == 8) {
			return ApoSimpleSudokuLevelMake.getNineLevel();
		} else if (level == 9) {
			return ApoSimpleSudokuLevelMake.getTenLevel();
		} else if (level == 10) {
			return ApoSimpleSudokuLevelMake.getElevenLevel();
		} else if (level == 11) {
			return ApoSimpleSudokuLevelMake.getTwelveLevel();
		} else if (level == 12) {
			return ApoSimpleSudokuLevelMake.getThirdteenLevel();
		} else if (level == 13) {
			return ApoSimpleSudokuLevelMake.getFourteenLevel();
		} else if (level == 14) {
			return ApoSimpleSudokuLevelMake.getFifthteenLevel();
		} else if (level == 15) {
			return ApoSimpleSudokuLevelMake.getSixteenLevel();
		} else if (level == 16) {
			return ApoSimpleSudokuLevelMake.getSeventeenLevel();
		} else if (level == 17) {
			return ApoSimpleSudokuLevelMake.getEightteenLevel();
		} else if (level == 18) {
			return ApoSimpleSudokuLevelMake.getNineteenLevel();
		} else if (level == 19) {
			return ApoSimpleSudokuLevelMake.getTwentyLevel();
		} else if (level == 20) {
			return ApoSimpleSudokuLevelMake.getTwentyOneLevel();
		} else if (level == 21) {
			return ApoSimpleSudokuLevelMake.getTwentyTwoLevel();
		} else if (level == 22) {
			return ApoSimpleSudokuLevelMake.getTwentyThreeLevel();
		} else if (level == 23) {
			return ApoSimpleSudokuLevelMake.getTwentyFourLevel();
		} else if (level == 24) {
			return ApoSimpleSudokuLevelMake.getTwentyFiveLevel();
		} else if (level == 25) {
			return ApoSimpleSudokuLevelMake.getTwentySixLevel();
		} else if (level == 26) {
			return ApoSimpleSudokuLevelMake.getTwentySevenLevel();
		} else if (level == 27) {
			return ApoSimpleSudokuLevelMake.getTwentyEightLevel();
		} else if (level == 28) {
			return ApoSimpleSudokuLevelMake.getTwentyNineLevel();
		} else if (level == 29) {
			return ApoSimpleSudokuLevelMake.getThirtyLevel();
		} else if (level == 30) {
			return ApoSimpleSudokuLevelMake.getThirtyOneLevel();
		}
		return null;
	}
	
	private static final ApoSimpleSudokuLevel getFirstLevel() {
		byte width = 4;
		String[] connected = new String[4];
		connected[0] = "00011021";
		connected[1] = "30203132";
		connected[2] = "02112233";
		connected[3] = "12031323";
		byte[][] level = new byte[][] {
			{0,0,0,0},
			{0,2,4,0},
			{0,1,3,0},
			{0,0,0,0}			
		};
		ApoSimpleSudokuLevelHelp help = new ApoSimpleSudokuLevelHelp(width, connected, level);
		return new ApoSimpleSudokuLevel(help);
	}
	
	private static final ApoSimpleSudokuLevel getSecondLevel() {
		byte width = 4;
		String[] connected = new String[4];
		connected[0] = "00112130";
		connected[1] = "01102031";
		connected[2] = "02132332";
		connected[3] = "03122233";
		byte[][] level = new byte[][] {
			{0,0,0,0},
			{4,1,0,0},
			{0,0,2,0},
			{0,0,0,3}			
		};
		ApoSimpleSudokuLevelHelp help = new ApoSimpleSudokuLevelHelp(width, connected, level);
		return new ApoSimpleSudokuLevel(help);
	}
	
	private static final ApoSimpleSudokuLevel getThirdLevel() {
		byte width = 4;
		String[] connected = new String[4];
		connected[0] = "00102120";
		connected[1] = "01110203";
		connected[2] = "13122333";
		connected[3] = "30312232";
		byte[][] level = new byte[][] {
			{0,0,0,0},
			{0,2,4,0},
			{0,3,2,0},
			{0,0,0,0}			
		};
		ApoSimpleSudokuLevelHelp help = new ApoSimpleSudokuLevelHelp(width, connected, level);
		return new ApoSimpleSudokuLevel(help);
	}
	
	private static final ApoSimpleSudokuLevel getFourthLevel() {
		byte width = 4;
		String[] connected = new String[4];
		connected[0] = "02010011";
		connected[1] = "10203031";
		connected[2] = "03122122";
		connected[3] = "13233332";
		byte[][] level = new byte[][] {
			{0,0,0,0},
			{0,0,0,3},
			{0,0,4,0},
			{0,2,0,0}			
		};
		ApoSimpleSudokuLevelHelp help = new ApoSimpleSudokuLevelHelp(width, connected, level);
		return new ApoSimpleSudokuLevel(help);
	}
	
	private static final ApoSimpleSudokuLevel getFifthLevel() {
		byte width = 4;
		String[] connected = new String[4];
		connected[0] = "00011202";
		connected[1] = "03132223";
		connected[2] = "10112030";
		connected[3] = "31213233";
		byte[][] level = new byte[][] {
			{0,0,0,0},
			{0,1,3,0},
			{0,2,4,0},
			{0,0,0,0}			
		};
		ApoSimpleSudokuLevelHelp help = new ApoSimpleSudokuLevelHelp(width, connected, level);
		return new ApoSimpleSudokuLevel(help);
	}
	
	private static final ApoSimpleSudokuLevel getSixLevel() {
		byte width = 4;
		String[] connected = new String[4];
		connected[0] = "01001021";
		connected[1] = "20303122";
		connected[2] = "11020313";
		connected[3] = "12233332";
		byte[][] level = new byte[][] {
			{0,3,0,0},
			{0,0,0,0},
			{0,0,0,1},
			{4,0,0,0}			
		};
		ApoSimpleSudokuLevelHelp help = new ApoSimpleSudokuLevelHelp(width, connected, level);
		return new ApoSimpleSudokuLevel(help);
	}
	
	private static final ApoSimpleSudokuLevel getSevenLevel() {
		byte width = 4;
		String[] connected = new String[4];
		connected[0] = "00112231";
		connected[1] = "1020302021";
		connected[2] = "0102030212";
		connected[3] = "13233332";
		byte[][] level = new byte[][] {
			{0,0,0,1},
			{0,0,0,2},
			{0,0,0,3},
			{0,0,0,0}			
		};
		ApoSimpleSudokuLevelHelp help = new ApoSimpleSudokuLevelHelp(width, connected, level);
		return new ApoSimpleSudokuLevel(help);
	}
	
	private static final ApoSimpleSudokuLevel getEightLevel() {
		byte width = 4;
		String[] connected = new String[4];
		connected[0] = "00112232";
		connected[1] = "01102031";
		connected[2] = "02122130";
		connected[3] = "03132333";
		byte[][] level = new byte[][] {
			{0,0,0,0},
			{2,0,0,0},
			{0,1,0,0},
			{3,0,0,0}			
		};
		ApoSimpleSudokuLevelHelp help = new ApoSimpleSudokuLevelHelp(width, connected, level);
		return new ApoSimpleSudokuLevel(help);
	}
	
	private static final ApoSimpleSudokuLevel getNineLevel() {
		byte width = 4;
		String[] connected = new String[4];
		connected[0] = "00112233";
		connected[1] = "01102031";
		connected[2] = "02132332";
		connected[3] = "03122130";
		byte[][] level = new byte[][] {
			{2,0,0,1},
			{0,0,0,0},
			{0,0,0,0},
			{3,0,0,4}			
		};
		ApoSimpleSudokuLevelHelp help = new ApoSimpleSudokuLevelHelp(width, connected, level);
		return new ApoSimpleSudokuLevel(help);
	}
	
	private static final ApoSimpleSudokuLevel getTenLevel() {
		byte width = 4;
		String[] connected = new String[4];
		connected[0] = "0011221102";
		connected[1] = "01102021";
		connected[2] = "30313233";
		connected[3] = "12031323";
		byte[][] level = new byte[][] {
			{0,0,0,0},
			{0,1,4,0},
			{2,0,0,0},
			{0,0,0,0}			
		};
		ApoSimpleSudokuLevelHelp help = new ApoSimpleSudokuLevelHelp(width, connected, level);
		return new ApoSimpleSudokuLevel(help);
	}
	
	private static final ApoSimpleSudokuLevel getElevenLevel() {
		byte width = 5;
		String[] connected = new String[width];
		connected[0] = "1000010212";
		connected[1] = "2211203040";
		connected[2] = "2131423324";
		connected[3] = "1404031323";
		connected[4] = "4132434434";
		byte[][] level = new byte[][] {
			{0,2,0,0,0},
			{0,0,0,0,1},
			{0,0,0,0,0},
			{3,0,0,0,0},
			{0,0,0,4,0}
		};
		ApoSimpleSudokuLevelHelp help = new ApoSimpleSudokuLevelHelp(width, connected, level);
		return new ApoSimpleSudokuLevel(help);
	}
	
	private static final ApoSimpleSudokuLevel getTwelveLevel() {
		byte width = 5;
		String[] connected = new String[width];
		connected[0] = "0011221202";
		connected[1] = "011020302021";
		connected[2] = "3140414232";
		connected[3] = "1404031323";
		connected[4] = "2434444333";
		byte[][] level = new byte[][] {
			{0,0,0,0,5},
			{0,0,3,0,0},
			{0,0,1,4,0},
			{0,0,0,0,0},
			{1,0,0,0,0}
		};
		ApoSimpleSudokuLevelHelp help = new ApoSimpleSudokuLevelHelp(width, connected, level);
		return new ApoSimpleSudokuLevel(help);
	}
	
	private static final ApoSimpleSudokuLevel getThirdteenLevel() {
		byte width = 5;
		String[] connected = new String[width];
		connected[0] = "0011120314";
		connected[1] = "0110212233";
		connected[2] = "021304132332";
		connected[3] = "3120304041";
		connected[4] = "4243443424";
		byte[][] level = new byte[][] {
			{0,0,0,0,0},
			{0,0,2,0,0},
			{0,0,0,0,5},
			{0,4,0,0,0},
			{0,0,0,1,0}
		};
		ApoSimpleSudokuLevelHelp help = new ApoSimpleSudokuLevelHelp(width, connected, level);
		return new ApoSimpleSudokuLevel(help);
	}
	
	private static final ApoSimpleSudokuLevel getFourteenLevel() {
		byte width = 5;
		String[] connected = new String[width];
		connected[0] = "0011203140";
		connected[1] = "1001120203";
		connected[2] = "0413223343";
		connected[3] = "3221304142";
		connected[4] = "2314243444";
		byte[][] level = new byte[][] {
			{0,0,4,0,0},
			{0,0,0,0,2},
			{0,0,5,0,0},
			{0,0,1,0,0},
			{0,0,0,0,0}
		};
		ApoSimpleSudokuLevelHelp help = new ApoSimpleSudokuLevelHelp(width, connected, level);
		return new ApoSimpleSudokuLevel(help);
	}
	
	private static final ApoSimpleSudokuLevel getFifthteenLevel() {
		byte width = 5;
		String[] connected = new String[width];
		connected[0] = "2010000112";
		connected[1] = "1121313040";
		connected[2] = "4142434434";
		connected[3] = "0203142322";
		connected[4] = "0413243332";
		byte[][] level = new byte[][] {
			{0,5,0,0,4},
			{0,0,0,0,0},
			{1,0,0,0,0},
			{0,0,0,0,2},
			{3,0,0,0,0}
		};
		ApoSimpleSudokuLevelHelp help = new ApoSimpleSudokuLevelHelp(width, connected, level);
		return new ApoSimpleSudokuLevel(help);
	}
	
	private static final ApoSimpleSudokuLevel getSixteenLevel() {
		byte width = 5;
		String[] connected = new String[width];
		connected[0] = "0010112232";
		connected[1] = "011203122334";
		connected[2] = "0213041424";
		connected[3] = "2120304041";
		connected[4] = "3142434433";
		byte[][] level = new byte[][] {
			{0,0,0,0,0},
			{0,0,0,0,0},
			{3,4,0,0,0},
			{2,5,0,0,0},
			{0,0,0,0,0}
		};
		ApoSimpleSudokuLevelHelp help = new ApoSimpleSudokuLevelHelp(width, connected, level);
		return new ApoSimpleSudokuLevel(help);
	}

	private static final ApoSimpleSudokuLevel getSeventeenLevel() {
		byte width = 5;
		String[] connected = new String[width];
		connected[0] = "0100102130";
		connected[1] = "1120314140";
		connected[2] = "0212231403";
		connected[3] = "0413223242";
		connected[4] = "3324344443";
		byte[][] level = new byte[][] {
			{0,0,1,0,0},
			{0,0,4,0,0},
			{0,0,0,0,0},
			{0,3,5,0,0},
			{0,0,0,0,0}
		};
		ApoSimpleSudokuLevelHelp help = new ApoSimpleSudokuLevelHelp(width, connected, level);
		return new ApoSimpleSudokuLevel(help);
	}

	private static final ApoSimpleSudokuLevel getEightteenLevel() {
		byte width = 5;
		String[] connected = new String[width];
		connected[0] = "0011120304";
		connected[1] = "0201102122";
		connected[2] = "2030404131";
		connected[3] = "1324344433";
		connected[4] = "1423324243";
		byte[][] level = new byte[][] {
			{2,0,0,0,1},
			{0,0,0,0,0},
			{3,0,0,0,4},
			{0,0,5,0,0},
			{0,0,0,0,0}
		};
		ApoSimpleSudokuLevelHelp help = new ApoSimpleSudokuLevelHelp(width, connected, level);
		return new ApoSimpleSudokuLevel(help);
	}

	private static final ApoSimpleSudokuLevel getNineteenLevel() {
		byte width = 5;
		String[] connected = new String[width];
		connected[0] = "1000011221";
		connected[1] = "2011020304";
		connected[2] = "1324343332";
		connected[3] = "1423223130";
		connected[4] = "4041424344";
		byte[][] level = new byte[][] {
			{0,0,5,0,0},
			{0,0,0,3,0},
			{0,0,0,1,0},
			{0,4,0,0,0},
			{0,0,0,0,0}
		};
		ApoSimpleSudokuLevelHelp help = new ApoSimpleSudokuLevelHelp(width, connected, level);
		return new ApoSimpleSudokuLevel(help);
	}
	
	private static final ApoSimpleSudokuLevel getTwentyLevel() {
		byte width = 5;
		String[] connected = new String[width];
		connected[0] = "0010203040";
		connected[1] = "0102030414";
		connected[2] = "2413122231";
		connected[3] = "1121322334";
		connected[4] = "414243444333";
		byte[][] level = new byte[][] {
			{0,0,0,0,0},
			{0,0,1,0,0},
			{5,0,0,0,0},
			{0,0,0,3,0},
			{0,2,0,0,0}
		};
		ApoSimpleSudokuLevelHelp help = new ApoSimpleSudokuLevelHelp(width, connected, level);
		return new ApoSimpleSudokuLevel(help);
	}
	
	private static final ApoSimpleSudokuLevel getTwentyOneLevel() {
		byte width = 6;
		String[] connected = new String[width];
		connected[0] = "001011212030";
		connected[1] = "01121312223334";
		connected[2] = "020304142535";
		connected[3] = "051524233231";
		connected[4] = "414050515253";
		connected[5] = "545545444342";
		byte[][] level = new byte[][] {
			{0,0,0,0,1,0},
			{5,0,2,0,0,0},
			{0,0,0,6,0,0},
			{0,0,0,0,0,0},
			{0,0,0,0,0,4},
			{0,0,3,0,0,0}
		};
		ApoSimpleSudokuLevelHelp help = new ApoSimpleSudokuLevelHelp(width, connected, level);
		return new ApoSimpleSudokuLevel(help);
	}
	
	private static final ApoSimpleSudokuLevel getTwentyTwoLevel() {
		byte width = 6;
		String[] connected = new String[width];
		connected[0] = "00102010111213";
		connected[1] = "010203040515";
		connected[2] = "22232414242535";
		connected[3] = "313040505142";
		connected[4] = "525343445455";
		connected[5] = "21324132333445";
		byte[][] level = new byte[][] {
			{0,0,0,0,1,0},
			{0,0,0,0,0,0},
			{0,0,0,0,0,2},
			{0,0,0,5,0,0},
			{0,0,0,0,0,0},
			{0,6,0,0,4,3}
		};
		ApoSimpleSudokuLevelHelp help = new ApoSimpleSudokuLevelHelp(width, connected, level);
		return new ApoSimpleSudokuLevel(help);
	}
	
	private static final ApoSimpleSudokuLevel getTwentyThreeLevel() {
		byte width = 6;
		String[] connected = new String[width];
		connected[0] = "020100101121";
		connected[1] = "203122121323";
		connected[2] = "140304051525";
		connected[3] = "304050515253";
		connected[4] = "424132332434";
		connected[5] = "354555544443";
		byte[][] level = new byte[][] {
			{0,0,0,0,4,0},
			{0,0,6,0,0,0},
			{0,0,0,0,3,0},
			{0,1,0,2,0,0},
			{0,4,0,0,0,0},
			{5,0,0,0,0,0}
		};
		ApoSimpleSudokuLevelHelp help = new ApoSimpleSudokuLevelHelp(width, connected, level);
		return new ApoSimpleSudokuLevel(help);
	}
	
	private static final ApoSimpleSudokuLevel getTwentyFourLevel() {
		byte width = 6;
		String[] connected = new String[width];
		connected[0] = "121100102031";
		connected[1] = "304050514132";
		connected[2] = "01020314151423";
		connected[3] = "21221324130405";
		connected[4] = "253544334253";
		connected[5] = "524354554534";
		byte[][] level = new byte[][] {
			{0,0,2,3,0,0},
			{5,0,0,0,0,1},
			{0,0,0,0,0,0},
			{0,4,0,2,1,0},
			{0,0,0,0,0,0},
			{0,0,0,0,0,0}
		};
		ApoSimpleSudokuLevelHelp help = new ApoSimpleSudokuLevelHelp(width, connected, level);
		return new ApoSimpleSudokuLevel(help);
	}
	
	private static final ApoSimpleSudokuLevel getTwentyFiveLevel() {
		byte width = 6;
		String[] connected = new String[width];
		connected[0] = "120100102130";
		connected[1] = "201102030415";
		connected[2] = "051423243425";
		connected[3] = "354555443342";
		connected[4] = "415152535443";
		connected[5] = "132232314050";
		byte[][] level = new byte[][] {
			{1,4,0,0,0,0},
			{2,5,0,0,0,0},
			{3,6,0,0,0,0},
			{0,0,0,0,0,0},
			{0,0,0,0,0,0},
			{0,0,0,0,0,0}
		};
		ApoSimpleSudokuLevelHelp help = new ApoSimpleSudokuLevelHelp(width, connected, level);
		return new ApoSimpleSudokuLevel(help);
	}
	
	private static final ApoSimpleSudokuLevel getTwentySixLevel() {
		byte width = 6;
		String[] connected = new String[width];
		connected[0] = "000112232435";
		connected[1] = "020304051514";
		connected[2] = "132211102130";
		connected[3] = "203140514253";
		connected[4] = "504132334455";
		connected[5] = "253445544352";
		byte[][] level = new byte[][] {
			{0,0,0,0,0,0},
			{6,0,4,0,2,0},
			{0,5,0,0,0,0},
			{0,0,0,0,0,0},
			{0,0,0,3,0,0},
			{0,0,0,0,0,1}
		};
		ApoSimpleSudokuLevelHelp help = new ApoSimpleSudokuLevelHelp(width, connected, level);
		return new ApoSimpleSudokuLevel(help);
	}
	
	private static final ApoSimpleSudokuLevel getTwentySevenLevel() {
		byte width = 6;
		String[] connected = new String[width];
		connected[0] = "00112011221303";
		connected[1] = "021201102130";
		connected[2] = "041524343342";
		connected[3] = "05142332313243";
		connected[4] = "414050515253";
		connected[5] = "445455453525";
		byte[][] level = new byte[][] {
			{1,0,0,0,0,4},
			{0,0,0,0,3,0},
			{0,0,0,0,0,0},
			{0,0,5,0,0,0},
			{0,6,0,0,0,0},
			{0,0,0,0,0,2}
		};
		ApoSimpleSudokuLevelHelp help = new ApoSimpleSudokuLevelHelp(width, connected, level);
		return new ApoSimpleSudokuLevel(help);
	}

	private static final ApoSimpleSudokuLevel getTwentyEightLevel() {
		byte width = 6;
		String[] connected = new String[width];
		connected[0] = "00112111121324";
		connected[1] = "201001020314";
		connected[2] = "040515253445";
		connected[3] = "354443535455";
		connected[4] = "51524231423323";
		connected[5] = "223241304050";
		byte[][] level = new byte[][] {
			{2,0,0,0,0,0},
			{0,0,0,0,0,0},
			{0,5,0,0,4,0},
			{0,6,0,0,3,0},
			{0,0,0,0,0,0},
			{0,0,1,0,0,0}
		};
		ApoSimpleSudokuLevelHelp help = new ApoSimpleSudokuLevelHelp(width, connected, level);
		return new ApoSimpleSudokuLevel(help);
	}

	private static final ApoSimpleSudokuLevel getTwentyNineLevel() {
		byte width = 6;
		String[] connected = new String[width];
		connected[0] = "001020212212";
		connected[1] = "110102030414";
		connected[2] = "051525242313";
		connected[3] = "504030313242";
		connected[4] = "415152535444";
		connected[5] = "433334354555";
		byte[][] level = new byte[][] {
			{5,0,3,0,0,0},
			{0,0,2,0,4,1},
			{0,4,0,0,0,0},
			{0,0,0,0,0,0},
			{0,0,0,0,5,3},
			{0,0,1,0,0,0}
		};
		ApoSimpleSudokuLevelHelp help = new ApoSimpleSudokuLevelHelp(width, connected, level);
		return new ApoSimpleSudokuLevel(help);
	}
	
	private static final ApoSimpleSudokuLevel getThirtyLevel() {
		byte width = 7;
		String[] connected = new String[width];
		connected[0] = "10000102122313";
		connected[1] = "14030415243332";
		connected[2] = "05061626253445";
		connected[3] = "35364656665544";
		connected[4] = "1120314031425352";
		connected[5] = "22213041506051";
		connected[6] = "61626364655443";
		byte[][] level = new byte[][] {
			{0,0,0,0,0,0,0},
			{0,0,0,0,0,7,0},
			{0,0,0,0,0,0,0},
			{0,0,1,0,6,5,0},
			{7,0,0,4,0,0,0},
			{0,2,0,1,0,0,0},
			{0,0,0,0,0,0,3}
		};
		ApoSimpleSudokuLevelHelp help = new ApoSimpleSudokuLevelHelp(width, connected, level);
		return new ApoSimpleSudokuLevel(help);
	}
	
	private static final ApoSimpleSudokuLevel getThirtyOneLevel() {
		byte width = 7;
		String[] connected = new String[width];
		connected[0] = "00102122322312";
		connected[1] = "01020313243343";
		connected[2] = "15140405061625";
		connected[3] = "11203031414050";
		connected[4] = "42516061626352";
		connected[5] = "53646555454454";
		connected[6] = "34352636465666";
		byte[][] level = new byte[][] {
			{0,0,7,0,0,0,6},
			{0,4,0,1,0,0,0},
			{0,0,2,0,5,1,0},
			{0,0,0,4,0,0,0},
			{0,0,0,5,0,2,0},
			{0,0,0,0,0,0,0},
			{6,0,0,0,0,0,3}
		};
		ApoSimpleSudokuLevelHelp help = new ApoSimpleSudokuLevelHelp(width, connected, level);
		return new ApoSimpleSudokuLevel(help);
	}
}
