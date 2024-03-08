package net.apogames.apomono.game;

import net.apogames.apomono.ApoMonoConstants;
import net.apogames.apomono.ApoMonoModel;
import net.apogames.apomono.ApoMonoSoundPlayer;
import net.apogames.apomono.entity.ApoMonoString;
import net.gliblybits.bitsengine.graphics.BitsGraphics;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;

public class ApoMonoEditor extends ApoMonoModel {

	public static final String BACK = "back";
	public static final String UPLOAD = "upload";
	public static final String TEST = "test";
	public static final String NEW = "new";
	
	/**
	 * p[0] == aktuelles Level
	 * p[1] == mirrorcount horizontal
	 * p[2] == aktuelles Level geschafft klick
	 * p[3] == mirrortime
	 * p[4] == time
	 * p[5] == x Wert touch
	 * p[6] == y Wert touch
	 * p[7] == mirrorcount vertical
	 * p[8] == gravitychangecount
	 */
	private final int[] p = new int[9];
	
	private int[][] level = new int[1][1];
	
	private int curSelected;
	
	private ApoMonoString monoString;

	private int frameGoal, frameTimeGoal;
	private float frameGravity, frameGravityAdd, frameGravityTime;
	
	private boolean bMenu;
	
	private int beamerFrame, beamerFrameTime;
	
	public ApoMonoEditor(ApoMonoPanel game) {
		super(game);
	}

	@Override
	public void init() {
		if (level.length <= 1) {
			this.newLevel();
			this.curSelected = 0;
			this.frameGravityAdd = 1;
		}
		this.canBeTested();
		this.bMenu = false;
	}
	
	public void setUploadVisible(final boolean bVisible) {
		this.getGame().getButtons()[7].setVisible(bVisible);
	}
	
	@Override
	public void touchedPressed(int x, int y, int finger) {
		p[5] = x;
		p[6] = y;
	}

	@Override
	public void touchedReleased(int x, int y, int finger) {
		p[5] = -1;
		p[6] = -1;
	}

	@Override
	public void touchedDragged(int x, int y, int oldX, int oldY, int finger) {
		if (p[5] > 0) {
			p[5] = x;
			p[6] = y;
		}
	}

	@Override
	public void touchedButton(String function) {
		if (function.equals(ApoMonoEditor.BACK)) {
			this.onBackButtonPressed();
		} else if (function.equals(ApoMonoEditor.NEW)) {
			this.newLevel();
		} else if (function.equals(ApoMonoEditor.TEST)) {
			this.testLevel();
		} else if (function.equals(ApoMonoEditor.UPLOAD)) {
			this.uploadLevel();
		}
		this.getGame().playSound(ApoMonoSoundPlayer.SOUND_BUTTON);
	}
	
	private boolean canBeTested() {
		int goal = 0;
		int player = 0;
		int beamer = 0;
		for (int y = 0; y < this.level.length; y++) {
			for (int x = 0; x < this.level[0].length; x++) {
				if (this.level[y][x] == 2) {
					player += 1;
				}
				if (this.level[y][x] == 3) {
					goal += 1;
				}
				if (this.level[y][x] == 11) {
					beamer += 1;
				}
			}
		}
		if ((player == 1) && (goal == 1) && ((beamer == 0) || (beamer == 2))) {
			this.getGame().getButtons()[5].setVisible(true);
			return true;
		}
		this.getGame().getButtons()[5].setVisible(false);
		this.setUploadVisible(false);
		return false;
	}
	
	public String getLevelString() {
		String levelString = String.valueOf(p[1]) + String.valueOf(p[7]) + String.valueOf(p[8]);
		for (int y = 0; y < this.level.length; y++) {
			for (int x = 1; x < this.level[y].length - 1; x++) {
				if (this.level[y][x] > 9) {
					levelString += "" + (char)(this.level[y][x] + 87);
				} else {
					levelString += "" + this.level[y][x];
				}
			}
		}
		return levelString;
	}
	
