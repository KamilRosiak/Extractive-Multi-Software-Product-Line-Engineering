package apoSimple.game.level;

public class ApoSimpleLevelHighscore {

	private final int level;
	private int stars;
	
	public ApoSimpleLevelHighscore(final int level, final int stars) {
		this.level = level;
		this.stars = stars;
	}

	public int getLevel() {
		return this.level;
	}

	public int getStars() {
		return this.stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}
	
}
