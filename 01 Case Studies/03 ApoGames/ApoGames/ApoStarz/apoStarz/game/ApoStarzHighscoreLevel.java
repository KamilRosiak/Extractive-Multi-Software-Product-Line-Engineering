package apoStarz.game;

public class ApoStarzHighscoreLevel {

	private String levelString;
	
	private int highscore;
	
	private String solution;
	
	public ApoStarzHighscoreLevel() {
		this.levelString = "";
		this.highscore = -1;
	}
	
	public ApoStarzHighscoreLevel(String levelString, int highscore, String solution) {
		this.levelString = levelString;
		this.highscore = highscore;
		this.solution = solution;
	}

	public String getLevelString() {
		return this.levelString;
	}

	public void setLevelString(String levelString) {
		this.levelString = levelString;
	}

	public int getHighscore() {
		return this.highscore;
	}

	public void setHighscore(int highscore) {
		this.highscore = highscore;
	}

	public String getSolution() {
		return this.solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}
	
}
