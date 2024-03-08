package apoCommando.panels;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import apoCommando.ApoMarioConstants;

public class ApoMarioTutorial {
	
	private final int MAX_DELTA = 20;
	
	private ApoMarioGame game;
	
	private int step;
	
	private String curString;
	
	private int curStringInt;
	
	private int curDelta;
	
	private boolean bTutorial;
	
	public ApoMarioTutorial(ApoMarioGame game) {
		this.game = game;
		
		this.bTutorial = false;
	}
	
	public boolean isBTutorial() {
		return this.bTutorial;
	}

	public void setBTutorial(boolean tutorial) {
		this.bTutorial = tutorial;
		
		if (this.bTutorial) {
			this.step = 0;
			this.curString = ApoMarioConstants.TUTORIAL_SPEECH[this.step];
			this.curStringInt = 0;
			this.curDelta = 0;
		}
	}
	
	private void nextCheck() {
		if (this.curStringInt < this.curString.length()) {
			this.curStringInt = this.curString.length();
		} else {
			this.step += 1;
			if (this.step >= ApoMarioConstants.TUTORIAL_SPEECH.length) {
				this.game.getGame().setAnalysis();
			} else {
				this.curString = ApoMarioConstants.TUTORIAL_SPEECH[this.step];
				this.curStringInt = 0;
				this.curDelta = 0;
			}
		}
	}

	public boolean checkCurString(String checkCurString) {
		if (this.bTutorial) {
			if ((this.step != 4) && (this.step != 6)) {
				if (checkCurString.equals("next")) {
					this.nextCheck();
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean think(int delta) {
		this.curDelta += delta;
		if (this.curDelta >= this.MAX_DELTA) {
			this.curDelta -= this.MAX_DELTA;
			if (this.curStringInt < this.curString.length()) {
				this.curStringInt++;
			}
		}
		if (this.curStringInt >= this.curString.length()) {
			if (this.step == 4) {
				if (this.game.getGame().getLevel().getPlayer().getX() > 14 * ApoMarioConstants.TILE_SIZE) {
					this.nextCheck();
					this.game.getGame().getLevel().setBDark(true);
				}
				return true;
			} else if (this.step == 6) {
				if (this.game.getGame().getLevel().getPlayer().getX() > 24 * ApoMarioConstants.TILE_SIZE) {
					this.nextCheck();
					this.game.getGame().getLevel().setBDark(false);
				}
				return true;
			} else if (this.step == 9) {
				return true;
			} else {
			}
		}
		return false;
	}
	
	public void renderTutorial(Graphics2D g) {
		g.setFont(ApoMarioConstants.FONT_DOS_BOX);
		g.setColor(Color.WHITE);
		this.drawSpeech(g, this.curString.substring(0, this.curStringInt), 16, 40, ApoMarioConstants.GAME_WIDTH - 32);
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