	private void testLevel() {
		this.getGame().setGame(0, this.getLevelString(), false);
	}
	
	private void uploadLevel() {
		this.setUploadVisible(false);
		this.monoString = new ApoMonoString(240, ApoMonoPuzzleGame.changeY + 200, 20, "Uploading ...", true, 200, true);
		
		Thread t = new Thread(new Runnable() {

			public void run() {
				ApoMonoEditor.this.uploadString();
			}
 		});
 		t.start();
	}
	
	private void uploadString() {
		String s = this.getLevelString();
		if (this.getGame().getUserlevels().addLevel(s)) {
			this.monoString = new ApoMonoString(240, ApoMonoPuzzleGame.changeY + 200, 20, "Upload successful", true, 20, true);
			this.getGame().loadUserlevels();
		} else {
			this.monoString = new ApoMonoString(240, ApoMonoPuzzleGame.changeY + 200, 20, "Upload failed", true, 20, true);
		}
	}
	
	public void onKeyDown(final int key) {
		
	}

	public void onKeyUp(final int key) {
		
	}
	
	public void onBackButtonPressed() {
		this.getGame().setMenu();
	}
	
	private void newLevel() {
		level = new int[15][30];
		p[1] = p[5] = p[6] = p[7] = p[8] = 0;
		for (int y = 0; y < level.length; y++) {
			level[y][0] = 0;
			level[y][level[0].length - 1] = 0;
			if (y == 14) {
				for (int x = 1; x < level[0].length - 1; x++) {
					level[y][x] = 1;
				}
			}
		}
		level[13][2] = 2;
		level[13][27] = 3;
		this.canBeTested();
		this.setUploadVisible(false);
	}

