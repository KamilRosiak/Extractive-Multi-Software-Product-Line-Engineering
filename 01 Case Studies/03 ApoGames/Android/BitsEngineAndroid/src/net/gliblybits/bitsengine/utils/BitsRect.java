
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

/**
 * 
 * @author Marc Wiedenhoeft - GliblyBits
 * @version 0.1 - Streak
 * @since 0.1 - Streak
 */
public final class 
BitsRect {

	public int mX		= 0;
	public int mY		= 0;
	public int mWidth	= 0;
	public int mHeight	= 0;
	
	public 
	BitsRect( 
			final int x,
			final int y, 
			final int width,
			final int height ) {
		
    	if( width < 0 || height < 0 ) {
    		BitsLog.e("BitsRect - constructor", "The given width and height values must be >= 0!");
    		throw new IllegalArgumentException("The given width and height values must be >= 0!");
    	}
		
		this.mX = x;
		this.mY = y;
		this.mWidth = width;
		this.mHeight = height;
	}
	
	public final boolean 
	contains( 
			final BitsRect rect ) {
		
    	if( rect == null ) {
    		BitsLog.e("BitsRect - contains", "The given BitsRect is Null!");
    		throw new NullPointerException("The given BitsRect is Null!");
    	}
		
		return this.contains(rect.mX, rect.mY, rect.mWidth, rect.mHeight);
	}
	
	public final boolean 
	contains( 
			final float checkX, 
			final float checkY ) {
		
		return this.contains(checkX, checkY, 1, 1);
	}
	
	public final boolean 
	contains( 
			final float checkX,
			final float checkY,
			final float checkWidth,
			final float checkHeight ) {
		
    	if( checkWidth < 0 || checkHeight < 0 ) {
    		BitsLog.e("BitsRect - contains", "The given width and height values must be >= 0!");
    		throw new IllegalArgumentException("The given width and height values must be >= 0!");
    	}
		
		if ((this.mX <= checkX) && (this.mX + this.mWidth >= checkX + checkWidth)) {
			if ((this.mY <= checkY) && (this.mY + this.mHeight >= checkY)) {
				return true;
			}
		}
		return false;
	}
	
	public final boolean 
	intersects( 
			final BitsRect rect ) {
		
    	if( rect == null ) {
    		BitsLog.e("BitsRect - intersects", "The given BitsRect is Null!");
    		throw new NullPointerException("The given BitsRect is Null!");
    	}
		
		return this.intersects(rect.mX, rect.mY, rect.mWidth, rect.mHeight);
	}
	
	public final boolean 
	intersects( 
			final float checkX, 
			final float checkY ) {
		
		return this.intersects(checkX, checkY, 1, 1);
	}

	public final boolean 
	intersects( 
			final float checkX,
			final float checkY,
			final float checkWidth,
			final float checkHeight ) {
		
    	if( checkWidth < 0 || checkHeight < 0 ) {
    		BitsLog.e("BitsRect - intersects", "The given width and height values must be >= 0!");
    		throw new IllegalArgumentException("The given width and height values must be >= 0!");
    	}
		
		if ((this.mX <= checkX) && (this.mX + this.mWidth >= checkX)) {
			if ((this.mY <= checkY) && (this.mY + this.mHeight >= checkY)) {
				return true;
			}
			if ((this.mY > checkY) && (checkY + checkHeight >= this.mY)) {
				return true;
			}
		} else if ((this.mX > checkX) && (checkX + checkWidth >= this.mX)) {
			if ((this.mY <= checkY) && (this.mY + this.mHeight >= checkY)) {
				return true;
			}
			if ((this.mY > checkY) && (checkY + checkHeight >= this.mY)) {
				return true;
			}
		}
		return false;
	}
}
