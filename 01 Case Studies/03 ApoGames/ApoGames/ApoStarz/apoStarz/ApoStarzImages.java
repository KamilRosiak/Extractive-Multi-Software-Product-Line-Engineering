package apoStarz;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import org.apogames.image.ApoImageFromValue;

public class ApoStarzImages {

	/** HilfsObjekt, um vorgefertigte Bilder zu laden (z.B. für Buttons) */
	private ApoImageFromValue image;
	
	public ApoStarzImages() {
		this.image = new ApoImageFromValue();
	}
	
	/**
	 * liefert ein Bild von der Festplatte zurück
	 * @param path = String mit der Pfadangabe
	 * @param bLoad = TRUE, für laden von der Festplatte oder URL, FALSE für laden aus der JAR
	 * @return liefert ein Bild zurück, wenn keins geladen werden kann wird NULL zurückgegeben
	 */
	public BufferedImage getImageFromPath(String path, boolean bLoad) {
		return this.image.getImageFromPath(path, bLoad);
	}	
	
	/**
	 * gibt einen Button zurück
	 * @param width : Breite des Buttons
	 * @param height : Höhe des Buttons
	 * @param text : Text auf dem Button
	 * @return der Button
	 */
	public BufferedImage getButtonImage(int width, int height, String text) {
		return this.image.getButtonImage(width, height, text);
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
	public BufferedImage getButtonImage(int width, int height, String text, Color c, Color c2, Color c3) {
		return this.image.getButtonImage(width, height, text, c, c2, c3);
	}
	
	/**
	 * liefert ein Button zurück
	 * @param width : Breite des Buttons
	 * @param height : Höhe des Buttons
	 * @param text : Text auf dem Button
	 * @param c : Backgroundcolor des Buttons
	 * @param c2 : Textcolor 
	 * @param c3 : Bordercolor des Buttons
	 * @param c4 : Color Maus Over Button
	 * @param c5 : Color Maus Pressed Button
	 * @return der Button
	 */
	public BufferedImage getButtonImage(int width, int height, String text, Color c, Color c2, Color c3, Color c4, Color c5) {
		return this.image.getButtonImage(width, height, text, c, c2, c3, c4, c5);
	}
	
	/**
	 * liefert ein Button zurück
	 * @param width : Breite des Buttons
	 * @param height : Höhe des Buttons
	 * @param text : Text auf dem Button
	 * @param c : Backgroundcolor des Buttons
	 * @param c2 : Textcolor 
	 * @param c3 : Bordercolor des Buttons
	 * @param c4 : Color Maus Over Button
	 * @param c5 : Color Maus Pressed Button
	 * @param write : Font der benutzt wird
	 * @return der Button
	 */
	public BufferedImage getButtonImage(int width, int height, String text, Color c, Color c2, Color c3, Color c4, Color c5, Font write) {
		return this.image.getButtonImage(width, height, text, c, c2, c3, c4, c5, write);
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
	public BufferedImage getButtonWithImage(int width, int height, BufferedImage iPic, Color background, Color font, Color border) {
		BufferedImage iButton = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(width,height,BufferedImage.TRANSLUCENT);
		Graphics2D g = (Graphics2D)iButton.getGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		int oneWidth = width/3;
		int widthPic = iPic.getWidth();
		if (widthPic > oneWidth) {
			
		}
		for ( int i = 0; i < 3; i++ ) {
			g.setColor( background );
			g.fillRoundRect( (width)/3 * i, 0, (width)/3 - 1, height - 1, 5, 5 );
			
			g.drawImage(iPic, oneWidth * i + oneWidth/2 - widthPic/2, height/2 - iPic.getHeight()/2, null);
			
			if (i == 0) {
				g.setColor(background);
			} else if (i == 1) {
				g.setColor(Color.yellow);
			} else {
				g.setColor(Color.red);
			}
			g.drawRoundRect((width)/3 * i + 1, 1, (width)/3 - 3, height - 3, 5, 5);
			
			g.setColor(border);
			g.drawRoundRect((width)/3 * i, 0, (width)/3 - 1, height - 1, 5, 5);
		}
		
		g.dispose();
		return iButton;
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
	public BufferedImage getButtonKartei(int width, int height, String text, Color background, Color font, Color border, Color over, Color pressed) {
		BufferedImage iButton = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(width,height,BufferedImage.TRANSLUCENT);
		Graphics2D g = (Graphics2D)iButton.getGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Font fontOkButton = new Font( "Dialog", Font.BOLD, 15 );
		int h = g.getFontMetrics().getHeight();
		
		for ( int i = 0; i < 3; i++ ) {
			g.setColor(background);
			g.fillRoundRect((width)/3 * i, 0, (width)/3 - 1, height - 1, 10, 10);
			g.setColor(border);
			g.drawRoundRect((width)/3 * i, 0, (width)/3 - 1, height - 1, 10, 10);
			
			g.setColor(background);
			g.fillRect((width)/3 * i, 7, (width)/3 - 1, height - 7);
			g.setColor(border);
			g.drawLine((width)/3 * i, 7, (width)/3 * i, height + 1);
			g.drawLine((width)/3 * (i+1) - 1, 7, (width)/3 * (i+1) - 1, height + 1);
			g.drawLine(0, height - 1, width, height - 1);
			
			g.setFont( fontOkButton );
			int w = g.getFontMetrics().stringWidth( text );
			int x = iButton.getWidth()/3;
			
			if ( i == 1 ) {
				g.setColor(over);
				g.fillRoundRect( i*x + x/2 - w/2, iButton.getHeight()/2 - 4, w, 8, 20, 20 );
			} else if ( i == 2 ) {
				g.setColor(pressed);
				g.fillRoundRect( i*x + x/2 - w/2, iButton.getHeight()/2 - 4, w, 8, 20, 20 );
			}
			
			g.setColor(font);
			g.drawString(text, i*x + x/2 - w/2, iButton.getHeight()/2 + h/3);
		}
		
		g.dispose();
		return iButton;
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
	public BufferedImage getButtonRotate(int width, int height, boolean bLeft, Color background, Color border ) {
		BufferedImage iButton = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(width,height,BufferedImage.TRANSLUCENT);
		Graphics2D g = (Graphics2D)iButton.getGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		int oneWidth = width/3;
		int[] xArray = new int[] {1, oneWidth - 10, oneWidth - 5, oneWidth - 5, oneWidth - 2, oneWidth - 8, oneWidth - 15, oneWidth - 11, oneWidth - 11, oneWidth - 16, 1};
		if (bLeft) {
			xArray = new int[] {oneWidth - 2, 10, 5, 5, 2, 8, 15, 11, 11, 16, oneWidth - 2};
		}
		int[] yArray = new int[] {1,             1,            6,   height - 5,   height - 5,   height - 1,    height - 5,    height - 5,            10,             6, 6};
		for ( int i = 0; i < 3; i++ ) {
			g.setColor( background );
			g.fillPolygon(xArray, yArray, xArray.length);
			
			if (i == 0) {
				g.setColor(border);
			} else if (i == 1) {
				g.setColor(Color.blue);
			} else {
				g.setColor(Color.red);
			}
			g.drawPolygon(xArray, yArray, xArray.length);
			
			for (int j = 0; j < xArray.length; j++) {
				xArray[j] += oneWidth;
			}
		}
		
		g.dispose();
		return iButton;
	}
	
	/**
	 * gibt das Bild des Spieler wieder in der Farbe die mit übergeben wurde
	 * @param width = Breite des Spielers
	 * @param height = Höhe des Spielers
	 * @param color: Farbe, die der Spieler haben soll
	 * @return gibt das Bild des Spieler wieder in der Farbe die mit übergeben wurde
	 */
	public BufferedImage getPlayer(int width, int height, Color color) {
		BufferedImage iPlayer = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( width, height, Transparency.TRANSLUCENT );
		Graphics2D g	= (Graphics2D)iPlayer.getGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor( Color.RED );
		int w = 8;
		int y = 8;
		Stroke basic = g.getStroke();
		g.setStroke( new BasicStroke(2) );
		g.drawLine( iPlayer.getWidth()/2 - w/2, iPlayer.getHeight()/2 + y, iPlayer.getWidth()/2 - w, iPlayer.getHeight()/2 + y/2 );
		g.drawLine( iPlayer.getWidth()/2 - w/2, iPlayer.getHeight()/2 + y, iPlayer.getWidth()/2 + w*3/4, iPlayer.getHeight()/2 + y/4*3 );
		
		g.setStroke( basic );
		g.setColor( color );
		g.fillRoundRect( 0, 0, iPlayer.getWidth() - 1, iPlayer.getHeight() - 1, 15, 15 );
		g.setColor( Color.BLACK );
		g.drawRoundRect( 0, 0, iPlayer.getWidth() - 1, iPlayer.getHeight() - 1, 15, 15 );
		
		g.fillRoundRect( iPlayer.getWidth()/2 - 5, iPlayer.getHeight()/2 - 8, 3, 10, 4, 4);
		g.fillRoundRect( iPlayer.getWidth()/2 + 2, iPlayer.getHeight()/2 - 8, 3, 10, 4, 4);
		
		g.dispose();
		return iPlayer;
	}
	
	/**
	 * gibt das Hintergrundbild wieder
	 * @param width = Breite des Bildes
	 * @param height = Höhe des Bildes
	 * @return gibt das Hintergrundbild wieder
	 */
	public BufferedImage getBackgroundGame(int width, int height, String pathBackground, String pathMenu) {
		BufferedImage iBackground = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( width, height, Transparency.TRANSLUCENT );
		Graphics2D g	= (Graphics2D)iBackground.getGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if (pathBackground == null) {
			g.setColor(Color.black);
			g.fillRect(0, 0, iBackground.getWidth(), iBackground.getHeight());
		} else {
			g.drawImage(this.getImageFromPath(pathBackground, false), 0, 0, null);
		}

		if (pathMenu == null) {
			g.setColor(Color.yellow);
			g.fillRect(ApoStarzConstants.GAME_GAME_WIDTH, 0, ApoStarzConstants.GAME_MENU_WIDTH, ApoStarzConstants.GAME_HEIGHT);
		} else {
			g.drawImage(this.getImageFromPath(pathMenu, false), ApoStarzConstants.GAME_GAME_WIDTH, 0, null);
		}
		
		g.setColor(Color.black);
		g.setFont(ApoStarzConstants.FONT_BIG);
		
		Color orange = new Color(255, 200, 0);
		BufferedImage iLevel = this.getButtonKartei( 77 * 3, 20, "level", orange, Color.black, Color.black, new Color(0, 128, 255, 100), new Color(255, 0, 0, 100));
		g.drawImage(iLevel.getSubimage(0, 0, 77, 20), ApoStarzConstants.GAME_GAME_WIDTH + 3, 160, null);
		
		BufferedImage iLoad = this.getButtonKartei( 77 * 3, 20, "load", orange, Color.black, Color.black, new Color(0, 128, 255, 100), new Color(255, 0, 0, 100));
		g.drawImage(iLoad.getSubimage(0, 0, 77, 20), ApoStarzConstants.GAME_GAME_WIDTH + 3 + 77, 160, null);
		
		g.setColor(orange);
		g.drawLine(ApoStarzConstants.GAME_GAME_WIDTH + 3, 179, ApoStarzConstants.GAME_WIDTH - 5, 179);
		g.fillRect(ApoStarzConstants.GAME_GAME_WIDTH + 3, 179, ApoStarzConstants.GAME_MENU_WIDTH - 6, 230);
		
		g.dispose();
		return iBackground;
	}

	/**
	 * gibt das Hintergrundbild wieder
	 * @param width = Breite des Bildes
	 * @param height = Höhe des Bildes
	 * @return gibt das Hintergrundbild wieder
	 */
	public BufferedImage getBackgroundEditor(int width, int height, Color c) {
		BufferedImage iBackground = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( width, height, Transparency.TRANSLUCENT );
		Graphics2D g	= (Graphics2D)iBackground.getGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(c);
		g.fillRect(0, 0, iBackground.getWidth(), iBackground.getHeight());
		
		g.setColor(Color.yellow);
		g.fillRect(ApoStarzConstants.GAME_GAME_WIDTH, 0, ApoStarzConstants.GAME_MENU_WIDTH, ApoStarzConstants.GAME_HEIGHT);
		
		g.setColor(Color.black);
		g.setFont(ApoStarzConstants.FONT_BIG);
		
		String s = "Apo-Starz";
		int w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH + ApoStarzConstants.GAME_MENU_WIDTH/2 - w/2, 30);
		
		s = "Editor";
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH + ApoStarzConstants.GAME_MENU_WIDTH/2 - w/2, 55);
		
		g.drawLine(ApoStarzConstants.GAME_GAME_WIDTH, 65, ApoStarzConstants.GAME_WIDTH, 65);
		g.setFont(ApoStarzConstants.FONT_NORMAL);
		
		s = "levelwidth: ";
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH + 5, 85);
		
		s = "wall: ";
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH + 5, 145);
		
		s = "block: ";
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH + 5, 185);
		
