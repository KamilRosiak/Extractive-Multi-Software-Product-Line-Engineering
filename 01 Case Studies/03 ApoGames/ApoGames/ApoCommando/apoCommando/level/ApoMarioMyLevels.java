package apoCommando.level;

import apoCommando.ApoMarioConstants;
import apoCommando.entity.ApoMarioEntity;

public class ApoMarioMyLevels {

	public static final String[] LEVEL_STRINGS = new String[] {
		"start", "dark", "jumper", "leftright", "thehole", "duckjump", "enemy", "maze", "cannonrun", "expert", 
		"realdark", "darkenemy"
	};
	
	private ApoMarioLevel level;
	
	public ApoMarioMyLevels(ApoMarioLevel level) {
		this.level = level;
	}
	
	public boolean makeLevel(int level, int difficulty) {
		if (level == 1) {
			this.makeFirstLevel(difficulty, level - 1);
			return true;
		} else if (level == 3) {
			this.makeSecondLevel(difficulty, level - 1);
			return true;
		} else if (level == 4) {
			this.makeThirdLevel(difficulty, level - 1);
			return true;
		} else if (level == 5) {
			this.makeFourthLevel(difficulty, level - 1);
			return true;
		} else if (level == 6) {
			this.makeFifthLevel(difficulty, level - 1);
			return true;
		} else if (level == 7) {
			this.makeSixLevel(difficulty, level - 1);
			return true;
		} else if (level == 8) {
			this.makeSevenLevel(difficulty, level - 1);
			return true;
		} else if (level == 9) {
			this.makeEightLevel(difficulty, level - 1);
			return true;
		} else if (level == 10) {
			this.makeNineLevel(difficulty, level - 1);
			return true;
		} else if (level == 2) {
			this.makeTenLevel(difficulty, level - 1);
			return true;
		} else if (level == 11) {
			this.makeElevenLevel(difficulty, level - 1);
			return true;
		} else if (level == 12) {
			this.makeTwelveLevel(difficulty, level - 1);
			return true;
		}
		return false;
	}

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
	
