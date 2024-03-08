package apoSoccer.analysis;

public class ApoSoccerAnalysisGamesHelp {

	private String team;
	private String path;
	private String className;
	
	public ApoSoccerAnalysisGamesHelp(String path, String team, String className) {
		this.setTeam(team);
		this.setPath(path);
		this.setClassName(className);
	}

	public String getTeam() {
		return this.team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
}
