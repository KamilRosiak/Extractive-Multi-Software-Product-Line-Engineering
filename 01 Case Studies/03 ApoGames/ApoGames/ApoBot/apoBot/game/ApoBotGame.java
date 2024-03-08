package apoBot.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import org.apogames.ApoConstants;
import org.apogames.help.ApoHelp;

import apoBot.ApoBotComponent;
import apoBot.ApoBotConstants;
import apoBot.entity.ApoBotEntity;
import apoBot.level.ApoBotLevel;
import apoBot.level.ApoBotLevelIO;
import apoBot.level.ApoBotLevelTest;

/**
 * eigentliche Spieklasse, die wieder mal zu groß geworden ist :D
 * sie beinhaltet alles wichtige zum Spiel und handelt alle 4 SItuationen die auftreten können: Menu, Game, Editor, Tutorial 
 * und erbt von der Komponente, die schon einige Funktionen bereithält
 * @author Dirk Aporius
 *
 */
public class ApoBotGame extends ApoBotComponent {

	private static final long serialVersionUID = 1L;
	
	private final int FPS_COUNT = 2;
	
	private boolean bMenu;
	private boolean bGame;
	private boolean bTutorial;
	private boolean bEditor;

	private boolean bGo;

	private ApoBotEditor editor;
	
	private ArrayList<Float> waitTimeRender;
	
	private int waitTime;
	
	private int levelSteps;
	private int levelCommand;
	private int totalCommand;
	private int totalSteps;
	
	private int point;
	
	private ApoBotEntity[] mainCommands;
	private ApoBotEntity[] functionOneCommands;
	private ApoBotEntity[] functionTwoCommands;
	
	private int curCommand = 0;

	private int curMainCommand = 0;
	private int curFunctionOneCommand = 0;
	private int curFunctionTwoCommand = 0;
	private int curInMethod = 0;
	
	private BufferedImage iMouse;
	
	private String curFunction;
	
	private int mouseX;
	private int mouseY;
	
	private int curLevelString;
	
	private ApoBotLevelIO io;
	
	private boolean bWinUp;
	
	private float winUp;
	
	private ApoBotTutorial tutorial;
	
	private int curCommandDraw;
	
	private boolean bShowFps;
	
	public ApoBotGame(boolean mouse, boolean key, int wait_time_think, int wait_time_render) {
		//mouse gibt an, ob die Mausevents berücksichtigt werden sollen
		// key gibt an, ob Tastaturevent berücksichtigt werden sollen
		// in welchen Abständen soll gedacht werden (in Millisekunden)
		// in welchen Abständen soll gezeichnet werden (in Millisekunden)
		super(mouse, key, wait_time_think, wait_time_render);
	}

	public void init() {
		super.init();
		
		if (super.getIBackgroundOriginal() == null) {
			super.setIBackgroundOriginal(super.getImage().getImage("images/background.png", false));
		}
		
		this.curFunction = null;
		this.curLevelString = 0;
		this.curCommandDraw = ApoBotConstants.FORWARD;
		
		if (this.editor == null) {
			this.editor = new ApoBotEditor(this);
		}
		
		if (this.tutorial == null) {
			this.tutorial = new ApoBotTutorial(this);
		}
		
		if (this.waitTimeRender == null) {
			this.waitTimeRender = new ArrayList<Float>();
		} else {
			this.waitTimeRender.clear();
		}
	
		if (this.mainCommands == null) {
			this.mainCommands = new ApoBotEntity[1];
		} else {
			
		}

		if (this.functionOneCommands == null) {
			this.functionOneCommands = new ApoBotEntity[1];
		} else {
			
		}
		
		if (this.functionTwoCommands == null) {
			this.functionTwoCommands = new ApoBotEntity[1];
		} else {
			
		}
		
		this.curInMethod = 0;
		this.curMainCommand = 0;
		this.curFunctionOneCommand = -1;
		this.curFunctionTwoCommand = -1;
		this.curCommand = 0;
		
		super.setBWin(false);
		
		this.bGo = false;
		
		this.waitTime = 0;
		this.totalSteps = 0;
		
		if (this.io == null) {
			this.io = new ApoBotLevelIO();
			if (ApoConstants.B_APPLET) {
				this.loadLevel(ApoBotComponent.APPLET_LEVEL + "/" + ApoBotConstants.LEVELS[this.curLevelString] + ".bot");
			} else {
				this.loadLevel(System.getProperty("user.dir") + File.separator + "levels" + File.separator + ApoBotConstants.LEVELS[this.curLevelString] + ".bot");
			}
		}
		
		this.setMenu();
	}
	
	/**
	 * läd ein neues Level
	 */
	public void loadLevel(String fileName) {
		if (this.bEditor) {
			this.editor.loadLevel(fileName);
		} else {
			if (this.io.readLevel(fileName)) {
				this.setCurLevel(0);
				this.setLevel(this.io.getLevel(this.getCurLevel()));
				System.out.println(fileName);
			}
		}
	}

	/**
	 * speichert ein Level (nur im Editor)
	 */
	public void saveLevel(String fileName) {
		if (this.bEditor) {
			this.editor.saveLevel(fileName);
		}
	}
	
	/**
	 * fügt ein Level hinzu (für den Editor gedacht, wenn man auf Test geht und das Level anspielen möchte)
	 * @param level
	 */
	public void addLevelToIO(ApoBotLevel level) {
		this.curLevelString = 3;
		this.io.addLevel(level, 0);
	}
	
	/**
	 * wird aufgerufen, wenn man auf den MenuButton geht
	 */
	protected void setMenu() {
		super.setBWin(false);
		this.bMenu = true;
		this.bGame = false;
		this.bTutorial = false;
		this.bEditor = false;
		for (int i = 0; i < ApoBotConstants.BUTTON_MENU.length && i < super.getButtons().length; i++) {
			super.getButtons()[i].setBVisible(ApoBotConstants.BUTTON_MENU[i]);
		}
		if (ApoConstants.B_APPLET) {
			super.getButtons()[0].setBVisible(false);
		}
		this.setLevel(new ApoBotLevelTest().getMenuLevel());
	}
	
	/**
	 * wird aufgerufen, wenn man auf den GameButton geht
	 */
	protected void setGame() {
		super.setBWin(false);
		this.bMenu = false;
		this.bGame = true;
		this.bTutorial = false;
		this.bEditor = false;
		for (int i = 0; i < ApoBotConstants.BUTTON_GAME.length && i < super.getButtons().length; i++) {
			super.getButtons()[i].setBVisible(ApoBotConstants.BUTTON_GAME[i]);
		}
		if (ApoConstants.B_APPLET) {
			super.getButtons()[0].setBVisible(false);
			super.getButtons()[39].setBVisible(false);
		}
		if (super.getButtons()[13].isBVisible()) {
			if (this.bGo) {
				super.getButtons()[4].setBVisible(false);
			} else {
				super.getButtons()[13].setBVisible(false);
			}
		}
		this.nextLevelChange(0);
	}
	
