package apoSkunkman.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import org.apogames.ApoConstants;
import org.apogames.help.ApoHelp;

import apoSkunkman.ApoSkunkmanConstants;
import apoSkunkman.ApoSkunkmanImageContainer;
import apoSkunkman.entity.ApoSkunkmanPlayer;

/**
 * Klasse, die das eigentliche Spiel repäsentiert und alle Aktionen dadrin handelt<br />
 * Vom laden, Speichern über die Mausevents usw.<br />
 * @author Dirk Aporius
 *
 */
public class ApoSkunkmanModelGame extends ApoSkunkmanModel {
	
	/** eindeutige Stringvariable für den QuitButton */
	public static String QUIT = "quit";
	/** eindeutige Stringvariable für den EditorButton */
	public static String EDITOR = "editor";
	/** eindeutige Stringvariable für das Laden eines Editorlevels */
	public static String LOAD_EDITORLEVEL = "load_editorlevel";
	/** eindeutige Stringvariable für das Laden eines Replays */
	public static String LOAD_REPLAY = "load_replay";
	/** eindeutige Stringvariable für den PlayButton */
	public static String PLAY_GAME = "play_game";
	/** eindeutige Stringvariable für den NewLevelButton */
	public static String NEW_LEVEL = "new_level";
	/** eindeutige Stringvariable für den LadenButton des ersten Spielers */
	public static String LOAD_PLAYER_ONE = "load_player_one";
	/** eindeutige Stringvariable für den Button des ersten Spielers, um nach Links zu scrollen durch die möglichen gespeicherten KI's */
	public static String CHANGE_PLAYER_ONE_LEFT = "change_player_one_left";
	/** eindeutige Stringvariable für den Button des ersten Spielers, um nach Rechts zu scrollen durch die möglichen gespeicherten KI's */
	public static String CHANGE_PLAYER_ONE_RIGHT = "change_load_player_one_right";
	/** eindeutige Stringvariable für den LadenButton des zweiten Spielers */
	public static String LOAD_PLAYER_TWO = "load_player_two";
	/** eindeutige Stringvariable für den Button des zweiten Spielers, um nach Links zu scrollen durch die möglichen gespeicherten KI's */
	public static String CHANGE_PLAYER_TWO_LEFT = "change_player_two_left";
	/** eindeutige Stringvariable für den Button des zweiten Spielers, um nach Rechts zu scrollen durch die möglichen gespeicherten KI's */
	public static String CHANGE_PLAYER_TWO_RIGHT = "change_load_player_two_right";
	/** eindeutige Stringvariable für den LadenButton des dritten Spielers */
	public static String LOAD_PLAYER_THREE = "load_player_three";
	/** eindeutige Stringvariable für den Button des dritten Spielers, um nach Links zu scrollen durch die möglichen gespeicherten KI's */
	public static String CHANGE_PLAYER_THREE_LEFT = "change_player_three_left";
	/** eindeutige Stringvariable für den Button des dritten Spielers, um nach Rechts zu scrollen durch die möglichen gespeicherten KI's */
	public static String CHANGE_PLAYER_THREE_RIGHT = "change_load_player_three_right";
	/** eindeutige Stringvariable für den LadenButton des vierten Spielers */
	public static String LOAD_PLAYER_FOUR = "load_player_four";
	/** eindeutige Stringvariable für den Button des vierten Spielers, um nach Links zu scrollen durch die möglichen gespeicherten KI's */
	public static String CHANGE_PLAYER_FOUR_LEFT = "change_player_four_left";
	/** eindeutige Stringvariable für den Button des vierten Spielers, um nach Rechts zu scrollen durch die möglichen gespeicherten KI's */
	public static String CHANGE_PLAYER_FOUR_RIGHT = "change_load_player_four_right";
	/** eindeutige Stringvariable für den StopButton */
	public static String STOP_GAME = "stop_game";
	/** eindeutige Stringvariable für den PauseButton */
	public static String PAUSE_GAME = "pause_game";
	/** eindeutige Stringvariable für den Button um die maximale Spieleranzahl zu verringern */
	public static String MAX_PLAYER_CHANGE_LEFT = "max_player_left";
	/** eindeutige Stringvariable für den Button um die maximale Spieleranzahl zu erhöhen */
	public static String MAX_PLAYER_CHANGE_RIGHT = "max_player_right";
	/** eindeutige Stringvariable für den Button um den Leveltypen zu verändern */
	public static String TYPE_CHANGE_LEFT = "type_change_left";
	/** eindeutige Stringvariable für den Button um den Leveltypen zu verändern */
	public static String TYPE_CHANGE_RIGHT = "type_change_right";
	/** eindeutige Stringvariable für den Button um einzustellen ob mit Busch oder ohne */
	public static String BUSH_CHANGE_LEFT = "bush_change_left";
	/** eindeutige Stringvariable für den Button um einzustellen ob mit Busch oder ohne */
	public static String BUSH_CHANGE_RIGHT = "bush_change_right";
	/** eindeutige Stringvariable für den Button um einzustellen wieviel Spielzeit zur Verfügung gestellt wird */
	public static String TIME_CHANGE_LEFT = "time_change_left";
	/** eindeutige Stringvariable für den Button um einzustellen wieviel Spielzeit zur Verfügung gestellt wird */
	public static String TIME_CHANGE_RIGHT = "time_change_right";
	/** eindeutige Stringvariable für den Button um ein Replay zu speichern */
	public static String ANALYSIS_SAVE_REPLAY = "save";
	/** eindeutige Stringvariable für den Button um ein Replay zu speichern */
	public static String ANALYSIS_PLAY_REPLAY = "play";
	/** eindeutige Stringvariable für den Button um einzustellen wie schnell das Spiel ablaufen soll */
	public static String SPEED_CHANGE_LEFT = "speed_change_left";
	/** eindeutige Stringvariable für den Button um einzustellen wie schnell das Spiel ablaufen soll */
	public static String SPEED_CHANGE_RIGHT = "speed_change_right";
	/** eindeutige Stringvariable f�r den Button um ein Spiel zu simulieren */
	public static String SIMULATION = "simulation";
	/** eindeutige Stringvariable f�r den Help-Button */
	public static String HELP = "help";
	
