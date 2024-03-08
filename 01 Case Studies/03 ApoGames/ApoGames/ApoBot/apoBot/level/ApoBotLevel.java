package apoBot.level;

import java.awt.Point;

import apoBot.ApoBotConstants;

/**
 * Klasse, die das eigentliche Level repräsentiert
 * es beinhaltet einen 2d Array und die Angaben, wo der Spieler startet, wo er hinschaut
 * und wieviel Kommandos benutzt werden dürfen
 * @author Dirk Aporius
 *
 */
public class ApoBotLevel {

	private ApoBotLevelLevel[][] level;
	
	private Point startPoint;
	
	private int directionView;
	
	private int commandsMenu;
	
	private int functionsOneMenu;
	
	private int functionsTwoMenu;
	
	public ApoBotLevel() {
		this.level = new ApoBotLevelLevel[10][10];
		for (int y = 0; y < this.level.length; y++) {
			for (int x = 0; x < this.level[0].length; x++) {
				this.level[y][x] = new ApoBotLevelLevel(ApoBotConstants.GROUND_EMPTY, 0);
			}
		}
		this.startPoint = new Point(0, 0);
		this.directionView = ApoBotConstants.DIR_SOUTH_DOWNRIGHT;
		
		this.commandsMenu = 16;
		this.functionsOneMenu = 8;
		this.functionsTwoMenu = 8;
		
		this.init();
	}
	
	public ApoBotLevel(int startX, int startY) {
		this.level = new ApoBotLevelLevel[startY][startX];
		for (int y = 0; y < this.level.length; y++) {
			for (int x = 0; x < this.level[0].length; x++) {
				this.level[y][x] = new ApoBotLevelLevel(ApoBotConstants.GROUND_EMPTY, 0);
			}
		}
		this.startPoint = new Point(0, 0);
		this.directionView = ApoBotConstants.DIR_SOUTH_DOWNRIGHT;
		
		this.commandsMenu = 16;
		this.functionsOneMenu = 8;
		this.functionsTwoMenu = 8;
		
		this.init();
	}
	
	public ApoBotLevel(ApoBotLevelLevel[][] level, Point startPoint, int directionView, int[] menu) {
		this.level = level;
		this.startPoint = startPoint;
		this.directionView = directionView;
		
		this.commandsMenu = menu[0];
		this.functionsOneMenu = menu[1];
		this.functionsTwoMenu = menu[2];
		
		this.init();
	}

	/**
	 * restartet das Level
	 */
	public void init() {
		if (this.level != null) {
			for (int y = 0; y < this.level.length; y++) {
				for (int x = 0; x < this.level[0].length; x++) {
					this.level[y][x].init();
				}
			}
		}
	}
	
	public int getCommandsMenu() {
		return this.commandsMenu;
	}

	public void setCommandsMenu(int commandsMenu) {
		this.commandsMenu = commandsMenu;
	}

	public int getFunctionsOneMenu() {
		return this.functionsOneMenu;
	}

	public void setFunctionsOneMenu(int functionsOneMenu) {
		this.functionsOneMenu = functionsOneMenu;
	}

	public int getFunctionsTwoMenu() {
		return this.functionsTwoMenu;
	}

	public void setFunctionsTwoMenu(int functionsTwoMenu) {
		this.functionsTwoMenu = functionsTwoMenu;
	}

	public ApoBotLevelLevel[][] getLevel() {
		return this.level;
	}

	public void setLevel(ApoBotLevelLevel[][] level) {
		this.level = level;
	}

	public Point getStartPoint() {
		return this.startPoint;
	}

	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	public int getDirectionView() {
		return this.directionView;
	}

	public void setDirectionView(int directionView) {
		this.directionView = directionView;
	}
	
}
