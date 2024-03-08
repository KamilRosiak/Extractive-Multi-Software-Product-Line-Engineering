package apoStarz.entity;

import java.awt.image.BufferedImage;

import apoStarz.level.ApoStarzLevel;

public class ApoStarzBlock extends ApoStarzEntity {

	public ApoStarzBlock(BufferedImage background, float x, float y, float width, float height, int tiles, boolean bCanFall) {
		super(background, x, y, width, height, tiles, bCanFall);
	}

	@Override
	public void think(int delta, ApoStarzLevel level) {
		if ((this.isBCanFall()) && (this.isBFalling())) {
			if (!this.isNextBlocked(level)) {
				if (this.getVecX() != 0) {
					this.setX(this.getX() + this.getVecX());
				} else if (this.getVecY() != 0) {
					this.setY(this.getY() + this.getVecY());
				}
				//System.out.println("vecX = "+this.getVecX()+" vecY = "+this.getVecY());
			}
		}
	}

}
