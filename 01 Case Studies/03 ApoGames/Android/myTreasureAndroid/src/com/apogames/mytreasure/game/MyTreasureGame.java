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
import net.gliblybits.bitsengine.graphics.opengl.BitsGLImage;

import com.apogames.mytreasure.MyTreasureConstants;
import com.apogames.mytreasure.MyTreasureModel;
import com.apogames.mytreasure.MyTreasureSoundPlayer;
import com.apogames.mytreasure.entity.MyTreasureTutorial;
import com.apogames.mytreasure.entity.MyTreasureTutorialHelp;
import com.apogames.mytreasure.userlevels.MyTreasureLevels;

public class MyTreasureGame extends MyTreasureModel {

	public static final String BACK = "back";
	public static final String INGAME_RESTART = "ingame_restart";
	public static final String INGAME_NEXT = "ingame_next";
	public static final String INGAME_PREVIOUS = "ingame_previous";
	public static final String INGAME_SOUND = "ingame_sound";
	public static final String INGAME_MUSIC = "ingame_music";
	public static final String RESULT_RESTART = "result_restart";
	public static final String RESULT_NEXT = "result_next";
	public static final String RESULT_MENU = "result_menu";
	
	private boolean bUserLevel;
	
	private int state = 0;
	private int turn = 0;
	private int breath = 0;

	private int gold = 0;
	private int silver = 0;
	private int steps = 0;
	private int curLevel = 0;
	private int max = 1;
	/**
	 * 0 = from up to down
	 * 1 = from right to left
	 * 2 = from down to up
	 * 3 = from left to right
	 */
	private int gravity = 0;
	private int[][] level = new int[12][6];
	private int[][] levelBlocks = new int[12][6];
	private final int[][] lights = new int[16][10];
	private final int[][] levelRandom = new int[16][10];
	private boolean bChange = false;
	private boolean bFall = true;
	private boolean bPlayerStop = false;
	private boolean bPlayerFall = false;
	private boolean bEditor = false;
	
	private String levelString = "";
	private String editorString = "";
	private String idleString = "";
	
	private int idleTime = 0;
	
	private BitsGLImage screenBackground;
	
	private MyTreasureTutorial tutorial;
	
	public MyTreasureGame(final MyTreasurePanel game) {
		super(game);
	}

	@Override
	public void init() {
		this.lights[0][0] = 300;
		this.lights[0][1] = 700;
		this.lights[0][2] = 500;
		this.level[0][0] = -1;

		if (this.screenBackground == null) {
			this.makeScreenBackground();
		}
		
		if (this.tutorial == null) {
			MyTreasureTutorialHelp[] points = new MyTreasureTutorialHelp[4];
			points[0] = new MyTreasureTutorialHelp(144, 224, 64, 224, 500);
			points[1] = new MyTreasureTutorialHelp(20, 224, 64, 224, 0);
			points[2] = new MyTreasureTutorialHelp(20, 224, 96, 224, 1000);
			points[3] = new MyTreasureTutorialHelp(20, 224, 64, 224, 750);
			this.tutorial = new MyTreasureTutorial(points);
		}
	}

	public void makeScreenBackground() {
		for (int y = 1; y < 16; y += 1) {
			for (int x = 0; x < 10; x += 1) {
				this.levelRandom[y][x] = (int)(Math.random() * 4);
			}
		}
	}
	
	public void setUserLevel(boolean bUserLevel) {
		this.bUserLevel = bUserLevel;
	}
	
