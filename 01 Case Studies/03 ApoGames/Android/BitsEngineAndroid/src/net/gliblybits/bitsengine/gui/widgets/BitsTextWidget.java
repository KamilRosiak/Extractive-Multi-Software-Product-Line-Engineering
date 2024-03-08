package net.gliblybits.bitsengine.gui.widgets;

import net.gliblybits.bitsengine.graphics.opengl.BitsGLFont;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;
import net.gliblybits.bitsengine.gui.BitsWidget;
import net.gliblybits.bitsengine.input.BitsPointerEvent;
import net.gliblybits.bitsengine.render.state.BitsRenderState;
import net.gliblybits.bitsengine.utils.BitsLog;

public final class 
BitsTextWidget 
extends BitsWidget {

	public boolean wantCenter = false;
	
	public int mX = 0;
	public int mY = 0;
	
	public String mText = "";
	
	public float[] 		mColor = { 1f, 1f, 1f, 1f };
	public BitsGLFont 	mFont  = null;
	
	public 
	BitsTextWidget(
			final int x,
			final int y,
			final String text,
			final BitsGLFont font ) {
		
		this.mX = x;
		this.mY = y;
		
		this.mText = text;
		this.mFont = font;
	}
	
    public final void 
    setColor( 
    		final int red, 
    		final int green, 
    		final int blue ) {
    	
    	this.setColor( red/255f, green/255f, blue/255f, this.mColor[3] );
    }    
    
    public final void 
    setColor( 
    		final int red, 
    		final int green,
    		final int blue, 
    		final int alpha ) {
    	
    	this.setColor( red/255f, green/255f, blue/255f, alpha/255f );
    }

    public final void 
    setColor( 
    		final float red, 
    		final float green, 
    		final float blue ) {
    	
    	this.setColor( red, green, blue, this.mColor[3] );
    }
    
    public final void 
    setColor( 
    		final float red, 
    		final float green, 
    		final float blue, 
    		final float alpha ) {
    	
		if( red < 0f || green < 0f || blue < 0f || alpha < 0f || red > 1f || green > 1f || blue > 1f || alpha > 1f ) {
			BitsLog.e("BitsTextWidget - setColor", "All color values must be >= 0f and <= 1f!");
			throw new IllegalArgumentException("All color values must be >= 0f and <= 1f!");
		}
    	
        this.mColor[0] = red;
        this.mColor[1] = green;
        this.mColor[2] = blue;
        this.mColor[3] = alpha;
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
		
    	if( this.isVisible == false ) { //don't render if it is invisible
    		return;
    	}

        if(this.mText != null && this.mFont != null) {
        	
        	g.saveStates( BitsRenderState.STATE_COLOR | BitsRenderState.STATE_FONT );
            
        	g.setColor( this.mColor[0], this.mColor[1], this.mColor[2], this.mColor[3] );
        	g.setFont( this.mFont );
        	if( this.wantCenter == true ) {
        		g.centerText( this.mText, this.mX, this.mY );
        	} else {
        		g.drawText( this.mText, this.mX, this.mY );        		
        	}
        	
        	g.restoreStates();
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

		return false;
	}

	@Override
	public final boolean 
	onPointerUp(
			final int pointerId, 
			final float x, 
			final float y,
			final BitsPointerEvent event) {

		return false;
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

		return false;
	}

}
