package apoNotSoSimple.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.apogames.ApoConstants;
import org.apogames.entity.ApoButton;

public class ApoNotSoSimpleLevelChooser extends ApoNotSoSimpleModel {
	
	private final Font FONT = new Font(Font.SANS_SERIF, Font.BOLD, 20);
	
	public static final String MENU_STRING = "menu";
	public static final String MENU = "credits_menu";

	private BufferedImage iBackground;
	
	private ArrayList<ApoButton> buttons;
	
	private int maxLevel;
	
	public ApoNotSoSimpleLevelChooser(ApoNotSoSimplePanel game) {
		super(game);
		
		this.init();
	}
	
	@Override
	public void init() {
		if (this.buttons == null) {
			this.buttons = new ArrayList<ApoButton>();
			
			Font font = new Font(Font.SANS_SERIF, Font.BOLD, 20);
			String text = "10";
			String function = "button";
			int width = 55;
			int height = 55;
			
			int x = 20;
			int y = 20;
			for (int i = 0; i < 31; i++) {
				this.buttons.add(new ApoButton(this.getGame().getImages().getButtonImageLevelChooser(width * 3, height, text, Color.WHITE, new Color(0, 0, 0, 0), Color.BLACK, new Color(254, 254, 0, 140), new Color(254, 0, 0, 140), font, 250), x, y, width, height, function));
				x += 65;
				if (x + width >= ApoConstants.GAME_WIDTH - 20) {
					x = 20;
					y += 75;
					if ((y > 100) && (y < 200)) {
						y = 310;
					}
				}
				this.buttons.get(this.buttons.size() - 1).setBVisible(true);
			}
		}
		
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
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 37));
			String s = "ApoNotSoSimple - LevelChooser";
			int w = g.getFontMetrics().stringWidth(s);
			int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
			g.drawString(s, this.iBackground.getWidth()/2 - w/2, this.iBackground.getHeight()/2 - 5 + h/2);
			
			g.setStroke(stroke);
			
			for (int i = 0; i < this.buttons.size(); i++) {
				if (this.buttons.get(i).isBVisible()) {
					this.buttons.get(i).render(g, 0, 0);
					
					g.setColor(Color.LIGHT_GRAY);
					int width = 40;
					int x = (int)(this.buttons.get(i).getX() + this.buttons.get(i).getWidth()/2 - width/2);
					int y = (int)(this.buttons.get(i).getY() + this.buttons.get(i).getHeight()/2 - width/2);
					g.fillOval(x, y, width + 1, width + 1);
					
					g.setColor(Color.BLACK);
					g.setFont(this.FONT);
					s = String.valueOf((i + 1));
					w = g.getFontMetrics().stringWidth(s);
					h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
					x = (int)(this.buttons.get(i).getX() + this.buttons.get(i).getWidth()/2);
					y = (int)(this.buttons.get(i).getY() + this.buttons.get(i).getHeight()/2);
					g.drawString(s, x - w/2, y + h /2);
				}
			}
			
			g.dispose();
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
		if (function.equals(ApoNotSoSimpleLevelChooser.MENU)) {
			this.getGame().setMenu();
		}
	}
	
	@Override
	public void mouseButtonReleased(int x, int y, boolean right) {
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
	public void think(int delta) {
		
	}
	
	@Override
	public void render(Graphics2D g) {
		if (this.iBackground != null) {
			g.drawImage(this.iBackground, 0, 0, null);
		}
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		for (int i = 0; i < this.buttons.size() && i <= this.maxLevel; i++) {
			if (this.buttons.get(i).isBVisible()) {
				this.buttons.get(i).render(g, 0, 0);
				
				Color c = Color.GREEN.brighter().brighter().brighter();
				if ((i < this.maxLevel) || (i > this.maxLevel)) {
					if (i < this.maxLevel) {
						g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 80));
					} else {
						g.setColor(Color.LIGHT_GRAY);
					}
					int width = 40;
					int x = (int)(this.buttons.get(i).getX() + this.buttons.get(i).getWidth()/2 - width/2);
					int y = (int)(this.buttons.get(i).getY() + this.buttons.get(i).getHeight()/2 - width/2);
					g.fillOval(x, y, width + 1, width + 1);
				}
				
				g.setColor(Color.BLACK);
				g.setFont(this.FONT);
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
