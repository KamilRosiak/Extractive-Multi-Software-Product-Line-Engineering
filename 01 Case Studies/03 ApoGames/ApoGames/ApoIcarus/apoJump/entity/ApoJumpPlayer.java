package apoJump.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.apogames.entity.ApoAnimation;

import apoJump.ApoJumpConstants;
import apoJump.ApoJumpImageContainer;
import apoJump.level.ApoJumpLevel;

public class ApoJumpPlayer extends ApoAnimation {
	
	private final float START_VEC_Y = 1f;
	
	private final float MISSLE_VEC_Y = 1.2f;
	private final float HELICOPTER_VEC_Y = 1.2f;
	
	private final int TIME_MISSLE = 4500;
	private final int TIME_HELICOPTER = 2500;
	
	private boolean bDie;
	
	private boolean bHelicopter;
	
	private boolean bMissle;
	
	private int timeBonus;
	
	private boolean invulnerable;
	
	private int automaticArrow;
	
	
	
	public ApoJumpPlayer(BufferedImage background, float x, float y, float width, float height) {
		super(background, x, y, width, height, 1, 100000000, 2, false);
	}
	
	public void init() {
		super.init();
		this.setBOpaque(true);
		this.bDie = false;
		this.bMissle = false;
		this.bHelicopter = false;
		this.setVelocityY(-this.START_VEC_Y);
		this.automaticArrow = 0;
	}
	
	public int getAutomaticArrow() {
		return this.automaticArrow;
	}

	public void setAutomaticArrow(int automaticArrow) {
		this.automaticArrow = automaticArrow;
	}

	public boolean isBDie() {
		return this.bDie;
	}

	public void setVecX(float newVecX) {
		float oldVecX = super.getVelocityX();
		if (Math.abs(newVecX) > ApoJumpConstants.MAX_VEC_X) {
			if (newVecX > 0) {
				newVecX = ApoJumpConstants.MAX_VEC_X;
			} else {
				newVecX = -ApoJumpConstants.MAX_VEC_X;
			}
		}
		if (newVecX != 0) {
			if (newVecX > oldVecX) {
				super.setDirection(0);
			} else if (newVecX < oldVecX) {
				super.setDirection(1);
			}
		}
		super.setVelocityX(newVecX);
	}
	
	public void setHelicopter() {
		if (this.timeBonus <= 0) {
			this.invulnerable = true;
			this.bHelicopter = true;
			this.bMissle = false;
			this.setVelocityY(-this.HELICOPTER_VEC_Y);
			this.timeBonus = this.TIME_HELICOPTER;
			super.setIBackground(ApoJumpImageContainer.iPlayerGood);
			super.makeImageArray();
		}
	}
	
	public void setMissle() {
		if (this.timeBonus <= 0) {
			this.invulnerable = true;
			this.bHelicopter = false;
			this.bMissle = true;
			this.setVelocityY(-this.MISSLE_VEC_Y);
			this.timeBonus = this.TIME_MISSLE;
			super.setIBackground(ApoJumpImageContainer.iPlayerBest);
			super.makeImageArray();
		}
	}
	
	public void think(int delta, ApoJumpLevel level) {
		super.think(delta);
		if (this.bDie) {
			level.setWin();
		} else {
			this.thinkPosition(delta);
			this.thinkAutomaticArrow(delta, level);
			if (this.getVelocityX() < 0) {
				if (this.getX() < -this.getWidth()) {
					this.setX(ApoJumpConstants.GAME_WIDTH - this.getWidth());
				}
			}
			if (this.getVelocityX() >= 0) {
				if (this.getX() + this.getWidth() >= ApoJumpConstants.GAME_WIDTH) {
					this.setX(this.getX() - ApoJumpConstants.GAME_WIDTH);
				}
			}
			
			if (this.getY() + this.getHeight() - level.getChangeY() > ApoJumpConstants.GAME_HEIGHT) {
				this.bDie = true;
			}
		}
	}
	
