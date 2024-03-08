package apoSlitherLink.level;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import apoSlitherLink.ApoSlitherLinkConstants;
import apoSlitherLink.game.ApoSlitherLinkPanel;

public class ApoSlitherLinkLevel {

	private final int EASY = 0;
	private final int MEDIUM = 1;
	private final int HARD = 2;
	private final int CUSTOM = 3;
	
	private ApoSlitherLinkPanel model;
	
	private ApoSlitherLinkLevelHelp[][] level;
	
	private ApoSlitherLinkLevels levels;
	
	private int curLevel;
	
	private int value;
	
	private BufferedImage iLevel;
	
	private BufferedImage iLevelOver;

	private ApoSlitherLinkBlinkLine blinkLine;
	
	private String curString;
	
	private ApoSlitherLinkLine[][] points;
	
	private boolean bPoints;
	
	public ApoSlitherLinkLevel(ApoSlitherLinkPanel model) {
		this.model = model;
		
		this.init();
	}
	
	public void init() {
		if (this.levels == null) {
			this.levels = new ApoSlitherLinkLevels();
		}
	}
	
	public boolean isBPoints() {
		return this.bPoints;
	}

	public void setBPoints(boolean points) {
		this.bPoints = points;
	}

	public ApoSlitherLinkPanel getModel() {
		return this.model;
	}

	public ApoSlitherLinkLevels getLevels() {
		return this.levels;
	}

	public int getCurLevel() {
		return this.curLevel;
	}

	public boolean isCurLevelSolved() {
		return this.isCurLevelSolved(this.curLevel);
	}

	public ApoSlitherLinkLevelsHelp getCurrentLevel() {
		return this.getCurrentLevel(this.curLevel);
	}
	
	public ApoSlitherLinkLevelsHelp getCurrentLevel(int curLevel) {
		if (this.value == this.EASY) {
			if ((curLevel >= 0) && (curLevel < this.levels.getEasyLevels().size())) {
				return this.levels.getEasyLevels().get(curLevel);
			}
		} else if (this.value == this.MEDIUM) {
			if ((curLevel >= 0) && (curLevel < this.levels.getMediumLevels().size())) {
				return this.levels.getMediumLevels().get(curLevel);
			}
		} else if (this.value == this.HARD) {
			if ((curLevel >= 0) && (curLevel < this.levels.getHardLevels().size())) {
				return this.levels.getHardLevels().get(curLevel);
			}
		} else if (this.value == this.CUSTOM) {
			if ((curLevel >= 0) && (curLevel < this.levels.getCustomLevels().size())) {
				return this.levels.getCustomLevels().get(curLevel);
			}
		}
		return null;
	}
	
	public boolean isCurLevelSolved(int curLevel) {
		ApoSlitherLinkLevelsHelp level = this.getCurrentLevel(curLevel);
		if (level == null) {
			return false;
		} else {
			return level.isBSolved();
		}
	}

	public boolean isCorrectLevel() {
		for (int y = 0; y < this.level.length; y++) {
			for (int x = 0; x < this.level[0].length; x++) {
				if (this.level[y][x].getValue() >= 0) {
					return true;
				}
			}
		}
		return false;
	}
	
	public BufferedImage getCurLevelSolvedImage() {
		return this.getCurLevelSolvedImage(this.curLevel);
	}
	
	public BufferedImage getCurLevelSolvedImage(int curLevel) {
		ApoSlitherLinkLevelsHelp level = this.getCurrentLevel(curLevel);
		if (level == null) {
			return null;
		} else {
			return level.getISolved();
		}
	}
	
	public boolean isEasyLevel() {
		if (this.value == this.EASY) {
			return true;
		}
		return false;
	}
	
	public boolean isMediumLevel() {
		if (this.value == this.MEDIUM) {
			return true;
		}
		return false;
	}
	
	public boolean isHardLevel() {
		if (this.value == this.HARD) {
			return true;
		}
		return false;
	}
	
