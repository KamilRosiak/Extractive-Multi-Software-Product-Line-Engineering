
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

package net.gliblybits.bitsengine.input.listener;

import net.gliblybits.bitsengine.input.BitsKeyEvent;

public interface 
BitsKeyListener {
	
	/**
	 * This method will be called if a key is pressed.<br>
	 * <br>
	 * This method will be called by the game thread.<br>
	 * 
	 * @param key
	 * @param event
	 */
	public boolean 
	onKeyDown( 
			final int key, 
			final BitsKeyEvent event );

	/**
	 * This method will be called if a key is released.<br>
	 * <br>
	 * This method will be called by the game thread.<br>
	 * 
	 * @param key
	 * @param event
	 */
	public boolean 
	onKeyUp( 
			final int key, 
			final BitsKeyEvent event );
}
