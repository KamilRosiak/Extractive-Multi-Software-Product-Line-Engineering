package apoSimple.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

public class ApoSimpleButtonNot extends ApoSimpleOptionsButton {

	public ApoSimpleButtonNot(BufferedImage iImage, float x, float y, float width, float height, String description, boolean bActive) {
		super(iImage, x, y, width, height, description, bActive);
	}

	public void render(Graphics2D g, int changeX, int changeY) {
		if (super.isBVisible()) {
			if ( this.isBVisible() ) {
				if ( this.isBPressed() ) 
					g.drawImage( this.getIBackground(), (int)this.getX() + changeX, (int)this.getY() + changeY, (int)( this.getX() + changeX + this.getWidth() ), (int)( this.getY() + changeY + this.getHeight() ), (int)( 2*this.getWidth() ), 0, (int)( 3*this.getWidth() ), (int)this.getHeight(), null);
				else if ( this.isBOver() )
					g.drawImage( this.getIBackground(), (int)this.getX() + changeX, (int)this.getY() + changeY, (int)( this.getX() + changeX + this.getWidth() ), (int)( this.getY() + changeY + this.getHeight() ), (int)( 1*this.getWidth() ), 0, (int)( 2*this.getWidth() ), (int)this.getHeight(), null);
				else if ( this.isBUse() )
					g.drawImage( this.getIBackground(), (int)this.getX() + changeX, (int)this.getY() + changeY, (int)( this.getX() + changeX + this.getWidth() ), (int)( this.getY() + changeY + this.getHeight() ), (int)( 3*this.getWidth() ), 0, (int)( 4*this.getWidth() ), (int)this.getHeight(), null);
				else
					g.drawImage( this.getIBackground(), (int)this.getX() + changeX, (int)this.getY() + changeY, (int)( this.getX() + changeX + this.getWidth() ), (int)( this.getY() + changeY + this.getHeight() ), (int)( 0*this.getWidth() ), 0, (int)( 1*this.getWidth() ), (int)this.getHeight(), null);
			}
			if (!this.isBActive()) {
				g.setColor(Color.RED);
				Stroke stroke = g.getStroke();
				g.setStroke(new BasicStroke(2));
				int add = 10;
				g.drawLine((int)(this.getX() + add), (int)(this.getY() + add), (int)(this.getX() + this.getWidth() - add), (int)(this.getY() + this.getHeight() - add));
				g.drawLine((int)(this.getX() + this.getWidth() - add), (int)(this.getY() + add), (int)(this.getX() + add), (int)(this.getY() + this.getHeight() - add));
				g.setStroke(stroke);
			}
		}
	}
}