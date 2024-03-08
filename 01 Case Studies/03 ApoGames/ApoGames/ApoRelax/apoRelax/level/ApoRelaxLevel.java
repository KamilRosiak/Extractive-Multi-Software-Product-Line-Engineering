package apoRelax.level;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

import apoRelax.ApoRelaxComponent;
import apoRelax.ApoRelaxConstants;
import apoRelax.entity.ApoRelaxTile;


public class ApoRelaxLevel {
	
	public static final int MAX = 400;
	
	private final String levelName;
	private final String description;
	
	private final ApoRelaxTile[] recs;
	
	private boolean bMoving,
					bCorrect;
	
	private int chooseRec,
				mouseOverRec;
	
	private final int width,
					  height;
	
	public ApoRelaxLevel(final String levelname, final String description, final ApoRelaxTile[] recs, final int width, final int height) {
		this.levelName = levelname;
		this.description = description;
		this.recs = recs;
		this.width = width;
		this.height = height;
		this.mouseOverRec = -1;
		this.chooseRec = -1;
		
		this.restart();
	}
	
	public final String getLevelName() {
		return this.levelName;
	}
	
	public boolean mouseMoved(int x, int y, ApoRelaxComponent component) {
		if (this.bMoving) {
			return false;
		}
		x = x - this.getXLevel(10);
		y = y - this.getYLevel(15);
		
		for (int i = 0; i < this.recs.length; i++) {
			if (this.recs[i].intersects(x, y)) {
				if (this.mouseOverRec != i) {
					this.mouseOverRec = i;
					component.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					return true;
				} else {
					return false;
				}
			} else if (i + 1 == this.recs.length) {
				if (this.mouseOverRec != -1) {
					this.mouseOverRec = -1;
					component.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					return true;
				}
			}
		}
		component.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		return false;
	}
	
	public int mouseReleased(int x, int y, boolean bRight) {
		if (this.bMoving) {
			return 0;
		}
		
		x = x - this.getXLevel(10);
		y = y - this.getYLevel(15);
		if (bRight) {
			this.chooseRec = -1;
		} else {
			if (this.chooseRec < 0) {
				for (int i = 0; i < this.recs.length; i++) {
					if (this.recs[i].intersects(x, y)) {
						this.recs[i].setChoosen(true);
						this.chooseRec = i;
						return 2;
					}
				}
			} else {
				for (int i = 0; i < this.recs.length; i++) {
					if (this.recs[i].intersects(x, y)) {
						this.recs[i].setChoosen(false);
						if (this.chooseRec != i) {
							this.changeTwoTiles(i, this.chooseRec, true);
							return 1;
						} else {
							this.chooseRec = -1;
							return 2;
						}
					} else {
						
					}
					if (i + 1 >= this.recs.length) {
						if (this.chooseRec >= 0) {
							this.recs[this.chooseRec].setChoosen(false);
							this.chooseRec = -1;
							return 2;
						}
					}
				}
			}
		}
		return 0;
	}
	
	public void changeTwoTiles(int i, int choose, boolean bWithMove) {
		int curNumber = this.recs[i].getCurNumber();
		BufferedImage iNew = this.recs[curNumber].getIBackground();
		int otherCurNumber = this.recs[choose].getCurNumber();
		BufferedImage iNewOther = this.recs[otherCurNumber].getIBackground();
		this.recs[i].changeImage(this.recs[choose], iNewOther, otherCurNumber, bWithMove);
		this.recs[choose].changeImage(this.recs[i], iNew, curNumber, bWithMove);
		this.chooseRec = -1;
		this.mouseOverRec = -1;
	}

	public void restart() {
	}
	
	public boolean think(int delta) {
		this.bMoving = false;
		this.bCorrect = true;
		for (int i = 0; i < this.recs.length; i++) {
			this.recs[i].think(delta);
			if (this.recs[i].isMoving()) {
				this.bMoving = true;
			}
			if (!this.recs[i].isCorrect()) {
				this.bCorrect = false;
			}
		}
		return this.bMoving;
	}

	public boolean isCorrect() {
		if (this.bMoving) {
			return false;
		}
		return this.bCorrect;
	}
	
	public int getXLevel(int changeX) {
		return changeX + 25 + (ApoRelaxLevel.MAX - this.width)/2;
	}
	
	public int getYLevel(int changeY) {
		return changeY + 25 + (ApoRelaxLevel.MAX - this.height)/2;
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		g.setColor(Color.BLACK);
		int max = 450;
		if (this.levelName != null) {
			g.setFont(ApoRelaxConstants.FONT_LEVELNAME);
			int w = g.getFontMetrics().stringWidth(this.levelName);
			g.drawString(this.levelName, changeX + max/2 - w/2, changeY + 20);
		}
		if (this.description != null) {
			g.setFont(ApoRelaxConstants.FONT_DESCRIPTIONS);
			int w = g.getFontMetrics().stringWidth(this.description);
			while (w > max - 10) {
				Font f = g.getFont();
				g.setFont(new Font(f.getName(), f.getStyle(), f.getSize() - 1));
				w = g.getFontMetrics().stringWidth(this.description);
			}
			g.drawString(this.description, changeX + max/2 - w/2, changeY + max - 5);
		}
		
		if (this.recs != null) {
			int x = this.getXLevel(changeX);
			int y = this.getYLevel(changeY);

			Stroke stroke = g.getStroke();
			g.setColor(Color.BLACK);
			g.setStroke(new BasicStroke(3));
			g.drawRoundRect(x - 2, y - 2, this.width + 2, this.height + 2, 10, 10);
			g.setStroke(stroke);
			
			for (int i = 0; i < this.recs.length; i++) {
				this.recs[i].render(g, x, y);
				
				if (i == this.mouseOverRec) {
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.RED);
					g.drawRect((int)(this.recs[i].getX() + x), (int)(this.recs[i].getY() + y), (int)(this.recs[i].getWidth() - 1), (int)(this.recs[i].getHeight() - 1));
					g.setStroke(stroke);
				}
			}
			
			for (int i = 0; i < this.recs.length; i++) {
				if (this.recs[i].isMoving()) {
					this.recs[i].renderImage(g, x, y);
				}
			}
		}
	}
}
