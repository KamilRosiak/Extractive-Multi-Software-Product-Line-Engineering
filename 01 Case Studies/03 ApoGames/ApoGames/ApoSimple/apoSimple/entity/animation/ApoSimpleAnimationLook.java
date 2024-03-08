package apoSimple.entity.animation;

import java.awt.Graphics2D;
import java.util.ArrayList;

import apoSimple.ApoSimpleConstants;
import apoSimple.ApoSimpleImages;
import apoSimple.entity.ApoSimpleAnimation;
import apoSimple.entity.ApoSimpleAnimationHelp;

public class ApoSimpleAnimationLook  extends ApoSimpleAnimation {

	private int direction;
	
	public ApoSimpleAnimationLook(final boolean bBlack, final int direction) {
		super(bBlack);
		
		this.direction = direction;
		if (this.direction >= ApoSimpleConstants.DOUBLE_UP) {
			this.direction -= 13;
		}
		
		ArrayList<ApoSimpleAnimationHelp> animation = new ArrayList<ApoSimpleAnimationHelp>();
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_LOOK_0, 100));
		
		int count = (int)(Math.random() * 4) + 1;
		for (int i = 0; i < count; i++) {
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_LOOK_1, 750));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_LOOK_2, 400));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_LOOK_1, 300));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_LOOK_3, 400));
		}

		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_LOOK_1, 250));
		
		super.setAnimation(animation);
	}

	@Override
	public void render(Graphics2D g, int x, int y, int changeX, int changeY) {
		if (super.getCurrentHelp() != null) {
			int image = super.getCurrentHelp().getImage();
			if (this.direction == ApoSimpleConstants.DOWN) {
				if (image == ApoSimpleAnimation.ANIMATION_LOOK_0) {
					if (this.isBBlack()) {
						g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_DOWN, (int)(x - changeX), (int)(y - changeY), null);					
					} else {
						g.drawImage(ApoSimpleImages.ORIGINAL_DOWN, (int)(x - changeX), (int)(y - changeY), null);
					}
				} else if (image == ApoSimpleAnimation.ANIMATION_LOOK_1) {
					if (this.isBBlack()) {
						g.drawImage(ApoSimpleImages.ANIMATION_LOOK_BLACK_1, (int)(x - changeX), (int)(y - changeY), null);					
					} else {
						g.drawImage(ApoSimpleImages.ANIMATION_LOOK_WHITE_1, (int)(x - changeX), (int)(y - changeY), null);
					}
				} else if (image == ApoSimpleAnimation.ANIMATION_LOOK_2) {
					if (this.isBBlack()) {
						g.drawImage(ApoSimpleImages.ANIMATION_LOOK_BLACK_2, (int)(x - changeX), (int)(y - changeY), null);					
					} else {
						g.drawImage(ApoSimpleImages.ANIMATION_LOOK_WHITE_2, (int)(x - changeX), (int)(y - changeY), null);
					}
				} else if (image == ApoSimpleAnimation.ANIMATION_LOOK_3) {
					if (this.isBBlack()) {
						g.drawImage(ApoSimpleImages.ANIMATION_LOOK_BLACK_3, (int)(x - changeX), (int)(y - changeY), null);					
					} else {
						g.drawImage(ApoSimpleImages.ANIMATION_LOOK_WHITE_3, (int)(x - changeX), (int)(y - changeY), null);
					}
				}
			}
		}
	}

}
