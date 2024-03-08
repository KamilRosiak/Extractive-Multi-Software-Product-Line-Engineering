package apoSkunkman.ai;

import java.awt.Color;

import apoSkunkman.ApoSkunkmanConstants;
import apoSkunkman.entity.ApoSkunkmanPlayer;
import apoSkunkman.entity.paint.ApoSkunkmanPlayerDrawCircle;
import apoSkunkman.entity.paint.ApoSkunkmanPlayerDrawLine;
import apoSkunkman.entity.paint.ApoSkunkmanPlayerDrawRect;

/**
 * Klasse, die den eigenen Spieler für die KI darstellt
 * @author Dirk Aporius
 *
 */
public class ApoSkunkmanAIPlayer extends ApoSkunkmanAIEnemy {
	/** Spielerobjekt */
	private final ApoSkunkmanPlayer player;
	
	public ApoSkunkmanAIPlayer(final ApoSkunkmanPlayer player) {
		super(player);
		this.player = player;
	}

	/**
	 * gibt die integer x-Koordinate des Spielers zurück
	 * @return gibt die x-Koordinate des Spielers zurück
	 */
	public int getPlayerX() {
		return (int)(this.player.getX() / (float)ApoSkunkmanConstants.TILE_SIZE);
	}
	
	/**
	 * gibt die integer y-Koordinate des Spielers zurück
	 * @return gibt die y-Koordinate des Spielers zurück
	 */
	public int getPlayerY() {
		return (int)(this.player.getY() / (float)ApoSkunkmanConstants.TILE_SIZE);
	}
	
	/**
	 * bewegt den Spieler in die übergebene Richtung, falls er dort hin gehen kann
	 * Möglichkeiten wären<br />
	 * ApoSkunkmanConstants.PLAYER_DIRECTION_DOWN für runter<br />
	 * ApoSkunkmanConstants.PLAYER_DIRECTION_UP für hoch<br />
	 * ApoSkunkmanConstants.PLAYER_DIRECTION_LEFT für links<br />
	 * ApoSkunkmanConstants.PLAYER_DIRECTION_RIGHT für rechts<br />
	 * @param direction : Möglichkeiten wären<br />
	 * ApoSkunkmanConstants.PLAYER_DIRECTION_DOWN für runter<br />
	 * ApoSkunkmanConstants.PLAYER_DIRECTION_UP für hoch<br />
	 * ApoSkunkmanConstants.PLAYER_DIRECTION_LEFT für links<br />
	 * ApoSkunkmanConstants.PLAYER_DIRECTION_RIGHT für rechts<br />
	 */
	public final void movePlayer(final int direction) {
		this.player.setNextDirection(direction);
	}
	
	/**
	 * bewegt den Spieler einen Tile nach rechts, falls er nach rechts gehen kann
	 */
	public final void movePlayerRight() {
		this.player.setNextDirection(ApoSkunkmanConstants.PLAYER_DIRECTION_RIGHT);
	}
	
	/**
	 * bewegt den Spieler einen Tile nach links, falls er nach links gehen kann
	 */
	public final void movePlayerLeft() {
		this.player.setNextDirection(ApoSkunkmanConstants.PLAYER_DIRECTION_LEFT);
	}
	
	/**
	 * bewegt den Spieler einen Tile nach oben, falls er nach oben gehen kann
	 */
	public final void movePlayerUp() {
		this.player.setNextDirection(ApoSkunkmanConstants.PLAYER_DIRECTION_UP);
	}
	
	/**
	 * bewegt den Spieler einen Tile nach unten, falls er nach unten gehen kann
	 */
	public final void movePlayerDown() {
		this.player.setNextDirection(ApoSkunkmanConstants.PLAYER_DIRECTION_DOWN);
	}
	
	/**
	 * veranlasst den Spieler einen Skunkman zu legen
	 */
	public final void laySkunkman() {
		if (super.canPlayerLayDownSkunkman()) {
			this.player.setLaySkunkman(true);
		}
	}
	
	
	/**
	 * malt eine rote Linie an die übergebene Stelle für die übergebene Zeit in ms
	 * @param x1 : erster x-Wert
	 * @param y1 : erster y-Wert
	 * @param x2 : zweiter x-Wert
	 * @param y2 : zweiter y-Wert
	 * @param time : Dauer der Anzeige
	 */
	public void drawLine(float x1, float y1, float x2, float y2, int time) {
		this.drawLine(x1, y1, x2, y2, time, Color.RED);
	}
	
