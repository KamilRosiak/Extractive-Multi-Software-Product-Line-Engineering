package net.apogames.apomono.game;

import java.util.ArrayList;

import net.apogames.apomono.ApoMonoConstants;
import net.apogames.apomono.ApoMonoModel;
import net.apogames.apomono.ApoMonoSoundPlayer;
import net.apogames.apomono.entity.ApoMonoSpeech;
import net.apogames.apomono.entity.ApoParticle;
import net.apogames.apomono.level.ApoMonoLevel;
import net.gliblybits.bitsengine.graphics.BitsGraphics;
import net.gliblybits.bitsengine.graphics.bitmap.BitsBitmap;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLFactory;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLImage;
import net.gliblybits.bitsengine.input.BitsKeyEvent;

public class ApoMonoPuzzleGame extends ApoMonoModel {

	public static final String BACK = "back";
	public static final String RETRY = "retry";
	
	public static final int SPEECH_MAX_TIME = 4000;
	public static final int SPEECH_NOMOVE_TIME = 10000;
	public static final int BEAMER_CHANGE_TIME = 400;
	public static final int MIRROR_CHANGE_TIME = 200;
	public static final int FRAME_TIME = 400;
	public static final int FRAME_GRAVITY_TIME = 1000;
	
	/**
	 * p[0] == aktuelles Level
	 * p[1] == mirrorcount horizontal
	 * p[2] == aktuelles Level geschafft klick
	 * p[3] == mirrortime
	 * p[4] == time
	 * p[5] == x Wert touch
	 * p[6] == y Wert touch
	 * p[7] == Zeit f�r Drehung
	 * p[8] == mirrorcount vertical
	 * p[9] == gravitychangecount
	 */
	private final int[] p = new int[10];
	
	private boolean bEditor = false;
	private boolean bUserlevel = false;
	
	private String levelString = "";
	
	private int[][] level = new int[1][1];
	private int[][] falla = new int[1][1];
	private int[][] boxes = new int[1][1];
	
	public static final int changeY = 40;
	
	private int add, x, y, i, playerX, playerY, addLeft, fall, addX, addY, mirrorX, mirrorY, withBox, playerStartX, playerStartY;
	
	private BitsGLImage iMirror, iBackground;
	
	private int gravity = 1;
	
	private ArrayList<ApoParticle> particles;
	
	private int frameGoal, frameTimeGoal, mirrorChange, speechShowTime, noMoveTime, noMoveTimeNext, beamerFrame, beamerFrameTime;
	private float frameGravity, frameGravityAdd, frameGravityTime;
	
	private ApoMonoSpeech speech;
	
	private ArrayList<int[][]> backsteps;
	
	private int backgroundWait = 0;
	
	private boolean bCanExplodeSound;
	
	public ApoMonoPuzzleGame(ApoMonoPanel game) {
		super(game);
	}

	@Override
	public void init() {
		if (level.length < 10) {
			this.level[0][0] = -1;
			this.frameGoal = 0;
			this.frameGravity = 0;
			this.frameGravityAdd = 1;
			this.frameGravityTime= 0; 
		}
		if (this.particles == null) {
			this.particles = new ArrayList<ApoParticle>();
		} else {
			this.particles.clear();
		}
		if (this.backsteps == null) {
			this.backsteps = new ArrayList<int[][]>();
		} else {
			this.backsteps.clear();
		}
		if (this.speech == null) {
			this.speech = new ApoMonoSpeech(this.getGame(), 0, 0, false, true, "");
			this.speech.setVisible(false);
		}
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
	}

	@Override
	public void touchedButton(String function) {
		if (function.equals(ApoMonoPuzzleGame.BACK)) {
			this.onBackButtonPressed();
		} else if (function.equals(ApoMonoPuzzleGame.RETRY)) {
			this.loadLevel(p[0], this.bUserlevel, this.levelString);
		}
		this.getGame().playSound(ApoMonoSoundPlayer.SOUND_BUTTON);
	}
	
	public void onKeyDown(final int key) {
		if (key == BitsKeyEvent.KEY_LEFT) {
			p[5] = 25;
			p[6] = changeY + 250;
		} else if (key == BitsKeyEvent.KEY_RIGHT) {
			p[5] = 75;
			p[6] = changeY + 250;
		} else if (key == BitsKeyEvent.KEY_L) {
			p[5] = 125;
			p[6] = changeY + 250;
		} else if ((key == BitsKeyEvent.KEY_UP) || (key == BitsKeyEvent.KEY_DOWN)) {
			p[5] = ApoMonoConstants.GAME_WIDTH - 30;
			p[6] = changeY + 250;
			if (this.mirrorY >= 0) {
				if (key == BitsKeyEvent.KEY_UP) {
					p[5] = 25;
				} else if (key == BitsKeyEvent.KEY_DOWN) {
					p[5] = 75;
				}
			}
		} else if (((key == BitsKeyEvent.KEY_SPACE) || (key == BitsKeyEvent.KEY_SHIFT_RIGHT)) && (p[1] > 0)) {
			p[5] = ApoMonoConstants.GAME_WIDTH - 80;
			p[6] = changeY + 250;
		} else if ((key == BitsKeyEvent.KEY_ENTER) && (p[8] > 0)) {
			p[5] = ApoMonoConstants.GAME_WIDTH - 130;
			p[6] = changeY + 250;
		} else if ((key == BitsKeyEvent.KEY_ESCAPE) ||  (key == BitsKeyEvent.KEY_CTRL_RIGHT)) {
			if ((this.mirrorX >= 0) || (this.mirrorY >= 0)) {
				p[5] = ApoMonoConstants.GAME_WIDTH - 180;
				p[6] = changeY + 250;
			} else {
				this.onBackButtonPressed();
			}
		} else if (key == BitsKeyEvent.KEY_B) {
			p[5] = ApoMonoConstants.GAME_WIDTH/2;
			p[6] = changeY + 250;
		} else if (key == BitsKeyEvent.KEY_R) {
			this.touchedButton(ApoMonoPuzzleGame.RETRY);
		} else if (key == BitsKeyEvent.KEY_P) {
			p[5] = ApoMonoConstants.GAME_WIDTH/2 - 95;
			p[6] = 10;
		} else if (key == BitsKeyEvent.KEY_N) {
			p[5] = ApoMonoConstants.GAME_WIDTH/2 + 95;
			p[6] = 10;
		}
	}

	public void onKeyUp(final int key) {
		if ((key == BitsKeyEvent.KEY_LEFT) || (key == BitsKeyEvent.KEY_RIGHT) || 
			(key == BitsKeyEvent.KEY_UP) || (key == BitsKeyEvent.KEY_DOWN) || 
			(key == BitsKeyEvent.KEY_SPACE) || (key == BitsKeyEvent.KEY_R) || 
			(key == BitsKeyEvent.KEY_P) || (key == BitsKeyEvent.KEY_N) || 
			(key == BitsKeyEvent.KEY_ESCAPE) || (key == BitsKeyEvent.KEY_SHIFT_RIGHT) ||
			(key == BitsKeyEvent.KEY_CTRL_RIGHT)) {
			if ((key == BitsKeyEvent.KEY_LEFT) || (key == BitsKeyEvent.KEY_RIGHT)) {
					this.mirrorChange = ApoMonoPuzzleGame.MIRROR_CHANGE_TIME + 1;
			}
			p[5] = -1;
			p[6] = -1;
		}
	}
	
	public void onBackButtonPressed() {
		if (this.bEditor) {
			this.getGame().setEditor(false);
		} else {
			if (this.bUserlevel) {
				this.getGame().setMenu();
			} else {
				this.getGame().setLevelChooser();
			}
		}
	}
	
	public void loadLevel(final int nextLevel, final boolean bUserLevel, final String levelString) {
		this.bUserlevel = bUserLevel;
		if (ApoMonoLevel.editorLevels == null) {
			this.bUserlevel = false;
		}
		p[0] = nextLevel;
		if (p[0] < 0) {
			if (this.bUserlevel) {
				p[0] = ApoMonoLevel.editorLevels.length - 1;
			} else {
				p[0] = this.getGame().getMaxCanChoosen();
			}
		}
		if (this.bUserlevel) {
			if (p[0] >= ApoMonoLevel.editorLevels.length) {
				p[0] = 0;
			}
		} else if (p[0] >= this.getGame().getMaxCanChoosen() + 1) {
			p[0] = 0;
		}
		this.bEditor = false;
		String l = "";
		if ((levelString != null) && (levelString.length() > 0)) {
			l = levelString;
			this.bEditor = true;
		} else {
			if (this.bUserlevel) {
				l = ApoMonoLevel.editorLevels[p[0]];
			} else {
				l = ApoMonoLevel.getLevel(p[0]);
			}
		}
		this.levelString = levelString;
		
		boxes = new int[15][30];
		falla = new int[15][30];
		level = new int[15][30];
		p[1] = Integer.valueOf(l.substring(0, 1));
		p[8] = Integer.valueOf(l.substring(1, 2));
		p[9] = Integer.valueOf(l.substring(2, 3));
		int cur = 3;
		for (y = 0; y < level.length; y++) {
			level[y][0] = 0;
			level[y][level[0].length - 1] = 0;
			for (x = 0; x < level[0].length - 2; x++) {
				char c = l.charAt(cur);
				if (c >= 97) {
					level[y][x+1] = Integer.valueOf(c) - 87;
				} else {
					level[y][x+1] = Integer.valueOf(c) - 48;
				}
				cur += 1;
				if (level[y][x+1] == 2) {
					playerX = x + 1;
					playerY = y;
					this.playerStartX = playerX;
					this.playerStartY = playerY;
					level[y][x+1] = 0;
				}
				if (level[y][x+1] == 9) {
					for (x = 0; x < level[0].length - 2; x++) {
						level[y][x+1] = 0;
					}
					break;
				}
				if (level[y][x+1] == 7) {
					boxes[y][x+1] = 7;
				} else if (level[y][x+1] == 6) {
					boxes[y][x+1] = 6;
				} else if (level[y][x+1] == 11) {
					boxes[y][x+1] = 11;
					level[y][x+1] = 0;
				}
			}
		}
		this.speechShowTime = this.noMoveTime = withBox = addLeft = addX = addY = fall = add = p[2] = p[3] = p[4] = p[5] = p[6] = 0;
		this.mirrorX = this.mirrorY = -1;
		this.gravity = 1;
		this.backgroundWait = 0;
		this.speech.setVisible(false);
		this.setNoMoveTimeNext();
		this.backsteps.clear();
		this.makeBackground();
		this.saveNextBackstep();
	}
	
	private void setPreviousStep() {
		if (this.backsteps.size() > 0) {
			int[][] backLevel = this.backsteps.get(this.backsteps.size() - 1);
			
			int[][] previousLevel = new int[backLevel.length][backLevel[0].length];
			for (int y = 0; y < backLevel.length; y++) {
				for (int x = 0; x < backLevel[0].length; x++) {
					previousLevel[y][x] = backLevel[y][x];
				}
			}
		
			String val = String.valueOf(previousLevel[0][0]);
			this.p[1] = Integer.valueOf(val.substring(1, 2));
			this.p[8] = Integer.valueOf(val.substring(2, 3));
			this.p[9] = Integer.valueOf(val.substring(3, 4));
			int grav = Integer.valueOf(val.substring(4, 5));
			if (grav == 2) {
				grav = -1;
			}
			
			this.gravity = grav;
			previousLevel[0][0] = Integer.valueOf(val.substring(5));
			
			for (int y = 0; y < this.level.length; y++) {
				for (int x = 0; x < this.level[0].length; x++) {
					if (previousLevel[y][x] < 50) {
						level[y][x] = previousLevel[y][x];
					} else {
						level[y][x] = 0;
						this.playerY = y;
						this.playerX = x;
						if (previousLevel[y][x] >= 55) {
							this.withBox = 1;
						} else {
							this.withBox = 0;
						}
						this.add = 0;
						if ((previousLevel[y][x] == 51) || (previousLevel[y][x] == 56)) {
							this.add = 1;
						}
					}
				}
			}
			for (int y = 0; y < this.boxes.length; y++) {
				for (int x = 0; x < this.boxes[0].length; x++) {
					this.boxes[y][x] = previousLevel[y + this.boxes.length][x];
				}
			}
			
			if (this.backsteps.size() > 1) {
				this.backsteps.remove(this.backsteps.size() - 1);
			}
			this.mirrorX = this.mirrorY = -1;
			this.makeBackground();
		}
	}
	
