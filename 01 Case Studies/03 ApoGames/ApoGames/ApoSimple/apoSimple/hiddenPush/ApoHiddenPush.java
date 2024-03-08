package apoSimple.hiddenPush;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import apoSimple.ApoSimpleConstants;
import apoSimple.ApoSimpleImages;
import apoSimple.game.ApoSimpleButtonNot;
import apoSimple.game.ApoSimpleGame;
import apoSimple.game.ApoSimpleModel;
import apoSimple.game.ApoSimplePanel;

public class ApoHiddenPush extends ApoSimpleModel {

	public static final String MENU = "credits_menu";
	
	public static final int MAX_LEVELUP_TIME = 2500;

	private BufferedImage iBackground;
	
	private BufferedImage iBackgroundScore;
	
	private boolean bAnalysis;
	
	private int points;
	
	private int steps;
	
	private int level;
	
	private final Rectangle2D.Float rec = new Rectangle2D.Float(10 + 1.5f * 65, 10 + 1.5f * 65, 260, 260);

	private final Rectangle2D.Float bigrec = new Rectangle2D.Float(10, 10, 455, 455);
	
	private final boolean[][] bClear = new boolean[4][4];
	
	private final ApoHiddenPushEntity[][] playGame = new ApoHiddenPushEntity[4][4];
	
	private ApoHiddenPushEntity addEntity;
	
	private int newEntity;
	
	private boolean bMove;
	
	private int mouseX, mouseY;
	
	private int levelUpTime;
	
	private boolean bLevelUp;
	
	private int curGames;
	
	private int pointsOverall;
	
	private int pointsBest;
	
	public ApoHiddenPush(ApoSimplePanel game) {
		super(game);

		this.curGames = 0;
		this.pointsOverall = 0;
		this.pointsBest = 0;
	}
	
	@Override
	public void init() {
		if (this.iBackground == null) {
			this.iBackground = this.getGame().getImages().getImage("images/game.png", false);
			this.makeBackgroundImage();
		}
		this.loadLevel(0);
		this.getBackgroundScore();
		this.getGame().getButtons()[28].setX(this.getGame().getButtons()[28].getX() + 130);
		this.winGame();
		this.curGames = 0;
		this.points = -1;
		
		this.getGame().getButtons()[68].setBVisible(false);
		this.getGame().getButtons()[70].setBVisible(false);
	}
	
	private void makeBackgroundImage() {
		Graphics2D g = this.iBackground.createGraphics();
		
		g.setColor(new Color(133, 209, 2));
		g.fillRect(0, 0, 470, 480);
		
		for (int i = 0; i < 2; i++) {
			g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(55, 0, 85, 65), (int)(rec.x + (i+1)*65 - 10),	(int)(rec.y + (0)*65) - 10, null);
			g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(55, 130, 85, 65), (int)(rec.x + (i+1)*65 - 10), (int)(rec.y + (3)*65) + 10, null);
			
