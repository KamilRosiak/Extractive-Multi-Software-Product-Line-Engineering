package apoSkunkman;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

import org.apogames.images.ApoImageFromValue;

/**
 * Hilfsklasse zum Laden von Bildern und zum Erstellen von Bildern mit vorgegebenen Werten
 * @author Dirk Aporius
 *
 */
public class ApoSkunkmanImages {

	/** HilfsObjekt, um vorgefertigte Bilder zu laden (z.B. für Buttons) */
	private ApoImageFromValue image;
	
	/**
	 * Konstruktor
	 */
	public ApoSkunkmanImages() {
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
	
	/**
	 * liefert ein dreigeteiltes Bild zurück mit übergebener Breite, Höhe und Text und mit der Hintergrundfarbe schwarz, mit der Farbe weiß für den Font und der Grenzfarbe grau
	 * @param width = Breite des Bildes
	 * @param height = Höhe des Bildes
	 * @param text = Text auf dem Button
	 * @return liefert ein dreigeteiltes Bild zurück mit übergebener Breite, Höhe und Text und mit der Hintergrundfarbe schwarz, mit der Farbe weiß für den Font und der Grenzfarbe grau
	 */
	public BufferedImage getButtonImage(int width, int height, String text, int round) {
		return this.image.getButtonImage(width, height, text, round);
	}
	
	/**
	 * liefert ein dreigeteiltes Bild zurück mit übergebener Breite, Höhe, Farben und Text zurück
	 * @param width = Breite des Bildes
	 * @param height = Höhe des Bildes
	 * @param text = Text auf dem Button
	 * @param background = Hintergrundfarbe
	 * @param font = Textfarbe
	 * @param border = Grenzfarbe
	 * @return liefert ein dreigeteiltes Bild zurück mit übergebener Breite, Höhe, Farben und Text zurück
	 */
	public BufferedImage getButtonImage(int width, int height, String text, Color c, Color c2, Color c3, int round) {
		return this.image.getButtonImage(width, height, text, c, c2, c3, round);
	}
	
	/**
	 * liefert ein dreigeteiltes Bild zurück mit übergebener Breite, Höhe, Farben und Text zurück
	 * @param width = Breite des Bildes
	 * @param height = Höhe des Bildes
	 * @param text = Text auf dem Button
	 * @param c = Hintergrundfarbe
	 * @param c2 = Textfarbe
	 * @param c3 = Grenzfarbe
	 * @param font = Font mit dem der String gezeichnet werden soll
	 * @param round = Abrundungen des Rechtecks
	 * @return liefert ein dreigeteiltes Bild zurück mit übergebener Breite, Höhe, Farben und Text zurück
	 */
	public BufferedImage getButtonImage(int width, int height, String text, Color c, Color c2, Color c3, Font font, int round) {
		return this.image.getButtonImage(width, height, text, c, c2, c3, new Color(255, 255, 0), new Color(255, 0, 0), font, round);
	}
	
	/**
	 * liefert ein dreigeteiltes Bild zurück mit übergebener Breite, Höhe, Farben und Text zurück
	 * @param width = Breite des Bildes
	 * @param height = Höhe des Bildes
	 * @param text = Text auf dem Button
	 * @param c = Hintergrundfarbe
	 * @param c2 = Textfarbe
	 * @param c3 = Grenzfarbe
	 * @param over = Farbe bei Maus darüber
	 * @param pressed = Farbe bei Maus gedrückt
	 * @param font = Font mit dem der String gezeichnet werden soll
	 * @param round = Abrundungen des Rechtecks
	 * @return liefert ein dreigeteiltes Bild zurück mit übergebener Breite, Höhe, Farben und Text zurück
	 */
	public BufferedImage getButtonImage(int width, int height, String text, Color c, Color c2, Color c3, Color mouseOver, Color mousePressed, Font font, int round) {
		return this.image.getButtonImage(width, height, text, c, c2, c3, mouseOver, mousePressed, font, round);
	}

	/**
	 * liefert ein dreigeteiltes Bild mit einem übergebenen Bild zurück mit übergebener Breite, Höhe, Farben und Text zurück<br />
	 * das übergebene Bild wird dabei zentriert dargestellt<br />
	 * @param width = Breite des Bildes
	 * @param height = Höhe des Bildes
	 * @param iPic = übergebenes Bild, welches zentriert dargestellt werden soll
	 * @param text = Text auf dem Button
	 * @param textColor = Textfarbe
	 * @param mousePressed = Farbe bei Maus darüber
	 * @param mouseReleased = Farbe bei Maus gedrückt
	 * @return liefert ein dreigeteiltes Bild mit einem übergebenen Bild zurück mit übergebener Breite, Höhe, Farben und Text zurück<br />
	 * das übergebene Bild wird dabei zentriert dargestellt<br />
	 */
	public BufferedImage getButtonWithImage(int width, int height, BufferedImage iPic, String text, Color textColor, Color mousePressed, Color mouseReleased) {
		return this.getButtonWithImage(width, height, iPic, text, textColor, mousePressed, mouseReleased, new Font(Font.SANS_SERIF, Font.BOLD, 13));
	}
	
	/**
	 * liefert ein dreigeteiltes Bild mit einem übergebenen Bild zurück mit übergebener Breite, Höhe, Farben und Text zurück<br />
	 * das übergebene Bild wird dabei zentriert dargestellt<br />
	 * @param width = Breite des Bildes
	 * @param height = Höhe des Bildes
	 * @param iPic = übergebenes Bild, welches zentriert dargestellt werden soll
	 * @param text = Text auf dem Button
	 * @param textColor = Textfarbe
	 * @param mousePressed = Farbe bei Maus darüber
	 * @param mouseReleased = Farbe bei Maus gedrückt
	 * @param font = Font mit dem der String gezeichnet werden soll
	 * @return liefert ein dreigeteiltes Bild mit einem übergebenen Bild zurück mit übergebener Breite, Höhe, Farben und Text zurück<br />
	 * das übergebene Bild wird dabei zentriert dargestellt<br />
	 */
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
		int fontHeight = g.getFontMetrics().getHeight() - 1 * g.getFontMetrics().getDescent();
		
		for ( int i = 0; i < 3; i++ ) {
			if (iPic != null) {
				g.drawImage(iPic, oneWidth * i + oneWidth/2 - widthPic/2, height/2 - iPic.getHeight()/2, null);
			}

			int w = g.getFontMetrics().stringWidth(text);
			if (i == 1) {
				g.setColor(mousePressed);
			} else if (i == 2) {
				g.setColor(mouseReleased);
			} else {
				g.setColor(Color.WHITE);
			}
			if (text.length() > 1) {
				g.drawString(text, i * oneWidth + oneWidth/2 - w/2 + 1, height/2 + fontHeight/2 + 1);
				g.setColor(textColor);
				g.drawString(text, i * oneWidth + oneWidth/2 - w/2, height/2 + fontHeight/2);
			} else {
				Stroke stroke = g.getStroke();
				g.setStroke(new BasicStroke(3));
				g.drawRoundRect(i * oneWidth + 1, 1, oneWidth - 3, height - 3, 5, 5);
				g.setStroke(stroke);
			}
		}
		
		g.dispose();
		return iButton;
	}
	
}
