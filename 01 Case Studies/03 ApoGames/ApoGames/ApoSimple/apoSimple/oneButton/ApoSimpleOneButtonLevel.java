package apoSimple.oneButton;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class ApoSimpleOneButtonLevel {

	private ArrayList<ApoSimpleOneButtonEntity> entities;
	
	private float vecSheep;
	
	private String[] message;
	
	public ApoSimpleOneButtonLevel() {
		this.entities = new ArrayList<ApoSimpleOneButtonEntity>();
		this.vecSheep = ApoSimpleOneButtonSheep.VEC;
		this.message = new String[3];
		for (int i = 0; i < this.message.length; i++) {
			this.message[i] = "";
		}
	}

	public void addMessage(final int level, final String message) {
		this.message[level] = message;
	}
	
	public String[] getMessage() {
		return this.message;
	}

	public ArrayList<ApoSimpleOneButtonEntity> getEntities() {
		return this.entities;
	}

	public void setEntities(ArrayList<ApoSimpleOneButtonEntity> entities) {
		this.entities = entities;
	}
	
	public float getVecSheep() {
		return this.vecSheep;
	}

	public void setVecSheep(float vecSheep) {
		this.vecSheep = vecSheep;
	}

	public void render(Graphics2D g, int changeX, int changeY) {
		for (int i = 0; i < this.entities.size(); i++) {
			this.entities.get(i).render(g, changeX, changeY);
		}
	}
	
}
