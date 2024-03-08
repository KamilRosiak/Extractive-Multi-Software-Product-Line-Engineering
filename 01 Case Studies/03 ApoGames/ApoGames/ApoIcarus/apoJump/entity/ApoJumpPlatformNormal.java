package apoJump.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import apoJump.ApoJumpConstants;
import apoJump.level.ApoJumpLevel;

public class ApoJumpPlatformNormal extends ApoJumpEntity {

	public static final float NEW_VEC_Y = 0.6f;
	public static final float NEW_VEC_ENEMY_Y = 0.8f;
	
	private float startVecX;
	private float startVecY;
	
	private int maxVecY = 70;
	
	private float curChangeY;
	
	public ApoJumpPlatformNormal(BufferedImage background, float x, float y, float width, float height, float startVecX, float startVecY) {
		super(background, x, y, width, height);
		this.startVecX = startVecX;
		this.startVecY = startVecY;
		this.init();
	}
	
	public void init() {
		super.init();
		super.setVelocityX(this.startVecX);
		super.setVelocityY(this.startVecY);
		this.curChangeY = 0;
	}

	public int getMaxVecY() {
		return this.maxVecY;
	}

	public void setMaxVecY(int maxVecY) {
		this.maxVecY = maxVecY;
	}

	@Override
	public void update(int delta, ApoJumpLevel level) {
		if ((level.getPlayer().getVelocityY() > 0) && (!level.getPlayer().isBDie()) && (this.isBVisible())) {
			if (new Rectangle2D.Float(this.getX(), this.getY(), this.getWidth(), 4).intersects(level.getPlayer().getX(), level.getPlayer().getY() + level.getPlayer().getHeight() - 3, level.getPlayer().getWidth(), 3)) {
				level.getPlayer().setVelocityY(-ApoJumpPlatformNormal.NEW_VEC_Y);
				level.getPlayer().setY(this.getY() - level.getPlayer().getHeight());
				level.addJump();
				this.playerJumpOnPlatform();
			}else if (level.getPlayer().getX() < 0) {
				float x = ApoJumpConstants.GAME_WIDTH + level.getPlayer().getX();
				if (new Rectangle2D.Float(this.getX(), this.getY(), this.getWidth(), 4).intersects(x, level.getPlayer().getY() + level.getPlayer().getHeight() - 3, level.getPlayer().getWidth(), 3)) {
					level.getPlayer().setVelocityY(-ApoJumpPlatformNormal.NEW_VEC_Y);
					level.getPlayer().setY(this.getY() - level.getPlayer().getHeight());
					level.addJump();
					this.playerJumpOnPlatform();
				}
			} else if (this.intersects(level.getPlayer())) {
				this.playerIntersectsWithPlatform(level);
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
			if (Math.abs(this.curChangeY) >= Math.abs(this.maxVecY)) {
				this.setVelocityY(-this.getVelocityY());
			}
		}
	}
	
	public void playerIntersectsWithPlatform(ApoJumpLevel level) {
		
	}
	
	public void playerJumpOnPlatform() {
		
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		if ((this.getY() - changeY >= 0) && (this.getY() - changeY <= ApoJumpConstants.GAME_HEIGHT) && (this.isBVisible())) {
			if (this.getIBackground() == null) {
				if (this.getVelocityX() != 0) {
					g.setColor(Color.BLUE);
				} else if (this.getVelocityY() != 0) {
					g.setColor(Color.GRAY);
				} else {
					g.setColor(Color.GREEN);
				}
				g.fillRoundRect((int)(this.getX() - changeX), (int)(this.getY() - changeY), (int)(this.getWidth()), (int)(this.getHeight()), 15, 15);
				g.setColor(Color.BLACK);
				g.drawRoundRect((int)(this.getX() - changeX), (int)(this.getY() - changeY), (int)(this.getWidth()), (int)(this.getHeight()), 15, 15);
			} else {
				super.render(g, -changeX, -changeY);
			}
		}
	}	

}
