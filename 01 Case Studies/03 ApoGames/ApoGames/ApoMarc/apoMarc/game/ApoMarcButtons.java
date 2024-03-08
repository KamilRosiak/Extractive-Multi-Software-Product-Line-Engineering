package apoMarc.game;

import org.apogames.entity.ApoButton;

import apoMarc.ApoMarcConstants;
import apoMarc.entity.ApoMarcHelpButton;

public class ApoMarcButtons {
	
	private ApoMarcPanel game;
	
	public ApoMarcButtons(ApoMarcPanel game) {
		this.game = game;
	}

	public void init() {
		if (this.game.getButtons() == null) {
			this.game.setButtons(new ApoButton[20]);
			
			int gameWidth = ApoMarcConstants.GAME_WIDTH;
			int gameHeight = ApoMarcConstants.GAME_HEIGHT;
			
			String function = ApoMarcMenu.QUIT;
			int width = 400;
			int height = 75;
			int x = gameWidth/2 - width/2;
			int y = gameHeight - 1 * height - 1 * 5;
			this.game.getButtons()[0] = new ApoButton(this.game.getImages().getImage("images/button_quit.png", false), x, y, width, height, function);
			
			function = ApoMarcMenu.START;
			x = gameWidth/2 - width/2;
			y = 90 + 0 * 10 + 0 * 75;
			this.game.getButtons()[1] = new ApoButton(this.game.getImages().getImage("images/button_play.png", false), x, y, width, height, function);

			function = ApoMarcMenu.OPTIONS;
			x = gameWidth/2 - width/2;
			y = 90 + 1 * 10 + 1 * 75;
			this.game.getButtons()[2] = new ApoButton(this.game.getImages().getImage("images/button_settings.png", false), x, y, width, height, function);
			
			function = ApoMarcGame.MENU;
			width = 30;
			height = 30;
			x = gameWidth - 1 * 10 - 1 * width;
			y = gameHeight/2 - 1 * height/2;
			this.game.getButtons()[3] = new ApoButton(this.game.getImages().getImage("images/button_pause.png", false), x, y, width, height, function);
			
			function = ApoMarcOptions.MENU;
			width = 400;
			height = 75;
			x = gameWidth/2 - width/2;
			y = gameHeight - 1 * height - 1 * 5;
			this.game.getButtons()[4] = new ApoButton(this.game.getImages().getImage("images/button_back.png", false), x, y, width, height, function);
			
			function = ApoMarcDifficulty.DIFFICULTY_MARC;
			x = gameWidth/2 - width/2;
			y = 90 + 1 * 10 + 1 * 75;
			this.game.getButtons()[5] = new ApoButton(this.game.getImages().getImage("images/button_dif_marc.png", false), x, y, width, height, function);
			
			function = ApoMarcDifficulty.DIFFICULTY_MANDY;
			x = gameWidth/2 - width/2;
			y = 90 + 2 * 10 + 2 * 75;
			this.game.getButtons()[6] = new ApoButton(this.game.getImages().getImage("images/button_dif_mandy.png", false), x, y, width, height, function);
			
			function = ApoMarcDifficulty.DIFFICULTY_APO;
			x = gameWidth/2 - width/2;
			y = 90 + 3 * 10 + 3 * 75;
			this.game.getButtons()[7] = new ApoButton(this.game.getImages().getImage("images/button_dif_apo.png", false), x, y, width, height, function);
			
			function = ApoMarcDifficulty.DIFFICULTY_UNBEATABLE;
			x = gameWidth/2 - width/2;
			y = 90 + 4 * 10 + 4 * 75;
			this.game.getButtons()[8] = new ApoButton(this.game.getImages().getImage("images/button_dif_godlike.png", false), x, y, width, height, function);
			
			function = ApoMarcDifficulty.BACK;
			x = gameWidth/2 - width/2;
			y = gameHeight - 1 * height - 1 * 5;
			this.game.getButtons()[9] = new ApoButton(this.game.getImages().getImage("images/button_back.png", false), x, y, width, height, function);
			
			function = ApoMarcMenu.ACHIEVEMENTS;
			x = gameWidth/2 - width/2;
			y = 90 + 2 * 10 + 2 * 75;
			this.game.getButtons()[10] = new ApoButton(this.game.getImages().getImage("images/button_achievements.png", false), x, y, width, height, function);
			
			function = ApoMarcMenu.CREDITS;
			x = gameWidth/2 - width/2;
			y = 90 + 3 * 10 + 3 * 75;
			this.game.getButtons()[11] = new ApoButton(this.game.getImages().getImage("images/button_credits.png", false), x, y, width, height, function);
			
			function = ApoMarcAchievements.MENU;
			x = gameWidth/2 - width/2;
			y = gameHeight - 1 * height - 1 * 5;
			this.game.getButtons()[12] = new ApoButton(this.game.getImages().getImage("images/button_back.png", false), x, y, width, height, function);
			
			function = ApoMarcCredits.MENU;
			x = gameWidth/2 - width/2;
			y = gameHeight - 1 * height - 1 * 5;
			this.game.getButtons()[13] = new ApoButton(this.game.getImages().getImage("images/button_back.png", false), x, y, width, height, function);
			
			function = ApoMarcOptions.EFFECTS;
			width = 50;
			height = 50;
			x = 50;
			y = 110 + 1 * 10 + 1 * 50;
			this.game.getButtons()[14] = new ApoMarcHelpButton(x, y, width, height, function, "Particle-Effects");
			
			function = ApoMarcOptions.LIGHTS;
			width = 50;
			height = 50;
			x = 50;
			y = 110 + 2 * 10 + 2 * 50;
			this.game.getButtons()[15] = new ApoMarcHelpButton(x, y, width, height, function, "Wall-Lights-Effects");
			
			function = ApoMarcOptions.APO;
			width = 50;
			height = 50;
			x = 50;
			y = 120 + 3 * 10 + 3 * 50;
			this.game.getButtons()[16] = new ApoMarcHelpButton(x, y, width, height, function, "I like Apo-Games");
			
			function = ApoMarcGame.RESTART;
			width = 150;
			height = 60;
			x = 20;
			y = gameHeight/2 + 50;
			this.game.getButtons()[17] = new ApoButton(this.game.getImages().getImage("images/button_restart.png", false), x, y, width, height, function);
			
			function = ApoMarcGame.MENU_REAL;
			x = ApoMarcConstants.GAME_WIDTH - width - 20;
			y = gameHeight/2 + 50;
			this.game.getButtons()[18] = new ApoButton(this.game.getImages().getImage("images/button_menu.png", false), x, y, width, height, function);
			
			function = ApoMarcGame.RESUME;
			x = 20;
			y = gameHeight/2 + 50;
			this.game.getButtons()[19] = new ApoButton(this.game.getImages().getImage("images/button_resume.png", false), x, y, width, height, function);
		}
	}
}
