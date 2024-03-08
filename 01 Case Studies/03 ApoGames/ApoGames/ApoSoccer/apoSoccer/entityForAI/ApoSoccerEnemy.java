package apoSoccer.entityForAI;

import apoSoccer.entity.ApoForward;
import apoSoccer.entity.ApoGoalKeeper;
import apoSoccer.entity.ApoPlayer;
import apoSoccer.entity.ApoTeam;

/**
 * Klasse, die einen Spieler aus der gegnerischen Mannschaft darstellt
 * @author Dirk Aporius
 *
 */
public class ApoSoccerEnemy {

	/** der Spieler von dem die Informationen geholt werden */
	private ApoPlayer player;
	/** das Team wo der Spieler drin ist */
	private ApoTeam team;
	
	public ApoSoccerEnemy(ApoPlayer player, ApoTeam team) {
		this.player = player;
		this.team = team;
	}
	
	/**
	 * gibt die Tore des Teams zurück</br>
	 * returns the goal
	 * @return gibt die Tore des Teams zurück</br>
	 */
	public int getGoals() {
		return this.team.getGoals();
	}
	
	/**
	 * gibt zurück, ob der Spieler eine Frau ist oder ein Mann,</br> 
	 * return wheter the player is a woman or a man
	 * @return TRUE, Spieler ist eine Frau, sonst FALSE,</br>
	 * TRUE, player is a woman, FALSE -> player is a man
	 */
	public boolean isFemale() {
		return this.player.isBFemale();
	}
	
	/**
	 * gibt den Frischewert des Spielers zurück</br>
	 * maximal ist 100 (am frischesten), bei 20 ist er total erschöpft,</br> 
	 * returns the freshness value for the player, </br>
	 * the min Value is ApoSoccerConstants.MIN_FRESHNESS</br>
	 * @return gibt den Frischewert des Spielers zurück,</br> 
	 * returns the freshness value for the player
	 */
	public int getFreshness() {
		return (int)this.player.getFreshness();
	}
	
	/**
	 * gibt den maximal noch erreichbaren Frischewert des Spielers zurück</br>
	 * maximal ist 100 (am frischesten), bei 20 ist er total erschöpft, </br>
	 * return the max freshness value which can the player have </br>
	 * @return gibt den Frischewert des Spielers zurück, </br>
	 * return the max freshness value which can the player have 
	 */
	public int getMaxFreshness() {
		return (int)this.player.getMaxFreshness();
	}
	
	/**
	 * gibt den Namen des Spielers zurück,</br>
	 * returns the playername
	 * @return gibt den Namen des Spielers zurück,</br> 
	 * returns the playername
	 */
	public String getName() {
		return this.player.getName();
	}
	
	/**
	 * gibt TRUE zurück, wenn der Spieler ein Torwart ist, sonst FALSE</br>
	 * returns a boolean value, wheter the player is a goalkeeper (TRUE), or not (FALSE)
	 * @return gibt TRUE zurück, wenn der Spieler ein Torwart ist, sonst FALSE,</br> 
	 * returns a boolean value, wheter the player is a goalkeeper (TRUE), or not (FALSE)
	 */
	public boolean isGoalKeeper() {
		if (this.player instanceof ApoGoalKeeper) {
			return true;
		}
		return false;
	}
	
	/**
	 * gibt TRUE zurück, wenn der Spieler ein Stürmer ist, sonst FALSE, </br>
	 * returns a boolean value, wheter the player is a forward (TRUE), or not (FALSE)
	 * @return gibt TRUE zurück, wenn der Spieler ein Stürmer ist, sonst FALSE,</br> 
	 * returns a boolean value, wheter the player is a forward (TRUE), or not (FALSE)
	 */
	public boolean isForward() {
		if (this.player instanceof ApoForward) {
			return true;
		}
		return false;
	}
	
	/**
	 * gibt die x-Korrdinate des Spielers zurück,</br>
	 * returns the x-values of the player
	 * @return gibt die x-Korrdinate des Spielers zurück,</br> 
	 * returns the x-values of the player
	 */
	public int getX() {
		return (int)this.player.getX();
	}
	
	/**
	 * gibt die y-Korrdinate des Spielers zurück,</br>
	 * returns the y-values of the player
	 * @return gibt die y-Korrdinate des Spielers zurück,</br> 
	 * returns the y-values of the player
	 */
	public int getY() {
		return (int)this.player.getY();
	}
	
	/**
	 * gibt die derzeitige Geschwindigkeit des Spielers zurück,</br>
	 * returns the speed-value of the player
	 * @return gibt die derzeitige Geschwindigkeit des Spielers zurück,</br> 
	 * returns the speed-value of the player
	 */
	public int getSpeed() {
		return (int)this.player.getSpeed();
	}
	
	/**
	 * gibt die angestrebte Geschwindigkeit des Spielers zurück,</br> 
	 * returns the aimspeed-value of the player
	 * @return gibt die angestrebte Geschwindigkeit des Spielers zurück,</br>
	 * returns the aimspeed-value of the player
	 */
	public int getAimSpeed() {
		return (int)this.player.getAimSpeed();
	}
	
	/**
	 * gibt den Radius des Spielers zurück,</br> 
	 * returns the radius-value of the player
	 * @return gibt den Radius des Spielers zurück,</br> 
	 * returns the radius-value of the player
	 */
	public int getRadius() {
		return (int)this.player.getWidth();
	}
	
