package net.apogames.apoclock.game;

import net.apogames.apoclock.ApoClockModel;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;

public class ApoClockArcardeGame extends ApoClockModel {

	public static final String BACK = "back";
	
	private int clocks = 0;
	
	/**
	 * level besteht aus
	 * 1 = x-Wert Uhr
	 * 2 = y-Wert Uhr
	 * 3 = Radius
	 * 4 = startangle
	 * 5 = rotationsgeschwindigkeit
	 * 6 = sichtbar = default is 0
	 * 7 = Startgrad = default is -1
	 * 8 = curTime = default is 0
	 * 9 = ??
	 * */
	private int[] level = new int[1];
	
	/**
	 * 0 = X-Wert
	 * 1 = y-Wert
	 * 2 = Angle
	 * 3 = Geschwindigkeit
	 */
	private float[] ball = new float[5];
	
	/**
	 * p[0] == losgelassen
	 * p[1] == aktuelle Punkteanzahl
	 * p[2] == Spiel gestartet
	 * p[3] == Level geschafft Klick
	 * p[4] == Maus X-Wert
	 * p[5] == Maus Y-Wert
	 * 
	 */
	private final int[] p = new int[6];
	
	public ApoClockArcardeGame(ApoClockPanel game) {
		super(game);
	}

	@Override
	public void init() {		
		this.getStringWidth().put(ApoClockPuzzleGame.BACK, (int)(ApoClockPanel.font.getLength(ApoClockPuzzleGame.BACK)));
		
		String s = "ApoClock - Arcarde";
		this.getStringWidth().put(s, (int) ApoClockPanel.game_font.getLength(s));
		s = "Congratulation";
		this.getStringWidth().put(s, (int) ApoClockPanel.game_font.getLength(s));
		s = "Touch to restart";
		this.getStringWidth().put(s, (int) ApoClockPanel.game_font.getLength(s));
		s = "Your score is";
		this.getStringWidth().put(s, (int) ApoClockPanel.game_font.getLength(s));
		s = "Touch to start the game";
		this.getStringWidth().put(s, (int) ApoClockPanel.game_font.getLength(s));
		
		this.loadLevel();
	}
	

	@Override
	public void touchedPressed(int x, int y, int finger) {
		p[0] = 1;
		p[4] = x;
		p[5] = y;
	}

	@Override
	public void touchedReleased(int x, int y, int finger) {
		
	}

	@Override
	public void touchedDragged(int x, int y, int oldX, int oldY, int finger) {
		
	}

	@Override
	public void touchedButton(String function) {
		if (function.equals(ApoClockArcardeGame.BACK)) {
			this.onBackButtonPressed();
		}
	}
	
	public void onBackButtonPressed() {
		this.getGame().setAracardeHelp(this.p[1], this.clocks);
	}
	
	protected void loadLevel() {
		p[1] = 0;
		
		/**
		 * level besteht aus
		 * 1 = x-Wert Uhr
		 * 2 = y-Wert Uhr
		 * 3 = Radius
		 * 4 = startangle
		 * 5 = rotationsgeschwindigkeit
		 * 6 = sichtbar = default is 0
		 * 7 = Startgrad = default is -1
		 * 8 = curTime = default is 0
		 * */
		
		level = new int[9*10];
		
		level[0+2] = (int)(Math.random() * 40) + 20;
		level[0] = 240  - level[2]/2;
		level[0+1] = 260 - level[2]/2;
		level[0+3] = (int)(Math.random() * 360);
		level[0+4] = (int)(Math.random() * 12) + 5;
		level[0+5] = level[0+7] = 0;
		level[0+6] = -1;
		level[0+8] = 1;
		if (Math.random() * 100 > 50) {
			level[0+8] = -1;
		}
		
		for (int i = 1; i < 10; i++) {
			level[i * 9 + 5] = 1;
		}
		for (int i = 1; i < 10; i++) {
			this.setNewRandomClock();
		}
		
		ball = new float[5];
		ball[0] = 5;
		ball[1] = 260;
		ball[2] = 90;
		ball[3] = 0.2f;
		
		p[0] = 0;
		p[3] = 0;
		p[2] = 0;
		
		this.clocks = 0;
	}
	
