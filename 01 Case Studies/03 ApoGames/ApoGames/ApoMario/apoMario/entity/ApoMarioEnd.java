package apoMario.entity;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import apoMario.ApoMarioConstants;
import apoMario.ApoMarioImageContainer;
import apoMario.level.ApoMarioLevel;

public class ApoMarioEnd extends ApoMarioEnemy {

	private boolean bUp;
	
	private float curY;
	
	public ApoMarioEnd(float x, float y, float width, float height, int id) {
		super(null, x, y, width, height, 1, 10000000, ApoMarioConstants.POINTS_END, id);
	}
	
	public void init() {
		super.init();
		
		super.setVecX(0);
		super.setVecY(0);
		
		this.bUp = true;
		this.curY = 0;
	}

	/**
	 * gibt das aktuelle Rechteck der Entity zurück
	 * @return gibt das aktuelle Rechteck der Entity zurück
	 */
	public Rectangle2D.Float getRec() {
		this.getRectangle().x = this.getX() + 1f/2f * ApoMarioConstants.TILE_SIZE;
		this.getRectangle().y = this.getY();
		this.getRectangle().width = this.getWidth();
		this.getRectangle().height = this.getHeight();
		return this.getRectangle();
	}
	
	public void think(int delta, ApoMarioLevel level) {
		if (this.bUp) {
			this.curY -= delta * ApoMarioConstants.END_VEC_Y;
			if (this.curY < -(this.getHeight() - ApoMarioConstants.TILE_SIZE)) {
				this.bUp = !this.bUp;
			}
		} else {
			this.curY += delta * ApoMarioConstants.END_VEC_Y;
			if (this.curY >= 0) {
				this.bUp = !this.bUp;
			}
		}
	}
	
	@Override
	public void coinCheck(ApoMarioLevel level, int x, int y) {
		
	}

	@Override
	public void xCheckChange(ApoMarioLevel level, int x, int y) {
	}

	@Override
	public void yCheckUp(ApoMarioLevel level, int x, int y) {
	}

	@Override
	public void yDownCheck(ApoMarioLevel level, int x, int y, int delta) {
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(12 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 4 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), (int)(this.getX() + changeX) * ApoMarioConstants.APP_SIZE, (int)(this.getY() + changeY) * ApoMarioConstants.APP_SIZE, null);
		g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(13 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 4 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), (int)(this.getX() + changeX + this.getWidth() + changeY - ApoMarioConstants.TILE_SIZE) * ApoMarioConstants.APP_SIZE, (int)(this.getY() * ApoMarioConstants.APP_SIZE), null);
		
		for (int i = ApoMarioConstants.TILE_SIZE; i < (int)(this.getHeight()); i += ApoMarioConstants.TILE_SIZE) {
			g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(12 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 5 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), (int)(this.getX() + changeX) * ApoMarioConstants.APP_SIZE, (int)(this.getY() + changeY + i) * ApoMarioConstants.APP_SIZE, null);
			g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(13 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 5 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), (int)(this.getX() + changeX + this.getWidth() - ApoMarioConstants.TILE_SIZE) * ApoMarioConstants.APP_SIZE, (int)(this.getY() + changeY + i) * ApoMarioConstants.APP_SIZE, null);			
		}
		g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(12 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 3 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), (int)(this.getX() + changeX) * ApoMarioConstants.APP_SIZE, (int)(this.getY() + changeY + this.curY + this.getHeight() - ApoMarioConstants.TILE_SIZE/2) * ApoMarioConstants.APP_SIZE, null);
	}

}
