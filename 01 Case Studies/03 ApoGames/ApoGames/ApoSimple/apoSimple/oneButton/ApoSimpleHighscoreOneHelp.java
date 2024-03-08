package apoSimple.oneButton;

public class ApoSimpleHighscoreOneHelp {

	private final String name;
	
	private final int death;
	
	public ApoSimpleHighscoreOneHelp(final String name, final int death) {
		this.name = name;
		this.death = death;
	}

	public final String getName() {
		return this.name;
	}

	public final int getDeath() {
		return this.death;
	}
	
}
