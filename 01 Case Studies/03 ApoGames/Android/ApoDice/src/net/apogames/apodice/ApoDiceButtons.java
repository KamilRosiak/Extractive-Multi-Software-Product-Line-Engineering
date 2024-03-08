package net.apogames.apodice;

import net.apogames.apodice.ApoDiceConstants;
import net.apogames.apodice.entity.ApoButton;
import net.apogames.apodice.game.ApoDiceEditor;
import net.apogames.apodice.game.ApoDiceMenu;
import net.apogames.apodice.game.ApoDicePanel;
import net.apogames.apodice.game.ApoDicePuzzleChooser;
import net.apogames.apodice.game.ApoDicePuzzleGame;

public class ApoDiceButtons {
	
	private final ApoDicePanel game;
	
	public ApoDiceButtons(final ApoDicePanel game) {
		this.game = game;
	}

	public void init() {
		if (this.game.getButtons() == null) {
			this.game.setButtons(new ApoButton[11]);

			String function = ApoDiceMenu.QUIT;
			int width = 200;
			int height = 60;
			int x = ApoDiceConstants.GAME_WIDTH/2 - width/2;
			int y = ApoDiceConstants.GAME_HEIGHT - 1 * height - 5;
			this.game.getButtons()[0] = new ApoButton(null, x, y, width, height, function);

			function = ApoDiceMenu.PUZZLE;
			width = 300;
			height = 60;
			x = ApoDiceConstants.GAME_WIDTH/2 - width/2;
			y = 150;
			this.game.getButtons()[1] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoDiceMenu.USERLEVELS;
			width = 300;
			height = 60;
			x = ApoDiceConstants.GAME_WIDTH/2 - width/2;
			y = 150 + height * 1 + 20 * 1;
			this.game.getButtons()[2] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoDiceMenu.EDITOR;
			width = 300;
			height = 60;
			x = ApoDiceConstants.GAME_WIDTH/2 - width/2;
			y = 150 + height * 2 + 20 * 2;
			this.game.getButtons()[3] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoDicePuzzleChooser.BACK;
			width = 70;
			height = 40;
			x = ApoDiceConstants.GAME_WIDTH - width - 5;
			y = ApoDiceConstants.GAME_HEIGHT - 1 * height - 10;
			this.game.getButtons()[4] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoDicePuzzleGame.BACK;
			width = 70;
			height = 40;
			x = ApoDiceConstants.GAME_WIDTH - width - 5;
			y = ApoDiceConstants.GAME_HEIGHT - 1 * height - 10;
			this.game.getButtons()[5] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoDiceEditor.BACK;
			width = 70;
			height = 40;
			x = ApoDiceConstants.GAME_WIDTH - width - 5;
			y = ApoDiceConstants.GAME_HEIGHT - 1 * height - 10;
			this.game.getButtons()[6] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoDiceEditor.NEW;
			width = 70;
			height = 40;
			x = ApoDiceConstants.GAME_WIDTH - 4 * width - 10 * 4;
			y = ApoDiceConstants.GAME_HEIGHT - 1 * height - 10;
			this.game.getButtons()[7] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoDiceEditor.TEST;
			width = 70;
			height = 40;
			x = ApoDiceConstants.GAME_WIDTH - 3 * width - 10 * 3;
			y = ApoDiceConstants.GAME_HEIGHT - 1 * height - 10;
			this.game.getButtons()[8] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoDiceEditor.UPLOAD;
			width = 70;
			height = 40;
			x = ApoDiceConstants.GAME_WIDTH - 2 * width - 10 * 2;
			y = ApoDiceConstants.GAME_HEIGHT - 1 * height - 10;
			this.game.getButtons()[9] = new ApoButton(null, x, y, width, height, function);

			function = ApoDiceEditor.SOLVE;
			width = 70;
			height = 20;
			x = ApoDiceConstants.GAME_WIDTH - width - 10;
			y = 2;
			this.game.getButtons()[10] = new ApoButton(null, x, y, width, height, function);
			
			for (int i = 0; i < this.game.getButtons().length; i++) {
				this.game.getButtons()[i].setBOpaque(true);
			}
		}
	}
}
