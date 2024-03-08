package apoMarc.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import org.apogames.ApoConstants;
import org.apogames.ApoScreen;

import apoMarc.ApoMarcConstants;
import apoMarc.ApoMarcGameComponent;

public class ApoMarcPanel extends ApoMarcGameComponent {

	private static final long serialVersionUID = 3446351722587948000L;

	private final Font FONT = new Font(Font.SANS_SERIF, Font.BOLD, 15);
	
	private final int ACHIEVEMENT_TIME = 3000;
	
	private ApoMarcButtons buttons;
	
	private ApoMarcModel model;
	
	private ApoMarcMenu menu;
	
	private ApoMarcOptions options;
	
	private ApoMarcGame game;
	
	private ApoMarcDifficulty difficulty;

	private ApoMarcAchievements achievements;

	private ApoMarcCredits credits;
	
	private BufferedImage iBackground;
	
	private int curTime;
	
	private String achievementString;
	
	public ApoMarcPanel(ApoScreen screen) {
		super(screen);
	}
	
	public void load() {
		super.load();
		super.setShowFPS(false);
		
		if (this.iBackground == null) {
			BufferedImage iBack = this.getImages().getImage("images/background.png", false);
			this.iBackground = new BufferedImage(iBack.getWidth(), iBack.getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics2D g = this.iBackground.createGraphics();
			g.drawImage(iBack, 0, 0, null);
			g.dispose();
		}
		
		if (this.buttons == null) {
			this.buttons = new ApoMarcButtons(this);
			this.buttons.init();
		}
		if (this.menu == null) {
			this.menu = new ApoMarcMenu(this);
		}
		if (this.options == null) {
			this.options = new ApoMarcOptions(this);
		}
		if (this.game == null) {
			this.game = new ApoMarcGame(this);
		}
		if (this.difficulty == null) {
			this.difficulty = new ApoMarcDifficulty(this);
		}
		if (this.achievements == null) {
			this.achievements = new ApoMarcAchievements(this);
		}
		if (this.credits == null) {
			this.credits = new ApoMarcCredits(this);
		}
		this.setMenu();
	}
	
	public void setMenu() {
		this.model = this.menu;
		
		this.setButtonVisible(ApoMarcConstants.BUTTON_MENU);
	}
	
	public void setGame(int difficulty) {
		this.model = this.game;
		
		this.setButtonVisible(ApoMarcConstants.BUTTON_GAME);
		
		this.game.init(difficulty);
	}
	
	public void setDifficulty() {
		this.model = this.difficulty;
		
		this.setButtonVisible(ApoMarcConstants.BUTTON_DIFFICULTY);
		
		this.difficulty.init();
	}
	
	public void setAchievements() {
		this.model = this.achievements;
		
		this.setButtonVisible(ApoMarcConstants.BUTTON_ACHIEVEMENTS);
		
		this.achievements.init();
	}
	
	public void setCredits() {
		this.model = this.credits;
		
		this.setButtonVisible(ApoMarcConstants.BUTTON_CREDITS);
		
		this.credits.init();
	}
	
	public void setOptions() {
		this.model = this.options;
		
		this.setButtonVisible(ApoMarcConstants.BUTTON_OPTIONS);
		
		this.options.init();
	}
	
	private void setButtonVisible(boolean[] bVisibile) {
		for (int i = 0; i < this.getButtons().length && i < bVisibile.length; i++) {
			this.getButtons()[i].setBVisible(bVisibile[i]);
		}
		if (ApoConstants.B_APPLET) {
			this.getButtons()[0].setBVisible(false);
		}
	}
	
	@Override
	public void keyReleased(int keyCode) {
		super.keyReleased(keyCode);
		if (keyCode == KeyEvent.VK_F) {
			super.setShowFPS(!super.isShowFPS());
		}
		if (this.model != null) {
			this.model.keyButtonReleased(keyCode, (char)(keyCode));
		}
	}

	@Override
	public void setButtonFunction(String function) {
		if (this.model != null) {
			this.model.mouseButtonFunction(function);
		}
	}

	@Override
	public void mouseReleased(int x, int y, boolean bLeft) {
		super.mouseReleased(x, y, bLeft);
		if (this.model != null) {
			this.model.mouseButtonReleased(x, y);
		}
	}
	
	public void mousePressed(int x, int y, boolean bLeft) {
		super.mousePressed(x, y, bLeft);
		if (this.model != null) {
			this.model.mousePressed(x, y, !bLeft);
		}
	}
	
	public void mouseMoved(int x, int y) {
		super.mouseMoved(x, y);
		if (this.model != null) {
			this.model.mouseMoved(x, y);
		}
	}
	
	public void mouseDragged(int x, int y) {
		super.mouseDragged(x, y);
		if (this.model != null) {
			this.model.mouseDragged(x, y);
		}
	}
	
	public void addAchievement(String achievement) {
		if (this.achievements.addAchievements(achievement)) {
			this.curTime = this.ACHIEVEMENT_TIME;
			this.achievementString = achievement;
		}
	}
	
	public void think(long delta) {
		if (this.model != null) {
			this.model.think(delta);
		}
		if (this.curTime > 0) {
			this.curTime -= delta;
		}
	}

	@Override
	public void render(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (this.model != null) {
			this.model.render(g);
		}
		super.renderButtons(g);
		this.renderAchievements(g);
		this.renderFPS(g);
	}
	
	public void renderBackground(Graphics2D g) {
		if (this.iBackground == null) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, ApoMarcConstants.GAME_WIDTH, ApoMarcConstants.GAME_HEIGHT);
		} else {
			g.drawImage(this.iBackground, 0, 0, null);
		}
	}
	
	private void renderAchievements(Graphics2D g) {
		if (this.curTime > 0) {
			g.setFont(this.FONT);
			String s = "New Achievement: "+this.achievementString;
			int w = g.getFontMetrics().stringWidth(s);
			int h = g.getFontMetrics().getHeight();
			g.setColor(Color.WHITE);
			g.fillRoundRect(ApoMarcConstants.GAME_WIDTH/2 - w/2 - 10, ApoMarcConstants.GAME_HEIGHT - h - 30, w + 20, h + 20, 20, 20);
			g.setColor(Color.BLACK);
			g.drawRoundRect(ApoMarcConstants.GAME_WIDTH/2 - w/2 - 10, ApoMarcConstants.GAME_HEIGHT - h - 30, w + 20, h + 20, 20, 20);
			g.drawString(s, ApoMarcConstants.GAME_WIDTH/2 - w/2, ApoMarcConstants.GAME_HEIGHT - 25);
		}
	}
	
	/**
	 * rendert die Anzeige für die FPS und die Thinkzeit
	 * @param g : das Graphicsobjekt
	 */
	private void renderFPS(Graphics2D g) {
		if (super.isShowFPS()) {
			g.setColor(Color.RED);
			g.setFont(ApoMarcConstants.FONT_FPS);
			g.drawString("think time: " + (super.getThinkTime()) + " ms",5, ApoMarcConstants.GAME_HEIGHT - 35);
			g.drawString("draw time: " + (super.getRenderTime()) + " ms", 5, ApoMarcConstants.GAME_HEIGHT - 15);
			g.drawString("FPS: " + super.getFPS(), 5, ApoMarcConstants.GAME_HEIGHT - 5);
		}
	}

}
