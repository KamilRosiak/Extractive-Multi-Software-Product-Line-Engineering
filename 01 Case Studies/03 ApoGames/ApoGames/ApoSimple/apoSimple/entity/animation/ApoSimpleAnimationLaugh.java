package apoSimple.entity.animation;

import java.awt.Graphics2D;
import java.util.ArrayList;

import apoSimple.ApoSimpleConstants;
import apoSimple.ApoSimpleImages;
import apoSimple.entity.ApoSimpleAnimation;
import apoSimple.entity.ApoSimpleAnimationHelp;

public class ApoSimpleAnimationLaugh  extends ApoSimpleAnimation {

	private int direction;
	
	public ApoSimpleAnimationLaugh(final boolean bBlack, final int direction) {
		super(bBlack);
		
		this.direction = direction;
		if (this.direction >= ApoSimpleConstants.DOUBLE_UP) {
			this.direction -= 13;
		}
		
		ArrayList<ApoSimpleAnimationHelp> animation = new ArrayList<ApoSimpleAnimationHelp>();
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_LAUGH_0, 100));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_LAUGH_1, 200));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_LAUGH_2, 200));
		
		int count = (int)(Math.random() * 3) + 1;
		for (int i = 0; i < count; i++) {
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_LAUGH_3, 80));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_LAUGH_2, 250));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_LAUGH_3, 80));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_LAUGH_2, 500));
		}
		
		int random = (int)(Math.random() * 100);
		if (random < 50) {
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_LAUGH_4, 400));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_LAUGH_5, 80));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_LAUGH_4, 400));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_LAUGH_5, 80));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_LAUGH_4, 500));
			if (random < 25) {
				this.addAnimationRedLaugh(animation);
			}
		} else if (random < 75) {
			this.addAnimationRedLaugh(animation);
		}

		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_LAUGH_2, 200));
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_LAUGH_1, 200));

		super.setAnimation(animation);
	}
	
	private void addAnimationRedLaugh(ArrayList<ApoSimpleAnimationHelp> animation) {
		int count = (int)(Math.random() * 2) + 1;
		for (int i = 0; i < count; i++) {
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_LAUGH_6, 400));
			animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_LAUGH_7, 80));
		}
		animation.add(new ApoSimpleAnimationHelp(ApoSimpleAnimation.ANIMATION_LAUGH_6, 400));
	}

	@Override
	public void render(Graphics2D g, int x, int y, int changeX, int changeY) {
		if (super.getCurrentHelp() != null) {
			int image = super.getCurrentHelp().getImage();
			if (this.direction == ApoSimpleConstants.RIGHT) {
				if (image == ApoSimpleAnimation.ANIMATION_LAUGH_0) {
					if (this.isBBlack()) {
						g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_LEFT, (int)(x - changeX), (int)(y - changeY), null);					
					} else {
						g.drawImage(ApoSimpleImages.ORIGINAL_LEFT, (int)(x - changeX), (int)(y - changeY), null);
					}
				} else if (image == ApoSimpleAnimation.ANIMATION_LAUGH_1) {
					if (this.isBBlack()) {
						g.drawImage(ApoSimpleImages.ANIMATION_LAUGH_BLACK_1, (int)(x - changeX), (int)(y - changeY), null);					
					} else {
						g.drawImage(ApoSimpleImages.ANIMATION_LAUGH_WHITE_1, (int)(x - changeX), (int)(y - changeY), null);
					}
				} else if (image == ApoSimpleAnimation.ANIMATION_LAUGH_2) {
					if (this.isBBlack()) {
						g.drawImage(ApoSimpleImages.ANIMATION_LAUGH_BLACK_2, (int)(x - changeX), (int)(y - changeY), null);					
					} else {
						g.drawImage(ApoSimpleImages.ANIMATION_LAUGH_WHITE_2, (int)(x - changeX), (int)(y - changeY), null);
					}
				} else if (image == ApoSimpleAnimation.ANIMATION_LAUGH_3) {
					if (this.isBBlack()) {
						g.drawImage(ApoSimpleImages.ANIMATION_LAUGH_BLACK_3, (int)(x - changeX), (int)(y - changeY), null);					
					} else {
						g.drawImage(ApoSimpleImages.ANIMATION_LAUGH_WHITE_3, (int)(x - changeX), (int)(y - changeY), null);
					}
				} else if (image == ApoSimpleAnimation.ANIMATION_LAUGH_4) {
					if (this.isBBlack()) {
						g.drawImage(ApoSimpleImages.ANIMATION_LAUGH_BLACK_4, (int)(x - changeX), (int)(y - changeY), null);					
					} else {
						g.drawImage(ApoSimpleImages.ANIMATION_LAUGH_WHITE_4, (int)(x - changeX), (int)(y - changeY), null);
					}
				} else if (image == ApoSimpleAnimation.ANIMATION_LAUGH_5) {
					if (this.isBBlack()) {
						g.drawImage(ApoSimpleImages.ANIMATION_LAUGH_BLACK_5, (int)(x - changeX), (int)(y - changeY), null);					
					} else {
						g.drawImage(ApoSimpleImages.ANIMATION_LAUGH_WHITE_5, (int)(x - changeX), (int)(y - changeY), null);
					}
				} else if (image == ApoSimpleAnimation.ANIMATION_LAUGH_6) {
					if (this.isBBlack()) {
						g.drawImage(ApoSimpleImages.ANIMATION_LAUGH_BLACK_6, (int)(x - changeX), (int)(y - changeY), null);					
					} else {
						g.drawImage(ApoSimpleImages.ANIMATION_LAUGH_WHITE_6, (int)(x - changeX), (int)(y - changeY), null);
					}
				} else if (image == ApoSimpleAnimation.ANIMATION_LAUGH_7) {
					if (this.isBBlack()) {
						g.drawImage(ApoSimpleImages.ANIMATION_LAUGH_BLACK_7, (int)(x - changeX), (int)(y - changeY), null);					
					} else {
						g.drawImage(ApoSimpleImages.ANIMATION_LAUGH_WHITE_7, (int)(x - changeX), (int)(y - changeY), null);
					}
				}
			} else if (this.direction == ApoSimpleConstants.LEFT) {
				if (image == ApoSimpleAnimation.ANIMATION_LAUGH_0) {
					if (this.isBBlack()) {
						g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_RIGHT, (int)(x - changeX), (int)(y - changeY), null);					
					} else {
						g.drawImage(ApoSimpleImages.ORIGINAL_RIGHT, (int)(x - changeX), (int)(y - changeY), null);
					}
				} else if (image == ApoSimpleAnimation.ANIMATION_LAUGH_1) {
					if (this.isBBlack()) {
						g.drawImage(ApoSimpleImages.ANIMATION_LAUGH_RIGHT_BLACK_1, (int)(x - changeX), (int)(y - changeY), null);					
					} else {
						g.drawImage(ApoSimpleImages.ANIMATION_LAUGH_RIGHT_WHITE_1, (int)(x - changeX), (int)(y - changeY), null);
					}
				} else if (image == ApoSimpleAnimation.ANIMATION_LAUGH_2) {
					if (this.isBBlack()) {
						g.drawImage(ApoSimpleImages.ANIMATION_LAUGH_RIGHT_BLACK_2, (int)(x - changeX), (int)(y - changeY), null);					
					} else {
						g.drawImage(ApoSimpleImages.ANIMATION_LAUGH_RIGHT_WHITE_2, (int)(x - changeX), (int)(y - changeY), null);
					}
				} else if (image == ApoSimpleAnimation.ANIMATION_LAUGH_3) {
					if (this.isBBlack()) {
						g.drawImage(ApoSimpleImages.ANIMATION_LAUGH_RIGHT_BLACK_3, (int)(x - changeX), (int)(y - changeY), null);					
					} else {
						g.drawImage(ApoSimpleImages.ANIMATION_LAUGH_RIGHT_WHITE_3, (int)(x - changeX), (int)(y - changeY), null);
					}
				} else if (image == ApoSimpleAnimation.ANIMATION_LAUGH_4) {
					if (this.isBBlack()) {
						g.drawImage(ApoSimpleImages.ANIMATION_LAUGH_RIGHT_BLACK_4, (int)(x - changeX), (int)(y - changeY), null);					
					} else {
						g.drawImage(ApoSimpleImages.ANIMATION_LAUGH_RIGHT_WHITE_4, (int)(x - changeX), (int)(y - changeY), null);
					}
				} else if (image == ApoSimpleAnimation.ANIMATION_LAUGH_5) {
					if (this.isBBlack()) {
						g.drawImage(ApoSimpleImages.ANIMATION_LAUGH_RIGHT_BLACK_5, (int)(x - changeX), (int)(y - changeY), null);					
					} else {
						g.drawImage(ApoSimpleImages.ANIMATION_LAUGH_RIGHT_WHITE_5, (int)(x - changeX), (int)(y - changeY), null);
					}
				} else if (image == ApoSimpleAnimation.ANIMATION_LAUGH_6) {
					if (this.isBBlack()) {
						g.drawImage(ApoSimpleImages.ANIMATION_LAUGH_RIGHT_BLACK_6, (int)(x - changeX), (int)(y - changeY), null);					
					} else {
						g.drawImage(ApoSimpleImages.ANIMATION_LAUGH_RIGHT_WHITE_6, (int)(x - changeX), (int)(y - changeY), null);
					}
				} else if (image == ApoSimpleAnimation.ANIMATION_LAUGH_7) {
					if (this.isBBlack()) {
						g.drawImage(ApoSimpleImages.ANIMATION_LAUGH_RIGHT_BLACK_7, (int)(x - changeX), (int)(y - changeY), null);					
					} else {
						g.drawImage(ApoSimpleImages.ANIMATION_LAUGH_RIGHT_WHITE_7, (int)(x - changeX), (int)(y - changeY), null);
					}
				}
			}
		}
	}

}
