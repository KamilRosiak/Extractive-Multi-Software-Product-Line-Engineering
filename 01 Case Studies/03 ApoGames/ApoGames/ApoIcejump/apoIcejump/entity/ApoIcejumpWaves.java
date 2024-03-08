package apoIcejump.entity;

import java.awt.image.BufferedImage;

import org.apogames.entity.ApoEntity;

import apoIcejump.ApoIcejumpConstants;

public class ApoIcejumpWaves extends ApoEntity {

	public ApoIcejumpWaves(BufferedImage background, float x, float y, float width, float height) {
		super(background, x, y, width, height);
	}
	
	public void init() {
		super.init();
		
		super.setVecX(-(float)(Math.random() * 0.015f));
		super.setVecY((float)(Math.random() * 0.005));
	}
	
	public void think(int delta) {
		super.setX(super.getX() + super.getVecX() * delta);
		if (Math.abs(super.getStartX() - super.getX()) >= 90) {
			super.setX(super.getStartX());
		}
		super.setY(super.getY() + super.getVecY() * delta);
		if (Math.abs(super.getY() - super.getStartY()) > ApoIcejumpConstants.WAVE_MAX_CHANGE_Y) {
			super.setVecY(-super.getVecY());
		}
	}

}
