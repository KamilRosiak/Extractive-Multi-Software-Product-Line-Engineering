package apoSimple.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import org.apogames.entity.ApoButton;
import org.apogames.entity.ApoButtonWithImageAndText;

import apoSimple.ApoSimpleConstants;

public class ApoSimpleButtons {
	
	private ApoSimplePanel game;
	
	public ApoSimpleButtons(ApoSimplePanel game) {
		this.game = game;
	}

	public void init() {
		if (this.game.getButtons() == null) {
			this.game.setButtons(new ApoButton[71]);
			
			Font font = ApoSimpleConstants.ORIGINAL_FONT.deriveFont(25f).deriveFont(Font.BOLD);//new Font(Font.SANS_SERIF, Font.BOLD, 20);
			BufferedImage image = this.game.getImages().getImage("images/button_quit.png", false);
			String text = ApoSimpleMenu.QUIT;
			String function = ApoSimpleMenu.QUIT;
			int width = image.getWidth()/3;
			int height = image.getHeight();
			int x = ApoSimpleConstants.GAME_WIDTH - width;
			int y = ApoSimpleConstants.GAME_HEIGHT - 1 * height;
			this.game.getButtons()[0] = new ApoButton(image, x, y, width, height, function);
			
			image = this.game.getImages().getImage("images/button_play.png", false);
			text = ApoSimpleMenu.START;
			function = ApoSimpleMenu.START;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = 270;
			y = 45;
			this.game.getButtons()[1] = new ApoButton(image, x, y, width, height, function);

			image = this.game.getImages().getImage("images/button_tutorial.png", false);
			text = ApoSimpleMenu.TUTORIAL;
			function = ApoSimpleMenu.TUTORIAL;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = 5;
			y = 5;
			this.game.getButtons()[2] = new ApoButton(image, x, y, width, height, function);
			
			image = this.game.getImages().getImage("images/button_menu_game.png", false);
			text = ApoSimpleGame.MENU_STRING;
			function = ApoSimpleGame.MENU;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = ApoSimpleConstants.GAME_WIDTH - width - 18;
			y = ApoSimpleConstants.GAME_HEIGHT - 1 * height - 10;
			this.game.getButtons()[3] = new ApoButton(image, x, y, width, height, function);
			
			image = this.game.getImages().getImage("images/button_menu.png", false);
			text = ApoSimpleTutorial.MENU_STRING;
			function = ApoSimpleTutorial.MENU;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = ApoSimpleConstants.GAME_WIDTH - width;
			y = ApoSimpleConstants.GAME_HEIGHT - 1 * height;
			this.game.getButtons()[4] = new ApoButton(image, x, y, width, height, function);
			
			image = this.game.getImages().getImage("images/button_upload_score.png", false);
			text = ApoSimpleGame.UPLOAD_STRING;
			function = ApoSimpleGame.UPLOAD;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = ApoSimpleConstants.GAME_WIDTH/2 - width/2 + 13;
			y = ApoSimpleConstants.GAME_HEIGHT/2 + 165 - height;
			this.game.getButtons()[5] = new ApoButton(image, x, y, width, height, function);
			
			image = this.game.getImages().getImage("images/button_restart.png", false);
			text = ApoSimpleGame.NEW_STRING;
			function = ApoSimpleGame.NEW;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = ApoSimpleConstants.GAME_WIDTH/2 - width - width/2 - 1 * 10;
			y = ApoSimpleConstants.GAME_HEIGHT/2 + 160 - height;
			this.game.getButtons()[6] = new ApoButton(image, x, y, width, height, function);
			
			image = this.game.getImages().getImage("images/button_menu.png", false);
			text = ApoSimpleHighscore.MENU_STRING;
			function = ApoSimpleHighscore.MENU;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = ApoSimpleConstants.GAME_WIDTH - width;
			y = ApoSimpleConstants.GAME_HEIGHT - 1 * height;
			this.game.getButtons()[7] = new ApoButton(image, x, y, width, height, function);
			
			image = this.game.getImages().getImage("images/button_highscore.png", false);
			text = ApoSimpleMenu.HIGHSCORE;
			function = ApoSimpleMenu.HIGHSCORE;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = ApoSimpleConstants.GAME_WIDTH/2 - width/2 + 20;
			y = ApoSimpleConstants.GAME_HEIGHT - 1 * height - 100;
			this.game.getButtons()[8] = new ApoButton(image, x, y, width, height, function);
			
			image = this.game.getImages().getImage("images/button_menu_score.png", false);
			text = ApoSimpleGame.MENU_SCORE_STRING;
			function = ApoSimpleGame.MENU_SCORE;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = ApoSimpleConstants.GAME_WIDTH/2 + width/2 + 1 * 10;
			y = ApoSimpleConstants.GAME_HEIGHT/2 + 160 - height;
			this.game.getButtons()[9] = new ApoButton(image, x, y, width, height, function);
			
			image = this.game.getImages().getImage("images/button_menu.png", false);
			text = ApoSimpleCredits.MENU_STRING;
			function = ApoSimpleCredits.MENU;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = ApoSimpleConstants.GAME_WIDTH - width;
			y = ApoSimpleConstants.GAME_HEIGHT - 1 * height;
			this.game.getButtons()[10] = new ApoButton(image, x, y, width, height, function);
			
			image = this.game.getImages().getImage("images/button_credits.png", false);
			text = ApoSimpleMenu.CREDITS;
			function = ApoSimpleMenu.CREDITS;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = 5;
			y = 450 - height;
			this.game.getButtons()[11] = new ApoButton(image, x, y, width, height, function);
			
			text = ApoSimpleHighscore.LEFT_STRING;
			function = ApoSimpleHighscore.LEFT;
			width = 40;
			height = 40;
			x = ApoSimpleConstants.GAME_WIDTH - width - 1 * 45;
			y = ApoSimpleConstants.GAME_HEIGHT/2 - 50 - 1 * 5 - 1 * height;
			this.game.getButtons()[12] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, "", new Color(255, 255, 255), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), true, false, font, 255), x, y, width, height, function);
			
			text = ApoSimpleHighscore.RIGHT_STRING;
			function = ApoSimpleHighscore.RIGHT;
			width = 40;
			height = 40;
			x = ApoSimpleConstants.GAME_WIDTH - width - 1 * 45;
			y = ApoSimpleConstants.GAME_HEIGHT/2 + 50 + 1 * 18;
			this.game.getButtons()[13] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, "", new Color(255, 255, 255), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, true, font, 255), x, y, width, height, function);

			text = ApoSimpleMenu.EDITOR;
			function = ApoSimpleMenu.EDITOR;
			width = 200;
			height = 60;
			x = 5;
			y = 10;
			this.game.getButtons()[14] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(255, 255, 255), Color.BLACK, Color.GRAY, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, true, font, 10), x, y, width, height, function);

			text = ApoSimpleEditor.MENU_STRING;
			function = ApoSimpleEditor.MENU;
			width = 70;
			height = 50;
			x = ApoSimpleConstants.GAME_WIDTH - 1 * 10 - 1 * width;
			y = ApoSimpleConstants.GAME_HEIGHT - 1 * height - 1 * 10;
			this.game.getButtons()[15] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(255, 255, 255), Color.BLACK, Color.GRAY, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function);

			image = this.game.getImages().getImage("images/button_levels.png", false);
			text = ApoSimpleMenu.LEVEL;
			function = ApoSimpleMenu.LEVEL;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = ApoSimpleConstants.GAME_WIDTH - 1 * 10 - 1 * width;
			y = 220 - height;
			this.game.getButtons()[16] = new ApoButton(image, x, y, width, height, function);
			
			image = this.game.getImages().getImage("images/button_menu.png", false);
			text = ApoSimpleLevelChooser.MENU_STRING;
			function = ApoSimpleLevelChooser.MENU;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = ApoSimpleConstants.GAME_WIDTH - width;
			y = ApoSimpleConstants.GAME_HEIGHT - 1 * height;
			this.game.getButtons()[17] = new ApoButton(image, x, y, width, height, function);
			
			text = ApoSimpleEditor.TEST;
			function = ApoSimpleEditor.TEST;
			width = 70;
			height = 50;
			x = ApoSimpleConstants.GAME_WIDTH - 2 * 10 - 2 * width;
			y = ApoSimpleConstants.GAME_HEIGHT - 1 * height - 1 * 10;
			this.game.getButtons()[18] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(255, 255, 255), Color.BLACK, Color.GRAY, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function);

			image = this.game.getImages().getImage("images/button_next_score.png", false);
			text = ApoSimpleGame.NEXT_STRING;
			function = ApoSimpleGame.NEXT;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = ApoSimpleConstants.GAME_WIDTH/2 - width/2 - 10;
			y = ApoSimpleConstants.GAME_HEIGHT/2 + 160 - height;
			this.game.getButtons()[19] = new ApoButton(image, x, y, width, height, function);

			image = this.game.getImages().getImage("images/button_menu_score.png", false);
			text = ApoSimpleGame.MENU_SCORE_STRING;
			function = ApoSimpleGame.MENU_SCORE;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = ApoSimpleConstants.GAME_WIDTH/2 + width/2 + 1 * 10;
			y = ApoSimpleConstants.GAME_HEIGHT/2 + 160 - height;
			this.game.getButtons()[20] = new ApoButton(image, x, y, width, height, function);
			
			text = ApoSimpleEditor.CLEAR;
			function = ApoSimpleEditor.CLEAR;
			width = 70;
			height = 50;
			x = ApoSimpleConstants.GAME_WIDTH - 1 * 10 - 1 * width;
			y = ApoSimpleConstants.GAME_HEIGHT - 3 * height - 3 * 10;
			this.game.getButtons()[21] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(255, 255, 255), Color.BLACK, Color.GRAY, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function);

			text = ApoSimpleEditor.SOLVE;
			function = ApoSimpleEditor.SOLVE;
			width = 70;
			height = 50;
			x = ApoSimpleConstants.GAME_WIDTH - 2 * 10 - 2 * width;
			y = ApoSimpleConstants.GAME_HEIGHT - 3 * height - 3 * 10;
			this.game.getButtons()[22] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(255, 255, 255), Color.BLACK, Color.GRAY, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function);

			text = "rand";
			function = ApoSimpleEditor.RANDOM;
			width = 70;
			height = 50;
			x = ApoSimpleConstants.GAME_WIDTH - 1 * 10 - 1 * width;
			y = ApoSimpleConstants.GAME_HEIGHT - 3 * height - 3 * 10;
			this.game.getButtons()[23] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(255, 255, 255), Color.BLACK, Color.GRAY, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function);

			text = "<";
			function = ApoSimpleEditor.LEFT_3;
			width = 50;
			height = 50;
			x = ApoSimpleConstants.GAME_WIDTH + 5 - 170;
			y = ApoSimpleConstants.GAME_HEIGHT - 4 * height - 4 * 10 - 1 * 5;
			this.game.getButtons()[24] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, "", new Color(255, 255, 255), Color.BLACK, Color.GRAY, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), true, false, font, 255), x, y, width, height, function);

			text = ">";
			function = ApoSimpleEditor.RIGHT_3;
			width = 50;
			height = 50;
			x = ApoSimpleConstants.GAME_WIDTH - width - 5;
			y = ApoSimpleConstants.GAME_HEIGHT - 4 * height - 4 * 10 - 1 * 5;
			this.game.getButtons()[25] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, "", new Color(255, 255, 255), Color.BLACK, Color.GRAY, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, true, font, 255), x, y, width, height, function);

			text = "<";
			function = ApoSimpleEditor.LEFT_2;
			width = 50;
			height = 50;
			x = ApoSimpleConstants.GAME_WIDTH + 5 - 170;
			y = ApoSimpleConstants.GAME_HEIGHT - 5 * height - 5 * 10 - 1 * 5;
			this.game.getButtons()[26] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, "", new Color(255, 255, 255), Color.BLACK, Color.GRAY, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), true, false, font, 255), x, y, width, height, function);

			text = ">";
			function = ApoSimpleEditor.RIGHT_2;
			width = 50;
			height = 50;
			x = ApoSimpleConstants.GAME_WIDTH - width - 5;
			y = ApoSimpleConstants.GAME_HEIGHT - 5 * height - 5 * 10 - 1 * 5;
			this.game.getButtons()[27] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, "", new Color(255, 255, 255), Color.BLACK, Color.GRAY, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, true, font, 255), x, y, width, height, function);
			
			image = this.game.getImages().getImage("images/button_restart.png", false);
			text = ApoSimpleGame.RESTART_STRING;
			function = ApoSimpleGame.RESTART;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = ApoSimpleConstants.GAME_WIDTH/2 - width - width/2 - 1 * 10;
			y = ApoSimpleConstants.GAME_HEIGHT/2 + 160 - height;
			this.game.getButtons()[28] = new ApoButton(image, x, y, width, height, function);
			
			text = ApoSimpleHighscore.ALL_STRING;
			function = ApoSimpleHighscore.ALL;
			width = 100;
			height = 30;
			x = 30 + 10;
			y = ApoSimpleConstants.GAME_HEIGHT - height - 20;
			this.game.getButtons()[29] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(255, 255, 255), Color.BLACK, Color.GRAY, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function);

			text = ApoSimpleHighscore.MONTH_STRING;
			function = ApoSimpleHighscore.MONTH;
			width = 100;
			height = 30;
			x = 30 + 2 * 10 + 1 * width;
			y = ApoSimpleConstants.GAME_HEIGHT - height - 20;
			this.game.getButtons()[30] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(255, 255, 255), Color.BLACK, Color.GRAY, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function);

			text = ApoSimpleHighscore.WEEK_STRING;
			function = ApoSimpleHighscore.WEEK;
			width = 100;
			height = 30;
			x = 30 + 3 * 10 + 2 * width;
			y = ApoSimpleConstants.GAME_HEIGHT - height - 20;
			this.game.getButtons()[31] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(255, 255, 255), Color.BLACK, Color.GRAY, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function);

			text = ApoSimpleHighscore.DAY_STRING;
			function = ApoSimpleHighscore.DAY;
			width = 100;
			height = 30;
			x = 30 + 4 * 10 + 3 * width;
			y = ApoSimpleConstants.GAME_HEIGHT - height - 20;
			this.game.getButtons()[32] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(255, 255, 255), Color.BLACK, Color.GRAY, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function);

			text = "<";
			function = ApoSimpleEditor.LEFT_1;
			width = 50;
			height = 50;
			x = ApoSimpleConstants.GAME_WIDTH + 5 - 170;
			y = ApoSimpleConstants.GAME_HEIGHT - 6 * height - 6 * 10 - 1 * 5;
			this.game.getButtons()[33] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, "", new Color(255, 255, 255), Color.BLACK, Color.GRAY, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), true, false, font, 255), x, y, width, height, function);

			text = ">";
			function = ApoSimpleEditor.RIGHT_1;
			width = 50;
			height = 50;
			x = ApoSimpleConstants.GAME_WIDTH - width - 5;
			y = ApoSimpleConstants.GAME_HEIGHT - 6 * height - 6 * 10 - 1 * 5;
			this.game.getButtons()[34] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, "", new Color(255, 255, 255), Color.BLACK, Color.GRAY, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, true, font, 255), x, y, width, height, function);

			image = this.game.getImages().getImage("images/button_editor.png", false);
			text = ApoSimplePuzzle.EDITOR;
			function = ApoSimplePuzzle.EDITOR;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = 115;
			y = 330;
			this.game.getButtons()[35] = new ApoButton(image, x, y, width, height, function);
			
			image = this.game.getImages().getImage("images/button_userlevels.png", false);
			text = ApoSimplePuzzle.USERLEVELS;
			function = ApoSimplePuzzle.USERLEVELS;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = 30;
			y = 165;
			this.game.getButtons()[36] = new ApoButton(image, x, y, width, height, function);
			
			image = this.game.getImages().getImage("images/button_levelchooser.png", false);
			text = ApoSimplePuzzle.LEVELCHOOSER;
			function = ApoSimplePuzzle.LEVELCHOOSER;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = 205;
			y = 10;
			this.game.getButtons()[37] = new ApoButton(image, x, y, width, height, function);
			
			text = ApoSimpleEditor.UPLOAD;
			function = ApoSimpleEditor.UPLOAD;
			width = 140;
			height = 50;
			x = ApoSimpleConstants.GAME_WIDTH - 2 * 10 - 1 * width;
			y = ApoSimpleConstants.GAME_HEIGHT - 2 * height - 2 * 10;
			this.game.getButtons()[38] = new ApoButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(255, 255, 255), Color.BLACK, Color.GRAY, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function);

			image = this.game.getImages().getImage("images/button_notice.png", false);
			text = ApoSimpleGame.NOTICE;
			function = ApoSimpleGame.NOTICE;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = 500;
			y = 4;
			this.game.getButtons()[39] = new ApoButton(image, x, y, width, height, function);

			image = this.game.getImages().getImage("images/button_reload.png", false);
			text = ApoSimpleGame.RELOAD;
			function = ApoSimpleGame.RELOAD;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = 580;
			y = 4;
			this.game.getButtons()[40] = new ApoButton(image, x, y, width, height, function);
			
			image = this.game.getImages().getImage("images/button_back.png", false);
			text = ApoSimpleNotice.BACK;
			function = ApoSimpleNotice.BACK;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = 475;
			y = 80 + 0 * height + 0 * 5;
			this.game.getButtons()[41] = new ApoButton(image, x, y, width, height, function);
			
			image = this.game.getImages().getImage("images/button_eraser_all.png", false);
			text = ApoSimpleNotice.ERASEALL;
			function = ApoSimpleNotice.ERASEALL;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = 475;
			y = 80 + 1 * height + 1 * 5;
			this.game.getButtons()[42] = new ApoButton(image, x, y, width, height, function);
			
			image = this.game.getImages().getImage("images/button_eraser.png", false);
			text = ApoSimpleNotice.ERASER;
			function = ApoSimpleNotice.ERASER;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = 475;
			y = 80 + 2 * height + 2 * 5;
			this.game.getButtons()[43] = new ApoButton(image, x, y, width, height, function);
			
			image = this.game.getImages().getImage("images/button_pencil.png", false);
			text = ApoSimpleNotice.PENCIL;
			function = ApoSimpleNotice.PENCIL;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = 475;
			y = 80 + 3 * height + 3 * 5;
			this.game.getButtons()[44] = new ApoButton(image, x, y, width, height, function);
			
			image = this.game.getImages().getImage("images/button_green.png", false);
			text = ApoSimpleNotice.GREEN;
			function = ApoSimpleNotice.GREEN;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = 475;
			y = 80 + 4 * height + 4 * 5;
			this.game.getButtons()[45] = new ApoButton(image, x, y, width, height, function);

			image = this.game.getImages().getImage("images/button_red.png", false);
			text = ApoSimpleNotice.RED;
			function = ApoSimpleNotice.RED;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = 475;
			y = 80 + 5 * height + 5 * 5;
			this.game.getButtons()[46] = new ApoButton(image, x, y, width, height, function);
			
			image = this.game.getImages().getImage("images/button_options.png", false);
			text = ApoSimpleMenu.OPTIONS;
			function = ApoSimpleMenu.OPTIONS;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = 155;
			y = 310;
			this.game.getButtons()[47] = new ApoButton(image, x, y, width, height, function);
			
			image = this.game.getImages().getImage("images/button_menu.png", false);
			text = ApoSimpleOptions.MENU_STRING;
			function = ApoSimpleOptions.MENU;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = ApoSimpleConstants.GAME_WIDTH - width;
			y = ApoSimpleConstants.GAME_HEIGHT - 1 * height;
			this.game.getButtons()[48] = new ApoButton(image, x, y, width, height, function);
			
			text = "";
			function = ApoSimpleOptions.MUSIC;
			width = 40;
			height = 40;
			x = 385;
			y = 155;
			this.game.getButtons()[49] = new ApoSimpleOptionsButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function, false);
			((ApoSimpleOptionsButton)(this.game.getButtons()[49])).setBActive(true);
			
			text = "";
			function = ApoSimpleOptions.SOUND;
			width = 40;
			height = 40;
			x = 541;
			y = 155;
			this.game.getButtons()[50] = new ApoSimpleOptionsButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function, false);
			((ApoSimpleOptionsButton)(this.game.getButtons()[50])).setBActive(true);
			
			image = this.game.getImages().getImage("images/button_tutorial_endlessmode.png", false);
			text = ApoSimpleTutorial.ENDLESS;
			function = ApoSimpleTutorial.ENDLESS;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = 250;
			y = 15;
			this.game.getButtons()[51] = new ApoButton(image, x, y, width, height, function);
			
			image = this.game.getImages().getImage("images/button_tutorial_puzzlemode.png", false);
			text = ApoSimpleTutorial.PUZZLE;
			function = ApoSimpleTutorial.PUZZLE;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = 25;
			y = 230;
			this.game.getButtons()[52] = new ApoButton(image, x, y, width, height, function);
			
			image = this.game.getImages().getImage("images/button_music.png", false);
			text = ApoSimpleGame.MUSIC;
			function = ApoSimpleGame.MUSIC;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = 500;
			y = 55;
			this.game.getButtons()[53] = new ApoSimpleButtonNot(image, x, y, width, height, function, false);

			image = this.game.getImages().getImage("images/button_sound.png", false);
			text = ApoSimpleGame.SOUND;
			function = ApoSimpleGame.SOUND;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = 580;
			y = 55;
			this.game.getButtons()[54] = new ApoSimpleButtonNot(image, x, y, width, height, function, false);
			
			image = this.game.getImages().getImage("images/button_help.png", false);
			text = ApoSimpleGame.HELP;
			function = ApoSimpleGame.HELP;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = 580;
			y = 4;
			this.game.getButtons()[55] = new ApoSimpleButtonNot(image, x, y, width, height, function, false);

			image = this.game.getImages().getImage("images/button_goodie.png", false);
			text = ApoSimpleGame.GOODIE;
			function = ApoSimpleGame.GOODIE;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = 528;
			y = 280;
			this.game.getButtons()[56] = new ApoSimpleButtonNot(image, x, y, width, height, function, true);
			
			image = this.game.getImages().getImage("images/button_coins.png", false);
			text = ApoSimpleGame.COINS;
			function = ApoSimpleGame.COINS;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = 492;
			y = 349;
			this.game.getButtons()[57] = new ApoSimpleButtonNot(image, x, y, width, height, function, true);
			
			int change = 30;
			
			image = this.game.getImages().getImage("images/button_back_save.png", false);
			text = ApoSimpleGame.BACK_SAVE;
			function = ApoSimpleGame.BACK_SAVE;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = 113 - change;
			y = 215;
			this.game.getButtons()[58] = new ApoButton(image, x, y, width, height, function);
			
			image = this.game.getImages().getImage("images/button_back_menu.png", false);
			text = ApoSimpleGame.BACK_MENU;
			function = ApoSimpleGame.BACK_MENU;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = 261 - change;
			y = 219;
			this.game.getButtons()[59] = new ApoButton(image, x, y, width, height, function);
			
			image = this.game.getImages().getImage("images/button_back_back.png", false);
			text = ApoSimpleGame.BACK_BACK;
			function = ApoSimpleGame.BACK_BACK;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = 419 - change;
			y = 208;
			this.game.getButtons()[60] = new ApoButton(image, x, y, width, height, function);
			
			text = "";
			function = ApoSimpleOptions.GERMAN;
			width = 40;
			height = 40;
			x = 210;
			y = 330;
			this.game.getButtons()[61] = new ApoSimpleOptionsButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function, false);

			text = "";
			function = ApoSimpleOptions.ENGLISH;
			width = 40;
			height = 40;
			x = 107;
			y = 340;
			this.game.getButtons()[62] = new ApoSimpleOptionsButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function, false);

			text = "";
			function = ApoSimpleOptions.SHEEP;
			width = 40;
			height = 40;
			x = 447;
			y = 325;
			this.game.getButtons()[63] = new ApoSimpleOptionsButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function, false);

			text = "";
			function = ApoSimpleOptions.CLASSIC;
			width = 40;
			height = 40;
			x = 537;
			y = 323;
			this.game.getButtons()[64] = new ApoSimpleOptionsButton(this.game.getImages().getButtonImageSimple(width * 3, height, text, new Color(0, 0, 0, 0), Color.BLACK, Color.BLACK, new Color(255, 255, 0, 128), new Color(255, 0, 0, 128), false, false, font, 10), x, y, width, height, function, false);

			font = ApoSimpleConstants.ORIGINAL_FONT.deriveFont(15f).deriveFont(Font.BOLD);
			image = this.game.getImages().getImage("images/button_level_difficult_left.png", false);
			text = ApoSimpleLevelChooser.LEFT;
			function = ApoSimpleLevelChooser.LEFT;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = 548;
			y = 170;
			this.game.getButtons()[65] = new ApoButtonWithImageAndText(image, x, y, width, height, function, "easy", font, 4);
			
			image = this.game.getImages().getImage("images/button_level_difficult_right.png", false);
			text = ApoSimpleLevelChooser.RIGHT;
			function = ApoSimpleLevelChooser.RIGHT;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = 548;
			y = 309;
			this.game.getButtons()[66] = new ApoButtonWithImageAndText(image, x, y, width, height, function, "medium", font, -3);
			
			image = this.game.getImages().getImage("images/button_left_choose.png", false);
			text = ApoSimpleGame.SORTED_LEFT;
			function = ApoSimpleGame.SORTED_LEFT;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = 478;
			y = 111;
			this.game.getButtons()[67] = new ApoButton(image, x, y, width, height, function);
			
			text = ApoSimpleGame.LEVEL_LEFT;
			function = ApoSimpleGame.LEVEL_LEFT;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = 478;
			y = 157;
			this.game.getButtons()[68] = new ApoButton(image, x, y, width, height, function);
			
			image = this.game.getImages().getImage("images/button_right_choose.png", false);
			text = ApoSimpleGame.SORTED_RIGHT;
			function = ApoSimpleGame.SORTED_RIGHT;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = 609;
			y = 111;
			this.game.getButtons()[69] = new ApoButton(image, x, y, width, height, function);
			
			text = ApoSimpleGame.LEVEL_RIGHT;
			function = ApoSimpleGame.LEVEL_RIGHT;
			width = image.getWidth()/3;
			height = image.getHeight();
			x = 609;
			y = 157;
			this.game.getButtons()[70] = new ApoButton(image, x, y, width, height, function);
			
			for (int i = 0; i < this.game.getButtons().length; i++) {
				this.game.getButtons()[i].setBOpaque(true);
			}
			this.game.getButtons()[40].setBOpaque(false);
			this.game.getButtons()[49].setBOpaque(false);
			this.game.getButtons()[50].setBOpaque(false);
			this.game.getButtons()[53].setBOpaque(false);
			this.game.getButtons()[54].setBOpaque(false);
			this.game.getButtons()[56].setBOpaque(false);
			this.game.getButtons()[57].setBOpaque(false);
			this.game.getButtons()[61].setBOpaque(false);
			this.game.getButtons()[62].setBOpaque(false);
			this.game.getButtons()[63].setBOpaque(false);
			this.game.getButtons()[64].setBOpaque(false);
		}
	}
}
