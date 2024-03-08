package org.apogames.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
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
		BufferedImage iButton = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(width,height,BufferedImage.TRANSLUCENT);
		Graphics2D g = (Graphics2D)iButton.getGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Font fontOkButton = writeFont;
		int h = g.getFontMetrics().getHeight();
		
		for ( int i = 0; i < 3; i++ ) {
			g.setColor( background );
			g.fillRoundRect( (width)/3 * i, 0, (width)/3 - 1, height - 1, round, round );
			
			g.setFont( fontOkButton );
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
			g.drawString( text, i*x + x/2 - w/2, iButton.getHeight()/2 + h/3 );
			
			g.setColor( border );
			g.drawRoundRect( (width)/3 * i, 0, (width)/3 - 1, height - 1, round, round );
		}
		
		g.dispose();
		return iButton;
	}

}
