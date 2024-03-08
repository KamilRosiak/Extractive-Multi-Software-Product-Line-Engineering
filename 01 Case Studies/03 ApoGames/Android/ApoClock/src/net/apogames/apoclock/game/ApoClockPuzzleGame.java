package net.apogames.apoclock.game;

import net.apogames.apoclock.ApoClockModel;
import net.apogames.apoclock.entity.ApoClockEntityBall;
import net.apogames.apoclock.entity.ApoClockEntityClock;
import net.apogames.apoclock.level.ApoClockLevel;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;

public class ApoClockPuzzleGame extends ApoClockModel {

	public static final String BACK = "back";
	
	private ApoClockEntityClock[] clocks = new ApoClockEntityClock[1];
	
	/**
	 * 0 = X-Wert,
	 * 1 = y-Wert,
	 * 2 = Angle,
	 * 3 = Geschwindigkeit
	 */
	private ApoClockEntityBall ball;
	
	/**
	 * this.curLevel == aktuelles Level,
	 * this.winInt == Level geschafft Klick,
	 * this.touchX == Maus X-Wert,
	 * this.touchY == Maus Y-Wert
	 */
	private int curLevel, touchX, touchY, winInt;
	
	/**
	 * bPressed == losgelassen,
	 * bStarted == Spiel gestartet
	 */
	private boolean bStarted, bPressed;
	
	private static final String[] HELP = new String[] {
		"Your goal is to reach all the clocks",
		"When the ball hits a clock the countdown starts",
		"Touch to fire the ball with the",
		"current clockwise direction"
	};
	
	private boolean bNewStart = true;
	
	private boolean bEditor = false;
	
	private boolean bUserlevels;
	
	private String levelString;
	
	public ApoClockPuzzleGame(ApoClockPanel game) {
		super(game);
	}

	@Override
	public void init() {		
		this.getStringWidth().put(ApoClockPuzzleGame.BACK, (int)(ApoClockPanel.font.getLength(ApoClockPuzzleGame.BACK)));
		
		String s = "ApoClock";
		this.getStringWidth().put(s, (int) ApoClockPanel.game_font.getLength(s));
		s = "Congratulation";
		this.getStringWidth().put(s, (int) ApoClockPanel.title_font.getLength(s));
		s = "Touch to start the next level";
		this.getStringWidth().put(s, (int) ApoClockPanel.game_font.getLength(s));
		s = "Please try again";
		this.getStringWidth().put(s, (int) ApoClockPanel.title_font.getLength(s));
		s = "Touch to restart the level";
		this.getStringWidth().put(s, (int) ApoClockPanel.game_font.getLength(s));
		s = "Touch to start the level";
		this.getStringWidth().put(s, (int) ApoClockPanel.game_font.getLength(s));
		s = HELP[0];
		this.getStringWidth().put(s, (int) ApoClockPanel.game_font.getLength(s));
		s = HELP[1];
		this.getStringWidth().put(s, (int) ApoClockPanel.game_font.getLength(s));
		s = HELP[2];
		this.getStringWidth().put(s, (int) ApoClockPanel.game_font.getLength(s));
		s = HELP[3];
		this.getStringWidth().put(s, (int) ApoClockPanel.game_font.getLength(s));
	}
	
	@Override
	public void touchedPressed(int x, int y, int finger) {
		this.bPressed = true;
		this.touchX = x;
		this.touchY = y;
	}

	@Override
	public void touchedReleased(int x, int y, int finger) {
		
	}

	@Override
	public void touchedDragged(int x, int y, int oldX, int oldY, int finger) {
		
	}

	@Override
	public void touchedButton(String function) {
		if (function.equals(ApoClockPuzzleGame.BACK)) {
			this.onBackButtonPressed();
		}
	}
	
	public void onBackButtonPressed() {
		if (this.bEditor) {
			this.getGame().setEditor(false);
		} else if (this.bUserlevels) {
			this.getGame().setPuzzle();
		} else {
			this.getGame().setPuzzleChooser();
		}
	}
	
	protected void loadLevel(final int newLevel, final boolean bUserLevels) {
		this.bUserlevels = bUserLevels;
		this.curLevel = newLevel;
		if (this.curLevel < 0) {
			if (this.bUserlevels) {
				this.curLevel = ApoClockLevel.editorLevels.length - 1;
			} else {
				this.curLevel = this.getGame().getMaxCanChoosen();
			}
		}
		if (this.bUserlevels) {
			if (this.curLevel >= ApoClockLevel.editorLevels.length) {
				this.curLevel = 0;
			}
		} else if (this.curLevel > this.getGame().getMaxCanChoosen()) {
			this.curLevel = 0;
		}
		
		if (this.bUserlevels) {
			this.loadLevelWithString(ApoClockLevel.editorLevels[this.curLevel], false);
		} else {
			this.loadLevelWithString(ApoClockLevel.getLevel(this.curLevel), false);
		}
	}
	
