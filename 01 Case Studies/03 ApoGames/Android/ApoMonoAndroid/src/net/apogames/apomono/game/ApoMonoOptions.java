
package net.apogames.apomono.game;

import net.apogames.apomono.ApoMonoConstants;
import net.apogames.apomono.ApoMonoModel;
import net.apogames.apomono.ApoMonoSoundPlayer;
import net.apogames.apomono.entity.ApoLevelChooserButton;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;

public class ApoMonoOptions  extends ApoMonoModel {

	public static final String BACK = "back";
	public static final String LANGUAGE_GERMAN = "german";
	public static final String LANGUAGE_ENGLISH = "english";
	public static final String COLOR_WHITE = "white";
	public static final String COLOR_GREEN = "green";
	public static final String SOUND = "sound";
	public static final String MUSIC = "music";
	
	public ApoMonoOptions(ApoMonoPanel game) {
		super(game);
	}

	@Override
	public void init() {
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
		if (function.equals(ApoMonoOptions.BACK)) {
			this.onBackButtonPressed();
		} else if (function.equals(ApoMonoOptions.LANGUAGE_GERMAN)) {
			ApoLevelChooserButton buttonGerman = (ApoLevelChooserButton)(this.getGame().getButtons()[12]);
			if (!buttonGerman.isSelected()) {
				buttonGerman.setSelected(true);
				((ApoLevelChooserButton)(this.getGame().getButtons()[13])).setSelected(false);
				ApoMonoConstants.changeLanguageToGerman();
			}
		} else if (function.equals(ApoMonoOptions.LANGUAGE_ENGLISH)) {
			ApoLevelChooserButton buttonEnglish = (ApoLevelChooserButton)(this.getGame().getButtons()[13]);
			if (!buttonEnglish.isSelected()) {
				buttonEnglish.setSelected(true);
				((ApoLevelChooserButton)(this.getGame().getButtons()[12])).setSelected(false);
				ApoMonoConstants.changeLanguageToEnglish();
			}
		} else if (function.equals(ApoMonoOptions.COLOR_WHITE)) {
			ApoLevelChooserButton buttonWhite = (ApoLevelChooserButton)(this.getGame().getButtons()[14]);
			if (!buttonWhite.isSelected()) {
				buttonWhite.setSelected(true);
				((ApoLevelChooserButton)(this.getGame().getButtons()[15])).setSelected(false);
				ApoMonoConstants.changeToWhiteColor();
			}
		} else if (function.equals(ApoMonoOptions.COLOR_GREEN)) {
			ApoLevelChooserButton buttonGreen = (ApoLevelChooserButton)(this.getGame().getButtons()[15]);
			if (!buttonGreen.isSelected()) {
				buttonGreen.setSelected(true);
				((ApoLevelChooserButton)(this.getGame().getButtons()[14])).setSelected(false);
				ApoMonoConstants.changeToGreenColor();
			}
		} else if (function.equals(ApoMonoOptions.SOUND)) {
			ApoLevelChooserButton buttonSound = (ApoLevelChooserButton)(this.getGame().getButtons()[21]);
			buttonSound.setSelected(!buttonSound.isSelected());
			this.getGame().setSound(buttonSound.isSelected());
		} else if (function.equals(ApoMonoOptions.MUSIC)) {
			ApoLevelChooserButton buttonMusic = (ApoLevelChooserButton)(this.getGame().getButtons()[22]);
			buttonMusic.setSelected(!buttonMusic.isSelected());
			this.getGame().setMusic(buttonMusic.isSelected());
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
			addY += 45;
		}
		this.getGame().drawString(g, ApoMonoConstants.OPTION_TITLE, (int)(240 - ApoMonoPanel.title_font.getLength(ApoMonoConstants.OPTION_TITLE)/2), 5 + addY, ApoMonoPanel.title_font);

		g.setColor(ApoMonoConstants.BRIGHT_DARK[0], ApoMonoConstants.BRIGHT_DARK[1], ApoMonoConstants.BRIGHT_DARK[2]);
		g.fillRect(5, 57 + addY, 420, 58);
		g.fillRect(5, 117 + addY, 420, 58);
		g.fillRect(5, 177 + addY, 420, 58);
		
		g.setColor(ApoMonoConstants.DARK[0], ApoMonoConstants.DARK[1], ApoMonoConstants.DARK[2]);
		g.drawRect(5, 57 + addY, 420, 58);
		g.drawRect(5, 117 + addY, 420, 58);
		g.drawRect(5, 177 + addY, 420, 58);
		this.getGame().drawString(g, ApoMonoConstants.OPTION_LANGUAGE, (int)(10), 78 + addY, ApoMonoPanel.game_font);
		this.getGame().drawString(g, ApoMonoConstants.OPTION_COLOR, (int)(10), 138 + addY, ApoMonoPanel.game_font);
		this.getGame().drawString(g, "audio", (int)(10), 198 + addY, ApoMonoPanel.game_font);
		
		String s = "deutsch";
		this.getGame().drawString(g, s, (int)(175), 78 + addY, ApoMonoPanel.game_font);
		s = "english";
		this.getGame().drawString(g, s, (int)(310), 78 + addY, ApoMonoPanel.game_font);
		
		s = ApoMonoConstants.OPTION_COLOR_WHITE;
		this.getGame().drawString(g, s, (int)(175), 138 + addY, ApoMonoPanel.game_font);
		s = ApoMonoConstants.OPTION_COLOR_GREEN;
		this.getGame().drawString(g, s, (int)(310), 138 + addY, ApoMonoPanel.game_font);
		
		s = ApoMonoConstants.OPTION_SOUND;
		this.getGame().drawString(g, s, (int)(175), 198 + addY, ApoMonoPanel.game_font);
		s = ApoMonoConstants.OPTION_MUSIC;
		this.getGame().drawString(g, s, (int)(310), 198 + addY, ApoMonoPanel.game_font);
		
		this.getGame().renderButtons(g);
	}
}
