package org.apogames;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
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
	
	/**
	 * Konstruktor
	 */
	public ApoIO() {
		super();
	}

	/**
	 * schreibt eine Datei in das übergebene String Filesystem
	 * @param fileName = wo wird hingespeichert
	 */
	public void writeLevel(String fileName) {
		try	{
			DataOutputStream data = null;
			
			if (!ApoConstants.B_APPLET) {
				FileOutputStream file = new FileOutputStream(fileName);
				BufferedOutputStream buff = new BufferedOutputStream(file);
				data = new DataOutputStream(buff);
			} else {
				URL connectionURL = new URL(fileName);
				HttpURLConnection connection = (HttpURLConnection)connectionURL.openConnection(); 
				connection.setDoOutput(true);
				data = new DataOutputStream(connection.getOutputStream());
			}
			
			if (data != null) {
				this.writeLevel(data);
				data.close();
			}
			
		} catch (IOException e) {
			System.out.println("Error: "+e);
		}
	}
	
	/**
	 * abstrakte Methode die genutzt werden kann um Daten zu schreiben
	 * @param data = das DataOutputStreamObjekt mithilfe dessen man in die Datei schreiben kann
	 * @return TRUE falls alles glatt lief sonst FALSE
	 * @throws IOException = Exception die geworfen wird, falls etwas beim Schreiben schief gegangen ist
	 */
	public abstract boolean writeLevel(DataOutputStream data) throws IOException;
	
	/**
	 * liest eine Datei ein
	 */
	public boolean readLevel(String fileName) {
		return this.readLevel(ApoConstants.B_APPLET, fileName);
	}

	/**
	 * liest eine Datei ein
	 * @param bURL: TRUE = Level aus dem Internet laden, sonst von der Festplatte
	 */
	public boolean readLevel(boolean bURL, String fileName) {
		try {
			DataInputStream data;
			if (!bURL) {
				FileInputStream file		= new FileInputStream(fileName);
				BufferedInputStream buff	= new BufferedInputStream(file);
				data = new DataInputStream(buff);
			} else {
				URL connection = new URL(fileName);
				data = new DataInputStream(connection.openStream());
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
	public abstract boolean readLevel(DataInputStream data) throws EOFException, IOException;
}
