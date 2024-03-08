package apoSkunkman.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import apoSkunkman.ApoSkunkmanConstants;
import apoSkunkman.ai.ApoSkunkmanAI;
import apoSkunkman.ai.ApoSkunkmanAILevel;
import apoSkunkman.ai.ApoSkunkmanAIPlayer;
import apoSkunkman.entity.paint.ApoSkunkmanPlayerDraw;
import apoSkunkman.game.ApoSkunkmanPanel;
import apoSkunkman.level.ApoSkunkmanLevel;

/**
 * Klasse, die den Spieler repräsentiert
 * @author Dirk Aporius
 *
 */
public class ApoSkunkmanPlayer extends ApoSkunkmanEntity {
	/** Variable die angibt, wie schnell der Spieler gerade ist */
	private float speed;
	/** Variable die angibt, wieviele Stinktiere der Spieler maximal legen kann */
	private int maxSkunkman;
	/** Variable die angibt, wieviele Stinktiere der Spieler derzeit gelegt hat */
	private int curSkunkman;
	/** Variable die angibt, wie weit der Radius der Stinktiere des Spielers derzeit ist */
	private int curWidth;
	/** die eigentliche KI des Spielers */
	private ApoSkunkmanAI ai;
	/** das Kopfbild des Spielers (wird für die Anzeige der Statistiken benötigt) */
	private BufferedImage iHead;
	/** Variable die angibt, wieviel Punkte der Spieler derzeit hat */
	private int points;
	/** Variable die angibt, ob der Spieler sich gerade bewegt oder nicht */
	private boolean isMoving;
	/** Variable die angibt, welche neue Richtung eingeschlagen werden soll */
	private int nextDirection = -1;
	
	/** Hilfsvariable die angibt, wie weit sich schon seit dem letzten Stehen bewegt wurde */
	private float changeMovement;
	/** Hilfsvariable die angibt, welchen X-Wert der Spieler beim letzten Stehen hatte */
	private float changeMovementX;
	/** Hilfsvariable die angibt, welchen Y-Wert der Spieler beim letzten Stehen hatte */
	private float changeMovementY;
	/** eindeutige Variable die angibt, um welchen Spieler es sich handelt */
	private final int player;
	/** boolean Variable die angibt, ob ein Spieler eine Bombe legen will */
	private boolean laySkunkman;
	/** String Variable die angibt, wo die KI des Spielers liegt */
	private String pathAI;
	/** wieviele Nachrichten wurden seit dem letzten Aufruf der KI abgegeben von der KI */
	private ArrayList<String> messages;
	/** BufferedImage des Originalspielers */
	private final BufferedImage iOrgPlayer;
	/** welche Sachen sollen gezeichnet werden */
	private ArrayList<ApoSkunkmanPlayerDraw> drawElements;
	
	public ApoSkunkmanPlayer(BufferedImage pic, float x, float y, float width, float height, int player) {
		super(pic, x, y, width, height, ApoSkunkmanConstants.PLAYER_TILES, ApoSkunkmanConstants.PLAYER_ANIMATION_TIME, 4, false);
		
		this.player = player;
		this.iOrgPlayer = pic;
	}
	
	public void init() {
		super.init();
		
		super.setDirection(ApoSkunkmanConstants.PLAYER_DIRECTION_DOWN);
		this.speed = ApoSkunkmanConstants.PLAYER_SPEED_MIN;
		this.maxSkunkman = ApoSkunkmanConstants.PLAYER_SKUNKMAN_START_MAX_COUNT;
		this.curSkunkman = 0;
		this.curWidth = ApoSkunkmanConstants.PLAYER_WIDTH_MIN;
		this.iHead = super.getIBackground().getSubimage((int)(0 * this.getWidth()), (int)((ApoSkunkmanConstants.PLAYER_DIRECTION_DOWN)*super.getIBackground().getHeight()/8), (int)this.getWidth(), (int)((ApoSkunkmanConstants.PLAYER_DIRECTION_DOWN + 1)*super.getIBackground().getHeight()/8));
		this.points = 0;
		this.isMoving = false;
		this.nextDirection = -1;
		this.changeMovement = 0;
		if (this.pathAI == null) {
			this.pathAI = "";
		}
		if (this.messages == null) {
			this.messages = new ArrayList<String>();
		}
		this.messages.clear();
		
		if (this.drawElements == null) {
			this.drawElements = new ArrayList<ApoSkunkmanPlayerDraw>();
		}
		this.drawElements.clear();

	}
	
