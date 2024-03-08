package apoSoccer.entityForAI;

import apoSoccer.ApoSoccerConstants;
import apoSoccer.entity.ApoPlayer;
import apoSoccer.entity.ApoTeam;

/**
 * Klasse, um die Spieler auf eine bestimmte Position zu setzen
 * @author Dirk Aporius
 *
 */
public class ApoSoccerTeamSet extends ApoSoccerTeam {

	private ApoPlayer player;
	
	public ApoSoccerTeamSet(ApoPlayer player, ApoTeam team) {
		super(player, team);
		
		this.player = player;
	}

	public boolean init() {
		if (!this.player.isBRunPlayer()) {
			this.player.init();
		}
		return true;
	}
	
	/**
	 * setzt die Position des Spielers auf den übergebenen Wert, falls es möglich ist
	 * sonst gibt der FALSE zurück </br>
	 * returns TRUE, when the player can be set at the specific position, else FALSE
	 * @param x : neuer X-Wert des Spielers / new x-value
	 * @param y : neuer Y-Wert des Spielers / new y-value
	 * @param lineOfSight : neue Blickrichtung / new line of sight
	 * @return TRUE, wenn neue Koordinaten ok, sonst FALSE,</br>
	 * returns TRUE, when the player can be set at the specific position, else FALSE
	 */
	public boolean setPosition(int x, int y, int lineOfSight) {
		if (this.player.getStartX() < ApoSoccerConstants.FIELD_WIDTH/2) {
			if (ApoSoccerConstants.LEFT_SIDE_REC.contains(x - super.getRadius(), y - super.getRadius(), super.getRadius() * 2, super.getRadius() * 2)) {
				if (!ApoSoccerConstants.MIDDLE_CIRCLE_ELLIPSE.intersects(x - super.getRadius(), y - super.getRadius(), super.getRadius() * 2, super.getRadius() * 2)) {
					return this.player.setNewPosition(x, y, lineOfSight);
				}
			}
		} else {
			if (ApoSoccerConstants.RIGHT_SIDE_REC.contains(x - super.getRadius(), y - super.getRadius(), super.getRadius() * 2, super.getRadius() * 2)) {
				if (!ApoSoccerConstants.MIDDLE_CIRCLE_ELLIPSE.intersects(x - super.getRadius(), y - super.getRadius(), super.getRadius() * 2, super.getRadius() * 2)) {
					return this.player.setNewPosition(x, y, lineOfSight);
				}
			}
		}
		return false;
	}
	
	/**
	 * gibt zurück, ob das Team Anstoss hat oder nicht </br>
	 * returns wheter the team has the kick off or not </br>
	 * @return TRUE, Team hat Anstoss, FALSE, Team hat keinen Anstoss </br>
	 * TRUE, team has kick off, else FALSE
	 */
	public boolean isKickOff() {
		return this.player.isBKickOff();
	}
	
}