	/**
	 * wird aufgerufen, wenn man auf den TutorialButton geht
	 */
	private void setTutorial() {
		super.setBWin(false);
		this.bMenu = false;
		
		this.bGame = false;
		this.bTutorial = true;
		this.bEditor = false;
		for (int i = 0; i < ApoBotConstants.BUTTON_TUTORIAL.length && i < super.getButtons().length; i++) {
			super.getButtons()[i].setBVisible(ApoBotConstants.BUTTON_TUTORIAL[i]);
		}
		if (ApoConstants.B_APPLET) {
			super.getButtons()[0].setBVisible(false);
		}
		if (super.getButtons()[13].isBVisible()) {
			if (this.bGo) {
				super.getButtons()[4].setBVisible(false);
			} else {
				super.getButtons()[13].setBVisible(false);
			}
		}
		this.tutorial.init();
	}

	/**
	 * wird aufgerufen, wenn man auf den EditorButton geht
	 */
	private void setEditor() {
		super.setBWin(false);
		this.bMenu = false;
		this.bGame = false;
		this.bTutorial = false;
		this.bEditor = true;
		this.editor.init();
		for (int i = 0; i < ApoBotConstants.BUTTON_EDITOR.length && i < super.getButtons().length; i++) {
			super.getButtons()[i].setBVisible(ApoBotConstants.BUTTON_EDITOR[i]);
		}
		if (ApoConstants.B_APPLET) {
			super.getButtons()[0].setBVisible(false);

			super.getButtons()[27].setBVisible(false);
			super.getButtons()[28].setBVisible(false);
		}
		super.getButtons()[4].setBVisible(false);
		super.getButtons()[13].setBVisible(false);
	}
	
	@Override
	public void setKeyFunction(int function) {
		if (this.bTutorial) {
			this.tutorial.keyPressed(function);
		}
		if (function == KeyEvent.VK_F) {
			this.bShowFps = !this.bShowFps;
		}
		if ((this.bTutorial) || (this.bGame) || (this.bEditor)) {
			// überprüft, ob gescrollt werden kann mit den Pfeiltasten
			int w = 16 * ApoBotConstants.SIZE;
			if ((function == KeyEvent.VK_LEFT) || (function == KeyEvent.VK_RIGHT)) {
				if (super.isLevelToWidth()) {
					if (function == KeyEvent.VK_LEFT) {
						super.setCurX(this.getCurX() + 3);
					} else if (function == KeyEvent.VK_RIGHT) {
						super.setCurX(this.getCurX() - 3);
					}
					if (this.getCurX() + this.getCurWidth() - 1 * w / 2 >= this.getLevelWidth()) {
						this.setCurX(this.getLevelWidth() - this.getCurWidth() + 1 * w / 2 - 1);
					}
					if (this.getCurX() < -1 * w/2) {
						this.setCurX(-1 * w/2 + 1);
					}
				}
			} else if ((function == KeyEvent.VK_DOWN) || (function == KeyEvent.VK_UP)) {
				if (super.isLevelToHeight()) {
					if (function == KeyEvent.VK_UP) {
						super.setCurY(this.getCurY() + 3);
					} else if (function == KeyEvent.VK_DOWN) {
						super.setCurY(this.getCurY() - 3);
					}
					if (this.getCurY() + this.getCurHeight() - 2 * w >= this.getLevelHeight()) {
						this.setCurY(this.getLevelHeight() - this.getCurHeight() + 2 * w - 1);
					}
					if (this.getCurY() < -2 * w) {
						this.setCurY(-2 * w + 1);
					}
				}
			}
		}
	}

	@Override
	public void setMouseFunction(String function) {
		if (function.equals("quit")) {
			System.exit(0);
		} else if (function.equals("menu")) {
			this.setMenu();
		} else if (function.equals("game")) {
			this.setGame();
		} else if (function.equals("tutorial")) {
			this.setTutorial();
		} else if (function.equals("editor")) {
			this.setEditor();
		} else if (function.equals("forward")) {
			this.curCommandDraw = ApoBotConstants.FORWARD;
		} else if (function.equals("left")) {
			this.curCommandDraw = ApoBotConstants.LEFT;
		} else if (function.equals("right")) {
			this.curCommandDraw = ApoBotConstants.RIGHT;
		} else if (function.equals("jump")) {
			this.curCommandDraw = ApoBotConstants.JUMP;
		} else if (function.equals("redye")) {
			this.curCommandDraw = ApoBotConstants.REDYE;
		} else if (function.equals("functionOne")) {
			this.curCommandDraw = ApoBotConstants.FUNCTION_ONE;
		} else if (function.equals("functionTwo")) {
			this.curCommandDraw = ApoBotConstants.FUNCTION_TWO;
		} else if (function.equals("go")) {
			if (this.bTutorial) {
				if (this.tutorial.canGo()) {
					this.restart();
				}
			} else {
				this.restart();
			}
		} else if (function.equals("gameLoad")) {
			super.levelLoad(null);
		} else if (function.equals("reset")) {
			this.reset();
		} else if (function.equals("gameLevelLeft")) {
			this.nextLevel(-1);
		} else if (function.equals("gameLevelRight")) {
			this.nextLevel(+1);
		} else if (function.equals("gameLevelChangeLeft")) {
			this.nextLevelChange(-1);
		} else if (function.equals("gameLevelChangeRight")) {
			this.nextLevelChange(+1);
		} else if (function.equals("stop")) {
			this.stopGo();
		} else if ((this.bEditor) && (function.indexOf("editor") != -1)) {
			this.editor.mouseFunction(function);
		}
	}

	/**
	 * wird aufgerufen, wenn man im Spiel das Level wechselt (aber nicht von 1 zu 2 sondern von easy zu middle z.B.)
	 * @param next : int-Variable die entweder +1, 0 oder -1 ist
	 */
	private void nextLevelChange(int next) {
		if (this.curLevelString > -1) {
			this.totalSteps = 0;
			this.curLevelString += next;
			if (this.curLevelString >= ApoBotConstants.LEVELS.length) {
				this.curLevelString = 0;;
			} else if (this.curLevelString < 0) {
				this.curLevelString = ApoBotConstants.LEVELS.length - 1;
			}
			if (this.curLevelString == 3) {
				super.setCurLevel(0);
				if ((this.editor.getLevels() == null) || (this.editor.getLevels().size() <= 0)) {
					this.nextLevelChange(next);
					return;
				}
				this.io.setLevels(this.editor.getLevels());
				this.setLevel(this.io.getLevel(this.getCurLevel()));
			} else {
				if (ApoConstants.B_APPLET) {
					this.loadLevel(ApoBotComponent.APPLET_LEVEL + "/" + ApoBotConstants.LEVELS[this.curLevelString] + ".bot");
				} else {
					this.loadLevel(System.getProperty("user.dir") + File.separator + "levels" + File.separator + ApoBotConstants.LEVELS[this.curLevelString] + ".bot");
				}
				this.setLevel(this.io.getLevel(super.getCurLevel()));
			}
		}
	}

