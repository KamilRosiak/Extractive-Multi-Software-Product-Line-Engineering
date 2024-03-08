package org.apogames.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;

import org.apogames.entity.ApoButton;

public class ApoNewTextfield extends ApoButton {

	private final Font FONT = new Font(Font.SANS_SERIF, Font.BOLD, 20);
	
	private final int MAX_LENGTH = 40;
	
	private final int TIME_LINE = 700;
	
	private String curString;

	private int position;
	
	private int time;
	
	private boolean bLineOn;
	
	private Font myFont;
	
	private boolean bEnabled;
	
	private int maxLength;

	private Graphics2D myGraphics;
	
	private Point selectedPosition;
	
	public ApoNewTextfield(float x, float y, float width, float height, Font myFont) {
		super(null, (int)x, (int)y, (int)width, (int)height, "");
		
		this.myFont = myFont;
		this.bEnabled = true;
	}

	public void init() {
		super.init();
		this.curString = "icarus";
		this.position = this.curString.length();
		this.bLineOn = true;
		this.bEnabled = true;
		this.time = 0;
		if (this.myFont == null) {
			this.myFont = this.FONT;
		}
		if (this.maxLength <= 0) {
			this.maxLength = this.MAX_LENGTH;
		}
		this.selectedPosition = new Point(-1, -1);
	}

	public int getMaxLength() {
		return this.maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
		if (this.maxLength <= 0) {
			this.maxLength = this.MAX_LENGTH;
		}
	}

	public boolean isBEnabled() {
		return this.bEnabled;
	}

	public void setBEnabled(boolean bEnabled) {
		this.bEnabled = bEnabled;
	}

	public Font getFont() {
		return this.myFont;
	}

	public void setFont(Font myFont) {
		this.myFont = myFont;
	}