	public int getMaxLevel() {
		if (this.value == this.EASY) {
			return this.levels.getEasyLevels().size();
		} else if (this.value == this.MEDIUM) {
			return this.levels.getMediumLevels().size();
		} else if (this.value == this.HARD) {
			return this.levels.getHardLevels().size();
		} else if (this.value == this.CUSTOM) {
			return this.levels.getCustomLevels().size();
		}
		return -1;
	}
	
	public void nextLevel(boolean bSolved) {
		this.nextLevel(bSolved, +1);
	}

	public void setLevel(int level) {
		this.nextLevel(false, level - this.curLevel);
	}
	
	public void nextLevel(boolean bSolved, int add) {
		if (this.value == this.EASY) {
			if (bSolved) {
				this.levels.getEasyLevels().get(this.curLevel).setBSolved(bSolved);
			}
			this.setEasyLevels(this.curLevel + add);
		} else if (this.value == this.MEDIUM) {
			if (bSolved) {
				this.levels.getMediumLevels().get(this.curLevel).setBSolved(bSolved);
			}
			this.setMediumLevels(this.curLevel + add);
		} else if (this.value == this.HARD) {
			if (bSolved) {
				this.levels.getHardLevels().get(this.curLevel).setBSolved(bSolved);
			}
			this.setHardLevels(this.curLevel + add);
		} else if (this.value == this.CUSTOM) {
			if (bSolved) {
				this.levels.getCustomLevels().get(this.curLevel).setBSolved(bSolved);
			}
			this.setCustomLevels(this.curLevel + add);
		}
	}

	public void setEasyLevels(int curLevel) {
		if ((this.value != this.EASY) && (curLevel != 0)) {
			curLevel = 0;
		}
		this.value = this.EASY;
		int add = curLevel - this.curLevel;
		this.curLevel = curLevel;
		ApoSlitherLinkLevelsHelp help = this.levels.getEasyLevel(this.curLevel);
		if (help != null) {
			this.setLevel(this.levels.getEasyLevel(this.curLevel).getLevelString(), true);
		} else {
			if (add < 0) {
				this.setEasyLevels(this.levels.getEasyLevels().size() - 1);
			} else {
				this.setEasyLevels(0);
			}
		}
		this.makeLevelImage();
	}
	
	public int getMaxEasyLevel() {
		return this.levels.getEasyLevels().size();
	}
	
	public int getSolvedEasyLevel() {
		return this.levels.getEasyLevelsSolved();
	}
	
	public void setMediumLevels(int curLevel) {
		if ((this.value != this.MEDIUM) && (curLevel != 0)) {
			curLevel = 0;
		}
		this.value = this.MEDIUM;
		int add = curLevel - this.curLevel;
		this.curLevel = curLevel;
		ApoSlitherLinkLevelsHelp help = this.levels.getMediumLevel(this.curLevel);
		if (help != null) {
			this.setLevel(this.levels.getMediumLevel(this.curLevel).getLevelString(), true);
		} else {
			if (add < 0) {
				this.setMediumLevels(this.levels.getMediumLevels().size() - 1);
			} else {
				this.setMediumLevels(0);
			}
		}
		this.makeLevelImage();
	}
	
	public int getMaxMediumLevel() {
		return this.levels.getMediumLevels().size();
	}
	
	public int getSolvedMediumLevel() {
		return this.levels.getMediumLevelsSolved();
	}
	
	public void setHardLevels(int curLevel) {
		if ((this.value != this.HARD) && (curLevel != 0)) {
			curLevel = 0;
		}
		this.value = this.HARD;
		int add = curLevel - this.curLevel;
		this.curLevel = curLevel;
		ApoSlitherLinkLevelsHelp help = this.levels.getHardLevel(this.curLevel);
		if (help != null) {
			this.setLevel(this.levels.getHardLevel(this.curLevel).getLevelString(), true);
		} else {
			if (add < 0) {
				this.setHardLevels(this.levels.getHardLevels().size() - 1);
			} else {
				this.setHardLevels(0);
			}
		}
		this.makeLevelImage();
	}
	
