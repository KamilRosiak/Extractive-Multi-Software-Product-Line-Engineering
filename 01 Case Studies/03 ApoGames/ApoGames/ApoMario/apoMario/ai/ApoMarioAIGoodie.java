package apoMario.ai;

import apoMario.entity.ApoMarioCoin;
import apoMario.entity.ApoMarioEnemy;
import apoMario.entity.ApoMarioGoodieFireflower;
import apoMario.entity.ApoMarioGoodieMushroom;
import apoMario.level.ApoMarioLevel;

/**
 * Klasse, die ein Goodie darstellt<br />
 * es gibt 3 Arten von Goodies:<br />
 * Die Münzen/Donuts zum Punkte sammeln<br />
 * Einen Pilz/Burger zum Groß werden<br />
 * Eine Feuerblume/Lolli, um zum Feuermario zu werden<br />
 * @author Dirk Aporius
 *
 */
public class ApoMarioAIGoodie extends ApoMarioAIEntity {

	private ApoMarioEnemy goodie;
	
	public ApoMarioAIGoodie(ApoMarioEnemy entity, ApoMarioLevel level, int startX) {
		super(entity, level, startX);
		this.goodie = entity;
	}
	
	/**
	 * gibt zurück, ob es ein Coin ist
	 * @return TRUE, es ist ein Coin, else FALSE
	 */
	public boolean isCoin() {
		if (this.goodie instanceof ApoMarioCoin) {
			return true;
		}
		return false;
	}
	
	/**
	 * gibt zurück, ob es ein Pilz ist
	 * @return TRUE, es ist ein Pilz, else FALSE
	 */
	public boolean isMushroom() {
		if (this.goodie instanceof ApoMarioGoodieMushroom) {
			return true;
		}
		return false;
	}
	
	/**
	 * gibt zurück, ob es eine Feuerpflanze ist
	 * @return TRUE, es ist eine Feuerpflanze, else FALSE
	 */
	public boolean isFireflower() {
		if (this.goodie instanceof ApoMarioGoodieFireflower) {
			return true;
		}
		return false;
	}
	
	/**
	 * gibt die Anzahl der Punkte zurück, die es gibt, wenn dieses Goodie eingesammelt wird
	 * @return gibt die Anzahl der Punkte zurück, die es gibt, wenn dieses Goodie eingesammelt wird
	 */
	public int getPoints() {
		return this.goodie.getPoints();
	}

}