	/** ArrayList mit Integer, die Tasten enthalten, welche gerade gedrückt oder losgelassen wurden */
	private ArrayList<Integer> releasedKeys;
	/** Integer Array mit den Angaben welche KI gerade welcher Spieler hat */ 
	private int[] curAI;
	
	/**
	 * Konstruktor
	 * @param game : Das Gameobjekt der Hauptklasse für das Spiel
	 */
	public ApoSkunkmanModelGame(ApoSkunkmanPanel game) {
		super(game);
	}
	
	public void init() {
		if (this.releasedKeys == null) {
			this.releasedKeys = new ArrayList<Integer>();
		}
		this.releasedKeys.clear();
		
		if (this.curAI == null) {
			this.curAI = new int[ApoSkunkmanConstants.PLAYER_MAX_PLAYER];
			for (int i = 1; i < this.curAI.length; i++) {
				this.curAI[i] = ApoSkunkmanConstants.AI_RUNNER;
				if (i == 3) {
					this.curAI[i] = ApoSkunkmanConstants.AI_LEFTRIGHT;
				}
				this.getGame().loadPlayer(i, this.curAI[i], false);
			}
		}
	}

	public final int[] getCurAI() {
		return this.curAI;
	}

	public void keyButtonPressed(int button, char character) {
		this.releasedKeys.add(button);
	}
	
	@Override
	public void keyButtonReleased(int button, char character) {
		this.releasedKeys.add(button);
		if (this.getGame().isGameRunning()) {
			if (button == KeyEvent.VK_ESCAPE) {
				this.stopGame();
			} else if (button == KeyEvent.VK_E) {
				this.getGame().getLevel().setSpeedMode(this.getGame().getLevel().getSpeedMode() + 1);
			} else if (button == KeyEvent.VK_Q) {
				this.getGame().getLevel().setSpeedMode(this.getGame().getLevel().getSpeedMode() - 1);
			} else if (button == KeyEvent.VK_P) {
				this.getGame().getLevel().setPause(!this.getGame().getLevel().isPause());
			}
		} else {
			if (button == KeyEvent.VK_ESCAPE) {
				this.exitGame();
			} else if (button == KeyEvent.VK_N) {
				this.getGame().getLevel().makeNewLevel(-1);
			} else if (button == KeyEvent.VK_ENTER) {
				this.startGame();
			}
		}
	}

