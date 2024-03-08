package apoSkunkman;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ApoSkunkmanImageContainerAscii {
	
	private static final Font FONT = new Font(Font.MONOSPACED, Font.PLAIN, 25);
	private static final Font FONT_BUTTON = new Font(Font.MONOSPACED, Font.BOLD, 20);
	private static final Font FONT_LITTLE = new Font(Font.MONOSPACED, Font.PLAIN, 18);
	
	public ApoSkunkmanImageContainerAscii() {
	}
	
	public BufferedImage getTileAscii() {
		BufferedImage tile = new BufferedImage(32 * 3, 32, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = tile.createGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, tile.getWidth(), tile.getHeight());		
		
		g.setColor(Color.BLACK);

		g.setFont(ApoSkunkmanImageContainerAscii.FONT);
		String s = "#";
		int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
		int oneWidth = tile.getWidth()/3;
		ApoSkunkmanConstants.drawString(g, s, 2 * oneWidth + oneWidth/2, tile.getHeight()/2 + h/2, true, Color.WHITE, Color.BLACK);
		s = "X";
		ApoSkunkmanConstants.drawString(g, s, 1 * oneWidth + oneWidth/2, tile.getHeight()/2 + h/2, true, Color.WHITE, Color.BLACK);

		
		g.dispose();
		return tile;
	}
	
	public BufferedImage getGoodieAscii() {
		BufferedImage tile = new BufferedImage(32 * 16, 32, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = tile.createGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, tile.getWidth(), tile.getHeight());
		
		g.setFont(ApoSkunkmanImageContainerAscii.FONT_LITTLE);
		int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
		int oneWidth = tile.getWidth()/16;

		String s = "w+";
		ApoSkunkmanConstants.drawString(g, s, 0 * oneWidth + oneWidth/2, tile.getHeight()/2 + h/2, true, Color.WHITE, Color.BLACK);
		ApoSkunkmanConstants.drawString(g, s, 1 * oneWidth + oneWidth/2, tile.getHeight()/2 + h/2, true, Color.WHITE, Color.BLACK);
		
		s = "b+";
		ApoSkunkmanConstants.drawString(g, s, 2 * oneWidth + oneWidth/2, tile.getHeight()/2 + h/2, true, Color.WHITE, Color.BLACK);
		ApoSkunkmanConstants.drawString(g, s, 3 * oneWidth + oneWidth/2, tile.getHeight()/2 + h/2, true, Color.WHITE, Color.BLACK);

		s = "s+";
		ApoSkunkmanConstants.drawString(g, s, 4 * oneWidth + oneWidth/2, tile.getHeight()/2 + h/2, true, Color.WHITE, Color.BLACK);
		ApoSkunkmanConstants.drawString(g, s, 5 * oneWidth + oneWidth/2, tile.getHeight()/2 + h/2, true, Color.WHITE, Color.BLACK);

		s = "++";
		ApoSkunkmanConstants.drawString(g, s, 6 * oneWidth + oneWidth/2, tile.getHeight()/2 + h/2, true, Color.WHITE, Color.BLACK);
		ApoSkunkmanConstants.drawString(g, s, 7 * oneWidth + oneWidth/2, tile.getHeight()/2 + h/2, true, Color.WHITE, Color.BLACK);

		s = "w-";
		ApoSkunkmanConstants.drawString(g, s, 8 * oneWidth + oneWidth/2, tile.getHeight()/2 + h/2, true, Color.WHITE, Color.BLACK);
		ApoSkunkmanConstants.drawString(g, s, 9 * oneWidth + oneWidth/2, tile.getHeight()/2 + h/2, true, Color.WHITE, Color.BLACK);
		
		s = "b-";
		ApoSkunkmanConstants.drawString(g, s, 10 * oneWidth + oneWidth/2, tile.getHeight()/2 + h/2, true, Color.WHITE, Color.BLACK);
		ApoSkunkmanConstants.drawString(g, s, 11 * oneWidth + oneWidth/2, tile.getHeight()/2 + h/2, true, Color.WHITE, Color.BLACK);

		s = "s-";
		ApoSkunkmanConstants.drawString(g, s, 12 * oneWidth + oneWidth/2, tile.getHeight()/2 + h/2, true, Color.WHITE, Color.BLACK);
		ApoSkunkmanConstants.drawString(g, s, 13 * oneWidth + oneWidth/2, tile.getHeight()/2 + h/2, true, Color.WHITE, Color.BLACK);

		s = "--";
		ApoSkunkmanConstants.drawString(g, s, 14 * oneWidth + oneWidth/2, tile.getHeight()/2 + h/2, true, Color.WHITE, Color.BLACK);
		ApoSkunkmanConstants.drawString(g, s, 15 * oneWidth + oneWidth/2, tile.getHeight()/2 + h/2, true, Color.WHITE, Color.BLACK);
	
		g.dispose();
		return tile;
	}
	
	public BufferedImage getFlameAscii() {
		BufferedImage tile = new BufferedImage(32 * 7, 32, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = tile.createGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, tile.getWidth(), tile.getHeight());
		
		g.setFont(ApoSkunkmanImageContainerAscii.FONT);
		String s = "~";
		int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
		int oneWidth = tile.getWidth()/7;
		for (int i = 0; i < 7; i++) {
			ApoSkunkmanConstants.drawString(g, s, i * oneWidth + oneWidth/2, tile.getHeight()/2 + h/2, true, Color.WHITE, Color.BLACK);
		}
		
		g.dispose();
		return tile;
	}
	
	public BufferedImage getSkunkmanAscii() {
		BufferedImage tile = new BufferedImage(32 * 3, 32, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = tile.createGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, tile.getWidth(), tile.getHeight());
		
		g.setFont(ApoSkunkmanImageContainerAscii.FONT);
		String s = "@";
		int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
		int oneWidth = tile.getWidth()/3;
		for (int i = 0; i < 3; i++) {
			ApoSkunkmanConstants.drawString(g, s, i * oneWidth + oneWidth/2, tile.getHeight()/2 + h/2, true, Color.WHITE, Color.BLACK);
		}
		
		g.dispose();
		return tile;
	}
	
	public BufferedImage getHudAscii() {
		BufferedImage tile = new BufferedImage(288, 600, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = tile.createGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, tile.getWidth(), tile.getHeight());
		
		g.setFont(ApoSkunkmanImageContainerAscii.FONT);
		String s = "=== ApoSkunkman ===";
		ApoSkunkmanConstants.drawString(g, s, tile.getWidth()/2, 40, true, Color.WHITE, Color.BLACK);
		
		s = "The one and only";
		ApoSkunkmanConstants.drawString(g, s, tile.getWidth()/2, 70, true, Color.WHITE, Color.BLACK);
		
		s = "ascii-version";
		ApoSkunkmanConstants.drawString(g, s, tile.getWidth()/2, 90, true, Color.WHITE, Color.BLACK);
		
		g.setColor(Color.WHITE);
		g.fillRect(5, 190, tile.getWidth() - 10, 365);
		
		g.dispose();
		return tile;
	}
	
	public BufferedImage getHudLittleTreeAscii() {
		BufferedImage tile = new BufferedImage(65, 35, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = tile.createGraphics();
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, tile.getWidth(), tile.getHeight());
		g.setColor(Color.WHITE);
		g.drawRect(0, 0, tile.getWidth() - 1, tile.getHeight() - 1);
		
		g.dispose();
		return tile;
	}
	
	public BufferedImage getButtonWithLittleTree(String text) {
		return this.getButtonWithLittleTree(text, this.getHudLittleTreeAscii());	
	}
	
	public BufferedImage getButtonWithBigTree(String text) {
		return this.getButtonWithLittleTree(text, this.getHudTreeAscii());	
	}
	
	private BufferedImage getButtonWithLittleTree(final String text, final BufferedImage iLittle) {
		BufferedImage iButton = new BufferedImage(iLittle.getWidth() * 3, iLittle.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = iButton.createGraphics();
		
		g.setFont(ApoSkunkmanImageContainerAscii.FONT_BUTTON);
		String s = text;
		int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
		int oneWidth = iLittle.getWidth();
		
		for (int i = 0; i < 3; i++) {
			g.drawImage(iLittle, oneWidth * i, 0, null);
			
			Color c = Color.BLACK;
			if (i == 1) {
				c = Color.YELLOW;
			} else if (i == 2) {
				c = Color.RED;
			}
			ApoSkunkmanConstants.drawString(g, s, oneWidth * i + oneWidth/2, iButton.getHeight()/2 + h/2, true, c, Color.WHITE);
		}
		
		g.dispose();
		return iButton;
	}
	
	public BufferedImage getHudTreeAscii() {
		BufferedImage tile = new BufferedImage(93, 27, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = tile.createGraphics();
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, tile.getWidth(), tile.getHeight());
		g.setColor(Color.WHITE);
		g.drawRect(0, 0, tile.getWidth() - 1, tile.getHeight() - 1);
		
		g.dispose();
		return tile;
	}
	
	public BufferedImage getHudTimeAscii() {
		BufferedImage tile = new BufferedImage(67, 40, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = tile.createGraphics();
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, tile.getWidth(), tile.getHeight());
		g.setColor(Color.WHITE);
		g.drawRect(0, 0, tile.getWidth() - 1, tile.getHeight() - 1);
		
		g.dispose();
		return tile;
	}
	
	public BufferedImage getPlayerAscii(final int player) {
		BufferedImage tile = new BufferedImage(128, 192, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = tile.createGraphics();
		
		g.setFont(ApoSkunkmanImageContainerAscii.FONT);
		String s = String.valueOf(player);
		int oneWidth = tile.getWidth()/4;
		int oneHeight = tile.getHeight()/4;
		g.setColor(Color.WHITE);
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				ApoSkunkmanConstants.drawString(g, s, oneWidth * i + oneWidth/2, oneHeight * j + oneHeight - 2, true, Color.WHITE, Color.BLACK);
			}
		}
		
		g.dispose();
		return tile;
	}
	
}
