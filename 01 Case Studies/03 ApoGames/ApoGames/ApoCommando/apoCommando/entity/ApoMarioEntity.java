package apoCommando.entity;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.apogames.entity.ApoAnimation;

import apoCommando.ApoMarioConstants;
import apoCommando.ApoMarioImageContainer;
import apoCommando.level.ApoMarioLevel;

/**
 * Klasse, ob der jede Entität im Spiel erbt,
 * also sowohl der Spieler als auch die Gegner und die Levelobjekte
 * Sie kümmert sich auch um die Animation der Objekte
 * @author Dirk Aporius
 */
public abstract class ApoMarioEntity extends ApoAnimation {
	
	/** Variablen, die angeben in welche Richtung der SPieler gerade läuft */
	public static final int RIGHT = 0;
	public static final int LEFT = 1;
	
	private ApoMarioLevel level;
	
	private int id;
	
	/** Variable, die die aktulle Richtung enthält*/
	private int direction;

	public ApoMarioEntity(BufferedImage animation, float x, float y, float width, float height, int tiles, long time, int id) {
		super(animation, x, y, width, height, tiles, time, 1, false);
		this.id = id;
	}
	
	/**
	 * gibt die eindeutige ID der Entity zurück
	 * @return gibt die eindeutige ID der Entity zurück
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * gibt die Blickrichtung der Entity zurück
	 * @return gibt die Blickrichtung der Entity zurück
	 */
	public int getDirection() {
		return this.direction;
	}

	/**
	 * wechselt die Blickrichtung der Entity von links auf rechts oder umgekehrt
	 */
	public void changeDirection() {
		this.direction += 1;
		if (this.direction > ApoMarioEntity.LEFT) {
			this.direction = ApoMarioEntity.RIGHT;
		}
	}
	
	/**
	 * setzt die Blickrichtung
	 * @param direction
	 */
	public void setDirection(int direction) {
		this.direction = direction;
		if (ApoMarioEntity.RIGHT > direction) {
			this.direction = ApoMarioEntity.RIGHT;
		} else if (ApoMarioEntity.LEFT < direction) {
			this.direction = ApoMarioEntity.LEFT;
		}
	}
	
	/**
	 * gibt das Rechteck für den nächsten Schritt der Entity zurück
	 * @return gibt das Rechteck für den nächsten Schritt der Entity zurück
	 */
	public Rectangle2D.Float getNextRec(int delta) {
		return new Rectangle2D.Float(this.getX() + delta * this.getVelocityX(), this.getY() + delta * this.getVelocityY(), this.getWidth(), this.getHeight());
	}
	
	/**
	 * gibt das Objekt des Levels zurück
	 * @return gibt das Objekt des Levels zurück
	 */
	public ApoMarioLevel getLevel() {
		return this.level;
	}

	public void setLevel(ApoMarioLevel level) {
		this.level = level;
	}

	/**
	 * veranlasst den Spieler zum Nachdenken / Animieren
	 */
	public void think(int delta, ApoMarioLevel level) {
		this.level = level;
		super.think(delta);
	}
	
	/**
	 * abstrakte Methode, die von jeder Entity aufgerufen wird, wenn sie auf den Boden kommt 
	 */
	public abstract void yDownCheck(ApoMarioLevel level, int x, int y, int delta);
	
	public boolean yDownCheckEntity(ApoMarioLevel level, int x, int y, int delta) {
		if (this.getVelocityY() > 0) {
			this.setVelocityY(0);
			yDownCheck(level, x, y, delta);
			this.setY(y * ApoMarioConstants.TILE_SIZE - this.getHeight());
		}
		return true;
	}
	
