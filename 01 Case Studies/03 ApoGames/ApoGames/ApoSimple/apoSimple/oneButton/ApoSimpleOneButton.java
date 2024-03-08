package apoSimple.oneButton;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import apoSimple.ApoSimpleConstants;
import apoSimple.ApoSimpleImages;
import apoSimple.game.ApoSimpleGame;
import apoSimple.game.ApoSimpleModel;
import apoSimple.game.ApoSimplePanel;

public class ApoSimpleOneButton extends ApoSimpleModel {

	public static final String MENU_STRING = "menu";
	public static final String MENU = "credits_menu";

	private BufferedImage iBackgroundOriginal;
	private BufferedImage iBackground;
	
	private Rectangle2D.Float rec;
	
	private int death = 0;
	private int level = 0;
	
	private int curLevelInLevel;
	
	private ApoSimpleOneButtonLevel curLevel;
	
	private ApoSimpleOneButtonSheep sheep;
	
	private boolean bPause;
	
	private boolean bStart;
	
	private ApoSimpleHighscoreOneGet highscore;
	
	private boolean bScore;
	
	private int place;
	
	private String name;
	
	private ArrayList<ApoSimpleOneSplatter> splatter;
	
	private boolean bMouseReleased;
	
	public ApoSimpleOneButton(ApoSimplePanel game) {
		super(game);
	}
	
	@Override
	public void init() {
		if (this.name == null) {
			this.name = "";
			if ((this.getGame().getTextField() != null) && (this.getGame().getTextField().getCurString() != null)) {
				this.name = this.getGame().getTextField().getCurString();
			}
		}
		if (this.iBackground == null) {
			this.iBackgroundOriginal = this.getGame().getImages().getImage("images/onebuttongame.png", false);
			this.iBackground = this.getGame().getImages().getImage("images/onebuttongame.png", false);
		}
		if (this.highscore == null) {
			this.highscore = new ApoSimpleHighscoreOneGet();
		}
		this.highscore.loadHighscore();
		
		this.resetEverything();
	}
	
	private void resetEverything() {
		this.makeRec();
		this.place = -1;
		this.death = 0;
		this.level = 1;
		
		this.bStart = false;
		this.bPause = false;
		this.bScore = false;
		
		if (this.splatter == null) {
			this.splatter = new ArrayList<ApoSimpleOneSplatter>();
		}
		this.splatter.clear();
		this.curLevel = ApoSimpleOneButtonLevels.getLevel(this.level);
		this.sheep = new ApoSimpleOneButtonSheep(20, ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0 - 60, 60, 60);
		
		this.changeCurLevelInLevel(0);
	}
	
	public int getCurMaxY() {
		if (this.curLevelInLevel == 0) {
			return ApoSimpleOneButtonSheep.MAX_Y_LEVEL_0;
		} else if (this.curLevelInLevel == 1) {
			return ApoSimpleOneButtonSheep.MAX_Y_LEVEL_1;
		} else {
			return ApoSimpleOneButtonSheep.MAX_Y_LEVEL_2;
		}
	}
	
