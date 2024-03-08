package apoMarc.entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;

import org.apogames.entity.ApoButton;

public class ApoMarcHelpButton extends ApoButton {

	private final Font FONT = new Font(Font.SANS_SERIF, Font.BOLD, 30);
	
	private boolean bActive;
	
	private String description;
	
	public ApoMarcHelpButton(float x, float y, float width, float height, String function) {
		this((int)x, (int)y, (int)width, (int)height, function, "");
	}
	
	public ApoMarcHelpButton(float x, float y, float width, float height, String function, String description) {
		super(null, (int)x, (int)y, (int)width, (int)height, function);
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
		if (super.isBVisible()) {
			g.setColor(Color.WHITE);
			g.fillRoundRect((int)(this.getX()), (int)(this.getY()), (int)(this.getWidth() - 1), (int)(this.getHeight() - 1), 10, 10);
			g.setColor(Color.BLACK);
			g.drawRoundRect((int)(this.getX()), (int)(this.getY()), (int)(this.getWidth() - 1), (int)(this.getHeight() - 1), 10, 10);
			if (super.isBOver()) {
				g.setColor(Color.RED);
				g.drawRoundRect((int)(this.getX() + 1), (int)(this.getY() + 1), (int)(this.getWidth() - 2), (int)(this.getHeight() - 2), 10, 10);
			}
			if (this.bActive) {
				g.setColor(Color.GREEN);
				Stroke stroke = g.getStroke();
				g.setStroke(new BasicStroke(5));
				g.drawLine((int)(this.getX() + 6), (int)(this.getY() + this.getHeight() - 10), (int)(this.getX() + this.getWidth()/2 - 4), (int)(this.getY() + this.getHeight() - 6));
				g.drawLine((int)(this.getX() + this.getWidth() - 6), (int)(this.getY() + 6), (int)(this.getX() + this.getWidth()/2 - 4), (int)(this.getY() + this.getHeight() - 6));
				g.setStroke(stroke);
			}
			if (this.description != null) {
				g.setColor(Color.WHITE);
				if (super.isBOver()) {
					g.setColor(Color.RED);
				}
				String s = this.description;
				g.setFont(this.FONT);
				int h = g.getFontMetrics().getHeight() - 2 * g.getFontMetrics().getDescent();
				g.drawString(s, this.getX() + this.getWidth() + 3, this.getY() + this.getHeight()/2 + h/2);
			}
		}
	}

}

