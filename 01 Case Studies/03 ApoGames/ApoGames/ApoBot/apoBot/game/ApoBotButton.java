package apoBot.game;

import java.awt.Color;
import java.awt.Font;

import org.apogames.entity.ApoButton;

import apoBot.ApoBotComponent;
import apoBot.ApoBotConstants;

/**
 * Klasse für die Erstellung der Buttons
 * @author Dirk Aporius
 *
 */
public class ApoBotButton {

	private ApoBotComponent game;
	
	public ApoBotButton(ApoBotComponent game) {
		this.game = game;
	}
	
	/**
	 * erstellt die Buttons
	 */
	public void init() {
		if (this.game.getButtons() == null) {
			this.game.setButtons(new ApoButton[46]);
			
			Font write = new Font("Dialog", Font.BOLD, 28);
			
			/**
			 * ein ApoButton braucht, einen Text (der auf dem Button steht wenn es kein Bild ist)
			 * eineen String, welche Funktion der Button hat
			 * Breit, Höhe, x und y sollten klar sein :D
			 */
			String text = "quit";
			String function = "quit";
			int width = 300;
			int height = 50;
			int x = this.game.getWidth()/2 - width/2;
			int y = this.game.getHeight() - 1 * height - 1 * 5;
			this.game.getButtons()[0] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, new Color(255, 255, 255, 150), Color.black, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100), write), x, y, width, height, function );
			
			text = "tutorial";
			function = "tutorial";
			width = 300;
			height = 50;
			x = this.game.getWidth()/2 - width/2;
			y = 50;
			this.game.getButtons()[1] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, new Color(255, 255, 255, 150), Color.black, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100), write), x, y, width, height, function );
			
			text = "game";
			function = "game";
			width = 300;
			height = 50;
			x = this.game.getWidth()/2 - width/2;
			y = 150;
			this.game.getButtons()[2] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, new Color(255, 255, 255, 150), Color.black, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100), write), x, y, width, height, function );
			
			text = "menu";
			function = "menu";
			width = 60;
			height = 20;
			x = this.game.getWidth() - 5 - width;
			y = this.game.getHeight() - 1 * height - 1 * 5;
			this.game.getButtons()[3] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, Color.gray, Color.white, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );
			
			text = "Go";
			function = "go";
			width = 80;
			height = 20;
			x = this.game.getWidth() - 240;
			y = this.game.getHeight() - 1 * height - 1 * 5;
			this.game.getButtons()[4] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, Color.gray, Color.white, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = "i";
			function = "forward";
			width = 32;
			height = 32;
			x = this.game.getWidth() - 450;
			y = 1 * 5;
			this.game.getButtons()[5] = new ApoButton(this.game.getImage().getImage("images/button_forward.png", false), x, y, width, height, function );

			text = "->";
			function = "left";
			width = 32;
			height = 32;
			x = this.game.getWidth() - 450 + 1 * 5 + width * 1;
			y = 1 * 5;
			this.game.getButtons()[6] = new ApoButton(this.game.getImage().getImage("images/button_left.png", false), x, y, width, height, function );

			text = "<-";
			function = "right";
			width = 32;
			height = 32;
			x = this.game.getWidth() - 450 + 2 * 5 + width * 2;
			y = 1 * 5;
			this.game.getButtons()[7] = new ApoButton(this.game.getImage().getImage("images/button_right.png", false), x, y, width, height, function );

			text = "ju";
			function = "jump";
			width = 32;
			height = 32;
			x = this.game.getWidth() - 450 + 3 * 5 + width * 3;
			y = 1 * 5;
			this.game.getButtons()[8] = new ApoButton(this.game.getImage().getImage("images/button_jump.png", false), x, y, width, height, function );

			text = "re";
			function = "redye";
			width = 32;
			height = 32;
			x = this.game.getWidth() - 450 + 4 * 5 + width * 4;
			y = 1 * 5;
			this.game.getButtons()[9] = new ApoButton(this.game.getImage().getImage("images/button_redye.png", false), x, y, width, height, function );

			text = "f1";
			function = "functionOne";
			width = 32;
			height = 32;
			x = this.game.getWidth() - 450 + 5 * 5 + width * 5;
			y = 1 * 5;
			this.game.getButtons()[10] = new ApoButton(this.game.getImage().getImage("images/button_functionOne.png", false), x, y, width, height, function );

			text = "f2";
			function = "functionTwo";
			width = 32;
			height = 32;
			x = this.game.getWidth() - 450 + 6 * 5 + width * 6;
			y = 1 * 5;
			this.game.getButtons()[11] = new ApoButton(this.game.getImage().getImage("images/button_functionTwo.png", false), x, y, width, height, function );

			text = "Reset";
			function = "reset";
			width = 80;
			height = 20;
			x = 10;
			y = this.game.getHeight() - 1 * height - 1 * 5;
			this.game.getButtons()[12] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, Color.gray, Color.white, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = "Stop";
			function = "stop";
			width = 80;
			height = 20;
			x = this.game.getWidth() - 240;
			y = this.game.getHeight() - 1 * height - 1 * 5;
			this.game.getButtons()[13] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, Color.gray, Color.white, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );
			
			text = "editor";
			function = "editor";
			width = 300;
			height = 50;
			x = this.game.getWidth()/2 - width/2;
			y = 250;
			this.game.getButtons()[14] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, new Color(255, 255, 255, 150), Color.black, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100), write), x, y, width, height, function );

			text = "ground";
			function = "editorGround";
			width = 35;
			height = 35;
			x = ApoBotConstants.GAME_WIDTH - 35 * 3 - 5 * 3;
			y = 100;
			this.game.getButtons()[15] = new ApoButton(this.game.getImage().getButtonWithImage( width * 3, height, this.game.getITile().getSubimage(0, 0, 32, 25), Color.gray, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );
			
			text = "original";
			function = "editorOriginal";
			width = 35;
			height = 35;
			x = ApoBotConstants.GAME_WIDTH - 35 * 2 - 5 * 2;
			y = 100;
			this.game.getButtons()[16] = new ApoButton(this.game.getImage().getButtonWithImage( width * 3, height, this.game.getITile().getSubimage(1 * 32, 0, 32, 25), Color.gray, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = "goal";
			function = "editorGoal";
			width = 35;
			height = 35;
			x = ApoBotConstants.GAME_WIDTH - 35 * 1 - 5 * 1;
			y = 100;
			this.game.getButtons()[17] = new ApoButton(this.game.getImage().getButtonWithImage( width * 3, height, this.game.getITile().getSubimage(2 * 32, 0, 32, 25), Color.gray, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = "<";
			function = "editorLevelXLeft";
			width = 20;
			height = 20;
			x = ApoBotConstants.GAME_GAME_WIDTH + 1 * 5;
			y = 25;
			this.game.getButtons()[18] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, Color.gray, Color.white, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = ">";
			function = "editorLevelXRight";
			width = 20;
			height = 20;
			x = ApoBotConstants.GAME_WIDTH - 1 * 5 - 1 * width;
			y = 25;
			this.game.getButtons()[19] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, Color.gray, Color.white, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = "set";
			function = "editorSet";
			width = 80;
			height = 20;
			x = ApoBotConstants.GAME_GAME_WIDTH + ApoBotConstants.GAME_MENU_WIDTH/2 - width/2;
			y = 70;
			this.game.getButtons()[20] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, Color.gray, Color.white, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = "<";
			function = "editorMainLeft";
			width = 20;
			height = 20;
			x = ApoBotConstants.GAME_GAME_WIDTH + 1 * 5;
			y = 160;
			this.game.getButtons()[21] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, Color.gray, Color.white, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = ">";
			function = "editorMainRight";
			width = 20;
			height = 20;
			x = ApoBotConstants.GAME_WIDTH - 1 * 5 - 1 * width;
			y = 160;
			this.game.getButtons()[22] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, Color.gray, Color.white, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = "<";
			function = "editorFunctionOneLeft";
			width = 20;
			height = 20;
			x = ApoBotConstants.GAME_GAME_WIDTH + 1 * 5;
			y = 210;
			this.game.getButtons()[23] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, Color.gray, Color.white, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = ">";
			function = "editorFunctionOneRight";
			width = 20;
			height = 20;
			x = ApoBotConstants.GAME_WIDTH - 1 * 5 - 1 * width;
			y = 210;
			this.game.getButtons()[24] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, Color.gray, Color.white, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = "<";
			function = "editorFunctionTwoLeft";
			width = 20;
			height = 20;
			x = ApoBotConstants.GAME_GAME_WIDTH + 1 * 5;
			y = 260;
			this.game.getButtons()[25] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, Color.gray, Color.white, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = ">";
			function = "editorFunctionTwoRight";
			width = 20;
			height = 20;
			x = ApoBotConstants.GAME_WIDTH - 1 * 5 - 1 * width;
			y = 260;
			this.game.getButtons()[26] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, Color.gray, Color.white, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = "load";
			function = "editorLoad";
			width = 70;
			height = 20;
			x = ApoBotConstants.GAME_GAME_WIDTH + 5;
			y = 400;
			this.game.getButtons()[27] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, Color.gray, Color.white, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = "save";
			function = "editorSave";
			width = 70;
			height = 20;
			x = ApoBotConstants.GAME_WIDTH - width - 5;
			y = 400;
			this.game.getButtons()[28] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, Color.gray, Color.white, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = "<";
			function = "editorLevelYLeft";
			width = 20;
			height = 20;
			x = ApoBotConstants.GAME_GAME_WIDTH + 1 * 5;
			y = 50;
			this.game.getButtons()[29] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, Color.gray, Color.white, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = ">";
			function = "editorLevelYRight";
			width = 20;
			height = 20;
			x = ApoBotConstants.GAME_WIDTH - 1 * 5 - 1 * width;
			y = 50;
			this.game.getButtons()[30] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, Color.gray, Color.white, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = "test";
			function = "editorTest";
			width = 70;
			height = 20;
			x = ApoBotConstants.GAME_GAME_WIDTH + 5;
			y = ApoBotConstants.GAME_HEIGHT - height * 1 - 5 * 1;
			this.game.getButtons()[31] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, Color.gray, Color.white, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = "player";
			function = "editorPlayer";
			width = 35;
			height = 35;
			x = ApoBotConstants.GAME_WIDTH - 35 * 4 - 5 * 4;
			y = 100;
			this.game.getButtons()[32] = new ApoButton(this.game.getImage().getButtonWithImage( width * 3, height, this.game.getIPlayer().getSubimage(0, 70, 23, 25), Color.gray, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );
		
			text = "add";
			function = "editorAdd";
			width = 70;
			height = 20;
			x = ApoBotConstants.GAME_GAME_WIDTH + 5;
			y = 340;
			this.game.getButtons()[33] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, Color.gray, Color.white, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = "rem";
			function = "editorRemove";
			width = 70;
			height = 20;
			x = ApoBotConstants.GAME_WIDTH - width - 5;
			y = 340;
			this.game.getButtons()[34] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, Color.gray, Color.white, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = "<";
			function = "editorLevelLeft";
			width = 20;
			height = 20;
			x = ApoBotConstants.GAME_GAME_WIDTH + 1 * 5;
			y = 365;
			this.game.getButtons()[35] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, Color.gray, Color.white, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = ">";
			function = "editorLevelRight";
			width = 20;
			height = 20;
			x = ApoBotConstants.GAME_WIDTH - 1 * 5 - 1 * width;
			y = 365;
			this.game.getButtons()[36] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, Color.gray, Color.white, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = "<";
			function = "editorLevelChangeLeft";
			width = 20;
			height = 20;
			x = ApoBotConstants.GAME_GAME_WIDTH + 2 * 5 + 1 * width;
			y = 365;
			this.game.getButtons()[37] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, Color.gray, Color.white, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = ">";
			function = "editorLevelChangeRight";
			width = 20;
			height = 20;
			x = ApoBotConstants.GAME_WIDTH - 2 * 5 - 2 * width;
			y = 365;
			this.game.getButtons()[38] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, Color.gray, Color.white, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = "load";
			function = "gameLoad";
			width = 70;
			height = 20;
			x = 25;
			y = 10;
			this.game.getButtons()[39] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, Color.gray, Color.white, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = "<";
			function = "gameLevelChangeLeft";
			width = 20;
			height = 20;
			x = 5;
			y = 35;
			this.game.getButtons()[40] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, Color.gray, Color.white, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = ">";
			function = "gameLevelChangeRight";
			width = 20;
			height = 20;
			x = 95;
			y = 35;
			this.game.getButtons()[41] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, Color.gray, Color.white, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = "<";
			function = "gameLevelLeft";
			width = 20;
			height = 20;
			x = 5;
			y = 60;
			this.game.getButtons()[42] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, Color.gray, Color.white, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = ">";
			function = "gameLevelRight";
			width = 20;
			height = 20;
			x = 95;
			y = 60;
			this.game.getButtons()[43] = new ApoButton(this.game.getImage().getButtonImage( width * 3, height, text, Color.gray, Color.white, Color.black, new Color(255, 255, 0, 100), new Color(255, 0, 0, 100)), x, y, width, height, function );

			text = "->";
			function = "leftView";
			width = 32;
			height = 32;
			x = this.game.getWidth() - 450 - 1 * 5 - width * 1;
			y = 1 * 15;
			this.game.getButtons()[44] = new ApoButton(this.game.getImage().getImage("images/button_left.png", false), x, y, width, height, function );

			text = "->";
			function = "rightView";
			width = 32;
			height = 32;
			x = this.game.getWidth() - 450 + 7 * 5 + width * 7;
			y = 1 * 15;
			this.game.getButtons()[45] = new ApoButton(this.game.getImage().getImage("images/button_left.png", false), x, y, width, height, function );

		}
	}
	
}
