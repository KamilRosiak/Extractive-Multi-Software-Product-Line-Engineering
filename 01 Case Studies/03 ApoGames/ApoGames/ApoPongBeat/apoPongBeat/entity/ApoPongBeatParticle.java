package apoPongBeat.entity;

import java.awt.Color;

import apoPongBeat.game.ApoPongBeatPanel;

public class ApoPongBeatParticle extends ApoPongBeatBall {

	private int time;

	public static void makeParticle(ApoPongBeatBall ball, ApoPongBeatPanel panel, int time, int newSize) {
		ApoPongBeatParticle.makeParticle(ball.getX(), ball.getY(), ball.getWidth(), ball.getColor(), panel, time, newSize);
	}
	
	public static void makeParticle(float x, float y, float width, Color color, ApoPongBeatPanel panel, int time, int newSize) {
		int size = 50;
		if (newSize > 0) {
			size = newSize;
		}
		if (panel.getMult() >= 5) {
			panel.getParticle().add(new ApoPongBeatParticle(x - size, y, size, size, -0.3f, 0.3f, color, time));
			panel.getParticle().add(new ApoPongBeatParticle(x - size, y, size, size, -0.3f, -0.3f, color, time));
			panel.getParticle().add(new ApoPongBeatParticle(x + width, y, size, size, 0.3f, 0.3f, color, time));
			panel.getParticle().add(new ApoPongBeatParticle(x + width, y, size, size, 0.3f, -0.3f, color, time));
		}
		
		if (panel.getMult() >= 3) {
			panel.getParticle().add(new ApoPongBeatParticle(x - size, y, size, size, -0.5f, 0.7f, color, time));
			panel.getParticle().add(new ApoPongBeatParticle(x - size, y, size, size, -0.5f, -0.7f, color, time));
			panel.getParticle().add(new ApoPongBeatParticle(x + width, y, size, size, 0.5f, 0.7f, color, time));
			panel.getParticle().add(new ApoPongBeatParticle(x + width, y, size, size, 0.5f, -0.7f, color, time));
		}
		panel.getParticle().add(new ApoPongBeatParticle(x - size, y, size, size, -0.8f, 0, color, time));
		panel.getParticle().add(new ApoPongBeatParticle(x + width, y, size, size, 0.8f, 0, color, time));
		panel.getParticle().add(new ApoPongBeatParticle(x, y, size, size, 0.0f, -0.9f, color, time));
		panel.getParticle().add(new ApoPongBeatParticle(x, y, size, size, 0.0f, 0.9f, color, time));
	}
	
	public ApoPongBeatParticle(float x, float y, float width, float height, float vecX, float vecY, Color color, int time) {
		super(x, y, width, height, vecX, vecY, new Color(color.getRed(), color.getGreen(), color.getBlue(), 100), 0);
		
		this.time = time;
	}
	
	public void think(int delta, ApoPongBeatPanel panel) {
		this.setX(this.getX() + this.getVelocityX() * delta);
		this.setY(this.getY() + this.getVelocityY() * delta);
		
		this.time -= delta;
		if (this.time <= 0) {
			this.setBVisible(false);
		}
	}
	
}
