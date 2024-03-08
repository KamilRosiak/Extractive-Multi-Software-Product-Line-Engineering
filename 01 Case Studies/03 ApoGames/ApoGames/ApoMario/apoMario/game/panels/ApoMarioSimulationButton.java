package apoMario.game.panels;

import java.awt.Color;
import java.awt.Graphics2D;

import org.apogames.entity.ApoButton;

/**
 * SimulationButton<br />
 * Klasse, die für die Replays nach einer Simulation bestimmt sind<br />
 * @author Dirk Aporius
 */
public class ApoMarioSimulationButton extends ApoButton {

	private ApoMarioSimulationHelp help;
	
	public ApoMarioSimulationButton(int x, int y, int width, int height, ApoMarioSimulationHelp help) {
		super(null, x, y, width, height, "");
		this.help = help;
	}

	public ApoMarioSimulationHelp getHelp() {
		return this.help;
	}

	public void render(Graphics2D g, int changeX, int changeY) {
		g.setColor(Color.WHITE);
		g.fillRect((int)(this.getX() - changeX), (int)(this.getY() - changeY), (int)(this.getWidth() - 1), (int)(this.getHeight() - 1));
		if (this.isBPressed()) {
			g.setColor(Color.RED);
		} else if (this.isBOver()) {
			g.setColor(Color.YELLOW);
		} else {
			g.setColor(Color.BLACK);
		}
		g.drawRect((int)(this.getX() - changeX), (int)(this.getY() - changeY), (int)(this.getWidth() - 1), (int)(this.getHeight() - 1));
		
		if (this.help != null) {
			g.setColor(Color.BLACK);
			String s = "(" + this.help.getWidth() + "; " + this.help.getDifficulty() + "; " + this.help.getRandom() + ") " + this.help.getNameOne() + " : " + this.help.getPointsOne();
			if (this.help.getReplay().getPlayerTwo().getMaxSteps() <= 0) {
				if (!this.help.isInFinish()) {
					g.setColor(Color.RED);	
				}
			} else {
				s = this.help.getNameOne() + " : " + this.help.getPointsOne()+"; " + this.help.getNameTwo() + " : " + this.help.getPointsTwo();
				if (this.help.getPointsOne() > this.help.getPointsTwo()) {
					g.setColor(Color.RED);	
				} else {
					g.setColor(Color.BLUE);
				}
			}
			int h = g.getFontMetrics().getHeight() - g.getFontMetrics().getDescent() * 2;
			g.drawString(s, (int)(this.getX() + 3 - changeX), (int)(this.getY() + this.getHeight()/2 + h/2));
		}
	}
}
