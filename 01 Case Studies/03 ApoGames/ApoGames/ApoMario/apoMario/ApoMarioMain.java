package apoMario;

import org.apogames.ApoMainBufferedStrategy;

import apoMario.game.ApoMarioPanel;

/**
 * Startklasse die das Frame und das Spielobjekt erstellt, startet und anzeigt
 * @author Dirk Aporius
 *
 */
public class ApoMarioMain extends ApoMainBufferedStrategy {

	private static final long serialVersionUID = 4011552102902980247L;
	/** Spielobjekt */
	private ApoMarioComponent component;
	
	public ApoMarioMain(String splashUrl) {
		super(splashUrl);
	}

	@Override
	public void init() {
		super.setTitle( "=== Apo(Fat)Mario === (Version: "+ApoMarioConstants.VERSION+")" );
		
		this.component = new ApoMarioPanel();
		
		super.setComponent(this.component);

		super.setIconImage(new ApoMarioImage().getLogo(ApoMarioImageContainer.PLAYER[ApoMarioConstants.PLAYER_TYPE_BIG].getSubimage(0, 0, 2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE, 2 * ApoMarioConstants.TILE_SIZE * ApoMarioConstants.APP_SIZE)));
	}
	
	public void windowClosingEvent() {
		ApoMarioConstants.saveProperties();
	}

	public static void main(String[] args) {
		new ApoMarioMain("images/logo.png");
	}

}
