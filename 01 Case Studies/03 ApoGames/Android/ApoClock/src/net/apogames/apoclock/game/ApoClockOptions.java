package net.apogames.apoclock.game;

import net.apogames.apoclock.ApoClockModel;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;
import net.gliblybits.bitsengine.input.BitsInput;
import net.gliblybits.bitsengine.input.BitsKeyEvent;

public class ApoClockOptions extends ApoClockModel {

	public static final String BACK = "back";
	public static final String TITLE = "ApoClock - Options";

	private float clockRotate;
	
	private int textX;
	
	public ApoClockOptions(ApoClockPanel game) {
		super(game);
	}

	@Override
	public void init() {
		this.getStringWidth().put(ApoClockOptions.BACK, (int)(ApoClockPanel.font.getLength(ApoClockOptions.BACK)));
		this.getStringWidth().put(ApoClockOptions.TITLE, (int)(ApoClockPanel.title_font.getLength(ApoClockOptions.TITLE)));
		
		String s = "Name: ";
		this.textX = (int)(85 + ApoClockPanel.game_font.getLength(s));
	}

	@Override
	public void touchedPressed(int x, int y, int finger) {
		if ((x > this.textX) && (x < this.textX + this.getGame().getTextfield().getWidth()) &&
			(y > 120) && (y < 120 + this.getGame().getTextfield().getHeight())) {
			if (!this.getGame().getTextfield().isSelect()) {
				this.getGame().getTextfield().setSelect(true);
				BitsInput.getInstance().setVirtualKeyboardVisible(true);
			}
		} else {
			if (this.getGame().getTextfield().isSelect()) {
				BitsInput.getInstance().setVirtualKeyboardVisible(false);
				this.getGame().savePreferences();
			}
			this.getGame().getTextfield().setSelect(false);
		}
	}

	@Override
	public void touchedReleased(int x, int y, int finger) {
		
	}

	@Override
	public void touchedDragged(int x, int y, int oldX, int oldY, int finger) {
		
	}

	@Override
	public void touchedButton(String function) {
		if (function.equals(ApoClockOptions.BACK)) {
			this.onBackButtonPressed();
		}
	}
	
	public void onBackButtonPressed() {
		this.getGame().setMenu();
	}
	
	public void onKeyDown(final int key) {
	}

	public void onKeyUp(final int key, final BitsKeyEvent event) {
		
	}

	@Override
	public void think(int delta) {
		this.clockRotate += delta / 10f;
		if (this.clockRotate >= 360) {
			this.clockRotate -= 360;
		}
	}

	@Override
	public void render(BitsGLGraphics g) {
		this.getGame().drawString(g, ApoClockOptions.TITLE, 240, 2, ApoClockPanel.title_font, new float[] {1, 1, 1, 1}, new float[] {0, 0, 0, 1});
		
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
		
		g.setColor(0.0f, 0.0f, 0.0f, 1.0f);
		String s = "Name: ";
		g.drawText(s, 85, 123);
		this.getGame().getTextfield().render(g, this.textX, 120);
		
		g.setFont(ApoClockPanel.game_font);
		s = "ApoClock is made by Dirk 'Apo' Aporius";
		g.drawText(s, 240 - ApoClockPanel.game_font.getLength(s)/2, 500);
		s = "made with the bits-engine by Marc Wiedenhoeft";
		g.drawText(s, 240 - ApoClockPanel.game_font.getLength(s)/2, 530);
	}

}