
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
 * This class handles the BitsRenderCommandQueues.<br>
 * It enables some sort of double buffering by swapping two queues.<br>
 * One queue contains the render commands for the current frame and the other will be filled with the commands for the next frame.<br>
 * After each frame has been rendered these queues will be swapped. So the queue with the commands for the next frame will become the active queue,
 * which will then be fetched by the BitsOpenGLRenderer.<br>
 * 
 * @author Marc Wiedenhoeft - GliblyBits
 * @version 0.1 - Streak
 * @since 0.1 - Streak
 */
public final class 
BitsRenderCommandQueues {

	/**
	 * First command queue.
	 */
	private final ArrayList<BitsRenderCommand> mCommandQueue0 = new ArrayList<BitsRenderCommand>( );
	
	/**
	 * Second command queue.
	 */
	private final ArrayList<BitsRenderCommand> mCommandQueue1 = new ArrayList<BitsRenderCommand>( );
	
	/**
	 * Is used to check the un-used render commands back in into the pool.
	 */
    private final BitsRenderCommandPool mRenderCommandPool;
    
    /**
     * This is the number of the queue that is NOT currently used by the BitsOpenGLRenderer.<br>
     * So, new render commands will be added to the corresponding queue.<br>
     */
    private int mFreeQueueIndex = 0;
    
    public 
    BitsRenderCommandQueues( 
    		final BitsRenderCommandPool commandPool ) {
    	
    	this.mRenderCommandPool = commandPool;
    }
    
    /**
     * Swaps the queue index between 0 and 1.
     */
    public final void 
    swapIndex( ) {
    	
    	this.mFreeQueueIndex = ( this.mFreeQueueIndex + 1 ) % 2;
    }
    
    /**
     * Adds a render command to the FREE queue that is NOT used by the BitsOpenGLRenderer.
     * 
     * @param renderable
     */
    public final void 
    add( 
    		final BitsRenderCommand command ) {
    	
    	if( command != null ) {
    		if( this.mFreeQueueIndex == 0 ) {
    			this.mCommandQueue0.add( command );
    		} else {
    			this.mCommandQueue1.add( command );
    		}
    	}
    }
    
    /**
     * This method returns the index of the queue that will be rendered NEXT.<br>
     * So, if the mFreeQueueIndex is 0, this method will return the mCommandQueue1, because<br>
     * the swapIndex method has already been called by the BitsGame class.<br>
     * 
     * @return The queue index.
     */
    public final ArrayList<BitsRenderCommand> 
    getQueueToRender( ) {
    	
    	if( this.mFreeQueueIndex == 0 ) {
    		return this.mCommandQueue1;
    	} else {
    		return this.mCommandQueue0;
    	}
    }
    
    /**
     * Removes the commands from the queue that is currently used by the BitsOpenGLRenderer and checks them back in into the command pool.<br>
     */
    public final void 
    clearRenderedQueue( ) {
    	
    	//get the old queue
    	ArrayList<BitsRenderCommand> commandArray = null;
    	if( this.mFreeQueueIndex == 0) { //current active queue
    		commandArray = this.mCommandQueue1; //rendered queue is...
    	} else {
    		commandArray = this.mCommandQueue0; //rendered queue is...
    	}
   		final int count = commandArray.size();
   		for ( int i = count - 1; i >= 0; i-- ) {
   			this.mRenderCommandPool.checkIn( commandArray.remove( i ) );
   		}
    }
}
