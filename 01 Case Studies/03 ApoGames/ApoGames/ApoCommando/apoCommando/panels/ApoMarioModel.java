package apoCommando.panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import apoCommando.ApoMarioConstants;
import apoCommando.game.ApoMarioPanel;
import apoCommando.level.ApoMarioAchievements;
import apoCommando.level.ApoMarioMyLevels;
import apoCommando.level.ApoMarioStats;

public abstract class ApoMarioModel {
	
	public static final int CHANGE_TIME = 500;
	
	private final int MAX_ACHIEVMENTS = 15;
	
	private ArrayList<String> lastCommands;
	
	private int value = -1;
	
	private ApoMarioPanel game;
	
	protected BufferedImage iDosBox, iDosStats;
	
	protected boolean bCursor;
	
	protected boolean bStats;
	
	protected boolean bAchievements;
	
	protected boolean bLevel;
	
	protected String curString;
	
	protected int curTime;
	
	protected String extraString;
	
	private int curAchievement;
	
	public ApoMarioModel(ApoMarioPanel game) {
		this.game = game;
	}
	
	public void init() {
		this.iDosBox = this.getBackgroundImageWithRect();
		if (this.iDosStats == null) {
			this.iDosStats = this.getStatsImage();
		}
		if (this.lastCommands == null) {
			this.lastCommands = new ArrayList<String>();
		}
		this.curString = "";
		this.extraString = "";
		this.bStats = false;
		this.bAchievements = false;
		this.bLevel = false;
		this.curAchievement = 0;
	}
	
	public final int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public final ArrayList<String> getLastCommands() {
		return this.lastCommands;
	}

	public void addCommand(String curString) {
		this.lastCommands.add(curString);
		this.value = this.lastCommands.size() - 1;
		this.extraString = "";
	}
	
	public abstract BufferedImage getBackgroundImageWithRect();
	
	public BufferedImage getStatsImage() {
		int width = ApoMarioConstants.GAME_WIDTH - 25 * ApoMarioConstants.SIZE;
		int height = ApoMarioConstants.GAME_HEIGHT - 30 * ApoMarioConstants.SIZE;
		
		BufferedImage iStats = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = (Graphics2D)iStats.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(new Color(196, 215, 234));
		g.fillRoundRect(0, 0, width - 1, height - 1, 15, 15);			
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(2));
		g.drawRoundRect(0, 0, width - 1, height - 1, 15, 15);			
		
		int x = 10;
		int y = 20;
		g.fillRect(x, y, width - 20, height - 25);
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		g.setFont(ApoMarioConstants.FONT_DOS_BOX);
		int addY = 15;
		g.setColor(Color.WHITE);
		g.drawString("ApoCommando (version "+ApoMarioConstants.VERSION+")", x + 5, y + 20);
		g.drawString("Copyright (c) 2011 Apo-Games. All Rights reserved.", x + 5, y + 20 + 1 * addY);
		
