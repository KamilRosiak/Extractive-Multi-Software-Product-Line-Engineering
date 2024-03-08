package org.apogames.entity;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ApoButtonWithImageAndText extends ApoButton {

	private Font font;
	
	private String text;
	
	private int changeX;
	
	public ApoButtonWithImageAndText(BufferedImage iBackground, int x, int y, int width, int height, String function, String text, Font font, int changeX) {
		super(iBackground, x, y, width, height, function);

		this.text = text;
		this.font = font;
		
		this.changeX = changeX;
	}
	
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void render(Graphics2D g, int changeX, int changeY) {
		if (this.isBVisible()) {
			super.render(g, changeX, changeY);
			
			if (this.text != null) {
				if (this.font != null) {
					g.setFont(this.font);
				}
				int w = g.getFontMetrics().stringWidth(this.text);
				int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
				g.drawString(this.text, (int)(this.getXMiddle() - w/2 + changeX + this.changeX), (int)(this.getY() + changeY + this.getHeight()/2 + h/2));
			}
		}
	}

}
