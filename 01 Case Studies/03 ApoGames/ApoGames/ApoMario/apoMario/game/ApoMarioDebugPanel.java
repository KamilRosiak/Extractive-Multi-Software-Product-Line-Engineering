package apoMario.game;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import apoMario.ApoMarioConstants;
import apoMario.ai.ApoMarioAILevel;
import apoMario.ai.ApoMarioAIPlayer;

/**
 * Panel, dass das Spiel abstrakt darstellt (mit Rechtecken, Kreisen usw)
 * @author Dirk Aporius
 *
 */
public class ApoMarioDebugPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private ApoMarioAILevel level;
	
	private ApoMarioAIPlayer player;
	
	protected void setLevel(ApoMarioAIPlayer player, ApoMarioAILevel level) {
		this.level = level;
		this.player = player;
		this.repaint();
	}
	
	public void paintComponent(Graphics g) {
		try {
			if (this.level != null) {
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
				int width = 15;
				int height = 15;

				float changeX = this.level.getChangeX();
				if (changeX < 0) {
					changeX = 0;
				}
				int realChangeX = (int)(changeX);
				changeX = changeX - realChangeX;
				for (int y = 0; y < this.level.getLevelArray().length; y++) {
					for (int x = 0; x < this.level.getLevelArray()[0].length; x++) {
						if (this.level.getLevelArray()[y][x] == ApoMarioConstants.WALL) {
							g.setColor(Color.BLACK);
							g.drawRect((int)((x - changeX) * width), y * width, width - 1, height - 1);
						} else if (this.level.getLevelArray()[y][x] == ApoMarioConstants.QUESTIONMARKBOX) {
							g.setColor(Color.BLACK);
							g.drawRect((int)((x - changeX) * width), y * height, width - 1, height - 1);
							String s = "?";
							int w = g.getFontMetrics().stringWidth(s);
							g.drawString(s, (int)((x - changeX) * width) + w/2, (y + 1) * height - 1);
						} else if (this.level.getLevelArray()[y][x] == ApoMarioConstants.DESTRUCTIBLEBOX) {
							g.setColor(Color.BLACK);
							g.drawRect((int)((x - changeX) * width), y * height, width - 1, height - 1);
							String s = "X";
							int w = g.getFontMetrics().stringWidth(s);
							g.drawString(s, (int)((x - changeX) * width) + w/2, (y + 1) * height - 1);
						} else if (this.level.getLevelArray()[y][x] == ApoMarioConstants.CANNON) {
							g.setColor(Color.BLACK);
							g.drawRect((int)((x - changeX) * width), y * height, width - 1, height - 1);
							String s = "C";
							int w = g.getFontMetrics().stringWidth(s);
							g.drawString(s, (int)((x - changeX) * width) + w/2, (y + 1) * height - 1);
						} else if (this.level.getLevelArray()[y][x] == ApoMarioConstants.COIN) {
							g.setColor(Color.BLACK);
							String s = "C";
							int w = g.getFontMetrics().stringWidth(s);
							g.drawString(s, (int)((x - changeX) * width) + w/2, (y + 1) * height - 1);
						} else if ((this.level.getLevelArray()[y][x] == ApoMarioConstants.ONLY_ABOVE_WALL) ||
								(this.level.getLevelArray()[y][x] == ApoMarioConstants.NO_COLLISION_WALL)) {
							g.setColor(Color.GRAY);
							g.drawRect((int)((x - changeX) * width), y * height, width - 1, height - 1);
						} else if (this.level.getLevelArray()[y][x] == ApoMarioConstants.FINISH) {
							g.setColor(Color.CYAN);
							g.drawRect((int)((x - changeX) * width), y * height, width - 1, height - 1);
						}
					}
				}
				for (int goodie = 0; goodie < this.level.getGoodies().size(); goodie++) {
					g.setColor(Color.GREEN);
					float x = (int)((this.level.getGoodies().get(goodie).getX() - changeX) * 15);
					int y = (int)(this.level.getGoodies().get(goodie).getY() * 15);
					width = (int)(this.level.getGoodies().get(goodie).getWidth() * 15);
					height = (int)(this.level.getGoodies().get(goodie).getHeight() * 15);
					g.drawRect((int)(x), y, width - 1, height - 1);
				}
				for (int enemy = 0; enemy < this.level.getEnemies().size(); enemy++) {
					g.setColor(Color.RED);
					float x = (int)((this.level.getEnemies().get(enemy).getX() - changeX) * 15);
					int y = (int)(this.level.getEnemies().get(enemy).getY() * 15);
					width = (int)(this.level.getEnemies().get(enemy).getWidth() * 15);
					height = (int)(this.level.getEnemies().get(enemy).getHeight() * 15);
					g.drawRect((int)(x), y, width - 1, height - 1);
				}
				g.setColor(Color.BLUE);
				if ((this.level.getEnemyPlayer() != null) && (this.level.getEnemyPlayer().isVisible())) {
					float x = ((this.level.getEnemyPlayer().getX() - changeX) * 15);
					int y = (int)(this.level.getEnemyPlayer().getY() * 15);
					width = (int)(this.level.getEnemyPlayer().getWidth() * 15);
					height = (int)(this.level.getEnemyPlayer().getHeight() * 15);
					g.fillRect((int)(x), y, width, height);
				}
				if (this.player != null) {
					float x = ((this.player.getX() - changeX) * 15);
					int y = (int)(this.player.getY() * 15);
					width = (int)(this.player.getWidth() * 15);
					height = (int)(this.player.getHeight() * 15);
					g.fillRect((int)(x), y, width, height);
				}
			}
		} catch (Exception ex) {
			
		}
	}
	
}
