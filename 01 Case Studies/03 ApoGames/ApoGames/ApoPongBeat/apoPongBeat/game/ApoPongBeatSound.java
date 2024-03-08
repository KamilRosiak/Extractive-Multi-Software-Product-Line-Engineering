package apoPongBeat.game;

import java.util.ArrayList;

public class ApoPongBeatSound {

	private ApoPongBeatPanel panel;
	
	private ArrayList<ApoPongBeatSoundHelp> christmasOhDuFroehliche;
	
	private ArrayList<ApoPongBeatSoundHelp> christmasJingleBells;

	private ArrayList<ApoPongBeatSoundHelp> christmasLeiseRieseltDerSchnee;
	
	private int time;
	
	private int oldMulti;
	
	public ApoPongBeatSound(ApoPongBeatPanel panel) {
		this.panel = panel;
		
		this.time = 0;
		
		this.createOhDuFroehliche();
		this.createJingleBells();
		this.createLeiseRieseltDerSchnee();
	}
	
	private void createOhDuFroehliche() {
		this.christmasOhDuFroehliche = new ArrayList<ApoPongBeatSoundHelp>();
		int timeNote = 1400;
		this.christmasOhDuFroehliche.add(new ApoPongBeatSoundHelp("d", 0));
		this.christmasOhDuFroehliche.add(new ApoPongBeatSoundHelp("e", timeNote/2));
		this.christmasOhDuFroehliche.add(new ApoPongBeatSoundHelp("d", timeNote));
		this.christmasOhDuFroehliche.add(new ApoPongBeatSoundHelp("c", (int)(1.375 * timeNote)));
		this.christmasOhDuFroehliche.add(new ApoPongBeatSoundHelp("h", (int)(1.5 * timeNote)));
		this.christmasOhDuFroehliche.add(new ApoPongBeatSoundHelp("c", (int)(1.75 * timeNote)));
		this.christmasOhDuFroehliche.add(new ApoPongBeatSoundHelp("d", (int)(2 * timeNote)));
		this.christmasOhDuFroehliche.add(new ApoPongBeatSoundHelp("e", (int)(2.5 * timeNote)));
		this.christmasOhDuFroehliche.add(new ApoPongBeatSoundHelp("d", (int)(3 * timeNote)));
		this.christmasOhDuFroehliche.add(new ApoPongBeatSoundHelp("c", (int)(3.375 * timeNote)));
		this.christmasOhDuFroehliche.add(new ApoPongBeatSoundHelp("h", (int)(3.5 * timeNote)));
		this.christmasOhDuFroehliche.add(new ApoPongBeatSoundHelp("c", (int)(3.75 * timeNote)));
		this.christmasOhDuFroehliche.add(new ApoPongBeatSoundHelp("d", (int)(4 * timeNote)));
		this.christmasOhDuFroehliche.add(new ApoPongBeatSoundHelp("d", (int)(4.5 * timeNote)));
		this.christmasOhDuFroehliche.add(new ApoPongBeatSoundHelp("e", (int)(5 * timeNote)));
		this.christmasOhDuFroehliche.add(new ApoPongBeatSoundHelp("f#", (int)(5.5 * timeNote)));
		this.christmasOhDuFroehliche.add(new ApoPongBeatSoundHelp("g", (int)(5.75 * timeNote)));
		this.christmasOhDuFroehliche.add(new ApoPongBeatSoundHelp("f#", (int)(6 * timeNote)));
		this.christmasOhDuFroehliche.add(new ApoPongBeatSoundHelp("e", (int)(6.5 * timeNote)));
		this.christmasOhDuFroehliche.add(new ApoPongBeatSoundHelp("d", (int)(7 * timeNote)));
	}
	
