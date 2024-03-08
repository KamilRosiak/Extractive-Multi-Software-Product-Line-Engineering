package apoSimple.puzzle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import apoSimple.ApoSimpleConstants;
import apoSimple.ApoSimpleImages;
import apoSimple.game.ApoSimpleButtonNot;
import apoSimple.game.ApoSimpleGame;
import apoSimple.game.ApoSimpleModel;
import apoSimple.game.ApoSimplePanel;

public class ApoSimplePuzzleGame extends ApoSimpleModel {

	public static final String MENU = "credits_menu";

	private BufferedImage iBackground;
	
	private ApoSimplePuzzleLevel level;
	
	private int curLevel;
	
	private int curPressedX, curPressedY;
	
	private boolean bDelete, bMoving;
	
	private int steps;
	
	private int draggedX, draggedY;

	private int mouseX, mouseY;
	
	private int curPressedChangeX, curPressedChangeY;
	
	private BufferedImage iBackgroundScore;
	
	private boolean bAnalysis, bEnd;
	
	public ApoSimplePuzzleGame(ApoSimplePanel game) {
		super(game);
	}
	
	@Override
	public void init() {
		if (this.iBackground == null) {
			this.iBackground = this.getGame().getImages().getImage("images/game.png", false);
		}
		this.loadLevel(0);
		this.getBackgroundScore();
		this.getGame().getButtons()[28].setX(this.getGame().getButtons()[28].getX() + 130);
	}
	
	private void loadLevel(int level) {
		this.curLevel = level;
		if (this.curLevel < 0) {
			this.curLevel = ApoSimplePuzzleLevelMaker.MAX_LEVEL;
		}
		if (ApoSimplePuzzleLevelMaker.getLevel(this.curLevel) == null) {
			this.curLevel = 0;
		}
		this.level = ApoSimplePuzzleLevelMaker.getLevel(this.curLevel);
		this.bDelete = false;
		this.bMoving = false;
		this.curPressedX = -1;
		this.curPressedY = -1;
		this.draggedX = -1;
		this.draggedY = -1;
		this.steps = 0;
		this.bAnalysis = false;
		this.getGame().getButtons()[28].setBVisible(false);
		this.getGame().getButtons()[19].setBVisible(false);
	}
	
