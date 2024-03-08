package apoStarz.entity;

import java.awt.image.BufferedImage;

import apoStarz.level.ApoStarzLevel;

public class ApoStarzGoal extends ApoStarzEntity {

	public ApoStarzGoal(BufferedImage background, float x, float y,
			float width, float height, int tiles, boolean canFall) {
		super(background, x, y, width, height, tiles, canFall);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void think(int delta, ApoStarzLevel level) {
	}

}
