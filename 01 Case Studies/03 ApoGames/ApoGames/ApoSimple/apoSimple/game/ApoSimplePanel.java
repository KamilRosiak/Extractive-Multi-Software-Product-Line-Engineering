package apoSimple.game;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;

import org.apogames.ApoConstants;
import org.apogames.ApoScreen;
import org.apogames.entity.ApoNewTextfield;
import org.apogames.help.ApoGameCounter;
import org.apogames.help.ApoHelp;
import org.apogames.sound.ApoMP3SoundHandler;
import org.apogames.sound.ApoSounds;

import apoSimple.ApoSimpleComponent;
import apoSimple.ApoSimpleConstants;
import apoSimple.ApoSimpleImages;
import apoSimple.bubble.ApoBubbleGame;
import apoSimple.game.level.ApoSimpleLevel;
import apoSimple.game.level.ApoSimpleProperties;
import apoSimple.game.level.ApoSimpleUserlevels;
import apoSimple.hiddenFun.ApoFun;
import apoSimple.hiddenPush.ApoHiddenPush;
import apoSimple.oneButton.ApoSimpleOneButton;
import apoSimple.puzzle.ApoSimplePuzzleGame;

public class ApoSimplePanel extends ApoSimpleComponent {

	public static final String SOUND_BUTTON = "button";
	public static final String SOUND_START = "start";
	public static final String SOUND_BACKGROUND = "background";
	public static final String SOUND_LOOSE = "loose";
	public static final String SOUND_WIN_LEVELUP = "win_levelup";
	public static final String SOUND_DOG = "dog";
	public static final String SOUND_DUCK = "duck";
	public static final String SOUND_DOG_SHEEP = "sheep5";
	public static final String SOUND_MAEH = "maeh";
	public static final String SOUND_EXTRA = "extra";
	public static final String SOUND_STRONG = "strong";
	public static final String SOUND_ERROR = "error";
	
	public static String level_save;
	
	/** Hilfsvariablen, um herauszufinden wie lange zum Nachdenken und Zeichnen gebraucht wurde */
	private long thinkTime, renderTime;
	
	private ApoSimpleButtons buttons;
	
	private ApoSimpleModel model;
	
	private ApoSimpleMenu menu;
	
	private ApoSimpleTutorial tutorial;
	
	private ApoSimpleGame game;

	private ApoSimpleHighscore highscore;

	private ApoSimplePuzzle puzzle;

	private ApoSimpleCredits credits;
	
	private ApoSimpleEditor editor;
	
	private ApoSimpleLevelChooser levelChooser;
	
	private ApoSimpleLevelGame levelGame;

	private ApoSimpleOptions options;
	
	private ApoSimpleOneButton oneButton;

	private ApoBubbleGame bubble;

	private ApoSimplePuzzleGame hiddenPuzzle;

	private ApoFun hiddenFun;

	private ApoHiddenPush hiddenPush;
	
	public static BufferedImage iStar, iStarLittle, iTutAbove, iTutDown, iTutMiddle, iArrow;
	
	private boolean bLoose;
	
	private ApoSimpleUserlevels userlevels;
	
	private ApoMP3SoundHandler music;
	private ApoSounds sounds;
	private boolean bSoundEffects;
	private boolean bSoundMusic;
	
	private int coins;
	
	private ApoSimpleProperties prop;
	
	public ApoSimplePanel(ApoScreen screen) {
		super(screen);
	}
	
