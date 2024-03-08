package org.apogames.input;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.TreeMap;

public final class ApoKeyboard implements KeyListener {

	public static final int UP = KeyEvent.VK_UP;
	public static final int DOWN = KeyEvent.VK_DOWN;
	public static final int RIGHT = KeyEvent.VK_RIGHT;
	public static final int LEFT = KeyEvent.VK_LEFT;
	public static final int FIRE = KeyEvent.VK_CONTROL;
	public static final int ALTFIRE = KeyEvent.VK_ALT;
	public static final int ESCAPE = KeyEvent.VK_ESCAPE;

	private TreeMap<Integer, Boolean> keys = null;
	private ArrayList<Integer> releasedKeys = new ArrayList<Integer>();
	private ArrayList<Integer> pressedKeys = new ArrayList<Integer>();

	public ApoKeyboard() {
		this.keys = new TreeMap<Integer, Boolean>();
		this.releasedKeys = new ArrayList<Integer>();
		this.pressedKeys = new ArrayList<Integer>();
	}

	public boolean isPressed(int key) {
		if (this.keys.containsKey(key)) {
			return this.keys.get(key);
		} else {
			return false;
		}
	}

	public synchronized int[] getReleased() {
		if ((this.releasedKeys == null) || (this.releasedKeys.size() <= 0)) {
			return new int[0];
		}
		int[] released = new int[this.releasedKeys.size()];
		for (int i = 0; i < this.releasedKeys.size(); i++) {
			released[i] = this.releasedKeys.get(i);
		}
		this.releasedKeys.clear();
		return released;
	}

	public synchronized int[] getPressed() {
		if ((this.pressedKeys == null) || (this.pressedKeys.size() <= 0)) {
			return new int[0];
		}
		int[] pressed = new int[this.pressedKeys.size()];
		for (int i = 0; i < this.pressedKeys.size(); i++) {
			pressed[i] = this.pressedKeys.get(i);
		}
		return pressed;
	}

	public void keyPressed(KeyEvent e) {
		this.keys.put(e.getKeyCode(), true);
		if (this.pressedKeys.indexOf(e.getKeyCode()) < 0) {
			this.pressedKeys.add(e.getKeyCode());
		}
	}

	public void keyReleased(KeyEvent e) {
		this.keys.put(e.getKeyCode(), false);
		this.releasedKeys.add(e.getKeyCode());
		this.pressedKeys.remove((Integer) e.getKeyCode());
	}

	public void keyTyped(KeyEvent e) {

	}
}
