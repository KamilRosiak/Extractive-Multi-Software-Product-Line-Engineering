package net.apogames.apodice.game;

import net.apogames.apodice.ApoDiceModel;
import net.apogames.apodice.level.ApoDiceLevel;
import net.gliblybits.bitsengine.render.BitsGraphics;

public class ApoDicePuzzleGame extends ApoDiceModel {

	public static final String BACK = "back";
	
	/**
	 * p[0] == Maus losgelassen
	 * p[1] == aktuelles Level
	 * p[2] == how much dices
	 * p[3] == Level geschafft Klick
	 * p[4] == Maus X-Wert
	 * p[5] == Maus Y-Wert
	 * p[6] == aktuell gedrückter Würfel x-Wert
	 * p[7] == aktuell gedrückter Würfel y-Wert
	 * p[8] == difference gedrückter Würfel x-Wert
	 * p[9] == difference gedrückter Würfel y-Wert
	 * p[10] == cX /pressed and mouse X change
	 * p[11] == cY / pressed and mouse Y change
	 * p[12] == c / hoe much change
	 */
	private final int[] p = new int[13];
	private boolean bBreak = false;
	
	private boolean bEditor = false;
	private boolean bUserlevel = false;
	
	private String levelString = "";
	
	private byte[][] level = new byte[16][8];
	
	public static final byte changeY = 25;
	
	private final String[] HELP = new String[] {
		"The number on the dice shows the count",
		"of possible moves. Move each dice to a",
		"gray spot with no moves remaining.",
		"A dice can push another dice"
	};
	
	public ApoDicePuzzleGame(ApoDicePanel game) {
		super(game);
	}

	@Override
	public void init() {		
		this.getStringWidth().put(ApoDicePuzzleGame.BACK, (int)(ApoDiceMenu.font.getLength(ApoDicePuzzleGame.BACK)));
		
		String s = "ApoDice";
		this.getStringWidth().put(s, (int) ApoDiceMenu.game_font.getLength(s));
		s = "Congratulation";
		this.getStringWidth().put(s, (int) ApoDiceMenu.title_font.getLength(s));
		s = "Touch to start the next level";
		this.getStringWidth().put(s, (int) ApoDiceMenu.game_font.getLength(s));
		s = "Please try again";
		this.getStringWidth().put(s, (int) ApoDiceMenu.title_font.getLength(s));
		s = "restart";
		this.getStringWidth().put(s, (int) ApoDiceMenu.game_font.getLength(s));
		s = "Touch to restart the level";
		this.getStringWidth().put(s, (int) ApoDiceMenu.game_font.getLength(s));
		s = HELP[0];
		this.getStringWidth().put(s, (int) ApoDiceMenu.game_font.getLength(s));
		s = HELP[1];
		this.getStringWidth().put(s, (int) ApoDiceMenu.game_font.getLength(s));
		s = HELP[2];
		this.getStringWidth().put(s, (int) ApoDiceMenu.game_font.getLength(s));
		s = HELP[3];
		this.getStringWidth().put(s, (int) ApoDiceMenu.game_font.getLength(s));
	}
	
	@Override
	public void touchedPressed(int x, int y, int finger) {
		p[2] = 1;
		p[4] = x;
		p[5] = y;
	}

	@Override
	public void touchedReleased(int x, int y, int finger) {
		p[2] = 0;
		p[0] = 1;
	}

	@Override
	public void touchedDragged(int x, int y, int oldX, int oldY, int finger) {
		p[4] = x;
		p[5] = y;
		
		bBreak = false;
		if (p[6] < 0) {
			return;
		}
		p[10] = p[11] = p[12] = 0;
		if (Math.abs(p[4] - (p[6] * 60 + p[8])) > Math.abs(p[5] - changeY - (p[7]  *60 + p[9]))) {
			if (p[4] - (p[6] * 60 + p[8]) < 0) {
				p[10] = -1;
			} else {
				p[10] = 1;
			}
		} else if (p[5] - changeY - (p[7] * 60 + p[9]) != 0) {
			if (p[5] - changeY - (p[7] * 60 + p[9]) < 0) {
				p[11] = -1;
			} else {
				p[11] = 1;
			}
		}
		p[12] = p[10];
		if (p[10] != 0) {
			while ((p[6] + p[12] >= 0) && (p[6] + p[12] < 8) && (level[p[7] + 8][p[6] + p[12]] > 0)) {
				p[12] += p[10];
			}
			if ((p[6] + p[12] < 0) || (p[6] + p[12] > 7)) {
				bBreak = true;
			}
		}
		if (p[11] != 0) {
			p[12] = p[11];
			while ((p[7] + p[12] >= 0) && (p[7] + p[12] < 8) && (level[p[7] + p[12] + 8][p[6]] > 0)) {
				p[12] += p[11];
			}
			if ((p[7] + p[12] < 0) || (p[7] + p[12] > 7)) {
				bBreak = true;
			}
		}
	}

