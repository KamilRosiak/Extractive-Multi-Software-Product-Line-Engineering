package apoJump.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import apoJump.ApoJumpConstants;
import apoJump.level.ApoJumpLevel;

public class ApoJumpFeatureSpring extends ApoJumpEntity {

	private static final float NEW_VEC_Y = 1.1f;
	
	public ApoJumpFeatureSpring(BufferedImage background, float x, float y, float width, float height) {
		super(background, x, y, width, height);
	}

	@Override
	public void update(int delta, ApoJumpLevel level) {
		if ((level.getPlayer().getVelocityY() > 0) && (!level.getPlayer().isBDie()) && (this.isBVisible())) {
			if (new Rectangle2D.Float(this.getX(), this.getY(), this.getWidth(), 4).intersects(level.getPlayer().getX(), level.getPlayer().getY() + level.getPlayer().getHeight() - 3, level.getPlayer().getWidth(), 3)) {
				this.playerJump(level);
			}
		}
	}
	
	public void playerJump(ApoJumpLevel level) {
		level.getPlayer().setVelocityY(-ApoJumpFeatureSpring.NEW_VEC_Y);
		level.getPlayer().setY(this.getY() - level.getPlayer().getHeight());
	}

	public void render(Graphics2D g, int changeX, int changeY) {
		if ((this.getY() - changeY >= 0) && (this.getY() - changeY <= ApoJumpConstants.GAME_HEIGHT) && (this.isBVisible())) {
			if (this.getIBackground() == null) {
				this.renderContent(g, changeX, changeY);
			} else {
				super.render(g, -changeX, -changeY);
			}
		}
	}
	
	public void renderContent(Graphics2D g, int changeX, int changeY) {
		g.setColor(Color.GRAY);
		g.fillRect((int)(this.getX() - changeX), (int)(this.getY() - changeY), (int)(this.getWidth()), (int)(this.getHeight()));
		g.setColor(Color.BLACK);
		g.drawRect((int)(this.getX() - changeX), (int)(this.getY() - changeY), (int)(this.getWidth()), (int)(this.getHeight()));
	}
}
