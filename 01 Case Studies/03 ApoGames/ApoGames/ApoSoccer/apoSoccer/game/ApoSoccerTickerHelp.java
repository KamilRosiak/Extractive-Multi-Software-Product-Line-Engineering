package apoSoccer.game;

import java.awt.Color;

/**
 * Hilfsklasse, die speichert, welcher Name wie andersfarbig makiert werden soll im Ticker
 * @author Dirk Aporius
 *
 */
public class ApoSoccerTickerHelp {
	/** Name */
	private String name;
	/** Position des Vorkommens des Namens */
	private int position;
	/** Endposition wenn der Name ausgeschrieben wurde */
	private int maxPosition;
	/** Farbe, mit der der Name geschrieben werden soll */
	private Color color;
	
	public ApoSoccerTickerHelp(String name, int position, Color color) {
		this.name = name;
		this.position = position;
		this.color = color;
		this.maxPosition = position + name.length();
	}

	/**
	 * gibt den Namen zur�ck
	 * @return gibt den Namen zur�ck
	 */
	public String getName() {
		return name;
	}

	/**
	 * setzt den Namen auf den �bergebenen Wert
	 * @param name : neuer Name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gibt die Position des Namens in der Tickernachricht zur�ck
	 * @return Gibt die Position des Namens in der Tickernachricht zur�ck
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * setzt die Position, wo der Name steht auf den �bergebenen Wert
	 * @param position : neue Position
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * gibt die Endposition des Namens zur�ck
	 * @return gibt die Endposition des Namens zur�ck
	 */
	public int getMaxPosition() {
		return maxPosition;
	}

	/**
	 * setzt die Endposition des Namens auf den �bergebenen Wert
	 * @param maxPosition : neue Endposition
	 */
	public void setMaxPosition(int maxPosition) {
		this.maxPosition = maxPosition;
	}

	/**
	 * gibt die Farbe zur�ck, mit welcher der Name dargestellt werden soll
	 * @return gibt die Farbe zur�ck, mit welcher der Name dargestellt werden soll
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * setzt die Farbe, mit welcher der Name dargestellt wird, auf den �bergebenen
	 * @param color : neue Farbe
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
}
