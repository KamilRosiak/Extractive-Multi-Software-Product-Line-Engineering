package apoJump.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import apoJump.level.ApoJumpLevel;

public class ApoJumpFeatureHelicopter extends ApoJumpFeatureSpring {

	public ApoJumpFeatureHelicopter(BufferedImage background, float x, float y, float width, float height) {
		super(background, x, y, width, height);
	}

	@Override
	public void update(int delta, ApoJumpLevel level) {
		if ((!level.getPlayer().isBDie()) && (this.isBVisible())) {
			if (this.intersects(level.getPlayer())) {
				this.playerJump(level);
			}
		}
	}
	
	public void playerJump(ApoJumpLevel level) {
		level.getPlayer().setHelicopter();
		this.setBVisible(false);
	}
	
	public void renderContent(Graphics2D g, int changeX, int changeY) {
		g.setColor(Color.RED);
		g.fillRect((int)(this.getX() - changeX), (int)(this.getY() - changeY), (int)(this.getWidth()), (int)(this.getHeight()));
		g.setColor(Color.BLACK);
		g.drawRect((int)(this.getX() - changeX), (int)(this.getY() - changeY), (int)(this.getWidth()), (int)(this.getHeight()));
	}
}