	/**
	 * wird vor dem Start des Spiels aufgerufen und sagt der KI das ein neues Level beginnt<br />
	 */
	public void loadPlayer() {
		if (this.ai != null) {
			this.ai.load(this.pathAI);
		}
	}

	/**
	 * gibt zurück, ob ein Spieler in der nächsten Runde eine Bombe legen will
	 * @return TRUE, Spieler will in der nächsten Runde eine Bombe legen, ansonsten FALSE
	 */
	public boolean isLaySkunkman() {
		return this.laySkunkman;
	}

	/**
	 * setzt den Wert, ob ein Spieler in der nächsten Runde eine Bombe legen will, auf den übergebenen
	 * @param laySkunkman : TRUE, Spieler will in der nächsten Runde eine Bombe legen, ansonsten FALSE
	 */
	public void setLaySkunkman(boolean laySkunkman) {
		this.laySkunkman = laySkunkman;
	}

	/**
	 * gibt zurück, um welchen Spieler es sich handelt
	 * @return gibt zurück, um welchen Spieler es sich handelt
	 */
	public final int getPlayer() {
		return player;
	}

	/**
	 * gibt zurück, ob der Spieler sich gerade bewegt oder nicht
	 * @return TRUE, Spieler bewegt sich, FALSE Spieler steht
	 */
	public final boolean isMoving() {
		return this.isMoving;
	}

	/**
	 * setzt die Variable, ob sich ein Spieler gerade bewegt auf die Übergebene
	 * @param isMoving : TRUE, Spieler bewegt sich, FALSE Spieler steht
	 */
	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	/**
	 * gibt zurück, wieviel Punkte der Spieler derzeit hat
	 * @return gibt zurück, wieviel Punkte der Spieler derzeit hat
	 */
	public final int getPoints() {
		return this.points;
	}

	/**
	 * setzt die Punkte, die der Spieler derzeit hat, auf den übergebenen Wert
	 * @param points : Neue Punkteanzahl
	 */
	public void setPoints(int points) {
		this.points = points;
	}

	/**
	 * gibt das Kopfbild des Spielers zurück
	 * @return gibt das Kopfbild des Spielers zurück
	 */
	public final BufferedImage getIHead() {
		return this.iHead;
	}

	/**
	 * gibt zurück, wie schnell der Spieler gerade pro Millisekunde beim Laufen ist
	 * @return gibt zurück, wie schnell der Spieler gerade ist
	 */
	public final float getSpeed() {
		return this.speed;
	}

	/**
	 * setzt den Speed, wie schnell der Spieler gerade pro Millisekunde beim Laufen ist, auf den übergebenen Wert
	 * @param speed : Neuer Speed des Spielers pro Millisekunde beim Laufen
	 */
	public final void setSpeed(float speed) {
		if (speed < ApoSkunkmanConstants.PLAYER_SPEED_MIN) {
			speed = ApoSkunkmanConstants.PLAYER_SPEED_MIN;
		} else if (speed > ApoSkunkmanConstants.PLAYER_SPEED_MAX) {
			speed = ApoSkunkmanConstants.PLAYER_SPEED_MAX;
		}
		this.speed = speed;
	}
	
	/**
	 * gibt zurück, wie weit der Radius der Stinktiere des Spielers derzeit ist
	 * @return gibt zurück, wie weit der Radius der Stinktiere des Spielers derzeit ist
	 */
	public int getCurWidth() {
		return this.curWidth;
	}