	@Override
	public void mouseReleased(int x, int y, boolean right) {
	}
	
	/**
	 * diese Methode schlie�t das Spiel und speichert davor die Properties
	 */
	private void exitGame() {
		if (!ApoConstants.B_APPLET) {
			if (this.getGame().getLevel().getType() == ApoSkunkmanConstants.LEVEL_TYPE_EDITOR) {
				ApoSkunkmanConstants.LEVEL_TYPE = this.getGame().getLevel().getType();
				this.getGame().getEditor().setEditorLevel();
			}
			this.getGame().getEditor().makeEditorLevel();
			this.getGame().saveLevel(System.getProperty("user.dir") + File.separator + "levels" + File.separator + "lastLevel.skunk");
			ApoSkunkmanConstants.saveProperties();
			System.exit(1);
		}
	}
	
	@Override
	public void mouseButtonFunction(String function) {
		if (ApoSkunkmanModelGame.QUIT.equals(function)) {
			this.exitGame();
		} else if (ApoSkunkmanModelGame.EDITOR.equals(function)) {
			this.getGame().setEditor();
		} else if (ApoSkunkmanModelGame.NEW_LEVEL.equals(function)) {
			this.getGame().getLevel().setReplayShowing(false);
			this.getGame().getLevel().makeNewLevel(-1);
		} else if (ApoSkunkmanModelGame.PLAY_GAME.equals(function)) {
			this.startGame();
		} else if (ApoSkunkmanModelGame.SIMULATION.equals(function)) {
			Thread t = new Thread(this.getGame()) {
				public void run() {
					simulation();
				}
			};
			t.start();
			
		} else if (ApoSkunkmanModelGame.STOP_GAME.equals(function)) {
			this.stopGame();
		} else if (ApoSkunkmanModelGame.PAUSE_GAME.equals(function)) {
			this.getGame().getLevel().setPause(!this.getGame().getLevel().isPause());
		} else if (ApoSkunkmanModelGame.MAX_PLAYER_CHANGE_LEFT.equals(function)) {
			this.getGame().getLevel().setReplayShowing(false);
			this.getGame().getLevel().setMaxPlayers(this.getGame().getLevel().getMaxPlayers() - 1);
			this.getGame().render();
		} else if (ApoSkunkmanModelGame.MAX_PLAYER_CHANGE_RIGHT.equals(function)) {
			this.getGame().getLevel().setReplayShowing(false);
			this.getGame().getLevel().setMaxPlayers(this.getGame().getLevel().getMaxPlayers() + 1);
			this.getGame().render();
		} else if (ApoSkunkmanModelGame.TYPE_CHANGE_LEFT.equals(function)) {
			this.getGame().getLevel().setReplayShowing(false);
			this.getGame().getLevel().setType(this.getGame().getLevel().getType() - 1);
			this.getGame().getLevel().setMaxPlayers(this.getGame().getLevel().getMaxPlayers());
			this.getGame().getLevel().makeNewLevel(ApoSkunkmanConstants.LEVEL_LASTRANDOM);
			this.getGame().render();
		} else if (ApoSkunkmanModelGame.TYPE_CHANGE_RIGHT.equals(function)) {
			this.getGame().getLevel().setReplayShowing(false);
			this.getGame().getLevel().setType(this.getGame().getLevel().getType() + 1);
			this.getGame().getLevel().setMaxPlayers(this.getGame().getLevel().getMaxPlayers());
			this.getGame().getLevel().makeNewLevel(ApoSkunkmanConstants.LEVEL_LASTRANDOM);
			this.getGame().render();
		} else if (ApoSkunkmanModelGame.BUSH_CHANGE_LEFT.equals(function)) {
			this.getGame().getLevel().setReplayShowing(false);
			this.getGame().getLevel().setBush(!this.getGame().getLevel().isBush(), true);
			this.getGame().render();
		} else if (ApoSkunkmanModelGame.BUSH_CHANGE_RIGHT.equals(function)) {
			this.getGame().getLevel().setReplayShowing(false);
			this.getGame().getLevel().setBush(!this.getGame().getLevel().isBush(), true);
			this.getGame().render();
		} else if (ApoSkunkmanModelGame.TIME_CHANGE_LEFT.equals(function)) {
			this.getGame().getLevel().setReplayShowing(false);
			this.getGame().getLevel().setTime(this.getGame().getLevel().getTime() - ApoSkunkmanConstants.TIME_CHANGE);
			this.getGame().makeBackground(true, false, false, false);
		} else if (ApoSkunkmanModelGame.TIME_CHANGE_RIGHT.equals(function)) {
			this.getGame().getLevel().setReplayShowing(false);
			this.getGame().getLevel().setTime(this.getGame().getLevel().getTime() + ApoSkunkmanConstants.TIME_CHANGE);
			this.getGame().makeBackground(true, false, false, false);
		} else if (ApoSkunkmanModelGame.SPEED_CHANGE_LEFT.equals(function)) {
			this.getGame().getLevel().setSpeedMode(this.getGame().getLevel().getSpeedMode() - 1);
		} else if (ApoSkunkmanModelGame.SPEED_CHANGE_RIGHT.equals(function)) {
			this.getGame().getLevel().setSpeedMode(this.getGame().getLevel().getSpeedMode() + 1);
		} else if (ApoSkunkmanModelGame.LOAD_PLAYER_ONE.equals(function)) {
			this.getGame().loadPlayer(0, null);
		} else if (ApoSkunkmanModelGame.CHANGE_PLAYER_ONE_LEFT.equals(function)) {
			this.changeAI(0, -1);
		} else if (ApoSkunkmanModelGame.CHANGE_PLAYER_ONE_RIGHT.equals(function)) {
			this.changeAI(0, +1);
		} else if (ApoSkunkmanModelGame.LOAD_PLAYER_TWO.equals(function)) {
			this.getGame().loadPlayer(1, null);
		} else if (ApoSkunkmanModelGame.CHANGE_PLAYER_TWO_LEFT.equals(function)) {
			this.changeAI(1, -1);
		} else if (ApoSkunkmanModelGame.CHANGE_PLAYER_TWO_RIGHT.equals(function)) {
			this.changeAI(1, +1);
		} else if (ApoSkunkmanModelGame.LOAD_PLAYER_THREE.equals(function)) {
			this.getGame().loadPlayer(2, null);
		} else if (ApoSkunkmanModelGame.CHANGE_PLAYER_THREE_LEFT.equals(function)) {
			this.changeAI(2, -1);
		} else if (ApoSkunkmanModelGame.CHANGE_PLAYER_THREE_RIGHT.equals(function)) {
			this.changeAI(2, +1);
		} else if (ApoSkunkmanModelGame.LOAD_PLAYER_FOUR.equals(function)) {
			this.getGame().loadPlayer(3, null);
		} else if (ApoSkunkmanModelGame.CHANGE_PLAYER_FOUR_LEFT.equals(function)) {
			this.changeAI(3, -1);
		} else if (ApoSkunkmanModelGame.CHANGE_PLAYER_FOUR_RIGHT.equals(function)) {
			this.changeAI(3, +1);
		} else if (ApoSkunkmanModelGame.ANALYSIS_PLAY_REPLAY.equals(function)) {
			this.startReplay();
		} else if (ApoSkunkmanModelGame.ANALYSIS_SAVE_REPLAY.equals(function)) {
			this.saveReplay();
		} else if (ApoSkunkmanModelGame.LOAD_REPLAY.equals(function)) {
			this.loadReplay();
		} else if (ApoSkunkmanModelGame.LOAD_EDITORLEVEL.equals(function)) {
			this.getGame().getLevel().setReplayShowing(false);
			this.loadLevel(null);
			this.getGame().getLevel().makeNewLevel(-1);
		}
		if (!this.getGame().shouldRepaint()) {
			this.getGame().render();
		}
	}
	
