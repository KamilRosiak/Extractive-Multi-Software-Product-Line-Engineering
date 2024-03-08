package apoSimple.entity;

import apoSimple.ApoSimpleConstants;
import apoSimple.game.ApoSimpleGame;

public class ApoSimplePreview {

	private ApoSimpleGame game;
	
	private int x;
	private int y;
	
	private int size;
	
	public ApoSimplePreview(ApoSimpleGame game) {
		this.game = game;
		
		this.x = -1;
		this.y = -1;
	}
	
	public boolean setMouse(int x, int y) {
		if ((this.x != x) || (this.y != y)) {
			this.x = x;
			this.y = y;
			if ((x < 0) || (y < 0)) {
				return false;
			}
			if (this.game.getEntities()[y][x].getType() != ApoSimpleConstants.EMPTY) {
				this.size = 0;
				return false;
			}
			if ((this.game.getEntities()[y][x].getDirection() == ApoSimpleConstants.REAL_EMPTY) ||
				(this.game.getEntities()[y][x].getDirection() == ApoSimpleConstants.EMPTY) ||
				(this.game.getEntities()[y][x].getDirection() == ApoSimpleConstants.DOG_DOWN) ||
				(this.game.getEntities()[y][x].getDirection() == ApoSimpleConstants.DOG_UP) ||
				(this.game.getEntities()[y][x].getDirection() == ApoSimpleConstants.DOG_LEFT) ||
				(this.game.getEntities()[y][x].getDirection() == ApoSimpleConstants.DOG_RIGHT) ||
				(this.game.getEntities()[y][x].getDirection() == ApoSimpleConstants.STONE_1) ||
				(this.game.getEntities()[y][x].getDirection() == ApoSimpleConstants.STONE_2) ||
				(this.game.getEntities()[y][x].getDirection() == ApoSimpleConstants.STONE_3) ||
				(!this.game.getEntities()[y][x].isBVisible())) {
				this.size = 0;
				return false;
			}
			int[][] directions = new int[this.game.getEntities().length][this.game.getEntities()[0].length];
			for (int myY = 0; myY < directions.length; myY++) {
				for (int myX = 0; myX < directions[0].length; myX++) {
					directions[myY][myX] = this.game.getEntities()[myY][myX].getDirection();
				}
			}
			boolean[][] bVisible = new boolean[this.game.getEntities().length][this.game.getEntities()[0].length];
			for (int myY = 0; myY < bVisible.length; myY++) {
				for (int myX = 0; myX < bVisible[0].length; myX++) {
					if ((this.game.getEntities()[myY][myX].getDirection() != ApoSimpleConstants.REAL_EMPTY) && (this.game.getEntities()[myY][myX].isBVisible())) {
						bVisible[myY][myX] = true;
					}
					this.game.getEntities()[myY][myX].setBCheat(false);
				}
			}
			int driveX = x;
			int driveY = y;
			int vecX = this.getVecX(driveX, driveY, this.getDirection(x, y));
			int vecY = this.getVecY(driveX, driveY, this.getDirection(x, y));
			this.size = 0;
			int step = 0;
			while ((driveX >= 0) && (driveX < this.game.getEntities()[0].length) &&
				   (driveY >= 0) && (driveY < this.game.getEntities().length)) {
				if ((directions[driveY][driveX] >= ApoSimpleConstants.DOG_UP) && (directions[driveY][driveX] <= ApoSimpleConstants.DOG_RIGHT) && (bVisible[driveY][driveX])) {
					if (this.size <= ApoSimpleConstants.MIN_DOG_SIZE) {
						vecX = -vecX;
						vecY = -vecY;
					} else {
						bVisible[driveY][driveX] = false;
						this.size += 1;
						this.game.getEntities()[driveY][driveX].setBCheat(true);
					}
				} else if ((directions[driveY][driveX] >= ApoSimpleConstants.STONE_3) && (directions[driveY][driveX] <= ApoSimpleConstants.STONE_1) && (bVisible[driveY][driveX])) {
					vecX = -vecX;
					vecY = -vecY;

					if (directions[driveY][driveX] == ApoSimpleConstants.STONE_3) {
						directions[driveY][driveX] = ApoSimpleConstants.STONE_2;
					} else if (directions[driveY][driveX] == ApoSimpleConstants.STONE_2) {
						directions[driveY][driveX] = ApoSimpleConstants.STONE_1;
					} else if (directions[driveY][driveX] == ApoSimpleConstants.STONE_1) {
						directions[driveY][driveX] = ApoSimpleConstants.EMPTY;
					}
				} else {
					int direction = directions[driveY][driveX];
					if ((direction >= ApoSimpleConstants.BLACK_UP) && (direction <= ApoSimpleConstants.BLACK_RIGHT) && (bVisible[driveY][driveX])) {
						direction -= 8;
					}
					if ((direction >= ApoSimpleConstants.DOUBLE_BLACK_UP) && (direction <= ApoSimpleConstants.DOUBLE_BLACK_RIGHT) && (bVisible[driveY][driveX])) {
						direction -= 17;
					}
					if ((direction >= ApoSimpleConstants.DOUBLE_UP) && (direction <= ApoSimpleConstants.DOUBLE_RIGHT) && (bVisible[driveY][driveX])) {
						direction -= 13;
					}
					if ((direction != ApoSimpleConstants.EMPTY) && (direction < ApoSimpleConstants.DOG_UP) && (bVisible[driveY][driveX])) {
						vecX = this.getVecX(driveX, driveY, direction);
						vecY = this.getVecY(driveX, driveY, direction);
						this.size += 1;
					}
					if (directions[driveY][driveX] >= ApoSimpleConstants.DOUBLE_UP) {
						if ((directions[driveY][driveX] >= ApoSimpleConstants.DOUBLE_BLACK_UP) && (directions[driveY][driveX] <= ApoSimpleConstants.DOUBLE_BLACK_RIGHT) && (bVisible[driveY][driveX])) {
							directions[driveY][driveX] -= 9;
						}
						if ((directions[driveY][driveX] >= ApoSimpleConstants.DOUBLE_UP) && (directions[driveY][driveX] <= ApoSimpleConstants.DOUBLE_RIGHT) && (bVisible[driveY][driveX])) {
							directions[driveY][driveX] -= 13;
						}
					} else {
						bVisible[driveY][driveX] = false;
					}
					this.game.getEntities()[driveY][driveX].setBCheat(true);
				}
				driveX = driveX + vecX;
				driveY = driveY + vecY;
				step += 1;
				if (step > 200) {
					this.size = 0;
					return false;
				}
			}
			return true;
		}
		return true;
	}
	
