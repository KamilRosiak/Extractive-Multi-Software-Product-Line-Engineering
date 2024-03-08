package apoSimple.game;

import java.awt.Graphics2D;
import java.util.ArrayList;

import apoSimple.entity.ApoSimpleEntityMenu;

public abstract class ApoSimpleModel {

	private ApoSimplePanel game;
	
	private ArrayList<ApoSimpleEntityMenu> menuSheep;
	
	public ApoSimpleModel(ApoSimplePanel game) {
		this.game = game;
		
		this.menuSheep = new ArrayList<ApoSimpleEntityMenu>();
	}
	
	public ApoSimplePanel getGame() {
		return this.game;
	}

	public abstract void init();

	public ArrayList<ApoSimpleEntityMenu> getMenuSheep() {
		return this.menuSheep;
	}

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
	
	public void thinkSheep(int delta) {
		for (int i = 0; i < this.menuSheep.size(); i++) {
			this.menuSheep.get(i).think(delta);
		}
	}
	
	public abstract void render(Graphics2D g);
	
	public void drawOverlay(Graphics2D g) {
	}
	
	public void renderSheep(Graphics2D g) {
		for (int i = 0; i < this.menuSheep.size(); i++) {
			this.menuSheep.get(i).renderShadow(g, 0, 0);
		}
		for (int i = 0; i < this.menuSheep.size(); i++) {
			this.menuSheep.get(i).renderSheep(g, 0, 0);
		}
	}
	
}
