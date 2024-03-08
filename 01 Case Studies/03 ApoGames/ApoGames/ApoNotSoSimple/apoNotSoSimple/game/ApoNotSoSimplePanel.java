package apoNotSoSimple.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;

import org.apogames.ApoConstants;
import org.apogames.ApoScreen;
import org.apogames.help.ApoHelp;
import org.apogames.sound.ApoMP3SoundHandler;

import apoNotSoSimple.ApoNotSoSimpleComponent;
import apoNotSoSimple.ApoNotSoSimpleConstants;
import apoNotSoSimple.level.ApoNotSoSimpleLevel;

public class ApoNotSoSimplePanel extends ApoNotSoSimpleComponent {

	public static final String SOUND_POP = "pop";
	public static final String SOUND_BUTTON = "button";
	public static final String SOUND_BACKGROUND = "background";
	public static final String SOUND_LOOSE = "loose";
	public static final String SOUND_WIN = "win";
	
	/** Hilfsvariablen, um herauszufinden wie lange zum Nachdenken und Zeichnen gebraucht wurde */
	private long thinkTime, renderTime;
	
	private ApoNotSoSimpleButtons buttons;
	
	private ApoNotSoSimpleModel model;
	
	private ApoNotSoSimpleMenu menu;
	
	private ApoNotSoSimpleGame game;

	private ApoNotSoSimpleEditor editor;

	private ApoNotSoSimpleCredits credits;

	private ApoNotSoSimpleOptions options;
	
	private ApoNotSoSimpleLevelChooser levelChooser;
	
	private ApoMP3SoundHandler sounds;
	
	private boolean bSoundEffects;
	
	public ApoNotSoSimplePanel(ApoScreen screen) {
		super(screen);
	}
	
