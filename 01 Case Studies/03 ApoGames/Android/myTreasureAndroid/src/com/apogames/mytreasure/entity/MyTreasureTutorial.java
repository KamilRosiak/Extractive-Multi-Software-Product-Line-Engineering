package com.apogames.mytreasure.entity;

import java.util.ArrayList;

import com.apogames.mytreasure.MyTreasureConstants;

import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;

public class MyTreasureTutorial {

	private final MyTreasureTutorialHelp[] points;
	
	private float curX, curY;
	private float goalX, goalY;
	private float curVecX, curVecY;
	private int waitTime;
	
	private int curPosition;
	
	private ArrayList<Integer> goToPosition;
	
	private boolean bVisible;
	
	public MyTreasureTutorial(final MyTreasureTutorialHelp[] points) {
		this.curX = this.goalX = 144;
		this.curY = this.goalY = 224;
		
		this.points = points;
		
		this.curPosition = 0;
		
		this.bVisible = true;
		
		this.goToPosition = new ArrayList<Integer>();
		for (int i = -1; i < points.length; i++) {
			this.goToPosition.add(i);
		}
	}
	
	public boolean isVisible() {
		return this.bVisible;
	}

	public void setVisible(boolean bVisible) {
		this.bVisible = bVisible;
	}

	public float getCurX() {
		return this.curX;
	}

	public float getCurY() {
		return this.curY;
	}

	public int getCurPosition() {
		return this.curPosition;
	}

	public void think(final int delta) {
		if (this.bVisible) {
			if (this.waitTime > 0) {
				this.waitTime -= delta;
			} else if (((int)(this.curX) != (int)(this.goalX)) || ((int)(this.curY) != (int)(this.goalY))) {
				this.curX += this.curVecX * delta;
				this.curY += this.curVecY * delta;
				
				if (((this.curVecX > 0) && (this.curX > this.goalX)) || ((this.curVecX < 0) && (this.curX < this.goalX))) {
					this.curX = this.goalX;
				}
				if (((this.curVecY > 0) && (this.curY > this.goalY)) || ((this.curVecY < 0) && (this.curY < this.goalY))) {
					this.curY = this.goalY;
				}
				
				if (((int)(this.curX) == (int)(this.goalX)) && ((int)(this.curY) == (int)(this.goalY))) {
					this.curX = this.goalX;
					this.curY = this.goalY;
					
					this.goToNextPosition();
				}
			} else {
				this.goToNextPosition();			
			}
		}
	}
	
	public boolean isRunning() {
		if (this.goToPosition.size() > 0) {
			return true;
		}
		return false;
	}
	
	public void goToNextPosition() {
		if (this.goToPosition.size() > 0) {
			this.goToPosition.remove(0);
			if (this.goToPosition.size() > 0) {
				this.curPosition = this.goToPosition.get(0);
				this.goalX = this.points[this.curPosition].getX();
				this.goalY = this.points[this.curPosition].getY();
				this.waitTime = this.points[this.curPosition].getWaitTime();
				
				this.curVecX = 0.00f;
				this.curVecY = 0.00f;
				if (this.goalX < this.curX) {
					this.curVecX = -0.1f;	
				} else if (this.goalX > this.curX) {
					this.curVecX = 0.1f;	
				}
				if (this.goalY < this.curY) {
					this.curVecY = -0.1f;	
				} else if (this.goalY > this.curY) {
					this.curVecY = 0.1f;	
				}
			} else {
				this.bVisible = false;
			}
		}
	}
	
	public void render(BitsGLGraphics g) {
		if (this.bVisible) {
			g.cropImage(MyTreasureConstants.iSheet, this.curX, this.curY, 32, 32, this.points[this.curPosition].getCropX(), this.points[this.curPosition].getCropY(), 32, 32);
		}
	}
	
}
