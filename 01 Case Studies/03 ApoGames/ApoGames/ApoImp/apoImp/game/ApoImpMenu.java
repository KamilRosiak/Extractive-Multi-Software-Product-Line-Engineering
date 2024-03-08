package apoImp.game;

import java.awt.Color;
import java.awt.Graphics2D;

public class ApoImpMenu extends ApoImpModel {

	public static final String QUIT = "X";
	public static final String EDITOR = "editor";
	public static final String USERLEVELS = "userlevels";
	public static final String GAME = "game";
	
	public ApoImpMenu(ApoImpPanel game) {
		super(game);
	}

	@Override
	public void init() {
	}

	@Override
	public void keyButtonReleased(int button, char character) {
	}

	@Override
	public void mouseButtonFunction(String function) {
		if (function.equals(ApoImpMenu.QUIT)) {
			System.exit(0);
		} else if (function.equals(ApoImpMenu.GAME)) {
			super.getGame().setGame(false, false, "");
		} else if (function.equals(ApoImpMenu.USERLEVELS)) {
			super.getGame().setGame(true, false, "");
		} else if (function.equals(ApoImpMenu.EDITOR)) {
			super.getGame().setEditor();
		}
	}

	@Override
	public void mouseButtonReleased(int x, int y, boolean bRight) {
	}

	@Override
	public void think(int delta) {
	}

	@Override
	public void render(Graphics2D g) {
		super.getGame().renderSnow(g);
		
		g.setColor(new Color(150, 150, 190));
		g.fillRect(0, 0, 480, 30);
		g.setColor(new Color(40, 40, 80));
		g.drawRect(0, 0, 479, 30);
		
		g.setFont(g.getFont().deriveFont(20f).deriveFont(1));
		g.setColor(new Color(40, 40, 80));
		String s = "ApoElf";
		int w = g.getFontMetrics().stringWidth(s);
		g.drawString(s, 240 - w/2, 25);
	}

}
