package apoMario.level;

import java.awt.image.BufferedImage;
import java.util.Random;

import apoMario.ApoMarioConstants;
import apoMario.ApoMarioImageContainer;
import apoMario.entity.ApoMarioCannon;
import apoMario.entity.ApoMarioWall;

/**
 * Klasse, um ein Zufallslevel zu erstellen <br />
 * @author Dirk Aporius
 *
 */
public class ApoMarioRandomLevel {

	private ApoMarioLevel level;
	
	private long lastRandom;
	
	private Random random;
	
	private int difficulty;
	
	private int time;
	
	private int width;
	
	private boolean[] bFull;
	
	public ApoMarioRandomLevel(ApoMarioLevel level) {
		this.level = level;
		
		this.difficulty = 0;
		this.width = 100;
		this.time = 60000;
	}
	
	public long getLastRandom() {
		return this.lastRandom;
	}

	public void setLastRandom(long lastRandom) {
		this.lastRandom = lastRandom;
	}

	public int getWidth() {
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getDifficulty() {
		return this.difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public int getTime() {
		return this.time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public void makeRandomLevel(long lastRandom) {
		if (this.level.getPlayers() != null) {
			//this.level.getPlayers().clear();
		}
		this.lastRandom = lastRandom;
		this.random = new Random(lastRandom);
		
		this.time = 60000 + this.width * 400;
		
//		int endWidth = 7;
		int startX = 7;
		int width = 30;
		int curWidth = width;
		this.makeGround(startX);
		while (startX + curWidth < this.level.getWidth()) {
			int holes = this.random.nextInt((int)(6 + this.difficulty/100f)) - 3;
			if (holes > 3) {
				holes = 3;
			}
			for (int i = 0; i < holes; i++) {
				this.makeHole(startX, curWidth);
			}
			
//			if (startX == this.level.getWidth() - endWidth - startX) {
//				break;
//			} else if (startX + curWidth > this.level.getWidth() - endWidth) {
//				curWidth = this.level.getWidth() - endWidth - startX;
//				startX = curWidth;
//			} else {
				startX += curWidth;
//			}
		}
		this.level.makeGroundCorrect();
		startX = 7;
		curWidth = width;
		while (startX + curWidth < this.level.getWidth()) {
			int objects = 0;
			
			try {
				this.makeBFull();
			} catch (Exception ex) {
			}
			try {
				int increaseWalls = this.random.nextInt(8) - 4;
				for (int i = 0; i < increaseWalls; i++) {
					objects += 1;
					this.makeIncreaseWall(startX, curWidth);
				}
			} catch (Exception ex) {
			}
			try {
				int cannon = this.random.nextInt((int)(1 + this.difficulty/100f));
				if (cannon > 3) {
					cannon = 3;
				}
				for (int i = 0; i < cannon; i++) {
					objects += 1;
					this.makeCannon(startX, curWidth);
				}
			} catch (Exception ex) {
			}
			try {
				int flower = this.random.nextInt((int)(1 + this.difficulty/75f));
				if (flower > 2) {
					flower = 2;
				}
				for (int i = 0; i < flower; i++) {
					objects += 1;
					this.makeFlower(startX, curWidth);
				}
			} catch (Exception ex) {
			}
			try {
				int wallBox = this.random.nextInt((int)(4));
				if (wallBox > 2) {
					wallBox = 2;
				}
				for (int i = 0; i < wallBox; i++) {
					objects += 1;
					this.makeWalls(startX, curWidth);
				}
			} catch (Exception ex) {
			}
			
			try {
				this.makeBFull();
			} catch (Exception ex) {
			}
			try {
				int enemies = this.random.nextInt((int)(1 + this.difficulty/45f)) + 1;
				for (int i = 0; i < enemies; i++) {
					objects += 1;
					this.makeEnemy(startX, curWidth);
				}
			} catch (Exception ex) {
			}
			
			try {
				this.makeBFull();
			} catch (Exception ex) {
			}
			try {
				int coins = this.random.nextInt((int)(10 - this.difficulty/200f)) - 3;
				if (coins > 5) {
					coins = 5;
				}
				for (int i = 0; i < coins; i++) {
					this.makeCoin(startX, curWidth);
				}
			} catch (Exception ex) {
			}
			
//			if (startX == this.level.getWidth() - endWidth - startX) {
//				break;
//			} else if (startX + curWidth > this.level.getWidth() - endWidth) {
//				curWidth = this.level.getWidth() - endWidth - startX;
//				startX = curWidth;
//			} else {
				startX += curWidth;
//			}
		}
		this.level.setPlayerAndFinish();
	}
	
	private void makeBFull() {
		this.bFull = new boolean[this.width];
		for (int i = 0; i < this.width; i++) {
			if (this.isEmpty(i, 1)) {
				this.bFull[i] = true;
			}
		}
	}
	
	private void makeGround(int startX) {
		int curX = startX;
		int curY = this.level.getHeight() - 2;
		while (curX < level.getWidth() - 10) {
			int nextY = this.random.nextInt(4) - 1;
			int width = this.random.nextInt(7) + 3;
			while ((curY + nextY > this.level.getHeight() - 1) 
				|| (curY + nextY < this.level.getHeight() - 4)) {
				nextY = this.random.nextInt(4) - 1;
			}
			curY = nextY + curY;
			for (int x = curX; x < curX + width; x++) {
				for (int y = this.level.getHeight() - 1; y >= curY; y--) {
					this.level.makeGroundWall(x, y);
				}
			}
			curX += width;
		}
	}
	
	private void makeIncreaseWall(int startX, int width) {
		int wallWidth = this.random.nextInt(3) + 2;
		int x = this.random.nextInt(width) + startX;
		int count = 0;
		while ((this.isEmpty(x, wallWidth)) && (count < 15)) {
			x += 1;
			if (x + wallWidth > startX + width) {
				x = startX;
			}
			count++;
		}
		if (count >= 15) {
			return;
		}
		int maxY = this.level.getHeight() - 2;
		int startY = this.level.getHeight() - 1;
		for (int i = x; i < x + wallWidth; i++) {
			for (int y = this.level.getHeight() - 1; y >= 0; y--) {
				if (this.level.getLevelEntities()[y][i] != null) {
					if (y < maxY) {
						maxY = y;
					}
					if (this.level.getLevelEntities()[y][i] instanceof ApoMarioWall) {
						ApoMarioWall wall = (ApoMarioWall)this.level.getLevelEntities()[y][i];
						if ((!wall.isBNoCollision()) && (!wall.isBOnlyAboveWall())) {
							if (y < startY) {
								startY = y;
							}
						}
					}
				}
			}
		}
		int height = -this.random.nextInt(3) - 1 + startY;
		BufferedImage iWall = ApoMarioImageContainer.TILES_LEVEL.getSubimage(4 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 8 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 3 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
		for (int i = x; i < x + wallWidth; i++) {
			for (int y = this.level.getHeight() - 2; y >= height && y >= 0; y--) {
				if (this.level.getLevelEntities()[y][i] != null) {
					ApoMarioWall wall = (ApoMarioWall)this.level.getLevelEntities()[y][i];
					if ((!wall.isBNoCollision()) && (!wall.isBOnlyAboveWall())) {
						continue;
					}
				}
				if (i == x) {
					if (y != height) {
						BufferedImage myWall = iWall.getSubimage(0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
						this.level.makeWall(myWall, false, true, false, false, -1, i, y);
					} else {
						BufferedImage myWall = iWall.getSubimage(0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
						this.level.makeWall(myWall, true, false, false, false, -1, i, y);
					}
				} else if (i == x - 1 + wallWidth) {
					if (y != height) {
						BufferedImage myWall = iWall.getSubimage(2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
						this.level.makeWall(myWall, false, true, false, false, -1, i, y);
					} else {
						BufferedImage myWall = iWall.getSubimage(2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
						this.level.makeWall(myWall, true, false, false, false, -1, i, y);
					}
				} else {
					if (y != height) {
						BufferedImage myWall = iWall.getSubimage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
						this.level.makeWall(myWall, false, true, false, false, -1, i, y);
					} else {
						BufferedImage myWall = iWall.getSubimage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
						this.level.makeWall(myWall, true, false, false, false, -1, i, y);
					}
				}
			}
		}
	}
	
	private boolean isEmpty(int x, int width) {
		for (int i = x; i < x + width; i++) {
			if (this.level.getLevelEntities()[this.level.getHeight()-1][i] == null) {
				return true;
			}
		}		
		for (int i = x; i < x + width; i++) {
			for (int y = this.level.getHeight()-1; y >= 0; y--) {
				if (this.level.getLevelEntities()[y][i] != null) {
					return false;
				}
			}
		}
		return true;
	}
	
	private void makeHole(int startX, int width) {
		int holeWidth = this.random.nextInt(3) + 1;
		int x = this.random.nextInt(width - holeWidth - 2) + startX + 1;
		int count = 0;
		while (this.isFull(x - 2, holeWidth + 4)) {
			x += 1;
			if (x + holeWidth > startX + width) {
				x = startX;
			}
			count++;
			if (count > 30) {
				return;
			}
		}
		this.makeHoleOnly(x, holeWidth);
		if (this.random.nextInt(100) < 50) {
		}
	}
	
	private void makeHoleOnly(int x, int width) {
		for (int i = x; i < x + width; i++) {
			for (int y = this.level.getHeight() - 1; y >= 0; y--) {
				if (this.level.getLevelEntities()[y][i] != null) {
					this.level.getLevelEntities()[y][i] = null;
				} else {
					break;
				}
			}			
		}
	}
	
	private boolean isFull(int x, int width) {
		for (int i = x; i < x + width; i++) {
			for (int y = this.level.getHeight()-2; y >= 0; y--) {
				if (this.level.getLevelEntities()[y][i] != null) {
					if (this.level.getLevelEntities()[y][i] instanceof ApoMarioWall) {
						ApoMarioWall wall = (ApoMarioWall)this.level.getLevelEntities()[y][i];
						if ((wall.isBNoCollision()) || (wall.isBOnlyAboveWall())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	private void makeCannon(int startX, int width) {
		int x = this.random.nextInt(width) + startX;
		int count = 0;
		int startHeight = this.isOneHeight(x, 1);
		while ((startHeight != -1) && (this.isReserved(x, 1))) {
			count += 1;
			x += 1;
			if (x + 1 >= startX + width) {
				x = startX + 1;
			}
			startHeight = this.isOneHeight(x, 1);
			if (count >= 10) {
				return;
			}
		}
		int height = this.random.nextInt(3) + 1;
		int startTime = this.random.nextInt(2000) + 200;
		if (this.canJump(x, height)) {
			this.level.makeCanon(x, startHeight - height, height, ApoMarioConstants.CANNON_SPAWNTIME, startTime);
		}
	}
	
	private void makeFlower(int startX, int width) {
		int x = this.random.nextInt(width - 1) + startX;
		int count = 0;
		int startHeight = this.isOneHeight(x, 2);
		while ((startHeight <= -1) || (this.isReserved(x, 2))) {
			count += 1;
			x += 1;
			if (x + 2 >= startX + width) {
				x = startX;
			}
			startHeight = this.isOneHeight(x, 2);
			if (count >= 10) {
				return;
			}
		}
		boolean bEnemy = true;
		if (this.random.nextInt(100) > 60) {
			bEnemy = false;
		}
		int height = this.random.nextInt(1) + 2;
		int startTime = this.random.nextInt(2000) + 200;
		if (this.canJump(x, height)) {
			this.level.makeFlower(x, startHeight - height, height, ApoMarioConstants.FLOWER_SPAWNTIME, bEnemy, startTime);
		}
	}
	
	private boolean isReserved(int x, int width) {
		for (int i = x; i < x + width; i++) {
			if (this.bFull[i]) {
				return true;
			}
		}
		for (int i = x; i < x + width; i++) {
			this.bFull[i] = true;
		}
		return false;
	}
	
	private boolean canJump(int x, int heightDifference) {
		int curHeight = this.level.getLevelEntities().length - this.getHeight(x);
		int pH = this.getHeight(x - 1);
		if (pH <= -1) {
			return false;
		}
		int prevHeight = this.level.getLevelEntities().length - pH;
		if (curHeight + heightDifference < prevHeight + 4) {
			return true;
		}
		return false;
	}
	
	private int getHeight(int x) {
		if ((x < 0) || (x >= this.width)) {
			return -1;
		}
		for (int y = 0; y < this.level.getHeight(); y++) {
			if ((this.level.getLevelEntities()[y][x] != null) && (this.level.getLevelEntities()[y][x] instanceof ApoMarioWall)) {
				ApoMarioWall wall = (ApoMarioWall)this.level.getLevelEntities()[y][x];
				if ((wall.isBGround()) || (wall.isBNoCollision()) || (wall.isBOnlyAboveWall())) {
					return y;
				}
			}
		}
		return -1;
	}
	
	private int isOneHeight(int x, int width) {
		int height = -1;
		for (int i = x; i < x + width; i++) {
			for (int y = 0; y < this.level.getHeight(); y++) {
				if (this.level.getLevelEntities()[y][i] != null) {
					if (this.level.getLevelEntities()[y][i] instanceof ApoMarioCannon) {
						return -1;
					}
					if (height == -1) {
						height = y;
					} else if (height != y) {
						return -1;
					}
					if (y == 0) {
						return -1;
					}
					break;
				}
			}
		}
		return height;
	}
	
	private void makeWalls(int startX, int startWidth) {
		int width = this.random.nextInt(5) + 1;
		int x = this.random.nextInt(startWidth - width) + startX;
		int count = 0;
		int startHeight = this.canMakeWalls(x, width);
		while ((count < 10) && (startHeight <= -1)) {
			count += 1;
			x += 1;
			if (x + width >= startX + startWidth) {
				x = startX;
			}
			startHeight = this.canMakeWalls(x, width);
		}
		if (count >= 10) {
			return;
		}
		for (int i = 0; i < width; i++) {
			int random = this.random.nextInt(100);
			if (random < 50 - this.difficulty/25) {
				this.makeQuestionmark(x + i, startHeight);
			} else {
				this.makeDestructableWall(x + i, startHeight);
			}
		}
	}
	
	private int canMakeWalls(int x, int width) {
		int maxDif = -1;
		int minDif = -1;
		for (int i = x; i < x + width; i++) {
			for (int y = 0; y < this.level.getHeight(); y++) {
				if (this.level.getLevelEntities()[y][i] != null) {
					if (maxDif == -1) {
						maxDif = y;
					} else if (maxDif < y) {
						maxDif = y;
					}
					if (minDif == -1) {
						minDif = y;
					} else if (minDif > y) {
						minDif = y;
					}
					break;
				}
				if (y == this.level.getHeight() - 1) {
					return -1;
				}
			}
		}
		if (maxDif - minDif <= 1) {
			int returnValue = minDif - 3 - this.random.nextInt(1);
			return returnValue;
		} else {
			return -1;
		}
	}
	
	private void makeDestructableWall(int x, int y) {
		int goodie = -1;
		int random = this.random.nextInt(100);
		if (random < 10) {
			goodie = ApoMarioConstants.GOODIE_GOODIE;
		} else if (random < 30) {
			goodie = ApoMarioConstants.GOODIE_COIN;
		}
		boolean bDestructable = false;
		if (this.random.nextInt(100) < 80) {
			bDestructable = true;
		}
		boolean bNull = true;
		if (goodie >= 0) {
			bNull = false;
		}
		this.level.makeWall(bDestructable, bNull, goodie, x, y);
		
	}
	
	private void makeQuestionmark(int x, int y) {
		int goodie = ApoMarioConstants.GOODIE_COIN;
		if (this.random.nextInt(100) < 40) {
			goodie = ApoMarioConstants.GOODIE_GOODIE;
		}
		this.level.makeBoxQuestionMark(x, y, goodie);
		
	}
	
	private void makeCoin(int startX, int startWidth) {
		int x = this.random.nextInt(startWidth) + startX;
		int count = 0;
		int startHeight = this.isOneHeight(x, 1);
		while ((startHeight <= -1) || this.isReserved(x, 1)) {
			count += 1;
			x += 1;
			if (x >= startX + startWidth) {
				x = startX;
			}
			startHeight = this.canMakeWalls(x, 1);
			if (count >= 10) {
				return;
			}
		}
		if (startHeight - 2 >= 0) {
			this.level.makeBoxCoin(x, startHeight - 2);
		}
	}
	
	private void makeEnemy(int startX, int startWidth) {
		int x = this.random.nextInt(startWidth) + startX;
		int count = 0;
		int startHeight = this.isOneHeight(x, 1);
		while ((startHeight <= -1) || (this.isReserved(x, 1))) {
			count += 1;
			x += 1;
			if (x >= startX + startWidth) {
				x = startX;
			}
			startHeight = this.isOneHeight(x, 1);
			if (count >= 30) {
				return;
			}
		}
		int random = this.random.nextInt(100);
		if (random < 0 + this.difficulty/25f) {
			boolean bFly = false;
			if (this.random.nextInt(100) < this.difficulty/20f) {
				bFly = true;
			}
			boolean bFall = false;
			if (this.random.nextInt(100) < 60) {
				bFall = true;
			}
			boolean bLeft = true;
			if (this.random.nextInt(100) < 15) {
				bLeft = false;
			}
			this.level.makeImmortal(x, startHeight - 1, bFall, bFly, bLeft);
		} else if (random < 20 + this.difficulty/30f) {
			boolean bFly = false;
			if (this.random.nextInt(100) < this.difficulty/30f) {
				bFly = true;
			}
			boolean bFall = false;
			if (this.random.nextInt(100) < 60) {
				bFall = true;
			}
			boolean bLeft = true;
			if (this.random.nextInt(100) < 15) {
				bLeft = false;
			}
			boolean bRed = true;
			if (this.random.nextInt(100) < 50) {
				bRed = false;
			}
			this.level.makeKoopa(bRed, x, startHeight - 2, bFall, bFly, bLeft);
		} else {
			boolean bFly = false;
			if (this.random.nextInt(100) < this.difficulty/30f) {
				bFly = true;
			}
			boolean bFall = false;
			if (this.random.nextInt(100) < 60) {
				bFall = true;
			}
			boolean bLeft = true;
			if (this.random.nextInt(100) < 15) {
				bLeft = false;
			}
			this.level.makeGumba(x, startHeight - 1, bFall, bFly, bLeft);
		}
	}
}
