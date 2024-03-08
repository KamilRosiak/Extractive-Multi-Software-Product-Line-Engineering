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

package com.apogames.mytreasure.game;

import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;

import com.apogames.mytreasure.MyTreasureConstants;
import com.apogames.mytreasure.MyTreasureModel;
import com.apogames.mytreasure.MyTreasureSoundPlayer;
import com.apogames.mytreasure.entity.ApoEntity;
import com.apogames.mytreasure.entity.MyTreasureTutorial;
import com.apogames.mytreasure.entity.MyTreasureTutorialHelp;
import com.apogames.mytreasure.userlevels.MyTreasureLevels;

public class MyTreasureLevelChooser extends MyTreasureModel {

	public static final String BACK = "back";
	
	public static final int EASY = 0;
	public static final int MEDIUM = 1;
	public static final int HARD = 2;
	public static final int VERYHARD = 3;
	
	public static int FREE = 5;
	
	private int difficulty;
	
	private ApoEntity[] levels;
	
	private float curChange = 0;
	private int curStart = 0;
	private int curI = 0;
	private int min = 0;
	private int max = 20;
	
	private int mousePressedX, mousePressedY;
	
	private int curTouchX;
	
	private MyTreasureTutorial tutorial;
	
	private boolean bUserlevels;
	
	public MyTreasureLevelChooser(final MyTreasurePanel game) {
		super(game);
		
		this.difficulty = MyTreasureLevelChooser.EASY;		
	}

