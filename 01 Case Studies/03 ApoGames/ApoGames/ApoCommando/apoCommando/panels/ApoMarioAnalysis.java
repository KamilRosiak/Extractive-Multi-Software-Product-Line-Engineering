package apoCommando.panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import org.apogames.ApoConstants;

import apoCommando.ApoMarioConstants;
import apoCommando.ApoMarioImageContainer;
import apoCommando.game.ApoMarioPanel;

public class ApoMarioAnalysis extends ApoMarioModel {
	
	public ApoMarioAnalysis(ApoMarioPanel game) {
		super(game);
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		super.keyButtonReleased(button, character);
		if (button == KeyEvent.VK_UP) {
			if (this.getValue() >= 0) {
				this.curString = this.getLastCommands().get(this.getValue());
				this.setValue(this.getValue() - 1);
			}
		} else if (button == KeyEvent.VK_DOWN) {
			if (this.getValue() < this.getLastCommands().size() - 1) {
				this.setValue(this.getValue() + 1);
				this.curString = this.getLastCommands().get(this.getValue());
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
			if (!ApoConstants.B_APPLET) {
				System.exit(0);
			}
		} else if ((button == KeyEvent.VK_ENTER)) {
			super.checkString();
		} else if (button == KeyEvent.VK_PLUS) {
			if (ApoMarioConstants.TILE_SET == ApoMarioConstants.TILE_SET_EAT) {
				ApoMarioConstants.TILE_SET = ApoMarioConstants.TILE_SET_MARIO;
			} else if (ApoMarioConstants.TILE_SET == ApoMarioConstants.TILE_SET_MARIO) {
				ApoMarioConstants.TILE_SET = ApoMarioConstants.TILE_SET_EAT;
			}
			ApoMarioImageContainer.setTileSet(ApoMarioConstants.TILE_SET);
			this.getGame().restartLevel();
			this.getGame().setAnalysis();
		}
	}

	@Override
	public void mouseButtonFunction(String function) {
	}

	@Override
	public void mouseButtonReleased(int x, int y) {
		
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
		super.think(delta);
	}
	
	public void checkCurString() {
		this.curString = this.curString.toLowerCase();
		if (this.curString.equals("exit")) {
			if (!ApoConstants.B_APPLET) {
				System.exit(0);
			}
		} else if (checkLevelLoad(this.curString)) {
			
		} else if (super.checkAchievements(this.curString)) {
			
		} else if (super.checkLevelCode(this.curString)) {
			
		} else if (this.curString.equals("retry")) {
			this.getGame().setGame(this.getGame().getGame().getTutorial().isBTutorial());
		} else if ((this.curString.equals("random")) || (this.curString.equals("new"))) {
			this.getGame().newLevel(100 * ApoMarioConstants.DIFFICULTY, (ApoMarioConstants.DIFFICULTY - 1) * 75, null);
			this.getGame().setGame(false);
		} else if (this.checkDifficulty(this.curString)) {
			this.extraString = "";
		} else if (this.checkExtra(this.curString)) {
		}
		this.curString = "";
	}
	
	public BufferedImage getBackgroundImageWithRect() {
		BufferedImage iMenuBackground = this.getGame().getBackgroundImage();
		Graphics2D g = (Graphics2D)iMenuBackground.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int width = iMenuBackground.getWidth() - 25 * ApoMarioConstants.SIZE;
		int height = iMenuBackground.getHeight() - 30 * ApoMarioConstants.SIZE;
		
		g.setColor(new Color(196, 215, 234));
		g.fillRoundRect((ApoMarioConstants.GAME_WIDTH - width)/2, (ApoMarioConstants.GAME_HEIGHT - height)/2, width, height, 15, 15);			
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(2));
		g.drawRoundRect((ApoMarioConstants.GAME_WIDTH - width)/2, (ApoMarioConstants.GAME_HEIGHT - height)/2, width, height, 15, 15);			
		
		int x = (ApoMarioConstants.GAME_WIDTH - width)/2 + 10;
		int y = (ApoMarioConstants.GAME_HEIGHT - height)/2 + 20;
		g.fillRect(x, y, width - 20, height - 25);
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		g.setFont(ApoMarioConstants.FONT_DOS_BOX);
		int addY = 15;
		g.setColor(Color.WHITE);
		g.drawString("ApoCommando (version "+ApoMarioConstants.VERSION+")", x + 5, y + 20);
		g.drawString("Copyright (c) 2011 Apo-Games. All Rights reserved.", x + 5, y + 20 + 1 * addY);

		g.drawString("C:\\Help\\Menu>Type 'start' to play", x + 5, y + 20 + 3 * addY);
		if (ApoConstants.B_APPLET) {
			g.drawString("C:\\Help\\Menu>Type 'tutorial' to start tutorial", x + 5, y + 20 + 4 * addY);
		} else {
			g.drawString("C:\\Help\\Menu>Type 'exit' to exit the game", x + 5, y + 20 + 4 * addY);
			g.drawString("C:\\Help\\Menu>Type 'tutorial' to start tutorial", x + 5, y + 20 + 5 * addY);			
		}
		
		g.drawString("C:\\Help\\Game>Command 'jump' = player jump", x + 5, y + 20 + 7 * addY);
		g.drawString("C:\\Help\\Game>Command 'high' = player jump high", x + 5, y + 20 + 8 * addY);
		g.drawString("C:\\Help\\Game>Command 'duck' = player duck", x + 5, y + 20 + 9 * addY);
		g.drawString("C:\\Help\\Game>Command 'flip' = player change the direction", x + 5, y + 20 + 10 * addY);
		
		g.drawString("C:\\Help\\Difficulty>Change Difficulty 'dif x' x = 1, 2, 3, 4", x + 5, y + 20 + 15 * addY);
		
		if (super.getGame().isBFirstStart()) {
			g.drawString("C:\\Menu>", x + 5, y + 20 + 19 * addY);
		} else {
			g.drawString("C:\\Analysis>", x + 5, y + 20 + 22 * addY);	
		}
		
		g.dispose();
		return iMenuBackground;
	}

