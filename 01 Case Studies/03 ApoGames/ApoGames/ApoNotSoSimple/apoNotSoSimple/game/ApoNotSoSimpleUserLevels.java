package apoNotSoSimple.game;

import java.util.ArrayList;

import org.apogames.help.ApoHighscore;

import apoNotSoSimple.level.ApoNotSoSimpleLevel;

public class ApoNotSoSimpleUserLevels {

	public static final int SORT_USERNAME = 0;
	public static final int SORT_LEVELNAME = 1;
	public static final int SORT_SOLUTION = 2;
	
	public static final String[] SORT_STRING = new String[] {
		"username",
		"levelname",
		"solution"
	};
	
	private ApoHighscore highscore;
	
	private int curLevel;
	
	private int[][] sortLevel;
	
	public ApoNotSoSimpleUserLevels() {
		this.highscore = ApoHighscore.getInstance();
	}
	
	public void init() {
		this.loadHighscore();
	}
	
	public void loadHighscore() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				ApoNotSoSimpleUserLevels.this.load();
			}
 		});
 		t.start();
	}
	
	public int getCurLevel() {
		return this.curLevel;
	}

	public void setCurLevel(int curLevel) {
		this.curLevel = curLevel;
	}

	public void load() {
		try {
			this.highscore = ApoHighscore.getInstance();
			this.curLevel = 0;
			if (this.highscore.load()) {
				this.sort();
			}
		} catch (Exception ex) {
			this.highscore = null;
		}
	}
	
	private void sort() {
		this.sortLevel = new int[3][this.highscore.getLevel().size()];
		ArrayList<Integer> sortList = new ArrayList<Integer>();
		for (int i = 0; i < this.highscore.getUsernames().size(); i++) {
			if (sortList.size() == 0) {
				sortList.add(i);
			} else {
				for (int j = 0; j < sortList.size(); j++) {
					if (this.highscore.getUsernames().get(i).compareTo(this.highscore.getUsernames().get(sortList.get(j))) < 0) {
						sortList.add(j, i);
						break;
					} else if (j + 1 == sortList.size()) {
						sortList.add(i);
						break;
					}
				}
			}
		}
		
		ArrayList<Integer> sortListLevel = new ArrayList<Integer>();
		for (int i = 0; i < this.highscore.getLevelnames().size(); i++) {
			if (sortListLevel.size() == 0) {
				sortListLevel.add(i);
			} else {
				for (int j = 0; j < sortListLevel.size(); j++) {
					if (this.highscore.getLevelnames().get(i).compareTo(this.highscore.getLevelnames().get(sortListLevel.get(j))) < 0) {
						sortListLevel.add(j, i);
						break;
					} else if (j + 1 == sortListLevel.size()) {
						sortListLevel.add(i);
						break;
					}
				}
			}
		}
		
		for (int i = 0; i < this.sortLevel[0].length; i++) {
			this.sortLevel[SORT_USERNAME][i] = sortList.get(i);
			this.sortLevel[SORT_LEVELNAME][i] = sortListLevel.get(i);
			this.sortLevel[SORT_SOLUTION][i] = i;
		}
	}
	
	public ApoNotSoSimpleLevel getLevel(int level, int sort) {
		this.curLevel = level;
		if (this.curLevel < 0) {
			this.curLevel = this.highscore.getLevel().size() - 1;
		} else if (this.curLevel >= this.highscore.getLevel().size()) {
			this.curLevel = 0;
		}
		if (this.highscore.getLevel().size() <= 0) {
			return null;
		}
		int nextLevel = this.curLevel;
		if (sort != ApoNotSoSimpleUserLevels.SORT_SOLUTION) {
			nextLevel = sortLevel[sort][this.curLevel];
		}
		try {
			String levelString = this.highscore.getLevel().get(nextLevel);
			int layer = Integer.valueOf(levelString.substring(0, 1));
			levelString = levelString.substring(1);
			byte[][][] levelArray = new byte[layer][3][10];
			for (int l = 0; l < levelArray.length; l++) {
				for (int y = 0; y < levelArray[0].length; y++) {
					for (int x = 0; x < levelArray[0][0].length; x++) {
						levelArray[l][y][x] = Byte.valueOf(levelString.substring(0, 2));
						levelString = levelString.substring(2);
					}
				}
			}
			String levelName = this.highscore.getLevelnames().get(nextLevel);
			String[] instructions;
			if (layer == 1) {
				instructions= new String[2];
				instructions[0] = this.highscore.getDescriptions().get(nextLevel);
				instructions[1] = "by " + this.highscore.getUsernames().get(nextLevel);
			} else {
				instructions= new String[3];
				instructions[0] = levelName;
				instructions[1] = this.highscore.getDescriptions().get(nextLevel);
				instructions[2] = "by " + this.highscore.getUsernames().get(nextLevel);
			}
			return new ApoNotSoSimpleLevel(levelArray, instructions, levelName);
		} catch (Throwable ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public int getMaxLevels() {
		return this.highscore.getLevel().size();
	}
}
