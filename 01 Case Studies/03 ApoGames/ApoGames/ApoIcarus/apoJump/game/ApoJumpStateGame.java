package apoJump.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import org.apogames.entity.ApoNewTextfield;
import org.apogames.help.ApoHelp;
import org.apogames.help.ApoHighscore;

import apoJump.ApoJumpConstants;
import apoJump.ApoJumpImageContainer;

public class ApoJumpStateGame extends ApoJumpState {

	public static final String BUTTON_BACK = "back";
	public static final String BUTTON_RETRY = "retry";
	public static final String BUTTON_UPLOAD = "upload";

	public static final int UPLOAD_TIME = 3000;
	public static final int UPLOAD_TIME_TEST = 5000;

	private ApoNewTextfield textField;
	
	private boolean bUpload;
	
	private ArrayList<Integer> pressedKeys;
	
	private int pressedWinTime;
	
	private int countdown;
	
	public ApoJumpStateGame(ApoJumpPanel game) {
		super(game);
	}
	
	public void init() {
		this.getGame().setShouldRepaint(true);
		
		if (this.pressedKeys == null) {
			this.pressedKeys = new ArrayList<Integer>();
		}
		this.pressedKeys.clear();
		if (this.textField == null) {
			int width = 200;
			int height = 50;
			int x = ApoJumpConstants.GAME_WIDTH/2;
			int y = ApoJumpConstants.GAME_HEIGHT/2 + 50;
			this.textField = new ApoNewTextfield(x - 40, y, width, height, ApoJumpConstants.FONT_TEXTFIELD);
			this.textField.setMaxLength(11);
		}
		this.textField.setBVisible(false);
		this.bUpload = false;
		this.countdown = ApoJumpConstants.COUNTDOWN_TIME;
	}
	
	public void setWin() {
		this.textField.setBVisible(true);
		this.textField.setBSelect(true);
		this.textField.setSelect();
		this.pressedWinTime = 1000;
		this.getGame().getButtons()[9].setBVisible(true);
		this.getGame().getButtons()[10].setBVisible(false);
		this.bUpload = false;
		if (this.getGame().getKeyboard().isPressed(KeyEvent.VK_A)) {
			this.pressedKeys.add(KeyEvent.VK_A);
		} else if (this.getGame().getKeyboard().isPressed(KeyEvent.VK_D)) {
			this.pressedKeys.add(KeyEvent.VK_D);
		}
	}

	@Override
	public void keyButtonReleased(int button, char character) {
		if (this.getGame().getLevel() != null) {
			if (this.getGame().isBWin()) { 
				if ((this.textField != null) && (this.textField.isBVisible()) && (this.textField.isBSelect())) {
					boolean bShift = false;
					if (this.getGame().getKeyboard().isPressed(KeyEvent.VK_CONTROL)) {
						if (button == KeyEvent.VK_V) {
							String s = ApoHelp.getClipboardContents();
							if (s != null) {
								for (int i = 0; i < s.length(); i++) {
									char chara = s.charAt(i);
									this.textField.addCharacter(button, chara);
								}
								bShift = true;
							}
						} else if (button == KeyEvent.VK_C) {
							String s = this.textField.getSelectedString();
							if (s != null) {
								ApoHelp.setClipboardContents(s);
							}
							bShift = true;
						} else if (button == KeyEvent.VK_X) {
							String s = this.textField.getSelectedString();
							if (s != null) {
								ApoHelp.setClipboardContents(s);
								this.textField.deleteSelectedString();
							}
							bShift = true;
						}
					} else if (this.getGame().getKeyboard().isPressed(KeyEvent.VK_SHIFT)) {
						if (button == KeyEvent.VK_LEFT) {
							this.textField.nextSelectedPosition(this.textField.getPosition() - 1);
							bShift = true;
						} else if (button == KeyEvent.VK_RIGHT) {
							this.textField.nextSelectedPosition(this.textField.getPosition() + 1);
							bShift = true;
						}
					}
					if (!bShift) {
						if (this.pressedKeys.indexOf(button) != -1) {
						} else {
							this.textField.addCharacter(button, character);
						}
					}
				}
			
				if (button == KeyEvent.VK_ENTER) {
					if ((this.textField != null) && (this.textField.isBVisible()) && (this.textField.isBSelect())) {
						this.textField.setBSelect(false);
					} else {
						this.uploadScore();
						this.restart();
					}
				} else if (button == KeyEvent.VK_ESCAPE) {
					this.uploadScore();
					this.getGame().setMenu();
				}
			} else {
				if ((button == KeyEvent.VK_LEFT) || (button == KeyEvent.VK_A)) {
					this.getGame().getLevel().getPlayer().setVecX(0);	
				} else if ((button == KeyEvent.VK_RIGHT) || (button == KeyEvent.VK_D)) {
					this.getGame().getLevel().getPlayer().setVecX(0);	
				} else if (button == KeyEvent.VK_ESCAPE) {
					this.getGame().getLevel().setWin();
				}
			}
		}
	}
	

	@Override
	public void mouseButtonReleased(int x, int y) {
		if (this.getGame().isBWin()) {
			if ((this.textField != null) && (this.textField.isBVisible())) {
				this.textField.mouseReleased(x, y);
				if (this.textField.intersects(x, y)) {
					this.textField.setBSelect(true);
				} else {
					this.textField.setBSelect(false);
				}
			}
		} else {
		}
	}
	
	public boolean mouseDragged(int x, int y) {
		if (!this.getGame().isBWin()) {
			return this.mousePressed(x, y, false);
		}
		return false;
	}
	
