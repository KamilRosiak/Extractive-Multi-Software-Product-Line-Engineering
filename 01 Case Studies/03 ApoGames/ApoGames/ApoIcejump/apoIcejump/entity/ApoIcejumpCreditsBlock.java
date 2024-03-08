package apoIcejump.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.apogames.entity.ApoEntity;

import apoIcejump.ApoIcejumpConstants;

public class ApoIcejumpCreditsBlock extends ApoEntity {

	private String letter;
	
	public ApoIcejumpCreditsBlock(BufferedImage background, float x, float y, String letter) {
		super(background, x, y, background.getWidth(), background.getHeight());
		
		this.letter = letter;
		super.setVecY((float)(Math.random() * 0.05f - 0.025));
	}
	
	public void think(int delta) {
		if (this.isBVisible()) {
			this.setY(this.getY() + this.getVecY() * delta);
			if (Math.abs(this.getStartY() - this.getY()) > ApoIcejumpConstants.BUTTON_MAX_CHANGE_Y) {
				this.setVecY(-this.getVecY());
			}
		}
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		super.render(g, changeX, changeY);
		if (this.letter != null) {
			g.setFont(ApoIcejumpConstants.FONT_PLAYER);
			g.setColor(Color.BLACK);
			int w = g.getFontMetrics().stringWidth(this.letter);
			int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
			g.drawString(this.letter, this.getX() + this.getWidth()/2 + changeX - w/2, this.getY() + this.getHeight()/2 + h/2);
		}
	}

}
