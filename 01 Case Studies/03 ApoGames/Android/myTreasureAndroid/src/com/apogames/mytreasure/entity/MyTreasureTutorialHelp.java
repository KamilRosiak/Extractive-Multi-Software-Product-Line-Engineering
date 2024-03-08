package com.apogames.mytreasure.entity;

public class MyTreasureTutorialHelp {

	private final float x;
	private final float y;
	private final int cropX;
	private final int cropY;
	private final int waitTime;
	
	public MyTreasureTutorialHelp(final float x, final float y, final int cropX, final int cropY, final int waitTime) {
		this.x = x;
		this.y = y;
		this.cropX = cropX;
		this.cropY = cropY;
		this.waitTime = waitTime;
	}

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

	public int getCropX() {
		return this.cropX;
	}

	public int getCropY() {
		return this.cropY;
	}

	public int getWaitTime() {
		return this.waitTime;
	}
	
}