	@Override
	public void touchedButton(String function) {
		if (function.equals(ApoDicePuzzleGame.BACK)) {
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
	
	public void loadLevel(int nextLevel, boolean bUserLevel, String levelString) {
		this.bUserlevel = bUserLevel;
		if (ApoDiceLevel.editorLevels == null) {
			this.bUserlevel = false;
		}
		p[1] = nextLevel;
		if (p[1] < 0) {
			if (this.bUserlevel) {
				p[1] = ApoDiceLevel.editorLevels.length - 1;
			} else {
				p[1] = this.getGame().getMaxCanChoosen();
			}
		}
		if (this.bUserlevel) {
			if (p[1] >= ApoDiceLevel.editorLevels.length) {
				p[1] = 0;
			}
		} else if (p[1] >= this.getGame().getMaxCanChoosen() + 1) {
			p[1] = 0;
		}
		this.bEditor = false;
		String l = "";
		if ((levelString != null) && (levelString.length() > 0)) {
			l = levelString;
			this.bEditor = true;
		} else {
			if (this.bUserlevel) {
				l = ApoDiceLevel.editorLevels[p[1]];
			} else {
				l = ApoDiceLevel.getLevel(p[1]);
			}
		}
		this.levelString = levelString;
		
		level = new byte[16][8];
		for (int y = 0; y < 8; y += 1) {
			for (int x = 0; x < level[y].length; x += 1) {
				char c = l.charAt(y * 8 + x);
				if ((c >= 48) && (c <= 57)) {
					byte value = Byte.valueOf(l.substring(y * 8 + x, y * 8 + x + 1));
					if (value <= 1) {
						level[y][x] = value;
					} else {
						level[y + 8][x] = value;
					}
				} else {
					byte value = (byte)((int)c - 95);
					level[y][x] = 1;
					level[y + 8][x] = value;
				}
			}
		}
		
		p[3] = p[2] = p[0] = 0;
		p[6] = -1;
	}

	@Override
	public void think(int delta) {
		if (level[0][0] == -1) {
			this.loadLevel(p[1], this.bUserlevel, this.levelString);
		} else {
			if (p[0] > 0) {
				if ((p[4] > 20) && (p[4] < 60) &&
					(p[5] > 590) && (p[5] < 630) && (!this.bEditor)) {
					p[1] -= 1;
					level[0][0] = -1;
				} else if ((p[4] > 320) && (p[4] < 360) &&
						(p[5] > 590) && (p[5] < 630) && (!this.bEditor)) {
					p[1] += 1;
					level[0][0] = -1;
				} else if ((p[4] > 150) && (p[4] < 230) &&
						(p[5] > 590) && (p[5] < 630)) {
					level[0][0] = -1;
				} else if (p[3] > 0) {
					level[0][0] = -1;
					if (p[3] == 1) {
						if (this.bEditor) {
							this.getGame().setEditor(true);
						} else {
							p[1] += 1;
						}
					}
				} else if (p[6] >= 0) {
					if (((int)(p[4])/60 != p[6]) || ((int)(p[5] - changeY)/60 != p[7])) {
						
						if (!bBreak) {
							level[p[7] + 8][p[6]] -= 1;
							if (p[10] < 0) {
								for (int i = p[6] + p[12]; i <= p[6]; i++) {
									if (i + 1 < 8) {
										level[p[7] + 8][i] = level[p[7] + 8][i + 1];
									} else {
										level[p[7] + 8][i] = 0;
									}
								}
							} else if (p[10] > 0) {
								for (int i = p[6] + p[12] - 1; i >= p[6]; i--) {
									level[p[7] + 8][i + 1] = level[p[7] + 8][i];
								}
							}
							if (p[11] < 0) {
								for (int i = p[7] + p[12]; i <= p[7]; i++) {
									if (i + 1 < 8) {
										level[i + 8][p[6]] = level[i + 8 + 1][p[6]];
									} else {
										level[i + 8][p[6]] = 0;
									}
								}
							} else if (p[11] > 0) {
								for (int i = p[7] + p[12] - 1; i >= p[7]; i--) {
									level[i + 1 + 8][p[6]] = level[i + 8][p[6]];
								}
							}
							level[p[7] + 8][p[6]] = 0;
						}
						
						// is level solved?
						boolean bWin = true;
						boolean bLoose = true;
						for (int y = 0; y < 8; y += 1) {
							for (int x = 0; x < level[y].length; x += 1) {
								if ((level[y][x] == 1) && (level[y+8][x] != 2)) {
									bWin = false;
								}
								if (level[y+8][x] > 2) {
									bLoose = false;
								}
							}
						}
						if (bWin) {
							p[3] = 1;
							if ((!this.bUserlevel) && (!this.bEditor)) {
								this.getGame().solvedLevel(p[1] + 1);
							}
						} else if (bLoose) {
							p[3] = 2;
						}
					}
					
					p[6] = -1;
				}
			} else if (p[2] > 0) {
				if (p[6] < 0) {
					int x = p[4] / 60;
					int y = (p[5] - changeY) / 60;
					if ((x >= 0) && (y >= 0) && (x < 8) && (y < 8) && (level[y+8][x] > 2)) {
						p[6] = x;
						p[7] = y;
						p[2] = 0;
						p[8] = p[4] - p[6] * 60;
						p[9] = p[5] - changeY - p[7] * 60;
					}
				}
			}
		}
		
		p[0] = 0;
	}

	@Override
	public void render(BitsGraphics g) {
		
		g.setColor(128, 128, 128, 255);
		g.drawFilledRect(0,0,480,changeY);
		g.drawFilledRect(0,480 + changeY,480,160 - changeY);
		
		g.setColor(0f/255f, 0f/255f, 0f/255f, 1.0f);
		g.drawRect(0,0,480,changeY);
		g.drawRect(0,480 + changeY,480,160 - changeY);

		String s = "ApoDice";
		this.getGame().drawString(g, s, 240, - 4, ApoDiceMenu.game_font);
		
		if (!this.bEditor) {
			if (this.bUserlevel) {
				s = "Level "+(p[1] + 1)+" / "+(ApoDiceLevel.editorLevels.length);
			} else {
				s = "Level "+(p[1] + 1)+" / "+(this.getGame().getMaxCanChoosen() + 1);
			}
		} else {
			s = "Editorlevel";
		}
		this.getGame().drawString(g, s, 5, - 4, ApoDiceMenu.game_font);

		for (int y = 0; y < 8; y += 1) {
			for (int x = 0; x < level[y].length; x += 1) {
				if (level[y][x] == 1) {
					g.setColor(48f/255f, 48f/255f, 48f/255f, 1.0f);
					g.drawFilledRoundRect(x * 60 + 1, changeY + y * 60 + 1, 58, 58, 6, 10);
				}
				if (level[y+8][x] > 0) {
					g.setColor(255f/255f, 255f/255f, 255f/255f, 1.0f);
					g.drawFilledRoundRect(x * 60 + 5, changeY + y * 60 + 5, 50, 50, 6, 10);
					if ((p[6] == x) && (p[7] == y)) {
						g.setColor(255f/255f, 0f/255f, 0f/255f, 1.0f);
						g.drawRoundRect(x * 60 + 5, changeY + y * 60 + 5, 50, 50, 6, 6);
					}
					g.setColor(0f/255f, 0f/255f, 0f/255f, 1.0f);
					if ((level[y+8][x] == 3) || (level[y+8][x] == 5) || (level[y+8][x] == 7)) {
						g.drawFilledCircle(x * 60 + 30, changeY + y * 60 + 30, 6, 40);
					}
					if ((level[y+8][x] == 4) || (level[y+8][x] == 5) || (level[y+8][x] == 6) || (level[y+8][x] == 7) || (level[y+8][x] == 8)) {
						g.drawFilledCircle(x * 60 + 14, changeY + y * 60 + 14, 6, 40);
						g.drawFilledCircle(x * 60 + 46, changeY + y * 60 + 46, 6, 40);
					}
					if ((level[y+8][x] == 6) || (level[y+8][x] == 7) || (level[y+8][x] == 8)) {
						g.drawFilledCircle(x * 60 + 46, changeY + y * 60 + 14, 6, 40);
						g.drawFilledCircle(x * 60 + 14, changeY + y * 60 + 46, 6, 40);
					}
					if (level[y+8][x] == 8) {
						g.drawFilledCircle(x * 60 + 46, changeY + y * 60 + 30, 6, 40);
						g.drawFilledCircle(x * 60 + 14, changeY + y * 60 + 30, 6, 40);
					}
				}
			}
		}
		
		if (p[6] >= 0) {
			g.setColor(128, 128, 128, 128);
			if (((int)(p[4])/60 != p[6]) || ((int)(p[5] - changeY)/60 != p[7])) {
				if (!bBreak) {
					if (p[10] < 0) {
						for (int i = p[6] + p[12]; i < p[6]; i++) {
							g.drawFilledRoundRect((i) * 60 + 8, changeY + p[7] * 60 + 8, 44, 44, 6, 10);
						}
					} else if (p[10] > 0) {
						for (int i = p[6] + p[12]; i > p[6]; i--) {
							g.drawFilledRoundRect((i) * 60 + 8, changeY + p[7] * 60 + 8, 44, 44, 6, 10);
						}
					}
					if (p[11] < 0) {
						for (int i = p[7] + p[12]; i < p[7]; i++) {
							g.drawFilledRoundRect((p[6]) * 60 + 8, changeY + (i) * 60 + 8, 44, 44, 6, 10);
						}
					} else if (p[11] > 0) {
						for (int i = p[7] + p[12]; i > p[7]; i--) {
							g.drawFilledRoundRect((p[6]) * 60 + 8, changeY + (i) * 60 + 8, 44, 44, 6, 10);
						}
					}
				}
			}
		}
		
		
		if (p[3] > 0) {
			String s2 = "";
			if (p[3] == 1) {
				s2 = "Congratulation";					
				s = "Touch to start the next level";
			} else if (p[3] > 1) {
				s2 = "Please try again";					
				s = "Touch to restart the level";
			}
			if (p[3] > 0) {
				float w = ApoDiceMenu.title_font.getLength(s2);
				g.setColor(128, 128, 128, 255);
				g.drawFilledRect(240 - w/2 - 10, 277, w + 20, 46);
				g.setColor(0, 0, 0, 255);
				g.drawRect(240 - w/2 - 10, 277, w + 20, 46);
				
				this.getGame().drawString(g, s2, 240, 273, ApoDiceMenu.title_font);
				
				this.getGame().drawString(g, s, 190, 595, ApoDiceMenu.game_font);
			}
		} else {
			if ((p[1] == 0) || (this.bUserlevel)) {
				this.getGame().drawString(g, HELP[0], 240, 510, ApoDiceMenu.game_font);
				this.getGame().drawString(g, HELP[1], 240, 530, ApoDiceMenu.game_font);
				this.getGame().drawString(g, HELP[2], 240, 550, ApoDiceMenu.game_font);
			} else if ((p[1] == 1) && (!this.bUserlevel)) {
				this.getGame().drawString(g, HELP[3], 240, 530, ApoDiceMenu.game_font);
			}
			
			g.setColor(160, 160, 160, 255);
			g.drawFilledRoundRect(150, 590, 80, 40, 6, 10);
			if (!this.bEditor) {
				g.drawFilledRoundRect(20, 590, 40, 40, 6, 10);
				g.drawFilledRoundRect(320, 590, 40, 40, 6, 10);
			}
			g.setLineSize(3.0f);
			g.setColor(0, 0, 0, 255);
			g.drawRoundRect(150, 590, 80, 40, 6, 10);
			if (!this.bEditor) {
				g.drawRoundRect(20, 590, 40, 40, 6, 10);
				g.drawRoundRect(320, 590, 40, 40, 6, 10);
			}

			if (!this.bEditor) {
				g.drawLine(30, 610, 50, 600);
				g.drawLine(30, 610, 50, 620);

				g.drawLine(350, 610, 330, 600);
				g.drawLine(350, 610, 330, 620);
			}
			g.setLineSize(1.0f);
			
			this.getGame().drawString(g, "restart", 190, 590, ApoDiceMenu.game_font);
		}
		
		this.getGame().renderButtons(g);
	}

}
