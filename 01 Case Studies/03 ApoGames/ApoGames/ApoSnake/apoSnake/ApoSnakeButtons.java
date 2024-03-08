package apoSnake;

import org.apogames.entity.ApoButton;

import apoSnake.game.ApoSnakeEditor;
import apoSnake.game.ApoSnakeMenu;
import apoSnake.game.ApoSnakePanel;
import apoSnake.game.ApoSnakePuzzleChooser;
import apoSnake.game.ApoSnakePuzzleGame;

public class ApoSnakeButtons {
	
	private final ApoSnakePanel game;
	
	public ApoSnakeButtons(final ApoSnakePanel game) {
		this.game = game;
	}

	public void init() {
		if (this.game.getButtons() == null) {
			this.game.setButtons(new ApoButton[13]);

			String function = ApoSnakeMenu.QUIT;
			int width = 200;
			int height = 60;
			int x = ApoSnakeConstants.GAME_WIDTH/2 - width/2;
			int y = ApoSnakeConstants.GAME_HEIGHT - 1 * height - 5;
			this.game.getButtons()[0] = new ApoButton(null, x, y, width, height, function);

			function = ApoSnakeMenu.PUZZLE;
			width = 300;
			height = 60;
			x = ApoSnakeConstants.GAME_WIDTH/2 - width/2;
			y = 150;
			this.game.getButtons()[1] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoSnakeMenu.USERLEVELS;
			width = 300;
			height = 60;
			x = ApoSnakeConstants.GAME_WIDTH/2 - width/2;
			y = 150 + height * 1 + 20 * 1;
			this.game.getButtons()[2] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoSnakeMenu.EDITOR;
			width = 300;
			height = 60;
			x = ApoSnakeConstants.GAME_WIDTH/2 - width/2;
			y = 150 + height * 2 + 20 * 2;
			this.game.getButtons()[3] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoSnakePuzzleChooser.BACK;
			width = 70;
			height = 40;
			x = ApoSnakeConstants.GAME_WIDTH - width - 5;
			y = ApoSnakeConstants.GAME_HEIGHT - 1 * height - 10;
			this.game.getButtons()[4] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoSnakePuzzleGame.BACK;
			width = 70;
			height = 40;
			x = ApoSnakeConstants.GAME_WIDTH - width - 5;
			y = ApoSnakeConstants.GAME_HEIGHT - 60 - 1 * height - 20;
			this.game.getButtons()[5] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoSnakeEditor.BACK;
			width = 70;
			height = 40;
			x = ApoSnakeConstants.GAME_WIDTH - width - 5;
			y = ApoSnakeConstants.GAME_HEIGHT - 1 * height - 10;
			this.game.getButtons()[6] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoSnakeEditor.TEST;
			width = 70;
			height = 40;
			x = ApoSnakeConstants.GAME_WIDTH - 3 * width - 10 * 3;
			y = ApoSnakeConstants.GAME_HEIGHT - 1 * height - 10;
			this.game.getButtons()[7] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoSnakeEditor.UPLOAD;
			width = 70;
			height = 40;
			x = ApoSnakeConstants.GAME_WIDTH - 2 * width - 10 * 2;
			y = ApoSnakeConstants.GAME_HEIGHT - 1 * height - 10;
			this.game.getButtons()[8] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoSnakeEditor.XMINUS;
			width = 40;
			height = 40;
			x = 5;
			y = ApoSnakeConstants.GAME_HEIGHT - 1 * height - 10;
			this.game.getButtons()[9] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoSnakeEditor.XPLUS;
			width = 40;
			height = 40;
			x = 5 + 70;
			y = ApoSnakeConstants.GAME_HEIGHT - 1 * height - 10;
			this.game.getButtons()[10] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoSnakeEditor.YMINUS;
			width = 40;
			height = 40;
			x = 120;
			y = ApoSnakeConstants.GAME_HEIGHT - 1 * height - 10;
			this.game.getButtons()[11] = new ApoButton(null, x, y, width, height, function);
			
			function = ApoSnakeEditor.YPLUS;
			width = 40;
			height = 40;
			x = 120 + 70;
			y = ApoSnakeConstants.GAME_HEIGHT - 1 * height - 10;
			this.game.getButtons()[12] = new ApoButton(null, x, y, width, height, function);
			
			for (int i = 0; i < this.game.getButtons().length; i++) {
				this.game.getButtons()[i].setBOpaque(true);
			}
		}
	}
}
