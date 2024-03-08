package apoSkunkman.entity;

import java.awt.image.BufferedImage;

import apoSkunkman.ApoSkunkmanConstants;

/**
 * Klasse, die ein "Stinktier" repräsentiert
 * @author Dirk Aporius
 *
 */
public class ApoSkunkmanSkunkman extends ApoSkunkmanEntity {
	/** Variable die angibt, welcher Player das Stinktier gelegt hat */
	private final int player;
	/** Variable die angibt, wie groß der Radius des "Stinkens" ist */
	private final int skunkWidth;
	/** Variable die angibt, ob das Stinktier nun "hochgeht" oder nicht */
	private boolean shouldExplode;
	/** Zeit, die noch vergehen muss bis der Skunkman hochgeht */
	private int time;
	
	public ApoSkunkmanSkunkman(BufferedImage pic, float x, float y, float width, float height, int player, int skunkWidth) {
		super(pic, x, y, width, height, ApoSkunkmanConstants.SKUNKMAN_TILES, ApoSkunkmanConstants.SKUNKMAN_ANIMATION_TIME, 1, false);
		
		this.shouldExplode = false;
		this.player = player;
		this.skunkWidth = skunkWidth;
		this.time = ApoSkunkmanConstants.SKUNKMAN_TIME_TO_EXPLODE;
	}

	/**
	 * Zeit, die noch vergehen muss bis der Skunkman hochgeht
	 * @return Zeit, die noch vergehen muss bis der Skunkman hochgeht
	 */
	public int getTimeToExplosion() {
		return this.time;
	}
	
	/**
	 * gibt zurück, wie groß der Radius des "Stinkens" ist
	 * @return gibt zurück, wie groß der Radius des "Stinkens" ist
	 */
	public final int getSkunkWidth() {
		return this.skunkWidth;
	}

	/**
	 * gibt zurück, welcher Player das Stinktier gelegt hat
	 * @return gibt zurück, welcher Player das Stinktier gelegt hat
	 */
	public final int getPlayer() {
		return this.player;
	}

	/**
	 * gibt zurück, ob das Stinktier nun "hochgeht" oder nicht
	 * @return gibt zurück, ob das Stinktier nun "hochgeht" oder nicht
	 */
	public final boolean shouldExplode() {
		return this.shouldExplode;
	}

	public void think(int delta) {
		if (this.isBVisible()) {
			super.think(delta);
			this.time -= delta;
			// wenn die Zeit abgelaufen ist, soll der Skunkman hochgehen
			if (this.time <= 0) {
				this.shouldExplode = true;
			}
		}
	}
}