	public void init() {
		super.init();
		super.setShowFPS(false);
		
		if (this.music == null) {
			this.music = new ApoMP3SoundHandler();
			
			this.music.loadSound(ApoSimplePanel.SOUND_BACKGROUND, "/sounds/background.mp3");
			this.bSoundMusic = true;
		}
		
		if (this.sounds == null) {
			this.sounds = new ApoSounds();
			
			this.sounds.loadSound(ApoSimplePanel.SOUND_BUTTON, "/sounds/sheep_button.wav");
			this.sounds.loadSound(ApoSimplePanel.SOUND_START, "/sounds/sheep_1.wav");
			this.sounds.loadSound(ApoSimplePanel.SOUND_DOG, "/sounds/dog.wav");
			this.sounds.loadSound(ApoSimplePanel.SOUND_DOG_SHEEP, "/sounds/sheep_5.wav");
			this.sounds.loadSound(ApoSimplePanel.SOUND_DUCK, "/sounds/duck.wav");
			this.sounds.loadSound(ApoSimplePanel.SOUND_LOOSE, "/sounds/lose.wav");
			this.sounds.loadSound(ApoSimplePanel.SOUND_MAEH, "/sounds/sheep_2.wav");
			this.sounds.loadSound(ApoSimplePanel.SOUND_EXTRA, "/sounds/sheep_3.wav");
			this.sounds.loadSound(ApoSimplePanel.SOUND_STRONG, "/sounds/sheep_4.wav");
			this.sounds.loadSound(ApoSimplePanel.SOUND_WIN_LEVELUP, "/sounds/win_levelup.wav");
			this.sounds.loadSound(ApoSimplePanel.SOUND_ERROR, "/sounds/error.wav");
			
			this.bSoundEffects = true;
		}
		
		if (ApoSimplePanel.iStar == null) {
			ApoSimplePanel.iStar = this.getImages().getImage("images/star.png", false);
		}
		if (ApoSimplePanel.iStarLittle == null) {
			ApoSimplePanel.iStarLittle = this.getImages().getImage("images/star_little.png", false);
		}
		if (ApoSimplePanel.iTutAbove == null) {
			ApoSimplePanel.iTutAbove = this.getImages().getImage("images/tutorial_above.png", false);
		}
		if (ApoSimplePanel.iTutDown == null) {
			ApoSimplePanel.iTutDown = this.getImages().getImage("images/tutorial_down.png", false);
		}
		if (ApoSimplePanel.iTutMiddle == null) {
			ApoSimplePanel.iTutMiddle = this.getImages().getImage("images/tutorial_middle.png", false);
		}
		if (ApoSimplePanel.iArrow == null) {
			ApoSimplePanel.iArrow = this.getImages().getImage("images/arrow.png", false);
		}
		if (this.buttons == null) {
			this.buttons = new ApoSimpleButtons(this);
			this.buttons.init();
		}
		if (this.menu == null) {
			this.menu = new ApoSimpleMenu(this);
		}
		if (this.tutorial == null) {
			this.tutorial = new ApoSimpleTutorial(this);
		}
		if (this.game == null) {
			this.game = new ApoSimpleGame(this);
		}
		if (this.highscore == null) {
			this.highscore = new ApoSimpleHighscore(this);
		}
		if (this.credits == null) {
			this.credits = new ApoSimpleCredits(this);
		}
		if (this.editor == null) {
			this.editor = new ApoSimpleEditor(this);
		}
		if (this.levelChooser == null) {
			this.levelChooser = new ApoSimpleLevelChooser(this);
		}
		if (this.levelGame == null) {
			this.levelGame = new ApoSimpleLevelGame(this);
		}
		if (this.puzzle == null) {
			this.puzzle = new ApoSimplePuzzle(this);
		}
		if (this.options == null) {
			this.options = new ApoSimpleOptions(this);
		}
		if (this.oneButton == null) {
			this.oneButton = new ApoSimpleOneButton(this);
		}
		if (this.bubble == null) {
			this.bubble = new ApoBubbleGame(this);
		}
		if (this.hiddenPuzzle == null) {
			this.hiddenPuzzle = new ApoSimplePuzzleGame(this);
		}
		if (this.hiddenFun == null) {
			this.hiddenFun = new ApoFun(this);
		}
		if (this.hiddenPush == null) {
			this.hiddenPush = new ApoHiddenPush(this);
		}
		this.highscore.loadHighscore(false);
		this.setMenu();
		
		this.userlevels = new ApoSimpleUserlevels();
		this.loadUserlevels();
		
		if (ApoConstants.B_APPLET) {
			this.loadMusic();
			this.loadHelp();
			this.loadCoins();
			this.loadSavedLevel();
			this.loadRegion();
			this.loadClassicMode();
		} else {
			this.prop = new ApoSimpleProperties(this);
			try {
				this.prop.readLevel("properties");
			} catch (Exception ex) {
				this.prop.writeLevel("properties");
			}
		}
		if (this.bSoundMusic) {
			this.music.playSound(ApoSimplePanel.SOUND_BACKGROUND, true, 80);
		}

		ApoGameCounter.getInstance().save("start_game_"+this.getTextName());
	}
	
