package apoJump.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import apoJump.ApoJumpConstants;
import apoJump.ApoJumpImageContainer;

public class ApoJumpStateOptions extends ApoJumpState {

	public static final String BUTTON_BACK = "back";
	
	private BufferedImage iBackground;
	
	public ApoJumpStateOptions(ApoJumpPanel game) {
		super(game);
	}
	
	public void init() {
		this.getGame().setShouldRepaint(false);
		if (this.iBackground == null) {
			this.makeBackground();
		}
	}
	
	private void makeBackground() {
		this.iBackground = new BufferedImage(ApoJumpConstants.GAME_WIDTH, ApoJumpConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = this.iBackground.createGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		this.getGame().renderBackground(g);
		
		g.setColor(Color.BLACK);
		
		g.drawImage(ApoJumpImageContainer.iHighscoreBackground, 0, 75, null);

		g.setFont(ApoJumpConstants.FONT_ANALYSIS);
		String s = "Options? This game needs no options!";
		int w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, (int)(ApoJumpConstants.GAME_WIDTH/2 - w/2), (int)(ApoJumpConstants.GAME_HEIGHT/2 + 25));
		
		g.drawImage(this.getGame().getImages().getImage("images/options.png", false), 70, 0, null);

		g.dispose();
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			this.getGame().setMenu();
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoJumpStateOptions.BUTTON_BACK)) {
			this.getGame().setMenu();
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
	}

}
