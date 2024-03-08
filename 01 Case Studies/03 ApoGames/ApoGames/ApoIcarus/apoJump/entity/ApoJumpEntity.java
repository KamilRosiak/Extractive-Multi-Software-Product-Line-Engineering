package apoJump.entity;

import java.awt.image.BufferedImage;

import org.apogames.entity.ApoEntity;

import apoJump.level.ApoJumpLevel;

public abstract class ApoJumpEntity extends ApoEntity {

	public ApoJumpEntity(BufferedImage background, float x, float y, float width, float height) {
		super(background, x, y, width, height);
	}

	public void think(int delta, ApoJumpLevel level) {
		super.think(delta);
		this.update(delta, level);
	}
	
	public abstract void update(int delta, ApoJumpLevel level);
}
