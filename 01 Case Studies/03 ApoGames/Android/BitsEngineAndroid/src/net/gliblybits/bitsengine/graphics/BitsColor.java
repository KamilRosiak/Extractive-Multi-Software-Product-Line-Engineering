
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

package net.gliblybits.bitsengine.graphics;

import net.gliblybits.bitsengine.utils.BitsLog;

public final class 
BitsColor {

	public static final int
	getARGB( 
			final int red,
			final int green,
			final int blue, 
			final int alpha ) {
		
		if( red < 0 || green < 0 || blue < 0 || alpha < 0 || red > 255 || green > 255 || blue > 255 || alpha > 255 ) {
			BitsLog.e("BitsColor - getARGB", "All color values must be >= 0 and <= 255!");
			throw new IllegalArgumentException("All color values must be >= 0 and <= 255!");
		}
		
		return (alpha << 24) | (red << 16) | (green << 8) | blue;
	}

	public static final int
	getARGB( 
			final float red, 
			final float green, 
			final float blue, 
			final float alpha ) {
		
		if( red < 0f || green < 0f || blue < 0f || alpha < 0f || red > 1f || green > 1f || blue > 1f || alpha > 1f ) {
			BitsLog.e("BitsColor - getARGB", "All color values must be >= 0f and <= 1f!");
			throw new IllegalArgumentException("All color values must be >= 0f and <= 1f!");
		}
		
		return BitsColor.getARGB( (int)(red * 255f), (int)(green * 255f), (int)(blue * 255f), (int)(alpha * 255f) );
	}
	
	public static final int
	getRGB( 
			final int red, 
			final int green, 
			final int blue ) {
		
		if( red < 0 || green < 0 || blue < 0 || red > 255 || green > 255 || blue > 255 ) {
			BitsLog.e("BitsColor - getRGB", "All color values must be >= 0 and <= 255!");
			throw new IllegalArgumentException("All color values must be >= 0 and <= 255!");
		}
		
		return (red << 16) | (green << 8) | blue;
	}
	
	public static final int
	getRGB( 
			final float red, 
			final float green, 
			final float blue ) {
		
		if( red < 0f || green < 0f || blue < 0f || red > 1f || green > 1f || blue > 1f ) {
			BitsLog.e("BitsColor - getRGB", "All color values must be >= 0f and <= 1f!");
			throw new IllegalArgumentException("All color values must be >= 0f and <= 1f!");
		}
		
		return BitsColor.getRGB( (int)(red * 255f), (int)(green * 255f), (int)(blue * 255f) );
	}
	
	public static final int
	RGBtoARGB( 
			final int color ) {
		
		return BitsColor.RGBtoARGB(color, 255);
	}
	
	public static final int
	RGBtoARGB( 
			final int color,
			final float alpha ) {
		
		return BitsColor.RGBtoARGB(color, (int)(alpha * 255f));
	}
	
	public static final int
	RGBtoARGB( 
			final int color,
			final int alpha ) {
		
		final int r = (color >> 16) & 0xFF;
		final int g = (color >> 8) & 0xFF;
		final int b = (color >> 0) & 0xFF;
		return (alpha << 24) | (r << 16) | (g << 8) | b;
	}

	public static final int
	ARGBtoRGB( 
			final int color ) {
		
		final int r = (color >> 16) & 0xFF;
		final int g = (color >> 8) & 0xFF;
		final int b = (color >> 0) & 0xFF;
		return (r << 16) | (g << 8) | b;
	}
}
