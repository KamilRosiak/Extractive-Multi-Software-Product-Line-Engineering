package apoSimple.entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import org.apogames.entity.ApoEntity;

import apoSimple.ApoSimpleConstants;
import apoSimple.ApoSimpleImages;
import apoSimple.game.ApoSimpleGame;

public class ApoSimpleEntity extends ApoEntity {
	
	private final int MIN_MAX_TIME = 4000;
	
	private final int RANDOM_MAX_TIME = 5000;
	
	private final float GOODIE_MAX_CHANGE = 5;
	
	private int type;
	
	private int direction;
	
	private boolean bOver;
	
	private float changeY;
	
	private boolean bLevelUp;
	
	private boolean bCheat;
	
	private String level;
	
	private ApoSimpleAnimation animation;
	
	private int curTime;
	
	private int curMaxTime;
	
	private boolean bBlack;
	
	private int bGoodie;
	
	private float curChangeX;
	
	private float curChangeY;
	
	private float goodieChange;
	
	private boolean bGoodieChange;
	
	public ApoSimpleEntity(float x, float y, float width, float height, int type, int direction) {
		super(null, x, y, width, height);
		
		this.type = type;
		this.direction = direction;
		this.bLevelUp = false;
		this.bCheat = false;
		
		this.bBlack = false;
		this.bGoodie = -1;
		this.curChangeX = 0;
		this.curChangeY = 0;
		this.goodieChange = 0;
		this.bGoodieChange = false;
		
		if ((this.getDirection() >= ApoSimpleConstants.BLACK_UP) && (this.getDirection() <= ApoSimpleConstants.BLACK_RIGHT)) {
			this.setDirection(this.getDirection() - 8);
			this.bBlack = true;
		}
		if ((this.getDirection() >= ApoSimpleConstants.DOUBLE_BLACK_UP) && (this.getDirection() <= ApoSimpleConstants.DOUBLE_BLACK_RIGHT)) {
			this.setDirection(this.getDirection() - 4);
			this.bBlack = true;
		}
	}
	
	public void init() {
		super.init();
		
		this.animation = null;
		this.setRandomTime();
		this.curMaxTime = 10;
	}
	
	public void setBVisible(boolean bVisible) {
		if (bVisible) {
			super.setBVisible(bVisible);
		} else {
			if ((this.direction == ApoSimpleConstants.DOUBLE_DOWN) ||
				(this.direction == ApoSimpleConstants.DOUBLE_LEFT) ||
				(this.direction == ApoSimpleConstants.DOUBLE_RIGHT) ||
				(this.direction == ApoSimpleConstants.DOUBLE_UP)) {
				if (this.direction == ApoSimpleConstants.DOUBLE_DOWN) {
					this.direction = ApoSimpleConstants.DOWN;
				} else if (this.direction == ApoSimpleConstants.DOUBLE_LEFT) {
					this.direction = ApoSimpleConstants.LEFT;
				} else if (this.direction == ApoSimpleConstants.DOUBLE_RIGHT) {
					this.direction = ApoSimpleConstants.RIGHT;
				} else if (this.direction == ApoSimpleConstants.DOUBLE_UP) {
					this.direction = ApoSimpleConstants.UP;
				}
			} else if (this.direction == ApoSimpleConstants.STONE_3) {
				this.direction = ApoSimpleConstants.STONE_2;
			} else if (this.direction == ApoSimpleConstants.STONE_2) {
				this.direction = ApoSimpleConstants.STONE_1;
			} else if (this.direction == ApoSimpleConstants.STONE_1) {
				this.direction = ApoSimpleConstants.EMPTY;
			} else {
				super.setBVisible(bVisible);
			}
		}
	}
	
	public boolean isBBlack() {
		return this.bBlack;
	}

	public void setBBlack(boolean bBlack) {
		this.bBlack = bBlack;
	}

	public void setbGoodie(final int bGoodie) {
		this.bGoodie = bGoodie;
		
		this.goodieChange = 0;
		this.bGoodieChange = false;
	}

	private void setRandomTime() {
		this.curMaxTime = this.MIN_MAX_TIME + (int)(Math.random() * this.RANDOM_MAX_TIME);
		this.curTime = 0;
	}
	
