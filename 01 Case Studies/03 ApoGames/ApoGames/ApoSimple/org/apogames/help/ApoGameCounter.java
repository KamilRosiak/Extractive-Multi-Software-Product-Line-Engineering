package org.apogames.help;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apogames.ApoConstants;

public class ApoGameCounter {

	private static ApoGameCounter singleInstance = new ApoGameCounter();

	public static ApoGameCounter getInstance() {
		if (null == singleInstance) {
			singleInstance = new ApoGameCounter();
		}
		return singleInstance;
	}
	
	@SuppressWarnings("deprecation")
	public boolean save(final String value) {
		if (ApoConstants.B_ONLINE) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						URL url;
						URLConnection urlConn;
						DataOutputStream printout;
						DataInputStream input;
		
						url = new URL("http://www.apo-games.de/apoSheeptastic/game_counter.php");
						urlConn = url.openConnection();
		
						urlConn.setDoInput(true);
						urlConn.setDoOutput(true);
		
						urlConn.setUseCaches(false);
						urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		
						printout = new DataOutputStream(urlConn.getOutputStream());
						
						String content = "value=" + URLEncoder.encode(String.valueOf(value), "UTF-8");
						printout.writeBytes(content);
						printout.flush();
						printout.close();
		
						input = new DataInputStream(urlConn.getInputStream());
						@SuppressWarnings("unused")
						String str;
						while (null != ((str = input.readLine()))) {
						}
						input.close();
					} catch (MalformedURLException me) {
						System.err.println("MalformedURLException: " + me);
					} catch (IOException ioe) {
						System.err.println("IOException: " + ioe.getMessage());
					}
				}
			});
			t.start();
		}
		return false;
	}
	
}
