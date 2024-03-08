package apoMarc;

import java.awt.Cursor;
import java.awt.Graphics2D;

import org.apogames.ApoScreen;
import org.apogames.ApoSubGame;
import org.apogames.entity.ApoButton;
import org.apogames.input.ApoMouse;

public abstract class ApoMarcGameComponent extends ApoSubGame {

	/** Objekt, mit dem mit bestimmten Werten Bilder erstellt und geladen werden können */
	private ApoMarcImages images;
	
	private ApoButton[] buttons;

	private boolean bHandCursor;
	
	public ApoMarcGameComponent(ApoScreen screen) {
		super(screen);
	}
	
	@Override
	protected void load() {
		if (this.images == null) {
			this.images = new ApoMarcImages();
		}
	}

	public boolean isShowFPS() {
		return ApoMarcConstants.FPS;
	}

	public void setShowFPS(boolean showFPS) {
		ApoMarcConstants.FPS = showFPS;
	}

	public ApoButton[] getButtons() {
		return this.buttons;
	}

	public void setButtons(ApoButton[] buttons) {
		this.buttons = buttons;
	}

	public ApoMarcImages getImages() {
		return this.images;
	}
	
	public int getFPS() {
		return this.screen.getFps();
	}

	/**
	 * malt die Buttons
	 * @param g : das Graphics2D Object
	 */
	public void renderButtons(Graphics2D g) {
		if (this.buttons != null) {
			for (int i = 0; i < this.buttons.length; i++) {
				this.buttons[i].render(g, 0, 0);
			}
		}
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
	
	public void keyPressed(int keyCode, char keyCharacter) {
	}

	public void keyReleased(int keyCode) {
	}

	public void mouseDragged(int x, int y) {
	}

	public void mouseMoved(int x, int y) {
		boolean bOver = false;
		if (this.buttons != null) {
			for (int i = 0; i < this.buttons.length; i++) {
				if (this.buttons[i].getMove(x, y)) {
					bOver = true;
					if (!super.shouldRepaint()) {
						this.render();
					}
					break;
				} else if (this.buttons[i].isBOver()) {
					bOver = true;
				}
			}
		}
		if (bOver) {
			if (!this.bHandCursor) {
				this.screen.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				this.bHandCursor = true;
			}
		} else {
			if (this.bHandCursor) {
				this.screen.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				this.bHandCursor = false;
			}
		}
	}

	public void mousePressed(int x, int y, boolean left) {
		if (this.buttons != null) {
			for (int i = 0; i < this.buttons.length; i++) {
				if (this.buttons[i].getPressed(x, y)) {
					if (!super.shouldRepaint()) {
						this.render();
					}
					break;
				}
			}
		}
	}

	public void mouseReleased(int x, int y, boolean left) {
		if (this.buttons != null) {
			for (int i = 0; i < this.buttons.length; i++) {
				if (this.buttons[i].getReleased(x, y)) {
					String function = this.buttons[i].getFunction();
					this.setButtonFunction(function);
				}
			}
		}
		if (!super.shouldRepaint()) {
			this.render();
		}
	}

	public abstract void setButtonFunction(String function);

}
