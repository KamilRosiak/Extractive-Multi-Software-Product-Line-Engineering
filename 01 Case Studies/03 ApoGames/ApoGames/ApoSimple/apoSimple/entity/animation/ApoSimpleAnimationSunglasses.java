package apoSimple.entity.animation;

import java.awt.Graphics2D;
import java.util.ArrayList;

import apoSimple.ApoSimpleImages;
import apoSimple.entity.ApoSimpleAnimation;
import apoSimple.entity.ApoSimpleAnimationHelp;

public class ApoSimpleAnimationSunglasses extends ApoSimpleAnimation {

	public ApoSimpleAnimationSunglasses(final boolean bBlack) {
		super(bBlack);
		
		ArrayList<ApoSimpleAnimationHelp> animation = new ArrayList<ApoSimpleAnimationHelp>();
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_SUNGLASSES_0, 100));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_SUNGLASSES_1, 600));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_SUNGLASSES_2, 120));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_SUNGLASSES_3, 120));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_SUNGLASSES_4, 120));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_SUNGLASSES_5, 120));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_SUNGLASSES_6, 750));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_SUNGLASSES_7, 120));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_SUNGLASSES_8, 120));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_SUNGLASSES_9, 120));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_SUNGLASSES_10, 120));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_SUNGLASSES_11, 120));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_SUNGLASSES_12, 120));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_SUNGLASSES_13, 400));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_SUNGLASSES_14, 1000));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_SUNGLASSES_15, 150));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_SUNGLASSES_16, 150));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_SUNGLASSES_17, 150));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_SUNGLASSES_18, 150));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_SUNGLASSES_19, 150));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_SUNGLASSES_20, 150));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_SUNGLASSES_21, 150));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_SUNGLASSES_22, 900));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_SUNGLASSES_23, 150));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_SUNGLASSES_24, 150));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_SUNGLASSES_25, 150));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_SUNGLASSES_26, 150));
		
		super.setAnimation(animation);
	}

	@Override
	public void render(Graphics2D g, int x, int y, int changeX, int changeY) {
		if (super.getCurrentHelp() != null) {
			int image = super.getCurrentHelp().getImage();
			if (image == ApoSimpleAnimation.ANIMATION_SUNGLASSES_0) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_DOWN, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ORIGINAL_DOWN, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_SUNGLASSES_1) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_1, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_1, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_SUNGLASSES_2) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_2, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_2, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_SUNGLASSES_3) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_3, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_3, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_SUNGLASSES_4) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_4, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_4, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_SUNGLASSES_5) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_5, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_5, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_SUNGLASSES_6) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_6, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_6, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_SUNGLASSES_7) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_7, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_7, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_SUNGLASSES_8) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_8, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_8, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_SUNGLASSES_9) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_9, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_9, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_SUNGLASSES_10) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_10, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_10, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_SUNGLASSES_11) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_11, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_11, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_SUNGLASSES_12) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_12, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_12, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_SUNGLASSES_13) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_13, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_13, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_SUNGLASSES_14) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_14, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_14, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_SUNGLASSES_15) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_15, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_15, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_SUNGLASSES_16) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_16, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_16, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_SUNGLASSES_17) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_17, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_17, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_SUNGLASSES_18) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_18, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_18, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_SUNGLASSES_19) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_19, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_19, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_SUNGLASSES_20) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_20, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_20, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_SUNGLASSES_21) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_21, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_21, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_SUNGLASSES_22) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_22, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_22, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_SUNGLASSES_23) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_23, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_23, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_SUNGLASSES_24) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_24, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_24, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_SUNGLASSES_25) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_25, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_25, (int)(x - changeX), (int)(y - changeY), null);
				}
			} else if (image == ApoSimpleAnimation.ANIMATION_SUNGLASSES_26) {
				if (this.isBBlack()) {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_BLACK_26, (int)(x - changeX), (int)(y - changeY), null);					
				} else {
					g.drawImage(ApoSimpleImages.ANIMATION_SUNGLASSES_WHITE_26, (int)(x - changeX), (int)(y - changeY), null);
				}
			}
		}
	}

}
