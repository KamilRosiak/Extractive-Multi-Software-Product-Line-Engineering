package apoSimple.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apogames.ApoConstants;
import org.apogames.entity.ApoNewTextfield;
import org.apogames.help.ApoGameCounter;
import org.apogames.help.ApoHelp;
import org.apogames.help.ApoHighscore;

import apoSimple.ApoSimpleConstants;
import apoSimple.ApoSimpleImages;
import apoSimple.entity.ApoSimpleDrive;
import apoSimple.entity.ApoSimpleEntity;
import apoSimple.entity.ApoSimplePreview;
import apoSimple.entity.ApoSimpleString;

public class ApoSimpleGame extends ApoSimpleModel {
	
	public static final boolean B_TEST = false;
	
	public static final String LEVEL_STRING = "LEVELUP";
	
	public static final int POINTS_WIDTH = 40;
	
	public static final String MENU_STRING = "menu";
	public static final String MENU = "game_menu";
	public static final String UPLOAD_STRING = "upload";
	public static final String UPLOAD = "game_upload";
	public static final String MENU_SCORE_STRING = "menu";
	public static final String MENU_SCORE = "game_menuscore";
	public static final String NEW_STRING = "restart";
	public static final String NEW = "game_restart";
	public static final String NEXT_STRING = "next";
	public static final String NEXT = "game_next";
	public static final String RESTART_STRING = "restart";
	public static final String RESTART = "game_restart";
	public static final String NOTICE = "notice";
	public static final String RELOAD = "reload";
	public static final String MUSIC = "music";
	public static final String SOUND = "sound";
	public static final String HELP = "help";
	public static final String GOODIE = "goodie";
	public static final String COINS = "coins";
	public static final String BACK_SAVE = "save";
	public static final String BACK_MENU = "back_menu";
	public static final String BACK_BACK = "back";
	public static final String LEVEL_LEFT = "level_left";
	public static final String LEVEL_RIGHT = "level_right";
	public static final String SORTED_LEFT = "sorted_left";
	public static final String SORTED_RIGHT = "sorted_right";
	
	public static final Font FONT_SCORE = ApoSimpleConstants.ORIGINAL_FONT.deriveFont(60f).deriveFont(Font.BOLD);//new Font(Font.SANS_SERIF, Font.BOLD, 60);
	public static final Font FONT_STATICS = ApoSimpleConstants.ORIGINAL_FONT.deriveFont(40f).deriveFont(Font.BOLD);//new Font(Font.SANS_SERIF, Font.BOLD, 40);
	public static final Font FONT_RESTART = ApoSimpleConstants.ORIGINAL_FONT.deriveFont(24f).deriveFont(Font.BOLD);//new Font(Font.SANS_SERIF, Font.BOLD, 25);
	public static final Font FONT_COIN = ApoSimpleConstants.ORIGINAL_FONT.deriveFont(20f).deriveFont(Font.BOLD);
	public static final Font FONT_TEXTFIELD = ApoSimpleConstants.ORIGINAL_FONT.deriveFont(30f).deriveFont(Font.BOLD);//new Font(Font.SANS_SERIF, Font.BOLD, 30);
	public static final Font FONT_BUTTON = ApoSimpleConstants.ORIGINAL_FONT.deriveFont(18f).deriveFont(Font.BOLD);//new Font(Font.SANS_SERIF, Font.BOLD, 18);
	public static final Font FONT_LITTLE = ApoSimpleConstants.ORIGINAL_FONT.deriveFont(12f).deriveFont(Font.BOLD);//new Font(Font.SANS_SERIF, Font.BOLD, 18);
	public static final Font FONT_ONEBUTTON = ApoSimpleConstants.ORIGINAL_FONT.deriveFont(15f).deriveFont(Font.BOLD);
	
	public static final int LEVEL_UP_TIME = 45;
	
	public static final int UPLOAD_TIME = 3000;
	public static final int UPLOAD_TIME_TEST = 5000;
	
	private BufferedImage iBackground, iBackgroundOrg;
	
	private ApoNewTextfield textField;
	
	private int points = 0;
	
	private int level = 0;
	
	private int moves = 0;
	
	private ApoSimpleEntity[][] entities;
	
	private ApoSimpleDrive driver;
	
	private Rectangle2D.Float rec;
	
	private boolean bChangeY;
	
	private boolean bLevelUp;
	
	private int curLevelUpTime;
	
	private String curLevelString;
	
	private ArrayList<ApoSimpleString> strings;
	
	private Point position;
	
	private int curTimeUpload;
	
	private boolean bUploadFalse;
	
	private boolean bAchievement;
	
	private ApoSimpleGameAchievements achievements;
	
	private BufferedImage iBackgroundReal, iBackgroundScore, iBackgroundBack;
	
	private ApoSimplePreview preview;
	
	private int uploadTime;
	
	protected boolean bHandCursor;
	
	private int count;
	
	private boolean bNotice;
	
	private ApoSimpleNotice notice;
	
	private Point mouseInLevel;
	
	private boolean bHelp;
	
	private boolean bGoodie;
	
	private int goodieCount;
	
	private boolean bFlowers;
	
	private boolean bBackMenu;
	
	private boolean bUsedCheat;
	
	private boolean bCoinShow;
	
	private boolean bClassic;
	
	public ApoSimpleGame(ApoSimplePanel game) {
		super(game);

		this.bClassic = false;
		
		if (this.notice == null) {
			this.notice = new ApoSimpleNotice(this);
		}
		
		this.mouseInLevel = new Point(-1, -1);
		
		this.makeTextField();
		
		boolean bHelp = true;
		if (ApoConstants.B_APPLET) {
			try {
				String load = ApoHelp.loadData(new URL("http://www.apo-games.de/apoSheeptastic/"), "apoSheeptastic_help");
				if ((load != null) && (load.length() > 0)) {
					bHelp = Boolean.valueOf(load);
				}
			} catch (MalformedURLException e) {
				bHelp = true;
			} catch (Exception e) {
				bHelp = true;
			}
		}
		this.setbHelp(bHelp);
	}
	
	public boolean isbClassic() {
		return this.bClassic;
	}

	public void setbClassic(boolean bClassic) {
		this.bClassic = bClassic;
	}

	public void usedCheat() {
		this.bUsedCheat = true;
	}

	public boolean isbGoodie() {
		return this.bGoodie;
	}

	public void setbGoodie(boolean bGoodie) {
		this.bGoodie = bGoodie;
	}

	public boolean isbHelp() {
		return this.bHelp;
	}

