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
	 * gibt die Farbe des T-Shirts zur�ck,</br>
	 * return the shirtcolor</br>
	 * @return gibt die Farbe des T-Shirts zur�ck,</br>
	 * return the shirtcolor
	 */
	public Color getShirtColor() {
		return null;
	}
	
	/**
	 * gibt die Farbe der Hosen zur�ck,</br>
	 * return the trousercolor</br>
	 * @return gibt die Farbe der Hosen zur�ck, return the trousercolor
	 */
	public Color getTrouserColor() {
		return null;
	}
	
	/**
	 * kann �berschrieben werden, wenn man seinen Spielern eigene Namen geben m�chte</br>
	 * returns an array with the playernames</br>
	 * @return eigene Namen f�r die Spieler, array with the playernames
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
	 * kann �berschrieben werden mit Spr�chen die man gerne sagen m�chte </br>
	 * Dabei ist zu beachten, dass die L�nge des Array ApoSoccerConstants.MAX_SPEECH_STRINGARRAY_LENGTH
	 * nicht �berschreiten darf</br>
	 * return an array with all your speechs
	 * @return eigene Spr�che f�r die Spieler, own speechs for your team
	 */
	public String[] getSpeech() {
		String[] speech = new String[1];
		speech[0] = "Apo Soccer rulz";
		return speech;
	}
	
	/**
	 * kann �berschrieben werden, wenn man angeben m�chte, welches Geschlecht seine Spieler haben sollen
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
	 * kann �berschrieben werden, wenn man seinen Spielern eine andere Frisur geben m�chte</br>
	 * returns an array which player should have which hair</br>
	 * allow constants are everything which starts with ApoSoccerConstants.HAIR_ ... 
	 * @return eigene Frisuren f�r die Spieler, array with the hair for your players
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
	 * kann �berschrieben werden</br>
	 * soll ein String mit dem Bildnamen des Wappens zur�ckgeben,</br> 
	 * returns an string with your imagename
	 * @return gibt einen String mit dem Bildnamen zur�ck, NULL falls kein Wappen geladen werden soll,</br>
	 * NULL for no emblem, else an string with the imagename in your package
	 */
	public String getEmblem() {
		return null;
	}
	
	/**
	 * wird nach jedem Tor und Seitenwechsel ausgerufen</br>
	 * und erm�glicht es dem Spieler seine Abwehrspieler</br>
	 * in der eigenen Seite so zu positionieren wie er m�chte</br>
	 * is called after every goal and the half time and allows you to set your defenderplayers at a specific position
	 * @param team = eigenes Team, your own team
	 */
	public void setPosition(ApoSoccerTeamSet[] team) {
		for (int i = 0; i < team.length; i++) {
			team[i].init();
		}
	}
	
	/**
	 * kann �berschrieben werden</br>
	 * soll ein String mit dem Bildnamen f�r den m�nnlichen Spieler zur�ckgeben,</br> 
	 * returns an string with your imagename for your player (man)
	 * @return gibt einen String mit dem Bildnamen zur�ck, NULL falls kein Wappen geladen werden soll,</br>
	 * NULL for no playerimage, else an string with the imagename in your package for the player
	 */
	public String getPlayerManImage() {
		return null;
	}
	
	/**
	 * kann �berschrieben werden</br>
	 * soll ein String mit dem Bildnamen f�r den weiblichen Spieler zur�ckgeben,</br> 
	 * returns an string with your imagename for your player (female)
	 * @return gibt einen String mit dem Bildnamen zur�ck, NULL falls kein Wappen geladen werden soll,</br>
	 * NULL for no playerimage, else an string with the imagename in your package for the player
	 */
	public String getPlayerFemaleImage() {
		return null;
	}
}
