package apoImp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import org.apogames.image.ApoImageFromValue;

public class ApoImpImages {

	/** HilfsObjekt, um vorgefertigte Bilder zu laden (z.B. für Buttons) */
	private ApoImageFromValue image;
	
	private static ApoImpImages INSTANCE;
	
	public static ApoImpImages getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ApoImpImages();
		}
		return INSTANCE;
	}
	
	public ApoImpImages() {
		this.image = new ApoImageFromValue();
	}
	
	/**
	 * Methode um ein Bild aus der Jar oder vom FIlesystem zu laden
	 * @param path : Pfad zum Bild
	 * @param bFile : TRUE, Bild von der Festplatte laden, FALSE aus der JAR
	 * @return hoffentlich das Bild
	 */
	public BufferedImage getImage(final String path, final boolean bFile) {
		return this.image.getImageFromPath(path, bFile, true);
	}
	
	/**
	 * Methode um ein Bild aus der Jar oder vom FIlesystem zu laden
	 * @param path : Pfad zum Bild
	 * @param bFile : TRUE, Bild von der Festplatte laden, FALSE aus der JAR
	 * @param bWithAlpha : TRUE Bild mit Alphawerten, FALSE Bild nur RGB
	 * @return hoffentlich das Bild
	 */
	public BufferedImage getImage(final String path, final boolean bFile, final boolean bWithAlpha) {
		return this.image.getImageFromPath(path, bFile, bWithAlpha);
	}
	
	/**
	 * gibt das Logo für das Spiel zurück
	 * @param width : Breite des Logos
	 * @param height : Höhe des Logos
	 * @param iLogoOrg : Bild des Logos
	 * @return Logo für das Spiel
	 */
	public BufferedImage getLogo(BufferedImage iLogoOrg) {
		BufferedImage iLogo = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(iLogoOrg.getWidth(), iLogoOrg.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g	= (Graphics2D)iLogo.getGraphics();

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.drawImage(iLogoOrg, 0, 0, null);
		
		g.dispose();
		
		return iLogo;
	}
	
	public BufferedImage getButtonImage(int width, int height, String text, int round) {
		return this.image.getButtonImage(width, height, text, round);
	}
	
	public BufferedImage getButtonImage(int width, int height, String text, Color c, Color c2, Color c3, int round) {
		return this.image.getButtonImage(width, height, text, c, c2, c3, round);
	}
	
	public BufferedImage getButtonImage(int width, int height, String text, Color c, Color c2, Color c3, Font font, int round) {
		return this.image.getButtonImage(width, height, text, c, c2, c3, new Color(254, 254, 0, 140), new Color(254, 0, 0, 140), font, round);
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
