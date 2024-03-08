package apoSoccer.game;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.TexturePaint;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import org.apogames.ApoConstants;
import org.apogames.entity.ApoButton;
import org.apogames.help.ApoFileFilter;
import org.apogames.help.ApoHelp;
import org.apogames.image.ApoImage;

import beatMe.BeatMe;

import michakrams.ReferenzTeam;
import animals.Animals;
import doggyStyle.DoggyStyle;
import fcm.FCM;
import steto.Steto;
import apo.Apo;


import apoSoccer.ApoSoccerClassLoader;
import apoSoccer.ApoSoccerClassLoaderURL;
import apoSoccer.ApoSoccerComponent;
import apoSoccer.ApoSoccerConstants;
import apoSoccer.ApoSoccerProperties;
import apoSoccer.analysis.ApoSoccerAnalysisFrame;
import apoSoccer.entity.ApoBall;
import apoSoccer.entity.ApoForward;
import apoSoccer.entity.ApoGoalKeeper;
import apoSoccer.entity.ApoPlayer;
import apoSoccer.entity.ApoPlayerCloth;
import apoSoccer.entity.ApoSoccerEntity;
import apoSoccer.entity.ApoSoccerInformation;
import apoSoccer.entity.ApoTeam;
import apoSoccer.entityForAI.ApoSoccerAI;
import apoSoccer.entityForAI.ApoSoccerAIJNI;
import apoSoccer.entityForAI.ApoSoccerDebugLine;

/**
 * Die Klasse, die die Komponente darstellt und die einzelnen Teams verwaltet und somit
 * die ganze Logik enthaelt
 * @author Dirk Aporius
 *
 */
public class ApoSoccerGame extends ApoSoccerComponent {

	private static final long serialVersionUID = 1L;
	
	public static final String VERSION = "version 1.12";
	
	private final int FPS_COUNT = 2;
	
	/** A FileChooser */
	private JFileChooser fileChooser;
	/** A Class file filter */
	private ApoFileFilter fileFilter;
	/** Variable mithilfe derer Bilder geladen werden kann */
	private final ApoImage image = new ApoImage();
	
	private ApoSoccerAnalysisFrame analysisFrame;
	
	private boolean[] bPlayerHuman;
	
	private int curX, curY;
	private int curVecX, curVecY;
	/** Variable, die das Radar darstellt */
	private ApoSoccerRadar miniMap;
	/** die 2 Teams */
	private ApoTeam homeTeam, visitorTeam;
	/** das Objekt, dass den Ball darstellt */
	private ApoBall ball;
	/** die unterschiedlichen Bilder fuer das Spielfeld, Menu usw */
	private BufferedImage iMenu, iAnalysis, iRealMenu, iBackground, iEmblemOne, iEmblemTwo,
						  iEmblemOneLittle, iEmblemTwoLittle, iReplay, iPlayerFemale, iPlayerMan,
						  iLittleGameBackground, iGras;
	/** das Objekt was das Replay speichert und wiedergibt */
	private ApoSoccerReplay replay;
	/** ein Objekt was fuer das Simulieren eines Spiels ohne repaint zustaendig ist */
	private ApoSoccerSimulate simulate;
	/** Array mit Bildern von Haaren */
	private BufferedImage[] hairs;
	/** das Live-Ticker-Objekt */
	private ApoSoccerTicker ticker;
	/** Falls ein Button gedrueckt wurde, wird sein Funktionsstring hier drin gespeichert */
	private String curMouseFunction;

	private float littleWidth, littleHeight, delta;
	
	private boolean bGoal;
	
	private int goalMinute;
	
	private String goalTeamName, goalShoot;
	
	private int curHomeAIApplet, curVisitorAIApplet;
	
	private ApoSoccerTickerStrings tickerStrings;
	
	private ApoSoccerDebugFrame debugFrame;
	
	private boolean bPause;
	
	private int waitPause;
	
	private int curWaitPause;
	
	private ApoSoccerKey key;
	
	private int playerHuman;
	
	private boolean bNextStep;

	private ArrayList<Float> waitTimeRender;

	private ApoSoccerAIJNI apoSoccerAIJNI;
	
	private ApoSoccerProperties properties;
	
	private int replaySpeed;
	
	private ArrayList<String> debugText;
	/** Entity, die sich merkt, welches Objekt mit der Kamera gerade verfolgt werden soll */
	private ApoSoccerEntity centerEntity;
	/** ArrayList mit allen Spielern und dem Ball, f�r die grafische Ausgabe falls ein Spieler nicht zu sehen ist */
	private ArrayList<ApoSoccerInformation> visualInformation;
	
	public ApoSoccerGame(boolean mouse, boolean key, int wait_time_think, int wait_time_render) {
		super(mouse, key, wait_time_think, wait_time_render);
	}

