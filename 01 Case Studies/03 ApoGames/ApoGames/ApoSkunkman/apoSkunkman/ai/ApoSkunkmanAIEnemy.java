package apoSkunkman.ai;

import apoSkunkman.ApoSkunkmanConstants;
import apoSkunkman.entity.ApoSkunkmanPlayer;

/**
 * Klasse, die für die KI einen Gegner darstellt
 * @author Dirk Aporius
 *
 */
public class ApoSkunkmanAIEnemy extends ApoSkunkmanAIEntity {
	
	/** Spielerobjekt */
	private final ApoSkunkmanPlayer player;
	
	public ApoSkunkmanAIEnemy(ApoSkunkmanPlayer player) {
		super(player);
		this.player = player;
	}
	
	/**
	 * gibt zurück, ob die KI noch im Spiel ist oder nicht<br />
	 * auch schon aus dem Spiel ausgeschiedene KI's sind in dem Array mit drin, um zu schauen wieviele Punkte zu haben usw<br /> 
	 * @return TRUE, Entity ist sichtbar und damit noch im Spiel, FALSE Entity ist schon "verstunken"
	 */
	public final boolean isVisible() {
		return this.player.isBVisible();
	}
	
	/**
	 * gibt zurück, wie groß der Radius des "Stinkens" für das nächste gelegte Stinktier ist<br />
	 * dabei bedeutet ein Wert von 1, dass das Stinktier in jeder Richtung eine Ausweitung von 1 hat<br />
	 * Bsp: Bei einer Länge von 1 ist bei der Explosion über und unter und links und rechts vom Stinktier der Feuerradius 1 und beim Stinktier selber auch<br /> 
	 * @return gibt zurück, wie groß der Radius des "Stinkens" für das nächste gelegte Stinktier ist
	 */
	public final int getSkunkWidth() {
		return this.player.getCurWidth();
	}
	
	/**
	 * gibt die maximal mögliche Anzahl an Skunkmans des Spielers zurück
	 * @return gibt die maximal mögliche Anzahl an Skunkmans des Spielers zurück
	 */
	public final int getMaxSkunkman() {
		return this.player.getMaxSkunkman();
	}
	
	/**
	 * gibt die derzeit gelegte Anzahl an Skunkmans des Spielers zurück
	 * @return gibt die derzeit gelegte Anzahl an Skunkmans des Spielers zurück
	 */
	public final int getCurSkunkmanLay() {
		return this.player.getCurSkunkman();
	}
	
	/**
	 * gibt zurück, ob der Spieler derzeit ein Skunkman legen darf<br />
	 * @return TRUE, Spieler darf Skunkman legen, ansonsten FALSE
	 */
	public final boolean canPlayerLayDownSkunkman() {
		return this.player.canLaySkunkman();
	}
	
	/**
	 * gibt die mögliche Geschwindigkeit des Spielers pro Millisekunde zurück<br />
	 * dabei ist egal, ob sich der Spieler gerade bewegt oder nicht<br />
	 * @return gibt die mögliche Geschwindigkeit des Spielers pro Millisekunde zurück
	 */
	public final float getSpeed() {
		return this.player.getSpeed() * (float)ApoSkunkmanConstants.APPLICATION_SIZE / (float)ApoSkunkmanConstants.TILE_SIZE;
	}
	
	/**
	 * gibt an wieviel Millisekunden vergehen werden, damit diese Entity ein komplettes Tile läuft<br />
	 * und somit wieder nachdenken darf<br />
	 * @return gibt an wieviel Millisekunden vergehen werden, damit diese Entity ein komplettes Tile läuft
	 */
	public final int getMSForOneTile() {
		return (int)(1 / this.getSpeed()) + ApoSkunkmanConstants.WAIT_TIME_THINK;
	}
	
	/**
	 * gibt zurück, wann diese Entity den nächsten Punkt erreicht hat, um nachzudenken und sich zu entscheiden<br />
	 * falls die Entity steht, dann gibt diese Methode -1 zurück<br />
	 * @return gibt zurück, wann diese Entity den nächsten Punkt erreicht hat, um nachzudenken und sich zu entscheiden<br />
	 * falls die Entity steht, dann gibt diese Methode -1 zurück<br />
	 */
	public final int getMSUntilNextTile() {
		if (this.getVelocityX() > 0) {
			float dif = (int)(this.getX() + 1) - this.getX();
			return (int)(dif / this.getSpeed()) + ApoSkunkmanConstants.WAIT_TIME_THINK;
		} else if (this.getVelocityX() < 0) {
			float dif = this.getX() - (int)(this.getX());
			return (int)(dif / this.getSpeed()) + ApoSkunkmanConstants.WAIT_TIME_THINK;
		} else if (this.getVelocityY() > 0) {
			float dif = (int)(this.getY() + 1) - this.getY();
			return (int)(dif / this.getSpeed()) + ApoSkunkmanConstants.WAIT_TIME_THINK;
		} else if (this.getVelocityY() < 0) {
			float dif = this.getY() - (int)(this.getY());
			return (int)(dif / this.getSpeed()) + ApoSkunkmanConstants.WAIT_TIME_THINK;
		}
		return -1;
	}
	
	/**
	 * gibt die aktuelle Geschwindigkeit in x-Richtung wieder
	 * @return gibt die aktuelle Geschwindigkeit in x-Richtung wieder
	 */
	public final float getVelocityX() {
		return this.player.getSpeed() * this.player.getVelocityX() * (float)ApoSkunkmanConstants.APPLICATION_SIZE / (float)ApoSkunkmanConstants.TILE_SIZE;
	}
	
	/**
	 * gibt die aktuelle Geschwindigkeit in y-Richtung wieder
	 * @return gibt die aktuelle Geschwindigkeit in x-Richtung wieder
	 */
	public final float getVelocityY() {
		return this.player.getSpeed() * this.player.getVelocityY() * (float)ApoSkunkmanConstants.APPLICATION_SIZE / (float)ApoSkunkmanConstants.TILE_SIZE;
	}
	
	/**
	 * gibt zurück, ob der Spieler sich derzeit bewegt oder nicht
	 * @return TRUE, player bewegt sich, ansonsten FALSE
	 */
	public final boolean isMoving() {
		return this.player.isMoving();
	}
	
	/**
	 * gibt zurück, welcher Spieler es ist<br />
	 * Ist interessant, um herauszufinden, wer welche Bombe gelegt hat<br />
	 * @return gibt zurück, welcher Spieler es ist
	 */
	public final int getPlayer() {
		return this.player.getPlayer();
	}
}
