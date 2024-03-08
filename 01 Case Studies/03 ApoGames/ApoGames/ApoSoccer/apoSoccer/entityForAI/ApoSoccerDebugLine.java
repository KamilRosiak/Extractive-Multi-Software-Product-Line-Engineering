package apoSoccer.entityForAI;

import java.awt.Color;

import apoSoccer.ApoSoccerConstants;

/**
 * Klasse, um eine Linie zu zeichnen im debugModus
 * @author Dirk Aporius
 *
 */
public class ApoSoccerDebugLine {
	
	private final int MAX_TIME = 10000;
	private final int MAX_WIDTH = 5;
	private final int MAX_WIDTH_CIRCLE = ApoSoccerConstants.FIELD_WIDTH * 2;
	
	private int startX;
	private int endX;
	private int startY;
	private int endY;
	
	private int time;
	
	private Color color;
	
	private int width;
	
	private boolean bCircle;
	
	public ApoSoccerDebugLine(int startX, int startY, int endX, int endY, int time, Color color) {
		this(startX, startY, endX, endY, time, color, 1);
	}

	public ApoSoccerDebugLine(int startX, int startY, int endX, int endY, int time, Color color, int width) {
		this(startX, startY, endX, endY, time, color, width, false);
	}
	
	public ApoSoccerDebugLine(int startX, int startY, int endX, int endY, int time, Color color, int width, boolean bCircle) {
		if (startX < 0) {
			startX = 0;
		} else if (startX > ApoSoccerConstants.FIELD_WIDTH) {
			startX = ApoSoccerConstants.FIELD_WIDTH;
		}
		if (endX < 0) {
			endX = 0;
		} else if (endX > ApoSoccerConstants.FIELD_WIDTH) {
			endX = ApoSoccerConstants.FIELD_WIDTH;
		}
		
		if (startY < 0) {
			startY = 0;
		} else if (startY > ApoSoccerConstants.FIELD_HEIGHT) {
			startY = ApoSoccerConstants.FIELD_HEIGHT;
		}
		if (endY < 0) {
			endY = 0;
		} else if (endY > ApoSoccerConstants.FIELD_HEIGHT) {
			endY = ApoSoccerConstants.FIELD_HEIGHT;
		}
		this.setBCircle(bCircle);
		
		if (time <= 0) {
			time = 1000;
		} else if (time > this.MAX_TIME) {
			time = this.MAX_TIME;
		}
		
		if (width <= 0) {
			width = 1;
		} else if (this.isBCircle()) {
			if (width >= this.MAX_WIDTH_CIRCLE) {
				width = this.MAX_WIDTH_CIRCLE;
			}
		} else if (width >= this.MAX_WIDTH) {
			width = this.MAX_WIDTH;
		}
		
		this.setStartX(startX);
		this.setStartY(startY);
		this.setEndX(endX);
		this.setEndY(endY);
		this.setTime(time);
		this.setColor(color);
		this.setWidth(width);
	}

	public boolean isBCircle() {
		return this.bCircle;
	}

	public void setBCircle(boolean bCircle) {
		this.bCircle = bCircle;
	}

	public int getStartX() {
		return this.startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getEndX() {
		return this.endX;
	}

	public void setEndX(int endX) {
		this.endX = endX;
	}

	public int getStartY() {
		return this.startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public int getEndY() {
		return this.endY;
	}

	public void setEndY(int endY) {
		this.endY = endY;
	}

	public int getTime() {
		return this.time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getWidth() {
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
}
