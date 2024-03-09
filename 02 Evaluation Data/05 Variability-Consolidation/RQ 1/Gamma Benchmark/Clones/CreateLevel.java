
public class CreateLevel {
public void makeFirstLevel(int difficulty, int level) {
		this.level.setWidth(40);
		this.level.setMaxTime(30000 - (difficulty-1) * 4000);
		if (level + 1 < ApoMarioMyLevels.LEVEL_STRINGS.length) {
			this.level.setNextLevelString(ApoMarioMyLevels.LEVEL_STRINGS[level + 1]);			
		} else {
			this.level.setNextLevelString("random");		
		}
		this.level.setLevelEntities(new ApoMarioEntity[this.level.getHeight()][this.level.getWidth()]);
		this.makeGround(0, this.level.getWidth(), 2);
		this.makeGround(15, 4, 4);
		if (difficulty == 1) {
			this.makeHole(27, 1);
		} else {
			this.makeHole(27, 2);
		}
		this.level.makeBoxCoin(7, this.level.getHeight() - 4);
		this.level.makeBoxCoin(8, this.level.getHeight() - 4);
		this.level.makeBoxCoin(17, this.level.getHeight() - 8);
		this.level.makeBoxCoin(27, this.level.getHeight() - 4);
		this.level.makeBoxCoin(32, this.level.getHeight() - 5);
		
		this.level.makeGroundCorrect();
		this.level.makePlayer(1 * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 4) * ApoMarioConstants.TILE_SIZE);
		this.level.makeFinish((this.level.getWidth() - 3) * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 2) * ApoMarioConstants.TILE_SIZE, 7 * ApoMarioConstants.TILE_SIZE);
	}
	
	public void makeSecondLevel(int difficulty, int level) {
		this.level.setWidth(40);
		this.level.setMaxTime(30000 - (difficulty-1) * 5000);
		if (level + 1 < ApoMarioMyLevels.LEVEL_STRINGS.length) {
			this.level.setNextLevelString(ApoMarioMyLevels.LEVEL_STRINGS[level + 1]);			
		} else {
			this.level.setNextLevelString("random");		
		}
		this.level.setLevelEntities(new ApoMarioEntity[this.level.getHeight()][this.level.getWidth()]);
		this.makeGround(0, this.level.getWidth(), 2);
		this.makeGround(15, 4, 5);
		this.makeGround(24, 4, 7);

		this.level.makeBoxCoin(10, this.level.getHeight() - 5);

		this.level.makeBoxCoin(20, this.level.getHeight() - 5);
		this.level.makeBoxCoin(21, this.level.getHeight() - 5);
		this.level.makeBoxCoin(22, this.level.getHeight() - 5);
		
		for (int y = 0; y < 12; y++) {
			this.level.makeWall(false, true, -1, 32, y);
			this.level.makeWall(false, true, -1, 33, y);
		}
		
		this.level.makeGroundCorrect();
		if (difficulty > 2) {
			if (difficulty == 3) {
				this.level.makeGumba(35, this.level.getHeight() - 1 - 2, false, false, true);
			} else {
				this.makeHole(17, 3);
				this.level.makeGroundCorrect();
				this.level.makeGumba(35, this.level.getHeight() - 1 - 2, false, false, true);
				this.level.makeWall(true, true, -1, 23, 8);
				this.level.makeWall(true, true, -1, 22, 8);
			}
		}

		this.level.makePlayer(3 * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 4) * ApoMarioConstants.TILE_SIZE);
		this.level.makeFinish((this.level.getWidth() - 3) * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 2) * ApoMarioConstants.TILE_SIZE, 7 * ApoMarioConstants.TILE_SIZE);
	}
	
	public void makeThirdLevel(int difficulty, int level) {
		this.level.setWidth(30);
		this.level.setMaxTime(30000 - (difficulty-1) * 5000);
		if (level + 1 < ApoMarioMyLevels.LEVEL_STRINGS.length) {
			this.level.setNextLevelString(ApoMarioMyLevels.LEVEL_STRINGS[level + 1]);			
		} else {
			this.level.setNextLevelString("random");		
		}
		this.level.setLevelEntities(new ApoMarioEntity[this.level.getHeight()][this.level.getWidth()]);
		this.makeGround(0, this.level.getWidth(), 2);
		this.makeGround(15, 10, 5);
		
		if (difficulty > 1) {
			this.makeHole(18, 2);
		}
		
		if (difficulty > 2) {
			if (difficulty == 3) {
				this.level.makeGumba(21, this.level.getHeight() - 1 - 5, false, false, true);
			} else {
				this.makeHole(23, 3);
			}
		}
		this.level.makeBoxCoin(1, this.level.getHeight() - 5);
		this.level.makeBoxCoin(10, this.level.getHeight() - 8);
		this.level.makeBoxCoin(11, this.level.getHeight() - 8);
		this.level.makeBoxCoin(25, this.level.getHeight() - 7);

		this.level.makeGroundCorrect();
		
		for (int x = 6; x < 15; x++) {
			this.level.makeWall(false, true, -1, x, this.level.getHeight() - 5);
		}
		this.level.makeWall(false, true, -1, 6, this.level.getHeight() - 4);
		
		this.level.makePlayer(10 * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 4) * ApoMarioConstants.TILE_SIZE);
		this.level.makeFinish((this.level.getWidth() - 3) * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 2) * ApoMarioConstants.TILE_SIZE, 7 * ApoMarioConstants.TILE_SIZE);
	}
	
	public void makeFourthLevel(int difficulty, int level) {
		this.level.setWidth(30);
		this.level.setMaxTime(23000 - (difficulty-1) * 3000);
		if (level + 1 < ApoMarioMyLevels.LEVEL_STRINGS.length) {
			this.level.setNextLevelString(ApoMarioMyLevels.LEVEL_STRINGS[level + 1]);			
		} else {
			this.level.setNextLevelString("random");		
		}
		this.level.setLevelEntities(new ApoMarioEntity[this.level.getHeight()][this.level.getWidth()]);
		this.makeGround(0, this.level.getWidth(), 1);
		this.makeHole(25, 5);
		this.makeHole(16, 2);
		
		if (difficulty > 2) {
			this.makeHole(18, 1);
		}
		for (int x = 0; x < 23; x++) {
			if ((x < 16) || (x > 17)) {
				this.level.makeWall(false, true, -1, x, this.level.getHeight() - 7);
			}
		}
		
		this.level.makeBoxCoin(20, this.level.getHeight() - 9);
		this.level.makeBoxCoin(21, this.level.getHeight() - 9);
		this.level.makeBoxCoin(22, this.level.getHeight() - 9);
		
		this.level.makeBoxCoin(20, this.level.getHeight() - 3);
		this.level.makeBoxCoin(21, this.level.getHeight() - 4);
		this.level.makeBoxCoin(22, this.level.getHeight() - 3);
		
		if (difficulty > 2) {
			this.level.makeGumba(3, this.level.getHeight() - 1 - 2, false, false, false);
			this.level.makeGumba(23, this.level.getHeight() - 1 - 2, false, false, false);
		}

		this.level.makeGroundCorrect();
		
		for (int i = 3; i < 7; i++) {
			this.level.makeWall(false, true, -1, 6, this.level.getHeight() - i);
			this.level.makeWall(false, true, -1, 7, this.level.getHeight() - i);
		}
		
		this.level.makePlayer(3 * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 9) * ApoMarioConstants.TILE_SIZE);
		this.level.makeFinish(0 * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 1) * ApoMarioConstants.TILE_SIZE, 4 * ApoMarioConstants.TILE_SIZE);
	}
	
	public void makeFifthLevel(int difficulty, int level) {
		this.level.setWidth(50);
		this.level.setMaxTime(25000 - (difficulty - 1) * 2000);
		if (level + 1 < ApoMarioMyLevels.LEVEL_STRINGS.length) {
			this.level.setNextLevelString(ApoMarioMyLevels.LEVEL_STRINGS[level + 1]);			
		} else {
			this.level.setNextLevelString("random");		
		}
		this.level.setLevelEntities(new ApoMarioEntity[this.level.getHeight()][this.level.getWidth()]);
		this.makeGround(0, this.level.getWidth(), 2);
		this.makeGround(35, 15, 7);
		if (difficulty == 1) {
			this.makeHole(20, 1);
		} else {
			this.makeHole(20, 2);
			this.makeHole(30, 1);			
		}
		if (difficulty == 1) {
			this.level.makeGumba(16, this.level.getHeight() - 1 - 2, false, false, true);
			this.level.makeGumba(33, this.level.getHeight() - 1 - 2, false, false, true);
		} else if (difficulty == 2) {
			this.level.makeGumba(16, this.level.getHeight() - 1 - 2, false, false, true);
			this.level.makeGumba(25, this.level.getHeight() - 1 - 2, false, false, true);
			this.level.makeGumba(33, this.level.getHeight() - 1 - 2, false, false, true);
		} else if (difficulty == 3) {
			this.level.makeKoopa(true, 16, this.level.getHeight() - 1 - 3, false, false, true);
			this.level.makeGumba(25, this.level.getHeight() - 1 - 2, false, false, true);
			this.level.makeKoopa(false, 33, this.level.getHeight() - 1 - 3, false, false, true);
		} else if (difficulty == 4) {
			this.level.makeImmortal(16, this.level.getHeight() - 1 - 2, false, false, true);
			this.level.makeGumba(25, this.level.getHeight() - 1 - 2, false, false, true);
			this.level.makeKoopa(false, 33, this.level.getHeight() - 1 - 3, false, false, true);
		}
		for (int y = 0; y < 12; y++) {
			this.level.makeWall(false, true, -1, 15, y);
			this.level.makeWall(false, true, -1, 14, y);
		}
		this.level.makeBoxCoin(6, this.level.getHeight() - 4);
		this.level.makeBoxCoin(7, this.level.getHeight() - 4);
		this.level.makeBoxCoin(17, this.level.getHeight() - 4);
		this.level.makeBoxCoin(24, this.level.getHeight() - 5);
		this.level.makeBoxCoin(26, this.level.getHeight() - 5);

		this.level.makeGroundCorrect();
		this.level.makePlayer(1 * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 4) * ApoMarioConstants.TILE_SIZE);
		this.level.makeFinish((this.level.getWidth() - 3) * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 7) * ApoMarioConstants.TILE_SIZE, 5 * ApoMarioConstants.TILE_SIZE);
	}
	
	public void makeSixLevel(int difficulty, int level) {
		this.level.setWidth(70);
		this.level.setMaxTime(42000 - (difficulty-1) * 2000);
		if (level + 1 < ApoMarioMyLevels.LEVEL_STRINGS.length) {
			this.level.setNextLevelString(ApoMarioMyLevels.LEVEL_STRINGS[level + 1]);			
		} else {
			this.level.setNextLevelString("random");		
		}
		this.level.setLevelEntities(new ApoMarioEntity[this.level.getHeight()][this.level.getWidth()]);
		this.makeGround(0, this.level.getWidth(), 2);
		this.makeGround(20, 10, 4);
		this.makeGround(30, 3, 3);
		this.makeGround(33, 8, 8);
		this.makeGround(41, 20, 5);
		if (difficulty == 1) {
			this.makeHole(25, 1);
			this.makeHole(39, 2);
		} else {
			this.makeHole(25, 2);
			this.makeHole(39, 3);	
		}
		this.level.makeBoxCoin(8, this.level.getHeight() - 5);
		this.level.makeBoxCoin(7, this.level.getHeight() - 5);
		this.level.makeBoxCoin(23, this.level.getHeight() - 7);
		this.level.makeBoxCoin(36, this.level.getHeight() - 10);
		this.level.makeBoxCoin(37, this.level.getHeight() - 10);
		this.level.makeBoxCoin(46, this.level.getHeight() - 8);
		this.level.makeBoxCoin(62, this.level.getHeight() - 8);
		this.level.makeGroundCorrect();
		
		this.level.makeCanon(56, this.level.getHeight() - 7, 2, false, 2500);
		this.level.makeFlower(12, this.level.getHeight() - 4, 2, false, true, 1500);

		this.level.makeGumba(68, this.level.getHeight() - 1 - 2, false, false, true);
		this.level.makeGumba(38, this.level.getHeight() - 1 - 8, true, false, true);
		if (difficulty == 1) {
			this.level.makeGumba(21, this.level.getHeight() - 1 - 4, false, false, true);
		} else if (difficulty == 2) {
			this.level.makeGumba(21, this.level.getHeight() - 1 - 4, false, false, true);
			this.level.makeGumba(33, this.level.getHeight() - 1 - 8, false, false, false);
			this.level.makeGumba(65, this.level.getHeight() - 1 - 2, false, true, true);
		} else if (difficulty == 3) {
			this.level.makeKoopa(true, 21, this.level.getHeight() - 1 - 5, false, false, true);
			this.level.makeKoopa(false, 33, this.level.getHeight() - 1 - 9, false, false, false);
			this.level.makeKoopa(true, 65, this.level.getHeight() - 1 - 5, false, true, true);
		} else if (difficulty == 4) {
			this.level.makeKoopa(true, 21, this.level.getHeight() - 1 - 5, false, false, true);
			this.level.makeImmortal(33, this.level.getHeight() - 1 - 9, false, false, false);
			this.level.makeKoopa(true, 65, this.level.getHeight() - 1 - 5, false, true, true);
		}
		
		this.level.makePlayer(1 * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 4) * ApoMarioConstants.TILE_SIZE);
		this.level.makeFinish((this.level.getWidth() - 3) * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 2) * ApoMarioConstants.TILE_SIZE, 7 * ApoMarioConstants.TILE_SIZE);
	}
}

