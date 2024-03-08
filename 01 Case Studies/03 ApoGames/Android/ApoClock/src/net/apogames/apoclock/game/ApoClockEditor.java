package net.apogames.apoclock.game;

import java.util.ArrayList;

import net.apogames.apoclock.ApoClock;
import net.apogames.apoclock.ApoClockConstants;
import net.apogames.apoclock.ApoClockModel;
import net.apogames.apoclock.editor.ApoClockEditorClockStats;
import net.apogames.apoclock.entity.ApoClockEntityBall;
import net.apogames.apoclock.entity.ApoClockEntityClock;
import net.apogames.apoclock.entity.ApoClockString;

import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;
import net.gliblybits.bitsengine.utils.BitsLog;

public class ApoClockEditor extends ApoClockModel {

	public static final String BACK = "back";
	public static final String ADD = "add";
	public static final String REMOVE = "remove";
	public static final String NEW = "new";
	public static final String UPLOAD = "upload";
	public static final String TEST = "test";
	
	private ArrayList<ApoClockEntityClock> clocks;
	
	private ApoClockEditorClockStats clockStats;
	
	private ApoClockString uploadString;
	
	private final ApoClockEntityBall ball = new ApoClockEntityBall(5, 260, 5, 90, 0.2f);
	
	private boolean bCanDragged;
	
	public ApoClockEditor(ApoClockPanel game) {
		super(game);
	}

	@Override
	public void init() {
		if (this.clocks == null) {
			this.clocks = new ArrayList<ApoClockEntityClock>();
		}
		
		this.getStringWidth().put(ApoClockEditor.BACK, (int)(ApoClockPanel.game_font.getLength(ApoClockEditor.BACK)));
		this.getStringWidth().put(ApoClockEditor.ADD, (int)(ApoClockPanel.game_font.getLength(ApoClockEditor.ADD)));
		this.getStringWidth().put(ApoClockEditor.REMOVE, (int)(ApoClockPanel.game_font.getLength(ApoClockEditor.REMOVE)));
		this.getStringWidth().put(ApoClockEditor.NEW, (int)(ApoClockPanel.game_font.getLength(ApoClockEditor.NEW)));
		this.getStringWidth().put(ApoClockEditor.UPLOAD, (int)(ApoClockPanel.game_font.getLength(ApoClockEditor.UPLOAD)));
		this.getStringWidth().put(ApoClockEditor.TEST, (int)(ApoClockPanel.game_font.getLength(ApoClockEditor.TEST)));
		
		String s = "ApoClock - Editor";
		this.getStringWidth().put(s, (int) ApoClockPanel.game_font.getLength(s));
		
		this.checkTestLevel();
	}
	
	public void setLevelSolved(boolean bSolved) {
		if ((!bSolved) || (!ApoClock.isOnline())) {
			this.getGame().getButtons()[13].setVisible(false);
		}
	}
	
	private void checkTestLevel() {
		if (this.clocks.size() < 2) {
			this.getGame().getButtons()[14].setVisible(false);
		} else {
			this.getGame().getButtons()[14].setVisible(true);
		}
	}

	@Override
	public void touchedPressed(int x, int y, int finger) {
		this.bCanDragged = false;
		if (this.clockStats != null) {
			if (this.clockStats.contains(x, y)) {
				if (this.clockStats.touchedPressed(x, y, finger)) {
					this.setLevelSolved(false);
				}
				return;
			}
		}
		boolean bBreak = false;
		for (int i = 0; i < this.clocks.size(); i++) {
			if (this.clocks.get(i).intersects(x, y, 1)) {
				if (this.clockStats != null) {
					this.clockStats.getClock().setSelected(false);
				}
				this.bCanDragged = true;
				this.clockStats = new ApoClockEditorClockStats(this.clocks.get(i));
				bBreak = true;
				break;
			}
		}
		if (!bBreak) {
			if (this.clockStats != null) {
				this.clockStats.getClock().setSelected(false);
			}
			this.clockStats = null;
		}
	}

	@Override
	public void touchedReleased(int x, int y, int finger) {
		this.bCanDragged = false;
	}

