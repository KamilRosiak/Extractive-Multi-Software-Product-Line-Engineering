package apoRelax.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

import org.apogames.entity.ApoButton;

/**
 * OptionButton<br />
 * Klasse, die relativ abstrakt unterschiedliche Ausgaben für einen Button hat entweder<br />
 * er ist ausgewählt oder nicht (in diesem Fall mit einem Häkchen) <br />
 * @author Dirk Aporius
 */
public class ApoRelaxOptionsButton extends ApoButton {
	
	private boolean bActive;
	
	public ApoRelaxOptionsButton(BufferedImage iImage, float x, float y, float width, float height) {
		this(iImage, (int)x, (int)y, (int)width, (int)height, "");
	}
	
	public ApoRelaxOptionsButton(BufferedImage iImage, float x, float y, float width, float height, String description) {
		this(iImage, (int)x, (int)y, (int)width, (int)height, description, false);
	}
	
	public ApoRelaxOptionsButton(BufferedImage iImage, float x, float y, float width, float height, String description, boolean bActive) {
		super(iImage, (int)x, (int)y, (int)width, (int)height, description);
		this.bActive = bActive;
	}
	
	public void init() {
		super.init();
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
		if (super.isBVisible()) {
			super.render(g, changeX, changeY);
			if (super.isBOver()) {
				g.setColor(new Color(254, 0, 0, 160));
				g.drawRoundRect((int)(this.getX() + 1), (int)(this.getY() + 1), (int)(this.getWidth() - 3), (int)(this.getHeight() - 3), 10, 10);
			}
			if (this.bActive) {
				g.setColor(Color.BLACK);
				Stroke stroke = g.getStroke();
				g.setStroke(new BasicStroke(4));
				g.drawLine((int)(this.getX() + 6), (int)(this.getY() + 6), (int)(this.getX() + this.getWidth() - 6), (int)(this.getY() + this.getHeight() - 6));
				g.drawLine((int)(this.getX() + this.getWidth() - 6), (int)(this.getY() + 6), (int)(this.getX() + 6), (int)(this.getY() + this.getHeight() - 6));
				g.setStroke(stroke);
			}
		}
	}

}
