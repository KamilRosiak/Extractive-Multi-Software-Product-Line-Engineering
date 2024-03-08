package apoSkunkman.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import apoSkunkman.ApoSkunkmanConstants;

/**
 * Klasse, die einen Feuertile repräsentiert<br />
 * Sie beinhaltet eine Variable für die Zeit<br />
 * welche angibt, wie lange das Tile noch angezeigt wird<br />
 * @author Dirk Aporius
 *
 */
public class ApoSkunkmanFire extends ApoSkunkmanEntity {
	/** Variable für die Zeit, wie lange das Tile noch angezeigt werden soll */
	private int time;
	
	public ApoSkunkmanFire(BufferedImage pic, float x, float y, float width, float height) {
		super(pic, x, y, width, height);
	}
	
	public void init() {
		super.init();
		this.time = 0;
	}
	
	public void think(int delta) {
		super.think(delta);
		if (this.isBVisible()) {
			// zähle Zeit raus und wenn maximale Zeit erreicht, dann setzte die Entity auf unsichtbar
			this.time += delta;
			if (this.time >= ApoSkunkmanConstants.FIRE_MAX_SHOWTIME) {
				this.setBVisible(false);
			}
		}
	}
	
	public void render(Graphics2D g, int x, int y) {
		if (super.isBVisible()) {
			if (super.getIBackground() != null) {
				g.drawImage(super.getIBackground(), (int)(this.getX() + x), (int)(this.getY() + y), null);				
			}
		}
	}

}
