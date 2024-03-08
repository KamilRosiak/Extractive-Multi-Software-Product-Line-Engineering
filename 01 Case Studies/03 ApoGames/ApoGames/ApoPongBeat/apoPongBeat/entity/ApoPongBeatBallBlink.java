package apoPongBeat.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import org.apogames.help.ApoHelp;

import apoPongBeat.ApoPongBeatConstants;
import apoPongBeat.game.ApoPongBeatPanel;

public class ApoPongBeatBallBlink extends ApoPongBeatBall {

	public final static float MY_VEC_X = -0.4f;
	public final static float MY_VEC_Y = -0.35f;
	
	private final static int POINTS = 75;
	
	private final int TIME_CHANGE = 300;
	
	private boolean bShow;
	
	private int time;
	
	public ApoPongBeatBallBlink(float y, float width, float height, boolean bBoth) {
		super(ApoPongBeatConstants.GAME_WIDTH - 1, y, width, height, 0, 0, new Color(8, 163, 8), ApoPongBeatBallBlink.POINTS);
		float vecX = (float)(ApoPongBeatBallBlink.MY_VEC_X + Math.random() * 0.1f);
		this.setVelocityX(vecX);
		if (bBoth) {
			float vecY = (float)(ApoPongBeatBallBlink.MY_VEC_Y + Math.random() * 0.05f);
			if (ApoHelp.getRandomValue(100, 0) > 50) {
				vecY = -vecY;
			}
			this.setVelocityY(vecY);
		}
	}
	
	public ApoPongBeatBallBlink(float y, float width, float height, float vecX, float vecY, boolean bBorder) {
		super(ApoPongBeatConstants.GAME_WIDTH - 1, y, width, height, vecX, vecY, new Color(8, 163, 8), ApoPongBeatBallBlink.POINTS);
		
		this.setBBorder(bBorder);
	}
	
	public void think(int delta, ApoPongBeatPanel panel) {
		super.think(delta, panel);
		this.time += delta;
		if (this.time >= this.TIME_CHANGE) {
			this.time -= this.TIME_CHANGE;
			this.bShow = !this.bShow;
		}
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		if (this.bShow) {
			super.render(g, changeX, changeY);
		}
	}

}
