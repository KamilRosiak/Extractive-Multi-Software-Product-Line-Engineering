package net.apogames.apoclock.highscore;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import android.util.Log;

import net.apogames.apoclock.ApoClock;
import net.apogames.apoclock.ApoClockConstants;

public class ApoClockHighscoreLoad {
	
	private ArrayList<String> names;
	private ArrayList<Integer> points;
	private ArrayList<Integer> clocks;
	private ArrayList<Integer> id;
	private ArrayList<Float> times;

	private static ApoClockHighscoreLoad singleInstance = new ApoClockHighscoreLoad();

	public static ApoClockHighscoreLoad getInstance() {
		if (null == singleInstance) {
			singleInstance = new ApoClockHighscoreLoad();
		}
		return singleInstance;
	}

	private ApoClockHighscoreLoad() {
		this.names = new ArrayList<String>();
		this.points = new ArrayList<Integer>();
		this.clocks = new ArrayList<Integer>();
		this.id = new ArrayList<Integer>();
		this.times = new ArrayList<Float>();
		clear();
	}

	public ArrayList<String> getNames() {
		return names;
	}

	public ArrayList<Integer> getPoints() {
		return points;
	}

	public ArrayList<Integer> getClocks() {
		return clocks;
	}

	public ArrayList<Integer> getId() {
		return id;
	}

	public ArrayList<Float> getTimes() {
		return times;
	}

	private void clear() {
		this.points.clear();
		this.clocks.clear();
		this.id.clear();
		this.names.clear();

		this.times.clear();
	}

	public boolean save(final String name, final int points, final int clocks) {
		Log.d("Highscore", ""+points+" "+clocks);
		if (ApoClock.isOnline()) {
			try {
				URL url;
				URLConnection urlConn;
				DataOutputStream printout;
				DataInputStream input;

				url = new URL(ApoClockConstants.HIGHSCORE_SAVEPHP);
				urlConn = url.openConnection();

				urlConn.setDoInput(true);
				urlConn.setDoOutput(true);

				urlConn.setUseCaches(false);
				urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

				printout = new DataOutputStream(urlConn.getOutputStream());
				
				String content = "points=" + URLEncoder.encode(String.valueOf(points), "UTF-8")
						+ "&clocks=" + URLEncoder.encode(String.valueOf(clocks), "UTF-8")
						+ "&name=" + URLEncoder.encode(name, "UTF-8");
				Log.d("Highscore", content);
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
			} catch (IOException ioe) {
				System.err.println("IOException: " + ioe.getMessage());
			}
		}
		return false;
	}

	public boolean load() {
		clear();
		if (ApoClock.isOnline()) {
			try {
				URL url;
				URLConnection urlConn;
				DataOutputStream printout;
				DataInputStream input;

				url = new URL(ApoClockConstants.HIGHSCORE_GETPHP);
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
						this.names.add(str);
					} else if (i % 5 == 1) {
						this.times.add(Float.valueOf(str));
					} else if (i % 5 == 2) {
						this.points.add(Integer.valueOf(str));
					} else if (i % 5 == 3) {
						this.clocks.add(Integer.valueOf(str));
					} else if (i % 5 == 4) {
						this.id.add(Integer.valueOf(str));
					}
					i = i + 1;
				}
				input.close();
				return true;
			} catch (MalformedURLException me) {
				System.err.println("MalformedURLException: " + me);
			} catch (IOException ioe) {
				System.err.println("IOException: " + ioe.getMessage());
			}
		}
		return false;
	}
}