	public void loadLevelWithString(final String level, boolean bEditor) {
		this.levelString = level;
		
		String[] l = level.split(",");
		
		this.clocks = new ApoClockEntityClock[l.length/5];
		for (int y = 0; y < l.length; y += 5) {
			float clockX = Integer.valueOf(l[y + 0].replaceAll(" ", ""));
			float clockY = Integer.valueOf(l[y + 1].replaceAll(" ", ""));
			float clockRadius = Integer.valueOf(l[y + 2].replaceAll(" ", ""));
			float clockStartAngle = Integer.valueOf(l[y + 3].replaceAll(" ", ""));
			float clockRotationVelocity = Integer.valueOf(l[y + 4].replaceAll(" ", ""));
			this.clocks[y/5] = new ApoClockEntityClock(clockX, clockY, clockRadius, Math.abs(clockStartAngle), clockRotationVelocity);
			if (clockStartAngle < 0) {
				this.clocks[y/5].setRotateClockwise(false);
			}
		}
		this.ball = new ApoClockEntityBall(5, 260, 5, 90, 0.2f);
		
		this.bNewStart = false;
		
		this.bPressed = false;
		this.winInt = 0;
		this.bStarted = false;
		this.bEditor = bEditor;
	}

	@Override
	public void think(int delta) {
		if (this.bNewStart) {
			if (this.bEditor) {
				this.loadLevelWithString(this.levelString, this.bEditor);
			} else {
				this.loadLevel(this.curLevel, this.bUserlevels);
			}
		} else {
			if (this.bPressed) {
				if ((this.touchX > 10) && (this.touchX < 70) &&
					(this.touchY > 595) && (this.touchY < 635) && (!this.bEditor)) {
					this.curLevel -= 1;
					this.bNewStart = true;
					this.touchX = -1;
					this.touchY = -1;
					this.loadLevel(this.curLevel, this.bUserlevels);
				} else if ((this.touchX > 310) && (this.touchX < 370) &&
						(this.touchY > 595) && (this.touchY < 635) && (!this.bEditor)) {
					this.curLevel += 1;
					this.bNewStart = true;
					this.touchX = -1;
					this.touchY = -1;
					this.loadLevel(this.curLevel, this.bUserlevels);
				} else if (this.winInt > 0) {
					this.bNewStart = true;
					if (this.winInt == 1) {
						if (this.bEditor) {
							this.getGame().setEditor(true);
						} else {
							this.curLevel += 1;
							if (!this.bUserlevels) {
								this.getGame().solvedLevel(this.curLevel);
							}
							this.think(delta);
						}
					}
				} else if (!this.bStarted) {
					this.bStarted = true;
				} else {
					for (int i = 0; i < this.clocks.length; i++) {
						if ((this.clocks[i].getStartangle() >= 0) && (this.clocks[i].isVisible())) {
							this.ball.setVisible(true);
							this.clocks[i].setVisible(false);
							this.ball.setX(this.clocks[i].getX() + (int)((this.clocks[i].getRadius()) * Math.sin(Math.toRadians(this.clocks[i].getAngle()))));
							this.ball.setY(this.clocks[i].getY() - (int)((this.clocks[i].getRadius()) * Math.cos(Math.toRadians(this.clocks[i].getAngle()))));
							this.ball.setAngle(this.clocks[i].getAngle());
							break;
						}
					}
				}
			}
			if ((this.bStarted) && (this.winInt <= 0)) {
				for (int i = 0; i < this.clocks.length; i++) {
					this.clocks[i].think(delta);
					
					if (this.clocks[i].isVisible()) {
						this.clocks[i].setCurTime(this.clocks[i].getCurTime() + delta);
						while (this.clocks[i].getCurTime() >= this.clocks[i].getRotateVelocity()) {
							int add = 1;
							if (!this.clocks[i].isRotateClockwise()) {
								add = -1;
							}
							this.clocks[i].setAngle(this.clocks[i].getAngle() + add);
							if (this.clocks[i].getAngle() >= 360) {
								this.clocks[i].setAngle(this.clocks[i].getAngle() - 360);
							}
							if (this.clocks[i].getAngle() < 0) {
								this.clocks[i].setAngle(this.clocks[i].getAngle() + 360);
							}
							this.clocks[i].setCurTime(this.clocks[i].getCurTime() - this.clocks[i].getRotateVelocity());
						
							if ((this.clocks[i].getStartangle() >= 0) && ((int)this.clocks[i].getAngle() == (int)(this.clocks[i].getStartangle()))) {
								this.winInt = 2;
								break;
							}
						}
						
					}
					
					
				}
				if (this.ball.isVisible()) {
					this.ball.think(delta);
					
					if ((this.ball.getX() < 0) || (this.ball.getX() > 480)) {
						this.winInt = 2;
					}
					if ((this.ball.getY() < 25) || (this.ball.getY() > 595)) {
						this.winInt = 2;
					}
					
					for (int i = 0; i < this.clocks.length; i++) {
						if (this.clocks[i].isVisible()) {
							if (this.ball.intersects(this.clocks[i].getX(), this.clocks[i].getY(), this.clocks[i].getRadius())) {
								this.ball.setVisible(false);
								this.clocks[i].setStartangle(this.clocks[i].getAngle());
								break;
							}
						}
					}
				}
				boolean bBreak = true;
				for (int i = 0; i < this.clocks.length; i++) {
					if ((this.clocks[i].isVisible()) && (this.clocks[i].getStartangle() < 0)) {
						bBreak = false;
						break;
					}
				}
				if (bBreak) {
					this.winInt = 1;
				}
			}
		}
		
		this.bPressed = false;
	}

