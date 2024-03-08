package apoIcejump.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;

import org.apogames.entity.ApoEntity;

public class ApoIcejumpDrag extends ApoEntity {

	public static final Color COLOR_BACKGROUND = new Color(254, 254, 254, 240);
	
	private float maxWidth;
	
	private float width;
	
	private Rectangle2D.Float myRealRec;
	
	public ApoIcejumpDrag(float x, float y, float width, float height, float maxWidth) {
		super(null, x, y, maxWidth, height);
		this.maxWidth = maxWidth;
		this.myRealRec = new Rectangle2D.Float(x, y, width, height);
		this.width = width;
	}
	
	public boolean mouseReleased(int x, int y) {
		if (super.getRec().contains(x, y)) {
			float myX = (x - this.width/2);
			if (myX + this.width > this.getX() + this.maxWidth) {
				myX = this.maxWidth - this.width + this.getX();
			} else if (myX < this.getX()) {
				myX = this.getX();
			}
			this.myRealRec.x = (myX);
			return true;
		}
		return false;
	}
	
	public boolean mouseMoved(int x, int y) {
		if (super.getRec().contains(x, y)) {
			if (this.myRealRec.contains(x, y)) {
				super.setBSelect(true);
			} else {
				super.setBSelect(false);
			}
			return true;
		} else {
			super.setBSelect(false);
		}
		return false;
	}
	
	public float getPercent() {
		float percent = (this.myRealRec.x - this.getX()) * 100 / (float)(this.maxWidth - this.width);
		return percent;
	}
	
	public void setToPercent(float percent) {
		float width = this.maxWidth - this.width;
		float myX = (int)((percent) * width / 100 + this.getX());
		this.myRealRec.x = myX;
	}
	
	public void render(Graphics2D g, int x, int y) {
		g.setColor(Color.BLACK);
		g.fillRect((int)(this.getX() + this.width/2), (int)(this.getY() + this.getHeight()/2 - 1), (int)(this.maxWidth - this.width), (int)(2));
		
		Polygon p = new Polygon();
		p.addPoint((int)(this.myRealRec.getX()), (int)(this.myRealRec.getY() + this.myRealRec.getHeight()/2));
		p.addPoint((int)(this.myRealRec.getX() + this.myRealRec.getWidth()/2), (int)(this.myRealRec.getY()));
		p.addPoint((int)(this.myRealRec.getX() + this.myRealRec.getWidth()), (int)(this.myRealRec.getY() + this.myRealRec.getHeight()/2));
		p.addPoint((int)(this.myRealRec.getX() + this.myRealRec.getWidth()/2), (int)(this.myRealRec.getY() + this.myRealRec.getHeight()));

		g.fillPolygon(p);
		//g.fillOval((int)(this.myRealRec.getX()), (int)(this.myRealRec.getY()), (int)(this.myRealRec.getWidth() - 1), (int)(this.myRealRec.getHeight() - 1));
		if (super.isBSelect()) {
			g.setColor(Color.YELLOW);
			//g.drawOval((int)(this.myRealRec.getX()), (int)(this.myRealRec.getY()), (int)(this.myRealRec.getWidth() - 1), (int)(this.myRealRec.getHeight() - 1));			
			g.drawPolygon(p);
		}
	}

}
