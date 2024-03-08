package apoJump.game;

public class ApoJumpStateHighscoreHelp {

	private final int points;
	
	private final int time;
	
	private final String name;
	
	public ApoJumpStateHighscoreHelp(final int points, final int time, final String name) {
		this.points = points;
		this.time = time;
		this.name = name;
	}

	public int getPoints() {
		return this.points;
	}

	public int getTime() {
		return this.time;
	}

	public String getName() {
		return this.name;
	}
	
}
