package org.apogames.help;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JLabel; 

public class ApoSplashPainter extends JLabel {

	private static final long serialVersionUID = 1L;
	
	private Image image;

	public void setImage(Image image) {
		this.image = image;
		super.setPreferredSize(new Dimension(image.getWidth(null), image
				.getHeight(null)));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		g.drawImage(this.image, 0, 0, this);
	}
}
