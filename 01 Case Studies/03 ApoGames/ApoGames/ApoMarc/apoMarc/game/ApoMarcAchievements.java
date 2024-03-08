package apoMarc.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import apoMarc.ApoMarcConstants;

public class ApoMarcAchievements extends ApoMarcModel {

	private final Font FONT = new Font(Font.SANS_SERIF, Font.BOLD, 17);
	
	public static final String ACHIEVEMENT_APO = "You like Apo-Games <3";
	public static final String ACHIEVEMENT_WALLS = "All walls have the same color";
	public static final String ACHIEVEMENT_WIN_MARC = "Win against the Marc Paddle";
	public static final String ACHIEVEMENT_WIN_MANDY = "Win against the Mandy Paddle";
	public static final String ACHIEVEMENT_WIN_APO = "Win against the sweet Apo";
	public static final String ACHIEVEMENT_WIN_UNBEATABLE = "Win against the Godlike Paddle";
	public static final String ACHIEVEMENT_WIN_ZERO = "Win with "+ApoMarcConstants.WINNING_POINTS+" : 0 against the AI";
	
	public static final String MENU_STRING = "menu";
	public static final String MENU = "achievements_menu";
	
	private BufferedImage iAchievements;
	
	private ArrayList<String> achievements;
	
	public ApoMarcAchievements(ApoMarcPanel game) {
		super(game);
		
		this.init();
	}

	public void init() {
		if (this.iAchievements == null) {
			BufferedImage iAchievement = this.getGame().getImages().getImage("images/background_achievements.png", false);
			this.iAchievements = new BufferedImage(iAchievement.getWidth(), iAchievement.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
			Graphics2D g = this.iAchievements.createGraphics();
			g.drawImage(iAchievement, 0, 0, null);
			g.dispose();
		}
		if (this.achievements == null) {
			this.achievements = new ArrayList<String>();
		}
	}
	
	public boolean addAchievements(String achievement) {
		if (this.achievements.indexOf(achievement) < 0) {
			this.achievements.add(achievement);
			return true;
		}
		return false;
	}
	
	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			this.getGame().setMenu();
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoMarcAchievements.MENU)) {
			this.getGame().setMenu();
		}
	}
	
	@Override
	public void mouseButtonReleased(int x, int y) {
	}

	@Override
	public void think(long delta) {
		
	}
	
	@Override
	public void render(Graphics2D g) {
		this.getGame().renderBackground(g);
		
		if (this.iAchievements != null) {
			int x = ApoMarcConstants.GAME_WIDTH/2 - this.iAchievements.getWidth()/2;
			int y = 100;
			g.drawImage(this.iAchievements, x, y, null); 
		}
		
		g.setFont(this.FONT);
		g.setColor(Color.WHITE);
		int h = g.getFontMetrics().getHeight();
		for (int i = 0; i < this.achievements.size(); i++) {
			String s = (i+1) + " : " + this.achievements.get(i);
			g.drawString(s, 20, 175 + i * h);
		}
		String achievement = ApoMarcAchievements.ACHIEVEMENT_APO;
		if (this.achievements.indexOf(achievement) >= 0) {
			this.drawApoAchievement(g);
		}
	}
	
	private void drawApoAchievement(Graphics2D g) {
		
	}

}