	public int getMaxHardLevel() {
		return this.levels.getHardLevels().size();
	}
	
	public int getSolvedHardLevel() {
		return this.levels.getHardLevelsSolved();
	}

	public void setCustomLevel() {
		this.value = this.CUSTOM;		
	}
	
	public void addCustomLevels() {
		this.value = this.CUSTOM;
		this.levels.addCustomLevel(this.curString);
		this.curLevel = this.levels.getCustomLevels().size() - 1;
	}
	
	public void setCustomLevels(int curLevel) {
		if ((this.value != this.CUSTOM) && (curLevel != 0)) {
			curLevel = 0;
		}
		this.curLevel = curLevel;
		this.value = this.CUSTOM;
		if (this.getMaxCustomLevel() <= 0) {
			return;
		}
		int add = curLevel - this.curLevel;
		ApoSlitherLinkLevelsHelp help = this.levels.getCustomLevel(this.curLevel);
		if (help != null) {
			this.setLevel(this.levels.getCustomLevel(this.curLevel).getLevelString(), true);
		} else {
			if (add < 0) {
				this.setCustomLevels(this.levels.getCustomLevels().size() - 1);
			} else {
				this.setCustomLevels(0);
			}
		}
		this.makeLevelImage();
	}
	
	public int getMaxCustomLevel() {
		return this.levels.getCustomLevels().size();
	}
	
	public int getSolvedCustomLevel() {
		return this.levels.getCustomLevelsSolved();
	}
	
	public void retryLevel() {
		for (int x = 0; x < this.level[0].length; x++) {
			for (int y = 0; y < this.level.length; y++) {
				this.level[y][x].init();
			}
		}
		this.blinkLine = null;
	}
	
	public void makeLevelImage() {
		this.iLevel = new BufferedImage(ApoSlitherLinkConstants.GAME_WIDTH, ApoSlitherLinkConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D)(this.iLevel.getGraphics());
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.model.renderBackground(g);
		
		int changeX = this.model.getGame().getChangeX();
		int changeY = this.model.getGame().getChangeY();
		int width = this.level[0][0].getWidth();
		int roundWidth = this.level[0].length * width + 20;
		int roundHeight = this.level.length * width + 20;
		g.setColor(ApoSlitherLinkLevelHelp.COLOR_BACKGROUND);
		g.fillRoundRect(changeX - 10, changeY - 10, roundWidth - 1, roundHeight - 1, 30, 30);
		g.setColor(ApoSlitherLinkLevelHelp.COLOR_LINE);
		g.drawRoundRect(changeX - 10, changeY - 10, roundWidth - 1, roundHeight - 1, 30, 30);
		this.renderLevel(g, changeX, changeY, true);
		
		this.getModel().getModel().setRec(new Rectangle2D.Double(changeX - 10, changeY - 10, roundWidth, roundHeight));
		
		g.dispose();
		
		this.iLevelOver = new BufferedImage(roundWidth, roundHeight, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D)(this.iLevelOver.getGraphics());
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.model.renderBackground(g, -(changeX-10 + 1), -(changeY - 10 + 1));
		g.setColor(ApoSlitherLinkLevelHelp.COLOR_BACKGROUND);
		g.fillRoundRect(0, 0, roundWidth - 1, roundHeight - 1, 30, 30);
		g.setColor(ApoSlitherLinkLevelHelp.COLOR_LINE);
		g.drawRoundRect(0, 0, roundWidth - 1, roundHeight - 1, 30, 30);
		this.renderLevel(g, 10, 10, true);
		
		g.dispose();
	}
	
	public BufferedImage getILevelOver() {
		return this.iLevelOver;
	}

