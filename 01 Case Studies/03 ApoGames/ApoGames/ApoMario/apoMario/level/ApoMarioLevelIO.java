package apoMario.level;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;

import org.apogames.ApoIO;

import apoMario.ApoMarioConstants;
import apoMario.ApoMarioImageContainer;
import apoMario.entity.ApoMarioCannon;
import apoMario.entity.ApoMarioCoin;
import apoMario.entity.ApoMarioDestructableWall;
import apoMario.entity.ApoMarioFlower;
import apoMario.entity.ApoMarioGumba;
import apoMario.entity.ApoMarioKoopa;
import apoMario.entity.ApoMarioQuestionmark;
import apoMario.entity.ApoMarioWall;

public class ApoMarioLevelIO extends ApoIO {

	private ApoMarioLevel level;
	
	public ApoMarioLevelIO(ApoMarioLevel level) {
		this.level = level;
	}
	
	@Override
	public boolean readLevel(DataInputStream data) throws EOFException, IOException {
		if (this.level != null) {
			int width = data.readInt();
			int height = data.readInt();
			int difficulty = data.readInt();
			int time = data.readInt();
			this.level.setTime(time);
			this.level.makeEmptyLevel(width, height, difficulty);
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int value = data.readInt();
					if (value == 1) {
						this.level.makeGroundWall(x, y);
					} else if (value == 2) {
						this.level.makeBoxCoin(x, y);
					} else if (value == 3) {
						BufferedImage iWall = ApoMarioImageContainer.TILES_LEVEL.getSubimage(4 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 8 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 3 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
						BufferedImage myWall = iWall.getSubimage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
						this.level.makeWall(myWall, false, true, false, false, -1, x, y);
					} else if (value == 4) {
						BufferedImage iWall = ApoMarioImageContainer.TILES_LEVEL.getSubimage(4 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 8 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 3 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
						BufferedImage myWall = iWall.getSubimage(1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE);
						this.level.makeWall(myWall, true, false, false, false, -1, x, y);
					} else if (value == 5) {
						int goodie = data.readInt();
						this.level.makeBoxQuestionMark(x, y, goodie);
					} else if (value == 6) {
						boolean bDestructable = data.readBoolean();
						boolean bNull = data.readBoolean();
						int goodie = data.readInt();
						this.level.makeWall(bDestructable, bNull, goodie, x, y);
					} else if (value == 7) {
						int myHeight = data.readInt();
						int shootTime = data.readInt();
						int startTime = data.readInt();
						this.level.makeCanon(x, y, myHeight, shootTime, startTime);
					}
				}
			}
			int enemies = data.readInt();
			for (int i = 0; i < enemies; i++) {
				int value = data.readInt();
				if (value == 1) {
					int x = data.readInt();
					int y = data.readInt();
					int timeToShow = data.readInt();
					int startTime = data.readInt();
					this.level.makeFlower(x, y, 2, timeToShow, true, startTime);
				} else if (value == 2) {
					int x = data.readInt();
					int y = data.readInt();
					boolean bRed = data.readBoolean();
					boolean bFall = data.readBoolean();
					boolean bFly = data.readBoolean();
					boolean bLeft = data.readBoolean();
					this.level.makeKoopa(bRed, x, y, bFall, bFly, bLeft);
				} else if (value == 3) {
					int x = data.readInt();
					int y = data.readInt();
					boolean bImmortal = data.readBoolean();
					boolean bFall = data.readBoolean();
					boolean bFly = data.readBoolean();
					boolean bLeft = data.readBoolean();
					if (bImmortal) {
						this.level.makeImmortal(x, y, bFall, bFly, bLeft);
					} else {
						this.level.makeGumba(x, y, bFall, bFly, bLeft);
					}
				}
			}
			int playerX = data.readInt();
			int playerY = data.readInt();
			this.level.makePlayer(playerX * ApoMarioConstants.TILE_SIZE, (playerY) * ApoMarioConstants.TILE_SIZE);
			int finishX = data.readInt();
			int finishY = data.readInt();
			this.level.makeFinish((finishX) * ApoMarioConstants.TILE_SIZE, (finishY) * ApoMarioConstants.TILE_SIZE);
		}
		return false;
	}

