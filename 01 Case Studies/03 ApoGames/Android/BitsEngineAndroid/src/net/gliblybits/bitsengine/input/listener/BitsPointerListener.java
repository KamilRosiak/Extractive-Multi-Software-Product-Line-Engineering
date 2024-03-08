
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

import net.gliblybits.bitsengine.input.BitsPointerEvent;

public interface 
BitsPointerListener {
	
	public boolean 
	onPointerDown( 
			final int pointerId, 
			final float x, 
			final float y, 
			final BitsPointerEvent event );
	
	public boolean 
	onPointerUp( 
			final int pointerId, 
			final float x, 
			final float y, 
			final BitsPointerEvent event );
	
	public boolean 
	onPointerDragged( 
			final int pointerId, 
			final float x, 
			final float y, 
			final float deltaX, 
			final float deltaY, 
			final BitsPointerEvent event );
}
