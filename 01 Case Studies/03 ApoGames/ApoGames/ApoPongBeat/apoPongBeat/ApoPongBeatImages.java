package apoPongBeat;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import org.apogames.image.ApoImageFromValue;

public class ApoPongBeatImages {

	/** HilfsObjekt, um vorgefertigte Bilder zu laden (z.B. für Buttons) */
	private ApoImageFromValue image;
	
	public ApoPongBeatImages() {
		this.image = new ApoImageFromValue();
	}
	
	/**
	 * Methode um ein Bild aus der Jar oder vom FIlesystem zu laden
	 * @param path : Pfad zum Bild
	 * @param bFile : TRUE, Bild von der Festplatte laden, FALSE aus der JAR
	 * @return hoffentlich das Bild
	 */
	public BufferedImage getImage(String path, boolean bFile) {
		return this.image.getImageFromPath(path, bFile);
	}
	
	/**
	 * gibt das Logo für das Spiel zurück
	 * @param width : Breite des Logos
	 * @param height : Höhe des Logos
	 * @param iPlayer : Bild des Spielers
	 * @return Logo für das Spiel
	 */
	public BufferedImage getLogo(BufferedImage iPlayer) {
		BufferedImage iLogo = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(iPlayer.getWidth(), iPlayer.getHeight(), BufferedImage.TRANSLUCENT);
		Graphics2D g	= (Graphics2D)iLogo.getGraphics();

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.drawImage(iPlayer, 0, 0, null);
		
		g.dispose();
		
		return iLogo;
	}
	
	public BufferedImage getImageMouseOver(BufferedImage iOriginal) {
		return this.getImageMouseOver(iOriginal, 0x00000000, new Color(255, 0, 0, 230), 2);
	}
	
	public BufferedImage getImageMouseOver(BufferedImage iOriginal, int width) {
		return this.getImageMouseOver(iOriginal, 0x00000000, new Color(255, 0, 0, 230), width);
	}
	
	public BufferedImage getImageMouseOver(BufferedImage iOriginal, int searchColor, Color drawColor, int w) {
		int width = iOriginal.getWidth();
		int height = iOriginal.getHeight();
		BufferedImage iSelect = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( width, height, BufferedImage.TRANSLUCENT );
		Graphics2D g	= (Graphics2D)iSelect.getGraphics();

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.drawImage(iOriginal, 0, 0, null);
		
		g.setColor(drawColor);
		int color = searchColor;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (x + 1 < iOriginal.getWidth()) {
					if ((iOriginal.getRGB(x + 1, y) != color) &&
						(iOriginal.getRGB(x, y) == color)) {
						g.fillRect(x - w/2, y, w, 1);
					}
				}
				if ((x - 1 >= 0)) {
					if ((iOriginal.getRGB(x, y) == color) &&
						(iOriginal.getRGB(x - 1, y) != color)) {
						g.fillRect(x, y, w, 1);
					}
				} 
				if ((y - 1 >= 0)) {
					if ((iOriginal.getRGB(x, y) == color) &&
						(iOriginal.getRGB(x, y - 1) != color)) {
						g.fillRect(x, y, 1, w);
					}
				}
				if (y + 1 < height) {
					if ((iOriginal.getRGB(x, y + 1) != color) &&
						(iOriginal.getRGB(x, y) == color)) {
						g.fillRect(x, y - w/2, 1, w);
					}
				}
			}
		}
				
		g.dispose();
		
		return iSelect;
	}
	
	public BufferedImage getButtonImage(int width, int height, String text, int round) {
		return this.image.getButtonImage(width, height, text, round);
	}
	
	public BufferedImage getButtonImage(int width, int height, String text, Color c, Color c2, Color c3, int round) {
		return this.image.getButtonImage(width, height, text, c, c2, c3, round);
	}
	
	public BufferedImage getButtonImage(int width, int height, String text, Color c, Color c2, Color c3, Font font, int round) {
		return this.image.getButtonImage(width, height, text, c, c2, c3, new Color(255, 255, 0), new Color(255, 0, 0), font, round);
	}
	
	public BufferedImage getButtonImage(int width, int height, String text, Color c, Color c2, Color c3, Color mouseOver, Color mousePressed, Font font, int round) {
		return this.image.getButtonImage(width, height, text, c, c2, c3, mouseOver, mousePressed, font, round);
	}

	public BufferedImage getButtonWithImage(int width, int height, BufferedImage iPic, String text, Color textColor, Color mousePressed, Color mouseReleased) {
		return this.getButtonWithImage(width, height, iPic, text, textColor, mousePressed, mouseReleased, new Font("Dialog", Font.BOLD, 13));
	}
	
	public BufferedImage getButtonWithImage(int width, int height, BufferedImage iPic, String text, Color textColor, Color mousePressed, Color mouseReleased, Font font) {
		BufferedImage iButton = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(width,height,BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = (Graphics2D)iButton.getGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		int oneWidth = width/3;
		int widthPic = 0;
		if (iPic != null) {
			widthPic = iPic.getWidth();
		}
		g.setFont(font);
		int fontHeight = g.getFontMetrics().getHeight();
		
		for ( int i = 0; i < 3; i++ ) {
			if (iPic != null) {
				g.drawImage(iPic, oneWidth * i + oneWidth/2 - widthPic/2, height/2 - iPic.getHeight()/2, null);
			}
			
			int w = g.getFontMetrics().stringWidth(text);
			if (i > 0) {
				if (i == 1) {
					g.setColor(mousePressed);
				} else {
					g.setColor(mouseReleased);
				}
				g.fillRoundRect(oneWidth * i + oneWidth/2 - w/2 - 2, height/2 - fontHeight/2, w + 4, fontHeight - 10, 10, 10);
			}
			g.setColor(textColor);
			g.drawString(text, i * oneWidth + oneWidth/2 - w/2, height/2 + 5);
		}
		
		g.dispose();
		return iButton;
	}
	
}
