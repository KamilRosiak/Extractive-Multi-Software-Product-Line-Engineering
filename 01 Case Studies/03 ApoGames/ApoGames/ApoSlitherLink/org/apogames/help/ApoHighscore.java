package org.apogames.help;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apogames.ApoConstants;

public class ApoHighscore {

	private ArrayList<String> names;
	private ArrayList<String> levels;
	private ArrayList<String> solution;

	private static ApoHighscore singleInstance = new ApoHighscore();

	public static ApoHighscore getInstance() {
		if (null == singleInstance) {
			singleInstance = new ApoHighscore();
		}
		return singleInstance;
	}

	private ApoHighscore() {
		this.names = new ArrayList<String>();
		this.levels = new ArrayList<String>();
		this.solution = new ArrayList<String>();
		clear();
	}

	public ArrayList<String> getNames() {
		return this.names;
	}

	public ArrayList<String> getLevels() {
		return this.levels;
	}

	public ArrayList<String> getSolution() {
		return this.solution;
	}

	private void clear() {
		this.names.clear();
		this.levels.clear();
		this.solution.clear();
	}

	@SuppressWarnings("deprecation")
	public boolean save(String level, String solution, String name) {
		if (ApoConstants.B_ONLINE) {
			try {
				URL url;
				URLConnection urlConn;
				DataOutputStream printout;
				DataInputStream input;

				url = new URL(ApoConstants.HIGHSCORE_SAVEPHP);
				urlConn = url.openConnection();

				urlConn.setDoInput(true);
				urlConn.setDoOutput(true);

				urlConn.setUseCaches(false);
				urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

				printout = new DataOutputStream(urlConn.getOutputStream());

				String content = "level=" + URLEncoder.encode(level, "UTF-8")
						+ "&solution=" + URLEncoder.encode(solution, "UTF-8")
						+ "&name=" + URLEncoder.encode(name, "UTF-8");
				printout.writeBytes(content);
				printout.flush();
				printout.close();

				input = new DataInputStream(urlConn.getInputStream());
				@SuppressWarnings("unused")
				String str;
				while (null != ((str = input.readLine()))) {
				}
				input.close();
				return true;
			} catch (MalformedURLException me) {
				System.err.println("MalformedURLException: " + me);
				return false;
			} catch (IOException ioe) {
				System.err.println("IOException: " + ioe.getMessage());
				return false;
			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public boolean load() {
		clear();
		if (ApoConstants.B_ONLINE) {
			try {
				URL url;
				URLConnection urlConn;
				DataOutputStream printout;
				DataInputStream input;

				url = new URL(ApoConstants.HIGHSCORE_GETPHP);
				urlConn = url.openConnection();

				urlConn.setDoInput(true);
				urlConn.setDoOutput(true);

				urlConn.setUseCaches(false);
				urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

				printout = new DataOutputStream(urlConn.getOutputStream());

				String content = "Get=" + URLEncoder.encode("", "UTF-8");

				printout.writeBytes(content);
				printout.flush();
				printout.close();
				
				input = new DataInputStream(urlConn.getInputStream());
				String str;
				int i = 0;
				while (null != ((str = input.readLine()))) {
					if (i % 3 == 0) {
						this.levels.add(str);
					} else if (i % 3 == 1) {
						this.solution.add(str);
					} else {
						this.names.add(str);
					}
					i = i + 1;
				}
				input.close();
				return true;
			} catch (MalformedURLException me) {
				System.err.println("MalformedURLException: " + me);
				return false;
			} catch (IOException ioe) {
				System.err.println("IOException: " + ioe.getMessage());
				return false;
			}
		}
		return false;
	}
}