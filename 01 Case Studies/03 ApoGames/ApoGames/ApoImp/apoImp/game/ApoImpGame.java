package apoImp.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import org.apogames.ApoConstants;
import org.apogames.help.ApoHelp;

import apoImp.game.level.ApoImpLevel;

public class ApoImpGame extends ApoImpModel {
	
	public static final String QUIT = "X";
	public static final String LEVEL_LEFT = "<";
	public static final String LEVEL_RIGHT = ">";
	
	/**
	 * p[0] == aktuelles Level
	 * p[1] == Spiel gestartet
	 * p[2] == aktuelles Level geschafft klick
	 * p[3] == mouse X-click
	 * p[4] == mouse Y-click
	 */
	private final int[] p = new int[5];
	
	private boolean[] pressed = new boolean[256];
	
	boolean[][] ladder = new boolean[1][1];
	int[][] level = new int[1][1];
	
	int addX = 0;
	int addY = 0;
	
	int wallX = -1;
	int wallY = -1;
	
	int goalX = -1;
	int goalY = -1;
	int changeY = 30;
	int curSelect = 2;
	
	double vecX = -1;
	double vecY = -1;
	float curShootX = -1;
	float curShootY = -1;
	
	int add = 0;
	
	boolean bBlink = false;
	int time = 0;
	
	boolean bFall = false;
	
	private boolean bEditor = false;
	
	private String curLevel = "";
	
	private boolean bStart = false;
	private boolean bUserlevel = false;
	
	public ApoImpGame(ApoImpPanel game) {
		super(game);
	}

	@Override
	public void init() {
		if (level == null) {
			 level = new int[1][1];
		}
		level[0][0] = -1;
		this.bEditor = false;
		this.loadLevel(bEditor);

		this.bStart = true;
	}

	public boolean isUserlevel() {
		return this.bUserlevel;
	}

	public void setUserlevel(boolean bUserlevel) {
		this.bUserlevel = bUserlevel;
	}

	private int curPressed = -1;
	
	@Override
	public void keyButtonReleased(int button, char character) {
		if (button < 256) {
			pressed[button] = false;
		}
		bStart = true;
		if (button == KeyEvent.VK_ESCAPE) {
			this.quit();
		}
		if (p[2] > 0) {
			boolean bSolved = false;
			if (this.curPressed != button) {
				bSolved = true;
			} else {
				this.curPressed = -1;
			}
			if (bSolved) {
				this.loadNextLevel(+1);
				return;
			}
		} else if (button == KeyEvent.VK_L) {
			if (!ApoConstants.B_APPLET) {
				String s = ApoHelp.getClipboardContents();
				String oldLevel = this.curLevel;
				try {
					this.loadLevel(true, s);
					this.getGame().createEmptyLevel(s);
				} catch (Exception ex) {
					this.loadLevel(bEditor, oldLevel);
				}
			}
		} else if ((button == KeyEvent.VK_N) || (button == KeyEvent.VK_P) || (button == KeyEvent.VK_R)) {
			int add = 0;
			if (button == KeyEvent.VK_N) {
				add = 1;
			} else if (button == KeyEvent.VK_P)	{
				add = -1;
			}
			if ((add != 0) && (this.bEditor)) {
				return;
			}
			this.loadNextLevel(add);
			return;
		}
	}
	
	public void keyPressed(int keyCode, char keyCharacter) {
		if (keyCode < 256) {
			if ((!pressed[keyCode]) && (bStart)) {
				pressed[keyCode] = true;
			}
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(QUIT)) {
			this.quit();
		} else if (function.equals(LEVEL_LEFT)) {
			this.loadNextLevel(-1);
		} else if (function.equals(LEVEL_RIGHT)) {
			this.loadNextLevel(+1);
		}
	}
	
	private void loadNextLevel(int add) {
		p[0] += add;
		if (add != 0) {
			this.getGame().makeNewBackground();
		}
		level[0][0] = -1;
		this.loadLevel(this.bEditor);
	}

	private void quit() {
		if (this.bEditor) {
			super.getGame().setEditor();
		} else {
			super.getGame().setMenu();
		}
	}
	
	@Override
	public void mouseButtonReleased(int x, int y, boolean bRight) {
		if (bRight) {
			pressed[KeyEvent.VK_SPACE] = true;
		} else {
			p[3] = x;
			p[4] = y;
			if (p[2] > 0) {
				boolean bSolved = false;
				if (p[3] > 0) {
					bSolved = true;
				}
				if (bSolved) {
					level[0][0] = -1;
					p[0] += 1;
					this.getGame().makeNewBackground();
					this.loadLevel(this.bEditor);
					return;
				}
			}
		}
	}

