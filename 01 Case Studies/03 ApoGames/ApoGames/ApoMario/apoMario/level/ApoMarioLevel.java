package apoMario.level;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import org.apogames.ApoConstants;
import org.apogames.entity.ApoAnimation;

import test.IcarusAI;
import test.Test;
import test.Test2;
import test.TestIf;
import test.TestSolve;

import apoMario.ApoMarioClassLoader;
import apoMario.ApoMarioComponent;
import apoMario.ApoMarioConstants;
import apoMario.ApoMarioImageContainer;
import apoMario.ai.ApoMarioAI;
import apoMario.entity.ApoMarioCannon;
import apoMario.entity.ApoMarioCannonCannon;
import apoMario.entity.ApoMarioCoin;
import apoMario.entity.ApoMarioDestructableWall;
import apoMario.entity.ApoMarioEnd;
import apoMario.entity.ApoMarioEnemy;
import apoMario.entity.ApoMarioEntity;
import apoMario.entity.ApoMarioFlower;
import apoMario.entity.ApoMarioGumba;
import apoMario.entity.ApoMarioKoopa;
import apoMario.entity.ApoMarioParticle;
import apoMario.entity.ApoMarioPlayer;
import apoMario.entity.ApoMarioQuestionmark;
import apoMario.entity.ApoMarioShell;
import apoMario.entity.ApoMarioWall;
import apoMario.game.ApoMarioPanel;
import apoMario.game.ApoMarioReplay;
import apoMario.game.ApoMarioReplayPlay;

/**
 * Klasse, die ein Level darstellt
 * @author Dirk Aporius
 *
 */
public class ApoMarioLevel {
	
	private final int HUMAN_NO_PLAYER = 0;
	private final int TEST_IF_PLAYER = 1;
	private final int TEST_SLOW_PLAYER = 2;
	private final int TEST_FAST_PLAYER = 3;
	private final int TEST_SHORTI = 4;
	private final int TEST_CHEATER = 5;
	
	public static int ID;
	
	private int playerOneAI;
	private int playerTwoAI;
	
	/** Breite des Levels */
	private int width;
	/** Höhe des Levels */
	private int height;
	
	private long levelInt;
	/** Hintergrundbild */
	private BufferedImage iBackground;
	/** vordere Ebene des Hintergrundes */
	private BufferedImage iBackgroundFront;
	/** gecheateter Background */
	private BufferedImage iBoth;
	
	private int difficulty;
	
	private int time;
	
	private boolean bAnalysis;

	/** Eine Arraylist mit den Partikeln */
	private ArrayList<ApoMarioParticle> particle;
	/** Eine Arraylist mit den Gegnern */
	private ArrayList<ApoMarioEnemy> enemies;
	/** Eine Arraylist mit den Goodies */
	private ArrayList<ApoMarioEnemy> goodies;	
	/** eine Referenz zum Spielobjekt */
	private ApoMarioComponent component;
	
	private ApoMarioEntity[][] levelEntities;
	
	private ApoMarioEnd finish;
	
	private ArrayList<Point> cannons;
	
	private ArrayList<ApoMarioPlayer> players;
	
	private boolean bStop;
	
	private ApoMarioRandomLevel randomLevel;
	
	private ApoMarioReplay replay;
	
	private ApoMarioAI AIOne;
	private ApoMarioAI AITwo;
	
	private boolean bReplay;
	
	private ApoMarioReplayPlay replayPlay;
	
	private int steps;
	
	private ArrayList<Long> thinkPlayerOne, thinkPlayerTwo;
	
	private String levelString;
	
	private int levelStartTime;
	
	private boolean bIcarus;
	private ApoMarioPlayer icarus;
	private float icarusLastY;
	private float icarusLastVecY;
	private final ApoAnimation fireAnimation; 
	
	public ApoMarioLevel(ApoMarioComponent component) {
		this.component = component;
		this.fireAnimation = new ApoAnimation(this.component.getImages().getImage("images/fire.png", false), 0, 0, 3, 100);
		this.init();
		this.playerOneAI = this.HUMAN_NO_PLAYER;
		this.playerTwoAI = this.HUMAN_NO_PLAYER;
	}
	
	public void init() {
		this.thinkPlayerOne = new ArrayList<Long>();
		this.thinkPlayerTwo = new ArrayList<Long>();
		this.makeLevel((int)System.nanoTime(), true, true, 100, 0);
		//this.makeLevel(-1854288560, true, 528, 344);
		this.replay = new ApoMarioReplay();
		this.replay.setLevel(this.getWidth(), this.getDifficulty(), this.randomLevel.getLastRandom(), null);
		this.bAnalysis = false;
		this.bReplay = false;
		this.bIcarus = false;
		if (this.replayPlay == null) {
			this.replayPlay = new ApoMarioReplayPlay(this);
		}
		this.steps = 0;
	}
	
	public ApoMarioReplayPlay getReplayPlay() {
		return this.replayPlay;
	}

	public boolean isBReplay() {
		return this.bReplay;
	}

	public void setBReplay(boolean bReplay) {
		this.bReplay = bReplay;
	}

	public ApoMarioReplay getReplay() {
		return this.replay;
	}
	
	public void setReplay(ApoMarioReplay replay) {
		this.replay = replay;
	}

	/**
	 * gibt die Breite des Levels zurück
	 * @return die Breite des Levels
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * gibt die Höhe des Levels zurück
	 * @return
	 */
	public int getHeight() {
		return this.height;
	}

	public boolean isBStop() {
		return this.bStop;
	}

	public void setBStop(boolean bStop) {
		this.bStop = bStop;
	}

	public long getLevelInt() {
		return this.levelInt;
	}

	public ApoMarioComponent getComponent() {
		return this.component;
	}

	/**
	 * gibt eine ArrayList mit den Goodies zurück
	 * @return gibt eine ArrayList mit den Goodies zurück
	 */
	public ArrayList<ApoMarioEnemy> getGoodies() {
		return this.goodies;
	}

	/**
	 * gibt eine Arraylist mit den Gegnern zurück
	 * @return gibt eine Arraylist mit den Gegnern zurück
	 */
	public ArrayList<ApoMarioEnemy> getEnemies() {
		return this.enemies;
	}

	public void makeEmptyLevel() {
		this.makeEmptyLevel(this.getWidth(), this.getHeight(), this.getDifficulty());
	}
	
	public void makeEmptyLevel(int width, int height, int difficulty) {
		this.steps = 0;
		this.bIcarus = false;
		if (this.enemies == null) {
			this.enemies = new ArrayList<ApoMarioEnemy>();
		} else {
			this.enemies.clear();
		}
		if (this.goodies == null) {
			this.goodies = new ArrayList<ApoMarioEnemy>();
		} else {
			this.goodies.clear();
		}
		this.width = width;
		this.height = height;
		this.difficulty = difficulty;
		ApoMarioLevel.ID = 0;
		this.particle = new ArrayList<ApoMarioParticle>();
		this.bStop = false;
		
		this.cannons = new ArrayList<Point>();
		
		this.levelEntities = new ApoMarioEntity[this.height][this.width];
		
		for (int x = 0; x < this.width; x++) {
			this.makeGroundWall(x, this.height - 1);
		}
		this.makeGroundCorrect();
		
		this.setPlayerAndFinish();
		
		this.makeMyBackground();
	}
	
	public void setLevelEntities(ApoMarioEntity[][] levelEntities) {
		this.levelEntities = levelEntities;
	}

	public void setPlayerAndFinish() {
		this.makePlayer(3 * ApoMarioConstants.TILE_SIZE, (this.getHeight() - 3) * ApoMarioConstants.TILE_SIZE);
		this.makeFinish((this.getWidth() - 5) * ApoMarioConstants.TILE_SIZE, (this.getHeight() - 1) * ApoMarioConstants.TILE_SIZE);
	}
	
	/**
	 * erstellt ein zufälliges Level
	 * @param levelInt : Zufallszahl nach dem sich der Levelaufbau richtet
	 */
	public void makeLevel(long levelInt, boolean bNew, boolean bRealNew, int width, int difficulty) {
		this.steps = 0;
		this.bIcarus = false;
		this.thinkPlayerOne.clear();
		this.thinkPlayerTwo.clear();
		this.particle = new ArrayList<ApoMarioParticle>();
		this.bStop = false;
		if (this.cannons == null) {
			this.cannons = new ArrayList<Point>();
		}
		
		if ((this.levelInt == -1) && (!bRealNew)) {
			if (this.enemies != null) {
				for (int i = this.enemies.size() - 1; i >= 0; i--) {
					this.enemies.get(i).init();
					if (this.enemies.get(i) instanceof ApoMarioCannonCannon) {
						this.enemies.remove(i);
					} else if (this.enemies.get(i) instanceof ApoMarioShell) {
						this.enemies.remove(i);
					}
				}
			}
			this.goodies.clear();
			for (int x = 0; x < this.levelEntities[0].length; x++) {
				for (int y = 0; y < this.levelEntities.length; y++) {
					if (this.levelEntities[y][x] != null) {
						this.levelEntities[y][x].init();
					}
				}
			}
			for (int i = 0; i < this.players.size(); i++) {
				this.players.get(i).init();
			}
			this.time = 60000 + this.levelEntities[0].length * 400;
			this.levelStartTime = this.time;
		} else {
			if (this.enemies == null) {
				this.enemies = new ArrayList<ApoMarioEnemy>();
			} else {
				this.enemies.clear();
			}
			if (this.goodies == null) {
				this.goodies = new ArrayList<ApoMarioEnemy>();
			} else {
				this.goodies.clear();
			}
			ApoMarioLevel.ID = 0;
			this.particle = new ArrayList<ApoMarioParticle>();
			this.bStop = false;
			if (bRealNew) {
				this.cannons.clear();
			}
			
			if ((levelInt != this.levelInt) && (this.levelInt != -1) && (bNew) && (width == -1)) {
				this.width = (int)(Math.random() * 500 + 100);
				this.difficulty = (int)(Math.random() * 400);
			} else if (width > 0) {
				this.width = width;
				this.difficulty = difficulty;
			}
			this.levelInt = levelInt;
			this.height = 15;
			this.levelEntities = new ApoMarioEntity[this.height][this.width];
			for (int x = 0; x < this.width; x++) {
				this.makeGroundWall(x, this.height-1);
			}
			if (this.levelInt != -1) {
				this.randomLevel = new ApoMarioRandomLevel(this);
				this.randomLevel.setWidth(this.width);
				this.randomLevel.setDifficulty(this.difficulty);
				this.randomLevel.makeRandomLevel(levelInt);
				this.time = this.randomLevel.getTime();
				this.difficulty = this.randomLevel.getDifficulty();
			}
		}
		this.levelStartTime = this.time;
		this.makeMyBackground();
	}
	
