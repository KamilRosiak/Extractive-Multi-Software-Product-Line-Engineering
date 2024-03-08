package apoSkunkman.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Klasse, die ein Stein im Level repräsentiert
 * @author Dirk Aporius
 *
 */
public class ApoSkunkmanStone extends ApoSkunkmanEntity {

	public ApoSkunkmanStone(BufferedImage pic, float x, float y, float width, float height) {
		super(pic, x, y, width, height);
	}
	
	public void render(Graphics2D g, int x, int y) {
		if (super.isBVisible()) {
			if (super.getIBackground() != null) {
				g.drawImage(super.getIBackground(), (int)(this.getX() + x), (int)(this.getY() + y), null);				
			}
		}
	}
}
