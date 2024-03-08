package apoCommando.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import apoCommando.ApoMarioConstants;
import apoCommando.level.ApoMarioLevel;

public class ApoMarioQuestionmark extends ApoMarioWall {
	
	private BufferedImage iAnimation;	
	private BufferedImage iWall;
	
	private boolean bWall;
	
	public ApoMarioQuestionmark(BufferedImage animation, BufferedImage iWall, float x, float y, int goodie, int id) {
		super(animation, x, y, animation.getWidth()/4, animation.getHeight(), 4, ApoMarioConstants.COIN_ANIMATION_TIME, id);
		super.setGoodie(goodie);
		super.setBLoop(true);
		super.setBAnimation(true);
		this.iWall = iWall;
		this.iAnimation = animation;
		this.init();
	}

	public void init() {
		super.init();
		super.setIBackground(this.iAnimation);
		this.bWall = false;
	}
	
	public void damageWall(ApoMarioLevel level, int player) {
		if ((this.iWall != null) && (!(this.getIBackground().equals(this.iWall)))) {
			super.setIBackground(this.iWall);
			super.addGoodie(level, player);
			super.enemyCheck(level, player);
			this.bWall = true;
		}
	}
	
	public boolean isBWall() {
		return this.bWall;
	}

	public void think(int delta, ApoMarioLevel level) {
		if ((this.iWall != null) && (this.iWall.equals(this.getIBackground()))) {
		} else {
			super.setLevel(level);
			super.think(delta);
		}
	}
	
	/**
	 * rendert den Spieler, falls er sichtbar ist hin
	 */
	public void render(Graphics2D g, int changeX, int changeY) {
		try {
			if (super.isBVisible()) {
				if ((this.iWall != null) && (this.iWall.equals(super.getIBackground()))) {
					g.drawImage( super.getIBackground(), (int)(this.getX() - changeX), (int)(this.getY() - changeY), null);
				} else {
					super.render(g, changeX, changeY);
				}
			}
		} catch (Exception ex) {
		}
	}


}
