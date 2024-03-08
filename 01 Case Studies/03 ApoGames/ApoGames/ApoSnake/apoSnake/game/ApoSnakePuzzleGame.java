package apoSnake.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import apoSnake.ApoSnakeConstants;
import apoSnake.ApoSnakeModel;
import apoSnake.entity.ApoSnakeEntity;
import apoSnake.game.level.ApoSnakeLevel;

public class ApoSnakePuzzleGame extends ApoSnakeModel {

	public static final String BACK = "back";
	
	/**
	 * p[0] == aktuelles Level
	 * p[1] == aktuelle Moveanzahl
	 * p[2] == level geloest
	 * p[3] == change X Levelsize
	 * p[4] == change Y Levelsize
	 * p[5] == left key
	 * p[6] == down key
	 * p[7] == right key
	 * p[8] == up key
	 * p[9] == some key pressed
	 * p[10] == some key pressed ole
	 */
	private final int[] p = new int[11];
	
	private int[][] level;
	
	private boolean bEditor = false;
	private boolean bUserlevel = false;
	
	private String levelString = "";
	
	private boolean bHandCursor;
	
	@SuppressWarnings("unchecked")
	private ArrayList<ApoSnakeEntity>[] players = new ArrayList[3];
	
	public static final byte changeY = 25;
	
	public ApoSnakePuzzleGame(ApoSnakePanel game) {
		super(game);
	}

