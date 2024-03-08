package apoSkunkman.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Klasse, die einen Busch repräsentiert<br />
 * Sie beinhaltet eine Variable für ein Goodie<br />
 * welches angibt, ob Sie beim Entfernen des Busches ein Goodie hinterlässt oder nicht<br />
 * @author Dirk Aporius
 *
 */
public class ApoSkunkmanBush extends ApoSkunkmanEntity {
	/** mögliches Goodie, welches erscheint, wenn der Busch entfernt wird */
	private int goodie;
	
	public ApoSkunkmanBush(BufferedImage pic, float x, float y, float width, float height, int goodie) {
		super(pic, x, y, width, height);
		
		this.goodie = goodie;
	}

	/**
	 * gibt das mögliche Goodie, welches erscheint, wenn der Busch entfernt wird, zurück
	 * @return gibt das mögliche Goodie, welches erscheint, wenn der Busch entfernt wird, zurück
	 */
	public final int getGoodie() {
		return this.goodie;
	}
	
	/**
	 * setzt das Goodie auf den übergebenen Wert<br />
	 * ist überhaupt nur erlaubt um im Editor zwischen den Goodies zu wählen<br />
	 * @param goodie : neues Goodie
	 */
	public void setGoodie(int goodie) {
		this.goodie = goodie;
	}

	public void render(Graphics2D g, int x, int y) {
		if (super.isBVisible()) {
			if (super.getIBackground() != null) {
				g.drawImage(super.getIBackground(), (int)(this.getX() + x), (int)(this.getY() + y), null);				
			}
		}
	}
	
}
