
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

package net.gliblybits.bitsengine.render.commands;

/**
 * A BitsRenderCommand is used to send a render instruction from the game thread to the render thread.<br>
 * The commands will be queued up by the game thread. Afterwards the game thread will inform the render thread about<br>
 * the new queue. The render thread will take this queue and perform the commands in it.<br> 
 * 
 * @author Marc Wiedenhoeft - GliblyBits
 * @version 0.1 - Streak
 * @since 0.1 - Streak
 */
public final class 
BitsRenderCommand {
	
	public final static int TYPE_IMAGE			= 0;
	public final static int TYPE_COLOR 			= 1;
	public final static int TYPE_IMAGE_STACK 	= 2;
	public final static int TYPE_LINE 			= 3;
	public final static int TYPE_LINE_SIZE		= 4;
	public final static int TYPE_RECT			= 5;
	public final static int TYPE_ROTATION		= 6;
	public final static int TYPE_SCALE			= 7;
	public final static int TYPE_CIRCLE			= 8;
	public final static int TYPE_RECT_OUTLINED	= 9;
	public final static int TYPE_TEXT			= 10;
	public final static int TYPE_ROUND_RECT		= 11;
	public final static int TYPE_BLENDFUNC		= 12;
	public final static int TYPE_CLIP			= 13;
	public final static int TYPE_TRIANGLE		= 14;
	
	public int mType = TYPE_IMAGE;
	
	public int			mTexId;
	public int			mTexWidth;
	public int			mTexHeight;
	public float 		mWidth;
	public float 		mHeight;
	public float 		mX;
	public float 		mY;
	public float		mCropX;
	public float		mCropY;
	public float		mCropWidth;
	public float		mCropHeight;
	public float		mTexX1;
	public float		mTexY1;
	public float		mTexX2;
	public float		mTexY2;
	public int			mRenderMode;
	
	public 
	BitsRenderCommand( ) {
		
		this.reset( );
	}

	/**
	 * Resets this render command.
	 */
	public final void 
	reset( ) {
		
		this.mTexId 		= 0;
		this.mTexWidth  	= 0;
		this.mTexHeight 	= 0;
		this.mX 			= 0f;
		this.mY 			= 0f;
		this.mWidth 		= 0f;
		this.mHeight 		= 0f;
		this.mCropX 		= -1;
		this.mCropY 		= -1;
		this.mCropWidth 	= -1;
		this.mCropHeight 	= -1;
		this.mRenderMode	= -1;
	}
}
