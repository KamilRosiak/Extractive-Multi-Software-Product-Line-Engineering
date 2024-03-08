package apoSoccer.entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import apoSoccer.ApoSoccerConstants;
import apoSoccer.entityForAI.ApoSoccerDebugLine;
import apoSoccer.game.ApoSoccerGame;
import apoSoccer.game.ApoSoccerSimulate;

/**
 * komplexe Klasse, die den Spieler repräsentiert
 * sie lässt den Spieler bewegen und händelt auch die Ausgabe der Spieler
 * @author Dirk Aporius
 *
 */
public class ApoPlayer extends ApoSoccerEntity {
	
	/** Variable die angibt, wie lange man nach einem Schuss nicht mehr schiessen kann (in Millisekunden) */
	private final int SHOOT_WAIT_MAX = 300;
	
	/** gibt die Weite des eigentlichen Tiles zurück */
	private final int TILE_WIDTH = 22;
	/** gibt die Breite des eigentlichen Tiles zurück */
	private final int TILE_HEIGHT = 26;
	
	/* Variablen, die angeben in welche Richtung der SPieler gerade läuft */
	private final int RIGHT = 0;
	private final int RIGHT_DOWN = 1;
	private final int DOWN = 2;
	private final int DOWN_LEFT = 3;
	private final int LEFT = 4;
	private final int LEFT_UP = 5;
	private final int UP = 6;
	private final int UP_RIGHT = 7;
	
	/** Variable, die den Namen des SPieler enthält */
	private String name;
	/** Variable, die die aktulle Richtung enthält*/
	private int direction;
	/** Variable die die Farbe des Spieler darstellt (der kleine Kreis unter dem Spieler) */
	private Color color;
	
	/** Variable, die den Frischewert der Entity angibt */
	private float freshness;
	
	/** Variable, die den maximalen Frischewert der Entity angibt */
	private float maxFreshness;
	
	/** Variable die angibt, ob der Ball in der Nähe ist */
	private boolean bBall;
	
	/** Variable die angibt, ob der Spieler von Menschen gesteuert wird oder nicht */
	private boolean bPlayerHuman;
	
	/** Variable die angibt, ob der Ball geschossen werden kann */
	private boolean bShoot;
	
	/** Variable die angibt, ob der Spieler sprechen darf */
	private boolean bAllowSpeak;
	
	private boolean bKickOff;
	
	/** Variable die angibt, ob man schiessen darf
	 * ist grundsätzlich an, nur wenn man geschossen hat
	 * muss man sich erst von dem Schuss erholen
	 */
	private boolean bCanShoot;
	
	/** Variable die angibt, ob man schiessen darf
	 * ist grundsätzlich an, nur wenn man geschossen hat
	 * muss man sich erst von dem Schuss erholen
	 */
	private int canShootCounter;
	/** Variable, die die Schußrichtung speichert
	 * Wenn -1, dann wird nicht geschossen */
	private int shootDirection;
	/** Variable, die die Schußstärke speichert
	 * Wenn -1, dann wird nicht geschossen */
	private int shootStrength;
	/** Variable, die den Winkel zum Ball speichert */
	private int angleToBall;
	/** Variable, die die gewünsche Geschwinigkeit speichert */
	private int aimSpeed;
	/** Variable, die die nächste Blickrichtung die angestrebt wird repräsentiert */
	private int nextLineOfSightChange;
	/** das Spielobjekt */
	private ApoSoccerGame game;
	/** Boolean Variable, die angibt ob ein Spieler männlich oder weiblich ist */
	private boolean bFemale;
	/** int index, welcher Spruch gesagt werden soll vom am Anfang übergebenen Sprüchearray */
	private int textIndex;
	/** boolean Variable, die auf true gesetzt wird, wenn der Spieler schiessen kann und das Spiel gestoppt werden soll */
	private boolean bStop;
	/** boolean Variable, die auf true gesetzt wird, wenn im nächsten think aufruf das Spiel gestoppt werden soll */
	private boolean bNextStop;
	/** eine ArrayList mit Linienobjekten */
	private ArrayList<ApoSoccerDebugLine> lines;
	
	/**
	 * boolean-Variable die angibt, ob die Spieler von links nach rechts spielen
	 * oder andersrum
	 * TRUE wenn von Links nach Rechts sonst FALSE
	 */
	private boolean bLeft;
	
	public ApoPlayer(BufferedImage background, float x, float y, float radius, int tiles, long time, Rectangle2D.Float rec, ApoSoccerGame game) {
		super(background, x, y, radius, tiles, time, rec);
		
		this.game = game;
		this.color = Color.red;
		this.setBPlayerHuman(false);
		this.setDirection();
	}

	public ApoPlayer(BufferedImage background, float x, float y, float radius, int tiles, long time, Rectangle2D.Float rec, ApoSoccerGame game, boolean bFemale) {
		super(background, x, y, radius, tiles, time, rec);
		
		this.game = game;
		this.color = Color.red;
		this.setDirection();
	}
	
	public void init() {
		super.init();
		this.setMaxFreshness(ApoSoccerConstants.MAX_FRESHNESS);
		this.setFreshness(ApoSoccerConstants.MAX_FRESHNESS);
		super.setSpeed((int)0);
		this.setLineOfSight(super.getStartLineOfSight());
		this.nextLineOfSightChange = super.getStartLineOfSight();
		this.setShootDirection(-1);
		this.setShootStrength(-1);
		this.setBAllowSpeak(false);
		this.setTextIndex(-1);
		this.canShootCounter = 0;
		this.lines = new ArrayList<ApoSoccerDebugLine>();
	}
	
