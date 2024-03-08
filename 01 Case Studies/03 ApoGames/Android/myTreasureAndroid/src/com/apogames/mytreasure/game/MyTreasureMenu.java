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

import net.gliblybits.bitsengine.core.BitsGame;
import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;

import com.apogames.mytreasure.MyTreasureConstants;
import com.apogames.mytreasure.MyTreasureModel;
import com.apogames.mytreasure.MyTreasureSoundPlayer;

public class MyTreasureMenu extends MyTreasureModel {

	public static final String QUIT = "X";
	public static final String GAME = "Play";
	public static final String USERLEVELS = "Userlevels";
	public static final String EDITOR = "Editor";
	public static final String CREDITS = "C";
	public static final String OPTIONS = "O";
	
	public MyTreasureMenu(final MyTreasurePanel game) {
		super(game);
	}

	@Override
	public void init() {
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
		this.getGame().playSound(MyTreasureSoundPlayer.SOUND_CLICK);
		if (function.equals(MyTreasureMenu.QUIT)) {
			this.onBackButtonPressed();
		}
		if (function.equals(MyTreasureMenu.GAME)) {
			this.getGame().setMap();
		}
		if (function.equals(MyTreasureMenu.CREDITS)) {
			this.getGame().setCredits();
		}

		if (function.equals(MyTreasureMenu.OPTIONS)) {
			this.getGame().setOptions();
		}
		if (function.equals(MyTreasureMenu.EDITOR)) {
			if (!MyTreasureConstants.FREE_VERSION) {
				this.getGame().setEditor(false, 95);
			}
		}
		if (function.equals(MyTreasureMenu.USERLEVELS)) {
			if (!MyTreasureConstants.FREE_VERSION) {
//				this.getGame().setGame(true, false, 0, "");
				this.getGame().setLevelChooser(0, 0, false, true);
			}
		}
		
	}
	
	public void onBackButtonPressed() {
		BitsGame.getInstance().finishApp();
	}
	
	@Override
	public void think(int delta) {
	}

	@Override
	public void render(BitsGLGraphics g) {
		g.cropImage(MyTreasureConstants.iSheet, 0, 0, MyTreasureConstants.GAME_WIDTH, MyTreasureConstants.GAME_HEIGHT, MyTreasureConstants.iSheet.mWidth - MyTreasureConstants.GAME_WIDTH, 0, MyTreasureConstants.GAME_WIDTH, MyTreasureConstants.GAME_HEIGHT);

		g.cropImage(MyTreasureConstants.iSheet, 5 * 4, 25 * 4, 72 * 4, 8 * 4, 0, 112 * 4, 72 * 4, 8 * 4);
		g.cropImage(MyTreasureConstants.iSheet, 5 * 4 + 32 * 4, 25 * 4 - 24, 8 * 4, 8 * 4, 0, 0, 8 * 4, 8 * 4);

		g.cropImage(MyTreasureConstants.iSheet, 2 * 4, 390, 76 * 4, 8 * 4, 0, 96 * 4, 76 * 4, 8 * 4);
	
		g.cropImage(MyTreasureConstants.iSheet, 5 * 4 + 50 * 4 - 16, 25 * 4 - 24 - 16, 64, 64, 8 * 32, 32, 64, 64);
		g.cropImage(MyTreasureConstants.iSheet, 5 * 4 + 50 * 4, 25 * 4 - 24, 32, 32, 0 * 32, 1 * 32, 32, 32);
		g.cropImage(MyTreasureConstants.iSheet, 5 * 4 + 12 * 4 - 16, 25 * 4 - 24 - 16, 64, 64, 8 * 32, 32, 64, 64);
		g.cropImage(MyTreasureConstants.iSheet, 5 * 4 + 12 * 4, 25 * 4 - 24, 32, 32, 0 * 32, 1 * 32, 32, 32);
		
		g.setFont(MyTreasureConstants.font);
		g.setColor(MyTreasureConstants.COLOR_DARK[0], MyTreasureConstants.COLOR_DARK[1], MyTreasureConstants.COLOR_DARK[2]);
		
		String s = "MyTreasure";
		float w = MyTreasureConstants.font.getLength(s);
		g.drawText(s, MyTreasureConstants.GAME_WIDTH/2 - w/2 + 4, 54 - MyTreasureConstants.font.mCharCellHeight);
		
		if (MyTreasureConstants.FREE_VERSION) {
			s = "free version";
			g.setFont(MyTreasureConstants.fontSmall);
			
			w = MyTreasureConstants.fontSmall.getLength(s);
			g.drawText(s, MyTreasureConstants.GAME_WIDTH/2 - w/2 + 4, 56);
		}
		
		g.setColor(1f, 1f, 1f, 1f);
	}

}
