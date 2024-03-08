package apoRelax.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import apoRelax.ApoRelaxConstants;

public class ApoRelaxOptions extends ApoRelaxState {

	public static final String MENU_STRING = "menu";
	public static final String MENU = "options_menu";
	public static final String MUSIC = "options_music";
	public static final String SOUND = "options_sound";

	private BufferedImage iBackground;
	
	public ApoRelaxOptions(ApoRelaxPanel game) {
		super(game);
		
		this.init();
	}
	
	@Override
	public void init() {
		if (this.iBackground == null) {
			this.iBackground = new BufferedImage(ApoRelaxConstants.GAME_WIDTH, ApoRelaxConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
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
			String s = "ApoRelax - Options";
			int w = g.getFontMetrics().stringWidth(s);
			int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
			g.drawString(s, this.iBackground.getWidth()/2 - w/2, this.iBackground.getHeight()/2 - 5 + h/2);
			
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
			h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
			s = "Sounds";
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, this.iBackground.getWidth()/2 - w/2, 40);

			g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
			s = "Music";
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, 40, 100);
			
			s = "Effects";
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, 40, 145);
			
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
		if (function.equals(ApoRelaxOptions.MENU)) {
			this.getGame().setMenu();
		} else if (function.equals(ApoRelaxOptions.MUSIC)) {
			ApoRelaxOptionsButton button = (ApoRelaxOptionsButton)(this.getGame().getButtons()[9]);
			button.setBActive(!button.isBActive());
			if (!button.isBActive()) {
				this.getGame().stopSound(ApoRelaxPanel.SOUND_BACKGROUND);
			} else {
				this.getGame().playSound(ApoRelaxPanel.SOUND_BACKGROUND, true, 90);				
			}
		} else if (function.equals(ApoRelaxOptions.SOUND)) {
			ApoRelaxOptionsButton button = (ApoRelaxOptionsButton)(this.getGame().getButtons()[10]);
			button.setBActive(!button.isBActive());
			this.getGame().setSoundEffects(button.isBActive());
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