	public void setbHelp(boolean bHelp) {
		this.bHelp = bHelp;
		
		ApoSimpleButtonNot help = (ApoSimpleButtonNot)(this.getGame().getButtons()[55]);
		help.setBActive(this.bHelp);
		
		if (this.bHelp) {
			if ((this.level - 1 >= 0) && (this.level - 1 < ApoSimpleConstants.HELP.length) && (ApoSimpleConstants.HELP[this.level - 1].length() > 0) && (this.isbHelp())) {
				this.getStrings().add(new ApoSimpleString(295, 10, 50, ApoSimpleConstants.HELP[this.level - 1], true, 5000, false, ApoSimpleGame.FONT_BUTTON));
				
				this.getStrings().add(new ApoSimpleString(295, 430, 50, ApoSimpleConstants.HELP_NOTICE, true, 5000, false, ApoSimpleGame.FONT_BUTTON));
			}
		}
		if (ApoConstants.B_APPLET) {
			try {
				ApoHelp.saveData(new URL("http://www.apo-games.de/apoSheeptastic/"), "apoSheeptastic_help", String.valueOf(this.bHelp));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void makeTextField() {
		if (this.textField == null) {
			int width = 200;
			int height = 50;
			int x = ApoSimpleConstants.GAME_WIDTH/2;
			int y = ApoSimpleConstants.GAME_HEIGHT/2;
			this.textField = new ApoNewTextfield(x - 40, y - 10, width, height, ApoSimpleGame.FONT_TEXTFIELD);
			this.textField.setMaxLength(10);
			this.loadName("");
		}
	}
	
	public void setCoinsCost(int cost) {
		if (this.bCoinShow) {
			if (ApoSimpleConstants.REGION.equals("de")) {
				this.getStrings().add(new ApoSimpleString(235, 220, 50, "Tipp wird schon angezeigt!", true, 2000, false));				
			} else {
				this.getStrings().add(new ApoSimpleString(235, 220, 50, "Tipp is visible!", true, 2000, false));
			}
			return;
		}
		if ((this.getGame().getCoins() - cost >= 0) && (!this.bCoinShow)) {
			int goalX = -1;
			int goalY = -1;
			this.bCoinShow = true;
			int size = 0;
			ApoSimplePreview preview = new ApoSimplePreview(this);
			for (int y = 0; y < this.entities.length; y++) {
				for (int x = 0; x < this.entities[0].length; x++) {
					preview.setMouse(x, y);
					if (size < preview.getSize()) {
						size = preview.getSize();
						goalX = x;
						goalY = y;
					}
				}
			}
			if (goalX >= 0) {
				this.getGame().setCoins(this.getGame().getCoins() - cost, false);
				this.entities[goalY][goalX].setbGoodie(0);
			}
		} else {
			if (ApoSimpleConstants.REGION.equals("de")) {
				this.getStrings().add(new ApoSimpleString(235, 220, 50, "Nicht genug Münzen vorhanden!", true, 2000, false));				
			} else {
				this.getStrings().add(new ApoSimpleString(235, 220, 50, "Not enough coins!", true, 2000, false));
			}
		}
	}
	
	@Override
	public void init() {
		this.mouseInLevel = new Point(-1, -1);
		
		this.makePreview();
		this.bHandCursor = false;
		this.makeBackground();
		this.makeNewLevel();
		this.driver = null;
		this.bNotice = false;
		
		if (this.textField != null) {
			this.textField.setX(ApoSimpleConstants.GAME_WIDTH/2 - 40);
			this.textField.setY(ApoSimpleConstants.GAME_HEIGHT/2 - 10);
		}

		this.makeButtonVisible();
		this.showButtons();
		this.setBackMenu(false);
		this.setbHelp(this.bHelp);
	}
	
	public void makeButtonVisible() {
		ApoSimpleButtonNot effects = (ApoSimpleButtonNot)(this.getGame().getButtons()[54]);
		effects.setBActive(this.getGame().isbSoundEffects());

		ApoSimpleButtonNot music = (ApoSimpleButtonNot)(this.getGame().getButtons()[53]);
		music.setBActive(this.getGame().isbSoundMusic());
	}
	
	public void setBackMenu(final boolean bBackMenu) {
		this.bBackMenu = bBackMenu;
		for (int i = 58; i < 61; i++) {
			this.getGame().getButtons()[i].setBVisible(bBackMenu);
		}
	}
	
	public void showButtons() {
		super.getGame().getButtons()[55].setBVisible(true);
		super.getGame().getButtons()[56].setBVisible(true);
	}
	
	public void makePreview() {
		if (this.preview == null) {
			this.preview = new ApoSimplePreview(this);
		}
	}
	
	public BufferedImage getBackgroundScore() {
		if (this.iBackgroundScore == null) {
			this.iBackgroundScore = this.getGame().getImages().getImage("images/game_real.png", false);
		}
		return this.iBackgroundScore;
	}
	
	public BufferedImage getBackgroundBack() {
		if (this.iBackgroundBack == null) {
			this.iBackgroundBack = this.getGame().getImages().getImage("images/game_back.png", false);
		}
		return this.iBackgroundBack;
	}
	
	public void makeBackground() {
		if (this.iBackground == null) {
			this.iBackgroundOrg = this.getGame().getImages().getImage("images/game.png", false);
		}
	}
	
	public void makeBackgroundWithFence() {
		this.iBackground = new BufferedImage(this.iBackgroundOrg.getWidth(), this.iBackgroundOrg.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = this.iBackground.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(this.iBackgroundOrg, 0, 0, null);
		g.setColor(new Color(133, 209, 2));
		for (int y = 0; y < this.entities.length; y++) {
			for (int x = 0; x < this.entities[0].length; x++) {
				boolean bUp = false;
				if ((y == 0) || (this.entities[y - 1][x].getDirection() == ApoSimpleConstants.FENCE)) {
					bUp = true;
				}

				boolean bDown = false;
				if ((y == this.entities.length - 1) || (this.entities[y + 1][x].getDirection() == ApoSimpleConstants.FENCE)) {
					bDown = true;
				}

				boolean bRight = false;
				if ((x == this.entities[0].length - 1) || (this.entities[y][x + 1].getDirection() == ApoSimpleConstants.FENCE)) {
					bRight = true;
				}
				
				boolean bLeft = false;
				if ((x == 0) || (this.entities[y][x - 1].getDirection() == ApoSimpleConstants.FENCE)) {
					bLeft = true;
				}

				boolean bLeftUp = false;
				if ((y == 0) || (x == 0) || (this.entities[y - 1][x - 1].getDirection() == ApoSimpleConstants.FENCE)) {
					bLeftUp = true;
				}
				
				boolean bLeftDown = false;
				if ((y == this.entities.length - 1) || (x == 0) || (this.entities[y + 1][x - 1].getDirection() == ApoSimpleConstants.FENCE)) {
					bLeftDown = true;
				}

				boolean bRightUp = false;
				if ((y == 0) || (x == this.entities[0].length - 1) || (this.entities[y - 1][x + 1].getDirection() == ApoSimpleConstants.FENCE)) {
					bRightUp = true;
				}

				boolean bRightDown = false;
				if ((y == this.entities.length - 1) || (x == this.entities[0].length - 1) || (this.entities[y + 1][x + 1].getDirection() == ApoSimpleConstants.FENCE)) {
					bRightDown = true;
				}
				if (this.entities[y][x].getDirection() == ApoSimpleConstants.FENCE) {
					if (x == 0) {
						if (y == 0) {
							g.fillRect(0, 0, 20, 20);
						} else if (y == this.entities.length - 1) {
							g.fillRect(0, 10 + (y + 1) * 65 - 2, 20, 22);
						}
						g.fillRect(0, 10 + y * 65, 20, 65);
					} else if (x == this.entities[0].length - 1) {
						if (y == 0) {
							g.fillRect((x+ 1) * 65, 0, 20, 20);
						} else if (y == this.entities.length - 1) {
							g.fillRect((x+ 1) * 65, 10 + (y + 1) * 65 - 2, 20, 22);
						}
						g.fillRect((x+ 1) * 65, 10 + y * 65, 20, 65);
					}
					if (y == 0) {
						g.fillRect(10 + x * 65, 0, 65, 20);
					} else if (y == this.entities.length - 1) {
						g.fillRect(10 + x * 65, 10 + (y + 1) * 65 - 2, 65, 22);
					}
					
					if ((bUp) && (bDown) && (bRight) && (bLeft)) {
						
					} else if ((bDown) && (bRight) && (bLeft)) {
						g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(65, 0, 65, 65), 10 + x * 65, 10 + y * 65, null);
					} else if ((bUp) && (bRight) && (bLeft)) {
						g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(65, 130, 65, 65), 10 + x * 65, 10 + y * 65, null);
					} else if ((bUp) && (bDown) && (bLeft)) {
						g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(130, 65, 65, 65), 10 + x * 65, 10 + y * 65, null);
					} else if ((bUp) && (bDown) && (bRight)) {
						g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(0, 65, 65, 65), 10 + x * 65, 10 + y * 65, null);
					} else if ((bUp) && (bDown)) {
						g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(0, 65, 30, 65), 10 + x * 65, 10 + y * 65, null);
						g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(130 + 30, 65, 35, 65), 10 + 30 + x * 65, 10 + y * 65, null);
					} else if ((bLeft) && (bRight)) {
						g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(65, 0, 65, 30), 10 + x * 65, 10 + y * 65, null);
						g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(65, 130 + 30, 65, 35), 10 + x * 65, 10 + y * 65 + 30, null);
					} else if ((bLeft) && (bUp)) {
						g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(130, 130, 65, 65), 10 + x * 65, 10 + y * 65, null);
					} else if ((bLeft) && (bDown)) {
						g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(130, 0, 65, 65), 10 + x * 65, 10 + y * 65, null);
					} else if ((bRight) && (bUp)) {
						g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(0, 130, 65, 65), 10 + x * 65, 10 + y * 65, null);
					} else if ((bRight) && (bDown)) {
						g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(0, 0, 65, 65), 10 + x * 65, 10 + y * 65, null);
					} else if (bLeft) {
						g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(130, 0, 65, 30), 10 + x * 65, 10 + y * 65, null);
						g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(130, 130 + 30, 65, 35), 10 + x * 65, 10 + y * 65 + 30, null);
					} else if (bRight) {
						g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(0, 0, 65, 30), 10 + x * 65, 10 + y * 65, null);
						g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(0, 130 + 30, 65, 35), 10 + x * 65, 10 + y * 65 + 30, null);
					} else if (bUp) {
						g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(0, 130, 30, 65), 10 + x * 65, 10 + y * 65, null);
						g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(130 + 30, 130, 35, 65), 10 + 30 + x * 65, 10 + y * 65, null);
					} else if (bDown) {
						g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(0, 0, 30, 65), 10 + x * 65, 10 + y * 65, null);
						g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(130 + 30, 0, 35, 65), 10 + 30 + x * 65, 10 + y * 65, null);
					} else {
						g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(65, 65, 65, 65), 10 + x * 65, 10 + y * 65, null);
					}
					if (x == 0) {
						if ((bLeft) && (bLeftUp) && (!bUp)) {
							g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(0, 130 + 45, 10, 20), (x) * 65 + 10 - 8, 10 + (y + 0) * 65 - 4, null);
						}
					}
					if (x == this.entities[0].length - 1) {
						if (!bUp) {
							g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(130 + 55, 130 + 45, 10, 20), (x + 1) * 65 + 10 - 6, 10 + (y + 0) * 65 - 4, null);
						}
					}
					if (y == 0) {
						if ((!bLeft) && (bLeftUp) && (bUp)) {
							g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(130 + 55, 0, 10, 20), 10 - 4 + (x + 0) * 65, 10 - 7 + (y) * 65, null);
						}
					}
					if (y == this.entities.length - 1) {
						if (!bLeft) {
							g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(130 + 55, 130 + 45, 10, 20), (x) * 65 + 10 - 4, 10 + (y + 1) * 65 - 6, null);
						}
					}
				} else {
					if ((bUp) && (bLeft) && (bLeftUp)) {
						if (y == 0) {
							g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(0, 0, 10, 20), 10 - 6 + x * 65, 10 - 7 + y * 65, null);
						} else if (x == 0) {
							g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(0, 0, 10, 20), 10 - 8 + x * 65, 10 - 16 + y * 65, null);
						} else {
							g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(0, 0, 10, 20), 10 - 6 + x * 65, 10 - 16 + y * 65, null);
						}
					}
					if ((bUp) && (bRight) && (bRightUp)) {
						if (y == 0) {
//							g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(130 + 55, 0, 10, 20), 10 - 4 + (x + 1) * 65, 10 - 7 + (y) * 65, null);
						} else if (x == this.entities[0].length - 1) {
							g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(130 + 55, 0, 10, 20), 10 - 6 + (x + 1) * 65, 10 - 16 + (y) * 65, null);
						} else {
							g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(130 + 55, 0, 10, 20), 10 - 4 + (x + 1) * 65, 10 - 16 + (y) * 65, null);
						}
					}
					if ((bDown) && (bRight) && (bRightDown)) {
						if (x == this.entities[0].length - 1) {
							
						} else {
							g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(130 + 55, 130 + 45, 10, 20), (x + 1) * 65 + 10 - 4, 10 + (y + 1) * 65 - 4, null);
						}
					}
					if ((bDown) && (bLeft) && (bLeftDown)) {
						if (x != 0) {
							g.drawImage(ApoSimpleImages.FENCE_BIG.getSubimage(0, 130 + 45, 10, 20), (x) * 65 + 10 - 6, 10 + (y + 1) * 65 - 4, null);
						}
					}
				}
			}
		}
		g.dispose();
	}
	
	public void makeNewLevel() {
		this.makeLevel(1);
	}
	
	public void makeLevel(int level) {
		this.resetValues();
		this.level = level;
		this.bCoinShow = false;
		this.bFlowers = true;
		if (ApoSimplePanel.level_save != null) {
			this.bFlowers = false;
		}
		if (this.achievements == null) {
			this.achievements = new ApoSimpleGameAchievements(this);
		}
		this.fillLevel(level, false);
		this.makeBackgroundWithFence();
		this.makeRec();
		if (level == 1) {
			if (this.bFlowers) {
				this.points = 0;
			}
			this.bUsedCheat = false;
		}
		if ((!this.bClassic) || ((level != 3) && (level != 5) && (level != 7) && (level != 10))) {
			int time = 5000;
			if (level == 1) {
				time = 8000;
			}
			if ((level - 1 >= 0) && (level - 1 < ApoSimpleConstants.HELP.length) && (ApoSimpleConstants.HELP[level - 1].length() > 0) && (this.isbHelp())) {
				this.getStrings().add(new ApoSimpleString(295, 10, 50, ApoSimpleConstants.HELP[level - 1], true, time, false, ApoSimpleGame.FONT_BUTTON));
				
				this.getStrings().add(new ApoSimpleString(295, 430, 50, ApoSimpleConstants.HELP_NOTICE, true, time, false, ApoSimpleGame.FONT_BUTTON));
			}
		}
		if (this.bClassic) {
			this.getStrings().add(new ApoSimpleString(295, 230, 50, "classic-mode", true, 5000, false, ApoSimpleGame.FONT_BUTTON));
		}
		if (this.bFlowers) {
			if (level == 1) {
				this.goodieCount = ApoSimpleConstants.GOODIE_COUNT;
				this.moves = ApoSimpleConstants.START_MOVES;
				this.achievements = new ApoSimpleGameAchievements(this);
			} else {
				this.moves = ApoSimpleConstants.ADD_MOVES + this.moves;
				if (this.moves > ApoSimpleConstants.MAX_MOVES) {
					this.moves = ApoSimpleConstants.MAX_MOVES;
				}
				if (level % ApoSimpleConstants.GOODIE_ADD_ALL == 0) {
					this.goodieCount += 1;
					if (this.goodieCount > ApoSimpleConstants.MAX_GOODIE_COUNT) {
						this.goodieCount = ApoSimpleConstants.MAX_GOODIE_COUNT;
					}
				}
			}
		}
		int count = 2 + level;
		if (count >= this.entities.length * this.entities[0].length - 5) {
			count = this.entities.length * this.entities[0].length - 5;
		}
		this.count = count;
		if (this.bFlowers) {
			this.setFlowers(count);
		}
	}
	
	private boolean canBeSolved() {
		int count = 0;
		for (int y = 0; y < this.entities.length; y++) {
			for (int x = 0; x < this.entities[0].length; x++) {
				if (this.entities[y][x].isBVisible()) {
					if ((this.entities[y][x].getType() == ApoSimpleConstants.EMPTY) &&
						(this.entities[y][x].getDirection() != ApoSimpleConstants.EMPTY) &&
						(this.entities[y][x].getDirection() != ApoSimpleConstants.REAL_EMPTY) &&
						(this.entities[y][x].getDirection() != ApoSimpleConstants.DOG_DOWN) &&
						(this.entities[y][x].getDirection() != ApoSimpleConstants.DOG_LEFT) &&
						(this.entities[y][x].getDirection() != ApoSimpleConstants.DOG_RIGHT) &&
						(this.entities[y][x].getDirection() != ApoSimpleConstants.DOG_UP)) {
						count += 1;
					}
				}
			}
		}
		if (count <= 2) {
			return false;
		}
		return true;
	}
	
	public void saveLevel() {
		String levelString = "";
		for (int y = 0; y < this.entities.length; y++) {
			for (int x = 0; x < this.entities[0].length; x++) {
				int value = this.entities[y][x].getDirection();
				if (this.entities[y][x].isBBlack()) {
					if ((value >= ApoSimpleConstants.UP) && (value <= ApoSimpleConstants.RIGHT)) {
						value += 8;
					} else if ((value >= ApoSimpleConstants.DOUBLE_UP) && (value <= ApoSimpleConstants.DOUBLE_RIGHT)) {
						value += 4;
					}
				}
				if (this.entities[y][x].getType() != ApoSimpleConstants.EMPTY) {
					value += 50;
				}
				String s = String.valueOf(value);
				while (s.length() < 2) {
					s = "0" + s;
				}
				levelString += String.valueOf(s);
			}
		}
		String level = String.valueOf(this.level);
		while (level.length() < 3) {
			level = "0" + level;
		}
		levelString += level;
		levelString += String.valueOf(this.goodieCount);
		levelString += String.valueOf(this.moves);
		
		int c = 0;
		if (this.achievements.isBAllInOne()) {
			c = 1;
		}
		levelString += String.valueOf(c);
		
		c = 0;
		if (this.achievements.isBColumn()) {
			c = 1;
		}
		levelString += String.valueOf(c);
		
		c = 0;
		if (this.achievements.isBHalfOfField()) {
			c = 1;
		}
		levelString += String.valueOf(c);
		
		c = 0;
		if (this.achievements.isBRow()) {
			c = 1;
		}
		levelString += String.valueOf(c);
		
		levelString += String.valueOf(this.points);
//		System.out.println("save: "+levelString);
		ApoSimplePanel.level_save = levelString;
		this.getGame().saveSavedLevel();
	}
	
	public void fillLevel(int level, boolean bTutorial) {
		if ((ApoSimplePanel.level_save != null) && (ApoSimplePanel.level_save.length() > 0) && (!bTutorial)) {
			try {
				String s = ApoSimplePanel.level_save;
				for (int y = 0; y < this.entities.length; y++) {
					for (int x = 0; x < this.entities[0].length; x++) {
						int curInt = Integer.valueOf(s.substring(0, 2));
						int type = ApoSimpleConstants.EMPTY;
						if (curInt >= 50) {
							type = this.getRandomColor();
							curInt -= 50;
						}
						int width = ApoSimpleConstants.ENTITY_WIDTH;
						this.entities[y][x] = new ApoSimpleEntity(10 + x * (width + 5), 10 + y * (width + 5), width, width, type, curInt);
						s = s.substring(2);
					}
				}
				this.level = Integer.valueOf(s.substring(0, 3));
				s = s.substring(3);
				this.goodieCount = Integer.valueOf(s.substring(0, 1));
				s = s.substring(1);
				this.moves = Integer.valueOf(s.substring(0, 1));
				s = s.substring(1);
				
				boolean bAll = false;
				int c = Integer.valueOf(s.substring(0, 1));
				s = s.substring(1);
				if (c > 0) {
					bAll = true;
				}
				
				boolean bCol = false;
				c = Integer.valueOf(s.substring(0, 1));
				s = s.substring(1);
				if (c > 0) {
					bCol = true;
				}
				
				boolean bHalf = false;
				c = Integer.valueOf(s.substring(0, 1));
				s = s.substring(1);
				if (c > 0) {
					bHalf = true;
				}
				
				boolean bRow = false;
				c = Integer.valueOf(s.substring(0, 1));
				s = s.substring(1);
				if (c > 0) {
					bRow = true;
				}
				
				this.achievements.setAll(bAll, bCol, bHalf, bRow);
				
				this.points = Integer.valueOf(s);
			} catch (Exception ex) {
				this.bFlowers = true;
				ApoSimplePanel.level_save = null;
				this.fillLevel(level, bTutorial);
			} finally {
				ApoSimplePanel.level_save = null;
			}
		} else {
			this.bFlowers = true;
			for (int y = 0; y < this.entities.length; y++) {
				for (int x = 0; x < this.entities[0].length; x++) {
					int direction = this.getRandomDirection();
					int width = ApoSimpleConstants.ENTITY_WIDTH;
					this.entities[y][x] = new ApoSimpleEntity(10 + x * (width + 5), 10 + y * (width + 5), width, width, ApoSimpleConstants.EMPTY, direction);
					this.entities[y][x].setChangeY(this.entities.length * (width + 5));
				}
			}
			if (!this.canBeSolved()) {
				this.fillLevel(level, bTutorial);
			}
		}
	}
	
	public void setFlowers(int count) {
		while (count > 0) {
			int x = (int)(Math.random() * this.entities[0].length);
			if (x >= this.entities[0].length) {
				x = 0;
			}
			int y = (int)(Math.random() * this.entities.length);
			if (y >= this.entities.length) {
				y = 0;
			}
			if (this.entities[y][x].getType() == ApoSimpleConstants.EMPTY) {
				count -= 1;
				int color = this.getRandomColor();
				this.entities[y][x].setType(color);
			}
		}
	}
	
	public int getRandomColor() {
		int color = (int)(Math.random() * ApoSimpleConstants.BLUE);
		if (color == ApoSimpleConstants.EMPTY) {
			color = ApoSimpleConstants.BLUE;
		}
		return color;
	}
	
	public void resetValues() {
		this.setDriver(null);
		this.bLevelUp = false;
		this.bAchievement = false;
		this.curLevelUpTime = 0;
		this.getGame().setBLoose(false);
		if (this.textField != null) {
			this.textField.setBVisible(false);
		}
		ApoSimpleConstants.CHEAT = false;
		this.getGame().setButtonVisible(ApoSimpleConstants.BUTTON_GAME);
		this.showButtons();
		this.strings = new ArrayList<ApoSimpleString>();
		this.curLevelString = ApoSimpleGame.LEVEL_STRING;
		this.entities = new ApoSimpleEntity[7][7];
	}
	
	public void makeRec() {
		int levelWidth = (int)(ApoSimpleConstants.ENTITY_WIDTH + 5) * this.entities[0].length;
		int levelHeight = (int)(ApoSimpleConstants.ENTITY_WIDTH + 5) * this.entities.length;
		this.rec  = new Rectangle2D.Float(10, 10, levelWidth, levelHeight);
	}
	
	public int getCount() {
		return this.count;
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (this.bNotice) {
			this.notice.keyButtonReleased(button, character);
			return;
		}
		if (keyButtonReleasedTextField(button, character)) {
		} else if (button == KeyEvent.VK_R) {
			ApoSimplePanel.level_save = null;
			this.getGame().saveSavedLevel();
			this.makeNewLevel();
		}
		if (button == KeyEvent.VK_ESCAPE) {
			if (!this.bBackMenu) {
				this.setBackMenu(true);
			} else {
				this.mouseButtonFunction(ApoSimpleGame.BACK_MENU);
			}
		}
	}
	
	public boolean keyButtonReleasedTextField(int button, char character) {
		if ((this.textField != null) && (this.textField.isBVisible()) && (this.textField.isBSelect())) {
			boolean bShift = false;
			if (this.getGame().getKeyboard().isPressed(KeyEvent.VK_CONTROL)) {
				if (button == KeyEvent.VK_V) {
					String s = ApoHelp.getClipboardContents();
					if (s != null) {
						for (int i = 0; i < s.length(); i++) {
							char chara = s.charAt(i);
							this.textField.addCharacter(button, chara);
						}
						bShift = true;
					}
				} else if (button == KeyEvent.VK_C) {
					String s = this.textField.getSelectedString();
					if (s != null) {
						ApoHelp.setClipboardContents(s);
					}
					bShift = true;
				} else if (button == KeyEvent.VK_X) {
					String s = this.textField.getSelectedString();
					if (s != null) {
						ApoHelp.setClipboardContents(s);
						this.textField.deleteSelectedString();
					}
					bShift = true;
				}
			} else if (this.getGame().getKeyboard().isPressed(KeyEvent.VK_SHIFT)) {
				if (button == KeyEvent.VK_LEFT) {
					this.textField.nextSelectedPosition(this.textField.getPosition() - 1);
					bShift = true;
				} else if (button == KeyEvent.VK_RIGHT) {
					this.textField.nextSelectedPosition(this.textField.getPosition() + 1);
					bShift = true;
				}
			}
			if (!bShift) {
				if (button > 0) {
					this.textField.addCharacter(button, character);
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (this.bNotice) {
			this.notice.mouseButtonFunction(function);
			return;
		}
		if (this.bAchievement) {
			this.bAchievement = false;
			return;
		}
		if (this.textField != null) {
			this.textField.setBSelect(false);
		}
		if ((function.equals(ApoSimpleGame.MENU)) ||
			(function.equals(ApoSimpleGame.MENU_SCORE))) {
			this.getGame().setCoins(this.getGame().getCoins(), true);
			if (function.equals(ApoSimpleGame.MENU)) {
				this.setBackMenu(true);
			} else {
				ApoSimplePanel.level_save = null;
				this.getGame().saveSavedLevel();
				this.getGame().setMenu();
			}
		} else if (function.equals(ApoSimpleGame.BACK_SAVE)) {
			this.getGame().setCoins(this.getGame().getCoins(), true);
			if (this.getDriver() != null) {
				while (this.getDriver() != null) {
					this.think(ApoSimpleConstants.WAIT_TIME_THINK);
				}
			}
			this.saveLevel();
			this.getGame().setMenu();
		} else if (function.equals(ApoSimpleGame.BACK_BACK)) {
			this.getGame().setCoins(this.getGame().getCoins(), true);
			this.setBackMenu(false);
		} else if (function.equals(ApoSimpleGame.BACK_MENU)) {
			this.getGame().setCoins(this.getGame().getCoins(), true);
			ApoSimplePanel.level_save = null;
			this.getGame().saveSavedLevel();
			this.getGame().setMenu();
		} else if (function.equals(ApoSimpleGame.UPLOAD)) {
			this.curTimeUpload = -1;
			this.uploadTime = ApoSimpleGame.UPLOAD_TIME_TEST;
			new Thread(new Runnable() {
				@Override
				public void run() {
					ApoSimpleGame.this.uploadScore();
				}
			}).start();
			this.getGame().getButtons()[5].setBVisible(false);
		} else if (function.equals(ApoSimpleGame.NEW)) {
			this.getGame().setCoins(this.getGame().getCoins(), true);
			ApoSimplePanel.level_save = null;
			this.getGame().saveSavedLevel();
			this.makeNewLevel();
		} else if (function.equals(ApoSimpleGame.NOTICE)) {
			this.changeNoticeVisible(true);
		} else if (function.equals(ApoSimpleGame.MUSIC)) {
			ApoSimpleButtonNot b = (ApoSimpleButtonNot)(this.getGame().getButtons()[53]);
			b.setBActive(!b.isBActive());
			this.getGame().setbSoundMusic(b.isBActive());
		} else if (function.equals(ApoSimpleGame.SOUND)) {
			ApoSimpleButtonNot b = (ApoSimpleButtonNot)(this.getGame().getButtons()[54]);
			b.setBActive(!b.isBActive());
			this.getGame().setbSoundEffects(b.isBActive());
		} else if (function.equals(ApoSimpleGame.HELP)) {
			ApoSimpleButtonNot b = (ApoSimpleButtonNot)(this.getGame().getButtons()[55]);
			this.setbHelp(!b.isBActive());
		} else if (function.equals(ApoSimpleGame.GOODIE)) {
			if (this.getDriver() == null) {
				this.bGoodie = !this.bGoodie;
				if (this.goodieCount <= 0) {
					this.bGoodie = false;
					if (ApoSimpleConstants.REGION.equals("de")) {
						this.getStrings().add(new ApoSimpleString(235, 220, 50, "Keine Goodies mehr vorhanden. Spiele weiter für neue.", true, 1500, false));						
					} else {
						this.getStrings().add(new ApoSimpleString(235, 220, 50, "No goodie left! Play to get new goodies.", true, 1500, false));
					}
				}
			}
		} else if (function.equals(ApoSimpleGame.COINS)) {
			if (this.getDriver() == null) {
				this.setCoinsCost(this.getCost());
			}
		}
		super.getGame().playSound(ApoSimplePanel.SOUND_BUTTON, 100);
	}
	
	public void changeNoticeVisible(boolean bNotice) {
		if (this.driver == null) {
			this.bNotice = bNotice;
			if (this.bNotice) {
				super.getGame().setButtonVisible(ApoSimpleConstants.BUTTON_NOTICE);
			} else {
				super.getGame().setButtonVisible(ApoSimpleConstants.BUTTON_GAME);
				if (this instanceof ApoSimpleLevelGame) {
					super.getGame().getButtons()[40].setBVisible(true);
					if (this.getMoves() > 0) {
						this.getGame().getButtons()[57].setBVisible(false);
					}
				} else {
					this.showButtons();
				}
			}
		}
	}
	
	private void uploadScore() {
		String name = "";
		if ((this.textField != null) && (this.textField.getCurString() != null)) {
			name = this.textField.getCurString();
		}
		if (name.length() <= 0) {
			name = "guest";
		}
		if (this.bUsedCheat) {
			name += "_:-(";
		}
		boolean bRun = ApoHighscore.getInstance().save(this.level, this.points, name);
		this.curTimeUpload = ApoSimpleGame.UPLOAD_TIME;
		if (bRun) {
			this.bUploadFalse = false;
			this.getGame().setCoins(this.getGame().getCoins() + ApoSimpleConstants.ADD_COINS_UPLOAD, true);
			
			int width = (int)(ApoSimpleConstants.ENTITY_WIDTH + 5) * this.entities[0].length;
			int height = (int)(350);
			int x = ApoSimpleConstants.GAME_WIDTH/2 - width/2;
			int y = ApoSimpleConstants.GAME_HEIGHT/2 - height/2;
			
			if (ApoSimpleConstants.REGION.equals("de")) {
				this.strings.add(new ApoSimpleString(x + width/2, y + height - 5, 100, "Fürs Hochladen hast du "+ApoSimpleConstants.ADD_COINS_UPLOAD+" Münzen bekommen.", true, 4500, false));
			} else {
				this.strings.add(new ApoSimpleString(x + width/2, y + height - 5, 100, "You've got "+ApoSimpleConstants.ADD_COINS_UPLOAD+" coins for uploading your score.", true, 4500, false));
			}
		} else {
			this.bUploadFalse = true;
		}
	}
	
	@Override
	public void mouseButtonReleased(int mouseX, int mouseY, boolean bRight) {
		if (this.bNotice) {
			this.notice.mouseButtonReleased(mouseX, mouseY, bRight);
			return;
		}
		if (this.bAchievement) {
			this.bAchievement = false;
			return;
		}
		if (this.getGame().isBLoose()) {
			if ((this.textField != null) && (this.textField.isBVisible())) {
				this.textField.mouseReleased(mouseX, mouseY);
				if (this.textField.intersects(mouseX, mouseY)) {
					this.textField.setBSelect(true);
				} else {
					if (this.textField.isBSelect()) {
						this.saveName();
					}
					this.textField.setBSelect(false);
				}
			}
		}
		if ((this.driver != null) || (this.bChangeY) || (this.bBackMenu) || (this.getGame().isBLoose()) || (this.bLevelUp)) {
			return;
		}
		if (bRight) {
			if (this.bGoodie) {
				this.bGoodie = false;
				return;
			}
		}
		for (int y = 0; y < this.entities.length; y++) {
			for (int x = 0; x < this.entities[0].length; x++) {
				if ((!this.entities[y][x].isBVisible()) || (this.driver != null) || (this.entities[y][x].getDirection() == ApoSimpleConstants.FENCE)) {
				} else if (this.entities[y][x].intersects(mouseX, mouseY)) {
					if (this.bGoodie) {
						if (this.entities[y][x].isGoodiePossible()) {
							this.entities[y][x].setDirection(this.entities[y][x].getNextDirectionClockwise());
							this.goodieCount -= 1;
							this.bGoodie = false;
							if (this.goodieCount <= 0) {
								this.bGoodie = false;
							}
							return;
						}
					} else {
						if ((this.entities[y][x].getType() == ApoSimpleConstants.EMPTY) &&
							(this.entities[y][x].getDirection() != ApoSimpleConstants.EMPTY) &&
							(this.entities[y][x].getDirection() != ApoSimpleConstants.REAL_EMPTY) &&
							(this.entities[y][x].getDirection() != ApoSimpleConstants.DOG_UP) &&
							(this.entities[y][x].getDirection() != ApoSimpleConstants.DOG_DOWN) &&
							(this.entities[y][x].getDirection() != ApoSimpleConstants.DOG_LEFT) &&
							(this.entities[y][x].getDirection() != ApoSimpleConstants.FENCE) &&
							(this.entities[y][x].getDirection() != ApoSimpleConstants.STONE_1) &&
							(this.entities[y][x].getDirection() != ApoSimpleConstants.STONE_2) &&
							(this.entities[y][x].getDirection() != ApoSimpleConstants.STONE_3) &&
							(this.entities[y][x].getDirection() != ApoSimpleConstants.DOG_RIGHT)) {
							if (this.entities[y][x].isBOver()) {
								this.startDriver(x, y);
							} else if (this.mouseInLevel.x >= 0) {
								this.stringsForNot();
							}
							return;
						}	
					}
				}
			}
		}
		if (this.mouseInLevel.x >= 0) {
			this.stringsForNot();
		}
		if (this.bGoodie) {
			this.bGoodie = false;
		}
	}
	
	public String loadName(String name) {
		if ((this.textField != null) && (this.textField.isBVisible())) {
			if (ApoConstants.B_APPLET) {
				name = "guest";
				try {
					name = ApoHelp.loadData(new URL("http://www.apo-games.de/apoSimple/"), "name");
					if ((name == null) || (name.length() < 1)) {
						name = "guest";
					}
				} catch (Exception e) {
					name = "guest";
				}
				this.textField.setCurString(name);
			} else {
				if ((name == null) || (name.length() < 1)) {
					name = "guest";
				}
				this.textField.setCurString(name);
			}
		}
		return name;
	}
	
	public void saveName() {
		if ((this.textField != null) && (this.textField.getCurString() != null) && (this.textField.isBVisible())) {
			if (ApoConstants.B_APPLET) {
				try {
					ApoHelp.saveData(new URL("http://www.apo-games.de/apoSimple/"), "name", String.valueOf(this.textField.getCurString()));
				} catch (MalformedURLException e) {
				}
			}
		}
	}
	
	private final void stringsForNot() {
		if ((this.entities[this.mouseInLevel.y][this.mouseInLevel.x] != null) && (this.entities[this.mouseInLevel.y][this.mouseInLevel.x].isBVisible())) {
			super.getGame().playSound(ApoSimplePanel.SOUND_ERROR, 100);
			ApoSimpleEntity entity = this.entities[this.mouseInLevel.y][this.mouseInLevel.x];
			int x = (int)(entity.getX() - 15);
			int time = 1000;
			if (this.entities[this.mouseInLevel.y][this.mouseInLevel.x].getType() != ApoSimpleConstants.EMPTY) {
				this.strings.add(new ApoSimpleString(x, entity.getY() - entity.getHeight(), entity.getWidth() + 30, ApoSimpleConstants.ERROR[0], true, time, false));
			} else if ((this.entities[this.mouseInLevel.y][this.mouseInLevel.x].getDirection() <= ApoSimpleConstants.RIGHT) && (this.entities[this.mouseInLevel.y][this.mouseInLevel.x].getDirection() >= ApoSimpleConstants.UP)) {
				this.strings.add(new ApoSimpleString(x, entity.getY() - entity.getHeight(), entity.getWidth() + 30, ApoSimpleConstants.ERROR[1], true, time, false));
			} else if ((this.entities[this.mouseInLevel.y][this.mouseInLevel.x].getDirection() <= ApoSimpleConstants.BLACK_RIGHT) && (this.entities[this.mouseInLevel.y][this.mouseInLevel.x].getDirection() >= ApoSimpleConstants.BLACK_UP)) {
				this.strings.add(new ApoSimpleString(x, entity.getY() - entity.getHeight(), entity.getWidth() + 30, ApoSimpleConstants.ERROR[1], true, time, false));
			} else if ((this.entities[this.mouseInLevel.y][this.mouseInLevel.x].getDirection() <= ApoSimpleConstants.DOUBLE_RIGHT) && (this.entities[this.mouseInLevel.y][this.mouseInLevel.x].getDirection() >= ApoSimpleConstants.DOUBLE_UP)) {
				this.strings.add(new ApoSimpleString(x, entity.getY() - entity.getHeight(), entity.getWidth() + 30, ApoSimpleConstants.ERROR[1], true, time, false));
			} else if ((this.entities[this.mouseInLevel.y][this.mouseInLevel.x].getDirection() <= ApoSimpleConstants.DOUBLE_BLACK_RIGHT) && (this.entities[this.mouseInLevel.y][this.mouseInLevel.x].getDirection() >= ApoSimpleConstants.DOUBLE_BLACK_UP)) {
				this.strings.add(new ApoSimpleString(x, entity.getY() - entity.getHeight(), entity.getWidth() + 30, ApoSimpleConstants.ERROR[1], true, time, false));
			} else if (this.entities[this.mouseInLevel.y][this.mouseInLevel.x].getDirection() == ApoSimpleConstants.EMPTY) {
				this.strings.add(new ApoSimpleString(x, entity.getY() - entity.getHeight(), entity.getWidth() + 30, ApoSimpleConstants.ERROR[2], true, time, false));
			} else if ((this.entities[this.mouseInLevel.y][this.mouseInLevel.x].getDirection() <= ApoSimpleConstants.DOG_RIGHT) && (this.entities[this.mouseInLevel.y][this.mouseInLevel.x].getDirection() >= ApoSimpleConstants.DOG_UP)) {
				this.strings.add(new ApoSimpleString(x, entity.getY() - entity.getHeight(), entity.getWidth() + 30, ApoSimpleConstants.ERROR[3], true, time, false));
			} else if (this.entities[this.mouseInLevel.y][this.mouseInLevel.x].getDirection() == ApoSimpleConstants.FENCE) {
				this.strings.add(new ApoSimpleString(x, entity.getY() - entity.getHeight(), entity.getWidth() + 30, ApoSimpleConstants.ERROR[4], true, time, false));
			} else if ((this.entities[this.mouseInLevel.y][this.mouseInLevel.x].getDirection() <= ApoSimpleConstants.STONE_1) && (this.entities[this.mouseInLevel.y][this.mouseInLevel.x].getDirection() >= ApoSimpleConstants.STONE_3)) {
				this.strings.add(new ApoSimpleString(x, entity.getY() - entity.getHeight(), entity.getWidth() + 30, ApoSimpleConstants.ERROR[5], true, time, false));
			}
		}
	}
	
	public void startDriver(int x, int y) {
		super.getGame().playSound(ApoSimplePanel.SOUND_START, 100);
		this.driver = new ApoSimpleDrive(this.entities[y][x].getX(), this.entities[y][x].getY(), this.entities[y][x].getWidth(), this.entities[y][x].getWidth(), this.entities[y][x].getType(), this.entities[y][x].getDirection(), this.entities[y][x].isBBlack());
		this.entities[y][x].setBVisible(false);
		if (!this.entities[y][x].isBVisible()) {
			this.entities[y][x].setBBlack(false);
		}
		for (y = 0; y < this.entities.length; y++) {
			for (x = 0; x < this.entities[0].length; x++) {
				this.entities[y][x].setbGoodie(-1);
				this.entities[y][x].setBOver(false);
			}
		}
		this.moves -= 1;
	}
	
	@Override
	public boolean mousePressed(int x, int y, boolean bRight) {
		if (this.bNotice) {
			this.notice.mouseButtonReleased(x, y, bRight);
			return true;
		}
		if ((this.textField != null) && (this.textField.isBVisible())) {
			if (this.textField.mousePressed(x, y)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean mouseMoved(int mouseX, int mouseY) {
		this.mouseInLevel = new Point(-1, -1);
		if (this.bNotice) {
			this.notice.mouseMoved(mouseX, mouseY);
			return true;
		}
		if ((this.getGame().isBLoose()) || (this.bAchievement) || (this.bChangeY) || (this.bBackMenu) || (this.bLevelUp)) {
			if (this.bHandCursor) {
				this.getGame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				this.bHandCursor = false;
			}
			return false;
		}
		boolean bMove = false;
		for (int y = 0; y < this.entities.length; y++) {
			for (int x = 0; x < this.entities[0].length; x++) {
				if ((this.entities[y][x].intersects(mouseX, mouseY)) && (this.driver == null) && (this.entities[y][x].getDirection() != ApoSimpleConstants.REAL_EMPTY)) {
					this.mouseInLevel = new Point(x, y);
				}
				if ((!this.entities[y][x].isBVisible()) || (this.driver != null) || 
					(this.entities[y][x].getDirection() == ApoSimpleConstants.DOG_UP) ||
					(this.entities[y][x].getDirection() == ApoSimpleConstants.DOG_DOWN) ||
					(this.entities[y][x].getDirection() == ApoSimpleConstants.DOG_LEFT) ||
					(this.entities[y][x].getDirection() == ApoSimpleConstants.DOG_RIGHT) ||
					(this.entities[y][x].getDirection() == ApoSimpleConstants.FENCE) ||
					(this.entities[y][x].getDirection() == ApoSimpleConstants.STONE_1) ||
					(this.entities[y][x].getDirection() == ApoSimpleConstants.STONE_2) ||
					(this.entities[y][x].getDirection() == ApoSimpleConstants.STONE_3) ||
					(this.entities[y][x].getType() != ApoSimpleConstants.EMPTY) || (this.entities[y][x].getDirection() == ApoSimpleConstants.EMPTY)) {
					this.entities[y][x].setBOver(false);
				} else if (this.entities[y][x].intersects(mouseX, mouseY)) {
					this.entities[y][x].setBOver(true);
					bMove = true;
					if (this.preview != null) {
						if (!this.preview.setMouse(x, y)) {
							this.entities[y][x].setBOver(false);
							bMove = false;
						} else {
							this.mouseInLevel = new Point(-1, -1);
						}
					}
				} else {
					this.entities[y][x].setBOver(false);
				}
			}
		}
		if (!bMove) {
			if (this.preview != null) {
				this.preview.setMouse(-1, -1);
			}
			if (mouseX - 20 < this.entities[0].length * (ApoSimpleConstants.ENTITY_WIDTH + 5)) {
				if (this.bHandCursor) {
					this.getGame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					this.bHandCursor = false;
				}				
			}
		} else {
			if (!this.bHandCursor) {
				this.getGame().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				this.bHandCursor = true;
			}
		}
		if ((this.textField != null) && (this.textField.isBVisible())) {
			if (this.textField.getMove(mouseX, mouseY)) {
				return true;
			}
		}
		return true;
	}
	
	@Override
	public boolean mouseDragged(int x, int y) {
		if (this.bNotice) {
			this.notice.mouseDragged(x, y);
			return true;
		}
		if ((this.textField != null) && (this.textField.isBVisible())) {
			if (this.textField.mouseDragged(x, y)) {
				return true;
			}
		}
		return this.mouseMoved(x, y);
	}

	public boolean isbCoinShow() {
		return this.bCoinShow;
	}

	public void setbCoinShow(boolean bCoinShow) {
		this.bCoinShow = bCoinShow;
	}

	public void driverOutside() {
		int multi = this.driver.getMulti() - 1;
		
		this.driver = null;
		this.mouseInLevel = new Point(-1, -1);
		
		this.bCoinShow = false;
		
		this.preview.setMouse(-1, -1);
		boolean bBreak = false;
		int half = 0;
		for (int x = 0; x < this.entities[0].length; x++) {
			boolean bColumn = true;
			for (int y = this.entities.length - 1; y >= 0; y--) {
				if ((this.entities[y][x].isBVisible()) && (this.entities[y][x].getType() != ApoSimpleConstants.EMPTY)) {
					bBreak = true;
				}
				if (this.entities[y][x].isBVisible()) {
					bColumn = false;
				} else {
					half += 1;
				}
			}
			if (bColumn) {
				this.achievements.setBColumn(true);
			}
		}
		if (half > this.entities[0].length * this.entities.length / 2) {
			this.achievements.setBHalfOfField(true);
		}
		
		for (int y = this.entities.length - 1; y >= 0; y--) {
			boolean bRow = true;
			for (int x = 0; x < this.entities[0].length; x++) {
				if (this.entities[y][x].isBVisible()) {
					bRow = false;
				}
			}
			if (bRow) {
				this.achievements.setBRow(true);
			}
		}
		
		if (this.count == multi) {
				this.achievements.setBAllInOne(true);
		}
		
		if (!bBreak) {
			this.nextLevel();
			super.getGame().playSound(ApoSimplePanel.SOUND_WIN_LEVELUP, 100);
			return;
		}
		if (this.moves <= 0) {
			super.getGame().playSound(ApoSimplePanel.SOUND_LOOSE, 100);
			String name = "";
			if (this.textField != null) {
				name = this.textField.getCurString();
			}
			if (this.bClassic) {
				name += "_c";
			}
			if (this.bUsedCheat) {
				name += "_:-(";
			}
			ApoGameCounter.getInstance().save("game_"+this.level+"_"+this.points+"_"+name);
			this.getGame().setButtonVisible(ApoSimpleConstants.BUTTON_SCORE);
			this.iBackgroundReal = new BufferedImage(ApoSimpleConstants.GAME_WIDTH, ApoSimpleConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = this.iBackgroundReal.createGraphics();
			
			this.render(g);
			
			int width = (int)(ApoSimpleConstants.ENTITY_WIDTH + 5) * this.entities[0].length;
			int height = (int)(350);
			int x = ApoSimpleConstants.GAME_WIDTH/2 - width/2;
			int y = ApoSimpleConstants.GAME_HEIGHT/2 - height/2;
			
			g.drawImage(this.getBackgroundScore(), x, y, null);
			
			g.setFont(ApoSimpleGame.FONT_STATICS);
			String s = "Your score is";
			if (ApoSimpleConstants.REGION.equals("de")) {
				s = "Deine Punktzahl ist";
			}
			int w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, x + width/2 - w/2, y + 60);
			
			g.dispose();
			this.getGame().setBLoose(true);
			this.textField.setBVisible(true);
			this.textField.setBSelect(true);
			this.position = this.getGame().getPosition(this.points);
			
			if (!this.bClassic) {
				if (ApoSimpleConstants.REGION.equals("de")) {
					this.strings.add(new ApoSimpleString(x + width/2 - 50, y + height - 5, 100, ""+ApoSimpleConstants.ADD_COINS_UPLOAD+" Münzen klingen sexy? Dann lade deinen Punktestand hoch.", true, 4500, false));
				} else {
					this.strings.add(new ApoSimpleString(x + width/2 - 50, y + height - 5, 100, "You want "+ApoSimpleConstants.ADD_COINS_UPLOAD+" coins? Upload your score!", true, 4500, false));
				}
			} else {
				if (ApoSimpleConstants.REGION.equals("de")) {
					this.strings.add(new ApoSimpleString(x + width/2 - 50, y + height - 5, 100, "Im Klassikmode darf nicht hochgeladen werden! Schade? Dann spiel im Sheeptasticmodus!", true, 4500, false));
				} else {
					this.strings.add(new ApoSimpleString(x + width/2 - 50, y + height - 5, 100, "You can't upload your score in the classic mode. Play the sheeptastic mode to upload your score.", true, 4500, false));
				}
			}
			if (this.bClassic) {
				this.getGame().getButtons()[5].setBVisible(false);
			}
			return;
		}
		
		int height = (int)(this.entities[0][0].getWidth() + 5);
		for (int x = 0; x < this.entities[0].length; x++) {
			int[] count = new int[this.entities.length];
			for (int y = this.entities.length - 1; y >= 0; y--) {
				count[y] = 0;
				if (y < this.getEntities().length - 1) {
					count[y] = count[y + 1];
				} else if (!this.getEntities()[y][x].isBVisible()) {
					count[y] += 1;
				}
				int check = count[y];
				for (int myY = y - check; myY >= 0; myY--) {
					if (!this.getEntities()[myY][x].isBVisible()) {
						count[y] += 1;
					} else {
						break;
					}
				}
			}
			for (int y = this.entities.length - 1; y >= 0; y--) {
				if (count[y] != 0) {
					if (y - count[y] >= 0) {
						this.entities[y][x].setDirection(this.entities[y-count[y]][x].getDirection());
						this.entities[y][x].setBBlack(this.entities[y-count[y]][x].isBBlack());
					} else {
						this.entities[y][x].setDirection(this.getRandomDirection());
					}
				}
				this.entities[y][x].setBVisible(true);
				this.entities[y][x].setChangeY(count[y] * height);
			}
		}
		this.changeDirectionBlackSheep();
	}
	
	public void nextLevel() {
		int levelPoints = this.level * 10 + this.moves * 10;
		this.points += levelPoints;
		this.getGame().setCoins(this.getGame().getCoins() + this.level * 3, false);
		int width = 200;
		this.getStrings().add(new ApoSimpleString(this.entities[0].length * ApoSimpleConstants.ENTITY_WIDTH / 2 - width/2, this.entities.length * ApoSimpleConstants.ENTITY_WIDTH / 2 - width/2, width, "level bonus: "+String.valueOf(levelPoints), true));
		this.bLevelUp = true;
	}
	
	public void changeDirectionBlackSheep() {
		for (int x = 0; x < this.entities[0].length; x++) {
			for (int y = this.entities.length - 1; y >= 0; y--) {
				if ((this.entities[y][x].isBVisible()) &&
					(this.entities[y][x].isBBlack())) {
					int oldDirection = this.entities[y][x].getDirection();
					this.entities[y][x].setDirection(this.entities[y][x].getDirection() + 1);
					if ((this.entities[y][x].getDirection() > ApoSimpleConstants.DOUBLE_BLACK_RIGHT) && (oldDirection == ApoSimpleConstants.DOUBLE_BLACK_RIGHT)) {
						this.entities[y][x].setDirection(ApoSimpleConstants.DOUBLE_BLACK_UP);
					}
					if ((this.entities[y][x].getDirection() > ApoSimpleConstants.RIGHT) && (oldDirection == ApoSimpleConstants.RIGHT)) {
						this.entities[y][x].setDirection(ApoSimpleConstants.UP);
					}
					this.entities[y][x].setBBlack(true);
				}
			}
		}
	}
	
	public BufferedImage getiBackground() {
		return this.iBackground;
	}

	public void setiBackground(BufferedImage iBackground) {
		this.iBackground = iBackground;
	}

	public BufferedImage getiBackgroundReal() {
		return this.iBackgroundReal;
	}

	public void setiBackgroundReal(BufferedImage iBackgroundReal) {
		this.iBackgroundReal = iBackgroundReal;
	}

	public ApoNewTextfield getTextField() {
		return this.textField;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public ApoSimpleDrive getDriver() {
		return this.driver;
	}

	public void setDriver(ApoSimpleDrive driver) {
		this.driver = driver;
	}

	public Point getPosition() {
		return position;
	}

	public ApoSimplePreview getPreview() {
		return preview;
	}

	protected int getRandomDirection() {
		int direction = (int)(Math.random() * 4) + 1;
		if (Math.random() * 100 < 10) {
			direction = ApoSimpleConstants.EMPTY;
		} else if (direction > ApoSimpleConstants.RIGHT) {
			direction = ApoSimpleConstants.RIGHT;
		}
		if (!this.bClassic) {
			int random = (int)(Math.random() * 100);
			if (this.level >= 3) {
				if (random < 15) {
					direction = (int)(Math.random() * 4) + ApoSimpleConstants.BLACK_UP;
					if (direction > ApoSimpleConstants.BLACK_RIGHT) {
						direction = ApoSimpleConstants.BLACK_RIGHT;
					}
				} else if ((this.level >= 5) && (random < 25)) {
					if (random > 9) {
						direction = (int)(Math.random() * 8) + ApoSimpleConstants.DOUBLE_UP;
						if (direction > ApoSimpleConstants.DOUBLE_BLACK_RIGHT) {
							direction = ApoSimpleConstants.DOUBLE_BLACK_RIGHT;
						}
					}
				} else if ((this.level >= 7) && (random > 95)) {
					int count = this.getDogCount();
					if ((random > 95) && (count <= 2)) {
						direction = (int)(Math.random() * 4) + ApoSimpleConstants.DOG_UP;
						if (direction > ApoSimpleConstants.DOG_RIGHT) {
							direction = ApoSimpleConstants.DOG_RIGHT;
						}
					}
				} else if ((this.level >= 10) && (random > 90)) {
					int count = this.getStoneCount();
					if ((count < 2)) {
						direction = ApoSimpleConstants.STONE_2;
					}
				}
			}
		}
		return direction;
	}
	
	private int getDogCount() {
		int count = 0;
		for (int x = 0; x < this.entities[0].length; x++) {
			for (int y = this.entities.length - 1; y >= 0; y--) {
				if (this.entities[y][x] != null) {
					if ((this.entities[y][x].getDirection() >= ApoSimpleConstants.DOG_UP) &&
						(this.entities[y][x].getDirection() <= ApoSimpleConstants.DOG_RIGHT)) {
						count += 1;
					}
				}
			}
		}
		return count;
	}
	
	private int getStoneCount() {
		int count = 0;
		for (int x = 0; x < this.entities[0].length; x++) {
			for (int y = this.entities.length - 1; y >= 0; y--) {
				if (this.entities[y][x] != null) {
					if ((this.entities[y][x].getDirection() >= ApoSimpleConstants.STONE_3) &&
						(this.entities[y][x].getDirection() <= ApoSimpleConstants.STONE_1)) {
						count += 1;
					}
				}
			}
		}
		return count;
	}
	
	public int getPoints() {
		return this.points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public final ApoSimpleEntity[][] getEntities() {
		return this.entities;
	}

	public Rectangle2D.Float getRec() {
		return this.rec;
	}
	
	public int getLevelX(float checkX) {
		checkX -= 10;
		int levelWidth = (int)(ApoSimpleConstants.ENTITY_WIDTH + 5);
		return (int)(checkX / levelWidth);
	}
	
	public int getLevelY(float checkY) {
		checkY -= 10;
		int levelHeight = (int)(ApoSimpleConstants.ENTITY_WIDTH + 5);
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
		if (this.bAchievement) {
			this.achievements.think(delta);
			return;
		}
		if (this.bLevelUp) {
			this.curLevelUpTime += delta;
			if (this.curLevelUpTime >= ApoSimpleGame.LEVEL_UP_TIME) {
				this.curLevelUpTime -= ApoSimpleGame.LEVEL_UP_TIME;
				boolean bBreak = false;
				for (int y = 0; y < this.entities.length; y++) {
					for (int x = 0; x < this.entities[0].length; x++) {
						if ((this.entities[y][x].isBVisible()) && (!this.entities[y][x].isBLevelUp())) {
							bBreak = true;
							this.entities[y][x].setBLevelUp(true);
							if (this.curLevelString.length() <= 0) {
								this.curLevelString = ApoSimpleGame.LEVEL_STRING;
							}
							String s = this.curLevelString.substring(0, 1);
							this.curLevelString = this.curLevelString.substring(1);
							this.entities[y][x].setLevel(s);
							break;
						}
					}
					if (bBreak) {
						break;
					}
				}
				if (!bBreak) {
					this.makeLevel(this.level + 1);
				}
			}
		} else {
			this.bChangeY = false;
			for (int y = 0; y < this.entities.length; y++) {
				for (int x = 0; x < this.entities[0].length; x++) {
					this.entities[y][x].think(delta);
					if (this.entities[y][x].getChangeY() > 0) {
						this.bChangeY = true;
					}
				}
			}
			if (this.driver != null) {
				this.driver.think(delta, this);
			}
		}
		if (this.getGame().isBLoose()) {
			if (this.textField != null) {
				this.textField.think(delta);
			}
		}
		if (this.curTimeUpload > 0) {
			this.curTimeUpload -= delta;
			if (this.curTimeUpload <= 0) {
				this.curTimeUpload = 0;
			}
		}
		if (this.uploadTime > 0) {
			this.uploadTime -= delta;
			if (this.uploadTime <= 0) {
				this.curTimeUpload = 0;
				this.bUploadFalse = false;
			}
		}
	}
	
	public ArrayList<ApoSimpleString> getStrings() {
		return this.strings;
	}

	public void setStrings(ArrayList<ApoSimpleString> strings) {
		this.strings = strings;
	}

	public int getMoves() {
		return this.moves;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}

	public boolean isBAchievement() {
		return this.bAchievement;
	}

	public void setBAchievement(boolean achievement) {
		this.bAchievement = achievement;
	}
	
	public boolean isDogInside() {
		if (this.getEntities() != null) {
			for (int y = 0; y < this.getEntities().length; y++) {
				for (int x = 0; x < this.getEntities()[0].length; x++) {
					if (this.getEntities()[y][x].isBVisible()) {
						if ((this.getEntities()[y][x].getDirection() >= ApoSimpleConstants.DOG_UP) &&
							(this.getEntities()[y][x].getDirection() <= ApoSimpleConstants.DOG_RIGHT)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public void render(Graphics2D g) {
		if (this.bAchievement) {
			this.drawAchievement(g);
		} else if (this.getGame().isBLoose()) {
			this.drawLoose(g);
		} else {
			this.drawLevel(g);
		}
		if (this.bNotice) {
			this.notice.render(g);
		}
	}
	
	public void drawAchievement(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		this.achievements.render(g);
		return;
	}
	
	public void drawLoose(Graphics2D g) {
		if (this.iBackgroundReal != null) {
			g.drawImage(this.iBackgroundReal, 0, 0, null);
		}
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		int width = (int)(ApoSimpleConstants.ENTITY_WIDTH + 5) * this.entities[0].length;
		int height = (int)(350);
		int x = ApoSimpleConstants.GAME_WIDTH/2 - width/2;
		int y = ApoSimpleConstants.GAME_HEIGHT/2 - height/2;
		
		g.setColor(Color.BLACK);
		g.setFont(ApoSimpleGame.FONT_STATICS);
		String s = "Your score is";
		if (ApoSimpleConstants.REGION.equals("de")) {
			s = "Deine Punktzahl beträgt";
		}
		int w = g.getFontMetrics().stringWidth(s);
		int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
		
		g.setFont(ApoSimpleGame.FONT_SCORE);
		s = String.valueOf(this.points);
		w = g.getFontMetrics().stringWidth(s);
		h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
		g.drawString(s, x + width/2 - w/2, y + 115);
		
		if ((this.position != null) && (this.position.x > 0)) {
			g.setFont(ApoSimpleGame.FONT_RESTART);
			s = "Place "+String.valueOf(this.position.x)+" of "+String.valueOf(this.position.y);
			if (ApoSimpleConstants.REGION.equals("de")) {
				s = "Platz "+String.valueOf(this.position.x)+" von "+String.valueOf(this.position.y);
			}
			w = g.getFontMetrics().stringWidth(s);
			h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
			g.drawString(s, x + width/2 - w/2, y + 145);		
		}
		
		if (this.textField.isBVisible()) {
			this.textField.render(g, 0, 0);
			
			g.setFont(ApoSimpleGame.FONT_STATICS);
			s = "Name: ";
			w = g.getFontMetrics().stringWidth(s);
			h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
			g.drawString(s, (int)(this.textField.getX() - w), (int)(this.textField.getY() + this.textField.getHeight()/2 + h/2));
		}
		if (this.curTimeUpload != 0) {
			g.setFont(ApoSimpleGame.FONT_RESTART);
			if (this.curTimeUpload == -1) {
				s = "Uploading ...";
			} else {
				s = "Uploading was successful";
				if (ApoSimpleConstants.REGION.equals("de")) {
					s = "Hochladen war erfolgreich.";
				}
				if (this.bUploadFalse) {
					s = "Uploading failed";
					if (ApoSimpleConstants.REGION.equals("de")) {
						s = "Hochladen ging schief.";
					}
				}					
			}
			w = g.getFontMetrics().stringWidth(s);
			h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();

			g.setColor(Color.WHITE);
			g.fillRoundRect(x + width/2 - w/2 - 10, y + 120, w + 20, 30, 15, 15);
			g.setStroke(new BasicStroke(3));
			g.setColor(Color.BLACK);
			g.drawRoundRect(x + width/2 - w/2 - 10, y + 120, w + 20, 30, 15, 15);
			
			g.drawString(s, x + width/2 - w/2, y + 145);
		}
	}
	
	public void drawLevel(Graphics2D g) {
		if (this.iBackground != null) {
			g.drawImage(this.iBackground, 0, 0, null);
		}
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (this.entities != null) {
			for (int y = 0; y < this.entities.length; y++) {
				for (int x = 0; x < this.entities[0].length; x++) {
					if (this.entities[y][x].isBVisible()) {
						this.entities[y][x].render(g, 0, 0);
						this.renderArrow(g, x, y, 0, 0);
						if ((this.mouseInLevel.x == x) && (this.mouseInLevel.y == y)) {
							this.entities[y][x].renderNot(g, 0, 0);
						}
						if (this.bGoodie) {
							this.entities[y][x].renderGoodiePossible(g, 0, 0);
						}
					}
				}
			}
		}
		if (this.driver != null) {
			Shape shape = g.getClip();
			if (this.rec != null) {
				g.setClip(this.rec);	
			}
			this.driver.render(g, 0, 0);
			
			if (this instanceof ApoSimpleLevelGame) {
				if ((this.getDriver() != null) && (this.isDogInside())) {
					this.getDriver().renderSheepSize(g, 0, 0);
				}
			}
			g.setClip(shape);
		}
		
		this.drawLevelInfo(g);
		
		this.drawMoves(g);
		
		this.drawGoodie(g);

		this.drawPoints(g);
		
		this.drawCoins(g);
		
		this.drawBackBackground(g);
	}
	
	public void renderArrow(Graphics2D g, int x, int y, int changeX, int changeY) {
		if (this.isbHelp()) {
			this.entities[y][x].renderArrow(g, 0, 0);
		}
	}
	
	public void renderStrings(Graphics2D g, int changeX, int changeY) {
		try {
			for (int i = this.strings.size() - 1; i >= 0; i--) {
				this.strings.get(i).render(g, changeX, changeY);
				if (this.strings.get(i).getText().equals(ApoSimpleConstants.HELP_NOTICE)) {
					g.drawImage(ApoSimplePanel.iArrow, (int)(595 - ApoSimplePanel.iArrow.getWidth()), (int)(43), null);
				}
			}
		} catch (Exception ex) {	
		}
	}
	
	public static void drawString(Graphics2D g, String s, int x, int y) {
		g.setColor(Color.WHITE);
		g.drawString(s, x + 1, y + 1);
		g.setColor(Color.BLACK);
		g.drawString(s, x, y);
	}
	
	public void drawLevelInfo(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.setFont(ApoSimpleGame.FONT_RESTART);
		String s = String.valueOf(this.level);
		int w = g.getFontMetrics().stringWidth(s);
		int x = 558;
		int y = 195;
		ApoSimpleGame.drawString(g, s, x - w/2, y);
	}
	
	public void drawMoves(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.setFont(ApoSimpleGame.FONT_RESTART);
		if (this.moves > 0) {
			String s = String.valueOf(this.moves);
			int w = g.getFontMetrics().stringWidth(s);
			int x = 561;
			int y = 245;
			ApoSimpleGame.drawString(g, s, x - w/2, y);
		}
	}
	
	public void drawGoodie(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.setFont(ApoSimpleGame.FONT_COIN);
		String s = "Goodie";
		int w = g.getFontMetrics().stringWidth(s);
		int x = 565;
		int y = 270;
		g.drawString(s, x - w/2, y);
		
		s = ": " + String.valueOf(this.goodieCount);
		w = g.getFontMetrics().stringWidth(s);
		x = 595;
		y = (int)(this.getGame().getButtons()[56].getY());
		int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
		ApoSimpleGame.drawString(g, s, x - w, y + this.getGame().getButtons()[56].getIBackground().getHeight()/2 + h/2);
	}
	
	public void drawPoints(Graphics2D g) {
		int y = 147;
		g.drawImage(ApoSimpleImages.GAME_POINTS, 510, y - 40, null);
		g.setColor(Color.BLACK);
		g.setFont(ApoSimpleGame.FONT_RESTART);
		String s = String.valueOf(this.points);
		int w = g.getFontMetrics().stringWidth(s);
		int x = 565;
		ApoSimpleGame.drawString(g, s, x - w/2, y);
	}
	
	public void drawCoins(Graphics2D g) {
		int y = 354;
		g.setColor(Color.BLACK);
		g.setFont(ApoSimpleGame.FONT_COIN);
		String s = String.valueOf(this.getGame().getCoins());
		int w = g.getFontMetrics().stringWidth(s);
		int x = 625;
		int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
		ApoSimpleGame.drawString(g, s, x - w, y + ApoSimpleImages.COINS.getHeight()/2 + h/2);
	}

	public void drawTutorial(Graphics2D g, int maxY) {
		g.drawImage(ApoSimplePanel.iTutAbove, 0, 0, null);
		int max = (maxY - ApoSimplePanel.iTutAbove.getHeight()) / ApoSimplePanel.iTutMiddle.getHeight();
		for (int i = 0; i < max; i++) {
			g.drawImage(ApoSimplePanel.iTutMiddle, 0, ApoSimplePanel.iTutAbove.getHeight() + ApoSimplePanel.iTutMiddle.getHeight() * i, null);					
		}
		g.drawImage(ApoSimplePanel.iTutMiddle, 0, maxY - ApoSimplePanel.iTutMiddle.getHeight(), null);					
		
		g.drawImage(ApoSimplePanel.iTutDown, 0, maxY, null);
	}
	
	public void drawBackBackground(Graphics2D g) {
		if (this.bBackMenu) {
			int change = 30;
			g.drawImage(this.getBackgroundBack(), ApoSimpleConstants.GAME_WIDTH/2 - change - this.getBackgroundBack().getWidth()/2, ApoSimpleConstants.GAME_HEIGHT/2 - this.getBackgroundBack().getHeight()/2, null);
			
			g.setColor(Color.BLACK);
			g.setFont(ApoSimpleGame.FONT_RESTART);
			String s = String.valueOf("back to menu?");
			if (ApoSimpleConstants.REGION.equals("de")) {
				s = String.valueOf("zurück zum menu?");
			}
			int w = g.getFontMetrics().stringWidth(s);
			int x = ApoSimpleConstants.GAME_WIDTH/2 - change;
			int y = ApoSimpleConstants.GAME_HEIGHT/2 - this.getBackgroundBack().getHeight()/2 + 50;
			ApoSimpleGame.drawString(g, s, x - w/2, y);
		}
	}
	
	public void drawOverlay(Graphics2D g) {
		this.renderStrings(g, 0, 0);
		
		this.drawGoodieOver(g);
		this.drawMusicOver(g);
		this.drawEffectOver(g);
		this.drawNoticeOver(g);
		this.drawReloadOver(g);
		this.drawHelpOver(g);
		
		this.drawCoinOver(g);
	}
	
	public int getCost() {
		return (this.level + 1) * 10;
	}
	
	public void drawGoodieOver(Graphics2D g) {
		this.getGame().renderOver(g, ApoSimpleConstants.OVER_GOODIE, 56);
	}
	
	public void drawCoinOver(Graphics2D g) {
		String[] over = new String[ApoSimpleConstants.OVER_COIN_ENDLESS.length];
		for (int i = 0; i < over.length; i++) {
			over[i] = ApoSimpleConstants.OVER_COIN_ENDLESS[i];
			if (i == 1) {
				over[i] += String.valueOf(this.getCost());
			}
		}
		this.getGame().renderOver(g, over, 57);
	}
	
	public void drawMusicOver(Graphics2D g) {
		this.getGame().renderOver(g, ApoSimpleConstants.OVER_MUSIC, 53);
	}
	
	public void drawEffectOver(Graphics2D g) {
		this.getGame().renderOver(g, ApoSimpleConstants.OVER_EFFECTS, 54);
	}
	
	public void drawNoticeOver(Graphics2D g) {
		this.getGame().renderOver(g, ApoSimpleConstants.OVER_NOTICE, 39);
	}
	
	public void drawReloadOver(Graphics2D g) {
		this.getGame().renderOver(g, ApoSimpleConstants.OVER_RELOAD, 40);
	}

	public void drawHelpOver(Graphics2D g) {
		this.getGame().renderOver(g, ApoSimpleConstants.OVER_HELP, 55);
	}
	
}
