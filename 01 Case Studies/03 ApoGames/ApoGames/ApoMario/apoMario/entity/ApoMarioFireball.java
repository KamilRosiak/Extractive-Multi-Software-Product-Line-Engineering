package apoMario.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import apoMario.ApoMarioConstants;
import apoMario.level.ApoMarioLevel;

public class ApoMarioFireball extends ApoMarioEnemy {

	private int player;
	
	public ApoMarioFireball(BufferedImage animation, float x, float y, int tiles, long time, boolean bLeft, int player, int id) {
		super(animation, x, y, animation.getWidth()/tiles / ApoMarioConstants.APP_SIZE, animation.getHeight() / ApoMarioConstants.APP_SIZE, tiles, time, 0, id);
		this.player = player;
		this.setBFall(true);
		if (bLeft) {
			super.setVecX(-ApoMarioConstants.PLAYER_FIREBALL_VEC_X);
		} else {
			super.setVecX(+ApoMarioConstants.PLAYER_FIREBALL_VEC_X);
		}
		this.setVecY(0);
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
	
	public void setVecX(float vecX) {
		if ((vecX != this.getVecX()) && (vecX == -this.getVecX())) {
			vecX = 0;
			this.setBVisible(false);
		} else {
			super.setVecX(vecX);
		}
	}
	
	public synchronized void think(int delta, ApoMarioLevel level) {
		if (!this.isBVisible()) {
			return;
		}
		super.think(delta, level);
		Rectangle2D.Float rec = this.getRec();
		Point p = this.getMinMax(level, ApoMarioConstants.GAME_WIDTH);
		if ((rec.getX() < p.x) ||
			(rec.getX() >= p.y) || (rec.getX() <= 0) || (this.getVecX() == 0)) {
			this.setBVisible(false);
		}
		if (this.checkWall(level)) {
			if (this.isBVisible()) {
				this.setBVisible(false);
			}
		}
		for (int i = 0; i < level.getEnemies().size(); i++) {
			this.intersectsWithEnemy(level.getEnemies().get(i), level, this.player);
		}

		for (int i = 0; i < level.getCannons().size(); i++) {
			try {
				ApoMarioCannon canon = (ApoMarioCannon)level.getLevelEntities()[level.getCannons().get(i).y][level.getCannons().get(i).x];
				if ((canon != null) && (canon.getCannons() != null)) {
					for (int c = 0; c < canon.getCannons().size(); c++) {
						this.intersectsWithEnemy(canon.getCannons().get(c), level, this.player);
					}
				}
			} catch (Exception ex) {}
		}
	}
	
	private boolean checkWall(ApoMarioLevel level) {
		Rectangle2D.Float rec = this.getRec();
		int x = (int)((rec.x + rec.width/2) / ApoMarioConstants.TILE_SIZE);
		int y = (int)((rec.y + rec.height/2) / ApoMarioConstants.TILE_SIZE);
		if (y >= level.getHeight()) {
			return true;
		} else if (y < 0) {
			return true;
		}
		if (x >= level.getWidth()) {
			return true;
		} else if (x < 0) {
			return true;
		}
		if (level.getLevelEntities()[y][x] != null) {
			if (level.getLevelEntities()[y][x] instanceof ApoMarioWall) {
				ApoMarioWall wall = (ApoMarioWall)level.getLevelEntities()[y][x];
				if ((!wall.isBOnlyAboveWall()) && (!wall.isBNoCollision())) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean intersectsWithEnemy(ApoMarioEnemy enemy, ApoMarioLevel level, int player) {
		if (!this.isBVisible()) {
			return false;
		}
		Rectangle2D.Float rec = this.getRec();
		Rectangle2D.Float enemyRec = enemy.getRec();
		if (enemyRec.intersects(rec)) {
			if ((!enemy.isBDie()) &&
				(enemy.isBVisible())) {
				if (enemy.isBImmortal()) {
					
				} else if (enemy.isBFly()) {
					enemy.setBFly(false, player);
				} else {
					enemy.die(level, player);
				}
				this.setBVisible(false);
			}
		}
		return true;
	}
	
	public void nextYCheck(int delta, ApoMarioLevel level, int player) {
		super.nextYCheck(delta, level, ApoMarioConstants.VECY_FIREBALL);
	}
	
	public void downCheck() {
		this.setVecY(-ApoMarioConstants.PLAYER_FIREBALL_VEC_Y);
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