	/**
	 * setzt den Wert, wie weit der Radius der Stinktiere des Spielers derzeit ist, auf den Übergebenen
	 * @param curWidth : neuer Wert, wie weit der Radius der Stinktiere des Spielers derzeit ist
	 */
	public void setCurWidth(int curWidth) {
		if (curWidth < ApoSkunkmanConstants.PLAYER_WIDTH_MIN) {
			curWidth = ApoSkunkmanConstants.PLAYER_WIDTH_MIN;
		} else if (curWidth > ApoSkunkmanConstants.PLAYER_WIDTH_MAX) {
			curWidth = ApoSkunkmanConstants.PLAYER_WIDTH_MAX;
		}
		this.curWidth = curWidth;
	}

	/**
	 * gibt zurück, wieviele Stinktiere der Spieler maximal legen kann
	 * @return gibt zurück, wieviele Stinktiere der Spieler maximal legen kann
	 */
	public final int getMaxSkunkman() {
		return this.maxSkunkman;
	}

	/**
	 * setzt den Wert, wieviele Stinktiere der Spieler maximal legen kann, auf den Übergebenen
	 * @param maxSkunkman : neuer Wert für die maximale Anzahl der Stinktiere der Spieler maximal legen kann
	 */
	public final void setMaxSkunkman(final int maxSkunkman) {
		this.maxSkunkman = maxSkunkman;
		if (this.maxSkunkman < 1) {
			this.maxSkunkman = 1;
		}
	}

	/**
	 * gibt zurück, wieviele Stinktiere der Spieler derzeit gelegt hat
	 * @return gibt zurück, wieviele Stinktiere der Spieler derzeit gelegt hat
	 */
	public final int getCurSkunkman() {
		return this.curSkunkman;
	}

	/**
	 * setzt den Wert, wieviele Stinktiere der Spieler derzeit gelegt hat, auf den Übergebenen
	 * @param curSkunkman : neuer Wert für die derzeitige Anzahl an gelegten Stinktieren
	 */
	public final void setCurSkunkman(int curSkunkman) {
		if (curSkunkman < 0) {
			curSkunkman = 0;
		} else if (curSkunkman > this.maxSkunkman) {
			curSkunkman = this.maxSkunkman;
		}
		this.curSkunkman = curSkunkman;
	}

	/**
	 * gibt zurück, ob der Spieler überhaupt ein Stinktier derzeit legen kann
	 * @return TRUE, Spieler darf ein Stinktier legen, else FALSE
	 */
	public final boolean canLaySkunkman() {
		if (this.curSkunkman < this.maxSkunkman) {
			return true;
		}
		return false;
	}
	
	/**
	 * gibt die eigentliche KI des Spielers zurück
	 * @return gibt die eigentliche KI des Spielers zurück
	 */
	public final ApoSkunkmanAI getAi() {
		return this.ai;
	}

	/**
	 * setzt die eigentliche KI des Spielers auf den übergebenen Wert
	 * @param ai : neue KI
	 * @param path : Pfad in welcher die KI liegt
	 */
	public final void setAi(final ApoSkunkmanAI ai, final String path, final ApoSkunkmanPanel panel) {
		this.ai = ai;
		this.pathAI = path;
		if (this.ai != null) {
			this.ai.load(this.pathAI);
			String pathPic = this.ai.getImage();
			if (pathPic != null) {
				BufferedImage iNewPlayer = null;
				if ((this.pathAI == null) || (this.pathAI.length() <= 0)) {
					iNewPlayer = panel.getImages().getImage("images/" + pathPic, false);
				} else {
					iNewPlayer = panel.getImages().getImage(this.pathAI + File.separator + pathPic, true);
				}
				if (iNewPlayer != null) {
					this.setIBackground(iNewPlayer);
				} else {
					System.out.println(pathPic);
					this.setIBackground(this.iOrgPlayer);
				}
			} else {
				this.setIBackground(this.iOrgPlayer);	
			}
		} else {
			this.setIBackground(this.iOrgPlayer);
		}
		this.iHead = super.getIBackground().getSubimage((int)(0 * this.getWidth()), (int)((ApoSkunkmanConstants.PLAYER_DIRECTION_DOWN)*super.getIBackground().getHeight()/8), (int)this.getWidth(), (int)((ApoSkunkmanConstants.PLAYER_DIRECTION_DOWN + 1)*super.getIBackground().getHeight()/8));
	}
	
