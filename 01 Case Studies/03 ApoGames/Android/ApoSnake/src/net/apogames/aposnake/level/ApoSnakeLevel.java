package net.apogames.aposnake.level;

public class ApoSnakeLevel {

	/**
	 * 0 = free
	 * 1 = head red left
	 * 2 = head red down
	 * 3 = head red right
	 * 4 = head red up
	 * 5 = red coin
	 * 6 = red wall
	 * 7 = head blue left
	 * 8 = head blue down
	 * 9 = head blue right
	 * a = head blue up
	 * b = blue coin
	 * c = blue wall
	 * d = head green left
	 * e = head green down
	 * f = head green right
	 * g = head green up
	 * h = green coin
	 * i = green wall
	 * 
	 * 19 = rot Wand (ex Schlange)
	 * 20 = blau Wand (ex Schlange)
	 * 21 = grüne Wand (ex Schlange)
	 */
	private static final String[] levelsString = new String[] {
			"de"+
			"0000"+
			"0660"+
			"0650"+
			"0666"+
			"0001",
		
//			"de"+
//			"5555"+
//			"0666"+
//			"5005"+
//			"6660"+
//			"3555",
			
			"ed"+
			"56000"+
			"56b65"+
			"06060"+
			"0h064",
			
			"gd"+
			"5056b0b"+
			"0606060"+
			"0606660"+
			"4650b6b",
			
			"ee"+
			"50005"+
			"06560"+
			"05450"+
			"06560"+
			"50005",
			
			"cchi5c36b5b",
			
			"cf"+
			"0bc"+
			"0c0"+
			"bab"+
			"545"+
			"060"+
			"650",

			"ddebbb6ii656b5b65i",
			
//			"ge"+
//			"00bcb0b"+
//			"0ccccc0"+
//			"00b666b"+
//			"ccccbcb"+
//			"000646a",
			
			"efcihiccccccbiiibbigibiiiiibbabb",
			
			"fhb000000ccccc0cbbbb0cbc2b0cbabb0cb6bb00ccc50cbbbb",
			
			"gg00c0c000hhhhh0chihihc0hhghh0chihihc0hhhhh000c0c00",
			
			"eg"+
			"05450"+
			"06660"+
			"06060"+
			"05550"+
			"5ccc5"+
			"66566"+
			"06460",
			
			"ggh60006h60i0i0660ihi0650hgh0560ihi0660i0i06h60006h",	

			"ee5i5cb6i6c6hi5c56i6c6hi5c4",
			
			"gghcbbbchhc666chhc646chhc666chhcbabchhccccchicbbbci",
			
			"hc"+
			"06b60c5c"+
			"b5456b0b"+
			"06b60c5c",
			
			"ee5b5b6b5b6b5b6b566b5bg65b5",
			
			"dffh655h666655b6546666ihhh",
			
			"eeh25bhb6i659cbc7hi6ib5bhg5",
			
			"cjch15ch9bchc5cbd5ch3bchc5ch75ch",
			
			"edcccechcbchcc6h00h4i5",
			
			"eebh5b65666bc626hcb6hiabihi",
			
			"ggbhb5hbhh8iciebbciciihh66666bbcc6cchh6c4c6bbh555bh",
			
			"ic65chc5chi3c5bcb5cd6hc5chc5i",
			
			"hechb565hb566cbi61h5iib6c7bbbcbi6dh665hbbi",
			
			"ee05cb00hc50cc20b59c560ihih",
			
			"fe"+
			"b555bc"+
			"b565bc"+
			"b455ac"+
			"h666ch"+
			"i6555i",
			
			"fd855558b6ii6b55cc555h665h",
			
			"ghibehicbbb656bhbiei6bib666bcbb666bcb0cac6hhb06bcbbcba0ci5",
			
			"ehc666i6bbb6hacb56bbb5hi6i56b6b4h626icb6bi",
			
			"fe"+
			"c5c556"+
			"cbbcii"+
			"h6b6ie"+
			"h4a5ii"+
			"h655bb",
	};
	
	public static String[] editorLevels = null;
	
	public static final String getLevel(int level) {
		if ((level < 0) || (level >= levelsString.length)) {
			return null;
		}
		return levelsString[level];
	}
	
	public static final int MAX_LEVELS = levelsString.length;
	
	public static boolean isIn(String level) {
		for (int i = 0; i < levelsString.length; i++) {
			if (level.equals(levelsString[i])) {
				return true;
			}
		}
		return false;
	}
}
