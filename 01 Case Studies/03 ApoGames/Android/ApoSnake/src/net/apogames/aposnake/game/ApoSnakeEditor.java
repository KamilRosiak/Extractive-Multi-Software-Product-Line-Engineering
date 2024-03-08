package net.apogames.aposnake.game;

import net.apogames.aposnake.ApoSnake;
import net.apogames.aposnake.ApoSnakeModel;
import net.apogames.aposnake.entity.ApoSnakeString;

import net.gliblybits.bitsengine.render.BitsGraphics;
import net.gliblybits.bitsengine.utils.BitsLog;

public class ApoSnakeEditor extends ApoSnakeModel {

	private final int EMPTY = 0;
	private final int RED_SNAKE = 1;
	private final int RED_WALL = 2;
	private final int RED_COIN = 3;
	private final int BLUE_SNAKE = 4;
	private final int BLUE_WALL = 5;
	private final int BLUE_COIN = 6;
	private final int GREEN_SNAKE = 7;
	private final int GREEN_WALL = 8;
	private final int GREEN_COIN = 9;
	
	public static final String BACK = "back";
	public static final String UPLOAD = "upload";
	public static final String TEST = "test";
	public static final String XPLUS = "+";
	public static final String XMINUS = "-";
	public static final String YPLUS = " + ";
	public static final String YMINUS = " - ";

	private ApoSnakeString uploadString;
	
	private int[][] level = null;
	
	private int curSelect;
	
	public ApoSnakeEditor(ApoSnakePanel game) {
		super(game);
	}

	@Override
	public void init() {
		this.getStringWidth().put(ApoSnakeEditor.BACK, (int)(ApoSnakeMenu.game_font.getLength(ApoSnakeEditor.BACK)));
		this.getStringWidth().put(ApoSnakeEditor.TEST, (int)(ApoSnakeMenu.game_font.getLength(ApoSnakeEditor.TEST)));
		this.getStringWidth().put(ApoSnakeEditor.UPLOAD, (int)(ApoSnakeMenu.game_font.getLength(ApoSnakeEditor.UPLOAD)));
		this.getStringWidth().put(ApoSnakeEditor.XMINUS, (int)(ApoSnakeMenu.game_font.getLength(ApoSnakeEditor.XMINUS)));
		this.getStringWidth().put(ApoSnakeEditor.XPLUS, (int)(ApoSnakeMenu.game_font.getLength(ApoSnakeEditor.XPLUS)));
		this.getStringWidth().put(ApoSnakeEditor.YMINUS, (int)(ApoSnakeMenu.game_font.getLength(ApoSnakeEditor.YMINUS)));
		this.getStringWidth().put(ApoSnakeEditor.YPLUS, (int)(ApoSnakeMenu.game_font.getLength(ApoSnakeEditor.YPLUS)));
		
		String s = "ApoSnake - Editor";
		this.getStringWidth().put(s, (int) ApoSnakeMenu.game_font.getLength(s));
		
		if (this.level == null) {
			this.makeLevel();
		}
		
		this.checkTestLevel();
	}
	
	private void makeLevel() {
		this.level = new int[][] {
				{6,6,6,5,0},
				{6,3,6,6,0},
				{6,0,0,6,0},
				{6,6,0,0,0}
		};
	}
	
	public void setLevelSolved(boolean bSolved) {
		if ((!bSolved) || (!ApoSnake.isOnline())) {
			this.getGame().getButtons()[8].setVisible(false);
		} else {
			this.getGame().getButtons()[8].setVisible(true);
		}
	}
	
	private void checkTestLevel() {
		int snakes = 0;
		int coins = 0;
		for (int y = 0; y < this.level.length; y += 1) {
			for (int x = 0; x < this.level[y].length; x += 1) {
				if ((this.level[y][x] >= 1) && (this.level[y][x] <= 4)) {
					snakes += 1;
				}
				if ((this.level[y][x] >= 7) && (this.level[y][x] <= 10)) {
					snakes += 1;
				}
				if ((this.level[y][x] >= 13) && (this.level[y][x] <= 16)) {
					snakes += 1;
				}
				if ((this.level[y][x] == 5) || (this.level[y][x] == 11) || (this.level[y][x] == 17)) {
					coins += 1;
				}
			}
		}
		if ((snakes > 0) && (coins > 0)) {
			this.getGame().getButtons()[7].setVisible(true);
		} else {
			this.getGame().getButtons()[7].setVisible(false);
			this.getGame().getButtons()[8].setVisible(false);
		}
	}

