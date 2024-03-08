package apoSnake.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;

import org.apogames.ApoConstants;
import org.apogames.ApoScreen;
import org.apogames.help.ApoHelp;

import apoSnake.ApoSnakeButtons;
import apoSnake.ApoSnakeComponent;
import apoSnake.ApoSnakeConstants;
import apoSnake.game.level.ApoSnakeLevelIO;
import apoSnake.game.level.ApoSnakeUserlevels;

public class ApoSnakePanel extends ApoSnakeComponent {
	
	private ApoSnakeMenu menu;
	
	private ApoSnakePuzzleChooser puzzle;

	private ApoSnakePuzzleGame game;
	
	private ApoSnakeEditor editor;
	
	private int think;
	
	private ApoSnakeUserlevels userlevels;
	
	private ApoSnakeLevelIO prop;
	
	public ApoSnakePanel(final ApoScreen screen) {
		super(screen);
	}
	
	public void init() {
		super.init();
		
		super.setShouldRepaint(true);
		super.setShouldThink(true);
		
		ApoSnakeButtons buttons = new ApoSnakeButtons(this);
		buttons.init();
		
		this.think = 0;
		
		if (this.menu == null) {
			this.menu = new ApoSnakeMenu(this);
		}
		if (this.puzzle == null) {
			this.puzzle = new ApoSnakePuzzleChooser(this);
		}
		if (this.game == null) {
			this.game = new ApoSnakePuzzleGame(this);
		}
		if (this.editor == null) {
			this.editor = new ApoSnakeEditor(this);
		}
		
		this.setMenu();
		
		if (this.userlevels == null) {
			this.userlevels = new ApoSnakeUserlevels(this);
			this.loadUserlevels();
		}
		this.puzzle.init();
		
		this.loadPreferences();
	}
	
	public void loadPreferences() {
		int solved = 0;
		if (ApoConstants.B_APPLET) {
			String load;
			try {
				load = ApoHelp.loadData(new URL("http://www.apo-games.de/apoSnake/"), "apoSnake_solved");
				if ((load != null) && (load.length() > 0)) {
					solved = Integer.valueOf(load);
				} else {
					solved = 0;
				}
			} catch (MalformedURLException e) {
				solved = 0;
			} catch (Exception e) {
				solved = 0;
			}
		} else {
			this.prop = new ApoSnakeLevelIO();
			try {
				this.prop.readLevel("properties");
				solved = this.prop.getSolved();
			} catch (Exception ex) {
				this.prop.writeLevel("properties");
				solved = 0;
			}
		}
		if (this.puzzle != null) {
			this.puzzle.setSolved(solved, false);
		}
	}
	
	public void savePreferences() {
		if (ApoConstants.B_APPLET) {
			try {
				ApoHelp.saveData(new URL("http://www.apo-games.de/apoSnake/"), "apoSnake_solved", String.valueOf(this.puzzle.getSolved()));
			} catch (MalformedURLException e) {
			} catch (Exception e) {
			}
		} else {
			this.prop.setSolved(this.puzzle.getSolved());
			this.prop.writeLevel("properties");
		}
	}
	
	public final void loadUserlevels() {
		Thread t = new Thread(new Runnable() {

			public void run() {
				ApoSnakePanel.this.userlevels.loadUserlevels();
			}
 		});
 		t.start();
	}
	
	public void setUserlevelsVisible() {
		if (this.getModel().equals(this.menu)) {
			this.menu.setUserlevels();
		}
	}
	
	public final ApoSnakeUserlevels getUserlevels() {
		return this.userlevels;
	}

	protected final void setMenu() {
		if (super.getModel() != null) {
			super.getModel().close();
		}
		
		super.setModel(this.menu);
		
		this.setButtonVisible(ApoSnakeConstants.BUTTON_MENU);
		
		super.getModel().init();
	}
	
	protected final void setEditor(boolean bSolvedLevel) {
		if (super.getModel() != null) {
			super.getModel().close();
		}
		
		super.setModel(this.editor);
		
		this.setButtonVisible(ApoSnakeConstants.BUTTON_EDITOR);
		
		this.editor.setLevelSolved(bSolvedLevel);
		super.getModel().init();
	}
	
	protected final void setPuzzleChooser() {
		if (super.getModel() != null) {
			super.getModel().close();
		}
		
		super.setModel(this.puzzle);
		
		this.setButtonVisible(ApoSnakeConstants.BUTTON_PUZZLE);
		
		super.getModel().init();
	}
	
	protected final void setPuzzleGame(final int level, final String levelString, final boolean bUserLevel) {
		if (super.getModel() != null) {
			super.getModel().close();
		}
		
		super.setModel(this.game);
		
		this.setButtonVisible(ApoSnakeConstants.BUTTON_GAME);
		
		super.getModel().init();
		this.game.loadLevel(level, bUserLevel, levelString);
	}
	
	public final void setButtonVisible(boolean[] bVisibile) {
		for (int i = 0; i < this.getButtons().length && i < bVisibile.length; i++) {
			this.getButtons()[i].setBVisible(bVisibile[i]);
		}

		if (ApoConstants.B_APPLET) {
			super.getButtons()[0].setBVisible(false);
		}
	}
	
	@Override
	public void setButtonFunction(final String function) {
		if (super.getModel() != null) {
			super.getModel().mouseButtonFunction(function);
		}
	}
	
