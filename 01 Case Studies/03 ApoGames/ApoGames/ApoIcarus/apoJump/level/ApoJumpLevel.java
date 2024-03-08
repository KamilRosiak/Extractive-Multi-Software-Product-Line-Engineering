package apoJump.level;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.apogames.help.ApoHelp;

import apoJump.ApoJumpConstants;
import apoJump.ApoJumpImageContainer;
import apoJump.entity.ApoJumpArrow;
import apoJump.entity.ApoJumpEnemy;
import apoJump.entity.ApoJumpEntity;
import apoJump.entity.ApoJumpFeatureArrow;
import apoJump.entity.ApoJumpFeatureHelicopter;
import apoJump.entity.ApoJumpFeatureMissle;
import apoJump.entity.ApoJumpFeatureSpring;
import apoJump.entity.ApoJumpPlatformIce;
import apoJump.entity.ApoJumpPlatformInvisible;
import apoJump.entity.ApoJumpPlatformNormal;
import apoJump.entity.ApoJumpPlayer;
import apoJump.game.ApoJumpPanel;
import apoJump.game.ApoJumpStateAchievements;
import apoJump.game.ApoJumpStateHighscoreHelp;

public class ApoJumpLevel {

	public static final int ADD_POINTS = 200;
	
	private ApoJumpPlayer player;
	
	private ArrayList<ApoJumpEntity> plattforms;
	
	private ArrayList<ApoJumpEnemy> enemies;
	
	private ApoJumpArrow arrow;
	
	private ApoJumpPanel game;
	
	private int changeY = 0;
	
	private long time = 0;
	
	private int curPosition;
	
	private int addPoints;
	
	private int enemyKill;
	
	private int wingsCount;
	
	private int jump;
	
	private String curAchievement;
	
	private int curAchievementTime;
	
	public ApoJumpLevel(ApoJumpPanel game) {
		this.game = game;
	}
	
	public void init() {
		if (this.player == null) {
			BufferedImage iPlayer = ApoJumpImageContainer.iPlayer;
			int width = iPlayer.getWidth();
			int height = iPlayer.getHeight()/2;
			int x = ApoJumpConstants.GAME_WIDTH/2 - width/2;
			int y = ApoJumpConstants.GAME_HEIGHT - height - 1;
			this.player = new ApoJumpPlayer(iPlayer, x, y, width, height);
		}
		this.player.init();
		this.changeY = 0;
		this.time = 0;
		this.addPoints = 0;
		this.enemyKill = 0;
		this.wingsCount = 0;
		this.jump = 0;
		this.game.setBWin(false);
		this.curAchievementTime = 0;
		if (this.plattforms != null) {
			this.plattforms.clear();
		}
		
		this.enemies = new ArrayList<ApoJumpEnemy>();
		
		this.makePlatforms(ApoJumpConstants.GAME_HEIGHT, -100000);
		this.game.getButtons()[9].setBVisible(false);
		this.game.getButtons()[10].setBVisible(false);
		
		this.arrow = null;
		
		if (this.game.getHighscore().getHelp().size() > 0) {
			this.curPosition = this.game.getHighscore().getHelp().size() - 1;
		} else {
			this.curPosition = -1;
		}
	}
	
	public long getTime() {
		return this.time;
	}
	
	public void addJump() {
		this.jump += 1;
		if (this.jump == 100) {
			this.addAchievement(ApoJumpStateAchievements.ACHIEVEMENT_JUMPER);
		} else if (this.jump == 500) {
			this.addAchievement(ApoJumpStateAchievements.ACHIEVEMENT_REAL_JUMPER);
		}
	}
	
	public void addEnemyKill() {
		this.enemyKill += 1;
		if (this.enemyKill == 30) {
			this.addAchievement(ApoJumpStateAchievements.ACHIEVEMENT_ENEMY_KILLER);
		}
	}
	
	public void addWings() {
		this.wingsCount += 1;
		if (this.wingsCount == 5) {
			this.addAchievement(ApoJumpStateAchievements.ACHIEVEMENT_WINGS);
		}
	}

	public ApoJumpPlayer getPlayer() {
		return this.player;
	}

	public ArrayList<ApoJumpEntity> getPlattforms() {
		return this.plattforms;
	}
	
	public void addPoint(int addPoints) {
		this.addPoints += addPoints;
		if (this.addPoints < 0) {
			this.addPoints = 0;
		}
	}