	private void thinkAutomaticArrow(int delta, ApoJumpLevel level) {
		if (this.automaticArrow > 0) {
			int size = level.getEnemies().size();
			Rectangle2D rec = new Rectangle2D.Double(0, level.getChangeY() + 100, ApoJumpConstants.GAME_WIDTH, ApoJumpConstants.GAME_HEIGHT);
			for (int i = 0; i < size; i++) {
				if (level.getEnemies().get(i).getY() < level.getChangeY() - ApoJumpConstants.GAME_HEIGHT/2) {
					break;
				} else if (this.automaticArrow > 0) {
					if (level.getEnemies().get(i).isBVisible()) {
						if (level.getEnemies().get(i).intersects((float)rec.getX(), (float)rec.getY(), (float)rec.getWidth(), (float)rec.getHeight())) {	
							int x = (int)(level.getEnemies().get(i).getX() + level.getEnemies().get(i).getWidth()/2);
							if (level.getEnemies().get(i).getVelocityX() > 0) {
								x = (int)(level.getEnemies().get(i).getX() + level.getEnemies().get(i).getWidth() - 3);
							} else if (level.getEnemies().get(i).getVelocityX() < 0) {
								x = (int)(level.getEnemies().get(i).getX() + 3);
							}
							int y = (int)(level.getEnemies().get(i).getY() + level.getEnemies().get(i).getHeight()/2);
							if (level.getEnemies().get(i).getVelocityY() > 0) {
								y = (int)(level.getEnemies().get(i).getY() + level.getEnemies().get(i).getHeight() - 3);
							} else if (level.getEnemies().get(i).getVelocityY() < 0) {
								y = (int)(level.getEnemies().get(i).getY() + 3);
							}
							if (level.makeArrow(x, (int)(y - level.getChangeY()))) {
								this.automaticArrow -= 1;							
							}
							break;
						}
					}
				}
			}
		}
	}
	
	public final int getTimeBonus() {
		if (this.timeBonus > 0) {
			return this.timeBonus;
		} else if (this.invulnerable) {
			return 1;
		}
		return 0;
	}

	public void thinkPosition(int delta) {
		if (this.timeBonus <= 0) {
			this.setVelocityY(this.getVelocityY() + ApoJumpConstants.GRAVITY);
			if (this.invulnerable) {
				if (this.getVelocityY() >= 0) {
					this.invulnerable = false;
				}
			}
		} else {
			this.timeBonus -= delta;
			if (this.timeBonus <= 0) {
				this.bMissle = false;
				this.bHelicopter = false;
				super.setIBackground(ApoJumpImageContainer.iPlayer);
				super.makeImageArray();
			}
		}
		
		this.setX(this.getX() + this.getVelocityX() * delta);
		this.setY(this.getY() + this.getVelocityY() * delta);
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		if (this.getIBackground() == null) {
			g.setColor(Color.RED);
			g.drawRect((int)(this.getX() - changeX), (int)(this.getY() - changeY), (int)(this.getWidth()), (int)(this.getHeight()));
			if (this.bMissle) {
				g.setColor(Color.CYAN);
				g.fillRect((int)(this.getX() - changeX + 3), (int)(this.getY() - changeY + 3), (int)(this.getWidth() - 6), (int)(this.getHeight() - 6));
			} else if (this.bHelicopter) {
				g.setColor(Color.RED);
				g.fillRect((int)(this.getX() - changeX), (int)(this.getY() - changeY - 3), (int)(this.getWidth()), (int)(3));
			}
		} else {
			super.render(g, -changeX, -changeY);
			if (this.automaticArrow > 0) {
				int x = (int)(this.getX() + 1);
				int y = (int)(this.getY() + 22 - changeY);
				// nach rechts
				if (this.getDirection() == 0) {
					x = (int)(this.getX() + 34);
				}
				g.setColor(Color.GREEN.brighter());
				g.fillOval(x, y, 4, 4);
			}
			if (this.getX() < 0) {
				super.render(g, (int)(ApoJumpConstants.GAME_WIDTH - changeX), -changeY);
			}
		}
	}


}
