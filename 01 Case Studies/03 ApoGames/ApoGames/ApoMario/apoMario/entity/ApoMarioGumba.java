package apoMario.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import apoMario.ApoMarioConstants;
import apoMario.ApoMarioImageContainer;
import apoMario.level.ApoMarioLevel;

public class ApoMarioGumba extends ApoMarioEnemy {
	
	private BufferedImage iOriginal;
	
	private boolean bLeft;
	
	public ApoMarioGumba(BufferedImage animation, float x, float y,	float width, float height, int tiles, long time, boolean bFall, boolean bFly, boolean bLeft, int id) {
		super(animation, x, y, width, height, tiles, time, ApoMarioConstants.POINTS_ENEMY_GUMBA, id);
		this.iOriginal = animation;
		super.setBFlyOriginal(bFly);
		super.setBFly(bFly);
		super.setBFall(bFall);
		this.bLeft = bLeft;
		this.setBLeft(this.bLeft);
		if (this.isBFly()) {
			this.setVecY(-ApoMarioConstants.ENEMY_VEC_Y);
		}
	}
	
	public void init() {
		super.init();
		this.setBLeft(this.bLeft);
		this.setBFlyOriginal(this.isBFlyOriginal());
	}
	
	public boolean isBLeft() {
		return this.bLeft;
	}
	
	public void setBFlyOriginal(boolean bFly) {
		super.setBFlyOriginal(bFly);
		super.setBFly(bFly);
		if (bFly) {
			this.setVecY(-ApoMarioConstants.ENEMY_VEC_Y);
		} else {
			this.setVecY(0);
		}
	}
	
	public void setBLeft(boolean bLeft) {
		this.bLeft = bLeft;
		if (this.bLeft) {
			this.setVecX(-ApoMarioConstants.GUMBA_VEC_X);
		} else {
			this.setVecX(ApoMarioConstants.GUMBA_VEC_X);
		}
	}

	public void setIOriginal(BufferedImage original) {
		this.iOriginal = original;
		this.setIBackground(original);
		this.setBLeft(this.bLeft);
	}

	public void setVecX(float vecX) {
		super.setVecX(vecX);
		if (vecX < 0) {
			this.setIBackground(this.getMirrorImage(this.iOriginal));
		} else {
			this.setIBackground(this.iOriginal);
		}
	}
	
	public void think(int delta, ApoMarioLevel level) {
		if (!this.isBVisible()) {
			return;
		}
		super.think(delta, level);
		if (!this.isBDie()) {
			this.thinkJump(delta, level);
		}
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
	
	public void render(Graphics2D g, int changeX, int changeY) {
		super.render(g, changeX, (int)(changeY - this.getChangeY()));
		if (this.isBFly()) {
			Rectangle2D.Float rec = this.getRec();
			if (this.getVecX() < 0) {
				g.drawImage(this.getMirrorImage(ApoMarioImageContainer.ENEMIES.getSubimage(this.getFrame() * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 9 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE)), (int)(rec.x - changeX + rec.width/2) * ApoMarioConstants.APP_SIZE, (int)(rec.y - changeY - 1f/2f * ApoMarioConstants.TILE_SIZE) * ApoMarioConstants.APP_SIZE, null);				
			} else {
				g.drawImage(ApoMarioImageContainer.ENEMIES.getSubimage(this.getFrame() * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 9 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), (int)(rec.x - changeX - rec.width/2) * ApoMarioConstants.APP_SIZE, (int)(rec.y - changeY - 1f/2f * ApoMarioConstants.TILE_SIZE) * ApoMarioConstants.APP_SIZE, null);				
			}
		}
		if (ApoMarioConstants.DEBUG) {
			g.setColor(Color.red);
			Rectangle2D.Float rec = this.getRec();
			g.drawRect((int)(rec.x - changeX) * ApoMarioConstants.APP_SIZE, (int)(rec.y - changeY + this.getChangeY()) * ApoMarioConstants.APP_SIZE, (int)rec.width * ApoMarioConstants.APP_SIZE, (int)rec.height * ApoMarioConstants.APP_SIZE);
		}
	}
}
