package apoMario.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import apoMario.ApoMarioConstants;
import apoMario.level.ApoMarioLevel;

/**
 * Klasse, die sich um das Abspielen eines Replays mit kümmert und das Replayzeichen links oben anzeigt
 * @author Dirk Aporius
 *
 */
public class ApoMarioReplayPlay {

	public static final int CHANGE_TIME = 500;
	
	public static final Font FONT_REPLAY = new Font(Font.SANS_SERIF, Font.BOLD, 10 + 10 * ApoMarioConstants.SIZE);
	
	private ApoMarioLevel level;
	
	private int step;
	
	private boolean bShow;
	
	private int curTime;
	
	public ApoMarioReplayPlay(ApoMarioLevel level) {
		this.level = level;
	}
	
	public void initReplay() {
		if (this.level != null) {
			if (this.level.getReplay() != null) {
				ApoMarioReplay replay = this.level.getReplay();
				this.level.getPlayers().get(0).setTeamName(replay.getPlayerOne().getName());
				this.level.getPlayers().get(0).setAuthor(replay.getPlayerOne().getAuthor());
				replay.getPlayerOne().setReplay(replay.getPlayerOne().getReplay());
				if (replay.getPlayerTwo() != null) {
					this.level.getPlayers().get(1).setTeamName(replay.getPlayerTwo().getName());
					this.level.getPlayers().get(1).setAuthor(replay.getPlayerTwo().getAuthor());
					this.level.getPlayers().get(1).setBVisible(true);
					replay.getPlayerTwo().setReplay(replay.getPlayerTwo().getReplay());
				} else {
					this.level.getPlayers().get(1).setBVisible(false);
				}
				this.step = 0;
				this.curTime = 0;
				this.bShow = true;
				if (replay.getLevelString() == null) {
					this.level.makeLevel(replay.getRandom(), false, true, replay.getWidth(), replay.getDifficulty());
				} else {
					this.level.makeLevel(replay.getRandom(), true, false, replay.getWidth(), replay.getDifficulty());
				}
			}
		}
	}
	
	public boolean onlyOnePlayer() {
		if (this.level.getReplay().getPlayerTwo() == null) {
			return true;
		}
		return false;
	}
	
	public int nextStep() {
		if (this.level != null) {
			ApoMarioReplay replay = this.level.getReplay();
			int step = replay.setStep(this.level.getPlayers().get(0), this.level.getPlayers().get(1), this.step);
			this.step += 1;
			return step;
		}
		return -1;
	}
	
	public void think(int delta) {
		if (this.level.isBReplay()) {
			this.curTime += delta;
			if (this.curTime > ApoMarioReplayPlay.CHANGE_TIME) {
				this.curTime -= ApoMarioReplayPlay.CHANGE_TIME;
				this.bShow = !this.bShow;
			}
		}
	}
	
	public void render(Graphics2D g) {
		if ((this.level.isBReplay()) && (this.bShow)) {
			String s = "REPLAY";
			g.setFont(ApoMarioReplayPlay.FONT_REPLAY);
			int size = ApoMarioConstants.SIZE * ApoMarioConstants.APP_SIZE;
			int hei = 60 + 10 * size;
			g.setColor(Color.WHITE);
			g.drawString(s, 5 + 1, hei + 20 * size + 1);
			g.setColor(Color.BLACK);
			g.drawString(s, 5, hei + 20 * size);
		}
	}
	
}
