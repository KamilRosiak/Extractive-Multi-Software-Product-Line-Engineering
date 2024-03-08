package apoSoccer.entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import org.apogames.ApoConstants;
import org.apogames.speech.ApoSpeech;

import apoSoccer.ApoSoccerConstants;
import apoSoccer.entityForAI.ApoSoccerAI;
import apoSoccer.entityForAI.ApoSoccerAIJNI;
import apoSoccer.entityForAI.ApoSoccerDebugLine;
import apoSoccer.entityForAI.ApoSoccerEnemy;
import apoSoccer.entityForAI.ApoSoccerTeam;
import apoSoccer.entityForAI.ApoSoccerTeamSet;
import apoSoccer.game.ApoSoccerGame;

/**
 * Klasse die ein komplettes Team repraesentiert
 * @author Dirk Aporius
 *
 */
public class ApoTeam {
	
	/** gibt die Zeit an, die gewartet werden muss wenn gesprochen wird bis zum naechsten Mal Sprecherlaubnis */
	private final long WAIT_TIME_UNTIL_NEXT_SPEAK = 10000l;
	/** gibt die Zeit an, die gewartet werden muss wenn gesprochen wird bis zum naechsten Mal Sprecherlaubnis */
	private final long MIN_SHOW_TIME = 6000l;
	
	/** die KI, die diese Klasse lenkt */
	private ApoSoccerAI ai;
	/** die KI, fuer extern geschriebene KI's */
	
	private ApoSoccerAIJNI jni;
	/** eine ArrayList mit allen Spielern im Team */
	private ArrayList<ApoPlayer> team;
	/** Variable fuer die Anzahl der Tore */
	private int goals;
	/** eine ArrayList mit allen SPieler in der Klasse die die KI bekommt */
	private ArrayList<ApoSoccerTeam> teamForAi;
	/** das Array mit den Namen der Spieler */
	private String[] names;
	/** eine ArrayList mit den letzten Werten, wielange es gedauert hat, dass die KI nachdenkt */
	private ArrayList<Long> times;
	/** das SpeechObjekt, um die Strings vorgelesen zu bekommen */
	private ApoSpeech speech;
	/** Variable die angibt, wie lange schon gewartet wurde seit dem letzten Sprechen */
	private long waitTime;
	/** Variable die angibt, was gesagt werden kann */
	private String[] speakArray;
	/** Variable die angibt, was gesagt wird */
	private String speakText;
	/** Variable die angibt, welcher Spieler was sagt */
	private int playerText;
	
	private String path;
	private boolean bHome;
	
	public ApoTeam(boolean bHome) {
		this.init();
		this.bHome = bHome; 
	}
	
	/**
	 * Init-Methode, die aufgerufen wird wenn die Klasse erstellt wird, oder wenn
	 * die Spieler zurueckgesetzt werden sollen
	 */
	public void init() {
		if (this.team == null) {
			this.team = new ArrayList<ApoPlayer>();
			this.teamForAi = new ArrayList<ApoSoccerTeam>();
		} else {
			for (int i = 0; i < this.team.size(); i++) {
				this.team.get(i).init();
			}
		}
		this.goals = 0;
		this.times = new ArrayList<Long>();
		
		if (this.names == null) {
			String[] names = new String[ApoSoccerConstants.PLAYER_AMOUNT];
			names[ApoSoccerConstants.GOALKEEPER] = "Goalkeeper";
			names[ApoSoccerConstants.FORWARD] = "Forward";
			names[ApoSoccerConstants.DEFENDER_ONE] = "Defender_One";
			names[ApoSoccerConstants.DEFENDER_TWO] = "Defender_Two";
			this.names = names;
		}
		this.playerText = -1;
		this.waitTime = (long)(Math.random() * this.WAIT_TIME_UNTIL_NEXT_SPEAK);
		if (this.path == null) {
			if (ApoConstants.B_APPLET) {
				this.path = "http://apo-games.de/apoSoccer/player/";
			} else {
				this.path = System.getProperty("user.dir") + File.separator;
			}
		}
	}
	
