package net.apogames.apomono;

import net.apogames.apomono.entity.ApoButton;
import net.apogames.apomono.entity.ApoLevelChooserButton;
import net.apogames.apomono.game.ApoMonoCredits;
import net.apogames.apomono.game.ApoMonoEditor;
import net.apogames.apomono.game.ApoMonoLevelChooser;
import net.apogames.apomono.game.ApoMonoMenu;
import net.apogames.apomono.game.ApoMonoOptions;
import net.apogames.apomono.game.ApoMonoPanel;
import net.apogames.apomono.game.ApoMonoPuzzleGame;

public class ApoMonoButtons {
	
	private final ApoMonoPanel game;
	
	public ApoMonoButtons(final ApoMonoPanel game) {
		this.game = game;
	}

	public void init() {
		if (this.game.getButtons() == null) {
			this.game.setButtons(new ApoButton[23]);

			int addY = 0;
			if (ApoMonoConstants.FREE_VERSION) {
				addY = 45;
			}
			
			String function = ApoMonoMenu.QUIT;
			int width = 48;
			int height = 48;
			int x = ApoMonoConstants.GAME_WIDTH - width - 5;
			int y = ApoMonoConstants.GAME_HEIGHT - 1 * height - 5;
			this.game.getButtons()[0] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoMonoMenu.PLAY;
			width = 320;
			height = 64;
			x = ApoMonoConstants.GAME_WIDTH/2 - width/2;
			y = 100;
			this.game.getButtons()[1] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoMonoMenu.EDITOR;
			width = 320;
			height = 64;
			x = ApoMonoConstants.GAME_WIDTH/2 - width/2;
			y = 100 + height * 1 + 6;
			this.game.getButtons()[2] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoMonoPuzzleGame.BACK;
			width = 60;
			height = 38;
			x = ApoMonoConstants.GAME_WIDTH - width - 5;
			y = 1;
			this.game.getButtons()[3] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoMonoEditor.BACK;
			width = 60;
			height = 38;
			x = ApoMonoConstants.GAME_WIDTH - width - 5;
			y = 1;
			this.game.getButtons()[4] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoMonoEditor.TEST;
			width = 60;
			height = 38;
			x = ApoMonoConstants.GAME_WIDTH - 3 * width - 3 * 5;
			y = 1;
			this.game.getButtons()[5] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoMonoEditor.NEW;
			width = 60;
			height = 38;
			x = ApoMonoConstants.GAME_WIDTH - 4 * width - 4 * 5;
			y = 1;
			this.game.getButtons()[6] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoMonoEditor.UPLOAD;
			width = 60;
			height = 38;
			x = ApoMonoConstants.GAME_WIDTH - 2 * width - 2 * 5;
			y = 1;
			this.game.getButtons()[7] = new ApoButton(null, x, y, width, height, function);

			function = ApoMonoMenu.USERLEVELS;
			width = 320;
			height = 64;
			x = ApoMonoConstants.GAME_WIDTH/2 - width/2;
			y = 100 + height * 2 + 12;
			this.game.getButtons()[8] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoMonoMenu.CREDITS;
			width = 48;
			height = 48;
			x = 5;
			y = ApoMonoConstants.GAME_HEIGHT - 1 * height - 5;
			this.game.getButtons()[9] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoMonoCredits.BACK;
			width = 80;
			height = 40;
			x = ApoMonoConstants.GAME_WIDTH - width - 5;
			y = ApoMonoConstants.GAME_HEIGHT- height - 5;
			this.game.getButtons()[10] = new ApoLevelChooserButton(x, y, width, height, function, function, true);
			
			function = ApoMonoOptions.BACK;
			width = 80;
			height = 40;
			x = ApoMonoConstants.GAME_WIDTH - width - 5;
			y = ApoMonoConstants.GAME_HEIGHT- height - 5;
			this.game.getButtons()[11] = new ApoLevelChooserButton(x, y, width, height, function, function, true);
			
			function = ApoMonoOptions.LANGUAGE_GERMAN;
			width = 48;
			height = 48;
			x = 120;
			y = 60 + addY;
			this.game.getButtons()[12] = new ApoLevelChooserButton(x, y, width, height, function, "x");
			
			function = ApoMonoOptions.LANGUAGE_ENGLISH;
			width = 48;
			height = 48;
			x = 255;
			y = 60 + addY;
			this.game.getButtons()[13] = new ApoLevelChooserButton(x, y, width, height, function, "x", true);
			
			function = ApoMonoOptions.COLOR_WHITE;
			width = 48;
			height = 48;
			x = 120;
			y = 120 + addY;
			this.game.getButtons()[14] = new ApoLevelChooserButton(x, y, width, height, function, "x");
			
			function = ApoMonoOptions.COLOR_GREEN;
			width = 48;
			height = 48;
			x = 255;
			y = 120 + addY;
			this.game.getButtons()[15] = new ApoLevelChooserButton(x, y, width, height, function, "x", true);
			
			function = ApoMonoMenu.OPTIONS;
			width = 48;
			height = 48;
			x = 5;
			y = 100;
			this.game.getButtons()[16] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoMonoPuzzleGame.RETRY;
			width = 65;
			height = 38;
			x = ApoMonoConstants.GAME_WIDTH - 2 * width - 1 * 5;
			y = 1;
			this.game.getButtons()[17] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoMonoLevelChooser.BACK;
			width = 50;
			height = 40;
			x = ApoMonoConstants.GAME_WIDTH - width - 5;
			y = ApoMonoConstants.GAME_HEIGHT- height - 5;
			this.game.getButtons()[18] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoMonoLevelChooser.LEFT;
			width = 40;
			height = 40;
			x = 5;
			y = ApoMonoPuzzleGame.changeY + 135;
			this.game.getButtons()[19] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoMonoLevelChooser.RIGHT;
			width = 40;
			height = 40;
			x = ApoMonoConstants.GAME_WIDTH - width - 5;
			y = ApoMonoPuzzleGame.changeY + 135;
			this.game.getButtons()[20] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoMonoOptions.SOUND;
			width = 48;
			height = 48;
			x = 120;
			y = 180 + addY;
			this.game.getButtons()[21] = new ApoLevelChooserButton(x, y, width, height, function, "x");
			
			function = ApoMonoOptions.MUSIC;
			width = 48;
			height = 48;
			x = 255;
			y = 180 + addY;
			this.game.getButtons()[22] = new ApoLevelChooserButton(x, y, width, height, function, "x");
			
			for (int i = 0; i < this.game.getButtons().length; i++) {
				this.game.getButtons()[i].setBOpaque(true);
			}
		}
	}
}
