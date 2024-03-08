package apoIcejump.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import org.apogames.entity.ApoButton;

import apoIcejump.ApoIcejumpConstants;
import apoIcejump.ai.Easy;
import apoIcejump.ai.Hard;
import apoIcejump.ai.JoJo;
import apoIcejump.ai.Middle;
import apoIcejump.ai.Trazzag;

public class ApoIcejumpMenu extends ApoIcejumpModel {
	
	public static final String QUIT = "quit";
	public static final String START = "start";
	public static final String TUTORIAL = "tutorial";
	public static final String CREDITS = "credits";
	public static final String NETWORK = "network";
	public static final String SIMULATE = "simulate";
	public static final String LEFT_PLAYERONE = "leftPlayerOne";
	public static final String RIGHT_PLAYERONE = "rightPlayerOne";
	public static final String LOAD_PLAYERONE = "loadPlayerOne";
	public static final String LEFT_PLAYERTWO = "leftPlayerTwo";
	public static final String RIGHT_PLAYERTWO = "rightPlayerTwo";
	public static final String LOAD_PLAYERTWO = "loadPlayerTwo";
	
	private int time;
	
	private BufferedImage iBackgroundForPlayer;
	
	private boolean bStart;
	
	private float changeY;

	private int choosePlayerOne, choosePlayerTwo;
	
	public ApoIcejumpMenu(ApoIcejumpPanel game) {
		super(game);
	}
	
	public void start(boolean bFirst, boolean bAnalysis) {
		if (!bFirst) {
			this.bStart = false;
			this.changeY = ApoIcejumpConstants.GAME_HEIGHT;
		} else {
			this.bStart = true;
			this.changeY = 0;
		}

		if (!bAnalysis) {
			this.makeTheFish();
			this.makeButtons();
		}
		if (this.iBackgroundForPlayer == null) {
			this.iBackgroundForPlayer = this.makeAndGetBackgroundForPlayer();
		}
		for (int i = 0; i < this.getGame().getPlayers().length; i++) {
			this.getGame().getPlayers()[i].init();
		}
		this.getGame().makeNewParticle();
	}
	
	private void makeTheFish() {
		this.getGame().makeFish((int)(Math.random() * 25 + 30), false);
		this.time = (int)(Math.random() * 3000 + 200);
	}
	
	private void makeButtons() {
		for (int i = 0; i < this.getGame().getButtons().length; i++) {
			if (this.getGame().getButtons()[i].isBVisible()) {
				this.getGame().getButtons()[i].init();
				if ((i < 5) || (i > 10)) {
					this.getGame().getButtons()[i].setVecX((float)(Math.random() * 0.05 - 0.025f));
				}
				this.getGame().getButtons()[i].setVecY((float)(Math.random() * 0.01 - 0.005f));
			}
		}
	}
	
