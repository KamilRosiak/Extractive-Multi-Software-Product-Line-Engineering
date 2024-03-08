package apoIcejump.ai;

import apoIcejump.entity.ApoIcejumpPlayer;

/**
 * Klasse, die den Gegner darstellt<br />
 * class for the enemy
 * @author Dirk Aporius
 *
 */
public class ApoIcejumpAIEnemy extends ApoIcejumpAIEntity {

	private ApoIcejumpPlayer entity;
	
	public ApoIcejumpAIEnemy(ApoIcejumpPlayer entity) {
		super(entity);
		
		this.entity = entity;
	}
	
	/**
	 * gibt zurück, ob diese Entity gerade durch ein Goodie langsamer ist<br />
	 * returns wheter the entity is slow or not
	 * @return TRUE langsamer, sonst FALSE / TRUE slower, else FALSE
	 */
	public boolean isSlow() {
		if (this.entity.getOnSlower() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * gibt zurück, wie lange diese Entity noch langsam ist<br />
	 * returns how long the entity is slow in ms
	 * @return gibt zurück, wie lange diese Entity noch langsam ist / returns how long the entity is slow
	 */
	public int getSlowTimeLeft() {
		return this.entity.getOnSlower();
	}
	
	/**
	 * gibt zurück, ob die Entity gerade durch ein Goodie schneller ist<br />
	 * returns wheter the entity is fast or not
	 * @return TRUE schneller, ansonsten FALSE / TRUE fast, else FALSE
	 */
	public boolean isFaster() {
		if (this.entity.getOnFaster() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * gibt zurück, wie lange diese Entity noch schnell unterwegs ist<br />
	 * returns how long the entity is fast in ms
	 * @return gibt zurück, wie lange diese Entity noch schnell unterwegs ist / returns how long the entity is fast in ms
	 */
	public int getFastTimeLeft() {
		return this.entity.getOnFaster();
	}
	
	/**
	 * gibt zurück, ob diese Entity gerade on fire ist<br />
	 * returns wheter the entity is on fire or not
	 * @return TRUE Entity on fire, sonst FALSE
	 */
	public boolean isFire() {
		if (this.entity.getOnFire() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * gibt die Zeit zurück, wie lange diese Entity noch on fire ist<br />
	 * returns how long the entity is on fire in ms
	 * @return 0 Entity nicht on FIRE, ansonsten Zeit in ms wie lange noch / <=0 entity isn't on fire, else time in ms
	 */
	public int getFireTime() {
		return this.entity.getOnFire();
	}
	
	/**
	 * gibt zurück, wie oft die Entity noch höher springen kann<br />
	 * returns how often the entity can jump higher
	 * @return <= 0 Entity springt normal, ansonsten Anzahl wie oft diese Entity noch höher springt / <= 0 Entity jumps normal, else how often
	 */
	public int getHighJumpLeft() {
		return this.entity.getOnHigher();
	}

}
