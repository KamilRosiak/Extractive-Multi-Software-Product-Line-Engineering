package apoPongBeat.game;

public class ApoPongBeatSoundHelp {

	private String note;
	private int time;
	
	private boolean bPlay;
	
	public ApoPongBeatSoundHelp(String note, int time) {
		this.note = note;
		this.time = time;
		this.init();
	}
	
	public void init() {
		this.bPlay = false;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getTime() {
		return this.time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public boolean isBPlay() {
		return this.bPlay;
	}

	public void setBPlay(boolean play) {
		this.bPlay = play;
	}
	
}
