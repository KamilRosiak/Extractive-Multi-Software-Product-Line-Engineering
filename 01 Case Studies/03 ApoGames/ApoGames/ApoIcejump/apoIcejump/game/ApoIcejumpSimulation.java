package apoIcejump.game;

import java.util.Random;

import apoIcejump.ApoIcejumpConstants;

public class ApoIcejumpSimulation implements Runnable {

	public static final int PLAYER_ONE = 0;
	public static final int PLAYER_TWO = 1;
	
	private ApoIcejumpPanel game;
	
	private int birdTime;
	
	private int gameTime;
	
	private int goodieTime;
	
	private int count;
	
	private boolean bSimulate;
	
	private boolean bBreak;
	
	private ApoIcejumpSimulationHelp help;
	
	private long lastTime;
	
	private Random random;
	
	public ApoIcejumpSimulation(ApoIcejumpPanel game) {
		this.game = game;
		if (this.help == null) {
			this.help = new ApoIcejumpSimulationHelp();
		}
	}
	
	public ApoIcejumpPanel getGame() {
		return this.game;
	}

	public boolean isBSimulate() {
		return this.bSimulate;
	}

	public void deleteResults() {
		this.help.init();
	}
	
	public ApoIcejumpSimulationHelp getResults() {
		return this.help;
	}
	
	public void breakSimulation() {
		this.bBreak = true;
	}
	
	public void simulate(int count) {
		if (this.bSimulate) {
			return;
		}
		this.help.init();
		this.bSimulate = true;
		this.bBreak = false;
		this.count = count;
		Thread t = new Thread(this);
		t.start();
	}
	
	public void run() {
		this.makeSimulate(this.count);
	}
	
	public void makeSimulate(int count) {
		int think = -1;
		boolean bStop = false;
		boolean bGameStart = false;
		for (int i = 0; i < count; i++) {
			this.init();
			
			think = -1;
			while (think <= -1) {
				think = think(ApoIcejumpConstants.WAIT_TIME_THINK);
			}
			if (think == ApoIcejumpSimulation.PLAYER_ONE) {
				this.help.addWinPlayerTwo();
				if (bStop) {
					if (this.game.getPlayers()[think].getName().startsWith("Hard")) {
						bGameStart = true;
						break;
					}
				}
			} else if (think == ApoIcejumpSimulation.PLAYER_TWO) {
				this.help.addWinPlayerOne();
				if (bStop) {
					if (this.game.getPlayers()[think].getName().startsWith("Hard")) {
						bGameStart = true;
						break;
					}
				}
			}
			if (this.bBreak) {
				break;
			}
		}
		this.bSimulate = false;
		if (bGameStart) {
			this.game.setGame(this.lastTime);
		}
	}
	
	private void init() {
		this.lastTime = System.nanoTime();
		this.random = new Random(this.lastTime);
		
		this.game.makeBlocks(this.lastTime);

		this.gameTime = 0;
		this.goodieTime = (int)(this.random.nextInt(10000) + 3000);
		this.birdTime = (int)(this.random.nextInt(9500) + 8000);
		this.game.getBirds().clear();
		for (int i = 0; i < this.game.getPlayers().length; i++) {
			this.game.getPlayers()[i].init();
		}
		this.game.makeNewParticle();
	}
	
	private int think(int delta) {
		this.gameTime += delta;
		this.getGame().thinkPlayers(delta, true);
		for (int i = 0; i < this.getGame().getPlayers().length; i++) {
			if (!this.getGame().getPlayers()[i].isBVisible()) {
				return i;
			}
		}
		this.goodieTime -= delta;
		if (this.goodieTime <= 0) {
			this.goodieTime = (int)(this.random.nextInt(10000) + 3000);
			this.getGame().makeGoodie();
		}
		this.getGame().thinkGoodies(delta);
		
		this.birdTime -= delta;
		if (this.birdTime <= 0) {
			this.birdTime = (int)(this.random.nextInt(8500) + 7000);
			this.getGame().makeBird(1, false);
		}
		this.getGame().thinkBird(delta);
		
		this.getGame().thinkBlocks(delta);
		this.getGame().thinkParticles(delta);
		
		return -1;
	}
	
}