	public int getPoints() {
		return -this.changeY + this.addPoints;
	}
	
	public boolean makeArrow(int x, int y) {
		if (this.arrow != null) {
			return false;
		}
		this.addPoint(ApoJumpConstants.POINTS_ARROW);
		int difX = (int)Math.abs(x - this.player.getX() - this.player.getWidth()/2);
		int difY = (int)Math.abs(y - this.player.getY() - this.player.getHeight()/2 + this.changeY);
		int max = difX + difY;
		float velX = ApoJumpConstants.MAX_VEC_ARROW * ((float)difX/(float)max);
		if (x < this.player.getX()) {
			velX = -ApoJumpConstants.MAX_VEC_ARROW * ((float)difX/(float)max);
		}
		float velY = ApoJumpConstants.MAX_VEC_ARROW * ((float)difY/(float)max);
		if (y + this.changeY < this.player.getY()) {
			velY = -ApoJumpConstants.MAX_VEC_ARROW * ((float)difY/(float)max);
		}
		this.arrow = new ApoJumpArrow(this.player.getX() + this.player.getWidth()/2, this.player.getY() + this.player.getHeight()/2, velX, velY);
		return true;
	}
	
	private void makePlatforms(float startHeight, float endHeight) {
		if (this.plattforms == null) {
			this.plattforms = new ArrayList<ApoJumpEntity>();
		}
		int maxHeight = (int)endHeight;
		int curHeight = (int)(startHeight);
		while (curHeight > maxHeight) {
			int add = (int)((double)this.plattforms.size() * Math.random());
			if (add > 30) {
				add = 30;
			}
			curHeight -= ApoJumpConstants.PLATFORM_HEIGHT;
			int x = (int)(Math.random() * (ApoJumpConstants.GAME_WIDTH - ApoJumpConstants.PLATFORM_WIDTH));
			double firstRandom = Math.random() * 100;
			if (Math.abs(curHeight) < 10000) {
				firstRandom += 20;
			} else if (Math.abs(curHeight) < 25000) {
				firstRandom += 7;
			}
			if (firstRandom > 20) {
				int randCloud = (int)(Math.random() * 100);
				if (randCloud > 10) {
					if (Math.random() * 100 < 10) {
						int rand = (int)(Math.random() * 100);
						if (rand < 80) {
							BufferedImage iJumpFeather = ApoJumpImageContainer.iJumpFeather;
							this.plattforms.add(new ApoJumpFeatureSpring(iJumpFeather, x + (int)(Math.random() * (ApoJumpConstants.PLATFORM_WIDTH - iJumpFeather.getWidth() - 10)) + 5, curHeight - iJumpFeather.getHeight() + 6, iJumpFeather.getWidth(), iJumpFeather.getHeight()));
						} else if (rand < 90) {
							BufferedImage iFeather = ApoJumpImageContainer.iFeather;
							this.plattforms.add(new ApoJumpFeatureHelicopter(iFeather, x + (int)(Math.random() * (ApoJumpConstants.PLATFORM_WIDTH - iFeather.getWidth() - 10)) + 5, curHeight - iFeather.getHeight() + 6, iFeather.getWidth(), iFeather.getHeight()));							
						} else if (rand < 95) {
							BufferedImage iArrow = ApoJumpImageContainer.iGoodieArrow;
							this.plattforms.add(new ApoJumpFeatureArrow(iArrow, x + 3, curHeight - iArrow.getHeight() + 4, iArrow.getWidth(), iArrow.getHeight()));							
						} else {
							BufferedImage iWing = ApoJumpImageContainer.iWings;
							this.plattforms.add(new ApoJumpFeatureMissle(iWing, x + (int)(Math.random() * (ApoJumpConstants.PLATFORM_WIDTH - iWing.getWidth() - 10)) + 5, curHeight - iWing.getHeight(), iWing.getWidth() + 4, iWing.getHeight()));
						}
					} else {
						if (curHeight < -1000) {
							if (Math.random() * 1000 < 30) {
								int rand = (int)(Math.random() * 1000);
								if (rand < 100) {
									BufferedImage iEnemy = ApoJumpImageContainer.iEnemyOne;
									int enemyX = (int)(Math.random() * (ApoJumpConstants.GAME_WIDTH - iEnemy.getWidth()/2));
									int enemyY = curHeight;
									this.enemies.add(new ApoJumpEnemy(iEnemy, enemyX, enemyY, iEnemy.getWidth()/2, iEnemy.getHeight(), 2, 150, 1, false, 0, (float)(Math.random() * ApoJumpConstants.MAX_VEC_X - ApoJumpConstants.MAX_VEC_X/2f), ApoJumpConstants.POINTS_ENEMY_ONE));
									this.enemies.get(this.enemies.size() - 1).setMaxY(50);
								} else if (rand < 300) {
									BufferedImage iEnemy = ApoJumpImageContainer.iEnemyNine;
									int enemyX = (int)(Math.random() * (ApoJumpConstants.GAME_WIDTH - iEnemy.getWidth()/2));
									int enemyY = curHeight;
									this.enemies.add(new ApoJumpEnemy(iEnemy, enemyX, enemyY, iEnemy.getWidth()/2, iEnemy.getHeight(), 2, 150, 1, false, (float)(Math.random() * ApoJumpConstants.MAX_VEC_X - ApoJumpConstants.MAX_VEC_X/2f), 0, ApoJumpConstants.POINTS_ENEMY_NINE));
								} else if (rand < 350) {
									BufferedImage iEnemy = ApoJumpImageContainer.iEnemyEight;
									int enemyX = (int)(Math.random() * (ApoJumpConstants.GAME_WIDTH - iEnemy.getWidth()));
									int enemyY = curHeight;
									this.enemies.add(new ApoJumpEnemy(iEnemy, enemyX, enemyY, iEnemy.getWidth(), iEnemy.getHeight(), 1, 150, 1, false, (float)(Math.random() * ApoJumpConstants.MAX_VEC_X - ApoJumpConstants.MAX_VEC_X/2f), (float)(Math.random() * ApoJumpConstants.MAX_VEC_X - ApoJumpConstants.MAX_VEC_X/2f), ApoJumpConstants.POINTS_ENEMY_EIGHT));
								} else if (rand < 500) {
									BufferedImage iEnemy = ApoJumpImageContainer.iEnemySeven;
									int enemyX = (int)(Math.random() * (ApoJumpConstants.GAME_WIDTH - iEnemy.getWidth()));
									int enemyY = curHeight;
									this.enemies.add(new ApoJumpEnemy(iEnemy, enemyX, enemyY, iEnemy.getWidth(), iEnemy.getHeight(), 1, 150, 1, false, (float)(Math.random() * ApoJumpConstants.MAX_VEC_X - ApoJumpConstants.MAX_VEC_X/2f), (float)(Math.random() * ApoJumpConstants.MAX_VEC_X - ApoJumpConstants.MAX_VEC_X/2f), ApoJumpConstants.POINTS_ENEMY_SEVEN));
								} else if (rand < 600) {
									BufferedImage iEnemy = ApoJumpImageContainer.iEnemyFive;
									int enemyX = (int)(Math.random() * (ApoJumpConstants.GAME_WIDTH - iEnemy.getWidth()));
									int enemyY = curHeight;
									this.enemies.add(new ApoJumpEnemy(iEnemy, enemyX, enemyY, iEnemy.getWidth(), iEnemy.getHeight(), 1, 150, 1, false, 0, 0, ApoJumpConstants.POINTS_ENEMY_FIVE));
								} else if (rand < 675) {
									BufferedImage iEnemy = ApoJumpImageContainer.iEnemySix;
									int enemyX = (int)(Math.random() * (ApoJumpConstants.GAME_WIDTH - iEnemy.getWidth()));
									int enemyY = curHeight;
									this.enemies.add(new ApoJumpEnemy(iEnemy, enemyX, enemyY, iEnemy.getWidth(), iEnemy.getHeight(), 1, 150, 1, false, (float)(Math.random() * ApoJumpConstants.MAX_VEC_X - ApoJumpConstants.MAX_VEC_X/2f), (float)(Math.random() * ApoJumpConstants.MAX_VEC_X - ApoJumpConstants.MAX_VEC_X/2f), ApoJumpConstants.POINTS_ENEMY_SIX));
								} else if (rand < 750) {
									BufferedImage iEnemy = ApoJumpImageContainer.iEnemyTwo;
									int enemyX = (int)(Math.random() * (ApoJumpConstants.GAME_WIDTH - iEnemy.getWidth()));
									int enemyY = curHeight;
									this.enemies.add(new ApoJumpEnemy(iEnemy, enemyX, enemyY, iEnemy.getWidth(), iEnemy.getHeight(), 1, 100000, 1, false, (float)(Math.random() * ApoJumpConstants.MAX_VEC_X - ApoJumpConstants.MAX_VEC_X/2f), 0, ApoJumpConstants.POINTS_ENEMY_TWO));
								} else if (rand < 850) {
									BufferedImage iEnemy = ApoJumpImageContainer.iEnemyThree;
									int enemyX = (int)(Math.random() * (ApoJumpConstants.GAME_WIDTH - iEnemy.getWidth()));
									int enemyY = curHeight;
									this.enemies.add(new ApoJumpEnemy(iEnemy, enemyX, enemyY, iEnemy.getWidth(), iEnemy.getHeight(), 1, 100000, 1, false, 0, 0, ApoJumpConstants.POINTS_ENEMY_THREE));
								} else {
									BufferedImage iEnemy = ApoJumpImageContainer.iEnemyFour;
									int enemyX = (int)(Math.random() * (ApoJumpConstants.GAME_WIDTH - iEnemy.getWidth()));
									int enemyY = curHeight;
									this.enemies.add(new ApoJumpEnemy(iEnemy, enemyX, enemyY, iEnemy.getWidth(), iEnemy.getHeight(), 1, 100000, 1, false, 0, 0, ApoJumpConstants.POINTS_ENEMY_FOUR));
								}
							}
						}
					}
					this.plattforms.add(new ApoJumpPlatformNormal(ApoJumpImageContainer.iCloud, x, curHeight, ApoJumpConstants.PLATFORM_WIDTH, ApoJumpConstants.PLATFORM_HEIGHT, 0, 0));
				} else if ((randCloud > 1) || (Math.abs(curHeight) < 10000)) {
					float vecX = (float)(Math.random() * 0.1f + 0.05f);
					this.plattforms.add(new ApoJumpPlatformNormal(ApoJumpImageContainer.iCloud, x, curHeight, ApoJumpConstants.PLATFORM_WIDTH, ApoJumpConstants.PLATFORM_HEIGHT, vecX, 0));
				} else {
					float vecY = (float)(Math.random() * 0.1f + 0.05f);
					this.plattforms.add(new ApoJumpPlatformNormal(ApoJumpImageContainer.iCloud, x, curHeight, ApoJumpConstants.PLATFORM_WIDTH, ApoJumpConstants.PLATFORM_HEIGHT, 0, vecY));
				}
			} else if (firstRandom > 7) {
				int rand = (int)(Math.random() * 100);
				if (rand > 10) {
					this.plattforms.add(new ApoJumpPlatformIce(ApoJumpImageContainer.iCloudOne, x, curHeight, ApoJumpConstants.PLATFORM_WIDTH, ApoJumpConstants.PLATFORM_HEIGHT, 0, 0));
				} else if (rand > 2) {
					float vecX = (float)(Math.random() * 0.1f + 0.05f);
					this.plattforms.add(new ApoJumpPlatformIce(ApoJumpImageContainer.iCloudOne, x, curHeight, ApoJumpConstants.PLATFORM_WIDTH, ApoJumpConstants.PLATFORM_HEIGHT, vecX, 0));
				} else {
					float vecY = (float)(Math.random() * 0.1f + 0.05f);
					this.plattforms.add(new ApoJumpPlatformIce(ApoJumpImageContainer.iCloudOne, x, curHeight, ApoJumpConstants.PLATFORM_WIDTH, ApoJumpConstants.PLATFORM_HEIGHT, 0, vecY));					
				}
			} else {
				int rand = (int)(Math.random() * 100);
				if (Math.random() * 100 > 10) {
					this.plattforms.add(new ApoJumpPlatformInvisible(ApoJumpImageContainer.iCloudTwo, x, curHeight, ApoJumpConstants.PLATFORM_WIDTH, ApoJumpConstants.PLATFORM_HEIGHT, 0, 0));
				} else if (rand > 2) {
					float vecX = (float)(Math.random() * 0.1f + 0.05f);
					this.plattforms.add(new ApoJumpPlatformInvisible(ApoJumpImageContainer.iCloudTwo, x, curHeight, ApoJumpConstants.PLATFORM_WIDTH, ApoJumpConstants.PLATFORM_HEIGHT, vecX, 0));
				} else {
					float vecY = (float)(Math.random() * 0.1f + 0.05f);
					this.plattforms.add(new ApoJumpPlatformInvisible(ApoJumpImageContainer.iCloudTwo, x, curHeight, ApoJumpConstants.PLATFORM_WIDTH, ApoJumpConstants.PLATFORM_HEIGHT, 0, vecY));
				}
			}
			curHeight -= add;
		}
	}
	
