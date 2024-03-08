package apoRelax.level;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import apoRelax.ApoRelaxImages;
import apoRelax.entity.ApoRelaxTile;

public class ApoRelaxLevelGeneration {

	private static ApoRelaxImages images = new ApoRelaxImages();
	
	private static BufferedImage[] imageArray;
	
	private static ApoRelaxLevelGenerationRec[][] recs;
	
	public static final ApoRelaxLevel getLevel(int level) {
		ApoRelaxLevelGeneration.initImageArray();
		if (level == 0) {
			return ApoRelaxLevelGeneration.getFirstLevel();
		} else if (level == 1) {
			return ApoRelaxLevelGeneration.getSecondLevel();
		} else if (level == 2) {
			return ApoRelaxLevelGeneration.getThirdLevel();
		} else if (level == 3) {
			return ApoRelaxLevelGeneration.getFourthLevel();
		} else if (level == 4) {
			return ApoRelaxLevelGeneration.getFifthLevel();
		} else if (level == 5) {
			return ApoRelaxLevelGeneration.getSixLevel();
		} else if (level == 6) {
			return ApoRelaxLevelGeneration.getSevenLevel();
		} else if (level == 7) {
			return ApoRelaxLevelGeneration.getEightLevel();
		} else if (level == 8) {
			return ApoRelaxLevelGeneration.getNineLevel();
		} else if (level == 9) {
			return ApoRelaxLevelGeneration.getTenLevel();
		} else if (level == 10) {
			return ApoRelaxLevelGeneration.getElevenLevel();
		} else if (level == 11) {
			return ApoRelaxLevelGeneration.getTwelveLevel();
		}
		return null;
	}
	
	public static final void initImageArray() {
		if (imageArray == null) {
			imageArray = new BufferedImage[] {
				ApoRelaxLevelGeneration.getImage("images/first.jpg", false),
				ApoRelaxLevelGeneration.getImage("images/broadway.jpg", false),
				ApoRelaxLevelGeneration.getImage("images/first.png", false),
				ApoRelaxLevelGeneration.getImage("images/markt.jpg", false),
				ApoRelaxLevelGeneration.getImage("images/donkey.jpg", false),
				ApoRelaxLevelGeneration.getImage("images/blumen.jpg", false),
				ApoRelaxLevelGeneration.getImage("images/kloster.jpg", false),
				ApoRelaxLevelGeneration.getImage("images/halloween.jpg", false),
				ApoRelaxLevelGeneration.getImage("images/planierraupe.jpg", false),
				ApoRelaxLevelGeneration.getImage("images/stau.jpg", false),
				ApoRelaxLevelGeneration.getImage("images/futuristic.jpg", false),
				ApoRelaxLevelGeneration.getImage("images/auge.jpg", false)
				
			};
		}
	}
	
	public static final ApoRelaxLevel getEditorLevel(final BufferedImage iLevel, ArrayList<Rectangle2D.Double> recs) {
		ApoRelaxLevelGeneration.initImageArray();
		
		ApoRelaxTile[] tiles = new ApoRelaxTile[recs.size()];
		for (int i = 0; i < tiles.length; i++) {
			int x = (int)Math.round((float)(recs.get(i).getX()));
			int y = (int)Math.round((float)(recs.get(i).getY()));
			int width = (int)Math.round((float)(recs.get(i).getWidth()));
			int height = (int)Math.round((float)(recs.get(i).getHeight()));
			if (x + width > iLevel.getWidth()) {
				width = iLevel.getWidth() - x;
			}
			if (y + height > iLevel.getHeight()) {
				height = iLevel.getHeight() - y;
			}
			tiles[i] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, x, y, width, height, i);
		}
		
		ApoRelaxLevel simpleLevel = new ApoRelaxLevel("Random Level", "Click on a tile to select it. Then, click a different piece to swap them.", tiles, iLevel.getWidth(), iLevel.getHeight());
		
		ApoRelaxLevelGeneration.swapLevel(simpleLevel, tiles);
		
