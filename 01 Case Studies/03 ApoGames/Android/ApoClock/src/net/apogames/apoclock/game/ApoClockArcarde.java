package net.apogames.apoclock.game;

import net.apogames.apoclock.ApoClockConstants;
import net.apogames.apoclock.ApoClockModel;
import net.apogames.apoclock.highscore.ApoClockHighscore;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;
import net.gliblybits.bitsengine.input.BitsInput;
import net.gliblybits.bitsengine.input.BitsKeyEvent;

public class ApoClockArcarde extends ApoClockModel {

	public static final String BACK = "back";
	public static final String START = "start";
	public static final String TITLE = "ApoClock - Aracarde";
	public static final String POINTS = "Points";
	
	private int points = -1;
	
	private ApoClockHighscore highscore;
	
	private int textX = 0;
	
	public ApoClockArcarde(ApoClockPanel game) {
		super(game);
	}

	@Override
	public void init() {
		this.getStringWidth().put(ApoClockArcarde.BACK, (int)(ApoClockPanel.font.getLength(ApoClockArcarde.BACK)));
		this.getStringWidth().put(ApoClockArcarde.START, (int)(ApoClockPanel.font.getLength(ApoClockArcarde.START)));
		this.getStringWidth().put(ApoClockArcarde.TITLE, (int)(ApoClockPanel.title_font.getLength(ApoClockArcarde.TITLE)));
		this.getStringWidth().put(ApoClockArcarde.POINTS, (int)(ApoClockPanel.title_font.getLength(ApoClockArcarde.POINTS)));
		
		String s = "Name: ";
		this.textX = (int)(85 + ApoClockPanel.game_font.getLength(s));
		
		if (this.highscore == null) {
			this.highscore = new ApoClockHighscore(this.getGame());
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					ApoClockArcarde.this.highscore.loadHighscore();
				}
	 		});
	 		t.start();
		}
	}

	public ApoClockHighscore getHighscore() {
		return this.highscore;
	}

	@Override
	public void touchedPressed(int x, int y, int finger) {
		if ((x > this.textX) && (x < this.textX + this.getGame().getTextfield().getWidth()) &&
			(y > ApoClockConstants.GAME_HEIGHT - 45) && (y < ApoClockConstants.GAME_HEIGHT - 45 + this.getGame().getTextfield().getHeight())) {
			if (!this.getGame().getTextfield().isSelect()) {
				this.getGame().getTextfield().setSelect(true);
				BitsInput.getInstance().setVirtualKeyboardVisible(true);
			}
		} else {
			if (this.getGame().getTextfield().isSelect()) {
				BitsInput.getInstance().setVirtualKeyboardVisible(false);
				this.getGame().savePreferences();
			}
			this.getGame().getTextfield().setSelect(false);
		}
	}

	@Override
	public void touchedReleased(int x, int y, int finger) {
		
	}

	@Override
	public void touchedDragged(int x, int y, int oldX, int oldY, int finger) {
		
	}

	public int getPoints() {
		return this.points;
	}

	public void setPoints(final int points, final int clocks) {
		if (points > 0) {
			this.highscore.saveHighscore(this.getGame().getTextfield().getCurString(), points, clocks);
			this.getGame().getLocal().addNextValues(points, clocks);
			this.getGame().savePreferences();
		}
		this.points = points;
	}

	@Override
	public void touchedButton(String function) {
		if (function.equals(ApoClockArcarde.BACK)) {
			this.onBackButtonPressed();
		} else if (function.equals(ApoClockArcarde.START)) {
			this.getGame().setAracardeGame();
		}
	}
	
	public void onKeyDown(final int key) {
	}

	public void onKeyUp(final int key, final BitsKeyEvent event) {
	}
	
	public void onBackButtonPressed() {
		this.getGame().setMenu();
	}

	@Override
	public void think(int delta) {
		
	}

	@Override
	public void render(BitsGLGraphics g) {
		this.getGame().drawString(g, ApoClockArcarde.TITLE, 240, 2, ApoClockPanel.title_font, new float[] {1, 1, 1, 1}, new float[] {0, 0, 0, 1});
		
		if (this.points >= 0) {
			g.setColor(128f/255f, 128f/255f, 128f/255f, 1.0f);
			g.fillRect(50, 70, 380, 100);
			
			g.setColor(48f/255f, 48f/255f, 48f/255f, 1.0f);
			g.drawRect(50, 70, 380, 100);
			
			this.getGame().drawString(g, "Points", 240, 70, ApoClockPanel.title_font);
			String s = String.valueOf(this.points);
			this.getGame().drawString(g, s, (int)(240 - ApoClockPanel.title_font.getLength(s)/2), 120, ApoClockPanel.title_font);
		}
		
		g.setColor(128f/255f, 128f/255f, 128f/255f, 1.0f);
		g.fillRect(10, 450, 460, 105);
		
		g.setColor(48f/255f, 48f/255f, 48f/255f, 1.0f);
		g.drawRect(10, 450, 460, 105);
		
		this.getGame().drawString(g, "In the arcarde mode a new clock will", 20, 455, ApoClockPanel.font);
		this.getGame().drawString(g, "appear after starting from another.", 20, 485, ApoClockPanel.font);
		this.getGame().drawString(g, "Reach the highscore and be the best.", 20, 515, ApoClockPanel.font);
		
		if (this.highscore != null) {
			this.highscore.drawHighscore(g);
		}
		if (this.getGame().getLocal() != null) {
			this.getGame().getLocal().drawHighscore(g);
		}
		
		this.getGame().renderButtons(g);
		
		g.setColor(0.0f, 0.0f, 0.0f, 1.0f);
		String s = "Name: ";
		g.drawText(s, 85, ApoClockConstants.GAME_HEIGHT - 42);
		this.getGame().getTextfield().render(g, this.textX, ApoClockConstants.GAME_HEIGHT - 45);
	}

}
