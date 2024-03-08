package org.apogames.help;

/**
 * eine einfache Klasse, die einen Punkt darstellt,
 * aber nicht wie bei der Original Point Klasse mit int Werten,
 * sondern mit float Werten f�r x und y
 * @author Dirk Aporius
 */
public class ApoFloatPoint
{
	/**
	 * X-Wert des Punktes
	 */
	public float x;
	/**
	 * X-Wert des Punktes
	 */
	public float y;
	
	/**
	 * ein ApoFloatPoint Objekt wird erzeigt
	 * @param x: X-Wert des Punktes
	 * @param y: Y-Wert des Punktes
	 */
	public ApoFloatPoint( float x, float y ) {
		this.x	= x;
		this.y	= y;
	}

	/**
	 * gibt den X-Wert des Punktes zur�ck
	 * @return gibt den X-Wert des Punktes zur�ck
	 */
	public float getX() {
		return x;
	}

	/**
	 * setzt den x-Wert des Punktes auf den �bergebenen Wert
	 * @param x = setzt den x-Wert des Punktes auf den �bergebenen Wert
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * gibt den y-Wert des Punktes zur�ck
	 * @return gibt den y-Wert des Punktes zur�ck
	 */
	public float getY() {
		return y;
	}

	/**
	 * setzt den y-Wert des Punktes auf den �bergebenen Wert
	 * @param y = setzt den y-Wert des Punktes auf den �bergebenen Wert
	 */
	public void setY(float y) {
		this.y = y;
	}
	
	

}
