package apoSlitherLink.game;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import org.apogames.entity.ApoEntity;

import apoSlitherLink.ApoSlitherLinkConstants;
import apoSlitherLink.ApoSlitherLinkImages;

public class ApoSlitherLinkCloud extends ApoEntity {

	private int cloud;
	
	private Rectangle2D overRec;
	
	public ApoSlitherLinkCloud(int cloud, float x, float y, float width, float height, float vecX) {
		super(null, x, y, width, height);
		this.cloud = cloud;
		super.setVecX(vecX);
	}
	
	public void think(int delta) {
		if (super.getVecX() != 0) {
			this.setX(this.getX() + super.getVecX() * delta);
		}
	}
	
	public Rectangle2D getOverRec() {
		return this.overRec;
	}

	public void setOverRec(Rectangle2D overRec) {
		this.overRec = overRec;
	}

	public boolean shouldDelete() {
		if ((super.getX() + super.getWidth() < -10) ||
			(super.getX() > ApoSlitherLinkConstants.GAME_WIDTH + 10)) {
			return true;
		}
		return false;
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		try {
			if (super.isBVisible()) {
				g.drawImage(ApoSlitherLinkImages.clouds[this.cloud], (int)(this.getX() + changeX), (int)(this.getY() + changeY), null);
			}
		} catch (Exception ex) {
		}
	}

}
