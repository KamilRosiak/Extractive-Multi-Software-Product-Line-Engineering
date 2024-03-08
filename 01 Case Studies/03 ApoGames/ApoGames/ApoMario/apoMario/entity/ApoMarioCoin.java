package apoMario.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import apoMario.ApoMarioConstants;
import apoMario.level.ApoMarioLevel;

/**
 * Klasse, die eine Münze darstellt
 * @author Dirk Aporius
 *
 */
public class ApoMarioCoin extends ApoMarioEnemy {
	
	private float curY;
	
	public ApoMarioCoin(BufferedImage animation, BufferedImage iParticle, float x, float y, int tiles, long time, int id) {
		super(animation, x, y, animation.getWidth()/tiles / ApoMarioConstants.APP_SIZE, animation.getHeight() / ApoMarioConstants.APP_SIZE, tiles, time, ApoMarioConstants.POINTS_COIN, id);
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
				if (ApoMarioConstants.DEBUG) {
					g.setColor(Color.red);
					Rectangle2D.Float rec = this.getRec();
					g.drawRect((int)(rec.x - changeX) * ApoMarioConstants.APP_SIZE, (int)(rec.y - changeY + this.getChangeY()) * ApoMarioConstants.APP_SIZE, (int)rec.width * ApoMarioConstants.APP_SIZE, (int)rec.height * ApoMarioConstants.APP_SIZE);
				}
			}
		} catch (Exception ex) {
		}
	}

	@Override
	public void coinCheck(ApoMarioLevel level, int x, int y) {
	}

	@Override
	public void xCheckChange(ApoMarioLevel level, int x, int y) {
	}

	@Override
	public void yCheckUp(ApoMarioLevel level, int x, int y) {
	}

	@Override
	public void yDownCheck(ApoMarioLevel level, int x, int y, int delta) {
	}
	
}