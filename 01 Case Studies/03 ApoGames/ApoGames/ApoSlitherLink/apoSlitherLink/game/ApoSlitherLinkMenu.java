package apoSlitherLink.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import apoSlitherLink.ApoSlitherLinkConstants;

public class ApoSlitherLinkMenu extends ApoSlitherLinkModel {
	
	private final Color COLOR_BACKGROUND = new Color(222, 242, 208);
	public static final Font FONT_LEVEL = new Font(Font.SANS_SERIF, Font.BOLD, 50);
	public static final Font FONT_LEVELS = new Font(Font.SANS_SERIF, Font.BOLD, 30);
	
	public static final String QUIT = "Quit";
	public static final String EASY = "level_easy";
	public static final String MEDIUM = "level_medium";
	public static final String HARD = "level_hard";
	public static final String CUSTOM = "level_custom";
	public static final String OPTIONS = "How to Play";
	public static final String EDITOR = "Editor";
	
	private BufferedImage iBackground;
	
	private BufferedImage iMenu;
	
	public ApoSlitherLinkMenu(ApoSlitherLinkPanel game) {
		super(game);
		
		this.init();
	}
	
	public void init() {
		if (this.iBackground == null) {
			this.iBackground = new BufferedImage(ApoSlitherLinkConstants.GAME_WIDTH, ApoSlitherLinkConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = (Graphics2D)(this.iBackground.getGraphics());
			
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			this.getGame().renderBackground(g);
			g.setColor(this.COLOR_BACKGROUND);
			int x = 5;
			int y = 80;
			int width = ApoSlitherLinkConstants.GAME_WIDTH - 10;
			int height = 260;
			g.fillRoundRect(x, y, width, height, 50, 50);
			g.setColor(ApoSlitherLinkPanel.COLOR_ABOVE_FIRST);
			Stroke stroke = g.getStroke();
			g.setStroke(new BasicStroke(10));
			g.drawRoundRect(x + 4, y + 4, width - 2 * 4, height - 2 * 4, 50, 50);
			g.setStroke(stroke);
			
			g.setFont(ApoSlitherLinkMenu.FONT_LEVEL);
			String s = "Levels";
			int w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, ApoSlitherLinkConstants.GAME_WIDTH/2 - w/2, this.getGame().getButtons()[1].getY() - 20);
			
		    
		    g.setColor(ApoSlitherLinkPanel.COLOR_ABOVE_FIRST);
		    g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		    s = "ApoSlitherLink";
		    w = g.getFontMetrics().stringWidth(s);
		    g.drawString(s, ApoSlitherLinkConstants.GAME_WIDTH/2 - w/2, 33);
		    s = "Made by Dirk Aporius 2011";
		    g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
		    w = g.getFontMetrics().stringWidth(s);
		    int h = g.getFontMetrics().getHeight();
		    g.drawString(s, this.iBackground.getWidth()/2 - w/2, 33 + h);
			
			g.dispose();
		}
		if (this.iMenu == null) {
			int x = 5;
			int y = 80;
			int width = ApoSlitherLinkConstants.GAME_WIDTH - 10;
			int height = 260;
			super.setRec(new Rectangle2D.Double(x, y, width, height));
			this.iMenu = new BufferedImage(width + 1, height + 1, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = (Graphics2D)(this.iMenu.getGraphics());
			
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			this.getGame().renderBackground(g, -x, -y);
			g.setColor(this.COLOR_BACKGROUND);
			g.fillRoundRect(0, 0, width, height, 50, 50);
			g.setColor(ApoSlitherLinkPanel.COLOR_ABOVE_FIRST);
			Stroke stroke = g.getStroke();
			g.setStroke(new BasicStroke(10));
			g.drawRoundRect(0 + 4, 0 + 4, width - 2 * 4, height - 2 * 4, 50, 50);
			g.setStroke(stroke);
			
			g.setFont(ApoSlitherLinkMenu.FONT_LEVEL);
			String s = "Levels";
			int w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, this.iMenu.getWidth()/2 - w/2, this.getGame().getButtons()[1].getY() - 20 - y);

			g.dispose();
		}
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoSlitherLinkMenu.QUIT)) {
			this.getGame().saveLevels();
			System.exit(0);
		} else if (function.equals(ApoSlitherLinkMenu.EASY)) {
			this.getGame().startEasyLevel();
		} else if (function.equals(ApoSlitherLinkMenu.MEDIUM)) {
			this.getGame().startMediumLevel();
		} else if (function.equals(ApoSlitherLinkMenu.HARD)) {
			this.getGame().startHardLevel();
		} else if (function.equals(ApoSlitherLinkMenu.CUSTOM)) {
			if (this.getGame().getLevel().getMaxCustomLevel() > 0) {
				this.getGame().startCustomLevel();
			}
		} else if (function.equals(ApoSlitherLinkMenu.OPTIONS)) {
			this.getGame().setOptions();
		} else if (function.equals(ApoSlitherLinkMenu.EDITOR)) {
			this.getGame().setEditor(false);
		}
	}
	
	@Override
	public void mouseButtonReleased(int x, int y, boolean bRight) {
	}

	@Override
	public void think(int delta) {
		super.think(delta);
	}
	
	@Override
	public void render(Graphics2D g) {
		g.drawImage(this.iBackground, 0, 0, null);
		
		super.getGame().renderClouds(g);
		
		if (super.isBOver()) {
			g.drawImage(this.iMenu, (int)(super.getRec().getX()), (int)(super.getRec().getY()), null);
		}
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(ApoSlitherLinkPanel.COLOR_ABOVE_FIRST);
		g.setFont(ApoSlitherLinkMenu.FONT_LEVELS);
		String s = this.getGame().getLevel().getSolvedEasyLevel() + " / " + this.getGame().getLevel().getMaxEasyLevel();
		int w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, this.getGame().getButtons()[1].getX() + this.getGame().getButtons()[1].getWidth()/2 - w/2, this.getGame().getButtons()[1].getY() + this.getGame().getButtons()[1].getHeight() + 50);
		
		s = this.getGame().getLevel().getSolvedMediumLevel() + " / " + this.getGame().getLevel().getMaxMediumLevel();
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, this.getGame().getButtons()[2].getX() + this.getGame().getButtons()[2].getWidth()/2 - w/2, this.getGame().getButtons()[2].getY() + this.getGame().getButtons()[2].getHeight() + 50);
		
		s = this.getGame().getLevel().getSolvedHardLevel() + " / " + this.getGame().getLevel().getMaxHardLevel();
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, this.getGame().getButtons()[3].getX() + this.getGame().getButtons()[3].getWidth()/2 - w/2, this.getGame().getButtons()[3].getY() + this.getGame().getButtons()[3].getHeight() + 50);
		
		s = this.getGame().getLevel().getSolvedCustomLevel() + " / " + this.getGame().getLevel().getMaxCustomLevel();
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, this.getGame().getButtons()[24].getX() + this.getGame().getButtons()[24].getWidth()/2 - w/2, this.getGame().getButtons()[24].getY() + this.getGame().getButtons()[24].getHeight() + 50);
	}

}
