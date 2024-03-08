package apoSimpleSudoku.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apogames.ApoConstants;
import org.apogames.entity.ApoButton;
import org.apogames.help.ApoHelp;

import apoSimpleSudoku.ApoSimpleSudokuConstants;
import apoSimpleSudoku.level.ApoSimpleSudokuLevel;

public class ApoSimpleSudokuLevelChooser extends ApoSimpleSudokuModel {

	private final Color c = new Color(ApoSimpleSudokuConstants.BROWN.getRed(), ApoSimpleSudokuConstants.BROWN.getGreen(), ApoSimpleSudokuConstants.BROWN.getBlue(), 60);
	
	/** eindeutige Stringvariable für den MenuButton */
	public static String MENU = "menu";

	private ArrayList<ApoButton> buttons;
	
	private int maxLevel;
	
	private ArrayList<Integer> solvedLevels;
	
	public ApoSimpleSudokuLevelChooser(ApoSimpleSudokuPanel game) {
		super(game);
	}
	
	public void init() {
		this.maxLevel = 31;
		if (this.buttons == null) {
			this.buttons = new ArrayList<ApoButton>();
			
			Font font = new Font(Font.SANS_SERIF, Font.BOLD, 20);
			String text = "10";
			String function = "button";
			int width = 55;
			int height = 55;
			
			int x = 20;
			int y = 20;
			for (int i = 0; i < this.maxLevel; i++) {
				this.buttons.add(new ApoButton(this.getGame().getImages().getButtonImageLevelChooser(width * 3, height, text, Color.WHITE, new Color(0, 0, 0, 0), Color.BLACK, ApoSimpleSudokuLevel.c, new Color(254, 0, 0, 140), font, 250), x, y, width, height, function));
				x += 65;
				if (x + width >= ApoSimpleSudokuConstants.GAME_WIDTH - 20) {
					x = 20;
					y += 75;
					if ((y > 100) && (y < 200)) {
						y = 310;
					}
				}
				this.buttons.get(this.buttons.size() - 1).setBVisible(true);
			}
		}
		if (this.solvedLevels == null) {
			this.solvedLevels = new ArrayList<Integer>();
			Thread t = new Thread(this.getGame()) {
				public void run() {
					ApoSimpleSudokuLevelChooser.this.loadCookies();
				}
			};
			t.start();
		}
		if (super.getBackground() == null) {
			super.makeBackground(ApoSimpleSudokuConstants.GAME_WIDTH, ApoSimpleSudokuConstants.GAME_HEIGHT);
			Graphics2D g = super.getBackground().createGraphics();
			
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			Stroke stroke = g.getStroke();
			g.setStroke(new BasicStroke(5));
			
			int change = 50;
			
			g.setColor(ApoSimpleSudokuLevel.c);
			g.fillRect(7, ApoSimpleSudokuConstants.GAME_HEIGHT/2 - change, ApoSimpleSudokuConstants.GAME_WIDTH - 14, 2 * change);
			
			g.setColor(Color.DARK_GRAY);
			g.drawRoundRect(5, 5, ApoSimpleSudokuConstants.GAME_WIDTH - 12, ApoSimpleSudokuConstants.GAME_HEIGHT - 12, 20, 20);
			
			g.drawLine(7, ApoSimpleSudokuConstants.GAME_HEIGHT/2 - change, ApoSimpleSudokuConstants.GAME_WIDTH - 2 * 5, ApoSimpleSudokuConstants.GAME_HEIGHT/2 - change);
			g.drawLine(7, ApoSimpleSudokuConstants.GAME_HEIGHT/2 + change, ApoSimpleSudokuConstants.GAME_WIDTH - 2 * 5, ApoSimpleSudokuConstants.GAME_HEIGHT/2 + change);
			
			g.setColor(Color.BLACK);
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 36));
			String text = "Levels";
			
			int width = ApoSimpleSudokuConstants.GAME_WIDTH - 12;
			int h = g.getFontMetrics().getHeight();
			int circleRadius = h + 2;
			Stroke basicStroke = new BasicStroke(3);
			int lineWidth = 5;
			int[] nextYSet = new int[text.length()];
			for (int i = 0; i < nextYSet.length; i++) {
				nextYSet[i] = (int)(Math.random() * (80 - circleRadius - 4)) + 2 + 10;
			}
			int startX = (int)(width/2 - (text.length()/2f)*(circleRadius) - ((text.length()-1)/2f)*(lineWidth)) + 5;
			int nextWidth = circleRadius + lineWidth;
			for (int t = 0; t < text.length(); t++) {
				String s = String.valueOf(text.charAt(t));
				int w = g.getFontMetrics().stringWidth(s);
				g.setStroke(basicStroke);
				g.setColor(ApoSimpleSudokuConstants.BROWN);
				g.fillOval(startX + 1, ApoSimpleSudokuConstants.GAME_HEIGHT/2 - change + nextYSet[t], circleRadius - 1, circleRadius - 1);
				g.setColor(Color.BLACK);
				g.drawOval(startX + 1, ApoSimpleSudokuConstants.GAME_HEIGHT/2 - change + nextYSet[t], circleRadius - 1, circleRadius - 1);
				if (t != text.length() - 1) {
					g.drawLine(startX + circleRadius + 1, ApoSimpleSudokuConstants.GAME_HEIGHT/2 - change + nextYSet[t] + circleRadius/2 - 1, startX + circleRadius + 1 + lineWidth, ApoSimpleSudokuConstants.GAME_HEIGHT/2 - change + nextYSet[t + 1] + circleRadius/2 - 1);
				}
				g.setStroke(stroke);
				int realHeight = h - 2*g.getFontMetrics().getDescent();
				g.setColor(Color.WHITE);
				g.drawString(s, startX + circleRadius/2 - w/2, ApoSimpleSudokuConstants.GAME_HEIGHT/2 - change + nextYSet[t] + circleRadius/2 + realHeight/2);
				startX += nextWidth;
			}
			