	@Override
	public void think(int delta) {
		this.beamerFrameTime += delta;
		if (this.beamerFrameTime >= ApoMonoPuzzleGame.BEAMER_CHANGE_TIME) {
			this.beamerFrameTime -= ApoMonoPuzzleGame.BEAMER_CHANGE_TIME;
			this.beamerFrame += 1;
			if (this.beamerFrame > 3) {
				this.beamerFrame = 0;
			}
		}
		if (this.frameGravityTime > 0) {
			this.frameGravityTime -= delta;
		} else {
			this.frameGravity += 0.1f * this.frameGravityAdd;
			if ((this.frameGravity > 16) || (this.frameGravity < 0)) {
				this.frameGravityAdd = -this.frameGravityAdd;
				this.frameGravityTime = ApoMonoPuzzleGame.FRAME_GRAVITY_TIME;
			}
		}
		
		this.frameTimeGoal += delta;
		if (this.frameTimeGoal > ApoMonoPuzzleGame.FRAME_TIME) {
			this.frameTimeGoal -= ApoMonoPuzzleGame.FRAME_TIME;
			this.frameGoal += 1;
			if (this.frameGoal > 3) {
				this.frameGoal = 0;
			}
		}
		
		if (this.monoString != null) {
			this.monoString.think(delta);
			
			if (!this.monoString.isVisible()) {
				this.monoString = null;
			}
		}
		
		if (level[0][0] == -1) {
			this.newLevel();
		} else if (p[5] > 0) {
			if (this.bMenu) {
				if ((p[5] < 170) || (p[5] > 310) ||
					(p[6] < 45) || (p[6] > 235)) {
					this.bMenu = !this.bMenu;
					p[5] = -1;
				}
				
				if ((p[5] > 190) && (p[5] < 230) &&
					(p[6] > 70) && (p[6] < 100)) {
					p[1] -= 1;
					if (p[1] < 0) {
						p[1] = 9;
					}
					this.setUploadVisible(false);
					p[5] = -1;
					this.getGame().playSound(ApoMonoSoundPlayer.SOUND_BUTTON);
				}
				if ((p[5] > 255) && (p[5] < 285) &&
					(p[6] > 70) && (p[6] < 100)) {
					p[1] += 1;
					if (p[1] > 9) {
						p[1] = 0;
					}
					this.setUploadVisible(false);
					p[5] = -1;
					this.getGame().playSound(ApoMonoSoundPlayer.SOUND_BUTTON);
				}
				
				if ((p[5] > 190) && (p[5] < 220) &&
					(p[6] > 140) && (p[6] < 170)) {
					p[7] -= 1;
					if (p[7] < 0) {
						p[7] = 9;
					}
					this.setUploadVisible(false);
					p[5] = -1;
					this.getGame().playSound(ApoMonoSoundPlayer.SOUND_BUTTON);
				}
				if ((p[5] > 255) && (p[5] < 285) &&
					(p[6] > 140) && (p[6] < 170)) {
					p[7] += 1;
					if (p[7] > 9) {
						p[7] = 0;
					}
					this.setUploadVisible(false);
					p[5] = -1;
					this.getGame().playSound(ApoMonoSoundPlayer.SOUND_BUTTON);
				}
				if ((p[5] > 190) && (p[5] < 220) &&
					(p[6] > 210) && (p[6] < 240)) {
					p[8] -= 1;
					if (p[8] < 0) {
						p[8] = 9;
					}
					this.setUploadVisible(false);
					p[5] = -1;
					this.getGame().playSound(ApoMonoSoundPlayer.SOUND_BUTTON);
				}
				if ((p[5] > 255) && (p[5] < 285) &&
					(p[6] > 210) && (p[6] < 240)) {
					p[8] += 1;
					if (p[8] > 9) {
						p[8] = 0;
					}
					this.setUploadVisible(false);
					p[5] = -1;
					this.getGame().playSound(ApoMonoSoundPlayer.SOUND_BUTTON);
				}
			} else {
				if (p[6] < ApoMonoPuzzleGame.changeY) {
					if (!this.bMenu) {
						if ((p[5] >= 0) && (p[5] <= 130)) {
							this.bMenu = !this.bMenu;
							this.getGame().playSound(ApoMonoSoundPlayer.SOUND_BUTTON);
						}
					}
					p[5] = -1;
				} else if (p[6] > ApoMonoPuzzleGame.changeY + 240) {
					boolean sound = true;
					if ((p[5] > 5) && (p[5] < 40)) {
						this.curSelected = 0;
					} else if ((p[5] > 45) && (p[5] < 80)) {
						this.curSelected = 1;
					} else if ((p[5] > 85) && (p[5] < 120)) {
						this.curSelected = 2;
					} else if ((p[5] > 125) && (p[5] < 160)) {
						this.curSelected = 3;
					} else if ((p[5] > 165) && (p[5] < 200)) {
						this.curSelected = 4;
					} else if ((p[5] > 205) && (p[5] < 240)) {
						this.curSelected = 5;
					} else if ((p[5] > 245) && (p[5] < 280)) {
						this.curSelected = 6;
					} else if ((p[5] > 285) && (p[5] < 320)) {
						this.curSelected = 7;
					} else if ((p[5] > 325) && (p[5] < 360)) {
						this.curSelected = 8;
					} else if ((p[5] > 365) && (p[5] < 400)) {
						this.curSelected = 10;
					} else if ((p[5] > 405) && (p[5] < 440)) {
						this.curSelected = 11;
					} else if ((p[5] > 445) && (p[5] < 480)) {
						this.curSelected = 12;
					} else {
						sound = false;
					}
					if (sound) {
						this.getGame().playSound(ApoMonoSoundPlayer.SOUND_BUTTON);
					}
					p[5] = -1;
				} else if ((p[6] > ApoMonoPuzzleGame.changeY) && (p[6] < ApoMonoPuzzleGame.changeY + 240)) {
					int myX = p[5] / 16;
					int myY = (p[6] - ApoMonoPuzzleGame.changeY) / 16;
					int beamer = 0;
					if ((myX > 0) && (myX < level[0].length - 1) && (myY >= 0) && (myY < level.length) && (this.level[myY][myX] != this.curSelected)) {
						if ((this.curSelected == 2) || (this.curSelected == 3) || (this.curSelected == 11)) {
							for (int y = 0; y < level.length; y++) {
								for (int x = 0; x < level[0].length; x++) {
									if (this.curSelected != 11) {
										if (level[y][x] == this.curSelected) {
											this.level[y][x] = 0;
										}
									} else {
										if (level[y][x] == this.curSelected) {
											beamer += 1;
											if (beamer >= 2) {
												level[y][x] = 0;
											}
										}
									}
								}
							}
						}
						this.level[myY][myX] = this.curSelected;
						
						this.canBeTested();
						this.setUploadVisible(false);
					}
				}
			}
		}
	}
	
