package apoSoccer;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import org.apogames.image.ApoImageFromValue;

/**
 * Klasse um Bilder aus bestimmten Werten zu erstellen und zu laden
 * @author Dirk Aporius
 *
 */
public class ApoSoccerImages {
	/** HilfsObjekt, um vorgefertigte Bilder zu laden (z.B. für Buttons) */
	private ApoImageFromValue image;
	
	public ApoSoccerImages() {
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
	 * @return Logo für das Spiel
	 */
	public BufferedImage getLogo( int width, int height ) {
		BufferedImage iLogo = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( width, height, BufferedImage.TRANSLUCENT );
		Graphics2D g	= (Graphics2D)iLogo.getGraphics();

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor( Color.white );
		g.fillOval( 1, 1, width - 2, height - 2);
		g.setColor( Color.black );
		g.drawOval( 1, 1, width - 2, height - 2);
		
		g.dispose();
		
		return iLogo;
	}
	
	/**
	 * gibt das Bild für die Analyse zurück
	 * @param iBackground : das eigentliche Spielfeld als Bild
	 * @param width : Breite des Bildes
	 * @param height : Höhe des Bildes
	 * @param iEmblemOne : Wappen des 1 Teams
	 * @param iEmblemTwo : Wappen des 2 Teams
	 * @return AnalyseBild
	 */
	public BufferedImage getAnalysis( BufferedImage iBackground, int width, int height, BufferedImage iEmblemOne, BufferedImage iEmblemTwo ) {
		BufferedImage iAnalysis = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( width, height, BufferedImage.TYPE_INT_RGB );
		Graphics2D g	= (Graphics2D)iAnalysis.getGraphics();

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.drawImage(iBackground, 0, 0, null);
		
		g.setColor( new Color(0, 0, 0, 230) );
		int startX = (ApoSoccerConstants.GAME_FIELD_WIDTH - ApoSoccerConstants.ANALYSIS_WIDTH) / 2;
		int startY = (ApoSoccerConstants.GAME_FIELD_HEIGHT - ApoSoccerConstants.ANALYSIS_HEIGHT) / 2;
		g.fillRoundRect(startX, startY, ApoSoccerConstants.ANALYSIS_WIDTH, ApoSoccerConstants.ANALYSIS_HEIGHT, 10, 10);
		g.setColor(Color.WHITE);
		g.setStroke(new BasicStroke(4));
		g.drawRoundRect(startX - 2, startY - 2, ApoSoccerConstants.ANALYSIS_WIDTH + 2, ApoSoccerConstants.ANALYSIS_HEIGHT + 2, 10, 10);
		
		int changeX = ApoSoccerConstants.ANALYSIS_WIDTH * 1 / 4;
		if (iEmblemOne != null) {
			g.drawImage( iEmblemOne, startX + changeX - iEmblemOne.getWidth()/2, startY + 150, null);
		}
		
		if (iEmblemTwo != null) {
			g.drawImage( iEmblemTwo, startX + ApoSoccerConstants.ANALYSIS_WIDTH - changeX - iEmblemTwo.getWidth()/2, startY + 150, null);
		}
		
		g.dispose();
		
		return iAnalysis;
	}
	
	/**
	 * gibt das Bild für das Menubild zurück
	 * @param iBackground : das eigentliche Spielfeld als Bild
	 * @param width : Breite des Bildes
	 * @param height : Höhe des Bildes
	 * @return MenuBild
	 */
	public BufferedImage getMenu(BufferedImage iBackground, int width, int height) {
		BufferedImage iMenu = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( width, height, BufferedImage.TYPE_INT_RGB );
		Graphics2D g	= (Graphics2D)iMenu.getGraphics();

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.drawImage(iBackground, 0, 0, null);
		
		g.setColor( new Color(0, 0, 0, 230) );
		int startX = (ApoSoccerConstants.GAME_FIELD_WIDTH - ApoSoccerConstants.ANALYSIS_WIDTH) / 2;
		int startY = (ApoSoccerConstants.GAME_FIELD_HEIGHT - ApoSoccerConstants.ANALYSIS_HEIGHT) / 2;
		g.fillRoundRect(startX, startY, ApoSoccerConstants.ANALYSIS_WIDTH, ApoSoccerConstants.ANALYSIS_HEIGHT, 10, 10);
		g.setColor(Color.WHITE);
		g.setStroke(new BasicStroke(4));
		g.drawRoundRect(startX - 2, startY - 2, ApoSoccerConstants.ANALYSIS_WIDTH + 2, ApoSoccerConstants.ANALYSIS_HEIGHT + 2, 10, 10);
		
		g.setColor(Color.white);
		g.setFont(ApoSoccerConstants.FONT_SCORE);
		drawString(g, "Menu", startX + ApoSoccerConstants.ANALYSIS_WIDTH/2, startY + 25, 0);

		g.setColor(Color.WHITE);
		g.setStroke(new BasicStroke(1));
		g.drawLine(startX + 60, startY + 30, startX + ApoSoccerConstants.ANALYSIS_WIDTH - 60, startY + 30);
		g.drawLine(startX + 10, startY + ApoSoccerConstants.ANALYSIS_HEIGHT/2, startX + ApoSoccerConstants.ANALYSIS_WIDTH - 10, startY + ApoSoccerConstants.ANALYSIS_HEIGHT/2);
		g.drawLine(startX + 10, startY + ApoSoccerConstants.ANALYSIS_HEIGHT/2 + 60, startX + ApoSoccerConstants.ANALYSIS_WIDTH - 10, startY + ApoSoccerConstants.ANALYSIS_HEIGHT/2 + 60);
		g.drawLine(startX + 10, startY + ApoSoccerConstants.ANALYSIS_HEIGHT/2 + 120, startX + ApoSoccerConstants.ANALYSIS_WIDTH - 10, startY + ApoSoccerConstants.ANALYSIS_HEIGHT/2 + 120);
		
		g.drawLine(startX + 150, startY + ApoSoccerConstants.ANALYSIS_HEIGHT/2 + 121, startX + 150, startY + ApoSoccerConstants.ANALYSIS_HEIGHT/2 + 190);
		g.drawLine(startX + 250, startY + ApoSoccerConstants.ANALYSIS_HEIGHT/2 + 121, startX + 250, startY + ApoSoccerConstants.ANALYSIS_HEIGHT/2 + 190);
		
		g.setFont(ApoSoccerConstants.FONT_TEAM_NAME);
		drawString(g, "Replay", startX + ApoSoccerConstants.ANALYSIS_WIDTH/2, startY + ApoSoccerConstants.ANALYSIS_HEIGHT/2 + 20, 0);

		drawString(g, "Simulate", startX + ApoSoccerConstants.ANALYSIS_WIDTH/2, startY + ApoSoccerConstants.ANALYSIS_HEIGHT/2 + 80, 0);
		
		g.drawLine(startX + 10, startY + ApoSoccerConstants.ANALYSIS_HEIGHT/2 - 40, startX + ApoSoccerConstants.ANALYSIS_WIDTH - 10, startY + ApoSoccerConstants.ANALYSIS_HEIGHT/2 - 40);
		
		drawString(g, "Sound", startX + 30, startY + ApoSoccerConstants.ANALYSIS_HEIGHT/2 - 15, 0);
		
		this.drawString(g, "DebugMode", startX + 75, startY + ApoSoccerConstants.ANALYSIS_HEIGHT - 60, 0);
		this.drawString(g, "Bird Eye View", ApoSoccerConstants.GAME_FIELD_WIDTH - startX - 75, startY + ApoSoccerConstants.ANALYSIS_HEIGHT - 60, 0);
		
		g.dispose();
		
		return iMenu;
	}
	
	/**
	 * gibt das komplette Spielfeld wieder (mit Umrandung)
	 * @param iGras : Bild mit dem Gras
	 * @param width : Breite des Bildes was erstellt wird
	 * @param height : Höhe des Bildes was erstellt wird
	 * @return das komplette Spielfeld
	 */
	public BufferedImage getGameFieldBackgroundComplete( BufferedImage iGras, BufferedImage iLeft, BufferedImage iRight, BufferedImage iUp, BufferedImage iDown, BufferedImage iUpLittle, BufferedImage iDownLittle, BufferedImage iMiddleLittle, BufferedImage iUpUpLittle, BufferedImage iDownDownLittle, int width, int height ) {
		BufferedImage iBackground = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( width, height, BufferedImage.TYPE_INT_RGB );
		Graphics2D g	= (Graphics2D)iBackground.getGraphics();

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		BufferedImage iCompleteGras = this.getGameFieldBackground(iGras, ApoSoccerConstants.COMPLETE_FIELD_WIDTH, ApoSoccerConstants.COMPLETE_FIELD_HEIGHT);
		// Feld malen
		g.drawImage(iCompleteGras, 0, 0, null);
		
		// Umrandung malen
		//g.setColor(Color.gray);
		//g.fillRect(0, 0, width - 1, ApoSoccerConstants.BOARD_HEIGHT );
		//g.fillRect(0, height - ApoSoccerConstants.BOARD_HEIGHT - 1, width - 1, ApoSoccerConstants.BOARD_HEIGHT );
		
		int startX = 0;
		if (iUp != null) {
			if (iUp.getWidth() < width) {
				startX = (width - iUp.getWidth())/2;
			}
			g.drawImage(iUp, startX, 0, null);
		}
		startX = 0;
		if (iDown != null) {
			if (iDown.getWidth() < width) {
				startX = (width - iDown.getWidth())/2;
			}
			g.drawImage(iDown, startX, height - 1 - ApoSoccerConstants.BOARD_HEIGHT, null);
		}
		g.drawImage(iLeft, 0, ApoSoccerConstants.BOARD_HEIGHT, null);
		g.drawImage(iRight, width - 1 - ApoSoccerConstants.BOARD_WIDTH, ApoSoccerConstants.BOARD_HEIGHT, null);
		//g.fillRect(width - 1 - ApoSoccerConstants.BOARD_WIDTH, 0, ApoSoccerConstants.BOARD_WIDTH, height - 1);
		//g.fillRect(0, 0, ApoSoccerConstants.BOARD_WIDTH - 1, height - 1);
		
		// Tore malen
		g.drawImage(iCompleteGras.getSubimage(ApoSoccerConstants.BOARD_WIDTH - 50, height/2 - ApoSoccerConstants.GOAL_HEIGHT/2 - 1, 50, ApoSoccerConstants.GOAL_HEIGHT), ApoSoccerConstants.BOARD_WIDTH - 50, height/2 - ApoSoccerConstants.GOAL_HEIGHT/2 - 1, null);
		g.drawImage(iCompleteGras.getSubimage(width - ApoSoccerConstants.BOARD_WIDTH - 1, height/2 - ApoSoccerConstants.GOAL_HEIGHT/2 - 1, 50, ApoSoccerConstants.GOAL_HEIGHT), width - ApoSoccerConstants.BOARD_WIDTH - 1, height/2 - ApoSoccerConstants.GOAL_HEIGHT/2 - 1, null);
		
		g.setColor(Color.white);
		g.setStroke(new BasicStroke(1));
		for (int i = 0; i < 10; i++) {
			g.drawLine(ApoSoccerConstants.BOARD_WIDTH - 50 + i * 5, height/2 - ApoSoccerConstants.GOAL_HEIGHT/2, ApoSoccerConstants.BOARD_WIDTH - 50 + i * 5, height/2 + ApoSoccerConstants.GOAL_HEIGHT/2);
			g.drawLine(width - ApoSoccerConstants.BOARD_WIDTH + i * 5, height/2 - ApoSoccerConstants.GOAL_HEIGHT/2, width - ApoSoccerConstants.BOARD_WIDTH + i * 5, height/2 + ApoSoccerConstants.GOAL_HEIGHT/2);
		}
		
		for (int i = 0; i < 20; i++) {
			g.drawLine(ApoSoccerConstants.BOARD_WIDTH - 50, height/2 - ApoSoccerConstants.GOAL_HEIGHT/2 + i * ApoSoccerConstants.GOAL_HEIGHT / 20, ApoSoccerConstants.BOARD_WIDTH, height/2 - ApoSoccerConstants.GOAL_HEIGHT/2 + i * ApoSoccerConstants.GOAL_HEIGHT / 20);
			g.drawLine(width - ApoSoccerConstants.BOARD_WIDTH, height/2 - ApoSoccerConstants.GOAL_HEIGHT/2 + i * ApoSoccerConstants.GOAL_HEIGHT / 20, width - ApoSoccerConstants.BOARD_WIDTH + 50, height/2 - ApoSoccerConstants.GOAL_HEIGHT/2 + i * ApoSoccerConstants.GOAL_HEIGHT / 20);
		}
		
		g.setStroke(new BasicStroke(3));
		g.setColor(Color.lightGray);
		g.drawRect( ApoSoccerConstants.BOARD_WIDTH - 50 - 3, height/2 - ApoSoccerConstants.GOAL_HEIGHT/2 - 1, 50, ApoSoccerConstants.GOAL_HEIGHT );
		g.drawRect( width - ApoSoccerConstants.BOARD_WIDTH, height/2 - ApoSoccerConstants.GOAL_HEIGHT/2 - 1, 50, ApoSoccerConstants.GOAL_HEIGHT );
		
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
		if (iMiddleLittle != null) {
			g.drawImage(iMiddleLittle, width/2 - iMiddleLittle.getWidth()/2, height/2 - iMiddleLittle.getHeight()/2, null);
		}
		if (iUpLittle != null) {
			g.drawImage(iUpLittle, width/2 - iUpLittle.getWidth()/2, height/2 - 80 - iUpLittle.getHeight(), null);
		}
		if (iDownLittle != null) {
			g.drawImage(iDownLittle, width/2 - iDownLittle.getWidth()/2, height/2 + 80, null);
		}
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		if (iUpUpLittle != null) {
			g.drawImage(iUpUpLittle, width/2 - iUpUpLittle.getWidth()/2, height/2 - 170 - iUpUpLittle.getHeight(), null);
		}
		if (iDownDownLittle != null) {
			g.drawImage(iDownDownLittle, width/2 - iDownDownLittle.getWidth()/2, height/2 + 170, null);
		}
		
		g.dispose();
		
		return iBackground;
	}
	
	/**
	 * malt den Rasen auf das Spielfeld
	 * @param iGras : Bild mit dem Rasen
	 * @param width : Breite des Spielfeldes
	 * @param height : Höhe des Spielfeldes
	 * @return das eigentliche SPielfeld
	 */
	private BufferedImage getGameFieldBackground(BufferedImage iGras, int width, int height) {
		BufferedImage iBackground = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( width, height, BufferedImage.TYPE_INT_RGB );
		Graphics2D g	= (Graphics2D)iBackground.getGraphics();

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		for (int i = 0; i < (width / iGras.getWidth()) + 1; i++) {
			for (int j = 0; j < (height / iGras.getHeight()) + 1; j++) {
				g.drawImage( iGras, i * iGras.getWidth(), j * iGras.getHeight(), null );
			}
		}
		/*
		//Rasen
		g.setColor( Color.green );
		g.fillRect( 0, 0, width - 1, height - 1 );
		
		// Dreck auf dem Rasen
		int randomDirt = (int)( Math.random() * 3000 + 4000 );
		for (int i = 0; i < randomDirt; i++) {
			int randomX = (int)( Math.random() * width );
			int randomY = (int)( Math.random() * height );
			g.setColor(new Color(10, 125, 10, (int)(Math.random() * 40 + 20)));
			g.fillOval(randomX, randomY, (int)( Math.random() * 30 ), (int)( Math.random() * 30 ));
		}*/
		
		width = width - ApoSoccerConstants.BOARD_WIDTH * 2;
		height = height - ApoSoccerConstants.BOARD_HEIGHT * 2;
		int x = ApoSoccerConstants.BOARD_WIDTH;
		int y = ApoSoccerConstants.BOARD_HEIGHT;
		
		// Außenlinie
		g.setStroke(new BasicStroke(4));
		g.setColor( Color.white );
		g.drawRect( x + 1, y + 1, width - 3, height - 3 );
		
		// Torraum
		g.drawRect( x + 1, y + height/2 - ApoSoccerConstants.PENALTY_AREA_HEIGHT/2 - 2, ApoSoccerConstants.PENALTY_AREA_WIDTH, ApoSoccerConstants.PENALTY_AREA_HEIGHT );
		g.drawRect( x + width - ApoSoccerConstants.PENALTY_AREA_WIDTH - 2, y + height/2 - ApoSoccerConstants.PENALTY_AREA_HEIGHT/2 - 2, ApoSoccerConstants.PENALTY_AREA_WIDTH, ApoSoccerConstants.PENALTY_AREA_HEIGHT );
		
		// Mittellinie
		g.drawLine(x + width/2, y + 0, x + width/2, y + height - 1);
		int ovalWidth = ApoSoccerConstants.MIDDLE_CIRCLE_WIDTH;
		g.drawOval(x + width/2 - ovalWidth/2, y + height/2 - ovalWidth/2, ovalWidth, ovalWidth );
		
		g.dispose();
		
		return iBackground;
	}

	/**
	 * erstellt das Menuimage und gibt es zurück
	 * @param width : Breite des MenuImages
	 * @param height : Höhe des MenuImages
	 * @return das MenuImage
	 */
	public BufferedImage getMenuBackground(int width, int height, BufferedImage iGras) {
		BufferedImage iBackground = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( width, height, BufferedImage.TYPE_INT_RGB );
		Graphics2D g	= (Graphics2D)iBackground.getGraphics();

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		/*g.setColor(Color.green);
		g.fillRect(0, 0, width - 1, height - 1);*/
		
		for (int i = 0; i <= width/iGras.getWidth(); i++) {
			for (int j = 0; j <= height/iGras.getHeight(); j++) {
				g.drawImage(iGras, i * iGras.getWidth(), j * iGras.getHeight(), null);
			}
		}
		
		g.setColor(Color.black);
		g.fillRect(3, 3, width - 6, 80);
		
		g.setColor( Color.white );
		g.fillRect(0, ApoSoccerConstants.TICKER_Y, ApoSoccerConstants.TICKER_WIDTH, ApoSoccerConstants.TICKER_HEIGHT);
		g.setColor(Color.black);
		g.drawRect(2, ApoSoccerConstants.TICKER_Y, width - 6, ApoSoccerConstants.TICKER_HEIGHT);
		this.drawString(g, "Live-Ticker", ApoSoccerConstants.TICKER_WIDTH/2, ApoSoccerConstants.TICKER_Y + 15, 0);
		g.drawLine(10, ApoSoccerConstants.TICKER_Y + 20, width - 10, ApoSoccerConstants.TICKER_Y + 20);
		
		g.setStroke(new BasicStroke(4));
		g.setColor( Color.white );
		g.drawRect( 0, 0, width - 1, height - 1 );
		g.setStroke(new BasicStroke(2));
		g.drawOval(20, 50, 25, 25);
		g.drawLine(32, 62, 32, 50);
		g.drawLine(32, 62, 39, 62);
		
		g.drawOval(width - 45, 50, 25, 25);
		g.drawLine(width - 45 + 12, 62, width - 45 + 12, 50);
		g.drawLine(width - 45 + 12, 62, width - 45 + 5, 62);
		
		g.dispose();
		
		return iBackground;
	}
	
	/**
	 * erstellt das Replayimage und gibt es zurück
	 * @param width : Breite des MenuImages
	 * @param height : Höhe des MenuImages
	 * @return das MenuImage
	 */
	public BufferedImage getReplay(int width, int height) {
		BufferedImage iReplay = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( width, height, BufferedImage.TRANSLUCENT );
		Graphics2D g	= (Graphics2D)iReplay.getGraphics();

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setFont(ApoSoccerConstants.FONT_SCORE_ANAYLSIS);
		g.setColor(Color.white);
		this.drawString(g, "Replay", width/2, height - 6, 0);
		g.setColor(Color.black);
		this.drawString(g, "Replay", width/2 - 1, height - 7, 0);
		
		g.dispose();
		
		return iReplay;
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
	
	public BufferedImage getButtonReplayPlay(int width, int height, Color c, Color c2, Color c3) {
		BufferedImage iPlay = this.image.getButtonImage(width, height, "    ", c, c2, c3, 5);
		
		Graphics2D g = (Graphics2D)iPlay.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		for (int i = 0; i < 3; i++) {
			Polygon p = new Polygon();
			int startX = width/3 * i + width/6 - 5;
			int startY = height / 2 - 5;
			p.addPoint(startX, startY);
			p.addPoint(startX, startY + 10);
			p.addPoint(startX + 10, startY + 5);
			g.setColor(Color.white);
			g.fillPolygon(p);
		}
		
		g.dispose();
		return iPlay;
	}

	public BufferedImage getButtonReplayForward(int width, int height, Color c, Color c2, Color c3) {
		BufferedImage iPlay = this.image.getButtonImage(width, height, "    ", c, c2, c3, 5);
		
		Graphics2D g = (Graphics2D)iPlay.getGraphics();
		g.setColor(Color.white);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);		
		
		for (int i = 0; i < 3; i++) {
			Polygon p = new Polygon();
			int startX = width/3 * i + width/6 - 7;
			int startY = height / 2 - 5;
			p.addPoint(startX, startY);
			p.addPoint(startX, startY + 10);
			p.addPoint(startX + 7, startY + 5);
			g.fillPolygon(p);
			p = new Polygon();
			p.addPoint(startX + 7, startY);
			p.addPoint(startX + 7, startY + 10);
			p.addPoint(startX + 14, startY + 5);
			g.fillPolygon(p);
		}
		
		g.dispose();
		return iPlay;
	}

	public BufferedImage getButtonReplayForwardFast(int width, int height, Color c, Color c2, Color c3) {
		BufferedImage iPlay = this.image.getButtonImage(width, height, "    ", c, c2, c3, 5);
		
		Graphics2D g = (Graphics2D)iPlay.getGraphics();
		g.setColor(Color.white);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);		
		
		for (int i = 0; i < 3; i++) {
			Polygon p = new Polygon();
			int startX = width/3 * i + width/6 - 8;
			int startY = height / 2 - 5;
			p.addPoint(startX, startY);
			p.addPoint(startX, startY + 10);
			p.addPoint(startX + 6, startY + 5);
			g.fillPolygon(p);
			p = new Polygon();
			p.addPoint(startX + 5, startY);
			p.addPoint(startX + 5, startY + 10);
			p.addPoint(startX + 11, startY + 5);
			g.fillPolygon(p);
			p = new Polygon();
			p.addPoint(startX + 10, startY);
			p.addPoint(startX + 10, startY + 10);
			p.addPoint(startX + 16, startY + 5);
			g.fillPolygon(p);
		}
		
		g.dispose();
		return iPlay;
	}

