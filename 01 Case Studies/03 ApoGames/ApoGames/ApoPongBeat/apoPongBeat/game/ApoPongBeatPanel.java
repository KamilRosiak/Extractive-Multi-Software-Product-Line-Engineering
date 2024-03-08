package apoPongBeat.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import org.apogames.ApoConstants;
import org.apogames.ApoScreen;

import apoPongBeat.ApoPongBeatComponent;
import apoPongBeat.ApoPongBeatConstants;
import apoPongBeat.entity.ApoPongBeatBall;
import apoPongBeat.entity.ApoPongBeatParticle;
import apoPongBeat.entity.ApoPongBeatRacket;

public class ApoPongBeatPanel extends ApoPongBeatComponent {

	private static final long serialVersionUID = 3446351722587948000L;

	/** Hilfsvariablen, um herauszufinden wie lange zum Nachdenken und Zeichnen gebraucht wurde */
	private long thinkTime, renderTime;
	
	private ApoPongBeatButtons buttons;
	
	private ApoPongBeatModel model;
	
	private ApoPongBeatMenu menu;
	
	private ApoPongBeatOptions options;
	
	private ApoPongBeatGame game;
	
	private Font myFont;
	private Font pointsFont;
	
	private ApoPongBeatRacket racket;
	
	private ArrayList<ApoPongBeatBall> balls;
	
	private ArrayList<ApoPongBeatParticle> particle;
	
	public final Color COLOR_X = new Color(50, 156, 152);
	public final Color COLOR_MULT = new Color(186, 67, 136);
	public final Color COLOR_POINTS = new Color(255, 200, 0);
	public final Color COLOR_COUNTER = new Color(68, 77, 145);
	public final Color COLOR_COUNTER_OVER = new Color(222, 116, 156);
	public final Color COLOR_RESET_OVER = new Color(255, 0, 0);
	
	public final int MAX_RESET_COUNTER = 20;
	public final int COUNTER_PLUS = 25;
	
	private int points;
	
	private int counter;
	
	private int mult;
	
	private int resetCounter;
	
	private int counterMulti;
	
	private ApoPongBeatSound sound;
	
	public ApoPongBeatPanel(ApoScreen screen) {
		super(screen);
	}
	