	public void loadLevel(boolean bEditor) {
		if (bEditor) {
			if (p[2] > 0) {
				super.getGame().setEditor();
				super.getGame().save();
			} else {
				this.loadLevel(bEditor, this.curLevel);
			}
		} else {
			String l = "";
			if (this.bUserlevel) {
				if (p[0] >= ApoImpLevel.editorLevels.length) {
					p[0] = 0;
				}
				if (p[0] < 0) {
					p[0] = ApoImpLevel.editorLevels.length - 1;
				}
				l = ApoImpLevel.editorLevels[p[0]];
			} else {
				if (p[0] >= ApoImpLevel.levels.length) {
					p[0] = 0;
				}
				if (p[0] < 0) {
					p[0] = ApoImpLevel.levels.length - 1;
				}
				l = ApoImpLevel.levels[p[0]];
			}
			if (l.length() > 0) {
				this.loadLevel(this.bEditor, l);
			}
		}
	}
	
	public void loadLevel(boolean bEditor, String l) {
		this.curLevel = l;
		level = new int[(l.length() / 14 + 2) * 2][16];
		ladder = new boolean[level.length][level[0].length];
		for (int y = 0; y < (level.length - 4)/2; y++) {
			for (int x = 0; x < level[0].length - 2; x++) {
				char c = l.charAt(y * 14 + x);
				level[y+1][x+1] = Integer.valueOf(c) - 48;
				if (level[y+1][x+1] == 4) {
					goalX = x+1;
					goalY = y+1;
				}
			}
		}
		curSelect = 2;
		addX = addY = p[1] = p[2] = p[3] = p[4] = 0;
		pressed = new boolean[256];
		bFall = false;
		bStart = true;
		
		this.bEditor = bEditor;
		if (this.bEditor) {
			this.getGame().getButtons()[9].setBVisible(false);
			this.getGame().getButtons()[10].setBVisible(false);
		}
	}
	
