package apoMario.game.panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import org.apogames.entity.ApoButton;

import apoMario.ApoMarioConstants;

/**
 * OptionButton<br />
 * Klasse, die relativ abstrakt unterschiedliche Ausgaben für einen Button hat entweder<br />
 * er ist ausgewählt oder nicht (in diesem Fall mit einem Häkchen) <br />
 * @author Dirk Aporius
 */
public class ApoMarioOptionsButton extends ApoButton {

	private boolean bActive;
	
	private String description;
	
	public ApoMarioOptionsButton(float x, float y, float width, float height) {
		this((int)x, (int)y, (int)width, (int)height, "");
	}
	
	public ApoMarioOptionsButton(float x, float y, float width, float height, String description) {
		super(null, (int)x, (int)y, (int)width, (int)height, description);
		this.description = description;
	}
	
	public void init() {
		super.init();
		this.bActive = false;
	}

	public boolean isBActive() {
		return this.bActive;
	}

	public void setBActive(boolean bActive) {
		this.bActive = bActive;
	}

	public boolean mouseReleased(int x, int y) {
		if (super.getReleased(x, y)) {
			this.bActive = !this.bActive;
			return true;
		}
		return false;
	}
	
	public void render(Graphics2D g, int changeX, int changeY) {
		g.setColor(ApoMarioOptionsDrag.COLOR_BACKGROUND);
		g.fillRoundRect((int)(this.getX()), (int)(this.getY()), (int)(this.getWidth() - 1), (int)(this.getHeight() - 1), 5 * ApoMarioConstants.APP_SIZE, 5 * ApoMarioConstants.APP_SIZE);
		g.setColor(Color.BLACK);
		g.drawRoundRect((int)(this.getX()), (int)(this.getY()), (int)(this.getWidth() - 1), (int)(this.getHeight() - 1), 5 * ApoMarioConstants.APP_SIZE, 5 * ApoMarioConstants.APP_SIZE);
		if (super.isBOver()) {
			g.setColor(new Color(254, 0, 0, 160));
			g.drawRoundRect((int)(this.getX() + 1), (int)(this.getY() + 1), (int)(this.getWidth() - 3), (int)(this.getHeight() - 3), 5 * ApoMarioConstants.APP_SIZE, 5 * ApoMarioConstants.APP_SIZE);
		}
		if (this.bActive) {
			g.setColor(Color.GREEN);
			Stroke stroke = g.getStroke();
			g.setStroke(new BasicStroke(2 * ApoMarioConstants.APP_SIZE));
			g.drawLine((int)(this.getX() + 3 * ApoMarioConstants.APP_SIZE), (int)(this.getY() + this.getHeight() - 5 * ApoMarioConstants.APP_SIZE), (int)(this.getX() + this.getWidth()/2 - 2 * ApoMarioConstants.APP_SIZE), (int)(this.getY() + this.getHeight() - 3 * ApoMarioConstants.APP_SIZE));
			g.drawLine((int)(this.getX() + this.getWidth() - 3 * ApoMarioConstants.APP_SIZE), (int)(this.getY() + 3 * ApoMarioConstants.APP_SIZE), (int)(this.getX() + this.getWidth()/2 - 2 * ApoMarioConstants.APP_SIZE), (int)(this.getY() + this.getHeight() - 3 * ApoMarioConstants.APP_SIZE));
			g.setStroke(stroke);
		}
		if (this.description != null) {
			g.setColor(Color.BLACK);
			String s = this.description;
			int w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, this.getX() - 1 - w, this.getY() + this.getHeight());
		}
	}

}
