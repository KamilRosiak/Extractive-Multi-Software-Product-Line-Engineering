package apoSimple.hiddenPush;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;

import apoSimple.ApoSimpleConstants;
import apoSimple.entity.ApoSimpleEntity;

public class ApoHiddenPushEntity extends ApoSimpleEntity {

	private int goalX, goalY;
	
	private boolean shouldDeleted;
	
	private boolean isDeleted;
	
	private float transparent;
	
	public ApoHiddenPushEntity(float x, float y, float width, float height, int direction) {
		super(x, y, width, height, ApoSimpleConstants.EMPTY, direction);
	}
	
	public void init() {
		super.init();
		
		this.goalX = (int)(this.getX() / 65);
		this.goalY = (int)(this.getY() / 65);
		
		this.shouldDeleted = false;
		this.isDeleted = false;
		
		this.transparent = 255;
	}
	
	public void think(final int delta) {
		super.think(delta);
		
		if ((this.shouldDeleted) && (!this.isDeleted)) {
			if (this.transparent <= 0) {
				this.isDeleted = true;
			} else {
				this.transparent -= (float)delta * 110f / 255f;
			}
		} else if (!this.hasReachedTheEnd()) {
			this.move(delta);
		}
	}

	public void setNextGoal(final int addGoalX, final int addGoalY) {
		int oldX = (int)(this.getX() / 65);
		int oldY = (int)(this.getY() / 65);
		
		this.goalX = oldX + addGoalX;
		this.goalY = oldY + addGoalY;
		
		if (addGoalX < 0) {
			this.setVecX(-0.3f);
			this.setVecY(0);
		} else if (addGoalX > 0) {
			this.setVecX(0.3f);
			this.setVecY(0);
		} else if (addGoalY > 0) {
			this.setVecY(0.3f);
			this.setVecX(0);
		} else {
			this.setVecY(-0.3f);
			this.setVecX(0);
		}
		if (this.getVecX() < 0) {
			this.goalX -= 1;
		}
		if (this.getVecY() < 0) {
			this.goalY -= 1;
		}
	}
	
	public void move(final int delta) {
		this.setX(this.getX() + delta * this.getVecX());
		this.setY(this.getY() + delta * this.getVecY());
		
		if ((this.getX() < 0) && (this.getVecX() < 0)) {
			this.setX(0);
			this.setVecX(0);
			this.goalX += 1;
		}
		if ((this.getY() < 0) && (this.getVecY() < 0)) {
			this.setY(0);
			this.setVecY(0);
			this.goalY += 1;
		}
	}
	
	public boolean hasReachedTheEnd() {
		if (this.shouldDeleted) {
			return true;
		}
		int oldX = (int)(this.getX() / 65);
		int oldY = (int)(this.getY() / 65);
		
		if ((this.getX() < 0) && (this.getVecX() > 0)) {
			return false;
		}
		if ((this.getY() < 0) && (this.getVecY() > 0)) {
			return false;
		}
		
		if ((oldX == this.goalX) && (oldY == this.goalY) || ((this.getVecX() == 0) && (this.getVecY() == 0))) {
			return true;
		}
		return false;
	}

	public boolean isShouldDeleted() {
		return this.shouldDeleted;
	}

	public void setShouldDeleted(boolean shouldDeleted) {
		this.shouldDeleted = shouldDeleted;
	}

	public boolean isDeleted() {
		return this.isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public void render(final Graphics2D g, int changeX, int changeY) {
		if (this.shouldDeleted) {
			Composite composite = g.getComposite();
			float percent = (float)(this.transparent)/255f;
			if (percent > 1) {
				percent = 1;
			}
			if (percent < 0) {
				percent = 0;
			}
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, percent));
			super.render(g, changeX, changeY);
			g.setComposite(composite);
		} else {
			super.render(g, changeX, changeY);
		}
	}
}
