package apoSimple.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import apoSimple.ApoSimpleConstants;
import apoSimple.ApoSimpleImages;
import apoSimple.game.ApoSimpleGame;
import apoSimple.game.ApoSimpleLevelGame;
import apoSimple.game.ApoSimplePanel;

public class ApoSimpleDrive extends ApoSimpleEntity {
	
	public static final float MAX = 8f;
	
	public static final float VEC = 0.14f;
	
	private int points;
	
	private int multi;
	
	private int addPoints;

	private float changeY;
	
	private boolean bUp;
	
	private ArrayList<ApoSimpleDriveHelp> help;
	
	public ApoSimpleDrive(float x, float y, float width, float height, int type, int direction, boolean bBlack) {
		super(x, y, width, height, type, direction);
		
		super.setBBlack(bBlack);
		
		this.points = 1;
		this.multi = 1;
		this.addPoints = 0;

		this.changeY = 0;
		this.bUp = true;
		if (direction >= ApoSimpleConstants.DOUBLE_UP) {
			if ((direction >= ApoSimpleConstants.DOUBLE_UP) && (direction <= ApoSimpleConstants.DOUBLE_RIGHT)) {
				direction -= 13;
				this.setDirection(direction);
			}
		}
		
		this.help = new ArrayList<ApoSimpleDriveHelp>();
	}

	public int getMulti() {
		return this.multi;
	}

	public void think(int delta, ApoSimpleGame game) {
		float oldX = this.getX();
		float oldY = this.getY();
		if (this.getDirection() == ApoSimpleConstants.UP) {
			this.setY(this.getY() - (ApoSimpleDrive.VEC * delta));
			if (game.getLevelX(oldY + this.getHeight()) != game.getLevelX(this.getY() + this.getHeight())) {
				int x = game.getLevelX(this.getX() + this.getWidth()/2);
				int y = game.getLevelY(this.getY() + this.getHeight());
				if ((y >= 0) && (y < game.getEntities().length)) {
					ApoSimpleEntity entity = game.getEntities()[y][x];
					this.setEntity(entity, game);
				}
			}
		} else if (this.getDirection() == ApoSimpleConstants.DOWN) {
			this.setY(this.getY() + (ApoSimpleDrive.VEC * delta));
			if (game.getLevelX(oldY) != game.getLevelX(this.getY())) {
				int x = game.getLevelX(this.getX() + this.getWidth()/2);
				int y = game.getLevelY(this.getY());
				if ((y >= 0) && (y < game.getEntities().length)) {
					ApoSimpleEntity entity = game.getEntities()[y][x];
					this.setEntity(entity, game);
				}
			}
		} else if (this.getDirection() == ApoSimpleConstants.LEFT) {
			this.setX(this.getX() + (ApoSimpleDrive.VEC * delta));
			if (game.getLevelX(oldX) != game.getLevelX(this.getX())) {
				int x = game.getLevelX(this.getX());
				int y = game.getLevelY(this.getY() + this.getHeight()/2);
				if ((x >= 0) && (x < game.getEntities()[0].length)) {
					ApoSimpleEntity entity = game.getEntities()[y][x];
					this.setEntity(entity, game);
				}
			}
		} else if (this.getDirection() == ApoSimpleConstants.RIGHT) {
			this.setX(this.getX() - (ApoSimpleDrive.VEC * delta));
			if (game.getLevelX(oldX + this.getWidth()) != game.getLevelX(this.getX() + this.getWidth())) {
				int x = game.getLevelX(this.getX() + this.getWidth());
				int y = game.getLevelY(this.getY() + this.getHeight()/2);
				if ((x >= 0) && (x < game.getEntities()[0].length)) {
					ApoSimpleEntity entity = game.getEntities()[y][x];
					this.setEntity(entity, game);
				}
			}
		}
		if (!game.getRec().intersects(new Rectangle2D.Float(this.getX(), this.getY(), this.getWidth(), this.getHeight()))) {
			game.driverOutside();
		}
		this.thinkUpAndDown(delta);
		for (int i = 0; i < this.help.size(); i++) {
			this.help.get(i).think(delta);
		}
	}
	
	public void thinkUpAndDown(int delta) {
		if (this.bUp) {
			this.changeY += delta * 0.015f;
			if (this.changeY >= ApoSimpleDrive.MAX) {
				this.bUp = false;
			}
		} else {
			this.changeY -= delta * 0.015f;
			if (this.changeY <= 0) {
				this.bUp = true;
			}
		}
	}
	
