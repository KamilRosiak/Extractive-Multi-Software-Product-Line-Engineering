package apoSkunkman.ai;

import apoSkunkman.entity.ApoSkunkmanBush;
import apoSkunkman.entity.ApoSkunkmanEntity;
import apoSkunkman.entity.ApoSkunkmanGoodie;
import apoSkunkman.entity.ApoSkunkmanSkunkman;
import apoSkunkman.entity.ApoSkunkmanStone;

/**
 * Klasse von der alle LevelEntities erben und grundlegene Methoden zur Verfügung stellt
 * @author Dirk Aporius
 *
 */
public class ApoSkunkmanAILevelEntity extends ApoSkunkmanAIEntity {
	/** Entityobjekt */
	private final ApoSkunkmanEntity entity;
	
	public ApoSkunkmanAILevelEntity(final ApoSkunkmanEntity entity) {
		super(entity);
		this.entity = entity;
	}
	
	/**
	 * gibt zurück, ob es sich um ein Steintile handelt
	 * @return TRUE es ist ein Steintile, ansonsten FALSE
	 */
	public boolean isStone() {
		if (this.entity instanceof ApoSkunkmanStone) {
			return true;
		}
		return false;
	}

	/**
	 * gibt zurück, ob es sich um ein Buschtile handelt
	 * @return TRUE es ist ein Buschtile, ansonsten FALSE
	 */
	public boolean isBush() {
		if (this.entity instanceof ApoSkunkmanBush) {
			return true;
		}
		return false;
	}

	/**
	 * gibt zurück, ob es sich um ein Skunkman handelt
	 * @return TRUE es ist ein Skunkman, ansonsten FALSE
	 */
	public boolean isSkunkman() {
		if (this.entity instanceof ApoSkunkmanSkunkman) {
			return true;
		}
		return false;
	}
	
	/**
	 * gibt zurück, ob es sich um ein Goodie handelt
	 * @return TRUE es ist ein Goodie, ansonsten FALSE
	 */
	public boolean isGoodie() {
		if (this.entity instanceof ApoSkunkmanGoodie) {
			return true;
		}
		return false;
	}

}
