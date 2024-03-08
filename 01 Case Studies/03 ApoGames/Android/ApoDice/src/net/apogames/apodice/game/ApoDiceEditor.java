package net.apogames.apodice.game;

import net.apogames.apodice.ApoDice;
import net.apogames.apodice.ApoDiceConstants;
import net.apogames.apodice.ApoDiceModel;
import net.apogames.apodice.entity.ApoDiceString;

import net.gliblybits.bitsengine.render.BitsGraphics;
import net.gliblybits.bitsengine.utils.BitsLog;

public class ApoDiceEditor extends ApoDiceModel {

	private final int EMPTY = 0;
	private final int GOAL = 1;
	private final int DICE_EMPTY = 2;
	private final int DICE_ONE = 3;
	private final int DICE_TWO = 4;
	private final int DICE_THREE = 5;
	private final int DICE_FOUR = 6;
	private final int DICE_FIVE = 7;
	private final int DICE_SIX = 8;
	
	public static final String BACK = "back";
	public static final String UPLOAD = "upload";
	public static final String TEST = "test";
	public static final String NEW = "new";
	public static final String SOLVE = "solve";

	private ApoDiceString uploadString;
	
	private byte[][] level = new byte[16][8];
	
	private int curSelect;
	
	private Thread t;
	
	public ApoDiceEditor(ApoDicePanel game) {
		super(game);
	}

	@Override
	public void init() {
		this.getStringWidth().put(ApoDiceEditor.BACK, (int)(ApoDiceMenu.game_font.getLength(ApoDiceEditor.BACK)));
		this.getStringWidth().put(ApoDiceEditor.NEW, (int)(ApoDiceMenu.game_font.getLength(ApoDiceEditor.NEW)));
		this.getStringWidth().put(ApoDiceEditor.TEST, (int)(ApoDiceMenu.game_font.getLength(ApoDiceEditor.TEST)));
		this.getStringWidth().put(ApoDiceEditor.UPLOAD, (int)(ApoDiceMenu.game_font.getLength(ApoDiceEditor.UPLOAD)));
		this.getStringWidth().put(ApoDiceEditor.SOLVE, (int)(ApoDiceMenu.game_font.getLength(ApoDiceEditor.SOLVE)));
		
		String s = "ApoDice - Editor";
		this.getStringWidth().put(s, (int) ApoDiceMenu.game_font.getLength(s));
		
		this.checkTestLevel();
	}
	
	public void setLevelSolved(boolean bSolved) {
		if ((!bSolved) || (!ApoDice.isOnline())) {
			this.getGame().getButtons()[9].setVisible(false);
		} else {
			this.getGame().getButtons()[9].setVisible(true);
		}
	}
	
	private void checkTestLevel() {
		int goals = 0;
		int dices = 0;
		for (int y = 0; y < 8; y += 1) {
			for (int x = 0; x < level[y].length; x += 1) {
				if (this.level[y][x] == 1) {
					goals += 1;
				}
				if (this.level[y+8][x] >= 2) {
					dices += 1;
				}
			}
		}
		if ((goals > 0) && (dices == goals)) {
			this.getGame().getButtons()[8].setVisible(true);
//			this.getGame().getButtons()[10].setVisible(true);
		} else {
			this.getGame().getButtons()[8].setVisible(false);
			this.getGame().getButtons()[9].setVisible(false);
			this.getGame().getButtons()[10].setVisible(false);
		}
	}

	@Override
	public void touchedPressed(int x, int y, int finger) {
		if (y > 505) {
			for (int i = 0; i < 7; i++) {
				if ((x >= 5 + i*65) && (x < 65 + i * 65) && (y >= 510) && (y < 570)) {
					this.curSelect = i + 2;
				}
			}
			if ((x >= 5) && (x < 65) && (y >= 575) && (y < 635)) {
				this.curSelect = this.EMPTY;
			}
			if ((x >= 70) && (x < 130) && (y >= 575) && (y < 635)) {
				this.curSelect = this.GOAL;
			}
		} else {
			if ((y >= ApoDicePuzzleGame.changeY) && (y < 480 + ApoDicePuzzleGame.changeY)) {
				int newY = (y - ApoDicePuzzleGame.changeY)/60;
				if (this.curSelect == this.EMPTY) {
					level[newY][x/60] = level[newY + 8][x/60] = 0;
				} else if (this.curSelect == this.GOAL) {
					level[newY][x/60] = 1;
				} else if (this.curSelect == this.DICE_EMPTY) {
					level[newY + 8][x/60] = 2;
				} else if (this.curSelect == this.DICE_ONE) {
					level[newY + 8][x/60] = 3;
				} else if (this.curSelect == this.DICE_TWO) {
					level[newY + 8][x/60] = 4;
				} else if (this.curSelect == this.DICE_THREE) {
					level[newY + 8][x/60] = 5;
				} else if (this.curSelect == this.DICE_FOUR) {
					level[newY + 8][x/60] = 6;
				} else if (this.curSelect == this.DICE_FIVE) {
					level[newY + 8][x/60] = 7;
				} else if (this.curSelect == this.DICE_SIX) {
					level[newY + 8][x/60] = 8;
				}
				this.checkTestLevel();
			}
		}
	}

