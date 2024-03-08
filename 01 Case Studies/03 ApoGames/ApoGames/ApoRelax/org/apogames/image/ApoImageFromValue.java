package org.apogames.image;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

/**
 * erstellt Hintergrundbilder und Buttons mithilfe von den
 * übergebenen Werte wie Größe, Farbe usw.
 * @author Dirk Aporius
 *
 */
public class ApoImageFromValue {

	private ApoImage image;
	
	public ApoImageFromValue() {
		super();
		
		this.image = new ApoImage();
	}
	
	/**
	 * malt einen String zentriert an den übergebenen Wert
	 * @param g : Das Graphics2DObjekt
	 * @param s : der String
	 * @param x : x- Wert
	 * @param y : y- Wert
	 * @param width : Breite
	 */
	public void drawString( Graphics2D g, String s, int x, int y, int width ) {
		int w = g.getFontMetrics().stringWidth( s );
		g.drawString( s, x + width * 1/2 - w/2, y );
	}
	
	/**
	 * liefert ein Bild von der Festplatte zurück
	 * @param path = String mit der Pfadangabe
	 * @param bLoad = true, für laden aus der Jar, sonst FALSE
	 * @return liefert ein Bild zurück, wenn keins geladen werden kann wird NULL zurückgegeben
	 */
	public BufferedImage getImageFromPath(String path, boolean bLoad) {
		return this.image.getPic(path, bLoad);
	}	
	
	/**
	 * liefert ein Hintergrundbild mit der übergebenen Größe und der Farbe schwarz zurück
	 * @param width = Weite des Bildes
	 * @param height = Höhe des Bildes
	 * @return liefert ein Hintergrundbild mit der übergebenen Größe und der Farbe schwarz zurück
	 */
	public BufferedImage getAndMakeIBackground( int width, int height ) {
		return this.getAndMakeIBackground( width, height, Color.black );
	}
	
	/**
	 * liefert ein Hintergrundbild mit der übergebenen Größe und der übergebenen Farbe zurück
	 * @param width = Weite des Bildes
	 * @param height = Höhe des Bildes
	 * @param c = Hintergrundfarbe
	 * @return liefert ein Hintergrundbild mit der übergebenen Größe und der übergebenen Farbe zurück
	 */
	public BufferedImage getAndMakeIBackground( int width, int height, Color c ) {
		BufferedImage iBackground = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(width,height,BufferedImage.TRANSLUCENT);
		Graphics2D g = (Graphics2D)iBackground.getGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor( c );
		g.fillRect( 0, 0, width, height );
		
		g.dispose();
		
		return iBackground;
	}
	
	/**
	 * liefert ein dreigeteiltes Bild zurück mit übergebener Breite, Höhe und Text und mit der Hintergrundfarbe schwarz, mit der Farbe weiß für den Font und der Grenzfarbe grau
	 * @param width = Weite des Bildes
	 * @param height = Höhe des Bildes
	 * @param text = Text auf dem Button
	 * @return liefert ein dreigeteiltes Bild zurück mit übergebener Breite, Höhe und Text und mit der Hintergrundfarbe schwarz, mit der Farbe weiß für den Font und der Grenzfarbe grau
	 */
	public BufferedImage getButtonImage( int width, int height, String text, int round ) {
		return this.getButtonImage( width, height, text, Color.black, Color.white, Color.gray, new Color( 255, 255, 0, 100 ), new Color( 255, 0, 0, 100 ), new Font( "Dialog", Font.BOLD, 15 ), round );
	}
	
	/**
	 * liefert ein dreigeteiltes Bild zurück mit übergebener Breite, Höhe, Farben und Text zurück
	 * @param width = Weite des Bildes
	 * @param height = Höhe des Bildes
	 * @param text = Text auf dem Button
	 * @param background = Hintergrundfarbe
	 * @param font = Textfarbe
	 * @param border = Grenzfarbe
	 * @return liefert ein dreigeteiltes Bild zurück mit übergebener Breite, Höhe, Farben und Text zurück
	 */
	public BufferedImage getButtonImage( int width, int height, String text, Color background, Color font, Color border ) {
		return this.getButtonImage(width, height, text, background, font, border, new Color( 255, 255, 0, 100 ), new Color( 255, 0, 0, 100 ));
	}
	
	/**
	 * liefert ein dreigeteiltes Bild zurück mit übergebener Breite, Höhe, Farben und Text zurück
	 * @param width = Weite des Bildes
	 * @param height = Höhe des Bildes
	 * @param text = Text auf dem Button
	 * @param background = Hintergrundfarbe
	 * @param font = Textfarbe
	 * @param border = Grenzfarbe
	 * @return liefert ein dreigeteiltes Bild zurück mit übergebener Breite, Höhe, Farben und Text zurück
	 */
	public BufferedImage getButtonImage( int width, int height, String text, Color background, Color font, Color border, int round ) {
		return this.getButtonImage(width, height, text, background, font, border, new Color( 255, 255, 0, 100 ), new Color( 255, 0, 0, 100 ), new Font( "Dialog", Font.BOLD, 15 ), round);
	}
	
	/**
	 * liefert ein dreigeteiltes Bild zurück mit übergebener Breite, Höhe, Farben und Text zurück
	 * @param width = Weite des Bildes
	 * @param height = Höhe des Bildes
	 * @param text = Text auf dem Button
	 * @param background = Hintergrundfarbe
	 * @param font = Textfarbe
	 * @param border = Grenzfarbe
	 * @param over = Farbe bei Maus darüber
	 * @param pressed = Farbe bei Maus gedrückt
	 * @return liefert ein dreigeteiltes Bild zurück mit übergebener Breite, Höhe, Farben und Text zurück
	 */
	public BufferedImage getButtonImage( int width, int height, String text, Color background, Color font, Color border, Color over, Color pressed ) {
		return this.getButtonImage(width, height, text, background, font, border, over, pressed, new Font( "Dialog", Font.BOLD, 15 ), 5);
	}
	