	public BufferedImage getBackgroundScore() {
		if (this.iBackgroundScore == null) {
			this.iBackgroundScore = this.getGame().getImages().getImage("images/game_real.png", false);
		}
		return this.iBackgroundScore;
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
			this.getGame().getButtons()[28].setX(this.getGame().getButtons()[28].getX() - 130);
			this.getGame().setCredits();
		} else if (button == KeyEvent.VK_R) {
			this.loadLevel(this.curLevel);
		} else if (button == KeyEvent.VK_N) {
			this.loadLevel(this.curLevel + 1);
		} else if (button == KeyEvent.VK_P) {
			this.loadLevel(this.curLevel - 1);
		} else if (button == KeyEvent.VK_ENTER) {
			if (this.bAnalysis) {
				if (this.steps <= this.level.getSteps()[1]) {
					this.loadLevel(this.curLevel + 1);
				} else {
					this.loadLevel(this.curLevel);
				}
			}
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
		} else if (function.equals(ApoSimpleGame.LEVEL_LEFT)) {
			this.loadLevel(this.curLevel-1);
		} else if (function.equals(ApoSimpleGame.LEVEL_RIGHT)) {
			this.loadLevel(this.curLevel+1);
		} else if (function.equals(ApoSimpleGame.RELOAD)) {
			this.loadLevel(this.curLevel);
		} else if (function.equals(ApoSimpleGame.RESTART)) {
			this.loadLevel(this.curLevel);
		} else if (function.equals(ApoSimpleGame.NEXT)) {
			this.loadLevel(this.curLevel + 1);
		}
	}
	
	@Override
	public void mouseButtonReleased(int x, int y, boolean bRight) {
		if ((this.curPressedX >= 0) && (this.curPressedY >= 0) && (!this.bAnalysis)) {
			int levelX = (x - 10) / 65;
			int levelY = (y - 10) / 65;
			if ((levelX >= 0) && (levelX < this.level.getLevel()[0].length) &&
				(levelY >= 0) && (levelY < this.level.getLevel().length)) {
				int changeX = Math.abs(this.curPressedX - levelX);
				int changeY = Math.abs(this.curPressedY - levelY);
				if ((changeX == 1) && (changeY == 0)) {
					if (this.level.getLevel()[this.curPressedY][levelX] == null) {
						this.level.getLevel()[this.curPressedY][levelX] = this.level.getLevel()[this.curPressedY][this.curPressedX];
						this.level.getLevel()[this.curPressedY][levelX].setX(65 * levelX);
						this.level.getLevel()[this.curPressedY][this.curPressedX] = null;
					} else {
						ApoSimplePuzzleEntity cloneEntity = this.level.getLevel()[this.curPressedY][this.curPressedX].getClone();
						this.level.getLevel()[this.curPressedY][this.curPressedX] = this.level.getLevel()[this.curPressedY][levelX];
						this.level.getLevel()[this.curPressedY][this.curPressedX].setX(this.curPressedX * 65);
						this.level.getLevel()[this.curPressedY][levelX] = cloneEntity;
						this.level.getLevel()[this.curPressedY][levelX].setX(levelX * 65);
					}
					this.steps += 1;
					this.checkLevel();
				} else if ((changeX == 0) && (changeY == 1)) {
					if (this.level.getLevel()[levelY][this.curPressedX] == null) {
						this.level.getLevel()[levelY][this.curPressedX] = this.level.getLevel()[this.curPressedY][this.curPressedX];
						this.level.getLevel()[levelY][this.curPressedX].setY(65 * levelY);
						this.level.getLevel()[this.curPressedY][this.curPressedX] = null;
						this.steps += 1;
					} else {
						ApoSimplePuzzleEntity cloneEntity = this.level.getLevel()[this.curPressedY][this.curPressedX].getClone();
						this.level.getLevel()[this.curPressedY][this.curPressedX] = this.level.getLevel()[levelY][this.curPressedX];
						this.level.getLevel()[this.curPressedY][this.curPressedX].setY(65 * this.curPressedY);
						this.level.getLevel()[levelY][this.curPressedX] = cloneEntity;
						this.level.getLevel()[levelY][this.curPressedX].setY(65 * levelY);
						this.steps += 1;
						this.checkLevel();
					}
				}
			}
		}
		this.curPressedX = -1;
		this.curPressedY = -1;
		this.draggedX = -1;
		this.draggedY = -1;
	}
	
	private void checkLevel() {
		this.bEnd = false;
		if (this.level != null) {
			for (int y = this.level.getLevel().length - 1; y >= 0; y--) {
				for (int x = this.level.getLevel()[y].length - 1; x >= 0; x--) {
					if (this.level.getLevel()[y][x] != null) {
						if (this.canBeDeleted(x, y)) {
							this.level.getLevel()[y][x].setbEnding(true);
							bEnd = true;
						}
					}
				}
			}
		}
	}
	
	private boolean canBeDeleted(int x, int y) {
		if (x - 2 >= 0) {
			if ((this.level.getLevel()[y][x - 2] != null) &&
				(this.level.getLevel()[y][x - 2].getDirection() == this.level.getLevel()[y][x].getDirection()) &&
				(this.level.getLevel()[y][x - 1] != null) &&
				(this.level.getLevel()[y][x - 1].getDirection() == this.level.getLevel()[y][x].getDirection())) {
				return true;
			}
		}
		if ((x - 1 >= 0) && (x + 1 < this.level.getLevel()[y].length)) {
			if ((this.level.getLevel()[y][x - 1] != null) &&
				(this.level.getLevel()[y][x - 1].getDirection() == this.level.getLevel()[y][x].getDirection()) &&
				(this.level.getLevel()[y][x + 1] != null) &&
				(this.level.getLevel()[y][x + 1].getDirection() == this.level.getLevel()[y][x].getDirection())) {
				return true;
			}
		}
		if ((x + 2 < this.level.getLevel()[y].length)) {
			if ((this.level.getLevel()[y][x + 2] != null) &&
				(this.level.getLevel()[y][x + 2].getDirection() == this.level.getLevel()[y][x].getDirection()) &&
				(this.level.getLevel()[y][x + 1] != null) &&
				(this.level.getLevel()[y][x + 1].getDirection() == this.level.getLevel()[y][x].getDirection())) {
				return true;
			}
		}
		
		if (y - 2 >= 0) {
			if ((this.level.getLevel()[y - 2][x] != null) &&
				(this.level.getLevel()[y - 2][x].getDirection() == this.level.getLevel()[y][x].getDirection()) &&
				(this.level.getLevel()[y - 1][x] != null) &&
				(this.level.getLevel()[y - 1][x].getDirection() == this.level.getLevel()[y][x].getDirection())) {
				return true;
			}
		}
		if ((y - 1 >= 0) && (y + 1 < this.level.getLevel().length)) {
			if ((this.level.getLevel()[y - 1][x] != null) &&
				(this.level.getLevel()[y - 1][x].getDirection() == this.level.getLevel()[y][x].getDirection()) &&
				(this.level.getLevel()[y + 1][x] != null) &&
				(this.level.getLevel()[y + 1][x].getDirection() == this.level.getLevel()[y][x].getDirection())) {
				return true;
			}
		}
		if ((y + 2 < this.level.getLevel().length)) {
			if ((this.level.getLevel()[y + 2][x] != null) &&
				(this.level.getLevel()[y + 2][x].getDirection() == this.level.getLevel()[y][x].getDirection()) &&
				(this.level.getLevel()[y + 1][x] != null) &&
				(this.level.getLevel()[y + 1][x].getDirection() == this.level.getLevel()[y][x].getDirection())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean mousePressed(int x, int y, boolean bRight) {
		if (this.curPressedX < 0) {
			if ((this.bDelete) || (this.bMoving)) {
				this.curPressedX = -1;
				this.curPressedY = -1;
				return true;
			}
			int levelX = (x - 10) / 65;
			int levelY = (y - 10) / 65;
			if ((levelX >= 0) && (levelX < this.level.getLevel()[0].length) &&
				(levelY >= 0) && (levelY < this.level.getLevel().length) &&
				(this.level.getLevel()[levelY][levelX] != null)) {
				this.curPressedX = levelX;
				this.curPressedY = levelY;
				this.curPressedChangeX = (x-10) - this.curPressedX * 65;
				this.curPressedChangeY = (y-10) - this.curPressedY * 65;
				this.draggedX = x;
				this.draggedY = y;
			} else {
				this.curPressedX = -1;
				this.curPressedY = -1;
			}
		}
		return true;
	}
	
	public boolean mouseDragged(int x, int y) {
		this.draggedX = x;
		this.draggedY = y;
		return true;
	}
	
	public boolean mouseMoved(int x, int y) {
		this.mouseX = (x - 10) / 65;
		this.mouseY = (y - 10) / 65;
		if ((this.mouseX < 0) || (this.mouseX >= this.level.getLevel()[0].length) ||
			(this.mouseY < 0) || (this.mouseY >= this.level.getLevel().length)) {
			this.mouseX = -1;
		}
		return false;
	}

	public ApoSimplePuzzleLevel getLevel() {
		return this.level;
	}

	@Override
	public void think(int delta) {
		if (this.level != null) {
			boolean bDeleteNew = false;
			boolean bMoving = false;
			for (int y = this.level.getLevel().length - 1; y >= 0; y--) {
				for (int x = this.level.getLevel()[y].length - 1; x >= 0; x--) {
					if (this.level.getLevel()[y][x] != null) {
						this.level.getLevel()[y][x].think(delta, this);
						if (this.level.getLevel()[y][x] != null) {
							if (!this.level.getLevel()[y][x].isBVisible()) {
								this.level.getLevel()[y][x] = null;
							} else if (this.level.getLevel()[y][x].isMoving()) {
								bMoving = true;
							} else if (this.level.getLevel()[y][x].isbEnding()) {
								bDeleteNew = true;
							}
						} else {
							bMoving = true;
						}
					}
				}
			}

			if ((!this.bDelete) && (bDeleteNew)) {
				this.bDelete = true;
			} else if ((this.bDelete) && (!bDeleteNew)) {
				this.bDelete = false;
				this.bEnd = false;
				return;
			}
			if ((!this.bMoving) && (bMoving)) {
				this.bEnd = true;
				this.bMoving = true;
			} else if ((this.bMoving) && (!bMoving)) {
				this.bMoving = false;
				this.checkLevel();
			}
			if (!this.bEnd) {
				this.bEnd = true;
				this.levelWin();
			}
		}
	}
	
	private void levelWin() {
		if (this.level != null) {
			for (int y = this.level.getLevel().length - 1; y >= 0; y--) {
				for (int x = this.level.getLevel()[y].length - 1; x >= 0; x--) {
					if (this.level.getLevel()[y][x] != null) {
						if (this.steps >= this.level.getSteps()[1]) {
							this.steps += 1;
							this.bAnalysis = true;
							this.getGame().getButtons()[28].setBVisible(true);
						}
						return;
					}
				}
			}
		}
		this.getGame().getButtons()[19].setBVisible(true);
		this.bAnalysis = true;
	}
	
	@Override
	public void render(Graphics2D g) {
		if (this.iBackground != null) {
			g.drawImage(this.iBackground, 0, 0, null);
			
			if (this.level != null) {
				int levelX = (this.draggedX - 10) / 65;
				int levelY = (this.draggedY - 10) / 65;
				int changeFromX = (this.draggedX - 10) - this.curPressedX * 65 - this.curPressedChangeX;
				int changeFromY = (this.draggedY - 10) - this.curPressedY * 65 - this.curPressedChangeY;
				int changeX = (levelX - this.curPressedX);
				int changeY = (levelY - this.curPressedY);
				
				if (Math.abs(changeFromX) >= Math.abs(changeFromY))  {
					if (changeFromX < 0) {
						levelX = this.curPressedX - 1;
						levelY = this.curPressedY;
					} else {
						levelX = this.curPressedX + 1;
						levelY = this.curPressedY;
					}
				} else {
					if (changeFromY < 0) {
						levelX = this.curPressedX;
						levelY = this.curPressedY - 1;
					} else {
						levelX = this.curPressedX;
						levelY = this.curPressedY + 1;
					}
				}
				
				for (int y = this.level.getLevel().length - 1; y >= 0; y--) {
					for (int x = this.level.getLevel()[y].length - 1; x >= 0; x--) {
						if (this.level.getLevel()[y][x] != null) {
							if ((this.curPressedX != -1) && (this.curPressedX == x) && (this.curPressedY == y)) {// && (((changeX == 0) && (Math.abs(changeY) <= 1)) || ((Math.abs(changeX) <= 1) && (Math.abs(changeY) == 0)))) {
							} else if ((this.curPressedX != -1) && (levelX == x) && (levelY == y) && (((changeX == 0) && (Math.abs(changeY) == 1)) || ((Math.abs(changeX) <= 1) && (Math.abs(changeY) == 0)))) {
								
							} else {
								this.level.getLevel()[y][x].render(g, -10, -10);
							}
						}
					}
				}
				if ((this.curPressedX != -1)) {
					if (Math.abs(changeFromX) >= Math.abs(changeFromY))  {
						if (((changeX == 0) && (Math.abs(changeY) <= 1)) || ((Math.abs(changeX) <= 1) && (Math.abs(changeY) == 0))) {
							this.level.getLevel()[this.curPressedY][this.curPressedX].render(g, -10 - changeFromX, -10);
							this.level.getLevel()[this.curPressedY][this.curPressedX].renderOver(g, -10 - changeFromX, -10);
						} else {
							this.level.getLevel()[this.curPressedY][this.curPressedX].render(g, -10, -10);
							this.level.getLevel()[this.curPressedY][this.curPressedX].renderOver(g, -10, -10);
						}
					} else {
						if (((changeX == 0) && (Math.abs(changeY) <= 1)) || ((Math.abs(changeX) <= 1) && (Math.abs(changeY) == 0))) {
							this.level.getLevel()[this.curPressedY][this.curPressedX].render(g, -10, -10 - changeFromY);
							this.level.getLevel()[this.curPressedY][this.curPressedX].renderOver(g, -10, -10 - changeFromY);
						} else {
							this.level.getLevel()[this.curPressedY][this.curPressedX].render(g, -10, -10);
							this.level.getLevel()[this.curPressedY][this.curPressedX].renderOver(g, -10, -10);
						}
					}

					if ((levelY >= 0) && (levelY < this.level.getLevel().length) && 
						(levelX >= 0) && (levelX < this.level.getLevel()[0].length) &&
						(this.level.getLevel()[levelY][levelX] != null)) {
						if ((((changeX == 0) && (Math.abs(changeY) <= 1)) || ((Math.abs(changeX) <= 1) && (Math.abs(changeY) == 0)))) {
							if (Math.abs(changeFromX) >= Math.abs(changeFromY))  {
								this.level.getLevel()[levelY][levelX].render(g, -10 + changeFromX, -10);
							} else {
								this.level.getLevel()[levelY][levelX].render(g, -10, -10 + changeFromY);
							}
						} else {
							this.level.getLevel()[levelY][levelX].render(g, -10, -10);
						}
					}
				} else {
					if ((!this.bMoving) && (!this.bDelete) && (this.mouseX >= 0)) {
						if (this.level.getLevel()[this.mouseY][this.mouseX] != null) {
							this.level.getLevel()[this.mouseY][this.mouseX].renderOver(g, -10, -10);
						}
					}
				}
			}
			this.drawLevelInfo(g);
			this.drawMoves(g);
			
			this.drawLevelTutorial(g);
		}
	}
	
	private void drawAnalysis(Graphics2D g) {
		int width = (int)(ApoSimpleConstants.ENTITY_WIDTH + 5) * 7;
		int height = (int)(350);
		int x = ApoSimpleConstants.GAME_WIDTH/2 - width/2;
		int y = ApoSimpleConstants.GAME_HEIGHT/2 - height/2;
		
		g.drawImage(this.iBackgroundScore, x, y, null);
		
		if (this.steps > this.level.getSteps()[1]) {
			g.setFont(ApoSimpleGame.FONT_RESTART);
			String s = "No steps left.";
			if (ApoSimpleConstants.REGION.equals("de")) {
				s = "Es verbleiben leider keine Schritte mehr.";
			}
			int w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, x + width/2 - w/2, y + 55);
			
			s = "Max step count in this level is "+this.level.getSteps()[1];
			if (ApoSimpleConstants.REGION.equals("de")) {
				s = "Die maximale Schrittanzahl: "+this.level.getSteps()[1];
			}
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, x + width/2 - w/2, y + 90);
			
			g.setFont(ApoSimpleGame.FONT_TEXTFIELD);
			s = "Please maeh again";
			if (ApoSimpleConstants.REGION.equals("de")) {
				s = "Bitte versuchs nochmal!";
			}
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, x + width/2 - w/2, y + 160);
			
			g.drawImage(ApoSimpleImages.ORIGINAL_SORRY, x + 20, y + 125, null);
			g.drawImage(ApoSimpleImages.ORIGINAL_SORRY, x + width - ApoSimpleImages.ORIGINAL_SORRY.getWidth() - 20, y + 125, null);
		} else if (this.steps <= this.level.getSteps()[1]) {
			g.setColor(Color.BLACK);
			g.setFont(ApoSimpleGame.FONT_STATICS);
			String s = "";
			if (this.level.getSteps()[0] < this.steps) {
				s = "'ok'-result";
			} else {
				s = "PERFECT";
			}
			int w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, x + width/2 - w/2, y + 60);
			
			g.setFont(ApoSimpleGame.FONT_RESTART);
			if (ApoSimpleConstants.REGION.equals("de")) {
				if (this.steps > 1) {
					s = "Du hast das Level in "+String.valueOf(this.steps) + " Schritten gelöst!";
				} else {
					s = "Du hast das Level in "+String.valueOf(this.steps) + " Schritt gelöst!";
				}
			} else {
				s = "You solved the level in "+String.valueOf(this.steps) + " step";

				if (this.steps > 1) {
					s += "s";
				}
			}
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, x + width/2 - w/2, y + 110);
			
			int myY = 210;
			if (this.level.getSteps()[0] < this.steps) {
				g.drawImage(ApoSimplePanel.iStar, x + width/2 - ApoSimplePanel.iStar.getWidth()/2, myY, null);
			} else {
				g.drawImage(ApoSimplePanel.iStar, x + width/2 - ApoSimplePanel.iStar.getWidth()/2, myY, null);
				g.drawImage(ApoSimplePanel.iStar, x + width/2 - 10 - 3 * ApoSimplePanel.iStar.getWidth()/2, myY - 10, null);
				g.drawImage(ApoSimplePanel.iStar, x + width/2 + 10 + 1 * ApoSimplePanel.iStar.getWidth()/2, myY - 10, null);
			}
		}
		
		this.getGame().getButtons()[19].render(g, 0, 0);
		this.getGame().getButtons()[28].render(g, 0, 0);
	}
	
	public void drawLevelInfo(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.setFont(ApoSimpleGame.FONT_RESTART);
		String s = String.valueOf(this.curLevel + 1) + " / "+String.valueOf(ApoSimplePuzzleLevelMaker.MAX_LEVEL + 1);
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
		y = 278;
		
		g.setColor(Color.BLACK);
		if (this.level != null) {
			
			if (this.steps <= this.level.getSteps()[0]) {
				g.drawImage(ApoSimplePanel.iStarLittle, x, y - 16, null);
				g.drawImage(ApoSimplePanel.iStarLittle, x + 10, y - 16, null);
				g.drawImage(ApoSimplePanel.iStarLittle, x + 20, y - 16, null);
				ApoSimpleGame.drawString(g, String.valueOf(this.level.getSteps()[0]), x + 50, y);
			}

			y += 25;
			g.drawImage(ApoSimplePanel.iStarLittle, x, y - 16, null);
			ApoSimpleGame.drawString(g, String.valueOf(this.level.getSteps()[1]), x + 50, y);
		}
	}
	
	public void drawLevelTutorial(Graphics2D g) {
		int width = 480;
		g.setColor(Color.BLACK);
		g.setFont(ApoSimpleGame.FONT_RESTART);
		if (this.curLevel == 0) {			
			String[] tut = new String[] {
					"Your task: clear the field!",
					"Match 3 or more animals",
					"and they can escape.",
					"Drag an aninmal to the next position."
			};
			if (ApoSimpleConstants.REGION.equals("de")) {
				tut = new String[] {
						"Euer Ziel bei diesem Spiel ist einfach.",
						"3 oder mehr gleiche Tiere zusammenbringen",
						"und schon sind sie frei und verschwinden.",
						"Dazu ziehe einfach ein Tier mit der Maus."
				};
			}
			
			int maxY = 20 + 25 * tut.length;
			this.drawTutorial(g, maxY);
			
			for (int i = 0; i < tut.length; i++) {
				String s = tut[i];
				int w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, width/2 - w/2, 45 + i * 25);
			}
		} else if (this.curLevel == 1) {			
			String[] tut = new String[] {
					"If the field under an animal is free",
					"the animal will fall.",
					"Try it out now."
			};
			if (ApoSimpleConstants.REGION.equals("de")) {
				tut = new String[] {
						"Wenn unter einem Tier frei ist,",
						"fällt es runter.",
						"Versuche es hier gleich aus."
				};
			}
			
			int maxY = 20 + 25 * tut.length;
			this.drawTutorial(g, maxY);
			
			for (int i = 0; i < tut.length; i++) {
				String s = tut[i];
				int w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, width/2 - w/2, 45 + i * 25);
			}
		} else if (this.curLevel == 2) {			
			String[] tut = new String[] {
					"If you match more than 3 animals,",
					"All the animals will be removed at once."
			};
			if (ApoSimpleConstants.REGION.equals("de")) {
				tut = new String[] {
						"Wenn ihr mehr als 3 Tiere zusammenbringt,",
						"dann entkommen sie gleichzeitig!"
				};
			}
			
			int maxY = 20 + 25 * tut.length;
			this.drawTutorial(g, maxY);
			
			for (int i = 0; i < tut.length; i++) {
				String s = tut[i];
				int w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, width/2 - w/2, 45 + i * 25);
			}
		} else if (this.curLevel == 3) {			
			String[] tut = new String[] {
					"You can swap two connected animals.",
					"Try it out now."
			};
			if (ApoSimpleConstants.REGION.equals("de")) {
				tut = new String[] {
						"In diesem Level ergibt es Sinn,",
						"einfach mal 2 Tiere ihr Positionen",
						"tauschen zu lassen."
				};
			}
			
			int maxY = 20 + 25 * tut.length;
			this.drawTutorial(g, maxY);
			
			for (int i = 0; i < tut.length; i++) {
				String s = tut[i];
				int w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, width/2 - w/2, 45 + i * 25);
			}
		}
	}
	
	public void drawTutorial(Graphics2D g, int maxY) {
		g.drawImage(ApoSimplePanel.iTutAbove, 0, 0, null);
		int max = (maxY - ApoSimplePanel.iTutAbove.getHeight()) / ApoSimplePanel.iTutMiddle.getHeight();
		for (int i = 0; i < max; i++) {
			g.drawImage(ApoSimplePanel.iTutMiddle, 0, ApoSimplePanel.iTutAbove.getHeight() + ApoSimplePanel.iTutMiddle.getHeight() * i, null);					
		}
		g.drawImage(ApoSimplePanel.iTutMiddle, 0, maxY - ApoSimplePanel.iTutMiddle.getHeight(), null);					
		
		g.drawImage(ApoSimplePanel.iTutDown, 0, maxY, null);
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
	
}