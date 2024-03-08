package net.gliblybits.bitsengine.gui.widgets;

import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLImage;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLTextureRegion;
import net.gliblybits.bitsengine.gui.BitsWidget;
import net.gliblybits.bitsengine.input.BitsPointerEvent;
import net.gliblybits.bitsengine.utils.BitsLog;
import net.gliblybits.bitsengine.utils.BitsRect;
import net.gliblybits.bitsengine.utils.BitsRectF;

public final class 
BitsImageSheetWidget
extends BitsWidget {

	public boolean 					isClickable = false;
	public int 						mIndex 		= 0;

	public BitsGLImage 				mImage 		= null;
	public BitsGLTextureRegion[] 	mRegions 	= null;

	private int mMaxIndex = 0;

	public
	BitsImageSheetWidget(
			final float x,
			final float y,
			final float width,
			final float height ) {
		
		this.setRect( x, y, width, height );
	}
	
	public 
	BitsImageSheetWidget(
			final BitsRectF rect ) {
		
		this.setRect(rect);
	}
	
	public 
	BitsImageSheetWidget(
			final BitsRect rect ) {
		
		this.setRect(rect);
	}
	
	public 
	BitsImageSheetWidget(
			final BitsRectF rect,
			final BitsGLImage image,
			final BitsGLTextureRegion[] regions ) {

		this( image, regions );
		this.setRect(rect);
	}
	
	public 
	BitsImageSheetWidget(
			final BitsRect rect,
			final BitsGLImage image,
			final BitsGLTextureRegion[] regions ) {

		this( image, regions );
		this.setRect(rect);
	}
	
	public 
	BitsImageSheetWidget(
			final float x,
			final float y,
			final float width,
			final float height,
			final BitsGLImage image,
			final BitsGLTextureRegion[] regions ) {

		this( image, regions );
		this.setRect( x, y, width, height );
	}
	
	public 
	BitsImageSheetWidget(
			final BitsGLImage image,
			final BitsGLTextureRegion[] regions ) {

		this.setImage(image);
		this.setRegions(regions);
	}
	
	public final void
	setRect(
			final BitsRect rect ) {

		if( rect == null ) {
			BitsLog.e("BitsImageSheetWidget - setRect", "The given BitsRect is Null!");
			throw new NullPointerException("The given BitsRect is Null!");
		}
		
    	if( rect.mWidth < 0 || rect.mHeight < 0 ) {
    		BitsLog.e("BitsImageSheetWidget - setRect", "The given width and height values must be >= 0!");
    		throw new IllegalArgumentException("The given width and height values must be >= 0!");
    	}
    	
		this.mRect.mX = rect.mX;
		this.mRect.mY = rect.mY;
		this.mRect.mWidth = rect.mWidth;
		this.mRect.mHeight = rect.mHeight;
	}	
	public final void
	setRect(
			final BitsRectF rect ) {
		
		if( rect == null ) {
			BitsLog.e("BitsImageSheetWidget - setRect", "The given BitsRectF is Null!");
			throw new NullPointerException("The given BitsRectF is Null!");
		}
		
    	if( rect.mWidth < 0 || rect.mHeight < 0 ) {
    		BitsLog.e("BitsImageSheetWidget - setRect", "The given width and height values must be >= 0!");
    		throw new IllegalArgumentException("The given width and height values must be >= 0!");
    	}
		
    	this.mRect.mX = rect.mX;
		this.mRect.mY = rect.mY;
		this.mRect.mWidth = rect.mWidth;
		this.mRect.mHeight = rect.mHeight;
	}
	
	public final void
	setRect(
			final float x,
			final float y,
			final float width,
			final float height ) {
		
    	if( width < 0 || height < 0 ) {
    		BitsLog.e("BitsImageSheetWidget - setRect", "The given width and height values must be >= 0!");
    		throw new IllegalArgumentException("The given width and height values must be >= 0!");
    	}
		
		this.mRect.mX = x;
		this.mRect.mY = y;
		this.mRect.mWidth = width;
		this.mRect.mHeight = height;
	}
	
	public final void
	setImage(
			final BitsGLImage image ) {
			
		this.mImage = image;
	}
	
	public final void
	setRegions(
			final BitsGLTextureRegion[] regions ) {
		
		this.mRegions = regions;
		this.mMaxIndex = regions.length;
	}

	@Override
	public final void 
	onAdd() {

	}

	@Override
	public final void 
	onRemove() {

	}

	@Override
	public final void 
	onDrawFrame(
			final BitsGLGraphics g) {

		if( this.isVisible == false ) {
			return;
		}

		if( this.mImage != null && this.mRegions != null && this.mIndex >= 0 && this.mIndex < this.mMaxIndex ) {
			g.cropImage( this.mImage, this.mRect, this.mRegions[ this.mIndex ].mRect);
		}
	}

	@Override
	public final void 
	onUpdate(
			final float delta) {

	}

	@Override
	public final boolean 
	onPointerDown(
			final int pointerId, 
			final float x, 
			final float y,
			final BitsPointerEvent event) {

		return this.isClickable;
	}

	@Override
	public final boolean 
	onPointerUp(
			final int pointerId,
			final float x,
			final float y,
			final BitsPointerEvent event) {

		return this.isClickable;
	}

	@Override
	public final boolean 
	onPointerDragged(
			final int pointerId,
			final float x, 
			final float y,
			final float deltaX, 
			final float deltaY,
			final BitsPointerEvent event) {

		return this.isClickable;
	}
}
