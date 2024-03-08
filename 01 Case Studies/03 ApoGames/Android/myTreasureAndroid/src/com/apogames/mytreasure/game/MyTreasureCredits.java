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

import net.gliblybits.bitsengine.graphics.opengl.BitsGLGraphics;

import com.apogames.mytreasure.MyTreasureConstants;
import com.apogames.mytreasure.MyTreasureModel;
import com.apogames.mytreasure.MyTreasureSoundPlayer;

public class MyTreasureCredits extends MyTreasureModel {

	public static final String BACK = "back";
	
	public MyTreasureCredits(final MyTreasurePanel game) {
		super(game);
	}

	@Override
	public void init() {
	}

	@Override
	public void touchedButton(String function) {
		this.getGame().playSound(MyTreasureSoundPlayer.SOUND_CLICK);
		if (function.equals(MyTreasureCredits.BACK)) {
			this.onBackButtonPressed();
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
		
		String s = "credits";
		float w = MyTreasureConstants.FONT_LEVELCHOOSER.getLength(s);
		g.drawText(s, MyTreasureConstants.GAME_WIDTH/2 - w/2 + 4, 54 - y);
		
		g.setFont(MyTreasureConstants.fontMedium);
		y = MyTreasureConstants.fontMedium.mCharCellHeight;
		s = "art design by";
		w = MyTreasureConstants.fontMedium.getLength(s);
		g.drawText(s, MyTreasureConstants.GAME_WIDTH/2 - w/2, 125 - y);

		s = "engine by";
		w = MyTreasureConstants.fontMedium.getLength(s);
		g.drawText(s, MyTreasureConstants.GAME_WIDTH/2 - w/2, 220 - y);
		
		s = "everything else by";
		w = MyTreasureConstants.fontMedium.getLength(s);
		g.drawText(s, MyTreasureConstants.GAME_WIDTH/2 - w/2, 350 - y);

		s = "music by";
		w = MyTreasureConstants.fontMedium.getLength(s);
		g.drawText(s, MyTreasureConstants.GAME_WIDTH/2 - w/2, 285 - y);
		
		g.setFont(MyTreasureConstants.FONT_STATISTICS);
		y = MyTreasureConstants.FONT_STATISTICS.mCharCellHeight;
		s = "tinytouchtales.com";
		w = MyTreasureConstants.FONT_STATISTICS.getLength(s);
		g.drawText(s, MyTreasureConstants.GAME_WIDTH/2 - w/2, 150 - y);
		
		s = "and";
		w = MyTreasureConstants.FONT_STATISTICS.getLength(s);
		g.drawText(s, MyTreasureConstants.GAME_WIDTH/2 - w/2, 170 - y);
		
		s = "apo-games.de";
		w = MyTreasureConstants.FONT_STATISTICS.getLength(s);
		g.drawText(s, MyTreasureConstants.GAME_WIDTH/2 - w/2, 190 - y);
		
		s = "apo-games.de";
		w = MyTreasureConstants.FONT_STATISTICS.getLength(s);
		g.drawText(s, MyTreasureConstants.GAME_WIDTH/2 - w/2, 375 - y);

		s = "Marc Wiedenhoeft";
		w = MyTreasureConstants.FONT_STATISTICS.getLength(s);
		g.drawText(s, MyTreasureConstants.GAME_WIDTH/2 - w/2, 245 - y);
		
		s = "Max Josef Ender";
		w = MyTreasureConstants.FONT_STATISTICS.getLength(s);
		g.drawText(s, MyTreasureConstants.GAME_WIDTH/2 - w/2, 310 - y);
		
		g.setColor(1f, 1f, 1f, 1f);
	}

}