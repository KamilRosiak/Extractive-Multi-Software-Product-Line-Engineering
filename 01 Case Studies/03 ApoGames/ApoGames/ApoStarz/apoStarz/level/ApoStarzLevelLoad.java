package apoStarz.level;

import java.awt.Point;
import java.util.ArrayList;

import apoStarz.ApoStarzConstants;
import apoStarz.entity.ApoStarzBlock;
import apoStarz.entity.ApoStarzFire;
import apoStarz.entity.ApoStarzGoal;
import apoStarz.entity.ApoStarzStar;

public class ApoStarzLevelLoad {

	private final int METHOD_ONE = 1;
	private final int METHOD_VERTICAL = 2;
	private final int METHOD_HORIZONTAL = 3;
	
	private ApoStarzLevel level;
	
	public ApoStarzLevelLoad(ApoStarzLevel level) {
		this.level = level;
	}
	
	public boolean loadLevel(String levelString) {
		try {
			this.level.setFallingEntities(null);
			this.level.setEntities(null);
			int method = -1;
			if (levelString.length() >= 1) {
				method = levelString.charAt(0) - 96;
				levelString = levelString.substring(1);
			}
			if (method == this.METHOD_ONE) {
				return this.loadLevelMethodOne(levelString);
			} else if (method == this.METHOD_HORIZONTAL) {
				return this.loadLevelMethodHorizontal(levelString);
			} else if (method == this.METHOD_VERTICAL) {
				return this.loadLevelMethodVertical(levelString);
			} /*else if (method == this.METHOD_HORIZONTAL_TWO) {
			this.loadLevelMethodHorizontalTwo(levelString);
		} else if (method == this.METHOD_VERTICAL_TWO) {
			this.loadLevelMethodVerticalTwo(levelString);
		}*/
		} catch (Exception ex) {
			return false;
		}
		return false;
	}
	
