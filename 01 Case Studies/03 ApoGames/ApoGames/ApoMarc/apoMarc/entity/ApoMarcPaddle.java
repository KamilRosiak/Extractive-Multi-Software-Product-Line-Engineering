package apoMarc.entity;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import apoMarc.ApoMarcConstants;
import apoMarc.game.ApoMarcGame;

public class ApoMarcPaddle extends ApoMarcPlayer {

	public static final int MAX_NO_MOVEMENT = 10000;
	
	private int curTime;
	
	private boolean bFirstTouch;
	
	public ApoMarcPaddle(float x, float y, float width, Color myColor, Rectangle2D.Float collisionRec) {
		super(x, y, width, myColor, false, collisionRec);
		
		this.init();
	}
	
	public void init() {
		super.init();
		
		super.setSpeed(0);
		super.setMinWidth(1);
		super.setMaxWidth(3);
		super.setBRepeat(true);
		this.bFirstTouch = true;
	}

	public boolean isBFirstTouch() {
		return this.bFirstTouch;
	}

	public void setBFirstTouch(boolean firstTouch) {
		this.bFirstTouch = firstTouch;
	}

	@Override
	public void thinkEntity(int delta, ApoMarcGame game) {
		if (this.getY() + this.getWidth() < 0) {
			game.getPlayers()[1].addOnePoint();
			this.setNewPaddle(true);
		} else if (this.getY() > ApoMarcConstants.GAME_HEIGHT) {
			game.getPlayers()[0].addOnePoint();
			this.setNewPaddle(false);
		}
		float oldX = this.getX();
		float oldY = this.getY();
		
		super.setNewSpeedAndPosition(delta, game, this);

		float newX = this.getX();
		float newY = this.getY();
		
		if ((oldX != newX) || (oldY != newY)) {
			for (int i = 0; i < game.getWalls().length; i++) {
				if (game.getWalls()[i].intersects(this)) {
					ApoMarcWall wall = game.getWalls()[i];
					wall.nextColor();
					if (this.getSpeed() != 0) {
						if (wall.getHeight() > wall.getWidth()) {
							if ((super.getLos() >= 90) && (super.getLos() <= 270)) {
								if (super.getLos() < 180) {
									float dif = super.getLos() - 90;
									this.setLos(90 - dif);
								} else {
									float dif = 270 - super.getLos();
									this.setLos(270 + dif);
								}
								wall.makeEffects((int)(this.getX()), (int)(this.getY() + this.getWidth()/2));
							} else {
								if (super.getLos() < 180) {
									this.setLos(180 - super.getLos());
								} else {
									float dif = 270 - super.getLos();
									this.setLos(270 + dif);
								}
								wall.makeEffects((int)(this.getX() + this.getWidth()), (int)(this.getY() + this.getWidth()/2));
							}

							this.setPaddleBackWall(wall, this);
						} else {
							if (super.getLos() >= 180) {
								if (super.getLos() >= 270) {
									this.setLos(360 - super.getLos());
								} else {
									float dif = 180 - super.getLos();
									this.setLos(180 + dif);
								}
								wall.makeEffects((int)(this.getX() + this.getWidth()/2), (int)(this.getY()));
							} else {
								if (super.getLos() < 90) {
									this.setLos(360 - super.getLos());
								} else {
									float dif = 180 - super.getLos();
									this.setLos(180 + dif);
								}
								wall.makeEffects((int)(this.getX() + this.getWidth()/2), (int)(this.getY() + this.getWidth()));
							}
							this.setPaddleBackWall(wall, this);
							if (game.getWalls()[i].intersects(this)) {
								if ((super.getLos() >= 90) && (super.getLos() <= 270)) {
									if (super.getLos() < 180) {
										float dif = super.getLos() - 90;
										this.setLos(90 - dif);
									} else {
										float dif = 270 - super.getLos();
										this.setLos(270 + dif);
									}
									wall.makeEffects((int)(this.getX()), (int)(this.getY() + this.getWidth()/2));
								} else {
									if (super.getLos() < 180) {
										this.setLos(180 - super.getLos());
									} else {
										float dif = 270 - super.getLos();
										this.setLos(270 + dif);
									}
									wall.makeEffects((int)(this.getX() + this.getWidth()), (int)(this.getY() + this.getWidth()/2));
								}
							}
						}
					}
				}
			}
			if (super.checkPlayerIntersects(game, 0)) {
				this.makeEffects((int)(this.getX() + this.getWidth()/2), (int)(this.getY() + this.getWidth()));
			}
		}
		if (super.getX() < 10) {
			super.setX(10);
		} else if (super.getX() + super.getWidth() > ApoMarcConstants.GAME_WIDTH - 10) {
			super.setX(ApoMarcConstants.GAME_WIDTH - 10 - super.getWidth());
		}
		if (!this.bFirstTouch) {
			this.curTime += delta;
			if (super.getSpeed() != 0) {
				this.curTime = 0;
			}
			if (this.curTime > ApoMarcPaddle.MAX_NO_MOVEMENT) {
				if (this.getY() + this.getWidth()/2 < ApoMarcConstants.GAME_HEIGHT/2) {
					game.getPlayers()[1].addOnePoint();
					this.setNewPaddle(true);
				} else {
					game.getPlayers()[0].addOnePoint();
					this.setNewPaddle(false);
				}
			}
		}
	}
	
	public void littleMinSize() {
		super.littleMinSize();
		super.setBRepeat(true);
	}
	
	private void setNewPaddle(boolean bAbove) {
		this.setSpeed(0);
		this.setMyColor(this.getMyOrgColor());
		this.setX(ApoMarcConstants.GAME_WIDTH/2 - this.getWidth()/2);
		this.setY(ApoMarcConstants.GAME_HEIGHT/2 + 30);
		if (bAbove) {
			this.setY(ApoMarcConstants.GAME_HEIGHT/2 - 30 - this.getWidth());	
		}
		this.bFirstTouch = true;
	}

}
