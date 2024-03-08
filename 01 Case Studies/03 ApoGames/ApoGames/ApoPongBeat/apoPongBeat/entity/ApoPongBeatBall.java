package apoPongBeat.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import org.apogames.entity.ApoEntity;

import apoPongBeat.ApoPongBeatConstants;
import apoPongBeat.game.ApoPongBeatPanel;

public class ApoPongBeatBall extends ApoEntity {

	private Color color;
	
	private int points;
	
	private boolean bBorder;
	
	private boolean bRebound;
	
	public ApoPongBeatBall(float x, float y, float width, float height, float vecX, float vecY, Color color, int points) {
		super(null, x, y, width, height);
		this.color = color;
		this.points = points;
		
		this.setVelocityX(vecX);
		this.setVelocityY(vecY);
		
		this.bBorder = true;
		this.bRebound = false;
	}
	
	public boolean isBBorder() {
		return this.bBorder;
	}

	public void setBBorder(boolean bBorder) {
		this.bBorder = bBorder;
	}

	public void think(int delta, ApoPongBeatPanel panel) {
		this.setX(this.getX() + this.getVelocityX() * delta);
		this.setY(this.getY() + this.getVelocityY() * delta);
		
		this.checkPosition(delta, panel);
	}
	
	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getPoints() {
		return this.points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public boolean isBRebound() {
		return this.bRebound;
	}

	public void setBRebound(boolean rebound) {
		this.bRebound = rebound;
	}

	protected void checkPosition(int delta, ApoPongBeatPanel panel) {
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
			panel.getSounds().playSound("bounce");
			if (panel.getMult() > 1) {
				ApoPongBeatParticle.makeParticle(this, panel, ApoPongBeatConstants.PARTICLE_TIME, ApoPongBeatConstants.PARTICLE_WIDTH);
			}
		} else if (this.getY() + this.getHeight() > ApoPongBeatConstants.MAX_Y) {
			this.setY(ApoPongBeatConstants.MAX_Y - this.getHeight() - 1);
			this.setVelocityY(-this.getVelocityY());
			panel.getSounds().playSound("bounce");
			if (panel.getMult() > 1) {
				ApoPongBeatParticle.makeParticle(this, panel, ApoPongBeatConstants.PARTICLE_TIME, ApoPongBeatConstants.PARTICLE_WIDTH);
			}
		}
		
		if (panel.getRacket().intersects(this)) {
			if (panel.getRacket().getX() < this.getX()) {
				panel.getParticle().add(new ApoPongBeatParticleString(this.getX(), this.getY() + this.getHeight(), this.getColor(), ApoPongBeatConstants.PARTICLE_POINTS_STRING_TIME, String.valueOf(this.points * panel.getMult() + panel.getCounter()), panel.getPointsFont(), false));
				panel.ballOnRacket(this.points);
				panel.getSounds().playSound("pong");

				this.bRebound = true;
				this.setX(panel.getRacket().getX() + panel.getRacket().getWidth());
				this.setVelocityX(-this.getVelocityX());
				if (panel.getMult() > 1) {
					ApoPongBeatParticle.makeParticle(this, panel, ApoPongBeatConstants.PARTICLE_TIME, ApoPongBeatConstants.PARTICLE_WIDTH);
				}
			}
		}
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		g.setColor(this.color);
		g.fillRect((int)(this.getX() - changeX), (int)(this.getY() - changeY), (int)(this.getWidth()), (int)(this.getHeight()));
		if (bBorder) {
			g.setColor(this.color.darker().darker());
			g.drawRect((int)(this.getX() - changeX), (int)(this.getY() - changeY), (int)(this.getWidth() - 1), (int)(this.getHeight() - 1));
		}
	}

}
