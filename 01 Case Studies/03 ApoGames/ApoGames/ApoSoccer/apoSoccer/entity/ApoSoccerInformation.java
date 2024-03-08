package apoSoccer.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import apoSoccer.ApoSoccerConstants;

/**
 * Klasse, die die Anzeige regelt, wenn einer Spieler nicht zu sehen ist
 * und wenn man dann auf die Maus geht, was passiert
 * @author Dirk Aporius
 *
 */
public class ApoSoccerInformation {

	/** maximale Hoehe des Rechteck */
	private final int MAX_HEIGHT = 20;
	/** minimale Hoehe des Rechteckes */
	private final int HEIGHT = 10;
	/** Breite des Rechteckes */
	private final int WIDTH = 5;
	/** maximale Breit des Rechteckes */
	private final int MAX_WIDTH = 12;
	
	private ApoSoccerEntity entity;
	
	public ApoSoccerInformation(ApoSoccerEntity entity) {
		this.setEntity(entity);
	}

	public ApoSoccerEntity getEntity() {
		return this.entity;
	}

	public void setEntity(ApoSoccerEntity entity) {
		this.entity = entity;
	}

	/**
	 * Methode um zu überprüfen, ob der Mausklick mit dieser Entitaet zusammenhaengt
	 * @param x : X-Wert des Mausklicks
	 * @param y : y-Wert des Mausklicks
	 * @param changeX : Verschiebung des Bildes in x-Richtung
	 * @param changeY : Verschiebung des Bildes in y-Richtung
	 * @return TRUE, wenn auf die Entitaet geklickt wurde, sonst FALSE
	 */
	public boolean isOver(int x, int y, int changeX, int changeY) {
		if ( ( this.getEntity().getX() + changeX + this.getEntity().getWidth()*2 > 0 ) &&
			 ( this.getEntity().getX() + changeX - this.getEntity().getWidth()*2 < ApoSoccerConstants.GAME_FIELD_WIDTH ) &&
			 ( this.getEntity().getY() + changeY + this.getEntity().getWidth()*2 > 0 ) &&
			 ( this.getEntity().getY() + changeY - this.getEntity().getWidth()*2 < ApoSoccerConstants.GAME_FIELD_HEIGHT ) ) {
			
			if (new Rectangle2D.Float(this.getEntity().getX() + changeX - this.getEntity().getWidth() * 2, this.getEntity().getY() + changeY - this.getEntity().getWidth() * 2 - 30, this.getEntity().getWidth() * 4, this.getEntity().getWidth() * 4 + 30).intersects(x, y, 1, 1)) {
				return true;
			}
		} else {
			if (this.getEntity().getX() + changeX + this.getEntity().getWidth() * 2 < 0) {
				// zu weit links
				float max = ApoSoccerConstants.FIELD_WIDTH - ApoSoccerConstants.GAME_FIELD_WIDTH;
				int dif = (int)((1.f / max * (Math.abs(this.getEntity().getX() + changeX + this.getEntity().getWidth() * 2))) * (this.MAX_HEIGHT - this.HEIGHT));

				if (this.getEntity().getY() + changeY + this.getEntity().getWidth()*2 < 0) {
					//zu weit oben
					if (new Rectangle2D.Float(1, 1, this.WIDTH, this.HEIGHT + dif).intersects(x, y, 1, 1)) {
						return true;
					}
				} else if (this.getEntity().getY() + changeY - this.getEntity().getWidth()*2 > ApoSoccerConstants.GAME_FIELD_HEIGHT ) {
					// zu weit unten
					if (new Rectangle2D.Float(1, ApoSoccerConstants.GAME_FIELD_HEIGHT - 1 - this.HEIGHT - dif, this.WIDTH, this.HEIGHT + dif).intersects(x, y, 1, 1)) {
						return true;
					}
				} else {
					if (new Rectangle2D.Float(1, (int)this.getEntity().getY() + changeY - (this.HEIGHT + dif)/2, this.WIDTH, this.HEIGHT + dif).intersects(x, y, 1, 1)) {
						return true;
					}
				}
			} else if (this.getEntity().getX() + changeX - this.getEntity().getWidth()*2 > ApoSoccerConstants.GAME_FIELD_WIDTH) {
				// zu weit rechts
				float max = ApoSoccerConstants.FIELD_WIDTH - ApoSoccerConstants.GAME_FIELD_WIDTH;
				int dif = (int)((1.f / max * (Math.abs(-ApoSoccerConstants.GAME_FIELD_WIDTH + this.getEntity().getX() + changeX + this.getEntity().getWidth() * 2))) * (this.MAX_HEIGHT - this.HEIGHT));

				if (this.getEntity().getY() + changeY + this.getEntity().getWidth()*2 < 0) {
					//zu weit oben
					if (new Rectangle2D.Float(ApoSoccerConstants.GAME_FIELD_WIDTH - this.WIDTH - 1, 1, this.WIDTH, this.HEIGHT + dif).intersects(x, y, 1, 1)) {
						return true;
					}
				} else if (this.getEntity().getY() + changeY - this.getEntity().getWidth()*2 > ApoSoccerConstants.GAME_FIELD_HEIGHT ) {
					// zu weit unten
					if (new Rectangle2D.Float(ApoSoccerConstants.GAME_FIELD_WIDTH - this.WIDTH - 1, ApoSoccerConstants.GAME_FIELD_HEIGHT - 1 - this.HEIGHT - dif, this.WIDTH, this.HEIGHT + dif).intersects(x, y, 1, 1)) {
						return true;
					}
				} else {
					if (new Rectangle2D.Float(ApoSoccerConstants.GAME_FIELD_WIDTH - this.WIDTH - 1, (int)this.getEntity().getY() + changeY - (this.HEIGHT + dif)/2, this.WIDTH, this.HEIGHT + dif).intersects(x, y, 1, 1)) {
						return true;
					}
				}
			} else if (this.getEntity().getY() + changeY + this.getEntity().getWidth()*2 < 0) {
				//zu weit oben
				float max = ApoSoccerConstants.FIELD_HEIGHT - ApoSoccerConstants.GAME_FIELD_HEIGHT;
				int dif = (int)((1.f / max * (Math.abs(this.getEntity().getY() + changeY + this.getEntity().getWidth() * 2))) * (this.MAX_WIDTH - this.WIDTH));

				if (new Rectangle2D.Float((int)this.getEntity().getX() + changeX - (this.HEIGHT + dif)/2, 1, this.HEIGHT + dif, this.WIDTH).intersects(x, y, 1, 1)) {
					return true;
				}
			} else if (this.getEntity().getY() + changeY - this.getEntity().getWidth()*2 > ApoSoccerConstants.GAME_FIELD_HEIGHT) {
				//zu weit oben
				float max = ApoSoccerConstants.FIELD_HEIGHT - ApoSoccerConstants.GAME_FIELD_HEIGHT;
				int dif = (int)((1.f / max * (Math.abs(-ApoSoccerConstants.GAME_FIELD_HEIGHT + this.getEntity().getY() + changeY + this.getEntity().getWidth() * 2))) * (this.MAX_WIDTH - this.WIDTH));

				if (new Rectangle2D.Float((int)this.getEntity().getX() + changeX - (this.HEIGHT + dif)/2, ApoSoccerConstants.GAME_FIELD_HEIGHT - 1 - this.WIDTH, this.HEIGHT + dif, this.WIDTH).intersects(x, y, 1, 1)) {
					return true;
				}
			}

		}
		return false;
	}
	
