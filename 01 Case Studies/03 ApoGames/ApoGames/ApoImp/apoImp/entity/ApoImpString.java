package apoImp.entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class ApoImpString extends ApoImpEntity {

	public static final int TIME_DECREASE = 10;
	
	private String drawString;
	
	private int invisible;
	
	private int curTime;
	
	private boolean bWithBackground;
	
	private int timeDecrease;
	
	private boolean bFade;
	
	public ApoImpString(float x, float y, float width, String s, boolean bWithBackground, int timeDecrease, boolean bFade) {
		super((int)x, (int)y, (int)width, 0);
		
		this.bWithBackground = bWithBackground;
		this.drawString = s;
		this.invisible = 255;
		this.curTime = 0;
		this.timeDecrease = timeDecrease;
		this.curTime = this.timeDecrease;
		this.bFade = bFade;
	}
	
	public void think(int delta) {
		this.curTime -= delta;
		if (this.curTime <= 0) {
			if (this.bFade) {
				this.curTime = this.timeDecrease;
				this.invisible -= 1;
				if (this.invisible <= 50) {
					this.invisible = 0;
					super.setVisible(false);
				}
			} else {
				super.setVisible(false);
			}
		}
	}
	
	public void render(final Graphics2D g, int changeX, int changeY) {
		if (this.isVisible()) {
			
			String s = this.drawString;
			int w = (int)(g.getFontMetrics().stringWidth(s) + 10);
			int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
			int x = (int)(this.getX() + this.getDirection()/2 - w/2) - changeX;
			int change = 10;
			int y = (int)(this.getY() + this.getDirection()/2 + h/2) - changeY;
			if (this.bWithBackground) {
				g.setColor(new Color(255, 255, 255, this.invisible));
				g.fillRect((int)(x - change), (int)(y - h - change), (int)(w + 2 * change), (int)(h + 2 * change));
				g.setColor(new Color(0, 0, 0, this.invisible));
				g.setStroke(new BasicStroke(3));
				g.drawRect((int)(x - change), (int)(y - h - change), (int)(w + 2 * change), (int)(h + 2 * change));
				g.setStroke(new BasicStroke(1));
			}
			g.setColor(new Color(0, 0, 0, this.invisible));
			g.drawString(s, x, y);
		}
	}

}
