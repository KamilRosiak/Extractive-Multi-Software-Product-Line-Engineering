package apoSkunkman;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import apoSkunkman.ai.ApoSkunkmanAI;

/**
 * Klasse, um eine Class Datei im laufenden Betrieb zu laden
 * @author Dirk Aporius
 *
 */
public class ApoSkunkmanClassLoader extends ClassLoader {
	/** der Pfad, wo die Klasse liegt */
	private String root;
	/** der Name der KLasse */
	private String name;

	public ApoSkunkmanClassLoader(String rootDir, String name) {
		if (rootDir == null)
			throw new IllegalArgumentException("Null root directory");
		this.root = rootDir;
		this.name = name;
	}
	
	/**
	 * gibt die zu ladene KI zurück
	 * @return gibt die KI zurück
	 */
	public ApoSkunkmanAI getAI() throws Exception {
		ApoSkunkmanAI ai = null;
		Class<?> c;
		c = loadClass(this.name);
		Object o;
		try {
			o = c.newInstance();
			if (o instanceof ApoSkunkmanAI) {
				ai = (ApoSkunkmanAI)o;
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassCastException e) {
			e.printStackTrace();
		}
		return ai;
	}

	@SuppressWarnings({"deprecation"})
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		Class<?> c;
		try {
			c = findLoadedClass(name);

			if (c == null) {
				// Convert class name argument to filename
				// Convert package names into subdirectories
				String filename = name.replace('.', File.separatorChar)	+ ".class";
				try {
					byte data[] = loadClassData(filename);
					c = defineClass(data, 0, data.length);
				} catch (IOException e) {
					// tritt auf, wenn die Datei nicht gefunden wurde
				} catch (NoClassDefFoundError e) {
					// tritt auf, wenn die Datei gefunden wurde,
					// aber eine Klasse enthaelt, die nicht dem Dateinamen
					// enspricht (Windows findet Dateien
					// auch bei falscher Gross-Klein-Schreibung)
				}
			}

			// Since all support classes of loaded class use same class loader
			// must check subclass cache of classes for things like Object
			if (c == null) {
				try {
					c = findSystemClass(name);
				} catch (Exception e) {
				}
			}

			if (c == null) {
				throw new ClassNotFoundException(name);
			}

		} catch (Throwable ex) {
			throw new ClassNotFoundException(name);
		}

		return c;
	}

	private byte[] loadClassData(String filename) throws IOException {

		// Create a file object relative to directory provided
		File f = new File(this.root, filename);
		
		// Get size of class file
		int size = (int) f.length();
		
		// Reserve space to read
		byte buff[] = new byte[size];

		// Get stream to read from
		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);

		// Read in data
		dis.readFully(buff);

		// close stream
		dis.close();

		// return data
		return buff;
	}
}