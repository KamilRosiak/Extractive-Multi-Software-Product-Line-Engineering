package apoSimple.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import org.apogames.help.ApoFloatPoint;

import apoSimple.ApoSimpleConstants;
import apoSimple.game.level.ApoSimpleLevel;

public class ApoSimpleTutorialLevel extends ApoSimpleLevelGame {

	private final float MAX_CHANGE = -5;
	
	private final int MAX_DELTA = 20;
	
	private int step;
	
	private String curString;
	
	private int curStringInt;
	
	private int curDelta;
	
	private ApoSimpleTutorial tutorial;
	
	private ApoFloatPoint changePoint;
	
	private boolean bDecrease;
	
	public ApoSimpleTutorialLevel(ApoSimpleTutorial tutorial) {
		super(tutorial.getGame());
		
		this.tutorial = tutorial;
	}

	public void init() {
		super.init();
		this.step = 0;
		this.curString = ApoSimpleConstants.TUTORIAL_LEVEL_SPEECH[this.step];
		this.curStringInt = 0;
		this.curDelta = 0;
		
		this.makeLevel(new ApoSimpleLevel("13131313131313131313131313131313131313131313131313131313131313131313131313131313131313131302131313124"), false, ApoSimpleLevelGame.TUTORIAL);
		
		this.changePoint = new ApoFloatPoint(0, 0);
		this.bDecrease = true;
	}
	
	public void mouseButtonFunction(final String function) {
		if (this.curStringInt < this.curString.length()) {
			return;
		}
		if (function.equals(ApoSimpleGame.MENU)) {
			super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
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
					if (function.equals(ApoSimpleNotice.BACK)) {
						super.mouseButtonFunction(function);
						this.nextStep();
						return;
					}
				}
			}
			super.mouseButtonFunction(function);
		}
	}
	
	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
			this.tutorial.closeTutorial();
		} else if (button == KeyEvent.VK_ENTER) {
			if (this.curStringInt >= this.curString.length()) {
				if ((this.step == 3) || (this.step == 4) ||
					(this.step == 8) || (this.step == 9)){
					
				} else {
					this.nextStep();
				}
			} else {
				this.curStringInt = this.curString.length();
			}
		}
	}
	
	public void winGame() {
		super.getGame().playSound(ApoSimplePanel.SOUND_WIN_LEVELUP, 100);
		this.nextStep();
	}
	
	public void loseGameField() {
		super.getGame().playSound(ApoSimplePanel.SOUND_LOOSE, 100);
		this.makeNewLevel();
	}
	
	public void loseGameSteps() {
		super.getGame().playSound(ApoSimplePanel.SOUND_LOOSE, 100);
		this.makeNewLevel();
	}
	
	public void makeNewLevel() {
		this.makeLevel(this.getCurLevel(), false, ApoSimpleLevelGame.TUTORIAL);
	}
	
	public void nextStep() {
		super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
		if (this.step + 1 < ApoSimpleConstants.TUTORIAL_LEVEL_SPEECH.length) {
			this.step++;
			this.curString = ApoSimpleConstants.TUTORIAL_LEVEL_SPEECH[this.step];
			this.curStringInt = 0;
			this.curDelta = 0;
			
			if (this.step == 4) {
				this.makeLevel(new ApoSimpleLevel("13131313131313131313131313131313131313131313131313131313131313131313131313030204131313130201031313124"), false, ApoSimpleLevelGame.TUTORIAL);
			} else if (this.step == 5) {
				this.makeLevel(new ApoSimpleLevel("13131313131313131313131313131313131313131313131313131313130304040404130304020202010302020103040404135"), false, ApoSimpleLevelGame.TUTORIAL);
			} else if (this.step == 7) {
				this.makeLevel(new ApoSimpleLevel("13131313131313131313131313131313131313131313131313131313131313020313131302020103131313010206041313124"), false, ApoSimpleLevelGame.TUTORIAL);
			} else if (this.step == 9) {
				this.makeLevel(new ApoSimpleLevel("13131313131313131313131313131313131313131313131309091313131313101113131313090203101313130101031113246"), false, ApoSimpleLevelGame.TUTORIAL);
			}
		} else {
			this.tutorial.closeTutorial();
		}
	}
	
	@Override
	public void mouseButtonReleased(int x, int y, boolean right) {
		if (this.curStringInt >= this.curString.length()) {
			if ((this.step == 3) || (this.step == 4) ||
				(this.step == 8) || (this.step == 9)) {
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
			if ((this.step == 3) || (this.step == 4) ||
				(this.step == 8) || (this.step == 9)){
				super.think(delta);	
			}
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
		if (this.getMoves() <= 0) {
			if (this.step == 1) {
				g.drawImage(ApoSimplePanel.iArrow, (int)(530 - ApoSimplePanel.iArrow.getWidth() + this.changePoint.x), (int)(260 - this.changePoint.y), null);
			} else if (this.step == 2) {
				g.drawImage(ApoSimplePanel.iArrow, (int)(600 - ApoSimplePanel.iArrow.getWidth() + this.changePoint.x), (int)(35 - this.changePoint.y), null);
			} else if (this.step == 3) {
				g.drawImage(ApoSimplePanel.iArrow, (int)(211 - ApoSimplePanel.iArrow.getWidth() + this.changePoint.x), (int)(444 - this.changePoint.y), null);
			} else if (this.step == 5) {
				g.drawImage(ApoSimplePanel.iArrow, (int)(500 - ApoSimplePanel.iArrow.getWidth() + this.changePoint.x), (int)(48 - this.changePoint.y), null);
			} else if (this.step == 6) {
				g.drawImage(ApoSimplePanel.iArrow, (int)(480 - ApoSimplePanel.iArrow.getWidth() + this.changePoint.x), (int)(105 - this.changePoint.y), null);
			} else if (this.step == 7) {
				g.drawImage(ApoSimplePanel.iArrow, (int)(220 - ApoSimplePanel.iArrow.getWidth() + this.changePoint.x), (int)(447 - this.changePoint.y), null);
			} else if (this.step == 9) {
				g.drawImage(ApoSimplePanel.iArrow, (int)(150 - ApoSimplePanel.iArrow.getWidth() + this.changePoint.x), (int)(370 - this.changePoint.y), null);
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
