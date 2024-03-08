package apoMario.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import apoMario.ApoMarioConstants;
import apoMario.level.ApoMarioLevel;

public class ApoMarioFlower extends ApoMarioEnemy {

	private final float MAX_CHANGE_Y = -2 * ApoMarioConstants.TILE_SIZE + 5 * ApoMarioConstants.SIZE;
	
	private final float UP_TIME_STAND = 3000;
	
	private int timeToShow;
	
	private int startTime;
	
	private int curTimeToShow, curUpStandTime;
	
	private boolean bUp;
	
	private float changeY;
	
	public ApoMarioFlower(BufferedImage animation, float x, float y, float width, float height, int tiles, long time, int timeToShow, int startTime, int id) {
		super(animation, x, y, width, height, tiles, time, ApoMarioConstants.POINTS_ENEMY_FLOWER, id);
		this.timeToShow = timeToShow;
		this.curTimeToShow = startTime;
		this.startTime = startTime;
		this.setBFall(true);
		this.setBImmortal(false);
		this.setBFly(false);
	}
	
	public void init() {
		super.init();
		this.bUp = true;
		this.curTimeToShow = this.startTime;
		this.curUpStandTime = 0;
		this.changeY = 0;
	}
	
	public int getTimeToShow() {
		return this.timeToShow;
	}

	public int getStartTime() {
		return this.startTime;
	}

	public void setTimeToShow(int timeToShow) {
		this.timeToShow = timeToShow;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public void think(int delta, ApoMarioLevel level) {
		if (!this.isBVisible()) {
			return;
		}
		super.think(delta);
		if (this.isBDie()) {
			if (this.getY() + this.changeY + this.getChangeY() > level.getHeight() * ApoMarioConstants.TILE_SIZE) {
				this.setBVisible(false);
			} else {
				this.setVecY(this.getVecY() + delta * ApoMarioConstants.ENEMY_VEC_DECREASE_Y_DIE);
				this.setChangeY(this.getChangeY() + delta * this.getVecY());
			}
			return;
		}
		Rectangle2D.Float rec = this.getRec();
		for (int player = 0; player < level.getPlayers().size(); player++) {
			if (level.getPlayers().get(player).getRec().intersects(rec)) {
				level.getPlayers().get(player).damagePlayer();
			}
		}
		if ((this.curTimeToShow > 0) && (this.changeY == 0)) {
			if (!this.isPlayerNear(level)) {
				this.curTimeToShow -= delta;
			}
		} else {
			if (this.bUp) {
				this.changeY -= delta * ApoMarioConstants.FLOWER_VEC_Y;
				if (this.changeY <= this.MAX_CHANGE_Y) {
					this.changeY = this.MAX_CHANGE_Y;
					this.curUpStandTime += delta;
					if (this.curUpStandTime >= this.UP_TIME_STAND) {
						this.bUp = !this.bUp;
						this.curUpStandTime = 0;
					}
				}
			} else {
				this.changeY += delta * ApoMarioConstants.FLOWER_VEC_Y;
				if (this.changeY >= 0) {
					this.changeY = 0;
					this.bUp = !this.bUp;
					this.curTimeToShow = this.timeToShow;
				}
			}
		}
	}
	
	public float getMyChangeY() {
		return this.changeY;
	}
	
	/**
	 * gibt das aktuelle Rechteck der Entity zurück
	 * @return gibt das aktuelle Rechteck der Entity zurück
	 */
	public Rectangle2D.Float getRec() {
		this.getRectangle().x = this.getX();
		this.getRectangle().y = this.getY() + 8 * ApoMarioConstants.SIZE + this.changeY + this.getChangeY();
		this.getRectangle().width = this.getWidth();
		this.getRectangle().height = this.getHeight() - 8 * ApoMarioConstants.SIZE;
		return this.getRectangle();
	}
	
	/**
	 * gibt das Rechteck für den nächsten Schritt der Entity zurück
	 * @return gibt das Rechteck für den nächsten Schritt der Entity zurück
	 */
	public Rectangle2D.Float getNextRec(int delta) {
		this.nextRec.x = this.getX() + delta * this.getVecX();
		this.nextRec.y = this.getY() + delta * this.getVecY() + this.getChangeY() + 8 * ApoMarioConstants.SIZE + this.changeY;
		this.nextRec.width = this.getWidth();
		this.nextRec.height = this.getHeight() - 8 * ApoMarioConstants.SIZE;
		return this.nextRec;
	}

	public void render(Graphics2D g, int changeX, int changeY) {
		super.render(g, changeX, (int)(changeY - this.changeY - this.getChangeY()));
		if (ApoMarioConstants.DEBUG) {
			g.setColor(Color.red);
			Rectangle2D.Float rec = this.getRec();
			g.drawRect((int)(rec.x - changeX) * ApoMarioConstants.APP_SIZE, (int)(rec.y - changeY - this.getChangeY()) * ApoMarioConstants.APP_SIZE, (int)rec.width * ApoMarioConstants.APP_SIZE, (int)rec.height * ApoMarioConstants.APP_SIZE);
		}
	}

}
