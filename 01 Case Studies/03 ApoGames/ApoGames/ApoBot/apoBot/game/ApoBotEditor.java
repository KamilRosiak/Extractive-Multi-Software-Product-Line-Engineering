package apoBot.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import apoBot.ApoBotConstants;
import apoBot.level.ApoBotLevel;
import apoBot.level.ApoBotLevelIO;

/**
 * Klasse, die den Editor darstellt
 * @author Dirk Aporius
 *
 */
public class ApoBotEditor {

	private ApoBotGame game;
	
	private int mainFunction, functionOne, functionTwo;
	
	private int levelXWidth, levelYWidth;
	
	private int curTile;
	
	private ApoBotLevelIO io;
	
	private int curEditorLevel;
	
	public ApoBotEditor(ApoBotGame game) {
		this.game = game;
		this.levelXWidth = 10;
		this.levelYWidth = 10;
		this.mainFunction = 16;
		this.functionOne = 12;
		this.functionTwo = 8;
		this.io = new ApoBotLevelIO();
		this.curEditorLevel = -1;
	}
	
	public void init() {
		if (this.curEditorLevel != -1) {
			this.game.setLevel(this.io.getLevel(this.curEditorLevel));
		} else {
			this.game.setLevel(new ApoBotLevel(this.levelXWidth, this.levelYWidth));
		}
	}

	public ArrayList<ApoBotLevel> getLevels() {
		return this.io.getLevels();
	}

	/**
	 * läd ein Level für den Editor
	 * @param fileName
	 */
	public void loadLevel(String fileName) {
		if (this.io.readLevel(fileName)) {
			this.curEditorLevel = 0;
			this.game.setLevel(this.io.getLevel(this.curEditorLevel));
			this.functionOne = this.io.getLevel(this.curEditorLevel).getFunctionsOneMenu();
			this.functionTwo = this.io.getLevel(this.curEditorLevel).getFunctionsTwoMenu();
			this.mainFunction = this.io.getLevel(this.curEditorLevel).getCommandsMenu();
			this.levelXWidth = this.io.getLevel(this.curEditorLevel).getLevel()[0].length;
			this.levelYWidth = this.io.getLevel(this.curEditorLevel).getLevel().length;
		}
	}
	
	public void saveLevel(String fileName) {
		this.io.writeLevel(fileName);
	}
	
	public ApoBotLevel getLevel(int level) {
		return this.io.getLevel(level);
	}
	
	public int getMaxLevel() {
		return this.io.getMaxLevel();
	}
	
	public int getCurTile() {
		return this.curTile;
	}

	public void setCurTile(int curTile) {
		this.curTile = curTile;
	}

