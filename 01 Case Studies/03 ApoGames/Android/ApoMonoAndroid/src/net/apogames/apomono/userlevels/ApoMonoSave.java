package net.apogames.apomono.userlevels;

public class ApoMonoSave {

	public boolean[] solvedLevels;
	
	public int solvedCout, maxSolvedLevel;
	
	public ApoMonoSave() {
		this.solvedLevels = new boolean[10000];
		this.solvedCout = 0;
		this.maxSolvedLevel = 0;
	}
	
	public String getSaveString() {
		String saveString = "";
		for (int i = 0; i < this.solvedLevels.length && i <= this.maxSolvedLevel; i++) {
			if (this.solvedLevels[i]) {
				saveString += String.valueOf(i) + ",";
			}
		}
		if (saveString.length() > 0) {
			saveString = saveString.substring(0, saveString.length() - 1);
		}
		return saveString;
	}
	
	public void createArrayFromString(final String levels) {
		if ((levels != null) && (levels.length() > 0)) {
			String[] array = levels.split(",");
			for (int i = 0; i < array.length; i++) {
				int value = Integer.valueOf(array[i]);
				this.solvedLevels[value] = true;
				this.solvedCout += 1;
				if (value > this.maxSolvedLevel) {
					this.maxSolvedLevel = value;
				}
			}
		} else {
			this.solvedCout = 0;
			this.maxSolvedLevel = 0;
		}
	}
	
	public boolean isLevelSolved(final int level) {
		if ((level >= 0) && (level < this.solvedLevels.length)) {
			return this.solvedLevels[level];
		}
		return false;
	}
	
	public void setLevelToSolved(final int level) {
		if ((level >= 0) && (level < this.solvedLevels.length)) {
			if (!this.solvedLevels[level]) {
				this.solvedCout += 1;
				this.solvedLevels[level] = true;
				if (level > this.maxSolvedLevel) {
					this.maxSolvedLevel = level;
				}
			}
		}
	}
}
