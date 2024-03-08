package apoSkunkman.ai;

import apoSkunkman.entity.ApoSkunkmanGoodie;

/**
 * Klasse die ein Goodie im Levelarray repräsentiert und zurückgibt um welches Goodie es sich handelt<br />
 * und wie lange es noch sichtbar ist bevor es verschwindet<br />
 * @author Dirk Aporius
 *
 */
public class ApoSkunkmanAILevelGoodie extends ApoSkunkmanAILevelEntity {

	private final ApoSkunkmanGoodie goodie;
	
	public ApoSkunkmanAILevelGoodie(ApoSkunkmanGoodie entity) {
		super(entity);
		this.goodie = entity;
	}

	/**
	 * gibt das Goodie zurück<br />
	 * mögliche Konstanten sind:<br />
	 * ApoSkunkmanAIConstants.GOODIE_GOOD_WIDTH für ein Goodie, das die Feuerrate um 1 erhöht<br />
	 * ApoSkunkmanAIConstants.GOODIE_GOOD_SKUNKMAN für ein Goodie, das die Anzahl der maximal legbaren Skunkmans um 1 erhöht<br />
	 * ApoSkunkmanAIConstants.GOODIE_GOOD_FAST für ein Goodie, das die Geschwindigkeit erhöht<br />
	 * ApoSkunkmanAIConstants.GOODIE_GOOD_GOD für ein Goodie, das alle Werte maximiert<br />
	 * ApoSkunkmanAIConstants.GOODIE_BAD_WIDTH für ein Goodie, das die Feuerrate um 1 senkt<br />
	 * ApoSkunkmanAIConstants.GOODIE_BAD_SKUNKMAN für ein Goodie, das die Anzahl der maximal legbaren Skunkmans um 1 senkt<br />
	 * ApoSkunkmanAIConstants.GOODIE_BAD_FAST für ein Goodie, das die Geschwindigkeit senkt<br />
	 * ApoSkunkmanAIConstants.GOODIE_BAD_GOD für ein Goodie, das alle Werte des Spieler minimiert<br />
	 * @return gibt das Goodie zurück
	 */
	public int getGoodie() {
		return this.goodie.getGoodie();
	}
	
	/**
	 * gibt die Zeit zurück, wielange das Goodie noch sichtbar ist in Millisekunden
	 * @return gibt die Zeit zurück, wielange das Goodie noch sichtbar ist in Millisekunden
	 */
	public int getTimeLeftVisible() {
		return this.goodie.getTimeLeftVisible();
	}
}
