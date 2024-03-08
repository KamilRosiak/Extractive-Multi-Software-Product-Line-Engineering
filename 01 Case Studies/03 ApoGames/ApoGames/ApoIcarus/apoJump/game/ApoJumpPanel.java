package apoJump.game;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import org.apogames.ApoConstants;
import org.apogames.ApoScreen;

import apoJump.ApoJumpComponent;
import apoJump.ApoJumpConstants;
import apoJump.ApoJumpImageContainer;
import apoJump.level.ApoJumpLevel;

public class ApoJumpPanel extends ApoJumpComponent {

	/** Hilfsvariablen, um herauszufinden wie lange zum Nachdenken und Zeichnen gebraucht wurde */
	private long thinkTime, renderTime;
	
	private ApoJumpButton buttons;
	
	private ApoJumpStateMenu menu;
	private ApoJumpStateGame game;
	private ApoJumpStateOptions options;
	private ApoJumpStateHelp help;
	private ApoJumpStateAchievements achievements;
	private ApoJumpStateHighscore highscore;
	
	private ApoJumpState model;
	
	private ApoJumpLevel level;
	
	private BufferedImage iBackground;
	
	public ApoJumpPanel(ApoScreen screen) {
		super(screen);
	}
	
	public void init() {
		super.init();
		ApoJumpImageContainer.load();

		this.makeBackground();
		
		if (this.buttons == null) {
			this.buttons = new ApoJumpButton(this);
			this.buttons.init();
		}
		if (this.menu == null) {
			this.menu = new ApoJumpStateMenu(this);
		}
		if (this.game == null) {
			this.game = new ApoJumpStateGame(this);
		}
		if (this.options == null) {
			this.options = new ApoJumpStateOptions(this);
		}
		if (this.help == null) {
			this.help = new ApoJumpStateHelp(this);
		}
		if (this.achievements == null) {
			this.achievements = new ApoJumpStateAchievements(this);
		}
		if (this.highscore == null) {
			this.highscore = new ApoJumpStateHighscore(this);
		}
		if (this.level == null) {
			this.level = new ApoJumpLevel(this);
		}
		this.highscore.init();
		this.achievements.loadAchievements();
		this.level.init();
		this.setMenu();
	}
	
	public final ApoJumpLevel getLevel() {
		return this.level;
	}

	public ApoJumpStateAchievements getAchievements() {
		return this.achievements;
	}

	public final ApoJumpStateHighscore getHighscore() {
		return this.highscore;
	}

	private void makeBackground() {
		this.iBackground = new BufferedImage(ApoJumpConstants.GAME_WIDTH, ApoJumpConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = this.iBackground.createGraphics();

		Paint paint = g.getPaint();
		GradientPaint gradientPaint = new GradientPaint(iBackground.getWidth()/2, 0, new Color(100, 150, 200), iBackground.getWidth()/2, iBackground.getHeight(), new Color(160, 225, 254));
		g.setPaint(gradientPaint);
		g.fillRect(0, 0, iBackground.getWidth(), iBackground.getHeight());
		g.setPaint(paint);
		
		g.dispose();
	}
	
	public void setMenu() {
		this.model = this.menu;
		this.model.init();
		this.setButtons(ApoJumpConstants.BUTTONS_MENU);
		this.level.init();
		this.render();
	}
	
	public void setGame() {
		this.model = this.game;
		this.model.init();
		this.setButtons(ApoJumpConstants.BUTTONS_GAME);
		this.level.init();
	}
	
	public void setOptions() {
		this.model = this.options;
		this.model.init();
		this.setButtons(ApoJumpConstants.BUTTONS_OPTIONS);
		this.render();
	}
	
	public void setHelp() {
		this.model = this.help;
		this.model.init();
		this.setButtons(ApoJumpConstants.BUTTONS_HELP);
		this.render();
	}
	
	public void setAchievements() {
		this.model = this.achievements;
		this.model.init();
		this.setButtons(ApoJumpConstants.BUTTONS_ACHIEVEMENTS);
		this.render();
	}
	
	public void setHighscore() {
		this.model = this.highscore;
		this.setButtons(ApoJumpConstants.BUTTONS_HIGHSCORE);
		this.model.init();
		this.render();
	}
	
	public void setBWin(boolean bWin) {
		super.setBWin(bWin);
		if (bWin) {
			this.game.setWin();
		}
	}
	
	private void setButtons(boolean[] buttons) {
		for (int i = 0; i < this.getButtons().length && i < buttons.length; i++) {
			this.getButtons()[i].setBVisible(buttons[i]);
		}
		if (ApoConstants.B_APPLET) {
			this.getButtons()[0].setBVisible(false);
		}
	}
	
	@Override
	public void keyReleased(int button, char character) {
		if (button == KeyEvent.VK_F) {
			super.setShowFPS(!super.isShowFPS());
			this.render();
		}
		if (this.model != null) {
			this.model.keyButtonReleased(button, character);
		}
	}

	@Override
	public void setButtonFunction(String function) {
		if (this.model != null) {
			this.model.mouseButtonFunction(function);
		}
	}
	
	public void mouseMoved(int x, int y) {
		super.mouseMoved(x, y);
		if (this.model != null) {
			this.model.mouseMoved(x, y);
		}
	}
	
	public void mousePressed(int x, int y, boolean left) {
		super.mousePressed(x, y, left);
		if (this.model != null) {
			this.model.mousePressed(x, y, !left);
		}
	}
	
	public void mouseDragged(int x, int y, boolean left) {
		if (this.model != null) {
			this.model.mouseDragged(x, y);
		}
	}
	
	public void mouseReleased(int x, int y, boolean left) {
		super.mouseReleased(x, y, left);
		if (this.model != null) {
			this.model.mouseButtonReleased(x, y);
		}
	}
	
	public void think(long delta) {
		long t = System.nanoTime();
		if (this.model != null) {
			this.model.think((int)delta);
		}
		this.thinkTime = (int) (System.nanoTime() - t);
	}

	@Override
	public void render(Graphics2D g) {
		long t = System.nanoTime();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (this.model != null) {
			this.model.render(g);
		}
		super.renderButtons(g);
		this.renderFPS(g);
		this.renderTime = (int)(System.nanoTime() - t);
	}
	
	public void renderBackground(Graphics2D g) {
		if (this.iBackground == null) {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, ApoJumpConstants.GAME_WIDTH, ApoJumpConstants.GAME_HEIGHT);
		} else {
			g.drawImage(this.iBackground, 0, 0, null);
		}
	}
	
	/**
	 * rendert die Anzeige für die FPS und die Thinkzeit
	 * @param g : das Graphicsobjekt
	 */
	private void renderFPS(Graphics2D g) {
		if (super.isShowFPS()) {
			g.setColor(Color.red);
			g.setFont(ApoJumpConstants.FONT_FPS);
			g.drawString("think time: " + this.thinkTime + " ns", 5, ApoJumpConstants.GAME_HEIGHT - 45);
			g.drawString("think time: " + (this.thinkTime / 1000000) + " ms",5, ApoJumpConstants.GAME_HEIGHT - 35);

			g.drawString("draw time: " + this.renderTime + " ns", 5, ApoJumpConstants.GAME_HEIGHT - 25);
			g.drawString("draw time: " + (this.renderTime / 1000000) + " ms", 5, ApoJumpConstants.GAME_HEIGHT - 15);
			g.drawString("FPS: " + super.getFPS(), 5, ApoJumpConstants.GAME_HEIGHT - 5);
		}
	}


}