	private void renderMenuBox(final BitsGraphics g, final int width, final int height) {
		g.setColor(ApoMonoConstants.DARK[0], ApoMonoConstants.DARK[1], ApoMonoConstants.DARK[2], 1f);
		g.fillRect(3, 3, width - 4, height - 4);
		ApoMonoPuzzleGame.setDarkerColor(g);
		g.fillRect(1, 3, 2, height - 4);
		g.fillRect(3 + width - 4, 3, 2, height - 4);
		g.fillRect(3, 1, width - 4, 2);
		g.fillRect(3, 3 + height - 4, width - 4, 2);
		ApoMonoPuzzleGame.setBrighterColor(g);
		g.fillRect(3 + width - 2, 5, 2, height - 4);
		g.fillRect(3 + width - 4, 3 + height - 4, 2, 2);
		g.fillRect(5, 3 + height - 2, width - 4, 2);
	}

	@Override
	public void render(BitsGLGraphics g) {
		g.setColor(ApoMonoConstants.BRIGHT_DARK[0], ApoMonoConstants.BRIGHT_DARK[1], ApoMonoConstants.BRIGHT_DARK[2], 1f);
		for (int x = 1; x < this.level[0].length; x++) {
			g.drawLine(x * 16, ApoMonoPuzzleGame.changeY, x * 16, ApoMonoPuzzleGame.changeY + 240);
		}
		for (int y = 0; y < this.level.length + 1; y++) {
			g.drawLine(16, ApoMonoPuzzleGame.changeY + y * 16, (level[0].length - 1) * 16, ApoMonoPuzzleGame.changeY + y * 16);
		}

		this.renderMenuBox(g, 130, ApoMonoPuzzleGame.changeY - 5);
		ApoMonoPuzzleGame.drawMenu(g, this.getGame(), ApoMonoConstants.BRIGHT_DARK, ApoMonoConstants.BRIGHT, p[1], p[7], p[8], this.frameGravity);
		
		int addPX = 0;
		int addPY = 0 + ApoMonoPuzzleGame.changeY;		
		
		for (int y = 0; y < level.length; y++) {
			for (int x = 0; x < level[0].length; x++) {
				if (level[y][x] != 0) {
					g.setColor(ApoMonoConstants.DARK[0], ApoMonoConstants.DARK[1], ApoMonoConstants.DARK[2], 1f);

					addPX = x * 16;
					addPY = y * 16 + ApoMonoPuzzleGame.changeY;
					if (level[y][x] == 2) {
						ApoMonoPuzzleGame.renderPlayer(g, addPX, addPY, 0, 1, 0);
					}
					
					if (level[y][x] == 3) {
						/** Render das Ziel */	
						ApoMonoPuzzleGame.drawFinish(g, addPX, addPY, this.frameGoal);
					}
					if ((level[y][x] == 1) || (level[y][x] == 4) || (level[y][x] == 5) || (level[y][x] == 8)) {
						if (level[y][x] == 1) {
							ApoMonoPuzzleGame.drawBlock(g, addPX, addPY);
						} else if (level[y][x] == 4) {
							ApoMonoPuzzleGame.drawMoveableBox(g, addPX, addPY);
						} else if (level[y][x] == 5) {
							ApoMonoPuzzleGame.drawBox(g, addPX, addPY, false);
						} else if (level[y][x] == 8) {
							ApoMonoPuzzleGame.drawBlockPlusMirror(g, addPX, addPY);
						}
					}
					if (level[y][x] == 6) {
						ApoMonoPuzzleGame.drawAir(g, addPX, addPY);
					}
					if (level[y][x] == 7) {
						ApoMonoPuzzleGame.drawX(g, addPX, addPY);
					}
					if (level[y][x] == 10) {
						ApoMonoPuzzleGame.drawSpikes(g, addPX, addPY);
					}
					if (level[y][x] == 11) {
						ApoMonoPuzzleGame.drawBeamer(g, addPX, addPY, this.beamerFrame);
					}
					if (level[y][x] == 12) {
						ApoMonoPuzzleGame.drawOnStepBlock(g, addPX, addPY);
					}
				}
			}
		}

		
		g.setColor(ApoMonoConstants.DARK[0], ApoMonoConstants.DARK[1], ApoMonoConstants.DARK[2], 1f);
		boolean bSelect = false;
		if (this.curSelected == 0) {
			bSelect = true;
		}
		this.drawInputEmptyBox(g, bSelect);
		
		bSelect = false;
		if (this.curSelected == 1) {
			bSelect = true;
		}
		this.drawInputWallBox(g, bSelect);
		
		bSelect = false;
		if (this.curSelected == 2) {
			bSelect = true;
		}
		this.drawInputPlayerBox(g, bSelect);
		
		bSelect = false;
		if (this.curSelected == 3) {
			bSelect = true;
		}
		this.drawInputGoalBox(g, bSelect);
		
		bSelect = false;
		if (this.curSelected == 4) {
			bSelect = true;
		}
		this.drawInputPickBox(g, bSelect);
		
		bSelect = false;
		if (this.curSelected == 5) {
			bSelect = true;
		}
		this.drawInputMirrorBox(g, bSelect);
		
		bSelect = false;
		if (this.curSelected == 6) {
			bSelect = true;
		}
		this.drawInputAirBox(g, bSelect);
		
		bSelect = false;
		if (this.curSelected == 7) {
			bSelect = true;
		}
		this.drawInputXBox(g, bSelect);
		
		bSelect = false;
		if (this.curSelected == 8) {
			bSelect = true;
		}
		this.drawInputMirrorStayBox(g, bSelect);
		
		bSelect = false;
		if (this.curSelected == 10) {
			bSelect = true;
		}
		this.drawInputSpikes(g, bSelect);
		
		bSelect = false;
		if (this.curSelected == 11) {
			bSelect = true;
		}
		this.drawInputBeamer(g, bSelect);
		
		bSelect = false;
		if (this.curSelected == 12) {
			bSelect = true;
		}
		this.drawInputOneStep(g, bSelect);
		
		if (this.bMenu) {
			g.setColor(ApoMonoConstants.DARK[0], ApoMonoConstants.DARK[1], ApoMonoConstants.DARK[2], 1f);
			g.fillRect(170, 45, 140, 200);
			ApoMonoPuzzleGame.setDarkerColor(g);
			g.fillRect(168, 45, 2, 200);
			g.fillRect(310, 45, 2, 200);
			g.fillRect(170, 43, 140, 2);
			g.fillRect(170, 245, 140, 2);
			ApoMonoPuzzleGame.setBrighterColor(g);
			g.fillRect(312, 47, 2, 200);
			g.fillRect(310, 245, 2, 2);
			g.fillRect(172, 247, 140, 2);
			
			g.setColor(ApoMonoConstants.BRIGHT_DARK[0], ApoMonoConstants.BRIGHT_DARK[1], ApoMonoConstants.BRIGHT_DARK[2], 0.5f);
			g.fillRect(231, 55, 6, 8);
			g.fillRect(230, 56, 8, 6);
			
			g.fillRect(237, 120, 6, 8);
			g.fillRect(236, 121, 8, 6);
			g.setColor(ApoMonoConstants.BRIGHT_DARK[0], ApoMonoConstants.BRIGHT_DARK[1], ApoMonoConstants.BRIGHT_DARK[2], 1f);
			g.fillRect(242, 55, 6, 8);
			g.fillRect(241, 56, 8, 6);
			
			g.fillRect(237, 131, 6, 8);
			g.fillRect(236, 132, 8, 6);
			for (int i = 0; i < 10; i+= 2) {
				g.fillRect(239, 55 + i, 1, 1);
				g.fillRect(235 + i, 129, 1, 1);
			}
			g.fillRect(237, 190 + frameGravity/2, 6, 8);
			g.fillRect(236, 190 + frameGravity/2 + 1, 8, 6);

			this.drawInputLeftBoxForMirror(g, 190, 70);
			this.drawInputRightBoxForMirror(g, 255, 70);
			
			String s = String.valueOf(p[1]);
			this.getGame().drawString(g, s, (int)(240 - ApoMonoPanel.game_font.getLength(s)/2), 80, ApoMonoPanel.game_font, ApoMonoConstants.BRIGHT);			
			
			this.drawInputLeftBoxForMirror(g, 190, 140);
			this.drawInputRightBoxForMirror(g, 255, 140);

			s = String.valueOf(p[7]);
			this.getGame().drawString(g, s, (int)(240 - ApoMonoPanel.game_font.getLength(s)/2), 150, ApoMonoPanel.game_font, ApoMonoConstants.BRIGHT);			

			this.drawInputLeftBoxForMirror(g, 190, 210);
			this.drawInputRightBoxForMirror(g, 255, 210);
			
			s = String.valueOf(p[8]);
			this.getGame().drawString(g, s, (int)(240 - ApoMonoPanel.game_font.getLength(s)/2), 220, ApoMonoPanel.game_font, ApoMonoConstants.BRIGHT);			
		}
		this.getGame().drawButtons(g, ApoMonoPanel.game_font, 2);
		
		if (this.monoString != null) {
			this.monoString.render(g, 0, 0);
		}
	}

