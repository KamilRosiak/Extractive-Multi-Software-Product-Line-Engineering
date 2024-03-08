package apoIcejump.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.apogames.entity.ApoButton;
import org.apogames.help.ApoHelp;

import apoIcejump.ApoIcejumpConstants;
import apoIcejump.entity.ApoIcejumpPlayer;

public class ApoIcejumpGame extends ApoIcejumpModel {
	
	public static final String MENU_STRING = "menu";
	public static final String MENU = "game_menu";
	
	public static final int COUNTDOWN = 3000;
	
	private boolean bNetwork;
	
	private boolean bStart;
	
	private float changeY;
	
	private int fishTime;
	
	private int birdTime;
	
	private int gameTime;
	
	private int goodieTime;
	
	private boolean bRestart;
	
	private BufferedImage iStatistics;
	
	private long lastTime;
	
	private Random random;
	
	public ApoIcejumpGame(ApoIcejumpPanel game) {
		super(game);
	}

	public void start(boolean bRestart) {
		this.start(bRestart, -1);
	}
	
	public void start(boolean bRestart, long time) {
		this.bRestart = bRestart;
		
		if (!bRestart) {
			this.bStart = false;
			this.getGame().setBCountdown(false);
			this.changeY = -ApoIcejumpConstants.GAME_HEIGHT;
			
			for (int i = 0; i < this.getGame().getButtons().length; i++) {
				if (this.getGame().getButtons()[i].isBVisible()) {
					this.getGame().getButtons()[i].init();
					this.getGame().getButtons()[i].setVecX((float)(Math.random() * 0.05 - 0.025f));
					this.getGame().getButtons()[i].setVecY((float)(Math.random() * 0.01 - 0.005f));
				}
			}
		} else {
			this.bStart = true;
			this.getGame().setBCountdown(false);
		}

		if (time < 0) {
			this.lastTime = System.nanoTime();
		} else {
			this.lastTime = time;		
		}
		this.random = new Random(this.lastTime);
		this.getGame().makeBlocks(this.lastTime);
		this.gameTime = 0;
		this.getGame().setCountdownTime(ApoIcejumpGame.COUNTDOWN);
		
		if (this.iStatistics == null) {
			this.iStatistics = this.makeAndGetStatistics(ApoIcejumpConstants.GAME_WIDTH - 10, 80);
		}
		
		if (!bRestart) {
			this.goodieTime = (int)(this.random.nextInt(10000) + 3000);
			this.birdTime = (int)(this.random.nextInt(9500) + 8000);
			this.getGame().getBirds().clear();
		}
		for (int i = 0; i < this.getGame().getPlayers().length; i++) {
			this.getGame().getPlayers()[i].init();
		}
		this.getGame().makeNewParticle();
	}
	
	public boolean isNetwork() {
		return this.bNetwork;
	}

	public void setNetwork(boolean network) {
		this.bNetwork = network;
	}

	public int getTime() {
		return this.gameTime;
	}
	
	private BufferedImage makeAndGetStatistics(int width, int height) {
		BufferedImage iStatistics = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = (Graphics2D)iStatistics.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(new Color(0, 0, 0, 150));
		g.fillRoundRect(0, 0, width - 1, height - 1, 20, 20);
		g.setColor(Color.WHITE);
		g.drawRoundRect(0, 0, width - 1, height - 1, 20, 20);
		
		g.dispose();
		return iStatistics;
	}
	
