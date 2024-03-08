package apoMario.ai;

import apoMario.entity.ApoMarioCannonCannon;
import apoMario.entity.ApoMarioEnemy;
import apoMario.entity.ApoMarioFireball;
import apoMario.entity.ApoMarioFlower;
import apoMario.entity.ApoMarioGumba;
import apoMario.entity.ApoMarioKoopa;
import apoMario.entity.ApoMarioShell;
import apoMario.level.ApoMarioLevel;

/**
 * Klasse, die den Gegner repräsentiert
 * @author Dirk Aporius
 */
public class ApoMarioAIEnemy extends ApoMarioAIEntity {

	/** das Gegnerobjekt */
	private ApoMarioEnemy enemy;
	
	/**
	 * Konstruktor
	 * @param entity
	 * @param level
	 */
	public ApoMarioAIEnemy(ApoMarioEnemy entity, ApoMarioLevel level, int startX) {
		super(entity, level, startX);

		this.enemy = entity;
	}

	/**
	 * gibt zurück, ob der Gegner fliegt
	 * @return TRUE, Gegner fliegt, else FALSE
	 */
	public boolean isFlyingEnemy() {
		return this.enemy.isBFly();
	}
	
	/**
	 * gibt zurück, ob der Gegner unverwundbar ist
	 * @return TRUE Gegner unverwunderbar, else FALSE
	 */
	public boolean isImmortalEnemy() {
		return this.enemy.isBImmortal();
	}
	
	/**
	 * gibt die Anzahl der Punkte zurück, die es gibt, wenn man den Gegner erledigt
	 * @return gibt die Anzahl der Punkte zurück, die es gibt, wenn man den Gegner erledigt
	 */
	public int getPoints() {
		return this.enemy.getPoints();
	}
	
	/**
	 * falls es ein Gumba ist TRUE, ansonsten FALSE
	 * @return falls es eine Gumba ist TRUE, ansonsten FALSE
	 */
	public boolean isGumbaType() {
		if (this.enemy instanceof ApoMarioGumba) {
			return true;
		}
		return false;
	}
	
	/**
	 * falls es ein Koopa ist also eine Schildkröte TRUE, ansonsten FALSE
	 * @return falls es eine Koopa ist also eine Schildkröte ist TRUE, ansonsten FALSE
	 */
	public boolean isKoopaType() {
		if (this.enemy instanceof ApoMarioKoopa) {
			return true;
		}
		return false;
	}
	
	/**
	 * falls es eine Pflanze aus der Röhre ist TRUE, ansonsten FALSE
	 * @return falls es eine Pflanze aus der Röhre ist TRUE, ansonsten FALSE
	 */
	public boolean isFlowerType() {
		if (this.enemy instanceof ApoMarioFlower) {
			return true;
		}
		return false;
	}
	
	/**
	 * falls es eine Kannone ist TRUE, ansonsten FALSE
	 * @return falls es eine Kannone ist TRUE, ansonsten FALSE
	 */
	public boolean isCannonType() {
		if (this.enemy instanceof ApoMarioCannonCannon) {
			return true;
		}
		return false;
	}
	
	/**
	 * falls es ein vom Spieler abgeschossener Fireball ist wird TRUE zurückgegeben, ansonsten FALSE
	 * @return falls es ein ein vom Spieler abgeschossener Fireball ist wird TRUE zurückgegeben, ansonsten FALSE
	 */
	public boolean isFireballType() {
		if (this.enemy instanceof ApoMarioFireball) {
			return true;
		}
		return false;
	}
	
	/**
	 * falls es eine Bananenschale/Brustpanzer ist dann TRUE, ansonsten FALSE
	 * @return falls es eine Bananenschale ist dann TRUE, ansonsten FALSE
	 */
	public boolean isShellType() {
		if (this.enemy instanceof ApoMarioShell) {
			return true;
		}
		return false;
	}
}