	private void simulation() {
		this.getGame().setSimulation(true);
		this.startGame();
		this.getGame().setShouldRepaint(false);
		
		ApoSkunkmanSimulate simulation = new ApoSkunkmanSimulate(this.getGame().getLevel());
		simulation.simulate();

		this.getGame().setSimulation(false);
	}
	
	/**
	 * l�d ein Editorlevel von der Festplatte
	 * @param path : Pfad zum Laden des Levels, wenn NULL dann zeige den ladenDialog
	 */
	private void loadLevel(String path) {
		this.getGame().getLevel().setType(ApoSkunkmanConstants.LEVEL_TYPE_EDITOR);
		this.getGame().loadLevel(path, false);
	}
	
	/**
	 * Methode zum Speichern des Replays
	 */
	private void saveReplay() {
		this.getGame().saveReplay();
	}
	
	/**
	 * Methode zum Laden des Replays
	 */
	private void loadReplay() {
		this.getGame().loadReplay();
	}
	
	/**
	 * Methode zum wechseln der KI von auf die Pfeiltasten gedrückt wurde
	 * @param player : gibt an, welcher Spieler es ist
	 * @param change : gibt an, um wieviel die KI im Array dazuaddiert werden soll und damit eine andere KI genommen werden soll
	 */
	private void changeAI(int player, int change) {
		this.curAI[player] += change;
		if (this.curAI[player] < 0) {
			this.curAI[player] = ApoSkunkmanConstants.AI_LEFTRIGHT;
		} else if (this.curAI[player] > ApoSkunkmanConstants.AI_LEFTRIGHT) {
			this.curAI[player] = 0;
		}
		this.getGame().getLevel().setReplayShowing(false);
		this.getGame().loadPlayer(player, this.curAI[player], true);
	}
	
