
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

package net.gliblybits.bitsengine.render;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import net.gliblybits.bitsengine.core.BitsApp;
import net.gliblybits.bitsengine.core.BitsGame;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLFactory;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLImage;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;
import net.gliblybits.bitsengine.render.commands.BitsRenderCommand;
import net.gliblybits.bitsengine.utils.BitsLog;
import android.content.Context;
import android.opengl.GLES11;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

/**
 * The BitsOpenGLRenderer class is the render thread.<br>
 * It (re)creates the opengl es surface, configures the opengl es system and<br>
 * renders the available render queues of the game-/logic-thread.<br>
 * <br>
 * @author Marc Wiedenhoeft - GliblyBits
 * @version 0.1 - Streak
 * @since 0.1 - Streak
 */
public final class 
BitsGLRenderer 
extends GLSurfaceView 
implements Renderer {
	
	/**
	 * Defines if the BitsGame wants to be called back, if the surface is ready.<br>
	 */
    private boolean wantReport 	= true;
    
    /**
     * Defines if a new render queue is ready to take from the game-/logic-thread.<br>
     */
    private boolean isNewRenderQueueAvailable = false;
    
    /**
     * Defines if android wants to pause the render thread.<br>
     */
    private boolean isPaused = false;
    
    /**
     * Defines if android resumed the render thread.<br>
     */
    private boolean isResumed = false;
    
    /**
     * A lock to set the render thread to sleep until a new render queue is available.<br>
     */
    private final Object mRenderLock = new Object( );
	
    /**
     * The current render queue, that the renderer will render.<br>
     */
    private ArrayList<BitsRenderCommand> mRenderQueue = null;
    
    /**
     * The reference to the game-/logic-thread.
     */
	private final BitsGame mBitsGame;
	
	/**
	 * This array is a buffer for all the float points and texture coordinates used to render stuff on the screen.
	 */
	private final float[] mVertices;

	/**
	 * The current position in the mVertices-array.
	 */
	private int mBufferIndex = 0;

	/**
	 * How many images are currently in the mVertices-array.
	 */
	private int mImageCount = 0;

	/**
	 * How many line primitives are currently in the mVertices-array.
	 */
	private int mLineCount = 0;
	
	/**
	 * How many rectangle primitives are currently in the mVertices-array.
	 */
	private int mRectCount = 0;
	
	/**
	 * How many triangle primitives are currently in the mVertices-array.
	 */
	private int mTriangleCount = 0;
	
	/**
	 * The FloatBuffer that contains the float values for each point/texture to render.<br>
	 */
	private final FloatBuffer mVertexBuffer;
	
	/**
	 * This buffer is used to save some point calculation during rendering.<br>
	 * It is filled with a repeating sequence of 0, 1, 2, 2, 3, 0<br>
	 * This sequence defines 2 triangles with 3 points each.<br>
	 * The OpenGL system will recognize two identical successive numbers (like 0, 0 / 2, 2) and will re-use the corresponding point values.<br>
	 * This is very useful, if multiple triangles (or other primitives) shares some points.<br>
	 * For example a BitsImage consists of two triangles that share 2 points (as seen in the previous example).<br>
	 * So, instead of defining the whole 6 points, the system will define only 4 points, as 2 points are one and the same point.<br>
	 */
	private final ShortBuffer mIndexBuffer;
	
	/**
	 * DEFAULT: -1<br>
	 * <br>
	 * The last used texture id.<br>
	 * If a render command contains a different texture id, the renderer will bind this new texture id.<br>
	 */
	private int mLastTextureId = 0;
	
	/**
	 * DEFAULT: 0, -1, -1<br>
	 * <br>
	 * The last used rotation value and the last specified rotation point.<br>
	 * If a render command contains different values, the renderer will set a new rotation.<br>
	 */
	private float[] mLastRotation = new float[3];

	private int mReleaseType = 0; //1 - All; 2 - Next
	private int mLoadType = 0; //1 - All; 2 - Next
	private int mNewWidth = 0;
	private int mNewHeight = 0;
	private boolean mSetClearColor 			= false;
    private boolean isLoadingRequested 		= false;
    private boolean isReleasingRequested 	= false;
    
    private int mVertexBufferSize = BitsApp.sRenderBufferSize;
    private int mIndexBufferSize = BitsApp.sRenderBufferSize / 16 * BitsGLImage.INDICES_PER_SPRITE;
    
	/**
	 * Constructor sets the BitsGame reference and the current renderer of the surface.<br>
	 * Also it initializes the buffers.
	 */
	public 
	BitsGLRenderer( 
			final Context context ) {
		
		super( context );
		
		this.mBitsGame = BitsGame.getInstance( );

		this.setRenderer( this );
		this.setFocusable(true);
		this.setFocusableInTouchMode(true);
		
		this.mVertexBufferSize = BitsApp.sRenderBufferSize;
		this.mIndexBufferSize = BitsApp.sRenderBufferSize / 16 * BitsGLImage.INDICES_PER_SPRITE;

		//init the rotation array
		this.mLastRotation[0] = 0f;
		this.mLastRotation[1] = -1f;
		this.mLastRotation[2] = -1f;		
		
		this.mVertices = new float[this.mVertexBufferSize];
		ByteBuffer buffer = ByteBuffer.allocateDirect( this.mVertices.length * 16 ); //16 -> (X,Y,U,V) are 4 Float values -> Float is 4 Bytes long
	    buffer.order( ByteOrder.nativeOrder() );
	    this.mVertexBuffer = buffer.asFloatBuffer();
	    
	    //Pre-Filling the index buffer. The buffer content will never change.
	    //This buffer always contains a sequence of 0,1,2,2,3,0 values that are used to render BitsImages efficiently.
	    final short[] indices = new short[this.mIndexBufferSize];
	    final int len = indices.length;
	    short j = 0;
	    for ( int i = 0; i < len; i+= BitsGLImage.INDICES_PER_SPRITE, j += BitsGLImage.VERTICES_PER_SPRITE )  {
	    	indices[i + 0] = (short)( j + 0 );           // Calculate Index 0 (first Triangle)
	        indices[i + 1] = (short)( j + 1 );           // Calculate Index 1 (first Triangle)
	        indices[i + 2] = (short)( j + 2 );           // Calculate Index 2 (first Triangle)
	        indices[i + 3] = (short)( j + 2 );           // Calculate Index 3 (second Triangle)
	        indices[i + 4] = (short)( j + 3 );           // Calculate Index 4 (second Triangle)
	        indices[i + 5] = (short)( j + 0 );           // Calculate Index 5 (second Triangle)
	    }
        buffer = ByteBuffer.allocateDirect( indices.length * (Short.SIZE / 8) ); //Short == 2 Bytes
        buffer.order( ByteOrder.nativeOrder() );
        this.mIndexBuffer = buffer.asShortBuffer();
        this.mIndexBuffer.clear();
        this.mIndexBuffer.put( indices );
        this.mIndexBuffer.flip();
	}

	/**
	 * This method will be called by the game-/logic-thread,<br>
	 * if a new render queue is available.<br>
	 */
	public final void 
	setNewRenderQueueAvailable( ) {
		
        synchronized(this.mRenderLock) {
        	this.isNewRenderQueueAvailable = true;
    		this.mRenderLock.notify();
    	}
	}
	
	/**
	 * Defines if the renderer may inform the game-/logic-thread,<br>
	 * if the surface ready or the last render cycle is done.<br>
	 * 
	 * @param report - want report
	 */
	public final void 
	wantReadyReport( 
			final boolean report ) {
		
		this.wantReport = report;
	}

	/**
	 * This method will be called by the BitsApp class,<br>
	 * if the app will be paused oder closed.<br>
	 */
    public final void 
    onPause( ) {
    	
        synchronized( this.mRenderLock ) {
        	this.isPaused = true;
        	this.isNewRenderQueueAvailable = true;
        	this.mRenderLock.notify();
        }
        
        super.onPause();
    }
    
    /**
     * This method will be called by the BitsApp class,<br>
     * if the app has been resumed.<br>
     */
    public final void 
    onResume() {
    	
    	this.isPaused = false;
    	this.isResumed = true;
    	this.mLastTextureId = 0;
    	super.onResume();
    }

	@Override
	/**
	 * This method will be called by android, if a new opengl es surface<br>
	 * has to be created.<br>
	 * It configures OpenGL ES.<br>
	 */
	public final void 
	onSurfaceCreated( 
			final GL10 gl,
			final EGLConfig config ) {
		
		BitsLog.d( "BitsGLRenderer - onSurfaceCreated", "Creating surface..." );    	

		GLES11.glMatrixMode( GLES11.GL_PROJECTION );
		GLES11.glLoadIdentity( );

		//orthogonal projection with no depth
		//the origin is the upper left corner
	    GLU.gluOrtho2D( gl, 0, BitsApp.sViewportWidth, 0, BitsApp.sViewportHeight);

	    //sets the size of the viewport
	    GLES11.glViewport( 0, 0, BitsApp.sViewportWidth, BitsApp.sViewportHeight);

	    //sets the quality of the interpolation of color and texture coordinates
	    GLES11.glHint( GLES11.GL_PERSPECTIVE_CORRECTION_HINT, GLES11.GL_NICEST);
	    GLES11.glHint( GLES11.GL_POLYGON_SMOOTH_HINT, GLES11.GL_NICEST);

	    GLES11.glMatrixMode( GLES11.GL_MODELVIEW);

	    //objects which are drawn later come to the front
	    GLES11.glDisable( GLES11.GL_DEPTH_TEST); 
	    GLES11.glDisable( GLES11.GL_STENCIL_TEST); 
	    
		//no shading...only flat colors for primitives
	    GLES11.glShadeModel( GLES11.GL_FLAT );

	    //activates transparency for textures
	    GLES11.glEnable( GLES11.GL_BLEND );	        
	    GLES11.glBlendFunc( GLES11.GL_SRC_ALPHA, GLES11.GL_ONE_MINUS_SRC_ALPHA );

	    //restrict the rendering area
		GLES11.glEnable( GLES11.GL_SCISSOR_TEST );    
	    
	    //color for opengl clearing color
	    GLES11.glClearColor( BitsGLGraphics.sClearColor[0], BitsGLGraphics.sClearColor[1], BitsGLGraphics.sClearColor[2], BitsGLGraphics.sClearColor[3]);

	    //texture primitives
	    GLES11.glEnable(GLES11.GL_TEXTURE_2D);
	}

	@Override
	/**
	 * This method will be called by android, if some specific configuration<br>
	 * changed (e.g. orientation changed).
	 */
	public final void 
	onSurfaceChanged( 
			final GL10 gl, 
			final int w, 
			final int h ) {
		
		BitsLog.d( "BitsGLRenderer", "Running onSurfaceChanged..." );

		//sets the size of the viewport
		BitsApp.sViewportWidth = w;
		BitsApp.sViewportHeight = h;
		
		//calculates the scale factor
		BitsApp.sScaleFactor = StrictMath.min( (float)BitsApp.sViewportWidth / (float)BitsApp.sGameWidth, (float)BitsApp.sViewportHeight / (float)BitsApp.sGameHeight );
		BitsApp.sScaleFactor = (float) (StrictMath.floor( BitsApp.sScaleFactor * BitsApp.sScalePrecision + 0.5f ) / BitsApp.sScalePrecision);
		BitsLog.d("BitsGLRenderer", "ScaleFactor = " + String.valueOf(BitsApp.sScaleFactor) + " with ScalePrecision = " + String.valueOf(BitsApp.sScalePrecision));

		BitsApp.sViewportPaddingWidth = w - (int)(BitsApp.sScaleFactor * (float)BitsApp.sGameWidth);
		BitsApp.sViewportPaddingHeight = h - (int)(BitsApp.sScaleFactor * (float)BitsApp.sGameHeight);
		
		//sets the new size for the viewport
		GLES11.glViewport( 
				BitsApp.sViewportPaddingWidth / 2, 
				BitsApp.sViewportPaddingHeight / 2, 
				BitsApp.sViewportWidth - BitsApp.sViewportPaddingWidth, 
				BitsApp.sViewportHeight - BitsApp.sViewportPaddingHeight);
		
	    //restrict the rendering area
		GLES11.glScissor( 
				(int)StrictMath.floor(BitsApp.sViewportPaddingWidth / 2), 
				(int)StrictMath.floor(BitsApp.sViewportPaddingHeight / 2), 
				(int)StrictMath.ceil(BitsApp.sViewportWidth - BitsApp.sViewportPaddingWidth), 
				(int)StrictMath.ceil(BitsApp.sViewportHeight - BitsApp.sViewportPaddingHeight) );
		
		GLES11.glMatrixMode(GLES11.GL_PROJECTION);
		GLES11.glLoadIdentity();

		//orthogonal projection with no depth
		//the origin is the upper left corner
		GLU.gluOrtho2D( gl, 0, BitsApp.sViewportWidth - BitsApp.sViewportPaddingWidth, BitsApp.sViewportHeight - BitsApp.sViewportPaddingHeight, 0);

        GLES11.glScalef(BitsApp.sScaleFactor, BitsApp.sScaleFactor, 0f);
        
		GLES11.glMatrixMode(GLES11.GL_MODELVIEW);
		GLES11.glLoadIdentity();
	}
	
	public final void
	requestResolution( 
			final int width,
			final int height ) {
		
		if( this.mNewWidth != 0 || this.mNewHeight != 0 ) {
			return; //already requested
		}
		
		synchronized( this.mRenderLock ) {
			BitsLog.d("BitsGLRenderer - requestResolution", "Change resolution requested...");
			this.mNewWidth = width;
			this.mNewHeight = height;
		}
	}
	
	public final void 
	requestLoading( 
			final int type ) {
		
		if( this.isLoadingRequested == true ) {
			return; //already requested
		}
		
		synchronized( this.mRenderLock ) {
			BitsLog.d("BitsGLRenderer - requestLoading", "Loading requested...");
			this.mLoadType = type;
			this.isLoadingRequested = true;
		}
	}
	
	public final void 
	requestReleasing( 
			final int type ) {
		
		if( this.isReleasingRequested == true ) {
			return; //already requested
		}
		
		synchronized( this.mRenderLock ) {
			BitsLog.d("BitsGLRenderer - requestReleasing", "Releasing requested...");
			this.mReleaseType = type;
			this.isReleasingRequested = true;
		}		
	}
	
	public final void 
	requestClearColor( ) {
		
		if( this.mSetClearColor == true ) {
			return; //already requested
		}
		
		synchronized( this.mRenderLock ) {
			this.mSetClearColor = true;
		}
	}
	
	@Override
	/**
	 * This method will be called by android, if a new frame should be rendered.<br>
	 */
	public final void 
	onDrawFrame( 
			final GL10 gl ) {
		
		if( this.isResumed == true ) {
			BitsLog.d( "OpenGLRenderer - onDrawFrame", "OpenGL is resumed...re-loading all resources." );
			this.isResumed = false;
			BitsGLFactory.getInstance().invalidateAll(); //set all contained resources to "NOT-loaded"
			BitsGame.getInstance().onResumeApp(); //to re-stack the BitsStacks
			BitsGLFactory.getInstance().reloadAll(); //loading all "NOT-loaded" resources
		}
		
		//calls the game-/logic-thread
		if( this.wantReport == true )	{
			BitsLog.d( "OpenGLRenderer - onDrawFrame", "OpenGL is ready..." );
			this.wantReadyReport( false );
			this.mLastTextureId = -1;
			this.mBitsGame.rendererReady( );
		}
		
		synchronized( this.mRenderLock ) {
	    	if( this.mSetClearColor == true ) {
	    		GLES11.glClearColor( BitsGLGraphics.sClearColor[0], BitsGLGraphics.sClearColor[1], BitsGLGraphics.sClearColor[2], BitsGLGraphics.sClearColor[3] );
	    		this.mSetClearColor = false;
	    	}
	    	
	    	//TODO make it work!
	    	if( this.mNewWidth != 0 && this.mNewHeight != 0 ) {
	    		BitsApp.sGameWidth = this.mNewWidth;
	    		BitsApp.sGameHeight = this.mNewHeight;
	    		BitsLog.d( "BitsOpenGLRenderer", "Running onSurfaceChanged..." );
	    		
	    		//calculates the scale factor
	    		BitsApp.sScaleFactor = Math.min( (float)BitsApp.sViewportWidth / (float)BitsApp.sGameWidth, (float)BitsApp.sViewportHeight / (float)BitsApp.sGameHeight );
	    		BitsLog.d("BitsOpenGLRenderer", "ScaleFactor = " + String.valueOf(BitsApp.sScaleFactor));

	    		BitsApp.sViewportPaddingWidth = BitsApp.sViewportWidth - (int)(BitsApp.sScaleFactor * (float)BitsApp.sGameWidth);
	    		BitsApp.sViewportPaddingHeight = BitsApp.sViewportHeight - (int)(BitsApp.sScaleFactor * (float)BitsApp.sGameHeight);
	    		
	    		//sets the new size for the viewport
	    		GLES11.glViewport( 
	    				BitsApp.sViewportPaddingWidth / 2, 
	    				BitsApp.sViewportPaddingHeight / 2, 
	    				BitsApp.sViewportWidth - BitsApp.sViewportPaddingWidth, 
	    				BitsApp.sViewportHeight - BitsApp.sViewportPaddingHeight);

	    	    //restrict the rendering area
	    		GLES11.glScissor( 
	    				(int)StrictMath.floor(BitsApp.sViewportPaddingWidth / 2), 
	    				(int)StrictMath.floor(BitsApp.sViewportPaddingHeight / 2), 
	    				(int)StrictMath.ceil(BitsApp.sViewportWidth - BitsApp.sViewportPaddingWidth), 
	    				(int)StrictMath.ceil(BitsApp.sViewportHeight - BitsApp.sViewportPaddingHeight) );
	    		
	    		GLES11.glMatrixMode(GLES11.GL_PROJECTION);
	    		GLES11.glLoadIdentity();

	    		//orthogonal projection with no depth
	    		//the origin is the upper left corner
	    		GLU.gluOrtho2D( gl, 0, BitsApp.sViewportWidth - BitsApp.sViewportPaddingWidth, BitsApp.sViewportHeight - BitsApp.sViewportPaddingHeight, 0);

	    		GLES11.glMatrixMode(GLES11.GL_MODELVIEW);
	    		GLES11.glLoadIdentity();
	    		
	            GLES11.glScalef(BitsApp.sScaleFactor, BitsApp.sScaleFactor, 0f);
	    		
	    		this.mNewWidth = 0;
	    		this.mNewHeight = 0;
	    	}
	
	    	if( this.isLoadingRequested == true ) {
				if( BitsGLFactory.getInstance().load( this.mLoadType ) == true) { //was anything loaded?
					this.mLastTextureId = -1;
				} else {
					BitsLog.d("BitsGLRenderer - onDrawFrame", "...finished loading request.");
					this.isLoadingRequested = false;
					this.mLoadType = 0;
					this.mLastTextureId = -1;
				}
	    	}
	
	    	if( this.isReleasingRequested == true ) {
				if( BitsGLFactory.getInstance().release( this.mReleaseType ) == false) { //was anything released?
					BitsLog.d("BitsGLRenderer - onDrawFrame", "...finished releasing request.");
					this.isReleasingRequested = false;
					this.mReleaseType = 0;
					this.mLastTextureId = -1;
				}
	    	}
		}

		//pause the render thread, if no new render queue is available
        synchronized( this.mRenderLock ) {
        	if ( this.isNewRenderQueueAvailable == false && this.isPaused == false ) {
	            while ( this.isNewRenderQueueAvailable == false ) {
	            	try { this.mRenderLock.wait(); } catch (InterruptedException e) { }
	            }
	        }
	    }
        this.isNewRenderQueueAvailable = false;
        
        //take the new render queue
    	this.mRenderQueue = this.mBitsGame.takeRenderQueue( );
    	
    	//if the queue is null, don't do anything
    	if( this.mRenderQueue == null ) {
    		return;
    	}

    	//if the new render queue isn't empty or NULL...draw it
    	final int count = this.mRenderQueue.size( );
		if( count > 0 ) {
			GLES11.glDisable( GLES11.GL_SCISSOR_TEST ); //glClear doesn't like the SCISSOR_TEST :-/
			GLES11.glClear( GLES11.GL_COLOR_BUFFER_BIT );
			GLES11.glEnable( GLES11.GL_SCISSOR_TEST );
	        for( int i = 0; i < count; i++ ) {
	        	final BitsRenderCommand command = this.mRenderQueue.get( i );
	        	
	        	/*
	        	 * BIND A NEW TEXTURE, IF A NEW ONE IS DEFINED
	        	 * command.mTexId == Texture ID
	        	 * !!!command.mTexId is EXCLUSIVE!!!DON'T USE IT FOR OTHER STUFF!!!
	        	 */
        		if( command.mTexId != 0 ) {
        			if( this.mLastTextureId != 0) {
        				if(this.mLastTextureId != command.mTexId) {
        					if(this.mImageCount > 0) {
        						this.renderIt();
        					}
        					GLES11.glBindTexture( GLES11.GL_TEXTURE_2D, command.mTexId );
        			        this.mLastTextureId = command.mTexId;					
        				}
        			} else {
        				GLES11.glBindTexture( GLES11.GL_TEXTURE_2D, command.mTexId );
        		        this.mLastTextureId = command.mTexId;				
        			}
        		}
        		
        		/*
        		 * SET BLENDFUNC
        		 * command.mTexWidth == sFactor
        		 * command.mTexHeight == dFactor
        		 */
        		if( command.mType == BitsRenderCommand.TYPE_BLENDFUNC ) {
        			GLES11.glBlendFunc( command.mTexWidth, command.mTexHeight );
        		}
        		
        		/*
        		 * SET CLIP
        		 * command.mX == x
        		 * command.mY == y
        		 * command.mWidth == width
        		 * command.mHeight == height
        		 */
        		if( command.mType == BitsRenderCommand.TYPE_CLIP ) {
        			this.renderIt();

        			GLES11.glScissor( 
        					(int)StrictMath.floor(command.mX + (BitsApp.sViewportPaddingWidth / 2) ), 
        					(int)StrictMath.floor(BitsApp.sViewportHeight - (BitsApp.sViewportPaddingHeight / 2) - command.mY - command.mHeight), 
        					(int)StrictMath.ceil(command.mWidth), 
        					(int)StrictMath.ceil(command.mHeight) );
        		}
        		
        		/*
        		 * SET ROTATION; IT WILL BE USED LATER
        		 * command.mX == Rotation
        		 * command.mWidth == x-Point
        		 * command.mHeight == y-Point
        		 */
        		if( command.mType == BitsRenderCommand.TYPE_ROTATION ) {
        			this.renderIt();
        			this.mLastRotation[0] = command.mX;
        			this.mLastRotation[1] = command.mWidth;
        			this.mLastRotation[2] = command.mHeight;		
        			continue;
        		}
        		
        		/*
        		 * SET THE LINE SIZE.
        		 * command.mX == Line Size
        		 */
        		if( command.mType == BitsRenderCommand.TYPE_LINE_SIZE ) {
        			if( ( this.mLineCount > 0 ) || 
        				( this.mImageCount > 0 ) || 
        				( this.mRectCount > 0 ) ||
        				( this.mTriangleCount > 0 ) ) {
        				this.renderIt( );
        			}

        			GLES11.glLineWidth( command.mX );
        			continue;
        		}
	        	
        		/*
        		 * SET NEW COLOR
        		 * command.mX = red
        		 * command.mY = green
        		 * command.mWidth = blue
        		 * command.mHeight = alpha
        		 */
	        	if( command.mType == BitsRenderCommand.TYPE_COLOR ) {
        			if( ( this.mLineCount > 0 ) || 
        				( this.mImageCount > 0 ) || 
        				( this.mRectCount > 0 ) ||
        				( this.mTriangleCount > 0 ) ) {
        				this.renderIt( );
        			}
	        		GLES11.glColor4f(command.mX, command.mY, command.mWidth, command.mHeight);
	        		continue;
	        	}
	        	
	        	/*
	        	 * RENDER CIRCLE (directly - no buffering)
	        	 * command.mX == x-Point
	        	 * command.mY == y-Point
	        	 * command.mWidth == radiusX
	        	 * command.mHeight == radiusY
	        	 * command.mCropX == startAngle && isArc(!= -1)/noArc(== -1)
	        	 * command.mCropY == angle
	        	 * command.mCropWidth == pointCount
	        	 * command.mCropHeight == wantCenterPoint
	        	 * command.mTexWidth == Filled?
	        	 */
	        	if( command.mType == BitsRenderCommand.TYPE_CIRCLE ) {
        			if( ( this.mLineCount > 0 ) || 
        				( this.mImageCount > 0 ) || 
        				( this.mRectCount > 0 ) ||
        				( this.mTriangleCount > 0 ) ) {
        				this.renderIt( );
        			}
        			
        			float currentStep = 0f;        			

        			if( command.mCropX != -1 ) { //is Arc

        				// drawing counterclockwise
            			if(command.mCropY < 0) {
        					command.mCropX = command.mCropX + command.mCropY;
        					if(command.mCropX < 0) {
        						command.mCropX += 360;
        					}
        					command.mCropY *= -1;
        				}

            			//put points into vertex buffer
            			this.putArcPointsInBuffer(
            					command.mX, 
            					command.mY, 
            					command.mWidth, 
            					command.mHeight, 
            					command.mCropX, 
            					command.mCropY,
            					command.mCropWidth,
            					command.mCropHeight );
        				
        			} else { // no Arc / full circle
        				final int totalSteps = (int) ( (command.mCropWidth + 1) * 2); //+1 -> start point == end point -> used twice
            			final float stepWidth = 2f * (float)(Math.PI) / (command.mCropWidth);
		    			for (int i1 = 0; i1 < totalSteps; i1 += 2) {
		    				this.mVertices[this.mBufferIndex++] = (float) (Math.cos(currentStep) * command.mWidth + command.mX);
		    				this.mVertices[this.mBufferIndex++] = (float) (Math.sin(currentStep) * command.mHeight + command.mY);
		    				currentStep += stepWidth;
		    			}
        			}
        			
        			//setting rotation for this circle/arc
        			GLES11.glLoadIdentity();
        	 		if( this.mLastRotation[0] > 0 ) {
        	 			this.doRotation( command.mX, command.mY );
        	 		}
        	 		
        	        this.mVertexBuffer.clear();
        	        this.mVertexBuffer.put( this.mVertices, 0, this.mBufferIndex );
        	        this.mVertexBuffer.flip();
        			
        		    GLES11.glEnableClientState( GLES11.GL_VERTEX_ARRAY );
        		    GLES11.glVertexPointer( 2, GLES11.GL_FLOAT, 0, this.mVertexBuffer );
	        		if( command.mTexWidth == 0 ) { // isFilled
	        			if( command.mCropHeight == 0 ) { // want CenterPoint
	        				GLES11.glDrawArrays( GLES11.GL_LINE_LOOP, 0, this.mBufferIndex / 2);
	        			} else {
	        				GLES11.glDrawArrays( GLES11.GL_LINE_STRIP, 0, this.mBufferIndex / 2);	        				
	        			}
	        		} else {
	        		   	GLES11.glDrawArrays( GLES11.GL_TRIANGLE_FAN, 0, this.mBufferIndex / 2);
	        		}
        		    GLES11.glDisableClientState( GLES11.GL_VERTEX_ARRAY );
        		    GLES11.glLoadIdentity();
        		    
        		    this.mBufferIndex = 0;
        		    
        			continue;
	        	}
       	
	        	/*
	        	 * RENDER IMAGE STACK (directly - no buffering)
	        	 * command.mTexWidth == VertexBufferId
	        	 * command.mTexHeight == IndexBufferId
	        	 * command.mX == VerticeCount
	        	 */
	        	if( command.mType == BitsRenderCommand.TYPE_IMAGE_STACK) {
			        GLES11.glEnable(GLES11.GL_TEXTURE_2D);
	        		GLES11.glEnableClientState( GLES11.GL_VERTEX_ARRAY );
	                GLES11.glEnableClientState( GLES11.GL_TEXTURE_COORD_ARRAY );
	                
	                GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, command.mTexWidth);
	                GLES11.glVertexPointer(2, GLES11.GL_FLOAT, 16, 0);	                
	                GLES11.glTexCoordPointer( 2, GLES11.GL_FLOAT, 16, 8 );

	                GLES11.glBindBuffer(GLES11.GL_ELEMENT_ARRAY_BUFFER, command.mTexHeight);
	                GLES11.glDrawElements( GLES11.GL_TRIANGLES, (int) command.mX, GLES11.GL_UNSIGNED_SHORT, 0 );

	                GLES11.glBindBuffer(GLES11.GL_ELEMENT_ARRAY_BUFFER, 0);
	                GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, 0);
	                GLES11.glDisableClientState( GLES11.GL_TEXTURE_COORD_ARRAY );
	                GLES11.glDisableClientState( GLES11.GL_VERTEX_ARRAY );
			        GLES11.glDisable(GLES11.GL_TEXTURE_2D);
	                
	                continue;
	        	}

	        	/*
	        	 * RENDER LINE
	        	 * command.mX == x-Point
	        	 * command.mY == y-Point
	        	 * command.mWidth == Width
	        	 * command.mHeight == Height
	        	 */
	        	if( command.mType == BitsRenderCommand.TYPE_LINE ) {
	        		//if buffer is full or other render type are already in the buffer, render the content
        			if( ( (this.mLineCount + 1) * 4 >= this.mVertexBufferSize ) || 
        				( this.mImageCount > 0 ) || 
        				( this.mRectCount > 0 ) ||
        				( this.mTriangleCount > 0 ) ) {
        				this.renderIt( );
        			}

        			this.mVertices[this.mBufferIndex++] = command.mX;
        			this.mVertices[this.mBufferIndex++] = command.mY;
   	 	         	this.mVertices[this.mBufferIndex++] = command.mWidth;
   	 	         	this.mVertices[this.mBufferIndex++] = command.mHeight;

   	 	         	this.mLineCount++;
   	 	         	
        	 		if( this.mLastRotation[0] > 0 ) {
        	 			GLES11.glLoadIdentity();
        	 			this.doRotation( command.mX + ( command.mWidth / 2 ), command.mY + ( command.mHeight / 2 ) );
        	 			this.renderIt();
        	 			GLES11.glLoadIdentity();
        	 		}
	        		
	        		continue;
	        	}

	        	/*
	        	 * RENDER OUTLINED RECT
	        	 * Need to be rendered directly (no buffering) -> GL_LINE_LOOP would connect the last and first point of 2 or more rects
	        	 * command.mX == x-Point
	        	 * command.mY == y-Point
	        	 * command.mWidth = Width
	        	 * command.mHeight = Height
	        	 */
	        	if( command.mType == BitsRenderCommand.TYPE_RECT_OUTLINED ) {
	        		//if buffer is full or other render types are already in the buffer, render the content
        			if( ( this.mRectCount > 0 ) || 
        				( this.mLineCount > 0 ) || 
        				( this.mImageCount > 0 ) ||
        				( this.mTriangleCount > 0 ) ) {
        				this.renderIt( );
        			}
	        		
	   		        this.mVertices[this.mBufferIndex++] = command.mX;
	   		        this.mVertices[this.mBufferIndex++] = command.mY;
	   		        this.mVertices[this.mBufferIndex++] = command.mX + command.mWidth;
	   		        this.mVertices[this.mBufferIndex++] = command.mY;
	   		        this.mVertices[this.mBufferIndex++] = command.mX + command.mWidth;
	   		        this.mVertices[this.mBufferIndex++] = command.mY + command.mHeight;
	   		        this.mVertices[this.mBufferIndex++] = command.mX;
	   		        this.mVertices[this.mBufferIndex++] = command.mY + command.mHeight;
	   		        
        	 		if( this.mLastRotation[0] > 0 ) {
        	 			GLES11.glLoadIdentity();
        	 			this.doRotation( command.mX + ( command.mWidth / 2), command.mY + ( command.mHeight / 2 ) );
        	 		}
        	 		
	   		        this.mVertexBuffer.clear();
	   		        this.mVertexBuffer.put( this.mVertices, 0, 8 ); //put only 8 values into the VertexBuffer
	   		        this.mVertexBuffer.flip();
	   		        
	   		        GLES11.glEnableClientState( GLES11.GL_VERTEX_ARRAY );
	   		        GLES11.glVertexPointer(2, GLES11.GL_FLOAT, 0, this.mVertexBuffer);
	        		GLES11.glDrawArrays(GLES11.GL_LINE_LOOP, 0, 4); //every rect got 4 points
	        		GLES11.glDisableClientState( GLES11.GL_VERTEX_ARRAY );
	        		
	        		this.mBufferIndex = 0;
	        		
	        		continue;
	        	}
	        	
	        	/*
	        	 * RENDER RECT
	        	 * command.mX == x-Point
	        	 * command.mY == y-Point
	        	 * command.mWidth == Width
	        	 * command.mHeight == Height
	        	 */
	        	if( command.mType == BitsRenderCommand.TYPE_RECT ) {
	        		//if buffer is full or other render types are already in the buffer, render the content
        			if( ( this.mRectCount + 1 ) * 8 >= this.mVertexBufferSize || 
	        			( this.mRectCount + 1 ) * 6 >= this.mIndexBufferSize || 
        				( this.mLineCount > 0 ) || 
        				( this.mImageCount > 0 ) ||
        				( this.mTriangleCount > 0 ) ) {
        				this.renderIt( );
        			}
	        		
	   		        this.mVertices[this.mBufferIndex++] = command.mX;
	   		        this.mVertices[this.mBufferIndex++] = command.mY;
	   		        this.mVertices[this.mBufferIndex++] = command.mX + command.mWidth;
	   		        this.mVertices[this.mBufferIndex++] = command.mY;
	   		        this.mVertices[this.mBufferIndex++] = command.mX + command.mWidth;
	   		        this.mVertices[this.mBufferIndex++] = command.mY + command.mHeight;
	   		        this.mVertices[this.mBufferIndex++] = command.mX;
	   		        this.mVertices[this.mBufferIndex++] = command.mY + command.mHeight;
	   		         
	   		        this.mRectCount++;
	   		        
        	 		if( this.mLastRotation[0] > 0 ) {
        	 			GLES11.glLoadIdentity();
        	 			this.doRotation( command.mX + ( command.mWidth / 2 ), command.mY + ( command.mHeight / 2 ) );
        	 			this.renderIt();
        	 			GLES11.glLoadIdentity();
        	 		}
	   		         
	   		        continue;
	        	}
	        	
	        	/*
	        	 * RENDER ROUND RECT (directly - no buffering)
	        	 * command.mX == x-Point
	        	 * command.mY == y-Point
	        	 * command.mWidth == Width
	        	 * command.mHeight == Height
	        	 * command.mCropWidth == curveRadius
	        	 * command.mCropHeight == curvePoints
	        	 * command.mTexWidth == Outline?
	        	 */
	        	if( command.mType == BitsRenderCommand.TYPE_ROUND_RECT ) {
	        		//if buffer is full or other render type are already in the buffer, render the content
        			if( ( this.mImageCount > 0 ) || 
        				( this.mLineCount > 0 ) || 
        				( this.mRectCount > 0 ) ||
        				( this.mTriangleCount > 0 ) ) {
        				this.renderIt( );
        			}
        			
        			if( command.mTexWidth == 0 ) { //outlined
    	        		//if the current buffer size is to small for a round rect
    	        		if( this.mVertices.length < (command.mCropHeight * 4) ) {
    	        			BitsLog.e("BitsGLRenderer - onDrawFrame", "The buffer is to small to hold all the points for the round rect you like to render. Adjust the BitsApp.sRenderBufferSize value. BufferSize: " + this.mVertices.length + " -- RoundRect points: " + (16 + (command.mCropHeight * 4)));
    	        			throw new RuntimeException("The buffer is to small to hold all the points for the round rect you like to render. Adjust the BitsApp.sRenderBufferSize value. BufferSize: " + this.mVertices.length + " -- RoundRect points: " + (16 + (command.mCropHeight * 4)));
    	        		}
    	        		
		   		        //first Arc (top left)
    	        		float x = command.mX + command.mCropWidth;
    	        		float y = command.mY + command.mCropWidth;
		   		        this.putArcPointsInBuffer( x, y, command.mCropWidth, command.mCropWidth, 180, 90, command.mCropHeight, 1 );

		   		        //second Arc (top right)
		   		        x = x + command.mWidth - command.mCropWidth - command.mCropWidth;
		   		        this.putArcPointsInBuffer( x, y, command.mCropWidth, command.mCropWidth, 270, 90, command.mCropHeight, 1 );

		   		        //third Arc (bottom right)
		   		        y = y + command.mHeight - command.mCropWidth - command.mCropWidth;
		   		        this.putArcPointsInBuffer( x, y, command.mCropWidth, command.mCropWidth, 0, 90, command.mCropHeight, 1 );
		   		        
		   		        //fourth Arc (bottom left)
		   		        x = x - command.mWidth + command.mCropWidth + command.mCropWidth;
		   		        this.putArcPointsInBuffer( x, y, command.mCropWidth, command.mCropWidth, 90, 90, command.mCropHeight, 1 );
		   		        
	        	 		if( this.mLastRotation[0] > 0 ) {
	        	 			GLES11.glLoadIdentity();
	        	 			this.doRotation( command.mX + ( command.mWidth / 2), command.mY + ( command.mHeight / 2 ) );
	        	 		}
		   		        
	        			//render
		   		        this.mVertexBuffer.clear();
		   		        this.mVertexBuffer.put( this.mVertices, 0, this.mBufferIndex );
		   		        this.mVertexBuffer.flip();
		   		        GLES11.glEnableClientState( GLES11.GL_VERTEX_ARRAY );
		   		        GLES11.glVertexPointer(2, GLES11.GL_FLOAT, 0, this.mVertexBuffer);
		        		GLES11.glDrawArrays(command.mTexWidth == 0 ? GLES11.GL_LINE_LOOP : GLES11.GL_TRIANGLE_FAN, 0, this.mBufferIndex / 2);
		        		GLES11.glDisableClientState( GLES11.GL_VERTEX_ARRAY );
//		        		GLES11.glLoadIdentity();
		        		this.mBufferIndex = 0;
		        		
        			}

	        		continue;
	        	}

	        	if( command.mType == BitsRenderCommand.TYPE_TRIANGLE ) {
	        		//if buffer is full or other render type are already in the buffer, render the content
        			if( (this.mTriangleCount + 1) * 12 >= this.mVertexBufferSize ||
        				( this.mImageCount > 0 ) ||
        				( this.mLineCount > 0 ) || 
        				( this.mRectCount > 0 ) ) {
        				this.renderIt( );
        			}
        			
        			if( command.mTexX1 == -1 ) {
	        			this.mVertices[this.mBufferIndex++] = command.mX;
	        			this.mVertices[this.mBufferIndex++] = command.mY;
       	 	         	this.mVertices[this.mBufferIndex++] = 0f;
       	 	         	this.mVertices[this.mBufferIndex++] = 0f;
	   	 	         	this.mVertices[this.mBufferIndex++] = command.mWidth;
	   	 	         	this.mVertices[this.mBufferIndex++] = command.mHeight;
       	 	         	this.mVertices[this.mBufferIndex++] = 1f;
       	 	         	this.mVertices[this.mBufferIndex++] = 0f;
	   	 	         	this.mVertices[this.mBufferIndex++] = command.mCropX;
	   	 	         	this.mVertices[this.mBufferIndex++] = command.mCropY;
       	 	         	this.mVertices[this.mBufferIndex++] = 0f;
       	 	         	this.mVertices[this.mBufferIndex++] = 1f;
        			} else {
        				this.mVertices[this.mBufferIndex++] = command.mX;
	        			this.mVertices[this.mBufferIndex++] = command.mY;
       	 	         	this.mVertices[this.mBufferIndex++] = (float)(command.mCropWidth) / (float)(command.mTexWidth);
       	 	         	this.mVertices[this.mBufferIndex++] = (float)(command.mCropHeight) / (float)(command.mTexHeight);
	   	 	         	this.mVertices[this.mBufferIndex++] = command.mWidth;
	   	 	         	this.mVertices[this.mBufferIndex++] = command.mHeight;
       	 	         	this.mVertices[this.mBufferIndex++] = (float)(command.mTexX1) / (float)(command.mTexWidth);
       	 	         	this.mVertices[this.mBufferIndex++] = (float)(command.mTexY1) / (float)(command.mTexHeight);
	   	 	         	this.mVertices[this.mBufferIndex++] = command.mCropX;
	   	 	         	this.mVertices[this.mBufferIndex++] = command.mCropY;
       	 	         	this.mVertices[this.mBufferIndex++] = (float)(command.mTexX2) / (float)(command.mTexWidth);;
       	 	         	this.mVertices[this.mBufferIndex++] = (float)(command.mTexY2) / (float)(command.mTexHeight);
        			}
   	 	         	
   	 	         	this.mTriangleCount++;
   	 	         	
   	 	         	//TODO Rotation fehlt
   	 	         	continue;
	        	}
	        	
	        	//add image to render buffer
	        	/*
	        	 * RENDER IMAGE - buffered
	        	 * command.mX == x-Point
	        	 * command.mY == y-Point
	        	 * command.mWidth == Width
	        	 * command.mHeight == Height
	        	 * command.mCropX == x-TextureCrop
	        	 * command.mCropY == y-TextureCrop
	        	 * command.mCropWidth == width-TextureCrop
	        	 * command.mCropHeight == height-TextureCrop
	        	 * command.mTexWidth == width-Texture
	        	 * command.mTexHeight == height-Texture
	        	 */
	        	if( command.mType == BitsRenderCommand.TYPE_IMAGE ) {
	        		//if buffer is full or other render type are already in the buffer, render the content
        			if( ( this.mImageCount + 1) * 16 == this.mVertexBufferSize || 
	        			( this.mImageCount + 1 ) * 6 >= this.mIndexBufferSize ||  
        				( this.mLineCount > 0 ) || 
        				( this.mRectCount > 0 ) ||
        				( this.mTriangleCount > 0 ) ) {
        				this.renderIt( );
        			}
        	 		
        			//set texture crop
        			if(command.mCropX > -1 && command.mCropY > -1 && command.mCropWidth > -1 && command.mCropHeight > -1) {
        	 	         this.mVertices[this.mBufferIndex++] = command.mX;
        	 	         this.mVertices[this.mBufferIndex++] = command.mY;
        	 	         this.mVertices[this.mBufferIndex++] = (float)(command.mCropX) / (float)(command.mTexWidth);
        	 	         this.mVertices[this.mBufferIndex++] = (float)(command.mCropY) / (float)(command.mTexHeight);

        	 	         this.mVertices[this.mBufferIndex++] = command.mX + command.mWidth;
        	 	         this.mVertices[this.mBufferIndex++] = command.mY;
        	 	         this.mVertices[this.mBufferIndex++] = (float)(command.mCropX + command.mCropWidth) / (float)(command.mTexWidth);
        	 	         this.mVertices[this.mBufferIndex++] = (float)(command.mCropY) / (float)(command.mTexHeight);

        	 	         this.mVertices[this.mBufferIndex++] = command.mX + command.mWidth;
        	 	         this.mVertices[this.mBufferIndex++] = command.mY + command.mHeight;
        	 	         this.mVertices[this.mBufferIndex++] = (float)(command.mCropX + command.mCropWidth) / (float)(command.mTexWidth);
        	 	         this.mVertices[this.mBufferIndex++] = (float)(command.mCropY + command.mCropHeight) / (float)(command.mTexHeight);

        	 	         this.mVertices[this.mBufferIndex++] = command.mX;
        	 	         this.mVertices[this.mBufferIndex++] = command.mY + command.mHeight;
        	 	         this.mVertices[this.mBufferIndex++] = (float)(command.mCropX) / (float)(command.mTexWidth);
        	 	         this.mVertices[this.mBufferIndex++] = (float)(command.mCropY + command.mCropHeight) / (float)(command.mTexHeight);

        			} else { //use the full texture
        		         this.mVertices[this.mBufferIndex++] = command.mX;
        		         this.mVertices[this.mBufferIndex++] = command.mY;
        		         this.mVertices[this.mBufferIndex++] = 0f;
        		         this.mVertices[this.mBufferIndex++] = 0f;

        		         this.mVertices[this.mBufferIndex++] = command.mX + command.mWidth;
        		         this.mVertices[this.mBufferIndex++] = command.mY;
        		         this.mVertices[this.mBufferIndex++] = command.mRenderMode == BitsGLImage.RENDER_STRETCH ? 1f : (command.mWidth / command.mTexWidth);
        		         this.mVertices[this.mBufferIndex++] = 0f;

        		         this.mVertices[this.mBufferIndex++] = command.mX + command.mWidth;
        		         this.mVertices[this.mBufferIndex++] = command.mY + command.mHeight;
        		         this.mVertices[this.mBufferIndex++] = command.mRenderMode == BitsGLImage.RENDER_STRETCH ? 1f : (command.mWidth / command.mTexWidth);
        		         this.mVertices[this.mBufferIndex++] = command.mRenderMode == BitsGLImage.RENDER_STRETCH ? 1f : (command.mHeight / command.mTexHeight);

        		         this.mVertices[this.mBufferIndex++] = command.mX;
        		         this.mVertices[this.mBufferIndex++] = command.mY + command.mHeight;
        		         this.mVertices[this.mBufferIndex++] = 0f;
        		         this.mVertices[this.mBufferIndex++] = command.mRenderMode == BitsGLImage.RENDER_STRETCH ? 1f : (command.mHeight / command.mTexHeight);
        			}
        	 		this.mImageCount++;

        	 		if( this.mLastRotation[0] > 0 ) {
        	 			GLES11.glLoadIdentity();
        	 			this.doRotation( command.mX + ( command.mWidth / 2), command.mY + ( command.mHeight / 2 ) );
        	 			this.renderIt();
        	 			GLES11.glLoadIdentity();
        	 		}

	        		continue;
	        	}	        	
	        }
	        
	        this.renderIt(); //finally, render the remaining content of the render buffer
		}
	}
	
	/**
	 * Rotates the coordinate system around its origin (0, 0).<br>
	 * It is possible to translate the render objects (primitives) to a specific point (x, y)<br>
	 * to manipulate there rotation behavior.<br>
	 * To rotate a primitive around its center, the given point (x, y) must be the center point<br>
	 * of the rendered primitive:<br> 
	 * x == primitive.x + (primitive.width / 2)<br>
	 * y == primitive.y + (primitive.height / 2)<br>
	 * 
	 * @param x - Position to translate the object to, to rotate it around its center
	 * @param y - Position to translate the object to, to rotate it around its center
	 */
	private final void 
	doRotation( 
			final float x, 
			final float y ) {
		
		if( this.mLastRotation[1] >= 0 && this.mLastRotation[2] >= 0 ) {
			GLES11.glTranslatef( this.mLastRotation[1], this.mLastRotation[2], 0f);
		} else {
			GLES11.glTranslatef( x, y, 0f);
		}
		
		GLES11.glRotatef(this.mLastRotation[0], 0f, 0f, 1f);
		if( this.mLastRotation[1] >= 0 && this.mLastRotation[2] >= 0 ) {
			GLES11.glTranslatef( -this.mLastRotation[1], -this.mLastRotation[2], 0f);
		} else {
			GLES11.glTranslatef( -x, -y, 0f);
		}
	}
	
	/**
	 * Calculates the points of an Arc and puts these points into the vertice array.
	 * 
	 * @param x - x-Position of the center point
	 * @param y - y-Position of the center point
	 * @param radiusX - width
	 * @param radiusY - height
	 * @param startAngle
	 * @param angle
	 * @param totalSteps
	 * @param pointCount
	 * @param wantCenterPoint
	 */
	private final void 
	putArcPointsInBuffer(
			final float x,
			final float y,
			final float radiusX,
			final float radiusY,
			final float startAngle,
			final float angle,
			final float pointCount,
			final float wantCenterPoint ) {
		
		// calculation of the step width:
		// 2 * Pi is a full circle multiplied by the float representation of the angle == the desired angle  
		// The desired angle is divided by the given BitsApp.CirclePoints == the step width for the desired angle with the given circle points
		final float stepWidth = (2f * (float)(Math.PI) * (angle / 360f)) / pointCount;

		//calculate the start angle/step
		float currentStep = (2f * (float)Math.PI) * (startAngle / 360f);
		int totalSteps = (int) (pointCount); //how many steps between 
		
		if( wantCenterPoint == 0 ) {
			// show a center point if the arc is not 360 degrees
			if(angle > 0 && angle < 360) {
				this.mVertices[this.mBufferIndex++] = x;
				this.mVertices[this.mBufferIndex++] = y;
			}
		}

		// calculating the steps
		for (int i = 0; i < totalSteps; i++) {
			this.mVertices[this.mBufferIndex++] = (float) (Math.cos(currentStep) * radiusX + x);
			this.mVertices[this.mBufferIndex++] = (float) (Math.sin(currentStep) * radiusY + y);
			currentStep += stepWidth;
		}

		// setting the last point to the desired angle position (step width does not always get to this point)
		this.mVertices[this.mBufferIndex++] = (float) (Math.cos(currentStep) * radiusX + x);
		this.mVertices[this.mBufferIndex++] = (float) (Math.sin(currentStep) * radiusY + y);
	}
	
	/**
	 * Renders buffered Lines, Rects or Images.
	 */
    private final void 
    renderIt( ) {
    	
    	if( this.mImageCount > 0 || this.mLineCount > 0 || this.mRectCount > 0 || this.mTriangleCount > 0 ) {
	        
    		this.mVertexBuffer.clear(); //clearing the buffer
	        GLES11.glEnableClientState( GLES11.GL_VERTEX_ARRAY );

	        //if there are images to render
	        if( this.mImageCount > 0 ) {
		        this.mVertexBuffer.put( this.mVertices, 0, this.mImageCount * 16 ); //put available points into the buffer
		        this.mVertexBuffer.flip(); //flipping buffer
		        GLES11.glVertexPointer( 2, GLES11.GL_FLOAT, 16, this.mVertexBuffer ); //copy the vertices into the GPU
		        GLES11.glEnableClientState( GLES11.GL_TEXTURE_COORD_ARRAY ); //copy the texture coordinates into the GPU
		        GLES11.glEnable(GLES11.GL_TEXTURE_2D);
		        this.mVertexBuffer.position( 2 ); //put buffer position to the texture coordinates
		        GLES11.glTexCoordPointer( 2, GLES11.GL_FLOAT, 16, this.mVertexBuffer );
		        this.mIndexBuffer.limit(this.mImageCount * 6); //DESKTOP VERSION ONLY
		        GLES11.glDrawElements( GLES11.GL_TRIANGLES, this.mImageCount * BitsGLImage.INDICES_PER_SPRITE, GLES11.GL_UNSIGNED_SHORT, this.mIndexBuffer );
	        	GLES11.glDisable(GLES11.GL_TEXTURE_2D);
		        GLES11.glDisableClientState( GLES11.GL_TEXTURE_COORD_ARRAY );
	        }
	        
	        //if there are lines to render
	        if( this.mLineCount > 0 ) {
		        this.mVertexBuffer.put( this.mVertices, 0, this.mLineCount * 4 ); //put available points into the buffer
		        this.mVertexBuffer.flip(); //flipping buffer
		        GLES11.glVertexPointer( 2, GLES11.GL_FLOAT, 0, this.mVertexBuffer ); //copy the vertices into the GPU
		        GLES11.glDrawArrays( GLES11.GL_LINES, 0, this.mLineCount * 2 ); //* 2 because every line got 2 points
	        }

	        //if there are rects to render
	        if( this.mRectCount > 0 ) {
		        this.mVertexBuffer.put( this.mVertices, 0, this.mRectCount * 8 ); //put available points into the buffer
		        this.mVertexBuffer.flip(); //flipping buffer
		        this.mIndexBuffer.limit(this.mRectCount * 6); //DESKTOP VERSION ONLY
	    	    GLES11.glVertexPointer(2, GLES11.GL_FLOAT, 0, this.mVertexBuffer); //copy the vertices into the GPU	    	    
	    	   	GLES11.glDrawElements( GLES11.GL_TRIANGLES, this.mRectCount * BitsGLImage.INDICES_PER_SPRITE, GLES11.GL_UNSIGNED_SHORT, this.mIndexBuffer );
	        }
	        
	        //if there are triangles to render
	        if( this.mTriangleCount > 0 ) {
		        this.mVertexBuffer.put( this.mVertices, 0, this.mTriangleCount * 12 ); //put available points into the buffer
		        this.mVertexBuffer.flip(); //flipping buffer
		        GLES11.glVertexPointer( 2, GLES11.GL_FLOAT, 16, this.mVertexBuffer ); //copy the vertices into the GPU
		        GLES11.glEnableClientState( GLES11.GL_TEXTURE_COORD_ARRAY ); //copy the texture coordinates into the GPU
		        GLES11.glEnable(GLES11.GL_TEXTURE_2D);
		        this.mVertexBuffer.position( 2 ); //put buffer position to the texture coordinates
		        GLES11.glTexCoordPointer( 2, GLES11.GL_FLOAT, 16, this.mVertexBuffer ); //16 == byteoffset -> es liegen 2 werte dazwischen
		        GLES11.glDrawArrays( GLES11.GL_TRIANGLES, 0, this.mTriangleCount * 3 ); //* 2 because every line got 2 points	        	
		        GLES11.glDisable(GLES11.GL_TEXTURE_2D);
		        GLES11.glDisableClientState( GLES11.GL_TEXTURE_COORD_ARRAY );
	        }
	        
	        GLES11.glDisableClientState( GLES11.GL_VERTEX_ARRAY );
    		
	        //resetting counters
	        this.mBufferIndex = 0;
	    	this.mImageCount = 0;
	    	this.mLineCount = 0;
	    	this.mRectCount = 0;
	    	this.mTriangleCount = 0;
    	}
    }
}
