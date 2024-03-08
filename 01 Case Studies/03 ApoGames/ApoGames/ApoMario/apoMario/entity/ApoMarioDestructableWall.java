package apoMario.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.apogames.entity.ApoEntity;

import apoMario.ApoMarioConstants;
import apoMario.level.ApoMarioLevel;

public class ApoMarioDestructableWall extends ApoMarioWall {

	/** eine ArrayListe mit den Partikeln, die nach dem Einsammel dargestllt werden */
	private ArrayList<ApoEntity> particle;
	/** Bild der Partikel */
	private BufferedImage iParticle;
	private BufferedImage iAnimation;	
	private BufferedImage iWall;
	
	private boolean bWall;
	
	public ApoMarioDestructableWall(BufferedImage animation, BufferedImage iWall, float x, float y, int tiles, int time, int id) {
		super(animation, x, y, animation.getWidth()/tiles / ApoMarioConstants.APP_SIZE, animation.getHeight() / ApoMarioConstants.APP_SIZE, tiles, time, id);
		this.iParticle = animation.getSubimage(0, 0, animation.getWidth()/tiles/2, animation.getHeight()/2);
		super.setBLoop(true);
		super.setBDestructable(true);
		super.setBCanBeUp(true);
		this.iWall = iWall;
		this.iAnimation = animation;
		this.init();
	}

	public void init() {
		super.init();
		this.particle = new ArrayList<ApoEntity>();
		super.setIBackground(this.iAnimation);
		this.bWall = false;
	}
	
	public void setIWall(BufferedImage wall) {
		this.iWall = wall;
	}

	public ArrayList<ApoEntity> getParticle() {
		return this.particle;
	}

	public boolean isBWall() {
		return this.bWall;
	}

	private void makeParticle() {
		this.particle = new ArrayList<ApoEntity>();
		ApoEntity entity = new ApoEntity(this.iParticle, this.getX(), this.getY(), this.iParticle.getWidth(), this.iParticle.getHeight());
		entity.setVecX(-ApoMarioConstants.WALL_PARTICLE_VEC_X);
		entity.setVecY(-ApoMarioConstants.WALL_PARTICLE_VEC_Y);
		this.particle.add(entity);
		entity =new ApoEntity(this.iParticle, this.getX(), this.getY() + ApoMarioConstants.TILE_SIZE/2, this.iParticle.getWidth(), this.iParticle.getHeight());
		entity.setVecX(-ApoMarioConstants.WALL_PARTICLE_VEC_X);
		entity.setVecY(+ApoMarioConstants.WALL_PARTICLE_VEC_Y);
		this.particle.add(entity);
		entity =new ApoEntity(this.iParticle, this.getX() + ApoMarioConstants.TILE_SIZE/2, this.getY() + ApoMarioConstants.TILE_SIZE/2, this.iParticle.getWidth(), this.iParticle.getHeight());
		entity.setVecX(ApoMarioConstants.WALL_PARTICLE_VEC_X);
		entity.setVecY(-ApoMarioConstants.WALL_PARTICLE_VEC_Y);
		this.particle.add(entity);
		entity = new ApoEntity(this.iParticle, this.getX() + ApoMarioConstants.TILE_SIZE/2, this.getY(), this.iParticle.getWidth(), this.iParticle.getHeight());
		entity.setVecX(ApoMarioConstants.WALL_PARTICLE_VEC_X);
		entity.setVecY(ApoMarioConstants.WALL_PARTICLE_VEC_Y);
		this.particle.add(entity);		
	}
	
	public BufferedImage getIWall() {
		return this.iWall;
	}

	public void damageWall(ApoMarioLevel level, int player) {	
		if (level.getPlayers().get(player).getType() == ApoMarioConstants.PLAYER_TYPE_LITTLE) {
			if (this.iWall != null) {
				if ((!(this.getIBackground().equals(this.iWall)))) {
					super.setIBackground(this.iWall);
					super.addGoodie(level, player);
					super.enemyCheck(level, player);
					this.bWall = true;
				}
			} else if ((this.iWall == null) || (!(this.getIBackground().equals(this.iWall)))) {
				super.damageWall(level, player);
				super.enemyCheck(level, player);
			}
		} else {
			if (this.iWall != null) {
				if ((!(this.getIBackground().equals(this.iWall)))) {
					super.setIBackground(this.iWall);
					super.addGoodie(level, player);
					super.enemyCheck(level, player);
					this.bWall = true;
				}
			} else {
				level.getPlayers().get(player).setPoints(level.getPlayers().get(player).getPoints() + ApoMarioConstants.POINTS_DESTRUCTABLE_WALL);
				this.makeParticle();
				super.enemyCheck(level, player);
			}
		}
	}
	
	public void think(int delta, ApoMarioLevel level) {
		if (super.isBVisible()) {
			super.setLevel(level);
			super.think(delta);
			if ((this.iWall == null) || (!this.getIBackground().equals(this.iWall))) {
				super.thinkBeUp(delta);
			}
			for (int i = this.particle.size() - 1; i >= 0; i--) {
				this.particle.get(i).think(delta);
				this.particle.get(i).setVecY(this.particle.get(i).getVecY() + delta * ApoMarioConstants.WALL_PARTICLE_ADD_VEC_Y);
				this.particle.get(i).setY(this.particle.get(i).getY() + delta * this.particle.get(i).getVecY());
				this.particle.get(i).setX(this.particle.get(i).getX() + delta * this.particle.get(i).getVecX());
				if (this.particle.get(i).getY() > ApoMarioConstants.GAME_HEIGHT / ApoMarioConstants.APP_SIZE) {
					this.particle.remove(i);
				}
				if (this.particle.size() <= 0) {
					this.setBVisible(false);
				}
			}
		}
	}
	
	/**
	 * rendert den Spieler, falls er sichtbar ist hin
	 */
	public void render(Graphics2D g, int changeX, int changeY) {
		try {
			if (super.isBVisible()) {
				if ((this.iWall != null) && (this.iWall.equals(super.getIBackground()))) {
					g.drawImage(super.getIBackground(), (int)(this.getX() - changeX) * ApoMarioConstants.APP_SIZE, (int)(this.getY() - changeY + super.getChangeY()) * ApoMarioConstants.APP_SIZE, null);
				} else {
					if (this.particle.size() <= 0) {
						super.render(g, changeX, changeY);
					}
				}
			}
			if (this.particle.size() > 0) {
				for (int i = this.particle.size() - 1; i >= 0; i--) {
					if ((this.particle.get(i).getIBackground() != null) && (this.isBVisible())) {
						g.drawImage(this.particle.get(i).getIBackground(), (int) (this.particle.get(i).getX() - changeX) * ApoMarioConstants.APP_SIZE, (int) (this.particle.get(i).getY() - changeY) * ApoMarioConstants.APP_SIZE, null);
					}
				}
			}			
		} catch (Exception ex) {
		}
	}

}