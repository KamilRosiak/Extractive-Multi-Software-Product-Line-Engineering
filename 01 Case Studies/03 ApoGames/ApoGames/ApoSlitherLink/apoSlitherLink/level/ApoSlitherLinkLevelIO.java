package apoSlitherLink.level;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;

import org.apogames.ApoIO;

public class ApoSlitherLinkLevelIO extends ApoIO {

	private ArrayList<String> levels;
	private ArrayList<String> solvedLevels;
	
	private ArrayList<Integer> inOtherLevels;
	
	public ApoSlitherLinkLevelIO() {
		this.levels = new ArrayList<String>();
		this.solvedLevels = new ArrayList<String>();
		this.inOtherLevels = new ArrayList<Integer>();
	}
	
	public final ArrayList<String> getLevels() {
		return this.levels;
	}

	public void checkLevelsIsIn(ArrayList<ApoSlitherLinkLevelsHelp> checkLevels, ApoSlitherLinkLevel level) {
		for (int i = 0; i < checkLevels.size(); i++) {
			for (int j = 0; j < this.levels.size(); j++) {
				if (this.levels.get(j).equals(checkLevels.get(i).getLevelString())) {
					checkLevels.get(i).setBSolved(true);
					checkLevels.get(i).setSolvedLevelString(this.solvedLevels.get(j));
					checkLevels.get(i).makeSolvedImage(level);
					this.inOtherLevels.add(j);
					break;
				}
			}
		}
	}
	
	public void addCustomsLevels(ArrayList<ApoSlitherLinkLevelsHelp> customLevels, ApoSlitherLinkLevel level) {
		for (int j = 0; j < this.levels.size(); j++) {
			if (this.inOtherLevels.indexOf(j) == -1) {
				ApoSlitherLinkLevelsHelp help = new ApoSlitherLinkLevelsHelp(this.levels.get(j));
				help.setBSolved(true);
				help.setSolvedLevelString(this.solvedLevels.get(j));
				help.makeSolvedImage(level);
				customLevels.add(help);
			}
		}		
	}
	
	public void saveLevels(ArrayList<ApoSlitherLinkLevelsHelp> checkLevels) {
		for (int i = 0; i < checkLevels.size(); i++) {
			if (checkLevels.get(i).isBSolved()) {
				if (this.levels.size() == 0) {
					this.levels.add(checkLevels.get(i).getLevelString());
					this.solvedLevels.add(checkLevels.get(i).getSolvedLevelString());
				} else {
					if (this.levels.indexOf(checkLevels.get(i).getLevelString()) == -1) {
						this.levels.add(checkLevels.get(i).getLevelString());
						this.solvedLevels.add(checkLevels.get(i).getSolvedLevelString());
					}
				}
			}
		}
	}
	
	@Override
	public boolean readLevel(DataInputStream data) throws EOFException, IOException {
		int size = data.readInt();
		this.levels.clear();
		this.solvedLevels.clear();
		for (int i = 0; i < size; i++) {
			String s = data.readUTF();
			this.levels.add(s);
			s = data.readUTF();
			this.solvedLevels.add(s);
		}
		return false;
	}

	@Override
	public boolean writeLevel(DataOutputStream data) throws IOException {
		int size = this.levels.size();
		data.writeInt(size);
		for (int i = 0; i < size; i++) {
			data.writeUTF(this.levels.get(i));
			data.writeUTF(this.solvedLevels.get(i));
		}
		return false;
	}

}
