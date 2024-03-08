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

public class MyTreasureEditor extends MyTreasureModel {

	public static final int ENTITY_WALL = 0;
	public static final int ENTITY_FREE = 1;
	public static final int ENTITY_PLAYER = 2;
	public static final int ENTITY_SKULL = 3;
	public static final int ENTITY_BLOCK = 4;
	
	public static final String BACK = "back";
	public static final String SIZE_LEFT = "left";
	public static final String SIZE_RIGHT = "right";
	public static final String TEST = "test";
	public static final String UPLOAD = "upload";
	public static final String SOLVE = "solve";
	
	private int[][] level;
	
	private int size = 6;
	
	private int curChoose;
	
	private int steps;
	
	private int startX, startY;
	
	private int time;
	private String uploadString;
	
	private Thread t;
	
	private int[][] levelRandom;
	
	public MyTreasureEditor(final MyTreasurePanel game) {
		super(game);
	}

	@Override
	public void init() {
		if (this.level == null) {
			this.level = new int[6][6];
			this.makeStandardLevel();
			
			this.curChoose = MyTreasureEditor.ENTITY_BLOCK;
		}
		this.time = 0;
		this.makeBackground();
		this.setCanBeTest();
	}
	
	public void makeBackground() {
		this.levelRandom = new int[16][11];
		
		for (int y = 0; y < 16; y += 1) {
			for (int x = 0; x < 11; x += 1) {
				this.levelRandom[y][x] = (int)(Math.random() * 4);
			}
		}
	}
	
	public void setSteps(int steps) {
		if (this.steps > steps) {
			this.steps = steps;
		}
//		System.out.println(this.getLevelString());
	}
	
	public void setVisibleUpload(final boolean visible) {
		this.getGame().getButtons()[19].setVisible(visible);
		if (!visible) {
			this.time = 0;
		}
	}	
	
	public void makeStandardLevel() {
		for (int y = 1; y < this.level.length - 1; y++) {
			for (int x = 1; x < this.level[0].length - 1; x++) {
				this.level[y][x] = 1;
			}
		}
		this.level[this.level.length - 2][2] = 2;
		this.level[this.level.length - 2][1] = 4;
		this.level[1][this.level[0].length - 3] = 3;
		
		this.startX = MyTreasureConstants.GAME_WIDTH/2 - this.level[0].length * 16;
		this.startY = MyTreasureConstants.GAME_HEIGHT/2 - this.level.length * 16 - 32;
	}
	
	public void setCanBeTest() {
		int player = 0;
		int skull = 0;
		
		for (int y = 0; y < this.level.length; y += 1) {
			for (int x = 0; x < this.level[0].length; x += 1) {
				if (this.level[y][x] == 2) player += 1;
				if (this.level[y][x] == 3) skull += 1;
			}
		}
		
		if ((player == 1) && (skull == 1)) {
			this.getGame().getButtons()[18].setVisible(true);
			this.getGame().getButtons()[20].setVisible(true);	
		} else {
			this.getGame().getButtons()[18].setVisible(false);
			this.getGame().getButtons()[20].setVisible(false);
		}
	}
	
	public String getLevelString() {
		String levelString = "";
		String width = String.valueOf(this.level[0].length);
		if (width.length() < 2) {
			width = "0" + width;
		}
		String height = String.valueOf(this.level.length);
		if (height.length() < 2) {
			height = "0" + height;
		}
		levelString = width + height;
		for (int y = 0; y < this.level.length; y += 1) {
			for (int x = 0; x < this.level[0].length; x += 1) {
				levelString += String.valueOf(this.level[y][x]);
			}
		}
		String stepsString = String.valueOf(this.steps);
		
		if (stepsString.length() < 2) {
			stepsString = "0" + stepsString;
		}
		
		levelString += stepsString;
		
		stepsString = String.valueOf(this.steps + 4);
		if (stepsString.length() < 2) {
			stepsString = "0" + stepsString;
		}
		
		levelString += stepsString;
		
		return levelString;
	}

	@Override
	public void touchedButton(String function) {
		this.getGame().playSound(MyTreasureSoundPlayer.SOUND_CLICK);
		if (function.equals(MyTreasureEditor.BACK)) {
			this.onBackButtonPressed();
		} else if (function.equals(MyTreasureEditor.SIZE_LEFT)) {
			if (this.size > 5) {
				this.size -= 1;
				this.level = new int[this.size][this.size];
				this.makeStandardLevel();
				this.steps = 95;
			}
		} else if (function.equals(MyTreasureEditor.SIZE_RIGHT)) {
			if (this.size < 10) {
				this.size += 1;
				this.level = new int[this.size][this.size];
				this.makeStandardLevel();
				this.steps = 95;
			}
		} else if (function.equals(MyTreasureEditor.TEST)) {
			this.getGame().setGame(false, true, 0, this.getLevelString());
		} else if (function.equals(MyTreasureEditor.UPLOAD)) {
			this.upload();
		} else if (function.equals(MyTreasureEditor.SOLVE)) {
			this.setSolutionString("");
			this.solveLevel(this.getLevelString());
		}
	}

