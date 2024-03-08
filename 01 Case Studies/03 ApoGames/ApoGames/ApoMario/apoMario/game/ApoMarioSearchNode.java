package apoMario.game;

import java.util.ArrayList;

/**
 * Hilfsklasse für die Wegfindung für einen einzelnen Knoten<br />
 * enthält x, y Wert und gibt die benachtbarten anderen Nodes zurück
 * @author Dirk Aporius
 *
 */
public class ApoMarioSearchNode {

	private final int node;
	
	private final int x;
	private final int y;
	
	private ArrayList<Integer> conntectedNodes;
	
	public ApoMarioSearchNode(int note, int x, int y) {
		this.node = note;
		this.x = x;
		this.y = y;
		this.conntectedNodes = new ArrayList<Integer>();
	}
	
	public ApoMarioSearchNode(int note, int x, int y, ArrayList<Integer> conntectedNodes) {
		this.node = note;
		this.x = x;
		this.y = y;
		this.conntectedNodes = conntectedNodes;
		if (this.conntectedNodes == null) {
			this.conntectedNodes = new ArrayList<Integer>();
		}
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getNode() {
		return this.node;
	}

	public final ArrayList<Integer> getConntectedNodes() {
		return this.conntectedNodes;
	}
	
	public void addConnectedNode(int node) {
		if (this.conntectedNodes.indexOf(node) < 0) {
			this.conntectedNodes.add(node);
		}
	}
	
	public void addConnectedNodes(ArrayList<Integer> nodes) {
		for (int i = 0; i < nodes.size(); i++) {
			if (this.conntectedNodes.indexOf(nodes.get(i)) < 0) {
				this.conntectedNodes.add(nodes.get(i));
			}
		}
	}
}
