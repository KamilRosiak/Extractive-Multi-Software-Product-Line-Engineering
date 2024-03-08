
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

package net.gliblybits.bitsengine.gui;

import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;
import net.gliblybits.bitsengine.input.BitsPointerEvent;
import net.gliblybits.bitsengine.utils.BitsLog;
import net.gliblybits.bitsengine.utils.BitsRectF;

/**
 * The base abstract class all the gui components must extend.<br>
 * <br>
 * @author Marc Wiedenhoeft - GliblyBits
 * @version 0.1 - Streak
 * @since 0.1 - Streak
 */
public abstract class 
BitsWidget {
	
	public BitsRectF 	mRect 		= new BitsRectF(0f, 0f, 0f, 0f);
	public boolean 		isVisible 	= true;
	public boolean 		isActive 	= true;

    public final void 
    setVisible( 
    		final boolean visible ) {
    	
    	this.isVisible = visible;
    }
    
    public final void 
    setActive( 
    		final boolean state ) {
    	
    	this.isActive = state;
    }
    
    public final void 
    setPosition( 
    		final float x, 
    		final float y ) {
    	
    	this.mRect.mX = x;
    	this.mRect.mY = y;
    }
    
    public final void 
    setSize( 
    		final float width, 
    		final float height ) {
    	
    	if( width < 0 || height < 0 ) {
    		BitsLog.e("BitsWidget - setSize", "The given width and height values must be >= 0!");
    		throw new IllegalArgumentException("The given width and height values must be >= 0!");
    	}
    	
    	this.mRect.mWidth 	= width;
    	this.mRect.mHeight 	= height;
    }
    
    public final void 
    setBounds( 
    		final float x, 
    		final float y, 
    		final float width, 
    		final float height ) {
    	
    	if( width < 0 || height < 0 ) {
    		BitsLog.e("BitsWidget - setBounds", "The given width and height values must be >= 0!");
    		throw new IllegalArgumentException("The given width and height values must be >= 0!");
    	}
    	
    	this.mRect.mX = x;
    	this.mRect.mY = y;
    	this.mRect.mWidth = width;
    	this.mRect.mHeight = height;
    }
	
	public abstract void
	onAdd();
	
	public abstract void
	onRemove();
	
	public abstract void 
    onDrawFrame( 
    		final BitsGLGraphics g ); 
    
	public abstract void 
    onUpdate( 
    		final float delta );
	
	public abstract boolean 
	onPointerDown( 
			final int pointerId, 
			final float x, 
			final float y, 
			final BitsPointerEvent event );
	
	public abstract boolean 
	onPointerUp( 
			final int pointerId, 
			final float x, 
			final float y, 
			final BitsPointerEvent event );
	
	public abstract boolean 
	onPointerDragged( 
			final int pointerId, 
			final float x, 
			final float y, 
			final float deltaX, 
			final float deltaY, 
			final BitsPointerEvent event );
}
