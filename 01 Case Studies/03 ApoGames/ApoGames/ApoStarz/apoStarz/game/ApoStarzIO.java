package apoStarz.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import org.apogames.ApoConstants;

public class ApoStarzIO {

	private ArrayList<String> allLevels;

	private String levelName;
	
	public ApoStarzIO() {
		this.allLevels = new ArrayList<String>();
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
	public ArrayList<String> getAllLevels() {
		return this.allLevels;
	}

	public void setAllLevel(ArrayList<String> allLevels) {
		this.allLevels = allLevels;
	}
	
	public boolean addLevel(String level) {
		for (int i = 0; i < this.allLevels.size(); i++) {
			if (this.allLevels.get(i).equals(level)) {
				return false;
			}
		}
		this.allLevels.add(level);
		return true;
	}
	
	public boolean remLevel(String level) {
		for (int i = 0; i < this.allLevels.size(); i++) {
			if (this.allLevels.get(i).equals(level)) {
				this.allLevels.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public boolean pullLevel(int position, int count) {
		String level = this.allLevels.get(position);
		int newPosition = position + count;
		if (newPosition >= this.allLevels.size()) {
			newPosition = 0;
		} else if (newPosition < 0) {
			newPosition = this.allLevels.size() - 1;
		} 
		this.allLevels.remove(position);
		this.allLevels.add(newPosition, level);
		return true;
	}
	
	public int getLevelSize() {
		return this.allLevels.size();
	}
	
	public String getLevel(int position) {
		if ((position < 0) || (position >= this.allLevels.size())) {
			return null;
		}
		return this.allLevels.get(position);
	}
	
	public String getLevelName() {
		return this.levelName;
	}

	public boolean readLevel(String fileName) {
		try {
			if (ApoConstants.B_APPLET) {
				this.levelName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.lastIndexOf("."));			
			} else {
				this.levelName = fileName.substring(fileName.lastIndexOf(File.separator) + 1, fileName.lastIndexOf("."));
			}
		} catch (Exception ex) {
			return false;
		}
		String thisLine = "";
		this.allLevels = new ArrayList<String>();
		try {
			BufferedReader in;
			if (!ApoConstants.B_APPLET) {
				in = new BufferedReader(new FileReader(fileName));
			} else {
				URL connection = new URL(fileName);
				in = new BufferedReader(new InputStreamReader(connection.openStream()));
			}
			try {
				while (((thisLine = in.readLine()) != null)) {
					this.allLevels.add(thisLine);
				}
				in.close();
			} catch (IOException e) {
				return false;
			}
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public boolean writeLevel(String fileName) {
		try {
			BufferedWriter in = new BufferedWriter(new FileWriter(fileName));
			try {
				for (int i = 0; i < this.allLevels.size(); i++) {
					in.write(this.allLevels.get(i));
					in.newLine();
				}
				in.close();
			} catch (IOException e) {
				return false;
			}
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}
