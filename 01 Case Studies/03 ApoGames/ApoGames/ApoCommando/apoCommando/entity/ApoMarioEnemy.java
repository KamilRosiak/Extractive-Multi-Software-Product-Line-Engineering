package apoCommando.entity;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import apoCommando.ApoMarioConstants;
import apoCommando.level.ApoMarioLevel;

public class ApoMarioEnemy extends ApoMarioEntity {

	private boolean bFall;
	
	private boolean bFly;
	
	private boolean bDie;
	
	private float changeY;
	
	private boolean bImmortal;
	
	private boolean bFlyOriginal;
	
	private int points;
	
	public ApoMarioEnemy(BufferedImage animation, float x, float y, float width, float height, int tiles, long time, int points, int id) {
		super(animation, x, y, width, height, tiles, time, id);
		this.points = points;
		this.bFall = true;
	}
	
	public void init() {
		super.init();
		this.bDie = false;
		this.changeY = 0;
		this.bFly = this.bFlyOriginal;
	}
	
	public float getChangeY() {
		return this.changeY;
	}

	public void setChangeY(float changeY) {
		this.changeY = changeY;
	}

	public boolean isBImmortal() {
		return this.bImmortal;
	}

	public void setBImmortal(boolean bImmortal) {
		if (bImmortal) {
			this.setPoints(ApoMarioConstants.POINTS_ENEMY_UNDESTRUCABLE);
		}
		this.bImmortal = bImmortal;
	}

	public boolean isBDie() {
		return this.bDie;
	}

	public void setBDie(boolean bDie) {
		this.bDie = bDie;
	}

	public boolean isBFall() {
		return this.bFall;
	}

	public void setBFall(boolean bFall) {
		this.bFall = bFall;
	}

	public boolean isBFlyOriginal() {
		return this.bFlyOriginal;
	}

	public void setBFlyOriginal(boolean bFlyOriginal) {
		this.bFlyOriginal = bFlyOriginal;
	}

