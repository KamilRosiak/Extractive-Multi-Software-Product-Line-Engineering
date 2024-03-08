package apoMario.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.apogames.entity.ApoButton;

import apoMario.ApoMarioConstants;
import apoMario.game.panels.ApoMarioAnalysis;
import apoMario.game.panels.ApoMarioCredits;
import apoMario.game.panels.ApoMarioGame;
import apoMario.game.panels.ApoMarioMenu;
import apoMario.game.panels.ApoMarioOptions;
import apoMario.game.panels.ApoMarioSimulation;

/**
 * Hilfsklasse, um die ganzen Buttons (für alle Panels) zu erstellen
 * @author Dirk Aporius
 *
 */
public class ApoMarioButtons {
	
	private ApoMarioPanel game;
	
	public ApoMarioButtons(final ApoMarioPanel game) {
		this.game = game;
	}
	
	public void makeButtons() {
		if (this.game.getButtons() == null) {
			this.game.setButtons(new ApoButton[38]);
			
			Font font = ApoMarioConstants.FONT_BUTTON;
			String text = "quit";
			String function = ApoMarioMenu.FUNCTION_QUIT;
			int width = (int)(ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE * 1.5f);
			int height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			int x = 18 * height;
			int y = 13 * height;
			BufferedImage iButton = this.game.getImages().getButtonImageColor(3 * width, height, text, new Color(255, 255, 255, 150), new Color(0, 0, 0), new Color(0, 0, 0), font, 10);
			this.game.getButtons()[0] = new ApoButton(iButton, x, y, width, height, function);
			
			font = ApoMarioConstants.FONT_BUTTON_START;
			text = "start";
			function = ApoMarioMenu.FUNCTION_START;
			width = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE * 3;
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE * 2;
			x = 10 * height / 2 - width/3;
			y = 5 * height / 2 + height/8;
			iButton = this.game.getImages().getButtonImageColor(3 * width, height, text, new Color(255, 255, 255, 150), new Color(0, 0, 0), new Color(0, 0, 0), font, 10);
			this.game.getButtons()[1] = new ApoButton(iButton, x, y, width, height, function);
			
			font = ApoMarioConstants.FONT_BUTTON;
			text = "load";
			function = ApoMarioMenu.FUNCTION_LOAD_PLAYER_ONE;
			width = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE * 2;
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = 5 * height - width/4;
			y = 4 * height + height/4;
			iButton = this.game.getImages().getButtonImageColor(3 * width, height, text, new Color(255, 255, 255, 150), new Color(0, 0, 0), new Color(0, 0, 0), font, 10);
			this.game.getButtons()[2] = new ApoButton(iButton, x, y, width, height, function);

			text = "load";
			function = ApoMarioMenu.FUNCTION_LOAD_PLAYER_TWO;
			width = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE * 2;
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = 15 * height - width/4;
			y = 4 * height + height/4;
			iButton = this.game.getImages().getButtonImageColor(3 * width, height, text, new Color(255, 255, 255, 150), new Color(0, 0, 0), new Color(0, 0, 0), font, 10);
			this.game.getButtons()[3] = new ApoButton(iButton, x, y, width, height, function);
			
			text = "options";
			function = ApoMarioMenu.FUNCTION_OPTIONS;
			width = (int)(ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE * 2.7f);
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = 4 * height - width;
			y = 9 * height;
			iButton = this.game.getImages().getButtonImageColor(3 * width, height, text, new Color(255, 255, 255, 150), new Color(0, 0, 0), new Color(0, 0, 0), font, 10);
			this.game.getButtons()[4] = new ApoButton(iButton, x, y, width, height, function);
			
			text = "back";
			function = ApoMarioOptions.FUNCTION_OPTIONS_BACK;
			width = (int)(ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE * 2f);
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = 17 * height;
			y = 13 * height;
			iButton = this.game.getImages().getButtonImageColor(3 * width, height, text, new Color(255, 255, 255, 150), new Color(0, 0, 0), new Color(0, 0, 0), font, 10);
			this.game.getButtons()[5] = new ApoButton(iButton, x, y, width, height, function);
			
			text = "<";
			function = ApoMarioOptions.FUNCTION_OPTIONS_LEFT_DIFFICULTY;
			width = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = ApoMarioConstants.GAME_WIDTH*1/4 - width/2;
			y = ApoMarioConstants.GAME_HEIGHT*1/4 + 2 * height;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[6] = new ApoButton(iButton, x, y, width, height, function);
			this.game.getButtons()[6].setBWait(true);
			
			text = ">";
			function = ApoMarioOptions.FUNCTION_OPTIONS_RIGHT_DIFFICULTY;
			width = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = ApoMarioConstants.GAME_WIDTH*3/4 - width/2;
			y = ApoMarioConstants.GAME_HEIGHT*1/4 + 2 * height;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[7] = new ApoButton(iButton, x, y, width, height, function);
			this.game.getButtons()[7].setBWait(true);
			
			text = "<";
			function = ApoMarioOptions.FUNCTION_OPTIONS_LEFT_LEVELWIDTH;
			width = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = ApoMarioConstants.GAME_WIDTH*1/4 - width/2;
			y = ApoMarioConstants.GAME_HEIGHT*1/2 + height;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[8] = new ApoButton(iButton, x, y, width, height, function);
			this.game.getButtons()[8].setBWait(true);
			
			text = ">";
			function = ApoMarioOptions.FUNCTION_OPTIONS_RIGHT_LEVELWIDTH;
			width = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = ApoMarioConstants.GAME_WIDTH*3/4 - width/2;
			y = ApoMarioConstants.GAME_HEIGHT*1/2 + height;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[9] = new ApoButton(iButton, x, y, width, height, function);
			this.game.getButtons()[9].setBWait(true);
			
			text = "menu";
			function = ApoMarioAnalysis.FUNCTION_ANALYSIS_BACK;
			width = (int)(ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE * 2f);
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = 16 * height;
			y = 14 * height;
			iButton = this.game.getImages().getButtonImageColor(3 * width, height, text, new Color(255, 255, 255, 150), new Color(0, 0, 0), new Color(0, 0, 0), font, 10);
			this.game.getButtons()[10] = new ApoButton(iButton, x, y, width, height, function);
			
			text = "restart";
			function = ApoMarioAnalysis.FUNCTION_ANALYSIS_RESTART;
			width = (int)(ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE * 2.3f);
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = 16 * height;
			y = 12 * height;
			iButton = this.game.getImages().getButtonImageColor(3 * width, height, text, new Color(255, 255, 255, 150), new Color(0, 0, 0), new Color(0, 0, 0), font, 10);
			this.game.getButtons()[11] = new ApoButton(iButton, x, y, width, height, function);
			
			text = "new Level";
			function = ApoMarioAnalysis.FUNCTION_ANALYSIS_NEWLEVEL;
			width = (int)(ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE * 3.7f);
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = 16 * height;
			y = 10 * height;
			iButton = this.game.getImages().getButtonImageColor(3 * width, height, text, new Color(255, 255, 255, 150), new Color(0, 0, 0), new Color(0, 0, 0), font, 10);
			this.game.getButtons()[12] = new ApoButton(iButton, x, y, width, height, function);
			
			Font fontArrows = ApoMarioConstants.FONT_ARROW;
			text = "<";
			function = ApoMarioMenu.FUNCTION_PLAYER_ONE_LEFT;
			width = ApoMarioConstants.APP_SIZE * 15;
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = ApoMarioConstants.GAME_WIDTH/8 - width/2;
			y = ApoMarioConstants.GAME_HEIGHT*1/4 - 2 * height;
			iButton = this.game.getImages().getButtonImageColor(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), fontArrows, 3);
			this.game.getButtons()[13] = new ApoButton(iButton, x, y, width, height, function);
			
			text = ">";
			function = ApoMarioMenu.FUNCTION_PLAYER_ONE_RIGHT;
			width = ApoMarioConstants.APP_SIZE * 15;
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = ApoMarioConstants.GAME_WIDTH/4 + width/2 + 25 * ApoMarioConstants.APP_SIZE;
			y = ApoMarioConstants.GAME_HEIGHT*1/4 - 2 * height;
			iButton = this.game.getImages().getButtonImageColor(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), fontArrows, 3);
			this.game.getButtons()[14] = new ApoButton(iButton, x, y, width, height, function);

