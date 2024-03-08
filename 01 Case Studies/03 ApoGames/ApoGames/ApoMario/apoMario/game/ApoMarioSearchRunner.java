package apoMario.game;

import java.awt.image.BufferedImage;

import org.apogames.entity.ApoAnimation;

import apoMario.ApoMarioConstants;

/**
 * eigentliche Runnerklasse, um von einer Node zu einer Anderen zu kommen
 * @author Dirk Aporius
 *
 */
public class ApoMarioSearchRunner extends ApoAnimation {

	private int endX;
	private int endY;
	
	public ApoMarioSearchRunner(BufferedImage animation, float x, float y) {
		super(animation, x, y, ApoMarioConstants.MENU_PLAYER_TILES, ApoMarioConstants.MENU_PLAYER_ANIMATION_TIME);
		
		this.endX = (int)(this.getX());
		this.endY = (int)(this.getY());
	}
	
	public void setEnd(int x, int y) {
		this.endX = x;
		this.endY = y;
	}
	
	public boolean hasReachedEnd() {
		if (((int)(this.getX()) == this.endX) && ((int)(this.getY()) == this.endY)) {
			return true;
		}
		return false;
	}
	
	public void think(int delta) {
		if (hasReachedEnd()) {
			return;
		}
		float oldX = this.getX();
		float oldY = this.getY();
		if (oldX < this.endX) {
			this.setX(this.getX() + ApoMarioConstants.MENU_PLAYER_VEC * delta * ApoMarioConstants.APP_SIZE);
			if ((int)(this.getX()) >= this.endX) {
				this.setX(this.endX);
			}
		} else if (oldX > this.endX) {
			this.setX(this.getX() - ApoMarioConstants.MENU_PLAYER_VEC * delta * ApoMarioConstants.APP_SIZE);
			if ((int)(this.getX()) <= this.endX) {
				this.setX(this.endX);
			}
		}
		if (oldY < this.endY) {
			this.setY(this.getY() + ApoMarioConstants.MENU_PLAYER_VEC * delta * ApoMarioConstants.APP_SIZE);
			if ((int)(this.getY()) >= this.endY) {
				this.setY(this.endY);
			}
		} else if (oldY > this.endY) {
			this.setY(this.getY() - ApoMarioConstants.MENU_PLAYER_VEC * delta * ApoMarioConstants.APP_SIZE);
			if ((int)(this.getY()) <= this.endY) {
				this.setY(this.endY);
			}
		}
		super.think(delta);
	}

}
