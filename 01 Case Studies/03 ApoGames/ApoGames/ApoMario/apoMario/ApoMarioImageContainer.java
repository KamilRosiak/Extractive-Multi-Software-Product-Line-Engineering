package apoMario;

import java.awt.image.BufferedImage;

/**
 * Hilfsbilderklasse, die die Originalbilder läd und ermöglicht zwischen den einzelnen Tiles hin und herzuschalten
 * @author Dirk Aporius
 *
 */
public class ApoMarioImageContainer {

	public static BufferedImage[] PLAYER_MARIO;
	
	public static BufferedImage PARTICLE_MARIO;
	
	public static BufferedImage TILES_BACKGROUND_MARIO;

	public static BufferedImage TILES_LEVEL_MARIO;
	
	public static BufferedImage ITEMS_MARIO;
	
	public static BufferedImage ENEMIES_MARIO;

	public static BufferedImage MENU_MARIO;
	
	public static BufferedImage[] PLAYER_EAT;
	
	public static BufferedImage PARTICLE_EAT;
	
	public static BufferedImage TILES_BACKGROUND_EAT;

	public static BufferedImage TILES_LEVEL_EAT;
	
	public static BufferedImage ITEMS_EAT;
	
	public static BufferedImage ENEMIES_EAT;
	
	public static BufferedImage MENU_EAT;
	
	public static BufferedImage[] PLAYER;
	
	public static BufferedImage PARTICLE;
	
	public static BufferedImage TILES_BACKGROUND;

	public static BufferedImage TILES_LEVEL;
	
	public static BufferedImage ITEMS;
	
	public static BufferedImage ENEMIES;

	public static BufferedImage MENU;
	
	public static BufferedImage CAMERA;
	
	private static final boolean B_EXTERN = ApoMarioConstants.LOAD_EXTERN;
	
	public static int TILE_SET;
	
	public static void init(ApoMarioComponent component) {
		ApoMarioImageContainer.PLAYER_EAT = new BufferedImage[3];
		ApoMarioImageContainer.PLAYER_EAT[ApoMarioConstants.PLAYER_TYPE_LITTLE] = component.getResizeImage(component.getImages().getImage("images/player_little.gif", ApoMarioImageContainer.B_EXTERN));
		ApoMarioImageContainer.PLAYER_EAT[ApoMarioConstants.PLAYER_TYPE_BIG] = component.getResizeImage(component.getImages().getImage("images/player_big.gif", ApoMarioImageContainer.B_EXTERN));
		ApoMarioImageContainer.PLAYER_EAT[ApoMarioConstants.PLAYER_TYPE_FIRE] = component.getResizeImage(component.getImages().getImage("images/player_fire.gif", ApoMarioImageContainer.B_EXTERN));
		
		ApoMarioImageContainer.PARTICLE_EAT = component.getResizeImage(component.getImages().getImage("images/particlesheet.gif", ApoMarioImageContainer.B_EXTERN));		
		ApoMarioImageContainer.MENU_EAT = component.getResizeImage(component.getImages().getImage("images/menu.gif", ApoMarioImageContainer.B_EXTERN));
		ApoMarioImageContainer.TILES_BACKGROUND_EAT = component.getResizeImage(component.getImages().getImage("images/background.png", ApoMarioImageContainer.B_EXTERN));
		ApoMarioImageContainer.TILES_LEVEL_EAT = component.getResizeImage(component.getImages().getImage("images/mapsheet.gif", ApoMarioImageContainer.B_EXTERN));
		ApoMarioImageContainer.ITEMS_EAT = component.getResizeImage(component.getImages().getImage("images/itemsheet.gif", ApoMarioImageContainer.B_EXTERN));
		ApoMarioImageContainer.ENEMIES_EAT = component.getResizeImage(component.getImages().getImage("images/enemysheet.gif", ApoMarioImageContainer.B_EXTERN));
		
		ApoMarioImageContainer.PLAYER_MARIO = new BufferedImage[3];
		ApoMarioImageContainer.PLAYER_MARIO[ApoMarioConstants.PLAYER_TYPE_LITTLE] = component.getResizeImage(component.getImages().getImage("images/player_little_mario.gif", ApoMarioImageContainer.B_EXTERN));
		ApoMarioImageContainer.PLAYER_MARIO[ApoMarioConstants.PLAYER_TYPE_BIG] = component.getResizeImage(component.getImages().getImage("images/player_big_mario.gif", ApoMarioImageContainer.B_EXTERN));
		ApoMarioImageContainer.PLAYER_MARIO[ApoMarioConstants.PLAYER_TYPE_FIRE] = component.getResizeImage(component.getImages().getImage("images/player_fire_mario.gif", ApoMarioImageContainer.B_EXTERN));
		
		ApoMarioImageContainer.PARTICLE_MARIO = component.getResizeImage(component.getImages().getImage("images/particlesheet_mario.gif", ApoMarioImageContainer.B_EXTERN));		
		ApoMarioImageContainer.MENU_MARIO = component.getResizeImage(component.getImages().getImage("images/menu_mario.gif", ApoMarioImageContainer.B_EXTERN));
		ApoMarioImageContainer.TILES_BACKGROUND_MARIO = component.getResizeImage(component.getImages().getImage("images/background_mario.png", ApoMarioImageContainer.B_EXTERN));
		ApoMarioImageContainer.TILES_LEVEL_MARIO = component.getResizeImage(component.getImages().getImage("images/mapsheet_mario.gif", ApoMarioImageContainer.B_EXTERN));
		ApoMarioImageContainer.ITEMS_MARIO = component.getResizeImage(component.getImages().getImage("images/itemsheet_mario.gif", ApoMarioImageContainer.B_EXTERN));
		ApoMarioImageContainer.ENEMIES_MARIO = component.getResizeImage(component.getImages().getImage("images/enemysheet_mario.gif", ApoMarioImageContainer.B_EXTERN));
		
		ApoMarioImageContainer.CAMERA = component.getResizeImage(component.getImages().getImage("images/camera.gif", ApoMarioImageContainer.B_EXTERN));
		ApoMarioImageContainer.setTileSet(ApoMarioConstants.TILE_SET_EAT);
	}
	
