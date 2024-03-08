package apoSoccer.analysis;

public class ApoSoccerAnalysisTable {

	private int points;
	private String name;
	private int ownGoal;
	private int enemyGoal;
	private int games;
	private String lastFiveGames;
	
	public ApoSoccerAnalysisTable(String name) {
		this.setPoints(0);
		this.setName(name);
		this.setOwnGoal(0);
		this.setEnemyGoal(0);
		this.setGames(0);
		this.setLastFiveGames("");
	}

	public int getPoints() {
		return this.points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOwnGoal() {
		return this.ownGoal;
	}

	public void setOwnGoal(int ownGoal) {
		this.ownGoal = ownGoal;
	}

	public int getEnemyGoal() {
		return this.enemyGoal;
	}

	public void setEnemyGoal(int enemyGoal) {
		this.enemyGoal = enemyGoal;
	}

	public int getGames() {
		return this.games;
	}

	public void setGames(int games) {
		this.games = games;
	}

	public String getLastFiveGames() {
		return this.lastFiveGames;
	}

	public void setLastFiveGames(String lastFiveGames) {
		this.lastFiveGames = lastFiveGames;
	}
	
	public void addToLastFiveGame(String lastGame) {
		this.lastFiveGames = this.lastFiveGames + lastGame;
	}
	
	public int getWins() {
		int wins = 0;
		for (int i = 0; i < this.lastFiveGames.length(); i++) {
			if (this.lastFiveGames.substring(i, i+1).equals("s")) {
				wins++;
			}
		}
		return wins;
	}
	
	public int getTie() {
		int tie = 0;
		for (int i = 0; i < this.lastFiveGames.length(); i++) {
			if (this.lastFiveGames.substring(i, i+1).equals("u")) {
				tie++;
			}
		}
		return tie;
	}
	
	public int getLost() {
		int lost = 0;
		for (int i = 0; i < this.lastFiveGames.length(); i++) {
			if (this.lastFiveGames.substring(i, i+1).equals("n")) {
				lost++;
			}
		}
		return lost;
	}
	
	public ApoSoccerAnalysisTable getCopy() {
		ApoSoccerAnalysisTable table = new ApoSoccerAnalysisTable(this.getName());
		table.setEnemyGoal(this.getEnemyGoal());
		table.setOwnGoal(this.getOwnGoal());
		table.setPoints(this.getPoints());
		table.setLastFiveGames(this.getLastFiveGames());
		table.setGames(this.getGames());
		return table;
	}
	
	public void addScore(int ownScore, int enemyScore) {
		this.setGames(this.getGames() + 1);
		this.setOwnGoal(this.getOwnGoal() + ownScore);
		this.setEnemyGoal(this.getEnemyGoal() + enemyScore);
		if (ownScore > enemyScore) {
			this.setPoints(this.getPoints() + 3);
			this.addToLastFiveGame("s");
		} else if (ownScore == enemyScore) {
			this.setPoints(this.getPoints() + 1);
			this.addToLastFiveGame("u");
		} else if (ownScore < enemyScore) {
			this.setPoints(this.getPoints() + 0);
			this.addToLastFiveGame("n");
		}
	}

}
