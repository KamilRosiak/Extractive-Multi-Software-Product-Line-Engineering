package apoIcejump.game;

public class ApoIcejumpSimulationHelp {

	private int winsPlayerOne;
	
	private int winsPlayerTwo;
	
	public ApoIcejumpSimulationHelp() {
		this.init();
	}
	
	public void init() {
		this.winsPlayerOne = 0;
		this.winsPlayerTwo = 0;
	}
	
	public int getResultCount() {
		return (this.winsPlayerOne + this.winsPlayerTwo);
	}
	
	public boolean noResults() {
		if ((this.winsPlayerOne <= 0) && (this.winsPlayerTwo <= 0)) {
			return true;
		}
		return false;
	}
	
	public void addWinPlayerOne() {
		this.winsPlayerOne += 1;
	}
	
	public void addWinPlayerTwo() {
		this.winsPlayerTwo += 1;
	}

	public int getWinsPlayerOne() {
		return this.winsPlayerOne;
	}

	public int getWinsPlayerTwo() {
		return this.winsPlayerTwo;
	}
	
}
