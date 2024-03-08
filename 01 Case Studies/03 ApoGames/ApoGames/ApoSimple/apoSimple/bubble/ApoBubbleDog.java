package apoSimple.bubble;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.apogames.entity.ApoEntity;

public class ApoBubbleDog extends ApoEntity {

	private final Color bubbleColor = new Color(255, 210, 240, 180);
	private final Color bubbleColorWall = new Color(255, 127, 210);
	
	private final Color bubbleColorBlue = new Color(76, 255, 255, 180);
	private final Color bubbleColorWallBlue = new Color(0, 127, 127);
	
	private ArrayList<Point> bubbles;
	
	private final Point startPoint;
	
	private int[][] curLevel;
	
	private boolean[][] isBubble;
	
	private int count;
	
	private int curCount;
	
	public ApoBubbleDog(BufferedImage iBackground, float x, float y, float width, float height) {
		super(null, x, y, width, height);
		
		BufferedImage iImage = new BufferedImage(iBackground.getWidth()/2, iBackground.getHeight()/2, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = iImage.createGraphics();
		g.drawImage(iBackground.getScaledInstance(iBackground.getWidth()/2, iBackground.getHeight()/2, BufferedImage.SCALE_SMOOTH), 0, 0, null);
		g.dispose();
		
		this.setIBackground(iImage);
		
		this.startPoint = new Point((int)((x - 10) / ApoBubbleGame.ENTITY_SIZE), (int)((y - 10) / ApoBubbleGame.ENTITY_SIZE));
		this.clearBubbles(null);
		this.count = 10000;
	}
	
	public void init() {
		super.init();
		
		this.curCount = 0;
	}
	
	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	private void clearBubbles(ApoBubbleGame game) {
		if (this.bubbles == null) {
			this.bubbles = new ArrayList<Point>();
		} else {
			if (game != null) {
				for (int i = 0; i < this.bubbles.size(); i++) {
					if ((game.getSheep().isInBubble()) && (game.getSheep().isSheepHere(this.bubbles.get(i).x, this.bubbles.get(i).y))) {
						game.getSheep().setInBubble(false);
					}
					game.getCurLevel()[this.bubbles.get(i).y][this.bubbles.get(i).x] = ApoBubbleGame.EMPTY;
				}
				this.curLevel = game.getCurLevel();
			}
			this.bubbles.clear();
		}
		this.isBubble = new boolean[15][15];
		this.isBubble[this.startPoint.y][this.startPoint.x] = true;
		this.bubbles.add(this.startPoint);
		this.curCount = 0;
		if (game != null) {
			game.getCurLevel()[this.bubbles.get(0).y][this.bubbles.get(0).x] = ApoBubbleGame.BUBBLE;
			this.curLevel = game.getCurLevel();
		}
	}
	
	public boolean clickOnDog(ApoBubbleGame game, int levelX, int levelY) {
		int size = this.bubbles.size();
		boolean bClick = false;
		for (int i = 0; i < size; i++) {
			int x = this.bubbles.get(i).x;
			int y = this.bubbles.get(i).y;
			if ((x == levelX) && (y == levelY)) {
				bClick = true;
				break;
			}
		}
		
		if (bClick) {
			game.setMoves(game.getMoves() + 1);
			this.curCount += 1;
			if (this.curCount > this.count) {
				this.clearBubbles(game);
			} else {
				Point p = game.getSheep().getSheepPoint();
				p.y += 1;
				for (int i = 0; i < size; i++) {
					int x = this.bubbles.get(i).x;
					int y = this.bubbles.get(i).y;
					if ((x == p.x) && (y == p.y)) {
						if (y - 1 >= 0) {
							if (game.getCurLevel()[y - 1][x] == ApoBubbleGame.EMPTY) {
								this.sheepCheck(game, x, y - 1, 0, -1);
								game.getCurLevel()[y - 1][x] = ApoBubbleGame.BUBBLE;
								this.bubbles.add(new Point(x, y - 1));
								this.isBubble[y - 1][x] = true;
							} else if (game.getCurLevel()[y - 1][x] == ApoBubbleGame.SPIKE) {
								this.clearBubbles(game);
								break;
							}
						}
					}
				}
				for (int i = 0; i < size; i++) {
					int x = this.bubbles.get(i).x;
					int y = this.bubbles.get(i).y;
					if (y - 1 >= 0) {
						if (game.getCurLevel()[y - 1][x] == ApoBubbleGame.EMPTY) {
							this.sheepCheck(game, x, y - 1, 0, -1);
							game.getCurLevel()[y - 1][x] = ApoBubbleGame.BUBBLE;
							this.bubbles.add(new Point(x, y - 1));
							this.isBubble[y - 1][x] = true;
						} else if (game.getCurLevel()[y - 1][x] == ApoBubbleGame.SPIKE) {
							this.clearBubbles(game);
							break;
						}
					}
					if (x + 1 < game.getCurLevel()[y].length) {
						if (game.getCurLevel()[y][x + 1] == ApoBubbleGame.EMPTY) {
							this.sheepCheck(game, x + 1, y, 1, 0);
							game.getCurLevel()[y][x + 1] = ApoBubbleGame.BUBBLE;
							this.bubbles.add(new Point(x + 1, y));
							this.isBubble[y][x + 1] = true;
						} else if (game.getCurLevel()[y][x + 1] == ApoBubbleGame.SPIKE) {
							this.clearBubbles(game);
							break;
						}
					}
					if (x - 1 >= 0) {
						if (game.getCurLevel()[y][x - 1] == ApoBubbleGame.EMPTY) {
							this.sheepCheck(game, x - 1, y, -1, 0);
							game.getCurLevel()[y][x - 1] = ApoBubbleGame.BUBBLE;
							this.bubbles.add(new Point(x - 1, y));
							this.isBubble[y][x - 1] = true;
						} else if (game.getCurLevel()[y][x - 1] == ApoBubbleGame.SPIKE) {
							this.clearBubbles(game);
							break;
						}
					}
					if ((y + 1 < game.getCurLevel().length)) {
						if (game.getCurLevel()[y + 1][x] == ApoBubbleGame.EMPTY) {
							this.sheepCheck(game, x, y + 1, 0, 1);
							game.getCurLevel()[y + 1][x] = ApoBubbleGame.BUBBLE;
							this.bubbles.add(new Point(x, y + 1));
							this.isBubble[y + 1][x] = true;
						} else if (game.getCurLevel()[y + 1][x] == ApoBubbleGame.SPIKE) {
							this.clearBubbles(game);
							break;
						}
					}
				}
			}
		}
		this.curLevel = game.getCurLevel();
		return bClick;
	}
	
	public void think(ApoBubbleGame game, int delta) {
		if (this.curLevel == null) {
			this.curLevel = game.getCurLevel();
		}
	}
	
	private void sheepCheck(ApoBubbleGame game, int x, int y, int addX, int addY) {
		if ((!game.getSheep().isInBubble()) && (game.getSheep().isSheepHere(x, y))) {
			if ((this.curLevel[y + addY][x + addX] != ApoBubbleGame.EMPTY) &&
				(this.curLevel[y + addY][x + addX] != ApoBubbleGame.GOAL) &&
				(this.curLevel[y + addY][x + addX] != ApoBubbleGame.SPIKE)) {
				game.getSheep().setInBubble(true);
			} else {
				game.getSheep().setX((x + addX) * ApoBubbleGame.ENTITY_SIZE + 10);
				game.getSheep().setY((y + addY) * ApoBubbleGame.ENTITY_SIZE + 10);
				game.getSheep().checkNextPosition(game, x + addX, y + addY);
			}
		}
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		super.render(g, changeX, changeY);
		try {
			if (this.curLevel != null) {
				for (int i = 0; i < this.bubbles.size(); i++) {
					g.setColor(this.bubbleColor);
					if (this.count < 20) {
						g.setColor(this.bubbleColorBlue);	
					}
					int size = 3;
					int x = 10 + changeX + this.bubbles.get(i).x * ApoBubbleGame.ENTITY_SIZE + size;
					int y = 10 + changeY + this.bubbles.get(i).y * ApoBubbleGame.ENTITY_SIZE + size;
					g.fillRect(x + changeX, y + changeY, ApoBubbleGame.ENTITY_SIZE - 2 * size, ApoBubbleGame.ENTITY_SIZE - 2 * size);
					boolean bLeftFree = true;
					if ((this.bubbles.get(i).x - 1 < 0) || (!this.isBubble[this.bubbles.get(i).y][this.bubbles.get(i).x - 1])) {
						bLeftFree = false;
					}
					boolean bRightFree = true;
					if ((this.bubbles.get(i).x + 1 >= this.isBubble[0].length) || (!this.isBubble[this.bubbles.get(i).y][this.bubbles.get(i).x + 1])) {
						bRightFree = false;
					}
					boolean bUpFree = true;
					if ((this.bubbles.get(i).y - 1 < 0) || (!this.isBubble[this.bubbles.get(i).y - 1][this.bubbles.get(i).x])) {
						bUpFree = false;
					}
					boolean bDownFree = true;
					if ((this.bubbles.get(i).y + 1 >= this.isBubble.length) || (!this.isBubble[this.bubbles.get(i).y + 1][this.bubbles.get(i).x])) {
						bDownFree = false;
					}
					g.setColor(this.bubbleColorWall);
					if (this.count < 20) {
						g.setColor(this.bubbleColorWallBlue);	
					}
					if (!bUpFree) {
						if (!bLeftFree) {
							g.fillRect(x, y - size, ApoBubbleGame.ENTITY_SIZE/2 - size, size);
						} else {
							g.fillRect(x - size, y - size, ApoBubbleGame.ENTITY_SIZE/2, size);
						}
						if (!bRightFree) {
							g.fillRect(x + ApoBubbleGame.ENTITY_SIZE/2 - size, y - size, ApoBubbleGame.ENTITY_SIZE/2 - size, size);
							g.setColor(Color.WHITE);
							g.fillRect(x + ApoBubbleGame.ENTITY_SIZE/2 - 1, y + 3, 7, 4);
							g.fillRect(x + ApoBubbleGame.ENTITY_SIZE/2 + 2, y + 7, 4, 4);
						} else {
							g.fillRect(x + ApoBubbleGame.ENTITY_SIZE/2 - size, y - size, ApoBubbleGame.ENTITY_SIZE/2, size);
						}
					} else {
						g.setColor(this.bubbleColor);
						if (this.count < 20) {
							g.setColor(this.bubbleColorBlue);	
						}
						g.fillRect(x - size, y - size, ApoBubbleGame.ENTITY_SIZE, size);
					}
					
					g.setColor(this.bubbleColorWall);
					if (this.count < 20) {
						g.setColor(this.bubbleColorWallBlue);	
					}
					if (!bDownFree) {
						if (!bLeftFree) {
							g.fillRect(x, y - size + ApoBubbleGame.ENTITY_SIZE - size, ApoBubbleGame.ENTITY_SIZE/2 - size, size);
						} else {
							g.fillRect(x - size, y - size + ApoBubbleGame.ENTITY_SIZE - size, ApoBubbleGame.ENTITY_SIZE/2, size);
						}
						if (!bRightFree) {
							g.fillRect(x + ApoBubbleGame.ENTITY_SIZE/2 - size, y - size + ApoBubbleGame.ENTITY_SIZE - size, ApoBubbleGame.ENTITY_SIZE/2 - size, size);
						} else {
							g.fillRect(x + ApoBubbleGame.ENTITY_SIZE/2 - size, y - size + ApoBubbleGame.ENTITY_SIZE - size, ApoBubbleGame.ENTITY_SIZE/2, size);
						}
					} else {
						g.setColor(this.bubbleColor);
						if (this.count < 20) {
							g.setColor(this.bubbleColorBlue);	
						}
						g.fillRect(x - size, y + ApoBubbleGame.ENTITY_SIZE - size - size, ApoBubbleGame.ENTITY_SIZE, size);
					}
					
					g.setColor(this.bubbleColorWall);
					if (this.count < 20) {
						g.setColor(this.bubbleColorWallBlue);	
					}
					if (!bLeftFree) {
						if (!bUpFree) {
							g.fillRect(x - size, y, size, ApoBubbleGame.ENTITY_SIZE/2 - size);
						} else {
							g.fillRect(x - size, y - size, size, ApoBubbleGame.ENTITY_SIZE/2);
						}
						if (!bDownFree) {
							g.fillRect(x - size, y + ApoBubbleGame.ENTITY_SIZE/2 - size, size, ApoBubbleGame.ENTITY_SIZE/2 - size);
						} else {
							g.fillRect(x - size, y + ApoBubbleGame.ENTITY_SIZE/2 - size, size, ApoBubbleGame.ENTITY_SIZE/2);
						}
					} else {
						g.setColor(this.bubbleColor);
						if (this.count < 20) {
							g.setColor(this.bubbleColorBlue);	
						}
						g.fillRect(x - size, y, size, ApoBubbleGame.ENTITY_SIZE - 2 * size);
					}
					
					
					g.setColor(this.bubbleColorWall);
					if (this.count < 20) {
						g.setColor(this.bubbleColorWallBlue);	
					}
					if (!bRightFree) {
						if (!bUpFree) {
							g.fillRect(x + ApoBubbleGame.ENTITY_SIZE - size - size, y, size, ApoBubbleGame.ENTITY_SIZE/2 - size);
						} else {
							g.fillRect(x + ApoBubbleGame.ENTITY_SIZE - size - size, y - size, size, ApoBubbleGame.ENTITY_SIZE/2);
						}
						if (!bDownFree) {
							g.fillRect(x + ApoBubbleGame.ENTITY_SIZE - size - size, y + ApoBubbleGame.ENTITY_SIZE/2 - size, size, ApoBubbleGame.ENTITY_SIZE/2 - size);
						} else {
							g.fillRect(x + ApoBubbleGame.ENTITY_SIZE - size - size, y + ApoBubbleGame.ENTITY_SIZE/2 - size, size, ApoBubbleGame.ENTITY_SIZE/2);
						}
					} else {
						g.setColor(this.bubbleColor);
						if (this.count < 20) {
							g.setColor(this.bubbleColorBlue);	
						}
						g.fillRect(x + ApoBubbleGame.ENTITY_SIZE - size - size, y, size, ApoBubbleGame.ENTITY_SIZE - 2 * size);
					}
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage()+" in BubbleDog draw bubbles");
		}
	}

}
