package apoCommando.level;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import apoCommando.ApoMarioConstants;
import apoCommando.ApoMarioImageContainer;
import apoCommando.ApoMarioGameComponent;
import apoCommando.entity.ApoMarioCannon;
import apoCommando.entity.ApoMarioCoin;
import apoCommando.entity.ApoMarioDestructableWall;
import apoCommando.entity.ApoMarioEnd;
import apoCommando.entity.ApoMarioEnemy;
import apoCommando.entity.ApoMarioEntity;
import apoCommando.entity.ApoMarioFlower;
import apoCommando.entity.ApoMarioGumba;
import apoCommando.entity.ApoMarioKoopa;
import apoCommando.entity.ApoMarioParticle;
import apoCommando.entity.ApoMarioPlayer;
import apoCommando.entity.ApoMarioQuestionmark;
import apoCommando.entity.ApoMarioWall;
import apoCommando.game.ApoMarioPanel;

/**
 * Klasse, die ein Level darstellt
 * @author Dirk Aporius
 *
 */
public class ApoMarioLevel {
	
	public static int ID;
	
	/** Breite des Levels */
	private int width;
	/** Höhe des Levels */
	private int height;
	
	private long levelInt;
	/** Hintergrundbild */
	private BufferedImage iBackground;
	/** vordere Ebene des Hintergrundes */
	private BufferedImage iBackgroundFront;
	
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
	private ApoMarioGameComponent component;
	
	private ApoMarioEntity[][] levelEntities;
	
	private ApoMarioEnd finish;
	
	private ArrayList<Point> cannons;
	
	private ApoMarioPlayer player;
	
	private boolean bStop;
	
	private ApoMarioRandomLevel randomLevel;
	
	private int maxTime, maxCoins;
	
	private ApoMarioMyLevels levels = new ApoMarioMyLevels(this);
	
	private String currentLevelString, nextLevelString;
	
	private boolean bDark;
	
	private float darkTime;
	
	public ApoMarioLevel(ApoMarioGameComponent component) {
		this.component = component;
		this.init();
	}
	
	public void init() {
		this.makeLevel((int)System.nanoTime(), true, 100, 0, "start");
		this.bAnalysis = false;
	}

	public boolean isBDark() {
		return this.bDark;
	}

	public void setBDark(boolean bDark) {
		this.bDark = bDark;
	}

	public float getDarkTime() {
		return this.darkTime;
	}

	public void setDarkTime(float darkTime) {
		this.darkTime = darkTime;
	}

