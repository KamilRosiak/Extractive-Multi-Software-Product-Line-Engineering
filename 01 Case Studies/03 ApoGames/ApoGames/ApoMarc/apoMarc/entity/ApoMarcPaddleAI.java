package apoMarc.entity;

import java.awt.geom.Rectangle2D;

import org.apogames.help.ApoHelp;

import apoMarc.ApoMarcConstants;
import apoMarc.game.ApoMarcGame;

public class ApoMarcPaddleAI {
	
	public void think(ApoMarcGame game, ApoMarcPlayer player, int delta) {
		ApoMarcPaddle paddle = game.getPaddle();
//		if (player.getDifficulty() == ApoMarcConstants.DIFFICULTY_MARC) {
			if ((paddle.getY() < ApoMarcConstants.GAME_HEIGHT/2) && (paddle.getSpeed() == 0)) {
				int angle = (int)(ApoHelp.getAngleBetween2Points(paddle.getXMiddle(), paddle.getY(), player.getXMiddle(), player.getY() + player.getWidth()/2));
				player.setLos(angle);
				player.setSpeed(0.01f);
				player.setNewSpeedAndPosition(delta, game, player);
			} else {
				int angle = (int)(ApoHelp.getAngleBetween2Points(ApoMarcConstants.GAME_WIDTH/2, 60, player.getXMiddle(), player.getY() + player.getWidth()/2));
				player.setLos(angle);
				player.setSpeed(0.01f);
				if (!(new Rectangle2D.Float(ApoMarcConstants.GAME_WIDTH/2 - player.getWidth()/2 - 5, 60 - player.getWidth()/2 - 5, player.getWidth() + 10, player.getWidth() + 10)).contains(player.getX(), player.getY(), player.getWidth(), player.getWidth())) {
					player.setNewSpeedAndPosition(delta, game, player);
				}
			}
//		}
	}
	
}
