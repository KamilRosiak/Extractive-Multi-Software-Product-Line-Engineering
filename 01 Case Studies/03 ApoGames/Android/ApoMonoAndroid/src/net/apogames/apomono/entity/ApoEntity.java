package net.apogames.apomono.entity;

import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLImage;
import net.gliblybits.bitsengine.utils.BitsRect;

/**
 * Klasse von der Button und Player erben und einige grundlegene Sachen zur
 * Verf�gung stellt
 * 
 * @author Dirk Aporius
 * 
 */
public class ApoEntity {
	private float x, y, startX, startY, vecX, vecY;

	private float width, height;

	private BitsGLImage iBackground;

	private boolean bSelect, bVisible, bClose, bUse, bOpaque;
	
	public ApoEntity(BitsGLImage iBackground, float x, float y, float width, float height) {
		this.iBackground = iBackground;
		this.startX = x;
		this.startY = y;
		this.width = width;
		this.height = height;
		this.bOpaque = true;
		this.init();
	}

	/**
	 * setzt die Werte auf ihre urspr�nglichen Values
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
	 * gibt den Start X-Wert der Entity zur�ck, der immer gesetzt wird
	 * wenn init aufgerufen wird
	 * @return gibt den Start X-Wert der Entity zur�ck, der immer gesetzt wird
	 * wenn init aufgerufen wird
	 */
	public float getStartX() {
		return this.startX;
	}

	/**
	 * setzt den Start X-Wert auf den �bergebenen
	 * @param startX : neuer X-Startwert
	 */
	public void setStartX(float startX) {
		this.startX = startX;
	}

	/**
	 * gibt den Start Y-Wert der Entity zur�ck, der immer gesetzt wird
	 * wenn init aufgerufen wird
	 * @return gibt den Start Y-Wert der Entity zur�ck, der immer gesetzt wird
	 * wenn init aufgerufen wird
	 */
	public float getStartY() {
		return this.startY;
	}

	/**
	 * setzt den Start Y-Wert auf den �bergebenen
	 * @param startX : neuer Y-Startwert
	 */
	public void setStartY(float startY) {
		this.startY = startY;
	}

	/**
	 * �berpr�fung, ob Pixelgenau gepr�ft werden soll
	 * @return TRUE, pixelgenau, FALSE nicht
	 */
	public boolean isBOpaque() {
		return this.bOpaque;
	}

	/**
	 * setzt den boolean Wert, ob bei der �berpr�fung von 2 Entitys durchsichtige Sachen betrachtet werden, auf true oder false
	 * @param bOpaque
	 */
	public void setBOpaque(boolean bOpaque) {
		this.bOpaque = bOpaque;
	}

	/**
	 * gibt zur�ck, ob die Entity angezeigt werden soll oder nicht
	 * 
	 * @return gibt zur�ck, ob die Entity angezeigt werden soll oder nicht
	 */
	public boolean isBVisible() {
		return this.bVisible;
	}

	/**
	 * setzt die Sichtbarkeit der Entity auf den �bergebenen Wert
	 * 
	 * @param bVisible
	 */
	public void setVisible(boolean bVisible) {
		this.bVisible = bVisible;
	}

	/**
	 * gibt an ob die Entity ausgew�hlt wurde oder nicht
	 * 
	 * @return TRUE falls ausgew�hlt sonst FALSE
	 */
	public boolean isBSelect() {
		return this.bSelect;
	}

	/**
	 * setzt den boolean Wert ob ausgew�hlt oder nicht auf den �bergebenen
	 * 
	 * @param bSelect
	 */
	public void setBSelect(boolean bSelect) {
		this.bSelect = bSelect;
	}

	/**
	 * gibt zur�ck, ob die JumpEntity fest ist oder vom Spieler gesetzt wurde
	 * 
	 * @return gibt zur�ck, ob die JumpEntity fest ist oder vom Spieler gesetzt
	 *         wurde
	 */
	public boolean isBClose() {
		return this.bClose;
	}

	/**
	 * setzt die JumpEntity ob sie fest ist oder nicht auf den �bergebenen Wert
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
	 * setzt den Wert f�r die Entity, ob sie benutzt wurde oder nicht auf den
	 * �bergebenen Wert
	 * 
	 * @param use
	 */
	public void setBUse(boolean bUse) {
		this.bUse = bUse;
	}

	/**
	 * gibt die Geschwindigkeit in y-Richtung zur�ck
	 * 
	 * @return gibt die Geschwindigkeit in y-Richtung zur�ck
	 */
	public float getVecY() {
		return this.vecY;
	}

	/**
	 * setzt die Geschwindkeit in y-Richtung zur�ck
	 * 
	 * @param vecX
	 */
	public void setVecY(float vecY) {
		this.vecY = vecY;
	}

	/**
	 * gibt die Geschwindigkeit in x-Richtung zur�ck
	 * 
	 * @return gibt die Geschwindigkeit in x-Richtung zur�ck
	 */
	public float getVecX() {
		return this.vecX;
	}

	/**
	 * setzt die Geschwindkeit in x-Richtung zur�ck
	 * 
	 * @param vecX
	 */
	public void setVecX(float vecX) {
		this.vecX = vecX;
	}