		g.dispose();
		return iStats;
	}
	
	public ApoMarioPanel getGame() {
		return this.game;
	}

	public void keyButtonReleased(int button, char character) {
		if (this.bAchievements) {
			if (button == KeyEvent.VK_UP) {
				if (this.curAchievement > 0) {
					this.curAchievement -= 1;
				}
			} else if (button == KeyEvent.VK_DOWN) {
				if (this.getGame().getAchievements().getAchievements().size() >= this.MAX_ACHIEVMENTS) {
					if (this.curAchievement + this.MAX_ACHIEVMENTS < this.getGame().getAchievements().getAchievements().size()) {
						this.curAchievement += 1;
					}
				}
			}
		}
	}
	
	public abstract void mouseButtonFunction(String function);
	
	public abstract void mouseButtonReleased(int x, int y);
	
	public abstract boolean mouseMoved(int x, int y);
	
	public abstract boolean mouseDragged(int x, int y);
	
	public abstract boolean mousePressed(int x, int y);	
	
	public void checkString() {
		if (this.curString.length() > 0) {
			this.addCommand(this.curString);
			boolean bNew = true;
			for (int i = 0; i < this.getGame().getAllCommands().size(); i++) {
				if (this.curString.equals(this.getGame().getAllCommands().get(i).getCurString())) {
					bNew = false;
					this.getGame().getAllCommands().get(i).addCount();
					break;
				}
			}
			if (bNew) {
				this.getGame().getAllCommands().add(new ApoMarioStats(this.curString));
			}
			boolean bBreak = false;
			if (this.bAchievements) {
				if ((this.curString.equals("back")) ||
					(this.curString.equals("exit"))) {
					this.bStats = false;
					this.bAchievements = false;
					bBreak = true;
				} else if (this.curString.equals("achievements")) {
					this.extraString = "Hello ... look where you are";
					bBreak = true;
				}
			} else if (this.bLevel) {
				if (this.curString.equals("back")) {
					this.bStats = false;
					this.bLevel = false;
					this.bAchievements = false;
					bBreak = true;
				} else if (this.curString.indexOf("levelcode") >= 0) {
					this.extraString = "Hello ... look where you are";
					bBreak = true;
				}
			} else if (this.bStats) {
				if (this.curString.equals("back")) {
					this.bStats = false;
					this.getGame().getLevel().setBStop(false);
					bBreak = true;
				} else if (this.curString.equals("stats")) {
					this.extraString = "Hello ... you are in the stats ...";
					bBreak = true;
				}
			}
			
			if (!bBreak) {
				if (this.curString.equals("stats")) {
					this.getGame().getAchievements().addHiddenWord(this.curString);
					this.getGame().getAchievements().addAchievement(ApoMarioAchievements.ACHIEVEMENT_STATS);
					this.bStats = true;
					this.bAchievements = false;
					this.getGame().getLevel().setBStop(true);
				}
			}
			this.checkCurString();
			this.curString = "";
		}
	}
	
	public boolean checkAchievements(String curString) {
		curString = curString.toLowerCase();
		if (curString.length() <= 0) {
			return false;
		}
		if (curString.startsWith("achievement")) {
			this.curAchievement = 0;
			this.getGame().getAchievements().addHiddenWord(this.curString);
			this.getGame().getAchievements().addAchievement(ApoMarioAchievements.ACHIEVEMENT_ACHIEVEMENT);
			this.bStats = false;
			this.bAchievements = true;
			this.bLevel = false;
			return true;
		}
		return false;
	}
	
	public boolean checkLevelCode(String curString) {
		curString = curString.toLowerCase();
		if (curString.length() <= 0) {
			return false;
		}
		if (curString.startsWith("levelcode")) {
			this.getGame().getAchievements().addHiddenWord(this.curString);
			this.getGame().getAchievements().addAchievement(ApoMarioAchievements.ACHIEVEMENT_LEVELCODE);
			this.bStats = false;
			this.bAchievements = false;
			this.bLevel = true;
			return true;
		}
		return false;
	}
	
	public boolean checkLevelLoad(String curString) {
		curString = curString.toLowerCase();
		if (curString.length() <= 0) {
			return false;
		}
		for (int i = 0; i < ApoMarioMyLevels.LEVEL_STRINGS.length; i++) {
			if (curString.equals(ApoMarioMyLevels.LEVEL_STRINGS[i])) {
				this.getGame().newLevel(-1, -1, curString);
				this.getGame().setGame(false);
				return true;
			}
		}
		if ((curString.equals("mandy")) || (curString.equals("antje")) || 
			(curString.equals("clemens")) || (curString.equals("jessika"))) {
			this.getGame().newLevel(-1, -1, curString);
			this.getGame().setGame(false);
		}
		if (curString.equals("tutorial")) {
			this.getGame().newLevel(-1, -1, curString);
			this.getGame().setGame(true);
		}

		return false;
	}
	
	public abstract void checkCurString();
	
	public boolean checkDifficulty(String curString) {
		curString = curString.toLowerCase();
		if (curString.length() <= 0) {
			return false;
		}
		if (curString.startsWith("dif ")) {
			String number = curString.substring(4);
			try {
				int x = Integer.parseInt(number);
				if ((x > 4) || (x < 1)) {
					return false;
				}
				ApoMarioConstants.DIFFICULTY = x;
				this.getGame().newLevel(50 * (ApoMarioConstants.DIFFICULTY - 1) + 50, (ApoMarioConstants.DIFFICULTY - 1) * 50, null);
				this.iDosBox = this.getBackgroundImageWithRect();
				return true;
			} catch (NumberFormatException ex) {
			}
		}
		return false;
	}
	
	public boolean checkExtra(String curString) {
		curString = curString.toLowerCase();
		if (curString.length() <= 0) {
			return false;
		}
		boolean bNew = true;
		if (curString.equals("i love you")) {
			this.extraString = "I love you too sweetheart!";
		} else if (curString.equals("love")) {
			this.extraString = "LOVE is everywhere";
		} else if (curString.equals("up")) {
			this.extraString = "Up and down and up and I fly fly like a flyer";
		} else if (curString.equals("fcm")) {
			this.extraString = "Best soccer team ever!";
		} else if (curString.equals("hfc")) {
			this.extraString = "Wir sind eure Hauptstadt, ihr Bauern!";
		} else if (curString.equals("soccer")) {
			this.extraString = "Best waste of time";
		} else if (curString.equals("simpsons")) {
			this.extraString = "D'oh!";
		} else if ((curString.equals("sex")) ||
				   (curString.equals("porn"))) {
			this.extraString = "Not in that game, go to xhamster.com";
		} else if (curString.equals("down")) {
			this.extraString = "You mean the syndrome?";
		} else if (curString.equals("sie war 44")) {
			this.extraString = "und ich war 13 1/2";
		} else if (curString.equals("vancouver")) {
			this.extraString = "I loveeeeeeee that city!";
		} else if (curString.indexOf("fuck") >= 0) {
			bNew = false;
			this.extraString = "Oh sorry, but I am only a game. Use a banana peel.";
		} else if (curString.equals("tom")) {
			this.extraString = "I see chocolate apples ...";
		} else if (curString.equals("vancouver")) {
			this.extraString = "Lovely town";
		} else if (curString.equals("herzi")) {
			this.extraString = "My herzi belongs to me";
		} else if (curString.equals("wedding")) {
			this.extraString = "Need I say more then 'Al Bundy'?";
		} else if (curString.equals("test")) {
			this.extraString = "010101110! Yes everything works fine.";
		} else if (curString.equals("faecbook")) {
			this.extraString = "Facebook is watching you.";
		} else if (curString.equals("twitter")) {
			this.extraString = "I think twitter ist useless!";
		} else if (curString.equals("iphone")) {
			this.extraString = "Nice design but too expensive";
		} else if (curString.equals("ipod")) {
			this.extraString = "Nice to play and hear music";
		} else if (curString.equals("apple")) {
			this.extraString = "Apples next product: iMer";
		} else if (curString.equals("windows")) {
			this.extraString = "I see blue screens!";
		} else if (curString.equals("dos")) {
			this.extraString = "Very old but has a nice charme like this game!";
		} else if (curString.equals("eclipse")) {
			this.extraString = "What do you mean? Song or Open Source IDE?";
		} else if (curString.equals("c#")) {
			this.extraString = "Yes I see sharp!";
		} else if (curString.equals("wine")) {
			this.extraString = "Red wine is so nice";
		} else if (curString.equals("beer")) {
			this.extraString = "Only german beer is good beer";
		} else if ( (curString.equals("witz")) ||
					(curString.equals("joke"))) {
			double random = Math.random() * 100;
			if (random < 25) {
				this.extraString = "Was ist grün und trägt ein Kopftuch? Eine Gürkin";
			} else if (random < 50) {
				this.extraString = "Was ist rot und steht am Straßenrand? Eine Hagenutte!";
			} else if (random < 75) {
				this.extraString = "Trockenpflaume mit 3 Buchstaben? OMA";
			} else {
				this.extraString = "Was ist ein 30jähriger Palästinenser? Ein Spätzünder.";
			}
		} else if (curString.equals("java")) {
			this.extraString = "JAVA rocksss!";
		} else if (curString.equals("duke nukem")) {
			this.extraString = "Hail to the king baby!";
		} else if (curString.equals("id software")) {
			this.extraString = "When its done!";
		} else if (curString.equals("magdeburg")) {
			this.extraString = "I like ... no I love this city";
		} else if (curString.equals("halle")) {
			this.extraString = "you mean merseburg nord?";
		} else if (curString.equals("single")) {
			this.extraString = "no woman no cry ...";
		} else if (curString.equals("woman")) {
			this.extraString = "Woman? The greatest gift on earth";
		} else if (curString.equals("man")) {
			this.extraString = "A man? That hairy bastards?";
		} else if (curString.equals("music")) {
			this.extraString = "Oh yeah, music is like sex";
		} else if (curString.equals("music")) {
			this.extraString = "Oh yeah, music is like sex";
		} else if (curString.equals("apogod")) {
			this.extraString = "I know ... apo is a god.";
		} else if (curString.equals("cowlevel")) {
			this.extraString = "You know ... there is no cow level ... or?";
		} else if (curString.equals("apocommando")) {
			this.extraString = "Yes, the game rules";
		} else if (curString.equals("haha")) {
			this.extraString = "Why are you laughing?";
		} else if (curString.equals("lol")) {
			this.extraString = "You are laughing out loud? Thats good.";
		} else if (curString.equals("rofl")) {
			this.extraString = "You roll on your floor laughing? Thats crazy.";
		} else if (curString.equals("mandy")) {
			this.extraString = "Mein Kugelbierfischchen :-* :D";
		} else if (curString.equals("jessica")) {
			this.extraString = "Das wird aber mit 'k' geschrieben *hust*";
		} else if (curString.equals("clemens")) {
			this.extraString = "Oh yeah prince lumberjackcharme";
		} else if (curString.equals("basti")) {
			this.extraString = "The husband of clemens";
		} else if (curString.equals("topfi")) {
			this.extraString = "The best slave ähh husband I know";
		} else if (curString.equals("peter")) {
			this.extraString = "Mister 'Every week injured or get a parking ticket'";
		} else if (curString.equals("tobias")) {
			this.extraString = "Mister 'Youtube' and writing 1 sentences per day";
		} else if (curString.equals("annoying orange")) {
			this.extraString = "Hey apple, hey apple, hey apo, you look fruity";
		} else if (curString.equals("anna")) {
			this.extraString = "interesting mix between lovley, moody and female";
		} else if (curString.equals("anne")) {
			this.extraString = "*no words*";
		} else if (curString.equals("rob")) {
			this.extraString = "Mister flat jokes";
		} else if (curString.equals("katrin")) {
			this.extraString = "*no words*";
		} else if (curString.equals("jani")) {
			this.extraString = "princess lumberjackcharme";
		} else if (curString.equals("dorle")) {
			this.extraString = "my lovley sister";
		} else if (curString.equals("minecraft")) {
			this.extraString = "Damn great game ...";
		} else if ((curString.equals("apo-games")) ||
				   (curString.equals("apogames"))) {
			this.extraString = "More than games";
		} else if ((curString.indexOf("fuck") >= 0) ||
				(curString.indexOf("damn") >= 0) ||
				(curString.indexOf("bastard") >= 0)) {
			bNew = false;
			this.extraString = "Please dont use such words";
		} else if (curString.equals("i love apo")) {
			this.extraString = "You are so nice. He rules! Thats right";
			this.getGame().getAchievements().addAchievement(ApoMarioAchievements.ACHIEVEMENT_APO_LOVE);
		} else if (curString.equals("apo")) {
			this.extraString = "He is so cute!";
		} else if (curString.equals("java-forum.org")) {
			this.extraString = "Very nice forum!";
		} else if (curString.equals("http://forums.tigsource.com")) {
			this.extraString = "Very nice forum!";
		} else if (curString.equals("fps")) {
			ApoMarioConstants.FPS = !ApoMarioConstants.FPS;
		} else if ((curString.equals("jump")) || (curString.equals("high")) || (curString.equals("duck")) || (curString.equals("fast")) ||
				(curString.equals("slow")) || (curString.equals("fire")) || (curString.equals("flip"))) {
			bNew = false;
			this.extraString = this.curString + " is an ingame command!";
		} else {
			bNew = false;
			this.extraString = "";
		}
		if (bNew) {
			this.getGame().getAchievements().addHiddenWord(curString);
		}
		return false;
	}
	
	public void think(int delta) {
		this.curTime -= delta;
		if (this.curTime < 0) {
			this.curTime = ApoMarioModel.CHANGE_TIME;
			this.bCursor = !this.bCursor;
		}
	}
	
	public abstract void render(Graphics2D g);
	
	public void renderExtraString(Graphics2D g, int x, int y) {
		if (this.extraString.length() > 0) {
			g.drawString("C:\\Extra>" + this.extraString, x, y);			
		}
	}
	
	public void renderStats(Graphics2D g) {
		if (this.bStats) {
			g.drawImage(this.iDosStats, ApoMarioConstants.GAME_WIDTH/2 - this.iDosStats.getWidth()/2, ApoMarioConstants.GAME_HEIGHT/2 - this.iDosStats.getHeight()/2, null);
			int width = this.iDosStats.getWidth();
			int height = this.iDosStats.getHeight();
			int x = (ApoMarioConstants.GAME_WIDTH - width)/2 + 10;
			int y = (ApoMarioConstants.GAME_HEIGHT - height)/2 + 20;
			g.setFont(ApoMarioConstants.FONT_DOS_BOX);
			int addY = 15;
			g.setColor(Color.WHITE);
			int w = g.getFontMetrics().stringWidth("w");
			g.drawString("Command", x + 5, y + 20 + 3 * addY);
			g.drawString("=", x + 15 + w * 15, y + 20 + 3 * addY);
			g.drawLine(x + 5, y + 25 + 3 * addY, x + width - 10, y + 25 + 3 * addY);
			for (int i = 0; i < this.getGame().getAllCommands().size(); i++) {
				String command = this.getGame().getAllCommands().get(i).getCurString();
				int count = this.getGame().getAllCommands().get(i).getCount();
				int widthCount = g.getFontMetrics().stringWidth(count+"");
				if (i < 15) {
					g.drawString(command, x + 5, y + 20 + (5 + i) * addY);
					g.drawString(""+count, x + 15 + w * 16 - widthCount, y + 20 + (5 + i) * addY);
				} else if (i < 30) {
					int newX = x + 10 + w * 17;
					if (i == 15) {
						g.drawLine(x + 18 + w * 16, y + 20 + 4 * addY, x + 18 + w * 16, y + 20 + 19 * addY);
					}
					g.drawString(command, newX + 5, y + 20 + (5 + i - 15) * addY);
					g.drawString(""+count, newX + 15 + w * 16 - widthCount, y + 20 + (5 + i - 15) * addY);
				} else if (i < 45) {
					int newX = x + 20 + w * 34;
					if (i == 30) {
						g.drawLine(x + 28 + w * 33, y + 20 + 4 * addY, x + 28 + w * 33, y + 20 + 19 * addY);
					}
					g.drawString(command, newX + 5, y + 20 + (5 + i - 30) * addY);
					g.drawString(""+count, newX + 15 + w * 16 - widthCount, y + 20 + (5 + i - 30) * addY);
				}
			}

			int myX = x + g.getFontMetrics().stringWidth("C:\\Stats>");
			if (this.bCursor) {
				g.drawString(this.curString+"_", myX + 5, y + 20 + 22 * addY);
			} else {
				g.drawString(this.curString, myX + 5, y + 20 + 22 * addY);
			}
			this.renderExtraString(g, x + 5, y + 20 + 24 * addY);
			
			g.drawString("C:\\Stats>Type 'back' to close the stats", x + 5, y + 20 + 21 * addY);
			g.drawString("C:\\Stats>", x + 5, y + 20 + 22 * addY);
		}
	}
	
	public void renderAchievements(Graphics2D g) {
		if (this.bAchievements) {
			g.drawImage(this.iDosStats, ApoMarioConstants.GAME_WIDTH/2 - this.iDosStats.getWidth()/2, ApoMarioConstants.GAME_HEIGHT/2 - this.iDosStats.getHeight()/2, null);
			int width = this.iDosStats.getWidth();
			int height = this.iDosStats.getHeight();
			int x = (ApoMarioConstants.GAME_WIDTH - width)/2 + 10;
			int y = (ApoMarioConstants.GAME_HEIGHT - height)/2 + 20;
			g.setFont(ApoMarioConstants.FONT_DOS_BOX);
			int addY = 15;
			g.setColor(Color.WHITE);
			g.drawString("C:\\Achievements>load ... ("+this.getGame().getAchievements().getAchievements().size()+" / "+ApoMarioAchievements.ACHIEVEMENT_MAX+") achievements", x + 5, y + 20 + 3 * addY);
			for (int i = this.curAchievement; i < this.getGame().getAchievements().getAchievements().size() && i < this.curAchievement + this.MAX_ACHIEVMENTS; i++) {
				g.drawString("C:\\Achievements>" + this.getGame().getAchievements().getAchievements().get(i), x + 5, y + 20 + (4+i-this.curAchievement) * addY);
			}
			if (this.getGame().getAchievements().getAchievements().size() > this.MAX_ACHIEVMENTS) {
				g.drawString("C:\\Achievements>Press up or down key to scroll", x + 5, y + 20 + (21) * addY);
			}
			
			int myX = x + g.getFontMetrics().stringWidth("C:\\Achievements>");
			if (this.bCursor) {
				g.drawString(this.curString+"_", myX + 5, y + 20 + 24 * addY);
			} else {
				g.drawString(this.curString, myX + 5, y + 20 + 24 * addY);
			}
			g.drawString("C:\\Achievements>Type 'back' to close the achievements", x + 5, y + 20 + 23 * addY);
			g.drawString("C:\\Achievements>", x + 5, y + 20 + 24 * addY);
		}
	}
	
	public void renderLevelcodes(Graphics2D g) {
		if (this.bLevel) {
			g.drawImage(this.iDosStats, ApoMarioConstants.GAME_WIDTH/2 - this.iDosStats.getWidth()/2, ApoMarioConstants.GAME_HEIGHT/2 - this.iDosStats.getHeight()/2, null);
			int width = this.iDosStats.getWidth();
			int height = this.iDosStats.getHeight();
			int x = (ApoMarioConstants.GAME_WIDTH - width)/2 + 10;
			int y = (ApoMarioConstants.GAME_HEIGHT - height)/2 + 20;
			g.setFont(ApoMarioConstants.FONT_DOS_BOX);
			int addY = 15;
			g.setColor(Color.WHITE);
			g.drawString("C:\\Levelcodes>load ...", x + 5, y + 20 + 3 * addY);
			for (int i = 0; i < ApoMarioMyLevels.LEVEL_STRINGS.length; i++) {
				g.drawString("C:\\Levelcodes>Level "+(i+1)+" : " + ApoMarioMyLevels.LEVEL_STRINGS[i], x + 5, y + 20 + (4+i) * addY);
			}
			
			int myX = x + g.getFontMetrics().stringWidth("C:\\Levelcodes>");
			if (this.bCursor) {
				g.drawString(this.curString+"_", myX + 5, y + 20 + 24 * addY);
			} else {
				g.drawString(this.curString, myX + 5, y + 20 + 24 * addY);
			}
			g.drawString("C:\\Levelcodes>Type 'back' to close the levelcodes", x + 5, y + 20 + 23 * addY);
			g.drawString("C:\\Levelcodes>", x + 5, y + 20 + 24 * addY);
		}
	}
	
}
