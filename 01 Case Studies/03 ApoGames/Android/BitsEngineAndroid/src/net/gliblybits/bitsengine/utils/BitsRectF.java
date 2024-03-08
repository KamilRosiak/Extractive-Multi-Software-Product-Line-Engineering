
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
 * @author Dirk Aporius
 * @author Marc Wiedenhoeft - GliblyBits
 * @version 0.1 - Streak
 * @since 0.1 - Streak
 */
public final class 
BitsRectF {

	public float mX			= 0;
	public float mY			= 0;
	public float mWidth		= 0;
	public float mHeight	= 0;
	
	public 
	BitsRectF( 
			final float x, 
			final float y,
			final float width, 
			final float height ) {
		
    	if( width < 0 || height < 0 ) {
    		BitsLog.e("BitsRectF - constructor", "The given width and height values must be >= 0!");
    		throw new IllegalArgumentException("The given width and height values must be >= 0!");
    	}
    	
		this.mX = x;
		this.mY = y;
		this.mWidth = width;
		this.mHeight = height;
	}
	
	public final boolean 
	contains( 
			final BitsRectF rect ) {
		
    	if( rect == null ) {
    		BitsLog.e("BitsRectF - contains", "The given BitsRectF is Null!");
    		throw new NullPointerException("The given BitsRectF is Null!");
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
    		BitsLog.e("BitsRectF - contains", "The given width and height values must be >= 0!");
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
			final BitsRectF rect ) {
		
    	if( rect == null ) {
    		BitsLog.e("BitsRectF - intersects", "The given BitsRectF is Null!");
    		throw new NullPointerException("The given BitsRectF is Null!");
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
    		BitsLog.e("BitsRectF - intersects", "The given width and height values must be >= 0!");
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