	public void thinkMove(int delta) {
		if (this.getDirection() == ApoSimpleConstants.UP) {
			this.setY(this.getY() - (ApoSimpleDrive.VEC * delta));
		} else if (this.getDirection() == ApoSimpleConstants.DOWN) {
			this.setY(this.getY() + (ApoSimpleDrive.VEC * delta));
		} else if (this.getDirection() == ApoSimpleConstants.LEFT) {
			this.setX(this.getX() + (ApoSimpleDrive.VEC * delta));
		} else if (this.getDirection() == ApoSimpleConstants.RIGHT) {
			this.setX(this.getX() - (ApoSimpleDrive.VEC * delta));
		}
	}
	
	public int getSheeps() {
		return this.help.size();
	}
	
	private void setEntity(ApoSimpleEntity entity, ApoSimpleGame game) {
		int dir = entity.getDirection();
		if (entity.getDirection() == ApoSimpleConstants.FENCE) {
			game.driverOutside();
		} else if (entity.isBVisible()) {
			if ((entity.getDirection() >= ApoSimpleConstants.DOG_UP) && (entity.getDirection() <= ApoSimpleConstants.DOG_RIGHT) && 
				(this.help.size() < ApoSimpleConstants.MIN_DOG_SIZE)) {
				entity.setDirection(this.getOppositeDirection() + ApoSimpleConstants.DOG_UP - 1);
				this.changeOppositeDirection();
				game.getStrings().add(new ApoSimpleString(entity.getX(), entity.getY() - entity.getHeight(), entity.getWidth(), "wuff wuff", true));
				game.getGame().playSound(ApoSimplePanel.SOUND_DOG, 100);
			} else 	if ((entity.getDirection() >= ApoSimpleConstants.STONE_3) && (entity.getDirection() <= ApoSimpleConstants.STONE_1)) {
				this.changeOppositeDirection();
				if (ApoSimpleConstants.REGION.equals("de")) {
					game.getStrings().add(new ApoSimpleString(entity.getX(), entity.getY() - entity.getHeight(), entity.getWidth(), "ouch ouch", true));					
				} else {
					game.getStrings().add(new ApoSimpleString(entity.getX(), entity.getY() - entity.getHeight(), entity.getWidth(), "ouch ouch", true));
				}
				entity.setBVisible(false);
			} else {
				boolean bBlack = false;
				if (entity.isBBlack()) {
					bBlack = true;
				}
				
				if (dir != ApoSimpleConstants.EMPTY) {
					this.addHelp(entity, game, bBlack);
				}
				
				if ((dir >= ApoSimpleConstants.DOUBLE_UP) && (dir <= ApoSimpleConstants.DOUBLE_RIGHT)) {
					dir -= 13;
				}
				
				if ((this.getDirection() != dir) && (dir != ApoSimpleConstants.EMPTY)) {
					if ((entity.getDirection() >= ApoSimpleConstants.DOG_UP) && (entity.getDirection() <= ApoSimpleConstants.DOG_RIGHT)) {
						
					} else {
						this.setDirection(dir);
					}
				}
				if (entity.getDirection() == ApoSimpleConstants.FENCE) {
					
				} else {
					entity.setBVisible(false);
					if (!entity.isBVisible()) {
						entity.setBBlack(false);
					}
					int addPoints = 0;

					if ((entity.getDirection() >= ApoSimpleConstants.DOG_UP) && (entity.getDirection() <= ApoSimpleConstants.DOG_RIGHT)) {
						game.getStrings().add(new ApoSimpleString(entity.getX() - 15, entity.getY() - entity.getHeight(), entity.getWidth() + 30, "maehhh", true));
						game.getGame().playSound(ApoSimplePanel.SOUND_DOG_SHEEP, 100);
					}
					if (entity.getType() != ApoSimpleConstants.EMPTY) {
						if (ApoSimpleConstants.REGION.equals("de")) {
							game.getStrings().add(new ApoSimpleString(entity.getX(), entity.getY() - entity.getHeight(), entity.getWidth(), "lecker", true));					
						} else {
							game.getStrings().add(new ApoSimpleString(entity.getX(), entity.getY() - entity.getHeight(), entity.getWidth(), "tasty", true));
						}
						game.getGame().playSound(ApoSimplePanel.SOUND_MAEH, 100);
						this.multi += 1;
						this.addPoints += 20;
					}
					
					this.points += 1;
					if (!(game instanceof ApoSimpleLevelGame)) {
						if (this.points % ApoSimpleConstants.ADD_MOVE == 0) {
							game.setMoves(game.getMoves() + 1);
							game.getStrings().add(new ApoSimpleString(entity.getX() - 15, entity.getY() + 30, entity.getWidth() + 30, ApoSimpleConstants.DRIVE_EXTRAMOVE, true));
							game.getGame().playSound(ApoSimplePanel.SOUND_EXTRA, 100);
						}
						if (this.points % ApoSimpleConstants.SIZE_ADD_COINS == 0) {
							game.getStrings().add(new ApoSimpleString(entity.getX() - 15, entity.getY() + 30, entity.getWidth() + 30, ApoSimpleConstants.DRIVE_EXTRACOINS, true));
							game.getGame().setCoins(game.getGame().getCoins() + ApoSimpleConstants.ADD_COINS, false);
							game.getGame().playSound(ApoSimplePanel.SOUND_EXTRA, 100);
						}
					}
					addPoints = (this.points - 1) * this.multi + this.addPoints;
					game.setPoints(game.getPoints() + addPoints);
					if (!(game instanceof ApoSimpleLevelGame)) {
						if (addPoints > 0) {
							game.getStrings().add(new ApoSimpleString(entity.getX(), entity.getY(), entity.getWidth(), "+ "+String.valueOf(addPoints)));
						}
					}
					entity.setType(ApoSimpleConstants.EMPTY);
				}
			}
		}
	}
	
