package apoPongBeat.entity;

import java.awt.Color;

import org.apogames.help.ApoHelp;

import apoPongBeat.ApoPongBeatConstants;

public class ApoPongBeatBallMedium extends ApoPongBeatBall {

	public final static float MY_VEC_X = -0.45f;
	private final static float MY_VEC_Y = 0.2f;
	
	private final static int POINTS = 55;
	
	private static final Color COLOR_EASY = new Color(255, 255, 10);
	private static final Color COLOR_MEDIUM = new Color(249, 160, 10);

	public ApoPongBeatBallMedium(float y, float width, float height, boolean bBoth) {
		super(ApoPongBeatConstants.GAME_WIDTH - 1, y, width, height, 0, 0, ApoPongBeatBallMedium.COLOR_EASY, ApoPongBeatBallMedium.POINTS);
		float vecX = (float)(ApoPongBeatBallMedium.MY_VEC_X + Math.random() * 0.15f);
		this.setVelocityX(vecX);
		if (bBoth) {
			if (ApoHelp.getRandomValue(100, 0) > 50) {
				this.setVelocityY(ApoPongBeatBallMedium.MY_VEC_Y);
			} else {
				this.setVelocityY(-ApoPongBeatBallMedium.MY_VEC_Y);
			}
			this.setColor(ApoPongBeatBallMedium.COLOR_MEDIUM);
		}
	}
	
	public ApoPongBeatBallMedium(float y, float width, float height, float vecX, boolean bBoth, boolean bBorder) {
		super(ApoPongBeatConstants.GAME_WIDTH - 1, y, width, height, vecX, 0, ApoPongBeatBallMedium.COLOR_EASY, ApoPongBeatBallMedium.POINTS);
		if (bBoth) {
			if (ApoHelp.getRandomValue(100, 0) > 50) {
				this.setVelocityY(ApoPongBeatBallMedium.MY_VEC_Y);
			} else {
				this.setVelocityY(-ApoPongBeatBallMedium.MY_VEC_Y);
			}
			this.setColor(ApoPongBeatBallMedium.COLOR_MEDIUM);
		}
		this.setBBorder(bBorder);
	}

}
