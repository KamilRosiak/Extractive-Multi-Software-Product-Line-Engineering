package apoStarz.entity;

import java.awt.image.BufferedImage;

import apoStarz.level.ApoStarzLevel;

public class ApoStarzFire extends ApoStarzEntity {

	public ApoStarzFire(BufferedImage background, float x, float y,
			float width, float height, int tiles, boolean canFall) {
		super(background, x, y, width, height, tiles, canFall);
	}

	@Override
	public void think(int delta, ApoStarzLevel level) {
	}

}