	public void setTestString(final String solution) {
		this.time = 10000;
		this.uploadString = solution;
	}
	
	public void setSolutionString(final String solution) {
		this.time = 10000;
		if (solution.length() == 0) {
			this.uploadString = "try to solve the level";
		} else if (solution.startsWith("no solution")) {
			this.uploadString = solution;
		} else {
			this.uploadString = "level solved in "+solution.length()+" steps";
			this.steps = solution.length();
			this.setVisibleUpload(true);
		}
	}
	
	public final void solveLevel(final String level) {
		this.t = new Thread(new Runnable() {

			public void run() {
				MyTreasureEditor.this.getGame().doSolveLevel(level);
			}
 		});
 		this.t.start();
	}
	
	public void onBackButtonPressed() {
		this.getGame().setMenu();
	}
	
	public void upload() {
		this.time = 5000;
		this.uploadString = "uploading ...";
		this.setVisibleUpload(false);
		this.t = new Thread(new Runnable() {
			public void run() {
				MyTreasureEditor.this.uploadString();
			}
 		});
		this.t.start();
	}
	
	public void uploadString() {
		boolean bUpload = this.getGame().getUserlevels().addLevel(this.getLevelString());
		if (bUpload) {
			this.uploadString = "upload successfully";
			this.getGame().loadUserlevels();
			this.time = 2000;
		} else {
			this.uploadString = "upload failed";
			this.time = 2000;
		}
	}
	

	@Override
	public void touchedPressed(int x, int y, int finger) {
		this.touchedReleased(x, y, finger);
	}

	@Override
	public void touchedReleased(int x, int y, int finger) {
		if ((y >= MyTreasureConstants.GAME_HEIGHT - 64) && (y <= MyTreasureConstants.GAME_HEIGHT - 32) && 
				(x >= 128)) {
				for (int i = 0; i < 5; i++) {
					if ((x >= 128 + i * 40) && (x <= 160 + i * 40)) {
						this.curChoose = i;
						this.getGame().playSound(MyTreasureSoundPlayer.SOUND_CLICK);
						break;
					}
				}
			}
			if ((y >= this.startY) && (y < this.startY + this.size * 32) &&
				(x >= this.startX) && (x < this.startX + this.size * 32)) {
				int curX = (x - this.startX) / 32;
				int curY = (y - this.startY) / 32;
				if ((this.curChoose == 2) || (this.curChoose == 3)) {
					if ((curX < 1) || (curY < 1) || (curX >= this.level[0].length - 1) || (curY >= this.level.length - 1)) {
						return;
					}
				}
				if (this.level[curY][curX] != this.curChoose) {
					if ((this.curChoose == MyTreasureEditor.ENTITY_SKULL) || (this.curChoose == MyTreasureEditor.ENTITY_PLAYER)) {
						for (int lY = 0; lY < this.level.length; lY += 1) {
							for (int lX = 0; lX < this.level[0].length; lX += 1) {
								if (this.level[lY][lX] == this.curChoose) {
									this.level[lY][lX] = MyTreasureEditor.ENTITY_FREE;
								}
							}
						}
					}
					this.level[curY][curX] = this.curChoose;
					this.steps = 95;
					this.setCanBeTest();
					this.setVisibleUpload(false);
				}
			}
	}

