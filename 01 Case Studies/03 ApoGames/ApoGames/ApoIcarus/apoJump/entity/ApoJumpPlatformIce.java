package apoJump.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import apoJump.ApoJumpConstants;

public class ApoJumpPlatformIce extends ApoJumpPlatformNormal {

	public ApoJumpPlatformIce(BufferedImage background, float x, float y, float width, float height, float startVecX, float startVecY) {
		super(background, x, y, width, height, startVecX, startVecY);
	}
	
	public void playerJumpOnPlatform() {
		this.setBVisible(false);
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		if ((this.getY() - changeY >= 0) && (this.getY() - changeY <= ApoJumpConstants.GAME_HEIGHT) && (this.isBVisible())) {
			if (this.getIBackground() == null) {
				g.setColor(Color.WHITE);
				g.fillRoundRect((int)(this.getX() - changeX), (int)(this.getY() - changeY), (int)(this.getWidth()), (int)(this.getHeight()), 15, 15);
				g.setColor(Color.BLACK);
				g.drawRoundRect((int)(this.getX() - changeX), (int)(this.getY() - changeY), (int)(this.getWidth()), (int)(this.getHeight()), 15, 15);
			} else {
				super.render(g, changeX, changeY);
			}
		}
	}

}
