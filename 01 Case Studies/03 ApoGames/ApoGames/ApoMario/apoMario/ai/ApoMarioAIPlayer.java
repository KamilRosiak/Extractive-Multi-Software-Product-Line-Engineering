package apoMario.ai;

import java.awt.Color;

import apoMario.ApoMarioConstants;
import apoMario.entity.ApoMarioPlayer;
import apoMario.help.ApoMarioPlayerDrawCircle;
import apoMario.help.ApoMarioPlayerDrawLine;
import apoMario.help.ApoMarioPlayerDrawRect;
import apoMario.level.ApoMarioLevel;

/**
 * Klasse für den Spieler in der KI
 * @author Dirk Aporius
 *
 */
public class ApoMarioAIPlayer extends ApoMarioAIEntity {
	
	/** Spielerobjekt */
	private ApoMarioPlayer player;
	/** das Levelobjekt */
	private ApoMarioLevel level;
	
	/**
	 * Konstruktur
	 * @param player : Spielerobjekt
	 */
	public ApoMarioAIPlayer(ApoMarioPlayer player, ApoMarioLevel level) {
		super(player, level, (int)ApoMarioConstants.getStartX(player));
		this.player = player;
		this.level = level;
	}
	
	/**
	 * gibt den derzeitigen MarioTyp zurück
	 * entweder ApoMarioConstants.PLAYER_TYPE_LITTLE, ApoMarioConstants.PLAYER_TYPE_BIG oder
	 * ApoMarioConstants.PLAYER_TYPE_FIRE
	 * @return derzeitigen MarioTyp
	 */
	public int getType() {
		return this.player.getType();
	}
	
	/**
	 * veranlasst den Spieler schnell zu rennen
	 */
	public void runFast() {
		this.player.setBSpeed(true);
	}
	
	/**
	 * veranlasst den Spieler normal zu laufen
	 */
	public void runNormal() {
		this.player.setBSpeed(false);
	}
	
	/**
	 * veranlasst den Spieler nach links zu laufen
	 */
	public void runLeft() {
		this.player.setGoalVecX(-ApoMarioConstants.PLAYER_MAX_VEC_X_FAST);
	}
	
	/**
	 * veranlasst den Spieler nach rechts zu laufen
	 */
	public void runRight() {
		this.player.setGoalVecX(ApoMarioConstants.PLAYER_MAX_VEC_X_FAST);
	}
	
	/**
	 * veranlasst den Spieler stehen zu bleiben
	 */
	public void runStand() {
		this.player.setGoalVecX(0);		
	}
	
	/**
	 * veranlasst den Spieler stehen zu bleiben
	 */
	public void duck() {
		this.player.setBDown(true);		
	}
	
	/**
	 * gibt zurück, ob ein Spieler springen kann
	 * @return 
	 */
	public boolean canJump() {
		return this.player.canJump();
	}
	
	/**
	 * veranlasst den Spieler hoch zu springen
	 */
	public void jump() {
		if (this.canJump()) {
			this.player.setBNextJump(true);
		}
	}
	
	/**
	 * gibt zurück, ob der Spieler gerade unverwundbar ist<br />
	 * z.B. wenn er einen Schaden abbekommen hat,<br />
	 * ist er fuer einige Zeit danach unverwundbar<br />
	 * @return TRUE Spieler ist unverwundbar, else FALSE
	 */
	public boolean isImmortal() {
		if (this.player.getDamageTime() != 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * stoppt das ganze Spiel (mit der 'p' Taste kann es fortgesetzt werden)<br />
	 * funktioniert nur im Debugmodus<br />
	 */
	public void stopGame() {
		if (ApoMarioConstants.DEBUG) {
			this.level.getComponent().setBPause(true);
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
		x1 = x1 * (float)ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE + super.startX * (float)ApoMarioConstants.TILE_SIZE * (float)ApoMarioConstants.APP_SIZE;
		y1 = y1 * (float)ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE + this.level.getComponent().getChangeY();
		x2 = x2 * (float)ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE + super.startX * (float)ApoMarioConstants.TILE_SIZE * (float)ApoMarioConstants.APP_SIZE;
		y2 = y2 * (float)ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE + this.level.getComponent().getChangeY();
		this.player.addDrawElement(new ApoMarioPlayerDrawLine(x1, y1, x2, y2, time, color));
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
		x = x * (float)ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE + super.startX * (float)ApoMarioConstants.TILE_SIZE * (float)ApoMarioConstants.APP_SIZE;
		y = y * (float)ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE + this.level.getComponent().getChangeY();
		radius = radius * (float)ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE;
		this.player.addDrawElement(new ApoMarioPlayerDrawCircle(x, y, radius, bFill, time, color));
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
		x = x * (float)ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE + this.startX * (float)ApoMarioConstants.TILE_SIZE * (float)ApoMarioConstants.APP_SIZE;
		y = y * (float)ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE + this.level.getComponent().getChangeY();
		width = width * (float)ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE;
		height = height * (float)ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE;
		this.player.addDrawElement(new ApoMarioPlayerDrawRect(x, y, width, height, bFill, time, color));
	}
	
	/**
	 * fügt eine Nachricht im Debugfenster hinzu
	 * @param message : neue Nachricht
	 */
	public void addMessage(String message) {
		this.player.addMessage(message);
	}
}