		s = "fire: ";
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH + 5, 225);
		
		s = "goal: ";
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH + 5, 265);
		
		s = "star: ";
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH + 5, 305);
		
		s = "Levelcode";
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH + ApoStarzConstants.GAME_MENU_WIDTH/2 - w/2, ApoStarzConstants.GAME_HEIGHT - 140);
		
		g.dispose();
		return iBackground;
	}
	
	/**
	 * gibt das Bild für den Block wieder
	 * @param width = Breite des Blockes
	 * @param height = Höhe des Blockes
	 * @param transparency = Transparenz des Blockes
	 * @return gibt das Bild für den Block wieder
	 */
	public BufferedImage getBlock(int width, int height, Color c, int transparency) {
		BufferedImage iBlock = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( width, height, Transparency.TRANSLUCENT );
		Graphics2D g	= (Graphics2D)iBlock.getGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor( new Color( c.getRed(), c.getGreen(), c.getBlue(), transparency ) );
		g.fillRoundRect( 0, 0, iBlock.getWidth() - 1, iBlock.getHeight() - 1, 10, 10 );
		g.setColor( Color.black );
		g.drawRoundRect( 0, 0, iBlock.getWidth() - 1, iBlock.getHeight() - 1, 10, 10 );

		g.setColor(Color.black);
		g.fillRoundRect( iBlock.getWidth()/2 - 5, iBlock.getHeight()/2 - 8, 3, 10, 4, 4);
		g.fillRoundRect( iBlock.getWidth()/2 + 2, iBlock.getHeight()/2 - 8, 3, 10, 4, 4);
		
		g.dispose();
		return iBlock;
	}
	
	/**
	 * gibt das Bild für den Stern wieder
	 * @param width = Breite des Sterns
	 * @param height = Höhe des Blockes
	 * @param transparency = Transparenz des Blockes
	 * @return gibt das Bild für den Stern wieder
	 */
	public BufferedImage getStar(int width, int height, Color c, int transparency) {
		BufferedImage iStar = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( width, height, Transparency.TRANSLUCENT );
		Graphics2D g	= (Graphics2D)iStar.getGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		int[] xValue = new int[] { 0, iStar.getWidth()/2 - 5, iStar.getWidth()/2, iStar.getWidth()/2 + 5, iStar.getWidth(), iStar.getWidth()/2 + 5, iStar.getWidth()/2, iStar.getWidth()/2 - 5 };
		int[] yValue = new int[] { iStar.getHeight()/2, iStar.getHeight()/2 - 5, 0, iStar.getHeight()/2 - 5, iStar.getHeight()/2, iStar.getHeight()/2 + 5, iStar.getHeight(), iStar.getHeight()/2 + 5 };		
		
		g.setColor( new Color( c.getRed(), c.getGreen(), c.getBlue(), transparency ) );
		g.fillPolygon(xValue, yValue, xValue.length);
		g.setColor( Color.WHITE );
		g.drawPolygon(xValue, yValue, xValue.length);

		g.setColor(Color.black);
		g.fillRoundRect( iStar.getWidth()/2 - 5, iStar.getHeight()/2 - 8, 3, 10, 4, 4);
		g.fillRoundRect( iStar.getWidth()/2 + 2, iStar.getHeight()/2 - 8, 3, 10, 4, 4);
		g.dispose();
		
		return iStar;
	}
	
	/**
	 * gibt das Bild für das Ziel wieder
	 * @param width = Breite des Blockes
	 * @param height = Höhe des Blockes
	 * @param transparency = Transparenz des Blockes
	 * @return gibt das Bild für den Ziel wieder
	 */
	public BufferedImage getGoal(int width, int height, Color c, int transparency) {
		BufferedImage iGoal = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( width, height, Transparency.TRANSLUCENT );
		Graphics2D g	= (Graphics2D)iGoal.getGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.drawImage(this.getBlock(width, height, c, transparency), 0, 0, width, height, null);
		g.drawImage(this.getStar(width, height, Color.red, transparency), 0, 0, width, height, null);
		
		g.dispose();
		return iGoal;
	}

	/**
	 * gibt das Bild für das Feuer wieder
	 * @param width = Breite des Feuers
	 * @param height = Höhe des Feuers
	 * @param transparency = Transparenz des Feuers
	 * @return gibt das Bild für das Feuer wieder
	 */
	public BufferedImage getFire(int width, int height, Color c, int transparency) {
		BufferedImage iFire = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( width, height, Transparency.TRANSLUCENT );
		Graphics2D g	= (Graphics2D)iFire.getGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int[] xValue = new int[] { 5, iFire.getWidth()/3, iFire.getWidth()/2, iFire.getWidth()*2/3, iFire.getWidth() - 5, iFire.getWidth() - 5,  iFire.getWidth()*2/3, iFire.getWidth()/3, 5 };
		int[] yValue = new int[] { 0, 5,                  0,                  5,                    0,                    iFire.getHeight() - 5, iFire.getHeight() - 1,    iFire.getHeight() - 1,  iFire.getHeight() - 5 };
		
		g.setColor( new Color( c.getRed(), c.getGreen(), c.getBlue(), transparency ) );
		g.fillPolygon(xValue, yValue, xValue.length);
		g.setColor( Color.black );
		g.drawPolygon(xValue, yValue, xValue.length);
		
		g.dispose();
		return iFire;
	}
	
}
