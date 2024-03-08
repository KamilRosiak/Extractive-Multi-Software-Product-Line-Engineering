package apoSlitherLink.level;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class ApoSlitherLinkLevelHelp {

	public static final Color COLOR_BACKGROUND = new Color(190, 230, 164);
	public static final Color COLOR_LINE = new Color(0, 102, 0);
	public static final Color COLOR_MOVE = new Color(180, 241, 0);
	
	private final Font FONT = new Font(Font.SANS_SERIF, Font.BOLD, 15);
	private final Font FONT_X = new Font(Font.SANS_SERIF, Font.BOLD, 20);
	
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	private final int value;
	
	private int curValue;
	
	private boolean[] edges;
	
	private boolean[] blockedEdges;

	private boolean[] movedEdges;
	
	private final int x, y, width;
	
	public ApoSlitherLinkLevelHelp(int x, int y, int value, int width) {
		this.x = x;
		this.y = y;
		this.value = value;
		this.width = width;
		
		this.init();
	}
	
	public void init() {
		this.curValue = 0;
		this.edges = new boolean[4];
		this.blockedEdges = new boolean[4];
		this.movedEdges = new boolean[4];
	}
	
	public final int getWidth() {
		return this.width;
	}

	public boolean changeEastEdge() {
		return this.changeEdge(ApoSlitherLinkLevelHelp.EAST);
	}
	
	public boolean changeWestEdge() {
		return this.changeEdge(ApoSlitherLinkLevelHelp.WEST);
	}
	
	public boolean changeNorthEdge() {
		return this.changeEdge(ApoSlitherLinkLevelHelp.NORTH);
	}
	
	public boolean changeSouthEdge() {
		return this.changeEdge(ApoSlitherLinkLevelHelp.SOUTH);
	}
	
	private boolean changeEdge(int edge) {
		if (!this.blockedEdges[edge]) {
			this.edges[edge] = !this.edges[edge];
			if (this.edges[edge]) {
				this.curValue += 1;
				return true;
			} else {
				this.curValue -= 1;
				return false;
			}
		}
		return false;
	}
	
	public void changeEastEdgeBlocked() {
		this.changeEdgeBlocked(ApoSlitherLinkLevelHelp.EAST);
	}
	
	public void changeWestEdgeBlocked() {
		this.changeEdgeBlocked(ApoSlitherLinkLevelHelp.WEST);
	}
	
	public void changeNorthEdgeBlocked() {
		this.changeEdgeBlocked(ApoSlitherLinkLevelHelp.NORTH);
	}
	
	public void changeSouthEdgeBlocked() {
		this.changeEdgeBlocked(ApoSlitherLinkLevelHelp.SOUTH);
	}
	
	private void changeEdgeBlocked(int edge) {
		if (this.edges[edge]) {
			this.edges[edge] = false;
			this.curValue -= 1;
		}
		this.blockedEdges[edge] = !this.blockedEdges[edge];
	}

	public void changeEastMoved(boolean bOver) {
		this.changeMoved(ApoSlitherLinkLevelHelp.EAST, bOver);
	}
	
	public void changeWestMoved(boolean bOver) {
		this.changeMoved(ApoSlitherLinkLevelHelp.WEST, bOver);
	}
	
	public void changeNorthMoved(boolean bOver) {
		this.changeMoved(ApoSlitherLinkLevelHelp.NORTH, bOver);
	}
	
	public void changeSouthMoved(boolean bOver) {
		this.changeMoved(ApoSlitherLinkLevelHelp.SOUTH, bOver);
	}
	
	private void changeMoved(int edge, boolean bOver) {
		for (int i = 0; i < 4; i++) {
			if (i == edge) {
				this.movedEdges[edge] = bOver;				
			} else {
				this.movedEdges[i] = false;
			}
		}
	}
	
	public final boolean[] getEdges() {
		return this.edges;
	}

	public final int getValue() {
		return this.value;
	}

	public final int getCurValue() {
		return this.curValue;
	}

	public final boolean isComplete() {
		if (this.value < 0) {
			return true;
		} else if (this.value == this.curValue) {
			return true;
		} else {
			return false;
		}
	}
	
	public final boolean hasAllEdges() {
		for (int i = 0; i < 4; i++) {
			if (!this.edges[i]) {
				return false;
			}
		}
		return true;
	}
	
	public void render(Graphics2D g, int changeX, int changeY, boolean bImage) {
		this.render(g, changeX, changeY, ApoSlitherLinkLevelHelp.COLOR_BACKGROUND, ApoSlitherLinkLevelHelp.COLOR_LINE, bImage);
	}
	
	public void render(Graphics2D g, int changeX, int changeY, Color background, Color line, boolean bImage) {
		if (bImage) {
			g.setColor(background.brighter().brighter());
			g.fillRect(this.x + changeX, this.y + changeY, this.width - 1, this.width - 1);
			g.setColor(background.darker().darker());
			g.fillRect(this.x + changeX + 3, this.y + changeY + 3, this.width - 4, this.width - 4);
		
			g.setColor(background);
			g.fillRoundRect(this.x + changeX + 2, this.y + changeY + 2, this.width - 4, this.width - 4, 4 ,4);

			g.setColor(line.brighter().brighter());
			g.drawRect(this.x + changeX, this.y + changeY, this.width - 1, this.width - 1);
		}
		
		String s;
		int w;
		int h;
		
		this.drawNumber(g, changeX, changeY, background, line, bImage);
		
		if (bImage) {
			return;
		}
		Color c = ApoSlitherLinkLevelHelp.COLOR_MOVE;
		Color c_alpha = new Color(c.getRed(), c.getGreen(), c.getBlue(), 170);
		g.setColor(line);
		Stroke stroke = g.getStroke();
		g.setStroke(new BasicStroke(5));
		if (this.blockedEdges[ApoSlitherLinkLevelHelp.NORTH]) {
			g.setFont(this.FONT_X);
			s = String.valueOf("X");
			w = g.getFontMetrics().stringWidth(s);
			h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
			g.drawString(s, this.x + changeX + this.width/2 - w/2, this.y + changeY + h/2);
		} else if (this.edges[ApoSlitherLinkLevelHelp.NORTH]) {
			int startX = this.x + changeX;
			int endX = this.x + changeX + this.width;
			if (this.movedEdges[ApoSlitherLinkLevelHelp.NORTH]) {
				g.setColor(line);
			}
			g.drawLine(startX, this.y + changeY - 1, endX, this.y + changeY - 1);
			if (this.movedEdges[ApoSlitherLinkLevelHelp.NORTH]) {
				g.setColor(c_alpha);
				g.drawLine(this.x + changeX, this.y + changeY - 1, this.x + changeX + this.width, this.y + changeY - 1);
			}
		} else if (this.movedEdges[ApoSlitherLinkLevelHelp.NORTH]) {
			g.setColor(c);
			g.drawLine(this.x + changeX, this.y + changeY - 1, this.x + changeX + this.width, this.y + changeY - 1);
		}
		g.setStroke(stroke);
		
		g.setColor(line);
		stroke = g.getStroke();
		g.setStroke(new BasicStroke(5));
		if (this.blockedEdges[ApoSlitherLinkLevelHelp.SOUTH]) {
			g.setFont(this.FONT_X);
			s = String.valueOf("X");
			w = g.getFontMetrics().stringWidth(s);
			h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
			g.drawString(s, this.x + changeX + this.width/2 - w/2, this.y + changeY + h/2 + this.width);
		} else if (this.edges[ApoSlitherLinkLevelHelp.SOUTH]) {
			int startX = this.x + changeX;
			int endX = this.x + changeX + this.width;
			g.drawLine(startX, this.y + changeY - 1 + this.width, endX, this.y + changeY - 1 + this.width);
			if (this.movedEdges[ApoSlitherLinkLevelHelp.SOUTH]) {
				g.setColor(c_alpha);
				g.drawLine(this.x + changeX, this.y + changeY - 1 + this.width, this.x + changeX + this.width, this.y + changeY - 1 + this.width);
			}
		} else if (this.movedEdges[ApoSlitherLinkLevelHelp.SOUTH]) {
			g.setColor(c);
			g.drawLine(this.x + changeX, this.y + changeY - 1 + this.width, this.x + changeX + this.width, this.y + changeY - 1 + this.width);
		}
		g.setStroke(stroke);

		g.setColor(line);
		stroke = g.getStroke();
		g.setStroke(new BasicStroke(5));
		if (this.blockedEdges[ApoSlitherLinkLevelHelp.EAST]) {
			g.setFont(this.FONT_X);
			s = String.valueOf("X");
			w = g.getFontMetrics().stringWidth(s);
			h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
			g.drawString(s, this.x + changeX + this.width - w/2, this.y + changeY + this.width/2 + h/2);
		} else if (this.edges[ApoSlitherLinkLevelHelp.EAST]) {
			int startY = this.y + changeY - 1;
			int endY = this.y + changeY - 1 + this.getWidth();
			g.drawLine(this.x + changeX + this.width - 1, startY, this.x + changeX + this.width - 1, endY);
			if (this.movedEdges[ApoSlitherLinkLevelHelp.EAST]) {
				g.setColor(c_alpha);
				g.drawLine(this.x + changeX + this.width - 1, startY, this.x + changeX + this.width - 1, endY);
			}
		} else if (this.movedEdges[ApoSlitherLinkLevelHelp.EAST]) {
			g.setColor(c);
			int startY = this.y + changeY - 1;
			int endY = this.y + changeY - 1 + this.getWidth();
			g.drawLine(this.x + changeX + this.width - 1, startY, this.x + changeX + this.width - 1, endY);
		}
		g.setStroke(stroke);
		
		g.setColor(line);
		stroke = g.getStroke();
		g.setStroke(new BasicStroke(5));
		if (this.blockedEdges[ApoSlitherLinkLevelHelp.WEST]) {
			g.setFont(this.FONT_X);
			s = String.valueOf("X");
			w = g.getFontMetrics().stringWidth(s);
			h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
			g.drawString(s, this.x + changeX - w/2, this.y + changeY + this.width/2 + h/2);
		} else if (this.edges[ApoSlitherLinkLevelHelp.WEST]) {
			int startY = this.y + changeY - 1;
			int endY = this.y + changeY - 1 + this.getWidth();
			g.drawLine(this.x + changeX - 1, startY, this.x + changeX - 1, endY);
			if (this.movedEdges[ApoSlitherLinkLevelHelp.WEST]) {
				g.setColor(c_alpha);
				g.drawLine(this.x + changeX - 1, startY, this.x + changeX - 1, endY);
			}
		} else if (this.movedEdges[ApoSlitherLinkLevelHelp.WEST]) {
			g.setColor(c);
			int startY = this.y + changeY - 1;
			int endY = this.y + changeY - 1 + this.getWidth();
			g.drawLine(this.x + changeX - 1, startY, this.x + changeX - 1, endY);
		}
		g.setStroke(stroke);
	}
	
	private void drawNumber(Graphics2D g, int changeX, int changeY, Color background, Color line, boolean bImage) {
		if (this.value >= 0) {
			if (this.curValue != this.value) {
				g.setColor(new Color(line.getRed(), line.getGreen(), line.getBlue(), 80));
				if ((!bImage) && (this.value != 0)) {
					return;
				}
			} else {
				g.setColor(background.brighter().brighter());
			}
			int ourW = 20;
			g.fillOval(this.x + changeX + this.getWidth()/2 - ourW/2, this.y + changeY + this.getWidth()/2 - ourW/2, ourW, ourW);
			g.setColor(line.brighter());
			g.drawOval(this.x + changeX + this.getWidth()/2 - ourW/2, this.y + changeY + this.getWidth()/2 - ourW/2, ourW, ourW);
			g.setColor(line);
			if (this.curValue != this.value) {
				g.setColor(line.darker());			
			}
			g.setFont(this.FONT);
			String s = String.valueOf(this.value);
			int w = g.getFontMetrics().stringWidth(s);
			int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
			g.drawString(s, this.x + changeX + this.width/2 - w/2, this.y + changeY + this.width/2 + h/2);
		}
	}
}
