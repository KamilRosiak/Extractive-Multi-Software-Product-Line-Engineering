package apoIcejump.ai;

import apoIcejump.entity.ApoIcejumpGoodie;

/**
 * Klasse, die ein Goodie darstellt<br />
 * class for a goodie
 * @author Dirk Aporius
 *
 */
public class ApoIcejumpAIGoodies extends ApoIcejumpAIEntity {

	private ApoIcejumpGoodie entity;
	
	public ApoIcejumpAIGoodies(ApoIcejumpGoodie entity) {
		super(entity);
		
		this.entity = entity;
	}

	/**
	 * gibt zurück, welches Goodie es ist<br />
	 * returns what goodie it is<br />
	 * ApoIcejumpAIConstants.GOODIE_FIRE = Feuergoodie
	 * ApoIcejumpAIConstants.GOODIE_ICE_LITTLE = Goodie für 3 neue Eisblöcke
	 * ApoIcejumpAIConstants.GOODIE_ICE_BIG = Goodie für 6 neue Eisblöcke
	 * ApoIcejumpAIConstants.GOODIE_SLOWER = Goodie, um den Spieler langsamer zu machen (Vereisung)
	 * ApoIcejumpAIConstants.GOODIE_HIGHER = Goodie, um den Spieler höher springen zu lassen
	 * ApoIcejumpAIConstants.GOODIE_FASTER = Goodie, um den Spieler für eine Zeitspanne schneller zu machen
	 * 
	 * @return gibt zurück, welches Goodie es ist / returns what goodie it is
	 */
	public int getGoodie() {
		return this.entity.getGoodie();
	}
}
