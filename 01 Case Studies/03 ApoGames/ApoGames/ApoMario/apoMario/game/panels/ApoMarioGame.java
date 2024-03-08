package apoMario.game.panels;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import org.apogames.entity.ApoEntity;
import org.apogames.help.ApoHelp;

import apoMario.ApoMarioConstants;
import apoMario.ApoMarioImageContainer;
import apoMario.entity.ApoMarioPlayer;
import apoMario.game.ApoMarioPanel;

/**
 * Spielpanelklasse<br />
 * Klasse, die anzeigt wird, wenn ein Level gestartet wurde und sich um das HUD kümmert<br />
 * @author Dirk Aporius
 */
public class ApoMarioGame extends ApoMarioModel {

	public static final String FUNCTION_SPEED_LEFT = "speedLeft";
	public static final String FUNCTION_SPEED_RIGHT = "speedRight";
	
	public static final int PLAYER_ONE = 0;
	public static final int PLAYER_TWO = 1;
	
	private int playerSelectCamera;
	
	private ApoEntity playerCamerEntity;
	
	private BufferedImage iStats;
	
	public ApoMarioGame(ApoMarioPanel game) {
		super(game);
		
		this.playerSelectCamera = ApoMarioGame.PLAYER_ONE;
		this.setPlayerSelectCamera(ApoMarioGame.PLAYER_ONE);
		if (this.iStats == null) {
			this.iStats = this.getIStats();
		}
	}

	@Override
	public void init() {
		
	}
	
