package apoCommando.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import apoCommando.ApoMarioConstants;
import apoCommando.ApoMarioImageContainer;
import apoCommando.game.ApoMarioPanel;
import apoCommando.level.ApoMarioAchievements;
import apoCommando.level.ApoMarioLevel;

/**
 * Klasse, die den Spieler repräsentiert
 * @author Dirk Aporius
 */
public class ApoMarioPlayer extends ApoMarioEntity {
	
	private boolean bGodMode;
	
	private final int MAX_CHANGE_TYPE = 100;
	
	private boolean bLastTypeDamage;
	
	private boolean bDown;
	
	private boolean bJump;
	
	private boolean bEnemyJump;
	
	private boolean bSpeed;
	
	private int lastType;
	
	private int type;
	
	private float goalVecX;
	
	private int damageTime;
	
	private int coins;
	
	private int points;
	
	private int changeType;
	
	private int curFireTime;
	
	private ArrayList<ApoMarioFireball> fireballs;
	
	private boolean bDie;
	
	private float changeY;
	
	private int player;
	
	private ArrayList<String> messages;
	
	private String teamName, author;
	
	private float startDuck;
	
	private boolean bShouldStandUp;
	
	public ApoMarioPlayer(float x, float y, float width, float height, int tiles, long time, int player, int id) {
		super(null, x, y, width, height, tiles, time, id);
		this.player = player;
	}
	
	public void init() {
		super.init();
		
		this.bDown = false;
		this.bShouldStandUp = false;
		this.setBJump(false, false);
		this.setGoalVecX(0);
		if (ApoMarioConstants.DIFFICULTY <= 3) {
			this.setType(ApoMarioConstants.PLAYER_TYPE_FIRE);
		} else {
			this.setType(ApoMarioConstants.PLAYER_TYPE_BIG);			
		}
		this.lastType = this.getType();
		this.setDamageTime(0);
		this.setCoins(0);
		this.setPoints(0);
		this.bLastTypeDamage = false;
		this.bSpeed = false;
		this.changeType = this.getType();
		this.fireballs = new ArrayList<ApoMarioFireball>();
		this.curFireTime = 0;
		this.bDie = false;
		this.changeY = 0;
		this.messages = new ArrayList<String>();
		this.startDuck = -1;
		this.bGodMode = false;
		if (this.getDirection() == ApoMarioEntity.LEFT) {
			this.changeDirection();
		}
	}
	
	public String getTeamName() {
		return this.teamName;
	}
	
