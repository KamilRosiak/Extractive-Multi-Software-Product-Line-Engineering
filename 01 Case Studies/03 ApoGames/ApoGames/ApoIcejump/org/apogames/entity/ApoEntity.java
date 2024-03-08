package org.apogames.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Klasse von der Button und Player erben und einige grundlegene Sachen zur
 * Verfügung stellt
 * 
 * @author Dirk Aporius
 * 
 */
public class ApoEntity {
	private float x, y, startX, startY, vecX, vecY;

	private float width, height;

	private BufferedImage iBackground;

	private boolean bSelect, bVisible, bClose, bUse, bOpaque;
	
	public ApoEntity(BufferedImage iBackground, float x, float y, float width, float height) {
		this.iBackground = iBackground;
		this.startX = x;
		this.startY = y;
		this.width = width;
		this.height = height;
		this.bOpaque = false;
		this.init();
	}

	/**
	 * setzt die Werte auf ihre ursprünglichen Values
	 */
	public void init() {
		this.x = this.startX;
		this.y = this.startY;
		this.bSelect = false;
		this.bVisible = true;
		this.vecX = 0.0F;
		this.vecY = 0.0F;
		this.setBUse(false);
	}

	/**
	 * gibt den Start X-Wert der Entity zurück, der immer gesetzt wird
	 * wenn init aufgerufen wird
	 * @return gibt den Start X-Wert der Entity zurück, der immer gesetzt wird
	 * wenn init aufgerufen wird
	 */
	public float getStartX() {
		return this.startX;
	}

	/**
	 * setzt den Start X-Wert auf den übergebenen
	 * @param startX : neuer X-Startwert
	 */
	public void setStartX(float startX) {
		this.startX = startX;
	}

	/**
	 * gibt den Start Y-Wert der Entity zurück, der immer gesetzt wird
	 * wenn init aufgerufen wird
	 * @return gibt den Start Y-Wert der Entity zurück, der immer gesetzt wird
	 * wenn init aufgerufen wird
	 */
	public float getStartY() {
		return this.startY;
	}

	/**
	 * setzt den Start Y-Wert auf den übergebenen
	 * @param startX : neuer Y-Startwert
	 */
	public void setStartY(float startY) {
		this.startY = startY;
	}

	/**
	 * Überprüfung, ob Pixelgenau geprüft werden soll
	 * @return TRUE, pixelgenau, FALSE nicht
	 */
	public boolean isBOpaque() {
		return this.bOpaque;
	}

	/**
	 * setzt den boolean Wert, ob bei der Überprüfung von 2 Entitys durchsichtige Sachen betrachtet werden, auf true oder false
	 * @param bOpaque
	 */
	public void setBOpaque(boolean bOpaque) {
		this.bOpaque = bOpaque;
	}

	/**
	 * gibt zurück, ob die Entity angezeigt werden soll oder nicht
	 * 
	 * @return gibt zurück, ob die Entity angezeigt werden soll oder nicht
	 */
	public boolean isBVisible() {
		return this.bVisible;
	}

	/**
	 * setzt die Sichtbarkeit der Entity auf den übergebenen Wert
	 * 
	 * @param bVisible
	 */
	public void setBVisible(boolean bVisible) {
		this.bVisible = bVisible;
	}

	/**
	 * gibt an ob die Entity ausgewählt wurde oder nicht
	 * 
	 * @return TRUE falls ausgewählt sonst FALSE
	 */
	public boolean isBSelect() {
		return this.bSelect;
	}

	/**
	 * setzt den boolean Wert ob ausgewählt oder nicht auf den übergebenen
	 * 
	 * @param bSelect
	 */
	public void setBSelect(boolean bSelect) {
		this.bSelect = bSelect;
	}

	/**
	 * gibt zurück, ob die JumpEntity fest ist oder vom Spieler gesetzt wurde
	 * 
	 * @return gibt zurück, ob die JumpEntity fest ist oder vom Spieler gesetzt
	 *         wurde
	 */
	public boolean isBClose() {
		return this.bClose;
	}

	/**
	 * setzt die JumpEntity ob sie fest ist oder nicht auf den übergebenen Wert
	 * 
	 * @param close
	 */
	public void setBClose(boolean bClose) {
		this.bClose = bClose;
	}

	/**
	 * gibt an, ob eine Entity schon benutzt wurde oder nicht
	 * 
	 * @return gibt an, ob eine Entity schon benutzt wurde oder nicht
	 */
	public boolean isBUse() {
		return this.bUse;
	}

	/**
	 * setzt den Wert für die Entity, ob sie benutzt wurde oder nicht auf den
	 * übergebenen Wert
	 * 
	 * @param use
	 */
	public void setBUse(boolean bUse) {
		this.bUse = bUse;
	}

	/**
	 * gibt die Geschwindigkeit in y-Richtung zurück
	 * 
	 * @return gibt die Geschwindigkeit in y-Richtung zurück
	 */
	public float getVecY() {
		return this.vecY;
	}

