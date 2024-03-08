package org.apogames.help;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.apogames.ApoConstants;

/**
 * Klasse, um eine Class Datei im laufenden Betrieb zu laden
 * @author Dirk Aporius
 *
 */
public class ApoClassLoader {
	private String path;
	private String classname;

	public ApoClassLoader(String path, String classname) {
		this.setPath( path );
		this.setClassname( classname );
	}

	/**
	 * gibt den Klassennamen zurück
	 * @return gibt den Klassennamen zurück
	 */
	public String getClassname() {
		return this.classname;
	}

	/**
	 * setzt den Klassennamen auf den übergebenen Wert
	 * @param classname
	 */
	public void setClassname(String classname) {
		this.classname = classname;
	}

	/**
	 * gibt den Pfad zur Klasse zurück
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * setzt den Pfad, welcher zur Klasse führt, auf den
	 * übergebenen Wert
	 * @param path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * liefert ein Object der Klasse zurück, welches nur noch in die
	 * entsprechende Klasse gecastet werden muss
	 * @return liefert ein Object der Klasse zurück, welches nur noch in die
	 * entsprechende Klasse gecastet werden muss
	 */
	public Object getMyClass() { 
		try {
			URL url;
			if ( !ApoConstants.B_APPLET )
				url = new File( this.getPath() ).toURI().toURL();
			else
				url = new URL( this.getPath() );
			URLClassLoader cl = new URLClassLoader( new URL[] { url } ); 
			Class<?> c;
			try {
				//System.out.println( this.getPath() + "" + this.getClassname() );
				c = cl.loadClass( this.getClassname() );
			    try {
					return c.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} 

		return null;
	  } 

}