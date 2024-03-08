package apoSoccer.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import apoSoccer.ApoSoccerConstants;
import apoSoccer.entityForAI.ApoSoccerBall;
import apoSoccer.game.ApoSoccerGame;

/**
 * Klasse, die den Ball repräsentiert
 * @author Dirk Aporius
 *
 */
public class ApoBall extends ApoSoccerEntity {
	
	private final int BALL_SHOOT_WIDTH = 8;
	
	/** Variable, die beim Thinkaufruf der Spieler mit übergeben wird */
	private ApoSoccerBall ball;
	
	private ArrayList<Point2D.Float> place;
	
	/** ArrayListe die die Punkte mit den letzten Aufenthaltspunkten des Balles enthält */
	private ArrayList<Point> ballPoint;
	/** Name des Spielers der zuletzt am Ball war*/
	private String lastContactPlayerName;
	/** Name des Teams, das zuletzt am Ball war */
	private String lastContactTeamName;
	/** Name des Spielers der zuletzt den Ball geschossen hat*/
	private String lastShootPlayerName;
	/** Name des Teams, welches zuletzt den Ball geschossen hat */
	private String lastShootTeamName;
	
	private ApoSoccerGame game;
	
	/** Konstruktor
	 * @param background
	 * @param x
	 * @param y
	 * @param radius
	 * @param tiles
	 * @param time
	 * @param rec
	 */
	public ApoBall(BufferedImage background, float x, float y, float radius, int tiles, long time, Rectangle2D.Float rec) {
		super(background, x, y, radius, tiles, time, rec);
		
		this.ball = new ApoSoccerBall(this);
	}

	public void init() {
		super.init();
		this.setLineOfSight(0);
		//this.setLineOfSight(45);
		this.setSpeed(0);
		this.ballPoint = new ArrayList<Point>();
		this.lastShootPlayerName = "";
		this.lastShootTeamName = "";
		this.lastContactPlayerName = "";
		this.lastContactTeamName = "";
	}
	
	/**
	 * gibt den Namen des Spielers zurück, der den Ball als letztes geschossen hat
	 * @return gibt den Namen des Spielers zurück, der den Ball als letztes geschossen hat
	 */
	public String getLastShootPlayerName() {
		return this.lastShootPlayerName;
	}

	/**
	 * setzt den Namen des Spielers, der den Ball als letztes geschossen hat, auf den übergebenen
	 * @param lastShootPlayerName : neuer Name
	 */
	public void addLastShootPlayerName(String lastShootPlayerName) {
		this.lastShootPlayerName = lastShootPlayerName;
	}

	/**
	 * gibt den Namen des Teams zurück, das den Ball als letztes geschossen hat
	 * @return gibt den Namen des Teams zurück, das den Ball als letztes geschossen hat
	 */
	public String getLastShootTeamName() {
		return lastShootTeamName;
	}

	/**
	 * setzt den Namen des Teams, das den Ball als letztes geschossen hat, auf den übergebenen
	 * @param lastShootTeamName : neuer Name
	 */
	public void addLastShootTeamName(String lastShootTeamName) {
		this.lastShootTeamName = lastShootTeamName;
	}
	
	/**
	 * gibt den Namen des Spielers zurück, der den Ball als letztes geschossen hat
	 * @return gibt den Namen des Spielers zurück, der den Ball als letztes geschossen hat
	 */
	public String getLastContactPlayerName() {
		return this.lastContactPlayerName;
	}

	/**
	 * setzt den Namen des Spielers, der den Ball als letztes geschossen hat, auf den übergebenen
	 * @param lastContactPlayerName : neuer Name
	 */
	public void addLastContactPlayerName(String lastContactPlayerName) {
		this.lastContactPlayerName = lastContactPlayerName;
	}

	/**
	 * gibt den Namen des Teams zurück, das den Ball als letztes geschossen hat
	 * @return gibt den Namen des Teams zurück, das den Ball als letztes geschossen hat
	 */
	public String getLastContactTeamName() {
		return lastContactTeamName;
	}

	/**
	 * setzt den Namen des Teams, das den Ball als letztes geschossen hat, auf den übergebenen
	 * @param lastContactTeamName : neuer Name
	 */
	public void addLastContactTeamName(String lastContactTeamName) {
		this.lastContactTeamName = lastContactTeamName;
	}

	/**
	 * gibt den Ball für die THinkaufrufe der SPieler zurück
	 * @return gibt den Ball für die Thinkaufrufe der Spieler zurück
	 */
	public ApoSoccerBall getBall() {
		return this.ball;
	}
	
	public int getTimeInMinutes() {
		return this.game.getTimeForGraphic();
	}
	
	/**
	 * gibt die Zeit die vergangen ist im Spiel zurück
	 * @return gibt die Zeit die vergangen ist im Spiel zurück
	 */
	public long getTimeElapsed() {
		return this.game.getTime();
	}
	
