package apoSlitherLink;

import org.apogames.ApoMainBufferedStrategy;

import apoSlitherLink.game.ApoSlitherLinkPanel;

public class ApoSlitherLinkMain extends ApoMainBufferedStrategy {

	private static final long serialVersionUID = 4011552102902980247L;
	/** Spielobjekt */
	private ApoSlitherLinkPanel component;
	
	public ApoSlitherLinkMain(String splashUrl) {
		super(splashUrl);
	}

	@Override
	public void init() {
		super.setTitle(ApoSlitherLinkConstants.TITLE);
		
		this.component = new ApoSlitherLinkPanel();
		super.setComponent(this.component);
		
		super.setIconImage(this.component.getImages().getLogo());
	}

	public static void main(String[] args) {
		new ApoSlitherLinkMain("images/logo.png");
	}

	@Override
	public void exitFrame() {
		this.component.saveLevels();
	}

}