package net.apogames.apomono.game;

import net.apogames.apomono.ApoMonoConstants;
import net.apogames.apomono.ApoMonoModel;
import net.apogames.apomono.ApoMonoSoundPlayer;
import net.apogames.apomono.entity.ApoLevelChooserButton;
import net.gliblybits.bitsengine.core.BitsGame;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;

public class ApoMonoMenu extends ApoMonoModel {

	public static final String QUIT = "quit";
	public static final String PLAY = "play";
	public static final String EDITOR = "editor";
	public static final String CREDITS = "credits";
	public static final String OPTIONS = "o";
	public static final String USERLEVELS = "userlevels";
	public static final String TITLE = "ApoMono";
	public static final String SUB = "a game made by Dirk Aporius";
	public static final String SUB_2 = "made with the bits-engine by Marc Wiedenhoeft";
	
	public ApoMonoMenu(ApoMonoPanel game) {
		super(game);
	}

	@Override
	public void init() {
		this.getStringWidth().put(ApoMonoMenu.TITLE, (int)(ApoMonoPanel.title_font.getLength(ApoMonoMenu.TITLE)));
		this.getStringWidth().put(ApoMonoMenu.SUB, (int)(ApoMonoPanel.game_font.getLength(ApoMonoMenu.SUB)));
		this.getStringWidth().put(ApoMonoMenu.SUB_2, (int)(ApoMonoPanel.game_font.getLength(ApoMonoMenu.SUB_2)));
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
		if (function.equals(ApoMonoMenu.QUIT)) {
			this.onBackButtonPressed();
		} else if (function.equals(ApoMonoMenu.PLAY)) {
			this.getGame().setLevelChooser();
		} else if (function.equals(ApoMonoMenu.EDITOR)) {
			this.getGame().setEditor(false);
		} else if (function.equals(ApoMonoMenu.USERLEVELS)) {
			this.getGame().setGame(0, null, true);
		} else if (function.equals(ApoMonoMenu.CREDITS)) {
			this.getGame().setCredits();
		} else if (function.equals(ApoMonoMenu.OPTIONS)) {
			this.getGame().setOptions();
		}
		this.getGame().playSound(ApoMonoSoundPlayer.SOUND_BUTTON);
	}
	
	public void onBackButtonPressed() {
		BitsGame.getInstance().finishApp();
	}
	
	@Override
	public void think(int delta) {
		
	}

	@Override
	public void render(final BitsGLGraphics g) {
		if (ApoMonoConstants.FREE_VERSION) {
			this.getGame().drawString(g, ApoMonoMenu.TITLE, 240, 55, ApoMonoPanel.title_font);			
		} else {
			this.getGame().drawString(g, ApoMonoMenu.TITLE, 240, 5, ApoMonoPanel.title_font);
			this.getGame().drawString(g, ApoMonoMenu.SUB, 240, 50, ApoMonoPanel.game_font);
			this.getGame().drawString(g, ApoMonoMenu.SUB_2, 240, 65, ApoMonoPanel.game_font);
		}
		
		if (this.getGame().getButtons() != null) {
			for (int i = 0; i < this.getGame().getButtons().length; i++) {
				if (this.getGame().getButtons()[i].isBVisible()) {
					int x = (int)(this.getGame().getButtons()[i].getX());
					int y = (int)(this.getGame().getButtons()[i].getY());
					int width = (int)(this.getGame().getButtons()[i].getWidth());
					int height = (int)(this.getGame().getButtons()[i].getHeight());
					String s = this.getGame().getButtons()[i].getFunction();
					
					ApoLevelChooserButton.drawOneBlock(g, x, y, width, height);
					
					if (s.equals(ApoMonoMenu.CREDITS)) {
						s = "c";
						this.getGame().drawString(g, s,(int)(x + width/2 - ApoMonoPanel.font.getLength(s)/2), y + height/2 - ApoMonoPanel.font.mCharCellHeight/2, ApoMonoPanel.font, ApoMonoConstants.BRIGHT);
					} else if (s.equals(ApoMonoMenu.QUIT)) {
//						s = "x";
//						this.getGame().drawString(g, s,(int)(x + width/2 - ApoMonoMenu.game_font.getLength(s)/2), y + height/2 - ApoMonoMenu.game_font.mCharCellHeight/2, ApoMonoMenu.game_font, ApoMonoConstants.BRIGHT);
						ApoMonoPuzzleGame.drawX(g, x + width/2 - 16, y + height/2 - 16, true, ApoMonoConstants.BRIGHT, false, false);
					} else if (s.equals(ApoMonoMenu.PLAY)) {
						s = ApoMonoConstants.MENU_PLAY;
						this.getGame().drawString(g, s,(int)(x + width/2 - ApoMonoPanel.font.getLength(s)/2), y + height/2 - ApoMonoPanel.font.mCharCellHeight/2, ApoMonoPanel.font, ApoMonoConstants.BRIGHT);
					} else if (s.equals(ApoMonoMenu.OPTIONS)) {
//						s = "o";
//						this.getGame().drawString(g, s,(int)(x + width/2 - ApoMonoMenu.game_font.getLength(s)/2), y + height/2 - ApoMonoMenu.game_font.mCharCellHeight/2, ApoMonoMenu.game_font, ApoMonoConstants.BRIGHT);
						this.drawOptions(g, x, y, width, height);
					} else if (s.equals(ApoMonoMenu.EDITOR)) {
						s = ApoMonoConstants.MENU_EDITOR;
						this.getGame().drawString(g, s,(int)(x + width/2 - ApoMonoPanel.font.getLength(s)/2), y + height/2 - ApoMonoPanel.font.mCharCellHeight/2, ApoMonoPanel.font, ApoMonoConstants.BRIGHT);
					} else if (s.equals(ApoMonoMenu.USERLEVELS)) {
						s = ApoMonoConstants.MENU_USERLEVELS;
						this.getGame().drawString(g, s,(int)(x + width/2 - ApoMonoPanel.font.getLength(s)/2), y + height/2 - ApoMonoPanel.font.mCharCellHeight/2, ApoMonoPanel.font, ApoMonoConstants.BRIGHT);
					}
				}
			}
		}
	}
	
	private final void drawOptions(final BitsGLGraphics g, final int x, final int y, final int width, final int height) {
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		g.setLineSize(1 * ApoMonoConstants.MAX);
		g.fillCircle(x + width/2 + 1, y + height/2, 12);
		g.setColor(ApoMonoConstants.DARK[0], ApoMonoConstants.DARK[1], ApoMonoConstants.DARK[2], 1f);
		g.fillCircle(x + width/2 + 1, y + height/2, 8);
		g.setLineSize(1 * ApoMonoConstants.MAX);
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		for (int i = 0; i < 360; i+= 45) {
			if (i == 0) {
				g.setRotation(0.01f);				
			} else {
				g.setRotation(i);
			}
			float posX = x + width/2 + 13 * (float)Math.sin(Math.toRadians(i));
			float posY = y + 5f + height/2 - 13 * (float)Math.cos(Math.toRadians(i));
			g.fillRect(posX - 2, posY - 10, 5, 10);
		}
		g.setRotation(0);
	}

}
