package apoSoccer.game;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import org.apogames.ApoConstants;
import org.apogames.help.ApoFileFilter;
import org.apogames.help.ApoFloatPoint;

import apoSoccer.ApoSoccerConstants;
import apoSoccer.entity.ApoBall;
import apoSoccer.entity.ApoSoccerEntity;
import apoSoccer.entity.ApoTeam;

/**
 * Klasse, die das Replay repräsentiert
 * @author Dirk Aporius
 *
 */
public class ApoSoccerReplay {

	/** Variable, die angibt, ob das Replay gerade abgespielt wird */
	private boolean bReplayPlay;
	/** Variable, die angibt, ob das Replay gerade abgespielt wird */
	private boolean bReplaySave;
	
	/** A FileChooser */
	private JFileChooser fileChooser;
	/** A Class file filter */
	private ApoFileFilter apoFileFilter;
	/** Variable die angibt, welcher Schritt gerade angezeigt werden soll */
	private int curStep;
	
	private ArrayList<ApoFloatPoint> homeDefenderOne;
	private ArrayList<ApoFloatPoint> homeDefenderTwo;
	private ArrayList<ApoFloatPoint> homeGoalkeeper;
	private ArrayList<ApoFloatPoint> homeForward;
	
	private ArrayList<ApoFloatPoint> visitorDefenderOne;
	private ArrayList<ApoFloatPoint> visitorDefenderTwo;
	private ArrayList<ApoFloatPoint> visitorGoalkeeper;
	private ArrayList<ApoFloatPoint> visitorForward;
	
	private ArrayList<ApoFloatPoint> ball;
	
	/** Objekt, was sich um das SPeichern und Laden von externen Dateien kümmert */
	private ApoSoccerReplayIO io;
	
	private String homeTeamName;
	private String visitorTeamName;
	
	private boolean bGoal;
	private boolean bHomeGoal;
	
	public ApoSoccerReplay() {
		if (ApoConstants.B_APPLET) {
			
		} else {
			this.fileChooser = new JFileChooser();
			this.apoFileFilter = new ApoFileFilter("rep");
			this.fileChooser.setCurrentDirectory( new File( System.getProperty("user.dir") + File.separator+ "replays" ) );
			this.fileChooser.setFileFilter( this.apoFileFilter );
		}
	}

	/**
	 * alle Werte werden initialisiert
	 */
	public void init() {
		if (this.io == null) {
			this.io = new ApoSoccerReplayIO();
		}
		
		this.ball = new ArrayList<ApoFloatPoint>();
		
		this.homeDefenderOne = new ArrayList<ApoFloatPoint>();
		this.homeDefenderTwo = new ArrayList<ApoFloatPoint>();
		this.homeForward = new ArrayList<ApoFloatPoint>();
		this.homeGoalkeeper = new ArrayList<ApoFloatPoint>();
		
		this.visitorDefenderOne = new ArrayList<ApoFloatPoint>();
		this.visitorDefenderTwo = new ArrayList<ApoFloatPoint>();
		this.visitorForward = new ArrayList<ApoFloatPoint>();
		this.visitorGoalkeeper = new ArrayList<ApoFloatPoint>();
		
		this.setBReplayPlay(false);
		this.setBReplaySave(true);
		
		this.curStep = 0;
	}
	
	/**
	 * gibt den aktuellen Schritt, welcher angezeigt werden soll, zurück
	 * @return gibt den aktuellen Schritt, welcher angezeigt werden soll, zurück
	 */
	public int getCurStep() {
		return this.curStep;
	}

	/**
	 * setzt den aktuellen Schritt, welcher angezeigt werden soll, auf den übergebenen Wert
	 * @param curStep : der aktuelle Schritt
	 */
	public void setCurStep(int curStep) {
		this.curStep = curStep;
	}

	/**
	 * gibt zurück, ob ein Replay abgespielt wird
	 * @return TRUE, Replay wird abgespielt, sonst FALSE
	 */
	public boolean isBReplayPlay() {
		return this.bReplayPlay;
	}

