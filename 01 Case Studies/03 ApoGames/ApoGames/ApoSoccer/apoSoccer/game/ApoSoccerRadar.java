package apoSoccer.game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import apoSoccer.ApoSoccerConstants;

/**
 * Klasse die das Radar repräsentiert
 * @author Dirk Aporius
 *
 */
public class ApoSoccerRadar {

	/** x-Koordinate des Radars */
	private int x;
	/** y-Koordinate des Radars */
	private int y;
	/** Breite des Radars */
	private int width;
	/** Höhe des Radars */
	private int height;
	
	private int movementWidth;
	private int movementHeight;
	/** das verkleinerte Bild */
	private BufferedImage iLittleMap;
	/** das Spielobjekt */
	private ApoSoccerGame game;
	
	public ApoSoccerRadar(ApoSoccerGame game, int x, int y, int width, int height) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		//System.out.println(width+" "+height);
		
		this.iLittleMap = this.getLittleMap( this.getLittleField(this.game.getIBackground()), this.width, this.height );
	}
	
	/**
	 * 
	 * @param iLevel
	 * @return
	 */
	private BufferedImage getLittleField( BufferedImage iLevel ) {
		BufferedImage iLittleMap = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( ApoSoccerConstants.FIELD_WIDTH, ApoSoccerConstants.FIELD_HEIGHT, BufferedImage.TRANSLUCENT );
		Graphics2D g	= (Graphics2D)iLittleMap.getGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.drawImage( iLevel, 0, 0, iLittleMap.getWidth(), iLittleMap.getHeight(), ApoSoccerConstants.BOARD_WIDTH, ApoSoccerConstants.BOARD_HEIGHT, iLittleMap.getWidth() + ApoSoccerConstants.BOARD_WIDTH, iLittleMap.getHeight() + ApoSoccerConstants.BOARD_HEIGHT, null );
				
		g.dispose();
		
		return iLittleMap;
	}
	
	private BufferedImage getLittleMap( BufferedImage iLevel, int width, int height ) {
		BufferedImage iLittleMap = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( width, height, BufferedImage.TRANSLUCENT );
		Graphics2D g	= (Graphics2D)iLittleMap.getGraphics();

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Image image = null;
		if ( (float)iLevel.getWidth()/(float)width > (float)iLevel.getHeight()/(float)height ) {
			float delta = (float)iLevel.getWidth()/(float)width;
			image = iLevel.getScaledInstance( width, (int)( iLevel.getHeight()/delta ), Image.SCALE_SMOOTH );
		} else {
			float delta = (float)iLevel.getHeight()/(float)height;
			image = iLevel.getScaledInstance( (int)( iLevel.getWidth()/delta ), height, Image.SCALE_SMOOTH );
		}
		this.movementWidth = ( width - image.getWidth(null) ) / 2;
		this.movementHeight = ( height - image.getHeight(null) ) / 2;
		
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
		g.drawImage( image, this.movementWidth, this.movementHeight, null );
				
		g.dispose();
		
		return iLittleMap;
	}
	
	/**
	 * gibt die X-Koordinate des Radars zurück
	 * @return gibt die X-Koordinate des Radars zurück
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * setzt die X-Koordinate des Radars auf den übergebenen Wert
	 * @param x : neuer x-Wert des Radars
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * gibt die Y-Koordinate des Radars zurück
	 * @return gibt die Y-Koordinate des Radars zurück
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * setzt die y-Koordinate des Radars auf den übergebenen Wert
	 * @param x : neuer y-Wert des Radars
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * gibt die Weite des Radars zurück
	 * @return gibt die Weite des Radars zurück
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * setzt die Breite des Radars auf den übergebenen Wert
	 * @param x : neue Breite des Radars
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * gibt die Höhe des Radars zurück
	 * @return gibt die Höhe des Radars zurück
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * setzt die Höhe des Radars auf den übergebenen Wert
	 * @param x : neue Höhe des Radars
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * malt das Radar hin
	 * @param g : das Graphics Objekt
	 * @param changeX : Verschiebung in x-Richtung
	 * @param changeY : Verschiebung in y-Richtung
	 */
	public void render( Graphics2D g, int changeX, int changeY ) {
		if ( this.iLittleMap != null ) {
			g.drawImage( this.iLittleMap, this.getX() + changeX, this.getY() + changeY, null );
			this.renderMoveableThings( g, changeX, changeY );
		}
	}
	
	/**
	 * malt die Spieler und den Ball auf das Bild
	 * @param g : das Graphics Objekt
	 * @param changeX : Verschiebung in x-Richtung
	 * @param changeY : Verschiebung in y-Richtung
	 */
	private void renderMoveableThings( Graphics2D g, float changeX, float changeY ) {
		if (this.game.getHomeTeam() != null) {
			for (int i = 0; i < this.game.getHomeTeam().getPlayers().size(); i++) {
				g.setColor(Color.red);
				g.fillOval((int)(changeX - 2 + this.getX() + this.game.getHomeTeam().getPlayer(i).getX() * ApoSoccerConstants.DELTA_RADAR), (int)(changeY - 2 + this.getY() + this.game.getHomeTeam().getPlayer(i).getY() * ApoSoccerConstants.DELTA_RADAR), (int)(5), (int)(5));
				if (this.game.getHomeTeam().getPlayers().get(i).isBPlayerHuman()) {
					g.setColor(Color.yellow);
					g.drawOval((int)(changeX - 2 + this.getX() - 1 + this.game.getHomeTeam().getPlayer(i).getX() * ApoSoccerConstants.DELTA_RADAR), (int)(changeY - 2 - 1 + this.getY() + this.game.getHomeTeam().getPlayer(i).getY() * ApoSoccerConstants.DELTA_RADAR), (int)(7), (int)(7));		
				}
				g.setColor(Color.black);
				g.drawLine((int)(changeX + 1 + this.getX() + this.game.getHomeTeam().getPlayer(i).getX() * ApoSoccerConstants.DELTA_RADAR), (int)(changeY + 1 + this.getY() + this.game.getHomeTeam().getPlayer(i).getY() * ApoSoccerConstants.DELTA_RADAR), (int)(changeX + this.getX() + this.game.getHomeTeam().getPlayer(i).getX() * ApoSoccerConstants.DELTA_RADAR + 5 * Math.sin(Math.toRadians( 90 + this.game.getHomeTeam().getPlayer(i).getLineOfSight() ))), (int)(changeY + this.getY() + this.game.getHomeTeam().getPlayer(i).getY() * ApoSoccerConstants.DELTA_RADAR + 5 * Math.cos(Math.toRadians( 270 + this.game.getHomeTeam().getPlayer(i).getLineOfSight() ))));
			}
		}
		if (this.game.getVisitorTeam() != null) {
			for (int i = 0; i < this.game.getVisitorTeam().getPlayers().size(); i++) {
				g.setColor(Color.blue);
				g.fillOval((int)(changeX - 2 + this.getX() + this.game.getVisitorTeam().getPlayer(i).getX() * ApoSoccerConstants.DELTA_RADAR), (int)(changeY - 2 + this.getY() + this.game.getVisitorTeam().getPlayer(i).getY() * ApoSoccerConstants.DELTA_RADAR), (int)(5), (int)(5));
				if (this.game.getVisitorTeam().getPlayers().get(i).isBPlayerHuman()) {
					g.setColor(Color.yellow);
					g.drawOval((int)(changeX - 2 + this.getX() - 1 + this.game.getVisitorTeam().getPlayer(i).getX() * ApoSoccerConstants.DELTA_RADAR), (int)(changeY - 2 - 1 + this.getY() + this.game.getVisitorTeam().getPlayer(i).getY() * ApoSoccerConstants.DELTA_RADAR), (int)(7), (int)(7));		
				}
				g.setColor(Color.black);
				g.drawLine((int)(changeX + 1 + this.getX() + this.game.getVisitorTeam().getPlayer(i).getX() * ApoSoccerConstants.DELTA_RADAR), (int)(changeY + 1 + this.getY() + this.game.getVisitorTeam().getPlayer(i).getY() * ApoSoccerConstants.DELTA_RADAR), (int)(changeX + this.getX() + this.game.getVisitorTeam().getPlayer(i).getX() * ApoSoccerConstants.DELTA_RADAR + 5 * Math.sin(Math.toRadians( 90 + this.game.getVisitorTeam().getPlayer(i).getLineOfSight() ))), (int)(changeY + this.getY() + this.game.getVisitorTeam().getPlayer(i).getY() * ApoSoccerConstants.DELTA_RADAR + 5 * Math.cos(Math.toRadians( 270 + this.game.getVisitorTeam().getPlayer(i).getLineOfSight() ))));
			}
		}
		if (this.game.getBall() != null) {
			g.setColor(Color.darkGray);
			g.fillOval((int)(changeX - 1 + this.getX() + this.game.getBall().getX() * ApoSoccerConstants.DELTA_RADAR), (int)(changeY - 1 + this.getY() + this.game.getBall().getY() * ApoSoccerConstants.DELTA_RADAR), (int)(4), (int)(4));
			g.setColor(Color.black);
			g.drawLine((int)(changeX + 1 + this.getX() + this.game.getBall().getX() * ApoSoccerConstants.DELTA_RADAR), (int)(changeY + 1 + this.getY() + this.game.getBall().getY() * ApoSoccerConstants.DELTA_RADAR), (int)(changeX + this.getX() + this.game.getBall().getX() * ApoSoccerConstants.DELTA_RADAR + 5 * Math.sin(Math.toRadians( 90 + this.game.getBall().getLineOfSight() ))), (int)(changeY + this.getY() + this.game.getBall().getY() * ApoSoccerConstants.DELTA_RADAR + 5 * Math.cos(Math.toRadians( 270 + this.game.getBall().getLineOfSight() ))));
		}
	}
	
}
