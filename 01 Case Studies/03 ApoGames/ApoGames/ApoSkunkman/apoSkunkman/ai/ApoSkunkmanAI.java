package apoSkunkman.ai;

/**
 * abstrakte Klasse, von der alle KI's erben m�ssen<br />
 * @author Dirk Aporius
 *
 */
public abstract class ApoSkunkmanAI {
	
	/**
	 * gibt den Namen des Teams zur�ck</br>
	 * returns the name of the player</br>
	 * @return gibt den Namen des Teams zur�ck, returns the name of the player
	 */
	public abstract String getPlayerName();
	
	/**
	 * gibt den Namen des Authors zur�ck</br>
	 * returns the name of the author</br>
	 * @return gibt den Namen des Authors zur�ck, returns the name of the author
	 */
	public abstract String getAuthor();
	
	/**
	 * eigentliche Hauptmethode zum Nachdenken des Spielers<br />
	 * wenn ein Spieler steht wird sie alle ApoSkunkmanAIConstants.WAIT_TIME_THINK Millisekunden aufgerufen<br />
	 * wenn ein Spieler sich bewegt, wird die Methode nicht aufgerufen bis er wieder zum Stehen kommt<br />
	 * Die Methode �bergibt das Level mit den Angaben zur Zeit, dem Levelarray, den Gegner usw.<br/>
	 * und dem eigentlichen Spieler selbst<br /> 
	 * @param level : Das Levelobjekt
	 * @param player : Der eigene Spieler
	 */
	public abstract void think(ApoSkunkmanAILevel level, ApoSkunkmanAIPlayer player);
	
	/**
	 * wird vor dem Start eines Levels aufgerufen und dient zur Initialisierung der Variablen der KI und <br />
	 * zum Laden einer eigenen Datei z.B. f�r ein neuronales Netz<br />
	 * Der �bergebene String ist der Pfad, wo die KI liegt<br />
	 * @param path : Pfad, wo die KI drin liegt
	 */
	public void load(String path) {
	}

	/**
	 * wird aufgerufen, wenn das Spiel beendet wurde und ein Sieger feststeht<br />
	 * Au�erdem dient es zum Speichern einer eigenen Datei<br />
	 * Der �bergebene String ist der Pfad, wo die KI liegt<br />
	 * @param path : Pfad, wo die KI drin liegt
	 */
	public void save(String path) {
	}
	
	/**
	 * kann �berschrieben werden, wenn man selber ein Image f�r seinen Spieler laden will<br />
	 * Dabei ist zu beachten das es aus 4 Richtungen mit jeweils 4 Frames besteht<br />
	 * Ein Frame ist 32 x 48 Pixel gro�, was zusammen ein Bild von 128 x 192 PX ergibt<br />
	 * Richtungen sind: zuerst runter, dann links, dann rechts dann hoch<br />
	 * @return gibt den Pfad zum eigenen Bild wieder, wenn NULL oder BIld nicht gefunden dann kommt das Standardbild
	 */
	public String getImage() {
		return null;
	}
}