	private void drawInputEmptyBox(final BitsGLGraphics g, final boolean bSelect) {
		int x = 5;
		int y = ApoMonoPuzzleGame.changeY + 245;
		this.drawInputBox(g, x, y, bSelect);
	}
	
	private void drawInputWallBox(final BitsGLGraphics g, final boolean bSelect) {
		int x = 45;
		int y = ApoMonoPuzzleGame.changeY + 245;
		this.drawInputBox(g, x, y, bSelect);
		
		int addPX = x + 9;
		int addPY = y + 9;
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		g.fillRect(addPX, addPY + 2, 16, 12);
		g.fillRect(addPX + 2, addPY, 12, 16);
		g.setColor(ApoMonoConstants.DARK[0], ApoMonoConstants.DARK[1], ApoMonoConstants.DARK[2], 1f);
		g.fillRect(addPX + 6, addPY + 6, 4, 4);
	}
	
	private void drawInputPlayerBox(final BitsGLGraphics g, final boolean bSelect) {
		int x = 85;
		int y = ApoMonoPuzzleGame.changeY + 245;
		this.drawInputBox(g, x, y, bSelect);
		
		int addPX = x + 9;
		int addPY = y + 9;
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		g.fillRect(addPX + 5, addPY + 1, 6, 11);
		g.fillRect(addPX + 3, addPY + 2, 10, 2);
		g.fillRect(addPX + 11, addPY + 5, 2, 2);
		g.fillRect(addPX + 5, addPY + 12, 2, 5);
		g.fillRect(addPX + 9, addPY + 12, 2, 5);
	}
	
