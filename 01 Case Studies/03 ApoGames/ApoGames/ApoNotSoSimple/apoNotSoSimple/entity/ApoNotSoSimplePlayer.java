package apoNotSoSimple.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.apogames.entity.ApoEntity;

import apoNotSoSimple.ApoNotSoSimpleConstants;

/**
 * Klasse, die den Spieler repräsentiert
 * @author Dirk Aporius
 *
 */
public class ApoNotSoSimplePlayer extends ApoEntity {

	/** Variable die angibt, wie schnell der Spieler gerade ist */
	private float speed;
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

	private int fixedMovement = -1;

	public ApoNotSoSimplePlayer(BufferedImage pic, float x, float y, float width, float height) {
		super(pic, x, y, width, height);
	}
	
	public void init() {
		super.init();
		this.setBOpaque(true);

		this.speed = ApoNotSoSimpleConstants.PLAYER_SPEED_MIN;
		this.isMoving = false;
		this.nextDirection = -1;
		this.changeMovement = 0;
	}

	public final int getFixedMovement() {
		return this.fixedMovement;
	}

	public void setFixedMovement(final int fixedMovement) {
		this.fixedMovement = fixedMovement;
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
	public void setMoving(final boolean isMoving) {
		this.isMoving = isMoving;
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
		if (speed < ApoNotSoSimpleConstants.PLAYER_SPEED_MIN) {
			speed = ApoNotSoSimpleConstants.PLAYER_SPEED_MIN;
		} else if (speed > ApoNotSoSimpleConstants.PLAYER_SPEED_MAX) {
			speed = ApoNotSoSimpleConstants.PLAYER_SPEED_MAX;
		}
		this.speed = speed;
	}
	
	/**
	 * gibt zurück, welche neue Richtung eingeschlagen werden soll<br />
	 * Möglichkeiten wären<br />
	 * ApoNotSoSimpleConstants.PLAYER_DIRECTION_DOWN für runter<br />
	 * ApoNotSoSimpleConstants.PLAYER_DIRECTION_UP für hoch<br />
	 * ApoNotSoSimpleConstants.PLAYER_DIRECTION_LEFT für links<br />
	 * ApoNotSoSimpleConstants.PLAYER_DIRECTION_RIGHT für rechts<br />
	 * @return gibt zurück, welche neue Richtung eingeschlagen werden soll<br />
	 */
	public final int getNextDirection() {
		return this.nextDirection;
	}

	/**
	 * setzt den Wert, welche neue Richtung eingeschlagen werden soll, auf den Übergebenen<br />
	 * Möglichkeiten wären<br />
	 * ApoNotSoSimpleConstants.PLAYER_DIRECTION_DOWN für runter<br />
	 * ApoNotSoSimpleConstants.PLAYER_DIRECTION_UP für hoch<br />
	 * ApoNotSoSimpleConstants.PLAYER_DIRECTION_LEFT für links<br />
	 * ApoNotSoSimpleConstants.PLAYER_DIRECTION_RIGHT für rechts<br />
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
	public void moveNextDirection() {
		if (this.isMoving) {
			return;
		}
		if (this.fixedMovement >= 0) {
			this.nextDirection = this.fixedMovement;
			if (this.nextDirection == ApoNotSoSimpleConstants.PLAYER_DIRECTION_LEFT) {
				this.nextDirection = ApoNotSoSimpleConstants.PLAYER_DIRECTION_UP;
			} else if (this.nextDirection == ApoNotSoSimpleConstants.PLAYER_DIRECTION_RIGHT) {
				this.nextDirection = ApoNotSoSimpleConstants.PLAYER_DIRECTION_DOWN;
			} else if ((this.nextDirection == ApoNotSoSimpleConstants.PLAYER_DIRECTION_STEP) ||
					(this.nextDirection == ApoNotSoSimpleConstants.PLAYER_DIRECTION_STEP_FINISH)) {
				this.nextDirection = -1;
			}
		}
		this.isMoving = true;
		if (this.nextDirection == ApoNotSoSimpleConstants.PLAYER_DIRECTION_DOWN) {
			this.setVelocityX(0);
			this.setVelocityY(1);
		} else if (this.nextDirection == ApoNotSoSimpleConstants.PLAYER_DIRECTION_UP) {
			this.setVelocityX(0);
			this.setVelocityY(-1);
		} else if (this.nextDirection == ApoNotSoSimpleConstants.PLAYER_DIRECTION_LEFT) {
			if (this.getX() <= 0) {
				this.isMoving = false;
			} else {
				this.setVelocityX(-1);
				this.setVelocityY(0);
			}
		} else if (this.nextDirection == ApoNotSoSimpleConstants.PLAYER_DIRECTION_RIGHT) {
			if (this.getX() + this.getWidth() >= ApoNotSoSimpleConstants.LEVEL_WIDTH) {
				this.isMoving = false;
			} else {
				this.setVelocityX(+1);
				this.setVelocityY(0);
			}
		} else if (this.nextDirection == ApoNotSoSimpleConstants.PLAYER_DIRECTION_CHANGEVISIBLE_UP) {
			super.setBVisible(!super.isBVisible());
			this.nextDirection = -1;
			this.isMoving = false;
		} else if (this.nextDirection == ApoNotSoSimpleConstants.PLAYER_DIRECTION_CHANGEVISIBLE_LEFT) {
			super.setBVisible(!super.isBVisible());
			this.nextDirection = -1;
			this.isMoving = false;
		} else if (this.nextDirection == ApoNotSoSimpleConstants.PLAYER_DIRECTION_FINISH) {
			this.isMoving = false;
		} else if (this.nextDirection == ApoNotSoSimpleConstants.PLAYER_DIRECTION_NO_MOVEMENT) {
			this.isMoving = false;
		}
		this.changeMovement = 0;
		this.changeMovementX = this.getX();
		this.changeMovementY = this.getY();
	}
	
	public void think(int delta) {
		super.think(delta);
		if (this.isMoving) {
			this.movePlayer(delta);
		} else {
			if (this.nextDirection >= 0) {
				this.moveNextDirection();
				if (this.isMoving) {
					this.movePlayer(delta);
				}
			}
		}
		this.nextDirection = -1;
	}
	
	/**
	 * bewegt den Spieler
	 * @param delta : Zeit, die seit dem letzten Aufruf vergangen ist
	 */
	private void movePlayer(int delta) {
		float changeX = this.speed * this.getVelocityX() * delta;
		float changeY = this.speed * this.getVelocityY() * delta;
		this.changeMovement += changeX + changeY;
		this.setX((this.getX() + changeX));
		this.setY((this.getY() + changeY));
		if (Math.abs(this.changeMovement) >= ApoNotSoSimpleConstants.TILE_SIZE) {
			this.isMoving = false;
			this.changeMovement = 0;
			this.setX((int)(((this.changeMovementX) / ApoNotSoSimpleConstants.TILE_SIZE) + this.getVelocityX()) * ApoNotSoSimpleConstants.TILE_SIZE);
			this.setY((int)(((this.changeMovementY) / ApoNotSoSimpleConstants.TILE_SIZE) + this.getVelocityY()) * ApoNotSoSimpleConstants.TILE_SIZE);
			this.setVelocityX(0);
			this.setVelocityY(0);
			if (this.getY() < 0) {
				this.setY(ApoNotSoSimpleConstants.LEVEL_HEIGHT - ApoNotSoSimpleConstants.TILE_SIZE);
			} else if (this.getY() + this.getHeight() > ApoNotSoSimpleConstants.LEVEL_HEIGHT) {
				this.setY(0);
			}
		}
	}
	
	public void render( Graphics2D g, int x, int y ) {
		if (super.isBVisible()) {
			if (super.getIBackground() != null) {
				g.drawImage(this.getIBackground(), (int)(this.getX() + x), (int)(this.getY() + y), null);
				if (this.getY() < 0) {
					g.drawImage(this.getIBackground(), (int)(this.getX() + x), (int)(this.getY() + y + ApoNotSoSimpleConstants.LEVEL_HEIGHT), null);
				} else if (this.getY() + this.getHeight() > ApoNotSoSimpleConstants.LEVEL_HEIGHT) {
					g.drawImage(this.getIBackground(), (int)(this.getX() + x), (int)(this.getY() + y - ApoNotSoSimpleConstants.LEVEL_HEIGHT), null);
				}
			}
		}
	}

}