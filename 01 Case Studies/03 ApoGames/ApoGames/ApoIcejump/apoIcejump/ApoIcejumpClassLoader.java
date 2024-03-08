package apoIcejump;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import apoIcejump.ai.ApoIcejumpAI;

public class ApoIcejumpClassLoader extends ClassLoader {
	
	/** der Pfad, wo die Klasse liegt */
	private String root;
	/** der Name der KLasse */
	private String name;

	public ApoIcejumpClassLoader(String rootDir, String name) {
		if (rootDir == null)
			throw new IllegalArgumentException("Null root directory");
		this.root = rootDir;
		this.name = name;
	}
	
	/**
	 * gibt die zu ladene KI zurück
	 * @return gibt die KI zurück
	 */
	@SuppressWarnings("unchecked")
	public ApoIcejumpAI getAI() {
		ApoIcejumpAI ai = null;
		Class c;
		try {
			c = loadClass(this.name);
			Object o;
			try {
				o = c.newInstance();
				if (o instanceof ApoIcejumpAI) {
					ai = (ApoIcejumpAI)o;
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassCastException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return ai;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public Class loadClass(String name) throws ClassNotFoundException {
		Class c;
		try {
			c = findLoadedClass(name);

			if (c == null) {
				String filename = name.replace('.', File.separatorChar)	+ ".class";
				try {
					byte data[] = loadClassData(filename);
					c = defineClass(data, 0, data.length);
				} catch (IOException e) {
				} catch (NoClassDefFoundError e) {
				}
			}

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
		File f = new File(this.root, filename);
	
		int size = (int) f.length();
		byte buff[] = new byte[size];
		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);

		dis.readFully(buff);
		dis.close();
		
		return buff;
	}

}