	/**
	 * wird alle delta aufgerufen und animiert und bewegt den Ball
	 * @param delta : Zeit, die seit dem letzten Aufruf vergangen ist in Millisekunden
	 * @param game : Das Spielobjekt, um an die Spieler zu kommen
	 */
	public void think(int delta, ApoSoccerGame game) {
		super.think(delta);
		this.game = game;
		this.thinkPlayerCollision(delta, game);
		this.thinkWalkableArea(game);
		this.thinkSpeed(delta);
		if (!this.thinkBallPoint(delta, game)) {
			game.ballReset();
		}
		this.makePlace(delta, game);
	}
	
	/**
	 * überprüft, ob der Ball mit dem übergebenen Spieler kollidiert und lässt den Ball dann korrekt(?)
	 * abprallen
	 * @param player : Spieler zur Überprüfung
	 * @return TRUE, wenn Kollision, sonst FALSE
	 */
	private boolean checkPlayerCollision(ApoPlayer player, ApoSoccerGame game) {
		if (player.isBRunPlayer()) {
			return false;
		}
		if (this.intersects(player)) {
			int los = player.getReboundAngle(this);
			if (this.getSpeed() < 100) {
				los = player.getLineOfSight();
			}
			this.setSpeed(this.getSpeed() * ApoSoccerConstants.BALL_HIT_PLAYER);
			this.setEntitiyBackBall(player, this);
			this.setLineOfSight(los);
			player.setBallContactInformations(this, game);
			return true;
		}
		return false;
	}
	
	/**
	 * überprüft den Spieler, ob er in dem Bereich ist, wo er schiessen darf
	 * und falls ja dann setzt er die entsprechenenden Werte des Spielers
	 * @param player : Spieler der überprüft wird
	 * @return TRUE, falls der Spieler schiessen kann, sonst FALSE
	 */
	private boolean checkPlayerShoot(ApoPlayer player) {
		if (player.isBRunPlayer()) {
			return false;
		}
		if (player.intersects(this.getX(), this.getY(), (this.getWidth() + this.BALL_SHOOT_WIDTH))) {
			player.setBBall(true);
			player.setAngleToBall( (int)(player.getAngleBetweenTwoPoints((int)this.getX(), (int)this.getY())) );
			int minAngle = player.getLineOfSight() - ApoSoccerConstants.ANGLE_TO_SHOOT;
			if (minAngle < 0) {
				minAngle += 360;
			}
			int maxAngle = player.getLineOfSight() + ApoSoccerConstants.ANGLE_TO_SHOOT;
			if (maxAngle > 360) {
				maxAngle -= 360;
			}
			//System.out.println("playerLos: "+player.getLineOfSight()+" minAngle: "+minAngle+" maxAngle: "+maxAngle+" angle: "+player.getAngleToBall()+" name: "+player.getName());
			if (maxAngle > minAngle) {
				if ((player.getAngleToBall() < maxAngle) && (player.getAngleToBall() > minAngle)) {
					if (!player.isBCanShoot()) {
						player.setBCanShoot(true);
					}
					return true;
				} else {
					player.setBCanShoot(false);
					player.setAngleToBall( -1 );
				}
			} else {
				if ((player.getAngleToBall() < maxAngle) || (player.getAngleToBall() > minAngle)) {
					if (!player.isBCanShoot()) {
						player.setBCanShoot(true);
					}
					return true;
				} else {
					player.setBCanShoot(false);
					player.setAngleToBall( -1 );
				}
			}
		} else {
			player.setBBall(false);
			player.setBCanShoot(false);
			player.setAngleToBall( -1 );
		}
		return false;
	}
	
	/**
	 * überprüft ob die Spieler mit dem Ball kollidieren und ob sie schießen dürfen
	 * @param game : Das Spielobjekt, damit man an die SPieler kommt
	 * @return immer TRUE
	 */
	private boolean thinkPlayerCollision(int delta, ApoSoccerGame game) {
		for (int i = 0; i < game.getHomeTeam().getPlayers().size(); i++) {
			this.checkPlayerCollision(game.getHomeTeam().getPlayer(i), game);
		}
		for (int i = 0; i < game.getVisitorTeam().getPlayers().size(); i++) {
			this.checkPlayerCollision(game.getVisitorTeam().getPlayer(i), game);
		}
		
		for (int i = 0; i < game.getHomeTeam().getPlayers().size(); i++) {
			this.checkPlayerShoot(game.getHomeTeam().getPlayer(i));
		}
		for (int i = 0; i < game.getVisitorTeam().getPlayers().size(); i++) {
			this.checkPlayerShoot(game.getVisitorTeam().getPlayer(i));
		}
		
		return true;
	}
	
