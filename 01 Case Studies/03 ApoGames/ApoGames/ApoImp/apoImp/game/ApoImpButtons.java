package apoImp.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import org.apogames.entity.ApoButton;

import apoImp.ApoImpConstants;
import apoImp.editor.ApoImpEditor;

public class ApoImpButtons {
	
	private ApoImpPanel game;
	
	public ApoImpButtons(ApoImpPanel game) {
		this.game = game;
	}

	public void init() {
		if (this.game.getButtons() == null) {
			this.game.setButtons(new ApoButton[11]);
			
			Font font = ApoImpConstants.ORIGINAL_FONT.deriveFont(20f).deriveFont(Font.BOLD);
			String text = ApoImpMenu.QUIT;
			String function = ApoImpMenu.QUIT;
			int width = 40;
			int height = 40;
			BufferedImage image = this.game.getImages().getButtonImage(width * 3, height, text, ApoImpConstants.c, ApoImpConstants.c2, ApoImpConstants.c2, Color.YELLOW, Color.RED, font, 10);
			int x = ApoImpConstants.GAME_WIDTH - width - 5;
			int y = ApoImpConstants.GAME_HEIGHT - 1 * height - 5;
			this.game.getButtons()[0] = new ApoButton(image, x, y, width, height, function);
		
			text = ApoImpMenu.GAME;
			function = ApoImpMenu.GAME;
			width = 300;
			height = 100;
			image = this.game.getImages().getButtonImage(width * 3, height, text, ApoImpConstants.c, ApoImpConstants.c2, ApoImpConstants.c2, Color.YELLOW, Color.RED, font, 10);
			x = ApoImpConstants.GAME_WIDTH/2 - width/2;
			y = 100;
			this.game.getButtons()[1] = new ApoButton(image, x, y, width, height, function);
			
			text = ApoImpMenu.EDITOR;
			function = ApoImpMenu.EDITOR;
			width = 300;
			height = 100;
			image = this.game.getImages().getButtonImage(width * 3, height, text, ApoImpConstants.c, ApoImpConstants.c2, ApoImpConstants.c2, Color.YELLOW, Color.RED, font, 10);
			x = ApoImpConstants.GAME_WIDTH/2 - width/2;
			y = 340;
			this.game.getButtons()[2] = new ApoButton(image, x, y, width, height, function);
			
			text = ApoImpGame.QUIT;
			function = ApoImpGame.QUIT;
			width = 30;
			height = 30;
			image = this.game.getImages().getButtonImage(width * 3, height, text, ApoImpConstants.c, ApoImpConstants.c2, ApoImpConstants.c2, Color.YELLOW, Color.RED, font, 10);
			x = ApoImpConstants.GAME_WIDTH - width - 5;
			y = 0;
			this.game.getButtons()[3] = new ApoButton(image, x, y, width, height, function);
			
			text = ApoImpEditor.QUIT;
			function = ApoImpEditor.QUIT;
			width = 30;
			height = 30;
			image = this.game.getImages().getButtonImage(width * 3, height, text, ApoImpConstants.c, ApoImpConstants.c2, ApoImpConstants.c2, Color.YELLOW, Color.RED, font, 10);
			x = ApoImpConstants.GAME_WIDTH - width - 5;
			y = 0;
			this.game.getButtons()[4] = new ApoButton(image, x, y, width, height, function);
			
			text = ApoImpEditor.TEST;
			function = ApoImpEditor.TEST;
			width = 50;
			height = 30;
			image = this.game.getImages().getButtonImage(width * 3, height, text, ApoImpConstants.c, ApoImpConstants.c2, ApoImpConstants.c2, Color.YELLOW, Color.RED, font, 10);
			x = ApoImpConstants.GAME_WIDTH - 35 - width - 5;
			y = 0;
			this.game.getButtons()[5] = new ApoButton(image, x, y, width, height, function);
			
			text = ApoImpEditor.NEW;
			function = ApoImpEditor.NEW;
			width = 50;
			height = 30;
			image = this.game.getImages().getButtonImage(width * 3, height, text, ApoImpConstants.c, ApoImpConstants.c2, ApoImpConstants.c2, Color.YELLOW, Color.RED, font, 10);
			x = ApoImpConstants.GAME_WIDTH - 90 - width - 5;
			y = 0;
			this.game.getButtons()[6] = new ApoButton(image, x, y, width, height, function);
			
			text = ApoImpMenu.USERLEVELS;
			function = ApoImpMenu.USERLEVELS;
			width = 300;
			height = 100;
			image = this.game.getImages().getButtonImage(width * 3, height, text, ApoImpConstants.c, ApoImpConstants.c2, ApoImpConstants.c2, Color.YELLOW, Color.RED, font, 10);
			x = ApoImpConstants.GAME_WIDTH/2 - width/2;
			y = 220;
			this.game.getButtons()[7] = new ApoButton(image, x, y, width, height, function);
			
			text = ApoImpEditor.UPLOAD;
			function = ApoImpEditor.UPLOAD;
			width = 80;
			height = 30;
			image = this.game.getImages().getButtonImage(width * 3, height, text, ApoImpConstants.c, ApoImpConstants.c2, ApoImpConstants.c2, Color.YELLOW, Color.RED, font, 10);
			x = ApoImpConstants.GAME_WIDTH - 145 - width - 5;
			y = 0;
			this.game.getButtons()[8] = new ApoButton(image, x, y, width, height, function);
			
			text = ApoImpGame.LEVEL_LEFT;
			function = ApoImpGame.LEVEL_LEFT;
			width = 30;
			height = 30;
			image = this.game.getImages().getButtonImage(width * 3, height, text, ApoImpConstants.c, ApoImpConstants.c2, ApoImpConstants.c2, Color.YELLOW, Color.RED, font, 10);
			x = 5;
			y = 0;
			this.game.getButtons()[9] = new ApoButton(image, x, y, width, height, function);
			
			text = ApoImpGame.LEVEL_RIGHT;
			function = ApoImpGame.LEVEL_RIGHT;
			width = 30;
			height = 30;
			image = this.game.getImages().getButtonImage(width * 3, height, text, ApoImpConstants.c, ApoImpConstants.c2, ApoImpConstants.c2, Color.YELLOW, Color.RED, font, 10);
			x = 140;
			y = 0;
			this.game.getButtons()[10] = new ApoButton(image, x, y, width, height, function);
			
			for (int i = 0; i < this.game.getButtons().length; i++) {
				this.game.getButtons()[i].setBOpaque(true);
			}
		}
	}
}
