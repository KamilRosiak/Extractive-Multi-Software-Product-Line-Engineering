package apoJump.entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import apoJump.ApoJumpConstants;
import apoJump.level.ApoJumpLevel;

public class ApoJumpPlatformInvisible extends ApoJumpPlatformNormal {

	private int maxTime;
	
	private int curTime;
	
	public ApoJumpPlatformInvisible(BufferedImage background, float x, float y, float width, float height, float startVecX, float startVecY) {
		super(background, x, y, width, height, startVecX, startVecY);
	}
	
	public void init() {
		super.init();
		this.curTime = 0;
		this.maxTime = (int)(Math.random() * 1500 + 4000);
	}
	
	public void update(int delta, ApoJumpLevel level) {
		if (this.curTime < this.maxTime) {
			this.curTime += delta;
		}
		if (this.curTime >= this.maxTime) {
			this.setBVisible(false);
		}
		if (this.isBVisible()) {
			super.update(delta, level);
		}
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		if ((this.getY() - changeY >= 0) && (this.getY() - changeY <= ApoJumpConstants.GAME_HEIGHT) && (this.isBVisible())) {
			if (this.getIBackground() == null) {
				g.setColor(Color.WHITE);
				g.fillRoundRect((int)(this.getX() - changeX), (int)(this.getY() - changeY), (int)(this.getWidth()), (int)(this.getHeight()), 15, 15);
				g.setColor(Color.BLACK);
				g.drawRoundRect((int)(this.getX() - changeX), (int)(this.getY() - changeY), (int)(this.getWidth()), (int)(this.getHeight()), 15, 15);
			} else {
				float alpha = 1 - ((float)this.curTime / (float)this.maxTime);
				Composite composite = g.getComposite();
				AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
				g.setComposite(ac);
				super.render(g, changeX, changeY);
				g.setComposite(composite);
			}
		}
	}

}
