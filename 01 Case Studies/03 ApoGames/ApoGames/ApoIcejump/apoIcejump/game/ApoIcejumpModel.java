package apoIcejump.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class ApoIcejumpModel {

	private ApoIcejumpPanel game;
	
	public ApoIcejumpModel(ApoIcejumpPanel game) {
		this.game = game;
	}
	
	public ApoIcejumpPanel getGame() {
		return this.game;
	}

	public abstract void keyPressed(KeyEvent e);
	
	public abstract void keyButtonReleased(int button, char character);
	
	public abstract void mouseButtonFunction(String function);
	
	public abstract void mouseButtonReleased(int x, int y);
	
	public boolean mouseMoved(MouseEvent e) {
		return false;
	}
	
	public boolean mouseDragged(MouseEvent e) {
		return false;
	}
	
	public abstract void think(int delta);
	
	public abstract void render(Graphics2D g);
	
}
