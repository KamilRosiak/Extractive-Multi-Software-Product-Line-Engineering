package apoSimple.entity;

import apoSimple.ApoSimpleConstants;
import apoSimple.entity.animation.ApoSimpleAnimationBlink;
import apoSimple.entity.animation.ApoSimpleAnimationDuck;
import apoSimple.entity.animation.ApoSimpleAnimationLaugh;
import apoSimple.entity.animation.ApoSimpleAnimationLook;
import apoSimple.entity.animation.ApoSimpleAnimationRabbit;
import apoSimple.entity.animation.ApoSimpleAnimationSleep;
import apoSimple.entity.animation.ApoSimpleAnimationSunglasses;
import apoSimple.entity.animation.ApoSimpleAnimationTail;
import apoSimple.entity.animation.ApoSimpleAnimationYawn;

public class ApoSimpleAnimationMake {

	public static final ApoSimpleAnimation getRandomAnimation(ApoSimpleEntity entity) {
		if ((entity.getDirection() == ApoSimpleConstants.LEFT) || (entity.getDirection() == ApoSimpleConstants.DOUBLE_LEFT)) {
			int random = (int)(Math.random() * 100);
			if (entity.isBBlack()) {
				if (random < 30) {
					return new ApoSimpleAnimationSleep(true);
				} else if (random < 60) {
					return new ApoSimpleAnimationBlink(true, entity.getDirection());
				} else {
					return new ApoSimpleAnimationLaugh(true, entity.getDirection());
				}
			} else {
				if (random < 30) {
					return new ApoSimpleAnimationSleep(false);
				} else if (random < 60) {
					return new ApoSimpleAnimationBlink(false, entity.getDirection());
				} else {
					return new ApoSimpleAnimationLaugh(false, entity.getDirection());
				}
			}
		} else if ((entity.getDirection() == ApoSimpleConstants.DOWN) || (entity.getDirection() == ApoSimpleConstants.DOUBLE_DOWN)) {
			int random = (int)(Math.random() * 100);
			if (entity.isBBlack()) {
				if (random < 25) {
					return new ApoSimpleAnimationYawn(true);
				} else if (random < 50) {
					return new ApoSimpleAnimationSunglasses(true);
				} else if (random < 75) {
					return new ApoSimpleAnimationBlink(true, entity.getDirection());
				} else {
					return new ApoSimpleAnimationLook(true, entity.getDirection());
				}
			} else {
				if (random < 25) {
					return new ApoSimpleAnimationYawn(false);
				} else if (random < 50) {
					return new ApoSimpleAnimationSunglasses(false);
				} else if (random < 75) {
					return new ApoSimpleAnimationBlink(false, entity.getDirection());
				} else {
					return new ApoSimpleAnimationLook(false, entity.getDirection());
				}
			}
		} else if ((entity.getDirection() == ApoSimpleConstants.UP) || (entity.getDirection() == ApoSimpleConstants.DOUBLE_UP)) {
			int random = (int)(Math.random() * 100);
			if (entity.isBBlack()) {
				if (random < 50) {
					return new ApoSimpleAnimationTail(true);
				}
			} else {
				if (random < 50) {
					return new ApoSimpleAnimationTail(false);
				}
			}
		} else if ((entity.getDirection() == ApoSimpleConstants.RIGHT) || (entity.getDirection() == ApoSimpleConstants.DOUBLE_RIGHT)) {
			int random = (int)(Math.random() * 100);
			if (entity.isBBlack()) {
				if (random < 70) {
					return new ApoSimpleAnimationLaugh(true, entity.getDirection());
				} else {
					return new ApoSimpleAnimationBlink(true, entity.getDirection());
				}
			} else {
				if (random < 70) {
					return new ApoSimpleAnimationLaugh(false, entity.getDirection());
				} else {
					return new ApoSimpleAnimationBlink(false, entity.getDirection());
				}
			}
		} else if (entity.getDirection() == ApoSimpleConstants.HIDDEN_RABBIT) {
			int random = (int)(Math.random() * 100);
			if (random < 80) {
				return new ApoSimpleAnimationRabbit();
			}
		} else if (entity.getDirection() == ApoSimpleConstants.HIDDEN_DUCK) {
			int random = (int)(Math.random() * 100);
			if (random < 80) {
				return new ApoSimpleAnimationDuck();
			}
		}
		return null;
	}
	
}