	@Override
	public void think(int delta) {
		time += 10;
		if (time % 800 == 0) {
			bBlink = !bBlink;
		}
		
		if (level[0][0] == -1) {
			this.loadLevel(this.bEditor);
		}  else {
			int curX = -1, curY = -1;
			int addLeft = 0;
			for (int y = 0; y < level.length/2; y++) {
				for (int x = 0; x < level[y].length; x++) {
					if (level[y][x] == curSelect) {
						curX = x;
						curY = y;
					}
				}
			}
			
			if ((pressed[KeyEvent.VK_N]) || (pressed[KeyEvent.VK_P]) || (pressed[KeyEvent.VK_R])) {
				return;
			} else if (p[2] == 1) {
				return;
			} else if ((addX == 0) && (addY == 0) && (curShootX < 0) && (!bFall)) {
				ladder = new boolean[level.length][level[0].length];
				for (int x = 0; x < level[0].length; x++) {
					int addFall = 0;
					int addFallPlayer = 0;
					for (int y = level.length - 1; y >= 0; y--) {
						if (level[y][x] == 0) {
							addFallPlayer = addFall = 0;
						}
						if ((level[y][x] == 1) && ((x != goalX) || (y != goalY))) {
							addFall += 1;
							addFallPlayer += 1;
						}
						if ((x == goalX) && (y == goalY)) {
							addFallPlayer += 1;
						} else if ((((level[y][x] >= 5) && (level[y][x] <= 8)) && (addFall > 0)) || (((level[y][x] == 2) || (level[y][x] == 3)) && (addFallPlayer > 0) && (!ladder[y][x]))) {
							if (level[y][x] != 5) {
								addFall = addFallPlayer;
							}
							level[y + 16 + addFall][x] = addFall * 30;
							level[y + addFall][x] = level[y][x];
							level[y][x] = 1;
							bFall = true;
						} else if (level[y][x] == 6) {
							int count = 0;
							int play = 0;
							for (int above = y - 1; above >= 0; above--) {
								if (level[above][x] == 0) {
									break;
								}
								if ((level[above][x] == 2) || (level[above][x] == 3)) {
									play += 1;
								}
								if ((level[above][x] >= 5) || (play > 1)) {
									count = -1;
									break;
								}
								count += 1;
							}
							if (count > 0) {
								for (int above = y; above >= y - count; above--) {
									ladder[above][x] = true;
								}
							}
						}

					}
				}
				if (bFall) {
				} else
				// mouse clicked
					
				if (p[3] >= 0) {
					if ((p[3] > 0) && (p[3] < 480) && (p[4] > changeY) && (p[4] < 480 + changeY)) {
						int levelX = p[3] / 30;
						int levelY = (p[4] - changeY) / 30;
						if ((level[levelY][levelX] == 5) || (level[levelY][levelX] == 8) || (level[levelY][levelX] == 6)) {
							int goalShootX = p[3];
							int goalShootY = p[4] - changeY;
							for (int y = 0; y < level.length/2; y++) {
								for (int x = 0; x < level[y].length; x++) {
									if (level[y][x] == curSelect) {
										curShootX = x * 30 + 15;
										curShootY = y * 30 + 15;
										if (levelX == x) {
											curShootX = -1;
										}
									}
								}
							}
							
							if (curShootX > 0) {
								if (levelX < curX) {
									add = 2;
								} else {
									add = 0;
								}
								int ins = (int)(Math.abs(goalShootX - curShootX) + Math.abs(goalShootY - curShootY));
								vecX = (goalShootX - curShootX) / (float)(ins) * 2;
								vecY = (goalShootY - curShootY) / (float)(ins) * 2;
							}
						}
					}
					p[3] = -1;
				} else {
					int addUp = 0;
					// check the cursor keys and space
					if (pressed[KeyEvent.VK_SPACE]) {
						pressed[KeyEvent.VK_SPACE] = false;
						curSelect += 1;
						if (curSelect > 3) {
							curSelect = 2;
						}
					}
					if (pressed[KeyEvent.VK_LEFT]) {
						pressed[KeyEvent.VK_LEFT] = false;
						addLeft = -1;
						add = 2;
					} else if (pressed[KeyEvent.VK_RIGHT]) {
						pressed[KeyEvent.VK_RIGHT] = false;
						addLeft = 1;
						add = 0;
					} else if (pressed[KeyEvent.VK_UP]) {
						pressed[KeyEvent.VK_UP] = false;
						addUp = -1;
					} else if (pressed[KeyEvent.VK_DOWN]) {
						pressed[KeyEvent.VK_DOWN] = false;
						addUp = 1;
					}
					if (addUp != 0) {
						if ((ladder[curY + addUp][curX]) && (level[curY + addUp][curX] != 6)) {
							addY = addUp * -30;
							level[curY][curX] = 1;
							level[curY+addUp][curX] = curSelect;
						}
					}
					if (addLeft != 0) {
						if ((curX + addLeft >= 0) && (curX + addLeft < level[curY].length)) {
							if ((level[curY][curX + addLeft] == 1) || ((curY == goalY) && (curX + addLeft == goalX))) {
								level[curY][curX] = 1;
								addX = -addLeft * 30;
								level[curY][curX + addLeft] = curSelect;
								addY = 0;
							} else if ((curY - 1 >= 0) && (level[curY - 1][curX] == 1) && (((level[curY - 1][curX + addLeft] == 1) || ((curY - 1 == goalY) && (curX + addLeft == goalX))))) {
								level[curY][curX] = 1;
								level[curY-1][curX + addLeft] = curSelect;
								addX = -addLeft * 30;
								addY = 30;
							}
						}
					}
				}
			} else if (bFall) {
				bFall = false;
				for (int y = level.length - 1; y >= level.length/2; y--) {
					for (int x = 0; x < level[0].length; x++) {
						if (level[y][x] > 0) {
							level[y][x] -= 1;
							bFall = true;
						}
					}
				}
				if (!bFall) {
					if (level[goalY][goalX] == curSelect) {
						this.solvedLevel();
					}
				}
			} else if (curShootX >= 0) {
				curShootX += vecX;
				curShootY += vecY;
				if ((level[(int)(curShootY/30)][(int)(curShootX/30)] == 0) || (level[(int)(curShootY/30)][(int)(curShootX/30)] == 6) || (level[(int)(curShootY/30)][(int)(curShootX/30)] == 7) || (level[(int)(curShootY/30)][(int)(curShootX/30)] == 5) || (level[(int)(curShootY/30)][(int)(curShootX/30)] == 8)) {
					if (level[(int)(curShootY/30)][(int)(curShootX/30)] == 8) {
						level[(int)(curShootY/30)][(int)(curShootX/30)] = level[curY][curX];
						level[curY][curX] = 8;
					}
					if ((level[(int)(curShootY/30)][(int)(curShootX/30)] == 5) || (level[(int)(curShootY/30)][(int)(curShootX/30)] == 6)) {
						int cur = level[(int)(curShootY/30)][(int)(curShootX/30)];
						if (cur == 6) ladder = new boolean[level.length][level[0].length];
						addLeft = 1;
						if (vecX > 0) {
							addLeft = -1;
						}
						if (curSelect == 3) {
							addLeft = -addLeft;
						}
						curX = (int)(curShootX/30);
						curY = (int)(curShootY/30);
						if (addLeft != 0) {
							if ((curX + addLeft >= 0) && (curX + addLeft < level[curY].length)) {
								if (level[curY][curX + addLeft] == 1) {
									level[curY][curX] = 1;
									addX = -addLeft * 30;
									wallX = curX + addLeft;
									wallY = curY;
									level[curY][curX + addLeft] = cur;
									addY = 0;
								}
							}
						}
					}
					curShootX = -1;
					curShootY = -1;
				}
				
			} else {
				if (addY > 0) {
					addY -= 2;
				} else if (addX > 0) {
					addX -= 1;
				} else if (addX < 0) {
					addX += 1;
				} else if (addY < 0) {
					addY += 1;
				}
				if ((addY == 0) && (addX == 0)) {
					if (wallX >= 0) {
						wallX = -1;
					}
					if (level[goalY][goalX] == curSelect) {
						this.solvedLevel();
					}
				}
			}
		}
	}
	