	@Override
	public void render(Graphics2D g) {
		if (this.iDosBox != null) {
			g.drawImage(this.iDosBox, 0, 0, null);
		}
		if (this.bStats) {
			super.renderStats(g);				
		} else if (this.bAchievements) {
			super.renderAchievements(g);
		} else if (this.bLevel) {
			super.renderLevelcodes(g);
		} else {
			int width = this.iDosBox.getWidth() - 25 * ApoMarioConstants.SIZE;
			int height = this.iDosBox.getHeight() - 30 * ApoMarioConstants.SIZE;
			int x = (ApoMarioConstants.GAME_WIDTH - width)/2 + 10;
			int y = (ApoMarioConstants.GAME_HEIGHT - height)/2 + 20;
			g.setFont(ApoMarioConstants.FONT_DOS_BOX);
			int addY = 15;
			g.setColor(Color.WHITE);
			if (this.getGame().isBFirstStart()) {
				int myX = x + g.getFontMetrics().stringWidth("C:\\Menu>");
				if (this.bCursor) {
					g.drawString(this.curString+"_", myX + 5, y + 20 + 19 * addY);
				} else {
					g.drawString(this.curString, myX + 5, y + 20 + 19 * addY);
				}
			} else {
				int myX = x + g.getFontMetrics().stringWidth("C:\\Analysis>");
				
				if (this.getGame().isBWin()) {
					g.drawString("C:\\Analysis>Points: "+this.getGame().getLevel().getPlayer().getPoints(), x + 5, y + 20 + 18 * addY);
					g.drawString("C:\\Analysis>Congratulation you solve the level", x + 5, y + 20 + 19 * addY);
					if (this.getGame().getLevel().getNextLevelString() != null) {
						g.drawString("C:\\Analysis>The command for the next level is '"+this.getGame().getLevel().getNextLevelString()+"'", x + 5, y + 20 + 20 * addY);
					} else {
						g.drawString("C:\\Analysis>The command for the next level is 'random'", x + 5, y + 20 + 20 * addY);					
					}
				} else {
					g.drawString("C:\\Analysis>Points: "+this.getGame().getLevel().getPlayer().getPoints(), x + 5, y + 20 + 18 * addY);
					g.drawString("C:\\Analysis>Please 'retry' the level ...", x + 5, y + 20 + 19 * addY);
					if ((this.getGame().getLevel().getTime() <= 0) && (ApoMarioConstants.DIFFICULTY > 1)) {
						g.drawString("C:\\Analysis>Time is running out", x + 5, y + 20 + 20 * addY);
					}
				}
				
				if (this.bCursor) {
					g.drawString(this.curString+"_", myX + 5, y + 20 + 22 * addY);
				} else {
					g.drawString(this.curString, myX + 5, y + 20 + 22 * addY);
				}
			}
			g.drawString("C:\\Help\\Difficulty>Current Difficulty '"+ApoMarioConstants.DIFFICULTY+"'", x + 5, y + 20 + 16 * addY);
			
			super.renderExtraString(g, x + 5, y + 20 + 24 * addY);
		}
	}

}
