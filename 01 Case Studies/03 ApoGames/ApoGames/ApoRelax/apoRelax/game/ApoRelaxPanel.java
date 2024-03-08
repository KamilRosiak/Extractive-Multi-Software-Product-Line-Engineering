package apoRelax.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;

import org.apogames.ApoConstants;
import org.apogames.ApoScreen;
import org.apogames.help.ApoHelp;
import org.apogames.sound.ApoMP3SoundHandler;

import apoRelax.ApoRelaxComponent;
import apoRelax.ApoRelaxConstants;
import apoRelax.level.ApoRelaxLevel;

public class ApoRelaxPanel extends ApoRelaxComponent {

	public static final String SOUND_TILE = "tile";
	public static final String SOUND_BUTTON = "button";
	public static final String SOUND_BACKGROUND = "background";
	public static final String SOUND_WIN = "win";
	
	/** Hilfsvariablen, um herauszufinden wie lange zum Nachdenken und Zeichnen gebraucht wurde */
	private long thinkTime, renderTime;
	
	private ApoRelaxButtons buttons;
	
	private ApoRelaxState model;
	
	private ApoRelaxMenu menu;
	
	private ApoRelaxGame game;

	private ApoRelaxEditor editor;

	private ApoRelaxCredits credits;

	private ApoRelaxOptions options;

	private ApoRelaxLevelChooser levelChooser;;
	
	private ApoMP3SoundHandler sounds;
	
	private boolean bSoundEffects;
	
	public ApoRelaxPanel(ApoScreen screen) {
		super(screen);
	}
	
	public void init() {
		super.init();
		super.setShowFPS(false);
		
		if (this.sounds == null) {
			this.sounds = new ApoMP3SoundHandler();
			this.sounds.loadSound(ApoRelaxPanel.SOUND_BACKGROUND, "/sounds/background.mp3");
			this.sounds.loadSound(ApoRelaxPanel.SOUND_TILE, "/sounds/tile.mp3");
			this.sounds.loadSound(ApoRelaxPanel.SOUND_BUTTON, "/sounds/button.mp3");
			this.sounds.loadSound(ApoRelaxPanel.SOUND_WIN, "/sounds/win.mp3");

//			this.sounds.playSound(ApoRelaxPanel.SOUND_BACKGROUND, true, 90);
			
			this.bSoundEffects = true;
		}
		
		if (this.buttons == null) {
			this.buttons = new ApoRelaxButtons(this);
			this.buttons.init();
		}
		if (this.menu == null) {
			this.menu = new ApoRelaxMenu(this);
		}
		if (this.game == null) {
			this.game = new ApoRelaxGame(this);
		}
		if (this.editor == null) {
			this.editor = new ApoRelaxEditor(this);
		}
		if (this.credits == null) {
			this.credits = new ApoRelaxCredits(this);
		}
		if (this.options == null) {
			this.options = new ApoRelaxOptions(this);
		}
		if (this.levelChooser == null) {
			this.levelChooser = new ApoRelaxLevelChooser(this);
		}
		if (ApoConstants.B_APPLET) {
			String load;
			try {
				load = ApoHelp.loadData(new URL("http://www.apo-games.de/apoRelax/"), "level");
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
		
		this.setButtonVisible(ApoRelaxConstants.BUTTON_MENU);
		
		this.model.init();
		
		this.render();
	}
	
	public void playSound(String sound, int volumen) {
		this.playSound(sound, false, volumen);
	}
	
	public void playSound(String sound, boolean bLoop, int volumen) {
		if (sound.equals(ApoRelaxPanel.SOUND_BACKGROUND)) {
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
		
		this.setButtonVisible(ApoRelaxConstants.BUTTON_GAME);
		
		this.model.init(level);
		
		this.render();
	}
	
	public void setRandomGame() {
		this.model = this.game;
		
		this.setButtonVisible(ApoRelaxConstants.BUTTON_GAME);
		
		this.game.initRandomLevels();
		
		this.render();
	}
	
	public void setCredits() {
		this.model = this.credits;
		
		this.setButtonVisible(ApoRelaxConstants.BUTTON_CREDITS);
		
		this.model.init();
		
		this.render();
	}
		
	public void setOptions() {
		this.model = this.options;
		
		this.setButtonVisible(ApoRelaxConstants.BUTTON_OPTIONS);
		
		this.model.init();
		
		this.render();
	}
	
	public void setLevelChooser() {
		this.model = this.levelChooser;
		
		this.setButtonVisible(ApoRelaxConstants.BUTTON_LEVELCHOOSER);
		
		this.model.init();
		
		this.render();
	}
	
	public void setEditor() {
		this.model = this.editor;
		
		this.setButtonVisible(ApoRelaxConstants.BUTTON_EDITOR);
		
		this.model.init();
		
		this.render();
	}
	
	public int getMaxLevels() {
		return this.levelChooser.getMaxLevel();
	}
	
	public void setMaxLevel(final int level) {
		if (this.levelChooser.setMaxLevel(level)) {
			if (ApoConstants.B_APPLET) {
				try {
					ApoHelp.saveData(new URL("http://www.apo-games.de/apoRelax/"), "level", String.valueOf(level));
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
	
	public void setGameWithLevel(final ApoRelaxLevel level) {
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
			if (!function.equals(ApoRelaxMenu.QUIT)) {
				this.playSound(ApoRelaxPanel.SOUND_BUTTON, 100);
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
		g.fillRect(0, 0, ApoRelaxConstants.GAME_WIDTH, ApoRelaxConstants.GAME_HEIGHT);
	}
	
	/**
	 * rendert die Anzeige für die FPS und die Thinkzeit
	 * @param g : das Graphicsobjekt
	 */
	private void renderFPS(Graphics2D g) {
		if (super.isShowFPS()) {
			g.setColor(Color.red);
			g.setFont(ApoRelaxConstants.FONT_FPS);
			g.drawString("think time: " + this.thinkTime + " ns", 5, ApoRelaxConstants.GAME_HEIGHT - 45);
			g.drawString("think time: " + (this.thinkTime / 1000000) + " ms",5, ApoRelaxConstants.GAME_HEIGHT - 35);

			g.drawString("draw time: " + this.renderTime + " ns", 5, ApoRelaxConstants.GAME_HEIGHT - 25);
			g.drawString("draw time: " + (this.renderTime / 1000000) + " ms", 5, ApoRelaxConstants.GAME_HEIGHT - 15);
			g.drawString("FPS: " + super.getFPS(), 5, ApoRelaxConstants.GAME_HEIGHT - 5);
		}
	}

}
