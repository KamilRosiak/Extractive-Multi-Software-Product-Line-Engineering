/*
 * Copyright (c) 2005-2013 Dirk Aporius <dirk.aporius@gmail.com>
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.apogames.mytreasure.entity;

import java.util.ArrayList;

import com.apogames.mytreasure.MyTreasureConstants;

public class MyTreasureMapPlayer {
	
	public static final MyTreasureMapHelp[] HELP = new MyTreasureMapHelp[] {
		new MyTreasureMapHelp(28 * 4, 104 * 4), // first station
		new MyTreasureMapHelp(47 * 4, 104 * 4),
		new MyTreasureMapHelp(47 * 4, 95 * 4), // second station
		new MyTreasureMapHelp(47 * 4, 75 * 4),
		new MyTreasureMapHelp(52 * 4, 57 * 4),
		new MyTreasureMapHelp(35 * 4, 61 * 4), 
		new MyTreasureMapHelp(35 * 4, 57 * 4), // third station
		new MyTreasureMapHelp(35 * 4, 39 * 4),
		new MyTreasureMapHelp(47 * 4, 39 * 4),
		new MyTreasureMapHelp(52 * 4, 32 * 4),
		new MyTreasureMapHelp(52 * 4, 27 * 4),
		new MyTreasureMapHelp(46 * 4, 26 * 4),
		new MyTreasureMapHelp(47 * 4, 21 * 4) // last station
	};
	
	private float curX, curY;
	private float goalX, goalY;
	private float curVecX, curVecY;
	
	private int curPosition;
	
	private ArrayList<Integer> goToPosition;
	
	public MyTreasureMapPlayer() {
		this.setPlayerPosition(0);
		
		this.goToPosition = new ArrayList<Integer>();
	}
	
	public void setPlayerPosition(final int position) {
		if ((position >= 0) && (position < MyTreasureMapPlayer.HELP.length)) {
			this.goalX = this.curX = MyTreasureMapPlayer.HELP[position].getX();
			this.goalY = this.curY = MyTreasureMapPlayer.HELP[position].getY();
			
			this.curPosition = position;
		}
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
		if (((int)(this.curX) != (int)(this.goalX)) || ((int)(this.curY) != (int)(this.goalY))) {
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
				this.goalX = MyTreasureMapPlayer.HELP[this.curPosition].getX();
				this.goalY = MyTreasureMapPlayer.HELP[this.curPosition].getY();
				
				this.curVecX = 0.00f;
				this.curVecY = 0.00f;
				if (this.goalX < this.curX) {
					this.curVecX = -0.2f;	
				} else if (this.goalX > this.curX) {
					this.curVecX = 0.2f;	
				}
				if (this.goalY < this.curY) {
					this.curVecY = -0.2f;	
				} else if (this.goalY > this.curY) {
					this.curVecY = 0.2f;	
				}
			}
		}
	}
	
	public void setNextPosition(final int position) {
		int cur = this.curPosition;
		if (this.goToPosition.size() > 0) {
			cur = this.goToPosition.get(0);
			this.goToPosition.clear();
		}
		if (cur != position) {
			if (cur > position) {
				for (int i = cur; i >= position; i--) {
					this.goToPosition.add(i);
				}
			} else if (cur < position) {
				for (int i = cur; i <= position; i++) {
					this.goToPosition.add(i);
				}
			}
		}
		MyTreasureConstants.LEVELCHOOSER_STEP = position;
	}
}
