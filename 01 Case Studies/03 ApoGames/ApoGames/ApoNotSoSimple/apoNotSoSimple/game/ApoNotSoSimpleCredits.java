package apoNotSoSimple.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import org.apogames.ApoConstants;

public class ApoNotSoSimpleCredits extends ApoNotSoSimpleModel {

	public static final String MENU_STRING = "menu";
	public static final String MENU = "credits_menu";

	private BufferedImage iBackground;
	
	public ApoNotSoSimpleCredits(ApoNotSoSimplePanel game) {
		super(game);
		
		this.init();
	}
	
	@Override
	public void init() {
		if (this.iBackground == null) {
			this.iBackground = new BufferedImage(ApoConstants.GAME_WIDTH, ApoConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = this.iBackground.createGraphics();
			
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			this.getGame().renderBackground(g);
			
			Stroke stroke = g.getStroke();
			g.setStroke(new BasicStroke(5));
			
			g.setColor(Color.GRAY);
			g.drawRoundRect(5, 5, this.iBackground.getWidth() - 12, this.iBackground.getHeight() - 12, 20, 20);
			
			int change = 50;
			g.drawLine(7, this.iBackground.getHeight()/2 - change, this.iBackground.getWidth() - 2 * 5, this.iBackground.getHeight()/2 - change);
			g.drawLine(7, this.iBackground.getHeight()/2 + change, this.iBackground.getWidth() - 2 * 5, this.iBackground.getHeight()/2 + change);
			
			g.setColor(Color.BLACK);
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 45));
			String s = "ApoNotSoSimple - Credits";
			int w = g.getFontMetrics().stringWidth(s);
			int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
			g.drawString(s, this.iBackground.getWidth()/2 - w/2, this.iBackground.getHeight()/2 - 5 + h/2);
			
			g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
			h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
			s = "Basic Game idea by Wanderlands";
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, this.iBackground.getWidth()/2 - w/2, h/2 + 70);
			
			s = "http://wanderlands.org/main/";
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, this.iBackground.getWidth()/2 - w/2, h/2 + 90 + h);
			
			s = "Everything else made by";
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, this.iBackground.getWidth()/2 - w/2, this.iBackground.getHeight()/2 + change + 70 + 5 + h - h - 5);
			
			s = "Dirk Aporius";
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, this.iBackground.getWidth()/2 - w/2, this.iBackground.getHeight()/2 + change + 80 + 5 + h);
			
			s = "www.apo-games.de";
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, this.iBackground.getWidth()/2 - w/2, this.iBackground.getHeight()/2 + change + 90 + 10 + 2 * h);
			
			g.setStroke(stroke);
			
			g.dispose();
		}
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			this.getGame().setMenu();
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoNotSoSimpleCredits.MENU)) {
			this.getGame().setMenu();
		}
	}
	
	@Override
	public void mouseButtonReleased(int x, int y, boolean bRight) {
	}

	@Override
	public void think(int delta) {
		
	}
	
	@Override
	public void render(Graphics2D g) {
		if (this.iBackground != null) {
			g.drawImage(this.iBackground, 0, 0, null);
		}
	}

}
