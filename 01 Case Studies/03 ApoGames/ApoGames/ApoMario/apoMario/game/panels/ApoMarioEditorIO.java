package apoMario.game.panels;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;

import org.apogames.ApoIO;

import apoMario.ApoMarioConstants;
import apoMario.ApoMarioImageContainer;
import apoMario.entity.ApoMarioCannon;
import apoMario.entity.ApoMarioCoin;
import apoMario.entity.ApoMarioDestructableWall;
import apoMario.entity.ApoMarioEntity;
import apoMario.entity.ApoMarioFlower;
import apoMario.entity.ApoMarioGumba;
import apoMario.entity.ApoMarioKoopa;
import apoMario.entity.ApoMarioQuestionmark;
import apoMario.entity.ApoMarioWall;
import apoMario.level.ApoMarioLevel;

/**
 * EditorIOpanelklasse<br />
 * Klasse, die sich um das Speicher und Laden der Editorlevels kümmert <br />
 * @author Dirk Aporius
 */
public class ApoMarioEditorIO extends ApoIO {

	public static final int ENEMY_GUMBA = 1;
	public static final int ENEMY_KOOPA = 2;
	public static final int ENEMY_FLOWER = 3;
	
	private ApoMarioLevel level;
	
	private String levelString;
	
	public ApoMarioEditorIO(ApoMarioLevel level) {
		this.level = level;
	}

	@Override
	public boolean readLevel(String fileName) {
		this.levelString = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
		return super.readLevel(fileName);
	}
	
