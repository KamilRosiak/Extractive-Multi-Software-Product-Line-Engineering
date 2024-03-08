package apoNotSoSimple.game;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.apogames.ApoConstants;

import apoNotSoSimple.ApoNotSoSimpleConstants;
import apoNotSoSimple.ApoNotSoSimpleImages;
import apoNotSoSimple.entity.ApoNotSoSimpleString;
import apoNotSoSimple.level.ApoNotSoSimpleLevel;
import apoNotSoSimple.level.ApoNotSoSimpleLevelGeneration;

public class ApoNotSoSimpleGame extends ApoNotSoSimpleModel {
	
	private final int MAX_LOOSE_TIME = 500;
	
	public static final int POINTS_WIDTH = 40;
	
	public static final String MENU_STRING = "menu";
	public static final String MENU = "game_menu";
	public static final String UPLOAD_STRING = "upload";
	public static final String UPLOAD = "game_upload";
	public static final String MENU_SCORE_STRING = "menu";
	public static final String MENU_SCORE = "game_menuscore";
	public static final String NEW_STRING = "restart";
	public static final String NEW = "game_restart";
	public static final String LEFT_SORT = "left_sort";
	public static final String RIGHT_SORT = "right_sort";
	public static final String LEFT_LEVEL = "left_level";
	public static final String RIGHT_LEVEL = "right_level";
	
	public static final Font FONT_SCORE = new Font(Font.SANS_SERIF, Font.BOLD, 60);
	public static final Font FONT_STATICS = new Font(Font.SANS_SERIF, Font.BOLD, 40);
	public static final Font FONT_RESTART = new Font(Font.SANS_SERIF, Font.BOLD, 25);
	public static final Font FONT_IMAGE_HELP = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
	public static final Font FONT_TEXTFIELD = new Font(Font.SANS_SERIF, Font.BOLD, 30);
	
	public static final int LEVEL_UP_TIME = 80;
	
	public static final int UPLOAD_TIME = 3000;
	public static final int UPLOAD_TIME_TEST = 5000;
	
	private ApoNotSoSimpleLevel curLevel;
	
	private BufferedImage iBackground;
	
	private int points = 0;
	
	private int level = 0;
	
	private int moves = 0;

	private ArrayList<ApoNotSoSimpleString> strings;

	private BufferedImage iLoose;
	
	private int curLooseTime;
	
	private String solution;
	
	private ApoNotSoSimpleUserLevels userLevels;
	
	private int sort = -1;
	
	public ApoNotSoSimpleGame(ApoNotSoSimplePanel game) {
		super(game);
		
		if (this.userLevels == null) {
			this.userLevels = new ApoNotSoSimpleUserLevels();
		}
	}
	
	@Override
	public void init() {
		this.curLooseTime = 0;
		this.getGame().setShouldRepaint(false);
		this.solution = "";
	}
	
	public void init(int level) {
		if (level >= 0) {
			this.makeLevel(level);			
		}
	}
	
	public void initUserLevels() {
		this.level = -3;
		if (this.sort < 0) {
			this.sort = ApoNotSoSimpleUserLevels.SORT_SOLUTION;
		}
		
		this.makeLevel(this.level);
		
		this.curLooseTime = 0;
		this.getGame().setShouldRepaint(false);
		this.solution = "";
	}
	
	public ApoNotSoSimpleUserLevels getUserLevels() {
		return this.userLevels;
	}

