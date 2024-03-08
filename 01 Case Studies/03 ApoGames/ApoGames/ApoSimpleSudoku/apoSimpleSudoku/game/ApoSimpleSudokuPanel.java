package apoSimpleSudoku.game;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import org.apogames.ApoConstants;
import org.apogames.ApoScreen;

import apoSimpleSudoku.ApoSimpleSudokuConstants;
import apoSimpleSudoku.ApoSimpleSudokuGameComponent;

/**
 * eigentliche Spielklasse, die alles handelt und was angezeigt wird
 * @author Dirk Aporius
 *
 */
public class ApoSimpleSudokuPanel extends ApoSimpleSudokuGameComponent {
	/** Objekt zum Erstellen der ganzen Buttons, damit diese Klasse übersichtlich bleibt */
	private ApoSimpleSudokuButtons buttons;
	/** abstrakte Modelklasse von der das eigentliche Spiel und der Editor erben */
	private ApoSimpleSudokuModel model;
	/** Spielklasse, die das eigentliche Spiel handelt */
	private ApoSimpleSudokuGame game;
	/** Menu */
	private ApoSimpleSudokuMenu menu;
	/** Credits */
	private ApoSimpleSudokuCredits credits;
	/** Options */
	private ApoSimpleSudokuOptions options;
	/** Help */
	private ApoSimpleSudokuHelp help;
	/** LevelChooser */
	private ApoSimpleSudokuLevelChooser levelChooser;
	
	private boolean bGameRunning;
	
	private boolean bWin;
	
	/**
	 * Konstruktor
	 * @param screen : Screenobjekt
	 */
	public ApoSimpleSudokuPanel(ApoScreen screen) {
		super(screen);
	}
	
	/**
	 * wird beim initialisieren aufgerufen und setzt die Standardwerte für die Variablen
	 */
	public void load() {
		super.load();
		if (this.buttons == null) {
			this.buttons = new ApoSimpleSudokuButtons(this);
			this.buttons.init();
		}
		if (this.game == null) {
			this.game = new ApoSimpleSudokuGame(this);
		}
		if (this.menu == null) {
			this.menu = new ApoSimpleSudokuMenu(this);
		}
		if (this.credits == null) {
			this.credits = new ApoSimpleSudokuCredits(this);
		}
		if (this.options == null) {
			this.options = new ApoSimpleSudokuOptions(this);
		}
		if (this.help == null) {
			this.help = new ApoSimpleSudokuHelp(this);
		}
		if (this.levelChooser == null) {
			this.levelChooser = new ApoSimpleSudokuLevelChooser(this);
		}

		this.setMenu();
	}

	public boolean isWinGame() {
		return this.bWin;
	}

	public void setWinGame(boolean bWin) {
		this.bWin = bWin;
	}

	/**
	 * setzt das Spiel in den Menumodus (mit den jeweiligen Buttons und Anzeigen)
	 */
	public void setMenu() {
		this.stopGame();
		
		this.model = this.menu;
		this.model.init();
		
		this.setButtonVisible(ApoSimpleSudokuConstants.BUTTON_MENU);
		
		this.render();
	}
	
	/**
	 * setzt das Spiel in den Menumodus (mit den jeweiligen Buttons und Anzeigen)
	 */
	public void setGame(final int level) {
		this.model = this.game;
		this.game.init(level);
		
		this.setButtonVisible(ApoSimpleSudokuConstants.BUTTON_GAME);
		
		this.render();
	}
	
	/**
	 * setzt das Spiel in den Creditsmodus (mit den jeweiligen Buttons und Anzeigen)
	 */
	public void setCredits() {
		this.stopGame();
		
		this.model = this.credits;
		this.model.init();
		
		this.setButtonVisible(ApoSimpleSudokuConstants.BUTTON_CREDITS);
		
		this.render();
	}
	
	/**
	 * setzt das Spiel in den Optionsmodus (mit den jeweiligen Buttons und Anzeigen)
	 */
	public void setOptions() {
		this.stopGame();
		
		this.model = this.options;
		this.model.init();
		
		this.setButtonVisible(ApoSimpleSudokuConstants.BUTTON_OPTIONS);
		
		this.render();
	}
	
	/**
	 * setzt das Spiel in den Helpmodus (mit den jeweiligen Buttons und Anzeigen)
	 */
	public void setHelp() {
		this.stopGame();
		
		this.model = this.help;
		this.model.init();
		
		this.setButtonVisible(ApoSimpleSudokuConstants.BUTTON_HELP);
		
		this.render();
	}
	
	/**
	 * setzt das Spiel in den Levelchoosermodus (mit den jeweiligen Buttons und Anzeigen)
	 */
	public void setLevelChooser() {
		this.stopGame();
		
		this.model = this.levelChooser;
		this.model.init();
		
		this.setButtonVisible(ApoSimpleSudokuConstants.BUTTON_OPTIONS);
		
		this.render();
	}
	
	/**
	 * boolean Variable, die angibt ob ein Spiel gerade läuft oder nicht
	 * @return TRUE, Spiel läuft gerade, FALSE Spiel läuft nicht
	 */
	public final boolean isGameRunning() {
		return this.bGameRunning;
	}

