package net.apogames.apomono.entity;

import net.apogames.apomono.ApoMonoConstants;
import net.apogames.apomono.game.ApoMonoPanel;
import net.apogames.apomono.game.ApoMonoPuzzleGame;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;

public class ApoLevelChooserButton extends ApoButton {

	private boolean bSelected;
	
	private String text;
	
	public ApoLevelChooserButton(int x, int y, int width, int height, String function, String text) {
		this(x, y, width, height, function, text, false);
	}
	
	public ApoLevelChooserButton(int x, int y, int width, int height, String function, String text, final boolean bSelected) {
		super(null, x, y, width, height, function);
		
		this.bSelected = bSelected;
		this.text = text;
	}

	public boolean isSelected() {
		return this.bSelected;
	}

	public void setSelected(boolean bSelected) {
		this.bSelected = bSelected;
	}

	/**
	 * malt den Button an die Stelle getX() + changeX und getY() + changeY hin
	 * @param changeX: Verschiebung in x-Richtung
	 * @param changeY: Verschiebung in y-Richtung
	 */
	public void render(BitsGLGraphics g, int changeX, int changeY ) {
		if ( this.isBVisible() ) {
			int x = (int)(this.getX());
			int y = (int)(this.getY());
			int width = (int)(this.getWidth());
			int height = (int)(this.getHeight());
			String s = this.text;
			
			ApoLevelChooserButton.drawOneBlock(g, x, y, width, height);
			
			if (this.bSelected) {
				if (s.equals("x")) {
					ApoMonoPuzzleGame.drawX(g, x + width/2 - 16, y + height/2 - 16, true, ApoMonoConstants.BRIGHT, false, false);
				} else {
					g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
					g.setFont(ApoMonoPanel.game_font);
					g.drawText(s, (int)(x + width/2 - ApoMonoPanel.game_font.getLength(s)/2), y + height/2 - ApoMonoPanel.game_font.mCharCellHeight/2);					
				}
			}
		}
	}
	
	public static void drawBlock(final BitsGLGraphics g, final int x, final int y, final int width, final int height) {
		for (int j = 0; j < width/16; j++) {
			ApoLevelChooserButton.drawOneBlock(g, x + j * 16, y, 16, 16);
			if ((j == 0) || (j + 1 == width/16)) {
				for (int k = 1; k < height/16 - 1; k++) {
					ApoLevelChooserButton.drawOneBlock(g, x + j * 16, y + k * 16, 16, 16);
				}
			}
			ApoLevelChooserButton.drawOneBlock(g, x + j * 16, y + height - 16, 16, 16);
		}
	}
	
	public static void drawOneBlock(final BitsGLGraphics g, int x, int y, final int width, final int height) {
		g.setColor(ApoMonoConstants.DARK[0], ApoMonoConstants.DARK[1], ApoMonoConstants.DARK[2], 1f);
		g.fillRect(x + 2, y + 2, width - 4, height - 4);
		ApoMonoPuzzleGame.setDarkerColor(g);
		g.fillRect(x + 2, y, width - 4, 2);
		g.fillRect(x + 2, y + height - 2, width - 4, 2);
		g.fillRect(x, y + 2, 2, height - 4);
		g.fillRect(x + width - 2, y + 2, 2, height - 4);
		ApoMonoPuzzleGame.setBrighterColor(g);
		g.fillRect(x + 4, y + height, width - 4, 2);
		g.fillRect(x + width, y + 4, 2, height - 4);
		g.fillRect(x + width - 2, y + height - 2, 2, 2);
	}
}
