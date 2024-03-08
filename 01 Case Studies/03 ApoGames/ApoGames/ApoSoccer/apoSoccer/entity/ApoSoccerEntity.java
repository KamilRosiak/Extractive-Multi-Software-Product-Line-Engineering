package apoSoccer.entity;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.apogames.entity.ApoAnimation;
import org.apogames.entity.ApoEntity;
import org.apogames.help.ApoHelp;

import apoSoccer.ApoSoccerConstants;
import apoSoccer.ApoSoccerImages;

/**
 * Klasse von der der Ball und die Spieler erben
 * und ihnen einige wichtige Informationen bereit stellt
 * wie z.B. Speed oder Blickrichtung
 * @author Dirk Aporius
 */
public class ApoSoccerEntity extends ApoAnimation {

	/** boolean-Variable die angibt ob der Spieler zu seiner Ausgangsposition läuft */
	private boolean bRunPlayer;
	/** Variable, die die Blickrichtung der Entity angibt */
	private int startLineOfSight;
	/** Variable, die die Blickrichtung der Entity angibt */
	private int lineOfSight;
	/** Variable, die den Speed der Entity angibt */
	private float speed;
	/** Variable, die die Zeit des Spiels angibt */
	private long timeElapsed;
	
	/**
	 * Rechteck, was den Spielraum repräsentiert,
	 * den der Spieler nicht verlassen darf
	 */
	private Rectangle2D.Float walkableArea;
	
	/** das OriginalPlayer Bild */
	private BufferedImage oldPlayer;
	
	public ApoSoccerEntity(BufferedImage background, float x, float y, float radius, int tiles, long time, Rectangle2D.Float rec) {
		super(background, x, y, radius, radius, tiles, time);
		
		this.walkableArea = rec;
		
		this.oldPlayer = ApoSoccerImages.getImageCopy(this.getIBackground());
	}
	
	public void init() {
		super.init();
		
		this.setLineOfSight( this.getStartLineOfSight() );
		this.setTimeElapsed(0);
	}

	/**
	 * gibt die Zeit die vergangen ist im Spiel zurück
	 * @return gibt die Zeit die vergangen ist im Spiel zurück
	 */
	public long getTimeElapsed() {
		return this.timeElapsed;
	}

	/**
	 * setzt die Zeit die im Spiel vergangen ist, auf den übergebenen Wert
	 * @param timeElapsed : neue Zeit
	 */
	public void setTimeElapsed(long timeElapsed) {
		this.timeElapsed = timeElapsed;
	}

	/**
	 * gibt das OriginalBild des Spielers zurück
	 * @return gibt das OriginalBild des Spielers zurück
	 */
	public BufferedImage getOldPlayer() {
		return this.oldPlayer;
	}

	/**
	 * gibt zurück, ob der Spieler gerade zu seiner Ausgangsposition rennt
	 * @return TRUE, Spieler rennt, FALSE Spieler da
	 */
	public boolean isBRunPlayer() {
		return this.bRunPlayer;
	}

	/**
	 * setzt die boolean Variable, ob der Spieler gerade zu seiner Ausgangsposition rennt auf den übergebenen Wert
	 * @param bRunPlayer : TRUE, Spieler rennt, FALSE Spieler da
	 */
	public void setBRunPlayer(boolean bRunPlayer) {
		this.bRunPlayer = bRunPlayer;
	}

	/**
	 * gibt den Startwert für die Richtung in die geschaut werden soll zurück
	 * @return gibt den Startwert für die Richtung in die geschaut werden soll zurück
	 */
	public int getStartLineOfSight() {
		return this.startLineOfSight;
	}

	public void setStartLineOfSight(int startLineOfSight) {
		while (startLineOfSight > 360) {
			startLineOfSight -= 360;
		}
		while (startLineOfSight < 0) {
			startLineOfSight += 360;
		}
		this.startLineOfSight = startLineOfSight;
	}

	/**
	 * gibt den Winkel zwischen dieser Entity und dem übergebenen Punkt zurück
	 * @param x : X-Wert des Punktes
	 * @param y : Y-Wert des Punktes
	 * @return gibt den Winkel zwischen dieser Entity und dem übergebenen Punkt zurück
	 */
	public double getAngleBetweenTwoPoints(float x, float y) {
		return ApoHelp.getAngleBetween2Points(x, y, this.getX(), this.getY());
	}
	
	/**
	 * gibt den Winkel zwischen dem myPoint und dem anderen übergeben Punkt zurück
	 * @param x : x-Wert des Punktes
	 * @param y : y- Wert des Punktes
	 * @param myX : x-Wert des MyPunktes
	 * @param myY : y-Wert des MyPunktes
	 * @return gibt den Winkel zwischen dem myPoint und dem anderen übergeben Punkt zurück
	 */
	public double getAngleBetweenTwoPoints(float x, float y, float myX, float myY) {
		return ApoHelp.getAngleBetween2Points(x, y, myX, myY);
	}
	
	/**
	 * gibt das Rechteck zurück, in welcher sich die Entity bewegen darf
	 * @return gibt das Rechteck zurück, in welcher sich die Entity bewegen darf
	 */
	public Rectangle2D.Float getWalkableArea() {
		return this.walkableArea;
	}
	
	public void setWalkableArea(Rectangle2D.Float walkableArea) {
		this.walkableArea = walkableArea;
	}