	@Override
	public void init() {
		if (this.levels == null) {
			this.levels = new ApoEntity[5000];
			int curX = 6 * 4;
			int curY = 24 * 4;
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < this.levels.length/20; j++) {
					this.levels[i + j * 20] = new ApoEntity(null, curX + 17 * 16 * j, curY, 16 * 4, 16 * 4);
				}
				
				curX += 17 * 4;
				if (curX > 280) {
					curX = 6 * 4;
					curY += 18 * 4;
				}
			}
		}
		this.curTouchX = 0;
		
		if (MyTreasureConstants.FIRST_LEVELCHOOSER) {
			MyTreasureTutorialHelp[] points = new MyTreasureTutorialHelp[4];
			points[0] = new MyTreasureTutorialHelp(144, 224, 64, 224, 500);
			points[1] = new MyTreasureTutorialHelp(48, 113, 64, 224, 100);
			points[2] = new MyTreasureTutorialHelp(48, 113, 96, 224, 1000);
			points[3] = new MyTreasureTutorialHelp(48, 113, 64, 224, 600);
			this.tutorial = new MyTreasureTutorial(points);
		} else {
			this.tutorial = null;
		}
	}
	
	public int getCurMax() {
		int free = MyTreasureLevelChooser.FREE;
		for (int i = 0; i < this.levels.length && i < this.max - this.min; i++) {
			boolean bSolved = this.getGame().getSolvedLevel().containsKey(MyTreasureLevels.getLevel(this.curI + i));
			if (!bSolved) {
				free -= 1;
				if (free <= 0) {
					return i;
				}
			}
		}
		return this.max - this.min;
	}

	public int getMin() {
		return this.min;
	}

	public int getCurI() {
		return this.curI;
	}

	public int getDifficulty() {
		return this.difficulty;
	}

	public void setDifficulty(final int difficulty, final int level, final boolean bMap) {
		this.difficulty = difficulty;
		this.curChange = 0;
		this.curStart = level / 20;
		
		if (this.difficulty == MyTreasureLevelChooser.EASY) {
			this.min = 0;
			this.max = 40;
			if (MyTreasureConstants.FREE_VERSION) {
				this.max = 20;
			}
			this.curI = 0; 
		}
		if (this.difficulty == MyTreasureLevelChooser.MEDIUM) {
			this.min = 40;
			this.max = 80;
			this.curI = 40; 
		}
		
		if (this.difficulty == MyTreasureLevelChooser.HARD) {
			this.min = 80;
			this.max = 120;
			this.curI = 80; 
		}
		if (this.difficulty == MyTreasureLevelChooser.VERYHARD) {
			this.min = 120;
			this.max = 150;
			this.curI = 120; 
		}
		if ((!MyTreasureConstants.FIRST_LEVELCHOOSER) && (level < 20) && (level + MyTreasureLevelChooser.FREE >= 20) && (MyTreasureConstants.FIRST_LEVELCHOOSER_DRAG)) {
			MyTreasureTutorialHelp[] points = new MyTreasureTutorialHelp[5];
			points[0] = new MyTreasureTutorialHelp(144, 224, 64, 224, 0);
			points[1] = new MyTreasureTutorialHelp(289, 65, 64, 224, 0);
			points[2] = new MyTreasureTutorialHelp(289, 65, 96, 224, 200);
			points[3] = new MyTreasureTutorialHelp( 40, 65, 96, 224, 50);
			points[4] = new MyTreasureTutorialHelp( 40, 65, 64, 224, 500);
			this.tutorial = new MyTreasureTutorial(points);
		} else if ((!MyTreasureConstants.FIRST_LEVELCHOOSER) && (level >= 20) && (MyTreasureConstants.FIRST_LEVELCHOOSER_DRAG)) {
			MyTreasureTutorialHelp[] points = new MyTreasureTutorialHelp[5];
			points[0] = new MyTreasureTutorialHelp(144, 224, 64, 224, 0);
			points[1] = new MyTreasureTutorialHelp( 40, 65, 64, 224, 0);
			points[2] = new MyTreasureTutorialHelp( 40, 65, 96, 224, 200);
			points[3] = new MyTreasureTutorialHelp(289, 65, 96, 224, 50);
			points[4] = new MyTreasureTutorialHelp(289, 65, 64, 224, 500);
			this.tutorial = new MyTreasureTutorial(points);
		}
		
		if (bMap) {
			for (int i = 0; i < this.levels.length && i < this.max - this.min; i++) {
				boolean bSolved = this.getGame().getSolvedLevel().containsKey(MyTreasureLevels.getLevel(this.curI + i));
				if (!bSolved) {
					this.curStart = i / 20;
					break;
				}
			}
		}
		this.bUserlevels = false;
	}

	public void setUserlevels() {
		this.bUserlevels = true;
		this.curChange = this.curI = this.min = 0;
		this.max = MyTreasureLevels.editorLevels.length;
		
		for (int i = 0; i < this.levels.length && i < this.max - this.min; i++) {
			boolean bSolved = this.getGame().getSolvedLevel().containsKey(MyTreasureLevels.editorLevels[this.curI + i]);
			if (!bSolved) {
				this.curStart = i / 20;
				break;
			}
		}
	}

	@Override
	public void touchedPressed(int x, int y, int finger) {
		this.mousePressedX = x;
		this.mousePressedY = y;
		this.curTouchX = x;
	}

	@Override
	public void touchedReleased(int x, int y, int finger) {
		if (Math.abs(x - this.mousePressedX) > MyTreasureConstants.CHANGE_DRAG) {
			if (this.mousePressedX > x) {
				if (this.curStart * 20 + 20 + this.min < this.max) {
					this.curChange = 17 * 16 + (this.curTouchX - this.mousePressedX);
					this.curStart += 1;
				}
			}
			if (this.mousePressedX < x) {
				if (this.curStart * 20 - 20 + this.min >= this.min) {
					this.curChange = -17 * 16 + (this.curTouchX - this.mousePressedX);;
					this.curStart -= 1;
				}
			}
		} else if (Math.abs(x - this.mousePressedX) <= 2) {
			int free = MyTreasureLevelChooser.FREE;
			for (int i = 0; i < this.levels.length && i < this.max - this.min; i++) {
				boolean bSolved = this.getGame().getSolvedLevel().containsKey(MyTreasureLevels.getLevel(this.curI + i));
				if (!bSolved) {
					free -= 1;
				}
				if ((this.levels[i].getX() <= x + this.curStart * 272) && (this.levels[i].getX() + 64 >= x + this.curStart * 272) &&
					(this.levels[i].getY() <= y) && (this.levels[i].getY() + 64 >= y)) {
					if (this.bUserlevels) {
						this.getGame().playSound(MyTreasureSoundPlayer.SOUND_CLICK);
						this.getGame().setGame(true, false, i, "");
					} else if ((free > 0) || (bSolved)) {
						if (MyTreasureConstants.FIRST_LEVELCHOOSER) {
							MyTreasureConstants.FIRST_LEVELCHOOSER = false;
							if (this.tutorial != null) {
								this.tutorial.setVisible(false);
							}
						}
						this.getGame().playSound(MyTreasureSoundPlayer.SOUND_CLICK);
						this.getGame().setGame(false, false, i, "");
					}
					return;
				}
			}
		} else if (this.curTouchX != 0){
			this.curChange = (this.curTouchX - this.mousePressedX);
		}
		this.curTouchX = 0;
		this.mousePressedX = 0;
	}

	@Override
	public void touchedDragged(int x, int y, int oldX, int oldY, int finger) {
		if (this.mousePressedX > x) {
			if (this.curStart * 20 + 20 + this.min < this.max) {
				this.curTouchX = x;
			}
		}
		if (this.mousePressedX < x) {
			if (this.curStart * 20 - 20 + this.min >= this.min) {
				this.curTouchX = x;
			}
		}
	}

	@Override
	public void touchedButton(String function) {
		this.getGame().playSound(MyTreasureSoundPlayer.SOUND_CLICK);
		if (function.equals(MyTreasureMap.BACK)) {
			this.onBackButtonPressed();
		}
	}
	
	public void onBackButtonPressed() {
		if (this.bUserlevels) {
			this.getGame().setMenu();
		} else {
			this.getGame().setMap();
		}
	}
	
	@Override
	public void think(int delta) {
		if (this.curChange > 0) {
			this.curChange -= 4;
		}
		
		if (this.curChange < 0) {
			this.curChange += 4;
		}
		
		if (this.tutorial != null) {
			if (this.tutorial.isVisible()) {
				this.tutorial.think(delta);
				if (!this.tutorial.isVisible()) {
					if (MyTreasureConstants.FIRST_LEVELCHOOSER) {
						MyTreasureConstants.FIRST_LEVELCHOOSER = false;
					} else {
						MyTreasureConstants.FIRST_LEVELCHOOSER_DRAG = false;
					}
				}
			}
		}
	}

	@Override
	public void render(BitsGLGraphics g) {
		g.cropImage(MyTreasureConstants.iSheet, 0, 0, MyTreasureConstants.GAME_WIDTH, MyTreasureConstants.GAME_HEIGHT, 1024 - MyTreasureConstants.GAME_WIDTH, 0, MyTreasureConstants.GAME_WIDTH, MyTreasureConstants.GAME_HEIGHT);

		g.cropImage(MyTreasureConstants.iSheet, 2 * 4, 58, 76 * 4, 8 * 4, 0, 96 * 4, 76 * 4, 8 * 4);
		
		g.setFont(MyTreasureConstants.FONT_LEVELCHOOSER);
		int y = MyTreasureConstants.FONT_LEVELCHOOSER.mCharCellHeight;
		g.setColor(MyTreasureConstants.COLOR_DARK);
		String s = MyTreasureConstants.DIFFICULTY[this.difficulty];
		if (this.bUserlevels) {
			s = "Userlevels";
		}
		float w = MyTreasureConstants.FONT_LEVELCHOOSER.getLength(s);
		g.drawText(s, MyTreasureConstants.GAME_WIDTH*1/2 - w/2, 48 - y);
		
		if ((this.max - this.min - 1) / 20 > 0) {
			g.setFont(MyTreasureConstants.fontSmall);
			y = MyTreasureConstants.fontSmall.mCharCellHeight;
			s = " ("+(this.curStart + 1) + " / " + ((this.max - this.min - 1)/20 + 1)+")";
			g.drawText(s, MyTreasureConstants.GAME_WIDTH*1/2 + w/2, 46 - y);	
		}
		
		int free = MyTreasureLevelChooser.FREE;
		g.setClip(16, 96, 288, 360);
		for (int i = 0; i < this.levels.length && i < this.max - this.min; i++) {
			boolean bSolved = this.getGame().getSolvedLevel().containsKey(MyTreasureLevels.getLevel(this.curI + i));
			if (this.bUserlevels) {
				bSolved = this.getGame().getSolvedLevel().containsKey(MyTreasureLevels.editorLevels[this.curI + i]);
			}
			if ((!bSolved) && (!this.bUserlevels)) {
				free -= 1;
			}
			int change = 0;
			if (this.curTouchX > 0) {
				change = -(this.curTouchX - this.mousePressedX);
			}
			if ((this.levels[i].getX() + 64 > 20 - this.curChange + this.curStart * 272 + change) && (this.levels[i].getX() < 20 - this.curChange + (this.curStart + 1) * 272 + change)) {
				int add = 0;
				if ((Math.abs(this.curTouchX - this.mousePressedX) <= 2) && (this.levels[i].intersects(this.mousePressedX - this.curChange + this.curStart * 272 + change, this.mousePressedY))) {
					add = 2;
				}
				g.setColor(1f, 1f, 1f, 1f);
				if ((free > 0) || (bSolved)) {
					g.cropImage(MyTreasureConstants.iSheet, (int)(this.levels[i].getX() - change + this.curChange - this.curStart * 272), (int)(this.levels[i].getY() + add), 64, 64, 16 * 4, 80 * 4, 64, 64);
				} else {
					g.cropImage(MyTreasureConstants.iSheet, (int)(this.levels[i].getX() - change + this.curChange - this.curStart * 272), (int)(this.levels[i].getY() + add), 64, 64, 0, 80 * 4, 64, 64);
				}
				g.setColor(MyTreasureConstants.COLOR_LIGHT);
				g.setFont(MyTreasureConstants.fontBig);
				y = MyTreasureConstants.fontBig.mCharCellHeight;
				s = String.valueOf(i + 1);
				w = MyTreasureConstants.fontBig.getLength(s);
				g.drawText(s, (int)(this.levels[i].getX() + this.curChange - change - this.curStart * 272 + 32) - w/2, (int)(this.levels[i].getY() + 38 - y + add));
				
				if (bSolved) {
					g.setColor(1f, 1f, 1f, 1f);
					int skulls = 0;
					if (!this.bUserlevels) {
						skulls = this.getGame().getSolvedLevel().get(MyTreasureLevels.getLevel(this.curI + i));
					} else {
						skulls = this.getGame().getSolvedLevel().get(MyTreasureLevels.editorLevels[this.curI + i]);
					}
					g.cropImage(MyTreasureConstants.iSheet, (int)(this.levels[i].getX() - change + this.curChange - this.curStart * 272 + 2 * 4), (int)(this.levels[i].getY() + 10 * 4 + add), 12, 12, 8 * 4, 120 * 4, 12, 12);
					if (skulls > 1) {
						g.cropImage(MyTreasureConstants.iSheet, (int)(this.levels[i].getX() - change + this.curChange - this.curStart * 272 + 6 * 4), (int)(this.levels[i].getY() + 10 * 4 + add), 12, 12, 12 * 4, 120 * 4, 12, 12);
						if (skulls > 2) {
							g.cropImage(MyTreasureConstants.iSheet, (int)(this.levels[i].getX() - change + this.curChange - this.curStart * 272 + 10 * 4), (int)(this.levels[i].getY() + 10 * 4 + add), 12, 12, 16 * 4, 120 * 4, 12, 12);
						}
					}
				}
			}
		}
		g.setClip(0, 0, MyTreasureConstants.GAME_WIDTH, MyTreasureConstants.GAME_HEIGHT);
		
		g.setColor(1f, 1f, 1f, 1f);
		if (this.tutorial != null) {
			if (this.tutorial.isVisible()) {
				this.tutorial.render(g);
			}
		}
	}

}

