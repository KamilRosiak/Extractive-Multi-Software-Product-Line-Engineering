package apoStarz.level;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.apogames.help.ApoInterface;

import apoStarz.ApoStarzConstants;
import apoStarz.ApoStarzImages;
import apoStarz.entity.ApoStarzBlock;
import apoStarz.entity.ApoStarzEntity;
import apoStarz.entity.ApoStarzFire;
import apoStarz.entity.ApoStarzGoal;
import apoStarz.entity.ApoStarzStar;

public class ApoStarzLevel implements ApoInterface {
	
	/** boolean Variable, die angibt, ob sich noch eine Entity bewegt */
	private boolean bFalling;
	/** boolean Variable, die angibt, ob alle Sterne im Ziel sind */
	private boolean bWin;
	/** boolean Variable, die angibt, ob das Level nicht geschafft wurde */
	private boolean bLost;
	
	private int curDirection;
	
	private ApoStarzEntity[][] entities;
	private ApoStarzEntity[][] originalEntities;
	
	private int[][] level;
	
	private ArrayList<ApoStarzEntity> fallingEntities;
	
	private ApoStarzImages images;
	
	private ApoStarzLevelLoad levelLoad;
	
	private BufferedImage iWall, iBlock, iStar, iGoal, iFire;
	
	public ApoStarzLevel(ApoStarzImages images) {
		this.images = images;
		this.init();
	}

	public void init() {
		this.bFalling = false;
		this.bWin = false;
		this.bLost = false;
		
		if ((this.iWall == null) && (this.images != null)) {
			//this.iWall = this.images.getBlock(ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, Color.blue, 255);
			this.iWall = this.images.getImageFromPath("images/block.png", false);
		}
		if ((this.iBlock == null) && (this.images != null)) {
			//this.iBlock = this.images.getBlock(ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, Color.green, 255);
			this.iBlock = this.images.getImageFromPath("images/block_fall.png", false);
		}
		if ((this.iStar == null) && (this.images != null)) {
			//this.iStar = this.images.getStar(ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, Color.yellow, 255);
			this.iStar = this.images.getImageFromPath("images/star.png", false);
		}
		if ((this.iGoal == null) && (this.images != null)) {
			//this.iGoal = this.images.getGoal(ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, Color.white, 255);
			this.iGoal = this.images.getImageFromPath("images/goal.png", false);
		}
		if ((this.iFire == null) && (this.images != null)) {
			//this.iFire = this.images.getFire(ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, Color.red, 255);
			this.iFire = this.images.getImageFromPath("images/fire.png", false);
		}
		
		if (this.levelLoad == null) {
			this.levelLoad = new ApoStarzLevelLoad(this);
			this.levelLoad.loadLevel("ag%.123#'-#5>\"0(");
		}
	}
	
	public ApoStarzLevel getCopy() {
		ApoStarzLevel levelCopy = new ApoStarzLevel(this.images);
		levelCopy.setCopyEntites(this.getLevelInt());
		return levelCopy;
	}
	
	private void setCopyEntites(int[][] level) {
		this.originalEntities = new ApoStarzEntity[level.length][level[0].length];
		for (int i = 0; i < level.length; i++) {
			for (int j = 0; j < level[0].length; j++) {
				if (level[i][j] != ApoStarzConstants.EMPTY) {
					this.addEntity(j, i, level[i][j], false);
				}
			}
		}
		this.makeLevel(this.originalEntities, true);
	}
	
	public void thinkSolver() {
		while (this.bFalling) {
			this.think(ApoStarzConstants.WAIT_TIME);
		}
	}
	
