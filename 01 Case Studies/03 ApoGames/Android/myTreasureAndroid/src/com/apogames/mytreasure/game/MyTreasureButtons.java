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

import net.gliblybits.bitsengine.graphics.opengl.BitsGLFont;

import com.apogames.mytreasure.MyTreasureConstants;
import com.apogames.mytreasure.entity.ApoButton;

public class MyTreasureButtons {
	
	private final MyTreasurePanel game;
	
	public MyTreasureButtons(MyTreasurePanel game) {
		this.game = game;
	}

	public void init() {
		if (this.game.getButtons() == null) {
			this.game.setButtons(new ApoButton[27]);
			
			BitsGLFont fontVerySmall = MyTreasureConstants.fontSmall;
			BitsGLFont fontSmall = MyTreasureConstants.fontMedium;
			BitsGLFont font = MyTreasureConstants.FONT_LEVELCHOOSER;
			String text = MyTreasureMenu.QUIT;
			String function = MyTreasureMenu.QUIT;
			int width = 32;
			int height = 32;
			int x = MyTreasureConstants.GAME_WIDTH - width - 10;
			int y = MyTreasureConstants.GAME_HEIGHT - 1 * height - 20;
			this.game.getButtons()[0] = new ApoButton(x, y, width, height, 56 * 4, 0, function, text, fontSmall, MyTreasureConstants.COLOR_LIGHT);
			
			text = MyTreasureMenu.GAME;
			function = MyTreasureMenu.GAME;
			width = 192;
			height = 64;
			x = MyTreasureConstants.GAME_WIDTH/2 - width/2;
			y = MyTreasureConstants.GAME_HEIGHT/2 - 80;
			this.game.getButtons()[1] = new ApoButton(x, y, width, height, 128, 320, function, text, font, MyTreasureConstants.COLOR_LIGHT);
			
			text = MyTreasureMenu.USERLEVELS;
			function = MyTreasureMenu.USERLEVELS;
			width = 192;
			height = 64;
			x = MyTreasureConstants.GAME_WIDTH/2 - width/2;
			y = MyTreasureConstants.GAME_HEIGHT/2 - 80 + (height + 5) * 1;
			if (MyTreasureConstants.FREE_VERSION) {
				this.game.getButtons()[2] = new ApoButton(x, y, width, height, 32 * 4, 64 * 4, function, text, font, MyTreasureConstants.COLOR_LIGHT);				
			} else {
				this.game.getButtons()[2] = new ApoButton(x, y, width, height, 32 * 4, 80 * 4, function, text, font, MyTreasureConstants.COLOR_LIGHT);
			}
			
			text = MyTreasureMenu.EDITOR;
			function = MyTreasureMenu.EDITOR;
			width = 192;
			height = 64;
			x = MyTreasureConstants.GAME_WIDTH/2 - width/2;
			y = MyTreasureConstants.GAME_HEIGHT/2 - 80 + (height + 5) * 2;
			if (MyTreasureConstants.FREE_VERSION) {
				this.game.getButtons()[3] = new ApoButton(x, y, width, height, 32 * 4, 64 * 4, function, text, font, MyTreasureConstants.COLOR_LIGHT);
			} else {
				this.game.getButtons()[3] = new ApoButton(x, y, width, height, 32 * 4, 80 * 4, function, text, font, MyTreasureConstants.COLOR_LIGHT);
			}
			
			text = MyTreasureMenu.CREDITS;
			function = MyTreasureMenu.CREDITS;
			width = 32;
			height = 32;
			x = 14;
			y = MyTreasureConstants.GAME_HEIGHT - 1 * height - 24;
			this.game.getButtons()[4] = new ApoButton(x, y, width, height, 56 * 4, 0 * 4, function, text, fontSmall, MyTreasureConstants.COLOR_LIGHT);
			
			text = MyTreasureLevelChooser.BACK;
			function = MyTreasureLevelChooser.BACK;
			width = 32;
			height = 32;
			x = 7 * 4;
			y = 5 * 4;
			this.game.getButtons()[5] = new ApoButton(x, y, width, height, 0 * 4, 120 * 4, function, "", fontSmall, MyTreasureConstants.COLOR_LIGHT);
			
			text = MyTreasureGame.BACK;
			function = MyTreasureGame.BACK;
			width = 32;
			height = 32;
			x = MyTreasureConstants.GAME_WIDTH - width - 8;
			y = 0;
			this.game.getButtons()[6] = new ApoButton(x, y, width, height, 56 * 4, 16 * 4, function, "", fontSmall, MyTreasureConstants.COLOR_LIGHT);
			
			text = MyTreasureMap.BACK;
			function = MyTreasureMap.BACK;
			width = 32;
			height = 32;
			x = 7 * 4;
			y = 5 * 4;
			this.game.getButtons()[7] = new ApoButton(x, y, width, height, 0 * 4, 120 * 4, function, "", fontSmall, MyTreasureConstants.COLOR_LIGHT);
			
			text = MyTreasureGame.INGAME_RESTART;
			function = MyTreasureGame.INGAME_RESTART;
			width = 32;
			height = 32;
			x = MyTreasureConstants.GAME_WIDTH - 2 * width - 16;
			y = 0;
			this.game.getButtons()[8] = new ApoButton(x, y, width, height, 48 * 4, 16 * 4, function, "", font, MyTreasureConstants.COLOR_LIGHT);
			
			text = MyTreasureGame.INGAME_NEXT;
			function = MyTreasureGame.INGAME_NEXT;
			width = 32;
			height = 32;
			x = MyTreasureConstants.GAME_WIDTH - width - 48;
			y = MyTreasureConstants.GAME_HEIGHT - height;
			this.game.getButtons()[9] = new ApoButton(x, y, width, height, 48 * 4, 32 * 4, function, "", font, MyTreasureConstants.COLOR_LIGHT);

			
			text = MyTreasureGame.INGAME_PREVIOUS;
			function = MyTreasureGame.INGAME_PREVIOUS;
			width = 32;
			height = 32;
			x = 48;
			y = MyTreasureConstants.GAME_HEIGHT - height;
			this.game.getButtons()[10] = new ApoButton(x, y, width, height, 48 * 4, 24 * 4, function, "", font, MyTreasureConstants.COLOR_LIGHT);
			
			text = MyTreasureGame.RESULT_MENU;
			function = MyTreasureGame.RESULT_MENU;
			width = 64;
			height = 64;
			x = 48;
			y = 380;
			this.game.getButtons()[11] = new ApoButton(x, y, width, height, 16 * 4, 40 * 4, function, "", font, MyTreasureConstants.COLOR_LIGHT);

			
			text = MyTreasureGame.RESULT_RESTART;
			function = MyTreasureGame.RESULT_RESTART;
			width = 64;
			height = 64;
			x = 128;
			y = 380;
			this.game.getButtons()[12] = new ApoButton(x, y, width, height, 0 * 4, 40 * 4, function, "", font, MyTreasureConstants.COLOR_LIGHT);
			
			text = MyTreasureGame.RESULT_NEXT;
			function = MyTreasureGame.RESULT_NEXT;
			width = 64;
			height = 64;
			x = 208;
			y = 380;
			this.game.getButtons()[13] = new ApoButton(x, y, width, height, 0 * 4, 56 * 4, function, "", font, MyTreasureConstants.COLOR_LIGHT);
			
			text = MyTreasureCredits.BACK;
			function = MyTreasureCredits.BACK;
			width = 32;
			height = 32;
			x = 7 * 4;
			y = 5 * 4;
			this.game.getButtons()[14] = new ApoButton(x, y, width, height, 0 * 4, 120 * 4, function, "", font, MyTreasureConstants.COLOR_LIGHT);

			text = MyTreasureEditor.BACK;
			function = MyTreasureEditor.BACK;
			width = 32;
			height = 32;
			x = MyTreasureConstants.GAME_WIDTH - width - 8;
			y = 0;
			this.game.getButtons()[15] = new ApoButton(x, y, width, height, 56 * 4, 16 * 4, function, "", font, MyTreasureConstants.COLOR_LIGHT);
			
			text = MyTreasureEditor.SIZE_LEFT;
			function = MyTreasureEditor.SIZE_LEFT;
			width = 32;
			height = 32;
			x = 8;
			y = MyTreasureConstants.GAME_HEIGHT - height * 2;
			this.game.getButtons()[16] = new ApoButton(x, y, width, height, 48 * 4, 24 * 4, function, "", font, MyTreasureConstants.COLOR_LIGHT);
			
			text = MyTreasureEditor.SIZE_RIGHT;
			function = MyTreasureEditor.SIZE_RIGHT;
			width = 32;
			height = 32;
			x = 80;
			y = MyTreasureConstants.GAME_HEIGHT - height * 2;
			this.game.getButtons()[17] = new ApoButton(x, y, width, height, 48 * 4, 32 * 4, function, "", font, MyTreasureConstants.COLOR_LIGHT);

			text = MyTreasureEditor.TEST;
			function = MyTreasureEditor.TEST;
			width = 64;
			height = 32;
			x = 8;
			y = 0;
			this.game.getButtons()[18] = new ApoButton(x, y, width, height, 32 * 4, 40 * 4, function, text, fontVerySmall, MyTreasureConstants.COLOR_SEPARATOR);
			
			text = MyTreasureEditor.UPLOAD;
			function = MyTreasureEditor.UPLOAD;
			width = 64;
			height = 32;
			x = 96;
			y = 0;
			this.game.getButtons()[19] = new ApoButton(x, y, width, height, 32 * 4, 40 * 4, function, text, fontVerySmall, MyTreasureConstants.COLOR_SEPARATOR);

			text = MyTreasureEditor.SOLVE;
			function = MyTreasureEditor.SOLVE;
			width = 64;
			height = 32;
			x = 184;
			y = 0;
			this.game.getButtons()[20] = new ApoButton(x, y, width, height, 32 * 4, 40 * 4, function, text, fontVerySmall, MyTreasureConstants.COLOR_SEPARATOR);

			text = MyTreasureMenu.OPTIONS;
			function = MyTreasureMenu.OPTIONS;
			width = 32;
			height = 32;
			x = MyTreasureConstants.GAME_WIDTH/2 - width/2;
			y = MyTreasureConstants.GAME_HEIGHT - 1 * height - 24;
			this.game.getButtons()[21] = new ApoButton(x, y, width, height, 56 * 4, 0 * 4, function, text, fontSmall, MyTreasureConstants.COLOR_LIGHT);

			text = MyTreasureOptions.BACK;
			function = MyTreasureOptions.BACK;
			width = 32;
			height = 32;
			x = 7 * 4;
			y = 5 * 4;
			this.game.getButtons()[22] = new ApoButton(x, y, width, height, 0 * 4, 120 * 4, function, "", font, MyTreasureConstants.COLOR_LIGHT);

			text = "";
			function = MyTreasureOptions.SOUND;
			width = 32;
			height = 32;
			x = MyTreasureConstants.GAME_WIDTH*3/4 - width/2;
			y = 160 - height/2;
			this.game.getButtons()[23] = new ApoButton(x, y, width, height, 56 * 4, 0 * 4, function, text, fontSmall, MyTreasureConstants.COLOR_LIGHT);
			this.game.getButtons()[23].setSelected(true);
			
			text = "";
			function = MyTreasureOptions.MUSIC;
			width = 32;
			height = 32;
			x = MyTreasureConstants.GAME_WIDTH*3/4 - width/2;
			y = 240 - height/2;
			this.game.getButtons()[24] = new ApoButton(x, y, width, height, 56 * 4, 0 * 4, function, text, fontSmall, MyTreasureConstants.COLOR_LIGHT);
			this.game.getButtons()[24].setSelected(true);
			
			text = MyTreasureGame.INGAME_MUSIC;
			function = MyTreasureGame.INGAME_MUSIC;
			width = 32;
			height = 32;
			x = MyTreasureConstants.GAME_WIDTH - width - 8;
			y = MyTreasureConstants.GAME_HEIGHT - height;
			this.game.getButtons()[25] = new ApoButton(x, y, width, height, 48 * 4, 48 * 4, function, "", font, MyTreasureConstants.COLOR_SEPARATOR);

			text = MyTreasureGame.INGAME_SOUND;
			function = MyTreasureGame.INGAME_SOUND;
			width = 32;
			height = 32;
			x = 8;
			y = MyTreasureConstants.GAME_HEIGHT - height;
			this.game.getButtons()[26] = new ApoButton(x, y, width, height, 48 * 4, 40 * 4, function, "", font, MyTreasureConstants.COLOR_SEPARATOR);
			
			for (int i = 0; i < this.game.getButtons().length; i++) {
				this.game.getButtons()[i].setBOpaque(false);
			}
		}
	}
}
