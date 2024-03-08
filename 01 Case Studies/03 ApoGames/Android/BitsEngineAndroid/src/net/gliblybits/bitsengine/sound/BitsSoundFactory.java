
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

import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import android.media.AudioManager;
import android.media.SoundPool;


import net.gliblybits.bitsengine.core.BitsApp;
import net.gliblybits.bitsengine.io.BitsIO;
import net.gliblybits.bitsengine.utils.BitsLog;
import net.gliblybits.bitsengine.utils.BitsUtils;

public final class 
BitsSoundFactory {
	
	private static final BitsSoundFactory instance = new BitsSoundFactory();

	private final ArrayList<BitsSound> 	mSoundList;
	public 	final SoundPool 			mSoundPool;

	private
	BitsSoundFactory( ) {
		
		this.mSoundList = new ArrayList<BitsSound>();
		this.mSoundPool = new SoundPool(32, AudioManager.STREAM_MUSIC, 0);
	}
	
	public static final BitsSoundFactory 
	getInstance() {
		
		return instance;
	}
	
	public static final void 
	resetInstance( ) {
		
		BitsLog.d( "BitsSoundFactory - resetInstance", "Resetting the singleton instance..." );
		instance.releaseAll( );
	}
	
	public final BitsSound
	getSound( 
			final String audioFile,
			final int type,
			final boolean load ) {
		
		return this.getSound( 
				audioFile, 
				type,
				null, 
				-1,
				-1,
				-1,
				-1,
				-1,
				-1,
				load );
	}
	
	public final BitsSound
	getSound( 
			final byte[] rawData,
			final int channels,
			final int samplesPerSecond,
			final int bytesPerSecond,
			final int bytesPerSample,
			final int bitsPerSample,
			final boolean load ) {
		
		return this.getSound( 
				null, 
				-1,
				rawData, 
				-1,
				channels,
				samplesPerSecond,
				bytesPerSecond,
				bytesPerSample,
				bitsPerSample,
				load );
	}
	
	public final BitsSound
	getSound( 
			final int resourceId,
			final int type,
			final boolean load ) {
		
		return this.getSound( 
				null, 
				-1,
				null, 
				resourceId,
				-1,
				-1,
				-1,
				-1,
				-1,
				load );
	}
	
	private final BitsSound
	getSound( 
			final String soundFile, 
			final int type,
			final byte[] rawData, 
			final int resourceId,
			final int channels,
			final int samplesPerSecond,
			final int bytesPerSecond,
			final int bytesPerSample,
			final int bitsPerSample,
			final boolean load ) {
		
    	BitsSound sound = null;
    	BitsSound emptySound = null;

    	BitsLog.d("BitsSoundFactory - getSound", "Searching BitsSound in audio pool...");
		final ArrayList<BitsSound> soundList = instance.mSoundList;
		final int size = soundList.size();
        for( int i = 0; i < size; i++ ) {
        	final BitsSound poolSound = soundList.get( i );
        	/*
        	 * Search for an empty audio object.
        	 */
    		if( poolSound.isLoaded	== false &&
    			poolSound.mResourceId == -1 &&
    			poolSound.mFile   	== null &&
    			poolSound.mRawData  == null ) {
    			emptySound = poolSound;
        	}

    		/*
    		 * Search for audio that is already loaded via file name. 
    		 */
    		if( poolSound.mFile != null && 
    			soundFile 		!= null ) {
        		if( poolSound.mFile.equals( soundFile ) ) {
    				BitsLog.d("BitsSoundFactory - getSound", "...sound found via file name. BitsSound already loaded.");
    				sound = poolSound;
    		    	break;
    			}
    		}
    		
    		/*
    		 * Search for audio that is already loaded via byte array. 
    		 */
    		if( poolSound.mRawDataHash 	!= null && 
    			rawData 		 	 	!= null ) {
    			if(poolSound.mRawDataHash.equals( BitsUtils.getHash( rawData ) ) ) {
    				BitsLog.d("BitsSoundFactory - getSound", "...sound found via hash. BitsSound already loaded.");
    				sound = poolSound; 
    		    	break;
    			}
    		}
    		
    		/*
    		 * Search for audio that is already loaded via resource id. 
    		 */
    		if( poolSound.mResourceId 	!= -1 && 
    			resourceId 		 	 	!= -1 ) {
    			if( poolSound.mResourceId == resourceId ) {
    				BitsLog.d("BitsSoundFactory - getSound", "...sound found via resource id. BitsSound already loaded.");
    				sound = poolSound; 
    		    	break;
    			}
    		}
    	}
    	
        /*
         * No BitsAudio found.
         */
        if( sound == null ) {
        	if( emptySound == null ) { //if there is no empty font in the font list
            	BitsLog.d("BitsSoundFactory - getSound", "...sound not found. Using a new BitsSound object.");
            	sound = new BitsSound( ); //create a new font object
        		this.mSoundList.add(sound); //and add it to the list
        	} else { //if there is an empty font in the font list
            	BitsLog.d("BitsSoundFactory - getSound", "...sound not found. Using an empty BitsSound.");
            	sound = emptySound;
        	}
        }
        
        sound.mFile 		= soundFile;
        sound.mRawData 		= rawData;
        sound.mResourceId 	= resourceId;
        sound.mType 		= type;
        
        if( load == true ) {
        	this.load(sound);
        }
        
        return sound;
	}

	public final void
	load( 
			final BitsSound sound ) {
		
		if( sound == null ) {
			BitsLog.w("BitsSoundFactory - load", "The give BitsSound object is NULL!");
			return;
		}
		
		if( sound.mRawData != null ) {
			sound.mRawDataHash = BitsUtils.asHex( sound.mRawData );
			BitsLog.d( "BitsSoundFactory - load", "Loading raw byte audio wave data: " + sound.mRawDataHash );
			
			final byte[] buffer = this.getRawWav(sound.mRawData, sound.mChannels, sound.mSamplesPerSecond, sound.mBytesPerSecond, sound.mBytesPerSample, sound.mBitsPerSample);
	        
	    	BitsIO.getInstance().writePrivateFile("temp.wav", buffer, false);
	    	sound.mId = this.mSoundPool.load(BitsIO.getInstance().getPrivateStorageDir() + File.separator + "temp.wav", 0);
	    	sound.mRawData = null;
	    	sound.isLoaded = true;
	        BitsIO.getInstance().deletePrivateFile("temp.wav");
			BitsLog.d( "BitsSoundFactory - load", "...done loading raw byte audio wave data: " + sound.mId );

			return;
		}
		
		if( sound.mFile != null ) {
			BitsLog.d( "BitsSoundFactory - load", "Loading audio data via file: " + sound.mFile );
			sound.mId = this.mSoundPool.load( sound.mFile, 1 );				
			sound.isLoaded = true;
			BitsLog.d( "BitsSoundFactory - load", "...done loading audio via file: " + sound.mId );
			
			return;
		}
		
		if( sound.mResourceId != -1 ) {
			BitsLog.d( "BitsSoundFactory - load", "Loading audio data via resource id: " + sound.mResourceId );
			sound.mId = this.mSoundPool.load( BitsApp.sAppContext, sound.mResourceId, 1 );				
			sound.isLoaded = true;
			BitsLog.d( "BitsSoundFactory - load", "...done loading audio via file: " + sound.mId );
			
			return;			
		}
	}
	
	public final void
	loadAll( ) {
		
		final ArrayList<BitsSound> soundList = this.mSoundList;
		final int size = soundList.size( );
		for( int i = 0; i < size; i++ ) {
			final BitsSound sound = soundList.get( i );
			if( sound != null ) {
				if( sound.isLoaded == false ) {
					this.load( sound );
				}
			}
		}
	}
	
	public final void 
	release( 
			final BitsSound sound ) {
		
    	if(sound != null) {
    		if( sound.isLoaded == true ) {
	    		BitsLog.d( "BitsSoundFactory", "Releasing sound: " + sound.mId );
	    		this.mSoundPool.unload( sound.mId );
	    		sound.reset();		        	
    		}
    	} else {
			BitsLog.w("BitsSoundFactory - release", "The give BitsSound object is NULL!");
		}
	}
	
	public final void 
	releaseAll( ) {
		
		BitsLog.d( "BitsSoundFactory", "Releasing all sound resources..." );
		final ArrayList<BitsSound> soundList = this.mSoundList;
		final int size = soundList.size( );
        for( int i = 0; i < size; i++ ) {
        	this.release( soundList.get( i ) );
		}
		this.mSoundList.clear();
	}
	
	public final void 
	stopAll( ) {
		
		BitsLog.d( "BitsSoundFactory", "Stopping all audio resources..." );
		final ArrayList<BitsSound> soundList = this.mSoundList;
		final int size = soundList.size( );
        for( int i = 0; i < size; i++ ) {
        	this.mSoundPool.stop( soundList.get( i ).mStreamId );
		}
	}
	
	public final byte[]
	getRawWav(
			final BitsSound sound ) {
		
		return this.getRawWav( sound.mRawData, sound.mChannels, sound.mSamplesPerSecond, sound.mBytesPerSecond, sound.mBytesPerSample, sound.mBitsPerSample );
	}
	
	public final byte[]
	getRawWav( 
			final byte[] rawData,
			final int channels,
			final int samplesPerSecond,
			final int bytesPerSecond,
			final int bytesPerSample,
			final int bitsPerSample ) {
		
    	final int size = 44 + rawData.length * 2;
    	
    	final ByteBuffer buff = ByteBuffer.allocate(size);
        buff.put("RIFF".getBytes());
        buff.put(new byte[] { (byte) (size - 8), (byte) ((size - 8) >> 8), (byte) ((size - 8) >> 16), (byte) ((size - 8) >> 24) });
        buff.put("WAVE".getBytes());
        buff.put("fmt ".getBytes());
        buff.put(new byte[] { (byte) (16), (byte) (16 >> 8), (byte) (16 >> 16), (byte) (16 >> 24) }); //chunk size
        buff.put(new byte[] { (byte) (1), (byte) (1 >> 8) }); //1 == PCM
        buff.put(new byte[] { (byte) (channels), (byte) (channels >> 8) }); //mono == 1 / stereo == 2
        buff.put(new byte[] { (byte) (samplesPerSecond), (byte) (samplesPerSecond >> 8), (byte) (samplesPerSecond >> 16), (byte) (samplesPerSecond >> 24) }); //samples per second 8000,11025,16000,22050,44100
        buff.put(new byte[] { (byte) (bytesPerSecond), (byte) (bytesPerSecond >> 8), (byte) (bytesPerSecond >> 16), (byte) (bytesPerSecond >> 24) }); //bytes per second
        buff.put(new byte[] { (byte) (bytesPerSample), (byte) (bytesPerSample >> 8) }); //number of bytes in one sample, for all channels
        buff.put(new byte[] { (byte) (bitsPerSample), (byte) (bitsPerSample >> 8) }); //bits per sample -> 16 or 24
        buff.put("data".getBytes());
        buff.put(new byte[] { (byte) (rawData.length * 2), (byte) ((rawData.length * 2) >> 8), (byte) ((rawData.length * 2) >> 16), (byte) ((rawData.length * 2) >> 24) });
    	for (int i = 0; i < rawData.length; i++) {
    		int value = rawData[i];
    		value = value - 0x20;
    		buff.put((byte) value);
    		buff.put((byte) value);
    	}
    	
    	return buff.array();
	}
}
