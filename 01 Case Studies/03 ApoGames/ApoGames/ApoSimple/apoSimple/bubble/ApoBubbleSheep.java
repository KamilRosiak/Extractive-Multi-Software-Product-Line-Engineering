package apoSimple.bubble;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import org.apogames.entity.ApoEntity;

import apoSimple.ApoSimpleConstants;
import apoSimple.ApoSimpleImages;

public class ApoBubbleSheep extends ApoEntity {
	
	private int[][] curLevel;
	
	private int curDirection;
	
	private BufferedImage iLeft, iRight, iBubble;
	
	private boolean isInBubble;
	
	private int nextX;
	
	public ApoBubbleSheep(float x, float y, float width, float height) {
		super(null, x, y, width, height);
		
		this.curDirection = ApoSimpleConstants.RIGHT;
		
		BufferedImage iBackground = ApoSimpleImages.ORIGINAL_LEFT;
		this.iLeft = new BufferedImage(iBackground.getWidth()/2, iBackground.getHeight()/2, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = this.iLeft.createGraphics();
		g.drawImage(iBackground.getScaledInstance(iBackground.getWidth()/2, iBackground.getHeight()/2, BufferedImage.SCALE_SMOOTH), 0, 0, null);
		g.dispose();
		
		iBackground = ApoSimpleImages.ORIGINAL_RIGHT;
		this.iRight = new BufferedImage(iBackground.getWidth()/2, iBackground.getHeight()/2, BufferedImage.TYPE_INT_ARGB_PRE);
		g = this.iRight.createGraphics();
		g.drawImage(iBackground.getScaledInstance(iBackground.getWidth()/2, iBackground.getHeight()/2, BufferedImage.SCALE_SMOOTH), 0, 0, null);
		g.dispose();
		
		iBackground = ApoSimpleImages.ORIGINAL_DOWN;
		this.iBubble = new BufferedImage(iBackground.getWidth()/2, iBackground.getHeight()/2, BufferedImage.TYPE_INT_ARGB_PRE);
		g = this.iBubble.createGraphics();
		g.drawImage(iBackground.getScaledInstance(iBackground.getWidth()/2, iBackground.getHeight()/2, BufferedImage.SCALE_SMOOTH), 0, 0, null);
		g.dispose();
	}
	
	public void init() {
		super.init();
		
		this.isInBubble = false;
		this.setVecX(0);
		this.setVecY(0);
		this.nextX = -1;
	}
	
	public void think(ApoBubbleGame game, int delta) {
		if (this.curLevel == null) {
			this.curLevel = game.getCurLevel();
		}
		if (this.isInBubble) {
			return;
		}
		int curX = (int)((this.getX() - 10)/ApoBubbleGame.ENTITY_SIZE);
		int curY = (int)((this.getY() - 10) / ApoBubbleGame.ENTITY_SIZE);
		if (this.getVecX() != 0) {
			this.setX(this.getVecX() * delta + this.getX());
			int newX = (int)((this.getX() - 10)/ApoBubbleGame.ENTITY_SIZE);
			if (this.curDirection == ApoSimpleConstants.LEFT) {				
				newX = (int)((this.getX() + this.getWidth() - 10)/ApoBubbleGame.ENTITY_SIZE);
			}
			if (newX == this.nextX) {
				game.setMoves(game.getMoves() + 1);
				this.setVecX(0);
				this.setX((int)(this.nextX * ApoBubbleGame.ENTITY_SIZE + 10));
				this.checkNextPosition(game, newX, curY);
				if (curY + 1 < game.getCurLevel().length) {
					if ((this.curLevel[curY + 1][newX] == ApoBubbleGame.EMPTY) ||
						(this.curLevel[curY + 1][newX] == ApoBubbleGame.GOAL) ||
						(this.curLevel[curY + 1][newX] == ApoBubbleGame.SPIKE)) {
						this.setVecY(0.2f);
					}
				}
			}
		} else {
			if (this.getVecY() != 0) {
				if (curY + 1 >= game.getCurLevel().length) {
					this.setVecY(0);
					this.setY((int)(curY*ApoBubbleGame.ENTITY_SIZE + 10));
					this.checkNextPosition(game, curX, curY);
				} else {
					this.setVecY(this.getVecY() + 0.0003f * delta);
					this.setY(this.getVecY() * delta + this.getY());
					int nextY = (int)((this.getY() - 10) / ApoBubbleGame.ENTITY_SIZE);
					if (nextY != curY) {
						if ((nextY + 1 >= game.getCurLevel().length) || (this.curLevel[nextY + 1][curX] != ApoBubbleGame.EMPTY)) {
							this.setVecY(0);
							this.setY(10 + nextY * ApoBubbleGame.ENTITY_SIZE);
							this.checkNextPosition(game, curX, nextY);
						}
					}
				}
			} else {
				if (curY + 1 < game.getCurLevel().length) {
					if ((this.curLevel[curY + 1][curX] == ApoBubbleGame.EMPTY) ||
						(this.curLevel[curY + 1][curX] == ApoBubbleGame.GOAL) ||
						(this.curLevel[curY + 1][curX] == ApoBubbleGame.SPIKE)) {
						this.setVecY(0.2f);
					}
				}
			}
		}
	}
	
	public void checkNextPosition(ApoBubbleGame game, int x, int y) {
		if (this.curLevel[y][x] == ApoBubbleGame.SPIKE) {
			// die
			game.die();
		} else if (this.curLevel[y][x] == ApoBubbleGame.GOAL) {
			// win
			game.win();
		}
	}
	
	public boolean isSheepHere(int x, int y) {
		int curX = (int)((this.getX() - 10)/ApoBubbleGame.ENTITY_SIZE);
		int curY = (int)((this.getY() - 10) / ApoBubbleGame.ENTITY_SIZE);
		if ((curX == x) && (curY == y)) {
			return true;
		}
		return false;
	}
	
	public Point getSheepPoint() {
		int curX = (int)((this.getX() - 10)/ApoBubbleGame.ENTITY_SIZE);
		int curY = (int)((this.getY() - 10) / ApoBubbleGame.ENTITY_SIZE);
		return new Point(curX, curY);
	}
	
	public void sheepRun(int addX) {
		if ((!this.isInBubble) && (this.getVecX() == 0) && (this.getVecY() == 0)) {
			int curX = (int)((this.getX() - 10)/ApoBubbleGame.ENTITY_SIZE);
			int curY = (int)((this.getY() - 10)/ApoBubbleGame.ENTITY_SIZE);
			this.nextX = curX + addX;
			if (this.curLevel == null) {
				return;
			}
			if ((this.curLevel[curY][this.nextX] == ApoBubbleGame.EMPTY) ||
				(this.curLevel[curY][this.nextX] == ApoBubbleGame.GOAL) ||
				(this.curLevel[curY][this.nextX] == ApoBubbleGame.SPIKE)) {
				this.setVecX(0.12f);
				this.curDirection = ApoSimpleConstants.RIGHT;
				if (addX < 0) {
					this.setVecX(-this.getVecX());
					this.curDirection = ApoSimpleConstants.LEFT;
				}
			}
		}
	}
	
	public boolean isMoving() {
		if (this.getVecX() != 0) {
			return true;
		}
		if (this.getVecY() != 0) {
			return true;
		}
		return false;
	}
	
	public int getCurDirection() {
		return this.curDirection;
	}

	public void setCurDirection(int curDirection) {
		this.curDirection = curDirection;
	}

	public boolean isInBubble() {
		return this.isInBubble;
	}

	public void setInBubble(boolean isInBubble) {
		this.isInBubble = isInBubble;
	}

	public void render(Graphics2D g, int changeX, int changeY) {
		if (this.isInBubble) {
			g.drawImage(this.iBubble, (int)(this.getX() + changeX), (int)(this.getY() + changeY - 5), null);
		} else if (this.curDirection == ApoSimpleConstants.LEFT) {
			g.drawImage(this.iLeft, (int)(this.getX() + changeX), (int)(this.getY() + changeY), null);
		} else if (this.curDirection == ApoSimpleConstants.RIGHT) {
			g.drawImage(this.iRight, (int)(this.getX() + changeX), (int)(this.getY() + changeY), null);
		}
	}

}