	private void setNewRandomClock() {
		int radius = 0;
		int x = 0;
		int y = 0;
		boolean bIntersect = false;
		do {
			radius = (int)(Math.random() * 30) + 15;
			if (this.p[1] > 50000) {
				radius -= 25;	
			} else if (this.p[1] > 15000) {
				radius -= 15;
			} else if (this.p[1] > 5000) {
				radius -= 10;
			}
			if (radius < 15) {
				radius = 15;
			}
			x = (int)(Math.random() * (480 - 2 * radius - 1)) + radius;
			y = (int)(Math.random() * (565 - 2 * radius - 1)) + 26 + radius;
			
			bIntersect = false;
			for (int i = 0; i < level.length; i += 9) {
				if (level[i+5] <= 0) {
					float newX = (level[i] - x) * (level[i] - x);
					float newY = (level[i+1] - y) * (level[i+1] - y);
					float newRadius = (level[i+2] + radius + 5) * (level[i+2] + radius + 5);
					if (newX + newY <= newRadius) {
						bIntersect = true;
						break;
					}
				}
			}
		
		} while (bIntersect);
		
		for (int i = 0; i < level.length; i += 9) {
			if (level[i+5] > 0) {
				level[i] = x;
				level[i+1] = y;
				level[i+2] = radius;
				level[i+3] = (int)(Math.random() * 360);
				level[i+4] = (int)(Math.random() * 12) + 5;
				if (this.p[1] > 5000) {
					if (this.p[1] > 50000) {
						level[i+4] -= 10;
					} else if (this.p[1] > 15000) {
						level[i+4] -= 7;
					} else {
						level[i+4] -= 4;
					}
					if (level[i+4] < 4) {
						level[i+4] = 4;
					}
				}
				level[i+5] = level[i+7] = 0;
				level[i+6] = -1;
				level[i+8] = 1;
				if (Math.random() * 100 > 50) {
					level[i+8] = -1;
				}
				break;
			}
		}
	}

	@Override
	public void think(int delta) {
		if (level[0] == -1) {
			this.loadLevel();
		} else {
			if (p[0] > 0) {
				if (p[2] <= 0) {
					p[2] = 1;
				} else {
					p[1] += 1;
					for (int i = 0; i < level.length; i+=9) {
						if ((level[i+6] >= 0) && (level[i+5] <= 0)) {
							ball[4] = 0;
							level[i+5] = 1;
							p[1] += 500;
							this.clocks += 1;
							// neue Uhr 
							ball[2] = level[i+3];
							ball[0] = level[i] + (int)((level[i+2]) * Math.sin(Math.toRadians(level[i+3])));
							ball[1] = level[i+1] - (int)((level[i+2]) * Math.cos(Math.toRadians(level[i+3])));
							this.setNewRandomClock();
							break;
						}
					}
				}
			}
			if ((p[2] > 0) && (p[3] <= 0)) {
				p[1] += 1;
				for (int i = 0; i < level.length; i+=9) {
					if (level[i+5] <= 0) {
						level[i+7] += 10;
						while (level[i+7] >= level[i+4]) {
							level[i+3] += level[i+8] * 1;
							if (level[i+3] >= 360) {
								level[i+3] -= 360;
							}
							if (level[i+3] < 0) {
								level[i+3] += 360;
							}
							level[i+7] -= level[i+4];
							if ((level[i+6] >= 0) && (level[i+3] == level[i+6])) {
								this.getGame().setAracardeHelp(this.p[1], this.clocks);
								return;
							}
						}
					}
				}
				if (ball[4] >= 0) {
					ball[0] += (ball[3] * 10) * Math.sin(Math.toRadians(ball[2]));
					ball[1] += (-ball[3] * 10) * Math.cos(Math.toRadians(ball[2]));
					
					if ((ball[0] < 0) || (ball[0] > 480)) {
						this.getGame().setAracardeHelp(this.p[1], this.clocks);
						return;
					}
					if ((ball[1] < 25) || (ball[1] > 595)) {
						this.getGame().setAracardeHelp(this.p[1], this.clocks);
						return;
					}
					
					for (int i = 0; i < level.length; i+=9) {
						if (level[i+5] <= 0) {
							float newX = (level[i] - ball[0]) * (level[i] - ball[0]);
							float newY = (level[i+1] - ball[1]) * (level[i+1] - ball[1]);
							float radius = (level[i+2] + 5) * (level[i+2] + 5);
							if (newX + newY <= radius) {
								ball[4] = -1;
								level[i+6] = level[i+3];
								break;
							}
						}
					}
				}
				boolean bBreak = true;
				for (int i = 0; i < level.length; i+=9) {
					if ((level[i+5] <= 0) && (level[i+6] < 0)) {
						bBreak = false;
						break;
					}
				}
				if (bBreak) {
					this.getGame().setAracardeHelp(this.p[1], this.clocks);
				}
			}
		}
		
		p[0] = 0;
	}

