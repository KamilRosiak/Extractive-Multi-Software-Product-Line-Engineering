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

	private ArrayList<String> levelnames;
	private ArrayList<String> usernames;
	private ArrayList<String> descriptions;
	private ArrayList<String> level;
	private ArrayList<String> solution;

	private static ApoHighscore singleInstance = new ApoHighscore();

	public static ApoHighscore getInstance() {
		if (null == singleInstance) {
			singleInstance = new ApoHighscore();
		}
		return singleInstance;
	}

	private ApoHighscore() {
		this.levelnames = new ArrayList<String>();
		this.usernames = new ArrayList<String>();
		this.descriptions = new ArrayList<String>();
		this.level = new ArrayList<String>();
		this.solution = new ArrayList<String>();
		clear();
	}

	public final ArrayList<String> getLevelnames() {
		return this.levelnames;
	}

	public final ArrayList<String> getUsernames() {
		return this.usernames;
	}

	public final ArrayList<String> getDescriptions() {
		return this.descriptions;
	}

	public final ArrayList<String> getLevel() {
		return this.level;
	}

	public final ArrayList<String> getSolution() {
		return this.solution;
	}

	private void clear() {
		this.solution.clear();
		this.level.clear();
		this.descriptions.clear();
		this.usernames.clear();
		this.levelnames.clear();
	}

	@SuppressWarnings("deprecation")
	public boolean save(byte[][][] level, String levelname, String username, String description, String solution)  {
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
				
				String myLevel = String.valueOf(level.length);
				for (int l = 0; l < level.length; l++) {
					for (int y = 0; y < level[0].length; y++) {
						for (int x = 0; x < level[0][0].length; x++) {
							String addString = String.valueOf(level[l][y][x]);
							while (addString.length() < 2) {
								addString = "0" + addString;
							}
							myLevel += addString;
						}
					}
				}
				if ((levelname == null) || (levelname.length() <= 0)) {
					levelname = "UserLevel";
				}
				if (username == null) {
					username = "Mister X";
				}
				if (description == null) {
					description = "User-generated level";
				}
				if (solution == null) {
					solution = "";
				}
				
				String content = "levelname=" + URLEncoder.encode(String.valueOf(levelname), "UTF-8")
						+ "&username=" + URLEncoder.encode(String.valueOf(username), "UTF-8")
						+ "&description=" + URLEncoder.encode(String.valueOf(description), "UTF-8")
						+ "&solution=" + URLEncoder.encode(String.valueOf(solution), "UTF-8")
						+ "&level=" + URLEncoder.encode(String.valueOf(myLevel), "UTF-8");
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
					if (i % 5 == 0) {
						this.level.add(String.valueOf(str));
					} else if (i % 5 == 1) {
						this.usernames.add(String.valueOf(str));
					} else if (i % 5 == 2) {
						this.levelnames.add(String.valueOf(str));
					} else if (i % 5 == 3) {
						this.descriptions.add(String.valueOf(str));
					} else {
						this.solution.add(String.valueOf(str));
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