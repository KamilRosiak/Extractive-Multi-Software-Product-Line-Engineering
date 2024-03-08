package apoPongBeat.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import org.apogames.entity.ApoEntity;

public class ApoPongBeatRacket extends ApoEntity {

	private Color color;
	
	public ApoPongBeatRacket(float x, float y, float width, float height) {
		super(null, x, y, width, height);
		
		this.setBOpaque(false);
		
		this.color = new Color(255, 161, 0);
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		g.setColor(this.color);
		g.fillRect((int)(this.getX() - changeX), (int)(this.getY() - changeY), (int)(this.getWidth() - 1), (int)(this.getHeight() - 1));
		g.setColor(this.color.darker().darker());
		g.drawRect((int)(this.getX() - changeX), (int)(this.getY() - changeY), (int)(this.getWidth() - 1), (int)(this.getHeight() - 1));
	}

}
