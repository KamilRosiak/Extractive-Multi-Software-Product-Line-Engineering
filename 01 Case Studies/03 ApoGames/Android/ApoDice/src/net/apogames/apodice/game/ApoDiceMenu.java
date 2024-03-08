package net.apogames.apodice.game;

import net.apogames.apodice.ApoDiceModel;
import net.apogames.apodice.level.ApoDiceLevel;
import net.gliblybits.bitsengine.core.BitsFactory;
import net.gliblybits.bitsengine.core.BitsFont;
import net.gliblybits.bitsengine.core.BitsGame;
import net.gliblybits.bitsengine.render.BitsGraphics;

public class ApoDiceMenu extends ApoDiceModel {

	public static final String QUIT = "quit";
	public static final String PUZZLE = "puzzle";
	public static final String USERLEVELS = "userlevels";
	public static final String EDITOR = "editor";
	public static final String TITLE = "ApoDice";
	
	public static BitsFont font;
	public static BitsFont game_font;
	public static BitsFont title_font;
	
	private float clockRotate;
	
	public ApoDiceMenu(ApoDicePanel game) {
		super(game);
	}

	@Override
	public void init() {
		this.loadFonts();
		
		this.getStringWidth().put(ApoDiceMenu.QUIT, (int)(ApoDiceMenu.font.getLength(ApoDiceMenu.QUIT)));
		this.getStringWidth().put(ApoDiceMenu.PUZZLE, (int)(ApoDiceMenu.font.getLength(ApoDiceMenu.PUZZLE)));
		this.getStringWidth().put(ApoDiceMenu.USERLEVELS, (int)(ApoDiceMenu.font.getLength(ApoDiceMenu.USERLEVELS)));
		this.getStringWidth().put(ApoDiceMenu.EDITOR, (int)(ApoDiceMenu.font.getLength(ApoDiceMenu.EDITOR)));
		this.getStringWidth().put(ApoDiceMenu.TITLE, (int)(ApoDiceMenu.title_font.getLength(ApoDiceMenu.TITLE)));
		
		this.setUserlevels();
	}
	
	public void onResume() {
		this.loadFonts();
	}
	
	private void loadFonts() {
		ApoDiceMenu.font = BitsFactory.getIt().getFont("reprise.ttf", 30);
		ApoDiceMenu.title_font = BitsFactory.getIt().getFont("reprise.ttf", 38);
			
		ApoDiceMenu.game_font = BitsFactory.getIt().getFont("reprise.ttf", 26);
	}

	@Override
	public void touchedPressed(int x, int y, int finger) {
		
	}

	@Override
	public void touchedReleased(int x, int y, int finger) {
		
	}

	@Override
	public void touchedDragged(int x, int y, int oldX, int oldY, int finger) {
		
	}

	@Override
	public void touchedButton(String function) {
		if (function.equals(ApoDiceMenu.QUIT)) {
			this.onBackButtonPressed();
		} else if (function.equals(ApoDiceMenu.PUZZLE)) {
			this.getGame().setPuzzleChooser();
		} else if (function.equals(ApoDiceMenu.EDITOR)) {
			this.getGame().setEditor(false);
		} else if (function.equals(ApoDiceMenu.USERLEVELS)) {
			this.getGame().setPuzzleGame(0, "", true);
		}
	}
	
	public void onBackButtonPressed() {
		BitsGame.getIt().finish();
	}
	
	public void setUserlevels() {
		this.getGame().getButtons()[2].setVisible(true);
		if (ApoDiceLevel.editorLevels == null) {
			this.getGame().getButtons()[2].setVisible(false);
		}
	}
	
	@Override
	public void think(int delta) {
		this.clockRotate += delta / 10f;
		if (this.clockRotate >= 360) {
			this.clockRotate -= 360;
		}
	}

	@Override
	public void render(final BitsGraphics g) {
		
		this.getGame().drawString(g, ApoDiceMenu.TITLE, 240, 45, ApoDiceMenu.title_font, new float[] {1, 1, 1, 1}, new float[] {0, 0, 0, 1});
		
		int number = 1;
		if (this.getGame().getButtons() != null) {
			for (int i = 0; i < this.getGame().getButtons().length; i++) {
				if (this.getGame().getButtons()[i].isBVisible()) {
					int x = (int)(this.getGame().getButtons()[i].getX());
					int y = (int)(this.getGame().getButtons()[i].getY());
					int width = (int)(this.getGame().getButtons()[i].getWidth());
					int height = (int)(this.getGame().getButtons()[i].getHeight());
					
					g.setColor(128, 128, 128, 255);
					g.drawFilledRect(x, y, width, height);
					g.setColor(48f/255f, 48f/255f, 48f/255f, 1.0f);
					g.drawRect(x, y, width, height);
					
					this.getGame().drawString(g, this.getGame().getButtons()[i].getFunction(), x + width/2, y + height/2 - ApoDiceMenu.font.mCharCellHeight/2, ApoDiceMenu.font);
					
					for (int dice = 0; dice < 2; dice++) {
						x += dice * width;
						
						g.setColor(255, 255, 255, 255);
						g.drawFilledRoundRect(x - height/2, y, height, height, 6, 10);

						g.setLineSize(3.0f);
						g.setColor(48, 48, 48);
						g.drawRoundRect(x - height/2, y, height, height, 6, 10);
						
						g.setLineSize(1.0f);
						
						if ((number == 1) || (number == 3) || (number == 5)) {
							g.drawFilledCircle(x - height/2 + 30, y + 30, 6, 40);
						}
						if ((number == 2) || (number == 3) || (number == 4) || (number == 5)) {
							g.drawFilledCircle(x - height/2 + 14, y + 14, 6, 40);
							g.drawFilledCircle(x - height/2 + 46, y + 46, 6, 40);
						}
						if ((number == 4) || (number == 5)) {
							g.drawFilledCircle(x - height/2 + 46, y + 14, 6, 40);
							g.drawFilledCircle(x - height/2 + 14, y + 46, 6, 40);
						}
					}
					number += 1;
				}
			}
		}
	}

}
