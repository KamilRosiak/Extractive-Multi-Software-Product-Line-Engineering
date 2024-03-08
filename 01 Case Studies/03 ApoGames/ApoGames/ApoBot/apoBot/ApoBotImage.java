package apoBot;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import org.apogames.image.ApoImageFromValue;

import apoBot.level.ApoBotLevel;

/**
 * Klasse, die sich um alles mit den Bildern kümmert
 * vom Laden bis zum selber erstellen
 * @author Dirk Aporius
 *
 */
public class ApoBotImage {
	
	/** HilfsObjekt, um vorgefertigte Bilder zu laden (z.B. für Buttons) */
	private ApoImageFromValue image;
	
	public ApoBotImage() {
		this.image = new ApoImageFromValue();
	}
	
	public BufferedImage getImage(String path, boolean bFile) {
		return this.image.getImageFromPath(path, bFile);
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
	 * gibt das Logo für das Spiel zurück
	 * @param width : Breite des Logos
	 * @param height : Höhe des Logos
	 * @param iPlayer : Bild des Spielers
	 * @return Logo für das Spiel
	 */
	public BufferedImage getLogo(int width, int height, BufferedImage iPlayer) {
		BufferedImage iLogo = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( width, height, BufferedImage.TRANSLUCENT );
		Graphics2D g	= (Graphics2D)iLogo.getGraphics();

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.drawImage(iPlayer.getSubimage(0 * ApoBotConstants.SIZE, 70 * ApoBotConstants.SIZE, width, height), 0, 0, null);
				
		g.dispose();
		
		return iLogo;
	}

	/**
	 * erstellt das Backgroundimage für das Level
	 * @param iBackgroundOrg
	 * @param width
	 * @param height
	 * @param x
	 * @param y
	 * @param widthFunction
	 * @return backgroundimage
	 */
	public BufferedImage getIBackground(BufferedImage iBackgroundOrg, int width, int height, int x, int y, int widthFunction) {
		BufferedImage iBackground = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( iBackgroundOrg.getWidth(), iBackgroundOrg.getHeight(), BufferedImage.TRANSLUCENT );
		Graphics2D g	= (Graphics2D)iBackground.getGraphics();

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.drawImage(iBackgroundOrg, 0, 0, null);
		
		g.setColor(new Color(255, 255, 255, 100));
		g.fillRoundRect(iBackgroundOrg.getWidth() - width, 0, width - 2, height, 30, 30);
		g.fillRoundRect(x - 5, y - 5, widthFunction + 10, 32 + 10, 8, 8);
		g.setColor(Color.black);
		g.drawRoundRect(iBackgroundOrg.getWidth() - width, 0, width - 2, height, 30, 30);
		g.drawRoundRect(x - 5, y - 5, widthFunction + 10, 32 + 10, 8, 8);
		
		g.dispose();
		
		return iBackground;
	}

	/**
	 * gibt das Image für das Level zurück (das eigentliche Level)
	 * @param level
	 * @param iTile
	 * @param width
	 * @param height
	 * @return Image
	 */
	public BufferedImage getIBackground(ApoBotLevel level, BufferedImage iTile, int width, int height) {
		BufferedImage iBackground = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( width, height + 6 * (16 * ApoBotConstants.SIZE), BufferedImage.TRANSLUCENT );
		Graphics2D g	= (Graphics2D)iBackground.getGraphics();

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		int w = 16 * ApoBotConstants.SIZE;
		int startX = 0;
		int startY = (level.getLevel().length) * w/2 + 2 * w;
		
		for (int y = level.getLevel().length - 1; y >= 0; y--) {
			for (int x = 0; x < level.getLevel()[0].length; x++) {
				for (int heightLevel = 0; heightLevel <= level.getLevel()[y][x].getHeight(); heightLevel++) {
					int myX = startX + x * w + y * w;
					int myY = startY - y * w/2 + x * w/2 - heightLevel * w/2;
					if (level.getLevel()[y][x].getGround() != ApoBotConstants.GROUND_REAL_EMPTY) {
						if (y % 2 == 0) {
							g.drawImage(iTile.getSubimage(0 * 32 * ApoBotConstants.SIZE, 0, 32 * ApoBotConstants.SIZE, 25 * ApoBotConstants.SIZE), myX, myY, null); 
						} else {
							g.drawImage(iTile.getSubimage(4 * 32 * ApoBotConstants.SIZE, 0, 32 * ApoBotConstants.SIZE, 25 * ApoBotConstants.SIZE), myX, myY, null);
						}
					}/* else if (level.getLevel()[y][x].getGround() == ApoBotConstants.GROUND_REAL_EMPTY) {
						g.drawImage(iTile.getSubimage(3 * 32, 0, 32, 25), myX, myY, null); 
					}*/
					if (level.getLevel()[y][x].getGround() == ApoBotConstants.GROUND_ORIGINAL) {
						g.drawImage(iTile.getSubimage(1 * 32 * ApoBotConstants.SIZE, 0 * ApoBotConstants.SIZE, 32 * ApoBotConstants.SIZE, 25 * ApoBotConstants.SIZE), myX, myY, null); 
					} else if (level.getLevel()[y][x].getGround() == ApoBotConstants.GROUND_GOAL) {
						g.drawImage(iTile.getSubimage(2 * 32 * ApoBotConstants.SIZE, 0 * ApoBotConstants.SIZE, 32 * ApoBotConstants.SIZE, 25 * ApoBotConstants.SIZE), myX, myY, null); 
					}
				}
			}
		}
		
		g.dispose();
		
		return iBackground;
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
		int h = g.getFontMetrics().getHeight();
		
		for ( int i = 0; i < 3; i++ ) {
			g.setColor( background );
			g.fillRoundRect( (width)/3 * i, 0, (width)/3 - 1, height - 1, 5, 5 );
			
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
			g.drawRoundRect( (width)/3 * i, 0, (width)/3 - 1, height - 1, 5, 5 );
		}
		
		g.dispose();
		return iButton;
	}
	
	
	/**
	 * gibt einen Button zurück
	 * @param width : Breite des Buttons
	 * @param height : Höhe des Buttons
	 * @param text : Text auf dem Button
	 * @return der Button
	 */
	public BufferedImage getButtonImage(int width, int height, String text, int round) {
		return this.image.getButtonImage( width, height, text, round );
	}
	
	/**
	 * liefert ein Button zurück
	 * @param width : Breite des Buttons
	 * @param height : Höhe des Buttons
	 * @param text : Text auf dem Button
	 * @param c : Backgroundcolor des Buttons
	 * @param c2 : Textcolor 
	 * @param c3 : Bordercolor des Buttons
	 * @return der Button
	 */
	public BufferedImage getButtonImage(int width, int height, String text, Color c, Color c2, Color c3, int round) {
		return this.image.getButtonImage( width, height, text, c, c2, c3, round );
	}
	
	public BufferedImage getButtonImage(int width, int height, String text, Color c, Color c2, Color c3, Color c4, Color c5) {
		return this.image.getButtonImage(width, height, text, c, c2, c3, c4, c5);
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
