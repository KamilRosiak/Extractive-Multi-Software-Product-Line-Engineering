package net.apogames.aposnake.game;

import java.util.ArrayList;

import net.apogames.aposnake.ApoSnakeModel;
import net.apogames.aposnake.entity.ApoSnakeEntity;
import net.apogames.aposnake.level.ApoSnakeLevel;
import net.gliblybits.bitsengine.render.BitsGraphics;

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
	
	@SuppressWarnings("unchecked")
	private ArrayList<ApoSnakeEntity>[] players = new ArrayList[3];
	
	public static final byte changeY = 25;
	
	public ApoSnakePuzzleGame(ApoSnakePanel game) {
		super(game);
	}

	@Override
	public void init() {		
		this.getStringWidth().put(ApoSnakePuzzleGame.BACK, (int)(ApoSnakeMenu.font.getLength(ApoSnakePuzzleGame.BACK)));
		
		String s = "ApoSnake";
		this.getStringWidth().put(s, (int) ApoSnakeMenu.game_font.getLength(s));
		s = "Congratulation";
		this.getStringWidth().put(s, (int) ApoSnakeMenu.title_font.getLength(s));
		s = "Touch to start the next level";
		this.getStringWidth().put(s, (int) ApoSnakeMenu.game_font.getLength(s));
		s = "Please try again";
		this.getStringWidth().put(s, (int) ApoSnakeMenu.title_font.getLength(s));
		s = "restart";
		this.getStringWidth().put(s, (int) ApoSnakeMenu.game_font.getLength(s));
		s = "Touch to restart the level";
		this.getStringWidth().put(s, (int) ApoSnakeMenu.game_font.getLength(s));
		s = "controls";
		this.getStringWidth().put(s, (int) ApoSnakeMenu.game_font.getLength(s));
	}
	
	@Override
	public void touchedPressed(int x, int y, int finger) {
		if (((x > 5) && (x < 65) && (y > 575) && (y < 635)) ||
			((x > 0) && (x < 60) && (y > ApoSnakePuzzleGame.changeY) && (y < ApoSnakePuzzleGame.changeY + 480))) {
			p[5] = 1;
		} else if (((x > 70) && (x < 130) && (y > 575) && (y < 635)) ||
				((x > 420) && (x < 480) && (y > ApoSnakePuzzleGame.changeY) && (y < ApoSnakePuzzleGame.changeY + 480)))  {
			p[7] = 1;
		}
		
		if (((x > 415) && (x < 475) && (y > 575) && (y < 635)) ||
			((x > 60) && (x < 420) && (y > ApoSnakePuzzleGame.changeY + 420) && (y < ApoSnakePuzzleGame.changeY + 480))) {
			p[6] = 1;
		} else if (((x > 350) && (x < 410) && (y > 575) && (y < 635)) ||
				((x > 60) && (x < 420) && (y > ApoSnakePuzzleGame.changeY) && (y < ApoSnakePuzzleGame.changeY + 60))) {
			p[8] = 1;
		}
		p[9] = 1;
		
		if ((x > 15) && (x < 55) && (y > 520) && (y < 560)) {
			p[0] -= 1;
			level[0][0] = -1;
		} else if ((x > 310) && (x < 350) && (y > 520) && (y < 560)) {
			p[0] += 1;
			level[0][0] = -1;
		} else if ((x > 142) && (x < 222) && (y > 520) && (y < 560)) {
			level[0][0] = -1;
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
	public void render(BitsGraphics g) {	
		g.setColor(128, 128, 128, 255);
		g.drawFilledRect(0,0,480,changeY);
		g.drawFilledRect(0,480 + changeY,480,160 - changeY);
		
		g.setColor(0f/255f, 0f/255f, 0f/255f, 1.0f);
		g.drawRect(0,0,480,changeY);
		g.drawRect(0,480 + changeY,480,160 - changeY);

		String s = "ApoSnake";
		this.getGame().drawString(g, s, 240, - 4, ApoSnakeMenu.game_font);
		
		if (!this.bEditor) {
			if (this.bUserlevel) {
				s = "Level "+(p[0] + 1)+" / "+(ApoSnakeLevel.editorLevels.length);
			} else {
				s = "Level "+(p[0] + 1)+" / "+(this.getGame().getMaxCanChoosen() + 1);
			}
		} else {
			s = "Editorlevel";
		}
		this.getGame().drawString(g, s, 5, - 4, ApoSnakeMenu.game_font);
		
		s = "Moves: "+p[1];
		this.getGame().drawString(g, s, 360, - 4, ApoSnakeMenu.game_font);

		int changeX = p[3];
		int changeY = p[4];
		g.setColor(210, 210, 210, 255);
		g.drawFilledRect(changeX, changeY, level[0].length * 30, level.length * 30);
		g.setColor(0, 0, 0, 255);
		g.drawRect(changeX, changeY, level[0].length * 30, level.length * 30);

		for (int y = 0; y < level.length; y++) {
			for (int x = 0; x < level[0].length; x++) {
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
					g.setColor(200, 0, 0);
					if (level[y][x] == 12) {
						g.setColor(90, 165, 200);
					} else if (level[y][x] == 18) {
						g.setColor(0, 200, 0);							
					}
					g.drawFilledRect(changeX + 4 + x * 30, changeY + 4 + y * 30, 22, 22);
					g.setColor(150, 0, 0);
					if (level[y][x] == 12) {
						g.setColor(0, 90, 200);
					} else if (level[y][x] == 18) {
						g.setColor(0, 150, 0);							
					}
					g.drawFilledRect(changeX + 8 + x * 30, changeY + 8 + y * 30, 15, 15);
					g.setColor(0, 0, 0, 255);
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
					g.setColor(c[0], c[1], c[2], 255);
					g.drawFilledOval(changeX + x * 30 + 15, changeY + y * 30 + 15, 12, 12);
					g.setColor(0, 0, 0, 255);
					g.drawOval(changeX + x * 30 + 15, changeY + y * 30 + 15, 12, 12);
				}
			}
		}

		for (int i = 0; i < players.length; i++) {
			for (int p = players[i].size() - 1; p >= 0; p--) {
				ApoSnakeEntity entity = players[i].get(p);
				int w = entity.getColor();
				
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
					c = new int[] {90, 255, 90};
				}

				g.setColor(c[0], c[1], c[2], 255);
				if (nextX >= 0) {
					if (nextX < x) {
						g.drawFilledOval(changeX + nextX * 30 + 30, changeY + nextY * 30 + 15, 9, 9);
					} else if (nextX > x) {
						g.drawFilledOval(changeX + nextX * 30, changeY + nextY * 30 + 15, 9, 9);
					} else if (nextY < y) {
						g.drawFilledOval(changeX + nextX * 30 + 15, changeY + nextY * 30 + 30, 9, 9);
					} else if (nextY > y) {
						g.drawFilledOval(changeX + nextX * 30 + 15, changeY + nextY * 30, 9, 9);
					}
				}

				g.setColor(0, 0, 0, 255);
				if (nextX >= 0) {
					if (nextX < x) {
						g.drawOval(changeX + nextX * 30 + 30, changeY + nextY * 30 + 15, 9, 9);
					} else if (nextX > x) {
						g.drawOval(changeX + nextX * 30, changeY + nextY * 30 + 15, 9, 9);
					} else if (nextY < y) {
						g.drawOval(changeX + nextX * 30 + 15, changeY + nextY * 30 + 30, 9, 9);
					} else if (nextY > y) {
						g.drawOval(changeX + nextX * 30 + 15, changeY + nextY * 30, 9, 9);
					}
				}
			}
		}
		
		for (int i = 0; i < players.length; i++) {
			for (int p = players[i].size() - 1; p >= 0; p--) {
				int[] c = new int[] {0, 0, 0, 255};
				ApoSnakeEntity entity = players[i].get(p);
				int w = entity.getColor();
				int value = entity.getDirection();;				
				int x = entity.getX();
				int y = entity.getY();
				
				c = new int[] {255, 0, 0};
				if (w == 1) {
					c = new int[] {0, 90, 255};
				}
				if (w == 2) {
					c = new int[] {0, 255, 0};
				}

				g.setColor(c[0], c[1], c[2], 255);
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
		
		if (p[2] > 0) {
			String s2 = "";
			s2 = "Congratulation";					
			s = "Touch to start the next level";
			if (p[2] > 0) {
				float w = ApoSnakeMenu.title_font.getLength(s2);
				g.setColor(128, 128, 128, 255);
				g.drawFilledRect(240 - w/2 - 10, 137, w + 20, 46);
				g.setColor(0, 0, 0, 255);
				g.drawRect(240 - w/2 - 10, 137, w + 20, 46);
				
				this.getGame().drawString(g, s2, 240, 133, ApoSnakeMenu.title_font);
				
				this.getGame().drawString(g, s, 190, 595, ApoSnakeMenu.game_font);
			}
		} else {
			String[] string = null;
			if ((this.bEditor) || (this.bUserlevel)) {
			} else if (p[0] == 0) {
				string = new String[] {
					"Guide the snake to the red coin. Move with",
					"the cursor buttons or touch on the side of",
					"the level screen to move in that direction"
				};
			} else if (p[0] == 1) {
				string = new String[] {
					"Eat all coins to solve the level"
				};
			} else if (p[0] == 2) {
				string = new String[] {
					"You are what you eat"
				};
			} else if (p[0] == 3) {
				string = new String[] {
					"You can eat walls with a different color"
				};
			} else if (p[0] == 4) {
				string = new String[] {
					"You can't go backwards with your snake",
					"Touch 'restart' to restart a level"
				};
			} else if (p[0] == 9) {
				string = new String[] {
					"You control all snakes at once"
				};
			} else if (p[0] == 20) {
				string = new String[] {
					"You can eat parts of a snake",
					"with a different color"
				};
			}
			if (string != null) {
				g.setColor(128, 128, 128, 255);
				int w = (int)(ApoSnakeMenu.game_font.getLength(string[0]));
				for (int i = 1; i < string.length; i++) {
					int newW = (int)(ApoSnakeMenu.game_font.getLength(string[i]));
					if (newW > w) {
						w = newW;
					}
				}
				int x = (480 - w) / 2;
				g.drawFilledRect(x - 10,410,w + 20,30 * string.length);
				for (int i = 0; i < string.length; i++) {
					this.getGame().drawString(g, string[i], 240 - w/2, 405 + i*30, ApoSnakeMenu.game_font);
				}
			}
			
			g.setColor(160, 160, 160, 255);
			g.drawFilledRoundRect(142, 520, 80, 40, 6, 10);
			if (!this.bEditor) {
				g.drawFilledRoundRect(15, 520, 40, 40, 6, 10);
				g.drawFilledRoundRect(310, 520, 40, 40, 6, 10);
			}
			g.setLineSize(2.0f);
			g.setColor(0, 0, 0, 255);
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
			g.setLineSize(1.0f);
			
			this.getGame().drawString(g, "restart", 182, 520, ApoSnakeMenu.game_font);

			this.drawMoveButtons(g);
		}
		
		this.getGame().renderButtons(g);
	}

	private void drawMoveButtons(BitsGraphics g) {
		this.getGame().drawString(g, "controls", 240, 590, ApoSnakeMenu.game_font);
		
		g.setColor(160, 160, 160, 255);
		g.drawFilledRoundRect(5, 575, 60, 60, 6, 10);
		g.drawFilledRoundRect(70, 575, 60, 60, 6, 10);
		g.drawFilledRoundRect(415, 575, 60, 60, 6, 10);
		g.drawFilledRoundRect(350, 575, 60, 60, 6, 10);
		
		g.setLineSize(2.0f);
		g.setColor(0, 0, 0, 255);
		g.drawRoundRect(5, 575, 60, 60, 6, 10);
		g.drawRoundRect(70, 575, 60, 60, 6, 10);
		g.drawRoundRect(415, 575, 60, 60, 6, 10);
		g.drawRoundRect(350, 575, 60, 60, 6, 10);
		
		// draw left button ( 5, 575)
		g.drawLine(15, 605, 30, 620);
		g.drawLine(30, 620, 30, 610);
		g.drawLine(30, 610, 55, 610);
		g.drawLine(55, 610, 55, 600);
		g.drawLine(55, 600, 30, 600);
		g.drawLine(30, 600, 30, 590);
		g.drawLine(30, 590, 15, 605);
		
		// draw right button (70, 575)
		g.drawLine(120, 605, 105, 620);
		g.drawLine(105, 620, 105, 610);
		g.drawLine(105, 610, 80, 610);
		g.drawLine(80, 610, 80, 600);
		g.drawLine(80, 600, 105, 600);
		g.drawLine(105, 600, 105, 590);
		g.drawLine(105, 590, 120, 605);
		
		// draw down button (415, 575)
		g.drawLine(445, 625, 430, 610);
		g.drawLine(430, 610, 440, 610);
		g.drawLine(440, 610, 440, 585);
		g.drawLine(440, 585, 450, 585);
		g.drawLine(450, 585, 450, 610);
		g.drawLine(450, 610, 460, 610);
		g.drawLine(460, 610, 445, 625);
		
		// draw up button (350, 575)
		g.drawLine(380, 585, 365, 600);
		g.drawLine(365, 600, 375, 600);
		g.drawLine(375, 600, 375, 625);
		g.drawLine(375, 625, 385, 625);
		g.drawLine(385, 625, 385, 600);
		g.drawLine(385, 600, 395, 600);
		g.drawLine(395, 600, 380, 585);

		g.setLineSize(1.0f);
	}
}