	private int[][] getLevelInt() {
		int[][] level = new int[this.originalEntities.length][this.originalEntities[0].length];
		for (int i = 0; i < level.length; i++) {
			for (int j = 0;  j < level[0].length; j++) {
				if (this.entities[i][j] == null) {
					level[i][j] = ApoStarzConstants.EMPTY;
				} else if (this.entities[i][j] instanceof ApoStarzBlock) {
					if (this.entities[i][j].isBCanFall()) {
						level[i][j] = ApoStarzConstants.BLOCK_FALLING;
					} else {
						level[i][j] = ApoStarzConstants.BLOCK;
					}
				} else if (this.entities[i][j] instanceof ApoStarzStar) {
					level[i][j] = ApoStarzConstants.STAR;
				} else if (this.entities[i][j] instanceof ApoStarzGoal) {
					level[i][j] = ApoStarzConstants.GOAL;
				} else if ((this.entities[i][j] instanceof ApoStarzFire) && (this.entities[i][j].isBVisible())) {
					level[i][j] = ApoStarzConstants.FIRE;
				}
			}
		}
		for (int falling = 0; falling < this.fallingEntities.size(); falling++) {
			if (this.fallingEntities.get(falling).isBVisible()) {
				int x = this.fallingEntities.get(falling).getStarzX();
				int y = this.fallingEntities.get(falling).getStarzY();
				if (this.fallingEntities.get(falling) instanceof ApoStarzBlock) {
					level[y][x] = ApoStarzConstants.BLOCK_FALLING;
				} else if (this.fallingEntities.get(falling) instanceof ApoStarzStar) {
					//System.out.println("x = "+x+" y = "+y+" f = "+falling);
					level[y][x] = ApoStarzConstants.STAR;
				}
			}
		}
		return level;
	}
	
	public boolean isStarAndGoalEqual() {
		int star = 0;
		int goal = 0;
		for (int i = 0; i < this.originalEntities.length; i++) {
			for (int j = 0; j < this.originalEntities[0].length; j++) {
				if ((this.originalEntities[i][j] != null) && 
					(this.originalEntities[i][j] instanceof ApoStarzGoal)) {
					goal++;
				} else if ((this.originalEntities[i][j] != null) && 
					(this.originalEntities[i][j] instanceof ApoStarzStar)) {
					star++;
				}
			}
		}
		if ((star == goal) && (star > 0)) {
			return true;
		} else {
			return false;
		}
	}
	
	public BufferedImage getIWall() {
		return iWall;
	}

	public void setIWall(BufferedImage wall) {
		this.iWall = wall;
	}

	public BufferedImage getIBlock() {
		return iBlock;
	}

	public BufferedImage getIStar() {
		return iStar;
	}

	public BufferedImage getIGoal() {
		return iGoal;
	}

	public BufferedImage getIFire() {
		return iFire;
	}
	
	public ApoStarzEntity[][] getOriginalEntities() {
		return this.originalEntities;
	}

