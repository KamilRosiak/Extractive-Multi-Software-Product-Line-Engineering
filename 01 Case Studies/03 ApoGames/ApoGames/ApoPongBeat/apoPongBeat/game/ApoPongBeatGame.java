package apoPongBeat.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import apoPongBeat.ApoPongBeatConstants;

public class ApoPongBeatGame extends ApoPongBeatModel {
	
	public static final String MENU_STRING = "MENU";
	public static final String MENU = "game_menu";
	
	private String levelString;
	
	public ApoPongBeatGame(ApoPongBeatPanel game) {
		super(game);
	}

	@Override
	public void init() {
		this.getGame().initGameStats();
		
		this.levelString = "FIRST";
	}
	
	public String getLevelString() {
		return this.levelString;
	}

	public void setLevelString(String levelString) {
		this.levelString = levelString;
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		
	}
	
	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoPongBeatGame.MENU)) {
			this.getGame().setMenu();
		}
	}
	
	@Override
	public void mouseButtonReleased(int x, int y) {
	}
	
	@Override
	public void mouseMoved(int x, int y) {
		this.getGame().moveRacket(x, y);
	}
	
	@Override
	public void ballOnBorder(boolean left) {
		this.getGame().ballOnBorderGame(left);
	}
	
	@Override
	public void ballOnRacket(int points) {
		this.getGame().ballOnRacketGame(points);
	}

	@Override
	public void think(int delta) {
		this.getGame().thinkBalls(delta);
		this.getGame().thinkParticles(delta);
	}
	
	@Override
	public void render(Graphics2D g) {
		this.getGame().renderBackground(g);
		
		if (this.getGame().getMyFont() != null) {
			g.setColor(Color.WHITE);
			g.setFont(this.getGame().getMyFont());
			this.getGame().renderString(g, this.levelString, ApoPongBeatConstants.GAME_WIDTH/2, 40);
		}
		this.getGame().renderStats(g);
		
		this.getGame().renderBalls(g);
		this.getGame().getRacket().render(g, 0, 0);
	}

}
