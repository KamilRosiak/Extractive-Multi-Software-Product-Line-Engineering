package apoSimpleSudoku.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.KeyEvent;

import apoSimpleSudoku.ApoSimpleSudokuConstants;
import apoSimpleSudoku.level.ApoSimpleSudokuLevel;

public class ApoSimpleSudokuCredits extends ApoSimpleSudokuModel {

	/** eindeutige Stringvariable für den QuitButton */
	public static String MENU = "menu";
	
	public ApoSimpleSudokuCredits(ApoSimpleSudokuPanel game) {
		super(game);
	}
	
	public void init() {
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
			String text = "Credits";
			this.drawInBubbles(g, text, ApoSimpleSudokuConstants.GAME_HEIGHT/2 - change, 5, 80, ApoSimpleSudokuConstants.BROWN, Color.WHITE, Color.BLACK);
			
			
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
			text = "Basic game idea by";
			this.drawInBubbles(g, text, 35, 2, 50, Color.WHITE, Color.BLACK, new Color(0, 0, 0, 50));
			text = "Grabarchuk family";
			this.drawInBubbles(g, text, 90, 2, 50, Color.WHITE, Color.BLACK, new Color(0, 0, 0, 50));
			
			text = "Programmed by";
			this.drawInBubbles(g, text, 295, 2, 40, Color.WHITE, Color.BLACK, new Color(0, 0, 0, 50));
			text = "Dirk Aporius";
			this.drawInBubbles(g, text, 335, 2, 40, Color.WHITE, Color.BLACK, new Color(0, 0, 0, 50));
			text = "www.apo-games.de";
			this.drawInBubbles(g, text, 385, 2, 40, Color.WHITE, Color.BLACK, new Color(0, 0, 0, 50));

			g.setStroke(stroke);
			
			g.dispose();
		}
	}
	
	private void drawInBubbles(Graphics2D g, String text, int y, int lineWidth, int height, Color c, Color textColor, Color border) {
		int width = ApoSimpleSudokuConstants.GAME_WIDTH - 12;
		int h = g.getFontMetrics().getHeight();
		int circleRadius = h + 2;
		Stroke basicStroke = new BasicStroke(3);
		int[] nextYSet = new int[text.length()];
		for (int i = 0; i < nextYSet.length; i++) {
			nextYSet[i] = (int)(Math.random() * (height - circleRadius - 4)) + 2 + 10;
		}
		int startX = (int)(width/2 - (text.length()/2f)*(circleRadius) - ((text.length()-1)/2f)*(lineWidth)) + 5;
		int nextWidth = circleRadius + lineWidth;
		for (int t = 0; t < text.length(); t++) {
			String s = String.valueOf(text.charAt(t));
			int w = g.getFontMetrics().stringWidth(s);
			g.setStroke(basicStroke);
			g.setColor(c);
			g.fillOval(startX + 1, y + nextYSet[t], circleRadius - 1, circleRadius - 1);
			g.setColor(border);
			g.drawOval(startX + 1, y + nextYSet[t], circleRadius - 1, circleRadius - 1);
			if (t != text.length() - 1) {
				g.drawLine(startX + circleRadius + 1, y + nextYSet[t] + circleRadius/2 - 1, startX + circleRadius + 1 + lineWidth, y + nextYSet[t + 1] + circleRadius/2 - 1);
			}
			int realHeight = h - 2*g.getFontMetrics().getDescent();
			g.setColor(textColor);
			g.drawString(s, startX + circleRadius/2 - w/2, y + nextYSet[t] + circleRadius/2 + realHeight/2);
			startX += nextWidth;
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
	}

	@Override
	public void render(Graphics2D g) {
		if (super.getBackground() != null) {
			g.drawImage(super.getBackground(), 0, 0, null);
		}
	}

}