	/**
	 * testet die Entity, ob sie im nächsten Schritt Bodenkontakt hat
	 * @param delta : Zeit die vergangen ist, seit dem letzten Aufruf
	 * @param level : das Objekt des Levels
	 */
	public void nextYCheck(int delta, ApoMarioLevel level, int player) {
		if (this.getVelocityY() < 0) {
			return;
		}
		Rectangle2D.Float nextRec = this.getNextRec(delta); // gib mir das Rechteck vom nächsten Schritt zurück
		int startX = (int)(nextRec.x / ApoMarioConstants.TILE_SIZE); // umgerechneter x-Wert zur Überprüfung mit den Blöcken
		int startY = (int)((nextRec.y + nextRec.height) / ApoMarioConstants.TILE_SIZE) + 2; // umgerechneter y-Wert zur Überprüfung mit den Blöcken
		if (startX < 0) {
			startX = 0;
		}
		if (startY < 0) {
			startY = 0;
		} else if (startY >= level.getHeight()) {
			startY = level.getHeight() - 1;
		}
		// zu überprüfender Teil, ob der frei ist
		// ist einfach der untere Teil der Entity
		Rectangle2D.Float myNextRec = new Rectangle2D.Float(nextRec.x, nextRec.y + nextRec.height, nextRec.width, 2 * ApoMarioConstants.SIZE); 
		boolean bBreak = false;
		for (int y = startY; (y >= startY - 2) && (y >= 0); y--) {
			for (int x = startX; (x < startX + 2) && (x < level.getWidth()); x++) {
				// als erstes wird geschaut ob die Entity nicht leer und sichtbar ist und ob es eine Mauer ist und ob sie Kollisionen zulässt
				// falls ja dann schaue weiter =)
				if ((level.getLevelEntities()[y][x] != null) &&
					(level.getLevelEntities()[y][x].isBVisible()) &&
					(level.getLevelEntities()[y][x] instanceof ApoMarioWall) &&
					(!((ApoMarioWall)(level.getLevelEntities()[y][x])).isBNoCollision())) {
					// Wenn es eine zerstörbare Wand ist und schon Partikel nach ihrer Zerstörung zu sehen sind dann mache nicht
					if ((level.getLevelEntities()[y][x] instanceof ApoMarioDestructableWall) &&
						(((ApoMarioDestructableWall)(level.getLevelEntities()[y][x])).getParticle().size() > 0)) {
					// ansonsten überprüfe, ob der Block mit dem zu überprüfenden Teil der Entity sich schneidet
					// und falls ja gib mir die Schnittmenge beider Rechtecke zurück
					} else if (myNextRec.intersects(level.getLevelEntities()[y][x].getRec())) {
						Rectangle2D.Float rec = level.getLevelEntities()[y][x].getRec();
						Rectangle2D.Float myRec = rec;//*/new Rectangle2D.Float(rec.x, rec.y, rec.width, 2 * ApoMarioConstants.SIZE);
						Rectangle2D.Float cut = (Rectangle2D.Float)myNextRec.createIntersection(myRec);
						// falls die Breite und die Höhe größer als die SIZE ist und die Breite größer ist als die Höhe dann ist bewiesen, dass
						// sich die Entity mit der Wand/Block unten schneidet. Setze die Entity an die richtige Stelle und sage das die Geschwindigkeit
						// in y-Richtung 0 ist
						if (((cut.width > 0 * ApoMarioConstants.SIZE) || (cut.height > 0 * ApoMarioConstants.SIZE))
							/*&& (cut.width > cut.height)*/) {
							if ((player == ApoMarioConstants.VECY_FIREBALL) && (cut.height + 1 * ApoMarioConstants.SIZE > cut.width)) {
							} else if ((player == ApoMarioConstants.VECY_ENEMY) && (cut.width > cut.height + 3 * ApoMarioConstants.SIZE)) {
								if (this.yDownCheckEntity(level, x, y, delta)) {
									bBreak = true;
									return;
								}
							} else if ((player == ApoMarioConstants.VECY_PLAYER) || (player == ApoMarioConstants.VECY_FIREBALL)) {
								//if ((ApoMarioConstants.DIFFICULTY <= 1) || ((ApoMarioConstants.DIFFICULTY > 1) && (((cut.width > cut.height * 3) || (Math.abs(this.getVelocityX()) >= ApoMarioConstants.PLAYER_MAX_VEC_X_FAST))))) {
									if (this.yDownCheckEntity(level, x, y, delta)) {
										bBreak = true;
										return;
									}
								//}
							}
						}
					}
				}
			}
			if (bBreak) {
				break;
			}
		}
		// falls sich die Entity nicht schneidet und die Geschwindigkeit bei 0 ist, gib ihr einen minimalen Schubs nach unten =)
		if (!bBreak) {
			if (this.getVelocityY() == 0) {
				this.setVelocityY(0.000001f);
			}
		}
	}
	
	/**
	 * abstrakte Methode, die aufgerufen wird, wenn eine Entity mit einer Coin kollidiert
	 * @param level : das Levelobjekt
	 * @param x : X-Stelle der Münze
	 * @param y : Y-Stelle der Münze
	 */
	public abstract void coinCheck(ApoMarioLevel level, int x, int y);
	
	/**
	 * abstrakte Methode, die aufgerufen wird, wenn eine Entity mit einer Wand oben kollidert (zum Beispiel beim Springen)
	 * @param level : das Levelobjekt
	 * @param x : X-Stelle der Wand
	 * @param y : Y-Stelle der Wand
	 */
	public abstract void yCheckUp(ApoMarioLevel level, int x, int y);
	
