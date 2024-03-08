package apoPongBeat.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class ApoPongBeatParticleString extends ApoPongBeatParticle {

	private String myString;
	
	private Font font;
	
	private boolean bBack;
	
	public ApoPongBeatParticleString(float x, float y, Color color, int time, String string, Font font, boolean bBack) {
		super(x, y, 2, 2, 0, 0, color, time);
		
		this.myString = string;
		this.font = font;
		this.bBack = bBack;
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		if (this.myString != null) {
			g.setColor(this.getColor());
			g.setFont(font);
			if (this.bBack) {
				int x = (int)(this.getX() - g.getFontMetrics().stringWidth(this.myString) - 5);
				g.drawString(this.myString, x, this.getY());
			} else {
				g.drawString(this.myString, this.getX(), this.getY());
			}
		}
	}

}
