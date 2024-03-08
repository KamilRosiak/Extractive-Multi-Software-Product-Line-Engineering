package apoSlitherLink.level;

import java.awt.Color;
import java.awt.Graphics2D;

public class ApoSlitherLinkBlinkHelp {

	private final Color COLOR = new Color(150, 201, 0);
	private final float CHANGE = 0.5f;
	private static final int WIDTH = 5;
	
	private int x;
	private int y;
	
	private int width;
	
	private float alpha;
	
	public ApoSlitherLinkBlinkHelp(int x, int y) {
		this(x, y, ApoSlitherLinkBlinkHelp.WIDTH);
	}
	
	public ApoSlitherLinkBlinkHelp(int x, int y, int width) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.alpha = 254;
	}
	
	public boolean isInvisible() {
		if (this.alpha < 0 ) {
			return true;
		}
		return false;
	}
	
	public void think(int delta) {
		this.alpha -= delta * this.CHANGE;

		if (this.alpha < 0 ) {
			this.alpha = 0;
		}
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		Color c = new Color(this.COLOR.getRed(), this.COLOR.getGreen(), this.COLOR.getBlue(), (int)(this.alpha));
		g.setColor(c);
		g.fillRoundRect((int)(this.x - this.width/2 + changeX), (int)(this.y - this.width/2 + changeY), this.width, this.width, 5, 5);
		
	}
	
}
