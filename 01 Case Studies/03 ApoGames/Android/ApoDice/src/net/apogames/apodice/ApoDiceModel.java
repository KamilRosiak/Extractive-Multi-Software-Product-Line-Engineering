package net.apogames.apodice;

import java.util.HashMap;
import java.util.Map;

import net.apogames.apodice.game.ApoDicePanel;
import net.gliblybits.bitsengine.render.BitsGraphics;

public abstract class ApoDiceModel {

	private ApoDicePanel game;
	
	private final Map<String, Integer> stringWidth = new HashMap<String, Integer>();
	
	public ApoDiceModel(ApoDicePanel game) {
		this.game = game;
	}
	
	public Map<String, Integer> getStringWidth() {
		return this.stringWidth;
	}
	
	public ApoDicePanel getGame() {
		return this.game;
	}
	
	public abstract void init();
	
	public void close() {}
	
	public abstract void touchedPressed(int x, int y, int finger);
	
	public abstract void touchedReleased(int x, int y, int finger);
	
	public abstract void touchedDragged(int x, int y, int oldX, int oldY, int finger);
	
	public abstract void touchedButton(String function);
	
	public void onKeyDown(final int key) {
	}

	public void onKeyUp(final int key) {
	}
	
	public void onBackButtonPressed() {	
	}
	
	public void onPause() {
	}

	public void onResume() {		
	}
	
	public abstract void think(int delta);
	
	public abstract void render(BitsGraphics g);
	
	public void drawOverlay(BitsGraphics g) {
	}
	
}