	private void makeLoose() {
		this.iLoose = new BufferedImage(ApoConstants.GAME_WIDTH, ApoConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = this.iLoose.createGraphics();
		
		this.getGame().render(g);
		
		g.dispose();
		
		this.curLooseTime = this.MAX_LOOSE_TIME;
		
		this.getGame().setShouldRepaint(true);
	}
	
	private void makeBackground() {
		this.iBackground = new BufferedImage(ApoConstants.GAME_WIDTH, ApoConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = this.iBackground.createGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.getGame().renderBackground(g);
		
		Stroke stroke = g.getStroke();
		g.setStroke(new BasicStroke(5));
		
		g.setColor(Color.DARK_GRAY);
		int startX = 5;
		int startY = 5;
		int width = (int)(65) * 7 + 5;
		int height = (int)(65) * 7 + 5;
		g.drawRoundRect(startX, startY, width, height, 20, 20);
		int w = (int)(ApoNotSoSimpleConstants.TILE_SIZE);
		g.drawLine(startX + width, height - 150, startX + width + 60, height - 150);
		
		g.drawLine(startX + width, startY + 40, startX + width + 130, startY + 40);

		g.setColor(Color.BLACK);
		g.fillOval(startX + width + 60, height - 150 - w/2, (int)(ApoNotSoSimpleConstants.TILE_SIZE), (int)(ApoNotSoSimpleConstants.TILE_SIZE));
		g.setColor(Color.DARK_GRAY);
		g.drawOval(startX + width + 60, height - 150 - w/2, (int)(ApoNotSoSimpleConstants.TILE_SIZE), (int)(ApoNotSoSimpleConstants.TILE_SIZE));

		w = (int)(ApoNotSoSimpleGame.POINTS_WIDTH);
		g.setColor(Color.BLACK);
		g.fillOval(startX + width + 130, startY + 40 - w/2, (int)(ApoNotSoSimpleGame.POINTS_WIDTH), (int)(ApoNotSoSimpleGame.POINTS_WIDTH));
		g.setColor(Color.DARK_GRAY);
		g.drawOval(startX + width + 130, startY + 40 - w/2, (int)(ApoNotSoSimpleGame.POINTS_WIDTH), (int)(ApoNotSoSimpleGame.POINTS_WIDTH));
		
		g.setColor(Color.BLACK);
		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		String s = "level";
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, startX + width + 10, height - 155);
		
		s = "moves";
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, startX + width + 10, startY + 35);
		
		g.setStroke(stroke);
		
		if (this.curLevel != null) {
			this.curLevel.renderBackground(g, 0, 0);
		}
		
		g.dispose();
	}
	
	public String getSolution() {
		return this.solution;
	}

	public void nextLevel() {
		if (this.level == -2) {
			this.getGame().setEditor(true);
		} else if (level == -3) {
			this.userLevels.setCurLevel(this.userLevels.getCurLevel() + 1);
			this.makeLevel(this.level);
		} else {
			this.makeLevel(this.level + 1);
		}
		 
	}
	
	public void restart() {
		this.makeLevel(this.level);
		this.getGame().render();
	}
	
	public void makeLevel(int level) {
		this.makeLoose();
		this.getGame().setBWin(false);
		this.getGame().setButtonVisible(ApoNotSoSimpleConstants.BUTTON_GAME);
		this.strings = new ArrayList<ApoNotSoSimpleString>();
		this.level = level;
		this.moves = 0;
		this.solution = "";
		if (level == -2) {
			this.curLevel.restart();
		} else if (level == -3) {
			this.curLevel = this.userLevels.getLevel(this.userLevels.getCurLevel(), this.sort);
			for (int i = 23; i <= 26; i++) {
				this.getGame().getButtons()[i].setBVisible(true);
			}
		} else {
			this.curLevel = ApoNotSoSimpleLevelGeneration.getLevel(this.level);
			if (this.curLevel == null) {
				this.level = 0;
				this.curLevel = ApoNotSoSimpleLevelGeneration.getLevel(this.level);	
			}
		}
		this.makeBackground();
		this.getGame().setShouldRepaint(true);
		this.getGame().render();
	}
	
