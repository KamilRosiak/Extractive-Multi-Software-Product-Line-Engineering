package apoSlitherLink.game;

import java.awt.Color;
import java.awt.Font;

import org.apogames.entity.ApoButton;

import apoSlitherLink.ApoSlitherLinkConstants;

public class ApoSlitherLinkButtons {
	
	private ApoSlitherLinkPanel game;
	
	public ApoSlitherLinkButtons(ApoSlitherLinkPanel game) {
		this.game = game;
	}

	public void init() {
		if (this.game.getButtons() == null) {
			this.game.setButtons(new ApoButton[25]);
			
			Font font = new Font(Font.SANS_SERIF, Font.BOLD, 20);
			String text = ApoSlitherLinkMenu.QUIT;
			String function = ApoSlitherLinkMenu.QUIT;
			int width = 200;
			int height = 50;
			int x = this.game.getWidth()/2 - width/2;
			int y = this.game.getHeight() - 1 * height - 1 * 5;
			this.game.getButtons()[0] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(190, 230, 164), new Color(0, 102, 0), new Color(0, 102, 0), font, 50), x, y, width, height, function);
			
			font = new Font(Font.SANS_SERIF, Font.BOLD, 25);
			text = "Easy";
			function = ApoSlitherLinkMenu.EASY;
			width = 100;
			height = 100;
			x = 20;
			y = 160;
			this.game.getButtons()[1] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(190, 230, 164), new Color(0, 102, 0), new Color(0, 102, 0), font, 150), x, y, width, height, function);

			font = new Font(Font.SANS_SERIF, Font.BOLD, 20);
			text = "Medium";
			function = ApoSlitherLinkMenu.MEDIUM;
			width = 100;
			height = 100;
			x = 20 + 1 * 66 + 1 * width;
			y = 160;
			this.game.getButtons()[2] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(190, 230, 164), new Color(0, 102, 0), new Color(0, 102, 0), font, 150), x, y, width, height, function);

			font = new Font(Font.SANS_SERIF, Font.BOLD, 25);
			text = "Hard";
			function = ApoSlitherLinkMenu.HARD;
			width = 100;
			height = 100;
			x = 20 + 2 * 66 + 1 + 2 * width;
			y = 160;
			this.game.getButtons()[3] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(190, 230, 164), new Color(0, 102, 0), new Color(0, 102, 0), font, 150), x, y, width, height, function);
			
			font = new Font(Font.SANS_SERIF, Font.BOLD, 20);
			text = ApoSlitherLinkMenu.OPTIONS;
			function = ApoSlitherLinkMenu.OPTIONS;
			width = 200;
			height = 50;
			x = this.game.getWidth()/2 + 1 * 5;
			y = 300 + 1 * 10 + 1 * height;
			this.game.getButtons()[4] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(190, 230, 164), new Color(0, 102, 0), new Color(0, 102, 0), font, 50), x, y, width, height, function);
			
			text = ApoSlitherLinkGame.MENU_STRING;
			function = ApoSlitherLinkGame.MENU;
			width = 150;
			height = 40;
			x = this.game.getWidth() - 1 * 10 - 1 * width;
			y = 15;
			this.game.getButtons()[5] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(205, 245, 195), new Color(0, 102, 0), new Color(0, 102, 0), font, 50), x, y, width, height, function);

			text = ApoSlitherLinkGame.RETRY_STRING;
			function = ApoSlitherLinkGame.RETRY;
			width = 150;
			height = 40;
			x = 10;
			y = 15;
			this.game.getButtons()[6] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(205, 245, 195), new Color(0, 102, 0), new Color(0, 102, 0), font, 50), x, y, width, height, function);
			
			font = new Font(Font.SANS_SERIF, Font.BOLD, 20);
			text = ApoSlitherLinkTutorial.MENU_STRING;
			function = ApoSlitherLinkTutorial.MENU;
			width = 200;
			height = 40;
			x = ApoSlitherLinkConstants.GAME_WIDTH/2 - 1 * width/2;
			y = ApoSlitherLinkConstants.GAME_HEIGHT - 1 * height - 1 * 5;
			this.game.getButtons()[7] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(190, 230, 164), new Color(0, 102, 0), new Color(0, 102, 0), font, 50), x, y, width, height, function);
			
			font = new Font(Font.SANS_SERIF, Font.BOLD, 25);
			text = ApoSlitherLinkGame.NEXT_STRING;
			function = ApoSlitherLinkGame.NEXT;
			width = 200;
			height = 70;
			x = this.game.getWidth()/2 - width/2;
			y = 200 + 1 * height;
			this.game.getButtons()[8] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(190, 230, 164), new Color(0, 102, 0), new Color(0, 102, 0), font, 50), x, y, width, height, function);

			font = new Font(Font.SANS_SERIF, Font.BOLD, 25);
			text = ApoSlitherLinkGame.RETURN_STRING;
			function = ApoSlitherLinkGame.RETURN;
			width = 250;
			height = 50;
			x = this.game.getWidth()/2 - width/2;
			y = ApoSlitherLinkConstants.GAME_HEIGHT - 1 * 10 - 1 * height;
			this.game.getButtons()[9] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(190, 230, 164), new Color(0, 102, 0), new Color(0, 102, 0), font, 50), x, y, width, height, function);

			text = ApoSlitherLinkGame.RIGHT_STRING;
			function = ApoSlitherLinkGame.RIGHT;
			width = 40;
			height = 40;
			x = this.game.getWidth() - 1 * 170 - 1 * width;
			y = 15;
			this.game.getButtons()[10] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(205, 245, 195), new Color(0, 102, 0), new Color(0, 102, 0), font, 50), x, y, width, height, function);

			text = ApoSlitherLinkGame.LEFT_STRING;
			function = ApoSlitherLinkGame.LEFT;
			width = 40;
			height = 40;
			x = 170;
			y = 15;
			this.game.getButtons()[11] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(205, 245, 195), new Color(0, 102, 0), new Color(0, 102, 0), font, 50), x, y, width, height, function);
			
			text = ApoSlitherLinkLevelChooser.RIGHT_STRING;
			function = ApoSlitherLinkLevelChooser.RIGHT;
			width = 40;
			height = 40;
			x = this.game.getWidth() - 1 * 170 - 1 * width;
			y = 15;
			this.game.getButtons()[12] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(205, 245, 195), new Color(0, 102, 0), new Color(0, 102, 0), font, 50), x, y, width, height, function);

			text = ApoSlitherLinkLevelChooser.LEFT_STRING;
			function = ApoSlitherLinkLevelChooser.LEFT;
			width = 40;
			height = 40;
			x = 170;
			y = 15;
			this.game.getButtons()[13] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(205, 245, 195), new Color(0, 102, 0), new Color(0, 102, 0), font, 50), x, y, width, height, function);

			text = ApoSlitherLinkLevelChooser.MENU_STRING;
			function = ApoSlitherLinkLevelChooser.MENU;
			width = 200;
			height = 50;
			x = ApoSlitherLinkConstants.GAME_WIDTH/2  - 1 * width/2;
			y = ApoSlitherLinkConstants.GAME_HEIGHT - 1 * height - 1 * 5;
			this.game.getButtons()[14] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(190, 230, 164), new Color(0, 102, 0), new Color(0, 102, 0), font, 50), x, y, width, height, function);
			
			font = new Font(Font.SANS_SERIF, Font.BOLD, 20);
			text = ApoSlitherLinkMenu.EDITOR;
			function = ApoSlitherLinkMenu.EDITOR;
			width = 200;
			height = 50;
			x = this.game.getWidth()/2 - width - 1 * 5;
			y = 300 + 1 * 10 + 1 * height;
			this.game.getButtons()[15] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(190, 230, 164), new Color(0, 102, 0), new Color(0, 102, 0), font, 50), x, y, width, height, function);

			font = new Font(Font.SANS_SERIF, Font.BOLD, 16);
			text = ApoSlitherLinkEditor.LEFTWIDTH_STRING;
			function = ApoSlitherLinkEditor.LEFTWIDTH;
			width = 20;
			height = 20;
			x = 15;
			y = 12;
			this.game.getButtons()[16] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(205, 245, 195), new Color(0, 102, 0), new Color(0, 102, 0), font, 50), x, y, width, height, function);

			text = ApoSlitherLinkEditor.RIGHTWIDTH_STRING;
			function = ApoSlitherLinkEditor.RIGHTWIDTH;
			width = 20;
			height = 20;
			x = 75;
			y = 12;
			this.game.getButtons()[17] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(205, 245, 195), new Color(0, 102, 0), new Color(0, 102, 0), font, 50), x, y, width, height, function);

			text = ApoSlitherLinkEditor.LEFTHEIGHT_STRING;
			function = ApoSlitherLinkEditor.LEFTHEIGHT;
			width = 20;
			height = 20;
			x = 15;
			y = 33;
			this.game.getButtons()[18] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(205, 245, 195), new Color(0, 102, 0), new Color(0, 102, 0), font, 50), x, y, width, height, function);

			text = ApoSlitherLinkEditor.RIGHTHEIGHT_STRING;
			function = ApoSlitherLinkEditor.RIGHTHEIGHT;
			width = 20;
			height = 20;
			x = 75;
			y = 33;
			this.game.getButtons()[19] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(205, 245, 195), new Color(0, 102, 0), new Color(0, 102, 0), font, 50), x, y, width, height, function);

			font = new Font(Font.SANS_SERIF, Font.BOLD, 20);
			text = ApoSlitherLinkEditor.RETURN_STRING;
			function = ApoSlitherLinkEditor.RETURN;
			width = 150;
			height = 40;
			x = ApoSlitherLinkConstants.GAME_WIDTH - width - 1 * 5;
			y = 15;
			this.game.getButtons()[20] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(205, 245, 195), new Color(0, 102, 0), new Color(0, 102, 0), font, 50), x, y, width, height, function);

			text = ApoSlitherLinkEditor.SET_STRING;
			function = ApoSlitherLinkEditor.SET;
			width = 100;
			height = 40;
			x = 100;
			y = 15;
			this.game.getButtons()[21] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(205, 245, 195), new Color(0, 102, 0), new Color(0, 102, 0), font, 50), x, y, width, height, function);
			
			text = ApoSlitherLinkEditor.TEST_STRING;
			function = ApoSlitherLinkEditor.TEST;
			width = 100;
			height = 40;
			x = 2 * 100 + 1 * 5;
			y = 15;
			this.game.getButtons()[22] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(205, 245, 195), new Color(0, 102, 0), new Color(0, 102, 0), font, 50), x, y, width, height, function);

			text = ApoSlitherLinkEditor.UPLOAD_STRING;
			function = ApoSlitherLinkEditor.UPLOAD;
			width = 100;
			height = 40;
			x = 2 * 100 + 1 * 5;
			y = 15;
			this.game.getButtons()[23] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(205, 245, 195), new Color(0, 102, 0), new Color(0, 102, 0), font, 50), x, y, width, height, function);

			font = new Font(Font.SANS_SERIF, Font.BOLD, 25);
			text = "Custom";
			function = ApoSlitherLinkMenu.CUSTOM;
			width = 100;
			height = 100;
			x = ApoSlitherLinkConstants.GAME_WIDTH - width - 20;
			y = 160;
			this.game.getButtons()[24] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(190, 230, 164), new Color(0, 102, 0), new Color(0, 102, 0), font, 150), x, y, width, height, function);

		}
	}
}
