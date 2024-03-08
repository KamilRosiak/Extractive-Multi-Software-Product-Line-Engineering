package apoStarz;

import org.apogames.ApoApplet;

import apoStarz.editor.ApoStarzEditor;
import apoStarz.game.ApoStarzGame;

public class ApoStarzApplet extends ApoApplet {

	private static final long serialVersionUID = 1L;

	private ApoStarzGame game;
	
	private ApoStarzEditor editor;
	
	public ApoStarzApplet() {
		super(ApoStarzConstants.GAME_WIDTH, ApoStarzConstants.GAME_HEIGHT);
	}
	
	public ApoStarzApplet(int width, int height) {
		super(width, height);
	}
	
	public void init() {
		super.init();
		
		this.game = new ApoStarzGame(this);
		this.game.setSize(ApoStarzConstants.GAME_WIDTH, ApoStarzConstants.GAME_HEIGHT);

		this.editor = new ApoStarzEditor(this);
		this.editor.setSize(ApoStarzConstants.GAME_WIDTH, ApoStarzConstants.GAME_HEIGHT);
		
		this.setGame(null);
	}

	public void setEditor(String levelString) {
		super.setComponent(this.editor);
		if (levelString != null) {
			this.editor.setLevelString(levelString);
		}
	}
	
	public void setGame(String levelString) {
		super.setComponent(this.game);
		if (levelString != null) {
			this.game.setNormalMode();
			this.game.setLevelString(levelString);
		}
	}
	
}
