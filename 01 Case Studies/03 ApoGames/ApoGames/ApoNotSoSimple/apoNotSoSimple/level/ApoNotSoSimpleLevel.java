package apoNotSoSimple.level;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.apogames.ApoConstants;

import apoNotSoSimple.ApoNotSoSimpleConstants;
import apoNotSoSimple.ApoNotSoSimpleImages;
import apoNotSoSimple.entity.ApoNotSoSimpleEntity;
import apoNotSoSimple.entity.ApoNotSoSimplePlayer;

public class ApoNotSoSimpleLevel {
	
	private String levelName;
	
	private String[] instructions;
	
	private byte[][][] level;
	
	private ArrayList<ApoNotSoSimpleEntity>[] entities;
	
	private ApoNotSoSimplePlayer[] players;
	
	private boolean[] visibleEntities;
	
	private boolean bShouldTest;
	
	private boolean bCheat;
	
	private boolean bShouldChange;
	
	public ApoNotSoSimpleLevel(final byte[][][] level, final String[] instructions, final String levelName) {
		this.level = level;
		this.instructions = instructions;
		this.levelName = levelName;
		this.bShouldChange = false;
		this.bCheat = false;
		this.restart();
	}
	
	public String getLevelName() {
		return this.levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String[] getInstructions() {
		return this.instructions;
	}

	public void setInstructions(String[] instructions) {
		this.instructions = instructions;
	}

	public byte[][][] getLevel() {
		return this.level;
	}

	public void setLevel(byte[][][] level) {
		this.level = level;
	}

	public boolean isCheat() {
		return this.bCheat;
	}

	public void setCheat(boolean cheat) {
		this.bCheat = cheat;
	}

	@SuppressWarnings("unchecked")
	public void restart() {
		this.entities = new ArrayList[this.level.length];
		this.players = new ApoNotSoSimplePlayer[this.level.length];
		this.visibleEntities = new boolean[this.level.length];
		for (int i = 0; i < this.level.length; i++) {
			this.visibleEntities[i] = true;
			this.entities[i] = new ArrayList<ApoNotSoSimpleEntity>();
			for (int y = 0; y < this.level[0].length; y++) {
				for (int x = 0; x < this.level[0][0].length; x++) {
					if (this.level[i][y][x] == ApoNotSoSimpleConstants.LEVEL_FIXED) {
						this.entities[i].add(new ApoNotSoSimpleEntity(ApoNotSoSimpleImages.ORIGINAL_FIXED, x * ApoNotSoSimpleConstants.TILE_SIZE, y * ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.PLAYER_DIRECTION_NO_MOVEMENT, true));
					} else if (this.level[i][y][x] == ApoNotSoSimpleConstants.LEVEL_UP) {
						this.entities[i].add(new ApoNotSoSimpleEntity(ApoNotSoSimpleImages.ORIGINAL_UP, x * ApoNotSoSimpleConstants.TILE_SIZE, y * ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.PLAYER_DIRECTION_UP, true));
					} else if (this.level[i][y][x] == ApoNotSoSimpleConstants.LEVEL_DOWN) {
						this.entities[i].add(new ApoNotSoSimpleEntity(ApoNotSoSimpleImages.ORIGINAL_DOWN, x * ApoNotSoSimpleConstants.TILE_SIZE, y * ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.PLAYER_DIRECTION_DOWN, true));
					} else if (this.level[i][y][x] == ApoNotSoSimpleConstants.LEVEL_RIGHT) {
						this.entities[i].add(new ApoNotSoSimpleEntity(ApoNotSoSimpleImages.ORIGINAL_RIGHT, x * ApoNotSoSimpleConstants.TILE_SIZE, y * ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.PLAYER_DIRECTION_RIGHT, true));
					} else if (this.level[i][y][x] == ApoNotSoSimpleConstants.LEVEL_LEFT) {
						this.entities[i].add(new ApoNotSoSimpleEntity(ApoNotSoSimpleImages.ORIGINAL_LEFT, x * ApoNotSoSimpleConstants.TILE_SIZE, y * ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.PLAYER_DIRECTION_LEFT, true));
					} else if (this.level[i][y][x] == ApoNotSoSimpleConstants.LEVEL_PLAYER) {
						this.players[i] = new ApoNotSoSimplePlayer(ApoNotSoSimpleImages.ORIGINAL_PLAYER, x * ApoNotSoSimpleConstants.TILE_SIZE, y * ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.TILE_SIZE);
					} else if (this.level[i][y][x] == ApoNotSoSimpleConstants.LEVEL_FINISH) {
						this.entities[i].add(new ApoNotSoSimpleEntity(ApoNotSoSimpleImages.ORIGINAL_FINISH, x * ApoNotSoSimpleConstants.TILE_SIZE, y * ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.PLAYER_DIRECTION_FINISH, true));
					} else if (this.level[i][y][x] == ApoNotSoSimpleConstants.LEVEL_VISIBLE_TRUE) {
						this.entities[i].add(new ApoNotSoSimpleEntity(ApoNotSoSimpleImages.ORIGINAL_VISIBLE_TRUE, x * ApoNotSoSimpleConstants.TILE_SIZE, y * ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.PLAYER_DIRECTION_CHANGEVISIBLE_UP, true));
					} else if (this.level[i][y][x] == ApoNotSoSimpleConstants.LEVEL_VISIBLE_FALSE) {
						this.entities[i].add(new ApoNotSoSimpleEntity(ApoNotSoSimpleImages.ORIGINAL_VISIBLE_FALSE, x * ApoNotSoSimpleConstants.TILE_SIZE, y * ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.PLAYER_DIRECTION_CHANGEVISIBLE_UP, false));
					} else if (this.level[i][y][x] == ApoNotSoSimpleConstants.LEVEL_STEP) {
						this.entities[i].add(new ApoNotSoSimpleEntity(ApoNotSoSimpleImages.ORIGINAL_STEP, x * ApoNotSoSimpleConstants.TILE_SIZE, y * ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.PLAYER_DIRECTION_STEP, true));
					} else if (this.level[i][y][x] == ApoNotSoSimpleConstants.LEVEL_STEP_FINISH) {
						this.entities[i].add(new ApoNotSoSimpleEntity(ApoNotSoSimpleImages.ORIGINAL_STEP_FINISH, x * ApoNotSoSimpleConstants.TILE_SIZE, y * ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.TILE_SIZE, ApoNotSoSimpleConstants.PLAYER_DIRECTION_STEP_FINISH, true));
					}
				}
			}
		}
		this.bShouldTest = false;
	}
	
	public int getLayer() {
		return this.entities.length;
	}
	
	public boolean changeDirection(int direction) {
		boolean bChange = false;
		if ((direction == ApoNotSoSimpleConstants.PLAYER_DIRECTION_LEFT) ||
			(direction == ApoNotSoSimpleConstants.PLAYER_DIRECTION_RIGHT) ||
			(direction == ApoNotSoSimpleConstants.PLAYER_DIRECTION_UP) ||
			(direction == ApoNotSoSimpleConstants.PLAYER_DIRECTION_DOWN)) {
			for (int i = 0; i < this.entities.length; i++) {
				if (this.visibleEntities[i]) {
					if (this.players[i] != null) {
						if (this.players[i].isBVisible()) {
							if (this.players[i].isMoving()) {
								return false;
							}
							this.players[i].setNextDirection(direction);
							this.players[i].moveNextDirection();
							if (this.players[i].isMoving()) {
								bChange = true;
							}
						}
					}
					if (bChange) {
						this.bShouldChange = false;
						for (int e = 0; e < this.entities[i].size(); e++) {
							this.entities[i].get(e).setMoving();
							if ((direction == ApoNotSoSimpleConstants.PLAYER_DIRECTION_UP) ||
								(direction == ApoNotSoSimpleConstants.PLAYER_DIRECTION_DOWN)) {
								if ((this.entities[i].get(e).getFixedMovement() == ApoNotSoSimpleConstants.PLAYER_DIRECTION_UP) ||
									(this.entities[i].get(e).getFixedMovement() == ApoNotSoSimpleConstants.PLAYER_DIRECTION_DOWN) ||
									(this.entities[i].get(e).getFixedMovement() == ApoNotSoSimpleConstants.PLAYER_DIRECTION_CHANGEVISIBLE_UP)) {
									this.entities[i].get(e).moveNextDirection();
								}
							}
							if ((direction == ApoNotSoSimpleConstants.PLAYER_DIRECTION_LEFT) ||
								(direction == ApoNotSoSimpleConstants.PLAYER_DIRECTION_RIGHT)) {
								if ((this.entities[i].get(e).getFixedMovement() == ApoNotSoSimpleConstants.PLAYER_DIRECTION_LEFT) ||
									(this.entities[i].get(e).getFixedMovement() == ApoNotSoSimpleConstants.PLAYER_DIRECTION_RIGHT)) {
									this.entities[i].get(e).moveNextDirection();
								}
							}
						}
					}
				}
			}
			return bChange;
		}
		return false;
	}
	
	public boolean think(int delta) {
		boolean bMove = false;
		this.bShouldTest = false;
		if (this.entities != null) {
			for (int i = 0; i < this.entities.length; i++) {
				if (this.visibleEntities[i]) {
					for (int j = 0; j < this.entities[i].size(); j++) {
						this.entities[i].get(j).think(delta);
					}
					if (this.players[i] != null) {
						boolean bMoveOld = this.players[i].isMoving();
						this.players[i].think(delta);
						if (this.players[i].isMoving()) {
							bMove = true;
						}
						if (bMoveOld != this.players[i].isMoving()) {
							this.bShouldTest = true;
						}
					}
				}
			}
		}
		return bMove;
	}
	
	public void changeLevelVisible(int level) {
		this.visibleEntities[level] = false;
	}
	
	public boolean isLayerVisible(int layer) {
		return this.visibleEntities[layer];
	}
	
	public boolean isVisible() {
		for (int i = 0; i < this.visibleEntities.length; i++) {
			if (this.visibleEntities[i]) {
				return true;
			}
		}
		return false;
	}
	
	public boolean shouldChange() {
		return this.bShouldChange;
	}

	public int[][] checkMovement(int delta) {
		int[][] result = new int[this.players.length][this.entities.length];
		for (int p = 0; p < result.length; p++) {
			for (int e = 0; e < result[0].length; e++) {
				result[p][e] = -1;
			}
		}
		if (!this.bShouldTest) {
			return result;
		} else {
//			for (int p = 0; p < this.players.length; p++) {
				for (int i = 0; i < this.entities.length; i++) {
					if (this.visibleEntities[i]) {
						for (int size = 0; size < this.entities[i].size(); size++) {
							if ((this.entities[i].get(size).isBVisible()) && (!this.entities[i].get(size).isMoving())) {
								if (this.players[i].intersects(this.entities[i].get(size))) {
									if (this.entities[i].get(size).getFixedMovement() == ApoNotSoSimpleConstants.PLAYER_DIRECTION_FINISH) {
//										if (i == i) {
											result[i][i] = this.entities[i].get(size).getFixedMovement();											
//										}
									} else if (this.entities[i].get(size).getFixedMovement() == ApoNotSoSimpleConstants.PLAYER_DIRECTION_STEP) {
//										if (i == i) {
											this.entities[i].get(size).setBVisible(false);
											this.changeVisibleStepFinish();
//										}
									} else {
										result[i][i] = this.entities[i].get(size).getFixedMovement();
									}
								}
							}
						}
					}
//				}
			}
		}
		return result;
	}
	
	private void changeVisibleStepFinish() {
		for (int i = 0; i < this.entities.length; i++) {
			for (int size = 0; size < this.entities[i].size(); size++) {
				if (this.entities[i].get(size).getFixedMovement() == ApoNotSoSimpleConstants.PLAYER_DIRECTION_STEP_FINISH) {
					this.entities[i].get(size).setBVisible(!this.entities[i].get(size).isBVisible());	
				}
			}
		}
	}
	
	public int getChangeY() {
		int changeY = ApoConstants.GAME_HEIGHT/2 - ApoNotSoSimpleConstants.LEVEL_HEIGHT/2;
		if (this.entities.length == 2) {
			changeY -= (int)(2.5 * ApoNotSoSimpleConstants.TILE_SIZE);
		}
		return changeY;
	}
	
	public boolean shouldVisibilityChange() {
		return bShouldChange;
	}

	public void renderBackground(Graphics2D g, int changeX, int changeY) {
		changeY += this.getChangeY();
		for (int i = 0; i < this.entities.length; i++) {
			Paint paint = g.getPaint();
			GradientPaint grafientPaint = new GradientPaint(8, changeY - 2, Color.LIGHT_GRAY, 8, changeY - 2 + ApoNotSoSimpleConstants.LEVEL_HEIGHT/2, new Color(230, 230, 230), true);
			g.setPaint(grafientPaint);
			g.fillRect(8, changeY - 2, ApoNotSoSimpleConstants.LEVEL_WIDTH + 4, ApoNotSoSimpleConstants.LEVEL_HEIGHT + 4);
			
			g.setPaint(paint);
			g.setColor(Color.BLACK);
			g.setStroke(new BasicStroke(5));
			g.drawRoundRect(6, changeY - 4, ApoNotSoSimpleConstants.LEVEL_WIDTH + 8, ApoNotSoSimpleConstants.LEVEL_HEIGHT + 8, 15, 15);
			
			changeY += (int)(5 * ApoNotSoSimpleConstants.TILE_SIZE);
		}
	}

	public void render(Graphics2D g, int changeX, int changeY, boolean bEditor) {
		int realChangeY = changeY;
		final Shape clip = g.getClip();
		if (this.entities != null) {
			changeY += this.getChangeY();
			final int startChangeY = changeY;
			for (int i = 0; i < this.entities.length; i++) {
				if (this.visibleEntities[i]) {
					if ((this.isCheat()) && (i > 0)) {
						changeY = startChangeY;
					}
					Shape recClip = new Rectangle2D.Double(10, changeY, ApoNotSoSimpleConstants.LEVEL_WIDTH, ApoNotSoSimpleConstants.LEVEL_HEIGHT);
					g.setClip(recClip);
					Point[][] levelArray = new Point[3][10];
					for (int j = 0; j < this.entities[i].size(); j++) {
						if (this.entities[i].get(j).getFixedMovement() == ApoNotSoSimpleConstants.PLAYER_DIRECTION_NO_MOVEMENT) {
							this.entities[i].get(j).render(g, 10, changeY);
							levelArray[(int)(this.entities[i].get(j).getY()/ApoNotSoSimpleConstants.TILE_SIZE)][(int)(this.entities[i].get(j).getX()/ApoNotSoSimpleConstants.TILE_SIZE)] = new Point(i, j);
						}
					}
					for (int j = 0; j < this.entities[i].size(); j++) {
						if (this.entities[i].get(j).getFixedMovement() != ApoNotSoSimpleConstants.PLAYER_DIRECTION_NO_MOVEMENT) {
							boolean bVisible = this.entities[i].get(j).isBVisible();
							if (bEditor) {
								this.entities[i].get(j).setBVisible(true);
							}
							if (this.entities[i].get(j).isBVisible()) {
								boolean bClip = false;
								if ((!this.entities[i].get(j).isMoving()) && (!this.entities[i].get(j).isAnotherEntityOver())) {
									if (levelArray[(int)(this.entities[i].get(j).getY()/ApoNotSoSimpleConstants.TILE_SIZE)][(int)(this.entities[i].get(j).getX()/ApoNotSoSimpleConstants.TILE_SIZE)] != null) {
										Point p = levelArray[(int)(this.entities[i].get(j).getY()/ApoNotSoSimpleConstants.TILE_SIZE)][(int)(this.entities[i].get(j).getX()/ApoNotSoSimpleConstants.TILE_SIZE)];
										if (((this.entities[p.x].get(p.y).getFixedMovement() == ApoNotSoSimpleConstants.PLAYER_DIRECTION_UP) && (this.entities[i].get(j).getFixedMovement() == ApoNotSoSimpleConstants.PLAYER_DIRECTION_DOWN)) ||
											((this.entities[p.x].get(p.y).getFixedMovement() == ApoNotSoSimpleConstants.PLAYER_DIRECTION_DOWN) && (this.entities[i].get(j).getFixedMovement() == ApoNotSoSimpleConstants.PLAYER_DIRECTION_UP))) {
											this.entities[p.x].get(p.y).setOverAnotherEntity(0);
											this.entities[i].get(j).setOverAnotherEntity(ApoNotSoSimpleEntity.MAX_TIME);
											this.bShouldChange = true;
										}
									}
								}
//								if (!this.entities[i].get(j).isMoving()) {
//									this.bShouldChange = true;
//									
//									if (levelArray[(int)(this.entities[i].get(j).getY()/ApoNotSoSimpleConstants.TILE_SIZE)][(int)(this.entities[i].get(j).getX()/ApoNotSoSimpleConstants.TILE_SIZE)] > 0) {
//										g.setClip(new Rectangle2D.Double(this.entities[i].get(j).getX() + 10 , this.entities[i].get(j).getY() + changeY, ApoNotSoSimpleConstants.TILE_SIZE/2, ApoNotSoSimpleConstants.TILE_SIZE));
//										bClip = true;
//									}
//								}
								this.entities[i].get(j).render(g, 10, changeY);
								if (levelArray[(int)(this.entities[i].get(j).getY()/ApoNotSoSimpleConstants.TILE_SIZE)][(int)(this.entities[i].get(j).getX()/ApoNotSoSimpleConstants.TILE_SIZE)] != null) {
									levelArray[(int)(this.entities[i].get(j).getY()/ApoNotSoSimpleConstants.TILE_SIZE)][(int)(this.entities[i].get(j).getX()/ApoNotSoSimpleConstants.TILE_SIZE)].y = j;									
								} else {
									levelArray[(int)(this.entities[i].get(j).getY()/ApoNotSoSimpleConstants.TILE_SIZE)][(int)(this.entities[i].get(j).getX()/ApoNotSoSimpleConstants.TILE_SIZE)] = new Point(i, j);
								}
								if (bClip) {
									g.setClip(recClip);
								}
							}
							this.entities[i].get(j).setBVisible(bVisible);
						}
					}
					if (this.players[i] != null) {
						this.players[i].render(g, 10, changeY);
					}
					g.setClip(clip);
				}
				changeY += (int)(5 * ApoNotSoSimpleConstants.TILE_SIZE);
			}
		}
		changeY = realChangeY;
		if (this.instructions != null) {
			// render instructions
			changeY += ApoConstants.GAME_HEIGHT/2 - ApoNotSoSimpleConstants.LEVEL_HEIGHT/2;
			if (this.entities.length == 2) {
				changeY -= (int)(2.5 * ApoNotSoSimpleConstants.TILE_SIZE);
			}
			g.setColor(Color.BLACK);
			int add = (int)(4 * ApoNotSoSimpleConstants.TILE_SIZE);
			if (this.instructions.length == 3) {
				add = (int)(4.5 * ApoNotSoSimpleConstants.TILE_SIZE);
			}
			g.setFont(ApoNotSoSimpleConstants.FONT_LEVELNAME);
			if (this.instructions.length == 2) {
				String s = this.levelName;
				int w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, ApoNotSoSimpleConstants.LEVEL_WIDTH/2 + 10 - w/2, 50); 
			}
			g.setFont(ApoNotSoSimpleConstants.FONT_INSTRUCTIONS);
			for (int i = 0; i < this.instructions.length; i++) {
				String s = this.instructions[i];
				if (s != null) {
					int w = g.getFontMetrics().stringWidth(s);
					g.drawString(s, ApoNotSoSimpleConstants.LEVEL_WIDTH/2 + 10 - w/2, changeY - 14); 
				}
				changeY += add;
			}
		}
	}
}
