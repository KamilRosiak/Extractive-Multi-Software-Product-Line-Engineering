package apoSimple.entity.animation;

import java.awt.Graphics2D;
import java.util.ArrayList;

import apoSimple.ApoSimpleImages;
import apoSimple.entity.ApoSimpleAnimation;
import apoSimple.entity.ApoSimpleAnimationHelp;

public class ApoSimpleAnimationYawn extends ApoSimpleAnimation {

	public ApoSimpleAnimationYawn(final boolean bBlack) {
		super(bBlack);
		
		ArrayList<ApoSimpleAnimationHelp> animation = new ArrayList<ApoSimpleAnimationHelp>();
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_DOWN_YAWN_0, 100));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_DOWN_YAWN_1, 300));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_DOWN_YAWN_2, 300));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_DOWN_YAWN_3, 1000));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_DOWN_YAWN_2, 200));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_DOWN_YAWN_4, 300));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_DOWN_YAWN_5, 400));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_DOWN_YAWN_6, 400));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_DOWN_YAWN_5, 400));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_DOWN_YAWN_6, 400));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_DOWN_YAWN_5, 400));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_DOWN_YAWN_7, 100));

		super.setAnimation(animation);
	}

	@Override
	public void render(Graphics2D g, int x, int y, int changeX, int changeY) {
		if (super.getCurrentHelp() != null) {
			int image = super.getCurrentHelp().getImage();
			if (image == ApoSimpleAnimation.ANIMATION_DOWN_YAWN_0) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_DOWN, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ORIGINAL_DOWN, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_DOWN_YAWN_1) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_YAWN_BLACK_1, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_YAWN_WHITE_1, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_DOWN_YAWN_2) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_YAWN_BLACK_2, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_YAWN_WHITE_2, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_DOWN_YAWN_3) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_YAWN_BLACK_3, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_YAWN_WHITE_3, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_DOWN_YAWN_4) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_YAWN_BLACK_4, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_YAWN_WHITE_4, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_DOWN_YAWN_5) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_YAWN_BLACK_5, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_YAWN_WHITE_5, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_DOWN_YAWN_6) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_YAWN_BLACK_6, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_YAWN_WHITE_6, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_DOWN_YAWN_7) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_BLINK_BLACK_1, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_BLINK_WHITE_1, (int)(x - changeX), (int)(y - changeY), null);
				}
			}
		}
	}

}