	/**
	 * wird aufgerufen, wenn ein Tor gefallen ist und sagt den Spielern, dass sie wieder auf ihre Ausgangspositionen
	 * zurueck gesetzt werden sollen
	 */
	public void goal(boolean bRunPlayer) {
		boolean bLeft = true;
		if (this.team.get(ApoSoccerConstants.GOALKEEPER).getStartX() > ApoSoccerConstants.FIELD_WIDTH/2) {
			bLeft = false;
		}
		for (int i = 0; i < this.team.size(); i++) {
			if (!bRunPlayer) {
				this.team.get(i).goal();
				this.team.get(i).setBRunPlayer(false);
			} else {
				this.team.get(i).setBRunPlayer(bRunPlayer);
			}
			this.team.get(i).setBLeft(bLeft);
		}
		if (this.ai != null) {
			ApoSoccerTeamSet[] team = new ApoSoccerTeamSet[ApoSoccerConstants.PLAYER_AMOUNT_DEFENDER];
			team[ApoSoccerConstants.DEFENDER_ONE] = new ApoSoccerTeamSet(this.getPlayer(ApoSoccerConstants.DEFENDER_ONE), this);
			team[ApoSoccerConstants.DEFENDER_TWO] = new ApoSoccerTeamSet(this.getPlayer(ApoSoccerConstants.DEFENDER_TWO), this);
			this.ai.setPosition(team);
		} else if (this.jni != null) {
			ApoSoccerTeamSet[] team = new ApoSoccerTeamSet[ApoSoccerConstants.PLAYER_AMOUNT_DEFENDER];
			team[ApoSoccerConstants.DEFENDER_ONE] = new ApoSoccerTeamSet(this.getPlayer(ApoSoccerConstants.DEFENDER_ONE), this);
			team[ApoSoccerConstants.DEFENDER_TWO] = new ApoSoccerTeamSet(this.getPlayer(ApoSoccerConstants.DEFENDER_TWO), this);
			ApoSoccerAIJNI.setPosition(this.bHome, team);
		}
	}
	
	/**
	 * wird bei der Halbzeit aufgerufen und tauscht die Seiten der Spieler
	 * @return immer TRUE
	 */
	public boolean halfTime() {
		boolean bLeft = this.team.get(0).isBLeft();
		if (bLeft) {
			this.halfTimeChange(this.team.get(ApoSoccerConstants.DEFENDER_ONE), ApoSoccerConstants.DEFENDER_ONE_RIGHT, bLeft);
			this.halfTimeChange(this.team.get(ApoSoccerConstants.DEFENDER_TWO), ApoSoccerConstants.DEFENDER_TWO_RIGHT, bLeft);
			this.halfTimeChange(this.team.get(ApoSoccerConstants.GOALKEEPER), ApoSoccerConstants.GOALKEEPER_RIGHT, bLeft);
			this.team.get(ApoSoccerConstants.GOALKEEPER).setWalkableArea(ApoSoccerConstants.RIGHT_GOALKEEPER_REC);
			this.halfTimeChange(this.team.get(ApoSoccerConstants.FORWARD), ApoSoccerConstants.FORWARD_RIGHT, bLeft);
			this.team.get(ApoSoccerConstants.FORWARD).setWalkableArea(ApoSoccerConstants.LEFT_SIDE_REC);
		} else {
			this.halfTimeChange(this.team.get(ApoSoccerConstants.DEFENDER_ONE), ApoSoccerConstants.DEFENDER_ONE_LEFT, bLeft);
			this.halfTimeChange(this.team.get(ApoSoccerConstants.DEFENDER_TWO), ApoSoccerConstants.DEFENDER_TWO_LEFT, bLeft);
			this.halfTimeChange(this.team.get(ApoSoccerConstants.GOALKEEPER), ApoSoccerConstants.GOALKEEPER_LEFT, bLeft);
			this.team.get(ApoSoccerConstants.GOALKEEPER).setWalkableArea(ApoSoccerConstants.LEFT_GOALKEEPER_REC);
			this.halfTimeChange(this.team.get(ApoSoccerConstants.FORWARD), ApoSoccerConstants.FORWARD_LEFT, bLeft);
			this.team.get(ApoSoccerConstants.FORWARD).setWalkableArea(ApoSoccerConstants.RIGHT_SIDE_REC);
		}
		for (int i = 0; i < this.team.size(); i++) {
			this.team.get(i).setHalfTime();
		}
		return true;
	}
	
