package apoMario.help;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Klasse, um mit einer KI einen Kreis zeichnen zu können<br />
 * @author Dirk Aporius
 */
public class ApoMarioPlayerDrawCircle extends ApoMarioPlayerDraw {

	private float x;
	private float y;
	private float radius;
	private boolean bFill;
	
	public ApoMarioPlayerDrawCircle(float x, float y, float radius, boolean bFill, int time, Color color) {
		super(time, color);
		//System.out.println("x: "+x);
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.bFill = bFill;
	}

	@Override
	public void render(Graphics2D g, int changeX, int changeY) {
		if (super.getColor() != null) {
			g.setColor(super.getColor());
		}
		if (this.bFill) {
			g.fillOval((int)(this.x - this.radius - changeX), (int)(this.y - this.radius - changeY), (int)(2 * this.radius), (int)(2 * this.radius));
		} else {
			g.drawOval((int)(this.x - this.radius - changeX), (int)(this.y - this.radius - changeY), (int)(2 * this.radius), (int)(2 * this.radius));
		}
	}

}
