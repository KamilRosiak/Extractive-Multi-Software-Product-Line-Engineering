package apoIcejump;

import org.apogames.ApoMainBufferedStrategy;

import apoIcejump.game.ApoIcejumpPanel;

public class ApoIcejumpMain extends ApoMainBufferedStrategy {

	private static final long serialVersionUID = 4011552102902980247L;
	/** Spielobjekt */
	private ApoIcejumpComponent component;
	
	public ApoIcejumpMain(String splashUrl) {
		super(splashUrl);
	}

	@Override
	public void init() {
		super.setTitle(ApoIcejumpConstants.GAME_NAME);
		
		this.component = new ApoIcejumpPanel();
		
		super.setComponent(this.component);
		
		super.setIconImage(this.component.getImages().getLogo(30, 30));
	}
	
	public void windowClosingEvent() {
		ApoIcejumpConstants.FPS = component.isBFPS();
		ApoIcejumpConstants.saveProperties();
	}

	public static void main(String[] args) {
		new ApoIcejumpMain("images/logo.png");
	}

}
