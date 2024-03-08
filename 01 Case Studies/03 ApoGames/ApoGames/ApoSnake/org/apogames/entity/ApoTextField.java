package org.apogames.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class ApoTextField extends ApoEntity {

	private final int TIME_LINE = 400;
	private String curString;
	private int position;
	private int time;
	private boolean bLineOn;
	private int maxLength;
	private Font font;
	
	public ApoTextField(float x, float y, float width, float height) {
		super(null, x, y, width, height);
	}

	public void init() {
		super.init();
		this.curString = "";
		this.position = this.curString.length();
		this.bLineOn = true;
		this.time = 0;
		if (this.maxLength <= 0) {
			this.maxLength = 20;
		}
		if (this.font == null) {
			this.font = new Font(Font.DIALOG, Font.BOLD, 15);
		}
	}

	public Font getFont() {
		return this.font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public int getMaxLength() {
		return this.maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public String getCurString() {
		return this.curString;
	}

	public void setCurString(String curString) {
		this.curString = curString;
	}

	public int getPosition() {
		return this.position;
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
		if (curString.length() > 0 && position != 0) {
			this.curString = (new StringBuilder(String.valueOf(this.curString.substring(0, this.position - 1)))).append(this.curString.substring(this.position, this.curString.length())).toString();
			setPosition(getPosition() - 1);
		}
		showLine();
	}

	private void deleteTextDelete() {
		if (this.curString.length() != this.position) {
			this.curString = (new StringBuilder(String.valueOf(this.curString.substring(0, getPosition())))).append(this.curString.substring(getPosition() + 1, this.curString.length())).toString();
		}
		showLine();
	}

	public void addCharacter(int button, char character) {
		if (isBSelect()) {
			if (button == 8) {
				deleteTextBackspace();
			} else if (button == 127) {
				deleteTextDelete();
			} else if (button == 37) {
				setPosition(getPosition() - 1);
			} else if (button == 39) {
				setPosition(getPosition() + 1);
			} else if (curString.length() <= 20 && character >= '!' && character <= '~') {
				this.curString = (new StringBuilder(String.valueOf(this.curString.substring(0, this.position)))).append(character).append(this.curString.substring(this.position, this.curString.length())).toString();
				this.position++;
				showLine();
			}
		}
	}

	private void showLine() {
		if (isBSelect()) {
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
		g.setColor(Color.white);
		g.fillRect((int) (getX() + (float) changeX), (int) (getY() + (float) changeY), (int) getWidth(), (int) getHeight());
		if (!isBSelect()) {
			g.setColor(Color.black);
		} else {
			g.setColor(Color.red);
		}
		g.drawRect((int) (getX() + (float) changeX), (int) (getY() + (float) changeY), (int) getWidth(), (int) getHeight());
		g.setColor(Color.black);
		if (this.curString != null) {
			g.setFont(this.font);
			g.drawString(this.curString, (int) (getX() + 5F + (float) changeX), (int) (getY() + this.getHeight()/2 + 10 + (float) changeY));
			if (this.isBSelect() && this.bLineOn) {
				int w = g.getFontMetrics().stringWidth(this.curString.substring(0, this.position));
				g.drawLine((int)(getX() + 5F + w + changeX), (int)(getY() + 5F + changeY), (int)(getX() + 5F + w + changeX), (int)(getY() + this.getHeight()/2 + 10 + changeY));
			}
		}
	}
}
