package apoSimple.game;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apogames.ApoConstants;
import org.apogames.entity.ApoButton;
import org.apogames.entity.ApoButtonWithImageAndText;
import org.apogames.help.ApoHelp;

import apoSimple.ApoSimpleConstants;
import apoSimple.ApoSimpleImages;
import apoSimple.game.level.ApoLevelIO;
import apoSimple.game.level.ApoSimpleLevel;
import apoSimple.game.level.ApoSimpleLevelHighscore;
import apoSimple.game.level.ApoSimpleLevels;

public class ApoSimpleLevelChooser extends ApoSimpleModel {
	private final Font FONT = ApoSimpleConstants.ORIGINAL_FONT.deriveFont(23f).deriveFont(Font.BOLD);//new Font(Font.SANS_SERIF, Font.BOLD, 20);
	
	public static final int MAX_LEVELS = 42;
	
	public static final String MENU_STRING = "menu";
	public static final String MENU = "credits_menu";
	public static final String LEFT = "left";
	public static final String RIGHT = "right";

	private BufferedImage iBackground;
	
	private ArrayList<ApoButton> buttons;
	
	private ApoLevelIO io;
	
	private ArrayList<ApoSimpleLevelHighscore> levelSolutions;
	
	private int showStart;
	
	public ApoSimpleLevelChooser(ApoSimplePanel game) {
		super(game);
	}
	
