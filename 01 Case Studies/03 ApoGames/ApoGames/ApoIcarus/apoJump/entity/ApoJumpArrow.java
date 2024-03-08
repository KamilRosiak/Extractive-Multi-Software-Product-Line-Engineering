package apoJump.entity;

import java.awt.Graphics2D;

import org.apogames.help.ApoHelp;

import apoJump.ApoJumpConstants;
import apoJump.ApoJumpImageContainer;
import apoJump.level.ApoJumpLevel;

public class ApoJumpArrow extends ApoJumpPlatformNormal {

	public ApoJumpArrow(float x, float y, float startVecX, float startVecY) {
		super(ApoJumpImageContainer.iArrow, x, y, ApoJumpImageContainer.iArrow.getWidth(), ApoJumpImageContainer.iArrow.getHeight(), startVecX, startVecY);

		int degrees = 0;
		float newX = x + startVecX * 100;
		float newY = y + startVecY * 100;
		degrees= (int)(ApoHelp.getAngleBetween2Points(newX, newY, x, y));
		super.setIBackground(ApoHelp.rotateImage(ApoJumpImageContainer.iArrow, degrees));
	}

	@Override
	public void update(int delta, ApoJumpLevel level) {
		for (ApoJumpEnemy entity: level.getEnemies()) {
			if (entity.isBVisible()) {
				if ((entity.getY() + ApoJumpConstants.GAME_HEIGHT > this.getY()) && (entity.getY() - ApoJumpConstants.GAME_HEIGHT < this.getY())) {
					if (entity.intersects(this)) {
						level.addPoint(entity.getPoints());
						level.addEnemyKill();
						entity.setBVisible(false);
						this.setBVisible(false);
						break;
					}
				}
			}
		}
		this.setX(this.getX() + this.getVelocityX() * delta);
		if (this.getX() < 0) {
			this.setBVisible(false);
		} else if (this.getX() + this.getWidth() > ApoJumpConstants.GAME_WIDTH) {
			this.setBVisible(false);
		}
		this.setY(this.getY() + this.getVelocityY() * delta);
		if (this.getY() - level.getChangeY() < 0) {
			this.setBVisible(false);
		} else if (this.getY() - level.getChangeY() > ApoJumpConstants.GAME_HEIGHT) {
			this.setBVisible(false);
		}
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		if ((this.getY() - changeY >= 0) && (this.getY() - changeY <= ApoJumpConstants.GAME_HEIGHT) && (this.isBVisible())) {
			if (this.getIBackground() == null) {
			} else {
				super.render(g, -changeX, changeY);
			}
		}
	}
}