	/**
	 * handelt die Funktionen der Buttons beim Editor
	 * @param function
	 */
	public void mouseFunction(String function) {
		if (function.equals("editorLevelXRight")) {
			this.levelXWidth += 1;
			if (this.levelXWidth >= 40) {
				this.levelXWidth = 40;
			}
		} else if (function.equals("editorLevelXLeft")) {
			this.levelXWidth -= 1;
			if (this.levelXWidth < 3) {
				this.levelXWidth = 3;
			}
		} else if (function.equals("editorLevelYRight")) {
			this.levelYWidth += 1;
			if (this.levelYWidth >= 40) {
				this.levelYWidth = 40;
			}
		} else if (function.equals("editorLevelYLeft")) {
			this.levelYWidth -= 1;
			if (this.levelYWidth < 3) {
				this.levelYWidth = 3;
			}
		} else if (function.equals("editorMainRight")) {
			this.mainFunction += 1;
			if (this.mainFunction >= 20) {
				this.mainFunction = 20;
			}
			this.game.getLevel().setFunctionsTwoMenu(this.mainFunction);
		} else if (function.equals("editorMainLeft")) {
			this.mainFunction -= 1;
			if (this.mainFunction < 1) {
				this.mainFunction = 1;
			}
			this.game.getLevel().setFunctionsTwoMenu(this.mainFunction);
		} else if (function.equals("editorFunctionOneRight")) {
			this.functionOne += 1;
			if (this.functionOne >= 12) {
				this.functionOne = 12;
			}
			this.game.getLevel().setFunctionsTwoMenu(this.functionOne);
		} else if (function.equals("editorFunctionOneLeft")) {
			this.functionOne -= 1;
			if (this.functionOne < 0) {
				this.functionOne = 0;
			}
			this.game.getLevel().setFunctionsTwoMenu(this.functionOne);
		} else if (function.equals("editorFunctionTwoRight")) {
			this.functionTwo += 1;
			if (this.functionTwo >= 12) {
				this.functionTwo = 12;
			}
			this.game.getLevel().setFunctionsTwoMenu(this.functionTwo);
		} else if (function.equals("editorFunctionTwoLeft")) {
			this.functionTwo -= 1;
			if (this.functionTwo < 0) {
				this.functionTwo = 0;
			}
			this.game.getLevel().setFunctionsTwoMenu(this.functionTwo);
		} else if (function.equals("editorSet")) {
			this.game.setLevel(new ApoBotLevel(this.levelXWidth, this.levelYWidth));
		} else if (function.equals("editorTest")) {
			this.game.getLevel().setFunctionsTwoMenu(this.functionTwo);
			this.game.getLevel().setFunctionsOneMenu(this.functionOne);
			this.game.getLevel().setCommandsMenu(this.mainFunction);
			
			this.curEditorLevel += 1;
			this.io.addLevel(this.game.getLevel(), this.curEditorLevel + 1);
			
			this.game.addLevelToIO(this.game.getLevel());
			this.game.setGame();
		} else if (function.equals("editorGround")) {
			this.setCurTile(ApoBotConstants.GROUND_EMPTY);
		} else if (function.equals("editorOriginal")) {
			this.setCurTile(ApoBotConstants.GROUND_ORIGINAL);
		} else if (function.equals("editorGoal")) {
			this.setCurTile(ApoBotConstants.GROUND_GOAL);
		} else if (function.equals("editorPlayer")) {
			this.setCurTile(ApoBotConstants.PLAYER);
		} else if (function.equals("editorLevelRight")) {
			if (this.curEditorLevel != -1) {
				this.game.getLevel().setFunctionsOneMenu(this.functionOne);
				this.game.getLevel().setFunctionsTwoMenu(this.functionTwo);
				this.game.getLevel().setCommandsMenu(this.mainFunction);
				this.curEditorLevel += 1;
				if (this.curEditorLevel >= this.io.getMaxLevel()) {
					this.curEditorLevel = 0;
				}
				this.game.setLevel(this.io.getLevel(this.curEditorLevel));
				this.functionOne = this.io.getLevel(this.curEditorLevel).getFunctionsOneMenu();
				this.functionTwo = this.io.getLevel(this.curEditorLevel).getFunctionsTwoMenu();
				this.mainFunction = this.io.getLevel(this.curEditorLevel).getCommandsMenu();
				this.levelXWidth = this.io.getLevel(this.curEditorLevel).getLevel()[0].length;
				this.levelYWidth = this.io.getLevel(this.curEditorLevel).getLevel().length;
			}
		} else if (function.equals("editorLevelLeft")) {
			if (this.curEditorLevel != -1) {
				this.game.getLevel().setFunctionsOneMenu(this.functionOne);
				this.game.getLevel().setFunctionsTwoMenu(this.functionTwo);
				this.game.getLevel().setCommandsMenu(this.mainFunction);
				this.curEditorLevel -= 1;
				if (this.curEditorLevel < 0) {
					this.curEditorLevel = this.io.getMaxLevel() - 1;
				}
				this.game.setLevel(this.io.getLevel(this.curEditorLevel));
				this.functionOne = this.io.getLevel(this.curEditorLevel).getFunctionsOneMenu();
				this.functionTwo = this.io.getLevel(this.curEditorLevel).getFunctionsTwoMenu();
				this.mainFunction = this.io.getLevel(this.curEditorLevel).getCommandsMenu();
				this.levelXWidth = this.io.getLevel(this.curEditorLevel).getLevel()[0].length;
				this.levelYWidth = this.io.getLevel(this.curEditorLevel).getLevel().length;
			}
		} else if (function.equals("editorSave")) {
			this.game.levelSave();
		} else if (function.equals("editorLoad")) {
			this.game.levelLoad(null);
		} else if (function.equals("editorAdd")) {
			this.curEditorLevel += 1;
			this.game.getLevel().setFunctionsOneMenu(this.functionOne);
			this.game.getLevel().setFunctionsTwoMenu(this.functionTwo);
			this.game.getLevel().setCommandsMenu(this.mainFunction);
			//this.game.getLevel().init();
			this.io.addLevel(this.game.getLevel(), this.curEditorLevel + 1);
		} else if (function.equals("editorRemove")) {
			if (this.curEditorLevel > -1) {
				this.curEditorLevel -= 1;
				if (this.curEditorLevel < 0) {
					if (this.io.getMaxLevel() > 0) {
						this.curEditorLevel = this.io.getMaxLevel() - 1;
					}
				}
				this.game.getLevel().init();
				this.io.removeLevel(this.game.getLevel());
				if (this.curEditorLevel > -1) {
					this.game.setLevel(this.io.getLevel(this.curEditorLevel));
				}
			}
		} else if (function.equals("editorLevelChangeLeft")) {
			if (this.curEditorLevel > -1) {
				this.io.changeLevel(this.curEditorLevel, -1);
				this.curEditorLevel -= 1;
				if (this.curEditorLevel < 0) {
					this.curEditorLevel = this.io.getMaxLevel() - 1;
				}
			}
		} else if (function.equals("editorLevelChangeRight")) {
			if (this.curEditorLevel > -1) {
				this.io.changeLevel(this.curEditorLevel, +1);
				this.curEditorLevel += 1;
				if (this.curEditorLevel >= this.io.getMaxLevel()) {
					this.curEditorLevel = 0;
				}
			}
		}
	}
	
