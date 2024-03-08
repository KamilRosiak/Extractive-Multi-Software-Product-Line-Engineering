package org.apogames.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * diese Klasse beinhaltet eine Entity, die als Animation abl�uft
 * Es wird ein Bild �bergeben, was aus mehreren Teilbilder besteht
 * und bei jeden Spielschritt wird geschaut, ob genug Zeit vergangen ist
 * um den n�chsten Frame anzuzeigen
 * @author Dirk Aporius
 *
 */
public class ApoAnimation extends ApoEntity {
	/**
	 * diese Variable gibt an, welcher Frame gerade anzeigt wird
	 */
	private int frame;
	/**
	 * diese Variable gibt an, wieviele Frames es �berhaupt gibt
	 */
	private int tiles;
	
	/**
	 * diese Variable gibt an, wieviel Zeit vergehen muss,
	 * um ein neues Frame anzuzeigen
	 */
	private long time;
	/**
	 * diese Variable gibt an, wieviel Zeit schon vergangen ist
	 * und falls sie gr��er als die time-Variable ist,
	 * dann wird sie, um deren Wert verkleinert
	 */
	private long curTime;
	
	/**
	 * diese Variable gibt an, ob die Animation wiederholt werden soll
	 */
	private boolean bLoop;
	
	/**
	 * diese Variable gibt an, ob die Animation gerade abl�uft oder nicht
	 */
	private boolean bAnimation;
	
	/**
	 * dient zur Erstellung des Objektes
	 * @param iAnimation: Das Bild, dass aus vielen Teilbildern f�r die Animation besteht
	 * @param x: X-Wert, wo die Animation dargestellt werden soll (links)
	 * @param y: Y-Wert, wo die Animation dargestellt werden soll (oben)
	 * @param tiles: gibt an, aus wievielen Teilbildern besteht das Bild
	 * @param time: wieviel Zeit muss vergehen, um ein neues Frame anzuzeigen
	 */
	public ApoAnimation( BufferedImage iAnimation, float x, float y, int tiles, long time ) {
		this(iAnimation, x, y, iAnimation.getWidth()/tiles, iAnimation.getHeight(), tiles, time);
	}
	
	/**
	 * dient zur Erstellung des Objektes
	 * @param iAnimation: Das Bild, dass aus vielen Teilbildern f�r die Animation besteht
	 * @param x: X-Wert, wo die Animation dargestellt werden soll (links)
	 * @param y: Y-Wert, wo die Animation dargestellt werden soll (oben)
	 * @param width: gibt die Breite eines einzelnen Tiles an
	 * @param height: gibt die H�he eines einzelnen Tiles an
	 * @param tiles: gibt an, aus wievielen Teilbildern besteht das Bild
	 * @param time: wieviel Zeit muss vergehen, um ein neues Frame anzuzeigen
	 */
	public ApoAnimation( BufferedImage iAnimation, float x, float y, float width, float height, int tiles, long time ) {
		super( iAnimation, x, y, width, height );
		
		this.setTiles(tiles);
		this.setTime(time);
		this.setBLoop(true);
		this.setBAnimation(true);
		
		this.init();
	}
	
	public void init() {
		super.init();
		
		this.setFrame(0);
		this.setCurTime(0);
	}
	
	/**
	 * gibt die Anzahl der Tiles zur�ck
	 * @return gibt die Anzahl der Tiles zur�ck
	 */
	public int getTiles() {
		return this.tiles;
	}

	/**
	 * setzt die Anzahl der Tiles auf den �bergebenen Wert
	 * @param tiles
	 */
	public void setTiles(int tiles) {
		this.tiles = tiles;
	}

	/**
	 * gibt zur�ck, in welches Frame gerade angezeigt wird
	 * @return gibt zur�ck, in welches Frame gerade angezeigt wird
	 */
	public int getFrame() {
		return this.frame;
	}

	/**
	 * setzt das aktuelle Frame was gerade angezeigt werden soll
	 * auf ein �bergebenes
	 * @param frame = neues Frame
	 */
	public void setFrame(int frame) {
		this.frame = frame;
	}

	/**
	 * gibt die aktuelle Zeit zur�ck, die vergangen ist
	 * @return gibt die aktuelle Zeit zur�ck, die vergangen ist
	 */
	public long getCurTime() {
		return this.curTime;
	}

	/**
	 * setzt die aktuelle Zeit auf den �bergebenen Wert
	 * @param curTime = neue aktuelle Zeit
	 */
	public void setCurTime(long curTime) {
		this.curTime = curTime;
	}

	/**
	 * gibt zur�ck, wieviel Zeit vergehen muss, damit ein neues Frame angezeigt wird
	 * @return gibt zur�ck, wieviel Zeit vergehen muss, damit ein neues Frame angezeigt wird
	 */
	public long getTime() {
		return time;
	}

	/**
	 * setzt die Zeit bis ein neues Frame angezeigt wird auf den �bergebenen Wert
	 * @param time
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * gibt zur�ck, on die Animation wiederholt werden soll oder nicht
	 * @return TRUE, Animation wird wiederholt, ansonsten FALSE
	 */
	public boolean isBLoop() {
		return this.bLoop;
	}

	/**
	 * setzt den Wert, ob die Animation wiederholt werden soll auf den �bergebenen Wert
	 * @param bLoop : TRUE, Animation wird wiederholt, ansonsten FALSE
	 */
	public void setBLoop(boolean bLoop) {
		this.bLoop = bLoop;
	}

	/**
	 * gibt zur�ck, ob die Animation gerade abl�uft
	 * @return TRUE, die Animation gerade abl�uft ab, ansonsten FALSE
	 */
	public boolean isBAnimation() {
		return this.bAnimation;
	}

	/**
	 * setzt die Variable, ob die Animation gerade abl�uft auf den �bergebenen Wert
	 * @param bAnimation : TRUE, die Animation soll starten, ansonsten FALSE
	 */
	public void setBAnimation(boolean bAnimation) {
		this.bAnimation = bAnimation;
	}

	/**
	 * wird jeden Zyklus einmal aufgerufen,
	 * und schaut, ob gen�gend Zeit vergangen ist,
	 * um ein neues Frame anzuzeigen
	 * @param time: Zeit, die seit dem letzten Aufruf dieser Funktion vergangen ist
	 */
	public void think( int time ) {
		if (this.isBAnimation()) {
			this.setCurTime(this.getCurTime() + time);
			while ( this.getCurTime() >= this.getTime() ) {
				this.setCurTime(this.getCurTime() - this.getTime());
				this.setFrame(this.getFrame() + 1);
				if ( this.getFrame() >= this.getTiles() ) {
					this.setFrame(0);
					if (!this.isBLoop()) {
						this.setBAnimation(false);
					}
				}
			}
		}
	}

	public void render( Graphics2D g ) {
		this.render( g, 0, 0 );
	}
	
	public void render( Graphics2D g, int x, int y ) {
		if (super.isBVisible()) {
			if (super.getIBackground() != null) {
				g.drawImage(super.getIBackground().getSubimage((int)(this.getFrame() * this.getWidth()), 0, (int)this.getWidth(), (int)this.getHeight()), (int)(this.getX() + x), (int)(this.getY() + y), null);
			}
			if (super.isBSelect()) {
				g.setColor(Color.red);
				g.drawRect((int)(this.getX() - x), (int)(this.getY() - y), (int)(this.getWidth() - 1), (int)(this.getHeight() - 1));
			}
		}
	}
	
}
