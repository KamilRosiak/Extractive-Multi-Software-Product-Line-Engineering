package apoSlitherLink.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import apoSlitherLink.ApoSlitherLinkConstants;
import apoSlitherLink.level.ApoSlitherLinkLevelHelp;

public class ApoSlitherLinkTutorial extends ApoSlitherLinkGame {
	
	private final int MAX_DELTA = 20;
	
	public static final String MENU_STRING = "Menu";
	public static final String MENU = "tutorial_menu";
	
	private int step;
	
	private String curString;
	
	private int curStringInt;
	
	private int curDelta;
	
	public ApoSlitherLinkTutorial(ApoSlitherLinkPanel game) {
		super(game);
	}
	
	public void init() {
		super.init(false);
		
		this.step = 0;
		this.curString = ApoSlitherLinkConstants.TUTORIAL_SPEECH[this.step];
		this.curStringInt = 0;
		this.curDelta = 0;
		
		this.getGame().getLevel().setEasyLevels(1);
		this.getGame().getLevel().makeLevelImage();
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ENTER) {
			if (this.step != 7) {
				this.clickOrPressed();
			}
		} else if (button == KeyEvent.VK_R) {
			if (this.step == 7) {
				this.getGame().getLevel().retryLevel();
			}
		} else if (button == KeyEvent.VK_ESCAPE) {
			this.getGame().setMenu();
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoSlitherLinkTutorial.MENU)) {
			this.getGame().setMenu();
		}
	}
	
	@Override
	public void mouseButtonReleased(int x, int y, boolean bRight) {
		if (this.step == 7) {
			if (this.curStringInt < this.curString.length()) {
				this.clickOrPressed();
			} else {
				super.mouseButtonReleased(x, y, bRight);
				if (super.isBWin()) {
					this.clickOrPressed();
				}
			}
		} else {
			this.clickOrPressed();
		}
	}
	
	private void clickOrPressed() {
		if (this.curStringInt < this.curString.length()) {
			this.curStringInt = this.curString.length();
		} else {
			this.step += 1;
			if (this.step >= ApoSlitherLinkConstants.TUTORIAL_SPEECH.length) {
				this.mouseButtonFunction(ApoSlitherLinkTutorial.MENU);
			} else {
				this.curString = ApoSlitherLinkConstants.TUTORIAL_SPEECH[this.step];
				this.curStringInt = 0;
				this.curDelta = 0;
			}
		}
	}
	
	public void mousePressed(int x, int y) {
		if (this.step == 7) {
			if (this.curStringInt >= this.curString.length()) {
				super.mousePressed(x, y);
			}
		}
	}
	
	public void mouseMoved(int x, int y) {
		if (this.step == 7) {
			if (this.curStringInt >= this.curString.length()) {
				super.mouseMoved(x, y);
			}
		}
	}

	@Override
	public void think(int delta) {
		super.think(delta);
		this.curDelta += delta;
		if (this.curDelta >= this.MAX_DELTA) {
			this.curDelta -= this.MAX_DELTA;
			if (this.curStringInt < this.curString.length()) {
				this.curStringInt++;
			}
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		super.render(g);
		
		g.setColor(ApoSlitherLinkLevelHelp.COLOR_LINE);
		g.setFont(ApoSlitherLinkConstants.FONT_TUTORIAL);
		this.drawSpeech(g, this.curString.substring(0, this.curStringInt), 5, 28, ApoSlitherLinkConstants.GAME_WIDTH - 10);
	}
	

	private void drawSpeech(Graphics2D g, String s, int x, int y, int width) {
		int curHeight = 0;
		int[] maxLength = new int[] {width - 4, width - 4, width - 4, width - 4};
		ArrayList<String> strings = new ArrayList<String>();
		strings.add(s);
		int cur = 0;
		int w = g.getFontMetrics().stringWidth(strings.get(cur));
		if (w > maxLength[curHeight]) {
			int curPos = strings.get(cur).indexOf(" ");
			while ((curPos > -1) && (g.getFontMetrics().stringWidth(strings.get(cur).substring(0, curPos)) < maxLength[curHeight])) {
				int nextPos = strings.get(cur).indexOf(" ", curPos + 1);
				if (nextPos != -1) {
					if (g.getFontMetrics().stringWidth(strings.get(cur).substring(0, nextPos)) >= maxLength[curHeight]) {
						String curString = strings.get(cur);
						strings.set(cur, curString.substring(0, curPos));
						cur++;
						if (curHeight == 0) {
							curHeight++;
						}
						strings.add(curString.substring(curPos + 1));
						curPos = strings.get(cur).indexOf(" ");
					} else {
						curPos = nextPos;
					}
				} else {
					String curString = strings.get(cur);
					if (g.getFontMetrics().stringWidth( curString ) > maxLength[curHeight]) {
						strings.set( cur, curString.substring(0, curPos) );
						cur++;
						strings.add( curString.substring(curPos + 1));
					}
					break;
				}
			}
		}
		int h = g.getFontMetrics().getHeight();
		for ( int i = 0; i < strings.size(); i++ ) {
			g.drawString(strings.get(i), x, y);
			y += h;
		}
	}

}
