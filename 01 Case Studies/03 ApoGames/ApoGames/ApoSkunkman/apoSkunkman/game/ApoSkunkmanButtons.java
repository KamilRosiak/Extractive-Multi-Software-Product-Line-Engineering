package apoSkunkman.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import org.apogames.entity.ApoButton;

import apoSkunkman.ApoSkunkmanConstants;
import apoSkunkman.ApoSkunkmanImageContainer;

/**
 * Hilfsklasse, um die Buttons für das Spiel zu erstellen
 * @author Dirk Aporius
 *
 */
public class ApoSkunkmanButtons {
	/** das eigentliche Spielobjekt */
	private ApoSkunkmanPanel game;
	/**
	 * Konstruktor
	 * @param game : Das eigentliche Spielobjekt
	 */
	public ApoSkunkmanButtons(ApoSkunkmanPanel game) {
		this.game = game;
	}

	/**
	 * wird aufgerufen, um die ganzen Buttons zu erstellen
	 */
	public void init() {
		if (this.game.getButtons() == null) {
			this.game.setButtons(new ApoButton[51]);
			
			int gameWidth = ApoSkunkmanConstants.GAME_WIDTH;
			int gameHeight = ApoSkunkmanConstants.GAME_HEIGHT;
			
			Font font = ApoSkunkmanConstants.FONT_HUD_TREE;
			if (ApoSkunkmanConstants.LEVEL_TILESET.equals(ApoSkunkmanConstants.LEVEL_TILESETS[1])) {
				font = ApoSkunkmanConstants.FONT_HUD_TREE_ASCII;
			}
			final BufferedImage iHudTree = ApoSkunkmanImageContainer.iHudTree;

			String function = ApoSkunkmanModelGame.QUIT;
			int width = iHudTree.getWidth();
			int height = iHudTree.getHeight();
			int x = 7 * ApoSkunkmanConstants.APPLICATION_SIZE;
			int y = gameHeight - 1 * height - 1 * 3 * ApoSkunkmanConstants.APPLICATION_SIZE;
			BufferedImage iButton = ApoSkunkmanImageContainer.iQuit;//this.game.getImages().getButtonWithImage(width * 3, height, iHudTree, function, Color.BLACK, Color.YELLOW, Color.RED, font);
			this.game.getButtons()[0] = new ApoButton(iButton, x, y, width, height, function);
			
			function = ApoSkunkmanModelEditor.BACK;
			width = iHudTree.getWidth();
			height = iHudTree.getHeight();
			x = ApoSkunkmanImageContainer.iHud.getWidth() - width - 7 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = gameHeight - 1 * height - 1 * 3 * ApoSkunkmanConstants.APPLICATION_SIZE;
			//iButton = this.game.getImages().getButtonWithImage(width * 3, height, iHudTree, function, Color.BLACK, Color.YELLOW, Color.RED, font);
			iButton = ApoSkunkmanImageContainer.iGame;
			this.game.getButtons()[1] = new ApoButton(iButton, x, y, width, height, function);
			
			function = ApoSkunkmanModelGame.EDITOR;
			width = iHudTree.getWidth();
			height = iHudTree.getHeight();
			x =  ApoSkunkmanImageContainer.iHud.getWidth() - width - 7 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = gameHeight - 1 * height - 1 * 3 * ApoSkunkmanConstants.APPLICATION_SIZE;
			//iButton = this.game.getImages().getButtonWithImage(width * 3, height, iHudTree, function, Color.BLACK, Color.YELLOW, Color.RED, font);
			iButton = ApoSkunkmanImageContainer.iEditor;
			this.game.getButtons()[2] = new ApoButton(iButton, x, y, width, height, function);

			BufferedImage iNew = ApoSkunkmanImageContainer.iNew;
			function = ApoSkunkmanModelGame.NEW_LEVEL;
			width = iNew.getWidth()/3;
			height = iNew.getHeight();
			x = 7 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = gameHeight / 4 - 17 * ApoSkunkmanConstants.APPLICATION_SIZE;
			this.game.getButtons()[3] = new ApoButton(iNew, x, y, width, height, function);

			BufferedImage iPlay = ApoSkunkmanImageContainer.iPlay;
			function = ApoSkunkmanModelGame.PLAY_GAME;
			width = iPlay.getWidth()/3;
			height = iPlay.getHeight();
			x = ApoSkunkmanImageContainer.iHud.getWidth() - width - 7 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = gameHeight / 4 - 17 * ApoSkunkmanConstants.APPLICATION_SIZE;
			this.game.getButtons()[4] = new ApoButton(iPlay, x, y, width, height, function);
			
			BufferedImage iLoad = ApoSkunkmanImageContainer.iLoad;
			function = ApoSkunkmanModelGame.LOAD_EDITORLEVEL;
			width = iLoad.getWidth()/3;
			height = iLoad.getHeight();
			x = 15 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = gameHeight / 4 + 180 * ApoSkunkmanConstants.APPLICATION_SIZE;
			this.game.getButtons()[5] = new ApoButton(iLoad, x, y, width, height, function);

			function = ApoSkunkmanModelGame.LOAD_REPLAY;
			width = iLoad.getWidth()/3;
			height = iLoad.getHeight();
			x = ApoSkunkmanImageContainer.iHud.getWidth() - width - 15 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = gameHeight / 4 + 180 * ApoSkunkmanConstants.APPLICATION_SIZE;
			this.game.getButtons()[6] = new ApoButton(iLoad, x, y, width, height, function);

			function = ApoSkunkmanModelGame.LOAD_PLAYER_ONE;
			width = iLoad.getWidth()/3;
			height = iLoad.getHeight();
			x = ApoSkunkmanImageContainer.iHud.getWidth() + 5 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = 2 * ApoSkunkmanConstants.APPLICATION_SIZE;
			this.game.getButtons()[7] = new ApoButton(iLoad, x, y, width, height, function);

			function = ApoSkunkmanModelGame.CHANGE_PLAYER_ONE_LEFT;
			String text = "<";
			width = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			height = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			x = ApoSkunkmanImageContainer.iHud.getWidth() + 10 * ApoSkunkmanConstants.APPLICATION_SIZE + iLoad.getWidth()/3;
			y = iLoad.getHeight()/2 - height/2 + 2 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[8] = new ApoButton(iButton, x, y, width, height, function);

			function = ApoSkunkmanModelGame.CHANGE_PLAYER_ONE_RIGHT;
			text = ">";
			width = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			height = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			x = ApoSkunkmanImageContainer.iHud.getWidth() + 10 * ApoSkunkmanConstants.APPLICATION_SIZE + iLoad.getWidth()/3 + iHudTree.getWidth() + width + 4 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = iLoad.getHeight()/2 - height/2 + 2 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[9] = new ApoButton(iButton, x, y, width, height, function);
			
			function = ApoSkunkmanModelGame.LOAD_PLAYER_TWO;
			width = iLoad.getWidth()/3;
			height = iLoad.getHeight();
			x = gameWidth - width - 5 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = gameHeight - height - 2 * ApoSkunkmanConstants.APPLICATION_SIZE;
			this.game.getButtons()[10] = new ApoButton(iLoad, x, y, width, height, function);
			
			function = ApoSkunkmanModelGame.CHANGE_PLAYER_TWO_LEFT;
			text = "<";
			width = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			height = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			x = gameWidth - 14 * ApoSkunkmanConstants.APPLICATION_SIZE - iLoad.getWidth()/3 - iHudTree.getWidth() - 2 * width;
			y = gameHeight - height - iLoad.getHeight()/2 + height/2 - 2 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[11] = new ApoButton(iButton, x, y, width, height, function);

			function = ApoSkunkmanModelGame.CHANGE_PLAYER_TWO_RIGHT;
			text = ">";
			width = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			height = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			x = gameWidth - 12 * ApoSkunkmanConstants.APPLICATION_SIZE - iLoad.getWidth()/3 - 1 * width;
			y = gameHeight - height - iLoad.getHeight()/2 + height/2 - 2 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[12] = new ApoButton(iButton, x, y, width, height, function);

			function = ApoSkunkmanModelGame.LOAD_PLAYER_THREE;
			width = iLoad.getWidth()/3;
			height = iLoad.getHeight();
			x = ApoSkunkmanImageContainer.iHud.getWidth() + 5 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = gameHeight - height - 2 * ApoSkunkmanConstants.APPLICATION_SIZE;
			this.game.getButtons()[13] = new ApoButton(iLoad, x, y, width, height, function);
			
			function = ApoSkunkmanModelGame.CHANGE_PLAYER_THREE_LEFT;
			text = "<";
			width = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			height = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			x = ApoSkunkmanImageContainer.iHud.getWidth() + 10 * ApoSkunkmanConstants.APPLICATION_SIZE + iLoad.getWidth()/3;
			y = gameHeight - height - iLoad.getHeight()/2 + height/2 - 2 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[14] = new ApoButton(iButton, x, y, width, height, function);

			function = ApoSkunkmanModelGame.CHANGE_PLAYER_THREE_RIGHT;
			text = ">";
			width = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			height = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			x = ApoSkunkmanImageContainer.iHud.getWidth() + 10 * ApoSkunkmanConstants.APPLICATION_SIZE + iLoad.getWidth()/3 + iHudTree.getWidth() + width + 4 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = gameHeight - height - iLoad.getHeight()/2 + height/2 - 2 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[15] = new ApoButton(iButton, x, y, width, height, function);

			function = ApoSkunkmanModelGame.LOAD_PLAYER_FOUR;
			width = iLoad.getWidth()/3;
			height = iLoad.getHeight();
			x = gameWidth - width - 5 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = 2 * ApoSkunkmanConstants.APPLICATION_SIZE;
			this.game.getButtons()[16] = new ApoButton(iLoad, x, y, width, height, function);
			
			function = ApoSkunkmanModelGame.CHANGE_PLAYER_FOUR_LEFT;
			text = "<";
			width = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			height = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			x = gameWidth - 14 * ApoSkunkmanConstants.APPLICATION_SIZE - iLoad.getWidth()/3 - iHudTree.getWidth() - 2 * width;
			y = iLoad.getHeight()/2 - height/2 + 2 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[17] = new ApoButton(iButton, x, y, width, height, function);

			function = ApoSkunkmanModelGame.CHANGE_PLAYER_FOUR_RIGHT;
			text = ">";
			width = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			height = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			x = gameWidth - 12 * ApoSkunkmanConstants.APPLICATION_SIZE - iLoad.getWidth()/3 - 1 * width;
			y = iLoad.getHeight()/2 - height/2 + 2 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[18] = new ApoButton(iButton, x, y, width, height, function);

			BufferedImage iPause = ApoSkunkmanImageContainer.iPause;
			function = ApoSkunkmanModelGame.PAUSE_GAME;
			width = iPause.getWidth()/3;
			height = iPause.getHeight();
			x = 7 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = gameHeight / 4 - 17 * ApoSkunkmanConstants.APPLICATION_SIZE;;
			this.game.getButtons()[19] = new ApoButton(iPause, x, y, width, height, function);

			BufferedImage iStop = ApoSkunkmanImageContainer.iStop;
			function = ApoSkunkmanModelGame.STOP_GAME;
			width = iStop.getWidth()/3;
			height = iStop.getHeight();
			x = ApoSkunkmanImageContainer.iHud.getWidth() - width - 7 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = gameHeight / 4 - 17 * ApoSkunkmanConstants.APPLICATION_SIZE;;
			this.game.getButtons()[20] = new ApoButton(iStop, x, y, width, height, function);
			
			function = ApoSkunkmanModelGame.MAX_PLAYER_CHANGE_LEFT;
			text = "<";
			width = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			height = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			x = 15 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = gameHeight / 4 + 40 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[21] = new ApoButton(iButton, x, y, width, height, function);
			this.game.getButtons()[21].setBWait(true);

			function = ApoSkunkmanModelGame.MAX_PLAYER_CHANGE_RIGHT;
			text = ">";
			width = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			height = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			x = ApoSkunkmanImageContainer.iHud.getWidth() - width - 15 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = gameHeight / 4 + 40 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[22] = new ApoButton(iButton, x, y, width, height, function);
			this.game.getButtons()[22].setBWait(true);
			
			function = ApoSkunkmanModelGame.TYPE_CHANGE_LEFT;
			text = "<";
			width = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			height = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			x = 15 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = gameHeight / 4 + 70 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[23] = new ApoButton(iButton, x, y, width, height, function);

			function = ApoSkunkmanModelGame.TYPE_CHANGE_RIGHT;
			text = ">";
			width = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			height = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			x = ApoSkunkmanImageContainer.iHud.getWidth() - width - 15 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = gameHeight / 4 + 70 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[24] = new ApoButton(iButton, x, y, width, height, function);
			
			function = ApoSkunkmanModelGame.BUSH_CHANGE_LEFT;
			text = "<";
			width = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			height = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			x = 15 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = gameHeight / 4 + 100 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[25] = new ApoButton(iButton, x, y, width, height, function);

			function = ApoSkunkmanModelGame.BUSH_CHANGE_RIGHT;
			text = ">";
			width = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			height = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			x = ApoSkunkmanImageContainer.iHud.getWidth() - width - 15 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = gameHeight / 4 + 100 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[26] = new ApoButton(iButton, x, y, width, height, function);
			
			function = ApoSkunkmanModelGame.TIME_CHANGE_LEFT;
			text = "<";
			width = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			height = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			x = 15 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = gameHeight / 4 + 130 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[27] = new ApoButton(iButton, x, y, width, height, function);
			this.game.getButtons()[27].setBWait(true);
			                       
			function = ApoSkunkmanModelGame.TIME_CHANGE_RIGHT;
			text = ">";
			width = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			height = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			x = ApoSkunkmanImageContainer.iHud.getWidth() - width - 15 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = gameHeight / 4 + 130 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[28] = new ApoButton(iButton, x, y, width, height, function);
			this.game.getButtons()[28].setBWait(true);
			
			function = ApoSkunkmanModelGame.ANALYSIS_PLAY_REPLAY;
			width = iHudTree.getWidth();
			height = iHudTree.getHeight();
			x = ApoSkunkmanImageContainer.iHud.getWidth() + (gameWidth - ApoSkunkmanImageContainer.iHud.getWidth())/2 - 2 * ApoSkunkmanConstants.APPLICATION_SIZE - width;
			y = gameHeight - height - 35 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonWithImage(width * 3, height, iHudTree, function, Color.BLACK, Color.YELLOW, Color.RED, font);
			this.game.getButtons()[29] = new ApoButton(iButton, x, y, width, height, function);
			
			function = ApoSkunkmanModelGame.ANALYSIS_SAVE_REPLAY;
			width = iHudTree.getWidth();
			height = iHudTree.getHeight();
			x = ApoSkunkmanImageContainer.iHud.getWidth() + (gameWidth - ApoSkunkmanImageContainer.iHud.getWidth())/2 + 2 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = gameHeight - height - 35 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonWithImage(width * 3, height, iHudTree, function, Color.BLACK, Color.YELLOW, Color.RED, font);
			this.game.getButtons()[30] = new ApoButton(iButton, x, y, width, height, function);
			
			function = ApoSkunkmanModelGame.SPEED_CHANGE_LEFT;
			text = "<";
			width = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			height = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			x = 15 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = gameHeight / 4 + 40 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[31] = new ApoButton(iButton, x, y, width, height, function);

			function = ApoSkunkmanModelGame.SPEED_CHANGE_RIGHT;
			text = ">";
			width = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			height = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			x = ApoSkunkmanImageContainer.iHud.getWidth() - width - 15 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = gameHeight / 4 + 40 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[32] = new ApoButton(iButton, x, y, width, height, function);
	
			function = ApoSkunkmanModelEditor.EDITOR_GOODIE_LEFT;
			text = "<";
			width = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			height = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			x = 15 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = gameHeight / 4 + 80 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[33] = new ApoButton(iButton, x, y, width, height, function);
			
			function = ApoSkunkmanModelEditor.EDITOR_GOODIE_RIGHT;
			text = ">";
			width = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			height = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			x = ApoSkunkmanImageContainer.iHud.getWidth() - width - 15 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = gameHeight / 4 + 80 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[34] = new ApoButton(iButton, x, y, width, height, function);
			
			function = ApoSkunkmanModelEditor.EDITOR_TIME_LEFT;
			text = "<";
			width = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			height = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			x = 15 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = gameHeight / 4 + 90 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[35] = new ApoButton(iButton, x, y, width, height, function);
			
			function = ApoSkunkmanModelEditor.EDITOR_TIME_RIGHT;
			text = ">";
			width = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			height = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			x = ApoSkunkmanImageContainer.iHud.getWidth() - width - 15 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = gameHeight / 4 + 90 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[36] = new ApoButton(iButton, x, y, width, height, function);
			
			function = ApoSkunkmanModelEditor.EDITOR_LOAD;
			width = iHudTree.getWidth();
			height = iHudTree.getHeight();
			x = ApoSkunkmanImageContainer.iHud.getWidth() + (gameWidth - ApoSkunkmanImageContainer.iHud.getWidth())/2 - 2 * ApoSkunkmanConstants.APPLICATION_SIZE - width;
			y = gameHeight - height - 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = ApoSkunkmanImageContainer.iLoadTree;
			this.game.getButtons()[37] = new ApoButton(iButton, x, y, width, height, function);
			
			function = ApoSkunkmanModelEditor.EDITOR_SAVE;
			width = iHudTree.getWidth();
			height = iHudTree.getHeight();
			x = ApoSkunkmanImageContainer.iHud.getWidth() + (gameWidth - ApoSkunkmanImageContainer.iHud.getWidth())/2 + 2 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = gameHeight - height - 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = ApoSkunkmanImageContainer.iSaveTree;
			this.game.getButtons()[38] = new ApoButton(iButton, x, y, width, height, function);
			
			function = ApoSkunkmanModelEditor.EDITOR_FREE;
			width = ApoSkunkmanConstants.TILE_SIZE + 6 * ApoSkunkmanConstants.APPLICATION_SIZE;
			height = ApoSkunkmanConstants.TILE_SIZE + 6 * ApoSkunkmanConstants.APPLICATION_SIZE;
			x = ApoSkunkmanImageContainer.iHud.getWidth() + 25 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = 2 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonWithImage(width * 3, height, ApoSkunkmanImageContainer.iTile.getSubimage(0 * ApoSkunkmanConstants.TILE_SIZE, 0, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE), "", Color.BLACK, Color.YELLOW, Color.RED, font);
			this.game.getButtons()[39] = new ApoButton(iButton, x, y, width, height, function);

			function = ApoSkunkmanModelEditor.EDITOR_STONE;
			width = ApoSkunkmanConstants.TILE_SIZE + 6 * ApoSkunkmanConstants.APPLICATION_SIZE;
			height = ApoSkunkmanConstants.TILE_SIZE + 6 * ApoSkunkmanConstants.APPLICATION_SIZE;
			x = ApoSkunkmanImageContainer.iHud.getWidth() + 25 * ApoSkunkmanConstants.APPLICATION_SIZE + 1 * 5 * ApoSkunkmanConstants.APPLICATION_SIZE + 1 * width;
			y = 2 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonWithImage(width * 3, height, ApoSkunkmanImageContainer.iTile.getSubimage(1 * ApoSkunkmanConstants.TILE_SIZE, 0, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE), "", Color.BLACK, Color.YELLOW, Color.RED, font);
			this.game.getButtons()[40] = new ApoButton(iButton, x, y, width, height, function);
			
			function = ApoSkunkmanModelEditor.EDITOR_BUSH;
			width = ApoSkunkmanConstants.TILE_SIZE + 6 * ApoSkunkmanConstants.APPLICATION_SIZE;
			height = ApoSkunkmanConstants.TILE_SIZE + 6 * ApoSkunkmanConstants.APPLICATION_SIZE;
			x = ApoSkunkmanImageContainer.iHud.getWidth() + 25 * ApoSkunkmanConstants.APPLICATION_SIZE + 2 * 5 * ApoSkunkmanConstants.APPLICATION_SIZE + 2 * width;
			y = 2 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonWithImage(width * 3, height, ApoSkunkmanImageContainer.iTile.getSubimage(2 * ApoSkunkmanConstants.TILE_SIZE, 0, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE), "", Color.BLACK, Color.YELLOW, Color.RED, font);
			this.game.getButtons()[41] = new ApoButton(iButton, x, y, width, height, function);
			
			function = ApoSkunkmanModelEditor.EDITOR_PLAYER_ONE;
			width = ApoSkunkmanConstants.TILE_SIZE + 6 * ApoSkunkmanConstants.APPLICATION_SIZE;
			height = ApoSkunkmanConstants.TILE_SIZE + 6 * ApoSkunkmanConstants.APPLICATION_SIZE;
			x = ApoSkunkmanImageContainer.iHud.getWidth() + 25 * ApoSkunkmanConstants.APPLICATION_SIZE + 3 * 5 * ApoSkunkmanConstants.APPLICATION_SIZE + 3 * width;
			y = 2 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonWithImage(width * 3, height, ApoSkunkmanImageContainer.iPlayerOne.getSubimage(1 * ApoSkunkmanConstants.TILE_SIZE, 0, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE), "", Color.BLACK, Color.YELLOW, Color.RED, font);
			this.game.getButtons()[42] = new ApoButton(iButton, x, y, width, height, function);
			
			function = ApoSkunkmanModelEditor.EDITOR_PLAYER_TWO;
			width = ApoSkunkmanConstants.TILE_SIZE + 6 * ApoSkunkmanConstants.APPLICATION_SIZE;
			height = ApoSkunkmanConstants.TILE_SIZE + 6 * ApoSkunkmanConstants.APPLICATION_SIZE;
			x = ApoSkunkmanImageContainer.iHud.getWidth() + 25 * ApoSkunkmanConstants.APPLICATION_SIZE + 4 * 5 * ApoSkunkmanConstants.APPLICATION_SIZE + 4 * width;
			y = 2 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonWithImage(width * 3, height, ApoSkunkmanImageContainer.iPlayerTwo.getSubimage(1 * ApoSkunkmanConstants.TILE_SIZE, 0, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE), "", Color.BLACK, Color.YELLOW, Color.RED, font);
			this.game.getButtons()[43] = new ApoButton(iButton, x, y, width, height, function);
			
			function = ApoSkunkmanModelEditor.EDITOR_PLAYER_THREE;
			width = ApoSkunkmanConstants.TILE_SIZE + 6 * ApoSkunkmanConstants.APPLICATION_SIZE;
			height = ApoSkunkmanConstants.TILE_SIZE + 6 * ApoSkunkmanConstants.APPLICATION_SIZE;
			x = ApoSkunkmanImageContainer.iHud.getWidth() + 25 * ApoSkunkmanConstants.APPLICATION_SIZE + 5 * 5 * ApoSkunkmanConstants.APPLICATION_SIZE + 5 * width;
			y = 2 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonWithImage(width * 3, height, ApoSkunkmanImageContainer.iPlayerThree.getSubimage(1 * ApoSkunkmanConstants.TILE_SIZE, 0, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE), "", Color.BLACK, Color.YELLOW, Color.RED, font);
			this.game.getButtons()[44] = new ApoButton(iButton, x, y, width, height, function);

			function = ApoSkunkmanModelEditor.EDITOR_PLAYER_FOUR;
			width = ApoSkunkmanConstants.TILE_SIZE + 6 * ApoSkunkmanConstants.APPLICATION_SIZE;
			height = ApoSkunkmanConstants.TILE_SIZE + 6 * ApoSkunkmanConstants.APPLICATION_SIZE;
			x = ApoSkunkmanImageContainer.iHud.getWidth() + 25 * ApoSkunkmanConstants.APPLICATION_SIZE + 6 * 5 * ApoSkunkmanConstants.APPLICATION_SIZE + 6 * width;
			y = 2 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonWithImage(width * 3, height, ApoSkunkmanImageContainer.iPlayerFour.getSubimage(1 * ApoSkunkmanConstants.TILE_SIZE, 0, ApoSkunkmanConstants.TILE_SIZE, ApoSkunkmanConstants.TILE_SIZE), "", Color.BLACK, Color.YELLOW, Color.RED, font);
			this.game.getButtons()[45] = new ApoButton(iButton, x, y, width, height, function);

			function = ApoSkunkmanModelEditor.EDITOR_GOALX;
			width = ApoSkunkmanConstants.TILE_SIZE + 6 * ApoSkunkmanConstants.APPLICATION_SIZE;
			height = ApoSkunkmanConstants.TILE_SIZE + 6 * ApoSkunkmanConstants.APPLICATION_SIZE;
			x = ApoSkunkmanImageContainer.iHud.getWidth() + 25 * ApoSkunkmanConstants.APPLICATION_SIZE + 7 * 5 * ApoSkunkmanConstants.APPLICATION_SIZE + 7 * width;
			y = 2 * ApoSkunkmanConstants.APPLICATION_SIZE;
			iButton = this.game.getImages().getButtonWithImage(width * 3, height, ApoSkunkmanImageContainer.iGoalX, "", Color.BLACK, Color.YELLOW, Color.RED, font);
			this.game.getButtons()[46] = new ApoButton(iButton, x, y, width, height, function);

			function = ApoSkunkmanModelEditor.EDITOR_NEW_LEVEL;
			width = iNew.getWidth()/3;
			height = iNew.getHeight();
			x = 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = gameHeight / 4 - 17 * ApoSkunkmanConstants.APPLICATION_SIZE;
			this.game.getButtons()[47] = new ApoButton(iNew, x, y, width, height, function);

			BufferedImage iClear = ApoSkunkmanImageContainer.iClear;
			function = ApoSkunkmanModelEditor.EDITOR_CLEAR_LEVEL;
			width = iClear.getWidth()/3;
			height = iClear.getHeight();
			x = ApoSkunkmanImageContainer.iHud.getWidth() - width - 10 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = gameHeight / 4 - 17 * ApoSkunkmanConstants.APPLICATION_SIZE;
			this.game.getButtons()[48] = new ApoButton(iClear, x, y, width, height, function);
			
			BufferedImage iSimulate = ApoSkunkmanImageContainer.iSimulate;
			function = ApoSkunkmanModelGame.SIMULATION;
			width = iNew.getWidth()/3;
			height = iNew.getHeight();
			x = ApoSkunkmanImageContainer.iHud.getWidth() - width - 7 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = gameHeight / 4 - 15 * ApoSkunkmanConstants.APPLICATION_SIZE + height;
			this.game.getButtons()[49] = new ApoButton(iSimulate, x, y, width, height, function);

			BufferedImage iHelp = ApoSkunkmanImageContainer.iHelp;
			function = ApoSkunkmanModelGame.HELP;
			width = iPlay.getWidth()/3;
			height = iPlay.getHeight();
			x = 7 * ApoSkunkmanConstants.APPLICATION_SIZE;
			y = gameHeight / 4 - 15 * ApoSkunkmanConstants.APPLICATION_SIZE + height;
			this.game.getButtons()[50] = new ApoButton(iHelp, x, y, width, height, function);
		}
	}
}
