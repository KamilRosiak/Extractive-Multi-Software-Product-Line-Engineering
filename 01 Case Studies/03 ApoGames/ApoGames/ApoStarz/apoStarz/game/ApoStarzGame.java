package apoStarz.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apogames.ApoComponent;
import org.apogames.ApoConstants;
import org.apogames.entity.ApoButton;
import org.apogames.help.ApoFileFilter;

import apoStarz.ApoStarzApplet;
import apoStarz.ApoStarzConstants;
import apoStarz.ApoStarzImages;
import apoStarz.ApoStarzMain;
import apoStarz.level.ApoStarzLevel;

public class ApoStarzGame extends ApoComponent {

	private static final long serialVersionUID = 1L;

	private final String VERSION = "Beta 0.94";
	
	public static final String APPLET_LEVEL = "http://apo-games.de/apoStarz/levels/";
	
	private final float ROTATE_DIF = 1f;
	
	protected ApoStarzImages images;
	
	private BufferedImage iBackground, iRotate, iAnalysis, iMenu;
	
	private ApoStarzIO io;
	
	private int curRotationSpeed;
	
	private int curLevel;
	
	private int curLevelMode;
	
	private int curHighscore;
	
	private int curTimeLevel;
	
	private int curTime;
	
	private String curSolution;
	
	private String levelSolution;
	
	protected ApoStarzLevel level;
	
	private boolean bRotate;
	
	private float curAngle, difAngle;
	
	private ApoStarzMain main;
	
	private ApoStarzApplet applet;
	
	private int steps;

	private JTextArea textArea;
	
	private JScrollPane scrollPane;
	
	/** A FileChooser */
	private JFileChooser fileChooser;
	/** A Class file filter */
	private final ApoFileFilter	fileFilter = new ApoFileFilter("ase");
	
	private boolean bMenu;
	
	private boolean bNormal;
	
	private boolean bTime;
	
	private boolean bTimeEnd;
	
	private boolean bThink;
	
	private boolean bHighscore;
	
	private boolean bTutorial;
	
	private String curLevelStringHighscore;
	
	private String curMouseFunction;
	
	private ApoStarzTutorial tutorial;
	
	private int curKey;
	
	private int curTheme;
	
	public ApoStarzGame(ApoStarzMain main) {
		this(main, true, true, ApoStarzConstants.WAIT_TIME);
	}
	
	public ApoStarzGame(ApoStarzMain main, boolean mouse, boolean key, int wait_time) {
		super(mouse, key, wait_time);
		
		this.main = main;
	}

	public ApoStarzGame(ApoStarzApplet applet) {
		this(applet, true, true, ApoStarzConstants.WAIT_TIME);
	}
	
	public ApoStarzGame(ApoStarzApplet applet, boolean mouse, boolean key, int wait_time) {
		super(mouse, key, wait_time);
		
		this.applet = applet;
	}
	
	public void setEditor() {
		if (this.main != null) {
			this.main.setEditor(this.level.getLevelString());
		} else if (this.applet != null) {
			this.applet.setEditor(this.level.getLevelString());
		}
	}
	
