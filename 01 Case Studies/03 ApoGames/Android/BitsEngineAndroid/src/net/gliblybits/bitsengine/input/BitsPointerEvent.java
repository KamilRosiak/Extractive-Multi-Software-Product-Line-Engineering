
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

package net.gliblybits.bitsengine.input;

public final class 
BitsPointerEvent {
	
	static final int POINTER_DOWN 	= 0;
	static final int POINTER_UP 	= 1;
	static final int POINTER_MOVE	= 2;
	
	public int 		mId 		= -1;
	public float	mX 			= -1f;
	public float	mY 			= -1f;
	public float	mDX			= 0f;
	public float	mDY			= 0f;
	public int		mType		= -1;
	
	public final void 
	reset( ) {
		
		this.mId		= -1;
		this.mX 		= -1f;
		this.mY 		= -1f;
		this.mDX		= 0f;
		this.mDY		= 0f;
		this.mType		= -1;
	}
}
