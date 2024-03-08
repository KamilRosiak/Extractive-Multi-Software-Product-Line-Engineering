package apoIcejump.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.apogames.entity.ApoEntity;

import apoIcejump.ApoIcejumpConstants;

public class ApoIcejumpParticle {

	private final int PARTICLE_TIME = 1000;
	
	private BufferedImage iParticle;
	
	private ArrayList<ApoEntity> particles;
	
	private int time;
	
	private boolean bVisible;
	
	public ApoIcejumpParticle(BufferedImage iParticle, ApoEntity source) {
		this.iParticle = iParticle;
		this.particles = new ArrayList<ApoEntity>();
		this.bVisible = true;
		this.time = this.PARTICLE_TIME;
		this.init(source);
	}
	
	private void init(ApoEntity source) {
		int count = (int)(Math.random() * 30 + 40);
		for (int i = 0; i < count; i++) {
			float vecX = (float)(Math.random() * 0.5 - 0.25f);
			if ((vecX < 0) && (vecX > -0.01)) {
				vecX = -0.007f;
			} else if ((vecX < 0) && (vecX > -0.01)) {
				vecX = -0.007f;
			}
			float vecY = -(float)(Math.random() * 0.3f);
			
			int random = (int)(Math.random() * ApoIcejumpConstants.PARTICLE_TILES);
			if (random >= ApoIcejumpConstants.PARTICLE_TILES) {
				random = ApoIcejumpConstants.PARTICLE_TILES - 1;
			}
			BufferedImage myParticle = this.iParticle.getSubimage(random * this.iParticle.getWidth()/ApoIcejumpConstants.PARTICLE_TILES, 0, this.iParticle.getWidth()/ApoIcejumpConstants.PARTICLE_TILES, this.iParticle.getHeight());
			ApoEntity entity = new ApoEntity(myParticle, source.getX() + source.getWidth()/2, source.getY(), myParticle.getWidth(), myParticle.getHeight());
			entity.setVecX(vecX);
			entity.setVecY(vecY);
			this.particles.add(entity);
		}
	}
	
	public boolean isBVisible() {
		return this.bVisible;
	}

	public void think(int delta) {
		this.time -= delta;
		if (this.time <= 0) {
			this.bVisible = false;
		} else {
			for (int i = 0; i < this.particles.size(); i++) {
				ApoEntity entity = this.particles.get(i);
				entity.setX(entity.getX() + entity.getVecX() * delta);
				entity.setY(entity.getY() + entity.getVecY() * delta);
			}
		}
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		for (int i = 0; i < this.particles.size(); i++) {
			this.particles.get(i).render(g, changeX, changeY);
		}
	}

}