	/**
	 * wird aufgerufen, wenn der Start-Button gedrückt wird und startet das Spiel
	 */
	public void startGame() {
		if (!this.bGameRunning) {
			this.bGameRunning = true;
//			this.setShouldRepaint(true);
			this.setGame(0);
		}
	}
	
	public Cursor getCursor() {
		return this.screen.getComponent().getCursor();
	}
	
	public void setCursor(Cursor cursor) {
		this.screen.getComponent().setCursor(cursor);
	}
	
	/**
	 * wird aufgerufen, wenn der Stop-Button gedrückt wird und stoppt das eigentliche Spiel ohne Gewinner
	 */
	public void stopGame() {
		if (this.bGameRunning) {
			this.setShouldRepaint(false);
			this.bGameRunning = false;
		}
	}
	
	public void solveLevel(int level) {
		this.levelChooser.solveLevel(level);
	}
	
	/**
	 * wechselt die Sichtbarkeit der Buttons auf den übergebenen Wert
	 * @param bVisibile : Array mit boolean Variablen, ob ein Button sichtbar ist oder nicht
	 */
	public void setButtonVisible(boolean[] bVisibile) {
		for (int i = 0; i < this.getButtons().length && i < bVisibile.length; i++) {
			this.getButtons()[i].setBVisible(bVisibile[i]);
		}
		// falls es ein Applet ist, dann blende IMMER diese Buttons aus (z.B. der Quitbutton)
		if (ApoConstants.B_APPLET) {
			this.getButtons()[0].setBVisible(false);
		}
	}
	
	public void keyPressed(int keyCode, char keyCharacter) {
		if (this.model != null) {
			this.model.keyButtonPressed(keyCode, (char)(keyCode));
		}
	}

	public void keyReleased(int keyCode) {
		if (keyCode == KeyEvent.VK_F) {
			super.setShowFPS(!super.isShowFPS());
			if (!super.shouldRepaint()) {
				super.render();
			}
		}
		if (this.model != null) {
			this.model.keyButtonReleased(keyCode, (char)(keyCode));
		}
	}

	public void mouseDragged(int x, int y, boolean left) {
		if (this.model != null) {
			this.model.mouseDragged(x, y, !left);
		}
	}

	public void mouseMoved(int x, int y) {
		super.mouseMoved(x, y);
		if (this.model != null) {
			this.model.mouseMoved(x, y);
		}
	}

	public void mousePressed(int x, int y, boolean left) {
		super.mousePressed(x, y, left);
		if (this.model != null) {
			this.model.mousePressed(x, y, !left);
		}
	}

	public void mouseReleased(int x, int y, boolean left) {
		super.mouseReleased(x, y, left);
		if (this.model != null) {
			this.model.mouseReleased(x, y, !left);
		}
	}

	@Override
	public void setButtonFunction(String function) {
		if (this.model != null) {
			this.model.mouseButtonFunction(function);
		}
	}
	
	@Override
	public void think(long delta) {
		if (this.model != null) {
			this.model.think(delta);
		}
	}
	
	@Override
	protected void render(Graphics2D g) {
		if (g == null) {
			return;
		}

		this.renderModel(g);

		this.renderButtons(g);
		
		this.renderFPS(g);
	}
	
	/**
	 * rendert das derzeitige Model
	 * @param g : Grafics2D-Object
	 */
	private void renderModel(Graphics2D g) {
		if (this.model != null) {
			this.model.render(g);
		}
	}

	/**
	 * rendert, wenn gewollt, die Frames per Second auf den Display
	 * @param g : Graphicsobjekt
	 */
	private void renderFPS(Graphics2D g) {
		if (this.isShowFPS()) {
			g.setFont(ApoSimpleSudokuConstants.FONT_LEVEL_FPS);
			int h = g.getFontMetrics().getHeight() - g.getFontMetrics().getDescent();
			ApoSimpleSudokuConstants.drawString(g, "FPS: "+super.getFPS()+" ms", 2 * 2, ApoSimpleSudokuConstants.GAME_HEIGHT - 25 * 2, false, Color.BLACK, Color.WHITE);
			ApoSimpleSudokuConstants.drawString(g, "render: "+super.getRenderTime()+" ns", 2 * 2, ApoSimpleSudokuConstants.GAME_HEIGHT - 25 * 2 - 1 * h, false, Color.BLACK, Color.WHITE);
			ApoSimpleSudokuConstants.drawString(g, "render: "+(super.getRenderTime()/1000000)+" ms", 2 * 2, ApoSimpleSudokuConstants.GAME_HEIGHT - 25 * 2 - 2 * h, false, Color.BLACK, Color.WHITE);
			ApoSimpleSudokuConstants.drawString(g, "think: "+super.getThinkTime()+" ns" , 2 * 2, ApoSimpleSudokuConstants.GAME_HEIGHT - 25 * 2 - 3 * h, false, Color.BLACK, Color.WHITE);
			ApoSimpleSudokuConstants.drawString(g, "think: "+(super.getThinkTime()/1000000)+" ms" , 2 * 2, ApoSimpleSudokuConstants.GAME_HEIGHT - 25 * 2 - 4 * h, false, Color.BLACK, Color.WHITE);
		}
	}
}