package apoStarz.entity;

import java.awt.image.BufferedImage;

import apoStarz.ApoStarzConstants;
import apoStarz.level.ApoStarzLevel;

public class ApoStarzStar extends ApoStarzEntity {

	public ApoStarzStar(BufferedImage background, float x, float y,
			float width, float height, int tiles, boolean canFall) {
		super(background, x, y, width, height, tiles, canFall);
	}

	@Override
	public void think(int delta, ApoStarzLevel level) {
		if ((this.isBCanFall()) && (this.isBFalling())) {
			if (!this.isNextBlocked(level)) {
				if (this.getVecX() != 0) {
					this.setX(this.getX() + this.getVecX());
				} else if (this.getVecY() != 0) {
					this.setY(this.getY() + this.getVecY());
				}
				//System.out.println("vecX = "+this.getVecX()+" vecY = "+this.getVecY());
			}
		}
	}
	
	/**
	 * testet, ob im nächsten Schritt noch frei ist und er fallen kann
	 * @param level : das Level Objekt
	 * @return TRUE, der nächste Schritt ist blockiert und der Block kann nicht mehr weiterfallen
	 * FALSE, Block kann weiterfallen
	 */
	public boolean isNextBlocked(ApoStarzLevel level) {
		if (this.getVecX() != 0) {
			int newX = (int)((this.getX() + this.getVecX()) / ApoStarzConstants.TILE_SIZE);
			int oldX = this.getStarzX();
			if (this.getVecX() > 0) {
				newX = (int)((this.getX() + this.getWidth() - 1 + this.getVecX()) / ApoStarzConstants.TILE_SIZE);
				oldX = (int)((this.getX() + this.getWidth() - 1) / ApoStarzConstants.TILE_SIZE);
			}
			if (oldX != newX) {
				if ((level.getLevel()[this.getStarzY()][oldX] == ApoStarzConstants.FIRE) &&
				    (level.getEntities()[this.getStarzY()][oldX].isBVisible())) {
					level.getEntities()[this.getStarzY()][oldX].setBVisible(false);
					this.setBVisible(false);
					this.setBFalling(false);
					level.getLevel()[this.getStarzY()][oldX] = ApoStarzConstants.EMPTY;
				} else if ((level.getLevel()[this.getStarzY()][newX] != ApoStarzConstants.EMPTY) &&
					(level.getLevel()[this.getStarzY()][newX] != ApoStarzConstants.FIRE) &&
					((level.getLevel()[this.getStarzY()][newX] != ApoStarzConstants.GOAL) || 
					 (level.getEntities()[this.getStarzY()][newX].isBInGoal()))) {
					this.setBFalling(false);
					this.setX(oldX * ApoStarzConstants.TILE_SIZE);
					return true;
				} else if (level.getLevel()[this.getStarzY()][oldX] == ApoStarzConstants.GOAL) {
					super.setBInGoal(true);
					super.setBFalling(false);
					this.setX(oldX * ApoStarzConstants.TILE_SIZE);
					return true;
				} else if ((level.getLevel()[this.getStarzY()][newX] == ApoStarzConstants.GOAL) && 
						   (!level.getEntities()[this.getStarzY()][newX].isBInGoal())) {
					level.getLevel()[this.getStarzY()][oldX] = ApoStarzConstants.EMPTY;
					//System.out.println("Check bei y");
					level.getEntities()[this.getStarzY()][newX].setBInGoal(true);
					return false;
				} else {
					if (level.getLevel()[this.getStarzY()][newX] != ApoStarzConstants.FIRE) {
						level.getLevel()[this.getStarzY()][newX] = level.getLevel()[this.getStarzY()][oldX];
					}
					level.getLevel()[this.getStarzY()][oldX] = ApoStarzConstants.EMPTY;
					return false;
				}
			}
		} else if (this.getVecY() != 0) {
			int newY = (int)((this.getY() + this.getVecY()) / ApoStarzConstants.TILE_SIZE);
			int oldY = this.getStarzY();
			if (this.getVecY() > 0) {
				newY = (int)((this.getY() + this.getHeight() - 1 + this.getVecY()) / ApoStarzConstants.TILE_SIZE);
				oldY =(int)((this.getY() + this.getHeight() - 1) / ApoStarzConstants.TILE_SIZE);
			}
			if (oldY != newY) {
				if ((level.getLevel()[oldY][this.getStarzX()] == ApoStarzConstants.FIRE) &&
						   (level.getEntities()[oldY][this.getStarzX()].isBVisible())) {
					level.getEntities()[oldY][this.getStarzX()].setBVisible(false);
					this.setBVisible(false);
					this.setBFalling(false);
					level.getLevel()[oldY][this.getStarzX()] = ApoStarzConstants.EMPTY;
				} else if ((level.getLevel()[newY][this.getStarzX()] != ApoStarzConstants.EMPTY) &&
					(level.getLevel()[newY][this.getStarzX()] != ApoStarzConstants.FIRE) &&
					((level.getLevel()[newY][this.getStarzX()] != ApoStarzConstants.GOAL) || 
						 (level.getEntities()[newY][this.getStarzX()].isBInGoal()))) {
					this.setBFalling(false);
					this.setY(oldY * ApoStarzConstants.TILE_SIZE);
					return true;
				} else if (level.getLevel()[oldY][this.getStarzX()] == ApoStarzConstants.GOAL) {
					super.setBInGoal(true);
					super.setBFalling(false);
					this.setY(oldY * ApoStarzConstants.TILE_SIZE);
					return true;
				} else if ((level.getLevel()[newY][this.getStarzX()] == ApoStarzConstants.GOAL) && 
						   (!level.getEntities()[newY][this.getStarzX()].isBInGoal())) {
					level.getLevel()[oldY][this.getStarzX()] = ApoStarzConstants.EMPTY;
					//System.out.println("Check bei y");
					level.getEntities()[newY][this.getStarzX()].setBInGoal(true);
					return false;
				} else {
					if (level.getLevel()[newY][this.getStarzX()] != ApoStarzConstants.FIRE) {
						level.getLevel()[newY][this.getStarzX()] = level.getLevel()[oldY][this.getStarzX()];
					}
					level.getLevel()[oldY][this.getStarzX()] = ApoStarzConstants.EMPTY;
					return false;
				}
			}
		}
		return false;
	}

}
