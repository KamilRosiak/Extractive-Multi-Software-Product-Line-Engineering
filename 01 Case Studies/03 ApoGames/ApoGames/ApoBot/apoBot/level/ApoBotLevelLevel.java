package apoBot.level;

/**
 * Hilsklasse, die angibt wie hoch ein Tile ist und was für ein Tile es ist
 * @author Dirk Aporius
 *
 */
public class ApoBotLevelLevel {

	private int startGround;
	private int startHeight;
	
	private int ground;
	private int height;
	
	public ApoBotLevelLevel(int ground, int height) {
		this.setStartGround(ground);
		this.setStartHeight(height);
		
		this.init();
	}

	public void init() {
		this.setHeight(this.getStartHeight());
		this.setGround(this.getStartGround());
	}
	
	public int getStartGround() {
		return this.startGround;
	}

	public void setStartGround(int startGround) {
		this.startGround = startGround;
	}

	public int getStartHeight() {
		return this.startHeight;
	}

	public void setStartHeight(int startHeight) {
		this.startHeight = startHeight;
	}

	public int getGround() {
		return this.ground;
	}

	public void setGround(int ground) {
		this.ground = ground;
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		if (height < 0) {
			height = 0;
		}
		this.height = height;
	}
	
}