	public int getMaxTime() {
		return this.maxTime;
	}

	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}

	public String getNextLevelString() {
		return this.nextLevelString;
	}

	public void setNextLevelString(String nextLevelString) {
		this.nextLevelString = nextLevelString;
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

	public ApoMarioGameComponent getComponent() {
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

	public void setLevelEntities(ApoMarioEntity[][] levelEntities) {
		this.levelEntities = levelEntities;
	}

	/**
	 * erstellt ein zufälliges Level
	 * @param levelInt : Zufallszahl nach dem sich der Levelaufbau richtet
	 */
	public void makeLevel(long levelInt, boolean bNew, int width, int difficulty, String levelString) {
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
		this.bDark = false;
		this.darkTime = 0;
		ApoMarioLevel.ID = 0;
		this.particle = new ArrayList<ApoMarioParticle>();
		this.bStop = false;
		
		this.cannons = new ArrayList<Point>();
		this.levelInt = levelInt;
		this.height = 15;
		boolean bLoad = false;
		this.maxCoins = 0;
		if (levelString != null) {
			for (int i = 0; i < ApoMarioMyLevels.LEVEL_STRINGS.length; i++) {
				if (levelString.equals(ApoMarioMyLevels.LEVEL_STRINGS[i])) {
					this.currentLevelString = levelString;
					bLoad = this.levels.makeLevel((i + 1), ApoMarioConstants.DIFFICULTY);
				}
			}
			if (!bLoad) {
				levelString = levelString.toLowerCase();
				if (levelString.equals("mandy")) {
					this.currentLevelString = levelString;
					bLoad = this.levels.makeMandyLevel(1, -2);
					if (this.component instanceof ApoMarioPanel) {
						ApoMarioPanel game = (ApoMarioPanel)this.component;
						game.getAchievements().addAchievement(ApoMarioAchievements.ACHIEVEMENT_MANDY);
					}
				} else if (levelString.equals("antje")) {
					this.currentLevelString = levelString;
					bLoad = this.levels.makeAntjeLevel(1, -2);
					if (this.component instanceof ApoMarioPanel) {
						ApoMarioPanel game = (ApoMarioPanel)this.component;
						game.getAchievements().addAchievement(ApoMarioAchievements.ACHIEVEMENT_ANTJE);
					}
				} else if (levelString.equals("clemens")) {
					this.currentLevelString = levelString;
					bLoad = this.levels.makeClemensLevel(1, -2);
					if (this.component instanceof ApoMarioPanel) {
						ApoMarioPanel game = (ApoMarioPanel)this.component;
						game.getAchievements().addAchievement(ApoMarioAchievements.ACHIEVEMENT_CLEMENS);
					}
				} else if (levelString.equals("jessika")) {
					this.currentLevelString = levelString;
					bLoad = this.levels.makeJessikaLevel(1, -2);
					if (this.component instanceof ApoMarioPanel) {
						ApoMarioPanel game = (ApoMarioPanel)this.component;
						game.getAchievements().addAchievement(ApoMarioAchievements.ACHIEVEMENT_JESSIKA);
					}
				} else if (levelString.equals("tutorial")) {
					this.currentLevelString = levelString;
					bLoad = this.levels.makeTutorialLevel(1, -2);
					if (this.component instanceof ApoMarioPanel) {
						ApoMarioPanel game = (ApoMarioPanel)this.component;
						game.getAchievements().addAchievement(ApoMarioAchievements.ACHIEVEMENT_TUTORIAL);
					}
				}
			}
		}
		
		if (!bLoad) {
			this.currentLevelString = null;
			this.nextLevelString = "random";
			if ((levelInt != this.levelInt) && (bNew) && (width == -1)) {
				this.width = (int)(Math.random() * 500 + 100);
				this.difficulty = (int)(Math.random() * 400);
			} else if (width > 0) {
				this.width = width;
				this.difficulty = difficulty;
			}
			this.levelEntities = new ApoMarioEntity[this.height][this.width];
			for (int x = 0; x < this.width; x++) {
				this.makeGroundWall(x, this.height-1);
			}
			this.randomLevel = new ApoMarioRandomLevel(this);
			this.randomLevel.setWidth(this.width);
			this.randomLevel.setDifficulty(this.difficulty);
			this.randomLevel.makeRandomLevel(levelInt);
			this.difficulty = this.randomLevel.getDifficulty();
		}
		
		this.time = this.maxTime;
		int imageWidth = 32;
		int imageHeight = 20;
		this.iBackground = this.component.getImages().getBackgroundBack(ApoMarioImageContainer.TILES_BACKGROUND, imageWidth, imageHeight);
		imageWidth = 40;
		this.iBackgroundFront = this.component.getImages().getBackgroundFront(ApoMarioImageContainer.TILES_BACKGROUND, imageWidth, imageHeight);
		
		this.iBoth = new BufferedImage(this.iBackgroundFront.getWidth(), this.iBackgroundFront.getHeight(), BufferedImage.TYPE_INT_RGB);
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
		return this.maxTime;
	}
	
	public int getTime() {
		return this.time;
	}

	protected void makeFinish(int x, int y) {
		int height = 13 * ApoMarioConstants.TILE_SIZE;
		this.makeFinish(x, y, height);
	}
	
	protected void makeFinish(int x, int y, int height) {	
		this.finish = new ApoMarioEnd(x, y - height, 3 * ApoMarioConstants.TILE_SIZE, height, ++ApoMarioLevel.ID);
	}
	
	protected void makeBoxQuestionMark(int x, int y, int goodie) {
		this.levelEntities[y][x] = new ApoMarioQuestionmark(ApoMarioImageContainer.TILES_LEVEL.getSubimage(4 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, 4 * ApoMarioConstants.TILE_SIZE, ApoMarioConstants.TILE_SIZE), ApoMarioImageContainer.TILES_LEVEL.getSubimage(4 * ApoMarioConstants.TILE_SIZE, 0 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, ApoMarioConstants.TILE_SIZE), x*ApoMarioConstants.TILE_SIZE, (y)*ApoMarioConstants.TILE_SIZE, goodie, ++ApoMarioLevel.ID);
	}
	
	protected void makeBoxCoin(int x, int y) {
		this.levelEntities[y][x] = new ApoMarioCoin(ApoMarioImageContainer.TILES_LEVEL.getSubimage(0 * ApoMarioConstants.TILE_SIZE, 2 * ApoMarioConstants.TILE_SIZE, 4 * ApoMarioConstants.TILE_SIZE, ApoMarioConstants.TILE_SIZE), ApoMarioImageContainer.PARTICLE.getSubimage(0 * ApoMarioConstants.TILE_SIZE/2, 2 * ApoMarioConstants.TILE_SIZE/2, 3 * ApoMarioConstants.TILE_SIZE/2, ApoMarioConstants.TILE_SIZE/2), x*ApoMarioConstants.TILE_SIZE, (y)*ApoMarioConstants.TILE_SIZE, ApoMarioConstants.COIN_ANIMATION_FRAMES, ApoMarioConstants.COIN_ANIMATION_TIME, ++ApoMarioLevel.ID);
		this.maxCoins += 1;
	}
	
	public int getMaxCoins() {
		return this.maxCoins;
	}

	protected void makePlayer(float x, float y) {
		if (this.player == null) {
			this.player = new ApoMarioPlayer(x, y, ApoMarioConstants.TILE_SIZE * 2, ApoMarioConstants.TILE_SIZE * 2, ApoMarioConstants.PLAYER_ANIMATION_FRAMES, ApoMarioConstants.PLAYER_ANIMATION_TIME, 0, ++ApoMarioLevel.ID);
		} else {
			this.player.init();
			this.player.setX(x);
			this.player.setY(y);
		}
	}

	protected void makeGroundWall(int x, int y) {
		BufferedImage iWall = new BufferedImage(1 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D)iWall.getGraphics();
		g.drawImage(ApoMarioImageContainer.TILES_LEVEL.getSubimage(1 * ApoMarioConstants.TILE_SIZE, 8 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, ApoMarioConstants.TILE_SIZE), 0, 0, null);
		g.dispose();
		this.levelEntities[y][x] = new ApoMarioWall(iWall, x*ApoMarioConstants.TILE_SIZE, (y)*ApoMarioConstants.TILE_SIZE, ++ApoMarioLevel.ID);				
	}
	
	protected void makeGroundCorrect() {
		for (int x = 0; x < this.width; x++) {
			for (int y = this.height - 1; y >= 0; y--) {
				if (this.levelEntities[y][x] != null) {
					if ((y - 1 >= 0) && (this.levelEntities[y-1][x] != null)) {
						if (((x - 1 >= 0) && (this.levelEntities[y][x-1] == null)) && ((x + 1 < this.width) && (this.levelEntities[y][x+1] == null))) {
							BufferedImage iLeft = ApoMarioImageContainer.TILES_LEVEL.getSubimage(0 * ApoMarioConstants.TILE_SIZE, 9 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE);
							BufferedImage iRight = ApoMarioImageContainer.TILES_LEVEL.getSubimage(2 * ApoMarioConstants.TILE_SIZE, 9 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE);
							BufferedImage iNew = new BufferedImage(iLeft.getWidth(), iLeft.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
							Graphics2D g = (Graphics2D)(iNew.getGraphics());
							g.drawImage(iLeft.getSubimage(0, 0, iLeft.getWidth()/2, iLeft.getHeight()), 0, 0, null);
							g.drawImage(iRight.getSubimage(iRight.getWidth()/2, 0, iRight.getWidth()/2, iRight.getHeight()), iRight.getWidth()/2, 0, null);
							g.dispose();
							this.levelEntities[y][x].setIBackground(iNew);
						} else if ((x - 1 >= 0) && (this.levelEntities[y][x-1] == null)) {
							if ((x + 1 < this.width) && (this.levelEntities[y][x+1] != null) && (this.levelEntities[y-1][x+1] == null)) {
								BufferedImage iLeft = ApoMarioImageContainer.TILES_LEVEL.getSubimage(0 * ApoMarioConstants.TILE_SIZE, 9 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE);
								BufferedImage iRight = ApoMarioImageContainer.TILES_LEVEL.getSubimage(3 * ApoMarioConstants.TILE_SIZE, 9 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE);
								BufferedImage iNew = new BufferedImage(iRight.getWidth(), iRight.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
								Graphics2D g = (Graphics2D)iNew.getGraphics();
								g.drawImage(iLeft.getSubimage(0, 0, iLeft.getWidth()/2, iLeft.getHeight()), 0, 0, null);
								g.drawImage(iRight.getSubimage(iRight.getWidth()/2, 0, iRight.getWidth()/2, iRight.getHeight()), iRight.getWidth()/2, 0, null);
								g.dispose();
								this.levelEntities[y][x].setIBackground(iNew);
							} else {
								this.levelEntities[y][x].setIBackground(ApoMarioImageContainer.TILES_LEVEL.getSubimage(0 * ApoMarioConstants.TILE_SIZE, 9 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE));
							}
						} else if ((x + 1 < this.width) && (this.levelEntities[y][x+1] == null)) {
							if ((x - 1 >= 0) && (this.levelEntities[y][x-1] != null) && (this.levelEntities[y-1][x-1] == null)) {
								BufferedImage iLeft = ApoMarioImageContainer.TILES_LEVEL.getSubimage(3 * ApoMarioConstants.TILE_SIZE, 8 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE);
								BufferedImage iRight = ApoMarioImageContainer.TILES_LEVEL.getSubimage(2 * ApoMarioConstants.TILE_SIZE, 9 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE);
								BufferedImage iNew = new BufferedImage(iRight.getWidth(), iRight.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
								Graphics2D g = (Graphics2D)iNew.getGraphics();
								g.drawImage(iLeft.getSubimage(0, 0, iLeft.getWidth()/2, iLeft.getHeight()), 0, 0, null);
								g.drawImage(iRight.getSubimage(iRight.getWidth()/2, 0, iRight.getWidth()/2, iRight.getHeight()), iRight.getWidth()/2, 0, null);
								g.dispose();
								this.levelEntities[y][x].setIBackground(iNew);
							} else {
								this.levelEntities[y][x].setIBackground(ApoMarioImageContainer.TILES_LEVEL.getSubimage(2 * ApoMarioConstants.TILE_SIZE, 9 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE));
							}
						} else if ((x + 1 < this.width) && (this.levelEntities[y-1][x+1] == null)) {
							this.levelEntities[y][x].setIBackground(ApoMarioImageContainer.TILES_LEVEL.getSubimage(3 * ApoMarioConstants.TILE_SIZE, 9 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE));
						} else if ((x - 1 >= 0) && (this.levelEntities[y-1][x-1] == null)) {
							this.levelEntities[y][x].setIBackground(ApoMarioImageContainer.TILES_LEVEL.getSubimage(3 * ApoMarioConstants.TILE_SIZE, 8 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE));
						} else {
							this.levelEntities[y][x].setIBackground(ApoMarioImageContainer.TILES_LEVEL.getSubimage(1 * ApoMarioConstants.TILE_SIZE, 9 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE));							
						}
					} else {
						if (((x + 1 < this.width) && (this.levelEntities[y][x+1] == null)) && ((x - 1 >= 0) && (this.levelEntities[y][x-1] == null))) {
							BufferedImage iRight = ApoMarioImageContainer.TILES_LEVEL.getSubimage(2 * ApoMarioConstants.TILE_SIZE, 8 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE);
							BufferedImage iLeft = ApoMarioImageContainer.TILES_LEVEL.getSubimage(0 * ApoMarioConstants.TILE_SIZE, 8 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE);
							BufferedImage iNew = new BufferedImage(iRight.getWidth(), iRight.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
							Graphics2D g = (Graphics2D)iNew.getGraphics();
							g.drawImage(iLeft.getSubimage(0, 0, iLeft.getWidth()/2, iLeft.getHeight()), 0, 0, null);
							g.drawImage(iRight.getSubimage(iRight.getWidth()/2, 0, iRight.getWidth()/2, iRight.getHeight()), iRight.getWidth()/2, 0, null);
							g.dispose();
							this.levelEntities[y][x].setIBackground(iNew);
						} else if ((x - 1 >= 0) && (this.levelEntities[y][x-1] == null)) {
							this.levelEntities[y][x].setIBackground(ApoMarioImageContainer.TILES_LEVEL.getSubimage(0 * ApoMarioConstants.TILE_SIZE, 8 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE));
						} else if ((x + 1 < this.width) && (this.levelEntities[y][x+1] == null)) {
							this.levelEntities[y][x].setIBackground(ApoMarioImageContainer.TILES_LEVEL.getSubimage(2 * ApoMarioConstants.TILE_SIZE, 8 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE));
						}
					}
				} else {
					break;
				}
			}
		}
	}
	
	protected void makeWall(BufferedImage iWall, boolean bOnlyAbove, boolean bNoCollision, boolean bDestructable, boolean bNull, int goodie, int x, int y) {
		if (bDestructable) {
			if (bNull) {
				this.levelEntities[y][x] = new ApoMarioDestructableWall(iWall, null, x*ApoMarioConstants.TILE_SIZE, (y)*ApoMarioConstants.TILE_SIZE, ApoMarioConstants.WALL_DESTRUCABLE_ANIMATION_FRAMES, ApoMarioConstants.WALL_DESTRUCABLE_ANIMATION_TIME, ++ApoMarioLevel.ID);				
			} else {
				this.levelEntities[y][x] = new ApoMarioDestructableWall(iWall, ApoMarioImageContainer.TILES_LEVEL.getSubimage(4 * ApoMarioConstants.TILE_SIZE, 0 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, ApoMarioConstants.TILE_SIZE), x*ApoMarioConstants.TILE_SIZE, (y)*ApoMarioConstants.TILE_SIZE, ApoMarioConstants.WALL_DESTRUCABLE_ANIMATION_FRAMES, ApoMarioConstants.WALL_DESTRUCABLE_ANIMATION_TIME, ++ApoMarioLevel.ID);	
				if (goodie >= 0) {
					((ApoMarioWall)(this.levelEntities[y][x])).setGoodie(goodie);
				}
			}
		} else {
			this.levelEntities[y][x] = new ApoMarioWall(iWall, x*ApoMarioConstants.TILE_SIZE, (y)*ApoMarioConstants.TILE_SIZE, ++ApoMarioLevel.ID);
			((ApoMarioWall)(this.levelEntities[y][x])).setBOnlyAboveWall(bOnlyAbove);
			((ApoMarioWall)(this.levelEntities[y][x])).setBNoCollision(bNoCollision);
		}
	}
	
	protected void makeWall(boolean bDestructable, boolean bNull, int goodie, int x, int y) {
		if (bDestructable) {
			if (bNull) {
				this.levelEntities[y][x] = new ApoMarioDestructableWall(ApoMarioImageContainer.TILES_LEVEL.getSubimage(0 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, 4 * ApoMarioConstants.TILE_SIZE, ApoMarioConstants.TILE_SIZE), null, x*ApoMarioConstants.TILE_SIZE, (y)*ApoMarioConstants.TILE_SIZE, ApoMarioConstants.WALL_DESTRUCABLE_ANIMATION_FRAMES, ApoMarioConstants.WALL_DESTRUCABLE_ANIMATION_TIME, ++ApoMarioLevel.ID);				
			} else {
				this.levelEntities[y][x] = new ApoMarioDestructableWall(ApoMarioImageContainer.TILES_LEVEL.getSubimage(0 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, 4 * ApoMarioConstants.TILE_SIZE, ApoMarioConstants.TILE_SIZE), ApoMarioImageContainer.TILES_LEVEL.getSubimage(4 * ApoMarioConstants.TILE_SIZE, 0 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, ApoMarioConstants.TILE_SIZE), x*ApoMarioConstants.TILE_SIZE, (y)*ApoMarioConstants.TILE_SIZE, ApoMarioConstants.WALL_DESTRUCABLE_ANIMATION_FRAMES, ApoMarioConstants.WALL_DESTRUCABLE_ANIMATION_TIME, ++ApoMarioLevel.ID);	
				if (goodie >= 0) {
					((ApoMarioWall)(this.levelEntities[y][x])).setGoodie(goodie);
				}
			}
			((ApoMarioDestructableWall)(this.levelEntities[y][x])).setTimeToRepeat(ApoMarioConstants.WALL_DESTRUCABLE_REPEATTIME + (int)(Math.random() * 1000));
		} else {
			this.levelEntities[y][x] = new ApoMarioWall(ApoMarioImageContainer.TILES_LEVEL.getSubimage(4 * ApoMarioConstants.TILE_SIZE, 0 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, ApoMarioConstants.TILE_SIZE), x*ApoMarioConstants.TILE_SIZE, (y)*ApoMarioConstants.TILE_SIZE, ++ApoMarioLevel.ID);				
		}
	}
	
	protected void makeKoopa(boolean bRed, int x, int y, boolean bFall, boolean bFly, boolean bLeft) {
		BufferedImage iEnemy = ApoMarioImageContainer.ENEMIES.getSubimage(0 * ApoMarioConstants.TILE_SIZE, 0 * ApoMarioConstants.TILE_SIZE, 2 * ApoMarioConstants.TILE_SIZE, 2 * ApoMarioConstants.TILE_SIZE);
		if (!bRed) {
			iEnemy = ApoMarioImageContainer.ENEMIES.getSubimage(0 * ApoMarioConstants.TILE_SIZE, 2 * ApoMarioConstants.TILE_SIZE, 2 * ApoMarioConstants.TILE_SIZE, 2 * ApoMarioConstants.TILE_SIZE);			
		}
		this.enemies.add(new ApoMarioKoopa(iEnemy, x*ApoMarioConstants.TILE_SIZE, (y)*ApoMarioConstants.TILE_SIZE, 1*ApoMarioConstants.TILE_SIZE, 2*ApoMarioConstants.TILE_SIZE, ApoMarioConstants.KOOPA_ANIMATION_FRAMES, ApoMarioConstants.KOOPA_ANIMATION_TIME, bFall, bFly, bLeft, !bRed, ++ApoMarioLevel.ID));		
	}
	
	protected void makeGumba(int x, int y, boolean bFall, boolean bFly, boolean bLeft) {
		BufferedImage iGumba = ApoMarioImageContainer.ENEMIES.getSubimage(0 * ApoMarioConstants.TILE_SIZE, 5 * ApoMarioConstants.TILE_SIZE, 2 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE);
		this.enemies.add(new ApoMarioGumba(iGumba, x*ApoMarioConstants.TILE_SIZE, (y)*ApoMarioConstants.TILE_SIZE, 1*ApoMarioConstants.TILE_SIZE, 1*ApoMarioConstants.TILE_SIZE, ApoMarioConstants.GUMBA_ANIMATION_FRAMES, ApoMarioConstants.GUMBA_ANIMATION_TIME, bFall, bFly, bLeft, ++ApoMarioLevel.ID));		
	}
	
	protected void makeImmortal(int x, int y, boolean bFall, boolean bFly, boolean bLeft) {
		BufferedImage iImmortal = ApoMarioImageContainer.ENEMIES.getSubimage(0 * ApoMarioConstants.TILE_SIZE, 7 * ApoMarioConstants.TILE_SIZE, 2 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE);
		this.enemies.add(new ApoMarioGumba(iImmortal, x*ApoMarioConstants.TILE_SIZE, (y)*ApoMarioConstants.TILE_SIZE, 1*ApoMarioConstants.TILE_SIZE, 1*ApoMarioConstants.TILE_SIZE, ApoMarioConstants.GUMBA_ANIMATION_FRAMES, ApoMarioConstants.GUMBA_ANIMATION_TIME, bFall, bFly, bLeft, ++ApoMarioLevel.ID));		
		this.enemies.get(this.enemies.size() - 1).setBImmortal(true);
	}
	
	protected void makeCanon(int x, int y, int height, boolean bRandom, int startTime) {
		int shootTime = (int)(Math.random() * 1000 + 6000);
		if (!bRandom) {
			shootTime = ApoMarioConstants.CANNON_SPAWNTIME;
		}
		this.levelEntities[y][x] = new ApoMarioCannon(ApoMarioImageContainer.TILES_LEVEL.getSubimage(14 * ApoMarioConstants.TILE_SIZE, 0 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE), ApoMarioImageContainer.ENEMIES.getSubimage(0 * ApoMarioConstants.TILE_SIZE, 11 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, ApoMarioConstants.TILE_SIZE), x*ApoMarioConstants.TILE_SIZE, (y)*ApoMarioConstants.TILE_SIZE, shootTime, startTime, ++ApoMarioLevel.ID);				
		
		this.cannons.add(new Point(x, y));
		
		if ((height >= 2) && (y + 1 < this.height) && (this.levelEntities[y+1][x] == null)) {
			this.levelEntities[y+1][x] = new ApoMarioWall(ApoMarioImageContainer.TILES_LEVEL.getSubimage(14 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE), x*ApoMarioConstants.TILE_SIZE, (y+1)*ApoMarioConstants.TILE_SIZE, ++ApoMarioLevel.ID);
			for (int i = 2; i < height; i++) {
				if ((y + i < this.height) && (this.levelEntities[y+i][x] == null)) {
					this.levelEntities[y+i][x] = new ApoMarioWall(ApoMarioImageContainer.TILES_LEVEL.getSubimage(14 * ApoMarioConstants.TILE_SIZE, 2 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE), x*ApoMarioConstants.TILE_SIZE, (y+i)*ApoMarioConstants.TILE_SIZE, ++ApoMarioLevel.ID);				
				} else {
					return;
				}
			}
		}
	}
	
	protected void makeFlower(int x, int y, int height, boolean bRandom, boolean bWithFlower, int startTime) {
		int timeToShow = (int)(Math.random() * 1000 + ApoMarioConstants.FLOWER_SPAWNTIME);
		if (!bRandom) {
			timeToShow = ApoMarioConstants.FLOWER_SPAWNTIME;
		}
		
		if (bWithFlower) {
			this.enemies.add(new ApoMarioFlower(ApoMarioImageContainer.ENEMIES.getSubimage(0 * ApoMarioConstants.TILE_SIZE, 12 * ApoMarioConstants.TILE_SIZE, 4 * ApoMarioConstants.TILE_SIZE, 2 * ApoMarioConstants.TILE_SIZE), (int)((x + 0.5f) * ApoMarioConstants.TILE_SIZE), (int)(y * ApoMarioConstants.TILE_SIZE), ApoMarioConstants.TILE_SIZE, 2 * ApoMarioConstants.TILE_SIZE, ApoMarioConstants.ENEMY_ANIMATION_FRAMES, ApoMarioConstants.FLOWER_ANIMATION_TIME, timeToShow, startTime, ++ApoMarioLevel.ID));
		}
		
		this.levelEntities[y][x] = new ApoMarioWall(ApoMarioImageContainer.TILES_LEVEL.getSubimage(10 * ApoMarioConstants.TILE_SIZE, 0 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE), x*ApoMarioConstants.TILE_SIZE, (y)*ApoMarioConstants.TILE_SIZE, ++ApoMarioLevel.ID);
		this.levelEntities[y][x+1] = new ApoMarioWall(ApoMarioImageContainer.TILES_LEVEL.getSubimage(11 * ApoMarioConstants.TILE_SIZE, 0 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE), (x+1)*ApoMarioConstants.TILE_SIZE, (y)*ApoMarioConstants.TILE_SIZE, ++ApoMarioLevel.ID);
		for (int i = 1; i < height; i++) {
			this.levelEntities[y+i][x] = new ApoMarioWall(ApoMarioImageContainer.TILES_LEVEL.getSubimage(10 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE), x*ApoMarioConstants.TILE_SIZE, (y+i)*ApoMarioConstants.TILE_SIZE, ++ApoMarioLevel.ID);
			this.levelEntities[y+i][x+1] = new ApoMarioWall(ApoMarioImageContainer.TILES_LEVEL.getSubimage(11 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE, 1 * ApoMarioConstants.TILE_SIZE), (x+1)*ApoMarioConstants.TILE_SIZE, (y+i)*ApoMarioConstants.TILE_SIZE, ++ApoMarioLevel.ID);			
		}
	}

	public void addParticle(ApoMarioParticle particle) {
		this.particle.add(particle);
	}
	
	private void levelRestart() {
		this.bAnalysis = true;
	}
	
	public void restart() {
		this.getComponent().setBPause(false);
		this.makeLevel(this.levelInt, true, -1, -1, this.currentLevelString);
		this.component.setChangeX(0);
		this.levelRestart();
	}
	
	public void newLevel(String currentLevelString) {
		this.makeLevel((int)(System.nanoTime()), true, -1, -1, currentLevelString);
		this.levelRestart();
	}
	
	public void newLevel(int width, int difficulty, String currentLevelString) {
		this.makeLevel((int)(System.nanoTime()), true, width, difficulty, currentLevelString);
		this.levelRestart();
	}
	
	public void newSameLevel() {
		this.makeLevel((int)(System.nanoTime()), true, this.width, this.difficulty, null);
		this.levelRestart();
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

	public ApoMarioPlayer getPlayer() {
		return this.player;
	}

	public int getPassedTime() {
		return (this.maxTime - this.time);
	}
	
	public void setAnalysis(boolean bWin) {
		if (!this.bAnalysis) {
			return;
		}
		this.bDark = false;
		this.darkTime = 0;
		if (bWin) {
			this.getPlayer().setPoints((int)(this.getPlayer().getPoints() + this.getPlayer().getX() + (float)this.getTime() * ApoMarioConstants.POINT_TIME_MULTIPLICATOR));
			this.getPlayer().setPoints((int)(this.getPlayer().getPoints() + ApoMarioConstants.POINTS_END));
			
			if (this.getPlayer().getType() == ApoMarioConstants.PLAYER_TYPE_FIRE) {
				this.getPlayer().setPoints((int)(this.getPlayer().getPoints() + ApoMarioConstants.POINT_BONUS_FIRE));
			} else if (this.getPlayer().getType() == ApoMarioConstants.PLAYER_TYPE_BIG) {
				this.getPlayer().setPoints((int)(this.getPlayer().getPoints() + ApoMarioConstants.POINT_BONUS_BIG));
			}
		}
		
		if (this.component instanceof ApoMarioPanel) {
			ApoMarioPanel game = (ApoMarioPanel)this.component;
			if (bWin) {
				if ((this.currentLevelString != null) && (this.currentLevelString.indexOf("dark") >= 0)) {
					game.getAchievements().addAchievement(ApoMarioAchievements.ACHIEVEMENT_DARK_LEVEL);
				}
				if (ApoMarioConstants.DIFFICULTY == 2) {
					game.getAchievements().addAchievement(ApoMarioAchievements.ACHIEVEMENT_EASY_GAME_SOLVED);
				} else if (ApoMarioConstants.DIFFICULTY == 3) {
					game.getAchievements().addAchievement(ApoMarioAchievements.ACHIEVEMENT_MEDIUM_GAME_SOLVED);
				} else if (ApoMarioConstants.DIFFICULTY == 4) {
					game.getAchievements().addAchievement(ApoMarioAchievements.ACHIEVEMENT_HARD_GAME_SOLVED);
				}
				if (this.getPlayer().getCoins() >= this.getMaxCoins()) {
					this.getPlayer().setPoints((int)(this.getPlayer().getPoints() + ApoMarioConstants.POINT_BONUS_COIN));
					game.getAchievements().addAchievement(ApoMarioAchievements.ACHIEVEMENT_COIN_LEVEL);
					if ((this.currentLevelString != null) && (this.currentLevelString.equals("maze"))) {
						game.getAchievements().addAchievement(ApoMarioAchievements.ACHIEVEMENT_COIN_MAZE);
					} else if ((this.currentLevelString != null) && (this.currentLevelString.equals("mandy"))) {
						game.getAchievements().addAchievement(ApoMarioAchievements.ACHIEVEMENT_COIN_MANDY);
					}
				}
			}
			if (this.getPlayer().getPoints() > 5000) {
				game.getAchievements().addAchievement(ApoMarioAchievements.ACHIEVEMENT_POINTS_MORE_5000);
				if (this.getPlayer().getPoints() > 10000) {
					game.getAchievements().addAchievement(ApoMarioAchievements.ACHIEVEMENT_POINTS_MORE_10000);
					if (this.getPlayer().getPoints() > 50000) {
						game.getAchievements().addAchievement(ApoMarioAchievements.ACHIEVEMENT_POINTS_MORE_50000);
					}
				}
			} 
			game.setBWin(bWin);
			game.setAnalysis();
		}
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
		if (this.bDark) {
			if (this.darkTime > 0) {
				float change = ((float)delta * 100f) / (2000f - ApoMarioConstants.DIFFICULTY * 400f);
				this.darkTime -= change;
			}
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
		if (ApoMarioConstants.DIFFICULTY > 1) {
			if (this.time <= 0) {
				this.setAnalysis(false);
			}
		}
	}	
	
	private void thinkPlayer(int delta) {
		this.player.think(delta, this);
		Rectangle2D.Float rec = this.player.getRec();
		float dif = (float)(rec.getX() - this.player.getX());
		if (rec.getX() + rec.width >= this.getWidth() * ApoMarioConstants.TILE_SIZE) {
			this.player.setX((this.getWidth() - 1) * ApoMarioConstants.TILE_SIZE - rec.width - dif);
		}
		if (rec.getY() >= this.getHeight() * ApoMarioConstants.TILE_SIZE) {
			this.player.playerDie();
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
		for (int i = this.enemies.size() - 1; i >= 0; i--) {
			Point p = this.enemies.get(i).getMinMax(this, ApoMarioConstants.GAME_WIDTH);
			if ((this.enemies.get(i).getX() + 3 * ApoMarioConstants.TILE_SIZE > p.x) &&
				(this.enemies.get(i).getX() - 3 * ApoMarioConstants.TILE_SIZE < p.y)) {
				if (this.enemies.get(i).isBVisible()) {
					this.enemies.get(i).think(delta, this);
				}
			}
		}
	}
	
	private void thinkGoodies(int delta) {
		for (int i = this.goodies.size() - 1; i >= 0; i--) {
			if (this.goodies.get(i).isBVisible()) {
				this.goodies.get(i).think(delta, this);
			}
			if (!this.goodies.get(i).isBVisible()) {
				
					this.goodies.remove(i);
				
			}
		}
	}
	
	private void thinkLevelEntities(int delta) {
		Point p = this.player.getMinMax(this, ApoMarioConstants.GAME_WIDTH/2);
		int startX = (int)((p.x) / ApoMarioConstants.TILE_SIZE);
		if (startX < 0) {
			startX = 0;
		}
		int startY = (int)((this.component.getChangeY() - ApoMarioConstants.TILE_SIZE) / ApoMarioConstants.TILE_SIZE);
		if (startY < 0) {
			startY = 0;
		}
		int endX = startX + p.y / ApoMarioConstants.TILE_SIZE;
		if (endX - startX < ApoMarioConstants.GAME_WIDTH/ApoMarioConstants.TILE_SIZE) {
			endX = startX + ApoMarioConstants.GAME_WIDTH/ApoMarioConstants.TILE_SIZE;
			if (endX >= this.getWidth()) {
				endX = this.getWidth() - 1;
			}
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
		if ((this.bDark) && (this.darkTime <= 0)) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, ApoMarioConstants.GAME_WIDTH, ApoMarioConstants.GAME_HEIGHT);
			this.renderFinish(g, changeX, changeY);
			this.renderCoins(g, changeX, changeY);
		} else {
			this.renderBackgroundImage(g, this.iBoth, 0.5f, changeX);
			
			this.renderFinish(g, changeX, changeY);
			this.renderEnemies(g, changeX, changeY, true);
			this.renderTiles(g, changeX, changeY);
			this.renderEnemies(g, changeX, changeY, false);
			this.renderGoodies(g, changeX, changeY);
			if (this.darkTime > 0) {
				g.setColor(new Color(0, 0, 0, Math.max(0, 254 - (int)(this.darkTime * 2.54f))));
				g.fillRect(0, 0, ApoMarioConstants.GAME_WIDTH, ApoMarioConstants.GAME_HEIGHT);
				this.renderCoins(g, changeX, changeY);
			}
		}
		this.renderPlayer(g, changeX, changeY);
		this.renderParticle(g, changeX, changeY);
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
			if (startX + ApoMarioConstants.GAME_WIDTH >= iBackground.getWidth()) {
				int newW = ApoMarioConstants.GAME_WIDTH - (iBackground.getWidth() - startX) + 1;
				g.drawImage(iBackground.getSubimage((int)(startX), (int)(0), iBackground.getWidth() - startX, ApoMarioConstants.GAME_HEIGHT), 0, 0, null);
				g.drawImage(iBackground.getSubimage((int)(0), (int)(0), newW, ApoMarioConstants.GAME_HEIGHT), iBackground.getWidth() - startX, 0, null);				
			} else {
				g.drawImage(iBackground.getSubimage((int)(startX), (int)(0), ApoMarioConstants.GAME_WIDTH, ApoMarioConstants.GAME_HEIGHT), 0, 0, null);
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
				if ((changeX - ApoMarioConstants.TILE_SIZE < this.particle.get(i).getX()) &&
					(changeX + ApoMarioConstants.GAME_WIDTH + ApoMarioConstants.TILE_SIZE > this.particle.get(i).getX())) {
					this.particle.get(i).render(g, changeX, changeY);
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
		if (this.player != null) {
			this.player.render(g, changeX, changeY);
		}
	}
	
	/**
	 * rendert die Gegner
	 * @param g : das Grafikobjekt
	 * @param changeX : Verschiebung auf der X-Achse
	 * @param changeY : Verschiebung auf der Y-Achse
	 */
	private void renderEnemies(Graphics2D g, int changeX, int changeY, boolean bFlower) {
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
					//if (this.goodies.get(i).isBVisible()) {
						this.goodies.get(i).render(g, changeX, changeY);
					//}
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
	private void renderCoins(Graphics2D g, int changeX, int changeY) {
		if (this.levelEntities != null) {
			int startX = changeX / ApoMarioConstants.TILE_SIZE;
			int startY = changeY / ApoMarioConstants.TILE_SIZE;
			int endX = startX + ApoMarioConstants.GAME_WIDTH / ApoMarioConstants.TILE_SIZE;
			int endY = startY + ApoMarioConstants.GAME_HEIGHT / ApoMarioConstants.TILE_SIZE;
			for (int y = startY; (y < this.height) && (y < endY + 1); y++) {
				for (int x = startX; (x < this.width) && (x < endX + 1); x++) {
					if (this.levelEntities[y][x] != null) {
						if ((this.levelEntities[y][x] instanceof ApoMarioCoin)) {
							this.levelEntities[y][x].render(g, changeX, changeY);
						}
					}
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
		}
	}
}