	/**
	 * setzt die boolean Variable, ob das Replay abgespielt wird, auf den übergebenen
	 * @param bReplayPlay : TRUE, Replay abspielen sonst FALSE
	 */
	public void setBReplayPlay(boolean bReplayPlay) {
		this.bReplayPlay = bReplayPlay;
	}

	/**
	 * gibt zurück, ob ein Replay gespeichert wird
	 * @return TRUE, Replay wird gespeichert, sonst FALSE
	 */
	public boolean isBReplaySave() {
		return this.bReplaySave;
	}

	/**
	 * setzt die boolean Variable, ob das Replay gespeichert wird, auf den übergebenen
	 * @param bReplaySave TRUE, Replay wird gespeichert, sonst FALSE
	 */
	public void setBReplaySave(boolean bReplaySave) {
		this.bReplaySave = bReplaySave;
	}
	
	public ArrayList<ApoFloatPoint> getHomeDefenderOne() {
		return this.homeDefenderOne;
	}

	public void setHomeDefenderOne(ArrayList<ApoFloatPoint> homeDefenderOne) {
		this.homeDefenderOne = homeDefenderOne;
	}

	public ArrayList<ApoFloatPoint> getHomeDefenderTwo() {
		return this.homeDefenderTwo;
	}

	public void setHomeDefenderTwo(ArrayList<ApoFloatPoint> homeDefenderTwo) {
		this.homeDefenderTwo = homeDefenderTwo;
	}

	public ArrayList<ApoFloatPoint> getHomeGoalkeeper() {
		return this.homeGoalkeeper;
	}

	public void setHomeGoalkeeper(ArrayList<ApoFloatPoint> homeGoalkeeper) {
		this.homeGoalkeeper = homeGoalkeeper;
	}

	public ArrayList<ApoFloatPoint> getHomeForward() {
		return this.homeForward;
	}

	public void setHomeForward(ArrayList<ApoFloatPoint> homeForward) {
		this.homeForward = homeForward;
	}

	public ArrayList<ApoFloatPoint> getVisitorDefenderOne() {
		return this.visitorDefenderOne;
	}

	public void setVisitorDefenderOne(ArrayList<ApoFloatPoint> visitorDefenderOne) {
		this.visitorDefenderOne = visitorDefenderOne;
	}

	public ArrayList<ApoFloatPoint> getVisitorDefenderTwo() {
		return this.visitorDefenderTwo;
	}

	public void setVisitorDefenderTwo(ArrayList<ApoFloatPoint> visitorDefenderTwo) {
		this.visitorDefenderTwo = visitorDefenderTwo;
	}

	public ArrayList<ApoFloatPoint> getVisitorGoalkeeper() {
		return this.visitorGoalkeeper;
	}

	public void setVisitorGoalkeeper(ArrayList<ApoFloatPoint> visitorGoalkeeper) {
		this.visitorGoalkeeper = visitorGoalkeeper;
	}

	public ArrayList<ApoFloatPoint> getVisitorForward() {
		return this.visitorForward;
	}

	public void setVisitorForward(ArrayList<ApoFloatPoint> visitorForward) {
		this.visitorForward = visitorForward;
	}

	public ArrayList<ApoFloatPoint> getBall() {
		return this.ball;
	}

	public void setBall(ArrayList<ApoFloatPoint> ball) {
		this.ball = ball;
	}

	public String getHomeTeamName() {
		return this.homeTeamName;
	}

	public void setHomeTeamName(String homeTeamName) {
		this.homeTeamName = homeTeamName;
	}

	public String getVisitorTeamName() {
		return this.visitorTeamName;
	}

	public void setVisitorTeamName(String visitorTeamName) {
		this.visitorTeamName = visitorTeamName;
	}

