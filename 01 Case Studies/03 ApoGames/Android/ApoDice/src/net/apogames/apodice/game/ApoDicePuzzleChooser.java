package net.apogames.apodice.game;

import net.apogames.apodice.ApoDice;
import net.apogames.apodice.ApoDiceModel;
import net.apogames.apodice.entity.ApoLevelChooserButton;
import net.apogames.apodice.level.ApoDiceLevel;
import net.gliblybits.bitsengine.render.BitsGraphics;

public class ApoDicePuzzleChooser extends ApoDiceModel {

	public static final String BACK = "back";
	public static final String LEVEL_CHOOSER = "ApoDice - Levelchooser";
	
	private ApoLevelChooserButton[] levels;
	
	private int solved = 0;
	
	private int curShow = 0;
	
	public ApoDicePuzzleChooser(ApoDicePanel game) {
		super(game);
	}

	@Override
	public void init() {
		this.getStringWidth().put(ApoDicePuzzleChooser.BACK, (int)(ApoDiceMenu.font.getLength(ApoDicePuzzleChooser.BACK)));
		this.getStringWidth().put(ApoDicePuzzleChooser.LEVEL_CHOOSER, (int)(ApoDiceMenu.title_font.getLength(ApoDicePuzzleChooser.LEVEL_CHOOSER)));
		
		this.curShow = 0;
		
		if (this.levels == null) {
			this.levels = new ApoLevelChooserButton[ApoDiceLevel.MAX_LEVELS];
			
			int xPos = 20;
			int yPos = 50;
			int radius = 70;
			int add = 20;
			int curLevel = 0;
			for (int y = 0; y < 6; y++) {
				for (int x = 0; x < 5; x++) {
					this.levels[curLevel] = new ApoLevelChooserButton(null, xPos, yPos, radius, radius, String.valueOf(curLevel + 1));
					
					xPos += radius + add;
					curLevel += 1;
					if (curLevel >= this.levels.length) {
						break;
					}
				}
				xPos = 20;
				yPos += radius + add;
				if (curLevel >= this.levels.length) {
					break;
				}
				if (curLevel % 30 == 0) {
					yPos = 70;
				}
			}
			this.setSolved(0);
		}
	}
	
	public final int getSolved() {
		return this.solved;
	}

	public final void setSolved(int solved) {
		if (this.solved < solved) {
			this.solved = solved;
			if (this.solved > ApoDiceLevel.MAX_LEVELS - 1) {
				this.solved = ApoDiceLevel.MAX_LEVELS - 1;
			}
			if (this.solved < this.levels.length) {
				for (int i = 0; i < this.solved && i < this.levels.length; i++) {
					this.levels[i].setSolved(true);
				}
			}
			this.getGame().savePreferences(ApoDice.settings);
		}
	}	

	@Override
	public void touchedPressed(int x, int y, int finger) {
		if (this.levels != null) {
			for (int i = this.curShow; i < this.curShow + 64 && i < this.levels.length && i <= this.solved; i++) {
				if (this.levels[i].intersects(x, y)) {
					this.getGame().setPuzzleGame(i, "", false);
				}
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
		if (function.equals(ApoDicePuzzleChooser.BACK)) {
			this.onBackButtonPressed();
		}
	}
	
	public void onBackButtonPressed() {
		this.getGame().setMenu();
	}

	@Override
	public void think(int delta) {
		
	}

	@Override
	public void render(BitsGraphics g) {
		this.getGame().drawString(g, ApoDicePuzzleChooser.LEVEL_CHOOSER, 240, 2, ApoDiceMenu.title_font, new float[] {1, 1, 1, 1}, new float[] {0, 0, 0, 1});
		
		this.getGame().renderButtons(g);
		
		if (this.levels != null) {
			for (int i = this.curShow; i < this.curShow + 64 && i < this.levels.length; i++) {
				int x = (int)(this.levels[i].getX());
				int y = (int)(this.levels[i].getY());
				int radius = (int)(this.levels[i].getWidth());
				
				g.setColor(255, 255, 255, 255);
				if (this.levels[i].isSolved()) {
					g.setColor(102, 135, 89, 255);
				} else if (this.solved < i) {
					g.setColor(128, 128, 128, 255);
				}
				g.drawFilledRoundRect(x, y, radius, radius, 6, 10);

				g.setLineSize(2.5f);
				g.setColor(48, 48, 48);
				g.drawRoundRect(x, y, radius, radius, 6, 10);
				
				if (this.solved == i) {
					this.getGame().drawString(g, this.levels[i].getFunction(), x + radius/2 - (int)ApoDiceMenu.font.getLength(this.levels[i].getFunction())/2, y + radius/2 - ApoDiceMenu.font.mCharCellHeight/2, ApoDiceMenu.font, new float[] {1, 1, 1, 1}, new float[] {0, 0, 0, 1});
				} else {
					this.getGame().drawString(g, this.levels[i].getFunction(), x + radius/2 - (int)ApoDiceMenu.font.getLength(this.levels[i].getFunction())/2, y + radius/2 - ApoDiceMenu.font.mCharCellHeight/2, ApoDiceMenu.font);
				}
				
				g.setLineSize(1.0f);
			}
		}
	}

}