	public void loadLevel(final boolean bEditor, final int levelInt, String levelString) {
		this.bEditor = bEditor;
		
		int level = levelInt;
		if (this.bEditor) {
			level = 0;
			if (levelString.length() > 0) {
				this.editorString = levelString;
			} else {
				levelString = this.editorString;
			}
		} else if ((levelString == null) || (levelString.length() <= 0)) {
			if (this.bUserLevel) {
				if ((MyTreasureLevels.editorLevels != null) && (MyTreasureLevels.editorLevels.length > 0)) {
					if (level < 0) {
						level = MyTreasureLevels.editorLevels.length - 1;
					}
					if (level >= MyTreasureLevels.editorLevels.length) {
						level = 0;
					}
					levelString = MyTreasureLevels.editorLevels[level];
				} else {
					level = 0;
					levelString = MyTreasureLevels.getLevel(level);
				}
			} else {
				this.max = this.getGame().getCurMax();
				int start = this.getGame().getCurStart();
				if (level < 0) {
					level = this.max - 1;
				}
				if (level >= this.max) {
					level = 0;
				}
				levelString = MyTreasureLevels.getLevel(level + start);
			}
		}
		this.levelString = levelString;
		
		this.changeState(0);
		this.curLevel = level;
		this.gravity = 0;
		this.steps = 0;
		this.bFall = true;
		this.bChange = false;
		this.turn = 0;
		this.idleTime = 0;
		this.idleString = "";
		this.gold = Integer.valueOf(levelString.substring(levelString.length() - 4, levelString.length() - 2));
		this.silver = Integer.valueOf(levelString.substring(levelString.length() - 2, levelString.length()));
		int i = Integer.valueOf(levelString.substring(0, 2));
		int j = Integer.valueOf(levelString.substring(2, 4));
		
		this.level = new int[j*2][i];
		this.levelBlocks = new int[j*2][i];
		
		for (int y = 0; y < this.level.length/2; y += 1) {
			for (int x = 0; x < this.level[0].length; x += 1) {
				this.levelBlocks[y][x] = 0;
				this.level[y][x] = Integer.valueOf(levelString.substring(y * this.level[0].length + x + 4, y * this.level[0].length + x + 5));
				if (this.level[y][x] == 0) {
					this.level[y+this.level.length/2][x] = (int)(Math.random()*4);
				}
			}
		}
		
		for (int y = 1; y < 14; y += 1) {
			for (int x = 0; x < 10; x += 1) {
				this.lights[y][x] = 0;
				if ((y < 4) || (y > 10)) {
					if ((this.curLevel == 0) && (this.getGame().getDifficulty() == MyTreasureLevelChooser.EASY)) {
						
					} else if (Math.random() * 100 < 3) {
						this.lights[y][x] = (int)(Math.random() * 900) + 20;
					}
				}
			}
		}
	}
	

	@Override
	public void touchedButton(String function) {
		if ((function.equals(MyTreasureGame.BACK)) || (function.equals(MyTreasureGame.RESULT_MENU))) {
			this.getGame().playSound(MyTreasureSoundPlayer.SOUND_CLICK);
			this.onBackButtonPressed();
		} else if ((function.equals(MyTreasureGame.INGAME_RESTART)) || (function.equals(MyTreasureGame.RESULT_RESTART))) {
			this.getGame().playSound(MyTreasureSoundPlayer.SOUND_CLICK);
			this.restartLevel();
		} else if ((function.equals(MyTreasureGame.INGAME_NEXT)) || (function.equals(MyTreasureGame.RESULT_NEXT))) {
			this.getGame().playSound(MyTreasureSoundPlayer.SOUND_CLICK);
			this.loadNextLevel();
		} else if (function.equals(MyTreasureGame.INGAME_PREVIOUS)) {
			this.getGame().playSound(MyTreasureSoundPlayer.SOUND_CLICK);
			this.loadPreviousLevel();
		} else if (function.equals(MyTreasureGame.INGAME_MUSIC)) {
			this.getGame().setMusic(!this.getGame().getButtons()[24].isSelected());
		} else if (function.equals(MyTreasureGame.INGAME_SOUND)) {
			this.getGame().setSound(!this.getGame().getButtons()[23].isSelected());
		}
	}
	
	public void onBackButtonPressed() {
		if (this.bEditor) {
			this.getGame().setEditor(false, 95);
		} else if (this.bUserLevel) {
			//this.getGame().setMenu();
			this.getGame().setLevelChooser(this.getGame().getDifficulty(), this.curLevel, false, this.bUserLevel);
		} else {
			this.getGame().setLevelChooser(this.getGame().getDifficulty(), this.curLevel, false, this.bUserLevel);
		}
	}

	@Override
	public void touchedPressed(int x, int y, int finger) {
		if ((this.turn == 0) && (this.state == 0) && (!this.bFall) && (y > 32) && (y < 448)) {
			if (x < MyTreasureConstants.GAME_WIDTH/2) {
				this.rotateLeft();
			} else {
				this.rotateRight();
			}
		}
	}

	@Override
	public void touchedReleased(int x, int y, int finger) {
	}

	@Override
	public void touchedDragged(int x, int y, int oldX, int oldY, int finger) {
		
	}
	
