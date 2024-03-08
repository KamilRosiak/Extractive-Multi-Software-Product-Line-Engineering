
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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import net.gliblybits.bitsengine.core.BitsApp;
import net.gliblybits.bitsengine.graphics.BitsDrawable;
import net.gliblybits.bitsengine.utils.BitsLog;

/**
 * This class provides the possibility to predefine a static image stack, that can be drawn like a normal BitsImage.<br>
 * The difference to a normal BitsImage is the way the engine handles the BitsImageStack objects.<br>
 * You can get a new BitsImageStack object by calling BitsImageFactory.getIt().getImageStack(int size);<br>
 * The size defines the amount of images (4 vertices and 4 texture coordinates for each image) the stack can hold.<br>
 * Now you can add empty images by calling the add(...) methods.<br>
 * Here you don't need to care about texture images. The stack consists of empty images only.<br>
 * The texture will be added later in the rendering process automatically by binding a corresponding texture/BitsImage.<br>
 * <br>
 * So, why to do all this stuff? Performance!<br>
 * The BitsImageStack will be rendered like a normal BitsImage, but the vertices and texture coordinates are already copied into<br>
 * the GPUs RAM. Also there will only occur one glDrawElements(...) call for one BitsImageStack.<br>
 * So, think of 500 BitsImages, that can be rendered with only one call to OpenGL ES and without copying the vertices and all the stuff<br>
 * into the GPUs RAM every single frame.<br>
 * <br>
 * But there is also a drawback.<br>
 * Because there is only one call to OpenGL ES, you will be unable to draw different textures for one BitsImageStack.<br>
 * But you may use the texture crop feature to render different parts of one texture, which will lead to the same<br>
 * result as binding/rendering different textures.<br>
 * 
 * @author Marc Wiedenhoeft - GliblyBits
 * @version 0.1 - Streak
 * @since 0.1 - Streak
 *
 */