	public int getMaxCanChoosen() {
		return this.puzzle.getSolved();
	}
	
	public void solvedLevel(int level) {
		this.puzzle.setSolved(level, true);
	}
	
	@Override
	public void keyReleased(int keyCode, char keyChar) {
		if (keyCode == KeyEvent.VK_F) {
			super.setShowFPS(!super.isShowFPS());
		}
		if (this.getModel() != null) {
			this.getModel().keyButtonReleased(keyCode, keyChar);
		}
	}
	
	public void keyPressed(int keyCode, char keyCharacter) {
		if (this.getModel() != null) {
			this.getModel().keyPressed(keyCode, keyCharacter);
		}
	}

	@Override
	public boolean mouseReleased(int x, int y, boolean left) {
		if (!super.mouseReleased(x, y, left)) {
			if (this.getModel() != null) {
				this.getModel().mouseButtonReleased(x, y, !left);
			}
		}
		return false;
	}
	
	public void mousePressed(int x, int y, boolean left) {
		super.mousePressed(x, y, left);		
		if (this.getModel() != null) {
			this.getModel().mousePressed(x, y, !left);
		}
	}
	
	public void mouseMoved(int x, int y) {
		super.mouseMoved(x, y);
		if (this.getModel() != null) {
			this.getModel().mouseMoved(x, y);
		}
	}
	
	public void mouseDragged(int x, int y, boolean left) {
		super.mouseDragged(x, y, left);
		if (this.getModel() != null) {
			this.getModel().mouseDragged(x, y);
		}
	}
	
	@Override
	public void mouseWheelChanged(int changed) {
		if (this.getModel() != null) {
			this.getModel().mouseWheelChanged(changed);
		}
	}
	
	public void think(long delta) {		
		this.think += delta;
		
		// Update / think
		// Wenn 10 ms vergangen sind, dann denke nach
		while (this.think >= 10) {
			this.think -= 10;
			if (super.getModel() != null) {
				super.getModel().think((int)10);
			}	
		}
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(new Color(192, 192, 192));
		g.fillRect(0, 0, ApoSnakeConstants.GAME_WIDTH, ApoSnakeConstants.GAME_HEIGHT);
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (super.getModel() != null) {
			super.getModel().render(g);
		}
		super.renderButtons(g);
		if (super.getModel() != null) {
			super.getModel().drawOverlay(g);
		}
		if (ApoSnakeConstants.FPS) {
			this.renderFPS(g);
		}
	}

	protected void drawString(final Graphics2D g, final String s, final int x, final int y, final Font font) {
		this.drawString(g, s, x, y, font, new float[] {0, 0, 0, 1}, new float[] {1, 1, 1, 1});
	}
	
	protected void drawString(final Graphics2D g, final String s, final int x, final int y, final Font font, float[] colorBack, float[] colorFront) {
		int w = 0;
		g.setFont(font);
		w = g.getFontMetrics().stringWidth(s);
		
		int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
		
		g.setColor(new Color(colorBack[0], colorBack[1], colorBack[2], colorBack[3]));
		g.drawString(s, x - w/2 + 1, y + 1 + h);
		g.setColor(new Color(colorFront[0], colorFront[1], colorFront[2], colorFront[3]));
		g.drawString(s, x - w/2 + 0, y + 0 + h);
	}
	
	public void renderButtons(final Graphics2D g) {
		this.renderButtons(g, ApoSnakeMenu.font);
	}
	
	public void renderButtons(final Graphics2D g, final Font font) {
		g.setFont(font);
		if (this.getButtons() != null) {
			for (int i = 0; i < this.getButtons().length; i++) {
				if (this.getButtons()[i].isBVisible()) {
					int x = (int)(this.getButtons()[i].getX());
					int y = (int)(this.getButtons()[i].getY());
					int width = (int)(this.getButtons()[i].getWidth());
					int height = (int)(this.getButtons()[i].getHeight());
					
					g.setColor(new Color(160, 160, 160, 255));
					g.fillRoundRect(x, y, width, height, 6, 10);
					g.setStroke(new BasicStroke(2.0f));
					g.setColor(new Color(48f/255f, 48f/255f, 48f/255f, 1.0f));
					g.drawRoundRect(x, y, width, height, 6, 10);
					g.setStroke(new BasicStroke(1.0f));
					
					int h = (int)(g.getFontMetrics().getHeight() - 1.5f * g.getFontMetrics().getDescent());
					this.drawString(g, this.getButtons()[i].getFunction(), x + width/2, y + height/2 - h/2 - 3, font);
				}
			}
		}
	}
	
	/**
	 * rendert die Anzeige für die FPS und die Thinkzeit
	 * @param g : das Graphicsobjekt
	 */
	private void renderFPS(Graphics2D g) {
		if (super.isShowFPS()) {
			g.setColor(Color.RED);
			g.drawString("draw time: " + this.renderTime + " ns", 5, ApoSnakeConstants.GAME_HEIGHT - 25);
			g.drawString("draw time: " + (this.renderTime / 1000000) + " ms", 5, ApoSnakeConstants.GAME_HEIGHT - 15);
			g.drawString("FPS: " + super.getFPS(), 5, ApoSnakeConstants.GAME_HEIGHT - 5);
		}
	}
}