	@Override
	public boolean readLevel(DataInputStream data) throws EOFException, IOException {
		try {
			int width = data.readInt();
			int height = data.readInt();
			this.level.getEnemies().clear();
			this.level.setWidth(width);
			this.level.setLevelEntities(new ApoMarioEntity[height][width]);
			int count = 0;
			for (int y = height - 1; y >= 0; y--) {
				for (int x = 0; x < width; x++) {
					count += 1;
					int type = data.readInt();
					if (type == ApoMarioConstants.EMPTY) {
					} else if (type == ApoMarioConstants.WALL) {
						boolean bTube = data.readBoolean();
						if (bTube) {
							int timeToShow = data.readInt();
							int startTime = data.readInt();
							boolean bEnemy = data.readBoolean();
							this.level.makeFlower(x, y, 2, timeToShow, bEnemy, startTime);
						} else {
							this.level.makeGroundWall(x, y);
						}
					} else if (type == ApoMarioConstants.QUESTIONMARKBOX) {
						int goodie = data.readInt();
						this.level.makeBoxQuestionMark(x, y, goodie);
					} else if (type == ApoMarioConstants.NO_GROUND_WALL) {
						boolean bTube = data.readBoolean();
						this.level.makeWall(false, false, -1, x, y);
						if (bTube) {
						}
					} else if (type == ApoMarioConstants.DESTRUCTIBLEBOX) {
						boolean bNull = data.readBoolean();
						int goodie = data.readInt();
						this.level.makeWall(true, bNull, goodie, x, y);
					} else if (type == ApoMarioConstants.CANNON) {
						int shootTime = data.readInt();
						int startTime = data.readInt();
						this.level.makeCanon(x, y, height, shootTime, startTime);
					} else if (type == ApoMarioConstants.COIN) {
						this.level.makeBoxCoin(x, y);
					} else if (type == ApoMarioConstants.NO_COLLISION_WALL) {
						this.level.makeWall(ApoMarioImageContainer.TILES_LEVEL.getSubimage(0 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 8 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 1 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE), true, true, false, false, -1, x, y);
					}
				}
			}
			this.level.makeGroundCorrect();
			this.level.makeGroundCorrectNoCollision();
			
			int enemySize = data.readInt();
			for (int i = 0; i < enemySize; i++) {
				int type = data.readInt();
				if (type == ApoMarioEditorIO.ENEMY_GUMBA) {
					int x = data.readInt();
					int y = data.readInt();
					boolean bFall = data.readBoolean();
					boolean bFly = data.readBoolean();
					boolean bLeft = data.readBoolean();
					boolean bImmortal = data.readBoolean();
					if (!bImmortal) {
						this.level.makeGumba(x, y, bFall, bFly, bLeft);
					} else {
						this.level.makeImmortal(x, y, bFall, bFly, bLeft);
					}
				} else if (type == ApoMarioEditorIO.ENEMY_KOOPA) {
					int x = data.readInt();
					int y = data.readInt();
					boolean bFall = data.readBoolean();
					boolean bFly = data.readBoolean();
					boolean bLeft = data.readBoolean();
					this.level.makeKoopa(false, x, y, bFall, bFly, bLeft);
				}
			}
			
			this.level.setPlayerAndFinish();
			this.level.setLevelInt(-1);
			this.level.setLevelString(width, this.levelString);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public void writeLevel(String fileName) {
		this.levelString = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
		super.writeLevel(fileName);
	}
	
	@Override
	public boolean writeLevel(DataOutputStream data) throws IOException {
		int width = this.level.getLevelEntities()[0].length;
		data.writeInt(width);
		int height = this.level.getLevelEntities().length;
		data.writeInt(height);
		
		int count = 0;
		for (int y = height - 1; y >= 0; y--) {
			for (int x = 0; x < width; x++) {
				count += 1;
				if (this.level.getLevelEntities()[y][x] != null) {
					if (this.level.getLevelEntities()[y][x] instanceof ApoMarioQuestionmark) {
						ApoMarioQuestionmark q = (ApoMarioQuestionmark)(this.level.getLevelEntities()[y][x]);
						data.writeInt(ApoMarioConstants.QUESTIONMARKBOX);
						data.writeInt(q.getGoodie());
					} else if (this.level.getLevelEntities()[y][x] instanceof ApoMarioDestructableWall) {
						ApoMarioDestructableWall w = (ApoMarioDestructableWall)(this.level.getLevelEntities()[y][x]);
						data.writeInt(ApoMarioConstants.DESTRUCTIBLEBOX);
						if (w.getIWall() == null) {
							data.writeBoolean(true);
						} else {
							data.writeBoolean(false);
						}
						data.writeInt(w.getGoodie());
					} else if (this.level.getLevelEntities()[y][x] instanceof ApoMarioCoin) {
						data.writeInt(ApoMarioConstants.COIN);
					} else if (this.level.getLevelEntities()[y][x] instanceof ApoMarioWall) {
						boolean bTube = false;
						ApoMarioWall w = (ApoMarioWall)(this.level.getLevelEntities()[y][x]);
						if ((w.isBOnlyAboveWall()) || (w.isBNoCollision())) {
							data.writeInt(ApoMarioConstants.NO_COLLISION_WALL);	
						} else if (w.isBTube()) {
							Point p = w.getTubePoint();
							if ((p.x == 0) && (p.y == 0)) {
								bTube = true;
								data.writeInt(ApoMarioConstants.WALL);
								data.writeBoolean(bTube);
								ApoMarioEntity entity = this.isThereAnEnemy(x, y);
								try {
									if (entity != null) {
										ApoMarioFlower flower = (ApoMarioFlower)(entity);
										data.writeInt(flower.getTimeToShow());
										data.writeInt(flower.getStartTime());
										data.writeBoolean(true);
									} else {
										data.writeInt(-1);
										data.writeInt(-1);
										data.writeBoolean(false);										
									}
								} catch (Exception ex) {
									ex.printStackTrace();
									data.writeInt(-1);
									data.writeInt(-1);
									data.writeBoolean(false);
								}
							} else {
								data.writeInt(ApoMarioConstants.EMPTY);
							}
						} else if (w.isBCanon()) {
							Point p = w.getTubePoint();
							if ((p.x == 0) && (p.y == 0)) {
								data.writeInt(ApoMarioConstants.CANNON);
								data.writeInt(((ApoMarioCannon)(this.level.getLevelEntities()[y][x])).getShootTime());
								data.writeInt(((ApoMarioCannon)(this.level.getLevelEntities()[y][x])).getStartTime());
							} else {
								data.writeInt(ApoMarioConstants.EMPTY);
							}
						} else if (!w.isBGround()){
							data.writeInt(ApoMarioConstants.NO_GROUND_WALL);
							data.writeBoolean(bTube);
						} else {
							data.writeInt(ApoMarioConstants.WALL);
							data.writeBoolean(bTube);
						}
					}
				} else {
					data.writeInt(ApoMarioConstants.EMPTY);
				}
			}
		}
		
		int enemySize = this.level.getEnemies().size();
		data.writeInt(enemySize);
		for (int i = 0; i < enemySize; i++) {
			if (this.level.getEnemies().get(i) instanceof ApoMarioGumba) {
				ApoMarioGumba gumba = (ApoMarioGumba)(this.level.getEnemies().get(i));
				int type = ApoMarioEditorIO.ENEMY_KOOPA;
				if (!(this.level.getEnemies().get(i) instanceof ApoMarioKoopa)) {
					type = ApoMarioEditorIO.ENEMY_GUMBA;
				}
				int x = (int)(gumba.getX() / ApoMarioConstants.TILE_SIZE);
				int y = (int)(gumba.getY() / ApoMarioConstants.TILE_SIZE);
				boolean bFly = gumba.isBFly();
				boolean bFall = gumba.isBFall();
				boolean bLeft = gumba.isBLeft();
				data.writeInt(type);
				data.writeInt(x);
				data.writeInt(y);
				data.writeBoolean(bFall);
				data.writeBoolean(bFly);
				data.writeBoolean(bLeft);
				if (!(this.level.getEnemies().get(i) instanceof ApoMarioKoopa)) {
					boolean bImmortal = gumba.isBImmortal();
					data.writeBoolean(bImmortal);
				}
			} else {
				data.writeInt(ApoMarioConstants.EMPTY);
			}
		}
		this.level.setLevelString(width, this.levelString);
		
		return false;
	}
	
	private ApoMarioEntity isThereAnEnemy(int x, int y) {
		for (int i = 0; i < this.level.getEnemies().size(); i++) {
			if (this.level.getEnemies().get(i).intersects((float)x * (float)ApoMarioConstants.TILE_SIZE, ((float)y) * (float)ApoMarioConstants.TILE_SIZE, this.level.getEnemies().get(i).getWidth(), this.level.getEnemies().get(i).getHeight())) {
				return this.level.getEnemies().get(i);
			}
		}
		return null;
	}

}