	public String getTextName() {
		String name = "";
		if ((this.game != null) && (this.game.getTextField() != null)) {
			name = this.game.getTextField().getCurString();
		}
		return name;
	}
	
	public boolean isClassicMode() {
		return this.game.isbClassic();
	}
	
	public void setClassicMode(boolean bClassic) {
		this.game.setbClassic(bClassic);
	}
	
	public boolean isBHelp() {
		return this.game.isbHelp();
	}
	
	public void setBHelp(boolean bHelp) {
		this.game.setbHelp(bHelp);
	}

	public final int getCoins() {
		return this.coins;
	}
	
	public void loadRegion() {
		if (ApoConstants.B_APPLET) {
			String load;
			try {
				load = ApoHelp.loadData(new URL("http://www.apo-games.de/apoSheeptastic/"), "apoSheeptastic_region");
				if ((load != null) && (load.length() > 0)) {
					ApoSimpleConstants.REGION = load;
				} else {
					ApoSimpleConstants.REGION = "en";
				}
			} catch (MalformedURLException e) {
				ApoSimpleConstants.REGION = "en";
			} catch (Exception e) {
				ApoSimpleConstants.REGION = "en";
			}
			ApoSimpleConstants.setLanguage(ApoSimpleConstants.REGION);
		} else {
			ApoSimplePanel.level_save = "";
		}
	}
	
	public void saveRegion() {
		if (ApoConstants.B_APPLET) {
			try {
				ApoHelp.saveData(new URL("http://www.apo-games.de/apoSheeptastic/"), "apoSheeptastic_region", String.valueOf(ApoSimpleConstants.REGION));
			} catch (MalformedURLException e) {
			} catch (Exception e) {
			}
		}
	}
	
	public void loadClassicMode() {
		if (ApoConstants.B_APPLET) {
			String load;
			try {
				load = ApoHelp.loadData(new URL("http://www.apo-games.de/apoSheeptastic/"), "apoSheeptastic_classic");
				if ((load != null) && (load.length() > 0)) {
					this.setClassicMode(Boolean.parseBoolean(load));
				} else {
					this.setClassicMode(false);
				}
			} catch (MalformedURLException e) {
				this.setClassicMode(false);
			} catch (Exception e) {
				this.setClassicMode(false);
			}
		}
	}
	
	public void saveClassicmode() {
		if (ApoConstants.B_APPLET) {
			try {
				ApoHelp.saveData(new URL("http://www.apo-games.de/apoSheeptastic/"), "apoSheeptastic_classic", String.valueOf(this.isClassicMode()));
			} catch (MalformedURLException e) {
			} catch (Exception e) {
			}
		}
	}