	/**
	 * setzt die Geschwindkeit in y-Richtung zurück
	 * 
	 * @param vecX
	 */
	public void setVecY(float vecY) {
		this.vecY = vecY;
	}

	/**
	 * gibt die Geschwindigkeit in x-Richtung zurück
	 * 
	 * @return gibt die Geschwindigkeit in x-Richtung zurück
	 */
	public float getVecX() {
		return this.vecX;
	}

	/**
	 * setzt die Geschwindkeit in x-Richtung zurück
	 * 
	 * @param vecX
	 */
	public void setVecX(float vecX) {
		this.vecX = vecX;
	}

	/**
	 * gibt das Bild zurück
	 * 
	 * @return Bild
	 */
	public BufferedImage getIBackground() {
		return this.iBackground;
	}

	/**
	 * setzt das Bild auf den übergebenen Wert
	 * 
	 * @param background
	 */
	public void setIBackground(BufferedImage background) {
		iBackground = background;
	}

	/**
	 * gibt die Weite des Objektes zurück
	 * 
	 * @return Weite des Objektes
	 */
	public float getWidth() {
		return this.width;
	}

	/**
	 * setzt die Weite des Objektes auf den übergebenen Wert
	 * 
	 * @param width
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * gibt die Höhe des Objektes zurück
	 * 
	 * @return Höhe des Objektes
	 */
	public float getHeight() {
		return this.height;
	}

	/**
	 * setzt die Höhe des Objektes auf den übergebenen Wert
	 * 
	 * @param height
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	/**
	 * gibt den x-Wert des Objektes zurück (also den linken Rand des Bildes
	 * 
	 * @return x-Wert des Objektes
	 */
	public float getX() {
		return this.x;
	}

	/**
	 * gibt den mittigen x-Wert des Objektes (also die Kopfmitte sozusagen)
	 * 
	 * @return x-Wert des Objektes
	 */
	public float getXMiddle() {
		return this.x + this.width / 2;
	}

	/**
	 * setzt den X-Wert auf den übergebenen Wert
	 * 
	 * @param x
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * gibt den y-Wert des Objektes zurück (also den höchsten Punkt am Kopf)
	 * 
	 * @return y-Wert des Objektes
	 */
	public float getY() {
		return this.y;
	}

	/**
	 * setzt den y-Wert des Objektes auf den Übergebenen
	 * 
	 * @param y
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * überprüft, ob die übergebenen Werte in der Entity liegen
	 * 
	 * @param x: x-Koordinate der Maus
	 * @param y: y-Koordinate der Maus
	 * @return: TRUE, falls drin, sonst FALSE
	 */
	public boolean intersects(float x, float y) {
		return this.intersects(x, y, 1, 1);
	}

