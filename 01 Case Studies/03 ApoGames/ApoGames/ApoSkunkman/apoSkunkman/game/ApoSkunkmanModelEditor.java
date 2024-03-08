package apoSkunkman.game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import apoSkunkman.ApoSkunkmanConstants;
import apoSkunkman.ApoSkunkmanImageContainer;
import apoSkunkman.entity.ApoSkunkmanBush;
import apoSkunkman.entity.ApoSkunkmanStone;

/**
 * Klasse, die den Editor des Spiel repäsentiert und alle Aktionen dadrin handelt<br />
 * Vom laden, Speichern über die Mausevents usw.<br />
 * @author Dirk Aporius
 *
 */
public class ApoSkunkmanModelEditor extends ApoSkunkmanModel {
	
	/** gibt die Verschiebung in x-Richtung für das Level an */
	private final int CHANGE_X = ApoSkunkmanImageContainer.iHud.getWidth() + ApoSkunkmanConstants.TILE_SIZE/2;
	/** gibt die Verschiebung in y-Richtung für das Level an */
	private final int CHANGE_Y = ApoSkunkmanConstants.CHANGE_Y_LEVEL;
	
	/** String für den BACK-Button */
	public static String BACK = "back";
	/** String für die Goodieauswahl links */
	public static String EDITOR_GOODIE_LEFT = "goodie_left";
	/** String für die Goodieauswahl rechts */
	public static String EDITOR_GOODIE_RIGHT = "goodie_right";
	/** String für die Zeitauswahl links */
	public static String EDITOR_TIME_LEFT = "goodie_left";
	/** String für die Zeitauswahl rechts */
	public static String EDITOR_TIME_RIGHT = "goodie_right";
	/** String für das Speichern des Editorlevels */
	public static String EDITOR_SAVE = "save";
	/** String für das Laden des Editorlevels */
	public static String EDITOR_LOAD = "load";
	/** String für den Button des Freelevel-buttons */
	public static String EDITOR_FREE = "free";
	/** String für den Button des Bushlevel-buttons */
	public static String EDITOR_BUSH = "bush";
	/** String für den Button des Stonelevel-buttons */
	public static String EDITOR_STONE = "stone";
	/** String für den Button des Stonelevel-buttons */
	public static String EDITOR_GOALX = "goalX";
	/** String für den Button zum Setzen des ersten Spielers */
	public static String EDITOR_PLAYER_ONE = "player_one";
	/** String für den Button zum Setzen des zweiten Spielers */
	public static String EDITOR_PLAYER_TWO = "player_two";
	/** String für den Button zum Setzen des dritten Spielers */
	public static String EDITOR_PLAYER_THREE = "player_three";
	/** String für den Button zum Setzen des vierten Spielers */
	public static String EDITOR_PLAYER_FOUR = "player_four";
	/** String für den Button zum zufälligen Füllen des Spielfeldes */
	public static String EDITOR_NEW_LEVEL = "editor_new";
	/** String für den Button zum Leeren des Spielfeldes */
	public static String EDITOR_CLEAR_LEVEL = "editor_clear";

	/** Konstante, die die Auswahl für das freie Tile wiedergibt */
	public static int SELECTION_FREE = 0;
	/** Konstante, die die Auswahl für das Busch-Tile wiedergibt */
	public static int SELECTION_BUSH = 2;
	/** Konstante, die die Auswahl für das Stone-Tile wiedergibt */
	public static int SELECTION_STONE = 1;
	/** Konstante, die die Auswahl für das PlayerOne-Tile wiedergibt */
	public static int SELECTION_PLAYER_ONE = 3;
	/** Konstante, die die Auswahl für das PlayerTwo-Tile wiedergibt */
	public static int SELECTION_PLAYER_TWO = 4;
	/** Konstante, die die Auswahl für das PlayerThree-Tile wiedergibt */
	public static int SELECTION_PLAYER_THREE = 5;
	/** Konstante, die die Auswahl für das PlayerFour-Tile wiedergibt */
	public static int SELECTION_PLAYER_FOUR = 6;
	/** Konstante die angibt, ob es einen Zielpunkt gibt oder nicht */
	public static int SELECTION_GOALX = 7;
	/** derzeit ausgewähltes Tile */
	private int selection = -1;
	/** Bild des derzeit ausgewähltem Tile */
	private BufferedImage iSelection, iRealSelection;
	/** aktuelle Mausposition in Leveldatenform */
	private Point curMousePosition;
	/** ein 3 dimensionales Integerarray, in dem x, y Wert und in der 3ten Dimension Wert im Level und falls es ein Goodie ist noch welches Goodie */
	private int[][][] level;
	/** der derzeitig ausgewählte Busch */
	private ApoSkunkmanBush selectedBush;
	