	public void setCoins(final int coins, final boolean bSave) {
		this.coins = coins;
		if (this.coins < 0) {
			this.coins = 0;
		}
		if (bSave) {
			if (ApoConstants.B_APPLET) {
				try {
					ApoHelp.saveData(new URL("http://www.apo-games.de/apoSheeptastic/"), "apoSheeptastic_coins", String.valueOf(this.coins));
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void loadSavedLevel() {
		if (ApoConstants.B_APPLET) {
			String load;
			try {
				load = ApoHelp.loadData(new URL("http://www.apo-games.de/apoSheeptastic/"), "apoSheeptastic_level");
				if ((load != null) && (load.length() > 0)) {
					ApoSimplePanel.level_save = load;
				} else {
					ApoSimplePanel.level_save = "";
				}
			} catch (MalformedURLException e) {
				ApoSimplePanel.level_save = "";
			} catch (Exception e) {
				ApoSimplePanel.level_save = "";
			}
		} else {
			ApoSimplePanel.level_save = "";
		}
	}
	
	public void saveSavedLevel() {
		if (ApoConstants.B_APPLET) {
			String s = ApoSimplePanel.level_save;
			if ((ApoSimplePanel.level_save == null) || (ApoSimplePanel.level_save.length() <= 0)) {
				s = "";
			}
			try {
				ApoHelp.saveData(new URL("http://www.apo-games.de/apoSheeptastic/"), "apoSheeptastic_level", String.valueOf(s));
			} catch (MalformedURLException e) {
			} catch (Exception e) {
			}
		}
	}

	private void loadCoins() {
		if (ApoConstants.B_APPLET) {
			String load;
			try {
				load = ApoHelp.loadData(new URL("http://www.apo-games.de/apoSheeptastic/"), "apoSheeptastic_coins");
				if ((load != null) && (load.length() > 0)) {
					this.coins = Integer.valueOf(load);
				}
			} catch (MalformedURLException e) {
				this.coins = 0;
			} catch (Exception e) {
				this.coins = 0;
			}
		} else {
			this.coins = 0;
		}
	}
	
	private void loadHelp() {
		if (ApoConstants.B_APPLET) {
			String load;
			try {
				load = ApoHelp.loadData(new URL("http://www.apo-games.de/apoSheeptastic/"), "help");
				if ((load != null) && (load.length() > 0)) {
					int help = Integer.valueOf(load.substring(0, 1));
					if (help > 0) {
						this.game.setbHelp(true);
					} else {
						this.game.setbHelp(false);
					}
				} else {
					this.game.setbHelp(true);
				}
			} catch (MalformedURLException e) {
				this.game.setbHelp(true);
			} catch (Exception e) {
				this.game.setbHelp(true);
			}
		}
	}
	
	public String loadName(String name) {
		return this.game.loadName(name);
	}
	
	private void loadMusic() {
		if (ApoConstants.B_APPLET) {
			String load;
			try {
				load = ApoHelp.loadData(new URL("http://www.apo-games.de/apoSheeptastic/"), "sound");
				if ((load != null) && (load.length() > 0)) {
					int music = Integer.valueOf(load.substring(0, 1));
					int effects = Integer.valueOf(load.substring(1, 2));
					if (music > 0) {
						this.bSoundMusic = true;
					} else {
						this.bSoundMusic = false;
					}
					if (effects > 0) {
						this.bSoundEffects = true;
					} else {
						this.bSoundEffects = false;
					}
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stopGame() {
		if (this.music != null) {
			this.music.stopAll();
		}
		this.saveClassicmode();
		this.saveRegion();
		this.setCoins(this.getCoins(), true);
		if (!ApoConstants.B_APPLET) {
			this.prop.writeLevel("properties");
		}
	}
	
	public void playSound(String sound, int volumen) {
		this.playSound(sound, false, volumen);
	}
	
	public void playSound(String sound, boolean bLoop, int volumen) {
		if ((this.bSoundMusic) && (sound.equals(ApoSimplePanel.SOUND_BACKGROUND))) {
			this.music.playSound(sound, bLoop, volumen);
		} else if ((this.bSoundEffects) && (!sound.equals(ApoSimplePanel.SOUND_BACKGROUND))) {
			this.sounds.playSound(sound, bLoop);
		}
	}
	
	public void stopMusic(String sound) {
		this.music.stopSound(sound);
	}
	
	public final ApoSimpleUserlevels getUserlevels() {
		return this.userlevels;
	}
	
	public final void loadUserlevels() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				ApoSimplePanel.this.userlevels.loadUserlevels();
			}
 		});
 		t.start();
	}

	public boolean isBLoose() {
		return this.bLoose;
	}

	public void setBLoose(boolean bLoose) {
		this.bLoose = bLoose;
	}

	public void setMenu() {
		this.model = this.menu;
		this.setShouldRepaint(true);
		
		this.setButtonVisible(ApoSimpleConstants.BUTTON_MENU);
		
		this.menu.init();
	}
	
	public void setGame() {
		this.model = this.game;
		this.setShouldRepaint(true);
		
		this.setButtonVisible(ApoSimpleConstants.BUTTON_GAME);
		
		this.game.init();
	}
	
	public void setTutorial() {
		this.model = this.tutorial;
		this.setShouldRepaint(true);
		
		this.setButtonVisible(ApoSimpleConstants.BUTTON_TUTORIAL);
		
		this.tutorial.init();
	}
	
	public void setHighscore() {
		this.model = this.highscore;
		this.setShouldRepaint(false);
		
		this.setButtonVisible(ApoSimpleConstants.BUTTON_HIGHSCORE);
		
		this.highscore.init();
	}
	
	public void setCredits() {
		this.model = this.credits;
		this.setShouldRepaint(false);
		
		this.setButtonVisible(ApoSimpleConstants.BUTTON_CREDITS);
		
		this.credits.init();
		
		this.render();
	}
	
	public void setEditor() {
		this.setEditor(null);
	}
	
	public void setEditor(String level) {
		this.model = this.editor;
		this.setShouldRepaint(false);
		
		this.setButtonVisible(ApoSimpleConstants.BUTTON_EDITOR);
		
		this.editor.init();
		
		if (level != null) {
			this.editor.setLevel(level);
		}
	}
	
	public void setLevelChooser() {
		this.model = this.levelChooser;
		this.setShouldRepaint(false);
		
		this.setButtonVisible(ApoSimpleConstants.BUTTON_LEVELCHOOSER);
		
		this.levelChooser.init();
	}
	
	public void setPuzzle() {
		this.model = this.puzzle;
		this.setShouldRepaint(false);
		
		this.setButtonVisible(ApoSimpleConstants.BUTTON_PUZZLEMODE);
		
		this.puzzle.init();
	}
	
	public void setOptions() {
		this.model = this.options;
		this.setShouldRepaint(true);
		
		this.setButtonVisible(ApoSimpleConstants.BUTTON_OPTIONS);
		
		this.options.init();
	}
	
	public void setOneButton() {
		this.model = this.oneButton;
		this.setShouldRepaint(true);
		
		this.setButtonVisible(ApoSimpleConstants.BUTTON_ONEBUTTON);
		
		this.oneButton.init();
	}
	
	public void setBubbleGame() {
		this.model = this.bubble;
		this.setShouldRepaint(true);
		
		this.setButtonVisible(ApoSimpleConstants.BUTTON_BUBBLE);
		
		this.bubble.init();
	}
	
	public void setHiddenPuzzleGame() {
		this.model = this.hiddenPuzzle;
		this.setShouldRepaint(true);
		
		this.setButtonVisible(ApoSimpleConstants.BUTTON_PUZZLE);
		
		this.hiddenPuzzle.init();
	}

	public void setHiddenFun() {
		this.model = this.hiddenFun;
		this.setShouldRepaint(true);
		
		this.setButtonVisible(ApoSimpleConstants.BUTTON_PUZZLE);
		
		this.hiddenFun.init();
	}

	public void setHiddenPush() {
		this.model = this.hiddenPush;
		this.setShouldRepaint(true);
		
		this.setButtonVisible(ApoSimpleConstants.BUTTON_PUZZLE);
		
		this.hiddenPush.init();
	}
	
	public boolean isbSoundEffects() {
		return this.bSoundEffects;
	}

	public void setbSoundEffects(boolean bSoundEffects) {
		this.bSoundEffects = bSoundEffects;
		this.saveSound();
	}

	public boolean isbSoundMusic() {
		return this.bSoundMusic;
	}

	public void setbSoundMusic(boolean bSoundMusic) {
		this.bSoundMusic = bSoundMusic;
		if (this.bSoundMusic) {
			this.music.playSound(ApoSimplePanel.SOUND_BACKGROUND, true, 80);
		} else {
			if (this.music != null) {
				this.music.stopAll();
			}
		}
		this.saveSound();
	}
	
	private void saveSound() {
		String sound = "";
		if (this.bSoundMusic) {
			sound += "1";
		} else {
			sound += "0";
		}
		if (this.bSoundEffects) {
			sound += "1";
		} else {
			sound += "0";
		}
		if (ApoConstants.B_APPLET) {
			try {
				ApoHelp.saveData(new URL("http://www.apo-games.de/apoSheeptastic/"), "sound", String.valueOf(sound));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public ApoNewTextfield getTextField() {
		return this.game.getTextField();
	}
	
	public boolean keyButtonReleasedTextField(int button, char character) {
		return this.game.keyButtonReleasedTextField(button, character);
	}
	
	public void changeChecked(boolean bChecked) {
		this.editor.changeChecked(bChecked);
	}
	
	public void setLevel(final ApoSimpleLevel level, boolean bEditor, int levelInt) {
		this.model = this.levelGame;
		this.setShouldRepaint(true);
		
		this.setButtonVisible(ApoSimpleConstants.BUTTON_GAME);
		
		this.levelGame.init();
		this.levelGame.makeLevel(level, bEditor, levelInt);
	}
	
	public Point getPosition(int points) {
		return this.highscore.getPosition(points);
	}
	
	public int getMaxLevel() {
		return this.levelChooser.getMaxLevel();
	}
	
	public boolean isLevelSolved(int level) {
		int value = this.levelChooser.getStarsForLevel(level);
		if (value == -1) {
			if (level == this.levelChooser.getMaxLevel() + 1) {
				return true;
			}
			return false;
		}
		return true;
	}
	
	public void setLevelSolved(int level, int stars) {
		int addCoins = (this.levelChooser.addLevel(level, stars));
		this.coins += addCoins;
		
		if (ApoConstants.B_APPLET) {
			try {
				String solved = "";
				for (int i = 0; i < this.levelChooser.getLevelSolutions().size(); i++) {
					String levelS = String.valueOf(this.levelChooser.getLevelSolutions().get(i).getLevel());
					while (levelS.length() < 3) {
						levelS = "0" + levelS;
					}
					levelS += String.valueOf(this.levelChooser.getLevelSolutions().get(i).getStars());
					solved += levelS;
				}
				ApoHelp.saveData(new URL("http://www.apo-games.de/apoSheeptastic/"), "solved", String.valueOf(solved));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setButtonVisible(boolean[] bVisibile) {
		for (int i = 0; i < this.getButtons().length && i < bVisibile.length; i++) {
			this.getButtons()[i].setBVisible(bVisibile[i]);
		}
		if (ApoConstants.B_APPLET) {
			this.getButtons()[0].setBVisible(false);
		}
		this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
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
		}
		if (keyCode == KeyEvent.VK_F12) {
			ApoSimpleConstants.CHEAT = !ApoSimpleConstants.CHEAT;
			if (this.game != null) {
				this.game.usedCheat();
			}
		}
		if (this.model != null) {
			this.model.keyButtonReleased(keyCode, keyChar);
		}
	}
	
	public void keyPressed(int keyCode, char keyCharacter) {
		if (this.model != null) {
			this.model.keyPressed(keyCode, keyCharacter);
		}
	}
	
	@Override
	public void setButtonFunction(String function) {
		if (this.model != null) {
			this.model.mouseButtonFunction(function);
		}
	}

	@Override
	public boolean mouseReleased(int x, int y, boolean left) {
		if (!super.mouseReleased(x, y, left)) {
			if (this.model != null) {
				this.model.mouseButtonReleased(x, y, !left);
			}
		}
		return false;
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
			this.model.mouseDragged(x, y);
		}
	}
	
	@Override
	public void mouseWheelChanged(int changed) {
		if (this.model != null) {
			this.model.mouseWheelChanged(changed);
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
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			this.model.render(g);
		}
		super.renderButtons(g);
		if (this.model != null) {
			this.model.drawOverlay(g);
		}
		this.renderFPS(g);
		this.renderTime = (int)(System.nanoTime() - t);
	}
	
	public void renderBackground(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, ApoSimpleConstants.GAME_WIDTH, ApoSimpleConstants.GAME_HEIGHT);
	}
	
	/**
	 * rendert die Anzeige für die FPS und die Thinkzeit
	 * @param g : das Graphicsobjekt
	 */
	private void renderFPS(Graphics2D g) {
		if (super.isShowFPS()) {
			g.setColor(Color.red);
			g.setFont(ApoSimpleConstants.FONT_FPS);
			g.drawString("think time: " + this.thinkTime + " ns", 5, ApoSimpleConstants.GAME_HEIGHT - 45);
			g.drawString("think time: " + (this.thinkTime / 1000000) + " ms",5, ApoSimpleConstants.GAME_HEIGHT - 35);

			g.drawString("draw time: " + this.renderTime + " ns", 5, ApoSimpleConstants.GAME_HEIGHT - 25);
			g.drawString("draw time: " + (this.renderTime / 1000000) + " ms", 5, ApoSimpleConstants.GAME_HEIGHT - 15);
			g.drawString("FPS: " + super.getFPS(), 5, ApoSimpleConstants.GAME_HEIGHT - 5);
		}
	}

	public void renderOver(Graphics2D g, String[] s, int button) {
		if (this.getButtons()[button].isBOver()) {
			g.setFont(ApoSimpleGame.FONT_BUTTON);
			int w = g.getFontMetrics().stringWidth(s[0]);
			int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
			int x = (int)(this.getButtons()[button].getX() + this.getButtons()[button].getWidth()/2 - w/2);
			int change = 10;
			if (x < 0) {
				x = 0;
			} else if (x + w + change >= ApoSimpleConstants.GAME_WIDTH) {
				x = ApoSimpleConstants.GAME_WIDTH - 2 * change - w;
			}
			int y = (int)(this.getButtons()[button].getY() + this.getButtons()[button].getHeight() + 2 + h + change);
			if (y > ApoSimpleConstants.GAME_HEIGHT - change) {
				y = ApoSimpleConstants.GAME_HEIGHT - change;
			}
			g.setColor(Color.WHITE);
			g.fillRoundRect((int)(x - change), (int)(y - h - change), (int)(w + 2 * change), (int)(h * s.length + 2 * change), 15, 15);
			g.setColor(Color.BLACK);
			g.drawRoundRect((int)(x - change), (int)(y - h - change), (int)(w + 2 * change), (int)(h * s.length + 2 * change), 15, 15);
			
			g.setColor(Color.BLACK);
			for (int i = 0; i < s.length; i++) {
				g.drawString(s[i], x, y + h * i);
			}
		}
	}
	
	public void renderCoins(Graphics2D g, int changeX, int changeY) {
		int y = 5;
		int x = 550;
		g.drawImage(ApoSimpleImages.COINS, x, y, null);
		
		g.setColor(Color.BLACK);
		g.setFont(ApoSimpleGame.FONT_COIN);
		String s = String.valueOf(this.getCoins());
		x = x + ApoSimpleImages.COINS.getWidth() + 5;
		int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
		ApoSimpleGame.drawString(g, s, x, y + ApoSimpleImages.COINS.getHeight()/2 + h/2);
	}

}