	/**
	 * malt eine Linie an die übergebene Stelle für die übergebene Zeit in ms
	 * @param x1 : erster x-Wert
	 * @param y1 : erster y-Wert
	 * @param x2 : zweiter x-Wert
	 * @param y2 : zweiter y-Wert
	 * @param time : Dauer der Anzeige
	 * @param color : Farbe der Linie
	 */
	public void drawLine(float x1, float y1, float x2, float y2, int time, Color color) {
		x1 = x1 * (float)ApoSkunkmanConstants.TILE_SIZE;
		y1 = y1 * (float)ApoSkunkmanConstants.TILE_SIZE;
		x2 = x2 * (float)ApoSkunkmanConstants.TILE_SIZE;
		y2 = y2 * (float)ApoSkunkmanConstants.TILE_SIZE;
		this.player.addDrawElement(new ApoSkunkmanPlayerDrawLine(x1, y1, x2, y2, time, color));
	}
	
	/**
	 * malt einen roten Kreis an die übergebene Stelle für die übergebene Zeit in ms
	 * @param x : x-Wert des Mittelpunktes
	 * @param y : y-Wert des Mittelpunktes
	 * @param radius : der Radius
	 * @param bFill : Boolean-Wert der angibt, ob der Kreis ausgemalt werden soll oder nicht
	 * @param time : Dauer der Anzeige
	 */
	public void drawCircle(float x, float y, float radius, boolean bFill, int time) {
		this.drawCircle(x, y, radius, bFill, time, Color.RED);
	}
	
	/**
	 * malt einen Kreis an die übergebene Stelle für die übergebene Zeit in ms
	 * @param x : x-Wert des Mittelpunktes
	 * @param y : y-Wert des Mittelpunktes
	 * @param radius : der Radius
	 * @param bFill : Boolean-Wert der angibt, ob der Kreis ausgemalt werden soll oder nicht
	 * @param time : Dauer der Anzeige
	 * @param color : Farbe des Kreises
	 */
	public void drawCircle(float x, float y, float radius, boolean bFill, int time, Color color) {
		x = x * (float)ApoSkunkmanConstants.TILE_SIZE;
		y = y * (float)ApoSkunkmanConstants.TILE_SIZE;
		radius = radius * (float)ApoSkunkmanConstants.TILE_SIZE;
		this.player.addDrawElement(new ApoSkunkmanPlayerDrawCircle(x, y, radius, bFill, time, color));
	}
	
	/**
	 * malt ein rotes Rechteck an die übergebene Stelle für die übergebene Zeit in ms
	 * @param x : x-Wert der linken oberen Ecke des Rechteckes
	 * @param y : y-Wert der linken oberen Ecke des Rechteckes
	 * @param width : Breite des Rechteckes
	 * @param height : Hoehe des Rechteckes
	 * @param bFill : Boolean-Wert der angibt, ob das Rechteckt ausgemalt werden soll oder nicht
	 * @param time : Dauer der Anzeige
	 */
	public void drawRect(float x, float y, float width, float height, boolean bFill, int time) {
		this.drawRect(x, y, width, height, bFill, time, Color.RED);
	}
	
	/**
	 * malt ein Rechteck an die übergebene Stelle für die übergebene Zeit in ms
	 * @param x : x-Wert der linken oberen Ecke des Rechteckes
	 * @param y : y-Wert der linken oberen Ecke des Rechteckes
	 * @param width : Breite des Rechteckes
	 * @param height : Hoehe des Rechteckes
	 * @param bFill : Boolean-Wert der angibt, ob das Rechteckt ausgemalt werden soll oder nicht
	 * @param time : Dauer der Anzeige
	 * @param color : Farbe des Rechteckes
	 */
	public void drawRect(float x, float y, float width, float height, boolean bFill, int time, Color color) {
		x = x * (float)ApoSkunkmanConstants.TILE_SIZE;
		y = y * (float)ApoSkunkmanConstants.TILE_SIZE;
		width = width * (float)ApoSkunkmanConstants.TILE_SIZE;
		height = height * (float)ApoSkunkmanConstants.TILE_SIZE;
		this.player.addDrawElement(new ApoSkunkmanPlayerDrawRect(x, y, width, height, bFill, time, color));
	}
	
	/**
	 * übergibt eine Nachricht ins Debugfenster, wenn der Debugmodus an ist
	 * @param message : Nachricht, die im Debugframe angezeigt werden
	 */
	public final void addMessage(String message) {
		this.player.addMessage(message);
	}

}
