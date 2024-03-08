package net.apogames.apoclock.highscore;

import java.util.ArrayList;

import android.util.Log;

import net.apogames.apoclock.game.ApoClockPanel;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;

public class ApoClockHighscoreLocal {

	private ArrayList<Integer> points;
	private ArrayList<Integer> clocks;
	private ArrayList<Integer> highscore;
	
	private final ApoClockPanel panel;
	
	public ApoClockHighscoreLocal(final ApoClockPanel panel) {
		this.panel = panel;
		
		this.points = new ArrayList<Integer>();
		this.clocks = new ArrayList<Integer>();
		this.highscore = new ArrayList<Integer>();
	}
	
	public void sortByPoints() {
		this.highscore.clear();
		this.highscore.add(0);
		for (int i = 1; i < this.points.size(); i++) {
			float points = this.points.get(i);
			boolean bAdd = false;
			for (int k = 0; k < this.highscore.size(); k++) {
				float sortPoints = this.points.get(this.highscore.get(k));
				if (points > sortPoints) {
					this.highscore.add(k, i);
					bAdd = true;
					break;
				}
			}
			if (!bAdd) {
				this.highscore.add(i);
			}
		}
	}
	
	public void addNextValues(final int points, final int clocks) {
		this.points.add(points);
		this.clocks.add(clocks);
		
		this.sortByPoints();
	}
	
	public void setHighscoreFromString(final String s) {
		if ((s != null) && (s.length() > 0)) {
			this.points.clear();
			this.clocks.clear();
			String curString = s;
			Log.d("curString",curString);
			for (int i = 0; i < 5; i++) {
				int next = curString.indexOf(";");
				if (next < 0) {
					break;
				}
				this.points.add(Integer.valueOf(curString.substring(0, next)));
				Log.d("curString",curString);
				curString = curString.substring(curString.indexOf(";") + 1);
				Log.d("curString",curString);
				next = curString.indexOf(";");
				this.clocks.add(Integer.valueOf(curString.substring(0, next)));
				curString = curString.substring(curString.indexOf(";") + 1);
			}
			this.sortByPoints();
		}
	}
	
	public String getString() {
		String s = "";
		for (int i = 0; i < 5 && i < this.highscore.size(); i++) {
			s += this.points.get(this.highscore.get(i))+";";
			s += this.clocks.get(this.highscore.get(i))+";";
		}
		return s;
	}
	
	public void drawHighscore(final BitsGLGraphics g) {
		if (this.points.size() > 0) {
			g.setColor(128f/255f, 128f/255f, 128f/255f, 1.0f);
			g.fillRect(250, 190, 220, 240);
			
			g.setColor(48f/255f, 48f/255f, 48f/255f, 1.0f);
			g.drawRect(250, 190, 220, 240);
			
			String s = "local";
			this.panel.drawString(g, s, (int)(360 - ApoClockPanel.font.getLength(s)/2), 195, ApoClockPanel.font);
			this.panel.drawString(g, "name", 260, 225, ApoClockPanel.font);
			this.panel.drawString(g, "points", 400, 225, ApoClockPanel.font);
			for (int i = 0; i < 5 && i < this.points.size(); i++) {
				this.panel.drawString(g, (i+1) + " "+this.panel.getTextfield().getCurString(), 255, 270 + 30 * i, ApoClockPanel.game_font);
				s = String.valueOf(this.points.get(this.highscore.get(i)));
				this.panel.drawString(g, s, (int)(460 - ApoClockPanel.font.getLength(s)), 270 + 30 * i, ApoClockPanel.font);
			}
		}
	}
	
}
