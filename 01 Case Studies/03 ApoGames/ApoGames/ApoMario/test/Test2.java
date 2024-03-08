package test;

import apoMario.ai.ApoMarioAI;
import apoMario.ai.ApoMarioAILevel;
import apoMario.ai.ApoMarioAIPlayer;

public class Test2 extends ApoMarioAI {

	@Override
	public String getAuthor() {
		return "Dirk Aporius";
	}

	@Override
	public String getTeamName() {
		return "TestRunSlow";
	}

	@Override
	public void think(ApoMarioAIPlayer player, ApoMarioAILevel level) {
		player.runRight();
		player.runNormal();
		if (player.canJump()) {
			player.jump();
		}
	}

}
