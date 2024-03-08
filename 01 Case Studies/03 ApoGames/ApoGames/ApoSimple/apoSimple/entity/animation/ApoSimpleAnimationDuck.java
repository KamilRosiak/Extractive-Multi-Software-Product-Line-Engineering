package apoSimple.entity.animation;

import java.awt.Graphics2D;
import java.util.ArrayList;

import apoSimple.ApoSimpleImages;
import apoSimple.entity.ApoSimpleAnimation;
import apoSimple.entity.ApoSimpleAnimationHelp;

public class ApoSimpleAnimationDuck extends ApoSimpleAnimation {
	
	public ApoSimpleAnimationDuck() {
		super(false);
		
		ArrayList<ApoSimpleAnimationHelp> animation = new ArrayList<ApoSimpleAnimationHelp>();
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_DUCK_0, 100));
		
		int count = (int)(Math.random() * 3) + 1;
		for (int i = 0; i < count; i++) {
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_DUCK_1, 150));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_DUCK_2, 150));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_DUCK_3, 150));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_DUCK_4, 150));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_DUCK_3, 150));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_DUCK_2, 150));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_DUCK_1, 150));
		}

		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_DUCK_0, 150));
		
		super.setAnimation(animation);
	}

	@Override
	public void render(Graphics2D g, int x, int y, int changeX, int changeY) {
		if (super.getCurrentHelp() != null) {
			int image = super.getCurrentHelp().getImage();
			if (image == ApoSimpleAnimation.ANIMATION_DUCK_0) {
				g.drawImage(ApoSimpleImages.HIDDEN_DUCK, (int)(x - changeX), (int)(y - changeY), null);
			} else if (image == ApoSimpleAnimation.ANIMATION_LOOK_1) {
				g.drawImage(ApoSimpleImages.ANIMATION_DUCK_1, (int)(x - changeX), (int)(y - changeY), null);
			} else if (image == ApoSimpleAnimation.ANIMATION_DUCK_2) {
				g.drawImage(ApoSimpleImages.ANIMATION_DUCK_2, (int)(x - changeX), (int)(y - changeY), null);
			} else if (image == ApoSimpleAnimation.ANIMATION_DUCK_3) {
				g.drawImage(ApoSimpleImages.ANIMATION_DUCK_3, (int)(x - changeX), (int)(y - changeY), null);
			} else if (image == ApoSimpleAnimation.ANIMATION_DUCK_4) {
				g.drawImage(ApoSimpleImages.ANIMATION_DUCK_4, (int)(x - changeX), (int)(y - changeY), null);
			}
		}
	}

}
