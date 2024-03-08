
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

package net.gliblybits.bitsengine.sound;

public final class 
BitsSound {
	
	public static final int TYPE_WAV 	= 1;
	public static final int TYPE_OGG 	= 2;
	
	public boolean 	isLoaded 			= false;
	public int		mId					= -1;
	public int		mStreamId			= 0;
	public int		mType				= TYPE_WAV;
	
	public String	mFile				= null;
	public byte[]   mRawData			= null;
	public String	mRawDataHash 		= null;
	public int		mResourceId			= -1;
	public int 		mChannels			= 1;
	public int 		mSamplesPerSecond 	= 8000;
	public int 		mBytesPerSecond 	= 16000;
	public int 		mBytesPerSample 	= 2;
	public int 		mBitsPerSample		= 16;
	
	public 
	BitsSound() {
		
	}
	
	public final void 
	reset( ) {
		
		this.isLoaded 			= false;
		this.mId				= -1;
		this.mStreamId			= 0;
		this.mFile				= null;
		this.mRawData			= null;
		this.mRawDataHash 		= null;
		this.mResourceId		= -1;
		this.mChannels			= 1;
		this.mSamplesPerSecond 	= 8000;
		this.mBytesPerSecond 	= 16000;
		this.mBytesPerSample 	= 2;
		this.mBitsPerSample		= 16;
		this.mType				= -1;
	}
	
	public final void
	play( 
			final float pitch, 
			final float volume, 
			final boolean loop ) {
		
		if( this.mId != -1 ) {
			if( this.isLoaded == true ) {
				this.mStreamId = BitsSoundFactory.getInstance().mSoundPool.play(mId, volume, volume, 0, loop == true ? -1 : 0, pitch);
			}
		}
	}
	
	public final void
	play( 
			final boolean loop ) {
		
		this.play(1f, 1f, loop);
	}
	
	public final void 
	stop( ) {
		
		if( this.mId != -1 ) {
			if( this.isLoaded == true ) {
				BitsSoundFactory.getInstance().mSoundPool.stop( this.mStreamId );
			}
		}
	}
}
