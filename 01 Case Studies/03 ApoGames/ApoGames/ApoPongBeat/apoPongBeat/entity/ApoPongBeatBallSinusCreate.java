package apoPongBeat.entity;

import org.apogames.help.ApoHelp;

import apoPongBeat.ApoPongBeatConstants;
import apoPongBeat.game.ApoPongBeatPanel;

public class ApoPongBeatBallSinusCreate {

	public ApoPongBeatBallSinusCreate(ApoPongBeatPanel panel, int count) {
		float vecX = (float)(-0.5 + Math.random() * 0.1f);
		float vecY = (float)(-0.4 + Math.random() * 0.1f);
		int width = 20;
		int maxChange = 55 - ApoHelp.getRandomValue(15, 0);
		int y = ApoHelp.getRandomValue(ApoPongBeatConstants.MAX_Y - ApoPongBeatConstants.MIN_Y - width - 2 * maxChange - 2, ApoPongBeatConstants.MIN_Y + maxChange + 1);
		for (int i = 0; i < count; i++) {
			panel.getBalls().add(new ApoPongBeatBallSinus(y, width, width, vecX, vecY, i * 80, maxChange));
		}
	}
	
}