	public void renderLevel(Graphics2D g, int changeX, int changeY, boolean bImage) {
		for (int x = 0; x < this.level[0].length; x++) {
			for (int y = 0; y < this.level.length; y++) {
				this.level[y][x].render(g, changeX, changeY, bImage);
			}
		}
	}

	public void changeLevelString(int pos, String value) {
		this.curString = this.curString.substring(0, pos) + value + this.curString.substring(pos + 1);
		this.setLevel(this.curString, true);
	}
	
	public void setLevel(int width, int height) {
		String w = String.valueOf(width);
		while (w.length() < 2) {
			w = "0" + w;
		}
		String h = String.valueOf(height);
		while (h.length() < 2) {
			h = "0" + h;
		}
		String levelString = w + h;
		for (int i = 0; i < width * height; i++) {
			levelString += " ";
		}
		this.setLevel(levelString, true);
	}
	
	public String getCurString() {
		return this.curString;
	}

	public void setLevel(String levelString, boolean bMakeLevel) {
		this.blinkLine = null;
		
		this.curString = levelString;
		int width = -1;
		try {
			width = Integer.valueOf(levelString.substring(0, 2));
			levelString = levelString.substring(2);
		} catch (Exception ex) {
			width = -1;
		}
		int height = -1;
		try {
			height = Integer.valueOf(levelString.substring(0, 2));
			levelString = levelString.substring(2);
		} catch (Exception ex) {
			height = -1;
		}
		this.level = new ApoSlitherLinkLevelHelp[height][width];
		int realWidth = 60;
		if ((width >= 7) || (height >= 7)) {
			realWidth = 45;
			if ((width >= 9) || (height >= 9)) {
				realWidth = 35;
				if ((width >= 12) || (height >= 12)) {
					realWidth = 25;
				}
			}
		}
		for (int y = 0; y < this.level.length; y++) {
			for (int x = 0; x < this.level[0].length; x++) {
				int value = -1;
				try {
					value = Integer.valueOf(levelString.substring(0, 1));
				} catch (Exception ex) {
					value = -1;
				}
				try {
					levelString = levelString.substring(1);
				} catch (Exception ex) {
				}
				this.level[y][x] = new ApoSlitherLinkLevelHelp(x * realWidth, y * realWidth, value, realWidth);
			}
		}
		this.points = new ApoSlitherLinkLine[this.level.length + 1][this.level[0].length + 1];
		for (int x = 0; x < this.level[0].length + 1; x++) {
			for (int y = 0; y < this.level.length + 1; y++) {
				this.points[y][x] = new ApoSlitherLinkLine(x, y);
			}
		}
		if (bMakeLevel) {
			this.makeLevelImage();
		}
	}
	
	public void mouseMoved(int x, int y) {
		int width = this.level[0][0].getWidth();
		int levelX = x / width;
		int levelY = y / width;
		
		this.mouseMoved();
		
		int value = 12;
		if (x % width < value) {
			if (x % width > y % width) {
				this.mouseMovedNorth(levelX, levelY, true);
			} else if ((y % width >= width - value) && (x % width > width - y % width)) {
				this.mouseMovedSouth(levelX, levelY, true);
			} else {
				this.mouseMovedWest(levelX, levelY, true);
			}
		} else if (x % width >= width - value) {
			this.mouseMovedEast(levelX, levelY, true);
		} else if (y % width < value) {
			this.mouseMovedNorth(levelX, levelY, true);
		} else if (y % width >= width - value) {
			this.mouseMovedSouth(levelX, levelY, true);
		}
	}
	
	public void mouseMoved() {
		for (int x = 0; x < this.level[0].length; x++) {
			for (int y = 0; y < this.level.length; y++) {
				this.level[y][x].changeNorthMoved(false);
			}
		}
	}
	
	private void mouseMovedNorth(int levelX, int levelY, boolean bOver) {
		this.level[levelY][levelX].changeNorthMoved(bOver);
		if (levelY - 1 >= 0) {
			this.level[levelY - 1][levelX].changeSouthMoved(bOver);					
		}
	}
	
