package apoSnake.game;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Cursor;
import java.awt.Graphics2D;

import org.apogames.ApoConstants;

import apoSnake.ApoSnakeModel;
import apoSnake.entity.ApoSnakeString;

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
	
	private boolean bHandCursor;
	
	private int curMouseX, curMouseY;
	
	public ApoSnakeEditor(ApoSnakePanel game) {
		super(game);
	}

	@Override
	public void init() {
		if (this.level == null) {
			this.makeLevel();
		}
		
		this.checkTestLevel();
		
		this.bHandCursor = false;
		this.getGame().getScreen().getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
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
		if ((!bSolved) || (!ApoConstants.B_ONLINE)) {
			this.getGame().getButtons()[8].setBVisible(false);
		} else {
			this.getGame().getButtons()[8].setBVisible(true);
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
			this.getGame().getButtons()[7].setBVisible(true);
		} else {
			this.getGame().getButtons()[7].setBVisible(false);
			this.getGame().getButtons()[8].setBVisible(false);
		}
	}
	
	private boolean checkMouseMoved(int x, int y) {
		int changeX = (480 - level[0].length * 30)/2;
		int changeY = (480 - level.length * 30)/2 + ApoSnakePuzzleGame.changeY;
		if ((y >= changeY) && (y < changeY + level.length * 30) &&
			(x >= changeX) && (x < changeX + level[0].length * 30)) {
			this.curMouseY = (y - changeY)/30;
			this.curMouseX = (x - changeX)/30;
			if (!this.bHandCursor) {
				this.bHandCursor = true;
				this.getGame().getScreen().getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			return true;
		}
		this.curMouseX = -1;
		if (this.bHandCursor) {
			this.bHandCursor = false;
			this.getGame().getScreen().getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		return false;
	}

	public boolean mouseMoved(int x, int y) {
		if (!this.checkMouseMoved(x, y)) {
			for (int i = 0; i <= this.GREEN_COIN; i++) {
				if ((x >= 5 + i * 47) && (x < 45 + i * 47) && (y >= 520) && (y < 560)) {
					if (!this.bHandCursor) {
						this.bHandCursor = true;
						this.getGame().getScreen().getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					}
					return true;
				}
			}
			if (this.bHandCursor) {
				this.bHandCursor = false;
				this.getGame().getScreen().getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
		return false;
	}
	
	@Override
	public void mouseButtonReleased(int x, int y, boolean bRight) {
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
				this.setLevelSolved(false);
			}
		}
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoSnakeEditor.BACK)) {
			this.onBackButtonPressed();
		} else if (function.equals(ApoSnakeEditor.TEST)) {
			String levelString = this.getLevelString();
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
		this.setLevelSolved(false);
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
	public void render(final Graphics2D g) {
		g.setColor(new Color(128, 128, 128, 255));
		g.fillRect(0,0,480,ApoSnakePuzzleGame.changeY);
		g.fillRect(0,480 + ApoSnakePuzzleGame.changeY,480,160 - ApoSnakePuzzleGame.changeY);
		
		g.setColor(new Color(0f/255f, 0f/255f, 0f/255f, 1.0f));
		g.drawRect(0,0,480,ApoSnakePuzzleGame.changeY);
		g.drawRect(0,480 + ApoSnakePuzzleGame.changeY,480,160 - ApoSnakePuzzleGame.changeY);
		
		String s = "ApoSnake - Editor";
		this.getGame().drawString(g, s, 240, 2, ApoSnakeMenu.game_font);
		
		this.getGame().renderButtons(g, ApoSnakeMenu.game_font);
		
		int changeX = (480 - level[0].length * 30)/2;
		int changeY = (480 - level.length * 30)/2 + ApoSnakePuzzleGame.changeY;
		g.setColor(new Color(0, 0, 0, 255));
		g.drawRect(changeX, changeY, level[0].length * 30, level.length * 30);

		for (int y = 0; y < level.length; y++) {
			for (int x = 0; x < level[0].length; x++) {
				g.setColor(new Color(0, 0, 0, 255));
				g.drawRect(changeX + x * 30, changeY + y * 30, 30, 30);
				if ((level[y][x] == 5) || (level[y][x] == 11) || (level[y][x] == 17)) {
					g.setColor(new Color(255, 0, 0));
					if (level[y][x] == 11) {
						g.setColor(new Color(0, 120, 255));							
					} else if (level[y][x] == 17) {
						g.setColor(new Color(0, 255, 0));							
					}
					g.fillOval(changeX + 10 + x * 30, changeY + 10 + y * 30, 10, 10);
					g.setColor(new Color(0, 0, 0, 255));
					g.drawOval(changeX + 10 + x * 30, changeY + 10 + y * 30, 10, 10);
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
					g.setColor(new Color(c[0], c[1], c[2], 255));
					g.fillOval(changeX + x * 30 + 3, changeY + y * 30 + 3, 24, 24);
					g.setColor(new Color(0, 0, 0, 255));
					g.drawOval(changeX + x * 30 + 3, changeY + y * 30 + 3, 24, 24);
				}
				if ((level[y][x] >= 1) && (level[y][x] <= 4)) {
					g.setColor(new Color(255, 0, 0));
					this.drawSnakeEyes(g, x, y, changeX, changeY, level[y][x]);
				} else if ((level[y][x] >= 7) && (level[y][x] <= 10)) {
					g.setColor(new Color(0, 90, 255));
					this.drawSnakeEyes(g, x, y, changeX, changeY, level[y][x] - 6);
				} else if ((level[y][x] >= 13) && (level[y][x] <= 16)) {
					g.setColor(new Color(0, 255, 0));
					this.drawSnakeEyes(g, x, y, changeX, changeY, level[y][x] - 12);
				}
			}
		}
		
		if (this.uploadString != null) {
			this.uploadString.render(g, 0, 0);
		}
		
		g.setFont(ApoSnakeMenu.game_font);
		s = String.valueOf(this.level[0].length);
		this.getGame().drawString(g, s, 60, 605, ApoSnakeMenu.game_font);
		
		s = String.valueOf(this.level.length);
		this.getGame().drawString(g, s, 175, 605, ApoSnakeMenu.game_font);
		
		g.setStroke(new BasicStroke(3.0f));
		g.setColor(new Color(255, 0, 0, 255));
		g.drawRoundRect(5 + this.curSelect * 47, 520, 40, 40, 6, 10);
		g.setStroke(new BasicStroke(1.0f));
		
		if (this.curMouseX >= 0) {
			this.drawOverlay(g, this.curMouseX * 30 + changeX, this.curMouseY * 30 + changeY);
		}
		
		//draw empty
		g.setColor(new Color(192, 192, 192, 255));
		g.fillRoundRect(10, 525, 30, 30, 6, 6);
		
		for (int i = 1; i <= this.GREEN_COIN; i++) {
			if (i == this.RED_SNAKE) {
				g.setColor(new Color(255, 0, 0, 255));
				this.drawSnakeEyes(g, 0, 0, 10 + i * 47, 525, 2);
			} else if (i == this.BLUE_SNAKE) {
				g.setColor(new Color(0, 90, 255, 255));
				this.drawSnakeEyes(g, 0, 0, 10 + i * 47, 525, 2);
			} else if (i == this.GREEN_SNAKE) {
				g.setColor(new Color(0, 255, 0, 255));
				this.drawSnakeEyes(g, 0, 0, 10 + i * 47, 525, 2);
			}
			if (i == this.RED_COIN) {
				g.setColor(new Color(255, 0, 0, 255));
				g.fillOval(10 + i * 47 + 10, 525 + 10, 10, 10);
				g.setColor(new Color(0, 0, 0, 255));
				g.drawOval(10 + i * 47 + 10, 525 + 10, 10, 10);
			} else if (i == this.BLUE_COIN) {
				g.setColor(new Color(0, 90, 255, 255));
				g.fillOval(10 + i * 47 + 10, 525 + 10, 10, 10);
				g.setColor(new Color(0, 0, 0, 255));
				g.drawOval(10 + i * 47 + 10, 525 + 10, 10, 10);
			} else if (i == this.GREEN_COIN) {
				g.setColor(new Color(0, 255, 0, 255));
				g.fillOval(10 + i * 47 + 10, 525 + 10, 10, 10);
				g.setColor(new Color(0, 0, 0, 255));
				g.drawOval(10 + i * 47 + 10, 525 + 10, 10, 10);
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
	
	private void drawOverlay(final Graphics2D g, final int x, final int y) {
		Composite composite = g.getComposite();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(0.65f)));
		
		if (this.curSelect == this.RED_WALL) {
			this.drawWall(g, 0, 0, x, y, 0);
		} else if (this.curSelect == this.BLUE_WALL) {
			this.drawWall(g, 0, 0, x, y, 1);
		} else if (this.curSelect == this.GREEN_WALL) {
			this.drawWall(g, 0, 0, x, y, 2);
		} else if (this.curSelect == this.RED_COIN) {
			g.setColor(new Color(255, 0, 0, 255));
			g.fillOval(x + 10, y + 10, 10, 10);
			g.setColor(new Color(0, 0, 0, 255));
			g.drawOval(x + 10, y + 10, 10, 10);
		} else if (this.curSelect == this.BLUE_COIN) {
			g.setColor(new Color(0, 90, 255, 255));
			g.fillOval(x + 10, y + 10, 10, 10);
			g.setColor(new Color(0, 0, 0, 255));
			g.drawOval(x + 10, y + 10, 10, 10);
		} else if (this.curSelect == this.GREEN_COIN) {
			g.setColor(new Color(0, 255, 0, 255));
			g.fillOval(x + 10, y + 10, 10, 10);
			g.setColor(new Color(0, 0, 0, 255));
			g.drawOval(x + 10, y + 10, 10, 10);
		} else if (this.curSelect == this.RED_SNAKE) {
			if ((this.level[this.curMouseY][this.curMouseX] >= 1) && (this.level[this.curMouseY][this.curMouseX] <= 4)) {
				
			} else {
				g.setColor(new Color(255, 0, 0, 255));
				this.drawSnakeEyes(g, 0, 0, x, y, 1);
			}
		} else if (this.curSelect == this.BLUE_SNAKE) {
			if ((this.level[this.curMouseY][this.curMouseX] >= 7) && (this.level[this.curMouseY][this.curMouseX] <= 10)) {
				
			} else {
				g.setColor(new Color(0, 90, 255, 255));
				this.drawSnakeEyes(g, 0, 0, x, y, 1);
			}
		} else if (this.curSelect == this.GREEN_SNAKE) {
			if ((this.level[this.curMouseY][this.curMouseX] >= 13) && (this.level[this.curMouseY][this.curMouseX] <= 16)) {
				
			} else {
				g.setColor(new Color(0, 255, 0, 255));
				this.drawSnakeEyes(g, 0, 0, x, y, 1);
			}
		} else {
			g.setColor(new Color(192, 192, 192, 255));
			g.fillRoundRect(x + 1, y + 1, 27, 27, 6, 6);
		}
		
		g.setComposite(composite);
	}
	
	private void drawWall(final Graphics2D g, int x, int y, int changeX, int changeY, int value) {
		g.setColor(new Color(200, 0, 0));
		if (value == 1) {
			g.setColor(new Color(90, 165, 200));
		} else if (value == 2) {
			g.setColor(new Color(0, 200, 0));							
		}
		g.fillRect(changeX + 4 + x * 30, changeY + 4 + y * 30, 22, 22);
		g.setColor(new Color(150, 0, 0));
		if (value == 1) {
			g.setColor(new Color(0, 90, 200));
		} else if (value == 2) {
			g.setColor(new Color(0, 150, 0));							
		}
		g.fillRect(changeX + 8 + x * 30, changeY + 8 + y * 30, 14, 14);
		g.setColor(new Color(0, 0, 0, 255));
		g.drawRect(changeX + 4 + x * 30, changeY + 4 + y * 30, 22, 22);
	}
	
	private void drawSnakeEyes(final Graphics2D g, int x, int y, int changeX, int changeY, int value) {
		g.fillOval(changeX + x * 30 + 3, changeY + y * 30 + 3, 24, 24);

		g.setColor(new Color(0, 0, 0, 255));
		g.drawOval(changeX + x * 30 + 3, changeY + y * 30 + 3, 24, 24);
		
		if (value == 1) {
			g.fillRect(changeX + x * 30 + 2, changeY + y * 30 + 11, 9, 3);
			g.fillRect(changeX + x * 30 + 2, changeY + y * 30 + 16, 9, 3);
		} else if (value == 3) {
			g.fillRect(changeX + (x + 1) * 30 - 11, changeY + y * 30 + 11, 9, 3);
			g.fillRect(changeX + (x + 1) * 30 - 11, changeY + y * 30 + 16, 9, 3);
		} else if (value == 2) {
			g.fillRect(changeX + (x) * 30 + 11, changeY + (y + 1) * 30 - 11, 3, 9);
			g.fillRect(changeX + (x) * 30 + 16, changeY + (y + 1) * 30 - 11, 3, 9);
		} else if (value == 4) {
			g.fillRect(changeX + (x) * 30 + 11, changeY + (y) * 30 + 2, 3, 9);
			g.fillRect(changeX + (x) * 30 + 16, changeY + (y) * 30 + 2, 3, 9);
		}
	}

}