	private BufferedImage getIStats() {
		int height = 60 + 10 * ApoMarioConstants.APP_SIZE;
		BufferedImage iStats = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(ApoMarioConstants.GAME_WIDTH - 10, height, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = (Graphics2D)iStats.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(new Color(198, 215, 255, 180));
		g.fillRoundRect(0, 0, iStats.getWidth() - 1, iStats.getHeight() - 1, 15, 15);
		g.setColor(Color.BLACK);
		g.drawRoundRect(0, 0, iStats.getWidth() - 1, iStats.getHeight() - 1, 15, 15);
		
		g.dispose();
		return iStats;
	}

	public int getPlayerSelectCamera() {
		return this.playerSelectCamera;
	}

	public void setPlayerSelectCamera(int player) {
		if (this.getGame().getLevel().isBReplay()) {
			if ((this.getGame().getLevel().getPlayers().get(player).isBVisible())) {
				if (((player == ApoMarioConstants.PLAYER_TWO) && (this.getGame().getLevel().getReplay().getPlayerTwo() != null)) || 
					 (player == ApoMarioConstants.PLAYER_ONE)) {
					this.playerSelectCamera = player;
					this.playerCamerEntity = new ApoEntity(null, -1, -1, 8, 50);
				}
			}
		} else if ((this.getGame().getLevel().getPlayers().get(player).isBVisible()) && (this.getGame().getLevel().getPlayers().get(this.playerSelectCamera).getAi() != null)) {
			this.playerSelectCamera = player;
			this.playerCamerEntity = new ApoEntity(null, -1, -1, 8, 50);
		}
	}
	
	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_ESCAPE) {
			if (this.getGame().isBEditor()) {
				this.getGame().setEditor();
			} else {
				this.getGame().setMenu();
			}
		} else if (button == KeyEvent.VK_Q) {
			this.getGame().addSpeed(-1);
		} else if (button == KeyEvent.VK_E) {
			this.getGame().addSpeed(1);
		} else if (button == KeyEvent.VK_R) {
			this.getGame().restartLevel();
		} else if (button == KeyEvent.VK_N) {
			this.getGame().newLevel();
		} else if (button == KeyEvent.VK_M) {
			this.getGame().newLevel(0, 0);
		} else if (button == KeyEvent.VK_P) {
			this.getGame().setBPause(!this.getGame().isBPause());
		} else if (button == KeyEvent.VK_C) {
			if (this.playerSelectCamera == ApoMarioGame.PLAYER_ONE) {
				this.setPlayerSelectCamera(ApoMarioGame.PLAYER_TWO);
			} else {
				this.setPlayerSelectCamera(ApoMarioGame.PLAYER_ONE);
			}
		} else {
			if ((this.getGame().getLevel() != null) && (this.getGame().getLevel().getPlayers() != null)) {
				for (int player = 0; player < this.getGame().getLevel().getPlayers().size(); player++) {
					if (this.getGame().getLevel().getPlayers().get(player).getAi() == null) {
						if ((button == KeyEvent.VK_S) || (button == KeyEvent.VK_SPACE)) {
							this.getGame().getKeys()[KeyEvent.VK_S] = false;
							this.getGame().getKeys()[KeyEvent.VK_SPACE] = false;
							this.getGame().setBJump(false);
							this.getGame().getLevel().getPlayers().get(player).setBJump(false);
						} else if (button == KeyEvent.VK_DOWN) {
							this.getGame().getLevel().getPlayers().get(player).setBDown(false);
						}
					}
				}
			}
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoMarioGame.FUNCTION_SPEED_LEFT)) {
			this.getGame().addSpeed(-1);
		} else if (function.equals(ApoMarioGame.FUNCTION_SPEED_RIGHT)) {
			this.getGame().addSpeed(1);
		}
	}

	@Override
	public void mouseButtonReleased(int x, int y) {
		if (this.getGame().getLevel().isBStop()) {
			this.getGame().getLevel().setBStop(false);
		}
		if (this.playerCamerEntity != null) {
			if (this.playerCamerEntity.intersects(x, y)) {
				if (this.playerSelectCamera == ApoMarioGame.PLAYER_ONE) {
					this.setPlayerSelectCamera(ApoMarioGame.PLAYER_TWO);
				} else {
					this.setPlayerSelectCamera(ApoMarioGame.PLAYER_ONE);
				}
			}
		}
	}
	
	@Override
	public boolean mouseDragged(int x, int y) {
		return false;
	}

	@Override
	public boolean mouseMoved(int x, int y) {
		return false;
	}

	@Override
	public boolean mousePressed(int x, int y, boolean bRight) {
		return false;
	}

	@Override
	public synchronized void think(int delta) {
		if (!this.getGame().isBPause()) {
			if (this.getGame().getLevel() != null) {
				int count = 1;
				if (this.getGame().getSpeed() >= ApoMarioConstants.TIMES_NORMAL) {
					if (this.getGame().getSpeed() == ApoMarioConstants.TIMES_FASTER_3) {
						count = 3;
					} else if (this.getGame().getSpeed() == ApoMarioConstants.TIMES_FASTER_10) {
						count = 10;
					}
					for (int s = 0; s < count; s++) {
						if (this.getGame().getLevel().isBAnalysis()) {
							this.thinkLevel(delta);
						} else {
							break;
						}
					}
				} else {
					this.getGame().setSpeedCounter(this.getGame().getSpeedCounter() + delta);
					if ((this.getGame().getSpeed() == ApoMarioConstants.TIMES_SLOWER_5) && (this.getGame().getSpeedCounter() >= 5 * delta)) {
						this.thinkLevel(delta);
						this.getGame().setSpeedCounter(0);
					} else if ((this.getGame().getSpeed() == ApoMarioConstants.TIMES_SLOWER_50) && (this.getGame().getSpeedCounter() >= 50 * delta)) {
						this.thinkLevel(delta);
						this.getGame().setSpeedCounter(0);
					}
				}
				this.changeCamera();
			}
			for (int i = 0; i < this.getGame().getLevel().getPlayers().size(); i++) {
				if (i == this.playerSelectCamera) {
					if (!this.getGame().getLevel().getPlayers().get(i).isBVisible()) {
						this.playerSelectCamera = 0;
					}
					continue;
				}
				if ((this.getGame().getLevel().getPlayers().get(i).isBVisible()) && (this.playerCamerEntity != null)) {
					double playerX = this.getGame().getLevel().getPlayers().get(i).getRec().getX();
					if (playerX < this.getGame().getChangeX()) {
						int height = (int)(50 - Math.min(30, (this.getGame().getChangeX() - playerX) / 5));
						int y = (int)(this.getGame().getLevel().getPlayers().get(i).getRec().getY() + this.getGame().getLevel().getPlayers().get(i).getRec().getHeight());
						int x = 3;
						this.playerCamerEntity.setX(x);
						this.playerCamerEntity.setY(y);
						this.playerCamerEntity.setHeight(height);
					} else if (playerX > this.getGame().getChangeX() + ApoMarioConstants.GAME_WIDTH) {
						int height = (int)(50 - Math.min(30, (playerX - this.getGame().getChangeX() - ApoMarioConstants.GAME_WIDTH) / 5));
						int y = (int)(this.getGame().getLevel().getPlayers().get(i).getRec().getY() + this.getGame().getLevel().getPlayers().get(i).getRec().getHeight());
						int x = (int)(ApoMarioConstants.GAME_WIDTH - 3 - this.playerCamerEntity.getWidth());
						this.playerCamerEntity.setX(x);
						this.playerCamerEntity.setY(y);
						this.playerCamerEntity.setHeight(height);
					} else {
						this.playerCamerEntity.setX(-1);
					}
				} else {
					if (this.playerCamerEntity != null) {
						this.playerCamerEntity.setX(-1);
					}
				}
			}
			if (this.getGame().getDebugFrame() != null) {
				this.getGame().getDebugFrame().refresh();
			}
		}
	}
	
	public void changeCamera() {
		float x = (float)(this.getGame().getLevel().getPlayers().get(this.playerSelectCamera).getRec().getX() * ApoMarioConstants.APP_SIZE);
		if (x > 0) {
			float changeX = x - ApoMarioConstants.GAME_WIDTH/2;
			if (changeX < 0) {
				changeX = 0;
			} else if (changeX + ApoMarioConstants.GAME_WIDTH >= this.getGame().getLevel().getWidth() * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE) {
				changeX = this.getGame().getLevel().getWidth() * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE - ApoMarioConstants.GAME_WIDTH;
			}
			if (changeX != this.getGame().getChangeX()) {
				this.getGame().setChangeX(changeX / ApoMarioConstants.APP_SIZE);
			}
		}
	}
	
	protected synchronized void thinkLevel(int delta) {
		if (!this.getGame().getLevel().isBReplay()) {
			this.playerHumanControl();
		}
		int change = delta;
		for (int i = 0; i < delta; i += change) {
			this.getGame().getLevel().think(change);					
		}
	}
	
	private synchronized void playerHumanControl() {
		for (int player = 0; player < this.getGame().getLevel().getPlayers().size(); player++) {
			if ((this.getGame().getLevel().getPlayers().get(player) != null) && (this.getGame().getLevel().getPlayers().get(player).getAi() == null) &&
				(!this.getGame().getLevel().getPlayers().get(player).isBDie()) && (this.getGame().getLevel().getPlayers().get(player).isBVisible())) {
				if ((!this.getGame().isBJump()) && (!this.getGame().isBJump())) {
					if ((this.getGame().getKeys()[KeyEvent.VK_S]) || (this.getGame().getKeys()[KeyEvent.VK_SPACE])) {
						if (!this.getGame().getLevel().getPlayers().get(player).isBJump()) {
							this.getGame().getLevel().getPlayers().get(player).setBJump(true);
							this.getGame().setBJump(true);
						}
					}
				} else {
					if ((this.getGame().getLevel().getPlayers().get(player).isBJump())) {
						if ((!this.getGame().getKeys()[KeyEvent.VK_S]) && (!this.getGame().getKeys()[KeyEvent.VK_SPACE])) {
							this.getGame().getLevel().getPlayers().get(player).setBJump(false);
						}
					}
				}
				if ((this.getGame().getKeys()[KeyEvent.VK_A]) || (this.getGame().getKeys()[KeyEvent.VK_SHIFT])) {
					this.getGame().getLevel().getPlayers().get(player).setBSpeed(true);
				} else {
					this.getGame().getLevel().getPlayers().get(player).setBSpeed(false);				
				}
				if (this.getGame().getKeys()[KeyEvent.VK_DOWN]) {
					this.getGame().getLevel().getPlayers().get(player).setBDown(true);
				}
				if (this.getGame().getKeys()[KeyEvent.VK_LEFT]) {
					if ((this.getGame().getKeys()[KeyEvent.VK_A]) || (this.getGame().getKeys()[KeyEvent.VK_SHIFT])) {
						this.getGame().getLevel().getPlayers().get(player).setGoalVecX(-ApoMarioConstants.PLAYER_MAX_VEC_X_FAST);					
					} else {
						this.getGame().getLevel().getPlayers().get(player).setGoalVecX(-ApoMarioConstants.PLAYER_MAX_VEC_X_NORMAL);
					}
				} else if (this.getGame().getKeys()[KeyEvent.VK_RIGHT]) {
					if ((this.getGame().getKeys()[KeyEvent.VK_A]) || (this.getGame().getKeys()[KeyEvent.VK_SHIFT])) {
						this.getGame().getLevel().getPlayers().get(player).setGoalVecX(ApoMarioConstants.PLAYER_MAX_VEC_X_FAST);					
					} else {
						this.getGame().getLevel().getPlayers().get(player).setGoalVecX(ApoMarioConstants.PLAYER_MAX_VEC_X_NORMAL);
					}
				} else {
					this.getGame().getLevel().getPlayers().get(player).setGoalVecX(0);				
				}
			}
		}
	}

	@Override
	public void render(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		if (this.getGame().getLevel() != null) {
			this.getGame().getLevel().render(g, (int)this.getGame().getChangeX(), (int)this.getGame().getChangeY());
		}
		this.renderPlayerBar(g);
		this.renderStatistics(g);
		this.getGame().renderButtons(g);
	}
	
	private void renderStatistics(Graphics2D g) {
		if (this.iStats != null) {
			g.drawImage(this.iStats, 5, 5, null);
		}
		
		g.setFont(ApoMarioConstants.FONT_STATISTICS);
		g.setColor(Color.BLACK);
		this.renderStatisticsPlayer(g, 0, this.getGame().getLevel().getPlayers().get(0), true);
		
		int startY = 7 + ApoMarioConstants.TILE_SIZE/2 + 3 * ApoMarioConstants.APP_SIZE;
		int addY = g.getFontMetrics().getHeight() - 2 * ApoMarioConstants.APP_SIZE;
		String s = "Time: "+ApoHelp.getTimeToDraw(this.getGame().getLevel().getTime());
		int w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoMarioConstants.GAME_WIDTH/2 - w/2, startY);

		g.setFont(ApoMarioConstants.FONT_FPS);
		addY = g.getFontMetrics().getHeight() - 3;
		float maxWidth = -1;
		for (int i = 0; i < this.getGame().getLevel().getPlayers().size(); i++) {
			ApoMarioPlayer player = this.getGame().getLevel().getPlayers().get(i);
			if (player.isBVisible()) {
				if (maxWidth < player.getX()) {
					maxWidth = player.getX();
				}
			}
		}
		s = "Dif: " + String.valueOf(this.getGame().getLevel().getDifficulty());
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoMarioConstants.GAME_WIDTH/2 - w/2, startY + addY + 5);		
		
		s = "Width: "+String.valueOf((int)(maxWidth/(ApoMarioConstants.TILE_SIZE)))+" / "+String.valueOf(this.getGame().getLevel().getWidth());
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoMarioConstants.GAME_WIDTH/2 - w/2, startY + 2 * addY + 5);
		
		s = "RandomLevel: "+this.getGame().getLevel().getLevelInt();
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoMarioConstants.GAME_WIDTH/2 - w/2, startY + 3 * addY + 5);
		
		s = "Speed: "+ApoMarioConstants.TIMES_SPEED[this.getGame().getSpeed()];
		w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, ApoMarioConstants.GAME_WIDTH/2 - w/2, startY + 4 * addY + 5);

		if (!this.isOnePlayer()) {
			g.setFont(ApoMarioConstants.FONT_STATISTICS);
			g.setColor(Color.BLACK);
			this.renderStatisticsPlayer(g, ApoMarioConstants.GAME_WIDTH, this.getGame().getLevel().getPlayers().get(1), false);
			
			if (this.playerSelectCamera >= 0) {
				int y = (int)(this.getGame().getLevel().getPlayers().get(this.playerSelectCamera).getRec().y - this.getGame().getChangeY()) * ApoMarioConstants.APP_SIZE - 20 - ApoMarioImageContainer.CAMERA.getHeight();
				int x = (int)(this.getGame().getLevel().getPlayers().get(this.playerSelectCamera).getXMiddle() - this.getGame().getChangeX()) * ApoMarioConstants.APP_SIZE - ApoMarioImageContainer.CAMERA.getWidth()/2;
				g.drawImage(ApoMarioImageContainer.CAMERA, x, y, null);
			}
		}
	}
	
	private boolean isOnePlayer() {
		if ((!this.getGame().getLevel().isBReplay()) && (this.getGame().getLevel().getPlayers().get(1).getAi() == null))  {
			return true;
		}
		if  ((this.getGame().getLevel().isBReplay()) && (Math.abs(this.getGame().getLevel().getReplay().getPlayerTwo().getMaxSteps() - this.getGame().getLevel().getReplay().getPlayerOne().getMaxSteps()) >= 2)) {
			return true;
		}
		return false;
	}
	
	private void renderStatisticsPlayer(Graphics2D g, int x, ApoMarioPlayer player, boolean bLeft) {
		// Coins
		int startY = 7 + ApoMarioConstants.TILE_SIZE/2 + 6 + 1 * ApoMarioConstants.APP_SIZE;
		int addY = g.getFontMetrics().getHeight();
		int y = startY;
		BufferedImage iCoin = ApoMarioImageContainer.TILES_LEVEL.getSubimage(0, 2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
		if (bLeft) {
			g.drawImage(iCoin, 10 + x, 7, null);
			g.drawString(String.valueOf(player.getCoins()), x + 10 * ApoMarioConstants.SIZE + 5 + iCoin.getWidth(), y);
		} else {
			g.drawImage(iCoin, x - 10 - iCoin.getWidth(), 7, null);
			String s = String.valueOf(player.getCoins());
			int w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, x - 10 - iCoin.getWidth() - w - 5, y);			
		}
		
		y += addY + 2 * ApoMarioConstants.APP_SIZE;
		String s = "Name: human";
		if (player.getTeamName() != null) {
			s = "Name: " + player.getTeamName();
		}
		if (bLeft) {
			g.drawString(s, 10 + x, y);
		} else {
			int w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, x - 10 - w, y);			
		}
		y = y + addY;
		s = "Points: " + player.getPoints();
		if (bLeft) {
			g.drawString(s, 10 + x, y);
		} else {
			int w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, x - 10 - w, y);			
		}
	}
	
	private void renderPlayerBar(Graphics2D g) {
//		if ((this.playerCamerEntity != null) && (this.playerCamerEntity.getX() >= 0)) {
//			int x = (int)(this.playerCamerEntity.getX());
//			int width = (int)(this.playerCamerEntity.getWidth());
//			int y = (int)(this.playerCamerEntity.getY());
//			int height = (int)(this.playerCamerEntity.getHeight());
//			g.setColor(Color.RED);
//			g.fillRect(x * ApoMarioConstants.APP_SIZE, (y - height) * ApoMarioConstants.APP_SIZE, width * ApoMarioConstants.APP_SIZE, height * ApoMarioConstants.APP_SIZE);
//			g.setColor(Color.BLACK);
//			g.drawRect(x * ApoMarioConstants.APP_SIZE, (y - height) * ApoMarioConstants.APP_SIZE, width * ApoMarioConstants.APP_SIZE, height * ApoMarioConstants.APP_SIZE);
//		}
	}

}
