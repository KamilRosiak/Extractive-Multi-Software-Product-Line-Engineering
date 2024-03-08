package apoSimple.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.apogames.help.ApoHighscore;

public class ApoSimpleHighscore extends ApoSimpleModel {

	public static final String MENU_STRING = "menu";
	public static final String MENU = "highscore_menu";
	
	public static final String LEFT_STRING = "left";
	public static final String LEFT = "highscore_left";
	public static final String RIGHT_STRING = "right";
	public static final String RIGHT = "highscore_right";
	public static final String WEEK_STRING = "week";
	public static final String WEEK = "week";
	public static final String MONTH_STRING = "month";
	public static final String MONTH = "month";
	public static final String ALL_STRING = "all";
	public static final String ALL = "all";
	public static final String DAY_STRING = "day";
	public static final String DAY = "day";

	public static final int MAX_X = 10;
	
	private BufferedImage iBackground;
	
	private ApoHighscore highscore;
	
	private ArrayList<ApoSimpleHighscoreHelp> help;
	
	private int curPos;
	
	private int curChoose;
	
	public ApoSimpleHighscore(ApoSimplePanel game) {
		super(game);
	}
	
	@Override
	public void init() {
		if (this.curChoose <= 0) {
			this.curChoose = 29;
		}
		this.changeCurChoose(this.curChoose);
		if (this.help == null) {
			this.help = new ArrayList<ApoSimpleHighscoreHelp>();
			
		}
 		if (this.iBackground == null) {
 			this.iBackground = this.getGame().getImages().getImage("images/highscore.png", false);
		}
 		this.loadHighscore(true);
	}
	