	/**
	 * gibt zurück ob der Spieler zum Team gehört das den Anstoss hat oder nicht
	 * @return TRUE, Team hat Anstoss, FALSE, Team hat keinen Anstoss
	 */
	public boolean isBKickOff() {
		return this.bKickOff;
	}

	public void setBKickOff(boolean bKickOff) {
		this.bKickOff = bKickOff;
	}

	public void setStartLineOfSight(int startLineOfSight) {
		super.setStartLineOfSight(startLineOfSight);
		this.setLineOfSight(super.getStartLineOfSight());
		this.nextLineOfSightChange = super.getStartLineOfSight();
	}
	
	public void setHalfTime() {
		this.setFreshness(this.getFreshness() + ApoSoccerConstants.HALFTIME_FRESHNESS_ADD);
	}
	
	public ArrayList<ApoSoccerDebugLine> getLines() {
		return this.lines;
	}
	
	/**
	 * fügt eine zu malende Linie der ArrayList hinzu
	 * @param line
	 */
	public void addLine(ApoSoccerDebugLine line) {
		this.lines.add(line);
	}

	/**
	 * boolean Variable, die zurückgibt, ob gerade geschossen werden darf oder nicht
	 * @return TRUE, stop beim Schiessen, sonst FALSE
	 */
	public boolean isBStop() {
		return this.bStop;
	}

	/**
	 * setzt die boolean Variable, ob gerade geschossen werden darf oder nicht, auf den übergebenen Wert
	 * @param bStop : TRUE, 
	 */
	public void setBStop(boolean bStop) {
		this.bStop = bStop;
	}

	/**
	 * gibt zurück, ob ein Spieler per Tastatur gesteuert wird oder nicht
	 * @return TRUE, Spieler wird per Tastatur gesteuert, FALSE Spieler wird per AI gesteuert
	 */
	public boolean isBPlayerHuman() {
		return this.bPlayerHuman;
	}

