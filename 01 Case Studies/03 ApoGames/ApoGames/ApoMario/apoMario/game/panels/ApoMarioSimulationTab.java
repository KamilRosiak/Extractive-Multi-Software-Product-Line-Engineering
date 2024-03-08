package apoMario.game.panels;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import org.apogames.entity.ApoButton;

import apoMario.ApoMarioConstants;

/**
 * SimulationTab<br />
 * Klasse, die für die Tabs oben bei der Simulation gedacht ist<br />
 * @author Dirk Aporius
 */
public class ApoMarioSimulationTab extends ApoButton {

	private boolean bChoose;
	
	public ApoMarioSimulationTab(int x, int y, int width, int height, String function) {
		super(null, x, y, width, height, function);
	}

	public boolean isChoosen() {
		return this.bChoose;
	}

	public void setChoosen(boolean bChoose) {
		this.bChoose = bChoose;
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		Shape shape = g.getClip();
		g.setClip(new Rectangle2D.Float(this.getX(), this.getY(), this.getWidth(), this.getHeight()));
		if (!this.bChoose) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRoundRect((int)(this.getX()), (int)(this.getY()), (int)(this.getWidth() - 1), (int)(this.getHeight() + 15), 15, 15);
		}
		g.setColor(Color.BLACK);
		g.drawRoundRect((int)(this.getX()), (int)(this.getY()), (int)(this.getWidth() - 1), (int)(this.getHeight() + 15), 15, 15);
		
		if (!this.bChoose) {
			g.drawLine((int)(this.getX()), (int)(this.getY() + this.getHeight() - 1), (int)(this.getX() + this.getWidth() - 1), (int)(this.getY() + this.getHeight() - 1));
			if (this.isBOver()) {
				g.setColor(Color.YELLOW);
			} else if (this.isBPressed()) {
				g.setColor(Color.RED);
			}
		}
		g.setFont(ApoMarioConstants.FONT_STATISTICS);
		int w = g.getFontMetrics().stringWidth(this.getFunction());
		int h = g.getFontMetrics().getHeight() - g.getFontMetrics().getDescent() * 2;
		g.drawString(this.getFunction(), this.getX() + this.getWidth()/2 - w/2, this.getY() + this.getHeight()/2 + h/2);
		
		g.setClip(shape);
	}
	
}
