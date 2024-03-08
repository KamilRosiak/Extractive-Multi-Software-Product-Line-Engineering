package apoRelax.game;

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

import apoRelax.ApoRelaxConstants;
import apoRelax.entity.ApoRelaxString;
import apoRelax.level.ApoRelaxLevel;
import apoRelax.level.ApoRelaxLevelGeneration;

public class ApoRelaxGame extends ApoRelaxState {
	
	private final int MAX_LOOSE_TIME = 1000;
	
	public static final int POINTS_WIDTH = 40;
	
	public static final String MENU_STRING = "menu";
	public static final String MENU = "game_menu";
	public static final String UPLOAD_STRING = "upload";
	public static final String UPLOAD = "game_upload";
	public static final String MENU_SCORE_STRING = "menu";
	public static final String MENU_SCORE = "game_menuscore";
	public static final String NEW_STRING = "restart";
	public static final String NEW = "game_restart";
	public static final String NEXT = "game_next";
	
	public static final Font FONT_SCORE = new Font(Font.SANS_SERIF, Font.BOLD, 60);
	public static final Font FONT_STATICS = new Font(Font.SANS_SERIF, Font.BOLD, 40);
	public static final Font FONT_RESTART = new Font(Font.SANS_SERIF, Font.BOLD, 25);
	public static final Font FONT_IMAGE_HELP = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
	public static final Font FONT_TEXTFIELD = new Font(Font.SANS_SERIF, Font.BOLD, 30);
	
	public static final int LEVEL_UP_TIME = 80;
	
	public static final int UPLOAD_TIME = 3000;
	public static final int UPLOAD_TIME_TEST = 5000;
	
	private ApoRelaxLevel curLevel;
	
	private BufferedImage iBackground;
	
	private int points = 0;
	
	private int level = -5;
	
	private int moves = 0;

	private ArrayList<ApoRelaxString> strings;

	private BufferedImage iLoose;
	
	private int curLooseTime;
	
	private String solution;
	
	private ApoRelaxRandomLevels userLevels;
	
	public ApoRelaxGame(ApoRelaxPanel game) {
		super(game);
		
		if (this.userLevels == null) {
			this.userLevels = new ApoRelaxRandomLevels();
		}
	}
	
	public void init() {
		if (this.level < 0) {
			this.makeLevel(0);
		}
		
		this.curLooseTime = 0;
		this.getGame().setShouldRepaint(false);
		this.solution = "";
	}
	
	public void init(int level) {
		if (level >= 0) {
			this.makeLevel(level);
		}
	}
	
	public void initRandomLevels() {
		this.level = -3;
		
		this.makeLevel(this.level);
		
		this.curLooseTime = 0;
		this.getGame().setShouldRepaint(false);
		this.solution = "";
	}
	
	public ApoRelaxRandomLevels getUserLevels() {
		return this.userLevels;
	}

	private void makeLoose() {
		this.iLoose = new BufferedImage(ApoRelaxConstants.GAME_WIDTH, ApoRelaxConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = this.iLoose.createGraphics();
		
		this.getGame().render(g);
		
		g.dispose();
		
		this.curLooseTime = this.MAX_LOOSE_TIME;
		
		this.getGame().setShouldRepaint(true);
	}
	
	private void makeBackground() {
		this.iBackground = new BufferedImage(ApoRelaxConstants.GAME_WIDTH, ApoRelaxConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
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
		int size = 45;
		int w = (int)(size);
		g.drawLine(startX + width, height - 150, startX + width + 60, height - 150);
		
		g.drawLine(startX + width, startY + 40, startX + width + 130, startY + 40);

		g.setColor(Color.BLACK);
		g.fillOval(startX + width + 60, height - 150 - w/2, (int)(size), (int)(size));
		g.setColor(Color.DARK_GRAY);
		g.drawOval(startX + width + 60, height - 150 - w/2, (int)(size), (int)(size));

		w = (int)(ApoRelaxGame.POINTS_WIDTH);
		g.setColor(Color.BLACK);
		g.fillOval(startX + width + 130, startY + 40 - w/2, (int)(ApoRelaxGame.POINTS_WIDTH), (int)(ApoRelaxGame.POINTS_WIDTH));
		g.setColor(Color.DARK_GRAY);
		g.drawOval(startX + width + 130, startY + 40 - w/2, (int)(ApoRelaxGame.POINTS_WIDTH), (int)(ApoRelaxGame.POINTS_WIDTH));
		
		g.setColor(Color.BLACK);
		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		String s = "level";
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, startX + width + 10, height - 155);
		
		s = "swaps";
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, startX + width + 10, startY + 35);
		
		g.setStroke(stroke);
		
		g.dispose();
	}
	