	@Override
	public void render(BitsGLGraphics g) {
		for (int i = 0; i < level.length; i+=9) {
			int points = 45;
			
			if (level[i+5] <= 0) {
				if (level[i+6] >= 0) {
					g.setColor(1f, 1f, 1f, 1f);
					g.fillCircle(level[i], level[i + 1], level[i+2] * 1, points);
					
					int startAngle = level[i+6] - 90;
					if (startAngle < 0) {
						startAngle += 360;
					}

					int dif = level[i+3] - level[i+6];
					if (level[i+6] > level[i+3]) {
						dif = dif + 360;
					}
					if (level[i+8] < 0) {
						dif = level[i+6] - level[i+3];
						if (level[i+6] < level[i+3]) {
							dif = dif + 360;
						}
						dif = -dif;
					}
					
					if (i+2 < level.length) {
						g.setColor(255f/255f, 120f/255f, 120f/255f, 1f);
						g.fillArc(level[i], level[i + 1], level[i+2] * 1, level[i+2] * 1, startAngle, dif, points);
					}

				} else {
					g.setColor(160f/255f, 160f/255f, 160f/255f, 1f);
					g.fillCircle(level[i], level[i + 1], level[i+2] * 1, points);
				}

				if (level[i+2] > 30) {
					points = 120;
				}
				g.setColor(0f/255f, 0f/255f, 0f/255f, 1f);
				g.setLineSize(2.0f);
				g.drawCircle(level[i], level[i + 1], level[i+2] * 1, points);
				g.setLineSize(3.0f);
				for (int j = 0; j < 12; j++) {
					g.setColor(0f, 0f, 0f, 1f);
					g.drawLine(level[i] + (int)((level[i+2] - 5) * Math.sin(Math.toRadians(j * 30))), level[i + 1] + (int)(-(level[i+2] - 5) * Math.cos(Math.toRadians(j * 30))), level[i] + (int)((level[i+2]) * Math.sin(Math.toRadians(j * 30))), level[i + 1] + (int)(-(level[i+2]) * Math.cos(Math.toRadians(j * 30))));
				}
				
				g.drawLine(level[i], level[i + 1], level[i] + (int)((level[i+2] - 5) * Math.sin(Math.toRadians(level[i+3]))), level[i + 1] + (int)(-(level[i+2] - 5) * Math.cos(Math.toRadians(level[i+3]))));
				g.setLineSize(1.0f);
			}
		}
		if (ball[4] >= 0) {
			g.setColor(255f/255f, 120f/255f, 120f/255f, 1.0f);
			g.fillCircle((int)(ball[0] - 2), (int)(ball[1] - 2), (int)(5), 120);
			g.setColor(0.0f, 0.0f, 0.0f, 1.0f);
			g.drawCircle((int)(ball[0] - 2), (int)(ball[1] - 2), (int)(5), 120);
		}
		
		g.setColor(128f/255f, 128f/255f, 128f/255f, 1.0f);
		g.fillRect(0,0,480,25);
		g.fillRect(0,590,480,50);
		
		g.setColor(48f/255f, 48f/255f, 48f/255f, 1.0f);
		g.drawRect(0,0,480,25);
		g.drawRect(0,590,480,50);

		String s = "ApoClock - Arcarde";
		this.getGame().drawString(g, s, 240, -4, ApoClockPanel.game_font);
		
		s = "Points: "+(p[1]);
		this.getGame().drawString(g, s, 5, -4, ApoClockPanel.game_font);
		
		if (p[3] > 0) {
			s = "Touch to restart";
			this.getGame().drawString(g, s, 190, 598, ApoClockPanel.game_font);
			
			s = "Congratulation";
			int w = this.getStringWidth().get(s);
			g.setColor(128f/255f, 128f/255f, 128f/255f, 1f);
			g.fillRect(240 - w/2 - 5, 277, w + 10, 36);
			g.setColor(48f/255f, 48f/255f, 48f/255f, 1.0f);
			g.drawRect(240 - w/2 - 5, 277, w + 10, 36);
			
			this.getGame().drawString(g, s, 240, 282, ApoClockPanel.game_font);
		} else {
			if (p[2] <= 0) {
				s = "Touch to start the game";
				this.getGame().drawString(g, s, 190, 598, ApoClockPanel.game_font);
			}
		}
		
		this.getGame().renderButtons(g);
	}

}