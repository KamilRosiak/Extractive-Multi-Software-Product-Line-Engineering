
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

package net.gliblybits.bitsengine.render.state;

import java.util.ArrayList;

public final class 
BitsRenderStateStack {

	private final ArrayList<BitsRenderState> mStack = new ArrayList<BitsRenderState>();
	private int mStackLast = -1;
	private final ArrayList<BitsRenderState> mStatePool = new ArrayList<BitsRenderState>();
	private int mPoolLast = -1;
	
	public 
	BitsRenderStateStack() {

	}
	
	public final void
	push( final BitsRenderState renderState ) {
		
		if( renderState != null ) {
			this.mStack.add(renderState);
			this.mStackLast++;
		}
	}
	
	public final BitsRenderState
	pop( ) {
		
		if( this.mStackLast > -1 ) {
			if( this.mStack.isEmpty() == false ) {
				return this.mStack.remove( this.mStackLast-- );
			}
		}
		
		return null;
	}
	
	public final void 
	checkIn( 
			final BitsRenderState renderState ) {

		this.mStatePool.add( renderState );
		this.mPoolLast++;
	}

	public final BitsRenderState 
	checkOut( ) {
		if( this.mPoolLast == -1 ) { //the state pool is empty!
			return new BitsRenderState( );
		} else {
			return this.mStatePool.remove( this.mPoolLast-- ); //always remove the last element of the ArrayList -> fastest!
		}
	}
	
	public final void
	clearStack( ) {
		
		this.mStackLast = -1;
		this.mStack.clear();
	}
	
	public final void
	release( ) {

		this.mStackLast = -1;
		this.mStack.clear();
		this.mPoolLast = -1;
		this.mStatePool.clear();
	}
}