	private void drawInputGoalBox(final BitsGLGraphics g, final boolean bSelect) {
		int x = 125;
		int y = ApoMonoPuzzleGame.changeY + 245;
		this.drawInputBox(g, x, y, bSelect);
		
		int addPX = x + 9;
		int addPY = y + 9;
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		ApoMonoPuzzleGame.drawFinish(g, addPX, addPY, this.frameGoal);
	}
	
	private void drawInputPickBox(final BitsGLGraphics g, final boolean bSelect) {
		int x = 165;
		int y = ApoMonoPuzzleGame.changeY + 245;
		this.drawInputBox(g, x, y, bSelect);
		
		int addPX = x + 9;
		int addPY = y + 9;
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		g.fillRect(addPX, addPY + 2, 16, 12);
		g.fillRect(addPX + 2, addPY, 12, 16);
		g.setColor(ApoMonoConstants.DARK[0], ApoMonoConstants.DARK[1], ApoMonoConstants.DARK[2], 1f);
		g.fillRect(addPX + 3, addPY + 3, 10, 10);
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		g.fillRect(addPX + 5, addPY + 5, 6, 6);
	}
	
	private void drawInputMirrorBox(final BitsGLGraphics g, final boolean bSelect) {
		int x = 205;
		int y = ApoMonoPuzzleGame.changeY + 245;
		this.drawInputBox(g, x, y, bSelect);
		
		int addPX = x + 9;
		int addPY = y + 9;
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		g.fillRect(addPX, addPY + 2, 16, 12);
		g.fillRect(addPX + 2, addPY, 12, 16);
	}
	
