package apoSnake.game.level;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;

import org.apogames.ApoIO;

public class ApoSnakeLevelIO extends ApoIO {
	
	private int solved;
	
	public ApoSnakeLevelIO() {
		this.solved = 0;
	}

	public int getSolved() {
		return solved;
	}

	public void setSolved(int solved) {
		this.solved = solved;
	}

	@Override
	public boolean writeLevel(DataOutputStream data) throws IOException {
		data.writeInt(this.solved);
		return true;
	}

	@Override
	public boolean readLevel(DataInputStream data) throws EOFException, IOException {
		this.solved = data.readInt();
		return false;
	}

}
