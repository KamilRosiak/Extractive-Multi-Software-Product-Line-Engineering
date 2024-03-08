package apoSimple.game.level;

import java.util.ArrayList;

import apoSimple.ApoSimpleConstants;

public class ApoSimpleUserlevels {

	public static int SOLUTION = 0;
	public static int UPLOAD = 1;
	public static int JUDGMENT = 2;
	
	private ArrayList<Integer> sortBySolution;
	private ArrayList<Integer> sortByUpload;
	private ArrayList<Integer> sortByJudgment;
	
	private ApoSimpleUserlevelsLoad userlevels;
	
	private int curLevel;
	
	private int curMode;
	
	public ApoSimpleUserlevels() {
		this.sortBySolution = new ArrayList<Integer>();
		this.sortByUpload = new ArrayList<Integer>();
		this.sortByJudgment = new ArrayList<Integer>();
		
		this.curLevel = 0;
		this.curMode = ApoSimpleUserlevels.UPLOAD;
	}
	
	public void loadUserlevels() {
		this.userlevels = ApoSimpleUserlevelsLoad.getInstance();
		this.userlevels.load();
		this.curLevel = 0;
		
		if (this.userlevels.getLevels().size() > 0) {
			this.sortBySolution();
			this.sortByUpload();
			this.sortByJudgment();
		}
	}
	
	public void nextCurMode(int add) {
		this.curMode += add;
		if (this.curMode < ApoSimpleUserlevels.SOLUTION) {
			this.curMode = ApoSimpleUserlevels.JUDGMENT;
		} else if (this.curMode > ApoSimpleUserlevels.JUDGMENT) {
			this.curMode = ApoSimpleUserlevels.SOLUTION;
		}
	}
	
	public int getMaxLevel() {
		return this.userlevels.getLevels().size();
	}
	
	public ApoSimpleLevel getCurrentLevel() {
		if (this.userlevels.getLevels().size() <= 0) {
			return null;
		}
		if (this.curMode == ApoSimpleUserlevels.SOLUTION) {
			if (this.curLevel >= this.sortBySolution.size()) {
				this.curLevel = 0;
			} else if (this.curLevel < 0) {
				this.curLevel = this.sortBySolution.size() - 1;
			}
			return new ApoSimpleLevel(this.userlevels.getLevels().get(this.sortBySolution.get(this.curLevel)));
		} else if (this.curMode == ApoSimpleUserlevels.UPLOAD) {
			if (this.curLevel >= this.sortByUpload.size()) {
				this.curLevel = 0;
			} else if (this.curLevel < 0) {
				this.curLevel = this.sortByUpload.size() - 1;
			}
			return new ApoSimpleLevel(this.userlevels.getLevels().get(this.sortByUpload.get(this.curLevel)));
		} else if (this.curMode == ApoSimpleUserlevels.JUDGMENT) {
			if (this.curLevel >= this.sortByJudgment.size()) {
				this.curLevel = 0;
			} else if (this.curLevel < 0) {
				this.curLevel = this.sortByJudgment.size() - 1;
			}
			return new ApoSimpleLevel(this.userlevels.getLevels().get(this.sortByJudgment.get(this.curLevel)));
		}
		return null;
	}
	
	public final int getCurLevel() {
		return this.curLevel;
	}

	public void setCurLevel(final int curLevel) {
		this.curLevel = curLevel;
	}

	private void sortBySolution() {
		this.sortBySolution.clear();
		this.sortBySolution.add(0);
		for (int i = 1; i < this.userlevels.getLevels().size(); i++) {
			ApoSimpleLevel curLevel = new ApoSimpleLevel(this.userlevels.getLevels().get(i));
			boolean bAdd = false;
			for (int k = 0; k < this.sortBySolution.size(); k++) {
				ApoSimpleLevel checkLevel = new ApoSimpleLevel(this.userlevels.getLevels().get(this.sortBySolution.get(k)));
				if (curLevel.getStars()[2] <= checkLevel.getStars()[2]) {
					this.sortBySolution.add(k, i);
					bAdd = true;
					break;
				}
			}
			if (!bAdd) {
				this.sortBySolution.add(i);
			}
		}
	}
	
	private void sortByUpload() {
		this.sortByUpload.clear();
		this.sortByUpload.add(0);
		for (int i = 1; i < this.userlevels.getLevels().size(); i++) {
			float time = this.userlevels.getTimes().get(i);
			boolean bAdd = false;
			for (int k = 0; k < this.sortByUpload.size(); k++) {
				float sortTime = this.userlevels.getTimes().get(this.sortByUpload.get(k));
				if (time > sortTime) {
					this.sortByUpload.add(k, i);
					bAdd = true;
					break;
				}
			}
			if (!bAdd) {
				this.sortByUpload.add(i);
			}
		}
	}
	
	private void sortByJudgment() {
		this.sortByJudgment.clear();
		this.sortByJudgment.add(0);
		for (int i = 1; i < this.userlevels.getLevels().size(); i++) {
			float judgment = (float)this.userlevels.getAll().get(i) / (float)this.userlevels.getCount().get(i);
			boolean bAdd = false;
			for (int k = 0; k < this.sortByJudgment.size(); k++) {
				float sortJudgment = (float)this.userlevels.getAll().get(this.sortByJudgment.get(k)) / (float)this.userlevels.getCount().get(this.sortByJudgment.get(k));
				if (judgment < sortJudgment) {
					this.sortByJudgment.add(k, i);
					bAdd = true;
					break;
				}
			}
			if (!bAdd) {
				this.sortByJudgment.add(i);
			}
		}
	}

	public ArrayList<Integer> getSortBySolution() {
		return this.sortBySolution;
	}

	public ArrayList<Integer> getSortByUpload() {
		return this.sortByUpload;
	}

	public ArrayList<Integer> getSortByJudgment() {
		return this.sortByJudgment;
	}
	
	public boolean addLevel(String level) {
		for (int i = 0; i < this.userlevels.getLevels().size(); i++) {
			if (this.userlevels.getLevels().get(i).equals(level)) {
				return false;
			}
		}
		return ApoSimpleUserlevelsLoad.getInstance().save(level);
	}
	
	public String getSortedBy() {
		String s = "";
		if (ApoSimpleConstants.REGION.equals("de")) {
			if (this.curMode == ApoSimpleUserlevels.SOLUTION) {
				s = "Zügen";
			} else if (this.curMode == ApoSimpleUserlevels.UPLOAD) {
				s = "Datum";
			} else if (this.curMode == ApoSimpleUserlevels.JUDGMENT) {
				s = "Bewertungen";
			}
		} else {
			if (this.curMode == ApoSimpleUserlevels.SOLUTION) {
				s = "movecount";
			} else if (this.curMode == ApoSimpleUserlevels.UPLOAD) {
				s = "uploaddate";
			} else if (this.curMode == ApoSimpleUserlevels.JUDGMENT) {
				s = "judgment";
			}
		}
		return s;
	}
}
