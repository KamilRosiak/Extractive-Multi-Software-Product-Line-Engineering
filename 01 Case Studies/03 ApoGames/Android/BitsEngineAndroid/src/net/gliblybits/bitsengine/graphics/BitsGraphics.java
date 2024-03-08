
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

package net.gliblybits.bitsengine.graphics;

import net.gliblybits.bitsengine.core.BitsApp;
import net.gliblybits.bitsengine.render.state.BitsRenderState;
import net.gliblybits.bitsengine.render.state.BitsRenderStateStack;
import net.gliblybits.bitsengine.utils.BitsLog;
import net.gliblybits.bitsengine.utils.BitsRect;
import net.gliblybits.bitsengine.utils.BitsRectF;

public abstract class 
BitsGraphics {
	
	protected final BitsRenderStateStack mStateStack = new BitsRenderStateStack();

    protected final BitsRectF mLastClip 	= new BitsRectF(0f, 0f, 0f, 0f);
    protected final float[] mLastColor 		= { -1f, -1f, -1f, -1f };
    protected final float[] mLastRotation 	= { 0f, 0f, 0f };
    protected final float[] mLastTranslate	= { 0f, 0f };
    protected float 		mLastLineSize 	= 1f;
    protected float 		mLastScale 		= 1f;
    protected BitsFont		mLastFont		= null;
    
    public final void 
    reset( ) {
    	
    	this.mLastColor[0] = -1f;
    	this.mLastColor[1] = -1f;
    	this.mLastColor[2] = -1f;
    	this.mLastColor[3] = -1f;
    	
    	this.mLastRotation[0] = 0f;
    	this.mLastRotation[1] = 0f;
    	this.mLastRotation[2] = 0f;
    	
    	this.mLastTranslate[0] = 0f;
    	this.mLastTranslate[1] = 0f;
    	
    	this.mLastClip.mX 		= 0;
    	this.mLastClip.mY 		= 0;
    	this.mLastClip.mWidth 	= BitsApp.sViewportWidth;
    	this.mLastClip.mHeight 	= BitsApp.sViewportHeight;
    	
    	this.mLastLineSize = 1f;
    	
    	this.mLastScale = 1f;
    	
    	this.mLastFont = null;
    }
    
    public abstract void release();

    public final void
    clearStates() {
    	
    	this.mStateStack.clearStack();
    }
    
    public final void
    saveStates( 
    		final int stateMask ) {
    	
    	final BitsRenderState renderState = this.mStateStack.checkOut();
    	if( renderState == null ) {
    		BitsLog.e("BitsGraphics - saveStates", "The BitsRenderState is Null!");
    		throw new NullPointerException("The BitsRenderState is Null!");
    	}
    	
    	renderState.mStateMask = stateMask;
    	
    	if( (stateMask & BitsRenderState.STATE_CLIP) == BitsRenderState.STATE_CLIP ) {
    		renderState.mClip.mX 		= this.mLastClip.mX;
    		renderState.mClip.mY 		= this.mLastClip.mY;
    		renderState.mClip.mWidth 	= this.mLastClip.mWidth;
    		renderState.mClip.mHeight 	= this.mLastClip.mHeight;
    	}
    	
    	if( (stateMask & BitsRenderState.STATE_COLOR) == BitsRenderState.STATE_COLOR ) {
    		renderState.mColor[0] = this.mLastColor[0];
    		renderState.mColor[1] = this.mLastColor[1];
    		renderState.mColor[2] = this.mLastColor[2];
    		renderState.mColor[3] = this.mLastColor[3];
    	}
    	
    	if( (stateMask & BitsRenderState.STATE_ROTATION) == BitsRenderState.STATE_ROTATION ) {
    		renderState.mRotation[0] = this.mLastRotation[0];
    		renderState.mRotation[1] = this.mLastRotation[1];
    		renderState.mRotation[2] = this.mLastRotation[2];
    	}
    	
    	if( (stateMask & BitsRenderState.STATE_LINESIZE) == BitsRenderState.STATE_LINESIZE ) {
    		renderState.mLineSize = this.mLastLineSize;
    	}
    	
    	if( (stateMask & BitsRenderState.STATE_SCALE) == BitsRenderState.STATE_SCALE ) {
    		renderState.mScale = this.mLastScale;
    	}
    	
    	if( (stateMask & BitsRenderState.STATE_FONT) == BitsRenderState.STATE_FONT ) {
    		renderState.mFont = this.mLastFont;
    	}
    	
    	if( (stateMask & BitsRenderState.STATE_TRANSLATE) == BitsRenderState.STATE_TRANSLATE ) {
    		renderState.mTranslate[0] = this.mLastTranslate[0];
    		renderState.mTranslate[1] = this.mLastTranslate[1];
    	}
    	
    	this.mStateStack.push( renderState );
    }
    
    public final void
    saveStates() {
    	
    	final BitsRenderState renderState = this.mStateStack.checkOut();
    	if( renderState == null ) {
    		BitsLog.e("BitsGraphics - saveStates", "The BitsRenderState is Null!");
    		throw new NullPointerException("The BitsRenderState is Null!");
    	}

    	renderState.mStateMask = ( BitsRenderState.STATE_TRANSLATE | BitsRenderState.STATE_CLIP | BitsRenderState.STATE_COLOR | BitsRenderState.STATE_FONT | BitsRenderState.STATE_LINESIZE | BitsRenderState.STATE_ROTATION | BitsRenderState.STATE_SCALE );
    	
    	renderState.mClip.mX 		= this.mLastClip.mX;
    	renderState.mClip.mY 		= this.mLastClip.mY;
    	renderState.mClip.mWidth 	= this.mLastClip.mWidth;
    	renderState.mClip.mHeight 	= this.mLastClip.mHeight;
    	
    	renderState.mColor[0] = this.mLastColor[0];
    	renderState.mColor[1] = this.mLastColor[1];
    	renderState.mColor[2] = this.mLastColor[2];
    	renderState.mColor[3] = this.mLastColor[3];
    	
    	renderState.mRotation[0] = this.mLastRotation[0];
    	renderState.mRotation[1] = this.mLastRotation[1];
    	renderState.mRotation[2] = this.mLastRotation[2];
    	
    	renderState.mTranslate[0] = this.mLastTranslate[0];
    	renderState.mTranslate[1] = this.mLastTranslate[1];
    	
    	renderState.mLineSize = this.mLastLineSize;
    	
    	renderState.mScale = this.mLastScale;
    	
    	renderState.mFont = this.mLastFont;
    	
    	this.mStateStack.push( renderState );
    }

    public abstract void restoreStates();
    
    public final BitsRectF getClip( ) { return this.mLastClip; }
    public abstract void setClip( final float x, final float y, final float width, final float height );
    public abstract void setClip( final BitsRect rect );
    public abstract void setClip( final BitsRectF rect );
    
    public final float getLineSize( ) { return this.mLastLineSize; }
    public abstract void setLineSize( final float size );
    
    public final float[] getColor( ) { return this.mLastColor; }
    public abstract void setColor( final float[] color );
    public abstract void setColor( final int[] color );
    public abstract void setColor( final int red, final int green, final int blue );
    public abstract void setColor( final int red, final int green, final int blue, final int alpha );
    public abstract void setColor( final float red, final float green, final float blue );
    public abstract void setColor( final float red, final float green, final float blue, final float alpha );
    
    public final float getScale( ) { return this.mLastScale; }
    public abstract void setScale( final float scaleFactor );
    
    public final float[] getRotation( ) { return this.mLastRotation; }
    public abstract void setRotation( final float rotation );
    public abstract void setRotation( final float rotation, final float x, final float y );
    public abstract void setRotation( final float[] rotation );
    
    public final float[] getTranslation( ) { return this.mLastTranslate; }
    public abstract void setTranslation( final float x, final float y );
    public abstract void setTranslation( final float[] translation );
    
    public final BitsFont getFont( ) { return this.mLastFont; }
    public abstract void setFont( final BitsFont font );
    
    public abstract void drawImage( final BitsDrawable drawable, final float x, final float y );
    public abstract void drawImage( final BitsDrawable drawable, final float x, final float y, final float width, final float height );
    public abstract void drawImage( final BitsDrawable drawable, BitsRectF destRect );
    public abstract void drawImage( final BitsDrawable drawable, BitsRect destRect );
    public abstract void cropImage( final BitsDrawable drawable, final float x, final float y, final float width, final float height, final float cropX, final float cropY, final float cropWidth, final float cropHeight );
    public abstract void cropImage( final BitsDrawable drawable, final BitsRectF destRect, final BitsRectF cropRect );
    public abstract void cropImage( final BitsDrawable drawable, final BitsRect destRect, final BitsRect cropRect );
    
    public abstract void drawLine( final float x1, final float y1, final float x2, final float y2 );

    public abstract void drawRect( final float x, final float y, final float width, final float height );
    public abstract void drawRect( final float x, final float y, final float width, final float height, final boolean outlined );
    public abstract void fillRect( final float x, final float y, final float width, final float height );
    public abstract void drawRect( final BitsRectF destRect );
    public abstract void drawRect( final BitsRect destRect );
    public abstract void drawRect( final BitsRectF destRect, final boolean outlined );
    public abstract void drawRect( final BitsRect destRect, final boolean outlined );
    public abstract void fillRect( final BitsRectF destRect );
    public abstract void fillRect( final BitsRect destRect );

    public abstract void drawRoundRect( final float x, final float y, final float width, final float height, final float curveRadius, final int curvePoints );
    public abstract void fillRoundRect( final float x, final float y, final float width, final float height, final float curveRadius, final int curvePoints );
    public abstract void drawRoundRect( final float x, final float y, final float width, final float height, final float curveRadius, final int curvePoints, final boolean outlined);
    public abstract void drawRoundRect( final BitsRectF destRect, final float curveRadius, final int curvePoints );
    public abstract void drawRoundRect( final BitsRect destRect, final float curveRadius, final int curvePoints );
    public abstract void fillRoundRect( final BitsRectF destRect, final float curveRadius, final int curvePoints );
    public abstract void fillRoundRect( final BitsRect destRect, final float curveRadius, final int curvePoints );
    public abstract void drawRoundRect( final BitsRectF destRect, final float curveRadius, final int curvePoints, final boolean outlined);
    public abstract void drawRoundRect( final BitsRect destRect, final float curveRadius, final int curvePoints, final boolean outlined);

    public abstract void drawArc( final float x, final float y, final float radius, final float startAngle, final float arcAngle );
    public abstract void drawArc( final float x, final float y, final float radius, final float startAngle, final float arcAngle, final int pointCount, final boolean wantCenterPoint );
    public abstract void drawArc( final float x, final float y, final float radiusX, final float radiusY, final float startAngle, final float arcAngle );
    public abstract void drawArc( final float x, final float y, final float radiusX, final float radiusY, final float startAngle, final float arcAngle, final int pointCount, final boolean wantCenterPoint );
    public abstract void drawArc( final BitsRectF destRect, final float startAngle, final float arcAngle );
    public abstract void drawArc( final BitsRect destRect, final float startAngle, final float arcAngle );
    public abstract void drawArc( final BitsRectF destRect, final float startAngle, final float arcAngle, final int pointCount, final boolean wantCenterPoint );
    public abstract void drawArc( final BitsRect destRect, final float startAngle, final float arcAngle, final int pointCount, final boolean wantCenterPoint );
    public abstract void fillArc( final float x, final float y, final float radius, final float startAngle, final float arcAngle );
    public abstract void fillArc( final float x, final float y, final float radius, final float startAngle, final float arcAngle, final int pointCount );
    public abstract void fillArc( final float x, final float y, final float radiusX, final float radiusY, final float startAngle, final float arcAngle );
    public abstract void fillArc( final float x, final float y, final float radiusX, final float radiusY, final float startAngle, final float arcAngle, final int pointCount);
    public abstract void fillArc( final BitsRectF destRect, final float startAngle, final float arcAngle );
    public abstract void fillArc( final BitsRect destRect, final float startAngle, final float arcAngle );
    public abstract void fillArc( final BitsRectF destRect, final float startAngle, final float arcAngle, final int pointCount);
    public abstract void fillArc( final BitsRect destRect, final float startAngle, final float arcAngle, final int pointCount);

    public abstract void drawCircle( final float x, final float y, final float radius );
    public abstract void drawCircle( final float x, final float y, final float radius, final int pointCount);
    public abstract void fillCircle( final float x, final float y, final float radius );
    public abstract void fillCircle( final float x, final float y, final float radius, final int pointCount);
    
    public abstract void drawOval( final float x, final float y, final float radiusX, final float radiusY );
    public abstract void drawOval( final float x, final float y, final float radiusX, final float radiusY, final int pointCount);
    public abstract void fillOval( final float x, final float y, final float radiusX, final float radiusY );
    public abstract void fillOval( final float x, final float y, final float radiusX, final float radiusY, final int pointCount );
    public abstract void drawOval( final float x, final float y, final float radiusX, final float radiusY, final boolean filled, final boolean isArc, final float startAngle, final float arcAngle, final int pointCount, final boolean wantCenterPoint );
    
    public abstract void drawOval( final BitsRectF destRect );
    public abstract void drawOval( final BitsRect destRect );
    public abstract void drawOval( final BitsRectF destRect, final int pointCount);
    public abstract void drawOval( final BitsRect destRect, final int pointCount);
    public abstract void fillOval( final BitsRectF destRect );
    public abstract void fillOval( final BitsRect destRect );
    public abstract void fillOval( BitsRectF destRect, final int pointCount );
    public abstract void fillOval( BitsRect destRect, final int pointCount );
    public abstract void drawOval( BitsRectF destRect, final boolean filled, final boolean isArc, final float startAngle, final float arcAngle, final int pointCount, final boolean wantCenterPoint );
    public abstract void drawOval( BitsRect destRect, final boolean filled, final boolean isArc, final float startAngle, final float arcAngle, final int pointCount, final boolean wantCenterPoint );
    
    public abstract void drawTriangle( final BitsDrawable drawable, final float x1, final float y1, final float x2, final float y2, final float x3, final float y3 );
    public abstract void drawTriangle( final BitsDrawable drawable, final float x1, final float y1, final float x2, final float y2, final float x3, final float y3, final float texX1, final float texY1, final float texX2, final float texY2, final float texX3, final float texY3 );
    
    public abstract void drawText( final String text, float x, float y );
}