	/**
	 * wird aufgerufen, wenn der Startbutton geklickt wurde und startet das eigentliche Spiel
	 */
	public void startReplay() {
		this.getGame().getLevel().setSpeedMode(ApoSkunkmanConstants.SPEED_OPTIONS_NORMAL);
		this.getGame().getLevel().setReplay(this.getGame().getLevel().getReplay().clone());
		this.getGame().getLevel().setReplayShowing(true);
		this.getGame().getLevel().setBush(this.getGame().getLevel().getReplay().isBBush(), true);
		this.getGame().getLevel().setMaxPlayers(this.getGame().getLevel().getReplay().getPlayers());
		this.getGame().getLevel().setReplay(this.getGame().getLevel().getReplay());
		this.getGame().getLevel().loadAndMakeReplayLevel();
		this.getGame().getLevel().setTime(this.getGame().getLevel().getReplay().getTime());
		this.getGame().startGame();
	}
	
	/**
	 * wird aufgerufen, wenn der Startbutton geklickt wurde und startet das eigentliche Spiel
	 */
	private void startGame() {
		this.getGame().getLevel().setSpeedMode(ApoSkunkmanConstants.SPEED_OPTIONS_NORMAL);
		this.getGame().getLevel().setReplayShowing(false);
		this.getGame().getLevel().restart();
		this.getGame().startGame();
	}
	
	/**
	 * wird aufgerufen, wenn der Stopbutton geklickt wurde und stoppt das eigentliche Spiel
	 */
	private void stopGame() {
		this.getGame().setSimulation(false);
		this.getGame().stopGame();
	}
	
	@Override
	public void think(long delta) {
		if (this.getGame().isSimulate()) {
			
		} else if (this.getGame().isGameRunning()) {
			if ((!this.getGame().getLevel().isPause()) && (!this.getGame().getLevel().isWin())) {
				this.thinkHumanPlayer((int)delta);
				
				if (this.getGame().getLevel() != null) {
					this.getGame().getLevel().think(delta);
				}
			}
		} else {
			for (int i = 21; i < 29; i++) {
				if (this.getGame().getButtons()[i].isBWait()) {
					int wait = this.getGame().getButtons()[i].getWait();
					this.getGame().getButtons()[i].think((int)(delta));
					if (this.getGame().getButtons()[i].getWait() < wait) {
						this.mouseButtonFunction(this.getGame().getButtons()[i].getFunction());
						this.getGame().render();
					}
				}
			}
		}
	}
	
	/**
	 * handelt die Keyboardeingaben der Menschen und steuert dann ihren Spieler
	 * @param delta : Zeit, die seit dem letzten Aufruf vergangen ist
	 */
	private void thinkHumanPlayer(int delta) {
		if (!this.getGame().getLevel().isReplayShowing()) {
			this.thinkHumanPlayer(delta, 0, ApoSkunkmanConstants.PLAYER_ONE_LEFT, ApoSkunkmanConstants.PLAYER_ONE_RIGHT, ApoSkunkmanConstants.PLAYER_ONE_UP, ApoSkunkmanConstants.PLAYER_ONE_DOWN, ApoSkunkmanConstants.PLAYER_ONE_LAY);
			this.thinkHumanPlayer(delta, 1, ApoSkunkmanConstants.PLAYER_TWO_LEFT, ApoSkunkmanConstants.PLAYER_TWO_RIGHT, ApoSkunkmanConstants.PLAYER_TWO_UP, ApoSkunkmanConstants.PLAYER_TWO_DOWN, ApoSkunkmanConstants.PLAYER_TWO_LAY);
		}
		this.releasedKeys.clear();
	}
	
