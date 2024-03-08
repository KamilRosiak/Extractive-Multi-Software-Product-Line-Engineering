package apoRelax.entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;

import org.apogames.entity.ApoEntity;

public class ApoRelaxString extends ApoEntity {

	public static final int TIME_DECREASE = 25;
	
	public static final Font FONT_STRING = new Font(Font.SANS_SERIF, Font.BOLD, 25);
	
	private String points;
	
	private float invisible;
	
	private int curTime;
	
	private boolean bWithBackground;

	public ApoRelaxString(float x, float y, String s) {
		this(x, y, s, false);
	}
	
	public ApoRelaxString(float x, float y, String s, boolean bWithBackground) {
		super(null, x, y, 1, 1);
		
		this.bWithBackground = bWithBackground;
		this.points = s;
		this.invisible = 255;
		this.curTime = 0;
	}
	
	public void think(int delta) {
		this.curTime -= delta;
		if (this.curTime <= 0) {
			this.curTime = ApoRelaxString.TIME_DECREASE;
			this.invisible -= 1;
			if (this.invisible <= 200) {
				this.invisible = 0;
				super.setBVisible(false);
			}
		}
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		if (this.isBVisible()) {
			String s = this.points;
			g.setFont(ApoRelaxString.FONT_STRING);
			int w = g.getFontMetrics().stringWidth(s);
			int width = w + 10;
			int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
			int height = h + 10;
			int x = (int)(this.getX() - width/2 + (width - w)/2);
			int change = 10;
			int y = (int)(this.getY() + height - (height - h)/2);
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