	public final ArrayList<ApoJumpEnemy> getEnemies() {
		return this.enemies;
	}

	public int getChangeY() {
		return this.changeY;
	}

	public void setWin() {
		if (this.isFirstPosition()) {
			this.addAchievement(ApoJumpStateAchievements.ACHIEVEMENT_TOP);
		}
		this.game.setBWin(true);
	}
	
	public boolean isFirstPosition() {
		ApoJumpStateHighscoreHelp help = this.game.getHighscore().getHelp().get(1);
		if (help.getPoints() < this.getPoints()) {
			return true;
		}
		return false;
	}
	
	public void think(int delta) {
		if (this.game.isBWin()) {
			
		} else {
			if (this.curAchievementTime > 0) {
				this.curAchievementTime -= delta;
			}
			this.time += delta;
			this.makeChangeY();
			this.player.think((int)delta, this);
			for (int i = this.plattforms.size() - 1; i >= 0; i--) {
				ApoJumpEntity entity = this.plattforms.get(i);
				if (entity.isBVisible()) {
					if ((entity.getY() - ApoJumpConstants.GAME_HEIGHT <= this.changeY) && (entity.getY() + ApoJumpConstants.GAME_HEIGHT/2 >= this.changeY)) {
						entity.think(delta, this);
					}
				}
				if ((!entity.isBVisible()) || (entity.getY() - ApoJumpConstants.GAME_HEIGHT > this.player.getY())) {
					this.plattforms.remove(i);
				}
			}
			for (int i = this.enemies.size() - 1; i >= 0; i--) {
				ApoJumpEnemy entity = this.enemies.get(i);
				if ((entity.getY() + ApoJumpConstants.GAME_HEIGHT > this.player.getY()) && (entity.getY() - ApoJumpConstants.GAME_HEIGHT < this.player.getY())) {
					entity.update(delta, this);
				}
				if (entity.getY() - ApoJumpConstants.GAME_HEIGHT > this.player.getY()) {
					this.enemies.remove(i);
				}
			}
			if (this.plattforms.size() < 100) {
				this.makePlatforms(this.plattforms.get(this.plattforms.size() - 1).getY(), this.player.getY() - 100000);
			}
			if (this.arrow != null) {
				this.arrow.update(delta, this);
				if (!this.arrow.isBVisible()) {
					this.arrow = null;
				}
			}
			try {
				if ((this.curPosition >= 0) && (this.game.getHighscore().getHelp().size() > this.curPosition)) {
					ApoJumpStateHighscoreHelp help = this.game.getHighscore().getHelp().get(this.curPosition);
					if (help.getPoints() < this.getPoints() - ApoJumpConstants.GAME_HEIGHT/2) {
						boolean bFind = false;
						for (int i = this.curPosition - 1; i >= 0; i--) {
							if (this.game.getHighscore().getHelp().get(i).getPoints() >= this.getPoints() + ApoJumpLevel.ADD_POINTS - ApoJumpConstants.GAME_HEIGHT/2) {
								this.curPosition = i;
								bFind = true;
								break;
							}
						}
						if (!bFind) {
							this.curPosition = -1;
						}
					}
				}
			} catch (Exception ex) {	
			}

			if ((this.getPoints() >= 75000) && (this.getPoints() <= 77000)) {
				this.addAchievement(ApoJumpStateAchievements.ACHIEVEMENT_75000_POINTS);
			}
			if ((this.time >= 900000) && (this.time <= 900500)) {
				this.addAchievement(ApoJumpStateAchievements.ACHIEVEMENT_TIMER_15);					
			}
			if ((this.time >= 180000) && (this.time <= 180500)) {
				this.addAchievement(ApoJumpStateAchievements.ACHIEVEMENT_TIMER_3);					
			}
		}
	}
	
