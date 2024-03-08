package apoPongBeat.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import apoPongBeat.ApoPongBeatConstants;

public class ApoPongBeatOptions extends ApoPongBeatModel {

	public static final String MENU_STRING = "MENU";
	public static final String MENU = "options_menu";
	
	public ApoPongBeatOptions(ApoPongBeatPanel game) {
		super(game);
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		
	}
	
	@Override
	public void init() {
		this.getGame().initGameStats();
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoPongBeatOptions.MENU)) {
			this.getGame().setMenu();
		}
	}
	
	@Override
	public void mouseButtonReleased(int x, int y) {
	}
	
	@Override
	public void mouseMoved(int x, int y) {
	}
	
	@Override
	public void ballOnBorder(boolean left) {
	}
	
	@Override
	public void ballOnRacket(int points) {
	}

	@Override
	public void think(int delta) {
		
	}
	
	@Override
	public void render(Graphics2D g) {
		this.getGame().renderBackground(g);
		
		if (this.getGame().getMyFont() != null) {
			g.setColor(Color.WHITE);
			g.setFont(this.getGame().getMyFont());
			this.getGame().renderString(g, "OPTIONS", ApoPongBeatConstants.GAME_WIDTH/2, 35);
		}
		this.getGame().renderBorder(g, Color.YELLOW, -1, null, -1, null);
	}

}
