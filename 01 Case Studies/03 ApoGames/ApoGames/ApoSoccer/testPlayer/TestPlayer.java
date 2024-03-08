import java.util.ArrayList;

import apoSoccer.ApoSoccerConstants;
import apoSoccer.entityForAI.ApoSoccerAI;
import apoSoccer.entityForAI.ApoSoccerBall;
import apoSoccer.entityForAI.ApoSoccerEnemy;
import apoSoccer.entityForAI.ApoSoccerTeam;

public class TestPlayer extends ApoSoccerAI {

	/** Speecharray, was nicht zwingend erstellt werden muss, sondern nur die Strings beinhaltet die gesagt werden */
	private final String[] SPEECH = new String[] {"This is a test", "Tester die Testen"};
	
	@Override
	public String getAuthor() {
		return "Test";
	}

	@Override
	public String getTeamName() {
		return "Test-Team";
	}

	@Override
	public void think(ApoSoccerBall ball, ArrayList<ApoSoccerTeam> ownTeam, ArrayList<ApoSoccerEnemy> enemyTeam) {
		ApoSoccerTeam defender = ownTeam.get(ApoSoccerConstants.DEFENDER_ONE);
		int angleToBall = defender.getAngleToPoint(ball.getX(), ball.getY());
		if (defender.getLineOfSight() != angleToBall) {
			// verändere deine Blickrichtung
			// positiver Wert, dann dreht sich der Spieler nach rechts
			// negativer Wert, Spieler dreht sich nach links
			// also z.B. setLineOfSight(5) verändert die derzeitige Blickrichtung um 5°
			defender.setLineOfSight(angleToBall - defender.getLineOfSight());
		}
		if (defender.canShoot()) {
			// falls man schiessen kann, dann schiesse in Blickrichtung
			defender.setShoot(defender.getLineOfSight(), ApoSoccerConstants.MAX_SPEED_SHOOT);
		}
		defender.setSpeed(ApoSoccerConstants.MAX_SPEED_PLAYER);
		// sprich, wenn du Sprechen kannst
		if (defender.canSpeak()) {
			defender.speakText((int)(Math.random()*(this.SPEECH.length - 1)));
		}
	}
	
	/**
	 * gibt das SpeechArray zurürck, was die Spieler sagen können
	 * Wenn die Methode nicht überschrieben wird, dann sagt nie einer was =)
	 */
	public String[] getSpeech() {
		return this.SPEECH;
	}

}
