
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

package net.gliblybits.bitsengine.render.commands;

import java.util.ArrayList;

/**
 * This class implements a pool of BitsRenderCommands.<br>
 * These BitsRenderCommands are used to render stuff to the screen.<br>
 * 
 * @author Marc Wiedenhoeft - GliblyBits
 * @version 0.1 - Streak
 * @since 0.1 - Streak
 */
public final class 
BitsRenderCommandPool {

    private final ArrayList<BitsRenderCommand> mCommandList = new ArrayList<BitsRenderCommand>( );
    private int mLast = -1;

    /**
     * Resets the given BitsRenderCommand and puts it back into the pool.<br>
     */
	public final void 
	checkIn( 
			final BitsRenderCommand command ) {
		
		if( command != null )	{
			command.reset( );
		}
		
		synchronized( this.mCommandList ){
			this.mCommandList.add( command );
			this.mLast++;
		}
	}

    /**
     * Gets a BitsRenderCommand out of the pool and returns it<br>
     * to the function caller.<br>
     * If there are no more BitsRenderCommand in the pool,<br>
     * this method will return a new BitsRenderCommand.<br>
     * <br>
     * @return object from pool
     */
	public final BitsRenderCommand 
	checkOut( ) {
		
		synchronized( this.mCommandList ) {
			if( this.mLast == -1 ) { //the command list is empty!
				return new BitsRenderCommand( );
			} else {
				return this.mCommandList.remove( this.mLast-- ); //always remove the last element of the ArrayList -> fastest!
			}
		}
	}

	public final void 
	release() {
		
		final ArrayList<BitsRenderCommand> commandList = this.mCommandList;
		final int size = commandList.size();
		for( int i = 0; i < size; i++ ) {
			final BitsRenderCommand command = commandList.get(i);
			command.reset();
		}
		this.mCommandList.clear();
		this.mLast = -1;
	}
}
