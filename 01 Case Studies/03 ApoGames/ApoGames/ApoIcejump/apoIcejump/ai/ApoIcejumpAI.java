package apoIcejump.ai;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Klasse von der alle KI's erben<br />
 * class from which all ais have to extend
 * @author Dirk Aporius
 *
 */
public abstract class ApoIcejumpAI {

	/**
	 * Euer Namen / Your name
	 * @return euren Namen / your name
	 */
	public abstract String getAuthor();
	
	/**
	 * Namen der KI / ai name
	 * @return Namen der KI / ai name
	 */
	public abstract String getName();
	
	/**
	 * Kann überschrieben werden und wenn ihr dann kein Bild oder euren Spieler selber malt, dann hat euer Spieler diese Farbe<br />
	 * override it if you want to change the color of your player
	 * @return Farbe des Spielers
	 */
	public Color getColor() {
		return null;
	}
	
	/**
	 * wichtigste Methode die alle ApoIcejumpAIConstants.WAIT_TIME_THINK Millisekunden aufgerufen wird.<br />
	 * main method which is called every ApoIcejumpAIConstants.WAIT_TIME_THINK ms<br />
	 * @param level : Das Levelobjekt mit allen wichtigten Entitäten dabei (Spieler, Gegner, Eisblöcke, Goodies, Vögel usw.) / the levelobject with all entities (player, enemy, ice blocks, goodies, birds ...)
	 */
	public abstract void think(ApoIcejumpAILevel level);
	
	/**
	 * kann überschrieben werden, wenn die Sprüche, die der Spieler sagt, selbst ausgesucht werden sollen<br />
	 * override it to say your own ridicules
	 * @return Stringarray mit Sprüchen / string array with your ridicules
	 */
	public String[] getRidicule() {
		return null;
	}
	
	/**
	 * Pfad zum Bild, wenn euer Spieler aus einem Bild bestehen soll (Pfad relativ zur JAR-Datei bitte)<br />
	 * path to the image
	 * @return Pfad zum Bild / imagepath
	 */
	public String getImage() {
		return null;
	}
	
	/**
	 * falls auch TRUE gesetzt, wird die eigene renderPlayer Methode aufgerufen, wo ihr drin malen könnt<br />
	 * if true then your renderPlayer method will be called
	 * @return TRUE, selber malen, FALSE Programm malt euren Spieler / TRUE, paint yourself, FALSE the programm draws your player
	 */
	public boolean shouldOwnRender() {
		return false;
	}
	
	/**
	 * malt euren Spieler selber<br />
	 * methode to draw your player
	 * @param g : Graphics Objekt auch dem gemalt werden darf / graphics object
	 * @return 
	 */
	public boolean renderPlayer(Graphics2D g) {
		return false;
	}
}
