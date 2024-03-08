package net.apogames.apomono.game;

import net.apogames.apomono.ApoMonoConstants;
import net.apogames.apomono.ApoMonoModel;
import net.apogames.apomono.ApoMonoSoundPlayer;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLFactory;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLImage;

public class ApoMonoCredits  extends ApoMonoModel {

	public static final String BACK = "back";
	public static final String TITLE = "Credits";
	public static final String SUB = "ApoMono is made by Dirk 'Apo' Aporius";
	public static final String SUB_2 = "made with the bits-engine by Marc Wiedenhoeft";
	
	public BitsGLImage credits;
	
	public ApoMonoCredits(ApoMonoPanel game) {
		super(game);
	}

	@Override
	public void init() {
		this.getStringWidth().put(ApoMonoCredits.TITLE, (int)(ApoMonoPanel.title_font.getLength(ApoMonoCredits.TITLE)));
		this.getStringWidth().put(ApoMonoCredits.SUB, (int)(ApoMonoPanel.game_font.getLength(ApoMonoCredits.SUB)));
		this.getStringWidth().put(ApoMonoCredits.SUB_2, (int)(ApoMonoPanel.game_font.getLength(ApoMonoCredits.SUB_2)));
		
		if (this.credits == null) {
			this.credits = BitsGLFactory.getInstance().getImage( "?net/gliblybits/bitsengine/BitsEngineLogo.png", BitsGLImage.FILTER_NEAREST, true );
//			BitsGLFactory.getInstance().markForLoading(this.credits);
			BitsGLFactory.getInstance().loadAllMarked();
		}
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
		if (function.equals(ApoMonoCredits.BACK)) {
			this.onBackButtonPressed();
		}
		this.getGame().playSound(ApoMonoSoundPlayer.SOUND_BUTTON_2);
	}
	
	public void onBackButtonPressed() {
		this.getGame().setMenu();
	}
	
	@Override
	public void think(int delta) {
		
	}

	@Override
	public void render(final BitsGLGraphics g) {
		int addY = 0;
		if (ApoMonoConstants.FREE_VERSION) {
			addY += 50;
		}
		this.getGame().drawString(g, ApoMonoCredits.TITLE, 240, 5 + addY, ApoMonoPanel.title_font);
		this.getGame().drawString(g, ApoMonoCredits.SUB, 240, 50 + addY, ApoMonoPanel.game_font);
		this.getGame().drawString(g, ApoMonoCredits.SUB_2, 240, 65 + addY, ApoMonoPanel.game_font);
		
		String s = "gameplay is based on";
		this.getGame().drawString(g, s, (int)(240 - ApoMonoPanel.game_font.getLength(s)/2), 100 + addY, ApoMonoPanel.game_font);
		s = "F.O.L.D. by Cosine";
		this.getGame().drawString(g, s, (int)(240 - ApoMonoPanel.game_font.getLength(s)/2), 115 + addY, ApoMonoPanel.game_font);
		s = "and";
		this.getGame().drawString(g, s, (int)(240 - ApoMonoPanel.game_font.getLength(s)/2), 130 + addY, ApoMonoPanel.game_font);
		s = "Blockdude by Brandon Sterner";
		this.getGame().drawString(g, s, (int)(240 - ApoMonoPanel.game_font.getLength(s)/2), 145 + addY, ApoMonoPanel.game_font);
		
		s = "music by";
		this.getGame().drawString(g, s, (int)(240 - ApoMonoPanel.game_font.getLength(s)/2), 175 + addY, ApoMonoPanel.game_font);
		s = "Eric Skiff, http://ericskiff.com/";
		this.getGame().drawString(g, s, (int)(240 - ApoMonoPanel.game_font.getLength(s)/2), 190 + addY, ApoMonoPanel.game_font);
		
		s = "thanks to";
		this.getGame().drawString(g, s, (int)(240 - ApoMonoPanel.game_font.getLength(s)/2), 220 + addY, ApoMonoPanel.game_font);
		s = "Florian, Network, Thecoolestnerdguy";
		this.getGame().drawString(g, s, (int)(5), 235 + addY, ApoMonoPanel.game_font);
		s = "Sparky83, Marc, Basti, Clemens";
		this.getGame().drawString(g, s, (int)(5), 250 + addY, ApoMonoPanel.game_font);
		
//		g.setColor(1f, 1f, 1f, 1f);
//		g.drawImage(this.credits, 200, 220, 80, 80);
		
		this.getGame().renderButtons(g);
	}
}