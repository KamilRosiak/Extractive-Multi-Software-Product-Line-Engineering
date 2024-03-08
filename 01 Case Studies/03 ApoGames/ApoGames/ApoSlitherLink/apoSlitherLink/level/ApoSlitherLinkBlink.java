package apoSlitherLink.level;

import java.awt.Point;
import java.util.ArrayList;

public class ApoSlitherLinkBlink {

	private ArrayList<Point> linePoints;
	
	private boolean bLine;
	
	public ApoSlitherLinkBlink() {
		this.linePoints = new ArrayList<Point>();
		this.bLine = false;
	}
	
	public boolean isLine() {
		return this.bLine;
	}

	public void setLine(boolean line) {
		this.bLine = line;
	}

	public boolean add(int x, int y) {
		if (this.isIn(x, y) < 0) {
			this.linePoints.add(new Point(x, y));
		} else {
			return false;
		}
		return true;
	}
	
	public boolean remove(int x, int y) {
		int value = this.isIn(x, y);
		if (value > 0) {
			this.linePoints.remove(value);
			return true;
		}
		return false;
	}
	
	public int isIn(int x, int y) {
		for (int i = 0; i < this.linePoints.size(); i++) {
			if ((this.linePoints.get(i).x == x) &&
				(this.linePoints.get(i).y == y)) {
				return i;
			}
		}
		return -1;
	}

	public ArrayList<Point> getLinePoints() {
		return this.linePoints;
	}
	
}