		return simpleLevel;
	}	
	
	public static final BufferedImage getRandomImage() {
		ApoRelaxLevelGeneration.initImageArray();
		int random = (int)(Math.random() * ApoRelaxLevelGeneration.imageArray.length);
		if (random >= ApoRelaxLevelGeneration.imageArray.length) {
			random = 0;
		}
		return ApoRelaxLevelGeneration.imageArray[random];
	}
	
	public static final ApoRelaxLevel getRandomLevel(final BufferedImage iLevel) {
		if (ApoRelaxLevelGeneration.recs == null) {
			ApoRelaxLevelGeneration.makeRecs();
		}
		
		int rec = (int)(Math.random() * ApoRelaxLevelGeneration.recs.length);
		if (rec == ApoRelaxLevelGeneration.recs.length) {
			rec = 0;
		}
		ApoRelaxTile[] tiles = new ApoRelaxTile[ApoRelaxLevelGeneration.recs[rec][0].getX()];
		for (int i = 1; i <= tiles.length; i++) {
			int x = (int)Math.round((float)(ApoRelaxLevelGeneration.recs[rec][i].getX() * (float)iLevel.getWidth()) / 100f);
			int y = (int)Math.round((float)(ApoRelaxLevelGeneration.recs[rec][i].getY() * (float)(iLevel.getHeight())) / 100f);
			int width = (int)Math.round((float)(ApoRelaxLevelGeneration.recs[rec][i].getWidth() * (float)(iLevel.getWidth())) / 100f);
			int height = (int)Math.round((float)(ApoRelaxLevelGeneration.recs[rec][i].getHeight() * (float)(iLevel.getHeight())) / 100f);
			if (x + width > iLevel.getWidth()) {
				width = iLevel.getWidth() - x;
			}
			if (y + height > iLevel.getHeight()) {
				height = iLevel.getHeight() - y;
			}
			tiles[i - 1] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, x, y, width, height, i - 1);
		}
		
		ApoRelaxLevel simpleLevel = new ApoRelaxLevel("Random Level", "Click on a tile to select it. Then, click a different piece to swap them.", tiles, iLevel.getWidth(), iLevel.getHeight());
		
		ApoRelaxLevelGeneration.swapLevel(simpleLevel, tiles);
		
		return simpleLevel;
	}
	
	private static final void swapLevel(final ApoRelaxLevel simpleLevel, final ApoRelaxTile[] tiles) {
		boolean bMustSwap = true;
		
		while (bMustSwap) {
			int swaps = (int)(Math.random() * tiles.length * 2) + 3;
			for (int i = 0; i < swaps; i++) {
				int first = (int)(Math.random() * tiles.length);
				if (first >= tiles.length) {
					first = 0;
				}
				int second = (int)(Math.random() * tiles.length);
				while (second == first) {
					second += 1;
				}
				if (second >= tiles.length) {
					second = 0;
				}
				simpleLevel.changeTwoTiles(first, second, false);			
			}
			for (int i = 0; i < tiles.length; i++) {
				if (tiles[i].getCurNumber() != tiles[i].getNumber()) {
					bMustSwap = false;
				}
			}
		}
	}
	
	private static final void makeRecs() {
		ApoRelaxLevelGeneration.recs = new ApoRelaxLevelGenerationRec[22][100];
		
		ApoRelaxLevelGeneration.recs[0][0] = new ApoRelaxLevelGenerationRec(4, 0, 0, 0);
		ApoRelaxLevelGeneration.recs[0][1] = new ApoRelaxLevelGenerationRec(0, 0, 50, 50);
		ApoRelaxLevelGeneration.recs[0][2] = new ApoRelaxLevelGenerationRec(0, 50, 50, 50);
		ApoRelaxLevelGeneration.recs[0][3] = new ApoRelaxLevelGenerationRec(50, 0, 50, 50);
		ApoRelaxLevelGeneration.recs[0][4] = new ApoRelaxLevelGenerationRec(50, 50, 50, 50);
		
		ApoRelaxLevelGeneration.recs[1][0] = new ApoRelaxLevelGenerationRec(3, 0, 0, 0);
		ApoRelaxLevelGeneration.recs[1][1] = new ApoRelaxLevelGenerationRec(0, 0, 50, 50);
		ApoRelaxLevelGeneration.recs[1][2] = new ApoRelaxLevelGenerationRec(0, 50, 50, 50);
		ApoRelaxLevelGeneration.recs[1][3] = new ApoRelaxLevelGenerationRec(50, 0, 50, 100);
		
		ApoRelaxLevelGeneration.recs[2][0] = new ApoRelaxLevelGenerationRec(  6,   0,   0,   0);
		ApoRelaxLevelGeneration.recs[2][1] = new ApoRelaxLevelGenerationRec(  0,   0,  33,  50);
		ApoRelaxLevelGeneration.recs[2][2] = new ApoRelaxLevelGenerationRec(  0,  50,  33,  50);
		ApoRelaxLevelGeneration.recs[2][3] = new ApoRelaxLevelGenerationRec( 33,   0,  33,  33);
		ApoRelaxLevelGeneration.recs[2][4] = new ApoRelaxLevelGenerationRec( 33,  33,  33,  67);
		ApoRelaxLevelGeneration.recs[2][5] = new ApoRelaxLevelGenerationRec( 66,   0,  34,  25);
		ApoRelaxLevelGeneration.recs[2][6] = new ApoRelaxLevelGenerationRec( 66,  25,  34,  75);
		
		ApoRelaxLevelGeneration.recs[3][0] = new ApoRelaxLevelGenerationRec(11, 0, 0, 0);
		ApoRelaxLevelGeneration.recs[3][1] = new ApoRelaxLevelGenerationRec(0, 0, 19, 31);
		ApoRelaxLevelGeneration.recs[3][2] = new ApoRelaxLevelGenerationRec(19, 0, 10, 44);
		ApoRelaxLevelGeneration.recs[3][3] = new ApoRelaxLevelGenerationRec(29, 0, 33, 36);
		ApoRelaxLevelGeneration.recs[3][4] = new ApoRelaxLevelGenerationRec(62, 0, 38, 21);
		ApoRelaxLevelGeneration.recs[3][5] = new ApoRelaxLevelGenerationRec(83, 21, 16, 42);
		ApoRelaxLevelGeneration.recs[3][6] = new ApoRelaxLevelGenerationRec(62, 21, 22, 42);
		ApoRelaxLevelGeneration.recs[3][7] = new ApoRelaxLevelGenerationRec(62, 62, 38, 38);
		ApoRelaxLevelGeneration.recs[3][8] = new ApoRelaxLevelGenerationRec(29, 36, 33, 27);
		ApoRelaxLevelGeneration.recs[3][9] = new ApoRelaxLevelGenerationRec(29, 62, 33, 38);
		ApoRelaxLevelGeneration.recs[3][10] = new ApoRelaxLevelGenerationRec(0, 44, 29, 55);
		ApoRelaxLevelGeneration.recs[3][11] = new ApoRelaxLevelGenerationRec(0, 31, 19, 14);
		
		ApoRelaxLevelGeneration.recs[4][0] = new ApoRelaxLevelGenerationRec(10, 0, 0, 0);
		ApoRelaxLevelGeneration.recs[4][1] = new ApoRelaxLevelGenerationRec(48, 73, 51, 26);
		ApoRelaxLevelGeneration.recs[4][2] = new ApoRelaxLevelGenerationRec(66, 39, 34, 34);
		ApoRelaxLevelGeneration.recs[4][3] = new ApoRelaxLevelGenerationRec(48, 0, 19, 73);
		ApoRelaxLevelGeneration.recs[4][4] = new ApoRelaxLevelGenerationRec(66, 0, 34, 23);
		ApoRelaxLevelGeneration.recs[4][5] = new ApoRelaxLevelGenerationRec(66, 23, 34, 16);
		ApoRelaxLevelGeneration.recs[4][6] = new ApoRelaxLevelGenerationRec(38, 0, 10, 100);
		ApoRelaxLevelGeneration.recs[4][7] = new ApoRelaxLevelGenerationRec(0, 48, 38, 10);
		ApoRelaxLevelGeneration.recs[4][8] = new ApoRelaxLevelGenerationRec(0, 0, 38, 48);
		ApoRelaxLevelGeneration.recs[4][9] = new ApoRelaxLevelGenerationRec(0, 58, 19, 41);
		ApoRelaxLevelGeneration.recs[4][10] = new ApoRelaxLevelGenerationRec(19, 58, 19, 41);
		
		ApoRelaxLevelGeneration.recs[5][0] = new ApoRelaxLevelGenerationRec(8, 0, 0, 0);
		ApoRelaxLevelGeneration.recs[5][1] = new ApoRelaxLevelGenerationRec(70, 0, 30, 55);
		ApoRelaxLevelGeneration.recs[5][2] = new ApoRelaxLevelGenerationRec(70, 55, 30, 45);
		ApoRelaxLevelGeneration.recs[5][3] = new ApoRelaxLevelGenerationRec(41, 78, 30, 22);
		ApoRelaxLevelGeneration.recs[5][4] = new ApoRelaxLevelGenerationRec(41, 0, 30, 78);
		ApoRelaxLevelGeneration.recs[5][5] = new ApoRelaxLevelGenerationRec(0, 0, 41, 15);
		ApoRelaxLevelGeneration.recs[5][6] = new ApoRelaxLevelGenerationRec(0, 15, 41, 31);
		ApoRelaxLevelGeneration.recs[5][7] = new ApoRelaxLevelGenerationRec(0, 45, 19, 55);
		ApoRelaxLevelGeneration.recs[5][8] = new ApoRelaxLevelGenerationRec(19, 45, 22, 55);
		
		ApoRelaxLevelGeneration.recs[6][0] = new ApoRelaxLevelGenerationRec(11, 0, 0, 0);
		ApoRelaxLevelGeneration.recs[6][1] = new ApoRelaxLevelGenerationRec(0, 0, 41, 39);
		ApoRelaxLevelGeneration.recs[6][2] = new ApoRelaxLevelGenerationRec(41, 0, 59, 12);
		ApoRelaxLevelGeneration.recs[6][3] = new ApoRelaxLevelGenerationRec(41, 12, 59, 11);
		ApoRelaxLevelGeneration.recs[6][4] = new ApoRelaxLevelGenerationRec(41, 23, 59, 12);
		ApoRelaxLevelGeneration.recs[6][5] = new ApoRelaxLevelGenerationRec(41, 34, 59, 12);
		ApoRelaxLevelGeneration.recs[6][6] = new ApoRelaxLevelGenerationRec(0, 39, 41, 25);
		ApoRelaxLevelGeneration.recs[6][7] = new ApoRelaxLevelGenerationRec(0, 63, 41, 37);
		ApoRelaxLevelGeneration.recs[6][8] = new ApoRelaxLevelGenerationRec(41, 46, 59, 13);
		ApoRelaxLevelGeneration.recs[6][9] = new ApoRelaxLevelGenerationRec(41, 59, 59, 17);
		ApoRelaxLevelGeneration.recs[6][10] = new ApoRelaxLevelGenerationRec(41, 76, 59, 12);
		ApoRelaxLevelGeneration.recs[6][11] = new ApoRelaxLevelGenerationRec(41, 87, 59, 13);
		
		ApoRelaxLevelGeneration.recs[7][0] = new ApoRelaxLevelGenerationRec(16, 0, 0, 0);
		ApoRelaxLevelGeneration.recs[7][1] = new ApoRelaxLevelGenerationRec(25, 20, 13, 10);
		ApoRelaxLevelGeneration.recs[7][2] = new ApoRelaxLevelGenerationRec(37, 28, 10, 30);
		ApoRelaxLevelGeneration.recs[7][3] = new ApoRelaxLevelGenerationRec(12, 43, 26, 29);
		ApoRelaxLevelGeneration.recs[7][4] = new ApoRelaxLevelGenerationRec(47, 49, 23, 24);
		ApoRelaxLevelGeneration.recs[7][5] = new ApoRelaxLevelGenerationRec(58, 0, 42, 35);
		ApoRelaxLevelGeneration.recs[7][6] = new ApoRelaxLevelGenerationRec(71, 35, 30, 65);
		ApoRelaxLevelGeneration.recs[7][7] = new ApoRelaxLevelGenerationRec(37, 58, 10, 42);
		ApoRelaxLevelGeneration.recs[7][8] = new ApoRelaxLevelGenerationRec(47, 73, 23, 28);
		ApoRelaxLevelGeneration.recs[7][9] = new ApoRelaxLevelGenerationRec(37, 0, 21, 28);
		ApoRelaxLevelGeneration.recs[7][10] = new ApoRelaxLevelGenerationRec(47, 28, 11, 21);
		ApoRelaxLevelGeneration.recs[7][11] = new ApoRelaxLevelGenerationRec(58, 35, 13, 14);
		ApoRelaxLevelGeneration.recs[7][12] = new ApoRelaxLevelGenerationRec(0, 0, 37, 20);
		ApoRelaxLevelGeneration.recs[7][13] = new ApoRelaxLevelGenerationRec(0, 30, 37, 14);
		ApoRelaxLevelGeneration.recs[7][14] = new ApoRelaxLevelGenerationRec(0, 20, 25, 10);
		ApoRelaxLevelGeneration.recs[7][15] = new ApoRelaxLevelGenerationRec(0, 43, 12, 57);
		ApoRelaxLevelGeneration.recs[7][16] = new ApoRelaxLevelGenerationRec(12, 72, 26, 28);
		
		ApoRelaxLevelGeneration.recs[8][0] = new ApoRelaxLevelGenerationRec(15, 0, 0, 0);
		ApoRelaxLevelGeneration.recs[8][1] = new ApoRelaxLevelGenerationRec(10, 0, 32, 26);
		ApoRelaxLevelGeneration.recs[8][2] = new ApoRelaxLevelGenerationRec(0, 0, 10, 41);
		ApoRelaxLevelGeneration.recs[8][3] = new ApoRelaxLevelGenerationRec(10, 26, 24, 59);
		ApoRelaxLevelGeneration.recs[8][4] = new ApoRelaxLevelGenerationRec(0, 41, 10, 59);
		ApoRelaxLevelGeneration.recs[8][5] = new ApoRelaxLevelGenerationRec(34, 63, 26, 37);
		ApoRelaxLevelGeneration.recs[8][6] = new ApoRelaxLevelGenerationRec(10, 85, 24, 15);
		ApoRelaxLevelGeneration.recs[8][7] = new ApoRelaxLevelGenerationRec(34, 26, 11, 36);
		ApoRelaxLevelGeneration.recs[8][8] = new ApoRelaxLevelGenerationRec(45, 26, 15, 19);
		ApoRelaxLevelGeneration.recs[8][9] = new ApoRelaxLevelGenerationRec(42, 0, 18, 26);
		ApoRelaxLevelGeneration.recs[8][10] = new ApoRelaxLevelGenerationRec(59, 0, 41, 26);
		ApoRelaxLevelGeneration.recs[8][11] = new ApoRelaxLevelGenerationRec(70, 26, 30, 33);
		ApoRelaxLevelGeneration.recs[8][12] = new ApoRelaxLevelGenerationRec(59, 26, 11, 74);
		ApoRelaxLevelGeneration.recs[8][13] = new ApoRelaxLevelGenerationRec(45, 45, 15, 17);
		ApoRelaxLevelGeneration.recs[8][14] = new ApoRelaxLevelGenerationRec(70, 59, 10, 41);
		ApoRelaxLevelGeneration.recs[8][15] = new ApoRelaxLevelGenerationRec(80, 59, 20, 41);
		
		ApoRelaxLevelGeneration.recs[9][0] = new ApoRelaxLevelGenerationRec(21, 0, 0, 0);
		ApoRelaxLevelGeneration.recs[9][1] = new ApoRelaxLevelGenerationRec(0, 0, 24, 30);
		ApoRelaxLevelGeneration.recs[9][2] = new ApoRelaxLevelGenerationRec(24, 0, 21, 39);
		ApoRelaxLevelGeneration.recs[9][3] = new ApoRelaxLevelGenerationRec(45, 0, 55, 13);
		ApoRelaxLevelGeneration.recs[9][4] = new ApoRelaxLevelGenerationRec(45, 13, 10, 26);
		ApoRelaxLevelGeneration.recs[9][5] = new ApoRelaxLevelGenerationRec(38, 39, 17, 17);
		ApoRelaxLevelGeneration.recs[9][6] = new ApoRelaxLevelGenerationRec(0, 30, 24, 13);
		ApoRelaxLevelGeneration.recs[9][7] = new ApoRelaxLevelGenerationRec(0, 43, 10, 26);
		ApoRelaxLevelGeneration.recs[9][8] = new ApoRelaxLevelGenerationRec(0, 69, 10, 31);
		ApoRelaxLevelGeneration.recs[9][9] = new ApoRelaxLevelGenerationRec(10, 87, 51, 13);
		ApoRelaxLevelGeneration.recs[9][10] = new ApoRelaxLevelGenerationRec(61, 87, 39, 13);
		ApoRelaxLevelGeneration.recs[9][11] = new ApoRelaxLevelGenerationRec(90, 13, 10, 43);
		ApoRelaxLevelGeneration.recs[9][12] = new ApoRelaxLevelGenerationRec(82, 56, 18, 31);
		ApoRelaxLevelGeneration.recs[9][13] = new ApoRelaxLevelGenerationRec(61, 56, 21, 31);
		ApoRelaxLevelGeneration.recs[9][14] = new ApoRelaxLevelGenerationRec(68, 35, 22, 21);
		ApoRelaxLevelGeneration.recs[9][15] = new ApoRelaxLevelGenerationRec(55, 35, 13, 21);
		ApoRelaxLevelGeneration.recs[9][16] = new ApoRelaxLevelGenerationRec(55, 13, 18, 22);
		ApoRelaxLevelGeneration.recs[9][17] = new ApoRelaxLevelGenerationRec(73, 13, 17, 22);
		ApoRelaxLevelGeneration.recs[9][18] = new ApoRelaxLevelGenerationRec(24, 39, 14, 20);
		ApoRelaxLevelGeneration.recs[9][19] = new ApoRelaxLevelGenerationRec(10, 43, 14, 43);
		ApoRelaxLevelGeneration.recs[9][20] = new ApoRelaxLevelGenerationRec(24, 59, 15, 28);
		ApoRelaxLevelGeneration.recs[9][21] = new ApoRelaxLevelGenerationRec(38, 56, 23, 31);
		
		ApoRelaxLevelGeneration.recs[10][0] = new ApoRelaxLevelGenerationRec(19, 0, 0, 0);
		ApoRelaxLevelGeneration.recs[10][1] = new ApoRelaxLevelGenerationRec(0, 79, 29, 21);
		ApoRelaxLevelGeneration.recs[10][2] = new ApoRelaxLevelGenerationRec(29, 79, 27, 21);
		ApoRelaxLevelGeneration.recs[10][3] = new ApoRelaxLevelGenerationRec(22, 44, 20, 35);
		ApoRelaxLevelGeneration.recs[10][4] = new ApoRelaxLevelGenerationRec(42, 44, 24, 35);
		ApoRelaxLevelGeneration.recs[10][5] = new ApoRelaxLevelGenerationRec(56, 79, 28, 21);
		ApoRelaxLevelGeneration.recs[10][6] = new ApoRelaxLevelGenerationRec(84, 64, 16, 36);
		ApoRelaxLevelGeneration.recs[10][7] = new ApoRelaxLevelGenerationRec(90, 18, 10, 46);
		ApoRelaxLevelGeneration.recs[10][8] = new ApoRelaxLevelGenerationRec(71, 0, 29, 18);
		ApoRelaxLevelGeneration.recs[10][9] = new ApoRelaxLevelGenerationRec(39, 0, 32, 18);
		ApoRelaxLevelGeneration.recs[10][10] = new ApoRelaxLevelGenerationRec(22, 0, 17, 44);
		ApoRelaxLevelGeneration.recs[10][11] = new ApoRelaxLevelGenerationRec(12, 0, 10, 30);
		ApoRelaxLevelGeneration.recs[10][12] = new ApoRelaxLevelGenerationRec(0, 0, 12, 27);
		ApoRelaxLevelGeneration.recs[10][13] = new ApoRelaxLevelGenerationRec(0, 27, 12, 28);
		ApoRelaxLevelGeneration.recs[10][14] = new ApoRelaxLevelGenerationRec(12, 30, 10, 25);
		ApoRelaxLevelGeneration.recs[10][15] = new ApoRelaxLevelGenerationRec(0, 55, 22, 24);
		ApoRelaxLevelGeneration.recs[10][16] = new ApoRelaxLevelGenerationRec(66, 64, 18, 15);
		ApoRelaxLevelGeneration.recs[10][17] = new ApoRelaxLevelGenerationRec(66, 44, 24, 20);
		ApoRelaxLevelGeneration.recs[10][18] = new ApoRelaxLevelGenerationRec(66, 18, 24, 26);
		ApoRelaxLevelGeneration.recs[10][19] = new ApoRelaxLevelGenerationRec(39, 18, 27, 26);
		
		ApoRelaxLevelGeneration.recs[11][0] = new ApoRelaxLevelGenerationRec(8, 0, 0, 0);
		ApoRelaxLevelGeneration.recs[11][1] = new ApoRelaxLevelGenerationRec(0, 0, 40, 34);
		ApoRelaxLevelGeneration.recs[11][2] = new ApoRelaxLevelGenerationRec(0, 34, 64, 26);
		ApoRelaxLevelGeneration.recs[11][3] = new ApoRelaxLevelGenerationRec(40, 0, 60, 34);
		ApoRelaxLevelGeneration.recs[11][4] = new ApoRelaxLevelGenerationRec(64, 34, 36, 14);
		ApoRelaxLevelGeneration.recs[11][5] = new ApoRelaxLevelGenerationRec(64, 47, 36, 12);
		ApoRelaxLevelGeneration.recs[11][6] = new ApoRelaxLevelGenerationRec(31, 60, 33, 41);
		ApoRelaxLevelGeneration.recs[11][7] = new ApoRelaxLevelGenerationRec(64, 60, 36, 41);
		ApoRelaxLevelGeneration.recs[11][8] = new ApoRelaxLevelGenerationRec(0, 60, 31, 41);
		
		ApoRelaxLevelGeneration.recs[12][0] = new ApoRelaxLevelGenerationRec(12, 0, 0, 0);
		ApoRelaxLevelGeneration.recs[12][1] = new ApoRelaxLevelGenerationRec(40, 37, 21, 23);
		ApoRelaxLevelGeneration.recs[12][2] = new ApoRelaxLevelGenerationRec(19, 17, 56, 20);
		ApoRelaxLevelGeneration.recs[12][3] = new ApoRelaxLevelGenerationRec(0, 0, 48, 17);
		ApoRelaxLevelGeneration.recs[12][4] = new ApoRelaxLevelGenerationRec(48, 0, 52, 17);
		ApoRelaxLevelGeneration.recs[12][5] = new ApoRelaxLevelGenerationRec(76, 17, 25, 30);
		ApoRelaxLevelGeneration.recs[12][6] = new ApoRelaxLevelGenerationRec(0, 82, 48, 18);
		ApoRelaxLevelGeneration.recs[12][7] = new ApoRelaxLevelGenerationRec(48, 82, 53, 18);
		ApoRelaxLevelGeneration.recs[12][8] = new ApoRelaxLevelGenerationRec(19, 60, 56, 22);
		ApoRelaxLevelGeneration.recs[12][9] = new ApoRelaxLevelGenerationRec(76, 47, 25, 35);
		ApoRelaxLevelGeneration.recs[12][10] = new ApoRelaxLevelGenerationRec(61, 37, 15, 23);
		ApoRelaxLevelGeneration.recs[12][11] = new ApoRelaxLevelGenerationRec(0, 17, 19, 65);
		ApoRelaxLevelGeneration.recs[12][12] = new ApoRelaxLevelGenerationRec(19, 37, 20, 23);
		
		ApoRelaxLevelGeneration.recs[13][0] = new ApoRelaxLevelGenerationRec(16, 0, 0, 0);
		ApoRelaxLevelGeneration.recs[13][1] = new ApoRelaxLevelGenerationRec(27, 27, 22, 23);
		ApoRelaxLevelGeneration.recs[13][2] = new ApoRelaxLevelGenerationRec(27, 0, 22, 27);
		ApoRelaxLevelGeneration.recs[13][3] = new ApoRelaxLevelGenerationRec(0, 0, 27, 27);
		ApoRelaxLevelGeneration.recs[13][4] = new ApoRelaxLevelGenerationRec(0, 27, 27, 23);
		ApoRelaxLevelGeneration.recs[13][5] = new ApoRelaxLevelGenerationRec(49, 0, 22, 27);
		ApoRelaxLevelGeneration.recs[13][6] = new ApoRelaxLevelGenerationRec(71, 0, 30, 27);
		ApoRelaxLevelGeneration.recs[13][7] = new ApoRelaxLevelGenerationRec(71, 27, 30, 23);
		ApoRelaxLevelGeneration.recs[13][8] = new ApoRelaxLevelGenerationRec(49, 27, 22, 23);
		ApoRelaxLevelGeneration.recs[13][9] = new ApoRelaxLevelGenerationRec(0, 50, 27, 20);
		ApoRelaxLevelGeneration.recs[13][10] = new ApoRelaxLevelGenerationRec(27, 50, 22, 20);
		ApoRelaxLevelGeneration.recs[13][11] = new ApoRelaxLevelGenerationRec(49, 50, 22, 20);
		ApoRelaxLevelGeneration.recs[13][12] = new ApoRelaxLevelGenerationRec(71, 50, 30, 20);
		ApoRelaxLevelGeneration.recs[13][13] = new ApoRelaxLevelGenerationRec(0, 70, 27, 30);
		ApoRelaxLevelGeneration.recs[13][14] = new ApoRelaxLevelGenerationRec(27, 70, 22, 30);
		ApoRelaxLevelGeneration.recs[13][15] = new ApoRelaxLevelGenerationRec(49, 70, 22, 30);
		ApoRelaxLevelGeneration.recs[13][16] = new ApoRelaxLevelGenerationRec(71, 70, 30, 30);
		
		ApoRelaxLevelGeneration.recs[14][0] = new ApoRelaxLevelGenerationRec(29, 0, 0, 0);
		ApoRelaxLevelGeneration.recs[14][1] = new ApoRelaxLevelGenerationRec(0, 0, 14, 27);
		ApoRelaxLevelGeneration.recs[14][2] = new ApoRelaxLevelGenerationRec(14, 0, 11, 14);
		ApoRelaxLevelGeneration.recs[14][3] = new ApoRelaxLevelGenerationRec(14, 14, 11, 14);
		ApoRelaxLevelGeneration.recs[14][4] = new ApoRelaxLevelGenerationRec(42, 27, 17, 19);
		ApoRelaxLevelGeneration.recs[14][5] = new ApoRelaxLevelGenerationRec(58, 27, 26, 19);
		ApoRelaxLevelGeneration.recs[14][6] = new ApoRelaxLevelGenerationRec(70, 46, 15, 14);
		ApoRelaxLevelGeneration.recs[14][7] = new ApoRelaxLevelGenerationRec(55, 46, 15, 14);
		ApoRelaxLevelGeneration.recs[14][8] = new ApoRelaxLevelGenerationRec(42, 46, 13, 14);
		ApoRelaxLevelGeneration.recs[14][9] = new ApoRelaxLevelGenerationRec(52, 59, 10, 15);
		ApoRelaxLevelGeneration.recs[14][10] = new ApoRelaxLevelGenerationRec(62, 59, 10, 15);
		ApoRelaxLevelGeneration.recs[14][11] = new ApoRelaxLevelGenerationRec(72, 59, 13, 15);
		ApoRelaxLevelGeneration.recs[14][12] = new ApoRelaxLevelGenerationRec(84, 37, 16, 19);
		ApoRelaxLevelGeneration.recs[14][13] = new ApoRelaxLevelGenerationRec(84, 56, 16, 18);
		ApoRelaxLevelGeneration.recs[14][14] = new ApoRelaxLevelGenerationRec(42, 59, 10, 15);
		ApoRelaxLevelGeneration.recs[14][15] = new ApoRelaxLevelGenerationRec(32, 27, 10, 19);
		ApoRelaxLevelGeneration.recs[14][16] = new ApoRelaxLevelGenerationRec(32, 46, 10, 28);
		ApoRelaxLevelGeneration.recs[14][17] = new ApoRelaxLevelGenerationRec(25, 0, 16, 27);
		ApoRelaxLevelGeneration.recs[14][18] = new ApoRelaxLevelGenerationRec(41, 0, 17, 27);
		ApoRelaxLevelGeneration.recs[14][19] = new ApoRelaxLevelGenerationRec(58, 0, 26, 27);
		ApoRelaxLevelGeneration.recs[14][20] = new ApoRelaxLevelGenerationRec(84, 0, 16, 37);
		ApoRelaxLevelGeneration.recs[14][21] = new ApoRelaxLevelGenerationRec(20, 27, 12, 22);
		ApoRelaxLevelGeneration.recs[14][22] = new ApoRelaxLevelGenerationRec(0, 27, 20, 22);
		ApoRelaxLevelGeneration.recs[14][23] = new ApoRelaxLevelGenerationRec(20, 49, 12, 25);
		ApoRelaxLevelGeneration.recs[14][24] = new ApoRelaxLevelGenerationRec(42, 74, 10, 26);
		ApoRelaxLevelGeneration.recs[14][25] = new ApoRelaxLevelGenerationRec(52, 74, 33, 26);
		ApoRelaxLevelGeneration.recs[14][26] = new ApoRelaxLevelGenerationRec(84, 74, 16, 26);
		ApoRelaxLevelGeneration.recs[14][27] = new ApoRelaxLevelGenerationRec(20, 74, 22, 26);
		ApoRelaxLevelGeneration.recs[14][28] = new ApoRelaxLevelGenerationRec(0, 49, 20, 18);
		ApoRelaxLevelGeneration.recs[14][29] = new ApoRelaxLevelGenerationRec(0, 67, 20, 33);
		
		ApoRelaxLevelGeneration.recs[15][0] = new ApoRelaxLevelGenerationRec(28, 0, 0, 0);
		ApoRelaxLevelGeneration.recs[15][1] = new ApoRelaxLevelGenerationRec(53, 12, 13, 17);
		ApoRelaxLevelGeneration.recs[15][2] = new ApoRelaxLevelGenerationRec(90, 90, 10, 10);
		ApoRelaxLevelGeneration.recs[15][3] = new ApoRelaxLevelGenerationRec(59, 86, 17, 14);
		ApoRelaxLevelGeneration.recs[15][4] = new ApoRelaxLevelGenerationRec(0, 90, 19, 10);
		ApoRelaxLevelGeneration.recs[15][5] = new ApoRelaxLevelGenerationRec(0, 80, 19, 10);
		ApoRelaxLevelGeneration.recs[15][6] = new ApoRelaxLevelGenerationRec(90, 24, 10, 21);
		ApoRelaxLevelGeneration.recs[15][7] = new ApoRelaxLevelGenerationRec(90, 0, 10, 24);
		ApoRelaxLevelGeneration.recs[15][8] = new ApoRelaxLevelGenerationRec(19, 45, 34, 16);
		ApoRelaxLevelGeneration.recs[15][9] = new ApoRelaxLevelGenerationRec(0, 61, 19, 18);
		ApoRelaxLevelGeneration.recs[15][10] = new ApoRelaxLevelGenerationRec(42, 86, 17, 14);
		ApoRelaxLevelGeneration.recs[15][11] = new ApoRelaxLevelGenerationRec(75, 77, 15, 23);
		ApoRelaxLevelGeneration.recs[15][12] = new ApoRelaxLevelGenerationRec(19, 80, 23, 20);
		ApoRelaxLevelGeneration.recs[15][13] = new ApoRelaxLevelGenerationRec(19, 61, 23, 18);
		ApoRelaxLevelGeneration.recs[15][14] = new ApoRelaxLevelGenerationRec(42, 61, 17, 25);
		ApoRelaxLevelGeneration.recs[15][15] = new ApoRelaxLevelGenerationRec(59, 61, 17, 25);
		ApoRelaxLevelGeneration.recs[15][16] = new ApoRelaxLevelGenerationRec(0, 0, 19, 45);
		ApoRelaxLevelGeneration.recs[15][17] = new ApoRelaxLevelGenerationRec(0, 45, 19, 16);
		ApoRelaxLevelGeneration.recs[15][18] = new ApoRelaxLevelGenerationRec(64, 28, 11, 33);
		ApoRelaxLevelGeneration.recs[15][19] = new ApoRelaxLevelGenerationRec(19, 0, 34, 18);
		ApoRelaxLevelGeneration.recs[15][20] = new ApoRelaxLevelGenerationRec(75, 61, 15, 16);
		ApoRelaxLevelGeneration.recs[15][21] = new ApoRelaxLevelGenerationRec(75, 46, 15, 16);
		ApoRelaxLevelGeneration.recs[15][22] = new ApoRelaxLevelGenerationRec(75, 28, 15, 17);
		ApoRelaxLevelGeneration.recs[15][23] = new ApoRelaxLevelGenerationRec(90, 46, 10, 44);
		ApoRelaxLevelGeneration.recs[15][24] = new ApoRelaxLevelGenerationRec(53, 28, 11, 33);
		ApoRelaxLevelGeneration.recs[15][25] = new ApoRelaxLevelGenerationRec(19, 18, 34, 27);
		ApoRelaxLevelGeneration.recs[15][26] = new ApoRelaxLevelGenerationRec(66, 0, 24, 15);
		ApoRelaxLevelGeneration.recs[15][27] = new ApoRelaxLevelGenerationRec(66, 15, 24, 14);
		ApoRelaxLevelGeneration.recs[15][28] = new ApoRelaxLevelGenerationRec(53, 0, 13, 12);
		
		ApoRelaxLevelGeneration.recs[16][0] = new ApoRelaxLevelGenerationRec(8, 0, 0, 0);
		ApoRelaxLevelGeneration.recs[16][1] = new ApoRelaxLevelGenerationRec(43, 0, 20, 63);
		ApoRelaxLevelGeneration.recs[16][2] = new ApoRelaxLevelGenerationRec(63, 0, 37, 23);
		ApoRelaxLevelGeneration.recs[16][3] = new ApoRelaxLevelGenerationRec(83, 23, 17, 77);
		ApoRelaxLevelGeneration.recs[16][4] = new ApoRelaxLevelGenerationRec(63, 23, 20, 77);
		ApoRelaxLevelGeneration.recs[16][5] = new ApoRelaxLevelGenerationRec(0, 63, 63, 38);
		ApoRelaxLevelGeneration.recs[16][6] = new ApoRelaxLevelGenerationRec(0, 43, 43, 20);
		ApoRelaxLevelGeneration.recs[16][7] = new ApoRelaxLevelGenerationRec(19, 0, 24, 43);
		ApoRelaxLevelGeneration.recs[16][8] = new ApoRelaxLevelGenerationRec(0, 0, 19, 43);
		
		ApoRelaxLevelGeneration.recs[17][0] = new ApoRelaxLevelGenerationRec(6, 0, 0, 0);
		ApoRelaxLevelGeneration.recs[17][1] = new ApoRelaxLevelGenerationRec(27, 27, 44, 27);
		ApoRelaxLevelGeneration.recs[17][2] = new ApoRelaxLevelGenerationRec(27, 53, 24, 47);
		ApoRelaxLevelGeneration.recs[17][3] = new ApoRelaxLevelGenerationRec(51, 53, 49, 47);
		ApoRelaxLevelGeneration.recs[17][4] = new ApoRelaxLevelGenerationRec(71, 0, 29, 53);
		ApoRelaxLevelGeneration.recs[17][5] = new ApoRelaxLevelGenerationRec(0, 27, 27, 74);
		ApoRelaxLevelGeneration.recs[17][6] = new ApoRelaxLevelGenerationRec(0, 0, 71, 27);
		
		ApoRelaxLevelGeneration.recs[18][0] = new ApoRelaxLevelGenerationRec(10, 0, 0, 0);
		ApoRelaxLevelGeneration.recs[18][1] = new ApoRelaxLevelGenerationRec(0, 32, 68, 10);
		ApoRelaxLevelGeneration.recs[18][2] = new ApoRelaxLevelGenerationRec(0, 0, 30, 32);
		ApoRelaxLevelGeneration.recs[18][3] = new ApoRelaxLevelGenerationRec(30, 0, 70, 14);
		ApoRelaxLevelGeneration.recs[18][4] = new ApoRelaxLevelGenerationRec(68, 14, 32, 61);
		ApoRelaxLevelGeneration.recs[18][5] = new ApoRelaxLevelGenerationRec(30, 14, 38, 18);
		ApoRelaxLevelGeneration.recs[18][6] = new ApoRelaxLevelGenerationRec(44, 42, 25, 33);
		ApoRelaxLevelGeneration.recs[18][7] = new ApoRelaxLevelGenerationRec(44, 75, 57, 25);
		ApoRelaxLevelGeneration.recs[18][8] = new ApoRelaxLevelGenerationRec(19, 42, 24, 59);
		ApoRelaxLevelGeneration.recs[18][9] = new ApoRelaxLevelGenerationRec(0, 79, 19, 22);
		ApoRelaxLevelGeneration.recs[18][10] = new ApoRelaxLevelGenerationRec(0, 42, 19, 37);
		
		ApoRelaxLevelGeneration.recs[19][0] = new ApoRelaxLevelGenerationRec(14, 0, 0, 0);
		ApoRelaxLevelGeneration.recs[19][1] = new ApoRelaxLevelGenerationRec(28, 31, 19, 19);
		ApoRelaxLevelGeneration.recs[19][2] = new ApoRelaxLevelGenerationRec(47, 24, 19, 42);
		ApoRelaxLevelGeneration.recs[19][3] = new ApoRelaxLevelGenerationRec(12, 50, 34, 31);
		ApoRelaxLevelGeneration.recs[19][4] = new ApoRelaxLevelGenerationRec(47, 66, 26, 34);
		ApoRelaxLevelGeneration.recs[19][5] = new ApoRelaxLevelGenerationRec(72, 66, 28, 34);
		ApoRelaxLevelGeneration.recs[19][6] = new ApoRelaxLevelGenerationRec(65, 11, 24, 55);
		ApoRelaxLevelGeneration.recs[19][7] = new ApoRelaxLevelGenerationRec(89, 0, 11, 66);
		ApoRelaxLevelGeneration.recs[19][8] = new ApoRelaxLevelGenerationRec(23, 0, 67, 11);
		ApoRelaxLevelGeneration.recs[19][9] = new ApoRelaxLevelGenerationRec(47, 11, 19, 14);
		ApoRelaxLevelGeneration.recs[19][10] = new ApoRelaxLevelGenerationRec(12, 11, 34, 20);
		ApoRelaxLevelGeneration.recs[19][11] = new ApoRelaxLevelGenerationRec(0, 0, 23, 11);
		ApoRelaxLevelGeneration.recs[19][12] = new ApoRelaxLevelGenerationRec(0, 11, 12, 89);
		ApoRelaxLevelGeneration.recs[19][13] = new ApoRelaxLevelGenerationRec(12, 31, 15, 19);
		ApoRelaxLevelGeneration.recs[19][14] = new ApoRelaxLevelGenerationRec(12, 81, 34, 19);
		
		ApoRelaxLevelGeneration.recs[20][0] = new ApoRelaxLevelGenerationRec(5, 0, 0, 0);
		ApoRelaxLevelGeneration.recs[20][1] = new ApoRelaxLevelGenerationRec(70, 0, 30, 66);
		ApoRelaxLevelGeneration.recs[20][2] = new ApoRelaxLevelGenerationRec(34, 66, 66, 34);
		ApoRelaxLevelGeneration.recs[20][3] = new ApoRelaxLevelGenerationRec(0, 29, 34, 71);
		ApoRelaxLevelGeneration.recs[20][4] = new ApoRelaxLevelGenerationRec(0, 0, 70, 29);
		ApoRelaxLevelGeneration.recs[20][5] = new ApoRelaxLevelGenerationRec(34, 29, 36, 37);
		
		ApoRelaxLevelGeneration.recs[21][0] = new ApoRelaxLevelGenerationRec(8, 0, 0, 0);
		ApoRelaxLevelGeneration.recs[21][1] = new ApoRelaxLevelGenerationRec(36, 0, 15, 100);
		ApoRelaxLevelGeneration.recs[21][2] = new ApoRelaxLevelGenerationRec(50, 39, 50, 22);
		ApoRelaxLevelGeneration.recs[21][3] = new ApoRelaxLevelGenerationRec(77, 0, 23, 39);
		ApoRelaxLevelGeneration.recs[21][4] = new ApoRelaxLevelGenerationRec(50, 0, 27, 39);
		ApoRelaxLevelGeneration.recs[21][5] = new ApoRelaxLevelGenerationRec(50, 60, 50, 40);
		ApoRelaxLevelGeneration.recs[21][6] = new ApoRelaxLevelGenerationRec(19, 38, 16, 63);
		ApoRelaxLevelGeneration.recs[21][7] = new ApoRelaxLevelGenerationRec(1, 0, 35, 38);
		ApoRelaxLevelGeneration.recs[21][8] = new ApoRelaxLevelGenerationRec(0, 38, 19, 63);

	}
	
	public static final ApoRelaxTile getRelaxTile(BufferedImage iLevel, int x, int y, int width, int height, int number) {
		return new ApoRelaxTile(ApoRelaxLevelGeneration.getSubImage(iLevel, x, y, width, height), x, y, width, height, number);
	}
	
	private static final BufferedImage getSubImage(BufferedImage iOriginal, int x, int y, int width, int height) {
		BufferedImage iNew = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = iNew.createGraphics();
		g.drawImage(iOriginal.getSubimage(x, y, width, height), 0, 0, null);
		g.dispose();
		return iNew;
	}

	public static final BufferedImage getImage(BufferedImage iImage) {
		int width = iImage.getWidth();
		int height = iImage.getHeight();
		float dif = (float)(width) / (float)(height);
		if ((width > ApoRelaxLevel.MAX) || (height > ApoRelaxLevel.MAX)) {
			if (width > height) {
				width = ApoRelaxLevel.MAX;
				height = (int)(width / dif);
			} else {
				height = ApoRelaxLevel.MAX;
				width = (int)(height * dif);
			}
			BufferedImage iNew = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = iNew.createGraphics();
			g.drawImage(iImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH), 0, 0, null);
			g.dispose();
			return iNew;
		} else {
			return iImage;
		}
	}
	
	public static final BufferedImage getImage(String path, boolean bFile) {
		BufferedImage iImage = images.getImage(path, bFile);
		return ApoRelaxLevelGeneration.getImage(iImage);
	}
	
	private static final ApoRelaxLevel getFirstLevel() {
		BufferedImage iLevel = ApoRelaxLevelGeneration.imageArray[0];
		
		ApoRelaxTile[] tiles = new ApoRelaxTile[4];
		tiles[0] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 0, iLevel.getWidth()/2, iLevel.getHeight()/2, 0);
		tiles[1] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, iLevel.getHeight()/2, iLevel.getWidth()/2, iLevel.getHeight()/2, 1);
		tiles[2] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, iLevel.getWidth()/2, 0, iLevel.getWidth()/2, iLevel.getHeight()/2, 2);
		tiles[3] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, iLevel.getWidth()/2, iLevel.getHeight()/2, iLevel.getWidth()/2, iLevel.getHeight()/2, 3);
		
		ApoRelaxLevel simpleLevel = new ApoRelaxLevel("Simple start", "Click on a tile to select it. Then, click a different piece to swap them.", tiles, iLevel.getWidth(), iLevel.getHeight());
		
		simpleLevel.changeTwoTiles(0, 3, false);
		simpleLevel.changeTwoTiles(2, 1, false);
		
		return simpleLevel;
	}
	
	private static final ApoRelaxLevel getSecondLevel() {
		BufferedImage iLevel = ApoRelaxLevelGeneration.imageArray[1];
		
		ApoRelaxTile[] tiles = new ApoRelaxTile[3];
		tiles[0] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 0, iLevel.getWidth()/2, iLevel.getHeight()/2, 0);
		tiles[1] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, iLevel.getHeight()/2, iLevel.getWidth()/2, iLevel.getHeight()/2, 1);
		tiles[2] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, iLevel.getWidth()/2, 0, iLevel.getWidth()/2, iLevel.getHeight(), 2);
		
		ApoRelaxLevel simpleLevel = new ApoRelaxLevel("Broadway", "Pieces will stretch or shrink to fit their container", tiles, iLevel.getWidth(), iLevel.getHeight());
		
		simpleLevel.changeTwoTiles(0, 1, false);
		simpleLevel.changeTwoTiles(2, 1, false);
		
		return simpleLevel;
	}
	
	private static final ApoRelaxLevel getThirdLevel() {
		BufferedImage iLevel = ApoRelaxLevelGeneration.imageArray[2];
		
		ApoRelaxTile[] tiles = new ApoRelaxTile[8];
		tiles[0] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 0, 216, 135, 0);
		tiles[1] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 216, 0, 136, 216, 1);
		tiles[2] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 352, 0, 48, 114, 2);
		tiles[3] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 352, 114, 48, 186, 3);
		tiles[4] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 216, 300, 184, 100, 4);
		tiles[5] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 216, 216, 136, 84, 5);
		tiles[6] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 135, 216, 79, 6);
		tiles[7] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 214, 216, 186, 7);
		
		ApoRelaxLevel simpleLevel = new ApoRelaxLevel("Puzzle", "Pieces will stretch or shrink to fit their container", tiles, iLevel.getWidth(), iLevel.getHeight());
		
		simpleLevel.changeTwoTiles(0, 2, false);
		simpleLevel.changeTwoTiles(6, 7, false);
		simpleLevel.changeTwoTiles(1, 5, false);
		simpleLevel.changeTwoTiles(3, 4, false);
		
		return simpleLevel;
	}
	
	private static final ApoRelaxLevel getFourthLevel() {
		BufferedImage iLevel = ApoRelaxLevelGeneration.imageArray[3];
		
		ApoRelaxTile[] tiles = new ApoRelaxTile[6];
		tiles[0] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 0, iLevel.getWidth()/3, iLevel.getHeight()/2, 0);
		tiles[1] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, iLevel.getHeight()/2, iLevel.getWidth()/3, iLevel.getHeight()/2, 1);
		tiles[2] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 1 * iLevel.getWidth()/3, 0, iLevel.getWidth()/3, iLevel.getHeight()/3, 2);
		tiles[3] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 1 * iLevel.getWidth()/3, iLevel.getHeight()/3, iLevel.getWidth()/3, iLevel.getHeight()*2/3, 3);
		tiles[4] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 2 * iLevel.getWidth()/3, 0, iLevel.getWidth()/3, iLevel.getHeight()*3/4, 4);
		tiles[5] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 2 * iLevel.getWidth()/3, iLevel.getHeight()*3/4, iLevel.getWidth()/3, iLevel.getHeight()/4, 5);
		
		ApoRelaxLevel simpleLevel = new ApoRelaxLevel("Market", "Some fresh fruit please!", tiles, iLevel.getWidth(), iLevel.getHeight());
		
		simpleLevel.changeTwoTiles(0, 5, false);
		simpleLevel.changeTwoTiles(4, 2, false);
		simpleLevel.changeTwoTiles(1, 3, false);
		simpleLevel.changeTwoTiles(4, 0, false);
		simpleLevel.changeTwoTiles(1, 2, false);
		
		return simpleLevel;
	}
	
	private static final ApoRelaxLevel getFifthLevel() {
		BufferedImage iLevel = ApoRelaxLevelGeneration.imageArray[4];
		
		ApoRelaxTile[] tiles = new ApoRelaxTile[7];
		tiles[0] = ApoRelaxLevelGeneration.getRelaxTile(iLevel,                       0,                     0,   iLevel.getWidth()/2, iLevel.getHeight()/2, 0);
		tiles[1] = ApoRelaxLevelGeneration.getRelaxTile(iLevel,                       0,    iLevel.getHeight()/2,   iLevel.getWidth()/4, iLevel.getHeight()/4, 1);
		tiles[2] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 1 * iLevel.getWidth()/4,    iLevel.getHeight()/2,   iLevel.getWidth()/4, iLevel.getHeight()/4, 2);
		tiles[3] = ApoRelaxLevelGeneration.getRelaxTile(iLevel,                       0,    iLevel.getHeight()*3/4, iLevel.getWidth()/2, iLevel.getHeight()/4, 3);
		tiles[4] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 1 * iLevel.getWidth()/2,                       0, iLevel.getWidth()/4, iLevel.getHeight(), 4);
		tiles[5] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 1 * iLevel.getWidth()*3/4,  iLevel.getHeight()/2, iLevel.getWidth()/4, iLevel.getHeight()/2, 5);
		tiles[6] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 1 * iLevel.getWidth()*3/4,                     0, iLevel.getWidth()/4, iLevel.getHeight()/2, 6);
		
		ApoRelaxLevel simpleLevel = new ApoRelaxLevel("Donkey", "I Ahh I Ahh", tiles, iLevel.getWidth(), iLevel.getHeight());
		
		simpleLevel.changeTwoTiles(0, 2, false);
		simpleLevel.changeTwoTiles(1, 6, false);
		simpleLevel.changeTwoTiles(3, 5, false);
		simpleLevel.changeTwoTiles(4, 0, false);
		simpleLevel.changeTwoTiles(1, 0, false);
		simpleLevel.changeTwoTiles(2, 4, false);
		
		return simpleLevel;
	}
	
	private static final ApoRelaxLevel getSixLevel() {
		BufferedImage iLevel = ApoRelaxLevelGeneration.imageArray[5];
		
		ApoRelaxTile[] tiles = new ApoRelaxTile[13];
		tiles[0] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 46, 0, 129, 92, 0);
		tiles[1] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 0, 46, 92, 1);
		tiles[2] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 244, 0, 111, 68, 2);
		tiles[3] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 355, 0, 45, 135, 3);
		tiles[4] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 175, 0, 69, 68, 4);
		tiles[5] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 291, 135, 109, 129, 5);
		tiles[6] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 282, 68, 73, 67, 6);
		tiles[7] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 175, 68, 107, 67, 7);
		tiles[8] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 92, 122, 114, 8);
		tiles[9] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 122, 92, 53, 81, 9);
		tiles[10] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 175, 135, 116, 130, 10);
		tiles[11] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 122, 173, 53, 92, 11);
		tiles[12] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 206, 122, 59, 12);
		
		ApoRelaxLevel simpleLevel = new ApoRelaxLevel("Flowers", "", tiles, iLevel.getWidth(), iLevel.getHeight());
		
		simpleLevel.changeTwoTiles( 1,  3, false);
		simpleLevel.changeTwoTiles( 8,  6, false);
		simpleLevel.changeTwoTiles(12, 11, false);
		simpleLevel.changeTwoTiles( 0,  4, false);
		simpleLevel.changeTwoTiles( 2, 10, false);
		simpleLevel.changeTwoTiles( 5,  7, false);
		simpleLevel.changeTwoTiles( 9, 11, false);
		
		return simpleLevel;
	}
	
	private static final ApoRelaxLevel getSevenLevel() {
		BufferedImage iLevel = ApoRelaxLevelGeneration.imageArray[6];
		
		ApoRelaxTile[] tiles = new ApoRelaxTile[17];
		tiles[0] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 0, 113, 66, 0);
		tiles[1] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 66, 90, 40, 1);
		tiles[2] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 90, 66, 75, 85, 2);
		tiles[3] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 113, 0, 40, 66, 3);
		tiles[4] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 153, 0, 113, 66, 4);
		tiles[5] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 165, 66, 40, 130, 5);
		tiles[6] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 205, 66, 61, 78, 6);
		tiles[7] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 106, 90, 102, 7);
		tiles[8] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 90, 151, 75, 142, 8);
		tiles[9] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 66, 293, 122, 40, 9);
		tiles[10] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 165, 196, 40, 97, 10);
		tiles[11] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 205, 144, 61, 149, 11);
		tiles[12] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 188, 293, 78, 107, 12);
		tiles[13] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 66, 333, 122, 67, 13);
		tiles[14] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 208, 90, 85, 14);
		tiles[15] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 293, 66, 58, 15);
		tiles[16] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 351, 66, 49, 16);
		
		ApoRelaxLevel simpleLevel = new ApoRelaxLevel("Monastery", "", tiles, iLevel.getWidth(), iLevel.getHeight());
		
		simpleLevel.changeTwoTiles( 0, 15, false);
		simpleLevel.changeTwoTiles(12, 14, false);
		simpleLevel.changeTwoTiles(11,  1, false);
		simpleLevel.changeTwoTiles( 4,  2, false);
		simpleLevel.changeTwoTiles( 3, 16, false);
		simpleLevel.changeTwoTiles(13,  5, false);
		simpleLevel.changeTwoTiles( 8, 10, false);
		simpleLevel.changeTwoTiles( 6,  9, false);
		simpleLevel.changeTwoTiles( 7, 15, false);
		
		return simpleLevel;
	}
	
	private static final ApoRelaxLevel getEightLevel() {
		BufferedImage iLevel = ApoRelaxLevelGeneration.imageArray[7];
		
		ApoRelaxTile[] tiles = new ApoRelaxTile[15];
		tiles[0] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 40, 0, 127, 70, 0);
		tiles[1] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 0, 40, 108, 1);
		tiles[2] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 40, 70, 94, 156, 2);
		tiles[3] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 108, 40, 158, 3);
		tiles[4] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 134, 167, 103, 99, 4);
		tiles[5] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 40, 226, 94, 40, 5);
		tiles[6] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 134, 70, 44, 97, 6);
		tiles[7] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 178, 70, 59, 51, 7);
		tiles[8] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 167, 0, 70, 70, 8);
		tiles[9] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 237, 0, 163, 70, 9);
		tiles[10] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 281, 70, 119, 87, 10);
		tiles[11] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 237, 70, 44, 196, 11);
		tiles[12] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 178, 121, 59, 46, 12);
		tiles[13] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 281, 157, 40, 109, 13);
		tiles[14] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 321, 157, 79, 108, 14);
		
		ApoRelaxLevel simpleLevel = new ApoRelaxLevel("Halloween", "", tiles, iLevel.getWidth(), iLevel.getHeight());
		
		simpleLevel.changeTwoTiles( 1, 7, false);
		simpleLevel.changeTwoTiles( 0, 2, false);
		simpleLevel.changeTwoTiles( 3, 4, false);
		simpleLevel.changeTwoTiles( 5, 6, false);
		simpleLevel.changeTwoTiles( 7, 8, false);
		simpleLevel.changeTwoTiles( 9,10, false);
		simpleLevel.changeTwoTiles(11,12, false);
		simpleLevel.changeTwoTiles(13,14, false);
		simpleLevel.changeTwoTiles(0,12, false);
		simpleLevel.changeTwoTiles(6,13, false);
		simpleLevel.changeTwoTiles(4,14, false);
		simpleLevel.changeTwoTiles(5,10, false);
		simpleLevel.changeTwoTiles(7,9, false);
		
		return simpleLevel;
	}
	
	private static final ApoRelaxLevel getNineLevel() {
		BufferedImage iLevel = ApoRelaxLevelGeneration.imageArray[8];
		
		ApoRelaxTile[] tiles = new ApoRelaxTile[21];
		tiles[0] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 0, 95, 90, 0);
		tiles[1] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 95, 0, 85, 117, 1);
		tiles[2] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 180, 0, 220, 40, 2);
		tiles[3] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 180, 40, 40, 77, 3);
		tiles[4] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 152, 117, 69, 51, 4);
		tiles[5] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 90, 95, 40, 5);
		tiles[6] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 130, 40, 78, 6);
		tiles[7] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 208, 40, 92, 7);
		tiles[8] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 40, 260, 203, 40, 8);
		tiles[9] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 243, 260, 157, 40, 9);
		tiles[10] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 360, 40, 40, 128, 10);
		tiles[11] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 327, 168, 73, 92, 11);
		tiles[12] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 243, 168, 84, 92, 12);
		tiles[13] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 271, 106, 89, 62, 13);
		tiles[14] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 221, 106, 50, 62, 14);
		tiles[15] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 220, 40, 73, 66, 15);
		tiles[16] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 293, 40, 67, 66, 16);
		tiles[17] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 95, 117, 57, 59, 17);
		tiles[18] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 40, 130, 55, 130, 18);
		tiles[19] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 95, 176, 58, 84, 19);
		tiles[20] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 153, 168, 90, 92, 20);
		
		ApoRelaxLevel simpleLevel = new ApoRelaxLevel("Bulldozer", "", tiles, iLevel.getWidth(), iLevel.getHeight());
		
		for (int i = 0; i < 20; i+= 2) {
			simpleLevel.changeTwoTiles(i, i+1, false);
		}
		Random random = new Random(19091982);
		int rand = random.nextInt(30);
		for (int i = 0; i < rand; i++) {
			simpleLevel.changeTwoTiles(random.nextInt(21), random.nextInt(21), false);
		}
		
		return simpleLevel;
	}
	
	private static final ApoRelaxLevel getTenLevel() {
		BufferedImage iLevel = ApoRelaxLevelGeneration.imageArray[9];
		
		ApoRelaxTile[] tiles = new ApoRelaxTile[19];
		tiles[0] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 212, 117, 55, 0);
		tiles[1] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 117, 212, 107, 55, 1);
		tiles[2] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 87, 118, 80, 94, 2);
		tiles[3] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 167, 118, 96, 94, 3);
		tiles[4] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 224, 212, 112, 55, 4);
		tiles[5] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 336, 172, 64, 95, 5);
		tiles[6] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 360, 48, 40, 124, 6);
		tiles[7] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 284, 0, 116, 48, 7);
		tiles[8] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 155, 0, 129, 48, 8);
		tiles[9] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 87, 0, 68, 118, 9);
		tiles[10] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 47, 0, 40, 81, 10);
		tiles[11] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 0, 47, 72, 11);
		tiles[12] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 72, 47, 75, 12);
		tiles[13] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 47, 81, 40, 66, 13);
		tiles[14] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 147, 87, 65, 14);
		tiles[15] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 263, 172, 73, 40, 15);
		tiles[16] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 263, 118, 97, 54, 16);
		tiles[17] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 263, 48, 97, 70, 17);
		tiles[18] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 155, 48, 108, 70, 18);
		
		ApoRelaxLevel simpleLevel = new ApoRelaxLevel("Cars", "", tiles, iLevel.getWidth(), iLevel.getHeight());
		
		for (int i = 0; i < tiles.length - 1; i+= 2) {
			simpleLevel.changeTwoTiles(i, i+1, false);
		}
		Random random = new Random(14031980);
		int rand = random.nextInt(30);
		for (int i = 0; i < rand; i++) {
			simpleLevel.changeTwoTiles(random.nextInt(tiles.length), random.nextInt(tiles.length), false);
		}
		
		return simpleLevel;
	}
	
	private static final ApoRelaxLevel getElevenLevel() {
		BufferedImage iLevel = ApoRelaxLevelGeneration.imageArray[10];
		
		ApoRelaxTile[] tiles = new ApoRelaxTile[28];
		tiles[0] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 212, 47, 53, 65, 0);
		tiles[1] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 360, 353, 40, 40, 1);
		tiles[2] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 234, 339, 66, 54, 2);
		tiles[3] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 353, 76, 40, 3);
		tiles[4] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 313, 76, 40, 4);
		tiles[5] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 360, 96, 40, 84, 5);
		tiles[6] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 360, 0, 40, 96, 6);
		tiles[7] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 76, 178, 136, 63, 7);
		tiles[8] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 241, 76, 72, 8);
		tiles[9] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 168, 339, 66, 54, 9);
		tiles[10] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 300, 302, 60, 91, 10);
		tiles[11] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 76, 313, 92, 80, 11);
		tiles[12] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 76, 241, 92, 72, 12);
		tiles[13] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 168, 241, 66, 98, 13);
		tiles[14] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 234, 241, 66, 98, 14);
		tiles[15] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 1, 0, 75, 178, 15);
		tiles[16] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 178, 76, 63, 16);
		tiles[17] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 257, 112, 43, 129, 17);
		tiles[18] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 76, 0, 136, 72, 18);
		tiles[19] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 300, 241, 60, 61, 19);
		tiles[20] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 300, 180, 60, 61, 20);
		tiles[21] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 300, 112, 60, 68, 21);
		tiles[22] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 360, 180, 40, 173, 22);
		tiles[23] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 212, 112, 45, 129, 23);
		tiles[24] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 76, 72, 136, 106, 24);
		tiles[25] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 265, 0, 95, 58, 25);
		tiles[26] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 265, 58, 95, 54, 26);
		tiles[27] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 212, 0, 53, 47, 27);
		
		ApoRelaxLevel simpleLevel = new ApoRelaxLevel("Futuristic", "", tiles, iLevel.getWidth(), iLevel.getHeight());
		
		for (int i = 0; i < tiles.length - 1; i+= 2) {
			simpleLevel.changeTwoTiles(i, i+1, false);
		}
		Random random = new Random(19031954);
		int rand = random.nextInt(3 * tiles.length);
		for (int i = 0; i < rand; i++) {
			simpleLevel.changeTwoTiles(random.nextInt(tiles.length), random.nextInt(tiles.length), false);
		}
		
		return simpleLevel;
	}
	
	private static final ApoRelaxLevel getTwelveLevel() {
		BufferedImage iLevel = ApoRelaxLevelGeneration.imageArray[11];
		
		ApoRelaxTile[] tiles = new ApoRelaxTile[29];
		tiles[0] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 0, 54, 80, 0);
		tiles[1] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 54, 0, 45, 40, 1);
		tiles[2] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 54, 40, 45, 40, 2);
		tiles[3] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 166, 80, 67, 55, 3);
		tiles[4] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 233, 80, 103, 55, 4);
		tiles[5] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 278, 135, 58, 40, 5);
		tiles[6] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 219, 135, 59, 40, 6);
		tiles[7] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 166, 135, 53, 40, 7);
		tiles[8] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 206, 175, 40, 44, 8);
		tiles[9] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 246, 175, 40, 44, 9);
		tiles[10] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 286, 175, 50, 44, 10);
		tiles[11] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 336, 109, 64, 56, 11);
		tiles[12] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 336, 165, 64, 54, 12);
		tiles[13] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 166, 175, 40, 44, 13);
		tiles[14] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 126, 80, 40, 55, 14);
		tiles[15] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 126, 135, 40, 84, 15);
		tiles[16] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 99, 0, 63, 80, 16);
		tiles[17] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 162, 0, 69, 80, 17);
		tiles[18] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 231, 0, 105, 80, 18);
		tiles[19] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 336, 0, 64, 109, 19);
		tiles[20] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 80, 80, 46, 65, 20);
		tiles[21] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 80, 80, 65, 21);
		tiles[22] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 80, 145, 46, 74, 22);
		tiles[23] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 166, 219, 40, 76, 23);
		tiles[24] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 206, 219, 130, 76, 24);
		tiles[25] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 336, 219, 64, 76, 25);
		tiles[26] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 80, 219, 86, 76, 26);
		tiles[27] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 145, 80, 53, 27);
		tiles[28] = ApoRelaxLevelGeneration.getRelaxTile(iLevel, 0, 198, 80, 97, 28);
		
		ApoRelaxLevel simpleLevel = new ApoRelaxLevel("Eye", "", tiles, iLevel.getWidth(), iLevel.getHeight());
		
		for (int i = 0; i < tiles.length - 1; i+= 2) {
			simpleLevel.changeTwoTiles(i, i+1, false);
		}
		Random random = new Random(19031954);
		int rand = random.nextInt(3 * tiles.length);
		for (int i = 0; i < rand; i++) {
			simpleLevel.changeTwoTiles(random.nextInt(tiles.length), random.nextInt(tiles.length), false);
		}
		
		return simpleLevel;
	}
}
