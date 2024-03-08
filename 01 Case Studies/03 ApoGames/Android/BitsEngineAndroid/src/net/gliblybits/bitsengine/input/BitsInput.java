
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

import java.util.ArrayList;

import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import net.gliblybits.bitsengine.core.BitsApp;
import net.gliblybits.bitsengine.core.BitsGame;
import net.gliblybits.bitsengine.input.listener.BitsKeyListener;
import net.gliblybits.bitsengine.input.listener.BitsPointerListener;
import net.gliblybits.bitsengine.utils.BitsLog;

public final class 
BitsInput {
	
	private static BitsInput instance = new BitsInput();

	private final ArrayList<BitsKeyListener> 	mKeyListener 		= new ArrayList<BitsKeyListener>( );
	private final ArrayList<BitsKeyEvent> 		mKeyEventList0 		= new ArrayList<BitsKeyEvent>( );
	private final ArrayList<BitsKeyEvent> 		mKeyEventList1 		= new ArrayList<BitsKeyEvent>( );
	private int									mKeyEventListIndex0	= 0;
	private int									mKeyEventListIndex1	= 0;
	private int 								mActiveKeyEventList = 0;
	private final Object						mKeyEventLock		= new Object( );

	private final ArrayList<BitsPointerListener> 	mPointerListener 		= new ArrayList<BitsPointerListener>( );
	private final ArrayList<BitsPointerEvent> 		mPointerEventList0 		= new ArrayList<BitsPointerEvent>( );
	private final ArrayList<BitsPointerEvent> 		mPointerEventList1 		= new ArrayList<BitsPointerEvent>( );
	private int										mPointerEventListIndex0	= 0;
	private int										mPointerEventListIndex1	= 0;
	private int 									mActivePointerEventList = 0;
	private final Object							mPointerEventLock		= new Object( );
	
	private InputMethodManager 	mInputManager 	= null;
	private BitsPointer[] 		mPointer 		= null;
	
	private BitsInput( ) {
		
	}
	
	public static final BitsInput 
	getInstance( ) {
		
		return instance;
	}
	
	public static final void 
	initInstance( ) {
		
		synchronized( instance.mKeyEventLock ) {
			BitsLog.d("BitsInput - initInstance", "Initializing singleton instance...");
			instance.mInputManager = (InputMethodManager)BitsApp.sBitsAppInstance.getSystemService(Context.INPUT_METHOD_SERVICE);
			instance.mPointer = new BitsPointer[BitsApp.sMaxTouchPointer];
			for( int i = 0; i < BitsApp.sMaxTouchPointer; i++ ) {
				instance.mPointer[i] = new BitsPointer();
			}
			instance.mPointerEventList0.add(new BitsPointerEvent());
			instance.mPointerEventList0.add(new BitsPointerEvent());
			instance.mPointerEventList1.add(new BitsPointerEvent());
			instance.mPointerEventList1.add(new BitsPointerEvent());
		}
	}
	
	public static final void
	resetInstance( ) {
		
		synchronized( instance.mKeyEventLock ) {
			BitsLog.d( "BitsInput - resetInstance", "Resetting the singleton instance..." );
			instance.mKeyEventList0.clear( );
			instance.mKeyEventList1.clear( );
			instance.mKeyListener.clear( );
			instance.mKeyEventListIndex0 = 0;
			instance.mKeyEventListIndex1 = 0;
			instance.mActiveKeyEventList = 0;
			instance.mPointerEventList0.clear( );
			instance.mPointerEventList1.clear( );
			instance.mPointerListener.clear( );
			instance.mPointerEventListIndex0 = 0;
			instance.mPointerEventListIndex1 = 0;
			instance.mActivePointerEventList = 0;
		}
	}
	
	public final void 
	registerKeyListener( 
			final BitsKeyListener listener ) {
		
		synchronized( this.mKeyEventLock ) {
			this.mKeyListener.add(0, listener );
		}
	}

	public final void 
	unregisterKeyListener( 
			final BitsKeyListener listener ) {
		
		synchronized( this.mKeyEventLock ) {
			this.mKeyListener.remove( listener );
		}
	}

	public final void 
	registerPointerListener( 
			final BitsPointerListener listener ) {
		
		synchronized( this.mPointerEventLock ) {
			this.mPointerListener.add(0, listener );
		}
	}
	
	public final void 
	unregisterPointerListener( 
			final BitsPointerListener listener ) {
		
		synchronized( this.mPointerEventLock ) {
			this.mPointerListener.remove( listener );
		}
	}
	
	/**
	 * This method will show the virtual keyboard if it was invisible before.<br>
	 * Otherwise it will hide the virtual keyboard.<br>
	 * 
	 * @param visible - visibility of the virtual keyboard
	 */
	public final void 
	setVirtualKeyboardVisible( 
			final boolean visible ) {
		
		if( visible ) {
			this.mInputManager.showSoftInput( BitsGame.getInstance( ).mRenderer, 0 );
		} else {
			this.mInputManager.hideSoftInputFromWindow( BitsGame.getInstance( ).mRenderer.getWindowToken( ), 0 );
		}		
	}

	public final void 
	addTouchEvent( 
			final MotionEvent event ) {
		
        final int action = event.getAction( ) & MotionEvent.ACTION_MASK;
        final int pointerIndex = ( event.getAction( ) & MotionEvent.ACTION_POINTER_INDEX_MASK ) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
        final int pointerId = event.getPointerId( pointerIndex );
        
        //check if the pointerId is NOT in our valid pointer range
        if( pointerId >= BitsApp.sMaxTouchPointer ) {
        	return; //we are not interested in this event!
        }
        
        final float x = (event.getX( pointerIndex ) - ( BitsApp.sViewportPaddingWidth / 2 ) ) / BitsApp.sScaleFactor;
        final float y = (event.getY( pointerIndex ) - ( BitsApp.sViewportPaddingHeight / 2 ) ) / BitsApp.sScaleFactor;
        float dX = 0f;
        float dY = 0f;
        int type = -1;
        
        switch( action ) {
        	case MotionEvent.ACTION_DOWN:
        	case MotionEvent.ACTION_POINTER_DOWN:        		
        		type = BitsPointerEvent.POINTER_DOWN;
        		break;
        	case MotionEvent.ACTION_UP:
        	case MotionEvent.ACTION_POINTER_UP:
        		type = BitsPointerEvent.POINTER_UP;
        		this.mPointer[pointerId].mDragged = false;
        		break;
        	case MotionEvent.ACTION_MOVE:
        		type = BitsPointerEvent.POINTER_MOVE;
        		dX = x - this.mPointer[pointerId].mX;
        		dY = y - this.mPointer[pointerId].mY;
        		
        		//check if there really was a drag event or just a little pointer movement
        		if( this.mPointer[pointerId].mDragged == false ) {
		    		if( Math.abs(dX) < BitsApp.sPointerSensitivity && Math.abs(dY) < BitsApp.sPointerSensitivity ) {
		    			return;
		    		} else {
		    			this.mPointer[pointerId].mDragged = true;
		    		}
        		}
				break;
        }

        this.mPointer[pointerId].mX = x;
		this.mPointer[pointerId].mY = y;

		BitsPointerEvent newEvent = null;
		synchronized( this.mPointerEventLock ) {
			if( this.mActivePointerEventList == 0 ) {
				if( this.mPointerEventListIndex0 >= this.mPointerEventList0.size( ) - 1 ) { //if there is no more key event objects in the pool "0"
					newEvent = new BitsPointerEvent( ); //create a new touch event object
					newEvent.mType = type;
					newEvent.mId = pointerId;
					newEvent.mX = x;
					newEvent.mY = y;
					if( type == BitsPointerEvent.POINTER_MOVE ) {
						newEvent.mDX = dX;
						newEvent.mDY = dY;
					}
					this.mPointerEventList0.add( newEvent ); //and add it to the list "0"
				} else {
					newEvent = this.mPointerEventList0.get(this.mPointerEventListIndex0);
					newEvent.mType = type;
					newEvent.mId = pointerId;
					newEvent.mX = x;
					newEvent.mY = y;
					if( type == BitsPointerEvent.POINTER_MOVE ) {
						newEvent.mDX = dX;
						newEvent.mDY = dY;
					}
					this.mPointerEventList0.add( newEvent ); //and add it to the list "0"
				}
				this.mPointerEventListIndex0++;
			} else {
				if( this.mPointerEventListIndex1 >= this.mPointerEventList1.size( ) - 1 ) { //if there is no more key event objects in the pool "1"
					newEvent = new BitsPointerEvent( ); //create a new touch event object
					newEvent.mType = type;
					newEvent.mId = pointerId;
					newEvent.mX = x;
					newEvent.mY = y;
					if( type == BitsPointerEvent.POINTER_MOVE ) {
						newEvent.mDX = dX;
						newEvent.mDY = dY;
					}
					this.mPointerEventList1.add( newEvent ); //and add it to the list "1"
				} else {
					newEvent = this.mPointerEventList1.get(this.mPointerEventListIndex1);
					newEvent.mType = type;
					newEvent.mId = pointerId;
					newEvent.mX = x;
					newEvent.mY = y;
					if( type == BitsPointerEvent.POINTER_MOVE ) {
						newEvent.mDX = dX;
						newEvent.mDY = dY;
					}
					this.mPointerEventList1.add( newEvent ); //and add it to the list "1"
				}
				this.mPointerEventListIndex1++;
			}
		}
	}

	public final void 
	propagatePointerEvents( ) {

		synchronized( this.mPointerEventLock ) { //prevent the main UI thread from adding new events or listeners to the active list
			int pointerEventSize = 0;
			ArrayList<BitsPointerEvent> pointerEvents = null;
			
			//do nothing if there are no events available
			if( (this.mPointerEventListIndex0 == 0 && this.mActivePointerEventList == 0) || //if the key event list "0" is empty and is the active list
				(this.mPointerEventListIndex1 == 0 && this.mActivePointerEventList == 1) ) { //if the key event list "1" is empty and is the active list
				return;
			}

			if( this.mActivePointerEventList == 0 ) { //list "0" is the active list
				pointerEventSize = this.mPointerEventListIndex0;
				pointerEvents = this.mPointerEventList0;
				//swap
				this.mActivePointerEventList = 1;
				this.mPointerEventListIndex1 = 0;
			} else { //list "1" is the active list
				pointerEventSize = this.mPointerEventListIndex1;
				pointerEvents = this.mPointerEventList1;
				//swap
				this.mActivePointerEventList = 0;
				this.mPointerEventListIndex0 = 0;
			}
			if( pointerEventSize == 0 || pointerEvents.isEmpty() == true ) {
				return;
			}
			//if we reached this point there must be events available

			//inform the known listeners about all the events (every listener will get all buffered events!)
			final ArrayList<BitsPointerListener> touchListener = this.mPointerListener;		
			//reset the propagate list if there are no listeners available and return
			final int listenerSize = touchListener.size( );
			if( listenerSize == 0 ) {
				for( int i = 0; i < pointerEventSize; i++ ) {
					final BitsPointerEvent event = pointerEvents.get( i );
					event.reset();
				}
				return;
			}
			
			for( int i = 0; i < pointerEventSize; i++ ) {
				final BitsPointerEvent event = pointerEvents.get( i );

				//send event to all registered listeners
				boolean wasProcessed = false;
				for( int j = listenerSize - 1; j >= 0; j-- ) { //newest listeners first -> end of the arraylist
					final BitsPointerListener listener = touchListener.get( j );
					switch( event.mType ) {
						case BitsPointerEvent.POINTER_MOVE: {
							wasProcessed = listener.onPointerDragged(event.mId, event.mX, event.mY, event.mDX, event.mDY, event);
							break;
						}
						case BitsPointerEvent.POINTER_DOWN: {
							wasProcessed = listener.onPointerDown(event.mId, event.mX, event.mY, event);
							break;
						}
						case BitsPointerEvent.POINTER_UP: {
							wasProcessed = listener.onPointerUp(event.mId, event.mX, event.mY, event);
							break;
						}
					}
					if( wasProcessed == true ) { //stop the propagation, if the event has been processed by a widget
						break;
					}
				}
				
				//reset the key event, so it may be re-used by the addKeyEvent method
				event.reset();
			}
		}
	}

	/**
	 * This method will be called by the main UI thread that re-directs the key events to this class.
	 * The key event will be translated into a BitsKeyEvent and double buffered in corresponding lists.<br>
	 * 
	 * @param event - android key event from the main UI thread
	 */
	public final void 
	addKeyEvent( 
			final KeyEvent event ) {
		
		BitsKeyEvent newEvent = null;
		
		synchronized( this.mKeyEventLock ) {
			if( this.mActiveKeyEventList == 0 ) {
				if( this.mKeyEventListIndex0 >= this.mKeyEventList0.size( ) - 1 ) { //if there is no more key event objects in the pool "0"
					newEvent = new BitsKeyEvent( ); //create a new key event object
					newEvent.mAction = event.getAction( );
					newEvent.mKey = event.getKeyCode( );
					newEvent.mUnicodeChar = (char) event.getUnicodeChar( );
					this.mKeyEventList0.add( newEvent ); //and add it to the list "0"
				} else {
					newEvent = this.mKeyEventList0.get(this.mKeyEventListIndex0);
					newEvent.mAction = event.getAction( );
					newEvent.mKey = event.getKeyCode();
					newEvent.mUnicodeChar = (char) event.getUnicodeChar( );
					this.mKeyEventList0.add( newEvent ); //and add it to the list "0"
				}
				this.mKeyEventListIndex0++;
			} else {
				if( this.mKeyEventListIndex1 >= this.mKeyEventList1.size( ) - 1 ) { //if there is no more key event objects in the pool "1"
					newEvent = new BitsKeyEvent( ); //create a new key event object
					newEvent.mAction = event.getAction( );
					newEvent.mKey = event.getKeyCode();
					newEvent.mUnicodeChar = (char) event.getUnicodeChar( );
					this.mKeyEventList1.add( newEvent ); //and add it to the list "1"
				} else {
					newEvent = this.mKeyEventList1.get(this.mKeyEventListIndex1);
					newEvent.mAction = event.getAction( );
					newEvent.mKey = event.getKeyCode();
					newEvent.mUnicodeChar = (char) event.getUnicodeChar( );
					this.mKeyEventList1.add( newEvent ); //and add it to the list "1"
				}
				this.mKeyEventListIndex1++;
			}
		} 
	}
	
	/**
	 * This method will be called by the game thread if it is ready to propagate the key events to<br>
	 * the known listeners.<br>
	 */
	public final void 
	propagateKeyEvents( ) {

		//do nothing if there are no events or listeners available
		if( (this.mKeyEventListIndex0 == 0 && this.mActiveKeyEventList == 0) || //if the key event list "0" is empty and is the active list
			(this.mKeyEventListIndex1 == 0 && this.mActiveKeyEventList == 1) ) { //if the key event list "1" is empty and is the active list
			return;
		}

		ArrayList<BitsKeyEvent> keyEvents = null;
		int keyEventSize = 0;

		synchronized( this.mKeyEventLock ) { //prevent the main UI thread from adding new events or listeners to the active list
			if( this.mActiveKeyEventList == 0 ) { //list "0" is the active list
				keyEventSize = this.mKeyEventListIndex0;
				keyEvents = this.mKeyEventList0;
				//swap
				this.mActiveKeyEventList = 1;
				this.mKeyEventListIndex1 = 0;
			} else { //list "1" is the active list
				if( this.mActiveKeyEventList == 1 ) {
					keyEventSize = this.mKeyEventListIndex1;
					keyEvents = this.mKeyEventList1;
					//swap
					this.mActiveKeyEventList = 0;
					this.mKeyEventListIndex0 = 0;
				}			
			}

			//inform the known listeners about all the key events (every listener will get all buffered events!)
			final ArrayList<BitsKeyListener> keyListener = this.mKeyListener;
			final int listenerSize = keyListener.size( );
			if( listenerSize > 0 ) { //if there are any listeners
				for( int i = 0; i < keyEventSize; i++ ) {
					final BitsKeyEvent event = keyEvents.get( i );
					
					//send event to all registered listeners
					boolean wasProcessed = false;
					for( int j = listenerSize - 1; j >= 0; j-- ) { //newest listeners first -> end of the arraylist
						final BitsKeyListener listener = keyListener.get( j );
						switch( event.mAction ) {
							case BitsKeyEvent.ACTION_KEY_DOWN: {
								wasProcessed = listener.onKeyDown(event.mKey, event);
								break;
							}
							case BitsKeyEvent.ACTION_KEY_UP: {
								wasProcessed = listener.onKeyUp(event.mKey, event);
								break;
							}
						}
						if( wasProcessed == true ) { //stop the propagation, if the event has been processed by a widget
							break;
						}
					}
					
					//reset the key event, so it may be re-used by the addKeyEvent method
					event.reset();
				}
			}
		}
	}
}