	/**
	 * Konstruktor
	 * @param game : Das Gameobjekt der Hauptklasse für das Spiel
	 */
	public ApoSkunkmanModelEditor(ApoSkunkmanPanel game) {
		super(game);
	}
	
	public void init() {
		super.init();
		this.curMousePosition = new Point(-1, -1);
		
		this.selectedBush = null;
		
		if (this.level == null) {
			this.level = new int[this.getGame().getLevel().getLevel().length][this.getGame().getLevel().getLevel()[0].length][2];
			this.makeFirstEditorLevel();
		} else {
			this.setEditorLevel();
		}
		if (this.selection < 0) {
			this.changeSelection(ApoSkunkmanModelEditor.SELECTION_STONE);
		}
	}

	/**
	 * gibt das 3 dimensionale Integerarray, in dem x, y Wert und in der 3ten Dimension Wert im Level und falls es ein Goodie ist noch welches Goodie, zurück
	 * @return gibt das 3 dimensionale Integerarray, in dem x, y Wert und in der 3ten Dimension Wert im Level und falls es ein Goodie ist noch welches Goodie, zurück
	 */
	public final int[][][] getLevel() {
		return this.level;
	}

	/**
	 * übergibt die Werte aus dem Levelarray an das richtige Level
	 */
	public void setEditorLevel() {
		if (this.level == null) {
			this.makeEmptyLevel();
		}
		for (int y = 1; y < this.level.length - 1; y++) {
			for (int x = 1; x < this.level[0].length - 1; x++) {
				if ((this.level[y][x][0] >= ApoSkunkmanEditorIO.PLAYER_ONE) && (this.level[y][x][0] <= ApoSkunkmanEditorIO.PLAYER_FOUR)) {
					this.getGame().getLevel().getPlayers()[this.level[y][x][0] - ApoSkunkmanEditorIO.PLAYER_ONE].setX(x * ApoSkunkmanConstants.TILE_SIZE);
					this.getGame().getLevel().getPlayers()[this.level[y][x][0] - ApoSkunkmanEditorIO.PLAYER_ONE].setY(y * ApoSkunkmanConstants.TILE_SIZE);
				} else if (this.level[y][x][0] == ApoSkunkmanEditorIO.FREE) {
					this.getGame().getLevel().getLevel()[y][x] = null;
				} else if (this.level[y][x][0] == ApoSkunkmanEditorIO.STONE) {
					this.getGame().getLevel().getLevel()[y][x] = this.getGame().getLevel().makeStone(x, y);
				} else if (this.level[y][x][0] == ApoSkunkmanEditorIO.BUSH) {
					this.getGame().getLevel().getLevel()[y][x] = this.getGame().getLevel().makeBush(x, y, this.level[y][x][1]);
				}
			}
		}
		this.getGame().getLevel().getGoalX().x = this.level[0][0][1];
		this.getGame().getLevel().getGoalX().y = this.level[1][0][1];
	}
	
