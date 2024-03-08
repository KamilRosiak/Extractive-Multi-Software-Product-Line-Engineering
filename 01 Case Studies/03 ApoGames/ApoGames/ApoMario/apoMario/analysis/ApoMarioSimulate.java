package apoMario.analysis;

import apoMario.ApoMarioConstants;
import apoMario.game.ApoMarioPanel;

/**
 * Klasse, um ein Spiel zu simulieren
 * @author Dirk Aporius
 */
public class ApoMarioSimulate {

	public static boolean B_SIMULATE;
	/** Das Spielobjekt */
	private ApoMarioPanel game;
	/** boolean Variable, die angibt ob die SImulation läuft */
	private boolean bSimulate;
	
	private int width;
	
	private int difficulty;
	
	public ApoMarioSimulate(ApoMarioPanel game, int width, int difficulty) {
		this.game = game;
		this.width = width;
		this.difficulty = difficulty;
	}
	
	/**
	 * gibt die boolean Variable, ob die Simulation läuft, zurück
	 * @return TRUE, Simulation läuft, sonst FALSE
	 */
	public boolean isBSimulate() {
		return this.bSimulate;
	}

	/**
	 * setzt die boolean Variable, ob die Simulation läuft, auf den übergebenen Wert
	 * @param bSimulate : TRUE, Simulation läuft, sonst FALSE
	 */
	public void setBSimulate(boolean bSimulate) {
		this.bSimulate = bSimulate;
	}

	/**
	 * startet ein Spiel und lässt es bis zum Schluss durchlaufen
	 * @return immer TRUE
	 */
	public synchronized boolean startSimulate() {
		return this.startSimulate(true);
	}
	
	public synchronized boolean startSimulate(boolean bNewLevel) {
		this.setBSimulate(true);
		this.game.setBRepaint(false);
		this.game.setBThink(false);
		if (bNewLevel) {
			this.game.getLevel().makeLevel((int)System.nanoTime(), false, true, this.width, this.difficulty);
		} else {
		}
		this.game.setGame(false);
		
		this.simulate();
		
		this.setBSimulate(false);
		this.game.setBRepaint(true);
		this.game.setBThink(true);
		return true;
	}
	
	/**
	 * simuliert das eigentliche Spiel
	 */
	private synchronized void simulate() {
		while (this.game.getLevel().isBAnalysis()) {
			this.game.thinkLevelGame(ApoMarioConstants.WAIT_TIME_THINK);
		}
	}
}