	/**
	 * wird aufgerufen, wenn der Ball festgeklemmt ist und wieder in die Mitte gelegt wird und veranlasst die Spieler
	 * keinen Schuss abgegeben zu wollen
	 * @return immer TRUE
	 */
	public boolean ballReset() {
		for (int i = 0; i < this.team.size(); i++) {
			this.team.get(i).ballReset();
		}
		return true;
	}
	
	/**
	 * wird pro Spieler aufgerufen bei der Halbeit und setzt den Spieler an den Punkt und gibt ihm die richtige Blickrichtung
	 * @param player : Spieler
	 * @param p : Punkt, wo der Spieler hinsoll
	 * @param bLeft : Boolean Variable, die angibt ob der links oder rechts dann spielt
	 */
	private void halfTimeChange(ApoPlayer player, Point p, boolean bLeft) {
		player.setStartX(p.x);
		player.setStartY(p.y);
		player.setBLeft(!bLeft);
		if (player.isBLeft()) {
			player.setStartLineOfSight(0);
		} else {
			player.setStartLineOfSight(180);
		}
		if (this.bHome) {
			player.setBKickOff(false);
		} else {
			player.setBKickOff(true);
		}
	}

	/**
	 * gibt die eine GegnerarrayList für das Nachdenken zurück
	 * @return die Gegner
	 */
	public ArrayList<ApoSoccerEnemy> getEnemy() {
		ArrayList<ApoSoccerEnemy> enemy = new ArrayList<ApoSoccerEnemy>();
		for (int i = 0; i < this.getPlayers().size(); i++) {
			enemy.add(new ApoSoccerEnemy(this.getPlayer(i), this));
		}
		return enemy;
	}

	/**
	 * gibt die KI zurueck
	 * @return gibt die KI zurueck
	 */
	public ApoSoccerAI getAi() {
		return this.ai;
	}
	
	public ApoSoccerAIJNI getJni() {
		return this.jni;
	}
	
	/**
	 * setzt die KI auf den uebergebenen Wert
	 * @param ai : neue KI
	 */
	public void setAi(ApoSoccerAIJNI jni, ApoSoccerAI ai, String path, ApoSoccerGame game) {
		this.ai = null;
		this.jni = null;
		if (ai != null) {
			this.ai = ai;
		} else if (jni != null) {
			this.jni = jni;
		}
		this.path = path;
		if (ai != null) {
			this.names = this.ai.getNames().clone();
		} else if (jni != null) {
			this.names = ApoSoccerAIJNI.getNames(this.bHome).clone();
		}
		for (int i = 0; i < this.team.size() && i < this.names.length; i++) {
			this.team.get(i).setName(this.names[i]);
		}
		boolean[] gender = null;
		int[] hairs = null;
		Color shirt = null;
		Color trouser = null;
		if (ai != null) {
			gender = this.ai.getGender();
			hairs = this.ai.getHair();
			shirt = this.ai.getShirtColor();
			trouser = this.ai.getTrouserColor();
		} else if (jni != null) {
			gender = ApoSoccerAIJNI.getGender(this.bHome);
			hairs = ApoSoccerAIJNI.getHair(this.bHome);
			shirt = ApoSoccerAIJNI.getShirtColor(this.bHome);
			trouser = ApoSoccerAIJNI.getTrouserColor(this.bHome);
		}
		boolean bLeft = true;
		if (this.team.get(ApoSoccerConstants.GOALKEEPER).getX() > ApoSoccerConstants.FIELD_WIDTH/2) {
			bLeft = false;
		}
		if (shirt == null) {
			if (bLeft) {
				shirt = Color.red;
			} else {
				shirt = Color.blue;
			}
		}
		if (trouser == null) {
			if (bLeft) {
				trouser = Color.white;
			} else {
				trouser = Color.black;
			}
		}
		for (int i = 0; i < this.team.size() && i < hairs.length; i++) {
			this.team.get(i).setBFemale(gender[i]);
			BufferedImage iPlayer = null; 
			if (gender[i]) {
				if (this.getPlayerImage(true) != null) {
					iPlayer = game.getImages().getImage(this.getPlayerImage(true), true);
				}
			} else {
				if (this.getPlayerImage(false) != null) {
					iPlayer = game.getImages().getImage(this.getPlayerImage(false), true);
				}
			}
			if (iPlayer == null) {
				if (gender[i]) {
					iPlayer = game.getIPlayerFemale();
					if ((hairs[i] < ApoSoccerConstants.HAIR_FEMALE_ONE_OGER) && (hairs[i] > ApoSoccerConstants.HAIR_WITHOUT)) {
						hairs[i] = hairs[i] - 1 + ApoSoccerConstants.HAIR_FEMALE_ONE_OGER;
					}
				} else {
					iPlayer = game.getIPlayerMan();
					if ((hairs[i] >= ApoSoccerConstants.HAIR_FEMALE_ONE_OGER) && (hairs[i] < ApoSoccerConstants.APO_HAIR)) {
						hairs[i] = hairs[i] + 1 - ApoSoccerConstants.HAIR_FEMALE_ONE_OGER;
					}
				}
			}
			iPlayer = game.getImages().getPlayerWithHair(iPlayer, game.getHairs()[hairs[i]]);
			iPlayer = new ApoPlayerCloth().getPlayerImage(game, iPlayer, shirt, trouser);
			this.team.get(i).setIBackground(iPlayer);
		}
		String[] aiSpeech = null;
		if (ai != null) {
			aiSpeech = this.ai.getSpeech();
		} else if (jni != null) {
			aiSpeech = ApoSoccerAIJNI.getSpeech(this.bHome);
		}
		String[] speech;
		if ((aiSpeech != null) && (aiSpeech.length > ApoSoccerConstants.MAX_SPEECH_STRINGARRAY_LENGTH)) {
			speech = new String[ApoSoccerConstants.MAX_SPEECH_STRINGARRAY_LENGTH];
			for (int i = 0; i < speech.length; i++) {
				speech[i] = aiSpeech[i];
			}
		} else {
			speech = aiSpeech;
		}
		if (speech != null) {
			if (game.isBSound()) {
				this.speech = new ApoSpeech(speech);
			}
			this.speakArray = new String[speech.length];
			for (int i = 0; i < speech.length; i++) {
				this.speakArray[i] = speech[i];
			}
		}
		this.waitTime = (long)(Math.random() * this.WAIT_TIME_UNTIL_NEXT_SPEAK);
	}
	