	private void mouseMovedSouth(int levelX, int levelY, boolean bOver) {
		this.level[levelY][levelX].changeSouthMoved(bOver);
		if (levelY + 1 < this.level.length) {
			this.level[levelY + 1][levelX].changeNorthMoved(bOver);
		}
	}
	
	private void mouseMovedEast(int levelX, int levelY, boolean bOver) {
		this.level[levelY][levelX].changeEastMoved(bOver);
		if (levelX + 1 < this.level[0].length) {
			this.level[levelY][levelX + 1].changeWestMoved(bOver);					
		}
	}
	
	private void mouseMovedWest(int levelX, int levelY, boolean bOver) {
		this.level[levelY][levelX].changeWestMoved(bOver);
		if (levelX - 1 >= 0) {
			this.level[levelY][levelX - 1].changeEastMoved(bOver);					
		}
	}
	
	public void mouseReleased(int x, int y, boolean bRight) {
		int width = this.level[0][0].getWidth();
		int levelX = x / width;
		int levelY = y / width;
		
		int value = 12;
		if (x % width < value) {
			if (x % width > y % width) {
				this.mouseNorth(levelX, levelY, bRight);
			} else if ((y % width >= width - value) && (x % width > width - y % width)) {
				this.mouseSouth(levelX, levelY, bRight);
			} else {
				this.mouseWest(levelX, levelY, bRight);
			}
		} else if (x % width >= width - value) {
			this.mouseEast(levelX, levelY, bRight);
		} else if (y % width < value) {
			this.mouseNorth(levelX, levelY, bRight);
		} else if (y % width >= width - value) {
			this.mouseSouth(levelX, levelY, bRight);
		}
		this.setPointsArray(false);
	}
	
	private void mouseNorth(int levelX, int levelY, boolean bRight) {
		if (bRight) {
			this.level[levelY][levelX].changeNorthEdgeBlocked();
			if (levelY - 1 >= 0) {
				this.level[levelY - 1][levelX].changeSouthEdgeBlocked();					
			}
		} else {
			this.level[levelY][levelX].changeNorthEdge();
			if (levelY - 1 >= 0) {
				this.level[levelY - 1][levelX].changeSouthEdge();					
			}
		}
	}
	
	private void mouseSouth(int levelX, int levelY, boolean bRight) {
		if (bRight) {
			this.level[levelY][levelX].changeSouthEdgeBlocked();
			if (levelY + 1 < this.level.length) {
				this.level[levelY + 1][levelX].changeNorthEdgeBlocked();					
			}
		} else {
			this.level[levelY][levelX].changeSouthEdge();				
			if (levelY + 1 < this.level.length) {
				this.level[levelY + 1][levelX].changeNorthEdge();					
			}
		}
	}
	
	private void mouseEast(int levelX, int levelY, boolean bRight) {
		if (bRight) {
			this.level[levelY][levelX].changeEastEdgeBlocked();
			if (levelX + 1 < this.level[0].length) {
				this.level[levelY][levelX + 1].changeWestEdgeBlocked();					
			}
		} else {
			this.level[levelY][levelX].changeEastEdge();
			if (levelX + 1 < this.level[0].length) {
				this.level[levelY][levelX + 1].changeWestEdge();					
			}
		}
	}
	
	private void mouseWest(final int levelX, final int levelY, final boolean bRight) {
		if (bRight) {
			this.level[levelY][levelX].changeWestEdgeBlocked();
			if (levelX - 1 >= 0) {
				this.level[levelY][levelX - 1].changeEastEdgeBlocked();					
			}
		} else {
			this.level[levelY][levelX].changeWestEdge();				
			if (levelX - 1 >= 0) {
				this.level[levelY][levelX - 1].changeEastEdge();					
			}
		}
	}
	
