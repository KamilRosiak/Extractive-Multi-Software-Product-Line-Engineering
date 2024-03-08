
/*
 * Copyright 2013 Marc Wiedenhoeft - GliblyBits
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.gliblybits.bitsengine.gui.widgets;

import net.gliblybits.bitsengine.graphics.opengl.BitsGLFont;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLImage;
import net.gliblybits.bitsengine.input.BitsPointerEvent;
import net.gliblybits.bitsengine.utils.BitsRectF;

public final class 
BitsToggleButtonWidget 
extends BitsButtonWidget {

	private boolean isMouseDown = false;
	
    public
    BitsToggleButtonWidget( 
    		final int id,
    		final float x,
    		final float y, 
    		final float width,
    		final float height ) {
    	
    	super(id, x, y, width, height);
    }
    
    public
    BitsToggleButtonWidget( 
    		final int id,
    		final BitsRectF rect ) {
    	
    	super(id, rect);
    }
    
    public 
    BitsToggleButtonWidget( 
    		final int id, 
    		final BitsGLImage image, 
    		final BitsGLImage pressedImage, 
    		final float x, 
    		final float y, 
    		final float w, 
    		final float h, 
    		final String text, 
    		final BitsGLFont font ) {

    	super(id, image, pressedImage, x, y, w, h, text, font);
    }
    
    public 
    BitsToggleButtonWidget( 
    		final int id, 
    		final BitsGLImage image, 
    		final BitsGLImage pressedImage, 
    		final BitsRectF rect, 
    		final String text, 
    		final BitsGLFont font ) {
    	
    	super(id, image, pressedImage, rect, text, font);
    }
    
    public final boolean isPressed() {
        return this.isPressed;
    }
	
	@Override
	public final void 
	onAdd() {


	}

	@Override
	public final void 
	onRemove() {


	}

	@Override
	public final void 
	onDrawFrame(
			final BitsGLGraphics g ) {

		super.onDrawFrame(g);
	}

	@Override
	public final void 
	onUpdate(
			final float delta ) {
		
	    super.onUpdate(delta);
	}

	@Override
	public final boolean 
	onPointerDown(
			final int pointerId, 
			final float x, 
			final float y,
			final BitsPointerEvent event) {

		if( this.isVisible == true && pointerId == 0 && this.mRect.contains(x, y) == true ) {
			if( this.isActive == true ) {
				this.isPressed = !this.isPressed;
				this.isMouseDown = true;
			}
			return true;
		}
		
		return false;
	}

	@Override
	public final boolean 
	onPointerUp(
			final int pointerId, 
			final float x, 
			final float y,
			final BitsPointerEvent event) {

		if( this.isVisible == true && pointerId == 0 && this.isMouseDown == true ) {
			this.isMouseDown = false;
			return true;
		}
		
		return false;
	}

	@Override
	public final boolean 
	onPointerDragged(
			final int pointerId, 
			final float x, 
			final float y,
			final float deltaX, 
			final float deltaY, 
			final BitsPointerEvent event) {

		if( this.isVisible == true && pointerId == 0 && this.isMouseDown == true ) {
			return true;
		}
		
		return false;
	}
}
