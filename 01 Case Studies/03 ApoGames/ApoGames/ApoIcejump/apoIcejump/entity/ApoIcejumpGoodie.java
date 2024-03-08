package apoIcejump.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.apogames.entity.ApoAnimation;

public class ApoIcejumpGoodie extends ApoAnimation {

	private BufferedImage iGoodie;
	
	private int goodie;
	
	public ApoIcejumpGoodie(BufferedImage animation, float x, float y, int goodie, BufferedImage iGoodie) {
		this(animation, x, y, 1, 1000000, goodie, iGoodie);
	}
	
	public ApoIcejumpGoodie(BufferedImage animation, float x, float y, int tiles, long time, int goodie, BufferedImage iGoodie) {
		super(animation, x, y, tiles, time);
		
		this.iGoodie = iGoodie;
		this.goodie = goodie;
	}

	public int getGoodie() {
		return this.goodie;
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		super.render(g, changeX, changeY);
		if (this.iGoodie != null) {
			g.drawImage(this.iGoodie, (int)(this.getX() + changeX + this.getWidth()/2 - this.iGoodie.getWidth()/2), (int)(this.getY() + changeY + this.getHeight()/2 - this.iGoodie.getHeight()/2), null);
		}
	}

}
