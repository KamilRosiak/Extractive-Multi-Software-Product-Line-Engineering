package apoBot.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.apogames.entity.ApoAnimation;

import apoBot.ApoBotConstants;

/**
 * Klasse für den eigenen Spieler
 * @author Dirk Aporius
 *
 */
public class ApoBotPlayer extends ApoAnimation {

	private final float VEC = 0.043f * ApoBotConstants.SIZE;
	
	private final int MAX_JUMP = 16 * ApoBotConstants.SIZE;
	
	/** boolean-Variable die angibt ob der Spieler zu seiner Ausgangsposition läuft */
	private int curDir;

	private int startDir;

	private boolean bRun;
	
	private float runX, runY;
	/** boolean Variable, ob der Spieler springt oder nicht */
	private boolean bJump;
	/** boolena Variable, die angibt, ob der Spieler gerade hoch springt oder net */
	private boolean bUp;
	
	private float jumpHeight, curJumpHeight;
	
	public ApoBotPlayer(BufferedImage background, float x, float y, float width, float height, int tiles, long time) {
		super(background, x, y, width, height, tiles, time);
		
		this.setStartDir(ApoBotConstants.DIR_SOUTH_DOWNRIGHT);

	}
	
	public void init() {
		super.init();
		
		this.setCurDir(this.startDir);
		this.bRun = false;
		this.bJump = false;
		this.bUp = true;
		this.curJumpHeight = 0;
		this.jumpHeight = 0;
		this.runX = 0;
		this.runY = 0;
	}
	
	public boolean isBUp() {
		return this.bUp;
	}

	public void setBUp(boolean up) {
		this.bUp = up;
	}

	public float getJumpHeight() {
		return this.jumpHeight;
	}

	public void setJumpHeight(float jumpHeight) {
		this.jumpHeight = jumpHeight;
		this.curJumpHeight = 0;
	}

	public float getCurJumpHeight() {
		return this.curJumpHeight;
	}

	public void setCurJumpHeight(float curJumpHeight) {
		this.curJumpHeight = curJumpHeight;
	}

	public boolean isBJump() {
		return this.bJump;
	}

	public void setBJump(boolean jump) {
		this.bJump = jump;
	}

	public float getRunX() {
		return this.runX;
	}

	public void setRunX(float runX) {
		this.runX = runX;
	}

	public float getRunY() {
		return this.runY;
	}

	public void setRunY(float runY) {
		this.runY = runY;
	}

	public boolean isBRun() {
		return this.bRun;
	}

	public void setBRun(boolean run) {
		this.bRun = run;
	}

	public int getStartDir() {
		return this.startDir;
	}

	public void setStartDir(int startDir) {
		this.startDir = startDir;
		this.setCurDir(this.startDir);		
	}

	public int getCurDir() {
		return curDir;
	}

	/**
	 * wechselt die Laufrichtung des Spielers
	 * @param curDir
	 */
	public void setCurDir(int curDir) {
		this.curDir = curDir;
		if (this.curDir > ApoBotConstants.DIR_WEST_DOWNLEFT) {
			this.curDir = ApoBotConstants.DIR_NORTH_UPLEFT;
		} else if (this.curDir < ApoBotConstants.DIR_NORTH_UPLEFT) {
			this.curDir = ApoBotConstants.DIR_WEST_DOWNLEFT;
		}
	}

	public void think(int delta) {
		super.think(delta);
		if (this.bRun) {
			if (this.curDir == ApoBotConstants.DIR_SOUTH_DOWNRIGHT) {
				this.setRunX(this.getRunX() + this.VEC * (float)delta);
				this.setRunY(this.getRunY() + this.VEC * 0.5f * (float)delta);
			} else if (this.curDir == ApoBotConstants.DIR_WEST_DOWNLEFT) {
				this.setRunX(this.getRunX() - this.VEC * (float)delta);
				this.setRunY(this.getRunY() + this.VEC * 0.5f * (float)delta);
			} else if (this.curDir == ApoBotConstants.DIR_NORTH_UPLEFT) {
				this.setRunX(this.getRunX() - this.VEC * (float)delta);
				this.setRunY(this.getRunY() - this.VEC * 0.5f * (float)delta);
			} else if (this.curDir == ApoBotConstants.DIR_EAST_UPRIGHT) {
				this.setRunX(this.getRunX() + this.VEC * (float)delta);
				this.setRunY(this.getRunY() - this.VEC * 0.5f * (float)delta);
			}
			if (((Math.abs(this.getRunX())) >= 16 * ApoBotConstants.SIZE) || ((Math.abs(this.getRunY())) >= 16 * ApoBotConstants.SIZE)) {
				this.setBRun(false);
			}
		}
		if (this.bJump) {
			if (this.bUp) {
				this.curJumpHeight += this.VEC * 40;
				if (this.curJumpHeight >= this.MAX_JUMP) {
					this.bUp = !this.bUp;
				}
			} else {
				this.curJumpHeight -= this.VEC * 40;
				if (this.curJumpHeight <= this.jumpHeight) {
					this.bJump = false;
					this.curJumpHeight = this.jumpHeight;
					if (!this.isBRun()) {
						this.curJumpHeight = 0;
						this.bJump = false;
						this.bUp = true;
						this.jumpHeight = -1;
					}
				}
			}
		}
		if ((!this.isBRun()) && (!this.isBJump())) {
			if (((Math.abs(this.getRunX())) >= 16 * ApoBotConstants.SIZE) || ((Math.abs(this.getRunY())) >= 16 * ApoBotConstants.SIZE)) {
				this.setRunX(0);
				this.setRunY(0);
				if (this.curDir == ApoBotConstants.DIR_SOUTH_DOWNRIGHT) {
					this.setX(this.getX() + 1);
				} else if (this.curDir == ApoBotConstants.DIR_WEST_DOWNLEFT) {
					this.setY(this.getY() - 1);
				} else if (this.curDir == ApoBotConstants.DIR_NORTH_UPLEFT) {
					this.setX(this.getX() - 1);
				} else if (this.curDir == ApoBotConstants.DIR_EAST_UPRIGHT) {
					this.setY(this.getY() + 1);
				}
			}
			this.curJumpHeight = 0;
			this.bJump = false;
			this.bUp = true;
			this.jumpHeight = -1;
		}
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		// Spieler wird nur angezeigt, wenn er auch angezeigt werden kann
		if (super.getIBackground() == null) {
		} else {
			if (super.isBVisible()) {
				g.drawImage(super.getIBackground().getSubimage( (int)( this.getFrame() * ApoBotConstants.TILE_WIDTH * ApoBotConstants.SIZE ), this.getCurDir() * ApoBotConstants.TILE_HEIGHT * ApoBotConstants.SIZE, (int)ApoBotConstants.TILE_WIDTH * ApoBotConstants.SIZE, (int)(ApoBotConstants.TILE_HEIGHT * ApoBotConstants.SIZE) ), (int)(this.getRunX() + changeX), (int)(this.getRunY() - this.getCurJumpHeight() + changeY), null );
			}
		}
	}

}