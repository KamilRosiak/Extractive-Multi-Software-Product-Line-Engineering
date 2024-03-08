package apoIcejump.ai;

import java.awt.Color;

import apoIcejump.ApoIcejumpConstants;

/**
 * Easy Player
 * @author Dirk Aporius
 *
 */
public class Easy extends ApoIcejumpAI {

	@Override
	public String getAuthor() {
		return "Dirk Aporius";
	}

	@Override
	public Color getColor() {
		return new Color(255, 255, 0);
	}

//	public String getImage() {
//		return "player_penguin.png";
//	}
	
	@Override
	public String getName() {
		return "Easy";
	}

	@Override
	public void think(ApoIcejumpAILevel level) {
		// falls es noch einen Eisblock gibt, dann
		if (level.getBlocks().size() > 0) {
			// gib die Differenz zwischen beiden Entitäten
			float difference = level.getPlayer().getX() + level.getPlayer().getWidth()/2 - level.getBlocks().get(0).getX() - level.getBlocks().get(0).getWidth()/2;
			// falls die Differenz größer als 3 ist, dann bewege den Spieler mit der Hälfte der Maximaleschwindigkeit nach links, falls Dif kleiner als -3 dann rechts, ansonsten beweg dich nicht
			if (difference > 3) {
				level.getPlayer().setVecX(-ApoIcejumpConstants.PLAYER_MAX_VEC_X/2);
			} else if (difference < -3) {
				level.getPlayer().setVecX(ApoIcejumpConstants.PLAYER_MAX_VEC_X/2);				
			} else {
				level.getPlayer().setVecX(0);				
			}
		}
	}

}
