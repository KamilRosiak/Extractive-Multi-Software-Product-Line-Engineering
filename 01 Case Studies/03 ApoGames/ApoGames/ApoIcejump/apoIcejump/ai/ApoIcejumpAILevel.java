package apoIcejump.ai;

import java.util.ArrayList;

import apoIcejump.entity.ApoIcejumpBlock;
import apoIcejump.entity.ApoIcejumpPlayer;
import apoIcejump.game.ApoIcejumpPanel;

/**
 * Klasse, die das eigentliche Spielfeld und alle Entities beinhaltet<br />
 * main class with all entities<br />
 * @author Dirk Aporius
 *
 */
public class ApoIcejumpAILevel {

	private ArrayList<ApoIcejumpAIBlock> blocks;
	
	private ArrayList<ApoIcejumpAIGoodies> goodies;
	
	private ApoIcejumpAIPlayer player;
	
	private ArrayList<ApoIcejumpAIEnemy> enemies;

	private ArrayList<ApoIcejumpAIEntity> birds;
	
	private int time;

	public ApoIcejumpAILevel(ApoIcejumpPanel panel, ApoIcejumpPlayer player) {
		this.player = new ApoIcejumpAIPlayer(player);
		this.time = panel.getTime();
		this.enemies = new ArrayList<ApoIcejumpAIEnemy>();
		for (int i = 0; i < panel.getPlayers().length; i++) {
			if (!player.equals(panel.getPlayers()[i])) {
				this.enemies.add(new ApoIcejumpAIEnemy(panel.getPlayers()[i]));
			}
		}
		this.goodies = new ArrayList<ApoIcejumpAIGoodies>();
		for (int i = 0; i < panel.getGoodies().size(); i++) {
			this.goodies.add(new ApoIcejumpAIGoodies(panel.getGoodies().get(i)));
		}
		
		this.birds = new ArrayList<ApoIcejumpAIEntity>();
		for (int i = 0; i < panel.getBirds().size(); i++) {
			this.birds.add(new ApoIcejumpAIEntity(panel.getBirds().get(i)));
		}
		
		ArrayList<ApoIcejumpBlock> myBlocks = new ArrayList<ApoIcejumpBlock>();
		for (int i = 0; i < panel.getBackBlocks().size(); i++) {
			myBlocks.add(panel.getBackBlocks().get(i));
		}
		for (int i = 0; i < panel.getFrontBlocks().size(); i++) {
			myBlocks.add(panel.getFrontBlocks().get(i));
		}
		this.blocks = this.sortBlock(myBlocks);
	}

	/**
	 * sortiert die Eisblöcke nach Abständen zum Spieler
	 * @param blocks : Eisblöcke
	 * @return sortierte Eisblöcke
	 */
	private ArrayList<ApoIcejumpAIBlock> sortBlock(ArrayList<ApoIcejumpBlock> blocks) {
		ArrayList<ApoIcejumpAIBlock> myBlocks = new ArrayList<ApoIcejumpAIBlock>();
		ArrayList<Integer> values = new ArrayList<Integer>();
		while (blocks.size() != myBlocks.size()) {
			float minValue = 1000000;
			int minI = -1;
			for (int i = 0; i < blocks.size(); i++) {
				float difference = Math.abs(blocks.get(i).getX() + blocks.get(i).getWidth()/2 - this.player.getX() - this.player.getWidth()/2);
				if ((minValue > difference) && (values.indexOf(i) == -1)) {
					minValue = difference;
					minI = i;
				}
			}
			myBlocks.add(new ApoIcejumpAIBlock(blocks.get(minI)));
			values.add(minI);
		}
		
		return myBlocks;
	}
	
	/**
	 * gibt eine ArrayList mit sortierten Eisblöcken zurück, welche natürlich auch leer sein kann <br />
	 * Die Blöcke sind nach Abstand zum Spieler sortiert <br />
	 * returns an arraylist with sorted ice blocks<br />
	 * Is sorted after the distance to the player<br />
	 * @return gibt eine ArrayList mit Eisblöcken zurück, welche natürlich auch leer sein kann / returns an arraylist with sorted ice blocks
	 */
	public ArrayList<ApoIcejumpAIBlock> getBlocks() {
		return this.blocks;
	}

	/**
	 * gibt eine ArrayList mit Goodies zurück, welche natürlich auch leer sein kann<br />
	 * returns an arraylist with goodie<br />
	 * @return gibt eine ArrayList mit Goodies zurück, welche natürlich auch leer sein kann / returns an arraylist with goodie
	 */
	public ArrayList<ApoIcejumpAIGoodies> getGoodies() {
		return this.goodies;
	}

	/**
	 * gibt euren Spieler zurück<br />
	 * returns your player<br />
	 * @return gibt euren Spieler zurück / returns your player<br />
	 */
	public ApoIcejumpAIPlayer getPlayer() {
		return this.player;
	}

	/**
	 * gibt euch eine ArrayList mit Gegner zurück <br />
	 * da ihr nur gegen einen Gegner kämpft, könnt ihr euch den Gegner mithilfe von <br />
	 * getEnemies().get(0); holen <br />
	 * returns an arraylist with your enemies<br />
	 * you fight only against one ai then get your enemy with getEnemies().get(0);<br />
	 * @return gibt euch eine ArrayList mit Gegner zurück / returns an arraylist with your enemies
	 */
	public ArrayList<ApoIcejumpAIEnemy> getEnemies() {
		return this.enemies;
	}

	/**
	 * gibt eine ArrayList mit Vögeln zurück, welche natürlich auch leer sein kann <br />
	 * returns an arraylist with birds<br />
	 * @return gibt eine ArrayList mit Vögeln zurück, welche natürlich auch leer sein kann / returns an arraylist with birds <br />
	 */
	public ArrayList<ApoIcejumpAIEntity> getBirds() {
		return this.birds;
	}
	
	/**
	 * gibt die Zeit zurück, die schon vergangen ist (in MS)<br />
	 * return the elapsed time in ms<br />
	 * @return gibt die Zeit zurück, die schon vergangen ist (in MS) / return the elapsed time in ms
	 */
	public int getTime() {
		return this.time;
	}
	
	/**
	 * gibt zurück, ob das Spiel sich im SuddenDeath Mode befindet, wo jeder Eicblock bei der nächsten Berührung zerspringt<br />
	 * return whether the game is in sudden death mode or not
	 * @return TRUE, SuddenDeath Mode on, sonst FALSE / TRUE game is in sudden death mode, else FALSE
	 */
	public boolean isSuddenDeath() {
		if (this.time > ApoIcejumpAIConstants.GAME_SUDDEN_DEATH_TIME) {
			return true;
		}
		return false;
	}
	
}
