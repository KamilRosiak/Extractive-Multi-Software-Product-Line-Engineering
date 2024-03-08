package apoMario.game.panels;

import apoMario.game.ApoMarioReplay;

/**
 * SimulationHelp<br />
 * HilfsKlassefür die Simulation<br />
 * @author Dirk Aporius
 */
public class ApoMarioSimulationHelp {

	private int width;
	private int difficulty;
	private long random;
	
	private String nameOne;
	private int pointsOne;
	
	private String nameTwo;
	private int pointsTwo;
	
	private boolean bFinish;
	
	private ApoMarioReplay replay;
	
	public ApoMarioSimulationHelp(int width, int difficulty, long random, String nameOne, int pointsOne, boolean bFinish, ApoMarioReplay replay) {
		this.width = width;
		this.difficulty = difficulty;
		this.random = random;
		this.nameOne = nameOne;
		this.pointsOne = pointsOne;
		this.bFinish = bFinish;
		this.replay = new ApoMarioReplay();
		this.replay = replay;
		this.nameTwo = null;
	}
	
	public void setPlayerTwo(String nameTwo, int pointsTwo) {
		this.nameTwo = nameTwo;
		this.pointsTwo = pointsTwo;
	}

	public ApoMarioReplay getReplay() {
		return this.replay;
	}

	public boolean isInFinish() {
		return this.bFinish;
	}

	public void setInFinish(boolean bFinish) {
		this.bFinish = bFinish;
	}

	public int getWidth() {
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getDifficulty() {
		return this.difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public long getRandom() {
		return this.random;
	}

	public void setRandom(long random) {
		this.random = random;
	}

	public String getNameOne() {
		return this.nameOne;
	}

	public void setNameOne(String nameOne) {
		this.nameOne = nameOne;
	}

	public int getPointsOne() {
		return this.pointsOne;
	}

	public void setPointsOne(int pointsOne) {
		this.pointsOne = pointsOne;
	}

	public String getNameTwo() {
		return this.nameTwo;
	}

	public void setNameTwo(String nameTwo) {
		this.nameTwo = nameTwo;
	}

	public int getPointsTwo() {
		return this.pointsTwo;
	}

	public void setPointsTwo(int pointsTwo) {
		this.pointsTwo = pointsTwo;
	}
	
}
