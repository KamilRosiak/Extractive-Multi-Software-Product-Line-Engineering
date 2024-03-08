package apoSimple.entity.animation;

import java.awt.Graphics2D;
import java.util.ArrayList;

import apoSimple.ApoSimpleImages;
import apoSimple.entity.ApoSimpleAnimation;
import apoSimple.entity.ApoSimpleAnimationHelp;

public class ApoSimpleAnimationRabbit extends ApoSimpleAnimation {
	
	public ApoSimpleAnimationRabbit() {
		super(false);
		
		ArrayList<ApoSimpleAnimationHelp> animation = new ArrayList<ApoSimpleAnimationHelp>();
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_RABBIT_0, 100));
		
		int count = (int)(Math.random() * 4) + 1;
		for (int i = 0; i < count; i++) {
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_RABBIT_0, 100));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_RABBIT_1, 80));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_RABBIT_0, 200));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_RABBIT_1, 80));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_RABBIT_0, 500));
		}
		
		count = (int)(Math.random() * 4) + 1;
		for (int i = 0; i < count; i++) {
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_RABBIT_2, 200));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_RABBIT_3, 200));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_RABBIT_2, 200));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_RABBIT_3, 200));
		}

		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_RABBIT_0, 250));
		
		super.setAnimation(animation);
	}

	@Override
	public void render(Graphics2D g, int x, int y, int changeX, int changeY) {
		if (super.getCurrentHelp() != null) {
			int image = super.getCurrentHelp().getImage();
			if (image == ApoSimpleAnimation.ANIMATION_RABBIT_0) {
				g.drawImage(ApoSimpleImages.HIDDEN_RABBIT, (int)(x - changeX), (int)(y - changeY), null);
			} else if (image == ApoSimpleAnimation.ANIMATION_LOOK_1) {
				g.drawImage(ApoSimpleImages.ANIMATION_RABBIT_1, (int)(x - changeX), (int)(y - changeY), null);
			} else if (image == ApoSimpleAnimation.ANIMATION_RABBIT_2) {
				g.drawImage(ApoSimpleImages.ANIMATION_RABBIT_2, (int)(x - changeX), (int)(y - changeY), null);
			} else if (image == ApoSimpleAnimation.ANIMATION_RABBIT_3) {
				g.drawImage(ApoSimpleImages.ANIMATION_RABBIT_3, (int)(x - changeX), (int)(y - changeY), null);
			}
		}
	}

}
