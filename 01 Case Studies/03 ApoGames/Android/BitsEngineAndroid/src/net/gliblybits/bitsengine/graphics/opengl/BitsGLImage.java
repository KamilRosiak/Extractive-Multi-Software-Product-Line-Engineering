
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

import net.gliblybits.bitsengine.graphics.BitsDrawable;
import net.gliblybits.bitsengine.graphics.bitmap.BitsBitmap;

public final class 
BitsGLImage 
extends BitsDrawable {
	
	public final static int RENDER_STRETCH 	= 0;
	public final static int RENDER_REPEAT 	= 1;

	public final static int FILTER_NEAREST 	= 0;
	public final static int FILTER_LINEAR 	= 1;

	/**
	 * A vertex consists of the coordinates (x, y) and
	 * the texture coordinates (u, v) for cropping.
	 */
	public final static int VERTEX_SIZE 		= 4;
	
	/**
	 * A BitsImage consists of a rectangle with 4 points.
	 */
	public final static int VERTICES_PER_SPRITE = 4;
	
	/**
	 * A BitsImage consists of 2 triangles with 3 points for each triangle.
	 */
	public final static int INDICES_PER_SPRITE 	= 6;

	public int 				mTextureId	= -1;
	public String			mFile 		= null;
    public int				mResourceId = -1;
    public int 				mWidth 		= 0;
    public int 				mHeight 	= 0;
    public BitsBitmap 		mBitmap 	= null;
    public String			mBitmapHash	= null;
    
    public boolean			mKeepBitmap = false;
    
    public int				mRenderMode = RENDER_STRETCH;
    public int				mFilterMode = FILTER_NEAREST;
	
	public 
	BitsGLImage( ) {
		
		super( BitsDrawable.DRAWABLE_TYPE_GL_IMAGE );
	}
	
    public void 
    reset( ) {
    	
        this.mTextureId		= -1;
        this.mResourceId	= -1;
        this.mFile 			= null;
        this.mWidth 		=  0;
        this.mHeight 		=  0;
        
        this.isLoaded 		= false;
        this.mMark			= 0;
        
        this.mBitmapHash = null;
        if( this.mBitmap != null ) {
        	this.mBitmap.release( );
        	this.mBitmap = null;
        }
        
        this.mRenderMode = RENDER_STRETCH;
        this.mFilterMode = FILTER_NEAREST;
    }
}
