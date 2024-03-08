package apoSlitherLink.level;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class ApoSlitherLinkLevelsHelp {
	
	public static final int IMAGE_WIDTH = 200;
	public static final int IMAGE_HEIGHT = 200;
	
	public static final String EMPTY = "a";
	public static final String UP_LEFT = "b";
	public static final String LEFT_DOWN = "c";
	public static final String DOWN_RIGHT = "d";
	public static final String RIGHT_UP = "e";
	public static final String UP_DOWN = "f";
	public static final String LEFT_RIGHT = "g";
	public static final String LEFT = "h";
	public static final String RIGHT = "i";
	public static final String UP = "j";
	public static final String DOWN = "k";
	public static final String LEFT_UP_RIGHT = "l";
	public static final String UP_RIGHT_DOWN = "m";
	public static final String RIGHT_DOWN_LEFT = "n";
	public static final String DOWN_LEFT_UP = "o";
	
	private String levelString;
	private boolean bSolved;
	private String solvedLevelString;
	private BufferedImage iSolved;
	
	private int width;
	private int height;
	
	public ApoSlitherLinkLevelsHelp(String levelString) {
		this(levelString, false);
	}
	
	public ApoSlitherLinkLevelsHelp(String levelString, boolean bSolved) {
		this.levelString = levelString;
		this.bSolved = bSolved;
		this.solvedLevelString = "";
		this.iSolved = null;
		
		int width = -1;
		try {
			width = Integer.valueOf(levelString.substring(0, 2));
		} catch (Exception ex) {
			width = -1;
		}
		int height = -1;
		try {
			height = Integer.valueOf(solvedLevelString.substring(2, 4));
		} catch (Exception ex) {
			height = -1;
		}
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public boolean isBSolved() {
		return this.bSolved;
	}

	public void setBSolved(boolean solved) {
		this.bSolved = solved;
	}
	
	public String getSolvedLevelString() {
		return this.solvedLevelString;
	}

	public BufferedImage getISolved() {
		return this.iSolved;
	}

	public void setSolvedLevelString(String solvedLevelString) {
		this.solvedLevelString = solvedLevelString;
	}
	
	public void setSolvedLevelString(ApoSlitherLinkLevel level) {
		if ((this.solvedLevelString != null) && (this.solvedLevelString.length() > 0)) {
			return;
		}
		this.setBSolved(true);
		String solvedLevelString = this.levelString;
		this.solvedLevelString = "";
		int width = -1;
		try {
			width = Integer.valueOf(solvedLevelString.substring(0, 2));
			this.solvedLevelString += solvedLevelString.substring(0, 2);
			solvedLevelString = solvedLevelString.substring(2);
		} catch (Exception ex) {
			width = -1;
		}
		int height = -1;
		try {
			height = Integer.valueOf(solvedLevelString.substring(0, 2));
			this.solvedLevelString += solvedLevelString.substring(0, 2);
			solvedLevelString = solvedLevelString.substring(2);
		} catch (Exception ex) {
			height = -1;
		}
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if ((level.getLevel()[y][x].getEdges()[ApoSlitherLinkLevelHelp.EAST]) && 
					(level.getLevel()[y][x].getEdges()[ApoSlitherLinkLevelHelp.NORTH]) && 
					(level.getLevel()[y][x].getEdges()[ApoSlitherLinkLevelHelp.SOUTH])) {
						this.solvedLevelString += String.valueOf(ApoSlitherLinkLevelsHelp.DOWN_LEFT_UP);
				} else if ((level.getLevel()[y][x].getEdges()[ApoSlitherLinkLevelHelp.EAST]) && 
						(level.getLevel()[y][x].getEdges()[ApoSlitherLinkLevelHelp.WEST]) && 
						(level.getLevel()[y][x].getEdges()[ApoSlitherLinkLevelHelp.SOUTH])) {
						this.solvedLevelString += String.valueOf(ApoSlitherLinkLevelsHelp.RIGHT_DOWN_LEFT);
				} else if ((level.getLevel()[y][x].getEdges()[ApoSlitherLinkLevelHelp.EAST]) && 
						(level.getLevel()[y][x].getEdges()[ApoSlitherLinkLevelHelp.WEST]) && 
						(level.getLevel()[y][x].getEdges()[ApoSlitherLinkLevelHelp.NORTH])) {
						this.solvedLevelString += String.valueOf(ApoSlitherLinkLevelsHelp.LEFT_UP_RIGHT);
				} else if ((level.getLevel()[y][x].getEdges()[ApoSlitherLinkLevelHelp.SOUTH]) && 
						(level.getLevel()[y][x].getEdges()[ApoSlitherLinkLevelHelp.WEST]) && 
						(level.getLevel()[y][x].getEdges()[ApoSlitherLinkLevelHelp.NORTH])) {
						this.solvedLevelString += String.valueOf(ApoSlitherLinkLevelsHelp.UP_RIGHT_DOWN);
				} else if ((level.getLevel()[y][x].getEdges()[ApoSlitherLinkLevelHelp.EAST]) && 
					(level.getLevel()[y][x].getEdges()[ApoSlitherLinkLevelHelp.NORTH])) {
					this.solvedLevelString += String.valueOf(ApoSlitherLinkLevelsHelp.UP_LEFT);
				} else if ((level.getLevel()[y][x].getEdges()[ApoSlitherLinkLevelHelp.EAST]) && 
					(level.getLevel()[y][x].getEdges()[ApoSlitherLinkLevelHelp.SOUTH])) {
					this.solvedLevelString += String.valueOf(ApoSlitherLinkLevelsHelp.LEFT_DOWN);
				} else if ((level.getLevel()[y][x].getEdges()[ApoSlitherLinkLevelHelp.EAST]) && 
					(level.getLevel()[y][x].getEdges()[ApoSlitherLinkLevelHelp.WEST])) {
					this.solvedLevelString += String.valueOf(ApoSlitherLinkLevelsHelp.LEFT_RIGHT);
				} else if ((level.getLevel()[y][x].getEdges()[ApoSlitherLinkLevelHelp.NORTH]) && 
					(level.getLevel()[y][x].getEdges()[ApoSlitherLinkLevelHelp.WEST])) {
					this.solvedLevelString += String.valueOf(ApoSlitherLinkLevelsHelp.RIGHT_UP);
				} else if ((level.getLevel()[y][x].getEdges()[ApoSlitherLinkLevelHelp.NORTH]) && 
					(level.getLevel()[y][x].getEdges()[ApoSlitherLinkLevelHelp.SOUTH])) {
					this.solvedLevelString += String.valueOf(ApoSlitherLinkLevelsHelp.UP_DOWN);
				} else if ((level.getLevel()[y][x].getEdges()[ApoSlitherLinkLevelHelp.WEST]) && 
					(level.getLevel()[y][x].getEdges()[ApoSlitherLinkLevelHelp.SOUTH])) {
					this.solvedLevelString += String.valueOf(ApoSlitherLinkLevelsHelp.DOWN_RIGHT);
				} else if (level.getLevel()[y][x].getEdges()[ApoSlitherLinkLevelHelp.WEST]) {
					this.solvedLevelString += String.valueOf(ApoSlitherLinkLevelsHelp.RIGHT);
				} else if (level.getLevel()[y][x].getEdges()[ApoSlitherLinkLevelHelp.EAST]) {
					this.solvedLevelString += String.valueOf(ApoSlitherLinkLevelsHelp.LEFT);
				} else if (level.getLevel()[y][x].getEdges()[ApoSlitherLinkLevelHelp.NORTH]) {
					this.solvedLevelString += String.valueOf(ApoSlitherLinkLevelsHelp.UP);
				} else if (level.getLevel()[y][x].getEdges()[ApoSlitherLinkLevelHelp.SOUTH]) {
					this.solvedLevelString += String.valueOf(ApoSlitherLinkLevelsHelp.DOWN);
				} else {
					this.solvedLevelString += String.valueOf(ApoSlitherLinkLevelsHelp.EMPTY);
				}
			}
		}
		this.makeSolvedImage(level);
	}
	
	public void makeSolvedImage(ApoSlitherLinkLevel level) {
		if (iSolved != null) {
			return;
		}
		
		String solvedLevelString = this.solvedLevelString;
		
		int width = -1;
		try {
			width = Integer.valueOf(solvedLevelString.substring(0, 2));
			solvedLevelString = solvedLevelString.substring(2);
		} catch (Exception ex) {
			width = -1;
		}
		int height = -1;
		try {
			height = Integer.valueOf(solvedLevelString.substring(0, 2));
			solvedLevelString = solvedLevelString.substring(2);
		} catch (Exception ex) {
			height = -1;
		}
		level.setLevel(this.levelString, false);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				String value = "";
				try {
					value = solvedLevelString.substring(0, 1);
					solvedLevelString = solvedLevelString.substring(1);
				} catch (Exception ex) {
					value = "";
				}
				if (value.equals(ApoSlitherLinkLevelsHelp.DOWN_RIGHT)) {
					level.getLevel()[y][x].changeSouthEdge();
					level.getLevel()[y][x].changeWestEdge();
				} else if (value.equals(ApoSlitherLinkLevelsHelp.LEFT_DOWN)) {
					level.getLevel()[y][x].changeSouthEdge();
					level.getLevel()[y][x].changeEastEdge();
				} else if (value.equals(ApoSlitherLinkLevelsHelp.LEFT_RIGHT)) {
					level.getLevel()[y][x].changeWestEdge();
					level.getLevel()[y][x].changeEastEdge();
				} else if (value.equals(ApoSlitherLinkLevelsHelp.RIGHT_UP)) {
					level.getLevel()[y][x].changeWestEdge();
					level.getLevel()[y][x].changeNorthEdge();
				} else if (value.equals(ApoSlitherLinkLevelsHelp.UP_DOWN)) {
					level.getLevel()[y][x].changeSouthEdge();
					level.getLevel()[y][x].changeNorthEdge();
				} else if (value.equals(ApoSlitherLinkLevelsHelp.UP_LEFT)) {
					level.getLevel()[y][x].changeEastEdge();
					level.getLevel()[y][x].changeNorthEdge();
				} else if (value.equals(ApoSlitherLinkLevelsHelp.DOWN_LEFT_UP)) {
					level.getLevel()[y][x].changeEastEdge();
					level.getLevel()[y][x].changeNorthEdge();
					level.getLevel()[y][x].changeSouthEdge();
				} else if (value.equals(ApoSlitherLinkLevelsHelp.UP_RIGHT_DOWN)) {
					level.getLevel()[y][x].changeWestEdge();
					level.getLevel()[y][x].changeNorthEdge();
					level.getLevel()[y][x].changeSouthEdge();
				} else if (value.equals(ApoSlitherLinkLevelsHelp.LEFT_UP_RIGHT)) {
					level.getLevel()[y][x].changeWestEdge();
					level.getLevel()[y][x].changeNorthEdge();
					level.getLevel()[y][x].changeEastEdge();
				} else if (value.equals(ApoSlitherLinkLevelsHelp.RIGHT_DOWN_LEFT)) {
					level.getLevel()[y][x].changeWestEdge();
					level.getLevel()[y][x].changeSouthEdge();
					level.getLevel()[y][x].changeEastEdge();
				} else if (value.equals(ApoSlitherLinkLevelsHelp.RIGHT)) {
					level.getLevel()[y][x].changeWestEdge();
				} else if (value.equals(ApoSlitherLinkLevelsHelp.LEFT)) {
					level.getLevel()[y][x].changeEastEdge();
				} else if (value.equals(ApoSlitherLinkLevelsHelp.UP)) {
					level.getLevel()[y][x].changeNorthEdge();
				} else if (value.equals(ApoSlitherLinkLevelsHelp.DOWN)) {
					level.getLevel()[y][x].changeSouthEdge();
				}
			}
		}
		
		int imageWidth = level.getLevel()[0][0].getWidth();
		int w = level.getLevel()[0].length;
		int h = level.getLevel().length;
		
		BufferedImage iLevel = new BufferedImage(w * imageWidth + 4, h * imageWidth + 4, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = (Graphics2D)(iLevel.getGraphics());
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		level.renderLevel(g, 2, 2, true);
		level.renderLevel(g, 2, 2, false);
		g.dispose();
		
		int nextWidth = ApoSlitherLinkLevelsHelp.IMAGE_WIDTH;
		int nextHeight = ApoSlitherLinkLevelsHelp.IMAGE_HEIGHT;
		if (w > h) {
			nextHeight = (int)((double)(h) / (double)(w) * (double)(ApoSlitherLinkLevelsHelp.IMAGE_WIDTH));
		} else if (h > w) {
			nextWidth = (int)((double)(w) / (double)(h) * (double)(ApoSlitherLinkLevelsHelp.IMAGE_WIDTH));
		}
		this.iSolved = new BufferedImage(nextWidth, nextHeight, BufferedImage.TYPE_INT_ARGB_PRE);
		g = (Graphics2D)(this.iSolved.getGraphics());
		g.drawImage(iLevel.getScaledInstance(nextWidth, nextHeight, Image.SCALE_SMOOTH), 0, 0, null);
		g.dispose();
	}
	
	public String getLevelString() {
		return this.levelString;
	}
	
}
