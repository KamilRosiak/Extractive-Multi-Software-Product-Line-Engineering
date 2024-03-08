package net.apogames.apoclock.entity;

import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;

public class ApoClockEntityClock extends ApoClockEntityBall {
	
	private float rotateVelocity;
	
	private float startangle;
	
	private float curTime;
	
	private boolean rotateClockwise;
	
	private boolean bSelected;
	
	public ApoClockEntityClock(float x, float y, float radius, float angle, float rotateVelocity) {
		super(x, y, radius, angle, 0);
		
		this.rotateVelocity = rotateVelocity;
		this.startangle = -1;
		this.curTime = 0;
		this.rotateClockwise = true;
	}

	public float getRotateVelocity() {
		return this.rotateVelocity;
	}

	public void setRotateVelocity(float rotateVelocity) {
		this.rotateVelocity = rotateVelocity;
	}

	public float getStartangle() {
		return this.startangle;
	}

	public void setStartangle(float startangle) {
		this.startangle = startangle;
	}

	public float getCurTime() {
		return this.curTime;
	}

	public void setCurTime(float curTime) {
		this.curTime = curTime;
	}

	public boolean isRotateClockwise() {
		return this.rotateClockwise;
	}

	public void setRotateClockwise(boolean rotateClockwise) {
		this.rotateClockwise = rotateClockwise;
	}
	
	public boolean isSelected() {
		return this.bSelected;
	}

	public void setSelected(boolean bSelected) {
		this.bSelected = bSelected;
	}

	public String getStringForLevel() {
		int angle = (int)(this.getAngle());
		if (!this.isRotateClockwise()) {
			angle = -angle;
		}
		return String.valueOf((int)(this.getX()))+","+String.valueOf((int)(this.getY()))+","+String.valueOf((int)(this.getRadius()))+","+String.valueOf(angle)+","+String.valueOf((int)(this.getRotateVelocity()));
	}
	
	public void render(final BitsGLGraphics g, int changeX, int changeY) {
		int points = 45;
		
		if (this.isVisible()) {
			if (this.startangle >= 0) {
				g.setColor(1f, 1f, 1f, 1f);
				g.fillCircle(this.getX() - changeX, this.getY() - changeY, this.getRadius() * 1, points);
				
				int startAngle = (int)(this.startangle - 90);
				if (startAngle < 0) {
					startAngle += 360;
				}
	
				int dif = (int)(this.getAngle() - this.startangle);
				if (this.startangle > this.getAngle()) {
					dif = dif + 360;
				}
				if (!this.rotateClockwise) {
					dif = (int)(this.startangle - this.getAngle());
					if (this.startangle < this.getAngle()) {
						dif = dif + 360;
					}
					dif = -dif;
				}
				
				g.setColor(255f/255f, 120f/255f, 120f/255f, 1f);
				g.fillArc(this.getX(), this.getY(), this.getRadius(), startAngle, dif, points);	
			} else {
				g.setColor(160f/255f, 160f/255f, 160f/255f, 1f);
				if (this.bSelected) {
					g.setColor(1f, 1f, 1f, 1f);
				}
				g.fillCircle(this.getX(), this.getY(), this.getRadius(), points);
			}
	
			if (this.getRadius() > 30) {
				points = 120;
			}
			g.setColor(0f/255f, 0f/255f, 0f/255f, 1f);
			g.setLineSize(2.0f);
			g.drawCircle(this.getX(), this.getY(), this.getRadius(), points);
			g.setLineSize(3.0f);
			for (int j = 0; j < 12; j++) {
				g.setColor(0f, 0f, 0f, 1f);
				g.drawLine(this.getX() + (int)((this.getRadius() - 5) * Math.sin(Math.toRadians(j * 30))), this.getY() + (int)(-(this.getRadius() - 5) * Math.cos(Math.toRadians(j * 30))), this.getX() + (int)((this.getRadius()) * Math.sin(Math.toRadians(j * 30))), this.getY() + (int)(-(this.getRadius()) * Math.cos(Math.toRadians(j * 30))));
			}
			
			g.drawLine(this.getX(), this.getY(), this.getX() + (int)((this.getRadius() - 5) * Math.sin(Math.toRadians(this.getAngle()))), this.getY() + (int)(-(this.getRadius() - 5) * Math.cos(Math.toRadians(this.getAngle()))));
			g.setLineSize(1.0f);
		}
	}
}