	public ApoSimpleOneButtonLevel getCurLevel() {
		return this.curLevel;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getCurLevelInLevel() {
		return this.curLevelInLevel;
	}

	public void setCurLevelInLevel(int curLevelInLevel) {
		this.curLevelInLevel = curLevelInLevel;
	}

	private void makeRec() {
		int levelWidth = (int)(ApoSimpleConstants.ENTITY_WIDTH + 5) * 7;
		int levelHeight = (int)(ApoSimpleConstants.ENTITY_WIDTH + 5) * 7;
		this.rec  = new Rectangle2D.Float(10, 10, levelWidth, levelHeight);
	}
	
	public Rectangle2D.Float getRec() {
		return this.rec;
	}
	
	public void isDone() {
		this.curLevelInLevel += 1;
		if (this.curLevelInLevel > 2) {
			this.iBackground = new BufferedImage(this.iBackgroundOriginal.getWidth(), this.iBackgroundOriginal.getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics2D g = this.iBackground.createGraphics();
			g.drawImage(this.iBackgroundOriginal, 0, 0, null);
			g.dispose();
			this.level += 1;
			if (ApoSimpleOneButtonLevels.getLevel(this.level) == null) {
				this.level = 1;
				this.bScore = true;
				try {
					this.place = this.highscore.getPlace(this.death, this.name);
					
					Thread t = new Thread(new Runnable() {
						@Override
						public void run() {
							ApoSimpleHighscoreOne.getInstance().save(ApoSimpleOneButton.this.death, ApoSimpleOneButton.this.name);
						}
			 		});
			 		t.start();
				} catch (Exception ex) {
					this.place = -1;
				}
			}
			this.curLevel = ApoSimpleOneButtonLevels.getLevel(this.level);
			this.changeCurLevelInLevel(0);
		} else {
			this.changeCurLevelInLevel(this.curLevelInLevel);
		}
	}
	
	public void die() {
		this.death += 1;
		
		Graphics2D g = this.iBackground.createGraphics();
		int random = (int)(Math.random() * 8);
		if (random == 8) {
			random = 0;
		}
		int width = ApoSimpleImages.ONEBUTTON_BLOOD.getWidth() / 8;
		int x = (int)(this.sheep.getX() + this.sheep.getWidth()/2 - width/2);
		int y = (int)(this.sheep.getY() + this.sheep.getHeight()*1/2 - ApoSimpleImages.ONEBUTTON_BLOOD.getHeight()/2);
		int rand = (int)(5 + Math.random() * 7);
		for (int i = 0; i < rand; i++) {
			random = (int)(Math.random() * 8);
			if (random == 8) {
				random = 0;
			}
			int addX = (int)(Math.random() * this.sheep.getWidth() - this.sheep.getWidth()/2);
			int addY = (int)(Math.random() * this.sheep.getHeight() - this.sheep.getHeight()/2);
			g.drawImage(ApoSimpleImages.ONEBUTTON_BLOOD.getSubimage(random * width, 0, width, ApoSimpleImages.ONEBUTTON_BLOOD.getHeight()), x + addX, y + addY, null);
		}
		g.dispose();
		
		random = (int)(20 + Math.random() * 20);
		for (int i = 0; i < random; i++) {
			int nextRandom = (int)(Math.random() * 8);
			if (nextRandom == 8) {
				nextRandom = 0;
			}
			BufferedImage image = new BufferedImage(width, width, BufferedImage.TYPE_INT_ARGB_PRE);
			g = image.createGraphics();
			g.drawImage(ApoSimpleImages.ONEBUTTON_BLOOD.getSubimage(nextRandom * width, 0, width, ApoSimpleImages.ONEBUTTON_BLOOD.getHeight()), 0, 0, null);
			g.dispose();
			this.splatter.add(new ApoSimpleOneSplatter(image, x, y, width, width));
		}
		
		this.changeCurLevelInLevel(this.curLevelInLevel);
	}
	
	private void changeCurLevelInLevel(int newLevelInLevel) {
		this.curLevelInLevel = newLevelInLevel;
		this.sheep.setVecX(this.curLevel.getVecSheep());
		if (this.curLevelInLevel == 1) {
			this.sheep.setX(453 - this.sheep.getWidth());
			if (this.sheep.getVecX() > 0) {
				this.sheep.setVecX(-this.sheep.getVecX());
			}
		} else {
			this.sheep.setX(20);
			if (this.sheep.getVecX() < 0) {
				this.sheep.setVecX(-this.sheep.getVecX());
			}
		}
		this.sheep.changeOppositeDirection();
		this.sheep.setY(this.getCurMaxY() - this.sheep.getHeight());
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
			this.getGame().setCredits();
		} else if (button == KeyEvent.VK_R) {
			this.resetEverything();
		} else if (button == KeyEvent.VK_P) {
			this.bPause = !this.bPause;
		} else if ((button == KeyEvent.VK_SPACE) || (button == KeyEvent.VK_DOWN) || (button == KeyEvent.VK_RIGHT) || (button == KeyEvent.VK_LEFT) || (button == KeyEvent.VK_UP)) {
			this.bMouseReleased = false;
			if (!this.bStart) {
				this.bStart = !this.bStart;
			} else if (this.bPause) {
				this.bPause = !this.bPause;
			} else if (this.bScore) {
				this.bScore = !this.bScore;
				this.resetEverything();
			} else {
			}
		}
	}
	
	public void keyPressed(int keyCode, char keyCharacter) {
		if ((this.bStart) && (!this.bPause) && (!this.bScore) && (!this.bMouseReleased)) {
			if ((keyCode == KeyEvent.VK_SPACE) || (keyCode == KeyEvent.VK_DOWN) || (keyCode == KeyEvent.VK_RIGHT) || (keyCode == KeyEvent.VK_LEFT) || (keyCode == KeyEvent.VK_UP)) {
				if (this.sheep != null) {
					if (this.sheep.jump()) {
						this.bMouseReleased = true;
					}
				}
			}
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
	}
	
	@Override
	public void mouseButtonReleased(int x, int y, boolean bRight) {
	}

	@Override
	public void think(int delta) {
		if ((!this.bPause) && (this.bStart) && (!this.bScore)) {
			if (this.sheep != null) {
				this.sheep.think(delta, this);
			} 
			try {
				if (this.splatter != null) {
					for (int i = this.splatter.size() - 1; i >= 0; i--) {
						this.splatter.get(i).think(delta);
						if (!this.splatter.get(i).isBVisible()) {
							this.splatter.remove(i);
						}
					}
				}
			} catch (Exception ex) {
			}
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		if (this.iBackground != null) {
			g.drawImage(this.iBackground, 0, 0, null);
			
			if (this.curLevel != null) {
				this.curLevel.render(g, 0, 0);
			}
			
			g.setColor(Color.BLACK);
			g.setFont(ApoSimpleGame.FONT_RESTART);
			if (this.curLevel.getMessage()[this.curLevelInLevel].length() > 0) {
				String[] s = new String[] {
						"level "+(this.level)+"/13: "+this.curLevel.getMessage()[this.curLevelInLevel]	
				};
				this.renderStringWithBackground(g, s, 240, 40 + 160 * this.curLevelInLevel, 30);
			}
			
			if (this.sheep != null) {
				this.sheep.render(g, 0, 0);
			}
			
			try {
				if (this.splatter != null) {
					for (int i = this.splatter.size() - 1; i >= 0; i--) {
						this.splatter.get(i).render(g, 0, 0);
					}
				}
			} catch (Exception ex) {
			}
			
			g.setColor(Color.BLACK);
			g.setFont(ApoSimpleGame.FONT_RESTART);
			String s = String.valueOf(this.level);
			int w = g.getFontMetrics().stringWidth(s);
			int x = 558;
			int y = 195;
			ApoSimpleGame.drawString(g, s, x - w/2, y);
			
			g.setColor(Color.BLACK);
			g.setFont(ApoSimpleGame.FONT_BUTTON);
			s = "OneButton";
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, 561 - w/2, 220);
			s = "Game";
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, 561 - w/2, 235);
			
			s = "death:";
			if (ApoSimpleConstants.REGION.equals("de")) {
				s = "Tode:";
			}
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, 560 - w/2, 270);

			g.setFont(ApoSimpleGame.FONT_RESTART);
			s = String.valueOf(this.death);
			w = g.getFontMetrics().stringWidth(s);
			ApoSimpleGame.drawString(g, s, 560 - w/2, 295);

			if (ApoSimpleConstants.REGION.equals("de")) {
				g.setFont(ApoSimpleGame.FONT_ONEBUTTON);
				g.drawString("Space ist dein Freund.", 495, 350);
				g.drawString("'r' = Neustart", 495, 368);
				g.drawString("ESC = Beenden", 495, 386);
			} else {
				g.setFont(ApoSimpleGame.FONT_ONEBUTTON);
				g.drawString("Space is your friend.", 495, 350);
				g.drawString("Press r to restart.", 495, 368);
				g.drawString("Press ESC to quit.", 495, 386);
			}
			
			if (this.bScore) {
				g.setFont(ApoSimpleGame.FONT_TEXTFIELD);
				this.drawHighscore(g, 185);
				
				String[] string = new String[] {
						"You died "+this.death+" times!",
						"",
						"Place "+(this.place+1)+" of "+this.highscore.getHelp().size()
				};
				if (ApoSimpleConstants.REGION.equals("de")) {
					string = new String[] {
							"Du bist "+String.valueOf(this.death)+" Mal gestorben!",
							"",
							"Platz "+(this.place+1)+" of "+this.highscore.getHelp().size()
					};
				}
				this.renderStringWithBackground(g, string, 240, 325, 80);
				
				string = new String[] {
						"Press Space to start a new game."
				};
				if (ApoSimpleConstants.REGION.equals("de")) {
					string = new String[] {
							"Leertaste = Spiel starten."
					};
				}
				this.renderStringWithBackground(g, string, 240, 470, 30);
			} else if ((!this.bStart) || (this.bPause)) {
				g.setFont(ApoSimpleGame.FONT_TEXTFIELD);
				String[] string = new String[] {
						"Press space to start the 1st level"
				};
				if (ApoSimpleConstants.REGION.equals("de")) {
					string = new String[] {
							"Leertaste = Spiel starten."
					};
				}
				if (this.bPause) {
					string[0] = "Press space to unpause the game";
					if (ApoSimpleConstants.REGION.equals("de")) {
						string = new String[] {
								"Drücke Leertaste, um die Pause zu beenden."
						};
					}
				}
				this.renderStringWithBackground(g, string, 240, 240, 30);
				
				string = new String[] {
						"  OneButtonSheeptasticHiddenGame  ",
						"Your task is to survive and escape!",
						"Press space to jump and don't die!",
				};
				if (ApoSimpleConstants.REGION.equals("de")) {
					string = new String[] {
							"  OneButtonSheeptasticHiddenGame  ",
							"Eure Aufgabe: Überleben und Abhauen!",
							"Springe mit der Leertaste.",
					};
				}
				this.renderStringWithBackground(g, string, 240, 85, 80);
				
				this.drawHighscore(g, 465);
			}
		}
	}
	
