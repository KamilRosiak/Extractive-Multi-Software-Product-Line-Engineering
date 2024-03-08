
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

package net.gliblybits.bitsengine.core;

import net.gliblybits.bitsengine.graphics.opengl.BitsGLFactory;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLImage;
import net.gliblybits.bitsengine.gui.BitsScreen;
import net.gliblybits.bitsengine.utils.BitsTimer;

public final class 
BitsEngineScreen 
extends BitsScreen {
	
	private BitsGLImage 	mBitsEngineLogoImage 	= null;
	private final BitsTimer	mTimer 					= new BitsTimer( );
	private float			mFader					= 0;
	private int				mState					= -1;
	private int				mNextScreenId			= 2;
	
	public 
	BitsEngineScreen( 
			final int id, 
			final int nextScreenId ) {
	
		super( id );
		
		this.mNextScreenId = nextScreenId;
	}
	
	@Override
	public final void 
	onInitScreen( ) {
		
		this.mBitsEngineLogoImage = BitsGLFactory.getInstance().getImage( "?net/gliblybits/bitsengine/BitsEngineLogo.png", BitsGLImage.FILTER_LINEAR, true );
		
		BitsGLGraphics.setClearColor(1f, 1f, 1f, 1f);
		
		this.mTimer.start( );
	}
	
	@Override
	public final void 
	onPauseScreen( ) {
		
		this.mTimer.pause( );
	}

	@Override
	public final void 
	onResumeScreen( ) {
		
		this.mTimer.start( );
	}
	
	@Override
	public final void 
	onFinishScreen( ) {
		
		this.mTimer.stop( );
		
		BitsGLFactory.getInstance( ).markForReleasing( this.mBitsEngineLogoImage );
	}

	public final void 
	onDrawFrame( 
			final BitsGLGraphics g ) {
		
		if( this.mState == -1 ) {
			g.setColor( 1f, 1f, 1f, 0f );
		} else {
			g.setColor( 1f, 1f, 1f, this.mFader );
		}
		g.drawImage(
				this.mBitsEngineLogoImage,
				( BitsApp.sGameWidth / 2 ) - 200,
				( BitsApp.sGameHeight / 2 ) - 94,
				400,
				188f );			

		super.onDrawFrame(g);
	}
	
	public final void 
	onUpdate( 
			final float delta ) {
		
		super.onUpdate( delta );

		if( this.mState == -1 ) {
			if( this.mTimer.getMilliseconds( ) > 750L ) {
				this.mState = 0;
			}
		}
		
		if( this.mState == 0 ) {
			if( this.mFader < 1f ) {
				this.mFader += 2.5f * delta;
			} else {
				this.mFader = 1f;
				this.mState = 1;
				this.mTimer.start( );
			}
		}

		if( this.mState == 1 ) {
			if( this.mTimer.getMilliseconds() > 1000L ) {
				this.mState = 2;
			}
		}
		
		if( this.mState == 2 ) {
			if( this.mFader > 0f ) {
				this.mFader -= 2.5f * delta;
			} else {
				this.mFader = 0f;
				BitsGame.getInstance( ).showScreen( this.mNextScreenId );
			}
		}
	}

	@Override
	public final void 
	onBackButtonPressed( ) {
		
		BitsGame.getInstance().finishApp();
	}
}