	public boolean isSolved() {
		for (int x = 0; x < this.level[0].length; x++) {
			for (int y = 0; y < this.level.length; y++) {
				if (!this.level[y][x].isComplete()) {
					return false;
				} else if (this.level[y][x].hasAllEdges()) {
					return false;
				}
			}
		}
		return this.isOneLine();
	}
	
	private void setPointsArray(boolean bOneLine) {
		for (int y = 0; y < this.level.length; y++) {
			for (int x = 0; x < this.level[0].length; x++) {
				if (x - 1 >= 0) {
					if (this.level[y][x-1].getEdges()[ApoSlitherLinkLevelHelp.NORTH]) {
						this.points[y][x].add(x - 1, y);
					} else {
						this.points[y][x].remove(x - 1, y);						
					}
				}
				if (y - 1 >= 0) {
					if (this.level[y-1][x].getEdges()[ApoSlitherLinkLevelHelp.WEST]) {
						this.points[y][x].add(x, y - 1);
					} else {
						this.points[y][x].remove(x, y - 1);						
					}
				}
				if (this.level[y][x].getEdges()[ApoSlitherLinkLevelHelp.NORTH]) {
					this.points[y][x].add(x + 1, y);
				} else {
					this.points[y][x].remove(x + 1, y);					
				}
				if (y + 1 <= this.level.length) {
					if (this.level[y][x].getEdges()[ApoSlitherLinkLevelHelp.WEST]) {
						this.points[y][x].add(x, y + 1);
					} else {
						this.points[y][x].remove(x, y + 1);					
					}
				}
				if (y + 1 == this.level.length) {
					if (x + 1 <= this.level[0].length) {
						if (x - 1 >= 0) {
							if (this.level[y][x-1].getEdges()[ApoSlitherLinkLevelHelp.SOUTH]) {
								this.points[y+1][x].add(x - 1, y + 1);
							} else {
								this.points[y+1][x].remove(x - 1, y + 1);
							}
						}
						if (this.level[y][x].getEdges()[ApoSlitherLinkLevelHelp.SOUTH]) {
							this.points[y+1][x].add(x + 1, y + 1);
						} else {
							this.points[y+1][x].remove(x + 1, y + 1);
						}
						if (this.level[y][x].getEdges()[ApoSlitherLinkLevelHelp.WEST]) {
							this.points[y+1][x].add(x, y);
						} else {
							this.points[y+1][x].remove(x, y);
						}
					}
				}
				if (x + 1 == this.level[0].length) {
					if (y + 1 == this.level.length) {
						if (this.level[y][x].getEdges()[ApoSlitherLinkLevelHelp.EAST]) {
							this.points[y + 1][x + 1].add(x + 1, y);
						} else {
							this.points[y + 1][x + 1].remove(x + 1, y);
						}
						if (this.level[y][x].getEdges()[ApoSlitherLinkLevelHelp.SOUTH]) {
							this.points[y + 1][x + 1].add(x, y - 1);
						} else {
							this.points[y + 1][x + 1].remove(x, y - 1);
						}
					}
					if (y - 1 >= 0) {
						if (this.level[y - 1][x].getEdges()[ApoSlitherLinkLevelHelp.EAST]) {
							this.points[y][x + 1].add(x + 1, y - 1);
						} else {
							this.points[y][x + 1].remove(x + 1, y - 1);
						}
					}
					if (this.level[y][x].getEdges()[ApoSlitherLinkLevelHelp.EAST]) {
						this.points[y][x + 1].add(x + 1, y + 1);
					} else {
						this.points[y][x + 1].remove(x + 1, y + 1);
					}
					if (this.level[y][x].getEdges()[ApoSlitherLinkLevelHelp.NORTH]) {
						this.points[y][x + 1].add(x, y);
					} else {
						this.points[y][x + 1].remove(x, y);
					}
				}
			}
		}
		
		if (this.blinkLine != null) {
			int myX = this.blinkLine.getStartX();
			int myY = this.blinkLine.getStartY();
			int nextX = this.blinkLine.getVecX();
			int nextY = this.blinkLine.getVecY();
			boolean bBreak = false;
			for (int i = 0; i < this.points[myY][myX].getConnected().size(); i++) {
				if ((myX + nextX == this.points[myY][myX].getConnected().get(i).x) &&
					(myY + nextY == this.points[myY][myX].getConnected().get(i).y)) {
					bBreak = true;
				}
			}
			if (!bBreak) {
				this.blinkLine = null;
			}
		}
		
		boolean bCan = true;
		for (int y = 0; y <= this.level.length; y++) {
			for (int x = 0; x <= this.level[0].length; x++) {
				if (this.points[y][x].getConnected().size() > 0) {
					bCan = false;
					if (this.blinkLine == null) {
						boolean[] nesw = new boolean[4];
						for (int i = 0; i < this.points[y][x].getConnected().size(); i++) {
							if (this.points[y][x].getConnected().get(i).x != x) {
								if (this.points[y][x].getConnected().get(i).x > x) {
									nesw[1] = true;
								} else {
									nesw[3] = true;
								}
							} else if (this.points[y][x].getConnected().get(i).y != y) {
								if (this.points[y][x].getConnected().get(i).y > y) {
									nesw[2] = true;
								} else {
									nesw[0] = true;
								}
							}
						}
						int width = this.level[0][0].getWidth();
						if (nesw[1]) {
							this.blinkLine = new ApoSlitherLinkBlinkLine(x, y, x, y, width, 1, 0);	
						} else if (nesw[3]) {
							this.blinkLine = new ApoSlitherLinkBlinkLine(x, y, x, y, width, -1, 0);	
						} else if (nesw[0]) {
							this.blinkLine = new ApoSlitherLinkBlinkLine(x, y, x, y, width, 0, -1);	
						} else {
							this.blinkLine = new ApoSlitherLinkBlinkLine(x, y, x, y, width, 0, 1);	
						}
						return;
					}
				}
			}
		}
		if (bCan) {
			this.blinkLine = null;
		}
	}
	