	private void saveNextBackstep() {
		int grav = 1;
		if (this.gravity < 0) {
			grav = 2;
		}
		String firstValue = "1" + this.p[1] + "" + p[8] + "" + p[9] + "" + grav + "" + level[0][0];
		int first = Integer.valueOf(firstValue);
		int[][] backLevel = new int[level.length * 2][level[0].length];
		backLevel[0][0] = first;
		for (int y = 0; y < this.level.length; y++) {
			for (int x = 0; x < this.level[0].length; x++) {
				if ((y != 0) || (x != 0)) {
					backLevel[y][x] = level[y][x];
				}
			}
		}
		for (int y = 0; y < this.level.length; y++) {
			for (int x = 0; x < this.level[0].length; x++) {
				backLevel[y + this.level.length][x] = this.boxes[y][x];
			}
		}
		int value = 50 + this.add;
		if (this.withBox != 0) {
			value += 5;
		}
		backLevel[this.playerY][this.playerX] = value;
		
		this.hideSpeech();
		this.backsteps.add(backLevel);
	}
	
	private void setNoMoveTimeNext() {
		this.noMoveTime = 0;
		this.noMoveTimeNext = ApoMonoPuzzleGame.SPEECH_NOMOVE_TIME + (int)(Math.random() * 10000);
	}
	
	private void makeBackground() {
 		ApoMonoPuzzleGame.this.loadAndMakeBackground();
 		backgroundWait = 200;
	}
	
	public void onResume() {
		if (this.iBackground == null) {
			this.makeBackground();
			if ((this.mirrorX >= 0) || (this.mirrorY >= 0)) {
				this.makeMirrrorImage();
			}
		}
	}
	
	private void loadAndMakeBackground() {
		int scale = 1;
		boolean bScale = false;
		if (ApoMonoConstants.MAX >= 10) {
			scale = 2;
			bScale = true;
		}
		BitsBitmap bitmap = new BitsBitmap(512 * scale, 256 * scale, BitsBitmap.TYPE_ARGB);
		BitsGraphics g = bitmap.createGraphics();
		
		g.setColor(0f,0f,0f, 1f);
		for (int y = 0; y < level.length; y++) {
			for (int x = 0; x < level[0].length; x++) {
				int addPX = x * 16 * scale;
				int addPY = y * 16 * scale;
				
				if ((level[y][x] != 0) && (level[y][x] != 4) && (level[y][x] != 3)) {
					if (level[y][x] == 1) {
						g.setColor(ApoMonoConstants.DARK[0], ApoMonoConstants.DARK[1], ApoMonoConstants.DARK[2], 1f);
						ApoMonoPuzzleGame.drawBlock(g, addPX, addPY, bScale);
					} else {
						g.setColor(ApoMonoConstants.DARK_BRIGHT[0], ApoMonoConstants.DARK_BRIGHT[1], ApoMonoConstants.DARK_BRIGHT[2], 1f);
						if (level[y][x] == 8) {
							ApoMonoPuzzleGame.drawBlockPlusMirror(g, addPX, addPY, bScale);
						}
						if (level[y][x] == 7) {
							ApoMonoPuzzleGame.drawX(g, addPX, addPY, bScale);
						}
						if (p[3] > 0) {
							if (this.mirrorX >= 0) {
								if (x >= this.mirrorX) {
									continue;
								}
							} else if (this.mirrorY >= 0) {
								if (y >= this.mirrorY) {
									continue;
								}
							}
						}
						g.setColor(ApoMonoConstants.DARK_BRIGHT[0], ApoMonoConstants.DARK_BRIGHT[1], ApoMonoConstants.DARK_BRIGHT[2], 1f);
						if (level[y][x] == 5) {
							ApoMonoPuzzleGame.drawBox(g, addPX, addPY, false, bScale);
						}
						if (level[y][x] == 6) {
							ApoMonoPuzzleGame.drawAir(g, addPX, addPY, bScale);
						}
						if (level[y][x] == 10) {
							ApoMonoPuzzleGame.drawSpikes(g, addPX, addPY, bScale);
						}
					}
				}
			}
		}
		g.release();
		
		//release old image
		if (this.iBackground != null) {
			BitsGLFactory.getInstance().markForReleasing(this.iBackground);
			BitsGLFactory.getInstance().releaseAllMarked();
		}
		
		//get new image
		this.iBackground = BitsGLFactory.getInstance().getImage(bitmap, BitsGLImage.FILTER_NEAREST, true);
//		BitsGLFactory.getInstance().markForLoading(this.iBackground);
		BitsGLFactory.getInstance().loadAllMarked();
	}
	
	private void makeMirrrorImage() {
		int width = this.level[0].length * 16;
		BitsBitmap bitmap = new BitsBitmap(512, 256, BitsBitmap.TYPE_ARGB);
		BitsGraphics g = bitmap.createGraphics();
		
		g.setColor(1f,1f,1f, 1f);
		for (y = 0; y < level.length; y++) {
			for (x = 0; x < level[0].length; x++) {
				int addPX = width - (x + 1) * 16;
				int addPY = y * 16;

				g.setColor(ApoMonoConstants.DARK[0], ApoMonoConstants.DARK[1], ApoMonoConstants.DARK[2], 1f);
				if ((y == playerY) && (x == playerX)) {
					ApoMonoPuzzleGame.renderPlayer(g, addPX, addPY, this.add, this.gravity, this.withBox);
				}
				
				if (level[y][x] != 0) {
					g.setColor(ApoMonoConstants.DARK_BRIGHT[0], ApoMonoConstants.DARK_BRIGHT[1], ApoMonoConstants.DARK_BRIGHT[2]);
					if ((level[y][x] == 4) || (level[y][x] == 5) || (level[y][x] == 8)) {
						if (level[y][x] == 4) {
							ApoMonoPuzzleGame.drawMoveableBox(g, addPX, addPY);
						}
						if (level[y][x] == 5) {
							ApoMonoPuzzleGame.drawBox(g, addPX, addPY, false);
						}
						if (level[y][x] == 8) {
							ApoMonoPuzzleGame.drawBlockPlusMirror(g, addPX, addPY);
						}
					}
					g.setColor(ApoMonoConstants.DARK_BRIGHT[0], ApoMonoConstants.DARK_BRIGHT[1], ApoMonoConstants.DARK_BRIGHT[2]);
					if (level[y][x] == 6) {
						ApoMonoPuzzleGame.drawAir(g, addPX, addPY);
					}
					if (level[y][x] == 10) {
						g.setColor(ApoMonoConstants.BRIGHT_DARK[0], ApoMonoConstants.BRIGHT_DARK[1], ApoMonoConstants.BRIGHT_DARK[2]);
						ApoMonoPuzzleGame.drawSpikes(g, addPX, addPY);
					}
				}
			}
		}
		g.release();
		
		//release old image
		this.releaseImage();
		
		//get new image
		this.iMirror = BitsGLFactory.getInstance().getImage(bitmap, BitsGLImage.FILTER_NEAREST, true);
//		BitsGLFactory.getInstance().markForLoading(this.iMirror);
		BitsGLFactory.getInstance().loadAllMarked();
	}
	
	private void addParticle(int x, int y) {
		this.particles.add(new ApoParticle(x, y, (float)(-0.05f + Math.random() * 0.03f), -0.05f));
		this.particles.add(new ApoParticle(x, y, (float)(-0.05f + Math.random() * 0.03f), -0.03f));
		this.particles.add(new ApoParticle(x, y, (float)(0.02f + Math.random() * 0.03f), -0.05f));
		this.particles.add(new ApoParticle(x, y, (float)(0.02f + Math.random() * 0.03f), -0.03f));

		if (this.bCanExplodeSound) {
			this.getGame().playSound(ApoMonoSoundPlayer.SOUND_LOSE);
			this.bCanExplodeSound = false;
		}
	}

	private void releaseImage() {
		if ((this.iMirror != null) && (this.iMirror.isLoaded)) {
			BitsGLFactory.getInstance().markForReleasing(this.iMirror);
			BitsGLFactory.getInstance().releaseAllMarked();
		}
	}
	
