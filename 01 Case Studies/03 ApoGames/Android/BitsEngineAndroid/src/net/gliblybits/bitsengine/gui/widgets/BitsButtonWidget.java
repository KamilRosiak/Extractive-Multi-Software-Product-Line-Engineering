
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
import net.gliblybits.bitsengine.gui.BitsWidget;
import net.gliblybits.bitsengine.gui.widgets.listener.BitsButtonListener;
import net.gliblybits.bitsengine.input.BitsPointerEvent;
import net.gliblybits.bitsengine.render.state.BitsRenderState;
import net.gliblybits.bitsengine.utils.BitsLog;
import net.gliblybits.bitsengine.utils.BitsRectF;
import net.gliblybits.bitsengine.utils.BitsVibration;

public class 
BitsButtonWidget 
extends BitsWidget {
	
	protected BitsButtonListener mListener = null;
    public boolean isPressed = false;
    protected final int mId;

    public float[] 		mColor 			= { 1f, 1f, 1f, 1f };
    public float[] 		mPressedColor 	= { 1f, 1f, 1f, 1f };
    public float[] 		mFontColor 		= { 1f, 1f, 1f, 1f };
    public BitsGLFont 	mFont 			= null;
    public String 		mText 			= null;    
    public BitsGLImage 	mImage 			= null;
    public BitsGLImage	mPressedImage 	= null;
    public boolean		mVibrate		= true;
    public int			mVibrateDuration = 50;

    public
    BitsButtonWidget( 
    		final int id,
    		final float x,
    		final float y, 
    		final float width,
    		final float height ) {
    	
    	if( width < 0 || height < 0 ) {
    		BitsLog.e("BitsButtonWidget - constructor", "The given width and height values must be >= 0!");
    		throw new IllegalArgumentException("The given width and height values must be >= 0!");
    	}
    	
    	this.mId 			= id;
    	this.mRect.mX 		= x;
    	this.mRect.mY 		= y;
    	this.mRect.mWidth 	= width;
    	this.mRect.mHeight 	= height;
    }
    
    public
    BitsButtonWidget( 
    		final int id,
    		final BitsRectF rect ) {
    	
    	if( rect == null ) {
    		BitsLog.e("BitsButtonWidget - constructor", "The given BitsRectF is Null!");
    		throw new NullPointerException("The given BitsRectF is Null!");
    	}
    	
    	if( rect.mWidth < 0 || rect.mHeight < 0 ) {
    		BitsLog.e("BitsButtonWidget - constructor", "The given width and height values must be >= 0!");
    		throw new IllegalArgumentException("The given width and height values must be >= 0!");
    	}
    	
    	this.mId 			= id;
    	this.mRect.mX 		= rect.mX;
    	this.mRect.mY 		= rect.mY;
    	this.mRect.mWidth 	= rect.mWidth;
    	this.mRect.mHeight 	= rect.mHeight;
    }
    
    public 
    BitsButtonWidget( 
    		final int id, 
    		final BitsGLImage image, 
    		final BitsGLImage pressedImage, 
    		final float x, 
    		final float y, 
    		final float width, 
    		final float height, 
    		final String text, 
    		final BitsGLFont font ) {
    	
    	if( width < 0 || height < 0 ) {
    		BitsLog.e("BitsButtonWidget - constructor", "The given width and height values must be >= 0!");
    		throw new IllegalArgumentException("The given width and height values must be >= 0!");
    	}
    	
        this.mId 				= id;
        this.mImage 			= image;
        this.mPressedImage 		= pressedImage;
        this.mRect.mX 			= x;
        this.mRect.mY 			= y;
        this.mRect.mWidth 		= width;
        this.mRect.mHeight 		= height;
        
        this.mText = text;
        this.mFont = font;
    }
    
    public 
    BitsButtonWidget( 
    		final int id, 
    		final BitsGLImage image, 
    		final BitsGLImage pressedImage, 
    		final BitsRectF rect, 
    		final String text, 
    		final BitsGLFont font ) {
    	
    	if( rect == null ) {
    		BitsLog.e("BitsButtonWidget - constructor", "The given BitsRectF is Null!");
    		throw new NullPointerException("The given BitsRectF is Null!");
    	}
    	
    	if( rect.mWidth < 0 || rect.mHeight < 0 ) {
    		BitsLog.e("BitsButtonWidget - constructor", "The given width and height values must be >= 0!");
    		throw new IllegalArgumentException("The given width and height values must be >= 0!");
    	}
    	
        this.mId 				= id;
        this.mImage 			= image;
        this.mPressedImage 		= pressedImage;
    	this.mRect.mX 		= rect.mX;
    	this.mRect.mY 		= rect.mY;
    	this.mRect.mWidth 	= rect.mWidth;
    	this.mRect.mHeight 	= rect.mHeight;
        
        this.mText = text;
        this.mFont = font;
    }
    
    public final void 
    setListener( 
    		final BitsButtonListener listener ) {
    	
    	this.mListener = listener;
    }

    public final void 
    setColor( 
    		final int red, 
    		final int green, 
    		final int blue ) {
    	
    	this.setColor( red/255f, green/255f, blue/255f, this.mColor[3] );
    }    
    
    public final void 
    setColor( 
    		final int red, 
    		final int green, 
    		final int blue, 
    		final int alpha ) {
    	
    	this.setColor( red/255f, green/255f, blue/255f, alpha/255f );
    }

    public final void 
    setColor( 
    		final float red, 
    		final float green, 
    		final float blue ) {
    	
    	this.setColor( red, green, blue, this.mColor[3] );
    }
    
    public final void 
    setColor( 
    		final float red, 
    		final float green, 
    		final float blue, 
    		final float alpha ) {
    	
		if( red < 0f || green < 0f || blue < 0f || alpha < 0f || red > 1f || green > 1f || blue > 1f || alpha > 1f ) {
			BitsLog.e("BitsButtonWidget - setColor", "All color values must be >= 0f and <= 1f!");
			throw new IllegalArgumentException("All color values must be >= 0f and <= 1f!");
		}
    	
        this.mColor[0] = red;
        this.mColor[1] = green;
        this.mColor[2] = blue;
        this.mColor[3] = alpha;
    }

    public final void 
    setPressedColor(
    		final int red, 
    		final int green, 
    		final int blue ) {
    	
    	this.setPressedColor( red/255f, green/255f, blue/255f, this.mPressedColor[3] );
    }    
    
    public final void 
    setPressedColor( 
    		final int red,
    		final int green, 
    		final int blue, 
    		final int alpha ) {
    	
    	this.setPressedColor( red/255f, green/255f, blue/255f, alpha/255f );
    }

    public final void 
    setPressedColor( 
    		final float red, 
    		final float green, 
    		final float blue ) {
    	
    	this.setPressedColor( red, green, blue, this.mPressedColor[3] );
    }

    public final void 
    setPressedColor( 
    		final float red, 
    		final float green, 
    		final float blue, 
    		final float alpha ) {
    	
		if( red < 0f || green < 0f || blue < 0f || alpha < 0f || red > 1f || green > 1f || blue > 1f || alpha > 1f ) {
			BitsLog.e("BitsButtonWidget - setPressedColor", "All color values must be >= 0f and <= 1f!");
			throw new IllegalArgumentException("All color values must be >= 0f and <= 1f!");
		}
    	
        this.mPressedColor[0] = red;
        this.mPressedColor[1] = green;
        this.mPressedColor[2] = blue;
        this.mPressedColor[3] = alpha;
    }

    public final void
    setFontColor( 
    		final int red,
    		final int green, 
    		final int blue ) {
    	
    	this.setFontColor( red/255f, green/255f, blue/255f, this.mFontColor[3] );
    }    
    
    public final void 
    setFontColor( 
    		final int red,
    		final int green,
    		final int blue, 
    		final int alpha ) {
    	
    	this.setFontColor( red/255f, green/255f, blue/255f, alpha/255f );
    }

    public final void 
    setFontColor( 
    		final float red,
    		final float green,
    		final float blue ) {
    	
    	this.setFontColor( red, green, blue, this.mFontColor[3] );
    }
    
    public final void 
    setFontColor( 
    		final float red,
    		final float green, 
    		final float blue, 
    		final float alpha ) {
    	
		if( red < 0f || green < 0f || blue < 0f || alpha < 0f || red > 1f || green > 1f || blue > 1f || alpha > 1f ) {
			BitsLog.e("BitsButtonWidget - setPressedColor", "All color values must be >= 0f and <= 1f!");
			throw new IllegalArgumentException("All color values must be >= 0f and <= 1f!");
		}
    	
        this.mFontColor[0] = red;
        this.mFontColor[1] = green;
        this.mFontColor[2] = blue;
        this.mFontColor[3] = alpha;
    }
    
    @Override
    public void 
    onUpdate( 
    		final float delta ) {
    	
    	if( this.isVisible == false || this.isActive == false ) { //if button is invisble and/or inactive do nothing
    		return;
    	}
    }

    @Override
    public void 
    onDrawFrame( 
    		final BitsGLGraphics g ) {
    	
    	if( this.isVisible == false || ( this.mImage == null && this.mPressedImage == null ) ) { //don't render the button if it is invisible
    		return;
    	}
    	
    	g.saveStates( BitsRenderState.STATE_COLOR | BitsRenderState.STATE_ROTATION );
    	
    	g.setRotation(0f);
    	
        if( this.isPressed == true ) {
        	//set color for pressed button
        	g.setColor( this.mPressedColor[0], this.mPressedColor[1], this.mPressedColor[2], this.mPressedColor[3] );

        	if( this.mPressedImage == null ) { //if no pressed image is defined...
        		if( this.mImage == null ) { //...and no normal image...
        			g.fillRect( this.mRect.mX, this.mRect.mY, this.mRect.mWidth, this.mRect.mHeight ); //...use a filled rect
        		} else { //...but a normal image...
            		g.drawImage(this.mImage, this.mRect.mX, this.mRect.mY, this.mRect.mWidth, this.mRect.mHeight); //...render the normal image        			
        		}
        	} else { //if a pressed image is defined...
        		g.drawImage(this.mPressedImage, this.mRect.mX, this.mRect.mY, this.mRect.mWidth, this.mRect.mHeight); //...render the pressed image        		
        	}        	
        } else {
        	if( this.isActive == true ) {
        		g.setColor( this.mColor[0], this.mColor[1], this.mColor[2], this.mColor[3] );
        	} else {
        		g.setColor( this.mColor[0] / 2, this.mColor[1] / 2, this.mColor[2] / 2, this.mColor[3] );
        	}
        	if( this.mImage != null ) { //if a normal image is defined...
        		g.drawImage(this.mImage, this.mRect.mX, this.mRect.mY, this.mRect.mWidth, this.mRect.mHeight); //...render the normal image
        	} else { //if no normal image is defined...
    			g.fillRect( this.mRect.mX, this.mRect.mY, this.mRect.mWidth, this.mRect.mHeight ); //...use a filled rect        		
        	}
        }
        
        if(this.mText != null && this.mFont != null) {
        	if( this.isActive == true ) {
            	g.setColor( this.mFontColor[0], this.mFontColor[1], this.mFontColor[2], this.mFontColor[3] );
        	} else {
        		g.setColor( this.mFontColor[0] / 2, this.mFontColor[1] / 2, this.mFontColor[2] / 2, this.mFontColor[3] );
        	}

        	g.saveStates( BitsRenderState.STATE_FONT );
        	g.setFont( this.mFont );
        	g.centerText( this.mText, this.mRect.mX + ( this.mRect.mWidth / 2 ), this.mRect.mY + ( this.mRect.mHeight / 2 ) );
        	g.restoreStates();
        }
        
        g.restoreStates();
    }

    public final int 
    getId() {
    	
        return this.mId;
    }

	@Override
	public boolean 
	onPointerDown( 
			final int pointerId, 
			final float x, 
			final float y, 
			final BitsPointerEvent event ) {
		
		if( this.isVisible == true && pointerId == 0 && this.mRect.contains(x, y) == true ) {
			if( this.isActive == true ) {
				this.isPressed = true;
			}
			return true;
		}
		
		return false;
	}

	@Override
	public boolean 
	onPointerUp( 
			final int pointerId,
			final float x,
			final float y,
			final BitsPointerEvent event ) {
		if( this.isVisible == true && pointerId == 0 && this.isPressed == true ) {
			this.isPressed = false;
			if( this.mRect.contains(x, y) == true ) { //only inform the listener if the up event happened inside the buttons rect 
				if(this.mVibrate == true ) { 
					BitsVibration.getInstance().vibrate(this.mVibrateDuration);
				}
				if(this.mListener != null) {
					return this.mListener.onButtonPressed( this.mId, this );
				}
			}
			
			return true; //don't forward the event because the button was pressed before!
		}
		
		return false;
	}

	@Override
	public boolean 
	onPointerDragged(
			final int pointerId,
			final float x, 
			final float y,
			final float deltaX,
			final float deltaY, 
			final BitsPointerEvent event ) {
		
		if( this.isVisible == true && pointerId == 0 && this.isPressed == true ) {
			return true; //don't forward the event if the button is visible and is pressed
		}
		
		return false;
	}

	@Override
	public void 
	onAdd() {
		
	}

	@Override
	public void 
	onRemove() {
		
        this.mListener = null;
    }
}
