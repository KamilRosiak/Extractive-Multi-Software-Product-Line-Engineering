package apoSoccer.entity;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import apoSoccer.ApoSoccerConstants;

public class ApoBallClone {
	private ApoBall ball;
	
	private float radius, x, y, speed;
	private int lineOfSight;
	
	private int inGoal;
	
	public ApoBallClone(ApoBall ball) {
		this.ball = ball;
		this.init();
	}
	
	private void init() {
		this.radius = this.ball.getWidth();
		this.x = this.ball.getX();
		this.y = this.ball.getY();
		this.speed = this.ball.getSpeed();
		this.lineOfSight = this.ball.getLineOfSight();
		this.inGoal = -1;
	}
	
	
	public int getInGoal() {
		return this.inGoal;
	}
	
	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public int getLineOfSight() {
		return lineOfSight;
	}

	public void setLineOfSight(int lineOfSight) {
		this.lineOfSight = lineOfSight;
	}
	
	public Rectangle2D.Float getWalkableArea() {
		return ApoSoccerConstants.COMPLETE_REC;
	}

	protected boolean isInWalkableArea() {
		return this.getWalkableArea().contains(this.getX() - radius, this.getY() -radius, radius * 2, radius * 2);
	}
	
	public void think(int delta) {
		this.inGoal = -1;
		this.thinkWalkableArea();
		this.thinkSpeed(delta);
	}
	
	/**
	 * überprüft, ob der Ball mit einem Tor kollidiert ;)
	 * @param area : zu überprüfendes Rechteck (das das Tor darstellt)
	 * @param bLeft : boolean Variable, die angibt in welches Tor er geht
	 * @param game : das Spielobjekt
	 * @return immer TRUE
	 */
	private boolean thinkGoalArea(Rectangle2D.Float area, boolean bLeft) {
		if ( (this.getX() - this.getRadius() < area.x) && (!bLeft) ) {
			this.setX(area.x + this.getRadius());
			this.setLineOfSight(180 - this.getLineOfSight());
			this.setSpeed(this.getSpeed() * ApoSoccerConstants.BALL_HIT_WALL);
		// Ball kommt oben gegen die Wand
		} else if (this.getY() - this.getRadius() < area.y) {
			this.setY(area.y + this.getRadius());
			this.setLineOfSight(360 - this.getLineOfSight());
			this.setSpeed(this.getSpeed() * ApoSoccerConstants.BALL_HIT_WALL);
		// Ball kommt rechts gegen die Wand bzw Tor
		} else if ((this.getX() + this.getRadius() > area.x + area.width) && (bLeft)) {
			this.setX(area.x + area.width - this.getRadius() - 1);
			this.setLineOfSight(180 - this.getLineOfSight());
			this.setSpeed(this.getSpeed() * ApoSoccerConstants.BALL_HIT_WALL);
		// Ball kommt unten gegen die Wand
		} else if (this.getY() + this.getRadius() > area.y + area.height) {
			this.setY(area.y + area.height - this.getRadius() - 1);
			this.setLineOfSight(360 - this.getLineOfSight());
			this.setSpeed(this.getSpeed() * ApoSoccerConstants.BALL_HIT_WALL);
		}
		if (area.contains(new Point2D.Float(this.getX(), this.getY()))) {
			this.setSpeed(0);
		}
		return true;
	}
	
	/**
	 * überprüft ob der Ball auf dem Spielfeld ist und falls nicht, dann
	 * lässt er den Ball abprallen
	 * überprüft somit auch die Tore :)
	 * @param game : das SPielobjekt
	 * @return immer TRUE
	 */
	private boolean thinkWalkableArea() {
		if (!this.isInWalkableArea()) {
			//System.out.println("Check x = "+this.getX()+" y = "+this.getY()+" los = "+this.getLineOfSight());
			// Ball kommt links gegen die Wand bzw Tor
			if (this.getX() - this.getRadius() < this.getWalkableArea().x) {
				if (!new Ellipse2D.Double(this.getX() - this.getRadius(), this.getY() - this.getRadius(), this.getRadius() * 2, this.getRadius() * 2).intersects(ApoSoccerConstants.LEFT_GOAL_REC)) {
					this.setX(this.getWalkableArea().x + this.getRadius());
					this.setLineOfSight(180 - this.getLineOfSight());
				} else {
					return this.thinkGoalArea(ApoSoccerConstants.LEFT_GOAL_REC, true);
				}
			// Ball kommt oben gegen die Wand
			} else if (this.getY() - this.getRadius() < this.getWalkableArea().y) {
				this.setY(this.getWalkableArea().y + this.getRadius());
				this.setLineOfSight(360 - this.getLineOfSight());
			// Ball kommt rechts gegen die Wand bzw Tor
			} else if (this.getX() + this.getRadius() > this.getWalkableArea().x + this.getWalkableArea().width) {
				if (!new Ellipse2D.Double(this.getX() - this.getRadius(), this.getY() - this.getRadius(), this.getRadius() * 2, this.getRadius() * 2).intersects(ApoSoccerConstants.RIGHT_GOAL_REC)) {
					this.setX(this.getWalkableArea().x + this.getWalkableArea().width - this.getRadius() - 1);
					this.setLineOfSight(180 - this.getLineOfSight());
				} else {
					return this.thinkGoalArea(ApoSoccerConstants.RIGHT_GOAL_REC, false);
				}
			// Ball kommt unten gegen die Wand
			} else if (this.getY() + this.getRadius() > this.getWalkableArea().y + this.getWalkableArea().height) {
				this.setY(this.getWalkableArea().y + this.getWalkableArea().height - this.getRadius() - 1);
				this.setLineOfSight(360 - this.getLineOfSight());
			}
			this.setSpeed(this.getSpeed() * ApoSoccerConstants.BALL_HIT_WALL);
		}
		return true;
	}
	
	/**
	 * bewegt den Ball falls sein Speed über 0 ist
	 * @param delta : Zeit, die seit dem letzten Aufruf vergangen ist
	 * @return immer TRUE
	 */
	private boolean thinkSpeed(int delta) {
		if (this.getSpeed() > 0) {
			//System.out.println("Check x = "+this.getX()+" y = "+this.getY()+" los = "+this.getLineOfSight());
			float radius = ApoSoccerConstants.MAX_VEC_BALL * (float)this.getSpeed() / 100.0f / 20.0f * delta;
			double alpha = this.getLineOfSight();// + 90;
			if (alpha > 360) {
				alpha = 360 - alpha;
			} else if (alpha < 0) {
				alpha = 360 + alpha;
			}
			if (radius > 0.01f) {
				float newX = this.getX() + radius * (float)Math.cos(Math.toRadians(alpha));
				float newY = this.getY() + radius * (float)Math.sin(Math.toRadians(alpha));
				this.setX(newX);
				this.setY(newY);
				this.setSpeed(this.getSpeed() * ApoSoccerConstants.BALL_OVER_GRASS);
			} else {
				this.setSpeed(0);
			}
		}
		return true;
	}
}
