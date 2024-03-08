package apoSimple.game.level;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apogames.ApoConstants;

public class ApoSimpleUserlevelsLoad {
	private ArrayList<String> levels;
	private ArrayList<Integer> count;
	private ArrayList<Integer> all;
	private ArrayList<Float> times;
	private ArrayList<Float> curTimes;

	private static ApoSimpleUserlevelsLoad singleInstance = new ApoSimpleUserlevelsLoad();

	public static ApoSimpleUserlevelsLoad getInstance() {
		if (null == singleInstance) {
			singleInstance = new ApoSimpleUserlevelsLoad();
		}
		return singleInstance;
	}

	private ApoSimpleUserlevelsLoad() {
		this.levels = new ArrayList<String>();
		this.count = new ArrayList<Integer>();
		this.all = new ArrayList<Integer>();
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

	public ArrayList<String> getLevels() {
		return this.levels;
	}

	public ArrayList<Integer> getCount() {
		return this.count;
	}

	public ArrayList<Integer> getAll() {
		return this.all;
	}

	private void clear() {
		this.all.clear();
		this.levels.clear();
		this.count.clear();
		this.times.clear();
		this.curTimes.clear();
	}

	@SuppressWarnings("deprecation")
	public boolean save(String level) {
		if (ApoConstants.B_ONLINE) {
			try {
				URL url;
				URLConnection urlConn;
				DataOutputStream printout;
				DataInputStream input;

				url = new URL(ApoConstants.USERLEVELS_SAVEPHP);
				urlConn = url.openConnection();

				urlConn.setDoInput(true);
				urlConn.setDoOutput(true);

				urlConn.setUseCaches(false);
				urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

				printout = new DataOutputStream(urlConn.getOutputStream());
				
				String content = "level=" + URLEncoder.encode(String.valueOf(level), "UTF-8");
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

				url = new URL(ApoConstants.USERLEVELS_GETPHP);
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
						this.levels.add(str);
					} else if (i % 5 == 1) {
						this.times.add(Float.valueOf(str));
					} else if (i % 5 == 2) {
						this.count.add(Integer.valueOf(str));
					} else if (i % 5 == 3) {
						this.all.add(Integer.valueOf(str));
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
