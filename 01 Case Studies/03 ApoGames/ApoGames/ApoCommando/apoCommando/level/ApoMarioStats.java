package apoCommando.level;

public class ApoMarioStats {

	private String curString;
	
	private int count;
	
	public ApoMarioStats(String curString) {
		this.curString = curString;
		this.count = 1;
	}

	public int getCount() {
		return count;
	}
	
	public void addCount() {
		this.count += 1;
	}

	public String getCurString() {
		return this.curString;
	}
	
	public String toString() {
		return "typed " + this.getCurString() + " "+this.getCount()+" times";
	}
}