			g.setStroke(stroke);
			
			g.dispose();
		}
	}
	
	public void loadCookies() {
		if (ApoConstants.B_APPLET) {
			String achievements = null;
			try {
				achievements = ApoHelp.loadData(new URL(ApoSimpleSudokuConstants.PROGRAM_URL), ApoSimpleSudokuConstants.COOKIE_NAME);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			while ((achievements != null) && (achievements.length() > 0) && (achievements.indexOf(",") >= 0)) {
				try {
					String curString = achievements.substring(0, achievements.indexOf(","));
					int value = Integer.valueOf(curString);
					this.solvedLevels.add(value);
					achievements = achievements.substring(achievements.indexOf(",") + 1);
				} catch (NumberFormatException ex) {
					break;
				} catch (Exception ex) {
					break;
				}
			}
		}
	}
	
	public void saveCookies() {
		if (ApoConstants.B_APPLET) {
			String cookie = "";
			for (int i = 0; i < this.solvedLevels.size(); i++) {
				cookie += String.valueOf(this.solvedLevels.get(i)) + ",";
			}
			try {
				ApoHelp.saveData(new URL(ApoSimpleSudokuConstants.PROGRAM_URL), ApoSimpleSudokuConstants.COOKIE_NAME, cookie);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void solveLevel(int level) {
		if (this.solvedLevels.indexOf(level) < 0) {
			this.solvedLevels.add(level);
			this.saveCookies();
		}
	}
	
	public int getMaxLevel() {
		return this.maxLevel;
	}

	public boolean setMaxLevel(int maxLevel) {
		if (maxLevel > this.maxLevel) {
			this.maxLevel = maxLevel;
			return true;
		}
		return false;
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			this.getGame().setMenu();
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (ApoSimpleSudokuCredits.MENU.equals(function)) {
			this.getGame().setMenu();
		}
		if (!this.getGame().shouldRepaint()) {
			this.getGame().render();
		}
	}


	@Override
	public void think(long delta) {
	}
	
	@Override
	public void mouseReleased(int x, int y, boolean right) {
		if (this.buttons != null) {
			for (int i = 0; i < this.buttons.size(); i++) {
				if (this.buttons.get(i).isBVisible()) {
					if (this.buttons.get(i).getReleased(x, y)) {
						this.getGame().setGame(i);
						this.getGame().render();
					}
				}
			}
		}
	}
	
	public boolean mousePressed(int x, int y, boolean bRight) {
		if (this.buttons != null) {
			for (int i = 0; i < this.buttons.size(); i++) {
				if (this.buttons.get(i).isBVisible()) {
					if (this.buttons.get(i).getPressed(x, y)) {
						this.getGame().render();
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean mouseMoved(int x, int y) {
		boolean bOver = false;
		if (this.buttons != null) {
			for (int i = 0; i < this.buttons.size() && i <= this.maxLevel; i++) {
				if (this.buttons.get(i).getMove(x, y)) {
					bOver = true;
					super.getGame().render();
					break;
				} else if (this.buttons.get(i).isBOver()) {
					bOver = true;
				}
			}
		}
		if (bOver) {
			super.getGame().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		} else {
			super.getGame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		return false;
	}

	@Override
	public void render(Graphics2D g) {
		if (super.getBackground() != null) {
			g.drawImage(super.getBackground(), 0, 0, null);
		}
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		for (int i = 0; i < this.buttons.size() && i <= this.maxLevel; i++) {
			if (this.buttons.get(i).isBVisible()) {
				this.buttons.get(i).render(g, 0, 0);
				
				if (this.solvedLevels.indexOf(i) >= 0) {
					g.setColor(this.c);
					int width = 40;
					int x = (int)(this.buttons.get(i).getX() + this.buttons.get(i).getWidth()/2 - width/2);
					int y = (int)(this.buttons.get(i).getY() + this.buttons.get(i).getHeight()/2 - width/2);
					g.fillOval(x, y, width + 1, width + 1);
				}
				
				g.setColor(Color.BLACK);
				g.setFont(ApoSimpleSudokuConstants.FONT_LEVEL_CHOOSER);
				String s = String.valueOf((i + 1));
				int w = g.getFontMetrics().stringWidth(s);
				int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
				float x = this.buttons.get(i).getX() + this.buttons.get(i).getWidth()/2;
				float y = this.buttons.get(i).getY() + this.buttons.get(i).getHeight()/2;
				g.drawString(s, x - w/2, y + h /2);
			}
		}
	}

}
