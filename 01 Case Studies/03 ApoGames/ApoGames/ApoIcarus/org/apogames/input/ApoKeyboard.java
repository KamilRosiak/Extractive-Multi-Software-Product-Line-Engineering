package org.apogames.input;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Klasse, die sich um die Keyboardeingaben kümmert
 * @author Dirk Aporius
 *
 */
public final class ApoKeyboard implements KeyListener {

	/** TreeMap mit den Tasten, welches gerade gedrückt werden */
	private TreeMap<Integer, Boolean> keys;
	/** ArrayList mit den Tasten, welche gerade losgelassen wurden */
	private ArrayList<Integer> releasedKeys;
	/** ArrayList mit den Tasten, welche gerade losgelassen wurden */
	private ArrayList<Integer> releasedCharKeys;
	/** ArrayList mit den Tasten, welche gerade gedrückt werden */
	private ArrayList<Integer> pressedKeys;

	/**
	 * Konstruktor
	 */
	public ApoKeyboard() {
		this.keys = new TreeMap<Integer, Boolean>();
		this.releasedKeys = new ArrayList<Integer>();
		this.releasedCharKeys = new ArrayList<Integer>();
		this.pressedKeys = new ArrayList<Integer>();
	}

	/**
	 * gibt zurück, ob eine übergebene Taste derzeit gedrückt wird oder nicht
	 * @param key : Zu überprüfende Taste, ob sie gedrückt wird
	 * @return TRUE, Taste wird gedrückt, else FALSE, Taste wird nicht gedrückt
	 */
	public boolean isPressed(int key) {
		if (this.keys.containsKey(key)) {
			return this.keys.get(key);
		} else {
			return false;
		}
	}

	/**
	 * gibt ein Array mit Tasten zurück, welche gerade losgelassen wurden, zurück
	 * @return gibt ein Array mit Tasten zurück, welche gerade losgelassen wurden, zurück
	 */
	public synchronized int[][] getReleased() {
		if ((this.releasedKeys == null) || (this.releasedKeys.size() <= 0)) {
			return new int[0][0];
		}
		int[][] released = new int[this.releasedKeys.size()][2];
		for (int i = 0; i < this.releasedKeys.size(); i++) {
			released[i][0] = this.releasedKeys.get(i);
			released[i][1] = this.releasedCharKeys.get(i);
		}
		this.releasedKeys.clear();
		this.releasedCharKeys.clear();
		return released;
	}

	/**
	 * gibt ein Array mit Tasten zurück, welche gerade gedrückt werden, zurück
	 * @return gibt ein Array mit Tasten zurück, welche gerade gedrückt werden, zurück
	 */
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
		this.releasedCharKeys.add((int)e.getKeyChar());
		this.releasedKeys.add(e.getKeyCode());
		this.pressedKeys.remove((Integer) e.getKeyCode());
	}

	public void keyTyped(KeyEvent e) {
		this.keys.put(e.getKeyCode(), false);
		this.releasedCharKeys.add((int)e.getKeyChar());
		this.releasedKeys.add(e.getKeyCode());
		this.pressedKeys.remove((Integer) e.getKeyCode());
	}
}
