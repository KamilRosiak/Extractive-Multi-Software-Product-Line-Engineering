package apoSkunkman.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFileChooser;

import org.apogames.ApoConstants;
import org.apogames.ApoScreen;

import Stinker.StinkerMain;
import apoSkunkman.ApoSkunkmanClassLoader;
import apoSkunkman.ApoSkunkmanConstants;
import apoSkunkman.ApoSkunkmanGameComponent;
import apoSkunkman.ApoSkunkmanImageContainer;
import apoSkunkman.ai.ApoSkunkmanAI;
import apoSkunkman.ai.ai.ApoAILeftRight;
import apoSkunkman.ai.ai.ApoAIRunner;
import apoSkunkman.debug.ApoSkunkmanDebug;
import apoSkunkman.entity.ApoSkunkmanStone;
import apoSkunkman.level.ApoSkunkmanLevel;
import apoSkunkman.replay.ApoSkunkmanReplayIO;

/**
 * eigentliche Spielklasse, die alles handelt und was angezeigt wird
 * @author Dirk Aporius
 *
 */
public class ApoSkunkmanPanel extends ApoSkunkmanGameComponent {
	/** Objekt zum Erstellen der ganzen Buttons, damit diese Klasse übersichtlich bleibt */
	private ApoSkunkmanButtons buttons;
	/** abstrakte Modelklasse von der das eigentliche Spiel und der Editor erben */
	private ApoSkunkmanModel model;
	/** Spielklasse, die das eigentliche Menu und Spiel handelt */
	private ApoSkunkmanModelGame game;
	/** Editorklasse, die die Anweisungen im Editor handeln */
	private ApoSkunkmanModelEditor editor;
	/** Hintergrundbild des Spiels */
	private BufferedImage iBackground;
	/** das eigentliche Level mit den Spielern und dem Spielfeld */
	private ApoSkunkmanLevel level;
	/** boolean Variable, ob ein Spiel läuft oder nicht */
	private boolean bGameRunning;
	/** boolean Variable, ob ein Spiel gerade simuliert wird oder nicht */
	private boolean bSimulation;
	/** Input/Output Objekt zum Speichern und Laden der Replays */
	private ApoSkunkmanReplayIO replayIO;
	/** das DebugFrame */
	private ApoSkunkmanDebug debugFrame;
	/** BooleanVariable die angibt, ob gerade der Editor gezeigt wird oder nicht */
	private boolean bEditor;
	/** Input/Output Objekt zum Speichern und Laden der Levels */
	private ApoSkunkmanEditorIO editorIO;
	
	/**
	 * Konstruktor
	 * @param screen : Screenobjekt
	 */
	public ApoSkunkmanPanel(ApoScreen screen) {
		super(screen);
	}
	
	/**
	 * wird beim initialisieren aufgerufen und setzt die Standardwerte für die Variablen
	 */
	public void load() {
		super.load();
		if (this.buttons == null) {
			this.buttons = new ApoSkunkmanButtons(this);
			this.buttons.init();
		}
		if (this.game == null) {
			this.game = new ApoSkunkmanModelGame(this);
		}
		if (this.level == null) {
			this.level = new ApoSkunkmanLevel(this);
		}
		if (this.editor == null) {
			this.editor = new ApoSkunkmanModelEditor(this);
		}
		if (this.replayIO == null) {
			this.replayIO = new ApoSkunkmanReplayIO(this.level);
		}
		if (this.editorIO == null) {
			this.editorIO = new ApoSkunkmanEditorIO(this);
		}
		this.level.init();
		this.editor.makeEditorLevel();
		this.setMenu();
		this.loadStartProperties();
	}
	
	/**
	 * wird einmal beim Start aufgerufen und läd die gespeicherten letzten ausgewählten KI's ein für alle Spieler
	 */
	private void loadStartProperties() {
		this.loadStartPlayer(ApoSkunkmanConstants.AI_ONE, 0);
		this.loadStartPlayer(ApoSkunkmanConstants.AI_TWO, 1);
		this.loadStartPlayer(ApoSkunkmanConstants.AI_THREE, 2);
		this.loadStartPlayer(ApoSkunkmanConstants.AI_FOUR, 3);
		this.setDebugMode(ApoSkunkmanConstants.DEBUG);
		
		if (ApoSkunkmanConstants.LEVEL_TYPE == ApoSkunkmanConstants.LEVEL_TYPE_EDITOR) {
			this.loadLevel(System.getProperty("user.dir") + File.separator + "levels" + File.separator + "lastLevel.skunk", false);
		}
	}
	
