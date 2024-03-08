package apoSlitherLink;

import org.apogames.ApoAppletBufferedStrategy;

import apoSlitherLink.game.ApoSlitherLinkPanel;

/**
* Startklasse für das Applet
* @author Dirk Aporius
*
*/
public class ApoSlitherLinkApplet extends ApoAppletBufferedStrategy {

	private static final long serialVersionUID = 4094382521849709508L;

	private ApoSlitherLinkComponent component;
	
	public ApoSlitherLinkApplet() {
		super(ApoSlitherLinkConstants.GAME_WIDTH, ApoSlitherLinkConstants.GAME_HEIGHT);
	}
	
	public ApoSlitherLinkApplet(int width, int height) {
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
		
		this.component = new ApoSlitherLinkPanel();
		//this.component.setSize(ApoAbstractConstants.GAME_WIDTH, ApoAbstractConstants.GAME_HEIGHT );
		
		super.setComponent(this.component);
	}

}
