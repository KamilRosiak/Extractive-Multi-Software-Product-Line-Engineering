package apoCommando.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import apoCommando.ApoMarioConstants;
import apoCommando.level.ApoMarioLevel;

public class ApoMarioCannonCannon extends ApoMarioEnemy {
	
	public ApoMarioCannonCannon(BufferedImage animation, float x, float y, int id) {
		super(animation, x, y, animation.getWidth(), animation.getHeight(), 1, 99999999, ApoMarioConstants.POINTS_ENEMY_CANNON, id);
	}
	
	public void init() {
		super.init();
	}
	
	/**
	 * gibt das aktuelle Rechteck der Entity zurück
	 * @return gibt das aktuelle Rechteck der Entity zurück
	 */
	public Rectangle2D.Float getRec() {
		return new Rectangle2D.Float(this.getX(), this.getY() + 4 * ApoMarioConstants.SIZE, this.getWidth(), this.getHeight() - 8 * ApoMarioConstants.SIZE);
	}
	
	/**
	 * gibt das Rechteck für den nächsten Schritt der Entity zurück
	 * @return gibt das Rechteck für den nächsten Schritt der Entity zurück
	 */
	public Rectangle2D.Float getNextRec(int delta) {
		return new Rectangle2D.Float(this.getX() + delta * this.getVelocityX(), this.getY() + delta * this.getVelocityY() + 4 * ApoMarioConstants.SIZE, this.getWidth(), this.getHeight() - 8 * ApoMarioConstants.SIZE);
	}
	
	public void think(int delta, ApoMarioLevel level) {
		super.setLevel(level);
		if (!this.isBVisible()) {
			return;
		}
		if (this.isBDie()) {
			super.think(delta, level);
		} else {
			this.setX(this.getX() + this.getVelocityX() * delta);
			this.thinkJump(delta, level);
		}
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		super.render(g, changeX, (int)(changeY - this.getChangeY()));		
		if (ApoMarioConstants.DEBUG) {
			g.setColor(Color.red);
			Rectangle2D.Float rec = this.getRec();
			g.drawRect((int)(rec.x - changeX), (int)(rec.y - changeY + this.getChangeY()), (int)rec.width, (int)rec.height);
		}
	}

}