	@Override
	public void render(BitsGLGraphics g) {
		for (int i = 0; i < this.clocks.length; i++) {
			this.clocks[i].render(g, 0, 0);
		}
		this.ball.render(g);
		
		g.setColor(128f/255f, 128f/255f, 128f/255f, 1.0f);
		g.fillRect(0,0,480,25);
		g.fillRect(0,590,480,50);
		
		g.setColor(48f/255f, 48f/255f, 48f/255f, 1.0f);
		g.drawRect(0,0,480,25);
		g.drawRect(0,590,480,50);

		String s = "ApoClock";
		this.getGame().drawString(g, s, 240, -4, ApoClockPanel.game_font);
		
		if (this.bEditor) {
			s = "Editorlevel";
		} else if (this.bUserlevels) {
			s = "Level "+(this.curLevel + 1)+" / "+(ApoClockLevel.editorLevels.length);
		} else {
			s = "Level "+(this.curLevel + 1)+" / "+(this.getGame().getMaxCanChoosen()+1);
		}
		this.getGame().drawString(g, s, 5, -4, ApoClockPanel.game_font);
		
		if (this.winInt > 0) {
			if (this.winInt == 1) {
				s = "Touch to start the next level";
				this.getGame().drawString(g, s, 190, 598, ApoClockPanel.game_font);
				
				s = "Congratulation";
			} else if (this.winInt > 1) {
				s = "Touch to restart the level";
				this.getGame().drawString(g, s, 190, 598, ApoClockPanel.game_font);
				
				s = "Please try again";
			}

			int w = this.getStringWidth().get(s);
			g.setColor(128f/255f, 128f/255f, 128f/255f, 1f);
			g.fillRect(240 - w/2 - 10, 277, w + 20, 10 + ApoClockPanel.title_font.mCharCellHeight);
			g.setColor(48f/255f, 48f/255f, 48f/255f, 1.0f);
			g.drawRect(240 - w/2 - 10, 277, w + 20, 10 + ApoClockPanel.title_font.mCharCellHeight);
			
			this.getGame().drawString(g, s, 240, 282, ApoClockPanel.title_font);
		} else {
			if (!this.bStarted) {
				s = "Touch to start the level";
				this.getGame().drawString(g, s, 190, 598, ApoClockPanel.game_font);
				
				if ((this.curLevel == 0) && (!this.bEditor) && (!this.bUserlevels)) {
					g.setColor(128f/255f, 128f/255f, 128f/255f, 1f);
					g.fillRect(0, 495, 480, 99);
					g.setColor(48f/255f, 48f/255f, 48f/255f, 1.0f);
					g.drawRect(0, 495, 480, 145);
					
					s = HELP[0];
					this.getGame().drawString(g, s, 240, 500, ApoClockPanel.game_font);
					
					s = HELP[1];
					this.getGame().drawString(g, s, 240, 520, ApoClockPanel.game_font);
					
					s = HELP[2];
					this.getGame().drawString(g, s, 240, 540, ApoClockPanel.game_font);
					
					s = HELP[3];
					this.getGame().drawString(g, s, 240, 560, ApoClockPanel.game_font);
				}
			}
			if (!this.bEditor) {
				try {
					g.setColor(192f/255f, 192f/255f, 192f/255f, 1.0f);
					g.fillRect(10, 595, 60, 39);
					g.fillRect(310, 595, 60, 39);
					g.setColor(48f/255f, 48f/255f, 48f/255f, 1.0f);
					g.drawRect(10, 595, 60, 39);
					g.drawRect(310, 595, 60, 39);
					
					g.setLineSize(3.0f);
					float[] color = new float [] {64.0f/255f, 64.0f/255f, 64.0f/255f};
					if ((this.touchX > 10) && (this.touchX < 70) &&
						(this.touchY > 595) && (this.touchY < 635)) {
						color = new float [] {255.0f/255f, 255.0f/255f, 72.0f/255f};
					}
					g.setColor(color[0], color[1], color[2], 1.0f);
					g.drawLine(30, 615, 50, 605);
					g.drawLine(30, 615, 50, 625);
					color = new float [] {64.0f/255f, 64.0f/255f, 64.0f/255f};
					if ((this.touchX > 310) && (this.touchX < 370) &&
						(this.touchY > 595) && (this.touchY < 635)) {
						color = new float [] {255.0f/255f, 255.0f/255f, 72.0f/255f};
					}
					g.setColor(color[0], color[1], color[2], 1.0f);
					g.drawLine(350, 615, 330, 605);
					g.drawLine(350, 615, 330, 625);
					g.setLineSize(1.0f);
				} catch (Exception ex) {}
			}
		}
		
		this.getGame().renderButtons(g);
	}

}
