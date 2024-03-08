
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

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import net.gliblybits.bitsengine.input.BitsInput;
import net.gliblybits.bitsengine.render.BitsGLRenderer;
import net.gliblybits.bitsengine.sound.BitsMusicPlayer;
import net.gliblybits.bitsengine.sound.BitsSoundFactory;
import net.gliblybits.bitsengine.utils.BitsLog;

/**
 * BitsApp is the main activity class and the entry point for the android system.<br>
 * This abstract class needs to be overridden by the game itself.<br>
 * The BitsApp class will initialize the app settings and start the game thread and the<br>
 * render thread.<br>
 * It will take care of the android life cycle, too.<br>
 * <br>
 * @author Marc Wiedenhoeft - GliblyBits
 * @version 0.1 - Streak
 * @since 0.1 - Streak
 */
public abstract class 
BitsApp 
extends Activity
implements OnAudioFocusChangeListener {

	/*
	 ************************************************************************
	 ******** STATIC FIELDS AND METHODS - BEGIN *****************************
	 ************************************************************************ 
	 */	
	/**
	 * App will change it's orientation by the internal sensors.
	 */
	public static final int ORIENTATION_BY_SENSOR = 0;
	
	/**
	 * App will start in landscape mode and change it's orientation by the internal sensors.
	 */
	public static final int ORIENTATION_LANDSCAPE = 1;
	
	/**
	 * App will start in portrait mode and change it's orientation by the internal sensors.
	 */
	public static final int ORIENTATION_PORTRAIT = 2;
	
	/**
	 * App will not allow to turn of the display.
	 */
	public static final int DISPLAY_ALLOW_OFF = 0;

	/**
	 * App will allow to turn off the display.
	 */
	public static final int DISPLAY_KEEP_ON = 1;
	
	/**
	 * Default: DISPLAY_KEEP_ON<br>
	 * <br>
	 * This value handles the displays sleep mode. It has to be set in the onInit method.
	 */
	public static int sDisplayMode = DISPLAY_KEEP_ON;
    
	/**
	 * Default: null<br>
	 * <br>
	 * Androids app context.<br>
	 */
	public static Context sAppContext = null;
	
	/**
	 * Default: BitsEngine-Game<br>
	 * <br>
	 * The Apps name.
	 */
	public static String sAppName = "BitsEngine-Game";
	
	/**
	 * Default: BitsEngine-Game<br>
	 * <br>
	 * The App will write and read public files from this folder.<br>
	 * The folder will be created on the SD-Card or internal Memory if a public file is written.
	 */
	public static String sPublicStorageFolder = "BitsEngine-Game";
	
	/**
	 * Default: null<br>
	 * <br>
	 * A reference to the BitsApp class instance.<br>
	 */
	public static BitsApp sBitsAppInstance = null;
	
	/**
	 * Default: 1<br>
	 * <br>
	 * Width of the render surface.<br>
	 * This value represents the width of the screen.<br>
	 */
	public static int sViewportWidth = 1;
	
	/**
	 * Default: 0<br>
	 * <br>
	 * If the aspect ratio of the game isn't the aspect ration of the screen, the current viewport must be<br>
	 * shifted either on the x-axis or the y-axis.<br>
	 * This value represents the amount of pixels the viewport needs to be shifted on the x-axis, so the<br>
	 * viewport will be centered.<br>  
	 */
	public static int sViewportPaddingWidth = 0;

	/**
	 * Default: 1<br>
     * <br>
	 * Height of the render surface.<br>
	 * This value represents the height of the screen.<br>
	 */
	public static int sViewportHeight = 1;
	
	/**
	 * Default: 0<br>
	 * <br>
	 * If the aspect ratio of the game isn't the aspect ration of the screen, the current viewport must be<br>
	 * shifted either on the x-axis or the y-axis.<br>
	 * This value represents the amount of pixels the viewport needs to be shifted on the y-axis, so the<br>
	 * viewport will be centered.<br>  
	 */	
	public static int sViewportPaddingHeight = 0;

	/**
	 * Default: 800<br>
	 * <br>
	 * Width of the game itself.<br>
	 * It is used to scale the game size up or down,<br>
	 * according to the real screen resolution.<br>
	 */
	public static int sGameWidth = 800;

	/**
	 * Default: 480<br>
	 * <br>
	 * Height of the game itself.<br>
	 * Is used to scale the game size up or down,<br>
	 * according to the real screen resolution.<br>
	 */
	public static int sGameHeight = 480;
	
	/**
	 * Default: 1.0f<br>
	 * <br>
	 * Rate of scaling that occurs if the game renders itself.<br>
	 * 1.0f is a 100% scale and occurs if the original GameWidth/GameHeight values are equal to the device native screen resolution.<br>
	 * A lower value of e.g. 0.9f will scale the original GameWidth/GameHeight to 90% to fit the game into the native screen resolution.<br>
	 * Vice versa for higher values.<br>
	 */
	public static float sScaleFactor = 1.0f;
	
	/**
	 * Default: 100f<br>
	 * <br>
	 * Defines what precision to use for the sScaleFactor.<br>
	 * This might help to prevent or minimize gaps between polygons (images, triangles, etc.) while applying a scale factor.<br>
	 * If this value is 100f and the scale factor is 1.56667f, the new sScaleFactor will be 1.57f.<br>
	 * If this value is 1000f and the scale factor is 1.56667f, the new sScaleFactor will be 1.567f.<br>
	 */
	public static float sScalePrecision = 100f;
	
	/**
	 * Default: false<br>
	 * <br>
	 * Display androids app title bar?<br>
	 */
	public static boolean sWantTitleBar = false;
	
	/**
	 * Default: true<br>
	 * <br>
	 * Display androids status bar?<br>
	 */
	public static boolean sWantFullscreen = true;
	
	/**
	 * Default: ORIENTATION_BY_SENSOR<br>
	 * <br>
	 * Apps orientation mode.<br>
	 * <br>
	 * Use: ORIENTATION_BY_SENSOR / ORIENTATION_LANDSCAPE / ORIENTATION_PORTRAIT
	 */
	public static int sOrientationMode	= ORIENTATION_BY_SENSOR;
	
	/**
	 * Default: 3200<br>
	 * <br>
	 * The max. number of images that can be rendered together with only one call to OpenGL ES.<br>
	 * <br>
	 * The engine will fill a pre-defined buffer with equal images. If this buffer is full the engine
	 * will render all the images in the buffer with one call to OpenGL ES.<br>
	 * So if you set sMaxRenderBatch to 10 the engine will render 10 images with one call instead of<br>
	 * calling OpenGL ES 10 times.<br>
	 * If you set this value to 1 the engine will behave like a non-optimized renderer.<br>
	 * <br>
	 * Images are equal, if they use the same texture and rotation! 
	 */
	public static int sRenderBufferSize = 3200;
	
	/**
	 * Default: 180<br>
	 * <br>
	 * The maximum number of points used to render a circle/oval.<br>
	 * The higher this number is, the smoother the circle. This can be useful for very large circles.<br>
	 * The smaller this number is, the more non-smooth the circle. This can be useful for smaller circles.<br>
	 */
	public static int sMaxCirclePoints = 180;
	
	/**
	 * Default: 2<br>
	 * <br>
	 * The maximum number of fingers, that may be on the screen at the same time.<br>
	 */
	public static int sMaxTouchPointer	= 2;
	
	/**
	 * Default: 0.25f<br>
	 * <br>
	 * This value defines the amount of width a pointer need to move before a drag event will be created.
	 */
	public static float sPointerSensitivity = 5f;
	
	/**
	 * Default: 60<br>
	 * <br>
	 * This value is not needed anymore in the Android version. 
	 * <br>
	 * This values defines the cap of the frame rate.<br>
	 * The app tries to reach this maximum, but won't go far beyond this value.<br>
	 */
//	public static int sMaxFPS = 60;
	
	/**
	 * Default: 25<br>
	 * <br>
	 * This values defines the maximum game update cycles that the app will try to achieve per second.<br>
	 */
	public static int sMaxUpdate = 25;
	
	/**
	 * Default: 10<br>
	 * <br>
	 * This value defines how many updates will be processed before a frame will be rendered.<br>
	 */
	public static int sMaxUpdatesBeforeRender = 10;
	
	/**
	 * The render thread.
	 */
	private BitsGLRenderer mRenderer = null;
	
	/**
	 * This abstract method will be called by the BitsApp class, if the onCreate method
	 * of the android lifecycle is called. So it won't be called, if the app has already been created.<br>
	 * The method is called before the app will be initialized. So this is the right<br>
	 * point to modify the settings of the BitsGame class.<br>
	 */
	protected abstract void onCreateApp( );
	protected abstract void onInitApp();
	
	protected abstract void onPauseApp();
	protected abstract void onResumeApp();

	/**
	 * The method is called before the app will be stopped. So this is the last chance<br>
	 * to save some game data.<br>
	 * But keep in mind, that this method will block the shutdown. So keep it short and simple.
	 */
	protected abstract void onStopApp( );
	
	protected abstract void onDestroyApp( );
	
	protected abstract void onAddView( );
		
	/*
	 ************************************************************************
	 ******** ANDROID APP LIFECYCLE - BEGIN *********************************
	 ************************************************************************ 
	 */
	
	private RelativeLayout 	mRelativeLayout = null;
	
	@Override
    /**
     * See: android life cycle
     */
    public final void 
    onCreate( 
    		final Bundle savedInstanceState ) {
		
        super.onCreate( savedInstanceState );

        //Sets the global app context.
        BitsLog.d( "BitsApp - onCreate", "Setting application context..." );
        BitsApp.sAppContext = this.getApplicationContext( );
        BitsApp.sBitsAppInstance = this;
        
        //Resets the BitsGame instance, because it's thread may be stopped
        //previously and a stopped thread can't be started again.
        BitsLog.d( "BitsApp - onCreate", "Resetting BitsGame singleton..." );
        BitsGame.createNewInstance( );

        //Calls the abstract method onCreate()
        BitsLog.d( "BitsApp - onCreate", "Calling abstract onCreate..." );
        this.onCreateApp( );
        
        //Define a layout
   		this.mRelativeLayout = new RelativeLayout(this);

        BitsLog.d( "BitsApp - onCreate", "Initializing the game thread..." );
        BitsGame.getInstance().init();
        
        //Sets the global orientation mode to landscape.
        BitsLog.d( "BitsApp - onCreate", "Setting the orientation mode..." );
        BitsGame.getInstance().setScreenOrientation( BitsApp.sOrientationMode );
        
        //Sets the title bar appearance.
        if( !BitsApp.sWantTitleBar ) {
        	BitsLog.d( "BitsApp - onCreate", "Hiding the title bar..." );
        	this.requestWindowFeature( Window.FEATURE_NO_TITLE );
        } else {
        	BitsLog.d( "BitsApp - onCreate", "Showing the title bar..." );
        }
        
        //Sets the android status bar appearance.
        if( BitsApp.sWantFullscreen ) {
        	BitsLog.d( "BitsApp - onCreate", "Going fullscreen..." );
        	this.getWindow().setFlags(
        			WindowManager.LayoutParams.FLAG_FULLSCREEN,
        			WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
        	BitsLog.d( "BitsApp - onCreate", "No fullscreen..." );
        }
        
        if( BitsApp.sDisplayMode == DISPLAY_KEEP_ON ) {
        	BitsLog.d("BitsApp - onCreate", "Screen will not be allowed to turn off.");
        	this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
        	BitsLog.d("BitsApp - onCreate", "Screen will be allowed to turn off.");
        }

        //Creates the OpenGL ES renderer (incl. render thread).
        BitsLog.d( "BitsApp - onCreate", "Creating the render thread..." );
       	this.mRenderer = new BitsGLRenderer( this );
       	this.mRenderer.setRenderMode( GLSurfaceView.RENDERMODE_CONTINUOUSLY );
       	this.mRenderer.setOnKeyListener( new OnKeyListener( ) {
       		//put key events into the BitsInput singleton
			@Override
			public boolean onKey( View v, int keyCode, KeyEvent event ) {
				BitsInput.getInstance().addKeyEvent( event );
				return false;
			} 
       		
       	});
        this.mRenderer.setOnTouchListener( new OnTouchListener( ) {
        	//put touch events into the BitsInput singleton
   			@Override
   			public boolean onTouch( final View v, final MotionEvent event ) {
   				if( BitsInput.getInstance() != null ) {
   					BitsInput.getInstance().addTouchEvent( event );
   				}
				return true;
   			}
   		});

        //Sets the SurfaceView of the renderer to the apps ContentView.
		this.mRelativeLayout.addView( this.mRenderer );
        this.onAddView();
        this.setContentView( this.mRelativeLayout );

        //Start the game thread.
        BitsGame.getInstance().setRenderer( this.mRenderer );
        
        BitsLog.d( "BitsApp - onCreate", "Calling abstract onInit..." );
        this.onInitApp( );
    }
	
	protected final void 
	addView( 
			final View v, 
			final RelativeLayout.LayoutParams params ) {
		
		if( this.mRelativeLayout != null ) {
			this.mRelativeLayout.addView( v, params );
		} else {
			BitsLog.e("BitsApp - addView", "View is NULL!");
		}
	}
    
    @Override
    protected final void 
    onStart( ) {
        super.onStart( );
        
        BitsLog.d( "BitsApp - onStart", "Starting game..." );        
//       	BitsMusicPlayer.getInstance().restart();
    }
    
	@Override
	protected final void 
	onResume( ) {
		super.onResume( );
		
		BitsLog.d( "BitsApp - onResume", "Resuming game..." );
       	
       	final AudioManager audioManager = (AudioManager)sAppContext.getSystemService(Context.AUDIO_SERVICE);
		if( audioManager != null ) {
			// Request audio focus for playback
			final int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
			if (result != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
			    BitsLog.w("BitsApp - onResume", "Unable to gain the audio focus!");
			}
		} else {
			BitsLog.e("BitsApp - onResume", "AudioManagerService is NULL! Unable to gain the audio focus!");
		}
       	
       	this.onResumeApp();
       	
       	this.mRenderer.onResume( );
	}

	@Override
	protected final void 
	onPause( ) {
		super.onPause( );
		BitsLog.d( "BitsApp - onPause", "Pausing game..." );

       	BitsSoundFactory.getInstance().stopAll();
       	BitsMusicPlayer.getInstance().stop();
       	this.mRenderer.onPause( );
       	BitsGame.getInstance().onPauseApp( );
       	
       	this.onPauseApp();
	}	

	@Override
	protected final void 
	onStop( ) {
		super.onStop( );
		BitsLog.d( "BitsApp - onStop", "Stopping game..." );
		BitsGame.getInstance().onStopApp();
	}

	@Override
	protected final void 
	onDestroy( ) {
		super.onDestroy( );
		BitsLog.d( "BitsApp - onDestroy", "Destroying game..." );
		this.onDestroyApp();
	}
	
	@Override
	/**
	 * This is an android method that will be called, if the back button on the
	 * device has been pressed.<br>
	 * If the static 'OverrideBackButton' boolean value of the BitsGame class<br>
	 * is set 'true' the method will call the 'backButtonPressed'-Method of the BitsGame class.<br>
	 * If the value is 'false', the method will call the standard android method and will close
	 * the app.
	 */
	public final void 
	onBackPressed( ) {
		BitsLog.d( "BitsApp", "BackButton pressed...calling own method" );
		BitsGame.getInstance().backButtonPressed( );
	}
	
	@Override
	public final void 
	onConfigurationChanged( final Configuration config ) {
		super.onConfigurationChanged( config );
		BitsLog.d( "BitsApp", "A configuration changed...(screen rotation?)" );
	}

	/*
	 ************************************************************************
	 ******** ANDROID APP LIFECYCLE - END ***********************************
	 ************************************************************************ 
	 */
}