	@Override
	public void touchedReleased(int x, int y, int finger) {
		
	}

	@Override
	public void touchedDragged(int x, int y, int oldX, int oldY, int finger) {
		
	}
	
	@Override
	public void touchedButton(String function) {
		if (function.equals(ApoDiceEditor.BACK)) {
			this.onBackButtonPressed();
		} else if (function.equals(ApoDiceEditor.NEW)) {
			this.level = new byte[16][8];
			this.checkTestLevel();
		} else if (function.equals(ApoDiceEditor.TEST)) {
			String levelString = this.getLevelString();
			BitsLog.d("levelString", levelString);
			this.getGame().setPuzzleGame(-1, levelString, false);
		} else if (function.equals(ApoDiceEditor.UPLOAD)) {
			this.setLevelSolved(false);
			this.uploadString = new ApoDiceString(240, 470, 20, "Uploading ...", true, 200, true);
			
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					ApoDiceEditor.this.uploadString();
				}
	 		});
	 		t.start();
		} else if (function.equals(ApoDiceEditor.SOLVE)) {
			this.uploadString = new ApoDiceString(240, 30, 20, "Try to solve ...", true, 20000, true);
			this.t = new Thread(new Runnable() {
				@Override
				public void run() {
					solveLevel();
				}
	 		});
	 		this.t.start();
		}
	}
	
	private void solveLevel() {
		this.solve = new ApoDiceSolve();
		if (solve.canBeSolved(ApoDiceEditor.this.level)) {
			ApoDiceEditor.this.uploadString = new ApoDiceString(240, 30, 20, "Can be solved", true, 20, true);
			setLevelSolved(true);
		} else {
			ApoDiceEditor.this.uploadString = new ApoDiceString(240, 30, 20, "Can't be solved", true, 20, true);
			setLevelSolved(false);
		}
		this.solve = null;
	}
	
	private ApoDiceSolve solve = null;
	
	private void uploadString() {
		if (this.getGame().getUserlevels().addLevel(this.getLevelString())) {
			this.uploadString = new ApoDiceString(240, 470, 20, "Uploading successfully", true, 20, true);
			this.getGame().loadUserlevels();
		} else {
			this.uploadString = new ApoDiceString(240, 470, 20, "Uploading failed", true, 20, true);
		}
	}
	
	public void onBackButtonPressed() {
		try {
			if (this.solve != null) {
				this.solve.setBreak(true);
				this.solve = null;
			}
		} catch (Exception ex) {
		}
		this.getGame().setMenu();
	}
	
	private String getLevelString() {
		String level = "";
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if (this.level[y+8][x] > 1) {
					if (this.level[y][x] <= 0) {
						level += String.valueOf(this.level[y+8][x]);	
					} else {
						char c = (char)(95 + this.level[y+8][x]);
						level += String.valueOf(c);
					}
				} else {
					level += String.valueOf(this.level[y][x]);
				}
			}
		}
		return level;
	}

	@Override
	public void think(int delta) {

		if (this.uploadString != null) {
			this.uploadString.think(delta);
			
			if (!this.uploadString.isVisible()) {
				this.uploadString = null;
			}
		}
	}

	@Override
	public void render(BitsGraphics g) {
		g.setColor(128, 128, 128, 255);
		g.drawFilledRect(0,0,480,ApoDicePuzzleGame.changeY);
		g.drawFilledRect(0,480 + ApoDicePuzzleGame.changeY,480,160 - ApoDicePuzzleGame.changeY);
		
		g.setColor(0f/255f, 0f/255f, 0f/255f, 1.0f);
		g.drawRect(0,0,480,ApoDicePuzzleGame.changeY);
		g.drawRect(0,480 + ApoDicePuzzleGame.changeY,480,160 - ApoDicePuzzleGame.changeY);
		
		g.setColor(48f/255f, 48f/255f, 48f/255f, 1.0f);
		for (int y = 0; y < 8; y += 1) {
			g.drawLine(0, ApoDicePuzzleGame.changeY + y * 60, ApoDiceConstants.GAME_WIDTH, ApoDicePuzzleGame.changeY + y * 60);
			g.drawLine((y + 1) * 60, ApoDicePuzzleGame.changeY, (y + 1) * 60, ApoDicePuzzleGame.changeY + 8 * 60);
		}
		
		String s = "ApoDice - Editor";
		this.getGame().drawString(g, s, 240, -4, ApoDiceMenu.game_font);
		
		this.getGame().renderButtons(g, ApoDiceMenu.game_font);
		
		for (int y = 0; y < 8; y += 1) {
			for (int x = 0; x < level[y].length; x += 1) {
				if (level[y][x] == 1) {
					g.setColor(48f/255f, 48f/255f, 48f/255f, 1.0f);
					g.drawFilledRoundRect(x * 60 + 1, ApoDicePuzzleGame.changeY + y * 60 + 1, 58, 58, 6, 10);
				}
				if (level[y+8][x] > 0) {
					g.setColor(255f/255f, 255f/255f, 255f/255f, 1.0f);
					g.drawFilledRoundRect(x * 60 + 5, ApoDicePuzzleGame.changeY + y * 60 + 5, 50, 50, 6, 10);
					g.setColor(0f/255f, 0f/255f, 0f/255f, 1.0f);
					if ((level[y+8][x] == 3) || (level[y+8][x] == 5) || (level[y+8][x] == 7)) {
						g.drawFilledCircle(x * 60 + 30, ApoDicePuzzleGame.changeY + y * 60 + 30, 6, 40);
					}
					if ((level[y+8][x] == 4) || (level[y+8][x] == 5) || (level[y+8][x] == 6) || (level[y+8][x] == 7) || (level[y+8][x] == 8)) {
						g.drawFilledCircle(x * 60 + 14, ApoDicePuzzleGame.changeY + y * 60 + 14, 6, 40);
						g.drawFilledCircle(x * 60 + 46, ApoDicePuzzleGame.changeY + y * 60 + 46, 6, 40);
					}
					if ((level[y+8][x] == 6) || (level[y+8][x] == 7) || (level[y+8][x] == 8)) {
						g.drawFilledCircle(x * 60 + 46, ApoDicePuzzleGame.changeY + y * 60 + 14, 6, 40);
						g.drawFilledCircle(x * 60 + 14, ApoDicePuzzleGame.changeY + y * 60 + 46, 6, 40);
					}
					if (level[y+8][x] == 8) {
						g.drawFilledCircle(x * 60 + 14, ApoDicePuzzleGame.changeY + y * 60 + 30, 6, 40);
						g.drawFilledCircle(x * 60 + 46, ApoDicePuzzleGame.changeY + y * 60 + 30, 6, 40);
					}
				}
			}
		}
		
		if (this.uploadString != null) {
			this.uploadString.render(g, 0, 0);
		}
		
		//draw empty
		g.setColor(192, 192, 192, 255);
		g.drawFilledRoundRect(5, 575, 60, 60, 6, 10);
		if (this.curSelect == 0) {
			g.setLineSize(3f);
			g.setColor(255, 0, 0, 255);
			g.drawRoundRect(5, 575, 60, 60, 6, 10);
			g.setLineSize(1f);
		}
		
		// draw goal
		g.setColor(48, 48, 48, 255);
		g.drawFilledRoundRect(70, 575, 60, 60, 6, 10);
		if (this.curSelect == 1) {
			g.setLineSize(3f);
			g.setColor(255, 0, 0, 255);
			g.drawRoundRect(70, 575, 60, 60, 6, 10);
			g.setLineSize(1f);
		}
		
		for (int i = 0; i < 7; i++) {
			g.setColor(255, 255, 255, 255);
			g.drawFilledRoundRect(5 + i * 65, 510, 60, 60, 6, 10);
			if (this.curSelect == i + 2) {
				g.setLineSize(3f);
				g.setColor(255, 0, 0, 255);
				g.drawRoundRect(5 + i * 65, 510, 60, 60, 6, 10);
				g.setLineSize(1f);
			}
			g.setColor(0, 0, 0, 255);
			if ((i == 1) || (i == 3) || (i == 5)) {
				g.drawFilledCircle(5 + i * 65 + 30, 510 + 30, 6, 40);
			}
			if ((i == 2) || (i == 3) || (i == 4) || (i == 5) || (i == 6)) {
				g.drawFilledCircle(5 + i * 65 + 14, 510 + 14, 6, 40);
				g.drawFilledCircle(5 + i * 65 + 46, 510 + 46, 6, 40);
			}
			if ((i == 4) || (i == 5) || (i == 6)) {
				g.drawFilledCircle(5 + i * 65 + 46, 510 + 14, 6, 40);
				g.drawFilledCircle(5 + i * 65 + 14, 510 + 46, 6, 40);
			}
			if (i == 6) {
				g.drawFilledCircle(5 + i * 65 + 46, 510 + 30, 6, 40);
				g.drawFilledCircle(5 + i * 65 + 14, 510 + 30, 6, 40);
			}
		}
	}

}
