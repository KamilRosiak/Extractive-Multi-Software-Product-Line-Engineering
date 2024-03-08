package apoCommando.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import org.apogames.entity.ApoButton;

import apoCommando.ApoMarioConstants;
import apoCommando.panels.ApoMarioMenu;
public class ApoMarioButtons {
	
	private ApoMarioPanel game;
	
	public ApoMarioButtons(final ApoMarioPanel game) {
		this.game = game;
	}
	
	public void makeButtons() {
		if (this.game.getButtons() == null) {
			this.game.setButtons(new ApoButton[1]);
			
			Font font = ApoMarioConstants.FONT_BUTTON;
			String text = "X";
			String function = ApoMarioMenu.FUNCTION_QUIT;
			int width = ApoMarioConstants.SIZE * 20;
			int height = ApoMarioConstants.SIZE * 20;
			int x = ApoMarioConstants.GAME_WIDTH - 5 - width;
			int y = ApoMarioConstants.GAME_HEIGHT - 1 * height - 1 * 5;
			BufferedImage iButton = this.game.getImages().getButtonImage(3 * width, height, text, new Color(0, 0, 0, 200), new Color(254, 254, 254, 240), new Color(254, 254, 254), font, 3);
			this.game.getButtons()[0] = new ApoButton(iButton, x, y, width, height, function);
		}
	}	
	
}
