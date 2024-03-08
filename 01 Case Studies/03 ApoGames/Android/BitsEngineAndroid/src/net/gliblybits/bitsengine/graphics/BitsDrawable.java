
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

public abstract class 
BitsDrawable {
	
	public static final int DRAWABLE_TYPE_GL_IMAGE 			= 0;
	public static final int DRAWABLE_TYPE_GL_FONT			= 1;
	public static final int DRAWABLE_TYPE_GL_RENDER_STACK 	= 2;
	public static final int DRAWABLE_TYPE_BITMAP	 		= 3;
	public static final int DRAWABLE_TYPE_BITMAP_FONT 		= 4;
	
	/**
	 * Defines the derived type of this class:<br>
	 * GL_IMAGE == BitsGLImage<br>
	 * GL_FONT == BitsGLFont<br>
	 * GL_RENDER_STACK == BitsGLRenderStack<br>
	 * BITMAP == BitsBitmap<br>
	 * BITMAP_FONT == BitsBitmapFont<br>
	 */
	public final int mType;

	/**
	 * 0 - free; 1 - load; 2 - release;
	 */
	public int mMark = 0;
	
	/**
	 * 
	 */
	public boolean isLoaded = false;
	
	protected 
	BitsDrawable( 
			final int type ) {
		
		this.mType = type;
	}
}