	@Override
	public boolean mousePressed(int x, int y, boolean bRight) {
		if (this.getGame().isBWin()) {
			if ((this.textField != null) && (this.textField.isBVisible())) {
				if (this.textField.mousePressed(x, y)) {
					return true;
				}
			}
		} else if (!this.getGame().isBWin()) {
			this.getGame().getLevel().makeArrow(x, y);
		}
		return false;
	}
	
	@Override
	public boolean mouseMoved(int mouseX, int mouseY) {
		if ((this.textField != null) && (this.textField.isBVisible())) {
			if (this.textField.getMove(mouseX, mouseY)) {
				return true;
			}
		}
		return true;
	}
	
	private void restart() {
		this.countdown = ApoJumpConstants.COUNTDOWN_TIME;
		this.getGame().getLevel().init();
		this.textField.setBVisible(false);
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoJumpStateGame.BUTTON_BACK)) {
			this.uploadScore();
			this.getGame().setMenu();
		} else if (function.equals(ApoJumpStateGame.BUTTON_RETRY)) {
			this.uploadScore();
			this.restart();
		} else if (function.equals(ApoJumpStateGame.BUTTON_UPLOAD)) {
			this.uploadScore();
		}
		this.textField.setBSelect(false);
	}
	
	private void uploadScore() {
		if (!this.bUpload) {
			this.bUpload = true;
			ApoJumpStateGame.this.upload();
		}
	}
	
	private void upload() {
		ApoHighscore.getInstance().save(this.getGame().getLevel().getPoints(), (int)this.getGame().getLevel().getTime(), this.textField.getCurString());
		this.getGame().getButtons()[10].setBVisible(false);
		this.getGame().getHighscore().loadHighscore(false);		
	}
	
	@Override
	public void think(int delta) {
		if (this.getGame().getLevel() != null) {
			if (this.countdown <= 0) {
				if (!this.getGame().isBWin()) {
					if ((this.getGame().getKeyboard().isPressed(KeyEvent.VK_LEFT)) || (this.getGame().getKeyboard().isPressed(KeyEvent.VK_A))) {
						this.getGame().getLevel().getPlayer().setVecX(-ApoJumpConstants.MAX_VEC_X);	
					} else if ((this.getGame().getKeyboard().isPressed(KeyEvent.VK_RIGHT)) || (this.getGame().getKeyboard().isPressed(KeyEvent.VK_D))) {
						this.getGame().getLevel().getPlayer().setVecX(ApoJumpConstants.MAX_VEC_X);	
					}
				}
				this.getGame().getLevel().think(delta);
			} else {
				this.countdown -= delta;
			}
		}
		if (this.getGame().isBWin()) {
			if (this.pressedWinTime >= 0) {
				this.pressedWinTime -= delta;
				if (this.pressedWinTime <= 0) {
					this.pressedKeys.clear();
				}
			}
			if (this.textField != null) {
				this.textField.think(delta);
			}
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		this.getGame().renderBackground(g);
		if (this.getGame().getLevel() != null) {
			this.getGame().getLevel().render(g, 0, 0);
			if (this.getGame().isBWin()) {
				int x = ApoJumpConstants.GAME_WIDTH/2 - ApoJumpImageContainer.iAnalysisBackground.getWidth()/2;
				g.drawImage(ApoJumpImageContainer.iAnalysisBackground, x, 54, null);
				
				g.setFont(ApoJumpConstants.FONT_TEXTFIELD);
				g.setColor(Color.BLACK);
				
				String s = "Game over";
				int w = g.getFontMetrics().stringWidth(s);
				int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
				g.drawString(s, (int)(ApoJumpConstants.GAME_WIDTH/2 - w/2), (int)(120));
				
				g.setFont(ApoJumpConstants.FONT_ANALYSIS);
				
				s = "After " + ApoHelp.getTimeToDraw((int)this.getGame().getLevel().getTime())+" you crashed!";
				w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, (int)(ApoJumpConstants.GAME_WIDTH/2 - w/2), (int)(205));
				
				int points = this.getGame().getLevel().getPoints();
				Point p = this.getGame().getHighscore().getPosition(points);
				int place = p.x;
				int max = p.y;
				s = "With "+String.valueOf(points)+" points you reached";
				w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, (int)(ApoJumpConstants.GAME_WIDTH/2 - w/2), (int)(240));
				
				s = "place "+String.valueOf(place)+" of "+String.valueOf(max);
				w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, (int)(ApoJumpConstants.GAME_WIDTH/2 - w/2), (int)(275));
				
				if (this.textField.isBVisible()) {
					this.textField.render(g, 0, 0);
					
					g.setFont(ApoJumpConstants.FONT_TEXTFIELD);
					s = "Name: ";
					w = g.getFontMetrics().stringWidth(s);
					h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
					g.drawString(s, (int)(this.textField.getX() - w), (int)(this.textField.getY() + this.textField.getHeight()/2 + h/2));
				}
			} else {
				if (this.countdown > 0) {
					g.setFont(ApoJumpConstants.FONT_COUNTDOWN);
					g.setColor(Color.RED);
					String s = String.valueOf((this.countdown / 1000) + 1);
					int w = g.getFontMetrics().stringWidth(s);
					int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
					g.drawString(s, ApoJumpConstants.GAME_WIDTH/2 - w/2, ApoJumpConstants.GAME_HEIGHT/2 + h/2);
				}
			}
		}
	}


}
