package net.apogames.aposnake;

import net.apogames.aposnake.entity.ApoButton;
import net.gliblybits.bitsengine.gui.BitsButton;
import net.gliblybits.bitsengine.gui.BitsScreen;
import net.gliblybits.bitsengine.input.touch.BitsTouchPointer;
import net.gliblybits.bitsengine.input.touch.BitsTouchPointerManager;
import net.gliblybits.bitsengine.render.BitsGraphics;

public abstract class ApoSnakeComponent extends BitsScreen {

	/** Array der ganzen Buttons im Spiel */
	private ApoButton[] buttons;

	private int oldX, oldY;
	
	private final boolean[] touched = new boolean[3];
	
	private ApoSnakeModel model;
	
	protected ApoSnakeComponent(int id) {
		super(id);
	}

	public void onButtonPressed(BitsButton button) {
		
	}

	public ApoSnakeModel getModel() {
		return this.model;
	}

	public void setModel(ApoSnakeModel model) {
		this.model = model;
	}

	/**
	 * gibt das Array mit den Buttons zurück
	 * @return gibt das Array mit den Buttons zurück
	 */
	public final ApoButton[] getButtons() {
		return this.buttons;
	}

	/**
	 * setzt das Array mit den Buttons auf den übergebenen Wert
	 * @param buttons : neues Array mit Buttons
	 */
	public void setButtons(ApoButton[] buttons) {
		this.buttons = buttons;
	}
	
	public void checkTouchInput() {
		for (int i = 0; i < this.touched.length; i++) {
			final BitsTouchPointer pointer = BitsTouchPointerManager.getIt().get(i);
			boolean newTouched = false;
			if (pointer == null) {
				newTouched = false;
			} else {
				newTouched = pointer.isDown;
			}
			if ((newTouched) && (!this.touched[i])) {
				if (pointer != null) {
					int x = pointer.mX;
					int y = pointer.mY;
					this.oldX = x;
					this.oldY = y;
					
					boolean bButton = false;
					if (this.getButtons() != null) {
						for (int b = 0; b < this.getButtons().length; b++) {
							if ((this.getButtons()[b].isBVisible()) && (this.getButtons()[b].intersects(x, y, 1, 1))) {
								String function = this.getButtons()[b].getFunction();
								this.setButtonFunction(function);
								bButton = true;
								break;
							}
						}
					}
					if (!bButton) {
						if (this.model != null) {
							this.model.touchedPressed(x, y, i);
						}
					}
				}
			} else if ((!newTouched) && (this.touched[i])) {
				int x = 0;
				int y = 0;
				if (pointer != null) {
					x = pointer.mX;
					y = pointer.mY;
				}
				if (this.model != null) {
					this.model.touchedReleased(x, y, i);
				}
			} else if ((newTouched) && (this.touched[i])) {
				if (pointer != null) {
					int x = pointer.mX;
					int y = pointer.mY;
					
					if ((this.model != null) && ((this.oldX != x) || (this.oldY != y))) {
						this.model.touchedDragged(x, y, this.oldX, this.oldY, i);
					}
					this.oldX = x;
					this.oldY = y;
				}
			}
			this.touched[i] = newTouched; 
		}
	}
	
	/**
	 * rendert die Buttons
	 * @param g : das Graphics2D Object
	 */
	public void renderButtons(BitsGraphics g) {
		if (this.buttons != null) {
			for (int i = 0; i < this.buttons.length; i++) {
				this.buttons[i].render(g, 0, 0);
			}
		}
	}

	/**
	 * wird aufgerufen, wenn ein Button gedrückt wurde
	 * @param function : Funktion, die der Button ausführen soll und ihn einzigartig macht
	 */
	public abstract void setButtonFunction(String function);
	
	@Override
	public void onInit() {
		init();
	}
	
	public void init() {
		
	}

	@Override
	public void onPause() {
		
	}

	@Override
	public void onResume() {
		
	}

	@Override
	public void onFinish() {
		
	}

	@Override
	public void onBackButtonPressed() {
		
	}

}