	private void solvedLevel() {
		for (int i = 0; i < this.pressed.length; i++) {
			if (this.pressed[i]) {
				this.curPressed = i;
				break;
			}
		}
		p[2] = 1;
	}

	@Override
	public void render(Graphics2D g) {
		super.getGame().renderSnow(g);
		g.setColor(new Color(150, 150, 190));
		g.fillRect(0, 0, 480, changeY);
		g.fillRect(0, changeY + 480, 480, 80 - changeY);
		g.setColor(new Color(40, 40, 80));
		g.drawRect(0, 0, 479, changeY);
		g.drawRect(0, changeY + 480, 479, 79 - changeY);

		g.setFont(g.getFont().deriveFont(20f).deriveFont(1));
		
		for (int y = -level.length/2; y < level.length/2; y++) {
			for (int x = 0; x < level[0].length; x++) {
				if (y < 0) {
					if (level[y + level.length/2][x] != 0) {
						g.setColor(new Color(150, 150, 190));
						g.fillRect(x * 30, changeY + (y + level.length/2) * 30, 30, 30);
					}
					if (ladder[y + level.length/2][x]) {
						g.setColor(new Color(7, 50, 7));
						g.fillRect(x * 30 + 7, changeY + (y + level.length/2) * 30, 2, 30);
						g.fillRect(x * 30 + 22, changeY + (y + level.length/2) * 30, 2, 30);
						g.fillRect(x * 30 + 7, changeY + (y + level.length/2) * 30 + 5, 16, 3);
						g.fillRect(x * 30 + 7, changeY + (y + level.length/2) * 30 + 15, 16, 3);
						g.fillRect(x * 30 + 7, changeY + (y + level.length/2) * 30 + 25, 16, 3);
					}
					if ((curShootX > 0) && (x == level[0].length - 1) && (y == -1)) {
						g.setColor(Color.RED);
						if (curSelect == 3) {
							g.setColor(Color.BLUE);
						}
						g.fillOval((int)(curShootX - 3), (int)(curShootY - 3 + changeY), 6, 6);
						g.setColor(Color.BLACK);
						g.drawOval((int)(curShootX - 3), (int)(curShootY - 3 + changeY), 6, 6);
					}
					continue;
				}
				if (level[y][x] != 0) {
					g.setColor(Color.BLACK);
					if (level[y-1][x] == 0) g.drawLine(x * 30, changeY + y * 30, (x + 1) * 30, changeY + y * 30);
					if (level[y+1][x] == 0) g.drawLine(x * 30, changeY + (y + 1) * 30, (x + 1) * 30, changeY + (y + 1) * 30);
					if (level[y][x-1] == 0) g.drawLine(x * 30, changeY + y * 30, (x + 0) * 30, changeY + (y + 1) * 30);
					if (level[y][x+1] == 0) g.drawLine((x + 1) * 30, changeY + y * 30, (x + 1) * 30, changeY + (y + 1) * 30);
					
					if ((x == goalX) && (y == goalY)) {
						/** Render das Ziel */							
						g.setColor(new Color(26, 157, 26));
						for (int i = 0; i < 20; i += 2) {
							g.fillRect(x * 30 + i/2 + 5, y * 30 + changeY + 25 - i, 20 - i, 2);
						}
						g.setColor(new Color(78, 58, 11));
						g.fillRect(x * 30 + 13, y * 30 + changeY + 25, 4, 5);
						
						g.setColor(new Color(228, 255, 0));
						g.fillRect(x * 30 + 11, y * 30 + changeY + 3, 8, 2);
						g.fillRect(x * 30 + 14, y * 30 + changeY, 2, 8);
						
						Color c = new Color(220, 40, 40);
						if (bBlink) {
							c = new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue());
						}
						g.setColor(c);
						g.fillRect(x * 30 + 10, y * 30 + changeY + 25 - 4, 2, 2);
						g.fillRect(x * 30 + 18, y * 30 + changeY + 25 - 6, 2, 2);
						g.fillRect(x * 30 + 12, y * 30 + changeY + 25 - 11, 2, 2);
					}
					int addPX = 0;
					int addPY = -level[y+16][x];
					if (level[y][x] == 8) {
						if ((x == wallX) && (y == wallY)) {
							addPX += addX;
							addPY += addY;
						}
						g.setColor(new Color(40, 60, 80));
						g.fillRect(x * 30 + 8 + addPX, changeY + y * 30 + 10 + addPY, 14, 20);
						g.fillRect(x * 30 + 14 + addPX, changeY + y * 30 + addPY, 3, 3);
						g.fillRect(x * 30 + 15 + addPX, changeY + y * 30 + 3 + addPY, 1, 7);
						g.setColor(Color.WHITE);
						g.fillRect(x * 30 + 10 + addPX, changeY + y * 30 + 13 + addPY, 4, 2);
						g.fillRect(x * 30 + 16 + addPX, changeY + y * 30 + 13 + addPY, 4, 2);
						
					}
					if ((level[y][x] >= 5) && (level[y][x] <= 7)) {
						if ((x == wallX) && (y == wallY)) {
							addPX += addX;
							addPY += addY;
						}
						
						g.setColor(new Color(70, 195, 50));
						if (level[y][x] == 7) {
							g.setColor(new Color(195, 50, 70));								
						}
						if (level[y][x] == 6) {
							g.setColor(new Color(100, 120, 245));					
						}
						g.fillRect(x * 30 + 3 + addPX, changeY + y * 30 + 4 + addPY, 24, 24);
						g.setColor(Color.BLACK);
						g.drawRect(x * 30 + 3 + addPX, changeY + y * 30 + 4 + addPY, 24, 24);
						g.setColor(new Color(167, 73, 70));
						if (level[y][x] == 7) {
							g.setColor(new Color(73, 70, 167));								
						}
						if (level[y][x] == 6) {
							g.setColor(new Color(7, 50, 7));						
						}
						g.fillRect(x * 30 + 4 + addPX, changeY + y * 30 + 4 + addPY + 11, 23, 4);
						g.fillRect(x * 30 + 3 + addPX + 10, changeY + y * 30 + 5 + addPY, 4, 23);
						g.fillRect(x * 30 + 3 + addPX + 7, changeY + y * 30 + 4 + addPY - 3, 3, 3);
						g.fillRect(x * 30 + 3 + addPX + 14, changeY + y * 30 + 4 + addPY - 3, 3, 3);
						g.fillRect(x * 30 + 3 + addPX + 10, changeY + y * 30 + 4 + addPY - 2, 4, 2);
					}
					if ((level[y][x] == 2) || (level[y][x] == 3)) {
						if (level[y][x] == curSelect) {
							if (wallX < 0) {
								addPX = addX;
								addPY += addY;
							}
						}
						for (int i = 0; i < 2; i++) {
							int curX = x * 30 + addPX;
							int curY = changeY + y * 30 + addPY;
							if (i == 1) {
								if (level[y][x] != curSelect) {
									break;
								}
								curX = 5;
								curY = changeY + 480 + 10;
								g.setColor(new Color(40, 40, 80));
								g.drawRoundRect(curX, curY, 30, 30, 10, 10);
							}
							if (i == 0) {
								if (level[y][x] == curSelect) {
									g.setColor(Color.LIGHT_GRAY);
									g.drawRoundRect(x * 30 + 7 + addPX, changeY + y * 30 + 3 + addPY, 14, 27, 10, 10);
								}
							}
							
							g.setColor(new Color(218, 60, 60));
							if (level[y][x] == 3) {
								g.setColor(new Color(60, 60, 218));
							}
							g.fillRect((int)(curX) + 10, (int)(curY) + 12, 10, 6);
							g.fillRect((int)(curX) + 12, (int)(curY) + 6, 6, 6);
							g.fillRect((int)(curX) + 8, (int)(curY) + 22, 14, 2);
							g.fillRect((int)(curX) + 10, (int)(curY) + 26, 10, 2);
							
							g.setColor(new Color(253, 188, 111));
							g.fillRect((int)(curX) + 10, (int)(curY) + 18, 10, 2);
							g.fillRect((int)(curX) + 8, (int)(curY) + 24, 14, 2);

							g.setColor(Color.BLACK);
							g.fillRect((int)(curX) + 10, (int)(curY) + 24, 10, 2);
							g.fillRect((int)(curX) + 10, (int)(curY) + 28, 2, 2);
							g.fillRect((int)(curX) + 18, (int)(curY) + 28, 2, 2);

							g.fillRect((int)(curX) + 12 - add, (int)(curY) + 18, 2, 2);
							g.fillRect((int)(curX) + 18 - add, (int)(curY) + 18, 2, 2);
							
							g.setColor(Color.YELLOW);
							g.fillRect((int)(curX) + 14 - add, (int)(curY) + 24, 4, 2);
							
							g.setColor(Color.WHITE);
							g.fillRect((int)(curX) + 10 - add, (int)(curY) + 20, 12, 2);
							g.fillRect((int)(curX) + 12 - add, (int)(curY) + 22, 8, 2);
							g.fillRect((int)(curX) + 10, (int)(curY) + 16, 10, 2);
							g.fillRect((int)(curX) + 12 + add, (int)(curY) + 2, 4, 4);
						}
					}
				}
			}
		}

