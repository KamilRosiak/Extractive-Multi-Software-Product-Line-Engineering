package apoIcejump.entity;

import java.awt.image.BufferedImage;

import org.apogames.entity.ApoAnimation;
import org.apogames.entity.ApoEntity;

import apoIcejump.ApoIcejumpConstants;

public class ApoIcejumpEntity extends ApoAnimation {
	
	public ApoIcejumpEntity(BufferedImage background, float x, float y, float width, float height) {
		super(background, x, y, width, height, 1, 100000);
	}

	public boolean isAboveEntity(ApoEntity entity) {
		if (this.intersects(entity)) {
			if (this.getRec().getY() < entity.getRec().getY()) {
				if (this.getRec().getY() + this.getRec().getHeight() < entity.getRec().getY() + ApoIcejumpConstants.ABOVE_PIXEL) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isDownEntity(ApoEntity entity) {
		if (this.intersects(entity)) {
			if (this.getRec().getY() > entity.getRec().getY()) {
				if (entity.getRec().getY() + entity.getRec().getHeight() < this.getRec().getY() + ApoIcejumpConstants.ABOVE_PIXEL) {
					return true;
				}
			}
		}
		return false;
	}
}
