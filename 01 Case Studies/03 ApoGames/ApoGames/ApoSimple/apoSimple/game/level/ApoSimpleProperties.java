package apoSimple.game.level;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;

import org.apogames.ApoIO;

import apoSimple.ApoSimpleConstants;
import apoSimple.game.ApoSimplePanel;

public class ApoSimpleProperties extends ApoIO {

	private ApoSimplePanel panel;
	
	public ApoSimpleProperties(final ApoSimplePanel panel) {
		this.panel = panel;
	}
	
	@Override
	public boolean writeLevel(DataOutputStream data) throws IOException {
		if (this.panel != null) {
			data.writeBoolean(this.panel.isbSoundMusic());
			data.writeBoolean(this.panel.isbSoundEffects());
			data.writeBoolean(this.panel.isBHelp());
			data.writeInt(this.panel.getCoins());
			data.writeBoolean(this.panel.isShowFPS());
			if (this.panel.getTextField() != null) {
				data.writeUTF(this.panel.getTextField().getCurString());
			}
			if (ApoSimplePanel.level_save == null) {
				data.writeUTF("");
			} else {
				data.writeUTF(ApoSimplePanel.level_save);
			}
			data.writeUTF(ApoSimpleConstants.REGION);
			
			data.writeBoolean(this.panel.isClassicMode());
		}
		return false;
	}

	@Override
	public boolean readLevel(DataInputStream data) throws EOFException, IOException {
		if (this.panel != null) {
			this.panel.setbSoundMusic(data.readBoolean());
			this.panel.setbSoundEffects(data.readBoolean());
			this.panel.setBHelp(data.readBoolean());
			this.panel.setCoins(data.readInt(), false);
			this.panel.setShowFPS(data.readBoolean());
			this.panel.loadName(data.readUTF());
			ApoSimplePanel.level_save = "";
			ApoSimplePanel.level_save = data.readUTF();
			
			ApoSimpleConstants.REGION = data.readUTF();
			ApoSimpleConstants.setLanguage(ApoSimpleConstants.REGION);
			
			this.panel.setClassicMode(data.readBoolean());
		}
		return false;
	}

}
