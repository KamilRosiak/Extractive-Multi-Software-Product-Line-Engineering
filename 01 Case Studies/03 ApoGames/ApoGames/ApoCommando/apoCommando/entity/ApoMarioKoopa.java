package apoCommando.entity;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import apoCommando.ApoMarioConstants;
import apoCommando.ApoMarioImageContainer;
import apoCommando.level.ApoMarioLevel;

public class ApoMarioKoopa extends ApoMarioGumba {

	private boolean bGreen;
	
	public ApoMarioKoopa(BufferedImage animation, float x, float y,	float width, float height, int tiles, long time, boolean fall, boolean fly, boolean left, boolean bGreen, int id) {
		super(animation, x, y, width, height, tiles, time, fall, fly, left, id);
		
		super.setPoints(ApoMarioConstants.POINTS_ENEMY_KOOPA);
		this.bGreen = bGreen;
		
		if (this.isBLeft()) {
			this.setVelocityX(-ApoMarioConstants.KOOPA_VEC_X);
		} else {
			this.setVelocityX(ApoMarioConstants.KOOPA_VEC_X);
		}
	}
	
	public void init() {
		super.init();
		if (this.isBLeft()) {
			this.setVelocityX(-ApoMarioConstants.KOOPA_VEC_X);
		} else {
			this.setVelocityX(ApoMarioConstants.KOOPA_VEC_X);
		}
	}
	
	public void die(ApoMarioLevel level, int player) {
		BufferedImage iShell = ApoMarioImageContainer.ENEMIES.getSubimage(3 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, 4 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE);
		if (this.bGreen) {
			iShell = ApoMarioImageContainer.ENEMIES.getSubimage(3 * ApoMarioConstants.TILE_SIZE, 3 * ApoMarioConstants.TILE_SIZE, 4 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE);
		}
		level.getEnemies().add(new ApoMarioShell(iShell, this.getX(), this.getY() + ApoMarioConstants.TILE_SIZE, ApoMarioConstants.TILE_SIZE, ApoMarioConstants.TILE_SIZE, 4, ApoMarioConstants.ENEMY_ANIMATION_TIME, player, ++ApoMarioLevel.ID));
		super.die(level, player);
		this.setVelocityY(-ApoMarioConstants.ENEMY_VEC_Y_DIE);
	}

	/**
	 * gibt das aktuelle Rechteck der Entity zurück
	 * @return gibt das aktuelle Rechteck der Entity zurück
	 */
	public Rectangle2D.Float getRec() {
		return new Rectangle2D.Float(this.getX(), this.getY() + 6 * ApoMarioConstants.SIZE, this.getWidth(), this.getHeight() - 6 * ApoMarioConstants.SIZE);
	}
	
	/**
	 * gibt das Rechteck für den nächsten Schritt der Entity zurück
	 * @return gibt das Rechteck für den nächsten Schritt der Entity zurück
	 */
	public Rectangle2D.Float getNextRec(int delta) {
		return new Rectangle2D.Float(this.getX() + delta * this.getVelocityX(), this.getY() + delta * this.getVelocityY() + 6 * ApoMarioConstants.SIZE, this.getWidth(), this.getHeight() - 6 * ApoMarioConstants.SIZE);
	}
}