	@Override
	public void init() {
		super.init();
		
		if (this.tickerStrings == null) {
			this.tickerStrings = new ApoSoccerTickerStrings();
		}
		
		if (this.waitTimeRender == null) {
			this.waitTimeRender = new ArrayList<Float>();
		} else {
			this.waitTimeRender.clear();
		}
		
		if ( !ApoConstants.B_APPLET ) {
			this.fileChooser = new JFileChooser();
			this.fileFilter = new ApoFileFilter( new String[] {"class", "dll"} );
			this.fileChooser.setCurrentDirectory( new File( System.getProperty("user.dir") + File.separator ) );
			this.fileChooser.setFileFilter( this.fileFilter );
		}
		
		if (this.key == null) {
			this.key = new ApoSoccerKey(this);
		}
		
		this.bPlayerHuman = new boolean[3];
		this.setHumanPlayer(ApoSoccerConstants.NO_HUMAN_PLAYER);
		this.playerHuman = 0;
		
		this.curVecX = 1;
		this.curVecY = 1;
		this.iGras = this.image.getPic("images/gras.png", false);
		BufferedImage iLeft = this.image.getPic("images/bande_left.png", false);
		BufferedImage iRight = this.image.getPic("images/bande_right.png", false);
		BufferedImage iUp = this.image.getPic("images/sdm_logo.png", false);
		BufferedImage iDown = this.image.getPic("images/farafin_logo.png", false);
		BufferedImage iUpLittle = this.image.getPic("images/sdm_logo_little.png", false);
		BufferedImage iUpUpLittle = this.image.getPic("images/qfin_logo_little.png", false);
		BufferedImage iDownDownLittle = this.image.getPic("images/acagamics_little.png", false);
		BufferedImage iDownLittle = this.image.getPic("images/igosys_logo_little.png", false);
		BufferedImage iMiddleLittle = this.image.getPic("images/sun_logo_little.png", false);

		super.setIBackground(super.getImages().getGameFieldBackgroundComplete(this.iGras, iLeft, iRight, iUp, iDown, iUpLittle, iDownLittle, iMiddleLittle, iUpUpLittle, iDownDownLittle, ApoSoccerConstants.COMPLETE_FIELD_WIDTH, ApoSoccerConstants.COMPLETE_FIELD_HEIGHT));
		
		if (this.iMenu == null) {
			this.iMenu = super.getImages().getMenuBackground(ApoSoccerConstants.GAME_WIDTH - ApoSoccerConstants.GAME_FIELD_WIDTH, ApoSoccerConstants.GAME_HEIGHT, iGras);
		}
		if (this.iReplay == null) {
			this.iReplay = super.getImages().getReplay(150, 40);
		}
		if (this.ticker == null) {
			this.ticker = new ApoSoccerTicker();
		} else {
			this.ticker.init();
		}
		this.ticker.setMenuText();
		if (this.simulate == null) {
			this.simulate = new ApoSoccerSimulate(this);
		}
		if (this.debugText == null) {
			this.debugText = new ArrayList<String>();
		}
		if (this.hairs == null) {
			this.hairs = new BufferedImage[ApoSoccerConstants.APO_HAIR + 1];
			ApoPlayerCloth cloth = new ApoPlayerCloth();
			this.hairs[ApoSoccerConstants.HAIR_WITHOUT] = null;
			BufferedImage iHairOne = this.image.getPic("images/hair_1.png", false);
			BufferedImage iHairTwo = this.image.getPic("images/hair_apo.png", false);

			BufferedImage iHairFemaleOne = this.image.getPic("images/hair_female_1.png", false);
			BufferedImage iHairFemaleTwo = this.image.getPic("images/hair_female_2.png", false);
			this.hairs[ApoSoccerConstants.HAIR_ONE_OGER] = iHairOne;
			this.hairs[ApoSoccerConstants.HAIR_ONE_BLUE] = cloth.getPlayerHair(this, iHairOne, Color.blue);
			this.hairs[ApoSoccerConstants.HAIR_ONE_RED] = cloth.getPlayerHair(this, iHairOne, Color.red);
			this.hairs[ApoSoccerConstants.HAIR_ONE_GRAY] = cloth.getPlayerHair(this, iHairOne, Color.gray);
			this.hairs[ApoSoccerConstants.HAIR_ONE_GREEN] = cloth.getPlayerHair(this, iHairOne, Color.green);
			this.hairs[ApoSoccerConstants.HAIR_ONE_YELLOW] = cloth.getPlayerHair(this, iHairOne, Color.yellow);
			this.hairs[ApoSoccerConstants.HAIR_TWO_BLUE] = cloth.getPlayerHair(this, iHairTwo, Color.blue);
			this.hairs[ApoSoccerConstants.HAIR_TWO_RED] = cloth.getPlayerHair(this, iHairTwo, Color.red);
			this.hairs[ApoSoccerConstants.HAIR_TWO_GRAY] = cloth.getPlayerHair(this, iHairTwo, Color.gray);
			this.hairs[ApoSoccerConstants.HAIR_TWO_GREEN] = cloth.getPlayerHair(this, iHairTwo, Color.green);
			this.hairs[ApoSoccerConstants.HAIR_TWO_YELLOW] = cloth.getPlayerHair(this, iHairTwo, Color.yellow);

			this.hairs[ApoSoccerConstants.HAIR_FEMALE_ONE_OGER] = iHairFemaleOne;
			this.hairs[ApoSoccerConstants.HAIR_FEMALE_ONE_BLUE] = cloth.getPlayerHair(this, iHairFemaleOne, Color.blue);
			this.hairs[ApoSoccerConstants.HAIR_FEMALE_ONE_RED] = cloth.getPlayerHair(this, iHairFemaleOne, Color.red);
			this.hairs[ApoSoccerConstants.HAIR_FEMALE_ONE_GRAY] = cloth.getPlayerHair(this, iHairFemaleOne, Color.gray);
			this.hairs[ApoSoccerConstants.HAIR_FEMALE_ONE_GREEN] = cloth.getPlayerHair(this, iHairFemaleOne, Color.green);
			this.hairs[ApoSoccerConstants.HAIR_FEMALE_ONE_YELLOW] = cloth.getPlayerHair(this, iHairFemaleOne, Color.yellow);
			this.hairs[ApoSoccerConstants.HAIR_FEMALE_ONE_BLACK] = cloth.getPlayerHair(this, iHairFemaleOne, Color.black);
			this.hairs[ApoSoccerConstants.HAIR_FEMALE_TWO_BLUE] = cloth.getPlayerHair(this, iHairFemaleTwo, Color.blue);
			this.hairs[ApoSoccerConstants.HAIR_FEMALE_TWO_RED] = cloth.getPlayerHair(this, iHairFemaleTwo, Color.red);
			this.hairs[ApoSoccerConstants.HAIR_FEMALE_TWO_GRAY] = cloth.getPlayerHair(this, iHairFemaleTwo, Color.gray);
			this.hairs[ApoSoccerConstants.HAIR_FEMALE_TWO_GREEN] = cloth.getPlayerHair(this, iHairFemaleTwo, Color.green);
			this.hairs[ApoSoccerConstants.HAIR_FEMALE_TWO_YELLOW] = cloth.getPlayerHair(this, iHairFemaleTwo, Color.yellow);

			this.hairs[ApoSoccerConstants.APO_HAIR] = iHairTwo;
		}
		
		this.miniMap = new ApoSoccerRadar( this, ApoSoccerConstants.GAME_FIELD_WIDTH/2 - ApoSoccerConstants.RADAR_WIDTH/2, ApoSoccerConstants.GAME_FIELD_HEIGHT - ApoSoccerConstants.RADAR_HEIGHT - 10, ApoSoccerConstants.RADAR_WIDTH, ApoSoccerConstants.RADAR_HEIGHT );
		
		Rectangle2D.Float gameField = new Rectangle2D.Float(0, 0, ApoSoccerConstants.FIELD_WIDTH, ApoSoccerConstants.FIELD_HEIGHT);
		
		if (this.ball == null) {
			this.ball = new ApoBall(image.getPic("images/ball.png", false), ApoSoccerConstants.FIELD_WIDTH/2, ApoSoccerConstants.FIELD_HEIGHT/2, 4, 4, ApoSoccerConstants.ANIMATION_TIME, gameField );
		}
		
		Rectangle2D.Float playerField = new Rectangle2D.Float(0, -5, ApoSoccerConstants.FIELD_WIDTH, ApoSoccerConstants.FIELD_HEIGHT + 10);
		
		this.iPlayerMan = this.image.getPic("images/player.png", false);
		this.iPlayerFemale = this.image.getPic("images/player_female.png", false);
		
		int radius = 5;
		if (this.homeTeam == null) {
			this.homeTeam = new ApoTeam(true);
			this.homeTeam.addPlayer(new ApoPlayer(this.iPlayerMan, ApoSoccerConstants.DEFENDER_ONE_LEFT.x, ApoSoccerConstants.DEFENDER_ONE_LEFT.y, radius, 4, ApoSoccerConstants.ANIMATION_TIME, playerField, this));
			this.homeTeam.addPlayer(new ApoPlayer(this.iPlayerMan, ApoSoccerConstants.DEFENDER_TWO_LEFT.x, ApoSoccerConstants.DEFENDER_TWO_LEFT.y, radius, 4, ApoSoccerConstants.ANIMATION_TIME, playerField, this));
			this.homeTeam.addPlayer(new ApoGoalKeeper(this.iPlayerMan, ApoSoccerConstants.GOALKEEPER_LEFT.x, ApoSoccerConstants.GOALKEEPER_LEFT.y, radius, 4, ApoSoccerConstants.ANIMATION_TIME, ApoSoccerConstants.LEFT_GOALKEEPER_REC, this));
			this.homeTeam.addPlayer(new ApoForward(this.iPlayerMan, ApoSoccerConstants.FORWARD_LEFT.x, ApoSoccerConstants.FORWARD_LEFT.y, radius, 4, ApoSoccerConstants.ANIMATION_TIME, ApoSoccerConstants.RIGHT_SIDE_REC, this));
			if (ApoConstants.B_APPLET) {
				this.homeTeam.setAi(null, new Apo(), "http://apo-games.de/apoSoccer/player/apo/", this);
			} else {
				this.homeTeam.setAi(null, new Apo(), System.getProperty("user.dir") + File.separator + "apo" + File.separator, this);
			}
		}
		if (this.visitorTeam == null) {
			this.visitorTeam = new ApoTeam(false);
			this.visitorTeam.addPlayer(new ApoPlayer(this.iPlayerMan, ApoSoccerConstants.DEFENDER_ONE_RIGHT.x, ApoSoccerConstants.DEFENDER_ONE_RIGHT.y, radius, 4, ApoSoccerConstants.ANIMATION_TIME, playerField, this));
			this.visitorTeam.addPlayer(new ApoPlayer(this.iPlayerMan, ApoSoccerConstants.DEFENDER_TWO_RIGHT.x, ApoSoccerConstants.DEFENDER_TWO_RIGHT.y, radius, 4, ApoSoccerConstants.ANIMATION_TIME, playerField, this));
			this.visitorTeam.addPlayer(new ApoGoalKeeper(this.iPlayerMan, ApoSoccerConstants.GOALKEEPER_RIGHT.x, ApoSoccerConstants.GOALKEEPER_RIGHT.y, radius, 4, ApoSoccerConstants.ANIMATION_TIME, ApoSoccerConstants.RIGHT_GOALKEEPER_REC, this));
			this.visitorTeam.addPlayer(new ApoForward(this.iPlayerMan, ApoSoccerConstants.FORWARD_RIGHT.x, ApoSoccerConstants.FORWARD_RIGHT.y, radius, 4, ApoSoccerConstants.ANIMATION_TIME, ApoSoccerConstants.LEFT_SIDE_REC, this));
			for (int i = 0; i < this.visitorTeam.getPlayers().size(); i++) {
				this.visitorTeam.getPlayer(i).setColor(Color.blue);
				this.visitorTeam.getPlayer(i).setStartLineOfSight(180);
			}
			if (ApoConstants.B_APPLET) {
				this.visitorTeam.setAi(null, new BeatMe(), "http://apo-games.de/apoSoccer/player/beatMe/", this);
			} else {
				this.visitorTeam.setAi(null, new BeatMe(), System.getProperty("user.dir") + File.separator + "beatMe" + File.separator, this);				
			}
		}
		this.goal();
		this.setPlayerToMiddle(this.homeTeam.getPlayer(ApoSoccerConstants.FORWARD), false);
		this.startPositions();
		
		this.replay = new ApoSoccerReplay();
		this.replay.init();
		this.replay.setBReplaySave(true);
		
		this.centerEntity = this.ball;
		
		if (this.visualInformation == null) {
			this.visualInformation = new ArrayList<ApoSoccerInformation>();
			for (int i = 0; i < this.homeTeam.getPlayers().size(); i++) {
				this.visualInformation.add(new ApoSoccerInformation(this.homeTeam.getPlayer(i)));
			}
			for (int i = 0; i < this.visitorTeam.getPlayers().size(); i++) {
				this.visualInformation.add(new ApoSoccerInformation(this.visitorTeam.getPlayer(i)));
			}
			this.visualInformation.add(new ApoSoccerInformation(this.ball));
		}
		
		this.makeBackgroundWithEmblem();
		
		// Die Buttons werden erstellt
		if (super.getButtons() == null) {
			super.setButtons(new ApoButton[30]);
			
			String text = "X";
			String function = "quit";
			int width = 40;
			int height = 40;
			int x = this.getWidth() - 5 - width;
			int y = this.getHeight() - 1 * height - 1 * 5;
			super.getButtons()[0] = new ApoButton( super.getImages().getButtonImage( width * 3, height, text, Color.black, Color.red, Color.white, 50 ), x, y, width, height, function );
			
			text = "start";
			function = "play";
			width = 40;
			height = 40;
			x = (ApoSoccerConstants.GAME_FIELD_WIDTH - ApoSoccerConstants.ANALYSIS_WIDTH) / 2 + ApoSoccerConstants.ANALYSIS_WIDTH/2 - width/2;
			y = (ApoSoccerConstants.GAME_FIELD_HEIGHT - ApoSoccerConstants.ANALYSIS_HEIGHT) / 2 + ApoSoccerConstants.ANALYSIS_HEIGHT - height - 10;
			super.getButtons()[1] = new ApoButton( super.getImages().getButtonImage( width * 3, height, text, Color.black, Color.green, Color.white, 50 ), x, y, width, height, function );
			
			text = "stop";
			function = "stop";
			width = 40;
			height = 40;
			x = this.getWidth() + 5 - ApoSoccerConstants.GAME_WIDTH + ApoSoccerConstants.GAME_FIELD_WIDTH;
			y = this.getHeight() - 1 * height - 1 * 5;
			super.getButtons()[2] = new ApoButton( super.getImages().getButtonImage( width * 3, height, text, Color.black, Color.red, Color.white, 50 ), x, y, width, height, function );
			super.getButtons()[2].setBVisible(false);
			
			text = "replay play";
			function = "replayPlay";
			width = 100;
			height = 20;
			x = (ApoSoccerConstants.GAME_FIELD_WIDTH - ApoSoccerConstants.ANALYSIS_WIDTH) / 2 + 10;
			y = (ApoSoccerConstants.GAME_FIELD_HEIGHT - ApoSoccerConstants.ANALYSIS_HEIGHT) / 2 + ApoSoccerConstants.ANALYSIS_HEIGHT/2 + 30;
			super.getButtons()[3] = new ApoButton( super.getImages().getButtonImage( width * 3, height, text, Color.black, Color.white, Color.white, 5 ), x, y, width, height, function );
			super.getButtons()[3].setBVisible(false);
			
			text = "replay save";
			function = "replaySave";
			width = 100;
			height = 20;
			x = (ApoSoccerConstants.GAME_FIELD_WIDTH - ApoSoccerConstants.ANALYSIS_WIDTH) / 2 + ApoSoccerConstants.ANALYSIS_WIDTH - width - 10;
			y = (ApoSoccerConstants.GAME_FIELD_HEIGHT - ApoSoccerConstants.ANALYSIS_HEIGHT) / 2 + ApoSoccerConstants.ANALYSIS_HEIGHT/2 + 30;
			super.getButtons()[4] = new ApoButton( super.getImages().getButtonImage( width * 3, height, text, Color.black, Color.white, Color.white, 5 ), x, y, width, height, function );
			super.getButtons()[4].setBVisible(false);
			
			text = "replay load";
			function = "replayLoad";
			width = 100;
			height = 20;
			x = (ApoSoccerConstants.GAME_FIELD_WIDTH - ApoSoccerConstants.ANALYSIS_WIDTH) / 2 + ApoSoccerConstants.ANALYSIS_WIDTH/2 - width/2;
			y = (ApoSoccerConstants.GAME_FIELD_HEIGHT - ApoSoccerConstants.ANALYSIS_HEIGHT) / 2 + ApoSoccerConstants.ANALYSIS_HEIGHT/2 + 30;
			super.getButtons()[5] = new ApoButton( super.getImages().getButtonImage( width * 3, height, text, Color.black, Color.white, Color.white, 5 ), x, y, width, height, function );
			
			text = "load Home AI";
			function = "loadHomeAI";
			width = 105;
			height = 20;
			x = (ApoSoccerConstants.GAME_FIELD_WIDTH - ApoSoccerConstants.ANALYSIS_WIDTH) / 2 + ApoSoccerConstants.ANALYSIS_WIDTH*1/4 - width/2;
			y = (ApoSoccerConstants.GAME_FIELD_HEIGHT - ApoSoccerConstants.ANALYSIS_HEIGHT) / 2 + ApoSoccerConstants.ANALYSIS_HEIGHT/2 - 90;
			super.getButtons()[6] = new ApoButton( super.getImages().getButtonImage( width * 3, height, text, Color.black, Color.white, Color.white, 5 ), x, y, width, height, function );
			
			text = "load Visitor AI";
			function = "loadVisitorAI";
			width = 105;
			height = 20;
			x = (ApoSoccerConstants.GAME_FIELD_WIDTH - ApoSoccerConstants.ANALYSIS_WIDTH) / 2 + ApoSoccerConstants.ANALYSIS_WIDTH*3/4 - width/2;
			y = (ApoSoccerConstants.GAME_FIELD_HEIGHT - ApoSoccerConstants.ANALYSIS_HEIGHT) / 2 + ApoSoccerConstants.ANALYSIS_HEIGHT/2 - 90;
			super.getButtons()[7] = new ApoButton( super.getImages().getButtonImage( width * 3, height, text, Color.black, Color.white, Color.white, 5 ), x, y, width, height, function );
			
			text = "simulate Game";
			function = "playGameWithoutRepaint";
			width = 200;
			height = 20;
			x = (ApoSoccerConstants.GAME_FIELD_WIDTH - ApoSoccerConstants.ANALYSIS_WIDTH) / 2 + ApoSoccerConstants.ANALYSIS_WIDTH/2 - width/2;
			y = (ApoSoccerConstants.GAME_FIELD_HEIGHT - ApoSoccerConstants.ANALYSIS_HEIGHT) / 2 + ApoSoccerConstants.ANALYSIS_HEIGHT/2 + 90;
			super.getButtons()[8] = new ApoButton( super.getImages().getButtonImage( width * 3, height, text, Color.black, Color.white, Color.white, 5 ), x, y, width, height, function );
			
			text = "Menu";
			function = "menu";
			width = 60;
			height = 20;
			x = (ApoSoccerConstants.GAME_FIELD_WIDTH - ApoSoccerConstants.ANALYSIS_WIDTH) / 2 + ApoSoccerConstants.ANALYSIS_WIDTH/2 - width/2;
			y = (ApoSoccerConstants.GAME_FIELD_HEIGHT - ApoSoccerConstants.ANALYSIS_HEIGHT) / 2 + ApoSoccerConstants.ANALYSIS_HEIGHT - height - 5;
			super.getButtons()[9] = new ApoButton( super.getImages().getButtonImage( width * 3, height, text, Color.black, Color.white, Color.white, 5 ), x, y, width, height, function );
			super.getButtons()[9].setBVisible(false);
			
			text = "on";
			function = "soundOn";
			width = 50;
			height = 20;
			x = (ApoSoccerConstants.GAME_FIELD_WIDTH - ApoSoccerConstants.ANALYSIS_WIDTH) / 2 + ApoSoccerConstants.ANALYSIS_WIDTH/2 - width/2;
			y = (ApoSoccerConstants.GAME_FIELD_HEIGHT - ApoSoccerConstants.ANALYSIS_HEIGHT) / 2 + ApoSoccerConstants.ANALYSIS_HEIGHT/2 - 30;
			super.getButtons()[10] = new ApoButton( super.getImages().getButtonImage( width * 3, height, text, Color.black, Color.white, Color.white, 5 ), x, y, width, height, function );
			
			text = "off";
			function = "soundOff";
			width = 50;
			height = 20;
			x = (ApoSoccerConstants.GAME_FIELD_WIDTH - ApoSoccerConstants.ANALYSIS_WIDTH) / 2 + ApoSoccerConstants.ANALYSIS_WIDTH/2 - width/2;
			y = (ApoSoccerConstants.GAME_FIELD_HEIGHT - ApoSoccerConstants.ANALYSIS_HEIGHT) / 2 + ApoSoccerConstants.ANALYSIS_HEIGHT/2 - 30;
			super.getButtons()[11] = new ApoButton( super.getImages().getButtonImage( width * 3, height, text, Color.black, Color.white, Color.white, 5 ), x, y, width, height, function );
			super.getButtons()[11].setBVisible(false);
			
			text = "change";
			function = "debugChange";
			width = 60;
			height = 20;
			x = (ApoSoccerConstants.GAME_FIELD_WIDTH - ApoSoccerConstants.ANALYSIS_WIDTH) / 2 + 75 - width/2;
			y = (ApoSoccerConstants.GAME_FIELD_HEIGHT - ApoSoccerConstants.ANALYSIS_HEIGHT) / 2 + ApoSoccerConstants.ANALYSIS_HEIGHT - height - 10;
			super.getButtons()[12] = new ApoButton( super.getImages().getButtonImage( width * 3, height, text, Color.black, Color.white, Color.white, 5 ), x, y, width, height, function );
			super.getButtons()[12].setBVisible(false);

			text = "<";
			function = "previousAIHomeApplet";
			width = 20;
			height = 20;
			x = (ApoSoccerConstants.GAME_FIELD_WIDTH - ApoSoccerConstants.ANALYSIS_WIDTH) / 2 + ApoSoccerConstants.ANALYSIS_WIDTH*1/4 - width - 5;
			y = (ApoSoccerConstants.GAME_FIELD_HEIGHT - ApoSoccerConstants.ANALYSIS_HEIGHT) / 2 + ApoSoccerConstants.ANALYSIS_HEIGHT/2 - 90;
			super.getButtons()[13] = new ApoButton( super.getImages().getButtonImage( width * 3, height, text, Color.black, Color.white, Color.white, 5 ), x, y, width, height, function );

			text = ">";
			function = "nextAIHomeApplet";
			width = 20;
			height = 20;
			x = (ApoSoccerConstants.GAME_FIELD_WIDTH - ApoSoccerConstants.ANALYSIS_WIDTH) / 2 + ApoSoccerConstants.ANALYSIS_WIDTH*1/4 + 5;
			y = (ApoSoccerConstants.GAME_FIELD_HEIGHT - ApoSoccerConstants.ANALYSIS_HEIGHT) / 2 + ApoSoccerConstants.ANALYSIS_HEIGHT/2 - 90;
			super.getButtons()[14] = new ApoButton( super.getImages().getButtonImage( width * 3, height, text, Color.black, Color.white, Color.white, 5 ), x, y, width, height, function );

			text = "<";
			function = "previousAIVisitorApplet";
			width = 20;
			height = 20;
			x = (ApoSoccerConstants.GAME_FIELD_WIDTH - ApoSoccerConstants.ANALYSIS_WIDTH) / 2 + ApoSoccerConstants.ANALYSIS_WIDTH*3/4 - width - 5;
			y = (ApoSoccerConstants.GAME_FIELD_HEIGHT - ApoSoccerConstants.ANALYSIS_HEIGHT) / 2 + ApoSoccerConstants.ANALYSIS_HEIGHT/2 - 90;
			super.getButtons()[15] = new ApoButton( super.getImages().getButtonImage( width * 3, height, text, Color.black, Color.white, Color.white, 5 ), x, y, width, height, function );

			text = ">";
			function = "nextAIVisitorApplet";
			width = 20;
			height = 20;
			x = (ApoSoccerConstants.GAME_FIELD_WIDTH - ApoSoccerConstants.ANALYSIS_WIDTH) / 2 + ApoSoccerConstants.ANALYSIS_WIDTH*3/4 + 5;
			y = (ApoSoccerConstants.GAME_FIELD_HEIGHT - ApoSoccerConstants.ANALYSIS_HEIGHT) / 2 + ApoSoccerConstants.ANALYSIS_HEIGHT/2 - 90;
			super.getButtons()[16] = new ApoButton( super.getImages().getButtonImage( width * 3, height, text, Color.black, Color.white, Color.white, 5 ), x, y, width, height, function );

			text = "change";
			function = "littleChange";
			width = 60;
			height = 20;
			x = (ApoSoccerConstants.GAME_FIELD_WIDTH + ApoSoccerConstants.ANALYSIS_WIDTH) / 2 - width/2 - 75;
			y = (ApoSoccerConstants.GAME_FIELD_HEIGHT - ApoSoccerConstants.ANALYSIS_HEIGHT) / 2 + ApoSoccerConstants.ANALYSIS_HEIGHT - height - 10;
			super.getButtons()[17] = new ApoButton( super.getImages().getButtonImage( width * 3, height, text, Color.black, Color.white, Color.white, 5 ), x, y, width, height, function );
			super.getButtons()[17].setBVisible(false);

			text = "pause";
			function = "pause";
			width = 50;
			height = 40;
			x = this.getWidth() + 5 + width - ApoSoccerConstants.GAME_WIDTH + ApoSoccerConstants.GAME_FIELD_WIDTH;
			y = this.getHeight() - 1 * height - 1 * 5;
			super.getButtons()[18] = new ApoButton( super.getImages().getButtonImage( width * 3, height, text, Color.black, Color.red, Color.white, 50 ), x, y, width, height, function );
			super.getButtons()[18].setBVisible(false);
			
			text = "x";
			function = "playerXOne";
			width = 30;
			height = 20;
			x = (ApoSoccerConstants.GAME_FIELD_WIDTH - ApoSoccerConstants.ANALYSIS_WIDTH) / 2 + ApoSoccerConstants.ANALYSIS_WIDTH*1/4 - width/2;
			y = (ApoSoccerConstants.GAME_FIELD_HEIGHT - ApoSoccerConstants.ANALYSIS_HEIGHT) / 2 + ApoSoccerConstants.ANALYSIS_HEIGHT/2 - 70 + 5;
			super.getButtons()[19] = new ApoButton( super.getImages().getButtonImage( width * 3, height, text, Color.black, Color.white, Color.white, 5 ), x, y, width, height, function );

			text = "x";
			function = "playerXTwo";
			width = 30;
			height = 20;
			x = (ApoSoccerConstants.GAME_FIELD_WIDTH - ApoSoccerConstants.ANALYSIS_WIDTH) / 2 + ApoSoccerConstants.ANALYSIS_WIDTH*3/4 - width/2;
			y = (ApoSoccerConstants.GAME_FIELD_HEIGHT - ApoSoccerConstants.ANALYSIS_HEIGHT) / 2 + ApoSoccerConstants.ANALYSIS_HEIGHT/2 - 70 + 5;
			super.getButtons()[20] = new ApoButton( super.getImages().getButtonImage( width * 3, height, text, Color.black, Color.white, Color.white, 5 ), x, y, width, height, function );

			text = "analysis";
			function = "analysis";
			width = 60;
			height = 20;
			x = (ApoSoccerConstants.GAME_FIELD_WIDTH - ApoSoccerConstants.ANALYSIS_WIDTH) / 2 + ApoSoccerConstants.ANALYSIS_WIDTH/2 - width/2;
			y = (ApoSoccerConstants.GAME_FIELD_HEIGHT - ApoSoccerConstants.ANALYSIS_HEIGHT) / 2 + ApoSoccerConstants.ANALYSIS_HEIGHT - height - 55;
			super.getButtons()[21] = new ApoButton( super.getImages().getButtonImage( width * 3, height, text, Color.black, Color.white, Color.white, 5 ), x, y, width, height, function );
			
			text = "<";
			function = "previousPauseWait";
			width = 20;
			height = 20;
			x = ApoSoccerConstants.GAME_FIELD_WIDTH/2 - width - 60;
			y = 35;
			super.getButtons()[22] = new ApoButton( super.getImages().getButtonImage( width * 3, height, text, Color.black, Color.white, Color.white, 5 ), x, y, width, height, function );

			text = ">";
			function = "nextPauseWait";
			width = 20;
			height = 20;
			x = ApoSoccerConstants.GAME_FIELD_WIDTH/2 + 60;
			y = 35;
			super.getButtons()[23] = new ApoButton( super.getImages().getButtonImage( width * 3, height, text, Color.black, Color.white, Color.white, 5 ), x, y, width, height, function );

			text = ">";
			function = "replayPlayButton";
			width = 20;
			height = 20;
			x = ApoSoccerConstants.GAME_FIELD_WIDTH/2 + 3;
			y = 35;
			super.getButtons()[24] = new ApoButton( super.getImages().getButtonReplayPlay( width * 3, height, Color.black, Color.white, Color.white), x, y, width, height, function );

			text = "||";
			function = "replayPauseButton";
			width = 20;
			height = 20;
			x = ApoSoccerConstants.GAME_FIELD_WIDTH/2 - width * 1 - 2 * 1;
			y = 35;
			super.getButtons()[25] = new ApoButton( super.getImages().getButtonReplayPause( width * 3, height, Color.black, Color.white, Color.white), x, y, width, height, function );

			text = "||";
			function = "replayRewindButton";
			width = 20;
			height = 20;
			x = ApoSoccerConstants.GAME_FIELD_WIDTH/2 - width * 2 - 2 * 1 - 5 * 1;
			y = 35;
			super.getButtons()[26] = new ApoButton( super.getImages().getButtonReplayRewind( width * 3, height, Color.black, Color.white, Color.white), x, y, width, height, function );

			text = "||";
			function = "replayRewindFastButton";
			width = 20;
			height = 20;
			x = ApoSoccerConstants.GAME_FIELD_WIDTH/2 - width * 3 - 2 * 1 - 5 * 2;
			y = 35;
			super.getButtons()[27] = new ApoButton( super.getImages().getButtonReplayRewindFast( width * 3, height, Color.black, Color.white, Color.white), x, y, width, height, function );

			text = "||";
			function = "replayForwardButton";
			width = 20;
			height = 20;
			x = ApoSoccerConstants.GAME_FIELD_WIDTH/2 + width * 1 + 3 * 1 + 5 * 1;
			y = 35;
			super.getButtons()[28] = new ApoButton( super.getImages().getButtonReplayForward( width * 3, height, Color.black, Color.white, Color.white), x, y, width, height, function );

			text = "||";
			function = "replayForwardFastButton";
			width = 20;
			height = 20;
			x = ApoSoccerConstants.GAME_FIELD_WIDTH/2 + width * 2 + 3 * 1 + 5 * 2;
			y = 35;
			super.getButtons()[29] = new ApoButton( super.getImages().getButtonReplayForwardFast( width * 3, height, Color.black, Color.white, Color.white), x, y, width, height, function );

		}
		this.curHomeAIApplet = 0;
		this.curVisitorAIApplet = 6;
		
		this.centerCameraWithEntity(this.centerEntity);
		if (this.iRealMenu == null) {
			super.setBMenu(false);
			BufferedImage iBackground = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( ApoSoccerConstants.GAME_WIDTH, ApoSoccerConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB );
			Graphics2D g	= (Graphics2D)iBackground.getGraphics();
			this.render(g);
			g.dispose();
			this.iRealMenu = super.getImages().getMenu(iBackground, ApoSoccerConstants.GAME_WIDTH, ApoSoccerConstants.GAME_HEIGHT);
			super.setBMenu(true);
		}

		if (!ApoConstants.B_APPLET) {
			try {
				this.apoSoccerAIJNI = new ApoSoccerAIJNI();
				ApoSoccerAIJNI.loadMultiplexer(System.getProperty("user.dir") + File.separator + ApoSoccerConstants.MULTIPLEXERDLLNAME);
			} catch (Exception ex) {
				this.apoSoccerAIJNI = null;
			} catch (Error er) {
				this.apoSoccerAIJNI = null;
			}
		}
		this.loadProperties();
		
		if (ApoConstants.B_APPLET) {
			this.setButtonVisible(ApoSoccerConstants.BUTTON_MENU_APPLET);
		} else {
			this.setButtonVisible(ApoSoccerConstants.BUTTON_MENU);
		}
	}
	
