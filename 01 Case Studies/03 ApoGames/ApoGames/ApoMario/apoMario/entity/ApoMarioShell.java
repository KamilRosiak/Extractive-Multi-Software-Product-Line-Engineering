package apoMario.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import apoMario.ApoMarioConstants;
import apoMario.level.ApoMarioLevel;

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
		this.getRectangle().x = this.getX();
		this.getRectangle().y = this.getY();
		this.getRectangle().width = this.getWidth();
		this.getRectangle().height = this.getHeight();
		return this.getRectangle();
	}
	
	/**
	 * gibt das Rechteck für den nächsten Schritt der Entity zurück
	 * @return gibt das Rechteck für den nächsten Schritt der Entity zurück
	 */
	public Rectangle2D.Float getNextRec(int delta) {
		this.nextRec.x = this.getX() + delta * this.getVecX();
		this.nextRec.y = this.getY() + delta * this.getVecY();
		this.nextRec.width = this.getWidth();
		this.nextRec.height = this.getHeight();
		return this.nextRec;
	}
	
	public void thinkJump(int delta, ApoMarioLevel level) {
		for (int player = 0; player < level.getPlayers().size(); player++) {
			if ((level.getPlayers().get(player).isBVisible()) && (level.getPlayers().get(player).getRec().intersects(this.getRec()))) {
				if ((level.getPlayers().get(player).getY() < this.getY()) && (level.getPlayers().get(player).getY() + level.getPlayers().get(player).getHeight() < this.getY() + this.getHeight())) {
					Rectangle2D.Float cut = (Rectangle2D.Float)level.getPlayers().get(player).getRec().createIntersection(this.getRec());
					if ((cut.width + 2 * ApoMarioConstants.SIZE > cut.height) && (level.getPlayers().get(player).getVecY() >= 0)) {
						if (this.getVecX() != 0) {
							this.setVecX(0);
							this.player = player;
						} else {
							Rectangle2D.Float rec = level.getPlayers().get(player).getRec();
							if (rec.x + rec.width/2 < this.getX() + this.getWidth()/2) {
								this.setVecX(ApoMarioConstants.SHELL_VEC_X);	
							} else {
								this.setVecX(-ApoMarioConstants.SHELL_VEC_X);
							}
							this.player = player;
						}
						level.getPlayers().get(player).setY(this.getRec().y - level.getPlayers().get(player).getHeight());
						level.getPlayers().get(player).setEnemyJump();
					} else {
						this.damageCheck(level);
					}
				} else {
					this.damageCheck(level);
				}
			}
		}
	}
	
	private void damageCheck(ApoMarioLevel level) {
		for (int player = 0; player < level.getPlayers().size(); player++) {
			Rectangle2D.Float rec = level.getPlayers().get(player).getRec();
			if (rec.intersects(this.getRec())) {
				if (this.getVecX() == 0) {
					if (rec.x < this.getX()) {
						this.setVecX(ApoMarioConstants.SHELL_VEC_X);
						this.setX(rec.x + rec.width);
					} else {
						this.setVecX(-ApoMarioConstants.SHELL_VEC_X);
						this.setX(rec.x - this.getWidth());
					}
					this.player = player;
				} else {
					if (level.getPlayers().get(player).getDamageTime() <= 0) {
						level.getPlayers().get(player).damagePlayer();
					}
				}
			}
		}
	}
	
	public void think(int delta, ApoMarioLevel level) {
		super.think(delta, level);
		if (!this.isBDie()) {
			this.thinkJump(delta, level);
		}
		
		if (this.getVecX() != 0) {
			for (int i = 0; i < level.getEnemies().size(); i++) {
				if (!(level.getEnemies().get(i).equals(this))) {
					this.intersectsWithEnemy(level.getEnemies().get(i), level, this.player);				
				}
			}
			for (int i = 0; i < level.getCannons().size(); i++) {
				try {
					ApoMarioCannon cannon = (ApoMarioCannon)level.getLevelEntities()[level.getCannons().get(i).y][level.getCannons().get(i).x];
					if ((cannon != null) && (cannon.getCannons() != null)) {
						for (int c = 0; c < cannon.getCannons().size(); c++) {
							this.intersectsWithEnemy(cannon.getCannons().get(c), level, this.player);
						}
					}
				} catch (Exception ex) {
					
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
			g.drawRect((int)(rec.x - changeX) * ApoMarioConstants.APP_SIZE, (int)(rec.y - changeY + this.getChangeY()) * ApoMarioConstants.APP_SIZE, (int)rec.width * ApoMarioConstants.APP_SIZE, (int)rec.height * ApoMarioConstants.APP_SIZE);
		}
	}

}
