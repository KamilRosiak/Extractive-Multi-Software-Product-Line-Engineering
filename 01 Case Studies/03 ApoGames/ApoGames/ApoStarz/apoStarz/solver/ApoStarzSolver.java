package apoStarz.solver;

import java.io.File;
import java.util.ArrayList;

import org.apogames.ApoConstants;

import apoStarz.ApoStarzConstants;
import apoStarz.game.ApoStarzGame;
import apoStarz.game.ApoStarzHighscore;
import apoStarz.game.ApoStarzIO;
import apoStarz.level.ApoStarzLevel;

public class ApoStarzSolver implements Runnable {

	public static final int MAX_DEPTH = 20;
	
	private ApoStarzIO io;
	
	private ArrayList<String> levels;
	
	private ApoStarzLevel level;
	
	private int curLevel = 0;
	
	private String curSolution = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
	
	private String curLevelString = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
	
	private int curHighscore = 100;
	
	protected String FILENAME;;
	
	private boolean bRun;
	
	public static void main(String[] args) {
		new ApoStarzSolver();
	}
	
	public ApoStarzSolver() {
		this.init();
	}
	
	public ApoStarzSolver(String fileName) {
		if (this.io == null) {
			this.io = new ApoStarzIO();
			this.curLevel = -1;
		}
		if (this.io.readLevel(fileName)) {
			this.curLevel = 0;
			this.levels = this.io.getAllLevels();
		}
		this.init();
		this.start();
	}

	public ApoStarzSolver(ArrayList<String> levels) {
		this.levels = new ArrayList<String>();
		for (int i = 0; i < levels.size(); i++) {
			this.levels.add(levels.get(i));
		}
		this.init();
		this.start();
	}
	
	public void getSolutionForLevel(String levelString) {
		this.levels = new ArrayList<String>();
		this.levels.add(levelString);
		this.start();
	}
	
	public String getCurSolution() {
		return this.curSolution;
	}
	
	public void init() {
		if (ApoStarzConstants.highscore == null) {
			ApoStarzConstants.highscore = new ApoStarzHighscore();
			if (ApoConstants.B_APPLET) {
				ApoStarzConstants.highscore.readLevel(ApoStarzGame.APPLET_LEVEL + "highscore.hig");				
			} else {
				ApoStarzConstants.highscore.readLevel(System.getProperty("user.dir") + File.separator+ "levels" + File.separator + "highscore.hig");
			}
		}
		if ((this.FILENAME == null) && (!ApoConstants.B_APPLET)) {
			 this.FILENAME = System.getProperty("user.dir") + File.separator+ "levels" + File.separator + "fire.ase";
		}
	}
	
	private void start() {
		this.curLevel = 0;
		this.level = new ApoStarzLevel(null);
		this.bRun = true;
		
		Thread t = new Thread(this);
		t.start();
	}

	public void run() {
		this.makeSolution();
	}
	
	private void makeSolution() {
		for (int i = 0; i < this.levels.size(); i++) {
			this.curSolution = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
			this.curLevel = i;
			this.curLevelString = this.levels.get(this.curLevel);
			this.curHighscore = ApoStarzConstants.highscore.getHighscore(this.curLevelString);
			if (this.curHighscore <= 0) {
				this.curHighscore = 1000;
			}
			//System.out.println(this.curLevelString);
			this.level.loadLevel(this.curLevelString);
			this.level.thinkSolver();
			this.getLevelSolution();	
			if (this.curSolution.indexOf("a") == -1) {
				ApoStarzConstants.highscore.addHighscore(this.curLevelString, this.curSolution.length(), this.curSolution, true);
			}
		}
		if (!ApoConstants.B_APPLET) {
			ApoStarzConstants.highscore.writeLevel(System.getProperty("user.dir") + File.separator + "levels" + File.separator + "highscore.hig");
		} else {
			ApoStarzConstants.highscore.writeLevel(ApoStarzGame.APPLET_LEVEL + "highscore.hig");			
		}
		this.bRun = false;
	}
	
	private void getLevelSolution() {
		this.getLevelSolution(this.level, "");
	}
	
	private void getLevelSolution(ApoStarzLevel level, String solution) {
		if (level.isBWin()) {
			//System.out.println("win with "+solution);
			if (this.curSolution.length() > solution.length()) {
				this.curSolution = solution;
				return;
			}
		} else if (level.isBLost()) {
			return;
		} else if ((solution.length() >= this.curHighscore) || (solution.length() > ApoStarzSolver.MAX_DEPTH) || (this.curSolution.length() < solution.length())) {
			//System.out.println("too long with "+solution);
			return;
		} else {
			ApoStarzLevel levelCopy = level.getCopy();
			//links
			levelCopy.setCurDirection(level.getCurDirection() + 1);
			levelCopy.thinkSolver();
			String leftSolution = solution + "l";
			this.getLevelSolution(levelCopy, leftSolution);
			
			levelCopy = level.getCopy();
			//links
			levelCopy.setCurDirection(level.getCurDirection() - 1);
			levelCopy.thinkSolver();
			String rightSolution = solution + "r";
			this.getLevelSolution(levelCopy, rightSolution);
		}
	}

	public boolean isBRun() {
		return this.bRun;
	}
	
	/*private void getLevelSolutionImp() {
		int step = 1;
		ApoStarzLevel levelCopy = level.getCopy();
		while (!levelCopy.isBWin()) {
			ArrayList<String> rotationString = new ArrayList<String>();
			for (int i = 0; i < step; i++) {
				rotationString
			}
		}
	}*/

}
