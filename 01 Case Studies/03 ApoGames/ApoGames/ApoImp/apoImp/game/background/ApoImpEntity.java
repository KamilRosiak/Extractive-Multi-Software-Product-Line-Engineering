package apoImp.game.background;

import java.awt.image.BufferedImage;

import org.apogames.entity.ApoEntity;

public class ApoImpEntity extends ApoEntity {

	public ApoImpEntity(BufferedImage iBackground, float x, float y) {
		super(iBackground, x, y, iBackground.getWidth(), iBackground.getHeight());
	}
	
	public ApoImpEntity(BufferedImage iBackground, float x, float y, float width, float height) {
		super(iBackground, x, y, width, height);
	}

}