	public BufferedImage getButtonReplayRewind(int width, int height, Color c, Color c2, Color c3) {
		BufferedImage iPlay = this.image.getButtonImage(width, height, "    ", c, c2, c3, 5);
		
		Graphics2D g = (Graphics2D)iPlay.getGraphics();
		g.setColor(Color.white);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);		
		
		for (int i = 0; i < 3; i++) {
			Polygon p = new Polygon();
			int startX = width/3 * i + width/6 - 7;
			int startY = height / 2 - 5;
			p.addPoint(startX + 14, startY);
			p.addPoint(startX + 14, startY + 10);
			p.addPoint(startX + 7, startY + 5);
			g.fillPolygon(p);
			p = new Polygon();
			p.addPoint(startX + 7, startY);
			p.addPoint(startX + 7, startY + 10);
			p.addPoint(startX + 0, startY + 5);
			g.fillPolygon(p);
		}
		
		g.dispose();
		return iPlay;
	}

	public BufferedImage getButtonReplayRewindFast(int width, int height, Color c, Color c2, Color c3) {
		BufferedImage iPlay = this.image.getButtonImage(width, height, "    ", c, c2, c3, 5);
		
		Graphics2D g = (Graphics2D)iPlay.getGraphics();
		g.setColor(Color.white);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);		
		
		for (int i = 0; i < 3; i++) {
			Polygon p = new Polygon();
			int startX = width/3 * i + width/6 - 8;
			int startY = height / 2 - 5;
			p.addPoint(startX + 6, startY);
			p.addPoint(startX + 6, startY + 10);
			p.addPoint(startX, startY + 5);
			g.fillPolygon(p);
			p = new Polygon();
			p.addPoint(startX + 11, startY);
			p.addPoint(startX + 11, startY + 10);
			p.addPoint(startX + 5, startY + 5);
			g.fillPolygon(p);
			p = new Polygon();
			p.addPoint(startX + 16, startY);
			p.addPoint(startX + 16, startY + 10);
			p.addPoint(startX + 10, startY + 5);
			g.fillPolygon(p);
		}
		
		g.dispose();
		return iPlay;
	}

	
	public BufferedImage getButtonReplayPause(int width, int height, Color c, Color c2, Color c3) {
		BufferedImage iPause = this.image.getButtonImage(width, height, "    ", c, c2, c3);
		
		Graphics2D g = (Graphics2D)iPause.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		for (int i = 0; i < 3; i++) {
			g.setStroke(new BasicStroke(3));
			int startX = width/3 * i + width/6 - 5;
			int startY = height / 2 - 5;
			g.setColor(Color.white);
			g.drawLine(startX, startY, startX, startY + 10);
			g.drawLine(startX + 8, startY, startX + 8, startY + 10);
		}
		
		g.dispose();
		return iPause;
	}
	
	public BufferedImage getPlayerWithHair( BufferedImage iPlayer, BufferedImage iHair ) {
		if (iHair == null) {
			return iPlayer;
		} else {
			BufferedImage iNewPlayer = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( iPlayer.getWidth(), iPlayer.getHeight(), BufferedImage.TRANSLUCENT );
			Graphics2D g	= (Graphics2D)iNewPlayer.getGraphics();

			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			g.drawImage( iPlayer, 0, 0, null );
			g.drawImage( iHair, 0, 0, null );
			
			g.dispose();
			
			return iNewPlayer;
		}
	}
	
	public static BufferedImage getImageCopy(BufferedImage iImage) {
		BufferedImage iNewImage = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( iImage.getWidth(), iImage.getHeight(), iImage.getTransparency() );
		Graphics2D g	= (Graphics2D)iNewImage.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.drawImage( iImage, 0, 0, null );
			
		g.dispose();
			
		return iNewImage;
	}
	
}
