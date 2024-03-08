
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
import net.gliblybits.bitsengine.gui.widgets.listener.BitsProgressBarListener;
import net.gliblybits.bitsengine.input.BitsPointerEvent;
import net.gliblybits.bitsengine.input.listener.BitsPointerListener;
import net.gliblybits.bitsengine.render.state.BitsRenderState;
import net.gliblybits.bitsengine.utils.BitsLog;
import net.gliblybits.bitsengine.utils.BitsRectF;

public final class 
BitsProgressBarWidget
extends BitsWidget
implements BitsPointerListener {

	protected BitsProgressBarListener mListener = null;
	
    public float[] 			mBarColor 			= { 1f, 1f, 1f, 1f };
    public float[] 			mBackgroundColor 	= { 0.5f, 0.5f, 0.5f, 1f };

    public boolean			showText			= true;
    public BitsGLFont 		mFont 				= null;
    public float[] 			mFontColor 			= { 0f, 0f, 0f, 1f };
	
    public BitsGLImage		mBackgroundImage	= null;
    public BitsGLImage		mBarImage			= null;
    
    private boolean			isClickable			= true;
    private String			mValueString 		= "50";
    private float			mValueStringLength 	= 0f;
    private float			mValue 				= 0.5f;
    
	private boolean 		isPressed 			= false;

    private final int		mId;

    public
    BitsProgressBarWidget( 
    		final int id,
    		final float x,
    		final float y, 
    		final float width,
    		final float height ) {
    	
    	if( width < 0 || height < 0 ) {
    		BitsLog.e("BitsProgressBarWidget - constructor", "The given width and height values must be >= 0!");
    		throw new IllegalArgumentException("The given width and height values must be >= 0!");
    	}
    	
    	this.mId = id;
    	
    	this.mRect.mX 		= x;
    	this.mRect.mY 		= y;
    	this.mRect.mWidth 	= width;
    	this.mRect.mHeight 	= height;
    	
    	this.updateValue(this.mValue);
    }
    
    public
    BitsProgressBarWidget( 
    		final int id,
    		final BitsRectF rect ) {
    	
		if( rect == null ) {
			BitsLog.e("BitsProgressBarWidget - constructor", "The given BitsRectF is Null!");
			throw new NullPointerException("The given BitsRectF is Null!");
		}
		
    	if( rect.mWidth < 0 || rect.mHeight < 0 ) {
    		BitsLog.e("BitsProgressBarWidget - constructor", "The given width and height values must be >= 0!");
    		throw new IllegalArgumentException("The given width and height values must be >= 0!");
    	}
    	
    	this.mId = id;

    	this.mRect.mX 		= rect.mX;
    	this.mRect.mY 		= rect.mY;
    	this.mRect.mWidth 	= rect.mWidth;
    	this.mRect.mHeight 	= rect.mHeight;
    	
    	this.updateValue(this.mValue);
    }
    
    public final void 
    setListener( 
    		final BitsProgressBarListener listener ) {
    	
    	this.mListener = listener;
    }
    
    public final void 
    setBarColor( 
    		final int red, 
    		final int green,
    		final int blue ) {
    	
    	this.setBarColor( red/255f, green/255f, blue/255f, this.mBarColor[3] );
    }    
    
    public final void 
    setBarColor( 
    		final int red, 
    		final int green, 
    		final int blue, 
    		final int alpha ) {
    	
    	this.setBarColor( red/255f, green/255f, blue/255f, alpha/255f );
    }

    public final void 
    setBarColor( 
    		final float red,
    		final float green, 
    		final float blue ) {
    	
    	this.setBarColor( red, green, blue, this.mBarColor[3] );
    }
    
    public final void 
    setBarColor( 
    		final float red, 
    		final float green,
    		final float blue, 
    		final float alpha ) {
    	
		if( red < 0f || green < 0f || blue < 0f || alpha < 0f || red > 1f || green > 1f || blue > 1f || alpha > 1f ) {
			BitsLog.e("BitsProgressBarWidget - setBarColor", "All color values must be >= 0f and <= 1f!");
			throw new IllegalArgumentException("All color values must be >= 0f and <= 1f!");
		}
    	
        this.mBarColor[0] = red;
        this.mBarColor[1] = green;
        this.mBarColor[2] = blue;
        this.mBarColor[3] = alpha;
    }
    
    public final void 
    updateValue( 
    		final float v ) {
    	
    	if( v >= 0f && v <= 1f ) {
    		this.mValue = v;
    		this.mValueString = String.valueOf(StrictMath.round(v * 100f));
    		if( this.mFont != null ) {
    			this.mValueStringLength = this.mFont.getLength( this.mValueString );
    		}
    	}
    }
    
    public final float
    getValue( ) {
    	
    	return this.mValue;
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
    
    @Override
    public final void 
    onUpdate( 
    		final float delta ) {
    	
    	if( this.isVisible == false || this.isActive == false ) {
    		return;
    	}
    }

    @Override
    public final void 
    onDrawFrame( 
    		final BitsGLGraphics g ) {
    	
    	if( this.isVisible == false ) {
    		return;
    	}
    	
    	g.saveStates( BitsRenderState.STATE_COLOR | BitsRenderState.STATE_FONT | BitsRenderState.STATE_ROTATION );
    	
    	g.setRotation(0f);
    	
    	if( this.mBackgroundImage == null && this.mBarImage == null ) {
    		g.setColor(this.mBackgroundColor);
    		g.fillRect(this.mRect);
    		g.setColor(this.mBarColor);
    		g.fillRect(this.mRect.mX, this.mRect.mY, (this.mRect.mWidth * this.mValue), this.mRect.mHeight);
    	} else {
    		g.drawImage(this.mBackgroundImage, this.mRect);
    		g.cropImage(this.mBarImage, this.mRect.mX, this.mRect.mY, (this.mRect.mWidth * this.mValue), this.mRect.mHeight, 0, 0, (this.mBarImage.mWidth * this.mValue), this.mBarImage.mHeight);
    	}
    	
    	if( this.showText == true ) {
    		g.setColor(this.mFontColor);
    		g.centerText(this.mValueString, this.mRect.mX + (this.mRect.mWidth / 2) - (this.mValueStringLength / 2) , this.mRect.mY + (this.mRect.mHeight / 2 ) );
    	}
        
        g.restoreStates();
    }

	@Override
	public final boolean 
	onPointerDown(
			final int pointerId, 
			final float x, 
			final float y,
			final BitsPointerEvent event) {

		if( this.isVisible == true && pointerId == 0 && this.mRect.contains(x, y) == true ) {
			if( this.isActive == true && this.isClickable == true ) {
				this.isPressed = true;
				final float clickedX = x - this.mRect.mX;
//				if( clickedX >= 0 && clickedX <= this.mRect.mWidth ) { //clicked inside the progress bar
					float percent = clickedX / this.mRect.mWidth;
					if( percent > 1f ) {
						percent = 1f;
					} else {
						if( percent < 0f ) {
							percent = 0f;
						}
					}
					this.updateValue(percent);
					if(this.mListener != null) {
						this.mListener.onProgressBarValueChanged( this.mId, this.mValue );
					}
//				}
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
			final BitsPointerEvent event ) {
		
		if( this.isVisible == true && pointerId == 0 && this.isPressed == true ) {
			this.isPressed = false;
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
			final BitsPointerEvent event ) {
		
		if( this.isVisible == true && pointerId == 0 && this.isPressed == true ) {
			final float clickedX = x - this.mRect.mX;
//			if( clickedX >= 0 && clickedX <= this.mRect.mWidth ) { //clicked inside the progress bar
				float percent = clickedX / this.mRect.mWidth;
				if( percent > 1f ) {
					percent = 1f;
				} else {
					if( percent < 0f ) {
						percent = 0f;
					}
				}
				this.updateValue(percent);
				if(this.mListener != null) {
					this.mListener.onProgressBarValueChanged( this.mId, this.mValue );
				}
//			}
			return true;
		}

		return false;
	}
}