	@Override
	public void touchedDragged(int x, int y, int oldX, int oldY, int finger) {
		if ((y >= this.startY) && (y < this.startY + this.size * 32) &&
				(x >= this.startX) && (x < this.startX + this.size * 32)) {
				this.touchedReleased(x, y, finger);
			}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void think(int delta) {
		if (this.time > 0) {
			this.time -= delta;
			if (this.time <= 0) {
				if (this.uploadString.equals("uploading ...")) {
					if (this.t != null) {
						try {
							this.t.stop();
						} catch (Exception ex) {}
					}
				} else if (this.uploadString.equals("try to solve the level")) {
					if (this.t != null) {
						try {
							this.t.stop();
						} catch (Exception ex) {}
					}
					this.time = 3000;
					this.uploadString = "Can't find a solution in 10 sec";
				}
			}
		}
	}

	@Override
	public void render(BitsGLGraphics g) {
//		g.cropImage(this.iBackground, 0, 0, MyTreasureConstants.GAME_WIDTH, MyTreasureConstants.GAME_HEIGHT, 0, 0, MyTreasureConstants.GAME_WIDTH, MyTreasureConstants.GAME_HEIGHT);
		
		for (int y = 1; y < 16; y += 1) {
			for (int x = 0; x < 11; x += 1) {
				g.cropImage(MyTreasureConstants.iSheet, (x-1) * 32, (y) * 32, 32, 32, this.levelRandom[y][x] * 32, 3 * 32, 32, 32);
			}
		}
		for (int i = 0; i < 3; i++) {
			g.cropImage(MyTreasureConstants.iSheet, 0, 448 - i * 32, 320, 32, 384, 480, 320, 32);
		}
		g.cropImage(MyTreasureConstants.iSheet, 0, 0, 320, 32, 384, 480, 320, 32);
		
		
		for (int y = 0; y < this.level.length; y += 1) {
			for (int x = 0; x < this.level[0].length; x += 1) {
				g.setColor(1f, 1f, 1f, 1f);
				if (this.level[y][x] == 3) {
					g.cropImage(MyTreasureConstants.iSheet, this.startX + (x) * 32 - 16, this.startY +(y) * 32 - 16, 64, 64, 8 * 32, 32, 64, 64);
					g.cropImage(MyTreasureConstants.iSheet, this.startX + (x) * 32, this.startY + (y) * 32, 32, 32, 0 * 32, 1 * 32, 32, 32);
				}
				if (this.level[y][x] == 4) {
					g.cropImage(MyTreasureConstants.iSheet, this.startX + (x) * 32, this.startY + (y) * 32, 32, 32, 2 * 32, 0 * 32, 32, 32);
				}
				if (this.level[y][x] == 0) {
					g.cropImage(MyTreasureConstants.iSheet, this.startX + (x) * 32, this.startY + (y) * 32, 32, 32, 0 * 32, 2 * 32, 32, 32);
				}
				if (this.level[y][x] == 2) {
					int curX = 0;
					int curY = 0;
					g.cropImage(MyTreasureConstants.iSheet, this.startX + (x) * 32, this.startY + (y) * 32, 32, 32, curX * 32, curY * 32, 32, 32);
				}
				if (this.level[y][x] == 1) {
					g.setColor(MyTreasureConstants.COLOR_LIGHT);
					g.drawRect(this.startX + (x) * 32, this.startY + (y) * 32, 32, 32);
				}
			}
		}
		
		g.setColor(MyTreasureConstants.COLOR_SEPARATOR);
		g.setFont(MyTreasureConstants.FONT_STATISTICS);
		
		String s = "size";
		float w = MyTreasureConstants.FONT_STATISTICS.getLength(s);
		g.drawText(s, 60 - w/2, MyTreasureConstants.GAME_HEIGHT - 72 - MyTreasureConstants.FONT_STATISTICS.mCharCellHeight);
		
		s = String.valueOf(this.size);
		w = MyTreasureConstants.FONT_STATISTICS.getLength(s);
		g.drawText(s, 60 - w/2, MyTreasureConstants.GAME_HEIGHT - 38 - MyTreasureConstants.FONT_STATISTICS.mCharCellHeight);
		
		s = "level objects";
		w = MyTreasureConstants.FONT_STATISTICS.getLength(s);
		g.drawText(s, 220 - w/2, MyTreasureConstants.GAME_HEIGHT - 72 - MyTreasureConstants.FONT_STATISTICS.mCharCellHeight);
		
		g.setColor(MyTreasureConstants.COLOR_SEPARATOR);
		g.fillRect(120, MyTreasureConstants.GAME_HEIGHT - 94, 2, 92);
		
		g.setColor(1f, 1f, 1f, 1f);
		// block
		g.cropImage(MyTreasureConstants.iSheet, 128, MyTreasureConstants.GAME_HEIGHT - 64, 32, 32, 0 * 32, 2 * 32, 32, 32);
		// free
		g.cropImage(MyTreasureConstants.iSheet, 168, MyTreasureConstants.GAME_HEIGHT - 64, 32, 32, 0 * 32, 3 * 32, 32, 32);
		// player
		g.cropImage(MyTreasureConstants.iSheet, 208, MyTreasureConstants.GAME_HEIGHT - 64, 32, 32, 0 * 32, 0 * 32, 32, 32);
		// skull
		g.cropImage(MyTreasureConstants.iSheet, 248, MyTreasureConstants.GAME_HEIGHT - 64, 32, 32, 0 * 32, 1 * 32, 32, 32);
		// block
		g.cropImage(MyTreasureConstants.iSheet, 288, MyTreasureConstants.GAME_HEIGHT - 64, 32, 32, 2 * 32, 0 * 32, 32, 32);
		
		float lineSize = g.getLineSize();
		g.setLineSize(5f);
		g.setColor(new int[] {255, 0, 0});
		g.drawRect(128 + 40 * this.curChoose, MyTreasureConstants.GAME_HEIGHT - 64, 31, 32);
		g.setLineSize(lineSize);
		
		if (this.time > 0) {
			g.setColor(new int[] {255, 255, 255});
			w = MyTreasureConstants.FONT_STATISTICS.getLength(this.uploadString);
			float x = 128 - w/2 - 5;
			if (x < 0) {
				x = 0;
			}
			g.fillRect(x + 1, 32, w + 8, 24);
			g.setColor(new int[] {0, 0, 0});
			g.drawRect(x + 1, 32, w + 8, 24);
			g.drawText(this.uploadString, x + 5, 51 - MyTreasureConstants.FONT_STATISTICS.mCharCellHeight);
		}
	}

}