	public void addEntity(int x, int y, int cur, boolean bRestart) {
		if (cur == ApoStarzConstants.BLOCK) {
			if (this.originalEntities[y][x] == null) {
				this.originalEntities[y][x] = new ApoStarzBlock(this.getIWall(), x * ApoStarzConstants.TILE_SIZE, y * ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, 1, false);
			} else if (this.originalEntities[y][x] instanceof ApoStarzBlock) {
				if (this.originalEntities[y][x].isBCanFall()) {
					this.originalEntities[y][x] = new ApoStarzBlock(this.getIWall(), x * ApoStarzConstants.TILE_SIZE, y * ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, 1, false);
				}
			} else {
				this.originalEntities[y][x] = new ApoStarzBlock(this.getIWall(), x * ApoStarzConstants.TILE_SIZE, y * ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, 1, false);
			}
		} else if (cur == ApoStarzConstants.BLOCK_FALLING) {
			if (this.originalEntities[y][x] == null) {
				this.originalEntities[y][x] = new ApoStarzBlock(this.getIBlock(), x * ApoStarzConstants.TILE_SIZE, y * ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, 1, true);
			} else if (this.originalEntities[y][x] instanceof ApoStarzBlock) {
				if (!this.originalEntities[y][x].isBCanFall()) {
					this.originalEntities[y][x] = new ApoStarzBlock(this.getIBlock(), x * ApoStarzConstants.TILE_SIZE, y * ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, 1, true);
				}
			} else {
				this.originalEntities[y][x] = new ApoStarzBlock(this.getIBlock(), x * ApoStarzConstants.TILE_SIZE, y * ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, 1, true);
			}
		} else if (cur == ApoStarzConstants.STAR) {
			if ((this.originalEntities[y][x] == null) || !(this.originalEntities[y][x] instanceof ApoStarzStar)) {
				this.originalEntities[y][x] = new ApoStarzStar(this.getIStar(), x * ApoStarzConstants.TILE_SIZE, y * ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, 1, true);
			}
		} else if (cur == ApoStarzConstants.FIRE) {
			if ((this.originalEntities[y][x] == null) || !(this.originalEntities[y][x] instanceof ApoStarzFire)) {
				this.originalEntities[y][x] = new ApoStarzFire(this.getIFire(), x * ApoStarzConstants.TILE_SIZE, y * ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, 4, false);
			}
		} else if (cur == ApoStarzConstants.GOAL) {
			if ((this.originalEntities[y][x] == null) || !(this.originalEntities[y][x] instanceof ApoStarzGoal)) {
				this.originalEntities[y][x] = new ApoStarzGoal(this.getIGoal(), x * ApoStarzConstants.TILE_SIZE, y * ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, 1, false);
			}
		} else {
			this.originalEntities[y][x] = null;
		}
		if (bRestart) {
			this.restartLevel();
		}
	}

	public void makeNewLevel(int width) {
		this.originalEntities = new ApoStarzEntity[width][width];
		for (int i = 0; i < this.originalEntities.length; i++) {
			this.originalEntities[i][0] = new ApoStarzBlock(this.iWall, 0 * ApoStarzConstants.TILE_SIZE, i * ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, 1, false);
			this.originalEntities[0][i] = new ApoStarzBlock(this.iWall, i * ApoStarzConstants.TILE_SIZE, 0 * ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, 1, false);
			this.originalEntities[i][this.originalEntities[0].length-1] = new ApoStarzBlock(this.iWall, (this.originalEntities[0].length-1) * ApoStarzConstants.TILE_SIZE, i * ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, 1, false);
			this.originalEntities[this.originalEntities.length-1][i] = new ApoStarzBlock(this.iWall, i * ApoStarzConstants.TILE_SIZE, (this.originalEntities.length-1) * ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, ApoStarzConstants.TILE_SIZE, 1, false);
		}
		this.restartLevel();
	}
	
	public void setBFalling(boolean bFalling) {
		this.bFalling = bFalling;
	}
	
	public boolean loadLevel(String levelString) {
		String oldLevelString = this.getLevelString();
		boolean bLoad = this.levelLoad.loadLevel(levelString);
		if (bLoad) {
			this.setBWin(false);
			this.setBLost(false);
		} else {
			this.levelLoad.loadLevel(oldLevelString);
			this.setBWin(false);
			this.setBLost(false);
		}
		return bLoad;
	}
	
	public String getLevelString() {
		return this.levelLoad.getLevelString();
	}
	
	public boolean makeLevel(ApoStarzEntity[][] entities, boolean bSolve) {
		if (!bSolve) {
			this.originalEntities = entities;
		}
		
		this.level = new int[entities.length][entities[0].length];
		for (int i = 0; i < this.level.length; i++) {
			for (int j = 0;  j < this.level[0].length; j++) {
				if (entities[i][j] == null) {
					this.level[i][j] = ApoStarzConstants.EMPTY;
				} else if (entities[i][j] instanceof ApoStarzBlock) {
					if (entities[i][j].isBCanFall()) {
						this.level[i][j] = ApoStarzConstants.BLOCK_FALLING;
					} else {
						this.level[i][j] = ApoStarzConstants.BLOCK;
					}
				} else if (entities[i][j] instanceof ApoStarzStar) {
					this.level[i][j] = ApoStarzConstants.STAR;
				} else if (entities[i][j] instanceof ApoStarzGoal) {
					this.level[i][j] = ApoStarzConstants.GOAL;
				} else if (entities[i][j] instanceof ApoStarzFire) {
					this.level[i][j] = ApoStarzConstants.FIRE;
				}
			}
		}
		
		if (!bSolve) {
			this.setCurDirection(ApoStarzConstants.SOUTH);
		}
		
		return true;
	}

