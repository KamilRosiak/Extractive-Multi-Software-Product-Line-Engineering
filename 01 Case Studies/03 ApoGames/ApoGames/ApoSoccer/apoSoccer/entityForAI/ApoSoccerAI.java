package apoSoccer.entityForAI;

import java.awt.Color;
import java.util.ArrayList;

import apoSoccer.ApoSoccerConstants;

/**
 * Klasse von der alle KI's erben, </br>
 * class from which all AI's must extend</br>
 * @author Dirk Aporius
 *
 */
public abstract class ApoSoccerAI {
	
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
	
	/**
	 * wichtigste Methode: wird bei jedem Schritt einmal aufgerufen</br>
	 * in dieser Methode bekommt man alles was man braucht,</br>
	 * den Ball, das eigene Team und das gegnerische Team</br>
	 * jetzt soll man die eigenen Spieler zum laufen und schiessen veranlassen ;)</br>
	 * main-method: every tick it is called one time</br>
	 * in the method you can do everythink you need</br>
	 * you have your own team, the ball and the enemy team,</br>
	 * so you can shoot, run and everything else</br>
	 * @param ball : Der Spielball, the ball
	 * @param ownTeam : Das eigene Team, your own team
	 * @param enemies : Das gegnerische Team, the enemy team
	 */
	public abstract void think(ApoSoccerBall ball, ArrayList<ApoSoccerTeam> ownTeam, ArrayList<ApoSoccerEnemy> enemies);
	
	/**
	 * gibt die Farbe des T-Shirts zurück,</br>
	 * return the shirtcolor</br>
	 * @return gibt die Farbe des T-Shirts zurück,</br>
	 * return the shirtcolor
	 */
	public Color getShirtColor() {
		return null;
	}
	
	/**
	 * gibt die Farbe der Hosen zurück,</br>
	 * return the trousercolor</br>
	 * @return gibt die Farbe der Hosen zurück, return the trousercolor
	 */
	public Color getTrouserColor() {
		return null;
	}
	
	/**
	 * kann überschrieben werden, wenn man seinen Spielern eigene Namen geben möchte</br>
	 * returns an array with the playernames</br>
	 * @return eigene Namen für die Spieler, array with the playernames
	 */
	public String[] getNames() {
		String[] names = new String[ApoSoccerConstants.PLAYER_AMOUNT];
		names[ApoSoccerConstants.GOALKEEPER] = "Goalkeeper";
		names[ApoSoccerConstants.FORWARD] = "Forward";
		names[ApoSoccerConstants.DEFENDER_ONE] = "Defender_One";
		names[ApoSoccerConstants.DEFENDER_TWO] = "Defender_Two";
		return names;
	}
	
	/**
	 * kann überschrieben werden mit Sprüchen die man gerne sagen möchte </br>
	 * Dabei ist zu beachten, dass die Länge des Array ApoSoccerConstants.MAX_SPEECH_STRINGARRAY_LENGTH
	 * nicht überschreiten darf</br>
	 * return an array with all your speechs
	 * @return eigene Sprüche für die Spieler, own speechs for your team
	 */
	public String[] getSpeech() {
		String[] speech = new String[1];
		speech[0] = "Apo Soccer rulz";
		return speech;
	}
	
	/**
	 * kann überschrieben werden, wenn man angeben möchte, welches Geschlecht seine Spieler haben sollen
	 * Standard ist: Zufall, ob Spieler Mann oder Frau</br>
	 * return an booleanarray which describes wheter the player is a man or a woman</br>
	 * @return ein Array mit boolean Werten, TRUE bedeutet Spieler ist eine Frau, sonst FALSE,</br>
	 * array with boolean values, TRUE -> player is a woman, FALSE -> player is a man
	 */
	public boolean[] getGender() {
		boolean[] gender = new boolean[ApoSoccerConstants.PLAYER_AMOUNT];
		gender[ApoSoccerConstants.GOALKEEPER] = Math.random()*100 < 50 ? true : false;
		gender[ApoSoccerConstants.FORWARD] = Math.random()*100 < 50 ? true : false;
		gender[ApoSoccerConstants.DEFENDER_ONE] = Math.random()*100 < 50 ? true : false;
		gender[ApoSoccerConstants.DEFENDER_TWO] = Math.random()*100 < 50 ? true : false;
		return gender;
	}
	
	/**
	 * kann überschrieben werden, wenn man seinen Spielern eine andere Frisur geben möchte</br>
	 * returns an array which player should have which hair</br>
	 * allow constants are everything which starts with ApoSoccerConstants.HAIR_ ... 
	 * @return eigene Frisuren für die Spieler, array with the hair for your players
	 */
	public int[] getHair() {
		int[] hair = new int[ApoSoccerConstants.PLAYER_AMOUNT];
		hair[ApoSoccerConstants.GOALKEEPER] = (int)(Math.random() * ApoSoccerConstants.APO_HAIR );
		hair[ApoSoccerConstants.FORWARD] = (int)(Math.random() * ApoSoccerConstants.APO_HAIR );
		hair[ApoSoccerConstants.DEFENDER_ONE] = (int)(Math.random() * ApoSoccerConstants.APO_HAIR );
		hair[ApoSoccerConstants.DEFENDER_TWO] = (int)(Math.random() * ApoSoccerConstants.APO_HAIR );
		return hair;
	}

	/**
	 * kann überschrieben werden</br>
	 * soll ein String mit dem Bildnamen des Wappens zurückgeben,</br> 
	 * returns an string with your imagename
	 * @return gibt einen String mit dem Bildnamen zurück, NULL falls kein Wappen geladen werden soll,</br>
	 * NULL for no emblem, else an string with the imagename in your package
	 */
	public String getEmblem() {
		return null;
	}
	
	/**
	 * wird nach jedem Tor und Seitenwechsel ausgerufen</br>
	 * und ermöglicht es dem Spieler seine Abwehrspieler</br>
	 * in der eigenen Seite so zu positionieren wie er möchte</br>
	 * is called after every goal and the half time and allows you to set your defenderplayers at a specific position
	 * @param team = eigenes Team, your own team
	 */
	public void setPosition(ApoSoccerTeamSet[] team) {
		for (int i = 0; i < team.length; i++) {
			team[i].init();
		}
	}
	
	/**
	 * kann überschrieben werden</br>
	 * soll ein String mit dem Bildnamen für den männlichen Spieler zurückgeben,</br> 
	 * returns an string with your imagename for your player (man)
	 * @return gibt einen String mit dem Bildnamen zurück, NULL falls kein Wappen geladen werden soll,</br>
	 * NULL for no playerimage, else an string with the imagename in your package for the player
	 */
	public String getPlayerManImage() {
		return null;
	}
	
	/**
	 * kann überschrieben werden</br>
	 * soll ein String mit dem Bildnamen für den weiblichen Spieler zurückgeben,</br> 
	 * returns an string with your imagename for your player (female)
	 * @return gibt einen String mit dem Bildnamen zurück, NULL falls kein Wappen geladen werden soll,</br>
	 * NULL for no playerimage, else an string with the imagename in your package for the player
	 */
	public String getPlayerFemaleImage() {
		return null;
	}
}
