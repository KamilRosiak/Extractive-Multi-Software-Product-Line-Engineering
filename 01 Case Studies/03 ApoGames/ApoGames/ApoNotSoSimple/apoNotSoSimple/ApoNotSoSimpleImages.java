package apoNotSoSimple;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.apogames.image.ApoImageFromValue;

public class ApoNotSoSimpleImages {

	/** HilfsObjekt, um vorgefertigte Bilder zu laden (z.B. für Buttons) */
	private ApoImageFromValue image;
	
	public static BufferedImage ORIGINAL_DRIVE;
	
	public static BufferedImage ORIGINAL_FIXED;
	public static BufferedImage ORIGINAL_UP;
	public static BufferedImage ORIGINAL_DOWN;
	public static BufferedImage ORIGINAL_LEFT;
	public static BufferedImage ORIGINAL_RIGHT;
	public static BufferedImage ORIGINAL_PLAYER;
	public static BufferedImage ORIGINAL_FINISH;
	public static BufferedImage ORIGINAL_VISIBLE_TRUE;
	public static BufferedImage ORIGINAL_VISIBLE_FALSE;
	public static BufferedImage ORIGINAL_STEP;
	public static BufferedImage ORIGINAL_STEP_FINISH;
	
	static {
		ApoNotSoSimpleImages.ORIGINAL_DRIVE = ApoNotSoSimpleImages.getOriginalImageDrive(Color.BLACK);
		ApoNotSoSimpleImages.ORIGINAL_FIXED = ApoNotSoSimpleImages.getImageSimple(Color.DARK_GRAY, null, null, Color.BLACK);
		boolean[] arrow = new boolean[7];
		arrow[ApoNotSoSimpleConstants.PLAYER_DIRECTION_UP] = true;
		ApoNotSoSimpleImages.ORIGINAL_UP = ApoNotSoSimpleImages.getImageSimpleRaute(Color.YELLOW, arrow, null, Color.BLACK);
		arrow = new boolean[7];
		arrow[ApoNotSoSimpleConstants.PLAYER_DIRECTION_DOWN] = true;
		ApoNotSoSimpleImages.ORIGINAL_DOWN = ApoNotSoSimpleImages.getImageSimpleRaute(Color.RED, arrow, null, Color.BLACK);
		arrow = new boolean[7];
		arrow[ApoNotSoSimpleConstants.PLAYER_DIRECTION_LEFT] = true;
		ApoNotSoSimpleImages.ORIGINAL_LEFT = ApoNotSoSimpleImages.getImageSimpleTriangle(Color.MAGENTA, arrow, null, Color.BLACK, false);
		arrow = new boolean[7];
		arrow[ApoNotSoSimpleConstants.PLAYER_DIRECTION_RIGHT] = true;
		ApoNotSoSimpleImages.ORIGINAL_RIGHT = ApoNotSoSimpleImages.getImageSimpleTriangle(Color.PINK, arrow, null, Color.BLACK, true);
		arrow = new boolean[7];
		arrow[4] = true;
		ApoNotSoSimpleImages.ORIGINAL_PLAYER = ApoNotSoSimpleImages.getImageSimple(Color.WHITE, arrow, null, Color.BLACK);
		ApoNotSoSimpleImages.ORIGINAL_FINISH = ApoNotSoSimpleImages.getImageSimple(Color.GREEN, null, "X", Color.BLACK);
		ApoNotSoSimpleImages.ORIGINAL_VISIBLE_TRUE = ApoNotSoSimpleImages.getImageSimpleRec(new Color(160, 120, 255), null, "+", Color.BLACK);
		ApoNotSoSimpleImages.ORIGINAL_VISIBLE_FALSE = ApoNotSoSimpleImages.getImageSimpleRec(Color.ORANGE, null, "-", Color.BLACK);
		arrow = new boolean[7];
		arrow[5] = true;
		ApoNotSoSimpleImages.ORIGINAL_STEP = ApoNotSoSimpleImages.getImageSimple(Color.GREEN.darker().darker(), arrow, null, Color.BLACK);
		arrow = new boolean[7];
		arrow[6] = true;
		ApoNotSoSimpleImages.ORIGINAL_STEP_FINISH = ApoNotSoSimpleImages.getImageSimple(Color.BLUE, arrow, null, Color.BLACK);
	}
	