	public void loadHighscore(final boolean bFirst) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				ApoSimpleHighscore.this.load(bFirst);
			}
 		});
 		t.start();
	}
	
	private void load(boolean bFirst) {
		try {
			this.highscore = ApoHighscore.getInstance();
			if (this.highscore.load()) {
				this.setTime(bFirst, this.getTime());
			}
		} catch (Exception ex) {
			if (this.help == null) {
				this.help = new ArrayList<ApoSimpleHighscoreHelp>();
			}
			this.help.clear();
			this.highscore = null;
			System.out.println("Fehler: "+ex.getMessage());
		}
	}
	
	public long getTime() {
		if (this.curChoose == 29) {
			return -1;
		} else if (this.curChoose == 30) {
			return 60*60*24*31;
		} else if (this.curChoose == 31) {
			return 60*60*24*7;
		} else if (this.curChoose == 32) {
			return 60*60*24;
		}
		return -1;
	}
	
	public Point getPosition(int points) {
		Point p = new Point(-1, -1);
		if (this.help != null) {
			for (int i = 0; i < this.help.size(); i++) {
				if (points > this.help.get(i).getPoints()) {
					p.x = i + 1;
					p.y = this.help.size() + 1;
					return p;
				}
			}
			p.x = this.help.size() + 1;
			p.y = this.help.size() + 1;
		}
		return p;
	}
	
	public ArrayList<ApoSimpleHighscoreHelp> getHelp() {
		return this.help;
	}

	private void sortList(boolean bFirst, final long time) {
		if (this.highscore == null) {
			return;
		}
		this.curPos = 0;
		this.help = new ArrayList<ApoSimpleHighscoreHelp>();
		if (this.highscore.getPoints().size() > 0) {
			for (int i = 0; i < this.highscore.getPoints().size(); i++) {
				if (time > 0) {
					float myTime = this.highscore.getCurTimes().get(i) - this.highscore.getTimes().get(i);
					if (myTime > time) {
						continue;
					}
				}
				boolean bBreak = false;
				for (int j = 0; j < this.help.size(); j++) {
					if (this.help.get(j).getPoints() < this.highscore.getPoints().get(i)) {
						this.help.add(j, new ApoSimpleHighscoreHelp(this.highscore.getNames().get(i), this.highscore.getPoints().get(i), this.highscore.getLevels().get(i), this.highscore.getTimes().get(i), this.highscore.getCurTimes().get(i)));
						bBreak = true;
						break;
					}
				}
				if (!bBreak) {
					this.help.add(new ApoSimpleHighscoreHelp(this.highscore.getNames().get(i), this.highscore.getPoints().get(i), this.highscore.getLevels().get(i), this.highscore.getTimes().get(i), this.highscore.getCurTimes().get(i)));					
				}
			}
		}
		if (bFirst) {
			if (this.help.size() > ApoSimpleHighscore.MAX_X) {
				this.getGame().getButtons()[12].setBVisible(false);
				this.getGame().getButtons()[13].setBVisible(true);
			} else {
				this.getGame().getButtons()[12].setBVisible(false);
				this.getGame().getButtons()[13].setBVisible(false);
			}
		}
	}
	
	private void setTime(boolean bFirst, long time) {
		this.sortList(bFirst, time);
	}
	
	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
			this.getGame().setMenu();
		} else if (button == KeyEvent.VK_LEFT) {
			super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
			this.nextHighscore(-1);
		} else if (button == KeyEvent.VK_RIGHT) {
			super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
			this.nextHighscore(+1);
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
		if (function.equals(ApoSimpleHighscore.MENU)) {
			this.getGame().setMenu();
		} else if (function.equals(ApoSimpleHighscore.LEFT)) {
			this.nextHighscore(-1);
		} else if (function.equals(ApoSimpleHighscore.RIGHT)) {
			this.nextHighscore(+1);
		} else if (function.equals(ApoSimpleHighscore.WEEK)) {
			this.changeCurChoose(31);
			this.setTime(true, this.getTime());
		} else if (function.equals(ApoSimpleHighscore.MONTH)) {
			this.changeCurChoose(30);
			this.setTime(true, this.getTime());
		} else if (function.equals(ApoSimpleHighscore.DAY)) {
			this.changeCurChoose(32);
			this.setTime(true, this.getTime());
		} else if (function.equals(ApoSimpleHighscore.ALL)) {
			this.changeCurChoose(29);
			this.setTime(true, this.getTime());
		}
	}
	
	private void nextHighscore(int plus) {
		int realPlus = plus * ApoSimpleHighscore.MAX_X;
		this.curPos += realPlus;
		if (this.curPos >= this.help.size()) {
			this.curPos -= realPlus;
			this.getGame().getButtons()[13].setBVisible(false);
		} else if (this.curPos < 0) {
			this.curPos = 0;
			this.getGame().getButtons()[12].setBVisible(false);			
		}
		if (this.curPos + realPlus >= this.help.size()) {
			this.getGame().getButtons()[13].setBVisible(false);
		} else {
			this.getGame().getButtons()[13].setBVisible(true);			
		}
			
		if (this.curPos > 0) {
			this.getGame().getButtons()[12].setBVisible(true);
		} else {
			this.getGame().getButtons()[12].setBVisible(false);
		}
	}
	
	@Override
	public void mouseButtonReleased(int x, int y, boolean bRight) {
	}

	@Override
	public void think(int delta) {
		
	}
	
	private void changeCurChoose(int curChoose) {
		this.curChoose = curChoose;
		if (this.curChoose > 0) {
			for (int i = 29; i < 33; i++) {
				this.getGame().getButtons()[i].setBVisible(true);
			}
			this.getGame().getButtons()[this.curChoose].setBVisible(false);
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		if (this.iBackground != null) {
			g.drawImage(this.iBackground, 0, 0, null);
			
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			for (int i = this.curPos; i < this.help.size() && i < ApoSimpleHighscore.MAX_X + this.curPos; i++) {
				int y = 65;
				int addY = 135;
				if (i - this.curPos <= 4) {
					addY = 0;
				}
				g.setColor(Color.BLACK);
				g.setFont(ApoSimpleGame.FONT_RESTART);
				Font f = ApoSimpleGame.FONT_RESTART;
				String s = String.valueOf(i + 1);
				g.drawString(s, 30, y + (i - this.curPos) * 25 + addY);
				
				s = String.valueOf(this.help.get(i).getPoints());
				int w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, 175 - w/2, y + (i - this.curPos) * 25 + addY);
				
				s = String.valueOf(this.help.get(i).getName());
				w = g.getFontMetrics().stringWidth(s);
				int size = f.getSize();
				while ((w > 240) && (size > 6)) {
					size -= 2;
					g.setFont(new Font(f.getName(), f.getStyle(), size));
					w = g.getFontMetrics().stringWidth(s);	
				}
				
				g.drawString(s, 375 - w/2, y + (i - this.curPos) * 25 + addY);
				
				g.setFont(ApoSimpleGame.FONT_RESTART);
				s = String.valueOf(this.help.get(i).getLevel());
				w = g.getFontMetrics().stringWidth(s);
				if (i - this.curPos <= 6) {
					g.drawString(s, 510, y + (i - this.curPos) * 25 + addY);
				} else {
					g.drawString(s, 460, y + (i - this.curPos) * 25 + addY);
				}
			}
			if (this.curChoose > 0) {
				g.setColor(Color.BLACK);
				g.setFont(ApoSimpleGame.FONT_BUTTON);
				String s = this.getGame().getButtons()[this.curChoose].getFunction();
				int w = g.getFontMetrics().stringWidth(s);
				int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
				g.drawString(s, this.getGame().getButtons()[this.curChoose].getXMiddle() - w/2, this.getGame().getButtons()[this.curChoose].getY() + this.getGame().getButtons()[this.curChoose].getHeight()/2 + h/2);
			}
		}
	}
	
	public void drawOverlay(Graphics2D g) {	
		super.getGame().renderCoins(g, 0, 0);
	}
}