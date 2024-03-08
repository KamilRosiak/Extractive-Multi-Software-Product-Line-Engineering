package apoSoccer.analysis;

/**
 * Hilfsklasse für das Speichern der Ergebnisse und welche KI's mitgespielt haben
 * @author Dirk Aporius
 *
 */
public class ApoSoccerAnalysisHelp {

	private String homeAIPath;
	private String visitorAIPath;

	private String homeAIClass;
	private String visitorAIClass;
	
	private String homeAIClassClass;
	private String visitorAIClassClass;
	
	private int scoreHome;
	private int scoreVisitor;
	
	public ApoSoccerAnalysisHelp(String homeAIPath, String homeAIClass, String homeAIClassClass, String visitorAIPath, String visitorAIClass, String visitorAIClassClass) {
		this.setHomeAIPath(homeAIPath);
		this.setHomeAIClass(homeAIClass);
		this.setVisitorAIPath(visitorAIPath);
		this.setVisitorAIClass(visitorAIClass);
		this.setVisitorAIClassClass(visitorAIClassClass);
		this.setHomeAIClassClass(homeAIClassClass);
		this.setScoreHome(0);
		this.setScoreVisitor(0);
	}

	public String getHomeAIClassClass() {
		return this.homeAIClassClass;
	}

	public void setHomeAIClassClass(String homeAIClassClass) {
		this.homeAIClassClass = homeAIClassClass;
	}

	public String getVisitorAIClassClass() {
		return this.visitorAIClassClass;
	}

	public void setVisitorAIClassClass(String visitorAIClassClass) {
		this.visitorAIClassClass = visitorAIClassClass;
	}

	public String getHomeAIPath() {
		return this.homeAIPath;
	}

	public void setHomeAIPath(String homeAIPath) {
		this.homeAIPath = homeAIPath;
	}

	public String getVisitorAIPath() {
		return this.visitorAIPath;
	}

	public void setVisitorAIPath(String visitorAIPath) {
		this.visitorAIPath = visitorAIPath;
	}

	public String getHomeAIClass() {
		return this.homeAIClass;
	}

	public void setHomeAIClass(String homeAIClass) {
		this.homeAIClass = homeAIClass;
	}

	public String getVisitorAIClass() {
		return this.visitorAIClass;
	}

	public void setVisitorAIClass(String visitorAIClass) {
		this.visitorAIClass = visitorAIClass;
	}

	public int getScoreHome() {
		return this.scoreHome;
	}

	public void setScoreHome(int scoreHome) {
		this.scoreHome = scoreHome;
	}

	public int getScoreVisitor() {
		return this.scoreVisitor;
	}

	public void setScoreVisitor(int scoreVisitor) {
		this.scoreVisitor = scoreVisitor;
	}
	
	public String toString() {
		return "Das Spiel zwischen "+this.getHomeAIClass()+" und "+this.getVisitorAIClass()+" ging "+this.getScoreHome()+":"+this.getScoreVisitor()+" aus.";
	}
}
