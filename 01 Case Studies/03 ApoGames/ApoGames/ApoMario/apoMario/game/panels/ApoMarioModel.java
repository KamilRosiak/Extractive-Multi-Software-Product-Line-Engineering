package apoMario.game.panels;

import java.awt.Graphics2D;

import apoMario.game.ApoMarioPanel;

/**
 * abstrakte Panelklasse<br />
 * abstrakte Klasse, die das Grundgerüst für alle Panels im Spiel bildet und die benötigten Methoden zur Verfügung stellt<br />
 * @author Dirk Aporius
 */
public abstract class ApoMarioModel {
	
	private ApoMarioPanel game;
	
	public ApoMarioModel(ApoMarioPanel game) {
		this.game = game;
	}
	
	public ApoMarioPanel getGame() {
		return this.game;
	}
	
	public abstract void init();

	public abstract void keyButtonReleased(int button, char character);
	
	public abstract void mouseButtonFunction(String function);
	
	public abstract void mouseButtonReleased(int x, int y);
	
	public abstract boolean mouseMoved(int x, int y);
	
	public abstract boolean mouseDragged(int x, int y);
	
	public abstract boolean mousePressed(int x, int y, boolean bRight);	
	
	public abstract void think(int delta);
	
	public abstract void render(Graphics2D g);
}
