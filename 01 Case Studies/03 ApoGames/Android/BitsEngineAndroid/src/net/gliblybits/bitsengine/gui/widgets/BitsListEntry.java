
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

import net.gliblybits.bitsengine.graphics.opengl.BitsGLImage;

public final class 
BitsListEntry {
	
	public static final int TEXT_LOCATION_LEFT 		= 0;
	public static final int TEXT_LOCATION_CENTER 	= 1;
	public static final int TEXT_LOCATION_RIGHT 	= 2;
	public static final int ICON_LOCATION_LEFT 		= 3;
	public static final int ICON_LOCATION_RIGHT 	= 4;

	public BitsGLImage 	mIcon 			= null;
	public int 			mIconLocation 	= TEXT_LOCATION_LEFT;
	public String 		mText 			= null;
	public int 			mTextLocation 	= ICON_LOCATION_LEFT;

	public
	BitsListEntry(
			final String text,
			final int textLocation ) {
		
		this.mText = text;
		this.mTextLocation = textLocation;
	}

	public
	BitsListEntry(
			final String text,
			final int textLocation,
			final BitsGLImage icon,
			final int iconLocation ) {
		
		this.mText = text;
		this.mTextLocation = textLocation;
		this.mIcon = icon;
		this.mIconLocation = iconLocation;
	}

	public final void 
	setIcon(
			final BitsGLImage icon,
			final int iconLocation ) {
		
		this.mIcon = icon;
		this.mIconLocation = iconLocation;
	}

	public final void 
	setIconLocation(
			final int iconLocation ) {
		
		this.mIconLocation = iconLocation;
	}

	public final void 
	setText(
			final String text ) {
		
		this.mText = text;
	}

	public final void 
	setTextLocation( 
			final int textLocation ) {
		
		this.mTextLocation = textLocation;
	}
}