	/**
	 * gibt das Bild zur�ck
	 * 
	 * @return Bild
	 */
	public BitsGLImage getIBackground() {
		return this.iBackground;
	}

	/**
	 * setzt das Bild auf den �bergebenen Wert
	 * 
	 * @param background
	 */
	public void setIBackground(BitsGLImage background) {
		iBackground = background;
	}

	/**
	 * gibt die Weite des Objektes zur�ck
	 * 
	 * @return Weite des Objektes
	 */
	public float getWidth() {
		return this.width;
	}

	/**
	 * setzt die Weite des Objektes auf den �bergebenen Wert
	 * 
	 * @param width
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * gibt die H�he des Objektes zur�ck
	 * 
	 * @return H�he des Objektes
	 */
	public float getHeight() {
		return this.height;
	}

	/**
	 * setzt die H�he des Objektes auf den �bergebenen Wert
	 * 
	 * @param height
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	/**
	 * gibt den x-Wert des Objektes zur�ck (also den linken Rand des Bildes
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
	 * setzt den X-Wert auf den �bergebenen Wert
	 * 
	 * @param x
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * gibt den y-Wert des Objektes zur�ck (also den h�chsten Punkt am Kopf)
	 * 
	 * @return y-Wert des Objektes
	 */
	public float getY() {
		return this.y;
	}

	/**
	 * setzt den y-Wert des Objektes auf den �bergebenen
	 * 
	 * @param y
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * �berpr�ft, ob die �bergebenen Werte in der Entity liegen
	 * 
	 * @param x: x-Koordinate der Maus
	 * @param y: y-Koordinate der Maus
	 * @return: TRUE, falls drin, sonst FALSE
	 */
	public boolean intersects(float x, float y) {
		return this.intersects(x, y, 1, 1);
	}

	/**
	 * �berpr�ft, ob die �bergebenen Werte (die ein Rechteck ergeben) die Entity
	 * schneiden
	 * 
	 * @param x: X-Wert (links oben vom Rechteck)
	 * @param y: Y-Wert (links oben vom Rechteck)
	 * @param width: Breiten-Wert (wie breit ist das Rechteck)
	 * @param height: H�hen-Wert (wie hoch ist das Rechteck)
	 * @return TRUE, falls drin, sonst FALSE
	 */
	public boolean intersects(float x, float y, float width, float height) {
		if (this.getRec().intersects((int)x, (int)y, (int)(width), (int)(height))) {
			return true;
		}
		return false;
	}

	/**
	 * �berpr�ft, ob die �bergebene Entity die Entity schneidet
	 * 
	 * @param entity : zu �berpr�fende Entity
	 * @return TRUE, falls drin, sonst FALSE
	 */
	public boolean intersects(ApoEntity entity) {
		if (this.getRec().intersects(entity.getRec())) {
			return true;
		}
		return false;
	}

	/**
	 * �berpr�ft, ob die �bergebenen Werte (die ein Reckteck ergeben) komplett
	 * in der Entity liegen
	 * 
	 * @param x:
	 *            X-Wert (links oben vom Rechteck)
	 * @param y:
	 *            Y-Wert (links oben vom Rechteck)
	 * @param width:
	 *            Breiten-Wert (wie breit ist das Rechteck)
	 * @param height:
	 *            H�hen-Wert (wie hoch ist das Rechteck)
	 * @return TRUE, falls drin, sonst FALSE
	 */
	public boolean contains(float x, float y, float width, float height) {
		return this.getRec().contains((int)x, (int)y, (int)(width + x), (int)(height + y));
	}

	/**
	 * �berpr�ft, ob die �bergebene Entity komplett in der Entity liegen
	 * 
	 * @param entity:
	 *            zu �berpr�fende Entity
	 * @return TRUE, falls drin, sonst FALSE
	 */
	public boolean contains(ApoEntity entity) {
		return this.getRec().contains(entity.getRec());
	}

	/**
	 * gibt das aktuelle Rechteck der Entity zur�ck
	 * @return gibt das aktuelle Rechteck der Entity zur�ck
	 */
	public BitsRect getRec() {
		return new BitsRect((int)this.getX(), (int)this.getY(), (int)(this.getWidth()), (int)(this.getHeight()));
	}

	/**
	 * �berpr�ft ob der �bergebene rgb Wert durchsichtig ist oder nicht
	 * @param rgb = zu �berpr�fender RGB Wert
	 * @return TRUE falls durchsichtig sonst FALSE
	 */
	public boolean isOpaque(int rgb) {

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
	public void render(BitsGLGraphics g, int x, int y) {
		if ((this.getIBackground() != null) && (this.isBVisible())) {
			g.drawImage(this.iBackground, (this.getX() + x), (this.getY() + y), (this.getX() + x + this.getWidth()), (this.getY() + y + this.getHeight()));
			if (this.isBSelect()) {
				g.setColor(255, 0, 0);
				g.drawRect((int) (this.getX() + x), (int) (this.getY() + y), (int) (this.getWidth() - 1), (int) (this.getHeight() - 1));
			}
		}
	}

	/**
	 * malt das Objekt
	 * @param g = Graphics2D Objekt
	 */
	public void render(BitsGLGraphics g) {
		this.render(g, 0, 0);
	}

}
