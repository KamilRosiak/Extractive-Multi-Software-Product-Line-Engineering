package apoSkunkman.entity.paint;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * abstrakte Klasse, um etwas zu zeichnen<br />
 * @author Dirk Aporius
 */
public abstract class ApoSkunkmanPlayerDraw {

	private int time;
	private Color color;
	
	public ApoSkunkmanPlayerDraw(int time, Color color) {
		this.time = time;
		this.color = color;
	}
	
	public int getTime() {
		return this.time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public abstract void render(Graphics2D g, int changeX, int changeY);
	
}
