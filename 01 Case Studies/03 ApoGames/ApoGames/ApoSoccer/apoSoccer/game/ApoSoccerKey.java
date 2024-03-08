package apoSoccer.game;

import java.awt.event.KeyEvent;

import apoSoccer.ApoSoccerConstants;
import apoSoccer.entity.ApoPlayer;
import apoSoccer.entity.ApoTeam;

/**
 * Klasse die die Tastatureingaben auswertet, falls es einen menschlichen Spieler gibt
 * @author Dirk Aporius
 *
 */
public class ApoSoccerKey {

	private final int SPEED = 50;
	private final int SPEED_TURBO = 100;
	
	private final int SHOOT_PASS = 50;
	private final int SHOOT_MAX = 150;
	
	private ApoSoccerGame game;
	
	public ApoSoccerKey(ApoSoccerGame game) {
		this.game = game;
	}
	
	public void checkKeys(ApoTeam team, int curPlayer) {
		if (curPlayer < 0) {
			int angle = -1;//player.getLineOfSight();
			//int speed = (int)player.getSpeed();
			boolean[] keys = this.game.getKeys();
			if ((keys[KeyEvent.VK_RIGHT]) && (keys[KeyEvent.VK_UP])) {
				angle = 315;
			} else if ((keys[KeyEvent.VK_RIGHT]) && (keys[KeyEvent.VK_DOWN])) {
				angle = 45;
			} else if ((keys[KeyEvent.VK_LEFT]) && (keys[KeyEvent.VK_UP])) {
				angle = 225;
			} else if ((keys[KeyEvent.VK_LEFT]) && (keys[KeyEvent.VK_DOWN])) {
				angle = 135;
			} else if ((keys[KeyEvent.VK_LEFT])) {
				//losAngle = losAngle - ApoSoccerConstants.MAX_CHANGE_LINEOFSIGHT - 5;
				angle = 180;
			} else if ((keys[KeyEvent.VK_RIGHT])) {
				//losAngle = losAngle + ApoSoccerConstants.MAX_CHANGE_LINEOFSIGHT - 5;
				angle = 0;
			} else if ((keys[KeyEvent.VK_UP])) {
				//speed = 100;
				angle = 270;
			} else if ((keys[KeyEvent.VK_DOWN])) {
				//speed = 0;
				angle = 90;
			}
			if (angle != -1) {
				this.game.getBall().setLineOfSight(angle);
				this.game.getBall().setSpeed(50);
			}
			if ((keys[KeyEvent.VK_S])) {
				this.game.getBall().setSpeed(0);
			}
			return;
		}
		ApoPlayer player = team.getPlayer(curPlayer);
		if (this.game.getBPlayerHuman()[ApoSoccerConstants.ONE_HUMAN_PLAYER]) {
			//int losAngle = player.getLineOfSight();
			int angle = -1;//player.getLineOfSight();
			//int speed = (int)player.getSpeed();
			boolean[] keys = this.game.getKeys();
			if ((keys[KeyEvent.VK_RIGHT]) && (keys[KeyEvent.VK_UP])) {
				angle = 315;
			} else if ((keys[KeyEvent.VK_RIGHT]) && (keys[KeyEvent.VK_DOWN])) {
				angle = 45;
			} else if ((keys[KeyEvent.VK_LEFT]) && (keys[KeyEvent.VK_UP])) {
				angle = 225;
			} else if ((keys[KeyEvent.VK_LEFT]) && (keys[KeyEvent.VK_DOWN])) {
				angle = 135;
			} else if ((keys[KeyEvent.VK_LEFT])) {
				//losAngle = losAngle - ApoSoccerConstants.MAX_CHANGE_LINEOFSIGHT - 5;
				angle = 180;
			} else if ((keys[KeyEvent.VK_RIGHT])) {
				//losAngle = losAngle + ApoSoccerConstants.MAX_CHANGE_LINEOFSIGHT - 5;
				angle = 0;
			} else if ((keys[KeyEvent.VK_UP])) {
				//speed = 100;
				angle = 270;
			} else if ((keys[KeyEvent.VK_DOWN])) {
				//speed = 0;
				angle = 90;
			}
			int myAngle = angle;
			if (myAngle == -1) {
				myAngle = player.getLineOfSight();
			} else {
				int los = player.getLineOfSight();
				if ((los < myAngle)) {
					if (myAngle - los > ApoSoccerConstants.ANGLE_TO_SHOOT){
						if (myAngle - los > 180) {
							myAngle = myAngle - ApoSoccerConstants.ANGLE_TO_SHOOT;
						} else {
							myAngle = los + ApoSoccerConstants.ANGLE_TO_SHOOT;
						}
					}
				} else if (los > myAngle) {
					if (los - myAngle > ApoSoccerConstants.ANGLE_TO_SHOOT){
						if (los - myAngle > 180) {
							myAngle = myAngle - ApoSoccerConstants.ANGLE_TO_SHOOT;
						} else {
							myAngle = los + ApoSoccerConstants.ANGLE_TO_SHOOT;
						}
					}
				}
			}
			
			if (keys[KeyEvent.VK_S]) {
				player.setShoot(myAngle, this.SHOOT_PASS);
				//angle = -1;
			} else if (keys[KeyEvent.VK_D]) {
				player.setShoot(myAngle, this.SHOOT_MAX);
				//angle = -1;
			}
			
			if (game.getReleasedKeys().size() >= 0) {
				for (int i = this.game.getReleasedKeys().size() - 1; i >= 0; i--) {
					int code = game.getReleasedKeys().get(i);
					if (code == KeyEvent.VK_A) {
						ApoPlayer nextPlayer = this.getNextPlayerToBall(team, player);
						if (nextPlayer != null) {
							player.setBPlayerHuman(false);
							nextPlayer.setBPlayerHuman(true);
						}
						angle = -1;
					}
				}
				this.game.getReleasedKeys().clear();
			}
			//player.setSpeed(speed);
			//player.setNextLineOfSightChange(losAngle);
			if (angle != -1) {
				player.setNextLineOfSightChange(angle);
				int speed = this.SPEED;
				if ((keys[KeyEvent.VK_W])) {
					speed = this.SPEED_TURBO;
				}
				player.setSpeed((int)(speed));
			} else {
				player.setSpeed(0);
			}
		}
	}
	
	private ApoPlayer getNextPlayerToBall(ApoTeam team, ApoPlayer player) {
		ApoPlayer nextPlayer = null;
		int distance = -1;
		for (int i = 0; i < team.getPlayers().size(); i++) {
			if (!team.getPlayer(i).equals(player)) {
				int nextDistance = this.getDistanceToBall(team.getPlayer(i));
				if ((distance == -1) || (nextDistance < distance)) {
					distance = nextDistance;
					nextPlayer = team.getPlayer(i);
				}
			}
		}
		return nextPlayer;
	}
	
	private int getDistanceToBall(ApoPlayer player) {
		int distance = 0;
		distance = (int)(((game.getBall().getX() - player.getX()) * (game.getBall().getX() - player.getX())) + 
						 ((game.getBall().getY() - player.getY()) * (game.getBall().getY() - player.getY())));
		return distance;
	}
}
