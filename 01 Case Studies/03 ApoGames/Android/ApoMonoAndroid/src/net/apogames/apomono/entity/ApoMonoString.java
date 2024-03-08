package net.apogames.apomono.entity;

import net.apogames.apomono.ApoMonoConstants;
import net.apogames.apomono.game.ApoMonoPanel;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;

public class ApoMonoString {

	public static final int TIME_DECREASE = 10;
	
	private final String drawString;
	
	private int invisible;
	
	private int curTime;
	
	private final float direction;
	
	private final float x, y;
	
	private boolean bWithBackground;
	
	private int timeDecrease;
	
	private boolean bFade, bVisible;
	
	public ApoMonoString(final float x, final float y, final float direction, String s, final boolean bWithBackground, final int timeDecrease, final boolean bFade) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.bWithBackground = bWithBackground;
		this.drawString = s;
		this.invisible = 255;
		this.curTime = 0;
		this.timeDecrease = timeDecrease;
		this.curTime = this.timeDecrease;
		this.bFade = bFade;
		this.bVisible = true;
	}
	
	public boolean isVisible() {
		return bVisible;
	}

	public void setVisible(boolean bVisible) {
		this.bVisible = bVisible;
	}

	public void think(int delta) {
		this.curTime -= delta;
		if (this.curTime <= 0) {
			if (this.bFade) {
				this.curTime = this.timeDecrease;
				this.invisible -= 1;
				if (this.invisible <= 50) {
					this.invisible = 0;
					this.bVisible = false;
				}
			} else {
				this.bVisible = false;
			}
		}
	}
	
	public void render(final BitsGLGraphics g, int changeX, int changeY) {
		if (this.isVisible()) {
			
			String s = this.drawString;
			int w = (int)(ApoMonoPanel.game_font.getLength(s) + 10);
			int h = ApoMonoPanel.game_font.mCharCellHeight;
			int x = (int)(this.x + this.direction/2 - w/2) - changeX - 5;
			int change = 10;
			int y = (int)(this.y + this.direction/2 + h/2) - changeY;
			if (this.bWithBackground) {
				g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], this.invisible/255f);
				g.fillRect((int)(x - change), (int)(y - h - change), (int)(w + 2 * change), (int)(h + 2 * change));
				g.setColor(0, 0, 0, this.invisible);
				g.setLineSize(3);
				g.drawRect((int)(x - change), (int)(y - h - change), (int)(w + 2 * change), (int)(h + 2 * change));
				g.setLineSize(1);
			}
			g.setColor(ApoMonoConstants.DARK[0], ApoMonoConstants.DARK[1], ApoMonoConstants.DARK[2], this.invisible/255f);
			g.setFont(ApoMonoPanel.game_font);
			g.drawText(s, x, y - h);
		}
	}

}