	/**
	 * abstrakte Methode, die aufgerufen wird, wenn eine Entity mit einer Wand links oder rechts kollidert (zum Beispiel beim Laufen oder Springen)
	 * @param level : das Levelobjekt
	 * @param x : X-Stelle der Wand
	 * @param y : Y-Stelle der Wand
	 */
	public abstract void xCheckChange(ApoMarioLevel level, int x, int y);
	
	/**
	 * Methode zur Überprüfung, ob sich eine Entity mit dem Level/den Wänden schneidet/kollidiert
	 * Dabei wird das Rechteck der Entity genommen, geschaut mit welchen Wänden es kollidiert und das größte davon genommen
	 * @param delta : Zeit seit dem letzten Aufruf
	 * @param level : das Level-Objekt
	 */
	public void thinkCollision(int delta, ApoMarioLevel level) {
		Rectangle2D.Float rec = this.getRec();
		float difX = rec.x - this.getX(); // Differenz zwischen StartX des Rechtecks und des eigentlichen X-Wertes
		float difY = rec.y - this.getY(); // Differenz zwischen StartY des Rechtecks und des eigentlichen Y-Wertes
		int startX = (int)(rec.x / ApoMarioConstants.TILE_SIZE);
		int startY = (int)(rec.y / ApoMarioConstants.TILE_SIZE);
		if (startX < 0) {
			startX = 0;
		}
		if (startY < 0) {
			startY = 0;
		}
		// suche nach dem Rechteck mit der größten Schnittmenge mit der Entity
		Rectangle2D.Float biggestCut = null;
		ArrayList<Point> points = new ArrayList<Point>();
		for (int y = startY; (y < startY + 3) && (y < level.getHeight()); y++) {
			for (int x = startX; (x < startX + 3) && (x < level.getWidth()); x++) {
				if ((level.getLevelEntities()[y][x] != null) && (level.getLevelEntities()[y][x].isBVisible())) {
					if (level.getLevelEntities()[y][x].getRec().intersects(rec)) {
						if (level.getLevelEntities()[y][x] instanceof ApoMarioCoin) {
							this.coinCheck(level, x, y);
						} else if ((level.getLevelEntities()[y][x] instanceof ApoMarioDestructableWall) &&
							(((ApoMarioDestructableWall)(level.getLevelEntities()[y][x])).getParticle().size() > 0)) {
						} else if (((level.getLevelEntities()[y][x] instanceof ApoMarioWall) &&
								(!((ApoMarioWall)(level.getLevelEntities()[y][x])).isBOnlyAboveWall()) &&
								(!((ApoMarioWall)(level.getLevelEntities()[y][x])).isBNoCollision()))) {
							Rectangle2D.Float cut = (Rectangle2D.Float)rec.createIntersection(level.getLevelEntities()[y][x].getRec());
							if ((cut.width > 0 * ApoMarioConstants.SIZE) || (cut.height > 0 * ApoMarioConstants.SIZE)) {
								if (points.size() <= 0) {
									biggestCut = cut;
									points.add(new Point(x, y));
								} else if (cut.width * cut.height > biggestCut.width * cut.height) {
									biggestCut = cut;
									points.add(0, new Point(x, y));
								}
							}
						}
					}
				}
			}
		}
		// die Wand mit der größten Schnittmenge mit dem Spieler
		for (int i = 0; /*i < 1 &&*/ i < points.size(); i++) {
			int x = points.get(i).x;
			int y = points.get(i).y;
			biggestCut = (Rectangle2D.Float)rec.createIntersection(level.getLevelEntities()[y][x].getRec());
			if (this.getVelocityY() < 0) {
				 //Spieler springt nach oben und kollidiert mit einer Entity. Die daraus resultierende
				// Schnittmenge ist Breiter als Höher -> Kollision mit einer Wand die über der Entity ist
				if ((biggestCut.width > biggestCut.height)) {
					if (rec.getY() > level.getLevelEntities()[y][x].getY()) {
						this.setY((y+1)*ApoMarioConstants.TILE_SIZE - difY);
						this.setVelocityY(+0.0005f * ApoMarioConstants.SIZE);
						this.yCheckUp(level, x, y);
					}
				// ansonsten untersuche einen Wechsel der X-Richtung
				} else /*if ((biggestCut.height + 2 * ApoMarioConstants.SIZE < biggestCut.width))*/ {
					this.xChange(level, x, y, rec, (int)difX);
				}
			} else {
				this.xChange(level, x, y, rec, (int)difX);
			}
		}
	}
	
