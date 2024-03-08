
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

package net.gliblybits.bitsengine.gui.widgets;

import java.util.ArrayList;

import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;
import net.gliblybits.bitsengine.gui.BitsScreen;
import net.gliblybits.bitsengine.gui.BitsWidget;
import net.gliblybits.bitsengine.input.BitsPointerEvent;
import net.gliblybits.bitsengine.utils.BitsLog;
import net.gliblybits.bitsengine.utils.BitsRectF;

public class 
BitsDialogWidget 
extends BitsWidget {

	/**
	 * Collection of all registered BitsButtons.
	 */
	protected ArrayList<BitsWidget> mWidgets = new ArrayList<BitsWidget>( );
	protected final BitsScreen mParent;
	
	public final BitsRectF mRect = new BitsRectF(0f,0f,0f,0f);
	
	public 
	BitsDialogWidget(
			final BitsScreen parent, 
			final BitsRectF rect ) {
		
		if( parent == null ) {
			BitsLog.e("BitsDialogWidget - constructor", "The given BitsScreen object is Null!");
			throw new NullPointerException("The given BitsScreen object is Null!");
		}
		
		if( rect == null ) {
			BitsLog.e("BitsDialogWidget - constructor", "The given BitsRectF object is Null!");
			throw new NullPointerException("The given BitsRectF object is Null!");
		}
		
    	if( rect.mWidth < 0 || rect.mHeight < 0 ) {
    		BitsLog.e("BitsDialogWidget - constructor", "The given width and height values must be >= 0!");
    		throw new IllegalArgumentException("The given width and height values must be >= 0!");
    	}

		this.mParent 		= parent;
		this.mRect.mX 		= rect.mX;
		this.mRect.mY 		= rect.mY;
		this.mRect.mWidth 	= rect.mWidth;
		this.mRect.mHeight 	= rect.mHeight;
		this.isVisible 		= false;
	}
	
	public 
	BitsDialogWidget(
			final BitsScreen parent,
			final float x,
			final float y,
			final float width,
			final float height ) {
		
		if( parent == null ) {
			BitsLog.e("BitsDialogWidget - constructor", "The given BitsScreen object is Null!");
			throw new NullPointerException("The given BitsScreen object is Null!");
		}
		
    	if( width < 0 || height < 0 ) {
    		BitsLog.e("BitsDialogWidget - constructor", "The given width and height values must be >= 0!");
    		throw new IllegalArgumentException("The given width and height values must be >= 0!");
    	}

		this.mParent 		= parent;
		this.mRect.mX 		= x;
		this.mRect.mY 		= y;
		this.mRect.mWidth 	= width;
		this.mRect.mHeight 	= height;
		this.isVisible 		= false;
	}
	
	public final void
	show( ) {
		
		this.isVisible = true;
	}
	
	/**
	 * Removes the BitsDialog from the BitsAScreen and therefore disposes it.
	 */
	public final void
	dispose() {
		
		this.isVisible = false;
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
			BitsLog.e("BitsDialogWidget - add", "The given BitsWidget object is Null!");
			throw new NullPointerException("The given BitsWidget object is Null!");
    	}
    	
    	widget.onAdd(); //point to register listeners
		this.mWidgets.add( widget );
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
			BitsLog.e("BitsDialogWidget - remove", "The given BitsWidget object is Null!");
			throw new NullPointerException("The given BitsWidget object is Null!");
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
	
	@Override
	public void 
	onAdd() {

	}

	@Override
	public void 
	onRemove( ) {

	}

    /**
     * This method is called by the engine itself.<br>
     * It will call the render method of all registered gui components.<br>
     */
    public void 
    onDrawFrame( 
    		final BitsGLGraphics g ) {
    	
    	if( this.isVisible == false ) {
    		return;
    	}

    	final ArrayList<BitsWidget> widgetList = this.mWidgets;
		final int size = widgetList.size();
		if( size > 0 ) {
			for(int i = 0; i < size; i++) {
				final BitsWidget w = widgetList.get(i);
			 	if( w != null ) {
		 			w.onDrawFrame( g );
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
    	
    	if( this.isVisible == false || this.isActive == false ) {
    		return;
    	}
    	
    	final ArrayList<BitsWidget> widgetList = this.mWidgets;
	    final int size = widgetList.size();
	    if( size > 0 ) {
		   	for(int i = 0; i < size; i++) {
		   		final BitsWidget w = widgetList.get(i);
		    	if( w != null ) {
	    			w.onUpdate( delta );
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
		
    	if( this.isVisible == false ) {
    		return false;
    	}
		
    	final ArrayList<BitsWidget> widgetList = this.mWidgets;
	    final int size = widgetList.size();
	    if( size > 0 ) {
		   	for(int i = size - 1; i >= 0; i--) { //give the event to the widget in front -> this is the last element in the arraylist -> rendering back-to-front
		   		final BitsWidget w = widgetList.get(i);
		    	if( w != null ) {
		        	if( w.onPointerDown(pointerId, x, y, event) == true ) {
		        		return true;
		        	}
		        }
		    }
	    }
	    
		return true;
	}

	@Override
	public boolean 
	onPointerUp( 
			final int pointerId, 
			final float x, 
			final float y, 
			final BitsPointerEvent event ) {
		
    	if( this.isVisible == false ) {
    		return false;
    	}
    	
    	final ArrayList<BitsWidget> widgetList = this.mWidgets;
	    final int size = widgetList.size();
	    if( size > 0 ) {
		   	for(int i = size - 1; i >= 0; i--) { //give the event to the widget in front -> this is the last element in the arraylist -> rendering back-to-front
		   		final BitsWidget w = widgetList.get(i);
		    	if( w != null ) {
		        	if( w.onPointerUp(pointerId, x, y, event) == true ) {
		        		return true;
		        	}
		        }
		    }
	    }
	    
		return true;
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
		
    	if( this.isVisible == false ) {
    		return false;
    	}
		
    	final ArrayList<BitsWidget> widgetList = this.mWidgets;
	    final int size = widgetList.size();
	    if( size > 0 ) {
		   	for(int i = size - 1; i >= 0; i--) { //give the event to the widget in front -> this is the last element in the arraylist -> rendering back-to-front
		   		final BitsWidget w = widgetList.get(i);
		    	if( w != null ) {
		        	if( w.onPointerDragged(pointerId, x, y, deltaX, deltaY, event) == true ) {
		        		return true;
		        	}
		        }
		    }
	    }
	    
		return true;
	}
}
