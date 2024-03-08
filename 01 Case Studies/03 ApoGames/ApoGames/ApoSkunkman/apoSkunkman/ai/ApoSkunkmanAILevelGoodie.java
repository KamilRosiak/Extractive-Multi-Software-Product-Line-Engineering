package apoSkunkman.ai;

import apoSkunkman.entity.ApoSkunkmanGoodie;

/**
 * Klasse die ein Goodie im Levelarray repr�sentiert und zur�ckgibt um welches Goodie es sich handelt<br />
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
	 * gibt das Goodie zur�ck<br />
	 * m�gliche Konstanten sind:<br />
	 * ApoSkunkmanAIConstants.GOODIE_GOOD_WIDTH f�r ein Goodie, das die Feuerrate um 1 erh�ht<br />
	 * ApoSkunkmanAIConstants.GOODIE_GOOD_SKUNKMAN f�r ein Goodie, das die Anzahl der maximal legbaren Skunkmans um 1 erh�ht<br />
	 * ApoSkunkmanAIConstants.GOODIE_GOOD_FAST f�r ein Goodie, das die Geschwindigkeit erh�ht<br />
	 * ApoSkunkmanAIConstants.GOODIE_GOOD_GOD f�r ein Goodie, das alle Werte maximiert<br />
	 * ApoSkunkmanAIConstants.GOODIE_BAD_WIDTH f�r ein Goodie, das die Feuerrate um 1 senkt<br />
	 * ApoSkunkmanAIConstants.GOODIE_BAD_SKUNKMAN f�r ein Goodie, das die Anzahl der maximal legbaren Skunkmans um 1 senkt<br />
	 * ApoSkunkmanAIConstants.GOODIE_BAD_FAST f�r ein Goodie, das die Geschwindigkeit senkt<br />
	 * ApoSkunkmanAIConstants.GOODIE_BAD_GOD f�r ein Goodie, das alle Werte des Spieler minimiert<br />
	 * @return gibt das Goodie zur�ck
	 */
	public int getGoodie() {
		return this.goodie.getGoodie();
	}
	
	/**
	 * gibt die Zeit zur�ck, wielange das Goodie noch sichtbar ist in Millisekunden
	 * @return gibt die Zeit zur�ck, wielange das Goodie noch sichtbar ist in Millisekunden
	 */
	public int getTimeLeftVisible() {
		return this.goodie.getTimeLeftVisible();
	}
}
