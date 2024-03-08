package apoJump.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apogames.ApoConstants;
import org.apogames.help.ApoHelp;

import apoJump.ApoJumpConstants;
import apoJump.ApoJumpImageContainer;

public class ApoJumpStateAchievements extends ApoJumpState {

	public static final String BUTTON_BACK = "back";
	
	public static final String ACHIEVEMENT_75000_POINTS = "Get more than 75000 points in one game";
	public static final String ACHIEVEMENT_ENEMY_KILLER = "Kill 30 enemies in one game";
	public static final String ACHIEVEMENT_JUMPER = "Jump more than 100 times in one game";
	public static final String ACHIEVEMENT_REAL_JUMPER = "Jump more than 500 times in one game";
	public static final String ACHIEVEMENT_TIMER_3 = "Survive in one game more than 3 minutes";
	public static final String ACHIEVEMENT_TIMER_15 = "Survive in one game more than 15 minutes";
	public static final String ACHIEVEMENT_TOP = "Reach the top of the highscore";
	public static final String ACHIEVEMENT_WINGS = "Catch more than 5 wings in one game";
	
	public static final String[] ACHIEVEMENTS_ARRAY = new String[] {
		ACHIEVEMENT_75000_POINTS,
		ACHIEVEMENT_ENEMY_KILLER,
		ACHIEVEMENT_JUMPER,
		ACHIEVEMENT_REAL_JUMPER,
		ACHIEVEMENT_TIMER_3,
		ACHIEVEMENT_TIMER_15,
		ACHIEVEMENT_TOP,
		ACHIEVEMENT_WINGS,
	};
	
	private ArrayList<String> achievements;
	
	private BufferedImage iBackground;
	 
	public ApoJumpStateAchievements(ApoJumpPanel game) {
		super(game);
		if (this.achievements == null) {
			this.achievements = new ArrayList<String>();
		}
	}
	
	public void init() {
		this.getGame().setShouldRepaint(false);
		if (this.iBackground == null) {
			this.makeBackground();
		}
	}
	
	public void loadAchievements() {
		if (ApoConstants.B_APPLET) {
			String achievements = null;
			try {
				achievements = ApoHelp.loadData(new URL(ApoJumpConstants.PROGRAM_URL), ApoJumpConstants.COOKIE_NAME);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			while ((achievements != null) && (achievements.length() > 0)) {
				try {
					String curString = achievements.substring(0, 1);
					achievements = achievements.substring(1);
					int value = Integer.valueOf(curString);
					if (value < ACHIEVEMENTS_ARRAY.length) {
						this.addAchievements(ACHIEVEMENTS_ARRAY[value], false);
					}
				} catch (NumberFormatException ex) {
					break;
				} catch (Exception ex) {
					break;
				}
			}
		}
	}
	
	public void saveAchievements() {
		if (ApoConstants.B_APPLET) {
			String cookie = "";
			for (int i = 0; i < ApoJumpStateAchievements.ACHIEVEMENTS_ARRAY.length; i++) {
				if (this.achievements.indexOf(ApoJumpStateAchievements.ACHIEVEMENTS_ARRAY[i]) >= 0) {
					cookie += String.valueOf(i);
				}
			}
			try {
				ApoHelp.saveData(new URL(ApoJumpConstants.PROGRAM_URL), ApoJumpConstants.COOKIE_NAME, cookie);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void makeBackground() {
		this.iBackground = new BufferedImage(ApoJumpConstants.GAME_WIDTH, ApoJumpConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = this.iBackground.createGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		this.getGame().renderBackground(g);
		
		g.drawImage(this.getGame().getImages().getImage("images/achievements.png", false), 70, 0, null);

		g.dispose();
	}
	
	public boolean addAchievements(String achievement, boolean bWithSave) {
		if (this.achievements.indexOf(achievement) < 0) {
			this.achievements.add(achievement);
			if (bWithSave) {
				this.saveAchievements();
			}
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
		if (function.equals(ApoJumpStateAchievements.BUTTON_BACK)) {
			this.getGame().setMenu();
		}
	
	}

	@Override
	public void mouseButtonReleased(int x, int y) {
	}

	
	@Override
	public void think(int delta) {
	}

	@Override
	public void render(Graphics2D g) {
		if (this.iBackground != null) {
			g.drawImage(this.iBackground, 0, 0, null);
		}
		
		g.setColor(Color.BLACK);
		
		String s;
		int w;
		
		int x = 30;
		int y = 95;
		g.setFont(ApoJumpConstants.FONT_BUTTON);
		BufferedImage iAchievement = ApoJumpImageContainer.iAchievement;
		if (this.achievements.size() > 0) {
			for (int i = 0; i < this.achievements.size(); i++) {
				s = this.achievements.get(i);
				w = g.getFontMetrics().stringWidth(s);
				g.drawImage(iAchievement, x, y, null);
				int h = g.getFontMetrics().getHeight() - 1 * g.getFontMetrics().getDescent();
				g.drawString(s, (int)(x + iAchievement.getWidth()/2 - w/2), (int)(y + iAchievement.getHeight()/2 + h/2));
				x += 40;
				if (iAchievement.getWidth() + x > ApoJumpConstants.GAME_WIDTH) {
					x = 30;
				}
				y += iAchievement.getHeight() + 10;
			}
		} else {
			x = ApoJumpConstants.GAME_WIDTH/2 - iAchievement.getWidth()/2;
			y = ApoJumpConstants.GAME_HEIGHT/2 - iAchievement.getHeight()/2;
			s = "You have not earned achievements yet";
			w = g.getFontMetrics().stringWidth(s);
			g.drawImage(iAchievement, x, y, null);
			int h = g.getFontMetrics().getHeight() - 1 * g.getFontMetrics().getDescent();
			g.drawString(s, (int)(x + iAchievement.getWidth()/2 - w/2), (int)(y + iAchievement.getHeight()/2 + h/2));
		}
	}


}