	/**
	 * Methode die überprüft, ob die Entity mit der Wand kollidiert und danach wird untersucht abhängig von der X-Richtung,
	 * ob die Entity die abstrakte Methode zum Wechseln der X-Richtugn aufrufen soll
	 * @param level : Das Level-Objekt
	 * @param x : x-Wert
	 * @param y : y-Wert
	 * @param rec : Rechteck der Entity
	 * @param difX : Dif der Enttiy in X-Richtung (da das Rechteck der Entity nicht zwangsläufig auch beim x der Entity beginnt
	 */
	private void xChange(ApoMarioLevel level, int x, int y, Rectangle2D.Float rec, int difX) {
		int myY = (int)((rec.getY() + rec.getHeight() + 1) / (ApoMarioConstants.TILE_SIZE));
		if (myY == y) {
			return;
		}
		if ((this.getVelocityX() < 0) || ((this.getVelocityX() == 0) && (rec.getX() > x * ApoMarioConstants.TILE_SIZE) && (rec.getX() <= (x + 1) * ApoMarioConstants.TILE_SIZE))) {
			if (rec.getX() > x * ApoMarioConstants.TILE_SIZE) {
				this.setX((x + 1) * ApoMarioConstants.TILE_SIZE - difX);
				this.xCheckChange(level, x, y);
			}
		} else if ((this.getVelocityX() > 0) || ((this.getVelocityX() == 0) && (rec.getX() < x * ApoMarioConstants.TILE_SIZE) && (rec.getX() + rec.getWidth() >= x * ApoMarioConstants.TILE_SIZE))) {
			if (rec.getX() < x * ApoMarioConstants.TILE_SIZE) {
				this.setX((x) * ApoMarioConstants.TILE_SIZE - this.getWidth() + difX);
				this.xCheckChange(level, x, y);
			}
		}
	}
	
	protected void makeParticles(float entityX, float entityY, float width, float height) {
		int max = (int)(Math.random() * 6) + 4;
		for (int p = 0; p < max; p++) {
			int x = (int)((Math.random() * width) + entityX - width/2);
			int y = (int)((Math.random() * height) + entityY - height/2);
			int par = (int)(Math.random() * 100);
			ApoMarioParticle particle;
			if (par < 25) {
				particle = new ApoMarioParticle(ApoMarioImageContainer.PARTICLE.getSubimage(0, 2 * ApoMarioConstants.TILE_SIZE/2, 3 * ApoMarioConstants.TILE_SIZE/2, 1 * ApoMarioConstants.TILE_SIZE/2), x, y, ApoMarioConstants.PARTICLE_ANIMATION_FRAMES, ApoMarioConstants.PARTICLE_ANIMATION_TIME);
			} else {
				particle = new ApoMarioParticle(ApoMarioImageContainer.PARTICLE.getSubimage(0, 1 * ApoMarioConstants.TILE_SIZE/2, 3 * ApoMarioConstants.TILE_SIZE/2, 1 * ApoMarioConstants.TILE_SIZE/2), x, y, ApoMarioConstants.PARTICLE_ANIMATION_FRAMES, ApoMarioConstants.PARTICLE_ANIMATION_TIME);		
			}
			if (this.level != null) {
				this.level.addParticle(particle);
			}
		}
	}
	
	public Point getMinMax(ApoMarioLevel level, int add) {
		int minX = (int)(level.getPlayer().getX() - add);
		int maxX = (int)(level.getPlayer().getX() + add);
		return new Point(minX, maxX);
	}
	
	
	
	/**
	 * rendert die Entity, falls er sichtbar ist hin
	 */
	public void render(Graphics2D g, int changeX, int changeY) {
		// Entity wird nur angezeigt, wenn sie auch angezeigt werden kann
		if ( ( super.getX() - changeX + super.getWidth()*2 > 0 ) &&
			 ( super.getX() - changeX - super.getWidth()*4 < ApoMarioConstants.GAME_WIDTH ) &&
			 ( super.getY() - changeY + super.getWidth()*2 > 0 ) &&
			 ( super.getY() - changeY - super.getWidth()*4 < ApoMarioConstants.GAME_HEIGHT ) ) {
			if (super.getIBackground() == null) {
			} else if (super.isBVisible()) {
				this.renderImage(g, changeX, changeY);
			}
		}
	}
	
	public void renderImage(Graphics2D g, int changeX, int changeY) {
		super.render(g, -changeX, -changeY);
		//g.drawImage( super.getIBackground().getSubimage((int)(this.getFrame() * this.getWidth()), (int)(this.getDirection() * this.getHeight()), (int)(this.getWidth()), (int)(this.getHeight())), (int)(this.getX() - changeX), (int)(this.getY() - changeY), null);
	}

}