	/**
	 * läd eine KI für einen Spieler beim Start
	 * @param propertiesName : Wo liegt die KI?
	 * @param player : für welcher Spieler
	 */
	private void loadStartPlayer(final String propertiesName, final int player) {
		if ((propertiesName != null) && (propertiesName.length() > 0)) {
			try {
				int ai = Integer.valueOf(propertiesName);
				this.loadPlayer(player, ai, false);
			} catch (Exception ex) {
				this.loadPlayer(player, propertiesName);
			}
		}
	}
	
	/**
	 * setzt den Debugmodus auf den übergebenen Wert
	 * @param bDebug : TRUE Debugmode on, FALSE Debugmode off
	 */
	private void setDebugMode(final boolean bDebug) {
		ApoSkunkmanConstants.DEBUG = bDebug;
		if ((ApoSkunkmanConstants.DEBUG) && (!ApoConstants.B_APPLET)) {
			if (this.debugFrame == null) {
				this.debugFrame = new ApoSkunkmanDebug(this);
			} else {
				this.debugFrame.setVisible(true);
			}
		} else if ((!ApoSkunkmanConstants.DEBUG) && (!ApoConstants.B_APPLET)) {
			if (this.debugFrame != null) {
				this.debugFrame.setVisible(false);
			}
		}
	}
	
	/**
	 * gibt das Debugfenster zurück
	 * @return gibt das Debugfenster zurück
	 */
	public final ApoSkunkmanDebug getDebugFrame() {
		return this.debugFrame;
	}

