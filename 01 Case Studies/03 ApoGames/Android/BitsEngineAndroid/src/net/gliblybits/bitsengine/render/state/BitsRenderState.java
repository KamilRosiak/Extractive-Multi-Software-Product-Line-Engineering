
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

import net.gliblybits.bitsengine.graphics.BitsFont;
import net.gliblybits.bitsengine.utils.BitsRectF;

public final class 
BitsRenderState {

	//render state mask
	public static final int STATE_CLIP 		= 1;
	public static final int STATE_COLOR 	= 2;
	public static final int STATE_ROTATION 	= 4;
	public static final int STATE_LINESIZE 	= 8;
	public static final int STATE_SCALE 	= 16;
	public static final int STATE_FONT 		= 32;
	public static final int STATE_TRANSLATE	= 64;
	
	public int				mStateMask	= -1;
	public final BitsRectF 	mClip 		= new BitsRectF(0f, 0f, 0f, 0f);
	public final float[] 	mColor 		= new float[4];
	public final float[] 	mRotation 	= new float[3];
	public final float[]	mTranslate	= new float[2];
	public float 			mLineSize 	= 1f;
	public float 			mScale 		= 1f;
	public BitsFont			mFont		= null;
}