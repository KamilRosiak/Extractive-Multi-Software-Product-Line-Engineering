package test;

import apoMario.ai.ApoMarioAI;
import apoMario.ai.ApoMarioAIEnemy;
import apoMario.ai.ApoMarioAILevel;
import apoMario.ai.ApoMarioAIPlayer;


public class Test extends ApoMarioAI {
	
	@Override
	public String getAuthor() {
		return "Dirk Aporius";
	}

	@Override
	public String getTeamName() {
		return "TestRunFast";
	}

	@Override
	public void think(ApoMarioAIPlayer player, ApoMarioAILevel level) {
		player.runRight();
		player.runFast();
		if (player.canJump()) {
			player.jump();
		}
		for (ApoMarioAIEnemy enemy : level.getEnemies()) {
			if (enemy.getX() + enemy.getWidth() < 0) {
			}
		}
	}

}
