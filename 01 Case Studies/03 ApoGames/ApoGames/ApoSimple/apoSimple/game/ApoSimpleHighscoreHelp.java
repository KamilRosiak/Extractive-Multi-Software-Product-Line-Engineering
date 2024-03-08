package apoSimple.game;

public class ApoSimpleHighscoreHelp {

	private int points;
	
	private int level;
	
	private String name;
	
	private long time;
	
	public ApoSimpleHighscoreHelp(String name, int points, int level, float time, float curTime) {
		this.name = name;
		this.points = points;
		this.level = level;
		
		this.time = (long)(curTime - time);
	}

	public int getPoints() {
		return this.points;
	}

	public int getLevel() {
		return this.level;
	}

	public String getName() {
		return this.name;
	}

	public long getTime() {
		return this.time;
	}
	
}
