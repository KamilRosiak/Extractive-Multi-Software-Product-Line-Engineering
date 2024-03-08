package apoMarc.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import apoMarc.ApoMarcConstants;
import apoMarc.entity.ApoMarcPaddle;
import apoMarc.entity.ApoMarcPaddleAI;
import apoMarc.entity.ApoMarcPlayer;
import apoMarc.entity.ApoMarcWall;

public class ApoMarcGame extends ApoMarcModel {
	
	public static final Font FONT = new Font(Font.SANS_SERIF, Font.BOLD, 45);
	
	public static final String MENU_STRING = "menu";
	public static final String MENU = "game_menu";
	public static final String RESTART = "game_restart";
	public static final String MENU_REAL = "game_menu_real";
	public static final String RESUME = "game_resume";
	
	private BufferedImage iBackground;
	
	private BufferedImage iBackgroundWin;
	
	private ApoMarcWall[] walls;
	
	private ApoMarcPlayer[] players;
	
	private ApoMarcPaddle paddle;
	
	private int changeX, changeY;
	
	private boolean bOver;
	
	private boolean bPause;
	
	private String playerWinner;
	
	public ApoMarcGame(ApoMarcPanel game) {
		super(game);
		
		this.init(ApoMarcConstants.DIFFICULTY_MARC);
	}
	