	public void setLevelFromEditor(ApoNotSoSimpleLevel level) {
		this.curLevel = level;
		this.makeLevel(-2);
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			if (this.level == -2) {
				this.getGame().setEditor(false);
			} else {
				this.getGame().setLevelChooser();
			}
		} else if (button == KeyEvent.VK_R) {
			this.restart();
		}
		if (this.curLooseTime > 0) {
			
		} else {
			if (button == KeyEvent.VK_N) {
				if (this.level >= 0) {
					if (this.level + 1 <= this.getGame().getMaxLevels()) {
						this.nextLevel();
					}
				} else {
					this.nextLevel();
				}
			} else if (button == KeyEvent.VK_P) {
				if (this.level == -3) {
					this.userLevels.setCurLevel(this.userLevels.getCurLevel() - 1);
					this.makeLevel(this.level);
				} else {
					this.makeLevel(this.level - 1);
				}
			} else if (button == KeyEvent.VK_C) {
				this.curLevel.setCheat(false);
				this.moves += 1;
				this.getGame().render();
			} else {
				this.changeDirection(button);
			}
		}
	}
	
	private void changeDirection(int direction) {
		if (this.curLooseTime <= 0) {
			if (this.curLevel != null) {
				if (direction == KeyEvent.VK_LEFT) {
					if (this.curLevel.changeDirection(ApoNotSoSimpleConstants.PLAYER_DIRECTION_LEFT)) {
						this.addMove();
						this.solution += "l";
					}
				} else if (direction == KeyEvent.VK_RIGHT) {
					if (this.curLevel.changeDirection(ApoNotSoSimpleConstants.PLAYER_DIRECTION_RIGHT)) {
						this.addMove();
						this.solution += "r";
					}
				} else if (direction == KeyEvent.VK_UP) {
					if (this.curLevel.changeDirection(ApoNotSoSimpleConstants.PLAYER_DIRECTION_UP)) {
						this.addMove();
						this.solution += "u";
					}
				} else if (direction == KeyEvent.VK_DOWN) {
					if (this.curLevel.changeDirection(ApoNotSoSimpleConstants.PLAYER_DIRECTION_DOWN)) {
						this.addMove();
						this.solution += "d";
					}
				}
			}
		}
	}
	
	private void addMove() {
		this.moves += 1;
		this.getGame().setShouldRepaint(true);
		this.getGame().playSound(ApoNotSoSimplePanel.SOUND_POP, -1);
	}

	@Override
	public void mouseButtonFunction(String function) {
		if ((function.equals(ApoNotSoSimpleGame.MENU)) ||
			(function.equals(ApoNotSoSimpleGame.MENU_SCORE))) {
			if (this.level == -2) {
				this.getGame().setEditor(false);
			} else {
				this.getGame().setLevelChooser();
			}
		} else if (function.equals(ApoNotSoSimpleGame.LEFT_SORT)) {
			this.changeSort(-1);
		} else if (function.equals(ApoNotSoSimpleGame.RIGHT_SORT)) {
			this.changeSort(+1);
		} else if (function.equals(ApoNotSoSimpleGame.LEFT_LEVEL)) {
			this.changeLevel(-1);
		} else if (function.equals(ApoNotSoSimpleGame.RIGHT_LEVEL)) {
			this.changeLevel(+1);
		}
	}
	
	private void changeSort(int change) {
		this.sort += change;
		if (this.sort < 0) {
			this.sort = ApoNotSoSimpleUserLevels.SORT_SOLUTION;
		} else if (this.sort > ApoNotSoSimpleUserLevels.SORT_SOLUTION) {
			this.sort = 0;
		}
		this.userLevels.setCurLevel(0);
		this.makeLevel(this.level);
	}
	
	private void changeLevel(int change) {
		if (this.level >= 0) {
			this.level += change;
		} else if (this.level == -3) {
			this.userLevels.setCurLevel(this.userLevels.getCurLevel() + change);
		}
		this.makeLevel(this.level);
	}

	@Override
	public void mouseButtonReleased(int mouseX, int mouseY, boolean bRight) {
	}
	
	@Override
	public boolean mousePressed(int x, int y, boolean bRight) {
		return false;
	}
	
	@Override
	public boolean mouseMoved(int mouseX, int mouseY) {
		return true;
	}
	
	@Override
	public boolean mouseDragged(int x, int y, boolean bRight) {
		return this.mouseMoved(x, y);
	}
	
	public int getPoints() {
		return this.points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getLevelX(float checkX) {
		checkX -= 10;
		int levelWidth = (int)(ApoNotSoSimpleConstants.TILE_SIZE + 5);
		return (int)(checkX / levelWidth);
	}
	
	public int getLevelY(float checkY) {
		checkY -= 10;
		int levelHeight = (int)(ApoNotSoSimpleConstants.TILE_SIZE + 5);
		return (int)(checkY / levelHeight);
	}

	@Override
	public void think(int delta) {
		for (int i = this.strings.size() - 1; i >= 0; i--) {
			this.strings.get(i).think(delta);
			if (!this.strings.get(i).isBVisible()) {
				this.strings.remove(i);
			}
		}
		if (this.curLooseTime > 0) {
			this.curLooseTime -= delta;
			if (this.curLooseTime <= 0) {
				if (this.curLevel != null) {
					this.getGame().setShouldRepaint(false);
					this.getGame().render();
				}
			}
		} else if (this.curLevel != null) {
			if (!this.curLevel.think(delta)) {
				if (this.curLevel.shouldChange()) {
					if (!this.getGame().shouldRepaint()) {
						this.getGame().setShouldRepaint(true);
					}
				}
				int[][] collision = this.curLevel.checkMovement(delta);
				for (int p = 0; p < collision.length; p++) {
					if (this.curLevel.isLayerVisible(p)) {
						for (int e = 0; e < collision[0].length; e++) {
							if (collision[p][e] == ApoNotSoSimpleConstants.PLAYER_DIRECTION_FINISH) {
								this.curLevel.changeLevelVisible(p);
							} else if (collision[p][e] >= 0) {
								this.getGame().playSound(ApoNotSoSimplePanel.SOUND_LOOSE, -1);
								this.makeLoose();
								this.restart();
								return;
							}
						}
					}
				}
				if (!this.curLevel.isVisible()) {
					this.getGame().playSound(ApoNotSoSimplePanel.SOUND_WIN, -1);
					if (this.level >= 0) {
						this.getGame().setMaxLevel(this.level + 1);
					}
					this.nextLevel();
				}
				if (this.getGame().getKeyboard().isPressed(KeyEvent.VK_C)) {
					this.curLevel.setCheat(true);
					this.getGame().render();
				} else {
					if ((this.getGame().getKeyboard().isPressed(KeyEvent.VK_LEFT)) ||
						(this.getGame().getKeyboard().isPressed(KeyEvent.VK_RIGHT)) ||
						(this.getGame().getKeyboard().isPressed(KeyEvent.VK_UP)) ||
						(this.getGame().getKeyboard().isPressed(KeyEvent.VK_DOWN))) {
						if (this.getGame().getKeyboard().isPressed(KeyEvent.VK_LEFT)) {
							this.changeDirection(KeyEvent.VK_LEFT);
						} else if (this.getGame().getKeyboard().isPressed(KeyEvent.VK_RIGHT)) {
							this.changeDirection(KeyEvent.VK_RIGHT);
						} else if (this.getGame().getKeyboard().isPressed(KeyEvent.VK_UP)) {
							this.changeDirection(KeyEvent.VK_UP);
						} else if (this.getGame().getKeyboard().isPressed(KeyEvent.VK_DOWN)) {
							this.changeDirection(KeyEvent.VK_DOWN);
						}
					} else if (this.getGame().shouldRepaint()) {
						if ((this.getGame().getKeyboard().isPressed(KeyEvent.VK_LEFT)) ||
							(this.getGame().getKeyboard().isPressed(KeyEvent.VK_RIGHT)) ||
							(this.getGame().getKeyboard().isPressed(KeyEvent.VK_UP)) ||
							(this.getGame().getKeyboard().isPressed(KeyEvent.VK_DOWN))) {
						} else {
							if (this.curLooseTime <= 0) {
								this.getGame().setShouldRepaint(false);
								this.getGame().render();
							}
						}
					}
				}
			}
		}
	}
	
	public ArrayList<ApoNotSoSimpleString> getStrings() {
		return this.strings;
	}

	public void setStrings(ArrayList<ApoNotSoSimpleString> strings) {
		this.strings = strings;
	}

	public int getMoves() {
		return this.moves;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}

	@Override
	public void render(Graphics2D g) {
		if (this.iBackground != null) {
			g.drawImage(this.iBackground, 0, 0, null);
		}
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);		
		try {
			for (int i = this.strings.size() - 1; i >= 0; i--) {
				this.strings.get(i).render(g, 0, 0);
			}
		} catch (Exception ex) {	
		}
		
		int nextH = (int)(ApoNotSoSimpleConstants.TILE_SIZE + 6);
		g.setFont(ApoNotSoSimpleGame.FONT_TEXTFIELD);
		int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
		String s = String.valueOf(this.level + 1);
		if (this.level == -3) {
			s = String.valueOf(this.userLevels.getCurLevel() + 1);
		} else if (this.level == -2) {
			s = String.valueOf("E");
		}
		for (int i = 0; i < s.length(); i++) {
			String p = s.substring(i, i + 1);
			int w = g.getFontMetrics().stringWidth(p);
			int x = 5 + (int)(65) * 7 + 5 + 60 + ApoNotSoSimpleConstants.TILE_SIZE/2;
			int y = (int)(65) * 7 + 5 - 150 + h/2;
			x += i * (nextH);
			if (i >= 1) {
				Stroke stroke = g.getStroke();
				g.setStroke(new BasicStroke(5));
				g.setColor(Color.BLACK);
				g.fillOval(x - ApoNotSoSimpleConstants.TILE_SIZE/2, y - h/2 - ApoNotSoSimpleConstants.TILE_SIZE/2, (int)(ApoNotSoSimpleConstants.TILE_SIZE), (int)(ApoNotSoSimpleConstants.TILE_SIZE));
				g.setColor(Color.DARK_GRAY);
				g.drawOval(x - ApoNotSoSimpleConstants.TILE_SIZE/2, y - h/2 - ApoNotSoSimpleConstants.TILE_SIZE/2, (int)(ApoNotSoSimpleConstants.TILE_SIZE), (int)(ApoNotSoSimpleConstants.TILE_SIZE));
				g.drawLine(x - ApoNotSoSimpleConstants.TILE_SIZE/2 - 5, y - h/2, x - ApoNotSoSimpleConstants.TILE_SIZE/2, y - h/2);
				g.setStroke(stroke);
			}
			g.setColor(Color.WHITE);
			g.drawString(p, x - w/2, y);
		}

		nextH = (int)(ApoNotSoSimpleGame.POINTS_WIDTH + 6);
		s = String.valueOf(this.moves);
		int x = 5 + (int)(65) * 7 + 5 + 130 + ApoNotSoSimpleGame.POINTS_WIDTH/2;
		for (int i = 0; i < s.length(); i++) {
			String p = s.substring(i, i + 1);
			int y = 5 + h/2 + 40 + i * (nextH);
			if (i >= 1) {
				Stroke stroke = g.getStroke();
				g.setStroke(new BasicStroke(5));
				g.setColor(Color.BLACK);
				g.fillOval(x - ApoNotSoSimpleGame.POINTS_WIDTH/2, y - h/2 - ApoNotSoSimpleGame.POINTS_WIDTH/2, (int)(ApoNotSoSimpleGame.POINTS_WIDTH), (int)(ApoNotSoSimpleGame.POINTS_WIDTH));
				g.setColor(Color.DARK_GRAY);
				g.drawOval(x - ApoNotSoSimpleGame.POINTS_WIDTH/2, y - h/2 - ApoNotSoSimpleGame.POINTS_WIDTH/2, (int)(ApoNotSoSimpleGame.POINTS_WIDTH), (int)(ApoNotSoSimpleGame.POINTS_WIDTH));
				g.drawLine(x, y - h/2 - ApoNotSoSimpleGame.POINTS_WIDTH/2 - 5, x, y - h/2 - ApoNotSoSimpleGame.POINTS_WIDTH/2);
				g.setStroke(stroke);
			}
			g.setColor(Color.WHITE);
			int w = g.getFontMetrics().stringWidth(p);
			g.drawString(p, x - w/2, y);
		}
		
		if (this.curLevel != null) {
			this.curLevel.render(g, 10, 0, false);
			if (this.curLevel.getLayer() == 1) {
				int levelGame = this.level;
				if (levelGame < 0) {
					levelGame = 50;
				}
				g.setColor(Color.BLACK);
				g.setFont(ApoNotSoSimpleGame.FONT_IMAGE_HELP);
				int imageX = 15;
				int imageY = ApoConstants.GAME_HEIGHT - ApoNotSoSimpleConstants.TILE_SIZE - 20;
				if (levelGame > 3) {
					Stroke stroke = g.getStroke();
					g.setStroke(new BasicStroke(5));
					g.drawLine(10, imageY - 40, 10 + ApoNotSoSimpleConstants.LEVEL_WIDTH, imageY - 40);
					g.setStroke(stroke);
					String help = "help";
					int w = g.getFontMetrics().stringWidth(help);
					g.drawString(help, ApoNotSoSimpleConstants.LEVEL_WIDTH/2 + 10 - w/2, imageY - 15);
					String imageString = "vertical";
					g.drawString(imageString, imageX + 10, imageY + 5);
					g.drawImage(ApoNotSoSimpleImages.ORIGINAL_UP, imageX, imageY + 5, null);
					imageX += ApoNotSoSimpleImages.ORIGINAL_UP.getWidth() + 5;
					if (levelGame > 7) {
						g.drawImage(ApoNotSoSimpleImages.ORIGINAL_DOWN, imageX, imageY + 5, null);
						imageX += ApoNotSoSimpleImages.ORIGINAL_DOWN.getWidth() + 15;
						if (levelGame > 11) {
							imageString = "on / off";
							g.drawString(imageString, imageX + 10, imageY + 5);
							g.drawImage(ApoNotSoSimpleImages.ORIGINAL_VISIBLE_TRUE, imageX, imageY + 5, null);
							imageX += ApoNotSoSimpleImages.ORIGINAL_VISIBLE_TRUE.getWidth() + 5;
							if (levelGame > 16) {
								g.drawImage(ApoNotSoSimpleImages.ORIGINAL_VISIBLE_FALSE, imageX, imageY + 5, null);
								imageX += ApoNotSoSimpleImages.ORIGINAL_VISIBLE_FALSE.getWidth() + 15;
								if (levelGame > 21) {
									imageString = "horizontal";
									g.drawString(imageString, imageX + 10, imageY + 5);
									g.drawImage(ApoNotSoSimpleImages.ORIGINAL_LEFT, imageX, imageY + 5, null);
									imageX += ApoNotSoSimpleImages.ORIGINAL_LEFT.getWidth() + 5;
									if (levelGame > 24) {
										g.drawImage(ApoNotSoSimpleImages.ORIGINAL_RIGHT, imageX, imageY + 5, null);
										imageX += ApoNotSoSimpleImages.ORIGINAL_RIGHT.getWidth() + 15;
									}
									if (levelGame > 27) {
										imageString = "button";
										g.drawString(imageString, imageX + 10, imageY + 5);
										g.drawImage(ApoNotSoSimpleImages.ORIGINAL_STEP, imageX, imageY + 5, null);
										imageX += ApoNotSoSimpleImages.ORIGINAL_STEP.getWidth() + 5;
										g.drawImage(ApoNotSoSimpleImages.ORIGINAL_STEP_FINISH, imageX, imageY + 5, null);
										imageX += ApoNotSoSimpleImages.ORIGINAL_STEP_FINISH.getWidth() + 5;
									}
								}
							}
						}
					}
				}
			}
		}
		if (this.curLooseTime > 0) {
			Shape shape = g.getClip();
			g.setClip(new Rectangle2D.Double(10, 0, ApoNotSoSimpleConstants.LEVEL_WIDTH, ApoConstants.GAME_HEIGHT));
			float percent = (float)this.curLooseTime / (float)(this.MAX_LOOSE_TIME);
			Composite composite = g.getComposite();
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, percent));
			g.drawImage(this.iLoose, 0, 0, null);
			g.setComposite(composite);
			g.setClip(shape);
		}
		if (this.level == -3) {
			g.setFont(ApoNotSoSimpleConstants.FONT_DESCRIPTIONS);
			final int curX = (ApoConstants.GAME_WIDTH - 10 - ApoNotSoSimpleConstants.LEVEL_WIDTH)/2 + 10 + ApoNotSoSimpleConstants.LEVEL_WIDTH;
			s = ApoNotSoSimpleUserLevels.SORT_STRING[this.sort];
			int w = g.getFontMetrics().stringWidth(s);
			final int curY = (int)(this.getGame().getButtons()[23].getY() + this.getGame().getButtons()[23].getHeight()/2 + g.getFontMetrics().getHeight()/2 - 1 * g.getFontMetrics().getDescent());
			g.drawString(s, curX - w/2, curY);
			
			s = "sorted by";
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, curX - w/2, curY - 30);
		}
	}

}