			g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(0, 55, 65, 85), (int)(rec.x - 10),	(int)(rec.y + (i + 1)*65 - 10), null);	
			g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(130, 55, 65, 85), (int)(rec.x + 5 + 3 * 65),	(int)(rec.y + (i + 1)*65 - 10), null);	
		}
		g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(0, 0, 65, 65), (int)(rec.x + (0)*65 - 10),	(int)(rec.y + (0)*65) - 10, null);		
		g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(130, 0, 65, 65), (int)(rec.x + (3)*65 + 5),	(int)(rec.y + (0)*65) - 10, null);	

		g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(0, 130, 65, 65), (int)(rec.x + (0)*65 - 10),	(int)(rec.y + (3)*65) + 10, null);		
		g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(130, 130, 65, 65), (int)(rec.x + (3)*65  + 5),	(int)(rec.y + (3)*65) + 10, null);		

		
		g.dispose();
	}
	
	private void loadLevel(int level) {
		this.bAnalysis = false;
		this.level = level;
		if (level == 0) {
			this.steps = 0;
			this.points = 0;
		} else {
			this.points += level * 100;
		}
		this.getGame().getButtons()[28].setBVisible(false);
		
		for (int y = 0; y < this.bClear.length; y++) {
			for (int x = 0; x < this.bClear[0].length; x++) {
				this.bClear[y][x] = false;				
			}
		}
		for (int y = 0; y < this.playGame.length; y++) {
			for (int x = 0; x < this.playGame[0].length; x++) {
				this.playGame[y][x] = null;				
			}
		}
		this.newEntity = this.getNextRandomEntity(this.level);
		
		this.addEntity = new ApoHiddenPushEntity(-65, 0, 60, 60, this.getDirectionForEntity(this.newEntity));
		
		this.newEntity = this.getNextRandomEntity(this.level);
		this.bMove = false;
		
		this.levelUpTime = 0;
		this.bLevelUp = false;
	}
	
	private int getDirectionForEntity(int entity) {
		if (entity == 0) {
			return ApoSimpleConstants.DOWN;
		} else if (entity == 1) {
			return ApoSimpleConstants.BLACK_RIGHT;
		} else if (entity == 2) {
			return ApoSimpleConstants.HIDDEN_DUCK;
		} else if (entity == 3) {
			return ApoSimpleConstants.DOG_DOWN;
		} else if (entity == 4) {
			return ApoSimpleConstants.STONE_3;
		} else if (entity == 5) {
			return ApoSimpleConstants.HIDDEN_RABBIT;
		} else if (entity == 6) {
			return ApoSimpleConstants.HIDDEN_DUCK_2;
		} else if (entity == 7) {
			return ApoSimpleConstants.DOUBLE_UP;
		} else if (entity == 8) {
			return ApoSimpleConstants.DOUBLE_BLACK_LEFT;
		} else if (entity == 9) {
			return ApoSimpleConstants.LEFT;
		} else if (entity == 10) {
			return ApoSimpleConstants.BLACK_DOWN;
		} else if (entity == 11) {
			return ApoSimpleConstants.STONE_1;
		}
		return ApoSimpleConstants.RIGHT;
	}
	
	private int getNextRandomEntity(int level) {
		int max = level + 4;
		if (max > 12) {
			max = 12;
		}
		return (int)(Math.random() * max);
	}
	
	public ApoHiddenPushEntity[][] getPlayGame() {
		return this.playGame;
	}

	private void winGame() {
		this.bAnalysis = true;
		this.getGame().getButtons()[28].setBVisible(true);
		
		this.curGames += 1;
		if (this.points > this.pointsBest) {
			this.pointsBest = this.points;
		}
		this.pointsOverall += this.points;
	}
	
	public Rectangle2D.Float getRec() {
		return this.rec;
	}

	public BufferedImage getBackgroundScore() {
		if (this.iBackgroundScore == null) {
			this.iBackgroundScore = this.getGame().getImages().getImage("images/game_real.png", false);
			
			Color c = new Color(133, 209, 2);
			Color c2 = new Color(200, 255, 108);
			for (int y = 0; y < this.iBackgroundScore.getHeight(); y++) {
				for (int x = 0; x < this.iBackgroundScore.getWidth(); x++) {
					int rgb = this.iBackgroundScore.getRGB(x, y);
					if (rgb == c.getRGB())
					this.iBackgroundScore.setRGB(x, y, c2.getRGB());
				}
			}
		}
		return this.iBackgroundScore;
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			if (this.bAnalysis) {
				super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
				super.getGame().getButtons()[28].setX(this.getGame().getButtons()[28].getX() - 130);
				super.getGame().setCredits();				
			} else {
				this.winGame();
			}
		} else if (button == KeyEvent.VK_ENTER) {
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
		if (function.equals(ApoSimpleGame.MENU)) {
			this.getGame().getButtons()[28].setX(this.getGame().getButtons()[28].getX() - 130);
			this.getGame().setCredits();
		}else if (function.equals(ApoSimpleGame.MUSIC)) {
			ApoSimpleButtonNot b = (ApoSimpleButtonNot)(this.getGame().getButtons()[53]);
			b.setBActive(!b.isBActive());
			this.getGame().setbSoundMusic(b.isBActive());
		} else if (function.equals(ApoSimpleGame.SOUND)) {
			ApoSimpleButtonNot b = (ApoSimpleButtonNot)(this.getGame().getButtons()[54]);
			b.setBActive(!b.isBActive());
			this.getGame().setbSoundEffects(b.isBActive());
		} else if (function.equals(ApoSimpleGame.RESTART)) {
			this.loadLevel(0);
		} else if (function.equals(ApoSimpleGame.RELOAD)) {
			this.loadLevel(0);
		}
	}
	
	@Override
	public void mouseButtonReleased(int x, int y, boolean bRight) {
		if ((!this.bMove) && (this.bigrec.contains(x, y))) {
			int curX = (int)(this.addEntity.getX() / 65);
			int curY = (int)(this.addEntity.getY() / 65);

			if (this.prepareToMove(curX, curY)) {
				this.bMove = true;
				this.steps += 1;
				this.points += 1 + this.level;
			} else {
				this.bMove = false;
			}
		}
	}
	
	public boolean prepareToMove(int x, int y) {
		int count = 0;
		if (this.addEntity.getX() < 0) {
			for (int endX = this.playGame[y].length - 1; endX >= 0; endX--) {
				if (this.playGame[y][endX] == null) {
					count += 1;
				} else if (count > 0) {
					this.playGame[y][endX + count] = this.playGame[y][endX];
					this.playGame[y][endX + count].setX(endX * 65);
					this.playGame[y][endX + count].setY(y * 65);
					this.playGame[y][endX] = null;
					this.playGame[y][endX + count].setNextGoal(count, 0);
				}
			}
			if (count <= 0) {
				return false;
			} else {
				this.playGame[y][count - 1] = this.addEntity;
				this.playGame[y][count - 1].setNextGoal(count, 0);
				this.addEntity = null;
				return true;
			}
		} else if (this.addEntity.getX() >= 4 * 65) {
			for (int endX = 0; endX < this.playGame[y].length; endX++) {
				if (this.playGame[y][endX] == null) {
					count += 1;
				} else if (count > 0) {
					this.playGame[y][endX - count] = this.playGame[y][endX];
					this.playGame[y][endX - count].setX(endX * 65);
					this.playGame[y][endX - count].setY(y * 65);
					this.playGame[y][endX] = null;
					this.playGame[y][endX - count].setNextGoal(-count, 0);
				}
			}
			if (count <= 0) {
				return false;
			} else {
				this.playGame[y][this.playGame[y].length - count] = this.addEntity;
				this.playGame[y][this.playGame[y].length - count].setNextGoal(-count, 0);
				this.addEntity = null;
				return true;
			}
		} else if (this.addEntity.getY() < 0) {
			for (int endY = this.playGame.length - 1; endY >= 0; endY--) {
				if (this.playGame[endY][x] == null) {
					count += 1;
				} else if (count > 0) {
					this.playGame[endY + count][x] = this.playGame[endY][x];
					this.playGame[endY + count][x].setX(x * 65);
					this.playGame[endY + count][x].setY(endY * 65);
					this.playGame[endY][x] = null;
					this.playGame[endY + count][x].setNextGoal(0, count);
				}
			}
			if (count <= 0) {
				return false;
			} else {
				this.playGame[count - 1][x] = this.addEntity;
				this.playGame[count - 1][x].setNextGoal(0, count);
				this.addEntity = null;
				return true;
			}
		} else if (this.addEntity.getY() >= 4 * 65) {
			for (int endY = 0; endY < this.playGame.length; endY++) {
				if (this.playGame[endY][x] == null) {
					count += 1;
				} else if (count > 0) {
					this.playGame[endY - count][x] = this.playGame[endY][x];
					this.playGame[endY - count][x].setX(x * 65);
					this.playGame[endY - count][x].setY(endY * 65);
					this.playGame[endY][x] = null;
					this.playGame[endY - count][x].setNextGoal(0, -count);
				}
			}
			if (count <= 0) {
				return false;
			} else {
				this.playGame[this.playGame.length - count][x] = this.addEntity;
				this.playGame[this.playGame.length - count][x].setNextGoal(0, -count);
				this.addEntity = null;
				return true;
			}
		}
		return true;
	}
	
	public boolean mousePressed(int x, int y, boolean bRight) {
		return true;
	}
	
	public boolean mouseDragged(int x, int y) {
		this.mouseMoved(x, y);
		return true;
	}
	
	public boolean mouseMoved(int x, int y) {
		if (!this.bAnalysis) {
			this.mouseX = x;
			this.mouseY = y;
			if (!this.bMove) {
				if (this.bigrec.contains(x, y)) {
					if (x < this.rec.x) {
						this.addEntity.setX(-65);
						if (y < this.rec.y + 1 * 65) {
							this.addEntity.setY(0);
						} else if (y < this.rec.y + 2 * 65) {
							this.addEntity.setY(1 * 65);
						} else if (y < this.rec.y + 3 * 65) {
							this.addEntity.setY(2 * 65);
						} else {
							this.addEntity.setY(3 * 65);
						}
					} else if (x > this.rec.x + this.rec.width) {
						this.addEntity.setX(4 * 65);
						if (y < this.rec.y + 1 * 65) {
							this.addEntity.setY(0);
						} else if (y < this.rec.y + 2 * 65) {
							this.addEntity.setY(1 * 65);
						} else if (y < this.rec.y + 3 * 65) {
							this.addEntity.setY(2 * 65);
						} else {
							this.addEntity.setY(3 * 65);
						}
					} else if (y < ApoSimpleConstants.GAME_HEIGHT/2) {
						this.addEntity.setY(-65);
						if (x < this.rec.x + 1 * 65) {
							this.addEntity.setX(0);
						} else if (x < this.rec.x + 2 * 65) {
							this.addEntity.setX(1 * 65);
						} else if (x < this.rec.x + 3 * 65) {
							this.addEntity.setX(2 * 65);
						} else {
							this.addEntity.setX(3 * 65);
						}
					} else {
						this.addEntity.setY(4 * 65);
						if (x < this.rec.x + 1 * 65) {
							this.addEntity.setX(0);
						} else if (x < this.rec.x + 2 * 65) {
							this.addEntity.setX(1 * 65);
						} else if (x < this.rec.x + 3 * 65) {
							this.addEntity.setX(2 * 65);
						} else {
							this.addEntity.setX(3 * 65);
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public void think(int delta) {
		if (this.bLevelUp) {
			this.levelUpTime += delta;
			if (this.levelUpTime >= ApoHiddenPush.MAX_LEVELUP_TIME) {
				this.bLevelUp = false;
				this.loadLevel(this.level + 1);
			}
		} else if (!this.bAnalysis) {
			boolean bMove = false;
			boolean bDelete = false;
			boolean bShouldDelete = false;
			for (int y = 0; y < this.playGame.length; y++) {
				for (int x = 0; x < this.playGame[0].length; x++) {
					if (this.playGame[y][x] != null) {
						this.playGame[y][x].think(delta);
						if ((this.playGame[y][x] != null) && (this.playGame[y][x].isDeleted())) {
							this.playGame[y][x] = null;
							bDelete = true;
						}
						if ((this.playGame[y][x] != null) && (this.playGame[y][x].isShouldDeleted())) {
							bShouldDelete = true;
						}
						if ((this.playGame[y][x] != null) && (!this.playGame[y][x].hasReachedTheEnd()) && (!this.playGame[y][x].isShouldDeleted())) {
							bMove = true;
						}
					}
				}
			}
			if ((this.bMove) && (bDelete)) {
				this.checkLevel();
			} else if ((this.bMove) && (!bMove) && (!bShouldDelete)) {
				this.checkLevel();
			}
		}
	}
	
	private void checkLevel() {		
		final boolean[][] bDelete = new boolean[4][4];
		for (int y = 0; y < this.playGame.length; y++) {
			for (int x = 0; x < this.playGame[0].length; x++) {
				if (this.playGame[y][x] != null) {
					int direction = this.playGame[y][x].getDirection();
					
					if (x - 2 >= 0) {
						if ((this.playGame[y][x - 2] != null) && 
							(this.playGame[y][x - 2].getDirection() == direction) &&
							(this.playGame[y][x - 1] != null) &&
							(this.playGame[y][x - 1].getDirection() == direction)) {
							bDelete[y][x-2] = true;
							bDelete[y][x-1] = true;
							bDelete[y][x] = true;
							this.points += 10 + this.level * 2;
						}
					}
					if ((x - 1 >= 0) && (x + 1 < this.playGame[0].length)) {
						if ((this.playGame[y][x - 1] != null) &&
							(this.playGame[y][x - 1].getDirection() == direction) &&
							(this.playGame[y][x + 1] != null) &&
							(this.playGame[y][x + 1].getDirection() == direction)) {
							bDelete[y][x+1] = true;
							bDelete[y][x-1] = true;
							bDelete[y][x] = true;
							this.points += 10 + this.level * 2;
						}
					}
					if ((x + 2 < this.playGame[0].length)) {
						if ((this.playGame[y][x + 2] != null) &&
							(this.playGame[y][x + 2].getDirection() == direction) &&
							(this.playGame[y][x + 1] != null) &&
							(this.playGame[y][x + 1].getDirection() == direction)) {
							bDelete[y][x+2] = true;
							bDelete[y][x+1] = true;
							bDelete[y][x] = true;
							this.points += 10 + this.level * 2;
						}
					}
					
					if (y - 2 >= 0) {
						if ((this.playGame[y - 2][x] != null) &&
							(this.playGame[y - 2][x].getDirection() == direction) &&
							(this.playGame[y - 1][x] != null) &&
							(this.playGame[y - 1][x].getDirection() == direction)) {
							bDelete[y-2][x] = true;
							bDelete[y-1][x] = true;
							bDelete[y][x] = true;
							this.points += 10 + this.level * 2;
						}
					}
					if ((y - 1 >= 0) && (y + 1 < this.playGame.length)) {
						if ((this.playGame[y - 1][x] != null) &&
							(this.playGame[y - 1][x].getDirection() == direction) &&
							(this.playGame[y + 1][x] != null) &&
							(this.playGame[y + 1][x].getDirection() == direction)) {
							bDelete[y+1][x] = true;
							bDelete[y-1][x] = true;
							bDelete[y][x] = true;
							this.points += 10 + this.level * 2;
						}
					}
					if ((y + 2 < this.playGame.length)) {
						if ((this.playGame[y + 2][x] != null) &&
							(this.playGame[y + 2][x].getDirection() == direction) &&
							(this.playGame[y + 1][x] != null) &&
							(this.playGame[y + 1][x].getDirection() == direction)) {
							bDelete[y+2][x] = true;
							bDelete[y+1][x] = true;
							bDelete[y][x] = true;
							this.points += 10 + this.level * 2;
						}
					}
				}
			}
		}
		
		boolean bDeleteEntity = false;
		for (int y = 0; y < bDelete.length; y++) {
			for (int x = 0; x < bDelete[0].length; x++) {
				if (bDelete[y][x]) {
					this.bClear[y][x] = true;
					this.playGame[y][x].setShouldDeleted(true);
					bDeleteEntity = true;
				}
			}
		}
		
		if (bDeleteEntity) {
			return;
		}
		
		boolean bBreak = false;
		for (int y = 0; y < this.playGame.length; y++) {
			if (bBreak) {
				break;
			}
			for (int x = 0; x < this.playGame[0].length; x++) {
				if (bBreak) {
					break;
				} else {
					if (this.playGame[y][x] == null) {
						bBreak = true;
						break;
					}
				}
			}
		}
		
		if (!bBreak) {
			this.winGame();
			return;
		}
		
		for (int y = 0; y < this.bClear.length; y++) {
			for (int x = 0; x < this.bClear[0].length; x++) {
				if (!this.bClear[y][x]) {
					this.bMove = false;
					
					this.addEntity = new ApoHiddenPushEntity(-65, 0, 60, 60, this.getDirectionForEntity(this.newEntity));
					this.mouseMoved(this.mouseX, this.mouseY);
					
					this.newEntity = this.getNextRandomEntity(this.level);
					
					return;
				}
			}
		}
		
		this.bLevelUp = true;
		this.levelUpTime = 0;
	}
	
	@Override
	public void render(Graphics2D g) {
		if (this.iBackground != null) {
			g.drawImage(this.iBackground, 0, 0, null);

			for (int y = 0; y < this.bClear.length; y++) {
				for (int x = 0; x < this.bClear[0].length; x++) {
					if (!this.bClear[y][x]) {
						g.drawImage(ApoSimpleImages.ORIGINAL_GREEN, (int)(this.rec.x + x * 65), (int)(this.rec.y + y * 65), null);
					} else {
						g.drawImage(ApoSimpleImages.ORIGINAL, (int)(this.rec.x + x * 65), (int)(this.rec.y + y * 65), null);
					}
				}
			}
			
			for (int y = 0; y < this.playGame.length; y++) {
				for (int x = 0; x < this.playGame[0].length; x++) {
					if (this.playGame[y][x] != null) {
						this.playGame[y][x].render(g, -(int)(this.rec.x), -(int)(this.rec.y));
					}
				}
			}
			
			if (this.addEntity != null) {
				this.addEntity.render(g, -(int)(this.rec.x), -(int)(this.rec.y));
			}
			this.drawLevelInfo(g);
			this.drawMoves(g);
			this.drawPoints(g);
			
			this.drawLevelUp(g);
		}
	}
	
	private void drawLevelUp(Graphics2D g) {
		if (this.bLevelUp) {
			int width = 300;
			int height = 100;
			int x = 230 - width/2;
			int y = 240 - height/2;
			g.setColor(Color.WHITE);
			g.fillRoundRect(x, y, width, height, 10, 10);
			g.setColor(Color.BLACK);
			g.drawRoundRect(x, y, width, height, 10, 10);
			
			g.setFont(ApoSimpleGame.FONT_RESTART);
			String s = "Congratulation!";
			int w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, x + width/2 - w/2, y + 30);
			
			g.drawImage(ApoSimplePanel.iStar, x + width/2 - ApoSimplePanel.iStar.getWidth() - w/2 - 10, y + 5, null);
			g.drawImage(ApoSimplePanel.iStar, x + width/2 + w/2 + 10, y + 5, null);
			
			s = "More tiles will appear.";
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, x + width/2 - w/2, y + 85);
		}
	}
	
	private void drawAnalysis(Graphics2D g) {
		int width = (int)(ApoSimpleConstants.ENTITY_WIDTH + 5) * 7;
		int height = (int)(350);
		int x = ApoSimpleConstants.GAME_WIDTH/2 - width/2;
		int y = ApoSimpleConstants.GAME_HEIGHT/2 - height/2;
		
		g.drawImage(this.iBackgroundScore, x, y, null);
		g.setColor(Color.BLACK);
		g.setFont(ApoSimpleGame.FONT_STATICS);
		if (this.points >= 0) {
			String s = "You got "+this.points+" points.";
			int w = g.getFontMetrics().stringWidth(s);
			ApoSimpleGame.drawString(g, s, x + width/2 - w/2, y + 60);
		}
		
		g.setColor(Color.BLACK);
		g.setFont(ApoSimpleGame.FONT_RESTART);
		
		if (ApoSimpleConstants.REGION.equals("de")) {
			String s = "Eure Aufgabe ist es, das Feld";
			int w = g.getFontMetrics().stringWidth(s);
			ApoSimpleGame.drawString(g, s, x + width/2 - w/2, y + 95);
			
			s = "von den Blumen zu säubern.";
			w = g.getFontMetrics().stringWidth(s);
			ApoSimpleGame.drawString(g, s, x + width/2 - w/2, y + 117);
			
			s = "Werden 3 oder mehr Tiere zusammengefügt,";
			w = g.getFontMetrics().stringWidth(s);
			ApoSimpleGame.drawString(g, s, x + width/2 - w/2, y + 139);
			
			s = "dann essen Sie die Blumen und verschwinden!";
			w = g.getFontMetrics().stringWidth(s);
			ApoSimpleGame.drawString(g, s, x + width/2 - w/2, y + 161);
			
			s = "Steuert das Spiel mit der Maus & bringt die";
			w = g.getFontMetrics().stringWidth(s);
			ApoSimpleGame.drawString(g, s, x + width/2 - w/2, y + 185);
			
			s = "Tiere von der Seite des Feldes ins Spiel.";
			w = g.getFontMetrics().stringWidth(s);
			ApoSimpleGame.drawString(g, s, x + width/2 - w/2, y + 207);
		} else {
			String s = "Your task is to clear the";
			int w = g.getFontMetrics().stringWidth(s);
			ApoSimpleGame.drawString(g, s, x + width/2 - w/2, y + 95);
			
			s = "field from the flowers.";
			w = g.getFontMetrics().stringWidth(s);
			ApoSimpleGame.drawString(g, s, x + width/2 - w/2, y + 117);
			
			s = "Match 3 or more animals to remove";
			w = g.getFontMetrics().stringWidth(s);
			ApoSimpleGame.drawString(g, s, x + width/2 - w/2, y + 139);
			
			s = "the animals and the flowers!";
			w = g.getFontMetrics().stringWidth(s);
			ApoSimpleGame.drawString(g, s, x + width/2 - w/2, y + 161);
			
			s = "Push the animals from up, down, left";
			w = g.getFontMetrics().stringWidth(s);
			ApoSimpleGame.drawString(g, s, x + width/2 - w/2, y + 185);
			
			s = "or right into the field.";
			w = g.getFontMetrics().stringWidth(s);
			ApoSimpleGame.drawString(g, s, x + width/2 - w/2, y + 207);
		}
		if (this.curGames > 0) {
			String s = "Best result";
			int w = g.getFontMetrics().stringWidth(s);
			ApoSimpleGame.drawString(g, s, x + width/2 - w/2 - 150, y + 250);
			
			s = String.valueOf(this.pointsBest);
			w = g.getFontMetrics().stringWidth(s);
			ApoSimpleGame.drawString(g, s, x + width/2 - w/2 - 150, y + 285);
			
			s = "Average score";
			w = g.getFontMetrics().stringWidth(s);
			ApoSimpleGame.drawString(g, s, x + width/2 - w/2 + 150, y + 250);
			
			s = String.valueOf(this.pointsOverall/this.curGames);
			w = g.getFontMetrics().stringWidth(s);
			ApoSimpleGame.drawString(g, s, x + width/2 - w/2 + 150, y + 285);
		}
		
		this.getGame().getButtons()[28].render(g, 0, 0);
	}
	
	public void drawLevelInfo(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.setFont(ApoSimpleGame.FONT_RESTART);
		String s = String.valueOf(this.level + 1);
		int w = g.getFontMetrics().stringWidth(s);
		int x = 552;
		int y = 193;
		ApoSimpleGame.drawString(g, s, x - w/2, y);
	}
	
	public void drawMoves(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.setFont(ApoSimpleGame.FONT_RESTART);
		String s = String.valueOf(this.steps);
		int w = g.getFontMetrics().stringWidth(s);
		int x = 561;
		int y = 245;
		ApoSimpleGame.drawString(g, s, x - w/2, y);
		
		g.setFont(ApoSimpleGame.FONT_BUTTON);
		x = 535;
		y = 258;
		
		int direction = getDirectionForEntity(this.newEntity);
		if (direction == ApoSimpleConstants.DOWN) {
			g.drawImage(ApoSimpleImages.ORIGINAL_DOWN, x, y, null);
		} else if (direction == ApoSimpleConstants.BLACK_RIGHT) {
			g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_LEFT, x, y, null);
		} else if (direction == ApoSimpleConstants.HIDDEN_DUCK) {
			g.drawImage(ApoSimpleImages.HIDDEN_DUCK, x, y, null);
		} else if (direction == ApoSimpleConstants.HIDDEN_RABBIT) {
			g.drawImage(ApoSimpleImages.HIDDEN_RABBIT, x, y, null);
		} else if (direction == ApoSimpleConstants.HIDDEN_DUCK_2) {
			g.drawImage(ApoSimpleImages.HIDDEN_DUCK_2, x, y, null);
		} else if (direction == ApoSimpleConstants.DOG_DOWN) {
			g.drawImage(ApoSimpleImages.ORIGINAL_DOG_DOWN, x, y, null);
		} else if (direction == ApoSimpleConstants.STONE_1) {
			g.drawImage(ApoSimpleImages.STONE_1, x, y, null);
		} else if (direction == ApoSimpleConstants.STONE_2) {
			g.drawImage(ApoSimpleImages.STONE_2, x, y, null);
		} else if (direction == ApoSimpleConstants.STONE_3) {
			g.drawImage(ApoSimpleImages.STONE_3, x, y, null);
		} else if (direction == ApoSimpleConstants.DOUBLE_UP) {
			g.drawImage(ApoSimpleImages.ORIGINAL_ABOVE, x - 5, y - 5, null);
			g.drawImage(ApoSimpleImages.ORIGINAL_ABOVE, x + 5, y + 5, null);
		} else if (direction == ApoSimpleConstants.DOUBLE_BLACK_LEFT) {
			g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_RIGHT, x - 5, y - 5, null);
			g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_RIGHT, x + 5, y + 5, null);
		} else if (direction == ApoSimpleConstants.LEFT) {
			g.drawImage(ApoSimpleImages.ORIGINAL_RIGHT, x, y, null);
		} else if (direction == ApoSimpleConstants.BLACK_DOWN) {
			g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_DOWN, x, y, null);
		}
	}
	
	public void drawOverlay(Graphics2D g) {
		this.drawCoins(g);
		this.drawMusicOver(g);
		this.drawEffectOver(g);
		this.drawNoticeOver(g);
		this.drawReloadOver(g);
		
		if (this.bAnalysis) {
			this.drawAnalysis(g);
		}
	}
	
	public void drawMusicOver(Graphics2D g) {
		this.getGame().renderOver(g, ApoSimpleConstants.OVER_MUSIC, 53);
	}
	
	public void drawEffectOver(Graphics2D g) {
		this.getGame().renderOver(g, ApoSimpleConstants.OVER_EFFECTS, 54);
	}
	
	public void drawNoticeOver(Graphics2D g) {
		this.getGame().renderOver(g, ApoSimpleConstants.OVER_NOTICE, 39);
	}
	
	public void drawReloadOver(Graphics2D g) {
		this.getGame().renderOver(g, ApoSimpleConstants.OVER_RELOAD, 40);
	}
	
	public void drawCoins(Graphics2D g) {
		int y = 354;
		g.setColor(Color.BLACK);
		g.setFont(ApoSimpleGame.FONT_COIN);
		String s = String.valueOf(this.getGame().getCoins());
		int w = g.getFontMetrics().stringWidth(s);
		int x = 625;
		int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
		ApoSimpleGame.drawString(g, s, x - w, y + ApoSimpleImages.COINS.getHeight()/2 + h/2);
	}
	
	public void drawPoints(Graphics2D g) {
		if (this.points >= 0) {
			int y = 147;
			g.drawImage(ApoSimpleImages.GAME_POINTS, 510, y - 40, null);
			g.setColor(Color.BLACK);
			g.setFont(ApoSimpleGame.FONT_RESTART);
			String s = String.valueOf(this.points);
			int w = g.getFontMetrics().stringWidth(s);
			int x = 565;
			ApoSimpleGame.drawString(g, s, x - w/2, y);
		}
	}
	
}