	@Override
	public void think(int delta) {
		if ((this.tutorial.isVisible()) && (!this.bUserLevel) && (!this.bEditor) && (this.curLevel == 0)) {
			this.tutorial.think(delta);
			if (!this.tutorial.isVisible()) {
				MyTreasureConstants.FIRST_GAME = false;
			}
		}
		
		for (int y = 0; y < 15; y += 1) {
			for (int x = 0; x < 10; x += 1) {
				if (this.lights[y][x] > 0) {
					this.lights[y][x] -= 10;
					if (this.lights[y][x] <= 0) {
						this.lights[y][x] = (int)(Math.random() * 100 + 850);
					}
				}
			}
		}
		
		if (this.state == 0) {
			if (this.breath > 0) {
				this.breath -= 10;
			}
			
			if (this.level[0][0] == -1) {
				this.loadLevel(this.bEditor, this.curLevel, "");
			} else if (this.turn == 0) {
				if (this.bFall) {
					if (!this.bChange) {
						int i = 0;
						int j = 1;
						if (this.gravity == 0) {
							for (int y = this.level.length/2 - 2; y > 0; y -= 1) {
								for (int x = 1; x < this.level[0].length - 1; x += 1) {
									if (this.level[y][x] == 2) {
										if (((this.level[y+j][x+i] == 1) || (this.level[y+j][x+i] == 3)) && (this.level[y+j][x+i] < 4)) {
											this.level[y][x] = 1;
											this.level[y+j][x+i] = 2;
											if (this.level[y+j][x+i] == 3) {
												this.level[y+j][x+i] = 0;
											}
											this.bPlayerFall = true;
											this.bChange = true;
											this.levelBlocks[y+j][x+i] = -32;
										} else if (bPlayerStop) {
											this.bPlayerStop = false;
											this.breath = 100;
										}
									}
									if (this.level[y][x] == 4) {
										if (this.level[y+j][x+i] == 1) {
											this.level[y][x] = 1;
											this.level[y+j][x+i] = 4;

											this.bChange = true;
											this.levelBlocks[y+j][x+i] = -32;

										}
									}
								}
							}
						}
						if (this.gravity == 2) {
							j = -1;
							for (int y = 1; y < this.level.length/2 - 1; y += 1) {
								for (int x = 1; x < this.level[0].length - 1; x += 1) {
									if (this.level[y][x] == 2) {
										if (((this.level[y+j][x+i] == 1) || (this.level[y+j][x+i] == 3)) && (this.level[y+j][x+i] < 4)) {
											this.level[y][x] = 1;
											this.level[y+j][x+i] = 2;
											if (this.level[y+j][x+i] == 3) {
												this.level[y+j][x+i] = 0;
											}
											this.bPlayerFall = true;
											this.bChange = true;
											this.levelBlocks[y+j][x+i] = 32;
										} else if (this.bPlayerStop) {
											this.bPlayerStop = false;
											this.breath = 100;
										}
									}
									if (this.level[y][x] == 4) {
										if (this.level[y+j][x+i] == 1) {
											this.level[y][x] = 1;
											this.level[y+j][x+i] = 4;
											
											this.bChange = true;
											this.levelBlocks[y+j][x+i] = 32;
										}
									}
								}
							}
						}
						if (this.gravity == 1) {
							i = 1;
							j = 0;
							for (int y = this.level.length/2 - 2; y > 0; y -= 1) {
								for (int x = this.level[0].length - 2; x > 0; x -= 1) {
									if (this.level[y][x] == 2) {
										if (((this.level[y+j][x+i] == 1) || (this.level[y+j][x+i] == 3)) && (this.level[y+j][x+i] < 4)) {
											this.level[y][x] = 1;
											this.level[y+j][x+i] = 2;
											if (this.level[y+j][x+i] == 3) {
												this.level[y+j][x+i] = 0;
											}
											this.bPlayerFall = true;
											this.bChange = true;
											this.levelBlocks[y+j+this.levelBlocks.length/2][x+i] = -32;
										} else if (this.bPlayerStop) {
											this.bPlayerStop = false;
											this.breath = 100;
										}
									}
									if (this.level[y][x] == 4) {
										if (this.level[y][x+i] == 1) {
											this.level[y][x] = 1;
											this.level[y][x+i] = 4;
											
											this.bChange = true;
											this.levelBlocks[y+this.levelBlocks.length/2][x+i] = -32;
										}
									}
								}
							}
						}
						if (this.gravity == 3) {
							i = -1;
							j = 0;
							for (int y = this.level.length/2 - 2; y > 0; y -= 1) {
								for (int x = 1; x < this.level[0].length - 1; x += 1) {
									if (this.level[y][x] == 2) {
										if (((this.level[y+j][x+i] == 1) || (this.level[y+j][x+i] == 3)) && (this.level[y+j][x+i] < 4)) {
											this.level[y][x] = 1;
											this.level[y+j][x+i] = 2;
											if (this.level[y+j][x+i] == 3) {
												this.level[y+j][x+i] = 0;
											}
											this.bPlayerFall = true;
											this.bChange = true;
											this.levelBlocks[y+j+this.levelBlocks.length/2][x+i] = 32;
										} else if (this.bPlayerStop) {
											this.bPlayerStop = false;
											this.breath = 100;
										}
									}
									if (this.level[y][x] == 4) {
										if (this.level[y][x+i] == 1) {
											this.level[y][x] = 1;
											this.level[y][x+i] = 4;
											
											this.bChange = true;
											this.levelBlocks[y+this.levelBlocks.length/2][x+i] = 32;
										}
									}
								}
							}
						}
						if (!this.bChange) {
							this.bFall = false;
							boolean bWin = true;
							for (int y = 1; y < this.level.length/2; y += 1) {
								for (int x = 1; x < this.level[0].length; x += 1) {
									if (this.level[y][x] == 3) {
										bWin = false;
										break;
									}
								}
							}
							if (bWin) {
								if (this.bEditor) {
									int step = this.steps;
									if (step >= 95) {
										step = 95;
									}
									this.getGame().setEditor(true, step);
								} else {
									this.changeState(1);
									this.saveSolvedLevel();
								}
							}
						}
					} else {
						for (int y = this.level.length/2 - 1; y >= 0; y -= 1) {
							for (int x = 0; x < this.level[0].length; x += 1) {
								if (this.levelBlocks[y][x] < 0) {
									this.levelBlocks[y][x] += 2.5f;
									if (this.levelBlocks[y][x] >= 0) this.levelBlocks[y][x] = 0;
									
									if (this.level[y][x] == 2) {
										if (this.levelBlocks[y][x] == 0)	{
											this.bPlayerStop = true;
											this.bPlayerFall = false;
										} else {
											this.bPlayerFall = true;
										}
									}
								}
								if (this.levelBlocks[y][x] > 0) {
									this.levelBlocks[y][x] -= 2.5f;
									if (this.levelBlocks[y][x] <= 0) this.levelBlocks[y][x] = 0;
									
									if (this.level[y][x] == 2) {
										if (this.levelBlocks[y][x] == 0)	{
											this.bPlayerStop = true;
											this.bPlayerFall = false;
										} else {
											this.bPlayerFall = true;
										}
									}
								}
								if (this.levelBlocks[y+this.levelBlocks.length/2][x] < 0) {
									this.levelBlocks[y+this.levelBlocks.length/2][x] += 3f;
									if (this.levelBlocks[y+this.levelBlocks.length/2][x] >= 0) this.levelBlocks[y+this.levelBlocks.length/2][x] = 0;
									
									if (this.level[y][x] == 2) {
										if (this.levelBlocks[y+this.levelBlocks.length/2][x] == 0)	{
											this.bPlayerStop = true;
											this.bPlayerFall = false;
										} else {
											this.bPlayerFall = true;
										}
									}
								}
								if (this.levelBlocks[y+this.levelBlocks.length/2][x] > 0) {
									this.levelBlocks[y+this.levelBlocks.length/2][x] -= 3f;
									if (this.levelBlocks[y+this.levelBlocks.length/2][x] <= 0) this.levelBlocks[y+this.levelBlocks.length/2][x] = 0;
									
									if (this.level[y][x] == 2) {
										if (this.levelBlocks[y+this.levelBlocks.length/2][x] == 0)	{
											this.bPlayerStop = true;
											this.bPlayerFall = false;
										} else {
											this.bPlayerFall = true;
										}
									}
								}
							}
						}
						this.bChange = false;
						for (int y = 0; y < this.level.length/2; y += 1) {
							for (int x = 0; x < this.level[0].length; x += 1) {
								if ((this.levelBlocks[y][x] != 0) || (this.levelBlocks[y+this.levelBlocks.length/2][x] != 0)) {
									this.bChange = true;
								}										
							}
						}
						if (!this.bChange) {
							this.think(delta);
						}
					}
				} else {
					this.idleTime += delta;
					if ((this.idleTime - delta < MyTreasureConstants.START_IDLE_TEXT_TIME) && (this.idleTime >= MyTreasureConstants.START_IDLE_TEXT_TIME)) {
						this.idleString = MyTreasureConstants.TEXT_IDLE[(int)(Math.random() * MyTreasureConstants.TEXT_IDLE.length)];
					}
					if ((this.idleTime - delta < MyTreasureConstants.END_IDLE_TEXT_TIME) && (this.idleTime >= MyTreasureConstants.END_IDLE_TEXT_TIME)) {
						this.idleString = "";
						this.idleTime = 0;
					}
				}
			} else {
				if (this.turn < 0) {
					this.turn += 5;
					if (this.turn >= 0) {
						this.turn = 0;
					}
				}
				if (this.turn > 0) {
					this.turn -= 5;
					if (this.turn <= 0) {
						this.turn = 0;
					}
				}
			}
		} else {
			
		}
	}
	
