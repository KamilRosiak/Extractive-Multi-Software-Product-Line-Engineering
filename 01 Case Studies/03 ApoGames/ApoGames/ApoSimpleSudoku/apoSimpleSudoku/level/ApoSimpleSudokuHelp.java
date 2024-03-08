package apoSimpleSudoku.level;

public class ApoSimpleSudokuHelp {

	private final int x;
	private final int y;
	private final int value;
	
	public ApoSimpleSudokuHelp(final int x, final int y, final int value) {
		this.x = x;
		this.y = y;
		this.value = value;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getValue() {
		return this.value;
	}
	
}
