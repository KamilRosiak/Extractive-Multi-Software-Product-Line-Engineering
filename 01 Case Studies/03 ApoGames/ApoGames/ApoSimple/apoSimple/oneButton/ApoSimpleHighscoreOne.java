package apoSimple.oneButton;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apogames.ApoConstants;

public class ApoSimpleHighscoreOne {

	private ArrayList<String> names;
	private ArrayList<Integer> death;

	private static ApoSimpleHighscoreOne singleInstance = new ApoSimpleHighscoreOne();

	public static ApoSimpleHighscoreOne getInstance() {
		if (null == singleInstance) {
			singleInstance = new ApoSimpleHighscoreOne();
		}
		return singleInstance;
	}

	private ApoSimpleHighscoreOne() {
		this.names = new ArrayList<String>();
		this.death = new ArrayList<Integer>();
		clear();
	}

	public ArrayList<String> getNames() {
		return this.names;
	}

	public ArrayList<Integer> getDeaths() {
		return this.death;
	}

	private void clear() {
		this.names.clear();
		this.death.clear();
	}

	public boolean save(int death, String name) {
		return this.save(death, name, ApoConstants.HIGHSCORE_SAVEPHP_ONE);
	}

	@SuppressWarnings("deprecation")
	public boolean save(int death, String name, String urlString) {
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
				
				String content = "death=" + URLEncoder.encode(String.valueOf(death), "UTF-8")
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
		return this.load(ApoConstants.HIGHSCORE_GETPHP_ONE);
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
					if (i % 2 == 0) {
						this.death.add(Integer.valueOf(str));
					} else if (i % 2 == 1) {
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