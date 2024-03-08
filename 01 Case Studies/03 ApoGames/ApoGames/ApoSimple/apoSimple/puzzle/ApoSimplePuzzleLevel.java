package apoSimple.puzzle;

public class ApoSimplePuzzleLevel {
	
	private ApoSimplePuzzleEntity[][] level;
	
	private int[] steps;
	
	public ApoSimplePuzzleLevel(final ApoSimplePuzzleEntity[][] level, int[] steps) {
		this.level = level;
		
		this.steps = steps;
	}

	public ApoSimplePuzzleEntity[][] getLevel() {
		return this.level;
	}

	public final int[] getSteps() {
		return this.steps;
	}
	
}
