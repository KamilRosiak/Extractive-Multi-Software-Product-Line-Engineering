package apoNotSoSimple.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import org.apogames.ApoConstants;
import org.apogames.entity.ApoButton;

import apoNotSoSimple.ApoNotSoSimpleConstants;
import apoNotSoSimple.ApoNotSoSimpleImages;

public class ApoNotSoSimpleButtons {
	
	private ApoNotSoSimplePanel game;
	
	public ApoNotSoSimpleButtons(ApoNotSoSimplePanel game) {
		this.game = game;
	}

	public void init() {
		if (this.game.getButtons() == null) {
			this.game.setButtons(new ApoButton[32]);
			
			Font font = new Font(Font.SANS_SERIF, Font.BOLD, 30);
			String text = "X";
			String function = ApoNotSoSimpleMenu.QUIT;
			int width = 45;
			int height = 45;
			int x = ApoConstants.GAME_WIDTH - 15 - width;
			int y = ApoConstants.GAME_HEIGHT - 1 * height - 1 * 15;
			this.game.getButtons()[0] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 255), x, y, width, height, function);
			
			text = "play";
			function = ApoNotSoSimpleMenu.START;
			width = 250;
			height = 60;
			x = ApoConstants.GAME_WIDTH/2 - width/2;
			y = 30;
			this.game.getButtons()[1] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, true, font, 10), x, y, width, height, function);

			text = "level";
			function = ApoNotSoSimpleGame.MENU;
			width = 150;
			height = 40;
			x = ApoConstants.GAME_WIDTH - 1 * 15 - 1 * width;
			y = ApoConstants.GAME_HEIGHT - 1 * height - 1 * 5;
			this.game.getButtons()[2] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, true, font, 10), x, y, width, height, function);

			text = "C";
			function = ApoNotSoSimpleMenu.CREDITS;
			width = 45;
			height = 45;
			x = 15;
			y = ApoConstants.GAME_HEIGHT - 1 * height - 1 * 15;
			this.game.getButtons()[3] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 255), x, y, width, height, function);

			text = ApoNotSoSimpleCredits.MENU_STRING;
			function = ApoNotSoSimpleCredits.MENU;
			width = 150;
			height = 40;
			x = ApoConstants.GAME_WIDTH - 1 * 15 - 1 * width;
			y = ApoConstants.GAME_HEIGHT - 1 * height - 1 * 15;
			this.game.getButtons()[4] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, true, font, 10), x, y, width, height, function);

			text = "editor";
			function = ApoNotSoSimpleMenu.EDITOR;
			width = 250;
			height = 60;
			x = ApoConstants.GAME_WIDTH/2 - width/2;
			y = ApoConstants.GAME_HEIGHT/2 + 70;
			this.game.getButtons()[5] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), true, false, font, 10), x, y, width, height, function);

			text = ApoNotSoSimpleEditor.MENU_STRING;
			function = ApoNotSoSimpleEditor.MENU;
			width = 150;
			height = 40;
			x = ApoConstants.GAME_WIDTH - 1 * 10 - 1 * width;
			y = ApoConstants.GAME_HEIGHT - 1 * height - 1 * 5;
			this.game.getButtons()[6] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function);

			font = new Font(Font.SANS_SERIF, Font.BOLD, 20);
			text = "test";
			function = ApoNotSoSimpleEditor.TEST;
			width = 75;
			height = 40;
			x = ApoConstants.GAME_WIDTH - 1 * 10 - 1 * width;
			y = ApoConstants.GAME_HEIGHT - 2 * height - 2 * 5;
			this.game.getButtons()[7] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function);

			text = "upload";
			function = ApoNotSoSimpleEditor.UPLOAD;
			width = 75;
			height = 40;
			x = ApoConstants.GAME_WIDTH - 1 * 10 - 2 * width;
			y = ApoConstants.GAME_HEIGHT - 2 * height - 2 * 5;
			this.game.getButtons()[8] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function);

			text = " < ";
			function = ApoNotSoSimpleEditor.LEFT_LAYER;
			width = 30;
			height = 30;
			x = ApoNotSoSimpleConstants.LEVEL_WIDTH  + 1 * 10  + 10;
			y = 50;
			this.game.getButtons()[9] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function);

			text = " > ";
			function = ApoNotSoSimpleEditor.RIGHT_LAYER;
			width = 30;
			height = 30;
			x = ApoConstants.GAME_WIDTH - 5 - width;
			y = 50;
			this.game.getButtons()[10] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function);

			text = "";
			function = ApoNotSoSimpleEditor.PLAYER;
			BufferedImage iEditorImage = ApoNotSoSimpleImages.getImageSimpleEditor(ApoNotSoSimpleImages.ORIGINAL_PLAYER, Color.YELLOW, Color.RED);
			width = iEditorImage.getWidth() / 3;
			height = iEditorImage.getHeight();
			x = ApoNotSoSimpleConstants.LEVEL_WIDTH + 10 + 30;
			y = 90 + 0 * height + 0 * 5;
			this.game.getButtons()[12] = new ApoButton(iEditorImage, x, y, width, height, function);
			
			function = ApoNotSoSimpleEditor.FINISH;
			iEditorImage = ApoNotSoSimpleImages.getImageSimpleEditor(ApoNotSoSimpleImages.ORIGINAL_FINISH, Color.YELLOW, Color.RED);
			width = iEditorImage.getWidth() / 3;
			height = iEditorImage.getHeight();
			x = ApoConstants.GAME_WIDTH - width - 25;
			y = 90 + 0 * height + 0 * 5;
			this.game.getButtons()[19] = new ApoButton(iEditorImage, x, y, width, height, function);
			
			function = ApoNotSoSimpleEditor.FIXED;
			iEditorImage = ApoNotSoSimpleImages.getImageSimpleEditor(ApoNotSoSimpleImages.ORIGINAL_FIXED, Color.YELLOW, Color.RED);
			width = iEditorImage.getWidth() / 3;
			height = iEditorImage.getHeight();
			x = ApoNotSoSimpleConstants.LEVEL_WIDTH + 10 + 30;
			y = 90 + 1 * height + 1 * 5;
			this.game.getButtons()[11] = new ApoButton(iEditorImage, x, y, width, height, function);
			
			function = ApoNotSoSimpleEditor.UP;
			iEditorImage = ApoNotSoSimpleImages.getImageSimpleEditor(ApoNotSoSimpleImages.ORIGINAL_UP, Color.YELLOW, Color.RED);
			width = iEditorImage.getWidth() / 3;
			height = iEditorImage.getHeight();
			x = ApoConstants.GAME_WIDTH - width - 25;
			y = 90 + 1 * height + 1 * 5;
			this.game.getButtons()[13] = new ApoButton(iEditorImage, x, y, width, height, function);
			
			function = ApoNotSoSimpleEditor.DOWN;
			iEditorImage = ApoNotSoSimpleImages.getImageSimpleEditor(ApoNotSoSimpleImages.ORIGINAL_DOWN, Color.YELLOW, Color.RED);
			width = iEditorImage.getWidth() / 3;
			height = iEditorImage.getHeight();
			x = ApoNotSoSimpleConstants.LEVEL_WIDTH + 10 + 30;
			y = 90 + 2 * height + 2 * 5;
			this.game.getButtons()[14] = new ApoButton(iEditorImage, x, y, width, height, function);
			
			function = ApoNotSoSimpleEditor.LEFT;
			iEditorImage = ApoNotSoSimpleImages.getImageSimpleEditor(ApoNotSoSimpleImages.ORIGINAL_LEFT, Color.YELLOW, Color.RED);
			width = iEditorImage.getWidth() / 3;
			height = iEditorImage.getHeight();
			x = ApoConstants.GAME_WIDTH - width - 25;
			y = 90 + 2 * height + 2 * 5;
			this.game.getButtons()[15] = new ApoButton(iEditorImage, x, y, width, height, function);
			
			function = ApoNotSoSimpleEditor.RIGHT;
			iEditorImage = ApoNotSoSimpleImages.getImageSimpleEditor(ApoNotSoSimpleImages.ORIGINAL_RIGHT, Color.YELLOW, Color.RED);
			width = iEditorImage.getWidth() / 3;
			height = iEditorImage.getHeight();
			x = ApoNotSoSimpleConstants.LEVEL_WIDTH + 10 + 30;
			y = 90 + 3 * height + 3 * 5;
			this.game.getButtons()[16] = new ApoButton(iEditorImage, x, y, width, height, function);
			
			function = ApoNotSoSimpleEditor.VISIBLE_TRUE;
			iEditorImage = ApoNotSoSimpleImages.getImageSimpleEditor(ApoNotSoSimpleImages.ORIGINAL_VISIBLE_TRUE, Color.YELLOW, Color.RED);
			width = iEditorImage.getWidth() / 3;
			height = iEditorImage.getHeight();
			x = ApoConstants.GAME_WIDTH - width - 25;
			y = 90 + 3 * height + 3 * 5;
			this.game.getButtons()[17] = new ApoButton(iEditorImage, x, y, width, height, function);
			
			function = ApoNotSoSimpleEditor.VISIBLE_FALSE;
			iEditorImage = ApoNotSoSimpleImages.getImageSimpleEditor(ApoNotSoSimpleImages.ORIGINAL_VISIBLE_FALSE, Color.YELLOW, Color.RED);
			width = iEditorImage.getWidth() / 3;
			height = iEditorImage.getHeight();
			x = ApoNotSoSimpleConstants.LEVEL_WIDTH + 10 + 30;
			y = 90 + 4 * height + 4 * 5;
			this.game.getButtons()[18] = new ApoButton(iEditorImage, x, y, width, height, function);
			
			function = ApoNotSoSimpleEditor.STEP;
			iEditorImage = ApoNotSoSimpleImages.getImageSimpleEditor(ApoNotSoSimpleImages.ORIGINAL_STEP, Color.YELLOW, Color.RED);
			width = iEditorImage.getWidth() / 3;
			height = iEditorImage.getHeight();
			x = ApoConstants.GAME_WIDTH - width - 25;
			y = 90 + 4 * height + 4 * 5;
			this.game.getButtons()[20] = new ApoButton(iEditorImage, x, y, width, height, function);
			
			function = ApoNotSoSimpleEditor.STEP_FINISH;
			iEditorImage = ApoNotSoSimpleImages.getImageSimpleEditor(ApoNotSoSimpleImages.ORIGINAL_STEP_FINISH, Color.YELLOW, Color.RED);
			width = iEditorImage.getWidth() / 3;
			height = iEditorImage.getHeight();
			x = ApoNotSoSimpleConstants.LEVEL_WIDTH + 10 + 30;
			y = 90 + 5 * height + 5 * 5;
			this.game.getButtons()[21] = new ApoButton(iEditorImage, x, y, width, height, function);
			
			font = new Font(Font.SANS_SERIF, Font.BOLD, 30);
			text = "userlevels";
			function = ApoNotSoSimpleMenu.USERLEVELS;
			width = 250;
			height = 60;
			x = ApoConstants.GAME_WIDTH/2 - width/2;
			y = ApoConstants.GAME_HEIGHT/2 - 70 - height;
			this.game.getButtons()[22] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, true, font, 10), x, y, width, height, function);

			text = " < ";
			function = ApoNotSoSimpleGame.LEFT_SORT;
			width = 30;
			height = 30;
			x = ApoNotSoSimpleConstants.LEVEL_WIDTH  + 1 * 10  + 10;
			y = 200;
			this.game.getButtons()[23] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function);

			text = " > ";
			function = ApoNotSoSimpleGame.RIGHT_SORT;
			width = 30;
			height = 30;
			x = ApoConstants.GAME_WIDTH - 5 - width;
			y = 200;
			this.game.getButtons()[24] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function);

			text = " < ";
			function = ApoNotSoSimpleGame.LEFT_LEVEL;
			width = 30;
			height = 30;
			x = ApoNotSoSimpleConstants.LEVEL_WIDTH  + 1 * 10  + 10;
			y = 335;
			this.game.getButtons()[25] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function);

			text = " > ";
			function = ApoNotSoSimpleGame.RIGHT_LEVEL;
			width = 30;
			height = 30;
			x = ApoConstants.GAME_WIDTH - 5 - width;
			y = 335;
			this.game.getButtons()[26] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function);

			text = "options";
			function = ApoNotSoSimpleMenu.OPTIONS;
			width = 250;
			height = 60;
			x = ApoConstants.GAME_WIDTH/2 - width/2;
			y = ApoConstants.GAME_HEIGHT - 30 - height;
			this.game.getButtons()[27] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, true, font, 10), x, y, width, height, function);
			
			text = ApoNotSoSimpleOptions.MENU_STRING;
			function = ApoNotSoSimpleOptions.MENU;
			width = 150;
			height = 40;
			x = ApoConstants.GAME_WIDTH - 1 * 15 - 1 * width;
			y = ApoConstants.GAME_HEIGHT - 1 * height - 1 * 15;
			this.game.getButtons()[28] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, true, font, 10), x, y, width, height, function);

			text = "";
			function = ApoNotSoSimpleOptions.MUSIC;
			width = 40;
			height = 40;
			x = ApoConstants.GAME_WIDTH/2;
			y = 70;
			this.game.getButtons()[29] = new ApoNotSoSimpleOptionsButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function, false);

			text = "";
			function = ApoNotSoSimpleOptions.SOUND;
			width = 40;
			height = 40;
			x = ApoConstants.GAME_WIDTH/2;
			y = 115;
			this.game.getButtons()[30] = new ApoNotSoSimpleOptionsButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function, true);

			text = "menu";
			function = ApoNotSoSimpleLevelChooser.MENU;
			width = 150;
			height = 40;
			x = ApoConstants.GAME_WIDTH - 1 * 15 - 1 * width;
			y = ApoConstants.GAME_HEIGHT - 1 * height - 1 * 15;
			this.game.getButtons()[31] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, true, font, 10), x, y, width, height, function);

		}
	}
}