	public boolean isBLevelUp() {
		return this.bLevelUp;
	}

	public void setBLevelUp(boolean levelUp) {
		this.bLevelUp = levelUp;
	}

	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public int getDirection() {
		return this.direction;
	}
	
	public int getOppositeDirection() {
		if (this.direction == ApoSimpleConstants.LEFT) {
			return ApoSimpleConstants.RIGHT;
		} else if (this.direction == ApoSimpleConstants.RIGHT) {
			return ApoSimpleConstants.LEFT;
		} else if (this.direction == ApoSimpleConstants.UP) {
			return ApoSimpleConstants.DOWN;
		} else if (this.direction == ApoSimpleConstants.DOWN) {
			return ApoSimpleConstants.UP;
		}
		return this.direction;
	}
	
	public int getNextDirectionClockwise() {
		if (this.direction == ApoSimpleConstants.LEFT) {
			return ApoSimpleConstants.DOWN;
		} else if (this.direction == ApoSimpleConstants.RIGHT) {
			return ApoSimpleConstants.UP;
		} else if (this.direction == ApoSimpleConstants.UP) {
			return ApoSimpleConstants.LEFT;
		} else if (this.direction == ApoSimpleConstants.DOWN) {
			return ApoSimpleConstants.RIGHT;
		} else if (this.direction == ApoSimpleConstants.DOUBLE_LEFT) {
			return ApoSimpleConstants.DOUBLE_DOWN;
		} else if (this.direction == ApoSimpleConstants.DOUBLE_RIGHT) {
			return ApoSimpleConstants.DOUBLE_UP;
		} else if (this.direction == ApoSimpleConstants.DOUBLE_UP) {
			return ApoSimpleConstants.DOUBLE_LEFT;
		} else if (this.direction == ApoSimpleConstants.DOUBLE_DOWN) {
			return ApoSimpleConstants.DOUBLE_RIGHT;
		}
		return this.direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
		
		if ((this.direction >= ApoSimpleConstants.BLACK_UP) && (this.direction <= ApoSimpleConstants.BLACK_RIGHT)) {
			this.bBlack = true;
			this.setDirection(this.direction - 8);
		} else if ((this.direction >= ApoSimpleConstants.DOUBLE_BLACK_UP) && (this.direction <= ApoSimpleConstants.DOUBLE_BLACK_RIGHT)) {
			this.bBlack = true;
			this.setDirection(this.direction - 4);
		} else if ((this.direction >= ApoSimpleConstants.STONE_3) && (this.direction <= ApoSimpleConstants.STONE_1)) {
			this.bBlack = false;
		}
		
		if (this.animation != null) {
			this.animation = null;
		}
	}

	public int getType() {
		return this.type;
	}
	
	public void setType(int type) {
		this.type = type;
	}

	public boolean isBOver() {
		return this.bOver;
	}

	public void setBOver(boolean over) {
		this.bOver = over;
	}

	public float getChangeY() {
		return this.changeY;
	}

	public void setChangeY(float changeY) {
		this.changeY = changeY;
	}

	public boolean isBCheat() {
		return this.bCheat;
	}

	public void setBCheat(boolean cheat) {
		this.bCheat = cheat;
	}

	public ApoSimpleAnimation getAnimation() {
		return this.animation;
	}

	public void setAnimation(ApoSimpleAnimation animation) {
		this.animation = animation;
	}
	
	public boolean isGoodiePossible() {
		if ((this.direction != ApoSimpleConstants.DOG_DOWN) &&
			(this.direction != ApoSimpleConstants.DOG_UP) &&
			(this.direction != ApoSimpleConstants.DOG_LEFT) &&
			(this.direction != ApoSimpleConstants.DOG_RIGHT) &&
			(this.direction != ApoSimpleConstants.EMPTY) &&
			(this.direction != ApoSimpleConstants.REAL_EMPTY) &&
			(this.direction != ApoSimpleConstants.FENCE) &&
			(this.direction != ApoSimpleConstants.STONE_1) &&
			(this.direction != ApoSimpleConstants.STONE_2) &&
			(this.direction != ApoSimpleConstants.STONE_3) &&
			(this.type == ApoSimpleConstants.EMPTY)) {
			return true;
		}
		return false;
	}

