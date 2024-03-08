package apoSimple.game.level;

import apoSimple.ApoSimpleConstants;

public class ApoSimpleLevel {

	private int[][] level;
	
	private int[] stars;
	
	public ApoSimpleLevel(int[][] level, int[] stars) {
		this.level = level;
		this.stars = stars;
	}
	
	public ApoSimpleLevel(final String levelString) {
		this.makeLevelFromString(levelString);
	}

	public int[][] getCopyFromLevel() {
		int[][] newLevel = new int[this.level.length][this.level[0].length];
		for (int y = 0; y < this.level.length; y++) {
			for (int x = 0; x < this.level[0].length; x++) {
				newLevel[y][x] = this.level[y][x];
			}
		}
		return newLevel;
	}
	
	public int[][] getLevel() {
		return this.level;
	}

	public int[] getStars() {
		return this.stars;
	}

	public void setLevel(int[][] level) {
		this.level = level;
	}

	public void setStars(int[] stars) {
		this.stars = stars;
	}
	
	public String makeString() {
		String result = "";
		for (int y = 0; y < this.level.length; y++) {
			for (int x = 0; x < this.level[0].length; x++) {
				String curString = String.valueOf(this.level[y][x]);
				while (curString.length() < 2) {
					curString = "0" + curString;
				}
				result += curString;
			}
		}
		for (int i = 0; i < 3; i++) {
			String curString = String.valueOf(this.stars[i]);
			result += curString;
		}
		return result;
	}
	
	public static String getLevelString(final int[][] level, final int step) {
		String result = "";
		for (int y = 0; y < level.length; y++) {
			for (int x = 0; x < level[0].length; x++) {
				String curString = String.valueOf(level[y][x]);
				while (curString.length() < 2) {
					curString = "0" + curString;
				}
				result += curString;
			}
		}
		for (int i = 0; i < 3; i++) {
			String curString = String.valueOf(step + i + 1);
			result += curString;
		}
		return result;
	}
	
	public void makeLevelFromString(String level) {
		this.level = new int[7][7];
		int x = 0;
		int y = 0;
		for (int i = 0; i < 49; i += 1) {
			String curString = level.substring(0, 2);
			level = level.substring(2);
			int value = ApoSimpleConstants.REAL_EMPTY;
			try {
				value = Integer.valueOf(curString);
			} catch (NumberFormatException ex) {
				value = ApoSimpleConstants.REAL_EMPTY;
			} catch (Exception ex) {
				value = ApoSimpleConstants.REAL_EMPTY;
			}
			this.level[y][x] = value;
			x += 1;
			if (x >= this.level[0].length) {
				x = 0;
				y += 1;
			}
		}
		this.stars = new int[3];
		for (int i = 0; i < this.stars.length; i++) {
			int value = 10;
			try {
				String curString = level.substring(0, 1);
				level = level.substring(1);
				value = Integer.valueOf(curString);
			} catch (NumberFormatException ex) {
				value = 10;
			} catch (Exception ex) {
				value = 10;
			}
			this.stars[i] = value;
		}
	}

}
