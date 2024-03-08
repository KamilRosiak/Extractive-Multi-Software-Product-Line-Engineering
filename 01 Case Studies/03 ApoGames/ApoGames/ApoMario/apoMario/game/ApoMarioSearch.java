package apoMario.game;

import java.util.ArrayList;

import apoMario.ApoMarioConstants;

/**
 * kleine Hilfsklasse, um einen Weg zum Ziel zu bekommen (nicht ZWANGSLÄUFIG den Besten ... eher selten ... dafür t < 20 Minuten geschrieben alles)
 * @author Dirk Aporius
 *
 */
public class ApoMarioSearch {

	private ArrayList<ApoMarioSearchNode> nodes;
	
	public ApoMarioSearch() {
		this.nodes = new ArrayList<ApoMarioSearchNode>();
	}
	
	public final ArrayList<ApoMarioSearchNode> getNodes() {
		return this.nodes;
	}

	public void addNode(ApoMarioSearchNode node) {
		this.nodes.add(node);
	}
	
	public ArrayList<ApoMarioSearchNode> searchWay(int startNode, int endNode) {
		ArrayList<ApoMarioSearchNode> way = new ArrayList<ApoMarioSearchNode>();
		
		way = this.getWay(startNode, endNode, way);
		
		return way;
	}
	
	private ArrayList<ApoMarioSearchNode> getWay(int startNode, int endNode, ArrayList<ApoMarioSearchNode> way) {
		ApoMarioSearchNode node = this.nodes.get(startNode);
		way.add(node);
		ArrayList<Integer> sortedNodes = new ArrayList<Integer>();
		for (int i = 0; i < node.getConntectedNodes().size(); i++) {
			if (node.getConntectedNodes().get(i) == endNode) {
				way.add(this.nodes.get(endNode));
				return way;
			}
			boolean bIn = false;
			ApoMarioSearchNode connectedNode = this.nodes.get(node.getConntectedNodes().get(i));
			for (int w = 0; w < way.size(); w++) {
				if (way.get(w).equals(connectedNode)) {
					bIn = true;
					break;
				}
			}
			if (!bIn) {
				if (sortedNodes.size() <= 0) {
					sortedNodes.add(node.getConntectedNodes().get(i));
				} else {
					for (int j = 0; j < sortedNodes.size(); j++) {
						if (this.getCost(this.nodes.get(sortedNodes.get(j)).getX(), this.nodes.get(sortedNodes.get(j)).getY(), this.nodes.get(endNode).getX(), this.nodes.get(endNode).getY()) >
							this.getCost(this.nodes.get(node.getConntectedNodes().get(i)).getX(), this.nodes.get(node.getConntectedNodes().get(i)).getY(), this.nodes.get(endNode).getX(), this.nodes.get(endNode).getY())) {
							sortedNodes.add(j, node.getConntectedNodes().get(i));
							break;
						}
						if (j == sortedNodes.size() - 1) {
							sortedNodes.add(node.getConntectedNodes().get(i));
							break;
						}
					}
				}
			}
		}
		for (int i = 0; i < sortedNodes.size(); i++) {
			ArrayList<ApoMarioSearchNode> ways = this.getWay(sortedNodes.get(i), endNode, way);
			if ((way.size() > 0) && (ways.get(way.size() - 1).getNode() == endNode)) {
				return way;
			} else {
				way.remove(ways.get(way.size() - 1));
			}
		}
		return way;
	}
	
	private int getCost(int x, int y, int endX, int endY) {
		int cost = (int)(Math.pow(Math.abs(x - endX), 2) + Math.pow(Math.abs(y - endY), 2));
		return cost;
	}
	
