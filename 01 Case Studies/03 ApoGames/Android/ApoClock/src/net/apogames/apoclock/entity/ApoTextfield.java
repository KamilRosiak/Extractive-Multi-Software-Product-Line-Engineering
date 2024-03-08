package net.apogames.apoclock.entity;

import net.apogames.apoclock.game.ApoClockPanel;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;

public class ApoTextfield extends ApoEntity {

	private final int SHOW_TIME = 500;
	
	private final int MAX_TEXT = 10;
	
	private boolean bShow = false;
	
	private int curTime = 0;
	
	private String curString;
	
	public ApoTextfield(float x, float y, float width, float height) {
		super(null, x, y, width, height);
		
		this.curString = "you";
	}
	
	public String getCurString() {
		return this.curString;
	}
	
	public void setCurString(String curString) {
		this.curString = curString;
		if (this.curString.length() >= this.MAX_TEXT) {
			this.curString = this.curString.substring(0, this.MAX_TEXT);
		}
	}

	public void addValue(String s) {
		if (this.curString.length() < this.MAX_TEXT) {
			this.curString += s;
		}
	}
	
	public void deleteValue() {
		if (this.curString.length() > 0) {
			this.curString = this.curString.substring(0, this.curString.length() - 1);
		}
	}

	/**
	 * setzt den boolean Wert ob ausgewählt oder nicht auf den übergebenen
	 * 
	 * @param bSelect
	 */
	public void setSelect(boolean bSelect) {
		if (bSelect) {
			if (this.curString.equals("you")) {
				this.curString = "";
			}
		} else {
			if (this.curString.length() == 0) {
				this.curString = "you";
			}
		}
		super.setSelect(bSelect);
	}
	
	/**
	 * Methode, die immer waehrend der update Methode aufgerufen wird
	 * 
	 * @param delta: Zeit, die seit dem letzten Aufruf vergangen ist
	 */
	public void think(int delta) {
		if (super.isSelect()) {
			this.curTime += delta;
			if (this.curTime >= this.SHOW_TIME) {
				this.curTime -= this.SHOW_TIME;
				this.bShow = !this.bShow;
			}
		}
	}

	/**
	 * malt das Objekt
	 * @param g
	 */
	public void render(BitsGLGraphics g, int x, int y) {
		if (super.isVisible()) {
			g.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			g.fillRect(this.getX() + x, this.getY() + y, this.getWidth(), this.getHeight());
			
			g.setColor(0.0f, 0.0f, 0.0f, 1.0f);
			if (this.isSelect()) {
				g.setColor(1.0f, 0.0f, 0.0f, 1.0f);	
			}
			g.drawRect(this.getX() + x, this.getY() + y, this.getWidth(), this.getHeight());
			
			g.setFont(ApoClockPanel.game_font);
			g.setColor(0.0f, 0.0f, 0.0f, 1.0f);
			int w = (int)(ApoClockPanel.game_font.getLength(this.curString));
			g.drawText(this.curString, this.getX() + x + 5, this.getY() + y + this.getHeight()/2 - ApoClockPanel.game_font.mCharCellHeight/2);
			
			if ((super.isSelect()) && (this.bShow)) {
				g.drawLine(this.getX() + x + 5 + w + 1, this.getY() + y + 3, this.getX() + x + 5 + w + 1, this.getY() + y + this.getHeight() - 3);
			}
		}
	}
}
