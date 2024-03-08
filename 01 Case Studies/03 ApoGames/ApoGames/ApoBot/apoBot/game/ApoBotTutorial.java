package apoBot.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import org.apogames.ApoConstants;

import apoBot.ApoBotComponent;
import apoBot.ApoBotConstants;

/**
 * Klasse, die sich um das Tutorial kümmert
 * @author Dirk Aporius
 *
 */
public class ApoBotTutorial {

	private ApoBotGame game;
	
	private final int MAX_DELTA = 25;
	
	private int step;
	
	private String curString;
	
	private int curStringInt;
	
	private int curDelta;
	
	public ApoBotTutorial(ApoBotGame game) {
		this.game = game;
	}
	
	/**
	 * läd das Tutoriallevel und setzt die Standardwerte
	 */
	public void init() {
		if (ApoConstants.B_APPLET) {
			this.game.loadLevel(ApoBotComponent.APPLET_LEVEL + "tutorial.bot");
		} else {
			this.game.loadLevel(System.getProperty("user.dir") + File.separator + "levels" + File.separator + "tutorial.bot");			
		}
		
		this.step = 0;
		this.curString = ApoBotConstants.TUTORIAL_SPEECH[this.step];
		this.curStringInt = 0;
		this.curDelta = 0;
		this.game.getButtons()[4].setBVisible(false);
		this.game.getButtons()[12].setBVisible(false);
		this.game.getButtons()[13].setBVisible(false);
	}
	
	/**
	 * gibt zurück, ob der Spieler gerade gehen kann oder nicht
	 * ist abhängig davon welcher Text gerade gezeigt wird
	 * @return TRUE; Spieler kann gehen; sonst false
	 */
	public boolean canGo() {
		if ((this.step == 5) || (this.step == 6) || (this.step == 7) || (this.step == 9) || (this.step == 11)) {
			return true;
		}
		return false;
	}

	/**
	 * wird aufgerufen, wenn eine Taste gedrückt wurde
	 * @param keyCode
	 */
	public void keyPressed(int keyCode) {
		if (keyCode == KeyEvent.VK_ESCAPE) {
			this.game.setMenu();
		}
		if (this.curStringInt >= this.curString.length()) {
			if (keyCode == KeyEvent.VK_ENTER) {
				if ((this.step == 5) || (this.step == 6) || (this.step == 7) || (this.step == 9) || (this.step == 11)) {
				} else {
					this.nextStep();
				}
			}
		} else {
			if (keyCode == KeyEvent.VK_ENTER) {
				this.curStringInt = this.curString.length();
			}
		}
	}

	/**
	 * wird aufgerufen, wenn ein neuer Text gezeigt werden soll
	 */
	public void nextStep() {
		if ((this.step == 5) || (this.step == 6) || (this.step == 7) || (this.step == 9) || (this.step == 11)) {
			if (!this.game.isBWin()) {
				return;
			}
		}
		if (this.step + 1 < ApoBotConstants.TUTORIAL_SPEECH.length) {
			this.step++;
			this.curString = ApoBotConstants.TUTORIAL_SPEECH[this.step];
			this.curStringInt = 0;
			this.curDelta = 0;
			if ((this.step == 5) || (this.step == 6) || (this.step == 7) || (this.step == 9) || (this.step == 11)) {
				this.game.getButtons()[4].setBVisible(true);
				this.game.getButtons()[12].setBVisible(true);				
			} else {
				this.game.getButtons()[4].setBVisible(false);
				this.game.getButtons()[12].setBVisible(false);
				this.game.getButtons()[13].setBVisible(false);				
			}
			if ((this.step == 6) || (this.step == 7) || (this.step == 8) || (this.step == 10)) {
				this.game.setButtonFunction("gameLevelRight");
			}
		} else {
			this.game.setMenu();
		}
	}
	
	/**
	 * wird aufgerufen beim "nachdenken" und erhöht die Zeichen die gemalt werden vom Text und überprüft, ob das Level
	 * gewonnen wurde oder nicht
	 * @param delta
	 */
	public void think(int delta) {
		this.curDelta += delta;
		if (this.curDelta >= this.MAX_DELTA) {
			this.curDelta -= this.MAX_DELTA;
			if (this.curStringInt < this.curString.length()) {
				this.curStringInt++;
			}
		}
		if ((this.step == 5) || (this.step == 6) || (this.step == 7) || (this.step == 9) || (this.step == 11)) {
			if (this.game.isBWin()) {
				this.nextStep();
			}
			this.game.thinkMove(delta);
		}
	}

