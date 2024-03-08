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
	private ArrayList<Integer> time;
	private ArrayList<Integer> points;

	private static ApoHighscore singleInstance = new ApoHighscore();

	public static ApoHighscore getInstance() {
		if (null == singleInstance) {
			singleInstance = new ApoHighscore();
		}
		return singleInstance;
	}

	private ApoHighscore() {
		this.names = new ArrayList<String>();
		this.time = new ArrayList<Integer>();
		this.points = new ArrayList<Integer>();
		clear();
	}

	public ArrayList<String> getNames() {
		return this.names;
	}

	public ArrayList<Integer> getTime() {
		return this.time;
	}

	public ArrayList<Integer> getPoints() {
		return this.points;
	}

	private void clear() {
		this.names.clear();
		this.time.clear();
		this.points.clear();
	}

	@SuppressWarnings("deprecation")
	public boolean save(int points, int time, String name) {
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
				
				String content = "points=" + URLEncoder.encode(String.valueOf(points), "UTF-8")
						+ "&time=" + URLEncoder.encode(String.valueOf(time), "UTF-8")
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
						this.points.add(Integer.valueOf(str));
					} else if (i % 3 == 1) {
						this.time.add(Integer.valueOf(str));
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