	private void drawHighscore(Graphics2D g, int y) {
		if (this.highscore != null) {
			int w = 300;
			final int size = 7;
			final int x = 240;
			final int height = 180;
			final int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
			
			g.setColor(Color.WHITE);
			g.fillRoundRect(x - w/2 - 5, y - height + 2, w + 10, height + 5, 5, 5);
			
			g.setColor(Color.BLACK);
			g.drawRoundRect(x - w/2 - 5, y - height + 2, w + 10, height + 5, 5, 5);
			
			String s = "Highscore";
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, x - w/2, y - height + 1 * h + 5);
			
			g.setColor(Color.DARK_GRAY);
			s = "death";
			if (ApoSimpleConstants.REGION.equals("de")) {
				s = "Tode";
			}
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, 170 - w, y - height + (3) * h);
			g.drawString("name", 200, y - height + (3) * h);
			for (int i = 0; i < this.highscore.getHelp().size() && i < size - 2; i++) {
				String death = String.valueOf(this.highscore.getHelp().get(i).getDeath());
				if (this.place == i) {
					g.setColor(Color.RED);
				} else {
					g.setColor(Color.BLACK);		
				}
				w = g.getFontMetrics().stringWidth(death);
				g.drawString(death, 170 - w, y - height + (i + 4) * h);
				
				g.drawString(String.valueOf(this.highscore.getHelp().get(i).getName()), 200, y - height + (i + 4) * h);
			}
		}
	}
	
	private void renderStringWithBackground(Graphics2D g, String[] s, int x, int y, int height) {
		int w = g.getFontMetrics().stringWidth(s[0]);
		for (int i = 0; i < s.length; i++) {
			if (w < g.getFontMetrics().stringWidth(s[i])) {
				w = g.getFontMetrics().stringWidth(s[i]);
			}
		}
		
		g.setColor(Color.WHITE);
		g.fillRoundRect(x - w/2 - 5, y - height + 2, w + 10, height, 5, 5);
		
		g.setColor(Color.BLACK);
		g.drawRoundRect(x - w/2 - 5, y - height + 2, w + 10, height, 5, 5);
		
		int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
		for (int i = 0; i < s.length; i++) {
			g.drawString(s[i], x - w/2, y - height + (i+1) * h + 5);
		}
	}
	
	public void drawOverlay(Graphics2D g) {	
		super.getGame().renderCoins(g, 0, 0);
	}

}
