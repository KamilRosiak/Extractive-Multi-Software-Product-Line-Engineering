package apoCommando.entity;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import apoCommando.ApoMarioConstants;
import apoCommando.level.ApoMarioLevel;

/**
 * Klasse, die eine Münze darstellt
 * @author Dirk Aporius
 *
 */
public class ApoMarioCoin extends ApoMarioEnemy {
	
	private float curY;
	
	public ApoMarioCoin(BufferedImage animation, BufferedImage iParticle, float x, float y, int tiles, long time, int id) {
		super(animation, x, y, animation.getWidth()/tiles, animation.getHeight(), tiles, time, ApoMarioConstants.POINTS_COIN, id);
	}

	public void init() {
		super.init();
		this.curY = 0;
	}

	public float getCurY() {
		return curY;
	}

	public void setCurY(float curY) {
		this.curY = curY;
	}

	public void setBVisible(boolean bVisible) {
		super.setBVisible(bVisible);
		if (!bVisible) {
			this.makeParticles(this.getX() + this.getWidth()/2, this.getY() - this.getCurY() + this.getHeight()/2, this.getWidth(), this.getHeight());
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
	
	public void think(int delta, ApoMarioLevel level) {
		if (super.isBVisible()) {
			super.setLevel(level);
			super.think(delta);
		} else {
		}
	}
	
	/**
	 * rendert den Spieler, falls er sichtbar ist hin
	 */
	public void render(Graphics2D g, int changeX, int changeY) {
		try {
			if (super.isBVisible()) {
				super.render(g, changeX, changeY);
			}
		} catch (Exception ex) {
		}
	}

	@Override
	public void coinCheck(ApoMarioLevel level, int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void xCheckChange(ApoMarioLevel level, int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void yCheckUp(ApoMarioLevel level, int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void yDownCheck(ApoMarioLevel level, int x, int y, int delta) {
		// TODO Auto-generated method stub
		
	}
	
}