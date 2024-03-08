package apoMario.game;

import java.util.ArrayList;

/**
 * Hilfsklasse für das Replay, was alle relevanten Sachen für eine KI und ein Spiel speichert
 * @author Dirk Aporius
 *
 */
public class ApoMarioReplayHelp {

	private String name;
	
	private String author;
	
	private String replay;
	
	private ArrayList<Integer> replayStep;
	
	public ApoMarioReplayHelp() {
		this("", "");
	}
	
	public ApoMarioReplayHelp(String name, String author) {
		this.name = name;
		this.author = author;
		this.replay = "";
		this.replayStep = new ArrayList<Integer>();
	}
	
	public synchronized ApoMarioReplayHelp getClone() {
		ApoMarioReplayHelp help = new ApoMarioReplayHelp(this.name, this.author);
		help.setReplay(this.replay);
		return help;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getReplay() {
		return this.replay;
	}
	
	public void setReplay(String replay) {
		this.replay = replay;
		this.makeReplaySteps();
	}
	
	private synchronized void makeReplaySteps() {
		this.replayStep.clear();
		if (this.replay.length() <= 1) {
			return;
		}
		String rep = this.replay;
		while (rep.indexOf(",") != -1) {
			String s = rep.substring(0, rep.indexOf(","));
			rep = rep.substring(rep.indexOf(",") + 1, rep.length());
			try {
				int value = Integer.valueOf(s);
				this.replayStep.add(value);
			} catch (NumberFormatException ex) {
			}
		}
	}
	
	public int getMaxSteps() {
		return this.replayStep.size() - 1;
	}
	
	public ArrayList<Integer> getReplayStep() {
		return this.replayStep;
	}

	public int getReplayStep(int step) {
		if ((step < 0) || (this.replayStep.size() <= step)) {
			return -1;
		} else {
			return this.replayStep.get(step);
		}
	}

	public void addNextStep(int step) {
		this.replay += String.valueOf(step)+",";
	}
}