	private boolean isOneLine() {
		this.setPointsArray(true);
		for (int y = 0; y < this.level.length + 1; y++) {
			for (int x = 0; x < this.level[0].length + 1; x++) {
				if (!points[y][x].hasCorrectEdges()) {
					return false;
				}
			}
		}
		return true;
	}
	
	public final ApoSlitherLinkLevelHelp[][] getLevel() {
		return this.level;
	}
	
	public void think(int delta) {
		if (this.blinkLine != null) {
			this.blinkLine.think(delta);
			if (this.blinkLine.isBNext()) {
				int x = this.blinkLine.getStartX();
				int y = this.blinkLine.getStartY();
				int oldX = x;
				int oldY = y;
				int changeX = 0;
				int changeY = 0;
				if (this.blinkLine.getVecX() > 0) {
					changeX = 1;
					x += changeX;
				} else if (this.blinkLine.getVecX() < 0) {
					changeX = -1;
					x += changeX;
				}
				if (this.blinkLine.getVecY() > 0) {
					changeY = 1;
					y += changeY;
				} else if (this.blinkLine.getVecY() < 0) {
					changeY = -1;
					y += changeY;
				}
				if ((x >= 0) && (x <= this.level[0].length) && 
					(y >= 0) && (y <= this.level.length) &&
					((x != oldX) || (y != oldY))) {
					boolean[] nesw = new boolean[4];
					int size = this.points[y][x].getConnected().size();
					for (int i = 0; i < this.points[y][x].getConnected().size(); i++) {
						if (this.points[y][x].getConnected().get(i).x != x) {
							if (this.points[y][x].getConnected().get(i).x > x) {
								nesw[1] = true;
								if (changeX < 0) {
									nesw[1] = false;
								}
							} else {
								nesw[3] = true;
								if (changeX > 0) {
									nesw[3] = false;
								}
							}
						} else if (this.points[y][x].getConnected().get(i).y != y) {
							if (this.points[y][x].getConnected().get(i).y > y) {
								nesw[2] = true;
								if (changeY < 0) {
									nesw[2] = false;
								}
							} else {
								nesw[0] = true;
								if (changeY > 0) {
									nesw[0] = false;
								}
							}
						}
					}
					int width = this.level[0][0].getWidth();
					if (size <= 1) {
						this.blinkLine = new ApoSlitherLinkBlinkLine(x, y, x, y, width, -changeX, -changeY);
					} else {
						if (nesw[1]) {
							this.blinkLine = new ApoSlitherLinkBlinkLine(x, y, x, y, width, 1, 0);	
						} else if (nesw[3]) {
							this.blinkLine = new ApoSlitherLinkBlinkLine(x, y, x, y, width, -1, 0);	
						} else if (nesw[0]) {
							this.blinkLine = new ApoSlitherLinkBlinkLine(x, y, x, y, width, 0, -1);	
						} else {
							this.blinkLine = new ApoSlitherLinkBlinkLine(x, y, x, y, width, 0, 1);	
						}
					}
				}
			}
		}
	}