	@Override
	public void touchedDragged(int x, int y, int oldX, int oldY, int finger) {
		if ((this.clockStats != null) && (this.bCanDragged)) {
			int changeX = x - oldX;
			int changeY = y - oldY;
			int newX = (int)(this.clockStats.getClock().getX() + changeX);
			if (newX - this.clockStats.getClock().getRadius() < 0) {
				newX = (int)(this.clockStats.getClock().getRadius());
			} else if (newX + this.clockStats.getClock().getRadius() >= ApoClockConstants.GAME_WIDTH) {
				newX = ApoClockConstants.GAME_WIDTH - (int)(this.clockStats.getClock().getRadius());
			}
			
			int newY = (int)(this.clockStats.getClock().getY() + changeY);
			if (newY - this.clockStats.getClock().getRadius() < 25) {
				newY = (int)(this.clockStats.getClock().getRadius()) + 25;
			} else if (newY + this.clockStats.getClock().getRadius() >= 590) {
				newY = 590 - (int)(this.clockStats.getClock().getRadius());
			}
			this.clockStats.getClock().setX(newX);
			this.clockStats.getClock().setY(newY);
		}
	}
	
	@Override
	public void touchedButton(String function) {
		if (function.equals(ApoClockEditor.ADD)) {
			if (this.clockStats != null) {
				this.clockStats.getClock().setSelected(false);
			}
			this.setLevelSolved(false);
			this.clockStats = null;
			ApoClockEntityClock clock = new ApoClockEntityClock(240, 260, 40, (int)(Math.random() * 360), (int)(Math.random() * 10 + 4));
			if (Math.random() * 100 > 50) {
				clock.setRotateClockwise(false);
			}
			this.clocks.add(clock);
			this.checkTestLevel();
		} else if (function.equals(ApoClockEditor.BACK)) {
			this.onBackButtonPressed();
		} else if (function.equals(ApoClockEditor.NEW)) {
			if (this.clockStats != null) {
				this.clockStats.getClock().setSelected(false);
			}
			this.setLevelSolved(false);
			this.clockStats = null;
			this.clocks.clear();
			this.checkTestLevel();
		} else if (function.equals(ApoClockEditor.REMOVE)) {
			if (this.clockStats != null) {
				this.clocks.remove(this.clockStats.getClock());
				this.clockStats = null;
			}
			this.setLevelSolved(false);
			this.checkTestLevel();
		} else if (function.equals(ApoClockEditor.TEST)) {
			String levelString = this.getLevelString();
			BitsLog.d("levelString", levelString);
			this.getGame().setPuzzleGame(-1, levelString, false);
		} else if (function.equals(ApoClockEditor.UPLOAD)) {
			this.setLevelSolved(false);
			this.uploadString = new ApoClockString(240, 550, 20, "Uploading ...", true, 200, true);
			
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					ApoClockEditor.this.uploadString();
				}
	 		});
	 		t.start();
		}
	}
	
	private void uploadString() {
		if (this.getGame().getUserlevels().addLevel(this.getLevelString())) {
			this.uploadString = new ApoClockString(240, 550, 20, "Uploading successfully", true, 20, true);
			this.getGame().loadUserlevels();
		} else {
			this.uploadString = new ApoClockString(240, 550, 20, "Uploading failed", true, 20, true);
		}
	}
	
	public void onBackButtonPressed() {
		this.getGame().setPuzzle();
	}
	
	private String getLevelString() {
		String level = "";
		for (int i = 0; i < this.clocks.size(); i++) {
			level += this.clocks.get(i).getStringForLevel();
			if (i + 1 < this.clocks.size()) {
				level += ",";
			}
		}
		return level;
	}

	@Override
	public void think(int delta) {
		if (this.clockStats != null) {
			this.clockStats.think(delta);
		}
		if (this.uploadString != null) {
			this.uploadString.think(delta);
			
			if (!this.uploadString.isVisible()) {
				this.uploadString = null;
			}
		}
	}

	@Override
	public void render(BitsGLGraphics g) {
		g.setColor(128f/255f, 128f/255f, 128f/255f, 1.0f);
		g.fillRect(0,0,480,25);
		g.fillRect(0,590,480,50);
		
		g.setColor(48f/255f, 48f/255f, 48f/255f, 1.0f);
		g.drawRect(0,0,480,25);
		g.drawRect(0,590,480,50);
		
		String s = "ApoClock - Editor";
		this.getGame().drawString(g, s, 240, -4, ApoClockPanel.game_font);
		
		for (int i = 0; i < this.clocks.size(); i++) {
			this.clocks.get(i).render(g);
		}
		this.ball.render(g);
		
		this.getGame().renderButtons(g, ApoClockPanel.game_font);
		
		if (this.clockStats != null) {
			this.clockStats.render(g);
		}
		
		if (this.uploadString != null) {
			this.uploadString.render(g, 0, 0);
		}
	}

}
