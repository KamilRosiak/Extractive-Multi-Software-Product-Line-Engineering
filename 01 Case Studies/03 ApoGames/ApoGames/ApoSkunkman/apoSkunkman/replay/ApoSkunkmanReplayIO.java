package apoSkunkman.replay;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;

import org.apogames.ApoIO;

import apoSkunkman.ApoSkunkmanConstants;
import apoSkunkman.entity.ApoSkunkmanBush;
import apoSkunkman.entity.ApoSkunkmanStone;
import apoSkunkman.game.ApoSkunkmanEditorIO;
import apoSkunkman.level.ApoSkunkmanLevel;

/**
 * Klasse zum Laden und Speichern von Replays
 * @author Dirk Aporius
 *
 */
public class ApoSkunkmanReplayIO extends ApoIO {
	/** Das Levelobjekt */
	private final ApoSkunkmanLevel level;
	/** Das geladene oder zu speichernde Replay */
	private ApoSkunkmanReplay replay;
	/** Hilfsreplayobjekt mit allen Sachen für das Replaylevel */
	private ApoSkunkmanReplayHelp help;
	
	public ApoSkunkmanReplayIO(final ApoSkunkmanLevel level) {
		this.level = level;
	}
	
	/**
	 * liest eine Datei ein
	 */
	public boolean readLevel(String fileName) {
		super.readLevel(fileName);
		this.replay.setHelp(help);
		this.level.setReplay(this.replay.clone());
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean readLevel(DataInputStream data) throws EOFException, IOException {
		int type = data.readInt();
		long random = data.readLong();
		boolean bush = data.readBoolean();
		int time = data.readInt();
		int players = data.readInt();
		String editorLevel = data.readUTF();
		String[] names = new String[ApoSkunkmanConstants.PLAYER_MAX_PLAYER];
		for (int i = 0; i < names.length; i++) {
			names[i] = data.readUTF();
		}
		ArrayList<Byte>[] array = new ArrayList[ApoSkunkmanConstants.PLAYER_MAX_PLAYER];
		for (int i = 0; i < array.length; i++) {
			array[i] = new ArrayList<Byte>();
		}
		for (int i = 0; i < array.length; i++) {
			int size = data.readInt();
			for (int j = 0; j < size; j++) {
				array[i].add((byte)data.readByte());
			}
		}
		// falls es ein Editorlevel ist, dann lad das Level einfach mit
		if ((editorLevel != null) && (editorLevel.length() > 0)) {
			if (this.level.getGame().getEditor().getLevel() == null) {
				this.level.getGame().getEditor().setEditorLevel();
				this.level.getGame().getEditor().makeEditorLevel();
			}
			this.level.setType(ApoSkunkmanConstants.LEVEL_TYPE_EDITOR);
			int helpTime = data.readInt();
			Point[] helpPlayers = new Point[ApoSkunkmanConstants.PLAYER_MAX_PLAYER];
			for (int i = 0; i < ApoSkunkmanConstants.PLAYER_MAX_PLAYER; i++) {
				int x = data.readByte();
				int y = data.readByte();
				helpPlayers[i] = new Point(x, y);
			}
			byte[][][] helpLevel = new byte[this.level.getLevel().length][this.level.getLevel()[0].length][2];
			for (int y = 1; y < this.level.getLevel().length - 1; y++) {
				for (int x = 1; x < this.level.getLevel()[0].length - 1; x++) {
					helpLevel[y][x][0] = data.readByte();
					if (helpLevel[y][x][0] == ApoSkunkmanEditorIO.BUSH) {
						helpLevel[y][x][1] = data.readByte();
					}
				}
			}
			int x = data.readByte();
			int y = data.readByte();
			Point helpGoalX = new Point(x, y);
			this.help = new ApoSkunkmanReplayHelp(helpLevel, helpPlayers, helpGoalX, helpTime);
			this.level.getGame().getEditor().makeEditorLevel();
			this.level.getGame().getEditor().setEditorLevel();
		} else {
			this.help = null;
		}
		this.replay = new ApoSkunkmanReplay(type, random, bush, time, players, editorLevel, names);
		this.replay.setPlayerArray(array);
		return true;
	}

	/**
	 * schreibt eine Datei in das übergebene String Filesystem
	 * @param fileName = wo wird hingespeichert
	 */
	public void writeLevel(String fileName, ApoSkunkmanReplay replay) {
		this.replay = replay.clone();
		super.writeLevel(fileName);
	}
	
	@Override
	public boolean writeLevel(DataOutputStream data) throws IOException {
		data.writeInt(this.replay.getType());
		data.writeLong(this.replay.getRandom());
		data.writeBoolean(this.replay.isBBush());
		data.writeInt(this.replay.getTime());
		data.writeInt(this.replay.getPlayers());
		data.writeUTF(this.replay.getEditorLevelString());
		for (int i = 0; i < this.replay.getNames().length; i++) {
			data.writeUTF(this.replay.getNames()[i]);
		}
		for (int i = 0; i < this.replay.getPlayerArray().length; i++) {
			data.writeInt(this.replay.getPlayerArray()[i].size());
			for (int j = 0; j < this.replay.getPlayerArray()[i].size(); j++) {
				data.writeByte(this.replay.getPlayerArray()[i].get(j));
			}
		}
		// falls es ein Editorlevel ist, dann speicher das Level einfach mit ab (wie ein Editorlevel
		if ((this.replay.getEditorLevelString() != null) && (this.replay.getEditorLevelString().length() > 0)) {
			this.level.getGame().getEditor().setEditorLevel();
			this.level.getGame().getEditor().makeEditorLevel();
			data.writeInt(this.level.getTime());
			for (int i = 0; i < ApoSkunkmanConstants.PLAYER_MAX_PLAYER; i++) {
				byte x = (byte)(this.level.getPlayers()[i].getX() / ApoSkunkmanConstants.TILE_SIZE);
				byte y = (byte)(this.level.getPlayers()[i].getY() / ApoSkunkmanConstants.TILE_SIZE);
				data.writeByte(x);
				data.writeByte(y);
			}
			for (int y = 1; y < this.level.getLevel().length - 1; y++) {
				for (int x = 1; x < this.level.getLevel()[0].length - 1; x++) {
					if (this.level.getLevel()[y][x] == null) {
						data.writeByte(ApoSkunkmanEditorIO.FREE);
					} else if (this.level.getLevel()[y][x] instanceof ApoSkunkmanStone) {
						data.writeByte(ApoSkunkmanEditorIO.STONE);
					} else if (this.level.getLevel()[y][x] instanceof ApoSkunkmanBush) {
						data.writeByte(ApoSkunkmanEditorIO.BUSH);
						data.writeByte((byte)(((ApoSkunkmanBush)(this.level.getLevel()[y][x])).getGoodie()));
					}
				}
			}
			byte x = (byte)(this.level.getGoalX().x);
			byte y = (byte)(this.level.getGoalX().y);
			data.writeByte(x);
			data.writeByte(y);
		}
		return true;
	}

}
