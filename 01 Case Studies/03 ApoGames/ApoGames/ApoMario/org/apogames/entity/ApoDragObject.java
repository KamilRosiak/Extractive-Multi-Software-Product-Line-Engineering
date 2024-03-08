package org.apogames.entity;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * ein spezieller Button um ein Objekt zu ziehen.
 * @author Dirk Aporius
 *
 */
public class ApoDragObject extends ApoButton {
	
	private int	diffX, diffY;
	
	public ApoDragObject( int x, int y, int width, int height ) {
		super( null, x, y, width, height, "" );
		
		this.diffX		= 0;
		this.diffY		= 0;
	}
	
	/**
	 * was passiert, wenn eine Maustaste im Spielfeld gedrückt wurde
	 * @param x: x-Koordinate der Maus
	 * @param y: y-Koordinate der Maus
	 * @return TRUE, falls über Button Maus gedrückt, sonst FALSE
	 */
	public boolean getPressed( int x, int y ) {
		boolean bPressed	= super.getPressed( x, y );
		if ( bPressed )	{
			this.diffX			= (int)( x - this.getX() );
			this.diffY			= (int)( y - this.getY() );
			super.setBSelect(true);
		}
		return bPressed;
	}
	
	/**
	 * was passiert, wenn eine Maustaste im Spielfeld losgelassen wurde
	 * @param x: x-Koordinate der Maus
	 * @param y: y-Koordinate der Maus
	 * @return TRUE, wenn die Maustaste losgelassen wurde und der Spieler auch diesen Button gedrückt hatte, sonst FALSE
	 */
	public boolean getReleased( int x, int y ) {
		boolean bReleased	= super.getReleased( x, y );
		this.diffX			= 0;
		this.diffY			= 0;
		return bReleased;
	}
	
	/**
	 * überprüft, ob die übergebenen Werte auf dem Button liegen
	 * @param x: x-Koordinate der Maus
	 * @param y: y-Koordinate der Maus
	 * @return: TRUE, falls drin, sonst FALSE
	 */
	public boolean getIn( int x, int y ) {
		if ( 	( this.getX() - this.getWidth()/2 < x ) && ( this.getX() + this.getWidth()/2 + this.getWidth() >= x ) &&
				( this.getY() - this.getHeight()/2 < y ) && ( this.getY() + this.getHeight()/2 + this.getHeight() >= y ) ) {
			return true;
		}
		return false;
	}
	
	/**
	 * setzt die x Variable des Button auf den übergebenen Wert minus der Differenz von Start des Button und dem Punkt, auf welchen gedrückt wurde
	 * @param x: x-Koordinate
	 */
	public void setDragX( int x ) {
		this.setX( x - this.diffX );
	}
	
	/**
	 * setzt die y Variable des Button auf den übergebenen Wert minus der Differenz von Start des Button und dem Punkt, auf welchen gedrückt wurde
	 * @param x: y-Koordinate
	 */
	public void setDragY( int y ) {
		this.setY( y - this.diffY );
	}

	/**
	 * gibt die x Variable des Button vom übergebenen Wert minus der Differenz von Start des Button und dem Punkt, auf welchen gedrückt wurde, zurück
	 * @param x: x-Koorinate
	 * @return gibt die x Variable des Button vom übergebenen Wert minus der Differenz von Start des Button und dem Punkt, auf welchen gedrückt wurde, zurück
	 */
	public int getNewX( int x ) {
		return ( x - this.diffX );
	}
	
	/**
	 * gibt die y Variable des Button vom übergebenen Wert minus der Differenz von Start des Button und dem Punkt, auf welchen gedrückt wurde, zurück
	 * @param y: y-Koorinate
	 * @return gibt die y Variable des Button vom übergebenen Wert minus der Differenz von Start des Button und dem Punkt, auf welchen gedrückt wurde, zurück
	 */
	public int getNewY( int y ) {
		return ( y - this.diffY );
	}
	
	/**
	 * gibt den derzeitigen DifferenzX-Wert zurück
	 * @return gibt den derzeitigen DifferenzX-Wert zurück
	 */
	public int getDiffX() {
		return this.diffX;
	}

	/**
	 * gibt den derzeitigen DifferenzY-Wert zurück
	 * @return gibt den derzeitigen DifferenzY-Wert zurück
	 */
	public int getDiffY() {
		return this.diffY;
	}
	
	/**
	 * malt das DragObject hin
	 */
	public void render( Graphics2D g, int changeX, int changeY ) {
		if ( this.isBVisible() ) {
			if ( super.isBSelect() ) {
				g.setColor( Color.WHITE );
				g.drawRect( (int)( this.getX() - this.getWidth()/2 ), (int)( this.getY() - this.getHeight()/2 ), (int)this.getWidth(), (int)this.getHeight() );
			}
			g.setColor( Color.BLACK );
			g.fillRect( (int)( this.getX() - 2 ), (int)( this.getY() - 2 ), 4, 4 );
		}
	}

}
