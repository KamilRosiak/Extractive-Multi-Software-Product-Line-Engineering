package apoSimple.bubble;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import apoSimple.ApoSimpleConstants;
import apoSimple.ApoSimpleImages;
import apoSimple.entity.ApoSimpleString;
import apoSimple.game.ApoSimpleButtonNot;
import apoSimple.game.ApoSimpleGame;
import apoSimple.game.ApoSimpleModel;
import apoSimple.game.ApoSimplePanel;

public class ApoBubbleGame extends ApoSimpleModel {

	public static final int FENCE = 0;
	public static final int EMPTY = 1;
	public static final int SHEEP = 2;
	public static final int GOAL = 3;
	public static final int DOG = 4;
	public static final int SPIKE = 5;
	public static final int BUBBLE = 6;
	public static final int DOG_3 = 7;

	public static final int ENTITY_SIZE = 30;
	
	private BufferedImage iBackground, iBackgroundOriginal, iFlower, iSpikes;
	
	private int moves;
	
	private int level;
	
	private int[][] curLevel;
	
	private ArrayList<ApoBubbleDog> dogs;
	
	private ApoBubbleSheep sheep;
	
	private ArrayList<ApoSimpleString> strings;
	
	private boolean bDie;

	private boolean bWin;
	
	public ApoBubbleGame(ApoSimplePanel game) {
		super(game);
	}
	
	@Override
	public void init() {
		if (this.iBackgroundOriginal == null) {
			this.iBackgroundOriginal = this.getGame().getImages().getImage("images/game.png", false);
		}
		if (this.iFlower == null) {
			this.iFlower = this.getGame().getImages().getImage("images/bubble_flower.png", false);
		}
		if (this.iSpikes == null) {
			this.iSpikes = this.getGame().getImages().getImage("images/bubble_spikes.png", false);
		}
		if (this.strings == null) {
			this.strings = new ArrayList<ApoSimpleString>();
		} else {
			this.strings.clear();
		}
		this.moves = 0;
		this.level = 0;
		this.bDie = false;
		this.bWin = false;
		this.nextLevel(+0);
	}

