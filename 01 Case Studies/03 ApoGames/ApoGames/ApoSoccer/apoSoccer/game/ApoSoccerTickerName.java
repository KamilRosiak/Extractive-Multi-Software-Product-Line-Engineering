package apoSoccer.game;

/**
 * Hilfsklasse für den Ticker
 * @author Dirk Aporius
 *
 */
public class ApoSoccerTickerName {

	private String name;
	
	private int position;
	
	public ApoSoccerTickerName(String name, int position) {
		this.name = name;
		this.position = position;
	}

	public String getName() {
		return this.name;
	}

	public int getPosition() {
		return this.position;
	}
	
}