	public static void setTileSet(int tileSet) {
		ApoMarioImageContainer.TILE_SET = tileSet;
		if (tileSet == ApoMarioConstants.TILE_SET_EAT) {
			ApoMarioConstants.TILE_SET = ApoMarioConstants.TILE_SET_EAT;
			ApoMarioImageContainer.PLAYER = ApoMarioImageContainer.PLAYER_EAT;
			ApoMarioImageContainer.PARTICLE = ApoMarioImageContainer.PARTICLE_EAT;
			ApoMarioImageContainer.TILES_BACKGROUND = ApoMarioImageContainer.TILES_BACKGROUND_EAT;
			ApoMarioImageContainer.TILES_LEVEL = ApoMarioImageContainer.TILES_LEVEL_EAT;
			ApoMarioImageContainer.ITEMS = ApoMarioImageContainer.ITEMS_EAT;
			ApoMarioImageContainer.ENEMIES = ApoMarioImageContainer.ENEMIES_EAT;
			ApoMarioImageContainer.MENU = ApoMarioImageContainer.MENU_EAT;
		} else if (tileSet == ApoMarioConstants.TILE_SET_MARIO) {
			ApoMarioConstants.TILE_SET = ApoMarioConstants.TILE_SET_MARIO;
			ApoMarioImageContainer.PLAYER = ApoMarioImageContainer.PLAYER_MARIO;
			ApoMarioImageContainer.PARTICLE = ApoMarioImageContainer.PARTICLE_MARIO;
			ApoMarioImageContainer.TILES_BACKGROUND = ApoMarioImageContainer.TILES_BACKGROUND_MARIO;
			ApoMarioImageContainer.TILES_LEVEL = ApoMarioImageContainer.TILES_LEVEL_MARIO;
			ApoMarioImageContainer.ITEMS = ApoMarioImageContainer.ITEMS_MARIO;
			ApoMarioImageContainer.ENEMIES = ApoMarioImageContainer.ENEMIES_MARIO;
			ApoMarioImageContainer.MENU = ApoMarioImageContainer.MENU_MARIO;
		}
	}
}
