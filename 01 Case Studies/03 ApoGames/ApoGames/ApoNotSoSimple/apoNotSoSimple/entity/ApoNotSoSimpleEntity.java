package apoNotSoSimple.entity;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ApoNotSoSimpleEntity extends ApoNotSoSimplePlayer {

	public static final int MAX_TIME = 1500;
	
	private boolean bStartVisible;
	
	private boolean bAnotherEntityOver;
	
	private boolean bShow;
	
	private int curChangeTime;
	
	public ApoNotSoSimpleEntity(BufferedImage pic, float x, float y, float width, float height, int fixedMovement, boolean bStartVisible) {
		super(pic, x, y, width, height);
		this.setFixedMovement(fixedMovement);
		this.bStartVisible = bStartVisible;
		this.init();
	}
	
	public void init() {
		super.init();
		super.setBVisible(this.bStartVisible);
		this.bAnotherEntityOver = false;
		this.bShow = true;
		this.curChangeTime = 0;
	}
	
	public final boolean isAnotherEntityOver() {
		return this.bAnotherEntityOver;
	}

	public void setMoving() {
		this.bAnotherEntityOver = false;
	}
	
	public void setOverAnotherEntity(int startTime) {
		this.curChangeTime = startTime;
		this.bAnotherEntityOver = true;
		this.bShow = false;
	}
	
	public void think(int delta) {
		super.think(delta);
		if (super.isMoving()) {
			if (this.bAnotherEntityOver) {
				this.bAnotherEntityOver = false;
			}
		} else if (this.bAnotherEntityOver) {
			this.curChangeTime += delta;
			if (this.curChangeTime >= ApoNotSoSimpleEntity.MAX_TIME) {
				this.bShow = !this.bShow;
				this.curChangeTime -= ApoNotSoSimpleEntity.MAX_TIME;
			}
		}
	}
	
	public final void render( Graphics2D g, int x, int y ) {
		if (super.isBVisible()) {
			if (super.getIBackground() != null) {
				if ((this.bAnotherEntityOver) && (this.bShow)) {
					Composite composite = g.getComposite();
					float alpha = 1 - ((float)this.curChangeTime) / (float)ApoNotSoSimpleEntity.MAX_TIME;
					g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
					g.drawImage(this.getIBackground(), (int)(this.getX() + x), (int)(this.getY() + y), null);
					g.setComposite(composite);
				} else if ((this.bAnotherEntityOver) && (!this.bShow)) {
					Composite composite = g.getComposite();
					float alpha = ((float)this.curChangeTime) / (float)ApoNotSoSimpleEntity.MAX_TIME;
					g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
					g.drawImage(this.getIBackground(), (int)(this.getX() + x), (int)(this.getY() + y), null);
					g.setComposite(composite);
				} else {
					super.render(g, x, y);
				}
			}
		}
	}

}