	/**
	 * erstellt das Speech Objekt mithilfe des SpeakArrays
	 * @return immer TRUE
	 */
	public boolean setSpeechOn() {
		if (ApoSoccerConstants.B_MUSIC) {
			this.speech = new ApoSpeech(this.speakArray);
		}
		return true;
	}
	
	/**
	 * gibt das Array mit den Namen der Spieler zurueck
	 * @return gibt das Array mit den Namen der Spieler zurueck
	 */
	public String[] getNames() {
		return this.names;
	}
	
	/**
	 * gibt den String fuer das Emblem zurueck
	 * @return gibt den String fuer das Emblem zurueck
	 */
	public String getEmblem() {
		if ((this.ai != null) && (this.ai.getEmblem() != null)) {
			return this.path + this.ai.getEmblem();
		} else if ((this.jni != null) && (ApoSoccerAIJNI.getEmblem(this.bHome) != null)) {
			return this.path + ApoSoccerAIJNI.getEmblem(this.bHome);
		} else {
			return null;
		}
	}

	/**
	 * gibt den String fuer das Emblem zurueck
	 * @return gibt den String fuer das Emblem zurueck
	 */
	public String getPlayerImage(boolean bFemale) {
		if (bFemale) {
			if ((this.ai != null) && (this.ai.getPlayerFemaleImage() != null)) {
				return this.path + this.ai.getPlayerFemaleImage();
			} else if ((this.jni != null) && (ApoSoccerAIJNI.getPlayerFemaleImage(this.bHome) != null)) {
				return this.path + ApoSoccerAIJNI.getPlayerFemaleImage(this.bHome);
			}
		} else {
			if ((this.ai != null) && (this.ai.getPlayerManImage() != null)) {
				return this.path + this.ai.getPlayerManImage();
			} else if ((this.jni != null) && (ApoSoccerAIJNI.getPlayerManImage(this.bHome) != null)) {
				return this.path + ApoSoccerAIJNI.getPlayerManImage(this.bHome);
			}
		}
		return null;
	}
	
