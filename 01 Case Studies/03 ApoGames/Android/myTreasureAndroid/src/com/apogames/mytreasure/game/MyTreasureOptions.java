package com.apogames.mytreasure.game;

import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;

import com.apogames.mytreasure.MyTreasureConstants;
import com.apogames.mytreasure.MyTreasureModel;
import com.apogames.mytreasure.MyTreasureSoundPlayer;

public class MyTreasureOptions extends MyTreasureModel {

	public static final String BACK = "back";
	public static final String MUSIC = "music";
	public static final String SOUND = "sound";
	
	public MyTreasureOptions(final MyTreasurePanel game) {
		super(game);
	}

	@Override
	public void init() {
	}

	@Override
	public void touchedButton(String function) {
		if (function.equals(MyTreasureOptions.BACK)) {
			this.getGame().playSound(MyTreasureSoundPlayer.SOUND_CLICK);
			this.onBackButtonPressed();
		} else if (function.equals(MyTreasureOptions.MUSIC)) {
			this.getGame().setMusic(!this.getGame().getButtons()[24].isSelected());
		} else if (function.equals(MyTreasureOptions.SOUND)) {
			this.getGame().setSound(!this.getGame().getButtons()[23].isSelected());
		}
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
	
	public void onBackButtonPressed() {
		this.getGame().setMenu();
	}

	
	@Override
	public void think(int delta) {
	}


	@Override
	public void render(BitsGLGraphics g) {
		g.cropImage(MyTreasureConstants.iSheet, 0, 0, MyTreasureConstants.GAME_WIDTH, MyTreasureConstants.GAME_HEIGHT, 1024 - MyTreasureConstants.GAME_WIDTH, 0, MyTreasureConstants.GAME_WIDTH, MyTreasureConstants.GAME_HEIGHT);

		g.cropImage(MyTreasureConstants.iSheet, 2 * 4, 60, 76 * 4, 8 * 4, 0, 96 * 4, 76 * 4, 8 * 4);
		g.cropImage(MyTreasureConstants.iSheet, 2 * 4, 390, 76 * 4, 8 * 4, 0, 96 * 4, 76 * 4, 8 * 4);
		
		g.setFont(MyTreasureConstants.FONT_LEVELCHOOSER);
		int y = MyTreasureConstants.FONT_LEVELCHOOSER.mCharCellHeight;
		g.setColor(MyTreasureConstants.COLOR_DARK[0], MyTreasureConstants.COLOR_DARK[1], MyTreasureConstants.COLOR_DARK[2]);
		
		String s = "options";
		float w = MyTreasureConstants.FONT_LEVELCHOOSER.getLength(s);
		g.drawText(s, MyTreasureConstants.GAME_WIDTH/2 - w/2 + 4, 54 - y);
		
		g.setFont(MyTreasureConstants.fontMedium);
		y = MyTreasureConstants.fontMedium.mCharCellHeight;
		s = "sound";
		g.drawText(s, 25, 170 - y);

		s = "music";
		g.drawText(s, 25, 250 - y);
		
		g.setColor(1f, 1f, 1f, 1f);
	}

}