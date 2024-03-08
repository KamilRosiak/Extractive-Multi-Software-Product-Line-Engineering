package apoSlitherLink.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.apogames.entity.ApoButton;

import apoSlitherLink.ApoSlitherLinkConstants;
import apoSlitherLink.level.ApoSlitherLinkLevelHelp;

public class ApoSlitherLinkLevelChooser extends ApoSlitherLinkModel {

	private final Font FONT = new Font(Font.SANS_SERIF, Font.BOLD, 20);
	
	private ArrayList<ApoButton> buttons;
	
	private BufferedImage iBackground;
	
	public static final String LEFT_STRING = "<";
	public static final String LEFT = "game_left";
	public static final String RIGHT_STRING = ">";
	public static final String RIGHT = "game_right";
	public static final String MENU_STRING = "Menu";
	public static final String MENU = "game_menu";
	
	private int curLevel;
	
	public ApoSlitherLinkLevelChooser(ApoSlitherLinkPanel game) {
		super(game);
	}
	
	public void init() {
		if (this.iBackground == null) {
			this.iBackground = new BufferedImage(ApoSlitherLinkConstants.GAME_WIDTH, ApoSlitherLinkConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = (Graphics2D)(this.iBackground.getGraphics());
			
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			this.getGame().renderBackground(g);
			g.setColor(ApoSlitherLinkLevelHelp.COLOR_BACKGROUND);
			g.fillRoundRect(5, 80, ApoSlitherLinkConstants.GAME_WIDTH - 10, 336, 50, 50);
			g.setColor(ApoSlitherLinkPanel.COLOR_ABOVE_FIRST);
			Stroke stroke = g.getStroke();
			g.setStroke(new BasicStroke(10));
			g.drawRoundRect(9, 84, ApoSlitherLinkConstants.GAME_WIDTH - 18, 328, 50, 50);
			g.setStroke(stroke);
			
			g.setColor(ApoSlitherLinkPanel.COLOR_ABOVE_FIRST);
		    g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
		    String s = "LevelChooser";
		    int w = g.getFontMetrics().stringWidth(s);
		    g.drawString(s, ApoSlitherLinkConstants.GAME_WIDTH/2 - w/2, 44);
			
			g.dispose();
		}
		
		if (buttons == null) {
			this.buttons = new ArrayList<ApoButton>();
			
			Font font = new Font(Font.SANS_SERIF, Font.BOLD, 20);
			String text = "10";
			String function = "button";
			int width = 65;
			int height = 65;
			
			int x = 20;
			int y = 95;
			for (int i = 0; i < 32; i++) {
				this.buttons.add(new ApoButton(this.getGame().getImages().getButtonImage(width * 3, height, text, new Color(205, 245, 195), new Color(0, 102, 0, 0), new Color(0, 102, 0), font, 250), x, y, width, height, function));
				x += 75;
				if (x >= ApoSlitherLinkConstants.GAME_WIDTH - 20) {
					x = 20;
					y += 75;
				}
				this.buttons.get(this.buttons.size() - 1).setBVisible(false);
			}
		}
		this.curLevel = 0;
		int maxLevels = this.getGame().getLevel().getMaxLevel();
		if (maxLevels > this.buttons.size()) {
			while (maxLevels > this.buttons.size()) {
				maxLevels -= this.buttons.size();
			}
		} else {
			this.getGame().getButtons()[12].setBVisible(false);
			this.getGame().getButtons()[13].setBVisible(false);
		}
		for (int i = 0; i < this.buttons.size(); i++) {
			if (i < maxLevels) {
				this.buttons.get(i).setBVisible(true);
			} else {
				this.buttons.get(i).setBVisible(false);
			}
		}
	}

	@Override
	public void keyButtonReleased(int button, char character) {
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoSlitherLinkLevelChooser.MENU)) {
			this.getGame().setMenu();
		} else if (function.equals(ApoSlitherLinkLevelChooser.LEFT)) {
			this.curLevel -= this.buttons.size();
			if (this.curLevel < 0) {
				this.curLevel = 0;
			}
		} else if (function.equals(ApoSlitherLinkLevelChooser.RIGHT)) {
			int oldCurLevel = this.curLevel;
			this.curLevel += this.buttons.size();
			if (this.curLevel >= this.getGame().getLevel().getMaxLevel()) {
				this.curLevel = oldCurLevel;
			}
		}
	}

	@Override
	public void mouseButtonReleased(int x, int y, boolean right) {
		if (this.buttons != null) {
			for (int i = 0; i < this.buttons.size(); i++) {
				if (this.buttons.get(i).isBVisible()) {
					if (this.buttons.get(i).getReleased(x, y)) {
						this.getGame().startLevel(i + this.curLevel);
					}
				}
			}
		}
	}
	
	public void mousePressed(int x, int y) {
		if (this.buttons != null) {
			for (int i = 0; i < this.buttons.size(); i++) {
				if (this.buttons.get(i).isBVisible()) {
					if (this.buttons.get(i).getPressed(x, y)) {
						break;
					}
				}
			}
		}
	}
	
	public void mouseMoved(int x, int y) {
		if (this.buttons != null) {
			for (int i = 0; i < this.buttons.size(); i++) {
				if (this.buttons.get(i).isBVisible()) {
					if (this.buttons.get(i).getMove(x, y)) {
						break;
					}
				}
			}
		}
	}

	@Override
	public void think(int delta) {
	}
	
	@Override
	public void render(Graphics2D g) {
		g.drawImage(this.iBackground, 0, 0, null);
		
		for (int i = 0; i < this.buttons.size(); i++) {
			if (this.buttons.get(i).isBVisible()) {
				this.buttons.get(i).render(g, 0, 0);
				
				Color c = ApoSlitherLinkLevelHelp.COLOR_LINE;
				if (this.getGame().getLevel().isCurLevelSolved(i + this.curLevel)) {
					g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 80));
					int width = 40;
					int x = (int)(this.buttons.get(i).getX() + this.buttons.get(i).getWidth()/2 - width/2);
					int y = (int)(this.buttons.get(i).getY() + this.buttons.get(i).getHeight()/2 - width/2);
					g.fillOval(x, y, width, width);
				}
				
				g.setColor(c);
				g.setFont(this.FONT);
				String s = String.valueOf((i + this.curLevel + 1));
				int w = g.getFontMetrics().stringWidth(s);
				int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
				float x = this.buttons.get(i).getX() + this.buttons.get(i).getWidth()/2;
				float y = this.buttons.get(i).getY() + this.buttons.get(i).getHeight()/2;
				g.drawString(s, x - w/2, y + h /2);
			}
		}
	}
	
	public void renderAfter(Graphics2D g) {
		for (int i = 0; i < this.buttons.size(); i++) {
			if ((this.buttons.get(i).isBVisible()) && (this.buttons.get(i).isBOver())) {
				if (this.getGame().getLevel().isCurLevelSolved(i + this.curLevel)) {
					int width = 40;
					int x = (int)(this.buttons.get(i).getX() + this.buttons.get(i).getWidth()/2 - width/2);
					int y = (int)(this.buttons.get(i).getY() + this.buttons.get(i).getHeight()/2 - width/2);
					BufferedImage iSolved = this.getGame().getLevel().getCurLevelSolvedImage(i + this.curLevel);
					if (iSolved != null) {
						int imageX = x + width/2 - iSolved.getWidth()/2;
						if (imageX < 0) {
							imageX = 0;
						} else if (imageX + iSolved.getWidth() > ApoSlitherLinkConstants.GAME_WIDTH) {
							imageX = ApoSlitherLinkConstants.GAME_WIDTH - iSolved.getWidth();
						}
						int imageY = y + (int)this.buttons.get(i).getHeight()/2 + width/2;
						if (imageY + iSolved.getHeight() > ApoSlitherLinkConstants.GAME_HEIGHT) {
							imageY = (int)(this.buttons.get(i).getY() - iSolved.getHeight());
						}
						g.drawImage(iSolved, imageX, imageY, null);
					}
				}
			}
		}
	}

}
