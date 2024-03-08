package apoSimple.entity.animation;

import java.awt.Graphics2D;
import java.util.ArrayList;

import apoSimple.ApoSimpleConstants;
import apoSimple.ApoSimpleImages;
import apoSimple.entity.ApoSimpleAnimation;
import apoSimple.entity.ApoSimpleAnimationHelp;

public class ApoSimpleAnimationBlink extends ApoSimpleAnimation {

	private int direction;
	
	public ApoSimpleAnimationBlink(final boolean bBlack, final int direction) {
		super(bBlack);
		
		this.direction = direction;
		if (this.direction >= ApoSimpleConstants.DOUBLE_UP) {
			this.direction -= 13;
		}
		
		ArrayList<ApoSimpleAnimationHelp> animation = new ArrayList<ApoSimpleAnimationHelp>();
		int count = (int)(Math.random() * 3) + 1;
		for (int i = 0; i < count; i++) {
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_BLINK_0, 100));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_BLINK_1, 80));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_BLINK_0, 200));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_BLINK_1, 80));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_BLINK_0, 500));
		}
		
		super.setAnimation(animation);
	}

	@Override
	public void render(Graphics2D g, int x, int y, int changeX, int changeY) {
		if (super.getCurrentHelp() != null) {
			int image = super.getCurrentHelp().getImage();
			if (this.direction == ApoSimpleConstants.DOWN) {
				if (image == ApoSimpleAnimation.ANIMATION_BLINK_0) {
					if (this.isBBlack()) {
						g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_DOWN, (int)(x - changeX), (int)(y - changeY), null);					
					} else {
						g.drawImage(ApoSimpleImages.ORIGINAL_DOWN, (int)(x - changeX), (int)(y - changeY), null);
					}
				} else if (image == ApoSimpleAnimation.ANIMATION_BLINK_1) {
					if (this.isBBlack()) {
						g.drawImage(ApoSimpleImages.ANIMATION_BLINK_BLACK_1, (int)(x - changeX), (int)(y - changeY), null);					
					} else {
						g.drawImage(ApoSimpleImages.ANIMATION_BLINK_WHITE_1, (int)(x - changeX), (int)(y - changeY), null);
					}
				}
			} else if (this.direction == ApoSimpleConstants.LEFT) {
				if (image == ApoSimpleAnimation.ANIMATION_BLINK_0) {
					if (this.isBBlack()) {
						g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_RIGHT, (int)(x - changeX), (int)(y - changeY), null);					
					} else {
						g.drawImage(ApoSimpleImages.ORIGINAL_RIGHT, (int)(x - changeX), (int)(y - changeY), null);
					}
				} else if (image == ApoSimpleAnimation.ANIMATION_BLINK_1) {
					if (this.isBBlack()) {
						g.drawImage(ApoSimpleImages.ANIMATION_RIGHT_BLINK_BLACK_1, (int)(x - changeX), (int)(y - changeY), null);					
					} else {
						g.drawImage(ApoSimpleImages.ANIMATION_RIGHT_BLINK_WHITE_1, (int)(x - changeX), (int)(y - changeY), null);
					}
				}
			} else if (this.direction == ApoSimpleConstants.RIGHT) {
				if (image == ApoSimpleAnimation.ANIMATION_BLINK_0) {
					if (this.isBBlack()) {
						g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_LEFT, (int)(x - changeX), (int)(y - changeY), null);					
					} else {
						g.drawImage(ApoSimpleImages.ORIGINAL_LEFT, (int)(x - changeX), (int)(y - changeY), null);
					}
				} else if (image == ApoSimpleAnimation.ANIMATION_BLINK_1) {
					if (this.isBBlack()) {
						g.drawImage(ApoSimpleImages.ANIMATION_LEFT_BLINK_BLACK_1, (int)(x - changeX), (int)(y - changeY), null);					
					} else {
						g.drawImage(ApoSimpleImages.ANIMATION_LEFT_BLINK_WHITE_1, (int)(x - changeX), (int)(y - changeY), null);
					}
				}
			}
		}
	}

}
