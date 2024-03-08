package apoNotSoSimple.entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;

import org.apogames.entity.ApoEntity;

public class ApoNotSoSimpleString extends ApoEntity {

	public static final int TIME_DECREASE = 25;
	
	public static final Font FONT_STRING = new Font(Font.SANS_SERIF, Font.BOLD, 25);
	
	private String points;
	
	private float invisible;
	
	private int curTime;
	
	private boolean bWithBackground;

	public ApoNotSoSimpleString(float x, float y, float width, String s) {
		this(x, y, width, s, false);
	}
	
	public ApoNotSoSimpleString(float x, float y, float width, String s, boolean bWithBackground) {
		super(null, x, y, width, width);
		
		this.bWithBackground = bWithBackground;
		this.points = s;
		this.invisible = 255;
		this.curTime = 0;
	}
	
	public void think(int delta) {
		this.curTime -= delta;
		if (this.curTime <= 0) {
			this.curTime = ApoNotSoSimpleString.TIME_DECREASE;
			this.invisible -= 1;
			if (this.invisible <= 100) {
				this.invisible = 0;
				super.setBVisible(false);
			}
		}
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		if (this.isBVisible()) {
			String s = this.points;
			g.setFont(ApoNotSoSimpleString.FONT_STRING);
			int w = g.getFontMetrics().stringWidth(s);
			int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
			int x = (int)(this.getX() + this.getWidth()/2 - w/2);
			int change = 10;
			int y = (int)(this.getY() + this.getHeight()/2 + h/2);
			if (this.bWithBackground) {
				Composite composite = g.getComposite();
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(this.invisible/255f)));
				g.setColor(Color.WHITE);
				g.fillRoundRect((int)(x - change), (int)(y - h - change), (int)(w + 2 * change), (int)(h + 2 * change), 15, 15);
				g.setComposite(composite);
				g.setColor(Color.BLACK);
				g.drawRoundRect((int)(x - change), (int)(y - h - change), (int)(w + 2 * change), (int)(h + 2 * change), 15, 15);
			}
			Composite composite = g.getComposite();
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(this.invisible/255f)));
			g.setColor(Color.BLACK);
			g.drawString(s, x, y);
			g.setComposite(composite);
		}
	}

}
