package apoCommando.panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import org.apogames.help.ApoHelp;

import apoCommando.ApoMarioConstants;
import apoCommando.entity.ApoMarioEntity;
import apoCommando.game.ApoMarioPanel;
import apoCommando.level.ApoMarioAchievements;

public class ApoMarioGame extends ApoMarioModel {
	
	private boolean bStop;
	
	private boolean bStep;
	
	private int startX;
	
	private ApoMarioTutorial tutorial;
	
	public ApoMarioGame(ApoMarioPanel game) {
		super(game);
		
	}
	
	public void init(boolean bTutorial) {
		super.init();
		if (this.tutorial == null) {
			this.tutorial = new ApoMarioTutorial(this);
		}
		this.tutorial.setBTutorial(bTutorial);
		this.bStop = false;
		this.bStep = false;
	}
	
	public ApoMarioTutorial getTutorial() {
		return this.tutorial;
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (button == KeyEvent.VK_UP) {
			if (this.getValue() >= 0) {
				this.curString = this.getLastCommands().get(this.getValue());
				this.setValue(this.getValue() - 1);
			}
		} else if (button == KeyEvent.VK_BACK_SPACE) {
			String s = this.curString;
			if ((s != null) && (s.length() > 0)) {
				this.curString = this.curString.substring(0, this.curString.length() - 1);
			}
		} else {
			if ((character >= 32) && (character <= 126) && (this.curString.length() < 15)) {
				this.curString = this.curString + character;
			}
		}
		if (button == KeyEvent.VK_ESCAPE) {
			this.getGame().getLevel().setAnalysis(false);
		} else if (button == KeyEvent.VK_ENTER) {
			super.checkString();
		}
	}
	
	public void checkCurString() {
		this.curString = this.curString.toLowerCase();
		if (this.curString.length() > 0) {
			this.addCommand(this.curString);
		}
		if (this.curString.equals("exit")) {
			this.getGame().getLevel().setAnalysis(false);
		} else if (this.curString.equals("retry")) {
			this.getGame().setGame(this.getGame().getGame().getTutorial().isBTutorial());
		} else if (super.checkLevelCode(this.curString)) {
			
		} else if (checkLevelLoad(this.curString)) {
			
		} else if (this.tutorial.isBTutorial()) {
			if (this.curString.equals("next")) {
				this.addCommand(this.curString);	
			}
			if (this.tutorial.checkCurString(this.curString)) {
				this.curString = "";
				return;
			}
		}
		if (this.curString.equals("jump")) {
			this.getGame().getLevel().getPlayer().setBJump(true, false);
			this.getGame().getAchievements().addJump();
			this.bStep = false;
		} else if (this.curString.equals("high")) {
			this.getGame().getLevel().getPlayer().setBJump(true, true);
			this.bStep = false;
		} else if (this.curString.equals("flip")) {
			this.getGame().getLevel().getPlayer().changeDirection();
			this.bStep = false;
		} else if (this.curString.equals("fast")) {
			this.getGame().getLevel().getPlayer().setBSpeed(true);
			this.getGame().getAchievements().addAchievement(ApoMarioAchievements.ACHIEVEMENT_FAST);
			this.bStep = false;
		} else if (this.curString.equals("slow")) {
			this.getGame().getLevel().getPlayer().setBSpeed(false);
			this.getGame().getAchievements().addAchievement(ApoMarioAchievements.ACHIEVEMENT_SLOW);
			this.bStep = false;
		} else if (this.curString.equals("duck")) {
			this.getGame().getLevel().getPlayer().setBDown(true);
			this.getGame().getAchievements().addDuck();
			this.bStep = false;
		} else if (this.curString.equals("stop")) {
			this.bStep = false;
			this.getGame().getAchievements().addAchievement(ApoMarioAchievements.ACHIEVEMENT_STOP);
			this.setStop();
		} else if (this.curString.equals("step")) {
			this.bStop = false;
			this.getGame().getAchievements().addAchievement(ApoMarioAchievements.ACHIEVEMENT_STEP);
			this.bStep = true;
			this.startX = (int)(this.getGame().getLevel().getPlayer().getRec().getX() / ApoMarioConstants.TILE_SIZE);
		} else if (this.curString.equals("go")) {
			this.bStop = false;
			this.bStep = false;
			this.getGame().getAchievements().addAchievement(ApoMarioAchievements.ACHIEVEMENT_GO);
		} else if (this.curString.startsWith("godmode ")) {
			try {
				String s = this.curString.substring(8);
				if (s != null) {
					if (s.equals("on")) {
						this.getGame().getLevel().getPlayer().setBGodMode(true);
						this.getGame().getAchievements().addAchievement(ApoMarioAchievements.ACHIEVEMENT_CHEATER_GODMODE);
					} else if (s.equals("off")) {
						this.getGame().getLevel().getPlayer().setBGodMode(false);
					}
				}
			} catch (Exception ex) {
			}
		} else if (this.curString.equals("fire")) {
			this.bStep = false;
			this.getGame().getLevel().getPlayer().addFireBall();
			this.getGame().getAchievements().addAchievement(ApoMarioAchievements.ACHIEVEMENT_FIRE);
		} else if (this.checkExtra(this.curString)) {
		
		} 
		this.curString = "";
	}
	
