package apoRelax.game;

import java.awt.Color;
import java.awt.Font;

import org.apogames.entity.ApoButton;

import apoRelax.ApoRelaxConstants;

public class ApoRelaxButtons {
	
	private ApoRelaxPanel game;
	
	public ApoRelaxButtons(ApoRelaxPanel game) {
		this.game = game;
	}

	public void init() {
		if (this.game.getButtons() == null) {
			this.game.setButtons(new ApoButton[16]);
			
			Font font = new Font(Font.SANS_SERIF, Font.BOLD, 30);
			String text = "X";
			String function = ApoRelaxMenu.QUIT;
			int width = 45;
			int height = 45;
			int x = ApoRelaxConstants.GAME_WIDTH - 15 - width;
			int y = ApoRelaxConstants.GAME_HEIGHT - 1 * height - 1 * 15;
			this.game.getButtons()[0] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 255), x, y, width, height, function);
			
			text = "play";
			function = ApoRelaxMenu.START;
			width = 250;
			height = 60;
			x = ApoRelaxConstants.GAME_WIDTH/2 - width/2;
			y = 30;
			this.game.getButtons()[1] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function);

			text = "level";
			function = ApoRelaxGame.MENU;
			width = 150;
			height = 40;
			x = ApoRelaxConstants.GAME_WIDTH - 1 * 10 - 1 * width;
			y = ApoRelaxConstants.GAME_HEIGHT - 1 * height - 1 * 5;
			this.game.getButtons()[2] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function);

			text = "C";
			function = ApoRelaxMenu.CREDITS;
			width = 45;
			height = 45;
			x = 15;
			y = ApoRelaxConstants.GAME_HEIGHT - 1 * height - 1 * 15;
			this.game.getButtons()[3] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 255), x, y, width, height, function);

			text = ApoRelaxCredits.MENU_STRING;
			function = ApoRelaxCredits.MENU;
			width = 150;
			height = 40;
			x = ApoRelaxConstants.GAME_WIDTH - 1 * 15 - 1 * width;
			y = ApoRelaxConstants.GAME_HEIGHT - 1 * height - 1 * 15;
			this.game.getButtons()[4] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function);

			text = "editor";
			function = ApoRelaxMenu.EDITOR;
			width = 250;
			height = 60;
			x = ApoRelaxConstants.GAME_WIDTH/2 - width/2;
			y = ApoRelaxConstants.GAME_HEIGHT/2 + 70;
			this.game.getButtons()[5] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function);

			text = "menu";
			function = ApoRelaxEditor.MENU;
			width = 150;
			height = 40;
			x = ApoRelaxConstants.GAME_WIDTH - 1 * 10 - 1 * width;
			y = ApoRelaxConstants.GAME_HEIGHT - 1 * height - 1 * 5;
			this.game.getButtons()[6] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function);

			text = "options";
			function = ApoRelaxMenu.OPTIONS;
			width = 250;
			height = 60;
			x = ApoRelaxConstants.GAME_WIDTH/2 - width/2;
			y = ApoRelaxConstants.GAME_HEIGHT - 30 - height;
			this.game.getButtons()[7] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function);
			
			text = ApoRelaxOptions.MENU_STRING;
			function = ApoRelaxOptions.MENU;
			width = 150;
			height = 40;
			x = ApoRelaxConstants.GAME_WIDTH - 1 * 15 - 1 * width;
			y = ApoRelaxConstants.GAME_HEIGHT - 1 * height - 1 * 15;
			this.game.getButtons()[8] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function);

			text = "";
			function = ApoRelaxOptions.MUSIC;
			width = 40;
			height = 40;
			x = ApoRelaxConstants.GAME_WIDTH/2;
			y = 70;
			this.game.getButtons()[9] = new ApoRelaxOptionsButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function);

			text = "";
			function = ApoRelaxOptions.SOUND;
			width = 40;
			height = 40;
			x = ApoRelaxConstants.GAME_WIDTH/2;
			y = 115;
			this.game.getButtons()[10] = new ApoRelaxOptionsButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function, true);

			text = "next";
			function = ApoRelaxGame.NEXT;
			width = 150;
			height = 40;
			x = ApoRelaxConstants.GAME_WIDTH - 1 * 10 - 1 * width;
			y = ApoRelaxConstants.GAME_HEIGHT - 2 * height - 2 * 5;
			this.game.getButtons()[11] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, true, font, 10), x, y, width, height, function);

			text = "random levels";
			function = ApoRelaxMenu.RANDOM;
			width = 250;
			height = 60;
			x = ApoRelaxConstants.GAME_WIDTH/2 - width/2;
			y = ApoRelaxConstants.GAME_HEIGHT/2 - 70 - height;
			this.game.getButtons()[12] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function);

			text = "load";
			function = ApoRelaxEditor.LOAD;
			width = 150;
			height = 40;
			x = ApoRelaxConstants.GAME_WIDTH - 1 * 10 - 1 * width;
			y = 1 * 5;
			this.game.getButtons()[13] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, true, font, 10), x, y, width, height, function);

			text = "test";
			function = ApoRelaxEditor.TEST;
			width = 150;
			height = 40;
			x = ApoRelaxConstants.GAME_WIDTH - 1 * 10 - 1 * width;
			y = ApoRelaxConstants.GAME_HEIGHT - 2 * height - 2 * 5;
			this.game.getButtons()[14] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, true, font, 10), x, y, width, height, function);

			text = ApoRelaxLevelChooser.MENU_STRING;
			function = ApoRelaxLevelChooser.MENU;
			width = 150;
			height = 40;
			x = ApoRelaxConstants.GAME_WIDTH - 1 * 15 - 1 * width;
			y = ApoRelaxConstants.GAME_HEIGHT - 1 * height - 1 * 15;
			this.game.getButtons()[15] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function);

		}
	}
}