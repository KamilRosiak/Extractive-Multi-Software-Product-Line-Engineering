package apoSkunkman.entity;

import java.awt.image.BufferedImage;

import org.apogames.entity.ApoAnimation;

/**
 * eigentliche Entityklasse von der alle Entities im Spiel erben
 * @author Dirk Aporius
 *
 */
public class ApoSkunkmanEntity extends ApoAnimation {

	public ApoSkunkmanEntity(BufferedImage iPic, float x, float y, float width, float height) {
		this(iPic, x, y, width, height, 1, 1000000000, 1, false);
	}
	
	public ApoSkunkmanEntity(BufferedImage animation, float x, float y, float width, float height, int tiles, long time, int maxDirections, boolean bRGB) {
		super(animation, x, y, width, height, tiles, time, maxDirections, bRGB);
	}
	
	public void setIBackground(BufferedImage background) {
		super.setIBackground(background);
		super.makeImageArray();
	}

}