	public void render(Graphics2D g, int changeX, int changeY) {
		if (this.iLevel != null) {
			g.drawImage(this.iLevel, 0, 0, null);
			
			this.getModel().renderClouds(g);
			
			if (this.getModel().getModel().isBOver()) {
				for (int i = this.getModel().getClouds().size() - 1; i >= 0; i--) {
					if (this.getModel().getClouds().get(i).getOverRec() != null) {
						int x = (int)(this.getModel().getClouds().get(i).getOverRec().getX() - this.getModel().getModel().getRec().getX());
						int y = (int)(this.getModel().getClouds().get(i).getOverRec().getY() - this.getModel().getModel().getRec().getY());
						int w = (int)(this.getModel().getClouds().get(i).getOverRec().getWidth());
						int h = (int)(this.getModel().getClouds().get(i).getOverRec().getHeight());
						if ((x >= 0) && (y >= 0) && (w > 0) && (h > 0)) {
							if (w+1 < this.getILevelOver().getWidth()) {
								w = w+1;
							}
							try {
								g.drawImage(this.getILevelOver().getSubimage(x, y, w, h), (int)(this.getModel().getClouds().get(i).getOverRec().getX()), (int)(this.getModel().getClouds().get(i).getOverRec().getY()), null);
							} catch (Exception ex) {
							}
						}
					}
				}
				
			}
		}
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for (int x = 0; x < this.level[0].length; x++) {
			for (int y = 0; y < this.level.length; y++) {
				this.level[y][x].render(g, changeX, changeY, false);
			}
		}
		
		if (this.bPoints) {
			int width = 20;
			for (int x = 0; x < this.points[0].length; x++) {
				for (int y = 0; y < this.points.length; y++) {
					if (this.points[y][x].getConnected().size() > 0) {
						g.setColor(ApoSlitherLinkLevelHelp.COLOR_BACKGROUND);
						g.fillOval(changeX + x * this.level[0][0].getWidth() - width/2, changeY + y * this.level[0][0].getWidth() - width/2, width, width);
						g.setColor(ApoSlitherLinkLevelHelp.COLOR_BACKGROUND.darker().darker());
						g.drawOval(changeX + x * this.level[0][0].getWidth() - width/2, changeY + y * this.level[0][0].getWidth() - width/2, width, width);
						String s = String.valueOf(this.points[y][x].getConnected().size());
						int w = g.getFontMetrics().stringWidth(s);
						g.setColor(ApoSlitherLinkLevelHelp.COLOR_LINE);
						g.drawString(s, changeX + x * this.level[0][0].getWidth() - w/2, changeY + y * this.level[0][0].getWidth() + width/3);
					}
				}
			}
		}

		if (this.blinkLine != null) {
			this.blinkLine.render(g, changeX, changeY);
		}
	}
	
}
