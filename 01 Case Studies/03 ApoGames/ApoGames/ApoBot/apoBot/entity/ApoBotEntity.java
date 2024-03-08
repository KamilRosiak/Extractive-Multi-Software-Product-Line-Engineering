package apoBot.entity;

import org.apogames.entity.ApoEntity;

/**
 * die Entityklasse für das Spiel
 * und fügt zu den vorgegebenen Funktionen noch eine für das aktulle Kommando hinzu
 * @author Dirk Aporius
 *
 */
public class ApoBotEntity extends ApoEntity {

	private int command;
	
	public ApoBotEntity(int command, float x, float y, float width, float height) {
		super(null, x, y, width, height);
		this.setCommand(command);
	}

	public int getCommand() {
		return this.command;
	}

	public void setCommand(int command) {
		this.command = command;
	}

}
