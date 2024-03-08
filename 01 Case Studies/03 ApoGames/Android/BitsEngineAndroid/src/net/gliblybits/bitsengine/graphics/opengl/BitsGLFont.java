
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
import net.gliblybits.bitsengine.graphics.BitsFont;
import net.gliblybits.bitsengine.graphics.bitmap.BitsBitmap;

public final class 
BitsGLFont
extends BitsFont {
	
	public static final int TYPE_FILE 		= 0;
	public static final int TYPE_BITMAP 	= 1;
	
	public final static int FILTER_NEAREST 	= 0;
	public final static int FILTER_LINEAR 	= 1;
	
	public final static int CHAR_START 		= 32;
	public final static int CHAR_END 		= 126;
	public final static int CHAR_CNT 		= ( ( ( CHAR_END - CHAR_START ) + 1 ) + 1 );
	public final static int CHAR_NONE 		= 32;
	public final static int CHAR_UNKNOWN 	= ( CHAR_CNT - 1 );
	public final static int SIZE_MIN 		= 6;
	public final static int SIZE_MAX 		= 180;
	
	public int			mType		= -1;
	public BitsBitmap 	mBitmap 	= null;	
    public String 		mBitmapHash = null;
	public String 		mFile 		= null;
	
	public int 			mTextureId 		= -1;
	public int 			mFontSize 		= -1;
	public int			mTextureSize 	= 0;
    public int			mFilterMode 	= FILTER_NEAREST;

	public float mMaxCharWidth;
	public float mMaxCharHeight;
	public int mCharCellWidth;
	public int mCharCellHeight;
	public float mFontHeight;
	public float mFontAscent;	
	public float mFontDescent;

	public final float[] 				mCharWidths;
	public final BitsGLTextureRegion[] 	mCharTextureRegions;
	
	public 
	BitsGLFont( ) {
		
		super( BitsDrawable.DRAWABLE_TYPE_GL_FONT );
		
		// Create the Array of Character Widths
	    this.mCharWidths = new float[CHAR_CNT]; 
	    
	    // Create the Array of Character Regions
	    this.mCharTextureRegions = new BitsGLTextureRegion[CHAR_CNT];

	    // initialize remaining members
	    this.mFontHeight 			= 0.0f;
	    this.mFontAscent 			= 0.0f;
	    this.mFontDescent 			= 0.0f;

	    this.mMaxCharWidth 			= 0;
	    this.mMaxCharHeight 		= 0;

	    this.mCharCellWidth 		= 0;
	    this.mCharCellHeight 		= 0;
	}
	
	public final void 
	reset( ) {
		
		this.mType 		= -1;
		this.mFontSize  = -1;
		this.mFontDescent = 0.0f;
		this.mFile 		= null;
		this.mTextureId = -1;
		this.isLoaded 	= false;
		this.mMark		= 0;
        this.mFilterMode = FILTER_NEAREST;
		
		if( this.mBitmap != null ) {
			this.mBitmap.release();
			this.mBitmap = null;
		}
		this.mBitmapHash = null;
	}

	public final float 
	getLength( 
			final String text ) {
		
		float len = 0.0f; // Working Length
	    final int strLen = text.length(); // Get String Length (Characters)
	    final float[] charWidths = this.mCharWidths;
	    for ( int i = 0; i < strLen; i++ ) { // For Each Character in String (Except Last)
	    	final int c = (int)text.charAt( i ) - BitsGLFont.CHAR_START; // Calculate Character Index (Offset by First Char in Font)
	        len += ( charWidths[c] ); // Add Scaled Character Width to Total Length
	    }
	    return len; // Return Total Length
	}
}