	private void addAchievement(String achievement) {
		if (this.game.getAchievements().addAchievements(achievement, true)) {
			this.curAchievement = achievement;
			this.curAchievementTime = 3500;
		}
	}
	
	private void makeChangeY() {
		if (this.getPlayer().getY() - this.changeY < ApoJumpConstants.GAME_HEIGHT/2) {
			this.changeY = (int)(this.getPlayer().getY() - ApoJumpConstants.GAME_HEIGHT/2);
		}
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		for (ApoJumpEntity entity: this.plattforms) {
			entity.render(g, changeX, changeY + this.changeY);
		}
		for (ApoJumpEnemy entity: this.enemies) {
			if (entity.isBVisible()) {
				entity.render(g, changeX, changeY + this.changeY);
			}
		}
		if (this.arrow != null) {
			this.arrow.render(g, changeX, changeY + this.changeY);
		}

		if ((this.game.getHighscore().getHelp().size() > 0) && (this.curPosition >= 0) && (this.game.getHighscore().getHelp().size() > this.curPosition)) {
			int oldPoint = -1;
			for (int i = this.curPosition; i >= 0 && i < this.game.getHighscore().getHelp().size(); i--) {
				ApoJumpStateHighscoreHelp help = this.game.getHighscore().getHelp().get(i);
				boolean bDraw = false;
				if (oldPoint == -1) {
					oldPoint = help.getPoints();
					bDraw = true;
				} else if (oldPoint + ApoJumpLevel.ADD_POINTS < help.getPoints()) {
					oldPoint = help.getPoints();
					bDraw = true;
				} 
				if (bDraw) {
					if ((help.getPoints() > this.getPoints()) && (help.getPoints() < this.getPoints() + ApoJumpConstants.GAME_HEIGHT)) {
						g.setFont(ApoJumpConstants.FONT_BUTTON);
						g.setColor(Color.BLACK);
						String s = String.valueOf(i + 1) +") "+help.getName();
						int w = g.getFontMetrics().stringWidth(s);
						g.drawString(s, ApoJumpConstants.GAME_WIDTH - w - 2, ApoJumpConstants.GAME_HEIGHT - help.getPoints() + this.getPoints() - 2);
						
						s = String.valueOf(help.getPoints());
						w = g.getFontMetrics().stringWidth(s);
						g.drawString(s, ApoJumpConstants.GAME_WIDTH - w - 2, ApoJumpConstants.GAME_HEIGHT - help.getPoints() + this.getPoints() + 17);
					}
				}
			}
		}
		
		this.player.render(g, changeX, changeY + this.changeY);
		this.renderStatistics(g);
		
		if (this.curAchievementTime > 0) {
			BufferedImage iAchievement = ApoJumpImageContainer.iAchievement;
			g.setColor(Color.BLACK);
			g.setFont(ApoJumpConstants.FONT_BUTTON);
			String s = this.curAchievement;
			int w = g.getFontMetrics().stringWidth(s);
			int x = ApoJumpConstants.GAME_WIDTH/2 - iAchievement.getWidth()/2;
			int y = 55;
			g.drawImage(iAchievement, x, y, null);
			int h = g.getFontMetrics().getHeight() - 1 * g.getFontMetrics().getDescent();
			g.drawString(s, (int)(x + iAchievement.getWidth()/2 - w/2), (int)(y + iAchievement.getHeight()/2 + h/2));
		}
	}
	
	private void renderStatistics(Graphics2D g) {
		g.drawImage(ApoJumpImageContainer.iGameHud, 0, 0, null);
		
		g.setColor(Color.BLACK);
		g.setFont(ApoJumpConstants.FONT_STATISTICS);
		String s = "Time: "+ApoHelp.getTimeToDraw((int)this.time);
		g.drawString(s, 15, 35);
		s = "Points: "+(String.valueOf(this.getPoints()));
		int w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoJumpConstants.GAME_WIDTH - 20 - w, 35);
		
		if (!this.game.isBWin()) {
			if ((this.game.getHighscore().getHelp().size() > 0) && (this.curPosition >= 0) && (this.game.getHighscore().getHelp().size() > this.curPosition)) {
				s = "Place: "+(String.valueOf(this.curPosition + 2) + " / " + String.valueOf(this.game.getHighscore().getHelp().size() + 1));
				w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, ApoJumpConstants.GAME_WIDTH/2 - w/2, 35);
			}
		}
	}
}
