
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

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.SystemClock;
import android.widget.Toast;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLFactory;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;
import net.gliblybits.bitsengine.gui.BitsScreen;
import net.gliblybits.bitsengine.input.BitsInput;
import net.gliblybits.bitsengine.io.BitsIO;
import net.gliblybits.bitsengine.render.BitsGLRenderer;
import net.gliblybits.bitsengine.render.commands.BitsRenderCommand;
import net.gliblybits.bitsengine.render.commands.BitsRenderCommandPool;
import net.gliblybits.bitsengine.render.commands.BitsRenderCommandQueues;
import net.gliblybits.bitsengine.sound.BitsMusicPlayer;
import net.gliblybits.bitsengine.sound.BitsSoundFactory;
import net.gliblybits.bitsengine.utils.BitsLog;
import net.gliblybits.bitsengine.utils.BitsTimer;
import net.gliblybits.bitsengine.utils.BitsVibration;

/**
 * The BitsGame class is the core of the BitsEngine.<br>
 * It contains the game thread, all the app settings and methods to<br>
 * control the game flow.<br>
 * <br>
 * @author Marc Wiedenhoeft - GliblyBits
 * @version 0.1 - Streak
 * @since 0.1 - Streak
 */
public final class 
BitsGame 
extends Thread {

    /**
     * Default: null<br>
	 * <br>
     * A reference to the renderer thread.
     */
	public BitsGLRenderer mRenderer = null;
	
    /**
     * Default: false<br>
	 * <br>
     * Game-/Logic-thread is running?
     */
    private boolean isRunning = false;
    
    /**
     * Default: false<br>
	 * <br>
	 * Game-/Logic-thread is paused.
     */
    private boolean isPaused = false;
    
    /**
     * Default: new Object()<br>
	 * <br>
	 * Object to lock the game-/logic-thread, e.g. if the game is paused,<br>
	 * or the logic is waiting for the render thread to complete and take<br>
	 * the new render queue.<br>
     */
    private final Object mLogicLock = new Object();

    /**
     * Default: true<br>
	 * <br>
	 * Defines if the new render queue has been taken.<br>
	 * The game-/logic-thread waits until this value is set to 'true'<br>
	 * before the next render queue will be build up.<br>
	 * <br>
	 * Initial 'true', or both threads will wait until the end of all days.<br>
     */
    private boolean wasRenderQueueTaken = true;
    
    /**
     * Defines if a new BitsScreen has to be shown.<br>
     */
    private boolean	wantScreenChange = false;
    
    /**
     * Defines the index of the next (new) BitsScreen that will be shown.<br>
     */
    private int mNextScreenIndex = 0;
    
    /**
     * Defines the index of the current visible/active BitsScreen.<br>
     */
    private int	mActiveScreenIndex = 1;

    /**
     * A map of all registered BitsScreens.<br>
     */
    private final ConcurrentHashMap<Integer, BitsScreen> mScreenHashMap = new ConcurrentHashMap<Integer, BitsScreen>( );

    /**
     * A pool of pre-created BitsRenderCommands.<br>
     */
    private final BitsRenderCommandPool mRenderCommandPool = new BitsRenderCommandPool( );
    
    /**
     * Reference to the RenderQueueManager that handles the swapping and <br>
     * clearing of the render queues.<br>
     */
    private final BitsRenderCommandQueues mRenderCommandQueues = new BitsRenderCommandQueues( this.mRenderCommandPool );
    
    /**
     * The BitsGraphics object that is passed to the users app via the onRender() method.<br>
     */
    private final BitsGLGraphics mGraphics = new BitsGLGraphics( this.mRenderCommandQueues, this.mRenderCommandPool );
    
    /**
     * Frame rate of the last second.<br>
     */
    private int mFps = 0;
    
    /**
     * Update count of the last second<br>
     */
    private int mUps = 0;
    
    private boolean mWantFinish = false;
    
    /**
     * Singleton reference to this class.<br>
     */
    private static BitsGame instance = null;
    
    /**
     * Constructor must be private. (Singleton)
     */
	private 
	BitsGame( )	{
		
		super();
		this.setName("BitsGame-Thread");
	}
	
	/**
	 * This static method return THE one and only instance of BitsGame.
	 * 
	 * @return - instance of BitsGame
	 */
	public static final	BitsGame 
	getInstance( ) {
		
		return instance;
	}
	
	/**
	 * YOU MUST NOT CALL THIS METHOD!<br>
	 * <br>
	 * This static method will be called by the engine itself,<br>
	 * if the app has been re-created.<br>
	 * By calling this method, the old BitsGame instance will<br>
	 * be set to NULL and a new instance will be created.<br>
	 * This is due to threads that has been stopped, can't be<br>
	 * started again.<br>
	 * So calling this method while the app is running, will<br>
	 * destroy the current BitsGame object and cause the app to<br>
	 * force close.<br>
	 */
	public static final void 
	createNewInstance( ) {
		
		instance = null;
		instance = new BitsGame( );
		
	}
	
	public final void 
	init() {	

		BitsIO.initInstance();
		BitsInput.initInstance();
	}
	
	public static final void 
	resetInstance( ) {

		BitsSoundFactory.resetInstance();
		BitsMusicPlayer.resetInstance();
		BitsVibration.resetInstance();
		BitsIO.resetInstance();
		BitsInput.resetInstance();

		BitsLog.d("BitsGame - resetInstance", "Closing App...");
		BitsLog.closeLogFile();
		
//		BitsApp.sBitsAppInstance.finish();
		BitsApp.sBitsAppInstance = null;
		BitsApp.sAppContext = null;
		
		instance = null;

		System.gc();
	}
	
	/**
	 * Call this method, if you want the app to be closed.<br>
	 * <br>
	 * This method prepares the app to shut itself down.<br>
	 * It will call the finish() method of the BitsApp class<br>
	 * and will stop the game thread by calling the onStop() method.<br>
	 */
	public final void 
	finishApp()	{
		
		//call onFinish on the current active BitsScreen
		BitsLog.d("BitsGame - finishApp", "Calling onFinishScreen on current BitsScreen.");
		final BitsScreen screen = this.mScreenHashMap.get( this.mActiveScreenIndex );
		if( screen != null ) {
			screen.onFinishScreen();
		} else {
			BitsLog.e("BitsGame - finishApp", "Screen is NULL. Can't finish the current acitve screen with id: " + this.mActiveScreenIndex);
			throw new RuntimeException("Screen is NULL. Can't finish the current acitve screen with id: " + this.mActiveScreenIndex);
		}
		BitsGame.getInstance().mRenderer.queueEvent(new Runnable() {
            @Override
            public void run() {
        		BitsGLFactory.resetInstance();
            }
        });
		
		BitsLog.d("BitsGame - finishApp", "App will be finished...soon...");
		this.mWantFinish = true;
		BitsApp.sBitsAppInstance.finish();
	}
	
	/**
	 * This method will be called by the engine itself,<br>
	 * if the back button on the device is pressed.<br>
	 * It calls the onBackButtonPressed method of the<br>
	 * current active BitsScreen.<br>
	 */
	public final void 
	backButtonPressed( ) {
		
		if( this.mActiveScreenIndex >= 0 ) {
			final BitsScreen screen = this.mScreenHashMap.get( this.mActiveScreenIndex );
			if( screen != null ) {
				screen.onBackButtonPressed( );
			} else {
				BitsLog.e( "BitsGame - backButtonPressed", "The active BitsScreen is NULL. ActiveScreenIndex: " + String.valueOf(this.mActiveScreenIndex) );
			}
		}
	}
	
	/**
	 * Call this method to show a short status message in your app.<br>
	 * <br>
	 * @param message - the message you'd like to show
	 */
	public final void 
	showToastMessage( 
			final String message ) {
		
		final Toast t = Toast.makeText(BitsApp.sAppContext, message, Toast.LENGTH_LONG);
		t.show();
	}
	
	/**
	 * This method returns the current apps screen orientation.<br>
	 * <br>
	 * @return - current screen orientation
	 */
	public final int 
	getScreenOrientation( ) {
		
		switch( BitsApp.sAppContext.getResources( ).getConfiguration().orientation ) {
			case Configuration.ORIENTATION_LANDSCAPE:
				return BitsApp.ORIENTATION_LANDSCAPE;
			
			case Configuration.ORIENTATION_PORTRAIT:
				return BitsApp.ORIENTATION_PORTRAIT;
			
			default:
				return BitsApp.ORIENTATION_BY_SENSOR;
		}
	}
	
	/**
	 * This method sets the new orientation mode.<br>
	 * <br>
	 * Possible values are:<br>
	 * ORIENTATION_LANDSCAPE<br>
	 * ORIENTATION_PORTRAIT<br>
	 * ORIENTATION_BY_SENSOR<br>
	 * <br>
	 * @param orientation - the new orientation mode
	 */
	public final void 
	setScreenOrientation( 
			final int orientation ) {
		
        switch( orientation ) {
        	case BitsApp.ORIENTATION_LANDSCAPE:
        		BitsLog.d( "BitsGame - setScreenOrientation", "Setting orientation mode to landscape..." );                
        		BitsApp.sBitsAppInstance.setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE );
                break;

        	case BitsApp.ORIENTATION_PORTRAIT:
        		BitsLog.d( "BitsGame - setScreenOrientation", "Setting orientation mode to portrait..." );                
        		BitsApp.sBitsAppInstance.setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
                break;
            
        	default:
        		BitsLog.d( "BitsGame - setScreenOrientation", "Setting orientation mode to sensor..." );                
        		BitsApp.sBitsAppInstance.setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_SENSOR );
                break;
        }		
	}
	
	/**
	 * This method sets the screen orientation to the opposing orientation.<br>
	 * If the current orientation is ORIENTATION_LANDSCAPE, it will<br>
	 * set the new orientation to ORIENTATION_PORTRAIT and vice versa.<br>
	 * <br>
	 * The method will prefer the ORIENTATION_LANDSCAPE setting, if the<br>
	 * current orientation is unknown (due to an unknown error).
	 */
	public final void 
	rotateScreenOrientation( ) {
		
		switch(BitsApp.sAppContext.getResources( ).getConfiguration( ).orientation) {
			case Configuration.ORIENTATION_LANDSCAPE:
				BitsLog.d( "BitsGame - rotateScreenOrientation", "Setting orientation mode to portrait..." );                
				BitsApp.sBitsAppInstance.setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
				break;
				
			case Configuration.ORIENTATION_PORTRAIT:
				BitsLog.d( "BitsGame - rotateScreenOrientation", "Setting orientation mode to landscape..." );                
				BitsApp.sBitsAppInstance.setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE );
				break;
			
			default:
				BitsLog.w("BitsGame - rotateScreenOrientation", "Current screen orientation is unknown. Setting orientation mode to landscape..." );
				BitsApp.sBitsAppInstance.setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE );
               	break;
		}
	}
	
	public final void 
	setResolution( 
			final int width, 
			final int height ) {
		
		BitsGame.getInstance().mRenderer.requestResolution( width, height );
	}
	
	/**
	 * Returns the current Fps rate.
	 * @return - Fps rate
	 */
	public final int 
	getFps( ) {
		
		return this.mFps;
	}
	
	/**
	 * Returns the current Update rate.
	 * @return - Update rate
	 */
	public final int 
	getUps( ) {
		
		return this.mUps;
	}
	
	/**
	 * This method will be called by the opengl es renderer if the game thread<br>
	 * asked for a report and the opengl es surface is ready.<br>
	 * <br>
	 * You must not call this method. If you do, the game thread will reload the<br>
	 * current BitsScreen, which may be an unwanted behavior.<br>
	 */
	public final void 
	rendererReady( ) {
		
		/* If the local game thread hasn't been started yet. */
		if( this.isRunning == false ) {
			
			//set the default clip size
			this.mGraphics.reset();
			this.mGraphics.setColor(1f, 1f, 1f, 1f);
			this.mGraphics.setClip(0, 0, BitsApp.sGameWidth, BitsApp.sGameHeight);
	       	
			//Initialize the (first) BitsScreen
			final BitsScreen screen = this.mScreenHashMap.get( this.mActiveScreenIndex );
			if( screen != null ) {
				screen.onInitScreen( );
			} else {
				BitsLog.e("BitsGame - rendererReady", "The next screen you wanted to show is NULL. ID: " + String.valueOf(this.mActiveScreenIndex ) );
				throw new RuntimeException( "The next screen you wanted to show is NULL. ID: " + String.valueOf(this.mActiveScreenIndex ) );
			}
			
			//load all images that have been marked for loading during the screen init
			BitsLog.d( "BitsGame - rendererReady", "Request loading for marked resources in onInit method..." );
			BitsGLFactory.getInstance().loadAllMarked();
			
			//release some data (this is the right time to do so)
			System.gc( );
			
			//Start the local game thread, now.
			this.start( );
		} else {
			//if the local game thread is running already and a new screen should be shown.
			if( this.wantScreenChange == true ) {
				BitsLog.d( "BitsGame - rendererReady", "Screen will change..." );
				
				//get the active screen and finish it
				if(this.mNextScreenIndex != this.mActiveScreenIndex) {
					final BitsScreen screen = this.mScreenHashMap.get( this.mActiveScreenIndex );
					if( screen != null ) {
						BitsLog.d("BitsGame - rendererReady", "Finishing old screen...");
						screen.onFinishScreen( );
					}
				}
				
				//release all images that have been marked for releasing during the onFinish method
				BitsLog.d( "BitsGame - rendererReady", "Request releasing for marked resources in onInit method..." );
				BitsGLFactory.getInstance().releaseAllMarked();
				
				//clears the render state stack, so the new screen will start with no stored render states
				this.mGraphics.clearStates();
				
				//set the next screen as the new active screen
				this.mActiveScreenIndex = this.mNextScreenIndex;
				
				//clear the last render queue (the one from the old screen)
				this.mRenderCommandQueues.clearRenderedQueue( );
		    	this.mRenderCommandQueues.swapIndex( );

		    	//set the default start color
				this.mGraphics.setColor(1f, 1f, 1f, 1f);

				//get and init the new screen
				BitsLog.d("BitsGame - rendererReady", "Init new screen...");
				final BitsScreen screen = this.mScreenHashMap.get( this.mActiveScreenIndex );
				if( screen != null ) {
					screen.onInitScreen( );
				} else {
					BitsLog.e("BitsGame - rendererReady", "The next screen you wanted to show is NULL. ID: " + String.valueOf(this.mActiveScreenIndex ) );
					throw new RuntimeException( "The next screen you wanted to show is NULL. ID: " + String.valueOf(this.mActiveScreenIndex ) );
				}
				
				//load all images that have been marked for loading during the screen init
				BitsLog.d( "BitsGame - rendererReady", "Request loading for marked resources in onInit method..." );
				BitsGLFactory.getInstance().loadAllMarked();
	
				//release some data (this is the right time to do so)
				System.gc( );
				
				//not another call to this if-clause before showScreen(...) hasn't been called
				this.wantScreenChange = false;
			}
		}
	}
	
	/**
	 * This method can be used to register a BitsScreen.<br>
	 * After the registration the screen may be shown via the method<br>
	 * 'showScreen(BitsScreen)'.<br>
	 * The BitsScreen object passed to this method must not to be NULL<br>
	 * and the ID of the screen must be >= 0. Otherwise the app will log an<br>
	 * error (nothing else will happen).<br>
	 * <br>
	 * @param screen - BitsScreen
	 */
	public final void 
	addScreen(
			final BitsScreen screen ) {
		
		if( screen != null ) {
			if( screen.getId() >= 0 ) { 
				this.mScreenHashMap.put( screen.getId(), screen ); 
			} else {
				BitsLog.e("BitsGame - addScreen", "ScreenID need to be >= 0. Can't add the screen with id: " + String.valueOf(screen.getId()) + "." );
			}
		}
		else {
			BitsLog.e( "BitsGame - addScreen", "Screen is NULL. Can't add a screen that is NULL." );
		}
	}
	
	public final void
	removeScreen(
			final int screenId ) {
		
		final BitsScreen screen = this.mScreenHashMap.remove( screenId );
		if( screen != null ) {
			screen.removeAll();
		}
	}
	
	/**
	 * If you want to show another registered BitsScreen, you have to<br>
	 * call this method with the corresponding screen id.<br>
	 * <br>
	 * The screen id must be >= 0 and the id must point at a<br>
	 * previously registered BitsScreen. Otherwise the app will log an<br>
	 * error (nothing else will happen).<br>
	 * <br>
	 * @param screenID - id of the screen to show next
	 */
	public final void 
	showScreen( 
			final int screenID ) {
		
		if( screenID >= 0 ) {
			if(this.mScreenHashMap.get( screenID ) != null) {
				this.wantScreenChange = true;
				this.mNextScreenIndex = screenID;
			
				//waits until the renderer is done
				//the screen will be switched in the rendererReady() method
				this.mRenderer.wantReadyReport( true );
			} else {
				BitsLog.e( "BitsGame - showScreen", "Screen is NULL. Can't show the screen with id: " + String.valueOf(screenID) + "." );
			}
		}
		else {
			BitsLog.e( "BitsGame - showScreen", "ScreenID need to be >= 0. Can't show the screen with id: " + String.valueOf(screenID) + "." );
		}
	}
	
	/**
	 * This method will be called by the engine itself,<br>
	 * if the app is created.<br>
	 * It passes a reference to the BitsOpenGLRenderer to the game-/logic-thread.<br>
	 * <br>
	 * @param renderer - the BitsOpenGLRenderer
	 */
	public final void 
	setRenderer( 
			final BitsGLRenderer renderer ) {
		
		this.mRenderer = renderer;
	}
	
	/**
	 * This method will be called by the OpenGLRenderer,<br>
	 * if a new render queue is available.<br>
	 * <br>
	 * @return - the new render queue
	 */
    public final synchronized ArrayList<BitsRenderCommand> 
    takeRenderQueue( ) {
    	
		this.wasRenderQueueTaken = true;

		this.mRenderCommandQueues.clearRenderedQueue( );
    	this.mRenderCommandQueues.swapIndex( );

        synchronized(this.mLogicLock) {
    		this.mLogicLock.notify( );
    	}

        return this.mRenderCommandQueues.getQueueToRender( );
    }
    
    /**
     * This method will be called by the BitsApp,<br>
	 * if the app is going to be paused.<br>
	 * <br>
	 * It is calling the onPause method of the current<br>
	 * active BitsScreen.<br>
     */
    public final void 
    onPauseApp( ) {
    	
   		this.isPaused = true;

		final BitsScreen screen = this.mScreenHashMap.get( this.mActiveScreenIndex );
		if( screen != null ) {
			screen.onPauseScreen( );
		} else {
			BitsLog.e("BitsGame - onPause", "Screen is NULL. Can't call onPause() of the current acitve screen with id: " + String.valueOf(this.mActiveScreenIndex) + ".");
			throw new RuntimeException("Screen is NULL. Can't call onPause() of the current acitve screen with id: " + String.valueOf(this.mActiveScreenIndex) + ".");
		}
    }
    
    /**
     * This method will be called by the BitsApp,<br>
	 * if the app is going to be closed.<br>
	 * <br>
	 * It is calling the onFinish method of the current<br>
	 * active BitsScreen and releases all remaining data.<br>
     */
    public final void 
    onStopApp( ) {
    	
    	if(this.mWantFinish == true) {
    		this.mWantFinish = false;
    		
    		BitsLog.d("BitsGame - onStop", "Calling abstract method onFinish in BitsApp class.");
    		BitsApp.sBitsAppInstance.onStopApp();
	    	
	    	BitsLog.d("BitsGame - onStop", "Informing the logic thread to stop itself.");
	        this.isPaused = false;
	        this.wasRenderQueueTaken = true;
	        this.isRunning = false;
	
	        //wakes up the game-/logic-thread, if it is waiting
	        //for the renderer to finish
	        synchronized( this.mLogicLock ) {
	            this.mLogicLock.notify( );
	    	}
    	}
    }

    /**
     * This method will be called by the BitsApp,<br>
	 * if the app is going to be resumed.<br>
	 * <br>
	 * It is calling the onResume method of the current<br>
	 * active BitsScreen and resets the timer for the frame rate<br>
	 * calculation.<br>
     */
    public final void 
    onResumeApp( ) {
    	
        this.isPaused = false;
        
		final BitsScreen screen = this.mScreenHashMap.get( this.mActiveScreenIndex );
		if( screen != null ) {
			screen.onResumeScreen( );
		} else {
			BitsLog.e("BitsGame - onResume", "Screen is NULL. Can't call onPause() of the current acitve screen with id: " + String.valueOf(this.mActiveScreenIndex) + ".");
			throw new RuntimeException("Screen is NULL. Can't call onPause() of the current acitve screen with id: " + String.valueOf(this.mActiveScreenIndex) + ".");
		}
    }
    
    /**
     * Is the game-/logic-thread running?
     * 
     * @return
     */
    public final boolean 
    isRunning() {
    	
    	return this.isRunning;
    }
    
	@Override
	public void 
	run() {
		
		BitsLog.d("BitsGame - run", "Running...");

		this.isRunning = true;
		boolean willBeRendered = false;
		
		int ups = 0;
		int fps = 0;

	    final float timeBetweenUpdates = Math.round( (1000f / (float)BitsApp.sMaxUpdate) );
	    BitsLog.d("BitsGame - run", "Time between updates (ms): " + timeBetweenUpdates);
	    final BitsTimer timer = new BitsTimer();
	    timer.start();

	    long lastUpdateTime = SystemClock.uptimeMillis();		
	    while ( this.isRunning ) {
	    	synchronized( this ) {

	    		final BitsScreen screen = this.mScreenHashMap.get( this.mActiveScreenIndex );
    	        final long now = SystemClock.uptimeMillis();
    	        
    	        int updateCount = 0;
	    	    while( now - lastUpdateTime > timeBetweenUpdates && updateCount < BitsApp.sMaxUpdatesBeforeRender ) {
	    	    	//propagate the cached touch events
	                BitsInput.getInstance().propagatePointerEvents( );
		            //propagate the cached key events
		            BitsInput.getInstance().propagateKeyEvents( );

		            if( this.wantScreenChange == false && this.mWantFinish == false ) {
		               	screen.onUpdate( timeBetweenUpdates );
		               	ups++;
		            }
	    	        
		            lastUpdateTime += timeBetweenUpdates;
	    	        updateCount++;
	    	    }

                //call the onDrawFrame method of the current active screen
				if( this.wantScreenChange == false && this.mWantFinish == false ) {
					screen.onDrawFrame( this.mGraphics );
					fps++;
				}	    				
                this.mRenderer.setNewRenderQueueAvailable( );
                willBeRendered = true;

	            if (timer.getSeconds() >= 1) {
	            	this.mFps = fps;
	            	this.mUps = ups;
	            	fps = ups = 0;
	            	timer.start();
	            }
	    	}

	    	//Waits until the render queue has been taken, or if the game
	    	//has been paused.
	    	if(willBeRendered == true || this.isPaused == true)	{
		        synchronized( this.mLogicLock ) {
		            if ( this.wasRenderQueueTaken == false || this.isPaused == true ) {		            	
		                while ( this.wasRenderQueueTaken == false || this.isPaused == true ) {
		                    try { this.mLogicLock.wait(); } catch ( InterruptedException e ) { }
		                }
		            }
		            this.wasRenderQueueTaken = false;
		            willBeRendered = false;
		        }
	    	}
	    }
	    BitsLog.d("BitsGame - run", "...logic thread stopped");
		
		BitsGame.resetInstance();
	}
}