	private void createJingleBells() {
		this.christmasJingleBells = new ArrayList<ApoPongBeatSoundHelp>();
		int timeNote = 1400;
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("e", 0));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("e", (int)(0.25 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("e", (int)(0.5 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("e", (int)(1 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("e", (int)(1.25 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("e", (int)(1.5 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("e", (int)(2 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("g", (int)(2.25 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("c", (int)(2.5 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("d", (int)(2.75 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("e", (int)(3 * timeNote)));
		
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("f", (int)(4 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("f", (int)(4.25 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("f", (int)(4.5 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("f", (int)(4.75 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("f", (int)(5 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("e", (int)(5.25 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("e", (int)(5.5 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("e", (int)(6 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("d", (int)(6.25 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("d", (int)(6.5 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("e", (int)(6.75 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("d", (int)(7 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("g", (int)(7.5 * timeNote)));
		
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("e", (int)(8 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("e", (int)(8.25 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("e", (int)(8.5 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("e", (int)(9 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("e", (int)(9.25 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("e", (int)(9.5 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("e", (int)(10 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("g", (int)(10.25 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("c", (int)(10.5 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("d", (int)(10.75 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("e", (int)(11 * timeNote)));
		
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("f", (int)(12 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("f", (int)(12.25 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("f", (int)(12.5 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("f", (int)(12.75 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("f", (int)(13 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("e", (int)(13.25 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("e", (int)(13.5 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("g", (int)(14 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("g", (int)(14.25 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("f", (int)(14.5 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("d", (int)(14.75 * timeNote)));
		this.christmasJingleBells.add(new ApoPongBeatSoundHelp("c", (int)(15 * timeNote)));
	}
	
	private void createLeiseRieseltDerSchnee() {
		this.christmasLeiseRieseltDerSchnee = new ArrayList<ApoPongBeatSoundHelp>();
		int timeNote = 1400;
		this.christmasLeiseRieseltDerSchnee.add(new ApoPongBeatSoundHelp("g", 0));
		this.christmasLeiseRieseltDerSchnee.add(new ApoPongBeatSoundHelp("g", (int)(0.66 * timeNote)));
		this.christmasLeiseRieseltDerSchnee.add(new ApoPongBeatSoundHelp("f", (int)(1.00 * timeNote)));
		this.christmasLeiseRieseltDerSchnee.add(new ApoPongBeatSoundHelp("g", (int)(1.33 * timeNote)));
		this.christmasLeiseRieseltDerSchnee.add(new ApoPongBeatSoundHelp("f", (int)(1.66 * timeNote)));
		this.christmasLeiseRieseltDerSchnee.add(new ApoPongBeatSoundHelp("d#", (int)(2 * timeNote)));
		this.christmasLeiseRieseltDerSchnee.add(new ApoPongBeatSoundHelp("d#", (int)(4 * timeNote)));
		this.christmasLeiseRieseltDerSchnee.add(new ApoPongBeatSoundHelp("c", (int)(4.66 * timeNote)));
		this.christmasLeiseRieseltDerSchnee.add(new ApoPongBeatSoundHelp("d#", (int)(5 * timeNote)));
		this.christmasLeiseRieseltDerSchnee.add(new ApoPongBeatSoundHelp("d", (int)(5.33 * timeNote)));
		this.christmasLeiseRieseltDerSchnee.add(new ApoPongBeatSoundHelp("c", (int)(5.66 * timeNote)));
		this.christmasLeiseRieseltDerSchnee.add(new ApoPongBeatSoundHelp("b", (int)(6 * timeNote)));
		
		this.christmasLeiseRieseltDerSchnee.add(new ApoPongBeatSoundHelp("f", (int)(8 * timeNote)));
		this.christmasLeiseRieseltDerSchnee.add(new ApoPongBeatSoundHelp("e", (int)(8.33 * timeNote)));
		this.christmasLeiseRieseltDerSchnee.add(new ApoPongBeatSoundHelp("f", (int)(8.66 * timeNote)));
		this.christmasLeiseRieseltDerSchnee.add(new ApoPongBeatSoundHelp("g#", (int)(9.00 * timeNote)));
		this.christmasLeiseRieseltDerSchnee.add(new ApoPongBeatSoundHelp("g", (int)(9.33 * timeNote)));
		this.christmasLeiseRieseltDerSchnee.add(new ApoPongBeatSoundHelp("f", (int)(9.66 * timeNote)));
		this.christmasLeiseRieseltDerSchnee.add(new ApoPongBeatSoundHelp("d#", (int)(10 * timeNote)));
		this.christmasLeiseRieseltDerSchnee.add(new ApoPongBeatSoundHelp("f", (int)(12 * timeNote)));
		this.christmasLeiseRieseltDerSchnee.add(new ApoPongBeatSoundHelp("c", (int)(12.5 * timeNote)));
		this.christmasLeiseRieseltDerSchnee.add(new ApoPongBeatSoundHelp("c", (int)(12.66 * timeNote)));
		this.christmasLeiseRieseltDerSchnee.add(new ApoPongBeatSoundHelp("d", (int)(13 * timeNote)));
		this.christmasLeiseRieseltDerSchnee.add(new ApoPongBeatSoundHelp("c", (int)(13.33 * timeNote)));
		this.christmasLeiseRieseltDerSchnee.add(new ApoPongBeatSoundHelp("d", (int)(13.66 * timeNote)));
		this.christmasLeiseRieseltDerSchnee.add(new ApoPongBeatSoundHelp("d#", (int)(14 * timeNote)));
	}
	
	public void init() {
		this.time = 0;
		
		for (int i = 0; i < this.christmasJingleBells.size(); i++) {
			this.christmasJingleBells.get(i).init();
		}
		for (int i = 0; i < this.christmasOhDuFroehliche.size(); i++) {
			this.christmasOhDuFroehliche.get(i).init();
		}
		for (int i = 0; i < this.christmasLeiseRieseltDerSchnee.size(); i++) {
			this.christmasLeiseRieseltDerSchnee.get(i).init();
		}
	}
	
	public boolean think(int delta, int song) {
		if (this.oldMulti != song) {
			this.init();
		}
		ArrayList<ApoPongBeatSoundHelp> list = this.christmasOhDuFroehliche;
		if (song == 2) {
			list = this.christmasJingleBells;
		} else if (song == 3) {
			list = this.christmasLeiseRieseltDerSchnee;
		}
		for (int i = 0; i < list.size(); i++) {
			if ((this.time >= list.get(i).getTime()) && (!list.get(i).isBPlay())) {
				panel.getSounds().playSound(list.get(i).getNote(), false);
				list.get(i).setBPlay(true);
			}
			if (i + 1 == list.size()) {
				if (list.get(i).isBPlay()) {
					return false;
				}
			}
		}
		this.time += delta;
		this.oldMulti = song;
		return true;
	}
	
}
