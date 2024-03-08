package apoSoccer;

import java.util.Hashtable;
import java.net.*;
import java.io.*;

import apoSoccer.entityForAI.ApoSoccerAI;

public class ApoSoccerClassLoaderURL extends ClassLoader {

	private Hashtable<String, Class<ApoSoccerAI>> cache;
	private URL u;
	private String name;

	public ApoSoccerClassLoaderURL(URL u, String name) {
		this.u = u;
		this.name = name;
		cache = new Hashtable<String, Class<ApoSoccerAI>>();
	}
	
	public ApoSoccerAI getAI() {
		ApoSoccerAI ai = null;
		Class<ApoSoccerAI> c;
		try {
			c = loadClass( this.name, true );
			Object o;
			try {
				o = c.newInstance();
				ai = (ApoSoccerAI)o;
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ai;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public synchronized Class<ApoSoccerAI> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {
		// first look for the class in the cache
		Class c = (Class) cache.get(name);

		// If it's not in the cache, then look
		// in the system classes
		if (c == null) {
			try {
				c = findSystemClass(name);
			} catch (ClassNotFoundException e) {

			}
		}

		// if the class still hasn't been found,
		// load it from the network
		if (c == null) {
			byte b[] = loadClassData(name);
			c = defineClass(b, 0, b.length);
			cache.put(name, c);
		}
		if (resolve) {
			resolveClass(c);
		}

		return c;

	} // end loadClass

	private byte[] loadClassData(String name) throws ClassNotFoundException {
		byte[] b;
		InputStream theClass = null;
		int bfr = 128;

		try {
			URL classURL = new URL(u, name + ".class");
			URLConnection uc = classURL.openConnection();
			uc.setAllowUserInteraction(false);

			// I don't know why, but uc.getInputStream absolutely
			// has to come before uc.getContentLength or you'll
			// get a nasty NullPointerException
			try {
				theClass = uc.getInputStream();
			} catch (NullPointerException e) {
				System.err.println(e);
				throw new ClassNotFoundException(name + " input stream problem");
			}
			int cl = uc.getContentLength();

			// A lot of web servers don't send content-lengths
			// for .class files
			if (cl == -1) {
				b = new byte[bfr * 16];
			} else {
				b = new byte[cl];
			}

			int bytesread = 0;
			int offset = 0;

			while (bytesread >= 0) {
				bytesread = theClass.read(b, offset, bfr);
				if (bytesread == -1)
					break;
				offset += bytesread;
				if (cl == -1 && offset == b.length) { // grow the array
					byte temp[] = new byte[offset * 2];
					System.arraycopy(b, 0, temp, 0, offset);
					b = temp;
				} else if (offset > b.length) {
					throw new ClassNotFoundException(name
							+ " error reading data into the array");
				}
			}

			if (offset < b.length) { // shrink the array
				byte temp[] = new byte[offset];
				System.arraycopy(b, 0, temp, 0, offset);
				b = temp;
			}

			// Make sure all the bytes were received
			if (cl != -1 && offset != cl) {
				throw new ClassNotFoundException("Only " + offset
						+ " bytes received for " + name + "\n Expected " + cl
						+ " bytes");
			}
		} // end try
		catch (Exception e) {
			throw new ClassNotFoundException(name + " " + e);
		} finally {
			try {
				if (theClass != null)
					theClass.close();
			} catch (IOException e) {
			}

		}

		return b;

	} // end loadClassData

} // end URLClassLoader
