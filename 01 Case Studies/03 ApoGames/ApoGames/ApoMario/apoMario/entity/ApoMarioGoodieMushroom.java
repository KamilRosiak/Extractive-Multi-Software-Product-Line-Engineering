package apoMario.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import apoMario.ApoMarioConstants;
import apoMario.level.ApoMarioLevel;

public class ApoMarioGoodieMushroom extends ApoMarioEnemy {

	private final long MAX_Y = (long)ApoMarioConstants.TILE_SIZE;
	
	private boolean bStart;
	
	private float curChangeY;
	
	public ApoMarioGoodieMushroom(BufferedImage animation, float x, float y, boolean bLeft, int id) {
		super(animation, x, y, animation.getWidth() / ApoMarioConstants.APP_SIZE, animation.getHeight() / ApoMarioConstants.APP_SIZE, 1, 99999999, 0, id);
		if (bLeft) {
			this.setVecX(-ApoMarioConstants.MUSHROOM_VEC_X);
		} else {
			this.setVecX(ApoMarioConstants.MUSHROOM_VEC_X);			
		}
		this.setVecY(ApoMarioConstants.GOODIE_VEC_Y);
	}

	public void init() {
		super.init();
		this.bStart = true;
		this.curChangeY = 0;		
	}
	
	public void catchMe(ApoMarioPlayer player) {
		player.catchMushroom();
		player.setPoints(player.getPoints() + ApoMarioConstants.POINTS_GOODIE_MUSHROOM);
		this.setBVisible(false);
	}
	
	public void think(int delta, ApoMarioLevel level) {
		if (!this.isBVisible()) {
			return;
		}
		if (this.bStart) {
			this.curChangeY += delta * this.getVecY();
			this.setY(this.getY() - ((float)delta * this.getVecY()));
			if (this.curChangeY > this.MAX_Y) {
				this.bStart = false;
				this.setVecY(0);
			}
		} else {
			super.think(delta, level);
			Rectangle2D.Float rec = this.getRec();
			for (int player = 0; player < level.getPlayers().size(); player++) {
				if (level.getPlayers().get(player).getRec().intersects(rec)) {
					this.catchMe(level.getPlayers().get(player));
					break;
				}
			}
			if (this.getRec().y > level.getHeight() * ApoMarioConstants.TILE_SIZE) {
				this.setBVisible(false);
			}
		}
	}
	
	public Rectangle2D.Float getRec() {
		return new Rectangle2D.Float(this.getX() + 2 * ApoMarioConstants.SIZE, this.getY(), this.getWidth() - 2 * 2 * ApoMarioConstants.SIZE, this.getHeight());
	}
	
	public Rectangle2D.Float getNextRec(int delta) {
		return new Rectangle2D.Float(this.getX() + delta * this.getVecX() + 2 * ApoMarioConstants.SIZE, this.getY() + delta * this.getVecY(), this.getWidth() - 2 * 2 * ApoMarioConstants.SIZE, this.getHeight());
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		if (super.isBVisible()) {
			super.render(g, changeX, (int)(changeY));
			if (ApoMarioConstants.DEBUG) {
				g.setColor(Color.red);
				Rectangle2D.Float rec = this.getRec();
				g.drawRect((int)(rec.x - changeX) * ApoMarioConstants.APP_SIZE, (int)(rec.y - changeY) * ApoMarioConstants.APP_SIZE, (int)rec.width * ApoMarioConstants.APP_SIZE, (int)rec.height * ApoMarioConstants.APP_SIZE);
			}
		}
	}

}