	private int getDirection(int x, int y) {
		int direction = this.game.getEntities()[y][x].getDirection();
		if ((direction >= ApoSimpleConstants.BLACK_UP) && (direction <= ApoSimpleConstants.BLACK_RIGHT)) {
			direction -= 8;
		}
		if ((direction >= ApoSimpleConstants.DOUBLE_BLACK_UP) && (direction <= ApoSimpleConstants.DOUBLE_BLACK_RIGHT)) {
			direction -= 17;
		}
		if ((direction >= ApoSimpleConstants.DOUBLE_UP) && (direction <= ApoSimpleConstants.DOUBLE_RIGHT)) {
			direction -= 13;
		}
		return direction;
	}
	
	public int getSize() {
		return this.size;
	}

	public int getVecX(int x, int y, int direction) {
		if ((direction == ApoSimpleConstants.LEFT) || (direction == ApoSimpleConstants.BLACK_LEFT)) {
			return 1;
		} else if ((direction == ApoSimpleConstants.RIGHT) || (direction == ApoSimpleConstants.BLACK_RIGHT)) {
			return -1;
		}
		return 0;
	}
	
	public int getVecY(int x, int y, int direction) {
		if ((direction == ApoSimpleConstants.UP) || (direction == ApoSimpleConstants.BLACK_UP)) {
			return -1;
		} else if ((direction == ApoSimpleConstants.DOWN) || (direction == ApoSimpleConstants.BLACK_DOWN)) {
			return 1;
		}
		return 0;
	}
}
