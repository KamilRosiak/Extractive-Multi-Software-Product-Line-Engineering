package org.apogames.input;

import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;

/**
 * Klasse, die sich um alle Mausevents kümmert
 * @author Dirk Aporius
 */
public final class ApoMouse extends MouseInputAdapter {

	/** Integervariable für die linke Maustaste */
	public static final int LEFT = MouseEvent.BUTTON1;
	/** Integervariable für die mittlere Maustaste */
	public static final int MIDDLE = MouseEvent.BUTTON2;
	/** Integervariable für die rechte Maustaste */
	public static final int RIGHT = MouseEvent.BUTTON3;
	/** x-Variable der aktuellen Mausposition */
	private int x = 0;
	/** y-Variable der aktuellen Mausposition */
	private int y = 0;
	/** x-Variable der alten vorherigen Mausposition */
	private int oldX = 0;
	/** Y-Variable der alten vorherigen Mausposition */
	private int oldY = 0;
	/** Booleanarray welches zurückgibt, ob eine Maustaste gerade gehalten wird oder nicht */
	private boolean[] clicks = null;
	/** Booleanvariable die angibt, ob eine Maustaste gerade gedrückt wird und die Maus sich bewegt */
	private boolean dragged;
	/** Booleanarray mit allen Mausevents, welche gerade losgelassen wurden */
	private boolean[] released;

	/**
	 * Konstruktor
	 */
	public ApoMouse() {
		this.x = 0;
		this.y = 0;
		this.oldX = this.x;
		this.oldY = this.y;
		this.clicks = new boolean[5];
		this.dragged = false;
		this.released = new boolean[5];
	}

	/**
	 * gibt den aktuellen x-Wert der Mausposition zurück
	 * @return gibt den aktuellen x-Wert der Mausposition zurück
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * gibt den aktuellen y-Wert der Mausposition zurück
	 * @return gibt den aktuellen y-Wert der Mausposition zurück
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * gibt zurück, ob eine Maustaste gerade gedrückt wird und die Maus sich bewegt
	 * @return TRUE, Maustaste gedrückt und Maus bewegt, ansonsten FALSE
	 */
	public boolean isDragged() {
		return this.dragged;
	}

	/**
	 * gibt ein booleanArray zurück, welches die Mausbuttons enthält, die gerade losgelassen wurden
	 * @return gibt ein booleanArray zurück, welches die Mausbuttons enthält, die gerade losgelassen wurden
	 */
	public boolean[] getReleased() {
		boolean[] returnReleased = this.released.clone();
		this.released = new boolean[5];
		return returnReleased;
	}

	/**
	 * gibt zurück, ob eine zu überprüfende Maustaste gedrückt wird
	 * @param click : zu überprüfende Maustaste
	 * @return TRUE, Maustaste wird gedrückt, else Maustaste wird nicht gedrückt
	 */
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

	/**
	 * aktualisiert die Mauskoordinaten
	 * @param e : Mousevent
	 */
	private void updateCoord(MouseEvent e) {
		this.oldX = this.x;
		this.oldY = this.y;
		this.x = e.getX();
		this.y = e.getY();
	}

	/**
	 * gibt zurück, ob seit dem letzten Mousevent sich die Maus bewegt hat oder nicht
	 * @return TRUE Maus hast sich seit dem letzten Mouseevent bewegt, ansonsten FALSE
	 */
	public boolean isMoved() {
		if ((this.oldX != this.x) || (this.oldY != this.y)) {
			this.oldX = this.x;
			this.oldY = this.y;
			return true;
		}
		return false;
	}
}
