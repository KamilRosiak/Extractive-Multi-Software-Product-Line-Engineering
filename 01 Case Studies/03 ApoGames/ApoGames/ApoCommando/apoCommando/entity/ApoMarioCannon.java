package apoCommando.entity;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import apoCommando.ApoMarioConstants;
import apoCommando.level.ApoMarioLevel;

public class ApoMarioCannon extends ApoMarioWall {

	private BufferedImage iCanon;
	
	private int shootTime;
	
	private int startTime;
	
	private int curShootTime;
	
	private ArrayList<ApoMarioCannonCannon> cannons; 
	
	public ApoMarioCannon(BufferedImage animation, BufferedImage iCanon, float x, float y, int shootTime, int startTime, int id) {
		super(animation, x, y, animation.getWidth(), animation.getHeight(), 1, 1000000000, id);
		this.iCanon = iCanon;
		this.shootTime = shootTime;
		this.startTime = startTime;
		this.curShootTime = startTime;
	}
	
	public void init() {
		super.init();
		this.curShootTime = this.startTime;
	}
	
	public ArrayList<ApoMarioCannonCannon> getCannons() {
		return this.cannons;
	}

	public void think(int delta, ApoMarioLevel level) {
		super.think(delta);
		this.curShootTime -= delta;
		if (this.curShootTime <= 0) {
			this.curShootTime += this.shootTime;
			this.makeCannon(level);
		}
	}
	
	public void thinkCanon(int delta, ApoMarioLevel level) {
		if (this.cannons != null) {
			for (int i = this.cannons.size() - 1; i >= 0; i--) {
				this.cannons.get(i).think(delta, level);
				if (level.getPlayer().isBVisible()) {
					Point p = this.getMinMax(level, ApoMarioConstants.GAME_WIDTH/2);
					if ((this.cannons.get(i).getX() < p.x - ApoMarioConstants.GAME_WIDTH*1/2) ||
						(this.cannons.get(i).getX() > p.y + ApoMarioConstants.GAME_WIDTH*1/2) ||
						(!this.cannons.get(i).isBVisible())) {
						this.cannons.remove(i);
						break;
					}
				}
			}
		}
	}
	
	private void makeCannon(ApoMarioLevel level) {
		Rectangle2D.Float rec = level.getPlayer().getRec();
		if ((rec.getX() + rec.getWidth() > this.getX() - ApoMarioConstants.TILE_SIZE) &&
			(rec.getX() < this.getX() + 2 * ApoMarioConstants.TILE_SIZE)) {
			return;
		}
		if (this.cannons == null) {
			this.cannons = new ArrayList<ApoMarioCannonCannon>();
		}
		BufferedImage cannon = new BufferedImage(this.iCanon.getWidth(), this.iCanon.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = cannon.createGraphics();
		float vecX = ApoMarioConstants.CANNON_VEC_X;
		if (level.getPlayer().getX() < this.getX()) {
			vecX = -vecX;
			g.drawImage(this.iCanon, 0, 0, this.iCanon.getWidth(), this.iCanon.getHeight(), this.iCanon.getWidth(), 0, 0, this.iCanon.getHeight(), null);
		} else {
			g.drawImage(this.iCanon, 0, 0, null);
		}
		g.dispose();
		this.cannons.add(new ApoMarioCannonCannon(cannon, this.getX(), this.getY(), ++ApoMarioLevel.ID));
		this.cannons.get(this.cannons.size() - 1).setVelocityX(vecX);
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		try {
			super.render(g, changeX, changeY);
		} catch (Exception ex) {
			
		}
	}

}