	@Override
	public void init() {
		if (this.io == null) {
			try {
				this.io = new ApoLevelIO();
				if (!ApoConstants.B_APPLET) {
					this.io.readLevel("levels");
				} else {
					String load;
					try {
						load = ApoHelp.loadData(new URL("http://www.apo-games.de/apoSheeptastic/"), "solved");
						if ((load != null) && (load.length() > 0)) {
							int size = load.length() / 4;
							for (int i = 0; i < size; i++) {
								int level = Integer.valueOf(load.substring(0, 3));
								int stars = Integer.valueOf(load.substring(3,4));
								load = load.substring(4);
								this.getLevelSolutions().add(new ApoSimpleLevelHighscore(level, stars));	
							}
						}
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception ex) {
				if (this.levelSolutions == null) {
					this.levelSolutions = new ArrayList<ApoSimpleLevelHighscore>();
				}
			}
		}
		if (this.buttons == null) {
			this.buttons = new ArrayList<ApoButton>();
			
			this.showStart = 0;
			
			Font font = new Font(Font.SANS_SERIF, Font.BOLD, 20);
			String text = "10";
			String function = "button";
			int width = 60;
			int height = 60;
			BufferedImage buttonImage = ApoSimpleImages.ORIGINAL_RIGHT;
			
			int x = 20;
			int y = 20;
			for (int i = 0; i < ApoSimpleLevels.getMaxLevels(); i++) {
				if (i % ApoSimpleLevelChooser.MAX_LEVELS == 0) {
					x = 20;
					y = 20;
				}
				this.buttons.add(new ApoButton(this.getGame().getImages().getButtonWithImage(width * 3, height, buttonImage, text, new Color(0, 0, 0, 0), new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), font), x, y, width, height, function));
				x += width + 10;
				if (i % ApoSimpleLevelChooser.MAX_LEVELS == 15) {
					x = 20;
					y += height + 10;
				} else if (i % ApoSimpleLevelChooser.MAX_LEVELS == 21) {
					x = 20;
					y += height + 10;
				} else if (i % ApoSimpleLevelChooser.MAX_LEVELS == 27) {
					x = 20;
					y += height + 10;
				}
				if ((x + width >= ApoSimpleConstants.GAME_WIDTH - 20) || (i % ApoSimpleLevelChooser.MAX_LEVELS == 34)) {
					x = 20;
					y += height + 10;
					if ((y > 150) && (y < 240)) {
						y = 320;
					}
				}
				this.buttons.get(this.buttons.size() - 1).setBVisible(false);
				
			}
			this.showStart = ((int)(this.getMaxLevel() / ApoSimpleLevelChooser.MAX_LEVELS)) * ApoSimpleLevelChooser.MAX_LEVELS;
			
		}
		this.setVisibleButtons();
		this.getMaxLevel();
		
		if (this.iBackground == null) {
			this.iBackground = this.getGame().getImages().getImage("images/levelchooser.png", false);
		}
		
		this.setShowStart(this.showStart);
	}
	
	public void setVisibleButtons() {
		for (int i = 0; i < this.getLevelSolutions().size(); i++) {
			int level = this.getLevelSolutions().get(i).getLevel();
			this.buttons.get(level-1).setBVisible(true);
		}
	}
	
	public int getMaxLevel() {
		int[] max = new int[3];
		for (int m = 0; m < max.length; m++) {
			for (int i = 0; i < this.getLevelSolutions().size(); i++) {
				int level = this.getLevelSolutions().get(i).getLevel();
				if ((level > ApoSimpleLevelChooser.MAX_LEVELS * m) && (level <= ApoSimpleLevelChooser.MAX_LEVELS * (m+1))) {
					if (level > max[m]) {
						max[m] = level;
					}
				}
			}
			if (max[m] < this.buttons.size()) {
				this.buttons.get(max[m]).setBVisible(true);
			}
		}
		int ma = 0;
		for (int m = 0; m < max.length; m++) {
			if (max[m] > ma) {
				ma = max[m];
			}
		}
		return ma;
	}
	
	private void setShowStart(int showStart) {
		this.showStart = showStart;
		if (this.showStart < 0) {
			this.showStart = 0;
		} else if (this.showStart >= 2 * ApoSimpleLevelChooser.MAX_LEVELS) {
			this.showStart = 2 * ApoSimpleLevelChooser.MAX_LEVELS;
		}
		if (this.showStart == 0) {
			this.getGame().getButtons()[65].setBVisible(false);
			this.getGame().getButtons()[66].setBVisible(true);
			ApoButtonWithImageAndText b = (ApoButtonWithImageAndText)(this.getGame().getButtons()[66]);
			b.setText("medium");
		} else if (this.showStart == ApoSimpleLevelChooser.MAX_LEVELS) {
			this.getGame().getButtons()[65].setBVisible(true);
			this.getGame().getButtons()[66].setBVisible(true);
			ApoButtonWithImageAndText b = (ApoButtonWithImageAndText)(this.getGame().getButtons()[66]);
			b.setText("hard");
			b = (ApoButtonWithImageAndText)(this.getGame().getButtons()[65]);
			b.setText("easy");
			if (this.buttons.size() < this.showStart + ApoSimpleLevelChooser.MAX_LEVELS) {
				this.getGame().getButtons()[66].setBVisible(false);	
			}
		} else {
			this.getGame().getButtons()[65].setBVisible(true);
			this.getGame().getButtons()[66].setBVisible(false);
			ApoButtonWithImageAndText b = (ApoButtonWithImageAndText)(this.getGame().getButtons()[65]);
			b.setText("medium");
		}
		
		if (this.buttons.size() >= this.showStart) {
			if (!this.buttons.get(this.showStart).isBVisible()) {
				this.buttons.get(this.showStart).setBVisible(true);
			}
		}
	}

	public ArrayList<ApoSimpleLevelHighscore> getLevelSolutions() {
		if (this.io != null) {
			return this.io.getHighscore();
		}
		return this.levelSolutions;
	}

	public int getStarsForLevel(int level) {
		if (level < this.getLevelSolutions().size()) {
			if (this.getLevelSolutions().get(level).getLevel() == level) {
				return this.getLevelSolutions().get(level).getStars();
			}
		}
		for (int i = 0; i < this.getLevelSolutions().size(); i++) {
			if (this.getLevelSolutions().get(i).getLevel() == level) {
				return this.getLevelSolutions().get(i).getStars();
			}
		}
		return -1;
	}

	public int addLevel(int level, int stars) {
		for (int i = 0; i < this.getLevelSolutions().size(); i++) {
			if (this.getLevelSolutions().get(i).getLevel() == level) {
				if (this.getLevelSolutions().get(i).getStars() < stars) {
					int cost = (3 - this.getLevelSolutions().get(i).getStars()) * level;
					this.getLevelSolutions().get(i).setStars(stars);
					this.saveLevel();
					return cost;
				} else {
					return 0;
				}
			}
		}
		this.getLevelSolutions().add(new ApoSimpleLevelHighscore(level, stars));
		this.saveLevel();
		int cost = (3) * level;
		return cost;
	}
	
	private void saveLevel() {
		if (!ApoConstants.B_APPLET) {
			if (this.io != null) {
				Thread t = new Thread() {
					public void run() {
						ApoSimpleLevelChooser.this.io.writeLevel("levels");
					}
				};
				t.start();
			}
		}
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			this.getGame().setPuzzle();
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
		if (function.equals(ApoSimpleLevelChooser.MENU)) {
			this.getGame().setPuzzle();
		} else if (function.equals(ApoSimpleLevelChooser.LEFT)) {
			this.setShowStart(this.showStart - ApoSimpleLevelChooser.MAX_LEVELS);
		} else if (function.equals(ApoSimpleLevelChooser.RIGHT)) {
			this.setShowStart(this.showStart + ApoSimpleLevelChooser.MAX_LEVELS);
		}
	}
	
	@Override
	public void mouseButtonReleased(int x, int y, boolean right) {
		if (this.buttons != null) {
			for (int i = 0; i < this.buttons.size(); i++) {
				if (this.buttons.get(i).isBVisible()) {
					if (this.buttons.get(i).getReleased(x, y)) {
						int levelInt = i+1;
						ApoSimpleLevel level = ApoSimpleLevels.getLevel(levelInt);
						if (level == null) {
							levelInt = 1;
							level = ApoSimpleLevels.getLevel(levelInt);
						}
						this.getGame().setLevel(level, false, levelInt);
						super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
						this.getGame().render();
					}
				}
			}
		}
	}
	
	public boolean mousePressed(int x, int y, boolean bRight) {
		if (this.buttons != null) {
			for (int i = 0; i < this.buttons.size(); i++) {
				if (this.buttons.get(i).isBVisible()) {
					if (this.buttons.get(i).getPressed(x, y)) {
						this.getGame().render();
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean mouseMoved(int x, int y) {
		boolean bOver = false;
		if (this.buttons != null) {
			for (int i = this.showStart; i < this.buttons.size() && i < this.showStart + ApoSimpleLevelChooser.MAX_LEVELS; i++) {
				if (this.buttons.get(i).isBVisible()) {
					if (this.buttons.get(i).getMove(x, y)) {
						bOver = true;
						super.getGame().render();
						break;
					} else if (this.buttons.get(i).isBOver()) {
						bOver = true;
					}
				}
			}
		}
		if (bOver) {
			super.getGame().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		} else {
			super.getGame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		return false;
	}

	@Override
	public void think(int delta) {
		
	}
	
	@Override
	public void render(Graphics2D g) {
		if (this.iBackground != null) {
			g.drawImage(this.iBackground, 0, 0, null);
		}
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		for (int i = this.showStart; i < this.buttons.size() && i < this.showStart + ApoSimpleLevelChooser.MAX_LEVELS; i++) {
			if (this.buttons.get(i).isBVisible()) {
				this.buttons.get(i).render(g, 0, 0);
				
//				Color c = Color.GREEN.brighter().brighter().brighter();
				int width = 45;
				int x = (int)(this.buttons.get(i).getX() + this.buttons.get(i).getWidth()/2 - width/2);
				int y = (int)(this.buttons.get(i).getY() + this.buttons.get(i).getHeight()/2 - width/2);
//				if ((i < maxLevel) || (i > maxLevel)) {
//					if (i < maxLevel) {
//						g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 80));
//					} else {
//						g.setColor(Color.LIGHT_GRAY);
//					}
//					g.fillOval(x, y, width, width);
//				}
				
				int stars = this.getStarsForLevel(i+1);
				if (stars > 0) {
					if (stars == 1) {
						g.drawImage(ApoSimplePanel.iStarLittle, x + width/2 - ApoSimplePanel.iStarLittle.getWidth()/2, y + width - ApoSimplePanel.iStarLittle.getHeight()/2, null);
					} else if (stars == 2) {
						g.drawImage(ApoSimplePanel.iStarLittle, x + width/2 - ApoSimplePanel.iStarLittle.getWidth() - 2, y + width - ApoSimplePanel.iStarLittle.getHeight()/2, null);
						g.drawImage(ApoSimplePanel.iStarLittle, x + width/2 + 0*ApoSimplePanel.iStarLittle.getWidth() + 2, y + width - ApoSimplePanel.iStarLittle.getHeight()/2, null);
					} else if (stars == 3) {
						g.drawImage(ApoSimplePanel.iStarLittle, x + width/2 - 3 * ApoSimplePanel.iStarLittle.getWidth()/2, y + width - 4 - ApoSimplePanel.iStarLittle.getHeight()/2, null);
						g.drawImage(ApoSimplePanel.iStarLittle, x + width/2 - ApoSimplePanel.iStarLittle.getWidth()/2, y + width - ApoSimplePanel.iStarLittle.getHeight()/2, null);
						g.drawImage(ApoSimplePanel.iStarLittle, x + width/2 + ApoSimplePanel.iStarLittle.getWidth()/2, y + width - 4 - ApoSimplePanel.iStarLittle.getHeight()/2, null);
					}
				}
				
				g.setColor(Color.BLACK);
				g.setFont(this.FONT);
				String s = String.valueOf((i + 1));
				int w = g.getFontMetrics().stringWidth(s);
				int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
				x = (int)(this.buttons.get(i).getX() + this.buttons.get(i).getWidth()/2);
				y = (int)(this.buttons.get(i).getY() + this.buttons.get(i).getHeight()/2);
				g.drawString(s, x - w/2 - 5, y + h /2 - 3);
			}
		}
		super.getGame().renderCoins(g, 0, 0);
	}

}
