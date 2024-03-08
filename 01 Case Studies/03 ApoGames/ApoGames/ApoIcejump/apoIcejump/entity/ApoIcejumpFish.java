package apoIcejump.entity;

import java.awt.image.BufferedImage;

import org.apogames.entity.ApoAnimation;

import apoIcejump.ApoIcejumpConstants;

public class ApoIcejumpFish extends ApoAnimation {
	
	public ApoIcejumpFish(BufferedImage animation, float x, float y, float width, float height, int tiles, long time, boolean bLeft) {
		super(animation, x, y, width, height, tiles, time);
		
		if (!bLeft) {
			this.setVecX((float)(Math.random() * 0.05 - 0.05f));
			if (this.getVecX() > -0.003f) {
				this.setVecX(-0.004f);
			}
		} else {
			this.setVecX((float)(Math.random() * 0.05));
			if (this.getVecX() < 0.003f) {
				this.setVecX(0.004f);
			}
		}
	}
	
	public void init() {
		super.init();
	}
	
	public void think(int delta) {
		if (super.isBVisible()) {
			super.think(delta);
			if (this.getVecX() != 0) {
				super.setX(super.getX() + super.getVecX() * delta);
			}
			if ((this.getX() + this.getWidth() < 0) ||
				(this.getX() > ApoIcejumpConstants.GAME_WIDTH)) {
				super.setBVisible(false);
			}
		}
	}

}
