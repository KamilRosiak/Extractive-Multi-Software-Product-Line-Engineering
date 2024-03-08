package apoSkunkman.game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;

import org.apogames.ApoIO;

import apoSkunkman.ApoSkunkmanConstants;
import apoSkunkman.entity.ApoSkunkmanBush;
import apoSkunkman.entity.ApoSkunkmanStone;

/**
 * Klasse zum Laden und Speichern von EditorLevels
 * @author Dirk Aporius
 *
 */
public class ApoSkunkmanEditorIO extends ApoIO {

	/** Integervariable die angibt, das diese Position mit einem Stein besetzt ist */
	public static final byte STONE = 0;
	/** Integervariable die angibt, das diese Position frei ist */
	public static final byte FREE = 1;
	/** Integervariable die angibt, das diese Position mit einem Busch besetzt ist */
	public static final byte BUSH = 2;
	/** Integervariable die angibt, das diese Position mit Spieler 1 besetzt ist */
	public static final byte PLAYER_ONE = 3;
	/** Integervariable die angibt, das diese Position mit Spieler 2 besetzt ist */
	public static final byte PLAYER_TWO = 4;
	/** Integervariable die angibt, das diese Position mit Spieler 3 besetzt ist */
	public static final byte PLAYER_THREE = 5;
	/** Integervariable die angibt, das diese Position mit Spieler 4 besetzt ist */
	public static final byte PLAYER_FOUR = 6;
	
	/** Das Levelobjekt */
	private final ApoSkunkmanPanel panel;
	
	public ApoSkunkmanEditorIO(final ApoSkunkmanPanel panel) {
		this.panel = panel;
	}
	
	@Override
	public boolean readLevel(DataInputStream data) throws EOFException, IOException {
		this.panel.getLevel().setType(ApoSkunkmanConstants.LEVEL_TYPE_EDITOR);
		this.panel.getLevel().setTime(data.readInt());
		for (int i = 0; i < ApoSkunkmanConstants.PLAYER_MAX_PLAYER; i++) {
			int x = data.readByte();
			int y = data.readByte();
			this.panel.getLevel().getPlayers()[i].setX(x * ApoSkunkmanConstants.TILE_SIZE);
			this.panel.getLevel().getPlayers()[i].setY(y * ApoSkunkmanConstants.TILE_SIZE);
		}
		for (int y = 1; y < this.panel.getLevel().getLevel().length - 1; y++) {
			for (int x = 1; x < this.panel.getLevel().getLevel()[0].length - 1; x++) {
				byte type = data.readByte();
				if (type == ApoSkunkmanEditorIO.FREE) {
					this.panel.getLevel().getLevel()[y][x] = null;
				} else if (type == ApoSkunkmanEditorIO.STONE) {
					this.panel.getLevel().getLevel()[y][x] = this.panel.getLevel().makeStone(x, y);
				} else if (type == ApoSkunkmanEditorIO.BUSH) {
					int goodie = data.readByte();
					ApoSkunkmanBush bush = this.panel.getLevel().makeBush(x, y, goodie);
					this.panel.getLevel().getLevel()[y][x] = bush;
				}
			}
		}
		int x = data.readByte();
		int y = data.readByte();
		this.panel.getLevel().getGoalX().x = (x);
		this.panel.getLevel().getGoalX().y = (y);
		
		return false;
	}

	@Override
	public boolean writeLevel(DataOutputStream data) throws IOException {
		data.writeInt(this.panel.getLevel().getTime());
		for (int i = 0; i < ApoSkunkmanConstants.PLAYER_MAX_PLAYER; i++) {
			byte x = (byte)(this.panel.getLevel().getPlayers()[i].getX() / ApoSkunkmanConstants.TILE_SIZE);
			byte y = (byte)(this.panel.getLevel().getPlayers()[i].getY() / ApoSkunkmanConstants.TILE_SIZE);
			data.writeByte(x);
			data.writeByte(y);
		}
		for (int y = 1; y < this.panel.getLevel().getLevel().length - 1; y++) {
			for (int x = 1; x < this.panel.getLevel().getLevel()[0].length - 1; x++) {
				if (this.panel.getLevel().getLevel()[y][x] == null) {
					data.writeByte(ApoSkunkmanEditorIO.FREE);
				} else if (this.panel.getLevel().getLevel()[y][x] instanceof ApoSkunkmanStone) {
					data.writeByte(ApoSkunkmanEditorIO.STONE);
				} else if (this.panel.getLevel().getLevel()[y][x] instanceof ApoSkunkmanBush) {
					data.writeByte(ApoSkunkmanEditorIO.BUSH);
					data.writeByte((byte)(((ApoSkunkmanBush)(this.panel.getLevel().getLevel()[y][x])).getGoodie()));
				}
			}
		}
		byte x = (byte)(this.panel.getLevel().getGoalX().x);
		byte y = (byte)(this.panel.getLevel().getGoalX().y);
		data.writeByte(x);
		data.writeByte(y);
		return false;
	}

}
