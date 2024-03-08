package org.apogames.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Diese Klasse handelt einen Button, die ein Bild enthält und deren Textfarbe verändert wird,
 * wenn man mit der Maus drüber geht bzw draufklickt
 * @author Dirk Aporius
 *
 */
public class ApoButtonText extends ApoButton
{
	private String			text;
	private Font			font;
	private Color			colorPressed, colorReleased;
	private int				value;
	
	public ApoButtonText( BufferedImage iBackground, int x, int y, int width, int height, String function, String text )
	{
		super( iBackground, x, y, width, height, function );
		
		this.value			= -1;
		this.text			= text;
		
		this.font			= new Font( "Dialog", Font.BOLD, 14 );
		this.colorPressed	= new Color( 80, 80, 0 );
		this.colorReleased	= Color.RED;
	}
	
	public ApoButtonText( BufferedImage iBackground, int x, int y, int width, int height, String function, int value )
	{
		super( iBackground, x, y, width, height, function );
		
		this.value			= value;
		this.text			= "";
		
		this.font			= new Font( "Dialog", Font.BOLD, 14 );
		this.colorPressed	= new Color( 80, 80, 0 );
		this.colorReleased	= Color.RED;
	}
	
	/**
	 * gibt die derzeitige Farbe zurück, die der Text hat, wenn die Maus drüber ist
	 * @return gibt die derzeitige Farbe zurück, die der Text hat, wenn die Maus drüber ist
	 */
	public Color getColorPressed() {
		return this.colorPressed;
	}

	/**
	 * setzt die Farbe, die der Text hat, wenn die Maus drüber ist, auf den übergebenen Wert
	 * @param colorPressed: neue Farbe für MausPressed
	 */
	public void setColorPressed(Color colorPressed) {
		this.colorPressed = colorPressed;
	}

	/**
	 * gibt die derzeitige Farbe zurück, die der Text hat, wenn die Maus auf die Entity geklickt hat
	 * @return gibt die derzeitige Farbe zurück, die der Text hat, wenn die Maus auf die Entity geklickt hat
	 */
	public Color getColorReleased() {
		return this.colorReleased;
	}

	/**
	 * setzt die Farbe, die der Text hat, wenn die Maus auf die Entity geklickt hat, auf den übergebenen Wert
	 * @param colorPressed: neue Farbe für MausReleased
	 */
	public void setColorReleased(Color colorReleased) {
		this.colorReleased = colorReleased;
	}

	/**
	 * setzt die font Size auf den übergebenen Wert
	 * @param size = Größe des Fonts
	 */
	public void setFontSize( int size )
	{
		this.font		= new Font( "Dialog", Font.BOLD, size );
	}
	
	/**
	 * gibt eine Zahl zurück, die beim Erstellen des Objektes mit übergeben wurde oder auf -1
	 * gesetzt wurde
	 * @return gibt eine Zahl zurück
	 */
	public int getValue()
	{
		return this.value;
	}
	
	/**
	 * malt den Button an die Stelle getX() + changeX und getY() + changeY hin
	 * @param changeX: Verschiebung in x-Richtung
	 * @param changeY: Verschiebung in y-Richtung
	 */
	public void render( Graphics2D g, int changeX, int changeY ) {
		if ( this.isBVisible() ) {
			g.setFont( this.font );
			if ( this.getIBackground() != null )
				g.drawImage( this.getIBackground(), (int)this.getX() + changeX, (int)this.getY() + changeY, null);
			int w = g.getFontMetrics().stringWidth( this.text );
			int x = (int)( this.getX() + this.getWidth() / 2 - w/2 );
			int y = (int)( this.getY() + this.getHeight() / 2 + 5 );
			if ( this.isBPressed() ) {
				g.setColor( this.colorReleased );
				if ( this.text.equals( "" ) )
					g.drawRect( (int)this.getX() + changeX, (int)this.getY() + changeY, (int)this.getWidth() - 1, (int)this.getHeight() - 1 );
				else
					g.drawString( this.text, x + changeX, y + changeY );
			} else if ( this.isBOver() ) {
				g.setColor( this.colorPressed );
				if ( this.text.equals( "" ) )
					g.drawRect( (int)this.getX() + changeX, (int)this.getY() + changeY, (int)this.getWidth() - 1, (int)this.getHeight() - 1 );
				else
					g.drawString( this.text, x + changeX, y + changeY );
			} else {
				g.setColor( Color.BLACK );
				if ( !this.text.equals( "" ) )
					g.drawString( this.text, x + changeX, y + changeY );
				else if ( this.isBSelect() ) {
					g.setColor( this.colorReleased );
					g.drawRect( (int)this.getX() + changeX, (int)this.getY() + changeY, (int)this.getWidth() - 1, (int)this.getHeight() - 1 );
				}
			}
		}
	}

}
