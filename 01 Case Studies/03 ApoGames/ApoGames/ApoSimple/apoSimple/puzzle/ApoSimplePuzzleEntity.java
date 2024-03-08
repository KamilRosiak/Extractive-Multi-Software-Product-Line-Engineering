package apoSimple.puzzle;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;

import apoSimple.ApoSimpleConstants;
import apoSimple.entity.ApoSimpleEntity;

public class ApoSimplePuzzleEntity extends ApoSimpleEntity {

	private boolean bEnding;
	
	private float transparent;
	
	public ApoSimplePuzzleEntity(float x, float y, float width, float height, int direction) {
		super(x, y, width, height, ApoSimpleConstants.EMPTY, direction);
	}
	
	public ApoSimplePuzzleEntity getClone() {
		ApoSimplePuzzleEntity entity = new ApoSimplePuzzleEntity(this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.getDirection());
		if (this.isBBlack()) {
			entity = new ApoSimplePuzzleEntity(this.getX(), this.getY(), this.getWidth(), this.getHeight(), ApoSimpleConstants.BLACK_LEFT);
		}
		return entity;
	}
	
	public void init() {
		super.init();
		
		this.transparent = 255;
		this.bEnding = false;
	}
	
	public boolean isbEnding() {
		return bEnding;
	}

	public void setbEnding(boolean bEnding) {
		this.bEnding = bEnding;
	}

	public void think(int delta, ApoSimplePuzzleGame puzzle) {
		super.think(delta);
		
		if (this.bEnding) {
			if (this.transparent <= 0) {
				super.setBVisible(false);
			} else {
				this.transparent -= (float)delta * 110f / 255f;
			}
		} else {
			if (this.isMoving()) {
				int oldY = (int)(this.getY() / 65f);
				this.setY(this.getY() + 0.3f * delta);
				int newY = (int)(this.getY() / 65f);
				if (newY != oldY) {
					this.setY(newY * 65);
					if (newY + 1 < puzzle.getLevel().getLevel().length) {
						int x = (int)(this.getX() / 65f);
						if (puzzle.getLevel().getLevel()[newY + 1][x] == null) {
							puzzle.getLevel().getLevel()[newY + 1][x] = this;
							puzzle.getLevel().getLevel()[newY][x] = null;
							this.setY(this.getY() + 0.3f * delta);
						}
					}
				}
			} else {
				this.checkMove(delta, puzzle);
			}
		}
	}
	
	private void checkMove(int delta, ApoSimplePuzzleGame puzzle) {
		int y = (int)(this.getY() / 65);
		int x = (int)(this.getX() / 65);
		if (y + 1 < puzzle.getLevel().getLevel().length) {
			if (x == 3) {
				if (puzzle.getLevel().getLevel()[y + 1][x] == null) {

				}
			}
			if (puzzle.getLevel().getLevel()[y + 1][x] == null) {
				puzzle.getLevel().getLevel()[y + 1][x] = this;
				puzzle.getLevel().getLevel()[y][x] = null;
				this.setY(this.getY() + 0.3f * delta);
			}
		}
	}
	
	public boolean isMoving() {
		if (((int)(this.getY()) % 65) != 0) {
			return true;
		}
		return false;
	}
	
	public void render(final Graphics2D g, int changeX, int changeY) {
		if (this.bEnding) {
			Composite composite = g.getComposite();
			float percent = (float)(this.transparent)/255f;
			if (percent > 1) {
				percent = 1;
			}
			if (percent < 0) {
				percent = 0;
			}
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, percent));
			super.render(g, changeX, changeY);
			g.setComposite(composite);
		} else {
			super.render(g, changeX, changeY);
		}
	}

}
