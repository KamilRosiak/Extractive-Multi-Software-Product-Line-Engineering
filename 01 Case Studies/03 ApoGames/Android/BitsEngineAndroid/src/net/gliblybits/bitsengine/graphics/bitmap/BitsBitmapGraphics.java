
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

package net.gliblybits.bitsengine.graphics.bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import net.gliblybits.bitsengine.graphics.BitsDrawable;
import net.gliblybits.bitsengine.graphics.BitsFont;
import net.gliblybits.bitsengine.graphics.BitsGraphics;
import net.gliblybits.bitsengine.render.state.BitsRenderState;
import net.gliblybits.bitsengine.utils.BitsLog;
import net.gliblybits.bitsengine.utils.BitsRect;
import net.gliblybits.bitsengine.utils.BitsRectF;

public final class 
BitsBitmapGraphics 
extends BitsGraphics {

	private final Canvas 	mCanvas;
	private final Paint  	mPaint;
	private final Rect 		mRectS = new Rect();
	private final RectF 	mRectD = new RectF();
	private final int 		mType;
	
	public 
	BitsBitmapGraphics( 
			final Bitmap source ) {
		
		if( source == null ) {
			BitsLog.e("BitsBitmapGraphics - constructor", "The given Bitmap is Null!");
		}
		
		this.reset();
		
		this.mCanvas 	= new Canvas( source );
		this.mPaint		= new Paint();
		
		if(source.getConfig() == Bitmap.Config.RGB_565) {
			this.mType = BitsBitmap.TYPE_RGB;
    		this.mPaint.setColor(
    				Color.rgb( 
    						(int)(this.mLastColor[0] * 255f), 
    						(int)(this.mLastColor[1] * 255f), 
    						(int)(this.mLastColor[2] * 255f) ) );
		} else {			
			this.mType = BitsBitmap.TYPE_ARGB;
    		this.mPaint.setColor(
    				Color.argb( 
    						(int)(this.mLastColor[3] * 255f), 
    						(int)(this.mLastColor[0] * 255f), 
    						(int)(this.mLastColor[1] * 255f),
    						(int)(this.mLastColor[2] * 255f) ) );
		}
	}
	
    /***************************************
     * OVERRIDE METHODS                    *
     ***************************************/
	
    @Override
    public final void
    restoreStates() {
    	
    	final BitsRenderState renderState = this.mStateStack.pop();
    	if( renderState == null ) {
    		return;
    	}
    	
    	final int stateMask = renderState.mStateMask;
    	
    	if( (stateMask & BitsRenderState.STATE_COLOR) == BitsRenderState.STATE_COLOR ) {
    		this.setColor(renderState.mColor[0], renderState.mColor[1], renderState.mColor[2], renderState.mColor[3]);
    	}
    	
    	if( (stateMask & BitsRenderState.STATE_ROTATION) == BitsRenderState.STATE_ROTATION ) {
    		this.setRotation(renderState.mRotation[0], renderState.mRotation[1], renderState.mRotation[2]);
    	}

    	if( (stateMask & BitsRenderState.STATE_LINESIZE) == BitsRenderState.STATE_LINESIZE ) {
    		this.setLineSize(renderState.mLineSize);
    	}
    	
        if( (stateMask & BitsRenderState.STATE_SCALE) == BitsRenderState.STATE_SCALE ) {
        	this.setScale(renderState.mScale);
        }
    	
        if( (stateMask & BitsRenderState.STATE_FONT) == BitsRenderState.STATE_FONT ) {
        	this.setFont(renderState.mFont);
        }
    	
        if( (stateMask & BitsRenderState.STATE_CLIP) == BitsRenderState.STATE_CLIP ) {
        	this.setClip(renderState.mClip.mX, renderState.mClip.mY, renderState.mClip.mWidth, renderState.mClip.mHeight);
        }
        
        if( (stateMask & BitsRenderState.STATE_TRANSLATE) == BitsRenderState.STATE_TRANSLATE ) {
        	this.setTranslation(renderState.mTranslate);
        }
        
        this.mStateStack.checkIn(renderState);
    }
    
    @Override
	public final void 
	release( ) {
    	
    	this.reset();
	}
    
    @Override
    public final void 
    setClip( 
    		final BitsRect rect ) {
 
    	if( rect != null ) {
    		this.setClip(rect.mX, rect.mY, rect.mWidth, rect.mHeight);
    	}
    }
    
    @Override
    public final void 
    setClip( 
    		final BitsRectF rect ) {
 
    	if( rect != null ) {
    		this.setClip(rect.mX, rect.mY, rect.mWidth, rect.mHeight);
    	}
    }
	
    @Override
    public final void 
    setClip( 
    		final float x, 
    		final float y, 
    		final float width, 
    		final float height ) {
    	
    	if( this.mLastClip.mX != x || 
    		this.mLastClip.mY != y || 
    		this.mLastClip.mWidth != width || 
    		this.mLastClip.mHeight != height ) {
        	
    		this.mLastClip.mX = x;
        	this.mLastClip.mY = y;
        	this.mLastClip.mWidth = width;
        	this.mLastClip.mHeight = height;
        	
        	this.mCanvas.clipRect(
        			x, 
        			y, 
        			width, 
        			height);
    	}
    }

    @Override
    public final void 
    setLineSize( 
    		final float size ) {
    	if( size > 0 && 
    		this.mLastLineSize != size ) {
    		
    		this.mPaint.setStrokeWidth( size );
    		this.mLastLineSize = size;
    	}
    }
    
    @Override
    public final void 
    setColor( 
    		final int[] color ) {

    	if( color.length == 3 ) {
    		this.setColor( color[0], color[1], color[2] );
    		return;
    	}
    	
    	if( color.length == 4) {
    		this.setColor( color[0], color[1], color[2], color[3] );
    		return;
    	}
    }
    
    @Override
    public final void 
    setColor( 
    		final float[] color ) {

    	if( color.length == 3 ) {
    		this.setColor( color[0], color[1], color[2] );
    		return;
    	}
    	
    	if( color.length == 4) {
    		this.setColor( color[0], color[1], color[2], color[3] );
    		return;
    	}
    }

    @Override
    public final void 
    setColor( 
    		final int red, 
    		final int green, 
    		final int blue ) {
    	this.setColor( 
    			((float)red) / 255f, 
    			((float)green) / 255f,
    			((float)blue) / 255f,
    			this.mLastColor[3] );
    }
    
    @Override
    public final void 
    setColor( 
    		final int red, 
    		final int green, 
    		final int blue, 
    		final int alpha ) {
    	this.setColor( 
    			((float)red) / 255f, 
    			((float)green) / 255f,
    			((float)blue) / 255f,
    			((float)blue) / 255f );
    }

    @Override
    public final void 
    setColor( 
    		final float red, 
    		final float green, 
    		final float blue ) {
    	this.setColor( 
    			red, 
    			green, 
    			blue, 
    			this.mLastColor[3] );
    }
    
    @Override
    public final void 
    setColor( 
    		final float red, 
    		final float green, 
    		final float blue, 
    		final float alpha ) {
    	if( red != this.mLastColor[0] 	|| 
    		green != this.mLastColor[1] || 
    		blue != this.mLastColor[2] 	|| 
    		alpha != this.mLastColor[3] ) {

    		this.mLastColor[0] = red;
    		this.mLastColor[1] = green;
    		this.mLastColor[2] = blue;
    		this.mLastColor[3] = alpha;

    		if(this.mType == BitsBitmap.TYPE_RGB) {
        		this.mPaint.setColor(
        				Color.rgb( 
        						(int)(this.mLastColor[0] * 255f), 
        						(int)(this.mLastColor[1] * 255f), 
        						(int)(this.mLastColor[2] * 255f) ) );
    		} else {			
        		this.mPaint.setColor(
        				Color.argb( 
        						(int)(this.mLastColor[3] * 255f), 
        						(int)(this.mLastColor[0] * 255f), 
        						(int)(this.mLastColor[1] * 255f),
        						(int)(this.mLastColor[2] * 255f) ) );
    		}
    	}
    }
    
    @Override
    public final void 
    setScale( 
    		final float scaleFactor ) {
    	if( scaleFactor > 0f && 
    		this.mLastScale != scaleFactor ) {
    		
    		this.mLastScale = scaleFactor;    		
    		this.mCanvas.scale(
    				scaleFactor, 
    				scaleFactor);
    	}
    }

    @Override
    public final void 
    setRotation( 
    		final float[] rotation ) {
    	
    	if( rotation.length == 3 ) {
    		this.setRotation(rotation[0], rotation[1], rotation[2]);
    	}
    }
    
    @Override
    public final void 
    setRotation( 
    		final float rotation ) {
    	this.setRotation(
    			rotation, 
    			0, 
    			0);
    }
    
    @Override
    public final void 
    setRotation( 
    		float rotation, 
    		final float x, 
    		final float y ) {
    	
    	if( this.mLastRotation[0] != rotation || 
    		this.mLastRotation[1] != x || 
    		this.mLastRotation[2] != y ) {
    		
    		this.mLastRotation[0] = rotation;
    		this.mLastRotation[1] = x;
    		this.mLastRotation[2] = y;
    		
    		this.mCanvas.rotate(rotation, x, y);
    	}
    }
    
    public final void 
    setTranslation( 
    		final float x, 
    		final float y ) {
    	
    	if( this.mLastTranslate[0] != x ||
    		this.mLastTranslate[1] != y ) {
    		
    		this.mLastTranslate[0] = x;
    		this.mLastTranslate[1] = y;
    		
    		this.mCanvas.translate(x, y);
    	}
    }
    
    public final void 
    setTranslation( 
    		final float[] translation ) {

    	if( this.mLastTranslate[0] != translation[0] ||
        	this.mLastTranslate[1] != translation[1] ) {
        		
        		this.mLastTranslate[0] = translation[0];
        		this.mLastTranslate[1] = translation[1];
        		
        		this.mCanvas.translate(translation[0], translation[1]);
        }
    }
    
    @Override
    public final void 
    drawImage( 
    		final BitsDrawable drawable, 
    		final float x, 
    		final float y ) {
    	
    	if( drawable.mType == BitsDrawable.DRAWABLE_TYPE_BITMAP ) {
    		this.mCanvas.drawBitmap( 
    				((BitsBitmap)drawable).mSource, 
    				x, 
    				y, 
    				this.mPaint);
    	} else {
    		BitsLog.e("BitsBitmapGraphics - drawImage", "The given BitsDrawable is not a BitsBitmap!");
    		throw new IllegalArgumentException("The given BitsDrawable is not a BitsBitmap!");
    	}
    }

    @Override
    public final void 
    drawImage( 
    		final BitsDrawable drawable, 
    		final float x, 
    		final float y, 
    		final float width, 
    		final float height ) {
    	this.cropImage( 
    			drawable, 
    			x, 
    			y, 
    			width, 
    			height, 
    			0, 
    			0, 
    			((BitsBitmap)drawable).getWidth(), 
    			((BitsBitmap)drawable).getHeight() );
    }
    
    @Override
    public final void 
    drawImage( 
    		final BitsDrawable drawable, 
    		final BitsRectF destRect ) {
    	
    	if( destRect != null ) {
    		this.drawImage(drawable, destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight);
    	}
    }
    
    @Override
    public final void 
    drawImage( 
    		final BitsDrawable drawable, 
    		final BitsRect destRect ) {
    	
    	if( destRect != null ) {
    		this.drawImage(drawable, destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight);
    	}
    }
    
    @Override
    public final void 
    cropImage( 
    		final BitsDrawable drawable, 
    		final float x, 
    		final float y, 
    		final float width, 
    		final float height,
    		final float cropX,
    		final float cropY,
    		final float cropWidth,
    		final float cropHeight ) {
    	if( drawable.mType == BitsDrawable.DRAWABLE_TYPE_BITMAP ) {
    		
    		this.mCanvas.save(); //save matrix
   			this.mCanvas.scale(width > 0 ? 1 : -1, height > 0 ? 1 : -1); //flip x/y axis
   			
    		//where to draw
    		this.mRectD.left	= (int)(width > 0 ? x : -x);
    		this.mRectD.top 	= (int)(height > 0 ? y : -y);
    		this.mRectD.right 	= (int)(width > 0 ? x + width : -x - width);
    		this.mRectD.bottom 	= (int)(height > 0 ? y + height : -y - height);
			
    		//what to draw
    		this.mRectS.left 	= (int)cropX;
    		this.mRectS.top 	= (int)cropY;
    		this.mRectS.right 	= (int)(cropX + cropWidth);
    		this.mRectS.bottom 	= (int)(cropY + cropHeight);
    		
    		this.mCanvas.drawBitmap( 
    				((BitsBitmap)drawable).mSource, 
    				this.mRectS, 
    				this.mRectD, 
    				this.mPaint );

    		this.mCanvas.restore(); //restore matrix
    	} else {
    		BitsLog.e("BitsBitmapGraphics - cropImage", "The given BitsDrawable is not a BitsBitmap!");
    		throw new IllegalArgumentException("The given BitsDrawable is not a BitsBitmap!");
    	}
    }
    
    @Override
    public final void 
    cropImage( 
    		final BitsDrawable drawable, 
    		final BitsRectF destRect,
    		final BitsRectF cropRect ) {
    	
    	if( destRect != null && cropRect != null ) {
    		this.cropImage(drawable, destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, cropRect.mX, cropRect.mY, cropRect.mWidth, cropRect.mHeight);
    	}
    }
    
    @Override
    public final void 
    cropImage( 
    		final BitsDrawable drawable, 
    		final BitsRect destRect,
    		final BitsRect cropRect ) {
    	
    	if( destRect != null && cropRect != null ) {
    		this.cropImage(drawable, destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, cropRect.mX, cropRect.mY, cropRect.mWidth, cropRect.mHeight);
    	}
    }
    
    @Override
    public final void 
    drawLine( 
    		final float x1, 
    		final float y1, 
    		final float x2, 
    		final float y2 ) {
    	this.mCanvas.drawLine(
    			x1, 
    			y1, 
    			x2, 
    			y2, 
    			this.mPaint);
    }
    
    @Override
    public final void 
    drawRect( 
    		float x, 
    		float y, 
    		float width, 
    		float height ) {
    	this.mPaint.setStyle( Paint.Style.STROKE );
    	
    	this.mCanvas.drawRect(
    			width > 0 ? x : x + width,
        		height > 0 ? y : y + height,
        		width > 0 ? x + width : x,
        		height > 0 ? y + height : y,
    			this.mPaint);
    }
    
    @Override
    public final void 
    drawRect( 
    		final BitsRectF destRect ) {
    	
    	if( destRect != null ) {
    		this.drawRect(destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight);
    	}
    }
    
    @Override
    public final void 
    drawRect( 
    		final BitsRect destRect ) {
    	
    	if( destRect != null ) {
    		this.drawRect(destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight);
    	}
    }
    
    @Override
    public final void 
    fillRect( 
    		final float x, 
    		final float y, 
    		final float width, 
    		final float height ) {
    	this.mPaint.setStyle( Paint.Style.FILL );
    	this.mCanvas.drawRect(
    			width > 0 ? x : x + width,
    	       	height > 0 ? y : y + height,
    	        width > 0 ? x + width : x,
    	        height > 0 ? y + height : y,
    	    	this.mPaint);
    }
    
    @Override
    public final void 
    fillRect( 
    		final BitsRectF destRect ) {
    	
    	if( destRect != null ) {
    		this.fillRect(destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight);
    	}
    }
    
    @Override
    public final void 
    fillRect( 
    		final BitsRect destRect ) {
    	
    	if( destRect != null ) {
    		this.fillRect(destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight);
    	}
    }
    
    @Override
    public final void 
    drawRect( 
    		final float x, 
    		final float y, 
    		final float width, 
    		final float height, 
    		final boolean outlined ) {
    	if( outlined == true ) {
    		this.drawRect(x, y, width, height);
    	} else {
    		this.fillRect(x, y, width, height);
    	}
    }
    
    @Override
    public final void 
    drawRect( 
    		final BitsRectF destRect, 
    		final boolean outlined ) {
    	
    	if( destRect != null ) {
    		this.drawRect(destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, outlined);
    	}
    }

    @Override
    public final void 
    drawRect( 
    		final BitsRect destRect, 
    		final boolean outlined ) {
    	
    	if( destRect != null ) {
    		this.drawRect(destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, outlined);
    	}
    }

    @Override
    public final void 
    drawRoundRect( 
    		final float x, 
    		final float y, 
    		final float width,
    		final float height,
    		final float curveRadius,
    		final int curvePoints ) {
		//where to draw
		this.mRectD.left	= (int)(width > 0 ? x : x + width);
		this.mRectD.top 	= (int)(height > 0 ? y : y + height);
		this.mRectD.right 	= (int)(width > 0 ? x + width : x);
		this.mRectD.bottom 	= (int)(height > 0 ? y + height : y);
    	
    	this.mPaint.setStyle( Paint.Style.STROKE );
    	this.mCanvas.drawRoundRect(this.mRectD, curveRadius, curveRadius, this.mPaint);
    }
    
    @Override
    public final void 
    drawRoundRect( 
    		final BitsRectF destRect,
    		final float curveRadius,
    		final int curvePoints ) {
    	
    	if( destRect != null ) {
    		this.drawRoundRect( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, curveRadius, curvePoints );
    	}
    }
    
    @Override
    public final void 
    drawRoundRect( 
    		final BitsRect destRect,
    		final float curveRadius,
    		final int curvePoints ) {
    	
    	if( destRect != null ) {
    		this.drawRoundRect( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, curveRadius, curvePoints );
    	}
    }
    
    @Override
    public final void 
    fillRoundRect(
    		final float x,
    		final float y,
    		final float width,
    		final float height,
    		final float curveRadius,
    		final int curvePoints ) {
    	
		//where to draw
		this.mRectD.left	= (int)(width > 0 ? x : x + width);
		this.mRectD.top 	= (int)(height > 0 ? y : y + height);
		this.mRectD.right 	= (int)(width > 0 ? x + width : x);
		this.mRectD.bottom 	= (int)(height > 0 ? y + height : y);

    	this.mPaint.setStyle( Paint.Style.FILL );
    	this.mCanvas.drawRoundRect(this.mRectD, curveRadius, curveRadius, this.mPaint);
    }
    
    @Override
    public final void 
    fillRoundRect(
    		final BitsRectF destRect,
    		final float curveRadius,
    		final int curvePoints ) {
    	
       	if( destRect != null ) {
    		this.fillRoundRect( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, curveRadius, curvePoints );
    	}
    }
    
    @Override
    public final void 
    fillRoundRect(
    		final BitsRect destRect,
    		final float curveRadius,
    		final int curvePoints ) {
    	
       	if( destRect != null ) {
    		this.fillRoundRect( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, curveRadius, curvePoints );
    	}
    }
    
    @Override
    public final void 
    drawRoundRect( 
    		final float x, 
    		final float y, 
    		final float width,
    		final float height,
    		final float curveRadius,
    		final int curvePoints,
    		final boolean outlined) {
    	if( outlined == true ) {
    		this.drawRoundRect(x, y, width, height, curveRadius, curvePoints);
    	} else {
    		this.fillRoundRect(x, y, width, height, curveRadius, curvePoints);
    	}
    }
    
    @Override
    public final void drawRoundRect( 
    		final BitsRectF destRect,
    		final float curveRadius,
    		final int curvePoints,
    		final boolean outlined) {
    	
       	if( destRect != null ) {
    		this.drawRoundRect( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, curveRadius, curvePoints, outlined );
    	}
    }
    
    @Override
    public final void drawRoundRect( 
    		final BitsRect destRect,
    		final float curveRadius,
    		final int curvePoints,
    		final boolean outlined) {
    	
       	if( destRect != null ) {
    		this.drawRoundRect( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, curveRadius, curvePoints, outlined );
    	}
    }

    @Override
    public final void drawArc( 
    		final float x, 
    		final float y, 
    		final float radius, 
    		final float startAngle, 
    		final float arcAngle ) {
    	this.drawArc( x, y, radius, radius, startAngle, arcAngle );
    }

    @Override
    public final void drawArc( 
    		final float x, 
    		final float y, 
    		final float radiusX, 
    		final float radiusY, 
    		final float startAngle, 
    		final float arcAngle ) {
    	//where to draw
    	this.mRectD.left 	= x;
    	this.mRectD.top 	= y;
    	this.mRectD.right 	= x + radiusX;
    	this.mRectD.bottom 	= y + radiusY;
    	
    	this.mPaint.setStyle( Paint.Style.STROKE );
    	this.mCanvas.drawArc( this.mRectD, startAngle, arcAngle, false, this.mPaint );
    }
    
	@Override
	public final void 
	drawArc(
			final float x, 
			final float y, 
			final float radius, 
			final float startAngle,
			final float arcAngle, 
			final int pointCount,
			final boolean wantCenterPoint ) {
		
		this.drawArc( x, y, radius, radius, startAngle, arcAngle );
	}

	@Override
	public final void 
	drawArc(
			final float x, 
			final float y, 
			final float radiusX, 
			final float radiusY,
			final float startAngle, 
			final float arcAngle, 
			final int pointCount,
			final boolean wantCenterPoint ) {

		this.drawArc( x, y, radiusX, radiusY, startAngle, arcAngle );
	}

	@Override
	public final void 
	drawArc(
			final BitsRectF destRect, 
			final float startAngle, 
			final float arcAngle,
			final int pointCount, 
			final boolean wantCenterPoint ) {

		if( destRect != null ) {
			this.drawArc( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, startAngle, arcAngle );
		}
	}

	@Override
	public final void 
	drawArc(
			final BitsRect destRect, 
			final float startAngle, 
			final float arcAngle,
			final int pointCount, 
			final boolean wantCenterPoint) {

		if( destRect != null ) {
			this.drawArc( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, startAngle, arcAngle );
		}		
	}
    
    @Override
    public final void drawArc( 
    		final BitsRectF destRect, 
    		final float startAngle, 
    		final float arcAngle ) {
    	
       	if( destRect != null ) {
       		this.drawArc( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, startAngle, arcAngle );
    	}
    }

    @Override
    public final void drawArc( 
    		final BitsRect destRect, 
    		final float startAngle, 
    		final float arcAngle ) {
    	
       	if( destRect != null ) {
       		this.drawArc( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, startAngle, arcAngle );
    	}
    }
    
    @Override
    public final void fillArc( 
    		final float x, 
    		final float y, 
    		final float radius, 
    		final float startAngle, 
    		final float arcAngle ) {
    	
    	this.fillArc( x, y, radius, radius, startAngle, arcAngle );
    }
    
    @Override
    public final void fillArc( 
    		final float x, 
    		final float y, 
    		final float radiusX, 
    		final float radiusY, 
    		final float startAngle, 
    		final float arcAngle ) {
    	//where to draw
    	this.mRectD.left 	= x;
    	this.mRectD.top 	= y;
    	this.mRectD.right 	= x + radiusX;
    	this.mRectD.bottom 	= y + radiusY;
    	
    	this.mPaint.setStyle( Paint.Style.FILL );
    	this.mCanvas.drawArc( this.mRectD, startAngle, arcAngle, false, this.mPaint );
    }
    
	@Override
	public final void 
	fillArc( 
			final float x, 
			final float y, 
			final float radius, 
			final float startAngle,
			final float arcAngle, 
			final int pointCount) {

		this.fillArc( x, y, radius, radius, startAngle, arcAngle );
	}

	@Override
	public final void 
	fillArc(
			final float x, 
			final float y, 
			final float radiusX, 
			final float radiusY,
			final float startAngle, 
			final float arcAngle, 
			final int pointCount) {

		this.fillArc( x, y, radiusX, radiusY, startAngle, arcAngle );		
	}

	@Override
	public final void 
	fillArc(
			final BitsRectF destRect, 
			final float startAngle, 
			final float arcAngle,
			final int pointCount ) {

       	if( destRect != null ) {
       		this.fillArc( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, startAngle, arcAngle );
    	}
	}

	@Override
	public final void 
	fillArc(
			final BitsRect destRect, 
			final float startAngle, 
			final float arcAngle,
			final int pointCount) {

       	if( destRect != null ) {
       		this.fillArc( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, startAngle, arcAngle );
    	}
	}
    
    @Override
    public final void fillArc( 
    		final BitsRectF destRect, 
    		final float startAngle, 
    		final float arcAngle ) {
    	
       	if( destRect != null ) {
       		this.fillArc( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, startAngle, arcAngle );
    	}
    }
    
    @Override
    public final void fillArc( 
    		final BitsRect destRect, 
    		final float startAngle, 
    		final float arcAngle ) {
    	
       	if( destRect != null ) {
       		this.fillArc( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, startAngle, arcAngle );
    	}
    }
    
    @Override
    public final void drawCircle( 
    		final float x,
    		final float y, 
    		final float radius ) {
    	this.mPaint.setStyle( Paint.Style.STROKE );
    	this.mCanvas.drawCircle(x, y, radius, this.mPaint);
    }
    
	@Override
	public final void 
	drawCircle(
			final float x, 
			final float y, 
			final float radius, 
			final int pointCount ) {

		this.drawCircle(x, y, radius);
	}

    @Override
    public final void fillCircle( 
    		final float x, 
    		final float y, 
    		final float radius ) {
    	this.mPaint.setStyle( Paint.Style.FILL );
    	this.mCanvas.drawCircle(x, y, radius, this.mPaint);
    }
    
	@Override
	public final void 
	fillCircle(
			final float x, 
			final float y, 
			final float radius, 
			final int pointCount ) {
	
		this.fillCircle(x, y, radius);
	}
    
    @Override
    public final void drawOval( 
    		final float x, 
    		final float y, 
    		final float radiusX, 
    		final float radiusY ) {
    	//where to draw
    	this.mRectD.left 	= x;
    	this.mRectD.top 	= y;
    	this.mRectD.right 	= x + radiusX;
    	this.mRectD.bottom 	= y + radiusY;
    	
    	this.mPaint.setStyle( Paint.Style.STROKE );
    	this.mCanvas.drawOval( this.mRectD, this.mPaint);
    }
    
    @Override
    public final void drawOval( 
    		final BitsRectF destRect ) {
    	
       	if( destRect != null ) {
        	this.drawOval(destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight);
    	}
    }
    
    @Override
    public final void drawOval( 
    		final BitsRect destRect ) {
    	
       	if( destRect != null ) {
        	this.drawOval(destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight);

    	}
    }
    
	@Override
	public final void 
	drawOval(
			final float x, 
			final float y, 
			final float radiusX, 
			final float radiusY,
			final int pointCount) {

		this.drawOval( x, y, radiusX, radiusY );
	}

	@Override
	public final void 
	drawOval(
			final float x, 
			final float y, 
			final float radiusX, 
			final float radiusY,
			final boolean filled, 
			final boolean isArc, 
			final float startAngle, 
			final float arcAngle,
			final int pointCount, 
			final boolean wantCenterPoint ) {

		this.drawOval(x, y, radiusX, radiusY);
	}

	@Override
	public final void 
	drawOval(
			final BitsRectF destRect, 
			final int pointCount ) {
		
		if( destRect != null ) {
			this.drawOval( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight );
		}
	}

	@Override
	public final void 
	drawOval(
			final BitsRect destRect, 
			final int pointCount ) {

		if( destRect != null ) {
			this.drawOval( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight );
		}
	}
	
	@Override
	public final void drawOval(
			final BitsRectF destRect, 
			final boolean filled, 
			final boolean isArc,
			final float startAngle, 
			final float arcAngle, 
			final int pointCount,
			final boolean wantCenterPoint ) {

		if( destRect != null ) {
			this.drawOval( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight );
		}
	}

	@Override
	public final void 
	drawOval(
			final BitsRect destRect,
			final boolean filled, 
			final boolean isArc,
			final float startAngle, 
			final float arcAngle, 
			final int pointCount,
			final boolean wantCenterPoint ) {
		
		if( destRect != null ) {
			this.drawOval( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight );
		}
	}

    @Override
    public final void fillOval( 
    		final float x, 
    		final float y, 
    		final float radiusX, 
    		final float radiusY ) {
    	//where to draw
    	this.mRectD.left 	= x;
    	this.mRectD.top 	= y;
    	this.mRectD.right 	= x + radiusX;
    	this.mRectD.bottom 	= y + radiusY;
    	
    	this.mPaint.setStyle( Paint.Style.FILL );
    	this.mCanvas.drawOval( this.mRectD, this.mPaint);
    }
    
	@Override
	public final void 
	fillOval(
			final float x, 
			final float y, 
			final float radiusX, 
			final float radiusY,
			final int pointCount ) {

		this.fillOval(x, y, radiusX, radiusY);
	}

	@Override
	public final void 
	fillOval(
			final BitsRectF destRect, 
			final int pointCount ) {

		if( destRect != null ) {
			this.fillOval(destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight);
		}
	}

	@Override
	public final void 
	fillOval(
			final BitsRect destRect, 
			final int pointCount ) {

		if( destRect != null ) {
			this.fillOval(destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight);
		}
	}
    
    @Override
    public final void fillOval( 
    		final BitsRectF destRect ) {
    	
      	if( destRect != null ) {
        	this.fillOval(destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight);
    	}
    }
    
    @Override
    public final void fillOval( 
    		final BitsRect destRect ) {
    	
      	if( destRect != null ) {
        	this.fillOval(destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight);
    	}
    }
    
	@Override
	public final void 
	setFont(
			final BitsFont font ) {
		
		if( font != null ) {
			if( font.mType != BitsDrawable.DRAWABLE_TYPE_GL_FONT ) {
	    		BitsLog.e("BitsGLGraphics - setFont", "The given BitsFont is not an object of the type BitsGLFont!");
	    		throw new IllegalArgumentException("The given BitsFont is not an object of the type BitsGLFont!");
	    	} else {
	    		if( font.isLoaded == false ) {
	    			BitsLog.w("BitsGLGraphics - setFont", "The given BitsFont has not been loaded yet!");
	    		}
	    		this.mPaint.setTypeface( ((BitsBitmapFont)font).mSource );
	    	}
		} else {
			this.mPaint.setTypeface( null );
		}
		
		this.mLastFont = font;
	}
    
    @Override
    public final void 
    drawText( 
    		final String text,
    		final float x, 
    		final float y ) {
    	
    	this.mCanvas.drawText(text, x, y, this.mPaint);
    }
    
	@Override
	public final void 
	drawTriangle(
			final BitsDrawable drawable,
			final float x1, 
			final float y1, 
			final float x2, 
			final float y2, 
			final float x3,
			final float y3 ) {

		// TODO empty
	}

	@Override
	public void drawTriangle(BitsDrawable drawable, float x1, float y1,
			float x2, float y2, float x3, float y3, float texX1, float texY1,
			float texX2, float texY2, float texX3, float texY3) {
		
		// TODO empty
		
	}
}