			text = "<";
			function = ApoMarioMenu.FUNCTION_PLAYER_TWO_LEFT;
			width = ApoMarioConstants.APP_SIZE * 15;
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = ApoMarioConstants.GAME_WIDTH*1/8 + ApoMarioConstants.GAME_WIDTH*1/2 - width/2;
			y = ApoMarioConstants.GAME_HEIGHT*1/4 - 2 * height;
			iButton = this.game.getImages().getButtonImageColor(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), fontArrows, 3);
			this.game.getButtons()[15] = new ApoButton(iButton, x, y, width, height, function);
			
			text = ">";
			function = ApoMarioMenu.FUNCTION_PLAYER_TWO_RIGHT;
			width = ApoMarioConstants.APP_SIZE * 15;
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = ApoMarioConstants.GAME_WIDTH*3/4 + width/2 + 25 * ApoMarioConstants.APP_SIZE;
			y = ApoMarioConstants.GAME_HEIGHT*1/4 - 2 * height;
			iButton = this.game.getImages().getButtonImageColor(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), fontArrows, 3);
			this.game.getButtons()[16] = new ApoButton(iButton, x, y, width, height, function);
			
			int startY = 7 + ApoMarioConstants.TILE_SIZE/2 + 1 + 1 * ApoMarioConstants.APP_SIZE;
			Graphics g = iButton.getGraphics();
			g.setFont(ApoMarioConstants.FONT_FPS);
			int addY = g.getFontMetrics().getHeight() - 3;
			g.dispose();
			text = "<";
			function = ApoMarioGame.FUNCTION_SPEED_LEFT;
			width = ApoMarioConstants.APP_SIZE * 8;
			height = ApoMarioConstants.APP_SIZE * 8;
			x = ApoMarioConstants.GAME_WIDTH*1/2 - width - 30 * ApoMarioConstants.APP_SIZE;
			y = startY + 4 * addY - 3;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), new Color(0, 0, 0), new Color(254, 254, 254, 0), font, 3);
			this.game.getButtons()[17] = new ApoButton(iButton, x, y, width, height, function);
			
			text = ">";
			function = ApoMarioGame.FUNCTION_SPEED_RIGHT;
			width = ApoMarioConstants.APP_SIZE * 8;
			height = ApoMarioConstants.APP_SIZE * 8;
			x = ApoMarioConstants.GAME_WIDTH*1/2 + 30 * ApoMarioConstants.APP_SIZE;
			y = startY + 4 * addY - 3;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), new Color(0, 0, 0), new Color(254, 254, 254, 0), font, 3);
			this.game.getButtons()[18] = new ApoButton(iButton, x, y, width, height, function);

			text = "simulate";
			function = ApoMarioMenu.FUNCTION_SIMULATE;
			width = (int)(ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE * 2.9f);
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = 3 * height - width;
			y = 11 * height;
			iButton = this.game.getImages().getButtonImageColor(3 * width, height, text, new Color(255, 255, 255, 150), new Color(0, 0, 0), new Color(0, 0, 0), font, 10);
			this.game.getButtons()[19] = new ApoButton(iButton, x, y, width, height, function);
			
			text = "load replay";
			function = ApoMarioMenu.FUNCTION_REPLAYLOAD;
			width = (int)(ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE * 4f);
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = 9 * height - width;
			y = 14 * height;
			iButton = this.game.getImages().getButtonImageColor(3 * width, height, text, new Color(255, 255, 255, 150), new Color(0, 0, 0), new Color(0, 0, 0), font, 10);
			this.game.getButtons()[20] = new ApoButton(iButton, x, y, width, height, function);
			
			text = "play replay";
			function = ApoMarioAnalysis.FUNCTION_ANALYSIS_REPLAYPLAY;
			width = (int)(ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE * 4.5f);
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = 5 * height - width;
			y = 10 * height;
			iButton = this.game.getImages().getButtonImageColor(3 * width, height, text, new Color(255, 255, 255, 150), new Color(0, 0, 0), new Color(0, 0, 0), font, 10);
			this.game.getButtons()[21] = new ApoButton(iButton, x, y, width, height, function);

			text = "load replay";
			function = ApoMarioAnalysis.FUNCTION_ANALYSIS_REPLAYLOAD;
			width = (int)(ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE * 4.5f);
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = 5 * height - width;
			y = 12 * height;
			iButton = this.game.getImages().getButtonImageColor(3 * width, height, text, new Color(255, 255, 255, 150), new Color(0, 0, 0), new Color(0, 0, 0), font, 10);
			this.game.getButtons()[22] = new ApoButton(iButton, x, y, width, height, function);
			
			text = "save replay";
			function = ApoMarioAnalysis.FUNCTION_ANALYSIS_REPLAYSAVE;
			width = (int)(ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE * 4.5f);
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = 5 * height - width;
			y = 14 * height;
			iButton = this.game.getImages().getButtonImageColor(3 * width, height, text, new Color(255, 255, 255, 150), new Color(0, 0, 0), new Color(0, 0, 0), font, 10);
			this.game.getButtons()[23] = new ApoButton(iButton, x, y, width, height, function);
			
			text = "back";
			function = ApoMarioCredits.FUNCTION_CREDITS_BACK;
			width = (int)(ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE * 2f);
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = 17 * height;
			y = 13 * height;
			iButton = this.game.getImages().getButtonImageColor(3 * width, height, text, new Color(255, 255, 255, 150), new Color(0, 0, 0), new Color(0, 0, 0), font, 10);
			this.game.getButtons()[24] = new ApoButton(iButton, x, y, width, height, function);
			
			text = "credits";
			function = ApoMarioMenu.FUNCTION_CREDITS;
			width = (int)(ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE * 2.5f);
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = 17 * height;
			y = 10 * height;
			iButton = this.game.getImages().getButtonImageColor(3 * width, height, text, new Color(255, 255, 255, 150), new Color(0, 0, 0), new Color(0, 0, 0), font, 10);
			this.game.getButtons()[25] = new ApoButton(iButton, x, y, width, height, function);
			
			text = "back";
			function = ApoMarioSimulation.FUNCTION_SIMULATION_BACK;
			width = (int)(ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE * 2f);
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = 17 * height;
			y = 13 * height;
			iButton = this.game.getImages().getButtonImageColor(3 * width, height, text, new Color(255, 255, 255, 150), new Color(0, 0, 0), new Color(0, 0, 0), font, 10);
			this.game.getButtons()[26] = new ApoButton(iButton, x, y, width, height, function);
			
			text = "simulate";
			function = ApoMarioSimulation.FUNCTION_SIMULATION_SIMULATE;
			width = (int)(ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE * 3f);
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = ApoMarioConstants.GAME_WIDTH/2 - width/2;
			y = 10 * height;
			iButton = this.game.getImages().getButtonImageColor(3 * width, height, text, new Color(255, 255, 255, 150), new Color(0, 0, 0), new Color(0, 0, 0), font, 10);
			this.game.getButtons()[27] = new ApoButton(iButton, x, y, width, height, function);
			
			text = "<";
			function = ApoMarioSimulation.FUNCTION_SIMULATION_LEFTGAME;
			width = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = (int)this.game.getButtons()[6].getX();
			y = (int)this.game.getButtons()[6].getY() - 1 * height;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[28] = new ApoButton(iButton, x, y, width, height, function);
			this.game.getButtons()[28].setBWait(true);
			
			text = ">";
			function = ApoMarioSimulation.FUNCTION_SIMULATION_RIGHTGAME;
			width = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = (int)this.game.getButtons()[7].getX();
			y = (int)this.game.getButtons()[7].getY() - 1 * height;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[29] = new ApoButton(iButton, x, y, width, height, function);
			this.game.getButtons()[29].setBWait(true);
			
			text = "<";
			function = ApoMarioSimulation.FUNCTION_SIMULATION_LEFTWIDTH;
			width = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = (int)this.game.getButtons()[6].getX();
			y = (int)this.game.getButtons()[6].getY() + 3 * height;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[30] = new ApoButton(iButton, x, y, width, height, function);
			this.game.getButtons()[30].setBWait(true);
			
			text = ">";
			function = ApoMarioSimulation.FUNCTION_SIMULATION_RIGHTWIDTH;
			width = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = (int)this.game.getButtons()[7].getX();
			y = (int)this.game.getButtons()[7].getY() + 3 * height;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[31] = new ApoButton(iButton, x, y, width, height, function);
			this.game.getButtons()[31].setBWait(true);
			
			text = "<";
			function = ApoMarioSimulation.FUNCTION_SIMULATION_LEFTDIF;
			width = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = (int)this.game.getButtons()[6].getX();
			y = (int)this.game.getButtons()[6].getY() + 1 * height;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[32] = new ApoButton(iButton, x, y, width, height, function);
			this.game.getButtons()[32].setBWait(true);
			
			text = ">";
			function = ApoMarioSimulation.FUNCTION_SIMULATION_RIGHTDIF;
			width = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = (int)this.game.getButtons()[7].getX();
			y = (int)this.game.getButtons()[7].getY() + 1 * height;
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[33] = new ApoButton(iButton, x, y, width, height, function);
			this.game.getButtons()[33].setBWait(true);
			
			text = "<";
			function = ApoMarioSimulation.FUNCTION_SIMULATION_LEFTREPLAY;
			width = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = (int)width * 2 + 5;
			y = (int)(ApoMarioConstants.GAME_HEIGHT - 4 * height);
			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[34] = new ApoButton(iButton, x, y, width, height, function);
			this.game.getButtons()[34].setBWait(true);
			
			text = ">";
			function = ApoMarioSimulation.FUNCTION_SIMULATION_RIGHTREPLAY;
			width = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = (int)(ApoMarioConstants.GAME_WIDTH - width * 3 - 5);
			y = (int)(ApoMarioConstants.GAME_HEIGHT - 4 * height);			iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 0), Color.BLACK, new Color(0, 0, 0, 0), font, 3);
			this.game.getButtons()[35] = new ApoButton(iButton, x, y, width, height, function);
			this.game.getButtons()[35].setBWait(true);
			
			text = "editor";
			function = ApoMarioMenu.fUNCTION_EDITOR;
			width = (int)(ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE * 2.1f);
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = 12 * height;
			y = 10 * height;
			iButton = this.game.getImages().getButtonImageColor(3 * width, height, text, new Color(255, 255, 255, 150), new Color(0, 0, 0), new Color(0, 0, 0), font, 10);
			this.game.getButtons()[36] = new ApoButton(iButton, x, y, width, height, function);
			
			text = "load Level";
			function = ApoMarioMenu.FUNCTION_EDITORLOAD;
			width = (int)(ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE * 3.45f);
			height = ApoMarioConstants.APP_SIZE * ApoMarioConstants.TILE_SIZE;
			x = (int)(5.9 * height);
			y = 10 * height;
			iButton = this.game.getImages().getButtonImageColor(3 * width, height, text, new Color(255, 255, 255, 150), new Color(0, 0, 0), new Color(0, 0, 0), font, 10);
			this.game.getButtons()[37] = new ApoButton(iButton, x, y, width, height, function);

		}
	}	
	
}