	public String getSolution() {
		return this.solution;
	}

	public void nextLevel() {
		if (this.level == -2) {
			this.getGame().setEditor();
		} else if (level == -3) {
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
		this.getGame().setButtonVisible(ApoRelaxConstants.BUTTON_GAME);
		this.getGame().setShouldRepaint(false);
		this.strings = new ArrayList<ApoRelaxString>();
		this.level = level;
		this.moves = 0;
		this.solution = "";
		this.makeBackground();
		this.getGame().setShouldRepaint(true);
		this.strings.add(new ApoRelaxString(225, 200, "=== load level ===", true));
		this.getGame().render();
		if (level == -2) {
			this.curLevel.restart();
		} else if (level == -3) {
			this.getGame().render();
			this.curLevel = this.userLevels.getRandomLevel();
		} else {
			this.curLevel = ApoRelaxLevelGeneration.getLevel(this.level);
			if (this.curLevel == null) {
				this.level = 0;
				this.curLevel = ApoRelaxLevelGeneration.getLevel(this.level);	
			}
		}
		this.strings.clear();
		this.getGame().render();
	}
	
	public void setLevelFromEditor(ApoRelaxLevel level) {
		this.curLevel = level;
		this.makeLevel(-2);
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			if (this.level == -2) {
				this.getGame().setEditor();
			} else {
				this.getGame().setMenu();
			}
		} else if (button == KeyEvent.VK_R) {
			this.restart();
		}
		if (this.curLooseTime > 0) {
			
		} else {
			if (button == KeyEvent.VK_N) {
				if (this.level + 1 <= this.getGame().getMaxLevels()) {
					this.nextLevel();
				}
			} else if (button == KeyEvent.VK_P) {
				if (this.level == -3) {
					this.makeLevel(this.level);
				} else {
					this.makeLevel(this.level - 1);
				}
			}
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		if ((function.equals(ApoRelaxGame.MENU)) ||
			(function.equals(ApoRelaxGame.MENU_SCORE))) {
			if (this.level == -2) {
				this.getGame().setEditor();
			} else {
				this.getGame().setLevelChooser();
			}
		} else if (function.equals(ApoRelaxGame.NEXT)) {
			this.nextLevel();
		}
	}

	@Override
	public void mouseButtonReleased(int mouseX, int mouseY, boolean bRight) {
		if (this.curLevel != null) {
			if (this.curLevel.isCorrect()) {
				if (new Rectangle2D.Double(0, 0, 450, ApoRelaxConstants.GAME_HEIGHT).contains(mouseX, mouseY)) {
					this.nextLevel();
				}
			} else {
				int released = this.curLevel.mouseReleased(mouseX, mouseY, bRight);
				if (released >= 1) {
					if (released == 1) {
						this.moves += 1;
					}
					this.getGame().playSound(ApoRelaxPanel.SOUND_TILE, 100);
					this.getGame().render();
				}
			}
		}
	}
	
	@Override
	public boolean mousePressed(int x, int y, boolean bRight) {
		return false;
	}
	
	@Override
	public boolean mouseMoved(int mouseX, int mouseY) {
		if (this.curLevel != null) {
			if (this.curLevel.isCorrect()) {
			} else if (this.curLevel.mouseMoved(mouseX, mouseY, this.getGame())) {
				this.getGame().render();
			}
		}
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

	@Override
	public void think(int delta) {
		for (int i = this.strings.size() - 1; i >= 0; i--) {
			this.strings.get(i).think(delta);
			if (!this.strings.get(i).isBVisible()) {
				this.strings.remove(i);
			}
			if (i == 0) {
				if (this.strings.size() <= 0) {
					this.getGame().setShouldRepaint(false);
				}
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
			if (!this.getGame().getButtons()[11].isBVisible()) {
				if (this.curLevel.think(delta)) {
					if (!this.getGame().shouldRepaint()) {
						this.getGame().setShouldRepaint(true);
					}
				} else {
					if (this.getGame().shouldRepaint()) {
						this.getGame().setShouldRepaint(false);
						this.getGame().render();
					}
				}
				if (this.curLevel.isCorrect()) {
					this.getGame().getButtons()[11].setBVisible(true);
					this.strings.add(new ApoRelaxString(225, 200, "=== Congratulation ===", true));
					this.strings.add(new ApoRelaxString(250, 400, "Press next to start the next level", true));
					this.getGame().setShouldRepaint(true);
					this.getGame().playSound(ApoRelaxPanel.SOUND_WIN, 100);
					if (this.level >= 0) {
						this.getGame().setMaxLevel(this.level + 1);
					}
				}
			}
		}
	}
	
	public ArrayList<ApoRelaxString> getStrings() {
		return this.strings;
	}

	public void setStrings(ArrayList<ApoRelaxString> strings) {
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
		
		int size = 45;
		int nextH = (int)(size + 6);
		g.setFont(ApoRelaxGame.FONT_TEXTFIELD);
		int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
		String s = String.valueOf(this.level + 1);
		if (this.level == -3) {
			s = String.valueOf("R");
		} else if (this.level == -2) {
			s = String.valueOf("E");
		}
		for (int i = 0; i < s.length(); i++) {
			String p = s.substring(i, i + 1);
			int w = g.getFontMetrics().stringWidth(p);
			int x = 5 + (int)(65) * 7 + 5 + 60 + size/2;
			int y = (int)(65) * 7 + 5 - 150 + h/2;
			x += i * (nextH);
			if (i >= 1) {
				Stroke stroke = g.getStroke();
				g.setStroke(new BasicStroke(5));
				g.setColor(Color.BLACK);
				g.fillOval(x - size/2, y - h/2 - size/2, (int)(size), (int)(size));
				g.setColor(Color.DARK_GRAY);
				g.drawOval(x - size/2, y - h/2 - size/2, (int)(size), (int)(size));
				g.drawLine(x - size/2 - 5, y - h/2, x - size/2, y - h/2);
				g.setStroke(stroke);
			}
			g.setColor(Color.WHITE);
			g.drawString(p, x - w/2, y);
		}

		nextH = (int)(ApoRelaxGame.POINTS_WIDTH + 6);
		s = String.valueOf(this.moves);
		int x = 5 + (int)(65) * 7 + 5 + 130 + ApoRelaxGame.POINTS_WIDTH/2;
		for (int i = 0; i < s.length(); i++) {
			String p = s.substring(i, i + 1);
			int y = 5 + h/2 + 40 + i * (nextH);
			if (i >= 1) {
				Stroke stroke = g.getStroke();
				g.setStroke(new BasicStroke(5));
				g.setColor(Color.BLACK);
				g.fillOval(x - ApoRelaxGame.POINTS_WIDTH/2, y - h/2 - ApoRelaxGame.POINTS_WIDTH/2, (int)(ApoRelaxGame.POINTS_WIDTH), (int)(ApoRelaxGame.POINTS_WIDTH));
				g.setColor(Color.DARK_GRAY);
				g.drawOval(x - ApoRelaxGame.POINTS_WIDTH/2, y - h/2 - ApoRelaxGame.POINTS_WIDTH/2, (int)(ApoRelaxGame.POINTS_WIDTH), (int)(ApoRelaxGame.POINTS_WIDTH));
				g.drawLine(x, y - h/2 - ApoRelaxGame.POINTS_WIDTH/2 - 5, x, y - h/2 - ApoRelaxGame.POINTS_WIDTH/2);
				g.setStroke(stroke);
			}
			g.setColor(Color.WHITE);
			int w = g.getFontMetrics().stringWidth(p);
			g.drawString(p, x - w/2, y);
		}
		
		if (this.curLevel != null) {
			this.curLevel.render(g, 10, 15);
		}
		if (this.curLooseTime > 0) {
			Shape shape = g.getClip();
			g.setClip(new Rectangle2D.Double(10, 0, 450, ApoRelaxConstants.GAME_HEIGHT));
			float percent = (float)this.curLooseTime / (float)(this.MAX_LOOSE_TIME);
			Composite composite = g.getComposite();
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, percent));
			g.drawImage(this.iLoose, 0, 0, null);
			g.setComposite(composite);
			g.setClip(shape);
		}
		
		try {
			for (int i = this.strings.size() - 1; i >= 0; i--) {
				this.strings.get(i).render(g, 0, 0);
			}
		} catch (Exception ex) {	
		}
	}
}
