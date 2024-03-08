package apoSkunkman.game;

import apoSkunkman.ApoSkunkmanConstants;
import apoSkunkman.level.ApoSkunkmanLevel;

public class ApoSkunkmanSimulate {

	private final ApoSkunkmanLevel level;
	
	public ApoSkunkmanSimulate(final ApoSkunkmanLevel level) {
		this.level = level;
	}
	
	public void simulate() {
		while ((!this.level.isWin()) && (this.level.getGame().isGameRunning())) {
			this.level.think(ApoSkunkmanConstants.WAIT_TIME_THINK);
		}
	}
	
}
