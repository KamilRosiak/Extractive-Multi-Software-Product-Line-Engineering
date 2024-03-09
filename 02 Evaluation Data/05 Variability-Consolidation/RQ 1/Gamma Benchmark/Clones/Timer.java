
public class Timer {
private void setStartTime(int plus) {
		if (this.curChooseEntity == null) {
			return;
		}
		if (this.curChooseEntity instanceof ApoMarioWall) {
			ApoMarioWall wall = (ApoMarioWall)(this.curChooseEntity);
			if (wall.isBTube()) {
				Point p = wall.getTubePoint();
				if (p == null) {
					p = new Point(0, 0);
				}
				int x = (int)(wall.getX() / ApoMarioConstants.TILE_SIZE) - p.x;
				int y = (int)(wall.getY() / ApoMarioConstants.TILE_SIZE) - p.y;
				try {
					ApoMarioFlower flower = (ApoMarioFlower)(isThereAnEnemy(x, y, true));
					if (flower != null) {
						flower.setStartTime(flower.getStartTime() + 10 * plus);
						if (flower.getStartTime() < 1500) {
							flower.setStartTime(1500);
						} else if (flower.getStartTime() > 7000) {
							flower.setStartTime(7000);
						}
					}
				} catch (Exception ex) {
				}
			} else if (wall.isBCanon()) {
				ApoMarioCannon canon = (ApoMarioCannon)(this.curChooseEntity);
				canon.setStartTime(canon.getStartTime() + 10 * plus);
				if (canon.getStartTime() < 1500) {
					canon.setStartTime(1500);
				} else if (canon.getStartTime() > 7000) {
					canon.setStartTime(7000);
				}
			}
		}
	}
	
	private void setShootTime(int plus) {
		if (this.curChooseEntity == null) {
			return;
		}
		if (this.curChooseEntity instanceof ApoMarioWall) {
			ApoMarioWall wall = (ApoMarioWall)(this.curChooseEntity);
			if (wall.isBTube()) {
				Point p = wall.getTubePoint();
				if (p == null) {
					p = new Point(0, 0);
				}
				int x = (int)(wall.getX() / ApoMarioConstants.TILE_SIZE) - p.x;
				int y = (int)(wall.getY() / ApoMarioConstants.TILE_SIZE) - p.y;
				try {
					ApoMarioFlower flower = (ApoMarioFlower)(isThereAnEnemy(x, y, true));
					if (flower != null) {
						flower.setTimeToShow(flower.getTimeToShow() + 10 * plus);
						if (flower.getTimeToShow() < 1500) {
							flower.setTimeToShow(1500);
						} else if (flower.getTimeToShow() > 7000) {
							flower.setTimeToShow(7000);
						}
					}
				} catch (Exception ex) {
				}
			} else if (wall.isBCanon()) {
				ApoMarioCannon canon = (ApoMarioCannon)(this.curChooseEntity);
				canon.setShootTime(canon.getShootTime() + 10 * plus);
				if (canon.getShootTime() < 1500) {
					canon.setShootTime(1500);
				} else if (canon.getShootTime() > 8000) {
					canon.setShootTime(8000);
				}
			}
		}
	}	
}

