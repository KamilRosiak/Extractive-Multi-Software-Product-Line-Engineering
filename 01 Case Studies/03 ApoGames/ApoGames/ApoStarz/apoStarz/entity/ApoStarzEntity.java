package apoStarz.entity;

import java.awt.image.BufferedImage;

import org.apogames.entity.ApoAnimation;

import apoStarz.ApoStarzConstants;
import apoStarz.level.ApoStarzLevel;

public abstract class ApoStarzEntity extends ApoAnimation {

	/** boolean Variable, die angibt, ob sich eine Entity gerade bewegt */
	private boolean bFalling;
	/** boolean Variabe, die angibt, ob sich eine Entity überhaupt bewegen kann */
	private boolean bCanFall;
	/** boolean Variabe, die angibt, ob sich eine Entity im Ziel befindet */
	private boolean bInGoal;
	
	public ApoStarzEntity(BufferedImage background, float x, float y, float width, float height, int tiles, boolean bCanFall) {
		super(background, x, y, width, height, tiles, ApoStarzConstants.ANIMATION_TIME);
		this.setBCanFall(bCanFall);
	}
	
	public void init() {
		super.init();
		if ((this.isBCanFall()) || (this instanceof ApoStarzGoal)) {
			this.setBInGoal(false);
		}
	}
	
	public boolean isBInGoal() {
		return bInGoal;
	}

	public void setBInGoal(boolean inGoal) {
		bInGoal = inGoal;
	}

	public int getStarzX() {
		return (int)(super.getX() / ApoStarzConstants.TILE_SIZE);
	}
	
	public int getStarzY() {
		return (int)(super.getY() / ApoStarzConstants.TILE_SIZE);
	}

	public boolean isBFalling() {
		return this.bFalling;
	}

	public void setBFalling(boolean bFalling) {
		this.bFalling = bFalling;
	}

	public boolean isBCanFall() {
		return this.bCanFall;
	}

	public void setBCanFall(boolean bCanFall) {
		this.bCanFall = bCanFall;
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
					(level.getLevel()[this.getStarzY()][newX] != ApoStarzConstants.FIRE)) {
					
					this.setBFalling(false);
					this.setX(oldX * ApoStarzConstants.TILE_SIZE);
					return true;
				} else if ((level.getLevel()[this.getStarzY()][oldX] == ApoStarzConstants.FIRE) &&
						   (level.getEntities()[this.getStarzY()][oldX].isBVisible())) {
					level.getEntities()[this.getStarzY()][oldX].setBVisible(false);
					this.setBVisible(false);
					this.setBFalling(false);
					level.getLevel()[this.getStarzY()][oldX] = ApoStarzConstants.EMPTY;
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
					(level.getLevel()[newY][this.getStarzX()] != ApoStarzConstants.FIRE)) {
						this.setBFalling(false);
						this.setY(oldY * ApoStarzConstants.TILE_SIZE);
						return true;
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
	
	public int getCurrent(ApoStarzLevel level) {
		return level.getLevel()[this.getStarzY()][this.getStarzX()];
	}
	
	public abstract void think(int delta, ApoStarzLevel level);

}
