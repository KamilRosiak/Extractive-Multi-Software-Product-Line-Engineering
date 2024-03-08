package apoSimple.oneButton;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import apoSimple.ApoSimpleConstants;
import apoSimple.entity.ApoSimpleEntity;

public class ApoSimpleOneButtonEntity extends ApoSimpleEntity {

	public ApoSimpleOneButtonEntity(BufferedImage iImage, float x, float y, float width, float height) {
		super(x, y, width, height, ApoSimpleConstants.EMPTY, ApoSimpleConstants.DOG_DOWN);
		super.setIBackground(iImage);
		super.setBOpaque(true);
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		if (super.getIBackground() != null) {
			g.drawImage(this.getIBackground(), (int)(this.getX() + changeX), (int)(this.getY() + changeY), null);
		} else {
			super.render(g, changeX, changeY);
		}
	}

}
