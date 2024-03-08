package apoCommando.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.apogames.entity.ApoEntity;

import apoCommando.ApoMarioConstants;
import apoCommando.level.ApoMarioLevel;

public class ApoMarioDestructableWall extends ApoMarioWall {

	/** eine ArrayListe mit den Partikeln, die nach dem Einsammel dargestllt werden */
	private ArrayList<ApoEntity> particle;
	/** Bild der Partikel */
	private BufferedImage iParticle;
	private BufferedImage iAnimation;	
	private BufferedImage iWall;
	
	private boolean bWall;
	
	public ApoMarioDestructableWall(BufferedImage animation, BufferedImage iWall, float x, float y, int tiles, int time, int id) {
		super(animation, x, y, animation.getWidth()/tiles, animation.getHeight(), tiles, time, id);
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
	
	public ArrayList<ApoEntity> getParticle() {
		return this.particle;
	}

	public boolean isBWall() {
		return this.bWall;
	}

	private void makeParticle() {
		this.particle = new ArrayList<ApoEntity>();
		ApoEntity entity =new ApoEntity(this.iParticle, this.getX(), this.getY(), this.iParticle.getWidth(), this.iParticle.getHeight());
		entity.setVelocityX(-ApoMarioConstants.WALL_PARTICLE_VEC_X);
		entity.setVelocityY(-ApoMarioConstants.WALL_PARTICLE_VEC_Y);
		this.particle.add(entity);
		entity =new ApoEntity(this.iParticle, this.getX(), this.getY() + ApoMarioConstants.TILE_SIZE/2, this.iParticle.getWidth(), this.iParticle.getHeight());
		entity.setVelocityX(-ApoMarioConstants.WALL_PARTICLE_VEC_X);
		entity.setVelocityY(+ApoMarioConstants.WALL_PARTICLE_VEC_Y);
		this.particle.add(entity);
		entity =new ApoEntity(this.iParticle, this.getX() + ApoMarioConstants.TILE_SIZE/2, this.getY() + ApoMarioConstants.TILE_SIZE/2, this.iParticle.getWidth(), this.iParticle.getHeight());
		entity.setVelocityX(ApoMarioConstants.WALL_PARTICLE_VEC_X);
		entity.setVelocityY(-ApoMarioConstants.WALL_PARTICLE_VEC_Y);
		this.particle.add(entity);
		entity =new ApoEntity(this.iParticle, this.getX() + ApoMarioConstants.TILE_SIZE/2, this.getY(), this.iParticle.getWidth(), this.iParticle.getHeight());
		entity.setVelocityX(ApoMarioConstants.WALL_PARTICLE_VEC_X);
		entity.setVelocityY(ApoMarioConstants.WALL_PARTICLE_VEC_Y);
		this.particle.add(entity);		
	}
	
	public void damageWall(ApoMarioLevel level, int player) {	
		if (level.getPlayer().getType() == ApoMarioConstants.PLAYER_TYPE_LITTLE) {
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
				level.getPlayer().setPoints(level.getPlayer().getPoints() + ApoMarioConstants.POINTS_DESTRUCTABLE_WALL);
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
				this.particle.get(i).setVelocityY(this.particle.get(i).getVelocityY() + delta * ApoMarioConstants.WALL_PARTICLE_ADD_VEC_Y);
				this.particle.get(i).setY(this.particle.get(i).getY() + delta * this.particle.get(i).getVelocityY());
				this.particle.get(i).setX(this.particle.get(i).getX() + delta * this.particle.get(i).getVelocityX());
				if (this.particle.get(i).getY() > ApoMarioConstants.GAME_HEIGHT) {
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
					g.drawImage(super.getIBackground(), (int)(this.getX() - changeX), (int)(this.getY() - changeY + super.getChangeY()), null);
				} else {
					if (this.particle.size() <= 0) {
						super.render(g, changeX, changeY);
					}
				}
			}
			if (this.particle.size() > 0) {
				for (int i = this.particle.size() - 1; i >= 0; i--) {
					this.particle.get(i).render(g, -changeX, -changeY);
				}
			}			
		} catch (Exception ex) {
		}
	}

}