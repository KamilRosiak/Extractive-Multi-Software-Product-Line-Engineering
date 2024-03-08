package apoRelax.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.apogames.entity.ApoEntity;

import apoRelax.ApoRelaxConstants;

/**
 * Klasse, die ein Teil des Bildes repräsentiert
 * @author Dirk Aporius
 *
 */
public class ApoRelaxTile extends ApoEntity {

	/** Variable die angibt, ob der Spieler sich gerade bewegt oder nicht */
	private boolean isMoving;

	private boolean isChoosen;
	
	private final int number;
	
	private int curNumber;
	
	private float curPositionX,
				  curPositionY,
	  			  curPositionWidth,
	  			  curPositionHeight;
	
	private BufferedImage iNewImage;
	
	private BufferedImage iImage;

	public ApoRelaxTile(BufferedImage pic, float x, float y, float width, float height, int number) {
		super(pic, x, y, width, height);
		
		this.iNewImage = pic;
		this.number = number;
		this.curNumber = this.number;
	}
	
	public void init() {
		super.init();

		this.isMoving = false;
		this.curNumber = this.number;
	}
	
	public BufferedImage getiNewImage() {
		if (this.iImage != null) {
			return this.iImage;
		}
		if (this.iNewImage == null) {
			return this.getIBackground();
		}
		return this.iNewImage;
	}

	public void changeImage(ApoRelaxTile otherTile, boolean bWithMoving) {
		this.changeImage(otherTile, otherTile.getiNewImage(), otherTile.getCurNumber(), bWithMoving);
	}
	
	public void changeImage(ApoRelaxTile otherTile, BufferedImage iNewTile, int curNumber, boolean bWithMoving) {
		this.changeImage(iNewTile, curNumber, (int)(otherTile.getX()), (int)(otherTile.getY()), (int)(otherTile.getWidth()), (int)(otherTile.getHeight()), bWithMoving);
	}
	