	private void saveSolvedLevel() {
		if (!this.bEditor) {
			int change = 0;
			int skulls = 1;
			if (this.steps <= this.gold) {
				skulls = 3;
			} else if (this.steps <= this.silver) {
				skulls = 2;
			}
			if (this.getGame().getSolvedLevel().containsKey(this.levelString)) {
				if (skulls > this.getGame().getSolvedLevel().get(this.levelString)) {
					change = skulls - this.getGame().getSolvedLevel().get(this.levelString);
					this.getGame().getSolvedLevel().remove(this.levelString);
					this.getGame().getSolvedLevel().put(this.levelString, skulls);
				}
			} else {
				this.getGame().getSolvedLevel().put(this.levelString, skulls);
				change = skulls;
			}
			if ((!this.bUserLevel) && (change > 0)) {
				this.getGame().setCurSkulls(this.getGame().getCurSkulls() + change);
			}
		}
	}
	
	private void rotateLeft() {
		this.idleTime = 0;
		this.idleString = "";
		this.bFall = true;
		this.gravity -= 1;
		if (this.gravity < 0) this.gravity = 3;
		this.turn = -90;
		this.steps += 1;
	}
	
	private void rotateRight() {
		this.idleTime = 0;
		this.idleString = "";
		this.bFall = true;
		this.gravity += 1;
		if (this.gravity > 3) this.gravity = 0;
		this.turn = 90;
		this.steps += 1;
	}
	