	/**
	 * zeichnet das Rechteck falls diese Entitaet nicht auf dem aktuellen Bildschirm zu sehen ist
	 * @param g : das Graphics-Object
	 * @param changeX : Verschiebung des Bildschirmes in X-Richtung
	 * @param changeY : Verschiebung des Bildschirmes in Y-Richtung
	 */
	public void render(Graphics2D g, int changeX, int changeY) {
		if ( ( this.getEntity().getX() + changeX + this.getEntity().getWidth()*2 > 0 ) &&
			 ( this.getEntity().getX() + changeX - this.getEntity().getWidth()*2 < ApoSoccerConstants.GAME_FIELD_WIDTH ) &&
			 ( this.getEntity().getY() + changeY + this.getEntity().getWidth()*2 > 0 ) &&
			 ( this.getEntity().getY() + changeY - this.getEntity().getWidth()*2 < ApoSoccerConstants.GAME_FIELD_HEIGHT ) ) {
			//Spieler sichtbar male nichts
		} else {
			//Spieler nicht sichtbar, zeichne Informationen

			// zeichne mit der Farbe für die Spieler
			if (this.getEntity() instanceof ApoPlayer) {
				g.setColor(((ApoPlayer)(this.getEntity())).getColor());
			} else {
				g.setColor(Color.white);
			}
			// male das Rechteck an die richtige Wand
			if (this.getEntity().getX() + changeX + this.getEntity().getWidth() * 2 < 0) {
				// zu weit links
				float max = ApoSoccerConstants.FIELD_WIDTH - ApoSoccerConstants.GAME_FIELD_WIDTH;
				int dif = (int)((1.f / max * (Math.abs(this.getEntity().getX() + changeX + this.getEntity().getWidth() * 2))) * (this.MAX_HEIGHT - this.HEIGHT));
				
				if (this.getEntity().getY() + changeY + this.getEntity().getWidth()*2 < 0) {
					//zu weit oben
					g.fillRect(1, 1, this.WIDTH, this.HEIGHT + dif);
					g.setColor(Color.black);
					g.drawRect(1, 1, this.WIDTH, this.HEIGHT + dif);
				} else if (this.getEntity().getY() + changeY - this.getEntity().getWidth()*2 > ApoSoccerConstants.GAME_FIELD_HEIGHT ) {
					// zu weit unten
					g.fillRect(1, ApoSoccerConstants.GAME_FIELD_HEIGHT - 1 - this.HEIGHT - dif, this.WIDTH, this.HEIGHT + dif);
					g.setColor(Color.black);
					g.drawRect(1, ApoSoccerConstants.GAME_FIELD_HEIGHT - 1 - this.HEIGHT - dif, this.WIDTH, this.HEIGHT + dif);			
				} else {
					g.fillRect(1, (int)this.getEntity().getY() + changeY - (this.HEIGHT + dif)/2, this.WIDTH, this.HEIGHT + dif);
					g.setColor(Color.black);
					g.drawRect(1, (int)this.getEntity().getY() + changeY - (this.HEIGHT + dif)/2, this.WIDTH, this.HEIGHT + dif);
				}
			} else if (this.getEntity().getX() + changeX - this.getEntity().getWidth()*2 > ApoSoccerConstants.GAME_FIELD_WIDTH) {
				float max = ApoSoccerConstants.FIELD_WIDTH - ApoSoccerConstants.GAME_FIELD_WIDTH;
				int dif = (int)((1.f / max * (Math.abs(-ApoSoccerConstants.GAME_FIELD_WIDTH + this.getEntity().getX() + changeX + this.getEntity().getWidth() * 2))) * (this.MAX_HEIGHT - this.HEIGHT));

				// zu weit rechts
				if (this.getEntity().getY() + changeY + this.getEntity().getWidth()*2 < 0) {
					//zu weit oben
					g.fillRect(ApoSoccerConstants.GAME_FIELD_WIDTH - this.WIDTH - 1, 1, this.WIDTH, this.HEIGHT + dif);
					g.setColor(Color.black);
					g.drawRect(ApoSoccerConstants.GAME_FIELD_WIDTH - this.WIDTH - 1, 1, this.WIDTH, this.HEIGHT + dif);
				} else if (this.getEntity().getY() + changeY - this.getEntity().getWidth()*2 > ApoSoccerConstants.GAME_FIELD_HEIGHT ) {
					// zu weit unten
					g.fillRect(ApoSoccerConstants.GAME_FIELD_WIDTH - this.WIDTH - 1, ApoSoccerConstants.GAME_FIELD_HEIGHT - 1 - this.HEIGHT - dif, this.WIDTH, this.HEIGHT + dif);
					g.setColor(Color.black);
					g.drawRect(ApoSoccerConstants.GAME_FIELD_WIDTH - this.WIDTH - 1, ApoSoccerConstants.GAME_FIELD_HEIGHT - 1 - this.HEIGHT - dif, this.WIDTH, this.HEIGHT + dif);			
				} else {
					g.fillRect(ApoSoccerConstants.GAME_FIELD_WIDTH - this.WIDTH - 1, (int)this.getEntity().getY() + changeY - (this.HEIGHT + dif)/2, this.WIDTH, this.HEIGHT + dif);
					g.setColor(Color.black);
					g.drawRect(ApoSoccerConstants.GAME_FIELD_WIDTH - this.WIDTH - 1, (int)this.getEntity().getY() + changeY - (this.HEIGHT + dif)/2, this.WIDTH, this.HEIGHT + dif);
				}
			} else if (this.getEntity().getY() + changeY + this.getEntity().getWidth()*2 < 0) {
				//zu weit oben
				float max = ApoSoccerConstants.FIELD_HEIGHT - ApoSoccerConstants.GAME_FIELD_HEIGHT;
				int dif = (int)((1.f / max * (Math.abs(this.getEntity().getY() + changeY + this.getEntity().getWidth() * 2))) * (this.MAX_WIDTH - this.WIDTH));

				g.fillRect((int)this.getEntity().getX() + changeX - (this.HEIGHT + dif)/2, 1, this.HEIGHT + dif, this.WIDTH);
				g.setColor(Color.black);
				g.drawRect((int)this.getEntity().getX() + changeX - (this.HEIGHT + dif)/2, 1, this.HEIGHT + dif, this.WIDTH);
			} else if (this.getEntity().getY() + changeY - this.getEntity().getWidth()*2 > ApoSoccerConstants.GAME_FIELD_HEIGHT) {
				//zu weit oben
				float max = ApoSoccerConstants.FIELD_HEIGHT - ApoSoccerConstants.GAME_FIELD_HEIGHT;
				int dif = (int)((1.f / max * (Math.abs(-ApoSoccerConstants.GAME_FIELD_HEIGHT + this.getEntity().getY() + changeY + this.getEntity().getWidth() * 2))) * (this.MAX_WIDTH - this.WIDTH));

				g.fillRect((int)this.getEntity().getX() + changeX - (this.HEIGHT + dif)/2, ApoSoccerConstants.GAME_FIELD_HEIGHT - 1 - this.WIDTH, this.HEIGHT + dif, this.WIDTH);
				g.setColor(Color.black);
				g.drawRect((int)this.getEntity().getX() + changeX - (this.HEIGHT + dif)/2, ApoSoccerConstants.GAME_FIELD_HEIGHT - 1 - this.WIDTH, this.HEIGHT + dif, this.WIDTH);
			}
		}
	}
}