	/**
	 * handelt die Keyboardeingaben eines Menschen und steuert ihn
	 * @param delta : Zeit, die seit dem letzten Aufruf vergangen ist
	 * @param playerInt : gibt an um welchen Spieler es sich handelt
	 * @param left : welcher Button soll �berpr�ft werden, wenn der Spieler nach links gehen soll
	 * @param right : welcher Button soll �berpr�ft werden, wenn der Spieler nach rechts gehen soll
	 * @param up : welcher Button soll �berpr�ft werden, wenn der Spieler nach oben gehen soll
	 * @param down : welcher Button soll �berpr�ft werden, wenn der Spieler nach unten gehen soll
	 * @param lay : welcher Button soll �berpr�ft werden, wenn der Spieler ein Stinktier legen soll
	 */
	private void thinkHumanPlayer(final int delta, final int playerInt, final int left, final int right, final int up, final int down, final int lay) {
		if ((this.getGame().getLevel().getMaxPlayers() > playerInt) && (this.getGame().getLevel().getPlayers()[playerInt].isBVisible()) && 
			(this.getGame().getLevel().getPlayers()[playerInt].getAi() == null)) {
			ApoSkunkmanPlayer player = this.getGame().getLevel().getPlayers()[playerInt];
			if (!player.isMoving()) {
				if (this.getGame().getKeyboard().isPressed(left)) {
					player.setDirection(ApoSkunkmanConstants.PLAYER_DIRECTION_LEFT);
					if (this.getGame().getLevel().canPlayerGoLeft(player)) {
						player.setNextDirection(ApoSkunkmanConstants.PLAYER_DIRECTION_LEFT);
					}
				} else if (this.getGame().getKeyboard().isPressed(right)) {
					player.setDirection(ApoSkunkmanConstants.PLAYER_DIRECTION_RIGHT);
					if (this.getGame().getLevel().canPlayerGoRight(player)) {
						player.setNextDirection(ApoSkunkmanConstants.PLAYER_DIRECTION_RIGHT);
					}
				} else if (this.getGame().getKeyboard().isPressed(down)) {
					player.setDirection(ApoSkunkmanConstants.PLAYER_DIRECTION_DOWN);
					if (this.getGame().getLevel().canPlayerGoDown(player)) {
						player.setNextDirection(ApoSkunkmanConstants.PLAYER_DIRECTION_DOWN);
					}
				} else if (this.getGame().getKeyboard().isPressed(up)) {
					player.setDirection(ApoSkunkmanConstants.PLAYER_DIRECTION_UP);
					if (this.getGame().getLevel().canPlayerGoUp(player)) {
						player.setNextDirection(ApoSkunkmanConstants.PLAYER_DIRECTION_UP);
					}
				}
				if ((this.getGame().getKeyboard().isPressed(lay)) || (this.releasedKeys.contains(lay))) {
					int x = (int)(player.getX() / ApoSkunkmanConstants.TILE_SIZE);
					int y = (int)(player.getY() / ApoSkunkmanConstants.TILE_SIZE);
					if ((!player.isMoving()) && (player.canLaySkunkman()) && (this.getGame().getLevel().getLevel()[y][x] == null)) {
						player.setLaySkunkman(true);//this.getGame().getLevel().layBomb(x, y, player.getPlayer());
					}
				}
			}
		}
	}

	@Override
	public void render(Graphics2D g) {
		if (this.getGame().isGameRunning()) {
			if (this.getGame().getLevel().getWinner() < -1) {
				this.renderGameOptions(g, false);
			}
		} else {
			this.getGame().renderNamesForLevel(g);
			this.renderOptions(g);
		}
	}

