package apoMario.entity;

import java.awt.image.BufferedImage;

import apoMario.ApoMarioConstants;

public class ApoMarioGoodieFireflower extends ApoMarioGoodieMushroom {

	public ApoMarioGoodieFireflower(BufferedImage animation, float x, float y, int id) {
		super(animation, x, y, true, id);
		this.setVecX(0);
	}
	
	public void catchMe(ApoMarioPlayer player) {
		player.catchFireflower();
		player.setPoints(player.getPoints() + ApoMarioConstants.POINTS_GOODIE_FLOWER);
		this.setBVisible(false);
	}

}
