package net.apogames.apoclock;

import net.apogames.apoclock.ApoClockConstants;
import net.apogames.apoclock.entity.ApoButton;
import net.apogames.apoclock.game.ApoClockArcarde;
import net.apogames.apoclock.game.ApoClockEditor;
import net.apogames.apoclock.game.ApoClockMenu;
import net.apogames.apoclock.game.ApoClockOptions;
import net.apogames.apoclock.game.ApoClockPanel;
import net.apogames.apoclock.game.ApoClockPuzzle;
import net.apogames.apoclock.game.ApoClockPuzzleChooser;
import net.apogames.apoclock.game.ApoClockPuzzleGame;

public class ApoClockButtons {
	
	private final ApoClockPanel game;
	
	public ApoClockButtons(final ApoClockPanel game) {
		this.game = game;
	}

	public void init() {
		if (this.game.getButtons() == null) {
			this.game.setButtons(new ApoButton[20]);

			String function = ApoClockMenu.QUIT;
			int width = 200;
			int height = 70;
			int x = ApoClockConstants.GAME_WIDTH/2 - width/2;
			int y = ApoClockConstants.GAME_HEIGHT - 1 * height - 5;
			this.game.getButtons()[0] = new ApoButton(null, x, y, width, height, function);

			function = ApoClockMenu.PUZZLE;
			width = 300;
			height = 100;
			x = ApoClockConstants.GAME_WIDTH/2 - width/2;
			y = 150;
			this.game.getButtons()[1] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoClockMenu.ARCADE;
			width = 300;
			height = 100;
			x = ApoClockConstants.GAME_WIDTH/2 - width/2;
			y = 150 + height * 1 + 20;
			this.game.getButtons()[2] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoClockPuzzleChooser.BACK;
			width = 70;
			height = 40;
			x = ApoClockConstants.GAME_WIDTH - width - 5;
			y = ApoClockConstants.GAME_HEIGHT - 1 * height - 5;
			this.game.getButtons()[3] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoClockPuzzleGame.BACK;
			width = 70;
			height = 40;
			x = ApoClockConstants.GAME_WIDTH - width - 5;
			y = ApoClockConstants.GAME_HEIGHT - 1 * height - 5;
			this.game.getButtons()[4] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoClockArcarde.BACK;
			width = 70;
			height = 40;
			x = ApoClockConstants.GAME_WIDTH - width - 5;
			y = ApoClockConstants.GAME_HEIGHT - 1 * height - 5;
			this.game.getButtons()[5] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoClockArcarde.START;
			width = 70;
			height = 40;
			x = 5;
			y = ApoClockConstants.GAME_HEIGHT - 1 * height - 5;
			this.game.getButtons()[6] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoClockArcarde.BACK;
			width = 70;
			height = 40;
			x = ApoClockConstants.GAME_WIDTH - width - 5;
			y = ApoClockConstants.GAME_HEIGHT - 1 * height - 5;
			this.game.getButtons()[7] = new ApoButton(null, x, y, width, height, function);

			
			function = ApoClockPuzzle.BACK;
			width = 200;
			height = 70;
			x = ApoClockConstants.GAME_WIDTH/2 - width/2;
			y = ApoClockConstants.GAME_HEIGHT - 1 * height - 5;
			this.game.getButtons()[8] = new ApoButton(null, x, y, width, height, function);

			function = ApoClockPuzzle.PUZZLE;
			width = 300;
			height = 70;
			x = ApoClockConstants.GAME_WIDTH/2 - width/2;
			y = 100;
			this.game.getButtons()[9] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoClockPuzzle.USERLEVELS;
			width = 300;
			height = 70;
			x = ApoClockConstants.GAME_WIDTH/2 - width/2;
			y = 100 + height * 1 + 20;
			this.game.getButtons()[10] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoClockPuzzle.EDITOR;
			width = 300;
			height = 70;
			x = ApoClockConstants.GAME_WIDTH/2 - width/2;
			y = 100 + height * 2 + 20 * 2;
			this.game.getButtons()[11] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoClockEditor.BACK;
			width = 75;
			height = 40;
			x = ApoClockConstants.GAME_WIDTH - width - 5;
			y = ApoClockConstants.GAME_HEIGHT - 1 * height - 5;
			this.game.getButtons()[12] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoClockEditor.UPLOAD;
			width = 75;
			height = 40;
			x = ApoClockConstants.GAME_WIDTH - width * 2 - 5 * 2;
			y = ApoClockConstants.GAME_HEIGHT - 1 * height - 5;
			this.game.getButtons()[13] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoClockEditor.TEST;
			width = 75;
			height = 40;
			x = ApoClockConstants.GAME_WIDTH - width * 3 - 5 * 3;
			y = ApoClockConstants.GAME_HEIGHT - 1 * height - 5;
			this.game.getButtons()[14] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoClockEditor.REMOVE;
			width = 75;
			height = 40;
			x = ApoClockConstants.GAME_WIDTH - width * 4 - 5 * 4;
			y = ApoClockConstants.GAME_HEIGHT - 1 * height - 5;
			this.game.getButtons()[15] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoClockEditor.ADD;
			width = 75;
			height = 40;
			x = ApoClockConstants.GAME_WIDTH - width * 5 - 5 * 5;
			y = ApoClockConstants.GAME_HEIGHT - 1 * height - 5;
			this.game.getButtons()[16] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoClockEditor.NEW;
			width = 75;
			height = 40;
			x = ApoClockConstants.GAME_WIDTH - width * 6 - 5 * 6;
			y = ApoClockConstants.GAME_HEIGHT - 1 * height - 5;
			this.game.getButtons()[17] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoClockOptions.BACK;
			width = 200;
			height = 70;
			x = ApoClockConstants.GAME_WIDTH/2 - width/2;
			y = ApoClockConstants.GAME_HEIGHT - 1 * height - 5;
			this.game.getButtons()[18] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoClockMenu.OPTIONS;
			width = 300;
			height = 100;
			x = ApoClockConstants.GAME_WIDTH/2 - width/2;
			y = 150 + height * 2 + 2 * 20;
			this.game.getButtons()[19] = new ApoButton(null, x, y, width, height, function);
			
			for (int i = 0; i < this.game.getButtons().length; i++) {
				this.game.getButtons()[i].setBOpaque(true);
			}
		}
	}
}
