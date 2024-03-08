package apoMarc.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import org.apogames.entity.ApoEntity;

import apoMarc.ApoMarcConstants;

public class ApoMarcEffects extends ApoEntity {

	private final int MAX_TIME = 2500;
	
	private Color myColor;
	private Color alphaMyColor;
	
	private int time;
	
	public ApoMarcEffects(float x, float y, Color myColor) {
		super(null, x, y, 1, 1);
		
		super.setVecX((float)((Math.random() * 20) - 10) / 500f);
		super.setVecY((float)((Math.random() * 20) - 10) / 500f);
		
		this.myColor = myColor;
		this.alphaMyColor = new Color(myColor.getRed(), myColor.getGreen(), myColor.getBlue(), 80);
	}
	
	public void think(int delta) {
		if (!super.isBVisible()) {
			return;
		}
		super.setX(super.getX() + super.getVecX() * delta);
		super.setY(super.getY() + super.getVecY() * delta);
	
		if ((this.getX() < 0) || (this.getY() < 0) ||
			(this.getX() > ApoMarcConstants.GAME_WIDTH) || 
			(this.getY() > ApoMarcConstants.GAME_HEIGHT)) {
			super.setBVisible(false);
		}
		
		this.time += delta;
		if (this.time >= this.MAX_TIME) {
			super.setBVisible(false);
		}
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		g.setColor(this.alphaMyColor);
		int width = (int)(this.getWidth() + 3);
		g.fillRoundRect((int)(this.getX() - width/2), (int)(this.getY() - width/2), width, width, 5, 5);
		
		g.setColor(this.myColor);
		width = (int)(this.getWidth());
		g.fillRoundRect((int)(this.getX() - width/2), (int)(this.getY() - width/2), width, width, 5, 5);
	}
	
}
