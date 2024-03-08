package apoSimple.entity.animation;

import java.awt.Graphics2D;
import java.util.ArrayList;

import apoSimple.ApoSimpleImages;
import apoSimple.entity.ApoSimpleAnimation;
import apoSimple.entity.ApoSimpleAnimationHelp;

public class ApoSimpleAnimationTail extends ApoSimpleAnimation {

	public ApoSimpleAnimationTail(final boolean bBlack) {
		super(bBlack);
		
		ArrayList<ApoSimpleAnimationHelp> animation = new ArrayList<ApoSimpleAnimationHelp>();
		int count = (int)(Math.random() * 3) + 1;
		for (int i = 0; i < count; i++) {
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_UP_TAIL_0, 150 + (int)(Math.random() * 2000)));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_UP_TAIL_1, 150));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_UP_TAIL_0, 150));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_UP_TAIL_2, 150));
		}
		
		super.setAnimation(animation);
	}

	@Override
	public void render(Graphics2D g, int x, int y, int changeX, int changeY) {
		if (super.getCurrentHelp() != null) {
			int image = super.getCurrentHelp().getImage();
			if (image == ApoSimpleAnimation.ANIMATION_UP_TAIL_0) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_UP, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ORIGINAL_ABOVE, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_UP_TAIL_1) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_TAIL_BLACK_1, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_TAIL_WHITE_1, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_UP_TAIL_2) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_TAIL_BLACK_2, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_TAIL_WHITE_2, (int)(x - changeX), (int)(y - changeY), null);
				}
			}
		}
	}

}