	private void addHelp(ApoSimpleEntity entity, ApoSimpleGame game, boolean bBlack) {
		this.help.add(new ApoSimpleDriveHelp(bBlack));
		if (game.isDogInside()) {
			if (this.help.size() == ApoSimpleConstants.MIN_DOG_SIZE) {
				game.getStrings().add(new ApoSimpleString(entity.getX() - 15, entity.getY() - entity.getHeight(), entity.getWidth() + 30, ApoSimpleConstants.DRIVE_STRONG, true));
				game.getGame().playSound(ApoSimplePanel.SOUND_STRONG, 100);
			}
		}
	}
	
	private void changeOppositeDirection() {
		if (this.getDirection() == ApoSimpleConstants.LEFT) {
			this.setDirection(ApoSimpleConstants.RIGHT);
		} else if (this.getDirection() == ApoSimpleConstants.RIGHT) {
			this.setDirection(ApoSimpleConstants.LEFT);
		} else if (this.getDirection() == ApoSimpleConstants.UP) {
			this.setDirection(ApoSimpleConstants.DOWN);
		} else if (this.getDirection() == ApoSimpleConstants.DOWN) {
			this.setDirection(ApoSimpleConstants.UP);
		}
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		this.renderShadow(g, changeX, changeY);
		this.renderSheep(g, changeX, changeY);
	}
	
	public void renderShadow(Graphics2D g, int changeX, int changeY) {
		g.drawImage(ApoSimpleImages.ORIGINAL_SHADOW, (int)(this.getX() - changeX), (int)(this.getY() - changeY), null);
	}
	
