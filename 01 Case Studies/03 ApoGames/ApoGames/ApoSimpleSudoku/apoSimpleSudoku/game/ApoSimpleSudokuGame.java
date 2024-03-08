package apoSimpleSudoku.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import org.apogames.help.ApoHelp;

import apoSimpleSudoku.ApoSimpleSudokuConstants;
import apoSimpleSudoku.level.ApoSimpleSudokuLevel;
import apoSimpleSudoku.level.ApoSimpleSudokuLevelMake;

/**
 * Klasse, die das eigentliche Spiel rep√§sentiert und alle Aktionen dadrin handelt<br />
 * Vom laden, Speichern √ºber die Mausevents usw.<br />
 * @author Dirk Aporius
 *
 */
public class ApoSimpleSudokuGame extends ApoSimpleSudokuModel {
	
	/** eindeutige Stringvariable f¸r den MenuButton */
	public static String MENU = "menu";
	/** eindeutige Stringvariable f¸r den RestartButton */
	public static String RESTART = "restart";
	/** eindeutige Stringvariable f¸r den NextButton */
	public static String NEXT = "next";
	
	public static final int startX = 10;
	public static final int startY = 10;
	public static final int width = 450;
	public static final int height = 450;
	
	private ApoSimpleSudokuLevel level;
	
	private long time;
	
	private int levelInt;
	
	private BufferedImage iWin;
	
	private int timer;
	
	/**
	 * Konstruktor
	 * @param game : Das Gameobjekt der Hauptklasse f¸r das Spiel
	 */
	public ApoSimpleSudokuGame(ApoSimpleSudokuPanel game) {
		super(game);
	}
	
	public void init(final int level) {
		this.levelInt = level;
		this.timer = 0;
		this.getGame().getButtons()[10].setBVisible(false);
		this.level = ApoSimpleSudokuLevelMake.getLevel(level);
		if (this.level == null) {
			this.levelInt = 0;
			this.init(this.levelInt);
		}
		
		super.makeBackground(ApoSimpleSudokuConstants.GAME_WIDTH, ApoSimpleSudokuConstants.GAME_HEIGHT);
		Graphics2D g = super.getBackground().createGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Stroke stroke = g.getStroke();
		g.setStroke(new BasicStroke(5));
		
		int startX = ApoSimpleSudokuGame.startX - 5;
		int startY = ApoSimpleSudokuGame.startY - 5;
		int width = ApoSimpleSudokuGame.width + 10;
		int height = ApoSimpleSudokuGame.height + 10;
		g.setColor(new Color(220, 220, 220));
		g.fillRoundRect(startX, startY, width, height, 20, 20);
		g.setColor(Color.DARK_GRAY);
		g.drawRoundRect(startX, startY, width, height, 20, 20);		
		g.drawLine(startX + width, startY + 40, startX + width + 120, startY + 40);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		String s = "time";
		g.drawString(s, startX + width + 10, startY + 35);

		g.drawLine(startX + width, startY + 320, startX + width + 80, startY + 320);
		s = "level";
		g.drawString(s, startX + width + 10, startY + 315);
		
		g.setStroke(stroke);
		
		if (this.level != null) {
			g.drawImage(this.level.getIBackground(), ApoSimpleSudokuGame.startX, ApoSimpleSudokuGame.startY, null);
		}
		
		g.dispose();
		
		this.time = System.nanoTime();
	}

