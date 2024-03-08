package apoSimple.entity;

import java.awt.geom.Rectangle2D;

import org.apogames.help.ApoFloatPoint;

import apoSimple.ApoSimpleConstants;

public class ApoSimpleEntityMenu extends ApoSimpleDrive {

	private Rectangle2D.Float rec;
	
	private ApoFloatPoint goalPoint;
	
	private float vec;
	
	public static final float MAX_VEC = 0.06f;
	public static final float MIN_VEC = 0.01f;
	public static final float MAX_STAND = 4000f;
	public static final float MIN_STAND = 2000f;
	
	private boolean bStand;
	
	private int standTime = 0;
	
	public ApoSimpleEntityMenu(float x, float y, float width, float height, Rectangle2D.Float rec) {
		super(x, y, width, height, ApoSimpleConstants.EMPTY, ApoSimpleConstants.LEFT, false);
		
		this.rec = rec;
		
		this.init();
	}

	public void init() {
		super.init();
		
		this.goalPoint = new ApoFloatPoint(this.getX(), this.getY());
		
		if (Math.random() * 100 > 60) {
			this.setBBlack(true);
		}
		
		this.newDirection();
	}
	
	private void newDirection() {
		if (this.rec != null) {
			int rand = (int)(Math.random() * 100);
			if (rand > 80) {
				this.bStand = true;
				this.standTime = (int)((Math.random() * (ApoSimpleEntityMenu.MAX_STAND - ApoSimpleEntityMenu.MIN_STAND)) + ApoSimpleEntityMenu.MIN_STAND);
			} else if (rand > 60) {
				super.setAnimation(ApoSimpleAnimationMake.getRandomAnimation(this));
				if (super.getAnimation() == null) {
					this.newDirection();
				}
			} else {
				this.bStand = false;
				this.vec = (float)((ApoSimpleEntityMenu.MAX_VEC - ApoSimpleEntityMenu.MIN_VEC) * Math.random()) + ApoSimpleEntityMenu.MIN_VEC;
				if (this.vec < ApoSimpleEntityMenu.MIN_VEC) {
					this.vec = ApoSimpleEntityMenu.MIN_VEC;
				}
				int direction = (int)(Math.random() * ApoSimpleConstants.RIGHT + 1);
				if (direction > ApoSimpleConstants.RIGHT) {
					direction = ApoSimpleConstants.RIGHT;
				}
				if (direction == ApoSimpleConstants.LEFT) {
					float width = this.rec.x + this.rec.width - this.getX();
					if (width <= 1) {
						this.newDirection();
					} else {
						this.setDirection(direction);
						this.goalPoint.x = (float)(Math.random() * (width - 1) + 1) + this.getX();
						this.goalPoint.y = this.getY();
					}
				} else if (direction == ApoSimpleConstants.RIGHT) {
					float width = this.getX() - this.rec.x;
					if (width <= 1) {
						this.newDirection();
					} else {
						this.setDirection(direction);
						this.goalPoint.x = this.rec.x + (float)(Math.random() * (width - 1));
						this.goalPoint.y = this.getY();
					}
				} else if (direction == ApoSimpleConstants.UP) {
					float width = this.getY() - this.rec.y;
					if (width <= 1) {
						this.newDirection();
					} else {
						this.setDirection(direction);
						this.goalPoint.y = this.rec.y + (float)(Math.random() * (width - 1));
						this.goalPoint.x = this.getX();
					}
				} else if (direction == ApoSimpleConstants.DOWN) {
					float width = this.rec.y + this.rec.height - this.getY();
					if (width <= 1) {
						this.newDirection();
					} else {
						this.setDirection(direction);
						this.goalPoint.y = (float)(Math.random() * (width - 1) + 1) + this.getY();
						this.goalPoint.x = this.getX();
					}
				}
			}
		}
	}
	
	public void think(int delta) {
		if (super.getAnimation() != null) {
			super.getAnimation().think(delta);
			if (super.getAnimation().isBFinish()) {
				super.setAnimation(null);
				this.newDirection();
			}
		} else if (this.bStand) {
			this.standTime -= delta;
			if (this.standTime <= 0) {
				this.newDirection();
			}
		} else {
			int direction = this.getDirection();
			if (direction == ApoSimpleConstants.UP) {
				this.setY(this.getY() - (this.vec * delta));
				if (this.getY() < this.goalPoint.y) {
					this.newDirection();
				}
			} else if (direction == ApoSimpleConstants.DOWN) {
				this.setY(this.getY() + (this.vec * delta));
				if (this.getY() > this.goalPoint.y) {
					this.newDirection();
				}
			} else if (direction == ApoSimpleConstants.LEFT) {
				this.setX(this.getX() + (this.vec * delta));
				if (this.getX() > this.goalPoint.x) {
					this.newDirection();
				}
			} else if (direction == ApoSimpleConstants.RIGHT) {
				this.setX(this.getX() - (this.vec * delta));
				if (this.getX() < this.goalPoint.x) {
					this.newDirection();
				}
			}
			this.thinkUpAndDown(delta);
		}
	}
	
}
