package org.apogames.subgame;

import org.apogames.ApoScreen;
import org.apogames.ApoSubGame;
import org.apogames.input.ApoMouse;

public abstract class ApoGamePanel extends ApoSubGame {

	public ApoGamePanel(ApoScreen screen) {
		super(screen);
	}

	@Override
	protected void update(long delta) {
		int[] keyPressed = this.keyboard.getPressed();
		if (keyPressed != null) {
			for (int i = 0; i < keyPressed.length; i++) {
				this.keyPressed(keyPressed[i], (char) ((int) keyPressed[i]));
			}
		}

		int[] keyReleased = this.keyboard.getReleased();
		if (keyReleased != null) {
			for (int i = 0; i < keyReleased.length; i++) {
				this.keyReleased(keyReleased[i]);
			}
		}

		if (this.mouse.isDragged()) {
			this.mouseDragged(this.mouse.getX(), this.mouse.getY());
		} else if (this.mouse.hasClicked(ApoMouse.LEFT)) {
			this.mousePressed(this.mouse.getX(), this.mouse.getY(), true);
		} else if (this.mouse.hasClicked(ApoMouse.RIGHT)) {
			this.mousePressed(this.mouse.getX(), this.mouse.getY(), false);
		} else if (this.mouse.isMoved()) {
			this.mouseMoved(this.mouse.getX(), this.mouse.getY());
		}
		boolean[] mouseReleased = this.mouse.getReleased();
		if (mouseReleased[ApoMouse.LEFT]) {
			this.mouseReleased(this.mouse.getX(), this.mouse.getY(), true);
		} else if (mouseReleased[ApoMouse.RIGHT]) {
			this.mouseReleased(this.mouse.getX(), this.mouse.getY(), false);
		}
		this.think(delta);
	}

	public abstract void think(long delta);
	
	public abstract void keyPressed(int keyCode, char keyCharacter);

	public abstract void keyReleased(int keyCode);

	public abstract void mouseDragged(int x, int y);

	public abstract void mouseMoved(int x, int y);

	public abstract void mousePressed(int x, int y, boolean bLeft);

	public abstract void mouseReleased(int x, int y, boolean bLeft);
}