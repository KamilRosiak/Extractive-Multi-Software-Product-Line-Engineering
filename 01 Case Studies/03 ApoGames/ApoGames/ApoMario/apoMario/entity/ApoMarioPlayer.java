package apoMario.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.apogames.entity.ApoAnimation;

import apoMario.ApoMarioConstants;
import apoMario.ApoMarioImageContainer;
import apoMario.ai.ApoMarioAI;
import apoMario.ai.ApoMarioAILevel;
import apoMario.ai.ApoMarioAIPlayer;
import apoMario.help.ApoMarioPlayerDraw;
import apoMario.level.ApoMarioLevel;

/**
 * Klasse, die den Spieler repräsentiert
 * @author Dirk Aporius
 */
public class ApoMarioPlayer extends ApoMarioEntity {
	
	private final int MAX_CHANGE_TYPE = 100;
	
	private final int MAX_DRAWELEMENTS = 100;
	
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
	
	private ApoMarioAI ai;
	
	private int changeType;
	
	private int curFireTime;
	
	private ArrayList<ApoMarioFireball> fireballs;
	
	private boolean bDie;
	
	private float changeY;
	
	private int player;
	
	private ArrayList<ApoMarioPlayerDraw> drawElements;
	
	private ArrayList<String> messages;
	
	private boolean bNextJump;
	
	private String teamName, author;
	
	private int count;
	
	private boolean bDuckOld;
	
	private String pathAI;
	
	private float lastGoalVecX;
	
	public ApoMarioPlayer(float x, float y, float width, float height, int tiles, long time, int player, int id) {
		super(null, x, y, width, height, tiles, time, 6, id);
		this.player = player;
	}
	