	private void drawInputAirBox(final BitsGLGraphics g, final boolean bSelect) {
		int x = 245;
		int y = ApoMonoPuzzleGame.changeY + 245;
		this.drawInputBox(g, x, y, bSelect);
		
		int addPX = x + 9;
		int addPY = y + 9;
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		g.fillRect(addPX + 6, addPY + 6, 4, 4);
	}
	
	private void drawInputXBox(final BitsGLGraphics g, final boolean bSelect) {
		int x = 285;
		int y = ApoMonoPuzzleGame.changeY + 245;
		this.drawInputBox(g, x, y, bSelect);
		
		int addPX = x + 9;
		int addPY = y + 9;
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		g.drawLine(addPX + 4, addPY + 4, addPX + 12, addPY + 12);
		g.drawLine(addPX + 12, addPY + 4, addPX + 4, addPY + 12);
	}
	
	private void drawInputMirrorStayBox(final BitsGLGraphics g, final boolean bSelect) {
		int x = 325;
		int y = ApoMonoPuzzleGame.changeY + 245;
		this.drawInputBox(g, x, y, bSelect);
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		int addPX = x + 9;
		int addPY = y + 9;
		
		g.fillRect(addPX, addPY + 2, 16, 12);
		g.fillRect(addPX + 2, addPY, 12, 16);
		g.setColor(ApoMonoConstants.DARK[0], ApoMonoConstants.DARK[1], ApoMonoConstants.DARK[2], 1f);
		g.fillRect(addPX + 3, addPY + 6, 10, 4);
		g.fillRect(addPX + 6, addPY + 3, 4, 10);
	}
	
	private void drawInputSpikes(final BitsGLGraphics g, final boolean bSelect) {
		int x = 365;
		int y = ApoMonoPuzzleGame.changeY + 245;
		this.drawInputBox(g, x, y, bSelect);
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		int addPX = x + 9;
		int addPY = y + 9;
		
		ApoMonoPuzzleGame.drawSpikes(g, addPX, addPY);
	}
	
	private void drawInputBeamer(final BitsGLGraphics g, final boolean bSelect) {
		int x = 405;
		int y = ApoMonoPuzzleGame.changeY + 245;
		this.drawInputBox(g, x, y, bSelect);
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		int addPX = x + 9;
		int addPY = y + 9;
		
		ApoMonoPuzzleGame.drawBeamer(g, addPX, addPY, this.beamerFrame, false, ApoMonoConstants.BRIGHT_DARK);
	}
	
