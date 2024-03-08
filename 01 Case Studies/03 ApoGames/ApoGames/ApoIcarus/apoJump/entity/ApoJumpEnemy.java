package apoJump.entity;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.apogames.entity.ApoAnimation;

import apoJump.ApoJumpConstants;
import apoJump.level.ApoJumpLevel;

public class ApoJumpEnemy extends ApoAnimation {

	private int points;
	
	private int maxY;
	
	private float curChangeY;
	
	public ApoJumpEnemy	(BufferedImage iAnimation, float x, float y, float width, float height, int tiles, long time, int maxDirections, boolean bRGB, float startVecX, float startVecY, int addPoints) {
		super(iAnimation, x, y, width, height, tiles, time, maxDirections, bRGB);
		this.points = addPoints;
		this.setVelocityX(startVecX);
		this.setVelocityY(startVecY);
		
		if (this.getVelocityY() != 0) {
			this.maxY = 50;
		}
	}
	
	public void init() {
		super.init();
		this.setBOpaque(true);
		
		this.curChangeY = 0;
	}
	
	public int getMaxY() {
		return this.maxY;
	}

	public void setMaxY(int maxY) {
		this.maxY = maxY;
	}

	public void update(int delta, ApoJumpLevel level) {
		super.think(delta);
		if (this.isBVisible()) {
			if (level.getPlayer().getTimeBonus() <= 0) {
				if (new Rectangle2D.Float(this.getX(), this.getY(), this.getWidth(), 4).intersects(level.getPlayer().getX(), level.getPlayer().getY() + level.getPlayer().getHeight() - 3, level.getPlayer().getWidth(), 3)) {
					this.playerJumpOnPlatform(level);
				}
				if (this.intersects(level.getPlayer())) {
					this.playerIntersectsWithPlatform(level);
				}
			}
		}
		if (this.getVelocityX() != 0) {
			this.setX(this.getX() + this.getVelocityX() * delta);
			if (this.getX() < 0) {
				this.setVelocityX(-this.getVelocityX());
				this.setX(0);
			} else if (this.getX() + this.getWidth() > ApoJumpConstants.GAME_WIDTH) {
				this.setVelocityX(-this.getVelocityX());
				this.setX(ApoJumpConstants.GAME_WIDTH - this.getWidth());
			}
		}
		if (this.getVelocityY() != 0) {
			float change = this.getVelocityY() * delta;
			this.curChangeY += change;
			this.setY(this.getY() + change);
			if (Math.abs(this.curChangeY) >= Math.abs(this.maxY)) {
				this.setVelocityY(-this.getVelocityY());
			}
		}
	}
	
	public int getPoints() {
		return this.points;
	}

	public void playerJumpOnPlatform(ApoJumpLevel level) {
		level.addPoint(this.points);
		level.getPlayer().setVelocityY(-ApoJumpPlatformNormal.NEW_VEC_ENEMY_Y);
		level.getPlayer().setY(this.getY() - level.getPlayer().getHeight());
		level.addEnemyKill();
		this.setBVisible(false);
	}

	public void playerIntersectsWithPlatform(ApoJumpLevel level) {
		level.setWin();
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		if (this.getIBackground() == null) {
		} else {
			super.render(g, -changeX, -changeY);
		}
	}
}