	@Override
	public void init() {
		if (!ApoConstants.B_APPLET) {
			this.fileChooser = new JFileChooser();
			this.fileChooser.setCurrentDirectory(new File( System.getProperty("user.dir") + File.separator+ "levels" ) );
			this.fileChooser.setFileFilter(this.fileFilter);
		}
		this.curMouseFunction = null;
		this.curKey = ApoStarzConstants.EMPTY;
		if (ApoStarzConstants.highscore == null) {
			ApoStarzConstants.highscore = new ApoStarzHighscore();
			if (ApoConstants.B_APPLET) {
				ApoStarzConstants.highscore.readLevel(ApoStarzGame.APPLET_LEVEL + "highscore.hig");
			} else {
				ApoStarzConstants.highscore.readLevel(System.getProperty("user.dir") + File.separator+ "levels" + File.separator + "highscore.hig");
			}
		}
		if (this.images == null) {
			this.images = new ApoStarzImages();
		}
		if (this.tutorial == null) {
			this.tutorial = new ApoStarzTutorial(this);
		}
		if (this.iBackground == null) {
			this.iBackground = this.images.getBackgroundGame(ApoStarzConstants.GAME_WIDTH, ApoStarzConstants.GAME_HEIGHT, "images/background.png", "images/menu.png");
		}
		if (this.iAnalysis == null) {
			this.iAnalysis = this.images.getImageFromPath("images/analysis.png", false);
		}
		if (this.iMenu == null) {
			this.iMenu = this.images.getImageFromPath("images/choose.png", false);
		}
		if (this.level == null) {
			this.level = new ApoStarzLevel(this.images);
		}
		if (this.io == null) {
			this.io = new ApoStarzIO();
			this.curLevel = -1;
			this.curRotationSpeed = 1;
		}
		if (this.textArea == null) {
			this.textArea = new JTextArea("");
			this.textArea.setEditable(true);
			this.textArea.setLineWrap(true);
		}
		if (this.scrollPane == null) {
			this.scrollPane = new JScrollPane(this.textArea);
			this.scrollPane.setBounds(ApoStarzConstants.GAME_GAME_WIDTH + 5, 300, ApoStarzConstants.GAME_MENU_WIDTH - 10,75);
			this.add(this.scrollPane);
		}
		if (super.getButtons() == null) {
			super.setButtons(new ApoButton[26]);
			
			String text = "menu";
			String function = "menu";
			int width = 70;
			int height = 20;
			int x = this.getWidth() - 5 - width;
			int y = this.getHeight() - 1 * height - 1 * 5;
			super.getButtons()[0] = new ApoButton( this.images.getButtonImage( width * 3, height, text, Color.yellow, Color.black, Color.black, new Color(0, 128, 255, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = "retry";
			function = "restart";
			width = 70;
			height = 20;
			x = ApoStarzConstants.GAME_GAME_WIDTH + 5;
			y = this.getHeight() - 1 * height - 1 * 5;
			super.getButtons()[1] = new ApoButton( this.images.getButtonImage( width * 3, height, text, Color.yellow, Color.black, Color.black, new Color(0, 128, 255, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );
			
			text = "editor";
			function = "editor";
			width = 70;
			height = 20;
			x = this.getWidth() - ApoStarzConstants.GAME_MENU_WIDTH/2 - width/2;
			y = this.getHeight() - 2 * height - 2 * 5;
			super.getButtons()[2] = new ApoButton( this.images.getButtonImage( width * 3, height, text, Color.yellow, Color.black, Color.black, new Color(0, 128, 255, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );		

			text = "leftRotation";
			function = "leftRotation";
			width = 30;
			height = 30;
			x = 5;
			y = 5;
			super.getButtons()[3] = new ApoButton( this.images.getButtonRotate( width * 3, height, true, Color.green, Color.black ), x, y, width, height, function );		

			text = "rightRotation";
			function = "rightRotation";
			width = 30;
			height = 30;
			x = ApoStarzConstants.GAME_GAME_WIDTH - width - 5;
			y = 5;
			super.getButtons()[4] = new ApoButton( this.images.getButtonRotate( width * 3, height, false, Color.green, Color.black ), x, y, width, height, function );
			
			text = "<";
			function = "previousLevelMode";
			width = 20;
			height = 20;
			x = ApoStarzConstants.GAME_GAME_WIDTH + 5;
			y = 300;
			super.getButtons()[5] = new ApoButton( this.images.getButtonImage( width * 3, height, text, Color.yellow, Color.black, Color.black, new Color(0, 128, 255, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );		

			text = ">";
			function = "nextLevelMode";
			width = 20;
			height = 20;
			x = ApoStarzConstants.GAME_WIDTH - 5 - width;
			y = 300;
			super.getButtons()[6] = new ApoButton( this.images.getButtonImage( width * 3, height, text, Color.yellow, Color.black, Color.black, new Color(0, 128, 255, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );
			
			text = "<";
			function = "previousLevel";
			width = 20;
			height = 20;
			x = ApoStarzConstants.GAME_GAME_WIDTH + 5;
			y = 325;
			super.getButtons()[7] = new ApoButton( this.images.getButtonImage( width * 3, height, text, Color.yellow, Color.black, Color.black, new Color(0, 128, 255, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );		

			text = ">";
			function = "nextLevel";
			width = 20;
			height = 20;
			x = ApoStarzConstants.GAME_WIDTH - 5 - width;
			y = 325;
			super.getButtons()[8] = new ApoButton( this.images.getButtonImage( width * 3, height, text, Color.yellow, Color.black, Color.black, new Color(0, 128, 255, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );
			
			text = "load";
			function = "loadLevel";
			width = 50;
			height = 20;
			x = ApoStarzConstants.GAME_GAME_WIDTH - width/2 + ApoStarzConstants.GAME_MENU_WIDTH/2;
			y = 360;
			super.getButtons()[9] = new ApoButton( this.images.getButtonImage( width * 3, height, text, Color.yellow, Color.black, Color.black, new Color(0, 128, 255, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );
			
			text = "load";
			function = "loadLevelSingle";
			width = 50;
			height = 20;
			x = ApoStarzConstants.GAME_GAME_WIDTH - width/2 + ApoStarzConstants.GAME_MENU_WIDTH/2;
			y = 380;
			super.getButtons()[10] = new ApoButton( this.images.getButtonImage( width * 3, height, text, Color.yellow, Color.black, Color.black, new Color(0, 128, 255, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );
			
			text = "level";
			function = "levelKartei";
			width = 77;
			height = 20;
			x = ApoStarzConstants.GAME_GAME_WIDTH + 3;
			y = 160;
			super.getButtons()[11] = new ApoButton( this.images.getButtonKartei( width * 3, height, text, Color.yellow, Color.black, Color.black, new Color(0, 128, 255, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );
			super.getButtons()[11].setBVisible(false);
			
			text = "load";
			function = "loadKartei";
			width = 77;
			height = 20;
			x = ApoStarzConstants.GAME_GAME_WIDTH + width + 3;
			y = 160;
			super.getButtons()[12] = new ApoButton( this.images.getButtonKartei( width * 3, height, text, Color.yellow, Color.black, Color.black, new Color(0, 128, 255, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );
			
			Color c = new Color(255, 255, 255, 0);
			Font write = new Font("Dialog", Font.BOLD, 25);
			
			text = "tutorial";
			function = "tutorialMode";
			width = 120;
			height = 30;
			x = 105;
			y = 85;
			super.getButtons()[13] = new ApoButton( this.images.getButtonImage( width * 3, height, text, c, Color.black, c, new Color(0, 128, 255, 100), new Color(255, 0, 0, 100), write), x, y, width, height, function );

			text = "normal";
			function = "normalMode";
			width = 120;
			height = 30;
			x = 285;
			y = 180;
			super.getButtons()[14] = new ApoButton( this.images.getButtonImage( width * 3, height, text, c, Color.black, c, new Color(0, 128, 255, 100), new Color(255, 0, 0, 100), write), x, y, width, height, function );

			text = "time trial";
			function = "timeMode";
			width = 120;
			height = 30;
			x = 95;
			y = 280;
			super.getButtons()[15] = new ApoButton( this.images.getButtonImage( width * 3, height, text, c, Color.black, c, new Color(0, 128, 255, 100), new Color(255, 0, 0, 100), write), x, y, width, height, function );

			text = "highscore";
			function = "highscoreMode";
			width = 120;
			height = 30;
			x = 215;
			y = 390;
			super.getButtons()[16] = new ApoButton( this.images.getButtonImage( width * 3, height, text, c, Color.black, c, new Color(0, 128, 255, 100), new Color(255, 0, 0, 100), write), x, y, width, height, function );

			text = "quit";
			function = "quit";
			width = 70;
			height = 20;
			x = this.getWidth() - 5 - width;
			y = this.getHeight() - 1 * height - 1 * 5;
			super.getButtons()[17] = new ApoButton( this.images.getButtonImage( width * 3, height, text, Color.yellow, Color.black, Color.black, new Color(0, 128, 255, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = "next Step";
			function = "nextStep";
			width = 150;
			height = 20;
			x = ApoStarzConstants.GAME_GAME_WIDTH + 5;
			y = 275;
			super.getButtons()[18] = new ApoButton( this.images.getButtonImage( width * 3, height, text, Color.yellow, Color.black, Color.black, new Color(0, 128, 255, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = "<";
			function = "previousLevelHighscore";
			width = 20;
			height = 20;
			x = ApoStarzConstants.GAME_GAME_WIDTH + 5;
			y = 250;
			super.getButtons()[19] = new ApoButton( this.images.getButtonImage( width * 3, height, text, Color.yellow, Color.black, Color.black, new Color(0, 128, 255, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );		

			text = ">";
			function = "nextLevelHighscore";
			width = 20;
			height = 20;
			x = ApoStarzConstants.GAME_WIDTH - 5 - width;
			y = 250;
			super.getButtons()[20] = new ApoButton( this.images.getButtonImage( width * 3, height, text, Color.yellow, Color.black, Color.black, new Color(0, 128, 255, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = "next Step";
			function = "nextStepTutorial";
			width = 150;
			height = 20;
			x = ApoStarzConstants.GAME_GAME_WIDTH + 5;
			y = 355;
			super.getButtons()[21] = new ApoButton( this.images.getButtonImage( width * 3, height, text, Color.yellow, Color.black, Color.black, new Color(0, 128, 255, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );
		
			text = "<";
			function = "previousTheme";
			width = 20;
			height = 20;
			x = ApoStarzConstants.GAME_GAME_WIDTH + 5;
			y = 425;
			super.getButtons()[22] = new ApoButton( this.images.getButtonImage( width * 3, height, text, Color.yellow, Color.black, Color.black, new Color(0, 128, 255, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );		

			text = ">";
			function = "nextTheme";
			width = 20;
			height = 20;
			x = ApoStarzConstants.GAME_WIDTH - 5 - width;
			y = 425;
			super.getButtons()[23] = new ApoButton( this.images.getButtonImage( width * 3, height, text, Color.yellow, Color.black, Color.black, new Color(0, 128, 255, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = "<";
			function = "previousRotationSpeed";
			width = 20;
			height = 20;
			x = ApoStarzConstants.GAME_GAME_WIDTH + 5;
			y = 375;
			super.getButtons()[24] = new ApoButton( this.images.getButtonImage( width * 3, height, text, Color.yellow, Color.black, Color.black, new Color(0, 128, 255, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );		

			text = ">";
			function = "nextRotationSpeed";
			width = 20;
			height = 20;
			x = ApoStarzConstants.GAME_WIDTH - 5 - width;
			y = 375;
			super.getButtons()[25] = new ApoButton( this.images.getButtonImage( width * 3, height, text, Color.yellow, Color.black, Color.black, new Color(0, 128, 255, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );
		}
		this.steps = 0;
		this.curLevelMode = 0;
		this.curTheme = (int)(Math.random() * (ApoStarzConstants.THEME_BLACK + 1));
		this.setMenuMode();
		this.setThemeMode(0);
	}
	
	private void setThemeMode(int plus) {
		this.curTheme += plus;
		if (this.curTheme > ApoStarzConstants.THEME_BLACK) {
			this.curTheme = ApoStarzConstants.THEME_BLUE;
		} else if (this.curTheme > ApoStarzConstants.THEME_BLUE) {
			this.curTheme = ApoStarzConstants.THEME_BLACK;
		}
		if (this.curTheme == ApoStarzConstants.THEME_BLUE) {
			this.iBackground = this.images.getBackgroundGame(ApoStarzConstants.GAME_WIDTH, ApoStarzConstants.GAME_HEIGHT, "images/background.png", "images/menu.png");
			this.level.setIWall(this.images.getImageFromPath("images/block.png", false));
		} else if (this.curTheme == ApoStarzConstants.THEME_BLACK) {
			this.iBackground = this.images.getBackgroundGame(ApoStarzConstants.GAME_WIDTH, ApoStarzConstants.GAME_HEIGHT, "images/background_2.png", "images/menu.png");
			this.level.setIWall(this.images.getImageFromPath("images/block_2.png", false));
		}
		this.setLevelString(ApoStarzConstants.highscore.getRandomLevel());
	}
	
	public void setNormalMode() {
		this.bMenu = false;
		this.bHighscore = false;
		this.bNormal = true;
		this.bTime = false;
		this.bTutorial = false;
		this.setButtonVisible(ApoStarzConstants.LEVEL_KARTEI);
		
		if (ApoConstants.B_APPLET) {
			this.levelLoad(ApoStarzGame.APPLET_LEVEL + ApoStarzConstants.LEVELS[this.curLevelMode]);
		} else {
			this.levelLoad(System.getProperty("user.dir") + File.separator+ "levels" + File.separator + ApoStarzConstants.LEVELS[this.curLevelMode]);
		}
	}
	
	protected void setMenuMode() {
		this.bMenu = true;
		this.bHighscore = false;
		this.bNormal = false;
		this.bTime = false;
		this.bTimeEnd = false;
		this.curTime = ApoStarzConstants.MAX_TIME;
		this.bTutorial = false;
		this.setButtonVisible(ApoStarzConstants.MENU_MODE);
		this.setLevelString(ApoStarzConstants.highscore.getRandomLevel());
	}

	protected void setTimeMode() {
		this.bMenu = false;
		this.bHighscore = false;
		this.bNormal = false;
		this.bTime = true;
		this.bTimeEnd = false;
		this.bTutorial = false;
		this.setButtonVisible(ApoStarzConstants.TIME_MODE);
		ApoStarzConstants.highscore.makeLevelsForTimeMode(ApoStarzConstants.MAX_TIME_LEVELS);
		this.curTimeLevel = 0;
		this.setLevelString(ApoStarzConstants.highscore.getLevelForTime(this.curTimeLevel));
		this.curTime = ApoStarzConstants.MAX_TIME;
	}

	protected void setHighscoreMode() {
		this.bMenu = false;
		this.bHighscore = true;
		this.bNormal = false;
		this.bTime = false;
		this.bTimeEnd = false;
		this.bTutorial = false;
		this.setButtonVisible(ApoStarzConstants.HIGHSCORE_MODE);
		ApoStarzConstants.highscore.makeLevelsForHighscoreMode();
		this.curTimeLevel = 0;
		this.setLevelString(ApoStarzConstants.highscore.getLevelForTime(this.curTimeLevel));
	}

	protected void setTutorialMode() {
		this.bMenu = false;
		this.bHighscore = false;
		this.bNormal = false;
		this.bTime = false;
		this.bTimeEnd = false;
		this.bTutorial = true;
		this.setButtonVisible(ApoStarzConstants.TUTORIAL_MODE);
		this.tutorial.init();
	}
	
	private void setButtonVisible(boolean[] bVisible) {
		for (int i = 0; i < super.getButtons().length; i++) {
			super.getButtons()[i].setBVisible(bVisible[i]);
		}
		this.textArea.setVisible(bVisible[bVisible.length - 1]);
		this.scrollPane.setVisible(bVisible[bVisible.length - 1]);
		if (ApoConstants.B_APPLET) {
			super.getButtons()[9].setBVisible(false);
			super.getButtons()[17].setBVisible(false);
		}
	}

	public void setLevelString(String levelString) {
		this.bRotate = false;
		if (this.level.loadLevel(levelString)) {
			this.textArea.setText(levelString);
			this.steps = 0;
			int curX = (ApoStarzConstants.GAME_GAME_WIDTH - ApoStarzConstants.TILE_SIZE * this.level.getWidth()) / 2;
			int curY = (ApoStarzConstants.GAME_HEIGHT - ApoStarzConstants.TILE_SIZE * this.level.getHeight()) / 2;
			int x = curX - (int)super.getButtons()[3].getWidth()/2 - 5;
			int y = curY - (int)super.getButtons()[3].getHeight()/2;
			if (x < 5) {
				x = 5;
			}
			if (y < 5) {
				y = 5;
			}
			super.getButtons()[3].setX(x);
			super.getButtons()[3].setY(y);
			x = curX + this.level.getWidth() * ApoStarzConstants.TILE_SIZE - (int)super.getButtons()[3].getWidth()/2 + 5;
			if (x > ApoStarzConstants.GAME_GAME_WIDTH - 5 - super.getButtons()[3].getWidth()) {
				x = ApoStarzConstants.GAME_GAME_WIDTH - 5 - (int)super.getButtons()[3].getWidth();
			}
			super.getButtons()[4].setX(x);
			super.getButtons()[4].setY(y);
		} else {
			this.steps = 0;
		}
		this.curHighscore = ApoStarzConstants.highscore.getHighscore(this.level.getLevelString());
		this.levelSolution = ApoStarzConstants.highscore.getSolution(this.level.getLevelString());
		this.curLevelStringHighscore = this.levelSolution;
		this.curSolution = "";
	}

	protected boolean levelLoad(String level) {
		int p = 0;
		if (level == null) {
			p = this.fileChooser.showOpenDialog(this);
		}
		if(p == 0) {
			String s = "";
			if (level != null) {
				s = level;
			} else {
				s = this.fileChooser.getSelectedFile().getPath();
			}
			if (this.io.readLevel(s)) {
				this.curLevel = 0;
				this.setLevelString(this.io.getLevel(this.curLevel));
				return true;
			}
		}
		return false;
	}
	
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		this.curKey = keyCode;
	}

	public void keyReleased(KeyEvent e) {
		super.keyReleased(e);
		this.curKey = ApoStarzConstants.EMPTY;
	}
	
	private void thinkKeyLogic() {
		if (this.curKey == ApoStarzConstants.EMPTY) {
			return;
		}
		int keyCode = this.curKey;
		if (this.bTutorial) {
			this.tutorial.keyPressed(keyCode);
		} else if ((this.bTime) && (this.bTimeEnd)) {
			if (keyCode == KeyEvent.VK_ENTER) {
				this.setTimeMode();
			}
		} else if ((!this.level.isBFalling()) && (!this.level.isBWin()) && (!this.level.isBLost()) && (!this.bHighscore)) {
			if (keyCode == KeyEvent.VK_LEFT) {
				this.rotateLevel(-1);
				this.curSolution += "l";
			}
			if (keyCode == KeyEvent.VK_RIGHT) {
				this.rotateLevel(1);
				this.curSolution += "r";
			}
		} else if ((this.level.isBWin()) || (this.level.isBLost())) {
			if (keyCode == KeyEvent.VK_ENTER) {
				if (this.bHighscore) {
					this.nextLevelHighscore(+1);
				} else if (this.bTime) {
					this.setTimeMode();
				} else if (this.level.isBWin()) {
					if (this.steps > 0) {
						ApoStarzConstants.highscore.addHighscore(this.level.getLevelString(), this.steps, this.curSolution, false);
						if (!ApoConstants.B_APPLET) {
							ApoStarzConstants.highscore.writeLevel(System.getProperty("user.dir") + File.separator+ "levels" + File.separator + "highscore.hig");
						} else {
							ApoStarzConstants.highscore.writeLevel(ApoStarzGame.APPLET_LEVEL + "highscore.hig");			
						}
						this.nextLevel();
					} else {
						this.level.setBWin(false);
					}
				} else if (this.level.isBLost()) {
					this.restart();
				}
			}
		}
	}
	
	protected void rotateLevel(int dif) {
		this.makeRotate(dif * (this.ROTATE_DIF * (this.curRotationSpeed + 1) + 1));
		this.level.setCurDirection(this.level.getCurDirection() - dif);
		this.steps++;
	}
	
	private void makeRotate(float difAngle) {
		this.bRotate = true;
		super.getButtons()[3].setBVisible(false);
		super.getButtons()[4].setBVisible(false);
		this.difAngle = difAngle;
		this.curAngle = 0;
		this.iRotate = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage( ApoStarzConstants.GAME_GAME_WIDTH, ApoStarzConstants.GAME_HEIGHT, Transparency.TRANSLUCENT );
		Graphics2D g = (Graphics2D)this.iRotate.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.level.render(g);
		g.dispose();
	}
	
	public void mouseReleased(MouseEvent e) {
		this.requestFocus();
		super.mouseReleased(e);
	}
	
	private void nextStep() {
		if (this.curLevelStringHighscore.length() > 0) {
			String nextString = this.curLevelStringHighscore.substring(0, 1);
			this.curLevelStringHighscore = this.curLevelStringHighscore.substring(1);
			if (nextString.equals("r")) {
				this.rotateLevel(1);
			} else if (nextString.equals("l")) {
				this.rotateLevel(-1);
			}
		}
	}
	
	private void nextLevelHighscore(int count) {
		this.curTimeLevel += count;
		if (this.curTimeLevel >= ApoStarzConstants.highscore.getTimeLevelSize()) {
			this.curTimeLevel = 0;
		} else if (this.curTimeLevel < 0) {
			this.curTimeLevel = ApoStarzConstants.highscore.getTimeLevelSize() - 1;
		}
		this.setLevelString(ApoStarzConstants.highscore.getLevelForTime(this.curTimeLevel));
	}
	
	@Override
	public void setButtonFunction(String function) {
		this.curMouseFunction = function;
	}
	
	private void thinkMouseFunction() {
		if ((this.curMouseFunction == null) || (this.curMouseFunction.length() <= 0)) {
			return;
		}
		String function = this.curMouseFunction;
		if (this.bTutorial) {
			this.tutorial.setButtonFunction(function);
		} else if (function.equals("menu")) {
			this.setMenuMode();
		} else if (function.equals("quit")) {
			System.exit(0);
		} else if (function.equals("restart")) {
			if ((!this.bRotate) && (!this.level.isBFalling())) {
				this.restart();
			} else if ((this.bTime) && (this.bTimeEnd)) {
				this.setTimeMode();
			}
		} else if (function.equals("editor")) {
			if ((!this.bRotate) && (!this.level.isBFalling())) {
				this.setEditor();
			}
		} else if (function.equals("leftRotation")) {
			if ((!this.level.isBFalling()) && (!this.level.isBWin()) && (!this.level.isBLost())) {
				this.rotateLevel(-1);
				this.curSolution += "l";
			}
		} else if (function.equals("rightRotation")) {
			if ((!this.level.isBFalling()) && (!this.level.isBWin()) && (!this.level.isBLost())) {
				this.rotateLevel(1);
				this.curSolution += "r";
			}
		} else if (function.equals("nextLevel")) {
			if ((!this.bRotate) && (!this.level.isBFalling())) {
				this.nextLevel();
			}
		} else if (function.equals("previousLevel")) {
			if ((!this.bRotate) && (!this.level.isBFalling())) {
				this.previousLevel();
			}
		} else if (function.equals("nextLevelHighscore")) {
			if ((!this.bRotate) && (!this.level.isBFalling())) {
				this.nextLevelHighscore(+1);
			}
		} else if (function.equals("previousLevelHighscore")) {
			if ((!this.bRotate) && (!this.level.isBFalling())) {
				this.nextLevelHighscore(-1);
			}
		} else if (function.equals("nextLevelMode")) {
			if ((!this.bRotate) && (!this.level.isBFalling())) {
				this.nextLevelMode();
			}
		} else if (function.equals("previousLevelMode")) {
			if ((!this.bRotate) && (!this.level.isBFalling())) {
				this.previousLevelMode();
			}
		} else if (function.equals("loadLevel")) {
			if ((!this.bRotate) && (!this.level.isBFalling())) {
				this.levelLoad(null);
			}
		} else if (function.equals("loadKartei")) {
			if ((!this.bRotate) && (!this.level.isBFalling())) {
				this.setButtonVisible(ApoStarzConstants.LOAD_KARTEI);
			}
		} else if (function.equals("levelKartei")) {
			if ((!this.bRotate) && (!this.level.isBFalling())) {
				this.setButtonVisible(ApoStarzConstants.LEVEL_KARTEI);
			}
		} else if (function.equals("loadLevelSingle")) {
			if ((!this.bRotate) && (!this.level.isBFalling())) {
				String s = this.textArea.getText();
				if (s != null) {
					if (this.bHighscore) {
						this.curTimeLevel = ApoStarzConstants.highscore.getPositionForTimeLevels(s);
					}
					this.setLevelString(s);
				}
			}
		} else if (function.equals("normalMode")) {
			this.setNormalMode();
		} else if (function.equals("timeMode")) {
			this.setTimeMode();
		} else if (function.equals("highscoreMode")) {
			this.setHighscoreMode();
		} else if (function.equals("tutorialMode")) {
			this.setTutorialMode();
		} else if (function.equals("nextStep")) {
			if ((!this.bRotate) && (!this.level.isBFalling())) {
				this.nextStep();
			}
		} else if (function.equals("previousTheme")) {
			this.setThemeMode(-1);
		} else if (function.equals("nextTheme")) {
			this.setThemeMode(+1);
		} else if (function.equals("nextRotationSpeed")) {
			this.setRotationSpeed(+1);
		} else if (function.equals("previousRotationSpeed")) {
			this.setRotationSpeed(-1);
		}
		this.curMouseFunction = null;
	}
	
	private void setRotationSpeed(int curChange) {
		this.curRotationSpeed += curChange;
		if (this.curRotationSpeed >= ApoStarzConstants.ROTATION_SPEED.length) {
			this.curRotationSpeed = 0;
		} else if (this.curRotationSpeed < 0) {
			this.curRotationSpeed = ApoStarzConstants.ROTATION_SPEED.length - 1;
		}
	}
	
	protected void restart() {
		if ((this.bTime) && (this.bTimeEnd)) {
			this.setTimeMode();
		} else {
			this.steps = 0;
			this.curLevelStringHighscore = this.levelSolution;
			this.curSolution = "";
			this.level.restartLevel();
		}
	}
	
	private void nextLevelMode() {
		this.curLevelMode++;
		if (this.curLevelMode >= ApoStarzConstants.LEVELS.length) {
			this.curLevelMode = 0;
		}
		if (ApoStarzConstants.LEVELS[this.curLevelMode].equals("highscore")) {
			this.curLevel = 0;
			this.io.setAllLevel(ApoStarzConstants.highscore.getAllLevels());
			this.io.setLevelName("all Levels");
			this.setLevelString(this.io.getLevel(this.curLevel));
		} else if (ApoStarzConstants.LEVELS[this.curLevelMode].equals("only_six")) {
			this.curLevel = 0;
			this.io.setAllLevel(ApoStarzConstants.highscore.getAllLevelsWithSix());
			this.io.setLevelName("6 width level");
			this.setLevelString(this.io.getLevel(this.curLevel));
		} else {
			if (ApoConstants.B_APPLET) {
				this.levelLoad(ApoStarzGame.APPLET_LEVEL + ApoStarzConstants.LEVELS[this.curLevelMode]);				
			} else {
				this.levelLoad(System.getProperty("user.dir") + File.separator+ "levels" + File.separator + ApoStarzConstants.LEVELS[this.curLevelMode]);
			}
		}
	}
	
	private void previousLevelMode() {
		this.curLevelMode--;
		if (this.curLevelMode < 0) {
			this.curLevelMode = ApoStarzConstants.LEVELS.length - 1;
		}
		if (ApoStarzConstants.LEVELS[this.curLevelMode].equals("highscore")) {
			this.curLevel = 0;
			this.io.setAllLevel(ApoStarzConstants.highscore.getAllLevels());
			this.io.setLevelName("all Levels");
			this.setLevelString(this.io.getLevel(this.curLevel));
		} else if (ApoStarzConstants.LEVELS[this.curLevelMode].equals("only_six")) {
			this.curLevel = 0;
			this.io.setAllLevel(ApoStarzConstants.highscore.getAllLevelsWithSix());
			this.io.setLevelName("6 width level");
			this.setLevelString(this.io.getLevel(this.curLevel));
		} else {
			if (ApoConstants.B_APPLET) {
				this.levelLoad(ApoStarzGame.APPLET_LEVEL + ApoStarzConstants.LEVELS[this.curLevelMode]);				
			} else {
				this.levelLoad(System.getProperty("user.dir") + File.separator + "levels" + File.separator + ApoStarzConstants.LEVELS[this.curLevelMode]);
			}
		}
	}
	
	protected void nextLevel() {
		this.curLevel++;
		if (this.curLevel >= this.io.getLevelSize()) {
			this.curLevel = 0;
		}
		if (this.io.getLevelSize() > 0) {
			this.setLevelString(this.io.getLevel(this.curLevel));
		}
	}
	
	private void previousLevel() {
		this.curLevel--;
		if (this.curLevel < 0) {
			this.curLevel = this.io.getLevelSize() - 1;
		}
		if (this.curLevel >= 0) {
			this.setLevelString(this.io.getLevel(this.curLevel));
		}
	}

	@Override
	public void think(int delta) {
		if (this.bThink) {
			return;
		}
		this.bThink = true;
		if ((this.level.isBWin()) && (this.steps <= 0)) {
			this.level.setBWin(false);
		}
		if ((this.bTime) && (!this.bTimeEnd)) {
			this.curTime -= delta;
			if (this.curTime < 0) {
				this.curTime = 0;
				this.bTimeEnd = true;
			}
		}
		if (this.curKey != ApoStarzConstants.EMPTY) {
			this.thinkKeyLogic();
			this.bThink = false;
			return;
		} else if (this.curMouseFunction != null) {
			this.thinkMouseFunction();
			this.bThink = false;
			return;
		} 
		if (this.bTutorial) {
			this.tutorial.think(delta);
		} 
		if (this.bTimeEnd) {
			this.bThink = false;
			return;
		} else if ((this.level.isBWin()) || (this.level.isBLost()) || (this.bMenu)) {
			if (this.bTime) {
				if (this.level.isBWin()) {
					this.nextLevelHighscore(+1);
				} else {
					this.restart();
				}
			}
			this.bThink = false;
			return;
		} else if (this.bRotate) {
			this.curAngle += this.difAngle;
			if (Math.abs(this.curAngle) >= 90) {
				if (this.difAngle < 0) {
					this.curAngle = -90;
				} else {
					this.curAngle = 90;
				}
				this.bRotate = false;
			}
		} else if ((!this.bRotate) && (this.level.isBFalling())) {
			this.level.think(delta);
			if (!this.level.isBFalling()) {
				if ((this.bNormal) || (this.bTime) || (this.bTutorial)) {
					if (!super.getButtons()[3].isBVisible()) {
						super.getButtons()[3].setBVisible(true);
						super.getButtons()[4].setBVisible(true);
					}
				}
			}
		} else {
			this.level.think(delta);
		}
		this.bThink = false;
	}
	
	@Override
	public void render(Graphics2D g) {
		if (this.iBackground != null) {
			g.drawImage(this.iBackground, 0, 0, null);
		}
		if (this.bRotate) {
			AffineTransform originalTransform = g.getTransform();
			g.setTransform( AffineTransform.getRotateInstance( Math.toRadians( this.curAngle ), this.iRotate.getWidth()/2, this.iRotate.getHeight()/2 ) );
			g.drawImage( this.iRotate, 0, 0, null );
			g.setTransform(originalTransform);
		} else {
			this.level.render(g);
		}
		if (this.bMenu) {
			this.renderMenu(g);
		} else if (this.bNormal) {
			this.renderSteps(g);
			this.renderLevel(g);
		} else if (this.bTime) {
			this.renderTime(g);
			this.renderSteps(g);
		} else if (this.bHighscore) {
			this.renderHighscore(g);
		} else if (this.bTutorial) {
			this.tutorial.render(g);
		}
		this.renderButtons(g);
		if ((this.bTime) && (this.bTimeEnd)) {
			this.renderTimeAnalysis(g);
		} else if ((this.level.isBWin()) && (!this.bTutorial)) {
			this.renderWin(g);
		} else if ((this.level.isBLost()) && (!this.bTutorial)) {
			this.renderLost(g);
		}
		
		g.setColor(Color.black);
		g.setFont(ApoStarzConstants.FONT_NORMAL);
		g.drawString(this.VERSION, 2, ApoStarzConstants.GAME_HEIGHT - 2);
	}
	
	/**
	 * malt die Buttons
	 * @param g : das GraphicsObject
	 */
	public void renderButtons(Graphics2D g) {
		if (super.getButtons() != null) {
			for (int i = 0; i < super.getButtons().length; i++) {
				super.getButtons()[i].render(g);
			}
		}
	}

	private void renderTimeAnalysis(Graphics2D g) {
		g.drawImage(this.iAnalysis, ApoStarzConstants.GAME_GAME_WIDTH/2 - this.iAnalysis.getWidth()/2, ApoStarzConstants.GAME_HEIGHT/2 - this.iAnalysis.getHeight()/2, null);

		g.setFont(ApoStarzConstants.FONT_VERY_BIG);
		String s = "Time Trial";
		int w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH/2 - w/2, ApoStarzConstants.GAME_HEIGHT/2 + 80);
		
		g.setFont(ApoStarzConstants.FONT_NORMAL);
		s = "You pass "+ (this.curTimeLevel+0) +" level!";
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH/2 - w/2, ApoStarzConstants.GAME_HEIGHT/2 + 105);
				
		s = "Press Enter to start";
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH/2 - w/2 + 40, ApoStarzConstants.GAME_HEIGHT/2 + 145);
		
		s = "the time trial again";
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH/2 - w/2 + 40, ApoStarzConstants.GAME_HEIGHT/2 + 160);
		
		s = "You pass " + (this.curTimeLevel+0) + " levels";
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH/2 + 75 - w/2, ApoStarzConstants.GAME_HEIGHT/2 - 105);
	}
	
	private void renderWin(Graphics2D g) {
		g.drawImage(this.iAnalysis, ApoStarzConstants.GAME_GAME_WIDTH/2 - this.iAnalysis.getWidth()/2, ApoStarzConstants.GAME_HEIGHT/2 - this.iAnalysis.getHeight()/2, null);

		g.setFont(ApoStarzConstants.FONT_VERY_BIG);
		String s = "Congratulation";
		int w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH/2 - w/2, ApoStarzConstants.GAME_HEIGHT/2 + 80);
	
		g.setFont(ApoStarzConstants.FONT_NORMAL);
		s = "You pass the level!";
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH/2 - w/2, ApoStarzConstants.GAME_HEIGHT/2 + 105);
			
		s = "Press Enter to start";
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH/2 - w/2 + 40, ApoStarzConstants.GAME_HEIGHT/2 + 145);
	
		s = "the next level";
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH/2 - w/2 + 40, ApoStarzConstants.GAME_HEIGHT/2 + 160);

		
		s = "Steps: " + this.steps;
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH/2 + 80 - w/2, ApoStarzConstants.GAME_HEIGHT/2 - 120);
		
		if ((this.steps < this.curHighscore) || (this.curHighscore < 0)) {
			s = "NEW HIGHSCORE";
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH/2 + 80 - w/2, ApoStarzConstants.GAME_HEIGHT/2 - 95);
		} else {
			s = "highscore: " + this.curHighscore;
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH/2 + 80 - w/2, ApoStarzConstants.GAME_HEIGHT/2 - 95);
		}
	}

	private void renderLost(Graphics2D g) {
		g.setFont(ApoStarzConstants.FONT_BIG);
		g.drawImage(this.iAnalysis, ApoStarzConstants.GAME_GAME_WIDTH/2 - this.iAnalysis.getWidth()/2, ApoStarzConstants.GAME_HEIGHT/2 - this.iAnalysis.getHeight()/2, null);
		String s = "=( You lost the level =(";
		int w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH/2 - w/2, ApoStarzConstants.GAME_HEIGHT/2 + 75);

		g.setFont(ApoStarzConstants.FONT_NORMAL);
		s = "Please retry!";
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH/2 - w/2, ApoStarzConstants.GAME_HEIGHT/2 + 105);
		
		s = "Press Enter to start";
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH/2 - w/2 + 40, ApoStarzConstants.GAME_HEIGHT/2 + 145);
		
		s = "the next level";
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH/2 - w/2 + 40, ApoStarzConstants.GAME_HEIGHT/2 + 160);
	}
	
	protected void renderSteps(Graphics2D g) {
		g.setColor(Color.black);
		g.setFont(ApoStarzConstants.FONT_NORMAL);
		String s = "Steps: ";
		g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH + 5, 215);
		g.drawString(String.valueOf(this.steps), ApoStarzConstants.GAME_GAME_WIDTH + 110, 215);
		
		if (this.curHighscore > 0) {
			s = "Highscore: ";
			g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH + 5, 242);
			g.drawString(String.valueOf(this.curHighscore), ApoStarzConstants.GAME_GAME_WIDTH + 110, 242);
		}
	}
	
	private void renderLevel(Graphics2D g) {
		g.setColor(Color.black);
		g.setFont(ApoStarzConstants.FONT_NORMAL);
		String s = "";
		int w = -1;
		
		g.setColor(Color.black);
		g.drawLine(ApoStarzConstants.GAME_GAME_WIDTH + 5, 260, ApoStarzConstants.GAME_WIDTH - 5, 260);
		g.drawLine(ApoStarzConstants.GAME_GAME_WIDTH, 409, ApoStarzConstants.GAME_WIDTH, 409);

		if (super.getButtons()[5].isBVisible()) {			
			s = "Level";
			w = g.getFontMetrics().stringWidth(s);
			int x = ApoStarzConstants.GAME_GAME_WIDTH + ApoStarzConstants.GAME_MENU_WIDTH/2 - w/2;
			g.drawString(s, x, 285);
			g.drawLine(x - 5, 290, x + w + 5, 290);
			
			s = this.io.getLevelName();
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH + ApoStarzConstants.GAME_MENU_WIDTH/2 - w/2, 315);
		
			s = "" + (this.curLevel + 1) + " / " + this.io.getLevelSize();
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH + ApoStarzConstants.GAME_MENU_WIDTH/2 - w/2, 340);
			
			if (this.levelSolution.length() > 0) {
				s = "solution: " + this.levelSolution;
				w = g.getFontMetrics().stringWidth(s);
				//g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH + 5, 400);				
			}
		} else {			
			s = "Levelcode";
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH + ApoStarzConstants.GAME_MENU_WIDTH/2 - w/2, 290);
		}
	}
	
	private void renderMenu(Graphics2D g) {
		g.drawImage(this.iMenu, 0, 0, null);
		
		g.setColor(Color.yellow);
		g.fillRect(ApoStarzConstants.GAME_GAME_WIDTH + 3, 159, ApoStarzConstants.GAME_MENU_WIDTH - 6, 251);
		g.setColor(Color.black);		
		g.setFont(ApoStarzConstants.FONT_NORMAL);
		g.drawLine(ApoStarzConstants.GAME_GAME_WIDTH + 5, 350, ApoStarzConstants.GAME_WIDTH - 5, 350);
		g.drawLine(ApoStarzConstants.GAME_GAME_WIDTH + 5, 403, ApoStarzConstants.GAME_WIDTH - 5, 403);
		g.drawLine(ApoStarzConstants.GAME_GAME_WIDTH + 5, 450, ApoStarzConstants.GAME_WIDTH - 5, 450);
		g.drawLine(ApoStarzConstants.GAME_GAME_WIDTH + 5, 190, ApoStarzConstants.GAME_WIDTH - 5, 190);
		int x = ApoStarzConstants.GAME_GAME_WIDTH + ApoStarzConstants.GAME_MENU_WIDTH/2;
		if (super.getButtons()[13].isBOver()) { //tutorial
			this.drawString(g, "The tutorial Mode", x, 180);
			this.drawString(g, "Learn how to play", x, 220);
			this.drawString(g, "the game", x, 240);
		} else if (super.getButtons()[14].isBOver()) { //normal
			this.drawString(g, "The normal Mode", x, 180);
			this.drawString(g, "Try to solve all", x, 220);
			this.drawString(g, "the levels.", x, 240);
			this.drawString(g, "From very easy to", x, 270);
			this.drawString(g, "really heavy levels", x, 290);
			this.drawString(g, "You have no", x, 320);
			this.drawString(g, "pressure of time", x, 340);
		} else if (super.getButtons()[15].isBOver()) { //time
			this.drawString(g, "The time Mode", x, 180);
			this.drawString(g, "Your task is to", x, 220);
			this.drawString(g, "solve as many as", x, 240);
			this.drawString(g, "possible levels in", x, 260);
			this.drawString(g, "5 minutes", x, 280);
		} else if (super.getButtons()[16].isBOver()) { //highscore
			this.drawString(g, "The highscore Mode", x, 180);
			this.drawString(g, "If you don't know", x, 220);
			this.drawString(g, "how to solve a given", x, 240);
			this.drawString(g, "level", x, 260);
			this.drawString(g, "Here you can find the", x, 280);
			this.drawString(g, "solution", x, 300);
		} else {
			this.drawString(g, "by Dirk Aporius", x, 180);
			this.drawString(g, "Apo-Games", x, 220);
			this.drawString(g, "More than games", x, 240);
		}
		
		this.drawString(g, "Theme", x, 420);
		if (this.curTheme == ApoStarzConstants.THEME_BLUE) {
			this.drawString(g, "Blue", x, 442);
		} else if (this.curTheme == ApoStarzConstants.THEME_BLACK) {
			this.drawString(g, "Night", x, 442);
		}
		
		this.drawString(g, "Rotationspeed", x, 370);
		this.drawString(g, ApoStarzConstants.ROTATION_SPEED[this.curRotationSpeed], x, 392);
	}
	
	private void renderTime(Graphics2D g) {
		g.setColor(Color.yellow);
		g.fillRect(ApoStarzConstants.GAME_GAME_WIDTH + 3, 159, ApoStarzConstants.GAME_MENU_WIDTH - 6, 251);
		g.setColor(Color.black);		
		g.setFont(ApoStarzConstants.FONT_NORMAL);
		g.drawLine(ApoStarzConstants.GAME_GAME_WIDTH + 5, 450, ApoStarzConstants.GAME_WIDTH - 5, 450);
		g.drawLine(ApoStarzConstants.GAME_GAME_WIDTH + 5, 190, ApoStarzConstants.GAME_WIDTH - 5, 190);
		g.drawLine(ApoStarzConstants.GAME_GAME_WIDTH + 5, 250, ApoStarzConstants.GAME_WIDTH - 5, 250);
		int x = ApoStarzConstants.GAME_GAME_WIDTH + ApoStarzConstants.GAME_MENU_WIDTH/2;
		this.drawString(g, "The time Mode", x, 180);
		this.drawString(g, "Time: " + this.getTimeToDraw(this.curTime), x, 280);
		this.drawString(g, "Level: " + (this.curTimeLevel+1), x, 310);
	}

	private void renderHighscore(Graphics2D g) {
		g.setColor(Color.yellow);
		g.fillRect(ApoStarzConstants.GAME_GAME_WIDTH + 3, 159, ApoStarzConstants.GAME_MENU_WIDTH - 6, 251);
		g.setColor(Color.black);
		g.setFont(ApoStarzConstants.FONT_NORMAL);
		g.drawLine(ApoStarzConstants.GAME_GAME_WIDTH + 5, 450, ApoStarzConstants.GAME_WIDTH - 5, 450);
		g.drawLine(ApoStarzConstants.GAME_GAME_WIDTH + 5, 190, ApoStarzConstants.GAME_WIDTH - 5, 190);
		g.drawLine(ApoStarzConstants.GAME_GAME_WIDTH + 5, 245, ApoStarzConstants.GAME_WIDTH - 5, 245);
		int x = ApoStarzConstants.GAME_GAME_WIDTH + ApoStarzConstants.GAME_MENU_WIDTH/2;
		this.drawString(g, "The highscore Mode", x, 180);
		if (this.curHighscore > 0) {
			String s = "Highscore: ";
			g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH + 5, 215);
			g.drawString(String.valueOf(this.curHighscore), ApoStarzConstants.GAME_GAME_WIDTH + 110, 215);
		}
		if (this.levelSolution.length() > 0) {
			String s = "solution: " + this.curLevelStringHighscore;
			g.drawString(s, ApoStarzConstants.GAME_GAME_WIDTH + 5, 235);				
		}
		this.drawString(g, "" + (this.curTimeLevel+1) + "/" + ApoStarzConstants.highscore.getTimeLevelSize(), x, 265);
	}
	
	/**
	 * gibt den String mit der Zeitangabe zurück
	 * @return gibt den String mit der Zeitangabe zurück
	 */
	private String getTimeToDraw(int time) {
		if (time <= 0)
			return "";
		String min = String.valueOf(time/1000/60);
		String sec = ""+((time/1000)%60);
		String msec = ""+((time/10)%100);
		if (sec.length()<2) sec = "0"+sec;
		if (msec.length()<2) msec = "0"+msec;
		String timeString = min+":"+sec+":"+msec;
		
		return timeString;
	}
	
	protected void drawString(Graphics2D g, String s, int x, int y) {
		int w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, x - w/2, y);
	}
}