	public void setLevelInt(long levelInt) {
		this.levelInt = levelInt;
	}

	protected void setTime(int time) {
		this.time = time;
	}

	private void makeMyBackground() {
		if (((ApoMarioPanel)(this.getComponent())).isBSimulate()) {
			return;
		}
		int imageWidth = 32;
		int imageHeight = 20;
		this.iBackground = this.component.getImages().getBackgroundBack(ApoMarioImageContainer.TILES_BACKGROUND, imageWidth, imageHeight);
		imageWidth = 40;
		this.iBackgroundFront = this.component.getImages().getBackgroundFront(ApoMarioImageContainer.TILES_BACKGROUND, imageWidth, imageHeight);
		
		this.iBoth = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(this.iBackgroundFront.getWidth(), this.iBackgroundFront.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D)this.iBoth.getGraphics();
		g.drawImage(this.iBackground, 0, 0, null);
		g.drawImage(this.iBackground, this.iBackground.getWidth(), 0, null);
		g.drawImage(this.iBackgroundFront, 0, 0, null);
		
		g.dispose();
	}
	
	public int getDifficulty() {
		return this.difficulty;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public int getStartTime() {
		return this.levelStartTime;
	}
	
	public int getTime() {
		return this.time;
	}

	protected void makeFinish(int x, int y) {
		int height = 10 * ApoMarioConstants.TILE_SIZE;
		this.finish = new ApoMarioEnd(x, y - height, 3 * ApoMarioConstants.TILE_SIZE, height, ++ApoMarioLevel.ID);
	}
	
	public void makeBoxQuestionMark(int x, int y, int goodie) {
		BufferedImage image = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(4 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = (Graphics2D)image.createGraphics();
		g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(4 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 4 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
		g.dispose();
		this.levelEntities[y][x] = new ApoMarioQuestionmark(image, ApoMarioImageContainer.TILES_LEVEL.getSubimage(4 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), x*ApoMarioConstants.TILE_SIZE, (y)*ApoMarioConstants.TILE_SIZE, goodie, ++ApoMarioLevel.ID);
	}
	
	public void makeBoxCoin(int x, int y) {
		BufferedImage image = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(4 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = (Graphics2D)image.createGraphics();
		g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 4 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
		g.dispose();
		BufferedImage particle = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( 3 * ApoMarioConstants.TILE_SIZE/2 * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE/2 * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
		g = (Graphics2D)particle.createGraphics();
		g.drawImage(ApoMarioImageContainer.PARTICLE.getSubimage(0 * ApoMarioConstants.TILE_SIZE/2 * ApoMarioConstants.APP_SIZE, 2 * ApoMarioConstants.TILE_SIZE/2 * ApoMarioConstants.APP_SIZE, 3 * ApoMarioConstants.TILE_SIZE/2 * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE/2 * ApoMarioConstants.APP_SIZE), 0, 0, null);
		g.dispose();
		this.levelEntities[y][x] = new ApoMarioCoin(image, particle, x*ApoMarioConstants.TILE_SIZE, (y)*ApoMarioConstants.TILE_SIZE, ApoMarioConstants.COIN_ANIMATION_FRAMES, ApoMarioConstants.COIN_ANIMATION_TIME, ++ApoMarioLevel.ID);
	}
	
	protected void makePlayer(float x, float y) {
		if (this.players == null) {
			this.players = new ArrayList<ApoMarioPlayer>();
			this.players.add(new ApoMarioPlayer(x, y, ApoMarioConstants.TILE_SIZE * 2, ApoMarioConstants.TILE_SIZE * 2, ApoMarioConstants.PLAYER_ANIMATION_FRAMES, ApoMarioConstants.PLAYER_ANIMATION_TIME, this.players.size(), ++ApoMarioLevel.ID));
			this.changePlayerOneAI(0);
			this.players.add(new ApoMarioPlayer(x, y, ApoMarioConstants.TILE_SIZE * 2, ApoMarioConstants.TILE_SIZE * 2, ApoMarioConstants.PLAYER_ANIMATION_FRAMES, ApoMarioConstants.PLAYER_ANIMATION_TIME, this.players.size(), ++ApoMarioLevel.ID));		
			this.changePlayerTwoAI(0);
		} else {
			for (int i = 0; i < this.players.size(); i++) {
				this.players.get(i).init();
			}
		}
	}

	public void makeGroundWall(int x, int y) {
		BufferedImage iWall = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D)iWall.getGraphics();
		g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 8 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
		g.dispose();
		this.levelEntities[y][x] = new ApoMarioWall(iWall, x*ApoMarioConstants.TILE_SIZE, (y)*ApoMarioConstants.TILE_SIZE, ++ApoMarioLevel.ID, true);				
	}
	
	public void makeGroundCorrect() {
		this.makeGroundCorrect(0, this.width, 0, this.height);
	}
	
	public void setNames() {
		for (int i = 0; i < this.players.size(); i++) {
			this.players.get(i).setAi(this.players.get(i).getAi());
		}
	}
	
	public void makeGroundCorrect(int startX, int endX, int startY, int endY) {
		try {
			if (startX < 0) {
				startX = 0;
			}
			if (startY < 0) {
				startY = 0;
			}
			if (endX > this.width) {
				endX = this.width;
			}
			if (endY > this.height) {
				endY = this.height;
			}
			for (int x = startX; x < endX; x++) {
				for (int y = endY - 1; y >= startY; y--) {
					if (this.isGroundWall(x, y)) {
						if (this.isGroundWall(x, y-1)) {
							if ((!this.isGroundWall(x - 1, y)) && (!this.isGroundWall(x + 1, y))) {
								BufferedImage iLeft = ApoMarioImageContainer.TILES_LEVEL.getSubimage(0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 9 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
								BufferedImage iRight = ApoMarioImageContainer.TILES_LEVEL.getSubimage(2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 9 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
								BufferedImage iNew = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(iLeft.getWidth(), iLeft.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
								Graphics2D g = (Graphics2D)(iNew.getGraphics());
								g.drawImage(iLeft.getSubimage(0, 0, iLeft.getWidth()/2, iLeft.getHeight()), 0, 0, null);
								g.drawImage(iRight.getSubimage(iRight.getWidth()/2, 0, iRight.getWidth()/2, iRight.getHeight()), iRight.getWidth()/2, 0, null);
								g.dispose();
								this.levelEntities[y][x].setIBackground(iNew);
							} else if (!this.isGroundWall(x - 1, y)) {
								if ((this.isGroundWall(x + 1, y) && ((!this.isGroundWall(x + 1, y - 1))))) {
									BufferedImage iLeft = ApoMarioImageContainer.TILES_LEVEL.getSubimage(0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 9 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
									BufferedImage iRight = ApoMarioImageContainer.TILES_LEVEL.getSubimage(3 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 9 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
									BufferedImage iNew = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(iRight.getWidth(), iRight.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
									Graphics2D g = (Graphics2D)iNew.getGraphics();
									g.drawImage(iLeft.getSubimage(0, 0, iLeft.getWidth()/2, iLeft.getHeight()), 0, 0, null);
									g.drawImage(iRight.getSubimage(iRight.getWidth()/2, 0, iRight.getWidth()/2, iRight.getHeight()), iRight.getWidth()/2, 0, null);
									g.dispose();
									this.levelEntities[y][x].setIBackground(iNew);
								} else {
									BufferedImage wall = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
									Graphics2D g = wall.createGraphics();
									g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 9 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
									g.dispose();
									this.levelEntities[y][x].setIBackground(wall);
								}
							} else if (!this.isGroundWall(x + 1, y)) {
								if ((this.isGroundWall(x - 1, y)) && (!this.isGroundWall(x - 1, y - 1))) {
									BufferedImage iLeft = ApoMarioImageContainer.TILES_LEVEL.getSubimage(3 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 8 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
									BufferedImage iRight = ApoMarioImageContainer.TILES_LEVEL.getSubimage(2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 9 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
									BufferedImage iNew = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(iRight.getWidth(), iRight.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
									Graphics2D g = (Graphics2D)iNew.getGraphics();
									g.drawImage(iLeft.getSubimage(0, 0, iLeft.getWidth()/2, iLeft.getHeight()), 0, 0, null);
									g.drawImage(iRight.getSubimage(iRight.getWidth()/2, 0, iRight.getWidth()/2, iRight.getHeight()), iRight.getWidth()/2, 0, null);
									g.dispose();
									this.levelEntities[y][x].setIBackground(iNew);
								} else {
									BufferedImage wall = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
									Graphics2D g = wall.createGraphics();
									g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 9 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
									g.dispose();
									this.levelEntities[y][x].setIBackground(wall);
								}
							} else if ((!this.isGroundWall(x + 1, y - 1)) && (!this.isGroundWall(x - 1, y - 1))) {
								BufferedImage iLeft = ApoMarioImageContainer.TILES_LEVEL.getSubimage(3 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 8 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
								BufferedImage iRight = ApoMarioImageContainer.TILES_LEVEL.getSubimage(3 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 9 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
								BufferedImage iNew = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(iRight.getWidth(), iRight.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
								Graphics2D g = (Graphics2D)iNew.getGraphics();
								g.drawImage(iLeft.getSubimage(0, 0, iLeft.getWidth()/2, iLeft.getHeight()), 0, 0, null);
								g.drawImage(iRight.getSubimage(iRight.getWidth()/2, 0, iRight.getWidth()/2, iRight.getHeight()), iRight.getWidth()/2, 0, null);
								g.dispose();
								this.levelEntities[y][x].setIBackground(iNew);
							} else if ((!this.isGroundWall(x + 1, y - 1))) {
								BufferedImage wall = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
								Graphics2D g = wall.createGraphics();
								g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(3 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 9 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
								g.dispose();
								this.levelEntities[y][x].setIBackground(wall);
							} else if ((!this.isGroundWall(x - 1, y - 1))) {
								BufferedImage wall = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
								Graphics2D g = wall.createGraphics();
								g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(3 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 8 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
								g.dispose();
								this.levelEntities[y][x].setIBackground(wall);
							} else {
								BufferedImage wall = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
								Graphics2D g = wall.createGraphics();
								g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 9 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
								g.dispose();
								this.levelEntities[y][x].setIBackground(wall);							
							}
						} else {
							if (((!this.isGroundWall(x + 1, y))) && ((!this.isGroundWall(x - 1, y))) && (y < endY - 1) && (!this.isGroundWall(x, y + 1))) {
								BufferedImage wall = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
								Graphics2D g = wall.createGraphics();
								g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(4 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
								g.dispose();
								this.levelEntities[y][x].setIBackground(wall);
							} else if (((!this.isGroundWall(x + 1, y))) && ((!this.isGroundWall(x - 1, y)))) {
								BufferedImage iRight = ApoMarioImageContainer.TILES_LEVEL.getSubimage(2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 8 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
								BufferedImage iLeft = ApoMarioImageContainer.TILES_LEVEL.getSubimage(0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 8 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
								BufferedImage iNew = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(iRight.getWidth(), iRight.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
								Graphics2D g = (Graphics2D)iNew.getGraphics();
								g.drawImage(iLeft.getSubimage(0, 0, iLeft.getWidth()/2, iLeft.getHeight()), 0, 0, null);
								g.drawImage(iRight.getSubimage(iRight.getWidth()/2, 0, iRight.getWidth()/2, iRight.getHeight()), iRight.getWidth()/2, 0, null);
								g.dispose();
								this.levelEntities[y][x].setIBackground(iNew);
							} else if ((!this.isGroundWall(x - 1, y))) {
								BufferedImage wall = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
								Graphics2D g = wall.createGraphics();
								g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 8 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
								g.dispose();
								this.levelEntities[y][x].setIBackground(wall);
							} else if ((!this.isGroundWall(x + 1, y))) {
								BufferedImage wall = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
								Graphics2D g = wall.createGraphics();
								g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 8 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
								g.dispose();
								this.levelEntities[y][x].setIBackground(wall);
							} else {
								BufferedImage wall = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
								Graphics2D g = wall.createGraphics();
								g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 8 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
								g.dispose();
								this.levelEntities[y][x].setIBackground(wall);
							}
						}
					} else {
						//break;
					}
				}
			}
		} catch (Exception ex) {
			
		}
	}

	public void makeGroundCorrectNoCollision() {
		this.makeGroundCorrectNoCollision(0, this.width, 0, this.height);
	}
	
	public void makeGroundCorrectNoCollision(int startX, int endX, int startY, int endY) {
		try {
			if (startX < 0) {
				startX = 0;
			}
			if (startY < 0) {
				startY = 0;
			}
			if (endX > this.width) {
				endX = this.width;
			}
			if (endY > this.height) {
				endY = this.height;
			}
			for (int x = startX; x < endX; x++) {
				for (int y = endY - 1; y >= startY; y--) {
					if (this.isGroundWallNoCollision(x, y)) {
						if (this.isGroundWallNoCollision(x, y-1)) {
							if ((!this.isGroundWallNoCollision(x - 1, y)) && (!this.isGroundWallNoCollision(x + 1, y))) {
								BufferedImage iLeft = ApoMarioImageContainer.TILES_LEVEL.getSubimage(4 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 9 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
								BufferedImage iRight = ApoMarioImageContainer.TILES_LEVEL.getSubimage(6 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 9 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
								BufferedImage iNew = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(iLeft.getWidth(), iLeft.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
								Graphics2D g = (Graphics2D)(iNew.getGraphics());
								g.drawImage(iLeft.getSubimage(0, 0, iLeft.getWidth()/2, iLeft.getHeight()), 0, 0, null);
								g.drawImage(iRight.getSubimage(iRight.getWidth()/2, 0, iRight.getWidth()/2, iRight.getHeight()), iRight.getWidth()/2, 0, null);
								g.dispose();
								this.levelEntities[y][x].setIBackground(iNew);
							} else if (!this.isGroundWallNoCollision(x - 1, y)) {
								if ((this.isGroundWallNoCollision(x + 1, y) && ((!this.isGroundWallNoCollision(x + 1, y - 1))))) {
									BufferedImage iLeft = ApoMarioImageContainer.TILES_LEVEL.getSubimage(4 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 9 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
									BufferedImage iRight = ApoMarioImageContainer.TILES_LEVEL.getSubimage(3 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 9 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
									BufferedImage iNew = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(iRight.getWidth(), iRight.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
									Graphics2D g = (Graphics2D)iNew.getGraphics();
									g.drawImage(iLeft.getSubimage(0, 0, iLeft.getWidth()/2, iLeft.getHeight()), 0, 0, null);
									g.drawImage(iRight.getSubimage(iRight.getWidth()/2, 0, iRight.getWidth()/2, iRight.getHeight()), iRight.getWidth()/2, 0, null);
									g.dispose();
									this.levelEntities[y][x].setIBackground(iNew);
								} else {
									BufferedImage wall = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
									Graphics2D g = wall.createGraphics();
									g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(4 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 9 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
									g.dispose();
									this.levelEntities[y][x].setIBackground(wall);
								}
							} else if (!this.isGroundWallNoCollision(x + 1, y)) {
								if ((this.isGroundWallNoCollision(x - 1, y)) && (!this.isGroundWallNoCollision(x - 1, y - 1))) {
									BufferedImage iLeft = ApoMarioImageContainer.TILES_LEVEL.getSubimage(3 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 8 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
									BufferedImage iRight = ApoMarioImageContainer.TILES_LEVEL.getSubimage(6 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 9 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
									BufferedImage iNew = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(iRight.getWidth(), iRight.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
									Graphics2D g = (Graphics2D)iNew.getGraphics();
									g.drawImage(iLeft.getSubimage(0, 0, iLeft.getWidth()/2, iLeft.getHeight()), 0, 0, null);
									g.drawImage(iRight.getSubimage(iRight.getWidth()/2, 0, iRight.getWidth()/2, iRight.getHeight()), iRight.getWidth()/2, 0, null);
									g.dispose();
									this.levelEntities[y][x].setIBackground(iNew);
								} else {
									BufferedImage wall = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
									Graphics2D g = wall.createGraphics();
									g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(6 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 9 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
									g.dispose();
									this.levelEntities[y][x].setIBackground(wall);
								}
							} else if ((!this.isGroundWallNoCollision(x + 1, y - 1)) && (!this.isGroundWallNoCollision(x - 1, y - 1))) {
								BufferedImage iLeft = ApoMarioImageContainer.TILES_LEVEL.getSubimage(3 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 8 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
								BufferedImage iRight = ApoMarioImageContainer.TILES_LEVEL.getSubimage(3 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 9 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
								BufferedImage iNew = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(iRight.getWidth(), iRight.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
								Graphics2D g = (Graphics2D)iNew.getGraphics();
								g.drawImage(iLeft.getSubimage(0, 0, iLeft.getWidth()/2, iLeft.getHeight()), 0, 0, null);
								g.drawImage(iRight.getSubimage(iRight.getWidth()/2, 0, iRight.getWidth()/2, iRight.getHeight()), iRight.getWidth()/2, 0, null);
								g.dispose();
								this.levelEntities[y][x].setIBackground(iNew);
							} else if ((!this.isGroundWallNoCollision(x + 1, y - 1))) {
								BufferedImage wall = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
								Graphics2D g = wall.createGraphics();
								g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(3 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 9 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
								g.dispose();
								this.levelEntities[y][x].setIBackground(wall);
							} else if ((!this.isGroundWallNoCollision(x - 1, y - 1))) {
								BufferedImage wall = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
								Graphics2D g = wall.createGraphics();
								g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(3 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 8 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
								g.dispose();
								this.levelEntities[y][x].setIBackground(wall);
							} else {
								BufferedImage wall = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
								Graphics2D g = wall.createGraphics();
								g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(5 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 9 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
								g.dispose();
								this.levelEntities[y][x].setIBackground(wall);							
							}
							((ApoMarioWall)(this.levelEntities[y][x])).setBNoCollision(true);
							((ApoMarioWall)(this.levelEntities[y][x])).setBOnlyAboveWall(false);
						} else {
							if (((!this.isGroundWallNoCollision(x + 1, y))) && ((!this.isGroundWallNoCollision(x - 1, y)))) {
								BufferedImage iRight = ApoMarioImageContainer.TILES_LEVEL.getSubimage(6 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 8 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
								BufferedImage iLeft = ApoMarioImageContainer.TILES_LEVEL.getSubimage(4 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 8 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
								BufferedImage iNew = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(iRight.getWidth(), iRight.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
								Graphics2D g = (Graphics2D)iNew.getGraphics();
								g.drawImage(iLeft.getSubimage(0, 0, iLeft.getWidth()/2, iLeft.getHeight()), 0, 0, null);
								g.drawImage(iRight.getSubimage(iRight.getWidth()/2, 0, iRight.getWidth()/2, iRight.getHeight()), iRight.getWidth()/2, 0, null);
								g.dispose();
								this.levelEntities[y][x].setIBackground(iNew);
							} else if ((!this.isGroundWallNoCollision(x - 1, y))) {
								BufferedImage wall = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
								Graphics2D g = wall.createGraphics();
								g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(4 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 8 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
								g.dispose();
								this.levelEntities[y][x].setIBackground(wall);
							} else if ((!this.isGroundWallNoCollision(x + 1, y))) {
								BufferedImage wall = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
								Graphics2D g = wall.createGraphics();
								g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(6 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 8 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
								g.dispose();
								this.levelEntities[y][x].setIBackground(wall);
							} else {
								BufferedImage wall = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
								Graphics2D g = wall.createGraphics();
								g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(5 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 8 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
								g.dispose();
								this.levelEntities[y][x].setIBackground(wall);
							}
							((ApoMarioWall)(this.levelEntities[y][x])).setBNoCollision(false);
							((ApoMarioWall)(this.levelEntities[y][x])).setBOnlyAboveWall(true);
						}
					} else {
						//break;
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private boolean isGroundWall(int x, int y) {
		if ((y >= 0) && (y < this.height) &&
			(x >= 0) && (x < this.width) &&
			(this.levelEntities[y][x] != null) && (this.levelEntities[y][x] instanceof ApoMarioWall) && (((ApoMarioWall)(this.levelEntities[y][x])).isBGround())) {
			return true;
		}
		
		return false;
	}
	
	private boolean isGroundWallNoCollision(int x, int y) {
		if ((y >= 0) && (y < this.height) &&
			(x >= 0) && (x < this.width) &&
			(this.levelEntities[y][x] != null) && (this.levelEntities[y][x] instanceof ApoMarioWall)) {
				ApoMarioWall wall = (ApoMarioWall)(this.levelEntities[y][x]);
			if ((wall.isBNoCollision()) || (wall.isBOnlyAboveWall())) {
				return true;
			}
		}
		
		return false;
	}
	
	public void makeWall(BufferedImage iWall, boolean bOnlyAbove, boolean bNoCollision, boolean bDestructable, boolean bNull, int goodie, int x, int y) {
		if (bDestructable) {
			if (bNull) {
				this.levelEntities[y][x] = new ApoMarioDestructableWall(iWall, null, x*ApoMarioConstants.TILE_SIZE, (y)*ApoMarioConstants.TILE_SIZE, ApoMarioConstants.WALL_DESTRUCABLE_ANIMATION_FRAMES, ApoMarioConstants.WALL_DESTRUCABLE_ANIMATION_TIME, ++ApoMarioLevel.ID);				
			} else {
				this.levelEntities[y][x] = new ApoMarioDestructableWall(iWall, ApoMarioImageContainer.TILES_LEVEL.getSubimage(4 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), x*ApoMarioConstants.TILE_SIZE, (y)*ApoMarioConstants.TILE_SIZE, ApoMarioConstants.WALL_DESTRUCABLE_ANIMATION_FRAMES, ApoMarioConstants.WALL_DESTRUCABLE_ANIMATION_TIME, ++ApoMarioLevel.ID);	
				if (goodie >= 0) {
					((ApoMarioWall)(this.levelEntities[y][x])).setGoodie(goodie);
				}
			}
		} else {
			this.levelEntities[y][x] = new ApoMarioWall(iWall, x*ApoMarioConstants.TILE_SIZE, (y)*ApoMarioConstants.TILE_SIZE, ++ApoMarioLevel.ID, false);
			((ApoMarioWall)(this.levelEntities[y][x])).setBOnlyAboveWall(bOnlyAbove);
			((ApoMarioWall)(this.levelEntities[y][x])).setBNoCollision(bNoCollision);
		}
	}
	
	public void makeWall(boolean bDestructable, boolean bNull, int goodie, int x, int y) {
		if (bDestructable) {
			BufferedImage image = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(4 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
			Graphics2D g = image.createGraphics();
			g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 4 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
			g.dispose();
			if (bNull) {
				this.levelEntities[y][x] = new ApoMarioDestructableWall(image, null, x*ApoMarioConstants.TILE_SIZE, (y)*ApoMarioConstants.TILE_SIZE, ApoMarioConstants.WALL_DESTRUCABLE_ANIMATION_FRAMES, ApoMarioConstants.WALL_DESTRUCABLE_ANIMATION_TIME, ++ApoMarioLevel.ID);				
			} else {
				BufferedImage wall = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
				g = wall.createGraphics();
				g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(4 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
				g.dispose();
				this.levelEntities[y][x] = new ApoMarioDestructableWall(image, wall, x*ApoMarioConstants.TILE_SIZE, (y)*ApoMarioConstants.TILE_SIZE, ApoMarioConstants.WALL_DESTRUCABLE_ANIMATION_FRAMES, ApoMarioConstants.WALL_DESTRUCABLE_ANIMATION_TIME, ++ApoMarioLevel.ID);	
				if (goodie >= 0) {
					((ApoMarioWall)(this.levelEntities[y][x])).setGoodie(goodie);
				}
			}
			((ApoMarioDestructableWall)(this.levelEntities[y][x])).setTimeToRepeat(ApoMarioConstants.WALL_DESTRUCABLE_REPEATTIME + (int)(Math.random() * 1000));
		} else {
			BufferedImage wall = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
			Graphics2D g = wall.createGraphics();
			g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(4 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
			g.dispose();
			this.levelEntities[y][x] = new ApoMarioWall(wall, x*ApoMarioConstants.TILE_SIZE, (y)*ApoMarioConstants.TILE_SIZE, ++ApoMarioLevel.ID, false);				
		}
	}
	
	public void makeKoopa(boolean bRed, int x, int y, boolean bFall, boolean bFly, boolean bLeft) {
		BufferedImage iEnemy = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = iEnemy.createGraphics();
		if (!bRed) {
			g.drawImage(ApoMarioImageContainer.ENEMIES.getSubimage(0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);			
		} else {
			g.drawImage(ApoMarioImageContainer.ENEMIES.getSubimage(0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
		}
		g.dispose();
		this.enemies.add(new ApoMarioKoopa(iEnemy, x*ApoMarioConstants.TILE_SIZE, (y)*ApoMarioConstants.TILE_SIZE, 1*ApoMarioConstants.TILE_SIZE, 2*ApoMarioConstants.TILE_SIZE, ApoMarioConstants.KOOPA_ANIMATION_FRAMES, ApoMarioConstants.KOOPA_ANIMATION_TIME, bFall, bFly, bLeft, !bRed, ++ApoMarioLevel.ID));		
	}
	
	public void makeGumba(int x, int y, boolean bFall, boolean bFly, boolean bLeft) {
		BufferedImage iGumba = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = iGumba.createGraphics();
		if ((ApoMarioImageContainer.TILE_SET == ApoMarioConstants.TILE_SET_EAT) && (Math.random() * 100 > 50)) {
			g.drawImage(ApoMarioImageContainer.ENEMIES.getSubimage(2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 5 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);			
		} else {
			g.drawImage(ApoMarioImageContainer.ENEMIES.getSubimage(0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 5 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
		}
		g.dispose();
		this.enemies.add(new ApoMarioGumba(iGumba, x*ApoMarioConstants.TILE_SIZE, (y)*ApoMarioConstants.TILE_SIZE, 1*ApoMarioConstants.TILE_SIZE, 1*ApoMarioConstants.TILE_SIZE, ApoMarioConstants.GUMBA_ANIMATION_FRAMES, ApoMarioConstants.GUMBA_ANIMATION_TIME, bFall, bFly, bLeft, ++ApoMarioLevel.ID));		
	}
	
	public void makeImmortal(int x, int y, boolean bFall, boolean bFly, boolean bLeft) {
		BufferedImage iImmortal = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = iImmortal.createGraphics();
		g.drawImage(ApoMarioImageContainer.ENEMIES.getSubimage(0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 7 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
		g.dispose();
		this.enemies.add(new ApoMarioGumba(iImmortal, x*ApoMarioConstants.TILE_SIZE, (y)*ApoMarioConstants.TILE_SIZE, 1*ApoMarioConstants.TILE_SIZE, 1*ApoMarioConstants.TILE_SIZE, ApoMarioConstants.GUMBA_ANIMATION_FRAMES, ApoMarioConstants.GUMBA_ANIMATION_TIME, bFall, bFly, bLeft, ++ApoMarioLevel.ID));		
		this.enemies.get(this.enemies.size() - 1).setBImmortal(true);
	}
	
	public void makeCanon(int x, int y, int height, int shootTime, int startTime) {
		BufferedImage iCanon = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = iCanon.createGraphics();
		g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(14 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
		g.dispose();
		BufferedImage iCanonCanon = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
		g = iCanonCanon.createGraphics();
		g.drawImage(ApoMarioImageContainer.ENEMIES.getSubimage(0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 11 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
		g.dispose();
		ApoMarioCannon canon = new ApoMarioCannon(iCanon, iCanonCanon, height, x*ApoMarioConstants.TILE_SIZE, (y)*ApoMarioConstants.TILE_SIZE, shootTime, startTime, ++ApoMarioLevel.ID);;
		canon.setBCanon(true);
		canon.setTubePoint(new Point(0, 0));
		this.levelEntities[y][x] = canon;
		
		this.cannons.add(new Point(x, y));
		
		if ((height >= 2) && (y + 1 < this.height) && (this.levelEntities[y+1][x] == null)) {
			BufferedImage iWall = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
			g = iWall.createGraphics();
			g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(14 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
			g.dispose();
			ApoMarioWall wall = new ApoMarioWall(iWall, x*ApoMarioConstants.TILE_SIZE, (y+1)*ApoMarioConstants.TILE_SIZE, ++ApoMarioLevel.ID, false);
			wall.setBCanon(true);
			wall.setTubePoint(new Point(0, 1));
			this.levelEntities[y+1][x] = wall;
			for (int i = 2; i < height; i++) {
				if ((y + i < this.height) && (this.levelEntities[y+i][x] == null)) {
					BufferedImage iWallWall = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
					g = iWallWall.createGraphics();
					g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(14 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
					g.dispose();
					wall = new ApoMarioWall(iWallWall, x*ApoMarioConstants.TILE_SIZE, (y+i)*ApoMarioConstants.TILE_SIZE, ++ApoMarioLevel.ID, false);
					wall.setBCanon(true);
					wall.setTubePoint(new Point(0, i));
					this.levelEntities[y+i][x] = wall;
				} else {
					return;
				}
			}
		}
	}
	
	public void makeFlower(int x, int y, int height, int timeToShow, boolean bWithFlower, int startTime) {
		if (bWithFlower) {
			BufferedImage iFlower = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(4 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
			Graphics2D g = iFlower.createGraphics();
			g.drawImage(ApoMarioImageContainer.ENEMIES.getSubimage(0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 12 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 4 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
			g.dispose();
			this.enemies.add(new ApoMarioFlower(iFlower, (int)((x + 0.5f) * ApoMarioConstants.TILE_SIZE), (int)(y * ApoMarioConstants.TILE_SIZE), ApoMarioConstants.TILE_SIZE, 2 * ApoMarioConstants.TILE_SIZE, ApoMarioConstants.ENEMY_ANIMATION_FRAMES, ApoMarioConstants.FLOWER_ANIMATION_TIME, timeToShow, startTime, ++ApoMarioLevel.ID));
		}
		
		BufferedImage iWallLeft = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = iWallLeft.createGraphics();
		g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(10 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
		g.dispose();
		ApoMarioWall wall = new ApoMarioWall(iWallLeft, x*ApoMarioConstants.TILE_SIZE, (y)*ApoMarioConstants.TILE_SIZE, ++ApoMarioLevel.ID, false);
		wall.setBTube(true);
		wall.setTubePoint(new Point(0, 0));
		this.levelEntities[y][x] = wall;
		BufferedImage iWallRight = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
		g = iWallRight.createGraphics();
		g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(11 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
		g.dispose();
		wall = new ApoMarioWall(iWallRight, (x+1)*ApoMarioConstants.TILE_SIZE, (y)*ApoMarioConstants.TILE_SIZE, ++ApoMarioLevel.ID, false);
		wall.setBTube(true);
		wall.setTubePoint(new Point(1, 0));
		this.levelEntities[y][x+1] = wall;
		for (int i = 1; i < height; i++) {
			BufferedImage iWallLeftLeft = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
			g = iWallLeftLeft.createGraphics();
			g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(10 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
			g.dispose();
			wall = new ApoMarioWall(iWallLeftLeft, x*ApoMarioConstants.TILE_SIZE, (y+i)*ApoMarioConstants.TILE_SIZE, ++ApoMarioLevel.ID, false);
			wall.setBTube(true);
			wall.setTubePoint(new Point(0, i));
			this.levelEntities[y+i][x] = wall;
			BufferedImage iWallRightRight = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
			g = iWallRightRight.createGraphics();
			g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(11 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), 0, 0, null);
			g.dispose();
			wall = new ApoMarioWall(iWallRightRight, (x+1)*ApoMarioConstants.TILE_SIZE, (y+i)*ApoMarioConstants.TILE_SIZE, ++ApoMarioLevel.ID, false);
			wall.setBTube(true);
			wall.setTubePoint(new Point(1, i));
			this.levelEntities[y+i][x+1] = wall;			
		}
	}
	
	public void unloadPlayer(int player) {
		if (player == ApoMarioConstants.PLAYER_ONE) {
			this.playerOneAI = this.HUMAN_NO_PLAYER;
			ApoMarioConstants.AI_LEFT = String.valueOf(this.playerOneAI);
			this.AIOne = null;
			this.getPlayers().get(0).setAi(this.AIOne);
		} else if (player == ApoMarioConstants.PLAYER_TWO) {
			this.playerTwoAI = this.HUMAN_NO_PLAYER;
			ApoMarioConstants.AI_RIGHT = String.valueOf(this.playerTwoAI);
			this.AITwo = null;
			this.players.get(1).setAi(null);
			this.players.get(1).setAi(this.AITwo);
		}
	}
	
	public void changePlayerOneAI(int plus) {
		if ((plus == 0) && (this.AIOne != null)) {
			this.getPlayers().get(0).setAi(this.AIOne);
			return;
		}
		this.playerOneAI += plus;
		if (this.playerOneAI < 0) {
			this.playerOneAI = this.TEST_CHEATER;
			if (ApoConstants.B_APPLET) {
				this.playerOneAI = this.TEST_SHORTI;
			}
		} else if (ApoConstants.B_APPLET) {
			if (this.playerOneAI > this.TEST_SHORTI) {
				this.playerOneAI = 0;
			}
		} else if (this.playerOneAI > this.TEST_CHEATER) {
			this.playerOneAI = 0;
		}
		if (this.playerOneAI == this.HUMAN_NO_PLAYER) {
			this.AIOne = null;
		} else if (this.playerOneAI == this.TEST_SLOW_PLAYER) {
			this.AIOne = new Test2();
		} else if (this.playerOneAI == this.TEST_IF_PLAYER) {
			this.AIOne = new TestIf();
		} else if (this.playerOneAI == this.TEST_FAST_PLAYER) {
			this.AIOne = new Test();
		} else if (this.playerOneAI == this.TEST_SHORTI) {
			this.AIOne = new TestSolve();
		} else if (this.playerOneAI == this.TEST_CHEATER) {
			this.AIOne = new IcarusAI();
		}
		ApoMarioConstants.AI_LEFT = String.valueOf(this.playerOneAI);
		this.getPlayers().get(0).setAi(this.AIOne);
	}
	
	public void setPlayerOneAI(int ai) {
		this.playerOneAI = ai;
	}
	
	public void setPlayerTwoAI(int ai) {
		this.playerTwoAI = ai;
	}
	
	public void changePlayerTwoAI(int plus) {
		if ((plus == 0) && (this.AITwo != null)) {
			this.getPlayers().get(1).setAi(null);
			this.getPlayers().get(1).setAi(this.AITwo);
			return;
		}
		this.playerTwoAI += plus;
		if (this.playerTwoAI < 0) {
			this.playerTwoAI = this.TEST_CHEATER;
			if (ApoConstants.B_APPLET) {
				this.playerTwoAI = this.TEST_SHORTI;
			}
		} else if (ApoConstants.B_APPLET) {
			if (this.playerTwoAI > this.TEST_SHORTI) {
				this.playerTwoAI = 0;
			}
		} else if (this.playerTwoAI > this.TEST_CHEATER) {
			this.playerTwoAI = 0;
		}
		if (this.playerTwoAI == this.HUMAN_NO_PLAYER) {
			this.AITwo = null;
		} else if (this.playerTwoAI == this.TEST_SLOW_PLAYER) {
			this.AITwo = new Test2();
		} else if (this.playerTwoAI == this.TEST_FAST_PLAYER) {
			this.AITwo = new Test();
		} else if (this.playerTwoAI == this.TEST_IF_PLAYER) {
			this.AITwo = new TestIf();
		} else if (this.playerTwoAI == this.TEST_SHORTI) {
			this.AITwo = new TestSolve();
		} else if (this.playerTwoAI == this.TEST_CHEATER) {
			this.AITwo = new IcarusAI();
		}
		this.players.get(1).setAi(null);
		this.players.get(1).setAi(this.AITwo);
		ApoMarioConstants.AI_RIGHT = String.valueOf(this.playerTwoAI);
	}
	
	/**
	 * laedt eine PlayerAI
	 * @param player = fÃ¼r welchen Spieler
	 * @param s = falls != NULL dann lade den Ã¼bergeben String
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void loadPlayer(int player, String s) {
		String res = null;
		String source = null;
		String playerName = null;
		String className = null;
		if (s == null) {
			if (this.getComponent().getFileChooser().showOpenDialog(this.getComponent()) == JFileChooser.APPROVE_OPTION) {
				res = this.getComponent().getFileChooser().getSelectedFile().getPath();
				className = res.substring(res.lastIndexOf("."));
				res = res.substring( 0, res.lastIndexOf("."));
				source = res.substring( 0, res.lastIndexOf( File.separator ) ) + File.separator;
				res = res.substring( res.lastIndexOf( File.separator ) + 1 );
			} else {
				return;
			}
			if ((res == null) || (res.length() <= 0)) {
				return;    	
			}
			playerName	= res;
		} else {	
			res	= s;
	    	playerName = res.substring(res.lastIndexOf("\\") + 1, res.lastIndexOf("."));
			source = res.substring(0,res.lastIndexOf("\\") + 1);
			className = res.substring(res.lastIndexOf("."));
		}
		this.loadPlayer(player, source, playerName, className);
	}
	
	/**
	 * laed einen Spieler, wobei Ã¼bergeben wird welches Team es ist, wie der Pfad lautet und wie die KI lautet
	 * @param bHome : Boolean Variable, die angibt ob die KI zum Home- oder VisitorTeam gehÃ¶rt
	 * @param path : Der Pfad wo die KI drinliegt
	 * @param classname : Der Klassenname
	 * @return immer TRUE
	 */
	public boolean loadPlayer(int player, String path, String classname) {
		return this.loadPlayer(player, path, classname, ".class");
	}
	
	/**
	 * laed einen Spieler, wobei Ã¼bergeben wird welches Team es ist, wie der Pfad lautet und wie die KI lautet
	 * @param bHome : Boolean Variable, die angibt ob die KI zum Home- oder VisitorTeam gehÃ¶rt
	 * @param path : Der Pfad wo die KI drinliegt
	 * @param classname : Der Klassenname
	 * @return immer TRUE
	 */
	public boolean loadPlayer(int player, String path, String classname, String classN ) {
    	ApoMarioAI apoMarioAI = null;
//    	System.out.println(path+" "+classname+" "+classN);
		if ((classN != null) && (classN.length() > 0) && (classN.indexOf(".class") != -1)) {
			if (!ApoConstants.B_APPLET) {
				apoMarioAI = (ApoMarioAI)(new ApoMarioClassLoader(path, classname).getAI());
			}
		}
		try {
        	if (apoMarioAI != null) {
        		if (player == 0) {
        			this.AIOne = apoMarioAI;
        			this.getPlayers().get(player).setAi(apoMarioAI, path);
        		} else {
        			this.AITwo = apoMarioAI;
        			this.getPlayers().get(player).setAi(null);
        			this.getPlayers().get(player).setAi(apoMarioAI, path);
        		}
        		if (player == ApoMarioConstants.PLAYER_ONE) {
        			ApoMarioConstants.AI_LEFT = path + classname + classN;
       			} else if (player == ApoMarioConstants.PLAYER_TWO) {
       				ApoMarioConstants.AI_RIGHT = path + classname + classN;
       			}
        		ApoMarioConstants.saveProperties();
        	}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public void addParticle(ApoMarioParticle particle) {
		this.particle.add(particle);
	}
	
	private void levelRestart(boolean bReplay) {
		if (this.getComponent().getDebugFrame() != null) {
			this.getComponent().getDebugFrame().clear();
		}
		this.bAnalysis = true;

		if (bReplay) {
			this.replayPlay.initReplay();
		} else {
			this.replay.init();
			if (this.levelInt != -1) {
				this.replay.setLevel(this.getWidth(), this.getDifficulty(), this.randomLevel.getLastRandom(), null);
			} else {
				this.replay.setLevel(this.getWidth(), this.getDifficulty(), -1l, this.replay.getLevelString());				
			}
		}
	}
	
	public void restart(boolean bReplay) {
		this.getComponent().setBPause(false);
		if (!bReplay) {
			this.makeLevel(this.levelInt, true, false, -1, -1);
		}
		this.component.setChangeX(0);
		this.setBReplay(bReplay);
		this.levelRestart(bReplay);
	}
	
	public void newLevel() {
		this.levelString = null;
		this.makeLevel((int)(System.nanoTime()), true, true, -1, -1);
		this.levelRestart(false);
	}
	
	public void newSameLevel() {
		this.levelString = null;
		this.makeLevel((int)(System.nanoTime()), true, true, this.width, this.difficulty);
		this.levelRestart(false);
	}
	
	public void setLevelString(int width, String levelString) {
		this.levelString = levelString;
		if (this.levelString != null) {
			this.width = width;
			this.difficulty = 0;
			this.levelString = levelString;
			this.replay.setLevel(width, 0, -1, levelString);
		}
	}

	public ArrayList<Point> getCannons() {
		return this.cannons;
	}

	public ApoMarioEnd getFinish() {
		return this.finish;
	}
	
	public ApoMarioEntity[][] getLevelEntities() {
		return this.levelEntities;
	}

	public ArrayList<ApoMarioPlayer> getPlayers() {
		return this.players;
	}

	public int getPassedTime() {
		return (this.randomLevel.getTime() - this.time);
	}
	
	public void setAnalysis(boolean bWin) {
		if (!this.bAnalysis) {
			return;
		}
		//System.out.println(this.steps);
		if (this.component instanceof ApoMarioPanel) {
			int addTimeDifficultyWidthPoints = (int)(ApoMarioConstants.POINT_TIME_MULTIPLICATOR * (float)this.getTime() + ApoMarioConstants.POINT_DIFFICULTY_MULTIPLICATOR * (float)this.getDifficulty() + ApoMarioConstants.POINT_WIDTH_MULTIPLICATOR * (float)this.getWidth());
			this.bAnalysis = false;
			this.component.setBWin(bWin);
			ApoMarioPanel game = ((ApoMarioPanel)(this.component));
			ApoMarioPlayer playerOne = this.getPlayers().get(0);
			ApoMarioPlayer playerTwo = this.getPlayers().get(1);
			float percentOne = (float)playerOne.getX() / ((float)ApoMarioConstants.TILE_SIZE * (float)this.getWidth());
			float percentTwo = (float)playerTwo.getX() / ((float)ApoMarioConstants.TILE_SIZE * (float)this.getWidth());
			if (!this.isOnePlayer()) {
				if (bWin) {
					if ((playerOne.getX() > playerTwo.getX()) && (playerOne.isBVisible())) {
						playerOne.setPoints(playerOne.getPoints() + this.getFinish().getPoints() + addTimeDifficultyWidthPoints);
						playerTwo.setPoints(playerTwo.getPoints() + (int)(addTimeDifficultyWidthPoints * percentTwo));
					} else if ((playerOne.getX() < playerTwo.getX()) && (playerTwo.isBVisible())) {
						playerTwo.setPoints(playerTwo.getPoints() + this.getFinish().getPoints() + addTimeDifficultyWidthPoints);
						playerOne.setPoints(playerOne.getPoints() + (int)(addTimeDifficultyWidthPoints * percentOne));
					} else if (playerOne.getX() == playerTwo.getX()) {
						if (playerOne.getPoints() > playerTwo.getPoints()) {
							playerOne.setPoints(playerOne.getPoints() + this.getFinish().getPoints() + addTimeDifficultyWidthPoints);
							playerTwo.setPoints(playerTwo.getPoints() + (int)(addTimeDifficultyWidthPoints * percentTwo));
						} else if (playerOne.getPoints() < playerTwo.getPoints()) {
							playerTwo.setPoints(playerTwo.getPoints() + this.getFinish().getPoints() + addTimeDifficultyWidthPoints);
							playerOne.setPoints(playerOne.getPoints() + (int)(addTimeDifficultyWidthPoints * percentOne));
						} else {
							if (playerOne.getCoins() > playerTwo.getCoins()) {
								playerOne.setPoints(playerOne.getPoints() + this.getFinish().getPoints() + addTimeDifficultyWidthPoints);
								playerTwo.setPoints(playerTwo.getPoints() + (int)(addTimeDifficultyWidthPoints * percentTwo));
							} else if (playerOne.getCoins() < playerTwo.getCoins()) {
								playerTwo.setPoints(playerTwo.getPoints() + this.getFinish().getPoints() + addTimeDifficultyWidthPoints);
								playerOne.setPoints(playerOne.getPoints() + (int)(addTimeDifficultyWidthPoints * percentOne));
							}
						}
					}
				} else {
					if ((!playerOne.isBVisible()) && (playerTwo.isBVisible())) {
						playerTwo.setPoints(playerTwo.getPoints() + ApoMarioConstants.POINTS_WIN_AGAINST_ANOTHER);
					} else if ((playerOne.isBVisible()) && (!playerTwo.isBVisible())) {
						playerOne.setPoints(playerOne.getPoints() + ApoMarioConstants.POINTS_WIN_AGAINST_ANOTHER);
					}
				}
			} else {
				if (bWin) {
					playerOne.setPoints(playerOne.getPoints() + this.getFinish().getPoints() + addTimeDifficultyWidthPoints);
				}
			}
			if (game.isBEditor()) {
				game.setEditor();
			} else {
				game.setAnalysis();
			}
		}
	}
	
	private boolean isOnePlayer() {
		if ((!this.isBReplay()) && (this.getPlayers().get(1).getAi() == null))  {
			return true;
		}
		if  ((this.isBReplay()) && (Math.abs(this.getReplay().getPlayerTwo().getMaxSteps() - this.getReplay().getPlayerOne().getMaxSteps()) >= 2)) {			
			return true;
		}
		return false;
	}
	
	public boolean isBAnalysis() {
		return this.bAnalysis;
	}

	/**
	 * die eigentliche Levellogik
	 * @param delta : Zeit seit dem letzten Aufruf
	 */
	public synchronized void think(int delta) {
		if (this.bStop) {
			return;
		}
		this.thinkTime(delta);
		this.thinkPlayer(delta);
		this.thinkFinish(delta);
		this.thinkEnemies(delta);
		this.thinkGoodies(delta);
		this.thinkLevelEntities(delta);
		this.thinkParticle(delta);
	}
	
	private void thinkTime(int delta) {
		this.time -= delta;
		if (this.time <= 0) {
			this.setAnalysis(false);
		}
		this.replayPlay.think(delta);
	}	
	
	private synchronized void thinkPlayer(int delta) {
		if (this.isBReplay()) {
			if ((this.getReplay().getPlayerTwo().getReplayStep().size() <= 0) && (this.players.get(1).isBVisible())) {
				this.players.get(1).setBVisible(false);
			}
			this.players.get(0).thinkAI(delta, this);
		} else {
			for (int i = 0; i < this.players.size(); i++) {
				if ((i > 0) && (this.players.get(i).getAi() == null)) {
					if (this.players.get(i).isBVisible()) {
						this.players.get(i).setBVisible(false);
					}
					break;
				}
				if ((this.players.get(i).isBVisible()) && (!this.players.get(i).isBDie())) {
					//System.out.print("in thinkPlayer -> ");
					long nano = System.nanoTime();
					this.players.get(i).thinkAI(delta, this);
					if (i == 0) {
						this.thinkPlayerOne.add(System.nanoTime() - nano);
					} else {
						this.thinkPlayerTwo.add(System.nanoTime() - nano);
					}
					this.steps += 1;
				}
			}
		}
		final int count = 50;
		if (this.players.get(0).isBVisible()) {
			int sum = 0;
			int size = this.thinkPlayerOne.size();
			for (int i = 0; i < size; i++) {
				sum += this.thinkPlayerOne.get(i);
			}
			if (size > count) {
				long dif = sum / size;
				if ((size > count) && (size > 0) && (dif > ApoMarioConstants.WAIT_TIME_THINK * 1000000)) {
					this.players.get(0).addMessage("Exception: Zu lange zum Nachdenken gebraucht");
					System.out.println("Exception: Spieler 1 hat zu lange zum Nachdenken gebraucht");
					this.players.get(0).playerDie();
					this.players.get(0).setPoints(this.players.get(0).getPoints() + ApoMarioConstants.POINTS_EXCEPTION);
				}
			}
			if (size > count) {
				this.thinkPlayerOne.clear();
			}
		}
		if (this.players.get(1).isBVisible()) {
			int sum = 0;
			int size = this.thinkPlayerTwo.size();
			for (int i = 0; i < size; i++) {
				sum += this.thinkPlayerTwo.get(i);
			}
			if ((size > count) && (size > 0) && (sum / size > ApoMarioConstants.WAIT_TIME_THINK * 1000000)) {
				this.players.get(1).addMessage("Exception: Zu lange zum Nachdenken gebraucht");
				System.out.println("Exception: Spieler 2 hat zu lange zum Nachdenken gebraucht");
				this.players.get(1).playerDie();
				this.players.get(1).setPoints(this.players.get(1).getPoints() + ApoMarioConstants.POINTS_EXCEPTION);
			}
			if (size > count) {
				this.thinkPlayerTwo.clear();
			}
		}
		for (int i = 0; i < this.players.size(); i++) {
			if ((this.isBReplay()) && (this.getReplay().getPlayerTwo() == null) && (i > 0)) {
				break;
			} else if ((!this.isBReplay()) && (i > 0) && (this.players.get(i).getAi() == null)) {
				break;
			}
			this.players.get(i).think(delta, this);
			Rectangle2D.Float rec = this.players.get(i).getRec();
			float dif = (float)(rec.getX() - this.players.get(i).getX());
			if (rec.getX() + rec.width >= this.getWidth() * ApoMarioConstants.TILE_SIZE) {
				this.players.get(i).setX((this.getWidth() - 1) * ApoMarioConstants.TILE_SIZE - rec.width - dif);
			}
			if (rec.getY() >= this.getHeight() * ApoMarioConstants.TILE_SIZE) {
				this.players.get(i).playerDie();
			}
			if (this.players.get(i).isBVisible()) {
				this.dieIcarus(this.players.get(i), delta);	
			}
		}
	}
	
	private void dieIcarus(ApoMarioPlayer player, int delta) {
		if ((player != null) && (player.getTeamName() != null) && (player.getTeamName().indexOf("Team Icarus") != -1)) {
			if (!this.bIcarus) {
				if ((player.getX()/ApoMarioConstants.TILE_SIZE + 10 > this.width) && (ApoMarioConstants.ICARUS_BURN)) {
					this.bIcarus = true;
					this.icarus = player;
					this.icarusLastY = this.icarus.getY();
					this.icarusLastVecY = this.icarus.getVecY();
				}
			} else if (ApoMarioConstants.ICARUS_BURN) {
				this.icarusLastVecY = this.icarusLastVecY + delta * ApoMarioConstants.PLAYER_VEC_FALL_Y_ICARUS;
				this.icarusLastY = this.icarusLastY + delta * this.icarusLastVecY;
				this.icarus.setY(this.icarusLastY);
				this.icarus.setX((this.width - 10) * ApoMarioConstants.TILE_SIZE);
				this.fireAnimation.think(delta);
			}
		}
		
	}
	
	private void thinkFinish(int delta) {
		this.finish.think(delta, this);
	}
	
	private void thinkParticle(int delta) {
		for (int i = this.particle.size() - 1; i >= 0; i--) {
			this.particle.get(i).think(delta);
			if (!this.particle.get(i).isBVisible()) {
				this.particle.remove(i);
			}
		}
	}
	
	private void thinkEnemies(int delta) {
		try {
			for (int i = this.enemies.size() - 1; i >= 0; i--) {
				try {
					Point p = this.enemies.get(i).getMinMax(this, ApoMarioConstants.GAME_WIDTH);
					if ((this.enemies.get(i).getX() + 3 * ApoMarioConstants.TILE_SIZE > p.x) &&
						(this.enemies.get(i).getX() - 3 * ApoMarioConstants.TILE_SIZE < p.y)) {
						if (this.enemies.get(i).isBVisible()) {
							this.enemies.get(i).think(delta, this);
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					System.out.println("Think Enemies Exception "+ex.getMessage());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Think Enemies Exception "+ex.getMessage());
		}
	}
	
	private void thinkGoodies(int delta) {
		try {
			for (int i = this.goodies.size() - 1; i >= 0; i--) {
				if (this.goodies.get(i).isBVisible()) {
					this.goodies.get(i).think(delta, this);
				}
				if (!this.goodies.get(i).isBVisible()) {
					
						this.goodies.remove(i);
					
				}
			}
		} catch (Exception ex) {
			System.out.println("Think Goodies Exception");
		}
	}
	
	private void thinkLevelEntities(int delta) {
		Point p = this.players.get(0).getMinMax(this, ApoMarioConstants.GAME_WIDTH/2);
		int startX = (int)((p.x) / ApoMarioConstants.TILE_SIZE);
		if (startX < 0) {
			startX = 0;
		}
		int startY = (int)((this.component.getChangeY() - ApoMarioConstants.TILE_SIZE) / ApoMarioConstants.TILE_SIZE);
		if (startY < 0) {
			startY = 0;
		}
		int endX = startX + p.y / ApoMarioConstants.TILE_SIZE;
		if (endX < ApoMarioConstants.GAME_WIDTH / ApoMarioConstants.TILE_SIZE) {
			endX = ApoMarioConstants.GAME_WIDTH / ApoMarioConstants.TILE_SIZE;
		}
		int endY = startY + ApoMarioConstants.GAME_HEIGHT / ApoMarioConstants.TILE_SIZE;
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				if (this.levelEntities[y][x] instanceof ApoMarioCannon) {
					ApoMarioCannon canon = (ApoMarioCannon)(this.levelEntities[y][x]);
					if ( (y < this.height) && (y < endY + 1) && (y >= startY) &&
						 (x < this.width) && (x < endX + 1) && (x >= startX)) {
						canon.think(delta, this);
					}
					canon.thinkCanon(delta, this);
				} else if ( (y < this.height) && (y < endY + 1) && (y >= startY) &&
							(x < this.width) && (x < endX + 1) && (x >= startX)) {
					if (this.levelEntities[y][x] != null) {
						this.levelEntities[y][x].think(delta, this);
					}
				}
			}
		}
	}
	
	/**
	 * rendert das Level
	 * @param g : das Grafikobjekt
	 * @param changeX : Verschiebung der X-Achse
	 * @param changeY : Verschiebung der Y-Achse
	 */
	public void render(Graphics2D g, int changeX, int changeY) {
		if ((this.component.isBMiddle()) && (((ApoMarioPanel)(this.component)).isBMiddleWall())) {
			this.renderBackgroundImage(g, this.iBackground, 0.5f, changeX);
			this.renderBackgroundImage(g, this.iBackgroundFront, 0.75f, changeX);
		} else {
			this.renderBackgroundImage(g, this.iBoth, 0.5f, changeX);
		}
		
		this.renderFinish(g, changeX, changeY);
		this.renderEnemies(g, changeX, changeY, true);
		this.renderTiles(g, changeX, changeY);
		this.renderEnemies(g, changeX, changeY, false);
		this.renderGoodies(g, changeX, changeY);
		this.renderPlayer(g, changeX, changeY);
		this.renderParticle(g, changeX, changeY);
		this.replayPlay.render(g);
		this.renderIcarus(g, changeX, changeY);
	}
	
	/**
	 * malt ein Bild in den Hintergrund
	 * @param g : Das Grafikobjekt
	 * @param iBackground : Das Bild was gemalt werden sll
	 * @param delta : Verschiebung der X-Achse wird mit diesem Wert multipliziert
	 * @param changeX : Verschiebung der X-Achse
	 */
	private void renderBackgroundImage(Graphics2D g, BufferedImage iBackground, float delta, int changeX) {
		if (iBackground != null) {
			int myChangeX = (int)((changeX * delta) / iBackground.getWidth());
			int startX = (int)((changeX * delta - (myChangeX * iBackground.getWidth())));
			int width = ApoMarioConstants.GAME_WIDTH;
			int height = ApoMarioConstants.GAME_HEIGHT;
			if (startX + ApoMarioConstants.GAME_WIDTH >= iBackground.getWidth()) {
				int newW = ApoMarioConstants.GAME_WIDTH - (iBackground.getWidth() - startX) + 1;
				g.drawImage(iBackground, 0, 0, iBackground.getWidth() - startX, height, (int)(startX), (int)(0), iBackground.getWidth(), height, null);
				g.drawImage(iBackground, iBackground.getWidth() - startX, 0, iBackground.getWidth() - startX + newW, height, (int)(0), (int)(0), newW, height, null);
			} else {
				g.drawImage(iBackground, 0, 0, width, height, (int)(startX), (int)(0), (int)(startX + width), height, null);
			}
		}
	}
	
	/**
	 * rendert die Partikel
	 * @param g : das Grafikobjekt
	 * @param changeX : Verschiebung auf der X-Achse
	 * @param changeY : Verschiebung auf der Y-Achse
	 */
	private void renderParticle(Graphics2D g, int changeX, int changeY) {
		if (this.particle != null) {
			for (int i = this.particle.size() - 1; i >= 0; i--) {
				try {
					if ((changeX - ApoMarioConstants.TILE_SIZE < this.particle.get(i).getX()) &&
						(changeX + ApoMarioConstants.GAME_WIDTH + ApoMarioConstants.TILE_SIZE > this.particle.get(i).getX())) {
						this.particle.get(i).render(g, changeX, changeY);
					}
				} catch (Exception ex) {
				}
			}
		}
	}
	
	/**
	 * rendert das Ziel
	 * @param g : das Grafikobjekt
	 * @param changeX : Verschiebung auf der X-Achse
	 * @param changeY : Verschiebung auf der Y-Achse
	 */
	private void renderFinish(Graphics2D g, int changeX, int changeY) {
		if (this.finish != null) {
			if ((changeX - ApoMarioConstants.TILE_SIZE < this.finish.getX()) &&
				(changeX + ApoMarioConstants.GAME_WIDTH + ApoMarioConstants.TILE_SIZE > this.finish.getX())) {
				this.finish.render(g, -changeX, -changeY);
			}
		}
	}

	/**
	 * rendert den Spieler
	 * @param g : das Grafikobjekt
	 * @param changeX : Verschiebung auf der X-Achse
	 * @param changeY : Verschiebung auf der Y-Achse
	 */
	private void renderPlayer(Graphics2D g, int changeX, int changeY) {
		for (int i = 0; i < this.players.size(); i++) {
			if (this.players.get(i) != null) {
				this.players.get(i).render(g, changeX, changeY);
			}
		}
	}
	
	/**
	 * rendert die Gegner
	 * @param g : das Grafikobjekt
	 * @param changeX : Verschiebung auf der X-Achse
	 * @param changeY : Verschiebung auf der Y-Achse
	 */
	public void renderEnemies(Graphics2D g, int changeX, int changeY, boolean bFlower) {
		if (this.enemies != null) {
			for (int i = 0; i < this.enemies.size(); i++) {
				if ((changeX - ApoMarioConstants.TILE_SIZE < this.enemies.get(i).getX()) &&
					(changeX + ApoMarioConstants.GAME_WIDTH + ApoMarioConstants.TILE_SIZE > this.enemies.get(i).getX())) {
						if (this.enemies.get(i).isBVisible()) {
							if (bFlower) {
								if (this.enemies.get(i) instanceof ApoMarioFlower) {
									this.enemies.get(i).render(g, changeX, changeY);
								}
							} else {
								if (!(this.enemies.get(i) instanceof ApoMarioFlower)) {
									this.enemies.get(i).render(g, changeX, changeY);
								}
						}
					}
				}
			}
		}
	}
	
	/**
	 * rendert die Goodies
	 * @param g : das Grafikobjekt
	 * @param changeX : Verschiebung auf der X-Achse
	 * @param changeY : Verschiebung auf der Y-Achse
	 */
	private void renderGoodies(Graphics2D g, int changeX, int changeY) {
		if (this.goodies != null) {
			for (int i = 0; i < this.goodies.size(); i++) {
				if ((changeX - ApoMarioConstants.TILE_SIZE < this.goodies.get(i).getX()) &&
					(changeX + ApoMarioConstants.GAME_WIDTH + ApoMarioConstants.TILE_SIZE > this.goodies.get(i).getX())) {
						this.goodies.get(i).render(g, changeX, changeY);
				}
			}
		}
	}
	
	/**
	 * rendert das eigentliche Level mit seinen Tiles
	 * @param g : das Grafikobjekt
	 * @param changeX : Verschiebung auf der X-Achse
	 * @param changeY : Verschiebung auf der Y-Achse
	 */
	private void renderTiles(Graphics2D g, int changeX, int changeY) {
		if (this.levelEntities != null) {
			try {
				int startX = changeX / ApoMarioConstants.TILE_SIZE;
				int startY = changeY / ApoMarioConstants.TILE_SIZE;
				int endX = startX + ApoMarioConstants.GAME_WIDTH / ApoMarioConstants.TILE_SIZE;
				int endY = startY + ApoMarioConstants.GAME_HEIGHT / ApoMarioConstants.TILE_SIZE;
				for (int y = startY; (y < this.height) && (y < endY + 1); y++) {
					for (int x = startX; (x < this.width) && (x < endX + 1); x++) {
						if (this.levelEntities[y][x] != null) {
							if (!(this.levelEntities[y][x] instanceof ApoMarioCannon)) {
								this.levelEntities[y][x].render(g, changeX, changeY);
							}
						}
					}
				}
				for (int y = 0; y < this.height; y++) {
					for (int x = 0; x < this.width; x++) {
						if (this.levelEntities[y][x] != null) {
							if (this.levelEntities[y][x] instanceof ApoMarioCannon) {
								ApoMarioCannon canon = (ApoMarioCannon)(this.levelEntities[y][x]);
								if (canon.getCannons() != null) {
									for (int i = 0; i < canon.getCannons().size(); i++) {
										canon.getCannons().get(i).render(g, changeX, changeY);
									}
								}
								canon.render(g, changeX, changeY);
							}
						}
					}
				}
			} catch (Exception ex) {
				System.out.println("Exception rendern Tiles");
			}
		}
	}
	
	private void renderIcarus(final Graphics2D g, int changeX, int changeY) {
		if ((ApoMarioConstants.ICARUS_BURN) && (this.bIcarus)) {
			g.setColor(Color.YELLOW);
			g.fillOval(ApoMarioConstants.GAME_WIDTH - 60, -60, 120, 120);
			
			Stroke stroke = g.getStroke();
			g.setStroke(new BasicStroke(2));
			int width = 150;
			int lines = 8;
			for ( int i = 0; i < lines; i++ ) {
				g.drawLine(ApoMarioConstants.GAME_WIDTH, 0, (int)(ApoMarioConstants.GAME_WIDTH - width * Math.sin(Math.toRadians(90 - 90 / lines * i))), (int)(width * Math.cos(Math.toRadians(90 - 90 / lines * i))));
			}
			g.setStroke(stroke);
			
			String no = "Oh, noooo! I'm burning!";
			this.icarus.drawStringAbove(g, no, changeX, changeY, true);
			
			this.icarus.drawImage(g, this.fireAnimation, changeX, changeY);
		}
	}
}