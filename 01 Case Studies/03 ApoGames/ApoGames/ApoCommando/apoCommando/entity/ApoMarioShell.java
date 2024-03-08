package apoCommando.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import apoCommando.ApoMarioConstants;
import apoCommando.level.ApoMarioLevel;

public class ApoMarioShell extends ApoMarioEnemy {

	private int player;
	
	public ApoMarioShell(BufferedImage animation, float x, float y, float width, float height, int tiles, long time, int player, int id) {
		super(animation, x, y, width, height, tiles, time, 0, id);
		this.player = player;
	}
	
	public int getPlayer() {
		return this.player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	/**
	 * gibt das aktuelle Rechteck der Entity zurück
	 * @return gibt das aktuelle Rechteck der Entity zurück
	 */
	public Rectangle2D.Float getRec() {
		return new Rectangle2D.Float(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}
	
	/**
	 * gibt das Rechteck für den nächsten Schritt der Entity zurück
	 * @return gibt das Rechteck für den nächsten Schritt der Entity zurück
	 */
	public Rectangle2D.Float getNextRec(int delta) {
		return new Rectangle2D.Float(this.getX() + delta * this.getVelocityX(), this.getY() + delta * this.getVelocityY(), this.getWidth(), this.getHeight());
	}
	
	public void thinkJump(int delta, ApoMarioLevel level) {
		if ((level.getPlayer().isBVisible()) && (level.getPlayer().getRec().intersects(this.getRec()))) {
			if ((level.getPlayer().getY() < this.getY()) && (level.getPlayer().getY() + level.getPlayer().getHeight() < this.getY() + this.getHeight())) {
				Rectangle2D.Float cut = (Rectangle2D.Float)level.getPlayer().getRec().createIntersection(this.getRec());
				if ((cut.width + 2 * ApoMarioConstants.SIZE > cut.height) && (level.getPlayer().getVelocityY() >= 0)) {
					if (this.getVelocityX() != 0) {
						this.setVelocityX(0);
					} else {
						Rectangle2D.Float rec = level.getPlayer().getRec();
						if (rec.x + rec.width/2 < this.getX() + this.getWidth()/2) {
							this.setVelocityX(ApoMarioConstants.SHELL_VEC_X);	
						} else {
							this.setVelocityX(-ApoMarioConstants.SHELL_VEC_X);
						}
					}
					level.getPlayer().setY(this.getRec().y - level.getPlayer().getHeight());
					level.getPlayer().setEnemyJump();
				} else {
					this.damageCheck(level);
				}
			} else {
				this.damageCheck(level);
			}
		}
	}
	
	private void damageCheck(ApoMarioLevel level) {
		Rectangle2D.Float rec = level.getPlayer().getRec();
		if (rec.intersects(this.getRec())) {
			if (this.getVelocityX() == 0) {
				if (rec.x < this.getX()) {
					this.setVelocityX(ApoMarioConstants.SHELL_VEC_X);
					this.setX(rec.x + rec.width);
				} else {
					this.setVelocityX(-ApoMarioConstants.SHELL_VEC_X);
					this.setX(rec.x - this.getWidth());
				}
			} else {
				if (level.getPlayer().getDamageTime() <= 0) {
					level.getPlayer().damagePlayer();
				}
			}
		}
	}
	
	public void think(int delta, ApoMarioLevel level) {
		super.think(delta, level);
		if (!this.isBDie()) {
			this.thinkJump(delta, level);
		}
		
		if (this.getVelocityX() != 0) {
			for (int i = 0; i < level.getEnemies().size(); i++) {
				if (!(level.getEnemies().get(i).equals(this))) {
					this.intersectsWithEnemy(level.getEnemies().get(i), level, this.player);				
				}
			}
			for (int i = 0; i < level.getCannons().size(); i++) {
				ApoMarioCannon cannon = (ApoMarioCannon)level.getLevelEntities()[level.getCannons().get(i).y][level.getCannons().get(i).x];
				if (cannon.getCannons() != null) {
					for (int c = 0; c < cannon.getCannons().size(); c++) {
						this.intersectsWithEnemy(cannon.getCannons().get(c), level, this.player);
					}
				}
			}
		}
	}
	
	private boolean intersectsWithEnemy(ApoMarioEnemy enemy, ApoMarioLevel level, int player) {
		if (!this.isBVisible()) {
			return false;
		}
		Rectangle2D.Float rec = this.getRec();
		Rectangle2D.Float enemyRec = enemy.getRec();
		if (this.equals(enemy)) {
			
		} else if (enemyRec.intersects(rec)) {
			if ((!enemy.isBDie()) &&
				(enemy.isBVisible())) {
				if (enemy.isBFly()) {
					enemy.setBFly(false, player);
				} else {
					enemy.die(level, player);
				}
			}
		}
		return true;
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		super.render(g, changeX, (int)(changeY - this.getChangeY()));
		if (ApoMarioConstants.DEBUG) {
			g.setColor(Color.red);
			Rectangle2D.Float rec = this.getRec();
			g.drawRect((int)(rec.x - changeX), (int)(rec.y - changeY + this.getChangeY()), (int)rec.width, (int)rec.height);
		}
	}

}