	/**
	 * Methode, die aufgerufen wird, wenn ein Level beendet wird und jemand gewonnen hat
	 */
	public final void saveAI() {
		if (this.ai != null) {
			this.ai.save(this.pathAI);
		}
	}

	/**
	 * gibt den Spielernamen zurück
	 * @return gibt den Spielernamen zurück
	 */
	public final String getPlayerName() {
		if (this.ai == null) {
			if (this.player < 2) {
				return "human";
			} else {
				return "no ai";
			}
		} else if (this.ai.getPlayerName() == null) {
			return "AI-Player";
		} else {
			return this.ai.getPlayerName();
		}
	}
	
	/**
	 * gibt den Namen des Authors zurück
	 * @return gibt den Namen des Authors zurück
	 */
	public final String getAuthorName() {
		if (this.ai == null) {
			return "you";
		} else if (this.ai.getAuthor() == null) {
			return "AI-Creator";
		} else {
			return this.ai.getAuthor();
		}
	}
	
	/**
	 * gibt zurück, welche neue Richtung eingeschlagen werden soll<br />
	 * Möglichkeiten wären<br />
	 * ApoSkunkmanConstants.PLAYER_DIRECTION_DOWN für runter<br />
	 * ApoSkunkmanConstants.PLAYER_DIRECTION_UP für hoch<br />
	 * ApoSkunkmanConstants.PLAYER_DIRECTION_LEFT für links<br />
	 * ApoSkunkmanConstants.PLAYER_DIRECTION_RIGHT für rechts<br />
	 * @return gibt zurück, welche neue Richtung eingeschlagen werden soll<br />
	 */
	public final int getNextDirection() {
		return this.nextDirection;
	}

	/**
	 * setzt den Wert, welche neue Richtung eingeschlagen werden soll, auf den Übergebenen<br />
	 * Möglichkeiten wären<br />
	 * ApoSkunkmanConstants.PLAYER_DIRECTION_DOWN für runter<br />
	 * ApoSkunkmanConstants.PLAYER_DIRECTION_UP für hoch<br />
	 * ApoSkunkmanConstants.PLAYER_DIRECTION_LEFT für links<br />
	 * ApoSkunkmanConstants.PLAYER_DIRECTION_RIGHT für rechts<br />
	 * @param nextDirection : neue Richtung
	 */
	public void setNextDirection(int nextDirection) {
		if ((nextDirection < 0) || (nextDirection > 3)) {
			return;
		}
		this.nextDirection = nextDirection;
	}

	/**
	 * veranlasst den Spieler in eine neue Richtung zu gehen und setzt seine Werte dafür
	 */
	private void moveNextDirection() {
		if (this.isMoving) {
			return;
		}
		this.isMoving = true;
		this.setDirection(this.nextDirection);
		if (this.nextDirection == ApoSkunkmanConstants.PLAYER_DIRECTION_DOWN) {
			this.setVelocityX(0);
			this.setVelocityY(1);
		} else if (this.nextDirection == ApoSkunkmanConstants.PLAYER_DIRECTION_UP) {
			this.setVelocityX(0);
			this.setVelocityY(-1);
		} else if (this.nextDirection == ApoSkunkmanConstants.PLAYER_DIRECTION_LEFT) {
			this.setVelocityX(-1);
			this.setVelocityY(0);
		} else if (this.nextDirection == ApoSkunkmanConstants.PLAYER_DIRECTION_RIGHT) {
			this.setVelocityX(+1);
			this.setVelocityY(0);
		}
		this.changeMovement = 0;
		this.changeMovementX = this.getX();
		this.changeMovementY = this.getY();
	}
	