	public String getAuthor() {
		return this.author;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void addMessage(String message) {
		this.messages.add(message);
	}
	
	/**
	 * gibt zurück, ob der Spieler gerade stirbt oder nicht =)
	 * @return
	 */
	public boolean isBDie() {
		return this.bDie;
	}

	public float getChangeY() {
		return this.changeY;
	}

	/**
	 * gibt die Anzahl der Münzen zurück, die der Spieler gesammelt hat
	 * @return gibt die Anzahl der Münzen zurück, die der Spieler gesammelt hat
	 */
	public int getCoins() {
		return this.coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	/**
	 * gibt die Punkte des Spielers zurück
	 * @return gibt die Punkte des Spielers zurück
	 */
	public int getPoints() {
		return this.points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getAi() {
		return null;
	}

	public void setAi(String ai) {
	}

	public int getDamageTime() {
		return this.damageTime;
	}

	public void setDamageTime(int damageTime) {
		if (this.bGodMode) {
			return;
		}
		this.damageTime = damageTime;
		if (this.damageTime < 0) {
			this.damageTime = 0;
		} else if (this.damageTime > ApoMarioConstants.PLAYER_DAMAGE_TIME) {
			this.damageTime = ApoMarioConstants.PLAYER_DAMAGE_TIME;
		}
	}

	/**
	 * wird aufgerufen wenn der Spieler einen Schaden erhält (z.B. gegen Gegner laufen)
	 */
	public void damagePlayer() {
		if (this.bGodMode) {
			return;
		}
		if (this.damageTime <= 0) {
			this.lastType = this.getType();
			this.setType(this.getType() - 1);
			this.setDamageTime(ApoMarioConstants.PLAYER_DAMAGE_TIME);
		}
	}
	
	/**
	 * wird aufgerufen wenn ein Pilz gefangen wurde
	 */
	public void catchMushroom() {
		if (this.getType() < ApoMarioConstants.PLAYER_TYPE_BIG) {
			this.lastType = this.getType();
			this.setType(ApoMarioConstants.PLAYER_TYPE_BIG);
		}
	}
	
	/**
	 * wird aufgerufen wenn eine Feuerpflanze aufgerufen wird
	 */
	public void catchFireflower() {
		if (this.getType() < ApoMarioConstants.PLAYER_TYPE_FIRE) {
			this.lastType = this.getType();
			this.setType(ApoMarioConstants.PLAYER_TYPE_FIRE);
		}
	}
	
	public int getType() {
		return this.type;
	}

	/**
	 * Verändert den Typen des Spielers von Klein zu Groß zu Feuer bzw andersrum
	 * @param type
	 */
	public void setType(int type) {
		this.type = type;
		if (this.getType() < ApoMarioConstants.PLAYER_TYPE_LITTLE) {
			this.playerDie();
			this.type = ApoMarioConstants.PLAYER_TYPE_LITTLE;
		}
	}

	/**
	 * wird aufgerufen wenn der Spieler stirbt
	 */
	public void playerDie() {
		if (!this.bDie) {
			this.bDie = true;
			this.changeY = 0;
			this.setVelocityY(-ApoMarioConstants.ENEMY_VEC_Y_DIE);
		}
	}
	
	public boolean isBGodMode() {
		return this.bGodMode;
	}

	public void setBGodMode(boolean bGodMode) {
		this.bGodMode = bGodMode;
	}

	public float getGoalVecX() {
		return this.goalVecX;
	}

	public void setGoalVecX(float goalVecX) {
		/*if (this.isBDown()) {
			goalVecX = 0;
		}*/
		if ((goalVecX != this.getGoalVecX()) && (goalVecX != 0) && (this.getVelocityY() == 0)) {
			if (goalVecX < this.getVelocityX()) {
				if (this.getVelocityX() > 0) {
					Rectangle2D.Float rec = this.getRec();
					this.makeParticles((float)(rec.getX() + rec.getWidth()/2), (float)(rec.getY() + rec.getHeight() - 4 * ApoMarioConstants.SIZE), (float)(rec.getWidth()),  (float)(8 * ApoMarioConstants.SIZE));
				}
			} else if (goalVecX > this.getVelocityX()) {
				if (this.getVelocityX() < 0) {
					Rectangle2D.Float rec = this.getRec();
					this.makeParticles((float)(rec.getX() + rec.getWidth()/2), (float)(rec.getY() + rec.getHeight() - 4 * ApoMarioConstants.SIZE), (float)(rec.getWidth()),  (float)(8 * ApoMarioConstants.SIZE));				
				}
			}
		}

		this.goalVecX = goalVecX;
		if (this.isBSpeed()) {
			if (this.goalVecX < -ApoMarioConstants.PLAYER_MAX_VEC_X_FAST - ApoMarioConstants.DIFFICULTY * ApoMarioConstants.PLAYER_MAX_VEC_X_DIF_FAST) {
				this.setGoalVecX(-ApoMarioConstants.PLAYER_MAX_VEC_X_FAST - ApoMarioConstants.DIFFICULTY * ApoMarioConstants.PLAYER_MAX_VEC_X_DIF_FAST);
			} else if (this.goalVecX > ApoMarioConstants.PLAYER_MAX_VEC_X_FAST + ApoMarioConstants.DIFFICULTY * ApoMarioConstants.PLAYER_MAX_VEC_X_DIF_FAST) {
				this.setGoalVecX(ApoMarioConstants.PLAYER_MAX_VEC_X_FAST + ApoMarioConstants.DIFFICULTY * ApoMarioConstants.PLAYER_MAX_VEC_X_DIF_FAST);
			}
		} else {
			if (this.goalVecX < -ApoMarioConstants.PLAYER_MAX_VEC_X_NORMAL - ApoMarioConstants.DIFFICULTY * ApoMarioConstants.PLAYER_MAX_VEC_X_DIF_NORMAL) {
				this.setGoalVecX(-ApoMarioConstants.PLAYER_MAX_VEC_X_NORMAL - ApoMarioConstants.DIFFICULTY * ApoMarioConstants.PLAYER_MAX_VEC_X_DIF_NORMAL);
			} else if (this.goalVecX > ApoMarioConstants.PLAYER_MAX_VEC_X_NORMAL + ApoMarioConstants.DIFFICULTY * ApoMarioConstants.PLAYER_MAX_VEC_X_DIF_NORMAL) {
				this.setGoalVecX(ApoMarioConstants.PLAYER_MAX_VEC_X_NORMAL + ApoMarioConstants.DIFFICULTY * ApoMarioConstants.PLAYER_MAX_VEC_X_DIF_NORMAL);
			}
		}
		if (goalVecX < 0) {
			this.setDirection(ApoMarioEntity.LEFT);
		} else if (goalVecX > 0) {
			this.setDirection(ApoMarioEntity.RIGHT);
		}
	}
	
	public int getLevelX() {
		return (int)((this.getX() + this.getWidth()/2) / ApoMarioConstants.TILE_SIZE);
	}
	
	public int getLevelY() {
		return (int)((this.getY() + this.getHeight()/2) / ApoMarioConstants.TILE_SIZE);
	}

	public boolean isBSpeed() {
		return this.bSpeed;
	}

	public int getPlayer() {
		return this.player;
	}
	
	public void setBSpeed(boolean bSpeed) {
		this.bSpeed = bSpeed;
	}
	
	public void addFireBall() {
		if (this.getType() == ApoMarioConstants.PLAYER_TYPE_FIRE) {
			if ((this.curFireTime <= 0) && (this.fireballs.size() < 2)) {
				Rectangle2D.Float rec = this.getRec();
				if (this.getDirection() == ApoMarioEntity.RIGHT) {
					this.fireballs.add(new ApoMarioFireball(ApoMarioImageContainer.PARTICLE.getSubimage(0, 3 * ApoMarioConstants.TILE_SIZE/2, 4 * ApoMarioConstants.TILE_SIZE/2, ApoMarioConstants.TILE_SIZE/2), (int)(rec.getX() + rec.getWidth()), (int)(rec.getY() + rec.getHeight()/2), 4, ApoMarioConstants.PLAYER_ANIMATION_TIME, false, this.player, ++ApoMarioLevel.ID));					
				} else {
					this.fireballs.add(new ApoMarioFireball(ApoMarioImageContainer.PARTICLE.getSubimage(0, 3 * ApoMarioConstants.TILE_SIZE/2, 4 * ApoMarioConstants.TILE_SIZE/2, ApoMarioConstants.TILE_SIZE/2), (int)(rec.getX()), (int)(rec.getY() + rec.getHeight()/2), 4, ApoMarioConstants.PLAYER_ANIMATION_TIME, true, this.player, ++ApoMarioLevel.ID));	
				}
				this.curFireTime = ApoMarioConstants.PLAYER_FIREBALL_TIME;
			}
		}
	}

	public boolean isBDown() {
		return this.bDown;
	}

	public void setBDown(boolean bDown) {
		if (this.bDie) {
			this.bDown = false;
		}
		if (bDown) {
			this.startDuck = 0.01f;
		} else {
			if (this.bDown) {
				this.bShouldStandUp = true;
				return;
			}
		}
		this.bDown = bDown;
	}

	private boolean canStandUp(ApoMarioLevel level) {
		Rectangle2D.Float rec = this.getRec();
		int x = (int)(rec.getX() / ApoMarioConstants.TILE_SIZE);
		int y = (int)(rec.getY() / ApoMarioConstants.TILE_SIZE) - 1;
		if ((x >= 0) && (y >= 0)) {
			if ((level.getLevelEntities()[y][x] != null) &&
				(level.getLevelEntities()[y][x].isBVisible()) &&
				(level.getLevelEntities()[y][x] instanceof ApoMarioWall) &&
				(!((ApoMarioWall)(level.getLevelEntities()[y][x])).isBNoCollision())) {
				return false;
			}
		}
		x = (int)((rec.getX() + rec.getWidth()) / ApoMarioConstants.TILE_SIZE);
		if ((x >= 0) && (y >= 0)) {
			if ((level.getLevelEntities()[y][x] != null) &&
				(level.getLevelEntities()[y][x].isBVisible()) &&
				(level.getLevelEntities()[y][x] instanceof ApoMarioWall) &&
				(!((ApoMarioWall)(level.getLevelEntities()[y][x])).isBNoCollision())) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isBJump() {
		return this.bJump;
	}
	
	public void setBJump(boolean bJump, boolean bHigh) {
		if (this.bDie) {
			return;
		}
		if (this.bEnemyJump) {
			return;
		}
		if (this.bJump == bJump) {
			return;
		}
		if (this.getVelocityY() > 0) {
			return;
		}
		this.setBDown(false);
		this.bJump = bJump;
		if (bJump) {
			if (!bHigh) {
				this.setVelocityY(-ApoMarioConstants.PLAYER_VEC_Y);
			} else {
				this.setVelocityY(-ApoMarioConstants.PLAYER_VEC_HIGH_Y);
			}
		} else {
			if (this.getVelocityY() < 0) {
				this.setVelocityY(0);
			}
		}
	}
	
	public boolean isBEnemyJump() {
		return this.bEnemyJump;
	}

	/**
	 * Methode die aufgerufen wird, wenn der Spieler auf einen Gegner springt
	 */
	public void setEnemyJump() {
		this.setVelocityY(-ApoMarioConstants.PLAYER_VEC_Y_ENEMY);
		this.bEnemyJump = true;
	}
	
	public ArrayList<ApoMarioFireball> getFireballs() {
		return this.fireballs;
	}

	/**
	 * gibt das aktuelle Rechteck der Entity zurück
	 * @return gibt das aktuelle Rechteck der Entity zurück
	 */
	public Rectangle2D.Float getRec() {
		if ((this.getType() == ApoMarioConstants.PLAYER_TYPE_LITTLE) || (this.isBDown())) {
			return new Rectangle2D.Float(this.getX() + 1f/4f * (float)ApoMarioConstants.TILE_SIZE + 7f * ApoMarioConstants.SIZE, this.getY() + ApoMarioConstants.TILE_SIZE, this.getWidth() - 2*1f/4f * ApoMarioConstants.TILE_SIZE - 2 * 7f * ApoMarioConstants.SIZE, this.getHeight() - ApoMarioConstants.TILE_SIZE);			
		} else {
			return new Rectangle2D.Float(this.getX() + 1f/4f * (float)ApoMarioConstants.TILE_SIZE + 7f * ApoMarioConstants.SIZE, this.getY() + 4 * ApoMarioConstants.SIZE, this.getWidth() - 2*1f/4f * ApoMarioConstants.TILE_SIZE - 2 * 7f * ApoMarioConstants.SIZE, this.getHeight() - 4 * ApoMarioConstants.SIZE);
		}
	}
	
	/**
	 * gibt das Rechteck für den nächsten Schritt der Entity zurück
	 * @return gibt das Rechteck für den nächsten Schritt der Entity zurück
	 */
	public Rectangle2D.Float getNextRec(int delta) {
		Rectangle2D.Float rec = this.getRec();
		return new Rectangle2D.Float((float)rec.getX() + delta * this.getVelocityX(), (float)rec.getY() + delta * this.getVelocityY(), (float)rec.getWidth(), (float)rec.getHeight());
	}
	
	public void thinkAI(int delta, ApoMarioLevel level) {
		if ((this.isBDie()) || (!this.isBVisible())) {
			return;
		}
	}
	
	public void think(int delta, ApoMarioLevel level) {
		if (this.isBDie()) {
			if (this.getY() + this.changeY >= level.getHeight() * ApoMarioConstants.TILE_SIZE) {
				this.setBVisible(false);
				level.setAnalysis(false);
			} else {
				this.setVelocityY(this.getVelocityY() + delta * ApoMarioConstants.ENEMY_VEC_DECREASE_Y_DIE);
				this.changeY = this.changeY + delta * this.getVelocityY();
			}
			return;
		}
		if (this.bShouldStandUp) {
			if (this.canStandUp(level)) {
				this.bShouldStandUp = false;
				this.bDown = false;
			}
		}
		super.think(delta, level);
		
		if (this.isBDown()) {
			if (Math.abs(this.startDuck) > 5 * ApoMarioConstants.TILE_SIZE) {
				this.setBDown(false);
			}
		}
		if (this.curFireTime > 0) {
			this.curFireTime -= delta;
		}
		if (this.getRec().intersects(level.getFinish().getRec())) {
			if (level.getFinish().hitTheBar(level)) {
				if (level.getComponent() instanceof ApoMarioPanel) {
					ApoMarioPanel panel = (ApoMarioPanel)level.getComponent();
					panel.getAchievements().addAchievement(ApoMarioAchievements.ACHIEVEMENT_BAR);
					this.setPoints(this.getPoints() + ApoMarioConstants.POINT_BONUS_BAR);
				}
			}
			level.setAnalysis(true);
			return;
		}
		this.thinkSpeedAndMovePlayer(delta, level);
		this.thinkCollision(delta, level);
		if (this.damageTime > 0) {
			this.damageTime -= delta;
			if (damageTime <= 0) {
				this.damageTime = 0;
				this.bLastTypeDamage = false;
				this.changeType = 0;
			} else {
				this.changeType += delta;
				if (this.changeType >= this.MAX_CHANGE_TYPE) {
					this.bLastTypeDamage = !this.bLastTypeDamage;
					this.changeType = 0;
				}
			}
		}
		int size = this.fireballs.size() - 1;
		for (int i = size; i >= 0; i--) {
			this.fireballs.get(i).think(delta, level);
			if (!this.fireballs.get(i).isBVisible()) {
				super.makeParticles(this.fireballs.get(i).getX(), this.fireballs.get(i).getY(), this.fireballs.get(i).getWidth(), this.fireballs.get(i).getHeight());
				if (this.fireballs.size() > i) {
					this.fireballs.remove(i);
				}
			}
		}
	}
	
	/**
	 * bewegt den Spieler
	 * @param delta : Zeit seit dem letzten Aufruf
	 */
	private void thinkSpeedAndMovePlayer(int delta, ApoMarioLevel level) {
		// Beschleunigung des Spielers auf die gewünschte Geschwindigkeit
		if (this.getGoalVecX() != this.getVelocityX()) {
			if (this.getGoalVecX() < this.getVelocityX()) {
				if (this.getVelocityX() > 0) {
					this.setVelocityX(this.getVelocityX() - delta * ApoMarioConstants.PLAYER_DECREASE_VEC_X);					
				} else {
					this.setVelocityX(this.getVelocityX() - delta * ApoMarioConstants.PLAYER_INCREASE_VEC_X);
				}
				if (this.getGoalVecX() >= this.getVelocityX()) {
					this.setVelocityX(this.getGoalVecX());
				}
			} else if (this.getGoalVecX() > this.getVelocityX()) {
				if (this.getVelocityX() < 0) {
					this.setVelocityX(this.getVelocityX() + delta * ApoMarioConstants.PLAYER_DECREASE_VEC_X);					
				} else {
					this.setVelocityX(this.getVelocityX() + delta * ApoMarioConstants.PLAYER_INCREASE_VEC_X);
				}
				if (this.getGoalVecX() <= this.getVelocityX()) {
					this.setVelocityX(this.getGoalVecX());
				}
			}
		}
		
		// Überprüfung Kollision nach unten
		this.nextYCheck(delta, level, ApoMarioConstants.VECY_PLAYER);
		if (this.getVelocityY() != 0) {
			if (this.getVelocityY() < 0) {
				this.setVelocityY(this.getVelocityY() + ApoMarioConstants.PLAYER_VEC_DECREASE_Y * delta);
			} else {
				this.setVelocityY(this.getVelocityY() + ApoMarioConstants.PLAYER_VEC_DECREASE_FALL_Y * delta);
			}
		}
		if (this.startDuck > 0) {
			this.startDuck += Math.abs(delta * this.getVelocityX());
		}
		this.setX(this.getX() + delta * this.getVelocityX());
		if (this.getRec().getX() < 0) {
			this.setX((float)(0 + this.getX() - this.getRec().getX()));
		}
		this.setY(this.getY() + delta * this.getVelocityY());
	}
	
	public boolean yDownCheckEntity(ApoMarioLevel level, int x, int y, int delta) {
		Rectangle2D.Float nextRec = this.getNextRec(delta);
		Rectangle2D.Float myNextRec = new Rectangle2D.Float(nextRec.x, nextRec.y + nextRec.height, nextRec.width, 2 * ApoMarioConstants.SIZE);
		float oldVecY = this.getVelocityY();
		float oldY = this.getY();
		
		if (!level.getLevelEntities()[y][x].intersects((float)(myNextRec.getX() + myNextRec.getWidth()/2), (float)(myNextRec.getY() + myNextRec.getHeight()))) {
			if (!this.test(level, x, y)) {
				return false;
			}
		}
		
		float newY = y * ApoMarioConstants.TILE_SIZE - this.getHeight();
		this.setVelocityY(0);
		this.setY(newY);
		if (!testNewY(level)) {
			this.setY(oldY);
			this.setVelocityY(oldVecY);
			return false;
		}
		if (Math.abs(newY - oldY) > 4 * ApoMarioConstants.SIZE) {
			this.setY(oldY);
			this.setVelocityY(oldVecY);
			return false;
		}
		
		yDownCheck(level, x, y, delta);
		return true;
	}
	
	private boolean test(ApoMarioLevel level, int x, int y) {
		int startChangeY = 2;
		if (this.getType() == ApoMarioConstants.PLAYER_TYPE_LITTLE) {
			startChangeY = 1;
		}
		while (y - startChangeY < 0) {
			startChangeY -= 1;
		}
		int startChangeX = 0;
		while (x - startChangeX < 0) {
			startChangeX -= 0;
		}
		for (int myY = y - startChangeY; myY <= y; myY += 1) {
			if (this.getDirection() == ApoMarioEntity.LEFT) {
				for (int myX = x - startChangeX; myX <= x; myX += 1) {
					if ((level.getLevelEntities()[myY][myX] != null) && (level.getLevelEntities()[myY][myX] instanceof ApoMarioWall)) {
						ApoMarioWall wall = (ApoMarioWall)level.getLevelEntities()[myY][myX];
						if ((!wall.isBNoCollision()) && (wall.isBVisible()) && (!wall.isBOnlyAboveWall())) {
							if ((x != myX) || (y != myY)) {
								return false;
							}
						}
					}
				}
			} else {
				for (int myX = x; myX <= x + startChangeX; myX += 1) {
					if ((level.getLevelEntities()[myY][myX] != null) && (level.getLevelEntities()[myY][myX] instanceof ApoMarioWall)) {
						ApoMarioWall wall = (ApoMarioWall)level.getLevelEntities()[myY][myX];
						if ((!wall.isBNoCollision()) && (wall.isBVisible()) && (!wall.isBOnlyAboveWall())) {
							if ((x != myX) || (y != myY)) {
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}
	
	private boolean testNewY(ApoMarioLevel level) {
		Rectangle2D.Float rec = this.getRec();
		int startX = (int)((rec.x) / ApoMarioConstants.TILE_SIZE);
		int startY = (int)((rec.y) / ApoMarioConstants.TILE_SIZE) - 1;
		if (startY < 0) {
			startY = 0;
		} else if (startY >= level.getLevelEntities().length) {
			return true;
		}
		if (startX < 0) {
			startX = 0;
		} else if (startX >= level.getLevelEntities()[0].length) {
			return true;
		}
		for (int x = startX; x < startX + 2; x++) {
			for (int y = startY; y < startY + 3; y++) {
				if ((level.getLevelEntities()[y][x] != null) && (level.getLevelEntities()[y][x] instanceof ApoMarioWall)) {
					ApoMarioWall wall = (ApoMarioWall)level.getLevelEntities()[y][x];
					if ((wall.isBVisible()) && (!wall.isBNoCollision()) && (!wall.isBOnlyAboveWall())) {
						if (rec.intersects(wall.getRec())) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public void yDownCheck(ApoMarioLevel level, int x, int y, int delta) {
		this.bEnemyJump = false;
		this.setBJump(false, false);
	}
	
	@Override
	public void coinCheck(ApoMarioLevel level, int x, int y) {
		level.getLevelEntities()[y][x].setBVisible(false);
		this.setPoints(this.getPoints() + ApoMarioConstants.POINTS_COIN);
		this.setCoins(this.getCoins() + 1);
		if (level.isBDark()) {
			level.setDarkTime(100);
		}
	}

	@Override
	public void yCheckUp(ApoMarioLevel level, int x, int y) {
		this.bEnemyJump = false;
		((ApoMarioWall)(level.getLevelEntities()[y][x])).damageWall(level, this.player);
	}
	
	@Override
	public void xCheckChange(ApoMarioLevel level, int x, int y) {
		if (this.getVelocityY() == 0) {
			
		} else {
			
		}
		this.setVelocityX(0);
		if (this.isBDown()) {
			this.setBDown(false);
		}
		return;
	}
	
	/**
	 * rendert den Spieler, falls er sichtbar ist hin
	 */
	public void render(Graphics2D g, int changeX, int changeY) {
		int type = this.getType();
		if (this.bLastTypeDamage) {
			type = this.lastType;
		}
		BufferedImage iPlayer = ApoMarioImageContainer.PLAYER[type];
		if (super.isBVisible()) {
			if (this.isBDie()) {
				int width = iPlayer.getWidth()/this.getTiles();
				int height = iPlayer.getHeight()/6;
				BufferedImage dieImage = iPlayer.getSubimage((int)(0 * width), (int)(this.getDirection() * height), (int)(width), (int)(height));
				int w = dieImage.getWidth();
				int h = dieImage.getHeight();
				int x = (int)(this.getX() - changeX + (this.getWidth() - width)/2);
				int y = (int)(this.getY() - changeY + this.changeY + this.getHeight() - height);
				g.drawImage(dieImage, x, y, x + w, y + h, 0, h, w, 0, null);
				return;
			}
			if ((type == ApoMarioConstants.PLAYER_TYPE_LITTLE) || (this.isBDown())) {
				int width = iPlayer.getWidth()/this.getTiles();
				int height = iPlayer.getHeight()/6;
				if (((this.getGoalVecX() < 0) && (this.getVelocityX() > 0)) ||
					((this.getGoalVecX() > 0) && (this.getVelocityX() < 0))) {
					g.drawImage(iPlayer.getSubimage((int)(2 * width), (int)((2 + this.getDirection()) * height), (int)(width), (int)(height)), (int)(this.getX() - changeX + (this.getWidth() - width)/2), (int)(this.getY() - changeY + this.getHeight() - height), null);
				} else if (this.isBJump()) {
					g.drawImage(iPlayer.getSubimage((int)(0 * width), (int)((2 + this.getDirection()) * height), (int)(width), (int)(height)), (int)(this.getX() - changeX + (this.getWidth() - width)/2), (int)(this.getY() - changeY + this.getHeight() - height), null);			
				} else if (this.isBDown()) {
					g.drawImage(iPlayer.getSubimage((int)(1 * width), (int)((2 + this.getDirection()) * height), (int)(width), (int)(height)), (int)(this.getX() - changeX + (this.getWidth() - width)/2), (int)(this.getY() - changeY + this.getHeight() - height), null);
				} else {
					if (this.getVelocityX() != 0) {
						g.drawImage(iPlayer.getSubimage((int)(this.getFrame() * width), (int)(this.getDirection() * height), (int)(width), (int)(height)), (int)(this.getX() - changeX + (this.getWidth() - width)/2), (int)(this.getY() - changeY + this.getHeight() - height), null);
					} else {
						g.drawImage(iPlayer.getSubimage((int)(0 * width), (int)(this.getDirection() * height), (int)(width), (int)(height)), (int)(this.getX() - changeX + (this.getWidth() - width)/2), (int)(this.getY() - changeY + this.getHeight() - height), null);
					}
				}
			} else { 
				if (((this.getGoalVecX() < 0) && (this.getVelocityX() > 0)) ||
				((this.getGoalVecX() > 0) && (this.getVelocityX() < 0))) {
					g.drawImage(iPlayer.getSubimage((int)(2 * this.getWidth()), (int)((2 + this.getDirection()) * this.getHeight()), (int)(this.getWidth()), (int)(this.getHeight())), (int)(this.getX() - changeX), (int)(this.getY() - changeY), null);				
				} else if (this.isBJump()) {
					g.drawImage(iPlayer.getSubimage((int)(0 * this.getWidth()), (int)((2 + this.getDirection()) * this.getHeight()), (int)(this.getWidth()), (int)(this.getHeight())), (int)(this.getX() - changeX), (int)(this.getY() - changeY), null);				
				} else if (this.isBDown()) {
					g.drawImage(iPlayer.getSubimage((int)(1 * this.getWidth()), (int)((2 + this.getDirection()) * this.getHeight()), (int)(this.getWidth()), (int)(this.getHeight())), (int)(this.getX() - changeX), (int)(this.getY() - changeY), null);				
				} else {
					if (this.getVelocityX() != 0) {
						g.drawImage(iPlayer.getSubimage((int)(this.getFrame() * this.getWidth()), (int)(this.getDirection() * this.getHeight()), (int)(this.getWidth()), (int)(this.getHeight())), (int)(this.getX() - changeX), (int)(this.getY() - changeY), null);
					} else {
						g.drawImage(iPlayer.getSubimage((int)(0 * this.getWidth()), (int)(this.getDirection() * this.getHeight()), (int)(this.getWidth()), (int)(this.getHeight())), (int)(this.getX() - changeX), (int)(this.getY() - changeY), null);
					}
				}
			}
			if (ApoMarioConstants.DEBUG) {
				g.setColor(Color.red);
				Rectangle2D.Float rec = this.getRec();
				g.drawRect((int)(rec.x - changeX), (int)(rec.y - changeY), (int)rec.width, (int)rec.height);
			}
			for (int i = 0; i < this.fireballs.size(); i++) {
				this.fireballs.get(i).render(g, changeX, changeY);
			}
			if (this.isBVisible()) {
				String name = "";
				if (this.isBGodMode()) {
					name = "godmode cheater";
				}
				if ((name != null) && (name.length() > 0)) {
					g.setColor(Color.BLACK);
					g.setFont(ApoMarioConstants.FONT_STATISTICS);
					int start = (int)(this.getX() + this.getWidth()/2 - g.getFontMetrics().stringWidth(name)/2);
					g.drawString(name, start - changeX, this.getRec().y - 5 - changeY);
				}
			}
		}
	}
}