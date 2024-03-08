package apoMario.ai;

/**
 * abstrakte Klasse von der alle KI's erben muessen
 * @author Dirk Aporius
 *
 */
public abstract class ApoMarioAI {

	/**
	 * gibt den Namen des Teams zur�ck</br>
	 * return the teamname</br>
	 * @return gibt den Namen des Teams zur�ck, return the teamname
	 */
	public abstract String getTeamName();
	
	/**
	 * gibt den Namen des Autors zur�ck</br>
	 * return the name of the author</br>
	 * @return gibt den Namen des Autos zur�ck, return the name of the author
	 */
	public abstract String getAuthor();
	
	public void load(String path) {
	}
	
	public void save(String path) {
	}
	
	/**
	 * wichtigste Methode: wird bei jedem Schritt einmal aufgerufen</br>
	 * in dieser Methode werden alle wichtigen Objekte f�r die Berechnungen �bergeben</br>
	 * @param player : Das Spielerobjekt
	 * @param level : Das Levelobjekt, welches das Level enth�lt und alle Gegner und Goodies
	 */
	public abstract void think(final ApoMarioAIPlayer player, final ApoMarioAILevel level);
}
