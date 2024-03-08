package apoSimple.oneButton;

import java.util.ArrayList;

public class ApoSimpleHighscoreOneGet {

	private ApoSimpleHighscoreOne highscore;
	
	private ArrayList<ApoSimpleHighscoreOneHelp> help;
	
	public ApoSimpleHighscoreOneGet() {
		this.help = new ArrayList<ApoSimpleHighscoreOneHelp>();
	}
	
	public void init() {
		this.loadHighscore();
	}
	
	public ArrayList<ApoSimpleHighscoreOneHelp> getHelp() {
		return this.help;
	}

	public void loadHighscore() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				ApoSimpleHighscoreOneGet.this.load();
			}
 		});
 		t.start();
	}
	
	private void load() {
		try {
			this.highscore = ApoSimpleHighscoreOne.getInstance();
			if (this.highscore.load()) {
				this.sortList();
			}
		} catch (Exception ex) {
			if (this.help == null) {
				this.help = new ArrayList<ApoSimpleHighscoreOneHelp>();
			}
			this.help.clear();
			this.highscore = null;
			System.out.println("Fehler: "+ex.getMessage());
		}
	}
	
	private void sortList() {
		if (this.help == null) {
			this.help = new ArrayList<ApoSimpleHighscoreOneHelp>();
		} else {
			this.help.clear();
		}
		this.help.add(new ApoSimpleHighscoreOneHelp(this.highscore.getNames().get(0), this.highscore.getDeaths().get(0)));
		for (int i = 1; i < this.highscore.getDeaths().size(); i++) {
			boolean bBreak = false;
			for (int j = 0; j < this.help.size(); j++) {
				if (this.help.get(j).getDeath() > this.highscore.getDeaths().get(i)) {
					bBreak = true;
					this.help.add(j, new ApoSimpleHighscoreOneHelp(this.highscore.getNames().get(i), this.highscore.getDeaths().get(i)));
					break;
				}
			}
			if (!bBreak) {
				this.help.add(new ApoSimpleHighscoreOneHelp(this.highscore.getNames().get(i), this.highscore.getDeaths().get(i)));				
			}
		}
	}
	
	public int getPlace(int death, String name) {
		for (int i = 0; i < this.help.size(); i++) {
			if (this.help.get(i).getDeath() > death) {
				this.help.add(i, new ApoSimpleHighscoreOneHelp(name, death));
				return i;
			}
		}
		this.help.add(new ApoSimpleHighscoreOneHelp(name, death));
		return (this.help.size() - 1);
	}
	
}