	/**
	 * öffnet einen OpenDialog und vesucht dann die Datei zu laden
	 * @return TRUE, DAtei konnte geladen werden, sonst FALSE
	 */
	public boolean loadReplay() {
		if (this.fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			this.io.setReplay(this);
			String s = this.fileChooser.getSelectedFile().getPath();
			this.io.readLevel(s);
			return true;
		}
		return false;
	}
	
	/**
	 * öffnet einen SaveDialog und versucht ein Replay zu speichern
	 * @return TRUE, Datei konnte gespeichert werden, sonst FALSE
	 */
	public boolean saveReplay() {
		if (this.fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			this.saveReplay(this.fileChooser.getSelectedFile().getPath());
			return true;
		}
		return false;
	}

	/**
	 * öffnet einen SaveDialog und versucht ein Replay zu speichern
	 * @return TRUE, Datei konnte gespeichert werden, sonst FALSE
	 */
	public boolean saveReplay(String file) {
		this.io.setReplay(this);
		String s = file;
		int t = s.indexOf(this.apoFileFilter.getLevelName());
		if ( t != -1 ) {
			s	= s.substring( 0, t );
		}
		s	+= this.apoFileFilter.getLevelName();
		this.io.writeLevel(s);
		return true;
	}
	
	/**
	 * fügt einen Schritt zumReplay hinzu
	 * @param home : Das HomeTeam
	 * @param visitor : Das VisitorTeam
	 * @param ball : Der Ball
	 * @return TRUE, wenn alles geklappt hat, sonst FALSE
	 */
	public boolean addStep(ApoTeam home, ApoTeam visitor, ApoBall ball) {
		if (this.isBReplayPlay()) {
			return false;
		}
		this.ball.add(new ApoFloatPoint(ball.getX(), ball.getY()));
		
		this.homeDefenderOne.add(new ApoFloatPoint(home.getPlayer(ApoSoccerConstants.DEFENDER_ONE).getX(), home.getPlayer(ApoSoccerConstants.DEFENDER_ONE).getY()));
		this.homeDefenderTwo.add(new ApoFloatPoint(home.getPlayer(ApoSoccerConstants.DEFENDER_TWO).getX(), home.getPlayer(ApoSoccerConstants.DEFENDER_TWO).getY()));
		this.homeForward.add(new ApoFloatPoint(home.getPlayer(ApoSoccerConstants.FORWARD).getX(), home.getPlayer(ApoSoccerConstants.FORWARD).getY()));
		this.homeGoalkeeper.add(new ApoFloatPoint(home.getPlayer(ApoSoccerConstants.GOALKEEPER).getX(), home.getPlayer(ApoSoccerConstants.GOALKEEPER).getY()));
		
		this.visitorDefenderOne.add(new ApoFloatPoint(visitor.getPlayer(ApoSoccerConstants.DEFENDER_ONE).getX(), visitor.getPlayer(ApoSoccerConstants.DEFENDER_ONE).getY()));
		this.visitorDefenderTwo.add(new ApoFloatPoint(visitor.getPlayer(ApoSoccerConstants.DEFENDER_TWO).getX(), visitor.getPlayer(ApoSoccerConstants.DEFENDER_TWO).getY()));
		this.visitorForward.add(new ApoFloatPoint(visitor.getPlayer(ApoSoccerConstants.FORWARD).getX(), visitor.getPlayer(ApoSoccerConstants.FORWARD).getY()));
		this.visitorGoalkeeper.add(new ApoFloatPoint(visitor.getPlayer(ApoSoccerConstants.GOALKEEPER).getX(), visitor.getPlayer(ApoSoccerConstants.GOALKEEPER).getY()));
		
		this.homeTeamName = home.getTeamName();
		this.visitorTeamName = visitor.getTeamName();
		return true;
	}
	