	private void changeState(final int state) {
		if (this.state != state) {
			this.state = state;
			boolean bVisible = true;
			if (this.state == 1) {
				bVisible = false;
				
				this.idleString = MyTreasureConstants.TEXT_WIN[(int)(Math.random() * MyTreasureConstants.TEXT_WIN.length)];
			}
			this.getGame().getButtons()[6].setVisible(bVisible);
			this.getGame().getButtons()[8].setVisible(bVisible);
			this.getGame().getButtons()[9].setVisible(bVisible);
			this.getGame().getButtons()[10].setVisible(bVisible);
			
			this.getGame().getButtons()[11].setVisible(!bVisible);
			this.getGame().getButtons()[12].setVisible(!bVisible);
			this.getGame().getButtons()[13].setVisible(!bVisible);
			
			if (this.getGame().getButtons()[11].isVisible()) {
				if (this.steps <= this.gold) {
					this.getGame().playSound(MyTreasureSoundPlayer.SOUND_WIN_3);
				} else if (this.steps <= this.silver) {
					this.getGame().playSound(MyTreasureSoundPlayer.SOUND_WIN_2);
				} else {
					this.getGame().playSound(MyTreasureSoundPlayer.SOUND_WIN_1);
				}
			}
		}
	}
	
	private void loadNextLevel() {
		this.curLevel += 1;
		this.level[0][0] = -1;
		this.changeState(0);
	}
	
	private void loadPreviousLevel() {
		this.curLevel -= 1;
		this.level[0][0] = -1;
		this.changeState(0);
	}
	
	private void restartLevel() {
		this.level[0][0] = -1;
		this.changeState(0);
	}

