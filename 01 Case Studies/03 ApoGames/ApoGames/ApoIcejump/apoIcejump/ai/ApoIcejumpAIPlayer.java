package apoIcejump.ai;

import apoIcejump.ApoIcejumpConstants;
import apoIcejump.entity.ApoIcejumpPlayer;

/**
 * Klasse, die euren Spieler repräsentiert<br />
 * class for your player<br />
 * @author Dirk Aporius
 *
 */
public class ApoIcejumpAIPlayer extends ApoIcejumpAIEnemy {

	private ApoIcejumpPlayer entity;
	
	public ApoIcejumpAIPlayer(ApoIcejumpPlayer entity) {
		super(entity);
		
		this.entity = entity;
	}
	
	/**
	 * setzt die neue Geschwindigkeit auf den übergebenen Wert<br />
	 * er muss aber zwischen -ApoIcejumpConstants.PLAYER_MAX_VEC_X (maximale Geschwindigkeit nach links pro ms)<br />
	 * und ApoIcejumpConstants.PLAYER_MAX_VEC_X (maximale Geschwindigkeit nach rechts pro ms) liegen<br />
	 * set the new speed in x-direction<br />
	 * the value has to be between -ApoIcejumpConstants.PLAYER_MAX_VEC_X (max speed left per ms)<br />
	 * and ApoIcejumpConstants.PLAYER_MAX_VEC_X (max speed right per ms)<br />
	 * @param vecX
	 */
	public void setVecX(float vecX) {
		if (vecX > ApoIcejumpConstants.PLAYER_MAX_VEC_X) {
			vecX = ApoIcejumpConstants.PLAYER_MAX_VEC_X;
		} else if (vecX < -ApoIcejumpConstants.PLAYER_MAX_VEC_X) {
			vecX = -ApoIcejumpConstants.PLAYER_MAX_VEC_X;
		}
		this.entity.setVecX(vecX);
	}

}
