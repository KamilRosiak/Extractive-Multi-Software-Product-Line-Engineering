package apoPongBeat.entity;

import java.awt.Color;

import apoPongBeat.ApoPongBeatConstants;
import apoPongBeat.game.ApoPongBeatPanel;

public class ApoPongBeatBallSinus extends ApoPongBeatBall {

	private int time;
	
	private static final int POINTS = 65;
	public int maxChange = 30;
	
	private float change;
	
	public ApoPongBeatBallSinus(float y, float width, float height, float vecX, float vecY, int time, int maxChange) {
		super(ApoPongBeatConstants.GAME_WIDTH, y, width, height, vecX, vecY, new Color(138, 0, 255), ApoPongBeatBallSinus.POINTS);
		
		this.time = time;
		this.change = 0;
		this.maxChange = maxChange;
	}
	
	public void think(int delta, ApoPongBeatPanel panel) {
		if (this.time > 0) {
			this.time -= delta;
		} else {
			this.setX(this.getX() + this.getVelocityX() * delta);
			this.change += this.getVelocityY() * delta;
			this.setY(this.getY() + this.getVelocityY() * delta);
			if (Math.abs(this.change) >= this.maxChange) {
				this.setVelocityY(-this.getVelocityY());
			}
			
			if ((this.getX() + this.getWidth() < 0) && (this.getVelocityX() < 0)) {
				this.setBVisible(false);
				panel.ballOnBorder(true);
				panel.getSounds().playSound("death");
				ApoPongBeatParticle.makeParticle(this, panel, ApoPongBeatConstants.PARTICLE_TIME, ApoPongBeatConstants.PARTICLE_WIDTH);
			} else if ((this.getX() > ApoPongBeatConstants.GAME_WIDTH) && (this.getVelocityX() > 0)) {
				this.setBVisible(false);
				panel.ballOnBorder(false);
				panel.getParticle().add(new ApoPongBeatParticleString(this.getX(), this.getY() + this.getHeight(), this.getColor(), ApoPongBeatConstants.PARTICLE_STRING_TIME, "GREAT", panel.getMyFont(), true));
			}
			
			if (this.getY() <= ApoPongBeatConstants.MIN_Y) {
				this.setY(ApoPongBeatConstants.MIN_Y + 1);
				this.setVelocityY(-this.getVelocityY());
				this.change = this.maxChange;
				panel.getSounds().playSound("bounce");
				if (panel.getMult() > 1) {
					ApoPongBeatParticle.makeParticle(this, panel, ApoPongBeatConstants.PARTICLE_TIME, ApoPongBeatConstants.PARTICLE_WIDTH);
				}
			} else if (this.getY() + this.getHeight() > ApoPongBeatConstants.MAX_Y) {
				this.setY(ApoPongBeatConstants.MAX_Y - this.getHeight() - 1);
				this.setVelocityY(-this.getVelocityY());
				this.change = this.maxChange;
				panel.getSounds().playSound("bounce");
				if (panel.getMult() > 1) {
					ApoPongBeatParticle.makeParticle(this, panel, ApoPongBeatConstants.PARTICLE_TIME, ApoPongBeatConstants.PARTICLE_WIDTH);
				}
			}
			
			if (panel.getRacket().intersects(this)) {
				if (panel.getRacket().getX() < this.getX()) {
					panel.getParticle().add(new ApoPongBeatParticleString(this.getX(), this.getY() + this.getHeight(), this.getColor(), ApoPongBeatConstants.PARTICLE_POINTS_STRING_TIME, String.valueOf(this.getPoints() * panel.getMult() + panel.getCounter()), panel.getPointsFont(), false));
					panel.ballOnRacket(this.getPoints());
					panel.getSounds().playSound("pong");

					this.setBRebound(true);
					this.setX(panel.getRacket().getX() + panel.getRacket().getWidth());
					this.setVelocityX(-this.getVelocityX());
					if (panel.getMult() > 1) {
						ApoPongBeatParticle.makeParticle(this, panel, ApoPongBeatConstants.PARTICLE_TIME, ApoPongBeatConstants.PARTICLE_WIDTH);
					}
				}
			}
		}
	}

}
