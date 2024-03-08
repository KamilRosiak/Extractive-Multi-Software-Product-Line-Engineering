package apoPongBeat.entity;

import java.awt.Color;

import apoPongBeat.ApoPongBeatConstants;
import apoPongBeat.game.ApoPongBeatPanel;

public class ApoPongBeatBallGravity extends ApoPongBeatBall {

	private final static float MY_VEC_X = -0.35f;
	
	private int time;
	
	private static final int POINTS = 65;

	private boolean bAbove;
	
	private float addGravity = 0.001f;

	public ApoPongBeatBallGravity(float y, float width, float height, int time, boolean bAbove) {
		super(ApoPongBeatConstants.GAME_WIDTH, y, width, height, 0, 0, new Color(0, 200, 200), ApoPongBeatBallGravity.POINTS);
		
		float vecX = (float)(ApoPongBeatBallGravity.MY_VEC_X + Math.random() * 0.1f);
		this.setVelocityX(vecX);
		
		this.time = time;
		this.bAbove = bAbove;
	}
	
	public ApoPongBeatBallGravity(float y, float width, float height, float vecX, int time, boolean bAbove) {
		super(ApoPongBeatConstants.GAME_WIDTH, y, width, height, vecX, 0, new Color(138, 0, 255), ApoPongBeatBallGravity.POINTS);
		
		this.time = time;
		this.bAbove = bAbove;
	}
	
	public void think(int delta, ApoPongBeatPanel panel) {
		if (this.time > 0) {
			this.time -= delta;
		} else {
			this.setX(this.getX() + this.getVelocityX() * delta);

			if (this.bAbove) {
				this.setVelocityY(this.getVelocityY() - this.addGravity*delta);
			} else {
				this.setVelocityY(this.getVelocityY() + this.addGravity*delta);
			}
			this.setY(this.getY() + this.getVelocityY() * delta);
			
			this.checkPosition(delta, panel);
		}
	}

}
