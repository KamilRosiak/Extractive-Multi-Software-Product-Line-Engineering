package apoSoccer.entity;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import apoSoccer.game.ApoSoccerGame;

/**
 * Klasse, die einen Torwart beschreibt
 * @author Dirk Aporius
 *
 */
public class ApoGoalKeeper extends ApoPlayer {
	
	public ApoGoalKeeper(BufferedImage background, float x, float y, float radius, int tiles, long time, Rectangle2D.Float rec, ApoSoccerGame game) {
		super(background, x, y, radius, tiles, time, rec, game);
	}

}