public final class 
BitsGLRenderStack 
extends BitsDrawable {

	public float[] mVertices;
	public int mBufferIndex = 0;
	public int mImageCount = 0;
	public int mImageCapacity = 0;
	
	public FloatBuffer mVertexBuffer = null;
	public ShortBuffer mIndexBuffer = null;

	public int mVertexBufferId = -1;
	public int mIndexBufferId = -1;
	public int mVerticeCount = 0;
	
	public BitsGLRenderStack( ) {
		super( BitsDrawable.DRAWABLE_TYPE_GL_RENDER_STACK );
	}
	
	/**
	 * This method initializes the vertex and index buffers.<br>
	 * This method will be called by the BitsImageFactory, if you call BitsImageFactory.getImageStack(int size);<br>
	 * 
	 * @param size - size of the stack (how many images this stack may contain)
	 */
	public final void init( final int size ) {
		BitsLog.d( "BitsRenderStack - init", "Initialize stack with size: " + size );
		if( size > 0 ) {
			this.mImageCapacity = size;
		} else {
			this.mImageCapacity = 1;
			BitsLog.e("BitsRenderStack - constructor", "The size need to be > 0 and it is: " + size);
		}
		
		this.mVertices = new float[mImageCapacity * BitsGLImage.VERTICES_PER_SPRITE * BitsGLImage.VERTEX_SIZE];
		
		//TODO l�schen
	    final short[] indices = new short[mImageCapacity * BitsGLImage.INDICES_PER_SPRITE];
	    final int len = indices.length;
	    short j = 0;
	    for ( int i = 0; i < len; i+= BitsGLImage.INDICES_PER_SPRITE, j += BitsGLImage.VERTICES_PER_SPRITE )  {
	    	indices[i + 0] = (short)( j + 0 );
	        indices[i + 1] = (short)( j + 1 );
	        indices[i + 2] = (short)( j + 2 );
	        indices[i + 3] = (short)( j + 2 );
	        indices[i + 4] = (short)( j + 3 );
	        indices[i + 5] = (short)( j + 0 );
	    }
	    
	    ByteBuffer buffer = ByteBuffer.allocateDirect( this.mVertices.length * 16 );
	    buffer.order( ByteOrder.nativeOrder( ) );
	    this.mVertexBuffer = buffer.asFloatBuffer( );
	    
        buffer = ByteBuffer.allocateDirect( indices.length * ( Short.SIZE / 8 ) );
        buffer.order( ByteOrder.nativeOrder() );
        this.mIndexBuffer = buffer.asShortBuffer( );
        
        this.mIndexBuffer.clear( );
        this.mIndexBuffer.put( indices );
        this.mIndexBuffer.flip( );
	}
	
	/**
	 * Resets the BitsImageStack object.<br>
	 * This is automatically done by the BitsImageFactory, if you release a BitsImageStack object.<br>
	 * After a call to this method, the BitsImageStack object is ready to be reused.<br>
	 */
	public final void reset( ) {
		this.mVertices 			= null;
		this.mBufferIndex 		= 0;
		this.mImageCount 		= 0;
		this.mImageCapacity 	= 0;
		this.mVertexBuffer 		= null;
		this.mIndexBuffer 		= null;
		this.mVertexBufferId 	= -1;
		this.mIndexBufferId 	= -1;
		this.mVerticeCount 		= 0;
		
		this.isLoaded 			= false;
		this.mMark				= 0;
	}
	
	/**
	 * Adds coordinates for a static image to the stack.<br>
	 * You have to specify the position and size of the image.<br>
	 * REMEMBER: This image will render/use the full texture image for rendering!
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public final void add( 
			final float x, 
			final float y, 
			final float width, 
			final float height ) {
		this.add( x, y, width, height, -1, -1, -1, -1, -1, -1 );
	}

	/**
	 * Adds coordinates for a static image to the stack.<br>
	 * You have to specify the position and size of the image.<br>
	 * The texture crop is defined in the same way. You define a texture part with<br>
	 * cropX, cropY to cropWidth, cropHeight.<br>
	 * The imageWidth and imageHeight values are needed to calculate the right texture part in float values.<br>
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param cropX
	 * @param cropY
	 * @param cropWidth
	 * @param cropHeight
	 * @param imageWidth - width of the original full texture image
	 * @param imageHeight - height of the original full texture image
	 */
	public final void add( 
			final float x, 
			final float y, 
			final float width, 
			final float height, 
			final float cropX, 
			final float cropY, 
			final float cropWidth, 
			final float cropHeight,
			final int imageWidth,
			final int imageHeight) {
		if( (this.mImageCount == this.mImageCapacity) ) {
			BitsLog.e("BitsRenderStack - add", "Can not add to BitsImageStack. The stack is full.");
			return;
		}
 		
		if(cropX > -1 && cropY > -1 && cropWidth > -1 && cropHeight > -1 && imageWidth > -1 && imageHeight > -1) {
 	         this.mVertices[this.mBufferIndex++] = x * BitsApp.sScaleFactor;
 	         this.mVertices[this.mBufferIndex++] = y * BitsApp.sScaleFactor;
 	         this.mVertices[this.mBufferIndex++] = (float)(cropX) / (float)(imageWidth);
 	         this.mVertices[this.mBufferIndex++] = (float)(cropY) / (float)(imageHeight);

 	         this.mVertices[this.mBufferIndex++] = (x * BitsApp.sScaleFactor) + (width * BitsApp.sScaleFactor);
 	         this.mVertices[this.mBufferIndex++] = y * BitsApp.sScaleFactor;
 	         this.mVertices[this.mBufferIndex++] = (float)(cropX + cropWidth) / (float)(imageWidth);
 	         this.mVertices[this.mBufferIndex++] = (float)(cropY) / (float)(imageHeight);

 	         this.mVertices[this.mBufferIndex++] = (x * BitsApp.sScaleFactor) + (width * BitsApp.sScaleFactor);
 	         this.mVertices[this.mBufferIndex++] = (y * BitsApp.sScaleFactor) + (height * BitsApp.sScaleFactor);
 	         this.mVertices[this.mBufferIndex++] = (float)(cropX + cropWidth) / (float)(imageWidth);
 	         this.mVertices[this.mBufferIndex++] = (float)(cropY + cropHeight) / (float)(imageHeight);

 	         this.mVertices[this.mBufferIndex++] = x * BitsApp.sScaleFactor;
 	         this.mVertices[this.mBufferIndex++] = (y * BitsApp.sScaleFactor) + (height * BitsApp.sScaleFactor);
 	         this.mVertices[this.mBufferIndex++] = (float)(cropX) / (float)(imageWidth);
 	         this.mVertices[this.mBufferIndex++] = (float)(cropY + cropHeight) / (float)(imageHeight);

		} else {
	         this.mVertices[this.mBufferIndex++] = x * BitsApp.sScaleFactor;
	         this.mVertices[this.mBufferIndex++] = y * BitsApp.sScaleFactor;
	         this.mVertices[this.mBufferIndex++] = 0f;
	         this.mVertices[this.mBufferIndex++] = 0f;

	         this.mVertices[this.mBufferIndex++] = (x * BitsApp.sScaleFactor) + (width * BitsApp.sScaleFactor);
	         this.mVertices[this.mBufferIndex++] = y * BitsApp.sScaleFactor;
	         this.mVertices[this.mBufferIndex++] = 1f;
	         this.mVertices[this.mBufferIndex++] = 0f;

	         this.mVertices[this.mBufferIndex++] = (x * BitsApp.sScaleFactor) + (width * BitsApp.sScaleFactor);
	         this.mVertices[this.mBufferIndex++] = (y * BitsApp.sScaleFactor) + (height * BitsApp.sScaleFactor);
	         this.mVertices[this.mBufferIndex++] = 1f;
	         this.mVertices[this.mBufferIndex++] = 1f;

	         this.mVertices[this.mBufferIndex++] = x * BitsApp.sScaleFactor;
	         this.mVertices[this.mBufferIndex++] = (y * BitsApp.sScaleFactor) + (height * BitsApp.sScaleFactor);
	         this.mVertices[this.mBufferIndex++] = 0f;
	         this.mVertices[this.mBufferIndex++] = 1f;
		}

 		this.mImageCount++;
	}
}
