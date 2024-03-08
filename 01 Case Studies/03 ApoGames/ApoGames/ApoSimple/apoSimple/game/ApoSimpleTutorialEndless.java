package apoSimple.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import org.apogames.help.ApoFloatPoint;

import apoSimple.ApoSimpleConstants;

public class ApoSimpleTutorialEndless extends ApoSimpleGame {
	
	private final float MAX_CHANGE = -5;
	
	private final int MAX_DELTA = 20;
	
	private int step;
	
	private String curString;
	
	private int curStringInt;
	
	private int curDelta;
	
	private ApoSimpleTutorial tutorial;
	
	private ApoFloatPoint changePoint;
	
	private boolean bDecrease;
	
	
	public ApoSimpleTutorialEndless(ApoSimpleTutorial tutorial) {
		super(tutorial.getGame());
		
		this.tutorial = tutorial;
	}

	public void init() {
		super.init();
		this.step = 0;
		this.curString = ApoSimpleConstants.TUTORIAL_ENDLESS_SPEECH[this.step];
		this.curStringInt = 0;
		this.curDelta = 0;
		
		this.changePoint = new ApoFloatPoint(0, 0);
		this.bDecrease = true;
	}
	
	public void makeTextField() {
	}
	
	public void mouseButtonFunction(final String function) {
		if (this.curStringInt < this.curString.length()) {
			return;
		}
		if (function.equals(ApoSimpleGame.MENU)) {
			this.tutorial.closeTutorial();
		} else {
			if (this.curStringInt >= this.curString.length()) {
				if (this.step == 5) {
					if (function.equals(ApoSimpleGame.NOTICE)) {
						super.mouseButtonFunction(function);
						this.nextStep();
						return;
					}
				} else if (this.step == 6) {
					if (this.curStringInt >= this.curString.length()) {
						if (function.equals(ApoSimpleNotice.BACK)) {
							super.mouseButtonFunction(function);
							this.nextStep();
							return;
						}
					}
				}
			}
			super.mouseButtonFunction(function);
		}
	}
	
	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			this.tutorial.closeTutorial();
		} else if (button == KeyEvent.VK_ENTER) {
			if (this.curStringInt >= this.curString.length()) {
				if ((this.step == 4) || (this.step == 9)){
					
				} else {
					this.nextStep();
				}
			} else {
				this.curStringInt = this.curString.length();
			}
		}
	}
	
	public void nextStep() {
		super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
		if (this.step + 1 < ApoSimpleConstants.TUTORIAL_ENDLESS_SPEECH.length) {
			this.step++;
			this.curString = ApoSimpleConstants.TUTORIAL_ENDLESS_SPEECH[this.step];
			this.curStringInt = 0;
			this.curDelta = 0;
		} else {
			this.tutorial.closeTutorial();
		}
	}
	
	public void driverOutside() {
		this.setMoves(this.getMoves() + 1);
		super.driverOutside();
	}
	
	public void nextLevel() {
		if (this.step == 4) {
			this.loadNextLevel();
			this.nextStep();
		} else if (this.step == 9) {
			this.nextStep();
		}
	}
	
	private void loadNextLevel() {
		super.init();
	}
	
	public void fillLevel(int level, boolean bTutorial) {
		super.fillLevel(-1, true);
		if (this.step < 4) {
			this.getEntities()[5][3].setDirection(ApoSimpleConstants.UP);
			this.getEntities()[5][2].setDirection(ApoSimpleConstants.LEFT);
		} else if (this.step < 8) {
			this.getEntities()[5][3].setDirection(ApoSimpleConstants.DOG_LEFT);
			this.getEntities()[5][2].setDirection(ApoSimpleConstants.BLACK_DOWN);
			this.getEntities()[5][4].setDirection(ApoSimpleConstants.BLACK_UP);
		}
	}
	
	public void setFlowers(int count) {
		int color = this.getRandomColor();
		if (this.step < 4) {
			this.getEntities()[5][3].setType(color);
		} else if (this.step < 8) {
			this.getEntities()[4][1].setType(color);
			this.getEntities()[4][5].setType(color);
			this.getEntities()[6][3].setType(color);
		}
	}
	
	public void startDriver(int x, int y) {
		if (this.step == 4) {
			if ((x == 2) && (y == 5)) {
				super.startDriver(x, y);
			}
		} else {
			super.startDriver(x, y);
		}
	}
	
	@Override
	public void mouseButtonReleased(int x, int y, boolean right) {
		if (this.curStringInt >= this.curString.length()) {
			if ((this.step == 4) || (this.step == 9)) {
				super.mouseButtonReleased(x, y, right);
			} else {
				if ((this.step == 5) || (this.step == 6)) {
					
				} else {
					this.nextStep();
				}
			}
		} else {
			this.curStringInt = this.curString.length();
		}
	}
	
	public void think(int delta) {
		this.curDelta += delta;
		if (this.curDelta >= this.MAX_DELTA) {
			this.curDelta -= this.MAX_DELTA;
			if (this.curStringInt < this.curString.length()) {
				this.curStringInt++;
			}
		}
		
		if (this.bDecrease) {
			this.changePoint.x = this.changePoint.x - delta * 0.01f;
			this.changePoint.y = this.changePoint.y - delta * 0.01f;
		} else {
			this.changePoint.x = this.changePoint.x + delta * 0.01f;
			this.changePoint.y = this.changePoint.y + delta * 0.01f;
		}
		
		if (this.changePoint.x >= 0) {
			this.bDecrease = !this.bDecrease;
		} else if (this.changePoint.x <= this.MAX_CHANGE) {
			this.bDecrease = !this.bDecrease;
		}
		
		if (this.curStringInt >= this.curString.length()) {
			if ((this.step == 4) || (this.step == 9)){
				super.think(delta);	
			}
		}
		if ((this.step == 0) || (this.step == 5) || (this.step == 10)) {
			super.think(delta);	
		}
	}
	
	public void render(Graphics2D g) {
		super.render(g);
		
		if (this.getDriver() == null) {
			g.setFont(ApoSimpleGame.FONT_RESTART);
			String s = this.curString;
			int width = (int)(ApoSimpleConstants.ENTITY_WIDTH + 5) * this.getEntities()[0].length;
			int x = 10;
			if ((s != null) && (s.length() > 0)) {
				ArrayList<String> all = ApoSimpleConstants.drawSpeech(g, s, width - 10);
				int maxY = 45 + 25 * (all.size() - 1);
				this.drawTutorial(g, maxY);
				
				ArrayList<String> curDraw = ApoSimpleConstants.drawSpeech(g, s.substring(0, this.curStringInt), width - 10);
				for (int i = 0; i < curDraw.size(); i++) {
					String cur = curDraw.get(i);
					int w = g.getFontMetrics().stringWidth(all.get(i));
					g.drawString(cur, x + width/2 - w/2, 45 + i * 25);
				}
			}
			
			this.renderArrow(g);
		}
	}
	
	private void renderArrow(Graphics2D g) {
		if ((this.getMoves() >= 5) && (this.getDriver() == null)) {
			if (this.step == 4) {
				g.drawImage(ApoSimplePanel.iArrow, (int)(165 - ApoSimplePanel.iArrow.getWidth() + this.changePoint.x), (int)(380 - this.changePoint.y), null);
			} else if (this.step == 5) {
				g.drawImage(ApoSimplePanel.iArrow, (int)(500 - ApoSimplePanel.iArrow.getWidth() + this.changePoint.x), (int)(48 - this.changePoint.y), null);
			} else if (this.step == 6) {
				g.drawImage(ApoSimplePanel.iArrow, (int)(480 - ApoSimplePanel.iArrow.getWidth() + this.changePoint.x), (int)(105 - this.changePoint.y), null);
			} else if (this.step == 7) {
				g.drawImage(ApoSimplePanel.iArrow, (int)(165 - ApoSimplePanel.iArrow.getWidth() + this.changePoint.x), (int)(380 - this.changePoint.y), null);
			} else if (this.step == 8) {
				g.drawImage(ApoSimplePanel.iArrow, (int)(225 - ApoSimplePanel.iArrow.getWidth() + this.changePoint.x), (int)(380 - this.changePoint.y), null);
			}
		}
	}
	
	public void drawLevelInfo(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.setFont(ApoSimpleGame.FONT_BUTTON);
		String s = String.valueOf("Tutorial");
		int w = g.getFontMetrics().stringWidth(s);
		int x = 555;
		int y = 195;
		ApoSimpleGame.drawString(g, s, x - w/2, y);
	}
}
