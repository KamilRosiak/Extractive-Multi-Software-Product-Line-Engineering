package apoMario.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import apoMario.ApoMarioConstants;
import apoMario.level.ApoMarioLevel;

public class ApoMarioCannonCannon extends ApoMarioEnemy {
	
	public ApoMarioCannonCannon(BufferedImage animation, float x, float y, int id) {
		super(animation, x, y, animation.getWidth() / ApoMarioConstants.APP_SIZE, animation.getHeight() / ApoMarioConstants.APP_SIZE, 1, 99999999, ApoMarioConstants.POINTS_ENEMY_CANNON, id);
	}
	
	public void init() {
		super.init();
	}
	
	/**
	 * gibt das aktuelle Rechteck der Entity zurück
	 * @return gibt das aktuelle Rechteck der Entity zurück
	 */
	public Rectangle2D.Float getRec() {
		this.getRectangle().x = this.getX();
		this.getRectangle().y = this.getY() + 4 * ApoMarioConstants.SIZE;
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
		this.nextRec.y = this.getY() + delta * this.getVecY() + 4 * ApoMarioConstants.SIZE;
		this.nextRec.width = this.getWidth();
		this.nextRec.height = this.getHeight() - 8 * ApoMarioConstants.SIZE;
		return this.nextRec;
	}
	
	public void think(int delta, ApoMarioLevel level) {
		super.setLevel(level);
		if (!this.isBVisible()) {
			return;
		}
		if (this.isBDie()) {
			super.think(delta, level);
		} else {
			this.setX(this.getX() + this.getVecX() * delta);
			this.thinkJump(delta, level);
		}
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
