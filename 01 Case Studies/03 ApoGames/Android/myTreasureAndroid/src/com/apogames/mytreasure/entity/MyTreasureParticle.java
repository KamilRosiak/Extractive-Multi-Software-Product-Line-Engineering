package com.apogames.mytreasure.entity;

import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;

import com.apogames.mytreasure.MyTreasureConstants;

public class MyTreasureParticle {

	private float x, y, width, height;
	private float vecX, vecY;
	
	private boolean bVisible;
	
	public MyTreasureParticle(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.vecY = (float)(Math.random() * 0.11) - 0.09f;
		this.vecX = (float)(Math.random() * 0.1) - 0.05f;
		
		this.bVisible = true;
	}
	
	public boolean isVisible() {
		return this.bVisible;
	}
	
	public void setVisible(boolean bVisible) {
		this.bVisible = bVisible;
	}

	public void think(final int delta) {
		if (this.bVisible) {
			this.x += this.vecX * delta;
			this.y += this.vecY * delta;
			if ((this.y > MyTreasureConstants.GAME_HEIGHT) || (this.x + this.width < 0) || (this.x > MyTreasureConstants.GAME_WIDTH)) {
				this.bVisible = false;
			}
			this.vecY += 0.0005f;
			if (this.vecX < 0) {
				this.vecX += 0.00005f;
				if (this.vecX >= 0) {
					this.vecX = 0;
				}
			}
			if (this.vecX > 0) {
				this.vecX -= 0.00005f;
				if (this.vecX <= 0) {
					this.vecX = 0;
				}
			}
		}
	}


	/**
	 * malt das Objekt
	 * @param g = Graphics2D Objekt
	 */
	public void render(BitsGLGraphics g) {
		this.render(g, 0, 0);
	}
	
	/**
	 * malt das Objekt
	 * @param g = Graphics2D Objekt
	 */
	public void render(BitsGLGraphics g, final int changeX, final int changeY) {
		g.setColor(MyTreasureConstants.COLOR_DARK);
		g.fillRect(this.x + changeX, this.y + changeY, this.width, this.height);
	}
	
}