	/**
	 * spielt den aktuellen Schritt ab
	 * @param home : das HomeTeam
	 * @param visitor : das VisitorTeam
	 * @param ball : der Ball
	 * @param delta : Zeit die seit dem letzten Aufruf vergangen ist
	 * @return TRUE, wenn alles geklappt hat, sonst FALSE
	 */
	public boolean playStep(ApoTeam home, ApoTeam visitor, ApoBall ball, int delta, int addCurStep) {
		if (this.getCurStep() >= this.ball.size()) {
			return false;
		}
		
		this.setEntityPosition(home.getPlayer(ApoSoccerConstants.DEFENDER_ONE), this.homeDefenderOne, delta);
		this.setEntityPosition(home.getPlayer(ApoSoccerConstants.DEFENDER_TWO), this.homeDefenderTwo, delta);
		this.setEntityPosition(home.getPlayer(ApoSoccerConstants.FORWARD), this.homeForward, delta);
		this.setEntityPosition(home.getPlayer(ApoSoccerConstants.GOALKEEPER), this.homeGoalkeeper, delta);
		
		this.setEntityPosition(visitor.getPlayer(ApoSoccerConstants.DEFENDER_ONE), this.visitorDefenderOne, delta);
		this.setEntityPosition(visitor.getPlayer(ApoSoccerConstants.DEFENDER_TWO), this.visitorDefenderTwo, delta);
		this.setEntityPosition(visitor.getPlayer(ApoSoccerConstants.FORWARD), this.visitorForward, delta);
		this.setEntityPosition(visitor.getPlayer(ApoSoccerConstants.GOALKEEPER), this.visitorGoalkeeper, delta);
		
		this.setEntityPosition(ball, this.ball, delta);
		
		this.curStep += addCurStep;
		if (this.curStep < 0) {
			this.curStep = 0;
		}
		return true;
	}
	
	/**
	 * setzt eine an den derzeitigen Schrittpunkt
	 * @param entity : die Entity
	 * @param point : eine ArrayList mit den dazugehörigen Punkten
	 * @param delta : Zeit, die seit dem letzten Aufruf vergangen ist
	 * @return immer TRUE
	 */
	private boolean setEntityPosition(ApoSoccerEntity entity, ArrayList<ApoFloatPoint> point, int delta) {
		entity.setX(point.get(this.getCurStep()).x);
		entity.setY(point.get(this.getCurStep()).y);
		entity.setSpeed(5);
		if (this.getCurStep() + 1 < point.size()) {
			entity.setLineOfSight((int)entity.getAngleBetweenTwoPoints(point.get(this.getCurStep() + 1).x, point.get(this.getCurStep() + 1).y));
		}
		entity.think(delta);
		if (entity instanceof ApoBall) {
			if (this.getCurStep() + 1 < point.size()) {
				if (Math.abs(point.get(this.getCurStep()).getX() - point.get(this.getCurStep() + 1).getX()) > 15) {
					this.bGoal = true;
					if (point.get(this.getCurStep()).getX() < ApoSoccerConstants.FIELD_WIDTH/2) {
						if (ApoSoccerConstants.LEFT_GOAL_REC.intersects(entity.getX() - entity.getWidth(), entity.getY() - entity.getWidth(), entity.getWidth() * 2, entity.getHeight() * 2)) {
							this.bHomeGoal = true;
						} else {
							this.bGoal = false;
						}
					} else if (point.get(this.getCurStep()).getX() > ApoSoccerConstants.FIELD_WIDTH/2) {
						if (ApoSoccerConstants.RIGHT_GOAL_REC.intersects(entity.getX() - entity.getWidth(), entity.getY() - entity.getWidth(), entity.getWidth() * 2, entity.getHeight() * 2)) {	
							this.bHomeGoal = false;
						} else {
							this.bGoal = false;
						}
					}
				} else {
					this.bGoal = false;
					this.bHomeGoal = false;
				}
			}
		}
		return true;
	}

	public boolean isBGoal() {
		return this.bGoal;
	}

	public boolean isBHomeGoal() {
		return this.bHomeGoal;
	}
	
}
