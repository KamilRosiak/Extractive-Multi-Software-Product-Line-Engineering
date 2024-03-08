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
	 * diese Variable gibt an, welches Tiles gerade angezeigt wird
	 */
	private int direction;
	
	/**
	 * diese Variable gibt an, wieviele Tiles in y-Richtugn es gibt
	 */
	private int maxDirection;
	
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
	
	private final boolean bRGB;
	
	private BufferedImage[][] images;
	
	/**
	 * dient zur Erstellung des Objektes
	 * @param iAnimation: Das Bild, dass aus vielen Teilbildern f�r die Animation besteht
	 * @param x: X-Wert, wo die Animation dargestellt werden soll (links)
	 * @param y: Y-Wert, wo die Animation dargestellt werden soll (oben)
	 * @param tiles: gibt an, aus wievielen Teilbildern besteht das Bild
	 * @param time: wieviel Zeit muss vergehen, um ein neues Frame anzuzeigen
	 */
	public ApoAnimation(BufferedImage iAnimation, float x, float y, int tiles, long time) {
		this(iAnimation, x, y, iAnimation.getWidth()/tiles, iAnimation.getHeight(), tiles, time, 1, false);
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
	public ApoAnimation(BufferedImage iAnimation, float x, float y, float width, float height, int tiles, long time, int maxDirections, boolean bRGB) {
		super(iAnimation, x, y, width, height);
		
		this.setTiles(tiles);
		this.setTime(time);
		this.setBLoop(true);
		this.setBAnimation(true);
		
		this.bRGB = bRGB;
		this.maxDirection = maxDirections;
		this.makeImageArray();
		
		this.init();
	}
	
	public void init() {
		super.init();
		
		this.direction = 0;
		this.setFrame(0);
		this.setCurTime(0);
	}
	
	public void setIBackground(BufferedImage iBackground) {
		super.setIBackground(iBackground);
		this.makeImageArray();
	}
	
	private void makeImageArray() {
		int type = BufferedImage.TYPE_INT_RGB;
		if (!this.bRGB) {
			type = BufferedImage.TYPE_INT_ARGB_PRE;
		}
		if (this.getIBackground() == null) {
			this.images = new BufferedImage[0][0];
		} else {
			System.out.println();
			int width = (int)(this.getIBackground().getWidth() / this.tiles);
			int height = (int)(this.getIBackground().getHeight() / this.maxDirection);
			this.images = new BufferedImage[this.maxDirection][this.tiles];
			for (int y = 0; y < this.images.length; y++) {
				for (int x = 0; x < this.images[0].length; x++) {
					BufferedImage image = new BufferedImage(width, height, type);
					Graphics2D g = image.createGraphics();
					g.drawImage(this.getIBackground().getSubimage(x * width, y * height, width, height), 0, 0, null);
					g.dispose();
					this.images[y][x] = image;
				}
			}
		}
	}
	
	/**
	 * Array mit den einzelnen Frames der Bilder
	 * 2-Dimensional weil es mehrere Animationen beinhalten k�nnte
	 * @return 2-dimensionales Array mit den einzelnen Frames der Bilder
	 */
	public BufferedImage[][] getImages() {
		return this.images;
	}

	/**
	 * gibt zur�ck, welches Tile angezeigt werden soll
	 * @return gibt zur�ck, welches Tile angezeigt werden soll
	 */
	public int getDirection() {
		return this.direction;
	}

	/**
	 * setzt das Tile, dass angezeigt werden soll, auf den �bergebenen Wert
	 * @param direction = neues Tiles was angezeigt werden soll
	 */
	public void setDirection(int direction) {
		this.direction = direction;
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
	public void think(int time) {
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

	public void render(Graphics2D g) {
		this.render(g, 0, 0);
	}
	
	public void render(Graphics2D g, int x, int y) {
		if (super.isBVisible()) {
			if (super.getIBackground() != null) {
				g.drawImage(this.images[this.getDirection()][this.getFrame()], (int)(this.getX() + x), (int)(this.getY() + y), null);
			}
			if (super.isBSelect()) {
				g.setColor(Color.red);
				g.drawRect((int)(this.getX() - x), (int)(this.getY() - y), (int)(this.getWidth() - 1), (int)(this.getHeight() - 1));
			}
		}
	}
	
}