	public void keyPressed(KeyEvent e) {
		
	}
	
	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			this.getGame().setMenu(false, false);
		} else if (button == KeyEvent.VK_R) {
			this.start(true);
		} else if (button == KeyEvent.VK_B) {
			ApoIcejumpConstants.ACCELERATION = !ApoIcejumpConstants.ACCELERATION;
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoIcejumpGame.MENU)) {
			this.getGame().setMenu(false, false);
		}
	}
	
	@Override
	public void mouseButtonReleased(int x, int y) {
	}
	
	@Override
	public void think(int delta) {
		if (!this.bStart) {
			this.getGame().thinkFish(delta);
			this.changeY += delta * ApoIcejumpConstants.CHANGE_MENU_TO_GAME;
			if (this.changeY >= 0) {
				this.changeY = 0;
				this.bStart = true;
				this.getGame().setBCountdown(true);
				this.getGame().setCountdownTime(ApoIcejumpGame.COUNTDOWN);
				if (!this.bRestart) {
					this.getGame().makeFish(10, true);
					this.fishTime = (int)(Math.random() * 2200 + 8000);
				}
			}
		} else {
			if (this.getGame().isBCountdown()) {
				this.getGame().setCountdownTime(this.getGame().getCountdownTime() - delta);
				if (this.getGame().getCountdownTime() <= 0) {
					this.getGame().setCountdownTime(0);
					this.getGame().setBCountdown(false);
				}
				return;
			} else {
				this.gameTime += delta;
				this.thinkPlayer(delta);
				if (this.getGame().thinkPlayers(delta, false)) {
					return;
				}
				this.goodieTime -= delta;
				if (this.goodieTime <= 0) {
					this.goodieTime = (int)(this.random.nextInt(10000) + 3000);
					this.getGame().makeGoodie();
				}
				this.getGame().thinkGoodies(delta);
			}
			this.fishTime -= delta;
			if (this.fishTime <= 0) {
				this.fishTime = (int)(Math.random() * 2500 + 500);
				int y = ApoIcejumpConstants.GAME_HEIGHT - (int)(Math.random() * ApoIcejumpConstants.WATER_HEIGHT);
				if (Math.random() * 100 > 50) {
					this.getGame().makeFish(true, y);
				} else {
					this.getGame().makeFish(false, y);
				}
			}
			this.getGame().thinkFish(delta);
			
			this.birdTime -= delta;
			if (this.birdTime <= 0) {
				this.birdTime = (int)(this.random.nextInt(8500) + 7000);
				this.getGame().makeBird(1, false);
			}
			this.getGame().thinkBird(delta);
			this.getGame().thinkBlocks(delta);
		}
		for (int i = 0; i < this.getGame().getButtons().length; i++) {
			ApoButton button = this.getGame().getButtons()[i];
			if (button.isBVisible()) {
				button.setX(button.getX() + button.getVecX() * delta);
				if ((button.getX() < 0) || (button.getX() + button.getWidth() > ApoIcejumpConstants.GAME_WIDTH)) {
					button.setVecX(-button.getVecX());
				}
				if (button.getX() < 0) {
					button.setX(0);
				} else if (button.getX() + button.getWidth() > ApoIcejumpConstants.GAME_WIDTH) {
					button.setX(ApoIcejumpConstants.GAME_WIDTH - button.getWidth());
				}
				button.setY(button.getY() + button.getVecY() * delta);
				if (Math.abs(button.getStartY() - button.getY()) > ApoIcejumpConstants.BUTTON_MAX_CHANGE_Y) {
					button.setVecY(-button.getVecY());
				}
			}
		}
		this.getGame().thinkWaves(delta);
		this.getGame().thinkParticles(delta);
	}
	
	private void thinkPlayer(int delta) {
		ApoIcejumpPlayer playerOne = this.getGame().getPlayers()[0];
		if (playerOne.getAI() == null) {
			if (this.getGame().getKeys()[KeyEvent.VK_LEFT]) {
				playerOne.setVecX(-ApoIcejumpConstants.PLAYER_MAX_VEC_X);
			} else if (this.getGame().getKeys()[KeyEvent.VK_RIGHT]) {
				playerOne.setVecX(ApoIcejumpConstants.PLAYER_MAX_VEC_X);				
			} else {
				playerOne.setVecX(0);
			}
		}
		ApoIcejumpPlayer playerTwo = this.getGame().getPlayers()[1];
		if (playerTwo.getAI() == null) {
			if (this.getGame().getKeys()[KeyEvent.VK_A]) {
				playerTwo.setVecX(-ApoIcejumpConstants.PLAYER_MAX_VEC_X);
			} else if (this.getGame().getKeys()[KeyEvent.VK_D]) {
				playerTwo.setVecX(ApoIcejumpConstants.PLAYER_MAX_VEC_X);				
			} else {
				playerTwo.setVecX(0);
			}
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		if (this.bStart) {
			this.getGame().renderBackground(g, 0, 0);
			this.getGame().renderBird(g, 0, 0);
			this.renderStatistics(g, 0, 0);
			this.getGame().renderParticle(g, 0, 0);
			this.getGame().renderPlayer(g, 0, 0);
			this.getGame().renderGoodies(g, 0, 0);
			this.getGame().renderSnowflakes(g, 0, 0);
			this.getGame().renderWavesWithBlocks(g, 0, 0);
			this.getGame().renderWater(g, 0, (int)(ApoIcejumpConstants.GAME_HEIGHT));
			this.getGame().renderFish(g, 0, 0);
			this.getGame().renderCountdown(g, 0, 0);
			this.getGame().renderButtons(g);
		} else {
			this.getGame().renderBackground(g, 0, (int)this.changeY);
			this.renderStatistics(g, 0, (int)this.changeY);
			this.getGame().renderPlayer(g, 0, (int)this.changeY);
			this.getGame().renderSnowflakes(g, 0, (int)this.changeY);
			this.getGame().renderWavesWithBlocks(g, 0, (int)this.changeY);
			this.getGame().renderBird(g, 0, (int)this.changeY);
			this.getGame().renderCountdown(g, 0, (int)this.changeY);
			this.getGame().renderWater(g, 0, (int)(ApoIcejumpConstants.GAME_HEIGHT + this.changeY));
			this.getGame().renderFish(g, 0, (int)(ApoIcejumpConstants.GAME_HEIGHT + this.changeY));
		}
	}
	
	private void renderStatistics(Graphics2D g, int changeX, int changeY) {
		if (this.iStatistics == null) {
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRoundRect(5 + changeX, 5 + changeY, ApoIcejumpConstants.GAME_WIDTH - 10, 80, 20, 20);
			g.setColor(Color.WHITE);
			g.drawRoundRect(5 + changeX, 5 + changeY, ApoIcejumpConstants.GAME_WIDTH - 10, 80, 20, 20);
		} else {
			g.drawImage(this.iStatistics, 5 + changeX, 5 + changeY, null);
		}
		g.setFont(ApoIcejumpConstants.FONT_PLAYER);
		g.setColor(Color.WHITE);
		
		String t = "Time";
		this.drawString(g, t, ApoIcejumpConstants.GAME_WIDTH/2 + changeX, 30 + changeY);
		
		if (this.gameTime > ApoIcejumpConstants.GAME_SUDDEN_DEATH_TIME) {
			g.setColor(new Color(255, 100, 100));
		}
		String time = ApoHelp.getTimeToDraw(this.gameTime);
		this.drawString(g, time, ApoIcejumpConstants.GAME_WIDTH/2 + changeX, 55 + changeY);
		if (this.gameTime > ApoIcejumpConstants.GAME_SUDDEN_DEATH_TIME) {
			this.drawString(g, "sudden death mode", ApoIcejumpConstants.GAME_WIDTH/2 + changeX, 80 + changeY);			
		}
		
		g.setFont(ApoIcejumpConstants.FONT_PLAYER_STATISTICS);
		g.setColor(Color.WHITE);
		
		String name = this.getGame().getPlayers()[0].getName() + " by " + this.getGame().getPlayers()[0].getAuthor();
		this.drawString(g, name, ApoIcejumpConstants.GAME_WIDTH * 1 / 5, 30 + changeY);
		
		int picX = (int)(ApoIcejumpConstants.GAME_WIDTH * 1 / 5 - this.getGame().getPlayers()[0].getWidth()/2);
		this.getGame().getPlayers()[0].renderPlayer(g, picX, 40, changeX, changeY);
		
		if (this.getGame().getPlayers()[0].getAI() == null) {
			g.drawImage(this.getGame().getIKeyImages()[0], picX - 10 - this.getGame().getIKeyImages()[0].getWidth(), 40 + changeY, null);
			g.drawImage(this.getGame().getIKeyImages()[1], picX + 10 + (int)this.getGame().getPlayers()[0].getWidth(), 40 + changeY, null);
		}
		
		g.setColor(Color.WHITE);
		
		name = this.getGame().getPlayers()[1].getName() + " by " + this.getGame().getPlayers()[1].getAuthor();
		this.drawString(g, name, ApoIcejumpConstants.GAME_WIDTH * 4 / 5, 30 + changeY);
		
		picX = (int)(ApoIcejumpConstants.GAME_WIDTH * 4 / 5 - this.getGame().getPlayers()[1].getWidth()/2);
		this.getGame().getPlayers()[1].renderPlayer(g, (int)(ApoIcejumpConstants.GAME_WIDTH * 4 / 5 - this.getGame().getPlayers()[1].getWidth()/2), 40, changeX, changeY);
		if (this.getGame().getPlayers()[1].getAI() == null) {
			g.drawImage(this.getGame().getIKeyImages()[2], picX - 10 - this.getGame().getIKeyImages()[0].getWidth(), 40 + changeY, null);
			g.drawImage(this.getGame().getIKeyImages()[3], picX + 10 + (int)this.getGame().getPlayers()[0].getWidth(), 40 + changeY, null);
		}
		
		
		if (ApoIcejumpConstants.ACCELERATION) {
			g.setColor(Color.BLACK);
			this.drawString(g, "acceleration mode", ApoIcejumpConstants.GAME_WIDTH / 2 + changeX, 30 + changeY + this.iStatistics.getHeight());			
		}
	}
	
	private void drawString(Graphics2D g, String s, int x, int y) {
		int w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, x - w/2, y);
	}

}
