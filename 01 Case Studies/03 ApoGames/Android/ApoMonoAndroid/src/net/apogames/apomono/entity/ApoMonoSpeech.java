package net.apogames.apomono.entity;

import net.apogames.apomono.ApoMonoConstants;
import net.apogames.apomono.game.ApoMonoPanel;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;

public class ApoMonoSpeech {

	private float x, y;
	
	private boolean bVisible, bLeft, bUp;
	
	private String text;
	
	private final ApoMonoPanel panel;
	
	public ApoMonoSpeech(final ApoMonoPanel panel, final int x, final int y, final boolean bLeft, final boolean bUp, final String text) {
		this.panel = panel;
		this.x = x;
		this.y = y;
		this.bLeft = bLeft;
		this.text = text;
		this.bVisible = true;
		this.bUp = bUp;
	}

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}
	
	public void setX(final float x) {
		this.x = x;
	}

	public void setY(final float y) {
		this.y = y;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public boolean isLeft() {
		return bLeft;
	}

	public void setLeft(final boolean bLeft) {
		this.bLeft = bLeft;
	}

	public boolean isUp() {
		return bUp;
	}

	public void setUp(boolean bUp) {
		this.bUp = bUp;
	}

	public boolean isVisible() {
		return bVisible;
	}

	public void setVisible(final boolean bVisible) {
		this.bVisible = bVisible;
	}
	
	public void think(final int delta) {
	}
	
	public void render(final BitsGLGraphics g) {
		this.render(g, 0, 0);
	}
	
	public void render(final BitsGLGraphics g, int changeX, int changeY) {
		if (this.isVisible()) {
			g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
			float w = ApoMonoPanel.game_font.getLength(this.text);
			float myWidth = w + 10;
			float myHeight = 15;
			float myX = this.x + 10 - changeX;
			float myY = this.y - changeY;
			if (myX - myWidth + 19 < 0) {
				this.bLeft = false;
			} else if (myX + myWidth > ApoMonoConstants.GAME_WIDTH) {
				this.bLeft = true;
			}
			
			if (this.bLeft) {
				myX -= myWidth + 20;
			}
			if (!this.bUp) {
				myY += 13;
			}
			if (myX < 0) {
				this.bLeft = false;
				myX = this.x + 10 - changeX;
			}
			
			g.fillRect(myX, myY, myWidth, myHeight);
			g.setColor(ApoMonoConstants.DARK_BRIGHT[0], ApoMonoConstants.DARK_BRIGHT[1], ApoMonoConstants.DARK_BRIGHT[2], 1f);

			if (this.bLeft) {
				g.fillRect(myX - 2, myY, 2, myHeight);
				g.fillRect(myX  + myWidth, myY, 2, myHeight - 3);
				g.fillRect(myX, myY - 2, myWidth, 2);
				g.fillRect(myX, myY + myHeight, myWidth - 2, 2);
				g.setLineSize(2.0f * ApoMonoConstants.MAX);
				g.drawLine(myX + myWidth - 2, myY + myHeight, myX + myWidth + 5, myY + myHeight + 4);
				g.drawLine(myX + myWidth + 1, myY + myHeight - 3, myX + myWidth + 5, myY + myHeight + 4);
			} else {
				g.fillRect(myX - 2, myY, 2, myHeight - 3);
				g.fillRect(myX  + myWidth, myY, 2, myHeight);
				g.fillRect(myX, myY - 2, myWidth, 2);
				g.fillRect(myX + 3, myY + myHeight, myWidth - 3, 2);
				g.setLineSize(2.0f * ApoMonoConstants.MAX);
				g.drawLine(myX - 2, myY + myHeight - 3, myX - 5, myY + myHeight + 4);
				g.drawLine(myX + 3, myY + myHeight + 1, myX - 5, myY + myHeight + 4);
			}
			g.setLineSize(1.0f * ApoMonoConstants.MAX);
			
			this.panel.drawString(g, this.text, (int)(myX + myWidth/2 - w/2), (int)(myY + 1 - 3), ApoMonoPanel.game_font, ApoMonoConstants.DARK_BRIGHT, true);
		}
	}
	
}