	/**
	 * gibt zurück, ob die Entity noch in ihrem Bereich ist
	 * @return TRUE, Entity ist im begehbaren Bereich, sonst FALSE
	 */
	protected boolean isInWalkableArea() {
		return this.getWalkableArea().contains(this.getX() - this.getWidth(), this.getY() - this.getHeight(), this.getWidth() * 2, this.getHeight() * 2);
	}

	/**
	 * gibt die aktuelle Blickrichtung zurück
	 * @return gibt die aktuelle Blickrichtung zurück
	 */
	public int getLineOfSight() {
		return this.lineOfSight;
	}

	public void setLineOfSight(int lineOfSight) {
		while (lineOfSight > 360) {
			lineOfSight -= 360;
		}
		while (lineOfSight < 0) {
			lineOfSight += 360;
		}
		this.lineOfSight = lineOfSight;
	}
	
	/**
	 * gibt den aktuellen Speed der Entity zurück
	 * @return gibt den aktuellen Speed der Entity zurück
	 */
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		if (speed > ApoSoccerConstants.MAX_SPEED_SHOOT) {
			speed = ApoSoccerConstants.MAX_SPEED_SHOOT;
		} else if (speed < ApoSoccerConstants.MIN_SPEED_SHOOT) {
			speed = ApoSoccerConstants.MIN_SPEED_SHOOT;
		}
		this.speed = speed;
	}

	public boolean intersects(ApoEntity entity) {
		return this.intersects(entity.getX(), entity.getY(), entity.getWidth());
	}
	
	/**
	 * überprüft, ob diese Entity sich mit den übergebenen Werten schneidet
	 * @param x : x-Wert des Mittelpunktes der mit überprüft werden soll
	 * @param y : y-Wert des Mittelpunktes der mit überprüft werden soll
	 * @param radius : Radius der zu überprüfenden Werte
	 * @return TRUE, falls sie sich schneiden, sonst FALSE
	 */
	public boolean intersects(float x, float y, float radius) {
		float newX = ((x - this.getX())*(x - this.getX()));
		float newY = ((y - this.getY())*(y - this.getY()));
		float newRadius = ((radius + this.getWidth())*(radius + this.getWidth()));
		if ( newX + newY <= newRadius ) {
			return true;
		}
		return false;
	}
	
	/**
	 * gibt den Abprallwinkel mit der übergebenen Entity zurück
	 * @param entity : andere Entity, um den Abprallwinkel zu berechnen
	 * @return gibt den Abprallwinkel mit der übergebenen Entity zurück
	 */
	public int getReboundAngle(ApoSoccerEntity entity) {
		/**double angle = this.getAngleBetweenTwoPoints(entity.getX(), entity.getY());
		int los = entity.getLineOfSight();
		double difference = Math.abs(angle - los);
		double result = angle;
		if (los > angle) {
			result -= difference;
		} else {
			result += difference;
		}
		if (result > 360) {
			result -= 360;
		} else if (result < 0) {
			result += 360;
		}*/
		double angle = this.getAngleBetweenTwoPoints(entity.getX(), entity.getY());
		int los = entity.getLineOfSight();
		double result = 2.0f*angle - los;

		if (result > 360) {
			result -= 360;
		} else if (result < 0) {
			result += 360;
		}

		//System.out.println("angle = "+angle+" los = "+los+" dif = "+difference+" result = "+result);
		return (int)result;
	}
	
	/**
	 * wird aufgerufen, wenn der Ball mit einem Spieler kollidiert und setzt den Ball zurück
	 * @param entityOne : Spieler der mit dem Ball kollidiert
	 * @param ball : der Ball
	 * @return immer TRUE
	 */
	public boolean setEntitiyBackBall(ApoSoccerEntity entityOne, ApoBall ball) {
		int count = 0;
		while ((entityOne.intersects(ball.getX(), ball.getY(), ball.getWidth())) && (count < 20)) {
			float radiusOne = 0.5f;
			count++;
			int losOne = (int)this.getAngleBetweenTwoPoints(entityOne.getX(), entityOne.getY(), ball.getX(), ball.getY());
			ball.setX( ball.getX() - radiusOne * (float)Math.cos(Math.toRadians(losOne)) );
			ball.setY( ball.getY() - radiusOne * (float)Math.sin(Math.toRadians(losOne)) );
		}
		if (entityOne.getSpeed() > ApoSoccerConstants.MAX_SPEED_WITH_BALL) {
			entityOne.setSpeed(ApoSoccerConstants.MAX_SPEED_WITH_BALL);
		}
		if (ball.getSpeed() < entityOne.getSpeed()) {
			ball.setSpeed(entityOne.getSpeed());
		}
		return true;
	}
	
	/**
	 * setzt die erste Entity wieder zurück, wo sie herkam weil
	 * @param entityOne
	 * @param entityTwo
	 * @return immer TRUE
	 */
	public boolean setEntitiyBack(ApoSoccerEntity entityOne, ApoSoccerEntity entityTwo) {
		int count = 0;
		while ((entityOne.intersects(entityTwo)) && (count < 20)) {
			float radiusOne = 0.5f;
			count++;
			int losOne = (int)this.getAngleBetweenTwoPoints(entityOne.getX(), entityOne.getY(), entityTwo.getX(), entityTwo.getY());//entityOne.getLineOfSight();// - 180;
			entityOne.setX( entityOne.getX()+ radiusOne * (float)Math.cos(Math.toRadians(losOne)) );
			entityOne.setY( entityOne.getY() + radiusOne * (float)Math.sin(Math.toRadians(losOne)) );
		}
		return true;
	}
	
	public void think(int delta) {
		super.think(delta);
	}
	
}
