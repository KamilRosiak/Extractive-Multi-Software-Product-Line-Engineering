package apoCommando.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.apogames.ApoConstants;
import org.apogames.ApoScreen;

import apoCommando.ApoMarioConstants;
import apoCommando.ApoMarioGameComponent;
import apoCommando.entity.ApoMarioPlayer;
import apoCommando.level.ApoMarioAchievements;
import apoCommando.level.ApoMarioStats;
import apoCommando.panels.ApoMarioAnalysis;
import apoCommando.panels.ApoMarioGame;
import apoCommando.panels.ApoMarioMenu;
import apoCommando.panels.ApoMarioModel;

/**
 * Klasse, die sich um das eigentliche Spiel kümmert und alle Objekt überwacht, anspricht und steuert
 * @author Dirk Aporius
 *
 */
public class ApoMarioPanel extends ApoMarioGameComponent {
	
	private final int ACHIEVEMENT_TIME = 5000;
	
	private boolean bShow;
	
	/** Hilfsvariablen, um herauszufinden wie lange zum Nachdenken und Zeichnen gebraucht wurde */
	private long thinkTime, renderTime;
	
	private boolean bJump;
	
	private boolean bInit;
	
	private ApoMarioAnalysis analysis;
	
	private ApoMarioGame game;
	
	private ApoMarioModel model;
	
	private ApoMarioButtons buttons;
	
	private BufferedImage iMenuBackground;
	
	private int speedCounter;
	
	private ArrayList<ApoMarioStats> allCommands;
	
	private ApoMarioAchievements achievements;
	
	private int achievementTime;
	
	private boolean bWin;
	
	private boolean bFirstStart;
	
	public ApoMarioPanel(ApoScreen screen) {
		super(screen);
	}
	
	public void init() {
		this.bInit = true;
		super.init();
		this.bJump = false;
		super.setBPause(false);
		this.allCommands = new ArrayList<ApoMarioStats>();
		this.bFirstStart = true;
		this.bShow = true;
		if (this.achievements == null) {
			this.achievements = new ApoMarioAchievements(this);
		}
		if (this.buttons == null) {
			this.buttons = new ApoMarioButtons(this);
			this.buttons.makeButtons();
		}
		if (this.analysis == null) {
			this.analysis = new ApoMarioAnalysis(this);
		}
		if (this.game == null) {
			this.game = new ApoMarioGame(this);
		}
		this.setAnalysis();
		
		this.bInit = false;
		this.speedCounter = 0;
	}

	public boolean isBWin() {
		return this.bWin;
	}

	public void setBWin(boolean bWin) {
		this.bWin = bWin;
	}
	
	public void newAchievement() {
		this.achievementTime = this.ACHIEVEMENT_TIME;
	}
	
	public ApoMarioAchievements getAchievements() {
		return this.achievements;
	}

	public ArrayList<ApoMarioStats> getAllCommands() {
		return this.allCommands;
	}

	public void setAllCommands(ArrayList<ApoMarioStats> allCommands) {
		this.allCommands = allCommands;
	}

	public void setDebugMode(boolean bDebug) {
	}

	public int getSpeedCounter() {
		return this.speedCounter;
	}

	public void setSpeedCounter(int speedCounter) {
		this.speedCounter = speedCounter;
	}

	public boolean isBJump() {
		return this.bJump;
	}
	
	public void setBJump(boolean bJump) {
		this.bJump = bJump;
	}