	/**
	 * gibt die derzeitige Blickrichtung des Spielers zurück</br>
	 * 360 oder 0 bedeutet er schaut nach Osten</br>
	 * 90 bedeutet er schaut in den Süden</br>
	 * 180 bedeutet er schaut in den Westen</br>
	 * 270 bedeutet er schaut in den Norden</br>
	 * returns the line of sight of the player</br>
	 * @return gibt die derzeitige Blickrichtung des Spielers zurück, </br>
	 * returns the line of sight of the player
	 */
	public int getLineOfSight() {
		return this.player.getLineOfSight();
	}
	
	/**
	 * gibt die Zeit die vergangen ist zurück,</br>
	 * returns the time which is elapsed in the game
	 * @return gibt die Zeit die vergangen ist zurück</br>
	 * returns the time which is elapsed in the game
	 */
	public int getTimeElapsed() {
		return (int)this.player.getTimeElapsed();
	}
	
	/**
	 * gibt die Zeit die vergangen ist zurück,</br>
	 * returns the time which is elapsed in the game
	 * @return gibt die Zeit die vergangen ist zurück</br>
	 * returns the time which is elapsed in the game
	 */
	public int getTimeInMinutes() {
		return (int)this.player.getTimeInMinutes();
	}
	
	/**
	 * gibt einen TRUE zurück, wenn der Ball in der Nähe ist und geschossen
	 * werden kann, sonst FALSE,</br>
	 * returns TRUE, if the player can shoot the ball, else FALSE
	 * @return gibt einen TRUE zurück, wenn der Ball in der Nähe ist und geschossen
	 * werden kann, sonst FALSE,</br>
	 * returns TRUE, if the player can shoot the ball, else FALSE
	 */
	public boolean canShoot() {
		if (this.player.getCanShootCounter() <= 0) {
			return this.player.isBCanShoot();
		} else {
			return false;
		}
	}
	
	/**
	 * gibt einen TRUE zurück, wenn der Ball in der Nähe des Spielers ist, sonst FALSE</br>
	 * returns TRUE when the ball is in your area
	 * @return gibt einen TRUE zurück, wenn der Ball in der Nähe des Spielers ist, sonst FALSE, </br>
	 * returns TRUE when the ball is in your area
	 */
	public boolean isBallInArea() {
		return this.player.isBBall();
	}
	
	/**
	 * gibt zurück, ob der Spieler dem Team angehört, welches von links nach rechts spielt
	 * oder von rechts nach links,</br>
	 * returns TRUE when the player plays from left to the right side, else FALSE
	 * @return TRUE, wenn der Ball ins RECHTE Tor muss, FALSE, wenn der Ball ins LINKE Tor muss,
	 * returns TRUE when the player plays from left to the right side, else FALSE
	 */
	public boolean isLeftTeam() {
		return this.player.isBLeft();
	}
	
	/**
	 * wird verwendet um herauszukriegen, wie groß der Winkel zwischen dem Spieler 
	 * und dem übergebenen Punkt liegt</br>
	 * return the angle between the player and the given point
	 * @param x : x-Koordinate des Punktes / x-value of the point
	 * @param y : y-Korrdinate des Punktes / y-value of the point
	 * @return Winkel / angle
	 */
	public int getAngleToPoint(float x, float y) {
		return (int)this.player.getAngleBetweenTwoPoints(x, y);
	}

	/**
	 * wird verwendet um herauszukriegen, wie groß der Winkel zwischen 2 Punkten ist</br>
	 * return the angle between the two points</br>
	 * @param x : x-Koordinate des Punktes / x-value of the point
	 * @param y : y-Korrdinate des Punktes / y-value of the point
	 * @param myX : x-Koordinate des MyPunktes / x-value of the point
	 * @param myY : y-Korrdinate des MyPunktes / y-value of the point
	 * @return Winkel / angle
	 */
	public int getAngleToPoint(float x, float y, float myX, float myY) {
		return (int)this.player.getAngleBetweenTwoPoints(x, y, myX, myY);
	}
	
	/**
	 * überprüft, ob sich der Spieler mit einem anderen Spieler berühren,</br>
	 * returns TRUE, when the player intersects with another player, else FALSE</br>
	 * @param entity : anderer Spieler / another player
	 * @return TRUE, falls sie sich berühren, sonst FALSE,</br>
	 * returns TRUE, when the player intersects with another player, else FALSE
	 */
	public boolean intersects(ApoSoccerEnemy entity) {
		return this.intersects(entity.getX(), entity.getY(), entity.getRadius());
	}
	
	/**
	 * überprüft, ob sich der Spieler mit dem übergebenen Kreis berühren</br>
	 * return TRUE, when the player intersects with the given values, else FALSE
	 * @param x : x-Koordinate des Mittelpunktes des Kreises / x-value of the given circle
	 * @param y : y-Koordinate des Mittelpunktes des Kreises / y-value of the given circle
	 * @param radius : Radius des Kreises / radius of the circle
	 * @return TRUE, falls sie sich berühren, sonst FALSE,</br>
	 * return TRUE, when the player intersects with the given values, else FALSE
	 */
	public boolean intersects(float x, float y, float radius) {
		return this.player.intersects(x, y, radius);
	}
}
