package net.apogames.apomono.game;

//import com.google.ads.AdView;

import com.google.ads.AdView;

import android.content.SharedPreferences;
import net.apogames.apomono.ApoMonoActivity;
import net.apogames.apomono.ApoMonoButtons;
import net.apogames.apomono.ApoMonoComponent;
import net.apogames.apomono.ApoMonoConstants;
import net.apogames.apomono.ApoMonoMusicPlayer;
import net.apogames.apomono.ApoMonoSoundPlayer;
import net.apogames.apomono.entity.ApoLevelChooserButton;
import net.apogames.apomono.level.ApoMonoLevel;
import net.apogames.apomono.userlevels.ApoMonoSave;
import net.apogames.apomono.userlevels.ApoMonoUserlevels;
import net.gliblybits.bitsengine.graphics.BitsFont;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLFactory;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLFont;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;
import net.gliblybits.bitsengine.sound.BitsSound;

public class ApoMonoPanel extends ApoMonoComponent {

	public static BitsGLFont font;
	public static BitsGLFont game_font;
	public static BitsGLFont title_font;

	private ApoMonoMenu menu;
	
	private ApoMonoPuzzleGame game;

	private ApoMonoEditor editor;

	private ApoMonoCredits credits;

	private ApoMonoOptions options;

	private ApoMonoLevelChooser levelChooser;
	
	private int think;
	
	private ApoMonoUserlevels userlevels;
	
	private ApoMonoSave solvedLevels;
	
	private boolean bSound, bMusic;
	
	private ApoMonoMusicPlayer musicPlayer;
	
	private ApoMonoSoundPlayer soundPlayer;
	
	public ApoMonoPanel(int id) {
		super(id);
	}
	
	public void init() {
		super.init();
		
		BitsGLGraphics.setClearColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		
		ApoMonoButtons buttons = new ApoMonoButtons(this);
		buttons.init();
		
		this.think = 0;
		
		if (this.menu == null) {
			this.menu = new ApoMonoMenu(this);
		}
		if (this.game == null) {
			this.game = new ApoMonoPuzzleGame(this);
		}
		if (this.editor == null) {
			this.editor = new ApoMonoEditor(this);
		}
		if (this.credits == null) {
			this.credits = new ApoMonoCredits(this);
		}
		if (this.options == null) {
			this.options = new ApoMonoOptions(this);
		}
		if (this.levelChooser == null) {
			this.levelChooser = new ApoMonoLevelChooser(this);
		}
		
		if (this.userlevels == null) {
			this.userlevels = new ApoMonoUserlevels(this);
			this.loadUserlevels();
		}
		
		if (this.solvedLevels == null) {
			this.solvedLevels = new ApoMonoSave();
		}
		if (this.musicPlayer == null) {
			this.musicPlayer = new ApoMonoMusicPlayer(this);
		}
		if (this.soundPlayer == null) {
			this.soundPlayer = new ApoMonoSoundPlayer();
		}
		
		this.loadFonts();

		this.loadPreferences();

		this.setMenu();
	}
	
	private void loadFonts() {
		ApoMonoPanel.font = BitsGLFactory.getInstance().getFont("res/font/font.ttf", 24, +4, BitsGLFont.FILTER_NEAREST, true);
//		ApoMonoPanel.font.mFilterMode = BitsGLFont.FILTER_NEAREST;
//		BitsGLFactory.getInstance().markForLoading(font);
		
		ApoMonoPanel.title_font = BitsGLFactory.getInstance().getFont("res/font/font.ttf", 36, +6, BitsGLFont.FILTER_NEAREST, true);
//		ApoMonoPanel.title_font.mFilterMode = BitsGLFont.FILTER_NEAREST;
//		BitsGLFactory.getInstance().markForLoading(title_font);
			
		ApoMonoPanel.game_font = BitsGLFactory.getInstance().getFont("res/font/font.ttf", 18, +3, BitsGLFont.FILTER_NEAREST, true);
//		ApoMonoPanel.game_font.mFilterMode = BitsGLFont.FILTER_NEAREST;
//		BitsGLFactory.getInstance().markForLoading(game_font);
		
		BitsGLFactory.getInstance().loadAllMarked();
	}

	
	protected final void loadPreferences() {
		SharedPreferences settings = ApoMonoActivity.settings;
		boolean english = settings.getBoolean("language", true);
		boolean colorGreen = settings.getBoolean("color", true);
		boolean bSound = settings.getBoolean("sound", true);
		boolean bMusic = settings.getBoolean("music", true);
		this.loadOptions(english, colorGreen, bSound, bMusic);
		
		String solvedLevels = settings.getString("solvedLevels", "");
		this.solvedLevels.createArrayFromString(solvedLevels);

//		ApoMonoConstants.FREE_VERSION = !settings.getBoolean("premium", false);
	}
	