	@Override
	public void touchedPressed(int x, int y, int finger) {
		if (y > 505) {
			for (int i = 0; i <= this.GREEN_COIN; i++) {
				if ((x >= 5 + i * 47) && (x < 45 + i * 47) && (y >= 520) && (y < 560)) {
					this.curSelect = i;
				}
			}
		} else {
			int changeX = (480 - level[0].length * 30)/2;
			int changeY = (480 - level.length * 30)/2 + ApoSnakePuzzleGame.changeY;
			
			if ((y >= changeY) && (y < changeY + level.length * 30) &&
				(x >= changeX) && (x < changeX + level[0].length * 30)) {
				int newY = (y - changeY)/30;
				int newX = (x - changeX)/30;
				if (this.curSelect == this.EMPTY) {
					level[newY][newX] = 0;
				} else if (this.curSelect == this.RED_SNAKE) {
					if ((level[newY][newX] >= 1) && (level[newY][newX] <= 4)) {
						level[newY][newX] += 1;
						if (level[newY][newX] > 4) {
							level[newY][newX] = 1;
						}
					} else {
						level[newY][newX] = 1;
					}
				} else if (this.curSelect == this.RED_COIN) {
					level[newY][newX] = 5;
				} else if (this.curSelect == this.RED_WALL) {
					level[newY][newX] = 6;
				} else if (this.curSelect == this.BLUE_SNAKE) {
					if ((level[newY][newX] >= 7) && (level[newY][newX] <= 10)) {
						level[newY][newX] += 1;
						if (level[newY][newX] > 10) {
							level[newY][newX] = 7;
						}
					} else {
						level[newY][newX] = 7;
					}
				} else if (this.curSelect == this.BLUE_COIN) {
					level[newY][newX] = 11;
				} else if (this.curSelect == this.BLUE_WALL) {
					level[newY][newX] = 12;
				} else if (this.curSelect == this.GREEN_SNAKE) {
					if ((level[newY][newX] >= 13) && (level[newY][newX] <= 16)) {
						level[newY][newX] += 1;
						if (level[newY][newX] > 16) {
							level[newY][newX] = 13;
						}
					} else {
						level[newY][newX] = 13;
					}
				} else if (this.curSelect == this.GREEN_COIN) {
					level[newY][newX] = 17;
				} else if (this.curSelect == this.GREEN_WALL) {
					level[newY][newX] = 18;
				}
				this.checkTestLevel();
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
	public void touchedButton(String function) {
		if (function.equals(ApoSnakeEditor.BACK)) {
			this.onBackButtonPressed();
		} else if (function.equals(ApoSnakeEditor.TEST)) {
			String levelString = this.getLevelString();
			BitsLog.d("levelString", levelString);
			this.getGame().setPuzzleGame(-1, levelString, false);
		} else if (function.equals(ApoSnakeEditor.UPLOAD)) {
			this.setLevelSolved(false);
			this.uploadString = new ApoSnakeString(240, 470, 20, "Uploading ...", true, 200, true);
			
			Thread t = new Thread(new Runnable() {

				public void run() {
					ApoSnakeEditor.this.uploadString();
				}
	 		});
	 		t.start();
		} else if (function.equals(ApoSnakeEditor.XMINUS)) {
			if (this.level[0].length - 1 >= 3) {
				this.newLevelSize(this.level[0].length - 1, this.level.length);
			}
		} else if (function.equals(ApoSnakeEditor.XPLUS)) {
			if (this.level[0].length + 1 <= 14) {
				this.newLevelSize(this.level[0].length + 1, this.level.length);
			}
		} else if (function.equals(ApoSnakeEditor.YMINUS)) {
			if (this.level.length - 1 >= 3) {
				this.newLevelSize(this.level[0].length, this.level.length - 1);
			}
		} else if (function.equals(ApoSnakeEditor.YPLUS)) {
			if (this.level.length + 1 <= 14) {
				this.newLevelSize(this.level[0].length, this.level.length + 1);
			}
		}
	}
	
	private void newLevelSize(int newWidth, int newHeight) {
		int[][] newLevel = new int[newHeight][newWidth];
		for (int y = 0; y < newHeight && y < level.length; y++) {
			for (int x = 0; x < newWidth && x < level[y].length; x++) {
				newLevel[y][x] = this.level[y][x];
			}
		}
		this.level = newLevel;
		this.checkTestLevel();
	}
	
	private void uploadString() {
		if (this.getGame().getUserlevels().addLevel(this.getLevelString())) {
			this.uploadString = new ApoSnakeString(240, 470, 20, "Uploading successfully", true, 20, true);
			this.getGame().loadUserlevels();
		} else {
			this.uploadString = new ApoSnakeString(240, 470, 20, "Uploading failed", true, 20, true);
		}
	}
	
	public void onBackButtonPressed() {
		this.getGame().setMenu();
	}
	
	private String getLevelString() {
		String level = "";
		char c = (char)(this.level[0].length + 96);
		level += String.valueOf(c);
		c = (char)(this.level.length + 96);
		level += String.valueOf(c);
		for (int y = 0; y < this.level.length; y++) {
			for (int x = 0; x < this.level[0].length; x++) {
				if (this.level[y][x] >= 10) {
					c = (char)(87 + this.level[y][x]);
					level += String.valueOf(c);
				} else {
					level += String.valueOf(this.level[y][x]);
				}
			}
		}
		return level;
	}

	@Override
	public void think(int delta) {

		if (this.uploadString != null) {
			this.uploadString.think(delta);
			
			if (!this.uploadString.isVisible()) {
				this.uploadString = null;
			}
		}
	}

	@Override
	public void render(BitsGraphics g) {
		g.setColor(128, 128, 128, 255);
		g.drawFilledRect(0,0,480,ApoSnakePuzzleGame.changeY);
		g.drawFilledRect(0,480 + ApoSnakePuzzleGame.changeY,480,160 - ApoSnakePuzzleGame.changeY);
		
		g.setColor(0f/255f, 0f/255f, 0f/255f, 1.0f);
		g.drawRect(0,0,480,ApoSnakePuzzleGame.changeY);
		g.drawRect(0,480 + ApoSnakePuzzleGame.changeY,480,160 - ApoSnakePuzzleGame.changeY);
		
		String s = "ApoSnake - Editor";
		this.getGame().drawString(g, s, 240, -4, ApoSnakeMenu.game_font);
		
		this.getGame().renderButtons(g, ApoSnakeMenu.game_font);
		
		int changeX = (480 - level[0].length * 30)/2;
		int changeY = (480 - level.length * 30)/2 + ApoSnakePuzzleGame.changeY;
		g.setColor(0, 0, 0, 255);
		g.drawRect(changeX, changeY, level[0].length * 30, level.length * 30);

		for (int y = 0; y < level.length; y++) {
			for (int x = 0; x < level[0].length; x++) {
				g.setColor(0, 0, 0, 255);
				g.drawRect(changeX + x * 30, changeY + y * 30, 30, 30);
				if ((level[y][x] == 5) || (level[y][x] == 11) || (level[y][x] == 17)) {
					g.setColor(255, 0, 0);
					if (level[y][x] == 11) {
						g.setColor(0, 120, 255);							
					} else if (level[y][x] == 17) {
						g.setColor(0, 255, 0);							
					}
					g.drawFilledOval(changeX + 15 + x * 30, changeY + 15 + y * 30, 5, 5);
					g.setColor(0, 0, 0, 255);
					g.drawOval(changeX + 15 + x * 30, changeY + 15 + y * 30, 5, 5);
				}
				if ((level[y][x] == 6) || (level[y][x] == 12) || (level[y][x] == 18)) {
					if (level[y][x] == 6) {
						this.drawWall(g, x, y, changeX, changeY, 0);
					} else if (level[y][x] == 12) {
						this.drawWall(g, x, y, changeX, changeY, 1);
					} else {
						this.drawWall(g, x, y, changeX, changeY, 2);
					}
				}
				if ((level[y][x] == 19) || (level[y][x] == 20) || (level[y][x] == 21)) {
					int[] c = new int[] {255, 45, 45};
					if (level[y][x] == 20) {
						c = new int[] {45, 165, 255};
					}
					if (level[y][x] == 21) {
						c = new int[] {45, 255, 45};
					}
					g.setColor(c[0], c[1], c[2], 255);
					g.drawFilledOval(changeX + x * 30 + 15, changeY + y * 30 + 15, 12, 12);
					g.setColor(0, 0, 0, 255);
					g.drawOval(changeX + x * 30 + 15, changeY + y * 30 + 15, 12, 12);
				}
				if ((level[y][x] >= 1) && (level[y][x] <= 4)) {
					g.setColor(255, 0, 0);
					this.drawSnakeEyes(g, x, y, changeX, changeY, level[y][x]);
				} else if ((level[y][x] >= 7) && (level[y][x] <= 10)) {
					g.setColor(0, 90, 255);
					this.drawSnakeEyes(g, x, y, changeX, changeY, level[y][x] - 6);
				} else if ((level[y][x] >= 13) && (level[y][x] <= 16)) {
					g.setColor(0, 255, 0);
					this.drawSnakeEyes(g, x, y, changeX, changeY, level[y][x] - 12);
				}
			}
		}
		
		if (this.uploadString != null) {
			this.uploadString.render(g, 0, 0);
		}
		
		this.getGame().drawString(g, String.valueOf(this.level[0].length), 46, 595, ApoSnakeMenu.game_font);
		this.getGame().drawString(g, String.valueOf(this.level.length), 161, 595, ApoSnakeMenu.game_font);
		
		g.setLineSize(3f);
		g.setColor(255, 0, 0, 255);
		g.drawRoundRect(5 + this.curSelect * 47, 520, 40, 40, 6, 10);
		g.setLineSize(1f);
		
		//draw empty
		g.setColor(192, 192, 192, 255);
		g.drawFilledRoundRect(10, 525, 30, 30, 6, 10);
		
		for (int i = 1; i <= this.GREEN_COIN; i++) {
			if (i == this.RED_SNAKE) {
				g.setColor(255, 0, 0, 255);
				this.drawSnakeEyes(g, 0, 0, 10 + i * 47, 525, 2);
			} else if (i == this.BLUE_SNAKE) {
				g.setColor(0, 90, 255, 255);
				this.drawSnakeEyes(g, 0, 0, 10 + i * 47, 525, 2);
			} else if (i == this.GREEN_SNAKE) {
				g.setColor(0, 255, 0, 255);
				this.drawSnakeEyes(g, 0, 0, 10 + i * 47, 525, 2);
			}
			if (i == this.RED_COIN) {
				g.setColor(255, 0, 0, 255);
				g.drawFilledOval(10 + i * 47 + 15, 525 + 15, 5, 5);
				g.setColor(0, 0, 0, 255);
				g.drawOval(10 + i * 47 + 15, 525 + 15, 5, 5);
			} else if (i == this.BLUE_COIN) {
				g.setColor(0, 90, 255, 255);
				g.drawFilledOval(10 + i * 47 + 15, 525 + 15, 5, 5);
				g.setColor(0, 0, 0, 255);
				g.drawOval(10 + i * 47 + 15, 525 + 15, 5, 5);
			} else if (i == this.GREEN_COIN) {
				g.setColor(0, 255, 0, 255);
				g.drawFilledOval(10 + i * 47 + 15, 525 + 15, 5, 5);
				g.setColor(0, 0, 0, 255);
				g.drawOval(10 + i * 47 + 15, 525 + 15, 5, 5);
			}
			if (i == this.RED_WALL) {
				this.drawWall(g, 0, 0, 10 + i * 47, 525, 0);
			} else if (i == this.BLUE_WALL) {
				this.drawWall(g, 0, 0, 10 + i * 47, 525, 1);
			} else if (i == this.GREEN_WALL) {
				this.drawWall(g, 0, 0, 10 + i * 47, 525, 2);
			}
		}

	}
	
	private void drawWall(BitsGraphics g, int x, int y, int changeX, int changeY, int value) {
		g.setColor(200, 0, 0);
		if (value == 1) {
			g.setColor(90, 165, 200);
		} else if (value == 2) {
			g.setColor(0, 200, 0);							
		}
		g.drawFilledRect(changeX + 4 + x * 30, changeY + 4 + y * 30, 22, 22);
		g.setColor(150, 0, 0);
		if (value == 1) {
			g.setColor(0, 90, 200);
		} else if (value == 2) {
			g.setColor(0, 150, 0);							
		}
		g.drawFilledRect(changeX + 8 + x * 30, changeY + 8 + y * 30, 15, 15);
		g.setColor(0, 0, 0, 255);
		g.drawRect(changeX + 4 + x * 30, changeY + 4 + y * 30, 22, 22);
	}
	
	private void drawSnakeEyes(BitsGraphics g, int x, int y, int changeX, int changeY, int value) {
		g.drawFilledOval(changeX + x * 30 + 15, changeY + y * 30 + 15, 12, 12);

		g.setColor(0, 0, 0, 255);
		g.drawOval(changeX + x * 30 + 15, changeY + y * 30 + 15, 12, 12);
		
		if (value == 1) {
			g.drawFilledRect(changeX + x * 30 + 2, changeY + y * 30 + 11, 9, 3);
			g.drawFilledRect(changeX + x * 30 + 2, changeY + y * 30 + 16, 9, 3);
		} else if (value == 3) {
			g.drawFilledRect(changeX + (x + 1) * 30 - 11, changeY + y * 30 + 11, 9, 3);
			g.drawFilledRect(changeX + (x + 1) * 30 - 11, changeY + y * 30 + 16, 9, 3);
		} else if (value == 2) {
			g.drawFilledRect(changeX + (x) * 30 + 11, changeY + (y + 1) * 30 - 11, 3, 9);
			g.drawFilledRect(changeX + (x) * 30 + 16, changeY + (y + 1) * 30 - 11, 3, 9);
		} else if (value == 4) {
			g.drawFilledRect(changeX + (x) * 30 + 11, changeY + (y) * 30 + 2, 3, 9);
			g.drawFilledRect(changeX + (x) * 30 + 16, changeY + (y) * 30 + 2, 3, 9);
		}
	}

}