	/**
	 * hier wird überprüft, wo die Maus genau hingeklickt hat und falls ins Level, dann wird mouseClick aufgerufen
	 * @param e
	 */
	public void mouseReleased(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();
		int w = 16 * ApoBotConstants.SIZE;
		int startX = this.game.getStartX();
		int startY = this.game.getStartY();
		for (int y = this.game.getLevel().getLevel().length - 1; y >= 0; y--) {
			for (int x = 0; x < this.game.getLevel().getLevel()[0].length; x++) {
				int myX = startX + x * w + y * w;
				int myY = startY - y * w/2 + x * w/2;
				Polygon p = new Polygon();
				p.addPoint(myX + w, myY);
				p.addPoint(myX, myY + w / 2);
				p.addPoint(myX + w, myY + w);
				p.addPoint(myX + w * 2, myY + w /2);
				if (p.intersects(mouseX, mouseY, 1, 1)) {
					this.mouseClick(x, y, e.getButton());
					break;
				}
			}
		}
	}
	
	/**
	 * erhöht entweder den Tile um eins, löscht das Tile oder ersetzt es mit dem neuen curTile
	 * @param x
	 * @param y
	 * @param button
	 */
	private void mouseClick(int x, int y, int button) {
		if (this.getCurTile() == ApoBotConstants.PLAYER) {
			this.game.getLevel().setStartPoint(new Point(x, y));
			this.game.getPlayer().setStartX(x);
			this.game.getPlayer().setStartY(y);
			this.game.getPlayer().setX(x);
			this.game.getPlayer().setY(y);
			return;
		}
		if (button == MouseEvent.BUTTON1) {
			if (this.getCurTile() == this.game.getLevel().getLevel()[y][x].getGround()) {
				this.game.getLevel().getLevel()[y][x].setStartHeight(this.game.getLevel().getLevel()[y][x].getStartHeight() + 1);
				this.game.getLevel().getLevel()[y][x].setHeight(this.game.getLevel().getLevel()[y][x].getHeight() + 1);
				if (this.game.getLevel().getLevel()[y][x].getStartHeight() > 10) {
					this.game.getLevel().getLevel()[y][x].setHeight(10);
					this.game.getLevel().getLevel()[y][x].setStartHeight(10);
				}
			} else {
				this.game.getLevel().getLevel()[y][x].setStartGround(this.getCurTile());
				this.game.getLevel().getLevel()[y][x].setStartHeight(0);
				this.game.getLevel().getLevel()[y][x].setGround(this.getCurTile());
				this.game.getLevel().getLevel()[y][x].setHeight(0);
			}
		} else if (button == MouseEvent.BUTTON3) {
			if (this.game.getLevel().getLevel()[y][x].getStartHeight() > 0) {
				this.game.getLevel().getLevel()[y][x].setStartHeight(this.game.getLevel().getLevel()[y][x].getStartHeight() - 1);
				this.game.getLevel().getLevel()[y][x].setHeight(this.game.getLevel().getLevel()[y][x].getHeight() - 1);
			} else {
				this.game.getLevel().getLevel()[y][x].setGround(ApoBotConstants.GROUND_REAL_EMPTY);
				this.game.getLevel().getLevel()[y][x].setStartGround(ApoBotConstants.GROUND_REAL_EMPTY);

			}
		}
	}
	
	/**
	 * malt die wichtigen Sachen für den Editor
	 * @param g
	 */
	public void renderEditor(Graphics2D g) {
		this.game.renderLevel(g, this.game.getILevel(), this.game.getLevel(), true);
		
		g.setColor(Color.black);
		g.fillRect(this.game.getStartX(), this.game.getStartY(), 2, 2);
		g.setFont(ApoBotConstants.FONT_MENU);
		int x = ApoBotConstants.GAME_WIDTH - 32 * 2 - 2 * 5;
		int y = 42;
		this.game.getImage().drawString(g, ""+this.levelXWidth, x, y, 0);
		y += 25;
		this.game.getImage().drawString(g, ""+this.levelYWidth, x, y, 0);

		y += 107;
		this.game.getImage().drawString(g, "MainMethod-Commands", x - 15, y - 18, 0);
		this.game.getImage().drawString(g, ""+this.mainFunction, x, y, 0);
		y += 50;
		this.game.getImage().drawString(g, "FunctionOne-Commands", x - 15, y - 18, 0);
		this.game.getImage().drawString(g, ""+this.functionOne, x, y, 0);
		y += 50;
		this.game.getImage().drawString(g, "FunctionTwo-Commands", x - 15, y - 18, 0);
		this.game.getImage().drawString(g, ""+this.functionTwo, x, y, 0);
		
		if (this.curEditorLevel > -1) {
			this.game.getImage().drawString(g, ""+(this.curEditorLevel+1)+"/"+this.io.getMaxLevel(), x, 385, 0);
		}
	}
	
}