	/**
	 * erstellt ein komplett leeres Spielfeld
	 */
	private void makeEmptyLevel() {
		this.level = new int[this.getGame().getLevel().getLevel().length][this.getGame().getLevel().getLevel()[0].length][2];
		for (int y = 1; y < this.level.length - 1; y++) {
			for (int x = 1; x < this.level[0].length - 1; x++) {
				this.level[y][x][0] = ApoSkunkmanEditorIO.FREE;
			}
		}
		for (int i = 0; i < this.getGame().getLevel().getPlayers().length; i++) {
			int x = (int)(this.getGame().getLevel().getPlayers()[i].getX()/ApoSkunkmanConstants.TILE_SIZE);
			int y = (int)(this.getGame().getLevel().getPlayers()[i].getY()/ApoSkunkmanConstants.TILE_SIZE);
			this.level[y][x][0] = ApoSkunkmanEditorIO.PLAYER_ONE + i;
		}
		this.level[0][0][1] = this.getGame().getLevel().getGoalX().x;
		this.level[1][0][1] = this.getGame().getLevel().getGoalX().y;
		this.level[2][0][1] = this.getGame().getLevel().getTime();
	}

	/**
	 * wird aufgerufen beim ersten Initialisieren und füllt das Level zufällig mit Steinen
	 */
	private void makeFirstEditorLevel() {
		this.makeEmptyLevel();
		for (int y = 2; y < this.level.length - 2; y++) {
			for (int x = 2; x < this.level[0].length - 2; x++) {
				if (Math.random() * 100 > 50) {
					this.level[y][x][0] = ApoSkunkmanEditorIO.STONE;
				}
			}
		}
	}
	
	/**
	 * erstellt aus den Leveldaten des eigentlichen Levels das Levelarray des Editors
	 */
	public void makeEditorLevel() {
		this.level = new int[this.getGame().getLevel().getLevel().length][this.getGame().getLevel().getLevel()[0].length][2];
		for (int y = 1; y < this.level.length - 1; y++) {
			for (int x = 1; x < this.level[0].length - 1; x++) {
				if (this.getGame().getLevel().getLevel()[y][x] == null) {
					this.level[y][x][0] = ApoSkunkmanEditorIO.FREE;
				} else if (this.getGame().getLevel().getLevel()[y][x] instanceof ApoSkunkmanStone) {
					this.level[y][x][0] = ApoSkunkmanEditorIO.STONE;
				} else if (this.getGame().getLevel().getLevel()[y][x] instanceof ApoSkunkmanBush) {
					this.level[y][x][0] = ApoSkunkmanEditorIO.BUSH;
					this.level[y][x][1] = ((ApoSkunkmanBush)(this.getGame().getLevel().getLevel()[y][x])).getGoodie();
				}
			}
		}
		for (int i = 0; i < this.getGame().getLevel().getPlayers().length; i++) {
			int x = (int)(this.getGame().getLevel().getPlayers()[i].getX()/ApoSkunkmanConstants.TILE_SIZE);
			int y = (int)(this.getGame().getLevel().getPlayers()[i].getY()/ApoSkunkmanConstants.TILE_SIZE);
			this.level[y][x][0] = ApoSkunkmanEditorIO.PLAYER_ONE + i;
		}
		this.level[0][0][1] = this.getGame().getLevel().getGoalX().x;
		this.level[1][0][1] = this.getGame().getLevel().getGoalX().y;
		this.level[2][0][1] = this.getGame().getLevel().getTime();
	}

	@Override
	public void keyButtonReleased(final int button, final char character) {
		if (button == KeyEvent.VK_1) {
			this.changeSelection(ApoSkunkmanModelEditor.SELECTION_FREE);
		} else if (button == KeyEvent.VK_3) {
			this.changeSelection(ApoSkunkmanModelEditor.SELECTION_BUSH);
		} else if (button == KeyEvent.VK_2) {
			this.changeSelection(ApoSkunkmanModelEditor.SELECTION_STONE);
		} else if (button == KeyEvent.VK_4) {
			this.changeSelection(ApoSkunkmanModelEditor.SELECTION_PLAYER_ONE);
		} else if (button == KeyEvent.VK_5) {
			this.changeSelection(ApoSkunkmanModelEditor.SELECTION_PLAYER_TWO);
		} else if (button == KeyEvent.VK_6) {
			this.changeSelection(ApoSkunkmanModelEditor.SELECTION_PLAYER_THREE);
		} else if (button == KeyEvent.VK_7) {
			this.changeSelection(ApoSkunkmanModelEditor.SELECTION_PLAYER_FOUR);
		} else if (button == KeyEvent.VK_8) {
			this.changeSelection(ApoSkunkmanModelEditor.SELECTION_GOALX);
		} else if (button == KeyEvent.VK_L) {
			this.loadLevel();
		} else if (button == KeyEvent.VK_S) {
			this.saveLevel();
		} else if (button == KeyEvent.VK_ESCAPE) {
			this.setMenu();
		} else if (button == KeyEvent.VK_N) {
			this.makeNewLevel();
		}
		if (!this.getGame().shouldRepaint()) {
			this.getGame().render();
		}
	}

