package apoIcejump.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.apogames.entity.ApoAnimation;
import org.apogames.entity.ApoEntity;

import apoIcejump.ApoIcejumpConstants;

public class ApoIcejumpBird extends ApoAnimation {
	
	private ApoAnimation fireAnimation;
	
	private ApoEntity iceBlock;
	
	public ApoIcejumpBird(BufferedImage animation, float x, float y, float width, float height, int tiles, long time, boolean bLeft) {
		super(animation, x, y, width, height, tiles, time);
		
		if (!bLeft) {
			this.setVecX((float)(Math.random() * 0.035 - 0.07f));
			if (this.getVecX() > -0.035f) {
				this.setVecX(-0.035f);
			}
		} else {
			this.setVecX((float)(Math.random() * 0.035) + 0.035f);
			if (this.getVecX() < 0.035f) {
				this.setVecX(0.015f);
			}
		}
	}
	
	public Rectangle2D.Float getRec() {
		return new Rectangle2D.Float(this.getX(), this.getY() + 17, this.getWidth(), this.getHeight() - 26);
	}
	
	public void setFireAnimation(ApoAnimation animation) {
		this.fireAnimation = animation;
		this.setVecY(0.001f);
	}
	
	public void setIceBlock(ApoEntity entity) {
		BufferedImage iIceBlock = new BufferedImage((int)entity.getWidth(), (int)entity.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = (Graphics2D)iIceBlock.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(new Color(255, 255, 255, 160));
		g.fillRoundRect(0, 0, iIceBlock.getWidth() - 1, iIceBlock.getHeight() - 1, 4, 4);
		g.setColor(Color.WHITE);
		g.drawRoundRect(0, 0, iIceBlock.getWidth() - 1, iIceBlock.getHeight() - 1, 4, 4);

		g.dispose();
		entity.setIBackground(iIceBlock);
		this.setVecY(0.001f);
		this.setTime(10000);
		this.setFrame(1);
		this.iceBlock = entity;
	}
	
	public void init() {
		super.init();
	}
	
	public void think(int delta) {
		if (super.isBVisible()) {
			if (this.iceBlock == null) {
				super.think(delta);
			}
			if (this.getVecX() != 0) {
				super.setX(super.getX() + super.getVecX() * delta);
			}
			if (this.getVecY() != 0) {
				super.setVecY(super.getVecY() + ApoIcejumpConstants.BIRD_DECREASE_Y * delta);
				super.setY(super.getY() + super.getVecY() * delta);
				if (this.fireAnimation != null) {
					this.fireAnimation.think(delta);
				}
			}
			if ((this.getX() + this.getWidth() < 0) ||
				(this.getX() > ApoIcejumpConstants.GAME_WIDTH)) {
				super.setBVisible(false);
			}
		}
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		if ((this.getVecY() > 0) && (this.fireAnimation != null)) {
			this.fireAnimation.render(g, (int)(this.getRec().getX() + changeX + this.getWidth()/2 - this.fireAnimation.getWidth()/2), (int)(this.getRec().getY() + changeY + this.getHeight()/2 - this.fireAnimation.getHeight() - 3));
		}
		super.render(g, changeX, changeY);
		if ((this.getVecY() > 0) && (this.iceBlock != null)) {
			this.iceBlock.render(g, (int)(this.getRec().getX() + changeX + this.getWidth()/2 - this.iceBlock.getWidth()/2), (int)(this.getRec().getY() + changeY + this.getHeight()/2 - this.iceBlock.getHeight()/2 - 13));
		}
	}
}