	@Override
	public void init() {		
		this.bHandCursor = false;
		this.getGame().getScreen().getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
	
	public boolean mouseMoved(int x, int y) {
		boolean bOver = false;
		if (p[2] <= 0) {
			if ((x > 15) && (x < 55) && (y > 520) && (y < 560) && (p[2] <= 0)) {
				bOver = true;
			} else if ((x > 310) && (x < 350) && (y > 520) && (y < 560) && (p[2] <= 0)) {
				bOver = true;
			} else if ((x > 142) && (x < 222) && (y > 520) && (y < 560) && (p[2] <= 0)) {
				bOver = true;
			}
		}
		if (bOver) {
			if (!this.bHandCursor) {
				this.getGame().getScreen().getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				this.bHandCursor = true;
			}
		} else {
			if (this.bHandCursor) {
				this.getGame().getScreen().getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				this.bHandCursor = false;
			}
		}
		return true;
	}
	
	public void mouseButtonReleased(int x, int y, boolean bRight) {
		if (p[2] <= 0) {
			if ((x > 15) && (x < 55) && (y > 520) && (y < 560) && (p[2] <= 0)) {
				p[0] -= 1;
				level[0][0] = -1;
			} else if ((x > 310) && (x < 350) && (y > 520) && (y < 560) && (p[2] <= 0)) {
				p[0] += 1;
				level[0][0] = -1;
			} else if ((x > 142) && (x < 222) && (y > 520) && (y < 560) && (p[2] <= 0)) {
				level[0][0] = -1;
			}
		}
		p[9] = 1;
	}
	
	public boolean mousePressed(int x, int y, boolean bRight) {
		
		return true;
	}
	
	public void keyPressed(int keyCode, char keyCharacter) {
		if ((p[10] <= 0) && (p[2] <= 0)) {
			if (keyCode == KeyEvent.VK_LEFT) {
				p[5] = 1;
			} else if (keyCode == KeyEvent.VK_RIGHT) {
				p[7] = 1;
			} else if (keyCode == KeyEvent.VK_DOWN) {
				p[6] = 1;
			} else if (keyCode == KeyEvent.VK_UP) {
				p[8] = 1;
			} 
		} else if ((p[2] > 0) && (p[10] <= 0)) {
			p[9] = 1;
		}
		p[10] = 1;
	}
	
	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_N) {
			p[0] += 1;
			level[0][0] = -1;
		} else if (button == KeyEvent.VK_P) {
			p[0] -= 1;
			level[0][0] = -1;
		} else if (button == KeyEvent.VK_R) {
			level[0][0] = -1;
		} 
		p[10] = 0;
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoSnakePuzzleGame.BACK)) {
			this.onBackButtonPressed();
		}
	}
	
	public void onBackButtonPressed() {
		if (this.bEditor) {
			this.getGame().setEditor(false);
		} else {
			if (this.bUserlevel) {
				this.getGame().setMenu();
			} else {
				this.getGame().setPuzzleChooser();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void loadLevel(int nextLevel, boolean bUserLevel, String levelString) {
		this.bUserlevel = bUserLevel;
		if (ApoSnakeLevel.editorLevels == null) {
			this.bUserlevel = false;
		}
		p[0] = nextLevel;
		if (p[0] < 0) {
			if (this.bUserlevel) {
				p[0] = ApoSnakeLevel.editorLevels.length - 1;
			} else {
				p[0] = this.getGame().getMaxCanChoosen();
			}
		}
		if (this.bUserlevel) {
			if (p[0] >= ApoSnakeLevel.editorLevels.length) {
				p[0] = 0;
			}
		} else if (p[0] >= this.getGame().getMaxCanChoosen() + 1) {
			p[0] = 0;
		}
		this.bEditor = false;
		String l = "";
		if ((levelString != null) && (levelString.length() > 0)) {
			l = levelString;
			this.bEditor = true;
		} else {
			if (this.bUserlevel) {
				l = ApoSnakeLevel.editorLevels[p[0]];
			} else {
				l = ApoSnakeLevel.getLevel(p[0]);
			}
		}
		this.levelString = levelString;
		
		int width = l.substring(0, 1).charAt(0) - 96;
		int height = l.substring(1, 2).charAt(0) - 96;
		level = new int[height][width];
		
		// count the snakes in the level
		int count = 0;
		for (int y = 0; y < level.length; y++) {
			for (int x = 0; x < level[0].length; x++) {
				int value = l.charAt(y * level[0].length + x + 2);
				if (value < 60) {
					value -= 48;
				} else {
					value -= 87;
				}
				if ((value >= 1) && (value <= 4)) {
				} else if ((value >= 7) && (value <= 10)) {
					value -= 6;
				} else if ((value >= 13) && (value <= 16)) {
					value -= 12;
				}
				if ((value >= 1) && (value <= 4)) {
					count += 1;
				}
			}
		}
		// create an array with the countsize of snakes
		players = new ArrayList[count];
		for (int i = 0; i < players.length; i++) {
			players[i] = new ArrayList<ApoSnakeEntity>();
		}

		p[2] = 0;
		p[1] = 0;
		int schlange = 0;
		for (int y = 0; y < level.length; y++) {
			for (int x = 0; x < level[0].length; x++) {
				int value = l.charAt(y * level[0].length + x + 2);
				if (value < 60) {
					value -= 48;
				} else {
					value -= 87;
				}
				int color = 0;
				if ((value >= 1) && (value <= 4)) {
				} else if ((value >= 7) && (value <= 10)) {
					color = 1;
					value -= 6;
				} else if ((value >= 13) && (value <= 16)) {
					color = 2;
					value -= 12;
				} else {
					level[y][x] = value;
				}
				if ((value >= 1) && (value <= 4)) {
					if (schlange < this.players.length) {
						ApoSnakeEntity entity = new ApoSnakeEntity(x, y, value, color);
						players[schlange].add(entity);
						schlange += 1;
					}
				}
			}
		}
		p[3] = (480 - level[0].length * 30)/2;
		p[4] = (480 - level.length * 30)/2 + ApoSnakePuzzleGame.changeY;
	}
	
	private boolean canMoveNextStep() {
		boolean bJumpResult = false;
		boolean[] bPlayerJump = new boolean[this.players.length];
		for (int j = 0; j < 4; j++) {
			if (bJumpResult) {
				return true;
			}
			for (int z = 0; z < 2; z++) {
				for (int i = 0; i < players.length; i++) {
					if (bPlayerJump[i]) {
						continue;
					}
					if (players[i].size() == 0) {
						continue;
					}
					int value = this.players[i].get(0).getDirection();
					int color = this.players[i].get(0).getColor();
					int x = this.players[i].get(0).getX();
					int y = this.players[i].get(0).getY();
					int checkX = x;
					int checkY = y;
					if (j == 0) {
						checkX = x - 1;
					} else if (j == 1) {
						checkX = x + 1;
					} else if (j == 2) {
						checkY = y + 1;
					} else if (j == 3) {
						checkY = y - 1;
					}
					if ((value == 1) && (j == 1)) {
						continue;
					}
					if ((value == 2) && (j == 3)) {
						continue;
					}
					if ((value == 3) && (j == 0)) {
						continue;
					}
					if ((value == 4) && (j == 2)) {
						continue;
					}
					boolean bJump = false;
					while ((checkX >= 0) && (checkY >= 0) && (checkX < level[0].length) && (checkY < level.length)) {
						boolean bBreak = false;
						boolean bIsThere = false;
						for (int l = 0; l < players.length; l++) {
							if (players[l].size() > 0) {
								ApoSnakeEntity checkEntity = players[l].get(0);
	
								int otherX = checkEntity.getX();
								int otherY = checkEntity.getY();
								
								if (l != i) {
									if ((otherX == checkX) && (otherY == checkY)) {
										bBreak = true;
										bJump = false;
										break;
									}
								}
								
								int otherColor = checkEntity.getColor();
								for (int k = 1; k < players[l].size(); k++) {
									checkEntity = players[l].get(k);
									otherX = checkEntity.getX();
									otherY = checkEntity.getY();
									if ((otherX == checkX) && (otherY == checkY)) {
										if ((i == l) && (k >= players[l].size() - 1)) {
										} else if (otherColor == color) {
											bBreak = true;
											break;
										} else {
											bIsThere = true;
											break;
										}
									}
								}
							}
						}
						if (bBreak) {
							break;
						}
						if (bIsThere) {
							
						} else if ((level[checkY][checkX] == 0) || (level[checkY][checkX] == 5) || (level[checkY][checkX] == 11) || (level[checkY][checkX] == 17)) {
							bJump = true;
							bJumpResult = true;
							return true;
						} else if (color == 0) {
							if ((level[checkY][checkX] == 6) || (level[checkY][checkX] == 19)) {
								break;
							}
						} else if (color == 1) {
							if ((level[checkY][checkX] == 12) || (level[checkY][checkX] == 20)) {
								break;
							}
						} else if (color == 2) {
							if ((level[checkY][checkX] == 18) || (level[checkY][checkX] == 21)) {
								break;
							}
						}
						
						if (j == 0) {
							checkX -= 1;
						} else if (j == 1) {
							checkX += 1;
						} else if (j == 2) {
							checkY += 1;
						} else if (j == 3) {
							checkY -= 1;
						}
					}
					
					if (bJump) {
						bPlayerJump[i] = true;
					}
				}
			}
		}
		
		return bJumpResult;
	}

	@Override
	public void think(int delta) {
		if (level[0][0] == -1) {
			this.loadLevel(p[0], this.bUserlevel, this.levelString);
		} else {
			if ((p[2] > 0) && (p[9] > 0)) {
				if (p[2] == 1) {
					p[0] += 1;
				}
				level[0][0] = -1;
				if ((this.bEditor) && (p[2] == 1)) {
					this.getGame().setEditor(true);
				} else {
					this.loadLevel(p[0], this.bUserlevel, this.levelString);
				}
			} else {
				int addJump = 0;
				boolean[] bPlayerJump = new boolean[this.players.length];
				if ((p[5] > 0) || (p[6] > 0) || (p[7] > 0) || (p[8] > 0)) {
					for (int z = 0; z < 2; z++) {
						for (int i = 0; i < players.length; i++) {
							if (bPlayerJump[i]) {
								continue;
							}
							if (players[i].size() == 0) {
								continue;
							}
							int value = this.players[i].get(0).getDirection();
							int color = this.players[i].get(0).getColor();
							int x = this.players[i].get(0).getX();
							int y = this.players[i].get(0).getY();
							int checkX = x;
							int checkY = y;
							if (p[5] > 0) {
								checkX = x - 1;
							} else if (p[7] > 0) {
								checkX = x + 1;
							} else if (p[6] > 0) {
								checkY = y + 1;
							} else if (p[8] > 0) {
								checkY = y - 1;
							}
							if ((value == 1) && (p[7] > 0)) {
								continue;
							}
							if ((value == 2) && (p[8] > 0)) {
								continue;
							}
							if ((value == 3) && (p[5] > 0)) {
								continue;
							}
							if ((value == 4) && (p[6] > 0)) {
								continue;
							}
							boolean bJump = false;
							int add = 1;
							while ((checkX >= 0) && (checkY >= 0) && (checkX < level[0].length) && (checkY < level.length)) {
								boolean bBreak = false;
								boolean bIsThere = false;
								for (int l = 0; l < players.length; l++) {
									if (players[l].size() > 0) {
										ApoSnakeEntity checkEntity = players[l].get(0);
	
										int otherX = checkEntity.getX();
										int otherY = checkEntity.getY();
										
										if (l != i) {
											if ((otherX == checkX) && (otherY == checkY)) {
												bBreak = true;
												bJump = false;
												break;
											}
										}
										
										int otherColor = checkEntity.getColor();
										for (int k = 1; k < players[l].size(); k++) {
											checkEntity = players[l].get(k);
											otherX = checkEntity.getX();
											otherY = checkEntity.getY();
											if ((otherX == checkX) && (otherY == checkY)) {
												if ((i == l) && (k >= players[l].size() - 1)) {
												} else if (otherColor == color) {
													bBreak = true;
													break;
												} else {
													bIsThere = true;
													break;
												}
											}
										}
									}
								}
								if (bBreak) {
									break;
								}
								if (bIsThere) {
									
								} else if ((level[checkY][checkX] == 0) || (level[checkY][checkX] == 5) || (level[checkY][checkX] == 11) || (level[checkY][checkX] == 17)) {
									bJump = true;
									if (level[checkY][checkX] == 5) {
										color = 0;
									} else if (level[checkY][checkX] == 11) {
										color = 1;
									} else if (level[checkY][checkX] == 17) {
										color = 2;
									}
									if (level[checkY][checkX] == 0) {
										add = 0;
									}
									break;
								} else if (color == 0) {
									if ((level[checkY][checkX] == 6) || (level[checkY][checkX] == 19)) {
										break;
									}
								} else if (color == 1) {
									if ((level[checkY][checkX] == 12) || (level[checkY][checkX] == 20)) {
										break;
									}
								} else if (color == 2) {
									if ((level[checkY][checkX] == 18) || (level[checkY][checkX] == 21)) {
										break;
									}
								}
								
								if (p[5] > 0) {
									checkX -= 1;
								} else if (p[7] > 0) {
									checkX += 1;
								} else if (p[6] > 0) {
									checkY += 1;
								} else if (p[8] > 0) {
									checkY -= 1;
								}
							}
							if (bJump) {
								bPlayerJump[i] = true;
								addJump = 1;
								players[i].get(0).setDirection(5);

								if (checkX < x) {
									for (int k = x - 1; k >= checkX; k--) {
										int v = 5;
										if (k == checkX) {
											v = 1;
										}
										a(k, y, add, i);
										ApoSnakeEntity newEntity = new ApoSnakeEntity(k, y, v, color);
										players[i].add(0, newEntity);
									}
								} else if (checkX > x) {
									for (int k = x + 1; k <= checkX; k++) {
										int v = 5;
										if (k == checkX) {
											v = 3;
										}
										a(k, y, add, i);
										ApoSnakeEntity newEntity = new ApoSnakeEntity(k, y, v, color);
										players[i].add(0, newEntity);
									}
								} else if (checkY < y) {
									for (int k = y - 1; k >= checkY; k--) {
										int v = 5;
										if (k == checkY) {
											v = 4;
										}
										a(x, k, add, i);
										ApoSnakeEntity newEntity = new ApoSnakeEntity(x, k, v, color);
										players[i].add(0, newEntity);
									}
								} else if (checkY > y) {
									for (int k = y + 1; k <= checkY; k++) {
										int v = 5;
										if (k == checkY) {
											v = 2;
										}
										a(x, k, add, i);
										ApoSnakeEntity newEntity = new ApoSnakeEntity(x, k, v, color);
										players[i].add(0, newEntity);
									}
								}
								for (int t = 1; t < players[i].size(); t++) {
									players[i].get(t).setColor(color);
								}
								if (add <= 0) {
									players[i].remove(players[i].size() - 1);
								}
								boolean bWin = true;
								for (y = 0; y < level.length; y++) {
									for (x = 0; x < level[0].length; x++) {
										if ((level[y][x] == 5) || (level[y][x] == 11) || (level[y][x] == 17)) {
											bWin = false;
											break;
										}
									}
									if (!bWin) {
										break;
									}
								}
								if (bWin) {
									p[2] = 1;
									if ((!this.bUserlevel) && (!this.bEditor)) {
										this.getGame().solvedLevel(p[0] + 1);
									}
								} else if (!this.canMoveNextStep()) {
									p[2] = 2;
								}
							}
						}
					}
				}
				p[1] += addJump;
			}
			for (int i = 5; i < 10; i++) {
				p[i] = 0;
			}
		}
	}
	
	private final void a(int x, int y, int add, int snake) {		
		for (int j = 0; j < players.length; j++) {
			for (int l = 1; l < players[j].size(); l++) {
				ApoSnakeEntity entity = this.players[j].get(l);
				int otherColor = entity.getColor();
				int otherX = entity.getX();
				int otherY = entity.getY();
				if ((otherX == x) && (otherY == y)) {
					int minus = 1;
					if (add > 0) {
						minus = 0;
					}
					if (snake != j) {
						minus = 0;
					}
					for (int f = players[j].size() - 1 - minus; f >= l; f--) {
						entity = players[j].get(f);
						int removeX = entity.getX();
						int removeY = entity.getY();
						level[removeY][removeX] = 19 + otherColor;
						players[j].remove(f);
					}
					break;
				}
			}
		}

		level[y][x] = 0;
	}

	@Override
	public void render(final Graphics2D g) {	
		g.setColor(new Color(128, 128, 128, 255));
		g.fillRect(0,0,479,changeY);
		g.fillRect(0,480 + changeY,479,159 - changeY);
		
		g.setColor(new Color(0f/255f, 0f/255f, 0f/255f, 1.0f));
		g.drawRect(0,0,479,changeY);
		g.drawRect(0,480 + changeY,479,159 - changeY);

		String s = "ApoSnake";
		this.getGame().drawString(g, s, 240, 2, ApoSnakeMenu.game_font);

		g.setFont(ApoSnakeMenu.game_font);
		if (!this.bEditor) {
			if (this.bUserlevel) {
				s = "Level "+(p[0] + 1)+" / "+(ApoSnakeLevel.editorLevels.length);
			} else {
				s = "Level "+(p[0] + 1)+" / "+(this.getGame().getMaxCanChoosen() + 1);
			}
		} else {
			s = "Editorlevel";
		}
		int w = g.getFontMetrics().stringWidth(s);
		this.getGame().drawString(g, s, 5 + w/2, 2, ApoSnakeMenu.game_font);
		
		s = "Moves: "+p[1];
		w = g.getFontMetrics().stringWidth(s);
		this.getGame().drawString(g, s, ApoSnakeConstants.GAME_WIDTH - 5 - w/2, 2, ApoSnakeMenu.game_font);

		int changeX = p[3];
		int changeY = p[4];
		g.setColor(new Color(210, 210, 210));
		g.fillRect(changeX, changeY, level[0].length * 30, level.length * 30);
		g.setColor(Color.BLACK);
		g.drawRect(changeX, changeY, level[0].length * 30, level.length * 30);

		for (int y = 0; y < level.length; y++) {
			for (int x = 0; x < level[0].length; x++) {
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
					g.setColor(new Color(200, 0, 0));
					if (level[y][x] == 12) {
						g.setColor(new Color(90, 165, 200));
					} else if (level[y][x] == 18) {
						g.setColor(new Color(0, 200, 0));							
					}
					g.fillRect(changeX + 4 + x * 30, changeY + 4 + y * 30, 22, 22);
					g.setColor(new Color(150, 0, 0));
					if (level[y][x] == 12) {
						g.setColor(new Color(0, 90, 200));
					} else if (level[y][x] == 18) {
						g.setColor(new Color(0, 150, 0));							
					}
					g.fillRect(changeX + 8 + x * 30, changeY + 8 + y * 30, 14, 14);
					g.setColor(new Color(0, 0, 0, 255));
					g.drawRect(changeX + 4 + x * 30, changeY + 4 + y * 30, 22, 22);
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
			}
		}

		for (int i = 0; i < players.length; i++) {
			for (int p = players[i].size() - 1; p >= 0; p--) {
				ApoSnakeEntity entity = players[i].get(p);
				w = entity.getColor();
				
				int x = entity.getX();
				int y = entity.getY();
				
				int nextX = -1;
				int nextY = -1;
				if (p > 0) {
					nextX = players[i].get(p - 1).getX();
					nextY = players[i].get(p - 1).getY();
				}
				
				int[] c = new int[] {255, 90, 90};
				if (w == 1) {
					c = new int[] {90, 165, 255};
				}
				if (w == 2) {
					c = new int[] {130, 255, 130};
				}

				g.setColor(new Color(c[0], c[1], c[2], 255));
				if (nextX >= 0) {
					if (nextX < x) {
						g.fillOval(changeX + nextX * 30 + 21, changeY + nextY * 30 + 6, 18, 18);
					} else if (nextX > x) {
						g.fillOval(changeX + nextX * 30 - 9, changeY + nextY * 30 + 6, 18, 18);
					} else if (nextY < y) {
						g.fillOval(changeX + nextX * 30 + 6, changeY + nextY * 30 + 24, 18, 18);
					} else if (nextY > y) {
						g.fillOval(changeX + nextX * 30 + 6, changeY + nextY * 30 - 9, 18, 18);
					}
				}

				g.setColor(new Color(0, 0, 0, 255));
				if (nextX >= 0) {
					if (nextX < x) {
						g.drawOval(changeX + nextX * 30 + 21, changeY + nextY * 30 + 6, 18, 18);
					} else if (nextX > x) {
						g.drawOval(changeX + nextX * 30 - 9, changeY + nextY * 30 + 6, 18, 18);
					} else if (nextY < y) {
						g.drawOval(changeX + nextX * 30 + 6, changeY + nextY * 30 + 24, 18, 18);
					} else if (nextY > y) {
						g.drawOval(changeX + nextX * 30 + 6, changeY + nextY * 30 - 9, 18, 18);
					}
				}
			}
		}
		
		for (int i = 0; i < players.length; i++) {
			for (int p = players[i].size() - 1; p >= 0; p--) {
				ApoSnakeEntity entity = players[i].get(p);
				w = entity.getColor();
				int value = entity.getDirection();;				
				int x = entity.getX();
				int y = entity.getY();
				
				int[] c = new int[] {210, 0, 0};
				if (w == 1) {
					c = new int[] {0, 90, 255};
				}
				if (w == 2) {
					c = new int[] {0, 210, 0};
				}

				g.setColor(new Color(c[0], c[1], c[2], 255));
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
		
		if (p[2] > 0) {
			String s2 = "";
			s2 = "Congratulation";
			s = "Press any key to start the next level";
			if (p[2] > 1) {
				s2 = "No snake can move further";
				s = "Press any key to restart the level";
			}
			if (p[2] > 0) {
				g.setFont(ApoSnakeMenu.title_font);
				w = g.getFontMetrics().stringWidth(s2);
				g.setColor(new Color(128, 128, 128, 255));
				g.fillRect(240 - w/2 - 10, 107, w + 20, 46);
				g.setColor(new Color(0, 0, 0, 255));
				g.drawRect(240 - w/2 - 10, 107, w + 20, 46);
				
				this.getGame().drawString(g, s2, 240, 114, ApoSnakeMenu.title_font);
				
				this.getGame().drawString(g, s, 190, 529, ApoSnakeMenu.game_font);
			}
		} else {
			String[] string = null;
			if ((this.bEditor) || (this.bUserlevel)) {
			} else if (p[0] == 0) {
				string = new String[] {
					"Guide the snake to the red coin",
					"Move with the cursor keys.",
				};
			} else if (p[0] == 1) {
				string = new String[] {
					"Eat all coins to solve the level.",
					"You are what you eat."
				};
			} else if (p[0] == 2) {
				string = new String[] {
					"You can get through walls when your",
					"color is different to the wall color.",
				};
			} else if (p[0] == 4) {
				string = new String[] {
					"Press 'restart' to restart a level"
				};
			} else if (p[0] == 3) {
				string = new String[] {
					"You can't go backwards with your snake"
				};
			} else if (p[0] == 5) {
				string = new String[] {
					"You control all snakes at once"
				};
			} else if (p[0] == 7) {
				string = new String[] {
					"You can eat parts of a snake",
					"with a different color"
				};
			}
			if (string != null) {
				g.setColor(new Color(128, 128, 128));
				g.setFont(ApoSnakeMenu.game_font);
				w = (int)(g.getFontMetrics().stringWidth(string[0]));
				for (int i = 1; i < string.length; i++) {
					int newW = (int)(g.getFontMetrics().stringWidth(string[i]));
					if (newW > w) {
						w = newW;
					}
				}
				int x = (480 - w) / 2;
				g.fillRect(x - 10,410,w + 20,30 * string.length);
				g.setColor(new Color(48, 48, 48, 255));
				g.drawRect(x - 10,410,w + 20,30 * string.length);
				for (int i = 0; i < string.length; i++) {
					this.getGame().drawString(g, string[i], 240, 415 + i*30, ApoSnakeMenu.game_font);
				}
			}
			
			g.setColor(new Color(160, 160, 160, 255));
			g.fillRoundRect(142, 520, 80, 40, 6, 6);
			if (!this.bEditor) {
				g.fillRoundRect(15, 520, 40, 40, 6, 6);
				g.fillRoundRect(310, 520, 40, 40, 6, 6);
			}
			g.setStroke(new BasicStroke(2.0f));
			g.setColor(new Color(48, 48, 48, 255));
			g.drawRoundRect(142, 520, 80, 40, 6, 10);
			if (!this.bEditor) {
				g.drawRoundRect(15, 520, 40, 40, 6, 10);
				g.drawRoundRect(310, 520, 40, 40, 6, 10);
			}

			if (!this.bEditor) {
				g.drawLine(25, 540, 45, 530);
				g.drawLine(25, 540, 45, 550);

				g.drawLine(340, 540, 320, 530);
				g.drawLine(340, 540, 320, 550);
			}
			g.setStroke(new BasicStroke(1.0f));
			
			this.getGame().drawString(g, "restart", 182, 530, ApoSnakeMenu.game_font);
		}
		
		this.getGame().renderButtons(g);
	}

}
