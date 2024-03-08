
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
import net.gliblybits.bitsengine.graphics.opengl.BitsGLImage;
import net.gliblybits.bitsengine.gui.BitsWidget;
import net.gliblybits.bitsengine.gui.widgets.listener.BitsListEntryListener;
import net.gliblybits.bitsengine.input.BitsPointerEvent;
import net.gliblybits.bitsengine.render.state.BitsRenderState;
import net.gliblybits.bitsengine.utils.BitsLog;
import net.gliblybits.bitsengine.utils.BitsRectF;

public final class 
BitsListWidget 
extends BitsWidget {

	private final ArrayList<BitsListEntry> 	mEntryList 	= new ArrayList<BitsListEntry>(); //List entries
	private int mEntryCount = 0;

	public BitsGLImage 		mBackground = null;
	public final float		mBackgroundColor[] = { 0f, 0f, 0f, 1f };
	
	public BitsGLFont		mEntryFont		= null;
	public final float		mFontColor[] 	= { 1f, 1f, 1f, 1f };
	public float 			mEntrySpacing 	= 0f;
	public float			mEntryWidth		= 0f;
	public float			mEntryHeight 	= 0f;
	
	private float mListOffset = 0f;
	private BitsListEntryListener mListener = null;
	private boolean isPressed = false;
	private boolean isDragged = false;
	
	public 
	BitsListWidget(
			final BitsRectF rect ) {
		
		if( rect == null ) {
			BitsLog.e("BitsListWidget - constructor", "The given BitsRectF is Null!");
			throw new NullPointerException("The given BitsRectF is Null!");
		}
		
    	if( rect.mWidth < 0 || rect.mHeight < 0 ) {
    		BitsLog.e("BitsListWidget - constructor", "The given width and height values must be >= 0!");
    		throw new IllegalArgumentException("The given width and height values must be >= 0!");
    	}
		
		this.mRect.mX 		= rect.mX;
		this.mRect.mY 		= rect.mY;
		this.mRect.mWidth 	= rect.mWidth;
		this.mRect.mHeight 	= rect.mHeight;
	}
	
	public 
	BitsListWidget(
			final float x,
			final float y,
			final float width,
			final float height ) {
		
    	if( width < 0 || height < 0 ) {
    		BitsLog.e("BitsListWidget - constructor", "The given width and height values must be >= 0!");
    		throw new IllegalArgumentException("The given width and height values must be >= 0!");
    	}
    	
		this.mRect.mX 		= x;
		this.mRect.mY 		= y;
		this.mRect.mWidth 	= width;
		this.mRect.mHeight 	= height;
	}
	
    public final void 
    setListener( 
    		final BitsListEntryListener listener ) {
    	
    	this.mListener = listener;
    }
    
    public final void
    setBackgroundColor( 
    		final float[] color ) {
    	
		if( color == null ) {
			BitsLog.e("BitsListWidget - setBackgroundColor", "The given color array is Null!");
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
				BitsLog.e("BitsListWidget - setBackgroundColor", "The given color arrays size is not 3 or 4!");
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
			BitsLog.e("BitsListWidget - setBackgroundColor", "All color values must be >= 0f and <= 1f!");
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
			BitsLog.e("BitsListWidget - setFontColor", "The given color array is Null!");
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
				BitsLog.e("BitsListWidget - setFontColor", "The given color arrays size is not 3 or 4!");
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
			BitsLog.e("BitsListWidget - setFontColor", "All color values must be >= 0f and <= 1f!");
			throw new IllegalArgumentException("All color values must be >= 0f and <= 1f!");
		}
    	
		this.mFontColor[0] = red;
		this.mFontColor[1] = green;
		this.mFontColor[2] = blue;
		this.mFontColor[3] = alpha;
    }
    
	@Override
	public final void 
	onDrawFrame(
			final BitsGLGraphics g) {
		
    	if( this.isVisible == false ) { //don't render the list if it is invisible
    		return;
    	}

		g.saveStates( BitsRenderState.STATE_CLIP | BitsRenderState.STATE_COLOR | BitsRenderState.STATE_FONT | BitsRenderState.STATE_LINESIZE | BitsRenderState.STATE_ROTATION );
		
		g.setRotation(0f);
		g.setLineSize(1f);
		g.setClip( this.mRect );
		g.setFont( this.mEntryFont );

		g.setColor( this.mBackgroundColor );
		g.fillRect( this.mRect );
		
		final float entryPadding = (this.mRect.mWidth - this.mEntryWidth) / 2f;
    	final ArrayList<BitsListEntry> entryList = this.mEntryList;
	    final int size = this.mEntryCount;
	    if( size > 0 ) {
	    	float yPos = this.mListOffset + this.mEntrySpacing;
		   	for(int i = 0; i < size; i++) {
		   		
		   		if( ( this.mRect.mY + yPos + this.mEntryHeight > this.mRect.mY ) && //crop at the top
		   			( this.mRect.mY + yPos < this.mRect.mY + this.mRect.mHeight ) ) { //crop at bottom

			   		final BitsListEntry le = entryList.get(i);
			    	if( le != null ) {
			    		g.setColor(1f, 1f, 1f, 1f);
			        	g.drawRect(
			        			this.mRect.mX + entryPadding, //center 
			        			this.mRect.mY + yPos,
			        			this.mEntryWidth, 
			        			this.mEntryHeight );
	
			        	float textShift = 0f;
			        	if( le.mIcon != null ) {
			        		switch( le.mIconLocation ) {
			        			case BitsListEntry.ICON_LOCATION_LEFT:
			        				g.drawImage(
			        						le.mIcon, 
			        						this.mRect.mX + entryPadding + 4f, 
			        						this.mRect.mY + yPos + 4f, 
			        						this.mEntryHeight - 8f, 
			        						this.mEntryHeight - 8f );
			        				break;
			        			case BitsListEntry.ICON_LOCATION_RIGHT:
			        				g.drawImage(
			        						le.mIcon, 
			        						this.mRect.mX + entryPadding + this.mEntryWidth - this.mEntryHeight + 4f, 
			        						this.mRect.mY + yPos + 4f, 
			        						this.mEntryHeight - 8f, 
			        						this.mEntryHeight - 8f );
			        				textShift = this.mEntryHeight;
			        				break;
			        		}
			        	}
			        	
			        	if(	le.mText 		!= null && 
			        		this.mEntryFont != null ) {

			        		g.setColor(this.mFontColor[0], this.mFontColor[1], this.mFontColor[2], this.mFontColor[3]);
				        	switch( le.mTextLocation ) {
			        			case BitsListEntry.TEXT_LOCATION_LEFT:
			        				g.drawText(
			        						le.mText, 
			        						this.mRect.mX + entryPadding + this.mEntryHeight + 8f - textShift,
			        						this.mRect.mY + yPos + ( (this.mEntryHeight / 2f) - (this.mEntryFont.mFontHeight / 2f) ) );
			        				break;
			        			case BitsListEntry.TEXT_LOCATION_CENTER:
			        				g.centerText(
			        						le.mText, 
			        						this.mRect.mX + ( this.mRect.mWidth / 2f ), 
			        						this.mRect.mY + yPos + ( this.mEntryHeight / 2f ) );
			        				break;
			        			case BitsListEntry.TEXT_LOCATION_RIGHT:
			        				g.drawText(
			        						le.mText, 
			        						this.mRect.mX + entryPadding + this.mEntryWidth - this.mEntryFont.getLength(le.mText) - 8f - textShift, 
			        						this.mRect.mY + yPos + ( (this.mEntryHeight / 2f) - (this.mEntryFont.mFontHeight / 2f) ) );
			        				break;
				        	}
			        	}
			        }		    	
		   		}
		    	yPos += (this.mEntryHeight + this.mEntrySpacing);
		    }
	    }

	    g.restoreStates( );
	}

	@Override
	public final void 
	onUpdate(
			final float delta) {
		
    	if( this.isVisible == false || this.isActive == false ) { //if list is invisble and/or inactive do nothing
    		return;
    	}
	}
	
	public final void
	add( 
			final BitsListEntry listEntry ) {
		
		if( listEntry == null ) {
			return;
		}
		
		this.mEntryList.add( listEntry );
		this.mEntryCount++;
	}
	
	public final void
	removeAll( ) {

		this.mEntryList.clear();
		this.mEntryCount = 0;
	}
	
	public final void
	remove(
			final BitsListEntry listEntry ) {

		if( this.mEntryList.remove(listEntry) == true ) {
			this.mEntryCount--;
		}
	}
	
	public final void
	remove(
			final int index ) {
		
		this.mEntryList.remove(index);
		this.mEntryCount--;
		final float minY = ( this.mRect.mHeight - (this.mEntryCount * ( this.mEntryHeight + this.mEntrySpacing ) + this.mEntrySpacing) );
		if( this.mListOffset <= minY ) {
			this.mListOffset = minY;
		}
		if( this.mListOffset > 0 ) {
			this.mListOffset = 0;
		}
	}
	
	public final BitsListEntry
	get(
			final int index ) {
		
		return this.mEntryList.get(index);
	}

	@Override
	public final boolean
	onPointerDown(
			final int pointerId, 
			final float x, 
			final float y,
			final BitsPointerEvent event ) {

    	if( this.isVisible == true && pointerId == 0 && this.mRect.contains(x,y) == true ) {
			if( this.isActive == true ) { //change list state if list is active only
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

		if( this.isVisible == true && pointerId == 0 && this.isPressed == true && this.isDragged == false ) { 
			this.isPressed = false;
			if( this.mListener != null ) {
				final int index = (int)((y - this.mRect.mY - this.mListOffset ) / (this.mEntrySpacing + this.mEntryHeight) );
				if( index >= 0 && index < this.mEntryList.size() ) {
					return this.mListener.onListEntryPressed( index, this.mEntryList.get(index) );
				}
			}
			return true;
		} else {
			if( this.isPressed == true ) {
				this.isPressed = false;
				this.isDragged = false;
			}
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
			this.isDragged = true;
			final float minY = ( this.mRect.mHeight - (this.mEntryCount * ( this.mEntryHeight + this.mEntrySpacing ) + this.mEntrySpacing) );
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

        this.mListener = null;
        this.mEntryList.clear();
    }
}
