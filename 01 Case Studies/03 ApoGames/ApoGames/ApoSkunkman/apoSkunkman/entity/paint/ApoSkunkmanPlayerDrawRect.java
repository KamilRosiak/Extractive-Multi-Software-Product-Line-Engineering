package apoSkunkman.entity.paint;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Klasse, um mit einer KI ein Rechteck zeichnen zu können<br />
 * @author Dirk Aporius
 */
public class ApoSkunkmanPlayerDrawRect extends ApoSkunkmanPlayerDraw {

	private float x;
	private float y;
	private float width;
	private float height;
	private boolean bFill;
	
	public ApoSkunkmanPlayerDrawRect(float x, float y, float width, float height, boolean bFill, int time, Color color) {
		super(time, color);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.bFill = bFill;
	}

	@Override
	public void render(Graphics2D g, int changeX, int changeY) {
		if (super.getColor() != null) {
			g.setColor(super.getColor());
		}
		if (this.bFill) {
			g.fillRect((int)(this.x + changeX), (int)(this.y + changeY), (int)(this.width), (int)(this.height));
		} else {
			g.drawRect((int)(this.x + changeX), (int)(this.y + changeY), (int)(this.width), (int)(this.height));
		}
	}

}
