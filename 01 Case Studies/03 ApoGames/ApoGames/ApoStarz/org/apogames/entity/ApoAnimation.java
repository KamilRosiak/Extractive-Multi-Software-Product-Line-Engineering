package org.apogames.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * diese Klasse beinhaltet eine Entity, die als Animation abläuft
 * Es wird ein Bild übergeben, was aus mehreren Teilbilder besteht
 * und bei jeden Spielschritt wird geschaut, ob genug Zeit vergangen ist
 * um den nächsten Frame anzuzeigen
 * @author Dirk Aporius
 *
 */
public class ApoAnimation extends ApoEntity {
	/**
	 * diese Variable gibt an, welcher Frame gerade anzeigt wird
	 */
	private int frame;
	/**
	 * diese Variable gibt an, wieviele Frames es überhaupt gibt
	 */
	private int tiles;
	
	/**
	 * diese Variable gibt an, wieviel Zeit vergehen muss,
	 * um ein neues Frame anzuzeigen
	 */
	private long time;
	/**
	 * diese Variable gibt an, wieviel Zeit schon vergangen ist
	 * und falls sie größer als die time-Variable ist,
	 * dann wird sie, um deren Wert verkleinert
	 */
	private long curTime;
	
	/**
	 * dient zur Erstellung des Objektes
	 * @param iAnimation: Das Bild, dass aus vielen Teilbildern für die Animation besteht
	 * @param x: X-Wert, wo die Animation dargestellt werden soll (links)
	 * @param y: Y-Wert, wo die Animation dargestellt werden soll (oben)
	 * @param tiles: gibt an, aus wievielen Teilbildern besteht das Bild
	 * @param time: wieviel Zeit muss vergehen, um ein neues Frame anzuzeigen
	 */
	public ApoAnimation( BufferedImage iAnimation, float x, float y, int tiles, long time ) {
		super( iAnimation, x, y, iAnimation.getWidth()/tiles, iAnimation.getHeight() );
		
		this.tiles = tiles;
		this.time = time;
		
		this.init();
	}
	
	/**
	 * dient zur Erstellung des Objektes
	 * @param iAnimation: Das Bild, dass aus vielen Teilbildern für die Animation besteht
	 * @param x: X-Wert, wo die Animation dargestellt werden soll (links)
	 * @param y: Y-Wert, wo die Animation dargestellt werden soll (oben)
	 * @param width: gibt die Breite eines einzelnen Tiles an
	 * @param height: gibt die Höhe eines einzelnen Tiles an
	 * @param tiles: gibt an, aus wievielen Teilbildern besteht das Bild
	 * @param time: wieviel Zeit muss vergehen, um ein neues Frame anzuzeigen
	 */
	public ApoAnimation( BufferedImage iAnimation, float x, float y, float width, float height, int tiles, long time ) {
		super( iAnimation, x, y, width, height );
		
		this.tiles = tiles;
		this.time = time;
		
		this.init();
	}
	
	public void init() {
		super.init();
		
		this.frame = 0;
		this.curTime = 0;
	}
	
	/**
	 * gibt die Anzahl der Tiles zurück
	 * @return gibt die Anzahl der Tiles zurück
	 */
	public int getTiles() {
		return this.tiles;
	}

	/**
	 * setzt die Anzahl der Tiles auf den übergebenen Wert
	 * @param tiles
	 */
	public void setTiles(int tiles) {
		this.tiles = tiles;
	}

	/**
	 * gibt zurück, in welches Frame gerade angezeigt wird
	 * @return gibt zurück, in welches Frame gerade angezeigt wird
	 */
	public int getFrame() {
		return this.frame;
	}

	/**
	 * setzt das aktuelle Frame was gerade angezeigt werden soll
	 * auf ein übergebenes
	 * @param frame = neues Frame
	 */
	public void setFrame(int frame) {
		this.frame = frame;
	}

	/**
	 * gibt die aktuelle Zeit zurück, die vergangen ist
	 * @return gibt die aktuelle Zeit zurück, die vergangen ist
	 */
	public long getCurTime() {
		return this.curTime;
	}

	/**
	 * setzt die aktuelle Zeit auf den übergebenen Wert
	 * @param curTime = neue aktuelle Zeit
	 */
	public void setCurTime(long curTime) {
		this.curTime = curTime;
	}

	/**
	 * gibt zurück, wieviel Zeit vergehen muss, damit ein neues Frame angezeigt wird
	 * @return gibt zurück, wieviel Zeit vergehen muss, damit ein neues Frame angezeigt wird
	 */
	public long getTime() {
		return time;
	}

	/**
	 * setzt die Zeit bis ein neues Frame angezeigt wird auf den übergebenen Wert
	 * @param time
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * wird jeden Zyklus einmal aufgerufen,
	 * und schaut, ob genügend Zeit vergangen ist,
	 * um ein neues Frame anzuzeigen
	 * @param time: Zeit, die seit dem letzten Aufruf dieser Funktion vergangen ist
	 */
	public void think( int time ) {
		this.curTime += time;
		while ( this.getCurTime() >= this.getTime() ) {
			this.curTime -= this.time;
			this.frame += 1;
			if ( this.getFrame() >= this.getTiles() ) {
				this.frame = 0;
			}
		}
	}

	public void render( Graphics2D g ) {
		this.render( g, 0, 0 );
	}
	
	public void render( Graphics2D g, int x, int y ) {
		if ( super.isBVisible() ) {
			if (super.getIBackground() != null) {
				g.drawImage( super.getIBackground().getSubimage( (int)( this.getFrame() * this.getWidth() ), 0, (int)this.getWidth(), (int)this.getHeight() ), (int)this.getX() + x, (int)this.getY() + y, null );
			}
			if ( super.isBSelect() ) {
				g.setColor( Color.red );
				g.drawRect( (int)( this.getX() - x ), (int)( this.getY() - y ), (int)( this.getWidth() - 1 ), (int)( this.getHeight() - 1 ) );
			}
		}
	}
	
}