	public void think(int delta) {
		super.think(delta);
		
		if (this.bGoodie >= 0) {
			if (this.bGoodieChange) {
				this.goodieChange -= ApoSimpleDrive.VEC / 2 * delta;
			} else {
				this.goodieChange += ApoSimpleDrive.VEC / 2 * delta;
			}
			if (Math.abs(this.goodieChange) >= this.GOODIE_MAX_CHANGE) {
				this.bGoodieChange = !this.bGoodieChange;
			}
		}
		if (this.changeY > 0) {
			if (this.animation != null) {
				this.animation = null;
			}
			this.changeY -= ApoSimpleDrive.VEC * 3 * delta;
			if (this.changeY <= 0) {
				this.changeY = 0;
			}
		} else {
			if (this.animation != null) {
				this.animation.think(delta);
				if (this.animation.isBFinish()) {
					this.animation = null;
					this.setRandomTime();
				}
			} else {
				this.curTime += delta;
				if (this.curTime >= this.curMaxTime) {
					if (Math.random() * 100 > 70) {
						this.animation = ApoSimpleAnimationMake.getRandomAnimation(this);
						if (this.animation == null) {
							this.setRandomTime();
						}
					} else {
						this.animation = null;
						this.setRandomTime();
					}
				}
			}
		}
	}

