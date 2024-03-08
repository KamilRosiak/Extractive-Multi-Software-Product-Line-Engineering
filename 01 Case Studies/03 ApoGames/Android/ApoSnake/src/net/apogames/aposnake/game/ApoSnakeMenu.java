package net.apogames.aposnake.game;

import net.apogames.aposnake.ApoSnakeModel;
import net.apogames.aposnake.level.ApoSnakeLevel;
import net.gliblybits.bitsengine.core.BitsFactory;
import net.gliblybits.bitsengine.core.BitsFont;
import net.gliblybits.bitsengine.core.BitsGame;
import net.gliblybits.bitsengine.render.BitsGraphics;

public class ApoSnakeMenu extends ApoSnakeModel {

	public static final String QUIT = "quit";
	public static final String PUZZLE = "puzzle";
	public static final String USERLEVELS = "userlevels";
	public static final String EDITOR = "editor";
	public static final String TITLE = "ApoSnake";
	
	public static BitsFont font;
	public static BitsFont game_font;
	public static BitsFont title_font;
	
	private float clockRotate;
	
	public ApoSnakeMenu(ApoSnakePanel game) {
		super(game);
	}

	@Override
	public void init() {
		this.loadFonts();
		
		this.getStringWidth().put(ApoSnakeMenu.QUIT, (int)(ApoSnakeMenu.font.getLength(ApoSnakeMenu.QUIT)));
		this.getStringWidth().put(ApoSnakeMenu.PUZZLE, (int)(ApoSnakeMenu.font.getLength(ApoSnakeMenu.PUZZLE)));
		this.getStringWidth().put(ApoSnakeMenu.USERLEVELS, (int)(ApoSnakeMenu.font.getLength(ApoSnakeMenu.USERLEVELS)));
		this.getStringWidth().put(ApoSnakeMenu.EDITOR, (int)(ApoSnakeMenu.font.getLength(ApoSnakeMenu.EDITOR)));
		this.getStringWidth().put(ApoSnakeMenu.TITLE, (int)(ApoSnakeMenu.title_font.getLength(ApoSnakeMenu.TITLE)));
		
		this.setUserlevels();
	}
	
	public void onResume() {
		this.loadFonts();
	}
	
	private void loadFonts() {
		ApoSnakeMenu.font = BitsFactory.getIt().getFont("reprise.ttf", 30);
		ApoSnakeMenu.title_font = BitsFactory.getIt().getFont("reprise.ttf", 38);
			
		ApoSnakeMenu.game_font = BitsFactory.getIt().getFont("reprise.ttf", 26);
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
		if (function.equals(ApoSnakeMenu.QUIT)) {
			this.onBackButtonPressed();
		} else if (function.equals(ApoSnakeMenu.PUZZLE)) {
			this.getGame().setPuzzleChooser();
		} else if (function.equals(ApoSnakeMenu.EDITOR)) {
			this.getGame().setEditor(false);
		} else if (function.equals(ApoSnakeMenu.USERLEVELS)) {
			this.getGame().setPuzzleGame(-1, "", true);
		}
	}
	
	public void onBackButtonPressed() {
		BitsGame.getIt().finish();
	}
	
	public void setUserlevels() {
		this.getGame().getButtons()[2].setVisible(true);
		if (ApoSnakeLevel.editorLevels == null) {
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
		
		this.getGame().drawString(g, ApoSnakeMenu.TITLE, 240, 45, ApoSnakeMenu.title_font, new float[] {1, 1, 1, 1}, new float[] {0, 0, 0, 1});
		
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
					
					this.getGame().drawString(g, this.getGame().getButtons()[i].getFunction(), x + width/2, y + height/2 - ApoSnakeMenu.font.mCharCellHeight/2, ApoSnakeMenu.font);
					
					for (int circle = 0; circle < 2; circle++) {
						x += circle * width;
						
						g.setColor(255, 0, 0, 255);
						if (number == 2) {
							g.setColor(0, 255, 0);
						} else if (number == 3) {
							g.setColor(0, 90, 200);
						} else if (number == 4) {
							g.setColor(255, 255, 0);
						}
						g.drawFilledCircle(x, y + height/2, height/2, height/2);

						g.setLineSize(3.0f);
						g.setColor(48, 48, 48);
						g.drawCircle(x, y + height/2, height/2, height/2);
						
						g.drawFilledRect(x - 5, y + 5, 4, 15);
						g.drawFilledRect(x + 1, y + 5, 4, 15);
						
						g.setLineSize(1.0f);
					}
					number += 1;
				}
			}
		}
	}

}
