package apoSimple.hiddenFun;

import java.awt.image.BufferedImage;

import org.apogames.entity.ApoEntity;

public class ApoFunEntity extends ApoEntity {

	public static final int SHEEP = 0;
	public static final int FLOWER = 1;
	public static final int DOG_LEFT = 2;
	public static final int DOG_UP = 3;
	public static final int DOG_RIGHT = 4;
	public static final int DOG_DOWN = 5;
	
	private int id;
	
	public ApoFunEntity(BufferedImage image, final float x, final float y, final int id) {
		super(image, x, y, image.getWidth(), image.getHeight());
		
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}
}
