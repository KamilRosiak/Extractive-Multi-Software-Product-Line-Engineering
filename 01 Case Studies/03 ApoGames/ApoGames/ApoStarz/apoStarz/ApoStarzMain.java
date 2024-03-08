package apoStarz;

import java.awt.Color;

import org.apogames.ApoMain;

import apoStarz.editor.ApoStarzEditor;
import apoStarz.game.ApoStarzGame;

public class ApoStarzMain extends ApoMain {

	private static final long serialVersionUID = 1L;
	
	private ApoStarzGame game;
	
	private ApoStarzEditor editor;
	
	public ApoStarzMain(String splashUrl) {
		super(splashUrl);
	}

	@Override
	public void init() {
		super.setTitle("=== ApoStarz ===");
		
		this.game = new ApoStarzGame(this);
		this.game.setSize(ApoStarzConstants.GAME_WIDTH, ApoStarzConstants.GAME_HEIGHT);
		
		this.editor = new ApoStarzEditor(this);
		this.editor.setSize(ApoStarzConstants.GAME_WIDTH, ApoStarzConstants.GAME_HEIGHT);
		
		this.setGame(null);
		
		super.setIconImage(new ApoStarzImages().getPlayer( 32, 32, Color.blue ));
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
	
	public static void main(String[] args) {
		new ApoStarzMain("images/logo.png");
	}

}
