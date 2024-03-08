package apoSimpleSudoku.game;

import org.apogames.entity.ApoButton;

import apoSimpleSudoku.ApoSimpleSudokuConstants;

/**
 * Hilfsklasse, um die Buttons für das Spiel zu erstellen
 * @author Dirk Aporius
 *
 */
public class ApoSimpleSudokuButtons {
	/** das eigentliche Spielobjekt */
	private ApoSimpleSudokuPanel game;
	/**
	 * Konstruktor
	 * @param game : Das eigentliche Spielobjekt
	 */
	public ApoSimpleSudokuButtons(ApoSimpleSudokuPanel game) {
		this.game = game;
	}

	/**
	 * wird aufgerufen, um die ganzen Buttons zu erstellen
	 */
	public void init() {
		if (this.game.getButtons() == null) {
			this.game.setButtons(new ApoButton[12]);
			
			int gameWidth = ApoSimpleSudokuConstants.GAME_WIDTH;
			int gameHeight = ApoSimpleSudokuConstants.GAME_HEIGHT;
			
			String function = ApoSimpleSudokuMenu.QUIT;
			String text = "Quit";
			int width = 200;
			int height = 80;
//			int x = gameWidth - width - 10;
			int x = gameWidth/2 - width/2;
			int y = gameHeight - height - 15;
			this.game.getButtons()[0] = new ApoButton(this.game.getImages().getButtonImageForMenu(width, height, text), x, y, width, height, function);

			function = ApoSimpleSudokuMenu.GAME;
			text = "Play";
			width = 400;
			height = 70;
			x = gameWidth/2 - width/2;
			y = 15;
			this.game.getButtons()[1] = new ApoButton(this.game.getImages().getButtonImageForMenu(width, height, text), x, y, width, height, function);
			
			function = ApoSimpleSudokuMenu.TUTORIAL;
			text = "Help";
			width = 400;
			height = 70;
			x = gameWidth/2 - width/2;
			y = 15 + height + 20;
			this.game.getButtons()[2] = new ApoButton(this.game.getImages().getButtonImageForMenu(width, height, text), x, y, width, height, function);

			function = ApoSimpleSudokuMenu.OPTIONS;
			text = "Options";
			width = 400;
			height = 70;
			x = gameWidth/2 - width/2;
			y = gameHeight - 2 * height - 15 - 20;
			this.game.getButtons()[3] = new ApoButton(this.game.getImages().getButtonImageForMenu(width, height, text), x, y, width, height, function);

			function = ApoSimpleSudokuMenu.CREDITS;
			text = "Credits";
			width = 380;
			height = 60;
//			x = 10;
//			y = gameHeight - 1 * height - 1 * 20;
			x = gameWidth/2 - width/2;
			y = gameHeight - 2 * height - 15 - 20;
			this.game.getButtons()[4] = new ApoButton(this.game.getImages().getButtonImageForMenu(width, height, text), x, y, width, height, function);
			
			function = ApoSimpleSudokuGame.MENU;
			text = "back";
			width = 140;
			height = 45;
			x = gameWidth - width - 5;
			y = gameHeight - height - 5;
			this.game.getButtons()[5] = new ApoButton(this.game.getImages().getButtonImageForGameBack(width, height, text), x, y, width, height, function);
			
			function = ApoSimpleSudokuCredits.MENU;
			text = "back";
			width = 140;
			height = 40;
			x = gameWidth - width - 5;
			y = gameHeight - height - 5;
			this.game.getButtons()[6] = new ApoButton(this.game.getImages().getButtonImageForBack(width, height, text), x, y, width, height, function);
			
			function = ApoSimpleSudokuOptions.MENU;
			text = "back";
			width = 140;
			height = 50;
			x = gameWidth - width - 5;
			y = gameHeight - height - 10;
			this.game.getButtons()[7] = new ApoButton(this.game.getImages().getButtonImageForBack(width, height, text), x, y, width, height, function);
			
			function = ApoSimpleSudokuGame.RESTART;
			text = "reload";
			width = 170;
			height = 45;
			x = gameWidth - width - 5;
			y = gameHeight - 2 * height - 5 - 10;
			this.game.getButtons()[8] = new ApoButton(this.game.getImages().getButtonImageForGameBack(width, height, text), x, y, width, height, function);
			
			function = ApoSimpleSudokuLevelChooser.MENU;
			text = "back";
			width = 140;
			height = 50;
			x = gameWidth - width - 5;
			y = gameHeight - height - 10;
			this.game.getButtons()[9] = new ApoButton(this.game.getImages().getButtonImageForBack(width, height, text), x, y, width, height, function);
			
			function = ApoSimpleSudokuGame.NEXT;
			text = "next";
			width = 140;
			height = 45;
			x = 225 - width/2;
			y = 340 - height - 10;
			this.game.getButtons()[10] = new ApoButton(this.game.getImages().getButtonImageForGameBack(width, height, text), x, y, width, height, function);
			
			function = ApoSimpleSudokuHelp.MENU;
			text = "back";
			width = 140;
			height = 50;
			x = gameWidth - width - 5;
			y = gameHeight - height - 10;
			this.game.getButtons()[11] = new ApoButton(this.game.getImages().getButtonImageForBack(width, height, text), x, y, width, height, function);

		}
	}
}