	public void addDebugText(String text) {
		this.debugText.add(text);
	}
	
	public boolean clearDebugText() {
		this.debugText.clear();
		return true;
	}
	
	private void makeDebugAndAnalysisFrame() {
		if ( !ApoConstants.B_APPLET ) {
			this.debugFrame = new ApoSoccerDebugFrame(this);
			this.debugFrame.setPreferredSize(new Dimension(800, 400));
			this.debugFrame.setVisible(ApoSoccerConstants.B_DEBUG);
		}

	}
	
	public void loadProperties() {
		if (!ApoConstants.B_APPLET) {
			this.properties = new ApoSoccerProperties();
			this.properties.loadProperties();
		}
		if (this.properties != null) {
			if (this.properties.getHomeAIPath() != null) {
				this.loadPlayer(true, this.properties.getHomeAIPath());
			}
			if (this.properties.getVisitorAIPath() != null) {
				this.loadPlayer(false, this.properties.getVisitorAIPath());
			}
			if (this.properties.isBBirdEyeView()) {
				this.littleGameChange();
			}
			if (this.properties.isBDebug()) {
				this.debugChange();
			}
		}
	}

	public ApoSoccerKey getKey() {
		return this.key;
	}

	protected boolean[] getBPlayerHuman() {
		return this.bPlayerHuman;
	}

	public ApoSoccerDebugFrame getDebugFrame() {
		return this.debugFrame;
	}

	/**
	 * gibt das kleine Bild zurueck, wenn das SPiel nur aus der Vogelperspektive betrachtet werden soll
	 * @return gibt das kleine Bild zurueck, wenn das SPiel nur aus der Vogelperspektive betrachtet werden soll
	 */
	private BufferedImage getLittleGameBackground() {
		BufferedImage iLittleGameBackground = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( ApoSoccerConstants.GAME_FIELD_WIDTH, ApoSoccerConstants.GAME_FIELD_HEIGHT, BufferedImage.TYPE_INT_RGB );
		Graphics2D g	= (Graphics2D)iLittleGameBackground.getGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, iLittleGameBackground.getWidth(), iLittleGameBackground.getHeight());
		
		TexturePaint paint = new TexturePaint(this.iGras, new Rectangle2D.Float(0, 0, this.iGras.getWidth(), this.iGras.getHeight()));
		Paint orgPaint = g.getPaint();
		g.setPaint(paint);
		g.fill(new Rectangle2D.Float(0, 0, iLittleGameBackground.getWidth(), iLittleGameBackground.getHeight()));
		
		g.setPaint(orgPaint);
				
		int width = iLittleGameBackground.getWidth();
		int height = iLittleGameBackground.getHeight();
		
		Image image = null;
		if ( (float)this.iBackground.getWidth()/(float)iLittleGameBackground.getWidth() > (float)this.iBackground.getHeight()/(float)iLittleGameBackground.getHeight() ) {
			this.delta = (float)this.iBackground.getWidth()/(float)width;
			image = this.iBackground.getScaledInstance( width, (int)( this.iBackground.getHeight()/this.delta ), Image.SCALE_SMOOTH );
		} else {
			this.delta = (float)this.iBackground.getHeight()/(float)height;
			image = this.iBackground.getScaledInstance( (int)( this.iBackground.getWidth()/this.delta ), height, Image.SCALE_SMOOTH );
		}
		this.littleWidth = ( width - image.getWidth(null) ) / 2;
		this.littleHeight = ( height - image.getHeight(null) ) / 2;
		g.drawImage( image, (int)this.littleWidth, (int)this.littleHeight, null );
		
		g.dispose();
		