	@Override
	public void mouseButtonFunction(final String function) {
		if (ApoSkunkmanModelEditor.BACK.equals(function)) {
			this.setMenu();
		} else if (ApoSkunkmanModelEditor.EDITOR_FREE.equals(function)) {
			this.changeSelection(ApoSkunkmanModelEditor.SELECTION_FREE);
		} else if (ApoSkunkmanModelEditor.EDITOR_BUSH.equals(function)) {
			this.changeSelection(ApoSkunkmanModelEditor.SELECTION_BUSH);
		} else if (ApoSkunkmanModelEditor.EDITOR_STONE.equals(function)) {
			this.changeSelection(ApoSkunkmanModelEditor.SELECTION_STONE);
		} else if (ApoSkunkmanModelEditor.EDITOR_PLAYER_ONE.equals(function)) {
			this.changeSelection(ApoSkunkmanModelEditor.SELECTION_PLAYER_ONE);
		} else if (ApoSkunkmanModelEditor.EDITOR_PLAYER_TWO.equals(function)) {
			this.changeSelection(ApoSkunkmanModelEditor.SELECTION_PLAYER_TWO);
		} else if (ApoSkunkmanModelEditor.EDITOR_PLAYER_THREE.equals(function)) {
			this.changeSelection(ApoSkunkmanModelEditor.SELECTION_PLAYER_THREE);
		} else if (ApoSkunkmanModelEditor.EDITOR_PLAYER_FOUR.equals(function)) {
			this.changeSelection(ApoSkunkmanModelEditor.SELECTION_PLAYER_FOUR);
		} else if (ApoSkunkmanModelEditor.EDITOR_GOALX.equals(function)) {
			this.changeSelection(ApoSkunkmanModelEditor.SELECTION_GOALX);
		} else if (ApoSkunkmanModelEditor.EDITOR_SAVE.equals(function)) {
			this.saveLevel();
		} else if (ApoSkunkmanModelEditor.EDITOR_LOAD.equals(function)) {
			this.loadLevel();
		} else if (ApoSkunkmanModelEditor.EDITOR_NEW_LEVEL.equals(function)) {
			this.makeNewLevel();
		} else if (ApoSkunkmanModelEditor.EDITOR_CLEAR_LEVEL.equals(function)) {
			this.makeClearLevel();
		} else if (ApoSkunkmanModelEditor.EDITOR_GOODIE_LEFT.equals(function)) {
			this.changeGoodie(-1);
		} else if (ApoSkunkmanModelEditor.EDITOR_GOODIE_RIGHT.equals(function)) {
			this.changeGoodie(+1);
		}
	}

	/**
	 * wird aufgerufen, wenn ein neues Level ohne etwas erstellt werden soll
	 */
	private void makeClearLevel() {
		this.makeEmptyLevel();
		this.setEditorLevel();
		this.getGame().makeBackground(true, false, false, true);
	}
	
	/**
	 * wird aufgerufen, wenn ein neues Level erstellt werden soll
	 */
	private void makeNewLevel() {
		boolean oldBush = this.getGame().getLevel().isBush();
		this.getGame().getLevel().setBush(true, false);
		this.getGame().getLevel().makeNewLevel(-1, ApoSkunkmanConstants.LEVEL_TYPE_STANDARD);
		this.getGame().setButtonVisible(ApoSkunkmanConstants.BUTTON_EDITOR);
		this.getGame().getLevel().setType(ApoSkunkmanConstants.LEVEL_TYPE_EDITOR);
		this.getGame().getLevel().setBush(oldBush, false);
		this.makeEditorLevel();
		this.getGame().makeBackground(true, false, false, true);
	}
	