	/**
	 * überprüft, ob der Ball mit einem Tor kollidiert ;)
	 * @param area : zu überprüfendes Rechteck (das das Tor darstellt)
	 * @param bLeft : boolean Variable, die angibt in welches Tor er geht
	 * @param game : das Spielobjekt
	 * @return immer TRUE
	 */
	private boolean thinkGoalArea(Rectangle2D.Float area, boolean bLeft, ApoSoccerGame game) {
		if ( (this.getX() - this.getWidth() < area.x) && (!bLeft) ) {
			this.setX(area.x + this.getWidth());
			this.setLineOfSight(180 - this.getLineOfSight());
			this.setSpeed(this.getSpeed() * ApoSoccerConstants.BALL_HIT_WALL);
		// Ball kommt oben gegen die Wand
		} else if (this.getY() - this.getHeight() < area.y) {
			this.setY(area.y + this.getHeight());
			this.setLineOfSight(360 - this.getLineOfSight());
			this.setSpeed(this.getSpeed() * ApoSoccerConstants.BALL_HIT_WALL);
		// Ball kommt rechts gegen die Wand bzw Tor
		} else if ((this.getX() + this.getWidth() > area.x + area.width) && (bLeft)) {
			this.setX(area.x + area.width - this.getWidth() - 1);
			this.setLineOfSight(180 - this.getLineOfSight());
			this.setSpeed(this.getSpeed() * ApoSoccerConstants.BALL_HIT_WALL);
		// Ball kommt unten gegen die Wand
		} else if (this.getY() + this.getHeight() > area.y + area.height) {
			this.setY(area.y + area.height - this.getHeight() - 1);
			this.setLineOfSight(360 - this.getLineOfSight());
			this.setSpeed(this.getSpeed() * ApoSoccerConstants.BALL_HIT_WALL);
		}
		// Tor gefallen, wenn der Ball VOLLSTÄNDIG im TOr ist ;)
		if (area.contains(this.getRec())) {
			game.goal();
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
	private boolean thinkWalkableArea(ApoSoccerGame game) {
		if (!this.isInWalkableArea()) {
			//System.out.println("Check x = "+this.getX()+" y = "+this.getY()+" los = "+this.getLineOfSight());
			// Ball kommt links gegen die Wand bzw Tor
			if (this.getX() - this.getWidth() < this.getWalkableArea().x) {
				if (!new Ellipse2D.Double(this.getX() - this.getWidth(), this.getY() - this.getHeight(), this.getWidth() * 2, this.getHeight() * 2).intersects(ApoSoccerConstants.LEFT_GOAL_REC)) {
					this.setX(this.getWalkableArea().x + this.getWidth());
					this.setLineOfSight(180 - this.getLineOfSight());
				} else {
					return this.thinkGoalArea(ApoSoccerConstants.LEFT_GOAL_REC, true, game);
				}
			// Ball kommt oben gegen die Wand
			} else if (this.getY() - this.getHeight() < this.getWalkableArea().y) {
				this.setY(this.getWalkableArea().y + this.getHeight());
				this.setLineOfSight(360 - this.getLineOfSight());
			// Ball kommt rechts gegen die Wand bzw Tor
			} else if (this.getX() + this.getWidth() > this.getWalkableArea().x + this.getWalkableArea().width) {
				if (!new Ellipse2D.Double(this.getX() - this.getWidth(), this.getY() - this.getHeight(), this.getWidth() * 2, this.getHeight() * 2).intersects(ApoSoccerConstants.RIGHT_GOAL_REC)) {
					this.setX(this.getWalkableArea().x + this.getWalkableArea().width - this.getWidth() - 1);
					this.setLineOfSight(180 - this.getLineOfSight());
				} else {
					return this.thinkGoalArea(ApoSoccerConstants.RIGHT_GOAL_REC, false, game);
				}
			// Ball kommt unten gegen die Wand
			} else if (this.getY() + this.getHeight() > this.getWalkableArea().y + this.getWalkableArea().height) {
				this.setY(this.getWalkableArea().y + this.getWalkableArea().height - this.getHeight() - 1);
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
				this.setSpeed(this.getSpeed() * (1 - ((1 - ApoSoccerConstants.BALL_OVER_GRASS) / 20.0f * delta)));
			} else {
				this.setSpeed(0);
			}
		}
		return true;
	}
	
	/**
	 * Methode um zu überprüfen, ob der Ball fest gefahren ist (z.b. in einer Ecke eingeklemmt ist)
	 * falls dem so ist wird nach einer bestimmten Zeit, der Ball zum Mittelpunkt gepackt
	 * damit es weitergehen kann =)
	 * @param delta : Zeit, die seit dem letzten Aufruf vergangen ist
	 * @param game : das SPielobjekt
	 * @return TRUE, alles ok, FALSE Ball fest und SPieler in der Nähe
	 */
	private boolean thinkBallPoint(int delta, ApoSoccerGame game) {
		if (this.ballPoint.size() < ApoSoccerConstants.BALL_STOP_CHECK) {
			this.ballPoint.add(new Point((int)(this.getX()), (int)(this.getY())));
		} else {
			this.ballPoint.remove(0);
			this.ballPoint.add(new Point((int)(this.getX()), (int)(this.getY())));
			Point startPoint = this.ballPoint.get(0);
			for (int i = 1; i < this.ballPoint.size(); i++) {
				if ( ( Math.abs(startPoint.x - this.ballPoint.get(i).x) >= this.getWidth() * 2 ) ||
					 ( Math.abs(startPoint.y - this.ballPoint.get(i).y) >= this.getWidth() * 2 ) ) {
					return true;
				}
			}
			for (int i = 0; i < game.getHomeTeam().getPlayers().size(); i++) {
				if (game.getHomeTeam().getPlayer(i).intersects(this.getX(), this.getY(), this.getWidth() + 5)) {
					return false;
				}
			}
			for (int i = 0; i < game.getVisitorTeam().getPlayers().size(); i++) {
				if (game.getVisitorTeam().getPlayer(i).intersects(this.getX(), this.getY(), this.getWidth() + 5)) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * erstellt die ArrayList mit den Punkten wo der Ball sein wird, wenn der Rest so bleibt wie er ist
	 * @param delta : Zeit
	 * @param game : GameObjekt
	 */
	private void makePlace(int delta, ApoSoccerGame game) {
		this.place = new ArrayList<Point2D.Float>();
		ApoBallClone ball = new ApoBallClone(this);
		Point2D.Float ballInGoal = null;
		for (int i = 0; i < ApoSoccerConstants.BALL_STEP_SAVE; i++) {
			ball.think(delta);
			if (ballInGoal != null) {
				this.place.add(ballInGoal);
			} else {
				Point2D.Float nextPoint = new Point2D.Float(ball.getX(), ball.getY());
				this.place.add(nextPoint);
				if (ApoSoccerConstants.RIGHT_GOAL_REC.contains(nextPoint)) {
					ballInGoal = nextPoint;
				} else if (ApoSoccerConstants.LEFT_GOAL_REC.contains(nextPoint)) {
					ballInGoal = nextPoint;
				}
			}
		}
	}
	
	/**
	 * gibt den Punkt des Balles in x-steps wieder
	 * @param x : stepanzahl
	 * @return gibt den Punkt des Balles in x-steps wieder
	 */
	public Point2D.Float whereIsTheBallInXSteps(int x) {
		if ((this.place == null) || (this.place.size() <= 0)) {
			return null;
		} else if (x < 0) {
			return this.place.get(0);
		} else if (x >= this.place.size()) {
			return this.place.get(this.place.size() - 1);
		} else {
			return this.place.get(x);
		}
	}
	
	public void render( Graphics2D g, int changeX, int changeY ) {
		if ( super.isBVisible() ) {
			if (ApoSoccerConstants.B_DEBUG) {
				g.setColor(Color.white);
				g.fillOval((int)(changeX + super.getX() - super.getWidth() - this.BALL_SHOOT_WIDTH), (int)(changeY + super.getY() - super.getWidth() - this.BALL_SHOOT_WIDTH), (int)((super.getWidth() + this.BALL_SHOOT_WIDTH) * 2), (int)((super.getWidth() + this.BALL_SHOOT_WIDTH) * 2));
				g.setColor(Color.cyan);
				g.fillOval((int)(changeX + super.getX() - super.getWidth()), (int)(changeY + super.getY() - super.getWidth()), (int)(super.getWidth() * 2), (int)(super.getWidth() * 2));
				g.setColor(Color.black);
				g.drawLine((int)(changeX + this.getX() + this.getWidth() * Math.sin(Math.toRadians( 90 + this.getLineOfSight() ))), (int)(changeY + this.getY() + this.getWidth() * Math.cos(Math.toRadians( 270 + this.getLineOfSight() ))), (int)(this.getX() + changeX), (int)(this.getY() + changeY));
			}
			
			if (this.getSpeed() != 0) {
				g.drawImage( super.getIBackground().getSubimage( (int)( this.getFrame() * this.getWidth()*2 ), 0, (int)this.getWidth()*2, (int)this.getHeight()*2 ), (int)(this.getX() + changeX - this.getWidth()), (int)(this.getY() - this.getHeight() + changeY), null );
			} else {
				g.drawImage( super.getIBackground().getSubimage( (int)( 1 * this.getWidth()*2 ), 0, (int)this.getWidth()*2, (int)this.getHeight()*2 ), (int)(this.getX() + changeX - this.getWidth()), (int)(this.getY() - this.getHeight() + changeY), null );				
			}
		}
	}
}