	public static void main(String[] args) {
		int size = ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE;
		int x = 18 * size;
		int y = 13 * size;
		
		ApoMarioSearch search = new ApoMarioSearch();
		ApoMarioSearchNode node = new ApoMarioSearchNode(0, x - 7 * size, y - 6 * size);
		node.addConnectedNode(1);
		search.addNode(node);
		
		node = new ApoMarioSearchNode(1, x - 7 * size, y - 5 * size);
		node.addConnectedNode(0);
		node.addConnectedNode(2);
		node.addConnectedNode(21);
		search.addNode(node);
		
		node = new ApoMarioSearchNode(2, x - 5 * size, y - 5 * size);
		node.addConnectedNode(1);
		node.addConnectedNode(3);
		node.addConnectedNode(6);
		search.addNode(node);
		
		node = new ApoMarioSearchNode(3, x - 5 * size, y - 6 * size);
		node.addConnectedNode(2);
		node.addConnectedNode(4);
		search.addNode(node);
		
		node = new ApoMarioSearchNode(4, x - 2 * size, y - 6 * size);
		node.addConnectedNode(3);
		node.addConnectedNode(5);
		search.addNode(node);

		node = new ApoMarioSearchNode(5, x - 2 * size, y - 8 * size);
		node.addConnectedNode(4);
		search.addNode(node);
		
		node = new ApoMarioSearchNode(6, x - 5 * size, y - 4 * size);
		node.addConnectedNode(2);
		node.addConnectedNode(7);
		search.addNode(node);
		
		node = new ApoMarioSearchNode(7, x - 3 * size, y - 4 * size);
		node.addConnectedNode(6);
		node.addConnectedNode(8);
		search.addNode(node);
		
		node = new ApoMarioSearchNode(8, x - 3 * size, y - 3 * size);
		node.addConnectedNode(7);
		node.addConnectedNode(9);
		node.addConnectedNode(10);
		search.addNode(node);
		
		node = new ApoMarioSearchNode(9, x - 1 * size, y - 3 * size);
		node.addConnectedNode(8);
		search.addNode(node);

		node = new ApoMarioSearchNode(10, x - 3 * size, y - 0 * size);
		node.addConnectedNode(8);
		node.addConnectedNode(11);
		node.addConnectedNode(12);
		search.addNode(node);
		
		node = new ApoMarioSearchNode(11, x - 0 * size, y - 0 * size);
		node.addConnectedNode(10);
		search.addNode(node);
		
		node = new ApoMarioSearchNode(12, x - 7 * size, y - 0 * size);
		node.addConnectedNode(10);
		node.addConnectedNode(13);
		search.addNode(node);
		
		node = new ApoMarioSearchNode(13, x - 7 * size, y - 1 * size);
		node.addConnectedNode(12);
		node.addConnectedNode(14);
		search.addNode(node);
		
		node = new ApoMarioSearchNode(14, x - 11 * size, y - 1 * size);
		node.addConnectedNode(16);
		node.addConnectedNode(13);
		node.addConnectedNode(15);
		search.addNode(node);
		
		node = new ApoMarioSearchNode(15, x - 11 * size, y - 0 * size);
		node.addConnectedNode(14);
		search.addNode(node);
		
		node = new ApoMarioSearchNode(16, x - 12 * size, y - 1 * size);
		node.addConnectedNode(14);
		node.addConnectedNode(17);
		search.addNode(node);
		
		node = new ApoMarioSearchNode(17, x - 12 * size, y - 2 * size);
		node.addConnectedNode(16);
		node.addConnectedNode(18);
		node.addConnectedNode(20);
		search.addNode(node);
		
		node = new ApoMarioSearchNode(18, x - 14 * size, y - 2 * size);
		node.addConnectedNode(17);
		search.addNode(node);
		
		node = new ApoMarioSearchNode(19, x - 13 * size, y - 4 * size);
		node.addConnectedNode(20);
		search.addNode(node);
		
		node = new ApoMarioSearchNode(20, x - 12 * size, y - 4 * size);
		node.addConnectedNode(19);
		node.addConnectedNode(17);
		node.addConnectedNode(21);
		search.addNode(node);
		
		node = new ApoMarioSearchNode(21, x - 12 * size, y - 5 * size);
		node.addConnectedNode(20);
		node.addConnectedNode(1);
		node.addConnectedNode(22);
		search.addNode(node);
		
		node = new ApoMarioSearchNode(22, x - 12 * size, y - 8 * size);
		node.addConnectedNode(21);
		search.addNode(node);
		
		ArrayList<ApoMarioSearchNode> way = search.searchWay(11, 0);
		for (int i = 0; i < way.size(); i++) {
			System.out.println(way.get(i).getNode());
		}
	}
	
}
