package apoBot.level;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;

import org.apogames.ApoIO;

/**
 * Klasse, die sich um das Laden und SPeichern von Levels kümmert
 * @author Dirk Aporius
 *
 */
public class ApoBotLevelIO extends ApoIO {

	private ArrayList<ApoBotLevel> levels;
	
	public ApoBotLevelIO() {
		this.levels = new ArrayList<ApoBotLevel>();
	}
	
	public ApoBotLevel getLevel(int level) {
		if ((level < 0) || (level >= this.levels.size())) {
			return null;
		}
		return this.levels.get(level);
	}
	
	public int getMaxLevel() {
		return this.levels.size();
	}
	
	public ArrayList<ApoBotLevel> getLevels() {
		return this.levels;
	}

	public void setLevels(ArrayList<ApoBotLevel> levels) {
		this.levels = levels;
	}

	public void addLevel(ApoBotLevel level, int position) {
		if ((position <= -1) || (position >= this.levels.size())) {
			this.levels.add(level);
		} else {
			this.levels.add(position, level);
		}
	}

	public void changeLevel(int position, int next) {
		ApoBotLevel level = this.levels.get(position);
		this.levels.remove(position);
		if (position + next < 0) {
			this.levels.add(level);
		} else if (position + next >= this.levels.size()) {
			this.levels.add(0, level);
		} else {
			this.levels.add(position + next, level);
		}
	}
	
	public void removeLevel(ApoBotLevel level) {
		this.levels.remove(level);
	}
	
	public void removeLevel(int position) {
		this.levels.remove(position);
	}
	
	@Override
	public boolean readLevel(DataInputStream data) throws EOFException,IOException {
		this.levels = new ArrayList<ApoBotLevel>();
		int size = data.readInt();
		for (int i = 0; i < size; i++) {
			int ySize = data.readInt();
			int xSize = data.readInt();
			ApoBotLevelLevel[][] levelLevel = new ApoBotLevelLevel[ySize][xSize];
			for (int y = 0; y < ySize; y++) {
				for (int x = 0; x < xSize; x++) {
					int ground = data.readInt();
					int height = data.readInt();
					levelLevel[y][x] = new ApoBotLevelLevel(ground, height);
				}
			}
			
			int xPoint = data.readInt();
			int yPoint = data.readInt();
			
			int directionView = data.readInt();
			
			int menuSize = data.readInt();
			int[] menu = new int[menuSize];
			for (int m = 0; m < menuSize; m++) {
				menu[m] = data.readInt();
			}
		this.levels.add(new ApoBotLevel(levelLevel, new Point(xPoint, yPoint), directionView, menu));	
		}
		return true;
	}

	@Override
	public boolean writeLevel(DataOutputStream data) throws IOException {
		if ((this.levels == null) && (this.levels.size() <= 0)) {
			return false;
		}
		data.writeInt(this.levels.size());
		for (int i = 0; i < this.levels.size(); i++) {
			ApoBotLevelLevel[][] levelLevel = this.levels.get(i).getLevel();
			data.writeInt(levelLevel.length);
			data.writeInt(levelLevel[0].length);
			for (int y = 0; y < levelLevel.length; y++) {
				for (int x = 0; x < levelLevel[0].length; x++) {
					data.writeInt(levelLevel[y][x].getGround());
					data.writeInt(levelLevel[y][x].getHeight());
				}
			}
			data.writeInt(this.levels.get(i).getStartPoint().x);
			data.writeInt(this.levels.get(i).getStartPoint().y);
			
			data.writeInt(this.levels.get(i).getDirectionView());
			
			data.writeInt(3);
			data.writeInt(this.levels.get(i).getCommandsMenu());
			data.writeInt(this.levels.get(i).getFunctionsOneMenu());
			data.writeInt(this.levels.get(i).getFunctionsTwoMenu());
		}
		return true;
	}

}
