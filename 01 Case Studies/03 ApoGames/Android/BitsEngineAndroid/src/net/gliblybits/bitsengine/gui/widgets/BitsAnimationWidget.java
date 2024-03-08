package net.gliblybits.bitsengine.gui.widgets;

import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLImage;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLTextureRegion;
import net.gliblybits.bitsengine.gui.BitsWidget;
import net.gliblybits.bitsengine.input.BitsPointerEvent;
import net.gliblybits.bitsengine.utils.BitsLog;
import net.gliblybits.bitsengine.utils.BitsRect;
import net.gliblybits.bitsengine.utils.BitsRectF;
import net.gliblybits.bitsengine.utils.BitsTimer;

public final class 
BitsAnimationWidget 
extends BitsWidget {

	public boolean 					isClickable = false;
	public int						mDelay		= 100;
	public int						mIndex 		= 0;
	
	public BitsGLTextureRegion[] 	mRegions 	= null;
	public BitsGLImage[]			mImages		= null;
	
	private boolean					mWantLoop	= true;
	private boolean					mDone		= false;
	private boolean					mReverse	= false;
	private int						mMaxIndex	= 0;
	
	private final BitsTimer	mTimer = new BitsTimer();
	
	public
	BitsAnimationWidget(
			final float x,
			final float y,
			final float width,
			final float height ) {
		
		this.setRect( x, y, width, height );
	}
	
	public 
	BitsAnimationWidget(
			final BitsRectF rect ) {
		
		this.setRect(rect);
	}
	
	public 
	BitsAnimationWidget(
			final BitsRect rect ) {
		
		this.setRect(rect);
	}
	
	public 
	BitsAnimationWidget(
			final BitsRectF rect,
			final BitsGLImage image,
			final BitsGLTextureRegion[] regions ) {

		this( image, regions );
		this.setRect(rect);
	}
	
	public 
	BitsAnimationWidget(
			final BitsRect rect,
			final BitsGLImage image,
			final BitsGLTextureRegion[] regions ) {

		this( image, regions );
		this.setRect(rect);
	}
	
	public 
	BitsAnimationWidget(
			final float x,
			final float y,
			final float width,
			final float height,
			final BitsGLImage image,
			final BitsGLTextureRegion[] regions ) {

		this( image, regions );
		this.setRect( x, y, width, height );
	}
	
	private
	BitsAnimationWidget(
			final BitsGLImage image,
			final BitsGLTextureRegion[] regions ) {
		
		if( image == null ) {
			BitsLog.e("BitsAnimationWidget - constructor", "The given BitsGLImage is Null!");
			throw new NullPointerException("The given BitsGLImage is Null!");
		}
		
		if( regions == null ) {
			BitsLog.e("BitsAnimationWidget - constructor", "The given BitsGLTextureRegion array is Null!");
			throw new NullPointerException("The given BitsGLTextureRegion array is Null!");
		}
			
		this.mImages = new BitsGLImage[1];
		this.mImages[0] = image;
		this.mRegions = regions;
		this.mMaxIndex = regions.length;
	}
	
	public 
	BitsAnimationWidget(
			final BitsRectF rect,
			final BitsGLImage[] images ) {
		
		this( images );
		this.setRect( rect );
	}
	
	public 
	BitsAnimationWidget(
			final BitsRect rect,
			final BitsGLImage[] images ) {
		
		this( images );
		this.setRect( rect );
	}
	
	public 
	BitsAnimationWidget(
			final float x,
			final float y,
			final float width,
			final float height,
			final BitsGLImage[] images ) {
		
		this( images );
		this.setRect( x, y, width, height );
	}
	
	private
	BitsAnimationWidget(
			final BitsGLImage[] images ) {

		this.mImages = images;
		this.mMaxIndex = images.length;	
	}
	
	public final boolean
	isDone() {
		
		return this.mDone;
	}
	
	public final void
	setLoop(
			final boolean doLoop ) {
		
		this.mWantLoop = doLoop;
	}
	
	public final void
	start() {
		
		this.mTimer.start();
	}
	
	public final void
	start(
			final boolean reverse ) {
		
		this.mReverse = reverse;
		if( reverse == true ) {
			this.mIndex = this.mMaxIndex - 1;
		}
		this.start();
	}
	
	public final void
	pause() {
		
		this.mTimer.pause();
	}
	
	public final void
	stop() {
		
		this.mDone = false;
		this.mIndex = 0;
		this.mTimer.stop();
	}
	
	public final void
	setRect(
			final BitsRect rect ) {
		
		if( rect == null ) {
			BitsLog.e("BitsAnimationWidget - setRect", "The given BitsRectF is Null!");
			throw new NullPointerException("The given BitsRectF is Null!");
		}
		
    	if( rect.mWidth < 0 || rect.mHeight < 0 ) {
    		BitsLog.e("BitsAnimationWidget - setRect", "The given width and height values must be >= 0!");
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
			BitsLog.e("BitsAnimationWidget - setRect", "The given BitsRectF is Null!");
			throw new NullPointerException("The given BitsRectF is Null!");
		}
		
    	if( rect.mWidth < 0 || rect.mHeight < 0 ) {
    		BitsLog.e("BitsAnimationWidget - setRect", "The given width and height values must be >= 0!");
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
    		BitsLog.e("BitsAnimationWidget - setRect", "The given width and height values must be >= 0!");
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
		
		if( this.mImages == null ) {
			this.mImages = new BitsGLImage[1];
		}
		this.mImages[0] = image;
	}
	
	public final void
	setImages(
			final BitsGLImage[] images ) {
		
		if( images == null ) {
			BitsLog.e("BitsAnimationWidget - setImages", "The given BitsGLImage array is Null!");
			throw new NullPointerException("The given BitsGLImage array  is Null!");
		}
		
		this.mImages = images;
		this.mMaxIndex = images.length;
	}
	
	public final void
	setRegions(
			final BitsGLTextureRegion[] regions ) {

		this.mRegions = regions;
		this.mMaxIndex = regions.length;
	}
	
	public final void
	update() {
		
		if( this.mTimer.getMilliseconds() >= this.mDelay ) {
			if( this.mReverse == true ) {
				this.mIndex -= 1;
				if( this.mIndex == -1 ) {
					if( this.mWantLoop == true ) {
						this.mIndex = this.mMaxIndex - 1;
					} else {
						this.mIndex += 1;
						this.mDone = true;
						return;
					}
				}
			} else {
				this.mIndex += 1;
				if( this.mIndex == this.mMaxIndex ) {
					if( this.mWantLoop == true ) {
						this.mIndex = 0;
					} else {
						this.mIndex -= 1;
						this.mDone = true;
						return;
					}
				}
			}
			this.mTimer.start();
		}
	}
	
	@Override
	public final void 
	onAdd() {

	}

	@Override
	public final void 
	onRemove() {

		this.stop();
	}

	@Override
	public final void 
	onDrawFrame(
			final BitsGLGraphics g ) {
		
		if( this.isVisible == false ) {
			return;
		}

		if( this.mImages != null && this.mIndex >= 0 && this.mIndex < this.mMaxIndex ) {
			if( this.mRegions == null ) {
				g.drawImage( this.mImages[ this.mIndex ], this.mRect );
			} else {
				g.cropImage( this.mImages[0], this.mRect, this.mRegions[ this.mIndex ].mRect);
			}
		}
	}

	@Override
	public final void 
	onUpdate(
			final float delta ) {
		
		if( this.isActive == false || this.isVisible == false || this.mDone == true ) {
			return;
		}

		this.update();
	}

	@Override
	public final boolean 
	onPointerDown(
			final int pointerId, 
			final float x, 
			final float y,
			final BitsPointerEvent event ) {

		return this.isClickable;
	}

	@Override
	public final boolean 
	onPointerUp(
			final int pointerId, 
			final float x, 
			final float y,
			final BitsPointerEvent event ) {

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
			final BitsPointerEvent event ) {

		return this.isClickable;
	}
}