	private final static BufferedImage getOriginalImageDrive(Color color) {
		BufferedImage image = new BufferedImage(ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.TILE_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = (Graphics2D)image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.BLACK);
		g.fillOval(8, 8, ApoNotSoSimpleConstants.TILE_SIZE - 16, ApoNotSoSimpleConstants.TILE_SIZE - 16);
		g.dispose();
		
		return image;
	}
	
	public final static BufferedImage getImageSimpleRaute(Color color, boolean[] arrows, String value, Color textColor) {
		BufferedImage image = new BufferedImage(ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.TILE_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = (Graphics2D)image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Polygon p = new Polygon();

		p.addPoint(8, image.getHeight()/2);
		p.addPoint(image.getWidth()/2, 8);
		
		p.addPoint(image.getWidth() - 8, image.getHeight()/2);
		p.addPoint(image.getWidth()/2, image.getHeight() - 8);

		Stroke stroke = g.getStroke();

		g.setStroke(stroke);
		g.setColor(color);
		g.fillPolygon(p);

//		g.setStroke(new BasicStroke(2));
		g.setColor(Color.BLACK);
		g.drawPolygon(p);
		
		ApoNotSoSimpleImages.getImageSimple(image, g, arrows, value, textColor);
		
		g.dispose();
		
		return image;
	}
	
	public final static BufferedImage getImageSimpleTriangle(Color color, boolean[] arrows, String value, Color textColor, boolean bDown) {
		BufferedImage image = new BufferedImage(ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.TILE_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = (Graphics2D)image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Polygon p = new Polygon();

		if (!bDown) {
			p.addPoint(5, image.getHeight() - 10);
			p.addPoint(image.getWidth()/2, 5);
			p.addPoint(image.getWidth() - 5, image.getHeight() - 10);
		} else {
			p.addPoint(5, 10);
			p.addPoint(image.getWidth()/2, image.getHeight() - 5);
			p.addPoint(image.getWidth() - 5, 10);			
		}
		Stroke stroke = g.getStroke();

		g.setStroke(stroke);
		g.setColor(color);
		g.fillPolygon(p);
		
//		g.setStroke(new BasicStroke(2));
		g.setColor(Color.BLACK);
		g.drawPolygon(p);

		
		ApoNotSoSimpleImages.getImageSimple(image, g, arrows, value, textColor);
		
		g.dispose();
		
		return image;
	}
	
	public final static BufferedImage getImageSimpleRec(Color color, boolean[] arrows, String value, Color textColor) {
		BufferedImage image = new BufferedImage(ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.TILE_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = (Graphics2D)image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Stroke stroke = g.getStroke();

		g.setStroke(stroke);
		g.setColor(color);
		g.fillRoundRect(11, 11, image.getWidth() - 22, image.getHeight() - 22, 5, 5);
		

//		g.setStroke(new BasicStroke(2));
		g.setColor(Color.BLACK);
		g.drawRoundRect(11, 11, image.getWidth() - 22, image.getHeight() - 22, 5, 5);
		
		ApoNotSoSimpleImages.getImageSimple(image, g, arrows, value, textColor);
		
		g.dispose();
		
		return image;
	}
	
	public final static BufferedImage getImageSimple(Color color, boolean[] arrows, String value, Color textColor) {
		BufferedImage image = new BufferedImage(ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.TILE_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = (Graphics2D)image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(ApoNotSoSimpleImages.ORIGINAL_DRIVE, 0, 0, null);
		g.setColor(color);
		g.fillOval(9, 9, ApoNotSoSimpleConstants.TILE_SIZE - 18, ApoNotSoSimpleConstants.TILE_SIZE - 18);
		
		ApoNotSoSimpleImages.getImageSimple(image, g, arrows, value, textColor);
		
		g.dispose();
		
		return image;
	}
	
	public final static void getImageSimple(BufferedImage image, Graphics2D g, boolean[] arrows, String value, Color textColor) {
		if (arrows != null) {
			if (arrows[6]) {
				g.setColor(textColor);
				int width = 15;
				int height = 15;
				g.fillOval(image.getWidth()/2 - width/2, image.getHeight()/2 - height/2, width, height);
			} else if (arrows[5]) {
				g.setColor(textColor);
				int width = 15;
				int height = 15;
				g.setStroke(new BasicStroke(5));
				g.drawOval(image.getWidth()/2 - width/2, image.getHeight()/2 - height/2, width, height);
			} else if (arrows[4]) {
				g.setColor(textColor);
				g.fillRoundRect(image.getWidth()/2 - 7, 12, 4, image.getHeight() - 30, 10, 10);
				g.fillRoundRect(image.getWidth()/2 + 4, 12, 4, image.getHeight() - 30, 10, 10);
			} else {
				Polygon poly = new Polygon();
				g.setColor(textColor);
				if (arrows[ApoNotSoSimpleConstants.PLAYER_DIRECTION_UP]) {
					poly.addPoint(image.getWidth()/2 - 2, image.getHeight() - 14);
					poly.addPoint(image.getWidth()/2 + 3, image.getHeight() - 14);
					poly.addPoint(image.getWidth()/2 + 3, image.getHeight()/2 + 1);
					poly.addPoint(image.getWidth() - 15, image.getHeight()/2 + 1);
					poly.addPoint(image.getWidth()/2, image.getHeight()/2 - 9);
					poly.addPoint(15, image.getHeight()/2 + 1);
					poly.addPoint(image.getWidth()/2 - 2, image.getHeight()/2 + 1);
				} else if (arrows[ApoNotSoSimpleConstants.PLAYER_DIRECTION_DOWN]) {
					poly.addPoint(image.getWidth()/2 - 2, 15);
					poly.addPoint(image.getWidth()/2 + 3, 15);
					poly.addPoint(image.getWidth()/2 + 3, image.getHeight()/2);
					poly.addPoint(image.getWidth() - 15, image.getHeight()/2);
					poly.addPoint(image.getWidth()/2, image.getHeight()/2 + 10);
					poly.addPoint(15, image.getHeight()/2);
					poly.addPoint(image.getWidth()/2 - 2, image.getHeight()/2);
				} else if (arrows[ApoNotSoSimpleConstants.PLAYER_DIRECTION_LEFT]) {
					poly.addPoint(image.getWidth()/2 - 2, image.getHeight() - 15);
					poly.addPoint(image.getWidth()/2 + 3, image.getHeight() - 15);
					poly.addPoint(image.getWidth()/2 + 3, image.getHeight()/2);
					poly.addPoint(image.getWidth() - 15, image.getHeight()/2);
					poly.addPoint(image.getWidth()/2, image.getHeight()/2 - 10);
					poly.addPoint(15, image.getHeight()/2);
					poly.addPoint(image.getWidth()/2 - 2, image.getHeight()/2);
				} else if (arrows[ApoNotSoSimpleConstants.PLAYER_DIRECTION_RIGHT]) {
					poly.addPoint(image.getWidth()/2 - 2, 15);
					poly.addPoint(image.getWidth()/2 + 3, 15);
					poly.addPoint(image.getWidth()/2 + 3, image.getHeight()/2);
					poly.addPoint(image.getWidth() - 15, image.getHeight()/2);
					poly.addPoint(image.getWidth()/2, image.getHeight()/2 + 10);
					poly.addPoint(15, image.getHeight()/2);
					poly.addPoint(image.getWidth()/2 - 2, image.getHeight()/2);
				}
				if (poly.npoints > 0) {
					g.fillPolygon(poly);
				}
			}
		} else if (value != null) {
			g.setColor(textColor);
			g.setFont(ApoNotSoSimpleConstants.FONT_IMAGE);
			int w = g.getFontMetrics().stringWidth(value);
			int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
			g.drawString(value, image.getWidth()/2 - w/2, image.getHeight()/2 + h/2);
		}
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
	public BufferedImage getButtonImageLevelChooser(int width, int height, String text, Color background, Color font, Color border, Color over, Color pressed, Font writeFont, int round) {
		BufferedImage iButton = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(width,height,BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = (Graphics2D)iButton.getGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Font fontOkButton = writeFont;
		g.setFont(fontOkButton);
		int h = g.getFontMetrics().getHeight();
		
		for ( int i = 0; i < 3; i++ ) {
			g.setColor( background );
			g.fillRoundRect((width)/3 * i + 1, 1, (width)/3 - 2, height - 1, round, round);
			
			g.setFont( fontOkButton );
			int w = g.getFontMetrics().stringWidth( text );
			int x = iButton.getWidth()/3;
			
			if ( i == 1 ) {
				g.setColor(over);
				g.fillRoundRect(i*x + x/2 - w/2, iButton.getHeight()/2 - h/4 + 1, w, h/2 - 2, 20, 20);
			} else if ( i == 2 ) {
				g.setColor(pressed);
				g.fillRoundRect(i*x + x/2 - w/2, iButton.getHeight()/2 - h/4 + 1, w, h/2 - 2, 20, 20);
			}
			
			g.setColor( font );
			g.drawString( text, i*x + x/2 - w/2, iButton.getHeight()/2 + h/3 );
			
			Stroke stroke = g.getStroke();
			g.setStroke(new BasicStroke(3));
			g.setColor( border );
			g.drawRoundRect( (width)/3 * i + 1, 1, (width)/3 - 3, height - 3, round, round );
			g.setStroke(stroke);
		}
		
		g.dispose();
		return iButton;
	}
	
	public final static BufferedImage getImageSimpleEditor(BufferedImage iSimpleImage, Color over, Color pressed) {
		BufferedImage iImage = new BufferedImage(iSimpleImage.getWidth() * 3, iSimpleImage.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = iImage.createGraphics();
		
		int width = iSimpleImage.getWidth();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setStroke(new BasicStroke(5));
		
		for (int i = 0; i < 3; i++) {
			g.drawImage(iSimpleImage, i * width, 0, null);
			if (i > 0) {
				if (i == 1) {
					g.setColor(over);
				} else if (i == 2) {
					g.setColor(pressed);
				}
				g.drawOval(8 + i * width, 8, ApoNotSoSimpleConstants.TILE_SIZE - 16, ApoNotSoSimpleConstants.TILE_SIZE - 16);
			}
		}
		
		g.dispose();
		return iImage;
	}

	public ApoNotSoSimpleImages() {
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
		return this.image.getButtonImage(width, height, text, c, c2, c3, new Color(254, 254, 0, 140), new Color(254, 0, 0, 140), font, round);
	}
	
	public BufferedImage getButtonImage(int width, int height, String text, Color c, Color c2, Color c3, Color mouseOver, Color mousePressed, Font font, int round) {
		return this.image.getButtonImage(width, height, text, c, c2, c3, mouseOver, mousePressed, font, round);
	}
	
	public BufferedImage getButtonImageSimple(int width, int height, String text, Color background, Color font, Color border, Color over, Color pressed, boolean bLeft, boolean bRight, Font writeFont, int round) {
		BufferedImage iButton = this.getButtonImage(width, height, text, background, font, border, over, pressed, writeFont, round);
		Graphics2D g = (Graphics2D)iButton.getGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(Color.BLACK);
		Stroke stroke = g.getStroke();
		Shape shape = g.getClip();
		
		for (int i = 0; i < 3; i++) {
			int startX = 6 + width * i / 3;
			int startY = 6;
			int myHeight = height - 2 * startY;
			g.setStroke(new BasicStroke(7));
			if (bLeft) {
				g.setClip(new Rectangle2D.Float(startX, startY, myHeight/2, myHeight));
				g.drawOval(startX + 3, startY + 3, myHeight - 7, myHeight - 7);
			}
			if (bRight) {
				g.setClip(new Rectangle2D.Float(width * (i + 1) / 3 - startY - myHeight/2, startY, myHeight/2, myHeight));
				g.drawOval(width * (i + 1) / 3 - startY - myHeight, startY + 3, myHeight - 7, myHeight - 7);
			}
			g.setStroke(stroke);
			g.setClip(shape);
		}
		
		g.dispose();
		return iButton;
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
