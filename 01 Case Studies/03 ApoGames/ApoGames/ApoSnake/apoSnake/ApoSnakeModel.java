package apoSnake;

import java.awt.Graphics2D;

import apoSnake.game.ApoSnakePanel;

public abstract class ApoSnakeModel {

	private ApoSnakePanel game;
	
	public ApoSnakeModel(ApoSnakePanel game) {
		this.game = game;
	}

	public ApoSnakePanel getGame() {
		return this.game;
	}
	
	public abstract void init();
	
	public void close() {}
	
	public abstract void keyButtonReleased(int button, char character);
	
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
	
	public abstract void render(final Graphics2D g);
	
	public void drawOverlay(final Graphics2D g) {
	}
	
}
