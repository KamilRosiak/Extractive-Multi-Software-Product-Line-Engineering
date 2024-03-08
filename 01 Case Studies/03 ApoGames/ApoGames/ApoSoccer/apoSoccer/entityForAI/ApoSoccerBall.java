package apoSoccer.entityForAI;

import java.awt.geom.Point2D;

import apoSoccer.ApoSoccerConstants;
import apoSoccer.entity.ApoBall;

/**
 * Klasse, die den Ball repräsentiert</br>
 * ballclass</br>
 * @author Dirk Aporius
 *
 */
public class ApoSoccerBall {

	private ApoBall ball;
	
	public ApoSoccerBall(ApoBall ball) {
		this.ball = ball;
	}
	
	/**
	 * gibt die x-Korrdinate des Balles zurück</br>
	 * returns the x-Value of the ball
	 * @return gibt die x-Korrdinate des Balles zurück,</br>
	 * returns the x-Value of the ball
	 */
	public int getX() {
		return (int)this.ball.getX();
	}
	
	/**
	 * gibt die y-Korrdinate des Balles zurück,</br>
	 * returns the y-Value of the ball
	 * @return gibt die y-Korrdinate des Balles zurück,</br>
	 * returns the y-Value of the ball
	 */
	public int getY() {
		return (int)this.ball.getY();
	}
	
	/**
	 * gibt den Radius des Balles zurück</br>
	 * returns the radius of the ball
	 * @return gibt den Radius des Balles zurück,</br>
	 * returns the radius of the ball
	 */
	public int getRadius() {
		return (int)this.ball.getWidth();
	}
	
	/**
	 * gibt die Geschwindigkeit des Balles zurück, </br>
	 * returns the speed of the ball
	 * @return gibt die Geschwindigkeit des Balles zurück,</br>
	 * returns the speed of the ball
	 */
	public int getSpeed() {
		return (int)this.ball.getSpeed();
	}
	
	/**
	 * gibt die Blickrichtung des Balles zurück,</br>
	 * returns the line of sight of the ball
	 * @return gibt die Blickrichtung des Balles zurück,</br>
	 * returns the line of sight of the ball
	 */
	public int getLineOfSight() {
		return (int)this.ball.getLineOfSight();
	}
	
	/**
	 * gibt die Zeit die vergangen ist zurück,</br>
	 * returns the time which is elapsed in the game
	 * @return gibt die Blickrichtung des Balles zurück,</br>
	 * returns the time which is elapsed in the game
	 */
	public int getTimeElapsed() {
		return (int)this.ball.getTimeElapsed();
	}
	
	/**
	 * gibt die Zeit die vergangen ist zurück,</br>
	 * returns the time which is elapsed in the game
	 * @return gibt die Zeit die vergangen ist zurück</br>
	 * returns the time which is elapsed in the game
	 */
	public int getTimeInMinutes() {
		return (int)this.ball.getTimeInMinutes();
	}

	/**
	 * gibt den Punkt, welchen der Ball in x-steps haben wird, zurück</br>
	 * returns the point of the ball where the ball will be in x-steps</br>
	 * MAX = ApoSoccerConstants.BALL_STEP_SAVE
	 * @return gibt den Punkt, welchen der Ball in x-steps haben wird, zurück</br>
	 * returns the point of the ball where the ball will be in x-steps</br>
	 */
	public Point2D.Float whereIsTheBallInXSteps(int x) {
		if (x <= 0) {
			x = 1;
		} else if (x > ApoSoccerConstants.BALL_STEP_SAVE) {
			x = ApoSoccerConstants.BALL_STEP_SAVE;
		}
		return this.ball.whereIsTheBallInXSteps(x);
	}
}
