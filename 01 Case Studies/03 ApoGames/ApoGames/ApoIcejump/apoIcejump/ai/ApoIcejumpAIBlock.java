package apoIcejump.ai;

import apoIcejump.entity.ApoIcejumpBlock;

/**
 * Klasse, die einen Eisblock darstellt
 * class for the iceblock
 * @author Dirk Aporius
 *
 */
public class ApoIcejumpAIBlock extends ApoIcejumpAIEntity {

	private ApoIcejumpBlock entity;
	
	public ApoIcejumpAIBlock(ApoIcejumpBlock entity) {
		super(entity);
		
		this.entity = entity;
	}
	
	/**
	 * gibt zurück, wie oft der Block noch besprungen werden kann, bevor er sich auflöst<br />
	 * returns how often the block can be touched until he will blow up ;)
	 * @return gibt zurück, wie oft der Block noch besprungen werden kann, bevor er sich auflöst / returns how often the block can be touched until he will blow up ;)
	 */
	public int getHits() {
		return this.entity.getHits();
	}

}
