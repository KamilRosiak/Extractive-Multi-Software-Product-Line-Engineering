package apoRelax.game;

import java.awt.Graphics2D;

public abstract class ApoRelaxState {

	private ApoRelaxPanel game;
	
	public ApoRelaxState(ApoRelaxPanel game) {
		this.game = game;
	}
	
	public ApoRelaxPanel getGame() {
		return this.game;
	}

	public abstract void init();
	
	public void init(int level) {
		
	}

	public abstract void keyButtonReleased(int button, char character);
	
	public abstract void mouseButtonFunction(String function);
	
	public abstract void mouseButtonReleased(int x, int y, boolean bRight);
	
	public boolean mouseMoved(int x, int y) {
		return false;
	}
	
	public boolean mouseDragged(int x, int y, boolean bRight) {
		return false;
	}
	
	public boolean mousePressed(int x, int y, boolean bRight) {
		return false;
	}
	
	public abstract void think(int delta);
	
	public abstract void render(Graphics2D g);
	
}
