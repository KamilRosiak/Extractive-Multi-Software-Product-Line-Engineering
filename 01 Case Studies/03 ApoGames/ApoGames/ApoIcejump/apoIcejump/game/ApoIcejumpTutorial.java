package apoIcejump.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.apogames.entity.ApoButton;

import apoIcejump.ApoIcejumpConstants;
import apoIcejump.entity.ApoIcejumpPlayer;

public class ApoIcejumpTutorial extends ApoIcejumpModel {

	public static final String MENU_STRING = "menu";
	public static final String MENU = "options_menu";

	private final int MAX_DELTA = 20;
	
	private boolean bStart;
	
	private float changeY;
	
	private int fishTime;
	
	private int birdTime;
	
	private BufferedImage iStatistics;
	
	private int step;
	
	private String curString;
	
	private int curStringInt;
	
	private int curDelta;
	
	public ApoIcejumpTutorial(ApoIcejumpPanel game) {
		super(game);
	}

	public void start() {
		this.bStart = false;
		this.changeY = -ApoIcejumpConstants.GAME_HEIGHT;
		
		for (int i = 0; i < this.getGame().getButtons().length; i++) {
			if (this.getGame().getButtons()[i].isBVisible()) {
				this.getGame().getButtons()[i].init();
				this.getGame().getButtons()[i].setVecX((float)(Math.random() * 0.05 - 0.025f));
				this.getGame().getButtons()[i].setVecY((float)(Math.random() * 0.01 - 0.005f));
			}
		}
		this.getGame().makeBlocks(System.nanoTime());
		
		this.birdTime = (int)(Math.random() * 9500 + 8000);
		
		for (int i = 0; i < this.getGame().getPlayers().length; i++) {
			this.getGame().getPlayers()[i].init();
		}
		this.getGame().makeNewParticle();
		
		if (this.iStatistics == null) {
			this.iStatistics = this.makeAndGetStatistics(ApoIcejumpConstants.GAME_WIDTH - 10, 80);
		}
		
		this.step = 0;
		this.curString = ApoIcejumpConstants.TUTORIAL_SPEECH[this.step];
		this.curStringInt = 0;
		this.curDelta = 0;
		
		this.getGame().setAIForTutorial();
		
		this.getGame().setCountdownTime(ApoIcejumpGame.COUNTDOWN);
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
	
	@Override
	public void keyPressed(KeyEvent e) {
	}
	
	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			this.getGame().setMenu(false, false);
		} else if (button == KeyEvent.VK_ENTER) {
			if (this.curStringInt >= this.curString.length()) {
				if (this.step == 5) {
					
				} else {
					this.nextStep();
				}
			} else {
				this.curStringInt = this.curString.length();
			}
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoIcejumpTutorial.MENU)) {
			this.getGame().setMenu(false, false);
		}
	}
	
	@Override
	public void mouseButtonReleased(int x, int y) {
		if (this.curStringInt >= this.curString.length()) {
			if (this.step == 5) {
				
			} else {
				this.nextStep();
			}
		} else {
			this.curStringInt = this.curString.length();
		}
	}
	
	public void nextStep() {
		if (this.step + 1 < ApoIcejumpConstants.TUTORIAL_SPEECH.length) {
			this.step++;
			this.curString = ApoIcejumpConstants.TUTORIAL_SPEECH[this.step];
			this.curStringInt = 0;
			this.curDelta = 0;
		} else {
			this.getGame().setMenu(false, false);
		}
	}

	@Override
	public void think(int delta) {
		if (!this.bStart) {
			this.getGame().thinkFish(delta);
			this.changeY += delta * ApoIcejumpConstants.CHANGE_MENU_TO_GAME;
			if (this.changeY >= 0) {
				this.changeY = 0;
				this.bStart = true;
				this.getGame().makeFish(10, true);
				this.fishTime = (int)(Math.random() * 2200 + 8000);
			}
		} else {
			this.fishTime -= delta;
			if (this.fishTime <= 0) {
				this.fishTime = (int)(Math.random() * 2200 + 600);
				int y = ApoIcejumpConstants.GAME_HEIGHT - (int)(Math.random() * ApoIcejumpConstants.WATER_HEIGHT);
				if (Math.random() * 100 > 50) {
					this.getGame().makeFish(true, y);
				} else {
					this.getGame().makeFish(false, y);
				}
			}
			this.getGame().thinkFish(delta);
			
			this.curDelta += delta;
			if (this.curDelta >= this.MAX_DELTA) {
				this.curDelta -= this.MAX_DELTA;
				if (this.curStringInt < this.curString.length()) {
					this.curStringInt++;
					if ((this.step == 5) && (this.curStringInt >= this.curString.length())) {
						
					}
				}
			}
			if ((this.step == 5) && (this.curStringInt >= this.curString.length())) {
				if (this.getGame().getCountdownTime() >= 0) {
					this.getGame().setBCountdown(true);
					this.getGame().setCountdownTime(this.getGame().getCountdownTime() - delta);
				} else {
					this.getGame().setBCountdown(false);
					this.thinkPlayer(delta);
					for (int i = 0; i < this.getGame().getPlayers().length; i++) {
						if (!this.getGame().getPlayers()[i].isBVisible()) {
							this.nextStep();			
						}
					}
				}
			}
		}
		this.birdTime -= delta;
		if (this.birdTime <= 0) {
			this.birdTime = (int)(Math.random() * 8500 + 7000);
			this.getGame().makeBird(1, false);
		}
		this.getGame().thinkBird(delta);
		for (int i = 0; i < this.getGame().getButtons().length; i++) {
			ApoButton button = this.getGame().getButtons()[i];
			if (button.isBVisible()) {
				button.setX(button.getX() + button.getVecX() * delta);
				if ((button.getX() < 0) || (button.getX() + button.getWidth() > ApoIcejumpConstants.GAME_WIDTH)) {
					button.setVecX(-button.getVecX());
					if (button.getX() < 0) {
						button.setX(0);
					} else if (button.getX() + button.getWidth() > ApoIcejumpConstants.GAME_WIDTH) {
						button.setX(ApoIcejumpConstants.GAME_WIDTH - button.getWidth());
					}
				}
				button.setY(button.getY() + button.getVecY() * delta);
				if (Math.abs(button.getStartY() - button.getY()) > ApoIcejumpConstants.BUTTON_MAX_CHANGE_Y) {
					button.setVecY(-button.getVecY());
				}
			}
		}
		this.getGame().thinkWaves(delta);
		this.getGame().thinkBlocks(delta);
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
		this.getGame().thinkPlayers(delta, true);
	}
	
	@Override
	public void render(Graphics2D g) {
		if (this.bStart) {
			this.getGame().renderBackground(g, 0, 0);
			this.getGame().renderBird(g, 0, 0);
			this.renderStatistics(g, 0, 0);
			this.getGame().renderParticle(g, 0, 0);
			this.getGame().renderPlayer(g, 0, 0);
			this.getGame().renderSnowflakes(g, 0, 0);
			this.getGame().renderWavesWithBlocks(g, 0, 0);
			this.getGame().renderWater(g, 0, (int)(ApoIcejumpConstants.GAME_HEIGHT));
			this.getGame().renderFish(g, 0, 0);
			this.getGame().renderCountdown(g, 0, 0);
			this.getGame().renderButtons(g);
		} else {
			this.getGame().renderBackground(g, 0, (int)this.changeY);
			this.renderStatistics(g, 0, (int)this.changeY);
			this.getGame().renderBird(g, 0, (int)this.changeY);
			this.getGame().renderSnowflakes(g, 0, (int)this.changeY);
			this.getGame().renderWavesWithBlocks(g, 0, (int)this.changeY);
			this.getGame().renderPlayer(g, 0, (int)this.changeY);
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
		g.setColor(Color.WHITE);
		g.setFont(ApoIcejumpConstants.FONT_TUTORIAL);
		this.drawSpeech(g, this.curString.substring(0, this.curStringInt), 10, 5 + 22, ApoIcejumpConstants.GAME_WIDTH - 20);
	}

	private void drawSpeech(Graphics2D g, String s, int x, int y, int width) {
		int curHeight = 0;
		int[] maxLength = new int[] {width - 4, width - 4, width - 4, width - 4};
		ArrayList<String> strings = new ArrayList<String>();
		strings.add(s);
		int cur = 0;
		int w = g.getFontMetrics().stringWidth(strings.get(cur));
		if (w > maxLength[curHeight]) {
			int curPos = strings.get(cur).indexOf(" ");
			while ((curPos > -1) && (g.getFontMetrics().stringWidth(strings.get(cur).substring(0, curPos)) < maxLength[curHeight])) {
				int nextPos = strings.get(cur).indexOf(" ", curPos + 1);
				if (nextPos != -1) {
					if (g.getFontMetrics().stringWidth(strings.get(cur).substring(0, nextPos)) >= maxLength[curHeight]) {
						String curString = strings.get(cur);
						strings.set(cur, curString.substring(0, curPos));
						cur++;
						if (curHeight == 0) {
							curHeight++;
						}
						strings.add(curString.substring(curPos + 1));
						curPos = strings.get(cur).indexOf(" ");
					} else {
						curPos = nextPos;
					}
				} else {
					String curString = strings.get(cur);
					if (g.getFontMetrics().stringWidth( curString ) > maxLength[curHeight]) {
						strings.set( cur, curString.substring(0, curPos) );
						cur++;
						strings.add( curString.substring(curPos + 1));
					}
					break;
				}
			}
		}
		int h = g.getFontMetrics().getHeight();
		for ( int i = 0; i < strings.size(); i++ ) {
			g.drawString(strings.get(i), x, y);
			y += h;
		}
	}
	
}
