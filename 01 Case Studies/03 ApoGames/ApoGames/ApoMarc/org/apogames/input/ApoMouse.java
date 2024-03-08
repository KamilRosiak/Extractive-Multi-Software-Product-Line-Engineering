package org.apogames.input;

import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;

public final class ApoMouse extends MouseInputAdapter {

	public static final int LEFT = MouseEvent.BUTTON1;
	public static final int MIDDLE = MouseEvent.BUTTON2;
	public static final int RIGHT = MouseEvent.BUTTON3;

	private int x = 0;
	private int y = 0;
	private int oldX = 0;
	private int oldY = 0;
	private boolean[] clicks = null;

	private boolean dragged;

	private boolean[] released;

	public ApoMouse() {
		this.x = 0;
		this.y = 0;
		this.oldX = this.x;
		this.oldY = this.y;
		this.clicks = new boolean[5];
		this.dragged = false;
		this.released = new boolean[5];
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public boolean isDragged() {
		return this.dragged;
	}

	public boolean[] getReleased() {
		boolean[] returnReleased = this.released.clone();
		this.released = new boolean[5];
		return returnReleased;
	}

	public boolean hasClicked(int click) {
		return this.clicks[click];
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.clicks[e.getButton()] = true;
		this.updateCoord(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.clicks[e.getButton()] = false;
		this.dragged = false;
		this.released[e.getButton()] = true;
		this.updateCoord(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.updateCoord(e);
		this.dragged = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		this.updateCoord(e);
		this.dragged = true;
	}

	private void updateCoord(MouseEvent e) {
		this.oldX = this.x;
		this.oldY = this.y;
		this.x = e.getX();
		this.y = e.getY();
	}

	public boolean isMoved() {
		if ((this.oldX != this.x) || (this.oldY != this.y)) {
			this.oldX = this.x;
			this.oldY = this.y;
			return true;
		}
		return false;
	}
}
