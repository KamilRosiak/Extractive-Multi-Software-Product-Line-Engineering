package apoSkunkman.level;

import java.awt.Point;

/**
 * Hilfsklasse für die Leveltypangabe<br />
 * mit passendem String, der Anzahl der minimalen Spieler und der Anzahl der maximalen Spieler
 * @author Dirk Aporius
 *
 */
public class ApoSkunkmanTypeHelp {

	/** Integervariable die angibt, wieviel Spieler es maximal sein dürfen bei diesem Leveltypus */
	private int maxPlayers;
	/** Integervariable die angibt, wieviel Spieler es minimal sein dürfen bei diesem Leveltypus */
	private int minPlayers;
	/** Stringvariable, die den Namen des Leveltypus angibt */
	private final String typeString;
	/** boolean Variable, die angibt ob das Level standarmäßig mit dem X-gestartet werden soll */
	private final boolean bGoalX;
	/** Zielpunkt für das X */
	private final Point goalXPoint;
	
	/**
	 * Kostruktor
	 * @param typeString : Name des Leveltypus
	 * @param minPlayers : Minimale Anzahl an Spielern
	 * @param maxPlayers : Maximale Anzahl an Spielern
	 */
	public ApoSkunkmanTypeHelp(String typeString, int minPlayers, int maxPlayers, final boolean bGoalX, final Point goalXPoint) {
		this.typeString = typeString;
		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;
		this.bGoalX = bGoalX;
		this.goalXPoint = goalXPoint;
	}

	/**
	 * gibt die maximale Anzahl an Spieler bei diesem Leveltypus zurück
	 * @return gibt die maximale Anzahl an Spieler bei diesem Leveltypus zurück
	 */
	public final int getMaxPlayers() {
		return this.maxPlayers;
	}

	/**
	 * setzt die maximale Anzahl an Spielern auf den übergebenen Wert
	 * @param minPlayers : neue Anzahl maximaler Spieler
	 */
	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	/**
	 * gibt die minimale Anzahl an Spieler bei diesem Leveltypus zurück
	 * @return gibt die minimale Anzahl an Spieler bei diesem Leveltypus zurück
	 */
	public final int getMinPlayers() {
		return this.minPlayers;
	}
	
	/**
	 * setzt die minimale Anzahl an Spielern auf den übergebenen Wert
	 * @param minPlayers : neue Anzahl minimaler Spieler
	 */
	public void setMinPlayers(int minPlayers) {
		this.minPlayers = minPlayers;
	}

	/**
	 * gibt den Namen des Leveltypus zurück
	 * @return gibt den Namen des Leveltypus zurück
	 */
	public final String getTypeString() {
		return this.typeString;
	}

	/**
	 * gibt zurück, ob das Level ein Ziel haben sollte oder nicht
	 * @return gibt zurück, ob das Level ein Ziel haben sollte oder nicht
	 */
	public final boolean hasGoalX() {
		return this.bGoalX;
	}

	/**
	 * gibt den Punkt zurück, wo das Ziel sein soll, wenn x < 0 dann zufällig
	 * @return gibt den Punkt zurück, wo das Ziel sein soll, wenn x < 0 dann zufällig
	 */
	public final Point getGoalXPoint() {
		return this.goalXPoint;
	}
	
}
