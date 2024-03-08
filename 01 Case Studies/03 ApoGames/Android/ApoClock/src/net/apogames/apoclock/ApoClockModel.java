package net.apogames.apoclock;

import java.util.HashMap;
import java.util.Map;

import net.apogames.apoclock.game.ApoClockPanel;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;
import net.gliblybits.bitsengine.input.BitsKeyEvent;

public abstract class ApoClockModel {

	private ApoClockPanel game;
	
	private final Map<String, Integer> stringWidth = new HashMap<String, Integer>();
	
	public ApoClockModel(ApoClockPanel game) {
		this.game = game;
	}
	
	public Map<String, Integer> getStringWidth() {
		return this.stringWidth;
	}

	public ApoClockPanel getGame() {
		return this.game;
	}
	
	public abstract void init();
	
	public void close() {}
	
	public abstract void touchedPressed(int x, int y, int finger);
	
	public abstract void touchedReleased(int x, int y, int finger);
	
	public abstract void touchedDragged(int x, int y, int oldX, int oldY, int finger);
	
	public abstract void touchedButton(String function);
	
	public void onKeyDown(final int key, final BitsKeyEvent event) {
	}

	public void onKeyUp(final int key, final BitsKeyEvent event) {
	}
	
	public void onBackButtonPressed() {	
	}
	
	public void onPause() {
	}

	public void onResume() {		
	}
	
	public abstract void think(int delta);
	
	public abstract void render(BitsGLGraphics g);
	
	public void drawOverlay(BitsGLGraphics g) {
	}
	
}