	@Override
	public boolean writeLevel(DataOutputStream data) throws IOException {
		if (this.level != null) {
			data.writeInt(this.level.getWidth());
			data.writeInt(this.level.getHeight());
			data.writeInt(this.level.getDifficulty());
			data.writeInt(this.level.getTime());
			int width = this.level.getWidth();
			int height = this.level.getHeight();
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (this.level.getLevelEntities()[y][x] != null) {
						if (this.level.getLevelEntities()[y][x] instanceof ApoMarioDestructableWall) {
							data.writeInt(6);
							ApoMarioDestructableWall wall = (ApoMarioDestructableWall)this.level.getLevelEntities()[y][x];
							data.writeBoolean(wall.isBDestructable());
							if (wall.getIWall() == null) {
								data.writeBoolean(true);
							} else {
								data.writeBoolean(false);
							}
							data.writeInt(wall.getGoodie());
						} else if (this.level.getLevelEntities()[y][x] instanceof ApoMarioQuestionmark) {
							data.writeInt(5);
							ApoMarioQuestionmark wall = (ApoMarioQuestionmark)this.level.getLevelEntities()[y][x];
							data.writeInt(wall.getGoodie());
						} else if (this.level.getLevelEntities()[y][x] instanceof ApoMarioCoin) {
							data.writeInt(2);
						} else if (this.level.getLevelEntities()[y][x] instanceof ApoMarioCannon) {
							data.writeInt(7);
							ApoMarioCannon cannon = (ApoMarioCannon)this.level.getLevelEntities()[y][x];
							data.writeInt(cannon.getMyHeight());
							data.writeInt(cannon.getShootTime());
							data.writeInt(cannon.getStartTime());
						} else if (this.level.getLevelEntities()[y][x] instanceof ApoMarioWall) {
							ApoMarioWall wall = (ApoMarioWall)this.level.getLevelEntities()[y][x];
							if (wall.isBNoCollision()) {
								data.writeInt(3);								
							} else if (wall.isBOnlyAboveWall()) {
								data.writeInt(4);
							} else {
								data.writeInt(1);
							}
						}
					}
				}
			}
			
			int enemies = this.level.getEnemies().size();
			data.writeInt(enemies);
			for (int i = 0; i < enemies; i++) {
				if (this.level.getEnemies().get(i) instanceof ApoMarioFlower) {
					ApoMarioFlower flower = (ApoMarioFlower)this.level.getEnemies().get(i);
					data.writeInt(1);
					data.writeInt((int)(flower.getX()/ApoMarioConstants.TILE_SIZE));
					data.writeInt((int)(flower.getY()/ApoMarioConstants.TILE_SIZE));
					data.writeInt(flower.getTimeToShow());
					data.writeInt(flower.getStartTime());
				} else if (this.level.getEnemies().get(i) instanceof ApoMarioKoopa) {
					ApoMarioKoopa koopa = (ApoMarioKoopa)this.level.getEnemies().get(i);
					data.writeInt(2);
					data.writeInt((int)(koopa.getX()/ApoMarioConstants.TILE_SIZE));
					data.writeInt((int)(koopa.getY()/ApoMarioConstants.TILE_SIZE));
					data.writeBoolean(!koopa.isBGreen());
					data.writeBoolean(koopa.isBFall());
					data.writeBoolean(koopa.isBFlyOriginal());
					data.writeBoolean(koopa.isBLeft());
				} else if (this.level.getEnemies().get(i) instanceof ApoMarioGumba) {
					ApoMarioGumba gumba = (ApoMarioGumba)this.level.getEnemies().get(i);
					data.writeInt(3);
					data.writeInt((int)(gumba.getX()/ApoMarioConstants.TILE_SIZE));
					data.writeInt((int)(gumba.getY()/ApoMarioConstants.TILE_SIZE));
					data.writeBoolean(gumba.isBImmortal());
					data.writeBoolean(gumba.isBFall());
					data.writeBoolean(gumba.isBFlyOriginal());
					data.writeBoolean(gumba.isBLeft());
				}
			}
			data.writeInt((int)(this.level.getPlayers().get(0).getX() / ApoMarioConstants.TILE_SIZE));
			data.writeInt((int)(this.level.getPlayers().get(0).getY() / ApoMarioConstants.TILE_SIZE));
			
			data.writeInt((int)(this.level.getFinish().getX() / ApoMarioConstants.TILE_SIZE));
			data.writeInt((int)(this.level.getFinish().getY() / ApoMarioConstants.TILE_SIZE));
		}
		return false;
	}

	
	
}
