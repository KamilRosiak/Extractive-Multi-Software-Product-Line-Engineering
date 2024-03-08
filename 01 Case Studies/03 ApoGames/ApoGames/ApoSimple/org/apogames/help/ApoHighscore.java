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
	private ArrayList<Integer> levels;
	private ArrayList<Integer> points;
	private ArrayList<Float> times;
	private ArrayList<Float> curTimes;

	private static ApoHighscore singleInstance = new ApoHighscore();

	public static ApoHighscore getInstance() {
		if (null == singleInstance) {
			singleInstance = new ApoHighscore();
		}
		return singleInstance;
	}

	private ApoHighscore() {
		this.names = new ArrayList<String>();
		this.levels = new ArrayList<Integer>();
		this.points = new ArrayList<Integer>();
		this.times = new ArrayList<Float>();
		this.curTimes = new ArrayList<Float>();
		clear();
	}

	public ArrayList<Float> getTimes() {
		return this.times;
	}

	public ArrayList<Float> getCurTimes() {
		return this.curTimes;
	}

	public ArrayList<String> getNames() {
		return this.names;
	}

	public ArrayList<Integer> getLevels() {
		return this.levels;
	}

	public ArrayList<Integer> getPoints() {
		return this.points;
	}

	private void clear() {
		this.names.clear();
		this.levels.clear();
		this.points.clear();
		this.times.clear();
		this.curTimes.clear();
	}

	public boolean save(int level, int solution, String name) {
		return this.save(level, solution, name, ApoConstants.HIGHSCORE_SAVEPHP);
	}

	@SuppressWarnings("deprecation")
	public boolean save(int level, int solution, String name, String urlString) {
		if (ApoConstants.B_ONLINE) {
			try {
				URL url;
				URLConnection urlConn;
				DataOutputStream printout;
				DataInputStream input;

				url = new URL(urlString);
				urlConn = url.openConnection();

				urlConn.setDoInput(true);
				urlConn.setDoOutput(true);

				urlConn.setUseCaches(false);
				urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

				printout = new DataOutputStream(urlConn.getOutputStream());
				
				String content = "level=" + URLEncoder.encode(String.valueOf(level), "UTF-8")
						+ "&points=" + URLEncoder.encode(String.valueOf(solution), "UTF-8")
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

	public boolean load() {
		return this.load(ApoConstants.HIGHSCORE_GETPHP);
	}

	@SuppressWarnings("deprecation")
	public boolean load(String urlString) {
		clear();
		if (ApoConstants.B_ONLINE) {
			try {
				URL url;
				URLConnection urlConn;
				DataOutputStream printout;
				DataInputStream input;

				url = new URL(urlString);
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
					if (i % 5 == 0) {
						this.points.add(Integer.valueOf(str));
					} else if (i % 5 == 1) {
						this.levels.add(Integer.valueOf(str));
					} else if (i % 5 == 2) {
						this.names.add(str);
					} else if (i % 5 == 3) {
						this.times.add(Float.valueOf(str));
					} else if (i % 5 == 4) {
						this.curTimes.add(Float.valueOf(str));
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