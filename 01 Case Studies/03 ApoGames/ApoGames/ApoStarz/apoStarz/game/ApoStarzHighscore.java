package apoStarz.game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;

import org.apogames.ApoIO;

public class ApoStarzHighscore extends ApoIO {

	private ArrayList<ApoStarzHighscoreLevel> levels;
	
	private ArrayList<String> timeLevels;
	
	public ApoStarzHighscore() {
		this.levels = new ArrayList<ApoStarzHighscoreLevel>();
		this.timeLevels = new ArrayList<String>();
	}
	
	public int getPositionForTimeLevels(String level) {
		return this.timeLevels.indexOf(level);
	}
	
	public ArrayList<String> getAllLevelsWithSix() {
		ArrayList<String> level = new ArrayList<String>();
		for (int i = 0; i < this.levels.size(); i++) {
			String levelString = this.levels.get(i).getLevelString();
			String equal = levelString.substring(1,2);
			if (equal.equals("f")) {
				level.add(levelString);
			}
		}
		return level;
	}
	
	public ArrayList<String> getAllLevels() {
		ArrayList<String> level = new ArrayList<String>();
		for (int i = 0; i < this.levels.size(); i++) {
			level.add(this.levels.get(i).getLevelString());
		}
		ArrayList<String> returnLevel = new ArrayList<String>();
		int size = level.size();
		for (int i = 0; i < size; i++) {
			int random = (int)(Math.random() * level.size());
			if (random >= level.size()) {
				random = 0;
			}
			returnLevel.add(level.get(random));
			level.remove(random);
		}
		return returnLevel;
	}
	
	public void makeLevelsForHighscoreMode() {
		this.timeLevels = new ArrayList<String>();
		for (int i = 0; i < this.levels.size(); i++) {
			this.timeLevels.add(this.levels.get(i).getLevelString());
		}
	}
	
	public void makeLevelsForTimeMode(int levelCount) {
		this.timeLevels = new ArrayList<String>();
		int count = 0;
		while ((count < levelCount) && (count < this.levels.size())) {
			int random = (int)(Math.random() * this.levels.size());
			if (random >= this.levels.size()) {
				random = 0;
			}
			String level = this.levels.get(random).getLevelString();
			while (this.timeLevels.indexOf(level) >= 0) {
				random++;
				if (random >= this.levels.size()) {
					random = 0;
				}
				level = this.levels.get(random).getLevelString();
			}
			this.timeLevels.add(level);
			count++;
		}
	}
	
	public int getTimeLevelSize() {
		return this.timeLevels.size();
	}
	
	public String getLevelForTime(int index) {
		if (index < this.timeLevels.size()) {
			return this.timeLevels.get(index);
		}
		return "";
	}
	
	public ArrayList<String> getTimeLevels() {
		return this.timeLevels;
	}

	public String getRandomLevel() {
		int random = (int)(Math.random() * this.levels.size());
		if (random >= this.levels.size()) {
			random = 0;
		}
		return this.levels.get(random).getLevelString();
	}
	
	public void addHighscore(String levelString, int highscore, String solution, boolean bSolver) {
		for (int i = 0; i < this.levels.size(); i++) {
			if (levelString.equals(this.levels.get(i).getLevelString())) {
				if ((highscore < this.levels.get(i).getHighscore()) || (bSolver)) {
					this.levels.get(i).setHighscore(highscore);
					this.levels.get(i).setSolution(solution);
				}
				return;
			}
		}
		this.levels.add(new ApoStarzHighscoreLevel(levelString, highscore, solution));
	}
	
	public int getHighscore(String levelString) {
		for (int i = 0; i < this.levels.size(); i++) {
			if (levelString.equals(this.levels.get(i).getLevelString())) {
				return this.levels.get(i).getHighscore();
			}
		}
		return -1;
	}
	
	public String getSolution(String levelString) {
		for (int i = 0; i < this.levels.size(); i++) {
			if (levelString.equals(this.levels.get(i).getLevelString())) {
				return this.levels.get(i).getSolution();
			}
		}
		return "";
	}
	
	@Override
	public boolean readLevel(DataInputStream data) throws EOFException, IOException {
		int size = data.readInt();
		this.levels = new ArrayList<ApoStarzHighscoreLevel>();
		for (int i = 0; i < size; i++) {
			String levelString = data.readUTF();
			int highscore = data.readInt();
			String solution = data.readUTF();
			this.levels.add(new ApoStarzHighscoreLevel(levelString, highscore, solution));
		}
		return false;
	}

	@Override
	public boolean writeLevel(DataOutputStream data) throws IOException {
		data.writeInt(this.levels.size());
		for (int i = 0; i < this.levels.size(); i++) {
			String solution = this.levels.get(i).getSolution();
			if (solution.length() > 0) {
				data.writeUTF(this.levels.get(i).getLevelString());
				data.writeInt(this.levels.get(i).getHighscore());
				data.writeUTF(this.levels.get(i).getSolution());
			}
		}
		return false;
	}

}
