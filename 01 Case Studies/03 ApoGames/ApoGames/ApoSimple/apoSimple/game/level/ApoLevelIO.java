package apoSimple.game.level;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;

import org.apogames.ApoIO;

public class ApoLevelIO extends ApoIO {

	private ArrayList<ApoSimpleLevelHighscore> highscore;
	
	public ApoLevelIO() {
		this.highscore = new ArrayList<ApoSimpleLevelHighscore>();
	}
	
	public ArrayList<ApoSimpleLevelHighscore> getHighscore() {
		return this.highscore;
	}

	public void setHighscore(ArrayList<ApoSimpleLevelHighscore> highscore) {
		this.highscore = highscore;
	}

	@Override
	public boolean writeLevel(DataOutputStream data) throws IOException {
		if (this.highscore == null) {
			return false;
		}
		data.writeInt(this.highscore.size());
		for (int i = 0; i < this.highscore.size(); i++) {
			data.writeInt(this.highscore.get(i).getLevel());
			data.writeInt(this.highscore.get(i).getStars());
		}
		return true;
	}

	@Override
	public boolean readLevel(DataInputStream data) throws EOFException, IOException {
		this.highscore.clear();
		int size = data.readInt();
		for (int i = 0; i < size; i++) {
			this.highscore.add(new ApoSimpleLevelHighscore(data.readInt(), data.readInt()));
		}
		return false;
	}

}
