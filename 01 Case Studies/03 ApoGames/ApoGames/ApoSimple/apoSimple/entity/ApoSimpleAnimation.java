package apoSimple.entity;

import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class ApoSimpleAnimation {

	public static final int ANIMATION_BLINK_0 = 0;
	public static final int ANIMATION_BLINK_1 = 1;
	
	public static final int ANIMATION_UP_TAIL_0 = 0;
	public static final int ANIMATION_UP_TAIL_1 = 1;
	public static final int ANIMATION_UP_TAIL_2 = 2;
	
	public static final int ANIMATION_DOWN_YAWN_0 = 0;
	public static final int ANIMATION_DOWN_YAWN_1 = 1;
	public static final int ANIMATION_DOWN_YAWN_2 = 2;
	public static final int ANIMATION_DOWN_YAWN_3 = 3;
	public static final int ANIMATION_DOWN_YAWN_4 = 4;
	public static final int ANIMATION_DOWN_YAWN_5 = 5;
	public static final int ANIMATION_DOWN_YAWN_6 = 6;
	public static final int ANIMATION_DOWN_YAWN_7 = 7;
	
	public static final int ANIMATION_LAUGH_0 = 0;
	public static final int ANIMATION_LAUGH_1 = 1;
	public static final int ANIMATION_LAUGH_2 = 2;
	public static final int ANIMATION_LAUGH_3 = 3;
	public static final int ANIMATION_LAUGH_4 = 4;
	public static final int ANIMATION_LAUGH_5 = 5;
	public static final int ANIMATION_LAUGH_6 = 6;
	public static final int ANIMATION_LAUGH_7 = 7;

	public static final int ANIMATION_LOOK_0 = 0;
	public static final int ANIMATION_LOOK_1 = 1;
	public static final int ANIMATION_LOOK_2 = 2;
	public static final int ANIMATION_LOOK_3 = 3;
	
	public static final int ANIMATION_RIGHT_SLEEP_0 = 0;
	public static final int ANIMATION_RIGHT_SLEEP_1 = 1;
	public static final int ANIMATION_RIGHT_SLEEP_2 = 2;
	public static final int ANIMATION_RIGHT_SLEEP_3 = 3;
	public static final int ANIMATION_RIGHT_SLEEP_4 = 4;
	public static final int ANIMATION_RIGHT_SLEEP_5 = 5;
	public static final int ANIMATION_RIGHT_SLEEP_6 = 6;
	public static final int ANIMATION_RIGHT_SLEEP_7 = 7;
	public static final int ANIMATION_RIGHT_SLEEP_8 = 8;
	public static final int ANIMATION_RIGHT_SLEEP_9 = 9;
	public static final int ANIMATION_RIGHT_SLEEP_10 = 10;
	public static final int ANIMATION_RIGHT_SLEEP_11 = 11;

	public static final int ANIMATION_SUNGLASSES_0 = 0;
	public static final int ANIMATION_SUNGLASSES_1 = 1;
	public static final int ANIMATION_SUNGLASSES_2 = 2;
	public static final int ANIMATION_SUNGLASSES_3 = 3;
	public static final int ANIMATION_SUNGLASSES_4 = 4;
	public static final int ANIMATION_SUNGLASSES_5 = 5;
	public static final int ANIMATION_SUNGLASSES_6 = 6;
	public static final int ANIMATION_SUNGLASSES_7 = 7;
	public static final int ANIMATION_SUNGLASSES_8 = 8;
	public static final int ANIMATION_SUNGLASSES_9 = 9;
	public static final int ANIMATION_SUNGLASSES_10 = 10;
	public static final int ANIMATION_SUNGLASSES_11 = 11;
	public static final int ANIMATION_SUNGLASSES_12 = 12;
	public static final int ANIMATION_SUNGLASSES_13 = 13;
	public static final int ANIMATION_SUNGLASSES_14 = 14;
	public static final int ANIMATION_SUNGLASSES_15 = 15;
	public static final int ANIMATION_SUNGLASSES_16 = 16;
	public static final int ANIMATION_SUNGLASSES_17 = 17;
	public static final int ANIMATION_SUNGLASSES_18 = 18;
	public static final int ANIMATION_SUNGLASSES_19 = 19;
	public static final int ANIMATION_SUNGLASSES_20 = 20;
	public static final int ANIMATION_SUNGLASSES_21 = 21;
	public static final int ANIMATION_SUNGLASSES_22 = 22;
	public static final int ANIMATION_SUNGLASSES_23 = 23;
	public static final int ANIMATION_SUNGLASSES_24 = 24;
	public static final int ANIMATION_SUNGLASSES_25 = 25;
	public static final int ANIMATION_SUNGLASSES_26 = 26;
	
	public static final int ANIMATION_RABBIT_0 = 0;
	public static final int ANIMATION_RABBIT_1 = 1;
	public static final int ANIMATION_RABBIT_2 = 2;
	public static final int ANIMATION_RABBIT_3 = 3;
	
	public static final int ANIMATION_DUCK_0 = 0;
	public static final int ANIMATION_DUCK_1 = 1;
	public static final int ANIMATION_DUCK_2 = 2;
	public static final int ANIMATION_DUCK_3 = 3;
	public static final int ANIMATION_DUCK_4 = 4;
	
	private boolean bFinish;
	
	private boolean bBlack;
	
	private ArrayList<ApoSimpleAnimationHelp> animation;
	
	private int curTime;
	
	private int curAnimation;
	
	public ApoSimpleAnimation(final boolean bBlack) {
		this.bBlack = bBlack;
		
		this.init();
	}
	
	public void setAnimation(final ArrayList<ApoSimpleAnimationHelp> animation) {
		this.animation = animation;
		this.init();
	}

	public void init() {
		this.curTime = 0;
		this.curAnimation = 0;
		this.bFinish = false;
	}
	
	public boolean isBBlack() {
		return this.bBlack;
	}

	public boolean isBFinish() {
		return this.bFinish;
	}

	public ApoSimpleAnimationHelp getCurrentHelp() {
		if (this.curAnimation < this.animation.size()) {
			return this.animation.get(this.curAnimation);
		}
		return null;
	}
	
	public void think(int delta) {
		this.curTime += delta;
		if (this.bFinish) {
			
		} else if (this.curTime >= this.animation.get(this.curAnimation).getTime()) {
			this.curTime = 0;
			this.curAnimation += 1;
			if (this.curAnimation >= this.animation.size()) {
				this.bFinish = true;
			}
		}
	}
	
	public abstract void render(Graphics2D g, int x, int y, int changeX, int changeY);
}
