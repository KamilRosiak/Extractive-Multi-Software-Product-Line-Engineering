package apoSlitherLink.level;

import java.awt.Graphics2D;
import java.util.ArrayList;

import apoSlitherLink.ApoSlitherLinkImages;

public class ApoSlitherLinkBlinkLine {

	private final float VEC = 0.55f;
	
	private final int realStartX;
	private final int realStartY;
	
	private final int startX;
	private final int startY;
	
	private float changeX;
	private float changeY;
	
	private int width;
	
	private final int vecX;
	private final int vecY;
	
	private boolean bNext;
	
	private ArrayList<ApoSlitherLinkBlinkHelp> help;
	
	public ApoSlitherLinkBlinkLine(int realStartX, int realStartY, int startX, int startY, int width, int vecX, int vecY) {
		this.realStartX = realStartX;
		this.realStartY = realStartY;
		this.startX = startX;
		this.startY = startY;
		this.width = width;
		this.vecX = vecX;
		this.vecY = vecY;
		this.changeX = 0;
		this.changeY = 0;
		this.bNext = false;
		this.help = new ArrayList<ApoSlitherLinkBlinkHelp>();
	}
	
	public final int getRealStartX() {
		return this.realStartX;
	}

	public final int getRealStartY() {
		return this.realStartY;
	}

	public final int getStartX() {
		return this.startX;
	}

	public final int getStartY() {
		return this.startY;
	}

	public final int getWidth() {
		return this.width;
	}

	public final int getVecX() {
		return this.vecX;
	}

	public final int getVecY() {
		return this.vecY;
	}

	public final boolean isBNext() {
		return this.bNext;
	}
	
	public final boolean isReady() {
		if (this.help.size() <= 0) {
			return true;
		}
		return false;
	}

	public void think(int delta) {
		if (!this.bNext) {
			for (int i = this.help.size() - 1; i >= 0; i--) {
				this.help.get(i).think(delta);
				if (this.help.get(i).isInvisible()) {
					this.help.remove(i);
				}
			}
			this.changeX += this.vecX * this.VEC;
			this.changeY += this.vecY * this.VEC;
			this.help.add(new ApoSlitherLinkBlinkHelp((int)(this.changeX + this.startX * this.width), (int)(this.changeY + this.startY * this.width)));
			if (Math.abs(this.changeX) > this.width) {
				this.bNext = true;
			}
			if (Math.abs(this.changeY) > this.width) {
				this.bNext = true;
			}
		}
		
		for (int i = this.help.size() - 1; i >= 0; i--) {
			this.help.get(i).think(delta);
			if (this.help.get(i).isInvisible()) {
				this.help.remove(i);
			}
		}
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
//		for (int i = 0; i < this.help.size(); i++) {
//			this.help.get(i).render(g, changeX, changeY);
//		}
		g.drawImage(ApoSlitherLinkImages.star, (int)(this.changeX + changeX + this.startX * this.width - 1 - ApoSlitherLinkImages.star.getWidth()/2), (int)(this.changeY - 3 + changeY + this.startY * this.width - ApoSlitherLinkImages.star.getHeight()/2), null);
	}
}