		return iLittleGameBackground;
	}
	
	/**
	 * gibt das Bild mit dem weiblichen Spieler zurueck
	 * @return gibt das Bild mit dem weiblichen Spieler zurueck
	 */
	public BufferedImage getIPlayerFemale() {
		return this.iPlayerFemale;
	}
	
	/**
	 * gibt das Bild mit dem maennlichen Spieler zurueck
	 * @return gibt das Bild mit dem maennlichen Spieler zurueck
	 */
	public BufferedImage getIPlayerMan() {
		return this.iPlayerMan;
	}

	private boolean setHumanPlayer(int player) {
		for (int i = 0; i < this.bPlayerHuman.length; i++) {
			if (i == player) {
				this.bPlayerHuman[i] = true;
			} else {
				this.bPlayerHuman[i] = false;
			}
		}
		return true;
	}
	
	/**
	 * gibt ein Array mit den Bildern der Haare zurueck
	 * @return gibt ein Array mit den Bildern der Haare zurueck
	 */
	public BufferedImage[] getHairs() {
		return this.hairs;
	}

	/**
	 * wird beim Laden einer KI aufgerufen
	 * und packt, falls es Wappen fuer die Teams gibt, laedt er die Wappen
	 * und packt sie auf das Spielfeld
	 */
	private void makeBackgroundWithEmblem() {
		if (this.iBackground == null) {
			this.iBackground = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( super.getIBackground().getWidth(), super.getIBackground().getHeight(), BufferedImage.TYPE_INT_RGB );
		}
		Graphics2D g = (Graphics2D)this.iBackground.getGraphics();
		
		g.drawImage(super.getIBackground(), 0, 0, null);
		if (this.homeTeam.getEmblem() != null) {
			this.iEmblemOne = this.image.getPic(this.homeTeam.getEmblem(), true);
			if (this.iEmblemOne != null) {
				this.iEmblemOneLittle = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( 75, 75, BufferedImage.TRANSLUCENT );
				Graphics2D gLittle = (Graphics2D)this.iEmblemOneLittle.getGraphics();
				gLittle.drawImage(this.iEmblemOne.getScaledInstance(75, 75, BufferedImage.SCALE_SMOOTH), 0, 0, null);
				gLittle.dispose();
				g.setComposite( AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f) );
				g.drawImage( iEmblemOne, ApoSoccerConstants.BOARD_WIDTH + (ApoSoccerConstants.FIELD_WIDTH / 2 - iEmblemOne.getWidth() )/2, ApoSoccerConstants.BOARD_HEIGHT + ApoSoccerConstants.FIELD_HEIGHT/2 - iEmblemOne.getHeight()/2, null);
			}
		} else {
			this.iEmblemOne = null;
			this.iEmblemOneLittle = null;
		}
		if (this.visitorTeam.getEmblem() != null) {
			this.iEmblemTwo = this.image.getPic(this.visitorTeam.getEmblem(), true);
			if (iEmblemTwo != null) {
				this.iEmblemTwoLittle = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( 75, 75, BufferedImage.TRANSLUCENT );
				Graphics2D gLittle = (Graphics2D)this.iEmblemTwoLittle.getGraphics();
				gLittle.drawImage(this.iEmblemTwo.getScaledInstance(75, 75, BufferedImage.SCALE_SMOOTH), 0, 0, null);
				gLittle.dispose();
				g.setComposite( AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f) );
				g.drawImage( iEmblemTwo, ApoSoccerConstants.BOARD_WIDTH + ApoSoccerConstants.FIELD_WIDTH / 2 + (ApoSoccerConstants.FIELD_WIDTH / 2 - iEmblemTwo.getWidth() )/2, ApoSoccerConstants.BOARD_HEIGHT + ApoSoccerConstants.FIELD_HEIGHT/2 - iEmblemTwo.getHeight()/2, null);
			}
		} else {
			this.iEmblemTwo = null;
			this.iEmblemTwoLittle = null;
		}
		g.dispose();
		this.centerCameraWithEntity(this.centerEntity);
		super.setBMenu(false);
		BufferedImage iBackground = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( ApoSoccerConstants.GAME_WIDTH, ApoSoccerConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB );
		g	= (Graphics2D)iBackground.getGraphics();
		this.render(g);
		g.dispose();
		this.iRealMenu = super.getImages().getMenu(iBackground, ApoSoccerConstants.GAME_WIDTH, ApoSoccerConstants.GAME_HEIGHT);
		super.setBMenu(true);
	}
	
	/**
	 * gibt das Team zurueck, welches in der ersten Halbzeit von Links nach Rechts spielt
	 * @return gibt das Team zurueck, welches in der ersten Halbzeit von Links nach Rechts spielt
	 */
	public ApoTeam getHomeTeam() {
		return this.homeTeam;
	}

	/**
	 * setzt das Team, was in der ersten Halbzeit von Links nach Rechts spielt, auf den uebergebenen Wert
	 * @param homeTeam : neues HomeTeam
	 */
	public void setHomeTeam(ApoTeam homeTeam) {
		this.homeTeam = homeTeam;
	}

	/**
	 * gibt das Team zurueck, welches in der ersten Halbzeit von Rechts nach Links spielt
	 * @return gibt das Team zurueck, welches in der ersten Halbzeit von Rechts nach Links spielt
	 */
	public ApoTeam getVisitorTeam() {
		return this.visitorTeam;
	}

	/**
	 * setzt das Team, was in der ersten Halbzeit von Rechts nach Links spielt, auf den uebergebenen Wert
	 * @param visitorTeam : neues VisitorTeam
	 */
	public void setVisitorTeam(ApoTeam visitorTeam) {
		this.visitorTeam = visitorTeam;
	}

	/**
	 * gibt den Ball zurueck
	 * @return gibt den Ball zurueck
	 */
	public ApoBall getBall() {
		return this.ball;
	}

	public ApoSoccerReplay getReplay() {
		return this.replay;
	}

	@Override
	public void setButtonFunction(String function) {
		if (this.curMouseFunction != null) {
			return;
		}
		this.curMouseFunction = function;
	}
	
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		if ((!this.isBStartGame()) || (!ApoSoccerConstants.B_DEBUG)) {
			return;
		}
		int x = e.getX();
		int y = e.getY();
		for (int i = this.visualInformation.size() - 1; i >= 0; i--) {
			if (this.visualInformation.get(i).isOver(x, y, -this.curX + ApoSoccerConstants.BOARD_WIDTH, -this.curY + ApoSoccerConstants.BOARD_HEIGHT)) {
				this.centerEntity = this.visualInformation.get(i).getEntity();
				return;
			}
		}

	}
	
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		if (this.bPause) {
			if (e.getX() < ApoSoccerConstants.GAME_FIELD_WIDTH) {
				this.bNextStep = true;
			}
		}
	}
	
	private void thinkMouseButton() {
		if (this.curMouseFunction == null) {
			return;
		}
		String function = this.curMouseFunction;
		this.curMouseFunction = null;
		if ((this.analysisFrame != null) && (this.analysisFrame.isBAnalysisTable())) {
			return;
		} else if (function.equals("quit")) {
			if (this.properties != null) {
				this.properties.setBBirdEyeView(ApoSoccerConstants.B_LITTLE_GAME);
				this.properties.setBDebug(ApoSoccerConstants.B_DEBUG);
				this.properties.writeProperties();
			}
			System.exit(0);
		} else if (function.equals("play")) {
			this.startGame();
		} else if (function.equals("stop")) {
			this.stopGame();
		} else if (function.equals("pause")) {
			this.bPause = !this.bPause;
			if (this.bPause) {
				this.bNextStep = false;
				this.setButtonVisible(ApoSoccerConstants.BUTTON_GAME_PAUSE);
			} else {
				if (this.replay.isBReplayPlay()) {
					this.setButtonVisible(ApoSoccerConstants.BUTTON_REPLAY);					
				} else {
					this.setButtonVisible(ApoSoccerConstants.BUTTON_GAME);
				}
			}
		} else if (function.equals("replayPlay")) {
			this.startReplay();
		} else if (function.equals("replaySave")) {
			this.replay.saveReplay();
		} else if (function.equals("replayLoad")) {
			if (this.replay.loadReplay()) {
				super.getButtons()[3].setBVisible(true);
			}
		} else if (function.equals("loadHomeAI")) {
			this.loadPlayer(true, null);
		} else if (function.equals("loadVisitorAI")) {
			this.loadPlayer(false, null);
		} else if (function.equals("playGameWithoutRepaint")) {
			this.playGameWithoutRepaint();
		} else if (function.equals("menu")) {
			this.setMenu();
		} else if (function.equals("soundOn")) {
			if (ApoSoccerConstants.B_MUSIC) {
				super.setBSound(true);
				this.homeTeam.setSpeechOn();
				this.visitorTeam.setSpeechOn();
				if (ApoConstants.B_APPLET) {
					this.setButtonVisible(ApoSoccerConstants.BUTTON_MENU_APPLET);
				} else {
					this.setButtonVisible(ApoSoccerConstants.BUTTON_MENU);
				}
			}
		} else if (function.equals("soundOff")) {
			super.setBSound(false);
			if (ApoConstants.B_APPLET) {
				this.setButtonVisible(ApoSoccerConstants.BUTTON_MENU_APPLET);
			} else {
				this.setButtonVisible(ApoSoccerConstants.BUTTON_MENU);
			}
		} else if (function.equals("debugChange")) {
			this.debugChange();
		} else if (function.equals("littleChange")) {
			this.littleGameChange();
		} else if (function.equals("previousAIVisitorApplet")) {
			this.curVisitorAIApplet--;
			if (this.curVisitorAIApplet < 0) {
				this.curVisitorAIApplet = 6;
			}
			this.loadAI(true, this.curVisitorAIApplet);
		} else if (function.equals("nextAIVisitorApplet")) {
			this.curVisitorAIApplet++;
			if (this.curVisitorAIApplet > 6) {
				this.curVisitorAIApplet = 0;
			}
			this.loadAI(true, this.curVisitorAIApplet);
		} else if (function.equals("previousAIHomeApplet")) {
			this.curHomeAIApplet--;
			if (this.curHomeAIApplet < 0) {
				this.curHomeAIApplet = 6;
			}
			this.loadAI(false, this.curHomeAIApplet);
		} else if (function.equals("nextAIHomeApplet")) {
			this.curHomeAIApplet++;
			if (this.curHomeAIApplet > 6) {
				this.curHomeAIApplet = 0;
			}
			this.loadAI(false, this.curHomeAIApplet);
		} else if (function.equals("playerXOne")) {
			for (int i = 0; i < this.homeTeam.getPlayers().size(); i++) {
				this.homeTeam.getPlayer(i).setBPlayerHuman(false);
				this.visitorTeam.getPlayer(i).setBPlayerHuman(false);
			}
			if (this.playerHuman != 1) {
				this.homeTeam.getPlayer(ApoSoccerConstants.FORWARD).setBPlayerHuman(true);
				this.playerHuman = 1;
				this.setHumanPlayer(ApoSoccerConstants.ONE_HUMAN_PLAYER);
			} else {
				this.playerHuman = 0;
				this.setHumanPlayer(ApoSoccerConstants.NO_HUMAN_PLAYER);
			}
		} else if (function.equals("playerXTwo")) {
			for (int i = 0; i < this.homeTeam.getPlayers().size(); i++) {
				this.homeTeam.getPlayer(i).setBPlayerHuman(false);
				this.visitorTeam.getPlayer(i).setBPlayerHuman(false);
			}
			if (this.playerHuman != 2) {
				this.visitorTeam.getPlayer(ApoSoccerConstants.FORWARD).setBPlayerHuman(true);
				this.playerHuman = 2;
				this.setHumanPlayer(ApoSoccerConstants.ONE_HUMAN_PLAYER);
			} else {
				this.playerHuman = 0;
				this.setHumanPlayer(ApoSoccerConstants.NO_HUMAN_PLAYER);
			}
		} else if (function.equals("analysis")) {
			if (this.analysisFrame == null) {
				this.analysisFrame = new ApoSoccerAnalysisFrame(this, "auswertung.txt");
			} else {
				this.analysisFrame.setVisible(!this.analysisFrame.isVisible());
			}
		} else if (function.equals("previousPauseWait")) {
			this.pauseWaitChange(-1);
		} else if (function.equals("nextPauseWait")) {
			this.pauseWaitChange(+1);
		} else if (function.equals("replayPlayButton")) {
			this.replaySpeed = 1;
		} else if (function.equals("replayPauseButton")) {
			this.replaySpeed = 0;
		} else if (function.equals("replayForwardButton")) {
			this.replaySpeed = 2;
		} else if (function.equals("replayForwardFastButton")) {
			this.replaySpeed = 4;
		} else if (function.equals("replayRewindButton")) {
			this.replaySpeed = -1;
		} else if (function.equals("replayRewindFastButton")) {
			this.replaySpeed = -3;
		}
	}
	
	private void pauseWaitChange(int next) {
		if (this.waitPause < 0) {
			if (next > 0) {
				this.waitPause += 5;
			} else if (next < 0) {
				this.waitPause -= 5;
			}
			if (this.waitPause >= 0) {
				this.waitPause = ApoSoccerConstants.WAIT_TIME_THINK;
			} else if (this.waitPause < -11) {
				this.waitPause = ApoSoccerConstants.WAIT_TIME_THINK * 100;
			}
		} else {
			if (next > 0) {
				this.waitPause *= 10;
			} else if (next < 0) {
				this.waitPause /= 10;
			}
			if (this.waitPause > ApoSoccerConstants.WAIT_TIME_THINK * 100) {
				this.waitPause = -10;
			} else if (this.waitPause < ApoSoccerConstants.WAIT_TIME_THINK) {
				this.waitPause = -5;
			}
		}
	}
	
	public void littleGameChange() {
		ApoSoccerConstants.B_LITTLE_GAME = !ApoSoccerConstants.B_LITTLE_GAME;
		if (ApoSoccerConstants.B_LITTLE_GAME) {
			if (this.iLittleGameBackground == null) {
				this.iLittleGameBackground = this.getLittleGameBackground();
			}
		}
		this.setMenu();
	}
	
	public void debugChange() {
		if (this.isBStartGame()) {
			this.stopGame();
		}
		ApoSoccerConstants.B_DEBUG = !ApoSoccerConstants.B_DEBUG;
		if (this.debugFrame != null) {
			this.debugFrame.setVisible(!this.debugFrame.isVisible());
		} else {
			this.makeDebugAndAnalysisFrame();
		}
		this.setMenu();
	}
	
	private boolean loadAI(boolean bVisitor, int ai) {
		if ((ai < 0) || (ai > 6)) {
			return false;
		}
		if (ai == 0) {
			if (bVisitor) {
				if (ApoConstants.B_APPLET) {
					this.visitorTeam.setAi(null, new Apo(), "http://apo-games.de/apoSoccer/player/apo/", this);
				} else {
					this.visitorTeam.setAi(null, new Apo(), System.getProperty("user.dir") + File.separator + "apo" + File.separator, this);
				}
			} else {
				if (ApoConstants.B_APPLET) {
					this.homeTeam.setAi(null, new Apo(), "http://apo-games.de/apoSoccer/player/apo/", this);
				} else {
					this.homeTeam.setAi(null, new Apo(), System.getProperty("user.dir") + File.separator + "apo" + File.separator, this);
				}
			}
		} else if (ai == 1) {
			if (bVisitor) {
				if (ApoConstants.B_APPLET) {
					this.visitorTeam.setAi(null, new Steto(), "http://apo-games.de/apoSoccer/player/steto/", this);
				} else {
					this.visitorTeam.setAi(null, new Steto(), System.getProperty("user.dir") + File.separator + "steto" + File.separator, this);				
				}
			} else {
				if (ApoConstants.B_APPLET) {
					this.homeTeam.setAi(null, new Steto(), "http://apo-games.de/apoSoccer/player/steto/", this);
				} else {
					this.homeTeam.setAi(null, new Steto(), System.getProperty("user.dir") + File.separator + "steto" + File.separator, this);				
				}
			}
		} else if (ai == 2) {
			if (bVisitor) {
				if (ApoConstants.B_APPLET) {
					this.visitorTeam.setAi(null, new ReferenzTeam(), "http://apo-games.de/apoSoccer/player/michakrams/", this);
				} else {
					this.visitorTeam.setAi(null, new ReferenzTeam(), System.getProperty("user.dir") + File.separator + "michakrams" + File.separator, this);				
				}
			} else {
				if (ApoConstants.B_APPLET) {
					this.homeTeam.setAi(null, new ReferenzTeam(), "http://apo-games.de/apoSoccer/player/michakrams/", this);
				} else {
					this.homeTeam.setAi(null, new ReferenzTeam(), System.getProperty("user.dir") + File.separator + "michakrams" + File.separator, this);				
				}
			}
		} else if (ai == 3) {
			if (bVisitor) {
				if (ApoConstants.B_APPLET) {
					this.visitorTeam.setAi(null, new DoggyStyle(), "http://apo-games.de/apoSoccer/player/doggyStyle/", this);
				} else {
					this.visitorTeam.setAi(null, new DoggyStyle(), System.getProperty("user.dir") + File.separator + "doggyStyle" + File.separator, this);				
				}
			} else {
				if (ApoConstants.B_APPLET) {
					this.homeTeam.setAi(null, new DoggyStyle(), "http://apo-games.de/apoSoccer/player/doggyStyle/", this);
				} else {
					this.homeTeam.setAi(null, new DoggyStyle(), System.getProperty("user.dir") + File.separator + "doggyStyle" + File.separator, this);				
				}
			}
		} else if (ai == 4) {
			if (bVisitor) {
				if (ApoConstants.B_APPLET) {
					this.visitorTeam.setAi(null, new Animals(), "http://apo-games.de/apoSoccer/player/animals/", this);
				} else {
					this.visitorTeam.setAi(null, new Animals(), System.getProperty("user.dir") + File.separator + "animals" + File.separator, this);				
				}
			} else {
				if (ApoConstants.B_APPLET) {
					this.homeTeam.setAi(null, new Animals(), "http://apo-games.de/apoSoccer/player/animals/", this);
				} else {
					this.homeTeam.setAi(null, new Animals(), System.getProperty("user.dir") + File.separator + "animals" + File.separator, this);				
				}
			}
		} else if (ai == 5) {
			if (bVisitor) {
				if (ApoConstants.B_APPLET) {
					this.visitorTeam.setAi(null, new FCM(), "http://apo-games.de/apoSoccer/player/fcm/", this);
				} else {
					this.visitorTeam.setAi(null, new FCM(), System.getProperty("user.dir") + File.separator + "fcm" + File.separator, this);				
				}
			} else {
				if (ApoConstants.B_APPLET) {
					this.homeTeam.setAi(null, new FCM(), "http://apo-games.de/apoSoccer/player/fcm/", this);
				} else {
					this.homeTeam.setAi(null, new FCM(), System.getProperty("user.dir") + File.separator + "fcm" + File.separator, this);				
				}
			}
		} else if (ai == 6) {
			if (bVisitor) {
				if (ApoConstants.B_APPLET) {
					this.visitorTeam.setAi(null, new BeatMe(), "http://apo-games.de/apoSoccer/player/beatMe/", this);
				} else {
					this.visitorTeam.setAi(null, new BeatMe(), System.getProperty("user.dir") + File.separator + "beatMe" + File.separator, this);				
				}
			} else {
				if (ApoConstants.B_APPLET) {
					this.homeTeam.setAi(null, new BeatMe(), "http://apo-games.de/apoSoccer/player/beatMe/", this);
				} else {
					this.homeTeam.setAi(null, new BeatMe(), System.getProperty("user.dir") + File.separator + "beatMe" + File.separator, this);				
				}
			}
		}
		this.homeTeam.setGoals(0);
		this.visitorTeam.setGoals(0);
		this.ticker.init();
		this.ticker.setMenuText();
		this.makeBackgroundWithEmblem();
		this.stopGame();
		this.iLittleGameBackground = this.getLittleGameBackground();
		return true;
	}
	
	private boolean setMenu() {
		super.setBAnalysis(false);
		super.setBMenu(true);
		if (ApoConstants.B_APPLET) {
			this.setButtonVisible(ApoSoccerConstants.BUTTON_MENU_APPLET);
		} else {
			this.setButtonVisible(ApoSoccerConstants.BUTTON_MENU);
		}
		super.setBMenu(false);
		BufferedImage iBackground = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( ApoSoccerConstants.GAME_WIDTH, ApoSoccerConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB );
		Graphics2D g	= (Graphics2D)iBackground.getGraphics();
		this.centerCameraWithEntity(this.centerEntity);
		this.render(g);
		g.dispose();
		this.iRealMenu = super.getImages().getMenu(iBackground, ApoSoccerConstants.GAME_WIDTH, ApoSoccerConstants.GAME_HEIGHT);
		super.setBMenu(true);
		return true;
	}
	
	/**
	 * Methode die aufgerufen wird, wenn ein Spiel simuliert werden soll ohne dabei angezeigt zu werden,
	 * um Zeit zu sparen
	 * @return immer TRUE
	 */
	public boolean playGameWithoutRepaint() {
		if (ApoSoccerConstants.B_MUSIC) {
			super.setBSound(false);
			if (ApoConstants.B_APPLET) {
				this.setButtonVisible(ApoSoccerConstants.BUTTON_MENU_APPLET);
			} else {
				this.setButtonVisible(ApoSoccerConstants.BUTTON_MENU);
			}
		}
		this.simulate.startSimulate();
		return true;
	}
	
	/**
	 * laedt eine PlayerAI
	 * @param player = fuer welchen Spieler
	 * @param s = falls != NULL dann lade den uebergeben String
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void loadPlayer( boolean bHome, String s ) {
		String res = null;
		String source = null;
		String playerName = null;
		String className = null;
		if ( s == null ) {
			if ( this.fileChooser.showOpenDialog(this) == 0 ) {
				res = this.fileChooser.getSelectedFile().getPath();
				className = res.substring(res.lastIndexOf("."));
				res = res.substring( 0, res.lastIndexOf("."));
				source = res.substring( 0, res.lastIndexOf( File.separator ) ) + File.separator;
				res = res.substring( res.lastIndexOf( File.separator ) + 1 );
			}
			if (res==null || res.length()<=0) {
				return;    	
			}
			playerName	= res;
		} else {
			
			res	= s;
	    	playerName = res.substring(res.lastIndexOf("\\") + 1, res.lastIndexOf("."));
			source = res.substring(0,res.lastIndexOf("\\") + 1);
			className = res.substring(res.lastIndexOf("."));
			//System.out.println(playerName);
		}
		this.loadPlayer( bHome, source, playerName, className );
	}
	
	/**
	 * laed einen Spieler, wobei uebergeben wird welches Team es ist, wie der Pfad lautet und wie die KI lautet
	 * @param bHome : Boolean Variable, die angibt ob die KI zum Home- oder VisitorTeam gehört
	 * @param path : Der Pfad wo die KI drinliegt
	 * @param classname : Der Klassenname
	 * @return immer TRUE
	 */
	public boolean loadPlayer( boolean bHome, String path, String classname) {
		return this.loadPlayer(bHome, path, classname, ".class");
	}
	
	/**
	 * laed einen Spieler, wobei uebergeben wird welches Team es ist, wie der Pfad lautet und wie die KI lautet
	 * @param bHome : Boolean Variable, die angibt ob die KI zum Home- oder VisitorTeam gehört
	 * @param path : Der Pfad wo die KI drinliegt
	 * @param classname : Der Klassenname
	 * @return immer TRUE
	 */
	public boolean loadPlayer( boolean bHome, String path, String classname, String classN ) {
    	ApoSoccerAI apoSoccerAI = null;
//    	ApoSoccerAIJNI apoSoccerAIJNI = null;
    	System.out.println(path+" "+classname+" "+classN);
		if ( ( classN != null ) && ( classN.length() > 0 ) && (classN.indexOf(".class") != -1) ) {
			if ( !ApoConstants.B_APPLET ) {
				apoSoccerAI = (ApoSoccerAI)( new ApoSoccerClassLoader( path, classname ).getAI() );
			} else {
				URL url;
				try {
					url = new URL(path);
					apoSoccerAI = (ApoSoccerAI)( new ApoSoccerClassLoaderURL(url, classname).getAI() );
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		} else if ( ( classN != null ) && ( classN.length() > 0 ) && ((classN.indexOf(".dll") != -1) || (classN.indexOf(".so") != -1)) ) {
			//System.out.println("Test dll");
			if ( !ApoConstants.B_APPLET ) {
				if (this.apoSoccerAIJNI != null) {
					ApoSoccerAIJNI.loadAI(bHome, path + classname + classN);
				}
			}
		}
		try {
			// TODO: check loaded ai not the multiplexer
        	if ((apoSoccerAI != null) || (apoSoccerAIJNI != null)) {
        		if (apoSoccerAI != null) {
        			if ( bHome ) {
        				if (this.properties != null) {
        					this.properties.setHomeAIPath(path+classname+classN);
        				}
        				this.homeTeam.setAi(null, apoSoccerAI, path, this );
        			} else {
        				if (this.properties != null) {
        					this.properties.setVisitorAIPath(path+classname+classN);
        				}
        				this.visitorTeam.setAi(null, apoSoccerAI, path, this );
        			}
        		} else if (apoSoccerAIJNI != null) {
        			if ( bHome ) {
        				if (this.properties != null) {
        					this.properties.setHomeAIPath(path+classname+classN);
        				}
        				this.homeTeam.setAi(apoSoccerAIJNI, null, path, this );
        			} else {
        				if (this.properties != null) {
        					this.properties.setVisitorAIPath(path+classname+classN);
        				}
        				this.visitorTeam.setAi(apoSoccerAIJNI, null, path, this );
        			}
        		}

        		this.homeTeam.setGoals(0);
        		this.visitorTeam.setGoals(0);
        		this.ticker.init();
        		this.ticker.setMenuText();
        		this.makeBackgroundWithEmblem();
        		this.stopGame();
        		this.iLittleGameBackground = this.getLittleGameBackground();
        	}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * setzt die Sichtbarkeit der Button auf den den Wert von bStart bzw den entgegengesetzten
	 * kommt drauf an, ob er sichtbar sein soll
	 * @param bStart
	 * @return immer TRUE
	 */
	private boolean setButtonVisible(boolean[] bButton) {
		for (int i = 0; i < bButton.length; i++) {
			super.getButtons()[i].setBVisible(bButton[i]);			
		}
		// On/off Button fuer Sound
		if ((super.getButtons()[1].isBVisible()) && (ApoSoccerConstants.B_MUSIC)) {
			if (super.isBSound()) {
				super.getButtons()[10].setBVisible(false);
				super.getButtons()[11].setBVisible(true);
			} else {
				super.getButtons()[10].setBVisible(true);
				super.getButtons()[11].setBVisible(false);
			}
		} else {
			super.getButtons()[10].setBVisible(false);
			super.getButtons()[11].setBVisible(false);			
		}
		if (ApoConstants.B_APPLET) {
			super.getButtons()[0].setBVisible(false);
			super.getButtons()[4].setBVisible(false);
			super.getButtons()[5].setBVisible(false);
		}
		return true;
	}
	
	/**
	 * startet ein Replay und setzt dadurch einige Buttons auf Invisible und setzt die Teams auf ihre Ausgangspositionen
	 * @return immer TRUE
	 */
	private boolean startReplay() {
		this.setButtonVisible(ApoSoccerConstants.BUTTON_REPLAY);
		super.setBRunPlayer(false);
		super.setBAnalysis(false);
		super.setBMenu(false);
		super.setTime(0);
		this.replaySpeed = 1;
		this.waitTimeRender.clear();
		if (this.homeTeam.getPlayer(ApoSoccerConstants.GOALKEEPER).getX() > ApoSoccerConstants.FIELD_WIDTH/2) {
			this.homeTeam.halfTime();
			this.visitorTeam.halfTime();
		}
		this.homeTeam.init();
		this.visitorTeam.init();
		this.replay.setCurStep(0);
		this.replay.setBReplayPlay(true);
		this.replay.setBReplaySave(false);
		super.setBStartGame(true);
		return true;
	}

	/**
	 * wird aufgerufen, wenn auf Start gedrueckt wird und startet das eigentliche Spiel indem
	 * einige Buttons invisible gemacht werden und alles weitere vorbereitet wird. =)
	 * @return immer TRUE
	 */
	protected boolean startGame() {
		this.setButtonVisible(ApoSoccerConstants.BUTTON_GAME);
		this.waitPause = ApoSoccerConstants.WAIT_TIME_THINK;
		this.curWaitPause = 0;
		super.setBStartGame(true);
		super.setBAnalysis(false);
		super.setBMenu(false);
		this.waitTimeRender.clear();
		this.bGoal = false;
		this.bPause = false;
		this.centerEntity = this.ball;
		if (this.debugFrame != null) {
			this.debugFrame.clear();
		}
		if (this.simulate.isBSimulate()) {
			super.setBRunPlayer(false);
		} else {
			super.setBRunPlayer(true);
		}
		
		this.homeTeam.init();
		this.visitorTeam.init();
		
		if (this.homeTeam.getPlayer(ApoSoccerConstants.GOALKEEPER).getX() > ApoSoccerConstants.FIELD_WIDTH/2) {
			this.homeTeam.halfTime();
			this.visitorTeam.halfTime();
		}
		this.teamReset();
		this.replay.init();
		this.ticker.init();
		this.ticker.setNames(this.homeTeam.getTeamName(), this.homeTeam.getNames(), this.visitorTeam.getTeamName(), this.visitorTeam.getNames());
		this.ticker.addTicker("---------------------------------- Das heutige Spiel findet zwischen "+this.homeTeam.getTeamName()+" und "+this.visitorTeam.getTeamName()+" statt. Hoffen wir auf ein faires und spannendes Spiel!");
		System.gc();
		super.setTime(0);
		this.setPlayerToMiddle(this.homeTeam.getPlayer(ApoSoccerConstants.FORWARD), false);
		if (!this.simulate.isBSimulate()) {
			this.startPositions();
		}
		this.setKickOffTeam(true);
		this.curX = (int)(this.ball.getX() + ApoSoccerConstants.BOARD_WIDTH - ApoSoccerConstants.GAME_FIELD_WIDTH/2);
		this.curY = (int)(this.ball.getY() + ApoSoccerConstants.BOARD_HEIGHT - ApoSoccerConstants.GAME_FIELD_HEIGHT/2);
		this.goal();
		return true;
	}
	
	/**
	 * setzt die Spieler wieder auf ihre Ausgangspositionen beim STart des SPiel
	 * @return immer TRUE
	 */
	private boolean teamReset() {
		this.setPlayerToStartPoint(this.homeTeam.getPlayer(ApoSoccerConstants.GOALKEEPER), ApoSoccerConstants.GOALKEEPER_LEFT);
		this.setPlayerToStartPoint(this.visitorTeam.getPlayer(ApoSoccerConstants.GOALKEEPER), ApoSoccerConstants.GOALKEEPER_RIGHT);
		this.setPlayerToStartPoint(this.homeTeam.getPlayer(ApoSoccerConstants.DEFENDER_ONE), ApoSoccerConstants.DEFENDER_ONE_LEFT);
		this.setPlayerToStartPoint(this.visitorTeam.getPlayer(ApoSoccerConstants.DEFENDER_ONE), ApoSoccerConstants.DEFENDER_ONE_RIGHT);
		this.setPlayerToStartPoint(this.homeTeam.getPlayer(ApoSoccerConstants.DEFENDER_TWO), ApoSoccerConstants.DEFENDER_TWO_LEFT);
		this.setPlayerToStartPoint(this.visitorTeam.getPlayer(ApoSoccerConstants.DEFENDER_TWO), ApoSoccerConstants.DEFENDER_TWO_RIGHT);
		this.setPlayerToStartPoint(this.homeTeam.getPlayer(ApoSoccerConstants.FORWARD), ApoSoccerConstants.FORWARD_LEFT);
		this.setPlayerToStartPoint(this.visitorTeam.getPlayer(ApoSoccerConstants.FORWARD), ApoSoccerConstants.FORWARD_RIGHT);
		return true;
	}

	/**
	 * setzt die SPieler beider Teams, auf ihre VOrstellungspositionen
	 * @return immer TRUE
	 */
	private boolean startPositions() {
		this.homeTeam.setStartPositions(true);
		this.visitorTeam.setStartPositions(false);
		return true;
	}
	
	/**
	 * wird aufgerufen, wenn Stop gedrueckt wird und setzt die Spieler wieder in ihre Ausgangspositionen
	 * @return immer TRUE
	 */
	private boolean stopGame() {
		if (ApoConstants.B_APPLET) {
			this.setButtonVisible(ApoSoccerConstants.BUTTON_MENU_APPLET);
		} else {
			this.setButtonVisible(ApoSoccerConstants.BUTTON_MENU);
		}
		this.bGoal = false;
		this.bPause = false;
		this.homeTeam.init();
		this.visitorTeam.init();
		this.ball.init();
		this.replay.setBReplayPlay(false);
		this.replay.setBReplaySave(true);
		this.ticker.init();
		this.ticker.setMenuText();
		super.setBStartGame(false);
		super.setBAnalysis(false);
		super.setBMenu(true);
		super.setTime(0);
		this.waitTimeRender.clear();
		this.startPositions();
		return true;
	}
	
	/**
	 * wird aufgerufen, wenn das Spiel endet und erstellt das Analysebild
	 * @return immer TRUE
	 */
	private boolean gameEnds() {
		this.setButtonVisible(ApoSoccerConstants.BUTTON_ANALYSIS);
		this.simulate.setBSimulate(false);
		//this.ticker.addTicker("Das Spiel zwischen "+this.homeTeam.getTeamName()+" und "+this.visitorTeam.getTeamName()+" endet "+this.homeTeam.getGoals()+":"+this.visitorTeam.getGoals()+" ----------------------------------");
		String tickerMessage = this.tickerStrings.getTickerMessage(super.getTimeForGraphic(), this.ball.getLastShootPlayerName(), this.ball.getLastContactPlayerName(), this.homeTeam.getTeamName(), this.visitorTeam.getTeamName(), this.homeTeam.getGoals(), this.visitorTeam.getGoals(), ApoSoccerTickerStrings.END_TIME);
		this.ticker.addTicker(tickerMessage+" ----------------------------------");
		
		super.setBStartGame(false);
		this.bGoal = false;
		BufferedImage iBackground = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( ApoSoccerConstants.GAME_WIDTH, ApoSoccerConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB );
		Graphics2D g	= (Graphics2D)iBackground.getGraphics();
		this.render(g);
		g.dispose();
		super.setBAnalysis(true);
		this.iAnalysis = super.getImages().getAnalysis(iBackground, ApoSoccerConstants.GAME_WIDTH, ApoSoccerConstants.GAME_HEIGHT, this.iEmblemOneLittle, this.iEmblemTwoLittle);
		return true;
	}
	
	public boolean isBSimulate() {
		return this.simulate.isBSimulate();
	}
	
	/**
	 * wird aufgerufen, wenn ein Tor gefallen ist oder bei der Halbzeit
	 * Wenn ein Tor gefallen ist, dann setzt der den Stuermer der Gegnermannschaft in die Mitte zum Anstoss
	 * @return immer TRUE
	 */
	public boolean goal() {
		if (this.simulate.isBSimulate()) {
			super.setBRunPlayer(false);
		} else {
			super.setBRunPlayer(true);
		}
		if (ball.getX() < 0) {
			this.bGoal = true;
			this.goalMinute = super.getTimeForGraphic();
			this.goalShoot = this.ball.getLastShootPlayerName();
			this.goalTeamName = this.ball.getLastShootTeamName();
			if (this.homeTeam.getPlayer(ApoSoccerConstants.GOALKEEPER).isBLeft()) {
				this.visitorTeam.setGoals(this.visitorTeam.getGoals() + 1);
				this.setKickOffTeam(true);
				this.setPlayerToMiddle(this.homeTeam.getPlayer(ApoSoccerConstants.FORWARD), false);
				this.setPlayerToStartPoint(this.visitorTeam.getPlayer(ApoSoccerConstants.FORWARD), ApoSoccerConstants.FORWARD_RIGHT );
				String tickerMessage = this.tickerStrings.getTickerMessage(super.getTimeForGraphic(), this.ball.getLastShootPlayerName(), this.ball.getLastContactPlayerName(), this.visitorTeam.getTeamName(), this.homeTeam.getTeamName(), this.visitorTeam.getGoals(), this.homeTeam.getGoals(), ApoSoccerTickerStrings.GOAL_TIME);
				this.ticker.addTicker(tickerMessage);
				this.goalTeamName = this.visitorTeam.getTeamName();
			} else {
				this.homeTeam.setGoals(this.homeTeam.getGoals() + 1);
				this.setKickOffTeam(false);
				this.setPlayerToMiddle(this.visitorTeam.getPlayer(ApoSoccerConstants.FORWARD), false);
				this.setPlayerToStartPoint(this.homeTeam.getPlayer(ApoSoccerConstants.FORWARD), ApoSoccerConstants.FORWARD_RIGHT );
				String tickerMessage = this.tickerStrings.getTickerMessage(super.getTimeForGraphic(), this.ball.getLastShootPlayerName(), this.ball.getLastContactPlayerName(), this.homeTeam.getTeamName(), this.visitorTeam.getTeamName(), this.homeTeam.getGoals(), this.visitorTeam.getGoals(), ApoSoccerTickerStrings.GOAL_TIME);
				this.ticker.addTicker(tickerMessage);
				this.goalTeamName = this.homeTeam.getTeamName();
			}
		} else if (ball.getX() > ApoSoccerConstants.FIELD_WIDTH) {
			this.bGoal = true;
			this.goalMinute = super.getTimeForGraphic();
			this.goalShoot = this.ball.getLastShootPlayerName();
			if (this.homeTeam.getPlayer(ApoSoccerConstants.GOALKEEPER).isBLeft()) {
				this.homeTeam.setGoals(this.homeTeam.getGoals() + 1);
				this.setKickOffTeam(false);
				this.setPlayerToMiddle(this.visitorTeam.getPlayer(ApoSoccerConstants.FORWARD), true);
				this.setPlayerToStartPoint(this.homeTeam.getPlayer(ApoSoccerConstants.FORWARD), ApoSoccerConstants.FORWARD_LEFT );
				String tickerMessage = this.tickerStrings.getTickerMessage(super.getTimeForGraphic(), this.ball.getLastShootPlayerName(), this.ball.getLastContactPlayerName(), this.homeTeam.getTeamName(), this.visitorTeam.getTeamName(), this.homeTeam.getGoals(), this.visitorTeam.getGoals(), ApoSoccerTickerStrings.GOAL_TIME);
				this.ticker.addTicker(tickerMessage);
				this.goalTeamName = this.homeTeam.getTeamName();
			} else {
				this.visitorTeam.setGoals(this.visitorTeam.getGoals() + 1);
				this.setKickOffTeam(true);
				this.setPlayerToMiddle(this.homeTeam.getPlayer(ApoSoccerConstants.FORWARD), true);
				this.setPlayerToStartPoint(this.visitorTeam.getPlayer(ApoSoccerConstants.FORWARD), ApoSoccerConstants.FORWARD_LEFT );
				String tickerMessage = this.tickerStrings.getTickerMessage(super.getTimeForGraphic(), this.ball.getLastShootPlayerName(), this.ball.getLastContactPlayerName(), this.visitorTeam.getTeamName(), this.homeTeam.getTeamName(), this.visitorTeam.getGoals(), this.homeTeam.getGoals(), ApoSoccerTickerStrings.GOAL_TIME);
				this.ticker.addTicker(tickerMessage);
				this.goalTeamName = this.visitorTeam.getTeamName();
			}
		}
		this.homeTeam.goal(super.isBRunPlayer());
		this.visitorTeam.goal(super.isBRunPlayer());

		this.ball.init();
		return true;
	}
	
	private void setKickOffTeam(boolean bHome) {
		for (int i = 0; i < this.homeTeam.getPlayers().size(); i++) {
			this.homeTeam.getPlayer(i).setBKickOff(bHome);
		}
		for (int i = 0; i < this.visitorTeam.getPlayers().size(); i++) {
			this.visitorTeam.getPlayer(i).setBKickOff(!bHome);
		}
	}
	
	/**
	 * setzt den uebergebenen Spieler entweder knapp links oder knapp rechts vom Ball in die Mitte
	 * @param player : SPieler
	 * @param bLeft : TRUE, links vom Ball, FALSE rechts vom Ball
	 * @return immer TRUE
	 */
	private boolean setPlayerToMiddle(ApoPlayer player, boolean bLeft) {
		player.setStartY(ApoSoccerConstants.FIELD_HEIGHT/2);
		if (bLeft) {
			player.setStartX(ApoSoccerConstants.FIELD_WIDTH/2 - player.getWidth() - this.ball.getWidth() - 1);
			player.setStartLineOfSight(0);
		} else {
			player.setStartX(ApoSoccerConstants.FIELD_WIDTH/2 + player.getWidth() + this.ball.getWidth() + 1);
			player.setStartLineOfSight(180);
		}
		return true;
	}

	/**
	 * setzt den uebergebenen Spieler entweder knapp links oder knapp rechts vom Ball in die Mitte
	 * @param player : SPieler
	 * @param bLeft : TRUE, links vom Ball, FALSE rechts vom Ball
	 * @return immer TRUE
	 */
	private boolean setPlayerToStartPoint(ApoPlayer player, Point p) {
		player.setStartY(p.y);
		player.setStartX(p.x);
		return true;
	}
	
	/**
	 * wird zur Halbzeit aufgerufen
	 * und wechselt die Seiten der Mannschaften
	 * @return immer TRUE
	 */
	private boolean halfTime() {
		this.homeTeam.halfTime();
		this.visitorTeam.halfTime();
		this.setPlayerToMiddle(this.visitorTeam.getPlayer(ApoSoccerConstants.FORWARD), false);
		this.ball.init();
		this.goal();
		//this.ticker.addTicker("---------------------------------- Die Halbzeit wird praesentiert von Apo-Games. Mehr als nur Spiele. ----------------------------------");
		this.ticker.addTicker("----------------------------------");
		String tickerMessage = this.tickerStrings.getTickerMessage(super.getTimeForGraphic(), this.ball.getLastShootPlayerName(), this.ball.getLastContactPlayerName(), this.homeTeam.getTeamName(), this.visitorTeam.getTeamName(), this.homeTeam.getGoals(), this.visitorTeam.getGoals(), ApoSoccerTickerStrings.HALF_TIME);
		this.ticker.addTicker(tickerMessage);
		this.ticker.addTicker("----------------------------------");
		
		return true;
	}
	
	/**
	 * wird aufgerufen, wenn Ball festhing und in die Mitte resetet wird
	 * @return immer TRUE
	 */
	public boolean ballReset() {
		this.ball.init();
		this.homeTeam.ballReset();
		this.visitorTeam.ballReset();
		return true;
	}
	
	@Override
	public void think(int delta) {
		if (this.curMouseFunction != null) {
			this.thinkMouseButton();
			return;
		}
		if (super.isBCurStop()) {
			return;
		}
		int repeat = 1;
		if (this.waitPause > ApoSoccerConstants.WAIT_TIME_THINK) {
			if (this.curWaitPause + delta < this.waitPause) {
				this.curWaitPause += delta;
				return;
			} else {
				this.curWaitPause = 0;
			}
		} else if (this.waitPause < 0) {
			repeat = -this.waitPause;
		}
		while (repeat > 0) {
			repeat -= 1;
			this.curX += this.curVecX;
			this.curY += this.curVecY;
			if ( (this.curX <= 0) || (this.curX + ApoSoccerConstants.GAME_FIELD_WIDTH >= super.getIBackground().getWidth()) ) {
				this.curVecX = -this.curVecX;
			}
			if ( (this.curY <= 0) || (this.curY + ApoSoccerConstants.GAME_FIELD_HEIGHT >= super.getIBackground().getHeight()) ) {
				this.curVecY = -this.curVecY;
			}
			// Kamera wird so ausgerichtet, dass aktuelle centerEntity genau mittig ist
			this.centerCameraWithEntity(this.centerEntity);
		
			if (this.bPause) {
				if (this.bNextStep) {
					this.bNextStep = false;
					this.thinkGame(delta);
				
					if ((this.debugFrame != null) && (this.debugFrame.isVisible()) && (!this.simulate.isBSimulate())) {
						this.debugFrame.setValues(this.homeTeam, this.visitorTeam, this.ball);
					}
				}
			} else if (super.isBStartGame()) {
				if (this.replay.isBReplayPlay()) {
					if (this.replaySpeed == 0) {
						return;
					} else if (this.replaySpeed < 0) {
						for (int i = this.replaySpeed; i <= 0; i++) {
							this.thinkGame(delta);
						}
					} else if (this.replaySpeed > 0) {
						for (int i = 0; i < this.replaySpeed; i++) {
							this.thinkGame(delta);
						}
					}
				} else {
					this.thinkGame(delta);
				}
				if ((this.debugFrame != null) && (this.debugFrame.isVisible()) && (!this.simulate.isBSimulate())) {
					this.debugFrame.setValues(this.homeTeam, this.visitorTeam, this.ball);
				}
			}
		}
	}
	
	/**
	 * wird aufgerufen, wenn das Spiel laeuft und laesst die KI's denken und die SPieler und den Ball bewegen
	 * @param delta : Zeit, seit dem letzten Aufruf die vergangen ist
	 * @return immer TRUE
	 */
	private boolean thinkGame(int delta) {
		
		if (!this.replay.isBReplayPlay()) {
			
			boolean bThinkRunPlayer = this.thinkRunPlayer();
			
			// der Ball "denkt" und bewegt sich
			this.ball.think(delta, this);
			
			if (!bThinkRunPlayer) {
				// die Spieler "denken"
				try {
					this.homeTeam.thinkAI(delta, this);
				} catch (Exception ex) {
					this.homeTeam.setGoals(0);
					this.visitorTeam.setGoals(10);
					this.gameEnds();
					System.out.println(ex.getMessage());
					if ((this.debugFrame != null)) {
						this.debugFrame.addString("Exception von Team '"+this.homeTeam.getTeamName()+"': "+ex.getMessage());
					}
					return false;
				}
				try {
					this.visitorTeam.thinkAI(delta, this);
				} catch (Exception ex) {
					this.homeTeam.setGoals(10);
					this.visitorTeam.setGoals(0);
					this.gameEnds();
					System.out.println(ex.getMessage());
					if ((this.debugFrame != null)) {
						this.debugFrame.addString("Exception von Team '"+this.visitorTeam.getTeamName()+"': "+ex.getMessage());
					}
					return false;
				}
			}
			// die Spieler bewegen sich
			this.homeTeam.think(delta, this);
			this.visitorTeam.think(delta, this);
			
			if ((this.debugText != null) && (this.debugText.size() > 0)) {
				for (int i = 0; i < this.debugText.size(); i++) {
					this.debugFrame.addString(this.debugText.get(i));
				}
				this.debugText.clear();
			}
			
			if ((this.replay.isBReplaySave()) && (!bThinkRunPlayer)) {
				this.replay.addStep(this.getHomeTeam(), this.getVisitorTeam(), this.ball);
			}
		} else if (this.replay.isBReplayPlay()) {
			int addCurStep = 1;
			if (this.replaySpeed < 0) {
				addCurStep = -1;
			}
			if (!this.replay.playStep(this.homeTeam, this.visitorTeam, this.ball, delta, addCurStep)) {
				super.setBReplayShow(true);
				this.gameEnds();
				return true;
			} else {
				super.setReplayTime(super.getReplayTime() + delta);
				if ( (this.replay.isBGoal()) && (this.replay.isBHomeGoal()) ) {
					if (this.homeTeam.getPlayer(ApoSoccerConstants.GOALKEEPER).getX() < ApoSoccerConstants.FIELD_WIDTH/2) {
						this.visitorTeam.setGoals(this.visitorTeam.getGoals() + addCurStep);
					} else {
						this.homeTeam.setGoals(this.homeTeam.getGoals() + addCurStep);
					}
				} else if ( (this.replay.isBGoal()) && (!this.replay.isBHomeGoal()) ) {
					if (this.homeTeam.getPlayer(ApoSoccerConstants.GOALKEEPER).getX() > ApoSoccerConstants.FIELD_WIDTH/2) {
						this.visitorTeam.setGoals(this.visitorTeam.getGoals() + addCurStep);
					} else {
						this.homeTeam.setGoals(this.homeTeam.getGoals() + addCurStep);
					}
				}
			}
		}
		
		if (!super.isBRunPlayer()) {
			int oldTime = super.getTimeForGraphic();
			if (this.replay.isBReplayPlay()) {
				if (this.replaySpeed < 0) {
					delta = -delta;
				}
				if (super.getTime() + delta < 0) {
					return true;
				}
			}
			super.setTime(super.getTime() + delta);
			int newTime = super.getTimeForGraphic();
			if ((oldTime != newTime) &&
				(newTime == ApoSoccerConstants.END_TIME/2)) {
				if (!this.replay.isBReplayPlay()) {
					this.halfTime();
				}
			} else
				// beendet das Spiel nach 90 Minuten
			if (super.getTimeForGraphic() > ApoSoccerConstants.END_TIME) {
				this.gameEnds();
			}
		}
		return true;
	}
	
	/**
	 * wird aufgerufen, wenn die Spieler zu ihren Startplaetzen laufen sollen
	 * @return TRUE, wenn Spieler noch zu ihrem Startplatz rennen muessen, sonst FALSE
	 */
	private boolean thinkRunPlayer() {
		if (super.isBRunPlayer()) {
			if ((!this.homeTeam.isPlayerRunning()) && (!this.visitorTeam.isPlayerRunning())) {
				super.setBRunPlayer(false);
				this.bGoal = false;
				return super.isBRunPlayer();
			} else {
				return super.isBRunPlayer();
			}
		} else {
			this.bGoal = false;
			return super.isBRunPlayer();
		}
	}
	
	/**
	 * zentriert das zu zeigene Spielfeld an den Ball
	 * @return immer TRUE
	 */
	private boolean centerCameraWithEntity(ApoSoccerEntity entity) {
		int x = (int)(entity.getX() + ApoSoccerConstants.BOARD_WIDTH - ApoSoccerConstants.GAME_FIELD_WIDTH/2);
		int y = (int)(entity.getY() + ApoSoccerConstants.BOARD_HEIGHT - ApoSoccerConstants.GAME_FIELD_HEIGHT/2);
		if (x < 0) {
			x = 0;
		} else if (x + ApoSoccerConstants.GAME_FIELD_WIDTH >= super.getIBackground().getWidth()) {
			x = super.getIBackground().getWidth() - ApoSoccerConstants.GAME_FIELD_WIDTH;
		}
		if (y < 0) {
			y = 0;
		} else if (y + ApoSoccerConstants.GAME_FIELD_HEIGHT >= super.getIBackground().getHeight()) {
			y = super.getIBackground().getHeight() - ApoSoccerConstants.GAME_FIELD_HEIGHT;
		}
		int value = 4;
		if ((Math.abs(this.curX - x) > value) ||
			(Math.abs(this.curY - y) > value)) {
			if (this.curX - x < - value) {
				this.curX += value;
			} else if (this.curX - x > value) {
				this.curX -= value;
			} else {
				this.curX = x;
			}
			if (this.curY - y < - value) {
				this.curY += value;
			} else if (this.curY - y > value) {
				this.curY -= value;
			} else {
				this.curY = y;
			}
			//System.out.println(this.curX +" "+this.curY);
			return true;
		} else {
			this.curX = x;
			this.curY = y;
		}
		return true;
	}
	
	@Override
	public void render(Graphics2D g) {
		long time = System.nanoTime();
		if ((!super.isBAnalysis()) && (!super.isBMenu())) {
			if (!ApoSoccerConstants.B_LITTLE_GAME) {
				if (this.iBackground != null) {
					g.drawImage(this.iBackground, 0, 0, ApoSoccerConstants.GAME_FIELD_WIDTH, ApoSoccerConstants.GAME_FIELD_HEIGHT, this.curX, this.curY, this.curX + ApoSoccerConstants.GAME_FIELD_WIDTH, this.curY + ApoSoccerConstants.GAME_FIELD_HEIGHT, null);
					g.setFont(ApoSoccerConstants.FONT_RENDER);
				}
				this.renderBall(g);
				this.renderPlayer(g);
				this.renderInformations(g);
				//g.drawString("x = "+this.curX+" y = "+this.curY, 5, 20);
				this.miniMap.render(g, 0, 0);
		
			} else {
				this.drawLittleGame(g);
			}
			this.renderMenu(g);
			this.renderTicker(g);
			if (this.bPause) {
				this.renderPauseMenu(g);
			} else if (this.replay.isBReplayPlay()) {
				this.renderReplayMenu(g);
			} else {
				this.renderKeyboardHelp(g);
			}
			this.renderButtons(g);
			this.renderReplay(g);
			this.renderGoal(g);
		} else if (super.isBMenu()) {
			if (this.iRealMenu != null) {
				g.drawImage(this.iRealMenu, 0, 0, null);
				this.renderRealMenu(g);
			}
			//this.renderTicker(g);
			this.renderButtons(g);
		} else if (super.isBAnalysis()) {
			if (this.iAnalysis != null) {
				g.drawImage(this.iAnalysis, 0, 0, null);
				this.renderAnalysis(g);
			}
			this.renderButtons(g);
		}
		
		float renderTime = ApoHelp.round(((System.nanoTime() - time)/1000000f), 2);
		if (ApoSoccerConstants.B_DEBUG) {
			if (ApoSoccerConstants.B_LITTLE_GAME) {
				g.setColor(Color.black);
			} else {
				g.setColor(Color.black);
			}
			g.setFont(ApoSoccerConstants.FONT_DEBUG);
			String t = "timeRender: "+renderTime+" ms";
			g.drawString(t, 250, ApoSoccerConstants.GAME_HEIGHT - 2);
			super.renderFPS(g, 100, ApoSoccerConstants.GAME_HEIGHT - 2);
			if (ApoSoccerConstants.B_STOP_SHOOT) {
				g.drawString("stopShoot: "+ApoSoccerConstants.B_STOP_SHOOT, 380, ApoSoccerConstants.GAME_HEIGHT - 2);
			}
		}
		// nun kommt die Berechnung, ob die FPS erhöht werden duerfen oder nicht
		this.waitTimeRender.add(renderTime);
		if (this.waitTimeRender.size() > 3000 / super.getWAIT_TIME_RENDER()) {
			float all = 0;
			for (int i = 0; i < this.waitTimeRender.size(); i++) {
				all += this.waitTimeRender.get(i);
			}
			if (all / this.waitTimeRender.size() > super.getWAIT_TIME_RENDER()) {
				super.setWAIT_TIME_RENDER((int)(all / this.waitTimeRender.size() + 2));
			} else if ((all / this.waitTimeRender.size() < super.getWAIT_TIME_RENDER() - this.FPS_COUNT - 1) &&
						(super.getWAIT_TIME_RENDER() - this.FPS_COUNT >= 20)){
				super.setWAIT_TIME_RENDER(super.getWAIT_TIME_RENDER() - this.FPS_COUNT);
			} else if ((all / this.waitTimeRender.size() < super.getWAIT_TIME_RENDER() - this.FPS_COUNT - 1)){
				super.setWAIT_TIME_RENDER(20);
			}
			this.waitTimeRender.clear();
		}
	}

	/**
	 * sortiert ein Team in die ArrayList mit ein nach folgender Regel:
	 * Je höher ein Spieler ist, desto weiter nach hinten kommt er in die Liste
	 * @param drawPositions : ArrayList mit Int Werten 
	 * @param team : Das Team
	 * @return eine aktualisierte ArrayList
	 */
	private ArrayList<Integer> getSortPlayer(ArrayList<Integer> drawPositions, ApoTeam team) {
		for (int i = 0; i < team.getPlayers().size(); i++) {
			if (drawPositions.size() == 0) {
				drawPositions.add(i);
			} else {
				for (int j = 0; j < drawPositions.size(); j++) {
					int value = drawPositions.get(j);
					if (value < ApoSoccerConstants.PLAYER_AMOUNT) {
						if (this.homeTeam.getPlayer(value).getY() < team.getPlayer(i).getY()) {
							if (team.equals(this.visitorTeam)) {
								drawPositions.add(j, i + ApoSoccerConstants.PLAYER_AMOUNT);
							} else {
								drawPositions.add(j, i);
							}
							break;
						}
					} else {
						if (this.visitorTeam.getPlayer(value - ApoSoccerConstants.PLAYER_AMOUNT).getY() < team.getPlayer(i).getY()) {
							if (team.equals(this.visitorTeam)) {
								drawPositions.add(j, i + ApoSoccerConstants.PLAYER_AMOUNT);
							} else {
								drawPositions.add(j, i);
							}
							break;
						}
					}
					if (drawPositions.size() - 1 == j) {
						if (team.equals(this.visitorTeam)) {
							drawPositions.add(i + ApoSoccerConstants.PLAYER_AMOUNT);
							break;
						} else {
							drawPositions.add(i);
							break;
						}
					}
				}
			}
		}
		return drawPositions;
	}
	
	/**
	 * malt alle Spieler auf den Rasen
	 * dabei wird auch darauf geachtet, dass die oberen Spieler zuerst gezeichnet werden
	 * und dann erst die darunter liegenden
	 * @param g : das GraphicsObject
	 */
	private void renderPlayer(Graphics2D g) {
		ArrayList<Integer> drawPositions = new ArrayList<Integer>();
		drawPositions = this.getSortPlayer(drawPositions, this.homeTeam);
		drawPositions = this.getSortPlayer(drawPositions, this.visitorTeam);
		
		int changeX = -this.curX + ApoSoccerConstants.BOARD_WIDTH;
		int changeY = -this.curY + ApoSoccerConstants.BOARD_HEIGHT;
		if (this.centerEntity instanceof ApoPlayer) {
			g.setColor(Color.black);
			g.drawOval((int)(changeX + this.centerEntity.getX() - this.centerEntity.getWidth() - 1), (int)(changeY + this.centerEntity.getY() - this.centerEntity.getWidth() - 1), (int)(this.centerEntity.getWidth() * 2 + 2), (int)(this.centerEntity.getWidth() * 2 + 2));
		}
		
		for (int i = drawPositions.size() - 1; i >= 0; i--) {
			int value = drawPositions.get(i);
			if (value < ApoSoccerConstants.PLAYER_AMOUNT) {
				this.homeTeam.renderPlayer(g, value, changeX, changeY);
			} else {
				this.visitorTeam.renderPlayer(g, value - ApoSoccerConstants.PLAYER_AMOUNT, changeX, changeY);
			}
		}
	}
	
	/**
	 * malt den Ball
	 * @param g : das GraphicsObject
	 */
	private void renderBall(Graphics2D g) {
		if (this.ball != null) {
			this.ball.render(g, -this.curX + ApoSoccerConstants.BOARD_WIDTH, -this.curY + ApoSoccerConstants.BOARD_HEIGHT);
		}
	}
	
	/**
	 * malt das Menu am rechten Rand
	 * @param g : das GraphicsObject
	 */
	private void renderMenu(Graphics2D g) {
		// Das Menu malen
		g.drawImage(this.iMenu, ApoSoccerConstants.GAME_FIELD_WIDTH, 0, null);
		g.setColor(Color.white);
		g.setFont(ApoSoccerConstants.FONT_TEAM_NAME);

		// Name der Heimmannschaft malen
		String teamName = this.homeTeam.getTeamName();
		if (this.replay.isBReplayPlay()) {
			teamName = this.replay.getHomeTeamName();
		}
		if (teamName == null) {
			teamName = "";
		}
		int width = g.getFontMetrics().stringWidth(teamName);
		if (width > ApoSoccerConstants.TICKER_WIDTH/2 - 5) {
			g.setFont(ApoSoccerConstants.FONT_DEBUG);
			width = g.getFontMetrics().stringWidth(teamName);
		}
		if (width > ApoSoccerConstants.TICKER_WIDTH/2 - 5) {
			g.setFont(ApoSoccerConstants.FONT_LITTLE_PLAYER);
			width = g.getFontMetrics().stringWidth(teamName);
		}
		g.drawString(teamName, ApoSoccerConstants.GAME_FIELD_WIDTH - width/2 + (ApoSoccerConstants.GAME_WIDTH - ApoSoccerConstants.GAME_FIELD_WIDTH )*1/4, 20);
		
		g.setFont(ApoSoccerConstants.FONT_TEAM_NAME);

		
		// Name des Gaesteteams malen
		teamName = this.visitorTeam.getTeamName();
		if (this.replay.isBReplayPlay()) {
			teamName = this.replay.getVisitorTeamName();
		}
		if (teamName == null) {
			teamName = "";
		}
		width = g.getFontMetrics().stringWidth(teamName);
		if (width > ApoSoccerConstants.TICKER_WIDTH/2 - 5) {
			g.setFont(ApoSoccerConstants.FONT_DEBUG);
			width = g.getFontMetrics().stringWidth(teamName);
		}
		if (width > ApoSoccerConstants.TICKER_WIDTH/2 - 5) {
			g.setFont(ApoSoccerConstants.FONT_LITTLE_PLAYER);
			width = g.getFontMetrics().stringWidth(teamName);
		}
		g.drawString(teamName, ApoSoccerConstants.GAME_FIELD_WIDTH - width/2 + (ApoSoccerConstants.GAME_WIDTH - ApoSoccerConstants.GAME_FIELD_WIDTH )*3/4, 20);
		
		// Doppelpunkt fuer die Toranzeige malen
		g.setFont(ApoSoccerConstants.FONT_SCORE);
		String score = ":";
		width = g.getFontMetrics().stringWidth(score);
		g.drawString(score, ApoSoccerConstants.GAME_FIELD_WIDTH - width/2 + (ApoSoccerConstants.GAME_WIDTH - ApoSoccerConstants.GAME_FIELD_WIDTH )/2, 40);

		// Tore der Heimmannschaft malen
		g.setColor(Color.red);
		score = this.homeTeam.getGoals() + "";
		width = g.getFontMetrics().stringWidth(score);
		g.drawString(score, ApoSoccerConstants.GAME_FIELD_WIDTH - width/2 + (ApoSoccerConstants.GAME_WIDTH - ApoSoccerConstants.GAME_FIELD_WIDTH )*1/4, 40);
		
		// Tore der Gaestemannschaft malen
		g.setColor(Color.blue);
		score = this.visitorTeam.getGoals() + "";
		width = g.getFontMetrics().stringWidth(score);
		g.drawString(score, ApoSoccerConstants.GAME_FIELD_WIDTH - width/2 + (ApoSoccerConstants.GAME_WIDTH - ApoSoccerConstants.GAME_FIELD_WIDTH )*3/4, 40);
		
		//Zeit malen
		g.setColor(Color.white);
		score = super.getTimeForGraphic() + "";
		width = g.getFontMetrics().stringWidth(score);
		g.drawString(score, ApoSoccerConstants.GAME_FIELD_WIDTH - width/2 + (ApoSoccerConstants.GAME_WIDTH - ApoSoccerConstants.GAME_FIELD_WIDTH )*1/2, 70);
	}
	
	/**
	 * malt den Ticker
	 * @param g : das GraphicsObject
	 */
	private void renderTicker(Graphics2D g) {
		if ((this.ticker != null)) {
			this.ticker.render(g);
		} else {
			this.renderDebugValues(g);
		}
	}

	/**
	 * malt das Replay hin
	 * @param g : das GraphicsObject
	 */
	private void renderReplay(Graphics2D g) {
		if (this.replay.isBReplayPlay()) {
			if (this.iReplay != null) {
				if (super.isBReplayShow()) {
					g.drawImage(this.iReplay, 5, 5, null);
				}
			}
		}
	}
	
	/**
	 * malt DebugValues hin
	 * @param g : das GraphicsObject
	 */
	private void renderDebugValues(Graphics2D g) {
		if (ApoSoccerConstants.B_DEBUG) {
			if (1 == 1) {//!ApoSoccerConstants.B_LITTLE_GAME) {
				g.setColor(Color.black);
				g.setFont(ApoSoccerConstants.FONT_DEBUG);
				int y = ApoSoccerConstants.TICKER_Y + 45;
				int x = ApoSoccerConstants.TICKER_X + 2;
				g.drawString("Name", x, y);
				g.drawString("F", x + 55, y);
				g.drawString("MaxF", x + 90, y);
				g.drawString("Spe", x + 125, y);
				y += 10;
				g.drawString("los", x + 5, y);
				g.drawString("x", x + 55, y);
				g.drawString("y", x + 90, y);
				g.drawString("Sho", x + 125, y);
				y += 20;
				g.setColor(Color.red);
				for (int i = 0; i < this.homeTeam.getPlayers().size(); i++) {
					ApoPlayer player = this.homeTeam.getPlayers().get(i);
					String name = "";
					if (player.getName().length() > 9) {
						name = player.getName().substring(0, 9);
					} else {
						name = player.getName();
					}
					g.setColor(Color.black);
					g.drawString(name, x, y);
					g.setColor(Color.red);
					g.drawString(""+ApoHelp.round(player.getFreshness(), 1), x + 55, y);
					g.drawString(""+ApoHelp.round(player.getMaxFreshness(), 1), x + 90, y);
					g.drawString(""+ApoHelp.round(player.getSpeed(), 1), x + 125, y);
					y += 13;
					g.drawString(""+player.getLineOfSight(), x + 5, y);
					g.drawString(""+ApoHelp.round(player.getX(), 1), x + 55, y);
					g.drawString(""+ApoHelp.round(player.getY(), 1), x + 90, y);
					if ((player.isBCanShoot()) && (player.getCanShootCounter() < 0)) {
						g.drawString("true", x + 125, y);	
					} else {
						g.drawString("false", x + 125, y);
					}
					y += 18;
				}
				y += 10;
				g.setColor(Color.blue);
				for (int i = 0; i < this.visitorTeam.getPlayers().size(); i++) {
					ApoPlayer player = this.visitorTeam.getPlayers().get(i);
					String name = "";
					if (player.getName().length() > 9) {
						name = player.getName().substring(0, 9);
					} else {
						name = player.getName();
					}
					g.setColor(Color.black);
					g.drawString(name, x, y);
					g.setColor(Color.blue);
					g.drawString(""+ApoHelp.round(player.getFreshness(), 1), x + 55, y);
					g.drawString(""+ApoHelp.round(player.getMaxFreshness(), 1), x + 90, y);
					g.drawString(""+ApoHelp.round(player.getSpeed(), 1), x + 125, y);
					y += 13;
					g.drawString(""+player.getLineOfSight(), x + 5, y);
					g.drawString(""+ApoHelp.round(player.getX(), 1), x + 55, y);
					g.drawString(""+ApoHelp.round(player.getY(), 1), x + 90, y);
					if ((player.isBCanShoot()) && (player.getCanShootCounter() < 0)) {
						g.drawString("true", x + 125, y);	
					} else {
						g.drawString("false", x + 125, y);
					}
					y += 18;
				}
			}
		}
	}
	
	/**
	 * malt die Analyse nach dem Spiel
	 * @param g : das GraphicsObject
	 */
	private void renderAnalysis(Graphics2D g) {
		g.setFont(ApoSoccerConstants.FONT_SCORE);
		g.setColor(Color.WHITE);
		String s = "And the winner is";
		int width = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoSoccerConstants.GAME_FIELD_WIDTH/2 - width/2, 100);
		s = "nobody";
		String authorName = null;
		if (this.homeTeam.getGoals() > this.visitorTeam.getGoals()) {
			g.setColor(this.homeTeam.getPlayer(0).getColor());
			s = this.homeTeam.getTeamName();
			authorName = this.homeTeam.getAuthor();
		} else if (this.homeTeam.getGoals() < this.visitorTeam.getGoals()) {
			g.setColor(this.visitorTeam.getPlayer(0).getColor());
			s = this.visitorTeam.getTeamName();
			authorName = this.visitorTeam.getAuthor();
		}
		width = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoSoccerConstants.GAME_FIELD_WIDTH/2 - width/2, 140);
		if (authorName != null) {
			g.setFont(ApoSoccerConstants.FONT_TEAM_NAME);
			g.setColor(Color.white);
			authorName = "by " + authorName;
			width = g.getFontMetrics().stringWidth(authorName);
			g.drawString(authorName, ApoSoccerConstants.GAME_FIELD_WIDTH/2 - width/2, 165);
		}
		
		int startX = (ApoSoccerConstants.GAME_FIELD_WIDTH - ApoSoccerConstants.ANALYSIS_WIDTH) / 2;
		int startY = (ApoSoccerConstants.GAME_FIELD_HEIGHT - ApoSoccerConstants.ANALYSIS_HEIGHT) / 2;
		g.setFont(ApoSoccerConstants.FONT_TEAM_NAME_ANAYLSIS);
		s = this.homeTeam.getTeamName();
		width = g.getFontMetrics().stringWidth(s);
		g.setColor(this.homeTeam.getPlayer(ApoSoccerConstants.GOALKEEPER).getColor());
		g.drawString(s, startX + ApoSoccerConstants.ANALYSIS_WIDTH * 1 / 4 - width/2, startY + 270);
		
		s = this.visitorTeam.getTeamName();
		width = g.getFontMetrics().stringWidth(s);
		g.setColor(this.visitorTeam.getPlayer(ApoSoccerConstants.GOALKEEPER).getColor());
		g.drawString(s, startX + ApoSoccerConstants.ANALYSIS_WIDTH * 3 / 4 - width/2, startY + 270);
		
		g.setFont(ApoSoccerConstants.FONT_SCORE_ANAYLSIS);
		g.setColor(this.homeTeam.getPlayer(ApoSoccerConstants.GOALKEEPER).getColor());
		s = String.valueOf(this.homeTeam.getGoals());
		width = g.getFontMetrics().stringWidth(s);
		g.drawString(s, startX + ApoSoccerConstants.ANALYSIS_WIDTH * 1 / 4 - width/2, startY + 305);
		
		g.setColor(this.visitorTeam.getPlayer(ApoSoccerConstants.GOALKEEPER).getColor());
		s = String.valueOf(this.visitorTeam.getGoals());
		width = g.getFontMetrics().stringWidth(s);
		g.drawString(s, startX + ApoSoccerConstants.ANALYSIS_WIDTH * 3 / 4 - width/2, startY + 305);
		
		g.setColor(Color.WHITE);
		s = ":";
		width = g.getFontMetrics().stringWidth(s);
		g.drawString(s, startX + ApoSoccerConstants.ANALYSIS_WIDTH * 2 / 4 - width/2, startY + 305);
	}
	
	/**
	 * malt das Menu nach/vor dem Spiel
	 * @param g : das GraphicsObject
	 */
	private void renderRealMenu(Graphics2D g) {
		g.setColor(Color.white);
		
		String s = "";
		String authorName = "";
		int width = 0;
		
		int startX = (ApoSoccerConstants.GAME_FIELD_WIDTH - ApoSoccerConstants.ANALYSIS_WIDTH) / 2;
		int startY = (ApoSoccerConstants.GAME_FIELD_HEIGHT - ApoSoccerConstants.ANALYSIS_HEIGHT) / 2;
		
		Composite c = g.getComposite();
		g.setComposite( AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.35f) );
		if (this.iEmblemOneLittle != null) {
			g.drawImage(this.iEmblemOneLittle, startX + ApoSoccerConstants.ANALYSIS_WIDTH * 1 / 4 - this.iEmblemOneLittle.getWidth()/2, startY + 40, null);
		}
		if (this.iEmblemTwoLittle != null) {
			g.drawImage(this.iEmblemTwoLittle, startX + ApoSoccerConstants.ANALYSIS_WIDTH * 3 / 4 - this.iEmblemTwoLittle.getWidth()/2, startY + 40, null);
		}
		g.setComposite(c);
		
		g.setFont(ApoSoccerConstants.FONT_TEAM_NAME_ANAYLSIS);
		s = this.homeTeam.getTeamName();
		width = g.getFontMetrics().stringWidth(s);
		g.setColor(this.homeTeam.getPlayer(ApoSoccerConstants.GOALKEEPER).getColor());
		g.drawString(s, startX + ApoSoccerConstants.ANALYSIS_WIDTH * 1 / 4 - width/2, startY + 65);
		authorName = this.homeTeam.getAuthor();
		if (authorName != null) {
			g.setFont(ApoSoccerConstants.FONT_TEAM_NAME);
			g.setColor(Color.white);
			authorName = "by " + authorName;
			width = g.getFontMetrics().stringWidth(authorName);
			g.drawString(authorName, startX + ApoSoccerConstants.ANALYSIS_WIDTH * 1 / 4 - width/2, startY + 90);
		}
		String playerHumanString = "off";
		if (this.playerHuman == 1) {
			playerHumanString = "on";
		}
		width = g.getFontMetrics().stringWidth(playerHumanString);
		g.drawString(playerHumanString, startX + ApoSoccerConstants.ANALYSIS_WIDTH * 1 / 4 - width/2 + 25, startY + 150);
		
		playerHumanString = "off";
		if (this.playerHuman == 2) {
			playerHumanString = "on";
		}
		width = g.getFontMetrics().stringWidth(playerHumanString);
		g.drawString(playerHumanString, startX + ApoSoccerConstants.ANALYSIS_WIDTH * 3 / 4 - width/2 - 25, startY + 150);
		
		playerHumanString = "human control";
		width = g.getFontMetrics().stringWidth(playerHumanString);
		g.drawString(playerHumanString, startX + ApoSoccerConstants.ANALYSIS_WIDTH * 2 / 4 - width/2, startY + 150);
		
		g.setFont(ApoSoccerConstants.FONT_TEAM_NAME_ANAYLSIS);
		s = this.visitorTeam.getTeamName();
		width = g.getFontMetrics().stringWidth(s);
		g.setColor(this.visitorTeam.getPlayer(ApoSoccerConstants.GOALKEEPER).getColor());
		g.drawString(s, startX + ApoSoccerConstants.ANALYSIS_WIDTH * 3 / 4 - width/2, startY + 65);
		
		authorName = this.visitorTeam.getAuthor();
		if (authorName != null) {
			g.setFont(ApoSoccerConstants.FONT_TEAM_NAME);
			g.setColor(Color.white);
			authorName = "by " + authorName;
			width = g.getFontMetrics().stringWidth(authorName);
			g.drawString(authorName, startX + ApoSoccerConstants.ANALYSIS_WIDTH * 3 / 4 - width/2, startY + 90);
		}
		
		int x = (ApoSoccerConstants.GAME_FIELD_WIDTH - ApoSoccerConstants.ANALYSIS_WIDTH) / 2;//- width/2;
		int y = (ApoSoccerConstants.GAME_FIELD_HEIGHT - ApoSoccerConstants.ANALYSIS_HEIGHT) / 2 + ApoSoccerConstants.ANALYSIS_HEIGHT - 45;

		if (ApoSoccerConstants.B_DEBUG) {
			super.getImages().drawString(g, "is on", x + 75, y, 0);
		} else {
			super.getImages().drawString(g, "is off", x + 75, y, 0);
		}
		
		if (ApoSoccerConstants.B_LITTLE_GAME) {
			super.getImages().drawString(g, "is on", ApoSoccerConstants.GAME_FIELD_WIDTH - x - 75, y, 0);
		} else {
			super.getImages().drawString(g, "is off", ApoSoccerConstants.GAME_FIELD_WIDTH - x - 75, y, 0);
		}
		
		x = startX + 55;
		y = startY + ApoSoccerConstants.ANALYSIS_HEIGHT/2 - 15;
		
		if (super.isBSound()) {
			g.drawString("is on", x, y);
		} else {
			g.drawString("is off", x, y);
		}
	}
	
	private void drawLittleGame(Graphics2D g) {
		g.drawImage(this.iLittleGameBackground, 0, 0, null);
		
		float plusX = ApoSoccerConstants.BOARD_WIDTH / delta + this.littleWidth;
		float plusY = ApoSoccerConstants.BOARD_HEIGHT / delta + this.littleHeight;
		
		float x = this.ball.getX() / this.delta + plusX;
		float y = this.ball.getY() / this.delta + plusY;
		float radius = this.ball.getWidth() / this.delta + 1;
		
		if (ApoSoccerConstants.B_DEBUG) {
			g.setColor(Color.white);
			g.fillOval((int)(x - radius - 8/this.delta), (int)(y - radius - 8/this.delta), (int)((radius + 8/delta) * 2), (int)((radius + 8/this.delta) * 2));
		}
		g.setColor(Color.cyan);
		g.fillOval((int)(x - radius), (int)(y - radius), (int)(radius * 2), (int)(radius * 2));
		g.setColor(Color.black);
		g.drawLine((int)(x + radius * Math.sin(Math.toRadians( 90 + this.ball.getLineOfSight() ))), (int)(y + radius * Math.cos(Math.toRadians( 270 + this.ball.getLineOfSight() ))), (int)(x), (int)(y));

		g.setFont(ApoSoccerConstants.FONT_LITTLE_PLAYER);
		
		for (int i = 0; i < this.homeTeam.getPlayers().size(); i++) {
			x = this.homeTeam.getPlayers().get(i).getX() / this.delta + plusX;
			y = this.homeTeam.getPlayers().get(i).getY() / this.delta + plusY;
			radius = this.homeTeam.getPlayers().get(i).getWidth() / this.delta + 1;
			
			g.setColor(Color.red);
			g.fillOval((int)(x - radius), (int)(y - radius), (int)(radius * 2), (int)(radius * 2));
			String s = this.homeTeam.getPlayers().get(i).getName();
			int w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, x - w/2, y + radius * 2 + 3);
			
			if (this.homeTeam.getPlayers().get(i).isBPlayerHuman()) {
				g.setColor(Color.yellow);
				g.drawOval((int)(x - radius - 1), (int)(y - radius - 1), (int)(radius * 2 + 2), (int)(radius * 2 + 2));
			}
			
			g.setColor(Color.black);
			g.drawLine((int)(x + (radius + 3) * Math.sin(Math.toRadians( 90 + this.homeTeam.getPlayers().get(i).getLineOfSight() ))), (int)(y + (radius + 3) * Math.cos(Math.toRadians( 270 + this.homeTeam.getPlayers().get(i).getLineOfSight() ))), (int)(x), (int)(y));
		
			if (ApoSoccerConstants.B_DEBUG) {
				if (this.homeTeam.getPlayers().get(i).getLines().size() >= 0) {
					Stroke originalStroke = g.getStroke();
					ArrayList<ApoSoccerDebugLine> lines = this.homeTeam.getPlayers().get(i).getLines();
					for (int j = lines.size() - 1; j >= 0; j--) {
						g.setColor(lines.get(j).getColor());
						if (lines.get(j).isBCircle()) {
							g.drawOval((int)((lines.get(j).getStartX() - lines.get(j).getWidth()/2) / this.delta + plusX), (int)((lines.get(j).getStartY() - lines.get(j).getWidth()/2) / this.delta + plusY), (int)(lines.get(j).getWidth() / this.delta), (int)(lines.get(j).getWidth() / this.delta));	
						} else {
							g.setStroke(new BasicStroke(lines.get(j).getWidth()));
							g.drawLine((int)(lines.get(j).getStartX() / this.delta + plusX), (int)(lines.get(j).getStartY() / this.delta + plusY), (int)(lines.get(j).getEndX() / this.delta + plusX), (int)(lines.get(j).getEndY() / this.delta + plusY));
						}
					}
					g.setStroke(originalStroke);
				}
			}
		}
		
		for (int i = 0; i < this.visitorTeam.getPlayers().size(); i++) {
			x = this.visitorTeam.getPlayers().get(i).getX() / this.delta + plusX;
			y = this.visitorTeam.getPlayers().get(i).getY() / this.delta + plusY;
			radius = this.visitorTeam.getPlayers().get(i).getWidth() / this.delta + 1;
			
			g.setColor(Color.blue);
			g.fillOval((int)(x - radius), (int)(y - radius), (int)(radius * 2), (int)(radius * 2));
			String s = this.visitorTeam.getPlayers().get(i).getName();
			int w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, x - w/2, y + radius * 2 + 3);
	
			if (this.visitorTeam.getPlayers().get(i).isBPlayerHuman()) {
				g.setColor(Color.yellow);
				g.drawOval((int)(x - radius - 1), (int)(y - radius - 1), (int)(radius * 2 + 2), (int)(radius * 2 + 2));
			}
			
			g.setColor(Color.black);
			g.drawLine((int)(x + (radius + 3) * Math.sin(Math.toRadians( 90 + this.visitorTeam.getPlayers().get(i).getLineOfSight() ))), (int)(y + (radius + 3) * Math.cos(Math.toRadians( 270 + this.visitorTeam.getPlayers().get(i).getLineOfSight() ))), (int)(x), (int)(y));

			if (ApoSoccerConstants.B_DEBUG) {
				if (this.visitorTeam.getPlayers().get(i).getLines().size() >= 0) {
					Stroke originalStroke = g.getStroke();
					ArrayList<ApoSoccerDebugLine> lines = this.visitorTeam.getPlayers().get(i).getLines();
					for (int j = lines.size() - 1; j >= 0; j--) {
						g.setColor(lines.get(j).getColor());
						if (lines.get(j).isBCircle()) {
							g.drawOval((int)((lines.get(j).getStartX() - lines.get(j).getWidth()/2) / this.delta + plusX), (int)((lines.get(j).getStartY() - lines.get(j).getWidth()/2) / this.delta + plusY), (int)(lines.get(j).getWidth() / this.delta), (int)(lines.get(j).getWidth() / this.delta));	
						} else {
							g.setStroke(new BasicStroke(lines.get(j).getWidth()));
							g.drawLine((int)(lines.get(j).getStartX() / this.delta + plusX), (int)(lines.get(j).getStartY() / this.delta + plusY), (int)(lines.get(j).getEndX() / this.delta + plusX), (int)(lines.get(j).getEndY() / this.delta + plusY));
						}					}
					g.setStroke(originalStroke);
				}
			}
		}
	}
	
	private void renderGoal(Graphics2D g) {
		if (this.bGoal) {
			g.setColor(new Color(210,210,210));
			int x = ApoSoccerConstants.GAME_FIELD_WIDTH / 2;
			int y = ApoSoccerConstants.GAME_FIELD_HEIGHT / 2;
			g.fillRoundRect(x - 210, y - 100, 420, 200, 100, 100);
			g.setColor(Color.black);
			g.drawRoundRect(x - 210, y - 100, 420, 200, 100, 100);
			g.setFont(ApoSoccerConstants.FONT_SCORE);
			String s = "Tor in der "+this.goalMinute+" Minute fuer";
			int w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, x - w/2, y - 40);
			s = this.goalTeamName;
			g.setColor(Color.red);
			if (s.equals(this.visitorTeam.getTeamName())) {
				g.setColor(Color.blue);	
			}
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, x - w/2, y - 0);
			s = "Torschuetze: "+this.goalShoot;
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, x - w/2, y + 40);
		}
	}
	
	private void renderKeyboardHelp(Graphics2D g) {
		if (this.bPlayerHuman[ApoSoccerConstants.ONE_HUMAN_PLAYER]) {
			g.setColor(Color.white);
			g.setFont(ApoSoccerConstants.FONT_DEBUG);
			String s = "'Arrow keys' to move (with speed = 50)   'w' to run as fast as possible (with speed = 100)";
			g.drawString(s, 5, 10);
			
			s = "'s' to pass (with strength = 50)   'd' to shoot as hard as possible (with strength = 100)";
			g.drawString(s, 5, 20);
			
			s = "'a' to change the player";
			g.drawString(s, 5, 30);
		}
	}
	
	private void renderPauseMenu(Graphics2D g) {
		g.setColor(Color.black);
		g.fillRoundRect(ApoSoccerConstants.GAME_FIELD_WIDTH/2 - 90, 10, 180, 50, 20, 20);
		g.setColor(Color.white);
		g.drawRoundRect(ApoSoccerConstants.GAME_FIELD_WIDTH/2 - 90, 10, 180, 50, 20, 20);
		g.setFont(ApoSoccerConstants.FONT_TEAM_NAME);
		String s = "Game Speed";
		int width = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoSoccerConstants.GAME_FIELD_WIDTH/2 - width/2, 30);
		s = "original Speed";
		if (this.waitPause == ApoSoccerConstants.WAIT_TIME_THINK * 10) {
			s = "10 times slower";
		} else if (this.waitPause == ApoSoccerConstants.WAIT_TIME_THINK * 100) {
			s = "100 times slower";
		} else if (this.waitPause == -10) {
			s = "10 times faster";
		} else if (this.waitPause == -5) {
			s = "5 times faster";
		}
		width = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoSoccerConstants.GAME_FIELD_WIDTH/2 - width/2, 50);
	}
	
	private void renderReplayMenu(Graphics2D g) {
		g.setColor(Color.black);
		g.fillRoundRect(ApoSoccerConstants.GAME_FIELD_WIDTH/2 - 90, 10, 180, 50, 20, 20);
		g.setColor(Color.white);
		g.drawRoundRect(ApoSoccerConstants.GAME_FIELD_WIDTH/2 - 90, 10, 180, 50, 20, 20);
		g.setFont(ApoSoccerConstants.FONT_TEAM_NAME);
		String s = "Replay Speed";
		int width = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoSoccerConstants.GAME_FIELD_WIDTH/2 - width/2, 30);
	}
	
	private void renderInformations(Graphics2D g) {
		if (ApoSoccerConstants.B_DEBUG) {
			if ((!this.isBStartGame()) || (!ApoSoccerConstants.B_DEBUG)) {
			} else {
				for (int i = 0; i < this.visualInformation.size(); i++) {
					this.visualInformation.get(i).render(g, -this.curX + ApoSoccerConstants.BOARD_WIDTH, -this.curY + ApoSoccerConstants.BOARD_HEIGHT);
				}				
			}
		}
	}
}
