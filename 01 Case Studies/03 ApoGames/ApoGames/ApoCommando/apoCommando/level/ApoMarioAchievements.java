package apoCommando.level;

import java.util.ArrayList;

import apoCommando.game.ApoMarioPanel;

public class ApoMarioAchievements {

	public static final String ACHIEVEMENT_EASY_GAME_START = "Start an easy level";
	public static final String ACHIEVEMENT_EASY_GAME_SOLVED = "Solve an easy level";
	public static final String ACHIEVEMENT_MEDIUM_GAME_START = "Start a medium level";
	public static final String ACHIEVEMENT_MEDIUM_GAME_SOLVED = "Solve a medium level";
	public static final String ACHIEVEMENT_HARD_GAME_START = "Start a hard level";
	public static final String ACHIEVEMENT_HARD_GAME_SOLVED = "Solve a hard level";
	public static final String ACHIEVEMENT_CHEATER_GODMODE = "Switch the godmode on";
	public static final String ACHIEVEMENT_POINTS_MORE_5000 = "Get more than 5000 points in a game";
	public static final String ACHIEVEMENT_POINTS_MORE_10000 = "Get more than 10000 points in a game";
	public static final String ACHIEVEMENT_POINTS_MORE_50000 = "Get more than 50000 points in a game";
	public static final String ACHIEVEMENT_WORDS_MORE_10 = "Find more than 10 hidden words";
	public static final String ACHIEVEMENT_APO_LOVE = "Say 'I love Apo'";
	public static final String ACHIEVEMENT_ACHIEVEMENT = "Find the achievement site";
	public static final String ACHIEVEMENT_STATS = "Find the stats site";
	public static final String ACHIEVEMENT_LEVELCODE = "Find the levelcode site";
	public static final String ACHIEVEMENT_JUMPER = "Jump more than 100 times";
	public static final String ACHIEVEMENT_DUCK = "Duck more than 100 times";
	public static final String ACHIEVEMENT_TIME = "Play more than 1 hour";
	public static final String ACHIEVEMENT_FIRE = "Find the 'fire' command";
	public static final String ACHIEVEMENT_FAST = "Find the 'fast' command";
	public static final String ACHIEVEMENT_SLOW = "Find the 'slow' command";
	public static final String ACHIEVEMENT_STOP = "Find the 'stop' command";
	public static final String ACHIEVEMENT_STEP = "Find the 'step' command";
	public static final String ACHIEVEMENT_MANDY = "Find the hidden 'mandy' level";
	public static final String ACHIEVEMENT_ANTJE = "Find the hidden 'antje' level";
	public static final String ACHIEVEMENT_CLEMENS = "Find the hidden 'clemens' level";
	public static final String ACHIEVEMENT_JESSIKA = "Find the hidden 'jessika' level";
	public static final String ACHIEVEMENT_GO = "Find the 'go'command";
	public static final String ACHIEVEMENT_BAR = "Hit the finish bar";
	public static final String ACHIEVEMENT_COIN_MANDY = "Collect all coins in the 'mandy' level";
	public static final String ACHIEVEMENT_COIN_MAZE = "Collect all coins in the 'maze' level";
	public static final String ACHIEVEMENT_COIN_LEVEL = "Collect all coins in the one level";
	public static final String ACHIEVEMENT_DARK_LEVEL = "Solve a dark level";
	public static final String ACHIEVEMENT_TUTORIAL = "Start the tutorial";
	
	public static final int ACHIEVEMENT_MAX = 34;
	
	private ArrayList<String> achievements;
	
	private ApoMarioPanel panel;
	
	private int jumps;
	
	private int duck;
	
	private int time;
	
	private ArrayList<String> hiddenWords;
	
	public ApoMarioAchievements(ApoMarioPanel panel) {
		this.achievements = new ArrayList<String>();
		this.panel = panel;
		
		this.jumps = 0;
		this.duck = 0;
		this.time = 0;
		this.hiddenWords = new ArrayList<String>();
	}

	public ArrayList<String> getAchievements() {
		return this.achievements;
	}
	
	public void addJump() {
		this.jumps += 1;
		if (this.jumps == 100) {
			this.addAchievement(ApoMarioAchievements.ACHIEVEMENT_JUMPER);
		}
	}
	
	public void addDuck() {
		this.duck += 1;
		if (this.duck == 100) {
			this.addAchievement(ApoMarioAchievements.ACHIEVEMENT_DUCK);
		}
	}
	
	public void addTime(int delta) {
		this.time += delta;
		if ((this.time >= 3600000) && (this.time < 3600100)) {
			this.addAchievement(ApoMarioAchievements.ACHIEVEMENT_TIME);
		}
	}
	
	public void addHiddenWord(String word) {
		if (this.hiddenWords.indexOf(word) < 0) {
			this.hiddenWords.add(word);
			if (this.hiddenWords.size() == 10) {
				this.addAchievement(ApoMarioAchievements.ACHIEVEMENT_WORDS_MORE_10);
			}
		}
	}
	
	public void addAchievement(String achievement) {
		if (this.achievements.indexOf(achievement) < 0) {
			this.achievements.add(achievement);
			this.panel.newAchievement();
		}
	}
}