	/**
	 * gibt den Namen des Teams zurueck
	 * @return gibt den Namen des Teams zurueck
	 */
	public String getTeamName() {
		if ((this.ai != null) && (this.ai.getTeamName() != null)) {
			return this.ai.getTeamName();
		} else if ((this.jni != null) && (ApoSoccerAIJNI.getTeamName(this.bHome) != null)) {
			return ApoSoccerAIJNI.getTeamName(this.bHome);
		} else {
			return "Standard Team";
		}
	}
	
	/**
	 * gibt den Autor der KI zurueck
	 * @return gibt den Autor der KI zurueck
	 */
	public String getAuthor() {
		if ((this.ai != null) && (this.ai.getAuthor() != null)) {
			return this.ai.getAuthor();
		} else if ((this.jni != null) && (ApoSoccerAIJNI.getAuthor(this.bHome) != null)) {
			return ApoSoccerAIJNI.getAuthor(this.bHome);
		} else {
			return "MaxMustermann";
		}
	}
	
	/**
	 * gibt die Anzahl der Tore, die dieses Team geschossen hat zurueck
	 * @return gibt die Anzahl der Tore, die dieses Team geschossen hat zurueck
	 */
	public int getGoals() {
		return this.goals;
	}

	/**
	 * setzt die Anzahl der Tore, die das Team geschossen hat auf den uebergebenen Wert
	 * @param goals
	 */
	public void setGoals(int goals) {
		this.goals = goals;
	}

	/**
	 * fuegt einen Spieler zum Team hinzu
	 * @param player : neuer SPieler
	 */
	public void addPlayer(ApoPlayer player) {
		this.team.add(player);
		this.teamForAi.add(new ApoSoccerTeam(player, this));
	}
	
	/**
	 * gibt den Spieler des Team an der Stelle i zurueck
	 * @param i : Position des Spielers den man haben mÃ¶chte
	 * @return gibt den Spieler des Team an der uebergebenen Stelle i zurueck
	 */
	public ApoPlayer getPlayer(int i) {
		if ((i < 0) || (i >= this.team.size())) {
			return null;
		}
		return this.team.get(i);
	}
	
	/**
	 * gibt das komplette Team als ArrayList zurueck
	 * @return
	 */
	public ArrayList<ApoPlayer> getPlayers() {
		return this.team;
	}
	
	/**
	 * setzt alle Spieler des Teams auf die Positionen vor dem Spiel zur Vorstellung der SPieler
	 * @param bLeft : links oder rechts von der Mittellinie
	 * @return immer TRUE
	 */
	public boolean setStartPositions(boolean bLeft) {
		int y = ApoSoccerConstants.FIELD_HEIGHT - 150;
		for (int i = 0; i < this.team.size(); i++) {
			team.get(i).setLineOfSight(90);
			team.get(i).setY(y - 40 * i);
			if (bLeft) {
				team.get(i).setX(ApoSoccerConstants.FIELD_WIDTH/2 - 80 - i * 30);
			} else {
				team.get(i).setX(ApoSoccerConstants.FIELD_WIDTH/2 + 80 + i * 30);
			}
		}
		return true;
	}
	
