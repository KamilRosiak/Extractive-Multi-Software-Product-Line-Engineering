package apoCommando.entity;

import java.awt.image.BufferedImage;

import apoCommando.level.ApoMarioLevel;

/**
 * Klasse die ein Partikel darstellt
 * Nachdem die Animation abgelaufen ist, wird sie unsichtbar
 * @author Dirk Aporius
 *
 */
public class ApoMarioParticle extends ApoMarioEntity {

	public ApoMarioParticle(BufferedImage animation, float x, float y, int tiles, long time) {
		super(animation, x, y, animation.getWidth()/tiles, animation.getHeight(), tiles, time, 0);
		this.setBLoop(false);
	}

	public void think(int delta) {
		super.think(delta);
		if (!this.isBAnimation()) {
			super.setBVisible(false);
		}
	}

	@Override
	public void coinCheck(ApoMarioLevel level, int x, int y) {

	}

	@Override
	public void xCheckChange(ApoMarioLevel level, int x, int y) {
		
	}

	@Override
	public void yCheckUp(ApoMarioLevel level, int x, int y) {
		
	}

	@Override
	public void yDownCheck(ApoMarioLevel level, int x, int y, int delta) {
		
	}
	
}