	public void changeImage(BufferedImage iNewImage, int curNumber, int oldPositionX, int oldPositionY, int oldWidth, int oldHeight, boolean bWithMoving) {
		if (this.isMoving) {
			return;
		}
		this.curNumber = curNumber;
		this.iNewImage = new BufferedImage(iNewImage.getWidth(), iNewImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		this.setChoosen(false);
		Graphics2D g = this.iNewImage.createGraphics();
		g.drawImage(iNewImage, 0, 0, null);
		g.dispose();
		if (bWithMoving) {
			this.curPositionX = oldPositionX;
			this.curPositionY = oldPositionY;
			this.curPositionWidth = oldWidth;
			this.curPositionHeight = oldHeight;
			this.isMoving = true;
			if ((int)this.curPositionX != (int)this.getX()) {
				this.setVelocityX(ApoRelaxConstants.SPEED);
				if (this.curPositionX > this.getX()) {
					this.setVelocityX(-ApoRelaxConstants.SPEED);
				}
			}
			if ((int)this.curPositionY != (int)this.getY()) {
				this.setVelocityY(ApoRelaxConstants.SPEED);
				if (this.curPositionY > this.getY()) {
					this.setVelocityY(-ApoRelaxConstants.SPEED);
				}
			}
		} else {
			this.iImage = new BufferedImage((int)(this.getWidth()), (int)(this.getHeight()), BufferedImage.TYPE_INT_RGB);
			g = iImage.createGraphics();
			g.drawImage(this.iNewImage.getScaledInstance((int)(this.getWidth()), (int)(this.getHeight()), BufferedImage.SCALE_SMOOTH), 0, 0, null);
			g.dispose();
		}
	}
	
	public boolean isCorrect() {
		if (this.curNumber == this.number) {
			return true;
		}
		return false;
	}

	public int getCurNumber() {
		return this.curNumber;
	}

	public boolean isChoosen() {
		return this.isChoosen;
	}

	public void setChoosen(boolean isChoosen) {
		this.isChoosen = isChoosen;
	}

	public int getNumber() {
		return this.number;
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
	
	public void think(int delta) {
		super.think(delta);
		if (this.isMoving) {
			this.movePlayer(delta);
		} else {
		}
	}
	
	/**
	 * bewegt den Spieler
	 * @param delta : Zeit, die seit dem letzten Aufruf vergangen ist
	 */
	private void movePlayer(int delta) {
		float changeX = this.getVelocityX() * delta;
		float changeY = this.getVelocityY() * delta;
		
		if ((int)(this.getWidth()) != (int)(this.curPositionWidth)) {
			if (this.curPositionWidth < this.getWidth()) {
				this.curPositionWidth += ApoRelaxConstants.SPEED_IMAGE * delta;
				if (this.curPositionWidth >= this.getWidth()) {
					this.curPositionWidth = this.getWidth();
				}
			} else if (this.curPositionWidth > this.getWidth()) {
				this.curPositionWidth -= ApoRelaxConstants.SPEED_IMAGE * delta;
				if (this.curPositionWidth <= this.getWidth()) {
					this.curPositionWidth = this.getWidth();
				}
			}
		}
		
		if ((int)(this.getHeight()) != (int)(this.curPositionHeight)) {
			if (this.curPositionHeight < this.getHeight()) {
				this.curPositionHeight += ApoRelaxConstants.SPEED_IMAGE * delta;
				if (this.curPositionHeight >= this.getHeight()) {
					this.curPositionHeight = this.getHeight();
				}
			} else if (this.curPositionHeight > this.getHeight()) {
				this.curPositionHeight -= ApoRelaxConstants.SPEED_IMAGE * delta;
				if (this.curPositionHeight <= this.getHeight()) {
					this.curPositionHeight = this.getHeight();
				}
			}
		}
		
		this.curPositionX = this.curPositionX + changeX;
		this.curPositionY = this.curPositionY + changeY;
		if ((this.getVelocityX() < 0) && (this.curPositionX <= this.getX())) {
			this.setVelocityX(0);
			this.curPositionX = this.getX();
		} else if ((this.getVelocityX() > 0) && (this.curPositionX >= this.getX())) {
			this.setVelocityX(0);
			this.curPositionX = this.getX();
		}
		
		if ((this.getVelocityY() < 0) && (this.curPositionY <= this.getY())) {
			this.setVelocityY(0);
			this.curPositionY = this.getY();
		} else if ((this.getVelocityY() > 0) && (this.curPositionY >= this.getY())) {
			this.setVelocityY(0);
			this.curPositionY = this.getY();
		}
		
		if ((this.getVelocityX() == 0) && (this.getVelocityY() == 0) &&
			((int)(this.getHeight()) == (int)(this.curPositionHeight)) &&
			((int)(this.getWidth()) == (int)(this.curPositionWidth))) {
			this.isMoving = false;
			this.iImage = new BufferedImage((int)(this.getWidth()), (int)(this.getHeight()), BufferedImage.TYPE_INT_RGB);
			Graphics2D g = iImage.createGraphics();
			g.drawImage(this.iNewImage.getScaledInstance((int)(this.getWidth()), (int)(this.getHeight()), BufferedImage.SCALE_SMOOTH), 0, 0, null);
			g.dispose();
		}
	}
	
	public void render(Graphics2D g, int x, int y) {
		if (super.isBVisible()) {
			if (super.getIBackground() != null) {
				if (this.isMoving) {
					g.setColor(Color.BLACK);
					g.fillRect((int)(x + this.getX()), (int)(y + this.getY()), (int)(this.getWidth()), (int)(this.getHeight()));
				} else {
					if (this.number == this.curNumber) {
						g.drawImage(this.getIBackground(), (int)(this.getX() + x), (int)(this.getY() + y), null);
					} else {
						g.drawImage(this.iImage, (int)(this.getX() + x), (int)(this.getY() + y), null);
					}
					if (this.isChoosen) {
						g.setColor(new Color(255, 255, 255, 100));
						g.fillRect((int)(x + this.getX()), (int)(y + this.getY()), (int)(this.getWidth()), (int)(this.getHeight()));						
					}
				}
			}
		}
	}
	
	public void renderImage(Graphics2D g, int x, int y) {
		g.drawImage(this.iNewImage.getScaledInstance((int)(this.curPositionWidth), (int)(this.curPositionHeight), BufferedImage.SCALE_FAST), (int)(this.curPositionX + x), (int)(this.curPositionY + y), null);
	}

}