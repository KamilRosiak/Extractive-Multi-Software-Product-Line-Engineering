
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

import android.os.SystemClock;

/**
 * This timer class uses the system clock to measure time intervals.<br>
 * The time interval may be returned in seconds or milliseconds.<br>
 * <br>
 * @author Marc Wiedenhoeft - GliblyBits
 * @version 0.1 - Streak
 * @since 0.1 - Streak
 */
public final class 
BitsTimer {
	
	/**
	 * Time at which the start of the timer.<br>
	 */
	private long 	mStartTime 	= 0L;
	
	/**
	 * Time at which the timer has been paused.<br>
	 */
	private long 	mPauseTime 	= 0L;
	
	/**
	 * Timer is paused?
	 */
	private boolean isPaused 	= false;
	
	/**
	 * Timer is 'running'?
	 */
	private boolean isStarted 	= false;
	
	/**
	 * This method starts the timer.<br>
	 * If the timer is already running, it will be restarted.<br>
	 * If the timer is paused, this method will resume the timer.<br>
	 */
	public final void 
	start( ) {
		
		if(this.isPaused == true) {
			this.isPaused = false;
			this.mStartTime = SystemClock.uptimeMillis( ) - this.mPauseTime;
			this.mPauseTime = 0L;
		} else {
			this.isStarted = true;
			this.isPaused = false;
		
			this.mStartTime = SystemClock.uptimeMillis( );
		}
	}
	
	/**
	 * This method stops/resets the timer.
	 */
	public final void 
	stop( ) {
		
		this.mStartTime = 0L;
		this.mPauseTime = 0L;
		this.isStarted = false;
		this.isPaused = false;
	}
	
	/**
	 * This method pauses the timer.<br>
	 */
	public final void 
	pause( ) {
		
		if(this.isStarted == true && this.isPaused == false) {
			this.isPaused = true;
			this.mPauseTime = SystemClock.uptimeMillis( ) - this.mStartTime;
		}
	}
	
	/**
	 * This method returns the time interval between now and the time<br>
	 * the timer was started.<br>
	 * <br>
	 * @return time intervall in milliseconds
	 */
	public final long 
	getMilliseconds( ) {

		if( this.isStarted == true ) {
			if( this.isPaused == true ) {
				return this.mPauseTime;
			} else {
				return SystemClock.uptimeMillis( ) - this.mStartTime;
			}
		}
		
		return 0;
	}
	
	/**
	 * This method returns the time interval between now and the time<br>
	 * the timer was started.<br>
	 * <br>
	 * @return time intervall in seconds
	 */
	public final int 
	getSeconds( ) {

		return (int)(this.getMilliseconds()) / 1000;
	}
	
	/**
	 * Timer is 'running'?<br>
	 * <br>
	 * @return true if timer is 'running', false otherwise
	 */
	public final boolean 
	isStarted( ) {

		return this.isStarted;
	}
	
	/**
	 * Timer is paused?<br>
	 * <br>
	 * @return true if timer is paused, false otherwise
	 */
	public final boolean 
	isPaused( ) {
		
		return this.isPaused;
	}
}