	/**
	 * wird aufgerufen, wenn das Spiel wieder ins Menu gehen soll
	 */
	private void setMenu() {
		this.makeEditorLevel();
		this.setEditorLevel();
		this.getGame().getLevel().setType(ApoSkunkmanConstants.LEVEL_TYPE_EDITOR);
		this.getGame().setMenu();
	}
	
	/**
	 * speichert ein Level auf die Festplatte
	 */
	private void saveLevel() {
		this.makeEditorLevel();
		this.setEditorLevel();
		this.getGame().saveLevel(null);
	}
	
	/**
	 * läd ein Level von der Festplatte
	 */
	private void loadLevel() {
		this.getGame().loadLevel(null, true);
		this.render();
	}
	
	/**
	 * wechselt das Goodie des ausgewählten Busches
	 * @param change : Veränderung
	 */
	private void changeGoodie(int change) {
		if (this.selectedBush != null) {
			int goodie = this.selectedBush.getGoodie() + change;
			if (goodie < 0) {
				goodie = ApoSkunkmanConstants.GOODIE_STRING.length - 1;
			} else if (goodie >= ApoSkunkmanConstants.GOODIE_STRING.length) {
				goodie = 0;
			}
			this.selectedBush.setGoodie(goodie);
		}
	}
	
	/**
	 * wechselt die Auswahl des ausgewählten Tiles
	 * @param newSelection : neu ausgewähltes Tile
	 */
	private void changeSelection(final int newSelection) {
		this.selection = newSelection;
		BufferedImage iNewSelection = null;
		if (this.selection == ApoSkunkmanModelEditor.SELECTION_FREE) {
			iNewSelection = ApoSkunkmanImageContainer.iTile.getSubimage(0 * ApoSkunkmanConstants.TILE_SIZE, 0 * ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE);
		} else if (this.selection == ApoSkunkmanModelEditor.SELECTION_BUSH) {
			iNewSelection = ApoSkunkmanImageContainer.iTile.getSubimage(2 * ApoSkunkmanConstants.TILE_SIZE, 0 * ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE);
		} else if (this.selection == ApoSkunkmanModelEditor.SELECTION_STONE) {
			iNewSelection = ApoSkunkmanImageContainer.iTile.getSubimage(1 * ApoSkunkmanConstants.TILE_SIZE, 0 * ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE);
		} else if (this.selection == ApoSkunkmanModelEditor.SELECTION_PLAYER_ONE) {
			iNewSelection = ApoSkunkmanImageContainer.iPlayerOne.getSubimage(0 * ApoSkunkmanConstants.TILE_SIZE, 0 * ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE * 3 / 2);
		} else if (this.selection == ApoSkunkmanModelEditor.SELECTION_PLAYER_TWO) {
			iNewSelection = ApoSkunkmanImageContainer.iPlayerTwo.getSubimage(0 * ApoSkunkmanConstants.TILE_SIZE, 0 * ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE * 3 / 2);
		} else if (this.selection == ApoSkunkmanModelEditor.SELECTION_PLAYER_THREE) {
			iNewSelection = ApoSkunkmanImageContainer.iPlayerThree.getSubimage(0 * ApoSkunkmanConstants.TILE_SIZE, 0 * ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE * 3 / 2);
		} else if (this.selection == ApoSkunkmanModelEditor.SELECTION_PLAYER_FOUR) {
			iNewSelection = ApoSkunkmanImageContainer.iPlayerFour.getSubimage(0 * ApoSkunkmanConstants.TILE_SIZE, 0 * ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE * 3 / 2);
		} else if (this.selection == ApoSkunkmanModelEditor.SELECTION_GOALX) {
			iNewSelection = ApoSkunkmanImageContainer.iGoalX;
		}
		if (iNewSelection != null) {
			this.iRealSelection = new BufferedImage(iNewSelection.getWidth(), iNewSelection.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
			Graphics2D g = this.iRealSelection.createGraphics();

			g.drawImage(iNewSelection, 0, 0, null);
			
			g.dispose();
			
			this.iSelection = new BufferedImage(iNewSelection.getWidth(), iNewSelection.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
			g = this.iSelection.createGraphics();
			
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
			g.drawImage(iNewSelection, 0, 0, null);
			
			g.dispose();
		}
	}
	
	public final boolean mouseMoved(final int x, final int y) {
		super.mouseMoved(x, y);
		int changeX = this.CHANGE_X;
		int changeY = this.CHANGE_Y;
		if ((changeX < x) && (changeX + this.getGame().getLevel().getLevel()[0].length * ApoSkunkmanConstants.TILE_SIZE > x) &&
			(changeY < y) && (changeY + this.getGame().getLevel().getLevel().length * ApoSkunkmanConstants.TILE_SIZE > y)) {
			int pointX = (int)(x - changeX)/ApoSkunkmanConstants.TILE_SIZE;
			int pointY = (int)(y - changeY)/ApoSkunkmanConstants.TILE_SIZE;
			if ((pointX > 0) && (pointX + 1 < this.getGame().getLevel().getLevel()[0].length) &&
				(pointY > 0) && (pointY + 1 < this.getGame().getLevel().getLevel().length)) {
				if ((this.curMousePosition.x != pointX) || (this.curMousePosition.y != pointY)) {
					this.curMousePosition = new Point(pointX, pointY);
					this.getGame().render();
				}
			} else {
				if (this.curMousePosition.x != -1) {
					this.curMousePosition = new Point(-1, -1);
					this.getGame().render();
				}
			}
		} else {
			if (this.curMousePosition.x != -1) {
				this.curMousePosition = new Point(-1, -1);
				this.getGame().render();
			}
		}
		return false;
	}

	@Override
	public final boolean mouseDragged(final int x, final int y, final boolean bRight) {
		super.mouseDragged(x, y, bRight);
		this.mouseMoved(x, y);
		this.setSelection(bRight);
		
		return false;
	}
	
	@Override
	public void mouseReleased(final int x, final int y, final boolean bRight) {
		this.mouseMoved(x, y);
		this.setSelection(bRight);
	}
	
	/**
	 * wird aufgerufen, wenn die Maus losgelassen wird oder die Maus gezogen wird<br />
	 * sie setzt dann die Leveltiles, wenn benötigt<br />
	 * @param bRight : TRUE, rechte Maustaste gedrückt, ansonsten FALSE
	 */
	private void setSelection(final boolean bRight) {
		int x = this.curMousePosition.x;
		int y = this.curMousePosition.y;
		boolean bSelectedBush = false;
		// falls die Maus überhaupt über dem Level ist
		if ((x > 0) && (y > 0) &&
			(x < this.getGame().getLevel().getLevel()[0].length - 1) && (y < this.getGame().getLevel().getLevel().length - 1)) {
			if (bRight) {
				if (this.getGame().getLevel().getLevel()[y][x] != null) {
					this.getGame().getLevel().getLevel()[y][x] = null;
					this.render();
				}
				if ((this.getGame().getLevel().getGoalX().x == x) &&
					(this.getGame().getLevel().getGoalX().y == y)) {
					this.getGame().getLevel().getGoalX().x = -1;
					this.getGame().getLevel().getGoalX().y = -1;
					this.render();
				}
			} else {
				if ((this.selection == ApoSkunkmanModelEditor.SELECTION_FREE) &&
					(this.getGame().getLevel().getLevel()[y][x] != null)) {
					this.getGame().getLevel().getLevel()[y][x] = null;
					this.render();
				} else if (this.selection == ApoSkunkmanModelEditor.SELECTION_BUSH) {
					if ((this.isPlayerThere(x, y) < 0) &&
						((this.getGame().getLevel().getLevel()[y][x] == null) ||
						(!(this.getGame().getLevel().getLevel()[y][x] instanceof ApoSkunkmanBush)))) {
						this.getGame().getLevel().getLevel()[y][x] = this.getGame().getLevel().makeBush(x, y, -1);
						this.render();
					} else if ((this.getGame().getLevel().getLevel()[y][x] != null) &&
							(this.getGame().getLevel().getLevel()[y][x] instanceof ApoSkunkmanBush)) {
						this.selectedBush = (ApoSkunkmanBush)(this.getGame().getLevel().getLevel()[y][x]);
						bSelectedBush = true;
					}
				} else if (this.selection == ApoSkunkmanModelEditor.SELECTION_STONE) {
					if ((this.isPlayerThere(x, y) < 0) &&
						((this.getGame().getLevel().getLevel()[y][x] == null) ||
						(!(this.getGame().getLevel().getLevel()[y][x] instanceof ApoSkunkmanStone)))) {
						this.getGame().getLevel().getLevel()[y][x] = this.getGame().getLevel().makeStone(x, y);
						this.render();
					}
				} else if (this.selection == ApoSkunkmanModelEditor.SELECTION_PLAYER_ONE) {
					this.placePlayerThere(x, y, 0);
				} else if (this.selection == ApoSkunkmanModelEditor.SELECTION_PLAYER_TWO) {
					this.placePlayerThere(x, y, 1);
				} else if (this.selection == ApoSkunkmanModelEditor.SELECTION_PLAYER_THREE) {
					this.placePlayerThere(x, y, 2);
				} else if (this.selection == ApoSkunkmanModelEditor.SELECTION_PLAYER_FOUR) {
					this.placePlayerThere(x, y, 3);
				} else if (this.selection == ApoSkunkmanModelEditor.SELECTION_GOALX) {
					if (this.isPlayerThere(x, y) < 0) {
						if ((this.getGame().getLevel().getGoalX().x != x) ||
							(this.getGame().getLevel().getGoalX().y != y)) {
							this.getGame().getLevel().getLevel()[y][x] = null;
							this.getGame().getLevel().getGoalX().x = x;
							this.getGame().getLevel().getGoalX().y = y;
							this.render();
						}
					}
				}
			}
			this.makeEditorLevel();
			if (bSelectedBush) {
				this.changeGoodieChangeVisible(true);
			} else {
				this.selectedBush = null;
				this.changeGoodieChangeVisible(false);
			}
		}
	}
	
	/**
	 * setzt die Sichtbarkeit der Buttons für den Goodie auf den übergebenen Wert
	 * @param bVisible : TRUE sichtbar, FALSE unsichtbar
	 */
	private void changeGoodieChangeVisible(final boolean bVisible) {
		this.getGame().getButtons()[33].setBVisible(bVisible);
		this.getGame().getButtons()[34].setBVisible(bVisible);
		this.render();
	}
	
	/**
	 * gibt zurück, ob dort ein Spieler steht und welcher, ansonsten -1
	 * @param x : zu überprüfender x-Wert
	 * @param y : zu überprüfender y-Wert
	 * @return gibt zurück, ob dort ein Spieler steht und welcher, ansonsten -1
	 */
	private final int isPlayerThere(final int x, final int y) {
		for (int i = 0; i < this.getGame().getLevel().getPlayers().length; i++) {
			if ((x == (int)(this.getGame().getLevel().getPlayers()[i].getX()/ApoSkunkmanConstants.TILE_SIZE)) &&
				(y == (int)(this.getGame().getLevel().getPlayers()[i].getY()/ApoSkunkmanConstants.TILE_SIZE))) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * setzt einen Spieler an die übergebene Stelle
	 * @param x : zu überprüfender x-Wert
	 * @param y : zu überprüfender y-Wert
	 * @param player : Spieler
	 */
	private void placePlayerThere(final int x, final int y, final int player) {
		if (this.canPlacePlayerThere(x, y)) {
			this.getGame().getLevel().getPlayers()[player].setX(x * ApoSkunkmanConstants.TILE_SIZE);
			this.getGame().getLevel().getPlayers()[player].setY(y * ApoSkunkmanConstants.TILE_SIZE);
			this.render();
		}
	}
	
	/**
	 * gibt zurück, ob ein Spieler dort gesetzt werden darf oder nicht
	 * @param x : zu überprüfender x-Wert
	 * @param y : zu überprüfender y-Wert
	 * @return TRUE, Spieler darf dort gesetzt werden, ansonsten FALSE
	 */
	private final boolean canPlacePlayerThere(final int x, final int y) {
		for (int i = 0; i < this.getGame().getLevel().getPlayers().length; i++) {
			if ((x == (int)(this.getGame().getLevel().getPlayers()[i].getX()/ApoSkunkmanConstants.TILE_SIZE)) &&
				(y == (int)(this.getGame().getLevel().getPlayers()[i].getY()/ApoSkunkmanConstants.TILE_SIZE))) {
				return false;
			}
		}
		if (this.getGame().getLevel().getLevel()[y][x] == null) {
			return true;
		}
		return false;
	}
	
	/**
	 * wird aufgerufen, wenn das Spiel neu gezeichnet werden soll und es wird nebenbei noch das Hintergrundbild neu erstellt
	 */
	private void render() {
		this.getGame().makeBackground(true, false, false, true);
		this.getGame().render();
	}
	
	@Override
	public void think(long delta) {
	}

	@Override
	public void render(Graphics2D g) {
		if (this.curMousePosition.x > 0) {
			if (this.iSelection != null) {
				g.drawImage(this.iSelection, this.CHANGE_X + this.curMousePosition.x * ApoSkunkmanConstants.TILE_SIZE, this.CHANGE_Y + (this.curMousePosition.y + 1) * ApoSkunkmanConstants.TILE_SIZE - this.iSelection.getHeight(), null);
			}
		}
		if (this.selection >= 0) {
			int x = (int)(this.getGame().getButtons()[39 + this.selection].getX());
			int y = (int)(this.getGame().getButtons()[39 + this.selection].getY());
			int width = (int)(this.getGame().getButtons()[39 + this.selection].getWidth());
			g.setColor(Color.RED);
			g.drawRect(x - 1, y - 1, width + 1, width + 1);
		}
		if (this.iRealSelection != null) {
			g.setFont(ApoSkunkmanConstants.FONT_OPTIONS_BOLD);
			String s = "current selection";
			int x = ApoSkunkmanImageContainer.iHud.getWidth()/2;
			ApoSkunkmanConstants.drawString(g, s, x, (int)(this.getGame().getButtons()[33].getY() - 45 * ApoSkunkmanConstants.APPLICATION_SIZE), true);
			
			g.drawImage(this.iRealSelection, x - this.iRealSelection.getWidth()/2, (int)(this.getGame().getButtons()[33].getY() - 35 * ApoSkunkmanConstants.APPLICATION_SIZE) + ApoSkunkmanConstants.TILE_SIZE - this.iRealSelection.getHeight(), null);
		}
		
		// falls ein Busch ausgewählt ist, dann render die Informationen dafür 
		if ((this.getGame().getButtons()[33].isBVisible()) && (this.selectedBush != null)) {
			g.setColor(Color.RED);
			int x = (int)(this.selectedBush.getX());
			int y = (int)(this.selectedBush.getY());
			g.drawRect(x + this.CHANGE_X, y + this.CHANGE_Y, ApoSkunkmanConstants.TILE_SIZE - 1, ApoSkunkmanConstants.TILE_SIZE - 1);
			
			g.setFont(ApoSkunkmanConstants.FONT_OPTIONS_BOLD);
			x = ApoSkunkmanImageContainer.iHud.getWidth()/2;
			String s = "Goodie in bush at x: "+(int)(this.selectedBush.getX()/ApoSkunkmanConstants.TILE_SIZE)+", y: "+(int)(this.selectedBush.getY()/ApoSkunkmanConstants.TILE_SIZE);
			ApoSkunkmanConstants.drawString(g, s, x, (int)(this.getGame().getButtons()[33].getY() - 5), true);
			
			g.setFont(ApoSkunkmanConstants.FONT_OPTIONS);
			x = ApoSkunkmanImageContainer.iHud.getWidth()/2;
			s = ApoSkunkmanConstants.GOODIE_STRING[this.selectedBush.getGoodie()];
			ApoSkunkmanConstants.drawString(g, s, x, (int)(this.getGame().getButtons()[33].getY() + this.getGame().getButtons()[33].getHeight()), true);
		}
	}

}