	/**
	 * laesst die KI's denken
	 * @param delta : Zeit die seit dem letzten Aufruf vergangen ist
	 * @param game : Das SPielbjekt, um den Ball zu bekommen
	 * @throws Exception 
	 */
	public void thinkAI(int delta, ApoSoccerGame game) throws Exception {
		long t = System.nanoTime();
		if (this.ai != null) {
			if (this.bHome) {
				this.ai.think(game.getBall().getBall(), this.teamForAi, game.getVisitorTeam().getEnemy());
			} else {
				this.ai.think(game.getBall().getBall(), this.teamForAi, game.getHomeTeam().getEnemy());
			}
			for (int i = 0; i < this.team.size(); i++) {
				if (this.team.get(i).isBPlayerHuman()) {
					game.getKey().checkKeys(this, i);
				} else if ((this.getAuthor().equals("Debugghost")) && (this.getTeamName().equals("Ghost"))) {
					game.getKey().checkKeys(this, -1);
				}
			}
		} else if (this.jni != null) {
			if (!this.bHome) {
				// converting ArrayList to simple array for easier access over JNI
//				(ApoSoccerTeam[])this.teamForAi.toArray()
//				(ApoSoccerEnemy[])game.getHomeTeam().getEnemy().toArray()
				
				ApoSoccerTeam[] team = new ApoSoccerTeam[this.teamForAi.size()];
				int i = 0;
				for (ApoSoccerTeam apoSoccerTeam : this.teamForAi)
				{
					team[i++] = apoSoccerTeam;
				}
				
				ApoSoccerEnemy[] enemies = new ApoSoccerEnemy[game.getHomeTeam().getEnemy().size()];
				i = 0;
				for (ApoSoccerEnemy apoSoccerEnemy : game.getHomeTeam().getEnemy())
				{
					enemies[i++] = apoSoccerEnemy;
				}
				
				ApoSoccerAIJNI.think(this.bHome, game.getBall().getBall(), team, enemies);
			} else {
				// converting ArrayList to simple array for easier access over JNI
//				(ApoSoccerTeam[])this.teamForAi.toArray()
//				(ApoSoccerEnemy[])game.getVisitorTeam().getEnemy().toArray()				
				ApoSoccerTeam[] team = new ApoSoccerTeam[this.teamForAi.size()];
				int i = 0;
				for (ApoSoccerTeam apoSoccerTeam : this.teamForAi)
				{
					team[i++] = apoSoccerTeam;
				}
				
				ApoSoccerEnemy[] enemies = new ApoSoccerEnemy[game.getVisitorTeam().getEnemy().size()];
				i = 0;
				for (ApoSoccerEnemy apoSoccerEnemy : game.getVisitorTeam().getEnemy())
				{
					enemies[i++] = apoSoccerEnemy;
				}
				
				ApoSoccerAIJNI.think(this.bHome, game.getBall().getBall(), team, enemies);				
			}
			for (int i = 0; i < this.team.size(); i++) {
				if (this.team.get(i).isBPlayerHuman()) {
					game.getKey().checkKeys(this, i);
				}
			}
		}
		t = System.nanoTime() - t;
		if (this.times.size() == 0) {
			this.times.add(t);
		} else {
			for (int i = 0; i < this.times.size(); i++) {
				if (this.times.get(i) > t) {
					this.times.add(i, t);
					break;
				} else if (i == this.times.size() - 1) {
					this.times.add(i, t);
					break;
				}
			}
		}
		t = 0;
		
		if (this.times.size() > 60) {
			this.times.remove(this.times.size() - 1);
			for (int i = 10; i < this.times.size() - 10; i++) {
				t += this.times.get(i); 
			}
			if (t / this.times.size() > 5 * (ApoSoccerConstants.WAIT_TIME_THINK/2 * 1000000)) {
				throw new Exception("Spieler hat laenger als "+ApoSoccerConstants.WAIT_TIME_THINK/2+" Millisekunden nachgedacht");
			}
		}
	}
	
	/**
	 * bewegt alle Spieler des Teams
	 * @param delta : Zeit die seit dem letzten Aufruf vergangen ist
	 * @param game : Das SPielbjekt, um den Ball zu bekommen
	 */
	public void think(int delta, ApoSoccerGame game) {
		int playerSpeak = -1;
		int text = -1;
		for (int i = 0; i < this.team.size(); i++) {
			this.team.get(i).think(delta, game);
			if (this.team.get(i).getTextIndex() >= 0) {
				playerSpeak = i;
				text = this.team.get(i).getTextIndex();
			}
		}
		if (!game.isBSimulate()) {
			if (this.waitTime >= 0) {
				this.waitTime -= delta;
				if (game.isBSound()) {
					if ((this.speech != null) && (!this.speech.isBSpeak())) {
						this.playerText = -1;
					} else {
						this.playerText = -1;
					}
				} else if ((this.waitTime < this.MIN_SHOW_TIME) && (this.playerText >= 0)){
					this.playerText = -1;
				}
			} else if (playerSpeak >= 0) {
				if (text < this.speakArray.length) {
					//System.out.println("Sprich Sklave und zwar "+this.speakArray[text]);
					if (this.speech != null) {
						if (game.isBSound()) {
							this.speech.speak(this.speakArray[text]);
						}
					}
					this.playerText = playerSpeak;
					this.speakText = this.speakArray[text];
					this.waitTime = this.WAIT_TIME_UNTIL_NEXT_SPEAK;
					for (int i = 0; i < this.team.size(); i++) {
						this.team.get(i).setBAllowSpeak(false);
						this.team.get(i).setTextIndex(-1);
					}
				}
			} else if (this.waitTime < 0) {
				for (int i = 0; i < this.team.size(); i++) {
					if (!this.team.get(i).isBAllowSpeak()) {
						this.team.get(i).setBAllowSpeak(true);
					}
				}
			}
		}
		for (int i = 0; i < this.team.size(); i++) {
			if (this.team.get(i).getLines().size() > 0) {
				ArrayList<ApoSoccerDebugLine> lines = this.team.get(i).getLines();
				for (int j = lines.size() - 1; j >= 0; j--) {
					lines.get(j).setTime(lines.get(j).getTime() - delta);
					if (lines.get(j).getTime() <= 0) {
						lines.remove(j);
					}
				}
			}
		}
	}
	
