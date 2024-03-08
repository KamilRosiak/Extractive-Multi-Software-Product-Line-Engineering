
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

package net.gliblybits.bitsengine.utils;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;

import android.graphics.Bitmap;
import net.gliblybits.bitsengine.graphics.bitmap.BitsBitmap;

public final class 
BitsUtils {

    private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

    public static final String 
    asHex(
    		final byte[] buf ) {
    	
    	if( buf == null ) {
    		BitsLog.e("BitsUtils - asHex", "The given byte array is Null!");
    		throw new NullPointerException("The given byte array is Null!");
    	}
    	
        final char[] chars = new char[2 * buf.length];
        for( int i = 0; i < buf.length; ++i ) {
            chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
            chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
        }
        return new String(chars);
    }
	
    public static final String 
    getHash( 
    		final BitsBitmap bitmap ) {
    	
    	if( bitmap == null ) {
    		BitsLog.e("BitsUtils - getHash", "The given BitsBitmap is Null!");
    		throw new NullPointerException("The given BitsBitmap is Null!");
    	}

		try {
	    	final MessageDigest md5 = MessageDigest.getInstance("MD5");
	    	if( md5 != null ) {
	            final ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	            bitmap.mSource.compress(Bitmap.CompressFormat.PNG, 100, baos);   
	            byte[] b = baos.toByteArray(); 
	            md5.update( b );
	            baos.close();
	            final byte[] hash = md5.digest();
	            return BitsUtils.asHex(hash);
	    	}
		} catch( Exception e ) {
			BitsLog.e( e, "BitsUtils - getBitmapHash", "Unable to determine the MD5 hash.");
			return null;
		}

		return null;
    }
    
    public static final String
    getHash( 
    		final byte[] bytes ) {
    	
    	if( bytes == null ) {
    		BitsLog.e("BitsUtils - getHash", "The given byte array is Null!");
    		throw new NullPointerException("The given byte array is Null!");
    	}
    	
		try {
	    	final MessageDigest md5 = MessageDigest.getInstance("MD5");
	    	if( md5 != null ) {
	            md5.update( bytes );
	            final byte[] hash = md5.digest();
	            return BitsUtils.asHex(hash);
	    	}
		} catch( Exception e ) {
			BitsLog.e( e, "BitsUtils - getHash", "Unable to determine the MD5 hash.");
			return null;
		}

    	return null;
    }
    
	public static final int 
	toLittleEndian( 
			final int value ) {
		
		return ((value & 0x000000ff) << 24) + ((value & 0x0000ff00) << 8) + ((value & 0x00ff0000) >>> 8) + ((value & 0xff000000) >>> 24);
	}
	
	public static final int 
	toBigEndian( 
			final int value ) {
		
	    return ((value & 0xff) << 24) + ((value & 0xff) << 16) + ((value & 0xff) << 8) + (value & 0xff);
	}
	
	public static final char 
	toLittleEndian( 
			final char value ) {
		
	   return (char) ( ((value >> 8) & 0xff) + ((value << 8) & 0xff00) );
	}
	
	public static final short 
	toLittleEndian( 
			final short value ) {
		
	   return (short) ( ((value >> 8) & 0xff) + ((value << 8) & 0xff00) );
	}
	
	@Deprecated
	public static final void 
	setMouseCursor( 
			final BitsBitmap image ) {
		
		BitsLog.w("BitsUtils - setMouseCursor", "This method is not available in the Android version of the BitsEngine!");
	}
	
	@Deprecated
	public static final void
	setWindowIcon( 
			final BitsBitmap window, 
			final BitsBitmap taskbar ) {	
		
		BitsLog.w("BitsUtils - setWindowIcon", "This method is not available in the Android version of the BitsEngine!");
	}
}
