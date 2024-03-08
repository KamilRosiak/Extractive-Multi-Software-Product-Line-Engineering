package apoSimple.entity;

public class ApoSimpleDriveHelp {

	private float changeX;
	private float changeY;
	private float changeVecY;
	
	private boolean bUp;
	
	private final boolean bBlack;
	
	public ApoSimpleDriveHelp(final boolean bBlack) {
		this.changeX = (float)(Math.random() * 20 - 10);
		this.changeY = (float)(Math.random() * 20 - 20);
		this.changeVecY = (float)(Math.random() * ApoSimpleDrive.MAX);
		this.bUp = true;
		this.bBlack = bBlack;
	}
	
	public boolean isBlack() {
		return this.bBlack;
	}

	public void think(int delta) {
		if (this.bUp) {
			this.changeVecY += delta * 0.015f;
			if (this.changeVecY >= ApoSimpleDrive.MAX) {
				this.bUp = false;
			}
		} else {
			this.changeVecY -= delta * 0.015f;
			if (this.changeVecY <= 0) {
				this.bUp = true;
			}
		}
	}
	
	public float getX() {
		return this.changeX;
	}

	public float getY() {
		return this.changeY - this.changeVecY;
	}
}
