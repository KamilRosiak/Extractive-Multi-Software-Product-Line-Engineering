package apoPongBeat.game;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public abstract class ApoPongBeatModel {

	private ApoPongBeatPanel game;
	
	public ApoPongBeatModel(ApoPongBeatPanel game) {
		this.game = game;
	}
	
	public ApoPongBeatPanel getGame() {
		return this.game;
	}
	
	public abstract void init();

	public abstract void keyButtonReleased(int button, char character);
	
	public abstract void mouseButtonFunction(String function);
	
	public abstract void mouseButtonReleased(int x, int y);
	
	public abstract void mouseMoved(int x, int y);
	
	public abstract void ballOnBorder(boolean bLeft);
	
	public abstract void ballOnRacket(int points);
	
	public abstract void think(int delta);
	
	public abstract void render(Graphics2D g);
	
}
