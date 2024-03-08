package apoSlitherLink.level;

import java.awt.Point;
import java.util.ArrayList;

public class ApoSlitherLinkLine {

	private final int x;
	private final int y;
	
	private final ArrayList<Point> connected;
	
	private boolean bVisited;
	
	public ApoSlitherLinkLine(final int x, final int y) {
		this.x = x;
		this.y = y;
		
		this.bVisited = false;
		
		this.connected = new ArrayList<Point>();
	}
	
	public boolean add(int x, int y) {
		for (int i = 0; i < this.connected.size(); i++) {
			if ((this.connected.get(i).x == x) &&
				(this.connected.get(i).y == y)) {
				return false;
			}
		}
		this.connected.add(new Point(x, y));
		return true;
	}
	
	public boolean remove(int x, int y) {
		for (int i = 0; i < this.connected.size(); i++) {
			if ((this.connected.get(i).x == x) &&
				(this.connected.get(i).y == y)) {
				this.connected.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public int isIn(int x, int y) {
		for (int i = 0; i < this.connected.size(); i++) {
			if ((this.connected.get(i).x == x) &&
				(this.connected.get(i).y == y)) {
				return i;
			}
		}
		return -1;
	}

	public final int getX() {
		return this.x;
	}

	public final int getY() {
		return this.y;
	}

	public final ArrayList<Point> getConnected() {
		return this.connected;
	}
	
	public boolean isBVisited() {
		return this.bVisited;
	}

	public void setBVisited(boolean visited) {
		this.bVisited = visited;
	}

	public final boolean hasCorrectEdges() {
		if (this.connected.size() == 2) {
			return true;
		}
		if (this.connected.size() == 0) {
			return true;
		}
		return false;
	}
}
