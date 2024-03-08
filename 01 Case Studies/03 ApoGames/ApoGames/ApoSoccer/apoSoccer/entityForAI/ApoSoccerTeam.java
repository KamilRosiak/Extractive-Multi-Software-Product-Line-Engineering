package apoSoccer.entityForAI;

import java.awt.Color;

import apoSoccer.ApoSoccerConstants;
import apoSoccer.entity.ApoPlayer;
import apoSoccer.entity.ApoTeam;

/**
 * Klasse, die einen eigenen Spieler darstellt
 * @author Dirk Aporius
 *
 */
public class ApoSoccerTeam extends ApoSoccerEnemy {
	
	private ApoPlayer player;
	
	public ApoSoccerTeam(ApoPlayer player, ApoTeam team) {
		super(player, team);
		this.player = player;
	}
	
	/**
	 * verändert die Sichtlinie des Spielers </br>
	 * maximale Veränderung: MAX_CHANGE_LINEOFSIGHT</br>
	 * Dabei gilt noch zu beachten, dass ein Spieler, der schneller läuft, sich nicht so schnell drehen kann
	 * wie jemand der steht</br>
	 * change the line of sight by the given value</br>
	 * @param change : Um wieviel ° soll der Winkel verändert werden
	 */
	public void setLineOfSight(int change) {
		int plus = (int)(this.player.getSpeed() / ApoSoccerConstants.MAX_SPEED_PLAYER * (ApoSoccerConstants.MAX_CHANGE_LINEOFSIGHT - ApoSoccerConstants.MIN_CHANGE_LINEOFSIGHT));
		if (change > ApoSoccerConstants.MAX_CHANGE_LINEOFSIGHT - plus) {
			change = ApoSoccerConstants.MAX_CHANGE_LINEOFSIGHT - plus;
		} else if (change < -ApoSoccerConstants.MAX_CHANGE_LINEOFSIGHT + plus) {
			change = -ApoSoccerConstants.MAX_CHANGE_LINEOFSIGHT + plus;
		}
		int newLineOfSight = this.player.getLineOfSight() + change;
		if (newLineOfSight < 0) {
			newLineOfSight = 360 + newLineOfSight;
		} else if (newLineOfSight > 360) {
			newLineOfSight = newLineOfSight - 360;
		}
		if (!this.player.isBPlayerHuman()) {
			this.player.setNextLineOfSightChange(newLineOfSight);
		}
	}
	
	/**
	 * setzt die Geschwindigkeit mit der gelaufen werden soll auf den übergebenen Wert</br>
	 * der Wert muss zwischen 0 und 100 liegen</br>
	 * wobei 100 das schnellste ist</br>
	 * Das wird dann die AimSpeed, weil der Spieler erst auf die Geschwindigkeit
	 * beschleunigen muss</br>
	 * Er kann höchstens so schnell laufen, wie seine derzeitige Freshness hergibt,
	 * dass heisst, wenn man 100 laufen will, aber einen Freshness Wert von 70 hat, dann
	 * kann der Spieler nur auf 70 beschleunigen</br>
	 * set the aimSpeed of the player at the given value</br>
	 * @param speed : neuer AimSpeed / new aimspeed
	 */
	public void setSpeed(int speed) {
		if (speed > ApoSoccerConstants.MAX_SPEED_PLAYER) {
			speed = ApoSoccerConstants.MAX_SPEED_PLAYER;
		} else if (speed < ApoSoccerConstants.MIN_SPEED_PLAYER) {
			speed = ApoSoccerConstants.MIN_SPEED_PLAYER;
		}
		if (!this.player.isBPlayerHuman()) {
			this.player.setSpeed((int)speed);
		}
	}
	
	/**
	 * veranlasst den Spieler zu schießen</br>
	 * das funktioniert nur, wenn der Ball in der Nähe ist</br>
	 * je stärker der Ball geschossen wird, desto mehr geht der Frischewert runter</br>
	 * und desto größer die Chance das der Ball leicht verzieht und nicht genau in die
	 * Richtung geht, wie gewünscht</br>
	 * Die zulässige Geschwindigkeit liegt zwischen 0 und 150</br>
	 * the player can shoot when the ball is in your area</br>
	 * @param shootDirection
	 * @param shootStrength
	 */
	public void setShoot(int shootDirection, int shootStrength) {
		if (!this.player.isBPlayerHuman()) {
			this.player.setShoot(shootDirection, shootStrength);
		}
	}
	
