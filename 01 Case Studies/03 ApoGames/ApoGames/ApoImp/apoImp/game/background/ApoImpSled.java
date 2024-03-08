package apoImp.game.background;

import java.awt.image.BufferedImage;

import org.apogames.entity.ApoAnimation;

public class ApoImpSled extends ApoAnimation {

	public ApoImpSled(BufferedImage iAnimation, float x, float y) {
		super(iAnimation, x, y, iAnimation.getWidth()/2, iAnimation.getHeight(), 2, 250);
	}

}