	public int[][] getCurLevel() {
		return this.curLevel;
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
			this.getGame().setCredits();
		} else if (button == KeyEvent.VK_R) {
			super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
			this.nextLevel(0);
		}
	}
	
	public void keyPressed(int keyCode, char keyCharacter) {
		if (keyCode == KeyEvent.VK_LEFT) {
			this.sheep.sheepRun(-1);
		} else if (keyCode == KeyEvent.VK_RIGHT) {
			this.sheep.sheepRun(+1);
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
		if (function.equals(ApoSimpleGame.MENU)) {
			this.getGame().setCredits();
		} else if (function.equals(ApoSimpleGame.MUSIC)) {
			ApoSimpleButtonNot b = (ApoSimpleButtonNot)(this.getGame().getButtons()[53]);
			b.setBActive(!b.isBActive());
			this.getGame().setbSoundMusic(b.isBActive());
		} else if (function.equals(ApoSimpleGame.SOUND)) {
			ApoSimpleButtonNot b = (ApoSimpleButtonNot)(this.getGame().getButtons()[54]);
			b.setBActive(!b.isBActive());
			this.getGame().setbSoundEffects(b.isBActive());
		} else if (function.equals(ApoSimpleGame.LEVEL_LEFT)) {
			this.nextLevel(-1);
		} else if (function.equals(ApoSimpleGame.LEVEL_RIGHT)) {
			this.nextLevel(+1);
		} else if (function.equals(ApoSimpleGame.RELOAD)) {
			this.nextLevel(0);
		}
	}
	
	private void nextLevel(int add) {
		this.level += add;
		if (this.level < 0) {
			this.level = ApoBubbleLevels.MAX_LEVEL;
		}
		this.curLevel = ApoBubbleLevels.getLevel(this.level);
		if (this.curLevel == null) {
			this.level = 0;
			this.curLevel = ApoBubbleLevels.getLevel(this.level);			
		}
		if (this.dogs == null) {
			this.dogs = new ArrayList<ApoBubbleDog>();
		} else {
			this.dogs.clear();
		}
		if (this.strings == null) {
			this.strings = new ArrayList<ApoSimpleString>();
		} else {
			this.strings.clear();
		}
		this.bDie = false;
		this.bWin = false;
		this.moves = 0;
		for (int y = 0; y < this.curLevel.length; y++) {
			for (int x = 0; x < this.curLevel[0].length; x++) {
				if (this.curLevel[y][x] == ApoBubbleGame.SHEEP) {
					this.curLevel[y][x] = ApoBubbleGame.EMPTY;
					this.sheep = new ApoBubbleSheep(x * ApoBubbleGame.ENTITY_SIZE + 10, y * ApoBubbleGame.ENTITY_SIZE + 10, ApoBubbleGame.ENTITY_SIZE, ApoBubbleGame.ENTITY_SIZE);
				} else if (this.curLevel[y][x] == ApoBubbleGame.DOG) {
					this.curLevel[y][x] = ApoBubbleGame.BUBBLE;
					this.dogs.add(new ApoBubbleDog(ApoSimpleImages.ORIGINAL_DOG_DOWN, x * ApoBubbleGame.ENTITY_SIZE + 10, y * ApoBubbleGame.ENTITY_SIZE + 10, ApoBubbleGame.ENTITY_SIZE, ApoBubbleGame.ENTITY_SIZE));
				} else if (this.curLevel[y][x] == ApoBubbleGame.DOG_3) {
					this.curLevel[y][x] = ApoBubbleGame.BUBBLE;
					ApoBubbleDog dog = new ApoBubbleDog(ApoSimpleImages.ORIGINAL_DOG_DOWN, x * ApoBubbleGame.ENTITY_SIZE + 10, y * ApoBubbleGame.ENTITY_SIZE + 10, ApoBubbleGame.ENTITY_SIZE, ApoBubbleGame.ENTITY_SIZE);
					dog.setCount(3);
					this.dogs.add(dog);
				}
			}
		}
		this.makeBackground();
	}
	
	private void makeBackground() {
		this.iBackground = new BufferedImage(this.iBackgroundOriginal.getWidth(), this.iBackgroundOriginal.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = this.iBackground.createGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(this.iBackgroundOriginal, 0, 0, null);
		
		BufferedImage image = new BufferedImage(90, 90, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g2 = image.createGraphics();
		g2.drawImage(ApoSimpleImages.FENCE_BIG.getScaledInstance(90, 90, BufferedImage.SCALE_SMOOTH), 0, 0, null);
		g2.dispose();
		
		g.setColor(new Color(133, 209, 2));
		g.fillRect(0, 0, 480, 480);
		for (int y = 0; y < this.curLevel.length; y++) {
			for (int x = 0; x < this.curLevel[0].length; x++) {
				boolean bUp = false;
				if ((y == 0) || (this.curLevel[y - 1][x] == ApoBubbleGame.FENCE)) {
					bUp = true;
				}

				boolean bDown = false;
				if ((y == this.curLevel.length - 1) || (this.curLevel[y + 1][x] == ApoBubbleGame.FENCE)) {
					bDown = true;
				}

				boolean bRight = false;
				if ((x == this.curLevel[0].length - 1) || (this.curLevel[y][x + 1] == ApoBubbleGame.FENCE)) {
					bRight = true;
				}
				
				boolean bLeft = false;
				if ((x == 0) || (this.curLevel[y][x - 1] == ApoBubbleGame.FENCE)) {
					bLeft = true;
				}

				boolean bLeftUp = false;
				if ((y == 0) || (x == 0) || (this.curLevel[y - 1][x - 1] == ApoBubbleGame.FENCE)) {
					bLeftUp = true;
				}
				
				boolean bLeftDown = false;
				if ((y == this.curLevel.length - 1) || (x == 0) || (this.curLevel[y + 1][x - 1] == ApoBubbleGame.FENCE)) {
					bLeftDown = true;
				}

				boolean bRightUp = false;
				if ((y == 0) || (x == this.curLevel[0].length - 1) || (this.curLevel[y - 1][x + 1] == ApoBubbleGame.FENCE)) {
					bRightUp = true;
				}

				boolean bRightDown = false;
				if ((y == this.curLevel.length - 1) || (x == this.curLevel[0].length - 1) || (this.curLevel[y + 1][x + 1] == ApoBubbleGame.FENCE)) {
					bRightDown = true;
				}
				if (this.curLevel[y][x] == ApoBubbleGame.FENCE) {
					if (x == 0) {
						if (y == 0) {
							g.fillRect(0, 0, 10, 10);
						} else if (y == this.curLevel.length - 1) {
							g.fillRect(0, 10 + (y + 1) * 30 - 2, 10, 11);
						}
						g.fillRect(0, 10 + y * 30, 10, 30);
					} else if (x == this.curLevel[0].length - 1) {
						if (y == 0) {
							g.fillRect((x+ 1) * 30, 0, 10, 30);
						} else if (y == this.curLevel.length - 1) {
							g.fillRect((x+ 1) * 30, 10 + (y + 1) * 30 - 2, 10, 11);
						}
						g.fillRect((x+ 1) * 30, 10 + y * 30, 10, 30);
					}
					if (y == 0) {
						g.fillRect(10 + x * 30, 0, 30, 10);
					} else if (y == this.curLevel.length - 1) {
						g.fillRect(10 + x * 30, 10 + (y + 1) * 30 - 2, 30, 11);
					}
					
					if ((bUp) && (bDown) && (bRight) && (bLeft)) {
						
					} else if ((bDown) && (bRight) && (bLeft)) {
						g.drawImage(image.getSubimage(30, 0, 30, 30), 10 + x * 30, 10 + y * 30, null);
					} else if ((bUp) && (bRight) && (bLeft)) {
						g.drawImage(image.getSubimage(30, 60, 30, 30), 10 + x * 30, 10 + y * 30, null);
					} else if ((bUp) && (bDown) && (bLeft)) {
						g.drawImage(image.getSubimage(60, 30, 30, 30), 10 + x * 30, 10 + y * 30, null);
					} else if ((bUp) && (bDown) && (bRight)) {
						g.drawImage(image.getSubimage(0, 30, 30, 30), 10 + x * 30, 10 + y * 30, null);
					} else if ((bUp) && (bDown)) {
						g.drawImage(image.getSubimage(0, 30, 15, 30), 10 + x * 30, 10 + y * 30, null);
						g.drawImage(image.getSubimage(60 + 15, 30, 15, 30), 10 + 15 + x * 30, 10 + y * 30, null);
					} else if ((bLeft) && (bRight)) {
						g.drawImage(image.getSubimage(30, 0, 30, 15), 10 + x * 30, 10 + y * 30, null);
						g.drawImage(image.getSubimage(30, 60 + 15, 30, 15), 10 + x * 30, 10 + y * 30 + 15, null);
					} else if ((bLeft) && (bUp)) {
						g.drawImage(image.getSubimage(60, 60, 30, 30), 10 + x * 30, 10 + y * 30, null);
					} else if ((bLeft) && (bDown)) {
						g.drawImage(image.getSubimage(60, 0, 30, 30), 10 + x * 30, 10 + y * 30, null);
					} else if ((bRight) && (bUp)) {
						g.drawImage(image.getSubimage(0, 60, 30, 30), 10 + x * 30, 10 + y * 30, null);
					} else if ((bRight) && (bDown)) {
						g.drawImage(image.getSubimage(0, 0, 30, 30), 10 + x * 30, 10 + y * 30, null);
					} else if (bLeft) {
						g.drawImage(image.getSubimage(60, 0, 30, 15), 10 + x * 30, 10 + y * 30, null);
						g.drawImage(image.getSubimage(60, 60 + 15, 30, 15), 10 + x * 30, 10 + y * 30 + 15, null);
					} else if (bRight) {
						g.drawImage(image.getSubimage(0, 0, 30, 15), 10 + x * 30, 10 + y * 30, null);
						g.drawImage(image.getSubimage(0, 60 + 15, 30, 15), 10 + x * 30, 10 + y * 30 + 15, null);
					} else if (bUp) {
						g.drawImage(image.getSubimage(0, 60, 15, 30), 10 + x * 30, 10 + y * 30, null);
						g.drawImage(image.getSubimage(60 + 15, 60, 15, 30), 10 + 15 + x * 30, 10 + y * 30, null);
					} else if (bDown) {
						g.drawImage(image.getSubimage(0, 0, 15, 30), 10 + x * 30, 10 + y * 30, null);
						g.drawImage(image.getSubimage(60 + 15, 0, 15, 30), 10 + 15 + x * 30, 10 + y * 30, null);
					} else {
						g.drawImage(image.getSubimage(30, 30, 30, 30), 10 + x * 30, 10 + y * 30, null);
					}
					if (x == 0) {
						if ((bLeft) && (bLeftUp) && (!bUp)) {
							g.drawImage(image.getSubimage(0, 60 + 20, 5, 10), (x) * 30 + 10 - 4, 10 + (y + 0) * 30 - 2, null);
						}
					}
					if (x == this.curLevel[0].length - 1) {
						if (!bUp) {
							g.drawImage(image.getSubimage(60 + 25, 60 + 20, 5, 10), (x + 1) * 30 + 10 - 3, 10 + (y + 0) * 30 - 2, null);
						}
					}
					if (y == 0) {
						if ((!bLeft) && (bLeftUp) && (bUp)) {
							g.drawImage(image.getSubimage(60 + 25, 0, 5, 10), 10 - 2 + (x + 0) * 30, 10 - 3 + (y) * 30, null);
						}
					}
					if (y == this.curLevel.length - 1) {
						if (!bLeft) {
							g.drawImage(image.getSubimage(60 + 25, 60 + 20, 5, 10), (x) * 30 + 10 - 4, 10 + (y + 1) * 30 - 3, null);
						}
					}
				} else {
					if (y == this.curLevel.length - 1) {
						g.drawImage(image.getSubimage(30, 60, 30, 30), 10 + x * 30, 10 + y * 30 + 7, null);
					} else if (y == 0) {
						g.drawImage(image.getSubimage(30, 0, 30, 30), 10 + x * 30, 10 + y * 30, null);
					}
					if ((bUp) && (bLeft) && (bLeftUp)) {
						if (y == 0) {
							g.drawImage(image.getSubimage(0, 0, 5, 10), 10 - 3 + x * 30, 10 - 3 + y * 30, null);
						} else if (x == 0) {
							g.drawImage(image.getSubimage(0, 0, 5, 10), 10 - 4 + x * 30, 10 - 7 + y * 30, null);
						} else {
							g.drawImage(image.getSubimage(0, 0, 5, 10), 10 - 3 + x * 30, 10 - 7 + y * 30, null);
						}
					}
					if ((bUp) && (bRight) && (bRightUp)) {
						if (y == 0) {
							g.drawImage(image.getSubimage(60 + 25, 0, 5, 10), 10 - 2 + (x + 1) * 30, 10 - 3 + (y) * 30, null);
						} else if (x == this.curLevel[0].length - 1) {
							g.drawImage(image.getSubimage(60 + 25, 0, 5, 10), 10 - 3 + (x + 1) * 30, 10 - 7 + (y) * 30, null);
						} else {
							g.drawImage(image.getSubimage(60 + 25, 0, 5, 10), 10 - 2 + (x + 1) * 30, 10 - 7 + (y) * 30, null);
						}
					}
					if ((bDown) && (bRight) && (bRightDown)) {
						if (x == this.curLevel[0].length - 1) {
							
						} else {
							g.drawImage(image.getSubimage(60 + 25, 60 + 20, 5, 10), (x + 1) * 30 + 10 - 2, 10 + (y + 1) * 30 - 2, null);
						}
					}
					if ((bDown) && (bLeft) && (bLeftDown)) {
						if (x != 0) {
							g.drawImage(image.getSubimage(0, 60 + 20, 5, 10), (x) * 30 + 10 - 3, 10 + (y + 1) * 30 - 2, null);
						}
					}
				}
			}
		}
		g.dispose();
	}
	
	public void die() {
		this.strings.add(new ApoSimpleString(this.sheep.getXMiddle() - 100, this.sheep.getY() - 115, 200, "Ouch ouch ouch!", true, 2000, false));
		
		this.bDie = true;
	}
	
	public void win() {
		this.strings.add(new ApoSimpleString(this.sheep.getXMiddle() - 100, this.sheep.getY() - 115, 200, "Mampf mampf mampf!", true, 1500, false));
		
		this.bWin = true;
	}
	
	public ApoBubbleSheep getSheep() {
		return this.sheep;
	}
	
	public int getMoves() {
		return this.moves;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}

	@Override
	public void mouseButtonReleased(int x, int y, boolean bRight) {
		if (!this.sheep.isMoving()) {
			int levelX = (x - 10) / ApoBubbleGame.ENTITY_SIZE;
			int levelY = (y - 10) / ApoBubbleGame.ENTITY_SIZE;
			if ((levelX >= 0) && (levelX < this.curLevel[0].length) && 
				(levelY >= 0) && (levelY < this.curLevel.length)) {
				for (int i = 0; i < this.dogs.size(); i++) {
					if (this.dogs.get(i).clickOnDog(this, levelX, levelY)) {
					}
				}
			}
		}
	}

	@Override
	public void think(int delta) {
		for (int i = this.strings.size() - 1; i >= 0; i--) {
			this.strings.get(i).think(delta);
			if (!this.strings.get(i).isBVisible()) {
				this.strings.remove(i);
			}
		}
		if (this.bDie) {
			if (this.sheep.getX() < 240) {
				this.sheep.setCurDirection(ApoSimpleConstants.LEFT);
				this.sheep.setX(-0.2f * delta + this.sheep.getX());
				if (this.sheep.getX() + this.sheep.getWidth() < 0) {
					this.nextLevel(0);
				}
			} else {
				this.sheep.setCurDirection(ApoSimpleConstants.RIGHT);
				this.sheep.setX(+0.2f * delta + this.sheep.getX());
				if (this.sheep.getX() > 470) {
					this.nextLevel(0);
				}
			}
		} else if (this.bWin) {
			if (this.strings.size() <= 0) {
				this.nextLevel(+1);
			}
		} else {
			this.sheep.think(this, delta);
			for (int i = 0; i < this.dogs.size(); i++) {
				this.dogs.get(i).think(this, delta);
			}
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		if (this.iBackground != null) {
			g.drawImage(this.iBackground, 0, 0, null);
		}
		
		for (int y = 0; y < this.curLevel.length; y++) {
			for (int x = 0; x < this.curLevel[0].length; x++) {
				if (this.curLevel[y][x] == ApoBubbleGame.SPIKE) {
					g.drawImage(this.iSpikes, 10 + x * ApoBubbleGame.ENTITY_SIZE, 10 + y * ApoBubbleGame.ENTITY_SIZE, null);
				} else if (this.curLevel[y][x] == ApoBubbleGame.GOAL) {
					g.drawImage(this.iFlower, 10 + x * ApoBubbleGame.ENTITY_SIZE, 10 + y * ApoBubbleGame.ENTITY_SIZE, null);
				}
			}
		}
		this.sheep.render(g, 0, 6);
		for (int i = 0; i < this.dogs.size(); i++) {
			this.dogs.get(i).render(g, 0, 0);
		}
		
		this.drawLevelInfo(g);
		this.drawMoves(g);
		this.drawLevelTutorial(g);
	}
	
	public void renderStrings(Graphics2D g, int changeX, int changeY) {
		try {
			for (int i = this.strings.size() - 1; i >= 0; i--) {
				this.strings.get(i).render(g, changeX, changeY);
			}
		} catch (Exception ex) {	
		}
	}
	
	public void drawLevelTutorial(Graphics2D g) {
		int width = 480;
		g.setColor(Color.BLACK);
		g.setFont(ApoSimpleGame.FONT_RESTART);
		if (this.level == 0) {			
			String[] tut = new String[] {
					"Your sheep 'Wolly' is hungry!",
					"Guide your sheep to the flower",
					"Move Wolly with the cursor keys"
			};
			if (ApoSimpleConstants.REGION.equals("de")) {
				tut = new String[] {
						"Das Schaf 'Wolly' ist hunrig!",
						"Führe Wolly deswegen zur leckeren Blume.",
						"Du kannst ihn mit den Cursortasten bewegen"
				};
			}
			
			int maxY = 20 + 25 * tut.length;
			this.drawTutorial(g, maxY);
			
			for (int i = 0; i < tut.length; i++) {
				String s = tut[i];
				int w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, width/2 - w/2, 45 + i * 25);
			}
		} else if (this.level == 1) {			
			String[] tut = new String[] {
					"The dogs love bubble gums!",
					"Click on the dogs to make a big bubble",
					"but look out the spikes will blow your bubble up",
					"Wolly can walk on the bubble"
			};
			if (ApoSimpleConstants.REGION.equals("de")) {
				tut = new String[] {
						"Die Hunde lieben einfach Kaugummi zu kauen!",
						"Klicke auf sie, um Kaugummiblasen zu erschaffen",
						"Aber Vorsicht Stachel zerstören die Blasen.",
						"Wolly kann über die Blasen laufen."
				};
			}
			
			int maxY = 20 + 25 * tut.length;
			this.drawTutorial(g, maxY);
			
			for (int i = 0; i < tut.length; i++) {
				String s = tut[i];
				int w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, width/2 - w/2, 45 + i * 25);
			}
		} else if (this.level == 2) {			
			String[] tut = new String[] {
					"Try to avoid the spikes.",
					"Wolly doesn't like spikes."
			};
			if (ApoSimpleConstants.REGION.equals("de")) {
				tut = new String[] {
						"Wolly mag keine Stacheln ...",
						"deshalb versuche sie nicht zu berühren.",
				};
			}
			
			int maxY = 20 + 25 * tut.length;
			this.drawTutorial(g, maxY);
			
			for (int i = 0; i < tut.length; i++) {
				String s = tut[i];
				int w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, width/2 - w/2, 45 + i * 25);
			}
		} else if (this.level == 3) {			
			String[] tut = new String[] {
					"The blue bubbles from the dogs",
					"will explode after 3 times.",
					"Keep this in mind"
			};
			if (ApoSimpleConstants.REGION.equals("de")) {
				tut = new String[] {
						"Die blauen Kaugummiblasen der Hunde",
						"explodieren nach dem 3ten mal aufblasen.",
						"Sie brauchen also keine Stacheln zum Platzen."
				};
			}
			
			int maxY = 20 + 25 * tut.length;
			this.drawTutorial(g, maxY);
			
			for (int i = 0; i < tut.length; i++) {
				String s = tut[i];
				int w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, width/2 - w/2, 45 + i * 25);
			}
		} else if (this.level == 5) {			
			String[] tut = new String[] {
					"If you are stuck, press 'r' or",
					"press the restart button in the",
					"upper right corner to restart the level."
			};
			if (ApoSimpleConstants.REGION.equals("de")) {
				tut = new String[] {
						"Wenn ihr mal nicht mehr weiterkommt,",
						"drückt 'r' oder den Knopf oben rechts,",
						"um das Level neu zu starten"
				};
			}
			
			int maxY = 20 + 25 * tut.length;
			this.drawTutorial(g, maxY);
			
			for (int i = 0; i < tut.length; i++) {
				String s = tut[i];
				int w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, width/2 - w/2, 45 + i * 25);
			}
		} else if (this.level == 8) {			
			String[] tut = new String[] {
					"You can be catched in the bubble.",
					"Then you can't move Wolly",
					"until the bubble explodes.",
			};
			if (ApoSimpleConstants.REGION.equals("de")) {
				tut = new String[] {
						"Ihr könnt in den Blasen festkleben!",
						"Dann könnt ihr euch nicht mehr bewegen",
						"bis die Blase explodiert."
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
	
	public void drawLevelInfo(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.setFont(ApoSimpleGame.FONT_RESTART);
		String s = String.valueOf(this.level + 1) + " / " + String.valueOf(ApoBubbleLevels.MAX_LEVEL + 1);
		int w = g.getFontMetrics().stringWidth(s);
		int x = 553;
		int y = 193;
		ApoSimpleGame.drawString(g, s, x - w/2, y);
	}
	
	public void drawMoves(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.setFont(ApoSimpleGame.FONT_RESTART);
		if (this.moves > 0) {
			String s = String.valueOf(this.moves);
			int w = g.getFontMetrics().stringWidth(s);
			int x = 561;
			int y = 245;
			ApoSimpleGame.drawString(g, s, x - w/2, y);
		}
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
		this.renderStrings(g, 0, 0);
		
		this.drawCoins(g);
		this.drawMusicOver(g);
		this.drawEffectOver(g);
		this.drawNoticeOver(g);
		this.drawReloadOver(g);
	}
	
	public int getCost() {
		return (this.level + 1) * 10;
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

}
