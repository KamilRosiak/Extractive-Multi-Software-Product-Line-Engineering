package apoPongBeat.game;

import java.awt.Color;
import java.awt.Font;

import org.apogames.entity.ApoButton;

import apoPongBeat.ApoPongBeatConstants;

public class ApoPongBeatButtons {
	
	private ApoPongBeatPanel game;
	
	public ApoPongBeatButtons(ApoPongBeatPanel game) {
		this.game = game;
	}

	public void init() {
		if (this.game.getButtons() == null) {
			this.game.setButtons(new ApoButton[6]);
			
			Font font = this.game.getMyFont().deriveFont(40f);
			String text = ApoPongBeatMenu.QUIT;
			String function = ApoPongBeatMenu.QUIT;
			int width = 200;
			int height = 50;
			int x = (ApoPongBeatConstants.GAME_WIDTH - width)/2;
			int y = ApoPongBeatConstants.MAX_Y - height - 1 * 10;
			this.game.getButtons()[0] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(0, 0, 0, 0), Color.WHITE, new Color(0, 0, 0, 0), font, 5), x, y, width, height, function);
			
			text = ApoPongBeatMenu.START;
			function = ApoPongBeatMenu.START;
			width = 200;
			height = 50;
			x = (ApoPongBeatConstants.GAME_WIDTH - width)/2;
			y = ApoPongBeatConstants.MIN_Y + 1 * 10;
			this.game.getButtons()[1] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(0, 0, 0, 0), Color.WHITE, new Color(255, 0, 0, 0), font, 5), x, y, width, height, function);

			text = ApoPongBeatGame.MENU_STRING;
			function = ApoPongBeatGame.MENU;
			width = 100;
			height = 40;
			x = ApoPongBeatConstants.GAME_WIDTH - width - 5;
			y = ApoPongBeatConstants.GAME_HEIGHT - height - 5;
			this.game.getButtons()[2] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(0, 0, 0, 0), Color.WHITE, new Color(255, 0, 0, 0), font, 5), x, y, width, height, function);
			
			text = ApoPongBeatOptions.MENU_STRING;
			function = ApoPongBeatOptions.MENU;
			width = 100;
			height = 40;
			x = ApoPongBeatConstants.GAME_WIDTH - width - 5;
			y = ApoPongBeatConstants.GAME_HEIGHT - height - 5;
			this.game.getButtons()[3] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(0, 0, 0, 0), Color.WHITE, new Color(255, 0, 0, 0), font, 5), x, y, width, height, function);
			
			text = ApoPongBeatMenu.OPTIONS;
			function = ApoPongBeatMenu.OPTIONS;
			width = 200;
			height = 50;
			x = (ApoPongBeatConstants.GAME_WIDTH - width)/2;
			y = ApoPongBeatConstants.MIN_Y + 2 * 10 + 1 * height;
			this.game.getButtons()[4] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(0, 0, 0, 0), Color.WHITE, new Color(255, 0, 0, 0), font, 5), x, y, width, height, function);

			text = ApoPongBeatMenu.ACHIEVEMENTS;
			function = ApoPongBeatMenu.ACHIEVEMENTS;
			width = 300;
			height = 50;
			x = (ApoPongBeatConstants.GAME_WIDTH - width)/2;
			y = ApoPongBeatConstants.MIN_Y + 3 * 10 + 2 * height;
			this.game.getButtons()[5] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(0, 0, 0, 0), Color.WHITE, new Color(255, 0, 0, 0), font, 5), x, y, width, height, function);
		}
	}
}