	@Override
	public void think(int delta) {
		this.bCanExplodeSound = true;
		this.beamerFrameTime += delta;
		if (this.beamerFrameTime >= ApoMonoPuzzleGame.BEAMER_CHANGE_TIME) {
			this.beamerFrameTime -= ApoMonoPuzzleGame.BEAMER_CHANGE_TIME;
			this.beamerFrame += 1;
			if (this.beamerFrame > 3) {
				this.beamerFrame = 0;
			}
		}
		
		if (this.speech == null) {
			this.speech = new ApoMonoSpeech(this.getGame(), 0, 0, false, true, "");
			this.speech.setVisible(false);
		}
		
		if (this.backgroundWait > 0) {
			this.backgroundWait -= delta;
		}
		
		if (this.p[2] == 0) {
			this.noMoveTime += delta;
			if (noMoveTime >= this.noMoveTimeNext) {
				int text = (int)(Math.random() * ApoMonoConstants.TEXT_IDLE.length);
				this.showSpeech(ApoMonoConstants.TEXT_IDLE[text]);
				this.setNoMoveTimeNext();
			}
		}
		if (this.speechShowTime > 0) {
			this.speechShowTime -= delta;
			if ((this.speechShowTime <= 0) && (this.speech != null) && (this.speech.isVisible())) {
				this.speech.setVisible(false);
			}
		}
		if (this.mirrorChange <= ApoMonoPuzzleGame.MIRROR_CHANGE_TIME) {
			this.mirrorChange += delta;
		}
		if (this.frameGravityTime > 0) {
			this.frameGravityTime -= delta;
		} else {
			this.frameGravity += 0.1f * this.frameGravityAdd;
			if ((this.frameGravity > 16) || (this.frameGravity < 0)) {
				this.frameGravityAdd = -this.frameGravityAdd;
				this.frameGravityTime = FRAME_GRAVITY_TIME;
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
		
		for (int k = this.particles.size() - 1; k >= 0; k--) {
			this.particles.get(k).think(delta);
			if (!this.particles.get(k).isVisible()) {
				this.particles.remove(k);
			}
		}
		p[4] += 10;
		if (p[7] > 0) {
			p[7] -= 10;
		}
		if (level[0][0] == -1) {
			this.loadLevel(p[0], this.bUserlevel, this.levelString);
		} else if (p[2] >= 1) {
			if (p[4] < 256) {
				return;
			}
			boolean bSolved = false;
			if (p[5] > 0) {
				bSolved = true;
				p[5] = -1;
			}
			if (bSolved) {
				if (p[2] == 1) {
					p[0] += 1;
					if (bEditor) {
						this.getGame().setEditor(true);
						return;
					}
				}
				this.loadLevel(p[0], this.bUserlevel, this.levelString);
				return;
			}
		} else {
			if (p[3] > 0) {
				p[3] -= 10;
				if (p[3] == 0) {
					if (this.mirrorX >= 0) {
						for (y = 0; y < level.length; y++) {
							for (x = this.mirrorX; x < level[0].length; x++) {
								if (this.boxes[y][x] == 6) {
									this.boxes[y][x] = 0;
								}
							}
						}
						for (y = 0; y < level.length; y++) {
							for (x = 0; x < level[0].length; x++) {
								if ((mirrorX + x < level[0].length) && (level[y][mirrorX + x] >= 4) && (level[y][mirrorX + x] != 12) && (level[y][mirrorX + x] < 20)) {
									if ((mirrorX - x - 1 >= 0) && (mirrorX + x < level[0].length)) {
										if ((level[y][mirrorX - 1 - x] != level[y][mirrorX + x]) && ((level[y][mirrorX - 1 - x] > 0) || (boxes[y][mirrorX - 1 - x] == 11))) {
											this.addParticle((mirrorX - 1 - x) * 16 + 8, y * 16 + changeY + 8);
										}
										if ((level[y][mirrorX - 1 - x] != 7) && (level[y][mirrorX - 1 - x] != 3)) {
											if (boxes[y][mirrorX - 1 - x] == 11) {
												if (level[y][mirrorX + x] == 4) {
													level[y][mirrorX + x] = 0;
													level[y][mirrorX - 1 - x] = 4;
													this.beamValue(mirrorX - 1 - x, y);
												}
												/** TODO BEAMER */
											} else if ((level[y][mirrorX + x] != 7) && (level[y][mirrorX + x] != 11)) {
												level[y][mirrorX - 1 - x] = level[y][mirrorX + x];
												if ((boxes[y][mirrorX + x] == 6) || (level[y][mirrorX - 1 - x] == 6)) {
													boxes[y][mirrorX - 1 - x] = 6;
												} else if ((level[y][mirrorX - 1 - x] == 10) && (withBox != 0) && (playerY - this.gravity == y) && (playerX == mirrorX - 1 - x)) {
													withBox = 0;
													this.addParticle((mirrorX - 1 - x) * 16 + 8, y * 16 + changeY + 8);
												}
											}
										}
										if (level[y][mirrorX - 1 - x] != 8) {
											level[y][mirrorX + x] = boxes[y][mirrorX + x];
										}
										
									} else if ((mirrorX - x - 1 < 0) && (mirrorX + x < level[0].length) && (level[y][mirrorX + x] != 8)) {
										level[y][mirrorX + x] = boxes[y][mirrorX + x];
									}
									if (level[y][mirrorX + x] == 7) {
										boxes[y][mirrorX + x] = level[y][mirrorX + x] = 7;
									}
								}
								if ((y == playerY) && (mirrorX + x == playerX)) {
									playerX = mirrorX - 1 - x;
									if ((playerX < 0) || (playerX >= level[0].length)) {
										this.lostLevel();
										playerX = 0;
									}
									if (mirrorX - 1 - x >= 0) {
										if ((level[y][mirrorX - 1 - x] == 7) || (level[y][mirrorX - 1 - x] == 10)) {
											this.lostLevel();
										}
										if (level[y][mirrorX - 1 - x] == 3) {
											this.winLevel();
										}
										if ((boxes[y][mirrorX - 1 - x] == 11) && (p[2] <= 0)) {
											/** TODO Beamer */
											this.beamValue(mirrorX - 1 - x, y);
										}
									}
								}
							}	
						}
						p[1] -= 1;
					} else if (this.mirrorY >= 0) {
						for (y = this.mirrorY; y < level.length; y++) {
							for (x = 0; x < level[0].length; x++) {
								if (this.boxes[y][x] == 6) {
									this.boxes[y][x] = 0;
								}
							}
						}
						for (y = 0; y < level.length; y++) {
							for (x = 0; x < level[0].length; x++) {
								if ((mirrorY + y < level.length) && (level[mirrorY + y][x] >= 4) && (level[mirrorY + y][x] != 12) && (level[mirrorY + y][x] < 20)) {
									if ((mirrorY - y - 1 >= 0) && (mirrorY + y < level.length)) {
										if ((level[mirrorY - 1 - y][x] != level[mirrorY + y][x]) && ((level[mirrorY - 1 - y][x] > 0) || (boxes[mirrorY - 1 - y][x] == 11))) {
											this.addParticle((x) * 16 + 8, (mirrorY - 1 - y) * 16 + changeY + 8);
										}
										if ((level[mirrorY - 1 - y][x] != 7) && (level[mirrorY - 1 - y][x] != 3)) {
											if (boxes[mirrorY - 1 - y][x] == 11) {
												if (level[mirrorY + y][x] == 4) {
													level[mirrorY + y][x] = 0;
													level[mirrorY - 1 - y][x] = 4;
													this.beamValue(x, mirrorY - 1 - y);
												}
												/** TODO BEAMER */
											} else if ((level[mirrorY + y][x] != 7) && (level[mirrorY + y][x] != 11)) {
												level[mirrorY - 1 - y][x] = level[mirrorY + y][x];
												if (level[mirrorY - 1 - y][x] == 6) {
													boxes[mirrorY - 1 - y][x] = 6;
												} else if ((level[mirrorY - 1 - y][x] == 10) && (withBox != 0) && (mirrorY - 1 - y - this.gravity == y) && (playerX == x)) {
													withBox = 0;
													this.addParticle((x) * 16 + 8, (mirrorY - 1 - y) * 16 + changeY + 8);
												}
											}
										}
										if (level[mirrorY - 1 - y][x] != 8) {
											level[mirrorY + y][x] = boxes[mirrorY + y][x];
										}
										
									} else if ((mirrorY - y - 1 < 0) && (mirrorY + y < level.length) && (level[mirrorY + y][x] != 8)) {
										level[mirrorY + y][x] = boxes[mirrorY + y][x];
									}
									if (level[mirrorY + y][x] == 7) {
										level[mirrorY + y][x] = 7;
									}
								}
								if ((x == playerX) && (mirrorY + y == playerY)) {
									playerY = mirrorY - 1 - y;
									if ((playerY < 0) || (playerY >= level.length)) {
										this.lostLevel();
										playerY = 0;
									}
									if (mirrorY - 1 - y >= 0) {
										if ((level[mirrorY - 1 - y][x] == 7) || (level[mirrorY - 1 - y][x] == 10)) {
											this.lostLevel();
										}
										if (level[mirrorY - 1 - y][x] == 3) {
											this.winLevel();
										}
										if ((p[2] <= 0) && (this.withBox > 0)) {
											if ((playerY + this.withBox * this.gravity >= 0) && (playerY + this.withBox * this.gravity < level.length)) {
												level[playerY + this.withBox * this.gravity][playerX] = 4;
												this.withBox = 0;
											}
										}
										if ((boxes[mirrorY - 1 - y][x] == 11) && (p[2] <= 0)) {
											/** TODO Beamer */
											this.beamValue(x, mirrorY - 1 - y);
										}
									}
								}
							}	
						}
						p[8] -= 1;
					}
					if (p[2] <= 0) {
						if ((level[playerY][playerX] == 1) || (level[playerY][playerX] == 12) || (level[playerY][playerX] == 8) || (level[playerY][playerX] == 4) || (level[playerY][playerX] == 5) || (level[playerY][playerX] == 10)) {
							this.lostLevel();
						}
					}
					
					mirrorX = -1;
					mirrorY = -1;
					this.makeBackground();
					if ((p[1] > 0) || (p[8] > 0)) {
						this.makeMirrrorImage();
					}
				}
			}
			
			int move = 0;
			int up = 0;
			int mirrorMode = 0;
			if (p[6] < changeY) {
				if ((p[5] > ApoMonoConstants.GAME_WIDTH/2 - 95) && (p[5] < ApoMonoConstants.GAME_WIDTH/2 - 55)) {
					this.loadLevel(p[0] - 1, this.bUserlevel, this.levelString);
					return;
				}
				if ((p[5] > ApoMonoConstants.GAME_WIDTH/2 + 55) && (p[5] < ApoMonoConstants.GAME_WIDTH/2 + 95)) {
					this.loadLevel(p[0] + 1, this.bUserlevel, this.levelString);
					return;
				}
			} else if (p[6] > changeY + 240) {
				if ((p[6] > changeY + 245) && (p[6] < changeY + 285)) {
					if ((p[5] > ApoMonoConstants.GAME_WIDTH/2 - 20) && (p[5] < ApoMonoConstants.GAME_WIDTH/2 + 20)) {
						if (p[3] <= 0) {
							if ((this.backsteps.size() > 1) || ((playerX != playerStartX) || (playerY != playerStartY))) {
								this.setPreviousStep();
							}
						}
						p[5] = -1;
						return;
					}
					if ((p[5] > 5) && (p[5] < 45) && (p[7] <= 0)) {
						move = -1;
						if ((mirrorX >= 0) || (mirrorY >= 0)) {
							if (this.mirrorChange < ApoMonoPuzzleGame.MIRROR_CHANGE_TIME) {
								move = 0;
							} else {
								this.mirrorChange -= ApoMonoPuzzleGame.MIRROR_CHANGE_TIME;
							}
						}
					} else if ((p[5] > ApoMonoConstants.GAME_WIDTH - 45) && (p[5] < ApoMonoConstants.GAME_WIDTH - 5)) {
						up = 1;
						p[5] = -1;
					} else if ((p[5] > 55) && (p[5] < 95) && (p[7] <= 0)) {
						move = +1;
						if ((mirrorX >= 0) || (mirrorY >= 0)) {
							if (this.mirrorChange < ApoMonoPuzzleGame.MIRROR_CHANGE_TIME) {
								move = 0;
							} else {
								this.mirrorChange -= ApoMonoPuzzleGame.MIRROR_CHANGE_TIME;
							}
						}
					} else if ((p[5] > ApoMonoConstants.GAME_WIDTH - 95) && (p[5] < ApoMonoConstants.GAME_WIDTH - 55)) {
						mirrorMode = 1;
						p[5] = -1;
					} else if ((p[5] > ApoMonoConstants.GAME_WIDTH - 195) && (p[5] < ApoMonoConstants.GAME_WIDTH - 155)) {
						mirrorMode = 2;
						p[5] = -1;
					} else if ((p[5] > ApoMonoConstants.GAME_WIDTH - 145) && (p[5] < ApoMonoConstants.GAME_WIDTH - 105)) {
						mirrorMode = 3;
						p[5] = -1;
					} else if ((p[5] > 105) && (p[5] < 145) && (p[9] > 0) && (fall == 0) && (this.mirrorX < 0) && (this.mirrorY < 0)) {
						p[9] -= 1;
						this.gravity = -this.gravity;
						if (this.withBox > 0) {
							withBox = 0;
							level[playerY + this.gravity][playerX] = 4;
						}
						p[5] = -1;
						this.getGame().playSound(ApoMonoSoundPlayer.SOUND_GRAVITY);
					}
				}
			}
			if ((mirrorX >= 0) && (p[3] <= 0)) {
				if ((mirrorMode == 2) && (mirrorX >= 0)) {
					mirrorX = -1;
					mirrorY = -1;
				}
				if ((mirrorMode == 1) && (p[1] > 0)) {
					p[3] = 960;

					this.makeBackground();
					this.saveNextBackstep();
					this.getGame().playSound(ApoMonoSoundPlayer.SOUND_FOLD);
				}
				if (move == -1) {
					if (mirrorX > 1) {
						mirrorX -= 1;
						i = 1;
					}
				}
				if (move == 1) {
					if (mirrorX < 28) {
						mirrorX += 1;
						i = 1;
					}
				}
				if ((mirrorX >= 0) && (i != 0)) {
					i = 0;
				}
			} else if ((mirrorY >= 0) && (p[3] <= 0)) {
				if ((mirrorMode == 2) && (mirrorY >= 0)) {
					mirrorX = -1;
					mirrorY = -1;
				}
				if ((mirrorMode == 3) && (p[8] > 0)) {
					p[3] = 960;

					this.makeBackground();
					this.saveNextBackstep();
					this.getGame().playSound(ApoMonoSoundPlayer.SOUND_FOLD);
				}
				if (move == -1) {
					if (mirrorY > 1) {
						mirrorY -= 1;
						i = 1;
					}
				}
				if (move == 1) {
					if (mirrorY < 14) {
						mirrorY += 1;
						i = 1;
					}
				}
				if ((mirrorY >= 0) && (i != 0)) {
					i = 0;
				}
			} else if ((addX == 0) && (addY == 0) && (fall == 0)) {
				/** FALL */
				this.fall();
				
				if (fall != 0) {
				} else if (p[3] <= 0) {
					addLeft = 0;
					if ((mirrorMode == 1) && (p[1] > 0)) {
						if (mirrorX < 0) {
							mirrorX = 15;
							this.mirrorChange = ApoMonoPuzzleGame.MIRROR_CHANGE_TIME;
							this.makeMirrrorImage();
							return;
						}
					}
					if ((mirrorMode == 3) && (p[8] > 0)) {
						if (mirrorY < 0) {
							mirrorY = 7;
							this.mirrorChange = ApoMonoPuzzleGame.MIRROR_CHANGE_TIME;
							this.makeMirrrorImage();
							return;
						}
					}
					if (move == -1) {
						addLeft = -1;
						if (add == 0) {
							this.saveNextBackstep();
							add = 1;
							p[7] = 250;
							return;
						}
						add = 1;
					}
					if (move == 1) {
						addLeft = 1;
						if (add == 1) {
							this.saveNextBackstep();
							add = 0;
							p[7] = 250;
							return;
						}
						add = 0;
					}
					if ((up == 1) && (withBox == 0)) {
						this.hideSpeech();
						addLeft = 1;
						if (add == 1) {
							addLeft = -1;
						}
						if ((playerX + addLeft >= 0) && (playerX + addLeft < level[playerY].length)) {
							if ((playerY - this.gravity >= 0) && (((level[playerY - this.gravity][playerX] == 0) || ((level[playerY - this.gravity][playerX] > 5) && (level[playerY - this.gravity][playerX] != 8) && (level[playerY - this.gravity][playerX] != 12)) || (level[playerY - this.gravity][playerX] == 3)))) {
								if (level[playerY][playerX + addLeft] == 4) {
									if ((playerY - this.gravity >= 0) && (playerY - this.gravity < level.length) && (((level[playerY - this.gravity][playerX + addLeft] == 0) || ((level[playerY - this.gravity][playerX + addLeft] > 5) && (level[playerY - this.gravity][playerX + addLeft] < 8)) || (level[playerY - this.gravity][playerX + addLeft] == 3)))) {
										this.saveNextBackstep();
										withBox = 1;
										level[playerY][playerX + addLeft] = boxes[playerY][playerX + addLeft];
										this.getGame().playSound(ApoMonoSoundPlayer.SOUND_PICK);
									}
								}
							}
						}
						return;
					}
					if ((up == 1) && (withBox != 0)) {
						this.hideSpeech();
						addLeft = 1;
						if (add == 1) {
							addLeft = -1;
						}
						if ((playerX + addLeft >= 0) && (playerX + addLeft < level[playerY].length)) {
							if (((level[playerY][playerX + addLeft] == 0) || (level[playerY][playerX + addLeft] == 11) || ((level[playerY][playerX + addLeft] > 5) && (level[playerY][playerX + addLeft] < 8))) && ((level[playerY - this.gravity][playerX + addLeft] == 0) || (level[playerY - this.gravity][playerX + addLeft] == 11) || ((level[playerY - this.gravity][playerX + addLeft] > 5) && (level[playerY - this.gravity][playerX + addLeft] < 8)))) {
								this.saveNextBackstep();
								level[playerY][playerX + addLeft] = 4;
								if (boxes[playerY][playerX + addLeft] == 11) {
									this.beamValue(playerX + addLeft, playerY);
								}
								withBox = 0;
								this.getGame().playSound(ApoMonoSoundPlayer.SOUND_DROP);
							} else if ((level[playerY - this.gravity][playerX + addLeft] == 0) || (level[playerY - this.gravity][playerX + addLeft] == 11) || ((level[playerY - this.gravity][playerX + addLeft] > 5) && (level[playerY - this.gravity][playerX + addLeft] < 8))) {
								this.saveNextBackstep();
								level[playerY - this.gravity][playerX + addLeft] = 4;
								if (boxes[playerY - this.gravity][playerX + addLeft] == 11) {
									this.beamValue(playerX + addLeft, playerY - this.gravity);
								}
								withBox = 0;
								this.getGame().playSound(ApoMonoSoundPlayer.SOUND_DROP);
							}
						}
						return;
					}
					if (addLeft != 0) {
						if ((playerX + addLeft >= 0) && (playerX + addLeft < level[playerY].length)) {
							if ((withBox == 0) || (level[playerY-withBox*this.gravity][playerX + addLeft] == 0) || ((level[playerY-withBox*this.gravity][playerX + addLeft] > 5) && (level[playerY-withBox*this.gravity][playerX + addLeft] != 8) && (level[playerY-withBox*this.gravity][playerX + addLeft] != 12) && (level[playerY-withBox*this.gravity][playerX + addLeft] != 10)) || (level[playerY-withBox*this.gravity][playerX + addLeft] == 3)) {
								if ((level[playerY][playerX + addLeft] == 0) || ((level[playerY][playerX + addLeft] > 5) && (level[playerY][playerX + addLeft] != 8) && (level[playerY][playerX + addLeft] != 12)) || (level[playerY][playerX + addLeft] == 3)) {
									if ((playerY + this.gravity < this.level.length) && (this.level[playerY + this.gravity][playerX] == 12)) {
										this.level[playerY + this.gravity][playerX] = 0;
										this.addParticle((playerX) * 16 + 8, (playerY + this.gravity) * 16 + changeY + 8);
									}
									playerX += addLeft;
									addX = -addLeft * 16;
									addY = 0;
									this.hideSpeech();
								} else if ((playerY - this.gravity >= 0) && (((level[playerY - this.gravity][playerX + addLeft] == 0) || ((level[playerY - this.gravity][playerX + addLeft] > 5) && (level[playerY - this.gravity][playerX + addLeft] != 8) && (level[playerY - this.gravity][playerX + addLeft] != 10) && (level[playerY - this.gravity][playerX + addLeft] != 12)) || (level[playerY - this.gravity][playerX + addLeft] == 3)))) {
									if ((playerY - this.gravity >= 0) && (((level[playerY - this.gravity][playerX] == 0) || ((level[playerY - this.gravity][playerX] > 5) && (level[playerY - this.gravity][playerX] != 8) && (level[playerY - this.gravity][playerX] != 10) && (level[playerY - this.gravity][playerX] != 12)) || (level[playerY - this.gravity][playerX] == 3)))) {
										if ((withBox == 0) || (playerY-withBox*this.gravity - this.gravity < 0) || (playerY-withBox*this.gravity - this.gravity >= this.level.length) || (level[playerY-withBox*this.gravity - this.gravity][playerX] == 0) || ((level[playerY-withBox*this.gravity - this.gravity][playerX] > 5) && (level[playerY-withBox*this.gravity - this.gravity][playerX] != 8) && (level[playerY-withBox*this.gravity - this.gravity][playerX] != 12) && (level[playerY-withBox*this.gravity - this.gravity][playerX] != 10)) || (level[playerY-withBox*this.gravity - this.gravity][playerX] == 3)) {
											if ((withBox == 0) || (playerY-withBox*this.gravity - this.gravity < 0) || (playerY-withBox*this.gravity - this.gravity >= this.level.length) || (level[playerY-withBox*this.gravity - this.gravity][playerX + addLeft] == 0) || ((level[playerY-withBox*this.gravity - this.gravity][playerX + addLeft] > 5) && (level[playerY-withBox*this.gravity - this.gravity][playerX + addLeft] != 8) && (level[playerY-withBox*this.gravity - this.gravity][playerX + addLeft] != 10) && (level[playerY-withBox*this.gravity - this.gravity][playerX + addLeft] != 12)) || (level[playerY-withBox*this.gravity - this.gravity][playerX + addLeft] == 3)) {
												if (playerY - withBox * this.gravity - this.gravity < 0) {
													this.addParticle((playerX) * 16 + 8, (playerY - this.gravity) * 16 + changeY + 8);
													this.withBox = 0;
												}
												if (playerY - withBox * this.gravity - this.gravity >= level.length) {
													this.addParticle((playerX) * 16 + 8, (playerY - this.gravity) * 16 + changeY + 8);
													this.withBox = 0;
												}
												
												if ((playerY + this.gravity < this.level.length) && (this.level[playerY + this.gravity][playerX] == 12)) {
													this.level[playerY + this.gravity][playerX] = 0;
													this.addParticle((playerX) * 16 + 8, (playerY + this.gravity) * 16 + changeY + 8);
												}
												
												playerX += addLeft;
												playerY -= 1 * this.gravity;
												addX = -addLeft * 16;
												addY = 16 * this.gravity;
												this.hideSpeech();
											}
										}
									}
								}
							}
						}
					}
				}
			} else if (fall != 0) {
				fall = 0;
				boolean bPlayer = true;
				int start = level.length - 1;
				if (this.gravity < 0) {
					start = 0;
				}
				for (int y = start; y >= 0 && y < level.length; y += -this.gravity) {
					for (int x = 0; x < level[0].length; x++) {
						if (falla[y][x] > 0) {
							falla[y][x] -= 1;
							fall = 1;
							if ((x != playerX) || (y != playerY)) {
								bPlayer = false;
							}
							if (falla[y][x] == 0) {
								if ((boxes[y][x] == 11) && (((x == playerX) && (y == playerY)) || (level[y][x] == 4))) {
									/** TODO Beamer */
									this.beamValue(x, y);
								} else if ((x == playerX) && (y + this.gravity == playerY)) {
									this.lostLevel();
								} else if (level[playerY][playerX] == 10) {
									this.lostLevel();
								}
							}
						}
					}
				}
				if (fall == 0) {
					for (x = 0; x < level[0].length; x++) {
						if ((level[level.length - 1][x] == 4) && (this.gravity > 0)) {
							level[level.length - 1][x] = 0;
							this.addParticle(x * 16, (level.length - 1) * 16);
						}
						if ((level[0][x] == 4) && (this.gravity < 0)) {
							level[0][x] = 0;
							this.addParticle(x * 16, 0);
						}
						if (((playerY == 0) && (this.gravity < 0)) || (this.playerY < 0)) {
							this.lostLevel();
							break;
						}
						if (((playerY == level.length - 1) && (this.gravity > 0)) || (this.playerY >= level.length)) {
							this.lostLevel();
							break;
						}
					}
					if (level[playerY][playerX] == 3) {
						this.winLevel();
					}
					if ((p[2] <= 0) && (!bPlayer)) {
						this.saveNextBackstep();
					}
				}
			} else {
				if (this.gravity > 0) {
					if (addY > 0) {
						addY -= 4;
					} else if (addX > 0) {
						addX -= 1;
					} else if (addX < 0) {
						addX += 1;
					} else if (addY < 0) {
						addY += 4;
					}
				} else {
					if (addY < 0) {
						addY += 4;
					} else if (addX > 0) {
						addX -= 1;
					} else if (addX < 0) {
						addX += 1;
					} else if (addY > 0) {
						addY -= 4;
					}
				}
				if ((addY == 0) && (addX == 0)) {
					if (level[playerY][playerX] == 3) {
						this.winLevel();
					} else if (level[playerY][playerX] == 10) {
						this.lostLevel();
					} else if (boxes[playerY][playerX] == 11) {
						/** TODO Beamer */
						this.beamValue(playerX, playerY);
					}
					this.think(delta);
				}
			}
		}
	}
	
	private void fallNew(final int x, final int startY) {
		int start = 0;
		int end = startY;
		if (gravity < 0) {
			start = level.length - 1;
			end = startY;
		}
		for (int y = start; y != end; y += this.gravity) {
			if (this.falla[y][x] != 0) {
				if ((x == playerX) && (y == playerY)) {
					playerY -= this.falla[y][x]/16 * this.gravity;
				} else {
					this.level[y - this.falla[y][x]/16 * this.gravity][x] = this.level[y][x];
				}
				this.level[y][x] = this.boxes[y][x];
				if (level[y][x] == 11) {
					level[y][x] = 0;
				}
				this.falla[y][x] = 0;
			}
		}
	}
	
	private void fall() {
		for (int x = 0; x < level[0].length; x++) {
			if (p[2] > 0) {
				break;
			}
			int addFall = 0;
			int addFallPlayer = 0;
			int start = 0;
			if (gravity > 0) {
				start = level.length - 1;
			}					
			
			for (int y = start; y >= 0 && y < level.length; y += -this.gravity) {
				if ((level[y][x] == 1) || (level[y][x] == 12) || (level[y][x] == 5) || (level[y][x] == 8) || (level[y][x] == 10)) {
					addFallPlayer = addFall = 0;
				} else if (((level[y][x] == 0) || (level[y][x] > 5)) && (level[y][x] < 8) && ((playerX != x) || (playerY != y))) {
					addFall += 1;
					addFallPlayer += 1;
				}
				
				if ((x == playerX) && (y == playerY)) {
					if ((y + addFallPlayer*this.gravity + this.gravity >= 0) && (y + addFallPlayer*this.gravity + this.gravity < this.level.length)) {
						if (level[y+addFallPlayer*this.gravity+this.gravity][x] == 3) {
							addFallPlayer += 1;
						}
					}
				}

				if (boxes[y][x] == 11) {
					/** TODO Beamer */
					if ((y + addFall + this.gravity >= 0) && (y + addFall + this.gravity < this.level.length) && (addFall == 0) && ((level[y+this.gravity][x] == 12) || (level[y+this.gravity][x] == 1) || (level[y+this.gravity][x] == 4) || (level[y+this.gravity][x] == 5) || (level[y+this.gravity][x] == 8))) {
						addFallPlayer = addFall = 0;						
					} else {
						addFallPlayer = addFall = 1;
					}
				}
				if ((x == playerX) && (y == playerY - withBox * this.gravity) && (withBox > 0)) {
					addFallPlayer = addFall = 0;
				}
				if ((level[y][x] == 4) && (x == playerX) && (y + this.gravity == playerY)) {
					this.lostLevel();
					break;
				}

				if ((level[y][x] == 3) || (level[y][x] == 10)) {
					if ((x == playerX) && (y == playerY + (addFallPlayer + 1) * this.gravity)) {
						addFallPlayer = 1;
						addFall = 0;
					} else {
						addFallPlayer = addFall = 0;
					}
				}
				if (((level[y][x] == 4) || ((x == playerX) && (y == playerY))) && (addFallPlayer > 0)) {
					if ((boxes[y][x] == 11) && (addFallPlayer == 1)) {
						if ((y + this.gravity >= 0) && (y + this.gravity < level.length)) {
							if ((level[y + this.gravity][x] == 1) || (level[y + this.gravity][x] == 4) || (level[y + this.gravity][x] == 5) || (level[y + this.gravity][x] == 8)) {
								addFallPlayer = addFall = 0;
								addFallPlayer = 0;
								continue;
							}
						}
					}
					if ((x == playerX) && (y == playerY)) {
						if ((y + addFallPlayer*this.gravity + this.gravity >= 0) && (y + addFallPlayer*this.gravity + this.gravity < this.level.length)) {
							if (level[y+addFallPlayer*this.gravity+this.gravity][x] == 3) {
								addFallPlayer += 1;
							}
						}
					}
					if (this.gravity < 0) {
						addFall = -addFall;
						addFallPlayer = -addFallPlayer;
					}
					if ((x == playerX) && (y == playerY)) {
						addFall = addFallPlayer;
						playerY += addFall;
					} else {
						level[y + addFall][x] = level[y][x];
						level[y][x] = boxes[y][x];	
					}
					falla[y + addFall][x] = addFall * 16 * this.gravity;
					fall = 1;
					if ((x == playerX) && (y == playerY - addFall)) {
						y -= withBox;
					}
					if (this.gravity < 0) {
						addFall = -addFall;
						addFallPlayer = -addFallPlayer;
					}
				}

			}
		}
	}
	
	private boolean beamValue(final int startX, final int startY) {
		int value = this.level[startY][startX];
		if (value != 4) {
			value = 2;
		}
		for (int y = 0; y < level.length; y++) {
			for (int x = 0; x < level[0].length; x++) {
				if (((x != startX) || (y != startY)) && (boxes[y][x] == 11)) {
					if ((level[y][x] == 0) || (level[y][x] == 11)) {
						if ((x == playerX) && (y == playerY) && (falla[y][x] == 0)) {
							return false;
						}
						
						this.fallNew(x, y);						
						this.level[startY][startX] = 0;
						this.level[y][x] = value;
						if (this.level[y][x] != 4) {
							this.level[y][x] = 0;
							this.playerX = x;
							this.playerY = y;
							if (this.withBox > 0) {
								if (this.level[startY - this.gravity][startX] != 1) {
									this.level[startY - this.gravity][startX] = 4;
								}
								this.withBox = 0;
							}
						}
						this.fall();
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private void lostLevel() {
		this.addParticle((playerX) * 16 + 8, playerY * 16 + changeY + 8);
		p[4] = 0;
		p[2] = 2;
	}
	
	private void winLevel() {
		p[4] = 0;
		p[2] = 1;
		int text = (int)(Math.random() * ApoMonoConstants.TEXT_WIN.length);
		this.showSpeech(ApoMonoConstants.TEXT_WIN[text]);
		if ((!this.bUserlevel) && (!this.bEditor)) {
			this.getGame().solveLevel(p[0]);
		}
		this.getGame().playSound(ApoMonoSoundPlayer.SOUND_WIN);
	}
	
	private void showSpeech(String text) {
		this.speech.setX(this.playerX * 16 + 8);
		this.speech.setY(this.playerY * 16 + ApoMonoPuzzleGame.changeY - 20);
		this.speech.setText(text);
		if (this.add != 0) {
			this.speech.setLeft(true);
		} else {
			this.speech.setLeft(false);
		}
		if (this.gravity > 0) {
			this.speech.setUp(true);
		} else {
			this.speech.setUp(false);			
		}
		this.speech.setVisible(true);
		this.speechShowTime = ApoMonoPuzzleGame.SPEECH_MAX_TIME;
	}
	
	private void hideSpeech() {
		this.speech.setVisible(false);
		this.noMoveTime = 0;
	}

	@Override
	public void render(BitsGLGraphics g) {
		g.setColor(ApoMonoConstants.DARK[0], ApoMonoConstants.DARK[1], ApoMonoConstants.DARK[2], 1f);
		
		String s = "Level";
		this.getGame().drawString(g, s, (int)(240 - ApoMonoPanel.game_font.getLength(s)/2), 10 - ApoMonoPanel.game_font.mCharCellHeight/2, ApoMonoPanel.game_font);

		if (!this.bEditor) {
			if (this.bUserlevel) {
				s = (p[0] + 1)+" / "+(ApoMonoLevel.editorLevels.length);
			} else {
				if (this.getGame().getSolvedLevels().isLevelSolved(p[0])) {
					g.setColor(0f, 1f, 0f, 1f);
					
					g.setLineSize(4f);
					g.drawLine(240 - ApoMonoPanel.game_font.getLength(s)/2, 30, 245 - ApoMonoPanel.game_font.getLength(s)/2, 35);
					g.drawLine(245 - ApoMonoPanel.game_font.getLength(s)/2, 35, 255 - ApoMonoPanel.game_font.getLength(s)/2, 25);
					g.setLineSize(1f);
					
					g.setColor(ApoMonoConstants.DARK[0], ApoMonoConstants.DARK[1], ApoMonoConstants.DARK[2], 1f);
				}
				s = (p[0] + 1)+" / "+(this.getGame().getMaxCanChoosen() + 1);
			}			
		} else {
			s = "Editor";
		}
		this.getGame().drawString(g, s, (int)(240 - ApoMonoPanel.game_font.getLength(s)/2), 30 - ApoMonoPanel.game_font.mCharCellHeight/2, ApoMonoPanel.game_font);
		
		ApoMonoPuzzleGame.drawMenu(g, this.getGame(), ApoMonoConstants.DARK_BRIGHT, ApoMonoConstants.DARK, p[1], p[8], p[9], this.frameGravity);
		
		this.getGame().drawButtons(g, ApoMonoPanel.game_font, 2);
		
		g.setColor(1f,1f,1f,1f);
		if ((this.iBackground != null) && (this.iBackground.isLoaded) && (this.backgroundWait <= 0)) {
			int scale = 1;
			if (this.iBackground.mWidth > 512) {
				scale = 2;
			}
			g.cropImage(this.iBackground, 0, changeY, ApoMonoConstants.GAME_WIDTH, 242, 0, 0, ApoMonoConstants.GAME_WIDTH * scale, 242 * scale);
		}
		
		for (y = 0; y < level.length; y++) {
			for (x = 0; x < level[0].length; x++) {
				int addPX = x * 16;
				int addPY = y * 16 + changeY - falla[y][x] * this.gravity;
				if (level[y][x] != 0) {
					g.setColor(ApoMonoConstants.DARK[0], ApoMonoConstants.DARK[1], ApoMonoConstants.DARK[2], 1f);

					if (level[y][x] == 3) {
						addPY += falla[y][x] * this.gravity;
						/** Render das Ziel */
						if (p[2] == 1) {
							g.fillRect(addPX + 11, addPY + 8, 4, 2);
							addPY -= 8;
						}
						ApoMonoPuzzleGame.drawFinish(g, addPX, addPY, this.frameGoal);
					}
					if ((level[y][x] == 1) || (level[y][x] == 4) || (level[y][x] == 5) || (level[y][x] == 8)) {
						if ((level[y][x] > 1) && (level[y][x] < 8) && (p[3] > 0) && (mirrorX - 1 < x) && (mirrorX >= 0)) continue;
						if ((level[y][x] > 1) && (level[y][x] < 8) && (p[3] > 0) && (mirrorY - 1 < y) && (mirrorY >= 0)) continue;
						if (level[y][x] != 1) {
							g.setColor(ApoMonoConstants.DARK_BRIGHT[0], ApoMonoConstants.DARK_BRIGHT[1], ApoMonoConstants.DARK_BRIGHT[2], 1f);
						}
						if (level[y][x] == 4) {
							g.setColor(ApoMonoConstants.DARK_BRIGHT[0], ApoMonoConstants.DARK_BRIGHT[1], ApoMonoConstants.DARK_BRIGHT[2], 1f);
							ApoMonoPuzzleGame.drawMoveableBox(g, addPX, addPY);
						} else if ((!this.iBackground.isLoaded) || (this.backgroundWait > 0)) {
							if (level[y][x] == 1) {
								ApoMonoPuzzleGame.drawBlock(g, addPX, addPY);
							} else {
								if (level[y][x] == 8) {
									ApoMonoPuzzleGame.drawBlockPlusMirror(g, addPX, addPY);
								} else {
									ApoMonoPuzzleGame.drawBox(g, addPX, addPY, false);									
								}
							}
						}
					}
					if ((!this.iBackground.isLoaded) || (this.backgroundWait > 0)) {
						if (level[y][x] == 6) {
							if ((p[3] > 0) && (((x >= this.mirrorX) && (this.mirrorX > 0)) || ((y >= this.mirrorY) && (this.mirrorY > 0)))) {
							} else {
								ApoMonoPuzzleGame.drawAir(g, addPX, addPY);
							}
						}
						if (level[y][x] == 7) {
							ApoMonoPuzzleGame.drawX(g, addPX, addPY);
						}
						if (level[y][x] == 10) {
							if ((p[3] > 0) && (x >= this.mirrorX)) {
							} else {
								g.setColor(ApoMonoConstants.DARK_BRIGHT[0], ApoMonoConstants.DARK_BRIGHT[1], ApoMonoConstants.DARK_BRIGHT[2], 1f);
								addPY += falla[y][x] * this.gravity;
								ApoMonoPuzzleGame.drawSpikes(g, addPX, addPY);
							}
						}
					}
				}
				if (level[y][x] == 12) {
					ApoMonoPuzzleGame.drawOnStepBlock(g, addPX, addPY);
				}
				if ((level[y][x] == 11) || (boxes[y][x] == 11)) {
					addPY += falla[y][x] * this.gravity;
					g.setColor(ApoMonoConstants.DARK_BRIGHT[0], ApoMonoConstants.DARK_BRIGHT[1], ApoMonoConstants.DARK_BRIGHT[2], 1f);
					ApoMonoPuzzleGame.drawBeamer(g, addPX, addPY, this.beamerFrame);
				}
			}
		}
		
		g.setColor(ApoMonoConstants.DARK[0], ApoMonoConstants.DARK[1], ApoMonoConstants.DARK[2], 1f);
		if (p[2] <= 1) {
			if ((p[3] > 0) && (((this.mirrorX <= playerX) && (this.mirrorX > 0)) || ((this.mirrorY <= playerY) && (this.mirrorY > 0)))) {
				
			} else {
				ApoMonoPuzzleGame.renderPlayer(g, playerX * 16 + addX, playerY * 16 + changeY + addY - falla[playerY][playerX] * this.gravity, add, this.gravity, withBox);
			}
		}
		
		if (p[1] <= 1) {
			if ((this.speech != null) && (this.speech.isVisible())) {
				this.speech.render(g);
			}
		}

		if (mirrorX >= 0) {
			g.setColor(ApoMonoConstants.DARK[0], ApoMonoConstants.DARK[1], ApoMonoConstants.DARK[2], 1f);
			for (y = 0; y < 240; y += 4) {
				g.fillRect(mirrorX * 16 - 1, y + changeY, 2, 2);
			}
			g.setColor(1f,1f,1f,0.5f);
			
			if (this.iMirror != null) {
				if (this.iMirror.isLoaded) {
					int width = ApoMonoConstants.GAME_WIDTH - mirrorX * 16;
					g.cropImage(this.iMirror, mirrorX * 16 - width, changeY, width, 240, 0, 0, width, 240);
				}
			}
		}
		if (mirrorY >= 0) {
			g.setColor(ApoMonoConstants.DARK[0], ApoMonoConstants.DARK[1], ApoMonoConstants.DARK[2], 1f);
			for (x = 0; x < ApoMonoConstants.GAME_WIDTH; x += 4) {
				g.fillRect(x, mirrorY * 16 - 1 + changeY, 2, 2);
			}
			g.setColor(1f,1f,1f,0.5f);
			
			if (this.iMirror != null) {
				if( this.iMirror.isLoaded == true ) {
					int height = 240 - mirrorY * 16;
					g.cropImage(this.iMirror, ApoMonoConstants.GAME_WIDTH, changeY + mirrorY * 16, -ApoMonoConstants.GAME_WIDTH, -height, 0, 240 - height, ApoMonoConstants.GAME_WIDTH, height);
				}
			}
		}
		g.setColor(1f, 1f, 1f, 1f);
		if ((p[3] > 0) && (this.iMirror != null)) {
			if( this.iMirror.isLoaded == true ) {
				if (this.mirrorX >= 0) {
					float percent = ((p[3]) - 480f) / 480f;
					int realWidth = 480 - mirrorX * 16;
					int width = (int)((float)(realWidth) * percent);
					g.cropImage(this.iMirror, mirrorX * 16 + width, changeY, -width, 240, 0, 0, realWidth, 240);
				} else {
					float percent = ((p[3]) - 480f) / 480f;
					int realHeight = 240 - mirrorY * 16;
					int height = (int)((float)(realHeight) * percent);
					g.cropImage(this.iMirror, 480, changeY + mirrorY * 16, -480, height, 0, 240 - realHeight, 480, realHeight);
				}
			}
		}

		boolean bActive = false;
		if (((p[0] == 0) || ((this.mirrorX >= 0) && (p[0] == 2))) && (!bUserlevel) && (!bEditor)) {
			bActive = true;
		}
		if (this.mirrorY > 0) {
			this.drawInputUpBox(g, bActive);
			this.drawInputDownBox(g, bActive);			
		} else {
			this.drawInputLeftBox(g, bActive);
			this.drawInputRightBox(g, bActive);			
		}
		
		if ((this.p[9] > 0) && (this.mirrorX < 0) && (this.mirrorY < 0)) {
			bActive = false;
			this.drawInputGravityBox(g, bActive);
		}
		
		if ((this.backsteps.size() > 1) || ((playerX != playerStartX) || (playerY != playerStartY))) {
			bActive = false;
			if ((p[0] == 3) && (!this.bUserlevel) && (!this.bEditor)) {
				bActive = true;
			}
			this.drawInputBackBox(g, bActive);
		}
		
		this.drawInputLeftBoxForLevelchooser(g);
		this.drawInputRightBoxForLevelchooser(g);
		
		bActive = false;
		if ((p[0] == 1) && (!bUserlevel) && (!bEditor)) {
			bActive = true;
		}
		if ((this.mirrorX < 0) && (this.mirrorY < 0)) {
			this.drawInputWithBox(g, bActive);
		}
		if ((this.p[1] > 0) && (this.mirrorY < 0)) {
			bActive = false;
			if ((p[0] == 2) && (!bUserlevel) && (!bEditor)) {
				bActive = true;
			}
			if (this.mirrorX < 0) {
				this.drawInputMirrorHorizontalBox(g, bActive);
			} else {
				this.drawInputMirrorHorizontalBox(g, bActive);
				this.drawInputMirrorExitBox(g, bActive);
			}
		}
		if ((this.p[8] > 0) && (this.mirrorX < 0)) {
			bActive = false;
			if ((p[0] == 2) && (!bUserlevel) && (!bEditor)) {
				bActive = true;
			}
			if (this.mirrorY < 0) {
				this.drawInputMirrorVerticalBox(g, bActive);
			} else {
				this.drawInputMirrorVerticalBox(g, bActive);
				this.drawInputMirrorExitBox(g, bActive);
			}
		}
		
		g.setColor(ApoMonoConstants.DARK[0], ApoMonoConstants.DARK[1], ApoMonoConstants.DARK[2], 1f);
		for (int i = this.particles.size() - 1; i >= 0; i--) {
			this.particles.get(i).render(g, 0, 0);
		}
		
		if (p[2] > 0) {
			String[] array = ApoMonoConstants.GAME_WIN.split(":");
			if (p[2] > 1) {
				array = ApoMonoConstants.GAME_RESTART.split(":");
			}
			g.setColor(ApoMonoConstants.BRIGHT_DARK[0], ApoMonoConstants.BRIGHT_DARK[1], ApoMonoConstants.BRIGHT_DARK[2], 1f);
			g.fillRect(50, changeY + 115, 380, array.length * 15 + 10);
			g.setColor(ApoMonoConstants.DARK[0], ApoMonoConstants.DARK[1], ApoMonoConstants.DARK[2], 1f);
			g.drawRect(50, changeY + 115, 380, array.length * 15 + 10);
			for (int i = 0; i < array.length; i++) {
				this.getGame().drawString(g, array[i], (int)(240 - ApoMonoPanel.game_font.getLength(array[i])/2), changeY + i * 15 + 120, ApoMonoPanel.game_font);
			}
		} else {
			if ((!bUserlevel) && (!bEditor)) {
				if (mirrorX >= 0) {
					if (p[0] == 2) {
						String[] array = ApoMonoConstants.HELP_MIRROR.split(":");
						for (int i = 0; i < array.length; i++) {
							this.getGame().drawString(g, array[i], (int)(240 - ApoMonoPanel.game_font.getLength(array[i])/2), changeY + 5 + i * 18, ApoMonoPanel.game_font);
						}
					}
				} else {
					String curString = ApoMonoConstants.HELP_STANDARD;
					if ((p[0] < ApoMonoConstants.HELP.length) && (ApoMonoConstants.HELP[p[0]].length() > 0)) {
						curString = ApoMonoConstants.HELP[p[0]];
					} else if (p[0] >= ApoMonoConstants.HELP.length) {
						curString = "";
					}
					
					if (curString.length() > 0) {
						String[] array = curString.split(":");
						for (int i = 0; i < array.length; i++) {
							this.getGame().drawString(g, array[i], (int)(240 - ApoMonoPanel.game_font.getLength(array[i])/2), changeY + 5 + i * 18, ApoMonoPanel.game_font);
						}
					}
				}
			}
		}
	}
	
	public static void renderPlayer(final BitsGraphics g, final int addPX, int addPY, final int add, final int gravity, final int withBox) {
		g.setColor(ApoMonoConstants.DARK[0], ApoMonoConstants.DARK[1], ApoMonoConstants.DARK[2], 1f);
		int addGravity = 0;
		if (gravity < 0) {
			addGravity = 16;
		}
		g.fillRect(addPX + 5, addPY + 1 * gravity + addGravity, 6, 11 * gravity);
		g.fillRect(addPX + 3, addPY + 2 * gravity + addGravity, 10, 2 * gravity);
		g.fillRect(addPX + 11 - add * 8, addPY + 5 * gravity + addGravity, 2, 2 * gravity);

		g.fillRect(addPX + 5, addPY + 12 * gravity + addGravity, 2, 5 * gravity);
		g.fillRect(addPX + 9, addPY + 12 * gravity + addGravity, 2, 5 * gravity);
		if (withBox > 0) {
			addPY -= 16 * gravity;
			ApoMonoPuzzleGame.drawMoveableBox(g, addPX, addPY);
			g.setColor(ApoMonoConstants.DARK[0], ApoMonoConstants.DARK[1], ApoMonoConstants.DARK[2], 1f);
			g.fillRect(addPX + 5, addPY + 16 * gravity + addGravity, 2, 2 * gravity);
			g.fillRect(addPX + 9, addPY + 16 * gravity + addGravity, 2, 2 * gravity);
			g.fillRect(addPX + 5, addPY + 17 * gravity + addGravity, 6, 1 * gravity);
		}
	}

	public static void drawMenu(final BitsGLGraphics g, final ApoMonoPanel panel, final float[] color, final float[] colorText, final int horizontal, final int vertical, final int gravity, final float frameGravity) {
		int addY = 10;
		g.setColor(color[0], color[1], color[2], 0.5f);
		g.fillRect(2, 6 + addY, 6, 8);
		g.fillRect(1, 7 + addY, 8, 6);
		
		g.fillRect(51, 1 + addY, 6, 8);
		g.fillRect(50, 2 + addY, 8, 6);
		g.setColor(color[0], color[1], color[2], 1f);
		g.fillRect(14, 6 + addY, 6, 8);
		g.fillRect(13, 7 + addY, 8, 6);
		
		g.fillRect(51, 12 + addY, 6, 8);
		g.fillRect(50, 13 + addY, 8, 6);
		for (int i = 0; i < 8; i+= 2) {
			g.fillRect(10, 6 + i + addY, 1, 1);
			g.fillRect(50 + i, 10 + addY, 1, 1);
		}
		String s = ":"+String.valueOf(horizontal);
		panel.drawString(g, s, (int)(23), 2 + addY, ApoMonoPanel.game_font, colorText);
		
		s = ":"+String.valueOf(vertical);
		panel.drawString(g, s, (int)(60), 2 + addY, ApoMonoPanel.game_font, colorText);
		
		g.fillRect(87, 13 + frameGravity/2, 6, 8);
		g.fillRect(86, 13 + frameGravity/2 + 1, 8, 6);
		s = ":"+String.valueOf(gravity);
		panel.drawString(g, s, (int)(100), 2 + addY, ApoMonoPanel.game_font, colorText);
	}

	public static void drawX(final BitsGraphics g, final int addPX, final int addPY) {
		ApoMonoPuzzleGame.drawX(g, addPX, addPY, false, ApoMonoConstants.DARK_BRIGHT, true, true);
	}
	
	public static void drawX(final BitsGraphics g, final int addPX, final int addPY, final boolean bDouble) {
		ApoMonoPuzzleGame.drawX(g, addPX, addPY, bDouble, ApoMonoConstants.DARK_BRIGHT, true, true);
	}
	
	public static void drawX(final BitsGraphics g, final int addPX, final int addPY, final boolean bDouble, final float[] backColor, final boolean bBrighter, final boolean bShadow) {
		int scale = 1;
		if (bDouble) {
			scale = 2;
		}
		for (int i = 0; i < 5; i++) {
			if (bShadow) {
				g.setColor(backColor[0], backColor[1], backColor[2], 1f);
				if (bBrighter) {
					ApoMonoPuzzleGame.setBrighterColor(g);
				} else {
					ApoMonoPuzzleGame.setDarkerColor(g);
				}
				g.fillRect(addPX + 4 * scale + i*2 * scale, addPY +  4 * scale + i*2 * scale, 2 * scale, 2 * scale);
				g.fillRect(addPX + 12 * scale - i*2 * scale, addPY + 4 * scale + i*2 * scale, 2 * scale, 2 * scale);
			}
			g.setColor(backColor[0], backColor[1], backColor[2], 1f);
			g.fillRect(addPX + 3 * scale + i*2 * scale, addPY + 3 * scale + i*2 * scale, 2 * scale, 2 * scale);
			g.fillRect(addPX + 11 * scale - i*2 * scale, addPY + 3 * scale + i*2 * scale, 2 * scale, 2 * scale);
		}
	}
	
	public static void drawAir(final BitsGraphics g, final int addPX, final int addPY) {
		ApoMonoPuzzleGame.drawAir(g, addPX, addPY, false);
	}
	
	public static void drawAir(final BitsGraphics g, final int addPX, final int addPY, final boolean bDouble) {
		int scale = 1;
		if (bDouble) {
			scale = 2;
		}
		g.setColor(ApoMonoConstants.DARK_BRIGHT[0], ApoMonoConstants.DARK_BRIGHT[1], ApoMonoConstants.DARK_BRIGHT[2], 1f);
		g.fillRect(addPX + 6 * scale, addPY + 6 * scale, 4 * scale, 4 * scale);
	}

	public static void drawSpikes(final BitsGraphics g, final int addPX, final int addPY) {
		ApoMonoPuzzleGame.drawSpikes(g, addPX, addPY, false);
	}
	
	public static void drawSpikes(final BitsGraphics g, final int addPX, final int addPY, final boolean bDouble) {
		int scale = 1;
		if (bDouble) {
			scale = 2;
		}
		g.fillRect(addPX, addPY + 14 * scale, 16 * scale, 3 * scale);
		g.fillRect(addPX + 1 * scale, addPY + 11 * scale, 6 * scale, 3 * scale);
		g.fillRect(addPX + 9 * scale, addPY + 11 * scale, 6 * scale, 3 * scale);
		g.fillRect(addPX + 2 * scale, addPY + 8 * scale, 4 * scale, 3 * scale);
		g.fillRect(addPX + 10 * scale, addPY + 8 * scale, 4 * scale, 3 * scale);
		g.fillRect(addPX + 3 * scale, addPY + 5 * scale, 2 * scale, 3 * scale);
		g.fillRect(addPX + 11 * scale, addPY + 5 * scale, 2 * scale, 3 * scale);
		g.fillRect(addPX + 4 * scale, addPY + 2 * scale, 1 * scale, 3 * scale);
		g.fillRect(addPX + 12 * scale, addPY + 2 * scale, 1 * scale, 3 * scale);
	}
	
	public static void drawBeamer(final BitsGraphics g, final int addPX, final int addPY, final int frame) {
		ApoMonoPuzzleGame.drawBeamer(g, addPX, addPY, frame, false, ApoMonoConstants.DARK_BRIGHT);
	}
	
	public static void drawBeamer(final BitsGraphics g, final int addPX, final int addPY, final int frame, final boolean bDouble, float[] color) {
		int scale = 1;
		if (bDouble) {
			scale = 2;
		}
		g.setColor(color[0], color[1], color[2], 1f);
		if (frame < 3) {
			g.fillRect(addPX + 2 * scale + 2 * frame * scale, addPY + 2 * frame * scale, (12 - 4 * frame) * scale, 2 * scale);
			g.fillRect(addPX + 14 * scale - 2 * frame * scale, addPY + 2 * scale + 2 * frame * scale, 2 * scale, (12 - 4 * frame) * scale);
			g.fillRect(addPX + 0 * scale + 2 * frame * scale, addPY + 2 * scale + 2 * frame * scale, 2 * scale, (12 - 4 * frame) * scale);
			g.fillRect(addPX + 2 * scale + 2 * frame * scale, addPY + 14 * scale - 2 * frame * scale, (12 - 4 * frame) * scale, 2 * scale);
		} else {
			g.fillRect(addPX + 6 * scale, addPY + 6 * scale, 4 * scale, 4 * scale);
		}
	}

	public static void drawBox(final BitsGraphics g, final int addPX, final int addPY, final boolean bWithShadow) {
		ApoMonoPuzzleGame.drawBox(g, addPX, addPY, bWithShadow, false);
	}
	
	public static void drawBox(final BitsGraphics g, final int addPX, final int addPY, final boolean bWithShadow, final boolean bDouble) {
		int scale = 1;
		if (bDouble) {
			scale = 2;
		}
		g.fillRect(addPX, addPY + 2 * scale, 14 * scale, 12 * scale);
		g.fillRect(addPX + 2 * scale, addPY, 12 * scale, 2 * scale);
		ApoMonoPuzzleGame.setDarkerColor(g);
		g.fillRect(addPX + 2 * scale, addPY + 14 * scale, 12 * scale, 2 * scale);
		g.fillRect(addPX + 14 * scale, addPY + 2 * scale, 2 * scale, 12 * scale);
		if (bWithShadow) {
			ApoMonoPuzzleGame.setBrighterColor(g);
			g.fillRect(addPX + 4 * scale, addPY + 16 * scale, 12 * scale, 2 * scale);
			g.fillRect(addPX + 16 * scale, addPY + 4 * scale, 2 * scale, 12 * scale);
			g.fillRect(addPX + 14 * scale, addPY + 14 * scale, 2 * scale, 2 * scale);
		}
	}
	
	public static void drawBlockPlusMirror(final BitsGraphics g, final int addPX, final int addPY) {
		ApoMonoPuzzleGame.drawBlockPlusMirror(g, addPX, addPY, false);
	}		
	
	public static void drawBlockPlusMirror(final BitsGraphics g, final int addPX, final int addPY, final boolean bDouble) {
		int scale = 1;
		if (bDouble) {
			scale = 2;
		}
		ApoMonoPuzzleGame.drawBox(g, addPX, addPY, false, bDouble);
		g.setColor(ApoMonoConstants.BRIGHT_DARK[0], ApoMonoConstants.BRIGHT_DARK[1], ApoMonoConstants.BRIGHT_DARK[2], 1f);
		g.fillRect(addPX + 3 * scale, addPY + 6 * scale, 10 * scale, 4 * scale);
		g.fillRect(addPX + 6 * scale, addPY + 3 * scale, 4 * scale, 10 * scale);
	}	

	public static void drawBlock(final BitsGraphics g, final int addPX, final int addPY) {
		ApoMonoPuzzleGame.drawBlock(g, addPX, addPY, false);
	}
	
	public static void drawBlock(final BitsGraphics g, final int addPX, final int addPY, final boolean bDouble) {
		int scale = 1;
		if (bDouble) {
			scale = 2;
		}
		ApoMonoPuzzleGame.drawBox(g, addPX, addPY, true, bDouble);
		g.setColor(ApoMonoConstants.BRIGHT_DARK[0], ApoMonoConstants.BRIGHT_DARK[1], ApoMonoConstants.BRIGHT_DARK[2], 1f);
		g.fillRect(addPX + 6 * scale, addPY + 6 * scale, 4 * scale, 4 * scale);
	}
	
	public static void drawOnStepBlock(final BitsGraphics g, final int addPX, final int addPY) {
		ApoMonoPuzzleGame.drawOnStepBlock(g, addPX, addPY, false);
	}
	
	public static void drawOnStepBlock(final BitsGraphics g, final int addPX, final int addPY, final boolean bDouble) {
		int scale = 1;
		if (bDouble) {
			scale = 2;
		}
		ApoMonoPuzzleGame.drawBox(g, addPX, addPY, true, bDouble);
		g.setColor(ApoMonoConstants.BRIGHT_DARK[0], ApoMonoConstants.BRIGHT_DARK[1], ApoMonoConstants.BRIGHT_DARK[2], 1f);
		g.setLineSize(2f * ApoMonoConstants.MAX);
		g.drawLine(addPX + 7 * scale, addPY + 1 * scale, addPX + 1 * scale, addPY + 7 * scale);
		g.drawLine(addPX + 13 * scale, addPY + 1 * scale, addPX + 1 * scale, addPY + 13 * scale);
		g.drawLine(addPX + 13 * scale, addPY + 7 * scale, addPX + 7 * scale, addPY + 13 * scale);
		g.setLineSize(1);
	}

	public static void drawMoveableBox(final BitsGraphics g, final int addPX, final int addPY) {
		ApoMonoPuzzleGame.drawMoveableBox(g, addPX, addPY, false);
	}
	
	public static void drawMoveableBox(final BitsGraphics g, final int addPX, final int addPY, final boolean bDouble) {
		int scale = 1;
		if (bDouble) {
			scale = 2;
		}
		g.setColor(ApoMonoConstants.DARK_BRIGHT[0], ApoMonoConstants.DARK_BRIGHT[1], ApoMonoConstants.DARK_BRIGHT[2], 1f);
		ApoMonoPuzzleGame.drawBox(g, addPX, addPY, false);
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		g.fillRect(addPX + 3 * scale, addPY + 3 * scale, 10 * scale, 10 * scale);
		g.setColor(ApoMonoConstants.DARK_BRIGHT[0], ApoMonoConstants.DARK_BRIGHT[1], ApoMonoConstants.DARK_BRIGHT[2], 1f);
		g.fillRect(addPX + 5 * scale, addPY + 5 * scale, 6 * scale, 6 * scale);
	}
	
	public static void setDarkerColor(final BitsGraphics g) {
		ApoMonoPuzzleGame.setChangeColor(g, -25, -25, -25);
	}
	
	public static void setBrighterColor(final BitsGraphics g) {
		if (ApoMonoConstants.BRIGHT[0] == ApoMonoConstants.BRIGHT_GREEN[0]) {
			ApoMonoPuzzleGame.setChangeColor(g, 70, 70, -5);
		} else {
			ApoMonoPuzzleGame.setChangeColor(g, +70, +70, +70);	
		}
	}
	
	public static void setChangeColor(final BitsGraphics g, final int valueRed, final int valueGreen, final int valuesBlue) {
		float[] values = g.getColor();
		float red = values[0] + valueRed/256f;
		float green = values[1] + valueGreen/256f;
		float blue = values[2] + valuesBlue/256f;
		float alpha = 1f;
		if (values.length > 3) {
			alpha = values[3];
		}
		if (red < 0) {
			red = 0;
		}
		if (red > 1) {
			red = 1f;
		}
		if (green < 0) {
			green = 0;
		}
		if (green > 1) {
			green = 1f;
		}
		if (blue < 0) {
			blue = 0;
		}
		if (blue > 1) {
			blue = 1f;
		}
		if (alpha < 0) {
			alpha = 0;
		}
		if (alpha > 1) {
			alpha = 1f;
		}
		g.setColor(red, green, blue, alpha);
	}
	
	public static void drawFinish(final BitsGraphics g, final int addPX, final int addPY, final int frame) {
		g.fillRect(addPX + 7 * 2, addPY + 0 * 2, 1 * 2, 9 * 2);
		ApoMonoPuzzleGame.setBrighterColor(g);
		g.fillRect(addPX + 8 * 2, addPY + 1 * 2 - 1, 1 * 1, 9 * 2);
		
		g.setColor(ApoMonoConstants.DARK_BRIGHT[0], ApoMonoConstants.DARK_BRIGHT[1], ApoMonoConstants.DARK_BRIGHT[2], 1f);
		ApoMonoPuzzleGame.setBrighterColor(g);
		if (frame == 0) {
			g.fillRect(addPX + 6 * 2, addPY + 1 * 2, 1 * 2, 3 * 2);
			g.fillRect(addPX + 5 * 2, addPY + 1 * 2, 1 * 2, 4 * 2);
			g.fillRect(addPX + 4 * 2, addPY + 2 * 2, 1 * 2, 3 * 2);
			g.fillRect(addPX + 3 * 2, addPY + 3 * 2, 1 * 2, 3 * 2);
			g.fillRect(addPX + 2 * 2, addPY + 4 * 2, 1 * 2, 2 * 2);
			g.fillRect(addPX + 1 * 2, addPY + 5 * 2, 1 * 2, 1 * 2);
		} else if (frame == 2) {
			g.fillRect(addPX + 6 * 2, addPY + 1 * 2, 1 * 2, 4 * 2);
			g.fillRect(addPX + 5 * 2, addPY + 2 * 2, 1 * 2, 4 * 2);
			g.fillRect(addPX + 4 * 2, addPY + 4 * 2, 1 * 2, 3 * 2);
		} else {
			g.fillRect(addPX + 6 * 2, addPY + 1 * 2, 1 * 2, 3 * 2);
			g.fillRect(addPX + 5 * 2, addPY + 1 * 2, 1 * 2, 4 * 2);
			g.fillRect(addPX + 4 * 2, addPY + 2 * 2, 1 * 2, 4 * 2);
			g.fillRect(addPX + 3 * 2, addPY + 4 * 2, 1 * 2, 3 * 2);
		}
	}

	private void drawInputRightBox(final BitsGraphics g, final boolean bActive) {
		int x = 55;
		int y = ApoMonoPuzzleGame.changeY + 245;
		this.drawInputBox(g, x, y, bActive);
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		for (int i = 0; i < 10; i++) {
			g.fillRect(x + 10 + i * 2, y + 10 + i * 1, 2, 20 - i * 2);
		}
	}
	
	private void drawInputLeftBox(final BitsGraphics g, final boolean bActive) {
		int x = 5;
		int y = ApoMonoPuzzleGame.changeY + 245;
		this.drawInputBox(g, x, y, bActive);
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		for (int i = 0; i < 10; i++) {
			g.fillRect(x + 30 - i * 2, y + 10 + i * 1, 2, 20 - i * 2);
		}
	}
	
	private void drawInputUpBox(final BitsGraphics g, final boolean bActive) {
		int x = 5;
		int y = ApoMonoPuzzleGame.changeY + 245;
		this.drawInputBox(g, x, y, bActive);
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		for (int i = 0; i < 10; i++) {
			g.fillRect(x + 10 + i * 1, y +  + 30 - i * 2, 20 - i * 2, 2);
		}
	}
	
	private void drawInputDownBox(final BitsGraphics g, final boolean bActive) {
		int x = 55;
		int y = ApoMonoPuzzleGame.changeY + 245;
		this.drawInputBox(g, x, y, bActive);
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		for (int i = 0; i < 10; i++) {
			g.fillRect(x + 10 + i * 1, y + 10 + i * 2, 20 - i * 2, 2);
		}
	}
	
	private void drawInputGravityBox(final BitsGraphics g, final boolean bActive) {
		int x = 105;
		int y = ApoMonoPuzzleGame.changeY + 245;
		this.drawInputBox(g, x, y, bActive);
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		x += 16;
		y += 8 + (int)(this.frameGravity);
		g.fillRect(x + 1 - 9, y, 6, 8);
		g.fillRect(x - 9, y + 1, 8, 6);
		
		g.fillRect(x + 1 + 9, y, 6, 8);
		g.fillRect(x + 9, y + 1, 8, 6);
		
		g.fillRect(x + 1, y, 6, 8);
		g.fillRect(x, y + 1, 8, 6);
		g.setColor(ApoMonoConstants.DARK_BRIGHT[0], ApoMonoConstants.DARK_BRIGHT[1], ApoMonoConstants.DARK_BRIGHT[2], 1f);
		g.fillRect(x + 2, y + 3, 4, 2);
		g.fillRect(x + 3, y + 2, 2, 4);
	}
	
	private void drawInputWithBox(final BitsGraphics g, final boolean bActive) {
		int x = ApoMonoConstants.GAME_WIDTH - 45;
		int y = ApoMonoPuzzleGame.changeY + 245;
		this.drawInputBox(g, x, y, bActive);
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		
		x += 12;
		g.fillRect(x + 5, y + 20 - 1, 2, 2);
		g.fillRect(x + 9, y + 20 - 1, 2, 2);
		g.fillRect(x + 5, y + 20 + 1, 6, 11);
		g.fillRect(x + 3, y + 20 + 2, 10, 2);
		g.fillRect(x + 11 - add * 8, y + 20 + 5, 2, 2);
		g.fillRect(x + 5, y + 20 + 12, 2, 5);
		g.fillRect(x + 9, y + 20 + 12, 2, 5);
		
		g.fillRect(x, y + 4 + 2, 16, 12);
		g.fillRect(x + 2, y + 4, 12, 16);
		g.setColor(ApoMonoConstants.DARK[0], ApoMonoConstants.DARK[1], ApoMonoConstants.DARK[2], 1f);
		g.fillRect(x + 3, y + 4 + 3, 10, 10);
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		g.fillRect(x + 5, y + 4 + 5, 6, 6);
	}
	
	private void drawInputMirrorHorizontalBox(final BitsGraphics g, final boolean bActive) {
		int x = ApoMonoConstants.GAME_WIDTH - 95;
		int y = ApoMonoPuzzleGame.changeY + 245;
		this.drawInputBox(g, x, y, bActive);
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		
		for (int i = 0; i < 9; i++) {
			g.fillRect(x + 19, y + 4 + i * 4, 2, 2);
		}
		x += 22;
		y += 12;
		g.fillRect(x, y + 2, 16, 12);
		g.fillRect(x + 2, y, 12, 16);
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 0.5f);
		x = x - 20;
		g.fillRect(x, y + 2, 16, 12);
		g.fillRect(x + 2, y, 12, 2);
		g.fillRect(x + 2, y + 14, 12, 2);
		
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
	}
	
	private void drawInputMirrorVerticalBox(final BitsGraphics g, final boolean bActive) {
		int x = ApoMonoConstants.GAME_WIDTH - 145;
		int y = ApoMonoPuzzleGame.changeY + 245;
		this.drawInputBox(g, x, y, bActive);
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		
		for (int i = 0; i < 9; i++) {
			g.fillRect(x + 4 + i * 4, y + 19, 2, 2);
		}
		x += 12;
		y += 2;
		g.fillRect(x, y + 2, 16, 12);
		g.fillRect(x + 2, y, 12, 16);
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 0.5f);
		y = y + 20;
		g.fillRect(x, y + 2, 16, 12);
		g.fillRect(x + 2, y, 12, 2);
		g.fillRect(x + 2, y + 14, 12, 2);
		
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
	}
	

	private void drawInputMirrorExitBox(final BitsGraphics g, final boolean bActive) {
		int x = ApoMonoConstants.GAME_WIDTH - 195;
		int y = ApoMonoPuzzleGame.changeY + 245;
		this.drawInputBox(g, x, y, bActive);
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		for (int i = 0; i < 11; i++) {
			g.fillRect(x + 10 + i * 2, y + 9 + i * 2, 2, 2);
			g.fillRect(x + 30 - i * 2, y + 9 + i * 2, 2, 2);
		}
	}
	
	private void drawInputBackBox(final BitsGLGraphics g, final boolean bActive) {
		int x = ApoMonoConstants.GAME_WIDTH/2 - 20;
		int y = ApoMonoPuzzleGame.changeY + 245;
		this.drawInputBox(g, x, y, bActive);
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		y -= 3;
		g.fillRect(x + 10, y + 30, 18, 2);
		g.fillRect(x + 10, y + 16, 18, 2);
		g.fillRect(x + 28, y + 28, 2, 2);
		g.fillRect(x + 30, y + 20, 2, 8);
		g.fillRect(x + 28, y + 18, 2, 2);
		for (int i = 1; i < 4; i++) {
			g.fillRect(x + 10 + i * 2, y + 16 - i * 2, 2, 2 + i * 4);
		}
	}
	
	private void drawInputBox(final BitsGraphics g, int x, int y, final boolean bActive) {
		g.setColor(ApoMonoConstants.DARK[0], ApoMonoConstants.DARK[1], ApoMonoConstants.DARK[2], 1f);
		g.fillRect(x + 2, y + 2, 36, 36);
		ApoMonoPuzzleGame.setDarkerColor(g);
		if ((p[5] > x) && (p[5] < x + 40) && (p[6] > y) && (p[6] < y + 40)) {
			g.setColor(ApoMonoConstants.BRIGHT_DARK[0], ApoMonoConstants.BRIGHT_DARK[1], ApoMonoConstants.BRIGHT_DARK[2], 1f);			
		}
		g.fillRect(x + 2, y, 36, 2);
		g.fillRect(x + 2, y + 38, 36, 2);
		g.fillRect(x, y + 2, 2, 36);
		g.fillRect(x + 38, y + 2, 2, 36);
		ApoMonoPuzzleGame.setBrighterColor(g);
		g.fillRect(x + 4, y + 40, 36, 2);
		g.fillRect(x + 40, y + 4, 2, 36);
		g.fillRect(x + 38, y + 38, 2, 2);
		if (bActive) {
			g.setColor(1f, 0f, 0f, 1f);
//			g.setColor(ApoMonoConstants.DARK_BRIGHT[0], ApoMonoConstants.DARK_BRIGHT[1], ApoMonoConstants.DARK_BRIGHT[2], 1f);
			g.setLineSize(3.5f * ApoMonoConstants.MAX);
			g.drawRect(x, y, 40, 40);
			g.setLineSize(1);
		}
	}
	
	private void drawInputLeftBoxForLevelchooser(final BitsGraphics g) {
		int x = ApoMonoConstants.GAME_WIDTH/2 - 95;
		int y = 1;
		this.drawInputBoxForLevelchooser(g, x, y, 38, 38);
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		for (int i = 0; i < 10; i++) {
			g.fillRect(x + 26 - i * 2, y + 10 + i * 1, 2, 20 - i * 2);
		}
	}
	
	private void drawInputRightBoxForLevelchooser(final BitsGraphics g) {
		int x = ApoMonoConstants.GAME_WIDTH/2 + 55;
		int y = 1;
		this.drawInputBoxForLevelchooser(g, x, y, 38, 38);
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);		
		for (int i = 0; i < 10; i++) {
			g.fillRect(x + 10 + i * 2, y + 10 + i * 1, 2, 20 - i * 2);
		}
	}
	
	private void drawInputBoxForLevelchooser(final BitsGraphics g, int x, int y, final int width, final int height) {
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