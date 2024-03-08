package apoMario.entity;

import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import apoMario.ApoMarioConstants;
import apoMario.ApoMarioImageContainer;
import apoMario.level.ApoMarioLevel;

public class ApoMarioKoopa extends ApoMarioGumba {

	private boolean bGreen;
	
	public ApoMarioKoopa(BufferedImage animation, float x, float y,	float width, float height, int tiles, long time, boolean fall, boolean fly, boolean left, boolean bGreen, int id) {
		super(animation, x, y, width, height, tiles, time, fall, fly, left, id);
		
		super.setPoints(ApoMarioConstants.POINTS_ENEMY_KOOPA);
		this.bGreen = bGreen;
		
		this.setBLeft(this.isBLeft());
	}
	
	public void init() {
		super.init();
		this.setBLeft(this.isBLeft());
	}
	
	public void setBLeft(boolean bLeft) {
		super.setBLeft(bLeft);
		if (this.isBLeft()) {
			this.setVecX(-ApoMarioConstants.KOOPA_VEC_X);
		} else {
			this.setVecX(ApoMarioConstants.KOOPA_VEC_X);
		}
	}
	
	public void setBFlyOriginal(boolean bFly) {
		super.setBFlyOriginal(bFly);
		super.setBFly(bFly);
		if (this.isBFly()) {
			this.setVecY(-ApoMarioConstants.ENEMY_VEC_Y);
		} else {
			this.setVecY(0);
		}
	}
	
	public boolean isBGreen() {
		return this.bGreen;
	}

	public void die(ApoMarioLevel level, int player) {
		BufferedImage iShell = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(4 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = iShell.createGraphics();
		if (this.bGreen) {
			g.drawImage(ApoMarioImageContainer.ENEMIES.getSubimage(3 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 3 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 4 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
		} else {
			g.drawImage(ApoMarioImageContainer.ENEMIES.getSubimage(3 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 4 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
		}
		g.dispose();
		level.getEnemies().add(new ApoMarioShell(iShell, this.getX(), this.getY() + ApoMarioConstants.TILE_SIZE, ApoMarioConstants.TILE_SIZE, ApoMarioConstants.TILE_SIZE, 4, ApoMarioConstants.ENEMY_ANIMATION_TIME, player, ++ApoMarioLevel.ID));
		super.die(level, player);
		this.setVecY(-ApoMarioConstants.ENEMY_VEC_Y_DIE);
	}
	
	/**
	 * gibt das aktuelle Rechteck der Entity zurück
	 * @return gibt das aktuelle Rechteck der Entity zurück
	 */
	public Rectangle2D.Float getRec() {
		this.getRectangle().x = this.getX();
		this.getRectangle().y = this.getY() + 6 * ApoMarioConstants.SIZE;
		this.getRectangle().width = this.getWidth();
		this.getRectangle().height = this.getHeight() - 6 * ApoMarioConstants.SIZE;
		return this.getRectangle();
	}
	
	/**
	 * gibt das Rechteck für den nächsten Schritt der Entity zurück
	 * @return gibt das Rechteck für den nächsten Schritt der Entity zurück
	 */
	public Rectangle2D.Float getNextRec(int delta) {
		this.nextRec.x = this.getX() + delta * this.getVecX();
		this.nextRec.y = this.getY() + delta * this.getVecY() + 6 * ApoMarioConstants.SIZE;
		this.nextRec.width = this.getWidth();
		this.nextRec.height = this.getHeight() - 6 * ApoMarioConstants.SIZE;
		return this.nextRec;
	}
}