	/**
	 * Methode, die aufgerufen wird, wenn dieser Spieler ein Goodie aufgesammelt hat<br />
	 * und verpasst dem Spieler dann gleich seine neuen Werte
	 * @param goodie : Welches Goodie wurde eingesammelt
	 */
	public void addGoodie(int goodie) {
		this.points += ApoSkunkmanConstants.GOODIE_POINTS;
		if (goodie == ApoSkunkmanConstants.GOODIE_BAD_FAST) {
			this.setSpeed(this.getSpeed() - ApoSkunkmanConstants.PLAYER_SPEED_INCREASE);
		} else if (goodie == ApoSkunkmanConstants.GOODIE_BAD_WIDTH) {
			this.setCurWidth(this.getCurWidth() - 1);
		} else if (goodie == ApoSkunkmanConstants.GOODIE_BAD_SKUNKMAN) {
			this.setMaxSkunkman(this.getMaxSkunkman() - 1);
		} else if (goodie == ApoSkunkmanConstants.GOODIE_BAD_GOD) {
			this.setMaxSkunkman(-1000);
			this.setSpeed(ApoSkunkmanConstants.PLAYER_SPEED_MIN);
			this.setCurWidth(ApoSkunkmanConstants.PLAYER_WIDTH_MIN);
		} else if (goodie == ApoSkunkmanConstants.GOODIE_GOOD_FAST) {
			this.setSpeed(this.getSpeed() + ApoSkunkmanConstants.PLAYER_SPEED_INCREASE);
		} else if (goodie == ApoSkunkmanConstants.GOODIE_GOOD_WIDTH) {
			this.setCurWidth(this.getCurWidth() + 1);
		} else if (goodie == ApoSkunkmanConstants.GOODIE_GOOD_SKUNKMAN) {
			this.setMaxSkunkman(this.getMaxSkunkman() + 1);
		} else if (goodie == ApoSkunkmanConstants.GOODIE_GOOD_GOD) {
			this.setMaxSkunkman(+1000);
			this.setSpeed(ApoSkunkmanConstants.PLAYER_SPEED_MAX);
			this.setCurWidth(ApoSkunkmanConstants.PLAYER_WIDTH_MAX);
		}
	}
	
	/**
	 * fügt eine Nachricht dem Debugfenster hinzu
	 * @param message : neue Nachricht
	 */
	public void addMessage(String message) {
		this.messages.add(message);
	}
	
	/**
	 * fügt eine Nachricht dem Debugfenster hinzu
	 * @param message : neue Nachricht
	 */
	public void addDrawElement(ApoSkunkmanPlayerDraw element) {
		this.drawElements.add(element);
	}
	
	public void think(int delta) {
		super.think(delta);
		if (this.isMoving) {
			this.movePlayer(delta);
		} else if (this.nextDirection >= 0) {
			this.moveNextDirection();
		}
		this.nextDirection = -1;
		
		for (int i = this.drawElements.size() - 1; i >= 0; i--) {
			this.drawElements.get(i).setTime(this.drawElements.get(i).getTime() - delta);
			if (this.drawElements.get(i).getTime() <= 0) {
				this.drawElements.remove(i);
			}
		}
	}
	
