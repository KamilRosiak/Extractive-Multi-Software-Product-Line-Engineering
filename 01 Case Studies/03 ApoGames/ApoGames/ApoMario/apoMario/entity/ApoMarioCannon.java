package apoMario.entity;

import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import apoMario.ApoMarioConstants;
import apoMario.level.ApoMarioLevel;

public class ApoMarioCannon extends ApoMarioWall {

	private BufferedImage iCanon;
	
	private int shootTime;
	
	private int startTime;
	
	private int curShootTime;
	
	private ArrayList<ApoMarioCannonCannon> cannons;
	
	private final int myHeight;
	
	public ApoMarioCannon(BufferedImage animation, BufferedImage iCanon, int height, float x, float y, int shootTime, int startTime, int id) {
		super(animation, x, y, animation.getWidth() / ApoMarioConstants.APP_SIZE, animation.getHeight() / ApoMarioConstants.APP_SIZE, 1, 1000000000, id);
		this.myHeight = height;
		this.iCanon = iCanon;
		this.shootTime = shootTime;
		this.setStartTime(startTime);
	}
	
	public void init() {
		super.init();
		this.setStartTime(this.startTime);
		if (this.cannons != null) {
			this.cannons.clear();
		}
	}
	
	public int getMyHeight() {
		return this.myHeight;
	}

	public int getShootTime() {
		return this.shootTime;
	}

	public int getStartTime() {
		return this.startTime;
	}

	public void setShootTime(int shootTime) {
		this.shootTime = shootTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
		this.curShootTime = this.startTime;
	}

	public ArrayList<ApoMarioCannonCannon> getCannons() {
		return this.cannons;
	}

	public void think(int delta, ApoMarioLevel level) {
		super.think(delta);
		if (!this.isPlayerNear(level)) {
			this.curShootTime -= delta;
		}
		if (this.curShootTime <= 0) {
			this.curShootTime += this.shootTime;
			this.makeCannon(level);
		}
	}
	
	public void thinkCanon(int delta, ApoMarioLevel level) {
		if (this.cannons != null) {
			for (int i = this.cannons.size() - 1; i >= 0; i--) {
				this.cannons.get(i).think(delta, level);
				for (int player = 0; player < level.getPlayers().size(); player++) {
					if (level.getPlayers().get(player).isBVisible()) {
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
	}
	
	private void makeCannon(ApoMarioLevel level) {
		for (int i = 0; i < level.getPlayers().size(); i++) {
			if (level.getPlayers().get(i).isBVisible()) {
				Rectangle2D.Float rec = level.getPlayers().get(i).getRec();
				if ((rec.getX() + rec.getWidth() > this.getX() - ApoMarioConstants.TILE_SIZE) &&
					(rec.getX() < this.getX() + 2 * ApoMarioConstants.TILE_SIZE)) {
					return;
				}
			}
		}
		if (this.cannons == null) {
			this.cannons = new ArrayList<ApoMarioCannonCannon>();
		}
		BufferedImage cannon = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(this.iCanon.getWidth(), this.iCanon.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = cannon.createGraphics();
		float vecX = ApoMarioConstants.CANNON_VEC_X;
		if (level.getPlayers().get(0).getX() < this.getX()) {
			vecX = -vecX;
			g.drawImage(this.iCanon, 0, 0, this.iCanon.getWidth(), this.iCanon.getHeight(), this.iCanon.getWidth(), 0, 0, this.iCanon.getHeight(), null);
		} else {
			g.drawImage(this.iCanon, 0, 0, null);
		}
		g.dispose();
		this.cannons.add(new ApoMarioCannonCannon(cannon, this.getX(), this.getY(), ++ApoMarioLevel.ID));
		this.cannons.get(this.cannons.size() - 1).setVecX(vecX);
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		try {
			super.render(g, changeX, changeY);
		} catch (Exception ex) {
			
		}
	}

}