	public void init() {
		super.init();
		super.setShowFPS(false);
		
		if (this.myFont == null) {
			try {
				Font font = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/images/my.TTF"));
				this.myFont = font.deriveFont(50f);
				this.pointsFont = font.deriveFont(45f);
			} catch (FontFormatException e) {
				System.out.println(e.getMessage());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		if (this.buttons == null) {
			this.buttons = new ApoPongBeatButtons(this);
			this.buttons.init();
		}
		if (this.menu == null) {
			this.menu = new ApoPongBeatMenu(this);
		}
		if (this.options == null) {
			this.options = new ApoPongBeatOptions(this);
		}
		if (this.game == null) {
			this.game = new ApoPongBeatGame(this);
		}
		if (this.racket == null) {
			this.racket = new ApoPongBeatRacket(ApoPongBeatConstants.RACKET_X, ApoPongBeatConstants.GAME_HEIGHT/2 - ApoPongBeatConstants.RACKET_HEIGHT/2, ApoPongBeatConstants.RACKET_WIDTH, ApoPongBeatConstants.RACKET_HEIGHT);
		}
		if (this.balls == null) {
			this.balls = new ArrayList<ApoPongBeatBall>();
		}
		if (this.particle == null) {
			this.particle = new ArrayList<ApoPongBeatParticle>();
		}
		if (this.sound == null) {
			this.sound = new ApoPongBeatSound(this);
		}
		
		this.setMenu();
	}
	
	public void initGameStats() {
		this.points = 0;
		this.counter = 0;
		this.resetCounter = 0;
		this.counterMulti = 0;
		this.mult = 1;
		
		this.getBalls().clear();
		this.getParticle().clear();
	}
	
	public ArrayList<ApoPongBeatParticle> getParticle() {
		return this.particle;
	}

	public int getPoints() {
		return this.points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getCounter() {
		return this.counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public int getMult() {
		return this.mult;
	}

	public void setMult(int mult) {
		this.mult = mult;
	}

	public int getResetCounter() {
		return this.resetCounter;
	}

	public void setResetCounter(int resetCounter) {
		this.resetCounter = resetCounter;
	}

	public int getCounterMulti() {
		return this.counterMulti;
	}

	public void setCounterMulti(int counterMulti) {
		this.counterMulti = counterMulti;
	}

	public ArrayList<ApoPongBeatBall> getBalls() {
		return this.balls;
	}

	public ApoPongBeatRacket getRacket() {
		return this.racket;
	}

	public void setRacket(ApoPongBeatRacket racket) {
		this.racket = racket;
	}

	public Font getPointsFont() {
		return this.pointsFont;
	}

	public Font getMyFont() {
		return this.myFont;
	}

	public void setMenu() {
		this.model = this.menu;
		
		this.menu.init();
		
		this.setButtonVisible(ApoPongBeatConstants.BUTTON_MENU);
		this.sound.init();
	}
	
	public void setGame() {
		this.model = this.game;
		
		this.game.init();
		
		this.setButtonVisible(ApoPongBeatConstants.BUTTON_GAME);
		this.sound.init();
	}
	
	public void setOptions() {
		this.model = this.options;
		
		this.options.init();
		
		this.setButtonVisible(ApoPongBeatConstants.BUTTON_OPTIONS);
	}
	
	private void setButtonVisible(boolean[] bVisibile) {
		for (int i = 0; i < this.getButtons().length && i < bVisibile.length; i++) {
			this.getButtons()[i].setBVisible(bVisibile[i]);
		}
		if (ApoConstants.B_APPLET) {
			this.getButtons()[0].setBVisible(false);
		}
	}
	
	/**
	 * Methode zum Starten des Spiels
	 */
	public void startGame() {
		this.setGame();
	}
	
	@Override
	public void keyReleased(int keyCode, char keyChar) {
		if (keyCode == KeyEvent.VK_F) {
			super.setShowFPS(!super.isShowFPS());
		} else if (this.model != null) {
			this.model.keyButtonReleased(keyCode, keyChar);
		}
	}

	@Override
	public void setButtonFunction(String function) {
		if (this.model != null) {
			this.model.mouseButtonFunction(function);
		}
	}

	@Override
	public void mouseReleased(int x, int y, boolean left) {
		super.mouseReleased(x, y, left);
		if (this.model != null) {
			this.model.mouseButtonReleased(x, y);
		}
	}
	
	public void mouseMoved(int x, int y) {
		super.mouseMoved(x, y);
		if (this.model != null) {
			this.model.mouseMoved(x, y);
		}
	}
	
	protected void moveRacket(int x, int y) {
		int mouseY = (int)(y - this.getRacket().getHeight()/2);
		if (mouseY < ApoPongBeatConstants.MIN_Y) {
			mouseY = ApoPongBeatConstants.MIN_Y;
		} else if (mouseY + this.getRacket().getHeight() >= ApoPongBeatConstants.MAX_Y) {
			mouseY = (int)(ApoPongBeatConstants.MAX_Y - this.getRacket().getHeight());
		}
		this.getRacket().setY(mouseY);
	}
	
	public void ballOnBorder(boolean left) {
		if (this.model != null) {
			this.model.ballOnBorder(left);
		}
	}
	
	public void ballOnBorderGame(boolean left) {
		if (left) {
			this.counter = 0;
			this.counterMulti = 0;
			this.resetCounter += 1;
			if (this.resetCounter >= this.MAX_RESET_COUNTER) {
				this.mult = 1;
				this.resetCounter = 0;
			}
		}
	}
	
	public void ballOnRacket(int points) {
		if (this.model != null) {
			this.model.ballOnRacket(points);
		}
	}
	
	public void ballOnRacketGame(int points) {
		this.points += points * this.mult + this.counter;
		this.counter += 1;
		this.counterMulti += 1;
		if (this.counter % this.COUNTER_PLUS == 0) {
			this.mult += 1;
			this.counterMulti = 0;
		}
	}
	
	public void think(long delta) {
		long t = System.nanoTime();
		if (this.model != null) {
			this.model.think((int)delta);
		}
		this.thinkTime = (int) (System.nanoTime() - t);
		if ((this.getMult() >= 2) && (this.getMult() <= 4)) {
			this.sound.think((int)delta, this.getMult());
		}
	}
	
	public void thinkBalls(int delta) {
		for (int i = this.getBalls().size() - 1; i >= 0; i--) {
			this.getBalls().get(i).think(delta, this);
			if (!this.getBalls().get(i).isBVisible()) {
				this.getBalls().remove(i);
			}
		}
	}
	
	public void thinkParticles(int delta) {
		for (int i = this.getParticle().size() - 1; i >= 0; i--) {
			this.getParticle().get(i).think(delta, this);
			if (!this.getParticle().get(i).isBVisible()) {
				this.getParticle().remove(i);
			}
		}
	}

	@Override
	public void render(Graphics2D g) {
		long t = System.nanoTime();
		if (this.model != null) {
			this.model.render(g);
		}
		super.renderButtons(g);
		this.renderFPS(g);
		this.renderTime = (int)(System.nanoTime() - t);
	}
	
	public void renderBackground(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, ApoPongBeatConstants.GAME_WIDTH, ApoPongBeatConstants.GAME_HEIGHT);
	}
	
	/**
	 * rendert die Anzeige für die FPS und die Thinkzeit
	 * @param g : das Graphicsobjekt
	 */
	private void renderFPS(Graphics2D g) {
		if (super.isShowFPS()) {
			g.setColor(Color.red);
			g.setFont(ApoPongBeatConstants.FONT_FPS);
			g.drawString("think time: " + this.thinkTime + " ns", 5, ApoPongBeatConstants.GAME_HEIGHT - 45);
			g.drawString("think time: " + (this.thinkTime / 1000000) + " ms",5, ApoPongBeatConstants.GAME_HEIGHT - 35);

			g.drawString("draw time: " + this.renderTime + " ns", 5, ApoPongBeatConstants.GAME_HEIGHT - 25);
			g.drawString("draw time: " + (this.renderTime / 1000000) + " ms", 5, ApoPongBeatConstants.GAME_HEIGHT - 15);
			g.drawString("FPS: " + super.getFPS(), 5, ApoPongBeatConstants.GAME_HEIGHT - 5);
		}
	}
	
	public void renderString(Graphics2D g, String s, int x, int y) {
		int w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, x - w/2, y);
	}
	
	public void renderBorder(Graphics2D g, Color color, int percent, Color overColor, int resetPercent, Color resetColor) {
		g.setColor(color);
		int height = 10;
		g.fillRect(ApoPongBeatConstants.RACKET_X, ApoPongBeatConstants.MIN_Y - height, ApoPongBeatConstants.GAME_WIDTH - 2 * ApoPongBeatConstants.RACKET_X, height);
		g.fillRect(ApoPongBeatConstants.RACKET_X, ApoPongBeatConstants.MAX_Y, ApoPongBeatConstants.GAME_WIDTH - 2 * ApoPongBeatConstants.RACKET_X, height);
		
		g.setColor(color.darker());
		g.drawRect(ApoPongBeatConstants.RACKET_X, ApoPongBeatConstants.MIN_Y - height, ApoPongBeatConstants.GAME_WIDTH - 2 * ApoPongBeatConstants.RACKET_X, height);
		g.drawRect(ApoPongBeatConstants.RACKET_X, ApoPongBeatConstants.MAX_Y, ApoPongBeatConstants.GAME_WIDTH - 2 * ApoPongBeatConstants.RACKET_X, height);
		
		if (percent > 0) {
			g.setColor(overColor);
			
			int width = (ApoPongBeatConstants.GAME_WIDTH - 2 * ApoPongBeatConstants.RACKET_X - 4) * percent / 100;
			g.fillRect(ApoPongBeatConstants.RACKET_X + 2, ApoPongBeatConstants.MAX_Y + 2, width, height - 4);	
		}
		if (resetPercent > 0) {
			g.setColor(resetColor);
			
			int width = (ApoPongBeatConstants.GAME_WIDTH - 2 * ApoPongBeatConstants.RACKET_X - 4) * resetPercent / 100;
			g.fillRect(ApoPongBeatConstants.RACKET_X + 2, ApoPongBeatConstants.MIN_Y - height + 2, width, height - 4);
		}
	}
	
	public void renderBalls(Graphics2D g) {
		for (int i = 0; i < this.balls.size(); i++) {
			this.balls.get(i).render(g, 0, 0);
		}
	}
	
	public void renderParticles(Graphics2D g) {
		for (int i = this.particle.size() - 1; i >= 0; i--) {
			this.particle.get(i).render(g, 0, 0);
		}
	}
	
	public void renderStats(Graphics2D g) {
		this.renderBorder(g, Color.YELLOW, (int)((float)this.counterMulti * 100f / (float)this.COUNTER_PLUS), this.COLOR_COUNTER_OVER, (int)((float)this.resetCounter * 100f / (float)this.MAX_RESET_COUNTER), this.COLOR_RESET_OVER);
		g.setColor(this.COLOR_POINTS);
		g.setFont(this.getPointsFont());
		String s = this.getPointString(this.points, 7);
		int x = ApoPongBeatConstants.RACKET_X;
		g.drawString(s, x, ApoPongBeatConstants.MAX_Y + 45);
		x += g.getFontMetrics().stringWidth(s) + 5;
		g.setColor(this.COLOR_X);		
		s = "X";
		g.drawString(s, x, ApoPongBeatConstants.MAX_Y + 45);
		x += g.getFontMetrics().stringWidth(s) + 5;
		s = this.getPointString(this.mult, 3);
		g.setColor(this.COLOR_MULT);
		g.drawString(s, x, ApoPongBeatConstants.MAX_Y + 45);
		x += g.getFontMetrics().stringWidth(s) + 5;
		g.setColor(this.COLOR_X);		
		s = "X";
		g.drawString(s, x, ApoPongBeatConstants.MAX_Y + 45);
		x += g.getFontMetrics().stringWidth(s) + 5;
		g.setColor(this.COLOR_COUNTER);		
		s = this.getPointString(this.counter, 5);
		g.drawString(s, x, ApoPongBeatConstants.MAX_Y + 45);
	}
	
	public String getPointString(int points, int length) {
		String s = String.valueOf(points);
		while (s.length() < length) {
			s = "0" + s;
		}
		return s;
	}

}
