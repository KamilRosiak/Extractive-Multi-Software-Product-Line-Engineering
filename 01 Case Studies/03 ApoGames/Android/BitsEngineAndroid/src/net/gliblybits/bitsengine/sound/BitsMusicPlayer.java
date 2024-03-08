
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

import java.io.FileInputStream;

import com.peculiargames.andmodplug.MODResourcePlayer;

import net.gliblybits.bitsengine.core.BitsApp;
import net.gliblybits.bitsengine.utils.BitsLog;
import android.media.MediaPlayer;
import android.net.Uri;

public final class 
BitsMusicPlayer {
	
	public static final int TYPE_MIDI 	= 0;

	public static final int TYPE_WAV 	= 2;
	public static final int TYPE_OGG 	= 3;
	public static final int TYPE_XM 	= 4;
	
	private static BitsMusicPlayer instance = new BitsMusicPlayer();
	
	private MODResourcePlayer 	mMODPlayer	= null;
	private MediaPlayer 		mAudio		= null;
	private int					mResourceId	= -1;
	private String				mFile		= null;
	private int					mType		= -1;
	private float				mPitch		= 1f;
	private float				mVolume		= 1f;
	private	boolean				mLoop		= false;
	
	private 
	BitsMusicPlayer() {
		
	}
	
	public static final BitsMusicPlayer 
	getInstance() {
		
		return instance;
	}
	
	public static final void 
	resetInstance( ) {
		
		BitsLog.d( "BitsMusicPlayer - resetInstance", "Resetting the singleton instance..." );
		instance.release( );
	}
	
	private final void
	load( ) {
		
		if( this.mResourceId != -1 ) {
			BitsLog.d( "BitsMusicPlayer - load", "Loading music file via resource id: " + this.mResourceId );
			if( this.mType == TYPE_XM ) {
				this.mMODPlayer = new MODResourcePlayer( BitsApp.sAppContext );
				this.mMODPlayer.LoadMODResource(this.mResourceId);
			} else {
				this.mAudio = MediaPlayer.create( BitsApp.sAppContext, this.mResourceId );
			}
		}
		
		if( this.mFile != null && this.mFile.equals("") == false ) {
			BitsLog.d( "BitsMusicPlayer - load", "Loading music via file: " + this.mFile );
			if( this.mType == TYPE_XM ) {
				this.mMODPlayer = new MODResourcePlayer( BitsApp.sAppContext );
				this.mMODPlayer.setLogOutput( false );
				try {
					final FileInputStream fis = new FileInputStream( this.mFile );
					final byte[] buffer = new byte[(int) fis.getChannel().size()];
					fis.read(buffer);
					fis.close();
					this.mMODPlayer.LoadMODData( buffer );
				} catch( Exception e ) {
					BitsLog.e(e, "BitsMusicPlayer - load", "Unable to load XM music file: " + this.mFile);
					this.mMODPlayer = null;
				}
			} else { //no XM
		    	if( this.mFile.startsWith( "?" ) == false ) {
					this.mAudio = MediaPlayer.create( BitsApp.sAppContext, Uri.parse(this.mFile) );
		    	} else {
		    		this.mAudio = new MediaPlayer();
		    		try {
						this.mAudio.setDataSource("android.resource://" + this.mFile.substring(1));
						this.mAudio.prepare();
					} catch (Exception e) {
						BitsLog.e(e, "BitsMusicPlayer - load", "Unable to load classpath music file: " + this.mFile);
					}
		    	}
			}
		}
		
		if( this.mType == TYPE_XM ) {
			if( this.mMODPlayer == null ) {
				BitsLog.e( "BitsMusicPlayer - load", "Unable to load the music file. Please use an audio format that is supported by Android and make sure the given file exists: " + this.mFile );				
			}
		} else {
			if( this.mAudio == null ) {
				BitsLog.e( "BitsMusicPlayer - load", "Unable to load the music file. Please use an audio format that is supported by Android and make sure the given file exists: " + this.mFile );
			}
		}
	}
	
	public final void
	setVolume( 
			final float volume ) {
		
		if( volume < 0f ) {
			this.mVolume = 0f;
		} else {
			if( volume > 1f ) {
				this.mVolume = 1f;
			} else {
				this.mVolume = volume;				
			}
		}
		
		if( this.mType != TYPE_XM ) {
			if( this.mAudio != null ) {
				this.mAudio.setVolume(this.mVolume, this.mVolume);
			}
		} else {
			if( this.mMODPlayer != null ) {
				this.mMODPlayer.setVolume(this.mVolume);
			}
		}
	}
	
	public final float
	getVolume( ) {
		
		return this.mVolume;
	}
	
	public final void 
	release( ) {

		if( this.mAudio != null ) {
			if( this.mAudio.isPlaying() == true || this.mAudio.isLooping() == true ) {
				this.mAudio.stop();
			}
			this.mAudio.release();
			this.mAudio = null;
		}
		
		if( this.mMODPlayer != null ) {
			this.mMODPlayer.StopAndClose();
			this.mMODPlayer.UnLoadMod();
			this.mMODPlayer = null;
		}

		this.mResourceId = -1;
		this.mFile = null;
		this.mType = -1;
		this.mPitch = 1f;
		this.mVolume = 1f;
		this.mLoop = false;
	}
	
	public final void
	play(
			final String file,
			final float pitch,
			final float volume,
			final boolean loop ) {
		
		int type = -1;
		
		if( file.matches("(?i)^.*[.]ogg$") == true ) {
			type = TYPE_OGG;
			this.play(file, type, pitch, volume, loop);
			return;
		}
		if( file.matches("(?i)^.*[.]mid$") == true ) {
			type = TYPE_MIDI;
			this.play(file, type, pitch, volume, loop);
			return;
		}
		if( file.matches("(?i)^.*[.]wav$") == true ) {
			type = TYPE_WAV;
			this.play(file, type, pitch, volume, loop);
			return;
		}
		if( file.matches("(?i)^.*[.]xm$") == true ) { //TODO kann noch mehr als nur XM!!!
			type = TYPE_XM;
			this.play(file, type, pitch, volume, loop);
			return;
		}
		
		BitsLog.e("BitsMusicPlayer - play", "The file type is not supported: " + file);
	}

	public final void
	play( 
			final String file, 
			final int type,
			final float pitch, 
			final float volume, 
			final boolean loop ) {

		this.play(file, -1, type, pitch, volume, loop);
	}
	
	public final void
	play( 
			final int resourceId,
			final int type,
			final float pitch,
			final float volume, 
			final boolean loop ) {

		this.play(null, resourceId, type, pitch, volume, loop);
	}
	
	private final void
	play(
			final String file, 
			final int resourceId,
			final int type,
			final float pitch,
			final float volume, 
			final boolean loop ) {
		
		this.release();
		this.mType = type;
		this.mLoop = loop;
		
		if( resourceId > 0 ) {
			this.mResourceId = resourceId;
			this.load();
			if( this.mType == TYPE_XM ) {
				if( this.mMODPlayer != null ) {
					this.mMODPlayer.start();
				}
			} else {
				if( this.mAudio != null ) {
					this.mAudio.setLooping(loop);
					this.mAudio.start();
				}
			}
			this.setVolume(volume);
			return;
		}
		
		if( file != null && file.equals("") == false ) {
			this.mFile = file;
			this.load();
			if( this.mType == TYPE_XM ) {
				if( this.mMODPlayer != null ) {
					this.mMODPlayer.start();
				}
			} else {
				if( this.mAudio != null ) {
					this.mAudio.setLooping(loop);
					this.mAudio.start();
				}
			}
			this.setVolume(volume);
			return;
		}
	}
	
	public final void
	restart( ) {
		
//		if( this.mAudio != null ) {
//			if( this.mAudio.isPlaying() == true || this.mAudio.isLooping() == true ) {
//				this.mAudio.stop();
//			}
//			this.mAudio.release();
//			this.mAudio = null;
//		}
//		
//		if( this.mMODPlayer != null ) {
//			this.mMODPlayer.StopAndClose();
//			this.mMODPlayer.UnLoadMod();
//			this.mMODPlayer = null;
//		}

		if( this.mResourceId > 0 ) {
//			this.load();
			if( this.mType == TYPE_XM ) {
				if( this.mMODPlayer != null ) {
					this.mMODPlayer.start();
				}
			} else {
				if( this.mAudio != null ) {
					this.mAudio.setLooping(this.mLoop);
					this.mAudio.start();
				}
			}
			this.setVolume(this.mVolume);
			return;
		}
		
		if( this.mFile != null && this.mFile.equals("") == false ) {
//			this.load();
			if( this.mType == TYPE_XM ) {
				if( this.mMODPlayer != null ) {
					this.mMODPlayer.start();
				}
			} else {
				if( this.mAudio != null ) {
					this.mAudio.setLooping(this.mLoop);
					this.mAudio.start();
				}
			}
			this.setVolume(this.mVolume);
			return;
		}
	}
	
	public final void
	setPosition( 
			final int pos ) {
		
		if( this.mType != TYPE_XM ) {
			if( this.mAudio != null ) {
				this.mAudio.seekTo( pos );
			}
		} else {
			BitsLog.w( "BitsMusicPlayer - setPosition", "Unable to set the position for the XM music. This operation is not supported by the XM module." );
		}
	}
	
	public final int
	getPosition( ) {
		
		if( this.mType != TYPE_XM ) {
			if( this.mAudio != null ) {
				return this.mAudio.getCurrentPosition();
			}
		} else {
			BitsLog.w( "BitsMusicPlayer - getPosition", "Unable to get the position of the XM music. This operation is not supported by the XM module." );
		}

		return 0;
	}
	
	public final boolean
	isPlaying() {
		
		if( this.mType != TYPE_XM ) {
			if( this.mAudio != null ) {
				if( this.mAudio.isPlaying() == true || this.mAudio.isLooping() == true ) {
					return true;
				}
			}
		} else {
			BitsLog.w( "BitsMusicPlayer - isPlaying", "Unable to get the status of the XM music. This operation is not supported by the XM module." );
		}
		
		return false;
	}
	
	public final void 
	stop( ) {
		
		if( this.mType == TYPE_XM ) {
			if( this.mMODPlayer != null ) {
				this.mMODPlayer.StopAndClose();
			}
		} else {
			if( this.mAudio != null ) {
				if( this.mAudio.isPlaying() == true || this.mAudio.isLooping() == true ) {
					this.mAudio.stop();
				}
			}
		}
	}
}
