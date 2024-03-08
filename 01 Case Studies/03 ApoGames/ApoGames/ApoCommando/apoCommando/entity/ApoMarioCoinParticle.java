package apoCommando.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import apoCommando.ApoMarioConstants;
import apoCommando.level.ApoMarioLevel;

public class ApoMarioCoinParticle extends ApoMarioCoin {

	private final float MAX_Y = 1.4f * ApoMarioConstants.TILE_SIZE;
	
	public ApoMarioCoinParticle(BufferedImage animation, BufferedImage particle, float x, float y, int tiles, long time) {
		super(animation, particle, x, y, tiles, time, 0);
		this.setVelocityY(ApoMarioConstants.GOODIE_VEC_Y);
	}
	
	public void think(int delta, ApoMarioLevel level) {
		super.setLevel(level);
		if (this.isBVisible()) {
			super.think(delta);
			this.setCurY(this.getCurY() + delta * this.getVelocityY());
			if (this.getCurY() >= this.MAX_Y) {
				this.setCurY(this.MAX_Y);
				this.setBVisible(false);
			}
		} else {
			super.think(delta);
		}
	}

	/**
	 * rendert den Spieler, falls er sichtbar ist hin
	 */
	public void render(Graphics2D g, int changeX, int changeY) {
		try {
			super.render(g, changeX, (int)(changeY + this.getCurY()));
		} catch (Exception ex) {
		}
	}
}
