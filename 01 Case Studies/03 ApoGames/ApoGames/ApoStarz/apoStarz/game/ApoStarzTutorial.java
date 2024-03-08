package apoStarz.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import org.apogames.ApoConstants;

import apoStarz.ApoStarzConstants;

public class ApoStarzTutorial {

	private ApoStarzGame game;
	
	private final int MAX_DELTA = 50;
	
	private int step;
	
	private String curString;
	
	private int curStringInt;
	
	private int curDelta;
	
	private BufferedImage iStarTutorial;
	
	public ApoStarzTutorial(ApoStarzGame game) {
		this.game = game;
		if (this.iStarTutorial == null) {
			this.iStarTutorial = this.game.images.getImageFromPath("images/tutorial_star.png", false);
		}
	}
	
	public void init() {
		this.step = 0;
		this.curString = ApoStarzConstants.TUTORIAL_SPEECH[this.step];
		this.curStringInt = 0;
		this.curDelta = 0;
		
		if (ApoConstants.B_APPLET) {
			this.game.levelLoad(ApoStarzGame.APPLET_LEVEL + "tutorial.ase");
		} else {
			this.game.levelLoad(System.getProperty("user.dir") + File.separator+ "levels" + File.separator + "tutorial.ase");
		}
	}
	
	public void keyPressed(int keyCode) {
		if (this.curStringInt >= this.curString.length()) {
			if (keyCode == KeyEvent.VK_ENTER) {
				if ((this.step == 4) || (this.step == 6) || (this.step == 8) || (this.step == 10) || (this.step == 12) || (this.step == 14)) {
				} else {
					this.nextStep();
				}
			}
			if ((this.step == 4) || (this.step == 6) || (this.step == 8) || (this.step == 10) || (this.step == 12) || (this.step == 14)) {
				if ((!this.game.level.isBFalling()) && (!this.game.level.isBWin()) && (!this.game.level.isBLost())) {
					if (keyCode == KeyEvent.VK_LEFT) {
						this.game.rotateLevel(-1);
					}
					if (keyCode == KeyEvent.VK_RIGHT) {
						this.game.rotateLevel(1);
					}
				}
			}
		} else {
			/*if (keyCode == KeyEvent.VK_ENTER) {
				this.curStringInt = this.curString.length();
			}*/
		}
	}
	
	public void setButtonFunction(String function) {
		if (function.equals("menu")) {
			this.game.setMenuMode();
		} else if (function.equals("nextStepTutorial")) {
			if ((this.step != 4) && (this.step != 6) && (this.step != 8) && (this.step != 10) && (this.step != 12) && (this.step != 14)) {
				this.nextStep();
			}
		} else if (function.equals("leftRotation")) {
			if (this.curStringInt >= this.curString.length()) {
				if ((this.step == 4) || (this.step == 6) || (this.step == 8) || (this.step == 10) || (this.step == 12) || (this.step == 14)) {
					if ((!this.game.level.isBFalling()) && (!this.game.level.isBWin()) && (!this.game.level.isBLost())) {
						this.game.rotateLevel(-1);
					}
				}
			}
		} else if (function.equals("rightRotation")) {
			if (this.curStringInt >= this.curString.length()) {
				if ((this.step == 4) || (this.step == 6) || (this.step == 8) || (this.step == 10) || (this.step == 12) || (this.step == 14)) {
					if ((!this.game.level.isBFalling()) && (!this.game.level.isBWin()) && (!this.game.level.isBLost())) {
						this.game.rotateLevel(1);
					}
				}
			}
		} else if (function.equals("restart")) {
			if (this.curStringInt >= this.curString.length()) {
				if ((this.step == 4) || (this.step == 6) || (this.step == 8) || (this.step == 10) || (this.step == 12) || (this.step == 14)) {
					if ((!this.game.level.isBFalling()) && (!this.game.level.isBWin()) && (!this.game.level.isBLost())) {
						this.game.restart();
					}
				}
			}
		}
	}
	
	public void nextStep() {
		if (this.step + 1 < ApoStarzConstants.TUTORIAL_SPEECH.length) {
			this.step++;
			this.curString = ApoStarzConstants.TUTORIAL_SPEECH[this.step];
			this.curStringInt = 0;
			this.curDelta = 0;
			if ((this.step == 6) || (this.step == 8) || (this.step == 10) || (this.step == 12) || (this.step == 14)) {
				this.game.nextLevel();
			}
		} else {
			this.game.setMenuMode();
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
		if ((this.step == 4) || (this.step == 6) || (this.step == 8) || (this.step == 10) || (this.step == 12) || (this.step == 14)) {
			if (this.game.level.isBWin()) {
				this.nextStep();
			} else if (this.game.level.isBLost()) {
				this.game.restart();
			}
		}
	}

	public void render(Graphics2D g) {
		g.drawImage(this.iStarTutorial, 0, 0, null);
		
		g.setColor(Color.yellow);
		g.fillRect(ApoStarzConstants.GAME_GAME_WIDTH + 3, 159, ApoStarzConstants.GAME_MENU_WIDTH - 6, 251);
		g.setColor(Color.black);
		g.setFont(ApoStarzConstants.FONT_NORMAL);
		g.drawLine(ApoStarzConstants.GAME_GAME_WIDTH + 5, 450, ApoStarzConstants.GAME_WIDTH - 5, 450);
		g.drawLine(ApoStarzConstants.GAME_GAME_WIDTH + 5, 250, ApoStarzConstants.GAME_WIDTH - 5, 250);
		g.drawLine(ApoStarzConstants.GAME_GAME_WIDTH + 5, 190, ApoStarzConstants.GAME_WIDTH - 5, 190);
		int x = ApoStarzConstants.GAME_GAME_WIDTH + ApoStarzConstants.GAME_MENU_WIDTH/2;
		this.game.drawString(g, "The tutorial Mode", x, 180);
		
		this.game.drawString(g, "Press Enter or the", x, 280);
		this.game.drawString(g, "next step button to", x, 300);
		this.game.drawString(g, "start the next step", x, 320);
		this.game.drawString(g, "of the tutorial", x, 340);
		
		this.game.renderSteps(g);
		
		this.drawSpeech(g, this.curString.substring(0, this.curStringInt));
	}
	
	private void drawSpeech(Graphics2D g, String s) {
		g.setColor(Color.black);
		g.setFont(ApoStarzConstants.FONT_NORMAL);
		
		int curHeight = 0;
		int[] maxLength = new int[] {250, 300};
		int x = 142;
		int y = 30;
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
		for ( int i = 0; i < strings.size(); i++ ) {
			g.drawString(strings.get(i), x, y);
			y += 20;
		}
	}
}
