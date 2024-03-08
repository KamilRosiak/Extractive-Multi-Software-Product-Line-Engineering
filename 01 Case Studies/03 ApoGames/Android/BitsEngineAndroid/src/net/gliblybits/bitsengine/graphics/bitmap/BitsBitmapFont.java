
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

import android.graphics.Typeface;
import net.gliblybits.bitsengine.core.BitsApp;
import net.gliblybits.bitsengine.graphics.BitsDrawable;
import net.gliblybits.bitsengine.graphics.BitsFont;
import net.gliblybits.bitsengine.utils.BitsLog;

public final class 
BitsBitmapFont 
extends BitsFont {
	
	public Typeface mSource = null; 
	
	public 
	BitsBitmapFont( ) {
		
		super( BitsDrawable.DRAWABLE_TYPE_BITMAP_FONT );
	}

	public 
	BitsBitmapFont( 
			final String file, 
			final int size ) {
		
		super( BitsDrawable.DRAWABLE_TYPE_BITMAP_FONT );
		
		if( file == null ) {
			BitsLog.e("BitsBitmapFont - constructor", "The given file String is Null!");
			throw new NullPointerException("The given file String is Null!");
		}
		
		if( file.equals("") == true ) {
			BitsLog.e("BitsBitmapFont - constructor", "The given file String is empty!");
			throw new IllegalArgumentException("The given file String is empty!");
		}
		
		if( size <= 0 ) {
			BitsLog.e("BitsBitmapFont - constructor", "The given size must be > 0!");
			throw new IllegalArgumentException("The given size must be > 0!");
		}
		
    	try {
    		this.mSource = Typeface.createFromAsset( BitsApp.sAppContext.getAssets( ), file ); // Create the Typeface from asset Font File
    	} catch( Exception e ) {
    		try {
    			this.mSource = Typeface.createFromFile( file );
    		} catch( Exception ee ) {
    			BitsLog.e( ee, "BitsBitmapFont - constructor", "Font not found. Unable to load the font: " + file );
    			throw new RuntimeException("Font not found. Unable to load the font: " + file);
    		}
    	}
    	
    	if( this.mSource == null ) {
			BitsLog.e( "BitsBitmapFont - constructor", "Unable to load the font: " + file );
			throw new RuntimeException("Unable to load the font: " + file);
    	} else {
    		this.isLoaded = true;
    	}
	}
	
	public final void 
	setSource( 
			final Typeface source ) {
		
		if( source == null ) {
			BitsLog.e("BitsBitmapFont - setSource", "The given Typeface object is Null!");
			throw new NullPointerException("The given Typeface object is Null!");
		}

		this.mSource = source;
	}
	
	public final Typeface 
	getSource( ) {
		
		return this.mSource;
	}
	
	/**
	 * Releases the data of the contained Bitmap.<br>
	 * After a call to this method, the BitsBitmap can't be used anymore.
	 */
	public final void 
	release( ) {

		this.mSource = null;
	}
}