	public void renderSheep(Graphics2D g, int changeX, int changeY) {
		if (super.getAnimation() != null) {
			super.getAnimation().render(g, (int)(super.getX()), (int)(super.getY()), changeX, changeY);
			return;
		}
		for (int i = 0; i < this.help.size(); i++) {
			if (this.help.get(i).isBlack()) {
				if (this.getDirection() == ApoSimpleConstants.UP) {
					g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_UP, (int)(this.getX() + this.help.get(i).getX() - changeX), (int)(this.getY() + this.help.get(i).getY() - changeY), null);
				} else if (this.getDirection() == ApoSimpleConstants.DOWN) {
					g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_DOWN, (int)(this.getX() + this.help.get(i).getX() - changeX), (int)(this.getY() + this.help.get(i).getY() - changeY), null);
				} else if (this.getDirection() == ApoSimpleConstants.RIGHT) {
					g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_LEFT, (int)(this.getX() + this.help.get(i).getX() - changeX), (int)(this.getY() + this.help.get(i).getY() - changeY), null);
				} else if (this.getDirection() == ApoSimpleConstants.LEFT) {
					g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_RIGHT, (int)(this.getX() + this.help.get(i).getX() - changeX), (int)(this.getY() + this.help.get(i).getY() - changeY), null);
				}
			} else {
				if (this.getDirection() == ApoSimpleConstants.UP) {
					g.drawImage(ApoSimpleImages.ORIGINAL_ABOVE, (int)(this.getX() + this.help.get(i).getX() - changeX), (int)(this.getY() + this.help.get(i).getY() - changeY), null);
				} else if (this.getDirection() == ApoSimpleConstants.DOWN) {
					g.drawImage(ApoSimpleImages.ORIGINAL_DOWN, (int)(this.getX() + this.help.get(i).getX() - changeX), (int)(this.getY() + this.help.get(i).getY() - changeY), null);
				} else if (this.getDirection() == ApoSimpleConstants.RIGHT) {
					g.drawImage(ApoSimpleImages.ORIGINAL_LEFT, (int)(this.getX() + this.help.get(i).getX() - changeX), (int)(this.getY() + this.help.get(i).getY() - changeY), null);
				} else if (this.getDirection() == ApoSimpleConstants.LEFT) {
					g.drawImage(ApoSimpleImages.ORIGINAL_RIGHT, (int)(this.getX() + this.help.get(i).getX() - changeX), (int)(this.getY() + this.help.get(i).getY() - changeY), null);
				}
			}
		}
		
		if (super.isBBlack()) {
			if (this.getDirection() == ApoSimpleConstants.UP) {
				g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_UP, (int)(this.getX() - changeX), (int)(this.getY() - changeY - this.changeY), null);
			} else if (this.getDirection() == ApoSimpleConstants.DOWN) {
				g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_DOWN, (int)(this.getX() - changeX), (int)(this.getY() - changeY - this.changeY), null);
			} else if (this.getDirection() == ApoSimpleConstants.RIGHT) {
				g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_LEFT, (int)(this.getX() - changeX), (int)(this.getY() - changeY - this.changeY), null);
			} else if (this.getDirection() == ApoSimpleConstants.LEFT) {
				g.drawImage(ApoSimpleImages.ORIGINAL_BLACK_RIGHT, (int)(this.getX() - changeX), (int)(this.getY() - changeY - this.changeY), null);
			}
		} else {
			if (this.getDirection() == ApoSimpleConstants.UP) {
				g.drawImage(ApoSimpleImages.ORIGINAL_ABOVE, (int)(this.getX() - changeX), (int)(this.getY() - changeY - this.changeY), null);
			} else if (this.getDirection() == ApoSimpleConstants.DOWN) {
				g.drawImage(ApoSimpleImages.ORIGINAL_DOWN, (int)(this.getX() - changeX), (int)(this.getY() - changeY - this.changeY), null);
			} else if (this.getDirection() == ApoSimpleConstants.RIGHT) {
				g.drawImage(ApoSimpleImages.ORIGINAL_LEFT, (int)(this.getX() - changeX), (int)(this.getY() - changeY - this.changeY), null);
			} else if (this.getDirection() == ApoSimpleConstants.LEFT) {
				g.drawImage(ApoSimpleImages.ORIGINAL_RIGHT, (int)(this.getX() - changeX), (int)(this.getY() - changeY - this.changeY), null);
			}
		}
	}
	
	public void renderSheepSize(Graphics2D g, int changeX, int changeY) {
		g.setColor(Color.RED);
		g.setFont(ApoSimpleGame.FONT_BUTTON);
		int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
		String s = String.valueOf(this.getSheeps() + 1);
		int w = g.getFontMetrics().stringWidth(s);
		if (this.getSheeps() >= ApoSimpleConstants.MIN_DOG_SIZE) {
			if (this.isBBlack()) {
				g.setColor(Color.WHITE);
			} else {
				g.setColor(Color.BLACK);	
			}
		}
		if (this.getDirection() == ApoSimpleConstants.RIGHT) {
			g.drawString(s, (int)(this.getX() + this.getWidth()/2 - w*1/4 - changeX), (int)(this.getY() + this.getHeight()/2 + h/2 - 8 - changeY - this.changeY));
		} else if (this.getDirection() == ApoSimpleConstants.LEFT) {
			g.drawString(s, (int)(this.getX() + this.getWidth()/2 - w*3/4 - changeX), (int)(this.getY() + this.getHeight()/2 + h/2 - 8 - changeY - this.changeY));
		} else if (this.getDirection() == ApoSimpleConstants.UP) {
			g.drawString(s, (int)(this.getX() + this.getWidth()/2 - w/2 - changeX), (int)(this.getY() + this.getHeight()/2 + h/2 - 9 - changeY - this.changeY));
		}

	}
	
}
