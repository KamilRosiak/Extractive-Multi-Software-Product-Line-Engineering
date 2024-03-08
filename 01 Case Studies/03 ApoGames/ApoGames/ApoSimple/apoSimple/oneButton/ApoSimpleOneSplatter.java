package apoSimple.oneButton;

import java.awt.image.BufferedImage;

import org.apogames.entity.ApoEntity;

import apoSimple.ApoSimpleConstants;

public class ApoSimpleOneSplatter extends ApoEntity {
	
	private final int MAX_TIME = 500;
	
	private final float VEC = 0.5f;
	
	private int time;
	
	public ApoSimpleOneSplatter(BufferedImage iBackground, float x, float y, float width, float height) {
		super(iBackground, x, y, width, height);
		
		this.setVecX((float)(Math.random() * this.VEC) - this.VEC/2);
		this.setVecY((float)(Math.random() * this.VEC) - this.VEC/2);
		
		this.time = 0;
	}
	
	public void think(int delta) {
		if (this.isBVisible()) {
			this.time += delta;
			if (this.time >= this.MAX_TIME) {
				this.setBVisible(false);
			}
			this.setX(this.getX() + this.getVecX() * delta);
			this.setY(this.getY() + this.getVecY() * delta);
			if ((this.getX() + this.getWidth() < 0) ||
				(this.getY() + this.getHeight() < 0) ||
				(this.getX() > ApoSimpleConstants.GAME_WIDTH) ||
				(this.getY() > ApoSimpleConstants.GAME_HEIGHT)) {
				this.setBVisible(false);
			}
		}
	}

}