	/**
	 * veranlasst die KI, falls vorhanden und der Spieler gerade nicht in Bewegung ist, nachzudenken
	 * @param delta : Zeit, die seit dem letzten Aufruf vergangen ist
	 */
	public void thinkAI(ApoSkunkmanLevel level, int delta) {
		if ((!this.isMoving) && (this.isBVisible())) {
			if (level.isReplayShowing()) {
				level.getReplay().receiveStep(level, this.player);
			} else if (this.ai != null) {
				this.nextDirection = -1;
				ApoSkunkmanAILevel aiLevel = new ApoSkunkmanAILevel(level, this.getPlayer());
				ApoSkunkmanAIPlayer player = new ApoSkunkmanAIPlayer(this);
				try {
					this.ai.think(aiLevel, player);
				} catch (Exception ex) {
					// Ausgabe auf den Error-Kanal, dann kann man das in der
					// Konsole durch Pipes in Datei umlenken und die
					// normalen Ausgaben bleiben auf der Konsole
					System.err.println("Player "+this.player+" Exception");		// wer war das
					ex.printStackTrace(System.err);								// und warum
					this.points += ApoSkunkmanConstants.PLAYER_EXCEPTION_POINTS;
					this.setBVisible(false);
				}
			}
			if (!level.isReplayShowing()) {
				level.getReplay().setStep(this.getPlayer(), this.nextDirection, this.laySkunkman);
			}
			if (this.isBVisible()) {
				if (ApoSkunkmanConstants.DEBUG) {
					for (String message : this.messages) {
						level.getGame().getDebugFrame().addString("Player "+(this.player + 1)+": "+message);
					}
				}
				if (this.laySkunkman) {
					int x = (int)(this.getX() / ApoSkunkmanConstants.TILE_SIZE);
					int y = (int)(this.getY() / ApoSkunkmanConstants.TILE_SIZE);
					if ((!this.isMoving()) && (this.canLaySkunkman()) && (level.getLevel()[y][x] == null)) {
						level.layBomb(x, y, this.getPlayer());
					}
				}
				// falls eine neue Richtung gegeben wurde, dann überprüfe ob er da überhaupt hingehen kann
				if (this.nextDirection != -1) {
					if (this.nextDirection == ApoSkunkmanConstants.PLAYER_DIRECTION_DOWN) {
						if (!level.canPlayerGoDown(this)) {
							this.nextDirection = -1;
						}
					} else if (this.nextDirection == ApoSkunkmanConstants.PLAYER_DIRECTION_UP) {
						if (!level.canPlayerGoUp(this)) {
							this.nextDirection = -1;
						}
					} else if (this.nextDirection == ApoSkunkmanConstants.PLAYER_DIRECTION_LEFT) {
						if (!level.canPlayerGoLeft(this)) {
							this.nextDirection = -1;
						}
					} else if (this.nextDirection == ApoSkunkmanConstants.PLAYER_DIRECTION_RIGHT) {
						if (!level.canPlayerGoRight(this)) {
							this.nextDirection = -1;
						}
					}
				}
			}
			this.laySkunkman = false;
			this.messages.clear();
		}
	}
	
	/**
	 * bewegt den Spieler
	 * @param delta : Zeit, die seit dem letzten Aufruf vergangen ist
	 */
	private void movePlayer(int delta) {
		float changeX = this.speed * this.getVelocityX() * delta * ApoSkunkmanConstants.APPLICATION_SIZE;
		float changeY = this.speed * this.getVelocityY() * delta * ApoSkunkmanConstants.APPLICATION_SIZE;
		this.changeMovement += changeX + changeY;
		this.setX((this.getX() + changeX));
		this.setY((this.getY() + changeY));
		if (Math.abs(this.changeMovement) >= ApoSkunkmanConstants.TILE_SIZE) {
			this.isMoving = false;
			this.changeMovement = 0;
			this.setX((int)(((this.changeMovementX) / ApoSkunkmanConstants.TILE_SIZE) + this.getVelocityX()) * ApoSkunkmanConstants.TILE_SIZE);
			this.setY((int)(((this.changeMovementY) / ApoSkunkmanConstants.TILE_SIZE) + this.getVelocityY()) * ApoSkunkmanConstants.TILE_SIZE);
			this.setVelocityX(0);
			this.setVelocityY(0);
		}
	}
	
	public final void render( Graphics2D g, int x, int y ) {
		if (super.isBVisible()) {
			if (super.getIBackground() != null) {
				if (this.isMoving) {
					g.drawImage(this.getImages()[this.getDirection()][this.getFrame()], (int)(this.getX() + x), (int)(this.getY() + y - super.getIBackground().getHeight()/4 + super.getHeight()), null);
				} else {
					g.drawImage(this.getImages()[this.getDirection()][0], (int)(this.getX() + x), (int)(this.getY() + y - super.getIBackground().getHeight()/4 + super.getHeight()), null);					
				}
			}
			if (ApoSkunkmanConstants.DEBUG) {
				for (int i = this.drawElements.size() - 1; i >= 0; i--) {
					this.drawElements.get(i).render(g, x, y);
				}
			}
		}
	}

}