package apoPongBeat.entity;

import java.awt.Color;

import apoPongBeat.ApoPongBeatConstants;
import apoPongBeat.game.ApoPongBeatPanel;

public class ApoPongBeatBallRebound extends ApoPongBeatBallMedium {
	
	private final Color COLOR_REBOUND = new Color(252, 177, 252);
	
	private boolean bAbove;
	
	private int count;
	
	private int maxCount;
	
	private float addVecX;
	
	private float addVecY;
	
	private final float addGravity = 0.0012f;
	
	private final int POINTS = 70;
	
	public ApoPongBeatBallRebound(float y, float width, float height, float vecX, boolean bAbove, int count) {
		super(y, width, height, vecX, false, true);
		
		this.bAbove = bAbove;
		this.maxCount = count;
		this.addVecX = (float)(0.5f + Math.random() * 0.3);
		super.setPoints(this.POINTS);
		super.setColor(this.COLOR_REBOUND);
	}
	
	public ApoPongBeatBallRebound(float y, float width, float height, float vecX, boolean bAbove, int count, float addVecX) {
		super(y, width, height, vecX, false, true);
		
		this.bAbove = bAbove;
		this.addVecX = addVecX;
		super.setPoints(this.POINTS);
		super.setColor(this.COLOR_REBOUND);
	}
	
	public void init() {
		super.init();
		
		this.count = 0;
		this.addVecY = -0.13f;
		if (!bAbove) {
			this.addVecY = -this.addVecY;
		}
	}

	public void think(int delta, ApoPongBeatPanel panel) {
		if ((this.isBRebound()) && (this.count < this.maxCount)) {
			this.addVecX = this.addVecX - this.addGravity*delta;
			this.setX(this.getX() + this.addVecX * delta);
			this.setY(this.getY() + this.addVecY * delta);
			
			if ((this.getX() + this.getWidth() < 0) && (this.addVecX < 0)) {
				this.setBVisible(false);
				panel.ballOnBorder(true);
				panel.getSounds().playSound("death");
			} else if ((this.getX() > ApoPongBeatConstants.GAME_WIDTH) && (this.addVecX > 0)) {
				this.setBVisible(false);
				panel.ballOnBorder(false);
			}
			
			if (this.getY() <= ApoPongBeatConstants.MIN_Y) {
				this.setY(ApoPongBeatConstants.MIN_Y);
				this.addVecY = -this.addVecY;
				panel.getSounds().playSound("bounce");
				if (panel.getMult() > 1) {
					ApoPongBeatParticle.makeParticle(this, panel, ApoPongBeatConstants.PARTICLE_TIME, ApoPongBeatConstants.PARTICLE_WIDTH);
				}
			} else if (this.getY() + this.getHeight() > ApoPongBeatConstants.MAX_Y) {
				this.setY(ApoPongBeatConstants.MAX_Y - this.getHeight());
				this.addVecY = -this.addVecY;
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
					this.count += 1;
					this.setX(panel.getRacket().getX() + panel.getRacket().getWidth());
					this.addVecX = -this.addVecX;
					if (panel.getMult() > 1) {
						ApoPongBeatParticle.makeParticle(this, panel, ApoPongBeatConstants.PARTICLE_TIME, ApoPongBeatConstants.PARTICLE_WIDTH);
					}
				}
			}
		} else {
			super.think(delta, panel);
		}
	}
	
}
