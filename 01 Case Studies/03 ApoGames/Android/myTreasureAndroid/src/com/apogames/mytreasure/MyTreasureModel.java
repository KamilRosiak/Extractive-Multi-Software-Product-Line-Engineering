package com.apogames.mytreasure;

import java.util.HashMap;
import java.util.Map;

import com.apogames.mytreasure.game.MyTreasurePanel;

import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;

public abstract class MyTreasureModel {

	private MyTreasurePanel game;
	
	private final Map<String, Integer> stringWidth = new HashMap<String, Integer>();
	
	public MyTreasureModel(MyTreasurePanel game) {
		this.game = game;
	}
	
	public Map<String, Integer> getStringWidth() {
		return this.stringWidth;
	}

	public MyTreasurePanel getGame() {
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
	
	public abstract void render(BitsGLGraphics g);
	
	public void drawOverlay(BitsGLGraphics g) {
	}
	
}
