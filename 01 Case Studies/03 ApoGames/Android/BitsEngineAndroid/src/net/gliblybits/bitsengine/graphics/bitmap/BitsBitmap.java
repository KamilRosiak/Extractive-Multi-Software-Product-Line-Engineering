
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import net.gliblybits.bitsengine.graphics.BitsColor;
import net.gliblybits.bitsengine.graphics.BitsDrawable;
import net.gliblybits.bitsengine.io.BitsIO;
import net.gliblybits.bitsengine.utils.BitsLog;

public final class 
BitsBitmap 
extends BitsDrawable {
	
	public static final int TYPE_RGB = 0;
	public static final int TYPE_ARGB = 1;
	
	public Bitmap mSource = null;
	
	/**
	 * Creates a new BitsBitmap object with an empty android bitmap of given size and type.<br>
	 * 
	 * @param width
	 * @param height
	 * @param type - BitsBitmap.TYPE_RGB or BitsBitmap.TYPE_RGBA
	 */
	public 
	BitsBitmap( 
			final int width, 
			final int height, 
			final int type ) {
		
		super( BitsDrawable.DRAWABLE_TYPE_BITMAP );

		if( width <= 0 || height <= 0 ) {
			BitsLog.e("BitsBitmap - constructor", "The width and height of the bitmap have to be > 0: (" + width + ";" + height + ")");
			throw new IllegalArgumentException("The width and height of the bitmap have to be > 0: (" + width + ";" + height + ")");
		}
		
		if(type == TYPE_ARGB) {
			this.mSource = Bitmap.createBitmap( width, height, Bitmap.Config.ARGB_8888 );
		} else {			
			this.mSource = Bitmap.createBitmap( width, height, Bitmap.Config.RGB_565 );
		}
		
		this.isLoaded = true;
	}

	/**
	 * Creates a new BitsBitmap object with the given android bitmap.<br>
	 * <br>
	 * Use the BitsFactory to load/get android bitmaps easily.<br>
	 * 
	 * @param bitmap - android bitmap
	 */
	public 
	BitsBitmap(
			final Bitmap source ) {
		
		super( BitsDrawable.DRAWABLE_TYPE_BITMAP );
		
		if( source == null ) {
			BitsLog.e("BitsBitmap - constructor", "The given Bitmap object is Null!");
			throw new NullPointerException("The given Bitmap object is Null!");
		}
		
		this.mSource = source;
	}
	
	public final void 
	setSource( 
			final Bitmap source ) {
		
		if( source == null ) {
			BitsLog.e("BitsBitmap - setSource", "The given Bitmap object is Null!");
			throw new NullPointerException("The given Bitmap object is Null!");
		}

		this.mSource = source;
	}
	
	public final Bitmap 
	getSource( ) {
		
		return this.mSource;
	}
	
	public final BitsBitmapGraphics 
	createGraphics( ) {

		return new BitsBitmapGraphics( this.mSource );
	}

	public final int 
	getWidth( ) {

		return this.mSource.getWidth();
	}

	public final int 
	getHeight( ) {

		return this.mSource.getHeight();
	}

	public final int 
	getPixel( 
			final int x,
			final int y ) {

		return this.mSource.getPixel( x, y );
	}
	
	public final void 
	setPixel(
			final int x,
			final int y, 
			final int red, 
			final int green, 
			final int blue, 
			final int alpha ) {

		this.mSource.setPixel(x, y, BitsColor.getARGB(red, green, blue, alpha));
	}
	
	public final void 
	setPixel(
			final int x,
			final int y,
			final int red,
			final int green,
			final int blue ) {
		
		this.setPixel(x, y, red, green, blue, 255);
	}

	public final void 
	setPixel(
			final int x, 
			final int y,
			final float red,
			final float green,
			final float blue, 
			final float alpha ) {

		this.mSource.setPixel(x, y, BitsColor.getARGB(red, green, blue, alpha));
	}

	public final void
	setPixel( 
			final int x,
			final int y,
			final float red,
			final float green,
			final float blue ) {
		
		this.setPixel(x, y, red, green, blue, 1f);
	}
	
	public final void 
	setPixel( 
			final int x,
			final int y, 
			final int color ) {

		this.mSource.setPixel(x, y, color );
	}
	
	public final BitsBitmap 
	scale( 
			final int w, 
			final int h, 
			final boolean doFilter ) {
		
		final Bitmap bitmap = Bitmap.createScaledBitmap( mSource, w, h, doFilter );
		this.release();
		this.mSource = bitmap;
		return this;
	}
	
	/**
	 * Releases the data of the contained Bitmap.<br>
	 * After a call to this method, the BitsBitmap can't be used anymore.
	 */
	public final void 
	release( ) {

		this.mSource.recycle();
	}
	
	public static BitsBitmap 
	makeColorTransparent( 
			final BitsBitmap bitmap, 
			final int color ) {
		
		if( bitmap == null ) {
			BitsLog.e("BitsBitmap - makeColorTransparent", "The given BitsBitmap object is Null!");
			throw new NullPointerException("The given BitsBitmap object is Null!");
		}
		
		if( bitmap.mSource.getConfig() != Bitmap.Config.ARGB_8888 ) {
			BitsLog.e("BitsBitmap - makeColorTransparent", "The given BitsBitmap object has no alpha channel!");
			return bitmap;
		}
		
		for(int y = 0; y < bitmap.getHeight(); ++y) {
			for(int x = 0; x < bitmap.getWidth(); ++x) {
				int argb = bitmap.getPixel(x, y);
		        if((argb | 0xFF000000) == 0) 		        {
		        	bitmap.setPixel(x, y, 0x00FFFFFF & argb);
		        }
		    }
		}

		return bitmap;
	}
	
	public static final void
	save( 
			final BitsBitmap bitmap,
			final String file, 
			final boolean release ) 
	throws IOException {
		
		if( bitmap == null || file == null ) {
			BitsLog.e("BitsBitmap - save", "The given BitsBitmap or String is Null!");
			throw new NullPointerException("The given BitsBitmap or String is Null!");
		}
		
		if( file.equals("") == true ) {
			BitsLog.e("BitsBitmap - save", "The given file String is empty.");
			throw new IllegalArgumentException("The given file String is empty.");
		}
		
		final FileOutputStream out = new FileOutputStream( new File( file ) );
		bitmap.mSource.compress(Bitmap.CompressFormat.PNG, 100, out);
	    out.close();
		if( release == true ) {
			bitmap.release();
		}
	}
	
	public static final void
	savePrivate(
			final BitsBitmap bitmap,
			final String file, 
			final boolean release ) 
	throws IOException {
		
		if( bitmap == null || file == null ) {
			BitsLog.e("BitsBitmap - savePrivate", "The given BitsBitmap or String is Null!");
			throw new NullPointerException("The given BitsBitmap or String is Null!");
		}
		
		if( file.equals("") == true ) {
			BitsLog.e("BitsBitmap - savePrivate", "The given file String is empty.");
			throw new IllegalArgumentException("The given file String is empty.");
		}
		
		final FileOutputStream out = new FileOutputStream( new File( BitsIO.getInstance().getPrivateStorageDir() , file ) );
	    bitmap.mSource.compress(Bitmap.CompressFormat.PNG, 100, out);
	    out.close();
		if( release == true ) {
			bitmap.release();
		}
	}
	
	public static final void
	savePublic( 
			final BitsBitmap bitmap,
			final String file, 
			final boolean release ) 
	throws IOException {
		
		if( bitmap == null || file == null ) {
			BitsLog.e("BitsBitmap - savePublic", "The given BitsBitmap or String is Null!");
			throw new NullPointerException("The given BitsBitmap or String is Null!");
		}
		
		if( file.equals("") == true ) {
			BitsLog.e("BitsBitmap - savePublic", "The given file String is empty.");
			throw new IllegalArgumentException("The given file String is empty.");
		}
		
		if( BitsIO.getInstance().isPublicStorageWritable() == false ) {
			BitsLog.e("BitsBitmap - savePublic", "The public storage dir is not writable: " + file);
			return;			
		}
		
		final FileOutputStream out = new FileOutputStream( new File( BitsIO.getInstance().getPublicStorageDir() , file ) );
	    bitmap.mSource.compress(Bitmap.CompressFormat.PNG, 100, out);
	    out.close();
		if( release == true ) {
			bitmap.release();
		}
	}
}
