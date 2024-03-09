
public class CanJump {
	/**
	 * ueberprueft, ob ein Spieler geradeaus laufen darf oder nicht
	 * @return TRUE; SPieler darf laufen, sonst FALSE
	 */
	private boolean canPlayerRun() {
		int x = (int)this.getPlayer().getX();
		int y = (int)this.getPlayer().getY();
		int height = this.getLevel().getLevel()[y][x].getHeight();
		if (this.getPlayer().getCurDir() == ApoBotConstants.DIR_SOUTH_DOWNRIGHT) {
			x += 1;
		} else if (this.getPlayer().getCurDir() == ApoBotConstants.DIR_NORTH_UPLEFT) {
			x -= 1;
		} else if (this.getPlayer().getCurDir() == ApoBotConstants.DIR_WEST_DOWNLEFT) {
			y -= 1;
		} else if (this.getPlayer().getCurDir() == ApoBotConstants.DIR_EAST_UPRIGHT) {
			y += 1;
		}
		if ((x < 0) || (y < 0) || (x >= this.getLevel().getLevel()[0].length) || (y >= this.getLevel().getLevel().length)) {
			return false;
		} else if (this.getLevel().getLevel()[y][x].getGround() == ApoBotConstants.GROUND_REAL_EMPTY) {
			return false;
		}
		if (height == this.getLevel().getLevel()[y][x].getHeight()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * gibt zurueck, ob ein Spieler springen darf oder nicht
	 * @return TRUE; Spieler kann eine Stufe hoeher springen oder viel tiefer, sonst FALSE (springt auf der Stelle)
	 */
	private boolean canPlayerJump() {
		int x = (int)this.getPlayer().getX();
		int y = (int)this.getPlayer().getY();
		int height = this.getLevel().getLevel()[y][x].getHeight();
		if (this.getPlayer().getCurDir() == ApoBotConstants.DIR_SOUTH_DOWNRIGHT) {
			x += 1;
		} else if (this.getPlayer().getCurDir() == ApoBotConstants.DIR_NORTH_UPLEFT) {
			x -= 1;
		} else if (this.getPlayer().getCurDir() == ApoBotConstants.DIR_WEST_DOWNLEFT) {
			y -= 1;
		} else if (this.getPlayer().getCurDir() == ApoBotConstants.DIR_EAST_UPRIGHT) {
			y += 1;
		}
		if ((x < 0) || (y < 0) || (x >= this.getLevel().getLevel()[0].length) || (y >= this.getLevel().getLevel().length)) {
			return false;
		} else if (this.getLevel().getLevel()[y][x].getGround() == ApoBotConstants.GROUND_REAL_EMPTY) {
			return false;
		}
		if ((height + 1 >= this.getLevel().getLevel()[y][x].getHeight()) && (height != this.getLevel().getLevel()[y][x].getHeight())) {
			this.getPlayer().setJumpHeight((this.getLevel().getLevel()[y][x].getHeight() - height) * 8);
			return true;
		} else {
			return false;
		}
	}
}

