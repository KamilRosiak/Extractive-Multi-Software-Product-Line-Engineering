package org.apogames.help;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Klasse, um Dateien oder ganze Ordner einfach zu kopieren
 * @author Dirk Aporius
 *
 */
public class ApoCopy {

	/**
	 * Konstruktor
	 */
	public ApoCopy() {
	}
	
	/**
	 * kopiert ein komplettes Verzeichnis (ohne Unterverzeichnisse)
	 * in einen übergebenen Bereich
	 * @param pSrcDirectory = String mit Pfadangabe zum Ordner, welcher kopiert werden 
	 * @param pCopyDirectory = String mit Pfadangabe zum Ordner, wohin kopiert werden soll
	 */
	public void copyDirectory( String pSrcDirectory, String pCopyDirectory ) {
		File srcDirectory = new File(pSrcDirectory);
		if (!srcDirectory.exists()) {
			return;
		}
		File copyDirectory = new File( pCopyDirectory );
		if (!copyDirectory.exists()) {
			copyDirectory.mkdirs();
		}
		File[] files = srcDirectory.listFiles();
		for ( int i = 0; i < files.length; i++ ) {
			if (!files[i].isDirectory()) {
				String fileName = files[i].getPath().substring(files[i].getPath().lastIndexOf(File.separator));
				if(!new File(pCopyDirectory + File.separator + fileName).exists()) {
					this.copyFile(files[i].getPath(), pCopyDirectory + File.separator + fileName);
				}
			}
		}
	}
	
	/**
	 * kopiert eine Datei in ein anderes übergebenes Verzeichnis
	 * @param pSrcFile = String mit Pfadangabe zur Datei, welche kopiert werden soll
	 * @param pCopyFile = String mit Pfadangabe zur Datei, wohin kopiert werden soll
	 */
	public void copyFile( String pSrcFile, String pCopyFile ) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		
		try {
			fis = new FileInputStream(pSrcFile);
			fos = new FileOutputStream(pCopyFile);
			
			byte[] buffer = new byte[0xFFFF];
			for ( int len; (len = fis.read(buffer)) != -1; ) {
				fos.write(buffer, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if ( fis != null ) {
				try { fis.close(); } catch (IOException e) { e.printStackTrace(); }
			}
			if ( fos != null ) {
				try { fos.close(); } catch (IOException e) { e.printStackTrace(); }
			}
		}
	}
	
}
