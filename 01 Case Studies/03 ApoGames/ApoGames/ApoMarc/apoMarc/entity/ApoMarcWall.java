package apoMarc.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import apoMarc.ApoMarcConstants;
import apoMarc.game.ApoMarcGame;

public class ApoMarcWall extends ApoMarcEntity {
	
	private int colorOrderOrg;
	
	private int colorOrder;
	
	public ApoMarcWall(float x, float y, float width, float height, int colorOrder) {
		super(x, y, width, height, ApoMarcConstants.COLOR_ORDER[colorOrder], true);
		
		this.colorOrderOrg = colorOrder;
		this.colorOrder = colorOrder;
	}
	
	public void init() {
		super.init();
		
		super.setBRepeat(false);
		
		this.colorOrder = this.colorOrderOrg;
		this.setMyColor(ApoMarcConstants.COLOR_ORDER[this.colorOrder]);
	}
	
	public void makeEffects(int x, int y) {
		super.makeEffects(x, y);
		super.setBRepeat(true);
	}
	
	public void littleMinSize() {
		super.littleMinSize();
		super.setBRepeat(false);
	}

	@Override
	public void thinkEntity(int delta, ApoMarcGame game) {
	}
	
	public int getColorOrder() {
		return this.colorOrder;
	}

	public void nextColor() {
		this.colorOrder += 1;
		if (this.colorOrder >= ApoMarcConstants.COLOR_ORDER.length) {
			this.colorOrder = 0;
		}
		this.setMyColor(ApoMarcConstants.COLOR_ORDER[this.colorOrder]);
	}

	@Override
	public void renderEntity(Graphics2D g) {
		int startX = (int)(this.getX());
		int startY = (int)(this.getY());
		int width = (int)(this.getWidth());
		int height = (int)(this.getHeight());
		Color color = super.getMyColor();
		int add = 255 / this.getCurWidth();
		for (int j = 0; j < this.getCurWidth(); j++) {
			int alpha = 255 - (j + 1) * add;
			if (alpha < 0) {
				alpha = 0;
			}
			g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha));
			if (width > height) {
				g.fillRect(startX, startY - (j+1), width, 1);
				g.fillRect(startX, startY + height - 1 + (j+1), width, 1);
			} else {
				g.fillRect(startX - (j+1), startY, 1, height);
				g.fillRect(startX + width - 1 + (j+1), startY, 1, height);
			}
		}
		
		g.setColor(Color.WHITE);
		g.fillRoundRect(startX, startY, width, height, 5, 5);
	}

}