	/**
	 * rendert die Informationen während das Spiel läuft (z.B. die Einstellungen wie schnell das Spiel laufen soll)
	 * @param g : Das Graphics2D-Object
	 * @param bBackground : boolean Variable die angibt, ob gerade das Hintergrundbild gemalt wird oder nicht
	 */
	public void renderGameOptions(Graphics2D g, final boolean bBackground) {
		g.setFont(ApoSkunkmanConstants.FONT_OPTIONS_BOLD);
		if (ApoSkunkmanConstants.LEVEL_TILESET.equals(ApoSkunkmanConstants.LEVEL_TILESETS[1])) {
			g.setFont(ApoSkunkmanConstants.FONT_OPTIONS_BOLD_ASCII);
		}
		
		int x = ApoSkunkmanImageContainer.iHud.getWidth()/2;
		String s = "Speed";
		if (bBackground) {
			ApoSkunkmanConstants.drawString(g, s, x, (int)(this.getGame().getButtons()[31].getY()), true);
		}
		g.setFont(ApoSkunkmanConstants.FONT_OPTIONS);
		if (ApoSkunkmanConstants.LEVEL_TILESET.equals(ApoSkunkmanConstants.LEVEL_TILESETS[1])) {
			g.setFont(ApoSkunkmanConstants.FONT_OPTIONS_ASCII);
		}
		
		if ((!bBackground) && (this.getGame().isGameRunning())) {
			s = String.valueOf(ApoSkunkmanConstants.SPEED_OPTIONS[this.getGame().getLevel().getSpeedMode()]);
			ApoSkunkmanConstants.drawString(g, s, x, (int)(this.getGame().getButtons()[21].getY() + this.getGame().getButtons()[21].getHeight()), true);
		}
		
		x = (int)(this.getGame().getButtons()[5].getX());
		int otherX = (int)(this.getGame().getButtons()[6].getX() + this.getGame().getButtons()[6].getWidth());
		int y = (int)(this.getGame().getButtons()[31].getY() + 100 * ApoSkunkmanConstants.APPLICATION_SIZE);
		if (bBackground) {
			s = "Spieler";
			ApoSkunkmanConstants.drawString(g, s, x, y - 15 * ApoSkunkmanConstants.APPLICATION_SIZE, false);
			s = "Punkte";
			int w = g.getFontMetrics().stringWidth(s);
			ApoSkunkmanConstants.drawString(g, s, otherX - w, y - 15 * ApoSkunkmanConstants.APPLICATION_SIZE, false);
			
			g.setColor(Color.BLACK);
			g.drawLine(x, y - 10 * ApoSkunkmanConstants.APPLICATION_SIZE, otherX, y - 10 * ApoSkunkmanConstants.APPLICATION_SIZE);
		}
		g.setFont(ApoSkunkmanConstants.FONT_LEVEL_TIME);
		if (ApoSkunkmanConstants.LEVEL_TILESET.equals(ApoSkunkmanConstants.LEVEL_TILESETS[1])) {
			g.setFont(ApoSkunkmanConstants.FONT_LEVEL_TIME_ASCII);
		}
		if (this.getGame().getLevel().getPlayers() != null) {
			for (int i = 0; i < this.getGame().getLevel().getMaxPlayers(); i++) {
				ApoSkunkmanPlayer player = this.getGame().getLevel().getPlayers()[i];
				if (bBackground) {
					BufferedImage iHead = player.getIHead();
					g.drawImage(iHead, x, y + (5 + 14 * i) * ApoSkunkmanConstants.APPLICATION_SIZE - iHead.getHeight(), null);
					int w = iHead.getWidth() + 3 * ApoSkunkmanConstants.APPLICATION_SIZE;
					s = player.getPlayerName() + " ("+(player.getPlayer()+1)+")";
					if (this.getGame().getLevel().isReplayShowing())  {
						s = this.getGame().getLevel().getReplay().getNames()[i] + " ("+(player.getPlayer()+1)+")";						
					}
					ApoSkunkmanConstants.drawString(g, s, x + w, y + (5 + 14 * i) * ApoSkunkmanConstants.APPLICATION_SIZE, false);
				}
				if ((!bBackground) && (this.getGame().isGameRunning())) {
					s = String.valueOf(player.getPoints());
					int w = g.getFontMetrics().stringWidth(s);
					ApoSkunkmanConstants.drawString(g, s, otherX - w, y + (5 + 14 * i) * ApoSkunkmanConstants.APPLICATION_SIZE, false);
				}
			}
		}
	}
	