	public void init(int difficulty) {
		if (this.iBackground == null) {
			this.iBackground = new BufferedImage(ApoMarcConstants.GAME_WIDTH, ApoMarcConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = this.iBackground.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			g.setColor(new Color(2, 0, 45));
			g.fillRect(0, 0, this.iBackground.getWidth(), this.iBackground.getHeight());
			
			g.setColor(new Color(255, 255, 255, 100));
			g.drawLine(0, this.iBackground.getHeight()/2 - 2, this.iBackground.getWidth(), this.iBackground.getHeight()/2 - 2);
			g.drawLine(0, this.iBackground.getHeight()/2 + 1, this.iBackground.getWidth(), this.iBackground.getHeight()/2 + 1);
			
			int width = 100;
			g.drawOval(this.iBackground.getWidth()/2 - width/2, this.iBackground.getHeight()/2 - width/2, width, width);
			width = 94;
			g.drawOval(this.iBackground.getWidth()/2 - width/2, this.iBackground.getHeight()/2 - width/2, width, width);
			
			width = ApoMarcConstants.GOAL_WIDTH;
			g.drawOval(this.iBackground.getWidth()/2 - width/2, 0 - width/2, width, width);
			g.drawOval(this.iBackground.getWidth()/2 - width/2, this.iBackground.getHeight() - width/2, width, width);
			
			g.dispose();
		}
		if (this.walls == null) {
			this.walls = new ApoMarcWall[8];
			int wallWidth = 7;
			int width = ApoMarcConstants.GAME_WIDTH/2 - ApoMarcConstants.GOAL_WIDTH/2 - 10;
			int height = ApoMarcConstants.GAME_HEIGHT/2 - 10;
			this.walls[0] = new ApoMarcWall(10, 10, width, wallWidth, 0);
			this.walls[1] = new ApoMarcWall(ApoMarcConstants.GAME_WIDTH/2 + ApoMarcConstants.GOAL_WIDTH/2, 10, width, wallWidth, 1);
			this.walls[2] = new ApoMarcWall(10, ApoMarcConstants.GAME_HEIGHT - 10, width, wallWidth, 2);
			this.walls[3] = new ApoMarcWall(ApoMarcConstants.GAME_WIDTH/2 + ApoMarcConstants.GOAL_WIDTH/2, ApoMarcConstants.GAME_HEIGHT - 10, width, wallWidth, 3);
			
			this.walls[4] = new ApoMarcWall(10, 10, wallWidth, height, 0);
			this.walls[5] = new ApoMarcWall(10, ApoMarcConstants.GAME_HEIGHT/2, wallWidth, height, 2);
			this.walls[6] = new ApoMarcWall(ApoMarcConstants.GAME_WIDTH - 10, 10, wallWidth, height, 1);
			this.walls[7] = new ApoMarcWall(ApoMarcConstants.GAME_WIDTH - 10, ApoMarcConstants.GAME_HEIGHT/2, wallWidth, height, 3);
		} else {
			for (int i = 0; i < this.walls.length; i++) {
				this.walls[i].init();
			}
		}
		
		if (this.players == null) {
			this.players = new ApoMarcPlayer[2];
			int width = ApoMarcConstants.PLAYER_WIDTH;
			Rectangle2D.Float rec = new Rectangle2D.Float(5, 5, ApoMarcConstants.GAME_WIDTH - 10, ApoMarcConstants.GAME_HEIGHT/2 - 5);
			this.players[0] = new ApoMarcPlayer(ApoMarcConstants.GAME_WIDTH/2 - width/2, 30, width, Color.BLUE, true, rec);
			this.players[0].setAi(new ApoMarcPaddleAI());
			rec = new Rectangle2D.Float(5, ApoMarcConstants.GAME_HEIGHT/2, ApoMarcConstants.GAME_WIDTH - 10, ApoMarcConstants.GAME_HEIGHT/2 - 5);
			this.players[1] = new ApoMarcPlayer(ApoMarcConstants.GAME_WIDTH/2 - width/2, ApoMarcConstants.GAME_HEIGHT - width - 30, width, Color.RED, true, rec);
		}
		for (int i = 0; i < this.players.length; i++) {
			this.players[i].init();
			this.players[i].setDifficulty(difficulty);
		}
		if (this.paddle == null) {
			int width = ApoMarcConstants.PADDLE_WIDTH;
			Rectangle2D.Float rec = new Rectangle2D.Float(0, -50, ApoMarcConstants.GAME_WIDTH, ApoMarcConstants.GAME_HEIGHT + 100);
			this.paddle = new ApoMarcPaddle(ApoMarcConstants.GAME_WIDTH/2 - width/2, ApoMarcConstants.GAME_HEIGHT/2 + 30, width, Color.WHITE, rec);
		} else {
			this.paddle.init();
		}
		this.setBOver(false);
	}
	
	private void restart() {
		for (int i = 0; i < this.walls.length; i++) {
			this.walls[i].init();
		}
		for (int i = 0; i < this.players.length; i++) {
			this.players[i].init();
		}
		this.paddle.init();
		this.setBOver(false);
		this.bPause = false;
	}

	private void setBPause(boolean pause) {
		this.bOver = false;
		this.bPause = pause;
		this.getGame().getButtons()[3].setBVisible(!this.bPause);
		this.getGame().getButtons()[17].setBVisible(false);
		this.getGame().getButtons()[18].setBVisible(this.bPause);
		this.getGame().getButtons()[19].setBVisible(this.bPause);
	}
	
	private void setBOver(boolean over) {
		this.bOver = over;
		this.bPause = false;
		this.getGame().getButtons()[3].setBVisible(!this.bOver);
		this.getGame().getButtons()[17].setBVisible(this.bOver);
		this.getGame().getButtons()[18].setBVisible(this.bOver);
		this.getGame().getButtons()[19].setBVisible(false);
	}

	public boolean isBPause() {
		return this.bPause;
	}

	public ApoMarcPaddle getPaddle() {
		return this.paddle;
	}

	public ApoMarcWall[] getWalls() {
		return this.walls;
	}

	public ApoMarcPlayer[] getPlayers() {
		return this.players;
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			if (this.bPause) {
				this.getGame().setMenu();	
			} else {
				this.setPause(true);
			}
		} else if (button == KeyEvent.VK_ENTER) {
			if (this.bPause) {
				this.setPause(false);
			} else {
				this.setPause(true);
			}
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoMarcGame.MENU)) {
			this.setPause(true);
		} else if (function.equals(ApoMarcGame.MENU_REAL)) {
			this.getGame().setMenu();
		} else if (function.equals(ApoMarcGame.RESUME)) {
			this.setBPause(false);
		} else if (function.equals(ApoMarcGame.RESTART)) {
			this.restart();
		}
	}

	private void setPause(boolean bPause) {
		if (bPause) {
			this.iBackgroundWin = new BufferedImage(ApoMarcConstants.GAME_WIDTH, ApoMarcConstants.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);						
			Graphics2D g = this.iBackgroundWin.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			this.render(g);
			this.setBPause(true);
			this.drawOver(g);
			
			g.dispose();
		}
		this.setBPause(bPause);
	}
	
	@Override
	public void mouseButtonReleased(int x, int y) {
		if ((this.bPause) || (this.bOver)) {
			return;
		} else {
			if (this.players != null) {
				if (!this.players[1].intersects(x, y)) {
					this.players[1].setBSelect(false);
				}
			}
		}
	}
	
	public boolean mouseMoved(int x, int y) {
		return this.mouseDragged(x, y);
	}
	
	public boolean mouseDragged(int x, int y) {
		if (this.players != null) {
			if (this.players[1].isBSelect()) {
				this.players[1].setNewPosition(x - this.changeX, y - this.changeY, this);
			}
		}
		return false;
	}
	
	public boolean mousePressed(int x, int y, boolean bRight) {
		if ((this.bPause) || (this.bOver)) {
			return false;
		}
		if (bRight) {
			this.players[1].setBSelect(false);
		} else {
			if (this.players != null) {
				if (this.players[1].intersects(x, y)) {
					this.players[1].setBSelect(true);
					this.changeX = (int)(x - this.players[1].getX());
					this.changeY = (int)(y - this.players[1].getY());
				} else {
					this.players[1].setBSelect(false);
				}
			}
		}
		return false;
	}

	@Override
	public void think(long delta) {
		if (this.bPause) {
			return;
		}
		if (!this.bOver) {
			if (this.walls != null) {
				int color = -1;
				boolean bAll = true;
				for (int i = 0; i < this.walls.length; i++) {
					this.walls[i].think(delta, this);
					if (color != -1) {
						if (color != this.walls[i].getColorOrder()) {
							bAll = false;
						}
					} else {
						color = this.walls[i].getColorOrder();
					}
				}
				if (bAll) {
					this.getGame().addAchievement(ApoMarcAchievements.ACHIEVEMENT_WALLS);
				}
			}
			if (this.players != null) {
				for (int i = 0; i < this.players.length; i++) {
					this.players[i].think(delta, this);
					if (this.players[i].getPoints() >= ApoMarcConstants.WINNING_POINTS) {
						if (i != 0) {
							if (this.players[i].getDifficulty() == ApoMarcConstants.DIFFICULTY_APO) {
								this.getGame().addAchievement(ApoMarcAchievements.ACHIEVEMENT_WIN_APO);
							} else if (this.players[i].getDifficulty() == ApoMarcConstants.DIFFICULTY_MANDY) {
								this.getGame().addAchievement(ApoMarcAchievements.ACHIEVEMENT_WIN_MANDY);
							} else if (this.players[i].getDifficulty() == ApoMarcConstants.DIFFICULTY_MARC) {
								this.getGame().addAchievement(ApoMarcAchievements.ACHIEVEMENT_WIN_MARC);
							} else if (this.players[i].getDifficulty() == ApoMarcConstants.DIFFICULTY_UNBEATABLE) {
								this.getGame().addAchievement(ApoMarcAchievements.ACHIEVEMENT_WIN_UNBEATABLE);
							}
							if (this.players[0].getPoints() == 0) {
								this.getGame().addAchievement(ApoMarcAchievements.ACHIEVEMENT_WIN_ZERO);
							}
							this.playerWinner = "YOU";
						} else {
							if (this.players[i].getDifficulty() == ApoMarcConstants.DIFFICULTY_APO) {
								this.playerWinner = "The Apo AI";
							} else if (this.players[i].getDifficulty() == ApoMarcConstants.DIFFICULTY_MANDY) {
								this.playerWinner = "The Mandy AI";
							} else if (this.players[i].getDifficulty() == ApoMarcConstants.DIFFICULTY_MARC) {
								this.playerWinner = "The Marc AI";
							} else if (this.players[i].getDifficulty() == ApoMarcConstants.DIFFICULTY_UNBEATABLE) {
								this.playerWinner = "The godlike AI";
							}
						}
						this.setPause(true);
					}
				}
			}
			if (this.paddle != null) {
				this.paddle.think(delta, this);
			}
		} else {
			
		}
		
	}
	
	@Override
	public void render(Graphics2D g) {
		if ((this.bOver) || (this.bPause)) {
			if (this.iBackgroundWin != null) {
				g.drawImage(this.iBackgroundWin, 0, 0, null);
			}
		} else {
			if (this.iBackground != null) {
				g.drawImage(this.iBackground, 0, 0, null);
			} else {
				this.getGame().renderBackground(g);
			}
			if (this.walls != null) {
				for (int i = 0; i < this.walls.length; i++) {
					this.walls[i].render(g, 0, 0);
				}
			}
			if (this.players != null) {
				for (int i = 0; i < this.players.length; i++) {
					this.players[i].render(g, 0, 0);
				}
			}
			if (this.paddle != null) {
				this.paddle.render(g, 0, 0);
			}
			this.drawStatistics(g);
		}
	}
	
	private void drawStatistics(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.setFont(ApoMarcGame.FONT);
		for (int i = 0; i < this.players.length; i++) {
			String s = String.valueOf(this.players[i].getPoints());
			int w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, ApoMarcConstants.GAME_WIDTH - 15 - w, ApoMarcConstants.GAME_HEIGHT/2 - 40 + (i *100));
		}
	}
	
	private void drawOver(Graphics2D g) {		
		g.setColor(new Color(0, 0, 0, 150));
		g.fillRoundRect(10, ApoMarcConstants.GAME_HEIGHT/2 - 80, ApoMarcConstants.GAME_WIDTH - 20, 210, 20, 20);
		g.setColor(Color.WHITE);
		g.drawRoundRect(10, ApoMarcConstants.GAME_HEIGHT/2 - 80, ApoMarcConstants.GAME_WIDTH - 20, 210, 20, 20);
		
		g.setFont(ApoMarcGame.FONT);
		if (this.bOver) {
			String s = "The winner is";
			int w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, ApoMarcConstants.GAME_WIDTH/2 - w/2, ApoMarcConstants.GAME_HEIGHT/2 - 30);
			
			s = this.playerWinner;
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, ApoMarcConstants.GAME_WIDTH/2 - w/2, ApoMarcConstants.GAME_HEIGHT/2 + 30);
		} else if (this.bPause) {
			String s = "Pause";
			int w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, ApoMarcConstants.GAME_WIDTH/2 - w/2, ApoMarcConstants.GAME_HEIGHT/2 - 15);			
		}
	}

}