	private void loadOptions(boolean bEnglish, boolean bColorGreen, boolean bSound, boolean bMusic) {
		((ApoLevelChooserButton)(this.getButtons()[12])).setSelected(!bEnglish);
		((ApoLevelChooserButton)(this.getButtons()[13])).setSelected(bEnglish);
		((ApoLevelChooserButton)(this.getButtons()[14])).setSelected(!bColorGreen);
		((ApoLevelChooserButton)(this.getButtons()[15])).setSelected(bColorGreen);
		
		if (bEnglish) {
			ApoMonoConstants.changeLanguageToEnglish();
		} else {
			ApoMonoConstants.changeLanguageToGerman();			
		}
		
		if (bColorGreen) {
			ApoMonoConstants.changeToGreenColor();
		} else {
			ApoMonoConstants.changeToWhiteColor();			
		}
		

		((ApoLevelChooserButton)(this.getButtons()[21])).setSelected(bSound);
		((ApoLevelChooserButton)(this.getButtons()[22])).setSelected(bMusic);
		this.setSound(bSound);
		this.setMusic(bMusic);
	}
	
	public boolean isMusicOn() {
		return bMusic;
	}

	public void setSound(boolean bSound) {
		this.bSound = bSound;
	}
	
	public void playSound(BitsSound sound) {
		if (this.bSound) {
			this.soundPlayer.playSound(sound);
		}
	}
	
	public void setMusic(boolean bMusic) {
		this.bMusic = bMusic;
		
		if (this.bMusic) {
			this.musicPlayer.load();
		} else {
			this.musicPlayer.stop();
		}
	}
	
	protected final void savePreferences() {
		SharedPreferences settings = ApoMonoActivity.settings;
		SharedPreferences.Editor editor = settings.edit();
		boolean bEnglish = true;
		if (((ApoLevelChooserButton)(this.getButtons()[12])).isSelected()) {
			bEnglish = false;
		}
		editor.putBoolean("language", bEnglish);
		boolean bColorGreen = true;
		if (((ApoLevelChooserButton)(this.getButtons()[14])).isSelected()) {
			bColorGreen = false;
		}
		editor.putBoolean("color", bColorGreen);
		
		boolean bSound = true;
		if (!((ApoLevelChooserButton)(this.getButtons()[21])).isSelected()) {
			bSound = false;
		}
		editor.putBoolean("sound", bSound);
		
		boolean bMusic = true;
		if (!((ApoLevelChooserButton)(this.getButtons()[22])).isSelected()) {
			bMusic = false;
		}
		editor.putBoolean("music", bMusic);
		
		editor.putString("solvedLevels", this.solvedLevels.getSaveString());

		editor.putBoolean("premium", !ApoMonoConstants.FREE_VERSION);
		
		editor.commit();
	}
	
	@Override
	public void onFinishScreen() {
		this.savePreferences();
	}
	
	protected final void setMenu() {
		if (super.getModel() != null) {
			super.getModel().close();
		}
		
		super.setModel(this.menu);
		
		this.setButtonVisible(ApoMonoConstants.BUTTON_MENU);
		
		if ((ApoMonoLevel.editorLevels != null) && (ApoMonoLevel.editorLevels.length > 0)) {
			this.getButtons()[8].setVisible(true);
		}
		if (ApoMonoConstants.FREE_VERSION) {
			this.changeAdViewVisibility(AdView.VISIBLE);
		} else {
			this.changeAdViewVisibility(AdView.INVISIBLE);			
		}
		
		super.getModel().init();
		this.musicPlayer.setMenu(true);
	}
	