	public void makeSevenLevel(int difficulty, int level) {
		this.level.setWidth(200);
		this.level.setMaxTime(140000 - (difficulty-1) * 4000);
		if (difficulty == 4) {
			this.level.setMaxTime(100000);	
		}
		if (level + 1 < ApoMarioMyLevels.LEVEL_STRINGS.length) {
			this.level.setNextLevelString(ApoMarioMyLevels.LEVEL_STRINGS[level + 1]);			
		} else {
			this.level.setNextLevelString("random");		
		}
		this.level.setLevelEntities(new ApoMarioEntity[this.level.getHeight()][this.level.getWidth()]);
		int[][] array = new int[][] {
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0}, 
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 0, 3, 3, 3, 0, 3, 3, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 3, 0, 3, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 0, 3, 3, 3, 0, 3, 0, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 3, 0, 3, 0, 0, 0, 3, 0, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 3, 2, 3, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 3, 0, 3, 2, 0, 0, 3, 3, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 3, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 3, 3, 3, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0},
				{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 3, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 3, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 3, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 3, 3, 1, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 3, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 3, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0},
				{1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 3, 3, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 3, 3, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 3, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 3, 3, 3, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 3, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0},
				{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1},
		};
		if (difficulty == 1) {
			array = new int[][] {
					{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0}, 
					{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0},
					{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0},
					{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 0, 3, 3, 3, 0, 3, 3, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0},
					{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0},
					{1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 3, 0, 3, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 0, 3, 3, 3, 0, 3, 0, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0},
					{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 3, 0, 3, 0, 0, 0, 3, 0, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0},
					{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 3, 0, 3, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 3, 0, 3, 0, 0, 0, 3, 3, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0},
					{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 3, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0},
					{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 3, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 3, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0},
					{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 1, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 3, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0},
					{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 3, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0},
					{1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 3, 3, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 3, 3, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 3, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 3, 3, 3, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0},
					{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 3, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0},
					{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1},
				};
		}
		this.makeWall(array, difficulty);		
		
		this.level.makePlayer(1 * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 4) * ApoMarioConstants.TILE_SIZE);
		this.level.makeFinish((this.level.getWidth() - 3) * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 1) * ApoMarioConstants.TILE_SIZE, 7 * ApoMarioConstants.TILE_SIZE);
	}
	
	private void makeWall(int[][] array, int diffiuclty) {
		for (int y = 0; y < array.length; y++) {
			for (int x = 0; x < array[0].length; x++) {
				if (array[y][x] == 1) {
					this.makeRandomWall(x, y);
				} else if (array[y][x] == 2) {
					if (diffiuclty == 4) {
						this.level.makeImmortal(x, y, false, false, true);
					} else {
						this.level.makeGumba(x, y, false, false, true);
					}
				} else if (array[y][x] == 3) {
					this.level.makeBoxCoin(x, y);
				}
			}
		}
	}
	
	private void makeRandomWall(int x, int y) {
		if (Math.random() * 100 > 0) {
			this.level.makeWall(false, true, -1, x, y);
		} else {
			this.level.makeWall(true, false, -1, x, y);
		}
	}
	
	public void makeEightLevel(int difficulty, int level) {
		this.level.setWidth(75);
		this.level.setMaxTime(42000 - (difficulty-1) * 2000);
		if (level + 1 < ApoMarioMyLevels.LEVEL_STRINGS.length) {
			this.level.setNextLevelString(ApoMarioMyLevels.LEVEL_STRINGS[level + 1]);			
		} else {
			this.level.setNextLevelString("random");		
		}
		this.level.setLevelEntities(new ApoMarioEntity[this.level.getHeight()][this.level.getWidth()]);
		this.makeGround(0, this.level.getWidth(), 2);
		this.makeGround(20, 20, 4);
		this.makeGround(40, 10, 6);
		this.makeGround(50, 10, 3);
		if (difficulty == 1) {
			this.makeHole(35, 1);
			this.makeHole(49, 2);
		} else {
			this.makeHole(35, 2);
			this.makeHole(49, 3);
		}
		this.level.makeGroundCorrect();
		
		this.level.makeBoxCoin(10, this.level.getHeight() - 5);
		this.level.makeBoxCoin(11, this.level.getHeight() - 5);
		this.level.makeBoxCoin(12, this.level.getHeight() - 5);
		this.level.makeBoxCoin(25, this.level.getHeight() - 7);
		this.level.makeBoxCoin(26, this.level.getHeight() - 7);
		this.level.makeBoxCoin(50, this.level.getHeight() - 5);
		this.level.makeBoxCoin(68, this.level.getHeight() - 5);
		
		this.level.makeCanon(19, this.level.getHeight() - 4, 2, false, 500);
		this.level.makeCanon(18, this.level.getHeight() - 3, 1, false, 800);
		this.level.makeCanon(39, this.level.getHeight() - 5, 1, false, 2500);
		this.level.makeCanon(65, this.level.getHeight() - 7, 5, false, 2500);
		this.level.makeCanon(55, this.level.getHeight() - 5, 2, false, 2500);
		this.level.makeCanon(45, this.level.getHeight() - 7, 1, false, 2500);
		
		this.level.makeGumba(48, this.level.getHeight() - 1 - 2, false, false, true);
		if (difficulty == 1) {
		} else if (difficulty == 2) {
			this.level.makeGumba(74, this.level.getHeight() - 1 - 2, false, true, true);
			this.level.makeCanon(54, this.level.getHeight() - 4, 1, false, 2200);
		} else if (difficulty == 3) {
			this.level.makeKoopa(false, 74, this.level.getHeight() - 1 - 3, false, false, true);
			this.level.makeCanon(54, this.level.getHeight() - 4, 1, false, 2200);
			this.level.makeCanon(38, this.level.getHeight() - 6, 2, false, 3000);
			this.level.makeCanon(46, this.level.getHeight() - 8, 2, false, 1500);
		} else if (difficulty == 4) {
			this.level.makeKoopa(true, 74, this.level.getHeight() - 1 - 3, false, true, true);
			this.level.makeCanon(54, this.level.getHeight() - 4, 1, false, 2200);
			this.level.makeCanon(38, this.level.getHeight() - 6, 2, false, 1500);
			this.level.makeCanon(64, this.level.getHeight() - 6, 4, false, 700);
			this.level.makeCanon(63, this.level.getHeight() - 5, 3, false, 900);
			this.level.makeCanon(46, this.level.getHeight() - 8, 2, false, 100);
		}
		
		this.level.makePlayer(1 * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 4) * ApoMarioConstants.TILE_SIZE);
		this.level.makeFinish((this.level.getWidth() - 3) * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 2) * ApoMarioConstants.TILE_SIZE, 7 * ApoMarioConstants.TILE_SIZE);
	}
	
	public void makeNineLevel(int difficulty, int level) {
		this.level.setWidth(250);
		this.level.setMaxTime(180000 - (difficulty-1) * 4000);
		if (level + 1 < ApoMarioMyLevels.LEVEL_STRINGS.length) {
			this.level.setNextLevelString(ApoMarioMyLevels.LEVEL_STRINGS[level + 1]);			
		} else {
			this.level.setNextLevelString("random");		
		}
		this.level.setLevelEntities(new ApoMarioEntity[this.level.getHeight()][this.level.getWidth()]);
		this.makeGround(0, this.level.getWidth(), 2);
		this.makeGround(15, 4, 4);
		this.makeGround(19, 20, 6);
		this.makeGround(39, 14, 3);
		this.makeGround(53, 17, 8);
		this.makeGround(70, 15, 5);
		this.makeGround(95, 8, 3);
		this.makeGround(110, 25, 6);
		this.makeGround(135, 20, 4);
		this.makeGround(155, 12, 3);
		this.makeGround(180, 15, 7);
		this.makeGround(195, 10, 5);
		this.makeGround(215, 20, 4);
		if (difficulty == 1) {
			this.makeHole(27, 1);
			this.makeHole(75, 1);
			this.makeHole(120, 1);
			this.makeHole(185, 1);
			this.makeHole(198, 1);
			this.makeHole(220, 1);
		} else {
			this.makeHole(27, 2);
			this.makeHole(75, 2);
			this.makeHole(120, 3);
			this.makeHole(185, 2);
			this.makeHole(195, 3);
			this.makeHole(220, 3);
		}
		this.level.makeGroundCorrect();
		
		this.level.makeBoxCoin(17, this.level.getHeight() - 6);
		this.level.makeBoxCoin(18, this.level.getHeight() - 6);
		this.level.makeBoxCoin(45, this.level.getHeight() - 6);
		this.level.makeBoxCoin(38, this.level.getHeight() - 8);
		this.level.makeBoxCoin(68, this.level.getHeight() - 10);
		this.level.makeBoxCoin(80, this.level.getHeight() - 8);
		this.level.makeBoxCoin(101, this.level.getHeight() - 6);
		this.level.makeBoxCoin(121, this.level.getHeight() - 9);
		this.level.makeBoxCoin(135, this.level.getHeight() - 5);
		this.level.makeBoxCoin(155, this.level.getHeight() - 6);
		this.level.makeBoxCoin(167, this.level.getHeight() - 6);
		this.level.makeBoxCoin(189, this.level.getHeight() - 10);
		this.level.makeBoxCoin(200, this.level.getHeight() - 8);
		this.level.makeBoxCoin(230, this.level.getHeight() - 7);
		
		this.level.makeCanon(111, this.level.getHeight() - 8, 2, false, 2200);
		this.level.makeCanon(228, this.level.getHeight() - 6, 2, false, 2500);
		this.level.makeCanon(229, this.level.getHeight() - 5, 1, false, 1500);
		this.level.makeCanon(45, this.level.getHeight() - 5, 2, false, 3000);
		this.level.makeCanon(160, this.level.getHeight() - 5, 2, false, 1200);
		this.level.makeFlower(22, this.level.getHeight() - 8, 2, false, true, 1800);
		this.level.makeFlower(145, this.level.getHeight() - 6, 2, false, true, 1300);
		this.level.makeFlower(88, this.level.getHeight() - 4, 2, false, true, 1700);
		this.level.makeFlower(175, this.level.getHeight() - 4, 2, false, true, 1900);
		
		this.level.makeGumba(16, this.level.getHeight() - 1 - 4, false, false, false);
		this.level.makeGumba(57, this.level.getHeight() - 1 - 8, false, true, true);
		this.level.makeGumba(136, this.level.getHeight() - 1 - 4, false, false, true);
		this.level.makeGumba(184, this.level.getHeight() - 1 - 2, true, false, true);
		this.level.makeGumba(230, this.level.getHeight() - 1 - 2, false, true, false);
		
		if (difficulty > 1) {
			this.level.makeGumba(35, this.level.getHeight() - 1 - 6, false, false, false);
			this.level.makeGumba(45, this.level.getHeight() - 1 - 3, false, true, false);
			this.level.makeGumba(207, this.level.getHeight() - 1 - 2, false, true, false);
			this.level.makeGumba(236, this.level.getHeight() - 1 - 2, false, false, false);
			this.level.makeKoopa(true,60, this.level.getHeight() - 1 - 9, false, false, true);
			this.level.makeCanon(227, this.level.getHeight() - 5, 1, false, 2300);
			for (int i = 5; i < 10; i++) {
				this.level.makeWall(false, false, -1, i, this.level.getHeight() - 5);
			}
			for (int i = this.level.getHeight() - 5; i < this.level.getHeight() - 2; i++) {
				this.level.makeWall(false, false, -1, 10, i);
			}
			this.level.makeWall(false, false, -1, 5, this.level.getHeight() - 4);
			if (difficulty > 2) {
				this.level.makeCanon(14, this.level.getHeight() - 3, 1, false, 3000);
				this.level.makeCanon(154, this.level.getHeight() - 6, 2, false, 2300);
				this.level.makeKoopa(false, 77, this.level.getHeight() - 1 - 6, false, true, true);
				this.level.makeKoopa(true, 90, this.level.getHeight() - 1 - 6, false, true, true);
				this.level.makeKoopa(false, 110, this.level.getHeight() - 1 - 7, false, true, true);
				if (difficulty > 3) {
					this.level.makeImmortal(130, this.level.getHeight() - 1 - 7, false, false, false);
					this.level.makeImmortal(180, this.level.getHeight() - 1 - 2, false, true, false);
				}
			}
		}
		
		this.level.makePlayer(7 * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 4) * ApoMarioConstants.TILE_SIZE);
		this.level.makeFinish((this.level.getWidth() - 3) * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 2) * ApoMarioConstants.TILE_SIZE, 7 * ApoMarioConstants.TILE_SIZE);
	}
	
	public boolean makeMandyLevel(int difficulty, int level) {
		this.level.setWidth(35);
		this.level.setMaxTime(600000);
		if ((level + 1 < ApoMarioMyLevels.LEVEL_STRINGS.length) && (level >= 0)) {
			this.level.setNextLevelString(ApoMarioMyLevels.LEVEL_STRINGS[level + 1]);			
		} else {
			this.level.setNextLevelString("random");		
		}
		this.level.setLevelEntities(new ApoMarioEntity[this.level.getHeight()][this.level.getWidth()]);
		this.makeGround(0, this.level.getWidth(), 2);
		this.makeGround(30, 2, 5);
		this.level.makeGroundCorrect();
		int[][] array = new int[][] {
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 3, 0, 0, 0, 3, 0, 0, 3, 0, 0, 3, 0, 0, 3, 0, 3, 3, 0, 0, 3, 0, 3, 0, 0, 3, 0, 3, 0, 0, 0, 0, 0, 0, 0},
				{0, 3, 3, 0, 3, 3, 0, 3, 0, 3, 0, 3, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0},
				{0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 3, 0, 3, 0, 3, 0, 3, 3, 3, 0, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0},
				{0, 3, 0, 0, 0, 3, 0, 3, 3, 3, 0, 3, 0, 0, 3, 0, 3, 0, 3, 0, 0, 3, 0, 0, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0},
				{0, 3, 0, 0, 0, 3, 0, 3, 0, 3, 0, 3, 0, 0, 3, 0, 3, 3, 0, 0, 0, 3, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				
		};

		this.makeWall(array, difficulty);
		
		this.level.makePlayer(1 * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 4) * ApoMarioConstants.TILE_SIZE);
		this.level.makeFinish((this.level.getWidth() - 3) * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 2) * ApoMarioConstants.TILE_SIZE, 7 * ApoMarioConstants.TILE_SIZE);
		
		return true;
	}
	
	public boolean makeAntjeLevel(int difficulty, int level) {
		this.level.setWidth(35);
		this.level.setMaxTime(600000);
		if ((level + 1 < ApoMarioMyLevels.LEVEL_STRINGS.length) && (level >= 0)) {
			this.level.setNextLevelString(ApoMarioMyLevels.LEVEL_STRINGS[level + 1]);			
		} else {
			this.level.setNextLevelString("random");		
		}
		this.level.setLevelEntities(new ApoMarioEntity[this.level.getHeight()][this.level.getWidth()]);
		this.makeGround(0, this.level.getWidth(), 2);
		this.makeGround(30, 2, 5);
		this.level.makeGroundCorrect();
		int[][] array = new int[][] {
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0},
				{0, 3, 3, 3, 0, 3, 0, 3, 0, 3, 3, 0, 3, 3, 0, 3, 3, 0, 3, 3, 0, 3, 3, 3, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0, 0},
				{0, 3, 0, 0, 0, 3, 0, 3, 0, 3, 0, 0, 3, 0, 0, 3, 0, 0, 3, 0, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0},
				{0, 3, 0, 0, 0, 3, 3, 3, 0, 3, 3, 0, 3, 3, 0, 3, 3, 0, 3, 3, 0, 3, 3, 3, 0, 3, 0, 3, 3, 3, 0, 0, 0, 0, 0},
				{0, 3, 0, 0, 0, 3, 0, 3, 0, 3, 0, 0, 3, 0, 0, 0, 3, 0, 3, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0},
				{0, 3, 3, 3, 0, 3, 0, 3, 0, 3, 3, 0, 3, 3, 0, 3, 3, 0, 3, 3, 0, 0, 0, 3, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				
		};

		this.makeWall(array, difficulty);
		
		this.level.makePlayer(1 * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 4) * ApoMarioConstants.TILE_SIZE);
		this.level.makeFinish((this.level.getWidth() - 3) * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 2) * ApoMarioConstants.TILE_SIZE, 7 * ApoMarioConstants.TILE_SIZE);
		
		return true;
	}
	
	public boolean makeClemensLevel(int difficulty, int level) {
		this.level.setWidth(35);
		this.level.setMaxTime(600000);
		if ((level + 1 < ApoMarioMyLevels.LEVEL_STRINGS.length) && (level >= 0)) {
			this.level.setNextLevelString(ApoMarioMyLevels.LEVEL_STRINGS[level + 1]);			
		} else {
			this.level.setNextLevelString("random");		
		}
		this.level.setLevelEntities(new ApoMarioEntity[this.level.getHeight()][this.level.getWidth()]);
		this.makeGround(0, this.level.getWidth(), 2);
		this.makeGround(30, 2, 5);
		this.level.makeGroundCorrect();
		int[][] array = new int[][] {
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 0, 3, 0, 0, 3, 3, 3, 0, 0, 3, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 3, 3, 3, 0, 3, 3, 3, 0, 3, 3, 3, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 3, 3, 3, 3, 3, 0, 0, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				
		};

		this.makeWall(array, difficulty);
		
		this.level.makePlayer(1 * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 4) * ApoMarioConstants.TILE_SIZE);
		this.level.makeFinish((this.level.getWidth() - 3) * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 2) * ApoMarioConstants.TILE_SIZE, 7 * ApoMarioConstants.TILE_SIZE);
		
		return true;
	}
	
	public boolean makeJessikaLevel(int difficulty, int level) {
		this.level.setWidth(35);
		this.level.setMaxTime(600000);
		if ((level + 1 < ApoMarioMyLevels.LEVEL_STRINGS.length) && (level >= 0)) {
			this.level.setNextLevelString(ApoMarioMyLevels.LEVEL_STRINGS[level + 1]);			
		} else {
			this.level.setNextLevelString("random");		
		}
		this.level.setLevelEntities(new ApoMarioEntity[this.level.getHeight()][this.level.getWidth()]);
		this.makeGround(0, this.level.getWidth(), 2);
		this.makeGround(31, 1, 5);
		this.level.makeGroundCorrect();
		int[][] array = new int[][] {
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 0, 0, 0},
				{0, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 0, 0, 0},
				{0, 0, 3, 3, 3, 0, 3, 0, 3, 3, 3, 0, 3, 0, 3, 3, 3, 0, 3, 0, 3, 3, 3, 0, 3, 0, 3, 3, 3, 0, 3, 0, 0, 0, 0},
				{0, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 0, 0, 0},
				{0, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				
		};

		this.makeWall(array, difficulty);
		
		this.level.makePlayer(1 * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 4) * ApoMarioConstants.TILE_SIZE);
		this.level.makeFinish((this.level.getWidth() - 3) * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 2) * ApoMarioConstants.TILE_SIZE, 7 * ApoMarioConstants.TILE_SIZE);
		
		return true;
	}
	
	public boolean makeTutorialLevel(int difficulty, int level) {
		this.level.setWidth(35);
		this.level.setMaxTime(18000000);
		if ((level + 1 < ApoMarioMyLevels.LEVEL_STRINGS.length) && (level >= 0)) {
			this.level.setNextLevelString(ApoMarioMyLevels.LEVEL_STRINGS[level + 1]);			
		} else {
			this.level.setNextLevelString("start");		
		}
		this.level.setLevelEntities(new ApoMarioEntity[this.level.getHeight()][this.level.getWidth()]);
		this.makeGround(0, this.level.getWidth(), 2);
		this.makeGround(11, 5, 4);
		
		for (int y = 0; y < 12; y++) {
			this.level.makeWall(false, true, -1, 20, y);
			this.level.makeWall(false, true, -1, 21, y);
		}
		
		this.makeGround(28, 2, 5);
		
		this.level.makeGroundCorrect();
		int[][] array = new int[][] {
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				
		};

		this.makeWall(array, difficulty);
		
		this.level.makePlayer(1 * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 4) * ApoMarioConstants.TILE_SIZE);
		this.level.makeFinish((this.level.getWidth() - 3) * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 2) * ApoMarioConstants.TILE_SIZE, 7 * ApoMarioConstants.TILE_SIZE);
		
		return true;
	}
	
	public void makeTenLevel(int difficulty, int level) {
		this.level.setWidth(50);
		this.level.setMaxTime(19000 - (difficulty-1) * 3000);
		this.level.setBDark(true);
		if (level + 1 < ApoMarioMyLevels.LEVEL_STRINGS.length) {
			this.level.setNextLevelString(ApoMarioMyLevels.LEVEL_STRINGS[level + 1]);			
		} else {
			this.level.setNextLevelString("random");		
		}
		this.level.setLevelEntities(new ApoMarioEntity[this.level.getHeight()][this.level.getWidth()]);
		this.makeGround(0, this.level.getWidth(), 2);
		this.makeGround(15, 3, 4);
		if (difficulty == 1) {
			this.makeHole(22, 1);
			this.makeHole(27, 1);
		} else {
			this.makeHole(27, 2);
			this.makeHole(22, 2);
		}
		this.level.makeBoxCoin(3, this.level.getHeight() - 4);
		this.level.makeBoxCoin(8, this.level.getHeight() - 4);
		this.level.makeBoxCoin(16, this.level.getHeight() - 8);
		this.level.makeBoxCoin(22, this.level.getHeight() - 4);
		this.level.makeBoxCoin(27, this.level.getHeight() - 4);
		this.level.makeBoxCoin(32, this.level.getHeight() - 5);
		this.level.makeBoxCoin(40, this.level.getHeight() - 5);
		
		this.level.makeGumba(40, this.level.getHeight() - 1 - 2, false, false, true);
		if (difficulty > 1) {
			this.level.makeGumba(40, this.level.getHeight() - 1 - 2, false, false, false);
			if (difficulty > 2) {
				this.level.makeGumba(48, this.level.getHeight() - 1 - 2, false, false, true);
			}
		}
		this.level.makeGroundCorrect();
		this.level.makePlayer(1 * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 4) * ApoMarioConstants.TILE_SIZE);
		this.level.makeFinish((this.level.getWidth() - 3) * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 2) * ApoMarioConstants.TILE_SIZE, 7 * ApoMarioConstants.TILE_SIZE);
	}
	
	public void makeElevenLevel(int difficulty, int level) {
		this.level.setWidth(80);
		this.level.setMaxTime(30000 - (difficulty-1) * 3000);
		this.level.setBDark(true);
		if (level + 1 < ApoMarioMyLevels.LEVEL_STRINGS.length) {
			this.level.setNextLevelString(ApoMarioMyLevels.LEVEL_STRINGS[level + 1]);			
		} else {
			this.level.setNextLevelString("random");		
		}
		this.level.setLevelEntities(new ApoMarioEntity[this.level.getHeight()][this.level.getWidth()]);
		this.makeGround(0, this.level.getWidth(), 2);
		this.makeGround(15, 10, 4);
		this.makeGround(25, 15, 3);
		this.makeGround(47, 13, 7);
		this.makeGround(60, 6, 4);
		if (difficulty == 1) {
			this.makeHole(18, 1);
			this.makeHole(22, 1);
			this.makeHole(30, 1);
			this.makeHole(35, 1);
			this.makeHole(43, 2);
			this.makeHole(56, 1);
			this.makeHole(63, 1);
			this.makeHole(75, 1);
		} else {
			this.makeHole(18, 2);
			this.makeHole(22, 2);
			this.makeHole(30, 2);
			this.makeHole(35, 3);
			this.makeHole(43, 3);
			this.makeHole(56, 2);
			this.makeHole(63, 2);
			this.makeHole(75, 2);
		}
		for (int y = 0; y < 7; y++) {
			this.level.makeWall(false, true, -1, 50, y);
			this.level.makeWall(false, true, -1, 51, y);
		}
		
		this.level.makeBoxCoin(8, this.level.getHeight() - 4);
		this.level.makeBoxCoin(19, this.level.getHeight() - 6);
		this.level.makeBoxCoin(29, this.level.getHeight() - 5);
		this.level.makeBoxCoin(39, this.level.getHeight() - 5);
		this.level.makeBoxCoin(49, this.level.getHeight() - 9);
		this.level.makeBoxCoin(57, this.level.getHeight() - 9);
		this.level.makeBoxCoin(70, this.level.getHeight() - 4);
		
		this.level.makeGroundCorrect();
		this.level.makePlayer(1 * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 4) * ApoMarioConstants.TILE_SIZE);
		this.level.makeFinish((this.level.getWidth() - 3) * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 2) * ApoMarioConstants.TILE_SIZE, 7 * ApoMarioConstants.TILE_SIZE);
	}
	
	public void makeTwelveLevel(int difficulty, int level) {
		this.level.setWidth(50);
		this.level.setMaxTime(20000 - (difficulty-1) * 2500);
		this.level.setBDark(true);
		if (level + 1 < ApoMarioMyLevels.LEVEL_STRINGS.length) {
			this.level.setNextLevelString(ApoMarioMyLevels.LEVEL_STRINGS[level + 1]);			
		} else {
			this.level.setNextLevelString("random");		
		}
		this.level.setLevelEntities(new ApoMarioEntity[this.level.getHeight()][this.level.getWidth()]);
		this.makeGround(0, this.level.getWidth(), 2);
		this.makeGround(40, this.level.getWidth(), 4);
		if (difficulty == 1) {
			this.makeHole(31, 2);
		} else {
			this.makeHole(31, 3);
		}
		int changeY = 0;
		if (difficulty > 1) {
			changeY = 1;
		}
		this.level.makeBoxCoin(3, this.level.getHeight() - (4));
		this.level.makeBoxCoin(9, this.level.getHeight() - (4 + changeY));
		this.level.makeBoxCoin(16, this.level.getHeight() - (4 + changeY));
		this.level.makeBoxCoin(23, this.level.getHeight() - (4 + changeY));
		this.level.makeBoxCoin(30, this.level.getHeight() - (4 + changeY));
		this.level.makeBoxCoin(37, this.level.getHeight() - (4 + changeY));
		this.level.makeBoxCoin(43, this.level.getHeight() - (6 + changeY));
		
		this.level.makeGroundCorrect();
		
		this.level.makeGumba(13, this.level.getHeight() - 1 - 2, false, false, true);
		this.level.makeGumba(20, this.level.getHeight() - 1 - 2, false, false, false);
		this.level.makeGumba(41, this.level.getHeight() - 1 - 4, false, false, true);
		if (difficulty > 1) {
			this.level.makeGumba(20, this.level.getHeight() - 1 - 2, false, true, true);
			this.level.makeGumba(34, this.level.getHeight() - 1 - 2, false, false, false);
			this.level.makeCanon(45, this.level.getHeight() - 6, 2, false, 2100);
			this.level.makeCanon(25, this.level.getHeight() - 4, 2, false, 2300);
			if (difficulty > 2) {
				this.level.makeFlower(38, this.level.getHeight() - 4, 2, false, true, 1900);
			}
		}
		
		this.level.makePlayer(1 * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 4) * ApoMarioConstants.TILE_SIZE);
		this.level.makeFinish((this.level.getWidth() - 3) * ApoMarioConstants.TILE_SIZE, (this.level.getHeight() - 2) * ApoMarioConstants.TILE_SIZE, 7 * ApoMarioConstants.TILE_SIZE);
	}
	
	private void makeGround(int startX, int width, int height) {
		for (int x = startX; x < (width + startX) && x < this.level.getWidth(); x++) {
			for (int i = 0; i < height; i++) {
				this.level.makeGroundWall(x, this.level.getHeight() - 1 - i);
			}
		}
	}
	
	private void makeHole(int x, int width) {
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
	
}
