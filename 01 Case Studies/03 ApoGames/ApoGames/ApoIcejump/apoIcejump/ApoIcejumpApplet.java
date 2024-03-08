package apoIcejump;

import org.apogames.ApoAppletBufferedStrategy;

import apoIcejump.game.ApoIcejumpPanel;

/**
* Startklasse für das Applet
* @author Dirk Aporius
*
*/
public class ApoIcejumpApplet extends ApoAppletBufferedStrategy {

	private static final long serialVersionUID = 4094382521849709508L;

	private ApoIcejumpComponent component;
	
	public ApoIcejumpApplet() {
		super(ApoIcejumpConstants.GAME_WIDTH, ApoIcejumpConstants.GAME_HEIGHT);
	}
	
	public ApoIcejumpApplet(int width, int height) {
		super(width, height);
	}
	
	public void stop() {
		this.component.stopSound();
	}
	
	public void destroy() {
		this.component.stopSound();
		super.destroy();
	}
	
	public void init() {
		super.init();
		
		this.component = new ApoIcejumpPanel();
		//this.component.setSize(ApoAbstractConstants.GAME_WIDTH, ApoAbstractConstants.GAME_HEIGHT );
		
		super.setComponent(this.component);
	}

}