	private BufferedImage makeAndGetBackgroundForPlayer() {
		BufferedImage iBackground = new BufferedImage(220, 200, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = (Graphics2D)iBackground.getGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(new Color(255, 255, 255, 200));
		g.fillRoundRect(0, 0, iBackground.getWidth() - 1, iBackground.getHeight() - 1, 15, 15);
		g.setColor(Color.WHITE);
		g.drawRoundRect(0, 0, iBackground.getWidth() - 1, iBackground.getHeight() - 1, 15, 15);
		
		g.dispose();
		
		return iBackground;
	}
	
	public void loadPlayerAI(int player, int ai) {
		if (player == ApoIcejumpConstants.PLAYER_ONE) {
			this.choosePlayerOne = ai;
			ApoIcejumpConstants.AI_LEFT = String.valueOf(ai);
		} else if (player == ApoIcejumpConstants.PLAYER_TWO) {
			this.choosePlayerTwo = ai;
			ApoIcejumpConstants.AI_RIGHT = String.valueOf(ai);
		} else {
			return;
		}
		if (ai == ApoIcejumpConstants.AI_HUMAN) {
			this.getGame().getPlayers()[player].setAI(null, this.getGame().getImages());
		} else if (ai == ApoIcejumpConstants.AI_EASY) {
			this.getGame().getPlayers()[player].setAI(new Easy(), this.getGame().getImages());
		} else if (ai == ApoIcejumpConstants.AI_MIDDLE) {
			this.getGame().getPlayers()[player].setAI(new Middle(), this.getGame().getImages());
		} else if (ai == ApoIcejumpConstants.AI_HARD) {
			this.getGame().getPlayers()[player].setAI(new Hard(), this.getGame().getImages());
		} else if (ai == ApoIcejumpConstants.AI_JOJO) {
			this.getGame().getPlayers()[player].setAI(new JoJo(), this.getGame().getImages());
		} else if (ai == ApoIcejumpConstants.AI_TRAZZAG) {
			this.getGame().getPlayers()[player].setAI(new Trazzag(), this.getGame().getImages());
		}
		
		ApoIcejumpConstants.saveProperties();
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}
	
	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			this.exitGame();
		} else if (button == KeyEvent.VK_ENTER) {
			this.getGame().setBNetwork(false);
			this.getGame().setGame();
		} else if (button == KeyEvent.VK_C) {
			this.getGame().setCredits();
		} else if (button == KeyEvent.VK_T) {
			this.getGame().setTutorial();
		} else if (button == KeyEvent.VK_N) {
			this.getGame().setNetwork(true);
		} else if (button == KeyEvent.VK_S) {
			this.getGame().setSimulate(true);
		}
	}
	
	private void exitGame() {
		ApoIcejumpConstants.FPS = this.getGame().isBFPS();
		ApoIcejumpConstants.saveProperties();
		System.exit(0);
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoIcejumpMenu.QUIT)) {
			this.exitGame();
		} else if (function.equals(ApoIcejumpMenu.START)) {
			this.getGame().setBNetwork(false);
			this.getGame().setGame();
		} else if (function.equals(ApoIcejumpMenu.TUTORIAL)) {
			this.getGame().setTutorial();
		} else if (function.equals(ApoIcejumpMenu.CREDITS)) {
			this.getGame().setCredits();
		} else if (function.equals(ApoIcejumpMenu.SIMULATE)) {
			this.getGame().setSimulate(true);
		} else if (function.equals(ApoIcejumpMenu.LEFT_PLAYERONE)) {
			this.choosePlayerOne -= 1;
			if (this.choosePlayerOne < 0) {
				this.choosePlayerOne = ApoIcejumpConstants.AI_HARD;
			}
			this.loadPlayerAI(ApoIcejumpConstants.PLAYER_ONE, this.choosePlayerOne);
		} else if (function.equals(ApoIcejumpMenu.RIGHT_PLAYERONE)) {
			this.choosePlayerOne += 1;
			if (this.choosePlayerOne > ApoIcejumpConstants.AI_HARD) {
				this.choosePlayerOne = 0;
			}
			this.loadPlayerAI(ApoIcejumpConstants.PLAYER_ONE, this.choosePlayerOne);
		} else if (function.equals(ApoIcejumpMenu.LEFT_PLAYERTWO)) {
			this.choosePlayerTwo -= 1;
			if (this.choosePlayerTwo < 0) {
				this.choosePlayerTwo = ApoIcejumpConstants.AI_HARD;
			}
			this.loadPlayerAI(ApoIcejumpConstants.PLAYER_TWO, this.choosePlayerTwo);
		} else if (function.equals(ApoIcejumpMenu.RIGHT_PLAYERTWO)) {
			this.choosePlayerTwo += 1;
			if (this.choosePlayerTwo > ApoIcejumpConstants.AI_HARD) {
				this.choosePlayerTwo = 0;
			}
			this.loadPlayerAI(ApoIcejumpConstants.PLAYER_TWO, this.choosePlayerTwo);
		} else if (function.equals(ApoIcejumpMenu.LOAD_PLAYERONE)) {
			this.getGame().loadPlayer(ApoIcejumpConstants.PLAYER_ONE, null);
		} else if (function.equals(ApoIcejumpMenu.LOAD_PLAYERTWO)) {
			this.getGame().loadPlayer(ApoIcejumpConstants.PLAYER_TWO, null);
		} else if (function.equals(ApoIcejumpMenu.NETWORK)) {
			this.getGame().setNetwork(true);
		}
	}
	
	@Override
	public void mouseButtonReleased(int x, int y) {
	}

	@Override
	public void think(int delta) {
		if (!this.bStart) {
			this.getGame().thinkFish(delta);
			this.changeY -= delta * ApoIcejumpConstants.CHANGE_MENU_TO_GAME;
			if (this.changeY <= 0) {
				this.changeY = 0;
				this.bStart = true;
			}
			this.getGame().thinkBird(delta);
			this.getGame().thinkWaves(delta);
			this.getGame().thinkBlocks(delta);
		}
		this.time -= delta;
		if (this.time <= 0) {
			this.time = (int)(Math.random() * 1000 + 200);
			int y = (int)(Math.random() * ApoIcejumpConstants.GAME_HEIGHT);
			if (Math.random() * 100 > 50) {
				this.getGame().makeFish(true, y);
			} else {
				this.getGame().makeFish(false, y);
			}
		}
		this.getGame().thinkFish(delta);
		this.thinkButtons(delta);
	}
	
	private void thinkButtons(int delta) {
		for (int i = 0; i < this.getGame().getButtons().length; i++) {
			ApoButton button = this.getGame().getButtons()[i];
			if (button.isBVisible()) {
				for (int j = 0; j < this.getGame().getButtons().length; j++) {
					if ((i != j) && (this.getGame().getButtons()[j].isBVisible()) && (button.intersects(this.getGame().getButtons()[j]))) {
						if (((button.getVecX() > 0) && (this.getGame().getButtons()[j].getVecX() < 0)) ||
							((button.getVecX() < 0) && (this.getGame().getButtons()[j].getVecX() > 0))) {
							this.getGame().getButtons()[j].setVecX(-this.getGame().getButtons()[j].getVecX());
						}
						button.setVecX(-button.getVecX());
						if (button.getX() < this.getGame().getButtons()[j].getX()) {
							if (this.getGame().getButtons()[j].getX() - button.getWidth() >= 0) {
								button.setX(this.getGame().getButtons()[j].getX() - button.getWidth());
							} else {
								button.setX(0);
								this.getGame().getButtons()[j].setX(button.getWidth() + 1);
								this.getGame().getButtons()[j].setVecX(-this.getGame().getButtons()[j].getVecX());
							}
						} else {
							button.setX(this.getGame().getButtons()[j].getX() + button.getWidth() + 1);
						}
						break;
					}
				}
				if ((i < 5) || (i > 10)) {
					button.setX(button.getX() + button.getVecX() * delta);
					if ((button.getX() < 0) || (button.getX() + button.getWidth() > ApoIcejumpConstants.GAME_WIDTH)) {
						button.setVecX(-button.getVecX());
						if (button.getX() < 0) {
							button.setX(0);
						} else if (button.getX() + button.getWidth() > ApoIcejumpConstants.GAME_WIDTH) {
							button.setX(ApoIcejumpConstants.GAME_WIDTH - button.getWidth());
						}
					}
				}
				button.setY(button.getY() + button.getVecY() * delta);
				if (Math.abs(button.getStartY() - button.getY()) > ApoIcejumpConstants.BUTTON_MAX_CHANGE_Y) {
					button.setVecY(-button.getVecY());
				}
			}
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		if (!this.bStart) {
			this.getGame().renderBackground(g, 0, -(int)(ApoIcejumpConstants.GAME_HEIGHT - this.changeY));
			this.getGame().renderBird(g, 0, -(int)(ApoIcejumpConstants.GAME_HEIGHT - this.changeY));
			this.getGame().renderParticle(g, 0, -(int)(ApoIcejumpConstants.GAME_HEIGHT - this.changeY));
			this.getGame().renderPlayer(g, 0, -(int)(ApoIcejumpConstants.GAME_HEIGHT - this.changeY));
			this.getGame().renderSnowflakes(g, 0, -(int)(ApoIcejumpConstants.GAME_HEIGHT - this.changeY));
			this.getGame().renderWavesWithBlocks(g, 0, -(int)(ApoIcejumpConstants.GAME_HEIGHT - this.changeY));
			this.getGame().renderWater(g, 0, (int)this.changeY);
			this.getGame().renderFish(g, 0, (int)this.changeY);
			
			this.renderPlayerLoad(g, 0, (int)this.changeY);
			
			this.getGame().renderButtons(g, 0, (int)this.changeY);			
		} else {
			this.getGame().renderWater(g, 0, 0);
			this.getGame().renderFish(g, 0, 0);
			
			this.renderPlayerLoad(g, 0, 0);
			
			this.getGame().renderButtons(g);
		}
	}
	
	private void renderPlayerLoad(Graphics2D g, int changeX, int changeY) {
		g.drawImage(this.iBackgroundForPlayer, changeX + ApoIcejumpConstants.GAME_WIDTH * 1 / 4 - this.iBackgroundForPlayer.getWidth()/2, changeY + ApoIcejumpConstants.GAME_HEIGHT * 1 / 2 + 95 - this.iBackgroundForPlayer.getHeight(), null);
		g.drawImage(this.iBackgroundForPlayer, changeX + ApoIcejumpConstants.GAME_WIDTH * 3 / 4 - this.iBackgroundForPlayer.getWidth()/2, changeY + ApoIcejumpConstants.GAME_HEIGHT * 1 / 2 + 95 - this.iBackgroundForPlayer.getHeight(), null);
		
		this.getGame().renderPlayer(g, changeX, changeY);
		
		g.setColor(Color.BLACK);
		g.setFont(ApoIcejumpConstants.FONT_PLAYER);
		this.drawString(g, this.getGame().getPlayers()[0].getName(), changeX + ApoIcejumpConstants.GAME_WIDTH * 1 / 4, (int)(changeY + this.getGame().getPlayers()[0].getY() - 10));
		this.drawString(g, "by", changeX + ApoIcejumpConstants.GAME_WIDTH * 1 / 4, (int)(changeY + this.getGame().getPlayers()[0].getY() + this.getGame().getPlayers()[0].getHeight() + 30));

		this.drawString(g, this.getGame().getPlayers()[1].getName(), changeX + ApoIcejumpConstants.GAME_WIDTH * 3 / 4, (int)(changeY + this.getGame().getPlayers()[0].getY() - 10));
		this.drawString(g, "by", changeX + ApoIcejumpConstants.GAME_WIDTH * 3 / 4, (int)(changeY + this.getGame().getPlayers()[0].getY() + this.getGame().getPlayers()[0].getHeight() + 30));
		
		g.setFont(ApoIcejumpConstants.FONT_PLAYER_STATISTICS);
		this.drawString(g, this.getGame().getPlayers()[0].getAuthor(), changeX + ApoIcejumpConstants.GAME_WIDTH * 1 / 4, (int)(changeY + this.getGame().getPlayers()[0].getY() + this.getGame().getPlayers()[0].getHeight() + 65));
		this.drawString(g, this.getGame().getPlayers()[1].getAuthor(), changeX + ApoIcejumpConstants.GAME_WIDTH * 3 / 4, (int)(changeY + this.getGame().getPlayers()[0].getY() + this.getGame().getPlayers()[0].getHeight() + 65));
	}
	
	private void drawString(Graphics2D g, String s, int x, int y) {
		int w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, x - w/2, y);
	}

}
