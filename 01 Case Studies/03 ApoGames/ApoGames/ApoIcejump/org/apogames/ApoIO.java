package org.apogames;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * abstrakte Klasse für das Einlesen und Speichern von Daten
 * @author Dirk Aporius
 */
public abstract class ApoIO{

	private int maxLevel;

	private int currentLevel;
	
	private String levelName;
	
	private String fileEnding;
	
	/**
	 * Konstruktor
	 */
	public ApoIO() {
		super();
		
		this.setCurrentLevel( -1 );
		this.setMaxLevel( 0 );
	}
	
	/**
	 * Konstruktor
	 * @param levelName = String mit dem Levelnamen
	 * @param fileEnding = String mit der Dateiendung
	 */
	public ApoIO( String levelName, String fileEnding ) {
		super();
		
		this.setCurrentLevel( -1 );
		this.setMaxLevel( 0 );
		
		this.setLevelName( levelName );
		this.setFileEnding( fileEnding );
	}

	/**
	 * gibt den Levelnamen zurück
	 * @return gibt den Levelnamen zurück
	 */
	public String getLevelName() {
		return this.levelName;
	}

	/**
	 * setzt den Levelnamen auf den übergebenen String
	 * @param levelName = neuer Levelname
	 */
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	/**
	 * gibt einen String mit der Dateiendung zurück
	 * @return gibt einen String mit der Dateiendung zurück
	 */
	public String getFileEnding() {
		return this.fileEnding;
	}

	/**
	 * setzt den String mit der Dateiendung auf den übergebenen Wert
	 * @param fileEnding = neue Dateiendung
	 */
	public void setFileEnding(String fileEnding) {
		this.fileEnding = fileEnding;
	}

	/**
	 * gibt das aktuelle Level zurück
	 * @return gibt das aktuelle Level zurück
	 */
	public int getCurrentLevel() {
		return this.currentLevel;
	}

	/**
	 * setzt das aktuelle Level auf den übergebenen Wert
	 * @param currentLevel
	 */
	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}
	
	/**
	 * gibt die maximale Anzahl an Levels zurück
	 * @return gibt die maximale Anzahl an Levels zurück
	 */
	public int getMaxLevel() {
		return this.maxLevel;
	}

	/**
	 * setzt die maximale Anzahl an Levels auf den übergebenen Wert
	 * @param maxLevel
	 */
	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}

	/**
	 * testet, ob es ein nächstes Level gibt
	 * @return TRUE falls es ein nächstes Level gibt sonst FALSE
	 */
	public boolean hasNextLevel()
	{
		if ( ( this.currentLevel + 1 <= 0 ) || ( this.currentLevel + 1 >= this.maxLevel ) )
			return false;
		else
			return true;
	}
	
	/**
	 * testet, ob es ein nächstes Level gibt
	 * @param bURL = TRUE, falls Level aus dem Inet holen
	 * FALSE, falls Datei von der Festplatte holen
	 * @return TRUE falls es ein nächstes Level gibt sonst FALSE
	 */
	public boolean hasNextLevel( boolean bURL ) {
		try {
			DataInputStream data;
			if ( !bURL ) {
				FileInputStream file		= new FileInputStream( this.getLevelName() + Integer.toString( this.getCurrentLevel() ) + this.getFileEnding() );
				BufferedInputStream buff	= new BufferedInputStream( file );
				data						= new DataInputStream( buff );
			} else {
				URL connection				= new URL( this.getLevelName() + Integer.toString( this.getCurrentLevel() ) + this.getFileEnding() );
				data						= new DataInputStream( connection.openStream() );
			}
			data.close();
			return true;
		} catch ( IOException e ) {
			return false;
		}
	}
	
	/**
	 * schreibt eine Datei in das übergebene String Filesystem
	 * @param fileName = wo wird hingespeichert
	 */
	public void writeLevel( String fileName ) {
		try	{
			DataOutputStream data = null;
			
			if (!ApoConstants.B_APPLET) {
				FileOutputStream file		= new FileOutputStream( fileName );
				BufferedOutputStream buff	= new BufferedOutputStream( file );
				data						= new DataOutputStream(buff);
			} else {
				URL connectionURL = new URL(fileName);
				HttpURLConnection connection = (HttpURLConnection)connectionURL.openConnection(); 
				connection.setDoOutput(true);
				data						= new DataOutputStream(connection.getOutputStream());
			}
			
			if (data != null) {
				this.writeLevel(data);
				data.close();
			}
			
		} catch ( IOException e ) {
			System.out.println("Error: "+e);
		}
	}
	
	/**
	 * abstrakte Methode die genutzt werden kann um Daten zu schreiben
	 * @param data = das DataOutputStreamObjekt mithilfe dessen man in die Datei schreiben kann
	 * @return TRUE falls alles glatt lief sonst FALSE
	 * @throws IOException = Exception die geworfen wird, falls etwas beim Schreiben schief gegangen ist
	 */
	public abstract boolean writeLevel( DataOutputStream data ) throws IOException;
	
	/**
	 * liest eine Datei ein
	 */
	public boolean readLevel( String fileName ) {
		return this.readLevel( ApoConstants.B_APPLET, fileName );
	}
	
	/**
	 * liest eine Datei ein
	 * @param bURL: TRUE = Level aus dem Internet laden, sonst von der Festplatte
	 */
	public boolean readLevel( boolean bURL, String fileName ) {
		return this.readLevel( bURL, fileName, false );
	}
		
	/**
	 * liest eine Datei ein
	 * @param bURL: TRUE = Level aus dem Internet laden, sonst von der Festplatte
	 */
	public boolean readLevel( boolean bURL, String fileName, boolean bAll ) {
		this.maxLevel = 0;
		this.currentLevel = 0;
		try {
			DataInputStream data;
			if ( !bURL ) {
				FileInputStream file		= new FileInputStream( fileName );
				BufferedInputStream buff	= new BufferedInputStream( file );
				data		= new DataInputStream( buff );
				this.levelName = fileName.substring(fileName.lastIndexOf(File.separator) + 1, fileName.lastIndexOf("."));
			} else {
				URL connection				= new URL( fileName );
				data						= new DataInputStream( connection.openStream() );
				this.levelName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.lastIndexOf("."));
			}
			try	{
				this.readLevel( data );
			} catch ( EOFException e ) {
			}
			data.close();
		} catch ( IOException e ) {
			System.out.println("Error: "+e);
			return false;
		}
		return true;
	}
	
	/**
	 * abstrakte Methode, um eine Datei einzulesen
	 * @param data = das DataInputStreamObjekt mithilfe dessen man die Datei lesen kann
	 * @return TRUE, falls alles glatt gegangen ist, sonst FALSE
	 * @throws EOFException = Exception die geworfen wird falls das Ende der einzulesenden Datei erreicht ist und man trotzdem noch was lesen möchte
	 * @throws IOException = Exception die geworfen wird, falls etwas beim Einlesen der Datei schiefgeht
	 */
	public abstract boolean readLevel( DataInputStream data ) throws EOFException, IOException;
}
