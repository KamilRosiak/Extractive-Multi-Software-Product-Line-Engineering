package apoSimple.oneButton;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import apoSimple.ApoSimpleConstants;
import apoSimple.ApoSimpleImages;
import apoSimple.entity.ApoSimpleEntity;

public class ApoSimpleOneButtonSheep extends ApoSimpleEntity {
	
	public static final int MAX_Y_LEVEL_0 = 140;
	public static final int MAX_Y_LEVEL_1 = 300;
	public static final int MAX_Y_LEVEL_2 = 460;
	
	public static final float VEC = 0.17f;
	
	public static final float START_VEC_Y = -0.3f;
	
	public ApoSimpleOneButtonSheep(float x, float y, float width, float height) {
		super(x, y, width, height, ApoSimpleConstants.EMPTY, ApoSimpleConstants.RIGHT);
		
		this.setVecX(ApoSimpleOneButtonSheep.VEC);
		
		this.setDirection(super.getDirection());
	}

	public void setDirection(int direction) {
		super.setDirection(direction);
		if (direction == ApoSimpleConstants.LEFT) {
			this.setIBackground(ApoSimpleImages.ONEBUTTON_SHEEP_LEFT);
		} else {
			this.setIBackground(ApoSimpleImages.ONEBUTTON_SHEEP_RIGHT);
		}
		this.setWidth(this.getIBackground().getWidth());
		this.setHeight(this.getIBackground().getHeight());
		super.setBOpaque(true);
	}
	
	public void think(int delta, ApoSimpleOneButton game) {
		this.setVecY(this.getVecY() + 0.0007f * delta);
		if ((this.getY() + this.getHeight() < game.getCurMaxY()) || (this.getVecY() < 0)) {
			this.setY(this.getY() + this.getVecY() * delta);
		}
		if ((this.getVecY() > 0) && (this.getY() + this.getHeight() >= game.getCurMaxY())) {
			this.setVecY(0);
			this.setY(game.getCurMaxY() - this.getHeight());
		}
		this.setX(this.getX() + (this.getVecX() * delta));

		for (int i = 0; i < game.getCurLevel().getEntities().size(); i++) {
			if (this.intersects(game.getCurLevel().getEntities().get(i))) {
				this.setVecY(0);
				game.die();
				break;
			}
		}
		
		if (!game.getRec().contains(new Rectangle2D.Float(this.getX(), this.getY(), this.getWidth(), this.getHeight()))) {
			game.isDone();
		}
	}
	
	public boolean jump() {
		if (this.getVecY() == 0) {
			this.setVecY(ApoSimpleOneButtonSheep.START_VEC_Y);
			return true;
		}
		return false;
	}
	
	public void thinkMove(int delta) {
		if (this.getDirection() == ApoSimpleConstants.LEFT) {
			this.setX(this.getX() + (this.getVecX() * delta));
		} else if (this.getDirection() == ApoSimpleConstants.RIGHT) {
			this.setX(this.getX() - (this.getVecX() * delta));
		}
	}
	
	public void changeOppositeDirection() {
		if (this.getVecX() > 0) {
			this.setDirection(ApoSimpleConstants.RIGHT);
		} else if (this.getVecX() < 0) {
			this.setDirection(ApoSimpleConstants.LEFT);
		}
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		this.renderShadow(g, changeX, changeY);
		this.renderSheep(g, changeX, changeY);
	}
	
	public void renderShadow(Graphics2D g, int changeX, int changeY) {
//		g.drawImage(ApoSimpleImages.ORIGINAL_SHADOW, (int)(this.getX() - changeX), (int)(this.getY() - changeY), null);
	}
	
	public void renderSheep(Graphics2D g, int changeX, int changeY) {
		g.drawImage(this.getIBackground(), (int)(this.getX() - changeX), (int)(this.getY() - changeY), null);
	}
	
}