	private boolean loadLevelMethodOne(String levelString) {
		try {
			if (levelString.length() >= 1) {
				this.setWidth(levelString.substring(0, 1));
				levelString = levelString.substring(1);
			}
			if (levelString.length() >= 1) {
				int x = this.setWall(levelString);
				levelString = levelString.substring(x);
			}
			if (levelString.length() >= 1) {
				int x = this.setBlock(levelString);
				levelString = levelString.substring(x);
			}
			if (levelString.length() >= 1) {
				int x = this.setFire(levelString);
				levelString = levelString.substring(x);
			}
			if (levelString.length() >= 1) {
				int x = this.setGoalAndStar(levelString);
				levelString = levelString.substring(x);
			}
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	private boolean loadLevelMethodVertical(String levelString) {
		try {
			if (levelString.length() >= 1) {
				this.setWidth(levelString.substring(0, 1));
				levelString = levelString.substring(1);
			}
			if (levelString.length() >= 1) {
				this.setVertical(levelString, true);	
			}
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	private boolean loadLevelMethodHorizontal(String levelString) {
		try {
			if (levelString.length() >= 1) {
				this.setWidth(levelString.substring(0, 1));
				levelString = levelString.substring(1);
			}
			if (levelString.length() >= 1) {
				this.setHorizontal(levelString, true);	
			}
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	public String getLevelString() {
		String levelString = this.getLevelStringMethodOne();
		this.level.restartLevel();
		String next = this.getLevelStringMethodHorizontal();
		if (levelString.length() > next.length()) {
			levelString = next;
		}
		next = this.getLevelStringMethodVertical();
		if (levelString.length() > next.length()) {
			levelString = next;
		}
		return levelString;
	}
	
	public String getLevelStringMethodOne() {
		String levelString = "a" + this.getStringForWidth();
		levelString += this.getStringForWall();
		levelString += this.getStringForBlock();
		levelString += this.getStringForFire();
		levelString += this.getStringForGoal();
		levelString += this.getStringForStar();
		return levelString;
	}

	public String getLevelStringMethodVertical() {
		String levelString = "b" + this.getStringForWidth();
		levelString += this.getStringVertical(true);
		return levelString;
	}
	
	public String getLevelStringMethodHorizontal() {
		String levelString = "c" + this.getStringForWidth();
		levelString += this.getStringHorizontal(true);
		return levelString;
	}
	
	private String getStringForWidth() {
		String levelString = "";
		int width = this.level.getWidth() + 96;
		levelString += Character.toString((char)width);
		return levelString;
	}	
	
	public void setWidth(String widthString) {
		char c;
		int width = 12;
		if (widthString.length() >= 1) {
			c = widthString.charAt(0);
			width = c - 96;
			//System.out.println("width = "+width);
		}
		this.level.makeNewLevel(width);
	}
	
	private String getStringForWall() {
		String wall = "";
		int size = 0;
		ArrayList<Point> pointList = new ArrayList<Point>();
		for (int i = 1; i < this.level.getOriginalEntities().length - 1; i++) {
			for (int j = 1; j < this.level.getOriginalEntities()[0].length - 1; j++) {
				if (this.level.getOriginalEntities()[i][j] != null) {
					if ((this.level.getOriginalEntities()[i][j] instanceof ApoStarzBlock) &&
						(!this.level.getOriginalEntities()[i][j].isBCanFall())) {
						pointList.add(new Point(j, i));
						size++;
					}
				}
			}
		}
		wall += Character.toString((char)(size + 33));
		for (int i = 0; i < pointList.size(); i++) {
			int position = pointList.get(i).x + pointList.get(i).y * (this.level.getWidth() - 2);
			if (position <= 93) {
				position += 32;
			} else {
				position += 68;
			}
			wall += Character.toString((char)(position));
		}
		return wall;
	}
	
	private int setWall(String wallString) {
		int width = 12;
		if (wallString.length() >= 1) {
			char c = wallString.charAt(0);
			width = c;
			if (width < 150) {
				width -= 33;
			} else {
				width -= 69;
			}
		}
		for (int i = 0; i < width; i++) {
			char c = wallString.charAt(i + 1);
			int position = c - 68;
			if ((int)c < 150) {
				position = c - 32;
			}
			int x = 0;
			int y = 0;
			while (position > this.level.getWidth() - 2) {
				y++;
				position -= this.level.getWidth() - 2;
			}
			x = position;
			this.level.addEntity(x, y, ApoStarzConstants.BLOCK, true);
		}
		return width + 1;
	}
	
	private String getStringForBlock() {
		String block = "";
		int size = 0;
		ArrayList<Point> pointList = new ArrayList<Point>();
		for (int i = 0; i < this.level.getOriginalEntities().length; i++) {
			for (int j = 0; j < this.level.getOriginalEntities()[0].length; j++) {
				if (this.level.getOriginalEntities()[i][j] != null) {
					if ((this.level.getOriginalEntities()[i][j] instanceof ApoStarzBlock) &&
						(this.level.getOriginalEntities()[i][j].isBCanFall())) {
						pointList.add(new Point(j, i));
						size++;
					}
				}
			}
		}
		block += Character.toString((char)(size + 33));
		for (int i = 0; i < pointList.size(); i++) {
			int position = pointList.get(i).x + pointList.get(i).y * (this.level.getWidth() - 2);
			if (position <= 93) {
				position += 32;
			} else {
				position += 68;
			}
			block += Character.toString((char)(position));
		}
		
		return block;
	}
	
	private int setBlock(String blockString) {
		int width = 12;
		if (blockString.length() >= 1) {
			char c = blockString.charAt(0);
			width = c;
			if (width < 150) {
				width -= 33;
			}
		}
		for (int i = 0; i < width; i++) {
			char c = blockString.charAt(i + 1);
			int position = c - 68;
			if ((int)c < 150) {
				position = c - 32;
			}
			int x = 0;
			int y = 0;
			while (position > this.level.getWidth() - 2) {
				y++;
				position -= this.level.getWidth() - 2;
			}
			x = position;
			this.level.addEntity(x, y, ApoStarzConstants.BLOCK_FALLING, true);
		}
		return width + 1;
	}
	
	private String getStringForFire() {
		String fire = "";
		int size = 0;
		ArrayList<Point> pointList = new ArrayList<Point>();
		for (int i = 0; i < this.level.getOriginalEntities().length; i++) {
			for (int j = 0; j < this.level.getOriginalEntities()[0].length; j++) {
				if ((this.level.getOriginalEntities()[i][j] != null) && (this.level.getOriginalEntities()[i][j] instanceof ApoStarzFire)) {
					pointList.add(new Point(j, i));
					size++;
				}
			}
		}
		fire += Character.toString((char)(size + 33));
		for (int i = 0; i < pointList.size(); i++) {
			int position = pointList.get(i).x + pointList.get(i).y * (this.level.getWidth() - 2);
			if (position <= 93) {
				position += 32;
			} else {
				position += 68;
			}
			fire += Character.toString((char)(position));
		}
		
		return fire;
	}
	
	private int setFire(String fireString) {
		int width = 12;
		if (fireString.length() >= 1) {
			char c = fireString.charAt(0);
			width = c;
			if (width < 150) {
				width -= 33;
			}
		}
		for (int i = 0; i < width; i++) {
			char c = fireString.charAt(i + 1);
			int position = c - 68;
			if ((int)c < 150) {
				position = c - 32;
			}
			int x = 0;
			int y = 0;
			while (position > this.level.getWidth() - 2) {
				y++;
				position -= this.level.getWidth() - 2;
			}
			x = position;
			this.level.addEntity(x, y, ApoStarzConstants.FIRE, true);
		}
		return width + 1;
	}
	
	private String getStringForGoal() {
		String goal = "";
		int size = 0;
		ArrayList<Point> pointList = new ArrayList<Point>();
		for (int i = 0; i < this.level.getOriginalEntities().length; i++) {
			for (int j = 0; j < this.level.getOriginalEntities()[0].length; j++) {
				if ((this.level.getOriginalEntities()[i][j] != null) && (this.level.getOriginalEntities()[i][j] instanceof ApoStarzGoal)) {
					size++;
					pointList.add(new Point(j, i));
				}
			}
		}
		goal += Character.toString((char)(size + 33));
		for (int i = 0; i < pointList.size(); i++) {
			int position = pointList.get(i).x + pointList.get(i).y * (this.level.getWidth() - 2);
			if (position <= 93) {
				position += 32;
			} else {
				position += 68;
			}
			//System.out.println(String.valueOf((char)(position)));
			goal += String.valueOf((char)(position));
		}
		return goal;
	}
	
	private String getStringForStar() {
		String star = "";
		ArrayList<Point> pointList = new ArrayList<Point>();
		for (int i = 0; i < this.level.getOriginalEntities().length; i++) {
			for (int j = 0; j < this.level.getOriginalEntities()[0].length; j++) {
				if ((this.level.getOriginalEntities()[i][j] != null) && (this.level.getOriginalEntities()[i][j] instanceof ApoStarzStar)) {
					pointList.add(new Point(j, i));
				}
			}
		}
		for (int i = 0; i < pointList.size(); i++) {
			int position = pointList.get(i).x + pointList.get(i).y * (this.level.getWidth() - 2);
			if (position <= 93) {
				position += 32;
			} else {
				position += 68;
			}
			star += Character.toString((char)(position));
		}
		
		return star;
	}
	
	private int setGoalAndStar(String goalStarString) {
		int width = 12;
		if (goalStarString.length() >= 1) {
			char c = goalStarString.charAt(0);
			width = c - 33;
		}
		for (int i = 0; i < width * 2; i++) {
			char c = goalStarString.charAt(i + 1);
			int position = c - 68;
			if ((int)c < 150) {
				position = c - 32;
			}
			int x = 0;
			int y = 0;
			while (position > this.level.getWidth() - 2) {
				y++;
				position -= this.level.getWidth() - 2;
			}
			x = position;
			if (i < width) {
				//System.out.println("goal mit x = "+x+" y = "+y);
				this.level.addEntity(x, y, ApoStarzConstants.GOAL, true);
			} else {
				//System.out.println("star mit x = "+x+" y = "+y);
				this.level.addEntity(x, y, ApoStarzConstants.STAR, true);
			}
		}
		return width * 2 + 1;
	}

	private String getStringVertical(boolean bFirst) {
		String vertical = "";
		int lastEntity = -1;
		int count = -1;
		for (int j = 1; j < this.level.getOriginalEntities()[0].length - 1; j++) {
			for (int i = 1; i < this.level.getOriginalEntities().length - 1; i++) {
				if (count == -1) {
					count = 1;
					lastEntity = this.level.getLevel()[i][j];
				} else {
					int thisEntity = this.level.getLevel()[i][j];
					if (!bFirst) {
						if ((thisEntity == ApoStarzConstants.STAR) || (thisEntity == ApoStarzConstants.GOAL)) {
							thisEntity = lastEntity;
						}
					}
					if (thisEntity != lastEntity) {
						if (count < 2) {
							vertical += Character.toString((char)(lastEntity + 33));
						} else {
							vertical += Character.toString((char)(count + 43)) + Character.toString((char)(lastEntity + 33));
						}
						count = 1;
						lastEntity = thisEntity;
					} else {
						count++;
					}
				}
			}
		}
		if (count > 1) {
			vertical += Character.toString((char)(count + 43)) + Character.toString((char)(lastEntity + 33));
		} else {
			vertical += Character.toString((char)(lastEntity + 33));
		}
		return vertical;
	}

	private void setVertical(String verticalString, boolean bFirst) {
		int position = 0;
		int x = 1;
		int y = 1;
		while (position < verticalString.length()) {
			char c = verticalString.charAt(position);
			if (c < 43) {
				if (bFirst) {
					this.level.addEntity(x, y, c - 33, true);
				} else {
					if (this.level.getEntities()[y][x] != null) {
						if ((this.level.getEntities()[y][x] instanceof ApoStarzGoal) ||
							(this.level.getEntities()[y][x] instanceof ApoStarzStar)) {
							
						} else {
							this.level.addEntity(x, y, c - 33, true);
						}
					} else {
						this.level.addEntity(x, y, c - 33, true);
					}
				}
				position += 1;
				y += 1;
			} else {
				int size = c - 43;
				int entity = verticalString.charAt(position + 1) - 33;
				//System.out.println("verti size = "+size+" entity = "+entity);
				position += 2;
				for (int i = 0; i < size; i++) {
					if (bFirst) {
						this.level.addEntity(x, y, entity, true);
					} else {
						if (this.level.getEntities()[y][x] == null) {
							this.level.addEntity(x, y, entity, true);
						}
					}
					y += 1;
					if (y >= this.level.getOriginalEntities().length - 1) {
						y -= this.level.getOriginalEntities().length - 2;
						x += 1;
					}
				}
			}
			if (y >= this.level.getOriginalEntities().length - 1) {
				y -= this.level.getOriginalEntities().length - 2;
				x += 1;
			}
		}
	}
	
	private String getStringHorizontal(boolean bFirst) {
		String horizontal = "";
		int lastEntity = -1;
		int count = -1;
		for (int i = 1; i < this.level.getOriginalEntities().length - 1; i++) {
			for (int j = 1; j < this.level.getOriginalEntities()[0].length - 1; j++) {
				if (count == -1) {
					count = 1;
					lastEntity = this.level.getLevel()[i][j];
				} else {
					int thisEntity = this.level.getLevel()[i][j];
					if (!bFirst) {
						if ((thisEntity == ApoStarzConstants.STAR) || (thisEntity == ApoStarzConstants.GOAL)) {
							thisEntity = lastEntity;
						}
					}
					if (thisEntity != lastEntity) {
						if (count < 2) {
							horizontal += Character.toString((char)(lastEntity + 33));
						} else {
							horizontal += Character.toString((char)(count + 43)) + Character.toString((char)(lastEntity + 33));
						}
						count = 1;
						lastEntity = thisEntity;
					} else {
						count++;
					}
				}
			}
		}
		if (count > 1) {
			horizontal += Character.toString((char)(count + 43)) + Character.toString((char)(lastEntity + 33));
		} else {
			horizontal += Character.toString((char)(lastEntity + 33));
		}
		return horizontal;
	}

	private void setHorizontal(final String horizontalString, final boolean bFirst) {
		int position = 0;
		int x = 1;
		int y = 1;
		while (position < horizontalString.length()) {
			char c = horizontalString.charAt(position);
			if (c < 43) {
				if (bFirst) {
					this.level.addEntity(x, y, c - 33, true);
				} else {
					if (this.level.getEntities()[y][x] == null) {
						this.level.addEntity(x, y, c - 33, true);
					}
				}
				position += 1;
				x++;
			} else {
				int size = c - 43;
				int entity = horizontalString.charAt(position + 1) - 33;
				//System.out.println("hori size = "+size+" entity = "+entity);
				position += 2;
				for (int i = 0; i < size; i++) {
					if (bFirst) {
						this.level.addEntity(x, y, entity, true);
					} else {
						if (this.level.getEntities()[y][x] == null) {
							if (!(this.level.getEntities()[y][x] instanceof ApoStarzStar)) {
								this.level.addEntity(x, y, entity, true);
							}
						}
					}
					x += 1;
					if (x >= this.level.getOriginalEntities()[0].length - 1) {
						x -= this.level.getOriginalEntities()[0].length - 2;
						y++;
					}
				}
			}
			if (x >= this.level.getOriginalEntities()[0].length - 1) {
				x -= this.level.getOriginalEntities()[0].length - 2;
				y++;
			}
		}
	}
	
}
