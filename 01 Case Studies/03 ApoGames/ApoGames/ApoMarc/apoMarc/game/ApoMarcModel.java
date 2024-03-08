package apoMarc.game;

import java.awt.Graphics2D;

public abstract class ApoMarcModel {

	private ApoMarcPanel game;
	
	public ApoMarcModel(ApoMarcPanel game) {
		this.game = game;
	}
	
	public ApoMarcPanel getGame() {
		return this.game;
	}

	public void init() {
		
	}
	
	public abstract void keyButtonReleased(int button, char character);
	
	public abstract void mouseButtonFunction(String function);
	
	public abstract void mouseButtonReleased(int x, int y);
	
	public boolean mouseMoved(int x, int y) {
		return false;
	}
	
	public boolean mouseDragged(int x, int y) {
		return false;
	}
	
	public boolean mousePressed(int x, int y, boolean bRight) {
		return false;
	}
	
	public abstract void think(long delta);
	
	public abstract void render(Graphics2D g);
	
}