	public boolean mouseDragged(int x, int y) {
		if (!this.bEnabled) {
			return false;
		}
		if (this.selectedPosition.x < 0) {
			this.selectedPosition.x = this.getPosition();
		}
		if (this.selectedPosition.x >= 0) {
			if (this.getRec().contains(x, y)) {
				int position = this.getThisPosition(x, y);
				if (position != -1) {
					this.setSelectedPosition(position);
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean mousePressed(int x, int y) {
		if (!this.bEnabled) {
			return false;
		}
		if (this.getPressed(x, y)) {
			if (this.selectedPosition.y >= 0) {
				this.selectedPosition = new Point(-1, -1);
			}
			int position = this.getThisPosition(x, y);
			if (position != -1) {
				this.selectedPosition.x = position;
			}
			return true;
		} else {
			this.selectedPosition = new Point(-1, -1);
		}
		return false;
	}
	
	public boolean mouseReleased(int x, int y) {
		if (!this.bEnabled) {
			return false;
		}
		if (super.getReleased(x, y)) {
			int position = this.getThisPosition(x, y);
			if (position != -1) {
				this.setPosition(position);
				this.setSelectedPosition(position);
			}
			return true;
		} else {
			this.selectedPosition = new Point(-1, -1);
		}
		return false;
	}
	
	public void nextSelectedPosition(int position) {
		if (this.selectedPosition.x < 0) {
			this.selectedPosition.x = this.getPosition();
		}
		if (position > this.curString.length()) {
			position = this.curString.length();
		} else if (position < 0) {
			position = 0;
		}
		this.setSelectedPosition(position);
	}
	
	private void setSelectedPosition(int position) {
		if (this.selectedPosition.x != position) {
			this.setPosition(position);
			if (this.selectedPosition.x > position) {
				if (this.selectedPosition.y < this.selectedPosition.x) {
					this.selectedPosition.y = this.selectedPosition.x;
				}
				this.selectedPosition.x = position;
			} else {
				this.selectedPosition.y = this.getPosition();
			}
		}
	}
	
	private int getThisPosition(int x, int y) {
		if (this.myGraphics != null) {
			this.myGraphics.setFont(this.myFont);
			String s = this.curString;
			int w = this.myGraphics.getFontMetrics().stringWidth(s);
			if (x > w + 5 + this.getX()) {
				return s.length();
			} else if (x < this.getX() + 5) {
				return 0;
			} else {
				for (int i = 0; i < this.curString.length(); i++) {
					String old = this.curString.substring(0, i);
					String next = this.curString.substring(0, i + 1);
					int wOld = this.myGraphics.getFontMetrics().stringWidth(old);
					int wNext = this.myGraphics.getFontMetrics().stringWidth(next);
					if ((x > wOld + 5 + this.getX()) && (x < wNext + 5 + this.getX())) {
						return i;
					}
				}
			}
		}
		return -1;
	}

	public String getCurString() {
		return this.curString;
	}

	public String getSelectedString() {
		String selected = null;
		if ((this.selectedPosition.x >= 0) && (this.selectedPosition.y >= 0)) {
			return this.curString.substring(this.selectedPosition.x, this.selectedPosition.y);
		}
		return selected;
	}
	
	public void deleteSelectedString() {
		String s = this.getSelectedString();
		if ((s != null) && (s.length() > 0)) {
			this.setPosition(this.selectedPosition.x);
			this.curString = this.curString.substring(0, this.selectedPosition.x) + this.curString.substring(this.selectedPosition.y);
			this.selectedPosition = new Point(-1, -1);
		}
	}
	
	public void removeCurStringAndSetCurString(String curString) {
		if (!curString.equals(this.curString)) {
			this.position = 0;
			this.setCurString(curString);
		}
	}
	
	public void setCurString(String curString) {
		this.curString = curString;
	}

	public int getPosition() {
		return this.position;
	}
	
	public void setSelect() {
		if ((this.curString != null) && (this.curString.length() > 0)) {
			this.selectedPosition = new Point(0, this.curString.length());
		}
	}

	public void setPosition(int position) {
		this.position = position;
		if (this.position < 0) {
			this.position = 0;
		} else if (this.position > this.curString.length()) {
			this.position = this.curString.length();
		}
		this.showLine();
	}

	private void deleteTextBackspace() {
		if (!this.bEnabled) {
			return;
		}
		if ((this.curString.length() > 0) && (this.position != 0)) {
			this.curString = this.curString.substring(0, position - 1) + this.curString.substring(this.position, this.curString.length());
			this.setPosition(this.getPosition() - 1);
		}
		this.showLine();
	}

	private void deleteTextDelete() {
		if (!this.bEnabled) {
			return;
		}
		if (this.curString.length() != this.position) {
			this.curString = this.curString.substring(0, this.getPosition()) + this.curString.substring(this.getPosition() + 1, this.curString.length());
		}
		this.showLine();
	}

	public void addCharacter(int button, char character) {
		if (!this.bEnabled) {
			return;
		}
		if (button == 0) {
			return;
		}
		if (this.isBSelect()) {
			if (button == KeyEvent.VK_BACK_SPACE) {
				String s = this.getSelectedString();
				if ((s != null) && (s.length() > 0)) {
					this.deleteSelectedString();
				} else {
					this.deleteTextBackspace();
				}
				this.selectedPosition = new Point(-1, -1);
			} else if (button == KeyEvent.VK_DELETE) {
				String s = this.getSelectedString();
				if ((s != null) && (s.length() > 0)) {
					this.deleteSelectedString();
				} else {
					this.deleteTextDelete();
				}
				this.selectedPosition = new Point(-1, -1);
			} else if (button == KeyEvent.VK_LEFT) {
				this.setPosition(this.getPosition() - 1);
				this.selectedPosition = new Point(-1, -1);
			} else if (button == KeyEvent.VK_RIGHT) {
				this.setPosition(this.getPosition() + 1);
				this.selectedPosition = new Point(-1, -1);
			} else if (this.curString.length() <= this.maxLength) {
				if ((character >= 33) && (character <= 126)) {
					this.deleteSelectedString();
					this.curString = this.curString.substring(0, this.position) + character + this.curString.substring(this.position, this.curString.length());
					this.position	+= 1;
					this.showLine();
					this.selectedPosition = new Point(-1, -1);
				}
			}
		} else {
			this.selectedPosition = new Point(-1, -1);			
		}
	}
	
	private void showLine() {
		if (this.isBSelect()) {
			this.bLineOn = true;
			this.time = 0;
		}
	}

	public void think(int delta) {
		this.time += delta;
		if (this.time > this.TIME_LINE) {
			this.time = 0;
			this.bLineOn = !this.bLineOn;
		}
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		if (this.myGraphics == null) {
			this.myGraphics = g;
		}
		if (this.bEnabled) {
			g.setColor(Color.WHITE);
		} else {
			g.setColor(Color.GRAY);
		}
		g.fillRect((int) (this.getX() + changeX), (int) (this.getY() + changeY), (int) (this.getWidth()), (int) (this.getHeight()));
		if ((this.isBOver()) && (this.bEnabled)) {
			g.setColor(Color.YELLOW);			
		} else if ((!this.isBSelect()) || (!this.bEnabled)) {
			g.setColor(Color.BLACK);
		} else {
			g.setColor(Color.RED);
		}
		g.drawRect((int) (this.getX() + changeX), (int) (this.getY() + changeY), (int) (this.getWidth()), (int) (this.getHeight()));

		if (this.curString != null) {
			g.setFont(this.myFont);
			if (this.isBSelect()) {
				if ((this.selectedPosition.x > -1) && (this.selectedPosition.y > -1)) {
					g.setColor(Color.GRAY);
					int x = g.getFontMetrics().stringWidth(this.curString.substring(0, this.selectedPosition.x)) + (int)(this.getX() + 5 + changeX);
					int width = g.getFontMetrics().stringWidth(this.curString.substring(this.selectedPosition.x, this.selectedPosition.y));
					g.drawRect((int)(x), (int) (this.getY() + changeY + 1), (int) (width), (int) (this.getHeight() - 2));
				}
			}
			g.setColor(Color.BLACK);

			int height = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
			g.drawString(this.curString, (int) (this.getX() + 5 + changeX), (int)(this.getY() + height/2 + this.getHeight()/2 + changeY));
			if ((this.isBSelect()) && (this.bLineOn) && (this.bEnabled)) {
				try {
					int w = g.getFontMetrics().stringWidth(this.curString.substring(0, this.position));
					g.drawLine((int) (this.getX() + 5 + w + changeX), (int) (this.getY() + this.getHeight()/2 - height/2 + changeY), (int) (this.getX() + 5 + w + changeX), (int) (this.getY() + height/2 + this.getHeight()/2 + changeY));
				} catch (StringIndexOutOfBoundsException ex) {
				}
			}
		}
	}

}