	/**
	 * gibt zurück, ob der Spieler sprechen darf oder nicht </br>
	 * returns TRUE, when the player can speak, else FALSE
	 * @return TRUE, Spieler darf sprechen, FALSE Spieler darf nicht sprechen, </br>
	 * returns TRUE, when the player can speak, else FALSE
	 */
	public boolean canSpeak() {
		return this.player.isBAllowSpeak();
	}
	
	/**
	 * gibt zurück, welchen Text aus dem am Anfang übergebenen Speakarray gesagt werden soll </br>
	 * returns TRUE, when the given text can be spoken else FALSE
	 * @param textIndex : Welcher Text aus dem am Anfang übergebenen Speakarray soll gesagt werden soll
	 * @return TRUE, es kann gesprochen werden, FALSE, es kann nicht gesprochen werden, </br>
	 * returns TRUE, when the given text can be spoken else FALSE
	 */
	public boolean speakText(int textIndex) {
		return this.player.speakText(textIndex);
	}
	
	/**
	 * fügt den übergebenen String im Debug Ausgabefenster hinzu </br>
	 * @param addText : Der String, welcher im Debug Fenster angezeigt werden soll
	 * @return TRUE, wenn ein Text angezeigt werden kann, sonst FALSE
	 */
	public boolean addDebugText(String addText) {
		return this.player.addDebugText(addText);
	}

	/**
	 * malt eine Line zwischen 2 übergebenen Punkten einer übergebenen Farbe für eine bestimmte Zeit auf das Spielfeld </br>
	 * ist vor allem für DebugZwecke gedacht </br>
	 * in the debug mode you can draw a line with that method
	 * @param startX : X-Wert des ersten Punktes für die Linie / x-value of the first point
	 * @param startY : Y-Wert des ersten Punktes für die Linie / y-value of the first point
	 * @param endX : X-Wert des zweiten Punktes für die Linie / x-value of the second point
	 * @param endY : Y-Wert des zweiten Punktes für die Linie / y-value of the second point
	 * @param time : Zeitangabe in Millisekunden, wie lange die Linie gemalt werden soll / time in ms how long the line should be drawn
	 * @param color : Farbe, welche die Linie haben soll / color of the line
	 */
	public void drawLine(int startX, int startY, int endX, int endY, int time, Color color) {
		this.drawLine(startX, startY, endX, endY, time, color, 1);
	}

	/**
	 * malt eine Line zwischen 2 übergebenen Punkten einer übergebenen Farbe für eine bestimmte Zeit auf das Spielfeld </br>
	 * ist vor allem für DebugZwecke gedacht </br>
	 * in the debug mode you can draw a line with that method 
	 * @param startX : X-Wert des ersten Punktes für die Linie / x-value of the first point
	 * @param startY : Y-Wert des ersten Punktes für die Linie / y-value of the first point
	 * @param endX : X-Wert des zweiten Punktes für die Linie / x-value of the second point
	 * @param endY : Y-Wert des zweiten Punktes für die Linie / y-value of the second point
	 * @param time : Zeitangabe in Millisekunden, wie lange die Linie gemalt werden soll / time in ms how long the line should be drawn
	 * @param color : Farbe, welche die Linie haben soll / color of the line
	 * @param width : Breite der Linie / width of the line
	 */
	public void drawLine(int startX, int startY, int endX, int endY, int time, Color color, int width) {
		this.player.addLine(new ApoSoccerDebugLine(startX, startY, endX, endY, time, color, width));
	}
	
	/**
	 * malt einen Kreis mit einer bestimmten Farbe für eine bestimmte Zeit auf das Spielfeld </br>
	 * in the debug mode you can draw a circle with that methd </br>
	 * @param middleX : Mittelpunkt X des Kreises / x-value of the circle
	 * @param middleY : Mittelpunkt Y des Kreises / y-value of the circle
	 * @param diameter : Durchmesser des Kreises / diameter of the circle
	 * @param time : Zeitangabe in Millisekunden, wie lange der Kreis gemalt werden soll / time in ms how long the circle should be drawn
	 * @param color : Farbe, welche der Kreis haben soll / color of the circle
	 */
	public void drawCircle(int middleX, int middleY, int diameter, int time, Color color) {
		this.player.addLine(new ApoSoccerDebugLine(middleX, middleY, middleX, middleY, time, color, diameter, true));
	}
}
