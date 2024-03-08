package apoMario;

import org.apogames.ApoAppletBufferedStrategy;
import org.apogames.ApoConstants;

import apoMario.game.ApoMarioPanel;

/**
 * Startklasse für das Applet
 * @author Dirk Aporius
 *
 */
public class ApoMarioApplet extends ApoAppletBufferedStrategy {

	static {
		ApoConstants.B_APPLET = true;
	}
	
	private static final long serialVersionUID = 4094382521849709508L;

	private ApoMarioComponent component;
	
	public ApoMarioApplet() {
		super(ApoMarioConstants.GAME_WIDTH, ApoMarioConstants.GAME_HEIGHT);
	}
	
	public ApoMarioApplet(int width, int height) {
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
		
		this.component = new ApoMarioPanel();

		super.setComponent(this.component);
	}

}
