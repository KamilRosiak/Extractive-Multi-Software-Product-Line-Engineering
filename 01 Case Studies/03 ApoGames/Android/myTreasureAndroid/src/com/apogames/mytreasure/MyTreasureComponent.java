package com.apogames.mytreasure;

import com.apogames.mytreasure.entity.ApoButton;

import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;
import net.gliblybits.bitsengine.gui.BitsScreen;
import net.gliblybits.bitsengine.gui.widgets.BitsButtonWidget;
import net.gliblybits.bitsengine.input.BitsInput;
import net.gliblybits.bitsengine.input.BitsKeyEvent;
import net.gliblybits.bitsengine.input.BitsPointerEvent;
import net.gliblybits.bitsengine.input.listener.BitsKeyListener;
import net.gliblybits.bitsengine.input.listener.BitsPointerListener;

public abstract class MyTreasureComponent extends BitsScreen implements BitsPointerListener, BitsKeyListener {

	/** Array der ganzen Buttons im Spiel */
	private ApoButton[] buttons;

	private int oldX, oldY;
	
	private MyTreasureModel model;
	
	protected MyTreasureComponent(int id) {
		super(id);
	}

	public void onButtonPressed(BitsButtonWidget button) {
		
	}

	public MyTreasureModel getModel() {
		return this.model;
	}

	public void setModel(MyTreasureModel model) {
		this.model = model;
	}

	/**
	 * gibt das Array mit den Buttons zur�ck
	 * @return gibt das Array mit den Buttons zur�ck
	 */
	public final ApoButton[] getButtons() {
		return this.buttons;
	}

	/**
	 * setzt das Array mit den Buttons auf den �bergebenen Wert
	 * @param buttons : neues Array mit Buttons
	 */
	public void setButtons(ApoButton[] buttons) {
		this.buttons = buttons;
	}
	
	public final boolean onPointerDown( final int pointerId, final float x, final float y, final BitsPointerEvent event ) {
		boolean bButton = false;
		if (this.getButtons() != null) {
			for (int b = 0; b < this.getButtons().length; b++) {
				if ((this.getButtons()[b].isVisible()) && (this.getButtons()[b].intersects(x, y, 1, 1))) {
					this.getButtons()[b].setBPressed(true);
				} else {
					this.getButtons()[b].setBPressed(false);
				}
			}
		}
		if (!bButton) {
			if (this.model != null) {
				this.model.touchedPressed((int)x, (int)y, pointerId);
			}
		}
		this.oldX = (int)x;
		this.oldY = (int)y;
		
		return true;
	}

	public final boolean onPointerUp( final int pointerId, final float x, final float y, final BitsPointerEvent event ) {
		boolean bButton = false;
		if (this.getButtons() != null) {
			for (int b = 0; b < this.getButtons().length; b++) {
				if ((this.getButtons()[b].isVisible()) && (this.getButtons()[b].intersects(x, y, 1, 1)) && (!bButton) && (this.getButtons()[b].isBPressed())) {
					String function = this.getButtons()[b].getFunction();
					this.setButtonFunction(function);
					bButton = true;
				}
				this.getButtons()[b].setBPressed(false);
			}
		}
		if (!bButton) {
			if (this.model != null) {
				this.model.touchedReleased((int)x, (int)y, pointerId);
			}
		}
		
		return true;
	}

	public final boolean onPointerDragged( final int pointerId, final float x, final float y, final float deltaX, final float deltaY, final BitsPointerEvent event ) {
		if ((this.model != null) && (((int)(x) != this.oldX) || ((int)(y) != this.oldY))) {
			this.model.touchedDragged((int)(x), (int)(y), this.oldX, this.oldY, pointerId);
		}
		
		this.oldX = (int)x;
		this.oldY = (int)y;
		
		return true;
	}
	
	/**
	 * rendert die Buttons
	 * @param g : das Graphics2D Object
	 */
	public void renderButtons(BitsGLGraphics g) {
		if (this.buttons != null) {
			for (int i = 0; i < this.buttons.length; i++) {
				this.buttons[i].render(g, 0, 0);
			}
		}
	}

	/**
	 * wird aufgerufen, wenn ein Button gedr�ckt wurde
	 * @param function : Funktion, die der Button ausf�hren soll und ihn einzigartig macht
	 */
	public abstract void setButtonFunction(String function);
	
	@Override
	public void onInitScreen() {
		BitsInput.getInstance().registerPointerListener(this);
		BitsInput.getInstance().registerKeyListener(this);
		init();
	}
	
	public void init() {
		
	}

	@Override
	public void onPauseScreen() {
		
	}

	@Override
	public void onResumeScreen() {
		
	}

	@Override
	public void onFinishScreen() {
		
	}

	@Override
	public void onBackButtonPressed() {
		
	}
	
	public boolean onKeyDown(final int key, final BitsKeyEvent event) {
		if (this.model != null) {
			this.model.onKeyDown(key);
		}
		
		return true;
	}

	public boolean onKeyUp(final int key, final BitsKeyEvent event) {
		if (this.model != null) {
			this.model.onKeyUp(key);
		}
		
		return true;
	}

}
