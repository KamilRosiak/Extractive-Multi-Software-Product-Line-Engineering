
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

import net.gliblybits.bitsengine.core.BitsApp;
import android.content.Context;
import android.os.Vibrator;

public final class 
BitsVibration {

	private static BitsVibration instance = new BitsVibration();
	
    /**
     * Reference to the vibration service.<br>
     */
    private Vibrator mVibrator = null;
    
	private 
	BitsVibration( ) {
		
		this.mVibrator = ( Vibrator ) BitsApp.sBitsAppInstance.getSystemService( Context.VIBRATOR_SERVICE );
		if( this.mVibrator == null ) {
			BitsLog.w("BitsVibration - constructor", "VibratorService is NULL!");
		}
	}
	
	public static final BitsVibration 
	getInstance( ) {
		
		return instance;
	}
	
	public static final void 
	resetInstance( ) {
		
		BitsLog.d("BitsVibration - resetInstance", "Resetting the singleton instance...");
		instance.cancel( );
	}
	
	public final void 
	cancel( ) {

		if( this.mVibrator != null ) {
			try {
				this.mVibrator.cancel();
			} catch( final SecurityException se ) {	}
		} else {
			BitsLog.e( "BitsVibration - vibrate", "VibratorService is NULL!");
		}
	}
	
	/**
	 * This method will vibrate the device for a given time.<br>
	 * <br>
	 * @param time - time length in milliseconds
	 */
	public final void 
	vibrate( 
			final long time ) {

		if( this.mVibrator != null ) {
			try {
				this.mVibrator.vibrate( time );
			} catch( final SecurityException se )	{
				BitsLog.w( se, "BitsVibration - vibrate", "Vibration not possible! Your app requires VIBRATE permission!" );
			}
		} else {
			BitsLog.w("BitsVibration - vibrate", "VibratorService is NULL!");
		}
	}
	
	/**
	 * This method will vibrate the device with a given pattern.<br>
	 * The pattern may be repeated several times, by setting the repeat value.<br>
	 * Set the repeat value to '-1', if the pattern should not be repeated.<br>
	 * <br>
	 * The pattern array represents time values in milliseconds and must follow some rules:<br>
	 * - the first value is the start delay<br>
	 * - followed by the first vibration length<br>
	 * - followed by another delay length<br>
	 * - followed by the second vibration length<br>
	 * - ..and so on<br>
	 * So a vibration length is always followed by a delay length (and vice versa).<br>
	 * <br>
	 * For example, a 'SOS' pattern looks like this:<br>
	 *   0, //vibrate immediately<br>
     * 200, //short vibration<br>
     * 200, //short delay<br>
     * 200, //short vibration<br>
     * 200, //short delay<br>
     * 200, //short vibration<br>
     * 500, //delay between 'S' and 'O'<br>
     * 500, //long vibration<br>
     * 200, //short delay<br>
     * 500, //long vibration<br>
     * 200, //short delay<br>
     * 500, //long vibration<br>
     * 500, //delay between 'O' and 'S'<br>
     * 200, //short vibration<br>
     * 200, //short delay<br>
     * 200, //short vibration<br>
     * 200, //short delay<br>
     * 200, //short vibration<br>
     * 1000, //long delay (if repeated)<br>
	 * <br>
	 * @param pattern - the vibration pattern (values in milliseconds)
	 * @param repeat - how many times may the vibration pattern be repeated (set -1 for no repeat)
	 */
	public final void 
	vibrate(
			final long[] pattern, 
			final int repeat) {
		
		if( this.mVibrator != null ) {
			try {
				this.mVibrator.vibrate( pattern, repeat );
			} catch( final SecurityException se ) {
				BitsLog.w( "BitsVibration - vibrate", "Vibration not possible! Your app requires VIBRATE permission!" );
			}
		} else {
			BitsLog.w("BitsVibration - vibrate", "VibratorService is NULL!");
		}
	}
}
