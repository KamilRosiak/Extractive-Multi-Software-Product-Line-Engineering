
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

package net.gliblybits.bitsengine.gui;

import java.util.ArrayList;

import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;
import net.gliblybits.bitsengine.input.BitsInput;
import net.gliblybits.bitsengine.input.BitsPointerEvent;
import net.gliblybits.bitsengine.input.listener.BitsPointerListener;
import net.gliblybits.bitsengine.utils.BitsLog;

/**
 * A BitsScreen is the heart of any app.<br>
 * Games always consists of different screens (e.g. title, options, etc.).<br>
 * So, this abstract class must be extended to create these game screens.<br>
 * <br>
 * A BitsScreen is like a Window. You may add GUI components to the screen, like BitsButtons, BitsList, BitsDialog, ...<br>
 * <br>
 * @author Marc Wiedenhoeft - GliblyBits
 * @version 0.1 - Streak
 * @since 0.1 - Streak
 */
public abstract class 
BitsScreen 
implements BitsPointerListener {
	/**
	 * Collection of all registered BitsButtons.
	 */
	private final ArrayList<BitsWidget> mWidgets = new ArrayList<BitsWidget>( );

	/**
	 * The BitsScreen ID.
	 */
    protected final int mId;

    /**
     * The constructor checks the given screen id.<br>
     * The id must be >= 0. Otherwise an error will be logged.<br>
     * <br>
     * @param id
     */
    protected 
    BitsScreen( final int id ) {
    	//check if the given screen id is >= 0
    	if( id >= 0 ) { 
    		this.mId = id;
    	} else {
    		//if the id is < 0 a runtime exception will be thrown
    		//and the app will force close
    		BitsLog.e( "BitsScreen - constructor", "Screen ID must be >= 0! Your given ID is: " + String.valueOf(id) );
    		throw new RuntimeException( "Screen ID must be >= 0! Your given ID is: " + String.valueOf(id) );
    	}
    }

    /**
     * Returns the id of this BitsScreen.<br>
     * <br>
     * @return - id
     */
    public final int 
    getId( ) {
    	return this.mId;
    }
    
    /**
     * Adds a widget to this BitsScreen.<br>
     * <br>
     * @param frame
     */
    public final void 
    add( 
    		final BitsWidget widget ) {
    	
    	if( widget == null ) {
    		BitsLog.e("BitsScreen - add", "The given BitsWidget is Null!");
    		throw new NullPointerException("The given BitsWidget is Null!");
    	}

		widget.onAdd(); //point to register listeners
		this.mWidgets.add( widget );
    }

    public final void 
    add( 
    		final BitsWidget widget, 
    		final int index ) {
    	
    	if( widget == null ) {
    		BitsLog.e("BitsScreen - add", "The given BitsWidget is Null!");
    		throw new NullPointerException("The given BitsWidget is Null!");
    	}

		widget.onAdd(); //point to register listeners
		this.mWidgets.add( index, widget );
    }
    
    /**
     * This method removes a widget from the BitsScreen.<br>
     * <br>
     * @param id
     */
    public final void 
    remove( 
    		final BitsWidget widget ) {
    	
    	if( widget == null ) {
    		BitsLog.e("BitsScreen - add", "The given BitsWidget is Null!");
    		throw new NullPointerException("The given BitsWidget is Null!");
    	}

		widget.onRemove(); //point to unregister listeners
		this.mWidgets.remove( widget );
    }
    
	public final void
	removeAll( ) {
		
    	final ArrayList<BitsWidget> widgetList = this.mWidgets;
	    final int size = widgetList.size();
	    if( size > 0 ) {
		   	for(int i = size - 1; i >= 0; i--) {
		   		final BitsWidget w = widgetList.remove(i);
		    	if( w != null ) {
		        	w.onRemove();
		        }
		    }
	    }
	}

    /**
     * This method will be called before this<br>
     * screen becomes visible.<br>
     * This is the right place to load screen specific<br>
     * resources.<br>
     */
    public void 
    onInitScreen( ) {
    	
		BitsInput.getInstance().registerPointerListener(this);
	}
    
    /**
     * This method will be called before the<br>
     * app is paused.<br>
     * This is the right place to pause BitsTimers.<br>
     */
    public void 
    onPauseScreen( ) {
    }

    /**
     * This method will be called before the<br>
     * app is resumed.<br>
     * This is the right place to resume BitsTimers.<br>
     */
    public void 
    onResumeScreen( ) {
	}
    
    /**
     * This method will be called before the<br>
     * screen is destroyed.<br>
     * This is the right place to free loaded resources.<br>
     */    
    public void 
    onFinishScreen( ) {

    	BitsInput.getInstance().unregisterPointerListener(this);
	}
    
    /**
     * This method will be called, if the back button on<br>
     * the device has been pressed.<br>
     * This is the right place to handle screen switched or<br>
     * to finish the app.<br>
     */
    public void 
    onBackButtonPressed( ) {
	}

    /**
     * This method is called by the engine itself.<br>
     * It will call the render method of all registered gui components.<br>
     */
    public void 
    onDrawFrame( 
    		final BitsGLGraphics g ) {
    	
    	final ArrayList<BitsWidget> widgetList = this.mWidgets;
	    final int size = widgetList.size();
	    if( size > 0 ) {
		   	for(int i = 0; i < size; i++) {
		   		final BitsWidget w = widgetList.get(i);
		    	if( w != null ) {
		    		if( w.isVisible == true ) {
		    			w.onDrawFrame( g );
		    		}
		        }
		    }
	    }
	}

    /**
     * This method is called by the engine itself.<br>
     * It will call the update method of all registered gui components.<br>
     */
    public void 
    onUpdate( 
    		final float delta ) {
    	
    	final ArrayList<BitsWidget> widgetList = this.mWidgets;
	    final int size = widgetList.size();
	    if( size > 0 ) {
		   	for(int i = 0; i < size; i++) {
		   		final BitsWidget w = widgetList.get(i);
		    	if( w != null ) {
		    		if( w.isVisible == true && w.isActive == true ) { //TODO CHECK isActive!
		    			w.onUpdate( delta );
		    		}
		        }
		    }
	    }
    }
    
	@Override
	public boolean 
	onPointerDown( 
			final int pointerId, 
			final float x, 
			final float y, 
			final BitsPointerEvent event ) {
		
    	final ArrayList<BitsWidget> widgetList = this.mWidgets;
	    final int size = widgetList.size();
	    if( size > 0 ) {
		   	for(int i = size - 1; i >= 0; i--) { //give the event to the widget in front -> this is the last element in the arraylist -> rendering back-to-front
		   		final BitsWidget w = widgetList.get(i);
		    	if( w != null ) {
		    		if( w.isVisible == true ) {
		    			if( w.onPointerDown(pointerId, x, y, event) == true ) {
		    				return true;
		    			}
		    		}
		        }
		    }
	    }
		return false;
	}

	@Override
	public boolean 
	onPointerUp( 
			final int pointerId, 
			final float x, 
			final float y, 
			final BitsPointerEvent event ) {
		
    	final ArrayList<BitsWidget> widgetList = this.mWidgets;
	    final int size = widgetList.size();
	    if( size > 0 ) {
		   	for(int i = size - 1; i >= 0; i--) { //give the event to the widget in front -> this is the last element in the arraylist -> rendering back-to-front
		   		final BitsWidget w = widgetList.get(i);
		    	if( w != null ) {
		    		if( w.isVisible == true ) {	
			        	if( w.onPointerUp(pointerId, x, y, event) == true ) {
			        		return true;
			        	}
		    		}
		        }
		    }
	    }
		return false;
	}

	@Override
	public boolean 
	onPointerDragged( 
			final int pointerId, 
			final float x, 
			final float y, 
			final float deltaX, 
			final float deltaY, 
			final BitsPointerEvent event ) {
		
    	final ArrayList<BitsWidget> widgetList = this.mWidgets;
	    final int size = widgetList.size();
	    if( size > 0 ) {
		   	for(int i = size - 1; i >= 0; i--) { //give the event to the widget in front -> this is the last element in the arraylist -> rendering back-to-front
		   		final BitsWidget w = widgetList.get(i);
		    	if( w != null ) {
		    		if( w.isVisible == true ) {
		    			if( w.onPointerDragged(pointerId, x, y, deltaX, deltaY, event) == true ) {
		    				return true;
		    			}
		    		}
		        }
		    }
	    }
		return false;
	}
}