	public int getPoints() {
		return this.points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public boolean isBFly() {
		return this.bFly;
	}

	public void setBFly(boolean bFly) {
		this.bFly = bFly;
	}
	
	public void setBFly(boolean bFly, int player) {
		if ((this.bFly) && (!bFly)) {
			if (this.getLevel().getPlayer() != null) {
				this.getLevel().getPlayer().setPoints(this.getLevel().getPlayer().getPoints() + this.points);
			}
		}
		this.bFly = bFly;
	}
	
	protected BufferedImage getMirrorImage(BufferedImage iOriginal) {
		if (iOriginal == null) {
			return null;
		} else {
			BufferedImage iMirror = new BufferedImage(iOriginal.getWidth(), iOriginal.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D)iMirror.getGraphics();
			
			int width = iOriginal.getWidth();
			int height = iOriginal.getHeight();
			g.drawImage(iOriginal, 0, 0, width, height, width, 0, 0, height, null);
			
			g.dispose();
			return iMirror;
		}
	}
	
	/**
	 * gibt das aktuelle Rechteck der Entity zurück
	 * @return gibt das aktuelle Rechteck der Entity zurück
	 */
	public Rectangle2D.Float getRec() {
		return new Rectangle2D.Float(this.getX(), this.getY() + 4 * ApoMarioConstants.SIZE, this.getWidth(), this.getHeight() - 4 * ApoMarioConstants.SIZE);
	}
	
	/**
	 * gibt das Rechteck für den nächsten Schritt der Entity zurück
	 * @return gibt das Rechteck für den nächsten Schritt der Entity zurück
	 */
	public Rectangle2D.Float getNextRec(int delta) {
		return new Rectangle2D.Float(this.getX() + delta * this.getVelocityX(), this.getY() + delta * this.getVelocityY() + 4 * ApoMarioConstants.SIZE, this.getWidth(), this.getHeight() - 4 * ApoMarioConstants.SIZE);
	}
	
	/**
	 * die Nachdenkmethode der Gegner
	 * @param delta : Zeitdifferenz seit dem letzten Aufruf
	 * @param level : das Level-Objekt
	 */
	public void think(int delta, ApoMarioLevel level) {
		if (!this.isBVisible()) {
			return;
		} else {
			// falls Gegner im korrekten Fenster zu sehen, ansonsten mache es unsichtbar
			Point p = this.getMinMax(level, ApoMarioConstants.GAME_WIDTH);
			if ((this.getX() + 3 * ApoMarioConstants.TILE_SIZE > p.x) &&
				(this.getX() - 3 * ApoMarioConstants.TILE_SIZE < p.y)) {
				
			} else {
				this.setBVisible(false);
				return;
			}
		}
		if (this.isBDie()) {
			if (this.getY() > level.getHeight() * ApoMarioConstants.TILE_SIZE) {
				this.setBVisible(false);
			} else {
				this.setVelocityY(this.getVelocityY() + delta * ApoMarioConstants.ENEMY_VEC_DECREASE_Y_DIE);
				this.setChangeY(this.getChangeY() + delta * this.getVelocityY());
			}
			return;
		}
		super.think(delta, level);
		this.thinkSpeedAndMoveEnemy(delta, level);
		this.thinkCollision(delta, level);
	}
	
	/**
	 * Methode zum Töten der Entity
	 * @param level : das Level-Objekt
	 */
	public void die(ApoMarioLevel level, int player) {
		if (player >= 0) {
			level.getPlayer().setPoints(level.getPlayer().getPoints() + this.points);
		}
		this.setBDie(true);
		this.setVelocityY(-ApoMarioConstants.ENEMY_VEC_Y_DIE);
	}
	
	/**
	 * wird aufgerufen um zu überprüfen, ob der Gegner mit dem Spieler kollidiert
	 * dabei wird unterschieden wo der Spieler ist (springt er auf den Kopf oder kollidiert er seitwärts)
	 * @param delta : Zeit seit dem letzten Aufruf
	 * @param level : das Level-Objekt
	 */
	public void thinkJump(int delta, ApoMarioLevel level) {
		if (level.getPlayer().isBDie()) {
			return;
		}
		Rectangle2D.Float playerRec = level.getPlayer().getRec();
		Rectangle2D.Float myRec = this.getRec();
		if ((playerRec.intersects(myRec))) {
			if ((playerRec.getY() < myRec.getY()) && (playerRec.getY() + playerRec.getHeight() < myRec.getY() + myRec.getHeight())) {
				//Rectangle2D.Float cut = (Rectangle2D.Float)level.getPlayer().getRec().createIntersection(this.getRec());
				if (playerRec.getY() + playerRec.getHeight() < myRec.getY() + myRec.getHeight()/2) {
				//if ((cut.width + 2 * ApoMarioConstants.SIZE > cut.height) && (level.getPlayer().getVelocityY() >= 0)) {
					if (this.isBImmortal()) {
						if (level.getPlayer().getDamageTime() <= 0) {
							level.getPlayer().damagePlayer();
						}
						return;
					}
					super.makeParticles((float)(playerRec.getX() + playerRec.getWidth()/2), (float)(playerRec.getY() + playerRec.getHeight() - 4 * ApoMarioConstants.SIZE), (float)(playerRec.getWidth()),  (float)(8 * ApoMarioConstants.SIZE));
					if (this.isBFly()) {
						this.setBFly(false, 0);
						this.setVelocityY(0);
					} else {
						this.die(level, 0);
					}
					level.getPlayer().setEnemyJump();
				} else {
					if (level.getPlayer().getDamageTime() <= 0) {
						level.getPlayer().damagePlayer();
					}
				}
			} else {
				if (level.getPlayer().getDamageTime() <= 0) {
					level.getPlayer().damagePlayer();
				}
			}
		}
	}

	/**
	 * bewegt den Gegner
	 * @param delta : Zeit seit dem letzten Aufruf
	 */
	public void thinkSpeedAndMoveEnemy(int delta, ApoMarioLevel level) {
		this.nextYCheck(delta, level, ApoMarioConstants.VECY_ENEMY);
		if (this.getVelocityY() != 0) {
			if (this.isBFly()) {
				this.setVelocityY(this.getVelocityY() + ApoMarioConstants.ENEMY_VEC_DECREASE_Y_FLY * delta);
			} else {
				this.setVelocityY(this.getVelocityY() + ApoMarioConstants.ENEMY_VEC_DECREASE_Y * delta);
			}
		}
		this.setX(this.getX() + delta * this.getVelocityX());
		this.setY(this.getY() + delta * this.getVelocityY());
		if (this.getY() > level.getHeight() * ApoMarioConstants.TILE_SIZE) {
			this.die(level, -1);
		}
		if (this.getX() + this.getWidth() < 0 * ApoMarioConstants.TILE_SIZE) {
			this.die(level, -1);
		} else if (this.getX() >= level.getWidth() * ApoMarioConstants.TILE_SIZE) {
			this.die(level, -1);
		}
	}
	
	@Override
	public void yDownCheck(ApoMarioLevel level, int x, int y, int delta) {
		if (this instanceof ApoMarioFireball) {
			//level.setBStop(true);
		}
		this.downCheck();
	}
	
	public void nextYCheck(int delta, ApoMarioLevel level, int player) {
		super.nextYCheck(delta, level, player);
		// Überprüfung wenn der Gegner am Rand umdrehen soll, dann soll er es auch machen =)
		if ((!this.isBFall()) && (!this.isBFly())) {
			Rectangle2D.Float rec = this.getRec();
			Rectangle2D.Float nextRec = this.getNextRec(delta);
			int startX = (int)(nextRec.x / ApoMarioConstants.TILE_SIZE);
			int startY = (int)((nextRec.y + nextRec.height - 1) / ApoMarioConstants.TILE_SIZE);
			if (startX < 0) {
				startX = 0;
			}
			if (startY < 0) {
				startY = 0;
			}
			int nextStartX = startX;
			int curStartX = (int)(rec.getX() / ApoMarioConstants.TILE_SIZE);
			if (this.getVelocityX() > 0) {
				nextStartX = (int)((nextRec.x + nextRec.width) / ApoMarioConstants.TILE_SIZE);
				curStartX = (int)((rec.x + rec.width) / ApoMarioConstants.TILE_SIZE);
			}
			if (curStartX < 0) {
				this.die(level, -1);
				return;
			}
			if (nextStartX >= level.getWidth()) {
				this.die(level, -1);
				return;
			}
			if (curStartX != nextStartX) {
				if ((startY + 1 < level.getHeight()) && (nextStartX < level.getWidth())) {
					if (level.getLevelEntities()[startY+1][curStartX] != null) {
						if ((level.getLevelEntities()[startY+1][nextStartX] == null) || (!level.getLevelEntities()[startY+1][nextStartX].isBVisible())) {
							this.setVelocityX(-this.getVelocityX());
						} else if (level.getLevelEntities()[startY+1][nextStartX] instanceof ApoMarioDestructableWall) {
							ApoMarioDestructableWall wall = (ApoMarioDestructableWall)(level.getLevelEntities()[startY+1][nextStartX]);
							if (wall.getParticle().size() > 0) {
								this.setVelocityX(-this.getVelocityX());							
							}
						}
					}
				}
			}
		}
	}
	
	public void downCheck() {
		if (this.isBFly()) {
			this.setVelocityY(-ApoMarioConstants.ENEMY_VEC_Y);
		} else {
			this.setVelocityY(0);
		}
	}
	
	@Override
	public void coinCheck(ApoMarioLevel level, int x, int y) {
	}

	@Override
	public void xCheckChange(ApoMarioLevel level, int x, int y) {
		this.setVelocityX(-this.getVelocityX());
	}

	@Override
	public void yCheckUp(ApoMarioLevel level, int x, int y) {
		
	}
	
	/**
	 * rendert die Entity, falls er sichtbar ist hin
	 */
	public void render(Graphics2D g, int changeX, int changeY) {
		if (this.isBDie()) {
			BufferedImage dieImage = super.getIBackground().getSubimage((int)(0 * this.getWidth()), (int)(this.getDirection() * this.getHeight()), (int)(this.getWidth()), (int)(this.getHeight()));
			int w = dieImage.getWidth();
			int h = dieImage.getHeight();
			int x = (int)(this.getX() - changeX);
			int y = (int)(this.getY() - changeY);
			g.drawImage(dieImage, x, y, x + w, y + h, 0, h, w, 0, null);
		} else {
			super.render(g, changeX, changeY);
		}
	}
}