	public void setBPlayerHuman(boolean bPlayerHuman) {
		this.bPlayerHuman = bPlayerHuman;
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
	
	public boolean addDebugText(String addText) {
		if (this.game == null) {
			return false;
		}
		this.game.addDebugText(this.getName()+"("+this.game.getTimeForGraphic()+" Min): "+addText);
		return true;
	}
	
	/**
	 * gibt zurück, wie lange noch gewartet werdne muss, bevor der Spieler wieder
	 * schiessen darf
	 * @return gibt zurück, wie lange noch gewartet werdne muss, bevor der Spieler wieder
	 * schiessen darf
	 */
	public int getCanShootCounter() {
		return this.canShootCounter;
	}

	/**
	 * gibt zurück, ob der Ball in der Nähe des Spieler ist
	 * @return TRUE, Ball darf geschossen werden, wenn canShooter <= 0, FALSE Ball darf nicht geschossen werden
	 * egal wie canShooter ist
	 */
	public boolean isBCanShoot() {
		return this.bCanShoot;
	}

	public void setBCanShoot(boolean bCanShoot) {
		if ((bCanShoot) && (this.canShootCounter >= 0)) {
			bCanShoot = false;
		}
		this.bCanShoot = bCanShoot;
		/*if ((this.bCanShoot) && (!this.isBStop()) && (this.getCanShootCounter() <= 0) && (ApoSoccerConstants.B_STOP_SHOOT)) {
			this.setBStop(true);
			this.bNextStop = true;
		} else {
			this.setBStop(false);
			this.bNextStop = false;
		}*/
	}
	
	/**
	 * gibt zurück, ob der Spieler sprechen darf
	 * @return TRUE, Spieler darf sprechen, sonst FALSE
	 */
	public boolean isBAllowSpeak() {
		return this.bAllowSpeak;
	}

	/**
	 * setzt den boolean-Wert, ob der Spieler sprechen darf, auf den übergebenen Wert
	 * @param bAllowSpeak : TRUE, Spieler darf sprechen, sonst FALSE
	 */
	public void setBAllowSpeak(boolean bAllowSpeak) {
		this.bAllowSpeak = bAllowSpeak;
	}
	
	/**
	 * setzt dem int-Wert, welchen Spruch der Spieler sagen will, auf den übergebenen
	 * @param textIndex : Der textIndex das gesagt werden soll, aus dem Array was man am Anfang übergeben hat
	 * @return TRUE, textIndex wurde gesetzt, FALSE wenn nicht gesprochen werden darf
	 */
	public boolean speakText(int textIndex) {
		if (this.isBAllowSpeak()) {
			this.textIndex = textIndex;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * setzt dem int-Wert, welchen Spruch der Spieler sagen will, auf den übergebenen
	 * @param textIndex : Der textIndex das gesagt werden soll, aus dem Array was man am Anfang übergeben hat
	 */
	public void setTextIndex(int textIndex) {
		this.textIndex = textIndex;
	}
	
	/**
	 * gibt den int-Wert zurück, welcher gesprochen werden soll aus dem SpeakArray
	 * @return gibt den int-Wert zurück, welcher gesprochen werden soll aus dem SpeakArray
	 */
	public int getTextIndex() {
		return this.textIndex;
	}
	
	/**
	 * gibt zurück, ob der Spieler eine Frau oder ein Mann ist
	 * @return TRUE, Spieler ist eine Frau, sonst FALSE
	 */
	public boolean isBFemale() {
		return this.bFemale;
	}

	/**
	 * setzt den boolean-Wert, ob der Spieler eine Frau oder ein Mann ist, auf den übergebenen Wert
	 * @param bFemale : TRUE, Spieler ist eine Frau, sonst FALSE
	 */
	public void setBFemale(boolean bFemale) {
		this.bFemale = bFemale;
	}

	/**
	 * wird aufgerufen wenn ein Tor gefallen ist und setzt die Spieler wieder auf ihre
	 * Ausgangswerte und ihren Speed auf 0
	 */
	public void goal() {
		super.init();
		super.setSpeed((int)0);
		this.setLineOfSight(super.getStartLineOfSight());
		this.nextLineOfSightChange = super.getStartLineOfSight();
		this.setShootDirection(-1);
		this.setShootStrength(-1);
		this.canShootCounter = -1;
	}
	
	/**
	 * wird aufgerufen wenn der Ball zurückgesetzt wird
	 * und sagt, dass die Spieler nicht schiessen wollen
	 * @return immer TRUE
	 */
	public boolean ballReset() {
		this.setShootDirection(-1);
		this.setShootStrength(-1);
		this.canShootCounter = 1;
		return true;
	}
	
	/**
	 * gibt eine boolean Variable zurück, die angibt, ob der SPieler von Rechts nach Links spielt oder andersrum
	 * @return TRUE wenn von Links nach Rechts, sonst FALSE
	 */
	public boolean isBLeft() {
		return this.bLeft;
	}

	/**
	 * setzt den Wert für die SPielrichtung des Spieler auf den Übergebenen
	 * @param bLeft : neue Spielrichtung
	 */
	public void setBLeft(boolean bLeft) {
		this.bLeft = bLeft;
	}

	/**
	 * gibt die maximale Freshness des Spielers zurück
	 * @return gibt die maximale Freshness des Spielers zurück
	 */
	public float getMaxFreshness() {
		return this.maxFreshness;
	}

	/**
	 * setzt die maximale Freshness des Spielers auf den übergebenen Wert
	 * @param maxFreshness : neue maximale Freshness
	 */
	public void setMaxFreshness(float maxFreshness) {
		this.maxFreshness = maxFreshness;
	}

	/**
	 * gibt die angestrebte Geschwinigkeit zurück
	 * @return gibt die angestrebte Geschwinigkeit zurück
	 */
	public int getAimSpeed() {
		return this.aimSpeed;
	}

	/**
	 * setzt die angestrebt Geschwindigkeit auf den übergebenen Wert
	 * @param aimSpeed : neue angestrebte Geschwindigkeit
	 */
	public void setAimSpeed(int aimSpeed) {
		this.aimSpeed = aimSpeed;
	}

	/**
	 * gibt den Namen des Spielers zurück
	 * @return gibt den Namen des Spielers zurück
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * setzt den Namen des Spielers auf den übergebenen
	 * @param name : neuer Name des SPielers
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Variable die angibt, ob der Ball in der Nähe ist
	 * @return TRUE, wenn der Ball in der Nähe des SPielers ist, sonst FALSE
	 */
	public boolean isBBall() {
		return this.bBall;
	}

	/**
	 * setzt die boolean Variable ob der Spieler in der Nähe ist auf den übergebenen Wert
	 * @param bBall : TRUE, wenn Ball in der Nähe sonst FALSE
	 */
	public void setBBall(boolean bBall) {
		this.bBall = bBall;
	}

	/**
	 * wird vor einem ANstoss jeweils abgefragt und setzt den Spieler an die neue Position, wenn
	 * diese gültig ist
	 * @param x : neuer X-Wert des Spielers
	 * @param y : neuer Y-Wert des Spielers
	 * @param lineOfSight : neue BLickrichtung des Spielers
	 * @return TRUE, wenn neue Position gültig, sonst FALSE
	 */
	public boolean setNewPosition(int x, int y, int lineOfSight) {
		//System.out.println("x = "+x+" y = "+y+" los = "+lineOfSight+" in Player");
		for (int i = 0; i < this.game.getHomeTeam().getPlayers().size(); i++) {
			if (!this.game.getHomeTeam().getPlayer(i).equals(this)) {
				if (this.game.getHomeTeam().getPlayer(i).intersects(x, y, this.getWidth())) {
					return false;
				}
			}
		}
		for (int i = 0; i < this.game.getVisitorTeam().getPlayers().size(); i++) {
			if (!this.game.getVisitorTeam().getPlayer(i).equals(this)) {
				if (this.game.getVisitorTeam().getPlayer(i).intersects(x, y, this.getWidth())) {
					return false;
				}
			}
		}
		this.setStartX(x);
		this.setStartY(y);
		this.setStartLineOfSight(lineOfSight);
		if (!super.isBRunPlayer()) {
			this.setLineOfSight(lineOfSight);
			this.setX(x);
			this.setY(y);
		}
		return true;
	}
	
	/**
	 * gibt zurück, ob der Ball geschossen werden darf oder nicht
	 * @return TRUE, wenn er geschossen werden darf, sonst FALSE
	 */
	public boolean isBShoot() {
		return this.bShoot;
	}

	/**
	 * setzt den boolean Wert, ob der Ball geschossen werden darf, auf den übergebenen
	 * @param bShoot : TRUE wenn er geschossen werden darf, sonst FALSE
	 */
	public void setBShoot(boolean bShoot) {
		this.bShoot = bShoot;
	}

	/**
	 * gibt den Winkel des Spielers zum Ball zurück
	 * @return gibt den Winkel des Spielers zum Ball zurück
	 */
	public int getAngleToBall() {
		return this.angleToBall;
	}

	/**
	 * setzt den Winkel des Spielers zum Ball auf den übergebenen Wert
	 * @param angleToBall : neuer Winkel zum Ball
	 */
	public void setAngleToBall(int angleToBall) {
		this.angleToBall = angleToBall;
	}

	/**
	 * gibt die Richtung in die geschossen werden soll zurück
	 * @return gibt die Richtung in die geschossen werden soll zurück
	 */
	public int getShootDirection() {
		return this.shootDirection;
	}

	/**
	 * setzt die Richtung in die geschossen werden soll, auf den übergebenen Wert
	 * @param shootDirection : neue Schussrichtung
	 */
	public void setShootDirection(int shootDirection) {
		this.shootDirection = shootDirection;
	}

	/**
	 * gibt die Stärke, wie hart der Ball geschossen werden soll zurück
	 * @return gibt die Stärke, wie hart der Ball geschossen werden soll zurück
	 */
	public int getShootStrength() {
		return this.shootStrength;
	}

	/**
	 * setzt die Stärke, wie hart der Ball geschossen werden soll, auf den übergebenen Wert
	 * @param shootStrength : neue Schussstärke
	 */
	public void setShootStrength(int shootStrength) {
		this.shootStrength = shootStrength;
	}
	
	public void setSpeed(float speed) {
		if (speed > ApoSoccerConstants.MAX_SPEED_SHOOT) {
			speed = ApoSoccerConstants.MAX_SPEED_SHOOT;
		} else if (speed < ApoSoccerConstants.MIN_SPEED_SHOOT) {
			speed = ApoSoccerConstants.MIN_SPEED_SHOOT;
		}
		super.setSpeed(speed);
	}

	/**
	 * setzt ShootStrength und ShootDirection auf die übergebenen Werte, wenn
	 * der Ball geschossen werden darf, sonst gibt er FALSE zurück
	 * Dabei ist zu beachten, dass die Schussstärke minimal ApoSoccerConstants.MIN_VEC_SHOOT beträgt
	 * und maximal der aktuellen Freshness der Spieler
	 * Je erschöpfter ein Spieler ist, desto langsamer kann er also nur schiessen
	 * Die neue Schussrichtung muss zwischen 0 und 360° liegen
	 * @param shootDirection : neue Schussrichtung
	 * @param shootStrength : neue Schussstärke
	 * @return TRUE, wenn Angaben richtig, sonst FALSE
	 */
	public boolean setShoot(int shootDirection, int shootStrength) {
		this.bNextStop = false;
		if ((!this.isBBall()) || (this.angleToBall < 0) || (this.canShootCounter > 0) || (shootStrength < 0)) {
			return false;
		}
		if (shootStrength < ApoSoccerConstants.MIN_SPEED_SHOOT) {
			shootStrength = ApoSoccerConstants.MIN_SPEED_SHOOT;
		} else if (shootStrength > (this.getFreshness() * ((float)ApoSoccerConstants.MAX_SPEED_SHOOT/(float)ApoSoccerConstants.MAX_SPEED_PLAYER))) {
			shootStrength = (int)(this.getFreshness() * ((float)ApoSoccerConstants.MAX_SPEED_SHOOT/(float)ApoSoccerConstants.MAX_SPEED_PLAYER));
		}
		while (shootDirection > 360) {
			shootDirection -= 360;
		}
		while (shootDirection < 0) {
			shootDirection += 360;
		}
		int minAngle = this.angleToBall - ApoSoccerConstants.ANGLE_TO_SHOOT;
		if (minAngle < 0) {
			minAngle += 360;
		}
		int maxAngle = this.angleToBall + ApoSoccerConstants.ANGLE_TO_SHOOT;
		if (maxAngle > 360) {
			maxAngle -= 360;
		}
		//System.out.println("min: "+minAngle+" dir: "+shootDirection+" max: "+maxAngle+" angle: "+this.angleToBall+" name: "+this.getName());
		if (maxAngle > minAngle) {
			if ((shootDirection <= maxAngle) && (shootDirection >= minAngle)) {
				this.shootDirection = shootDirection;
				this.shootStrength = shootStrength;
				this.setBStop(true);
				this.bNextStop = true;
			} else {
				this.shootDirection = -1;
				this.shootStrength = -1;
				return false;
			}
		} else {
			if ((shootDirection <= maxAngle) || (shootDirection >= minAngle)) {
				this.shootDirection = shootDirection;
				this.shootStrength = shootStrength;
				this.setBStop(true);
				this.bNextStop = true;
			} else {
				this.shootDirection = -1;
				this.shootStrength = -1;
				return false;
			}
		}
		//System.out.println("strength: "+this.shootStrength+" dir: "+this.shootDirection);

		/*if (this.getName().indexOf("The one") != -1) {
			System.out.println("Schuss mit "+this.shootDirection+" und "+this.shootStrength);
		}*/
		return true;
	}
	
	/**
	 * gibt die Blickrichtung, die angestrebt wird, zurück
	 * @return gibt die Blickrichtung, die angestrebt wird, zurück
	 */
	public int getNextLineOfSightChange() {
		return this.nextLineOfSightChange;
	}

	/**
	 * setzt die nächste BLickrichtung die angestrebt wird, auf den übergebenen Wert
	 * @param nextLineOfSightChange : nächste Blickrichtung
	 */
	public void setNextLineOfSightChange(int nextLineOfSightChange) {
		this.nextLineOfSightChange = nextLineOfSightChange;
	}

	public void setLineOfSight(int lineOfSight) {
		super.setLineOfSight(lineOfSight);
		this.setDirection();
	}
	
	/**
	 * überprüft die aktulle BLickrichtung und setzt die Variable direction dann auf den entsprechenden Wert
	 * Ist nur wichtig für die Ausgabe
	 */
	private void setDirection() {
		if ((super.getLineOfSight() > 67) && (super.getLineOfSight() <= 112)) {
			this.direction = this.DOWN;
		} else if ((super.getLineOfSight() > 112) && (super.getLineOfSight() <= 157)) {
			this.direction = this.DOWN_LEFT;
		} else if ((super.getLineOfSight() > 157) && (super.getLineOfSight() <= 202)) {
			this.direction = this.LEFT;
		} else if ((super.getLineOfSight() > 202) && (super.getLineOfSight() <= 247)) {
			this.direction = this.LEFT_UP;
		} else if ((super.getLineOfSight() > 247) && (super.getLineOfSight() <= 292)) {
			this.direction = this.UP;
		} else if ((super.getLineOfSight() > 292) && (super.getLineOfSight() <= 337)) {
			this.direction = this.UP_RIGHT;
		} else if ( ((super.getLineOfSight() > 337) && (super.getLineOfSight() <= 360)) ||
					((super.getLineOfSight() >= 0) && (super.getLineOfSight() <= 22))) {
			this.direction = this.RIGHT;
		} else {
			this.direction = this.RIGHT_DOWN;
		}
	}
	
	/**
	 * gibt die Farbe des kleinen Kreises unter dem Spieler wieder
	 * @return gibt die Farbe des kleinen Kreises unter dem Spieler wieder
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * setzt die Farbe des kleinen Kreises unter dem Spieler auf den übergebenen Wert
	 * @param color : neue Fabre
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * gibt die aktulle Freshness des SPieler wieder
	 * @return gibt die aktulle Freshness des SPieler wieder
	 */
	public float getFreshness() {
		return this.freshness;
	}

	/**
	 * setzt die Freshness des SPieler auf den übergebenen Wert
	 * @param freshness : neue Freshness des SPielers
	 */
	public void setFreshness(float freshness) {
		if (freshness < ApoSoccerConstants.MIN_FRESHNESS) {
			freshness = ApoSoccerConstants.MIN_FRESHNESS;
		} else if (freshness > this.getMaxFreshness()) {
			freshness = this.getMaxFreshness();
		}
		this.freshness = freshness;
	}
	
	/**
	 * setzt den neuen aimSpeed auf den übergebenen Wert nach dieser überprüft wurde
	 * ob er auch zulässig ist
	 * @param aimSpeed : neuer AimSpeed
	 */
	public void setSpeed(int aimSpeed) {
		if (aimSpeed > this.getFreshness()) {
			aimSpeed = (int)this.getFreshness();
		} else if (aimSpeed <= ApoSoccerConstants.MIN_SPEED_PLAYER) {
			aimSpeed = (int)ApoSoccerConstants.MIN_SPEED_PLAYER;
		}
		this.setAimSpeed(aimSpeed);
	}
	
	/**
	 * think Methode, die jeden Schritt aufgerufen wird und
	 * die Spieler verlasst sich zu bewegen und die Frischewerte zu berechnen
	 * @param delta : Zeit die vergangen ist seit dem letzten Aufruf
	 * @param game : Das Spielobjekt
	 */
	public void think(int delta, ApoSoccerGame game) {
		super.think(delta);
		this.setBStop(false);
		if (game.isBRunPlayer()) {
			this.thinkRunPlayer(delta);
			return;
		}
		if (this.bNextStop) {
			this.bNextStop = false;
			if ((ApoSoccerConstants.B_STOP_SHOOT) && (!ApoSoccerSimulate.B_SIMULATE)) {
				game.setBCurStop(true);
			}
		}
		if ((this.getShootStrength() != -1) && (this.canShootCounter < 0)) {
			this.thinkShoot(delta, game);
			this.canShootCounter = this.SHOOT_WAIT_MAX;
		} else if (this.canShootCounter >= 0) {
			this.canShootCounter -= delta;
		}
		if (this.nextLineOfSightChange != this.getLineOfSight()) {
			this.setLineOfSight(this.nextLineOfSightChange);
		}
		super.setTimeElapsed(super.getTimeElapsed() + delta);
		this.thinkSpeed(delta);
		this.thinkFreshness(delta);
		this.thinkVec(delta, game);
	}
	
	/**
	 * wird aufgerufen, wenn der Spieler wieder auf seine Ausgangsposition laufen soll
	 * @param delta : Zeit die seit dem letzten AUfruf vergangen ist
	 * @return immer TRUE
	 */
	private boolean thinkRunPlayer(int delta) {
		super.setSpeed(0);
		this.setShootDirection(-1);
		this.setShootStrength(-1);
		if ((Math.abs(this.getX() - this.getStartX()) > 8) ||
			(Math.abs(this.getY() - this.getStartY()) > 8) ) {
			double alpha = this.getAngleBetweenTwoPoints(this.getStartX(), this.getStartY());
			float radius = 0.125f * delta;
			this.setLineOfSight((int)alpha);
			this.setX( this.getX() + radius * (float)Math.cos(Math.toRadians(alpha)) );
			this.setY( this.getY() + radius * (float)Math.sin(Math.toRadians(alpha)) );
		} else {
			if (super.isBRunPlayer()) {
				if (((int)this.getX() != (int)this.getStartX()) ||
					((int)this.getY() != (int)this.getStartY()) ) {
					this.setX(this.getStartX());
					this.setY(this.getStartY());
				}
				super.setBRunPlayer(false);
				this.setLineOfSight(this.getStartLineOfSight());
				//System.out.println("los = "+this.getLineOfSight());
			}
		}
		return true;
	}
	
	/**
	 * denkt darüber nach, ob geschossen werden soll
	 * gibt dem Ball dann den gewünschten Speed und die gewünschte Richtung 
	 * (mit leichter Normalverteilung ... das heisst je stärker geschossen wird
	 * desto größer die Chance, dass der Ball leicht verzieht) und zieht ein bisschen
	 * von der Freshness ab 
	 * @param delta
	 * @param game
	 * @return immer TRUE
	 */
	private boolean thinkShoot(int delta, ApoSoccerGame game) {
		ApoBall ball = game.getBall();
		this.addLine(new ApoSoccerDebugLine((int)ball.getX(), (int)ball.getY(), (int)ball.getX() + (int)(Math.cos(Math.toRadians(this.shootDirection)) * 100), (int)ball.getY() + (int)(Math.sin(Math.toRadians(this.shootDirection)) * 100), 500, Color.white, 1));

		int random = 0;
		random = (int)(Math.random() * (this.shootStrength));
		float percent = (float)(random) / (float)(ApoSoccerConstants.MAX_SPEED_SHOOT);
		random = (int)((percent)*(float)(ApoSoccerConstants.SHOOT_RANDOM));
		if (Math.random() * 100 < 50) {
			random = -random;
		}
		//System.out.println("random: "+random+" percent: "+percent);

		int direction = this.shootDirection + random;
		if (direction >= 360) {
			direction -= 360;
		} else if (direction < 0) {
			direction += 360;
		}
		//System.out.println("strength = "+this.shootStrength+" random = "+random+" losBall = "+direction+" losPlayer = "+this.getLineOfSight()+" name = "+this.getName());

		/*physikalisch ganz tolles schießen
		Point2D.Double shootVector=new Point2D.Double(Math.cos(direction*Math.PI/180)*this.shootStrength,Math.sin(direction*Math.PI/180)*this.shootStrength);
		System.out.println("Shoot: "+ direction +"°," + shootStrength +";Player: "+ this.getLineOfSight() +"°," + this.getSpeed());
		shootVector.setLocation(shootVector.getX()+Math.cos(this.getLineOfSight()*Math.PI/180)*this.getSpeed(),shootVector.getY()+Math.sin(this.getLineOfSight()*Math.PI/180)*this.getSpeed());
		System.out.println("Shoot Vector: " + shootVector);
		this.setBallShootInformations(game.getBall(), game);
		game.getBall().setSpeed(Math.round(shootVector.distance(0,0)));
		int shootDirection=0;
		if(shootVector.getX()!=0 ||shootVector.getY()!=0){
			shootDirection=(int)Math.round(Math.atan2(shootVector.getY()/shootVector.distance(0,0), shootVector.getX()/shootVector.distance(0,0))/Math.PI*180);
			if(shootDirection<0)
				shootDirection+=360;
		}
		game.getBall().setLineOfSight(shootDirection);
		*/
		
		this.setBallShootInformations(game.getBall(), game);
		//in Laufrichtung stärker schießen
		//System.out.println("ballSpeed vorher: "+game.getBall().getSpeed());
		//System.out.println(""+(this.shootStrength*1+" und cos "+(int)(Math.cos((this.getLineOfSight()-direction)/180*Math.PI))));
		game.getBall().setSpeed(this.shootStrength*1);//2+(int)(Math.cos((this.getLineOfSight()-direction)/180*Math.PI)*this.getSpeed()*3/4));
		//System.out.println("ballSpeed nachher: "+game.getBall().getSpeed());
		//System.out.println("Arc Dif: " +(this.getLineOfSight()-direction));
		game.getBall().setLineOfSight(direction);
		
		while (game.getBall().intersects(super.getX(), super.getY(), super.getWidth() + 2)) {
			double alpha = game.getBall().getLineOfSight();
			float radius = 1.0f;
			game.getBall().setX( game.getBall().getX() + radius * (float)Math.cos(Math.toRadians(alpha)) );
			game.getBall().setY( game.getBall().getY() + radius * (float)Math.sin(Math.toRadians(alpha)) );
		}
		
		int angle = this.getLineOfSight()-direction;
		if (Math.abs(angle) > 100) {
			
		}
		//System.out.println("angel: "+angle+" neu: "+Math.max(0, ((this.shootStrength - (int)(Math.cos((this.getLineOfSight()-direction)/180.*Math.PI)*this.getSpeed()*3/4)) * ((float)ApoSoccerConstants.MAX_FRESHNESS_ALLOWANCE / ((float)ApoSoccerConstants.MAX_SPEED_SHOOT/(float)ApoSoccerConstants.MAX_SPEED_PLAYER)) / ApoSoccerConstants.MAX_FRESHNESS)));
		//System.out.println("alt: "+Math.max(0, ((this.shootStrength) * ((float)ApoSoccerConstants.MAX_FRESHNESS_ALLOWANCE / ((float)ApoSoccerConstants.MAX_SPEED_SHOOT/(float)ApoSoccerConstants.MAX_SPEED_PLAYER)) / ApoSoccerConstants.MAX_FRESHNESS)));
		this.setFreshness(this.getFreshness() - Math.max(0, ((this.shootStrength - (int)(Math.cos((this.getLineOfSight()-direction)/180.*Math.PI)*this.getSpeed()*3/4)) * ((float)ApoSoccerConstants.MAX_FRESHNESS_ALLOWANCE / ((float)ApoSoccerConstants.MAX_SPEED_SHOOT/(float)ApoSoccerConstants.MAX_SPEED_PLAYER)) / ApoSoccerConstants.MAX_FRESHNESS)));
		if (this.getFreshness() < ApoSoccerConstants.MIN_FRESHNESS) {
			this.setFreshness(ApoSoccerConstants.MIN_FRESHNESS);
		}
		this.addLine(new ApoSoccerDebugLine((int)ball.getX(), (int)ball.getY(), (int)ball.getX() + (int)(Math.cos(Math.toRadians(direction)) * 100), (int)ball.getY() + (int)(Math.sin(Math.toRadians(direction)) * 100), 500, Color.red, 1));

		//System.out.println("dir = "+game.getBall().getLineOfSight()+" random: "+random+" shootDir: "+this.shootDirection+" name: "+this.getName());
		this.shootDirection = -1;
		this.shootStrength = -1;
		return true;
	}
	
	/**
	 * speichert die Informationen des Spielers und seines Teams beim Ball ab
	 * @param ball : Ball
	 * @param game : Spielobjekt
	 * @return immer TRUE
	 */
	private boolean setBallShootInformations(ApoBall ball, ApoSoccerGame game) {
		ball.addLastShootPlayerName(this.getName());
		if (this.getColor().equals(Color.red)) {
			ball.addLastShootTeamName(game.getHomeTeam().getTeamName());
		} else {
			ball.addLastShootTeamName(game.getVisitorTeam().getTeamName());
		}
		this.setBallContactInformations(ball, game);
		return true;
	}
	
	/**
	 * speichert die Informationen des Spielers und seines Teams beim Ball ab
	 * @param ball : Ball
	 * @param game : Spielobjekt
	 * @return immer TRUE
	 */
	public boolean setBallContactInformations(ApoBall ball, ApoSoccerGame game) {
		ball.addLastContactPlayerName(this.getName());
		if (this.getColor().equals(Color.red)) {
			ball.addLastContactTeamName(game.getHomeTeam().getTeamName());
		} else {
			ball.addLastContactTeamName(game.getVisitorTeam().getTeamName());
		}
		//System.out.println("lastName = "+ball.getLastShootPlayerName()+" team = "+ball.getLastShootTeamName());
		return true;
	}
	
	/**
	 * gibt dem Spieler eine neue Geschwindigkeit
	 * @param delta
	 * @return immer TRUE
	 */
	private boolean thinkSpeed(int delta) {
		float newSpeed = this.getSpeed();
		if (this.getSpeed() <= this.getAimSpeed()) {
			if (this instanceof ApoGoalKeeper) {
				newSpeed = newSpeed + ApoSoccerConstants.PLAYER_GOALKEEPER_ACCELERATION * delta;
			} else {
				newSpeed = newSpeed + ApoSoccerConstants.PLAYER_ACCELERATION * delta;
			}
			if (newSpeed > this.getAimSpeed()) {
				newSpeed = this.getAimSpeed();
			}
		} else if (this.getSpeed() > this.getAimSpeed()) {
			newSpeed = newSpeed + ApoSoccerConstants.PLAYER_BREAK * delta;
			if (newSpeed < this.getAimSpeed()) {
				newSpeed = this.getAimSpeed();
			}
		} 
		super.setSpeed(newSpeed);
		return true;
	}
	
	/**
	 * berechnet die Freshness des Spielers neu
	 * @param delta
	 * @return immer TRUE
	 */
	private boolean thinkFreshness(int delta) {
		if (this.getMaxFreshness() == ApoSoccerConstants.MIN_FRESHNESS) {
			return true;
		}
		float newFreshness = this.getFreshness();
		if (((int)this.getSpeed() != ApoSoccerConstants.MIN_SPEED_PLAYER) && (this.getFreshness() != ApoSoccerConstants.MIN_FRESHNESS)){
			newFreshness = newFreshness - (((float)Math.exp((this.getSpeed())/100) - 1) / 65.0f) * delta / 20.0f;
		}
		if ((int)this.getSpeed() == ApoSoccerConstants.MIN_SPEED_PLAYER) {
			newFreshness += ApoSoccerConstants.INCREASE_FRESHNESS_STAND * delta / 20.0f;
		} else {
			newFreshness += ApoSoccerConstants.INCREASE_FRESHNESS * delta / 20.0f;
		}
		if (newFreshness >= this.getMaxFreshness()) {
			newFreshness = this.getMaxFreshness();
		}
		if ((newFreshness < this.getMaxFreshness()) && ((int)this.getSpeed() != ApoSoccerConstants.MIN_SPEED_PLAYER)) {
			this.setMaxFreshness(this.getMaxFreshness() - ApoSoccerConstants.DECREASE_MAX_FRESHNESS * delta / 20.0f);
			if (this.getMaxFreshness() < ApoSoccerConstants.MIN_FRESHNESS) {
				this.setMaxFreshness(ApoSoccerConstants.MIN_FRESHNESS);
			}
		}
		this.setFreshness(newFreshness);
		return true;
	}
	
	/**
	 * versucht dem Spieler neue x und y Werte zu geben anhand seiner Geschwindigkeit und Blickrichtung
	 * und überprüft hierbei, ob er mit einem anderen Spieler kollidiert
	 * @param delta : Zeit die seit dem letzten Aufruf vergangen ist
	 * @param game : das Spielobjekt
	 * @return immer TRUE
	 */
	public boolean thinkVec(int delta, ApoSoccerGame game) {
		if (this.getSpeed() != 0) {
			float radius = ((ApoSoccerConstants.MAX_VEC_PLAYER * (float)this.getSpeed() / 100.0f) / 20.0f) * delta;
			double alpha = this.getLineOfSight();
			if (radius > ApoSoccerConstants.MIN_RADIUS_SPEED) {
				float newX = this.getX() + radius * (float)Math.cos(Math.toRadians(alpha));
				float newY = this.getY() + radius * (float)Math.sin(Math.toRadians(alpha));
				if (this.getWalkableArea().contains(newX - this.getWidth(), newY - this.getWidth(), this.getWidth() * 2, this.getHeight())) {
					this.setX(newX);
					this.setY(newY);
					for (int i = 0; i < game.getHomeTeam().getPlayers().size(); i++) {
						if ((!game.getHomeTeam().getPlayer(i).equals(this)) &&
							(game.getHomeTeam().getPlayer(i).intersects(this)) ) {
							super.setEntitiyBack(this, game.getHomeTeam().getPlayer(i));
							this.setSpeed(this.getSpeed() * ApoSoccerConstants.PLAYER_HIT_PLAYER);
						}
					}
					for (int i = 0; i < game.getVisitorTeam().getPlayers().size(); i++) {
						if ((!game.getVisitorTeam().getPlayer(i).equals(this)) &&
							(game.getVisitorTeam().getPlayer(i).intersects(this)) ) {
							super.setEntitiyBack(this, game.getVisitorTeam().getPlayer(i));
							this.setSpeed(this.getSpeed() * ApoSoccerConstants.PLAYER_HIT_PLAYER);
						}
					}
				} else {
					this.thinkArea(this.getWalkableArea());
				}
			} else {
				this.setSpeed(0);
			}
		}
		return true;
	}
	
	/**
	 * überprüft, ob der Spieler komplett in dem übergebenen Rechteck liegt
	 * @param area : zu überprüfendes Rechtck
	 * @return TRUE, wenn drin, sonst FALSE
	 */
	private boolean thinkArea(Rectangle2D.Float area) {
		// Spieler kommt links gegen die Wand
		if ( (this.getX() - this.getWidth() < area.x) ) {
			this.setX(area.x + this.getWidth());
			this.setSpeed(this.getSpeed() * ApoSoccerConstants.BALL_HIT_WALL);
			return true;
		// Spieler kommt oben gegen die Wand
		}
		if (this.getY() - this.getHeight() < area.y) {
			this.setY(area.y + this.getHeight());
			this.setSpeed(this.getSpeed() * ApoSoccerConstants.BALL_HIT_WALL);
			return true;
		// Spieler kommt rechts gegen die Wand
		}
		if ((this.getX() + this.getWidth() > area.x + area.width)) {
			this.setX(area.x + area.width - this.getWidth() - 1);
			this.setSpeed(this.getSpeed() * ApoSoccerConstants.BALL_HIT_WALL);
			return true;
		// Spieler kommt unten gegen die Wand
		}
		if (this.getY() + this.getHeight() > area.y + area.height) {
			this.setY(area.y + area.height - this.getHeight() - 1);
			this.setSpeed(this.getSpeed() * ApoSoccerConstants.BALL_HIT_WALL);
			return true;
		}
		return false;
	}
	
	/**
	 * malt den Spieler falls er sichtbar ist hin ;)
	 */
	public void render(Graphics2D g, int changeX, int changeY) {
		// Spieler wird nur angezeigt, wenn er auch angezeigt werden kann
		if ( ( super.getX() + changeX + super.getWidth()*2 > 0 ) &&
			 ( super.getX() + changeX - super.getWidth()*2 < ApoSoccerConstants.GAME_FIELD_WIDTH ) &&
			 ( super.getY() + changeY + super.getWidth()*2 > 0 ) &&
			 ( super.getY() + changeY - super.getWidth()*2 < ApoSoccerConstants.GAME_FIELD_HEIGHT ) ) {
			if (super.getIBackground() == null) {
				g.setColor(color);
				g.fillOval((int)(changeX + super.getX() - super.getWidth()), (int)(changeY + super.getY() - super.getWidth()), (int)(super.getWidth() * 2), (int)(super.getWidth() * 2));
				g.setColor(Color.black);
				g.drawLine((int)(changeX + this.getX() + this.getWidth() * Math.sin(Math.toRadians( 90 + this.getLineOfSight() ))), (int)(changeY + this.getY() + this.getWidth() * Math.cos(Math.toRadians( 270 + this.getLineOfSight() ))), (int)(this.getX() + changeX), (int)(this.getY() + changeY));
			} else {
				if ( super.isBVisible() ) {
					if (ApoSoccerConstants.B_DEBUG) {
						g.setColor(color);
						g.fillOval((int)(changeX + super.getX() - super.getWidth()), (int)(changeY + super.getY() - super.getWidth()), (int)(super.getWidth() * 2), (int)(super.getWidth() * 2));
						g.setColor(Color.black);
						g.drawLine((int)(changeX + this.getX() + this.getWidth() * Math.sin(Math.toRadians( 90 + this.getLineOfSight() ))), (int)(changeY + this.getY() + this.getWidth() * Math.cos(Math.toRadians( 270 + this.getLineOfSight() ))), (int)(this.getX() + changeX), (int)(this.getY() + changeY));
					}
					if (this.isBPlayerHuman()) {
						Stroke s = g.getStroke();
						g.setStroke(new BasicStroke(2));
						g.setColor(Color.yellow);
						g.drawOval((int)(changeX + super.getX() - super.getWidth() - 2), (int)(changeY + super.getY() - super.getWidth() - 2), (int)(super.getWidth() * 2 + 4), (int)(super.getWidth() * 2 + 4));
						g.setStroke(s);
					}
					if ((this.getSpeed() != 0) || (super.isBRunPlayer())) {
						g.drawImage( super.getIBackground().getSubimage( (int)( this.getFrame() * this.TILE_WIDTH ), this.direction * this.TILE_HEIGHT, (int)this.TILE_WIDTH, (int)(this.TILE_HEIGHT) ), (int)(this.getX() + changeX - this.TILE_WIDTH/2), (int)(this.getY() + changeY - this.getHeight() + 3 - (this.TILE_HEIGHT - this.getHeight())), null );
					} else {
						g.drawImage( super.getIBackground().getSubimage( (int)( 1 * this.TILE_WIDTH ), this.direction * this.TILE_HEIGHT, (int)this.TILE_WIDTH, (int)(this.TILE_HEIGHT) ), (int)(this.getX() + changeX - this.TILE_WIDTH/2), (int)(this.getY() + changeY - this.getHeight() + 3 - (this.TILE_HEIGHT - this.getHeight())), null );						
					}
				}
			}
		}
	}

}