	/**
	 * malt alles wichtige was nur beim Tutorial gemalt wird
	 * @param g
	 */
	public void renderMenuTutorial(Graphics2D g) {
		g.setColor(Color.black);
		g.setFont(ApoBotConstants.FONT_MENU);
		int x = ApoBotConstants.GAME_GAME_WIDTH / 2;
		int y = ApoBotConstants.GAME_HEIGHT - 50;
		this.game.getImage().drawString(g, "Press Enter to start the next", x, y, 0);
		this.game.getImage().drawString(g, "step of the tutorial or to", x, y + 1 * 20, 0);
		this.game.getImage().drawString(g, "complete the sentence.", x, y + 2 * 20, 0);
		
		this.drawSpeech(g, this.curString.substring(0, this.curStringInt));
		
		if ((this.step == 3) || (this.step == 4)) {
			this.drawArrow(g, 0, (int)(this.game.getButtons()[5].getX() + 16), (int)(this.game.getButtons()[5].getY() + 35));
			this.drawArrow(g, 0, (int)(this.game.getButtons()[6].getX() + 16), (int)(this.game.getButtons()[9].getY() + 35));
			this.drawArrow(g, 0, (int)(this.game.getButtons()[7].getX() + 16), (int)(this.game.getButtons()[9].getY() + 35));
			this.drawArrow(g, 0, (int)(this.game.getButtons()[8].getX() + 16), (int)(this.game.getButtons()[9].getY() + 35));
			this.drawArrow(g, 0, (int)(this.game.getButtons()[9].getX() + 16), (int)(this.game.getButtons()[9].getY() + 35));
			
			this.drawArrow(g, 1, (int)(x = ApoBotConstants.GAME_WIDTH - 32 * 4 - 4 * 5 - 5), (int)(50));
		} else if (this.step == 5) {
			this.drawArrow(g, 0, (int)(this.game.getButtons()[5].getX() + 16), (int)(this.game.getButtons()[5].getY() + 35));
			this.drawArrow(g, 0, (int)(this.game.getButtons()[9].getX() + 16), (int)(this.game.getButtons()[9].getY() + 35));
			
			this.drawArrow(g, 2, (int)(this.game.getButtons()[4].getX() + this.game.getButtons()[4].getWidth()/2), (int)(this.game.getButtons()[4].getY()  - 5));

			this.drawArrow(g, 1, (int)(x = ApoBotConstants.GAME_WIDTH - 32 * 4 - 4 * 5 - 5), (int)(50));
		} else if (this.step == 6) {
			this.drawArrow(g, 0, (int)(this.game.getButtons()[6].getX() + 16), (int)(this.game.getButtons()[5].getY() + 35));
			this.drawArrow(g, 0, (int)(this.game.getButtons()[7].getX() + 16), (int)(this.game.getButtons()[9].getY() + 35));
			
			this.drawArrow(g, 1, (int)(x = ApoBotConstants.GAME_WIDTH - 32 * 4 - 4 * 5 - 5), (int)(50));
		} else if (this.step == 7) {
			this.drawArrow(g, 0, (int)(this.game.getButtons()[8].getX() + 16), (int)(this.game.getButtons()[5].getY() + 35));
			
			this.drawArrow(g, 1, (int)(x = ApoBotConstants.GAME_WIDTH - 32 * 4 - 4 * 5 - 5), (int)(50));
		} else if ((this.step == 8) || (this.step == 9)) {
			this.drawArrow(g, 0, (int)(this.game.getButtons()[10].getX() + 16), (int)(this.game.getButtons()[5].getY() + 35));
			
			this.drawArrow(g, 1, (int)(x = ApoBotConstants.GAME_WIDTH - 32 * 4 - 4 * 5 - 5), (int)(50));
			this.drawArrow(g, 1, (int)(x = ApoBotConstants.GAME_WIDTH - 32 * 4 - 4 * 5 - 5), (int)(100));
		} else if ((this.step == 10) || (this.step == 11)) {
			this.drawArrow(g, 0, (int)(this.game.getButtons()[10].getX() + 16), (int)(this.game.getButtons()[5].getY() + 35));
			this.drawArrow(g, 0, (int)(this.game.getButtons()[11].getX() + 16), (int)(this.game.getButtons()[5].getY() + 35));
			
			this.drawArrow(g, 1, (int)(x = ApoBotConstants.GAME_WIDTH - 32 * 4 - 4 * 5 - 5), (int)(50));
		}
	}
	
	private void drawArrow(Graphics2D g, int dir, int x, int y) {
		if (dir == 0) {
			g.setColor(Color.blue);
			g.fillRect(x - 3, y + 10, 6, 10);
			Polygon poly = new Polygon();
			poly.addPoint(x, y);
			poly.addPoint(x + 10, y + 10);
			poly.addPoint(x - 10, y + 10);
			g.fillPolygon(poly);
		} else if (dir == 1) {
			g.setColor(Color.blue);
			g.fillRect(x - 20, y - 3, 10, 6);
			Polygon poly = new Polygon();
			poly.addPoint(x, y);
			poly.addPoint(x - 10, y - 10);
			poly.addPoint(x - 10, y + 10);
			g.fillPolygon(poly);
		} else if (dir == 2) {
			g.setColor(Color.blue);
			g.fillRect(x - 3, y - 20, 6, 10);
			Polygon poly = new Polygon();
			poly.addPoint(x, y);
			poly.addPoint(x + 10, y - 10);
			poly.addPoint(x - 10, y - 10);
			g.fillPolygon(poly);
		}
	}
	
	private void drawSpeech(Graphics2D g, String s) {
		g.setFont(ApoBotConstants.FONT_WIN);
		g.setColor(new Color(255, 255, 255));
		int y = 55;
		g.fillRoundRect(5, y, ApoBotConstants.GAME_GAME_WIDTH - 10, 100, 40, 40);
		g.setColor(Color.black);
		
		int curHeight = 0;
		int[] maxLength = new int[] {ApoBotConstants.GAME_GAME_WIDTH - 20, ApoBotConstants.GAME_GAME_WIDTH - 20};
		int x = 10;
		y += 30;
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
			y += 23;
		}
	}
	
}
