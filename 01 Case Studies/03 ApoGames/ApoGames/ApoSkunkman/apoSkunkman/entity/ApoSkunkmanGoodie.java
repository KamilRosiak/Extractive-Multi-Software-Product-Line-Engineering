package apoSkunkman.entity;

import java.awt.image.BufferedImage;

import apoSkunkman.ApoSkunkmanConstants;

/**
 * Klasse, die ein Goodie repr�sentiert<br />
 * Sie beinhaltet eine Variable welches Goodie es ist<br />
 * und die Zeit wie lange das Goodie noch angezeigt werden soll<br />
 * @author Dirk Aporius
 *
 */
public class ApoSkunkmanGoodie extends ApoSkunkmanEntity {
	/** Variable, welche angibt, welches Goodie es repr�sentiert */
	private final int goodie;
	/** Variable f�r die Zeit, wie lange das Tile noch angezeigt werden soll */
	private int time;
	
	public ApoSkunkmanGoodie(BufferedImage animation, float x, float y, float width, float height, int goodie) {
		super(animation, x, y, width, height, ApoSkunkmanConstants.GOODIE_TILES, ApoSkunkmanConstants.GOODIE_ANIMATION_TIME, 1, false);
		
		this.goodie = goodie;
	}

	public void init() {
		super.init();
		this.time = 0;
	}
	
	/**
	 * gibt die Zeit zur�ck, wielange das Goodie noch sichtbar ist
	 * @return gibt die Zeit zur�ck, wielange das Goodie noch sichtbar ist
	 */
	public int getTimeLeftVisible() {
		return ApoSkunkmanConstants.GOODIE_MAX_SHOWTIME - this.time;
	}

	/**
	 * gibt das Goodie zur�ck
	 * @return gibt das Goodie zur�ck
	 */
	public final int getGoodie() {
		return this.goodie;
	}
	
	public void think(int delta) {
		super.think(delta);
		if (this.isBVisible()) {
			// z�hle Zeit raus und wenn maximale Zeit erreicht, dann setzte die Entity auf unsichtbar
			this.time += delta;
			if (this.time >= ApoSkunkmanConstants.GOODIE_MAX_SHOWTIME) {
				this.setBVisible(false);
			}
		}
	}

}