	private void drawInputOneStep(final BitsGLGraphics g, final boolean bSelect) {
		int x = 445;
		int y = ApoMonoPuzzleGame.changeY + 245;
		this.drawInputBox(g, x, y, bSelect);
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		int addPX = x + 9;
		int addPY = y + 9;
		
		ApoMonoPuzzleGame.drawOnStepBlock(g, addPX, addPY);
	}
	
	private void drawInputBox(final BitsGraphics g, int x, int y, final boolean bActive) {
		this.drawInputBox(g, x, y, 34, 34, bActive);
	}
	
	private void drawInputBox(final BitsGraphics g, int x, int y, final int width, final int height, final boolean bActive) {
		g.setColor(ApoMonoConstants.DARK[0], ApoMonoConstants.DARK[1], ApoMonoConstants.DARK[2], 1f);
		g.fillRect(x + 2, y + 2, width - 4, height - 4);
		ApoMonoPuzzleGame.setDarkerColor(g);
		if ((p[5] > x) && (p[5] < x + width) && (p[6] > y) && (p[6] < y + height)) {
			g.setColor(ApoMonoConstants.BRIGHT_DARK[0], ApoMonoConstants.BRIGHT_DARK[1], ApoMonoConstants.BRIGHT_DARK[2], 1f);			
		}
		g.fillRect(x + 2, y, width - 4, 2);
		g.fillRect(x + 2, y + height - 2, width - 4, 2);
		g.fillRect(x, y + 2, 2, height - 4);
		g.fillRect(x + width - 2, y + 2, 2, height - 4);
		ApoMonoPuzzleGame.setBrighterColor(g);
		g.fillRect(x + 4, y + height, width - 4, 2);
		g.fillRect(x + width, y + 4, 2, height - 4);
		g.fillRect(x + width - 2, y + height - 2, 2, 2);
		if (bActive) {
			g.setColor(1f, 0f, 0f, 1f);
			g.setLineSize(1.5f * ApoMonoConstants.MAX);
			g.drawRect(x, y, width, height);
			g.setLineSize(1);
		}
	}
	
	private void drawInputLeftBoxForMirror(final BitsGLGraphics g, int x, int y) {
		this.drawInputBoxForLevelchooser(g, x, y, 30, 30);
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		for (int i = 0; i < 10; i++) {
			g.fillRect(x + 18 - i * 1, y + 9 + i * 0.5f, 2, 10 - i * 1);
		}
	}
	
	private void drawInputRightBoxForMirror(final BitsGLGraphics g, final int x, final int y) {
		this.drawInputBoxForLevelchooser(g, x, y, 30, 30);
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		for (int i = 0; i < 10; i++) {
			g.fillRect(x + 10 + i * 1, y + 9 + i * 0.5f, 2, 10 - i * 1);
		}
	}
	
	private void drawInputBoxForLevelchooser(final BitsGLGraphics g, int x, int y, final int width, final int height) {
		g.setColor(ApoMonoConstants.DARK[0], ApoMonoConstants.DARK[1], ApoMonoConstants.DARK[2], 1f);
		g.fillRect(x + 2, y + 2, width - 4, height - 4);
		ApoMonoPuzzleGame.setDarkerColor(g);
		g.fillRect(x + 2, y, width - 4, 2);
		g.fillRect(x + 2, y + height - 2, width - 4, 2);
		g.fillRect(x, y + 2, 2, height - 4);
		g.fillRect(x + width - 2, y + 2, 2, height - 4);
		ApoMonoPuzzleGame.setBrighterColor(g);
		g.fillRect(x + 4, y + height, width - 4, 2);
		g.fillRect(x + width, y + 4, 2, height - 4);
		g.fillRect(x + width - 2, y + height - 2, 2, 2);
	}
}
