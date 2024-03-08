package net.apogames.apoclock.game;

import net.apogames.apoclock.ApoClockModel;
import net.gliblybits.bitsengine.core.BitsGame;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;

public class ApoClockMenu extends ApoClockModel {

	public static final String QUIT = "quit";
	public static final String PUZZLE = "puzzle";
	public static final String ARCADE = "arcade";
	public static final String OPTIONS = "options";
	public static final String TITLE = "ApoClock";
	
	private float clockRotate;
	
	public ApoClockMenu(ApoClockPanel game) {
		super(game);
	}

	@Override
	public void init() {
		this.getStringWidth().put(ApoClockMenu.QUIT, (int)(ApoClockPanel.font.getLength(ApoClockMenu.QUIT)));
		this.getStringWidth().put(ApoClockMenu.PUZZLE, (int)(ApoClockPanel.font.getLength(ApoClockMenu.PUZZLE)));
		this.getStringWidth().put(ApoClockMenu.ARCADE, (int)(ApoClockPanel.font.getLength(ApoClockMenu.ARCADE)));
		this.getStringWidth().put(ApoClockMenu.OPTIONS, (int)(ApoClockPanel.font.getLength(ApoClockMenu.OPTIONS)));
		this.getStringWidth().put(ApoClockMenu.TITLE, (int)(ApoClockPanel.title_font.getLength(ApoClockMenu.TITLE)));
	}
	
	public void onResume() {
	}

	@Override
	public void touchedPressed(int x, int y, int finger) {
		
	}

	@Override
	public void touchedReleased(int x, int y, int finger) {
		
	}

	@Override
	public void touchedDragged(int x, int y, int oldX, int oldY, int finger) {
		
	}

	@Override
	public void touchedButton(String function) {
		if (function.equals(ApoClockMenu.QUIT)) {
			this.onBackButtonPressed();
		} else if (function.equals(ApoClockMenu.PUZZLE)) {
			this.getGame().setPuzzle();
		} else if (function.equals(ApoClockMenu.ARCADE)) {
			this.getGame().setAracardeHelp(-1, -1);
		} else if (function.equals(ApoClockMenu.OPTIONS)) {
			this.getGame().setOptions();
		}
	}
	
	public void onBackButtonPressed() {
		BitsGame.getInstance().finishApp();
	}
	
	@Override
	public void think(int delta) {
		this.clockRotate += delta / 10f;
		if (this.clockRotate >= 360) {
			this.clockRotate -= 360;
		}
	}

	@Override
	public void render(final BitsGLGraphics g) {
		this.getGame().drawBackgroundCircle(g, 100, 20, 100, (int)this.clockRotate);
		this.getGame().drawBackgroundCircle(g, 380, 20, 100, (int)this.clockRotate);
		
		this.getGame().drawString(g, ApoClockMenu.TITLE, 240, 45, ApoClockPanel.title_font, new float[] {1, 1, 1, 1}, new float[] {0, 0, 0, 1});
		
		if (this.getGame().getButtons() != null) {
			for (int i = 0; i < this.getGame().getButtons().length; i++) {
				if (this.getGame().getButtons()[i].isVisible()) {
					int x = (int)(this.getGame().getButtons()[i].getX());
					int y = (int)(this.getGame().getButtons()[i].getY());
					int width = (int)(this.getGame().getButtons()[i].getWidth());
					int height = (int)(this.getGame().getButtons()[i].getHeight());
					
					g.setColor(128, 128, 128, 255);
					g.fillRect(x, y, width, height);
					g.setColor(48f/255f, 48f/255f, 48f/255f, 1.0f);
					g.drawRect(x, y, width, height);
					
					this.getGame().drawString(g, this.getGame().getButtons()[i].getFunction(), x + width/2, y + height/2 - ApoClockPanel.font.mCharCellHeight/2, ApoClockPanel.font);
					
					for (int circle = 0; circle < 2; circle++) {
						x += circle * width;
						
						g.setColor(255, 255, 255, 255);
						g.fillCircle(x, y + height/2, height/2, 120);

						g.setLineSize(3.0f);
						g.setColor(48, 48, 48);
						g.drawCircle(x, y + height/2, height/2, 120);
						
						g.setLineSize(5.0f);
						for (int j = 0; j < 12; j++) {
							g.drawLine(x + (int)((height/2 - 5) * Math.sin(Math.toRadians(j * 30))), y + height/2 + (int)(-(height/2 - 5) * Math.cos(Math.toRadians(j * 30))), x + (int)((height/2) * Math.sin(Math.toRadians(j * 30))), y + height/2 + (int)(-(height/2) * Math.cos(Math.toRadians(j * 30))));
						}
						
						int angle = (int)(this.clockRotate + circle * 180) + i * 100;
						while (angle > 360) {
							angle -= 360;
						}
						g.drawLine(x, y + height/2, x + (int)((height/2 - 5) * Math.sin(Math.toRadians(angle))), y + height/2 + (int)(-(height/2 - 5) * Math.cos(Math.toRadians(angle))));

						g.setLineSize(1.0f);
					}
				}
			}
		}
	}

}