	public ApoStarzEntity[][] getEntities() {
		return this.entities;
	}

	public void setEntities(ApoStarzEntity[][] entities) {
		this.originalEntities = entities;
		this.entities = entities;
	}

	public int[][] getLevel() {
		return this.level;
	}

	public ArrayList<ApoStarzEntity> getFallingEntities() {
		return this.fallingEntities;
	}

	public void setFallingEntities(ArrayList<ApoStarzEntity> fallingEntities) {
		this.fallingEntities = fallingEntities;
	}

	public int getWidth() {
		return this.entities.length;
	}

	public int getHeight() {
		return this.entities[0].length;
	}

	public boolean isBFalling() {
		return this.bFalling;
	}

	public int getCurDirection() {
		return this.curDirection;
	}

	private ApoStarzEntity getCopy(ApoStarzEntity entity) {
		if (entity instanceof ApoStarzBlock) {
			if (entity.isBCanFall()) {
				return new ApoStarzBlock(entity.getIBackground(), entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight(), entity.getTiles(), true);
			} else {
				return new ApoStarzBlock(entity.getIBackground(), entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight(), entity.getTiles(), false);	
			}
		} else if (entity instanceof ApoStarzStar) {
			return new ApoStarzStar(entity.getIBackground(), entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight(), entity.getTiles(), true);
		} else if (entity instanceof ApoStarzGoal) {
			return new ApoStarzGoal(entity.getIBackground(), entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight(), entity.getTiles(), false);
		} else if (entity instanceof ApoStarzFire) {
			return new ApoStarzFire(entity.getIBackground(), entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight(), entity.getTiles(), false);
		}
		return null;
	}
	
	private void addEntity(ArrayList<ApoStarzEntity> entities, ApoStarzEntity entity, int direction) {
		if (entities.size() == 0) {
			entities.add(entity);
			return;
		}
		for (int i = 0; i < entities.size(); i++) {
			if (direction == ApoStarzConstants.NORTH) { //Blöcke fallen nach oben
				if (entity.getY() < entities.get(i).getY()) {
					entities.add(i, entity);
					return;
				}
			} else if (direction == ApoStarzConstants.SOUTH) { //Blöcke fallen nach unten
				if (entity.getY() > entities.get(i).getY()) {
					entities.add(i, entity);
					return;
				}
			} else if (direction == ApoStarzConstants.EAST) { //Blöcke fallen nach rechts
				if (entity.getX() > entities.get(i).getX()) {
					entities.add(i, entity);
					return;
				}
			} else if (direction == ApoStarzConstants.WEST) { //Blöcke fallen nach links
				if (entity.getX() < entities.get(i).getX()) {
					entities.add(i, entity);
					return;
				}
			}
			if (i == entities.size() - 1) {
				entities.add(entity);
				return;
			}
		}
	}
	
	public void restartLevel() {
		this.bFalling = false;
		this.bWin = false;
		this.bLost = false;
		
		this.entities = null;
		this.fallingEntities = null;
		
		this.makeLevel(this.originalEntities, false);
	}
	
