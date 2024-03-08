/*
 * Copyright (c) 2005-2013 Dirk Aporius <dirk.aporius@gmail.com>
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.apogames.mytreasure.game;

import java.util.HashMap;
import java.util.Map.Entry;

import net.gliblybits.bitsengine.graphics.bitmap.BitsBitmap;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLFactory;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLFont;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLImage;
import net.gliblybits.bitsengine.sound.BitsSound;

import android.content.SharedPreferences;

import com.apogames.mytreasure.MyTreasureActivity;
import com.apogames.mytreasure.MyTreasureComponent;
import com.apogames.mytreasure.MyTreasureConstants;
import com.apogames.mytreasure.MyTreasureMusicPlayer;
import com.apogames.mytreasure.MyTreasureSoundPlayer;
import com.apogames.mytreasure.R;
import com.apogames.mytreasure.entity.MyTreasureSolve;
import com.apogames.mytreasure.userlevels.MyTreasureLevels;
import com.apogames.mytreasure.userlevels.MyTreasureUserLevels;

public class MyTreasurePanel extends MyTreasureComponent {
	
	private MyTreasureMenu menu;

	private MyTreasureMap map;

	private MyTreasureGame game;

	private MyTreasureEditor editor;

	private MyTreasureCredits credits;

	private MyTreasureLevelChooser levelChooser;
	
	private MyTreasureOptions options;
	
	private MyTreasureUserLevels userlevels;
	
	private int curSkulls, think;
	
	private HashMap<String, Integer> solvedLevel;

	private MyTreasureSolve solver;
	
	private MyTreasureSoundPlayer soundPlayer;
	
	private MyTreasureMusicPlayer musicPlayer;
	
	private long oldTime;
	
	public MyTreasurePanel(int id) {
		super(id);
	}
	
	public void init() {
		super.init();
		
		BitsGLGraphics.setClearColor(45f/255f, 50f/255f, 38f/255f, 1f);
		
		MyTreasureConstants.font = BitsGLFactory.getInstance().getFont("font/pixel.TTF", 40, +6, BitsGLFont.FILTER_NEAREST, true);
		
		MyTreasureConstants.FONT_FPS = BitsGLFactory.getInstance().getFont("font/pixel.TTF", 10, +1, BitsGLFont.FILTER_NEAREST, true);
		MyTreasureConstants.FONT_STATISTICS = BitsGLFactory.getInstance().getFont("font/pixel.TTF", 19, +4, BitsGLFont.FILTER_NEAREST, true);
		MyTreasureConstants.FONT_LEVELCHOOSER = BitsGLFactory.getInstance().getFont("font/pixel.TTF", 30, +5, BitsGLFont.FILTER_NEAREST, true);
		
		MyTreasureConstants.fontBig = BitsGLFactory.getInstance().getFont("font/pixel.TTF", 33, +6, BitsGLFont.FILTER_NEAREST, true);
		MyTreasureConstants.fontMedium = BitsGLFactory.getInstance().getFont("font/pixel.TTF", 25, +4, BitsGLFont.FILTER_NEAREST, true);
		MyTreasureConstants.fontSmall = BitsGLFactory.getInstance().getFont("font/pixel.TTF", 17, +3, BitsGLFont.FILTER_NEAREST, true);
		MyTreasureConstants.fontVerySmall = BitsGLFactory.getInstance().getFont("font/pixel.TTF", 14, +2, BitsGLFont.FILTER_NEAREST, true);
		
		BitsBitmap bitmap = BitsGLFactory.getInstance().getBitmap(R.drawable.treasure_sheet);
		MyTreasureConstants.iSheet = BitsGLFactory.getInstance().getImage(bitmap, BitsGLImage.FILTER_NEAREST, true, true);

		MyTreasureConstants.iWays = BitsGLFactory.getInstance().getImage(R.drawable.ways, BitsGLImage.FILTER_NEAREST, true);
		
		MyTreasureButtons buttons = new MyTreasureButtons(this);
		buttons.init();
		
		if (this.soundPlayer == null) {
			this.soundPlayer = new MyTreasureSoundPlayer();
		}
		
		if (this.musicPlayer == null) {
			this.musicPlayer = new MyTreasureMusicPlayer(this);
		}
		
		if (this.menu == null) {
			this.menu = new MyTreasureMenu(this);
		}
		if (this.game == null) {
			this.game = new MyTreasureGame(this);
		}
		if (this.map == null) {
			this.map = new MyTreasureMap(this);
		}
		if (this.editor == null) {
			this.editor = new MyTreasureEditor(this);
		}
		if (this.credits == null) {
			this.credits = new MyTreasureCredits(this);
		}
		if (this.options == null) {
			this.options = new MyTreasureOptions(this);
		}
		if (this.levelChooser == null) {
			this.levelChooser = new MyTreasureLevelChooser(this);
			this.levelChooser.init();
		}
		if (this.userlevels == null) {
			this.userlevels = new MyTreasureUserLevels(this);
			this.loadUserlevels();
		}
		if (this.solver == null) {
			this.solver = new MyTreasureSolve(this.editor);
		}
		
		this.curSkulls = 0;
		if (this.solvedLevel == null) {
			this.solvedLevel = new HashMap<String, Integer>();
		}
		this.loadProperties();
		
		this.setMenu();
		
		this.playMusic();
	}
	
	public void stopGame() {
		this.stopMusic();
		this.saveProperties();
	}
	
	public final void loadProperties() {
		SharedPreferences settings = MyTreasureActivity.settings;
		MyTreasureConstants.FIRST_MAP = settings.getBoolean("first", true);
		MyTreasureConstants.FIRST_LEVELCHOOSER = settings.getBoolean("levelchooser_first", true);
		MyTreasureConstants.FIRST_LEVELCHOOSER_DRAG = settings.getBoolean("levelchooser_drag", true);
		MyTreasureConstants.FIRST_GAME = settings.getBoolean("first_game", true);
		MyTreasureConstants.SOUND_GAME = settings.getBoolean("sound_game", true);
		MyTreasureConstants.MUSIC_GAME = settings.getBoolean("music_game", true);
		MyTreasureConstants.LEVELCHOOSER_STEP = settings.getInt("levelchooser_step", 0);
		this.curSkulls = settings.getInt("skulls", 0);
		this.map.setOldLevel(this.curSkulls);
		this.map.setPlayerPosition(MyTreasureConstants.LEVELCHOOSER_STEP);
		int size = settings.getInt("size", 0);
		for (int i = 0; i < size; i++) {
			int skulls = settings.getInt(String.valueOf(i)+"skulls", 1);
			String level = settings.getString(String.valueOf(i)+"level", "");
			this.solvedLevel.put(level, skulls);
		}
		this.setMusic(MyTreasureConstants.MUSIC_GAME);
		this.setSound(MyTreasureConstants.SOUND_GAME);
	}
	
	public final void saveProperties() {
		SharedPreferences settings = MyTreasureActivity.settings;
		SharedPreferences.Editor editor = settings.edit();
		
		editor.putBoolean("first", MyTreasureConstants.FIRST_MAP);
		editor.putBoolean("levelchooser_first", MyTreasureConstants.FIRST_LEVELCHOOSER);
		editor.putBoolean("levelchooser_drag", MyTreasureConstants.FIRST_LEVELCHOOSER_DRAG);
		editor.putBoolean("first_game", MyTreasureConstants.FIRST_GAME);
		editor.putBoolean("sound_game", MyTreasureConstants.SOUND_GAME);
		editor.putBoolean("music_game", MyTreasureConstants.MUSIC_GAME);
		editor.putInt("levelchooser_step", MyTreasureConstants.LEVELCHOOSER_STEP);
		editor.putInt("skulls", this.curSkulls);
		int size = this.solvedLevel.size();
		editor.putInt("size", size);
		int i = 0;
		for (final Entry<String, Integer> entry : this.solvedLevel.entrySet()) {
			editor.putInt(String.valueOf(i)+"skulls", entry.getValue());
			editor.putString(String.valueOf(i)+"level", entry.getKey());
			i += 1;
		}
		
		editor.commit();
	}
	
	@Override
	public void onFinishScreen() {
		this.stopMusic();
		this.saveProperties();
	}

	
	public int getCurSkulls() {
		return this.curSkulls;
	}

	public void setCurSkulls(final int curSkulls) {
		this.curSkulls = curSkulls;
	}

	public HashMap<String, Integer> getSolvedLevel() {
		return this.solvedLevel;
	}
	
	public int getDifficulty() {
		return this.levelChooser.getDifficulty();
	}

	public final void setMenu() {
		if (this.getModel() != null) {
			this.getModel().close();
		}
		
		this.setModel(this.menu);
		
		this.setButtonVisible(MyTreasureConstants.BUTTON_MENU);
		this.setUserlevelsVisible();
		this.getModel().init();
	}
	
	public final void setMap() {
		if (this.getModel() != null) {
			this.getModel().close();
		}
		this.setModel(this.map);
		
		this.setButtonVisible(MyTreasureConstants.BUTTON_MAP);
		this.getModel().init();
	}
	
	public final void setLevelChooser(final int difficulty, final int level, final boolean bMap, final boolean bUserlevels) {
		if (this.getModel() != null) {
			this.getModel().close();
		}
		this.setModel(this.levelChooser);
		
		this.setButtonVisible(MyTreasureConstants.BUTTON_LEVELCHOOSER);
		this.getModel().init();
		
		if (!bUserlevels) {
			this.levelChooser.setDifficulty(difficulty, level, bMap);
		} else {
			this.levelChooser.setUserlevels();
		}
	}
	
	public final void setGame(final boolean bUserlevel, final boolean bEditor, final int levelInt, final String levelString) {
		if (this.getModel() != null) {
			this.getModel().close();
		}
		
		this.setModel(this.game);
		
		this.setButtonVisible(MyTreasureConstants.BUTTON_GAME);

		this.game.setUserLevel(bUserlevel);
		this.getModel().init();
		
		this.game.loadLevel(bEditor, levelInt, levelString);
	}
	
	public final void setEditor(boolean bSolved, int steps) {
		if (this.getModel() != null) {
			this.getModel().close();
		}
		this.setModel(this.editor);
		
		this.setButtonVisible(MyTreasureConstants.BUTTON_EDITOR);
		
		this.getModel().init();
		
		this.editor.setSteps(steps);
		this.editor.setVisibleUpload(bSolved);
	}
	
	public final void setCredits() {
		if (this.getModel() != null) {
			this.getModel().close();
		}
		
		this.setModel(this.credits);
		
		this.setButtonVisible(MyTreasureConstants.BUTTON_CREDITS);
		
		this.getModel().init();
	}
	
	public final void setOptions() {
		if (this.getModel() != null) {
			this.getModel().close();
		}
		
		this.setModel(this.options);
		
		this.setButtonVisible(MyTreasureConstants.BUTTON_OPTIONS);
		
		this.getModel().init();
	}
	
	public final void setMusic(final boolean bMusic) {
		this.getButtons()[25].setSelected(!bMusic);
		this.getButtons()[24].setSelected(bMusic);
		
		MyTreasureConstants.MUSIC_GAME = bMusic;
		this.playMusic();
	}
	
	public void playMusic() {
		if (MyTreasureConstants.MUSIC_GAME) {
			this.musicPlayer.load();
		} else {
			this.musicPlayer.stop();
		}
	}
	
	public void stopMusic() {
		this.musicPlayer.stop();
	}
	
	public final void setSound(final boolean bSound) {
		this.getButtons()[26].setSelected(!bSound);
		this.getButtons()[23].setSelected(bSound);
		
		MyTreasureConstants.SOUND_GAME = bSound;
		this.playSound(MyTreasureSoundPlayer.SOUND_CLICK);
	}
	
	public void playSound(BitsSound sound) {
		if (MyTreasureConstants.SOUND_GAME) {
			this.soundPlayer.playSound(sound);
		}
	}
	
	public boolean isMusicOn() {
		return false;
	}
	
	public int getCurStart() {
		return this.levelChooser.getCurI();
	}
	
	public int getCurMax() {
		return this.levelChooser.getCurMax();
	}

	public final void setUserlevelsVisible() {
		if (this.getModel().equals(this.menu)) {
			if (MyTreasureLevels.editorLevels != null) {
				super.getButtons()[2].setVisible(true);
			} else {
				super.getButtons()[2].setVisible(false);
			}
		}
	}
	
	public final void setButtonVisible(boolean[] bVisibile) {
		for (int i = 0; i < this.getButtons().length && i < bVisibile.length; i++) {
			this.getButtons()[i].setVisible(bVisibile[i]);
		}
	}
	
	public MyTreasureUserLevels getUserlevels() {
		return this.userlevels;
	}

	public final void loadUserlevels() {
		Thread t = new Thread(new Runnable() {

			public void run() {
				MyTreasurePanel.this.userlevels.loadUserlevels();
			}
 		});
 		t.start();
	}
	
	public final void doSolveLevel(final String level) {
		String solution = this.solver.solveLevel(level);
		if (this.getModel().equals(this.editor)) {
			this.editor.setSolutionString(solution);
		}
	}
	
	@Override
	public void onBackButtonPressed() {
		if (super.getModel() != null) {
			super.getModel().onBackButtonPressed();
		}
	}
	
	@Override
	public void onResumeScreen() {
//		BitsBitmap bitmap = BitsGLFactory.getInstance().getBitmap(R.drawable.treasure_sheet);
//		MyTreasureConstants.iSheet = BitsGLFactory.getInstance().getImage(bitmap, BitsGLImage.FILTER_NEAREST, true, true);
		if (super.getModel() != null) {
			super.getModel().onResume();
			this.playMusic();
		}
	}
	
	@Override
	public void onPauseScreen() {
		if (this.solvedLevel != null) {
			this.stopMusic();
			this.saveProperties();
		}
	}
	
	@Override
	public void setButtonFunction(final String function) {
		if (super.getModel() != null) {
			super.getModel().touchedButton(function);
		}
	}
	
	@Override
	public void onUpdate(float delta) {
		super.onUpdate(delta);
		
		//long curTime = System.nanoTime();
		//long t = curTime - this.oldTime;
		
		this.think += delta;// t / 1000000;
		
//		this.think += delta * 1000;
		
		// Update / think
		// Wenn 10 ms vergangen sind, dann denke nach
		while (this.think >= 10) {
			this.think -= 10;
			if (super.getModel() != null) {
				super.getModel().think((int)10);
			}	
		}
		
		//this.oldTime = curTime;
	}

	@Override
	public void onDrawFrame(BitsGLGraphics g) {
		g.setBlendFunc(BitsGLGraphics.BLENDFUNC_ONE, BitsGLGraphics.BLENDFUNC_ONE_MINUS_SRC_ALPHA);
		g.setColor(1f, 1f, 1f, 1f);
		if (super.getModel() != null) {
			super.getModel().render(g);
		}
		g.setColor(1f, 1f, 1f, 1f);
		super.renderButtons(g);
		if (super.getModel() != null) {
			super.getModel().drawOverlay(g);
		}
		if (MyTreasureConstants.FPS) {
			g.setColor(MyTreasureConstants.COLOR_LIGHT[0], MyTreasureConstants.COLOR_LIGHT[1], MyTreasureConstants.COLOR_LIGHT[2], 1f);
			g.setFont(MyTreasureConstants.FONT_STATISTICS);
			g.drawFPS(5, 40);
		}
	}
	
	public void renderBackgroundInfo(BitsGLGraphics g) {
		
	}

}
