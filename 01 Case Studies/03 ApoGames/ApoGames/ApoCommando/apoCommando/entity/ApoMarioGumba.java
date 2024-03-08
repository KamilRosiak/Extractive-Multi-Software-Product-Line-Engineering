package apoCommando.entity;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import apoCommando.ApoMarioConstants;
import apoCommando.ApoMarioImageContainer;
import apoCommando.level.ApoMarioLevel;

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
		if (this.bLeft) {
			this.setVelocityX(-ApoMarioConstants.GUMBA_VEC_X);
		} else {
			this.setVelocityX(ApoMarioConstants.GUMBA_VEC_X);
		}
		if (this.isBFly()) {
			this.setVelocityY(-ApoMarioConstants.ENEMY_VEC_Y);
		}
	}
	
	public void init() {
		super.init();
		if (this.bLeft) {
			this.setVelocityX(-ApoMarioConstants.GUMBA_VEC_X);
		} else {
			this.setVelocityX(ApoMarioConstants.GUMBA_VEC_X);
		}
	}
	
	public boolean isBLeft() {
		return this.bLeft;
	}

	public void setVelocityX(float vecX) {
		super.setVelocityX(vecX);
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
		return new Rectangle2D.Float(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}
	
	/**
	 * gibt das Rechteck für den nächsten Schritt der Entity zurück
	 * @return gibt das Rechteck für den nächsten Schritt der Entity zurück
	 */
	public Rectangle2D.Float getNextRec(int delta) {
		return new Rectangle2D.Float(this.getX() + delta * this.getVelocityX(), this.getY() + delta * this.getVelocityY(), this.getWidth(), this.getHeight());
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		super.render(g, changeX, (int)(changeY - this.getChangeY()));
		if (this.isBFly()) {
			Rectangle2D.Float rec = this.getRec();
			if (this.getVelocityX() < 0) {
				g.drawImage(this.getMirrorImage(ApoMarioImageContainer.ENEMIES.getSubimage(this.getFrame() * ApoMarioConstants.TILE_SIZE, 9 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE)), (int)(rec.x - changeX + rec.width/2), (int)(rec.y - changeY - 1f/2f * ApoMarioConstants.TILE_SIZE), null);				
			} else {
				g.drawImage(ApoMarioImageContainer.ENEMIES.getSubimage(this.getFrame() * ApoMarioConstants.TILE_SIZE, 9 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE), (int)(rec.x - changeX - rec.width/2), (int)(rec.y - changeY - 1f/2f * ApoMarioConstants.TILE_SIZE), null);				
			}
		}
	}
}
