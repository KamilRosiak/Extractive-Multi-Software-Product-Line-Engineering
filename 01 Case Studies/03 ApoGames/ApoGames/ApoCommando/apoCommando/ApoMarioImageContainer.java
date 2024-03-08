package apoCommando;

import java.awt.image.BufferedImage;

public class ApoMarioImageContainer {

	public static BufferedImage[] PLAYER_MARIO;
	
	public static BufferedImage PARTICLE_MARIO;
	
	public static BufferedImage TILES_BACKGROUND_MARIO;

	public static BufferedImage TILES_LEVEL_MARIO;
	
	public static BufferedImage ITEMS_MARIO;
	
	public static BufferedImage ENEMIES_MARIO;
	
	public static BufferedImage[] PLAYER_EAT;
	
	public static BufferedImage PARTICLE_EAT;
	
	public static BufferedImage TILES_BACKGROUND_EAT;

	public static BufferedImage TILES_LEVEL_EAT;
	
	public static BufferedImage ITEMS_EAT;
	
	public static BufferedImage ENEMIES_EAT;
	
	public static BufferedImage[] PLAYER;
	
	public static BufferedImage PARTICLE;
	
	public static BufferedImage TILES_BACKGROUND;

	public static BufferedImage TILES_LEVEL;
	
	public static BufferedImage ITEMS;
	
	public static BufferedImage ENEMIES;
	
	public static BufferedImage CAMERA;
	
	public static void init(ApoMarioGameComponent component) {
		ApoMarioImageContainer.PLAYER_EAT = new BufferedImage[3];
		ApoMarioImageContainer.PLAYER_EAT[ApoMarioConstants.PLAYER_TYPE_LITTLE] = component.getResizeImage(component.getImages().getImage("images/player_little.gif", false));
		ApoMarioImageContainer.PLAYER_EAT[ApoMarioConstants.PLAYER_TYPE_BIG] = component.getResizeImage(component.getImages().getImage("images/player_big.gif", false));
		ApoMarioImageContainer.PLAYER_EAT[ApoMarioConstants.PLAYER_TYPE_FIRE] = component.getResizeImage(component.getImages().getImage("images/player_fire.gif", false));
		
		ApoMarioImageContainer.PARTICLE_EAT = component.getResizeImage(component.getImages().getImage("images/particlesheet.gif", false));		
		ApoMarioImageContainer.TILES_BACKGROUND_EAT = component.getResizeImage(component.getImages().getImage("images/background.png", false));
		ApoMarioImageContainer.TILES_LEVEL_EAT = component.getResizeImage(component.getImages().getImage("images/mapsheet.gif", false));
		ApoMarioImageContainer.ITEMS_EAT = component.getResizeImage(component.getImages().getImage("images/itemsheet.gif", false));
		ApoMarioImageContainer.ENEMIES_EAT = component.getResizeImage(component.getImages().getImage("images/enemysheet.gif", false));
		
		ApoMarioImageContainer.PLAYER_MARIO = new BufferedImage[3];
		ApoMarioImageContainer.PLAYER_MARIO[ApoMarioConstants.PLAYER_TYPE_LITTLE] = component.getResizeImage(component.getImages().getImage("images/player_little_mario.gif", false));
		ApoMarioImageContainer.PLAYER_MARIO[ApoMarioConstants.PLAYER_TYPE_BIG] = component.getResizeImage(component.getImages().getImage("images/player_big_mario.gif", false));
		ApoMarioImageContainer.PLAYER_MARIO[ApoMarioConstants.PLAYER_TYPE_FIRE] = component.getResizeImage(component.getImages().getImage("images/player_fire_mario.gif", false));
		
		ApoMarioImageContainer.PARTICLE_MARIO = component.getResizeImage(component.getImages().getImage("images/particlesheet_mario.gif", false));		
		ApoMarioImageContainer.TILES_BACKGROUND_MARIO = component.getResizeImage(component.getImages().getImage("images/background_mario.png", false));
		ApoMarioImageContainer.TILES_LEVEL_MARIO = component.getResizeImage(component.getImages().getImage("images/mapsheet_mario.gif", false));
		ApoMarioImageContainer.ITEMS_MARIO = component.getResizeImage(component.getImages().getImage("images/itemsheet_mario.gif", false));
		ApoMarioImageContainer.ENEMIES_MARIO = component.getResizeImage(component.getImages().getImage("images/enemysheet_mario.gif", false));
		
		ApoMarioImageContainer.CAMERA = component.getResizeImage(component.getImages().getImage("images/camera.gif", false));
		ApoMarioImageContainer.setTileSet(ApoMarioConstants.TILE_SET);
	}
	
	public static void setTileSet(int tileSet) {
		if (tileSet == ApoMarioConstants.TILE_SET_EAT) {
			ApoMarioConstants.TILE_SET = ApoMarioConstants.TILE_SET_EAT;
			ApoMarioImageContainer.PLAYER = ApoMarioImageContainer.PLAYER_EAT;
			ApoMarioImageContainer.PARTICLE = ApoMarioImageContainer.PARTICLE_EAT;
			ApoMarioImageContainer.TILES_BACKGROUND = ApoMarioImageContainer.TILES_BACKGROUND_EAT;
			ApoMarioImageContainer.TILES_LEVEL = ApoMarioImageContainer.TILES_LEVEL_EAT;
			ApoMarioImageContainer.ITEMS = ApoMarioImageContainer.ITEMS_EAT;
			ApoMarioImageContainer.ENEMIES = ApoMarioImageContainer.ENEMIES_EAT;
		} else if (tileSet == ApoMarioConstants.TILE_SET_MARIO) {
			ApoMarioConstants.TILE_SET = ApoMarioConstants.TILE_SET_MARIO;
			ApoMarioImageContainer.PLAYER = ApoMarioImageContainer.PLAYER_MARIO;
			ApoMarioImageContainer.PARTICLE = ApoMarioImageContainer.PARTICLE_MARIO;
			ApoMarioImageContainer.TILES_BACKGROUND = ApoMarioImageContainer.TILES_BACKGROUND_MARIO;
			ApoMarioImageContainer.TILES_LEVEL = ApoMarioImageContainer.TILES_LEVEL_MARIO;
			ApoMarioImageContainer.ITEMS = ApoMarioImageContainer.ITEMS_MARIO;
			ApoMarioImageContainer.ENEMIES = ApoMarioImageContainer.ENEMIES_MARIO;
		}
	}
}