		g.setColor(new Color(40, 40, 80));
		String s = "ApoElf";
		int w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, 240 - w/2, 25);

		g.setFont(g.getFont().deriveFont(15f).deriveFont(1));
		if (p[2] > 0) {
				s = "Congratulation!";
				g.drawString(s, 240 - g.getFontMetrics().stringWidth(s)/2, changeY + 480 + 20);
				
				s = "Press any key to start the next level!";
				g.drawString(s, 240 - g.getFontMetrics().stringWidth(s)/2, changeY + 480 + 42);
		} 
		else {				
			s = null;
			String s2 = null;
			if ((p[0] == 0) || (this.bEditor) || (this.bUserlevel)) {
				s = "The two elves love christmas and want to idle at the christmas";
				s2 = "tree. Move with the cursor keys and reach the tree";
			} else 
			if (p[0] == 1) {
				s = "Change the elf with the spacebar or the right mouse button";
				s2 = "Use the other elf as a ladder";
			} else if (p[0] == 2) {
				s = "Click with the mouse on a green present to push or pull it";
				s2 = "The red elf pull presents, the blue elf push presents";
			} else if (p[0] == 4) {
				s = "The blue present creates a ladder above it";
				s2 = "Climb the ladder to reach higher places";
			} else if (p[0] == 8) {
				s = "Shoot on a beamer to switch your position with it";
			} else if (p[0] == 7) {
				s = "Red presents can't be moved.";
			}
			
			if (s != null) {
				g.drawString(s, 258 - g.getFontMetrics().stringWidth(s)/2, changeY + 480 + 22);
			}
			if (s2 != null) {
				g.drawString(s2, 258 - g.getFontMetrics().stringWidth(s2)/2, changeY + 480 + 40);
			}
		}
		
		s = "";
		if (this.bEditor) {
			s = "Editorlevel";	
		} else if (this.bUserlevel) {
			s = "level: "+String.valueOf(p[0]+1)+" / "+ApoImpLevel.editorLevels.length;
		} else {
			s = "level: "+String.valueOf(p[0]+1)+" / "+ApoImpLevel.levels.length;
		}
		
		g.drawString(s, 85 - g.getFontMetrics().stringWidth(s)/2, 20);
	}

}
