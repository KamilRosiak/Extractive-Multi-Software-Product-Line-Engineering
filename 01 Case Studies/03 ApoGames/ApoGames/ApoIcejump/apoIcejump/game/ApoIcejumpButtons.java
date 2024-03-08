package apoIcejump.game;

import java.awt.Color;

import org.apogames.entity.ApoButton;

public class ApoIcejumpButtons {
	
	private ApoIcejumpPanel game;
	
	public ApoIcejumpButtons(ApoIcejumpPanel game) {
		this.game = game;
	}

	public void init() {
		if (this.game.getButtons() == null) {
			this.game.setButtons(new ApoButton[30]);
			
			String text = ApoIcejumpMenu.QUIT;
			String function = ApoIcejumpMenu.QUIT;
			int width = 50;
			int height = 30;
			int x = this.game.getWidth() - 15 - width;
			int y = this.game.getHeight() - 1 * height - 1 * 15;
			this.game.getButtons()[0] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(255, 255, 255, 200), Color.BLACK, Color.BLACK, 5), x, y, width, height, function);
			
			text = ApoIcejumpMenu.START;
			function = ApoIcejumpMenu.START;
			width = 80;
			height = 60;
			x = this.game.getWidth()/2 - width - 5;// width/2;
			y = 50;
			this.game.getButtons()[1] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(255, 255, 255, 200), Color.BLACK, Color.WHITE, 5), x, y, width, height, function);

			text = ApoIcejumpGame.MENU_STRING;
			function = ApoIcejumpGame.MENU;
			width = 50;
			height = 20;
			x = this.game.getWidth() - 1 * 5 - 1 * width;
			y = this.game.getHeight() - 1 * height - 1 * 5;
			this.game.getButtons()[2] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(255, 255, 255, 200), Color.BLACK, Color.WHITE, 5), x, y, width, height, function);
			
			text = ApoIcejumpTutorial.MENU_STRING;
			function = ApoIcejumpTutorial.MENU;
			width = 50;
			height = 20;
			x = this.game.getWidth() - 1 * 5 - 1 * width;
			y = this.game.getHeight() - 1 * height - 1 * 5;
			this.game.getButtons()[3] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(255, 255, 255, 200), Color.BLACK, Color.WHITE, 5), x, y, width, height, function);
			
			text = ApoIcejumpMenu.TUTORIAL;
			function = ApoIcejumpMenu.TUTORIAL;
			width = 80;
			height = 60;
			x = this.game.getWidth() - width  - 5;
			y = 52;
			this.game.getButtons()[4] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(255, 255, 255, 200), Color.BLACK, Color.WHITE, 5), x, y, width, height, function);
			
			text = "<";
			function = ApoIcejumpMenu.LEFT_PLAYERONE;
			width = 20;
			height = 20;
			x = this.game.getWidth()*1/4 - width - 80;
			y = this.game.getHeight() * 1 / 2 + 30;
			this.game.getButtons()[5] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(255, 255, 255, 200), Color.BLACK, Color.BLACK, 5), x, y, width, height, function);
			
			text = ">";
			function = ApoIcejumpMenu.RIGHT_PLAYERONE;
			width = 20;
			height = 20;
			x = this.game.getWidth()*1/4 + 80;
			y = this.game.getHeight() * 1 / 2 + 30;
			this.game.getButtons()[6] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(255, 255, 255, 200), Color.BLACK, Color.BLACK, 5), x, y, width, height, function);

			text = "load";
			function = ApoIcejumpMenu.LOAD_PLAYERONE;
			width = 50;
			height = 30;
			x = this.game.getWidth()*1/4 - width/2;
			y = this.game.getHeight() * 1 / 2 + 60;
			this.game.getButtons()[7] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(255, 255, 255, 200), Color.BLACK, Color.BLACK, 5), x, y, width, height, function);

			text = "<";
			function = ApoIcejumpMenu.LEFT_PLAYERTWO;
			width = 20;
			height = 20;
			x = this.game.getWidth()*3/4 - width - 80;
			y = this.game.getHeight() * 1 / 2 + 30;
			this.game.getButtons()[8] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(255, 255, 255, 200), Color.BLACK, Color.BLACK, 5), x, y, width, height, function);
			
			text = ">";
			function = ApoIcejumpMenu.RIGHT_PLAYERTWO;
			width = 20;
			height = 20;
			x = this.game.getWidth()*3/4 + 80;
			y = this.game.getHeight() * 1 / 2 + 30;
			this.game.getButtons()[9] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(255, 255, 255, 200), Color.BLACK, Color.BLACK, 5), x, y, width, height, function);

			text = "load";
			function = ApoIcejumpMenu.LOAD_PLAYERTWO;
			width = 50;
			height = 30;
			x = this.game.getWidth()*3/4 - width/2;
			y = this.game.getHeight() * 1 / 2 + 60;
			this.game.getButtons()[10] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(255, 255, 255, 200), Color.BLACK, Color.BLACK , 5), x, y, width, height, function);
			
			text = "restart";
			function = ApoIcejumpAnalysis.START;
			width = 60;
			height = 30;
			x = this.game.getWidth()/2 - 1 * 5 - 1 * width;
			y = this.game.getHeight() * 3 / 4;
			this.game.getButtons()[11] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(255, 255, 255, 200), Color.BLACK, Color.BLACK, 5), x, y, width, height, function);

			text = "menu";
			function = ApoIcejumpAnalysis.MENU;
			width = 50;
			height = 30;
			x = this.game.getWidth()/2 + 1 * 5;
			y = this.game.getHeight() * 3 / 4;
			this.game.getButtons()[12] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(255, 255, 255, 200), Color.BLACK, Color.BLACK, 5), x, y, width, height, function);

			text = "menu";
			function = ApoIcejumpCredits.MENU;
			width = 50;
			height = 30;
			x = this.game.getWidth()/2 - width/2;
			y = this.game.getHeight() - height - 5;
			this.game.getButtons()[13] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(255, 255, 255, 200), Color.BLACK, Color.BLACK, 5), x, y, width, height, function);
			
			text = ApoIcejumpMenu.CREDITS;
			function = ApoIcejumpMenu.CREDITS;
			width = 70;
			height = 30;
			x = 15;
			y = this.game.getHeight() - 1 * height - 1 * 15;
			this.game.getButtons()[14] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(255, 255, 255, 200), Color.BLACK, Color.BLACK, 5), x, y, width, height, function);
	
			text = "menu";
			function = ApoIcejumpNetwork.MENU;
			width = 50;
			height = 30;
			x = this.game.getWidth()/2 - width/2;
			y = this.game.getHeight() * 3 / 4;
			this.game.getButtons()[15] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(255, 255, 255, 200), Color.BLACK, Color.BLACK, 5), x, y, width, height, function);

			text = "start Game";
			function = ApoIcejumpNetwork.START;
			width = 100;
			height = 30;
			x = this.game.getWidth()*1/2 - width/2;
			y = this.game.getHeight() * 3 / 4 - 40;
			this.game.getButtons()[16] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(255, 255, 255, 200), Color.BLACK, Color.BLACK, 5), x, y, width, height, function);

			text = ApoIcejumpNetwork.SERVER;
			function = ApoIcejumpNetwork.SERVER;
			width = 100;
			height = 30;
			x = this.game.getWidth()*1/4 + 1*5;
			y = this.game.getHeight() * 1 / 2 - height + 80;
			this.game.getButtons()[17] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(255, 255, 255, 200), Color.BLACK, Color.BLACK, 5), x, y, width, height, function);

			text = ApoIcejumpNetwork.CLIENT;
			function = ApoIcejumpNetwork.CLIENT;
			width = 70;
			height = 30;
			x = this.game.getWidth()*3/4 - 1*5 - width;
			y = this.game.getHeight() * 1 / 2 - height + 80;
			this.game.getButtons()[18] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(255, 255, 255, 200), Color.BLACK, Color.BLACK, 5), x, y, width, height, function);
			
			text = ApoIcejumpMenu.NETWORK;
			function = ApoIcejumpMenu.NETWORK;
			width = 80;
			height = 60;
			x = this.game.getWidth()/2 + 5;
			y = 50;
			this.game.getButtons()[19] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(255, 255, 255, 200), Color.BLACK, Color.WHITE, 5), x, y, width, height, function);
			
			text = "menu";
			function = ApoIcejumpSimulate.MENU;
			width = 50;
			height = 30;
			x = this.game.getWidth()/2 - width/2;
			y = this.game.getHeight() * 3 / 4;
			this.game.getButtons()[20] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(255, 255, 255, 200), Color.BLACK, Color.BLACK, 5), x, y, width, height, function);
			
			text = ApoIcejumpMenu.SIMULATE;
			function = ApoIcejumpMenu.SIMULATE;
			width = 80;
			height = 60;
			x = 5;
			y = 50;
			this.game.getButtons()[21] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(255, 255, 255, 200), Color.BLACK, Color.WHITE, 5), x, y, width, height, function);

			text = "simulate";
			function = ApoIcejumpSimulate.SIMULATE;
			width = 100;
			height = 30;
			x = this.game.getWidth()/2 - width/2;
			y = this.game.getHeight() * 3 / 4 - height - 5;
			this.game.getButtons()[22] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(255, 255, 255, 200), Color.BLACK, Color.BLACK, 5), x, y, width, height, function);
			
			text = "<";
			function = ApoIcejumpMenu.LEFT_PLAYERONE;
			width = 20;
			height = 20;
			x = this.game.getWidth()*1/4 - width - 80;
			y = this.game.getHeight() * 1 / 2 + 30;
			this.game.getButtons()[23] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(255, 255, 255, 200), Color.BLACK, Color.BLACK, 5), x, y, width, height, function);
			
			text = ">";
			function = ApoIcejumpMenu.RIGHT_PLAYERONE;
			width = 20;
			height = 20;
			x = this.game.getWidth()*1/4 + 80;
			y = this.game.getHeight() * 1 / 2 + 30;
			this.game.getButtons()[24] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(255, 255, 255, 200), Color.BLACK, Color.BLACK, 5), x, y, width, height, function);

			text = "load";
			function = ApoIcejumpMenu.LOAD_PLAYERONE;
			width = 50;
			height = 30;
			x = this.game.getWidth()*1/4 - width/2;
			y = this.game.getHeight() * 1 / 2 + 60;
			this.game.getButtons()[25] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(255, 255, 255, 200), Color.BLACK, Color.BLACK, 5), x, y, width, height, function);

			text = "<";
			function = ApoIcejumpMenu.LEFT_PLAYERTWO;
			width = 20;
			height = 20;
			x = this.game.getWidth()*3/4 - width - 80;
			y = this.game.getHeight() * 1 / 2 + 30;
			this.game.getButtons()[26] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(255, 255, 255, 200), Color.BLACK, Color.BLACK, 5), x, y, width, height, function);
			
			text = ">";
			function = ApoIcejumpMenu.RIGHT_PLAYERTWO;
			width = 20;
			height = 20;
			x = this.game.getWidth()*3/4 + 80;
			y = this.game.getHeight() * 1 / 2 + 30;
			this.game.getButtons()[27] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(255, 255, 255, 200), Color.BLACK, Color.BLACK, 5), x, y, width, height, function);

			text = "load";
			function = ApoIcejumpMenu.LOAD_PLAYERTWO;
			width = 50;
			height = 30;
			x = this.game.getWidth()*3/4 - width/2;
			y = this.game.getHeight() * 1 / 2 + 60;
			this.game.getButtons()[28] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(255, 255, 255, 200), Color.BLACK, Color.BLACK , 5), x, y, width, height, function);

			text = "cancel";
			function = ApoIcejumpSimulate.CANCEL;
			width = 100;
			height = 30;
			x = this.game.getWidth()/2 - width/2;
			y = this.game.getHeight() * 3 / 4;
			this.game.getButtons()[29] = new ApoButton(this.game.getImages().getButtonImage(width * 3, height, text, new Color(255, 255, 255, 200), Color.BLACK, Color.BLACK, 5), x, y, width, height, function);

		}
	}
}