	/**
	 * überprüft, ob die übergebenen Werte (die ein Rechteck ergeben) die Entity
	 * schneiden
	 * 
	 * @param x: X-Wert (links oben vom Rechteck)
	 * @param y: Y-Wert (links oben vom Rechteck)
	 * @param width: Breiten-Wert (wie breit ist das Rechteck)
	 * @param height: Höhen-Wert (wie hoch ist das Rechteck)
	 * @return TRUE, falls drin, sonst FALSE
	 */
	public boolean intersects(float x, float y, float width, float height) {
		if (this.getRec().intersects(x, y, width, height)) {
			if (this.isBOpaque()) {
				if (this.getIBackground() != null) {
					Rectangle2D.Float myRec = (Rectangle2D.Float)this.getRec();
					Rectangle2D.Float cut = (Rectangle2D.Float)myRec.createIntersection(new Rectangle2D.Float(x, y, width, height));
					for (int i = (int)cut.y; i < (int)(cut.y + cut.height); i++) {
						for (int j = (int)cut.x; j < (int)(cut.x + cut.width); j++) {
							if ((i - this.getY() < this.getHeight()) &&
								(j - this.getX() < this.getWidth())) {
								if (this.isOpaque(this.getIBackground().getRGB((int)(j - myRec.x), (int)(i - myRec.y)))) {
									return true;
								}
							}
						}
					}
					return false;
				} else {
					return true;
				}
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * überprüft, ob die übergebene Entity die Entity schneidet
	 * 
	 * @param entity : zu überprüfende Entity
	 * @return TRUE, falls drin, sonst FALSE
	 */
	public boolean intersects(ApoEntity entity) {
		if (this.getRec().intersects( entity.getRec())) {
			if (this.isBOpaque()) {
				return this.checkOpaqueColorCollisions( entity );
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * überprüft, ob die übergebenen Werte (die ein Reckteck ergeben) komplett
	 * in der Entity liegen
	 * 
	 * @param x:
	 *            X-Wert (links oben vom Rechteck)
	 * @param y:
	 *            Y-Wert (links oben vom Rechteck)
	 * @param width:
	 *            Breiten-Wert (wie breit ist das Rechteck)
	 * @param height:
	 *            Höhen-Wert (wie hoch ist das Rechteck)
	 * @return TRUE, falls drin, sonst FALSE
	 */
	public boolean contains(float x, float y, float width, float height) {
		return this.getRec().contains(x, y, width, height);
	}

	/**
	 * überprüft, ob die übergebene Entity komplett in der Entity liegen
	 * 
	 * @param entity:
	 *            zu überprüfende Entity
	 * @return TRUE, falls drin, sonst FALSE
	 */
	public boolean contains(ApoEntity entity) {
		return this.getRec().contains(entity.getRec());
	}

	/**
	 * gibt das aktuelle Rechteck der Entity zurück
	 * @return gibt das aktuelle Rechteck der Entity zurück
	 */
	public Rectangle2D.Float getRec() {
		return new Rectangle2D.Float( this.getX(), this.getY(), this.getWidth(), this.getHeight() );
	}

	/**
	 * überprüft, wenn sich 2 Entity per Rechteck schneiden,
	 * ob sie sich wirklich bildmäßig schneiden 
	 * @param entity = andere Entity zur Schnittüberprüfung
	 * @return TRUE wenn schneiden sich wirklich sonst FALSE
	 */
	public boolean checkOpaqueColorCollisions(ApoEntity entity) {

		Rectangle2D.Float cut = (Rectangle2D.Float)this.getRec().createIntersection(entity.getRec());

		if ((cut.width < 1) || (cut.height < 1)) {
			return false;
		}

		// Rechtecke in Bezug auf die jeweiligen Images
		Rectangle2D.Float sub_me = getSubRec(this.getRec(), cut);
		Rectangle2D.Float sub_him = getSubRec(entity.getRec(), cut);

		BufferedImage img_me = this.getIBackground().getSubimage((int) sub_me.x, (int) sub_me.y, (int) sub_me.width, (int) sub_me.height);
		BufferedImage img_him = entity.getIBackground().getSubimage((int) sub_him.x, (int) sub_him.y, (int) sub_him.width, (int) sub_him.height);

		for (int i = 0; i < img_me.getWidth(); i++) {
			for (int n = 0; n < img_him.getHeight(); n++) {

				int rgb1 = img_me.getRGB(i, n);
				int rgb2 = img_him.getRGB(i, n);

				if (isOpaque(rgb1) && isOpaque(rgb2)) {
					return true;
				}

			}
		}

		return false;
	}

	private Rectangle2D.Float getSubRec(Rectangle2D.Float source, Rectangle2D.Float part) {

		// Rechtecke erzeugen
		Rectangle2D.Float sub = new Rectangle2D.Float();

		// get X - compared to the Rectangle
		if (source.x > part.x) {
			sub.x = 0;
		} else {
			sub.x = part.x - source.x;
		}

		if (source.y > part.y) {
			sub.y = 0;
		} else {
			sub.y = part.y - source.y;
		}

		sub.width = part.width;
		sub.height = part.height;

		return sub;
	}

	/**
	 * überprüft ob der übergebene rgb Wert durchsichtig ist oder nicht
	 * @param rgb = zu überprüfender RGB Wert
	 * @return TRUE falls durchsichtig sonst FALSE
	 */
	private boolean isOpaque(int rgb) {

		int alpha = (rgb >> 24) & 0xff;
		// red = (rgb >> 16) & 0xff;
		// green = (rgb >> 8) & 0xff;
		// blue = (rgb ) & 0xff;

		if (alpha == 0) {
			return false;
		}

		return true;
	}

	/**
	 * Methode, die immer waehrend der update Methode aufgerufen wird
	 * 
	 * @param delta:
	 *            Zeit, die seit dem letzten Aufruf vergangen ist
	 */
	public void think(int delta) {
	}

	/**
	 * malt das Objekt
	 * @param g
	 */
	public void render(Graphics2D g, int x, int y) {
		if ((this.getIBackground() != null) && (this.isBVisible())) {
			g.drawImage(this.iBackground, (int) (this.getX() + x), (int) (this
					.getY() + y), (int) (this.getX() + x + this.getWidth()),
					(int) (this.getY() + y + this.getHeight()), 0, 0,
					(int) this.getWidth(), (int) this.getHeight(), null);
			if (this.isBSelect()) {
				g.setColor(Color.red);
				g.drawRect((int) (this.getX() + x), (int) (this.getY() + y),
						(int) (this.getWidth() - 1),
						(int) (this.getHeight() - 1));
			}
		}
	}

	/**
	 * malt das Objekt
	 * @param g = Graphics2D Objekt
	 */
	public void render(Graphics2D g) {
		this.render(g, 0, 0);
	}

	/**
	 * malt das Objekt
	 * @param g = Graphics Objekt
	 */
	public void render(Graphics g) {
		this.render((Graphics2D) g, 0, 0);
	}

}