	private void setStop() {
		this.bStop = true;
		this.getGame().getLevel().getPlayer().setVelocityX(0);
		this.getGame().getLevel().getPlayer().setGoalVecX(0);
		this.bStep = false;
	}

	@Override
	public void mouseButtonFunction(String function) {
	}

	@Override
	public void mouseButtonReleased(int x, int y) {
		if (this.getGame().getLevel().isBStop()) {
			this.getGame().getLevel().setBStop(false);
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
	public boolean mousePressed(int x, int y) {
		return false;
	}

	@Override
	public void think(int delta) {
		if (!this.getGame().isBPause()) {
			super.think(delta);
			if (this.tutorial.isBTutorial()) {
				if (!this.tutorial.think(delta)) {
					return;
				}
			}
			if (this.getGame().getLevel() != null) {
				if (this.getGame().getLevel().isBAnalysis()) {
					this.thinkLevel(delta);
				}
				this.changeCamera();
			}
		}
	}
	
	public void changeCamera() {
		float x = this.getGame().getLevel().getPlayer().getX();
		if (x > 0) {
			float changeX = x - ApoMarioConstants.GAME_WIDTH/2;
			if (changeX < 0) {
				changeX = 0;
			} else if (changeX + ApoMarioConstants.GAME_WIDTH >= this.getGame().getLevel().getWidth() * ApoMarioConstants.TILE_SIZE) {
				changeX = this.getGame().getLevel().getWidth() * ApoMarioConstants.TILE_SIZE - ApoMarioConstants.GAME_WIDTH;
			}
			if (changeX != this.getGame().getChangeX()) {
				this.getGame().setChangeX(changeX);
			}
		}
	}
	
	protected void thinkLevel(int delta) {
		this.playerHumanControl();
		int change = delta;
		for (int i = 0; i < delta; i += change) {
			this.getGame().getLevel().think(change);					
		}
		if (this.bStep) {
			int curX = (int)(this.getGame().getLevel().getPlayer().getRec().getX() / ApoMarioConstants.TILE_SIZE);
			if (this.startX != curX) {
				this.setStop();
			}
		}
	}
	
	private void playerHumanControl() {
		if (!this.bStop) {
			if (this.getGame().getLevel().getPlayer().getDirection() == ApoMarioEntity.LEFT) {
				if (this.getGame().getLevel().getPlayer().isBSpeed()) {
					this.getGame().getLevel().getPlayer().setGoalVecX(-ApoMarioConstants.PLAYER_MAX_VEC_X_FAST - ApoMarioConstants.DIFFICULTY * ApoMarioConstants.PLAYER_MAX_VEC_X_DIF_FAST);
				} else {
					this.getGame().getLevel().getPlayer().setGoalVecX(-ApoMarioConstants.PLAYER_MAX_VEC_X_NORMAL - ApoMarioConstants.DIFFICULTY * ApoMarioConstants.PLAYER_MAX_VEC_X_DIF_NORMAL);
				}
			} else {
				if (this.getGame().getLevel().getPlayer().isBSpeed()) {
					this.getGame().getLevel().getPlayer().setGoalVecX(ApoMarioConstants.PLAYER_MAX_VEC_X_FAST + ApoMarioConstants.DIFFICULTY * ApoMarioConstants.PLAYER_MAX_VEC_X_DIF_FAST);
				} else {
					this.getGame().getLevel().getPlayer().setGoalVecX(ApoMarioConstants.PLAYER_MAX_VEC_X_NORMAL + ApoMarioConstants.DIFFICULTY * ApoMarioConstants.PLAYER_MAX_VEC_X_DIF_NORMAL);
				}
			}
		}
	}
	
	public BufferedImage getBackgroundImageWithRect() {
		BufferedImage iDosBox = new BufferedImage(ApoMarioConstants.GAME_WIDTH - 10, 130, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = (Graphics2D)iDosBox.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int width = iDosBox.getWidth();
		int height = iDosBox.getHeight();
		
		g.setColor(new Color(196, 215, 234));
		g.fillRoundRect(0, 0, width - 1, height - 1, 15, 15);			
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(2));
		g.drawRoundRect(0, 0, width - 1, height - 1, 15, 15);			
		
		int x = 10;
		int y = 20;
		g.fillRect(x, y, width - 20, height - 25);
		
		g.dispose();
		return iDosBox;
	}

	@Override
	public void render(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		if (this.getGame().getLevel() != null) {
			this.getGame().getLevel().render(g, (int)this.getGame().getChangeX(), (int)this.getGame().getChangeY());
		}

		if (this.tutorial.isBTutorial()) {
			g.drawImage(this.iDosBox, 5, 2, null);
			this.tutorial.renderTutorial(g);
			this.renderCurcor(g);
		} else {
			if (!this.bStats) {
				this.renderStatistics(g);
			} else {
				super.renderStats(g);
			}
		}
		this.getGame().renderButtons(g);
	}
	
	private void renderStatistics(Graphics2D g) {
		int x = 5 + 10;
		int y = 2 + 20;
		g.drawImage(this.iDosBox, 5, 2, null);
		g.setFont(ApoMarioConstants.FONT_DOS_BOX);
		g.setColor(Color.WHITE);
		int addY = 15;
		if (!this.tutorial.isBTutorial()) {
			g.drawString("ApoCommando (version "+ApoMarioConstants.VERSION+")", x + 5, y + 20);
			g.drawString("Copyright (c) 2011 Apo-Games. All Rights reserved.", x + 5, y + 20 + 1 * addY);
		}
		this.renderCurcor(g);
		
		String s = "";
		if (ApoMarioConstants.DIFFICULTY > 1) {
			s = "C:\\Time>"+ApoHelp.getTimeToDraw(this.getGame().getLevel().getTime());
			g.drawString(s, x + 5, y + 20 + 3 * addY);
			s = "C:\\Stats>Points: "+this.getGame().getLevel().getPlayer().getPoints() + ", width: " + (int)(this.getGame().getLevel().getPlayer().getX() / ApoMarioConstants.TILE_SIZE) + "/" + this.getGame().getLevel().getWidth()+", Coins: "+this.getGame().getLevel().getPlayer().getCoins() + " / " + this.getGame().getLevel().getMaxCoins();
			g.drawString(s, x + 5, y + 20 + 4 * addY);
		} else {
			s = "C:\\Coins>"+this.getGame().getLevel().getPlayer().getCoins() + " / " + this.getGame().getLevel().getMaxCoins();
			g.drawString(s, x + 5, y + 20 + 3 * addY);
			s = "C:\\Stats>Points: "+this.getGame().getLevel().getPlayer().getPoints() + ", width: " + (int)(this.getGame().getLevel().getPlayer().getX() / ApoMarioConstants.TILE_SIZE) + "/" + this.getGame().getLevel().getWidth();
			g.drawString(s, x + 5, y + 20 + 4 * addY);
		}
		
		if (this.extraString.length() > 0) {
			g.setColor(new Color(0, 0, 0, 180));
			g.fillRect(x, y + 20 + 6 * addY, this.iDosBox.getWidth() - 20, 20);
			g.setColor(Color.WHITE);
			super.renderExtraString(g, x + 5, y + 20 + 7 * addY);			
		}
	}
	
	private void renderCurcor(Graphics2D g) {
		int x = 5 + 10;
		int y = 2 + 20;
		g.setFont(ApoMarioConstants.FONT_DOS_BOX);
		int addY = 15;
		g.drawString("C:\\Command>", x + 5, y + 20 + 5 * addY);
		g.setColor(Color.WHITE);
		int myX = x + g.getFontMetrics().stringWidth("C:\\Command>");
		if (this.bCursor) {
			g.drawString(this.curString+"_", myX + 5, y + 20 + 5 * addY);
		} else {
			g.drawString(this.curString, myX + 5, y + 20 + 5 * addY);
		}
	}

}
