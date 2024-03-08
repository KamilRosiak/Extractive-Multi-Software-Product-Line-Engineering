package apoCommando.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import apoCommando.ApoMarioConstants;
import apoCommando.level.ApoMarioLevel;

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
	
	public void think(int delta, ApoMarioLevel level) {
		if (!this.isBVisible()) {
			return;
		}
		super.think(delta);
		if (this.isBDie()) {
			if (this.getY() + this.changeY + this.getChangeY() > level.getHeight() * ApoMarioConstants.TILE_SIZE) {
				this.setBVisible(false);
			} else {
				this.setVelocityY(this.getVelocityY() + delta * ApoMarioConstants.ENEMY_VEC_DECREASE_Y_DIE);
				this.setChangeY(this.getChangeY() + delta * this.getVelocityY());
			}
			return;
		}
		Rectangle2D.Float rec = this.getRec();
		if (level.getPlayer().getRec().intersects(rec)) {
			level.getPlayer().damagePlayer();
		}
		if ((this.curTimeToShow > 0) && (this.changeY == 0)) {
			Rectangle2D.Float recPlayer = level.getPlayer().getRec();
			if ((recPlayer.getX() + recPlayer.getWidth() > rec.getX() - 2 * ApoMarioConstants.TILE_SIZE) &&
				(recPlayer.getX() < rec.getX() + 2 * ApoMarioConstants.TILE_SIZE)) {
				return;
			}
			this.curTimeToShow -= delta;
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

	public Rectangle2D.Float getRec() {
		return new Rectangle2D.Float(this.getX(), this.getY() + 8 * ApoMarioConstants.SIZE + this.changeY + this.getChangeY(), this.getWidth(), this.getHeight() - 8 * ApoMarioConstants.SIZE);
	}
	
	public Rectangle2D.Float getNextRec(int delta) {
		return new Rectangle2D.Float(this.getX(), this.getY() + delta * this.getVelocityY() + this.getChangeY() + 8 * ApoMarioConstants.SIZE + this.changeY, this.getWidth(), this.getHeight() - 8 * ApoMarioConstants.SIZE);
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		super.render(g, changeX, (int)(changeY - this.changeY - this.getChangeY()));
		if (ApoMarioConstants.DEBUG) {
			g.setColor(Color.red);
			Rectangle2D.Float rec = this.getRec();
			g.drawRect((int)(rec.x - changeX), (int)(rec.y - changeY - this.getChangeY()), (int)rec.width, (int)rec.height);
		}
	}

}
