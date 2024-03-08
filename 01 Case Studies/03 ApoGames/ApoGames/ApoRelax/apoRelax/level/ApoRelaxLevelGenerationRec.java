package apoRelax.level;

public class ApoRelaxLevelGenerationRec {

	private final int x,
					  y,
					  width,
					  height;
	
	public ApoRelaxLevelGenerationRec(final int x, final int y, final int width, final int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public final int getX() {
		return this.x;
	}

	public final int getY() {
		return this.y;
	}

	public final int getWidth() {
		return this.width;
	}

	public final int getHeight() {
		return this.height;
	}
	
}
