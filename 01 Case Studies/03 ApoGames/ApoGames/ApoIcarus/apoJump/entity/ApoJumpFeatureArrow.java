package apoJump.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import apoJump.ApoJumpConstants;
import apoJump.level.ApoJumpLevel;

public class ApoJumpFeatureArrow extends ApoJumpEntity {

	public static final int MAX_ARROW = 4;
	
	public ApoJumpFeatureArrow(BufferedImage background, float x, float y, float width, float height) {
		super(background, x, y, width, height);
	}

	@Override
	public void update(int delta, ApoJumpLevel level) {
		if ((!level.getPlayer().isBDie()) && (this.isBVisible())) {
			if (this.intersects(level.getPlayer())) {
				this.setBVisible(false);
				level.getPlayer().setAutomaticArrow(MAX_ARROW);
			}
		}
	}

	public void render(Graphics2D g, int changeX, int changeY) {
		if ((this.getY() - changeY >= 0) && (this.getY() - changeY <= ApoJumpConstants.GAME_HEIGHT) && (this.isBVisible())) {
			if (this.getIBackground() == null) {
			} else {
				super.render(g, -changeX, -changeY);
			}
		}
	}

}
