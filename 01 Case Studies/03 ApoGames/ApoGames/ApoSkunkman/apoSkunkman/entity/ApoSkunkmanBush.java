package apoSkunkman.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Klasse, die einen Busch repr�sentiert<br />
 * Sie beinhaltet eine Variable f�r ein Goodie<br />
 * welches angibt, ob Sie beim Entfernen des Busches ein Goodie hinterl�sst oder nicht<br />
 * @author Dirk Aporius
 *
 */
public class ApoSkunkmanBush extends ApoSkunkmanEntity {
	/** m�gliches Goodie, welches erscheint, wenn der Busch entfernt wird */
	private int goodie;
	
	public ApoSkunkmanBush(BufferedImage pic, float x, float y, float width, float height, int goodie) {
		super(pic, x, y, width, height);
		
		this.goodie = goodie;
	}

	/**
	 * gibt das m�gliche Goodie, welches erscheint, wenn der Busch entfernt wird, zur�ck
	 * @return gibt das m�gliche Goodie, welches erscheint, wenn der Busch entfernt wird, zur�ck
	 */
	public final int getGoodie() {
		return this.goodie;
	}
	
	/**
	 * setzt das Goodie auf den �bergebenen Wert<br />
	 * ist �berhaupt nur erlaubt um im Editor zwischen den Goodies zu w�hlen<br />
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