	/**
	 * wird aufgerufen, wenn man im Spiel das Level wechselt (z.B. von 1 zu 2)
	 * @param next : int-Variable die entweder +1, 0 oder -1 ist
	 */
	private void nextLevel(int next) {
		if (super.getCurLevel() > -1) {
			super.setCurLevel(super.getCurLevel() + next);
			if (super.getCurLevel() < 0) {
				super.setCurLevel(this.io.getMaxLevel() - 1);
			} else if (super.getCurLevel() >= this.io.getMaxLevel()) {
				super.setCurLevel(0);
			}
			this.setLevel(this.io.getLevel(super.getCurLevel()));
		}
	}

	/**
	 * wird aufgerufen, wenn das Mausrad bewegt wird
	 */
	public void mouseWheelMoved(MouseWheelEvent e) {
		int change = e.getWheelRotation();
		this.curCommandDraw += change;
		if (this.curCommandDraw < ApoBotConstants.FORWARD) {
			this.curCommandDraw = ApoBotConstants.FUNCTION_TWO;
			if (this.functionTwoCommands.length <= 0) {
				this.curCommandDraw = ApoBotConstants.FUNCTION_ONE;
				if (this.functionOneCommands.length <= 0) {
					this.curCommandDraw = ApoBotConstants.REDYE;				
				}
			}
		} else if (this.curCommandDraw > ApoBotConstants.FUNCTION_TWO) {
			this.curCommand = ApoBotConstants.FORWARD;
		}
		if (this.curCommandDraw >= ApoBotConstants.FUNCTION_ONE) {
			if (this.curCommandDraw == ApoBotConstants.FUNCTION_ONE) {
				if (this.functionOneCommands.length <= 0) {
					this.curCommandDraw = ApoBotConstants.FUNCTION_TWO;				
				}
			}
			if (this.curCommandDraw == ApoBotConstants.FUNCTION_TWO) {
				if (this.functionTwoCommands.length <= 0) {
					this.curCommandDraw = ApoBotConstants.FORWARD;				
				}
			}
		}
		if (this.curCommandDraw > ApoBotConstants.FUNCTION_TWO) {
			this.curCommandDraw = ApoBotConstants.FORWARD;
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		if ((this.bMenu)) {
			return;
		}
		if (this.bEditor) {
			this.editor.mouseReleased(e);
		} else {
			int x = e.getX();
			int y = e.getY();
			this.levelCommand = 0;
			if (e.getButton() == MouseEvent.BUTTON3) {
				// löscht wieder die Kommandos wenn die Maus drüber ist und die rechte Maustaste gedrückt wurde
				for (int i = 0; i < this.mainCommands.length; i++) {
					if (this.mainCommands[i].intersects(x, y)) {
						this.mainCommands[i].setCommand(-1);
					}
					if (this.mainCommands[i].getCommand() != -1) {
						this.levelCommand += 1;
					}
				}
				for (int i = 0; i < this.functionOneCommands.length; i++) {
					if (this.functionOneCommands[i].intersects(x, y)) {
						this.functionOneCommands[i].setCommand(-1);
					}
					if (this.functionOneCommands[i].getCommand() != -1) {
						this.levelCommand += 1;
					}
				}
				for (int i = 0; i < this.functionTwoCommands.length; i++) {
					if (this.functionTwoCommands[i].intersects(x, y)) {
						this.functionTwoCommands[i].setCommand(-1);
					}
					if (this.functionTwoCommands[i].getCommand() != -1) {
						this.levelCommand += 1;
					}
				}
			} else if (e.getButton() == MouseEvent.BUTTON1) {
				// setzt das Kommando wenn die Maus über dem Kommando ist
				// entweder wenn es per Drag and Drop gezogen wird
				// sonst wird das derzeit gehighlighte Kommado genommen
				int command = -1;
				if (this.curFunction != null) {
					if (this.curFunction.equals("forward")) {
						command = ApoBotConstants.FORWARD;
					} else if (this.curFunction.equals("left")) {
						command = ApoBotConstants.LEFT;
					} else if (this.curFunction.equals("right")) {
						command = ApoBotConstants.RIGHT;
					} else if (this.curFunction.equals("jump")) {
						command = ApoBotConstants.JUMP;
					} else if (this.curFunction.equals("redye")) {
						command = ApoBotConstants.REDYE;
					} else if (this.curFunction.equals("functionOne")) {
						command = ApoBotConstants.FUNCTION_ONE;
					} else if (this.curFunction.equals("functionTwo")) {
						command = ApoBotConstants.FUNCTION_TWO;
					}
				}
				for (int i = 0; i < this.mainCommands.length; i++) {
					if (this.mainCommands[i].intersects(x, y)) {
						if ((command == -1) && (this.mainCommands[i].getCommand() == -1)) {
							this.mainCommands[i].setCommand(this.curCommandDraw);
						} else {
							if ((this.mouseX != -1) && (this.mouseY != -1)) {
								this.mainCommands[i].setCommand(command);
							}
							this.mouseX = -1;
							this.mouseY = -1;
						}
						//return;
					}
					if (this.mainCommands[i].getCommand() != -1) {
						this.levelCommand += 1;
					}
				}
				for (int i = 0; i < this.functionOneCommands.length; i++) {
					if (this.functionOneCommands[i].intersects(x, y)) {
						if ((command == -1) && (this.functionOneCommands[i].getCommand() == -1)) {
							this.functionOneCommands[i].setCommand(this.curCommandDraw);
						} else {
							if ((this.mouseX != -1) && (this.mouseY != -1)) {
								if (command != ApoBotConstants.FUNCTION_ONE) {
									this.functionOneCommands[i].setCommand(command);
								} else {
									this.functionOneCommands[i].setCommand(-1);
								}
								this.mouseX = -1;
								this.mouseY = -1;
							}
						}
						//return;
					}
					if (this.functionOneCommands[i].getCommand() != -1) {
						this.levelCommand += 1;
					}
				}
				for (int i = 0; i < this.functionTwoCommands.length; i++) {
					if (this.functionTwoCommands[i].intersects(x, y)) {
						if ((command == -1) && (this.functionTwoCommands[i].getCommand() == -1)) {
							this.functionTwoCommands[i].setCommand(this.curCommandDraw);
						} else {
							if ((this.mouseX != -1) && (this.mouseY != -1)) {
								if (command != ApoBotConstants.FUNCTION_TWO) {
									this.functionTwoCommands[i].setCommand(command);
								} else {
									this.functionTwoCommands[i].setCommand(-1);
								}
								this.mouseX = -1;
								this.mouseY = -1;
							}
						}
						//return;
					}
					if (this.functionTwoCommands[i].getCommand() != -1) {
						this.levelCommand += 1;
					}
				}
				this.mouseX = -1;
				this.mouseY = -1;
			}
		}
	}
	
	public void mousePressed(MouseEvent e) {
		if (super.isBWin()) {
			this.nextLevel(+1);
			return;
		}
		super.mousePressed(e);
		if (!this.bGo) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				this.mouseX = e.getX();
				this.mouseY = e.getY();
				for (int i = 5; i < 12; i++) {
					if (super.getButtons()[i].isBPressed()) {
						this.iMouse = super.getButtons()[i].getIBackground().getSubimage(0, 0, (int)(super.getButtons()[i].getWidth()), (int)(super.getButtons()[i].getHeight()));
						this.curFunction = super.getButtons()[i].getFunction();
						return;
					}
				}
				// setzt das gehighlight Kommando auf den Wert wo die Maus grad ist
				for (int i = 0; i < this.mainCommands.length; i++) {
					if ((this.mainCommands[i].intersects(this.mouseX, this.mouseY)) && (this.mainCommands[i].getCommand() != -1)) {
						this.iMouse = super.getButtons()[this.mainCommands[i].getCommand() + 5].getIBackground().getSubimage(0, 0, (int)(super.getButtons()[this.mainCommands[i].getCommand() + 5].getWidth()), (int)(super.getButtons()[this.mainCommands[i].getCommand() + 5].getHeight()));
						this.curFunction = super.getButtons()[this.mainCommands[i].getCommand() + 5].getFunction();
						this.mainCommands[i].setCommand(-1);
						return;
					}
				}
				for (int i = 0; i < this.functionOneCommands.length; i++) {
					if ((this.functionOneCommands[i].intersects(this.mouseX, this.mouseY)) && (this.functionOneCommands[i].getCommand() != -1)) {
						this.iMouse = super.getButtons()[this.functionOneCommands[i].getCommand() + 5].getIBackground().getSubimage(0, 0, (int)(super.getButtons()[this.functionOneCommands[i].getCommand() + 5].getWidth()), (int)(super.getButtons()[this.functionOneCommands[i].getCommand() + 5].getHeight()));
						this.curFunction = super.getButtons()[this.functionOneCommands[i].getCommand() + 5].getFunction();
						this.functionOneCommands[i].setCommand(-1);
						return;
					}
				}
				for (int i = 0; i < this.functionTwoCommands.length; i++) {
					if ((this.functionTwoCommands[i].intersects(this.mouseX, this.mouseY)) && (this.functionTwoCommands[i].getCommand() != -1)) {
						this.iMouse = super.getButtons()[this.functionTwoCommands[i].getCommand() + 5].getIBackground().getSubimage(0, 0, (int)(super.getButtons()[this.functionTwoCommands[i].getCommand() + 5].getWidth()), (int)(super.getButtons()[this.functionTwoCommands[i].getCommand() + 5].getHeight()));
						this.curFunction = super.getButtons()[this.functionTwoCommands[i].getCommand() + 5].getFunction();
						this.functionTwoCommands[i].setCommand(-1);
						return;
					}
				}
				this.mouseX = -1;
				this.mouseY = -1;
				this.curFunction = null;
			}
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);
		if ((this.mouseX != -1) && (this.mouseY != -1)) {
			this.mouseX = e.getX();
			this.mouseY = e.getY();
		}
	}
	
	/**
	 * wird aufgerufen wenn auf den reset Button gedrückt wurde und resetet das Level
	 */
	public void reset() {
		super.restart();
		super.setBWin(false);
		this.stopGo();
		this.levelSteps = 0;
		this.point = 0;
	}
	
	/**
	 * startet das Level neu und lässt den Spieler dann laufen
	 */
	public void restart() {
		super.restart();
		this.curInMethod = 0;
		this.curMainCommand = 0;
		this.curFunctionOneCommand = -1;
		this.curFunctionTwoCommand = -1;
		this.levelSteps = 0;
		super.setBWin(false);
		super.getButtons()[4].setBVisible(false);
		super.getButtons()[13].setBVisible(true);
		for (int i = 39; i < 44; i++) {
			super.getButtons()[i].setBVisible(false);
		}
		if (ApoConstants.B_APPLET) {
			super.getButtons()[0].setBVisible(false);
			super.getButtons()[39].setBVisible(false);
		}
		this.bGo = true;
	}
	
	/**
	 * sucht den nächsten Kommando der gewählt wird
	 * @return nächster Kommando
	 */
	private int getNextCommand() {
		int command = -1;
		if (this.curInMethod == 1) {
			if (curFunctionOneCommand >= this.functionOneCommands.length) {
				this.curFunctionOneCommand = -1;
				this.curFunctionTwoCommand = -1;
				this.curInMethod = 0;
				return this.getNextCommand();
			}
			command = this.functionOneCommands[this.curFunctionOneCommand].getCommand();
			//System.out.println("curInMethod "+this.curInMethod+" command: "+command);
			if (command == ApoBotConstants.FUNCTION_ONE) {
				this.curInMethod = 1;
				this.curFunctionOneCommand = 0;
			} else if (command == ApoBotConstants.FUNCTION_TWO) {
				this.curInMethod = 2;
				this.curFunctionTwoCommand = 0;
				this.curFunctionOneCommand += 1;
			} else if (this.curFunctionOneCommand + 1 >= this.functionOneCommands.length) {
				this.curFunctionOneCommand = -1;
				if (this.curFunctionTwoCommand < 0) {
					this.curInMethod = 0;
				} else {
					this.curInMethod = 2;
				}
			} else {
				this.curFunctionOneCommand += 1;				
			}
		} else if (this.curInMethod == 2) {
			if (curFunctionTwoCommand >= this.functionTwoCommands.length) {
				this.curFunctionOneCommand = -1;
				this.curFunctionTwoCommand = -1;
				this.curInMethod = 0;
				return this.getNextCommand();
			}
			command = this.functionTwoCommands[this.curFunctionTwoCommand].getCommand();
			if (command == ApoBotConstants.FUNCTION_ONE) {
				this.curInMethod = 1;
				this.curFunctionOneCommand = 0;
				this.curFunctionTwoCommand += 1;
			} else if (command == ApoBotConstants.FUNCTION_TWO) {
				this.curInMethod = 2;
				this.curFunctionTwoCommand = 0;
			} else if (this.curFunctionTwoCommand + 1 >= this.functionTwoCommands.length) {
				this.curInMethod = 0;
				this.curFunctionTwoCommand = -1;
				if (this.curFunctionOneCommand < 0) {
					this.curInMethod = 0;
				} else {
					this.curInMethod = 1;
				}
			} else {
				this.curFunctionTwoCommand += 1;
			}
		} else if (this.curInMethod == 0) {
			if (this.curMainCommand >= this.mainCommands.length) {
				this.curFunctionOneCommand = -1;
				this.curFunctionTwoCommand = -1;
				return -1;
			}
			command = this.mainCommands[this.curMainCommand].getCommand();
			if (command == ApoBotConstants.FUNCTION_ONE) {
				this.curFunctionOneCommand = 0;
				this.curInMethod = 1;
				this.curMainCommand += 1;
			} else if (command == ApoBotConstants.FUNCTION_TWO) {
				this.curInMethod = 2;
				this.curFunctionTwoCommand = 0;
				this.curMainCommand += 1;
			} else if (this.curMainCommand + 1 > this.mainCommands.length) {
				this.curFunctionOneCommand = -1;
				this.curFunctionTwoCommand = -1;
				return -1;
			} else {
				this.curMainCommand += 1;
			}
		}
		if (command == -1) {
			return this.getNextCommand();
		}
		return command;
	}
	
	/**
	 * setzt das aktuelle Level auf den übergebenen Wert
	 */
	public void setLevel(ApoBotLevel level) {
		level.init();
		this.curCommandDraw = ApoBotConstants.FORWARD;
		this.levelCommand = 0;
		this.point = 0;
		this.levelSteps = 0;
		this.totalCommand = 0;
		super.setLevel(level);
		super.setBWin(false);
		this.mainCommands = new ApoBotEntity[super.getLevel().getCommandsMenu()];
		this.totalCommand += this.mainCommands.length;
		int startX = ApoBotConstants.GAME_WIDTH - 32 * 4 - 4 * 5;
		int x = startX;
		int y = 30;
		// schaut wieviel MainMethod, FunctionOne und FunctionTwo Kommandos es geben wird und erstellt diese
		for (int i = 0; i < this.mainCommands.length; i++) {
			this.mainCommands[i] = new ApoBotEntity(-1, x, y, 32, 32);
			x += 32 + 5;
			if ((x >= ApoBotConstants.GAME_WIDTH) && (i < this.mainCommands.length - 1)) {
				x = startX;
				y += 37;
			}
		}
		x = startX;
		y += 57;

		int endX = (int)(super.getButtons()[9].getX() + super.getButtons()[9].getWidth());
		this.functionOneCommands = new ApoBotEntity[super.getLevel().getFunctionsOneMenu()];
		this.totalCommand += this.functionOneCommands.length;
		for (int i = 0; i < this.functionOneCommands.length; i++) {
			this.functionOneCommands[i] = new ApoBotEntity(-1, x, y, 32, 32);
			x += 32 + 5;
			if ((x >= ApoBotConstants.GAME_WIDTH) && (i < this.functionOneCommands.length - 1)) {
				x = startX;
				y += 37;
			}
		}
		if ((this.bGame) || (this.bTutorial)) {
			if (super.getLevel().getFunctionsOneMenu() <= 0) {
				super.getButtons()[10].setBVisible(false);
			} else {
				super.getButtons()[10].setBVisible(true);
				endX = (int)(super.getButtons()[10].getX() + super.getButtons()[10].getWidth());
			}
		}

		x = startX;
		y += 57;
		this.functionTwoCommands = new ApoBotEntity[super.getLevel().getFunctionsTwoMenu()];
		this.totalCommand += this.functionTwoCommands.length;
		for (int i = 0; i < this.functionTwoCommands.length; i++) {
			this.functionTwoCommands[i] = new ApoBotEntity(-1, x, y, 32, 32);
			x += 32 + 5;
			if (x >= ApoBotConstants.GAME_WIDTH) {
				x = startX;
				y += 37;
			}
		}
		if ((this.bGame) || (this.bTutorial)) {
			if (super.getLevel().getFunctionsTwoMenu() <= 0) {
				super.getButtons()[11].setBVisible(false);
			} else {
				super.getButtons()[11].setBVisible(true);
				endX = (int)(super.getButtons()[11].getX() + super.getButtons()[11].getWidth());
			}
		}
		int height = (int)this.mainCommands[this.mainCommands.length - 1].getY();
		if (this.functionTwoCommands.length > 0) {
			height = (int)this.functionTwoCommands[this.functionTwoCommands.length - 1].getY();
		} else if (this.functionOneCommands.length > 0) {
			height = (int)this.functionOneCommands[this.functionOneCommands.length - 1].getY();
		}
		// erstellt den neuen Background
		super.setIBackground(super.getImage().getIBackground(super.getIBackgroundOriginal(), 4 * 32 + 5 * 5, height + 40, (int)(super.getButtons()[5].getX()), (int)(super.getButtons()[5].getY()), (int)(endX - super.getButtons()[5].getX())));
	}
	
	/**
	 * wird aufgerufen wenn der Spieler fertig ist mit allen Kommandos
	 */
	public void stopGo() {
		this.bGo = false;
		if (!this.bEditor) {
			super.getButtons()[4].setBVisible(true);
			super.getButtons()[13].setBVisible(false);
			if (!this.bTutorial) {
				for (int i = 39; i < 44; i++) {
					super.getButtons()[i].setBVisible(true);
				}
				if (ApoConstants.B_APPLET) {
					super.getButtons()[0].setBVisible(false);
					super.getButtons()[39].setBVisible(false);
				}
			}
		}
	}
	
	/**
	 * überprüft, ob das Level gewonnen wurde oder nicht
	 * @return TRUE, Level gewonnen, sonst FALSE
	 */
	private boolean isLevelWin() {
		for (int y = 0; y < this.getLevel().getLevel().length; y++) {
			for (int x = 0; x < this.getLevel().getLevel()[0].length; x++) {
				if (this.getLevel().getLevel()[y][x].getGround() == ApoBotConstants.GROUND_ORIGINAL) {
					return false;
				}
			}	
		}
		return true;
	}
	
	/**
	 * kümmert sich um das "nachdenken" und die Spielerbewegung und welches Kommando ausgeführt wird
	 */
	public void think(int delta) {
		super.think(delta);
		if (this.bTutorial) {
			this.tutorial.think(delta);
		} else if (this.bMenu) {
			// lässt den SPieler im Menu zufällig laufen
			super.centerCamera();
			if ((this.getPlayer().isBRun()) || (this.getPlayer().isBJump())) {
				this.getPlayer().think(delta);
				this.waitTime -= delta;
			} else {
				if (this.waitTime > 0) {
					this.waitTime -= delta;
				} else {
					int nextCommand = (int)(Math.random() * 100);
					if (nextCommand < 50) {
						nextCommand = ApoBotConstants.FORWARD;
					} else if (nextCommand < 60) {
						nextCommand = ApoBotConstants.LEFT;
					} else if (nextCommand < 70) {
						nextCommand = ApoBotConstants.RIGHT;
					} else if (nextCommand < 80) {
						nextCommand = ApoBotConstants.REDYE;
					} else {
						nextCommand = ApoBotConstants.JUMP;
					}
					if (nextCommand == ApoBotConstants.FORWARD) {
						if (!this.canPlayerRun()) {
							if (this.canPlayerJump()) {
								nextCommand = ApoBotConstants.JUMP;
							} else {
								nextCommand += 1;
							}
						}
					} else if (nextCommand == ApoBotConstants.REDYE) {
						if (this.getLevel().getLevel()[(int)this.getPlayer().getY()][(int)this.getPlayer().getX()].getGround() == ApoBotConstants.GROUND_EMPTY) {
							nextCommand += 1;
						}
					}
					this.nextCommand(nextCommand);
				}
			}
		} else if (this.bEditor) {
			
		} else {
			this.thinkMove(delta);
		}
	}

	/**
	 * wird aufgerufen um den Spieler im TUtorial und Spiel zu bewegen
	 * @param delta
	 */
	protected void thinkMove(int delta) {
		if (super.isBWin()) {
			// wenn das Level gewonnen wurde dann springt der Spieler in der Auswertung hoch und runter
			// darum kümmert sich das hier :D
			if (this.bWinUp) {
				this.winUp += (float)delta * 0.05f;
				if (this.winUp >= 16) {
					this.bWinUp = false;
				}
			} else {
				this.winUp -= (float)delta * 0.05f;
				if (this.winUp <= 0) {
					this.bWinUp = true;
				}
			}
			return;
		}
		// fals gelaufen wird dann
		if (this.bGo) {
			// zentriere Kamera und führe Kommando aus bzw hole dir neuen Kommando
			this.centerCamera();
			if ((this.getPlayer().isBRun()) || (this.getPlayer().isBJump())) {
				this.getPlayer().think(delta);
				this.waitTime -= delta;
			} else {
				if (this.waitTime > 0) {
					this.waitTime -= delta;
				} else {
					int nextCommand = this.getNextCommand();
					if (nextCommand != -1) {
						this.levelSteps += 1;
						this.totalSteps += 1;
						this.curCommand = nextCommand;
						this.nextCommand(this.curCommand);
					} else {
						// falls es keinen Kommando mehr gibt dann überprüfe ob das Level gewonnen wurde
						if (this.isLevelWin()) {
							this.point = 1000 + (this.totalCommand - this.levelCommand) * 15 - this.levelSteps * 3;
							this.setBWin(true);
							this.bWinUp = true;
							this.winUp = 0;
						} else {
							this.stopGo();
						}
					}
				}
			}
		}
	}
	
	/**
	 * setzt das nächste Kommand für den Spieler
	 * @param command : welcher Kommando es ist =)
	 */
	private void nextCommand(int command) {
		//System.out.println("command = "+command);
		if (command == ApoBotConstants.FORWARD) {
			if (this.canPlayerRun()) {
				this.getPlayer().setBRun(true);
			}
			this.waitTime += 300;
		} else if (command == ApoBotConstants.REDYE) {
			if (this.getLevel().getLevel()[(int)this.getPlayer().getY()][(int)this.getPlayer().getX()].getGround() == ApoBotConstants.GROUND_GOAL) {
				this.getLevel().getLevel()[(int)this.getPlayer().getY()][(int)this.getPlayer().getX()].setGround(ApoBotConstants.GROUND_ORIGINAL);
			} else if (this.getLevel().getLevel()[(int)this.getPlayer().getY()][(int)this.getPlayer().getX()].getGround() == ApoBotConstants.GROUND_ORIGINAL) {
				this.getLevel().getLevel()[(int)this.getPlayer().getY()][(int)this.getPlayer().getX()].setGround(ApoBotConstants.GROUND_GOAL);
			}
			this.makeBackground();
			this.waitTime += 300;
		} else if (command == ApoBotConstants.LEFT) {
			this.getPlayer().setCurDir(this.getPlayer().getCurDir() - 1);
			this.waitTime += 300;
		} else if (command == ApoBotConstants.RIGHT) {
			this.getPlayer().setCurDir(this.getPlayer().getCurDir() + 1);
			this.waitTime += 300;
		} else if (command == ApoBotConstants.JUMP) {
			if (this.canPlayerJump()) {
				this.getPlayer().setBRun(true);				
			} else {
				this.getPlayer().setJumpHeight(0);
			}
			this.getPlayer().setBJump(true);
			this.waitTime += 300;
		}
	}
	
	/**
	 * überprüft, ob ein Spieler geradeaus laufen darf oder nicht
	 * @return TRUE; SPieler darf laufen, sonst FALSE
	 */
	private boolean canPlayerRun() {
		int x = (int)this.getPlayer().getX();
		int y = (int)this.getPlayer().getY();
		int height = this.getLevel().getLevel()[y][x].getHeight();
		if (this.getPlayer().getCurDir() == ApoBotConstants.DIR_SOUTH_DOWNRIGHT) {
			x += 1;
		} else if (this.getPlayer().getCurDir() == ApoBotConstants.DIR_NORTH_UPLEFT) {
			x -= 1;
		} else if (this.getPlayer().getCurDir() == ApoBotConstants.DIR_WEST_DOWNLEFT) {
			y -= 1;
		} else if (this.getPlayer().getCurDir() == ApoBotConstants.DIR_EAST_UPRIGHT) {
			y += 1;
		}
		if ((x < 0) || (y < 0) || (x >= this.getLevel().getLevel()[0].length) || (y >= this.getLevel().getLevel().length)) {
			return false;
		} else if (this.getLevel().getLevel()[y][x].getGround() == ApoBotConstants.GROUND_REAL_EMPTY) {
			return false;
		}
		if (height == this.getLevel().getLevel()[y][x].getHeight()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * gibt zurück, ob ein Spieler springen darf oder nicht
	 * @return TRUE; Spieler kann eine Stufe höher springen oder viel tiefer, sonst FALSE (springt auf der Stelle)
	 */
	private boolean canPlayerJump() {
		int x = (int)this.getPlayer().getX();
		int y = (int)this.getPlayer().getY();
		int height = this.getLevel().getLevel()[y][x].getHeight();
		if (this.getPlayer().getCurDir() == ApoBotConstants.DIR_SOUTH_DOWNRIGHT) {
			x += 1;
		} else if (this.getPlayer().getCurDir() == ApoBotConstants.DIR_NORTH_UPLEFT) {
			x -= 1;
		} else if (this.getPlayer().getCurDir() == ApoBotConstants.DIR_WEST_DOWNLEFT) {
			y -= 1;
		} else if (this.getPlayer().getCurDir() == ApoBotConstants.DIR_EAST_UPRIGHT) {
			y += 1;
		}
		if ((x < 0) || (y < 0) || (x >= this.getLevel().getLevel()[0].length) || (y >= this.getLevel().getLevel().length)) {
			return false;
		} else if (this.getLevel().getLevel()[y][x].getGround() == ApoBotConstants.GROUND_REAL_EMPTY) {
			return false;
		}
		if ((height + 1 >= this.getLevel().getLevel()[y][x].getHeight()) && (height != this.getLevel().getLevel()[y][x].getHeight())) {
			this.getPlayer().setJumpHeight((this.getLevel().getLevel()[y][x].getHeight() - height) * 8);
			return true;
		} else {
			return false;
		}
	}
	
	// malt das ganze Level
	// alle Methoden die nun noch kommen, kümmern sich um das Zeichnen
	@Override
	public void render(Graphics2D g) {
		long time = System.nanoTime();
		
		BufferedImage iBackground = super.getIBackground();
		if ((this.bMenu) || (this.bEditor)) {
			iBackground = super.getIBackgroundOriginal();
		}
		if (iBackground != null) {
			g.drawImage(iBackground, 0, 0, null);
		}
		if (this.bMenu) {
			super.renderLevel(g, super.getILevel(), super.getLevel());
		} else if (this.bGame) {
			super.renderLevel(g, super.getILevel(), super.getLevel());
			try {
				this.renderMenu(g);
			} catch (Exception e) {
				
			}
		} else if (this.bTutorial) {
			super.renderLevel(g, super.getILevel(), super.getLevel());
			try {
				this.renderMenu(g);
			} catch (Exception e) {
				
			}
			this.tutorial.renderMenuTutorial(g);
		} else if (this.bEditor) {
			this.editor.renderEditor(g);
		}
		super.renderButtons(g);
		
		this.renderMouseImage(g);
		this.renderWin(g);
		
		float renderTime = ApoHelp.round(((System.nanoTime() - time)/1000000f), 2);
		this.renderFPS(g, renderTime);
	}
	
	private void renderFPS(Graphics2D g, float renderTime) {
		g.setColor(Color.black);
		g.setFont(ApoBotConstants.FONT_MENU);
		if (this.bShowFps) {
			super.renderFPS(g, 5, 105);
			g.drawString(""+renderTime, 5, 125);
		}
		// nun kommt die Berechnung, ob die FPS erhöht werden dürfen oder nicht
		this.waitTimeRender.add(renderTime);
		if (this.waitTimeRender.size() > 3000 / super.getWAIT_TIME_RENDER()) {
			float all = 0;
			for (int i = 0; i < this.waitTimeRender.size(); i++) {
				all += this.waitTimeRender.get(i);
			}
			if (all / this.waitTimeRender.size() > super.getWAIT_TIME_RENDER()) {
				super.setWAIT_TIME_RENDER((int)(all / this.waitTimeRender.size() + 2));
			} else if ((all / this.waitTimeRender.size() < super.getWAIT_TIME_RENDER() - this.FPS_COUNT - 1) &&
						(super.getWAIT_TIME_RENDER() - this.FPS_COUNT >= 20)){
				super.setWAIT_TIME_RENDER(super.getWAIT_TIME_RENDER() - this.FPS_COUNT);
			} else if ((all / this.waitTimeRender.size() < super.getWAIT_TIME_RENDER() - this.FPS_COUNT - 1)){
				super.setWAIT_TIME_RENDER(20);
			}
			this.waitTimeRender.clear();
		}
	}
	
	private void renderMenu(Graphics2D g) {
		g.setFont(ApoBotConstants.FONT_MENU);
		
		g.setColor(Color.yellow);
		if (this.curInMethod == 0) {
			if ((this.curMainCommand > 0) && (this.curMainCommand < this.mainCommands.length)) {
				g.drawRect((int)(this.mainCommands[this.curMainCommand - 1].getX() - 1), (int)(this.mainCommands[this.curMainCommand - 1].getY() - 1), (int)(this.mainCommands[this.curMainCommand - 1].getWidth() + 2), (int)(this.mainCommands[this.curMainCommand - 1].getHeight() + 2));
			}
		} else if (this.curInMethod == 1) {
			if ((this.curFunctionOneCommand > 0) && (this.curFunctionOneCommand < this.functionOneCommands.length)) {
				g.drawRect((int)(this.functionOneCommands[this.curFunctionOneCommand - 1].getX() - 1), (int)(this.functionOneCommands[this.curFunctionOneCommand - 1].getY() - 1), (int)(this.functionOneCommands[this.curFunctionOneCommand - 1].getWidth() + 2), (int)(this.functionOneCommands[this.curFunctionOneCommand - 1].getHeight() + 2));
			}
		} else if (this.curInMethod == 2) {
			if ((this.curFunctionTwoCommand > 0) && (this.curFunctionTwoCommand < this.functionTwoCommands.length)) {
				g.drawRect((int)(this.functionTwoCommands[this.curFunctionTwoCommand - 1].getX() - 1), (int)(this.functionTwoCommands[this.curFunctionTwoCommand - 1].getY() - 1), (int)(this.functionTwoCommands[this.curFunctionTwoCommand - 1].getWidth() + 2), (int)(this.functionTwoCommands[this.curFunctionTwoCommand - 1].getHeight() + 2));
			}
		}

		g.setColor(Color.yellow);
		g.drawRect((int)(super.getButtons()[this.curCommandDraw + 5].getX() - 1), (int)(super.getButtons()[this.curCommandDraw + 5].getY() - 1), (int)(super.getButtons()[this.curCommandDraw + 5].getWidth() + 2), (int)(super.getButtons()[this.curCommandDraw + 5].getHeight() + 2));
		
		g.setColor(Color.black);
		int x = ApoBotConstants.GAME_WIDTH - 32 * 2 - 2 * 5;
		int y = 25;
		this.getImage().drawString(g, "Main-Method", x, y, 0);
		y += 5;
		int count = this.mainCommands.length - 1;
		int curX = ApoBotConstants.GAME_WIDTH - 32 * 4 - 4 * 5;
		while (count >= 0) {
			if ((this.mainCommands.length >= this.getLevel().getCommandsMenu() - count) &&
				(this.mainCommands[this.mainCommands.length - 1 - count].getCommand() > -1)) {
				g.drawImage(super.getButtons()[5+this.mainCommands[this.mainCommands.length - 1 - count].getCommand()].getIBackground().getSubimage(0, 0, 32, 32), curX, y, null);
			} else {
				g.drawImage(this.getIButtonEmpty(), curX, y, null);
			}
			curX += 32 * 1 + 1 * 5;
			count--;
			if ((count >= 0) && (curX >= ApoBotConstants.GAME_WIDTH)) {
				curX = ApoBotConstants.GAME_WIDTH - 32 * 4 - 4 * 5;
				y += 37;
			}
		}
		
		if (this.functionOneCommands.length > 0) {
			y += 52;
			this.getImage().drawString(g, "Function-One", x, y, 0);
			y += 5;
			count = this.functionOneCommands.length - 1;
			curX = ApoBotConstants.GAME_WIDTH - 32 * 4 - 4 * 5;
			while (count >= 0) {
				if ((this.functionOneCommands.length >= this.getLevel().getFunctionsOneMenu() - count) &&
						(this.functionOneCommands[this.functionOneCommands.length - 1 - count].getCommand() != -1)){
					g.drawImage(super.getButtons()[5+this.functionOneCommands[this.functionOneCommands.length - 1 - count].getCommand()].getIBackground().getSubimage(0, 0, 32, 32), curX, y, null);
				} else {
					g.drawImage(this.getIButtonEmpty(), curX, y, null);
				}
				curX += 32 * 1 + 1 * 5;
				count--;
				if ((count >= 0) && (curX >= ApoBotConstants.GAME_WIDTH)) {
					curX = ApoBotConstants.GAME_WIDTH - 32 * 4 - 4 * 5;
					y += 37;
				}
			}
		}

		if (this.functionTwoCommands.length > 0) {
			y += 52;
			this.getImage().drawString(g, "Function-Two", x, y, 0);
			y += 5;
			count = this.functionTwoCommands.length - 1;
			curX = ApoBotConstants.GAME_WIDTH - 32 * 4 - 4 * 5;
			while (count >= 0) {
				if ((this.functionTwoCommands.length >= this.getLevel().getFunctionsTwoMenu() - count) &&
						(this.functionTwoCommands[this.functionTwoCommands.length - 1 - count].getCommand() > -1)){
					g.drawImage(super.getButtons()[5+this.functionTwoCommands[this.functionTwoCommands.length - 1 - count].getCommand()].getIBackground().getSubimage(0, 0, 32, 32), curX, y, null);
				} else {
					g.drawImage(this.getIButtonEmpty(), curX, y, null);
				}
				curX += 32 * 1 + 1 * 5;
				count--;
				if ((count >= 0) && (curX >= ApoBotConstants.GAME_WIDTH)) {
					curX = ApoBotConstants.GAME_WIDTH - 32 * 4 - 4 * 5;
					y += 37;
				}
			}
		}

		if (!this.bTutorial) {
			if (!this.bGo) {
				g.setColor(Color.white);
				g.fillRoundRect((int)(super.getButtons()[40].getX() - 3), (int)(super.getButtons()[39].getY() - 5), (int)(115), (int)(84), 30, 30);
				g.setColor(Color.black);
				g.drawRoundRect((int)(super.getButtons()[40].getX() - 3), (int)(super.getButtons()[39].getY() - 5), (int)(115), (int)(84), 30, 30);

				this.getImage().drawString(g, ""+ApoBotConstants.LEVELS[this.curLevelString], 60, 50, 0);
				this.getImage().drawString(g, ""+(super.getCurLevel() + 1)+" / "+this.io.getMaxLevel(), 60, 75, 0);
			}

			g.setColor(Color.white);
			g.fillRoundRect((int)(2), (int)(ApoBotConstants.GAME_HEIGHT - 110), (int)(185), (int)(70), 30, 30);
			g.setColor(Color.black);
			g.drawRoundRect((int)(2), (int)(ApoBotConstants.GAME_HEIGHT - 110), (int)(185), (int)(70), 30, 30);
			g.drawString("Level Commands: "+this.levelCommand+" / "+this.totalCommand, 5, ApoBotConstants.GAME_HEIGHT - 90);
			g.drawString("Level Steps: "+this.levelSteps, 5, ApoBotConstants.GAME_HEIGHT - 70);
			g.drawString("Total Steps: "+this.totalSteps, 5, ApoBotConstants.GAME_HEIGHT - 50);
		}
	}

	private void renderMouseImage(Graphics2D g) {
		if ((this.mouseX >= 0) && (this.mouseY >= 0) && (this.iMouse != null)) {
			g.drawImage(this.iMouse, this.mouseX - this.iMouse.getWidth()/2, this.mouseY - this.iMouse.getHeight()/2, null);
		}
	}
	
	private void renderWin(Graphics2D g) {
		if (super.isBWin()) {
			g.setColor(new Color(0, 0, 0));
			g.fillRoundRect(100, 100, ApoBotConstants.GAME_GAME_WIDTH - 200, ApoBotConstants.GAME_GAME_HEIGHT - 200, 20, 20);
			g.setColor(Color.white);
			g.drawRoundRect(100, 100, ApoBotConstants.GAME_GAME_WIDTH - 200, ApoBotConstants.GAME_GAME_HEIGHT - 200, 20, 20);

			g.setFont(ApoBotConstants.FONT_WIN);
			int x = ApoBotConstants.GAME_GAME_WIDTH/2;
			this.getImage().drawString(g, "Congratulation", x, 140, 0);

			g.setFont(ApoBotConstants.FONT_MENU);
			this.getImage().drawString(g, "You passed the level in "+this.levelSteps+" steps", x, 190, 0);
			this.getImage().drawString(g, "and you needed "+this.levelCommand+" commands", x, 210, 0);
			this.getImage().drawString(g, "Press a mousebutton to start", x, 350, 0);
			this.getImage().drawString(g, "the next level", x, 370, 0);

			g.setFont(ApoBotConstants.FONT_WIN);
			this.getImage().drawString(g, "Points", x, 275, 0);
			this.getImage().drawString(g, ""+this.point, x, 300, 0);

			g.drawImage(this.getPlayer().getIBackground().getSubimage( (int)(0 * ApoBotConstants.TILE_WIDTH * ApoBotConstants.SIZE ), 2 * ApoBotConstants.TILE_HEIGHT * ApoBotConstants.SIZE, (int)ApoBotConstants.TILE_WIDTH * ApoBotConstants.SIZE, (int)(ApoBotConstants.TILE_HEIGHT * ApoBotConstants.SIZE) ), (int)(x - 100), (int)(240 - this.winUp), null);
			g.drawImage(this.getPlayer().getIBackground().getSubimage( (int)(0 * ApoBotConstants.TILE_WIDTH * ApoBotConstants.SIZE ), 3 * ApoBotConstants.TILE_HEIGHT * ApoBotConstants.SIZE, (int)ApoBotConstants.TILE_WIDTH * ApoBotConstants.SIZE, (int)(ApoBotConstants.TILE_HEIGHT * ApoBotConstants.SIZE) ), (int)(x + 50), (int)(240 - this.winUp), null);
		}
	}
}