package apoSimpleSudoku.level;

public class ApoSimpleSudokuLevelHelp {

	private final byte width;
	
	private final String[] connected;
	
	private final byte[][] level;
	
	public ApoSimpleSudokuLevelHelp(final byte width, final String[] connected, final byte[][] level) {
		this.width = width;
		this.connected = connected;
		this.level = level;
	}

	public byte getWidth() {
		return this.width;
	}

	public String[] getConnected() {
		return this.connected;
	}

	public byte[][] getLevel() {
		return this.level;
	}
	
}
