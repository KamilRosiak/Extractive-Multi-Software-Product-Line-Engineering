package apoMario.ai;

/**
 * abstrakte Klasse von der alle KI's erben muessen
 * @author Dirk Aporius
 *
 */
public abstract class ApoMarioAI {

	/**
	 * gibt den Namen des Teams zurück</br>
	 * return the teamname</br>
	 * @return gibt den Namen des Teams zurück, return the teamname
	 */
	public abstract String getTeamName();
	
	/**
	 * gibt den Namen des Autors zurück</br>
	 * return the name of the author</br>
	 * @return gibt den Namen des Autos zurück, return the name of the author
	 */
	public abstract String getAuthor();
	
	public void load(String path) {
	}
	
	public void save(String path) {
	}
	
	/**
	 * wichtigste Methode: wird bei jedem Schritt einmal aufgerufen</br>
	 * in dieser Methode werden alle wichtigen Objekte für die Berechnungen übergeben</br>
	 * @param player : Das Spielerobjekt
	 * @param level : Das Levelobjekt, welches das Level enthält und alle Gegner und Goodies
	 */
	public abstract void think(final ApoMarioAIPlayer player, final ApoMarioAILevel level);
}