	public boolean isLost() {
		for (int i = 0; i < this.fallingEntities.size(); i++) {
			if ( this.fallingEntities.get(i) instanceof ApoStarzStar) {
				if (!this.fallingEntities.get(i).isBVisible()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isWin() {
		for (int i = this.fallingEntities.size() - 1; i >= 0; i--) {
			if ( this.fallingEntities.get(i) instanceof ApoStarzStar) {
				if (this.fallingEntities.get(i).isBVisible()) {
					int x = this.fallingEntities.get(i).getStarzX();
					int y = this.fallingEntities.get(i).getStarzY();
					if (this.level[y][x] != ApoStarzConstants.GOAL) {
						return false;
					}
				} else {
					return false;
				}
			}
		}
		return true;
	}
	
	public void setCurDirection(int curDirection) {
		if (curDirection > ApoStarzConstants.WEST) {
			curDirection = ApoStarzConstants.NORTH;
		} else if (curDirection < ApoStarzConstants.NORTH) {
			curDirection = ApoStarzConstants.WEST;
		}
		if (this.entities == null) {
			this.entities = new ApoStarzEntity[this.originalEntities.length][this.originalEntities[0].length];
			for (int i = 0; i < this.entities.length; i++) {
				for (int j = 0; j < this.entities[0].length; j++) {
					if (this.originalEntities[i][j] != null) {
						this.entities[i][j] = this.getCopy(this.originalEntities[i][j]);
					}
				}
			}
		}
		
		if (this.fallingEntities == null) {
			this.fallingEntities = new ArrayList<ApoStarzEntity>();
			for (int i = 0; i < this.entities.length; i++) {
				for (int j = 0; j < this.entities[0].length; j++) {
					if ((this.entities[i][j] != null) && (this.entities[i][j].isBVisible()) && (this.entities[i][j].isBCanFall())) {
						ApoStarzEntity entity = this.getCopy(this.entities[i][j]);
						this.addEntity(this.fallingEntities, entity, curDirection);
						this.entities[i][j] = null;
					}
				}
			}
		} else {
			ArrayList<ApoStarzEntity> newFallingEntities = new ArrayList<ApoStarzEntity>();
			for (int i = 0; i < this.fallingEntities.size(); i++) {
				this.addEntity(newFallingEntities, this.fallingEntities.get(i), curDirection);
			}
			this.fallingEntities = newFallingEntities;
		}
		
		float vecX = 0;
		float vecY = 0;
		if (curDirection == ApoStarzConstants.NORTH) { //Blöcke fallen nach oben
			vecY = -ApoStarzConstants.VEC;
		} else if (curDirection == ApoStarzConstants.SOUTH) { //Blöcke fallen nach unten
			vecY = ApoStarzConstants.VEC;
		} else if (curDirection == ApoStarzConstants.EAST) { //Blöcke fallen nach rechts
			vecX = ApoStarzConstants.VEC;
		} else if (curDirection == ApoStarzConstants.WEST) { //Blöcke fallen nach links
			vecX = -ApoStarzConstants.VEC;
		}
		//System.out.println(curDirection);
		for (int i = 0; i < this.fallingEntities.size(); i++) {
			if (this.fallingEntities.get(i) != null) {
				if (this.fallingEntities.get(i).isBCanFall()) {
					this.fallingEntities.get(i).setVecX(vecX);
					this.fallingEntities.get(i).setVecY(vecY);
					this.fallingEntities.get(i).setBFalling(true);
				}
			}
		}
		this.curDirection = curDirection;
		this.bFalling = true;
	}

	public void think(int delta) {
		try {
			this.bFalling = false;
			if (this.fallingEntities != null) {
				for (int i = 0; i < this.fallingEntities.size(); i++) {
					if (this.fallingEntities.get(i).isBVisible()) {
						this.fallingEntities.get(i).think(delta, this);
						if (this.fallingEntities.get(i).isBFalling()) {
							this.bFalling = true;
						}
					}
				}
			}
			for (int i = 0; i < this.entities.length; i++) {
				for (int j = 0; j < this.entities[0].length; j++) {
					if ((this.entities[i][j] != null) && (this.entities[i][j].isBVisible())) {
						this.entities[i][j].think(delta);
					}
				}
			}
			if (!this.bFalling) {
				if (this.isWin()) {
					this.setBWin(true);
				} else if (this.isLost()) {
					this.setBLost(true);
				}
			}
		} catch (NullPointerException ex) {
			//System.out.println("null fehler");
			return;
		}
	}
	
	public boolean isBWin() {
		return this.bWin;
	}

	public void setBWin(boolean bWin) {
		this.bWin = bWin;
	}

	public boolean isBLost() {
		return this.bLost;
	}

	public void setBLost(boolean bLost) {
		this.bLost = bLost;
	}

	public void render(Graphics2D g) {
		if (this.entities != null) {
			int curX = (ApoStarzConstants.GAME_GAME_WIDTH - ApoStarzConstants.TILE_SIZE * this.getWidth()) / 2;
			int curY = (ApoStarzConstants.GAME_HEIGHT - ApoStarzConstants.TILE_SIZE * this.getHeight()) / 2;
			if (this.curDirection == ApoStarzConstants.NORTH) {
				int changeX = 0;
				int changeY = (this.getHeight() - 1) * (ApoStarzConstants.TILE_SIZE);
				
				changeX = 0;
				changeY = (this.getHeight() - 1) * (ApoStarzConstants.TILE_SIZE);
				for (int i = 0; i < this.entities.length; i++) {
					changeX = (this.getWidth() - 1) * ApoStarzConstants.TILE_SIZE;
					for (int j = 0; j < this.entities[0].length; j++) {
						if ((this.entities[i][j] != null) && (this.entities[i][j].isBVisible())) {
							this.entities[i][j].render(g, changeX + curX, changeY + curY);
						}
						//g.drawSting(""+this.level[i][j], changeX + curX + j*ApoStarzConstants.TILE_SIZE, 15 + changeY + curY + i*ApoStarzConstants.TILE_SIZE);
						changeX -= 2 * ApoStarzConstants.TILE_SIZE;
					}
					changeY -= 2 * ApoStarzConstants.TILE_SIZE;
				}
				for (int i = 0; i < this.fallingEntities.size(); i++) {
					changeX = (this.getWidth() - 1) * ApoStarzConstants.TILE_SIZE - (int)(2 * this.fallingEntities.get(i).getX());
					changeY = (this.getHeight() - 1) * ApoStarzConstants.TILE_SIZE - (int)(2 * this.fallingEntities.get(i).getY());
					if (this.fallingEntities.get(i).isBVisible()) {
						this.fallingEntities.get(i).render(g, changeX + curX, changeY + curY);
						//int x = this.fallingEntities.get(i).getStarzX();
						//int y = this.fallingEntities.get(i).getStarzY();
						//g.drawSting(""+this.level[y][x], changeX + curX + x*ApoStarzConstants.TILE_SIZE, 15 + changeY + curY + y*ApoStarzConstants.TILE_SIZE);
					
					}
				}
			} else if (this.curDirection == ApoStarzConstants.SOUTH) {
				int changeX = 0;
				int changeY = 0;
				
				for (int i = 0; i < this.entities.length; i++) {
					for (int j = 0; j < this.entities[0].length; j++) {
						if ((this.entities[i][j] != null) && (this.entities[i][j].isBVisible())) {
							this.entities[i][j].render(g, changeX + curX, changeY + curY);
						}
						//g.drawSting(""+this.level[i][j], changeX + curX + j*ApoStarzConstants.TILE_SIZE, 15 + changeY + curY + i*ApoStarzConstants.TILE_SIZE);
					}
				}
				for (int i = 0; i < this.fallingEntities.size(); i++) {
					changeX = 0;
					changeY = 0;
					if (this.fallingEntities.get(i).isBVisible()) {
						this.fallingEntities.get(i).render(g, changeX + curX, changeY + curY);
						//int x = this.fallingEntities.get(i).getStarzX();
						//int y = this.fallingEntities.get(i).getStarzY();
						//g.drawSting(""+this.level[y][x], changeX + curX + x*ApoStarzConstants.TILE_SIZE, 15 + changeY + curY + y*ApoStarzConstants.TILE_SIZE);
					
					}
				}
			} else if (this.curDirection == ApoStarzConstants.EAST) {
				int changeX = (this.getWidth() - 1) * (ApoStarzConstants.TILE_SIZE);
				int changeY = 0;
				
				changeX = (this.getWidth() - 1) * (ApoStarzConstants.TILE_SIZE);
				changeY = 0;
				for (int i = 0; i < this.entities.length; i++) {
					changeY = -i * (ApoStarzConstants.TILE_SIZE);
					for (int j = 0; j < this.entities[0].length; j++) {
						changeX = (this.getWidth() - 1 - j) * (ApoStarzConstants.TILE_SIZE) - (i * (ApoStarzConstants.TILE_SIZE));
						if ((this.entities[i][j] != null) && (this.entities[i][j].isBVisible())) {
							this.entities[i][j].render(g, changeX + curX, changeY + curY);
						}
						//g.drawSting(""+this.level[i][j], changeX + curX + j*ApoStarzConstants.TILE_SIZE, 15 + changeY + curY + i*ApoStarzConstants.TILE_SIZE);
						changeY += ApoStarzConstants.TILE_SIZE;
					}
				}
				for (int i = 0; i < this.fallingEntities.size(); i++) {
					changeX = (this.getWidth() - 1) * ApoStarzConstants.TILE_SIZE -  (int)this.fallingEntities.get(i).getX() - (int)this.fallingEntities.get(i).getY();
					changeY = (int)((this.fallingEntities.get(i).getX()) - 1 * this.fallingEntities.get(i).getY());
					if (this.fallingEntities.get(i).isBVisible()) {
						this.fallingEntities.get(i).render(g, changeX + curX, changeY + curY);
						//int x = this.fallingEntities.get(i).getStarzX();
						//int y = this.fallingEntities.get(i).getStarzY();
						//g.drawSting(""+this.level[y][x], changeX + curX + x*ApoStarzConstants.TILE_SIZE, 15 + changeY + curY + y*ApoStarzConstants.TILE_SIZE);
					}
				}
			} else if (this.curDirection == ApoStarzConstants.WEST) {
				int changeX = 0;
				int changeY = 0;
				
				changeX = 0;
				changeY = 0;
				for (int i = 0; i < this.entities.length; i++) {
					changeY = (this.getHeight() - 1 - i) * ApoStarzConstants.TILE_SIZE;
					changeX = (i * (ApoStarzConstants.TILE_SIZE));
					for (int j = 0; j < this.entities[0].length; j++) {
						if ((this.entities[i][j] != null) && (this.entities[i][j].isBVisible())) {
							this.entities[i][j].render(g, changeX + curX, changeY + curY);
						}
						//g.drawSting(""+this.level[i][j], changeX + curX + j*ApoStarzConstants.TILE_SIZE, 15 + changeY + curY + i*ApoStarzConstants.TILE_SIZE);
						changeX -= ApoStarzConstants.TILE_SIZE;
						changeY -= ApoStarzConstants.TILE_SIZE;
					}
				}
				for (int i = 0; i < this.fallingEntities.size(); i++) {
					changeX = (int)(this.fallingEntities.get(i).getY() - this.fallingEntities.get(i).getX());
					changeY = (this.getHeight() - 1) * ApoStarzConstants.TILE_SIZE - (int)(this.fallingEntities.get(i).getX() + this.fallingEntities.get(i).getY());
					if (this.fallingEntities.get(i).isBVisible()) {
						this.fallingEntities.get(i).render(g, changeX + curX, changeY + curY);
						//int x = this.fallingEntities.get(i).getStarzX();
						//int y = this.fallingEntities.get(i).getStarzY();
						//g.drawSting(""+this.level[y][x], changeX + curX + x*ApoStarzConstants.TILE_SIZE, 15 + changeY + curY + y*ApoStarzConstants.TILE_SIZE);
					}
				}
			}
		}
	}
	
}
