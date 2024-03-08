package apoJump.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.apogames.help.ApoHelp;
import org.apogames.help.ApoHighscore;

import apoJump.ApoJumpConstants;
import apoJump.ApoJumpImageContainer;

public class ApoJumpStateHighscore extends ApoJumpState {

	public static final String BUTTON_BACK = "back";
	public static final String LEFT = "highscore_left";
	public static final String RIGHT = "highscore_right";

	public static final int MAX_X = 10;
	
	private ApoHighscore highscore;
	
	private BufferedImage iBackground;
	
	private ArrayList<ApoJumpStateHighscoreHelp> help;
	
	private int curPos;
	
	public ApoJumpStateHighscore(ApoJumpPanel game) {
		super(game);
	}
	
	public void init() {
		this.getGame().setShouldRepaint(false);
		if (this.help == null) {
			this.help = new ArrayList<ApoJumpStateHighscoreHelp>();
		}
		this.load(true);
		if (this.iBackground == null) {
			this.makeBackground();
		}
	}
	
	private void makeBackground() {
		this.iBackground = new BufferedImage(ApoJumpConstants.GAME_WIDTH, ApoJumpConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = this.iBackground.createGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		this.getGame().renderBackground(g);
		
		g.drawImage(ApoJumpImageContainer.iHighscoreBackground, 0, 70, null);
		
		g.drawImage(this.getGame().getImages().getImage("images/title_highscore.png", false), 70, 0, null);

		g.dispose();
	}
	
	public void loadHighscore(final boolean bFirst) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				ApoJumpStateHighscore.this.load(bFirst);
			}
 		});
 		t.start();
	}
	
	private void load(boolean bFirst) {
		try {
			this.highscore = ApoHighscore.getInstance();
			if (this.highscore.load()) {
				this.sortList(bFirst);
			}
		} catch (Exception ex) {
			this.help.clear();
			this.highscore = null;
		}
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
	
	public ArrayList<ApoJumpStateHighscoreHelp> getHelp() {
		return this.help;
	}

	private void sortList(boolean bFirst) {
		this.curPos = 0;
		this.help = new ArrayList<ApoJumpStateHighscoreHelp>();
		if (this.highscore.getPoints().size() > 0) {
			this.help.add(new ApoJumpStateHighscoreHelp(this.highscore.getPoints().get(0), this.highscore.getTime().get(0), this.highscore.getNames().get(0)));
			for (int i = 1; i < this.highscore.getPoints().size(); i++) {
				boolean bBreak = false;
				for (int j = 0; j < this.help.size(); j++) {
					if (this.help.get(j).getPoints() < this.highscore.getPoints().get(i)) {
						this.help.add(j, new ApoJumpStateHighscoreHelp(this.highscore.getPoints().get(i), this.highscore.getTime().get(i), this.highscore.getNames().get(i)));
						bBreak = true;
						break;
					}
				}
				if (!bBreak) {
					this.help.add(new ApoJumpStateHighscoreHelp(this.highscore.getPoints().get(i), this.highscore.getTime().get(i), this.highscore.getNames().get(i)));					
				}
			}
		}
		if (bFirst) {
			if (this.help.size() > ApoJumpStateHighscore.MAX_X) {
				this.getGame().getButtons()[11].setBVisible(false);
				this.getGame().getButtons()[12].setBVisible(true);
			} else {
				this.getGame().getButtons()[11].setBVisible(false);
				this.getGame().getButtons()[12].setBVisible(false);
			}
		}
	}
	
	private void nextHighscore(int plus) {
		int realPlus = plus * ApoJumpStateHighscore.MAX_X;
		this.curPos += realPlus;
		if (this.curPos >= this.help.size()) {
			this.curPos -= realPlus;
			this.getGame().getButtons()[12].setBVisible(false);
		} else if (this.curPos < 0) {
			this.curPos = 0;
			this.getGame().getButtons()[11].setBVisible(false);			
		}
		if (this.curPos + realPlus >= this.help.size()) {
			this.getGame().getButtons()[12].setBVisible(false);
		} else {
			this.getGame().getButtons()[12].setBVisible(true);			
		}
			
		if (this.curPos > 0) {
			this.getGame().getButtons()[11].setBVisible(true);
		} else {
			this.getGame().getButtons()[11].setBVisible(false);
		}
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			this.getGame().setMenu();
		} else if (button == KeyEvent.VK_LEFT) {
			this.nextHighscore(-1);
		} else if (button == KeyEvent.VK_RIGHT) {
			this.nextHighscore(+1);
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoJumpStateHighscore.BUTTON_BACK)) {
			this.getGame().setMenu();
		}else if (function.equals(ApoJumpStateHighscore.LEFT)) {
			this.nextHighscore(-1);
		} else if (function.equals(ApoJumpStateHighscore.RIGHT)) {
			this.nextHighscore(+1);
		}
	}

	@Override
	public void mouseButtonReleased(int x, int y) {
	}
	
	@Override
	public void think(int delta) {
	}

	@Override
	public void render(Graphics2D g) {
		if (this.iBackground != null) {
			g.drawImage(this.iBackground, 0, 0, null);
		}
		
		g.setFont(ApoJumpConstants.FONT_TEXTFIELD);
		g.setColor(Color.BLACK);
		
		final Font font = ApoJumpConstants.FONT_ANALYSIS;
		g.setFont(font);
		String s = String.valueOf(this.help.size()) + " scores online";
		int w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, (int)(ApoJumpConstants.GAME_WIDTH/2 - w/2), (int)(140));
		
		for (int i = this.curPos; i < this.help.size() && i < ApoJumpStateHighscore.MAX_X + this.curPos; i++) {
			int y = 180;
			int addY = 0;
			if (i - this.curPos <= 4) {
				addY = 0;
			}
			g.setColor(Color.BLACK);
			g.setFont(font);
			s = String.valueOf(i + 1);
			g.drawString(s, 80, y + (i - this.curPos) * 25 + addY);
			
			s = String.valueOf(this.help.get(i).getPoints());
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, 210 - w, y + (i - this.curPos) * 25 + addY);
			
			s = String.valueOf(this.help.get(i).getName());
			w = g.getFontMetrics().stringWidth(s);
			int size = font.getSize();
			while ((w > 200) && (size > 6)) {
				size -= 2;
				g.setFont(new Font(font.getName(), font.getStyle(), size));
				w = g.getFontMetrics().stringWidth(s);	
			}
			
			g.drawString(s, 240, y + (i - this.curPos) * 25 + addY);
			
			g.setFont(font);
			s = String.valueOf(ApoHelp.getTimeToDraw(this.help.get(i).getTime()));
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, 460, y + (i - this.curPos) * 25 + addY);
		}
	}

}