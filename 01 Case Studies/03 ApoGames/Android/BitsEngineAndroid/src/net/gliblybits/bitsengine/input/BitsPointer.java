package net.gliblybits.bitsengine.input;

public final class 
BitsPointer {

	public boolean 	mIsDown	 	= false;
	public float	mX			= -1f;
	public float	mY			= -1f;
	public float	mDeltaX		= -1f;
	public float	mDeltaY		= -1f;
	public boolean	mDragged	= false;
	
	public void
	reset() {
		
		this.mIsDown	= false;
		this.mX			= -1f;
		this.mY			= -1f;
		this.mDeltaX	= -1f;
		this.mDeltaY	= -1f;
		this.mDragged   = false;
	}
}
