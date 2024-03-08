
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

import java.io.InputStream;
import java.util.ArrayList;

import net.gliblybits.bitsengine.core.BitsApp;
import net.gliblybits.bitsengine.core.BitsGame;
import net.gliblybits.bitsengine.graphics.BitsDrawable;
import net.gliblybits.bitsengine.graphics.bitmap.BitsBitmap;
import net.gliblybits.bitsengine.utils.BitsLog;
import net.gliblybits.bitsengine.utils.BitsUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.opengl.GLES11;
import android.opengl.GLUtils;

public final class 
BitsGLFactory {

    private final ArrayList<BitsGLImage> 		mImageList 				= new ArrayList<BitsGLImage>( );
    private final ArrayList<BitsGLRenderStack> 	mRenderStackList 		= new ArrayList<BitsGLRenderStack>( );
    private final ArrayList<BitsGLFont> 		mFontList 				= new ArrayList<BitsGLFont>( );
    private final ArrayList<BitsDrawable> 		mLoadList				= new ArrayList<BitsDrawable>( );
    private final ArrayList<BitsDrawable> 		mReleaseList			= new ArrayList<BitsDrawable>( );
    private final BitmapFactory.Options 		mBitmapFactoryOptions 	= new BitmapFactory.Options();
    
	private static BitsGLFactory instance = new BitsGLFactory();
	
    private 
    BitsGLFactory( ) {
    	
    	/*
    	 * Important for devices, that needs textures with a power of 2 size.
    	 * (e.g. Galaxy Tab 7)
    	 */
    	this.mBitmapFactoryOptions.inScaled = false;
    }
    
    public static final BitsGLFactory 
    getInstance( ) {
    	
    	return instance;
    }
    
    public static final void 
    resetInstance( ) {
    	
    	/*
    	 * Important for devices, that needs textures with a power of 2 size.
    	 * (e.g. Galaxy Tab 7)
    	 */
    	instance.mBitmapFactoryOptions.inScaled = false;
    	
    	BitsLog.d( "BitsGLFactory - resetInstance", "Resetting the singleton instance..." );
		final ArrayList<BitsGLImage> imageList = instance.mImageList;
		final int size = imageList.size(); 
		for( int i = 0; i < size; i++ ) {
			final BitsGLImage poolImage = imageList.get(i);
            if( poolImage.mTextureId != -1 && 
            	poolImage.isLoaded ) {            	
            	BitsLog.d("BitsGLFactory - resetInstance", "Releasing texture: " + String.valueOf(poolImage.mTextureId));
            	final int[] textureId = new int[1];
        		textureId[0] = poolImage.mTextureId;
        		GLES11.glDeleteTextures(1, textureId, 0);	                
        		poolImage.reset();
            }
        }
		imageList.clear();

		BitsLog.d("BitsGLFactory - resetInstance", "Releasing render stacks...");
		final ArrayList<BitsGLRenderStack> stackList = instance.mRenderStackList;
		final int size2 = stackList.size();
        for( int i = 0; i < size2; i++ ) {
        	final BitsGLRenderStack poolStack = stackList.get(i);
            if( poolStack.mIndexBufferId != -1 && 
            	poolStack.mVertexBufferId != -1 && 
            	poolStack.isLoaded ) {            	
            	BitsLog.d( "BitsGLFactory - resetInstance", "Releasing vertex buffer: " + String.valueOf( poolStack.mVertexBufferId ) );
            	final int[] bufferVertexId = new int[1];
            	bufferVertexId[0]= poolStack.mVertexBufferId;
                GLES11.glDeleteBuffers( 1, bufferVertexId, 0 );
                BitsLog.d( "BitsGLFactory - resetInstance", "Releasing index buffer: " + String.valueOf( poolStack.mIndexBufferId ) );
            	final int[] bufferIndexId = new int[1];
            	bufferIndexId[0] = poolStack.mIndexBufferId;
                GLES11.glDeleteBuffers( 1, bufferIndexId, 0 );
                poolStack.reset();
            }
        }
        stackList.clear();
        
		final ArrayList<BitsGLFont> fontList = instance.mFontList;
		final int size3 = fontList.size();
        for( int i = 0; i < size3; i++ ) {
        	final BitsGLFont poolFont = fontList.get( i );
            if( poolFont.mTextureId != -1 && 
            	poolFont.isLoaded == true ) {            	
            	BitsLog.d("BitsGLFactory - resetInstance", "Releasing font texture: " + String.valueOf(poolFont.mTextureId));
            	final int[] textureId = new int[1];
        		textureId[0] = poolFont.mTextureId;
        		poolFont.reset();
                GLES11.glDeleteTextures(1, textureId, 0);
            }
        }
        fontList.clear();
    }
    
    /**
     * This method creates an android bitmap via a given byte array.
     * 
     * @param bytes
     * @param offset
     * @param length
     * @return An android bitmap object.
     */
    public final BitsBitmap 
    getBitmap( 
    		final byte[] bytes,
    		final int offset, 
    		final int length ) {
    	
    	if( bytes == null ) {
    		BitsLog.e("BitsGLFactory - getBitmap", "The given byte array is Null!");
    		throw new NullPointerException("The given byte array is Null!");
    	}
    	
		if( this.mBitmapFactoryOptions.inJustDecodeBounds == true ) {
			BitsLog.d( "BitsGLFactory - getBitmap", "Loading bitmaps size via byte array." );
			BitmapFactory.decodeByteArray( 
					bytes, 
					offset, 
					length, 
					this.mBitmapFactoryOptions );
			
			return null;
		} else {
	    	BitsLog.d( "BitsGLFactory - getBitmap", "Loading bitmap via byte array." );
			final BitsBitmap bitmap =  new BitsBitmap( BitmapFactory.decodeByteArray( 
					bytes, 
					offset, 
					length, 
					this.mBitmapFactoryOptions ) );
			
			return bitmap;
		}
    }
    
    /**
     * This method loads an image resource that is located in the "res" folder of the android project.<br>
     * To specify a resource file you must use the R-class (e.g. "R.drawable.image").
     * 
     * @param resourceId
     * @return An android bitmap object.
     */
    public final BitsBitmap 
    getBitmap( 
    		final int resourceId ) {
    	
		if( this.mBitmapFactoryOptions.inJustDecodeBounds == true ) {
			BitsLog.d( "BitsGLFactory - getBitmap", "Loading resource bitmaps size: " + String.valueOf( resourceId ) );
			BitmapFactory.decodeResource(
					BitsApp.sAppContext.getResources( ),
					resourceId,
					this.mBitmapFactoryOptions );
			
			return null;
		} else {
	    	BitsLog.d( "BitsGLFactory - getBitmap", "Loading resource bitmap: " + String.valueOf( resourceId ) );
			final BitsBitmap bitmap = new BitsBitmap( BitmapFactory.decodeResource(
					BitsApp.sAppContext.getResources( ),
					resourceId,
					this.mBitmapFactoryOptions ) );
			
			return bitmap;
		}
    }

    /**
     * This method loads an image file that can be either located on the SD card (or any other readable location)<br>
     * or inside a class path (e.g. inside an embedded jar file).<br>
     * If the image file is located inside a class path, you must add the prefix "?" to the "file" String.<br>
     * <br>
     * The loaded image will be returned as an android bitmap, that can be used to create a new BitsBitmap.<br>
     * 
     * @param file
     * @param isClassPath
     * @return An android bitmap object.
     */
    public final BitsBitmap 
    getBitmap( 
    		final String file ) {
    	
    	if( file == null ) {
    		BitsLog.e("BitsGLFactory - getBitmap", "The file String is Null!");
    		throw new NullPointerException("The given file String is Null!");
    	}
    	
    	if( file.equals("") == true ) {
    		BitsLog.e("BitsGLFactory - getBitmap", "The file String is empty!");
    		throw new IllegalArgumentException("The given file String is empty!");
    	}
    	
    	BitsBitmap bitmap = null;
    	
    	if( !file.startsWith( "?" ) ) {
    		if( this.mBitmapFactoryOptions.inJustDecodeBounds == true ) {
    			BitsLog.d( "BitsGLFactory - getBitmap", "Loading file bitmaps size: " + file );
    			BitmapFactory.decodeFile( file, this.mBitmapFactoryOptions );
    		} else {
    			BitsLog.d( "BitsGLFactory - getBitmap", "Loading file bitmap: " + file );
    			bitmap = new BitsBitmap( BitmapFactory.decodeFile(
    					file,
    					this.mBitmapFactoryOptions ) );
    		}
    	} else {
    	    InputStream is = getClass().getClassLoader().getResourceAsStream(file.substring(1));
    	    if( is == null ) {
    	    	BitsLog.e( "BitsGLFactory - getBitmap", "The file does not exist in the classpath: " + file );
    	    	throw new NullPointerException("The file does not exist in the classpath: " + file);
    	    }

	    	if( this.mBitmapFactoryOptions.inJustDecodeBounds == true ) {
	    		BitsLog.d( "BitsGLFactory - getBitmap", "Loading classpath bitmaps size: " + file.substring(1) );
	    		BitmapFactory.decodeStream( is, null, this.mBitmapFactoryOptions );
	    	} else {
	    		BitsLog.d( "BitsGLFactory - getBitmap", "Loading classpath bitmap: " + file.substring(1) );
	    		bitmap = new BitsBitmap( BitmapFactory.decodeStream( is, null, this.mBitmapFactoryOptions ) );
	    	}
    	}

    	return bitmap;
    }
    
    private final void
    setImageWH(
    		final BitsGLImage image ) {
    	
    	if( image == null ) {
    		BitsLog.e("BitsGLFactory - setImageWH", "The given BitsGLImage is Null!");
    		throw new NullPointerException("The given BitsGLImage is Null!");
    	}
    	
        //pre-calculate the texture size
        if(image.mResourceId > -1) {
    		this.mBitmapFactoryOptions.inJustDecodeBounds = true;
    		this.getBitmap(image.mResourceId);
    		this.mBitmapFactoryOptions.inJustDecodeBounds = false;
    		image.mWidth = this.mBitmapFactoryOptions.outWidth;
    		image.mHeight = this.mBitmapFactoryOptions.outHeight;
			BitsLog.d("BitsGLFactory - setImageWH", "Image size: " + image.mWidth + "x" + image.mHeight );
        } else {
        	if(image.mBitmap != null) {
           		try {
    				image.mBitmapHash = BitsUtils.getHash( image.mBitmap );
    			} catch (Exception e) {
    				BitsLog.e( e, "BitsGLFactory - setImageWH", "Unable to calculate the MD5 hash.");
    			}
	   			image.mWidth = image.mBitmap.getWidth( );
	   			image.mHeight = image.mBitmap.getHeight( );
    			BitsLog.d("BitsGLFactory - setImageWH", "Image size: " + image.mWidth + "x" + image.mHeight );
        	} else {
        		if(image.mFile != null) {
        			this.mBitmapFactoryOptions.inJustDecodeBounds = true;
        			this.getBitmap(image.mFile);
        			this.mBitmapFactoryOptions.inJustDecodeBounds = false;
        			image.mWidth = this.mBitmapFactoryOptions.outWidth;
        			image.mHeight = this.mBitmapFactoryOptions.outHeight;
        			BitsLog.d("BitsGLFactory - setImageWH", "Image size: " + image.mWidth + "x" + image.mHeight );
        		} else {
        			BitsLog.e("BitsGLFactory - setImageWH", "The given BitsGLImage does not contain a resource ID, bitmap or file string!");
        			throw new IllegalArgumentException("The given BitsGLImage does not contain a resource ID, bitmap or file string!");
        		}
        	}
        }
    }
    
    private final void
    setFontWH(
    		final BitsGLFont font ) {
    	
    	if( font == null ) {
    		BitsLog.e("BitsGLFactory - setFontWH", "The given BitsGLFont is Null!");
    		throw new NullPointerException("The given BitsGLFont is Null!");
    	}
    	
		this.mBitmapFactoryOptions.inJustDecodeBounds = true;
		this.getBitmap(font.mFile);
		this.mBitmapFactoryOptions.inJustDecodeBounds = false;
		font.mTextureSize = this.mBitmapFactoryOptions.outWidth;
		BitsLog.d("BitsGLFactory - setFontWH", "Font texture size: " + font.mTextureSize );
    }
    
    public final BitsGLFont 
    getFont( 
    		final String fontFile, 
    		final int fontSize,
    		final int fontDescent,
    		final int filterMode,
    		final boolean mark ) {
    	
    	if( fontFile == null ) {
    		BitsLog.e("BitsGLFactory - getFont", "The given file String is Null!");
    		throw new NullPointerException("The given file String is Null!");
    	}
    	
    	if( fontFile.equals("") == true ) {
    		BitsLog.e("BitsGLFactory - getFont", "The given file String is empty!");
    		throw new IllegalArgumentException("The given file String is empty!");
    	}
    	
    	if( fontSize <= 0 ) {
    		BitsLog.e("BitsGLFactory - getFont", "The given font size must be > 0!");
    		throw new IllegalArgumentException("The given font size must be > 0!");
    	}
    	
    	if( filterMode < BitsGLFont.FILTER_NEAREST || filterMode > BitsGLFont.FILTER_LINEAR ) {
    		BitsLog.e("BitsGLFactory - getFont", "The given filter mode must be BitsGLFont.FILTER_NEAREST or BitsGLFont.FILTER_LINEAR!");
    		throw new IllegalArgumentException("The given filter mode must be BitsGLFont.FILTER_NEAREST or BitsGLFont.FILTER_LINEAR");
    	}
    	
    	return this.getFont(
    			fontFile, 
    			0,
    			null, 
    			fontSize,
    			-1, 
    			-1,
    			0, 
    			fontDescent,
    			filterMode,
    			mark,
    			BitsGLFont.TYPE_FILE );
    }
    
    public final BitsGLFont 
    getFont( 
    		final String fontFile, 
    		final int fontSize,
    		final int fontDescent,
    		final boolean mark ) {
    	
    	if( fontFile == null ) {
    		BitsLog.e("BitsGLFactory - getFont", "The given file String is Null!");
    		throw new NullPointerException("The given file String is Null!");
    	}
    	
    	if( fontFile.equals("") == true ) {
    		BitsLog.e("BitsGLFactory - getFont", "The given file String is empty!");
    		throw new IllegalArgumentException("The given file String is empty!");
    	}
    	
    	if( fontSize <= 0 ) {
    		BitsLog.e("BitsGLFactory - getFont", "The given font size must be > 0!");
    		throw new IllegalArgumentException("The given font size must be > 0!");
    	}
    	
    	return this.getFont(
    			fontFile, 
    			0,
    			null, 
    			fontSize,
    			-1, 
    			-1,
    			0, 
    			fontDescent,
    			BitsGLFont.FILTER_NEAREST,
    			mark,
    			BitsGLFont.TYPE_FILE );
    }
    
    public final BitsGLFont
    getFont(
    		final String fontBitmapFile,
    		final int textureSize,
    		final float[] fontCharWidths,
    		final int fontSize,
    		final int cellWidth,
    		final int cellHeight,
    		final int tweakWidth,
    		final int fontDescent,
    		final int filterMode,
    		final boolean mark ) {
    	
    	if( fontBitmapFile == null ) {
    		BitsLog.e("BitsGLFactory - getFont", "The given file String is Null!");
    		throw new NullPointerException("The given file String is Null!");
    	}
    	
    	if( fontBitmapFile.equals("") == true ) {
    		BitsLog.e("BitsGLFactory - getFont", "The given file String is empty!");
    		throw new IllegalArgumentException("The given file String is empty!");    		
    	}
    	
    	if( fontSize <= 0 || textureSize <= 0 || cellWidth <= 0 || cellHeight <= 0 ) {
    		BitsLog.e("BitsGLFactory - getFont", "The given font size, texture size, cell width and cell height must be > 0!");
    		throw new IllegalArgumentException("The given font size, texture size, cell width and cell height must be > 0!");
    	}
    	
    	if( filterMode < BitsGLFont.FILTER_NEAREST || filterMode > BitsGLFont.FILTER_LINEAR ) {
    		BitsLog.e("BitsGLFactory - getFont", "The given filter mode must be BitsGLFont.FILTER_NEAREST or BitsGLFont.FILTER_LINEAR!");
    		throw new IllegalArgumentException("The given filter mode must be BitsGLFont.FILTER_NEAREST or BitsGLFont.FILTER_LINEAR");
    	}
    	
    	if( fontCharWidths == null ) {
    		BitsLog.e("BitsGLFactory - getFont", "The given char width array is Null!");
    		throw new IllegalArgumentException("The given char width array is Null!");
    	}
    	
    	return this.getFont(
    			fontBitmapFile, 
    			textureSize,
    			fontCharWidths,
    			fontSize,
    			cellWidth, 
    			cellHeight,
    			tweakWidth, 
    			fontDescent,
    			filterMode,
    			mark, 
    			BitsGLFont.TYPE_BITMAP );
    }
    
    public final BitsGLFont
    getFont(
    		final String fontBitmapFile,
    		final int textureSize,
    		final float[] fontCharWidths,
    		final int fontSize,
    		final int cellWidth,
    		final int cellHeight,
    		final int tweakWidth,
    		final int fontDescent,
    		final boolean mark ) {
    	
    	if( fontBitmapFile == null ) {
    		BitsLog.e("BitsGLFactory - getFont", "The given file String is Null!");
    		throw new NullPointerException("The given file String is Null!");
    	}
    	
    	if( fontBitmapFile.equals("") == true ) {
    		BitsLog.e("BitsGLFactory - getFont", "The given file String is empty!");
    		throw new IllegalArgumentException("The given file String is empty!");    		
    	}
    	
    	if( fontSize <= 0 || textureSize <= 0 || cellWidth <= 0 || cellHeight <= 0 ) {
    		BitsLog.e("BitsGLFactory - getFont", "The given font size, texture size, cell width and cell height must be > 0!");
    		throw new IllegalArgumentException("The given font size, texture size, cell width and cell height must be > 0!");
    	}
    	
    	if( fontCharWidths == null ) {
    		BitsLog.e("BitsGLFactory - getFont", "The given char width array is Null!");
    		throw new IllegalArgumentException("The given char width array is Null!");
    	}
    	
    	return this.getFont(
    			fontBitmapFile, 
    			textureSize,
    			fontCharWidths,
    			fontSize,
    			cellWidth, 
    			cellHeight,
    			tweakWidth, 
    			fontDescent,
    			BitsGLFont.FILTER_NEAREST,
    			mark, 
    			BitsGLFont.TYPE_BITMAP );
    }
    
    /**
     * 
     * @param fontFile - Asset or file on the SD-Card
     * @return
     */
    private final BitsGLFont 
    getFont( 
    		final String fontFile, 
    		final int textureSize,
    		final float[] fontCharWidths,
    		final int fontSize,
    		final int cellWidth, 
    		final int cellHeight, 
    		final int tweakWidth,
    		final int fontDescent,
    		final int filterMode,
    		final boolean mark,
    		final int type ) {
    	
    	BitsGLFont font = null;
    	BitsGLFont emptyFont = null;

    	BitsLog.d("BitsGLFactory - getFont", "Searching BitsFont in font pool...");
		final ArrayList<BitsGLFont> fontList = this.mFontList;
		final int size = fontList.size();
        for( int i = 0; i < size; i++ ) {
        	final BitsGLFont poolFont = fontList.get( i );
        	/*
        	 * Search for an empty font.
        	 */
    		if( poolFont.isLoaded	== false &&
        		poolFont.mFile   	== null &&
        		poolFont.mFontSize  == -1 &&
        		poolFont.mBitmap    == null && 
        		poolFont.mMark		== 0 ) { //if NOT marked
        			emptyFont = poolFont;
        	}

    		/*
    		 * Search for a font that is already loaded. 
    		 */
    		if( poolFont.mFile 	!= null && 
        		poolFont.mMark  == 0 && //if NOT marked
        		poolFont.mFontSize != -1 &&
        		fontFile 		!= null ) {
            	if( poolFont.mFile.equals( fontFile ) && 
        			poolFont.mFontSize == fontSize ) {
        			BitsLog.d("BitsGLFactory - getFont", "...found via file name and size. BitsFont already loaded.");
        		   	font = poolFont;
        		   	break;
        		}
        	}
    	}
    	
        /*
         * No BitsFont found.
         */
        if( font == null ) {
        	if( emptyFont == null ) { //if there is no empty font in the font list
            	BitsLog.d("BitsGLFactory - getFont", "...not found. Using a new BitsFont.");
        		font = new BitsGLFont( ); //create a new font object
        		this.mFontList.add(font); //and add it to the list
        	} else { //if there is an empty font in the font list
            	BitsLog.d("BitsGLFactory - getFont", "...not found. Using an empty BitsFont.");
        		font = emptyFont;
        	}
        }
        
        font.mFile 			= fontFile;
        font.mFontSize 		= fontSize;
        font.mFontDescent 	= fontDescent;
        font.mType 			= type;
        font.mFilterMode	= filterMode;
        
        switch( type ) {
        	case BitsGLFont.TYPE_FILE: {
            	this.createFontBitmap( font );
            	
                // setup the array of character texture regions
                int x = 0; // Initialize X
                int y = 0; // Initialize Y
                for ( int c = 0; c < BitsGLFont.CHAR_CNT; c++ )  { // FOR Each Character (On Texture)
                   font.mCharTextureRegions[c] = new BitsGLTextureRegion( x, y, font.mCharCellWidth-1, font.mCharCellHeight-1 ); // Create Region for Character
                   x += font.mCharCellWidth; // Move to Next Char (Cell)
                   if ( x + font.mCharCellWidth > font.mTextureSize )  {
                      x = 0; // Reset X Position to Start
                      y += font.mCharCellHeight; // Move to Next Row (Cell)
                   }
                }
        		break; 
        	}
        	case BitsGLFont.TYPE_BITMAP: {
            	font.mFontHeight = cellHeight;
            	font.mCharCellHeight = cellHeight; 
            	font.mMaxCharHeight = cellHeight;
            	font.mCharCellWidth = cellWidth;
            	font.mMaxCharWidth = cellWidth;

            	this.setFontWH(font);

        		// setup the array of character widths
        		if( fontCharWidths.length != BitsGLFont.CHAR_CNT ) {
        			BitsLog.e("BitsGLFactory - getFont", "The given fontCharWidths-Arrays size is not 96!");
        		} else {
        			for( int i = 0; i < BitsGLFont.CHAR_CNT; i++ ) {
        				font.mCharWidths[i] = fontCharWidths[i];
        			}
        		}
	            // setup the array of character texture regions
	            int x = 0; // Initialize X
	            int y = fontDescent; // Initialize Y
	            for ( int c = 0; c < BitsGLFont.CHAR_CNT; c++ )  { // FOR Each Character (On Texture)
	               font.mCharTextureRegions[c] = new BitsGLTextureRegion( x, y, font.mCharCellWidth, font.mCharCellHeight ); // Create Region for Character
	               x += font.mCharCellWidth; // Move to Next Char (Cell)
	               if ( x + font.mCharCellWidth > font.mTextureSize )  {
	                  x = 0; // Reset X Position to Start
	                  y += font.mCharCellHeight; // Move to Next Row (Cell)
	               }
	            }
        		break;
        	}
        }

        if( mark == true ) {
        	this.markForLoading(font);
        }
        
        return font;
    }
    
    private final void 
    createFontBitmap(
    		final BitsGLFont font ) {
    	
    	Typeface tf = null;
    	
    	try {
    		tf = Typeface.createFromAsset( BitsApp.sAppContext.getAssets( ), font.mFile ); // Create the Typeface from asset Font File
    	} catch( Exception e ) {
    		try {
    			tf = Typeface.createFromFile( font.mFile );
    		} catch( Exception ee ) {
    			BitsLog.e( ee, "BitsGLFactory - createFontBitmap", "Font not found. Unable to load the font: " + font.mFile );
    			throw new RuntimeException("Font not found. Unable to load the font: " + font.mFile);
    		}
    	}
        
        final Paint paint = new Paint(); // Create Android Paint Instance
        paint.setTextSize( font.mFontSize ); // Set Text Size
        paint.setColor( 0xffffffff ); // Set ARGB (White, Opaque)
        paint.setTypeface( tf ); // Set Typeface
        
//        paint.setAntiAlias(true);
//        paint.setFilterBitmap(true);
//        paint.setDither(true);

        // get font metrics
        final Paint.FontMetrics fm = paint.getFontMetrics(); // Get Font Metrics
        font.mFontHeight = (float)Math.ceil( Math.abs( fm.bottom ) + Math.abs( fm.top ) ); // Calculate Font Height

        // determine the width of each character (including unknown character)
        // also determine the maximum character width
        final char[] s = new char[2]; // Create Character Array
        font.mMaxCharWidth = font.mMaxCharHeight = 0; // Reset Character Width/Height Maximums
        final float[] w = new float[2]; // Working Width Value
        int cnt = 0; // Array Counter
        for ( char c = BitsGLFont.CHAR_START; c <= BitsGLFont.CHAR_END; c++ )  { // FOR Each Character
           s[0] = c; // Set Character
           paint.getTextWidths( s, 0, 1, w ); // Get Character Bounds
           font.mCharWidths[cnt] = w[0]; // Get Width
           if ( font.mCharWidths[cnt] > font.mMaxCharWidth ) // IF Width Larger Than Max Width
        	   font.mMaxCharWidth = font.mCharWidths[cnt]; // Save New Max Width
           cnt++; // Advance Array Counter
        }
        s[0] = BitsGLFont.CHAR_NONE; // Set Unknown Character
        paint.getTextWidths( s, 0, 1, w ); // Get Character Bounds
        font.mCharWidths[cnt] = w[0]; // Get Width
        if ( font.mCharWidths[cnt] > font.mMaxCharWidth ) // IF Width Larger Than Max Width
        	font.mMaxCharWidth = font.mCharWidths[cnt]; // Save New Max Width
        cnt++; // Advance Array Counter

        // set character height to font height
        font.mMaxCharHeight = font.mFontHeight; // Set Character Height

        // find the maximum size, validate, and setup cell sizes
        font.mCharCellWidth = (int)font.mMaxCharWidth;  // Set Cell Width
        font.mCharCellHeight = (int)font.mMaxCharHeight;  // Set Cell Height
        
        final int maxSize = font.mCharCellWidth > font.mCharCellHeight ? font.mCharCellWidth : font.mCharCellHeight;  // Save Max Size (Width/Height)
        if ( maxSize < BitsGLFont.SIZE_MIN || maxSize > BitsGLFont.SIZE_MAX ) {  // IF Maximum Size Outside Valid Bounds
        	BitsLog.e( "BitsGLGraphicsFactory - loadFont", "The font texture is larger than the max. size: (max -> font) (" + maxSize + "->" + BitsGLFont.SIZE_MAX + ")");
        	return;
        }

        // set texture size based on max font size (width or height)
        // NOTE: these values are fixed, based on the defined characters. when
        // changing start/end characters (CHAR_START/CHAR_END) this will need adjustment too!
        if ( maxSize <= 24 )                            // IF Max Size is 18 or Less
           font.mTextureSize = 256;                           // Set 256 Texture Size
        else if ( maxSize <= 40 )                       // ELSE IF Max Size is 40 or Less
        	font.mTextureSize = 512;                           // Set 512 Texture Size
        else if ( maxSize <= 80 )                       // ELSE IF Max Size is 80 or Less
        	font.mTextureSize = 1024;                          // Set 1024 Texture Size
        else                                            // ELSE IF Max Size is Larger Than 80 (and Less than FONT_SIZE_MAX)
        	font.mTextureSize = 2048;                          // Set 2048 Texture Size

        // create an empty bitmap (alpha only)
        final Bitmap bitmap = Bitmap.createBitmap( font.mTextureSize, font.mTextureSize, Bitmap.Config.ARGB_8888 ); // Create Bitmap
        final Canvas canvas = new Canvas( bitmap ); // Create Canvas for Rendering to Bitmap
        bitmap.eraseColor( 0x00000000 ); // Set Transparent Background (ARGB)

        // render each of the characters to the canvas (ie. build the font map)
        float x = 0; // Set Start Position (X)
        float y = ( font.mCharCellHeight - 1 ) - font.mFontDescent;  // Set Start Position (Y)
        for ( char c = BitsGLFont.CHAR_START; c <= BitsGLFont.CHAR_END; c++ )  {  // FOR Each Character
        	s[0] = c; // Set Character to Draw
        	
        	canvas.drawText( s, 0, 1, x, y, paint ); // Draw Character
        	x += font.mCharCellWidth; // Move to Next Character
        	if ( ( x + font.mCharCellWidth ) > font.mTextureSize )  {  // IF End of Line Reached
        		x = 0; // Set X for New Row
        		y += font.mCharCellHeight; // Move Down a Row
        	}
        }
        s[0] = BitsGLFont.CHAR_NONE; // Set Character to Use for NONE
        canvas.drawText( s, 0, 1, x, y, paint ); // Draw Character
        
        font.mBitmap = new BitsBitmap( bitmap );
    }
        
    public final BitsGLRenderStack 
    getRenderStack( 
    		final int stackSize,
    		final boolean mark ) {
    	
    	if( stackSize <= 0 ) {
    		BitsLog.e("BitsGLFactory - getRenderStack", "The given stackSize must be > 0!");
    		throw new IllegalArgumentException("The given stackSize must be > 0!");
    	}
    	
    	BitsLog.d("BitsGLFactory - getRenderStack", "Searching BitsRenderStack in the render stack pool...");
    	BitsGLRenderStack imageStack = null;

    	final ArrayList<BitsGLRenderStack> stackList = this.mRenderStackList;
    	final int size = stackList.size();
    	for(int i = 0; i < size; i++) {
    		final BitsGLRenderStack poolStack = stackList.get(i);
    		if( poolStack.mVertexBuffer 	== null 	&& 
    			poolStack.mIndexBuffer 		== null 	&& 
    			poolStack.isLoaded 			== false 	&&
    			poolStack.mIndexBufferId 	== -1 		&&
    			poolStack.mVertexBufferId 	== -1 		&&
    			poolStack.mMark 			== 0 ) { //if NOT marked
    			BitsLog.d("BitsGLFactory - getRenderStack", "...found.");
    			imageStack = poolStack;
    			break;
    		}
    	}
    	
        if( imageStack == null ) { //if there is no empty render stack create a new one
        	imageStack = new BitsGLRenderStack();        	
        }
    	imageStack.init( stackSize );

    	if( mark == true ) {
    		this.markForLoading(imageStack);
    	}
    	
        return imageStack;
    }
    
    public final BitsGLImage 
    getImage( 
    		final int resourceID,
    		final int filterMode,
    		final boolean mark) {
    	
    	if( filterMode < BitsGLImage.FILTER_NEAREST || filterMode > BitsGLImage.FILTER_LINEAR ) {
    		BitsLog.e("BitsGLFactory - getImage", "The given filter mode must be BitsGLImage.FILTER_NEAREST or BitsGLImage.FILTER_LINEAR!");
    		throw new IllegalArgumentException("The given filter mode must be BitsGLImage.FILTER_NEAREST or BitsGLImage.FILTER_LINEAR!");
    	}
    	
    	return this.getImage( resourceID, null, null, filterMode, mark, false );
    }
    
    public final BitsGLImage 
    getImage( 
    		final int resourceID,
    		final boolean mark ) {
    	
    	return this.getImage( resourceID, null, null, BitsGLImage.FILTER_NEAREST, mark, false );
    }
    
    public final BitsGLImage 
    getImage( 
    		final String file,
    		final int filterMode,
    		final boolean mark ) {
    	
    	if( file == null ) {
    		BitsLog.e("BitsGLFactory - getImage", "The given file String is Null!");
    		throw new NullPointerException("The given file String is Null!");
    	}
    	
    	if( file.equals("") == true ) {
    		BitsLog.e("BitsGLFactory - getImage", "The given file String is empty!");
    		throw new IllegalArgumentException("The given file String is empty!");
    	}
    	
    	if( filterMode < BitsGLImage.FILTER_NEAREST || filterMode > BitsGLImage.FILTER_LINEAR ) {
    		BitsLog.e("BitsGLFactory - getImage", "The given filter mode must be BitsGLImage.FILTER_NEAREST or BitsGLImage.FILTER_LINEAR!");
    		throw new IllegalArgumentException("The given filter mode must be BitsGLImage.FILTER_NEAREST or BitsGLImage.FILTER_LINEAR!");
    	}
    	
    	return this.getImage( -1, null, file, filterMode, mark, false );
    }
    
    public final BitsGLImage 
    getImage( 
    		final String file,
    		final boolean mark ) {
    	
    	if( file == null ) {
    		BitsLog.e("BitsGLFactory - getImage", "The given file String is Null!");
    		throw new NullPointerException("The given file String is Null!");
    	}
    	
    	if( file.equals("") == true ) {
    		BitsLog.e("BitsGLFactory - getImage", "The given file String is empty!");
    		throw new IllegalArgumentException("The given file String is empty!");
    	}
    	
    	return this.getImage( -1, null, file, BitsGLImage.FILTER_NEAREST, mark, false );
    }
    
    public final BitsGLImage 
    getImage( 
    		final BitsBitmap bitmap,
    		final int filterMode,
    		final boolean mark ) {
    	
    	if( bitmap == null ) {
    		BitsLog.e("BitsGLFactory - getImage", "The given BitsBitmap is Null!");
    		throw new NullPointerException("The given BitsBitmap is Null!");
    	}
    	
    	if( filterMode < BitsGLImage.FILTER_NEAREST || filterMode > BitsGLImage.FILTER_LINEAR ) {
    		BitsLog.e("BitsGLFactory - getImage", "The given filter mode must be BitsGLImage.FILTER_NEAREST or BitsGLImage.FILTER_LINEAR!");
    		throw new IllegalArgumentException("The given filter mode must be BitsGLImage.FILTER_NEAREST or BitsGLImage.FILTER_LINEAR!");
    	}
    	
    	return this.getImage( -1 , bitmap, null, filterMode, mark, false );
    }
    
    public final BitsGLImage 
    getImage( 
    		final BitsBitmap bitmap,
    		final int filterMode,
    		final boolean mark,
    		final boolean keepBitmap ) {
    	
    	if( bitmap == null ) {
    		BitsLog.e("BitsGLFactory - getImage", "The given BitsBitmap is Null!");
    		throw new NullPointerException("The given BitsBitmap is Null!");
    	}
    	
    	if( filterMode < BitsGLImage.FILTER_NEAREST || filterMode > BitsGLImage.FILTER_LINEAR ) {
    		BitsLog.e("BitsGLFactory - getImage", "The given filter mode must be BitsGLImage.FILTER_NEAREST or BitsGLImage.FILTER_LINEAR!");
    		throw new IllegalArgumentException("The given filter mode must be BitsGLImage.FILTER_NEAREST or BitsGLImage.FILTER_LINEAR!");
    	}
    	
    	return this.getImage( -1 , bitmap, null, filterMode, mark, keepBitmap );
    }
    
    public final BitsGLImage 
    getImage( 
    		final BitsBitmap bitmap,
    		final boolean mark ) {
    	
    	if( bitmap == null ) {
    		BitsLog.e("BitsGLFactory - getImage", "The given BitsBitmap is Null!");
    		throw new NullPointerException("The given BitsBitmap is Null!");
    	}
    	
    	return this.getImage( -1 , bitmap, null, BitsGLImage.FILTER_NEAREST, mark, false );
    }
    
    public final BitsGLImage 
    getImage( 
    		final BitsBitmap bitmap,
    		final boolean mark,
    		final boolean keepBitmap ) {
    	
    	if( bitmap == null ) {
    		BitsLog.e("BitsGLFactory - getImage", "The given BitsBitmap is Null!");
    		throw new NullPointerException("The given BitsBitmap is Null!");
    	}
    	
    	return this.getImage( -1 , bitmap, null, BitsGLImage.FILTER_NEAREST, mark, keepBitmap );
    }

    private final BitsGLImage 
    getImage( 
    		final int resourceId,
    		final BitsBitmap bitmap,
    		final String file,
    		final int filterMode,
    		final boolean mark,
    		final boolean keepBitmap ) {
    	
    	BitsLog.d("BitsGLFactory - getImage", "Searching BitsImage in the image pool...");
    	BitsGLImage image = null;
    	BitsGLImage emptyImage = null;
    	String hash = null;
    	if( bitmap != null ) {
    		try {
				hash = BitsUtils.getHash( bitmap );
			} catch (Exception e) {
				BitsLog.e( e, "BitsGLFactory - getImage", "Unable to calculate the MD5 hash.");
			}
    		BitsLog.d( "BitsGLFactory - getImage", "The hash of the Bitmap is: " + hash );
    	}

    	final ArrayList<BitsGLImage> imageList = this.mImageList;
    	final int size = imageList.size();
    	for( int i = 0; i < size; i++ ) {
    		final BitsGLImage poolImage = imageList.get(i);
    		
    		/*
    		 * Search for empty BitsImage
    		 */
    		if( poolImage.isLoaded    	== false &&
        		poolImage.mResourceId 	== -1    &&
        		poolImage.mBitmap  		== null  &&
        		poolImage.mFile       	== null  &&
    			poolImage.mMark 		== 0 ) { //if NOT marked
        		emptyImage = poolImage;
       		}
    		
    		/*
    		 * Search for ResourceID
    		 */
	    	if( resourceId != -1 ) {
	    		if( poolImage.mResourceId != -1  &&
		        	poolImage.mMark == 0 ) {
	    			if( poolImage.mResourceId == resourceId ) { //if the resource ids are equal -> same bitmap
	    				BitsLog.d( "BitsGraphicsFactory - getImage", resourceId + "...found. Bitmap already loaded: " + resourceId );
	    				image = poolImage;
	    			}
	    		}
	    	}

    		/*
    		 * Search for Bitmap
    		 */
	    	if( bitmap != null ) {
	    		if( poolImage.mBitmap != null &&
	        		poolImage.mMark	  == 0 ) { //if NOT marked
	    			if( poolImage.mBitmapHash.equals(hash) ) { //if the hashes are equal -> same bitmap
	    				BitsLog.d( "BitsGLFactory - getImage", "...found. Bitmap already loaded: " + hash );
	    				image = poolImage;
	    			}
	    		}
	    	}

	    	/*
	    	 * Search for File
	    	 */
	    	if( file != null ) {
        		if( poolImage.mFile != null &&
            		poolImage.mMark == 0 ) { //if NOT marked
        			if( poolImage.mFile.equals( file ) ) {
        				BitsLog.d( "BitsGLFactory - getImage", "...found. File already loaded: " + file  );
        				image = poolImage;
        			}
        		}
	    	}
    	}
    	
        if( image == null ) {
        	if( emptyImage == null ) { //if there is no empty image in the image list
            	BitsLog.d( "BitsGLFactory - getImage", "...not found. Using a new BitsImage." );
        		image = new BitsGLImage( ); //create a new image
        		this.mImageList.add( image ); //and add it to the image list
        	} else { //if there is an empty image in the image list
            	BitsLog.d( "BitsGLFactory - getImage", "...not found. Using an empty BitsImage." );
        		image = emptyImage;
        	}
        }
        
        image.mResourceId 	= resourceId;
        image.mBitmap		= bitmap;
        image.mFile 		= file;
        image.mFilterMode	= filterMode;
        image.mKeepBitmap   = keepBitmap;

        this.setImageWH(image);
        
    	if( mark == true ) {
    		this.markForLoading(image);
    	}

        return image;
    }
    
    /**
     * This method will mark a resource to be loaded later at a safe point in the BitsOpenGLRenderer.<br>
     * 
     * @param drawable - a resource that will be loaded at a safe point
     */
    public final void 
    markForLoading( 
    		final BitsDrawable drawable ) {
    	
    	if( drawable == null ) {
    		BitsLog.w("BitsGLFactory - markForLoading", "The given BitsDrawable is NULL!");
    		return;
    	}
    	
    	if( drawable.mMark != 0 ) {
    		BitsLog.w("BitsGLFactory - markForLoading", "The given BitsDrawable is already marked!");
    		return;
    	}
    	
    	if( drawable.isLoaded == true ) {
    		BitsLog.w("BitsGLFactory - markForLoading", "The given BitsDrawable is already loaded!");
    		return;
    	}
    	
    	if( drawable.mType > BitsDrawable.DRAWABLE_TYPE_BITMAP ) {
    		BitsLog.w("BitsGLFactory - markForLoading", "The given BitsDrawable is not a BitsImage/BitsFont/BitsRenderStack!");
    		return;
    	}
    	
		drawable.mMark = 1;
		this.mLoadList.add( drawable );
    }
    
    /**
     * This method sends a request to the BitsOpenGLRenderer to load the marked resources at the next safe point.<br>
     */
    public final void 
    loadAllMarked( ) {
    	
    	BitsGame.getInstance().mRenderer.requestLoading( 1 );  	
    }

    /**
     * This method sends a request to the BitsOpenGLRenderer to load the next marked resource at the next safe point.<br>
     */
    public final void
    loadNextMarked( ) {
    	
    	BitsGame.getInstance().mRenderer.requestLoading( 2 );    	
    }

    /**
     * This method MUST be called by the BitsOpenGLRenderer!!!<br>
     * It will load the next or all the resource that have been stored in the load list previously via<br>
     * the markResource() method.
     * 
     * @param type - defines the way resource should be loaded
     * @return - true if a resource has been loaded - false otherwise
     */
    public final boolean 
    load( 
    		final int type ) {
    	
    	final ArrayList<BitsDrawable> drawableList = this.mLoadList;
    	final int size = drawableList.size();
    	if( size == 0 ) {
    		return false;
    	}
    	
    	if( type == 1 ) { //all
	        for( int i = 0; i < size; i++ ) {
	        	final BitsDrawable drawable = drawableList.get(i);
	        	switch( drawable.mType ) {
	        		case BitsDrawable.DRAWABLE_TYPE_GL_IMAGE:
	        			this.load( (BitsGLImage)(drawable) );
	        			break;
	        		case BitsDrawable.DRAWABLE_TYPE_GL_FONT:
	        			this.load( (BitsGLFont)(drawable) );
	        			break;
	        		case BitsDrawable.DRAWABLE_TYPE_GL_RENDER_STACK:
	        			this.load( (BitsGLRenderStack)(drawable) );
	        			break;
	        	}
	        	drawable.mMark = 0; //reset mark
	        }
	        drawableList.clear();
	        return false;
    	} else { //next
	    	final BitsDrawable drawable = drawableList.remove(size - 1); //last element is fastest
	    	if( drawable != null ) {
		       	switch( drawable.mType ) {
	       		case BitsDrawable.DRAWABLE_TYPE_GL_IMAGE:
	       			this.load( (BitsGLImage)(drawable) );
	       			break;
	       		case BitsDrawable.DRAWABLE_TYPE_GL_FONT:
	       			this.load( (BitsGLFont)(drawable) );
	       			break;
	       		case BitsDrawable.DRAWABLE_TYPE_GL_RENDER_STACK:
	       			this.load( (BitsGLRenderStack)(drawable) );
	       			break;
		      	}
	    	}
	    	drawable.mMark = 0; //reset mark
	    	return true;
    	}
    }
    
    public final int 
    getLoadCount( ) {
    	
    	return this.mLoadList.size();
    }
    
    private final void 
    load( 
    		final BitsGLFont font ) {
    	
    	if( font == null ) {
    		return;
    	}
    	
    	if( font.isLoaded == true || font.mMark != 1 ) {
    		return;
    	}
    	
	   	BitsLog.d("BitsGLFactory - load", "Loading BitsFont...");
	    	
	   	if( font.mBitmap == null ) { // IF the font bitmap needs to be recreated (app resumes)
	   		switch( font.mType ) {
	   			case BitsGLFont.TYPE_FILE: {
	   				if( font.mBitmap == null ) {
	   					this.createFontBitmap( font );
	   				}
	   				break;
	   			}
	   			case BitsGLFont.TYPE_BITMAP: {
	            	font.mBitmap = this.getBitmap( font.mFile );
	   				break;
	   			}
	   		}
	   	}
    	
        // generate a new texture
        final int[] textureIds = new int[1]; // Array to Get Texture Id
        GLES11.glGenTextures( 1, textureIds, 0 ); // Generate New Texture
        font.mTextureId = textureIds[0]; // Save Texture Id

        // setup filters for texture
        GLES11.glBindTexture( GLES11.GL_TEXTURE_2D, font.mTextureId );  // Bind Texture
        GLES11.glTexParameteri( GLES11.GL_TEXTURE_2D, GLES11.GL_TEXTURE_WRAP_S, GLES11.GL_CLAMP_TO_EDGE );  // Set U Wrapping
        GLES11.glTexParameteri( GLES11.GL_TEXTURE_2D, GLES11.GL_TEXTURE_WRAP_T, GLES11.GL_CLAMP_TO_EDGE );  // Set V Wrapping

        //Setup texture scaling filtering
		switch( font.mFilterMode ) {
			case BitsGLFont.FILTER_NEAREST: {
		        GLES11.glTexParameteri(GLES11.GL_TEXTURE_2D, GLES11.GL_TEXTURE_MIN_FILTER, GLES11.GL_NEAREST);
		        GLES11.glTexParameteri(GLES11.GL_TEXTURE_2D, GLES11.GL_TEXTURE_MAG_FILTER, GLES11.GL_NEAREST);
				break;
			}
			case BitsGLFont.FILTER_LINEAR: {
		        GLES11.glTexParameteri(GLES11.GL_TEXTURE_2D, GLES11.GL_TEXTURE_MIN_FILTER, GLES11.GL_LINEAR);
		        GLES11.glTexParameteri(GLES11.GL_TEXTURE_2D, GLES11.GL_TEXTURE_MAG_FILTER, GLES11.GL_LINEAR);
				break;
			}
		}
        // load the generated bitmap onto the texture
        GLUtils.texImage2D( GLES11.GL_TEXTURE_2D, 0, font.mBitmap.mSource, 0 ); // Load Bitmap to Texture
        GLES11.glBindTexture( GLES11.GL_TEXTURE_2D, 0 ); // Unbind Texture

        // release the bitmap
        font.mBitmap.release(); // Release the Bitmap
        font.mBitmap = null;
        
        font.isLoaded = true;
        
        BitsLog.d("BitsGLFactory - load", "...done loading BitsGLFont. ID: " + font.mTextureId);
    }
    
    private final void 
    load( 
    		final BitsGLRenderStack renderStack ) {
    	
    	if( renderStack == null ) {
    		return;
    	}
    	
    	if( renderStack.isLoaded == true || 
    		renderStack.mMark != 1 || 
    		renderStack.mVertexBuffer == null || 
    		renderStack.mIndexBuffer == null || 
    		renderStack.mImageCount <= 0 ) {
    		return;
    	}
    	
		BitsLog.d("BitsGLFactory - load", "Loading BitsRenderStack into GPU...");
		
		renderStack.mVertexBuffer.put(renderStack.mVertices);
		renderStack.mVertexBuffer.flip();		
		renderStack.mVerticeCount = renderStack.mImageCount * BitsGLImage.INDICES_PER_SPRITE; //2 floats per point
		
		final int[] buffer = new int[1];
		GLES11.glGenBuffers(1, buffer, 0);
		renderStack.mVertexBufferId = buffer[0];
		GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, renderStack.mVertexBufferId);
		GLES11.glBufferData(GLES11.GL_ARRAY_BUFFER, renderStack.mVertices.length * 4, renderStack.mVertexBuffer, GLES11.GL_STATIC_DRAW);
		
		GLES11.glGenBuffers(1, buffer, 0);
		renderStack.mIndexBufferId = buffer[0];
		GLES11.glBindBuffer(GLES11.GL_ELEMENT_ARRAY_BUFFER, renderStack.mIndexBufferId);
		GLES11.glBufferData(GLES11.GL_ELEMENT_ARRAY_BUFFER, (renderStack.mImageCapacity * BitsGLImage.INDICES_PER_SPRITE) * 2, renderStack.mIndexBuffer, GLES11.GL_STATIC_DRAW);
				
		GLES11.glBindBuffer(GLES11.GL_ELEMENT_ARRAY_BUFFER, 0);
		GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, 0);
				
		renderStack.mIndexBuffer = null;
		renderStack.mVertexBuffer = null;
		renderStack.mVertices = null;
		
		renderStack.isLoaded = true;

		BitsLog.d("BitsGLFactory - load", "...done: (VertexBuffer == " + renderStack.mVertexBufferId + " - IndexBuffer == " + renderStack.mIndexBufferId + ")");
    }
    
    private final void 
    load( 
    		final BitsGLImage image ) {
    	
    	if( image == null ) {
    		return;
    	}
    	
    	if( image.isLoaded == true || image.mMark != 1 ) {
    		return;
    	}
    	
    	if( image.mResourceId == -1 && image.mBitmap == null && image.mFile == null ) {
    		return;
    	}

    	BitsLog.d("BitsGLFactory - load", "Loading BitsImage...");
    	BitsBitmap bitmap = null;

    	if(image.mResourceId > -1) {
    		bitmap = this.getBitmap( image.mResourceId );
    	} else {
        	if(image.mFile != null) {
        		bitmap = this.getBitmap( image.mFile );
        	} else {
		    	if(image.mBitmap != null) {
		    		BitsLog.d("BitsGLFactory - load", "Using existing bitmap image.");
		    		bitmap = image.mBitmap;
		   		}
	    	}
    	}
    	
		if( bitmap == null || bitmap.mSource == null ) {
			BitsLog.e("BitsGLFactory - load", "Can't load BitsImage (" + image.mResourceId + "/" + image.mFile + "). The corresponding bitmap is NULL!");
			image.reset();
			return;
		}
    	    
		int[] textureID = new int[1];
		GLES11.glGenTextures( 1, textureID, 0 );
		
		GLES11.glBindTexture(GLES11.GL_TEXTURE_2D, textureID[0]); //Bind texture ID
		
        //Setup texture scaling filtering
		switch( image.mFilterMode ) {
			case BitsGLImage.FILTER_NEAREST: {
		        GLES11.glTexParameteri(GLES11.GL_TEXTURE_2D, GLES11.GL_TEXTURE_MIN_FILTER, GLES11.GL_NEAREST);
		        GLES11.glTexParameteri(GLES11.GL_TEXTURE_2D, GLES11.GL_TEXTURE_MAG_FILTER, GLES11.GL_NEAREST);
				break;
			}
			case BitsGLImage.FILTER_LINEAR: {
		        GLES11.glTexParameteri(GLES11.GL_TEXTURE_2D, GLES11.GL_TEXTURE_MIN_FILTER, GLES11.GL_LINEAR);
		        GLES11.glTexParameteri(GLES11.GL_TEXTURE_2D, GLES11.GL_TEXTURE_MAG_FILTER, GLES11.GL_LINEAR);
				break;
			}
		}
		
        //Setup wrap mode
		switch( image.mRenderMode ) {
			case BitsGLImage.RENDER_STRETCH: {
				GLES11.glTexParameteri(GLES11.GL_TEXTURE_2D, GLES11.GL_TEXTURE_WRAP_S, GLES11.GL_CLAMP_TO_EDGE);
				GLES11.glTexParameteri(GLES11.GL_TEXTURE_2D, GLES11.GL_TEXTURE_WRAP_T, GLES11.GL_CLAMP_TO_EDGE);
				break;
			}
			case BitsGLImage.RENDER_REPEAT: {
				GLES11.glTexParameteri(GLES11.GL_TEXTURE_2D, GLES11.GL_TEXTURE_WRAP_S, GLES11.GL_REPEAT);
				GLES11.glTexParameteri(GLES11.GL_TEXTURE_2D, GLES11.GL_TEXTURE_WRAP_T, GLES11.GL_REPEAT);							
				break;
			}
		}
		
		GLUtils.texImage2D( GLES11.GL_TEXTURE_2D, 0, bitmap.mSource, 0 );
        
        GLES11.glBindTexture(GLES11.GL_TEXTURE_2D, 0); //Bind texture ID
         
 	    image.mTextureId = textureID[0];
	    image.mWidth = bitmap.getWidth();
	    image.mHeight = bitmap.getHeight();
	    image.isLoaded = true;
	    
	    if( image.mKeepBitmap == false ) {
	    	bitmap.release();
	    	bitmap = null;
	    }
	    
        BitsLog.d("BitsGLFactory - load", "...done loading BitsImage. ID: " + image.mTextureId);
    }

    /**
     * This method will mark a resource to be released later at a safe point in the BitsOpenGLRenderer.<br>
     * 
     * @param resource - a resource that will be released at a safe point
     */
    public final void 
    markForReleasing( 
    		final BitsDrawable drawable ) {
    	
    	if( drawable == null ) {
    		BitsLog.w("BitsGLFactory - markForReleasing", "The given BitsDrawable is NULL!");
    		return;
    	}
    	
    	if( drawable.mMark != 0 ) {
    		BitsLog.w("BitsGLFactory - markForReleasing", "The given BitsDrawable is NULL!");
    		return;
    	}
    	
    	if( drawable.isLoaded == false ) {
    		BitsLog.w("BitsGLFactory - markForReleasing", "The given BitsDrawable was not loaded!");
    		return;
    	}
    	
    	if( drawable.mType > BitsDrawable.DRAWABLE_TYPE_BITMAP ) {
    		BitsLog.w("BitsGLFactory - markForLoading", "The given BitsDrawable is not a BitsImage/BitsFont/BitsRenderStack!");
    		return;
    	}

		drawable.mMark = 2;
		this.mReleaseList.add( drawable );
    }
    
    /**
     * This method sends a request to the BitsOpenGLRenderer to release the marked drawable at the next safe point.<br>
     */
    public final void 
    releaseAllMarked( ) {
    	
    	BitsGame.getInstance().mRenderer.requestReleasing( 1 );  	
    }

    /**
     * This method sends a request to the BitsOpenGLRenderer to release the next marked drawable at the next safe point.<br>
     */
    public final void 
    releaseNextMarked( ) {
    	
    	BitsGame.getInstance().mRenderer.requestReleasing( 2 );    	
    }

    /**
     * This method MUST be called by the BitsOpenGLRenderer!!!<br>
     * It will load the next or all the resource that have been stored in the load list previously via<br>
     * the markResource() method.
     * 
     * @param type - defines the way resource should be released
     * @return - true if a resource has been release - false otherwise
     */
    public final boolean 
    release(
    		final int type ) {
    	
    	final ArrayList<BitsDrawable> drawableList = this.mReleaseList;
    	final int size = drawableList.size();
    	if( size == 0 ) {
    		return false;
    	}
    	
    	if( type == 1 ) { //all
	        for( int i = 0; i < size; i++ ) {
	        	final BitsDrawable drawable = drawableList.get(i);
	        	switch( drawable.mType ) {
	        		case BitsDrawable.DRAWABLE_TYPE_GL_IMAGE:
	        			this.release( (BitsGLImage)(drawable) );
	        			break;
	        		case BitsDrawable.DRAWABLE_TYPE_GL_FONT:
	        			this.release( (BitsGLFont)(drawable) );
	        			break;
	        		case BitsDrawable.DRAWABLE_TYPE_GL_RENDER_STACK:
	        			this.release( (BitsGLRenderStack)(drawable) );
	        			break;
	        	}
	        	drawable.mMark = 0; //reset mark
	        }
	        drawableList.clear();
	        return false;
    	} else { //next
    		final BitsDrawable drawable = drawableList.remove(size - 1); //last element is fastest
    		if( drawable != null ) {
	        	switch( drawable.mType ) {
        			case BitsDrawable.DRAWABLE_TYPE_GL_IMAGE:
        				this.release( (BitsGLImage)(drawable) );
        				break;
        			case BitsDrawable.DRAWABLE_TYPE_GL_FONT:
        				this.release( (BitsGLFont)(drawable) );
        				break;
        			case BitsDrawable.DRAWABLE_TYPE_GL_RENDER_STACK:
        				this.release( (BitsGLRenderStack)(drawable) );
        				break;
	        	}
    		}
    		drawable.mMark = 0; //reset mark
    		return true;
    	}
    }
    
    public final int 
    getReleaseCount( ) {
    	
    	return this.mReleaseList.size();
    }
    
    /**
     * Resets the BitsImage object and deletes the previously loaded texture.<br>
     * This method does nothing, if the given BitsImage object is NULL.<br>
     * 
     * @param image - the image to release
     */
    private final void 
    release( 
    		final BitsGLImage image ) {
    	
    	if( image == null ) {
    		return;
    	}
    	
    	if( image.isLoaded == false || image.mMark != 2 ) {
    		return;
    	}

		BitsLog.d("BitsGLFactory - release", "Releasing texture: " + String.valueOf(image.mTextureId));
        final int[] textureId = new int[1];
        textureId[0]= image.mTextureId;
        GLES11.glDeleteTextures( 1, textureId, 0 );
        image.reset();
    }
    
    /**
     * Resets the BitsImageStack object and deletes the previously loaded vertex and index buffers.<br>
     * This method does nothing, if the given BitsImageStack object is NULL.<br>
     * 
     * @param imageStack - the imageStack to release
     */
    private final void 
    release( 
    		final BitsGLRenderStack imageStack ) {
    	
    	if( imageStack == null ) {
    		return;
    	}
    	
    	if( imageStack.isLoaded == false || imageStack.mMark != 2 ) {
    		return;
    	}

		BitsLog.d( "BitsGLFactory - release", "Releasing BitsRenderStack..." );
        BitsLog.d( "BitsGLFactory - release", "Releasing vertex buffer: " + String.valueOf( imageStack.mVertexBufferId ) );
        final int[] bufferVertexId = new int[1];
        bufferVertexId[0]= imageStack.mVertexBufferId;
        GLES11.glDeleteBuffers( 1, bufferVertexId, 0 );
        BitsLog.d( "BitsGLFactory - release", "Releasing index buffer: " + String.valueOf( imageStack.mIndexBufferId ) );
        final int[] bufferIndexId = new int[1];
        bufferIndexId[0]= imageStack.mIndexBufferId;
        GLES11.glDeleteBuffers( 1, bufferIndexId, 0 );
        imageStack.reset();
    }
    
    private final void 
    release( 
    		final BitsGLFont font ) {
    	
    	if( font == null ) {
    		return;
    	}
    	
    	if( font.isLoaded == false || font.mMark != 2 ) {
    		return;
    	}

        BitsLog.d("BitsGLFactory - release", "Releasing font texture: " + font.mTextureId);
        final int[] textureId = new int[1];
        textureId[0]= font.mTextureId;
        GLES11.glDeleteTextures( 1, textureId, 0 );
        font.reset();
    }
    
    public final void 
    invalidateAll() {
    	
    	BitsLog.d( "BitsGLFactory - invalidateAll", "Invalidating all loaded images..." );
		final ArrayList<BitsGLImage> imageList = this.mImageList;
		final int size = imageList.size();
        for( int i = 0; i < size; i++ ) {
        	final BitsGLImage poolImage = imageList.get(i);
        	if( poolImage.mResourceId > -1 ||
        		poolImage.mBitmap != null ||
        		poolImage.mFile != null ) {
        		if( poolImage.isLoaded == true ) {
        			poolImage.mTextureId = -1;
        			poolImage.isLoaded = false;
        			poolImage.mMark = 1; //re-mark for loading
        		}
            }
        }
        
        BitsLog.d( "BitsGLFactory - invalidateAll", "Invalidating all loaded BitsRenderStacks..." );
    	final ArrayList<BitsGLRenderStack> stackList = this.mRenderStackList;
    	final int size2 = stackList.size();
        for( int i = 0; i < size2; i++ ) {
        	final BitsGLRenderStack poolStack = stackList.get(i);
        	if( poolStack.mVertexBufferId != -1 &&
        		poolStack.mIndexBufferId != -1 &&
        		poolStack.isLoaded == true) {
        		poolStack.isLoaded = false;
        		poolStack.mMark = 1; //re-mark for loading
        		poolStack.mImageCount = 0;
        		poolStack.mBufferIndex = 0;
        		poolStack.init( poolStack.mImageCapacity );
        	}
        }
        
        BitsLog.d( "BitsGLFactory - invalidateAll", "Invalidating all loaded BitsFonts..." );
		final ArrayList<BitsGLFont> fontList = this.mFontList;
		final int size3 = fontList.size();
        for( int i = 0; i < size3; i++ ) {
        	final BitsGLFont poolFont = fontList.get( i );
            if( poolFont.mFile != null && 
            	poolFont.mFontSize != -1 &&
            	poolFont.isLoaded == true ) {
            	poolFont.mTextureId = -1;
            	poolFont.isLoaded = false;
            	poolFont.mMark = 1; //re-mark for loading
            }
        }
    }
    
    public void 
    reloadAll( ) {
    	
    	final ArrayList<BitsGLImage> imageList = this.mImageList;
    	final int size = imageList.size();
        for( int i = 0; i < size; i++ ) {
        	final BitsGLImage poolImage = imageList.get(i);
            if( poolImage.isLoaded == false ) {
            	if( poolImage.mResourceId > -1 ||
            	    poolImage.mBitmap	!= null ||
            	    poolImage.mFile 	!= null ) {
            		this.load( poolImage );
            	}
            }
        }

    	final ArrayList<BitsGLRenderStack> stackList = this.mRenderStackList;
    	final int size2 = stackList.size();
        for( int i = 0; i < size2; i++ ) {
        	final BitsGLRenderStack poolStack = stackList.get(i);
            if( poolStack.isLoaded == false &&
            	poolStack.mVertexBuffer != null &&
            	poolStack.mIndexBuffer != null) {
           		this.load( poolStack );
            }
        }
        
		final ArrayList<BitsGLFont> fontList = this.mFontList;
		final int size3 = fontList.size();
        for( int i = 0; i < size3; i++ ) {
        	final BitsGLFont poolFont = fontList.get( i );
            if( poolFont.isLoaded == false ) {
            	if( poolFont.mFile != null &&
            		poolFont.mFontSize != -1 ) {
            		this.load( poolFont );
            	}
            }
        }
    }
}