	public void init() {
		super.init();
		
		this.setBDown(false);
		this.setBJump(false);
		this.setGoalVecX(0);
		this.setType(ApoMarioConstants.PLAYER_TYPE_FIRE);
		this.setDirection(ApoMarioEntity.RIGHT);
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
		this.bNextJump = false;
		this.bEnemyJump = false;
		this.changeY = 0;
		this.drawElements = new ArrayList<ApoMarioPlayerDraw>();
		this.messages = new ArrayList<String>();
		this.count = 0;
		this.lastGoalVecX = 0;
		if (this.pathAI == null) {
			this.pathAI = "";
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
	
	public void addDrawElement(ApoMarioPlayerDraw drawElement) {
		if (this.drawElements.size() < this.MAX_DRAWELEMENTS) {
			this.drawElements.add(drawElement);
		}
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

	public ApoMarioAI getAi() {
		return this.ai;
	}

	public void setAi(final ApoMarioAI ai) {
		this.setAi(ai, this.pathAI);
	}
	
	public void setAi(final ApoMarioAI ai, final String path) {
		if (this.ai != null) {
			this.ai.save(this.pathAI);
		}
		this.ai = ai;
		if (this.ai != null) {
			this.teamName = this.ai.getTeamName();
			this.author = this.ai.getAuthor();
			this.pathAI = path;
			this.ai.load(this.pathAI);
		} else {
			this.teamName = null;
			this.author = null;
		}
	}
	
	public void saveAI() {
		if (this.ai != null) {
			this.ai.save(this.pathAI);
		}
	}

	public int getDamageTime() {
		return this.damageTime;
	}

	public void setDamageTime(int damageTime) {
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
			this.setVecY(-ApoMarioConstants.ENEMY_VEC_Y_DIE);
		}
	}
	
	public float getGoalVecX() {
		return this.goalVecX;
	}
	
	public void setGoalVecX(float goalVecX) {
		this.setGoalVecX(goalVecX, true);
	}

	private void setGoalVecX(float goalVecX, boolean bParticle) {
		if (this.isBDown()) {
			goalVecX = 0;
		}
		if (bParticle) {
			if ((goalVecX != this.lastGoalVecX) && (goalVecX != 0) && (this.getVecY() == 0)) {
				if ((goalVecX < this.getVecX()) && (goalVecX < 0) && (this.getVecX() > 0)) {
					if (this.getVecX() > 0) {
						Rectangle2D.Float rec = this.getRec();
						this.makeParticles((float)(rec.getX() + rec.getWidth()/2), (float)(rec.getY() + rec.getHeight() - 4 * ApoMarioConstants.SIZE), (float)(rec.getWidth()),  (float)(8 * ApoMarioConstants.SIZE));
					}
				} else if ((goalVecX > this.getVecX()) && (goalVecX > 0) && (this.getVecX() < 0)) {
					if (this.getVecX() < 0) {
						Rectangle2D.Float rec = this.getRec();
						this.makeParticles((float)(rec.getX() + rec.getWidth()/2), (float)(rec.getY() + rec.getHeight() - 4 * ApoMarioConstants.SIZE), (float)(rec.getWidth()),  (float)(8 * ApoMarioConstants.SIZE));				
					}
				}
			}
		}

		this.goalVecX = goalVecX;
		if (this.isBSpeed()) {
			if (this.goalVecX < -ApoMarioConstants.PLAYER_MAX_VEC_X_FAST) {
				this.setGoalVecX(-ApoMarioConstants.PLAYER_MAX_VEC_X_FAST);
			} else if (this.goalVecX > ApoMarioConstants.PLAYER_MAX_VEC_X_FAST) {
				this.setGoalVecX(ApoMarioConstants.PLAYER_MAX_VEC_X_FAST);
			}
		} else {
			if (this.goalVecX < -ApoMarioConstants.PLAYER_MAX_VEC_X_NORMAL) {
				this.setGoalVecX(-ApoMarioConstants.PLAYER_MAX_VEC_X_NORMAL);
			} else if (this.goalVecX > ApoMarioConstants.PLAYER_MAX_VEC_X_NORMAL) {
				this.setGoalVecX(ApoMarioConstants.PLAYER_MAX_VEC_X_NORMAL);
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

	public boolean isBDown() {
		return this.bDown;
	}

	public void setBDown(boolean bDown) {
		this.bDown = bDown;
	}

	public boolean isBJump() {
		return this.bJump;
	}
	
	
	/**
	 * gibt zurück, ob ein Spieler springen kann
	 * @return 
	 */
	public boolean canJump() {
		if (this.isBEnemyJump()) {
			return false;
		} else if (this.getVecY() <= 0) {
			return true;
		}
		return false;
	}
	
	public void setBNextJump(boolean bJump) {
		this.bNextJump = bJump;
	}
	
	public void setBJump(boolean bJump) {
		if ((this.bEnemyJump) || (this.isBDown())) {
			return;
		}
		this.bJump = bJump;
		if (bJump) {
			if (this.getVecY() == 0) {
				this.setVecY(-ApoMarioConstants.PLAYER_VEC_Y);
			}
		} else {
			if (this.getVecY() < 0) {
				this.setVecY(0);
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
		this.setVecY(-ApoMarioConstants.PLAYER_VEC_Y_ENEMY);
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
		this.getRectangle().x = this.getX() + 1f/4f * (float)ApoMarioConstants.TILE_SIZE + 7f * ApoMarioConstants.SIZE;
		this.getRectangle().width = this.getWidth() - 2*1f/4f * ApoMarioConstants.TILE_SIZE - 2 * 7f * ApoMarioConstants.SIZE;
		if ((this.getType() == ApoMarioConstants.PLAYER_TYPE_LITTLE) || (this.isBDown())) {
			this.getRectangle().y = this.getY() + ApoMarioConstants.TILE_SIZE;
			this.getRectangle().height = this.getHeight() - ApoMarioConstants.TILE_SIZE;			
		} else {
			this.getRectangle().y = this.getY() + 4 * ApoMarioConstants.SIZE;
			this.getRectangle().height = this.getHeight() - 4 * ApoMarioConstants.SIZE;
		}
		return this.getRectangle();
	}
	
	/**
	 * gibt das Rechteck für den nächsten Schritt der Entity zurück
	 * @return gibt das Rechteck für den nächsten Schritt der Entity zurück
	 */
	public Rectangle2D.Float getNextRec(int delta) {
		Rectangle2D.Float rec = this.getRec();
		this.nextRec.x = (float)(rec.getX() + delta * this.getVecX());
		this.nextRec.y = (float)(rec.getY() + delta * this.getVecY());
		this.nextRec.width = (float)(rec.getWidth());
		this.nextRec.height = (float)(rec.getHeight());
		return this.nextRec;
	}
	
	public synchronized void thinkAI(int delta, ApoMarioLevel level) {
		if (((this.isBDie()) || (!this.isBVisible()))) {
			return;
		}
		if (level.isBReplay()) {
			this.bDuckOld = this.isBDown();
			this.setBDown(false);
			this.setBSpeed(false);
			int value = level.getReplayPlay().nextStep();
			if (value < 0) {
				return;
			}
		} else if (this.ai != null) {
			this.bDuckOld = this.isBDown();
			this.setBDown(false);
			this.setBSpeed(false);
			int player = 0;
			if (level.getPlayers().size() > 1) {
				for (int i = 0; i < level.getPlayers().size(); i++) {
					if (level.getPlayers().get(i).equals(this)) {
						player = i;
						break;
					}
				}
			}
			try {
				this.ai.think(new ApoMarioAIPlayer(this, level), new ApoMarioAILevel(level, player));
				if (this.bSpeed) {
					if (this.goalVecX < 0) {
						this.goalVecX = -ApoMarioConstants.PLAYER_MAX_VEC_X_FAST;
					} else if (this.goalVecX > 0) {
						this.goalVecX = ApoMarioConstants.PLAYER_MAX_VEC_X_FAST;
					}
				}
				this.setGoalVecX(this.getGoalVecX(), true);
				this.setBJump(this.bNextJump);
				this.bNextJump = false;
				level.getReplay().setPlayerStep(this);
				this.count += 1;
			} catch (Exception ex) {
				this.bDie = true;
				this.points += ApoMarioConstants.POINTS_EXCEPTION;
				ex.printStackTrace();
				System.out.println("Exception: "+ex.getMessage()+" "+ex.getCause()+" "+ex.getLocalizedMessage());
				this.messages.add("Exception: "+ex.getMessage()+" "+ex.getCause()+" "+ex.getLocalizedMessage());
				ex.printStackTrace();
				level.setAnalysis(false);
			}
			if ((this.messages.size() > 0) && (level.getComponent().getDebugFrame() != null)) {
				for (int i = 0; i < this.messages.size(); i++) {
					String s = String.valueOf(this.ai.getTeamName())+"("+(String.valueOf(this.getPlayer()+1))+"): "+this.messages.get(i);
					level.getComponent().getDebugFrame().addString(s);
				}
			}
			this.messages.clear();
		} else {
		}
	}
	
	public synchronized void think(int delta, ApoMarioLevel level) {		
		if ((!level.isBReplay()) && (this.ai == null)) {
			this.setGoalVecX(this.getGoalVecX());
			level.getReplay().setPlayerStep(this);
		}
		if (this.isBDie()) {
			this.setBVisible(false);
			level.setAnalysis(false);
			return;
		}
		if (level.isBReplay()) {
			this.setBJump(this.bNextJump);
			this.bNextJump = false;
		}
		super.think(delta, level);
		
		for (int i = this.drawElements.size() - 1; i >= 0; i--) {
			this.drawElements.get(i).setTime(this.drawElements.get(i).getTime() - delta);
			if (this.drawElements.get(i).getTime() <= 0) {
				this.drawElements.remove(i);
			}
		}
		
		if (this.curFireTime > 0) {
			this.curFireTime -= delta;
		}
		if ((this.getType() == ApoMarioConstants.PLAYER_TYPE_FIRE) && (this.isBSpeed()))/* && (bSpeed == !this.bSpeed))*/ {
			if ((this.curFireTime <= 0) && (this.fireballs.size() < 2)) {
				Rectangle2D.Float rec = this.getRec();
				int y = (int)(rec.getY() + rec.getHeight()/2);
				if ((this.getType() == ApoMarioConstants.PLAYER_TYPE_LITTLE) || (this.isBDown())) {
					y = (int)(this.getY() + 4 * ApoMarioConstants.SIZE + rec.getHeight()/2);		
				};
				if (this.getDirection() == ApoMarioEntity.RIGHT) {
					this.fireballs.add(new ApoMarioFireball(ApoMarioImageContainer.PARTICLE.getSubimage(0, 3 * ApoMarioConstants.TILE_SIZE/2 * ApoMarioConstants.APP_SIZE, 4 * ApoMarioConstants.TILE_SIZE/2 * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE/2 * ApoMarioConstants.APP_SIZE), (int)(rec.getX() + rec.getWidth()), y, 4, ApoMarioConstants.PLAYER_ANIMATION_TIME, false, this.player, ++ApoMarioLevel.ID));					
				} else {
					this.fireballs.add(new ApoMarioFireball(ApoMarioImageContainer.PARTICLE.getSubimage(0, 3 * ApoMarioConstants.TILE_SIZE/2 * ApoMarioConstants.APP_SIZE, 4 * ApoMarioConstants.TILE_SIZE/2 * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE/2 * ApoMarioConstants.APP_SIZE), (int)(rec.getX()), y, 4, ApoMarioConstants.PLAYER_ANIMATION_TIME, true, this.player, ++ApoMarioLevel.ID));	
				}
				this.curFireTime = ApoMarioConstants.PLAYER_FIREBALL_TIME;
			}
		}
		if (this.getRec().intersects(level.getFinish().getRec())) {
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
		if (this.getGoalVecX() != this.getVecX()) {
			if (this.getGoalVecX() < this.getVecX()) {
				if (this.getVecX() > 0) {
					this.setVecX(this.getVecX() - delta * ApoMarioConstants.PLAYER_DECREASE_VEC_X);					
				} else {
					this.setVecX(this.getVecX() - delta * ApoMarioConstants.PLAYER_INCREASE_VEC_X);
				}
				if (this.getGoalVecX() >= this.getVecX()) {
					this.setVecX(this.getGoalVecX());
				}
			} else if (this.getGoalVecX() > this.getVecX()) {
				if (this.getVecX() < 0) {
					this.setVecX(this.getVecX() + delta * ApoMarioConstants.PLAYER_DECREASE_VEC_X);					
				} else {
					this.setVecX(this.getVecX() + delta * ApoMarioConstants.PLAYER_INCREASE_VEC_X);
				}
				if (this.getGoalVecX() <= this.getVecX()) {
					this.setVecX(this.getGoalVecX());
				}
			}
		}
		this.lastGoalVecX = this.getGoalVecX();
		
		// Überprüfung Kollision nach unten
		this.nextYCheck(delta, level, ApoMarioConstants.VECY_PLAYER);
		if (this.getVecY() != 0) {
			if (this.getVecY() < 0) {
				this.setVecY(this.getVecY() + ApoMarioConstants.PLAYER_VEC_DECREASE_Y * delta);
			} else {
				this.setVecY(this.getVecY() + ApoMarioConstants.PLAYER_VEC_DECREASE_FALL_Y * delta);
			}
		}
		this.setX(this.getX() + delta * this.getVecX());
		if (this.getRec().getX() < 0) {
			this.setX((float)(0 + this.getX() - this.getRec().getX()));
		}
		this.setY(this.getY() + delta * this.getVecY());
	}
	
	public boolean yDownCheckEntity(ApoMarioLevel level, int x, int y, int delta) {
		Rectangle2D.Float nextRec = this.getNextRec(delta);
		Rectangle2D.Float myNextRec = new Rectangle2D.Float(nextRec.x, nextRec.y + nextRec.height, nextRec.width, 2 * ApoMarioConstants.SIZE);
		if ((this.bDuckOld) && (!this.isBDown())) {
			if (level.getLevelEntities()[y][x].intersects(nextRec.x, nextRec.y, nextRec.width, nextRec.height)) {
				this.setBDown(true);
				nextRec = this.getNextRec(delta);
				myNextRec = new Rectangle2D.Float(nextRec.x, nextRec.y + nextRec.height, nextRec.width, 2 * ApoMarioConstants.SIZE);				
			}
		}
		float oldVecY = this.getVecY();
		float oldY = this.getY();
		
		if (!level.getLevelEntities()[y][x].intersects((float)(myNextRec.getX() + myNextRec.getWidth()/2), (float)(myNextRec.getY() + myNextRec.getHeight()))) {
			if (!this.test(level, x, y)) {
				return false;
			}
		}
		
		float newY = y * ApoMarioConstants.TILE_SIZE - this.getHeight();
		this.setVecY(0);
		this.setY(newY);
		if (!testNewY(level)) {
			this.setY(oldY);
			this.setVecY(oldVecY);
			return false;
		}
		if (Math.abs(newY - oldY) > 4 * ApoMarioConstants.SIZE) {
			this.setY(oldY);
			this.setVecY(oldVecY);
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
//							this.addDrawElement(new ApoMarioPlayerDrawRect(wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight(), true, 100, Color.BLUE));
//							level.getComponent().setBPause(true);
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
		this.setBJump(false);
	}
	
	@Override
	public void coinCheck(ApoMarioLevel level, int x, int y) {
		level.getLevelEntities()[y][x].setBVisible(false);
		this.setPoints(this.getPoints() + ApoMarioConstants.POINTS_COIN);
		this.setCoins(this.getCoins() + 1);
	}

	@Override
	public void yCheckUp(ApoMarioLevel level, int x, int y) {
		this.bEnemyJump = false;
		((ApoMarioWall)(level.getLevelEntities()[y][x])).damageWall(level, this.player);
	}
	
	@Override
	public void xCheckChange(ApoMarioLevel level, int x, int y) {
		this.setVecX(0);
	}
	
	/**
	 * rendert den Spieler, falls er sichtbar ist hin
	 */
	public synchronized void render(Graphics2D g, int changeX, int changeY) {
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
				int w = dieImage.getWidth() * ApoMarioConstants.APP_SIZE;
				int h = dieImage.getHeight() * ApoMarioConstants.APP_SIZE;
				int x = (int)(this.getX() - changeX + (this.getWidth() - width)/2)* ApoMarioConstants.APP_SIZE;
				int y = (int)(this.getY() - changeY + this.changeY + (this.getHeight() - height)) * ApoMarioConstants.APP_SIZE;
				g.drawImage(dieImage, x, y, (x + w), (y + h), 0, h, w, 0, null);
				return;
			}
			if ((type == ApoMarioConstants.PLAYER_TYPE_LITTLE) || (this.isBDown())) {
				int width = iPlayer.getWidth()/this.getTiles()/ApoMarioConstants.APP_SIZE;
				int height = iPlayer.getHeight()/6/ApoMarioConstants.APP_SIZE;
				if (((this.getGoalVecX() < 0) && (this.getVecX() > 0)) ||
					((this.getGoalVecX() > 0) && (this.getVecX() < 0))) {
					g.drawImage(iPlayer.getSubimage((int)(2 * width * ApoMarioConstants.APP_SIZE), (int)((2 + this.getDirection()) * height) * ApoMarioConstants.APP_SIZE, (int)(width) * ApoMarioConstants.APP_SIZE, (int)(height) * ApoMarioConstants.APP_SIZE), (int)(this.getX() - changeX + (this.getWidth() - width)/2) * ApoMarioConstants.APP_SIZE, (int)(this.getY() - changeY + this.getHeight() - height) * ApoMarioConstants.APP_SIZE, null);
				} else if (this.isBJump()) {
					g.drawImage(iPlayer.getSubimage((int)(0 * width * ApoMarioConstants.APP_SIZE), (int)((2 + this.getDirection()) * height) * ApoMarioConstants.APP_SIZE, (int)(width) * ApoMarioConstants.APP_SIZE, (int)(height) * ApoMarioConstants.APP_SIZE), (int)(this.getX() - changeX + (this.getWidth() - width)/2) * ApoMarioConstants.APP_SIZE, (int)(this.getY() - changeY + this.getHeight() - height) * ApoMarioConstants.APP_SIZE, null);			
				} else if (this.isBDown()) {
					g.drawImage(iPlayer.getSubimage((int)(1 * width * ApoMarioConstants.APP_SIZE), (int)((2 + this.getDirection()) * height) * ApoMarioConstants.APP_SIZE, (int)(width) * ApoMarioConstants.APP_SIZE, (int)(height) * ApoMarioConstants.APP_SIZE), (int)(this.getX() - changeX + (this.getWidth() - width)/2) * ApoMarioConstants.APP_SIZE, (int)(this.getY() - changeY + this.getHeight() - height) * ApoMarioConstants.APP_SIZE, null);
				} else {
					if (this.getVecX() != 0) {
						g.drawImage(iPlayer.getSubimage((int)(this.getFrame() * width * ApoMarioConstants.APP_SIZE), (int)(this.getDirection() * height) * ApoMarioConstants.APP_SIZE, (int)(width) * ApoMarioConstants.APP_SIZE, (int)(height) * ApoMarioConstants.APP_SIZE), (int)(this.getX() - changeX + (this.getWidth() - width)/2) * ApoMarioConstants.APP_SIZE, (int)(this.getY() - changeY + this.getHeight() - height) * ApoMarioConstants.APP_SIZE, null);
					} else {
						g.drawImage(iPlayer.getSubimage((int)(0 * width * ApoMarioConstants.APP_SIZE), (int)(this.getDirection() * height) * ApoMarioConstants.APP_SIZE, (int)(width) * ApoMarioConstants.APP_SIZE, (int)(height) * ApoMarioConstants.APP_SIZE), (int)(this.getX() - changeX + (this.getWidth() - width)/2) * ApoMarioConstants.APP_SIZE, (int)(this.getY() - changeY + this.getHeight() - height) * ApoMarioConstants.APP_SIZE, null);
					}
				}
			} else { 
				if (((this.getGoalVecX() < 0) && (this.getVecX() > 0)) ||
				((this.getGoalVecX() > 0) && (this.getVecX() < 0))) {
					g.drawImage(iPlayer.getSubimage((int)(2 * this.getWidth()) * ApoMarioConstants.APP_SIZE, (int)((2 + this.getDirection()) * this.getHeight() * ApoMarioConstants.APP_SIZE), (int)(this.getWidth() * ApoMarioConstants.APP_SIZE), (int)(this.getHeight()) * ApoMarioConstants.APP_SIZE), (int)(this.getX() - changeX) * ApoMarioConstants.APP_SIZE, (int)(this.getY() - changeY) * ApoMarioConstants.APP_SIZE, null);				
				} else if (this.isBJump()) {
					g.drawImage(iPlayer.getSubimage((int)(0 * this.getWidth()) * ApoMarioConstants.APP_SIZE, (int)((2 + this.getDirection()) * this.getHeight() * ApoMarioConstants.APP_SIZE), (int)(this.getWidth() * ApoMarioConstants.APP_SIZE), (int)(this.getHeight()) * ApoMarioConstants.APP_SIZE), (int)(this.getX() - changeX) * ApoMarioConstants.APP_SIZE, (int)(this.getY() - changeY) * ApoMarioConstants.APP_SIZE, null);				
				} else if (this.isBDown()) {
					g.drawImage(iPlayer.getSubimage((int)(1 * this.getWidth()) * ApoMarioConstants.APP_SIZE, (int)((2 + this.getDirection()) * this.getHeight() * ApoMarioConstants.APP_SIZE), (int)(this.getWidth() * ApoMarioConstants.APP_SIZE), (int)(this.getHeight()) * ApoMarioConstants.APP_SIZE), (int)(this.getX() - changeX) * ApoMarioConstants.APP_SIZE, (int)(this.getY() - changeY) * ApoMarioConstants.APP_SIZE, null);				
				} else {
					if (this.getVecX() != 0) {
						g.drawImage(iPlayer.getSubimage((int)(this.getFrame() * this.getWidth() * ApoMarioConstants.APP_SIZE), (int)(this.getDirection() * this.getHeight() * ApoMarioConstants.APP_SIZE), (int)(this.getWidth() * ApoMarioConstants.APP_SIZE), (int)(this.getHeight()) * ApoMarioConstants.APP_SIZE), (int)(this.getX() - changeX) * ApoMarioConstants.APP_SIZE, (int)(this.getY() - changeY) * ApoMarioConstants.APP_SIZE, null);
					} else {
						g.drawImage(iPlayer.getSubimage((int)(0 * this.getWidth() * ApoMarioConstants.APP_SIZE), (int)(this.getDirection() * this.getHeight() * ApoMarioConstants.APP_SIZE), (int)(this.getWidth() * ApoMarioConstants.APP_SIZE), (int)(this.getHeight()) * ApoMarioConstants.APP_SIZE), (int)(this.getX() - changeX) * ApoMarioConstants.APP_SIZE, (int)(this.getY() - changeY) * ApoMarioConstants.APP_SIZE, null);
					}
				}
			}
			if (ApoMarioConstants.DEBUG) {
				g.setColor(Color.red);
				Rectangle2D.Float rec = this.getRec();
				g.drawRect((int)(rec.x - changeX) * ApoMarioConstants.APP_SIZE, (int)(rec.y - changeY) * ApoMarioConstants.APP_SIZE, (int)rec.width * ApoMarioConstants.APP_SIZE, (int)rec.height * ApoMarioConstants.APP_SIZE);
			}
			for (int i = 0; i < this.fireballs.size(); i++) {
				this.fireballs.get(i).render(g, changeX, changeY);
			}
			if (this.isBVisible()) {
				String name = "human";
				name = this.getTeamName() + " (" + (this.getPlayer()+1) + ")";
				if (this.getTeamName() == null) {
					if (this.getAi() == null) {
						name = "human";
					} else {
						name = "ai (" + (this.getPlayer()+1) + ")";
					}
				}
				this.drawStringAbove(g, name, changeX, changeY, false);
				if (ApoMarioConstants.DEBUG) {
					for (int i = this.drawElements.size() - 1; i >= 0; i--) {
						this.drawElements.get(i).render(g, changeX * ApoMarioConstants.APP_SIZE, changeY * ApoMarioConstants.APP_SIZE);
					}
				}
			}
		}
	}
	
	public void drawStringAbove(final Graphics2D g, String name, int changeX, int changeY, boolean bBackground) {
		if (name != null) {
			g.setFont(ApoMarioConstants.FONT_STATISTICS);
			int w = g.getFontMetrics().stringWidth(name);
			int h = g.getFontMetrics().getHeight();
			int start = (int)((this.getX() + this.getWidth()/2) * ApoMarioConstants.APP_SIZE - w/2);
			int x = start - changeX * ApoMarioConstants.APP_SIZE;
			int y = (int)((this.getRec().y - 3 - changeY) * ApoMarioConstants.APP_SIZE);
			if (bBackground) {
				g.setColor(Color.WHITE);
				g.fillRoundRect(x - 5, y - 3 - h, w + 10, h + 5, 8, 8);
				g.setColor(Color.BLACK);
				g.drawRoundRect(x - 5, y - 3 - h, w + 10, h + 5, 8, 8);
			}
			g.setColor(Color.BLACK);
			g.drawString(name, x, y);
		}
	}
	
	public void drawImage(final Graphics2D g, final ApoAnimation animation, int changeX, int changeY) {
		int start = (int)(((this.getX() + this.getWidth()/2) * ApoMarioConstants.APP_SIZE) - animation.getWidth()/2);
		int x = start - changeX * ApoMarioConstants.APP_SIZE;
		int y = (int)((this.getRec().y - changeY) * ApoMarioConstants.APP_SIZE) - ApoMarioConstants.TILE_SIZE;
		animation.render(g, x, y);
	}
}