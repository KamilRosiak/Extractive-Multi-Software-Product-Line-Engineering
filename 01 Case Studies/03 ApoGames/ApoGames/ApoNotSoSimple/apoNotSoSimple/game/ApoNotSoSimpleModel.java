package apoNotSoSimple.game;

import java.awt.Graphics2D;

public abstract class ApoNotSoSimpleModel {

	private ApoNotSoSimplePanel game;
	
	public ApoNotSoSimpleModel(ApoNotSoSimplePanel game) {
		this.game = game;
	}
	
	public ApoNotSoSimplePanel getGame() {
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
