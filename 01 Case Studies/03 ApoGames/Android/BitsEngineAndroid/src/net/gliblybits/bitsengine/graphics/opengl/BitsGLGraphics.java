
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

package net.gliblybits.bitsengine.graphics.opengl;

import android.opengl.GLES11;
import net.gliblybits.bitsengine.core.BitsApp;
import net.gliblybits.bitsengine.core.BitsGame;
import net.gliblybits.bitsengine.graphics.BitsDrawable;
import net.gliblybits.bitsengine.graphics.BitsFont;
import net.gliblybits.bitsengine.graphics.BitsGraphics;
import net.gliblybits.bitsengine.gui.widgets.BitsAnimationWidget;
import net.gliblybits.bitsengine.gui.widgets.BitsImageSheetWidget;
import net.gliblybits.bitsengine.render.commands.BitsRenderCommand;
import net.gliblybits.bitsengine.render.commands.BitsRenderCommandPool;
import net.gliblybits.bitsengine.render.commands.BitsRenderCommandQueues;
import net.gliblybits.bitsengine.render.state.BitsRenderState;
import net.gliblybits.bitsengine.utils.BitsLog;
import net.gliblybits.bitsengine.utils.BitsRect;
import net.gliblybits.bitsengine.utils.BitsRectF;

public final class 
BitsGLGraphics 
extends BitsGraphics {
	
	public static final int BLENDFUNC_ZERO 					= GLES11.GL_ZERO;
	public static final int BLENDFUNC_ONE 					= GLES11.GL_ONE;
	public static final int BLENDFUNC_SRC_COLOR 			= GLES11.GL_SRC_COLOR;
	public static final int BLENDFUNC_ONE_MINUS_SRC_COLOR 	= GLES11.GL_ONE_MINUS_SRC_COLOR;
	public static final int BLENDFUNC_DST_COLOR 			= GLES11.GL_DST_COLOR;
	public static final int BLENDFUNC_ONE_MINUS_DST_COLOR 	= GLES11.GL_ONE_MINUS_DST_COLOR;
	public static final int BLENDFUNC_SRC_ALPHA 			= GLES11.GL_SRC_ALPHA;
	public static final int BLENDFUNC_ONE_MINUS_SRC_ALPHA 	= GLES11.GL_ONE_MINUS_SRC_ALPHA;
	public static final int BLENDFUNC_DST_ALPHA 			= GLES11.GL_DST_ALPHA;
	public static final int BLENDFUNC_ONE_MINUS_DST_ALPHA 	= GLES11.GL_ONE_MINUS_DST_ALPHA;
	public static final int BLENDFUNC_SRC_ALPHA_SATURATE 	= GLES11.GL_SRC_ALPHA_SATURATE;

	/**
	 * Default: {0, 0, 0, 1.0}<br>
	 * <br>
	 * Use the static 'setClearColor' method to set the color easily.<br>
	 * <br>
	 * This array contains the float color values (red, green, blue, alpha) for the<br>
	 * OpenGL ES clearing method.<br>
	 * The screen will be filled with this color every frame.<br>
	 */
	public static final float[] sClearColor = { 0f, 0f, 0f, 1.0f };
	
	/**
	 * This method sets the values for 'mClearColor'.<br>
	 * <br>
	 * @param red
	 * @param green
	 * @param blue
	 * @param alpha
	 */
	public static final void 
	setClearColor( 
			final float red, 
			final float green, 
			final float blue, 
			final float alpha) {
		
		if( BitsGame.getInstance() == null ) {
			BitsLog.e("BitsGLGraphics - setClearColor", "The BitsGame instance is NULL!");
			throw new NullPointerException("The BitsGame instance is NULL!");
		}
		
		//check if these are new values
		if( sClearColor[0] == red && 
			sClearColor[1] == green && 
			sClearColor[2] == blue &&
			sClearColor[3] == alpha ) {
			
			return; //do nothing if the values won't change
		}
		
		//set the new clear color
		sClearColor[0] = red;
		sClearColor[1] = green;
		sClearColor[2] = blue;
		sClearColor[3] = alpha;
		
		//request to set the new clear color at a save game state
		BitsGame.getInstance().mRenderer.requestClearColor( );
	}

    private BitsRenderCommandQueues		mRenderCommandQueues 	= null;
    private BitsRenderCommandPool   	mRenderCommandPool		= null;

    private final int[] mLastBlendFunc = new int[2];
    
    public 
    BitsGLGraphics( 
    		final BitsRenderCommandQueues queueManager, 
    		final BitsRenderCommandPool renderablePool ) {
    	
    	if( queueManager == null || renderablePool == null ) {
    		BitsLog.e("BitsGLGraphics - constructor", "The given BitsRenderCommandQueues or BitsRenderCommandPool object is NULL!");
    		throw new NullPointerException("The given BitsRenderCommandQueues or BitsRenderCommandPool object is NULL!");
    	}
    	
    	this.reset();
    	
    	this.mRenderCommandQueues = queueManager;
    	this.mRenderCommandPool = renderablePool;
    	
    	this.mLastBlendFunc[0] = -1; //sFactor
    	this.mLastBlendFunc[1] = -1; //dFactor
    }
    
    /***************************************
     * OWN METHODS                         *
     ***************************************/    
    public final int[] 
    getBlendFunc( ) {
    	
    	return this.mLastBlendFunc;
    }
    
    public final void 
    setBlendFunc( 
    		final int sFactor,
    		final int dFactor ) {
    	
    	if( this.mLastBlendFunc[0] == sFactor || 
    		this.mLastBlendFunc[1] == dFactor ) {
    		return; 
    	}
    	
    	final BitsRenderCommand command = this.mRenderCommandPool.checkOut( );
	   	if( command == null ) {
	   		BitsLog.e("BitsGLGraphics - setBlendFunc", "The BitsRenderCommand is NULL!");
	   		throw new NullPointerException("The BitsRenderCommand is NULL!");
	   	}
	   	
	   	this.mLastBlendFunc[0] = sFactor;
	   	this.mLastBlendFunc[1] = dFactor;

	   	command.mType = BitsRenderCommand.TYPE_BLENDFUNC;
	   	command.mTexWidth = sFactor;
	   	command.mTexHeight = dFactor;

	   	this.mRenderCommandQueues.add( command );
    }
    
    public final void 
    drawRenderStack( 
    		final BitsGLRenderStack imageStack, 
    		final BitsGLImage image ) {
    	
    	if( imageStack == null ) {
    		BitsLog.e("BitsGLGraphics - drawRenderStack", "The given BitsGLRenderStack is NULL!");
    		return;
    	}
    	
    	if( image == null ) {
    		BitsLog.e("BitsGLGraphics - drawRenderStack", "The given BitsGLImage is NULL!");
    		return;    		
    	}
    	
   		if( imageStack.isLoaded != true ) {
   			BitsLog.w("BitsGLGraphics - drawRenderStack", "The given BitsGLRenderStack was not loaded!");
   			return;
   		}
	    
   		final BitsRenderCommand command = this.mRenderCommandPool.checkOut( );
	    if( command == null ) {
	    	BitsLog.e("BitsGLGraphics - drawRenderStack", "The BitsRenderCommand is NULL!");
	    	throw new NullPointerException("The BitsRenderCommand is NULL!");
	    }
	    
	    command.mType = BitsRenderCommand.TYPE_IMAGE_STACK;
	    command.mTexWidth = imageStack.mVertexBufferId;
	    command.mTexHeight = imageStack.mIndexBufferId;
	    command.mX = imageStack.mVerticeCount;
	    command.mTexId = image.mTextureId;

	    this.mRenderCommandQueues.add(command);
    }
    
    /**
     * Renders the current FPS rate at the point (5, 5).
     * @param font - BitsFont used to render the FPS rate
     */
    public final void 
    drawFPS( ) {
    	
    	this.drawFPS( 5, 5 );
    }
    
    /**
     * Renders the current FPS rate at the given point.
     * @param font - BitsFont used to render the FPS rate
     * @param x
     * @param y
     */
    public final void 
    drawFPS( 
    		final float x, 
    		final float y ) {
    	
    	this.drawText( String.valueOf( BitsGame.getInstance( ).getFps( ) ), x, y);    			
    }
    
    public final void 
    centerText( 
    		final String text, 
    		final float x, 
    		final float y ) {

    	if( this.mLastFont == null ) {
    		return;
    	}

    	if( this.mLastFont.isLoaded == false ) {
    		BitsLog.w("BitsGLGraphics - centerText", "The BitsGLFont was not loaded!");
    		return;
    	}

    	if( text == null ) {
    		BitsLog.e("BitsGLGraphics - centerText", "The given String is NULL!");
    		return;
    	} else {
   			final float len = ((BitsGLFont)this.mLastFont).getLength( text ); // Get Text Length
   			if( len == 0 ) {
   				BitsLog.e("BitsGLGraphics - centerText", "The length of the String would be zero!");
       			return;
   			} else {
   				this.drawText( text, x - ( len / 2.0f ), y - ( (((BitsGLFont)this.mLastFont).mMaxCharHeight) / 2.0f ) ); // Draw Text Centered
   			}
   		}
    }
    
    public final void
    drawAnimation(
    		final BitsAnimationWidget animation ) {
    	
    	if( animation == null || animation.mImages == null ) {
    		BitsLog.e("BitsGLGraphics - drawAnimation", "The given BitsAnimationWidget is NULL!");
    		return;
    	}
    	
		if( animation.mRegions == null ) {
			this.drawImage( animation.mImages[ animation.mIndex ], animation.mRect );
		} else {
			this.cropImage( animation.mImages[0], animation.mRect, animation.mRegions[ animation.mIndex ].mRect);
		}
    }
    
    public final void
    drawImageSheet(
    		final BitsImageSheetWidget imageSheet,
    		final int index,
    		final float x,
    		final float y ) {
    	
    	if( imageSheet == null || imageSheet.mRegions == null ) {
    		BitsLog.e("BitsGLGraphics - drawImageSheet", "The given BitsImageSheetWidget is NULL!");
    		return;
    	}
    		
    	this.drawImageSheet( imageSheet, index, x, y, imageSheet.mRegions[index].mRect.mWidth, imageSheet.mRegions[index].mRect.mHeight );
    }
    
    public final void
    drawImageSheet(
    		final BitsImageSheetWidget imageSheet,
    		final int index,
    		final float x,
    		final float y,
    		final float width,
    		final float height ) {
    	
    	if( imageSheet == null || imageSheet.mRegions == null ) {
    		BitsLog.e("BitsGLGraphics - drawImageSheet", "The given BitsImageSheetWidget is NULL!");
    		return;
    	}

    	this.cropImage(imageSheet.mImage, x, y, width, height, imageSheet.mRegions[index].mRect.mX, imageSheet.mRegions[index].mRect.mY, imageSheet.mRegions[index].mRect.mWidth, imageSheet.mRegions[index].mRect.mHeight );
    }

    /***************************************
     * OVERRIDE METHODS                    *
     ***************************************/
    @Override
    public final void
    restoreStates() {
    	
    	final BitsRenderState renderState = this.mStateStack.pop();
    	if( renderState == null ) {
    		BitsLog.e("BitsGLGraphics - restoreStates", "The BitsRenderState is NULL!");
    		throw new NullPointerException("The BitsRenderState is Null!");
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
    	
    	if( rect == null ) {
    		BitsLog.e("BitsGLGraphics - setClip", "The given BitsRect is NULL!");
    		throw new NullPointerException("The given BitsRect is NULL!");
    	}
    		
    	this.setClip(rect.mX, rect.mY, rect.mWidth, rect.mHeight);
    }
    
    @Override
    public final void 
    setClip( 
    		final BitsRectF rect ) {

    	if( rect == null ) {
    		BitsLog.e("BitsGLGraphics - setClip", "The given BitsRectF is NULL!");
    		throw new NullPointerException("The given BitsRectF is NULL!");
    	}
    	
    	this.setClip( rect.mX, rect.mY, rect.mWidth, rect.mHeight);
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
    		
        	this.mLastClip.mX 		= x;
        	this.mLastClip.mY 		= y;
        	this.mLastClip.mWidth 	= width;
        	this.mLastClip.mHeight 	= height;
        	
	    	final BitsRenderCommand command = this.mRenderCommandPool.checkOut( );
		    if( command == null ) {
		    	BitsLog.e("BitsGLGraphics - setClip", "The BitsRenderCommand is NULL!");
		    	throw new NullPointerException("The BitsRenderCommand is NULL!");
		    }

    		command.mType = BitsRenderCommand.TYPE_CLIP;
    		command.mX = (x + this.mLastTranslate[0]) * BitsApp.sScaleFactor;// + (BitsApp.sViewportPaddingWidth / 2);
    		command.mY = (y + this.mLastTranslate[1]) * BitsApp.sScaleFactor;// + (BitsApp.sViewportPaddingHeight / 2);
    		command.mWidth = width * BitsApp.sScaleFactor;
    		command.mHeight = height * BitsApp.sScaleFactor;
    		
    		this.mRenderCommandQueues.add( command );
    	}
    }
    
    /**
     * This method sets the size/thickness of all the lines that will be drawn afterwards.<br>
     * Remember: non-filled rectangles or circles consist of lines, too.<br>
     * 
     * @param size - the thickness/size of lines that will be drawn
     */
    @Override
    public final void 
    setLineSize( 
    		final float size ) {
    	
    	if( this.mLastLineSize == size ) {
    		return;
    	}
    	
    	if( size <= 0f ) {
    		BitsLog.e("BitsGLGraphics - setLineSize", "The line size must be > 0!");
    		throw new IllegalArgumentException("The line size must be > 0!");
    	}
    	
	    final BitsRenderCommand command = this.mRenderCommandPool.checkOut( );
	    if( command == null ) {
	    	BitsLog.e("BitsGLGraphics - setLineSize", "The BitsRenderCommand is NULL!");
	    	throw new NullPointerException("The BitsRenderCommand is NULL!");
	    }
	    
	    command.mType = BitsRenderCommand.TYPE_LINE_SIZE;
	    this.mLastLineSize = command.mX = size;
	    		
	    this.mRenderCommandQueues.add( command );
    }

    @Override
    public final void 
    setColor( 
    		final int[] color ) {
    	
    	if( color == null ) {
	    	BitsLog.e("BitsGLGraphics - setColor", "The given color array is NULL!");
	    	throw new NullPointerException("The given color array is NULL!");
    	}
    	
    	switch( color.length ) {
    		case 3:
    			this.setColor( color[0], color[1], color[2] );
    			break;
    		case 4:
    			this.setColor( color[0], color[1], color[2], color[3] );
    			break;
    		default:
    			BitsLog.e("BitsGLGraphics - setColor", "The size of the given color array is not 3 or 4!");
    			throw new IllegalArgumentException("The size of the given color array is not 3 or 4!");
    	}
    }
    
    @Override
    public final void 
    setColor( 
    		final float[] color ) {

    	if( color == null ) {
	    	BitsLog.e("BitsGLGraphics - setColor", "The given color array is NULL!");
	    	throw new NullPointerException("The given color array is NULL!");
    	}
    	
    	switch( color.length ) {
    		case 3:
    			this.setColor( color[0], color[1], color[2] );
    			break;
    		case 4:
    			this.setColor( color[0], color[1], color[2], color[3] );
    			break;
    		default:
    			BitsLog.e("BitsGLGraphics - setColor", "The size of the given color array is not 3 or 4!");
    			throw new IllegalArgumentException("The size of the given color array is not 3 or 4!");
    	}
    }
    
    /**
     * This method creates a render command to set a NEW rendering color in OpenGL ES.<br>
     * But it only creates a render command, if at least one color component is different to a previous call to setColor.<br>
     * It will not modify the previously set alpha value!<br>
     * 
     * @param red
     * @param green
     * @param blue
     */
    @Override
    public final void 
    setColor( 
    		final int red, 
    		final int green, 
    		final int blue ) {
    	
    	this.setColor((float)red/255f, (float)green/255f, (float)blue/255f, this.mLastColor[3]);
    }
    
    /**
     * This method creates a render command to set a NEW rendering color in OpenGL ES.<br>
     * But it only creates a render command, if at least one color component is different to a previous call to setColor.<br>
     * 
     * @param red
     * @param green
     * @param blue
     */
    @Override
    public final void 
    setColor( 
    		final int red, 
    		final int green, 
    		final int blue, 
    		final int alpha ) {
    	
    	this.setColor((float)red/255f, (float)green/255f, (float)blue/255f, (float)(alpha)/255f);
    }
    
    /**
     * This method creates a render command to set a NEW rendering color in OpenGL ES.<br>
     * But it only creates a render command, if at least one color component is different to a previous call to setColor.<br>
     * It will not modify the previously set alpha value!<br>
     * 
     * @param red
     * @param green
     * @param blue
     */
    @Override
    public final void 
    setColor( 
    		final float red, 
    		final float green, 
    		final float blue ) {
    	
    	this.setColor(red, green, blue, this.mLastColor[3]);
    }
    
    /**
     * This method creates a render command to set a NEW rendering color in OpenGL ES.<br>
     * But it only creates a render command, if at least one color component is different to a previous call to setColor.<br>
     * 
     * @param red
     * @param green
     * @param blue
     * @param alpha
     */
    @Override
    public final void 
    setColor( 
    		final float red, 
    		final float green, 
    		final float blue, 
    		final float alpha ) {
    	
    	if( this.mLastColor[0] == red && 
    		this.mLastColor[1] == green && 
    		this.mLastColor[2] == blue && 
    		this.mLastColor[3] == alpha ) {
    		return;
    	}
    	
	    final BitsRenderCommand command = this.mRenderCommandPool.checkOut( );
	    if( command == null ) {
	    	BitsLog.e("BitsGLGraphics - setColor", "The BitsRenderCommand is NULL!");
	    	throw new NullPointerException("The BitsRenderCommand is NULL!");
	    }

   		command.mType = BitsRenderCommand.TYPE_COLOR;
   		this.mLastColor[0] = command.mX = red;
   		this.mLastColor[1] = command.mY = green;
   		this.mLastColor[2] = command.mWidth = blue;
   		this.mLastColor[3] = command.mHeight = alpha;
	    		
   		this.mRenderCommandQueues.add( command );
    }
    
    @Override
    public final void 
    setScale( 
    		final float scaleFactor ) {
    	
    	if( this.mLastScale == scaleFactor ) {
    		return;
    	}
    	
    	if( scaleFactor <= 0f ) {
    		BitsLog.e("BitsGLGraphics - setScale", "The scale factor must be > 0!");
    		throw new IllegalArgumentException("The scale factor must be > 0!");
    	}

    	this.mLastScale = scaleFactor;
    }

    @Override
    public final void 
    setRotation( 
    		final float[] rotation ) {
    	
    	if( rotation == null ) {
	    	BitsLog.e("BitsGLGraphics - setRotation", "The given rotation array is NULL!");
	    	throw new NullPointerException("The given rotation array is NULL!");
    	}
    	
    	if( rotation.length != 3 ) {
    		BitsLog.e("BitsGLGraphics - setRotation", "The size of the given rotation array is not 3!");
    		throw new IllegalArgumentException("The size of the given rotation array is not 3!");
    	}

    	this.setRotation(rotation[0], rotation[1], rotation[2]);
    }
    
    /**
     * Rotates around the center of images/rects/lines.<br>
     * 
     * @param rotation
     */
    @Override
    public final void 
    setRotation( 
    		final float rotation ) {
    	
    	this.setRotation(rotation, -1, -1);
    }
    
    /**
     * Rotates around a given point, that is not bound to images/rects/lines.<br>
     * 
     * @param rotation
     * @param x
     * @param y
     */
    @Override
    public final void 
    setRotation(
    		float rotation, 
    		final float x, 
    		final float y ) {
    	
    	//keep the angle between 0 and 359 degree
    	rotation = rotation % 360;
    	
    	//transform negative angles into positive ones
    	if( rotation < 0 ) {
    		rotation = rotation + 360;
    	}
    	
    	if( this.mLastRotation[0] == rotation && 
    		this.mLastRotation[1] == x && 
    		this.mLastRotation[2] == y ) {
    		return;
    	}
    	
    	final BitsRenderCommand command = this.mRenderCommandPool.checkOut( );
	    if( command == null ) {
	    	BitsLog.e("BitsGLGraphics - setRotation", "The BitsRenderCommand is NULL!");
	    	throw new NullPointerException("The BitsRenderCommand is NULL!");
	    }

   		command.mType = BitsRenderCommand.TYPE_ROTATION;
   		command.mX = rotation;
   		command.mWidth = (x + this.mLastTranslate[0]);// * BitsApp.sScaleFactor;
   		command.mHeight = (y + this.mLastTranslate[1]);// * BitsApp.sScaleFactor;
   		this.mLastRotation[0] = rotation;
   		this.mLastRotation[1] = x;
   		this.mLastRotation[2] = y;
	    		
   		this.mRenderCommandQueues.add( command );
    }
    
    public final void 
    setTranslation( 
    		final float x, 
    		final float y ) {
    	
    	if( this.mLastTranslate[0] == x &&
    		this.mLastTranslate[1] == y ) {
    		return;
    	}
    		
    	this.mLastTranslate[0] = x;
    	this.mLastTranslate[1] = y;
    }
    
    public final void 
    setTranslation( 
    		final float[] translation ) {
    	
    	if( translation == null ) {
	    	BitsLog.e("BitsGLGraphics - setTranslation", "The given translation array is NULL!");
	    	throw new NullPointerException("The given translation array is NULL!");
    	}
    	
    	if( translation.length != 2 ) {
    		BitsLog.e("BitsGLGraphics - setTranslation", "The size of the given translation array is not 2!");
    		throw new IllegalArgumentException("The size of the given translation array is not 2!");
    	}

    	this.setTranslation(translation[0], translation[1]);
    }
    
    /**
     * This method will create a render command that will order the render thread to render an image.<br>
     * The image will be rendered at the given point (x, y) and with the full image size.
     * 
     * @param image
     * @param x
     * @param y
     */
    public final void 
    drawImage( 
    		final BitsDrawable drawable, 
    		final float x,
    		final float y ) {
    	
    	if( drawable == null ) {
	    	BitsLog.e("BitsGLGraphics - drawImage", "The given BitsDrawable is NULL!");
	    	throw new NullPointerException("The given BitsDrawable is NULL!");
    	}
    	
    	if( drawable.mType != BitsDrawable.DRAWABLE_TYPE_GL_IMAGE ) {
    		BitsLog.e("BitsGLGraphics - drawImage", "The given BitsDrawable is not a BitsGLImage!");
    		throw new IllegalArgumentException("The given BitsDrawable is not a BitsGLImage!");
    	}
    	
    	if( drawable.isLoaded == false ) {
   			BitsLog.w("BitsGLGraphics - drawImage", "The given BitsDrawable was not loaded!");
   			return;
    	}
    	
		final BitsRenderCommand command = this.mRenderCommandPool.checkOut( );
	    if( command == null ) {
	    	BitsLog.e("BitsGLGraphics - drawImage", "The BitsRenderCommand is NULL!");
	    	throw new NullPointerException("The BitsRenderCommand is NULL!");
	    }
	    
		command.mType = BitsRenderCommand.TYPE_IMAGE;
		command.mTexId = ((BitsGLImage)drawable).mTextureId;
		command.mTexWidth = ((BitsGLImage)drawable).mWidth;
		command.mTexHeight = ((BitsGLImage)drawable).mHeight;
		command.mX = (x + this.mLastTranslate[0]);// * BitsApp.sScaleFactor;
		command.mY = (y + this.mLastTranslate[1]);// * BitsApp.sScaleFactor;
		command.mWidth = ((BitsGLImage)drawable).mWidth * this.mLastScale;//* BitsApp.sScaleFactor
		command.mHeight = ((BitsGLImage)drawable).mHeight * this.mLastScale;//* BitsApp.sScaleFactor
		command.mRenderMode = ((BitsGLImage)drawable).mRenderMode;

		this.mRenderCommandQueues.add( command );
    }

    /**
     * This method will create a render command that will order the render thread to render an image.<br>
     * The image will be rendered at the given point (x, y) and with the given size (width, height).
     * 
     * @param image
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public final void 
    drawImage( 
    		final BitsDrawable drawable, 
    		final float x, 
    		final float y, 
    		final float width,
    		final float height ) {
    	
    	if( drawable == null ) {
	    	BitsLog.e("BitsGLGraphics - drawImage", "The given BitsDrawable is NULL!");
	    	throw new NullPointerException("The given BitsDrawable is NULL!");
    	}
    	
    	if( drawable.mType != BitsDrawable.DRAWABLE_TYPE_GL_IMAGE ) {
    		BitsLog.e("BitsGLGraphics - drawImage", "The given BitsDrawable is not a BitsGLImage!");
    		throw new IllegalArgumentException("The given BitsDrawable is not a BitsGLImage!");
    	}
    	
    	if( drawable.isLoaded == false ) {
   			BitsLog.w("BitsGLGraphics - drawImage", "The given BitsDrawable was not loaded!");
   			return;
    	}
    	
		final BitsRenderCommand command = this.mRenderCommandPool.checkOut( );
	    if( command == null ) {
	    	BitsLog.e("BitsGLGraphics - drawImage", "The BitsRenderCommand is NULL!");
	    	throw new NullPointerException("The BitsRenderCommand is NULL!");
	    }
	    
		command.mType = BitsRenderCommand.TYPE_IMAGE;
		command.mTexId = ((BitsGLImage)drawable).mTextureId;
		command.mTexWidth = ((BitsGLImage)drawable).mWidth;
		command.mTexHeight = ((BitsGLImage)drawable).mHeight;
		command.mX = (x + this.mLastTranslate[0]);// * BitsApp.sScaleFactor;
		command.mY = (y + this.mLastTranslate[1]);// * BitsApp.sScaleFactor;
		command.mWidth = width * this.mLastScale;//* BitsApp.sScaleFactor
		command.mHeight = height * this.mLastScale;//* BitsApp.sScaleFactor
		command.mRenderMode = ((BitsGLImage)drawable).mRenderMode;

		this.mRenderCommandQueues.add( command );
    }
    
    public final void 
    drawImage( 
    		final BitsDrawable drawable, 
    		final BitsRectF destRect ) {
    	
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - drawImage", "The given BitsRectF is NULL!");
	    	throw new NullPointerException("The given BitsRectF is NULL!");
    	}
    		
    	this.drawImage( drawable, destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight );
    }
    
    public final void 
    drawImage( 
    		final BitsDrawable drawable,
    		final BitsRect destRect ) {
    	
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - drawImage", "The given BitsRect is NULL!");
	    	throw new NullPointerException("The given BitsRect is NULL!");
    	}
    	
    	this.drawImage( drawable, destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight );
    }
    
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
    	
    	if( drawable == null ) {
	    	BitsLog.e("BitsGLGraphics - cropImage", "The given BitsDrawable is NULL!");
	    	throw new NullPointerException("The given BitsDrawable is NULL!");
    	}
    	
    	if( drawable.mType != BitsDrawable.DRAWABLE_TYPE_GL_IMAGE ) {
    		BitsLog.e("BitsGLGraphics - cropImage", "The given BitsDrawable is not a BitsGLImage!");
    		throw new IllegalArgumentException("The given BitsDrawable is not a BitsGLImage!");
    	}
    	
    	if( drawable.isLoaded == false ) {
   			BitsLog.w("BitsGLGraphics - cropImage", "The given BitsDrawable was not loaded!");
   			return;
    	}
    	
		final BitsRenderCommand command = this.mRenderCommandPool.checkOut( );
	    if( command == null ) {
	    	BitsLog.e("BitsGLGraphics - cropImage", "The BitsRenderCommand is NULL!");
	    	throw new NullPointerException("The BitsRenderCommand is NULL!");
	    }

  		command.mType = BitsRenderCommand.TYPE_IMAGE;
   		command.mTexId = ((BitsGLImage)drawable).mTextureId;
   		command.mTexWidth = ((BitsGLImage)drawable).mWidth;
   		command.mTexHeight = ((BitsGLImage)drawable).mHeight;
   		command.mX = (x + this.mLastTranslate[0]);// * BitsApp.sScaleFactor;
   		command.mY = (y + this.mLastTranslate[1]);// * BitsApp.sScaleFactor;
 		command.mWidth = width * this.mLastScale;//* BitsApp.sScaleFactor
  		command.mHeight = height * this.mLastScale;//* BitsApp.sScaleFactor
   		command.mCropX = cropX;
   		command.mCropY = cropY;
   		command.mCropWidth = cropWidth;
   		command.mCropHeight = cropHeight;		       		
 		command.mRenderMode = ((BitsGLImage)drawable).mRenderMode;

   		this.mRenderCommandQueues.add( command );
    }
    
    public final void 
    cropImage( 
    		final BitsDrawable drawable, 
    		final BitsRectF destRect,
    		final BitsRectF cropRect ) {
    	
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - cropImage", "The given destination BitsRectF is NULL!");
	    	throw new NullPointerException("The given BitsRectF is NULL!");
    	}
    	
    	if( cropRect == null ) {
	    	BitsLog.e("BitsGLGraphics - cropImage", "The given source BitsRectF is NULL!");
	    	throw new NullPointerException("The given BitsRectF is NULL!");
    	}
 
   		this.cropImage(drawable, destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, cropRect.mX, cropRect.mY, cropRect.mWidth, cropRect.mHeight);
    }
    
    public final void 
    cropImage( 
    		final BitsDrawable drawable, 
    		final BitsRect destRect,
    		final BitsRect cropRect ) {
    	
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - cropImage", "The given destination BitsRect is NULL!");
	    	throw new NullPointerException("The given BitsRect is NULL!");
    	}
    	
    	if( cropRect == null ) {
	    	BitsLog.e("BitsGLGraphics - cropImage", "The given source BitsRect is NULL!");
	    	throw new NullPointerException("The given BitsRect is NULL!");
    	}
    		
    	this.cropImage(drawable, destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, cropRect.mX, cropRect.mY, cropRect.mWidth, cropRect.mHeight);
    }
    
    /**
     * This method will create a render command that will order the render thread to render a line<br>
     * from the start point (x1, y1) to the end point (x2, y2).<br>
     * 
     * @param x1 - x-coord. of the start point 
     * @param y1 - y-coord. of the start point
     * @param x2 - x-coord. of the destination point
     * @param y2 - y-coord. of the destination point
     */
    @Override
    public final void 
    drawLine( 
    		final float x1,
    		final float y1,
    		final float x2,
    		final float y2 ) {
    	
    	final BitsRenderCommand command = this.mRenderCommandPool.checkOut( );
	    if( command == null ) {
	    	BitsLog.e("BitsGLGraphics - drawLine", "The BitsRenderCommand is NULL!");
	    	throw new NullPointerException("The BitsRenderCommand is NULL!");
	    }
    		
	    command.mType = BitsRenderCommand.TYPE_LINE;
    	command.mX = (x1 + this.mLastTranslate[0]);// * BitsApp.sScaleFactor;
    	command.mY = (y1 + this.mLastTranslate[1]);// * BitsApp.sScaleFactor;
    	command.mWidth = (x2 + this.mLastTranslate[0]) * this.mLastScale;//* BitsApp.sScaleFactor 
    	command.mHeight = (y2 + this.mLastTranslate[1]) * this.mLastScale;//* BitsApp.sScaleFactor 
    		
    	this.mRenderCommandQueues.add( command );
    }
    
    /**
     * This method will create a render command that will order the render thread to render a rectangle.<br>
     * The rectangle will be rendered at the given point (x, y) and with the given size (width, height).<br>
     * It will be rendered as a non-filled rectangles via lines.<br>
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     */
    @Override
    public final void 
    drawRect( 
    		final float x,
    		final float y,
    		final float width,
    		final float height ) {
    	
    	this.drawRect( x, y, width, height, true );
    }
    
    @Override
    public final void 
    drawRect(
    		final BitsRectF destRect ) {
    	
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - drawRect", "The given destination BitsRectF is NULL!");
	    	throw new NullPointerException("The given destination BitsRectF is NULL!");
    	}

   		this.drawRect( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, true );
    }
    
    @Override
    public final void 
    drawRect( 
    		final BitsRect destRect ) {
    	
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - drawRect", "The given destination BitsRect is NULL!");
	    	throw new NullPointerException("The given destination BitsRect is NULL!");
    	}
    	
    	this.drawRect( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, true );
    }
    
    /**
     * This method will create a render command that will order the render thread to render a rectangle.<br>
     * The rectangle will be rendered at the given point (x, y) and with the given size (width, height).<br>
     * It will be rendered as a filled rectangles.<br>
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     */
    @Override
    public final void 
    fillRect( 
    		final float x,
    		final float y, 
    		final float width, 
    		final float height ) {
    	
    	this.drawRect( x, y, width, height, false );
    }
    
    @Override
    public final void 
    fillRect( 
    		final BitsRectF destRect ) {
    	
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - fillRect", "The given destination BitsRectF is NULL!");
	    	throw new NullPointerException("The given destination BitsRectF is NULL!");
    	}
    		
    	this.drawRect( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, false );
    }
    
    @Override
    public final void 
    fillRect( 
    		final BitsRect destRect ) {
    	
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - fillRect", "The given destination BitsRect is NULL!");
	    	throw new NullPointerException("The given destination BitsRect is NULL!");
    	}
    	
    	this.drawRect( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, false );
    }
    
    @Override
    public final void 
    drawRect( 
    		final float x,
    		final float y,
    		final float width, 
    		final float height, 
    		final boolean outlined ) {
    	
    	final BitsRenderCommand command = this.mRenderCommandPool.checkOut( );
	    if( command == null ) {
	    	BitsLog.e("BitsGLGraphics - drawRect", "The BitsRenderCommand is NULL!");
	    	throw new NullPointerException("The BitsRenderCommand is NULL!");
	    }
	    
    	if( outlined ) {
    		command.mType = BitsRenderCommand.TYPE_RECT_OUTLINED;
    	} else {
    		command.mType = BitsRenderCommand.TYPE_RECT;
    	}
    	command.mX = (x + this.mLastTranslate[0]);// * BitsApp.sScaleFactor;
    	command.mY = (y + this.mLastTranslate[1]);// * BitsApp.sScaleFactor;
    	command.mWidth = width * this.mLastScale;//* BitsApp.sScaleFactor 
    	command.mHeight = height * this.mLastScale;//* BitsApp.sScaleFactor 
    		
    	this.mRenderCommandQueues.add( command );    	
    }
    
    @Override
    public final void 
    drawRect( 
    		final BitsRectF destRect,
    		final boolean outlined ) {
    	
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - drawRect", "The given destination BitsRectF is NULL!");
	    	throw new NullPointerException("The given destination BitsRectF is NULL!");
    	}
    	
    	this.drawRect( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, outlined );
    }

    @Override
    public final void 
    drawRect( 
    		final BitsRect destRect, 
    		final boolean outlined ) {
    	
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - drawRect", "The given destination BitsRect is NULL!");
	    	throw new NullPointerException("The given destination BitsRect is NULL!");
    	}
    	
    	this.drawRect( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, outlined );
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
    	
    	this.drawRoundRect( x, y, width, height, curveRadius, curvePoints, true );
    }
    
    @Override
    public final void 
    drawRoundRect( 
    		final BitsRectF destRect,
    		final float curveRadius,
    		final int curvePoints ) {
    	
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - drawRoundRect", "The given destination BitsRectF is NULL!");
	    	throw new NullPointerException("The given destination BitsRectF is NULL!");
    	}
    	
    	this.drawRoundRect( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, curveRadius, curvePoints );
    }
    
    @Override
    public final void 
    drawRoundRect( 
    		final BitsRect destRect,
    		final float curveRadius,
    		final int curvePoints ) {
    	
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - drawRoundRect", "The given destination BitsRect is NULL!");
	    	throw new NullPointerException("The given destination BitsRect is NULL!");
    	}
    	
    	this.drawRoundRect( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, curveRadius, curvePoints );
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
    	//max. curveRadius == (min(width, height) / 2)
    	final float maxRadius = Math.min(width, height) / 2f;
    	if( curveRadius > maxRadius ) {
    		this.drawRoundRect( x, y, width, height, maxRadius, curvePoints, false );
    	} else {
    		this.drawRoundRect( x, y, width, height, curveRadius, curvePoints, false );
    	}
    }
    
    @Override
    public final void 
    fillRoundRect(
    		final BitsRectF destRect,
    		final float curveRadius,
    		final int curvePoints ) {
    	
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - fillRoundRect", "The given destination BitsRectF is NULL!");
	    	throw new NullPointerException("The given destination BitsRectF is NULL!");
    	}
    	
    	this.fillRoundRect( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, curveRadius, curvePoints );
    }
    
    @Override
    public final void 
    fillRoundRect(
    		final BitsRect destRect,
    		final float curveRadius,
    		final int curvePoints ) {
    	
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - fillRoundRect", "The given destination BitsRect is NULL!");
	    	throw new NullPointerException("The given destination BitsRect is NULL!");
    	}
    	
    	this.fillRoundRect( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, curveRadius, curvePoints );
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
    	
    	final BitsRenderCommand command = this.mRenderCommandPool.checkOut( );
	    if( command == null ) {
	    	BitsLog.e("BitsGLGraphics - drawRoundRect", "The BitsRenderCommand is NULL!");
	    	throw new NullPointerException("The BitsRenderCommand is NULL!");
	    }
   			
	    command.mType = BitsRenderCommand.TYPE_ROUND_RECT;
    	command.mX = (x + this.mLastTranslate[0]);// * BitsApp.sScaleFactor;
    	command.mY = (y + this.mLastTranslate[1]);// * BitsApp.sScaleFactor;
    	command.mWidth = width * this.mLastScale;//* BitsApp.sScaleFactor 
    	command.mHeight = height * this.mLastScale;//* BitsApp.sScaleFactor 
    	command.mCropWidth = curveRadius * BitsApp.sScaleFactor * this.mLastScale;//* BitsApp.sScaleFactor 
    	command.mCropHeight = curvePoints;
    	if( outlined == true ) {
    		command.mTexWidth = 0;
    	} else {
    		command.mTexWidth = 1;
    	}
    		
    	this.mRenderCommandQueues.add( command );    	
    }
    
    @Override
    public final void 
    drawRoundRect( 
    		final BitsRectF destRect,
    		final float curveRadius,
    		final int curvePoints,
    		final boolean outlined) {
    	
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - drawRoundRect", "The given destination BitsRectF is NULL!");
	    	throw new NullPointerException("The given destination BitsRectF is NULL!");
    	}
    	
    	this.drawRoundRect( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, curveRadius, curvePoints, outlined );
    }
    

	@Override
	public final void 
	drawRoundRect(
			final BitsRect destRect, 
			final float curveRadius,
			final int curvePoints, 
			final boolean outlined) {

    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - drawRoundRect", "The given destination BitsRect is NULL!");
	    	throw new NullPointerException("The given destination BitsRect is NULL!");
    	}
		
    	this.drawRoundRect(destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, curveRadius, curvePoints, outlined);
	}

    @Override
    public final void 
    drawArc( 
    		final float x, 
    		final float y, 
    		final float radius, 
    		final float startAngle, 
    		final float arcAngle ) {
    	
    	this.drawOval(x, y, radius, radius, false, true, startAngle, arcAngle, BitsApp.sMaxCirclePoints, false );
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
    	
    	this.drawOval(x, y, radius, radius, false, true, startAngle, arcAngle, pointCount, wantCenterPoint );
    }

    @Override
    public final void 
    drawArc( 
    		final float x, 
    		final float y, 
    		final float radiusX, 
    		final float radiusY, 
    		final float startAngle, 
    		final float arcAngle ) {
    	
    	this.drawOval(x, y, radiusX, radiusY, false, true, startAngle, arcAngle, BitsApp.sMaxCirclePoints, false );
    }
    
    @Override
    public final void 
    drawArc( 
    		final BitsRectF destRect, 
    		final float startAngle, 
    		final float arcAngle ) {
    	
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - drawArc", "The given destination BitsRectF is NULL!");
	    	throw new NullPointerException("The given destination BitsRectF is NULL!");
    	}
    	
    	this.drawOval( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, false, true, startAngle, arcAngle, BitsApp.sMaxCirclePoints, false );
    }
    
    @Override
    public final void 
    drawArc( 
    		final BitsRect destRect, 
    		final float startAngle, 
    		final float arcAngle ) {
    	
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - drawArc", "The given destination BitsRect is NULL!");
	    	throw new NullPointerException("The given destination BitsRect is NULL!");
    	}
    		
    	this.drawOval( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, false, true, startAngle, arcAngle, BitsApp.sMaxCirclePoints, false );
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
    		final boolean wantCenterPoint) {
    	
    	this.drawOval( x, y, radiusX, radiusY, false, true, startAngle, arcAngle, pointCount, wantCenterPoint );
    }
    
    @Override
    public final void 
    drawArc( 
    		final BitsRectF destRect, 
    		final float startAngle, 
    		final float arcAngle,
    		final int pointCount,
    		final boolean wantCenterPoint) {
    	
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - drawArc", "The given destination BitsRectF is NULL!");
	    	throw new NullPointerException("The given destination BitsRectF is NULL!");
    	}
    	
    	this.drawOval( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, false, true, startAngle, arcAngle, pointCount, wantCenterPoint );
    }
    
    @Override
    public final void 
    drawArc( 
    		final BitsRect destRect, 
    		final float startAngle, 
    		final float arcAngle,
    		final int pointCount,
    		final boolean wantCenterPoint) {
    	
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - drawArc", "The given destination BitsRect is NULL!");
	    	throw new NullPointerException("The given destination BitsRect is NULL!");
    	}
    	
    	this.drawOval( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, false, true, startAngle, arcAngle, pointCount, wantCenterPoint );
    }
    
    @Override
    public final void 
    fillArc( 
    		final float x, 
    		final float y, 
    		final float radius, 
    		final float startAngle, 
    		final float arcAngle ) {
    	
    	this.drawOval(x, y, radius, radius, true, true, startAngle, arcAngle, BitsApp.sMaxCirclePoints, true );
    }

    @Override
    public final void 
    fillArc( 
    		final float x, 
    		final float y, 
    		final float radius, 
    		final float startAngle, 
    		final float arcAngle,
    		final int pointCount ) {
    	
    	this.drawOval(x, y, radius, radius, true, true, startAngle, arcAngle, pointCount, true );
    }
    
    @Override
    public final void 
    fillArc( 
    		final float x, 
    		final float y, 
    		final float radiusX, 
    		final float radiusY, 
    		final float startAngle, 
    		final float arcAngle ) {
    	
    	this.drawOval(x, y, radiusX, radiusY, true, true, startAngle, arcAngle, BitsApp.sMaxCirclePoints, true );
    }
    
    @Override
    public final void 
    fillArc( 
    		final BitsRectF destRect, 
    		final float startAngle, 
    		final float arcAngle ) {
    	
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - fillArc", "The given destination BitsRectF is NULL!");
	    	throw new NullPointerException("The given destination BitsRectF is NULL!");
    	}
    	
    	this.drawOval( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, true, true, startAngle, arcAngle, BitsApp.sMaxCirclePoints, true );
    }
    
    @Override
    public final void 
    fillArc( 
    		final BitsRect destRect, 
    		final float startAngle, 
    		final float arcAngle ) {
    	
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - fillArc", "The given destination BitsRect is NULL!");
	    	throw new NullPointerException("The given destination BitsRect is NULL!");
    	}
    	
    	this.drawOval( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, true, true, startAngle, arcAngle, BitsApp.sMaxCirclePoints, true );
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
    	
    	this.drawOval(x, y, radiusX, radiusY, true, true, startAngle, arcAngle, pointCount, true );
    }
    
    @Override
    public final void 
    fillArc( 
    		final BitsRectF destRect, 
    		final float startAngle, 
    		final float arcAngle,
    		final int pointCount) {
    	
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - fillArc", "The given destination BitsRectF is NULL!");
	    	throw new NullPointerException("The given destination BitsRectF is NULL!");
    	}
    	
    	this.drawOval( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, true, true, startAngle, arcAngle, pointCount, true );
    }
    
    @Override
    public final void 
    fillArc( 
    		final BitsRect destRect, 
    		final float startAngle, 
    		final float arcAngle,
    		final int pointCount) {
    	
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - fillArc", "The given destination BitsRect is NULL!");
	    	throw new NullPointerException("The given destination BitsRect is NULL!");
    	}
    	
    	this.drawOval( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, true, true, startAngle, arcAngle, pointCount, true );
    }
    
    @Override
    public final void 
    drawCircle( 
    		final float x,
    		final float y, 
    		final float radius ) {
    	
    	this.drawOval( x, y, radius, radius, false, false, -1, -1, BitsApp.sMaxCirclePoints, false );
    }

    @Override
    public final void 
    drawCircle( 
    		final float x,
    		final float y, 
    		final float radius,
    		final int pointCount) {
    	
    	this.drawOval( x, y, radius, radius, false, false, -1, -1, pointCount, false );
    }
    
    @Override
    public final void 
    fillCircle( 
    		final float x, 
    		final float y, 
    		final float radius ) {
    	
    	this.drawOval( x, y, radius, radius, true, false, -1, -1, BitsApp.sMaxCirclePoints, false );
    }

    @Override
    public final void 
    fillCircle( 
    		final float x, 
    		final float y, 
    		final float radius,
    		final int pointCount) {
    	
    	this.drawOval( x, y, radius, radius, true, false, -1, -1, pointCount, true );
    }
    
    @Override
    public final void 
    drawOval( 
    		final float x, 
    		final float y, 
    		final float radiusX, 
    		final float radiusY ) {
    	
    	this.drawOval( x, y, radiusX, radiusY, false, false, -1, -1, BitsApp.sMaxCirclePoints, false );
    }
    
    @Override
    public final void 
    drawOval( 
    		final BitsRectF destRect ) {
    	
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - drawOval", "The given destination BitsRectF is NULL!");
	    	throw new NullPointerException("The given destination BitsRectF is NULL!");
    	}
    	
    	this.drawOval( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, false, false, -1, -1, BitsApp.sMaxCirclePoints, false );
    }
    
    @Override
    public final void 
    drawOval( 
    		final BitsRect destRect ) {
    	
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - drawOval", "The given destination BitsRect is NULL!");
	    	throw new NullPointerException("The given destination BitsRect is NULL!");
    	}
    	
    	this.drawOval( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, false, false, -1, -1, BitsApp.sMaxCirclePoints, false );
    }

    @Override
    public final void 
    drawOval( 
    		final float x, 
    		final float y, 
    		final float radiusX, 
    		final float radiusY,
    		final int pointCount) {
    	
    	this.drawOval( x, y, radiusX, radiusY, false, false, -1, -1, pointCount, false );
    }
    
    @Override
    public final void 
    drawOval( 
    		final BitsRectF destRect,
    		final int pointCount) {
    	
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - drawOval", "The given destination BitsRectF is NULL!");
	    	throw new NullPointerException("The given destination BitsRectF is NULL!");
    	}
    	
    	this.drawOval( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, false, false, -1, -1, pointCount, false );
    }

    @Override
    public final void 
    drawOval( 
    		final BitsRect destRect,
    		final int pointCount) {
    	
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - drawOval", "The given destination BitsRect is NULL!");
	    	throw new NullPointerException("The given destination BitsRect is NULL!");
    	}
    	
    	this.drawOval( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, false, false, -1, -1, pointCount, false );
    }

    @Override
    public final void 
    fillOval( 
    		final float x, 
    		final float y, 
    		final float radiusX, 
    		final float radiusY ) {
    	
    	this.drawOval( x, y, radiusX, radiusY, true, false, -1, -1, BitsApp.sMaxCirclePoints, true );
    }
    
    @Override
    public final void
    fillOval( 
    		final BitsRectF destRect ) {
    	
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - fillOval", "The given destination BitsRectF is NULL!");
	    	throw new NullPointerException("The given destination BitsRectF is NULL!");
    	}
    	
    	this.drawOval( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, true, false, -1, -1, BitsApp.sMaxCirclePoints, true );
    }
    
    @Override
    public final void
    fillOval( 
    		final BitsRect destRect ) {
    	
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - fillOval", "The given destination BitsRect is NULL!");
	    	throw new NullPointerException("The given destination BitsRect is NULL!");
    	}
    		
    	this.drawOval( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, true, false, -1, -1, BitsApp.sMaxCirclePoints, true );
    }
    
    @Override
    public final void 
    fillOval( 
    		final float x, 
    		final float y, 
    		final float radiusX, 
    		final float radiusY,
    		final int pointCount ) {
    	
    	this.drawOval( x, y, radiusX, radiusY, true, false, -1, -1, pointCount, true );
    }
    
    @Override
    public final void
    fillOval( 
    		final BitsRectF destRect,
    		final int pointCount ) {
    	
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - fillOval", "The given destination BitsRectF is NULL!");
	    	throw new NullPointerException("The given destination BitsRectF is NULL!");
    	}
    	
    	this.drawOval( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, true, false, -1, -1, pointCount, true );
    }
    
    @Override
    public final void 
    fillOval( 
    		final BitsRect destRect,
    		final int pointCount ) {
    	
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - fillOval", "The given destination BitsRect is NULL!");
	    	throw new NullPointerException("The given destination BitsRect is NULL!");
    	}
    
    	this.drawOval( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, true, false, -1, -1, pointCount, true );
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
    	
    	final BitsRenderCommand command = this.mRenderCommandPool.checkOut( );
	    if( command == null ) {
	    	BitsLog.e("BitsGLGraphics - drawOval", "The BitsRenderCommand is NULL!");
	    	throw new NullPointerException("The BitsRenderCommand is NULL!");
	    }

  		command.mType = BitsRenderCommand.TYPE_CIRCLE;
   		command.mX = (x + this.mLastTranslate[0]);// * BitsApp.sScaleFactor;
   		command.mY = (y + this.mLastTranslate[1]);// * BitsApp.sScaleFactor;
   		command.mWidth = radiusX * this.mLastScale;//* BitsApp.sScaleFactor 
   		command.mHeight = radiusY * this.mLastScale;//* BitsApp.sScaleFactor 
   		if( filled == false ) {
   			command.mTexWidth = 0;
   		} else {
   			command.mTexWidth = 1;
   		}
    		
   		if( isArc == true ) {
   			command.mCropX = startAngle; //only use this for isArc -> arcAngle could be negative
   			command.mCropY = arcAngle;
   		} else {
   			command.mCropX = -1; //only use this for isArc -> arcAngle could be negative
   			command.mCropY = -1;
   		}
    		
   		if( pointCount > BitsApp.sMaxCirclePoints || pointCount < 4 ) { 
   			command.mCropWidth = BitsApp.sMaxCirclePoints; 
   		} else {
   			command.mCropWidth = pointCount;
   		}
    		
   		if( wantCenterPoint == true ) {
   			command.mCropHeight = 0;
   		} else {
   			command.mCropHeight = 1;
   		}
    		
   		this.mRenderCommandQueues.add(command);
    }
    
    @Override
    public final void 
    drawOval( 
    		final BitsRectF destRect, 
    		final boolean filled, 
    		final boolean isArc,
    		final float startAngle, 
    		final float arcAngle,
    		final int pointCount,
    		final boolean wantCenterPoint ) {
    
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - drawOval", "The given destination BitsRectF is NULL!");
	    	throw new NullPointerException("The given destination BitsRectF is NULL!");
    	}
    	
    	this.drawOval( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, filled, isArc, startAngle, arcAngle, pointCount, wantCenterPoint );
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
    
    	if( destRect == null ) {
	    	BitsLog.e("BitsGLGraphics - drawOval", "The given destination BitsRect is NULL!");
	    	throw new NullPointerException("The given destination BitsRect is NULL!");
    	}
    	
    	this.drawOval( destRect.mX, destRect.mY, destRect.mWidth, destRect.mHeight, filled, isArc, startAngle, arcAngle, pointCount, wantCenterPoint );
    }
    
    @Override
    public final void 
    drawText( 
    		final String text, 
    		float x, 
    		float y ) {
    	
    	if( this.mLastFont == null ) {
    		return;
    	}

    	if( this.mLastFont.isLoaded != true ) {
    		BitsLog.w("BitsGLGraphics - drawText", "The BitsGLFont was not loaded!");
    		return;
    	}
    	
    	if( text == null ) {
    		BitsLog.e("BitsGLGraphics - drawText", "The given String is NULL!");
    		return;
    	} else {
	        final float chrHeight = ((BitsGLFont)this.mLastFont).mCharCellHeight;// * BitsApp.sScaleFactor; // Calculate Scaled Character Height
	        final float chrWidth = ((BitsGLFont)this.mLastFont).mCharCellWidth;// * BitsApp.sScaleFactor; // Calculate Scaled Character Width
	        final int len = text.length(); // Get String Length
    		        
	        for ( int i = 0; i < len; i++ ) { // FOR Each Character in String
	        	int c = (int)text.charAt( i ) - BitsGLFont.CHAR_START; // Calculate Character Index (Offset by First Char in Font)
	           	if ( c < 0 || c >= BitsGLFont.CHAR_CNT ) // IF Character Not In Font
	           		c = BitsGLFont.CHAR_UNKNOWN; // Set to Unknown Character Index
		           
		       	final BitsRenderCommand command = this.mRenderCommandPool.checkOut( );
			    if( command == null ) {
			    	BitsLog.e("BitsGLGraphics - drawText", "The BitsRenderCommand is NULL!");
			    	throw new NullPointerException("The BitsRenderCommand is NULL!");
			    }
			       	
			    command.mType = BitsRenderCommand.TYPE_IMAGE;
		       	command.mTexId = ((BitsGLFont)this.mLastFont).mTextureId;
		       	command.mTexWidth = ((BitsGLFont)this.mLastFont).mTextureSize;
		       	command.mTexHeight = ((BitsGLFont)this.mLastFont).mTextureSize;
		       	command.mX = (x + this.mLastTranslate[0]);// * BitsApp.sScaleFactor;
		       	command.mY = (y + this.mLastTranslate[1]);// * BitsApp.sScaleFactor;
		       	command.mWidth = chrWidth;
		       	command.mHeight = chrHeight;
		       	command.mCropX = ((BitsGLFont)this.mLastFont).mCharTextureRegions[c].mRect.mX;
		       	command.mCropY = ((BitsGLFont)this.mLastFont).mCharTextureRegions[c].mRect.mY;
		       	command.mCropWidth = ((BitsGLFont)this.mLastFont).mCharTextureRegions[c].mRect.mWidth;
		       	command.mCropHeight = ((BitsGLFont)this.mLastFont).mCharTextureRegions[c].mRect.mHeight;
			       		
		       	this.mRenderCommandQueues.add( command );
			       	
		       	x += ( ((BitsGLFont)this.mLastFont).mCharWidths[c] ); // Advance X Position by Scaled Character Width
	        }			
   		}
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

		this.drawTriangle(drawable, x1, y1, x2, y2, x3, y3, 0f, 0f, 1f, 0f, 0f, 1f);
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
			final float y3,
			final float texX1, 
			final float texY1,
			final float texX2, 
			final float texY2,
			final float texX3, 
			final float texY3 ) {
		
		//TODO Scale fehlt
		
    	if( drawable == null ) {
	    	BitsLog.e("BitsGLGraphics - drawTriangle", "The given BitsDrawable is NULL!");
	    	throw new NullPointerException("The given BitsDrawable is NULL!");
    	}
    	
    	if( drawable.mType != BitsDrawable.DRAWABLE_TYPE_GL_IMAGE ) {
    		BitsLog.e("BitsGLGraphics - drawTriangle", "The given BitsDrawable is not a BitsGLImage!");
    		throw new IllegalArgumentException("The given BitsDrawable is not a BitsGLImage!");
    	}
    	
    	if( drawable.isLoaded == false ) {
   			BitsLog.w("BitsGLGraphics - drawTriangle", "The given BitsDrawable was not loaded!");
   			return;
    	}

    	final BitsRenderCommand command = this.mRenderCommandPool.checkOut( );
		if( command == null ) {
		   	BitsLog.e("BitsGLGraphics - drawText", "The BitsRenderCommand is NULL!");
		   	throw new NullPointerException("The BitsRenderCommand is NULL!");
		}
    	
		command.mType = BitsRenderCommand.TYPE_TRIANGLE;
    	command.mTexId = ((BitsGLImage)drawable).mTextureId;
    	command.mTexWidth = ((BitsGLImage)drawable).mWidth;
    	command.mTexHeight = ((BitsGLImage)drawable).mHeight;
    	command.mX = (x1 + this.mLastTranslate[0]);// * BitsApp.sScaleFactor;
    	command.mY = (y1 + this.mLastTranslate[1]);// * BitsApp.sScaleFactor;
    	command.mWidth = (x2 + this.mLastTranslate[0]);// * BitsApp.sScaleFactor;
    	command.mHeight = (y2 + this.mLastTranslate[1]);// * BitsApp.sScaleFactor;
    	command.mCropX = (x3 + this.mLastTranslate[0]);// * BitsApp.sScaleFactor;
    	command.mCropY = (y3 + this.mLastTranslate[1]);// * BitsApp.sScaleFactor;
    	command.mCropWidth = texX1;
    	command.mCropHeight = texY1;
    	command.mTexX1 = texX2;
    	command.mTexY1 = texY2;
    	command.mTexX2 = texX3;
    	command.mTexY2 = texY3;
    	
    	this.mRenderCommandQueues.add( command );
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
	    	}
		} //accept null
		
		this.mLastFont = font;
	}
}
