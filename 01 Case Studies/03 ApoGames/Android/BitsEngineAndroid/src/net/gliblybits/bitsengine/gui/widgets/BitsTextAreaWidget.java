
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

import java.util.ArrayList;

import net.gliblybits.bitsengine.graphics.opengl.BitsGLFont;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;
import net.gliblybits.bitsengine.gui.BitsWidget;
import net.gliblybits.bitsengine.input.BitsPointerEvent;
import net.gliblybits.bitsengine.render.state.BitsRenderState;
import net.gliblybits.bitsengine.utils.BitsLog;
import net.gliblybits.bitsengine.utils.BitsRectF;

public final class 
BitsTextAreaWidget 
extends BitsWidget {

	final private ArrayList<String> mLines = new ArrayList<String>();

	public final BitsRectF 	mRect 				= new BitsRectF( 0f, 0f, 0f, 0f );
	public final float		mBackgroundColor[] 	= { 0f, 0f, 0f, 1f };
	public BitsGLFont		mFont				= null;
	public final float		mFontColor[] 		= { 1f, 1f, 1f, 1f };
	public float			mLineSpacing 		= 0f;
	
	private float 	mListOffset 	= 0f;
	private float	mCharHeight 	= 0f;
	private int		mLineCount		= 0;
	private float	mMaxTextHeight	= 0f;
	private float	mViewablePercent = 0f;
	private boolean isPressed 		= false;
	
	public 
	BitsTextAreaWidget(
			final BitsRectF rect ) {
		
		if( rect == null ) {
			BitsLog.e("BitsTextAreaWidget - constructor", "The given BitsRectF is Null!");
			throw new NullPointerException("The given BitsRectF is Null!");
		}
		
    	if( rect.mWidth < 0 || rect.mHeight < 0 ) {
    		BitsLog.e("BitsTextAreaWidget - constructor", "The given width and height values must be >= 0!");
    		throw new IllegalArgumentException("The given width and height values must be >= 0!");
    	}
		
		this.mRect.mX 		= rect.mX;
		this.mRect.mY 		= rect.mY;
		this.mRect.mWidth 	= rect.mWidth;
		this.mRect.mHeight 	= rect.mHeight;
	}
	
	public 
	BitsTextAreaWidget(
			final float x,
			final float y,
			final float width,
			final float height ) {
		
    	if( width < 0 || height < 0 ) {
    		BitsLog.e("BitsTextAreaWidget - constructor", "The given width and height values must be >= 0!");
    		throw new IllegalArgumentException("The given width and height values must be >= 0!");
    	}
		
		this.mRect.mX 		= x;
		this.mRect.mY 		= y;
		this.mRect.mWidth 	= width;
		this.mRect.mHeight 	= height;
	}
	
    public final void
    setBackgroundColor( 
    		final float[] color ) {
    	
		if( color == null ) {
			BitsLog.e("BitsTextAreaWidget - setBackgroundColor", "The given color array is Null!");
			throw new NullPointerException("The given color array is Null!");
		}
		
		switch( color.length ) {
			case 3:
				this.setBackgroundColor(color[0], color[1], color[2], this.mBackgroundColor[3]);
				break;
			case 4:
				this.setBackgroundColor(color[0], color[1], color[2], color[3]);
				break;
			default:
				BitsLog.e("BitsTextAreaWidget - setBackgroundColor", "The given color arrays size is not 3 or 4!");
				throw new NullPointerException("The given color arrays size is not 3 or 4!");	
		}
    }
    
    public final void
    setBackgroundColor( 
    		final float red,
    		final float green,
    		final float blue,
    		final float alpha ) {
    	
		if( red < 0f || green < 0f || blue < 0f || alpha < 0f || red > 1f || green > 1f || blue > 1f || alpha > 1f ) {
			BitsLog.e("BitsTextAreaWidget - setBackgroundColor", "All color values must be >= 0f and <= 1f!");
			throw new IllegalArgumentException("All color values must be >= 0f and <= 1f!");
		}
    	
		this.mBackgroundColor[0] = red;
		this.mBackgroundColor[1] = green;
		this.mBackgroundColor[2] = blue;
		this.mBackgroundColor[3] = alpha;
    }
    
    public final void
    setFontColor( 
    		final float[] color ) {
    	
		if( color == null ) {
			BitsLog.e("BitsTextAreaWidget - setFontColor", "The given color array is Null!");
			throw new NullPointerException("The given color array is Null!");
		}
		
		switch( color.length ) {
			case 3:
				this.setFontColor(color[0], color[1], color[2], this.mBackgroundColor[3]);
				break;
			case 4:
				this.setFontColor(color[0], color[1], color[2], color[3]);
				break;
			default:
				BitsLog.e("BitsTextAreaWidget - setFontColor", "The given color arrays size is not 3 or 4!");
				throw new NullPointerException("The given color arrays size is not 3 or 4!");	
		}
    }
    
    public final void
    setFontColor( 
    		final float red,
    		final float green,
    		final float blue,
    		final float alpha ) {
    	
		if( red < 0f || green < 0f || blue < 0f || alpha < 0f || red > 1f || green > 1f || blue > 1f || alpha > 1f ) {
			BitsLog.e("BitsTextAreaWidget - setFontColor", "All color values must be >= 0f and <= 1f!");
			throw new IllegalArgumentException("All color values must be >= 0f and <= 1f!");
		}
    	
		this.mFontColor[0] = red;
		this.mFontColor[1] = green;
		this.mFontColor[2] = blue;
		this.mFontColor[3] = alpha;
    }
	
	public final void
	setText( 
			final String text ) {
		
		this.mListOffset = 0f;
		this.mLineCount = 0;
		this.mLines.clear();
		this.mCharHeight = this.mFont.mMaxCharHeight + this.mFont.mFontDescent;

		final String[] tokenz = text.split(" ");
		float length = 0f;
		String s = new String("");
		for( int i = 0; i < tokenz.length; i++ ) {
			float stringLength = this.mFont.getLength(tokenz[i] + " ");
			
			if( length + stringLength < this.mRect.mWidth - 5 ) {
				s += tokenz[i] + " ";
				length += stringLength;
			} else {
				this.mLines.add(s);
				this.mLineCount++;
				s = new String(tokenz[i] + " ");
				length = 0f;
				length += stringLength;
			}

			if( s.endsWith("/n ") == true ) {
				this.mLines.add(s.substring(0, s.length() - 3)); //-3 -> there is a space at the end
				this.mLineCount++;
				s = new String("");
				length = 0f;
			}
		}
		this.mLines.add(s);
		this.mLineCount++;
		
		this.mMaxTextHeight = ( (this.mCharHeight + this.mLineSpacing) * this.mLineCount) + this.mLineSpacing;
		this.mViewablePercent = this.mRect.mHeight / this.mMaxTextHeight;
	}
	
	@Override
	public final boolean 
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
	public final boolean 
	onPointerUp(
			final int pointerId, 
			final float x, 
			final float y,
			final BitsPointerEvent event ) {

		if( this.isVisible == true && pointerId == 0 && this.isPressed == true ) {
			this.isPressed = false;
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
			final BitsPointerEvent event ) {

		if( this.isVisible == true && pointerId == 0 && this.isPressed == true ) {
			final float minY = ( this.mRect.mHeight - this.mMaxTextHeight );
			if( minY < 0 ) { //only if the List won't fit in the ListRect
				this.mListOffset += deltaY;
				if( this.mListOffset > 0 ) {
					this.mListOffset = 0;
				}
				if( this.mListOffset <= minY ) {
					this.mListOffset = minY;
				}
			}

			return true;
		}
		
		return false;
	}

	@Override
	public final void 
	onAdd() {

	}

	@Override
	public final void 
	onRemove() {

		this.mLines.clear();
	}

	@Override
	public final void 
	onDrawFrame(
			final BitsGLGraphics g ) {

    	if( this.isVisible == false ) {
    		return;
    	}
    	
		g.saveStates( BitsRenderState.STATE_CLIP | BitsRenderState.STATE_FONT | BitsRenderState.STATE_COLOR | BitsRenderState.STATE_ROTATION );
		
		g.setRotation(0f);
		g.setClip( this.mRect );
		g.setFont( this.mFont );

		g.setColor( this.mBackgroundColor );
		g.fillRect( this.mRect );
		
		g.setColor(1f, 1f, 1f, 1f);
		final int size = this.mLines.size();
		final ArrayList<String> lines = this.mLines;
    	float yPos = this.mListOffset + this.mLineSpacing;
		for( int i = 0; i < size; i++ ) {
			
	   		if( ( this.mRect.mY + yPos + this.mCharHeight > this.mRect.mY ) && //crop at the top
		   		( this.mRect.mY + yPos < this.mRect.mY + this.mRect.mHeight ) ) { //crop at bottom
	   			
	   			g.drawText(lines.get(i), this.mRect.mX, this.mRect.mY + yPos);
	   		}
	   		yPos += this.mCharHeight + this.mLineSpacing;
		}
		
		if( this.mViewablePercent < 1f ) {
			g.setColor(0f, 0f, 0f, 0.5f);
			g.fillRect( this.mRect.mX + this.mRect.mWidth - 5, this.mRect.mY, 5, this.mRect.mHeight );
			g.setColor(1f, 1f, 1f, 0.5f);
			g.fillRect( 
					this.mRect.mX + this.mRect.mWidth - 5, 
					this.mRect.mY - ((this.mListOffset + this.mLineSpacing) * this.mViewablePercent ) + 2, 
					5, 
					this.mRect.mHeight * this.mViewablePercent );
		}
		
		g.restoreStates();
	}

	@Override
	public final void 
	onUpdate(
			final float delta ) {

    	if( this.isVisible == false || this.isActive == false ) {
    		return;
    	}
	}
}