	public void render(Graphics2D g, int changeX, int changeY) {
		if (!this.bLevelUp) {
			if (this.type != ApoSimpleConstants.EMPTY) {
				g.drawImage(ApoSimpleImages.ORIGINAL_GREEN, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY), null);
			}
			int doubleChange = 5;
			if (this.direction == ApoSimpleConstants.FENCE) {
//					g.drawImage(ApoSimpleImages.FENCE_FREE, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY), null);
			} else {
				g.drawImage(ApoSimpleImages.ORIGINAL, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY), null);
				if (this.animation != null) {
					if ((this.direction >= ApoSimpleConstants.DOUBLE_UP) && (this.direction <= ApoSimpleConstants.DOUBLE_BLACK_RIGHT)) {
						this.animation.render(g, (int)(this.getX() + this.curChangeX + this.goodieChange - doubleChange), (int)(this.getY() + this.curChangeY - this.changeY - doubleChange), changeX, changeY);
						this.animation.render(g, (int)(this.getX() + this.curChangeX + this.goodieChange + doubleChange), (int)(this.getY() + this.curChangeY - this.changeY + doubleChange), changeX, changeY);					
					} else {
						this.animation.render(g, (int)(this.getX() + this.curChangeX + this.goodieChange), (int)(this.getY() + this.curChangeY - this.changeY), changeX, changeY);
					}
				} else if (this.isBBlack()) {
					if (this.direction == ApoSimpleConstants.DOWN) {
						g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_DOWN, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY), null);
					} else if (this.direction == ApoSimpleConstants.RIGHT) {
						g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_LEFT, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY), null);
					} else if (this.direction == ApoSimpleConstants.LEFT) {
						g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_RIGHT, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY), null);
					} else if (this.direction == ApoSimpleConstants.UP) {
						g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_UP, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY), null);
					} else if (this.direction == ApoSimpleConstants.DOUBLE_DOWN) {
						g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_DOWN, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange - doubleChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY - doubleChange), null);
						g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_DOWN, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange + doubleChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY + doubleChange), null);
					} else if (this.direction == ApoSimpleConstants.DOUBLE_RIGHT) {
						g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_LEFT, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange - doubleChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY - doubleChange), null);
						g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_LEFT, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange + doubleChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY + doubleChange), null);
					} else if (this.direction == ApoSimpleConstants.DOUBLE_LEFT) {
						g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_RIGHT, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange - doubleChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY - doubleChange), null);
						g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_RIGHT, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange + doubleChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY + doubleChange), null);
					} else if (this.direction == ApoSimpleConstants.DOUBLE_UP) {
						g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_UP, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange - doubleChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY - doubleChange), null);
						g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_UP, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange + doubleChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY + doubleChange), null);
					}
				} else {
					if (this.direction == ApoSimpleConstants.UP) {
						g.drawImage(ApoSimpleImages.ORIGINAL_ABOVE, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY), null);
					} else if (this.direction == ApoSimpleConstants.DOWN) {
						g.drawImage(ApoSimpleImages.ORIGINAL_DOWN, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY), null);
					} else if (this.direction == ApoSimpleConstants.RIGHT) {
						g.drawImage(ApoSimpleImages.ORIGINAL_LEFT, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY), null);
					} else if (this.direction == ApoSimpleConstants.LEFT) {
						g.drawImage(ApoSimpleImages.ORIGINAL_RIGHT, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY), null);
					} else if (this.direction == ApoSimpleConstants.DOUBLE_DOWN) {
						g.drawImage(ApoSimpleImages.ORIGINAL_DOWN, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange - doubleChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY - doubleChange), null);
						g.drawImage(ApoSimpleImages.ORIGINAL_DOWN, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange + doubleChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY + doubleChange), null);
					} else if (this.direction == ApoSimpleConstants.DOUBLE_RIGHT) {
						g.drawImage(ApoSimpleImages.ORIGINAL_LEFT, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange - doubleChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY - doubleChange), null);
						g.drawImage(ApoSimpleImages.ORIGINAL_LEFT, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange + doubleChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY + doubleChange), null);
					} else if (this.direction == ApoSimpleConstants.DOUBLE_LEFT) {
						g.drawImage(ApoSimpleImages.ORIGINAL_RIGHT, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange - doubleChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY - doubleChange), null);
						g.drawImage(ApoSimpleImages.ORIGINAL_RIGHT, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange + doubleChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY + doubleChange), null);
					} else if (this.direction == ApoSimpleConstants.DOUBLE_UP) {
						g.drawImage(ApoSimpleImages.ORIGINAL_ABOVE, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange - doubleChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY - doubleChange), null);
						g.drawImage(ApoSimpleImages.ORIGINAL_ABOVE, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange + doubleChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY + doubleChange), null);
					} else if (this.direction == ApoSimpleConstants.HIDDEN_DUCK) {
						g.drawImage(ApoSimpleImages.HIDDEN_DUCK, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY), null);
					} else if (this.direction == ApoSimpleConstants.HIDDEN_RABBIT) {
						g.drawImage(ApoSimpleImages.HIDDEN_RABBIT, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY), null);
					}
				}
				
				if (this.direction == ApoSimpleConstants.DOG_DOWN) {
					g.drawImage(ApoSimpleImages.ORIGINAL_DOG_DOWN, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY), null);
				} else if (this.direction == ApoSimpleConstants.DOG_RIGHT) {
					g.drawImage(ApoSimpleImages.ORIGINAL_DOG_LEFT, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY), null);
				} else if (this.direction == ApoSimpleConstants.DOG_LEFT) {
					g.drawImage(ApoSimpleImages.ORIGINAL_DOG_RIGHT, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY), null);
				} else if (this.direction == ApoSimpleConstants.DOG_UP) {
					g.drawImage(ApoSimpleImages.ORIGINAL_DOG_UP, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY), null);
				} else if (this.direction == ApoSimpleConstants.STONE_1) {
					g.drawImage(ApoSimpleImages.STONE_1, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY), null);
				} else if (this.direction == ApoSimpleConstants.STONE_2) {
					g.drawImage(ApoSimpleImages.STONE_2, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY), null);
				} else if (this.direction == ApoSimpleConstants.STONE_3) {
					g.drawImage(ApoSimpleImages.STONE_3, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY), null);
				} else if (this.direction == ApoSimpleConstants.HIDDEN_DUCK_2) {
					g.drawImage(ApoSimpleImages.HIDDEN_DUCK_2, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY), null);
				}
			}
			
			if (this.bGoodie > 0) {
				g.setFont(ApoSimpleGame.FONT_BUTTON);
				int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
				String s = String.valueOf(this.bGoodie);
				int w = g.getFontMetrics().stringWidth(s);
				if (this.isBBlack()) {
					g.setColor(Color.WHITE);
				} else {
					g.setColor(Color.BLACK);	
				}
				if (this.getDirection() == ApoSimpleConstants.RIGHT) {
					g.drawString(s, (int)(this.getX() + this.getWidth()/2 - w*1/4 - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() + this.getHeight()/2 + h/2 - 8 - changeY + this.curChangeY - this.changeY));
				} else if (this.getDirection() == ApoSimpleConstants.LEFT) {
					g.drawString(s, (int)(this.getX() + this.getWidth()/2 - w*3/4 - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() + this.getHeight()/2 + h/2 - 8 - changeY + this.curChangeY - this.changeY));
				} else if (this.getDirection() == ApoSimpleConstants.UP) {
					g.drawString(s, (int)(this.getX() + this.getWidth()/2 - w/2 - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() + this.getHeight()/2 + h/2 - 9 - changeY + this.curChangeY - this.changeY));
				} else if (this.getDirection() == ApoSimpleConstants.DOWN) {
					g.drawString(s, (int)(this.getX() + this.getWidth()/2 - w/2 - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() + this.getHeight()/2 + h/2 - 9 - changeY + this.curChangeY - this.changeY));
				}
			}
			
			if (this.bOver) {
				g.drawImage(ApoSimpleImages.ORIGINAL_OVER, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY), null);
			}
		} else {
			g.drawImage(ApoSimpleImages.ORIGINAL_LEVELUP, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY), null);
			if (this.getLevel() != null) {
				g.setColor(Color.BLACK);
				g.setFont(ApoSimpleGame.FONT_STATICS);
				String s = this.getLevel();
				int w = g.getFontMetrics().stringWidth(s);
				int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
				g.drawString(s, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange + this.getWidth()/2 - w/2), (int)(this.getY() - changeY + this.curChangeY - this.changeY + this.getHeight()/2 + h/2));
			}
		}
		
		if ((ApoSimpleConstants.CHEAT) && (this.bCheat)) {
			g.setColor(Color.RED);
			Stroke stroke = g.getStroke();
			g.setStroke(new BasicStroke(5));
			g.drawOval((int)(this.getX() - changeX + this.curChangeX + this.goodieChange + 4), (int)(this.getY() - changeY + this.curChangeY - this.changeY + 4), (int)(this.getWidth() - 9), (int)(this.getWidth() - 9));
			g.setStroke(stroke);
		}
	}
	
	public void renderOver(Graphics2D g, int changeX, int changeY) {
		g.drawImage(ApoSimpleImages.ORIGINAL_OVER, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY), null);		
	}
	
	public void renderArrow(Graphics2D g, int changeX, int changeY) {
		if (!this.bLevelUp) {
			if (this.direction == ApoSimpleConstants.DOWN) {
				g.drawImage(ApoSimpleImages.ORIGINAL_DOWN_ARROW, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY), null);
			} else if (this.direction == ApoSimpleConstants.RIGHT) {
				g.drawImage(ApoSimpleImages.ORIGINAL_LEFT_ARROW, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY), null);
			} else if (this.direction == ApoSimpleConstants.LEFT) {
				g.drawImage(ApoSimpleImages.ORIGINAL_RIGHT_ARROW, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY), null);
			} else if (this.direction == ApoSimpleConstants.UP) {
				g.drawImage(ApoSimpleImages.ORIGINAL_ABOVE_ARROW, (int)(this.getX() - changeX + this.curChangeX + this.goodieChange), (int)(this.getY() - changeY + this.curChangeY - this.changeY), null);
			}
		}
	}
	
	public void renderNot(Graphics2D g, int changeX, int changeY) {
		g.drawImage(ApoSimpleImages.ORIGINAL_RED, (int)(this.getX() - changeX + this.curChangeX), (int)(this.getY() - changeY + this.curChangeY - this.changeY), null);
	}

	public void renderGoodiePossible(Graphics2D g, int changeX, int changeY) {
		if (this.isGoodiePossible()) {
			g.setColor(Color.BLUE.brighter().brighter());
			Stroke stroke = g.getStroke();
			g.setStroke(new BasicStroke(3));
			g.drawOval((int)(this.getX() - changeX + this.curChangeX + 4), (int)(this.getY() - changeY + this.curChangeY - this.changeY + 4), (int)(this.getWidth() - 9), (int)(this.getWidth() - 9));
			g.setStroke(stroke);
		}
	}
}