	@Override
	public void render(BitsGLGraphics g) {
		// Renderabschnitt
		// Hintergrund malen
//		g.cropImage(this.screenBackground, 0, 0, MyTreasureConstants.GAME_WIDTH, MyTreasureConstants.GAME_HEIGHT, 0, 0, MyTreasureConstants.GAME_WIDTH, MyTreasureConstants.GAME_HEIGHT);

		for (int y = 1; y < 15; y += 1) {
			for (int x = 0; x < 10; x += 1) {
				g.cropImage(MyTreasureConstants.iSheet, (x) * 32, (y) * 32, 32, 32, (int)(this.levelRandom[y][x]) * 32, 3 * 32, 32, 32);
			}
		}
		g.cropImage(MyTreasureConstants.iSheet, 0, 0, 192, 32, 0, 128, 192, 32);
		g.cropImage(MyTreasureConstants.iSheet, 192, 0, 64, 32, 128, 64, 64, 32);
		g.cropImage(MyTreasureConstants.iSheet, 256, 0, 64, 32, 128, 96, 64, 32);
		
		g.cropImage(MyTreasureConstants.iSheet, 0, 448, 320, 32, 384, 480, 320, 32);
		
		for (int y = 1; y < this.lights.length; y += 1) {
			for (int x = 0; x < this.lights[0].length; x += 1) {
				if (this.lights[y][x] > 0) {
					g.cropImage(MyTreasureConstants.iSheet, (x) * 32 - 16, (y) * 32 - 16, 64, 64, 8 * 32, 32, 64, 64);
					if (this.lights[y][x] > 450) {
						g.cropImage(MyTreasureConstants.iSheet, (x) * 32, (y) * 32, 32, 32, 1 * 32, 0 * 32, 32, 32);
					} else {
						g.cropImage(MyTreasureConstants.iSheet, (x) * 32, (y) * 32, 32, 32, 3 * 32, 1 * 32, 32, 32);
					}
				}
			}
		}
		
		int playerX = 0, playerY = 0;			
		int i = MyTreasureConstants.GAME_WIDTH/2 - this.level[0].length * 16;
		int j = MyTreasureConstants.GAME_HEIGHT/2 - this.level.length/2 * 16;
		if (this.state == 0) {
			g.setRotation(90 * this.gravity - this.turn, MyTreasureConstants.GAME_WIDTH/2, MyTreasureConstants.GAME_HEIGHT/2);
			
			for (int y = 0; y < this.level.length/2; y += 1) {
				for (int x = 0; x < this.level[0].length; x += 1) {
					if (this.level[y][x] == 3) {
						g.cropImage(MyTreasureConstants.iSheet, i + (x) * 32 - 16, j +(y) * 32 - 16, 64, 64, 8 * 32, 32, 64, 64);
						g.cropImage(MyTreasureConstants.iSheet, i + (x) * 32, j + (y) * 32, 32, 32, 0 * 32, 1 * 32, 32, 32);
					}
					if (this.level[y][x] == 4) {
						g.cropImage(MyTreasureConstants.iSheet, i + (x) * 32 + (int)(this.levelBlocks[y+this.levelBlocks.length/2][x]), j + (y) * 32 + (int)(this.levelBlocks[y][x]), 32, 32, 2 * 32, 0 * 32, 32, 32);
					}
					if (this.level[y][x] == 0) {
						g.cropImage(MyTreasureConstants.iSheet, i + (x) * 32, j + (y) * 32, 32, 32, this.level[y + this.level.length/2][x] * 32, 2 * 32, 32, 32);
					}
					if (this.level[y][x] == 2) {
						playerX = x;
						playerY = y;
						int curX = 0;
						int curY = 0;
						if (this.bPlayerFall) {
							curX = 3;
						}
						if (this.breath > 0) {
							curX = 2;
							curY = 1;
						}
						if (this.gravity%2!=0) {
							curY = 0;
							curX = 4;
							if (this.bPlayerFall) {
								curX = 6;
							}
							if (this.breath > 0) {
								curX = 5;
							}
						}
						if ((this.gravity == 2) || (this.gravity == 1)) {
							curX = 7;
							curY = 3;
							if (this.gravity == 1) {
								curY = 4;
							}
							if (this.bPlayerFall) {
								curX = 8;
							}
							if (this.breath > 0) {
								curX = 9;
							}
						}
						g.cropImage(MyTreasureConstants.iSheet, i + (x) * 32 + (int)(this.levelBlocks[y+this.levelBlocks.length/2][x]), j + (y) * 32 + (int)(this.levelBlocks[y][x]), 32, 32, curX * 32, curY * 32, 32, 32);
					}
				}
			}
			g.setRotation(0, MyTreasureConstants.GAME_WIDTH/2, MyTreasureConstants.GAME_HEIGHT/2);
			
			if (this.gravity == 1) {
				int save = playerY;
				playerY = playerX;
				playerX = this.level.length/2 - save - 1;
			} else if (this.gravity == 2) {
				playerX = this.level[0].length - playerX - 1;
				playerY = this.level.length/2 - playerY - 1;
			} else if (this.gravity == 3) {
				int save = playerX;
				playerX = playerY;
				playerY = this.level[0].length - save - 1;
			}
			this.drawSpeech(g, i + (playerX) * 32, j + (playerY) * 32 - 4);
		}
		
		String s = "";
		float w = 0;

		g.setFont(MyTreasureConstants.fontBig);
		if (this.state == 1) {
			g.setColor(175,175,175,255);
			
			s = "Congratulation";
			w = MyTreasureConstants.fontBig.getLength(s);
			g.drawText(s, 160 - w/2, 96 - MyTreasureConstants.fontBig.mCharCellHeight);
			
			g.setFont(MyTreasureConstants.fontMedium);

			s = "You needed";
			w = MyTreasureConstants.fontMedium.getLength(s);
			g.drawText(s, 160 - w/2, 244 - MyTreasureConstants.fontMedium.mCharCellHeight);
			
			s = "moves.";
			w = MyTreasureConstants.fontMedium.getLength(s);
			g.drawText(s, 160 - w/2, 304 - MyTreasureConstants.fontMedium.mCharCellHeight);
			
			if (this.steps <= this.gold) {
				g.setColor(255, 235, 0, 255);
			}
			if (this.steps > this.silver) {
				g.setColor(215, 100, 50, 255);
			}
			s = String.valueOf(this.steps);
			w = MyTreasureConstants.fontMedium.getLength(s);
			g.drawText(s, 160 - w/2, 274 - MyTreasureConstants.fontMedium.mCharCellHeight);
			
			g.setColor(1f,1f,1f,1f);
			g.cropImage(MyTreasureConstants.iSheet, 24, 240, 64, 64, 8 * 32, 32, 64, 64);
			if (this.lights[0][1] > 450) {
				g.cropImage(MyTreasureConstants.iSheet, 40, 256, 32, 32, 1 * 32, 0 * 32, 32, 32);
			} else {
				g.cropImage(MyTreasureConstants.iSheet, 40, 256, 32, 32, 3 * 32, 1 * 32, 32, 32);
			}
			
			
			g.cropImage(MyTreasureConstants.iSheet, 240, 240, 64, 64, 8 * 32, 32, 64, 64);
			if (this.lights[0][0] > 450) {
				g.cropImage(MyTreasureConstants.iSheet, 256, 256, 32, 32, 1 * 32, 0 * 32, 32, 32);
			} else {
				g.cropImage(MyTreasureConstants.iSheet, 256, 256, 32, 32, 3 * 32, 1 * 32, 32, 32);
			}
			
			if (this.lights[0][2] > 450) {
				g.cropImage(MyTreasureConstants.iSheet, 144, 144, 32, 32, 4 * 32, 1 * 32, 32, 32);
			} else {
				g.cropImage(MyTreasureConstants.iSheet, 144, 144, 32, 32, 5 * 32, 1 * 32, 32, 32);
			}
			this.drawSpeech(g, 144, 140);
			
			g.cropImage(MyTreasureConstants.iSheet, 80, 144, 32, 32, 2 * 32, 0 * 32, 32, 32);
			g.cropImage(MyTreasureConstants.iSheet, 208, 144, 32, 32, 2 * 32, 0 * 32, 32, 32);
			
			if (this.steps <= this.gold) {
				g.cropImage(MyTreasureConstants.iSheet, 108 - 16, 332- 16, 64, 64, 8 * 32, 32, 64, 64);
				g.cropImage(MyTreasureConstants.iSheet, 108, 332, 32, 32, 0 * 32, 1 * 32, 32, 32);
				g.cropImage(MyTreasureConstants.iSheet, 144 - 16, 332- 16, 64, 64, 8 * 32, 32, 64, 64);
				g.cropImage(MyTreasureConstants.iSheet, 144, 332, 32, 32, 0 * 32, 1 * 32, 32, 32);				
				g.cropImage(MyTreasureConstants.iSheet, 180 - 16, 332- 16, 64, 64, 8 * 32, 32, 64, 64);
				g.cropImage(MyTreasureConstants.iSheet, 180, 332, 32, 32, 0 * 32, 1 * 32, 32, 32);
			} else if (this.steps <= this.silver) {
				g.cropImage(MyTreasureConstants.iSheet, 126 - 16, 332- 16, 64, 64, 8 * 32, 32, 64, 64);
				g.cropImage(MyTreasureConstants.iSheet, 126, 332, 32, 32, 6 * 32, 1 * 32, 32, 32);
				g.cropImage(MyTreasureConstants.iSheet, 162 - 16, 332- 16, 64, 64, 8 * 32, 32, 64, 64);
				g.cropImage(MyTreasureConstants.iSheet, 162, 332, 32, 32, 6 * 32, 1 * 32, 32, 32);
			} else {
				g.cropImage(MyTreasureConstants.iSheet, 144 - 16, 332- 16, 64, 64, 8 * 32, 32, 64, 64);
				g.cropImage(MyTreasureConstants.iSheet, 144, 332, 32, 32, 1 * 32, 1 * 32, 32, 32);
			}
		} else if ((!this.bEditor) && (!this.bUserLevel)) {
			if ((this.curLevel == 0) && (this.getGame().getDifficulty() == MyTreasureLevelChooser.EASY)) {
				g.setColor(175,175,175);
				g.setFont(MyTreasureConstants.fontMedium);
				for (i = 0; i < MyTreasureConstants.FIRST_COLLECT.length; i++) {
					s = MyTreasureConstants.FIRST_COLLECT[i];
					w = MyTreasureConstants.fontMedium.getLength(s);
					g.drawText(s, MyTreasureConstants.GAME_WIDTH/2 - w/2, 65 + 40 * i - MyTreasureConstants.fontMedium.mCharCellHeight);
				}
				
				g.setFont(MyTreasureConstants.fontVerySmall);
				for (i = 0; i < MyTreasureConstants.FIRST_MOVE.length; i++) {
					s = MyTreasureConstants.FIRST_MOVE[i];
					w = MyTreasureConstants.fontVerySmall.getLength(s);
					g.drawText(s, MyTreasureConstants.GAME_WIDTH/2 - w/2, 355 + 22 * i - MyTreasureConstants.fontVerySmall.mCharCellHeight);
				}
			}
		}
		g.setFont(MyTreasureConstants.fontSmall);
		g.setColor(50,50,50);
		
		if (this.bUserLevel) {
			s = "Level "+String.valueOf(curLevel+1)+"/"+MyTreasureLevels.editorLevels.length;
		} else {
			s = "Level "+String.valueOf(curLevel+1)+"/"+this.max;
		}
		w = MyTreasureConstants.fontSmall.getLength(s);
		g.drawText(s, 160 - w/2, 473 - MyTreasureConstants.fontSmall.mCharCellHeight);

		g.setColor(1f, 1f, 1f, 1f);
		if ((!this.bEditor) && (this.getGame().getSolvedLevel().containsKey(this.levelString))) {
			g.cropImage(MyTreasureConstants.iSheet, 160 + w/2 + 2, 458, 12, 12, 8 * 4 + 16 * (this.getGame().getSolvedLevel().get(this.levelString) - 1), 120 * 4, 12, 12);
		}
		

		if (this.silver > 0) {
			w = (int)((this.steps) * 100 / (this.silver));
			if (w > 98) {
				w = 98;
			}
			if (w < 0) {
				w = 0;
			}
			
			if (this.steps <= this.gold) {
				// gold
				g.setColor(255, 235, 50);
				g.fillRect(4, 8, (int)(185 - w * 1.84f), 20);
				g.setColor(255, 255, 200);
				g.fillRect(4, 12, (int)(185 - w * 1.84f), 4);
				g.setColor(215, 150, 50);
				g.fillRect(4, 24, (int)(185 - w * 1.84f), 4);
			} else if (this.steps > this.silver) {
				// bronze
				g.setColor(215, 100, 50);
				g.fillRect(4, 8, (int)(185 - w * 1.84f), 20);
				g.setColor(235, 185, 150);
				g.fillRect(4, 12, (int)(185 - w * 1.84f), 4);
				g.setColor(185, 90, 40);
				g.fillRect(4, 24, (int)(185 - w * 1.84f), 4);
			} else {
				// silver
				g.setColor(150, 150, 150);
				g.fillRect(4, 8, (int)(185 - w * 1.84f), 20);
				g.setColor(235, 235, 235);
				g.fillRect(4, 12, (int)(185 - w * 1.84f), 4);
				g.setColor(135, 135, 135);
				g.fillRect(4, 24, (int)(185 - w * 1.84f), 4);
			}
			g.setColor(MyTreasureConstants.COLOR_SEPARATOR);
			g.fillRect((int)(185 - 96 * 1.85f), 8, 2, 20);
			
			int si = (int)((this.gold) * 100 / (this.silver));
			g.fillRect((int)(185 - (si) * 1.85f), 8, 2, 20);
		}
		
		g.setColor(1f, 1f, 1f, 1f);
		if (MyTreasureConstants.FIRST_GAME) {
			this.tutorial.render(g);
		}
	}
	
	private void drawSpeech(final BitsGLGraphics g, final int x, final int y) {
		if (this.idleString.length() > 0) {
			g.setFont(MyTreasureConstants.FONT_STATISTICS);
			float w = MyTreasureConstants.FONT_STATISTICS.getLength(this.idleString);
			g.setColor(1f, 1f, 1f, 1f);
			g.fillRect(x + 16 - w/2 - 2, y - 24, w + 4, 2);
			g.fillRect(x + 16 - w/2 - 4, y - 22, w + 8, 19);
			g.fillRect(x + 16 - w/2 - 2, y - 3, w + 4, 2);
			for (int k = 3; k > 0; k--) {
				g.fillRect(x + 16 - k, y - 1 + (k-3)*-1, k * 2, 1);	
			}
			
			g.setColor(MyTreasureConstants.COLOR_SEPARATOR);
			g.drawText(this.idleString, x + 16 - w/2, y - 23);
			g.setColor(1f, 1f, 1f, 1f);
		}
	}

}