	public void init() {
		super.init();
		super.setShowFPS(false);
		
		if (this.sounds == null) {
			this.sounds = new ApoMP3SoundHandler();
			this.sounds.loadSound(ApoNotSoSimplePanel.SOUND_BACKGROUND, "/sounds/background.mp3");
			this.sounds.loadSound(ApoNotSoSimplePanel.SOUND_POP, "/sounds/pop.mp3");
			this.sounds.loadSound(ApoNotSoSimplePanel.SOUND_BUTTON, "/sounds/button.mp3");
			this.sounds.loadSound(ApoNotSoSimplePanel.SOUND_LOOSE, "/sounds/loose.mp3");
			this.sounds.loadSound(ApoNotSoSimplePanel.SOUND_WIN, "/sounds/win.mp3");

			//this.sounds.playSound(ApoNotSoSimplePanel.SOUND_BACKGROUND, true, 90);
			
			this.bSoundEffects = true;
		}
		
		if (this.buttons == null) {
			this.buttons = new ApoNotSoSimpleButtons(this);
			this.buttons.init();
		}
		if (this.menu == null) {
			this.menu = new ApoNotSoSimpleMenu(this);
		}
		if (this.game == null) {
			this.game = new ApoNotSoSimpleGame(this);
		}
		if (this.editor == null) {
			this.editor = new ApoNotSoSimpleEditor(this);
		}
		if (this.credits == null) {
			this.credits = new ApoNotSoSimpleCredits(this);
		}
		if (this.options == null) {
			this.options = new ApoNotSoSimpleOptions(this);
		}
		if (this.levelChooser == null) {
			this.levelChooser = new ApoNotSoSimpleLevelChooser(this);
		}
		this.game.getUserLevels().loadHighscore();
		
		if (ApoConstants.B_APPLET) {
			String load;
			try {
				load = ApoHelp.loadData(new URL(ApoNotSoSimpleConstants.PROGRAM_URL), ApoNotSoSimpleConstants.COOKIE_NAME);
				int level = Integer.valueOf(load);
				this.levelChooser.setMaxLevel(level);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		this.setMenu();
	}
	
	public boolean isSoundEffects() {
		return this.bSoundEffects;
	}

	public void setSoundEffects(boolean soundEffects) {
		this.bSoundEffects = soundEffects;
	}

	public void stopGame() {
		this.sounds.stopAll();
	}
	
	public void setMenu() {
		this.model = this.menu;
		
		this.setButtonVisible(ApoNotSoSimpleConstants.BUTTON_MENU);
		
		this.model.init();
		
		this.render();
	}
	
	public void playSound(String sound, int volumen) {
		this.playSound(sound, false, volumen);
	}
	
	public void playSound(String sound, boolean bLoop, int volumen) {
		if (sound.equals(ApoNotSoSimplePanel.SOUND_BACKGROUND)) {
			this.sounds.playSound(sound, bLoop, volumen);
		} else if (this.bSoundEffects) {
			this.sounds.playSound(sound, bLoop, volumen);
		}
	}
	
	public void stopSound(String sound) {
		this.sounds.stopSound(sound);
	}
	
	public void setGame(int level) {
		this.model = this.game;
		
		this.setButtonVisible(ApoNotSoSimpleConstants.BUTTON_GAME);
		
		this.model.init();
		
		this.model.init(level);
		
		this.render();
	}
	
	public void setUserGame() {
		if (this.game.getUserLevels().getLevel(0, ApoNotSoSimpleUserLevels.SORT_SOLUTION) != null) {
			this.model = this.game;
			
			this.setButtonVisible(ApoNotSoSimpleConstants.BUTTON_GAME);
			
			this.game.initUserLevels();
			
			this.render();
		} else {
			
		}
	}
	
	public void loadHighscore() {
		this.game.getUserLevels().loadHighscore();
	}
	
	public void setCredits() {
		this.model = this.credits;
		
		this.setButtonVisible(ApoNotSoSimpleConstants.BUTTON_CREDITS);
		
		this.model.init();
		
		this.render();
	}
	
	public void setLevelChooser() {
		this.model = this.levelChooser;
		
		this.setButtonVisible(ApoNotSoSimpleConstants.BUTTON_LEVELCHOOSER);
		
		this.model.init();
		
		this.render();
	}
	
	public void setOptions() {
		this.model = this.options;
		
		this.setButtonVisible(ApoNotSoSimpleConstants.BUTTON_OPTIONS);
		
		this.model.init();
		
		this.render();
	}
	
	public void setEditor(boolean bUpload) {
		this.model = this.editor;
		
		this.setButtonVisible(ApoNotSoSimpleConstants.BUTTON_EDITOR);
		
		this.model.init();
		this.editor.setbUpload(bUpload);
		
		this.render();
	}
	
	public int getMaxLevels() {
		return this.levelChooser.getMaxLevel();
	}
	
	public void setMaxLevel(final int level) {
		if (this.levelChooser.setMaxLevel(level)) {
			if (ApoConstants.B_APPLET) {
				try {
					ApoHelp.saveData(new URL(ApoNotSoSimpleConstants.PROGRAM_URL), ApoNotSoSimpleConstants.COOKIE_NAME, String.valueOf(level));
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public String getSolution() {
		return this.game.getSolution();
	}
	
	public void setGameWithLevel(final ApoNotSoSimpleLevel level) {
		this.setGame(-1);
		this.game.setLevelFromEditor(level);
	}
	
	public void setButtonVisible(boolean[] bVisibile) {
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
	public void startGame(int level) {
		this.setGame(level);
	}
	
	@Override
	public void keyReleased(int keyCode, char keyChar) {
		if (keyCode == KeyEvent.VK_F) {
			super.setShowFPS(!super.isShowFPS());
			this.render();
		}
		if (this.model != null) {
			this.model.keyButtonReleased(keyCode, keyChar);
		}
	}

	@Override
	public void setButtonFunction(String function) {
		if (this.model != null) {
			this.model.mouseButtonFunction(function);
			if (!function.equals(ApoNotSoSimpleMenu.QUIT)) {
				this.playSound(ApoNotSoSimplePanel.SOUND_BUTTON, 100);
			}
		}
	}

	@Override
	public void mouseReleased(int x, int y, boolean left) {
		super.mouseReleased(x, y, left);
		if (this.model != null) {
			this.model.mouseButtonReleased(x, y, !left);
		}
	}
	
	public void mousePressed(int x, int y, boolean left) {
		super.mousePressed(x, y, left);
		if (this.model != null) {
			this.model.mousePressed(x, y, !left);
		}
	}
	
	public void mouseMoved(int x, int y) {
		super.mouseMoved(x, y);
		if (this.model != null) {
			this.model.mouseMoved(x, y);
		}
	}
	
	public void mouseDragged(int x, int y, boolean left) {
		super.mouseDragged(x, y, left);
		if (this.model != null) {
			this.model.mouseDragged(x, y, !left);
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
		if (this.model != null) {
			this.model.render(g);
		}
		super.renderButtons(g);
		this.renderFPS(g);
		this.renderTime = (int)(System.nanoTime() - t);
	}
	
	public void renderBackground(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, ApoConstants.GAME_WIDTH, ApoConstants.GAME_HEIGHT);
	}
	
	/**
	 * rendert die Anzeige für die FPS und die Thinkzeit
	 * @param g : das Graphicsobjekt
	 */
	private void renderFPS(Graphics2D g) {
		if (super.isShowFPS()) {
			g.setColor(Color.red);
			g.setFont(ApoNotSoSimpleConstants.FONT_FPS);
			g.drawString("think time: " + this.thinkTime + " ns", 5, ApoConstants.GAME_HEIGHT - 45);
			g.drawString("think time: " + (this.thinkTime / 1000000) + " ms",5, ApoConstants.GAME_HEIGHT - 35);

			g.drawString("draw time: " + this.renderTime + " ns", 5, ApoConstants.GAME_HEIGHT - 25);
			g.drawString("draw time: " + (this.renderTime / 1000000) + " ms", 5, ApoConstants.GAME_HEIGHT - 15);
			g.drawString("FPS: " + super.getFPS(), 5, ApoConstants.GAME_HEIGHT - 5);
		}
	}

}