	/**
	 * rendert die Informationen während das Spiel noch nicht läuft (z.B. die Einstellungen wieviel Spieler am Spiel teilnehmen dürfen)
	 * @param g : Das Graphics2D-Object
	 */
	private void renderOptions(Graphics2D g) {
		g.setFont(ApoSkunkmanConstants.FONT_HUD_TREE);
		if (ApoSkunkmanConstants.LEVEL_TILESET.equals(ApoSkunkmanConstants.LEVEL_TILESETS[1])) {
			g.setFont(ApoSkunkmanConstants.FONT_HUD_TREE_ASCII);
		}
		
		String s = "Editorlevel";
		if (this.getGame().getButtons()[5].isBVisible()) {
			Stroke stroke = g.getStroke();
			g.setStroke(ApoSkunkmanConstants.LINE_STROKE);
			g.setColor(Color.BLACK);
			g.drawLine((int)(this.getGame().getButtons()[5].getX()), (int)(this.getGame().getButtons()[5].getY() - 16 * ApoSkunkmanConstants.APPLICATION_SIZE), (int)(this.getGame().getButtons()[6].getX() + this.getGame().getButtons()[6].getWidth()), (int)(this.getGame().getButtons()[5].getY() - 16 * ApoSkunkmanConstants.APPLICATION_SIZE));
			g.setStroke(stroke);
			
			ApoSkunkmanConstants.drawString(g, s, (int)(this.getGame().getButtons()[5].getX()), (int)(this.getGame().getButtons()[5].getY() - 3 * ApoSkunkmanConstants.APPLICATION_SIZE), false);
		}
		s = "Replay";
		if (this.getGame().getButtons()[6].isBVisible()) {
			ApoSkunkmanConstants.drawString(g, s, (int)(this.getGame().getButtons()[6].getX()), (int)(this.getGame().getButtons()[6].getY() - 3 * ApoSkunkmanConstants.APPLICATION_SIZE), false);
		}
		
		g.setFont(ApoSkunkmanConstants.FONT_OPTIONS_BOLD);
		if (ApoSkunkmanConstants.LEVEL_TILESET.equals(ApoSkunkmanConstants.LEVEL_TILESETS[1])) {
			g.setFont(ApoSkunkmanConstants.FONT_OPTIONS_BOLD_ASCII);
		}
		int x = ApoSkunkmanImageContainer.iHud.getWidth()/2;
		s = "Players";
		ApoSkunkmanConstants.drawString(g, s, x, (int)(this.getGame().getButtons()[21].getY()), true);
		s = "Leveltype";
		ApoSkunkmanConstants.drawString(g, s, x, (int)(this.getGame().getButtons()[23].getY()), true);
		s = "With bushes?";
		ApoSkunkmanConstants.drawString(g, s, x, (int)(this.getGame().getButtons()[25].getY()), true);
		s = "Time";
		ApoSkunkmanConstants.drawString(g, s, x, (int)(this.getGame().getButtons()[27].getY()), true);

		g.setFont(ApoSkunkmanConstants.FONT_OPTIONS);
		
		s = String.valueOf(this.getGame().getLevel().getMaxPlayers());
		ApoSkunkmanConstants.drawString(g, s, x, (int)(this.getGame().getButtons()[21].getY() + this.getGame().getButtons()[21].getHeight()), true);
		s = String.valueOf(this.getGame().getLevel().getTypeHelp()[this.getGame().getLevel().getType()].getTypeString());
		ApoSkunkmanConstants.drawString(g, s, x, (int)(this.getGame().getButtons()[23].getY() + this.getGame().getButtons()[23].getHeight()), true);
		s = String.valueOf(this.getGame().getLevel().isBush());
		ApoSkunkmanConstants.drawString(g, s, x, (int)(this.getGame().getButtons()[25].getY() + this.getGame().getButtons()[25].getHeight()), true);
		s = ApoHelp.getTimeToDraw(this.getGame().getLevel().getTime());
		if (this.getGame().getLevel().getTime() > ApoSkunkmanConstants.TIME_MAX) {
			s = String.valueOf((char)(247));;
		}
		ApoSkunkmanConstants.drawString(g, s, x, (int)(this.getGame().getButtons()[27].getY() + this.getGame().getButtons()[27].getHeight()), true);

	}
}