	/**
	 * testet, ob ein Spieler noch auf seine Ausgangsposition rennt
	 * @return TRUE, wenn ein Spieler noch auf seine AUsgangsposition rennt, sonst FALSE
	 */
	public boolean isPlayerRunning() {
		for (int i = 0; i < this.team.size(); i++) {
			if (this.team.get(i).isBRunPlayer()) {
				//System.out.println("name = "+this.team.get(i).getName()+"x = "+this.team.get(i).getX()+" startX = "+this.team.get(i).getStartX()+" y = "+this.team.get(i).getY()+" startY = "+this.team.get(i).getStartY());
				return true;
			}
		}
		return false;
	}
	
	/**
	 * rendert einen Spieler fuer ein Team
	 * @param g : Das GraphicsObjekt auf das gezeichnet wird
	 * @param changeX : Verschiebung um den x-Wert beim Zeichnen
	 * @param changeY : Verschiebung um den y-Wert beim Zeichnen
	 */
	public void renderPlayer(Graphics2D g, int player, int changeX, int changeY) {
		if ((this.team != null) && (player < this.team.size())) {
			if ((this.team.get(player).getX() + changeX < -15) || (this.team.get(player).getX() + changeX - 10 > ApoSoccerConstants.GAME_FIELD_WIDTH) ||
				(this.team.get(player).getY() + changeY < -15) || (this.team.get(player).getY() + changeY - 10 > ApoSoccerConstants.GAME_FIELD_HEIGHT)) {
					return;
			}
			this.team.get(player).render(g, changeX, changeY);
			g.setColor(this.team.get(player).getColor());
			/*if (this.team.get(player).isBFemale()) {
				g.setColor(Color.red);
			}*/
			int w = g.getFontMetrics().stringWidth(this.names[player]);
			g.drawString(this.names[player], (int)(this.team.get(player).getX() + changeX - w/2), (int)(this.team.get(player).getY() + changeY + 15));
							
			if (player == this.playerText) {
				w = g.getFontMetrics().stringWidth(this.speakText);
				g.setColor(new Color(255, 255, 255, 128));
				g.fillRoundRect((int)(this.team.get(player).getX() + changeX - w/2 - 5), (int)(this.team.get(player).getY() + changeY - 47), w + 10, 20, 4, 4);
				g.setColor(Color.black);
				g.drawString(this.speakText, (int)(this.team.get(player).getX() + changeX - w/2), (int)(this.team.get(player).getY() + changeY - 30));	
			}
		}
		
		try {
			if (ApoSoccerConstants.B_DEBUG) {
				if (this.team.get(player).getLines().size() >= 0) {
					Stroke originalStroke = g.getStroke();
					ArrayList<ApoSoccerDebugLine> lines = this.team.get(player).getLines();
					for (int j = lines.size() - 1; j >= 0; j--) {
						g.setColor(lines.get(j).getColor());
						if (lines.get(j).isBCircle()) {
							g.drawOval(lines.get(j).getStartX() + changeX - lines.get(j).getWidth()/2, lines.get(j).getStartY() + changeY - lines.get(j).getWidth()/2, lines.get(j).getWidth(), lines.get(j).getWidth());
						} else {
							g.setStroke(new BasicStroke(lines.get(j).getWidth()));
							g.drawLine(lines.get(j).getStartX() + changeX, lines.get(j).getStartY() + changeY, lines.get(j).getEndX() + changeX, lines.get(j).getEndY() + changeY);
						}
					}
					g.setStroke(originalStroke);
				}
			}
		} catch (IndexOutOfBoundsException ex) {
		}
	}
	
}