	public void keyButtonPressed(int button, char character) {
	}
	
	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			this.stopGame();
			this.getGame().render();
		} else if (button == KeyEvent.VK_R) {
			this.init(this.levelInt);
			this.getGame().render();
		} else if (button == KeyEvent.VK_N) {
			this.nextLevel();
			this.getGame().render();
		} else if (button == KeyEvent.VK_ENTER) {
			if (this.getGame().getButtons()[10].isBVisible()) {
				this.nextLevel();
				this.getGame().render();
			}
		} else if (button == KeyEvent.VK_P) {
			this.previousLevel();
			this.getGame().render();
		} else {
			if (this.level.keyButtonReleased(button, character)) {
				this.getGame().render();
				this.checkWin();
			}
		}
	}
	
	private void makeWinImage() {
		this.getGame().getButtons()[10].setBVisible(true);
		this.iWin = new BufferedImage(ApoSimpleSudokuConstants.GAME_WIDTH, ApoSimpleSudokuConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = this.iWin.createGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		this.renderIngame(g);
		
		Stroke stroke = g.getStroke();
		g.setStroke(new BasicStroke(5));
		
		int width = ApoSimpleSudokuGame.width + 10;
		int height = ApoSimpleSudokuGame.height + 10;
		int realWidth = 300;
		int realHeight = 200;
		int startX = width/2 - realWidth/2;
		int startY = height/2 - realHeight/2;
		g.setColor(new Color(255, 255, 255, 200));
		g.fillRoundRect(startX, startY, realWidth, realHeight, 20, 20);
		g.setColor(Color.DARK_GRAY);
		g.drawRoundRect(startX, startY, realWidth, realHeight, 20, 20);
		
		g.setStroke(stroke);
		g.setFont(ApoSimpleSudokuConstants.FONT_LEVEL_CHOOSER);
		g.setColor(Color.BLACK);
		String s = "Congratulation";
		int w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, width/2 - w/2, startY + 35);
		
		s = "You solved the level in";
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, width/2 - w/2, startY + 95);
		
		s = ApoHelp.getTimeToDraw((int)((System.nanoTime() - this.time)/1000000))+" minutes";
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, width/2 - w/2, startY + 120);
		
		g.dispose();
	}
	
	private void checkWin() {
		if (this.level.isWin()) {
			this.getGame().solveLevel(this.levelInt);
			this.makeWinImage();
			this.time = System.nanoTime() - this.time;
			super.getGame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			this.getGame().render();
		}
	}
	
	@Override
	public void mouseReleased(int x, int y, boolean right) {
		if (this.level != null) {
			if (this.level.isWin()) {
			} else if ((x >= ApoSimpleSudokuGame.startX) && (y >= ApoSimpleSudokuGame.startY) && 
				(x < ApoSimpleSudokuGame.startX + ApoSimpleSudokuGame.width) && (y < ApoSimpleSudokuGame.startY + ApoSimpleSudokuGame.height)) {
				if (this.level.mouseReleased(x, y, right)) {
					this.getGame().render();
					this.checkWin();
				}
			}
		}
	}
	
	private void nextLevel() {
		this.init(this.levelInt + 1);
	}
	
	private void previousLevel() {
		this.init(this.levelInt - 1);
	}
	
	public boolean mousePressed(int x, int y, boolean bRight) {
		if (this.level != null) {
			if (this.level.isWin()) {
			} else if ((x >= ApoSimpleSudokuGame.startX) && (y >= ApoSimpleSudokuGame.startY) && 
				(x < ApoSimpleSudokuGame.startX + ApoSimpleSudokuGame.width) && (y < ApoSimpleSudokuGame.startY + ApoSimpleSudokuGame.height)) {
				if (this.level.mousePressed(x, y, bRight)) {
					this.getGame().render();
				}
			}
		}
		return false;
	}
	
	public boolean mouseMoved(int x, int y) {
		if (this.level != null) {
			if (this.level.isWin()) {
			} else if ((x >= ApoSimpleSudokuGame.startX) && (y >= ApoSimpleSudokuGame.startY) && 
				(x < ApoSimpleSudokuGame.startX + ApoSimpleSudokuGame.width) && (y < ApoSimpleSudokuGame.startY + ApoSimpleSudokuGame.height)) {
				int value = this.level.mouseMoved(x, y);
				if (value == 1) {
					super.getGame().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					super.getGame().render();
				} else if (value == 2) {
					if (!super.getGame().getCursor().equals(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR))) {
						super.getGame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						super.getGame().render();
					}
				}
			} else {
				this.level.mouseMoved(-1, -1);
				if (!super.getGame().getCursor().equals(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR))) {
					super.getGame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					super.getGame().render();
				}
			}
		}
		return false;
	}
	
	@Override
	public void mouseButtonFunction(String function) {
		if (ApoSimpleSudokuGame.MENU.equals(function)) {
			this.stopGame();
		} else if (ApoSimpleSudokuGame.RESTART.equals(function)) {
			this.init(this.levelInt);
		} else if (ApoSimpleSudokuGame.NEXT.equals(function)) {
			this.nextLevel();
		}
		if (!this.getGame().shouldRepaint()) {
			this.getGame().render();
		}
	}
	
	/**
	 * wird aufgerufen, wenn der Stopbutton geklickt wurde und stoppt das eigentliche Spiel
	 */
	private void stopGame() {
		this.getGame().setLevelChooser();
	}
	
	@Override
	public void think(long delta) {
		this.timer += delta;
		if (this.timer >= 1000) {
			this.getGame().render();
			this.timer -= 1000;
		}
	}

	@Override
	public void render(Graphics2D g) {
		if (this.level.isWin()) {
			g.drawImage(this.iWin, 0, 0, null);
		} else {
			this.renderIngame(g);
		}
	}
	
	private void renderIngame(Graphics2D g) {
		if (super.getBackground() != null) {
			g.drawImage(super.getBackground(), 0, 0, null);
		}
		if (this.level != null) {
			this.level.render(g);
		}
		long time = System.nanoTime() - this.time;
		String s = String.valueOf(ApoHelp.getTimeToDraw((int)(time/1000000)));
		if (s.length() < 5) {
			s = "00:00";
		}
		int x = 5 + 450 + 130 + 40/2;
		int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
		for (int i = 0; i < s.length(); i++) {
			String p = s.substring(i, i + 1);
			int y = 5 + h/2 + 40 + i * (50);
			if (i >= 0) {
				Stroke stroke = g.getStroke();
				g.setStroke(new BasicStroke(3));
				g.setColor(Color.WHITE);
				g.fillOval(x - 40/2, y - h/2 - 40/2, (int)(40), (int)(40));
				g.setColor(Color.BLACK);
				g.drawOval(x - 40/2, y - h/2 - 40/2, (int)(40), (int)(40));
				if (i >= 1) {
					g.drawLine(x, y - h/2 - 40/2 - 8, x, y - h/2 - 40/2);
				}
				g.setStroke(stroke);
			}
			g.setColor(Color.BLACK);
			int w = g.getFontMetrics().stringWidth(p);
			g.drawString(p, x - w/2, y);
		}
		
		s = String.valueOf(this.levelInt + 1);
		x = 5 + 450 + 90 + 20;
		int y = 5 + h/2 + 320;
		for (int i = 0; i < s.length(); i++) {
			String p = s.substring(i, i + 1);
			Stroke stroke = g.getStroke();
			g.setStroke(new BasicStroke(3));
			g.setColor(Color.WHITE);
			g.fillOval(x - 20, y - h/2 - 40/2, (int)(40), (int)(40));
			g.setColor(Color.BLACK);
			g.drawOval(x - 20, y - h/2 - 40/2, (int)(40), (int)(40));
			if (i + 1 < s.length()) {
				g.drawLine(x + 20, y - h/2, x + 25, y - h/2);
			}
			g.setStroke(stroke);
			g.setColor(Color.BLACK);
			int w = g.getFontMetrics().stringWidth(p);
			g.drawString(p, x - w/2, y);
			x += 45;
		}
	}
}
