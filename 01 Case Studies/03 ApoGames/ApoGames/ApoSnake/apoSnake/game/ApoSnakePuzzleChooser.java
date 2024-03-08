package apoSnake.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;

import apoSnake.ApoSnakeModel;
import apoSnake.entity.ApoLevelChooserButton;
import apoSnake.game.level.ApoSnakeLevel;

public class ApoSnakePuzzleChooser extends ApoSnakeModel {

	public static final String BACK = "back";
	public static final String LEVEL_CHOOSER = "ApoSnake - Levelchooser";
	
	private ApoLevelChooserButton[] levels;
	
	private int solved = 0;
	
	private int curShow = 0;
	
	private boolean bHandCursor;
	
	public ApoSnakePuzzleChooser(ApoSnakePanel game) {
		super(game);
	}

	@Override
	public void init() {		
		this.curShow = 0;
		
		this.bHandCursor = false;
		
		if (this.levels == null) {
			this.levels = new ApoLevelChooserButton[ApoSnakeLevel.MAX_LEVELS];
			
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
			this.setSolved(0, true);
		}
	}
	
	public final int getSolved() {
		return this.solved;
	}

	public final void setSolved(int solved, boolean bSave) {
		if (this.solved < solved) {
			this.solved = solved;
			if (this.solved > ApoSnakeLevel.MAX_LEVELS - 1) {
				this.solved = ApoSnakeLevel.MAX_LEVELS - 1;
			}
			if (this.solved < this.levels.length) {
				for (int i = 0; i < this.solved && i < this.levels.length; i++) {
					this.levels[i].setSolved(true);
				}
			}
			if (bSave) {
				this.getGame().savePreferences();
			}
		}
	}
	

	@Override
	public void keyButtonReleased(int button, char character) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoSnakePuzzleChooser.BACK)) {
			this.onBackButtonPressed();
		}
	}

	@Override
	public void mouseButtonReleased(int x, int y, boolean bRight) {
		
	}
	
	public boolean mouseMoved(int x, int y) {
		if (this.levels != null) {
			for (int i = this.curShow; i < this.curShow + 30 && i < this.levels.length && i <= this.solved; i++) {
				if (this.levels[i].intersects(x, y)) {
					if (!this.bHandCursor) {
						this.getGame().getScreen().getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						this.bHandCursor = true;
					}
					return true;
				}
			}
		}
		if (this.bHandCursor) {
			this.getGame().getScreen().getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			this.bHandCursor = false;
		}
		return false;
	}

	public boolean mousePressed(int x, int y, boolean bRight) {
		if (this.levels != null) {
			for (int i = this.curShow; i < this.curShow + 30 && i < this.levels.length && i <= this.solved; i++) {
				if (this.levels[i].intersects(x, y)) {
					this.getGame().setPuzzleGame(i, "", false);
					return true;
				}
			}
		}
		return false;
	}
	
	public void onBackButtonPressed() {
		this.getGame().getScreen().getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		this.getGame().setMenu();
	}

	@Override
	public void think(int delta) {
		
	}

	@Override
	public void render(final Graphics2D g) {
		this.getGame().drawString(g, ApoSnakePuzzleChooser.LEVEL_CHOOSER, 240, 2, ApoSnakeMenu.title_font, new float[] {1, 1, 1, 1}, new float[] {0, 0, 0, 1});
		
		this.getGame().renderButtons(g);
		
		if (this.levels != null) {
			for (int i = this.curShow; i < this.curShow + 30 && i < this.levels.length; i++) {
				int x = (int)(this.levels[i].getX());
				int y = (int)(this.levels[i].getY());
				int radius = (int)(this.levels[i].getWidth());
				
				g.setColor(new Color(255, 255, 255));
				if (this.levels[i].isSolved()) {
					g.setColor(new Color(102, 135, 89));
				} else if (this.solved < i) {
					g.setColor(new Color(128, 128, 128));
				}
				g.fillOval(x, y, radius, radius);

				g.setStroke(new BasicStroke(2.5f));
				g.setColor(new Color(48, 48, 48));
				g.drawOval(x, y, radius, radius);
				g.fillRect(x + radius/2 - 4, y + 10, 3, 6);
				g.fillRect(x + radius/2 + 1, y + 10, 3, 6);
				
				int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
				if (this.solved == i) {
					this.getGame().drawString(g, this.levels[i].getFunction(), x + radius/2, y + radius/2 - h/2, ApoSnakeMenu.font, new float[] {1, 1, 1, 1}, new float[] {0, 0, 0, 1});
				} else {
					this.getGame().drawString(g, this.levels[i].getFunction(), x + radius/2, y + radius/2 - h/2, ApoSnakeMenu.font);
				}
				
				g.setStroke(new BasicStroke(1.0f));
			}
		}
	}

}
