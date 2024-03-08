package apoSoccer.game;

import apoSoccer.ApoSoccerConstants;

/**
 * Klasse, die sich um das SPielen ohne repainten kümmert
 * @author Dirk Aporius
 *
 */
public class ApoSoccerSimulate {

	public static boolean B_SIMULATE;
	/** Das SPielobjekt */
	private ApoSoccerGame game;
	/** boolean Variable, die angibt ob die SImulation läuft */
	private boolean bSimulate;
	
	public ApoSoccerSimulate(ApoSoccerGame game) {
		this.game = game;
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
		ApoSoccerSimulate.B_SIMULATE = bSimulate;
	}

	/**
	 * startet ein Spiel und lässt es bis zum Schluss durchlaufen
	 * @return immer TRUE
	 */
	public boolean startSimulate() {
		this.game.setBRepaint(false);
		this.game.setBThink(false);
		this.setBSimulate(true);
		this.game.startGame();
		
		this.simulate();
		
		this.setBSimulate(false);
		this.game.setRestartThreadValues();
		this.game.setBRepaint(true);
		this.game.setBThink(true);
		return true;
	}
	
	private void simulate() {
		while (!game.isBAnalysis()) {
			this.game.think(ApoSoccerConstants.WAIT_TIME_THINK);
		}
	}
	
}