	protected final void setGame(final int level, final String levelString, final boolean bUserlevel) {
		if (super.getModel() != null) {
			super.getModel().close();
		}
		
		super.setModel(this.game);
		
		this.setButtonVisible(ApoMonoConstants.BUTTON_GAME);
		
		super.getModel().init();
		
		this.changeAdViewVisibility(AdView.INVISIBLE);
		
		this.game.loadLevel(level, bUserlevel, levelString);
		this.musicPlayer.setMenu(false);

	}
	
	protected final void setEditor(boolean bUpload) {
		if (super.getModel() != null) {
			super.getModel().close();
		}
		
		super.setModel(this.editor);
		
		this.setButtonVisible(ApoMonoConstants.BUTTON_EDITOR);
		this.editor.setUploadVisible(bUpload);
		
		super.getModel().init();
		
		this.changeAdViewVisibility(AdView.INVISIBLE);
		this.musicPlayer.setMenu(true);
	}
	
	protected final void setCredits() {
		if (super.getModel() != null) {
			super.getModel().close();
		}
		
		super.setModel(this.credits);
		
		this.setButtonVisible(ApoMonoConstants.BUTTON_CREDITS);
		
		super.getModel().init();
		
		if (ApoMonoConstants.FREE_VERSION) {
			this.changeAdViewVisibility(AdView.VISIBLE);
		} else {
			this.changeAdViewVisibility(AdView.INVISIBLE);			
		}
		this.musicPlayer.setMenu(true);
	}
	
	protected final void setLevelChooser() {
		if (super.getModel() != null) {
			super.getModel().close();
		}
		
		super.setModel(this.levelChooser);
		
		this.setButtonVisible(ApoMonoConstants.BUTTON_LEVELCHOOSER);
		
		super.getModel().init();
		
		if (ApoMonoConstants.FREE_VERSION) {
			this.changeAdViewVisibility(AdView.VISIBLE);
		} else {
			this.changeAdViewVisibility(AdView.INVISIBLE);			
		}
		this.musicPlayer.setMenu(true);
	}
	
	protected final void setOptions() {
		if (super.getModel() != null) {
			super.getModel().close();
		}
		
		super.setModel(this.options);
		
		this.setButtonVisible(ApoMonoConstants.BUTTON_OPTIONS);
		
		super.getModel().init();
		
		if (ApoMonoConstants.FREE_VERSION) {
			this.changeAdViewVisibility(AdView.VISIBLE);
		} else {
			this.changeAdViewVisibility(AdView.INVISIBLE);			
		}
		this.musicPlayer.setMenu(true);
	}
	
	private void changeAdViewVisibility(final int visibility) {
		if (ApoMonoActivity.adView != null) {
			ApoMonoActivity.activity.runOnUiThread(new Runnable() {
			    public void run(){ 
			    	ApoMonoActivity.adView.setVisibility(visibility);
			    }
			});
		}
	}
	
	public final void setButtonVisible(boolean[] bVisibile) {
		for (int i = 0; i < this.getButtons().length && i < bVisibile.length; i++) {
			this.getButtons()[i].setVisible(bVisibile[i]);
		}
	}
	
	public ApoMonoSave getSolvedLevels() {
		return this.solvedLevels;
	}

	@Override
	public void setButtonFunction(final String function) {
		if (super.getModel() != null) {
			super.getModel().touchedButton(function);
		}
	}
	
	public final void loadUserlevels() {
		Thread t = new Thread(new Runnable() {

			public void run() {
				ApoMonoPanel.this.userlevels.loadUserlevels();
			}
 		});
 		t.start();
	}
	
	public ApoMonoUserlevels getUserlevels() {
		return this.userlevels;
	}

	public void setUserlevelsVisible() {
		if (this.getModel() instanceof ApoMonoMenu) {
			this.getButtons()[8].setVisible(true);
		}
	}
	
	protected void solveLevel(int level) {
		this.solvedLevels.setLevelToSolved(level);
				
		this.savePreferences();
		
		this.levelChooser.solveLevel(level);
	}
	
	public int getMaxCanChoosen() {
		int value = this.solvedLevels.solvedCout + 2;
		if (value >= ApoMonoLevel.MAX_LEVELS) {
			value = ApoMonoLevel.MAX_LEVELS -1;
		}
		return value;
	}
	
