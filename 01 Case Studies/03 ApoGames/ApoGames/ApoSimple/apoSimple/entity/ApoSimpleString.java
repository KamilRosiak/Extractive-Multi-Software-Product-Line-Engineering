package apoSimple.entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import org.apogames.entity.ApoEntity;

import apoSimple.ApoSimpleConstants;

public class ApoSimpleString extends ApoEntity {

	private final int MAX_WIDTH = ApoSimpleConstants.GAME_WIDTH - 80;
	
	public static final int TIME_DECREASE = 10;
	
	public static final Font FONT_STRING = ApoSimpleConstants.ORIGINAL_FONT.deriveFont(27f).deriveFont(Font.BOLD);//new Font(Font.SANS_SERIF, Font.BOLD, 25);
	public static final Font FONT_STRING_LITTLE = ApoSimpleConstants.ORIGINAL_FONT.deriveFont(20f).deriveFont(Font.BOLD);
	public static final Font FONT_STRING_REAL_LITTLE = ApoSimpleConstants.ORIGINAL_FONT.deriveFont(15f).deriveFont(Font.BOLD);
	
	private String points;
	
	private float invisible;
	
	private int curTime;
	
	private boolean bWithBackground;
	
	private int timeDecrease;
	
	private boolean bFade;
	
	private Font font;

	public ApoSimpleString(float x, float y, float width, String s) {
		this(x, y, width, s, false);
	}
	
	public ApoSimpleString(float x, float y, float width, String s, boolean bWithBackground) {
		this(x, y, width, s, bWithBackground, ApoSimpleString.TIME_DECREASE, true);
	}
	
	public ApoSimpleString(float x, float y, float width, String s, boolean bWithBackground, int timeDecrease, boolean bFade) {
		this(x, y, width, s, bWithBackground, timeDecrease, bFade, ApoSimpleString.FONT_STRING);
	}
	
	public ApoSimpleString(float x, float y, float width, String s, boolean bWithBackground, int timeDecrease, boolean bFade, final Font font) {
		super(null, x, y, width, width);
		
		this.bWithBackground = bWithBackground;
		this.points = s;
		this.invisible = 255;
		this.timeDecrease = timeDecrease;
		this.curTime = this.timeDecrease;
		this.bFade = bFade;
		this.font = font;
	}
	
	public String getText() {
		return this.points;
	}
	
	public void think(int delta) {
		this.curTime -= delta;
		if (this.curTime <= 0) {
			if (this.bFade) {
				this.curTime = this.timeDecrease;
				this.invisible -= 1;
				if (this.invisible <= 50) {
					this.invisible = 0;
					super.setBVisible(false);
				}
			} else {
				super.setBVisible(false);
			}
		}
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		if (this.isBVisible()) {
			String s = this.points;
			int change = 10;
			g.setFont(this.font);
			
			int w = 0;
			ArrayList<String> strings = ApoSimpleConstants.drawSpeech(g, s, this.MAX_WIDTH);
			for (int i = 0; i < strings.size(); i++) {
				if (w < g.getFontMetrics().stringWidth(strings.get(i))) {
					w = g.getFontMetrics().stringWidth(strings.get(i));
				}
			}

			int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
			int x = (int)(this.getX() + this.getWidth()/2 - w/2);
			if (x < change) {
				x = change;
			}
			int y = (int)(this.getY() + this.getHeight()/2 + h/2);
			if (y < h + change) {
				y = h + change;
			} else if (y + h * (strings.size() - 1) > ApoSimpleConstants.GAME_HEIGHT - change) {
				y = ApoSimpleConstants.GAME_HEIGHT - change - h * (strings.size() - 1);
			}
			if (this.bWithBackground) {
				Composite composite = g.getComposite();
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(this.invisible/255f)));
				g.setColor(Color.WHITE);
				g.fillRoundRect((int)(x - change), (int)(y - h - change), (int)(w + 2 * change), (int)(h * strings.size() + 2 * change), 15, 15);
				g.setComposite(composite);
				g.setColor(Color.BLACK);
				g.drawRoundRect((int)(x - change), (int)(y - h - change), (int)(w + 2 * change), (int)(h * strings.size() + 2 * change), 15, 15);
			}
			Composite composite = g.getComposite();
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(this.invisible/255f)));
			g.setColor(Color.BLACK);
			for (int i = 0; i < strings.size(); i++) {
				g.drawString(strings.get(i), x, y + i * h);
			}
			g.setComposite(composite);
		}
	}

}
