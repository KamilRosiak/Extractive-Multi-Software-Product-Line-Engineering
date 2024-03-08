package apoJump.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import org.apogames.entity.ApoButton;

import apoJump.ApoJumpConstants;
import apoJump.ApoJumpImageContainer;

public class ApoJumpButton {

	private ApoJumpPanel game;
	
	public ApoJumpButton(ApoJumpPanel game) {
		this.game = game;
	}
	
	public void init() {
		if ((this.game != null) && (this.game.getButtons() == null)) {
			this.game.setButtons(new ApoButton[15]);
			
			BufferedImage iButtonBackground = ApoJumpImageContainer.iButtonBackground;
			
			Font font = ApoJumpConstants.FONT_BUTTON;
			String text = "quit";
			String function = ApoJumpStateMenu.BUTTON_QUIT;
			int width = iButtonBackground.getWidth();
			int height = iButtonBackground.getHeight();
			int x = ApoJumpConstants.GAME_WIDTH/2;
			int y = ApoJumpConstants.GAME_HEIGHT - 1 * height - 2 * 5;
			BufferedImage iButton = this.game.getImages().getButtonWithImage(width * 3, height, iButtonBackground, text, Color.BLACK, Color.YELLOW, Color.RED, font);
			this.game.getButtons()[0] = new ApoButton(iButton, x, y, width, height, function);
			
			text = "start";
			function = ApoJumpStateMenu.BUTTON_GAME;
			width = iButtonBackground.getWidth();
			height = iButtonBackground.getHeight();
			x = ApoJumpConstants.GAME_WIDTH/2 - width/2;
			y = 130;
			iButton = this.game.getImages().getButtonWithImage(width * 3, height, iButtonBackground, text, Color.BLACK, Color.YELLOW, Color.RED, font);
			this.game.getButtons()[1] = new ApoButton(iButton, x, y, width, height, function);
			
			text = "help";
			function = ApoJumpStateMenu.BUTTON_HELP;
			width = iButtonBackground.getWidth();
			height = iButtonBackground.getHeight();
			x = ApoJumpConstants.GAME_WIDTH - width - 10;
			y = 160 + height * 1 + 1 * 10;
			iButton = this.game.getImages().getButtonWithImage(width * 3, height, iButtonBackground, text, Color.BLACK, Color.YELLOW, Color.RED, font);
			this.game.getButtons()[2] = new ApoButton(iButton, x, y, width, height, function);

			text = "menu";
			function = ApoJumpStateGame.BUTTON_BACK;
			width = iButtonBackground.getWidth();
			height = iButtonBackground.getHeight();
			x = ApoJumpConstants.GAME_WIDTH - width - 1 * 5;
			y = ApoJumpConstants.GAME_HEIGHT - 1 * height - 2 * 5;
			iButton = this.game.getImages().getButtonWithImage(width * 3, height, iButtonBackground, text, Color.BLACK, Color.YELLOW, Color.RED, font);
			this.game.getButtons()[3] = new ApoButton(iButton, x, y, width, height, function);
			
			text = "achievements";
			function = ApoJumpStateMenu.BUTTON_ACHIEVEMENTS;
			width = iButtonBackground.getWidth();
			height = iButtonBackground.getHeight();
			x = 10;
			y = 100 + height * 2 + 2 * 10;
			iButton = this.game.getImages().getButtonWithImage(width * 3, height, iButtonBackground, text, Color.BLACK, Color.YELLOW, Color.RED, font);
			this.game.getButtons()[4] = new ApoButton(iButton, x, y, width, height, function);
			
			text = "highscore";
			function = ApoJumpStateMenu.BUTTON_HIGHSCORE;
			width = iButtonBackground.getWidth();
			height = iButtonBackground.getHeight();
			x = ApoJumpConstants.GAME_WIDTH * 1 / 4;
			y = ApoJumpConstants.GAME_HEIGHT/2 + height/2 + 40;
			iButton = this.game.getImages().getButtonWithImage(width * 3, height, iButtonBackground, text, Color.BLACK, Color.YELLOW, Color.RED, font);
			this.game.getButtons()[5] = new ApoButton(iButton, x, y, width, height, function);
			
			text = "menu";
			function = ApoJumpStateOptions.BUTTON_BACK;
			width = iButtonBackground.getWidth();
			height = iButtonBackground.getHeight();
			x = ApoJumpConstants.GAME_WIDTH - width - 1 * 5;
			y = ApoJumpConstants.GAME_HEIGHT - 1 * height - 1 * 5;
			iButton = this.game.getImages().getButtonWithImage(width * 3, height, iButtonBackground, text, Color.BLACK, Color.YELLOW, Color.RED, font);
			this.game.getButtons()[6] = new ApoButton(iButton, x, y, width, height, function);
			
			text = "menu";
			function = ApoJumpStateAchievements.BUTTON_BACK;
			width = iButtonBackground.getWidth();
			height = iButtonBackground.getHeight();
			x = ApoJumpConstants.GAME_WIDTH - width - 1 * 5;
			y = ApoJumpConstants.GAME_HEIGHT - 1 * height - 1 * 5;
			iButton = this.game.getImages().getButtonWithImage(width * 3, height, iButtonBackground, text, Color.BLACK, Color.YELLOW, Color.RED, font);
			this.game.getButtons()[7] = new ApoButton(iButton, x, y, width, height, function);
			
			text = "menu";
			function = ApoJumpStateHighscore.BUTTON_BACK;
			width = iButtonBackground.getWidth();
			height = iButtonBackground.getHeight();
			x = ApoJumpConstants.GAME_WIDTH - width - 1 * 5;
			y = ApoJumpConstants.GAME_HEIGHT - 1 * height - 1 * 5;
			iButton = this.game.getImages().getButtonWithImage(width * 3, height, iButtonBackground, text, Color.BLACK, Color.YELLOW, Color.RED, font);
			this.game.getButtons()[8] = new ApoButton(iButton, x, y, width, height, function);
			
			text = "retry";
			function = ApoJumpStateGame.BUTTON_RETRY;
			width = iButtonBackground.getWidth();
			height = iButtonBackground.getHeight();
			x = 5;
			y = ApoJumpConstants.GAME_HEIGHT - 1 * height - 2 * 5;
			iButton = this.game.getImages().getButtonWithImage(width * 3, height, iButtonBackground, text, Color.BLACK, Color.YELLOW, Color.RED, font);
			this.game.getButtons()[9] = new ApoButton(iButton, x, y, width, height, function);
			
			text = "upload";
			function = ApoJumpStateGame.BUTTON_UPLOAD;
			width = iButtonBackground.getWidth();
			height = iButtonBackground.getHeight();
			x = ApoJumpConstants.GAME_WIDTH/2 - width/2;
			y = ApoJumpConstants.GAME_HEIGHT/2 + 110;
			iButton = this.game.getImages().getButtonWithImage(width * 3, height, iButtonBackground, text, Color.BLACK, Color.YELLOW, Color.RED, font);
			this.game.getButtons()[10] = new ApoButton(iButton, x, y, width, height, function);
			
			BufferedImage iButtonHighscore = ApoJumpImageContainer.iButtonLeft;
			text = " < ";
			function = ApoJumpStateHighscore.LEFT;
			width = iButtonHighscore.getWidth();
			height = iButtonHighscore.getHeight();
			x = 5;
			y = ApoJumpConstants.GAME_HEIGHT/2 + 140;
			iButton = this.game.getImages().getButtonWithImage(width * 3, height, iButtonHighscore, text, Color.BLACK, Color.YELLOW, Color.RED, font);
			this.game.getButtons()[11] = new ApoButton(iButton, x, y, width, height, function);
			
			text = " > ";
			function = ApoJumpStateHighscore.RIGHT;
			width = iButtonHighscore.getWidth();
			height = iButtonHighscore.getHeight();
			x = ApoJumpConstants.GAME_WIDTH - width - 5;
			y = ApoJumpConstants.GAME_HEIGHT/2 + 140;
			iButton = this.game.getImages().getButtonWithImage(width * 3, height, iButtonHighscore, text, Color.BLACK, Color.YELLOW, Color.RED, font);
			this.game.getButtons()[12] = new ApoButton(iButton, x, y, width, height, function);
			
			text = "options";
			function = ApoJumpStateMenu.BUTTON_OPTIONS;
			width = iButtonBackground.getWidth();
			height = iButtonBackground.getHeight();
			x = ApoJumpConstants.GAME_WIDTH - width - 50;
			y = 290 + height * 1 + 1 * 10;
			iButton = this.game.getImages().getButtonWithImage(width * 3, height, iButtonBackground, text, Color.BLACK, Color.YELLOW, Color.RED, font);
			this.game.getButtons()[13] = new ApoButton(iButton, x, y, width, height, function);
			
			text = "menu";
			function = ApoJumpStateHelp.BUTTON_BACK;
			width = iButtonBackground.getWidth();
			height = iButtonBackground.getHeight();
			x = ApoJumpConstants.GAME_WIDTH - width - 1 * 5;
			y = ApoJumpConstants.GAME_HEIGHT - 1 * height - 10 * 5;
			iButton = this.game.getImages().getButtonWithImage(width * 3, height, iButtonBackground, text, Color.BLACK, Color.YELLOW, Color.RED, font);
			this.game.getButtons()[14] = new ApoButton(iButton, x, y, width, height, function);

		}
	}
	
}