	/**
	 * liefert ein dreigeteiltes Bild zurück mit übergebener Breite, Höhe, Farben und Text zurück
	 * @param width = Weite des Bildes
	 * @param height = Höhe des Bildes
	 * @param text = Text auf dem Button
	 * @param background = Hintergrundfarbe
	 * @param font = Textfarbe
	 * @param border = Grenzfarbe
	 * @param over = Farbe bei Maus darüber
	 * @param pressed = Farbe bei Maus gedrückt
	 * @return liefert ein dreigeteiltes Bild zurück mit übergebener Breite, Höhe, Farben und Text zurück
	 */
	public BufferedImage getButtonImage( int width, int height, String text, Color background, Color font, Color border, Color over, Color pressed, Font writeFont, int round ) {
		BufferedImage iButton = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(width,height,BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = (Graphics2D)iButton.getGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Font fontOkButton = writeFont;
		g.setFont(fontOkButton);
		int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
		
		for ( int i = 0; i < 3; i++ ) {
			g.setColor( background );
			g.fillRoundRect( (width)/3 * i, 0, (width)/3 - 1, height - 1, round, round );
			
			int w = g.getFontMetrics().stringWidth( text );
			int x = iButton.getWidth()/3;

			g.setColor(font);
			g.drawString( text, i*x + x/2 - w/2, iButton.getHeight()/2 + h/2);

			Stroke stroke = g.getStroke();
			g.setStroke(new BasicStroke(2));
			if (i == 1) {
				g.setColor(over);
			} else if (i == 2) {
				g.setColor(pressed);
			} else {
				g.setColor(border);				
			}
			g.drawRoundRect((width)/3 * i + 1, 0 + 1, (width)/3 - 3, height - 3, round, round);
			g.setStroke(stroke);
		}
		
		g.dispose();
		return iButton;
	}
	
	public BufferedImage getButtonWithImage(int width, int height, BufferedImage iPic, Color background, Color border, Color mousePressed, Color mouseReleased) {
		BufferedImage iButton = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(width,height,BufferedImage.TRANSLUCENT);
		Graphics2D g = (Graphics2D)iButton.getGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		int oneWidth = width/3;
		int widthPic = 0;
		if (iPic != null) {
			widthPic = iPic.getWidth();
		}
		
		for ( int i = 0; i < 3; i++ ) {
			g.setColor( background );
			g.fillRoundRect( (width)/3 * i, 0, (width)/3 - 1, height - 1, 5, 5 );
			
			if (iPic != null) {
				g.drawImage(iPic, oneWidth * i + oneWidth/2 - widthPic/2, height/2 - iPic.getHeight()/2, null);
			}
			if (i == 0) {
				g.setColor(background);
			} else if (i == 1) {
				g.setColor(mousePressed);
			} else {
				g.setColor(mouseReleased);
			}
			g.drawRoundRect((width)/3 * i + 1, 1, (width)/3 - 3, height - 3, 5, 5);
			
			g.setColor(border);
			g.drawRoundRect((width)/3 * i, 0, (width)/3 - 1, height - 1, 5, 5);
		}
		
		g.dispose();
		return iButton;
	}
	
	public BufferedImage getButtonImage( int width, int height, String text, Color background, Color font, Color border, Color over, Color pressed, Font writeFont ) {
		BufferedImage iButton = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(width,height,BufferedImage.TRANSLUCENT);
		Graphics2D g = (Graphics2D)iButton.getGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Font fontOkButton = writeFont;
		g.setFont(fontOkButton);
		int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
		
		for ( int i = 0; i < 3; i++ ) {
			g.setColor( background );
			g.fillRoundRect( (width)/3 * i, 0, (width)/3 - 1, height - 1, 5, 5 );
			
			int w = g.getFontMetrics().stringWidth( text );
			int x = iButton.getWidth()/3;
			
			if ( i == 1 ) {
				g.setColor(over);
				g.fillRoundRect( i*x + x/2 - w/2, iButton.getHeight()/2 - h/4 + 1, w, h/2 - 2, 20, 20 );
			} else if ( i == 2 ) {
				g.setColor(pressed);
				g.fillRoundRect( i*x + x/2 - w/2, iButton.getHeight()/2 - h/4 + 1, w, h/2 - 2, 20, 20 );
			}
			
			g.setColor( font );
			g.drawString( text, i*x + x/2 - w/2, iButton.getHeight()/2 + h/2);
			
			g.setColor( border );
			g.drawRoundRect( (width)/3 * i, 0, (width)/3 - 1, height - 1, 5, 5 );
		}
		
		g.dispose();
		return iButton;
	}
	
	/**
	 * kopiert ein Bild
	 * @param iImage : das zu kopierende Image
	 * @return das kopierte Bild
	 */
	public static BufferedImage getImageCopy(BufferedImage iImage) {
		BufferedImage iNewImage = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( iImage.getWidth(), iImage.getHeight(), iImage.getTransparency() );
		Graphics2D g	= (Graphics2D)iNewImage.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.drawImage(iImage, 0, 0, null);
			
		g.dispose();
			
		return iNewImage;
	}

}
