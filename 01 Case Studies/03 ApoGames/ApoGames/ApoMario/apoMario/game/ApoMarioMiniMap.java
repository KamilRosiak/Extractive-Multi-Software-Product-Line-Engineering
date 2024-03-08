package apoMario.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import apoMario.ApoMarioConstants;
import apoMario.entity.ApoMarioPlayer;
import apoMario.level.ApoMarioLevel;

/**
 * Minimapklasse, die im Spiel am unteren Bildschirmrand sichtbar ist
 * @author Dirk Aporius
 *
 */
public class ApoMarioMiniMap {

	private ApoMarioLevel level;
	
	private final int MAX_HEIGHT = 35 * ApoMarioConstants.SIZE;
	
	private BufferedImage iMiniMap;
	
	public ApoMarioMiniMap(ApoMarioLevel level) {
		this.level = level;
		this.makeMiniMap();
	}
	
	public void makeMiniMap() {
		this.iMiniMap = this.getMiniImage();
	}
	
	private BufferedImage getMiniImage() {
		BufferedImage image = new BufferedImage(this.level.getWidth() * ApoMarioConstants.TILE_SIZE, this.level.getHeight() * ApoMarioConstants.TILE_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = (Graphics2D)(image.getGraphics());
		for (int y = 0; y < this.level.getHeight(); y++) {
			for (int x = 0; x < this.level.getWidth(); x++) {
				if (this.level.getLevelEntities()[y][x] != null) {
					this.level.getLevelEntities()[y][x].render(g, 0, 0);
				}
			}
		}
		g.dispose();
		
		float width = (float)(this.MAX_HEIGHT / (float)(this.level.getHeight() * ApoMarioConstants.TILE_SIZE)) * (this.level.getWidth() * ApoMarioConstants.TILE_SIZE); 
		BufferedImage returnImage = new BufferedImage((int)width, (int)(this.MAX_HEIGHT), BufferedImage.TYPE_INT_ARGB_PRE);
		g = (Graphics2D)(returnImage.getGraphics());
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(image.getScaledInstance(returnImage.getWidth(), returnImage.getHeight(), BufferedImage.SCALE_SMOOTH), 0, 0, null);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.dispose();
		return returnImage;
	}
	
	public void render(Graphics2D g) {
		if (this.iMiniMap != null) {
			g.setFont(ApoMarioConstants.FONT_FPS);
			int maxWidth = -1;
			for (int i = 0; i < this.level.getPlayers().size(); i++) {
				ApoMarioPlayer player = this.level.getPlayers().get(i);
				if (player.isBVisible()) {
					if (maxWidth < player.getX()) {
						maxWidth = (int)player.getX();
					}
				}
			}
			float dif = (float)(this.MAX_HEIGHT / (float)(this.level.getHeight() * ApoMarioConstants.TILE_SIZE)) * maxWidth;
			if (dif > ApoMarioConstants.GAME_WIDTH/2) {
				dif = dif - ApoMarioConstants.GAME_WIDTH/2;
			} else {
				dif = 0;
			}
			float x = (int)(- dif);
			if (x < 0) {
				int width = (int)(this.iMiniMap.getWidth() - dif);
				if (width > ApoMarioConstants.GAME_WIDTH) {
					width = ApoMarioConstants.GAME_WIDTH;
				}
				g.drawImage(this.iMiniMap.getSubimage((int)dif, 0, width, this.iMiniMap.getHeight()), (int)(0), ApoMarioConstants.GAME_HEIGHT - this.iMiniMap.getHeight(), null);
			}
			g.drawImage(this.iMiniMap, (int)(- dif), ApoMarioConstants.GAME_HEIGHT - this.iMiniMap.getHeight(), null);
			for (int i = 0; i < this.level.getPlayers().size(); i++) {
				ApoMarioPlayer player = this.level.getPlayers().get(i);
				if (player.isBVisible()) {
					if (i == 0) {
						g.setColor(Color.RED);
					} else {
						g.setColor(Color.BLUE);
					}
					float myDif = (float)(this.MAX_HEIGHT / (float)(this.level.getHeight() * ApoMarioConstants.TILE_SIZE)) * player.getX();
//					if (myDif > ApoMarioConstants.GAME_WIDTH/2) {
//						myDif = ApoMarioConstants.GAME_WIDTH/2;
//					}
					x = - dif + myDif;
					float y = ApoMarioConstants.GAME_HEIGHT - this.iMiniMap.getHeight() + ((float)(player.getY() + player.getHeight()) / (float)ApoMarioConstants.GAME_HEIGHT) * this.iMiniMap.getHeight();
					g.drawString("M", x, y);
				}
			}
		}
	}
	
}
