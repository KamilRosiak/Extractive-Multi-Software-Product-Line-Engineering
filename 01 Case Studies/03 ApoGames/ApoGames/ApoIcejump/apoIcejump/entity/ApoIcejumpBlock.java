package apoIcejump.entity;

import java.awt.image.BufferedImage;

import apoIcejump.ApoIcejumpConstants;

public class ApoIcejumpBlock extends ApoIcejumpEntity {

	private float normalSpeedVecX;
	private float normalSpeedVecY;
	
	private int hits;
	
	public ApoIcejumpBlock(BufferedImage background, float x, float y, float width, float height, float normalSpeedVecX, float normalSpeedVecY) {
		super(background, x, y, width, height);
		
		this.normalSpeedVecX = normalSpeedVecX;
		this.normalSpeedVecY = normalSpeedVecY;
		this.init();
	}
	
	public void init() {
		super.init();

		this.setVecX(this.normalSpeedVecX);
		this.setVecY(this.normalSpeedVecY);
		this.hits = ApoIcejumpConstants.ICEBLOCK_HITS;
	}
	
	public int getHits() {
		return this.hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public void think(int delta) {
		super.think(delta);
		
		super.setX(super.getX() + super.getVecX() * delta);
		if ((super.getX() < 0) || (super.getX() + super.getWidth() > ApoIcejumpConstants.GAME_WIDTH)) {
			super.setVecX(-super.getVecX());
			if (super.getX() < 0) {
				super.setX(0);
			} else if (super.getX() + super.getWidth() > ApoIcejumpConstants.GAME_WIDTH) {
				super.setX(ApoIcejumpConstants.GAME_WIDTH - super.getWidth());
			}
		}
		float oldVecX = this.getVecX();
		if (Math.abs(super.getVecX() - this.normalSpeedVecX) != 0) {
			if (super.getVecX() > 0) {
				super.setVecX(super.getVecX() - ApoIcejumpConstants.ICEBLOCK_DECREASE_VEC_X);
			} else {
				super.setVecX(super.getVecX() + ApoIcejumpConstants.ICEBLOCK_DECREASE_VEC_X);
			}
			if ((Math.abs(super.getVecX()) <= Math.abs(this.normalSpeedVecX)) ||
				((oldVecX < 0) && (super.getVecX() > 0)) ||
				((oldVecX > 0) && (super.getVecX() < 0))) {
				if (oldVecX < 0) {
					this.setVecX(-Math.abs(this.normalSpeedVecX));
				} else {
					this.setVecX(Math.abs(this.normalSpeedVecX));
				}
			}
		}
		super.setY(super.getY() + super.getVecY() * delta);
		if (Math.abs(super.getStartY() - super.getY()) > ApoIcejumpConstants.ICEBLOCK_MAX_CHANGE_Y) {
			super.setVecY(-super.getVecY());
		}
		if (this.hits <= 0) {
			//Partikelerstellung ...
			this.setBVisible(false);
		}
	}

}
