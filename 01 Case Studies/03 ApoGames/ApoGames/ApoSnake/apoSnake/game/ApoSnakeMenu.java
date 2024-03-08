package apoSnake.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import apoSnake.ApoSnakeConstants;
import apoSnake.ApoSnakeModel;
import apoSnake.game.level.ApoSnakeLevel;

public class ApoSnakeMenu extends ApoSnakeModel {

	public static final String QUIT = "quit";
	public static final String PUZZLE = "puzzle";
	public static final String USERLEVELS = "userlevels";
	public static final String EDITOR = "editor";
	public static final String TITLE = "ApoSnake";
	
	public static Font font;
	public static Font game_font;
	public static Font title_font;
	
	public ApoSnakeMenu(ApoSnakePanel game) {
		super(game);
	}

	@Override
	public void init() {
		this.loadFonts();
		
		this.setUserlevels();
	}
	
	public void onResume() {
		this.loadFonts();
	}
	
	private void loadFonts() {
		
		
		ApoSnakeMenu.font = ApoSnakeConstants.ORIGINAL_FONT.deriveFont(30f);
		ApoSnakeMenu.title_font = ApoSnakeConstants.ORIGINAL_FONT.deriveFont(38f);
			
		ApoSnakeMenu.game_font = ApoSnakeConstants.ORIGINAL_FONT.deriveFont(26f);
	}	

	@Override
	public void keyButtonReleased(int button, char character) {
		
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoSnakeMenu.QUIT)) {
			this.onBackButtonPressed();
		} else if (function.equals(ApoSnakeMenu.PUZZLE)) {
			this.getGame().setPuzzleChooser();
		} else if (function.equals(ApoSnakeMenu.EDITOR)) {
			this.getGame().setEditor(false);
		} else if (function.equals(ApoSnakeMenu.USERLEVELS)) {
			this.getGame().setPuzzleGame(0, "", true);
		}
	}

	@Override
	public void mouseButtonReleased(int x, int y, boolean bRight) {
		
	}
	
	public void onBackButtonPressed() {
		System.exit(0);
	}
	
	public void setUserlevels() {
		this.getGame().getButtons()[2].setBVisible(true);
		if (ApoSnakeLevel.editorLevels == null) {
			this.getGame().getButtons()[2].setBVisible(false);
		}
	}
	
	@Override
	public void think(int delta) {
	}

	@Override
	public void render(final Graphics2D g) {
		this.getGame().drawString(g, ApoSnakeMenu.TITLE, 240, 45, ApoSnakeMenu.title_font, new float[] {1, 1, 1, 1}, new float[] {0, 0, 0, 1});
		
		int number = 1;
		if (this.getGame().getButtons() != null) {
			for (int i = 0; i < this.getGame().getButtons().length; i++) {
				if (this.getGame().getButtons()[i].isBVisible()) {
					int x = (int)(this.getGame().getButtons()[i].getX());
					int y = (int)(this.getGame().getButtons()[i].getY());
					int width = (int)(this.getGame().getButtons()[i].getWidth());
					int height = (int)(this.getGame().getButtons()[i].getHeight());
					
					g.setColor(new Color(128, 128, 128, 255));
					g.fillRect(x, y, width, height);
					g.setColor(new Color(48f/255f, 48f/255f, 48f/255f, 1.0f));
					g.drawRect(x, y, width, height);
					
					int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
					this.getGame().drawString(g, this.getGame().getButtons()[i].getFunction(), x + width/2, y + height/2 - h/2, ApoSnakeMenu.font);
					
					for (int circle = 0; circle < 2; circle++) {
						x += circle * width;
						
						g.setColor(new Color(255, 0, 0, 255));
						if (number == 2) {
							g.setColor(new Color(0, 255, 0));
						} else if (number == 3) {
							g.setColor(new Color(0, 90, 200));
						} else if (number == 4) {
							g.setColor(new Color(255, 255, 0));
						}
						g.fillOval(x - height/2, y, height, height);

						g.setStroke(new BasicStroke(3.0f));
						g.setColor(new Color(48, 48, 48));
						g.drawOval(x - height/2, y, height, height);
						
						g.fillRect(x - 5, y + 5, 4, 15);
						g.fillRect(x + 1, y + 5, 4, 15);
						
						g.setStroke(new BasicStroke(1.0f));
					}
					number += 1;
				}
			}
		}
	}

}
