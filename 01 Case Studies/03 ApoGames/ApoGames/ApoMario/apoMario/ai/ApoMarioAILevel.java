package apoMario.ai;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import apoMario.ApoMarioConstants;
import apoMario.entity.ApoMarioCannon;
import apoMario.entity.ApoMarioCoin;
import apoMario.entity.ApoMarioDestructableWall;

import apoMario.entity.ApoMarioFlower;
import apoMario.entity.ApoMarioPlayer;

import apoMario.entity.ApoMarioQuestionmark;
import apoMario.entity.ApoMarioWall;
import apoMario.level.ApoMarioLevel;

/**
 * Klasse, die das Level repräsentiert
 * @author Dirk Aporius
 *
 */
public class ApoMarioAILevel {

	private ApoMarioLevel level;
	
	private ArrayList<ApoMarioAIEnemy> enemies;
	
	private ArrayList<ApoMarioAIGoodie> goodies;
	
	private ApoMarioAIEntity enemyPlayer;
	
	private byte[][] levelArray;
	
	private int player;
	
	public ApoMarioAILevel(ApoMarioLevel level, int player) {
		this.level = level;
		this.player = player;
		this.init();
	}
	
	/**
	 * wird beim Initialisieren aufgerufen und erstellt die Arrays für die Gegner, Goodie und auch das Level selber
	 */
	private void init() {
		this.enemies = new ArrayList<ApoMarioAIEnemy>();
		ApoMarioPlayer myPlayer = this.level.getPlayers().get(this.player);
		int myX = (int)(myPlayer.getRec().getX() * ApoMarioConstants.APP_SIZE - ((int)ApoMarioConstants.GAME_WIDTH / 2f));
		if (myX < 0) {
			myX = 0;
		}
		int startX = (int)(ApoMarioConstants.getStartX(myPlayer));
		
		if (this.level.getEnemies() != null) {
			for (int i = this.level.getEnemies().size() - 1; i >= 0; i--) {
				if ((this.level.getEnemies().get(i).getRec().getX() + this.level.getEnemies().get(i).getRec().getWidth() >= myX / ApoMarioConstants.APP_SIZE) &&
					(this.level.getEnemies().get(i).getRec().getX() <= myX / ApoMarioConstants.APP_SIZE + ApoMarioConstants.GAME_WIDTH / ApoMarioConstants.APP_SIZE)) {
					if ((this.level.getEnemies().get(i).isBVisible()) && (!this.level.getEnemies().get(i).isBDie())) {
						if (this.level.getEnemies().get(i) instanceof ApoMarioFlower) {
							ApoMarioFlower flower = (ApoMarioFlower)this.level.getEnemies().get(i);
							if (flower.getMyChangeY() != 0) {
								this.enemies.add(new ApoMarioAIEnemy(flower, this.level, startX));
							}
						} else {
							this.enemies.add(new ApoMarioAIEnemy(this.level.getEnemies().get(i), this.level, startX));
						}
					}
				}
			}
		}
		this.goodies = new ArrayList<ApoMarioAIGoodie>();
		if (this.level.getGoodies() != null) {
			for (int i = this.level.getGoodies().size() - 1; i >= 0; i--) {
				if ((this.level.getGoodies().get(i).getRec().getX() + this.level.getGoodies().get(i).getRec().getWidth() >= myX / ApoMarioConstants.APP_SIZE) &&
					(this.level.getGoodies().get(i).getRec().getX() <= myX / ApoMarioConstants.APP_SIZE + ApoMarioConstants.GAME_WIDTH / ApoMarioConstants.APP_SIZE)) {
					if (this.level.getGoodies().get(i).isBVisible()) {
						this.goodies.add(new ApoMarioAIGoodie(this.level.getGoodies().get(i), this.level, startX));
					}
				}
			}
		}
		int startY = (int)((this.level.getComponent().getChangeY()) / ApoMarioConstants.TILE_SIZE);
		if (startY < 0) {
			startY = 0;
		}
		int width = ApoMarioConstants.GAME_WIDTH / ApoMarioConstants.APP_SIZE / ApoMarioConstants.TILE_SIZE;
		int height = ApoMarioConstants.GAME_HEIGHT / ApoMarioConstants.APP_SIZE / ApoMarioConstants.TILE_SIZE;
		while (startX + width >= this.level.getWidth()) {
			width -= 1;
		}
		try {
			for (int y = 0; y < this.level.getLevelEntities().length; y++) {
				for (int x = 0; x < this.level.getLevelEntities()[0].length; x++) {
					if ((this.level.getLevelEntities()[y][x] != null) &&
						(this.level.getLevelEntities()[y][x] instanceof ApoMarioCannon)) {
						ApoMarioCannon cannon = (ApoMarioCannon)(this.level.getLevelEntities()[y][x]);
						if ((cannon.getCannons() != null) && (cannon.getCannons().size() > 0)) {
							Rectangle2D.Double rec = new Rectangle2D.Double(myX / ApoMarioConstants.APP_SIZE, this.level.getComponent().getChangeY(), ApoMarioConstants.GAME_WIDTH / ApoMarioConstants.APP_SIZE, ApoMarioConstants.GAME_HEIGHT / ApoMarioConstants.APP_SIZE);
							int cannonSize = cannon.getCannons().size();
							for (int i = 0; i < cannonSize; i++) {
								if ((cannon.getCannons().get(i).isBVisible()) &&
									(!cannon.getCannons().get(i).isBDie()) &&
									(cannon.getCannons().get(i).getRec().intersects(rec))) {
									this.enemies.add(new ApoMarioAIEnemy(cannon.getCannons().get(i), this.level, startX));
								}
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			System.out.println("exception: "+ex.getCause()+" "+ex.getMessage());
		}
		this.levelArray = new byte[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (this.level.getLevelEntities()[y+startY][x+startX] == null) {
					this.levelArray[y][x] = ApoMarioAIConstants.LEVEL_EMPTY;
				} else if (this.level.getLevelEntities()[y+startY][x+startX] instanceof ApoMarioCannon) {
					this.levelArray[y][x] = ApoMarioAIConstants.LEVEL_CANNON;
				} else if (this.level.getLevelEntities()[y+startY][x+startX] instanceof ApoMarioDestructableWall) {
					ApoMarioDestructableWall wall = (ApoMarioDestructableWall)(this.level.getLevelEntities()[y+startY][x+startX]);
					if (wall.isBWall()) {
						this.levelArray[y][x] = ApoMarioAIConstants.LEVEL_WALL;
					} else if (wall.isBVisible()) {
						if ((wall.getParticle() != null) && (wall.getParticle().size() > 0)) {
							this.levelArray[y][x] = ApoMarioAIConstants.LEVEL_EMPTY;
						} else {
							this.levelArray[y][x] = ApoMarioAIConstants.LEVEL_DESTRUCTIBLEBOX;
						}
					}
				} else if (this.level.getLevelEntities()[y+startY][x+startX] instanceof ApoMarioQuestionmark) {
					ApoMarioQuestionmark question = (ApoMarioQuestionmark)(this.level.getLevelEntities()[y+startY][x+startX]);
					if (question.isBWall()) {
						this.levelArray[y][x] = ApoMarioAIConstants.LEVEL_WALL;
					} else if (question.isBVisible()) {
						this.levelArray[y][x] = ApoMarioAIConstants.LEVEL_QUESTIONMARKBOX;
					}
				} else if (this.level.getLevelEntities()[y+startY][x+startX] instanceof ApoMarioWall) {
					ApoMarioWall wall = (ApoMarioWall)this.level.getLevelEntities()[y+startY][x+startX];
					if (wall.isBOnlyAboveWall()) {
						this.levelArray[y][x] = ApoMarioAIConstants.LEVEL_ONLY_ABOVE_WALL;
					} else if (wall.isBNoCollision()) {
						this.levelArray[y][x] = ApoMarioAIConstants.LEVEL_EMPTY;
					} else {
						this.levelArray[y][x] = ApoMarioAIConstants.LEVEL_WALL;
					}
				} else if (this.level.getLevelEntities()[y+startY][x+startX] instanceof ApoMarioCoin) {
					this.levelArray[y][x] = ApoMarioAIConstants.LEVEL_EMPTY;
					ApoMarioCoin coin = (ApoMarioCoin)(this.level.getLevelEntities()[y+startY][x+startX]);
					if (coin.isBVisible()) {
						this.goodies.add(new ApoMarioAIGoodie(coin, this.level, startX));
					}
				}
			}
		}
		int finishX = (int)(this.level.getFinish().getX()/ ApoMarioConstants.TILE_SIZE);
		if ((finishX < startX + width - 1) && (finishX > startX)) {
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < 2; x++) {
					this.levelArray[y][finishX-startX+x] = ApoMarioAIConstants.LEVEL_FINISH;
				}
			}
		}
		if (this.player == 0) {
			if (this.level.getPlayers().size() > 1) {
				this.enemyPlayer = new ApoMarioAIEntity(this.level.getPlayers().get(1), this.level, startX);
			}
		} else {
			this.enemyPlayer = new ApoMarioAIEntity(this.level.getPlayers().get(0), this.level, startX);
		}
		for (int i = 0; i < this.level.getPlayers().size(); i++) {
			ApoMarioPlayer player = this.level.getPlayers().get(i);
			if ((player.getFireballs() != null) && (player.getFireballs().size() > 0)) {
				int size = player.getFireballs().size();
				for (int fire = 0; fire < size; fire++) {
					this.enemies.add(new ApoMarioAIEnemy(player.getFireballs().get(fire), this.level, startX));
				}
			}
		}
	}
	
	/**
	 * gibt die Veränderung der Anzahl von Tiles in x-Richtung wieder<br />
	 * Dass heißt wenn es den Wert 6 wiedergibt, dann ist der Viewport um 6 Tiles nach rechts verschoben worden
	 * @return gibt die Veränderung der Anzahl von Tiles in x-Richtung wieder<br />
	 */
	public float getChangeX() {
		ApoMarioPlayer myPlayer = this.level.getPlayers().get(this.player);
		float myX = (float)((float)myPlayer.getRec().getX() * ApoMarioConstants.APP_SIZE - ((float)ApoMarioConstants.GAME_WIDTH / 2));
		if (myX < 0) {
			myX = 0;
		}
		myX = (myX / ApoMarioConstants.TILE_SIZE / ApoMarioConstants.APP_SIZE);
		return myX;
	}
	
	/**
	 * gibt die Zeit, die am Anfang des Levels zur Verfügung stand, zurück in ms<br />
	 * @return gibt die Zeit, die am Anfang des Levels zur Verfügung stand zurück, in ms
	 */
	public int getStartTime() {
		return this.level.getStartTime();
	}
	
	/**
	 * gibt die Zeit zurück, die der Spieler noch zur Verfügung hat ins Ziel zu kommen in ms<br />
	 * Das bedeutet, wenn man 10000 zurückbekommt, hat man noch 10 Sekunden Zeit ins Ziel zu kommen<br />
	 * @return gibt die Zeit zurück, die der Spieler noch zur Verfügung hat ins Ziel zu kommen in ms
	 */
	public int getTimeLeft() {
		return this.level.getTime();
	}
	
	/**
	 * gibt den Schwierigkeitsgrad des Levels zurück
	 * @return gibt den Schwierigkeitsgrad des Levels zurück
	 */
	public int getDifficulty() {
		return this.level.getDifficulty();
	}
	
	/**
	 * gibt die Breite des Levels zurück
	 * @return gibt die Breite des Levels zurück
	 */
	public int getLevelWidth() {
		return this.level.getWidth();
	}
	
	/**
	 * gibt die Zufalls zurück, auf deren Basis das Level gebaut wurde
	 * @return gibt die Zufalls zurück, auf deren Basis das Level gebaut wurde
	 */
	public long getLevelRandom() {
		return this.level.getLevelInt();
	}

	/**
	 * gibt eine ArrayList mit den Objekten der Gegner zurück<br />
	 * @return gibt eine ArrayList mit den Objekten der Gegner zurück<br />
	 */
	public ArrayList<ApoMarioAIEnemy> getEnemies() {
		return this.enemies;
	}

	/**
	 * gibt eine ArrayList mit den Objekten der Goodies zurück<br />
	 * @return gibt eine ArrayList mit den Objekten der Goodies zurück<br />
	 */
	public ArrayList<ApoMarioAIGoodie> getGoodies() {
		return this.goodies;
	}

	/**
	 * gibt ein 2-dimensionales byte Array mit den eigentlichen Leveldaten des Viewports zurück<br />
	 * die Stelle 0, 0 ist links oben<br />
	 * die Breite eines Tiles ist auf 1 normiert. Das bedeutet es ist nicht wichtig wieviele Pixel etwas breit oder lang ist.<br />
	 * Auch die Breit, Höhe und Position der anderen Entities ist auf 1 normiert<br />
	 * die letzte Breite und Höhe des Levels sind rechts unten<br />
	 * ein Aufruf wie getLevelArray()[2][4] würde das Tile an der y-Stelle 2 und der x-Stelle 4 abfragen<br />
	 * um herauszufinden wie breit der Ausschnitt ist einfach getLevelArray()[0].length aufrufen<br />
	 * um herauszufinden wie hoch das Ausschnitt ist einfahc getLevelArray().length aufrufen<br />
	 * <br />
	 * Leer = ApoMarioAIConstants.LEVEL_EMPTY<br />
	 * Wand = ApoMarioAIConstants.LEVEL_WALL<br />
	 * zerstörebare Wand = ApoMarioAIConstants.LEVEL_DESTRUCTIBLEBOX<br />
	 * Fragezeichenbox = ApoMarioAIConstants.LEVEL_QUESTIONMARKBOX<br />
	 * Kanone = ApoMarioAIConstants.LEVEL_CANNON<br />
	 * Ziel = ApoMarioAIConstants.LEVEL_FINISH<br />
	 * Wand, die nur oben die Kollisionsbetrachrung hat und ansonsten durchlaufbar ist = ApoMarioAIConstants.LEVEL_ONLY_ABOVE_WALL<br />
	 * @return gibt ein 2-dimensionales byte Array mit den eigentlichen Leveldaten zurück<br />
	 */
	public byte[][] getLevelArray() {
		return this.levelArray;
	}

	/**
	 * gibt den Gegnerspieler zurück<br />
	 * @return NULL kein Gegner, else das Gegnerobjekt<br />
	 */
	public ApoMarioAIEntity getEnemyPlayer() {
		if (this.enemyPlayer.isVisible()) {
			return this.enemyPlayer;
		}
		return null;
	}
	
}
