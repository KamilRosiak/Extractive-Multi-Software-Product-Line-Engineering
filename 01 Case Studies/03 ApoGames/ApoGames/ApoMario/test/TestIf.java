package test;

import apoMario.ApoMarioConstants;
import apoMario.ai.ApoMarioAI;
import apoMario.ai.ApoMarioAIEnemy;
import apoMario.ai.ApoMarioAILevel;
import apoMario.ai.ApoMarioAIPlayer;

public class TestIf extends ApoMarioAI {
	
	@Override
	public String getAuthor() {
		return "Dirk Aporius";
	}

	@Override
	public String getTeamName() {
		return "If-Team";
	}

	@Override
	public void think(ApoMarioAIPlayer player, ApoMarioAILevel level) {
		//System.out.println(player.getX()+" "+player.getY()+" "+player.getWidth()+" "+player.getHeight());
		player.runRight();
		player.runFast();
		//player.drawRect((player.getX()), (player.getY()), (player.getWidth()), (player.getHeight()), true, 2 * ApoMarioAIConstants.WAIT_TIME_THINK);
		float playerX = player.getX() + 5f * player.getWidth() / 7f;
		float playerY = player.getY();
		if (playerY < 0) {
			playerY = 0;
		}
		for (int i = 0; i < level.getEnemies().size(); i++) {
			ApoMarioAIEnemy enemy = level.getEnemies().get(i);
			//player.drawRect((enemy.getX()), (enemy.getY()), (enemy.getWidth()), (enemy.getHeight()), true, 2 * ApoMarioAIConstants.WAIT_TIME_THINK);
			float enemyX = (enemy.getX());
			float enemyY = (enemy.getY());
			if ((((int)enemyX - 1 == (int)playerX) || ((int)enemyX == (int)playerX)) && ((playerY - enemyY) >= -3) && (playerY - enemyY <= 1)) {
				if (!enemy.isFireballType()) {
					this.playerRunFastAndJump(player);
				}
				//player.stopGame();
				//return;
			}
		}
		if (((int)playerY + 1 < level.getLevelArray().length) && ((int)playerX + 1 < level.getLevelArray()[0].length)) {
			if ((int)playerY + 2 < level.getLevelArray().length) {
				if (level.getLevelArray()[(int)playerY + 2][(int)playerX + 1] == ApoMarioConstants.EMPTY) {
					this.playerRunFastAndJump(player);
					//return;
				}
			}
			if ((level.getLevelArray()[(int)playerY + 1][(int)playerX + 1] != ApoMarioConstants.EMPTY) &&
				(level.getLevelArray()[(int)playerY + 1][(int)playerX + 1] != ApoMarioConstants.ONLY_ABOVE_WALL) &&
				(level.getLevelArray()[(int)playerY + 1][(int)playerX + 1] != ApoMarioConstants.NO_COLLISION_WALL)) {
				this.playerRunFastAndJump(player);
				//return;
			}
		}
		if (player.getVecY() < 0) {
			if (player.getVecY() < this.JUMP_VALUE) {
				player.jump();
			}
		}
	}
	
	private void playerRunFastAndJump(ApoMarioAIPlayer player) {
		player.runFast();
		if (player.canJump()) {
			if (player.getVecY() < 0) {
				if (player.getVecY() < this.JUMP_VALUE) {
					player.jump();
				}
			} else if (player.getVecY() == 0) {
				player.jump();
			}
		}
		if ((player.getVecY() > 0) && (player.getVecY() < 0.004)) {
			player.duck();
		}
	}
	
	private final double JUMP_VALUE = 0;

}