	public BufferedImage getBackgroundImage() {
		BufferedImage iMenuBackground = new BufferedImage(ApoMarioConstants.GAME_WIDTH, ApoMarioConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
		boolean bOrg = ApoMarioConstants.DEBUG;
		ApoMarioConstants.DEBUG = false;
		Graphics2D g = (Graphics2D)iMenuBackground.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.getLevel().render(g, (int)this.getChangeX(), (int)this.getChangeY());
		ApoMarioConstants.DEBUG = bOrg;
		g.dispose();
		return iMenuBackground;
	}
	
	public ApoMarioGame getGame() {
		return this.game;
	}

	public void setGame(boolean bTutorial) {
		super.setShouldThink(false);
		super.setShouldRepaint(false);
		this.setBPause(false);
		this.bFirstStart = false;
		this.restartLevel();
		ApoMarioPanel game = this;
		if (!bTutorial) {
			if (game.getAchievements() != null) {
				if (ApoMarioConstants.DIFFICULTY == 2) {
					game.getAchievements().addAchievement(ApoMarioAchievements.ACHIEVEMENT_EASY_GAME_START);
				} else if (ApoMarioConstants.DIFFICULTY == 3) {
					game.getAchievements().addAchievement(ApoMarioAchievements.ACHIEVEMENT_MEDIUM_GAME_START);
				} else if (ApoMarioConstants.DIFFICULTY == 4) {
					game.getAchievements().addAchievement(ApoMarioAchievements.ACHIEVEMENT_HARD_GAME_START);
				}
			}
		}
		
		this.setButtons(ApoMarioConstants.BUTTON_GAME);
		
		this.model = this.game;
		this.game.init(bTutorial);
		
		super.setShouldThink(true);
		super.setShouldRepaint(true);
	}
	
	public boolean isBFirstStart() {
		return this.bFirstStart;
	}

	public void setAnalysis() {
		this.setBPause(false);
		this.model = this.analysis;
		
		this.setButtons(ApoMarioConstants.BUTTON_ANALYSIS);
		
		this.analysis.init();
	}
	
	private void setButtons(boolean[] visible) {
		for (int i = 0; i < visible.length && i < this.getButtons().length; i++) {
			this.getButtons()[i].setBVisible(visible[i]);
		}
		if (ApoConstants.B_APPLET) {
			this.getButtons()[0].setBVisible(false);
		}
	}

	@Override
	public void setButtonFunction(String function) {
		if (this.model != null) {
			this.model.mouseButtonFunction(function);
		}
	}

	public void keyPressed(int button, char character) {
		
	}
	
	public void keyReleased(int button, char character) {
		if ((button == KeyEvent.VK_PAGE_DOWN) || (button == KeyEvent.VK_PAGE_UP)) {
			ApoMarioConstants.FPS = !ApoMarioConstants.FPS;
		}
		this.model.keyButtonReleased(button, character);
	}
	
	public void restartLevel() {
		this.getLevel().restart();
		super.setChangeX(0);
	}
	
	public void newLevel() {
		this.newLevel(-1, -1, null);
	}
	
	public void newLevel(int width, int difficulty, String currentLevelString) {
		if (width < 0) {
			this.getLevel().newLevel(currentLevelString);
		} else {
			this.getLevel().newLevel(width, difficulty, currentLevelString);
		}
		super.setChangeX(0);
	}
	
	@Override
	public void mouseReleased(int x, int y, boolean left) {
		if (this.model != null) {
			this.model.mouseButtonReleased(x, y);
		}
	}
	
	public void mousePressed(int x, int y, boolean left) {
		if (this.model != null) {
			this.model.mousePressed(x, y);
		}
	}
	
	public void mouseMoved(int x, int y) {
		if (this.model != null) {
			this.model.mouseMoved(x, y);
		}
	}
	
	public void mouseDragged(int x, int y, boolean left) {
		if (this.model != null) {
			this.model.mouseDragged(x, y);
		}
	}
	
	public BufferedImage getIMenuBackground() {
		return this.iMenuBackground;
	}

	public void think(long delta) {
		if (this.bInit) {
			return;
		}
		this.achievements.addTime((int)delta);
		long t = System.nanoTime();
		this.thinkLevelGame((int)delta);
		this.thinkTime = (int) (System.nanoTime() - t);
		if (this.achievementTime >= 0) {
			this.achievementTime -= delta;
		}
	}
	
	public void thinkLevelGame(int delta) {
		if (this.model != null) {
			this.model.think(delta);
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		long t = System.nanoTime();
		Shape s = g.getClip();
		g.setClip(0, 0, ApoMarioConstants.GAME_WIDTH, ApoMarioConstants.GAME_HEIGHT);
		try {	
			if (this.model != null) {
				this.model.render(g);
			}
		} catch (Exception ex) {
			System.out.println("Exception beim zeichnen!");
			ex.printStackTrace();
		}
		this.renderLevelWidthStatistics(g);
		this.renderFPS(g);
		this.renderPause(g);
		this.renderAchievements(g);
		g.setClip(s);
		this.renderTime = (int)(System.nanoTime() - t);
	}
	
	private void renderLevelWidthStatistics(Graphics2D g) {
		g.setFont(ApoMarioConstants.FONT_STATISTICS);
		int difWidth = 20;
		int levelWidth = ApoMarioConstants.GAME_WIDTH - difWidth * 2;
		float maxWidth = -1;
		if (this.getLevel() != null) {
			ApoMarioPlayer player = this.getLevel().getPlayer();
			if (player.isBVisible()) {
				float marioWidth = (player.getX() * 100f / (this.getLevel().getWidth() * ApoMarioConstants.TILE_SIZE));
				if (maxWidth < marioWidth) {
					maxWidth = marioWidth;
				}
				g.setColor(Color.RED);
				g.drawString("M", difWidth + levelWidth * marioWidth / 100, ApoMarioConstants.GAME_HEIGHT - 5);
			}
			int distance = 15 * ApoMarioConstants.SIZE;
			for (int i = 0; i < levelWidth / distance; i++) {
				int width = i * distance;
				if ((float)width / (float)levelWidth < maxWidth / 100f) {
					g.setColor(Color.GREEN);
				} else {
					g.setColor(Color.BLACK);
				}
				g.fillRect(difWidth + width, ApoMarioConstants.GAME_HEIGHT - 10, 5, 5);
			}
			String s = "F";
			int w = g.getFontMetrics().stringWidth(s);
			
			g.drawString(s, difWidth + levelWidth - w, ApoMarioConstants.GAME_HEIGHT - 5);
		}
	}

	/**
	 * rendert die Anzeige für die FPS und die Thinkzeit
	 * @param g : das Graphicsobjekt
	 */
	private void renderFPS(Graphics2D g) {
		if (ApoMarioConstants.FPS) {
			g.setColor(Color.red);
			g.setFont(ApoMarioConstants.FONT_FPS);
			g.drawString("think time: " + this.thinkTime + " ns", 5, ApoMarioConstants.GAME_HEIGHT - 45);
			g.drawString("think time: " + (this.thinkTime / 1000000) + " ms",5, ApoMarioConstants.GAME_HEIGHT - 35);

			g.drawString("draw time: " + this.renderTime + " ns", 5, ApoMarioConstants.GAME_HEIGHT - 25);
			g.drawString("draw time: " + (this.renderTime / 1000000) + " ms", 5, ApoMarioConstants.GAME_HEIGHT - 15);
			g.drawString("FPS: " + super.getFPS(), 5, ApoMarioConstants.GAME_HEIGHT - 5);
		}
	}
	
	private void renderPause(Graphics2D g) {
		if ((super.isBPause()) && (this.bShow)) {
		}
	}
	
	private void renderAchievements(Graphics2D g) {
		if (this.achievementTime > 0) {
			g.setFont(ApoMarioConstants.FONT_DOS_BOX);
			g.setColor(new Color(196, 215, 234));
			g.fillRoundRect(5, ApoMarioConstants.GAME_HEIGHT - 40, ApoMarioConstants.GAME_WIDTH - 10, 38, 15, 15);
			g.setColor(Color.BLACK);
			g.drawRoundRect(5, ApoMarioConstants.GAME_HEIGHT - 40, ApoMarioConstants.GAME_WIDTH - 10, 38, 15, 15);
			g.fillRect(10, ApoMarioConstants.GAME_HEIGHT - 30, ApoMarioConstants.GAME_WIDTH - 20, 25);
			g.setColor(Color.WHITE);
			String s = "C:\\Achievements>"+this.getAchievements().getAchievements().get(this.getAchievements().getAchievements().size() - 1);
			g.drawString(s, 15, ApoMarioConstants.GAME_HEIGHT - 10);
		}
	}

}