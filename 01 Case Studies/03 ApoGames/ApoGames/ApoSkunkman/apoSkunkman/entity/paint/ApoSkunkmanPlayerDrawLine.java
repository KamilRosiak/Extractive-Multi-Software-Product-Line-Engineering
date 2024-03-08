package apoSkunkman.entity.paint;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Klasse, um mit einer KI eine Linie zeichnen zu können<br />
 * @author Dirk Aporius
 */
public class ApoSkunkmanPlayerDrawLine extends ApoSkunkmanPlayerDraw {

	private float x1;
	private float y1;
	private float x2;
	private float y2;
	
	public ApoSkunkmanPlayerDrawLine(float x1, float y1, float x2, float y2, int time, Color color) {
		super(time, color);
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public void render(Graphics2D g, int changeX, int changeY) {
		if (super.getColor() != null) {
			g.setColor(super.getColor());
		}
		g.drawLine((int)(this.x1 + changeX), (int)(this.y1 + changeY), (int)(this.x2 + changeX), (int)(this.y2 + changeY));
	}
}