	/**
	 * erstellt den kompletten Hintergrund des Spiels
	 */
	public void makeBackground(final boolean bWithLevel, final boolean bGameStart, final boolean bAnalysis, final boolean bEditor) {
		int gameWidth = ApoSkunkmanConstants.GAME_WIDTH;
		int gameHeight = ApoSkunkmanConstants.GAME_HEIGHT;
		
		this.iBackground = new BufferedImage(gameWidth, gameHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = this.iBackground.createGraphics();
		
		BufferedImage iHud = ApoSkunkmanImageContainer.iHud;
		BufferedImage iLevelTile = ApoSkunkmanImageContainer.iTile.getSubimage(0, 0, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE);
		for (int x = 0;  x < 17; x++) {
			for (int y = 0;  y < 20; y++) {
				g.drawImage(iLevelTile, x * ApoSkunkmanConstants.TILE_SIZE - ApoSkunkmanConstants.TILE_SIZE/2 + iHud.getWidth(), y * ApoSkunkmanConstants.TILE_SIZE - 8, null);
			}
		}
		BufferedImage iLevelTileStone = ApoSkunkmanImageContainer.iTile.getSubimage(1 * ApoSkunkmanConstants.TILE_SIZE, 0, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE);
		for (int x = 0;  x < ApoSkunkmanConstants.LEVEL_WIDTH; x++) {
			g.drawImage(iLevelTileStone, x * ApoSkunkmanConstants.TILE_SIZE + iHud.getWidth() + ApoSkunkmanConstants.TILE_SIZE/2, 0 * ApoSkunkmanConstants.TILE_SIZE + ApoSkunkmanConstants.CHANGE_Y_LEVEL, null);
			g.drawImage(iLevelTileStone, x * ApoSkunkmanConstants.TILE_SIZE + iHud.getWidth() + ApoSkunkmanConstants.TILE_SIZE/2, 14 * ApoSkunkmanConstants.TILE_SIZE + ApoSkunkmanConstants.CHANGE_Y_LEVEL, null);
			g.drawImage(iLevelTileStone, 0 * ApoSkunkmanConstants.TILE_SIZE + iHud.getWidth() + ApoSkunkmanConstants.TILE_SIZE/2, x * ApoSkunkmanConstants.TILE_SIZE + ApoSkunkmanConstants.CHANGE_Y_LEVEL, null);
			g.drawImage(iLevelTileStone, 14 * ApoSkunkmanConstants.TILE_SIZE + iHud.getWidth() + ApoSkunkmanConstants.TILE_SIZE/2, x * ApoSkunkmanConstants.TILE_SIZE + ApoSkunkmanConstants.CHANGE_Y_LEVEL, null);
		}

		if (this.level != null) {
			for (int x = 0;  x < ApoSkunkmanConstants.LEVEL_WIDTH; x++) {
				for (int y = 0; y < ApoSkunkmanConstants.LEVEL_HEIGHT; y++) {
					if ((this.level.getLevel()[y][x] != null) && (this.level.getLevel()[y][x] instanceof ApoSkunkmanStone)) {
						g.drawImage(iLevelTileStone, x * ApoSkunkmanConstants.TILE_SIZE + iHud.getWidth() + ApoSkunkmanConstants.TILE_SIZE/2, y * ApoSkunkmanConstants.TILE_SIZE + ApoSkunkmanConstants.CHANGE_Y_LEVEL, null);
					}
				}
			}
		}
		
		g.drawImage(iHud, 0, 0, null);
		
		if (!bEditor) {
			BufferedImage iHudTime = ApoSkunkmanImageContainer.iTime;
			g.drawImage(iHudTime, iHud.getWidth() + (gameWidth - iHud.getWidth())/2 - iHudTime.getWidth()/2, 2 * ApoSkunkmanConstants.APPLICATION_SIZE, null);
			g.drawImage(iHudTime, iHud.getWidth() + (gameWidth - iHud.getWidth())/2 - iHudTime.getWidth()/2, gameHeight - iHudTime.getHeight() - 2 * ApoSkunkmanConstants.APPLICATION_SIZE, null);	
		}
		
		// Falls es zur Analyse kommt, male die Analyse noch mit aufs Bild
		if (bWithLevel) {
			this.renderLevel(g);
			this.level.renderRealLevel(g, ApoSkunkmanImageContainer.iHud.getWidth() + ApoSkunkmanConstants.TILE_SIZE/2, ApoSkunkmanConstants.CHANGE_Y_LEVEL);
			
			if (bAnalysis) {
				g.setColor(new Color(0, 0, 0, 200));
				g.fillRoundRect(ApoSkunkmanImageContainer.iHud.getWidth() + ApoSkunkmanConstants.TILE_SIZE/2, ApoSkunkmanConstants.CHANGE_Y_LEVEL, this.getLevel().getLevel()[0].length * ApoSkunkmanConstants.TILE_SIZE, this.getLevel().getLevel().length * ApoSkunkmanConstants.TILE_SIZE, 50, 50);
				g.setColor(Color.WHITE);
				g.drawRoundRect(ApoSkunkmanImageContainer.iHud.getWidth() + ApoSkunkmanConstants.TILE_SIZE/2, ApoSkunkmanConstants.CHANGE_Y_LEVEL, this.getLevel().getLevel()[0].length * ApoSkunkmanConstants.TILE_SIZE, this.getLevel().getLevel().length * ApoSkunkmanConstants.TILE_SIZE, 50, 50);
				this.level.renderAnalysis(g, ApoSkunkmanImageContainer.iHud.getWidth() + ApoSkunkmanConstants.TILE_SIZE/2, ApoSkunkmanConstants.CHANGE_Y_LEVEL);
			}
		} else if (bGameStart) {
			this.game.renderGameOptions(g, true);
			this.renderNamesForLevel(g);
		}
		
		g.dispose();
		
		if (!this.shouldRepaint()) {
			super.render();
		}
	}

	/**
	 * setzt das Spiel in den Menumodus (mit den jeweiligen Buttons und Anzeigen)
	 */
	public void setMenu() {
		this.model = this.game;
		this.model.init();
		this.level.setReplayShowing(false);
		this.bGameRunning = false;
		this.bEditor = false;
		this.makeBackground(true, false, false, this.bEditor);
		
		this.setButtonVisible(ApoSkunkmanConstants.BUTTON_GAME);
		this.level.setVisibleButtonsForPlayer();
	}
	
	/**
	 * gibt das Editorobjekt zurück
	 * @return gibt das Editorobjekt zurück
	 */
	public final ApoSkunkmanModelEditor getEditor() {
		return this.editor;
	}

	/**
	 * setzt das Spiel in den Editormodus (mit den jeweiligen Buttons und Anzeigen)
	 */
	public void setEditor() {
		this.model = this.editor;
		this.model.init();
		this.level.setReplayShowing(false);
		this.bEditor = true;
		this.level.restart();
		this.editor.makeEditorLevel();
		this.editor.setEditorLevel();
		this.level.setType(ApoSkunkmanConstants.LEVEL_TYPE_EDITOR);
		this.makeBackground(true, false, false, true);
		
		this.setButtonVisible(ApoSkunkmanConstants.BUTTON_EDITOR);
	}
	
	/**
	 * gibt zurück, ob sich das Spiel gerade im EditorMode befindet oder nicht 
	 * @return TRUE, gerade im Editormode, ansonsten FALSE
	 */
	public boolean isEditorMode() {
		return this.bEditor;
	}

	/**
	 * gibt das Levelobjekt zurück
	 * @return gibt das Levelobjekt zurück
	 */
	public final ApoSkunkmanLevel getLevel() {
		return this.level;
	}
	
	/**
	 * boolean Variable, die angibt ob ein Spiel gerade läuft oder nicht
	 * @return TRUE, Spiel läuft gerade, FALSE Spiel läuft nicht
	 */
	public final boolean isGameRunning() {
		return this.bGameRunning;
	}
	
	/**
	 * boolean Variable, die angibt ob ein Spiel gerade simuliert wird oder nicht
	 * @return TRUE, Spiel wird gerade simuliert, FALSE Spiel läuft nicht
	 */
	public final boolean isSimulate() {
		return this.bSimulation;
	}
	
	public final void setSimulation(boolean bSimulation) {
		this.bSimulation = bSimulation;
	}

	/**
	 * wird aufgerufen, wenn der Start-Button gedrückt wird und startet das Spiel
	 */
	public void startGame() {
		if (!this.bGameRunning) {
			for (int p = 0; p < this.level.getMaxPlayers(); p++) {
				this.level.getPlayers()[p].loadPlayer();
			}
			this.bGameRunning = true;
			this.makeBackground(false, true, false, false);
			this.setShouldRepaint(true);
			this.setButtonVisible(ApoSkunkmanConstants.BUTTON_PLAY);
			if (this.debugFrame != null) {
				this.debugFrame.clear();
			}
		}
	}
	
	/**
	 * wird aufgerufen, wenn der Stop-Button gedrückt wird und stoppt das eigentliche Spiel ohne Gewinner
	 */
	public void stopGame() {
		if (this.bGameRunning) {
			this.setShouldRepaint(false);
			this.bGameRunning = false;
			if (this.level.isWin()) {
				this.setButtonVisible(ApoSkunkmanConstants.BUTTON_ANALYSIS);
				this.makeBackground(true, false, true, false);
			} else {
				this.setButtonVisible(ApoSkunkmanConstants.BUTTON_GAME);
				this.makeBackground(true, false, false, false);
			}
			
			if (this.level != null) {
				this.level.setVisibleButtonsForPlayer();
			}
		}
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
			this.getButtons()[5].setBVisible(false);
			this.getButtons()[6].setBVisible(false);
			this.getButtons()[7].setBVisible(false);
			this.getButtons()[10].setBVisible(false);
			this.getButtons()[13].setBVisible(false);
			this.getButtons()[16].setBVisible(false);
			this.getButtons()[30].setBVisible(false);
			this.getButtons()[37].setBVisible(false);
			this.getButtons()[38].setBVisible(false);
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
			super.render();
		} else if (keyCode == KeyEvent.VK_B) {
			this.setDebugMode(!ApoSkunkmanConstants.DEBUG);
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
	
	/**
	 * laedt eine PlayerAI
	 * @param player = fuer welchen Spieler
	 * @param s = falls != NULL dann lade den uebergeben String
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void loadPlayer(final int player, final String s) {
		if (ApoConstants.B_APPLET) {
			return;
		}
		this.level.setReplayShowing(false);
		try {
			String res = null;
			String source = null;
			String playerName = null;
			String className = null;
			if (s == null) {
				if (this.getFileChooser().showOpenDialog(this.screen.getComponent()) == JFileChooser.APPROVE_OPTION) {
					res = this.getFileChooser().getSelectedFile().getPath();
					className = res.substring(res.lastIndexOf("."));
					res = res.substring(0, res.lastIndexOf("."));
					source = res.substring(0, res.lastIndexOf(File.separator)) + File.separator;
					res = res.substring(res.lastIndexOf(File.separator) + 1);
				} else {
					return;
				}
				if ((res == null) || (res.length() <= 0)) {
					return;    	
				}
				playerName	= res;
				this.loadPlayer(player, source, playerName, className);
			} else {
				try {
					int ai = Integer.valueOf(s);
					this.loadPlayer(player, ai, true);
				} catch (Exception ex) {
					res	= s;
			    	playerName = res.substring(res.lastIndexOf("\\") + 1, res.lastIndexOf("."));
					source = res.substring(0,res.lastIndexOf("\\") + 1);
					className = res.substring(res.lastIndexOf("."));
					this.loadPlayer(player, source, playerName, className);
				}
			}
		} catch (Exception ex) {
			this.loadPlayer(player, ApoSkunkmanConstants.AI_RUNNER, true);
		}
	}
	
	/**
	 * laed eine KI, wobei übergeben wird um welchen Spieler es sich handelt und welche KI geladen werden soll
	 * @param player : Integervariable die angibt, welcher Spieler es ist
	 * @param ai : Integervariable die angibt, welche KI geladen werden soll
	 */
	public void loadPlayer(final int player, int ai, final boolean bSave) {
		ApoSkunkmanAI myAI = null;
		if (ai == ApoSkunkmanConstants.AI_LEFTRIGHT) {
			myAI = new ApoAILeftRight();
		} else if (ai == ApoSkunkmanConstants.AI_RUNNER) {
			myAI = new ApoAIRunner();
		} else if (ai == ApoSkunkmanConstants.AI_STINKY) {
			myAI = new StinkerMain();
		} else {
			ai = ApoSkunkmanConstants.AI_HUMAN;
			myAI = null;
		}
		this.game.getCurAI()[player] = ai;
		this.getLevel().getPlayers()[player].setAi(myAI, "", this);
		if (bSave) {
			if (player == 0) {
				ApoSkunkmanConstants.AI_ONE = String.valueOf(ai);
			} else if (player == 1) {
				ApoSkunkmanConstants.AI_TWO = String.valueOf(ai);
			} else if (player == 2) {
				ApoSkunkmanConstants.AI_THREE = String.valueOf(ai);
			} else if (player == 3) {
				ApoSkunkmanConstants.AI_FOUR = String.valueOf(ai);
			}
			ApoSkunkmanConstants.saveProperties();
		}
		this.makeBackground(true, false, false, false);
	}
	
	/**
	 * laed einen Spieler, wobei uebergeben wird welcher Spieler es ist, wie der Pfad lautet und wie die KI lautet
	 * @param player : Integervariable die angibt, welcher Spieler es ist
	 * @param path : Der Pfad wo die KI drinliegt
	 * @param classname : Der Klassenname
	 * @return immer TRUE
	 */
	private boolean loadPlayer(int player, String path, String classname, String classN) {
    	ApoSkunkmanAI apoSkunkmanAI = null;
		if ((classN != null) && (classN.length() > 0) && (classN.indexOf(".class") != -1)) {
			if (!ApoConstants.B_APPLET) {
				try {
					apoSkunkmanAI = (ApoSkunkmanAI)(new ApoSkunkmanClassLoader(path, classname).getAI());
				} catch (Exception ex) {
					if (player == 0) {
						this.loadPlayer(player, ApoSkunkmanConstants.AI_HUMAN, true);
					} else {
						this.loadPlayer(player, ApoSkunkmanConstants.AI_RUNNER, true);						
					}
					return true;
				}
			}
		}
		try {
        	if (apoSkunkmanAI != null) {
        		this.getLevel().getPlayers()[player].setAi(apoSkunkmanAI, path, this);
        		if (player == 0) {
        			ApoSkunkmanConstants.AI_ONE = path + classname + classN;
       			} else if (player == 1) {
       				ApoSkunkmanConstants.AI_TWO = path + classname + classN;
       			} else if (player == 2) {
       				ApoSkunkmanConstants.AI_THREE = path + classname + classN;
       			} else if (player == 3) {
       				ApoSkunkmanConstants.AI_FOUR = path + classname + classN;
       			}
        		ApoSkunkmanConstants.saveProperties();
        	}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Methode zum Speichern eines Replays
	 */
	public void saveReplay() {
		if (this.getFileChooserReplay().showSaveDialog(this.screen.getComponent()) == JFileChooser.APPROVE_OPTION) {
			String path = this.getFileChooserReplay().getSelectedFile().getPath();
			if (path.indexOf(this.getFileFilterReplay().getLevelName()) == -1) {
				path = path + this.getFileFilterReplay().getLevelName();
			}
			if (this.level.getType() == ApoSkunkmanConstants.LEVEL_TYPE_EDITOR) {
				this.level.getReplay().setEditorLevelString("Editor");
			} else {
				this.level.getReplay().setEditorLevelString("");
			}
			this.replayIO.writeLevel(path, this.level.getReplay());
		}
	}
	
	/**
	 * Methode zum Laden eines Replays
	 */
	public void loadReplay() {
		if (super.getFileChooserReplay().showOpenDialog(this.screen.getComponent()) == JFileChooser.APPROVE_OPTION) {
			String path = super.getFileChooserReplay().getSelectedFile().getPath();
			this.replayIO.readLevel(path);
			
			this.game.startReplay();
		}
	}
	
	/**
	 * Methode zum Speichern eines Levels
	 */
	public void saveLevel(String s) {
		String path = s;
		if ((path == null) || (path.length() <= 0)) {
			if (this.getFileChooserEditor().showSaveDialog(this.screen.getComponent()) == JFileChooser.APPROVE_OPTION) {
				path = this.getFileChooserEditor().getSelectedFile().getPath();
			}
		}
		if (path != null) {
			if (path.indexOf(this.getFileFilterEditor().getLevelName()) == -1) {
				path = path + this.getFileFilterEditor().getLevelName();
			}
			this.editorIO.writeLevel(path);
		}
	}
	
	/**
	 * Methode zum Laden eines Levels
	 * @param übergebener Pfad, wenn NULL dann showOpenDialoag
	 */
	public void loadLevel(String s, boolean bEditor) {
		String path = s;
		if (path == null) {
			if (super.getFileChooserEditor().showOpenDialog(this.screen.getComponent()) == JFileChooser.APPROVE_OPTION) {
				path = super.getFileChooserEditor().getSelectedFile().getPath();
			}
		}
		if (path != null) {
			this.editorIO.readLevel(path);
			this.editor.makeEditorLevel();
			this.editor.setEditorLevel();
			this.makeBackground(true, false, false, bEditor);
		}
	}
	
	@Override
	public void think(long delta) {
		if (this.model != null) {
			this.model.think(delta);
		}
		if (ApoSkunkmanConstants.DEBUG) {
			if ((this.debugFrame != null) && (this.debugFrame.isVisible())) {
				this.debugFrame.refresh();
			}
		}
	}
	
	@Override
	protected void render(Graphics2D g) {
		if (g == null) {
			return;
		}
		if (this.iBackground != null) {
			g.drawImage(this.iBackground, 0, 0, null);
		}
		this.renderLevel(g);
		this.renderModel(g);

		this.renderButtons(g);
		
		this.renderFPS(g);
	}
	
	/**
	 * rendert das eigentliche Level
	 * @param g : Grafics2D-Object
	 */
	private void renderLevel(Graphics2D g) {
		if (this.level != null) {
			this.level.render(g, ApoSkunkmanImageContainer.iHud.getWidth() + ApoSkunkmanConstants.TILE_SIZE/2, ApoSkunkmanConstants.CHANGE_Y_LEVEL);
		}
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
	 * rendert die Namen der Spieler auf den Baumstamm
	 * @param g : Graphicsobjekt
	 */
	public void renderNamesForLevel(Graphics2D g) {
		this.renderStasticsForLevel(g, true, false);
	}
	
	/**
	 * rendert die Punkte der Spieler auf den Baumstamm
	 * @param g : Graphicsobjekt
	 */
	public void renderPointsForLevel(Graphics2D g) {
		this.renderStasticsForLevel(g, false, true);
	}
	
	/**
	 * rendert die Statistiken der Spieler während des Spiel
	 * @param g : Graphicsobjekt
	 * @param bName : boolean Variable, ob der Name gezeichnet werden soll
	 * @param bPoints : boolean Variable, ob die Punkte der Spieler gezeichnet werden sollen
	 */
	private void renderStasticsForLevel(Graphics2D g, boolean bName, boolean bPoints) {
		for (int i = 0; i < this.getLevel().getPlayers().length; i++) {
			if (i < this.getLevel().getMaxPlayers()) {
				int orgX = (int)(this.getButtons()[8 + 3*i].getX() + this.getButtons()[8 + 3*i].getWidth() + 2 * ApoSkunkmanConstants.APPLICATION_SIZE);
				int y = (int)(this.getButtons()[8 + 3*i].getY() + 2 * ApoSkunkmanConstants.APPLICATION_SIZE + ApoSkunkmanImageContainer.iHudTree.getHeight()/2 - this.getButtons()[8 + 3*i].getHeight());
				g.drawImage(ApoSkunkmanImageContainer.iHudTree, orgX, y, null);
				int x = orgX + ApoSkunkmanImageContainer.iHudTree.getWidth()/2 - this.getLevel().getPlayers()[i].getIHead().getWidth()/2 + 2 * ApoSkunkmanConstants.APPLICATION_SIZE;
				if (i % 3 != 0) {
					g.drawImage(this.getLevel().getPlayers()[i].getIHead(), x, y - this.getLevel().getPlayers()[i].getIHead().getHeight(), null);					
				} else {
					g.drawImage(this.getLevel().getPlayers()[i].getIHead(), x, y + ApoSkunkmanImageContainer.iHudTree.getHeight(), null);
				}
				
				g.setFont(ApoSkunkmanConstants.FONT_LEVEL_TIME);
				String name = this.getLevel().getPlayers()[i].getPlayerName();
				if (this.level.isReplayShowing()) {
					name = this.level.getReplay().getNames()[i];
				}
				if (bPoints) {
					g.setFont(ApoSkunkmanConstants.FONT_HUD_TREE);
					name = String.valueOf(this.getLevel().getPlayers()[i].getPoints());
				}
				if (name != null) {
					Shape shape = g.getClip();
					g.setClip(new Rectangle2D.Double(orgX, y, ApoSkunkmanImageContainer.iHudTree.getWidth(), ApoSkunkmanImageContainer.iHudTree.getHeight()));
					ApoSkunkmanConstants.drawString(g, name, orgX + ApoSkunkmanImageContainer.iHudTree.getWidth()/2, y + ApoSkunkmanImageContainer.iHudTree.getHeight() - 2 * ApoSkunkmanConstants.APPLICATION_SIZE, true);
					g.setClip(shape);
				}
			}
		}
	}

	/**
	 * rendert, wenn gewollt, die Frames per Second auf den Display
	 * @param g : Graphicsobjekt
	 */
	private void renderFPS(Graphics2D g) {
		if (this.isShowFPS()) {
			g.setFont(ApoSkunkmanConstants.FONT_LEVEL_FPS);
			int h = g.getFontMetrics().getHeight() - g.getFontMetrics().getDescent();
			ApoSkunkmanConstants.drawString(g, "FPS: "+super.getFPS()+" ms", 2 * ApoSkunkmanConstants.APPLICATION_SIZE, ApoSkunkmanConstants.GAME_HEIGHT - 25 * ApoSkunkmanConstants.APPLICATION_SIZE, false, Color.BLACK, Color.WHITE);
			ApoSkunkmanConstants.drawString(g, "render: "+super.getRenderTime()+" ns", 2 * ApoSkunkmanConstants.APPLICATION_SIZE, ApoSkunkmanConstants.GAME_HEIGHT - 25 * ApoSkunkmanConstants.APPLICATION_SIZE - 1 * h, false, Color.BLACK, Color.WHITE);
			ApoSkunkmanConstants.drawString(g, "render: "+(super.getRenderTime()/1000000)+" ms", 2 * ApoSkunkmanConstants.APPLICATION_SIZE, ApoSkunkmanConstants.GAME_HEIGHT - 25 * ApoSkunkmanConstants.APPLICATION_SIZE - 2 * h, false, Color.BLACK, Color.WHITE);
			ApoSkunkmanConstants.drawString(g, "think: "+super.getThinkTime()+" ns" , 2 * ApoSkunkmanConstants.APPLICATION_SIZE, ApoSkunkmanConstants.GAME_HEIGHT - 25 * ApoSkunkmanConstants.APPLICATION_SIZE - 3 * h, false, Color.BLACK, Color.WHITE);
			ApoSkunkmanConstants.drawString(g, "think: "+(super.getThinkTime()/1000000)+" ms" , 2 * ApoSkunkmanConstants.APPLICATION_SIZE, ApoSkunkmanConstants.GAME_HEIGHT - 25 * ApoSkunkmanConstants.APPLICATION_SIZE - 4 * h, false, Color.BLACK, Color.WHITE);
		}
	}
}