	@Override
	public void onResumeScreen() {
		if (super.getModel() != null) {
			super.getModel().onResume();
		}
		
		if (this.musicPlayer != null) {
			this.setMusic(this.bMusic);
		}
	}
	
	@Override
	public void onPauseScreen() {
		if (this.musicPlayer != null) {
			this.musicPlayer.stop();
			this.musicPlayer.release();
		}
	}
	
	@Override
	public void onBackButtonPressed() {
		if (super.getModel() != null) {
			super.getModel().onBackButtonPressed();
		}
	}
	
	@Override
	public void onUpdate(float delta) {
		super.onUpdate(delta);
		
		this.think += delta;
		
		// Update / think
		// Wenn 10 ms vergangen sind, dann denke nach
		while (this.think >= 10) {
			this.think -= 10;
			if (super.getModel() != null) {
				super.getModel().think((int)10);
			}	
		}
	}

	@Override
	public void onDrawFrame(BitsGLGraphics g) {
		if (super.getModel() != null) {
			super.getModel().render(g);
		}
		super.renderButtons(g);
		if (super.getModel() != null) {
			super.getModel().drawOverlay(g);
		}
		if (ApoMonoConstants.FPS) {
			g.setColor(ApoMonoConstants.DARK[0], ApoMonoConstants.DARK[1], ApoMonoConstants.DARK[2], 1f);
			g.setFont(ApoMonoPanel.game_font);
			g.drawFPS(5, ApoMonoPuzzleGame.changeY);
		}
	}

	public void drawString(final BitsGLGraphics g, final String s, final int x, final int y, final BitsFont font) {
		this.drawString(g, s, x, y, font, ApoMonoConstants.DARK, false);
	}
	
	public void drawString(final BitsGLGraphics g, final String s, final int x, final int y, final BitsFont font, boolean bShadow) {
		this.drawString(g, s, x, y, font, ApoMonoConstants.DARK, bShadow);
	}

	public void drawString(final BitsGLGraphics g, final String s, final int x, final int y, final BitsFont font, float[] colorFront) {
		this.drawString(g, s, x, y, font, colorFront, false);
	}
	
	public void drawString(final BitsGLGraphics g, final String s, final int x, final int y, final BitsFont font, float[] colorFront, boolean bShadow) {
		int w = 0;
		if (super.getModel().getStringWidth().containsKey(s)) {
			w = super.getModel().getStringWidth().get(s);
		}
		
		float alpha = 1;
		if (colorFront.length > 3) {
			alpha = colorFront[3];
		}
		g.setFont(font);
		if (bShadow) {
			g.setColor(colorFront[0], colorFront[1], colorFront[2], alpha);
			if (colorFront[0] > 0.5f) {
				ApoMonoPuzzleGame.setDarkerColor(g);
				ApoMonoPuzzleGame.setDarkerColor(g);
			} else {
				ApoMonoPuzzleGame.setBrighterColor(g);
			}
			g.drawText(s, x - w/2 + 1, y + 1);
		}
		g.setColor(colorFront[0], colorFront[1], colorFront[2], alpha);
		g.drawText(s, x - w/2 + 0, y + 0);
	}
	
	protected void drawButtons(final BitsGLGraphics g, final BitsGLFont font, final int valueDif) {
		if (this.getButtons() != null) {
			for (int i = 0; i < this.getButtons().length; i++) {
				this.drawButtons(g, font, i, valueDif);
			}
		}
	}
	
	protected void drawButtons(final BitsGLGraphics g, final BitsGLFont font, final int i, final int valueDif) {
		if (this.getButtons()[i].isBVisible()) {
			int x = (int)(this.getButtons()[i].getX());
			int y = (int)(this.getButtons()[i].getY());
			int width = (int)(this.getButtons()[i].getWidth());
			int height = (int)(this.getButtons()[i].getHeight());
			
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
			
			String s = this.getButtons()[i].getFunction();
			this.drawString(g, s, (int)(x + width/2 - font.getLength(s)/2), y + height/2 - font.mCharCellHeight/2 - valueDif, font, ApoMonoConstants.BRIGHT);
		}
	}
}
