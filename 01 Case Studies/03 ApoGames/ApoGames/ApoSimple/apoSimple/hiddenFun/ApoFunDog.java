package apoSimple.hiddenFun;

import java.awt.image.BufferedImage;

import apoSimple.ApoSimpleImages;

public class ApoFunDog extends ApoFunEntity {

	public ApoFunDog(float x, float y) {
		super(ApoSimpleImages.LITTLE_DOG_DOWN, x, y, ApoFunEntity.DOG_DOWN);
	}
	
	public void init() {
		super.init();
		
		this.setVecX((float)(Math.random() * 0.2 - 0.1));
		this.setVecY((float)(Math.random() * 0.2 - 0.1));
	}
	
	public void think(int delta, ApoFun game) {
		super.think(delta);
		
		this.setX(this.getX() + this.getVecX() * delta);
		this.setY(this.getY() + this.getVecY() * delta);
		
		if (this.getX() + this.getWidth() > game.getRec().x + game.getRec().width) {
			this.setX(game.getRec().x + game.getRec().width - this.getWidth());
			this.setVecX(-this.getVecX());
		}
		if (this.getX() < game.getRec().x) {
			this.setX(game.getRec().x);
			this.setVecX(-this.getVecX());
		}
		if (this.getY() + this.getHeight() > game.getRec().y + game.getRec().height) {
			this.setY(game.getRec().y + game.getRec().height - this.getHeight());
			this.setVecY(-this.getVecY());
		}
		if (this.getY() < game.getRec().y) {
			this.setY(game.getRec().y);
			this.setVecY(-this.getVecY());
		}
		if (this.getVecX() < 0) {
			if (Math.abs(this.getVecX()) < Math.abs(this.getVecY())) {
				if (this.getVecY() < 0) {
					this.setId(ApoFunEntity.DOG_UP);
				} else {
					this.setId(ApoFunEntity.DOG_DOWN);
				}
			} else {
				this.setId(ApoFunEntity.DOG_LEFT);
			}
		} else {
			if (Math.abs(this.getVecX()) < Math.abs(this.getVecY())) {
				if (this.getVecY() < 0) {
					this.setId(ApoFunEntity.DOG_UP);
				} else {
					this.setId(ApoFunEntity.DOG_DOWN);
				}
			} else {
				this.setId(ApoFunEntity.DOG_RIGHT);
			}
		}
		if (this.getId() == ApoFunEntity.DOG_DOWN) {
			this.setIBackground(ApoSimpleImages.LITTLE_DOG_DOWN);
		} else if (this.getId() == ApoFunEntity.DOG_RIGHT) {
			this.setIBackground(ApoSimpleImages.LITTLE_DOG_RIGHT);
		} else if (this.getId() == ApoFunEntity.DOG_LEFT) {
			this.setIBackground(ApoSimpleImages.LITTLE_DOG_LEFT);
		} else if (this.getId() == ApoFunEntity.DOG_UP) {
			this.setIBackground(ApoSimpleImages.LITTLE_DOG_UP);
		}
	}
	
	public void setIBackground(BufferedImage iBackground) {
		super.setIBackground(iBackground);
		this.setWidth(iBackground.getWidth());
		this.setHeight(iBackground.getHeight());
	}

}
