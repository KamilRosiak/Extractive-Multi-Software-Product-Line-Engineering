package apoImp.game;

import java.awt.Graphics2D;

public abstract class ApoImpModel {

	private ApoImpPanel game;
	
	public ApoImpModel(ApoImpPanel game) {
		this.game = game;
	}
	
	public ApoImpPanel getGame() {
		return this.game;
	}

	public abstract void init();

	public abstract void keyButtonReleased(int button, char character);
	
	public void close() {}
	
	public void keyPressed(int keyCode, char keyCharacter) {
	}
	
	public abstract void mouseButtonFunction(String function);
	
	public abstract void mouseButtonReleased(int x, int y, boolean bRight);
	
	public boolean mouseMoved(int x, int y) {
		return false;
	}
	
	public boolean mouseDragged(int x, int y) {
		return false;
	}
	
	public boolean mousePressed(int x, int y, boolean bRight) {
		return false;
	}
	
	public void mouseWheelChanged(int changed) {
	}
	
	public abstract void think(int delta);
	
	public abstract void render(Graphics2D g);
	
	public void drawOverlay(Graphics2D g) {
	}
	
}
