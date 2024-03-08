package apoSimple.entity.animation;

import java.awt.Graphics2D;
import java.util.ArrayList;

import apoSimple.ApoSimpleImages;
import apoSimple.entity.ApoSimpleAnimation;
import apoSimple.entity.ApoSimpleAnimationHelp;

public class ApoSimpleAnimationSleep extends ApoSimpleAnimation {

	public ApoSimpleAnimationSleep(final boolean bBlack) {
		super(bBlack);
		
		ArrayList<ApoSimpleAnimationHelp> animation = new ArrayList<ApoSimpleAnimationHelp>();
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_RIGHT_SLEEP_0, 100));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_RIGHT_SLEEP_1, 500));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_RIGHT_SLEEP_2, 500));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_RIGHT_SLEEP_3, 500));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_RIGHT_SLEEP_4, 300));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_RIGHT_SLEEP_5, 500));
		for (int i = 0; i < 3; i++) {
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_RIGHT_SLEEP_6, 500));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_RIGHT_SLEEP_7, 500));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_RIGHT_SLEEP_8, 500));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_RIGHT_SLEEP_9, 500));
		}
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_RIGHT_SLEEP_6, 500));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_RIGHT_SLEEP_10, 600));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_RIGHT_SLEEP_11, 800));
		
		super.setAnimation(animation);
	}

	@Override
	public void render(Graphics2D g, int x, int y, int changeX, int changeY) {
		if (super.getCurrentHelp() != null) {
			int image = super.getCurrentHelp().getImage();
			if (image == ApoSimpleAnimation.ANIMATION_RIGHT_SLEEP_0) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_RIGHT, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ORIGINAL_RIGHT, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_RIGHT_SLEEP_1) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SLEEP_BLACK_1, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SLEEP_WHITE_1, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_RIGHT_SLEEP_2) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SLEEP_BLACK_2, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SLEEP_WHITE_2, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_RIGHT_SLEEP_3) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SLEEP_BLACK_3, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SLEEP_WHITE_3, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_RIGHT_SLEEP_4) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SLEEP_BLACK_4, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SLEEP_WHITE_4, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_RIGHT_SLEEP_5) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SLEEP_BLACK_5, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SLEEP_WHITE_5, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_RIGHT_SLEEP_6) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SLEEP_BLACK_6, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SLEEP_WHITE_6, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_RIGHT_SLEEP_7) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SLEEP_BLACK_7, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SLEEP_WHITE_7, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_RIGHT_SLEEP_8) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SLEEP_BLACK_8, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SLEEP_WHITE_8, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_RIGHT_SLEEP_9) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SLEEP_BLACK_9, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SLEEP_WHITE_9, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_RIGHT_SLEEP_10) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SLEEP_BLACK_10, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SLEEP_WHITE_10, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_RIGHT_SLEEP_11) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SLEEP_BLACK_11, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SLEEP_WHITE_11, (int)(x - changeX), (int)(y - changeY), null);
				}
			}
		}
	}

}
